import React, { Component } from 'react';
import QnnForm from '../modules/qnn-table/qnn-form';
import s from "./style.less";
import MyList from "../modules/MList";
import { NavBar, Icon, Button } from "antd-mobile";
import moment from 'moment';
import { push } from 'react-router-redux';
import { Divider, message as Msg } from 'antd';

class Index extends Component {
    constructor(props) {
        super(props);
        this.state = {

        }
    }
    callback = (key) => {
        if (this.table1 && this.table1.form) {
            this.table1.refresh();
        }
    }
    render() {
        let { dispatch, myPublic: { appInfo: { mainModule } } } = this.props;
        return (
            <div className={s.root}>
                <NavBar
                    style={{ width: "100%" }}
                    mode="dark"
                    icon={<Icon type="left" />}
                    onLeftClick={() => { }}
                >
                    {"中交一公局集团 - 耗材领用"}
                </NavBar>
                <div style={{
                    height: document.documentElement.clientHeight - 45, overflow: 'auto'
                }}>
                    <QnnForm
                        fetch={this.props.myFetch}
                        upload={this.props.myUpload}
                        headers={{
                            token: this.props.loginAndLogoutInfo.loginInfo.token
                        }}
                        wrappedComponentRef={(me) => {
                            this.table1 = me;
                        }}
                        onTabsChange={(val) => {
                            this.callback(val);
                        }}
                        formConfig={[]}
                        btns={[]}
                        tabs={[
                            {
                                field: "form1",
                                name: "qnnForm",
                                title: "审批",
                                content: {
                                    formConfig: [
                                        {
                                            type: 'component',
                                            field: 'form2',
                                            title: '',
                                            Component: () => {
                                                return (
                                                    <div className={s.rootList}>
                                                        <MyList
                                                            myFetch={this.props.myFetch}
                                                            upload={this.props.myUpload}
                                                            searchKey="name"
                                                            searchKeyTwo=""
                                                            fetchConfig={{
                                                                apiName: 'getZjConsumableApplyList',
                                                                otherParams: {
                                                                    status: '0'
                                                                }
                                                            }}
                                                            Item={props => {
                                                                const { rowData, rowID } = props;
                                                                const item = rowData;
                                                                const index = rowID;
                                                                let shenpiStatus = '';
                                                                if (item.status === '0') {
                                                                    shenpiStatus = '未审批';
                                                                } else if (item.status === '1') {
                                                                    shenpiStatus = '审批通过';
                                                                } else if (item.status === '2') {
                                                                    shenpiStatus = '驳回';
                                                                } else {

                                                                }
                                                                return (
                                                                    <div
                                                                        className={s.center}
                                                                        key={index}
                                                                        onClick={(e) => {
                                                                            e.stopPropagation();
                                                                            dispatch(push(`${mainModule}ConsumablesSettingShenpiDetail/${item.applyId}/${item.status}`));
                                                                        }}
                                                                    >
                                                                        <div className={s.top}>
                                                                            <div className={s.topl} style={{fontWeight:'bold'}}>申请部门：{item.deptName ? item.deptName : '无'}</div>

                                                                        </div>
                                                                        <Divider style={{ margin: "8px 0px" }} />
                                                                        <div className={s.top}>
                                                                            <div className={s.topl}>申请时间：{item.appDate ? moment(item.appDate).format('YYYY-MM') : '无'}</div>
                                                                            <div className={s.topr}>申请人：{item.name ? item.name : '无'}</div>
                                                                        </div>
                                                                        <Divider style={{ margin: "8px 0px" }} />
                                                                        <div className={s.top}>
                                                                            <div className={s.topl}>类别：{item.categoryName ? item.categoryName : '无'}</div>
                                                                            <div className={s.topr}>品牌：{item.brandName ? item.brandName : '无'}</div>
                                                                        </div>
                                                                        <Divider style={{ margin: "8px 0px" }} />
                                                                        <div className={s.top}>
                                                                            <div className={s.topl}>申领数量：{item.applyNum ? item.applyNum : '无'}</div>
                                                                            <div className={s.topr}>颜色：{item.colourName ? item.colourName : '无'}</div>
                                                                        </div>
                                                                        <Divider style={{ margin: "8px 0px" }} />
                                                                        <div className={s.top}>
                                                                            <div className={s.topl}>领用状态：{item.useStatus ? (item.useStatus === '0' ? '未领用' : '已领用') : ''}</div>
                                                                            <div className={s.topr}>审批状态：{shenpiStatus}</div>
                                                                        </div>
                                                                    </div>
                                                                );
                                                            }}
                                                        />
                                                    </div>
                                                )
                                            },
                                        }
                                    ],
                                    btns: []
                                }
                            },
                            {
                                field: "form3",
                                name: "qnnForm",
                                title: "已审批",
                                content: {
                                    formConfig: [
                                        {
                                            type: 'component',
                                            field: 'form2',
                                            title: '',
                                            Component: () => {
                                                return (
                                                    <div className={s.rootList}>
                                                        <MyList
                                                            myFetch={this.props.myFetch}
                                                            upload={this.props.myUpload}
                                                            searchKey="name"
                                                            searchKeyTwo=""
                                                            fetchConfig={{
                                                                apiName: 'getZjConsumableApplyList',
                                                                otherParams: {
                                                                    statusIsNot0Flag: '0'
                                                                }
                                                            }}
                                                            Item={props => {
                                                                const { rowData, rowID } = props;
                                                                const item = rowData;
                                                                const index = rowID;
                                                                let shenpiStatus = '';
                                                                if (item.status === '0') {
                                                                    shenpiStatus = '未审批';
                                                                } else if (item.status === '1') {
                                                                    shenpiStatus = '审批通过';
                                                                } else if (item.status === '2') {
                                                                    shenpiStatus = '驳回';
                                                                } else {

                                                                }
                                                                return (
                                                                    <div
                                                                        className={s.center}
                                                                        key={index}
                                                                        onClick={(e) => {
                                                                            e.stopPropagation();
                                                                            dispatch(push(`${mainModule}ConsumablesSettingShenpiDetail/${item.applyId}/${item.status}`));
                                                                        }}
                                                                    >
                                                                        <div className={s.top}>
                                                                            <div className={s.topl} style={{fontWeight:'bold'}}>申请部门：{item.deptName ? item.deptName : '无'}</div>

                                                                        </div>
                                                                        <Divider style={{ margin: "8px 0px" }} />
                                                                        <div className={s.top}>
                                                                            <div className={s.topl}>申请时间：{item.appDate ? moment(item.appDate).format('YYYY-MM') : '无'}</div>
                                                                            <div className={s.topr}>申请人：{item.name ? item.name : '无'}</div>
                                                                        </div>
                                                                        <Divider style={{ margin: "8px 0px" }} />
                                                                        <div className={s.top}>
                                                                            <div className={s.topl}>类别：{item.categoryName ? item.categoryName : '无'}</div>
                                                                            <div className={s.topr}>品牌：{item.brandName ? item.brandName : '无'}</div>
                                                                        </div>
                                                                        <Divider style={{ margin: "8px 0px" }} />
                                                                        <div className={s.top}>
                                                                            <div className={s.topl}>申领数量：{item.applyNum ? item.applyNum : '无'}</div>
                                                                            <div className={s.topr}>颜色：{item.colourName ? item.colourName : '无'}</div>
                                                                        </div>
                                                                        <Divider style={{ margin: "8px 0px" }} />
                                                                        <div className={s.top}>
                                                                            <div className={s.topl}>领用状态：{item.useStatus ? (item.useStatus === '0' ? '未领用' : '已领用') : ''}</div>
                                                                            <div className={s.topr}>审批状态：{shenpiStatus}</div>
                                                                        </div>
                                                                    </div>
                                                                );
                                                            }}
                                                        />
                                                    </div>
                                                )
                                            },
                                        }
                                    ],
                                    btns: []
                                }
                            },
                        ]}
                    />
                </div>

            </div>
        )
    }
}
export default Index;