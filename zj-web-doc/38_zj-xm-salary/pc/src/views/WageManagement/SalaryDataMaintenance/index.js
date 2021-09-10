import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import moment from 'moment';
import { push } from "react-router-redux";
import Apih5 from "qnn-apih5"
const config = {
    antd: {
        rowKey: 'compensationManagerId',
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
                        apiName: 'getZjXmSalaryCompensationManagerList'
                    }}
                    method={{}}
                    formConfig={[
                        {
                            isInTable: false,
                            form: {
                                field: 'compensationManagerId',//新增--主键为空，也传过去了？？？
                                type: 'string',
                                hide: true,
                            }
                        },
                        {
                            table: {
                                title: '月份',
                                width: 120,
                                onClick: 'detail',
                                dataIndex: 'payMonth',
                                key: 'payMonth',
                                render: (data) => {
                                    return data ? moment(data).format('YYYY-MM') : null
                                }
                            },
                            form: {
                                type: 'month',
                                required: true,
                                field: 'payMonth'
                            }
                        },
                        {
                            table: {
                                title: '类型',
                                dataIndex: 'payTypeId',
                                key: 'payTypeId',
                                type: 'select',
                            },
                            form: {
                                field: 'payTypeId',
                                type: 'select',
                                optionConfig: {
                                    value: 'value',
                                    label: 'name'
                                },
                                optionData: [
                                    {
                                        value: '0',
                                        name: '月薪'
                                    },
                                    {
                                        value: '1',
                                        name: '一次性奖金'
                                    }
                                ]
                            },
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
                                title: '修改者',
                                dataIndex: 'modifyUserName',
                                key: 'modifyUserName'
                            },
                            isInForm: false,
                            form: {
                                field: 'modifyUserName',
                                type: 'string',
                                placeholder: '请输入',
                                spanForm: 12,

                            },
                        },
                        {
                            table: {
                                title: '修改日期',
                                width: 120,
                                onClick: 'detail',
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
                                },
                                spanForm: 12
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
                                                    apiName: "updateZjXmSalaryCompensationManager"
                                                }
                                            }
                                        ]
                                    },
                                    {
                                        label: "薪酬维护",
                                        name: 'diyDetail',
                                        render: () => {
                                            return '薪酬维护'
                                        },
                                        onClick: (obj) => {
                                            const { mainModule } = this.props.myPublic.appInfo;
                                            const { compensationManagerId } = obj.rowData;
                                            this.props.dispatch(
                                                push(`${mainModule}SalaryDataMaintenancedetailndex/${compensationManagerId}`)
                                            )
                                        }
                                    }
                                ]
                            }
                        }

                    ]}
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
                                        apiName: 'addZjXmSalaryCompensationManager'
                                    }
                                }
                            ]
                        },
                        {
                            field: 'delBtn',
                            name: 'del',
                            icon: 'delete',
                            fetchConfig: {
                                apiName: 'deleteZjXmSalaryCompensationManager',
                            },
                            type: 'danger',
                            label: '删除',
                            disabled: (obj) => {
                                if (obj.btnCallbackFn.getSelectedRows().length === 1) {
                                    return false;
                                } else {
                                    return true;
                                }
                            }
                        },
                    ]}
                />
            </div>
        );
    }
}

export default index;