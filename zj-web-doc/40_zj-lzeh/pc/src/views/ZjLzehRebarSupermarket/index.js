import React, { Component } from 'react';
import QnnForm from "../modules/qnn-table/qnn-form";
import { message as Msg, Modal, Spin } from "antd";
const config = {
    //获取数据
    fetchConfig: {
        apiName: 'getZjLzehRebarSupermarketList'
    },
}
class index extends Component {
    render() {
        return (
            <div>
                <QnnForm
                    {...this.props}
                    fetch={this.props.myFetch}
                    headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                    {...config}
                    wrappedComponentRef={(me) => this.qnnForm = me}
                    formConfig={[
                        {
                            isInTable: false,
                            field: 'rebarSupermarketId',
                            form: {
                                label: '主键id',
                                field: 'rebarSupermarketId',
                                hide: true,
                            }
                        },
                        {
                            label: "钢筋总需用量",
                            type: "number",
                            field: "rebarRequirementNumber",
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
                                    sm: { span: 15 }
                                }
                            }
                        },
                        {
                            label: "现场累计使用量",
                            type: "number",
                            field: "cumulativeUsageNumber",
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
                        {
                            label: "累计已收料量",
                            type: "number",
                            field: "cumulativeHasBeenReceivingNumber",
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
                        {
                            label: "剩余库存量",
                            type: "number",
                            field: "remainingStockNumber",
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
                        {
                            label: "累计废料总量",
                            type: "number",
                            field: "cumulativeTotalWaste",
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
                                this.props.myFetch('updateZjLzehRebarSupermarket', obj.values).then(
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