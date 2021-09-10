import React, { Component } from "react";
import { Form } from "../modules/work-flow";
import { message as Msg } from 'antd';
import s from './style.less';
let config = {
    //流程专属配置   
    workFlowConfig: {
        //后台定的字段
        title: ["title"], //标题字段
        apiNameByAdd: "updateZjSjConsultTechnicalServiceFlow",
        apiNameByUpdate: "updateZjSjConsultTechnicalServiceFlow",
        apiNameByGet: "getZjSjConsultTechnicalServiceFlowDetails",

        todo: "todoByzjSjTechnicalService",
        hasTodo: "hasTodoByzjSjTechnicalService"
    }
};
class index extends Component {
    constructor(props){
        super(props);
        this.state = {
            flowData:{},
            noButton:props.noButton || false,
            data:null
        }
    }
    componentDidMount(){
        this.props.myFetch('getZjSjConsultTechnicalServiceFlowDetails',{ serviceId: this.props.initialValues.serviceId }).then(({ success, data, message }) => {
            if (success) {
                this.setState({
                    flowData:data
                })
            } else {
                Msg.error(message);
            }
        })
        this.props.myFetch('getZjSjConsultFlowId', {  }).then(({ success, message, data }) => {
            if (success) {
                this.setState({data:String(data)});
            } else {
                Msg.error(message);
            }
        });
    }
    render() {
        const { flowData, noButton, data } = this.state;
        return (
            <div className={s.root}>
                {data && flowData && flowData.serviceId ? <Form
                    {...this.props}
                    {...config}
                    upload={this.props.myUpload}
                    formConfig={[
                        {
                            field: 'serviceId',
                            type: 'string',
                            initialValue:flowData.serviceId ? flowData.serviceId : '',
                            placeholder: '请输入',
                            hide: true,
                        },
                        {
                            field: 'workId',
                            type: 'string',
                            initialValue:flowData.workId ? flowData.workId : '',
                            placeholder: '请输入',
                            hide: true,
                        },
                        {
                            field: 'apih5FlowStatus',
                            type: 'string',
                            initialValue:flowData.apih5FlowStatus ? flowData.apih5FlowStatus : '',
                            placeholder: '请输入',
                            hide: true,
                        },
                        {
                            label: '申请人',
                            field: 'userName',
                            type: 'string',
                            initialValue:flowData.userName ? flowData.userName : '',
                            qnnDisabled: true,
                            placeholder: '请输入',
                            formItemLayout:{
                                labelCol: {
                                    xs: { span: 3 },
                                    sm: { span: 3 }
                                },
                                wrapperCol: {
                                    xs: { span: 21 },
                                    sm: { span: 21 }
                                }
                            }
                        },
                        {
                            label: '申请单位',
                            field: 'deptName',
                            type: 'string',
                            initialValue:flowData.deptName ? flowData.deptName : '',
                            formatter: function (data) {
                                if(data && data.indexOf(',') !== -1){
                                    data = data.split(',').join('→');
                                }
                                return data;
                            },
                            qnnDisabled: true,
                            placeholder: '请输入',
                            formItemLayout:{
                                labelCol: {
                                    xs: { span: 3 },
                                    sm: { span: 3 }
                                },
                                wrapperCol: {
                                    xs: { span: 21 },
                                    sm: { span: 21 }
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
                            initialValue:flowData.questionTypeId ? flowData.questionTypeId : '',
                            qnnDisabled: noButton,
                            placeholder: '请选择',
                            formItemLayout:{
                                labelCol: {
                                    xs: { span: 3 },
                                    sm: { span: 3 }
                                },
                                wrapperCol: {
                                    xs: { span: 21 },
                                    sm: { span: 21 }
                                }
                            }
                        },
                        {
                            label: '服务标题',
                            field: 'title',
                            type: 'string',
                            required: true,
                            initialValue:flowData.title ? flowData.title : '',
                            qnnDisabled: noButton,
                            placeholder: '请输入',
                            formItemLayout:{
                                labelCol: {
                                    xs: { span: 3 },
                                    sm: { span: 3 }
                                },
                                wrapperCol: {
                                    xs: { span: 21 },
                                    sm: { span: 21 }
                                }
                            }
                        },
                        {
                            label: '服务内容',
                            field: 'content',
                            type: 'textarea',
                            required: true,
                            initialValue:flowData.content ? flowData.content : '',
                            qnnDisabled: noButton,
                            placeholder: '请输入',
                            formItemLayout:{
                                labelCol: {
                                    xs: { span: 3 },
                                    sm: { span: 3 }
                                },
                                wrapperCol: {
                                    xs: { span: 21 },
                                    sm: { span: 21 }
                                }
                            }
                        },
                        {
                            label: '发布时间',
                            field: 'sendTime',
                            type: 'date',
                            format: 'YYYY-MM-DD HH:mm:ss',
                            required: true,
                            initialValue:flowData.sendTime ? flowData.sendTime : '',
                            qnnDisabled: true,
                            placeholder: '请选择',
                            formItemLayout:{
                                labelCol: {
                                    xs: { span: 3 },
                                    sm: { span: 3 }
                                },
                                wrapperCol: {
                                    xs: { span: 21 },
                                    sm: { span: 21 }
                                }
                            }
                        },
                        {
                            label: '附件',
                            field: 'attachmentList',
                            type: 'files',
                            initialValue:flowData.attachmentList ? flowData.attachmentList : [],
                            qnnDisabled: noButton,
                            fetchConfig: {
                                apiName: window.configs.domain + 'upload'
                            },
                            formItemLayout:{
                                labelCol: {
                                    xs: { span: 3 },
                                    sm: { span: 3 }
                                },
                                wrapperCol: {
                                    xs: { span: 21 },
                                    sm: { span: 21 }
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
                            initialValue:flowData.evaluationFileList ? flowData.evaluationFileList : [],
                            qnnDisabled: true,
                            hide:flowData?.evaluationFileList?.length ? false : true,
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 3 },
                                    sm: { span: 3 }
                                },
                                wrapperCol: {
                                    xs: { span: 21 },
                                    sm: { span: 21 }
                                }
                            }
                        }
                    ]}
                    {...this.props.workFlowConfig}
                    flowId={data === 'false' ? 'CopyzjSjTechnicalService' : 'zjSjTechnicalService'}
                    {...config.workFlowConfig}
                /> : null}
            </div>
        );
    }
}
export default index;
