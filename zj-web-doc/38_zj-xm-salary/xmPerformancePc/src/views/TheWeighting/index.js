import React, { Component } from "react";
import QnnTable from "../modules/qnn-table";
import moment from 'moment';
const config = {
    antd: {
        rowKey: function (row) {
            return row.managementId
        },
        size: 'small'
    },
    drawerConfig: {
        width: '900px'
    },
    paginationConfig: {
        position: 'bottom'
    },
    searchBtnsStyle: 'inline'
}
class Index extends Component {
    render() {
        return (
            <div>
                <QnnTable
                    {...this.props}
                    fetch={this.props.myFetch}
                    upload={this.props.myUpload}
                    headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                    fetchConfig={{
                        apiName: 'getZjXmJxWeightManagementList'
                    }}
                    actionBtns={[
                        {
                            name: 'add',
                            icon: 'plus',
                            type: 'primary',
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
                                    label: '提交',
                                    fetchConfig: {
                                        apiName: 'addZjXmJxWeightManagement',
                                    }
                                }
                            ]
                        },
                        {
                            name: 'edit',
                            icon: 'edit',
                            type: 'primary',
                            label: '修改',
                            onClick: (obj) => {
                                obj.btnCallbackFn.clearSelectedRows();
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
                                    label: '提交',
                                    fetchConfig: {
                                        apiName: 'updateZjXmJxWeightManagement',
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
                                apiName: 'batchDeleteUpdateZjXmJxWeightManagement',
                            },
                        }
                    ]}
                    {...config}
                    formConfig={[
                        {
                            isInTable: false,
                            form: {
                                field: 'managementId',
                                type: 'string',
                                hide: true,
                            }
                        },
                        {
                            table: {
                                title: '年月',
                                dataIndex: 'yearMonth',
                                key: 'yearMonth',
                                width: 150,
                                fixed: 'left',
                                filter: true,
                                onClick: 'detail',
                                render:(data) => {
                                    return moment(data).format('YYYY-MM');
                                }
                            },
                            form: {
                                type: 'month',
                                placeholder: '请选择',
                                editDisabled:true,
                                required: true
                            }
                        },
                        {
                            table: {
                                title: '任务考核权重',
                                dataIndex: 'taskWeight',
                                key: 'taskWeight',
                                width: 150
                            },
                            form: {
                                type: 'number',
                                placeholder: '请输入',
                                required: true
                            }
                        },
                        {
                            table: {
                                title: '周边业绩考核权重',
                                dataIndex: 'peripheryWeight',
                                key: 'peripheryWeight',
                                width: 150
                            },
                            form: {
                                type: 'number',
                                placeholder: '请输入',
                                required: true
                            }
                        },
                        {
                            table: {
                                title: '项目正职考核权重',
                                dataIndex: 'principalWeight',
                                key: 'principalWeight',
                                width: 150
                            },
                            form: {
                                type: 'number',
                                placeholder: '请输入',
                                required: true
                            }
                        },
                        {
                            table: {
                                title: '备注',
                                dataIndex: 'remarks',
                                key: 'remarks',
                                width: 150,
                                tooltip:20,
                            },
                            form: {
                                type: 'textarea',
                                placeholder: '请输入'
                            }
                        }
                    ]}
                />
            </div>
        );
    }
}
export default Index;