import React, { Component } from "react";
import { Divider } from "antd";
import { push } from "react-router-redux";
import MyList from "../../modules/MList";
import s from "./style.less";
class TodoMobile extends Component {
    render() {
        return (
            <div className={s.root}>
                <div
                    style={{
                        height: window.innerHeight - 45
                    }}
                >
                    <MyList
                        myFetch={this.props.myFetch} //ajax方法必须返回一个promise
                        searchKey="taskMatter" //搜索时的key
                        fetchConfig={{
                            apiName: 'getZjOataskTodoList', //后台api
                            otherParams:{todoFlag:'1'}
                        }}
                        Item={props => {
                            //列表模板 props里有所有数据
                            const { rowData, rowID } = props;
                            const item = rowData;
                            const index = rowID;
                            if(item.taskStateFlag === '1'){
                                item.taskStateFlag = "待办";
                            }else if(item.taskStateFlag === '2'){
                                item.taskStateFlag = "未完成";
                            }else if(item.taskStateFlag === '3'){
                                item.taskStateFlag = "延期审核中";
                            }else if(item.taskStateFlag === '4'){
                                item.taskStateFlag = "完成审核中";
                            }else if(item.taskStateFlag === '5'){
                                item.taskStateFlag = "已完成";
                            }else if(item.taskStateFlag === '6'){
                                item.taskStateFlag = "未通过";
                            }
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
                                        const {
                                            mainModule
                                        } = this.props.myPublic.appInfo;
                                        this.props.dispatch(
                                            push(
                                                `${mainModule}TodoMobileDetail/${
                                                item.taskId
                                                }`
                                            )
                                        );
                                    }}
                                >
                                    <div className={s.top}>
                                         <div className={s.topl}>任务事项：{item.taskMatter}</div>
                                         <div className={s.topr}>状态：{item.taskStateFlag}</div>
                                    </div>
                                    <Divider style={{ margin: "8px 0px",backgroundColor:'#1890ff' }} />
                                    <div style={{ paddingLeft: '3%' }}>
                                        内容摘要：{item.content}
                                    </div>
                                    <Divider style={{ margin: "8px 0px" }} />
                                    <div className={s.top}>
                                         <div className={s.topl}>承办部门：{item.undertakeDeptName}</div>
                                         <div className={s.topr}>责任人：{item.leaderName}</div>
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
export default TodoMobile;
