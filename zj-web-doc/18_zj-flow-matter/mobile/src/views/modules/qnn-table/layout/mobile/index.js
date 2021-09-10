import React, { Component } from "react";
import { ListView, PullToRefresh, Toast, List } from "antd-mobile"; // SearchBar,
import "moment/locale/zh-cn";
import s from "./index.less";
import ListItemMobileByFlow from "../listItemModel/flow/flow";
import { push } from "connected-react-router";
const { Item } = List;
class MList extends Component {
  constructor(props) {
    super(props);
    const dataSource = new ListView.DataSource({
      rowHasChanged: (row1, row2) => row1 !== row2
    });
    this.pageSize = 10;
    this.currentPage = 1;
    this.initData = []; //初始数据_列表

    this.toSearchKeywords = ""; //将要搜索的Keywords

    this.state = {
      searchKeywords: this.toSearchKeywords, // 搜索关键词
      dataSource: dataSource.cloneWithRows(this.initData),
      hasMore: false, //有没有更多数据
      refreshing: false, //列表是否在下拉刷新
      isLoading: false, //列表是否在上拉加载

      //从父级传入的
      myFetch: this.props.myFetch,
      fetchConfig: this.props.fetchConfig || {},
      Item: this.props.Item, //列表组件 props里带着数据
      searchKey: this.props.searchKey || "searchText"
    };
  }
  componentDidMount() {
    //首次加载，执行手动刷新方法
    console.assert(this.state.fetchConfig.apiName, "fetchConfig.apiName为必传");
    console.assert(this.state.myFetch, "myFetch为必传");
    console.assert(this.state.Item, "Item为必传");
    this.manuallyRefreshFun({ searchKeywords: this.toSearchKeywords });
  }

  manuallyRefreshFun = otherObj => {
    //手动刷新方法
    if (!otherObj) {
      otherObj = {};
    }
    this.manuallyRefresh = true;
    this.setState({ ...otherObj, refreshing: true });
  };
  componentWillUpdate(nextProps, nextState) {
    //判断是否为手动刷新
    if (nextState.refreshing && this.manuallyRefresh) {
      this.manuallyRefresh = false;
      this.onRefresh();
    }
  }
  // 列表数据查询
  onRefresh = () => {
    let {
      dataSource,
      fetchConfig: { apiName, params = {}, otherParams },
      myFetch,
      searchKey
    } = this.state;

    let _params = {};
    const urlParams = this.props.match.params;
    for (const key in params) {
      _params[key] = urlParams[params[key]];
    }

    if (typeof otherParams === "function") {
      otherParams = otherParams({
        ...this.props,
        fetch: this.fetch
      });
    }

    if (typeof apiName === "function") {
      apiName = apiName({
        ...this.props,
        fetch: this.fetch
      });
    }

    this.currentPage = 1;
    const body = {
      limit: this.pageSize,
      page: this.currentPage,
      [searchKey]: this.toSearchKeywords, // 指导资料标题
      ...otherParams,
      ..._params
    };
    myFetch(apiName, body).then(({ success, data, message, totalNumber }) => {
      if (success) {
        this.initData = data || [];
        this.setState({
          dataSource: dataSource.cloneWithRows(this.initData),
          refreshing: false,
          hasMore: this.initData.length < totalNumber,
          message: success ? (
            data.length > 0 ? (
              this.initData.length < totalNumber ? (
                <center>上拉查看更多</center>
              ) : (
                <center>没有更多了</center>
              )
            ) : (
              <center>暂无数据</center>
            )
          ) : (
            message
          )
        });
      } else {
        Toast.fail(message);
      }
    });
  };
  onEndReached = event => {
    let {
      isLoading,
      hasMore,
      dataSource,
      fetchConfig: { apiName, otherParams = {}, params },
      myFetch,
      searchKey
    } = this.state;
    if (isLoading || !hasMore) {
      return;
    } else {
      let _params = {};
      const urlParams = this.props.match.params;
      for (const key in params) {
        _params[key] = urlParams[params[key]];
      }

      if (typeof otherParams === "function") {
        otherParams = otherParams({
          ...this.props,
          fetch: this.fetch
        });
      }

      if (typeof apiName === "function") {
        apiName = apiName({
          ...this.props,
          fetch: this.fetch
        });
      }

      this.setState({ isLoading: true, message: "正在加载..." });
      this.currentPage++;
      const body = {
        limit: this.pageSize,
        page: this.currentPage,
        [searchKey]: this.toSearchKeywords,
        ...otherParams,
        ...params
      };
      myFetch(apiName, body).then(({ success, data, message, totalNumber }) => {
        if (success) {
          this.initData = this.initData.concat(data);
          this.setState({
            dataSource: dataSource.cloneWithRows(this.initData),
            hasMore: this.initData.length < totalNumber,
            message:
              this.initData.length < totalNumber ? (
                <center>上拉查看更多</center>
              ) : (
                <center>没有更多了</center>
              ),
            isLoading: false
          });
        } else {
          Toast.fail(message);
        }
      });
    }
  };
  // 搜索组件改变回调
  onSearchChange = keywords => {
    if (this.toSearchKeywords !== keywords) {
      this.toSearchKeywords = keywords;
      this.manuallyRefreshFun({ searchKeywords: this.toSearchKeywords });
    }
  };
  render() {
    const { dataSource, message, refreshing } = this.state; //searchKeywords, 
    const DiyItem = this.state.Item; 
    const {
      myPublic: {
        appInfo: { mainModule }
      },
      dispatch,
      status
    } = this.props;

    const renderRow = (rowData, sectionID, rowID) => {
      const { title, workId, flowId, formLink } = rowData;

      if (rowData.errMsg) {
        return (
          <div key={rowID} className="lny-list-footer">
            {rowData.errMsg}
          </div>
        );
      } else {
        if (DiyItem === "flow") {
          return (
            <ListItemMobileByFlow
              key={rowID}
              rowData={rowData}
              sectionID={sectionID}
              rowID={rowID}
              {...this.props}
            />
          );
        }
        return (
          <Item
            key={rowID}
            multipleLine
            wrap
            className={s.item}
            onClick={() => {
              let _title = window.encodeURI(title);
              let _u = `${mainModule}${
                formLink[flowId]
              }/${flowId}/${status}/${workId}/${_title}`;
              dispatch(push(_u));
            }}
          >
            <DiyItem
              key={rowID}
              rowData={rowData}
              sectionID={sectionID}
              rowID={rowID}
              {...this.props}
            />
          </Item>
        );
      }
    };
    return (
      <div className={s["lny-List"]}>
        {/* <SearchBar
          value={searchKeywords}
          placeholder="搜索关键词"
          onChange={this.onSearchChange}
        /> */}

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
              indicator={{ deactivate: "下拉刷新" }}
            />
          }
          onEndReached={this.onEndReached}
          renderFooter={() => {
            return <div className={"lny-list-footer"}>{message}</div>;
          }}
        />
      </div>
    );
  }
}
export default MList;
