import React, { Component } from 'react';
import QnnForm from '../modules/qnn-table/qnn-form';
import s from "./style.less";
import { NavBar, Icon } from "antd-mobile";
import { push } from 'react-router-redux';
import { Form } from '@ant-design/compatible';
import { Divider, message as Msg } from 'antd';
import '@ant-design/compatible/assets/index.css';
class Index extends Component {
    constructor(props) {
        super(props);
        this.state = {
            applyId: props.match.params.applyId
        }
    }
    render() {
        const { dispatch, myPublic: { appInfo: { mainModule } } } = this.props;
        const { applyId } = this.state;
        return (
            <div className={s.root}>
                <NavBar
                    style={{ width: "100%" }}
                    mode="dark"
                    icon={<Icon type="left" />}
                    onLeftClick={() => {
                        dispatch(push(`${mainModule}ConsumablesSetting`));
                    }}
                >
                    {"详情"}
                </NavBar>
                <div style={{ height: window.innerHeight - 45 }}>
                    <QnnForm
                        fetch={this.props.myFetch}
                        upload={this.props.myUpload}
                        headers={{
                            token: this.props.loginAndLogoutInfo.loginInfo.token
                        }}
                        fetchConfig={{
                            apiName: 'getZjConsumableApplyDetails',
                            otherParams: {
                                applyId: applyId
                            }
                        }}
                        wrappedComponentRef={(me) => {
                            this.formLingy = me;
                        }}
                        formConfig={[
                            {
                                field: 'setId',
                                type: 'string',
                                hide: true
                            },
                            {
                                field: 'applyId',
                                type: 'string',
                                hide: true,
                                initialValue:applyId
                            },
                            {
                                label: '申请部门',
                                field: 'deptName',
                                type: 'string',
                                disabled: true,
                                placeholder: '请输入',
                            },
                            {
                                label: '申请人',
                                field: 'name',
                                type: 'string',
                                disabled: true,
                                placeholder: '请输入',
                            },
                            {
                                label: '申请时间',
                                field: 'appDate',
                                type: 'date',
                                disabled: true,
                                placeholder: '请输入',
                            },
                            {
                                label: '类别',
                                field: 'categoryName',
                                disabled: true,
                                type: 'string',
                                required: true,
                            },
                            {
                                label: '品牌',
                                field: 'brandName',
                                disabled: true,
                                type: 'string',
                                required: true,
                            },
                            {
                                label: '型号',
                                field: 'modelName',
                                disabled: true,
                                type: 'string',
                                required: true,
                            },
                            {
                                label: '颜色',
                                field: 'colourName',
                                disabled: true,
                                type: 'string',
                                required: true,
                            },
                            {
                                label: '申领数量',
                                field: 'applyNum',
                                disabled: true,
                                type: 'integer',
                                placeholder: '请输入',
                                required: true
                            },
                            {
                                label: '状态',
                                field: 'status',
                                disabled: true,
                                type: 'select',
                                placeholder: '请输入',
                                span: 12,
                                optionConfig: {
                                    value: 'value',
                                    label: 'label'
                                },
                                optionData: [
                                    {
                                        value: '0',
                                        label: '未审批'
                                    },
                                    {
                                        value: '1',
                                        label: '审批通过'
                                    },
                                    {
                                        value: '2',
                                        label: '驳回'
                                    },
                                ],
                                formItemLayout:{
                                    labelCol: {
                                        xs: { span: 12 },
                                        sm: { span: 14 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 12 },
                                        sm: { span: 10 }
                                    }
                                }
                            },
                            {
                                label: '领用状态',
                                field: 'useStatus',
                                disabled: true,
                                type: 'select',
                                placeholder: '请输入',
                                span: 12,
                                optionConfig: {
                                    value: 'value',
                                    label: 'label'
                                },
                                optionData: [
                                    {
                                        value: '0',
                                        label: '未领用'
                                    },
                                    {
                                        value: '1',
                                        label: '已领用'
                                    }
                                ],
                                hide: (obj) => {
                                    let vals = obj.form.getFieldsValue();
                                    if (vals.useStatus === '1') {
                                        return false
                                    } else {
                                        return true
                                    }
                                },
                                formItemLayout:{
                                    labelCol: {
                                        xs: { span: 12 },
                                        sm: { span: 12 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 12 },
                                        sm: { span: 12 }
                                    }
                                }
                            }
                        ]}
                        btns={[
                            {
                                name: "submit",
                                type: "primary",
                                label: "已领用",
                                field: 'yilingyong',
                                affirmTitle: '您确认已领取了相应耗材吗？',
                                affirmYes: '确定',
                                affirmNo: '取消',
                                onClick: (obj) => {
                                    const { myFetch } = this.props;
                                    myFetch('useZjConsumableApply', obj.values).then(
                                        ({ success, message }) => {
                                            if (success) {
                                                // this.formLingy.refresh();
                                                dispatch(push(`${mainModule}ConsumablesSetting`));
                                            } else {
                                                Msg.error(message)
                                            }
                                        }
                                    );
                                },
                                hide: (obj) => {
                                  
                                    // disabled: (obj) => {//跟affirmTitle冲突。
                                    let vals = obj.form.getFieldsValue();
                                    if (vals.status === '1') {
                                        if (vals.useStatus === '1') {
                                            return true
                                        } else {
                                            return false
                                        }
                                    } else {
                                        return true
                                    }
                                }
                            },
                            {
                                name: "cancelDiy",
                                type: "dashed",
                                label: "取消",
                                field: 'cancel',
                                isValidate: false,
                                onClick: (obj) => {
                                    dispatch(push(`${mainModule}ConsumablesSetting`));
                                },
                                hide: (obj) => {
                                    // disabled: (obj) => {//跟affirmTitle冲突。
                                    let vals = obj.form.getFieldsValue();
                                    if (vals.status === '1') {
                                        if (vals.useStatus === '1') {
                                            return true
                                        } else {
                                            return false
                                        }
                                    } else {
                                        return true
                                    }
                                }
                            },
                        ]}
                    />
                </div>
            </div>
        )
    }
}
export default Form.create()(Index)