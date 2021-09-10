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
                                    apiName: 'getZxSaSettleAuditArticleList',
                                    otherParams: {
                                        jsxZbid: params.baseData.zxSaEquipSettleAuditId
                                    }
                                },
                                antd: {
                                    rowKey: 'zxSaSettleAuditArticleId',
                                    size: 'small'
                                },
                                isShowRowSelect: false,
                                paginationConfig: {
                                    position: 'bottom'
                                },
                                wrappedComponentRef: (me) => {
                                    this.tableOne = me;
                                },
                                formConfig: [
                                    {
                                        isInTable: false,
                                        form: {
                                            label: '主键id',
                                            field: 'zxSaSettleAuditArticleId',
                                        }
                                    },
                                    {
                                        table: {
                                            title: '方向',
                                            dataIndex: 'jsxFx',
                                            key: 'jsxFx',
                                            width: 120,
                                            align: 'center',
                                            type: 'select'
                                        },
                                        form: {
                                            type: 'select',
                                            optionConfig: {
                                                label: 'label',
                                                value: 'value',
                                            },
                                            optionData: [
                                                { label: '增加', value: '1', },
                                                { label: '抵扣', value: '2', }
                                            ]
                                        }
                                    },
                                    {
                                        table: {
                                            title: '摘要',
                                            dataIndex: 'jsxZy',
                                            key: 'jsxZy',
                                            align: 'center',
                                            width: 150,
                                        }
                                    },
                                    {
                                        table: {
                                            title: '款项性质',
                                            dataIndex: 'jsxKxxzmc',
                                            key: 'jsxKxxzmc',
                                            align: 'center',
                                            width: 150,
                                            type: 'select'
                                        },
                                        form: {
                                            type: 'select',
                                            optionConfig: {
                                                label: 'label',
                                                value: 'label',
                                            },
                                            fetchConfig: {
                                                apiName: "selectZxSaiKxxzXiaLa"
                                            },
                                        }
                                    },
                                    {
                                        table: {
                                            title: '债权/债务',
                                            dataIndex: 'jsxZqzw',
                                            key: 'jsxZqzw',
                                            width: 150,
                                            align: 'center',
                                            type: 'select'
                                        },
                                        form: {
                                            type: 'select',
                                            optionConfig: { label: 'label', value: 'value' },
                                            optionData: [
                                                { label: '债权', value: '1' },
                                                { label: '债务', value: '2' }
                                            ]
                                        }
                                    },
                                    {
                                        table: {
                                            title: '当前计量',
                                            dataIndex: 'jsxDqjl',
                                            key: 'jsxDqjl',
                                            width: 150,
                                            align: 'center',
                                        },
                                    },
                                    {
                                        table: {
                                            title: '税额(元)',
                                            dataIndex: 'jsxSe',
                                            key: 'jsxSe',
                                            width: 150,
                                            align: 'center',
                                        }
                                    },
                                    {
                                        table: {
                                            title: '税率(%)',
                                            dataIndex: 'jsxSl',
                                            key: 'jsxSl',
                                            width: 100,
                                            align: 'center',
                                            type: "select",
                                        },
                                        form: {
                                            type: "select",
                                            optionConfig: {
                                                label: 'label',
                                                value: 'value',
                                            },
                                            optionData: [
                                                { label: '0', value: '0' },
                                                { label: '1', value: '1' },
                                                { label: '1.5', value: '1.5' },
                                                { label: '3', value: '3' },
                                                { label: '5', value: '5' },
                                                { label: '6', value: '6' },
                                                { label: '9', value: '9' },
                                                { label: '10', value: '10' },
                                                { label: '11', value: '11' },
                                                { label: '13', value: '13' },
                                                { label: '16', value: '16' },
                                                { label: '17', value: '17' },
                                            ]
                                        }
                                    },
                                    {
                                        table: {
                                            title: '税前金额',
                                            dataIndex: 'jsxSqje',
                                            key: 'jsxSqje',
                                            width: 150,
                                            align: 'center',
                                        }
                                    },
                                    {
                                        table: {
                                            title: '抵扣债权',
                                            dataIndex: 'qwjsdmxbZqzwnm',
                                            key: 'qwjsdmxbZqzwnm',
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
                                            title: '抵扣债权余额',
                                            dataIndex: 'qwjsdmxbZqzwye',
                                            key: 'qwjsdmxbZqzwye',
                                            width: 150,
                                            align: 'center',
                                        }
                                    },
                                    {
                                        table: {
                                            title: '抵扣金额',
                                            dataIndex: 'qwjsdmxbZhdkje',
                                            key: 'qwjsdmxbZhdkje',
                                            width: 150,
                                            align: 'center',
                                        }
                                    },
                                    {
                                        table: {
                                            title: '对应款项性质',
                                            dataIndex: 'jsxdkkxxzmc',
                                            key: 'jsxdkkxxzmc',
                                            width: 150,
                                            align: 'center',
                                        }
                                    },
                                    {
                                        table: {
                                            dataIndex: 'jsxDqrq',
                                            key: 'jsxDqrq',
                                            title: '到期日期',
                                            width: 150,
                                            format: 'YYYY-MM-DD',
                                        }
                                    },
                                    {
                                        table: {
                                            dataIndex: 'jsxYjfkrq',
                                            key: 'jsxYjfkrq',
                                            title: '预计付款日期',
                                            width: 150,
                                            format: 'YYYY-MM-DD',
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
