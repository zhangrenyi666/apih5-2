import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
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
        const curCompany = this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany;
        const lockProject = this.props.loginAndLogoutInfo.loginInfo.userInfo?.lockProject;
        this.state = {
            departmentId:((curCompany?.ext1 === '1' || curCompany?.ext1 === '2') && lockProject?.projectId) ? (lockProject.projectId !== 'all' ? lockProject.projectId : curCompany?.companyId) : curCompany?.projectId
        }
    }
    render() {
        const { departmentId } = this.state;
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
                    actionBtns={{
                        apiName: "getSysMenuBtn",
                        otherParams: (obj) => {
                            let props = obj.props;
                            let curRouteData = props.routerInfo.routeData[props.routerInfo.curKey];
                            return {
                                menuParentId: curRouteData._value,
                                tableField: "MechanicalEquipmentPersonnel"
                            }
                        }
                    }}
                    method={{
                        editDisabled: (obj) => {
                            let selectedData = obj.btnCallbackFn.getSelectedRows();
                            if (selectedData.length === 1) {
                                return false;
                            } else {
                                return true;
                            }
                        },
                    }}
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
                                addShow:false,
                                editShow:false,
                                detailShow:false,
                                placeholder: '请输入'
                            }
                        },
                        {
                            table: {
                                title: '项目名称',
                                dataIndex: 'projectName',
                                key: 'projectName',
                                filter: true,
                                fieldsConfig: {
                                    type: 'select',
                                    field: 'projectID',
                                    optionConfig: {
                                        label: 'departmentName',
                                        value: 'departmentId'
                                    },
                                    fetchConfig: {
                                        apiName: 'getSysProjectBySelect',
                                        otherParams: {
                                            departmentId: departmentId
                                        }
                                    },
                                }
                            },
                            form: {
                                field:'projectID',
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
                                                        value: "01"
                                                    },
                                                    {
                                                        label: "初级",
                                                        value: "02"
                                                    },
                                                    {
                                                        label: "中级",
                                                        value: "03"
                                                    },
                                                    {
                                                        label: "高级",
                                                        value: "04"
                                                    }
                                                ],
                                                allowClear: false,
                                                showSearch: true,
                                                placeholder: '请选择',
                                            },
                                        },
                                        {
                                            isInTable: false,
                                            form: {
                                                label: '岗位id',
                                                field: 'jobID',
                                                hide: true
                                            }
                                        },
                                        {
                                            table: {
                                                title: "<div>所在岗位<span style='color: #ff4d4f'>*</span></div>",
                                                dataIndex: 'pos',
                                                key: 'pos',
                                                tdEdit: true
                                            },
                                            form: {
                                                type: 'select',
                                                optionConfig: {
                                                    label: 'jobName',
                                                    value: 'jobName'
                                                },
                                                fetchConfig:{
                                                   apiName:'getZxEqMachineJobsList',
                                                   otherParams:{
                                                       startFlag:'1'
                                                   }
                                                },
                                                onChange: (val, obj, btnCallbackFn) => {
                                                    btnCallbackFn.getEditedRowData(false).then((editRowData) => {
                                                        editRowData.jobID = obj.itemData.id;
                                                        editRowData.pos = obj.itemData.jobName;
                                                        btnCallbackFn.setEditedRowData({ ...editRowData });
                                                    });
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