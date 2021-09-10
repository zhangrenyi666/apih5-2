import React, { Component } from "react";
import { Form } from "../../modules/work-flow";
import { Modal, Button } from 'antd';
import QnnForm from "../../modules/qnn-form";
let config = {
    //流程专属配置   
    parameterName: 'firstId',
    editDocCdnAddress: window.configs.ntkoAddress,
    workFlowConfig: {
        title: ["其他合同补充协议", 'contractName', 'contractNo'], //标题字段
        apiNameByAdd: "zxCtOtherSupplyAgreementReviewFlowApply",
        apiNameByUpdate: "updateZxCtOtherSupplyAgreement",
        apiNameByGet: "getZxCtOtherSupplyAgreementDetail",
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
    },
    formLayout: "leftDoc",
    formType: "descriptions",
    descriptionsConfig: {
        "layout": "horizontal",
        "column": 12,
        size: "small"
    },
    filesFieldName: "zxErpFileList",
    getTodoDataFetchConfig: window.qnnGetTodoDataFetchConfig,
};
class index extends Component {
    constructor(props) {
        super(props);
        this.state = {
            visibleBjdh: false,
        }
    }
    handleCancelBjdh = () => {
        this.setState({ visibleBjdh: false });
    }
    render() {
        const { isInQnnTable, flowData = {} } = this.props;
        const { visibleBjdh } = this.state;
        return (
            <div style={{ height: isInQnnTable ? "" : "100vh" }}>
                <Form
                    {...this.props}
                    {...config}
                    wrappedComponentRef={(me) => {
                      this.flowForm = me;
                    }}
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
                            initialValue: 'zxCtOtherSupplyAgreementReviewAp',
                            hide: true,
                        },
                        {
                            field: 'firstId',
                            type: 'string',
                            initialValue: flowData?.firstId,
                            hide: true,
                        },
                        {
                            field: 'zxCtOtherSupplyAgreementId',
                            type: 'string',
                            initialValue: flowData?.zxCtOtherSupplyAgreementId,
                            hide: true,
                        },
                        {
                            field: 'zxCtOtherManageId',
                            type: 'string',
                            initialValue: flowData?.zxCtOtherManageId,
                            hide: true,
                        },
                        {
                            label: '补充协议编号',
                            field: "contractNo",
                            type: 'string',
                            qnnDisabled: true,
                            initialValue: flowData?.contractNo,
                            span: 12,
                        },
                        {
                            label: '补充协议名称',
                            field: "proposer",
                            type: 'string',
                            qnnDisabled: true,
                            initialValue: flowData?.proposer,
                            span: 12,
                        },
                        {
                            field: 'contractName',
                            type: 'string',
                            qnnDisabled: true,
                            label: '合同名称',
                            initialValue: flowData?.contractName,
                            span: 12,
                        },
                        {
                            label: '合同类型',
                            field: "contractType",
                            type: 'string',
                            qnnDisabled: true,
                            initialValue: flowData?.contractType,
                            span: 12,
                        },
                        {
                            label: '甲方名称',
                            field: "firstName",
                            type: 'string',
                            qnnDisabled: true,
                            initialValue: flowData?.firstName,
                            span: 12,
                        },
                        {
                            label: '乙方名称',
                            field: "secondName",
                            type: 'string',
                            qnnDisabled: true,
                            initialValue: flowData?.secondName,
                            span: 12,
                        },
                        {
                          field: 'modelType',
                          type: 'string',
                          qnnDisabled: true,
                          label: '正文模板类型',
                          initialValue: ' ',
                          span: 12,
                        },
                        {
                          type: "component",
                          field: "component66",
                          label: '信息查看',
                          Component: obj => {
                            return (
                              <Button type="primary" onClick={() => {
                                this.setState({
                                  visibleBjdh: true
                                })
                              }}>清单查看</Button>
                            );
                          },
                          span: 12,
                        },
                        {
                          field: 'editList',
                          type: 'string',
                          qnnDisabled: true,
                          label: '清单编辑',
                          initialValue: ' ',
                          span: 12,
                        },
                        {
                          field: 'initiateFlowReview',
                          type: 'textarea',
                          label: '主要内容',
                          opinionField: true,
                          initialValue: flowData?.initiateFlowReview,
                          span: 12,
                        },
                        {
                            type: "textarea",
                            label: "项目各部门会签",
                            field: "opinionField2",
                            opinionField: true,
                            addShow: false,
                            span: 12,
                          },
                          {
                            type: "textarea",
                            label: "项目领导会签",
                            field: "opinionField3",
                            opinionField: true,
                            addShow: false,
                            span: 12,
                          },
                          {
                            type: "textarea",
                            label: "公司主管部门审核",
                            field: "opinionField4",
                            opinionField: true,
                            addShow: false,
                            span: 12,
                          },
                          {
                            type: "textarea",
                            label: "公司各部门(含法律部)会签",
                            field: "opinionField6",
                            opinionField: true,
                            addShow: false,
                            span: 12,
                          },
                          {
                            type: "textarea",
                            label: "公司主管部门领导审批",
                            field: "opinionField5",
                            opinionField: true,
                            addShow: false,
                            span: 12,
                          },
                          {
                            type: "textarea",
                            label: "公司总经理审批",
                            field: "opinionField7",
                            opinionField: true,
                            addShow: false,
                            span: 12,
                          },
                          {
                            type: "textarea",
                            label: "总部主管部门",
                            field: "opinionField8",
                            opinionField: true,
                            addShow: false,
                            span: 12,
                          },
                          {
                            type: "textarea",
                            label: "总部各部门会签",
                            field: "opinionField9",
                            opinionField: true,
                            addShow: false,
                            span: 12,
                          },
                          {
                            type: "textarea",
                            label: "总部领导会签",
                            field: "opinionField10",
                            opinionField: true,
                            addShow: false,
                            span: 12,
                          },
                          {
                            type: "textarea",
                            label: "托管公司主管",
                            field: "opinionField11",
                            opinionField: true,
                            addShow: false,
                            span: 12,
                          },
                          {
                            type: "textarea",
                            label: "托管公司各部门会签",
                            field: "opinionField12",
                            opinionField: true,
                            addShow: false,
                            span: 12,
                          },
                          {
                            type: "textarea",
                            label: "托管公司领导审批",
                            field: "opinionField13",
                            opinionField: true,
                            addShow: false,
                            span: 12,
                          },
                          {
                            type: "textarea",
                            label: "事业部审核",
                            field: "opinionField15",
                            opinionField: true,
                            addShow: false,
                            span: 12,
                          },
                          {
                            type: "textarea",
                            label: "事业部领导审批",
                            field: "opinionField16",
                            opinionField: true,
                            addShow: false,
                            span: 12,
                          },
                          {
                            type: "textarea",
                            label: "相关事业部审核",
                            field: "opinionField17",
                            opinionField: true,
                            addShow: false,
                            span: 12,
                          },
                          {
                            type: "textarea",
                            label: "相关事业部领导审批",
                            field: "opinionField18",
                            opinionField: true,
                            addShow: false,
                            span: 12,
                          },
                          {
                            type: "textarea",
                            label: "法律顾问审核",
                            field: "opinionField19",
                            opinionField: true,
                            addShow: false,
                            span: 12,
                          },
                          {
                            type: "textarea",
                            label: "合同校对",
                            field: "opinionField14",
                            opinionField: true,
                            addShow: false,
                            span: 12,
                          },
                    ]}
                    {...this.props.workFlowConfig}
                    {...config.workFlowConfig}
                    flowId={'zxCtOtherSupplyAgreementReviewAp'}
                />
                <Modal
                    width='1200px'
                    style={{ top: '0' }}
                    title="详细信息"
                    visible={visibleBjdh}
                    footer={null}
                    onCancel={this.handleCancelBjdh}
                    bodyStyle={{ width: '1200px' }}
                    centered={true}
                    destroyOnClose={this.handleCancelBjdh}
                    wrapClassName={'modals'}
                >
                    <QnnForm
                        fetch={this.props.myFetch}
                        fetchConfig={{
                            apiName: 'getZxCtOtherSupplyAgreementDetail',
                            otherParams: () => {
                                if (this.flowForm.rootBody?.workId) {
                                    return {
                                        zxCtOtherSupplyAgreementId: this.flowForm?.qnnForm?.form?.getFieldValue('apiBody').zxCtOtherSupplyAgreementId
                                    }
                                } else {
                                    return {
                                        zxCtOtherSupplyAgreementId: flowData?.zxCtOtherSupplyAgreementId
                                    }
                                }
                            }
                        }}
                        formConfig={[
                            {
                                type: "qnnTable",
                                incToForm: true,
                                field: "zxCtOtherSupplyAgreementWorksList",
                                qnnTableConfig: {
                                    antd: {
                                        rowKey: 'zxCtOtherSupplyAgreementWorksId',
                                        size: 'small',
                                        scroll: {
                                            y: document.documentElement.clientHeight * 0.6
                                        }
                                    },
                                    drawerConfig: {
                                        width: '900px'
                                    },
                                    pageSize: 999,
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
                                                    } else {
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
                                                align: 'center',
                                            },
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
                                            },
                                        },
                                        {
                                            table: {
                                                title: '变更后含税金额',
                                                dataIndex: 'changeContractSum',
                                                key: 'changeContractSum',
                                                width: 120,
                                                align: 'center',
                                                render: (val, rowData) => {
                                                    return rowData.changeContractSum ? rowData.changeContractSum.toFixed(2) : 0
                                                }
                                            }
                                        },
                                        {
                                            table: {
                                                title: '变更后不含税金额',
                                                dataIndex: 'changeContractSumNoTax',
                                                key: 'changeContractSumNoTax',
                                                width: 150,
                                                align: 'center',
                                                render: (val, rowData) => {
                                                    return rowData.changeContractSumNoTax ? rowData.changeContractSumNoTax.toFixed(2) : 0
                                                }
                                            }
                                        },
                                        {
                                            table: {
                                                title: '变更后税额',
                                                dataIndex: 'changeContractSumTax',
                                                key: 'changeContractSumTax',
                                                width: 150,
                                                align: 'center',
                                                render: (val, rowData) => {
                                                    return rowData.changeContractSumTax ? rowData.changeContractSumTax.toFixed(2) : 0
                                                }
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
                                                dataIndex: 'remark',
                                                key: 'remark',
                                                width: 150,
                                                align: 'center',
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
