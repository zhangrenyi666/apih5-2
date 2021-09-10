import React, { Component } from "react";
import QnnTable from "../modules/qnn-table";
const config = {
    antd: {
        rowKey: function (row) {
            return row.setId
        },
        size: 'small'
    },
    drawerConfig: {
        width: '1000px'
    },
    paginationConfig: {
        position: 'bottom'
    },

    formItemLayout: {
        labelCol: {
            xs: { span: 24 },
            sm: { span: 5 }
        },
        wrapperCol: {
            xs: { span: 24 },
            sm: { span: 19 }
        }
    }
};
class index extends Component {
    render() {
        return (
            <div>
                <QnnTable
                    {...this.props}
                    fetch={this.props.myFetch}
                    upload={this.props.myUpload}
                    headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                    {...config}
                    fetchConfig={{
                        apiName: 'getZjConsumableSetList'
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
                                    label: '提交',//提交数据并且关闭右边抽屉 
                                    fetchConfig: {//ajax配置
                                        apiName: 'addZjConsumableSet',
                                    }
                                }
                            ]
                        },
                        {
                            name: 'edit',//内置add del
                            icon: 'plus',//icon
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
                                    label: '提交',//提交数据并且关闭右边抽屉 
                                    fetchConfig: {//ajax配置
                                        apiName: 'updateZjConsumableSet',
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
                                apiName: 'batchDeleteUpdateZjConsumableSet',
                            },
                        }
                    ]}
                    searchFormColNum={3}
                    // formItemLayoutSearch={{
                    //     labelCol: {
                    //         xs: { span: 24 },
                    //         sm: { span: 12 }
                    //     },
                    //     wrapperCol: {
                    //         xs: { span: 24 },
                    //         sm: { span: 12 }
                    //     }
                    // }}
                    formConfig={[
                        {
                            isInTable: false,
                            form: {
                                field: 'setId',
                                type: 'string',
                                hide: true
                            }
                        },
                        {
                            isInSearch: true,
                            table: {
                                title: '类别',
                                dataIndex: 'category',
                                key: 'category'
                            },
                            form: {
                                type: 'string',
                                spanSearch: 8,
                                placeholder: '请输入',
                                spanForm: 12,
                                required: true,
                                editDisabled: true
                            }
                        },
                        {
                            isInSearch: true,
                            table: {
                                title: '品牌',
                                dataIndex: 'brand',
                                key: 'brand'
                            },
                            form: {
                                type: 'string',
                                spanSearch: 8,
                                placeholder: '请输入',
                                spanForm: 12,
                                required: true,
                                editDisabled: true
                            }
                        },
                        {
                            isInSearch: true,
                            table: {
                                title: '型号',
                                dataIndex: 'model',
                                key: 'model'
                            },
                            form: {
                                type: 'string',
                                spanSearch: 8,
                                placeholder: '请输入',
                                spanForm: 12,
                                required: true,
                                editDisabled: true
                            }
                        },
                        {
                            isInSearch: true,
                            table: {
                                title: '颜色',
                                dataIndex: 'colour',
                                key: 'colour'
                            },
                            form: {
                                type: 'string',
                                spanSearch: 8,
                                placeholder: '请输入',
                                spanForm: 12,
                                required: true,
                                editDisabled: true
                            }
                        },
                        {
                            isInSearch: true,
                            table: {
                                title: '使用状态',
                                dataIndex: 'useState',
                                key: 'useState',
                                render: (val) => {
                                    return val ? (val === '0' ? '使用中' : '停用') : ''
                                }
                            },
                            form: {
                                type: 'select',
                                placeholder: '请输入',
                                spanSearch: 8,
                                spanForm: 12,
                                required: true,
                                optionConfig: {
                                    label: 'label',
                                    value: 'value'
                                },
                                optionData: [
                                    {
                                        value: '0',
                                        label: '使用中'
                                    },
                                    {
                                        value: '1',
                                        label: '停用'
                                    }
                                ]
                            }
                        }
                    ]}
                />
            </div>
        );
    }
}

export default index;