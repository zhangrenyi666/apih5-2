import React, { Component } from 'react';
import QnnForm from "../modules/qnn-table/qnn-form";
import { message as Msg, Modal, Spin } from "antd";

const config = {
    //获取数据
    fetchConfig: {
        apiName: 'getZjProZcTbeamAdministrationDetails'
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
                            label: "梁场名称",
                            type: "string",
                            field: "beamFactoryName",
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
                            label: "所属标段",
                            type: "string",
                            field: "bidSection",
                            placeholder: "请输入...",
                            required: true,
                            span: 12,
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 24 },
                                    sm: { span: 3 }
                                },
                                wrapperCol: {
                                    xs: { span: 24 },
                                    sm: { span: 18 }
                                }
                            }
                        },
                        {
                            label: "工程名称",
                            type: "string",
                            field: "projectName",
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
                            label: "监理单位",
                            type: "string",
                            field: "constructionControlUnit",
                            placeholder: "请输入...",
                            required: true,
                            span: 12,
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 24 },
                                    sm: { span: 3 }
                                },
                                wrapperCol: {
                                    xs: { span: 24 },
                                    sm: { span: 18 }
                                }
                            }
                        },
                        {
                            label: "施工单位",
                            type: "string",
                            field: "constructionUnit",
                            placeholder: "请输入...",
                            required: true,
                            span: 24,
                            style: {
                                right: 2
                            },
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 24 },
                                    sm: { span: 2 }
                                },
                                wrapperCol: {
                                    xs: { span: 24 },
                                    sm: { span: 15 }
                                }
                            }
                        },
                        {
                            label: "梁场设计量",
                            type: "integer",
                            field: "beamFactoryDesignQuantity",
                            placeholder: "请输入...",
                            required: false,
                            span: 12,
                            min: 0,
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
                            label: "预制完成量",
                            type: "integer",
                            field: "beamFactoryDesignQuantityComplete",
                            placeholder: "请输入...",
                            required: false,
                            span: 9,
                            min: 0,
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 24 },
                                    sm: { span: 4 }
                                },
                                wrapperCol: {
                                    xs: { span: 24 },
                                    sm: { span: 21 }
                                }
                            }
                        },
                        {
                            type:'component',
                            field: 'diy1', 
                            span:3,
                            Component: (obj) => {
                                return (
                                    <button
                                        className='ant-btn ant-btn-primary default_qnnFormItem__24_PO'
                                        onClick={() => {
                                            this.props.myFetch('getZjProZcTbeamPrefabrication').then(({ success, data, message }) => {
                                                if (success) {
                                                    obj.form.setFieldsValue({beamFactoryDesignQuantityComplete:data.prefabricationNumber})
                                                } else {
                                                    Msg.error(message)
                                                }
                                            })
                                        }}
                                    >
                                        取值
                                    </button>
                                );
                            },
                        },
                        {
                            label: "架设设计量",
                            type: "integer",
                            field: "erectionDesignQuantity",
                            placeholder: "请输入...",
                            required: false,
                            span: 12,
                            min: 0,
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
                            label: "架设完成量",
                            type: "integer",
                            field: "erectionDesignQuantityComplete",
                            placeholder: "请输入...",
                            required: false,
                            span: 9,
                            min: 0,
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 24 },
                                    sm: { span: 4 }
                                },
                                wrapperCol: {
                                    xs: { span: 24 },
                                    sm: { span: 21 }
                                }
                            }
                        },
                        {
                            type:'component',
                            field: 'diy2', 
                            span:3,
                            Component: (obj) => {
                                return (
                                    <button
                                        className='ant-btn ant-btn-primary default_qnnFormItem__24_PO'
                                        onClick={() => {
                                            this.props.myFetch('getZjProZcTbeamErection').then(({ success, data, message }) => {
                                                if (success) {
                                                    obj.form.setFieldsValue({erectionDesignQuantityComplete:data.erectionNumber})
                                                } else {
                                                    Msg.error(message)
                                                }
                                            })
                                            
                                        }}
                                    >
                                        取值
                                    </button>
                                );
                            },
                        },
                        {
                            label: "护栏工程量",
                            type: "integer",
                            field: "quantityOfGuardrail",
                            placeholder: "请输入...",
                            required: false,
                            span: 12,
                            min: 0,
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
                            label: "护栏完成量",
                            type: "integer",
                            field: "quantityOfGuardrailComplete",
                            placeholder: "请输入...",
                            required: false,
                            span: 12,
                            min: 0,
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 24 },
                                    sm: { span: 3 }
                                },
                                wrapperCol: {
                                    xs: { span: 24 },
                                    sm: { span: 18 }
                                }
                            }
                        },
                        {
                            label: "铺装工程量",
                            type: "integer",
                            field: "pavementQuantity",
                            placeholder: "请输入...",
                            required: false,
                            span: 12,
                            min: 0,
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
                            label: "铺装完成量",
                            type: "integer",
                            field: "pavementQuantityComplete",
                            placeholder: "请输入...",
                            required: false,
                            span: 12,
                            min: 0,
                            // onChange:"bind:numberChange",
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 24 },
                                    sm: { span: 3 }
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
                                this.props.myFetch('updateZjProZcTbeamAdministration', obj.values).then(
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
                        // numberChange: (args,obj) => {
                        //     console.log(args)
                        //     var str = obj.id.split("_")
                        //     var str1 = "#"+str[1].replace("Complete","")
                        //     console.log(str1)
                        //     console.log($("#pavementQuantity").attr("value"))
                            
                        // },
                    
                    }}
                />
            </div>
        )
    }
}


export default index