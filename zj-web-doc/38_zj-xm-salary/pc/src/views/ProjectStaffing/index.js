import React, { Component } from "react";
import QnnTable from "../modules/qnn-table";
import moment from 'moment';
import { Modal } from 'antd';
const confirm = Modal.confirm;
const config = {
    antd: {
        rowKey: 'baseProjectConfId',
        size: 'small'
    },
    drawerConfig: {
        width: '1200px'
    },
    paginationConfig: {
        position: 'bottom'
    },
    firstRowIsSearch: false,
    isShowRowSelect: true
};
const configItem = {
    drawerConfig: {
        width: '1100px'
    },
    paginationConfig: false,
    actionBtnsPosition: "top",
    firstRowIsSearch: false,
    isShowRowSelect: true
};
class index extends Component {
    constructor(props) {
        super(props);
        this.state = {
            id: 0
        }
    }
    componentDidMount() { }
    setTotalAmtFun(rowData) {//计算表格内的总值，显示在表单上
        let djje = 0;

        let tableData = this.table.qnnForm.form.getFieldsValue();
        tableData.baseProjectConfJobList.map((item) => {
            if (item.baseProjectJobConfId === rowData.baseProjectJobConfId) {
                djje += Number(rowData.numMax);
            } else {
                djje += Number(item.numMax);
            }
            return item;
        });

        if (this.table && this.table.qnnForm) {

            this.table.qnnForm.form.setFieldsValue({
                departmentNum: Number(djje)
            });
        }

    }
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
                        apiName: 'getBaseProjectConfList'
                    }}
                    formConfig={[
                        {
                            isInTable: false,
                            form: {
                                field: 'baseProjectConfId',
                                type: 'string',
                                hide: true,
                            }
                        },
                        {
                            table: {
                                title: '工程类型',
                                width: 200,
                                dataIndex: 'projType',
                                key: 'projType',
                                type: 'select'
                            },
                            form: {
                                field: 'projType',
                                type: 'select',
                                fetchConfig: {
                                    apiName: "getBaseCodeTree",
                                    otherParams: {
                                        itemId: "xiangMuBuMen"
                                    }
                                },
                                optionConfig: {
                                    label: "itemName",
                                    value: "itemId",
                                    children: 'children'
                                },
                                required: true,
                                editDisabled: true,
                                placeholder: '请选择',
                                spanForm: 12,
                                formItemLayoutForm: {
                                    labelCol: {
                                        xs: { span: 24 },
                                        sm: { span: 6 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 24 },
                                        sm: { span: 18 }
                                    }
                                }
                            },
                        },
                        {
                            table: {
                                title: '部门名称',
                                width: 200,
                                dataIndex: 'departmentName',
                                key: 'departmentName'
                            },
                            form: {
                                field: 'departmentId',
                                required: true,
                                editDisabled: true,
                                parent: 'projType',
                                type: 'select',
                                optionConfig: {
                                    label: "itemName",
                                    value: "itemId",
                                    linkageFields: {
                                        'departmentName': 'itemName'
                                    }
                                },
                                placeholder: '请选择',
                                spanForm: 12,
                                formItemLayoutForm: {
                                    labelCol: {
                                        xs: { span: 24 },
                                        sm: { span: 6 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 24 },
                                        sm: { span: 18 }
                                    }
                                }
                            },
                        },
                        {
                            isInTable: false,
                            form: {
                                type: 'string',
                                field: 'departmentName',
                                hide: true
                            }
                        },
                        {
                            table: {
                                title: '项目级别',
                                width: 200,
                                dataIndex: 'proLevel',
                                key: 'proLevel',
                                type: 'select'
                            },
                            form: {
                                field: 'proLevel',
                                required: true,
                                editDisabled: true,
                                type: 'select',
                                fetchConfig: {
                                    apiName: "getBaseCodeSelect",
                                    otherParams: {
                                        itemId: "xiangMuJiBie"
                                    }
                                },
                                optionConfig: {
                                    label: "itemName",
                                    value: "itemId"
                                },
                                placeholder: '请选择',
                                spanForm: 12,
                                formItemLayoutForm: {
                                    labelCol: {
                                        xs: { span: 24 },
                                        sm: { span: 6 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 24 },
                                        sm: { span: 18 }
                                    }
                                }
                            },
                        },
                        {
                            table: {
                                title: '部门人数',
                                width: 200,
                                dataIndex: 'departmentNum',
                                key: 'departmentNum'
                            },
                            form: {
                                field: 'departmentNum',
                                required: true,
                                addDisabled: true,
                                editDisabled: true,
                                type: 'number',
                                spanForm: 12,
                                formItemLayoutForm: {
                                    labelCol: {
                                        xs: { span: 24 },
                                        sm: { span: 6 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 24 },
                                        sm: { span: 18 }
                                    }
                                }
                            },
                        },
                        {
                            isInTable: false,
                            form: {
                                type: 'qnnTable',
                                field: 'baseProjectConfJobList',
                                incToForm: true,
                                qnnTableConfig: {
                                    antd: {
                                        rowKey: 'baseProjectJobConfId',
                                        size: 'small'
                                    },
                                    ...configItem,
                                    wrappedComponentRef: (me) => {
                                        this.tableItem = me;
                                    },
                                    tableTdEdit: true,
                                    formConfig: [
                                        {
                                            isInTable: false,
                                            form: {
                                                label: '主键id',
                                                field: 'baseProjectJobConfId',
                                                hide: true
                                            }
                                        },
                                        {
                                            table: {
                                                title: "岗位",
                                                dataIndex: 'jobType',
                                                width: 200,
                                                key: 'jobType',
                                                type: 'select',
                                                tdEdit: true,
                                            },
                                            form: {
                                                type: 'select',
                                                field: 'jobType',
                                                required: true,
                                                optionConfig: {
                                                    label: 'itemName',
                                                    value: 'itemId'
                                                },
                                                fetchConfig: {
                                                    apiName: "getBaseCodeTreeByLevel",
                                                    otherParams: {
                                                        "itemId": "gangWeiGuanLi",
                                                        "subItemId": "03"
                                                    }
                                                }
                                            }
                                        },
                                        {
                                            table: {
                                                title: "人数类型",
                                                dataIndex: 'numType',
                                                width: 200,
                                                key: 'numType',
                                                type: 'select',
                                                tdEdit: true,
                                            },
                                            form: {
                                                type: 'select',
                                                field: 'numType',
                                                required: true,
                                                optionConfig: {
                                                    label: 'label',
                                                    value: 'value'
                                                },
                                                optionData: [
                                                    {
                                                        label: '个值',
                                                        value: '0'
                                                    },
                                                    {
                                                        label: '区间',
                                                        value: '1'
                                                    }
                                                ]
                                            }
                                        },
                                        {
                                            table: {
                                                title: '人数(最小)',
                                                width: 150,
                                                dataIndex: 'numMin',
                                                key: 'numMin',
                                                tdEdit: true
                                            },
                                            form: {
                                                type: 'number',
                                                field: 'numMin'
                                            }
                                        },
                                        {
                                            table: {
                                                title: '人数(最大)',
                                                width: 150,
                                                dataIndex: 'numMax',
                                                key: 'numMax',
                                                tdEdit: true
                                            },
                                            form: {
                                                type: 'number',
                                                field: 'numMax',
                                                onChange: async (colVal, thisProps, tableBtnCallbackFn) => {
                                                    if (typeof (colVal) === 'number' && colVal >= 0) {
                                                        const rowData = await tableBtnCallbackFn.getEditedRowData(false);
                                                        const newRowData = {
                                                            ...rowData
                                                        }
                                                        this.setTotalAmtFun(newRowData);
                                                        await tableBtnCallbackFn.setEditedRowData({
                                                            ...newRowData
                                                        });
                                                    }
                                                }
                                            }
                                        },
                                        {
                                            table: {
                                                title: '是否兼职',
                                                dataIndex: 'jobFlag',
                                                width: 100,
                                                key: 'jobFlag',
                                                type: 'select',
                                                tdEdit: true
                                            },
                                            form: {
                                                type: 'select',
                                                field: 'jobFlag',
                                                optionConfig: {
                                                    label: 'label',
                                                    value: 'value'
                                                },
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
                                            }
                                        },
                                        {
                                            table: {
                                                title: '备注',
                                                width: 120,
                                                dataIndex: 'remarks',
                                                key: 'remarks',
                                                tdEdit: true,
                                            },
                                            form: {
                                                type: 'textarea',
                                                field: 'remarks',
                                                autoSize: {
                                                    minRows: 1,
                                                    maxRows: 3
                                                }
                                            }
                                        }
                                    ],
                                    actionBtns: [
                                        {
                                            name: "addRow",
                                            icon: "plus",
                                            type: "primary",
                                            label: "新增",
                                        },
                                        {
                                            name: "Diydel",
                                            icon: 'delete',
                                            type: 'danger',
                                            label: "删除",
                                            disabled: "bind:_actionBtnNoSelected",
                                            onClick: (obj) => {
                                                if (this.table && this.table.qnnForm) {
                                                    let allData = this.table.qnnForm.form.getFieldsValue().baseProjectConfJobList;
                                                    let selectDa = obj.selectedRows;
                                                    let selectRowData = [];
                                                    confirm({
                                                        content: '确定删除选中的数据吗?',
                                                        onOk: () => {
                                                            let obj = {};
                                                            selectDa.forEach(function (item, index) {
                                                                obj[item.baseProjectJobConfId] = true
                                                            })
                                                            allData.forEach(function (item, index) {
                                                                if (!obj[item.baseProjectJobConfId]) {
                                                                    selectRowData.push(item)
                                                                } else {
                                                                }
                                                            })

                                                            if (this.table && this.table.qnnForm) {
                                                                this.table.qnnForm.form.setFieldsValue({
                                                                    baseProjectConfJobList: selectRowData
                                                                });
                                                                let djje = 0;
                                                                selectRowData.map((item) => {
                                                                    djje += Number(item.numMax);
                                                                    return item
                                                                })
                                                                this.table.qnnForm.form.setFieldsValue({
                                                                    departmentNum: Number(djje)
                                                                });
                                                                this.tableItem.clearSelectedRows();
                                                            }
                                                        }
                                                    });
                                                }


                                            }
                                        }
                                    ]
                                }
                            }
                        },
                        {
                            table: {
                                title: '修改者',
                                dataIndex: 'modifyUserName',
                                key: 'modifyUserName'
                            },
                            form: {
                                type: 'string',
                                field: 'modifyUserName',
                                initialValue: () => {
                                    return this.props.loginAndLogoutInfo.loginInfo.userInfo.realName
                                },
                                spanForm: 12,
                                formItemLayoutForm: {
                                    labelCol: {
                                        xs: { span: 24 },
                                        sm: { span: 6 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 24 },
                                        sm: { span: 18 }
                                    }
                                }
                            }
                        },
                        {
                            table: {
                                title: '修改时间',
                                width: 120,
                                onClick: 'detail',
                                dataIndex: 'modifyTime',
                                key: 'modifyTime',
                                render: (data) => {
                                    return data ? moment(data).format('YYYY-MM-DD') : null
                                }
                            },
                            form: {
                                type: 'date',
                                required: true,
                                field: 'modifyTime',
                                initialValue: () => {
                                    return moment(new Date()).format('YYYY-MM-DD')
                                },
                                spanForm: 12,
                                formItemLayoutForm: {
                                    labelCol: {
                                        xs: { span: 24 },
                                        sm: { span: 6 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 24 },
                                        sm: { span: 18 }
                                    }
                                }
                            }
                        },
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
                                        apiName: 'addBaseProjectConf'
                                    }
                                }
                            ]
                        },
                        {
                            name: 'edit',
                            icon: 'edit',
                            type: 'primary',
                            label: '修改',
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
                                        apiName: 'updateBaseProjectConf',
                                    },
                                    onClick: (obj) => {
                                        obj.btnCallbackFn.clearSelectedRows();
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
                                apiName: 'batchDeleteUpdateBaseProjectConf',
                            },
                        }
                    ]}

                />
            </div>
        );
    }
}

export default index;