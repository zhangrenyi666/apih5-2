import React, { Component } from 'react';
import { SearchBar, ListView, PullToRefresh, WingBlank, WhiteSpace, Badge, Flex, Toast } from 'antd-mobile';
import { Select } from 'antd';
import { myFetch, localDB } from '../../tools';
import { Filter, FilterDialog } from '../../components';
import { push } from 'react-router-redux';
import moment from 'moment';
import 'moment/locale/zh-cn';
import styles from './index.less';
const Option = Select.Option;
const { set: setLocalDB, get: getLocalDB } = localDB
class SpecList extends Component {
  constructor(props) {
    super(props);
    const dataSource = new ListView.DataSource({
      rowHasChanged: (row1, row2) => row1 !== row2,
    });
    const { match: { params: { typeName } } } = props;
    let type = "-1", notFound = false
    if (typeName === "problemList") {
      type = "0"
    } else {
      notFound = true
    }
    this.pageSize = 10;
    this.currentPage = 1;
    this.type = type; //区分是规范还是手册的参数
    this.initData = [];//初始数据_列表
    this.initData_lv1 = [];//初始数据_一级筛选

    this.initData_lv2 = [];//初始数据_二级筛选
    this.toSearchKeywords = "";//将要搜索的Keywords
    this.toFilterId_lv1 = "all";//将要筛选的Id_一级筛选
    this.toFilterIds_lv2 = [];//将要筛选的Id集合_二级筛选

    this.state = {
      searchKeywords: this.toSearchKeywords,// 搜索关键词
      filterId_lv1: this.toFilterId_lv1,//筛选id_一级筛选
      curFilterId_lv1: null,//当前筛选id_一级筛选
      curFilterIds_lv2: [],
      dataSource: dataSource.cloneWithRows(this.initData),
      hasMore: false,//有没有更多数据
      refreshing: false,//列表是否在下拉刷新
      isLoading: false,//列表是否在上拉加载
      selectOptions: []
    }
  }
  componentDidMount() {//首次加载，执行手动刷新方法
    this.initData_lv1 = [
      {
        value: "all",
        text: "全部"
      }, {
        value: "filt",
        text: "筛选"
      }
    ];
    let filterLocalDB = getLocalDB(`filterLocalDB_${this.type}`)
    for (const key in filterLocalDB) {
      if (filterLocalDB.hasOwnProperty(key)) {
        this[key] = filterLocalDB[key]
      }
    }
    this.manuallyRefreshFun({ searchKeywords: this.toSearchKeywords });
    this.getSelectOption();
  }

  manuallyRefreshFun = (otherObj) => {//手动刷新方法
    if (!otherObj) { otherObj = {} }
    this.manuallyRefresh = true;
    this.setState({ ...otherObj, refreshing: true });
  }
  componentWillUpdate(nextProps, nextState) {//判断是否为手动刷新   
    if (nextState.refreshing && this.manuallyRefresh) {
      this.manuallyRefresh = false
      this.onRefresh()
    }
  }
  // 列表数据查询
  onRefresh = () => {
    const { dataSource } = this.state
    this.currentPage = 1;
    var taskSource = ""
    if (this.toFilterId_lv1 === "filt" && this.toFilterIds_lv2[0] && this.toFilterIds_lv2[0].taskSource) {
      taskSource = this.toFilterIds_lv2[0].taskSource
    }
    const body = {
      pageSize: this.pageSize,
      currentPage: this.currentPage,
      project: this.toSearchKeywords,
      questionTitleId: this.toSearctType,
    }
    const filterLocalDB = {
      toSearchKeywords: this.toSearchKeywords,// 指导资料标题
      toFilterId_lv1: this.toFilterId_lv1,
      toFilterIds_lv2: this.toFilterIds_lv2,
      initData_lv2: this.initData_lv2
    }
    // console.log(body)
    setLocalDB(`filterLocalDB_${this.type}`, filterLocalDB)
    myFetch('getZjQuestionApprovalListWechat', body, ).then(({ success, data, message, totalNumber }) => {
      // console.log(data)
      this.initData = data || [];
      this.setState({
        filterId_lv1: this.toFilterId_lv1,
        dataSource: dataSource.cloneWithRows(this.initData),
        refreshing: false,
        hasMore: this.initData.length < totalNumber,
        message: success ? (data.length > 0 ? (this.initData.length < totalNumber ? "上拉查看更多" : "没有更多了") : "暂无数据") : message
      });
    })
  }
  onEndReached = (event) => {
    const { isLoading, hasMore, dataSource } = this.state
    if (isLoading || !hasMore) {
      return;
    } else {
      this.setState({ isLoading: true, message: "正在加载..." });
      this.currentPage++;
      var taskSource = ""
      if (this.toFilterId_lv1 === "filt" && this.toFilterIds_lv2[0] && this.toFilterIds_lv2[0].taskSource) {
        taskSource = this.toFilterIds_lv2[0].taskSource
      }
      const body = {
        pageSize: this.pageSize,
        currentPage: this.currentPage,
        project: this.toSearchKeywords,// 项目名
        questionTitleId: this.toSearctType,
      }
      myFetch('getZjQuestionApprovalListWechat', body, ).then(({ success, data, message, totalNumber }) => {
        this.initData = this.initData.concat(data)
        this.setState({
          dataSource: dataSource.cloneWithRows(this.initData),
          hasMore: this.initData.length < totalNumber,
          message: this.initData.length < totalNumber ? "上拉查看更多" : "没有更多了",
          isLoading: false
        });
      })
    }
  }
  // 搜索组件改变回调
  onSearchChange = (keywords) => {
    if (this.toSearchKeywords !== keywords) {
      this.toSearchKeywords = keywords;
      this.manuallyRefreshFun({ searchKeywords: this.toSearchKeywords })
    }
  };
  //获取下拉类型数据
  getSelectOption = () => {
    myFetch("getZjXmQuestionTitleSelectAllList", {}).then(
      ({ success, data, message }) => {
        if (success) {
          this.setState({
            selectOptions: data
          });
        } else {
          Toast.fail(message);
        }
      }
    );
  };
  // 一级筛选组件改变回调
  // onFilterChange = (item) => {
  //   const { filterId_lv1 } = this.state
  //   if (item["value"] !== "all") {
  //     this.onFilterDialogOpen(item["value"])
  //   } else {
  //     if (filterId_lv1 !== "all") {
  //       this.toFilterId_lv1 = "all"
  //       this.manuallyRefreshFun()
  //     }
  //   }
  // };
  // onFilterDialogChange = (curFilterIds_lv2) => {
  //   this.setState({ curFilterIds_lv2 })
  // };
  // FilterDialog打开
  // onFilterDialogOpen = (value) => {
  //   const { filterId_lv1 } = this.state;
  //   var initData_lv2 = [];
  //   if (filterId_lv1 === value) {
  //     for (let i = 0; i < this.initData_lv2.length; i++) {
  //       let initData_lv2_item = {}
  //       for (var key in this.initData_lv2[i]) {
  //         initData_lv2_item[key] = this.initData_lv2[i][key];
  //       }
  //       initData_lv2.push(initData_lv2_item);
  //     }
  //     this.setState({ curFilterId_lv1: value, curFilterIds_lv2: initData_lv2 })
  //   } else {
  //     let initData_lv2 = [];
  //     myFetch('getQuestionAllListForWechat', {}).then(({ success, data, message }) => {
  //       if (success && data) {
  //         data.map((item) => {
  //           return initData_lv2.push({
  //             taskSource: item.classId,
  //             taskSourceName: item.className,
  //             checked: false
  //           })
  //         })

  //         this.setState({ curFilterId_lv1: value, curFilterIds_lv2: initData_lv2 })
  //       }
  //     })
  //   }
  // }
  // FilterDialog保存
  // onFilterDialogSave = (checkedDatas) => {
  //   const { curFilterIds_lv2, filterId_lv1, curFilterId_lv1 } = this.state
  //   if (checkedDatas.length === 0 && filterId_lv1 !== curFilterId_lv1) {
  //     this.onFilterDialogClose();
  //   } else {
  //     if (checkedDatas.length === 0 && filterId_lv1 === curFilterId_lv1) {
  //       this.toFilterId_lv1 = "all"
  //     } else {
  //       this.toFilterId_lv1 = curFilterId_lv1
  //     }
  //     this.initData_lv2 = curFilterIds_lv2;
  //     this.toFilterIds_lv2 = checkedDatas;
  //     this.manuallyRefreshFun({ curFilterId_lv1: null })
  //   }
  // }
  // FilterDialog关闭
  // onFilterDialogClose = () => {
  //   this.setState({ curFilterId_lv1: null })
  // };
  render() {
    const {
      searchKeywords,
      // curFilterId_lv1,//一级当前id
      // filterId_lv1,//一级选中id
      dataSource,
      // curFilterIds_lv2,
      message,
      refreshing,
      selectOptions
    } = this.state
    const {
      di
    } = this.props
    // const initData_lv1 = this.initData_lv1
    const renderRow = (rowData, sectionID, rowID) => {
      if (rowData.errMsg) {
        return <div key={rowID} className="lny-list-footer">{rowData.errMsg}</div>
      } else {
        return <DatumItem key={rowID} type={this.type} rowData={rowData} sectionID={sectionID} rowID={rowID} {...this.props} />
      }
    }
    return (
      <div className={styles["lny-List"]}>
        {/* <SearchBar value={searchKeywords} cancelText='重置' placeholder="搜索关键词" onChange={this.onSearchChange} /> */}
        {/* <Filter data={initData_lv1} curValue={curFilterId_lv1} value={filterId_lv1} onChange={this.onFilterChange} /> */}
        <div>
          <Flex style={{ background: "#efeff4" }}>
            <Flex.Item
              style={{ flex: "5", textAlign: "center" }}
            >
              <div style={{ paddingLeft: "8px" }}>
                <Select
                  style={{ width: "100%" }}
                  onSelect={(val) => {
                    this.toSearctType = val;
                    this.manuallyRefreshFun({
                      searchKeywords: this
                        .toSearchKeywords
                    });
                  }}
                  value={this.toSearctType}
                  placeholder="请选择"
                >
                  {selectOptions.map(
                    ({ titleId, title }) => {
                      return (
                        <Option
                          value={titleId}
                          key={titleId}
                        >
                          {title}
                        </Option>
                      );
                    }
                  )}
                </Select>
              </div>
            </Flex.Item>

            <Flex.Item style={{ flex: "5" }}>
              <SearchBar
                value={searchKeywords}
                cancelText='重置'
                placeholder="搜索部门名称"
                onCancel={() => {
                  this.toSearctType = '';
                  this.toSearchKeywords = '';
                  this.manuallyRefreshFun({
                    searchKeywords: this
                      .toSearchKeywords
                  });
                }}
                onChange={this.onSearchChange}
              />
            </Flex.Item>
          </Flex>
        </div>
        <ListView
          className="flex"
          pageSize={5}
          dataSource={dataSource}
          renderRow={renderRow}
          pullToRefresh={
            <PullToRefresh
              distanceToRefresh={window.devicePixelRatio * 15}
              refreshing={refreshing}
              onRefresh={this.onRefresh}
              indicator={{ deactivate: '下拉刷新' }}
            />
          }
          onEndReached={this.onEndReached}
          renderFooter={() => {
            return (
              <div className={"lny-list-footer"}>
                {message}
              </div>)
          }
          }
        />
        {/* <FilterDialog
          data={curFilterIds_lv2}
          radio={true}
          valueName={"taskSource"}
          textName={"taskSourceName"}
          topH={"88px"}
          show={curFilterId_lv1 ? true : false}
          onChange={this.onFilterDialogChange}
          onClose={this.onFilterDialogClose}
        onSave={this.onFilterDialogSave} /> */}
      </div>
    );
  }
}
class DatumItem extends Component {

  render() {
    const { rowData, rowID, dispatch, time, type } = this.props;
    const {
      recordid = "",
      personnelFlag = "",
      questionTitleName = "",
      questionTitleText = '',
      questionDescribe,
      project = '',
      modifyTime,
      createUserName,
      questionState,
      projectId,
      leaderDealFlag,
      checkDepartmentName,
      flowType
    } = rowData;
    let questionStateText = '';
    let color = '';
    switch (questionState) {
      case '0':
        questionStateText = '未提交';
        color = '#ff9900'
        break;
      case '1':
        questionStateText = '初审中';
        color = '#99cc00'
        break;
      case '2':
        questionStateText = '初审通过'
        color = '#007bc7'
        break;
      case '3':
        questionStateText = '初审失败'
        color = 'orange'
        break;
      case '4':
        questionStateText = '通过审核'
        color = 'gray'
        break;
      case '5':
        questionStateText = '复审驳回'
        color = '#ff9900'
        break;
      case '6':
        questionStateText = '复审驳回'
        color = '#99cc00'
        break;
      default:
        questionStateText = '未知'
        color = 'gray'
        break;
    }
    return (
      <div onClick={() => {
        if (flowType === '0') {
          if (leaderDealFlag === '1') {
            dispatch(push(`/problemDetail/${recordid}/${personnelFlag}`));
          } else {
            dispatch(push(`/problemDetails/${recordid}/${personnelFlag}/1`));
          }
        }else{
          dispatch(push(`/problemDetailt/${recordid}/${personnelFlag}`));
        }
      }}>
        <WingBlank size="sm">
          <WhiteSpace size="sm" />
          <div className={styles["lny-List-item"]} style={{ borderColor: (rowID % 2 === 0) ? "#FF3F01" : "#FFAF02" }}>
            <div className={styles["lny-List-item-title"]}>
              {`【${questionTitleName || questionTitleText}】`}
              {
                < Badge text={questionStateText}
                  style={{
                    padding: '0 3px',
                    borderRadius: '2px',
                    border: '1px solid ' + color,
                    backgroundColor: 'transparent',
                    color: color,
                  }}
                />
              }
            </div>
            <div className={styles["lny-List-item-introduce"]}>
              <div style={{ color: "#333", width: '100%', paddingBottom: "7px", wordWrap: 'nowrap', wordBreak: 'break-all' }}>
                {questionDescribe}
              </div>
            </div>
            <div className={styles["lny-List-item-tool"]}>
              <div>
                <div className={styles["lny-List-item-tool-span"]}><div style={{float:'left'}}>来自：{checkDepartmentName}</div><div style={{float:'right'}}>哈哈哈</div></div>
                <div className={styles["lny-List-item-tool-span"]}>提出者：{createUserName}</div>
                <div className={styles["lny-List-item-tool-span"]}>提出时间：{moment(modifyTime).format('YYYY/MM/DD HH:mm:ss')}</div>
              </div>
            </div>


          </div>
        </WingBlank>
      </div>
    )
  }
}
export default SpecList;