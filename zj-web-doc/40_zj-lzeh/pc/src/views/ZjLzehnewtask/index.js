//新增任务管理
import React, { Component } from "react";
import QnnTable from "../modules/qnn-table";
import { Modal } from "antd";
const confirm = Modal.confirm;
const config = {
    fetchConfig: {
        apiName: 'getZjLzehSafetyInspectionManagementList'
    },
    antd: {
        rowKey: function (row) {
            return row.safetyInspectionManagementId
        },
        size: 'small'
    },
    drawerConfig: {
        width: '1100px'
    },
    paginationConfig: {
        position: 'bottom'
    },

    firstRowIsSearch: false,
    isShowRowSelect: true,
    // paginationConfig: true
};
class index extends Component {
    constructor(props) {
        super(props);
        this.state = {
        }
    }
    render() {
        return (
            <div>
                <QnnTable
                    {...this.props}
                    fetch={this.props.myFetch}
                    headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                    wrappedComponentRef={(me) => {
                        this.table = me;
                    }}
                    {...config}
                    formConfig={[
                        {
                            table: {
                                title: '序号',
                                dataIndex: 'orderFlag',
                                key: 'orderFlag',
                                align: 'center',
                                width: 100,
                            },
                            form: {
                                label: '序号',
                                field: 'orderFlag',
                                type: 'number'
                            },
                        },
                        {
                            table: {
                                title: '月份',
                                dataIndex: 'name',
                                key: 'name',
                                align: 'center',

                            },
                            form: {
                                label: '月份',
                                field: 'name',
                                type: 'string',
                                required: true,
                            },
                        },
                        {
                            table: {
                                title: '任务数',
                                dataIndex: 'totalNumber',
                                key: 'totalNumber',
                                align: 'center',
                                width: 300,
                            },
                            form: {
                                label: '任务数',
                                field: 'totalNumber',
                                type: 'number',
                                required: true,

                            },
                        },
                        {
                            table: {
                                title: '备注',
                                dataIndex: 'removeNumber',
                                key: 'removeNumber',
                                align: 'center',
                                width: 300,
                            },
                            form: {
                                label: '备注',
                                field: 'removeNumber',
                                type: 'number',
                                required: true,
                            },
                        },
                    ]}
                    actionBtns={[
                        {
                            name: 'add',
                            type: 'primary',
                            icon: 'plus',
                            label: '新增',
                            formBtns: [
                                {
                                    name: 'cancel',
                                    type: 'dashed',
                                    label: '取消',
                                },
                                {
                                    name: 'submit',
                                    type: 'primary',
                                    label: '保存',
                                    fetchConfig: {
                                        apiName: 'addZjLzehSafetyInspectionManagement'
                                    }
                                }
                            ]
                        },
                        {
                            name: 'edit',
                            type: 'primary',
                            label: '修改',
                            onClick: (obj) => {
                                this.table.clearSelectedRows();
                                //清除列表勾选
                            },
                            formBtns: [
                                {
                                    name: 'cancel',
                                    type: 'dashed',
                                    label: '取消',
                                },
                                {
                                    name: 'submit',
                                    type: 'primary',
                                    label: '保存',
                                    fetchConfig: {
                                        apiName: 'updateZjLzehSafetyInspectionManagement'
                                    }
                                }
                            ]
                        },
                        {
                            name: 'del',
                            icon: 'delete',
                            type: 'danger',
                            label: '删除',
                            fetchConfig: {
                                apiName: 'batchDeleteUpdateZjLzehSafetyInspectionManagement'
                            },
                        }
                    ]}
                />
            </div>
        );
    }
}

export default index;