import React, { Component } from "react";
import { Form } from "../modules/work-flow";
let config = {
    //流程专属配置   
    workFlowConfig: {
        //后台定的字段
        title: ["", "registerPerson", '资产负债情况表'],
        apiNameByAdd: "updateZjTzDebt",
        apiNameByUpdate: "updateZjTzDebt",
        apiNameByGet: "getZjTzDebtDetails",
        apiTitle: "getZjTzFlowTitle",
        flowId: "zjTzDebt",
        todo: "TodoHasTo",
        hasTodo: "TodoHasToq"
    }
};
class index extends Component {
    render() {
        const { isInQnnTable } = this.props;
        const { flowData = {} } = this.props;
        return (
            <div style={{ height: isInQnnTable ? "" : "100vh" }}>
                <Form
                    {...this.props}
                    {...config}
                    formConfig={[
                        {
                            field: 'flowId',
                            type: 'string',
                            placeholder: '请输入',
                            initialValue: 'zjTzDebt',
                            hide: true,
                        },
                        {
                            field: 'debtId',
                            type: 'string',
                            placeholder: '请输入',
                            initialValue: flowData.debtId ? flowData.debtId : '',
                            hide: true,
                        },
                        {
                            field: 'projectId',
                            type: 'string',
                            placeholder: '请输入',
                            initialValue: flowData.projectId ? flowData.projectId : '',
                            hide: true,
                        },
                        {
                            field: 'projectName',
                            type: 'string',
                            qnnDisabled: true,
                            label: '项目名称',
                            placeholder: '请输入',
                            initialValue: flowData.projectName ? flowData.projectName : '',
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
                            label: '年月',
                            type: 'month',
                            qnnDisabled: true,
                            initialValue: flowData.yearMonthDate ? flowData.yearMonthDate : '',
                            field: 'yearMonthDate',
                            span: 12,
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 24 },
                                    sm: { span: 8 }
                                },
                                wrapperCol: {
                                    xs: { span: 24 },
                                    sm: { span: 16 }
                                }
                            }
                        },
                        {
                            field: 'registerDate',
                            type: 'date',
                            label: '登记日期',
                            required: true,
                            qnnDisabled: true,
                            initialValue: flowData.registerDate ? flowData.registerDate : '',
                            placeholder: '请输入',
                            span: 12,
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 24 },
                                    sm: { span: 8 }
                                },
                                wrapperCol: {
                                    xs: { span: 24 },
                                    sm: { span: 16 }
                                }
                            }
                        },
                        {
                            field: 'registerPerson',
                            type: 'string',
                            qnnDisabled: true,
                            label: '登记用户',
                            required: true,
                            initialValue: flowData.registerPerson ? flowData.registerPerson : '',
                            placeholder: '请输入',
                            span: 12,
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 24 },
                                    sm: { span: 8 }
                                },
                                wrapperCol: {
                                    xs: { span: 24 },
                                    sm: { span: 16 }
                                }
                            }
                        },
                        {
                            field: 'opinionField1',
                            type: 'textarea',
                            initialValue: flowData.contentDesc ? flowData.contentDesc : '',
                            label: "项目公司意见",
                            required: true,
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
                            label: "托管公司",
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
                            label: "投资事业部审核",
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
                            label: "局各部门评审",
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
                            label: '附件',
                            hide: true,
                            field: 'zjTzFileOpinionList',
                            type: 'files',
                            showDownloadIcon: true,//是否显示下载按钮
                            onPreview: "bind:_docFilesByOfficeUrl",//365显示
                            
                            fetchConfig: {
                                apiName: window.configs.domain + 'upload'
                            },
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
                    ]}
                    {...this.props.workFlowConfig}
                    {...config.workFlowConfig}
                />
            </div>
        );
    }
}
export default index;
