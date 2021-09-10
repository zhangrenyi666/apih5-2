import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import moment from 'moment';
import { message as Msg } from 'antd';
const config = {
    antd: {
        rowKey: 'taxRateTableId',
        size: 'small'
    },
    drawerConfig: {
        width: '1200px'
    },
    paginationConfig: {
        position: 'bottom'
    },
    firstRowIsSearch: false,
    isShowRowSelect: true,

};
const configItem = {
    drawerConfig: {
        width: '1100px'
    },
    paginationConfig: {
        position: 'bottom'
    },
    actionBtnsPosition: "top",
    firstRowIsSearch: false,
    isShowRowSelect: true
};
class index extends Component {
    constructor(props) {
        super(props);
        this.state = {}
    }
    componentDidMount() { }
    render() {
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
                        apiName: 'getZjXmSalaryTaxRateTableList'
                    }}
                    rowSelection={{
                        type: 'radio'
                    }}
                    method={{}}
                    formConfig={[
                        {
                            isInTable: false,
                            form: {
                                field: 'taxRateTableId',
                                type: 'string',
                                hide: true,
                            }
                        },

                        {
                            table: {
                                title: '税率表名称',
                                dataIndex: 'name',
                                key: 'name'
                            },
                            form: {
                                field: 'name',
                                type: 'string',
                                placeholder: '请输入',
                                spanForm: 12,
                                formItemLayoutForm: {
                                    labelCol: {
                                        xs: { span: 24 },
                                        sm: { span: 8 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 24 },
                                        sm: { span: 16 }
                                    }
                                }
                            },
                        },
                        {
                            table: {
                                title: '地区',
                                dataIndex: 'area',
                                key: 'area',
                                type: 'select',
                            },
                            form: {
                                field: 'area',
                                type: 'select',
                                optionData: [
                                    {
                                        label: "全国通用",
                                        value: "0"
                                    }
                                ],
                                placeholder: '请输入',
                                spanForm: 12,
                                formItemLayoutForm: {
                                    labelCol: {
                                        xs: { span: 24 },
                                        sm: { span: 8 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 24 },
                                        sm: { span: 16 }
                                    }
                                }
                            },
                        },
                        {
                            table: {
                                title: '税率表类型',
                                dataIndex: 'type',
                                key: 'type',
                                type: 'select',
                            },
                            form: {
                                field: 'type',
                                type: 'select',//???
                                optionData: [
                                    //默认选项数据
                                    {
                                        label: "劳务报酬所得",
                                        value: "0"
                                    },
                                    {
                                        label: "工资薪酬所得",
                                        value: "1"
                                    }
                                ],
                                placeholder: '请输入',
                                spanForm: 12,
                                formItemLayoutForm: {
                                    labelCol: {
                                        xs: { span: 24 },
                                        sm: { span: 8 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 24 },
                                        sm: { span: 16 }
                                    }
                                }
                            },
                        },
                        {
                            table: {
                                title: '个税起征点(元)',
                                dataIndex: 'individualTaxThreshold',
                                key: 'individualTaxThreshold'
                            },
                            form: {
                                field: 'individualTaxThreshold',
                                type: 'number',
                                placeholder: '请输入',
                                spanForm: 12,
                                formItemLayoutForm: {
                                    labelCol: {
                                        xs: { span: 24 },
                                        sm: { span: 8 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 24 },
                                        sm: { span: 16 }
                                    }
                                }
                            },
                        },
                        {
                            table: {
                                title: '特殊个税起征点（元）',
                                dataIndex: 'specialIndividualTaxThreshold',
                                key: 'specialIndividualTaxThreshold'
                            },
                            form: {
                                field: 'specialIndividualTaxThreshold',
                                type: 'number',
                                placeholder: '请输入',
                                spanForm: 12,
                                formItemLayoutForm: {
                                    labelCol: {
                                        xs: { span: 24 },
                                        sm: { span: 8 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 24 },
                                        sm: { span: 16 }
                                    }
                                }
                            },
                        },
                        {
                            table: {
                                title: '启用状态',
                                dataIndex: 'enableStatus',
                                key: 'enableStatus',
                                type: 'select',
                                tdEdit: true,
                                tdEditCb: (obj) => {
                                    console.log(obj);
                                    let ovj = {
                                        taxRateTableId: obj.newRowData.taxRateTableId,
                                        enableStatus: obj.newRowData.enableStatus
                                    }
                                    this.props.myFetch('updateZjXmSalaryTaxRateTableStatus', ovj).then(
                                        ({ data, success, message }) => {
                                            if (success) {
                                                Msg.success(message);
                                                obj.qnnTableInstance.setEditedRowData({
                                                    enableStatus: obj.newRowData.enableStatus
                                                })
                                            } else {
                                                Msg.error(message);
                                                obj.qnnTableInstance.setEditedRowData({
                                                    enableStatus: obj.oldRowData.enableStatus
                                                })
                                            }
                                            this.table.refresh();
                                        }
                                    );
                                },
                            },
                            form: {
                                field: 'enableStatus',
                                type: 'select',
                                addShow: true,
                                detailShow: false,
                                allowClear: false,
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
                            isInTable: false,
                            form: {
                                type: 'qnnTable',
                                field: 'taxRateSubList',
                                incToForm: true,
                                qnnTableConfig: {
                                    antd: {
                                        rowKey: 'zjXmSalaryTaxRateSubId',
                                        size: 'small'
                                    },
                                    ...configItem,
                                    formConfig: [
                                        {
                                            isInTable: false,
                                            form: {
                                                label: '主键id',
                                                field: 'zjXmSalaryTaxRateSubId',
                                                hide: true
                                            }
                                        },
                                        {
                                            table: {
                                                title: '级距',
                                                dataIndex: 'intervals',
                                                key: 'intervals',
                                                tdEdit: true
                                            },
                                            form: {
                                                type: 'number',
                                                field: 'intervals',
                                                required: true
                                            }
                                        },
                                        {
                                            table: {
                                                title: '纳税级距下限',
                                                dataIndex: 'lowerTaxBracket',
                                                key: 'lowerTaxBracket',
                                                tdEdit: true
                                            },
                                            form: {
                                                type: 'number',
                                                field: 'lowerTaxBracket',
                                                required: true
                                            }
                                        },
                                        {
                                            table: {
                                                title: '纳税级距上限',
                                                dataIndex: 'maxTaxBracket',
                                                key: 'maxTaxBracket',
                                                tdEdit: true
                                            },
                                            form: {
                                                type: 'number',
                                                field: 'maxTaxBracket',
                                                required: true
                                            }
                                        },
                                        {
                                            table: {
                                                title: '税率（%）',
                                                dataIndex: 'taxRate',
                                                key: 'taxRate',
                                                tdEdit: true
                                            },
                                            form: {
                                                type: 'number',
                                                field: 'taxRate',
                                                required: true
                                            }
                                        },
                                        {
                                            table: {
                                                title: '速算扣除数',
                                                dataIndex: 'quickDeduction',
                                                key: 'quickDeduction',
                                                tdEdit: true
                                            },
                                            form: {
                                                type: 'number',
                                                field: 'quickDeduction',
                                                required: true
                                            }
                                        },
                                    ],
                                    actionBtns: [
                                        {
                                            name: "addRow",
                                            icon: "plus",
                                            type: "primary",
                                            label: "新增",
                                        },
                                        {
                                            name: "del",
                                            icon: 'delete',
                                            type: 'danger',
                                            label: "删除"
                                        }
                                    ]
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
                                                    //修改接口
                                                    apiName: "updateZjXmSalaryTaxRateTable"
                                                }
                                            }
                                        ]
                                    }
                                ]
                            }
                        }
                    ]}
                    method={{
                        willExecuteFunEdit: async (obj) => {
                            const { data, success, message } = await this.props.myFetch('checkZjXmSalaryTaxRateTableDeleteUpdate', { taxRateTableId: obj.rowData.taxRateTableId });
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
                                        apiName: 'addZjXmSalaryTaxRateTable'
                                    }
                                }
                            ]
                        },
                        {
                            field: 'del',
                            name: 'del',
                            icon: 'delete',
                            fetchConfig: {
                                apiName: 'deleteZjXmSalaryTaxRateTable',
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