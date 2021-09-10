import React, { Component } from "react";
import { Form } from "../../modules/work-flow";
let config = {
    //流程专属配置   
    parameterName: 'orgID',
    workFlowConfig: {
        //后台定的字段
        title: ["附属合同补充协议", 'contractName', 'contractNo'], //标题字段
        apiNameByAdd: "updateZxCtFsSideAgreement",
        apiNameByUpdate: "updateZxCtFsSideAgreement",
        apiNameByGet: "getZxCtFsSideAgreementDetail",
        todo: "TodoList",
        hasTodo: "HasTodoList",
    },
    isHaveDoc: true,
    docFieldLable: "公文正文",
    docFieldName: "zxZhengWenFileList",
    docFieldIsRequired: true,
    docIsReadOnly: false,
    docFormFormItemLayout: {
        labelCol: {
            xs: { span: 3 },
            sm: { span: 3 }
        },
        wrapperCol: {
            xs: { span: 20 },
            sm: { span: 20 }
        }
    }
};
class index extends Component {
    render() {
        const { isInQnnTable, flowData = {} } = this.props
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
                            field: 'flowId',
                            type: 'string',
                            initialValue: 'FsSideAmagment',
                            hide: true,
                        },
                        {
                            field: 'zxCtFsSideAgreementId',
                            type: 'string',
                            initialValue: flowData?.zxCtFsSideAgreementId,
                            hide: true,
                        },
                        {
                            field: 'upAlterContractSum',
                            type: 'number',
                            initialValue: flowData?.upAlterContractSum,
                            hide: true,
                        },
                        {
                            field: 'upAlterContractSumNoTax',
                            type: 'number',
                            initialValue: flowData?.upAlterContractSumNoTax,
                            hide: true,
                        },
                        {
                            field: 'upAlterContractSumTax',
                            type: 'number',
                            initialValue: flowData?.upAlterContractSumTax,
                            hide: true,
                        },
                        {
                            field: 'contractName',
                            type: 'string',
                            initialValue: flowData?.contractName,
                            hide: true,
                        },
                        {
                            field: 'comName',
                            type: 'string',
                            initialValue: flowData?.comName,
                            hide: true,
                        },
                        {
                            field: 'comID',
                            type: 'string',
                            initialValue: flowData?.comID,
                            hide: true,
                        },
                        {
                            field: 'secondID',
                            type: 'string',
                            hide: true,
                            initialValue: flowData?.secondID,
                        },
                        {
                            field: 'firstID',
                            type: 'string',
                            initialValue: flowData?.firstID,
                            hide: true,
                        },
                        {
                            field: 'orgName',
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
                            label: '补充协议编号',
                            field: "contractNo",
                            type: 'string',
                            qnnDisabled: true,
                            initialValue: flowData?.contractNo,
                            span: 12,
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 9 },
                                    sm: { span: 9 }
                                },
                                wrapperCol: {
                                    xs: { span: 15 },
                                    sm: { span: 15 }
                                }
                            }
                        },
                        {
                            label: '补充协议名称',
                            field: "pp3",
                            type: 'string',
                            qnnDisabled: true,
                            initialValue: flowData?.pp3,
                            span: 12,
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 9 },
                                    sm: { span: 9 }
                                },
                                wrapperCol: {
                                    xs: { span: 15 },
                                    sm: { span: 15 }
                                }
                            }
                        },
                        {
                            field: 'zxCtFsContractId',
                            type: 'select',
                            qnnDisabled: true,
                            label: '合同名称',
                            initialValue: flowData?.zxCtFsContractId,
                            span: 12,
                            optionConfig: {
                                label: 'contractName',
                                value: 'zxCtFsContractId',
                            },
                            fetchConfig: { apiName: 'getZxCtFsContractList' },
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 9 },
                                    sm: { span: 9 }
                                },
                                wrapperCol: {
                                    xs: { span: 15 },
                                    sm: { span: 15 }
                                }
                            }
                        },
                        {
                            label: '合同类型',
                            field: "contractType",
                            type: 'string',
                            qnnDisabled: true,
                            initialValue: flowData?.contractType,
                            span: 12,
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 9 },
                                    sm: { span: 9 }
                                },
                                wrapperCol: {
                                    xs: { span: 15 },
                                    sm: { span: 15 }
                                }
                            }
                        },
                        {
                            label: '甲方名称',
                            field: "firstName",
                            type: 'string',
                            qnnDisabled: true,
                            initialValue: flowData?.firstName,
                            span: 12,
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 9 },
                                    sm: { span: 9 }
                                },
                                wrapperCol: {
                                    xs: { span: 15 },
                                    sm: { span: 15 }
                                }
                            }
                        },
                        {
                            label: '乙方名称',
                            field: "secondName",
                            type: 'string',
                            qnnDisabled: true,
                            initialValue: flowData?.secondName,
                            span: 12,
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 9 },
                                    sm: { span: 9 }
                                },
                                wrapperCol: {
                                    xs: { span: 15 },
                                    sm: { span: 15 }
                                }
                            }
                        },
                        {
                            label: '合同签订人',
                            field: "agent",
                            type: 'string',
                            qnnDisabled: true,
                            initialValue: flowData?.agent,
                            span: 12,
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 9 },
                                    sm: { span: 9 }
                                },
                                wrapperCol: {
                                    xs: { span: 15 },
                                    sm: { span: 15 }
                                }
                            }
                        },
                        {
                            label: '含税合同金额(万元)',
                            field: "contractCost",
                            type: 'number',
                            qnnDisabled: true,
                            initialValue: flowData?.contractCost,
                            span: 12,
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 9 },
                                    sm: { span: 9 }
                                },
                                wrapperCol: {
                                    xs: { span: 15 },
                                    sm: { span: 15 }
                                }
                            }
                        },
                        {
                            label: '不含税合同金额(万元)',
                            field: "contractCostNoTax",
                            type: 'number',
                            qnnDisabled: true,
                            initialValue: flowData?.contractCostNoTax,
                            span: 12,
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 9 },
                                    sm: { span: 9 }
                                },
                                wrapperCol: {
                                    xs: { span: 15 },
                                    sm: { span: 15 }
                                }
                            }
                        },
                        {
                            label: '合同税额(万元)',
                            field: "contractCostTax",
                            type: 'number',
                            qnnDisabled: true,
                            initialValue: flowData?.contractCostTax,
                            span: 12,
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 9 },
                                    sm: { span: 9 }
                                },
                                wrapperCol: {
                                    xs: { span: 15 },
                                    sm: { span: 15 }
                                }
                            }
                        },
                        {
                            label: '本期含税增减金额(万元)',
                            field: "pp4",
                            type: 'number',
                            qnnDisabled: true,
                            initialValue: flowData?.pp4,
                            span: 12,
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 9 },
                                    sm: { span: 9 }
                                },
                                wrapperCol: {
                                    xs: { span: 15 },
                                    sm: { span: 15 }
                                }
                            }
                        },
                        {
                            label: '本期不含税增减金额(万元)',
                            field: "pp4NoTax",
                            type: 'number',
                            qnnDisabled: true,
                            initialValue: flowData?.pp4NoTax,
                            span: 12,
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 9 },
                                    sm: { span: 9 }
                                },
                                wrapperCol: {
                                    xs: { span: 15 },
                                    sm: { span: 15 }
                                }
                            }
                        },
                        {
                            label: '本期增减税额(万元)',
                            field: "pp4Tax",
                            type: 'number',
                            qnnDisabled: true,
                            initialValue: flowData?.pp4Tax,
                            span: 12,
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 9 },
                                    sm: { span: 9 }
                                },
                                wrapperCol: {
                                    xs: { span: 15 },
                                    sm: { span: 15 }
                                }
                            }
                        },
                        {
                            label: '变更后含税金额(万元)',
                            field: "alterContractSum",
                            type: 'number',
                            qnnDisabled: true,
                            initialValue: flowData?.alterContractSum,
                            span: 12,
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 9 },
                                    sm: { span: 9 }
                                },
                                wrapperCol: {
                                    xs: { span: 15 },
                                    sm: { span: 15 }
                                }
                            }
                        },
                        {
                            label: '变更后不含税金额(万元)',
                            field: "alterContractSumNoTax",
                            type: 'number',
                            qnnDisabled: true,
                            initialValue: flowData?.alterContractSumNoTax,
                            span: 12,
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 9 },
                                    sm: { span: 9 }
                                },
                                wrapperCol: {
                                    xs: { span: 15 },
                                    sm: { span: 15 }
                                }
                            }
                        },
                        {
                            label: '变更后税额(万元)',
                            field: "alterContractSumTax",
                            type: 'number',
                            qnnDisabled: true,
                            initialValue: flowData?.alterContractSumTax,
                            span: 12,
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 9 },
                                    sm: { span: 9 }
                                },
                                wrapperCol: {
                                    xs: { span: 15 },
                                    sm: { span: 15 }
                                }
                            }
                        },
                        {
                            label: '是否抵扣',
                            field: 'isDeduct',
                            type: 'radio',
                            optionData: [
                                { label: '是', value: '1' },
                                { label: '否', value: '0' }
                            ],
                            qnnDisabled: true,
                            initialValue: flowData.isDeduct ? flowData.isDeduct : '0',
                            span: 12,
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 9 },
                                    sm: { span: 9 }
                                },
                                wrapperCol: {
                                    xs: { span: 15 },
                                    sm: { span: 15 }
                                }
                            }
                        },
                        {
                            label: '开工日期',
                            field: "startDate",
                            format: 'YYYY-MM-DD',
                            type: 'date',
                            qnnDisabled: true,
                            initialValue: flowData?.startDate,
                            span: 12,
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 9 },
                                    sm: { span: 9 }
                                },
                                wrapperCol: {
                                    xs: { span: 15 },
                                    sm: { span: 15 }
                                }
                            }
                        },
                        {
                            label: '竣工日期',
                            field: "endDate",
                            type: 'date',
                            format: 'YYYY-MM-DD',
                            qnnDisabled: true,
                            initialValue: flowData?.endDate,
                            span: 12,
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 9 },
                                    sm: { span: 9 }
                                },
                                wrapperCol: {
                                    xs: { span: 15 },
                                    sm: { span: 15 }
                                }
                            }
                        },
                        {
                            label: '合同工期',
                            field: "csTimeLimit",
                            type: 'string',
                            qnnDisabled: true,
                            initialValue: flowData?.csTimeLimit,
                            span: 12,
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 9 },
                                    sm: { span: 9 }
                                },
                                wrapperCol: {
                                    xs: { span: 15 },
                                    sm: { span: 15 }
                                }
                            }
                        },
                        {
                            field: 'pp1',
                            type: 'select',
                            qnnDisabled: true,
                            label: '批复单位',
                            initialValue: flowData?.pp1,
                            span: 12,
                            optionConfig: {
                                label: 'label',
                                value: 'value',
                            },
                            optionData: [
                                { label: '项目', value: '项目', },
                                { label: '公司', value: '公司', },
                                { label: '局', value: '局', }
                            ],
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 9 },
                                    sm: { span: 9 }
                                },
                                wrapperCol: {
                                    xs: { span: 15 },
                                    sm: { span: 15 }
                                }
                            }
                        },
                        {
                            label: '批复日期',
                            field: "replyDate",
                            type: 'date',
                            format: 'YYYY-MM-DD',
                            qnnDisabled: true,
                            initialValue: flowData?.replyDate,
                            span: 12,
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 9 },
                                    sm: { span: 9 }
                                },
                                wrapperCol: {
                                    xs: { span: 15 },
                                    sm: { span: 15 }
                                }
                            }
                        },
                        {
                            label: '变更内容',
                            field: "alterContent",
                            type: 'string',
                            qnnDisabled: true,
                            initialValue: flowData?.alterContent,
                            span: 12,
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 9 },
                                    sm: { span: 9 }
                                },
                                wrapperCol: {
                                    xs: { span: 15 },
                                    sm: { span: 15 }
                                }
                            }
                        },
                        {
                            label: '合同内容',
                            field: "content",
                            type: 'string',
                            qnnDisabled: true,
                            initialValue: flowData?.content,
                            span: 12,
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 9 },
                                    sm: { span: 9 }
                                },
                                wrapperCol: {
                                    xs: { span: 15 },
                                    sm: { span: 15 }
                                }
                            }
                        },
                        {
                            label: '变更原因',
                            field: "alterReason",
                            type: 'string',
                            qnnDisabled: true,
                            initialValue: flowData?.alterReason,
                            span: 12,
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 9 },
                                    sm: { span: 9 }
                                },
                                wrapperCol: {
                                    xs: { span: 15 },
                                    sm: { span: 15 }
                                }
                            }
                        },
                        {
                            label: '备注',
                            field: "remarks",
                            type: 'string',
                            qnnDisabled: true,
                            initialValue: flowData?.remarks,
                            span: 12,
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 9 },
                                    sm: { span: 9 }
                                },
                                wrapperCol: {
                                    xs: { span: 15 },
                                    sm: { span: 15 }
                                }
                            }
                        },
                        {
                            type: "qnnTable",
                            field: 'itemList',
                            qnnTableConfig: {
                                fetchConfig: (obj) => {
                                    if (flowData?.zxCtFsSideAgreementId) {
                                        return {
                                            apiName: 'getZxCtFsSideAgreementInventoryList',
                                            otherParams: {
                                                zxCtFsSideAgreementId: flowData?.zxCtFsSideAgreementId,
                                            }
                                        }
                                    } else if (obj?.qnnFormProps?.qnnformData?.initialValues?.apiBody?.zxCtFsSideAgreementId) {
                                        return {
                                            apiName: 'getZxCtFsSideAgreementInventoryList',
                                            otherParams: {
                                                zxCtFsSideAgreementId: obj.qnnFormProps.qnnformData.initialValues.apiBody.zxCtFsSideAgreementId,
                                            }
                                        }
                                    }

                                },
                                antd: {
                                    rowKey: "zxCtFsSideAgreementInventoryId",
                                    size: 'small',
                                },
                                paginationConfig: {
                                    position: 'bottom'
                                },
                                paginationConfig: false,
                                isShowRowSelect: false,
                                formConfig: [
                                    {
                                        table: {
                                            title: '变更类型',
                                            dataIndex: 'alterType',
                                            key: 'alterType',
                                            width: 120,
                                            align: 'center',
                                            fixed: 'left',
                                            render: (h) => {
                                                if (h === '1') {
                                                    return '新增'
                                                } else if (h === '2') {
                                                    return '修改'
                                                }
                                            }
                                        }
                                    },
                                    {
                                        table: {
                                            title: '清单编号',
                                            dataIndex: 'workNo',
                                            key: 'workNo',
                                            width: 140,
                                            align: 'center',
                                            fixed: 'left',
                                        }
                                    },
                                    {
                                        table: {
                                            dataIndex: 'workName',
                                            key: 'workName',
                                            align: 'center',
                                            title: '清单名称',
                                            width: 150,
                                        }
                                    },
                                    {
                                        table: {
                                            title: ' 单位',
                                            dataIndex: 'unit',
                                            key: 'unit',
                                            width: 100,
                                            align: 'center',
                                        }
                                    },
                                    {
                                        table: {
                                            title: '数量',
                                            dataIndex: 'qty',
                                            key: 'qty',
                                            width: 100,
                                            type: 'number',
                                            align: 'center',
                                        }
                                    },
                                    {
                                        table: {
                                            title: '含税单价',
                                            dataIndex: 'price',
                                            key: 'price',
                                            width: 120,
                                            align: 'center',
                                        }
                                    },
                                    {
                                        table: {
                                            title: '不含税单价',
                                            dataIndex: 'priceNoTax',
                                            key: 'priceNoTax',
                                            width: 120,
                                            align: 'center',
                                        }
                                    },
                                    {
                                        table: {
                                            title: '含税金额',
                                            dataIndex: 'contractSum',
                                            key: 'contractSum',
                                            width: 120,
                                            align: 'center',
                                        }
                                    },

                                    {
                                        table: {
                                            title: '不含税金额',
                                            dataIndex: 'contractSumNoTax',
                                            key: 'contractSumNoTax',
                                            width: 120,
                                            align: 'center',
                                        },
                                    },
                                    {
                                        table: {
                                            title: '不含税税额',
                                            dataIndex: 'contractSumTax',
                                            key: 'contractSumTax',
                                            width: 120,
                                            align: 'center',
                                        },
                                    },
                                    {
                                        table: {
                                            title: '变更后数量',
                                            dataIndex: 'changeQty',
                                            key: 'changeQty',
                                            width: 120,
                                            align: 'center',
                                        }
                                    },
                                    {
                                        table: {
                                            title: '变更后单价',
                                            dataIndex: 'changePrice',
                                            key: 'changePrice',
                                            width: 120,
                                            align: 'center',
                                        }
                                    },
                                    {
                                        table: {
                                            title: '变更后含税金额',
                                            dataIndex: 'changeContractSum',
                                            key: 'changeContractSum',
                                            width: 120,
                                            align: 'center',
                                        }
                                    },
                                    {
                                        table: {
                                            title: '变更后不含税金额',
                                            dataIndex: 'changeContractSumNoTax',
                                            key: 'changeContractSumNoTax',
                                            width: 150,
                                            align: 'center',
                                        }
                                    },
                                    {
                                        table: {
                                            title: '变更后税额',
                                            dataIndex: 'changeContractSumTax',
                                            key: 'changeContractSumTax',
                                            width: 150,
                                            align: 'center',
                                        }
                                    },
                                    {
                                        table: {
                                            title: '税率(%)',
                                            dataIndex: 'taxRate',
                                            key: 'taxRate',
                                            width: 100,
                                            align: 'center',
                                        }
                                    },
                                    {
                                        table: {
                                            title: '是否抵扣',
                                            dataIndex: 'isDeduct',
                                            key: 'isDeduct',
                                            width: 100,
                                            align: 'center',
                                            render: (h) => {
                                                if (h === '1') {
                                                    return '是'
                                                } else {
                                                    return '否'
                                                }
                                            },
                                        }
                                    },
                                    {
                                        table: {
                                            title: '备注',
                                            dataIndex: 'remarks',
                                            key: 'remarks',
                                            width: 150,
                                            align: 'center',
                                        }
                                    },
                                ],
                            }
                        },
                        {
                            label: "项目经营负责",
                            field: 'opinionField1',
                            type: 'textarea',
                            opinionField: true,
                            addShow: false,
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
                            label: "项目总经济师",
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
                            label: "项目经理",
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
                            label: "上报总部",
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
                            type: "textarea",
                            label: "上报总部(托管)",
                            field: "opinionField5",
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
                            label: "公司审核人",
                            field: "opinionField6",
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
                            label: "公司经营负责",
                            field: "opinionField7",
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
                            label: "公司总经济师",
                            field: "opinionField8",
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
                            label: "校对",
                            field: "opinionField9",
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
                            field: 'zxErpFileList',
                            type: 'files',
                            qnnDisabled: true,
                            initialValue: flowData?.zxErpFileList,
                            fetchConfig: {
                                apiName: 'upload'
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
                    flowId={'FsSideAmagment'}
                />
            </div>
        );
    }
}
export default index;
