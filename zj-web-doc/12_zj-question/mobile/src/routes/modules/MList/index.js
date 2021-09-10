import React, { Component } from "react";
import { Select, Modal } from 'antd';
import {
    SearchBar,
    ListView,
    PullToRefresh,
    Toast,
    Flex,
    PickerView,
    List
    // Drawer
} from "antd-mobile";
import "moment/locale/zh-cn";
import styles from "./index.less";
const Option = Select.Option;
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
        this.toSearctType = ""; //将要搜索的Keywords

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
            searchKey: this.props.searchKey || "searchText",
            selectOptions: [],
            text: '请选择'
        };
    }
    componentDidMount() {
        //首次加载，执行手动刷新方法
        console.assert(
            this.state.fetchConfig.apiName,
            "fetchConfig.apiName为必传"
        );
        console.assert(this.state.myFetch, "myFetch为必传");
        console.assert(this.state.Item, "Item为必传");
        this.manuallyRefreshFun({ searchKeywords: this.toSearchKeywords });
        this.getSelectOption();
    }
    help = () => {
        console.log(`
      <MyList 
          myFetch={this.props.myFetch}
          searchKey="voteTitle"
          fetchConfig={{
              apiName:'getVoteListByVoterMobile',
              otherParams:{}
          }}
          Item={(props)=>{
              //jsx
              console.log(props)
              return <div>123</div>
          }}
      />
    `);
    };

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
    //获取下拉类型数据
    getSelectOption = () => {
        const { myFetch } = this.state;
        myFetch("getZjXmQuestionTitleSelectAllList", {}).then(
            ({ success, data, message }) => {
                if (success) {
                    this.setState({
                        selectOptions: data
                    });
                    // this.setState({
                    //     selectOptions: data.map((item) => {
                    //         item.lable = item.title;
                    //         item.value = item.titleId;
                    //         return item;
                    //     })
                    // });
                } else {
                    Toast.fail(message);
                }
            }
        );
    };
    // 列表数据查询
    onRefresh = () => {
        const {
            dataSource,
            fetchConfig: { apiName, otherParams = {} },
            myFetch,
            searchKey
        } = this.state;
        this.currentPage = 1;
        const body = {
            limit: this.pageSize, //每页显示条数
            page: this.currentPage, //当前页
            [searchKey]: this.toSearchKeywords, // 指导资料标题
            ...otherParams,
            questionTitleId: this.toSearctType || otherParams.type
        };
        myFetch(apiName, body).then(
            ({ success, data, message, totalNumber }) => {
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
            }
        );
    };
    onEndReached = event => {
        const {
            isLoading,
            hasMore,
            dataSource,
            fetchConfig: { apiName, otherParams = {} },
            myFetch,
            searchKey
        } = this.state;
        if (isLoading || !hasMore) {
            return;
        } else {
            this.setState({
                isLoading: true,
                message: <center>正在加载...</center>
            });
            this.currentPage++;
            const body = {
                limit: this.pageSize, //每页显示条数
                page: this.currentPage, //当前页
                [searchKey]: this.toSearchKeywords,
                ...otherParams,
                questionTitleId: this.toSearctType || otherParams.type
            };
            myFetch(apiName, body).then(
                ({ success, data, message, totalNumber }) => {
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
                }
            );
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
        const {
            searchKeywords,
            dataSource,
            message,
            refreshing,
            Item,
            selectOptions,
            text
        } = this.state;

        const renderRow = (rowData, sectionID, rowID) => {
            if (rowData.errMsg) {
                return (
                    <div key={rowID} className="lny-list-footer">
                        {rowData.errMsg}
                    </div>
                );
            } else {
                return (
                    <Item
                        key={rowID}
                        rowData={rowData}
                        sectionID={sectionID}
                        rowID={rowID}
                        {...this.props}
                    />
                );
            }
        };
        return (
            <div className={styles["lny-List"]}>
                <div style={{ textAlign: 'center', padding: '5px 0', fontWeight: 'bold' }}>{text}</div>
                <div>
                    <Flex style={{ background: "#efeff4" }}>
                        <Flex.Item
                            style={{ flex: "4", textAlign: "center" }}
                        >
                            <div style={{ paddingLeft: "8px" }}>
                                <Select
                                    style={{ width: "100%" }}
                                    placeholder="请选择"
                                    onChange={(val, text) => {
                                        this.setState({
                                            text: text.props.children
                                        });
                                        this.toSearctType = val;
                                        this.manuallyRefreshFun({
                                            searchKeywords: this
                                                .toSearchKeywords
                                        });
                                    }}
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
                                {/* <Picker
                                    data={[selectOptions]}
                                    cascade={false}
                                    onOk={(val) => {
                                        console.log(text);
                                        return;
                                        this.setState({
                                            text:val[0]
                                        });
                                        this.toSearctType = val[0];
                                        this.manuallyRefreshFun({
                                            searchKeywords: this
                                                .toSearchKeywords
                                        });
                                    }}
                                >
                                    <List.Item arrow="horizontal" ></List.Item>
                                </Picker> */}
                            </div>
                        </Flex.Item>

                        <Flex.Item style={{ flex: "6" }}>
                            <SearchBar
                                value={searchKeywords}
                                placeholder="搜索部门名称"
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
                            indicator={{ deactivate: "下拉刷新" }}
                        />
                    }
                    onEndReached={this.onEndReached}
                    renderFooter={() => {
                        return (
                            <div className={"lny-list-footer"}>{message}</div>
                        );
                    }}
                />
            </div>
        );
    }
}
export default MList;
