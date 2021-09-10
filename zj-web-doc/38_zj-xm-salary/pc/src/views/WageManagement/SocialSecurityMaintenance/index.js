import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import moment from 'moment';
import { push } from "react-router-redux";
import { message as Msg } from "antd";
import Apih5 from "qnn-apih5"
const config = {
    antd: {
        rowKey: 'socialSecurityManagementId',
        size: 'small'
    },
    drawerConfig: {
        width: '900px'
    },
    paginationConfig: {
        position: 'bottom'
    },
    firstRowIsSearch: false,
    isShowRowSelect: true,

};
class index extends Apih5 {
    constructor(props) {
        super(props);
        this.state = {}
    }
    componentDidMount() { }
    render() {
        const orgId = this.apih5.getOrgId();
        const { mainModule } = this.props.myPublic.appInfo;
        return (
            <div>
                <QnnTable
                    {...this.props}
                    fetch={this.props.myFetch}
                    upload={this.props.myUpload}
                    headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                    wrappedComponentRef={(me) => {
                        this.table = me;
                    }}
                    {...config}
                    fetchConfig={{
                        apiName: 'getZjXmSalarySocialSecurityManagementList',
                        otherParams: {
                            orgId: orgId
                        }
                    }}
                    method={{}}
                    formConfig={[
                        {
                            isInTable: false,
                            form: {
                                field: 'socialSecurityManagementId',
                                type: 'string',
                                hide: true,
                            }
                        },
                        {
                            table: {
                                title: '月份',
                                width: 120,
                                onClick: 'detail',
                                dataIndex: 'month',
                                key: 'month',
                                render: (data) => {
                                    return data ? moment(data).format('YYYY-MM') : null
                                }
                            },
                            form: {
                                type: 'month',
                                required: true,
                                field: 'month',
                                onChange: (val, rowData) => {
                                    this.props.myFetch('checkZjXmSalarySocialSecurityManagementMonth', rowData.form.getFieldsValue()).then(({ data, success, message }) => {
                                        if (success) {
                                            if (data?.check === false) {
                                                Msg.warn(data.checkMessage + '  请重新选择！');
                                                setTimeout(() => {
                                                    rowData.form.setFieldsValue({
                                                        month: null
                                                    })
                                                }, 200)
                                            } else {

                                            }
                                        } else {
                                            Msg.error(message)
                                        }
                                    })
                                }
                            }
                        },
                        {
                            table: {
                                title: '备注',
                                dataIndex: 'remarks',
                                key: 'remarks'
                            },
                            form: {
                                field: 'remarks',
                                type: 'textarea',
                                placeholder: '请输入'

                            },
                        },
                        {
                            table: {
                                title: '状态',
                                dataIndex: 'status',
                                key: 'status',
                                type: 'select',
                                tdEdit: true,
                                tdEditCb: (obj) => {
                                    let ovj = {
                                        socialSecurityManagementId: obj.newRowData.socialSecurityManagementId,
                                        status: obj.newRowData.status
                                    }
                                    this.props.myFetch('updateZjXmSalarySocialSecurityManagementStatus', ovj).then(
                                        ({ data, success, message }) => {
                                            if (success) {
                                                Msg.success(message);
                                                obj.qnnTableInstance.setEditedRowData({
                                                    status: obj.newRowData.status
                                                })
                                            } else {
                                                Msg.error(message);
                                                obj.qnnTableInstance.setEditedRowData({
                                                    status: obj.oldRowData.status
                                                })
                                            }
                                            this.table.refresh();
                                        }
                                    );
                                },
                            },
                            form: {
                                field: 'status',
                                type: 'select',
                                addShow: true,
                                allowClear: false,
                                detailShow: false,
                                editShow: true,
                                fetchConfig: {
                                    apiName: 'getBaseCodeSelect',
                                    otherParams: {
                                        itemId: 'qiYongZhuangTai'
                                    }
                                },
                                optionConfig: {
                                    label: 'itemName',
                                    value: 'itemId'
                                },
                                placeholder: '请输入'
                            },
                        },
                        {
                            table: {
                                title: '修改者',
                                dataIndex: 'modifyUserName',
                                key: 'modifyUserName'
                            },
                            isInForm: false,
                            form: {
                                field: 'modifyUserName',
                                type: 'string',
                                placeholder: '请输入'
                            },
                        },
                        {
                            table: {
                                title: '修改日期',
                                width: 120,
                                // onClick: 'detail',
                                dataIndex: 'modifyTime',
                                key: 'modifyTime',
                                render: (data) => {
                                    return data ? moment(data).format('YYYY-MM-DD') : null
                                }
                            },
                            isInForm: false,
                            form: {
                                type: 'date',
                                required: true,
                                field: 'modifyTime',
                                initialValue: () => {
                                    return moment(new Date()).format('YYYY-MM-DD')
                                }
                            }
                        },
                        {
                            isInForm: false,
                            table: {
                                showType: 'tile',
                                width: 80,
                                title: "操作",
                                key: "action",
                                fixed: 'right',
                                btns: [
                                    {
                                        name: "edit",
                                        render: function () {
                                            return "<a>修改</a>";
                                        },
                                        willExecute: 'bind:willExecuteFunEdit',
                                        formBtns: [
                                            {
                                                name: "cancel",
                                                type: "dashed",
                                                label: "取消"
                                            },
                                            {
                                                name: "submit",
                                                type: "primary",
                                                label: "提交",
                                                fetchConfig: {
                                                    apiName: "updateZjXmSalarySocialSecurityManagement"
                                                }
                                            }
                                        ]
                                    },
                                    {
                                        label: "明细",
                                        name: 'diyDetail',
                                        render: () => {
                                            return '明细'
                                        },
                                        willExecute: 'bind:willExecuteFunEdit',
                                        // onClick: (obj) => {
                                        //     const { mainModule } = this.props.myPublic.appInfo;
                                        //     const { socialSecurityManagementId } = obj.rowData;
                                        //     this.props.dispatch(
                                        //         push(`${mainModule}detailndex/${socialSecurityManagementId}`)
                                        //     )
                                        // }
                                    }
                                ]
                            }
                        }

                    ]}
                    method={{
                        willExecuteFunEdit: async (obj) => {
                            const { data, success, message } = await this.props.myFetch('checkZjXmSalarySocialSecurityManagementDeleteUpdate', { socialSecurityManagementId: obj.rowData.socialSecurityManagementId });
                            // 明细按钮
                            if (obj.rowInfo.name === "diyDetail") {
                                if (success) {
                                    // 可以操作
                                    const { socialSecurityManagementId } = obj.rowData;
                                    this.props.dispatch(
                                        push(`${mainModule}detailndex/${socialSecurityManagementId}/${'can'}`)
                                    )
                                } else {
                                    // 只允许查看
                                    const { socialSecurityManagementId } = obj.rowData;
                                    this.props.dispatch(
                                        push(`${mainModule}detailndex/${socialSecurityManagementId}/${'cant'}`)
                                    )
                                }
                                return true
                            } else {
                                // 修改按钮
                                if (success) {
                                    return true
                                } else {
                                    Msg.warn(message);
                                    return false
                                }
                            }

                        },
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
                                    label: '保存',
                                    field: 'addsubmit',
                                    fetchConfig: {
                                        apiName: 'addZjXmSalarySocialSecurityManagement'
                                    }
                                }
                            ]
                        },
                        {
                            field: 'delBtn',
                            name: 'del',
                            icon: 'delete',
                            fetchConfig: {
                                apiName: 'deleteZjXmSalarySocialSecurityManagement',
                            },
                            type: 'danger',
                            label: '删除',
                        },
                    ]}
                />
            </div>
        );
    }
}

export default index;