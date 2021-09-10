import React, { Component } from "react";
import { Form } from "../modules/work-flow";
let config = {
    //流程专属配置   
    workFlowConfig: {
        //后台定的字段
        title: ["", "registerPerson", '生命周期策划变更'],
        apiNameByAdd: "updateZjTzLifeCycleChangeForFlow",
        apiNameByUpdate: "updateZjTzLifeCycleChangeForFlow",
        apiNameByGet: "getZjTzLifeCycleChangeDetails",
        apiTitle: "getZjTzFlowTitle",
        // flowId: "zjTzLifeCycleChange",

        todo: "TodoHasTo",
        hasTodo: "TodoHasToq"
    }
};
class index extends Component {
    render() {
        const { isInQnnTable } = this.props;
        const { flowData = {} } = this.props;
        const { ext1 } = this.props.loginAndLogoutInfo.loginInfo.userInfo;
        let zjTzLifeCycleChange = '';
        if (ext1 === '3') {
            zjTzLifeCycleChange = 'zjTzLifeCycleChange3';
        }
        if (ext1 === '4') {
            zjTzLifeCycleChange = 'zjTzLifeCycleChange';
        }
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
                            initialValue: zjTzLifeCycleChange,
                            hide: true,
                        },
                        {
                            field: 'lifeCycleChangeId',
                            type: 'string',
                            placeholder: '请输入',
                            initialValue: flowData.lifeCycleChangeId ? flowData.lifeCycleChangeId : '',
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
                            label: '备注',
                            field: 'remark',
                            qnnDisabled: true,
                            initialValue: flowData.remark ? flowData.remark : '',
                            type: 'textarea',
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
                            qnnDisabled: true,
                            field: 'zjTzFileList',
                            initialValue: flowData.zjTzFileList ? flowData.zjTzFileList : '',
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
                            label: "托管公司意见",
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
                            label: "投资事业部",
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
                            label: "各部门评审意见",
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
                            field: 'zjTzFileOpinionList',
                            hide: true,
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
                    flowId={zjTzLifeCycleChange}
                    fieldsCURD={(fields, flowData, props) => {
                        var fieldsnew = fields.map((item) => {
                            let { field } = item;
                            var url = props.match.url;
                            var myPublic = props.myPublic.appInfo.mainModule;
                            if (field === 'zjTzFileOpinionList') {
                                if (flowData.flowNode.nodeId === 'Node5') {
                                    item.hide = false;
                                    item.required = false;
                                    item.disabled = false;
                                } else {
                                    item.hide = true;
                                }
                            } else if (url === `${myPublic}TodoHasTo`) {//待办
                                if (field === 'remark' || field === 'zjTzFileList') {
                                    item.disabled = false;
                                } else {

                                }
                            }

                            return item;
                        });
                        return fieldsnew
                    }}
                />
            </div>
        );
    }
}
export default index;
