import React, { Component } from 'react';
import QnnForm from "../modules/qnn-table/qnn-form";
import { message as Msg, Modal, Spin } from "antd";
const config = {
    //获取数据
    fetchConfig: {
        apiName: 'getZjLzehLaborRealNameList'
    },
}
class index extends Component {
    render() {
        return (
            <div>
                <QnnForm
                    {...this.props}
                    fetch={this.props.myFetch}
                    upload={this.props.myUpload}
                    headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                    {...config}
                    wrappedComponentRef={(me) => this.qnnForm = me}
                    formConfig={[
                        {
                            isInTable: false,
                            field: 'laborRealNameId',
                            form: {
                                label:'主键id',
                                field: 'laborRealNameId',
                                hide: true,
                            }
                        },
                        {
                            label: "参见单位数量",
                            type: "number",
                            field: "seeUnitNumber",
                            placeholder: "请输入...",
                            required: true,
                            span: 12,
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 24 },
                                    sm: { span: 6 }
                                },
                                wrapperCol: {
                                    xs: { span: 24 },
                                    sm: { span: 15 }
                                }
                            }
                        },
                        {
                            label: "班组数量",
                            type: "number",
                            field: "teamNumber",
                            placeholder: "请输入...",
                            required: true,
                            span: 12,
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 24 },
                                    sm: { span: 6 }
                                },
                                wrapperCol: {
                                    xs: { span: 24 },
                                    sm: { span: 15 }
                                }
                            }
                        },
                        {
                            label: "在场人员数量",
                            type: "number",
                            field: "presencePersonnelNumber",
                            placeholder: "请输入...",
                            required: true,
                            span: 12,
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 24 },
                                    sm: { span: 6 }
                                },
                                wrapperCol: {
                                    xs: { span: 24 },
                                    sm: { span: 15 }
                                }
                            }
                        },
                        {
                            label: "本月工资发放总和",
                            type: "number",
                            field: "currentMonthSalaryTotal",
                            placeholder: "请输入...",
                            required: true,
                            span: 12,
                            precision: 2,
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 24 },
                                    sm: { span: 6 }
                                },
                                wrapperCol: {
                                    xs: { span: 24 },
                                    sm: { span: 15 }
                                }
                            }
                        },
                    ]}
                    btns={[
                        {
                            name: 'submit',
                            type: 'primary',
                            label: '保存',
                            field: 'submit',
                            // offse: 5,
                            // style:{
                            //     left:540,
                            //     width:100,
                            //     top:20
                            // },
                            onClick: (obj) => {
                                this.props.myFetch('updateZjLzehLaborRealName', obj.values).then(
                                    ({ success, message }) => {
                                        if (success) {
                                            Msg.success(message);
                                        } else {
                                            Msg.error(message)
                                        }
                                    }
                                );
                            },
                        }
                    ]}
                />
            </div>
        )
    }
}


export default index