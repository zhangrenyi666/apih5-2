import React, { Component } from 'react';
import QnnTable from '../modules/qnn-table';
const configs = {
    fetchConfig: {//表格的ajax配置
        apiName: 'getZjXmServerProjectEstablishmentList',
    },
    antd: { //同步antd table组件配置 ***必传
        rowKey: function (row) {// ***必传
            return row.projectEstablishmentId
        },
        size: 'small'
    },
    drawerConfig: {
        width: '1000px'
    },
    paginationConfig: {// 同步antd的分页组件配置   
        position: 'bottom'
    },
    isShowRowSelect: true,//是否显示选择框  默认true
    actionBtns: [
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
                        apiName: 'addZjXmServerProjectEstablishment',
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
                apiName: 'batchDeleteUpdateZjXmServerProjectEstablishment',
            },
        }
    ],
    formConfig: [
        {
            isInTable: false,
            form: {
                field: 'projectEstablishmentId',
                type: 'string',
                placeholder: '请输入',
                hide: true
            }
        }, {
            table: {
                title: '时间节点', //表头标题
                dataIndex: 'timeNodeName', //表格里面的字段
                key: 'timeNodeName',//表格的唯一key  
                onClick:'detail'
            },
            form: {
                field: 'timeNodeId',
                type: 'select',
                placeholder: '请选择',
                fetchConfig: {
                    apiName: "getBaseCodeSelect",
                    otherParams: {
                        itemId: 'shiJianJieDian'
                    }
                },
                optionConfig: {//下拉选项配置
                    label: 'itemName', //默认 label
                    value: 'itemId',//
                },
                required: true,
            }
        }, {
            table: {
                title: '事项名称', //表头标题
                dataIndex: 'itemNameName', //表格里面的字段
                key: 'itemNameName',//表格的唯一key  
            },
            form: {
                field: 'itemNameId',
                type: 'select',
                placeholder: '请选择',
                fetchConfig: {
                    apiName: "getBaseCodeSelect",
                    otherParams: {
                        itemId: 'shiXiangMingCheng'
                    }
                },
                optionConfig: {//下拉选项配置
                    label: 'itemName', //默认 label
                    value: 'itemId',//
                },
                required: true,
            }
        }, {
            isInTable:false,
            form: {
                label: '使用范围',
                field: 'scopeOfUseList',
                type: 'treeSelect',
                required:true,
                treeSelectOption: {
                    selectType: "1",
                    fetchConfig: {//配置后将会去请求下拉选项数据
                        apiName: 'getSysDepartmentAllTree'
                    }
                }
            }
        }, {
            table: {
                title: '流程', //表头标题
                dataIndex: 'process', //表格里面的字段
                key: 'process',//表格的唯一key  
            },
            form: {
                type: 'textarea',
                placeholder:'请输入',
                required:true
            }
        }, {
            table: {
                title: '办理时限', //表头标题
                dataIndex: 'handleTimeLimit', //表格里面的字段
                key: 'handleTimeLimit',//表格的唯一key  
            },
            form: {
                type: 'string',
                placeholder:'请输入',
                required:true
            }
        }, {
            table: {
                title: '流程是否已信息化', //表头标题
                dataIndex: 'informationalizationFlag', //表格里面的字段
                key: 'informationalizationFlag',//表格的唯一key 
                render:(data) => {
                    if(data === '0'){
                        return '否';
                    }else{
                        return '是';
                    }
                } 
            },
            form: {
                type: 'radio',
                optionData: [  //可为function (props)=>array
                    {
                        label: "否",
                        value: "0"
                    },
                    {
                        label: "是",
                        value: "1"
                    }
                ],
                required:true
            }
        },{
            isInTable: false,
            form: {
                label: '主责部门',
                field: 'mainDepartmentList',
                type: 'treeSelect',
                treeSelectOption: {
                    selectType: "1",
                    maxNumber:1,
                    fetchConfig: {//配置后将会去请求下拉选项数据
                        apiName: 'getSysDepartmentAllTree'
                    }
                },
                spanForm:12,
                formItemLayoutForm: {
                    labelCol: {
                        xs: { span: 24 },
                        sm: { span: 8 }
                    },
                    wrapperCol: {
                        xs: { span: 24 },
                        sm: { span: 16 }
                    }
                },
                required:true
            }
        }, {
            isInTable: false,
            form: {
                label: '责任人',
                field: 'responsiblePersonList',
                type: 'treeSelect',
                treeSelectOption: {
                    selectType: "2",
                    maxNumber:1,
                    fetchConfig: {//配置后将会去请求下拉选项数据
                        apiName: 'getSysDepartmentUserAllTree'
                    }
                },
                spanForm:12,
                formItemLayoutForm: {
                    labelCol: {
                        xs: { span: 24 },
                        sm: { span: 8 }
                    },
                    wrapperCol: {
                        xs: { span: 24 },
                        sm: { span: 16 }
                    }
                },
                required:true
            }
        }, {
            isInTable: false,
            form: {
                label: '制度关联',
                field: 'systemAttachment',
                type: 'images',
                fetchConfig: {
                    apiName: window.configs.domain + 'upload',
                },
                desc: '制度', 
            }
        },{
            isInTable: false,
            form: {
                label: '资料',
                field: 'attachmentList',
                type: 'files',
                fetchConfig: {
                    apiName: window.configs.domain + 'upload',
                },
            }
        },{
            isInForm: false,
            table: {
                title: '操作',
                dataIndex: 'action', //表格里面的字段
                key: 'action',//表格的唯一key  
                showType: "tile",
                btns: [
                    {
                        name: 'edit',
                        render: function (rowData) {
                            return '<a>修改</a>';
                        },
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
                                    apiName: 'updateZjXmServerProjectEstablishment',
                                }
                            }
                        ]
                    }
                ]
            }
        },
    ]
}
class index extends Component {
    render() {
        return (
            <div>
                <QnnTable
                    {...this.props}
                    fetch={this.props.myFetch}
                    headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                    {...configs}
                />
            </div>
        )
    }
}

export default index;