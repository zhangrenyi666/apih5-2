import React, { Component } from "react";
import QnnForm from "../../modules/qnn-table/qnn-form";
import { message as Msg, Modal } from 'antd';
import { Toast } from 'antd-mobile';
const confirm = Modal.confirm;
class index extends Component {
    isNumber(obj) {
        var t1 = /^\d+(\.\d+)?$/; //非负浮点数
        var t2 = /^(-(([0-9]+\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\.[0-9]+)|([0-9]*[1-9][0-9]*)))$/; //负浮点数
        if (t1.test(obj) || t2.test(obj)) {
            return true;
        } else {
            return false;
        }
    }
    render() {
        let params = this.props
        return (
            <div style={{ padding: '10px' }}>
                <QnnForm
                    {...this.props}
                    fetch={this.props.myFetch}
                    upload={this.props.myUpload}
                    headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                    wrappedComponentRef={(me) => {
                        this.table = me;
                    }}
                    formConfig={[
                        {
                            field: "editLineName",
                            type: 'string',
                            hide: true
                        },
                        {
                            type: 'qnnTable',
                            field: 'table1',
                            qnnTableConfig: {
                                fetchConfig: () => {
                                    return {
                                        apiName: 'getZxCtFsContractReviewDetailList',
                                        otherParams: {
                                            contractReviewId: params.flowData?.contractReviewId
                                        }
                                    }
                                },
                                antd: {
                                    rowKey: 'conRevDetailId',
                                    size: 'small'
                                },
                                rowDisabledCondition: (rowData) => {
                                    return rowData.conRevDetailId !== this.table.form.getFieldValue('editLineName')
                                },
                                paginationConfig: {
                                    position: 'bottom'
                                },
                                wrappedComponentRef: (me) => {
                                    this.tableOne = me;
                                },
                                actionBtns: [
                                    {
                                        name: 'diyaddRow',
                                        icon: 'plus',
                                        type: 'primary',
                                        label: '新增行',
                                        detailShow: true,
                                        hide: () => {
                                            if (!params.flowData.isToDo || params.flowData.flowStatus === '结束') {
                                                return true;
                                            } else {
                                                return false;
                                            }
                                        },
                                        onClick: () => {
                                            let tableOneData = this.tableOne.state.data ? this.tableOne.state.data : [];
                                            if (tableOneData && tableOneData.length > 0) {
                                                for (var i = 0; i < tableOneData.length; i++) {
                                                    if (tableOneData[i].conRevDetailId === this.table.form.getFieldValue('editLineName')) {
                                                        Msg.warn('有未保存数据，请保存后重试。')
                                                        return
                                                    }
                                                }
                                            }
                                            let newRowData = {}
                                            newRowData.conRevDetailId = new Date().getTime().toString()
                                            tableOneData.push(newRowData)
                                            this.tableOne.btnCallbackFn.setState({
                                                data: tableOneData
                                            })

                                            this.table.form.setFieldsValue({
                                                editLineName: newRowData.conRevDetailId
                                            })
                                        },
                                    },
                                    {
                                        name: 'diydel',
                                        icon: 'del',
                                        type: 'danger',
                                        label: '删除',
                                        detailShow: true,
                                        hide: () => {
                                            if (!params.flowData.isToDo || params.flowData.flowStatus === '结束') {
                                                return true;
                                            } else {
                                                return false;
                                            }
                                        },
                                        disabled: (obj) => {
                                            if (obj.btnCallbackFn.getSelectedRows().length) {
                                                return false;
                                            } else {
                                                return true;
                                            }
                                        },
                                        onClick: (obj) => {
                                            confirm({
                                                content: '确定删除选中的数据吗?',
                                                onOk: () => {
                                                    let data = this.tableOne.state ? this.tableOne.state.data : []
                                                    for (var i = 0; i < data.length; i++) {
                                                        if (data[i].conRevDetailId === this.table.form.getFieldValue('editLineName')) {
                                                            Msg.warn('有未保存数据，请保存后重试。')
                                                            return
                                                        }
                                                    }
                                                    let deleteData = obj.selectedRows
                                                    this.props.myFetch('batchDeleteUpdateZxCtFsContractReviewDetail', deleteData).then(
                                                        ({ success, message }) => {
                                                            if (success) {
                                                                Msg.success(message);
                                                                obj.btnCallbackFn.refresh();
                                                            } else {
                                                                Msg.error(message);
                                                            }
                                                        }
                                                    );
                                                }
                                            })
                                        }
                                    },
                                ],
                                formConfig: [
                                    {
                                        isInTable: false,
                                        form: {
                                            field: 'conRevDetailId',
                                            type: 'string',
                                            hide: true,
                                        }
                                    },
                                    {
                                        table: {
                                            title: `<div>清单编号<span style='color: #ff4d4f'>*</span></div>`,
                                            dataIndex: 'workNo',
                                            key: 'workNo',
                                            width: 150,
                                            tooltip: 17,
                                            align: 'center',
                                            tdEdit: true,
                                        },
                                        form: {
                                            type: 'string',
                                            placeholder: '请输入',
                                            onBlur: (obj) => {
                                                let val = obj.target.value;
                                                let tableOneData = this.tableOne.state.data.map((rowData) => {
                                                    if (rowData.conRevDetailId === this.table.form.getFieldValue('editLineName')) {
                                                        rowData.workNo = val
                                                    }
                                                    return rowData;
                                                })
                                                if (!tableOneData.filter(item => item === null).length) {
                                                    setTimeout(() => {
                                                        this.tableOne.btnCallbackFn.setState({
                                                            data: tableOneData
                                                        })
                                                    }, 0)
                                                }
                                            }
                                        }
                                    },
                                    {
                                        table: {
                                            title: `<div>清单名称<span style='color: #ff4d4f'>*</span></div>`,
                                            width: 150,
                                            tooltip: 15,
                                            tdEdit: true,
                                            dataIndex: 'workName',
                                            key: 'workName',
                                            align: 'center',
                                        },
                                        form: {
                                            type: 'string',
                                            placeholder: '请输入',
                                            onBlur: (obj) => {
                                                let val = obj.target.value;
                                                let tableOneData = this.tableOne.state.data.map((rowData) => {
                                                    if (rowData.conRevDetailId === this.table.form.getFieldValue('editLineName')) {
                                                        rowData.workName = val
                                                    }
                                                    return rowData;
                                                })
                                                if (!tableOneData.filter(item => item === null).length) {
                                                    setTimeout(() => {
                                                        this.tableOne.btnCallbackFn.setState({
                                                            data: tableOneData
                                                        })
                                                    }, 0)
                                                }
                                            }
                                        }
                                    },
                                    {
                                        table: {
                                            title: '计量单位',
                                            dataIndex: 'unit',
                                            key: 'unit',
                                            width: 100,
                                            tdEdit: true,
                                            align: 'center',
                                        },
                                        form: {
                                            type: 'string',
                                            placeholder: '请输入',
                                            onBlur: (obj) => {
                                                let val = obj.target.value;
                                                let tableOneData = this.tableOne.state.data.map((rowData) => {
                                                    if (rowData.conRevDetailId === this.table.form.getFieldValue('editLineName')) {
                                                        rowData.unit = val
                                                    }
                                                    return rowData;
                                                })
                                                if (!tableOneData.filter(item => item === null).length) {
                                                    setTimeout(() => {
                                                        this.tableOne.btnCallbackFn.setState({
                                                            data: tableOneData
                                                        })
                                                    }, 0)
                                                }
                                            }
                                        }
                                    },
                                    {
                                        table: {
                                            title: '合同数量',
                                            dataIndex: 'qty',
                                            key: 'qty',
                                            width: 100,
                                            tdEdit: true,
                                            type: 'number',
                                            align: 'center',
                                        },
                                        form: {
                                            precision: 3,
                                            type: 'number',
                                            placeholder: '请输入',
                                            onBlur: (obj) => {
                                                let val = obj.target.value;
                                                let tableOneData = this.tableOne.state.data.map((rowData) => {
                                                    if (rowData.conRevDetailId === this.table.form.getFieldValue('editLineName')) {
                                                        rowData.qty = val ? Number(val) : 0;
                                                        rowData.contractSum = (val ? Number(val) : 0) * (rowData.price ? rowData.price : 0)  //含税合同金额
                                                        rowData.contractSumNoTax = ((val ? Number(val) : 0) * (rowData.price ? rowData.price : 0)) / (1 + (rowData.taxRate ? Number(rowData.taxRate) : 0) / 100) //不含税合同金额
                                                    }
                                                    return rowData;
                                                })
                                                if (!tableOneData.filter(item => item === null).length) {
                                                    setTimeout(() => {
                                                        this.tableOne.btnCallbackFn.setState({
                                                            data: tableOneData
                                                        })
                                                    }, 0)
                                                }
                                            }
                                        }
                                    },
                                    {
                                        table: {
                                            title: `<div>含税合同单价(元)<span style='color: #ff4d4f'>*</span></div>`,
                                            dataIndex: 'price',
                                            key: 'price',
                                            width: 150,
                                            tdEdit: true,
                                            align: 'center',
                                        },
                                        form: {
                                            type: 'number',
                                            placeholder: '请输入',
                                            precision: 6,
                                            onBlur: (obj) => {
                                                let val = obj.target.value;
                                                let tableOneData = this.tableOne.state.data.map((rowData) => {
                                                    if (rowData.conRevDetailId === this.table.form.getFieldValue('editLineName')) {
                                                        rowData.price = val ? Number(val) : 0;
                                                        let priceNoTax = (val ? Number(val) : 0) / (1 + (rowData.taxRate ? Number(rowData.taxRate) : 0) / 100) //不含税单价
                                                        rowData.priceNoTax = Math.round(priceNoTax * 100) / 100
                                                        rowData.contractSum = (rowData.qty ? rowData.qty : 0) * (val ? Number(val) : 0)   //含税合同金额
                                                        rowData.contractSumNoTax = (rowData.qty ? rowData.qty : 0) * (val ? Number(val) : 0) / (1 + (rowData.taxRate ? Number(rowData.taxRate) : 0) / 100) //不含税合同金额
                                                    }
                                                    return rowData;
                                                })
                                                if (!tableOneData.filter(item => item === null).length) {
                                                    setTimeout(() => {
                                                        this.tableOne.btnCallbackFn.setState({
                                                            data: tableOneData
                                                        })
                                                    }, 0)
                                                }
                                            }
                                        }
                                    },
                                    {
                                        table: {
                                            title: '不含税合同单价(元)',
                                            dataIndex: 'priceNoTax',
                                            key: 'priceNoTax',
                                            width: 150,
                                            tdEdit: true,
                                            align: 'center',
                                        },
                                        form: {
                                            type: 'number',
                                            placeholder: '请输入',
                                            precision: 6,
                                            onBlur: (obj) => {
                                                let val = obj.target.value;
                                                let tableOneData = this.tableOne.state.data.map((rowData) => {
                                                    if (rowData.conRevDetailId === this.table.form.getFieldValue('editLineName')) {
                                                        rowData.priceNoTax = val ? Number(val) : 0;
                                                        let price = (val ? Number(val) : 0) * (1 + (rowData.taxRate ? Number(rowData.taxRate) : 0) / 100)  //含税合同单价
                                                        rowData.price = Math.round(price * 100) / 100
                                                        rowData.contractSum = (rowData.qty ? rowData.qty : 0) * (val ? Number(val) : 0) * (1 + (rowData.taxRate ? Number(rowData.taxRate) : 0) / 100)  //含税合同金额
                                                        rowData.contractSumNoTax = (rowData.qty ? rowData.qty : 0) * (val ? Number(val) : 0) //不含税合同金额
                                                    }
                                                    return rowData;
                                                })
                                                if (!tableOneData.filter(item => item === null).length) {
                                                    setTimeout(() => {
                                                        this.tableOne.btnCallbackFn.setState({
                                                            data: tableOneData
                                                        })
                                                    }, 0)
                                                }
                                            },
                                        }
                                    },
                                    {
                                        table: {
                                            title: `<div>税率(%)<span style='color: #ff4d4f'>*</span></div>`,
                                            dataIndex: 'taxRate',
                                            key: 'taxRate',
                                            width: 100,
                                            tdEdit: true,
                                            type: 'select',
                                            align: 'center',
                                            initialValue: 0
                                        },
                                        form: {
                                            field: "taxRate",
                                            type: 'select',
                                            optionConfig: {
                                                label: 'itemName',
                                                value: 'itemId',
                                            },
                                            fetchConfig: {
                                                apiName: 'getBaseCodeSelect',
                                                otherParams: { itemId: 'shuiLv' }
                                            },
                                            allowClear: false,
                                            onChange: (val) => {
                                                let tableOneData = this.tableOne.state.data.map((rowData) => {
                                                    if (rowData.conRevDetailId === this.table.form.getFieldValue('editLineName')) {
                                                        rowData.taxRate = val;
                                                        let priceNoTax = (rowData.price ? rowData.price : 0) / (1 + (val ? Number(val) : 0) / 100) //不含税合同单价
                                                        rowData.priceNoTax = Math.round(priceNoTax * 100) / 100
                                                        rowData.contractSumNoTax = (rowData.qty ? rowData.qty : 0) * (rowData.price ? rowData.price : 0) / (1 + (Number(val) ? Number(val) : 0) / 100)
                                                    }
                                                    return rowData;
                                                })
                                                this.tableOne.btnCallbackFn.setState({
                                                    data: tableOneData
                                                })
                                            },
                                            placeholder: '请选择'
                                        }
                                    },
                                    {
                                        table: {
                                            title: '是否抵扣',
                                            dataIndex: 'isDeduct',
                                            key: 'isDeduct',
                                            width: 100,
                                            align: 'center',
                                            render: () => {
                                                if (params?.flowData?.isDeduct === '1') {
                                                    return '是';
                                                } else {
                                                    return '否';
                                                }
                                            }
                                        }
                                    },
                                    {
                                        table: {
                                            title: '含税合同金额',
                                            dataIndex: 'contractSum',
                                            key: 'contractSum',
                                            width: 120,
                                            align: 'center',
                                            render: (val, rowData) => {
                                                return rowData.contractSum ? rowData.contractSum.toFixed(2) : 0
                                            }
                                        }
                                    },

                                    {
                                        table: {
                                            title: '不含税合同金额',
                                            dataIndex: 'contractSumNoTax',
                                            key: 'contractSumNoTax',
                                            width: 120,
                                            align: 'center',
                                            render: (val, rowData) => {
                                                return rowData.contractSumNoTax ? rowData.contractSumNoTax.toFixed(2) : 0
                                            }
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
                                            title: '变更后含税单价',
                                            dataIndex: 'changePrice',
                                            key: 'changePrice',
                                            width: 120,
                                            align: 'center',
                                        }
                                    },
                                    {
                                        table: {
                                            title: '变更后不含税单价',
                                            dataIndex: 'changePriceNoTax',
                                            key: 'changePriceNoTax',
                                            width: 150,
                                            align: 'center',
                                        }
                                    },
                                    {
                                        table: {
                                            title: '变更后含税金额',
                                            dataIndex: 'changeContractSum',
                                            key: 'changeContractSum',
                                            width: 170,
                                            align: 'center',
                                        }
                                    },
                                    {
                                        table: {
                                            title: '变更后不含税金额',
                                            dataIndex: 'changeContractSumNoTax',
                                            key: 'changeContractSumNoTax',
                                            width: 170,
                                            align: 'center',
                                        }
                                    },
                                    {
                                        table: {
                                            title: '备注',
                                            dataIndex: 'remarks',
                                            key: 'remarks',
                                            width: 150,
                                            tdEdit: true,
                                            align: 'center',
                                        },
                                        form: {
                                            type: 'string',
                                            placeholder: '请输入',
                                            onBlur: (obj) => {
                                                let val = obj.target.value;
                                                let tableOneData = this.tableOne.state.data.map((rowData) => {
                                                    if (rowData.conRevDetailId === this.table.form.getFieldValue('editLineName')) {
                                                        rowData.remarks = val
                                                    }
                                                    return rowData;
                                                })
                                                if (!tableOneData.filter(item => item === null).length) {
                                                    setTimeout(() => {
                                                        this.tableOne.btnCallbackFn.setState({
                                                            data: tableOneData
                                                        })
                                                    }, 0)
                                                }
                                            }
                                        }
                                    },
                                    {
                                        isInTable: () => {
                                            if (!params.flowData.isToDo || params.flowData.flowStatus === '结束') {
                                                return false;
                                            } else {
                                                return true;
                                            }
                                        },
                                        isInForm: false,
                                        table: {
                                            showType: 'tile',
                                            width: 120,
                                            title: "操作",
                                            key: "action",//操作列名称
                                            fixed: 'right',//固定到右边
                                            align: 'center',
                                            render: (data, rowData) => {
                                                if (rowData.conRevDetailId === this.table.form.getFieldValue('editLineName')) {
                                                    return <a>保存</a>;
                                                } else {
                                                    return <a>编辑</a>;
                                                }
                                            },
                                            onClick: (obj) => {
                                                if (this.table.form.getFieldValue('editLineName') === obj.rowData.conRevDetailId) {
                                                    Toast.loading('loading...');
                                                    setTimeout(() => {
                                                        if (!obj.rowData.workNo) {
                                                            Msg.warn('请填写清单编号！')
                                                            return
                                                        } else if (!obj.rowData.workName) {//
                                                            Msg.warn('请填写清单名称！')
                                                            return
                                                        } else if (!obj.rowData.taxRate) {
                                                            Msg.warn('请填写税率！')
                                                            return
                                                        } else if (!obj.rowData.price) {
                                                            Msg.warn('请填写含税合同单价！')
                                                            return
                                                        }
                                                        let body = {
                                                            ...obj.rowData
                                                        };
                                                        body.contractReviewId = params.flowData.contractReviewId
                                                        this.props.myFetch(this.isNumber(obj.rowData.conRevDetailId) ? 'addZxCtFsContractReviewDetail' : 'updateZxCtFsContractReviewDetail', body).then(
                                                            ({ success, message }) => {
                                                                if (success) {
                                                                    Msg.success(message);
                                                                    this.table.form.setFieldsValue({
                                                                        editLineName: ''
                                                                    })
                                                                    obj.btnCallbackFn.refresh();
                                                                    this.table.refresh()
                                                                    Toast.hide()
                                                                } else {
                                                                    Toast.hide()
                                                                    Msg.error(message);
                                                                }
                                                            }
                                                        );
                                                    }, 300)
                                                } else {
                                                    let data = this.tableOne.state ? this.tableOne.state.data : []
                                                    for (var i = 0; i < data.length; i++) {
                                                        if (data[i].conRevDetailId === this.table.form.getFieldValue('editLineName')) {
                                                            Msg.warn('有未保存数据，请保存后重试。')
                                                            return
                                                        }
                                                    }
                                                    this.table.form.setFieldsValue({
                                                        editLineName: obj.rowData.conRevDetailId
                                                    })

                                                    obj.btnCallbackFn.refresh();
                                                }
                                            }
                                        }
                                    }
                                ],
                            },
                        }
                    ]}
                />
            </div>
        );
    }
}
export default index;
