import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import moment from 'moment';
import { push } from "react-router-redux";
import { message as Msg } from 'antd';
import Apih5 from "qnn-apih5"
const config = {
    antd: {
        rowKey: 'salaryItemDictionaryId',
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
class index extends Apih5 {
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
                        apiName: 'getZjXmSalarySalaryItemDictionaryList',
                        otherParams: {

                        }
                    }}
                    rowSelection={{
                        type: 'radio',
                        // selectedRowsStyle:{backgroundColor:'#ccc'}
                    }}
                    method={{}}
                    formConfig={[
                        {
                            isInTable: false,
                            form: {
                                field: 'salaryItemDictionaryId',
                                type: 'string',
                                hide: true,
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                field: 'payTypeName',
                                type: 'string',
                                hide: true,
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                field: 'calculateTypeName',
                                type: 'string',
                                hide: true,
                            }
                        },
                        {
                            table: {
                                title: '薪酬分类',
                                width: 120,
                                dataIndex: 'payTypeId',
                                key: 'payTypeId',
                                type: 'select',
                            },
                            form: {
                                type: 'select',
                                required: true,
                                field: 'payTypeId',
                                fetchConfig: {
                                    apiName: 'getBaseCodeSelect',
                                    otherParams: {
                                        itemId: 'xinChouFenLei'
                                    }
                                },
                                optionConfig: {
                                    label: 'itemName',
                                    value: 'itemId',
                                    linkageFields: {
                                        payTypeName: 'itemName'
                                    }
                                },
                                onChange: (val, rowData) => {
                                    if (val) {
                                        rowData.fns.setValues({
                                            payName: null,
                                            payCode: null
                                        });
                                    }
                                }
                            }
                        },
                        {
                            table: {
                                title: '薪酬名称',
                                onClick: 'detail',
                                dataIndex: 'payName',
                                key: 'payName'
                            },
                            form: {
                                field: 'payName',
                                required: true,
                                type: 'string',
                                placeholder: '请输入...',
                                condition: [
                                    {
                                        regex: {
                                            payTypeId: '7',
                                        },
                                        action: ['hide'],
                                    }
                                ]
                            },
                        },
                        {
                            isInTable: false,
                            form: {
                                label: '薪酬名称',
                                field: 'payCode',
                                required: true,
                                placeholder: '请选择...',
                                type: 'select',
                                fetchConfig: {
                                    apiName: 'getBaseCodeSelect',
                                    otherParams: {
                                        itemId: 'personal'
                                    }
                                },
                                optionConfig: {
                                    label: 'itemName',
                                    value: 'itemId',
                                    linkageFields: {
                                        payName: 'itemName'
                                    }
                                },
                                condition: [
                                    {
                                        regex: {
                                            payTypeId: ['!', '7'],
                                        },
                                        action: ['hide'],
                                    }
                                ]
                            },
                        },
                        {
                            table: {
                                title: '计算类型',
                                dataIndex: 'calculateTypeId',
                                key: 'calculateTypeId',
                                type: 'select',
                            },
                            form: {
                                type: 'select',
                                required: true,
                                field: 'calculateTypeId',
                                fetchConfig: {
                                    apiName: 'getBaseCodeSelect',
                                    otherParams: {
                                        itemId: 'faxqJiSuanLeiXing'
                                    }
                                },
                                optionConfig: {
                                    label: 'itemName',
                                    value: 'itemId',
                                    linkageFields: {
                                        calculateTypeName: 'itemName'
                                    }
                                }
                            }
                        },
                        {
                            table: {
                                title: '纳入薪酬总额',
                                dataIndex: 'includedTotalFlag',
                                key: 'includedTotalFlag',
                                type: 'select',
                            },
                            form: {
                                type: 'select',
                                required: true,
                                field: 'includedTotalFlag',
                                optionData: [
                                    {
                                        label: '否',
                                        value: '0'
                                    },
                                    {
                                        label: '是',
                                        value: '1'
                                    }
                                ],
                                optionConfig: {
                                    label: 'label',
                                    value: 'value'
                                }
                            }
                        },
                        {
                            table: {
                                title: '允许补发',
                                dataIndex: 'reissueAllowedFlag',
                                key: 'reissueAllowedFlag',
                                type: 'select',
                            },
                            form: {
                                type: 'select',
                                required: true,
                                field: 'reissueAllowedFlag',
                                optionData: [
                                    {
                                        label: '否',
                                        value: '0'
                                    },
                                    {
                                        label: '是',
                                        value: '1'
                                    }
                                ],
                                optionConfig: {
                                    label: 'label',
                                    value: 'value'
                                }
                            }
                        },

                        {
                            table: {
                                title: '系统预置薪酬项',
                                dataIndex: 'salaryItemsFlag',
                                key: 'salaryItemsFlag',
                                type: 'select',
                            },
                            form: {
                                type: 'select',
                                required: true,
                                field: 'salaryItemsFlag',
                                optionData: [
                                    {
                                        label: '否',
                                        value: '0'
                                    },
                                    {
                                        label: '是',
                                        value: '1'
                                    }
                                ],
                                optionConfig: {
                                    label: 'label',
                                    value: 'value'
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
                                        salaryItemDictionaryId: obj.newRowData.salaryItemDictionaryId,
                                        status: obj.newRowData.status
                                    }
                                    this.props.myFetch('updateZjXmSalarySalaryItemDictionaryStatus', ovj).then(
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
                                title: '排序',
                                dataIndex: 'sort',
                                key: 'sort'
                            },
                            form: {
                                type: 'number',
                                field: 'sort'
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
                                                    apiName: "updateZjXmSalarySalaryItemDictionary"
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
                            const { data, success, message } = await this.props.myFetch('checkZjXmSalarySalaryItemDictionaryDeleteUpdate', { salaryItemDictionaryId: obj.rowData.salaryItemDictionaryId });
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
                                        apiName: 'addZjXmSalarySalaryItemDictionary'
                                    }
                                }
                            ]
                        },
                        {
                            field: 'delBtn',
                            name: 'del',
                            icon: 'delete',
                            fetchConfig: {
                                apiName: 'batchDeleteUpdateZjXmSalarySalaryItemDictionary',
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