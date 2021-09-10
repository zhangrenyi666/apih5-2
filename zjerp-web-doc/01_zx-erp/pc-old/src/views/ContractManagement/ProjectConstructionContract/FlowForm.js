import React, { Component } from "react";
import { Form } from "apih5/modules/work-flow";
import tabOne from "./config/tabOne"
const config = {
    //流程专属配置   
    workFlowConfig: {
        //后台定的字段
        title: ["工程施工合同", 'contractName', 'contractNo'], //标题字段
        apiNameByAdd: "psUpdateZxGcsgCtContrApplyFirstByFlow",
        apiNameByUpdate: "psUpdateZxGcsgCtContrApplySecondByFlow",
        apiNameByGet: "psGetZxGcsgCtContrApplyDetailsByFlow",
        todo: "TodoList",
        hasTodo: "HasTodoList",
        flowId: "psZxGcsgCtContrApply"
    },
};
const formItemLayout = {
    labelCol: {
        sm: { span: 9 }
    },
    wrapperCol: {
        sm: { span: 15 }
    }
}

class index extends Component {
    render() {
        const { isInQnnTable, flowFormData = {} } = this.props;
        return (
            <div style={{ height: isInQnnTable ? "" : "100vh" }}>
                <Form
                    {...this.props}
                    {...config}
                    formTailLayout={{
                        wrapperCol: {
                            offset: 3
                        }
                    }}
                    upload={this.props.myUpload}
                    method={{
                        firstIDOtherParams: () => { }
                    }}
                    formConfig={[
                        {
                            field: 'flowId',
                            type: 'string',
                            initialValue: 'zxCtOtherSupplyAgreementReviewAp',
                            hide: true,
                        },

                        ...tabOne.formConfig.map(item => {
                            return {
                                formItemLayout,
                                ...item,
                                qnnDisabled: true,
                                required: false,
                                initialValue: flowFormData[item.field]
                            }
                        }),

                        {
                            label: "项目经营负责",
                            field: 'opinionField1',
                            type: 'textarea',
                            opinionField: true,
                            addShow: false,
                            placeholder: '请输入',
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 24 },
                                    sm: { span: 4 }
                                },
                                wrapperCol: {
                                    xs: { span: 24 },
                                    sm: { span: 20 }
                                }
                            }
                        },
                        {
                            type: "textarea",
                            label: "项目总经济师",
                            field: "opinionField2",
                            opinionField: true,
                            addShow: false,
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 24 },
                                    sm: { span: 4 }
                                },
                                wrapperCol: {
                                    xs: { span: 24 },
                                    sm: { span: 20 }
                                }
                            }
                        },
                        {
                            type: "textarea",
                            label: "项目经理",
                            field: "opinionField3",
                            opinionField: true,
                            addShow: false,
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 24 },
                                    sm: { span: 4 }
                                },
                                wrapperCol: {
                                    xs: { span: 24 },
                                    sm: { span: 20 }
                                }
                            }
                        },
                        {
                            type: "textarea",
                            label: "上报总部",
                            field: "opinionField4",
                            opinionField: true,
                            addShow: false,
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 24 },
                                    sm: { span: 4 }
                                },
                                wrapperCol: {
                                    xs: { span: 24 },
                                    sm: { span: 20 }
                                }
                            }
                        },
                        {
                            type: "textarea",
                            label: "上报总部(托管)",
                            field: "opinionField5",
                            opinionField: true,
                            addShow: false,
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 24 },
                                    sm: { span: 4 }
                                },
                                wrapperCol: {
                                    xs: { span: 24 },
                                    sm: { span: 20 }
                                }
                            }
                        },
                        {
                            type: "textarea",
                            label: "公司审核人",
                            field: "opinionField6",
                            opinionField: true,
                            addShow: false,
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 24 },
                                    sm: { span: 4 }
                                },
                                wrapperCol: {
                                    xs: { span: 24 },
                                    sm: { span: 20 }
                                }
                            }
                        },
                        {
                            type: "textarea",
                            label: "公司经营负责",
                            field: "opinionField7",
                            opinionField: true,
                            addShow: false,
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 24 },
                                    sm: { span: 4 }
                                },
                                wrapperCol: {
                                    xs: { span: 24 },
                                    sm: { span: 20 }
                                }
                            }
                        },
                        {
                            type: "textarea",
                            label: "公司总经济师",
                            field: "opinionField8",
                            opinionField: true,
                            addShow: false,
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 24 },
                                    sm: { span: 4 }
                                },
                                wrapperCol: {
                                    xs: { span: 24 },
                                    sm: { span: 20 }
                                }
                            }
                        },
                        {
                            type: "textarea",
                            label: "校对",
                            field: "opinionField9",
                            opinionField: true,
                            addShow: false,
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 24 },
                                    sm: { span: 4 }
                                },
                                wrapperCol: {
                                    xs: { span: 24 },
                                    sm: { span: 20 }
                                }
                            }
                        },

                        // {
                        //     label: '附件',
                        //     field: 'zxErpFileList',
                        //     type: 'files',
                        //     qnnDisabled: true,
                        //     initialValue: flowFormData?.zxErpFileList,
                        //     fetchConfig: {
                        //         apiName: 'upload'
                        //     },
                        //     formItemLayout: {
                        //         labelCol: {
                        //             xs: { span: 24 },
                        //             sm: { span: 4 }
                        //         },
                        //         wrapperCol: {
                        //             xs: { span: 24 },
                        //             sm: { span: 20 }
                        //         }
                        //     }
                        // },
                    ]}
                    {...config.workFlowConfig}
                    isHaveDoc={true}
                    docFieldLable={"公文正文"} //label
                    docFieldName={"docAttachmentList"} //field
                    docFieldIsRequired={true} //是否必填默认true
                    docFormFormItemLayout={{
                        labelCol: {
                            xs: { span: 24 },
                            sm: { span: 3 }
                        },
                        wrapperCol: {
                            xs: { span: 24 },
                            sm: { span: 21 }
                        }
                    }}
                />
            </div>
        );
    }
}
export default index;
