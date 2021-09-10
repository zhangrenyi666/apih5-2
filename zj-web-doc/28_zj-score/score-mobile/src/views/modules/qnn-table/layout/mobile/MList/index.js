import React,{ Component } from "react";
import { SearchBar,ListView,PullToRefresh,Toast,NavBar,Flex,ActivityIndicator } from "antd-mobile";
import { Icon,Drawer } from 'antd';
import "moment/locale/zh-cn";
import styles from "./index.less";
// import { goBack } from 'connected-react-router'; //,push
import QnnForm from "../../../qnn-form";

class MList extends Component {

    static getDerivedStateFromProps(props,state) {
        let obj = {
            ...state,
            ...props
        };

        return obj;
    }

    constructor(props) {
        super(props);
        const dataSource = new ListView.DataSource({
            rowHasChanged: (row1,row2) => row1 !== row2
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
            searchKey: this.props.searchKey || "searchText",

            open: false, //抽屉状态

            //过滤的参数
            mobileSearchParams: {}
        };
    }
    componentDidMount() {
        //首次加载，执行手动刷新方法
        console.assert(
            this.state.fetchConfig.apiName,
            "fetchConfig.apiName为必传"
        );
        console.assert(this.state.myFetch,"myFetch为必传");
        console.assert(this.state.Item,"Item为必传");
        this.manuallyRefreshFun({ searchKeywords: this.toSearchKeywords });
    }

    manuallyRefreshFun = otherObj => {
        //手动刷新方法
        if (!otherObj) {
            otherObj = {};
        }
        this.manuallyRefresh = true;
        this.setState({ ...otherObj,refreshing: true });
    };

    // componentWillUpdate(nextProps,nextState) {
    //     //判断是否为手动刷新
    //     if (nextState.refreshing && this.manuallyRefresh) {
    //         this.manuallyRefresh = false;
    //         this.onRefresh();
    //     }
    // }

    componentDidUpdate(nextProps,nextState) {
        if (this.state.refreshing && this.manuallyRefresh) {
            this.manuallyRefresh = false;
            this.onRefresh();
        }
    }

    // 列表数据查询
    onRefresh = () => {
        const {
            dataSource,
            fetchConfig: { apiName,otherParams = {} },
            myFetch,
            searchKey,
            mobileSearchParams = {},

        } = this.state;
        this.currentPage = 1;
        const body = {
            limit: this.pageSize, //每页显示条数
            page: this.currentPage, //当前页 
            [searchKey]: this.toSearchKeywords,
            ...otherParams,
            ...mobileSearchParams
        };
        myFetch(apiName,body).then(
            ({ success,data,message,totalNumber }) => {
                if (success) {
                    this.initData = data || [];
                    this.setState({
                        totalNumber,
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
            fetchConfig: { apiName,otherParams = {} },
            myFetch,
            searchKey,
            mobileSearchParams
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
                ...mobileSearchParams
            };
            myFetch(apiName,body).then(
                ({ success,data,message,totalNumber }) => {
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
                            isLoading: false,
                            totalNumber
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
    onClose = () => {
        this.setState({
            open: false,
        });
    };
    render() {
        const {
            searchKeywords,
            dataSource,
            message,
            refreshing,
            Item,
            totalNumber,
            mobileSearchParams = {}
        } = this.state;
        const {
            // dispatch,
            myPublic = {},
            title,
            searchFroms,
            onScroll,
            history
        } = this.props;
        let { androidApi } = myPublic;
        const _searchFroms = [...searchFroms].map(item => {
            item.hide = false;
            item.field = `search.${item.field.replace(/(search__|search.)/ig,'')}`;
            item.span = 24;
            item.formItemLayout = {
                labelCol: {
                    sm: { span: 24 },
                    xs: { span: 24 }
                },
                wrapperCol: {
                    sm: { span: 24 },
                    xs: { span: 24 }
                }
            }
            return item;
        });
        const renderRow = (rowData,sectionID,rowID) => {
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
            <div className={styles.container}>
                <div className={styles.header}>
                    <NavBar
                        mode="dark"
                        icon={<Flex><Icon type="left" /><span style={{ fontSize: 15 }}>返回</span></Flex>}
                        onLeftClick={() => {
                            if (androidApi) {
                                androidApi.closeActivity()
                            } else {
                                history.goBack();
                            }
                        }}
                        rightContent={<span style={{ fontSize: 15,color: JSON.stringify(mobileSearchParams) === "{}" ? 'white' : "orange" }} className={styles.btn} onClick={() => { this.setState({ open: true }) }}>更多<Icon type="filter" /></span>}
                    ><span style={{ fontSize: 15 }}>{title}</span></NavBar>
                    <div className={styles.searchContainer}>
                        <div className={styles.inp}>
                            <SearchBar
                                value={searchKeywords}
                                placeholder="搜索关键词"
                                onChange={this.onSearchChange}
                            />
                        </div>
                        {/* <span className={styles.btn} onClick={() => { this.setState({ open: true }) }}>更多<Icon type="filter" /></span> */}
                        {/* 检索内容 */}
                        <Drawer
                            // title="Basic Drawer"
                            width="80%"
                            placement="right"
                            closable={false}
                            onClose={this.onClose}
                            visible={this.state.open}
                            maskClosable={false}
                            bodyStyle={{ padding: 0 }}
                        >
                            <div>
                                <QnnForm
                                    form={this.props.form}
                                    fetch={this.props.fetch || this.props.myFetch}
                                    headers={this.props.headers}
                                    formConfig={_searchFroms}
                                    btns={[
                                        {
                                            label: '取消',
                                            isValidate: false,//是否验证表单 默认true
                                            onClick: this.onClose,
                                        },
                                        {
                                            label: '确定',
                                            type: 'primary',
                                            isValidate: false,//是否验证表单 默认true
                                            onClick: (obj) => {
                                                const { form } = obj.props;
                                                let searchVals = form.getFieldValue('search');
                                                let col = _searchFroms.map(item => {
                                                    item.field = item.field.replace(/(search__|search.)/ig,'')
                                                    return item;
                                                });
                                                let _data = QnnForm.sFormatData(searchVals,col,'get');
                                                this.setState({
                                                    mobileSearchParams: _data
                                                },() => {
                                                    this.manuallyRefreshFun({ searchKeywords: this.toSearchKeywords });
                                                    this.onClose();
                                                });
                                            }
                                        },
                                    ]}
                                    style={{
                                        height: "100vh",
                                        overflow: "hidden",
                                        paddingBottom: 0,
                                        background: "transparent"
                                    }}
                                />
                            </div>
                        </Drawer>
                    </div>

                    <div className={styles.info}>
                        {/* 搜索的关键词 */}
                        <span className={styles.searchKey}>
                            {this.toSearchKeywords ? <span>关键词：<a>{this.toSearchKeywords}</a></span> : "未输入关键词"}
                        </span>

                        {/* 检索条件 */}
                        <div className={styles.searchParams}></div>

                        <span className={styles.totalNumber}>共查询到：{totalNumber}条数据</span>
                    </div>
                </div>

                <div className={styles["lny-List"]}>
                    <ListView
                        className="flex"
                        pageSize={5}
                        dataSource={dataSource}
                        renderRow={renderRow}
                        onScroll={(e) => {
                            //将滚动条信息和分页信息储存下 返回时候用到
                            let scrollTop = e.target.scrollTop;
                            window.localStorage.setItem("qnnTableScrollInfoAndPageInfo",JSON.stringify({
                                scrollTop: scrollTop,
                                limit: this.pageSize, //每页显示条数
                                page: this.currentPage, //当前页 
                            }))
                            if (onScroll) {
                                onScroll(e);
                            }
                        }}
                        pullToRefresh={
                            <PullToRefresh
                                distanceToRefresh={window.devicePixelRatio * 15}
                                refreshing={refreshing}
                                onRefresh={this.onRefresh}
                                indicator={{ deactivate: "下拉刷新",release: <div style={{ display: 'flex',margin: '0 auto',justifyContent: 'center' }}><ActivityIndicator animating text="loading..." /> </div> }}
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
            </div>

        );
    }
}
export default MList;
