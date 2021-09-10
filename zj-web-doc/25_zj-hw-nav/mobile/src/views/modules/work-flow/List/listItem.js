import React from 'react';
import { findDOMNode } from 'react-dom'
import { ListView, List, ActivityIndicator, PullToRefresh, Flex, Icon, Toast } from 'antd-mobile';
import moment from "moment";
import { push } from 'connected-react-router'
import s from './list.less'
const { Item } = List
const { Brief } = Item;
function MyBody(props) {
    return (
        <div className="am-list-body my-body" style={{ width: "100vw" }}>
            {props.children}
        </div>
    );
}
class Demo extends React.Component {
    constructor(props) {
        super(props);
        const dataSource = new ListView.DataSource({
            rowHasChanged: (row1, row2) => row1 !== row2,
        });
        let { todoListApiName = 'getTodoList', hasTodoListApiName = "getHasTodoList", match, CustomItem } = this.props;
        let { params: { status } } = match;

        if (typeof (todoListApiName) === 'function') {
            todoListApiName = todoListApiName({ match })
        }
        if (typeof (hasTodoListApiName) === 'function') {
            hasTodoListApiName = todoListApiName({ match })
        }
        this.page = 1;
        this.limit = 10;
        this.realData = [];
        this.tabBarHeight = this.props.tabBarHeight || 0
        this.state = {
            hasMore: false,
            isLoading: false,
            refreshing: false,
            dataSource: dataSource.cloneWithRows(this.realData),
            height: document.documentElement.clientHeight * 3 / 4,
            todoListApiName,//待办列表apiName
            hasTodoListApiName,//待办列表apiName
            status: status === '0' ? 'todo' : status,
            formLink: this.props.formLink,
            CustomItem: CustomItem,
            flowId: ''
        };
    }
    componentDidMount() {
        const _flowId = [];
        const { formLink = {} } = this.props;
        for (const key in formLink) {
            _flowId.push(key)
        }
        this.setState({
            flowId: _flowId.join(',')
        }, () => {
            this.hei = document.documentElement.clientHeight - findDOMNode(this.lv).parentNode.offsetTop - this.tabBarHeight;
            this.onRefresh()
        }); 
    }
    componentWillReceiveProps(props) {
        const { match: { params: { status } } } = props;
        if (this.state.status !== status && !this.state.isLoading) {
            this.setState({
                status: status
            }, () => {
                this.onRefresh()
            })
        }
    }
    onBackTop = () => {
        this.lv.scrollTo(0, 0)
    }
    onRefresh = (searchKeywords = "") => {
        this.searchKeywords = searchKeywords || ""
        const { myFetch } = this.props;
        const { todoListApiName, hasTodoListApiName, status, flowId } = this.state;
        // console.log(status)
        this.page = 1;
        this.limit = 10;
        this.realData = []
        this.setState({
            hasMore: true,
            isLoading: true,
            refreshing: true,
            dataSource: this.state.dataSource.cloneWithRows(this.realData),
        })
        const body = {
            page: this.page,
            limit: this.limit,
            keyword: this.searchKeywords,
            flowId
        }
        const apiName = status === 'todo' ? todoListApiName : hasTodoListApiName;
        // console.log(status)
        myFetch(apiName, body).then(({ success, message, data, totalNumber }) => {
            if (success) {
                this.realData = [...data]
                this.setState({
                    hasMore: totalNumber > this.realData.length,
                    dataSource: this.state.dataSource.cloneWithRows(this.realData),
                    isLoading: false,
                    refreshing: false,
                    height: this.hei,
                    searchKeywords: this.searchKeywords
                });
            } else {
                Toast.fail(message)
            }
        })
    }
    onEndReached = (event) => {
        const { myFetch } = this.props;
        const { status, todoListApiName, hasTodoListApiName, flowId } = this.state;
        // load new data
        // hasMore: from backend data, indicates whether it is the last page, here is false
        if (this.state.isLoading || !this.state.hasMore) {
            return;
        }
        this.setState({ isLoading: true });
        this.page = this.page + 1
        const body = {
            page: this.page,
            limit: this.limit,
            keyword: this.searchKeywords,
            flowId
        }
        const apiName = status === 'todo' ? todoListApiName : hasTodoListApiName;
        myFetch(apiName, body).then(({ success, message, data, totalNumber }) => {
            if (success) {
                this.realData = this.realData.concat([...data])
                this.setState({
                    hasMore: totalNumber > this.realData.length,
                    dataSource: this.state.dataSource.cloneWithRows(this.realData),
                    isLoading: false,
                });
            } else {
                Toast.fail(message)
            }

        })
    }
    render() {
        const { dispatch, myPublic: { appInfo: { mainModule } } } = this.props
        const { searchKeywords, formLink, status, CustomItem } = this.state;
        const row = (rowData, rowID) => {
            const { title, flowStatus, flowName, nodeName, workId, flowId, sendTime, sendUserName } = rowData;
            return (
                <Item
                    key={rowID}
                    multipleLine
                    wrap
                    className={s.item}
                    onClick={() => {
                        let _title = window.encodeURI(title);
                        let _u = `${mainModule}${formLink[flowId]}/${flowId}/${status}/${workId}/${_title}`;
                        dispatch(push(_u));
                    }}
                >
                    {
                        CustomItem ? CustomItem({ rowData: { ...rowData }, ...this.props }) : <div>
                            <Flex className={s.itemHeader}>
                                <Flex.Item>{flowName}</Flex.Item>
                                <div style={{ textAlign: "right" }}>{flowStatus}</div>
                            </Flex>
                            <div className={s.itemBody}>
                                <Flex wrap="wrap" align="center" alignContent="center" style={{ color: "#666", fontSize: '13px' }}>
                                    <Flex.Item>{title}</Flex.Item>
                                    <div className={s.itemBodyArr}>{
                                        <Icon style={{ color: '#bbb', width: 25, height: 25 }} type={'right'} />
                                    }</div>
                                </Flex>
                            </div>
                            <Brief style={{ borderTop: '1px solid #e8e8e8', paddingTop: '5px', }}>
                                <Flex>
                                    <Flex.Item style={{ fontSize: '13px' }}>
                                        <span style={{ marginRight: '10px' }}>{sendUserName}</span>
                                        {moment(sendTime).format("YYYY-MM-DD hh-mm-ss")}
                                    </Flex.Item>
                                    <div style={{ textAlign: "right", fontSize: '13px' }}>{nodeName}</div>
                                </Flex>
                            </Brief>
                        </div>
                    }
                </Item>
            );
        };

        return (
            <ListView
                className={s.listview}
                ref={el => this.lv = el}
                dataSource={this.state.dataSource}
                renderHeader={searchKeywords ? () => {
                    return <div>关键词：{searchKeywords}<a onClick={() => {
                        this.setState({
                            searchKeywords: ''
                        }, () => {
                            this.props.clearSearchKeywords()
                            this.onRefresh('')
                        })
                    }} style={{ marginLeft: '8px', fontSize: '13px' }}>清除</a></div>
                } : null}
                renderFooter={() => (<div style={{ padding: 30, textAlign: 'center' }}>
                    {!this.state.hasMore ? <div>没有更多了</div> : (this.state.isLoading ? <ActivityIndicator text="Loading..." /> : '上拉加载')}
                </div>)}
                renderBodyComponent={() => <MyBody />}
                renderRow={row}
                style={{
                    height: this.state.height,
                    overflow: 'auto',
                }}
                pageSize={4}
                onScroll={() => { }}
                scrollRenderAheadDistance={500}
                onEndReached={this.onEndReached}
                onEndReachedThreshold={10}
                pullToRefresh={<PullToRefresh
                    refreshing={this.state.refreshing}
                    onRefresh={() => {
                        this.onRefresh(searchKeywords)
                    }}
                />}
            />
        );
    }
}


export default Demo