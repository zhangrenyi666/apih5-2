import React, { Component } from 'react';
import QnnForm from '../modules/qnn-table/qnn-form';
import s from "./style.less";
import { NavBar, Icon } from "antd-mobile";
import { push } from 'react-router-redux';
import { Form } from '@ant-design/compatible';
import { Divider, message as Msg, Modal } from 'antd';
import '@ant-design/compatible/assets/index.css';
const confirm = Modal.confirm;
class Index extends Component {
    constructor(props) {
        super(props);
        this.state = {
            applyId: props.match.params.applyId,
            status: props.match.params.status,
            visiable: false
        }
    }
    handleCancel = () => {
        this.setState({
            visiable: false
        })
    }
    render() {
        const { dispatch, myPublic: { appInfo: { mainModule } } } = this.props;
        const { applyId, visiable, status } = this.state;
        const { userId } = this.props.loginAndLogoutInfo.loginInfo.userInfo;

        return (
            <div className={s.root}>
                <NavBar
                    style={{ width: "100%" }}
                    mode="dark"
                    icon={<Icon type="left" />}
                    onLeftClick={() => {
                        dispatch(push(`${mainModule}ConsumablesSettingShenPi`));
                    }}
                >
                    {"中交一公局集团 - 耗材领用"}
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
                        formConfig={[
                            {
                                field: 'applyId',
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
                                placeholder: '请输入'
                            },
                            {
                                label: '申请人',
                                field: 'name',
                                type: 'string',
                                disabled: true,
                                placeholder: '请输入'
                            },
                            {
                                label: '申请时间',
                                field: 'appDate',
                                type: 'date',
                                disabled: true,
                                placeholder: '请输入'
                            },
                            {
                                label: '类别',
                                field: 'categoryName',
                                type: 'string',
                                disabled: true,
                                required: true
                            },
                            {
                                label: '品牌',
                                field: 'brandName',
                                disabled: true,
                                type: 'string',
                                required: true
                            },
                            {
                                label: '型号',
                                field: 'modelName',
                                disabled: true,
                                type: 'string',
                                required: true
                            },
                            {
                                label: '颜色',
                                field: 'colourName',
                                disabled: true,
                                type: 'string',
                                required: true
                            },
                            {
                                label: '申领数量',
                                field: 'applyNum',
                                type: 'integer',
                                disabled: true,
                                required: true
                            },
                            {
                                label: '当前最多可领取数量',
                                field: 'leftNum',
                                type: 'integer',
                                disabled: true,
                                required: true
                            }
                        ]}
                        btns={[
                            {
                                name: "submit",
                                type: "primary",
                                label: "审批",
                                field: 'shenpi',
                                isValidate: false,
                                onClick: (obj) => {
                                    this.setState({
                                        visiable: true
                                    })
                                },
                                hide: () => {
                                    // 徐兴海只有查看权限，陈都能操作。
                                    return userId === 'wangzhonghui' ? (status === '0' ? false : true) : true
                                },
                                // hide: () => {
                                //     // 徐兴海只有查看权限，陈都能操作。
                                //     return userId === 'zhangcan_test' ? (status === '0' ? false : true) : true
                                // }
                                // hide: () => {
                                //     // 徐兴海只有查看权限，陈都能操作。
                                //     return userId === 'feiteng_test' ? (status === '0' ? false : true) : true
                                // }
                            },
                            {
                                name: "cancelDiy",
                                type: "dashed",
                                label: "返回",
                                field: 'cancel',
                                isValidate: false,
                                onClick: (obj) => {
                                    dispatch(push(`${mainModule}ConsumablesSettingShenPi`));
                                },
                                hide: () => {
                                    // 徐兴海只有查看权限，陈都能操作。
                                    return userId === 'wangzhonghui' ? (status === '0' ? false : true) : true
                                },
                                // hide: () => {
                                //     // 徐兴海只有查看权限，陈都能操作。
                                //     return userId === 'zhangcan_test' ? (status === '0' ? false : true) : true
                                // }
                                // hide: () => {
                                //     // 徐兴海只有查看权限，陈都能操作。
                                //     return userId === 'feiteng_test' ? (status === '0' ? false : true) : true
                                // }
                            },
                        ]}
                    />
                </div>
                {visiable ? <Modal
                    width={'90%'}
                    style={{ paddingBottom: '0', top: '0' }}
                    title="请选择审批意见"
                    visible={visiable}
                    footer={null}
                    onCancel={this.handleCancel}
                    onOk={this.handleCancel}
                    bodyStyle={{ overflow: 'auto', height: window.innerHeight * 0.3 }}
                    centered={true}
                    destroyOnClose={this.handleCancel}
                    wrapClassName={'modals'}
                >
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
                        formConfig={[
                            {
                                field: 'applyId',
                                type: 'string',
                                hide: true
                            },
                            {
                                field: 'userKey',
                                type: 'string',
                                hide: true
                            },
                            {
                                label: '申请部门',
                                field: 'deptName',
                                type: 'string',
                                disabled: true,
                                placeholder: '请输入',
                                hide: true
                            },
                            {
                                label: '申请人',
                                field: 'name',
                                type: 'string',
                                disabled: true,
                                placeholder: '请输入',
                                hide: true
                            },
                            {
                                label: '申请时间',
                                field: 'appDate',
                                type: 'date',
                                disabled: true,
                                placeholder: '请输入',
                                hide: true
                            },
                            {
                                label: '类别',
                                field: 'categoryName',
                                type: 'string',
                                disabled: true,
                                required: true,
                                hide: true
                            },
                            {
                                label: '品牌',
                                field: 'brandName',
                                disabled: true,
                                type: 'string',
                                required: true,
                                hide: true
                            },
                            {
                                label: '型号',
                                field: 'modelName',
                                disabled: true,
                                type: 'string',
                                required: true,
                                hide: true
                            },
                            {
                                label: '颜色',
                                field: 'colourName',
                                disabled: true,
                                type: 'string',
                                required: true,
                                hide: true
                            },
                            {
                                label: '申领数量',
                                field: 'applyNum',
                                type: 'integer',
                                disabled: true,
                                required: true,
                                hide: true
                            },
                            {
                                label: '当前最多可领取数量',
                                field: 'leftNum',
                                type: 'integer',
                                disabled: true,
                                required: true,
                                hide: true
                            },
                            {
                                label: '意见',
                                type: 'radio',
                                field: 'status',
                                required: true,
                                placeholder: '请选择',
                                optionConfig: {
                                    label: "label",
                                    value: "value"
                                },
                                optionData: [
                                    {
                                        "label": "审批通过",
                                        "value": "1",
                                        // "disabled": true
                                    },
                                    {
                                        "label": "审批不通过",
                                        "value": "2"
                                    }
                                ]
                            }
                        ]}
                        btns={[
                            {
                                label: '确定',
                                type: 'primary',
                                field: 'submit',
                                onClick: (obj) => {
                                    const { myFetch } = this.props;
                                    myFetch('approveZjConsumableApply', obj.values).then(
                                        ({ success, message }) => {
                                            if (success) {
                                                this.setState({
                                                    visiable: false
                                                }, () => {
                                                    dispatch(push(`${mainModule}ConsumablesSettingShenPi`));
                                                })
                                            } else {
                                                Msg.error(message)
                                            }
                                        }
                                    );

                                }
                            },
                            {
                                name: "cancelDiy",
                                type: "dashed",
                                label: "取消",
                                field: 'cancel',
                                isValidate: false,
                                onClick: () => {
                                    this.setState({
                                        visiable: false
                                    })
                                }
                            }
                        ]}
                    />
                </Modal> : null}
            </div>
        )
    }
}
export default Form.create()(Index)