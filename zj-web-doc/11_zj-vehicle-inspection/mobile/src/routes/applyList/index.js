import React, { Component } from 'react';
import { SearchBar, ListView, PullToRefresh, WingBlank, WhiteSpace, Flex, } from 'antd-mobile';//Toast,Radio
import { myFetch } from '../../tools';// localDB,countDown 
import { push } from 'react-router-redux';
import 'moment/locale/zh-cn';
import styles from './index.less';
class CarList extends Component {
  constructor(props) {
    super(props);
    const dataSource = new ListView.DataSource({
      rowHasChanged: (row1, row2) => row1 !== row2,
    });
    this.pageSize = 10;
    this.currentPage = 1;
    this.initData = [];//初始数据_列表  
    this.toSearchKeywords = "";//将要搜索的Keywords 

    this.state = {
      searchKeywords: this.toSearchKeywords,// 搜索关键词  
      dataSource: dataSource.cloneWithRows(this.initData),
      hasMore: false,//有没有更多数据
      refreshing: false,//列表是否在下拉刷新
      isLoading: false,//列表是否在上拉加载
      carId: '',//选择时就会更新 
      // carName: '',//车名 详情弹出的时候会用
    }
  }
  componentDidMount() {//首次加载，执行手动刷新方法 
    document.getElementsByTagName('title')[0].innerHTML = '申请列表';
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

  mSetState(obj = {}) {
    this.setState({
      ...obj
    })
  }

  // 列表数据查询
  onRefresh = () => {
    const { dataSource } = this.state
    this.currentPage = 1;
    const body = {
      // status: this.type,
      // pageSize: this.pageSize,
      // currentPage: this.currentPage,
      carName: this.toSearchKeywords,// 指导资料标题 
    }
    // console.log(body)
    myFetch('getApplyList', body).then(({ success, data, message, totalNumber }) => {
      console.log('getApplyList', data)
      this.initData = data || [];
      this.setState({
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
      const body = {
        pageSize: this.pageSize,
        currentPage: this.currentPage,
        searchText: this.toSearchKeywords,// 指导资料标题 
      }
      myFetch('getApplyList', body, ).then(({ success, data, message, totalNumber }) => {
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
  goApply() {//去申请按钮点击方法 
    const { dispatch } = this.props;
    dispatch(push(`/applyAdd/${this.state.carId}`))
  }
  render() {
    const {
      searchKeywords,
      dataSource,
      message,
      refreshing } = this.state

    const renderRow = (rowData, sectionID, rowID) => {
      if (rowData.errMsg) {
        return <div key={rowID} className="lny-list-footer">{rowData.errMsg}</div>
      } else {
        return <DatumItem key={rowID} type={this.type} rowData={rowData} sectionID={sectionID} rowID={rowID} {...this.props} />
      }
    }
    return (
      <div className={styles["lny-List"]}>
        <SearchBar value={searchKeywords} placeholder="输入车辆名" onChange={this.onSearchChange} />
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
              </div>
            )
          }
          }
        />
      </div>
    );
  }
}
class DatumItem extends Component {
  render() {
    const { rowData, rowID, dispatch } = this.props// type, mSetState, detailOpen,time, 
    const {
      id = "",//车辆id 
      carName = "--",//车辆名字
      useUnit = "--",//使用单位  
      modelNumber,//型号
      approvalType,//办理类型
      approvalFlag,//审批状态
      approvalUser,//审批人
      approvalId,//审批id
    } = rowData;
    
    //办理类型
    let _approvalType = '';
    switch (approvalType) {
      case '0':
        _approvalType = '车辆年检';
        break;
      case '1':
        _approvalType = '车辆保险';
        break;
      default:
        _approvalType = '未知';
    }
    // console.log(approvalFlag)
    //审批状态
    let _approvalFlag = '';
    switch (approvalFlag) {
      case '0':
        _approvalFlag = '未审批';
        break;
      case '1':
        _approvalFlag = '已审批';
        break;
      default:
        _approvalFlag = '未知';
    }

    return (
      <div onClick={() => {
        dispatch(push(`/applyDetail/${approvalId}`))
      }}>
        <WingBlank size="sm">
          <WhiteSpace size="sm" />

          <div className={styles["lny-List-item"]} style={{ borderColor: (rowID % 2 === 0) ? "#FF3F01" : "#FFAF02" }}>
            <Flex className={styles["lny-List-item-title"]}>
              <Flex.Item> 【{_approvalType}】 </Flex.Item>
              <Flex.Item style={{ textAlign: 'right', fontSize: '15px' }}>车辆名称：{carName}</Flex.Item>

            </Flex>
            <div className={styles["lny-List-item-introduce"]}>
              <Flex style={{ color: "#333", paddingBottom: "7px" }}>
                <Flex.Item>审批人：{approvalUser}</Flex.Item>
                <Flex.Item style={{ textAlign: 'right', }}>审批状态：{_approvalFlag}</Flex.Item>
              </Flex>
            </div>
            <div className={styles["lny-List-item-tool"]}>
              <div className={styles["lny-List-item-tool-span1"]}>使用单位：{useUnit}</div>
            </div>
          </div>
        </WingBlank>
      </div>
    )
  }
}
export default CarList;