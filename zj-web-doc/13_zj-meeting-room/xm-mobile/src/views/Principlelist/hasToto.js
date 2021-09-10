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
                        height: window.innerHeight - 45
                    }}
                >
                    <MyList
                        myFetch={this.props.myFetch} //ajax方法必须返回一个promise
                        searchKey="meetingRoomTitle" //搜索时的key
                        fetchConfig={{
                            apiName: 'getZjMeetingListForWechat', //后台api
                            otherParams:{
                                tabFlag:'0'
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
                                            dispatch(push(`${mainModule}largeSignList/${item.reservationsId}/${item.loginMobile}/0/0`))
                                        }else if(item.personnelState === '1'){
                                            // dispatch(push(`${mainModule}signInss/${item.reservationsId}/${item.loginMobile}/0/0`))
                                            dispatch(push(`${mainModule}signInsDetail/${item.reservationsId}/${item.loginMobile}/0/0`))
                                        }else if(item.personnelState === '2'){
                                            dispatch(push(`${mainModule}signInsDetail/${item.reservationsId}/${item.loginMobile}/0/0`))
                                        }else if(item.personnelState === '3'){
                                            dispatch(push(`${mainModule}signInsDetail/${item.reservationsId}/${item.loginMobile}/0/0`))
                                        }  
                                    }}
                                >
                                    <div className={s.top}>
                                        <div className={s.topl}>{item.meetingRoomTitle}</div>
										 <div className={s.topr} style={{color:'#1890ff'}}>{item.personnelState === '0' ? '未确认' : '已确认'}</div>
                                    </div>
                                    <Divider style={{ margin: "8px 0px",backgroundColor:'#1890ff' }} />
                                    <div style={{ paddingLeft: '3%' }}>
                                        会议地点:{item.meetingRoomName}
                                    </div>
                                    <Divider style={{ margin: "8px 0px" }} />
                                    <div style={{ paddingLeft: '3%' }}>
                                        会议时间:{item.meetingTime}
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
