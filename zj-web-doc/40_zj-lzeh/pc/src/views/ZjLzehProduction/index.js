import React, { Component } from 'react';
import QnnForm from "../modules/qnn-table/qnn-form";
import { message as Msg, Modal, Spin } from "antd";
const config = {
    //获取数据
    fetchConfig: {
        apiName: 'getZjLzehProductionList'
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
                            field: 'productionId',
                            form: {
                                label: '主键id',
                                field: 'productionId',
                                hide: true,
                            }
                        },
                        {
                            label: "合同总计金额",
                            type: "number",
                            field: "totalContractAmount",
                            placeholder: "请输入...",
                            required: true,
                            precision: 2,
                            span: 12,
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 24 },
                                    sm: { span: 6 }
                                },
                                wrapperCol: {
                                    xs: { span: 24 },
                                    sm: { span: 12 }
                                }
                            }
                        },
                        {
                            label: "累计完成金额",
                            type: "number",
                            field: "accumulatedCompletionAmount",
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
                                    sm: { span: 12 }
                                }
                            }
                        },
                        {
                            label: "本年计划金额",
                            type: "number",
                            field: "currentYearSchemeAmount",
                            placeholder: "请输入...",
                            required: true,
                            span: 12,
                            // precision: 2,
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 24 },
                                    sm: { span: 6 }
                                },
                                wrapperCol: {
                                    xs: { span: 24 },
                                    sm: { span: 12 }
                                }
                            }
                        },
                        {
                            label: "本年完成金额",
                            type: "number",
                            field: "currentYearCompletionAmount",
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
                                    sm: { span: 12 }
                                }
                            }
                        },
                        {
                            label: "本月计划金额",
                            type: "number",
                            field: "currentMonthSchemeAmount",
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
                                    sm: { span: 12 }
                                }
                            }
                        },
                        {
                            label: "本月完成金额",
                            type: "number",
                            field: "currentMonthCompletionAmount",
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
                                    sm: { span: 12 }
                                }
                            }
                        }
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
                                this.props.myFetch('updateZjLzehProduction', obj.values).then(
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