//质量检查统计
import React, { Component } from "react";
import QnnTable from "../modules/qnn-table";
import { Modal } from "antd";
const confirm = Modal.confirm;
const config = {
    fetchConfig: {
        apiName: 'getZjLzehQualityInspectionStatisticsList'
    },
    //checkbox
    antd: {
        rowKey: function (row) {
            return row.qualityInspectionStatisticsId
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
    paginationConfig: false
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
                            isInTable: false,
                            form: {
                                label: '主键id',
                                field: 'qualityInspectionStatisticsId',
                                hide: true,
                            }
                        },
                        {
                            table: {
                                title: '排序',
                                dataIndex: 'orderFlag',
                                key: 'orderFlag',
                                align: 'center',
                                width: 100,
                            },
                            form: {
                                label: '排序',
                                field: 'orderFlag',
                                type: 'number'
                            },
                        },
                        {
                            table: {
                                title: '名称',
                                dataIndex: 'name',
                                key: 'name',
                                align: 'center',

                            },
                            form: {
                                label: '名称',
                                field: 'name',
                                type: 'string',
                                required: true
                            },
                        },
                        {
                            table: {
                                title: '总计数量',
                                dataIndex: 'totalNumber',
                                key: 'totalNumber',
                                align: 'center',
                                width: 300,
                            },
                            form: {
                                label: '总计数量',
                                field: 'totalNumber',
                                type: 'number',
                                required: true
                            },
                        },
                        {
                            table: {
                                title: '解除数量',
                                dataIndex: 'removeNumber',
                                key: 'removeNumber',
                                align: 'center',
                                width: 300,
                            },
                            form: {
                                label: '解除数量',
                                field: 'removeNumber',
                                type: 'number',
                                required: true

                            },
                        },
                    ]}
                    actionBtns={[
                        // {
                        //     name: 'add',
                        //     icon: 'plus',
                        //     type: 'primary',
                        //     label: '新增',
                        //     field: 'addOutBtn',
                        //     formBtns: [
                        //         {
                        //             name: 'cancel',
                        //             type: 'dashed',
                        //             label: '取消',
                        //         },
                        //         {
                        //             name: 'submit',
                        //             type: 'primary',
                        //             label: '保存',
                        //             fetchConfig: {
                        //                 apiName: 'addZjLzehQualityInspectionStatistics'
                        //             }
                        //         }
                        //     ]
                        // },
                        {
                            name: 'edit',
                            type: 'primary',
                            label: '修改',
                            editDisabled: false,
                            onClick: (obj) => {
                                this.table.clearSelectedRows();
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
                                        apiName: 'updateZjLzehQualityInspectionStatistics'
                                    }
                                }
                            ]
                        },
                        // {
                        //     name: 'del',
                        //     icon: 'delete',
                        //     type: 'danger',
                        //     label: '删除',
                        //     fetchConfig: {
                        //         apiName: 'batchDeleteUpdateZjLzehQualityInspectionStatistics'
                        //     },
                        // }
                    ]}
                />
            </div>

        );
    }
}

export default index;