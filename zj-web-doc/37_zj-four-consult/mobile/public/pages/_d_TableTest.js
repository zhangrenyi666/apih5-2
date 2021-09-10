window.tableTest = {
    // firstRowIsSearch: true,
    // selectType: "radio",
    // isShowRowSelect:false,
    antd: {
        rowKey: "id",
        size: "small",
        rowClassName: function (record,index) {
            return "aaa"
        }
    },
    drawerConfig: {
        width: "800px"
    },


    actionBtns: [
        {
            field: 'addCancelBtn',
            //willExecute:()=>void //点击前
            name: 'add',//【add, addRow,  del, edit, detail, Component, form】
            icon: 'plus',
            type: 'primary',
            label: '新增',
            drawerTitle: '新增',
            formBtns: [
                {
                    name: 'cancel', //关闭右边抽屉
                    type: 'dashed',//类型  默认 primary
                    label: '取消',
                    field: 'addCancelBtn',
                },
                {
                    name: 'submit',//内置add del
                    type: 'primary',//类型  默认 primary
                    label: '提交',//提交数据并且关闭右边抽屉
                    //格式化数据
                    //paramsFormat:({params, props, ...})=>{return {...}},
                    //控制是否提交表单
                    //isCanSubmit: ({params, props, ...}, callback)=>callback(true), //callback(true) || callback(false)
                    fetchConfig: {//ajax配置
                        apiName: 'login',
                        //删除参数中的name、age字段
                        delParams: []
                    },
                    //自定义按钮key值 必须配置 否则可能按钮点击后不会置为loading状态可能会导致重复提交问题
                    field: 'addSubmitBtn',
                    // isValidate:"curTab",
                    isValidate: true,//点击后是否验证表单 默认true
                },
                {
                    name: 'diy2',//内置add del
                    type: 'primary',//类型  默认 primary
                    label: 'tab1隐藏',//提交数据并且关闭右边抽屉 
                    field: 'hideSub',
                    isValidate: false,
                    hide: "bind:hideSub",
                    icon: 'diy1Com', 
                }
            ]
        },
        {
            field: 'delBtn',
            //willExecute:()=>void //点击前
            name: 'del',//【add, addRow,  del, edit, detail, Component, form】
            icon: 'delete',
            fetchConfig: {//ajax配置
                apiName: 'submit',
                //删除参数中的name、age字段
                delParams: []
            },
            type: 'danger',
            label: '删除',
            // 配置 可控制是否提交表单  params在这是个数组
            //isCanSubmit: ({params, props, ...}, callback)=>callback(true), //callback(true) || callback(false)
        },
        {
            field: 'delBtn', 
            name: 'diy', 
            icon: 'diy1Com', 
            type: 'danger',
            label: '自定义icon',
        },
    ],

    // actionBtns:{
    //     apiName: "getTodoList",
    //     otherParams:{}
    // },
    // fetchConfig: {
    //     apiName: "getTodoList"
    // },
    rowDisabledCondition: function (rowData,props) {
        return rowData.id === '2'
    },
    formItemLayout: {
        labelCol: {
            xs: { span: 24 },
            sm: { span: 4 }
        },
        wrapperCol: {
            xs: { span: 24 },
            sm: { span: 16 }
        }
    },
    data: [
        {
            id: "0",
            name: "测试王",
            title: "测试王",
            address: "蒙渡村",
            age: 21,
            sex: "男",
            hobby: "0",
            birthday: 1582275494301,
            year: 1582275494301,
            rangeDate: [1582275494301,1582575494301],
        },
        {
            title: "测试王",
            id: "2",
            name: "老李头",
            address: "沈阳村",
            age: 55,
            sex: "男",
        }
    ],
    formConfig: [
        {
            table: {
                tdEdit: true,
                title: "姓名",
                dataIndex: "title",
                width: 120,
                fixed: "left",
                render: (value,row,index) => {
                    const obj = {
                        children: value,
                        props: {},
                    };
                    if (index === 1) {
                        obj.props.colSpan = 2;
                    }

                    return obj;
                },
            },
            form: {
                type: "string",
                placeholder: "请输入..."
            },
        },


        {
            // isInSearch:true,
            table: {
                width: 120,
                title: "爱好",
                dataIndex: "hobby",
                render: (value,row,index) => {
                    const obj = {
                        children: value,
                        props: {},
                    };
                    if (index === 1) {
                        obj.props.colSpan = 0;
                    }

                    return obj;
                },
            },
            form: {
                type: "select",
                placeholder: "选择...",
                optionData: [
                    {
                        label: "吃饭",
                        value: "0"
                    },
                    {
                        label: "睡觉",
                        value: "1"
                    },
                    {
                        label: "玩游戏",
                        value: "2"
                    }
                ]
            },
        },
        {
            table: {
                width: 250,
                title: "月份",
                format: 'YYYY-MM-DD',
                dataIndex: "birthday",
                fieldsConfig: {
                    type: "month",
                    placeholder: "请选择...",
                }
            },
            form: {
                label: "日期",
                type: "month",
                placeholder: "请输入..."
            },
        },
        {
            table: {
                width: 300,
                title: "日期范围下拉(date、week、month、year)",
                format: 'YYYY-MM',
                dataIndex: "year",
            },
            form: {
                label: "日期范围下拉",
                type: "date",
                placeholder: "请输入..."
            },
        },
        {
            table: {
                width: 300,
                title: "日期范围(date、week、month、year)",
                dataIndex: "rangeDate",
                format: 'YYYY-MM',
            },
            form: {
                label: "日期范围",
                type: "rangeDate",
                placeholder: "请输入..."
            },
        },
        {
            table: {
                width: 120,
                title: "住址",
                dataIndex: "address",
            },
            form: {
                type: "string",
                placeholder: "请输入..."
            },
        },
        {
            isInForm: false,
            table: {
                width: 120,
                title: "操作",
                align: "center",
                dataIndex: "action", //表格里面的字段
                key: "action", //表格的唯一key
                fixed: "right",
                showType: 'tile',
                btns: function (props) {
                    return [
                        {
                            name: "edit",
                            label: "修改",
                            //抽屉中的按钮
                            formBtns: [
                                {
                                    field: "upCancelBtn",
                                    name: "cancel",
                                    type: "dashed",
                                    label: "取消",
                                },
                                {
                                    field: "upSubmitBtn1",
                                    name: "submit",
                                    type: "primary",
                                    label: "提交",
                                    fetchConfig: {
                                        //修改接口
                                        apiName: "login"
                                    }
                                },
                                {
                                    field: "diy1",
                                    name: "diy",
                                    type: "primary",
                                    label: "拿数据",
                                    onClick: "bind:diy1Fn"
                                }
                            ],

                            //条件配置
                            condition: [
                                {
                                    //匹配规则 正则或者字符串
                                    //eg:行数据中的字段 id:'01' 时将禁用输入框
                                    regex: {
                                        workId: '1',
                                    },
                                    action: 'hide', //disabled,  show,  hide, function(){}
                                }
                            ]

                        },
                        {
                            name: "detail",
                            label: "详情",
                            labelStyle: { color: '#1890ff',cursor: 'pointer' },
                        }
                    ]
                }
            }
        }
    ],
    tabs: [
        {
            field: "one",
            name: "qnnForm",
            title: "表单",
            content: {
                formConfig: [
                    {
                        type: 'string',
                        label: '姓名',
                        field: 'name',
                        placeholder: '请输入',
                        required: true,
                    },

                    {
                        type: 'number',
                        label: '年纪',
                        field: 'age',
                        placeholder: '请输入',
                        required: false,
                    },
                ],
            }
        },
        {
            field: "one2",
            name: "qnnForm",
            title: "表单",
            content: {
                formConfig: [
                    {
                        type: 'string',
                        label: '其他',
                        field: 'other',
                        placeholder: '请输入',
                        required: true,
                    },
                ],
            }
        },
        {
            field: "two",
            name: "qnnTable",
            title: "表格",
            content: {
                fetchConfig: {
                    apiName: "getTodoList"
                },
                formConfig: [
                    {
                        table: {
                            title: '流程id',
                            dataIndex: 'workId',
                            width: 300,
                            ellipsis: true,
                        }
                        ,form: {
                            type: 'string',
                            placeholder: '请输入',
                            required: false,
                        }
                    },
                    {
                        table: {
                            title: '标题',
                            dataIndex: 'title',
                            ellipsis: true,
                        }
                        ,form: {
                            type: 'string',
                            placeholder: '请输入',
                            required: false,
                        }
                    },
                    {
                        table: {
                            title: '发起时间',
                            dataIndex: 'createTime',
                            format: "YYYY-MM-DD",
                            ellipsis: true,
                        }
                        ,form: {
                            type: 'string',
                            placeholder: '请输入',
                            required: false,
                        }
                    }
                ],
                // actionBtns:[{...}] 
            }
        },
        {
            field: "diy1",
            name: "diy1",
            title: "自定义页面",
            content: "diy1Com"
        }
    ]

}