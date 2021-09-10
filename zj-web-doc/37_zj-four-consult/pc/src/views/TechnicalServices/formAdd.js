import React, { Component } from "react";
import { Form } from "../modules/work-flow";
import s from './style.less';
import { message as Msg } from 'antd';
let config = {
    //流程专属配置   
    workFlowConfig: {
        //后台定的字段
        title: ["title"], //标题字段
        apiNameByAdd: "addZjSjConsultTechnicalServiceFlow",
        apiNameByUpdate: "updateZjSjConsultTechnicalServiceFlow",
        apiNameByGet: "getZjSjConsultTechnicalServiceFlowDetails",

        todo: "todoByzjSjTechnicalService",
        hasTodo: "hasTodoByzjSjTechnicalService"
    }
};
class index extends Component {
    state = {
        data:null
    }
    componentDidMount(){
        this.props.myFetch('getZjSjConsultFlowId', {  }).then(({ success, message, data }) => {
            if (success) {
                this.setState({data:String(data)});
            } else {
                Msg.error(message);
            }
        });
    }
    render() {
        const { companyId, companyName } = this.props.loginAndLogoutInfo.loginInfo.userInfo.companyList[0];
        const { data } = this.state;
        return (
            <div className={s.root}>
                {data ? <Form
                    {...this.props}
                    {...config}
                    upload={this.props.myUpload}
                    formConfig={[
                        {
                            field: 'deptId',
                            type: 'string',
                            initialValue: companyId,
                            placeholder: '请输入',
                            hide: true,
                        },
                        {
                            field: 'deptName',
                            type: 'string',
                            initialValue: companyName,
                            placeholder: '请输入',
                            hide: true,
                        },
                        {
                            field: 'serviceId',
                            type: 'string',
                            placeholder: '请输入',
                            hide: true,
                        },
                        {
                            field: 'workId',
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
                            placeholder: '请选择',
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
                        },
                        {
                            label: '服务标题',
                            field: 'title',
                            type: 'string',
                            required: true,
                            placeholder: '请输入',
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
                        },
                        {
                            label: '服务内容',
                            field: 'content',
                            type: 'textarea',
                            required: true,
                            placeholder: '请输入',
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
                        },
                        {
                            label: '发布时间',
                            field: 'sendTime',
                            type: 'date',
                            format: 'YYYY-MM-DD HH:mm:ss',
                            required: true,
                            qnnDisabled: true,
                            initialValue: new Date(),
                            placeholder: '请选择',
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
                        },
                        {
                            label: '附件',
                            field: 'attachmentList',
                            type: 'files',
                            fetchConfig: {
                                apiName: window.configs.domain + 'upload'
                            },
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
                    flowId={data === 'false' ? 'CopyzjSjTechnicalService' : 'zjSjTechnicalService'}
                    {...config.workFlowConfig}
                /> : null}
            </div>
        );
    }
}
export default index;
