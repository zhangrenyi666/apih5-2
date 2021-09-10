import React, { Component } from 'react';
import { SearchBar, ListView, SegmentedControl, PullToRefresh, WingBlank, WhiteSpace, Flex } from 'antd-mobile';
import { myFetch } from '../../tools';
import moment from 'moment';
import 'moment/locale/zh-cn';

import styles from './index.less';
class MList extends Component {
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

    this.toSearchKeywords = "";//将要搜索的Keywords 

    this.state = {
      searchKeywords: this.toSearchKeywords,// 搜索关键词 
      dataSource: dataSource.cloneWithRows(this.initData),
      hasMore: false,//有没有更多数据
      refreshing: false,//列表是否在下拉刷新
      isLoading: false,//列表是否在上拉加载
      signState: '0',//签字状态
    }
  }
  componentDidMount() {//首次加载，执行手动刷新方法  
    let signState = localStorage.getItem('signState') || '0';//保持选项状态
    this.setState({
      signState
    }, ()=>{
      this.manuallyRefreshFun({ searchKeywords: this.toSearchKeywords })
    })
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
    const { dataSource, signState } = this.state
    this.currentPage = 1;
    var taskSource = "";
    const body = {
      searchStatus: this.type,
      limit: this.pageSize,
      page: this.currentPage,
      title: this.toSearchKeywords,// 指导资料标题
      taskSource,// 分类ID
      signState
    }
    const filterLocalDB = {
      toSearchKeywords: this.toSearchKeywords,// 指导资料标题 
    }
    // console.log(body) 
    myFetch('getFileListForWechatByUserId', body, ).then(({ success, data, message, totalNumber }) => {
      // console.log(data)
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
    const { isLoading, hasMore, dataSource, signState } = this.state
    if (isLoading || !hasMore) {
      return;
    } else {
      this.setState({ isLoading: true, message: "正在加载..." });
      this.currentPage++;
      var taskSource = ""
      const body = {
        searchStatus: this.type,
        limit: this.pageSize,
        page: this.currentPage,
        title: this.toSearchKeywords,// 项目名
        taskSource,// 分类ID
        signState
      }
      myFetch('getFileListForWechatByUserId', body, ).then(({ success, data, message, totalNumber }) => {
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
  render() {
    const {
      searchKeywords,
      dataSource,
      message,
      refreshing } = this.state
    const {
      di
    } = this.props
    const renderRow = (rowData, sectionID, rowID) => {
      if (rowData.errMsg) {
        return <div key={rowID} className="lny-list-footer">{rowData.errMsg}</div>
      } else {
        return <DatumItem key={rowID} type={this.type} rowData={rowData} sectionID={sectionID} rowID={rowID} {...this.props} />
      }
    }
    return (
      <div className={styles["lny-List"]}>
        <SearchBar value={searchKeywords} placeholder="搜索关键词" onChange={this.onSearchChange} />

        {/* <WingBlank size="small"> */}
        <SegmentedControl selectedIndex={Number(this.state.signState)} onValueChange={() => {
          let { signState } = this.state;
          let _signState = signState === '0' ? '1' : '0'
          this.setState({
            signState: _signState
          }, () => {
            localStorage.setItem('signState', _signState)
            this.manuallyRefreshFun({ searchKeywords: this.toSearchKeywords })
          })
          
        }} style={{ height: '30px', background: 'white', padding: '10px' }} values={['未签字', '已签字']} />
        <WhiteSpace />
        {/* </WingBlank> */}

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

      </div>
    );
  }
}
class DatumItem extends Component {

  render() {
    const { rowData, rowID } = this.props;
    const {
      title,
      content,
      supCreateTime,
      supCreateUserName,
      fileId
    } = rowData;

    return (
      <div onClick={() => {
        console.log('去详情', fileId);
        this.props.history.push(`/Detail/${fileId}`)
      }}>
        <WingBlank size="sm">
          <WhiteSpace size="sm" />
          <div className={styles["lny-List-item"]} style={{ borderColor: (rowID % 2 === 0) ? "#FF3F01" : "#FFAF02" }}>
            <div className={styles["lny-List-item-title"]}>
              【{title}】
            </div>
            <div className={styles["lny-List-item-introduce"]}>
              <div style={{ color: "#333", paddingBottom: "7px" }}>{content}</div>
            </div>
            <div className={styles["lny-List-item-tool"]}>
              <div style={{
                display: 'flex',
                justifyContent: 'space-between',
                width: '100%'
              }}>
                <div className={styles["lny-List-item-span1"]}>日期：{moment(supCreateTime).format('YYYY-MM-DD HH:mm:ss')}</div>
                <div className={styles["lny-List-item-span2"]}>发布者：{supCreateUserName}</div>
              </div>
            </div>
          </div>
        </WingBlank>
      </div>
    )
  }
}
export default MList;