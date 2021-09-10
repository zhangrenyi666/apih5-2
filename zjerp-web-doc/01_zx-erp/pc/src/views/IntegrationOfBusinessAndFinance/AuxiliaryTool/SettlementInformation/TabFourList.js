import React, { Component } from "react";
import QnnForm from "../../../modules/qnn-table/qnn-form";
class index extends Component {
    render() {
        let params = this.props
        return (
            <div style={{ padding: '10px' }}>
                <QnnForm
                    fetch={this.props.myFetch}
                    formConfig={[
                        {
                            type: 'qnnTable',
                            field: 'table1',
                            qnnTableConfig: {
                                fetchConfig: {
                                    apiName: 'getZxSaSettleAuditCostaccountList',
                                    otherParams: {
                                        mainID: params.baseData.zxSaEquipSettleAuditId
                                    }
                                },
                                antd: {
                                    rowKey: 'zxSaSettleAuditCostaccountId',
                                    size: 'small'
                                },
                                isShowRowSelect: false,
                                paginationConfig: {
                                    position: 'bottom'
                                },
                                formConfig: [
                                    {
                                        isInTable: false,
                                        form: {
                                            label: '主键id',
                                            field: 'zxSaSettleAuditCostaccountId',
                                        }
                                    },
                                    {
                                        table: {
                                            title: '费用名称',
                                            dataIndex: 'feeId',
                                            key: 'feeId',
                                            align: 'center',
                                            width: 150,
                                            type: 'select',
                                        },
                                        form: {
                                            type: 'select',
                                            optionConfig: {
                                                label: 'fmc',
                                                value: 'ffjnm',
                                            },
                                            fetchConfig: {
                                                apiName: "selectZxSaFiFyxmTree"
                                            }
                                        }
                                    },
                                    {
                                        table: {
                                            title: '费用编号',
                                            dataIndex: 'feeCode',
                                            key: 'feeCode',
                                            width: 150,
                                            align: 'center',
                                        }
                                    },
                                    {
                                        table: {
                                            title: '专项类别',
                                            dataIndex: 'accountTypeName',
                                            key: 'accountTypeName',
                                            width: 170,
                                            align: 'center',
                                            type: 'select',
                                        },
                                        form: {
                                            type: 'select',
                                            optionConfig: {
                                                label: 'lsxmlbLbmc',
                                                value: 'lsxmlbLbmc',
                                            },
                                            fetchConfig: {
                                                apiName: 'getZxSaFiXmlbList',
                                            }
                                        }
                                    },
                                    {
                                        table: {
                                            title: '专项编号',
                                            dataIndex: 'accountProjCode',
                                            key: 'accountProjCode',
                                            width: 150,
                                            align: 'center',
                                        }
                                    },
                                    {
                                        table: {
                                            title: '专项名称',
                                            dataIndex: 'accountProjName',
                                            key: 'accountProjName',
                                            align: 'center',
                                            width: 150,
                                            type: 'select',
                                        },
                                        form: {
                                            type: 'select',
                                            optionConfig: {
                                                label: 'lshsxmXmmc',
                                                value: 'lshsxmXmmc',
                                            },
                                            fetchConfig: (obj) => {
                                                return {
                                                    apiName: "getZxSaFiHsxmListTree",
                                                    otherParams: {
                                                        lshsxmLbmc: obj.props.rowData?.accountTypeName
                                                    }
                                                }

                                            }
                                        }
                                    },
                                    {
                                        table: {
                                            title: '金额(元)',
                                            dataIndex: 'amt',
                                            key: 'amt',
                                            width: 150,
                                            align: 'center',
                                        }
                                    },
                                    {
                                        table: {
                                            title: '产品核算',
                                            dataIndex: 'lscpCpmc',
                                            key: 'lscpCpmc',
                                            align: 'center',
                                            width: 150,
                                            type: 'select',
                                        },
                                        form: {
                                            type: 'select',
                                            optionConfig: {
                                                label: 'lscpCpmc',
                                                value: 'lscpCpmc',
                                            },
                                            fetchConfig: {
                                                apiName: "getZxSaFiCpzdXiaLaList",
                                                otherParams: {
                                                    orgID: params.baseData.orgID
                                                }
                                            }
                                        }
                                    },
                                    {
                                        table: {
                                            title: '摘要',
                                            dataIndex: 'summary',
                                            key: 'summary',
                                            align: 'center',
                                            width: 150,
                                        }
                                    }
                                ]
                            }
                        }
                    ]}
                />
            </div>
        );
    }
}
export default index;
