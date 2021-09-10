import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import moment from 'moment';
import { push } from "react-router-redux";
import { message as Msg } from 'antd';
import Apih5 from "qnn-apih5"
const config = {
    antd: {
        rowKey: 'compensationPlanId',
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
        let _me = this;
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
                        apiName: 'getZjXmSalaryCompensationPlanList',
                        otherParams: {
                            // orgId: orgId
                        }
                    }}
                    rowSelection={{
                        type: 'radio',
                        // selectedRowsStyle:{backgroundColor:'#ccc'}
                    }}
                    method={{}}
                    formItemLayout={{
                        labelCol: {
                            xs: { span: 24 },
                            sm: { span: 4 }
                        },
                        wrapperCol: {
                            xs: { span: 24 },
                            sm: { span: 20 }
                        }
                    }}
                    formConfig={[
                        {
                            isInTable: false,
                            form: {
                                field: 'compensationPlanId',
                                type: 'string',
                                hide: true,
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                field: 'salaryRangeName',
                                type: 'string',
                                hide: true,
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                field: 'taxTypeName',
                                type: 'string',
                                hide: true,
                            }
                        },

                        {
                            isInTable: false,
                            form: {
                                field: 'orgId',
                                type: 'string',
                                hide: true,
                                initialValue: orgId
                            }
                        },
                        {
                            table: {
                                title: '方案名称',
                                dataIndex: 'planName',
                                key: 'planName',
                                onClick: 'detail'
                            },
                            form: {
                                field: 'planName',
                                type: 'string',
                                required: true,
                                placeholder: '请输入'

                            },
                        },
                        {
                            table: {
                                title: '发薪范围',
                                dataIndex: 'salaryRangeId',
                                key: 'salaryRangeId',
                                type: 'select',
                            },
                            form: {
                                field: 'salaryRangeId',
                                type: 'select',
                                required: true,
                                placeholder: '请选择',
                                fetchConfig: {
                                    apiName: 'getBaseCodeSelect',
                                    otherParams: {
                                        itemId: 'faXinFanWei'
                                    }
                                },
                                optionConfig: {
                                    label: 'itemName',
                                    value: 'itemId',
                                    linkageFields: {
                                        salaryRangeName: 'itemName'
                                    }
                                },
                            }
                        },
                        {
                            table: {
                                title: '人员类别',
                                dataIndex: 'userType',
                                key: 'userType',
                                type: 'select',
                            },
                            form: {
                                field: 'userType',
                                type: 'select',
                                required: true,
                                placeholder: '请选择',
                                fetchConfig: {
                                    apiName: 'getBaseCodeSelect',
                                    otherParams: {
                                        itemId: 'pinYongLeiBie'
                                    }
                                },
                                optionConfig: {
                                    label: 'itemName',
                                    value: 'itemId'
                                },
                            }
                        },
                        {
                            table: {
                                title: '所在机构',
                                dataIndex: 'departmentFlag',
                                key: 'departmentFlag',
                                type: 'select',
                            },
                            form: {
                                field: 'departmentFlag',
                                type: 'select',
                                required: true,
                                placeholder: '请选择',
                                fetchConfig: {
                                    apiName: 'getBaseCodeSelect',
                                    otherParams: {
                                        itemId: 'departmentType'
                                    }
                                },
                                optionConfig: {
                                    label: 'itemName',
                                    value: 'itemId'
                                },
                            }
                        },
                        {
                            table: {
                                title: '会计类别',
                                dataIndex: 'accountingType',
                                key: 'accountingType',
                                type: 'select',
                            },
                            form: {
                                field: 'accountingType',
                                type: 'select',
                                required: true,
                                placeholder: '请选择',
                                optionData: [
                                    {
                                        label: '除研发费用外所有',
                                        value: '0'
                                    },
                                    {
                                        label: '研发费用',
                                        value: '1'
                                    }
                                ],
                                optionConfig: {
                                    label: 'label',
                                    value: 'value'
                                },
                            }
                        },
                        {
                            table: {
                                title: '计税类型',
                                dataIndex: 'taxTypeId',
                                key: 'taxTypeId',
                                type: 'select',
                            },
                            form: {
                                field: 'taxTypeId',
                                type: 'select',
                                required: true,
                                placeholder: '请选择',
                                optionConfig: {
                                    value: 'value',
                                    label: 'name',
                                    linkageFields: {
                                        taxTypeName: 'name'
                                    }
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
                            }
                        },
                        {
                            table: {
                                title: '税率表',
                                dataIndex: 'taxRateId',
                                key: 'taxRateId',
                                type: 'select',
                            },
                            form: {
                                field: 'taxRateId',
                                type: 'select',
                                required: true,
                                placeholder: '请选择',
                                fetchConfig: {
                                    apiName: "getZjXmSalaryTaxRateTableListStatus"
                                },
                                required: true,
                                optionConfig: {
                                    label: "name",
                                    value: "taxRateTableId"
                                }
                            }
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
                                        compensationPlanId: obj.newRowData.compensationPlanId,
                                        status: obj.newRowData.status
                                    }
                                    this.props.myFetch('updateZjXmSalaryCompensationPlanStatus', ovj).then(
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
                                allowClear: false,
                                detailShow: false,
                                addShow: true,
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
                            isInForm: false,
                            table: {
                                showType: 'tile',
                                // showType: 'bubble',
                                width: 200,
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
                                                    apiName: "updateZjXmSalaryCompensationPlan"
                                                }
                                            }
                                        ]
                                    },
                                    {
                                        label: "人员维护",
                                        name: 'diyDetail',
                                        render: () => {
                                            return '人员维护'
                                        },
                                        onClick: (obj) => {
                                            const { mainModule } = this.props.myPublic.appInfo;
                                            const { compensationPlanId, planName, taxTypeId } = obj.rowData;
                                            this.props.dispatch(
                                                push(`${mainModule}CompensationPlandetailndex/${compensationPlanId}/${planName}/${taxTypeId}`)
                                            )
                                        }
                                    },
                                    {
                                        label: "方案详情",
                                        name: 'diyDetail',
                                        render: () => {
                                            return '方案详情'
                                        },
                                        onClick: (obj) => {
                                            const { mainModule } = this.props.myPublic.appInfo;
                                            const { compensationPlanId } = obj.rowData;
                                            this.props.dispatch(
                                                push(`${mainModule}CompensationPlanPageDetail/${compensationPlanId}`)
                                            )
                                            // this.props.myFetch('getExpressionTabData', {
                                            //     compensationPlanId: compensationPlanId
                                            // }).then(
                                            //     ({ success, message, data }) => {
                                            //         if (success) {
                                            //             // Msg.success(message)
                                            //             this.props.dispatch(
                                            //                 push(`${mainModule}CompensationPlanPageDetail/${compensationPlanId}`)
                                            //             )
                                            //         } else {
                                            //             // Msg .error(message)
                                            //         }
                                            //     }
                                            // );

                                        }
                                    }
                                ]
                            }
                        }

                    ]}
                    method={{
                        willExecuteFunEdit: async (obj) => {
                            const { data, success, message } = await this.props.myFetch('checkZjXmSalaryCompensationPlanDeleteUpdate', { compensationPlanId: obj.rowData.compensationPlanId });
                            console.log(success);
                            if (success) {
                                return true
                            } else {
                                Msg.warn(message);
                                return false
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
                                        apiName: 'addZjXmSalaryCompensationPlan'
                                    }
                                }
                            ]
                        },
                        {
                            field: 'delBtn',
                            name: 'del',
                            icon: 'delete',
                            fetchConfig: {
                                apiName: 'deleteZjXmSalaryCompensationPlan',
                            },
                            type: 'danger',
                            label: '删除',
                            disabled: (obj) => {
                                if (obj.btnCallbackFn.getSelectedRows().length === 1) {
                                    return false;
                                } else {
                                    return true;
                                }
                            },
                        },
                    ]}
                />
            </div>
        );
    }
}

export default index;