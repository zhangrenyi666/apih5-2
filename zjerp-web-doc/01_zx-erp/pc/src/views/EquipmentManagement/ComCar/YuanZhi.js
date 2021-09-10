import React, { Component } from "react";
import QnnForm from "../../modules/qnn-table/qnn-form";
import { message as Msg, Modal } from 'antd';
import { Toast } from 'antd-mobile';
import moment from 'moment';
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
            <div style={{ padding: '15px' }}>
                <QnnForm
                    {...this.props}
                    fetch={this.props.myFetch}
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
                                        apiName: 'getZxEqEquipDepreciationItemListForTab',
                                        otherParams: { equipID: params.flowData.state.clickCb.selectedRows[0].id }
                                    }
                                },
                                antd: {
                                    rowKey: 'id',
                                    size: 'small'
                                },
                                rowDisabledCondition: (rowData) => {
                                    return rowData.id !== this.table.form.getFieldValue('editLineName')
                                },
                                paginationConfig: {
                                    position: 'bottom'
                                },
                                wrappedComponentRef: (me) => {
                                    this.tableOne = me;
                                },
                                actionBtns: [
                                    {
                                        name: 'diyaddRow',//内置add del
                                        icon: 'plus',//icon
                                        type: 'primary',//类型  默认 primary  [primary dashed danger]
                                        label: '新增行',
                                        field: "add",
                                        onClick: (obj) => {
                                            let tableOneData = this.tableOne.state.data ? this.tableOne.state.data : [];
                                            if (tableOneData && tableOneData.length > 0) {
                                                for (var i = 0; i < tableOneData.length; i++) {
                                                    if (tableOneData[i].id === this.table.form.getFieldValue('editLineName')) {
                                                        Msg.warn('有未保存数据，请保存后重试。')
                                                        return
                                                    }
                                                }
                                            }
                                            let newRowData = {}
                                            newRowData.id = new Date().getTime().toString()
                                            newRowData.businessDate = new Date()
                                            newRowData.reporter = this.props.loginAndLogoutInfo.loginInfo.userInfo.realName
                                            tableOneData.push(newRowData)
                                            this.tableOne.btnCallbackFn.setState({
                                                data: tableOneData
                                            })
                                            this.table.form.setFieldsValue({
                                                editLineName: newRowData.id
                                            })
                                        },
                                    },
                                    {
                                        name: 'diydel',//内置add del
                                        icon: 'del',//icon
                                        type: 'danger',//类型  默认 primary  [primary dashed danger]
                                        label: '删除',
                                        field: "del",
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
                                                        if (data[i].id === this.table.form.getFieldValue('editLineName')) {
                                                            Msg.warn('有未保存数据，请保存后重试。')
                                                            return
                                                        }
                                                    }
                                                    let deleteData = obj.selectedRows
                                                    this.props.myFetch('batchDeleteUpdateZxSaOtherEquipPaySettleItem', deleteData).then(
                                                        ({ success, message }) => {
                                                            if (success) {
                                                                Msg.success(message);
                                                                obj.btnCallbackFn.clearSelectedRows();
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
                                            field: 'id',
                                            hide: true
                                        }
                                    },
                                    {
                                        table: {
                                            title: '原始原值',
                                            dataIndex: 'orginalValue',
                                            key: 'orginalValue',
                                            width: 150,
                                        },
                                    },
                                    {
                                        table: {
                                            title: '修改原值',
                                            dataIndex: 'qty',
                                            key: 'qty',
                                            tdEdit: true
                                        },
                                        form: {
                                            precision: 3,
                                            field: 'qty',
                                            type: 'number',
                                            placeholder: '请输入',
                                            onBlur: (obj) => {
                                                let val = obj.target.value;
                                                let tableOneData = this.tableOne.state.data.map((rowData) => {
                                                    if (rowData.id === this.table.form.getFieldValue('editLineName')) {
                                                        rowData.qty = Number(val)     //本期结算数量  
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
                                        },
                                    },
                                    {
                                        table: {
                                            title: '业务日期',
                                            dataIndex: 'businessDate',
                                            key: 'businessDate',
                                            width: 120,
                                            render: (data) => {
                                                return moment(data).format('YYYY-MM-DD')
                                            }
                                        },
                                    },
                                    {
                                        table: {
                                            title: '登记人',
                                            dataIndex: 'reporter',
                                            key: 'reporter',
                                            width: 140,
                                        },
                                    },
                                    {
                                        isInForm: false,
                                        table: {
                                            showType: 'tile',
                                            width: 120,
                                            title: "操作",
                                            key: "action",//操作列名称
                                            fixed: 'right',//固定到右边
                                            align: 'center',
                                            render: (data, rowData) => {
                                                if (rowData.id === this.table.form.getFieldValue('editLineName')) {
                                                    return <a>保存</a>;
                                                } else {
                                                    return <a>编辑</a>;
                                                }
                                            },
                                            onClick: (obj) => {
                                                if (obj.rowData.id === this.table.form.getFieldValue('editLineName')) {
                                                    Toast.loading('loading...');
                                                    setTimeout(() => {
                                                        let body = {
                                                            ...obj.rowData
                                                        };
                                                        this.props.myFetch(this.isNumber(obj.rowData.id) ? 'addZxSaOtherEquipPaySettleItem' : 'updateZxSaOtherEquipPaySettleItem', body).then(
                                                            ({ success, message }) => {
                                                                if (success) {
                                                                    Msg.success(message);
                                                                    this.table.form.setFieldsValue({
                                                                        editLineName: ''
                                                                    })
                                                                    Toast.hide()
                                                                    obj.btnCallbackFn.refresh();
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
                                                        if (data[i].id === this.table.form.getFieldValue('editLineName')) {
                                                            Msg.warn('有未保存数据，请保存后重试。')
                                                            return
                                                        }
                                                    }
                                                    this.table.form.setFieldsValue({
                                                        editLineName: obj.rowData.id
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
