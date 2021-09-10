import React, { Component } from "react";
import MyList from "../modules/MList";
import { Divider,Modal,message as Msg } from "antd";
import s from "./style.less";
import { NavBar, Icon,Button } from "antd-mobile";
import moment from 'moment';
import { push } from 'react-router-redux';
import { goBack } from "connected-react-router";
const { confirm } = Modal;
class Index extends Component {
    constructor(props) {
        super(props);
        this.state = {
            height: document.documentElement.clientHeight - 44 - 43 - 45 - 1,
            workId: props.match.params.workId,
            source:props.match.params.source
        };
    }
    render() {
        const { dispatch, myPublic: { appInfo: { mainModule } } } = this.props;
        var LoginAndLogoutInfo = this.props.loginAndLogoutInfo;
        var ext1 = LoginAndLogoutInfo.loginInfo.userInfo;
        const {workId,source} = this.state;
        return (
            <div className={s.root}>
                <div>
                    <NavBar
                        style={{ width: "100%" }}
                        mode="dark"
                        icon={<Icon type="left" />}
                        onLeftClick={() => {
                            if (source === '1') {
                                dispatch(push(`${mainModule}ReadListMobile`));
                            } else if(source === '0'){
                                dispatch(push(`${mainModule}TabsListMobile`));
                            } else {
                                
                            }
                        }}
                    >
                        {"部门领导审批明细"}
                    </NavBar>
                </div>
                <div
                    style={{
                        height: document.documentElement.clientHeight - 45
                    }}
                >
                   <MyList
                        myFetch={this.props.myFetch} 
		                upload={this.props.myUpload} 
                        searchKey=""
                        fetchConfig={{
                            apiName: 'getZjFlowMinisterInfoList', 
                            otherParams:  {
                                workId: workId
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
                                    }}
                                >
                                    <div className={s.top}>
                                        <div className={s.topl}>【{item.name ? item.name : ''}】</div>
                                        <div className={s.topR}>申报时间：{item.appTime ? moment(item.appTime).format('YYYY-MM-DD') : ''}</div>
                                        
                                    </div>
                                    <Divider style={{ margin: "8px 0px" }} />
                                    <div className={s.top}>
                                        <div className={s.topl}>原职务：{item.originalPosition ? item.originalPosition : ''}</div>
                                        <div className={s.topr}>申请部门：{item.appDept ? item.appDept : ''}</div>
                                    </div>
                                    <Divider style={{ margin: "8px 0px" }} />
                                    <div className={s.top}>
                                        <div className={s.topl}>申请级别：{item.appLevel ? item.appLevel: ''}</div>
                                        <div className={s.topr}>基本条件：{item.basicCondition ? item.basicCondition : ''}</div>
                                    </div>
                                    <Divider style={{ margin: "8px 0px" }} />
                                    <div className={s.top}>
                                    <div className={s.topl}>人员类别：{item.category ? item.category : ''}</div>
                                    </div>
                                    <Divider style={{ margin: "8px 0px" }} />
                                    <div className={s.top}>
                                        <div className={s.toplong}>初审意见：{item.opinionField1 ? item.opinionField1 : ''}</div>
                                    </div>
                                    <Divider style={{ margin: "8px 0px" }} />
                                    <div className={s.top}>
                                        <div className={s.toplong}>领导意见：{item.opinionField2 ? item.opinionField2 : ''}</div>
                                    </div>
                                    {this.props.match.params.status === 'Done' ? '' : <Divider style={{ margin: "8px 0px"}} /> }
                                    {this.props.match.params.status === 'Done' ? '' : <div className={s.top}>
                                        <div className={s.topbtn} style={{textAlign:'right'}}>
                                            <Button type="primary" size="small" onClick={(e) => {
                                                e.stopPropagation();
                                                item.status = this.props.match.params.status;
                                                item.nodeName = this.props.match.params.nodeName;
                                                item.workId = this.props.match.params.workId;
                                                dispatch(this.props.actions.saveNodesMinister(item)); 
                                                dispatch(push(`${mainModule}MinisterPageDetail/${item.ministerInfoId}/${source}`));
                                            }}>填写意见</Button>
                                        </div>
                                   </div> }
                                   
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
