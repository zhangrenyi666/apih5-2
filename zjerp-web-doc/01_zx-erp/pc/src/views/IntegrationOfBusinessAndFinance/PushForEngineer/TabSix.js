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
                                    if (params.tabIndex === '5') {
                                        return {
                                            apiName: 'getZxSaSettleAuditCostaccountList',
                                            otherParams: {
                                                mainID: params.baseData.id
                                            }
                                        }
                                    }
                                    return {}
                                },
                                antd: {
                                    rowKey: 'zxSaSettleAuditCostaccountId',
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
                                        addRowDefaultData: { addFlag: '1', amt: params.baseData.bqtchjsje }
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
                                            let emptyData = []
                                            let costaccountList = await obj.qnnTableInstance.getTableData()
                                            await costaccountList.map((item) => {
                                                if (!item.jsxKxxzmc) emptyData.push('费用名称')
                                                if (!item.summary) emptyData.push('摘要')
                                                return item
                                            })
                                            if (emptyData.length > 0) {
                                                Msg.warn(`请填写${emptyData.join()}`)
                                            } else {
                                                await costaccountList.map((item) => {
                                                    item.mainID = this.props.baseData.id
                                                    return item
                                                })
                                                obj.btnCallbackFn.setBtnsLoading('add', 'diyButton');
                                                let params = {}
                                                params.id = this.props.baseData.id
                                                params.costaccountList = costaccountList
                                                this.props.myFetch('batchSaveGcZxSaSettleAuditCostaccount', params)
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
                                            field: 'zxSaSettleAuditCostaccountId',
                                        }
                                    },
                                    {
                                        isInTable: false,
                                        form: {
                                            field: 'feeId',
                                        }
                                    },
                                    {
                                        isInTable: false,
                                        form: {
                                            field: 'accountProjId',
                                        }
                                    },
                                    {
                                        table: {
                                            title: '费用名称',
                                            dataIndex: 'jsxKxxzmc',
                                            key: 'jsxKxxzmc',
                                            align: 'center',
                                            width: 200,
                                            tdEdit: true,
                                        },
                                        form: {
                                            required: true,
                                            type: 'selectByQnnTable',
                                            optionConfig: {
                                                label: 'fmc',
                                                value: 'fmc',
                                            },
                                            dropdownMatchSelectWidth: 400,
                                            qnnTableConfig: {
                                                antd: {
                                                    rowKey: "ffjnm"
                                                },
                                                fetchConfig: {
                                                    apiName: "selectZxSaFiFyxmTree"
                                                },
                                                paginationConfig: false,
                                                pageSize: 99,
                                                searchBtnsStyle: "inline",
                                                rowSelection: { clickRowCanSelect: false },
                                                formConfig: [{
                                                    table: {
                                                        title: '费用名称',
                                                        dataIndex: 'fmc',
                                                        key: 'ffjnm',
                                                        width: 260,
                                                        onClick: ({
                                                            rowData,
                                                            qnnTableInstance: {
                                                                getVTableData,
                                                                setTableData,
                                                                getExpandedRowsKey,
                                                                expandNode,
                                                                tool: { message }
                                                            }
                                                        }) => {
                                                            let expandedRowsKey = getExpandedRowsKey();
                                                            let parentID = rowData.ffjnm;
                                                            let tableData = getVTableData()
                                                            if (expandedRowsKey.includes(parentID)) {
                                                                expandNode(parentID, "close");
                                                                return;
                                                            }
                                                            message.loading('loading', 1)
                                                            this.props.myFetch('getZxSaFiFyxmTreeChildList', {
                                                                ffjnm: rowData.ffjnm
                                                            })
                                                                .then((res) => {
                                                                    message.destroy()
                                                                    if (res.success) {
                                                                        var childrenData = res.data;
                                                                        if (!childrenData.length) {
                                                                            Msg.warn("该节点没有子集数据")
                                                                            return;
                                                                        }
                                                                        var loopFn = function (data) {
                                                                            for (var i = 0; i < data.length; i++) {
                                                                                if (data[i].ffjnm === parentID) {
                                                                                    data[i].children = childrenData;
                                                                                } else if (data[i].children) {
                                                                                    data[i].children = loopFn(data[i].children)
                                                                                }
                                                                            }
                                                                            return data;
                                                                        }
                                                                        tableData = loopFn([...tableData]);
                                                                        setTableData([...tableData]);
                                                                        expandNode(parentID, 'expand');
                                                                    } else {
                                                                        Msg.error(res.message)
                                                                    }
                                                                })
                                                        }
                                                    }
                                                }]
                                            },
                                            onChange: async (val, obj) => {
                                                let rowData = await obj.qnnTableInstance.getEditedRowData()
                                                rowData.jsxKxxzmc = obj.itemData?.fmc
                                                rowData.feeCode = obj.itemData?.fbh  //费用编号
                                                rowData.feeId = obj.itemData?.ffjnm
                                                await obj.qnnTableInstance.setEditedRowData(rowData)
                                            }
                                        }
                                    },
                                    {
                                        table: {
                                            title: '费用编号',
                                            dataIndex: 'feeCode',
                                            key: 'feeCode',
                                            tdEdit: false,
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
                                            type: 'selectByPaging',
                                            tdEdit: true
                                        },
                                        form: {
                                            type: 'selectByPaging',
                                            optionConfig: {
                                                label: 'lsxmlbLbmc',
                                                value: 'lsxmlbLbmc',
                                            },
                                            fetchConfig: {
                                                apiName: 'getZxSaFiXmlbList',
                                            },
                                            onChange: async (val, obj) => {
                                                let rowData = await obj.qnnTableInstance.getEditedRowData()
                                                rowData.accountTypeName = obj.itemData?.lsxmlbLbmc
                                                rowData.accountProjCode = obj.itemData?.lsxmlbLbbh  //专项编号
                                                await obj.qnnTableInstance.setEditedRowData(rowData)
                                            }
                                        }
                                    },
                                    {
                                        table: {
                                            title: '专项编号',
                                            dataIndex: 'accountProjCode',
                                            key: 'accountProjCode',
                                            tdEdit: false,
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
                                            tdEdit: true,
                                        },
                                        form: {
                                            dependencies: ['accountTypeName'],
                                            dependenciesReRender: true,
                                            type: 'selectByQnnTable',
                                            optionConfig: {
                                                label: 'lshsxmXmmc',
                                                value: 'lshsxmXmmc',
                                            },
                                            dropdownMatchSelectWidth: 400,
                                            qnnTableConfig: {
                                                antd: {
                                                    rowKey: "lshsxmFjm"
                                                },
                                                fetchConfig: (obj) => {
                                                    return {
                                                        apiName: "getZxSaFiHsxmListTree",
                                                        otherParams: {
                                                            lshsxmLbmc: obj.props.rowData?.accountTypeName
                                                        }
                                                    }

                                                },
                                                paginationConfig: false,
                                                pageSize: 99,
                                                searchBtnsStyle: "inline",
                                                rowSelection: { clickRowCanSelect: false },
                                                formConfig: [{
                                                    table: {
                                                        title: '核算项目',
                                                        dataIndex: 'lshsxmXmmc',
                                                        key: 'lshsxmFjm',
                                                        width: 260,
                                                        onClick: ({
                                                            rowData,
                                                            props,
                                                            qnnTableInstance: {
                                                                getVTableData,
                                                                setTableData,
                                                                getExpandedRowsKey,
                                                                expandNode,
                                                                tool: { message }
                                                            }
                                                        }) => {
                                                            let expandedRowsKey = getExpandedRowsKey();
                                                            let parentID = rowData.lshsxmFjm;
                                                            let tableData = getVTableData()
                                                            if (expandedRowsKey.includes(parentID)) {
                                                                expandNode(parentID, "close");
                                                                return;
                                                            }
                                                            message.loading('loading', 1)
                                                            this.props.myFetch('getZxSaFiHsxmListChildTreeList', {
                                                                lshsxmFjm: parentID,
                                                                lshsxmLbmc: props.rowData.accountTypeName
                                                            })
                                                                .then((res) => {
                                                                    message.destroy()
                                                                    if (res.success) {
                                                                        var childrenData = res.data;
                                                                        if (!childrenData.length) {
                                                                            Msg.warn("该节点没有子集数据")
                                                                            return;
                                                                        }
                                                                        var loopFn = function (data) {
                                                                            for (var i = 0; i < data.length; i++) {
                                                                                if (data[i].lshsxmFjm === parentID) {
                                                                                    data[i].children = childrenData;
                                                                                } else if (data[i].children) {
                                                                                    data[i].children = loopFn(data[i].children)
                                                                                }
                                                                            }
                                                                            return data;
                                                                        }
                                                                        tableData = loopFn([...tableData]);
                                                                        setTableData([...tableData]);
                                                                        expandNode(parentID, 'expand');
                                                                    } else {
                                                                        Msg.error(res.message)
                                                                    }
                                                                })
                                                        }
                                                    }
                                                }]
                                            },
                                            onChange: async (val, obj) => {
                                                let rowData = await obj.qnnTableInstance.getEditedRowData()
                                                rowData.accountProjName = obj.itemData?.lshsxmXmmc
                                                rowData.accountProjId = obj.itemData?.lshsxmId
                                                await obj.qnnTableInstance.setEditedRowData(rowData)
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
                                            tdEdit: true,
                                        },
                                        form: {
                                            required: true,
                                            type: 'number',
                                        }
                                    },
                                    {
                                        table: {
                                            title: '产品核算',
                                            dataIndex: 'lscpCpmc',
                                            key: 'lscpCpmc',
                                            tdEdit: true,
                                            align: 'center',
                                            width: 150,
                                        },
                                        form: {
                                            type: 'selectByQnnTable',
                                            optionConfig: {
                                                label: 'lscpCpmc',
                                                value: 'lscpCpmc',
                                            },
                                            dropdownMatchSelectWidth: 800,
                                            qnnTableConfig: {
                                                antd: {
                                                    rowKey: "lscpFjm"
                                                },
                                                fetchConfig: {
                                                    apiName: "getZxSaFiCpzdXiaLaList",
                                                    otherParams: {
                                                        orgID: params.baseData.orgID
                                                    }
                                                },
                                                pageSize: 99,
                                                rowSelection: { clickRowCanSelect: false },
                                                formConfig: [
                                                    {
                                                        isInForm: false,
                                                        table: {
                                                            title: "类别名称",
                                                            dataIndex: "lscpLbmc",
                                                            key: 'lscpLbmc'
                                                        }
                                                    },
                                                    {
                                                        isInForm: false,
                                                        table: {
                                                            title: "产品编号",
                                                            dataIndex: "lscpCpbh",
                                                            key: 'lscpCpbh'
                                                        }
                                                    },
                                                    {
                                                        isInForm: false,
                                                        table: {
                                                            title: "产品名称",
                                                            dataIndex: "lscpCpmc",
                                                            key: 'lscpCpmc'
                                                        }
                                                    },
                                                ]
                                            },
                                            onChange: async (val, obj) => {
                                                let rowData = await obj.qnnTableInstance.getEditedRowData()
                                                rowData.lscpCpmc = obj.itemData?.lscpCpmc
                                                await obj.qnnTableInstance.setEditedRowData(rowData)
                                            }
                                        }
                                    },
                                    {
                                        table: {
                                            title: '摘要',
                                            dataIndex: 'summary',
                                            key: 'summary',
                                            tdEdit: true,
                                            align: 'center',
                                            width: 150,
                                        },
                                        form: {
                                            required: true,
                                            type: 'string',
                                            placeholder: '请输入',
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
