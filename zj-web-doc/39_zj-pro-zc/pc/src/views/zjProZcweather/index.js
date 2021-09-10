import React, { Component } from 'react';
import QnnForm from "../modules/qnn-table/qnn-form";
import { message as Msg, Modal, Spin } from "antd";

const config = {
    //获取数据
    fetchConfig: {
        apiName: 'getZjProZcWeatherDetails'
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
                            label: "气温",
                            type: "number",
                            field: "airTemperature",
                            placeholder: "请输入...",
                            span: 12,
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 24 },
                                    sm: { span: 4 }
                                },
                                wrapperCol: {
                                    xs: { span: 24 },
                                    sm: { span: 18 }
                                }
                            }
                        },
                        {
                            label: "湿度",
                            type: "number",
                            field: "humidity",
                            placeholder: "请输入...",
                            required: true,
                            span: 12,
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 24 },
                                    sm: { span: 4 }
                                },
                                wrapperCol: {
                                    xs: { span: 24 },
                                    sm: { span: 18 }
                                }
                            }
                        },
                        {
                            label: "噪音",
                            type: "number",
                            field: "noise",
                            placeholder: "请输入...",
                            required: true,
                            span: 12,
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 24 },
                                    sm: { span: 4 }
                                },
                                wrapperCol: {
                                    xs: { span: 24 },
                                    sm: { span: 18 }
                                }
                            }
                        },
                        {
                            label: "pm2.5",
                            type: "number",
                            field: "pm2dian5",
                            placeholder: "请输入...",
                            required: true,
                            span: 12,
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 24 },
                                    sm: { span: 4 }
                                },
                                wrapperCol: {
                                    xs: { span: 24 },
                                    sm: { span: 18 }
                                }
                            }
                        },
                        {
                            label: "pm10",
                            type: "number",
                            field: "pm10",
                            placeholder: "请输入...",
                            required: true,
                            span: 12,
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 24 },
                                    sm: { span: 4 }
                                },
                                wrapperCol: {
                                    xs: { span: 24 },
                                    sm: { span: 18 }
                                }
                            }
                        },
                    
                        {
                            label: "风向",
                            type: "string",
                            field: "windDirection",
                            placeholder: "请输入...",
                            required: true,
                            span: 12,
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 24 },
                                    sm: { span: 4 }
                                },
                                wrapperCol: {
                                    xs: { span: 24 },
                                    sm: { span: 18 }
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
                            onClick: (obj) => {
                                this.props.myFetch('updateZjProZcWeather', obj.values).then(
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
                    method={{
                       
                    }}
                />
            </div>
        )
    }
}


export default index