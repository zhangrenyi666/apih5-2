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
                                rowDisabledCondition: () => { return params.clickName === 'detail' },
                                fetchConfig: {
                                    apiName: 'getZxSaSettleAuditMeteringList',
                                    otherParams: {
                                        jsxZbid: params.baseData.zxSaEquipSettleAuditId
                                    }
                                },
                                antd: {
                                    rowKey: 'zxSaSettleAuditMeteringId',
                                    size: 'small'
                                },
                                actionBtnsContainerStyle: {
                                    textAlign: 'right'
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
                                            field: 'zxSaSettleAuditMeteringId',
                                        }
                                    },
                                    {
                                        table: {
                                            title: '摘要',
                                            dataIndex: 'jsxZy',
                                            key: 'jsxZy',
                                            align: 'center',
                                            fixed: 'left',
                                            width: 150,
                                        }
                                    },
                                    {
                                        table: {
                                            title: '债务编号',
                                            dataIndex: 'qwfjlbZqzwnm',
                                            key: 'qwfjlbZqzwnm',
                                            width: 150,
                                            align: 'center',
                                            type: 'select',
                                        },
                                        form: {
                                            type: 'select',
                                            optionConfig: {
                                                label: 'qwzqzwbBh',
                                                value: 'qwzqzwbNm',
                                            },
                                            fetchConfig: {
                                                apiName: "getZxSaFiQwzqzwbXialaList",
                                                otherParams: {
                                                    htbh: params.baseData.contractNo
                                                }
                                            }
                                        }
                                    },
                                    {
                                        table: {
                                            title: '款项性质名称',
                                            dataIndex: 'jsxKxxzmc',
                                            key: 'jsxKxxzmc',
                                            align: 'center',
                                            width: 150,
                                        },
                                    },
                                    {
                                        table: {
                                            title: '余额',
                                            dataIndex: 'qwfjlbDqye',
                                            key: 'qwfjlbDqye',
                                            width: 150,
                                            align: 'center',
                                        }
                                    },
                                    {
                                        table: {
                                            title: '抵扣金额',
                                            dataIndex: 'qwfjlbJe',
                                            key: 'qwfjlbJe',
                                            width: 150,
                                            align: 'center',
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
