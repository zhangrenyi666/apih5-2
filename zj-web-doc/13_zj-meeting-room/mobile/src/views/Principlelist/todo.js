import React, { Component } from "react";
import { Divider } from "antd";
import { push } from "react-router-redux";
import MyList from "../modules/MList";
import s from "./style.less";
class Index extends Component {
    render() {
        const { dispatch, myPublic: { appInfo:{ mainModule } } } = this.props;
        return (
            <div className={s.root}>
                <div
                    style={{
                        height: window.innerHeight - 90
                    }}
                >
                    <MyList
                        myFetch={this.props.myFetch} //ajax方法必须返回一个promise
                        searchKey="meetingRoomTitle" //搜索时的key
                        fetchConfig={{
                            apiName: 'getZjMeetingListForWechat', //后台api
                            otherParams:{
                                tabFlag:'1'
                            }
                        }}
                        Item={props => {
                            //列表模板 props里有所有数据
                            const { rowData, rowID } = props;
                            const item = rowData;
                            const index = rowID;
                            return (
                                <div
                                    className={s.center}
                                    style={{
                                        borderLeft: `3px solid ${
                                            index % 2 === 0 ? "#ff4000" : "#ff9900"
                                            }`
                                    }}
                                    key={index}
                                    onClick={() => {
                                        if(item.personnelState === '0'){
                                            dispatch(push(`${mainModule}signInDetail/${item.reservationsId}/${item.loginMobile}/1/0`))
                                        }else if(item.personnelState === '1'){
                                            dispatch(push(`${mainModule}signInList/${item.reservationsId}/${item.loginMobile}/1/0`))
                                        }else if(item.personnelState === '2'){
                                            dispatch(push(`${mainModule}signInDetail/${item.reservationsId}/${item.loginMobile}/1/0`))
                                        }else if(item.personnelState === '3'){
                                            dispatch(push(`${mainModule}signInDetail/${item.reservationsId}/${item.loginMobile}/1/0`))
                                        }
                                    }}
                                >
                                    <div className={s.top}>
                                        <div className={s.topl}>{item.meetingRoomTitle}</div>
										 <div className={s.topr} style={{color:'#1890ff'}}>{item.personnelState === '1' ? '未签到' : '已签到'}</div>
                                    </div>
                                    <Divider style={{ margin: "8px 0px",backgroundColor:'#1890ff' }} />
                                    <div style={{ paddingLeft: '3%' }}>
                                        会议地点:{item.meetingRoomName}
                                    </div>
                                    <Divider style={{ margin: "8px 0px" }} />
                                    <div className={s.top}>
                                        <div className={s.topl}>手机号:{item.loginMobile}</div>
										 <div className={s.topr}>{item.meetingTime}</div>
                                    </div>
                                </div>
                            );
                        }}
                    />
                </div>
            </div>
        );
    }
}
export default Index;
