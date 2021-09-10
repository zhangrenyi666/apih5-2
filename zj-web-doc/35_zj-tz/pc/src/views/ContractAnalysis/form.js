import React, { Component } from "react";
import { Form } from "../modules/work-flow";
let config = {
    //流程专属配置   
    workFlowConfig: {
        //后台定的字段
        title: ["", "", 'PPP合同分析'],
        apiNameByAdd: "updateZjTzPppTermForFlow",
        apiNameByUpdate: "updateZjTzPppTermForFlow",
        apiNameByGet: "getZjTzPppTermDetails",
        apiTitle: "getZjTzFlowTitle",
        flowId: "zjTzPppTerm",
        todo: "TodoHasTo",
        hasTodo: "TodoHasToq"
    }
};
class index extends Component {
    componentDidMount() { }
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
                            initialValue: 'zjTzPppTerm',
                            hide: true,
                        },
                        {
                            field: 'pppTermId',
                            type: 'string',
                            placeholder: '请输入',
                            initialValue: flowData.pppTermId ? flowData.pppTermId : '',
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
                            required: true,
                            label: '项目名称',
                            placeholder: '请输入',
                            initialValue: flowData.projectName ? flowData.projectName : '',
                            formItemLayoutForm: {
                                labelCol: {
                                    xs: { span: 24 },
                                    sm: { span: 3 }
                                },
                                wrapperCol: {
                                    xs: { span: 24 },
                                    sm: { span: 21 }
                                }
                            },
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 24 },
                                    sm: { span: 3 }
                                },
                                wrapperCol: {
                                    xs: { span: 24 },
                                    sm: { span: 21 }
                                }
                            },
                        },
                        {
                            type: "string",
                            label: "",
                            field: "subprojectInfoId",
                            initialValue: flowData.subprojectInfoId ? flowData.subprojectInfoId : '',
                            placeholder: "请输入",
                            hide: true
                        },
                        {
                            type: "string",
                            label: "子项目名称",
                            field: "subprojectInfoName",
                            initialValue: flowData.subprojectInfoName ? flowData.subprojectInfoName : '',
                            placeholder: "请输入",
                            qnnDisabled: true,
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 24 },
                                    sm: { span: 3 }
                                },
                                wrapperCol: {
                                    xs: { span: 24 },
                                    sm: { span: 21 }
                                }
                            },
                        },
                        {
                            label: '分析期次',
                            type: 'select',
                            field: 'issueId',
                            initialValue: flowData.issueId ? flowData.issueId : '',
                            placeholder: "请输入",
                            qnnDisabled: true,
                            optionConfig: {
                                label: 'itemName',
                                value: 'itemId'
                            },
                            fetchConfig: {
                                apiName: "getBaseCodeSelect",
                                otherParams: {
                                    itemId: 'fenXiQiCi'
                                }
                            },
                            required: true,
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 24 },
                                    sm: { span: 3 }
                                },
                                wrapperCol: {
                                    xs: { span: 24 },
                                    sm: { span: 21 }
                                }
                            },
                        },
                        {
                            label: '附件',
                            field: 'zjTzFileList',
                            initialValue: flowData.zjTzFileList ? flowData.zjTzFileList : '',
                            qnnDisabled: true,
                            type: 'files',
                            fetchConfig: {
                                apiName: window.configs.domain + 'upload',
                                otherParams: {
                                    name: 'PPP合同分析'
                                }
                            },
                            showDownloadIcon: true,//是否显示下载按钮
                            onPreview: "bind:_docFilesByOfficeUrl",//365显示
                            
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 24 },
                                    sm: { span: 3 }
                                },
                                wrapperCol: {
                                    xs: { span: 24 },
                                    sm: { span: 21 }
                                }
                            },
                        },
                        {
                            field: 'registerDate',
                            type: 'date',
                            label: '登记日期',
                            required: true,
                            initialValue: flowData.registerDate ? flowData.registerDate : '',
                            qnnDisabled: true,
                            placeholder: '请输入',
                            span: 12,
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 24 },
                                    sm: { span: 6 }
                                },
                                wrapperCol: {
                                    xs: { span: 24 },
                                    sm: { span: 18 }
                                }
                            }
                        },
                        {
                            field: 'registerPerson',
                            type: 'string',
                            label: '登记用户',
                            required: true,
                            initialValue: flowData.registerPerson ? flowData.registerPerson : '',
                            qnnDisabled: true,
                            placeholder: '请输入',
                            span: 12,
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 24 },
                                    sm: { span: 6 }
                                },
                                wrapperCol: {
                                    xs: { span: 24 },
                                    sm: { span: 18 }
                                }
                            }
                        },
                        {
                            field: 'opinionField1',
                            type: 'textarea',
                            initialValue: flowData.opinionField1 ? flowData.opinionField1 : '',
                            label: "项目公司意见",
                            required: true,
                            placeholder: '请输入',
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 24 },
                                    sm: { span: 3 }
                                },
                                wrapperCol: {
                                    xs: { span: 24 },
                                    sm: { span: 21 }
                                }
                            },
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
                                    sm: { span: 3 }
                                },
                                wrapperCol: {
                                    xs: { span: 24 },
                                    sm: { span: 21 }
                                }
                            },
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
                                    sm: { span: 3 }
                                },
                                wrapperCol: {
                                    xs: { span: 24 },
                                    sm: { span: 21 }
                                }
                            },
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
                                    sm: { span: 3 }
                                },
                                wrapperCol: {
                                    xs: { span: 24 },
                                    sm: { span: 21 }
                                }
                            },
                        }
                    ]}
                    {...this.props.workFlowConfig}
                    {...config.workFlowConfig}
                />
            </div>
        );
    }
}
export default index;
