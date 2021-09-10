import React, { Component } from "react";
import { NavBar, Icon, Tabs } from "antd-mobile";
import s from './style.less';
import MyList from "../modules/MList";
import { push } from 'react-router-redux';
import { PlusOutlined } from '@ant-design/icons';
import { Divider } from 'antd';
import moment from 'moment';
class index extends Component {
    constructor(props) {
        super(props);
        this.state = {
            page: Number(props.match.params.page) || 0,
            ext1: props.loginAndLogoutInfo.loginInfo.userInfo.ext1 || ''
        }
    }
    render() {
        const { ext1, page } = this.state;
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
                        {"技术服务列表"}
                    </NavBar>
                </div>
                <div style={{ height: document.documentElement.clientHeight - 45, width: '100%' }}>
                    <Tabs
                        tabs={[{ title: <div><PlusOutlined /> 技术服务申请</div> }]}
                        tabBarPosition='bottom'
                        swipeable={false}
                        onTabClick={() => {
                            dispatch(push(`${mainModule}TechnicalServicesAdd`));
                        }}
                        >
                        <div style={{ height: document.documentElement.clientHeight - 90, width: '100%' }}>
                            <Tabs
                                tabs={[{ title: '我发布的' }, { title: '全部' }]}
                                swipeable={false} page={page}
                                onChange={(_, page) => {
                                    this.setState({
                                        page
                                    })
                                }}>
                                <div style={{ height: '100%', width: '100%' }}>
                                    {page === 0 ? <MyList
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
                                            if (item.apih5FlowStatus === '0') {
                                                apih5FlowStatusName = '已发起';
                                            } else if (item.apih5FlowStatus === '1') {
                                                apih5FlowStatusName = '审核中';
                                            } else if (item.apih5FlowStatus === '2') {
                                                apih5FlowStatusName = '审核结束';
                                            } else if (item.apih5FlowStatus === '3') {
                                                apih5FlowStatusName = '退回';
                                            } else if (item.apih5FlowStatus === '4') {
                                                apih5FlowStatusName = '未发起';
                                            }
                                            if (item.solveFlag === '0') {
                                                solveFlagName = '未解决';
                                            } else if (item.solveFlag === '1') {
                                                solveFlagName = '解决中';
                                            } else if (item.solveFlag === '2') {
                                                solveFlagName = '已解决';
                                            }
                                            return (
                                                <div
                                                    className={s.center}
                                                    key={index}
                                                    onClick={() => {
                                                        dispatch(push(`${mainModule}TechnicalServicesDetail/${item.serviceId}/1`));
                                                    }}
                                                >
                                                    <div className={s.top}>
                                                        <div className={s.topl}>【{item.title}】</div>
                                                        <div className={s.topr} style={{ color: '#1890ff' }}>{apih5FlowStatusName}</div>
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
                                    /> : null}
                                </div>
                                <div style={{ height: '100%', width: '100%' }}>
                                    {page === 1 ? <MyList
                                        loginAndLogoutInfo={this.props.loginAndLogoutInfo}
                                        myFetch={this.props.myFetch}
                                        upload={this.props.myUpload}
                                        searchKey="title"
                                        fetchConfig={{
                                            apiName: 'getZjSjConsultTechnicalServiceFlowList',
                                            otherParams: {
                                                selectType: '0',
                                                ext2: ext1
                                            }
                                        }}
                                        Item={props => {
                                            const { rowData, rowID } = props;
                                            const item = rowData;
                                            const index = rowID;
                                            let apih5FlowStatusName;
                                            let solveFlagName;
                                            if (item.apih5FlowStatus === '0') {
                                                apih5FlowStatusName = '已发起';
                                            } else if (item.apih5FlowStatus === '1') {
                                                apih5FlowStatusName = '审核中';
                                            } else if (item.apih5FlowStatus === '2') {
                                                apih5FlowStatusName = '审核结束';
                                            } else if (item.apih5FlowStatus === '3') {
                                                apih5FlowStatusName = '退回';
                                            } else if (item.apih5FlowStatus === '4') {
                                                apih5FlowStatusName = '未发起';
                                            }
                                            if (item.solveFlag === '0') {
                                                solveFlagName = '未解决';
                                            } else if (item.solveFlag === '1') {
                                                solveFlagName = '解决中';
                                            } else if (item.solveFlag === '2') {
                                                solveFlagName = '已解决';
                                            }
                                            return (
                                                <div
                                                    className={s.center}
                                                    key={index}
                                                    onClick={() => {
                                                        dispatch(push(`${mainModule}TechnicalServicesDetail/${item.serviceId}/0`));
                                                    }}
                                                >
                                                    <div className={s.top}>
                                                        <div className={s.topl}>【{item.title}】</div>
                                                        <div className={s.topr} style={{ color: '#1890ff' }}>{apih5FlowStatusName}</div>
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
                                    /> : null}
                                </div>
                            </Tabs>
                        </div>
                    </Tabs>
                </div>
            </div>
        );
    }
}

export default index;