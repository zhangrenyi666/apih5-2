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
        this.state = {}
    }
    callback = (key) => {
        if (this.table1 && this.table1.form) {
            if (key === '0') {
                this.table1.form.setFieldsValue({
                    category: '',
                    brand: '',
                    model: '',
                    colour: '',
                    applyNum: ''
                })
            } else {
            }
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
                                title: "填报",
                                content: {
                                    formConfig: [
                                        {
                                            field: 'setId',
                                            type: 'string',
                                            hide: true
                                        },
                                        {
                                            field: 'userKey',
                                            type: 'string',
                                            hide: true,
                                            initialValue: () => {
                                                return this.props.loginAndLogoutInfo.loginInfo.userInfo.userKey
                                            },
                                        },
                                        {
                                            label: '申请部门',
                                            field: 'deptName',
                                            type: 'string',
                                            disabled: true,
                                            placeholder: '请输入',
                                            initialValue: () => {
                                                return this.props.loginAndLogoutInfo.loginInfo.userInfo.departmentList[0].departmentName
                                            },
                                        },
                                        {
                                            label: '申请人',
                                            field: 'name',
                                            type: 'string',
                                            disabled: true,
                                            placeholder: '请输入',
                                            initialValue: () => {
                                                return this.props.loginAndLogoutInfo.loginInfo.userInfo.realName
                                            },
                                        },
                                        {
                                            label: '申请时间',
                                            field: 'appDate',
                                            type: 'date',
                                            disabled: true,
                                            placeholder: '请输入',
                                            initialValue: new Date()
                                        },
                                        {
                                            label: '类别',
                                            field: 'category',
                                            type: 'select',
                                            placeholder: '请选择...',
                                            required: true,
                                            allowClear: false,
                                            optionConfig: {
                                                label: 'category',
                                                value: 'setId',
                                                children: 'childrenList',
                                                linkageFields: {
                                                    categoryName: 'category'
                                                }
                                            },
                                            fetchConfig: {
                                                apiName: 'getZjConsumableSetList',
                                                otherParams: {
                                                    codePid: '0',
                                                    useState: '0'
                                                }
                                            }
                                        },
                                        {
                                            field: 'categoryName',
                                            type: 'string',
                                            hide: true
                                        },
                                        {
                                            field: 'brandName',
                                            type: 'string',
                                            hide: true
                                        },
                                        {
                                            label: '品牌',
                                            field: 'brand',
                                            type: 'select',
                                            parent: 'category',
                                            required: true,
                                            placeholder: '请选择...',
                                            optionConfig: {
                                                label: 'brand',
                                                value: 'setId',
                                                linkageFields: {
                                                    brandName: 'brand'
                                                },
                                                children: 'childrenList'
                                            }
                                        },
                                        {
                                            field: 'modelName',
                                            type: 'string',
                                            hide: true
                                        },
                                        {
                                            label: '型号',
                                            field: 'model',
                                            parent: 'brand',
                                            type: 'select',
                                            required: true,
                                            optionConfig: {
                                                label: 'model',
                                                value: 'setId',
                                                linkageFields: {
                                                    modelName: 'model'
                                                },
                                                children: 'childrenList'
                                            }
                                        },
                                        {
                                            field: 'colourName',
                                            type: 'string',
                                            hide: true
                                        },
                                        {
                                            label: '颜色',
                                            type: 'select',
                                            field: 'colour',
                                            parent: 'model',
                                            required: true,
                                            optionConfig: {
                                                label: 'colour',
                                                value: 'setId',
                                                linkageFields: {
                                                    setId: 'setId',
                                                    colourName: 'colour'
                                                },
                                            }
                                        },
                                        {
                                            label: '申领数量',
                                            field: 'applyNum',
                                            type: 'integer',
                                            placeholder: '请输入',
                                            required: true
                                        }
                                    ],
                                    btns: [
                                        {
                                            name: 'submit',
                                            type: 'primary',
                                            label: '提交',
                                            field: 'tianbao',
                                            isValidate: 'curTab',
                                            onClick: (obj) => {
                                                const { myFetch } = this.props;
                                                myFetch('addZjConsumableApply', obj.values).then(
                                                    ({ success, message }) => {
                                                        if (success) {
                                                            obj.btnCallbackFn.setActiveKey("1");
                                                            this.callback('1');
                                                        } else {
                                                            Msg.error(message)
                                                        }
                                                    }
                                                );
                                            }
                                        }
                                    ]
                                }
                            },
                            {
                                field: "form3",
                                name: "qnnForm",
                                title: "已填报内容",
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
                                                            searchKey="category"
                                                            searchKeyTwo=""
                                                            fetchConfig={{
                                                                apiName: 'getZjConsumableApplyList',
                                                                otherParams: {
                                                                    userKey: this.props.loginAndLogoutInfo.loginInfo.userInfo.userKey
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
                                                                            dispatch(push(`${mainModule}ConsumablesSettingDetail/${item.applyId}`));
                                                                        }}
                                                                    >
                                                                        <div className={s.top}>
                                                                            <div className={s.topl} style={{ fontWeight: 'bold' }}>申请部门：{item.deptName ? item.deptName : '无'}</div>

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