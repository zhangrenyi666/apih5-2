import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import { message as Msg } from 'antd';
const config = {
    antd: {
        rowKey: 'id',
        size: 'small'
    },
    drawerConfig: {
        width: '1200px'
    },
    paginationConfig: {
        position: 'bottom'
    },
    formItemLayout: {
        labelCol: {
            xs: { span: 10 },
            sm: { span: 10 }
        },
        wrapperCol: {
            xs: { span: 14 },
            sm: { span: 14 }
        }
    }
};
class index extends Component {
    constructor(props) {
        super(props);
        this.state = {
            rowData: props?.selectedRows?.[0] || props?.rowData || {},
            drawerDetailTitle:props?.drawerDetailTitle || ''
        }
    }
    render() {
        const { rowData, drawerDetailTitle } = this.state;
        return (
            <div style={{padding:'10px'}}>
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
                        apiName: 'getZxCtAlterList',
                        otherParams:{
                            orgID:rowData?.orgID
                        }
                    }}
                    actionBtns={drawerDetailTitle === '详情' ? [] : [
                        {
                            name: 'add',//内置add del
                            icon: 'plus',//icon
                            type: 'primary',//类型  默认 primary  [primary dashed danger]
                            label: '新增',
                            formBtns: [
                                {
                                    name: 'cancel', //关闭右边抽屉
                                    type: 'dashed',//类型  默认 primary
                                    label: '取消',
                                },
                                {
                                    name: 'submit',//内置add del
                                    type: 'primary',//类型  默认 primary
                                    label: '保存',//提交数据并且关闭右边抽屉 
                                    fetchConfig: {//ajax配置
                                        apiName: 'addZxCtAlter',
                                    }
                                }
                            ]
                        },
                        {
                            name: 'edit',//内置add del
                            icon: 'edit',//icon
                            type: 'primary',//类型  默认 primary  [primary dashed danger]
                            label: '修改',
                            disabled: (obj) => {
                                let data = obj.btnCallbackFn.getSelectedRows();
                                if (data.length === 1 && data[0].auditStatus !== '2') {
                                    return false;
                                } else {
                                    return true;
                                }
                            },
                            formBtns: [
                                {
                                    name: 'cancel', //关闭右边抽屉
                                    type: 'dashed',//类型  默认 primary
                                    label: '取消',
                                },
                                {
                                    name: 'submit',//内置add del
                                    type: 'primary',//类型  默认 primary
                                    label: '保存',//提交数据并且关闭右边抽屉 
                                    fetchConfig: {//ajax配置
                                        apiName: 'updateZxCtAlter',
                                    },
                                    onClick: (obj) => {
                                        obj.btnCallbackFn.clearSelectedRows();
                                    }
                                }
                            ]
                        },
                        {
                            name: 'audit',
                            type: 'primary',//类型  默认 primary  [primary dashed danger]
                            label: '审核',
                            disabled: (obj) => {
                                let data = obj.btnCallbackFn.getSelectedRows();
                                if (data.length === 1 && data[0].auditStatus !== '2') {
                                    return false;
                                } else {
                                    return true;
                                }
                            },
                            onClick: (obj) => {
                                this.props.myFetch('zxCtAlterAudit', obj.selectedRows[0]).then(
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
                        },
                        // {
                        //     name: 'Reported',
                        //     type: 'primary',//类型  默认 primary  [primary dashed danger]
                        //     label: '上报',
                        //     disabled: (obj) => {
                        //         let data = obj.btnCallbackFn.getSelectedRows();
                        //         if (data.length === 1 && data[0].auditStatus !== '2') {
                        //             return false;
                        //         } else {
                        //             return true;
                        //         }
                        //     },
                        //     onClick: (obj) => {
                        //         this.props.myFetch('zxCtAlterReporting', obj.selectedRows[0]).then(
                        //             ({ success, message }) => {
                        //                 if (success) {
                        //                     Msg.success(message);
                        //                     obj.btnCallbackFn.clearSelectedRows();
                        //                     obj.btnCallbackFn.refresh();
                        //                 } else {
                        //                     Msg.error(message);
                        //                 }
                        //             }
                        //         );
                        //     }
                        // },
                        {
                            name: 'del',//内置add del
                            icon: 'delete',//icon
                            type: 'danger',//类型  默认 primary  [primary dashed danger]
                            label: '删除',
                            disabled: (obj) => {
                                let data = obj.btnCallbackFn.getSelectedRows();
                                if (data.length && !data.filter(item => item.auditStatus === '2').length) {
                                    return false;
                                } else {
                                    return true;
                                }
                            },
                            fetchConfig: {//ajax配置
                                apiName: 'batchDeleteUpdateZxCtAlter',
                            },
                        }
                    ]}
                    formConfig={[
                        {
                            isInTable: false,
                            form: {
                                field: 'orgID',
                                type: 'string',
                                initialValue: () => {
                                    return rowData?.orgID;
                                },
                                hide: true,
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                field: 'id',
                                type: 'string',
                                hide: true,
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                field: 'workBookID',
                                type: 'string',
                                hide: true,
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                field: 'auditStatus',
                                type: 'string',
                                hide: true,
                            }
                        },
                        {
                            table: {
                                title: '提出单位',
                                dataIndex: 'proposer',
                                key: 'proposer',
                                width: 150,
                                filter: true,
                                fixed: 'left',
                                onClick: "detail"
                            },
                            form: {
                                type: 'string',
                                required: true,
                                placeholder: '请输入',
                                spanForm: 8
                            }
                        },
                        {
                            table: {
                                title: '申报文号',
                                dataIndex: 'applyNo',
                                key: 'applyNo',
                                width: 150,
                                filter: true,
                                fixed: 'left'
                            },
                            form: {
                                type: 'string',
                                required: true,
                                placeholder: '请输入',
                                spanForm: 8
                            }
                        },
                        {
                            table: {
                                title: '申报金额',
                                dataIndex: 'applyAmount',
                                key: 'applyAmount',
                                width: 100,
                            },
                            form: {
                                type: 'number',
                                placeholder: '请输入',
                                spanForm: 8
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: '申报日期',
                                field: 'applyDate',
                                type: 'date',
                                initialValue: new Date(),
                                placeholder: '请选择',
                                spanForm: 8
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: '变更等级',
                                field: 'alterLevel',
                                type: 'select',
                                optionConfig: {
                                    label: 'itemName', //默认 label
                                    value: 'itemId'
                                },
                                fetchConfig: {
                                    apiName: 'getBaseCodeSelect',
                                    otherParams: {
                                        itemId: 'bianGengDengJi'
                                    }
                                },
                                allowClear: false,
                                showSearch: true,
                                placeholder: '请选择',
                                spanForm: 8
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: '发生日期',
                                field: 'happenDate',
                                type: 'date',
                                initialValue: new Date(),
                                placeholder: '请选择',
                                spanForm: 8
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: '需要公司协助',
                                field: 'companyHelp',
                                type: 'radio',
                                optionData: [
                                    {
                                        label: "否",
                                        value: "0",
                                    },
                                    {
                                        label: "是",
                                        value: "1",
                                    }
                                ],
                                placeholder: '请选择',
                                spanForm: 8
                            }
                        },
                        {
                            table: {
                                title: '变更内容',
                                dataIndex: 'alterContent',
                                key: 'alterContent',
                                width: 150,
                            },
                            form: {
                                type: 'textarea',
                                placeholder: '请输入',
                                formItemLayout: {
                                    labelCol: {
                                        xs: { span: 3 },
                                        sm: { span: 3 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 21 },
                                        sm: { span: 21 }
                                    }
                                }
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: '变更原因',
                                field: 'alterReason',
                                type: 'textarea',
                                placeholder: '请输入',
                                formItemLayout: {
                                    labelCol: {
                                        xs: { span: 3 },
                                        sm: { span: 3 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 21 },
                                        sm: { span: 21 }
                                    }
                                }
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: '驻地监理确认金额',
                                field: 'confirmatAmountOfResidentSupervision',
                                type: 'number',
                                placeholder: '请输入',
                                spanForm: 8
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: '驻地监理确认日期',
                                field: 'confirmatDateOfResidentSupervisor',
                                type: 'date',
                                placeholder: '请选择',
                                spanForm: 8
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: '总监办确认金额',
                                field: 'amountConfirmedByDirectorOffice',
                                type: 'number',
                                placeholder: '请输入',
                                spanForm: 8
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: '总监办确认日期',
                                field: 'dateConfirmedByDirectorOffice',
                                type: 'date',
                                placeholder: '请选择',
                                spanForm: 8
                            }
                        },
                        {
                            table: {
                                title: '批复文号',
                                dataIndex: 'replyNo',
                                key: 'replyNo',
                                width: 150,
                            },
                            form: {
                                type: 'string',
                                placeholder: '请输入',
                                spanForm: 8
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: '业主批复日期',
                                field: 'replyDate',
                                type: 'date',
                                placeholder: '请选择',
                                spanForm: 8
                            }
                        },
                        {
                            table: {
                                title: '业主批复金额',
                                dataIndex: 'replyAmount',
                                key: 'replyAmount',
                                width: 120,
                            },
                            form: {
                                type: 'number',
                                placeholder: '请输入',
                                spanForm: 8
                            }
                        },
                        {
                            table: {
                                title: '业主批复状态',
                                dataIndex: 'replyStatus',
                                key: 'replyStatus',
                                width: 120,
                                type: 'select'
                            },
                            form: {
                                type: 'select',
                                // initialValue: () => {
                                //     return rowData?.replyStatus;
                                // },
                                optionConfig: {
                                    label: 'itemName', //默认 label
                                    value: 'itemId'
                                },
                                fetchConfig: {
                                    apiName: 'getBaseCodeSelect',
                                    otherParams: {
                                        itemId: 'yeZhuPiFuZhuangTai'
                                    }
                                },
                                allowClear: false,
                                required: true,
                                placeholder: '请选择',
                                spanForm: 8
                            }
                        },
                        {
                            table: {
                                title: '审核状态',
                                dataIndex: 'auditStatus',
                                key: 'auditStatus',
                                width: 100,
                                render: (data) => {
                                    if (data === '0') {
                                        return '未审核';
                                    } else if(data === '1') {
                                        return '已上报';
                                    } else if(data === '2') {
                                        return '已审核';
                                    }else{
                                        return '';
                                    }
                                }
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '备注',
                                dataIndex: 'remarks',
                                key: 'remarks',
                                width: 100,
                            },
                            form: {
                                type: 'textarea',
                                placeholder: '请输入',
                                formItemLayout: {
                                    labelCol: {
                                        xs: { span: 3 },
                                        sm: { span: 3 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 21 },
                                        sm: { span: 21 }
                                    }
                                }
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: '附件',
                                field: 'attachment',
                                type: "files",
                                initialValue: [],
                                fetchConfig: {
                                    apiName: "upload"
                                },
                                formItemLayout: {
                                    labelCol: {
                                        xs: { span: 3 },
                                        sm: { span: 3 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 21 },
                                        sm: { span: 21 }
                                    }
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