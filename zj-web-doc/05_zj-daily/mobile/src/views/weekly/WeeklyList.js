import React, { Component } from 'react';
import { NoticeBar, Toast, SearchBar, List, WingBlank, WhiteSpace, Modal, Flex, ListView, Checkbox, Button, PullToRefresh } from 'antd-mobile';
import './WeeklyList.css';
import { push } from 'connected-react-router';
import moment from 'moment'
const alert = Modal.alert;
class WeeklyList extends Component {
    constructor(props) {
        super(props);
        const dataSource = new ListView.DataSource({
            rowHasChanged: (row1, row2) => row1 !== row2,
        });
        const { match: { params: { sendMsgId, startDate, endDate, listType } } } = this.props
        this.sendMsgId = sendMsgId;
        this.startDate = startDate;

        this.endDate = (!endDate || endDate === "0") ? moment().valueOf() : endDate;
        this.parameter = listType === "listByOne" ? "1" : "2";
        this.initData = [];
        this.modal = {}
        this.toSearchKeywords = "";//将要搜索的Keywords
        this.state = {
            refreshing: false,
            searchKeywords: this.toSearchKeywords,// 搜索关键词
            dataSource: dataSource.cloneWithRows(this.initData),
            topicContent: "",
            totalMembers: [],
        };
        this.totalMembers = [];
        this.checkedMembers = [];
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
    // 搜索组件改变回调
    onSearchChange = (keywords) => {
        if (this.toSearchKeywords !== keywords) {
            this.toSearchKeywords = keywords;
            this.manuallyRefreshFun({ searchKeywords: this.toSearchKeywords })
        }
    };
    // 第一次渲染后调用，只在客户端
    componentDidMount() {
        this.manuallyRefreshFun({ searchKeywords: this.toSearchKeywords })
    }
    componentWillUnmount() {
        for (var key in this.modal) {
            if (this.modal.hasOwnProperty(key)) {
                this.modal[key].close()
            }
        }
    }
    onRefresh = () => {
        const { sendMsgId, startDate, endDate, parameter } = this
        const { myFetch, actions: { logout }, dispatch, routerInfo: { routeData, curKey } } = this.props
        const { pathName } = routeData[curKey]
        const body = {
            sendMsgId,
            startDate,
            endDate,
            parameter,
            projectName: this.toSearchKeywords,
        }
        myFetch('getInvestCompanyListByMsg', body).then(({ success, data, message, code }) => {
            if (success) {
                this.initData = data;
                if (this.initData.length === 0) {
                    this.initData = [{ errMsg: "暂无数据" }]
                }
            } else if (code === "3003" || code === "3004") {
                dispatch(logout(pathName))
                return
            } else {
                this.initData = [{ errMsg: message }]
            }
            if (this.isSearch) {
                this.isSearch = false;
                this.setState({
                    dataSource: this.state.dataSource.cloneWithRows(this.initData),
                    refreshing: false,
                });
            } else {
                this.setState({
                    topicContent: this.initData[0].topicContent || "",
                    dataSource: this.state.dataSource.cloneWithRows(this.initData),
                    refreshing: false,
                });
            }
        })
    };
    onOpen = () => {
        const { myFetch, actions: { logout }, dispatch, routerInfo: { routeData, curKey } } = this.props
        const { pathName } = routeData[curKey]
        this.checkedMembers = [];
        if (this.totalMembers && this.totalMembers.length > 0) {
            this.popupShow(this.totalMembers)
        } else {
            this.totalMembers = [];
            Toast.loading('Loading...', 0);
            myFetch('getMessageMemberList').then(({ success, data, message, code }) => {
                Toast.hide()
                if (success) {
                    for (let i = 0; i < data.length; i++) {
                        this.totalMembers.push(data[i]);
                    }
                    this.popupShow(this.totalMembers)
                } else if (code === "3003" || code === "3004") {
                    dispatch(logout(pathName))
                } else {
                    Toast.offline(message, 2)
                }
            })
        }
    }
    popupHide = () => {
        this.setState({ visible: false })
    }
    popupShow = (totalMembers) => {
        this.setState({ visible: true, totalMembers })
    }
    onChangeM = (checked, oaUserId) => {
        var _list = this.checkedMembers;
        if (checked) {
            _list.push({ oaUserId })
        } else {
            _list.map((v, i) => {
                if (oaUserId === v.oaUserId) {
                    _list.splice(i, 1);
                }
                return v
            })
        }
    }
    onSend = () => {
        const { myFetch, actions: { logout }, dispatch, routerInfo: { routeData, curKey } } = this.props
        const { pathName } = routeData[curKey]
        const onPress = () => {
            const { sendMsgId, startDate, endDate, checkedMembers, parameter } = this;
            const body = {
                sendMsgId,
                startDate,
                endDate,
                parameter,
                tzMessageMemberList: checkedMembers
            }
            Toast.loading('Loading...', 0);
            myFetch('sendDailyMsg', body).then(({ success, data, message, code }) => {
                Toast.hide()
                if (success) {
                    this.popupHide();
                } else if (code === "3003" || code === "3004") {
                    dispatch(logout(pathName))
                } else {
                    Toast.offline(message, 2)
                }
            })
        }
        if (this.checkedMembers.length > 0) {
            this.modal.alert = alert('提醒', '确认是否发报?', [
                { text: '取消' },
                { text: '确认', onPress, style: { fontWeight: 'bold' } },
            ])
        } else {
            this.modal.alert = alert('提醒', '至少选择一个人员')
        }
    }
    render() {
        const me = this;
        const { searchKeywords, topicContent, totalMembers, visible, refreshing } = me.state
        const { dispatch, routerInfo: { routeData, curKey } } = this.props
        const { moduleName } = routeData[curKey]
        const row = (rowData, sectionID, rowID) => {
            if (rowData.errMsg) {
                return <div style={{ textAlign: "center", lineHeight: "50px", color: "#999" }}>{rowData.errMsg}</div>
            } else {
                const totalMark = isNaN(parseFloat(rowData.totalMark)) ? 0 : parseFloat(rowData.totalMark)
                const color = (rowID % 2 === 0) ? "#FF3F01" : "#FFAF02"
                return (
                    <WingBlank key={rowID} size="sm">
                        <WhiteSpace size="sm" />
                        <List.Item
                            className="myListItem"
                            style={{ borderColor: color }}
                            arrow={"horizontal"}
                            onClick={() => {
                                dispatch(push(`${moduleName}weeklyDetail/${rowData.companyId}/${this.startDate}/${this.endDate}`))
                            }}
                        >
                            <div>
                                <div style={{ whiteSpace: "normal" }}>
                                    {rowData.changes === "1" ? <span style={{ verticalAlign: "top", marginRight: "5px", borderRadius: "4px", display: "inline-block", width: "8px", height: "8px", backgroundColor: "red" }}></span> : ""}
                                    {rowData.projectName}
                                </div>
                                <Jdt totalMark={totalMark} color={color} />
                            </div>
                        </List.Item>
                    </WingBlank>
                );
            }
        };
        return (
            <div className="page WeeklyList">
                {topicContent ? <NoticeBar icon={null} marqueeProps={{ loop: true, leading: 500, trailing: 800, fps: 60, style: { padding: '0 32px', fontSize: "17px" } }}>{topicContent}</NoticeBar> : ""}
                <SearchBar
                    value={searchKeywords}
                    placeholder="搜索项目名称"
                    onClear={() => { this.onSearchChange("") }}
                    onCancel={() => { this.onSearchChange("") }}
                    onChange={this.onSearchChange}
                />
                <ListView
                    className="flex"
                    dataSource={this.state.dataSource}
                    renderRow={row}
                    scrollerOptions={{ scrollbars: true }}
                    pullToRefresh={
                        <PullToRefresh
                            distanceToRefresh={window.devicePixelRatio * 10}
                            refreshing={refreshing}
                            onRefresh={this.onRefresh}
                            indicator={{ deactivate: '下拉刷新' }}
                        />
                    }
                />
                {this.parameter === "1" ?
                    <div style={{ padding: "5px", backgroundColor: "white" }}>
                        <Button type="primary" onClick={this.onOpen}>发报</Button>
                    </div>
                    : ""
                }
                <Modal
                    popup
                    visible={visible}
                    animationType="slide-up"
                >
                    <List
                        className={"popupList"}
                        renderHeader={() => "选择人员"}>
                        {totalMembers.map((v, index) => (
                            <Checkbox.CheckboxItem key={v.oaUserId} onChange={(e) => this.onChangeM(e.target.checked, v.oaUserId)}>{v.oaUserName}</Checkbox.CheckboxItem>
                        ))}
                    </List>
                    <Flex style={{ padding: "5px", backgroundColor: "white" }}>
                        <Flex.Item><Button type="ghost" onClick={this.popupHide}>取消</Button></Flex.Item>
                        <Flex.Item><Button type="primary" onClick={this.onSend}>确定</Button></Flex.Item>
                    </Flex>
                </Modal>
            </div>
        );
    }
}
class Jdt extends Component {
    constructor(props) {
        super(props);
        this.state = {
            totalMark: 0,
            opacity: .1,
            init: false,
        }
    }
    componentDidMount() {
        const { totalMark } = this.props;
        setTimeout(() => {
            this.setState({ totalMark, opacity: .8, init: true })
        }, 0)
    }
    componentWillReceiveProps(newProps) {
        this.setState({ totalMark: 0, opacity: .1, init: false })
    }
    componentDidUpdate() {
        const { totalMark } = this.props;
        const { init } = this.state;
        if (!init) {
            setTimeout(() => {
                this.setState({ totalMark, opacity: .8, init: true })
            }, 10)
        }
    }
    render() {
        const { color } = this.props;
        const { totalMark, opacity } = this.state;
        return (
            <div style={{ position: "relative" }}>
                <div className="jdtBg" style={{ width: Math.max(totalMark, 3) + "%", opacity, backgroundColor: color }}></div>
                <div className="jdt">{totalMark}</div>
            </div>
        )
    }
}
export default WeeklyList;