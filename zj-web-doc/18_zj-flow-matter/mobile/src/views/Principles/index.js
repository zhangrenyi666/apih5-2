import React, { Component } from 'react';
import s from './style/index.less';
import { Toast, PullToRefresh } from 'antd-mobile';
import { Spin } from 'antd'
import { push } from 'react-router-redux';
import ReactDOM from 'react-dom';
import { createForm } from 'rc-form';
class Index extends Component {
    constructor(props) {
        super(props);
        this.state = {
            data: [],
            page: 1,
            limit: 10,
            height: document.documentElement.clientHeight,
            refreshing: false,
            loading: false,
            content: "",
        }
    }
    componentWillMount() {
        this.loadTherr();
    }
    componentDidMount() {
        const hei = this.state.height - ReactDOM.findDOMNode(this.contentNode).offsetTop;
        this.setState({
            height: hei,
        })
        document.getElementsByTagName('title')[0].innerHTML = "参会列表";
    }
    componentWillUnmount(){
        document.getElementsByTagName('title')[0].innerHTML = "";
    }
    loadTherr() {
        const { myFetch } = this.props
        let body = {
            page: this.state.page,
            limit: this.state.limit,
        }
        myFetch("getZjMeetingListForWechat", body).then(({ success, message, data }) => {
            if (success) {
                this.setState({
                    data: data,
                    loading: false,
                    content: "暂无数据..."
                })
            } else {
                Toast.fail(message)
            }
        })
    }
    render() {
        return (
            <div>
                <Spin spinning={this.state.loading}>
                    <PullToRefresh
                        ref={el => this.contentNode = el}
                        style={{
                            height: this.state.height,
                            overflow: 'scroll',
                            paddingBottom: '5%'
                        }}
                        direction='up'
                        refreshing={this.state.refreshing}
                        indicator={{ deactivate: '上拉刷新' }}
                        distanceToRefresh={window.devicePixelRatio * 25}
                        onRefresh={() => {
                            this.setState({
                                refreshing: true,
                                limit: this.state.limit + 10,
                                page: this.state.page
                            }, () => {
                                const { myFetch } = this.props
                                let body = {
                                    page: this.state.page,
                                    limit: this.state.limit,
                                }
                                myFetch("getZjMeetingListForWechat", body).then(({ success, message, data }) => {
                                    if (success) {
                                        this.setState({
                                            data: data
                                        }, () => {
                                            this.setState({
                                                refreshing: false
                                            });
                                        })
                                    } else {
                                        Toast.fail(message)
                                    }
                                })
                            });
                        }}
                    >
                        <div>
                            {
                                this.state.data != "" ? (this.state.data.map((item, index) => {
                                    if(index%2 == 0 ){
                                        return (
                                            <div className={s.center} style={{borderLeft:'3px solid red'}} key={index} onClick={() => {
                                                if(item.personnelState == 0){
                                                    this.props.dispatch(push(`/zjMeetingRoomMobile/largeSignIns/${item.reservationsId}/${item.loginMobile}/0`))
                                                }else if(item.personnelState == 1){
                                                    this.props.dispatch(push(`/zjMeetingRoomMobile/signInss/${item.reservationsId}/${item.loginMobile}/0`))
                                                }else if(item.personnelState == 2){
                                                    this.props.dispatch(push(`/zjMeetingRoomMobile/signInDetail/${item.reservationsId}/${item.loginMobile}/0`))
                                                }else if(item.personnelState == 3){
                                                    this.props.dispatch(push(`/zjMeetingRoomMobile/signInsDetail/${item.reservationsId}/${item.loginMobile}/0`))
                                                }                                        
                                            }}>
                                                <div className={s.centert}>
                                                    <div className={s.centertl}>【{item.meetingRoomTypeName}】</div>
                                                    <div className={s.centertr}>{item.personnelStateName}</div>
                                                </div>
                                                <div className={s.centerc}>
                                                    <div className={s.centertl}>会议地点: {item.meetingRoomName}</div>
                                                </div>
                                                <div className={s.centerb}>
                                                    会议时间: {item.meetingTime}
                                                </div>
                                            </div>
                                        )
                                    }else{
                                        return (
                                            <div className={s.center} style={{borderLeft:'3px solid yellow'}} key={index} onClick={() => {
                                                if(item.personnelState == 0){
                                                    this.props.dispatch(push(`/zjMeetingRoomMobile/largeSignIns/${item.reservationsId}/${item.loginMobile}/0`))
                                                }else if(item.personnelState == 1){
                                                    this.props.dispatch(push(`/zjMeetingRoomMobile/signInss/${item.reservationsId}/${item.loginMobile}/0`))
                                                }else if(item.personnelState == 2){
                                                    this.props.dispatch(push(`/zjMeetingRoomMobile/signInDetail/${item.reservationsId}/${item.loginMobile}/0`))
                                                }else if(item.personnelState == 3){
                                                    this.props.dispatch(push(`/zjMeetingRoomMobile/signInsDetail/${item.reservationsId}/${item.loginMobile}/0`))
                                                }  
                                            }}>
                                                <div className={s.centert}>
                                                    <div className={s.centertl}>【{item.meetingRoomTypeName}】</div>
                                                    <div className={s.centertr}>{item.personnelStateName}</div>
                                                </div>
                                                <div className={s.centerc}>
                                                    <div className={s.centertl}>会议地点: {item.meetingRoomName}</div>
                                                </div>
                                                <div className={s.centerb}>
                                                    会议时间: {item.meetingTime}
                                                </div>
                                            </div>
                                        )
                                    }
                                })) : <div className={s.noCentent}>{this.state.content}</div>
                            }
                        </div>
                    </PullToRefresh>
                </Spin>
            </div>
        )
    }
}
const Indexs = createForm()(Index);
export default Indexs;