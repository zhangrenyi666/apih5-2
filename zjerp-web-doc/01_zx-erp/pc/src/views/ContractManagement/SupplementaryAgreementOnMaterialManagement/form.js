import React, { Component } from "react";
import { Form } from "../../modules/work-flow";
import QnnForm from "../../modules/qnn-table/qnn-form";
import { Modal, Button } from 'antd';
let config = {
    //流程专属配置   
    editDocCdnAddress: window.configs.ntkoAddress,
    workFlowConfig: {
        //后台定的字段
        title: ["物资补充协议", "pp3", "contractNo"], //标题字段
        apiNameByAdd: "addZxCtSuppliesContrReplenishAgreementFlow",
        apiNameByUpdate: "updateZxCtSuppliesContrReplenishAgreementFlow",
        apiNameByGet: "getZxCtSuppliesContrReplenishFlowDetail",
        flowId: 'ZxCtSuppliesContrReplenish',
        todo: "TodoList",
        hasTodo: "HasTodoList"
    },
    parameterName: 'orgID',
    isHaveDoc: true,
    docFieldLable: "公文正文",
    docFieldName: "documentFileList",
    docFieldIsRequired: true,
    docIsReadOnly: false,
    docFormFormItemLayout: {
        labelCol: {
            xs: { span: 4 },
            sm: { span: 4 }
        },
        wrapperCol: {
            xs: { span: 20 },
            sm: { span: 20 }
        }
    },
    //切换布局
    formLayout: "leftDoc",
    //是否使用描述式表单
    formType: "descriptions",
    descriptionsConfig: {
        "layout": "horizontal",
        "column": 12,
        size: "small"
    },
    //左侧公文附件字段名字  
    filesFieldName: "replenishAgreementFileList",
    //请求左侧待办已办列表的ajax配置
    //@curList 当前处于什么列表 todo(待办)  hasTodo(已办)
    
  getTodoDataFetchConfig: window.qnnGetTodoDataFetchConfig,
};

class index extends Component {
    constructor(props) {
        super(props);
        this.state = {
            visible: false,
            ModalData: props?.flowData || null
        }
    }
    handleCancel = () => {
        this.setState({ visible: false });
    }
    render() {
        const { isInQnnTable, flowData } = this.props;
        const { visible, ModalData } = this.state;
        return (
            <div style={{ height: isInQnnTable ? "" : "100vh" }}>
                <Form
                    {...this.props}
                    {...config}
                    upload={this.props.myUpload}
                    formConfig={[
                        {
                            field: 'workId',
                            type: 'string',
                            initialValue: flowData?.workId,
                            hide: true,
                        },
                        {
                            field: 'comID',
                            type: 'string',
                            initialValue: flowData?.comID,
                            hide: true,
                        },
                        {
                            field: 'comName',
                            type: 'string',
                            initialValue: flowData?.comName,
                            hide: true,
                        },
                        {
                            field: 'orgID',
                            type: 'string',
                            initialValue: flowData?.orgID,
                            hide: true,
                        },
                        {
                            field: 'orgName',
                            type: 'string',
                            initialValue: flowData?.orgName,
                            hide: true,
                        },
                        {
                            field: 'replenishAgreementId',
                            type: 'string',
                            initialValue: flowData?.replenishAgreementId,
                            hide: true,
                        },
                        {
                            field: 'apih5FlowStatus',
                            type: 'string',
                            initialValue: flowData?.apih5FlowStatus,
                            hide: true,
                        },
                        {
                            field: 'zxCtSuppliesContractChangeId',
                            type: 'string',
                            initialValue: flowData?.zxCtSuppliesContractChangeId,
                            hide: true,
                        },
                        {
                            field: 'pp6',
                            type: 'string',
                            initialValue: flowData?.pp6,
                            hide: true,
                        },
                        {
                            field: 'beginPer',
                            type: 'string',
                            initialValue: flowData?.beginPer,
                            hide: true,
                        },
                        {
                            label: '合同编号',
                            field: "contractNo",
                            type: 'string',
                            qnnDisabled: true,
                            initialValue: flowData?.contractNo,
                            placeholder: '请输入',
                            span: 12,
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 8 },
                                    sm: { span: 8 }
                                },
                                wrapperCol: {
                                    xs: { span: 16 },
                                    sm: { span: 16 }
                                }
                            }
                        },
                        {
                            label: '补充协议名称',
                            field: "pp3",
                            type: 'string',
                            required: true,
                            qnnDisabled: true,
                            hide: true,
                            initialValue: flowData?.pp3,
                            placeholder: '请输入',
                            span: 12,
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 8 },
                                    sm: { span: 8 }
                                },
                                wrapperCol: {
                                    xs: { span: 16 },
                                    sm: { span: 16 }
                                }
                            }
                        },
                        {
                            label: '甲方名称',
                            field: 'firstName',
                            type: 'string',
                            initialValue: flowData?.firstName,
                            qnnDisabled: true,
                            span: 12,
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 8 },
                                    sm: { span: 8 }
                                },
                                wrapperCol: {
                                    xs: { span: 16 },
                                    sm: { span: 16 }
                                }
                            }
                        },
                        {
                            label: '甲方名称',
                            field: "firstID",
                            initialValue: flowData?.firstID,
                            type: 'string',
                            hide: true
                        },
                        {
                            label: '乙方名称',
                            field: 'secondName',
                            type: 'string',
                            initialValue: flowData?.secondName,
                            qnnDisabled: true,
                            span: 12,
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 8 },
                                    sm: { span: 8 }
                                },
                                wrapperCol: {
                                    xs: { span: 16 },
                                    sm: { span: 16 }
                                }
                            }
                        },
                        {
                            label: '乙方名称',
                            field: "secondID",
                            type: 'string',
                            hide: true,
                            initialValue: flowData?.secondID
                        },
                        {
                            label: '合同名称',
                            field: "contractName",
                            type: 'selectByPaging',
                            required: true,
                            optionConfig: {
                                label: 'contractName',
                                value: 'contractName'
                            },
                            fetchConfig: {
                                apiName: 'getZxCtSuppliesContractList'
                            },
                            condition: [
                                {
                                    regex: { replenishAgreementId: ['!', /(''|null|undefined)/ig] },
                                    action: 'disabled',
                                }
                            ],
                            placeholder: '请选择',
                            qnnDisabled: true,
                            initialValue: flowData?.contractName,
                            span: 12,
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 8 },
                                    sm: { span: 8 }
                                },
                                wrapperCol: {
                                    xs: { span: 16 },
                                    sm: { span: 16 }
                                }
                            }
                        },
                        {
                            label: '合同签订人',
                            field: "agent",
                            type: 'string',
                            placeholder: '请输入',
                            initialValue: flowData?.agent,
                            qnnDisabled: true,
                            hide: true,
                            span: 12,
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 8 },
                                    sm: { span: 8 }
                                },
                                wrapperCol: {
                                    xs: { span: 16 },
                                    sm: { span: 16 }
                                }
                            }
                        },
                        {
                            label: '开工日期',
                            field: "startDate",
                            type: 'date',
                            placeholder: '请选择',
                            span: 12,
                            initialValue: flowData?.startDate,
                            qnnDisabled: true,
                            hide: true,
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 8 },
                                    sm: { span: 8 }
                                },
                                wrapperCol: {
                                    xs: { span: 16 },
                                    sm: { span: 16 }
                                }
                            }
                        },
                        {
                            label: '竣工日期',
                            field: "endDate",
                            type: 'date',
                            placeholder: '请选择',
                            span: 12,
                            initialValue: flowData?.endDate,
                            qnnDisabled: true,
                            hide: true,
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 8 },
                                    sm: { span: 8 }
                                },
                                wrapperCol: {
                                    xs: { span: 16 },
                                    sm: { span: 16 }
                                }
                            }
                        },
                        {
                            label: '合同工期',
                            field: "csTimeLimit",
                            type: 'string',
                            placeholder: '请输入',
                            span: 12,
                            initialValue: flowData?.csTimeLimit,
                            qnnDisabled: true,
                            hide: true,
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 8 },
                                    sm: { span: 8 }
                                },
                                wrapperCol: {
                                    xs: { span: 16 },
                                    sm: { span: 16 }
                                }
                            }
                        },
                        {
                            label: '合同类型',
                            field: "contractType",
                            type: 'string',
                            qnnDisabled: true,
                            initialValue: flowData?.contractType,
                            placeholder: '请输入',
                            span: 12,
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 8 },
                                    sm: { span: 8 }
                                },
                                wrapperCol: {
                                    xs: { span: 16 },
                                    sm: { span: 16 }
                                }
                            }
                        },
                        {
                            label: '含税合同金额(万元)',
                            field: "contractCost",
                            type: 'number',
                            qnnDisabled: true,
                            hide: true,
                            initialValue: flowData?.contractCost,
                            placeholder: '请输入',
                            span: 12,
                            precision: 6,
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 8 },
                                    sm: { span: 8 }
                                },
                                wrapperCol: {
                                    xs: { span: 16 },
                                    sm: { span: 16 }
                                }
                            }
                        },
                        {
                            label: '不含税合同金额(万元)',
                            field: "contractCostNoTax",
                            type: 'number',
                            qnnDisabled: true,
                            hide: true,
                            initialValue: flowData?.contractCostNoTax,
                            placeholder: '请输入',
                            span: 12,
                            precision: 6,
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 8 },
                                    sm: { span: 8 }
                                },
                                wrapperCol: {
                                    xs: { span: 16 },
                                    sm: { span: 16 }
                                }
                            }
                        },
                        {
                            label: '合同税额(万元)',
                            field: "contractCostTax",
                            type: 'number',
                            qnnDisabled: true,
                            hide: true,
                            initialValue: flowData?.contractCostTax,
                            placeholder: '请输入',
                            span: 12,
                            precision: 6,
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 8 },
                                    sm: { span: 8 }
                                },
                                wrapperCol: {
                                    xs: { span: 16 },
                                    sm: { span: 16 }
                                }
                            }
                        },
                        {
                            label: '本期含税增减金额(万元)',
                            field: "pp4",
                            type: 'string',
                            qnnDisabled: true,
                            hide: true,
                            initialValue: flowData?.pp4,
                            placeholder: '请输入',
                            span: 12,
                            precision: 6,
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 8 },
                                    sm: { span: 8 }
                                },
                                wrapperCol: {
                                    xs: { span: 16 },
                                    sm: { span: 16 }
                                }
                            }
                        },
                        {
                            label: '本期不含税增减金额(万元)',
                            field: "pp4NoTax",
                            type: 'number',
                            qnnDisabled: true,
                            hide: true,
                            initialValue: flowData?.pp4NoTax,
                            placeholder: '请输入',
                            span: 12,
                            precision: 6,
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 8 },
                                    sm: { span: 8 }
                                },
                                wrapperCol: {
                                    xs: { span: 16 },
                                    sm: { span: 16 }
                                }
                            }
                        },
                        {
                            label: '本期增减金额(万元)',
                            field: "pp4Tax",
                            type: 'number',
                            qnnDisabled: true,
                            hide: true,
                            initialValue: flowData?.pp4Tax,
                            placeholder: '请输入',
                            span: 12,
                            precision: 6,
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 8 },
                                    sm: { span: 8 }
                                },
                                wrapperCol: {
                                    xs: { span: 16 },
                                    sm: { span: 16 }
                                }
                            }
                        },
                        {
                            label: '变更后含税金额(万元)',
                            field: "alterContractSum",
                            type: 'number',
                            qnnDisabled: true,
                            hide: true,
                            initialValue: flowData?.alterContractSum,
                            placeholder: '请输入',
                            span: 12,
                            precision: 6,
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 8 },
                                    sm: { span: 8 }
                                },
                                wrapperCol: {
                                    xs: { span: 16 },
                                    sm: { span: 16 }
                                }
                            }
                        },
                        {
                            label: '变更后不含税金额(万元)',
                            field: "alterContractSumNoTax",
                            type: 'number',
                            qnnDisabled: true,
                            hide: true,
                            initialValue: flowData?.alterContractSumNoTax,
                            placeholder: '请输入',
                            span: 12,
                            precision: 6,
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 8 },
                                    sm: { span: 8 }
                                },
                                wrapperCol: {
                                    xs: { span: 16 },
                                    sm: { span: 16 }
                                }
                            }
                        },
                        {
                            label: '变更后税额(万元)',
                            field: "alterContractSumTax",
                            type: 'number',
                            initialValue: flowData?.alterContractSumTax,
                            placeholder: '请输入',
                            span: 12,
                            precision: 6,
                            qnnDisabled: true,
                            hide: true,
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 8 },
                                    sm: { span: 8 }
                                },
                                wrapperCol: {
                                    xs: { span: 16 },
                                    sm: { span: 16 }
                                }
                            }
                        },
                        {
                            label: '是否抵扣',
                            field: "isDeduct",
                            type: 'checkbox',
                            optionData: [
                                {
                                    value: '1'
                                }
                            ],
                            qnnDisabled: true,
                            hide: true,
                            span: 12,
                            initialValue: flowData?.isDeduct,
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 8 },
                                    sm: { span: 8 }
                                },
                                wrapperCol: {
                                    xs: { span: 16 },
                                    sm: { span: 16 }
                                }
                            }
                        },
                        {
                            label: '合同类别',
                            field: "code7",
                            type: 'select',
                            optionConfig: {
                                label: 'itemName', //默认 label
                                value: 'itemId'
                            },
                            fetchConfig: {
                                apiName: 'getBaseCodeSelect',
                                otherParams: {
                                    itemId: 'category_contr_type_CL'
                                }
                            },
                            placeholder: '请选择',
                            qnnDisabled: true,
                            hide: true,
                            span: 12,
                            initialValue: flowData?.code7,
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 8 },
                                    sm: { span: 8 }
                                },
                                wrapperCol: {
                                    xs: { span: 16 },
                                    sm: { span: 16 }
                                }
                            }
                        },
                        {
                            label: '物资来源',
                            field: "materialSource",
                            type: 'select',
                            optionConfig: {
                                label: 'itemName', //默认 label
                                value: 'itemId'
                            },
                            fetchConfig: {
                                apiName: 'getBaseCodeSelect',
                                otherParams: {
                                    itemId: 'category_contract_materialSource'
                                }
                            },
                            qnnDisabled: true,
                            hide: true,
                            placeholder: '请选择',
                            span: 12,
                            initialValue: flowData?.materialSource,
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 8 },
                                    sm: { span: 8 }
                                },
                                wrapperCol: {
                                    xs: { span: 16 },
                                    sm: { span: 16 }
                                }
                            }
                        },
                        // {
                        //     label: '采购方式',
                        //     field: "shopWay",
                        //     type: 'select',
                        //     optionConfig: {
                        //         label: 'label', //默认 label
                        //         value: 'value'
                        //     },
                        //     optionData: [
                        //         {
                        //             label: '招标采购',
                        //             value: '0'
                        //         },
                        //         {
                        //             label: '云电商采购',
                        //             value: '1'
                        //         },
                        //         {
                        //             label: '其他采购',
                        //             value: '2'
                        //         }
                        //     ],
                        //     placeholder: '请选择',
                        //     disabled: true,
                        //     span: 12
                        // },
                        // {
                        //     label: '采购编号',
                        //     field: "shopNumber",
                        //     type: 'string',
                        //     dependencies: ["shopWay"],
                        //     hide: (obj) => {
                        //         if (obj.form.getFieldValue('shopWay') !== '0' && obj.form.getFieldValue('shopWay') !== '1') {
                        //             return true;
                        //         } else {
                        //             return false;
                        //         }
                        //     },
                        //     disabled: true,
                        //     placeholder: '请输入',
                        //     span: 12
                        // },
                        {
                            label: '合同内容',
                            field: 'content',
                            type: 'textarea',
                            placeholder: '请输入',
                            initialValue: flowData?.content,
                            qnnDisabled: true,
                            hide: true,
                            span: 12,
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 4 },
                                    sm: { span: 4 }
                                },
                                wrapperCol: {
                                    xs: { span: 20 },
                                    sm: { span: 20 }
                                }
                            }
                        },
                        {
                            label: '备注',
                            field: 'remarks',
                            type: 'textarea',
                            placeholder: '请输入',
                            initialValue: flowData?.remarks,
                            qnnDisabled: true,
                            hide: true,
                            span: 12,
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 4 },
                                    sm: { span: 4 }
                                },
                                wrapperCol: {
                                    xs: { span: 20 },
                                    sm: { span: 20 }
                                }
                            }
                        },
                        {
                            type: "component",
                            field: "componentQD",
                            label: '清单查看',
                            Component: obj => {
                                return (
                                    <Button type="primary" onClick={() => {
                                        this.setState({
                                            visible: true
                                        })
                                    }}>清单查看</Button>
                                );
                            },
                            span: 12,
                        },
                        {
                            type: "textarea",
                            label: "项目各部门会签",
                            field: "opinionField2",
                            opinionField: true,
                            addShow: flowData ? false : true,
                            span: 12,
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
                            type: "textarea",
                            label: "项目各领导会签",
                            field: "opinionField3",
                            opinionField: true,
                            addShow: flowData ? false : true,
                            span: 12,
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
                            type: "textarea",
                            label: "公司主管部门审核",
                            field: "opinionField4",
                            opinionField: true,
                            addShow: flowData ? false : true,
                            span: 12,
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
                            type: "textarea",
                            label: "公司各部门(含法律部)会签",
                            field: "opinionField5",
                            opinionField: true,
                            addShow: flowData ? false : true,
                            span: 12,
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
                            type: "textarea",
                            label: "公司主管领导审核",
                            field: "opinionField6",
                            opinionField: true,
                            addShow: flowData ? false : true,
                            span: 12,
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
                            type: "textarea",
                            label: "公司总经理审批",
                            field: "opinionField7",
                            opinionField: true,
                            addShow: flowData ? false : true,
                            span: 12,
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
                            type: "textarea",
                            label: "总部主管部门审核",
                            field: "opinionField8",
                            opinionField: true,
                            addShow: flowData ? false : true,
                            span: 12,
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
                            type: "textarea",
                            label: "总部各部门会签",
                            field: "opinionField9",
                            opinionField: true,
                            addShow: flowData ? false : true,
                            span: 12,
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
                            type: "textarea",
                            label: "总部领导会签",
                            field: "opinionField10",
                            opinionField: true,
                            addShow: flowData ? false : true,
                            span: 12,
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
                            type: "textarea",
                            label: "托管公司主管部门审核",
                            field: "opinionField11",
                            opinionField: true,
                            addShow: flowData ? false : true,
                            span: 12,
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
                            type: "textarea",
                            label: "托管公司各部门会签",
                            field: "opinionField12",
                            opinionField: true,
                            addShow: flowData ? false : true,
                            span: 12,
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
                            type: "textarea",
                            label: "托管公司领导审批",
                            field: "opinionField13",
                            opinionField: true,
                            addShow: flowData ? false : true,
                            span: 12,
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
                            type: "textarea",
                            label: "事业部审核",
                            field: "opinionField14",
                            opinionField: true,
                            addShow: flowData ? false : true,
                            span: 12,
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
                            type: "textarea",
                            label: "事业部领导审批",
                            field: "opinionField15",
                            opinionField: true,
                            addShow: flowData ? false : true,
                            span: 12,
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
                            type: "textarea",
                            label: "相关事业部审核",
                            field: "opinionField16",
                            opinionField: true,
                            addShow: flowData ? false : true,
                            span: 12,
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
                            type: "textarea",
                            label: "相关事业部领导审批",
                            field: "opinionField17",
                            opinionField: true,
                            addShow: flowData ? false : true,
                            span: 12,
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
                            type: "textarea",
                            label: "法律顾问审核",
                            field: "opinionField18",
                            opinionField: true,
                            addShow: flowData ? false : true,
                            span: 12,
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
                            type: "textarea",
                            label: "合同校对",
                            field: "opinionField19",
                            opinionField: true,
                            addShow: flowData ? false : true,
                            span: 12,
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
                    {...config.workFlowConfig}
                    fieldsCURD={(obj, flowData) => {
                        if(flowData?.apiData){
                            this.setState({ ModalData: JSON.parse(flowData.apiData) })
                        }
                        return obj
                    }}
                    tabs={[]}
                />
                <Modal
                    width='1200px'
                    style={{ top: '0' }}
                    title="清单信息"
                    visible={visible}
                    footer={null}
                    onCancel={this.handleCancel}
                    bodyStyle={{ width: '1200px' }}
                    centered={true}
                    wrapClassName={'modals'}
                >
                    <QnnForm
                        {...this.props}
                        match={this.props.match}
                        fetch={this.props.myFetch}
                        upload={this.props.myUpload}
                        headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                        formConfig={[
                            {
                                label: '补充协议编号',
                                field: "contractNo",
                                type: 'string',
                                disabled: true,
                                initialValue:ModalData?.contractNo,
                                placeholder: '请输入',
                                span: 12,
                                formItemLayout: {
                                    labelCol: {
                                        xs: { span: 8 },
                                        sm: { span: 8 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 16 },
                                        sm: { span: 16 }
                                    }
                                }
                            },
                            {
                                label: '批复单位',
                                field: "replyUnit",
                                type: 'select',
                                optionConfig: {
                                    label: 'label',
                                    value: 'value'
                                },
                                optionData: [
                                    {
                                        label: '项目',
                                        value: '0'
                                    },
                                    {
                                        label: '公司',
                                        value: '1'
                                    },
                                    {
                                        label: '局',
                                        value: '2'
                                    }
                                ],
                                disabled: true,
                                initialValue:ModalData?.replyUnit,
                                placeholder: '请选择',
                                span: 12,
                                formItemLayout: {
                                    labelCol: {
                                        xs: { span: 8 },
                                        sm: { span: 8 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 16 },
                                        sm: { span: 16 }
                                    }
                                }
                            },
                            {
                                label: '批复日期',
                                field: "replyDate",
                                type: 'date',
                                disabled: true,
                                initialValue:ModalData?.replyDate,
                                placeholder: '请输入',
                                span: 12,
                                formItemLayout: {
                                    labelCol: {
                                        xs: { span: 8 },
                                        sm: { span: 8 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 16 },
                                        sm: { span: 16 }
                                    }
                                }
                            },
                            {
                                label: '变更内容',
                                field: 'alterContent',
                                type: 'string',
                                required: true,
                                disabled: true,
                                initialValue:ModalData?.alterContent,
                                placeholder: '请输入',
                                formItemLayout: {
                                    labelCol: {
                                        xs: { span: 4 },
                                        sm: { span: 4 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 20 },
                                        sm: { span: 20 }
                                    }
                                }
                            },
                            {
                                label: '变更原因',
                                field: 'alterReason',
                                type: 'string',
                                disabled: true,
                                initialValue:ModalData?.alterReason,
                                placeholder: '请输入',
                                formItemLayout: {
                                    labelCol: {
                                        xs: { span: 4 },
                                        sm: { span: 4 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 20 },
                                        sm: { span: 20 }
                                    }
                                }
                            },
                            {
                                type: "qnnTable",
                                field: "replenishShopDetailedList",
                                incToForm: true,
                                hide: ModalData?.code7 !== 'WL' ? false : true,
                                initialValue:ModalData?.replenishShopDetailedList || [],
                                qnnTableConfig: {
                                    antd: {
                                        size: "small",
                                        rowKey: 'zxCtSuppliesContrReplenishShopDetailId'
                                    },
                                    paginationConfig: false,
                                    isShowRowSelect: false,
                                    formConfig: [
                                        {
                                            isInTable: false,
                                            form: {
                                                field: 'zxCtSuppliesContrReplenishShopDetailId',
                                                type: 'string',
                                                hide: true,
                                            }
                                        },
                                        {
                                            isInTable: false,
                                            form: {
                                                field: 'coBookID',
                                                type: 'string',
                                                hide: true,
                                            }
                                        },
                                        {
                                            isInTable: false,
                                            form: {
                                                field: 'pp5',
                                                type: 'string',
                                                hide: true,
                                            }
                                        },
                                        {
                                            isInTable: false,
                                            form: {
                                                field: 'workNo',
                                                type: 'string',
                                                hide: true,
                                            }
                                        },
                                        {
                                            isInTable: false,
                                            form: {
                                                field: 'workType',
                                                type: 'string',
                                                hide: true,
                                            }
                                        },
                                        {
                                            table: {
                                                title: `<div>变更类型<span style='color: #ff4d4f'>*</span></div>`,
                                                dataIndex: 'alterType',
                                                key: 'alterType',
                                                width: 100,
                                                type: 'select',
                                                fixed: 'left'
                                            },
                                            form: {
                                                type: 'select',
                                                optionConfig: {
                                                    label: 'label', //默认 label
                                                    value: 'value'
                                                },
                                                optionData: [
                                                    {
                                                        label: '新增',
                                                        value: '1'
                                                    },
                                                    {
                                                        label: '修改',
                                                        value: '2'
                                                    }
                                                ],
                                                required: true,
                                                placeholder: '请选择'
                                            }
                                        },
                                        {
                                            table: {
                                                title: `<div>物资类别<span style='color: #ff4d4f'>*</span></div>`,
                                                dataIndex: 'workTypeID',
                                                key: 'workTypeID',
                                                width: 150,
                                                fixed: 'left',
                                                type: 'select',
                                            },
                                            form: {
                                                type: 'select',
                                                dependencies: ['alterType'],
                                                dependenciesReRender: true,
                                                optionConfig: {
                                                    label: 'catName', //默认 label
                                                    value: 'id'
                                                },
                                                fetchConfig: {
                                                    apiName: 'getZxSkResCategoryMaterialsList',
                                                    otherParams: {
                                                        parentID: '0002'
                                                    }
                                                },
                                                allowClear: false,
                                                showSearch: true,
                                                required: true,
                                                placeholder: '请选择'
                                            }
                                        },
                                        {
                                            table: {
                                                title: `<div>物资编码<span style='color: #ff4d4f'>*</span></div>`,
                                                dataIndex: 'workID',
                                                key: 'workID',
                                                width: 150,
                                                fixed: 'left',
                                                type: 'select'
                                            },
                                            form: {
                                                type: 'select',
                                                dependencies: ['alterType', 'workTypeID'],
                                                dependenciesReRender: true,
                                                optionConfig: (obj) => {
                                                    if (obj.rowData.alterType === '1') {
                                                        return {
                                                            label: 'resCode', //默认 label
                                                            value: 'id'
                                                        }
                                                    } else if (obj.rowData.alterType === '2') {
                                                        return {
                                                            label: 'workNo', //默认 label
                                                            value: 'workID'
                                                        }
                                                    }
                                                },
                                                fetchConfig: (obj) => {
                                                    if (obj.rowData.alterType === '1') {
                                                        if (obj.rowData.workTypeID) {
                                                            return {
                                                                apiName: 'getZxSkResourceMaterialsListNameJoinResource',
                                                                otherParams: {
                                                                    id: obj.rowData.workTypeID
                                                                }
                                                            }
                                                        }
                                                    } else if (obj.rowData.alterType === '2') {
                                                        return {
                                                            apiName: 'getZxCtSuppliesContrShopListList',
                                                            // apiName: 'getZxCtSuppliesContrApplyShopListList',
                                                            otherParams: {
                                                                contractID: ModalData?.pp6
                                                            }
                                                        }
                                                    }
                                                },
                                                allowClear: false,
                                                showSearch: true,
                                                required: true,
                                                placeholder: '请选择'
                                            }
                                        },
                                        {
                                            table: {
                                                title: '物资名称',
                                                dataIndex: 'workName',
                                                key: 'workName',
                                                width: 100
                                            },
                                            form: {
                                                type: 'string',
                                                placeholder: '请输入'
                                            }
                                        },
                                        {
                                            table: {
                                                title: '物资规格',
                                                dataIndex: 'spec',
                                                key: 'spec',
                                                width: 100
                                            },
                                            form: {
                                                type: 'string',
                                                placeholder: '请输入'
                                            }
                                        },
                                        {
                                            table: {
                                                title: '单位',
                                                dataIndex: 'unit',
                                                key: 'unit',
                                                width: 100
                                            },
                                            form: {
                                                type: 'string',
                                                placeholder: '请输入'
                                            }
                                        },
                                        {
                                            table: {
                                                title: '是否网价',
                                                dataIndex: 'isNetPrice',
                                                key: 'isNetPrice',
                                                width: 100,
                                                render: (data) => {
                                                    if (data === '1') {
                                                        return '是';
                                                    } else {
                                                        return '否';
                                                    }
                                                }
                                            },
                                            form: {
                                                type: 'checkbox',
                                                optionData: [
                                                    {
                                                        value: "1"
                                                    }
                                                ]
                                            }
                                        },
                                        {
                                            table: {
                                                title: '数量',
                                                dataIndex: 'qty',
                                                key: 'qty',
                                                width: 100,
                                                render: (data, rowData) => {
                                                    if (rowData.qty || rowData.qty === 0) {
                                                        return rowData.qty.toFixed(3)
                                                    }
                                                }
                                            },
                                            form: {
                                                type: 'number',
                                                precision: 3,
                                                placeholder: '请输入'
                                            }
                                        },
                                        {
                                            table: {
                                                title: '含税合同单价',
                                                dataIndex: 'price',
                                                key: 'price',
                                                width: 120,
                                                render: (data, rowData) => {
                                                    if (rowData.price || rowData.price === 0) {
                                                        return rowData.price.toFixed(6)
                                                    }
                                                }
                                            },
                                            form: {
                                                type: 'number',
                                                precision: 6,
                                                placeholder: '请输入'
                                            }
                                        },
                                        {
                                            table: {
                                                title: '含税合同金额',
                                                dataIndex: 'contractSum',
                                                key: 'contractSum',
                                                width: 120,
                                                render: (data, rowData) => {
                                                    if (rowData.contractSum || rowData.contractSum === 0) {
                                                        return rowData.contractSum.toFixed(2)
                                                    }
                                                }
                                            },
                                            form: {
                                                type: 'number',
                                                precision: 2,
                                                placeholder: '请输入'
                                            }
                                        },
                                        {
                                            table: {
                                                title: '不含税合同单价',
                                                dataIndex: 'priceNoTax',
                                                key: 'priceNoTax',
                                                width: 130,
                                                render: (data, rowData) => {
                                                    if (rowData.priceNoTax || rowData.priceNoTax === 0) {
                                                        return rowData.priceNoTax.toFixed(6)
                                                    }
                                                }
                                            },
                                            form: {
                                                type: 'number',
                                                precision: 6,
                                                placeholder: '请输入'
                                            }
                                        },
                                        {
                                            table: {
                                                title: '不含税合同金额',
                                                dataIndex: 'contractSumNoTax',
                                                key: 'contractSumNoTax',
                                                width: 130,
                                                render: (data, rowData) => {
                                                    if (rowData.contractSumNoTax || rowData.contractSumNoTax === 0) {
                                                        return rowData.contractSumNoTax.toFixed(2)
                                                    }
                                                }
                                            },
                                            form: {
                                                type: 'number',
                                                precision: 2,
                                                placeholder: '请输入'
                                            }
                                        },
                                        {
                                            table: {
                                                title: '税额',
                                                dataIndex: 'contractSumTax',
                                                key: 'contractSumTax',
                                                width: 100,
                                                render: (data, rowData) => {
                                                    if (rowData.contractSumTax || rowData.contractSumTax === 0) {
                                                        return rowData.contractSumTax.toFixed(2)
                                                    }
                                                }
                                            },
                                            form: {
                                                type: 'number',
                                                precision: 2,
                                                placeholder: '请输入'
                                            }
                                        },
                                        {
                                            table: {
                                                title: '税率(%)',
                                                dataIndex: 'taxRate',
                                                key: 'taxRate',
                                                width: 100,
                                                type: 'select'
                                            },
                                            form: {
                                                type: 'select',
                                                optionConfig: {
                                                    label: 'itemName', //默认 label
                                                    value: 'itemId'
                                                },
                                                fetchConfig: {
                                                    apiName: 'getBaseCodeSelect',
                                                    otherParams: {
                                                        itemId: 'shuiLV'
                                                    }
                                                },
                                                allowClear: false,
                                                showSearch: true,
                                                placeholder: '请选择'
                                            }
                                        },
                                        {
                                            table: {
                                                title: '是否抵扣',
                                                dataIndex: 'isDeduct',
                                                key: 'isDeduct',
                                                width: 100,
                                                render: (data) => {
                                                    if (data === '1') {
                                                        return '是';
                                                    } else {
                                                        return '否';
                                                    }
                                                }
                                            },
                                            form: {
                                                type: 'string',
                                                placeholder: '请输入'
                                            }
                                        },
                                        // {
                                        //     table: {
                                        //         title: '已结算数量',
                                        //         dataIndex: 'settledQty',
                                        //         key: 'settledQty',
                                        //         width: 100,
                                        //         render: (data, rowData) => {
                                        //             if (rowData.a || rowData.a === 0) {
                                        //                 return rowData.a.toFixed(2)
                                        //             }
                                        //         }
                                        //     },
                                        //     form: {
                                        //         type: 'number',
                                        //         placeholder: '请输入'
                                        //     }
                                        // },
                                        {
                                            table: {
                                                title: '变更后数量',
                                                dataIndex: 'changeQty',
                                                key: 'changeQty',
                                                width: 120,
                                                render: (data, rowData) => {
                                                    if (rowData.changeQty || rowData.changeQty === 0) {
                                                        return rowData.changeQty.toFixed(3)
                                                    }
                                                }
                                            },
                                            form: {
                                                type: 'number',
                                                precision: 3,
                                                placeholder: '请输入'
                                            }
                                        },
                                        {
                                            table: {
                                                title: '变更后含税单价',
                                                dataIndex: 'changePrice',
                                                key: 'changePrice',
                                                width: 130,
                                                render: (data, rowData) => {
                                                    if (rowData.changePrice || rowData.changePrice === 0) {
                                                        return rowData.changePrice.toFixed(6)
                                                    }
                                                }
                                            },
                                            form: {
                                                type: 'number',
                                                precision: 6,
                                                placeholder: '请输入'
                                            }
                                        },
                                        {
                                            table: {
                                                title: '变更后不含税单价',
                                                dataIndex: 'changePriceNoTax',
                                                key: 'changePriceNoTax',
                                                width: 140,
                                                render: (data, rowData) => {
                                                    if (rowData.changePriceNoTax || rowData.changePriceNoTax === 0) {
                                                        return rowData.changePriceNoTax.toFixed(6)
                                                    }
                                                }
                                            },
                                            form: {
                                                type: 'number',
                                                precision: 6,
                                                placeholder: '请输入'
                                            }
                                        },
                                        {
                                            table: {
                                                title: '变更后含税金额',
                                                dataIndex: 'changeContractSum',
                                                key: 'changeContractSum',
                                                width: 130,
                                                render: (data, rowData) => {
                                                    if (rowData.changeContractSum || rowData.changeContractSum === 0) {
                                                        return rowData.changeContractSum.toFixed(2)
                                                    }
                                                }
                                            },
                                            form: {
                                                type: 'number',
                                                precision: 2,
                                                placeholder: '请输入'
                                            }
                                        },
                                        {
                                            table: {
                                                title: '变更后不含税金额',
                                                dataIndex: 'changeContractSumNoTax',
                                                key: 'changeContractSumNoTax',
                                                width: 140,
                                                render: (data, rowData) => {
                                                    if (rowData.changeContractSumNoTax || rowData.changeContractSumNoTax === 0) {
                                                        return rowData.changeContractSumNoTax.toFixed(2)
                                                    }
                                                }
                                            },
                                            form: {
                                                type: 'number',
                                                precision: 2,
                                                placeholder: '请输入'
                                            }
                                        },
                                        {
                                            table: {
                                                title: '变更后税额',
                                                dataIndex: 'changeContractSumTax',
                                                key: 'changeContractSumTax',
                                                width: 120,
                                                render: (data, rowData) => {
                                                    if (rowData.changeContractSumTax || rowData.changeContractSumTax === 0) {
                                                        return rowData.changeContractSumTax.toFixed(2)
                                                    }
                                                }
                                            },
                                            form: {
                                                type: 'number',
                                                precision: 2,
                                                placeholder: '请输入'
                                            }
                                        },
                                        {
                                            table: {
                                                title: '备注',
                                                dataIndex: 'remarks',
                                                key: 'remarks',
                                                width: 100,
                                            },
                                            form: {
                                                type: 'string',
                                                placeholder: '请输入'
                                            }
                                        }
                                    ]
                                }
                            },
                            {
                                type: "qnnTable",
                                field: "replenishLeaseDetailedList",
                                incToForm: true,
                                hide: ModalData?.code7 === 'WL' ? false : true,
                                initialValue:ModalData?.replenishLeaseDetailedList || [],
                                qnnTableConfig: {
                                    antd: {
                                        size: "small",
                                        rowKey: 'zxCtSuppliesContrReplenishLeaseDetailId'
                                    },
                                    paginationConfig: false,
                                    isShowRowSelect: false,
                                    formConfig: [
                                        {
                                            isInTable: false,
                                            form: {
                                                field: 'zxCtSuppliesContrReplenishLeaseDetailId',
                                                type: 'string',
                                                hide: true,
                                            }
                                        },
                                        {
                                            isInTable: false,
                                            form: {
                                                field: 'coBookID',
                                                type: 'string',
                                                hide: true,
                                            }
                                        },
                                        {
                                            isInTable: false,
                                            form: {
                                                field: 'pp5',
                                                type: 'string',
                                                hide: true,
                                            }
                                        },
                                        {
                                            isInTable: false,
                                            form: {
                                                field: 'workNo',
                                                type: 'string',
                                                hide: true,
                                            }
                                        },
                                        {
                                            isInTable: false,
                                            form: {
                                                field: 'workType',
                                                type: 'string',
                                                hide: true,
                                            }
                                        },
                                        {
                                            table: {
                                                title: `<div>变更类型<span style='color: #ff4d4f'>*</span></div>`,
                                                dataIndex: 'alterType',
                                                key: 'alterType',
                                                width: 100,
                                                type: 'select',
                                                fixed: 'left'
                                            },
                                            form: {
                                                type: 'select',
                                                optionConfig: {
                                                    label: 'label', //默认 label
                                                    value: 'value'
                                                },
                                                optionData: [
                                                    {
                                                        label: '新增',
                                                        value: '1'
                                                    },
                                                    {
                                                        label: '修改',
                                                        value: '2'
                                                    }
                                                ],
                                                required: true,
                                                placeholder: '请选择'
                                            }
                                        },
                                        {
                                            table: {
                                                title: `<div>物资类别<span style='color: #ff4d4f'>*</span></div>`,
                                                dataIndex: 'workTypeID',
                                                key: 'workTypeID',
                                                width: 150,
                                                fixed: 'left',
                                                type: 'select'
                                            },
                                            form: {
                                                type: 'select',
                                                optionConfig: {
                                                    label: 'catName', //默认 label
                                                    value: 'id'
                                                },
                                                dependencies: ['alterType'],
                                                dependenciesReRender: true,
                                                fetchConfig: {
                                                    apiName: 'getZxSkResCategoryMaterialsList',
                                                    otherParams: {
                                                        parentID: '0002'
                                                    }
                                                },
                                                required: true,
                                                allowClear: false,
                                                showSearch: true,
                                                placeholder: '请选择'
                                            }
                                        },
                                        {
                                            table: {
                                                title: `<div>物资编码<span style='color: #ff4d4f'>*</span></div>`,
                                                dataIndex: 'workID',
                                                key: 'workID',
                                                width: 150,
                                                fixed: 'left',
                                                type: 'select',
                                            },
                                            form: {
                                                type: 'select',
                                                dependencies: ['alterType', 'workTypeID'],
                                                dependenciesReRender: true,
                                                optionConfig: (obj) => {
                                                    if (obj.rowData.alterType === '1') {
                                                        return {
                                                            label: 'resCode', //默认 label
                                                            value: 'id'
                                                        }
                                                    } else if (obj.rowData.alterType === '2') {
                                                        return {
                                                            label: 'workNo', //默认 label
                                                            value: 'workID'
                                                        }
                                                    }
                                                },
                                                fetchConfig: (obj) => {
                                                    if (obj.rowData.alterType === '1') {
                                                        if (obj.rowData.workTypeID) {
                                                            return {
                                                                apiName: 'getZxSkResourceMaterialsListNameJoinResource',
                                                                otherParams: {
                                                                    id: obj.rowData.workTypeID
                                                                }
                                                            }
                                                        }
                                                    } else if (obj.rowData.alterType === '2') {
                                                        return {
                                                            apiName: 'getZxCtSuppliesContrLeaseListList',
                                                            otherParams: {
                                                                contractID: ModalData?.pp6
                                                            }
                                                        }
                                                    }
                                                },
                                                required: true,
                                                allowClear: false,
                                                showSearch: true,
                                                placeholder: '请选择'
                                            }
                                        },
                                        {
                                            table: {
                                                title: '物资名称',
                                                dataIndex: 'workName',
                                                key: 'workName',
                                                width: 100
                                            },
                                            form: {
                                                type: 'string',
                                                placeholder: '请输入'
                                            }
                                        },
                                        {
                                            table: {
                                                title: '物资规格',
                                                dataIndex: 'spec',
                                                key: 'spec',
                                                width: 100
                                            },
                                            form: {
                                                type: 'string',
                                                placeholder: '请输入'
                                            }
                                        },
                                        {
                                            table: {
                                                title: '单位',
                                                dataIndex: 'unit',
                                                key: 'unit',
                                                width: 100
                                            },
                                            form: {
                                                type: 'string',
                                                placeholder: '请输入'
                                            }
                                        },
                                        {
                                            table: {
                                                title: '是否网价',
                                                dataIndex: 'isNetPrice',
                                                key: 'isNetPrice',
                                                width: 100,
                                                render: (data) => {
                                                    if (data === '1') {
                                                        return '是';
                                                    } else {
                                                        return '否';
                                                    }
                                                }
                                            },
                                            form: {
                                                type: 'checkbox',
                                                optionData: [
                                                    {
                                                        value: "1"
                                                    }
                                                ]
                                            }
                                        },
                                        {
                                            table: {
                                                title: '租期',
                                                dataIndex: 'contrTrrm',
                                                key: 'contrTrrm',
                                                width: 100,
                                                render: (data, rowData) => {
                                                    if (rowData.contrTrrm || rowData.contrTrrm === 0) {
                                                        return rowData.contrTrrm.toFixed(3)
                                                    }
                                                }
                                            },
                                            form: {
                                                type: 'number',
                                                precision: 3,
                                                placeholder: '请输入'
                                            }
                                        },
                                        {
                                            table: {
                                                title: '数量',
                                                dataIndex: 'qty',
                                                key: 'qty',
                                                width: 100,
                                                render: (data, rowData) => {
                                                    if (rowData.qty || rowData.qty === 0) {
                                                        return rowData.qty.toFixed(3)
                                                    }
                                                }
                                            },
                                            form: {
                                                type: 'number',
                                                precision: 3,
                                                placeholder: '请输入'
                                            }
                                        },
                                        {
                                            table: {
                                                title: '含税合同单价',
                                                dataIndex: 'price',
                                                key: 'price',
                                                width: 120,
                                                render: (data, rowData) => {
                                                    if (rowData.price || rowData.price === 0) {
                                                        return rowData.price.toFixed(6)
                                                    }
                                                }
                                            },
                                            form: {
                                                type: 'number',
                                                precision: 6,
                                                placeholder: '请输入'
                                            }
                                        },
                                        {
                                            table: {
                                                title: '含税合同金额',
                                                dataIndex: 'contractSum',
                                                key: 'contractSum',
                                                width: 120,
                                                render: (data, rowData) => {
                                                    if (rowData.contractSum || rowData.contractSum === 0) {
                                                        return rowData.contractSum.toFixed(2)
                                                    }
                                                }
                                            },
                                            form: {
                                                type: 'number',
                                                precision: 2,
                                                placeholder: '请输入'
                                            }
                                        },
                                        {
                                            table: {
                                                title: '不含税合同单价',
                                                dataIndex: 'priceNoTax',
                                                key: 'priceNoTax',
                                                width: 130,
                                                render: (data, rowData) => {
                                                    if (rowData.priceNoTax || rowData.priceNoTax === 0) {
                                                        return rowData.priceNoTax.toFixed(6)
                                                    }
                                                }
                                            },
                                            form: {
                                                type: 'number',
                                                precision: 6,
                                                placeholder: '请输入'
                                            }
                                        },
                                        {
                                            table: {
                                                title: '不含税合同金额',
                                                dataIndex: 'contractSumNoTax',
                                                key: 'contractSumNoTax',
                                                width: 130,
                                                render: (data, rowData) => {
                                                    if (rowData.contractSumNoTax || rowData.contractSumNoTax === 0) {
                                                        return rowData.contractSumNoTax.toFixed(2)
                                                    }
                                                }
                                            },
                                            form: {
                                                type: 'number',
                                                precision: 2,
                                                placeholder: '请输入'
                                            }
                                        },
                                        {
                                            table: {
                                                title: '税额',
                                                dataIndex: 'contractSumTax',
                                                key: 'contractSumTax',
                                                width: 100,
                                                render: (data, rowData) => {
                                                    if (rowData.contractSumTax || rowData.contractSumTax === 0) {
                                                        return rowData.contractSumTax.toFixed(2)
                                                    }
                                                }
                                            },
                                            form: {
                                                type: 'number',
                                                precision: 2,
                                                placeholder: '请输入'
                                            }
                                        },
                                        {
                                            table: {
                                                title: '税率(%)',
                                                dataIndex: 'taxRate',
                                                key: 'taxRate',
                                                width: 100,
                                                type: 'select'
                                            },
                                            form: {
                                                type: 'select',
                                                optionConfig: {
                                                    label: 'itemName', //默认 label
                                                    value: 'itemId'
                                                },
                                                fetchConfig: {
                                                    apiName: 'getBaseCodeSelect',
                                                    otherParams: {
                                                        itemId: 'shuiLV'
                                                    }
                                                },
                                                allowClear: false,
                                                showSearch: true,
                                                placeholder: '请选择'
                                            }
                                        },
                                        {
                                            table: {
                                                title: '是否抵扣',
                                                dataIndex: 'isDeduct',
                                                key: 'isDeduct',
                                                width: 100,
                                                render: (data) => {
                                                    if (data === '1') {
                                                        return '是';
                                                    } else {
                                                        return '否';
                                                    }
                                                }
                                            },
                                            form: {
                                                type: 'string',
                                                placeholder: '请输入'
                                            }
                                        },
                                        // {
                                        //     table: {
                                        //         title: '已结算数量',
                                        //         dataIndex: 'settledQty',
                                        //         key: 'settledQty',
                                        //         width: 100,
                                        //         render: (data, rowData) => {
                                        //             if (rowData.a || rowData.a === 0) {
                                        //                 return rowData.a.toFixed(2)
                                        //             }
                                        //         }
                                        //     },
                                        //     form: {
                                        //         type: 'number',
                                        //         placeholder: '请输入'
                                        //     }
                                        // },
                                        {
                                            table: {
                                                title: '租期单位',
                                                dataIndex: 'rentUnit',
                                                key: 'rentUnit',
                                                width: 100,
                                            },
                                            form: {
                                                type: 'string',
                                                placeholder: '请输入'
                                            }
                                        },
                                        {
                                            table: {
                                                title: '变更后租期',
                                                dataIndex: 'alterTrrm',
                                                key: 'alterTrrm',
                                                width: 100,
                                                render: (data, rowData) => {
                                                    if (rowData.alterTrrm || rowData.alterTrrm === 0) {
                                                        return rowData.alterTrrm.toFixed(3)
                                                    }
                                                }
                                            },
                                            form: {
                                                type: 'number',
                                                precision: 3,
                                                placeholder: '请输入'
                                            }
                                        },
                                        {
                                            table: {
                                                title: '变更后数量',
                                                dataIndex: 'changeQty',
                                                key: 'changeQty',
                                                width: 120,
                                                render: (data, rowData) => {
                                                    if (rowData.changeQty || rowData.changeQty === 0) {
                                                        return rowData.changeQty.toFixed(3)
                                                    }
                                                }
                                            },
                                            form: {
                                                type: 'number',
                                                precision: 3,
                                                placeholder: '请输入'
                                            }
                                        },
                                        {
                                            table: {
                                                title: '变更后含税单价',
                                                dataIndex: 'changePrice',
                                                key: 'changePrice',
                                                width: 130,
                                                render: (data, rowData) => {
                                                    if (rowData.changePrice || rowData.changePrice === 0) {
                                                        return rowData.changePrice.toFixed(6)
                                                    }
                                                }
                                            },
                                            form: {
                                                type: 'number',
                                                precision: 6,
                                                placeholder: '请输入'
                                            }
                                        },
                                        {
                                            table: {
                                                title: '变更后不含税单价',
                                                dataIndex: 'changePriceNoTax',
                                                key: 'changePriceNoTax',
                                                width: 140,
                                                render: (data, rowData) => {
                                                    if (rowData.changePriceNoTax || rowData.changePriceNoTax === 0) {
                                                        return rowData.changePriceNoTax.toFixed(6)
                                                    }
                                                }
                                            },
                                            form: {
                                                type: 'number',
                                                precision: 6,
                                                placeholder: '请输入'
                                            }
                                        },
                                        {
                                            table: {
                                                title: '变更后含税金额',
                                                dataIndex: 'changeContractSum',
                                                key: 'changeContractSum',
                                                width: 130,
                                                render: (data, rowData) => {
                                                    if (rowData.changeContractSum || rowData.changeContractSum === 0) {
                                                        return rowData.changeContractSum.toFixed(2)
                                                    }
                                                }
                                            },
                                            form: {
                                                type: 'number',
                                                precision: 2,
                                                placeholder: '请输入'
                                            }
                                        },
                                        {
                                            table: {
                                                title: '变更后不含税金额',
                                                dataIndex: 'changeContractSumNoTax',
                                                key: 'changeContractSumNoTax',
                                                width: 140,
                                                render: (data, rowData) => {
                                                    if (rowData.changeContractSumNoTax || rowData.changeContractSumNoTax === 0) {
                                                        return rowData.changeContractSumNoTax.toFixed(2)
                                                    }
                                                }
                                            },
                                            form: {
                                                type: 'number',
                                                precision: 2,
                                                placeholder: '请输入'
                                            }
                                        },
                                        {
                                            table: {
                                                title: '变更后税额',
                                                dataIndex: 'changeContractSumTax',
                                                key: 'changeContractSumTax',
                                                width: 120,
                                                render: (data, rowData) => {
                                                    if (rowData.changeContractSumTax || rowData.changeContractSumTax === 0) {
                                                        return rowData.changeContractSumTax.toFixed(2)
                                                    }
                                                }
                                            },
                                            form: {
                                                type: 'number',
                                                precision: 2,
                                                placeholder: '请输入'
                                            }
                                        },
                                        {
                                            table: {
                                                title: '备注',
                                                dataIndex: 'remarks',
                                                key: 'remarks',
                                                width: 100,
                                            },
                                            form: {
                                                type: 'string',
                                                placeholder: '请输入'
                                            }
                                        }
                                    ]
                                }
                            }
                        ]}
                    />
                </Modal>
            </div>
        );
    }
}
export default index;
