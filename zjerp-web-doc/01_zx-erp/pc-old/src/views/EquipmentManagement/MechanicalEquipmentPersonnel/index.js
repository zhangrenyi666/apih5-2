import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
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
            xs: { span: 6 },
            sm: { span: 6 }
        },
        wrapperCol: {
            xs: { span: 18 },
            sm: { span: 18 }
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
                        apiName: 'getZxEqEProjectEmployeeList',
                        otherParams:{
                            orgID:departmentId
                        }
                    }}
                    actionBtns={[
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
                                        apiName: 'addZxEqEProjectEmployee',
                                    }
                                }
                            ]
                        },
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
                                        apiName: 'updateZxEqEProjectEmployee',
                                    },
                                    onClick:(obj) => {
                                        obj.btnCallbackFn.clearSelectedRows();
                                    }
                                }
                            ]
                        },
                        {
                            name: 'del',//内置add del
                            icon: 'delete',//icon
                            type: 'danger',//类型  默认 primary  [primary dashed danger]
                            label: '删除',
                            fetchConfig: {//ajax配置
                                apiName: 'batchDeleteUpdateZxEqEProjectEmployee',
                            },
                        }
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
                                field: 'comID',
                                type: 'string',
                                hide: true,
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                field: 'orgName',
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
                                field: 'projectName',
                                type: 'string',
                                hide: true,
                            }
                        },
                        {
                            table: {
                                title: '公司名称',
                                dataIndex: 'comName',
                                key: 'comName',
                                onClick:'detail',
                                filter: true,
                            },
                            form: {
                                type: 'string',
                                hide:true,
                                placeholder: '请输入'
                            }
                        },
                        {
                            table: {
                                title: '项目名称',
                                dataIndex: 'projectID',
                                key: 'projectID',
                                filter: true,
                                type:'select'
                            },
                            form: {
                                type: 'select',
                                optionConfig: {
                                    label: 'departmentName',
                                    value: 'departmentId',
                                    linkageFields: {
                                        projectName: 'departmentName',
                                        orgID:'departmentId',
                                        orgName:'departmentName',
                                        comID:'companyId',
                                        comName:'companyName'
                                    }
                                },
                                fetchConfig: {
                                    apiName: 'getSysProjectBySelect',
                                    otherParams:{
                                        departmentId:departmentId
                                    }
                                },
                                allowClear: false,
                                showSearch: true,
                                required:true,
                                editDisabled: true,
                                placeholder: '请输入',
                                spanForm:12
                            }
                        },
                        {
                            table: {
                                title: '编制时间',
                                dataIndex: 'bzDate',
                                key: 'bzDate',
                                format:'YYYY-MM-DD'
                            },
                            form: {
                                type: 'date',
                                required:true,
                                initialValue:new Date(),
                                placeholder: '请选择',
                                spanForm:12
                            }
                        },
                        {
                            table: {
                                title: '备注',
                                dataIndex: 'remark',
                                key: 'remark'
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
                                type: 'qnnTable',
                                field: 'eemployeeList',
                                incToForm: true,
                                qnnTableConfig: {
                                    actionBtnsPosition: "top",
                                    antd: {
                                        rowKey: 'id',
                                        size: 'small'
                                    },
                                    paginationConfig: {
                                        position: 'bottom'
                                    },
                                    wrappedComponentRef: (me) => {
                                        this.tableOne = me;
                                    },
                                    tableTdEdit: true,
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
                                            table: {
                                                title: '序号',
                                                dataIndex: 'index',
                                                key: 'index',
                                                width: 50,
                                                fixed: 'left',
                                                render: (data, rowData, index) => {
                                                    return index + 1;
                                                }
                                            },
                                            isInForm: false
                                        },
                                        {
                                            table: {
                                                title: "<div>姓名<span style='color: #ff4d4f'>*</span></div>",
                                                dataIndex: 'name',
                                                key: 'name',
                                                tdEdit: true
                                            },
                                            form: {
                                                type: 'string',
                                                required:true,
                                                placeholder: '请输入',
                                            },
                                        },
                                        {
                                            table: {
                                                title: '年龄',
                                                dataIndex: 'age',
                                                key: 'age',
                                                tdEdit: true
                                            },
                                            form: {
                                                type: 'string',
                                                placeholder: '请输入',
                                            },
                                        },
                                        {
                                            table: {
                                                title: '文化程度',
                                                dataIndex: 'eduLevel',
                                                key: 'eduLevel',
                                                type:'select',
                                                tdEdit: true
                                            },
                                            form: {
                                                type: 'select',
                                                optionConfig: {
                                                    label: 'label',
                                                    value: 'value',
                                                },
                                                optionData: [
                                                    {
                                                        label: "博士",
                                                        value: "0"
                                                    },
                                                    {
                                                        label: "硕士",
                                                        value: "1"
                                                    },
                                                    {
                                                        label: "本科",
                                                        value: "2"
                                                    },
                                                    {
                                                        label: "大专",
                                                        value: "3"
                                                    },
                                                    {
                                                        label: "中专",
                                                        value: "4"
                                                    },
                                                    {
                                                        label: "高中",
                                                        value: "5"
                                                    },
                                                    {
                                                        label: "初中",
                                                        value: "6"
                                                    },
                                                    {
                                                        label: "小学",
                                                        value: "7"
                                                    }
                                                ],
                                                allowClear: false,
                                                showSearch: true,
                                                placeholder: '请选择',
                                            },
                                        },
                                        {
                                            table: {
                                                title: '职称',
                                                dataIndex: 'title',
                                                key: 'title',
                                                type:'select',
                                                tdEdit: true
                                            },
                                            form: {
                                                type: 'select',
                                                optionConfig: {
                                                    label: 'label',
                                                    value: 'value',
                                                },
                                                optionData: [
                                                    {
                                                        label: "无",
                                                        value: "0"
                                                    },
                                                    {
                                                        label: "初级",
                                                        value: "1"
                                                    },
                                                    {
                                                        label: "中级",
                                                        value: "2"
                                                    },
                                                    {
                                                        label: "高级",
                                                        value: "3"
                                                    }
                                                ],
                                                allowClear: false,
                                                showSearch: true,
                                                placeholder: '请选择',
                                            },
                                        },
                                        {
                                            table: {
                                                title: "<div>所在岗位<span style='color: #ff4d4f'>*</span></div>",
                                                dataIndex: 'jobID',
                                                key: 'jobID',
                                                tdEdit: true,
                                                type:'select'
                                            },
                                            form: {
                                                type: 'select',
                                                optionConfig: {
                                                    label: 'jobName',
                                                    value: 'id',
                                                },
                                                fetchConfig:{
                                                   apiName:'getZxEqMachineJobsList',
                                                   otherParams:{
                                                       startFlag:'1'
                                                   }
                                                },
                                                allowClear: false,
                                                showSearch: true,
                                                required:true,
                                                placeholder: '请选择',
                                            },
                                        },
                                        {
                                            table: {
                                                title: '本岗工龄',
                                                dataIndex: 'posAge',
                                                key: 'posAge',
                                                tdEdit: true
                                            },
                                            form: {
                                                type: 'string',
                                                placeholder: '请输入',
                                            },
                                        }
                                    ],
                                    actionBtns: [
                                        {
                                            name: "addRow",
                                            icon: "plus",
                                            type: "primary",
                                            label: "新增行"
                                        },
                                        {
                                            name: 'del',
                                            icon: 'delete',
                                            type: 'danger',
                                            label: '删除'
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