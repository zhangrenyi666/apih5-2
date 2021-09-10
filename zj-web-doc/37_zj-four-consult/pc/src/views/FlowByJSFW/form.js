import React, { Component } from "react";
import { Form } from "../modules/work-flowold";
let config = {
    //流程专属配置   
    workFlowConfig: {
        //后台定的字段
        title: ["title"], //标题字段
        apiNameByAdd: "updateZjSjConsultTechnicalServiceFlow",
        apiNameByUpdate: "updateZjSjConsultTechnicalServiceFlow",
        apiNameByGet: "getZjSjConsultTechnicalServiceFlowDetails",
        flowId: "zjSjTechnicalService",

        todo: "todoByzjSjTechnicalService",
        hasTodo: "hasTodoByzjSjTechnicalService"
    }
};
class index extends Component {
    render() {
        const { isInQnnTable } = this.props;
        return (
            <div style={{ height: isInQnnTable ? "" : "100vh" }}>
                <Form
                    {...this.props}
                    {...config}
                    upload={this.props.myUpload}
                    formConfig={[
                        {
                            field: 'serviceId',
                            type: 'string',
                            placeholder: '请输入',
                            hide: true,
                        },
                        {
                            field: 'apih5FlowStatus',
                            type: 'string',
                            placeholder: '请输入',
                            hide: true,
                        },
                        {
                            label: '申请人',
                            field: 'userName',
                            type: 'string',
                            qnnDisabled: true,
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
                            label: '申请单位',
                            field: 'deptName',
                            type: 'string',
                            qnnDisabled: true,
                            formatter: function (data) {
                                if(data && data.indexOf(',') !== -1){
                                    data = data.split(',').join('→');
                                }
                                return data;
                            },
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
                            field: 'questionTypeId',
                            label: '服务类型',
                            type: 'cascader',
                            optionConfig: {
                                label: 'itemName',
                                value: 'itemId',
                                children: 'children'
                            },
                            fetchConfig: {
                                apiName: 'getBaseCodeTree',
                                otherParams: {
                                    itemId: 'wenTiLeiXing'
                                }
                            },
                            required: true,
                            qnnDisabled: true,
                            placeholder: '请选择',
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
                            label: '服务标题',
                            field: 'title',
                            type: 'string',
                            required: true,
                            qnnDisabled: true,
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
                            label: '服务内容',
                            field: 'content',
                            type: 'textarea',
                            required: true,
                            qnnDisabled: true,
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
                            label: '处理方式',
                            field: 'onlineAndOffline',
                            type: 'radio',
                            required: true,
                            qnnDisabled: true,
                            hide:true,
                            optionData: [  //可为function (props)=>array
                                {
                                    label: "线上专家处理",
                                    value: "0"
                                },
                                {
                                    label: "线下专家组",
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
                            label: '发布时间',
                            field: 'sendTime',
                            type: 'date',
                            format: 'YYYY-MM-DD HH:mm:ss',
                            required: true,
                            qnnDisabled: true,
                            placeholder: '请选择',
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
                        //     label: '图片',
                        //     field: 'photoList',
                        //     type: 'images',
                        //     accept: 'image/*',
                        //     qnnDisabled: true,
                        //     fetchConfig: {
                        //         apiName: window.configs.domain + 'upload'
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
                        {
                            label: '附件',
                            field: 'attachmentList',
                            type: 'files',
                            qnnDisabled: true,
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
                            type: "textarea",
                            label: "项目总工程师意见",
                            field: "opinionField1",
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
                            label: "局技术中心主办意见",
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
                            label: "专家意见",
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
                            label: "申请人意见",
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
                            label: '技术服务评价表',
                            field: 'evaluationFileList',
                            type: 'files',
                            fetchConfig: {
                                apiName: window.configs.domain + 'upload'
                            },
                            required: true,
                            qnnDisabled: true,
                            hide:true,
                            accept:"image/*,application/pdf",
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
                    actionParamsFormat = {(obj,props) => {
                        let apiBody = JSON.parse(obj.apiBody);
                        let actionData = JSON.parse(obj.actionData);
                        if(obj.nodeVars && obj.nodeVars.deptPmFlag){
                            apiBody.deptPmFlag = obj.nodeVars.deptPmFlag;
                        }
                        if(obj.nodeVars && obj.nodeVars.deptPmFlag === '1' && actionData.operate === 'submit'){
                            apiBody.apih5FlowStatus = '1';
                        }else if(obj.nodeVars && obj.nodeVars.deptPmFlag === '1' && actionData.operate === 'reject'){
                            apiBody.apih5FlowStatus = '3';
                        }else if(obj.nodeVars && obj.nodeVars.deptPmFlag === '2' && actionData.operate === 'submit'){
                            apiBody.apih5FlowStatus = '1';
                        }else if(obj.nodeVars && obj.nodeVars.deptPmFlag === '2' && actionData.operate === 'reject'){
                            apiBody.apih5FlowStatus = '3';
                        }else if(obj.nodeVars && obj.nodeVars.deptPmFlag === '3'){
                            apiBody.apih5FlowStatus = '2';
                        }
                        if(actionData.nextNodes.length && actionData.nextNodes[0].authors && actionData.nextNodes[0].authors.Author && actionData.nextNodes[0].authors.Author.selectAuthorNewApih5.length){
                            apiBody.expertPmList = actionData.nextNodes[0].authors.Author.selectAuthorNewApih5
                        }
                        obj.apiBody = JSON.stringify(apiBody);
                        return obj;
                    }}
                    fieldsCURD = {(obj, flowData, props) => {
                        console.log(flowData);
                        if (flowData.nodeVars && flowData.nodeVars.deptPmFlag && flowData.nodeVars.deptPmFlag === '2') {
                            obj = obj.map((item) => {
                                if (item.field === 'onlineAndOffline') {
                                    item.disabled = false;
                                    item.hide = false;
                                }
                                return item
                            })
                        }
                        if(flowData?.flowNode?.nodeId === 'Node6'){
                            obj = obj.map((item) => {
                                if (item.field === 'evaluationFileList') {
                                    item.disabled = flowData?.flowButtons?.length ? false : true;
                                    item.required = flowData?.flowButtons?.length ? true : false;
                                    item.hide = false;
                                }
                                return item
                            })
                        }
                        if(flowData?.flowNode?.nodeName === '结束'){
                            obj = obj.map((item) => {
                                if (item.field === 'evaluationFileList') {
                                    item.disabled = true;
                                    item.required = false;
                                    item.hide = false;
                                }
                                return item
                            })
                        }
                        return obj
                    }}
                />
            </div>
        );
    }
}
export default index;
