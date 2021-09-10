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
                        {"人员审批明细"}
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
                            apiName: 'getZjFlowPersonInfoList', 
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
                                        <div className={s.topr}>申报时间：{item.appTime ? moment(item.appTime).format('YYYY-MM-DD') : ''}</div>
                                        
                                    </div>
                                    <Divider style={{ margin: "8px 0px" }} />
                                    <div className={s.top}>
                                        <div className={s.topl}>性别：{item.sex ? (item.sex  === '0'? '男' : '女') : ''}</div>
                                        <div className={s.topr}>出生日期：{item.birth ? moment(item.birth).format('YYYY-MM-DD') : ''}</div>
                                    </div>
                                    <Divider style={{ margin: "8px 0px" }} />
                                    <div className={s.top}>
                                        <div className={s.topl}>民族：{item.national ? item.national: ''}</div>
                                        <div className={s.topr}>学历：{item.education ? item.education : ''}</div>
                                    </div>
                                    <Divider style={{ margin: "8px 0px" }} />
                                    <div className={s.top}>
                                        <div className={s.topl}>学校：{item.school ? item.school : ''}</div>
                                        <div className={s.topr}>所属专业：{item.major ? item.major : ''}</div>
                                    </div>
                                    <Divider style={{ margin: "8px 0px" }} />
                                    <div className={s.top}>
                                        <div className={s.topl}>入聘部门：{item.employDept ? item.employDept : ''}</div>
                                        <div className={s.topr}>当前部门人数：{item.deptNum ? item.deptNum : ''}</div>
                                    </div>
                                    <Divider style={{ margin: "8px 0px" }} />
                                    <div className={s.top}>
                                        <div className={s.topl}>单位名称：{item.unitName ? item.unitName : ''}</div>
                                    </div>
                                    <Divider style={{ margin: "8px 0px" }} />
                                    <div className={s.top}>
                                        <div className={s.toplong}>聘用说明：{item.hireThat ? item.hireThat : ''}</div>
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
                                                dispatch(this.props.actions.saveNodes(item)); 
                                                dispatch(push(`${mainModule}PersonPageDetail/${item.personInfoId}/${source}`));
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
