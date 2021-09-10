import React, { Component } from "react";
import { flush } from "redux-saga/effects";
import QnnTable from "../modules/qnn-table";
import { message as Msg, Modal, Button, Tabs } from "antd";

import PersonInfo from './comp/PersonInfo'

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
            modalShowStatus: false
        }
    }
    render() {
        const { departmentName } = this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany;
        const { realName } = this.props.loginAndLogoutInfo.loginInfo.userInfo;
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
                        apiName: ''
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
                                        apiName: '',
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
                                        apiName: '',
                                    },
                                    onClick: (obj) => {
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
                                apiName: '',
                            }
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
                            table: {
                                title: '标题',
                                dataIndex: '1',
                                key: '1',
                                filter: true,
                                width: 200
                            },
                            form: {
                                type: 'string',
                                required: true,
                                placeholder: '请选择'
                            }
                        },
                        {
                            table: {
                                title: '申报时间',
                                dataIndex: 'createTime',
                                key: 'createTime',
                                width: 100,
                                format: 'YYYY-MM-DD'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '单号',
                                dataIndex: '2',
                                key: '2',
                                width: 100,
                                format: 'YYYY-MM-DD'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '报审单位',
                                dataIndex: '3',
                                key: '3',
                                width: 100
                            },
                            form: {
                                type: 'string',
                                addDisabled: true,
                                editDisabled: true,
                                initialValue: departmentName,
                                spanForm: 12,
                                formItemLayout: {
                                    labelCol: {
                                        xs: { span: 6 },
                                        sm: { span: 6 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 18 },
                                        sm: { span: 18 }
                                    }
                                },
                                placeholder: '请输入'
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: '报审人',
                                field: '4',
                                type: 'string',
                                addDisabled: true,
                                editDisabled: true,
                                initialValue: realName,
                                spanForm: 12,
                                formItemLayout: {
                                    labelCol: {
                                        xs: { span: 6 },
                                        sm: { span: 6 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 18 },
                                        sm: { span: 18 }
                                    }
                                },
                                placeholder: '请输入'
                            }
                        },
                        {
                            table: {
                                title: '当前审批位置',
                                dataIndex: '5',
                                key: '5',
                                width: 120
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '审批状态',
                                dataIndex: '6',
                                key: '6',
                                width: 100
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '审批时间',
                                dataIndex: '7',
                                key: '7',
                                width: 100,
                                format: 'YYYY-MM-DD'
                            },
                            isInForm: false
                        },
                        {
                            isInTable: false,
                            form: {
                                label: "附件",
                                field: "fileList",
                                type: "files",
                                initialValue: [],
                                fetchConfig: {
                                    apiName: window.configs.domain + "upload"
                                }
                            }
                        },
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
                                    paginationConfig: {
                                        position: 'bottom'
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
                                                title: '姓名',
                                                dataIndex: '1',
                                                key: '1',
                                                width: 100,
                                                tdEdit: true
                                            },
                                            form: {
                                                type: 'string',
                                                placeholder: '请输入',
                                            },
                                        },
                                        {
                                            table: {
                                                title: '性别',
                                                dataIndex: '2',
                                                key: '2',
                                                width: 100,
                                                tdEdit: true,
                                                type: 'select'
                                            },
                                            form: {
                                                type: 'select',
                                                optionConfig: {
                                                    label: 'label',
                                                    value: 'value'
                                                },
                                                optionData: [
                                                    {
                                                        label: '男',
                                                        value: '0'
                                                    },
                                                    {
                                                        label: '女',
                                                        value: '1'
                                                    }
                                                ],
                                                placeholder: '请选择',
                                            },
                                        },
                                        {
                                            table: {
                                                title: '身份证号',
                                                dataIndex: '3',
                                                key: '3',
                                                width: 150,
                                                tdEdit: true
                                            },
                                            form: {
                                                type: 'identity',
                                                placeholder: '请输入',
                                            },
                                        },
                                        {
                                            table: {
                                                title: '岗位',
                                                dataIndex: '4',
                                                key: '4',
                                                width: 100,
                                                tdEdit: true
                                            },
                                            form: {
                                                type: 'string',
                                                placeholder: '请输入',
                                            },
                                        },
                                        {
                                            table: {
                                                title: '岗位等级',
                                                dataIndex: '5',
                                                key: '5',
                                                width: 100,
                                                tdEdit: true
                                            },
                                            form: {
                                                type: 'string',
                                                placeholder: '请输入',
                                            },
                                        },
                                        {
                                            table: {
                                                title: '岗薪',
                                                dataIndex: '6',
                                                key: '6',
                                                width: 100,
                                                tdEdit: true
                                            },
                                            form: {
                                                type: 'number',
                                                placeholder: '请输入',
                                            },
                                        },
                                        {
                                            isInForm: false,
                                            table: {
                                                showType: "tile", //出来的样式 bubble（气泡）  tile（平铺） 默认bubble  （0.6.15版本中将该属性移动到table属性下，也可写到table同级）
                                                width: 110,
                                                title: "操作",
                                                key: "action", //操作列名称
                                                fixed: "right", //固定到右边
                                                align: "center",
                                                btns: [
                                                    {
                                                        name: "diyEdit", // 内置name有【add,  del, edit, detail, Component, form】
                                                        label: "修改",
                                                        onClick: (obj) => {
                                                            this.setState({
                                                                modalShowStatus: 'edit'
                                                            })
                                                        },
                                                    },
                                                ]
                                            }
                                        }
                                    ],
                                    actionBtns: [
                                        {
                                            name: "diyAdd",
                                            icon: "plus",
                                            type: "primary",
                                            label: "新增行",
                                            onClick: () => {
                                                this.setState({
                                                    modalShowStatus: 'add'
                                                })
                                            }
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
                        }
                    ]}
                />
                <PersonInfo
                    propsData={this.props}
                    modalShowStatus={this.state.modalShowStatus}
                    closeCb={(val) => {
                        this.setState({
                            modalShowStatus: val
                        })
                    }}
                    tabsDataFunc={(data) => {
                        console.log(data)
                    }}
                />
            </div>
        );
    }
}

export default index;