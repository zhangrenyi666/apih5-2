import React from "react"
import QnnTable from "qnn-table"

export default (props) => {
    const config = {
        ...props,
        rowKey: "zxCtOtherWorksId",
        isShowRowSelect:false,
        formConfig: [
            {
                isInTable: false,
                form: {
                    label: '主键id',
                    field: 'zxCtOtherWorksId',
                    hide: true
                }
            },
            {
                table: {
                    title: '清单编号',
                    dataIndex: 'workNo',
                    key: 'workNo',
                    width: 150,
                    align: 'center',
                }
            },
            {
                table: {
                    title: '清单名称',
                    width: 180,
                    dataIndex: 'workName',
                    key: 'workName',
                    align: 'center',
                }
            },
            {
                table: {
                    title: '计量单位',
                    dataIndex: 'unit',
                    key: 'unit',
                    width: 100,
                    align: 'center',
                }
            },
            {
                table: {
                    title: '合同数量',
                    dataIndex: 'contractQty',
                    key: 'contractQty',
                    width: 100,
                    align: 'center',
                }
            },
            {
                table: {
                    title: '含税合同单价(元)',
                    dataIndex: 'price',
                    key: 'price',
                    width: 150,
                    align: 'center',
                }
            },
            {
                table: {
                    title: '不含税合同单价(元)',
                    dataIndex: 'contractPriceNoTax',
                    key: 'contractPriceNoTax',
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
                    }
                }
            },
            {
                table: {
                    title: '含税合同金额',
                    dataIndex: 'contractAmt',
                    key: 'contractAmt',
                    width: 120,
                    align: 'center' 
                }
            },

            {
                table: {
                    title: '不含税合同金额',
                    dataIndex: 'amtNoTax',
                    key: 'amtNoTax',
                    width: 120,
                    align: 'center' 
                },
            },
            {
                table: {
                    title: '备注',
                    dataIndex: 'remarks',
                    key: 'remarks',
                    width: 150,
                    align: 'center',
                }
            }
        ]
    };
    return <QnnTable {...config} />
}