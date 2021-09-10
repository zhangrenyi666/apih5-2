import React, { Component } from 'react';
import { SearchBar, Toast, ListView, PullToRefresh, WingBlank, WhiteSpace } from 'antd-mobile';
import { Filter, FilterDialog } from './filter';
import styles from './GuidanceList.less';
import { push } from 'connected-react-router';
class DatumList extends Component {
  constructor(props) {
    super(props);
    const dataSource = new ListView.DataSource({
      rowHasChanged: (row1, row2) => row1 !== row2,
    });
    this.pageSize = 10;
    this.currentPage = 1;
    this.initData = [];//初始数据_列表
    this.initData_lv1 = [];//初始数据_一级筛选

    this.initData_lv2 = [];//初始数据_二级筛选
    this.toSearchKeywords = "";//将要搜索的Keywords
    this.toFilterId_lv1 = "all";//将要筛选的Id_一级筛选
    this.toFilterIdS_lv2 = [];//将要筛选的Id集合_二级筛选

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
    const { myFetch, actions: { logout }, dispatch, routerInfo: { routeData, curKey } } = this.props
    const { pathName } = routeData[curKey]
    Toast.loading('Loading...', 0);
    myFetch('getFirstLevelList').then(({ data, success, code }) => {
      Toast.hide();
      this.initData_lv1 = [
        {
          value: "all",
          text: "全部"
        }
      ];
      if (success && data) {
        for (let i = 0; i <= data.length - 1; i++) {
          this.initData_lv1.push({
            value: data[i].firstLevelId,
            text: data[i].firstLevelName
          })
        }
        let localDB = localStorage.getItem("DatumList") ? JSON.parse(localStorage.getItem("DatumList")) : {}
        for (const key in localDB) {
          if (localDB.hasOwnProperty(key)) {
            this[key] = localDB[key]
          }
        }
        this.manuallyRefreshFun({ searchKeywords: this.toSearchKeywords })
      } else if (code === "3003" || code === "3004") {
        dispatch(logout(pathName))
      }
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
    const { dataSource } = this.state
    const { myFetch, actions: { logout }, dispatch, routerInfo: { routeData, curKey } } = this.props
    const { pathName } = routeData[curKey]
    this.currentPage = 1;
    const body = {
      pageSize: this.pageSize,
      currentPage: this.currentPage,
      infoTitle: this.toSearchKeywords,// 指导资料标题
      firstLevelId: this.toFilterId_lv1 !== "all" ? this.toFilterId_lv1 : "",
      secondLevelList: this.toFilterId_lv1 !== "all" ? this.toFilterIdS_lv2 : []
    }
    const localDB = {
      toSearchKeywords: this.toSearchKeywords,// 指导资料标
      toFilterId_lv1: this.toFilterId_lv1,
      toFilterIdS_lv2: this.toFilterIdS_lv2,
      initData_lv2: this.initData_lv2
    }
    localStorage.setItem("DatumList", JSON.stringify(localDB))
    myFetch('getInformationList', body).then(({ success, data, message, totalNumber, code }) => {
      if (code === "3003" || code === "3004") {
        dispatch(logout(pathName))
      } else {
        this.initData = data || [];
        this.setState({
          filterId_lv1: this.toFilterId_lv1,
          dataSource: dataSource.cloneWithRows(this.initData),
          refreshing: false,
          hasMore: this.initData.length < totalNumber,
          message: success ? (data.length > 0 ? (this.initData.length < totalNumber ? "上拉查看更多" : "没有更多了") : "暂无数据") : message
        });
      }
    })
  }
  onEndReached = (event) => {
    const { isLoading, hasMore, dataSource } = this.state
    const { myFetch, actions: { logout }, dispatch, routerInfo: { routeData, curKey } } = this.props
    const { pathName } = routeData[curKey]
    if (isLoading || !hasMore) {
      return;
    } else {
      this.setState({ isLoading: true, message: "正在加载..." });
      this.currentPage++;
      const body = {
        pageSize: this.pageSize,
        currentPage: this.currentPage,
        infoTitle: this.toSearchKeywords,// 指导资料标题
        firstLevelId: this.toFilterId_lv1 !== "all" ? this.toFilterId_lv1 : "",
        secondLevelList: this.toFilterId_lv1 !== "all" ? this.toFilterIdS_lv2 : []
      }
      myFetch("getInformationList", body).then(({ success, data, code, message, totalNumber }) => {
        if (code === "3003" || code === "3004") {
          dispatch(logout(pathName))
        } else {
          this.initData = this.initData.concat(data)
          this.setState({
            dataSource: dataSource.cloneWithRows(this.initData),
            hasMore: this.initData.length < totalNumber,
            message: this.initData.length < totalNumber ? "上拉查看更多" : "没有更多了",
            isLoading: false
          });
        }
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
    const { myFetch, actions: { logout }, dispatch, routerInfo: { routeData, curKey } } = this.props
    const { pathName } = routeData[curKey]
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
      myFetch('getSecondLevelList', { firstLevelId: value }).then(({ success, data, code }) => {
        Toast.hide();
        if (success) {
          for (var i = 0; i <= data.length - 1; i++) {
            const { firstLevelId, secondLevelId, secondLevelName } = data[i]
            initData_lv2.push({
              firstLevelId,
              secondLevelId,
              secondLevelName,
              checked: false
            })
          }
          this.setState({ curFilterId_lv1: value, curFilterIds_lv2: initData_lv2 })
        } else if (code === "3003" || code === "3004") {
          dispatch(logout(pathName))
        }
      })
    }
  }
  // FilterDialog保存
  onFilterDialogSave = (checkedDatas) => {
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
      this.toFilterIdS_lv2 = checkedDatas;
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
        return <div key={rowID} className="lny-list-footer">{rowData.errMsg}</div>
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
              distanceToRefresh={window.devicePixelRatio * 10}
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
          radio={false}
          valueName={"secondLevelId"}
          textName={"secondLevelName"}
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
    const { rowData, rowID, dispatch, routerInfo: { routeData, curKey } } = this.props
    const { moduleName } = routeData[curKey]
    return (
      <div onClick={() => {
        
        dispatch(push(`${moduleName}information/guidanceDetail/${rowData.infoId}`))
      }}>
        <WingBlank size="sm">
          <WhiteSpace size="sm" />
          <div className={styles["lny-DatumList-item"]} style={{ borderColor: (rowID % 2 === 0) ? "#FF3F01" : "#FFAF02" }}>
            <div className={styles["lny-DatumList-item-title"]}>{rowData.infoTitle}</div>
            <div className={styles["lny-DatumList-item-introduce"]}>{rowData.infoContent.length > 20 ? rowData.infoContent.slice(0, 60) + '...' : rowData.infoContent}</div>
            <div className={styles["lny-DatumList-item-tool"]}>
              <div className={styles["lny-DatumList-item-tool-span1"]}>{rowData.firstLevel.firstLevelName + "-" + rowData.secondLevel.secondLevelName}</div>
              <div className={styles["lny-DatumList-item-tool-span2"]}>作者：{rowData.infoAuthor}</div>
            </div>
          </div>
        </WingBlank>
      </div>
    )
  }
}
export default DatumList;