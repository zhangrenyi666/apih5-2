window.tableConfig = {}; //所有页面配置存放在这里面

window.tableConfig.testPage = {
    fetchConfig: {//表格的ajax配置
        apiName: 'getSxDocumentInfoList',
        otherParams: {}, //需要固定写死的参数
    },
    // data: [], //表格数据 默认为空 当fetchConfig存在时无意义
    antd: { //同步antd table组件配置 ***必传
        rowKey: function (row) {// ***必传
            return row.documentId
        },
    },

    //表格分页配置   
    limit: 10,   //每页显示条数 默认10
    curPage: 1, //当前页  默认1
    paginationConfig: {// 同步antd的分页组件配置   
        position: 'bottom'
    },

    drawerConfig: {},//抽屉的配置 同步antd的Drawer组件配置 
    //文字配置
    labelConfig: {
        actionBtn: '操作',//行右边下拉菜单标题
        detail: '详情',//抽屉详情左上角文字
        add: '新增',//抽屉新增左上角文字
        edit: '编辑' //抽屉新增左上角文字
    },

    isShowRowSelect: true,//是否显示选择框  默认true

    //全局提示框 给false将
    infoAlert: function (selectedRows) {
        return '已选择 ' + selectedRows.length + '项';
    },

    //每个表单项的布局 -- 表单
    formItemLayout: {
        //默认数据
        labelCol: {
            xs: { span: 24 },
            sm: { span: 5 }
        },
        wrapperCol: {
            xs: { span: 24 },
            sm: { span: 19 }
        }
    },
    //每个表单项的布局 -- 搜索区域
    formItemLayoutSearch: {
        //默认数据
        labelCol: {
            xs: { span: 24 },
            sm: { span: 4 }
        },
        wrapperCol: {
            xs: { span: 24 },
            sm: { span: 20 }
        }
    },

    //所有操作按钮
    //内置 新增add 删除del 
    actionBtns: [
        {
            name: 'add',
            icon: 'plus',//icon
            type: 'primary',//类型  默认 primary
            label: '新增',
            //表单里面的按钮  name内置 【submit， cancel】
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
                        apiName: 'submit',
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
                apiName: 'del',
                key: 'delete',//默认deleteList
            },
            //内置的按钮一般不需要传 传了就相当于一个回调
            onClick: function (data) {
                console.log(data)
            }
        },
        {
            name: 'diy',//内置add del
            icon: 'more',//icon
            type: 'dashed',//类型  默认 primary  [primary dashed danger]
            label: '其他操作', 
            //内置的按钮一般不需要传 传了就相当于一个回调
            onClick: function (obj) {
                console.log(obj)
            }
        }
    ],

    //所有字段配置
    formConfig: [// ***必传
        {
            //该字段是否是搜索条件 默认false
            isInSearch: true,
            //该字段是否在表格中存在 默认true
            isInTable: true,
            //该字段是否在表单中存在 默认true
            isInForm: true,

            //表格配置 同步antd table组件配置
            table: {
                title: '姓名', //表头标题
                dataIndex: 'createUser', //表格里面的字段
                key: 'createUser',//表格的唯一key 
                fixed: 'left',//固定到左边
                width: 120, //列数比较多时必须设置
                render: function (text) {
                    return text
                }
            },

            //表单配置 不配置将默认使用表格的配置的有【label, field, render】
            form: {
                label: '姓名',
                field: 'createUser',
                type: 'string',
                placeholder: '请输入',
                //决定表单是否能平铺 
                // spanForm: '12', //表单中行格数 一行24格 默认24
                spanSearch: '8', //表单中行格数 一行24格 默认8
                required: true,
            },
        },
        {
            isInSearch: true,
            table: {
                title: '部门', //表头标题
                dataIndex: 'deptName', //表格里面的字段
                key: 'deptName',//表格的唯一key 
                width: 150,
            },
            form: {
                type: 'string',
                placeholder: '请输入',
                required: true

            },
        },
        {
            isInSearch: true,
            table: {
                title: '文号', //表头标题
                dataIndex: 'documentId', //表格里面的字段
                key: 'documentId',//表格的唯一key 
                width: 150,
                render: function (data) {
                    if (data && data.length > 8) {
                        data = data.substr(0, 8) + '...'
                    }
                    return data
                }
            },
            form: {
                type: 'string',
                placeholder: '请输入',
                required: true
                // spanForm: '12', //表单中行格数 一行24格 默认24
            },
        },
        {
            isInSearch: true,
            table: {
                title: '标题', //表头标题
                dataIndex: 'documentTitle', //表格里面的字段
                key: 'documentTitle',//表格的唯一key 
                width: 150,
                tooltip: '5',//此属性和render属性不可并存 开启文字提示，当文字内容多时需要开启
            },
            form: {
                type: 'string',
                placeholder: '请输入',
            },
        },
        {
            table: {
                title: '流程', //表头标题
                dataIndex: 'processStatus', //表格里面的字段
                key: 'processStatus',//表格的唯一key 
                width: 150,
                render: function (data) {
                    return '处理中' + data
                }
            },
            form: {
                type: 'string',
                placeholder: '请输入',
            },
        },
        {
            table: {
                title: '延时', //表头标题
                dataIndex: 'delayStatus', //表格里面的字段
                key: 'delayStatus',//表格的唯一key 
                width: 120,
                render: function (data) {
                    return data
                }
            },
            form: {
                type: 'string',
                placeholder: '请输入',
            },
        },

        {
            table: {
                title: '分数', //表头标题
                // width: 150,
                dataIndex: 'docNumber', //表格里面的字段
                key: 'docNumber',//表格的唯一key 
            },

            form: {
                type: 'string',
            },
        },
        { 
            table: {
                title: '时间', //表头标题
                dataIndex: 'modifyTime', //表格里面的字段
                key: 'modifyTime',//表格的唯一key , 
                // width: 151,
                render: function (time) {
                    return new Date(time).toLocaleDateString()
                }
            },
            form: {
                type: 'date',
            }
        }, {
            isInTable: false,
            form: {
                label: '图片',
                field: 'images',
                type: 'images',
            }
        }, {
            isInTable: false,
            form: {
                label: '文件',
                field: 'files',
                type: 'files',
            }
        },
        {
            isInTable: false,
            form: {
                label: '备注',
                field: 'handlingRequirements',
                type: 'textarea',
                placeholder: '请输入',
            }
        },
        {
            isInForm: false,
            table: {
                width: 80,
                // title: "操作",
                key: "action",
                fixed: 'right',//固定到右边
                showType: 'bubble', //出来的样式 bubble（气泡）  tile（平铺） 默认bubble
                btns: [
                    {
                        name: 'edit', // 内置name有【edit, detail， del】
                        render: function (rowData) {
                            return '<a>编辑</a>';
                        },
                        //表单里面的按钮  name内置 【submit， cancel】
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
                                    apiName: 'save',
                                }
                            }
                        ] 
                    },
                    {
                        name: 'detail', // 内置name有【detail， del】
                        render: function (rowData) {
                            return '<a>详情</a>';
                        }, 
                        // fetchConfig:{//ajax配置
                        //     apiName:'123456',
                        //     params:{
                        //         test:'documnetId'
                        //     }
                        // },
                        // onClick:function(rowData){//内置的按钮一般不需要传
                        //     console.log(rowData)
                        // }
                    },
                    {
                        name: 'diy', // 内置name有【detail， del】
                        render: function (rowData) {
                            return '<a>自定义</a>';
                        },
                        onClick: function (rowData) {//内置的按钮一般不需要传
                            alert(rowData.documentId)
                        }
                    },
                ]
            }
        }
    ],
}


