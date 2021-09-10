import React, { Component } from "react";
import QnnForm from "../../modules/qnn-table/qnn-form";
import { message as Msg } from 'antd';
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
                                fetchConfig: () => {
                                    if (params.tabIndex === '4') {
                                        return {
                                            apiName: 'getZxSaSettleAuditMeteringList',
                                            otherParams: {
                                                jsxId: params.baseData.zxSaEquipSettleAuditId
                                            }
                                        }
                                    }
                                    return {}
                                },
                                antd: {
                                    rowKey: 'zxSaSettleAuditMeteringId',
                                    size: 'small'
                                },
                                actionBtnsContainerStyle: {
                                    textAlign: 'right'
                                },
                                isShowRowSelect: true,
                                actionBtns: [
                                    {
                                        name: 'addRow',
                                        icon: 'plus',
                                        type: 'primary',
                                        label: '新增行',
                                        hide: () => { if (params.clickName === 'detail') return true },
                                        addRowDefaultData: { addFlag: '1' }
                                    },
                                    {
                                        name: 'delRow',
                                        icon: 'del',
                                        type: 'danger',
                                        label: '删除',
                                        hide: () => { if (params.clickName === 'detail') return true },
                                    },
                                    {
                                        name: 'diyButton',
                                        type: 'primary',
                                        label: '保存',
                                        field: "diyButton",
                                        disabled: () => {
                                            return params.baseData.tcje !== 0 || params.baseData.tcse !== 0
                                        },
                                        hide: () => { if (params.clickName === 'detail') return true },
                                        onClick: async (obj) => {
                                            let meterList = await obj.qnnTableInstance.getTableData()
                                            await meterList.map((item) => {
                                                item.jsxId = this.props.baseData.zxSaEquipSettleAuditId
                                                return item
                                            })
                                            obj.btnCallbackFn.setBtnsLoading('add', 'diyButton');
                                            let params = {}
                                            params.zxSaEquipSettleAuditId = this.props.baseData.zxSaEquipSettleAuditId
                                            params.meterList = meterList
                                            this.props.myFetch('batchSaveZxSaSettleAuditMetering', params)
                                                .then(({ success, message }) => {
                                                    if (success) {
                                                        obj.btnCallbackFn.setBtnsLoading('remove', 'diyButton');
                                                        this.tableOne.refresh()
                                                        Msg.success(message)
                                                    } else {
                                                        obj.btnCallbackFn.setBtnsLoading('remove', 'diyButton');
                                                        Msg.error(message)
                                                    }
                                                })

                                        }
                                    }
                                ],
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
                                            field: 'zxSaSettleAuditMeteringId',
                                        }
                                    },
                                    {
                                        table: {
                                            title: '摘要',
                                            dataIndex: 'jsxZy',
                                            key: 'jsxZy',
                                            tdEdit: true,
                                            align: 'center',
                                            fixed: 'left',
                                            width: 150,
                                        },
                                        form: {
                                            required: true,
                                            type: 'string',
                                            placeholder: '请输入',
                                        }
                                    },
                                    {
                                        table: {
                                            title: '债务编号',
                                            dataIndex: 'qwfjlbZqzwnm',
                                            key: 'qwfjlbZqzwnm',
                                            tdEdit: true,
                                            width: 150,
                                            align: 'center',
                                            type: 'selectByQnnTable',
                                        },
                                        form: {
                                            required: true,
                                            allowClear: false,
                                            type: 'selectByQnnTable',
                                            optionConfig: {
                                                label: 'qwzqzwbBh',
                                                value: 'qwzqzwbNm',
                                            },
                                            dropdownMatchSelectWidth: 900,
                                            qnnTableConfig: {
                                                antd: {
                                                    rowKey: "qwzqzwbNm"
                                                },
                                                fetchConfig: (obj) => {
                                                    return {
                                                        apiName: "getZxSaFiQwzqzwbXialaList",
                                                        otherParams: {
                                                            htbh: params.baseData.contractNo
                                                        }
                                                    }

                                                },
                                                searchBtnsStyle: "inline",
                                                formConfig: [
                                                    {
                                                        isInForm: false,
                                                        table: {
                                                            dataIndex: "qwzqzwbBh",
                                                            title: "编号",
                                                            width: 150,
                                                        },
                                                        form: {
                                                            type: "string"
                                                        }
                                                    },
                                                    {
                                                        isInForm: false,
                                                        table: {
                                                            dataIndex: "qwzqzwyebYe",
                                                            title: "余额",
                                                            width: 120,
                                                        },
                                                        form: {
                                                            type: "number"
                                                        }
                                                    },
                                                    {
                                                        isInForm: false,
                                                        table: {
                                                            dataIndex: "kxxzmc",
                                                            title: "对应款项性质",
                                                            width: 120,
                                                        },
                                                        form: {
                                                            type: "string"
                                                        }
                                                    },
                                                    {
                                                        isInForm: false,
                                                        table: {
                                                            dataIndex: "htbh",
                                                            title: "合同编号",
                                                            width: 150,
                                                        },
                                                        form: {
                                                            type: "string"
                                                        }
                                                    },
                                                    {
                                                        isInForm: false,
                                                        table: {
                                                            dataIndex: "htmc",
                                                            title: "合同名称",
                                                            width: 150,
                                                        },
                                                        form: {
                                                            type: "string"
                                                        }
                                                    },
                                                    {
                                                        isInForm: false,
                                                        table: {
                                                            dataIndex: "xmbh",
                                                            title: "项目编号",
                                                            width: 150,
                                                        },
                                                        form: {
                                                            type: "string"
                                                        }
                                                    },
                                                    {
                                                        isInForm: false,
                                                        table: {
                                                            dataIndex: "xmmc",
                                                            title: "项目名称",
                                                            width: 150,
                                                        },
                                                        form: {
                                                            type: "string"
                                                        }
                                                    },
                                                    {
                                                        isInForm: false,
                                                        table: {
                                                            dataIndex: "dfdwmc",
                                                            title: "往来单位",
                                                            width: 150,
                                                        },
                                                        form: {
                                                            type: "string"
                                                        }
                                                    }
                                                ],
                                            },
                                            onChange: async (val, obj) => {
                                                if (val) {
                                                    let rowData = await obj.qnnTableInstance.getEditedRowData()
                                                    rowData.qwfjlbZqzwnm = obj.itemData?.qwzqzwbNm
                                                    rowData.jsxKxxzmc = obj.itemData?.kxxzmc  //款项性质名称
                                                    rowData.qwfjlbDqye = obj.itemData?.qwzqzwyebYe  //余额
                                                    await obj.qnnTableInstance.setEditedRowData(rowData)
                                                }
                                            }
                                        }
                                    },
                                    {
                                        table: {
                                            title: '款项性质名称',
                                            dataIndex: 'jsxKxxzmc',
                                            key: 'jsxKxxzmc',
                                            tdEdit: false,
                                            align: 'center',
                                            width: 150,
                                        },
                                    },
                                    {
                                        table: {
                                            title: '余额',
                                            dataIndex: 'qwfjlbDqye',
                                            key: 'qwfjlbDqye',
                                            tdEdit: false,
                                            width: 150,
                                            align: 'center',
                                        }
                                    },
                                    {
                                        table: {
                                            title: '抵扣金额',
                                            dataIndex: 'qwfjlbJe',
                                            key: 'qwfjlbJe',
                                            tdEdit: true,
                                            width: 150,
                                            align: 'center',
                                        },
                                        form: {
                                            type: 'number',
                                            onBlur: async (val, obj) => {
                                                let rowData = await obj.qnnTableInstance.getEditedRowData()
                                                let value = rowData.qwfjlbJe
                                                if (rowData.qwfjlbDqye && rowData.qwfjlbDqye < value) {
                                                    Msg.warn('抵扣金额不能大于余额')
                                                    rowData.qwfjlbJe = null
                                                    await obj.qnnTableInstance.setEditedRowData(rowData)
                                                } else {
                                                    rowData.qwfjlbJe = value
                                                    await obj.qnnTableInstance.setEditedRowData(rowData)
                                                }
                                            }
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
