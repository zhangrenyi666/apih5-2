import React, { Component } from "react";
import { Form } from "../modules/work-flow";
let config = {
    //流程专属配置   
    workFlowConfig: {
        //后台定的字段
        title: ["applicationName", "立案"], //标题字段
        apiNameByAdd: "updateZjLabourUnionFlowApplicationFirstByFlow",
        apiNameByUpdate: "updateZjLabourUnionFlowApplicationSecondByFlow",
        apiNameByGet: "getZjLabourUnionFlowApplicationDetailsByFlow",
        flowId: "zjLabourUnionFlowApplication",

        todo: "FlowByApplicationAwait",
        hasTodo: "FlowByApplicationOver"
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
                    formConfig={[
                        {
                            field: 'applicationId',
                            type: 'string',
                            placeholder: '请输入',
                            hide: true
                        },
                        {
                            label:'立案审批申请名称',
                            field: 'applicationName',
                            type: 'string',
                            placeholder: '请输入',
                            required: true,
                            qnnDisabled:true,
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
                            label:'立案审批申请名称',
                            field: 'applicationContent',
                            type: 'textarea',
                            qnnDisabled:true,
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
                            label: "部门领导意见",
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
                            label: "工会主席意见",
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
                        }
                    ]}
                    {...this.props.workFlowConfig}
                    {...config.workFlowConfig}
                    // fieldsCURD={(fields, flowData, props) => {
                    //     return fields.map((item) => {
                    //         let { field } = item;
                    //         if (field === 'applicationName' || field === 'applicationContent') {
                    //             item.disabled = false;
                    //         }
                    //         return item;
                    //     });
                    // }}
                />
            </div>
        );
    }
}
export default index;
