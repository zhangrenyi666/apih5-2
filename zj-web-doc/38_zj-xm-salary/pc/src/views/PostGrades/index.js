import React, { Component } from "react";
import QnnTable from "../modules/qnn-table";
import moment from 'moment';
const config = {
    antd: {
        rowKey: 'baseJobLevelId',
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
            xs: { span: 3 },
            sm: { span: 3 }
        },
        wrapperCol: {
            xs: { span: 21 },
            sm: { span: 21 }
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
                        apiName: 'getBaseJobLevelList'
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
                                    fetchConfig: {
                                        apiName: 'addBaseJobLevel',
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
                                        apiName: 'updateBaseJobLevel',
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
                                apiName: 'batchDeleteUpdateBaseJobLevel',
                            }
                        }
                    ]}
                    formConfig={[
                        {
                            isInTable: false,
                            form: {
                                field: 'baseJobLevelId',
                                type: 'string',
                                hide: true,
                            }
                        },
                        {
                            table: {
                                title: '机构类型',
                                dataIndex: 'companyId',
                                key: 'companyId',
                                filter: true,
                                width: 100,
                                type: 'select'
                            },
                            form: {
                                type: 'select',
                                fetchConfig: {
                                    apiName: "getBaseCodeSelect",
                                    otherParams: {
                                        itemId: "jiGouLeiXing"
                                    }
                                },
                                optionConfig: {
                                    label: "itemName",
                                    value: "itemId"
                                },
                                required:true,
                                placeholder: '请选择'
                            }
                        },
                        {
                            table: {
                                title: '岗位类型',
                                dataIndex: 'payLevelType',
                                key: 'payLevelType',
                                width: 100,
                                type: 'select'
                            },
                            form: {
                                type: 'select',
                                fetchConfig: {
                                    apiName: "getBaseCodeSelect",
                                    otherParams: {
                                        itemId: "xiangMuXinChouLeiXing"
                                    }
                                },
                                optionConfig: {
                                    label: "itemName",
                                    value: "itemId"
                                },
                                required:true,
                                placeholder: '请选择'
                            }
                        },
                        {
                            table: {
                                title: '岗位级别',
                                dataIndex: 'jobLevelName',
                                key: 'jobLevelName',
                                width: 100
                            },
                            form: {
                                type: 'string',
                                required:true,
                                placeholder: '请输入'
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                type: 'qnnTable',
                                field: 'baseJobLevelSubList',
                                incToForm: true,
                                qnnTableConfig: {
                                    actionBtnsPosition: "top",
                                    antd: {
                                        rowKey: 'baseJobLevelSubId',
                                        size: 'small'
                                    },
                                    paginationConfig:false,
                                    tableTdEdit: true,
                                    formConfig: [
                                        {
                                            isInTable: false,
                                            form: {
                                                label: '主键id',
                                                field: 'baseJobLevelSubId',
                                                hide: true
                                            }
                                        },
                                        {
                                            table: {
                                                title: 'No.',
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
                                                title: '档位',
                                                dataIndex: 'gear',
                                                key: 'gear',
                                                width: 400,
                                                tdEdit:true
                                            },
                                            form: {
                                                type: 'string',
                                                placeholder: '请输入',
                                            },
                                        },
                                        {
                                            table: {
                                                title: '岗薪(元/月)',
                                                dataIndex: 'salary',
                                                key: 'salary',
                                                width: 400,
                                                tdEdit:true
                                            },
                                            form: {
                                                type: 'number',
                                                placeholder: '请输入',
                                            },
                                        }
                                    ],
                                    actionBtns: [
                                        {
                                            name: "addRow",
                                            icon: "plus",
                                            type: "primary",
                                            label: "新增"
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
                        {
                            table: {
                                title: '备注',
                                dataIndex: 'remarks',
                                key: 'remarks',
                                width: 150
                            },
                            form: {
                                type: 'textarea',
                                placeholder: '请输入'
                            }
                        },
                        {
                            table: {
                                title: '修改者',
                                dataIndex: 'modifyUserName',
                                key: 'modifyUserName',
                                width: 100
                            },
                            isInForm:false
                        },
                        {
                            table: {
                                title: '修改时间',
                                dataIndex: 'modifyTime',
                                key: 'modifyTime',
                                width: 100,
                                onClick: 'detail',
                                render: (data) => {
                                    return data ? moment(data).format('YYYY-MM-DD') : null
                                }
                            },
                            isInForm:false
                        }
                    ]}
                />
            </div>
        );
    }
}

export default index;