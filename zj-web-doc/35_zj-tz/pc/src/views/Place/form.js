import React, { Component } from "react";
import { Form } from "../modules/work-flow";
let config = {
    //流程专属配置   
    workFlowConfig: {
        //后台定的字段
        title: ["", "", '地方政策'],
        apiNameByAdd: "updateZjTzPolicyLocalForFlow",
        apiNameByUpdate: "updateZjTzPolicyLocalForFlow",
        apiNameByGet: "getZjTzPolicyLocalDetails",
        apiTitle: "getZjTzFlowTitle",
        flowId: "zjTzPolicyLocal",

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
                            initialValue: 'zjTzPolicyLocal',
                            hide: true,
                        },
                        {
                            field: 'policyId',
                            type: 'string',
                            placeholder: '请输入',
                            initialValue: flowData.policyId ? flowData.policyId : '',
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
                            label: '登记项目',
                            field: 'projectShortName',
                            type: 'string',
                            qnnDisabled: true,
                            placeholder: '请输入',
                            initialValue: flowData.projectShortName ? flowData.projectShortName : '',
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
                            label: '标题',
                            field: 'title',
                            qnnDisabled: true,
                            initialValue: flowData.title ? flowData.title : '',
                            type: 'string',
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
                            label: '文号',
                            field: 'symbolNo',
                            qnnDisabled: true,
                            initialValue: flowData.symbolNo ? flowData.symbolNo : '',
                            type: 'string',
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
                            label: '所属省份',
                            field: 'provinceId',
                            qnnDisabled: true,
                            initialValue: flowData.provinceId ? flowData.provinceId : '',
                            type: 'select',
                            optionConfig: {
                                label: 'itemName',
                                value: 'itemId'
                            },
                            fetchConfig: {
                                apiName: "getBaseCodeSelect",
                                otherParams: {
                                    itemId: 'xingzhengquhuadaima'
                                }
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
                            label: '系统发布日期',
                            field: 'sysDate',
                            qnnDisabled: true,
                            initialValue: flowData.sysDate ? flowData.sysDate : '',
                            type: 'date',
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
                            label: '发文部门',
                            field: 'departmentName',
                            qnnDisabled: true,
                            initialValue: flowData.departmentName ? flowData.departmentName : '',
                            type: 'string',
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
                            label: '原文生效日期',
                            field: 'effectDate',
                            qnnDisabled: true,
                            initialValue: flowData.effectDate ? flowData.effectDate : '',
                            type: 'date',
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
                            label: '原文发布日期',
                            field: 'releaseDate',
                            qnnDisabled: true,
                            initialValue: flowData.releaseDate ? flowData.releaseDate : '',
                            type: 'date',
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
                            label: '是否有效文件',
                            qnnDisabled: true,
                            initialValue: flowData.effectiveId ? flowData.effectiveId : '',
                            type: 'radio',
                            field: "effectiveId",
                            // initialValue:'1',
                            optionData: [
                                {
                                    label: "否",
                                    value: "0"
                                },
                                {
                                    label: "是",
                                    value: "1"
                                }
                            ],
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
                            label: '分析报告',
                            field: 'report',
                            qnnDisabled: true,
                            initialValue: flowData.report ? flowData.report : '',
                            type: 'textarea',
                            autoSize: {
                                minRows: 2,
                                // maxRows: 10
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
                            field: 'registerUser',
                            type: 'string',
                            qnnDisabled: true,
                            label: '登记用户',
                            required: true,
                            initialValue: flowData.registerUser ? flowData.registerUser : '',
                            placeholder: '请输入',
                            // span: 12,
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
                            field: 'opinionField1',
                            type: 'textarea',
                            // initialValue:flowData.contentDesc ? flowData.contentDesc : '',
                            label: "项目公司意见",
                            required: true,
                            // opinionField: true,
                            // addShow: false,
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
                        }
                    ]}
                    {...this.props.workFlowConfig}
                    {...config.workFlowConfig}
                    fieldsCURD={(fields, flowData, props) => {
                        var fieldsnew = fields.map((item) => {
                            let { field } = item;
                            var url = props.match.url;
                            var myPublic = props.myPublic.appInfo.mainModule;
                            if (url === `${myPublic}TodoHasTo`) {//待办
                                if (field === 'report' || field === 'zjTzFileList' || field === 'effectiveId' || field === 'releaseDate' || field === 'effectDate' || field === 'departmentName' || field === 'sysDate' || field === 'provinceId' || field === 'symbolNo' || field === 'title') {
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
