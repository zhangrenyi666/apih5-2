import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
// import { message as Msg, Modal } from 'antd';
// const confirm = Modal.confirm;
import moment from 'moment';
const config = {
    antd: {
        rowKey: function (row) {
            return row.id
        },
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

        }
    }
    render() {
        const { departmentId } = this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany;
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
                        apiName: 'getZxSaProjectFinishList',
                        otherParams: {
                            orgID: departmentId
                        }
                    }}
                    actionBtns={[
                        // {
                        //     name: 'synchronization',//内置add del
                        //     type: 'primary',//类型  默认 primary  [primary dashed danger]
                        //     label: '同步',
                        //     onClick: () => {
                        //         confirm({
                        //             content: '确定同步数据吗?',
                        //             onOk: () => {
                        //                 this.props.myFetch('syncZxSaProjectFinish', { orgID: departmentId }).then(
                        //                     ({ success, message }) => {
                        //                         if (success) {
                        //                             Msg.success(message);
                        //                             this.table.refresh();
                        //                         } else {
                        //                             Msg.error(message);
                        //                         }
                        //                     }
                        //                 );
                        //             }
                        //         });
                        //     }
                        // },
                        {
                            name: 'edit',//内置add del
                            icon: 'edit',//icon
                            type: 'primary',//类型  默认 primary  [primary dashed danger]
                            label: '修改',
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
                                        apiName: 'updateZxSaProjectFinish',
                                    },
                                    onClick: (obj) => {
                                        obj.btnCallbackFn.clearSelectedRows();
                                    }
                                }
                            ]
                        },
                        // {
                        //     name: 'transmitToLowerLevels',
                        //     type: 'primary',//类型  默认 primary  [primary dashed danger]
                        //     label: '下达',
                        //     disabled: (obj) => {
                        //         if (obj.btnCallbackFn.getSelectedRows().length) {
                        //             return false;
                        //         } else {
                        //             return true;
                        //         }
                        //     }
                        // },
                        // {
                        //     name: 'del',//内置add del
                        //     icon: 'delete',//icon
                        //     type: 'danger',//类型  默认 primary  [primary dashed danger]
                        //     label: '删除',
                        //     fetchConfig: {//ajax配置
                        //         apiName: 'batchDeleteUpdateZxSaProjectFinish',
                        //     },
                        // }
                    ]}
                    formConfig={[
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
                                field: 'pp1',
                                type: 'string',
                                hide: true,
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                field: 'comID',
                                type: 'string',
                                hide: true,
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                field: 'comName',
                                type: 'string',
                                hide: true,
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                field: 'orgID',
                                type: 'string',
                                hide: true,
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                field: 'contractID',
                                type: 'string',
                                hide: true,
                            }
                        },
                        {
                            table: {
                                title: '合同编号',
                                dataIndex: 'contractNo',
                                key: 'contractNo',
                                filter: true
                            },
                            form: {
                                type: 'string',
                                addDisabled: true,
                                editDisabled: true,
                                spanForm: 8,
                                placeholder: '请输入'
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: '合同名称',
                                field: 'contractName',
                                type: 'string',
                                addDisabled: true,
                                editDisabled: true,
                                spanForm: 8,
                                placeholder: '请输入'
                            }
                        },
                        {
                            table: {
                                title: '项目名称',
                                dataIndex: 'orgName',
                                key: 'orgName',
                                filter: true,
                                onClick: 'detail'
                            },
                            form: {
                                type: 'string',
                                addDisabled: true,
                                editDisabled: true,
                                spanForm: 8,
                                placeholder: '请输入'
                            }
                        },
                        {
                            table: {
                                title: '项目所在地',
                                dataIndex: 'pp7',
                                key: 'pp7',
                                type: 'select'
                            },
                            form: {
                                type: 'select',
                                optionConfig: {
                                    label: 'itemName', //默认 label
                                    value: 'itemId'
                                },
                                fetchConfig: {
                                    apiName: 'getBaseCodeSelect',
                                    otherParams: (obj) => {
                                        if(obj?.rowData?.pp1 === '10001' || obj?.initialValues?.pp1 === '10001'){
                                            return {
                                                itemId: 'xingzhengquhuadaima'
                                            }
                                        }else{
                                            return {
                                                itemId: 'haiWaiXiangMuSuoZaiShengFenJianCheng'
                                            }
                                        }
                                    }
                                },
                                addDisabled: true,
                                editDisabled: true,
                                spanForm: 8,
                                placeholder: '请选择'
                            }
                        },
                        {
                            table: {
                                title: '合同总造价(万元)',
                                dataIndex: 'contractCost',
                                key: 'contractCost'
                            },
                            form: {
                                type: 'number',
                                addDisabled: true,
                                editDisabled: true,
                                spanForm: 8,
                                placeholder: '请输入'
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: '项目经理',
                                field: "projectManager",
                                type: 'string',
                                addDisabled: true,
                                editDisabled: true,
                                spanForm: 8,
                                placeholder: '请输入'
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: '合同开工日期',
                                field: "actualStartDate",
                                type: 'date',
                                addDisabled: true,
                                editDisabled: true,
                                spanForm: 8,
                                placeholder: '请选择'
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: '合同竣工日期',
                                field: "actualEndDate",
                                type: 'date',
                                addDisabled: true,
                                editDisabled: true,
                                spanForm: 8,
                                placeholder: '请选择'
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: '合同工期(天)',
                                field: "csTimeLimit",
                                type: 'string',
                                addDisabled: true,
                                editDisabled: true,
                                spanForm: 8,
                                placeholder: '请输入'
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: '项目开工日期',
                                field: "realBeginDate",
                                type: 'date',
                                addDisabled: true,
                                editDisabled: true,
                                spanForm: 8,
                                placeholder: '请选择'
                            }
                        },
                        {
                            table: {
                                title: '主体完工日期',
                                dataIndex: 'realSettleEndDate',
                                key: 'realSettleEndDate',
                                format: 'YYYY-MM-DD'
                            },
                            form: {
                                type: 'date',
                                spanForm: 8,
                                onChange:(val,obj) => {
                                    let tableOneData = obj.form.getFieldValue('itemList');
                                    tableOneData = tableOneData.map((item) => {
                                        item.planSettleCloseDate = moment(val).subtract(-3, "months").valueOf();
                                        item.realSettleCloseDate = moment(val).subtract(-3, "months").valueOf();
                                        return item;
                                    })
                                    obj.form.setFieldsValue({
                                        itemList:tableOneData
                                    })
                                },
                                placeholder: '请选择'
                            }
                        },
                        {
                            table: {
                                title: '项目归档日期',
                                dataIndex: 'determineFilingDate',
                                key: 'determineFilingDate',
                                format: 'YYYY-MM-DD'
                            },
                            form: {
                                type: 'date',
                                spanForm: 8,
                                addDisabled: true,
                                editDisabled: true,
                                placeholder: '请选择'
                            }
                        },
                        // {
                        //     table: {
                        //         title: '计划结算关闭日期',
                        //         dataIndex: 'planSettleCloseDate',
                        //         key: 'planSettleCloseDate',
                        //         format: 'YYYY-MM-DD'
                        //     },
                        //     form: {
                        //         type: 'date',
                        //         spanForm: 8,
                        //         placeholder: '请选择'
                        //     }
                        // },
                        // {
                        //     table: {
                        //         title: '实际结算关闭日期',
                        //         dataIndex: 'realSettleCloseDate',
                        //         key: 'realSettleCloseDate',
                        //         format: 'YYYY-MM-DD'
                        //     },
                        //     form: {
                        //         type: 'date',
                        //         spanForm: 8,
                        //         placeholder: '请选择'
                        //     }
                        // },
                        {
                            isInTable: false,
                            form: {
                                label: '填报日期',
                                field: "reportDate",
                                type: 'date',
                                required: true,
                                spanForm: 8,
                                initialValue: new Date(),
                                placeholder: '请选择'
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: '填报人',
                                field: "reportPerson",
                                type: 'string',
                                addDisabled: true,
                                editDisabled: true,
                                spanForm: 8,
                                placeholder: '请输入'
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: '备注',
                                field: "remark",
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
                            table: {
                                title: '完工状态',
                                dataIndex: 'finishStatus',
                                key: 'finishStatus',
                                type: 'select'
                            },
                            form: {
                                type: 'select',
                                optionConfig: {
                                    label: 'itemName',
                                    value: 'itemId',
                                },
                                fetchConfig: {
                                    apiName: 'getBaseCodeSelect',
                                    otherParams: {
                                        itemId: 'wanGongShenHeWanGongZhuangTai'
                                    }
                                },
                                hide: true
                            }
                        },
                        // {
                        //     table: {
                        //         title: '状态',
                        //         dataIndex: 'auditStatus',
                        //         key: 'auditStatus',
                        //         type: 'select'
                        //     },
                        //     form: {
                        //         type: 'select',
                        //         optionConfig: {
                        //             label: 'itemName',
                        //             value: 'itemId',
                        //         },
                        //         fetchConfig: {
                        //             apiName: 'getBaseCodeSelect',
                        //             otherParams: {
                        //                 itemId: 'wanGongShenHeZhuangTai'
                        //             }
                        //         },
                        //         hide: true
                        //     }
                        // },
                        {
                            isInTable: false,
                            form: {
                                type: 'qnnTable',
                                field: 'itemList',
                                incToForm: true,
                                qnnTableConfig: {
                                    actionBtnsPosition: "top",
                                    antd: {
                                        rowKey: 'id',
                                        size: 'small'
                                    },
                                    paginationConfig: false,
                                    isShowRowSelect: false,
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
                                            isInTable: false,
                                            form: {
                                                field: 'comID',
                                                hide: true
                                            }
                                        },
                                        {
                                            isInTable: false,
                                            form: {
                                                field: 'comName',
                                                hide: true
                                            }
                                        },
                                        {
                                            isInTable: false,
                                            form: {
                                                field: 'settleTypeCode',
                                                hide: true
                                            }
                                        },
                                        {
                                            isInTable: false,
                                            form: {
                                                label: '合同id',
                                                field: 'contractID',
                                                hide: true
                                            }
                                        },
                                        {
                                            table: {
                                                title: '序号',
                                                dataIndex: 'orderNum',
                                                key: 'orderNum',
                                                width: 50,
                                                fixed: 'left'
                                            },
                                            isInForm: false
                                        },
                                        {
                                            table: {
                                                title: '关闭结算类型',
                                                dataIndex: 'settleType',
                                                key: 'settleType'
                                            },
                                            form: {
                                                type: 'string',
                                                placeholder: '请输入',
                                            },
                                        },
                                        {
                                            table: {
                                                title: '计划结算关闭日期',
                                                dataIndex: 'planSettleCloseDate',
                                                key: 'planSettleCloseDate',
                                                format: 'YYYY-MM-DD'
                                            },
                                            form: {
                                                type: 'date',
                                                placeholder: '请选择'
                                            }
                                        },
                                        {
                                            table: {
                                                title: '实际结算关闭日期',
                                                dataIndex: 'realSettleCloseDate',
                                                key: 'realSettleCloseDate',
                                                format: 'YYYY-MM-DD',
                                                tdEdit: true
                                            },
                                            form: {
                                                type: 'date',
                                                placeholder: '请选择'
                                            }
                                        },
                                        {
                                            table: {
                                                title: '延期原因',
                                                dataIndex: 'delayReason',
                                                key: 'delayReason',
                                                tdEdit: true
                                            },
                                            form: {
                                                type: 'string',
                                                placeholder: '请输入',
                                            },
                                        },
                                        {
                                            table: {
                                                title: '填报人',
                                                dataIndex: 'reportPerson',
                                                key: 'reportPerson'
                                            },
                                            form: {
                                                type: 'string',
                                                placeholder: '请输入',
                                            },
                                        },
                                        {
                                            table: {
                                                title: '填报日期',
                                                dataIndex: 'reportDate',
                                                key: 'reportDate',
                                                format: 'YYYY-MM-DD',
                                                tdEdit: true
                                            },
                                            form: {
                                                type: 'date',
                                                initialValue: new Date(),
                                                placeholder: '请选择',
                                            },
                                        },
                                        {
                                            table: {
                                                title: '备注',
                                                dataIndex: 'remark',
                                                key: 'remark',
                                                tdEdit: true
                                            },
                                            form: {
                                                type: 'string',
                                                placeholder: '请输入',
                                            },
                                        }
                                    ]
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