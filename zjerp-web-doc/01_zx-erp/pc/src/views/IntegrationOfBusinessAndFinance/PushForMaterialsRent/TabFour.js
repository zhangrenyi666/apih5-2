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
                            type: "component",
                            field: "component1",
                            Component: obj => {
                                return (
                                    <div style={{ background: '#f0f2f5', padding: '8px 12px', color: 'red' }}>
                                        说明：当【数据校验】标签的“本期调差金额”、“本期税额调差”均为0时，才能保存“结算项”、“负计量”、“成本科目”！
                                    </div>
                                );
                            }
                        },
                        {
                            type: 'qnnTable',
                            field: 'table1',
                            qnnTableConfig: {
                                rowDisabledCondition: () => { return params.clickName === 'detail' },
                                fetchConfig: () => {
                                    if (params.tabIndex === '3') {
                                        return {
                                            apiName: 'getZxSaSettleAuditArticleList',
                                            otherParams: {
                                                jsxZbid: params.baseData.zxCtSuppliesLeaseSettlementListId
                                            }
                                        }
                                    }
                                    return {}
                                },
                                antd: {
                                    rowKey: 'zxSaSettleAuditArticleId',
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
                                        hide: () => { if (params.clickName === 'detail') return true },
                                        disabled: () => {
                                            return params.baseData.tcje !== 0 || params.baseData.tcse !== 0
                                        },
                                        onClick: async (obj) => {
                                            let emptyData = []
                                            let exceedData = false
                                            let articleList = await obj.qnnTableInstance.getTableData()
                                            await articleList.map((item) => {
                                                if (!item.jsxFx) emptyData.push('方向')
                                                if (!item.jsxZy) emptyData.push('摘要')
                                                if (item.jsxFx && item.jsxFx === '1') {
                                                    if (!item.jsxKxxzmc) emptyData.push('款项性质')
                                                    if (!item.jsxDqjl) emptyData.push('当前计量')
                                                    if (!item.jsxSe) emptyData.push('税额')
                                                    if (!item.jsxSl) emptyData.push('税率')
                                                } else if (item.jsxFx && item.jsxFx === '2') {
                                                    if (!item.qwjsdmxbZqzwnm) emptyData.push('抵扣债权')
                                                    if (!item.qwjsdmxbZhdkje) emptyData.push('抵扣金额')
                                                }
                                                if (!item.jsxDqrq) emptyData.push('到期日期')
                                                if (!item.jsxYjfkrq) emptyData.push('预计付款日期')
                                                if (item.qwjsdmxbZqzwye < item.qwjsdmxbZhdkje) {
                                                    exceedData = true
                                                }
                                                return item
                                            })
                                            if (exceedData) {
                                                Msg.warn('抵扣金额不能超过抵扣债权余额')
                                            } else if (emptyData.length > 0) {
                                                Msg.warn(`请填写${emptyData.join()}`)
                                            } else {
                                                await articleList.map((item) => {
                                                    item.jsxZbid = this.props.baseData.zxCtSuppliesLeaseSettlementListId
                                                    return item
                                                })
                                                obj.btnCallbackFn.setBtnsLoading('add', 'diyButton');
                                                let params = {}
                                                params.zxCtSuppliesLeaseSettlementListId = this.props.baseData.zxCtSuppliesLeaseSettlementListId
                                                params.articleList = articleList
                                                this.props.myFetch('batchSaveSpZxSaSettleAuditArticle', params)
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
                                            tdEdit: true,
                                            fixed: 'left',
                                            type: 'select'
                                        },
                                        form: {
                                            required: true,
                                            placeholder: '请选择',
                                            type: 'select',
                                            allowClear: false,//是否显示清除按钮
                                            optionConfig: {
                                                label: 'label',
                                                value: 'value',
                                            },
                                            optionData: [
                                                { label: '增加', value: '1', },
                                                { label: '抵扣', value: '2', }
                                            ],
                                            onChange: async (val, obj) => {
                                                let rowData = await obj.qnnTableInstance.getEditedRowData()
                                                rowData.jsxKxxzmc = null
                                                rowData.jsxZqzw = null
                                                rowData.jsxDqjl = null
                                                rowData.jsxSe = null
                                                rowData.jsxSl = null
                                                rowData.jsxSqje = null
                                                rowData.qwjsdmxbZqzwnm = null
                                                rowData.qwjsdmxbZhdkje = null
                                                rowData.qwjsdmxbZqzwye = null
                                                await obj.qnnTableInstance.setEditedRowData(rowData)
                                            }
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
                                            title: '款项性质',
                                            dataIndex: 'jsxKxxzmc',
                                            key: 'jsxKxxzmc',
                                            tdEdit: (obj) => {
                                                if (obj.jsxFx === '1') return true
                                                return false
                                            },
                                            align: 'center',
                                            width: 150,
                                        },
                                        form: {
                                            dependencies: ['jsxFx'],
                                            dependenciesReRender: true,
                                            required: true,
                                            type: 'selectByQnnTable',
                                            optionConfig: {
                                                label: 'label',
                                                value: 'label',
                                            },
                                            dropdownMatchSelectWidth: 400,
                                            qnnTableConfig: {
                                                antd: {
                                                    rowKey: "value"
                                                },
                                                fetchConfig: {
                                                    apiName: "selectZxSaiKxxzXiaLa"
                                                },
                                                rowSelection: { clickRowCanSelect: false },
                                                formConfig: [{
                                                    table: {
                                                        dataIndex: "label",
                                                        key: 'value'
                                                    }
                                                }]
                                            },
                                            onChange: async (val, obj) => {
                                                let rowData = await obj.qnnTableInstance.getEditedRowData()
                                                rowData.jsxKxxzmc = obj.itemData?.label
                                                rowData.jsxZqzw = obj.itemData?.type  //债权/债务
                                                await obj.qnnTableInstance.setEditedRowData(rowData)
                                            }
                                        }
                                    },
                                    {
                                        table: {
                                            title: '债权/债务',
                                            dataIndex: 'jsxZqzw',
                                            key: 'jsxZqzw',
                                            tdEdit: false,
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
                                            tdEdit: (obj) => {
                                                if (obj.jsxFx === '1') return true
                                                return false
                                            },
                                        },
                                        form: {
                                            dependencies: ['jsxFx'],
                                            dependenciesReRender: true,
                                            type: 'number',
                                            onBlur: async (val, obj) => {
                                                let rowData = await obj.qnnTableInstance.getEditedRowData()
                                                let value = rowData.jsxDqjl
                                                rowData.jsxDqjl = value
                                                rowData.jsxSqje = value - (rowData.jsxSe ? rowData.jsxSe : 0)
                                                await obj.qnnTableInstance.setEditedRowData(rowData)
                                            }
                                        }
                                    },
                                    {
                                        table: {
                                            title: '税额(元)',
                                            dataIndex: 'jsxSe',
                                            key: 'jsxSe',
                                            width: 150,
                                            align: 'center',
                                            tdEdit: (obj) => {
                                                if (obj.jsxFx === '1') return true
                                                return false
                                            },
                                        },
                                        form: {
                                            dependencies: ['jsxFx'],
                                            dependenciesReRender: true,
                                            type: 'number',
                                            onBlur: async (val, obj) => {
                                                let rowData = await obj.qnnTableInstance.getEditedRowData()
                                                let value = rowData.jsxSe
                                                rowData.jsxSe = value
                                                rowData.jsxSqje =  (rowData.jsxDqjl ? rowData.jsxDqjl : 0) - value 
                                                await obj.qnnTableInstance.setEditedRowData(rowData)
                                            }
                                        }
                                    },
                                    {
                                        table: {
                                            title: '税率(%)',
                                            dataIndex: 'jsxSl',
                                            key: 'jsxSl',
                                            tdEdit: (obj) => {
                                                if (obj.jsxFx === '1') return true
                                                return false
                                            },
                                            width: 100,
                                            align: 'center',
                                            type: "select",
                                        },
                                        form: {
                                            dependencies: ['jsxFx'],
                                            dependenciesReRender: true,
                                            required: true,
                                            placeholder: '请选择',
                                            type: "select",
                                            field: 'jsxSl',
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
                                            title: '不含税金额',
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
                                            tdEdit: (obj) => {
                                                if (obj.jsxFx === '2') return true
                                                return false
                                            },
                                            width: 150,
                                            align: 'center',
                                            type: 'selectByQnnTable',
                                        },
                                        form: {
                                            dependencies: ['jsxFx'],
                                            dependenciesReRender: true,
                                            required: true,
                                            type: 'selectByQnnTable',
                                            optionConfig: {
                                                label: 'qwzqzwbBh',
                                                value: 'qwzqzwbNm',
                                            },
                                            dropdownMatchSelectWidth: 900,
                                            qnnTableConfig: {
                                                field: 'qwzqzwbNm',
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
                                                let rowData = obj.rowData
                                                let allData = await obj.qnnTableInstance.getTableData()
                                                for (var i = 0; i < allData.length; i++) {
                                                    if (obj.rowIndex === i) continue
                                                    if (allData[i].qwjsdmxbZqzwnm === obj.itemData.qwzqzwbNm) {
                                                        rowData.qwjsdmxbZqzwnm = null
                                                        rowData.qwjsdmxbZqzwye = null
                                                        rowData.jsxdkkxxzmc = null
                                                        await obj.qnnTableInstance.setEditedRowData(rowData)
                                                        Msg.warn('已存在相同抵扣债权，请重试!')
                                                        return
                                                    }
                                                }
                                                rowData.qwjsdmxbZqzwnm = obj.itemData?.qwzqzwbNm
                                                rowData.qwjsdmxbZqzwye = obj.itemData?.qwzqzwyebYe  //抵扣债权余额
                                                rowData.jsxdkkxxzmc = obj.itemData?.kxxzmc  //对应款项性质
                                                rowData.qwjsdmxbZhdkje = null
                                                await obj.qnnTableInstance.setEditedRowData(rowData)
                                            }
                                        }
                                    },
                                    {
                                        table: {
                                            title: '抵扣债权余额',
                                            dataIndex: 'qwjsdmxbZqzwye',
                                            key: 'qwjsdmxbZqzwye',
                                            tdEdit: false,
                                            width: 150,
                                            align: 'center',
                                        }
                                    },
                                    {
                                        table: {
                                            title: '抵扣金额',
                                            dataIndex: 'qwjsdmxbZhdkje',
                                            key: 'qwjsdmxbZhdkje',
                                            tdEdit: (obj) => {
                                                if (obj.jsxFx === '2') return true
                                                return false
                                            },
                                            width: 150,
                                            align: 'center',
                                        },
                                        form: {
                                            dependencies: ['jsxFx'],
                                            dependenciesReRender: true,
                                            type: 'number',
                                            field: 'qwjsdmxbZhdkje',
                                        }
                                    },
                                    {
                                        table: {
                                            title: '对应款项性质',
                                            dataIndex: 'jsxdkkxxzmc',
                                            key: 'jsxdkkxxzmc',
                                            tdEdit: false,
                                            width: 150,
                                            align: 'center',
                                        }
                                    },
                                    {
                                        table: {
                                            dataIndex: 'jsxDqrq',
                                            key: 'jsxDqrq',
                                            title: '到期日期',
                                            tdEdit: true,
                                            width: 150,
                                            format: 'YYYY-MM-DD',
                                        },
                                        form: {
                                            required: true,
                                            field: 'jsxDqrq',
                                            placeholder: '请选择',
                                            type: 'date',
                                            initialValue: new Date()
                                        }
                                    },
                                    {
                                        table: {
                                            dataIndex: 'jsxYjfkrq',
                                            key: 'jsxYjfkrq',
                                            title: '预计付款日期',
                                            tdEdit: true,
                                            width: 150,
                                            format: 'YYYY-MM-DD',
                                        },
                                        form: {
                                            required: true,
                                            field: 'jsxYjfkrq',
                                            placeholder: '请选择',
                                            type: 'date',
                                            initialValue: new Date()
                                        }
                                    },
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
