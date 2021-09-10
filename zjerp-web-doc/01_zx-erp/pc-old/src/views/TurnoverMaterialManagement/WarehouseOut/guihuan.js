import React, { Component } from "react";
import QnnForm from "../../modules/qnn-table/qnn-form";
import { message as Msg,Button } from "antd";
const configItem = {
    drawerConfig: {
        width: '1100px'
    },
    paginationConfig: false,
    actionBtnsPosition: "top",
    firstRowIsSearch: false,
    isShowRowSelect: true
};
class index extends Component {
    constructor(props) {
        super(props);
        console.log(props);
        this.state = {
            id: props.id || '',
            height: document.body.clientHeight - 55 - 53,
        }
    }

    render() {
        const { id,height } = this.state;
        return (
            <div style={{ height: "100%", position: 'relative' }}>
                <div style={{ background: '#fff', padding: '30px', height: height, overflow: 'auto' }}>
                    <QnnForm
                        {...this.props}
                        match={this.props.match}
                        fetch={this.props.myFetch}
                        upload={this.props.myUpload}
                        wrappedComponentRef={(me) => {
                            this.formHasTicket = me;
                        }}
                        fetchConfig={{
                            apiName: 'getZxSkTurnoverOutDetai',
                            otherParams: {
                                id: id
                            }
                        }}
                        headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                        formConfig={[
                            {
                                label: '外层主键id',
                                field: 'id',
                                hide: true
                            },
                            {
                                label: '外层主键id',
                                field: 'orgID',
                                hide: true
                            },
                            {
                                field: 'orgName',
                                type: 'string',
                                disabled: true,
                                label: '发料单位',
                                span: 8,
                                formItemLayout: {
                                    labelCol: {
                                        xs: { span: 24 },
                                        sm: { span: 8 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 24 },
                                        sm: { span: 16 }
                                    }
                                }
                            },
                            {
                                field: 'billNo',
                                type: 'string',
                                disabled: true,
                                label: '单据编号',
                                span: 8,
                                formItemLayout: {
                                    labelCol: {
                                        xs: { span: 24 },
                                        sm: { span: 8 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 24 },
                                        sm: { span: 16 }
                                    }
                                }
                            },
                            {
                                type: 'date',
                                field: 'busDate',
                                disabled: true,
                                label: '出库日期',
                                span: 8,
                                formItemLayout: {
                                    labelCol: {
                                        xs: { span: 24 },
                                        sm: { span: 8 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 24 },
                                        sm: { span: 16 }
                                    }
                                }
                            },
                            {
                                type: 'string',
                                field: 'inOrgName',
                                disabled: true,
                                label: '领料单位',
                                span: 8,
                                formItemLayout: {
                                    labelCol: {
                                        xs: { span: 24 },
                                        sm: { span: 8 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 24 },
                                        sm: { span: 16 }
                                    }
                                }
                            },
                            {
                                type: 'string',
                                field: 'consignee',
                                label: '物资员',
                                disabled: true,
                                span: 8,
                                formItemLayout: {
                                    labelCol: {
                                        xs: { span: 24 },
                                        sm: { span: 8 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 24 },
                                        sm: { span: 16 }
                                    }
                                }
                            },
                            {
                                type: 'textarea',
                                field: 'remarks',
                                label: '备注',
                                disabled: true,
                                span: 8,
                                formItemLayout: {
                                    labelCol: {
                                        xs: { span: 24 },
                                        sm: { span: 8 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 24 },
                                        sm: { span: 16 }
                                    }
                                }
                            },
                            {
                                type: 'qnnTable',
                                field: 'zxSkTurnoverOutItemList',
                                incToForm: true,
                                qnnTableConfig: {
                                    antd: {
                                        rowKey: 'id',
                                        size: 'small'
                                    },
                                    ...configItem,
                                    tableTdEdit: true,
                                    formConfig: [
                                        {
                                            isInTable: false,
                                            form: {
                                                label: '主键id',
                                                field: 'id',
                                                hide: true
                                            }
                                        },
                                        {
                                            table: {
                                                title: '物资编码',
                                                width: 200,
                                                tooltip: 80,
                                                dataIndex: 'resCode',
                                                key: 'resCode',
                                                tdEdit: true
                                            },
                                            form: {
                                                type: 'selectByQnnTable',
                                                label: '',
                                                field: 'resCode',
                                                dropdownMatchSelectWidth: 900,
                                                optionConfig: {
                                                    label: 'resCode',
                                                    value: 'id'
                                                },
                                                // disabled: (obj) => {
                                                //     if (obj?.btnCallbackFn?.props?.qnnFormProps?.qnnformData?.qnnFormProps?.clickCb?.rowInfo?.name === 'diy' || obj?.btnCallbackFn?.props?.qnnFormProps?.qnnformData?.qnnFormProps?.clickCb?.rowInfo?.name === 'detail') {
                                                //         return true
                                                //     } else {
                                                //         return false
                                                //     }
                                                // },
                                                qnnTableConfig: {
                                                    antd: {
                                                        rowKey: "id"
                                                    },
                                                    firstRowIsSearch: false,
                                                    fetchConfig: {
                                                        apiName: "getZxSkTurnoverOutResourceList",
                                                        otherParams: async () => {
                                                            const vals = await this.formHasTicket.getValues(false);
                                                            return {
                                                                orgID: vals.orgID,
                                                            }
                                                        }
                                                    },
                                                    searchBtnsStyle: "inline",
                                                    formConfig: [
                                                        {
                                                            isInForm: false,
                                                            isInTable: false,
                                                            form: {
                                                                field: 'id',
                                                                type: "string"
                                                            }
                                                        },
                                                        {
                                                            isInForm: false,
                                                            isInSearch: true,
                                                            table: {
                                                                dataIndex: "resCode",
                                                                title: "编号",
                                                            },
                                                            form: {
                                                                type: "string"
                                                            }
                                                        },
                                                        {
                                                            isInForm: false,
                                                            isInSearch: true,
                                                            table: {
                                                                dataIndex: "resName",
                                                                title: "名称",
                                                            },
                                                            form: {
                                                                type: "string"
                                                            }
                                                        },
                                                        {
                                                            isInForm: false,
                                                            isInSearch: true,
                                                            table: {
                                                                dataIndex: "spec",
                                                                title: "规格型号",
                                                            },
                                                            form: {
                                                                type: "string"
                                                            }
                                                        },
                                                        {
                                                            isInForm: false,
                                                            isInSearch: true,
                                                            table: {
                                                                dataIndex: "resUnit",
                                                                title: "单位",
                                                            },
                                                            form: {
                                                                type: "string"
                                                            }
                                                        },
                                                        {
                                                            isInForm: false,
                                                            // isInSearch:true,
                                                            table: {
                                                                dataIndex: "inBusDate",
                                                                title: "入库日期",
                                                                format: 'YYYY-MM-DD'
                                                            },
                                                            form: {
                                                                type: "date"
                                                            }
                                                        },
                                                        {
                                                            isInForm: false,
                                                            // isInSearch:true,
                                                            table: {
                                                                dataIndex: "stockQty",
                                                                title: "库存数量",
                                                            },
                                                            form: {
                                                                type: "number"
                                                            }
                                                        },
                                                        {
                                                            isInForm: false,
                                                            // isInSearch:true,
                                                            table: {
                                                                dataIndex: "originalAmt",
                                                                title: "原值",
                                                            },
                                                            form: {
                                                                type: "number"
                                                            }
                                                        },
                                                        {
                                                            isInForm: false,
                                                            // isInSearch:true,
                                                            table: {
                                                                dataIndex: "remainAmt",
                                                                title: "净值",
                                                            },
                                                            form: {
                                                                type: "number"
                                                            }
                                                        }
                                                    ]
                                                },
                                                onChange: (colVal, thisProps, tableBtnCallbackFn) => {
                                                    if (colVal) {
                                                        const itemData = thisProps;
                                                        tableBtnCallbackFn.setEditedRowData({
                                                            resCode: itemData.itemData[0].resCode,
                                                            resName: itemData.itemData[0].resName,
                                                            resUnit: itemData.itemData[0].resUnit,
                                                            spec: itemData.itemData[0].spec,
                                                            resID: itemData.itemData[0].id,
                                                            stockQty: itemData.itemData[0].stockQty,
                                                            inBusDate: itemData.itemData[0].inBusDate,
                                                            remainAmt: itemData.itemData[0].remainAmt,
                                                            originalAmt: itemData.itemData[0].originalAmt,
                                                            batchNo: itemData.itemData[0].batchNo,
                                                            outQty: 0,
                                                            hasReturnQty: 0,
                                                            // oldhasReturnQty: 0
                                                        });
                                                    } else {
                                                        tableBtnCallbackFn.getEditedRowData(false).then((editRowData) => {
                                                            tableBtnCallbackFn.setEditedRowData({
                                                                ...editRowData,
                                                                resID: '',
                                                                resName: '',
                                                                resUnit: '',
                                                                spec: '',
                                                                inBusDate: undefined,
                                                                stockQty: 0,
                                                                originalAmt: 0,
                                                                remainAmt: 0,
                                                                outQty: 0,
                                                                hasReturnQty: 0,
                                                                // oldhasReturnQty: 0
                                                            });
                                                        })
                                                    }

                                                },
                                            }
                                        },
                                        {
                                            isInTable: false,
                                            form: {
                                                field: 'resID',
                                                hide: true
                                            }
                                        },
                                        {
                                            isInTable: false,
                                            form: {
                                                field: 'batchNo',
                                                hide: true
                                            }
                                        },
                                        {
                                            table: {
                                                title: '物资名称',
                                                width: 150,
                                                dataIndex: 'resName',
                                                key: 'resName',
                                                tooltip: 10,
                                            },
                                            form: {
                                                type: 'string',
                                                field: 'resName'
                                            }
                                        },
                                        {
                                            table: {
                                                title: '规格型号',
                                                dataIndex: 'spec',
                                                key: 'spec',
                                                width: 80,
                                                tooltip: 10,
                                            },
                                            form: {
                                                type: 'string',
                                                field: 'spec'
                                            }
                                        },
                                        {
                                            table: {
                                                title: '计量单位',
                                                dataIndex: 'resUnit',
                                                key: 'resUnit',
                                                width: 80,
                                            },
                                            form: {
                                                type: 'string',
                                                field: 'resUnit'
                                            }
                                        },
                                        {
                                            table: {
                                                title: '入库日期',
                                                dataIndex: 'inBusDate',
                                                key: 'inBusDate',
                                                format: 'YYYY-MM-DD'
                                            },
                                            form: {
                                                type: 'date',
                                                field: 'inBusDate'
                                            }
                                        },
                                        {
                                            table: {
                                                title: '库存数量',
                                                dataIndex: 'stockQty',
                                                key: 'stockQty',
                                            },
                                            form: {
                                                type: 'number',
                                                field: 'stockQty',
                                                precision: 3
                                            }
                                        },
                                        {
                                            table: {
                                                title: '原值',
                                                dataIndex: 'originalAmt',
                                                key: 'originalAmt',
                                                width: 80,
                                            },
                                            form: {
                                                type: 'number',
                                                field: 'originalAmt',
                                                precision: 2
                                            }
                                        },
                                        {
                                            table: {
                                                title: '净值',
                                                dataIndex: 'remainAmt',
                                                key: 'remainAmt',
                                                width: 80,
                                            },
                                            form: {
                                                type: 'number',
                                                field: 'remainAmt',
                                                precision: 2
                                            }
                                        },
                                        {
                                            table: {
                                                title: '出库数量',
                                                width: 100,
                                                dataIndex: 'outQty',
                                                key: 'outQty',
                                                tdEdit: true
                                            },
                                            form: {
                                                type: 'number',
                                                field: 'outQty',
                                                precision: 3,
                                                disabled: (obj) => {
                                                    if (obj?.btnCallbackFn?.props?.qnnFormProps?.qnnformData?.qnnFormProps?.clickCb?.rowInfo?.name === 'diy' || obj?.btnCallbackFn?.props?.qnnFormProps?.qnnformData?.qnnFormProps?.clickCb?.rowInfo?.name === 'detail') {
                                                        return true
                                                    } else {
                                                        return false
                                                    }
                                                },
                                                onChange: async (colVal, thisProps, tableBtnCallbackFn) => {
                                                    if (colVal) {
                                                        clearTimeout(this.tdEditedTimer);
                                                        this.tdEditedTimer = setTimeout(async () => {
                                                            const rowData = await tableBtnCallbackFn.getEditedRowData(false);
                                                            const newRowData = {
                                                                ...rowData
                                                            }
                                                            if (colVal > rowData.stockQty) {
                                                                Msg.warn('超出库存数量！');
                                                                newRowData.outQty = rowData.stockQty;
                                                            } else {
                                                                newRowData.outQty = colVal;
                                                            }
                                                            await tableBtnCallbackFn.setEditedRowData({
                                                                ...newRowData
                                                            });
                                                        }, 600)
                                                    }
                                                }
                                            }
                                        },
                                        {
                                            table: {
                                                title: '已归还数量',
                                                width: 120,
                                                dataIndex: 'hasReturnQty',
                                                key: 'hasReturnQty',
                                                tdEdit: true
                                            },
                                            form: {
                                                type: 'number',
                                                field: 'hasReturnQty',
                                                precision: 3,
                                                disabled: (obj) => {
                                                    if (obj?.btnCallbackFn?.props?.qnnFormProps?.qnnformData?.qnnFormProps?.clickCb?.rowInfo?.name === 'diy') {
                                                        return false
                                                    } else {
                                                        return true
                                                    }
                                                },
                                                onChange: async (colVal, thisProps, tableBtnCallbackFn) => {
                                                    if (colVal) {
                                                        clearTimeout(this.tdEditedTimer);
                                                        this.tdEditedTimer = setTimeout(async () => {
                                                            const rowData = await tableBtnCallbackFn.getEditedRowData(false);
                                                            const newRowData = {
                                                                ...rowData
                                                            }
                                                            if (colVal > rowData.outQty) {
                                                                Msg.warn('超出库存数量！');
                                                                newRowData.hasReturnQty = rowData.outQty;
                                                            } else {
                                                                // 上次归还赋值给新字段
                                                                if (rowData.hasReturnQty) {
                                                                    // newRowData.oldhasReturnQty = rowData.hasReturnQty;
                                                                }
                                                            }
                                                            await tableBtnCallbackFn.setEditedRowData({
                                                                ...newRowData
                                                            });
                                                        }, 600)
                                                    }
                                                },
                                            }
                                        },
                                        {
                                            isInTable: false,
                                            form: {
                                                type: 'number',
                                                field: 'oldhasReturnQty',
                                            }
                                        }
                                    ],
                                    actionBtns: [
                                        {
                                            name: "addRow",
                                            icon: "plus",
                                            type: "primary",
                                            label: "新增",
                                        },
                                        {
                                            name: "del",
                                            icon: 'delete',
                                            type: 'danger',
                                            label: "删除",
                                        }
                                    ]
                                }
                            }
                        ]}
                        btns={[]}
                    />
                </div>


                <div style={{ position: 'absolute', bottom: 0, background: 'white', textAlign: 'end', width: '100%', padding: '10px 16px', borderTop: '1px solid rgb(232, 232, 232)' }}>
                    <Button type="dashed" style={{ marginRight: '8px' }} onClick={() => {
                        this.props.btnCallbackFn.closeDrawer();
                    }}>返回</Button>
                    <Button type="primary" onClick={() => {
                        let value = this.formHasTicket.form.getFieldsValue();
						this.props.myFetch("returnZxSkTurnoverOut", value).then(({ success, data, message }) => {
							if (success) {
								Msg.success(message);
								this.props.btnCallbackFn.refresh();
								this.props.btnCallbackFn.closeDrawer();
							} else {
								Msg.error(message);
							}
						})
						
					}}>保存</Button>
                </div>
            </div>

        );
    }
}

export default index;