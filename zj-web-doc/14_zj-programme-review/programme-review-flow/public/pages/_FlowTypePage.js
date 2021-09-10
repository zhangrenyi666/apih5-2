window.FlowTypePage = {//流程类型导入
    fetchConfig: {//表格的ajax配置
        apiName: 'getBaseFlowCodeList',
    },
    antd: { //同步antd table组件配置 ***必传
        rowKey: function (row) {// ***必传
            return row.flowCodeId
        }
    },
    drawerConfig: {
        width: '600px'
    },
    infoAlert: function (selectedRows) {
        return '已选择 ' + selectedRows.length + '项';
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
            label: '导入',
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
                        apiName: 'baseFlowCodeImport',
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
                apiName: 'batchDeleteUpdateBaseFlowCode',
            },
        }
    ],
    //每个表单项的布局 -- 搜索区域
    formItemLayoutSearch: {
        //默认数据
        labelCol: {
            xs: { span: 24 },
            sm: { span: 6 }
        },
        wrapperCol: {
            xs: { span: 24 },
            sm: { span: 18 }
        }
    },
    formConfig: [
        {
            isInTable: false,
            form: {
                field: 'flowCodeId',
                type: 'string',
                placeholder: '请输入',
                addShow: false,
                editShow: false,
            }
        },
        {
            isInSearch: false,
            // isInForm: false,
            table: {
                title: '流程ID', //表头标题
                dataIndex: 'apih5FlowId', //表格里面的字段
                key: 'apih5FlowId',//表格的唯一key  
            },
            form: {
                type: 'string',
                placeholder: '请输入',
                addShow: false,
                editShow: true,
                editDisabled: true,
            }
        }, {
            isInSearch: true,
            // isInForm: false,
            table: {
                title: '流程名称', //表头标题
                dataIndex: 'apih5FlowName', //表格里面的字段
                key: 'apih5FlowName',//表格的唯一key  
            },
            form: {
                type: 'string',
                placeholder: '请输入',
                addShow: false,
                editShow: true,
            }
        }, {
            isInSearch: false,
            // isInForm: false,
            table: {
                title: '节点ID', //表头标题
                dataIndex: 'apih5NodeId', //表格里面的字段
                key: 'apih5NodeId',//表格的唯一key  
            },
            form: {
                type: 'string',
                placeholder: '请输入',
                addShow: false,
                editShow: true,
            }
        }, {
            isInSearch: false,
            // isInForm: false,
            table: {
                title: '节点名称', //表头标题
                dataIndex: 'apih5NodeName', //表格里面的字段
                key: 'apih5NodeName',//表格的唯一key  
            },
            form: {
                type: 'string',
                placeholder: '请输入',
                addShow: false,
                editShow: true,
            }
        }, {
            isInSearch: false,
            isInForm: false,
            table: {
                title: '创建者', //表头标题
                dataIndex: 'createUserName', //表格里面的字段
                key: 'createUserName',//表格的唯一key  
            },
            form: {
                type: 'string',
                placeholder: '请输入',
            }
        }, {
            isInSearch: false,
            isInForm: false,
            table: {
                title: '创建时间', //表头标题
                dataIndex: 'createTime', //表格里面的字段
                key: 'createTime',//表格的唯一key  
                format: 'YYYY-MM-DD HH:mm:ss',
            },
            form: {
                type: 'string',
                placeholder: '请输入',
            }
        }, {
            isInTable: false,
            form: {
                label: '导入流程xml',
                field: 'xmlFileList',
                type: 'files',
                accept: '.xml',
                initialValue: [],
                addShow: true,
                editShow: false,
                fetchConfig: {//配置后将会去请求下拉选项数据
                    apiName: window.configs.domain + 'upload',
                    // name:'123', //上传文件的name 默认空 
                },
            }
        }, 


        {
            isInForm: false,
            table: {
                dataIndex: 'action', //表格里面的字段
                key: 'action',//表格的唯一key  
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
                                    apiName: 'updateBaseFlowCode',
                                }
                            }
                        ]
                    }
                ]
            }
        },
    ]
}
