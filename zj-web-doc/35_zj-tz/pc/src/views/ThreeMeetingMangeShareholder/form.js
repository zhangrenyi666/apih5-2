import React, { Component } from "react";
import { Form } from "../modules/work-flow";
let config = {
    //流程专属配置   
    workFlowConfig: {
        //后台定的字段
        title: ["","",'董事会'], 
        apiNameByAdd: "updateZjTzThreeDirectorForFlow",
        apiNameByUpdate: "updateZjTzThreeDirectorForFlow",
        apiNameByGet: "getZjTzThreeDirectorDetails",
        flowId: "zjTzThreeDirector",
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
                            field: 'threeDirectorId',
                            type: 'string',
                            placeholder: '请输入',
                            initialValue: flowData.threeDirectorId ? flowData.threeDirectorId : '',
                            hide: true,
                        },
                        // {
                        //     field: 'projectId',
                        //     type: 'string',
                        //     placeholder: '请输入',
                        //     initialValue: flowData.projectId ? flowData.projectId : '',
                        //     hide: true,
                        // },
                        {
                            field: 'projectId',
                            type: 'select',
                            qnnDisabled: true,
                            label: '项目名称',
                            placeholder: '请输入',
                            initialValue: flowData.projectId ? flowData.projectId : '',
                            optionConfig: {
                                label: 'projectName',
                                value: 'projectId'
                            },
                            fetchConfig: {
                                apiName: "getZjTzProManageList"
                            },
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
                            },
                        },
                        {
                            label: '期次',
                            type: 'select',
                            required:true,
                            field: 'periodId',
                            optionConfig: {
                                label: 'itemName',
                                value: 'itemId'
                            },
                            fetchConfig: {
                                apiName:"getBaseCodeSelect",
                                otherParams: {
                                    itemId: 'huiYiJieCi'
                                }
                            },
                            qnnDisabled: true,
                            placeholder: '请输入',
                            initialValue: flowData.periodId ? flowData.periodId : '',
                            span: 6,
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 24 },
                                    sm: { span: 9 }
                                },
                                wrapperCol: {
                                    xs: { span: 24 },
                                    sm: { span: 15 }
                                }
                            },
                        },
                        {
                            type: 'select',
                            field: 'meetNumberId',
                            qnnDisabled: true,
                            initialValue: flowData.meetNumberId ? flowData.meetNumberId : '',
                            optionConfig: {
                                label: 'itemName',
                                value: 'itemId'
                            },
                            fetchConfig: {
                                apiName: "getBaseCodeSelect",
                                otherParams: {
                                    itemId: 'huiYiQiCi'
                                }
                            },
                            placeholder: '请输入',
                            required: true,
                            span:6,
                                formItemLayout: {
                                    labelCol: {
                                        xs: { span: 0 },
                                        sm: { span: 0 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 24 },
                                        sm: { span: 24 }
                                    }
                                },
                        },
                        {
                            label:'会议时间',
                            type: 'date',
                            qnnDisabled: true,
                            initialValue: flowData.meetDate ? flowData.meetDate : '',
                            field: 'meetDate',
                            placeholder: '请输入',
                            required: true,
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
                            },
                        },
                        {
                            label: '会议类型',
                            type: 'select',
                            qnnDisabled: true,
                            field: 'meetTypeId',
                            initialValue: flowData.meetTypeId ? flowData.meetTypeId : '',
                            optionConfig: {
                                label: 'itemName',
                                value: 'itemId'
                            },
                            fetchConfig: {
                                apiName:"getBaseCodeSelect",
                                otherParams: {
                                    itemId: 'huiYiLeiXing'
                                }
                            },
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
                            },
                        },
                        {
                            label: '会议表决方式',
                            type: 'select',
                            qnnDisabled: true,
                            field: 'meetVoteId',
                            initialValue: flowData.meetVoteId ? flowData.meetVoteId : '',
                            optionConfig: {
                                label: 'itemName',
                                value: 'itemId'
                            },
                            fetchConfig: {
                                apiName:"getBaseCodeSelect",
                                otherParams: {
                                    itemId: 'huiYiBiaoJueFangShi'
                                }
                            },
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
                            },
                        },
                        {
                            label: '原件是否备案',
                            type: 'select',
                            qnnDisabled: true,
                            field: 'originalId',
                            initialValue: flowData.originalId ? flowData.originalId : '',
                            optionData: [
                                {
                                    label:'是',
                                    value:'1'
                                },
                                {
                                    label:'否',
                                    value:'0'
                                }
                            ],
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
                            },
                        },
                        {
                            label: '会议地点',
                            type: 'textarea',
                            initialValue: flowData.meetPlace ? flowData.meetPlace : '',
                            qnnDisabled: true,
                            field: 'meetPlace',
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
                            label: '会议通知文件',
                            qnnDisabled:true,
                            field: 'zjTzFileList1',
                            initialValue: flowData.zjTzFileList1 ? flowData.zjTzFileList1 : '',
                            type: 'files',
                            fetchConfig: {
                                apiName: window.configs.domain + 'upload'
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
                            label: '议案资料',
                            qnnDisabled:true,
                            field: 'zjTzFileList2',
                            initialValue: flowData.zjTzFileList2 ? flowData.zjTzFileList2 : '',
                            type: 'files',
                            fetchConfig: {
                                apiName: window.configs.domain + 'upload'
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
                            label: '决议及会议纪要',
                            qnnDisabled:true,
                            field: 'zjTzFileList3',
                            initialValue: flowData.zjTzFileList3 ? flowData.zjTzFileList3 : '',
                            type: 'files',
                            fetchConfig: {
                                apiName: window.configs.domain + 'upload'
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
                            field: 'opinionField1',
                            type: 'textarea',
                            initialValue:flowData.contentDesc ? flowData.contentDesc : '',
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
                    fieldsCURD={(fields, flowData, props) => {
                        var fieldsnew = fields.map((item) => {
                            let { field } = item;
                            var url = props.match.url;
                            var myPublic = props.myPublic.appInfo.mainModule;
                            if (url === `${myPublic}TodoHasTo`){//待办
                                if(field === 'meetDate' || field === 'meetTypeId' ||　field === 'meetVoteId' ||　field === 'originalId' ||　field === 'meetPlace' ||　field === 'zjTzFileList1' ||　field === 'zjTzFileList2' ||　field === 'zjTzFileList3'){
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
