import React, { Component } from 'react';
import { SearchBar, ListView, PullToRefresh, WingBlank, WhiteSpace,  Flex,   } from 'antd-mobile';//Toast,Radio
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
    document.getElementsByTagName('title')[0].innerHTML = '车辆列表';
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
    myFetch('getZjCjCarRemindList', body).then(({ success, data, message, totalNumber }) => {
      console.log('getZjCjCarRemindList', data)
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
      const body = {
        pageSize: this.pageSize,
        currentPage: this.currentPage,
        searchText: this.toSearchKeywords,// 指导资料标题 
      }
      myFetch('getRunTheAgencyList', body, ).then(({ success, data, message, totalNumber }) => {
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
      refreshing} = this.state
  
    const renderRow = (rowData, sectionID, rowID) => {
      if (rowData.errMsg) {
        return <div key={rowID} className="lny-list-footer">{rowData.errMsg}</div>
      } else { 
        return <DatumItem  key={rowID} type={this.type} rowData={rowData} sectionID={sectionID} rowID={rowID} {...this.props} />
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
      isCarCheck,//保险状态 0：未办理；1：待办理；2：审批中；3：已完成
      isCarInsurance,//车检状态  0：未办理；1：待办理；2：审批中；3：已完成
    } = rowData;

    let _isCarCheck = '';//保险状态 
    switch (isCarCheck) { //  #ff9900 橙色    #ff4000  红色     #99cc00 绿色
      case '0':
        _isCarCheck = '未办理';
        break;
      case '1':
        _isCarCheck = '待办理';
        break;
      case '2':
        _isCarCheck = '审批中';
        break; 
      case '3':
        _isCarCheck = '通过';
        break;
      case '4':
        _isCarCheck = '驳回';
        break;  
      default:
        _isCarCheck = '未知';
    }

    let _isCarInsurance = '';//车检状态 
    switch (isCarInsurance) { //  #ff9900 橙色    #ff4000  红色     #99cc00 绿色
      case '0':
        _isCarInsurance = '未办理';
        break;
      case '1':
        _isCarInsurance = '待办理';
        break;
      case '2':
        _isCarInsurance = '审批中';
        break;
      case '3':
        _isCarInsurance = '通过';
        break;
      case '4':
        _isCarInsurance = '驳回';
        break;  
      default:
        _isCarInsurance = '未知';
    }

    return (
      <div onClick={() => { 
        dispatch(push(`/carDetail/${id}`))
      }}> 
        <WingBlank size="sm"> 
        <WhiteSpace size="sm" /> 

            <div className={styles["lny-List-item"]} style={{ borderColor: (rowID % 2 === 0) ? "#FF3F01" : "#FFAF02" }}>
              <Flex className={styles["lny-List-item-title"]}>
                <Flex.Item> 【{carName}】 </Flex.Item>
                <Flex.Item style={{ textAlign: 'right', fontSize: '15px'}}>型号：{modelNumber}</Flex.Item>
                 
              </Flex>
              <div className={styles["lny-List-item-introduce"]}>
                <Flex style={{ color: "#333", paddingBottom: "7px" }}>
                  <Flex.Item>保险状态：{_isCarInsurance}</Flex.Item>
                  <Flex.Item style={{ textAlign: 'right', }}>年检状态：{_isCarCheck}</Flex.Item>
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