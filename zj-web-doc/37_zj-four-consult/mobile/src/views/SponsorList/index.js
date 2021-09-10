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
            ext1: props.loginAndLogoutInfo.loginInfo.userInfo.ext1 || ''
        }
    }
    render() {
        const { ext1 } = this.state;
        const { dispatch, myPublic: { appInfo: { mainModule } } } = this.props;
        return (
            <div className={s.root} id='root'>
                <div>
                    <NavBar
                        style={{ width: "100%" }}
                        mode="dark"
                        icon={<Icon type="left" />}
                        onLeftClick={() => {
                            dispatch(push(`${mainModule}App`));
                        }}
                    >
                        {"技术服务列表"}
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
                        searchKey="title"
                        fetchConfig={{
                            apiName: 'getZjSjConsultTechnicalServiceFlowList',
                            otherParams: {
                                selectType: '1',
                                ext2: ext1
                            }
                        }}
                        Item={props => {
                            const { rowData, rowID } = props;
                            const item = rowData;
                            const index = rowID;
                            let apih5FlowStatusName;
                            let solveFlagName;
                            if(item.apih5FlowStatus === '0'){
                                apih5FlowStatusName = '已发起';
                            }else if(item.apih5FlowStatus === '1'){
                                apih5FlowStatusName = '审核中';
                            }else if(item.apih5FlowStatus === '2'){
                                apih5FlowStatusName = '审核结束';
                            }else if(item.apih5FlowStatus === '3'){
                                apih5FlowStatusName = '退回';
                            }else if(item.apih5FlowStatus === '4'){
                                apih5FlowStatusName = '未发起';
                            }
                            if(item.solveFlag === '0'){
                                solveFlagName = '未解决';
                            }else if(item.solveFlag === '1'){
                                solveFlagName = '解决中';
                            }else if(item.solveFlag === '2'){
                                solveFlagName = '已解决';
                            }
                            return (
                                <div
                                    className={s.center}
                                    key={index}
                                    onClick={() => {
                                        dispatch(push(`${mainModule}SponsorDetail/${item.serviceId}`));
                                    }}
                                >
                                    <div className={s.top}>
                                        <div className={s.topl}>【{item.title}】</div>
                                        <div className={s.topr} style={{color:'#1890ff'}}>{apih5FlowStatusName}</div>
                                    </div>
                                    <Divider style={{ margin: "8px 0px" }} />
                                    <div className={s.top}>
                                        <div className={s.topc}>{item.content}</div>
                                    </div>
                                    <Divider style={{ margin: "8px 0px" }} />
                                    <div className={s.top}>
                                        <div className={s.topl}>{solveFlagName}</div>
                                        <div className={s.topr}>{item.solveTime ? moment(item.solveTime).format('YYYY-MM-DD') : null}</div>
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