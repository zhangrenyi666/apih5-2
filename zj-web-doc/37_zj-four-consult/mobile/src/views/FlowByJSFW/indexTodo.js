import React, { Component } from "react";
import MyList from "../modules/MList";
import { Divider } from "antd";
import s from "./style.less";
import { NavBar, Icon } from "antd-mobile";
import { push } from 'react-router-redux';
import moment from "moment";
class Index extends Component {
    constructor(props) {
        super(props);
        this.state = {
            
        }
    }
    render() {
        const { dispatch, myPublic: { appInfo: { mainModule } } } = this.props;
        return (
            <div className={s.root}>
                <div>
                    <NavBar
                        style={{ width: "100%" }}
                        mode="dark"
                        icon={<Icon type="left" />}
                        onLeftClick={() => {
                            dispatch(push(`${mainModule}App`));
                        }}
                    >
                        {"待办列表"}
                    </NavBar>
                </div>
                <div
                    style={{
                        height: document.documentElement.clientHeight - 45
                    }}
                >
                    <MyList
                        loginAndLogoutInfo={this.props.loginAndLogoutInfo}
                        myFetch={this.props.myFetch}
                        upload={this.props.myUpload}
                        searchKey="keyword"
                        fetchConfig={{
                            apiName: 'getTodoList',
                            otherParams: {
                              flowId: 'zjSjTechnicalService,CopyzjSjTechnicalService'
                            }
                        }}
                        Item={props => {
                            const { rowData, rowID } = props;
                            const item = rowData;
                            const index = rowID;
                            return (
                                <div
                                    className={s.center}
                                    key={index}
                                    onClick={() => {
                                        dispatch(push(`${mainModule}FlowByJSFWForm/${item.flowId}/${item.workId}/${item.title}/todo`));
                                    }}
                                >
                                    <div className={s.top}>
                                        <div className={s.topl}>{item.flowName}</div>
                                        <div className={s.topr}>{item.flowStatus}</div>
                                    </div>
                                    <Divider style={{ margin: "8px 0px" }} />
                                    <div className={s.top}>
                                      <div className={s.topl}>{item.title}</div>
                                      <div className={s.topr}><Icon style={{color: "#bbb",width: 25,height: 25}} type={"right"}/></div>
                                    </div>
                                    <Divider style={{ margin: "8px 0px" }} />
                                    <div className={s.top}>
                                        <div className={s.topl}><span style={{ marginRight: "10px" }}>{item.sendUserName}</span>{moment(item.sendTime).format("YYYY-MM-DD HH:mm:ss")}</div>
                                        <div className={s.topr}>{item.nodeName}</div>
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