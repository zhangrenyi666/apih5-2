import React, { Component } from 'react';
import { SearchBar, Toast, ListView, PullToRefresh, WingBlank, WhiteSpace } from 'antd-mobile';
import { Filter, FilterDialog } from './filter';
import moment from 'moment';
import 'moment/locale/zh-cn';
import styles from './GuidanceList.less';
class SpecList extends Component {
  constructor(props) {
    super(props);
    const dataSource = new ListView.DataSource({
      rowHasChanged: (row1, row2) => row1 !== row2,
    });
    const { match: { params: { specType } } } = props;
    this.pageSize = 10;
    this.currentPage = 1;
    this.specType = specType; //区分是规范还是手册的参数
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
    let localDB = localStorage.getItem(`SpecList${this.specType}`) ? JSON.parse(localStorage.getItem(`SpecList${this.specType}`)) : {}
    for (const key in localDB) {
      if (localDB.hasOwnProperty(key)) {
        this[key] = localDB[key]
      }
    }
    this.manuallyRefreshFun({ searchKeywords: this.toSearchKeywords })
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
    const { myFetch } = this.props
    this.currentPage = 1;
    var specLevelId = ""
    if (this.toFilterId_lv1 === "filt" && this.toFilterIds_lv2[0] && this.toFilterIds_lv2[0].specLevelId) {
      specLevelId = this.toFilterIds_lv2[0].specLevelId
    }
    const body = {
      specType: this.specType,
      pageSize: this.pageSize,
      currentPage: this.currentPage,
      specTitle: this.toSearchKeywords,// 指导资料标题
      specLevelId,// 分类ID
    }
    const localDB = {
      toSearchKeywords: this.toSearchKeywords,// 指导资料标题
      toFilterId_lv1: this.toFilterId_lv1,
      toFilterIds_lv2: this.toFilterIds_lv2,
      initData_lv2: this.initData_lv2
    }
    localStorage.setItem(`SpecList${this.specType}`, JSON.stringify(localDB))
    myFetch('getSpecHomePage', body).then(({ success, data, message, totalNumber }) => {
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
    const { myFetch } = this.props
    if (isLoading || !hasMore) {
      return;
    } else {
      this.setState({ isLoading: true, message: "正在加载..." });
      this.currentPage++;
      var specLevelId = ""
      if (this.toFilterId_lv1 === "filt" && this.toFilterIds_lv2[0] && this.toFilterIds_lv2[0].specLevelId) {
        specLevelId = this.toFilterIds_lv2[0].specLevelId
      }
      const body = {
        specType: this.specType,
        pageSize: this.pageSize,
        currentPage: this.currentPage,
        specTitle: this.toSearchKeywords,// 指导资料标题
        specLevelId,// 分类ID
      }
      myFetch('getSpecHomePage', body).then(({ success, data, message, totalNumber }) => {
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
  // 一级筛选组件改变回调
  onFilterChange = (item) => {
    const { filterId_lv1 } = this.state
    if (item["value"] !== "all") {
      this.onFilterDialogOpen(item["value"])
    } else {
      if (filterId_lv1 !== "all") {
        this.toFilterId_lv1 = "all"
        this.manuallyRefreshFun()
      }
    }
  };
  onFilterDialogChange = (curFilterIds_lv2) => {
    this.setState({ curFilterIds_lv2 })
  };
  // FilterDialog打开
  onFilterDialogOpen = (value) => {
    const { filterId_lv1 } = this.state;
    const { myFetch } = this.props
    var initData_lv2 = [];
    if (filterId_lv1 === value) {
      for (let i = 0; i < this.initData_lv2.length; i++) {
        let initData_lv2_item = {}
        for (var key in this.initData_lv2[i]) {
          initData_lv2_item[key] = this.initData_lv2[i][key];
        }
        initData_lv2.push(initData_lv2_item);
      }
      this.setState({ curFilterId_lv1: value, curFilterIds_lv2: initData_lv2 })
    } else {
      Toast.loading('Loading...', 0);
      myFetch('getSpecLevelList').then(({ success, data }) => {
        if (success) {
          for (var i = 0; i <= data.length - 1; i++) {
            const { specLevelId, specLevelName } = data[i]
            initData_lv2.push({
              specLevelId,
              specLevelName,
              checked: false
            })
          }
          Toast.hide();
          this.setState({ curFilterId_lv1: value, curFilterIds_lv2: initData_lv2 })
        }
      })
    }
  }
  // FilterDialog保存
  onFilterDialogSave = (checkedDatas) => {
    console.log(checkedDatas)
    const { curFilterIds_lv2, filterId_lv1, curFilterId_lv1 } = this.state
    if (checkedDatas.length === 0 && filterId_lv1 !== curFilterId_lv1) {
      this.onFilterDialogClose();
    } else {
      if (checkedDatas.length === 0 && filterId_lv1 === curFilterId_lv1) {
        this.toFilterId_lv1 = "all"
      } else {
        this.toFilterId_lv1 = curFilterId_lv1
      }
      this.initData_lv2 = curFilterIds_lv2;
      this.toFilterIds_lv2 = checkedDatas;
      this.manuallyRefreshFun({ curFilterId_lv1: null })
    }
  }
  // FilterDialog关闭
  onFilterDialogClose = () => {
    this.setState({ curFilterId_lv1: null })
  };
  render() {
    const {
      searchKeywords,
      curFilterId_lv1,//一级当前id
      filterId_lv1,//一级选中id
      dataSource,
      curFilterIds_lv2,
      message,
      refreshing } = this.state
    const initData_lv1 = this.initData_lv1
    const renderRow = (rowData, sectionID, rowID) => {
      if (rowData.errMsg) {
        return <div key={rowID} className={styles["lny-list-footer"]}>{rowData.errMsg}</div>
      } else {
        return <DatumItem {...this.props} key={rowID} rowData={rowData} sectionID={sectionID} rowID={rowID} history={this.props.history} />
      }
    }
    return (
      <div className={styles["lny-DatumList"]}>
        <SearchBar value={searchKeywords} placeholder="搜索关键词" onChange={this.onSearchChange} />
        <Filter data={initData_lv1} curValue={curFilterId_lv1} value={filterId_lv1} onChange={this.onFilterChange} />
        <ListView
          className={styles["flex"]}
          pageSize={5}
          dataSource={dataSource}
          renderRow={renderRow}
          pullToRefresh={
            <PullToRefresh
              distanceToRefresh={window.devicePixelRatio * 30}
              refreshing={refreshing}
              onRefresh={this.onRefresh}
              indicator={{ deactivate: '下拉刷新' }}
            />
          }
          onEndReached={this.onEndReached}
          renderFooter={() => {
            return (
              <div className={styles["lny-list-footer"]}>
                {message}
              </div>)
          }
          }
        />
        <FilterDialog
          data={curFilterIds_lv2}
          radio={true}
          valueName={"specLevelId"}
          textName={"specLevelName"}
          topH={"88px"}
          show={curFilterId_lv1 ? true : false}
          onChange={this.onFilterDialogChange}
          onClose={this.onFilterDialogClose}
          onSave={this.onFilterDialogSave} />
      </div>
    );
  }
}
class DatumItem extends Component {
  render() {
    const { rowData, rowID, myFetch } = this.props
    return (
      <div onClick={() => {
        const body = {
          specId: rowData.specId
        }
        myFetch('getSpecDetailInsertHistory', body).then(() => {
          if (rowData.specAccessoryList) {
            document.location = rowData.specAccessoryList[0].accessoryAddress
          } else {
            Toast.fail("不存在该资料或该资料解析异常", 2)
          }
        })
      }}>
        <WingBlank size="sm">
          <WhiteSpace size="sm" />
          <div className={styles["lny-DatumList-item"]} style={{ borderColor: (rowID % 2 === 0) ? "#FF3F01" : "#FFAF02" }}>
            <div className={styles["lny-DatumList-item-title"]}>{rowData.specTitle}</div>
            <div className={styles["lny-DatumList-item-introduce"]}>{"实施号：" + rowData.specNumber}</div>
            <div className={styles["lny-DatumList-item-tool"]}>
              <div className={styles["lny-DatumList-item-tool-span1"]}>{(rowData.specStd === "0" ? "国标" : "行内") + "-" + rowData.specLevel.specLevelName}</div>
              <div className={styles["lny-DatumList-item-tool-span2"]}>{moment(rowData.specDate).format("YYYY-MM-DD")}</div>
            </div>
          </div>
        </WingBlank>
      </div>
    )
  }
}
export default SpecList;