window.tableBasic = {
    antd: {
        rowKey: "id",
        size: "small",
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
                }
            ]
        },
        {
            field: 'getSearch',
            name: 'getSearch',//【add, addRow,  del, edit, detail, Component, form】 
            type: 'primary',
            label: '获取搜索参数',
            onClick: "bind:getSearch"
        },
        {
            field: 'getSearch',
            name: 'getSearch',//【add, addRow,  del, edit, detail, Component, form】 
            type: 'primary',
            label: '切换搜索表单样式',
            onClick: "bind:changeSearchBtnsStyle"
        }, 
    ],
    formItemLayout: {
        labelCol: {
            xs: { span: 24 },
            sm: { span: 4 }
        },
        wrapperCol: {
            xs: { span: 24 },
            sm: { span: 20 }
        }
    },
    fetchConfig: {
        apiName: "getTodoList"
    },
    // data: [
    //     {
    //         id: "0",
    //         name: "测试王",
    //         address: "蒙渡村",
    //         age: 21,
    //         sex: "男",
    //         hobby: "0",
    //         birthday: 1582275494301,
    //         year: 1582275494301,
    //         rangeDate: [1582275494301,1582575494301],
    //     },
    //     {
    //         id: "2",
    //         name: "老李头",
    //         address: "沈阳村",
    //         age: 55,
    //         sex: "男"
    //     }
    // ],
    formConfig: [
        {
            isInSearch: true,
            table: {
                title: "姓名",
                dataIndex: "title",
                width: 120,
                onClick: "bind:getSearch", 
                tooltip: 10
            },
            form: {
                type: "string",
                required: true,
                placeholder: "请输入...",
                spanForm: 12,
                formItemLayoutForm: {
                    labelCol: {
                        sm: { span: 8 }
                    },
                    wrapperCol: {
                        sm: { span: 16 }
                    }
                },
                colStyle: {
                    paddingLeft: 12
                }
            },
        },

        {
            isInSearch: true,
            table: {
                dataIndex: "age",
                width: 120,
                title: "年纪", 
            },
            form: {
                type: "string",
                placeholder: "请输入...",
                spanForm: 12,
            },
        },
        {
            isInSearch: true,
            table: {
                width: 120,
                title: "爱好",
                dataIndex: "hobby",   
            },
            form: {
                type: "select",
                placeholder: "选择...",
                optionConfig: {
                    label: "itemName",
                    value: "itemId",
                    children: "children"
                },
                fetchConfig: {
                    apiName: "getBaseCodeTree",
                    otherParams: {
                        itemId: "xingzhengquhuadaima"
                    }
                }
                
            },
        },
        {
            // isInSearch: true,
            table: {
                width: 250,
                title: "日期(date、week、month、year)",
                format: 'YYYY-MM-DD',
                dataIndex: "birthday", 
            },
            form: {
                label: "日期",
                type: "date",
                placeholder: "请输入..."
            },
        },
        {
            table: {
                width: 300,
                title: "月选择",
                format: 'YYYY-MM',
                dataIndex: "year", 
            },
            form: {
                label: "月选择",
                type: "month",
                placeholder: "请输入..."
            },
        },
        {
            // isInSearch: true,
            table: {
                width: 300,
                title: "日期范围(date、week、month、year)",
                dataIndex: "rangeDate",
                format: 'YYYY-MM', 
            },
            form: {
                label: "日期范围",
                type: "rangeDate",
                picker: "date", //week  month  year  time date 
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
            isInTable: false,
            form: {
                type: "qnnForm",
                field: "textinput",
                // closeed: true,
                label: "表单块测试",
                formFields: [
                    {
                        type: "money",
                        label: "金额",
                        field: "money",
                        placeholder: "请输入",
                        required: true,
                        span: 12
                    },
                    {
                        type: "string",
                        field: "string",
                        label: "普通文本输入",
                        span: 12
                    },
                ]
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
                                    label: "拿数据3",
                                    onClick: "bind:diy1Fn",
                                    isValidate: "curTab"
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
}


window.tableBasic2 = {
    antd: {
        rowKey: "id",
        size: "small",
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
                }
            ]
        },
        {
            field: 'getSearch',
            name: 'getSearch',//【add, addRow,  del, edit, detail, Component, form】 
            type: 'primary',
            label: '获取搜索参数',
            onClick: "bind:getSearch"
        },
        {
            field: 'getSearch',
            name: 'getSearch',//【add, addRow,  del, edit, detail, Component, form】 
            type: 'primary',
            label: '切换搜索表单样式',
            onClick: "bind:changeSearchBtnsStyle"
        },
    ],
    formItemLayout: {
        labelCol: {
            xs: { span: 24 },
            sm: { span: 4 }
        },
        wrapperCol: {
            xs: { span: 24 },
            sm: { span: 20 }
        }
    },
    fetchConfig: {
        apiName: "getTodoList"
    }, 
    formConfig: [
        {
            isInSearch: true,
            table: {
                title: "姓名",
                dataIndex: "title",
                width: 120,
                onClick: "bind:getSearch", 
                tooltip: 10
            },
            form: {
                type: "string",
                required: true,
                placeholder: "请输入...",
                spanForm: 12,
                formItemLayoutForm: {
                    labelCol: {
                        sm: { span: 8 }
                    },
                    wrapperCol: {
                        sm: { span: 16 }
                    }
                },
                colStyle: {
                    paddingLeft: 12
                }
            },
        },

        {
            isInSearch: true,
            table: {
                dataIndex: "age",
                width: 120,
                title: "年纪", 
            },
            form: {
                type: "string",
                placeholder: "请输入...",
                spanForm: 12,
            },
        },
        {
            isInSearch: true,
            table: {
                width: 120,
                title: "爱好",
                dataIndex: "hobby", 
                filter: true, 
            },
            form: {
                type: "select",
                placeholder: "选择...",
                optionConfig: {
                    label: "itemName",
                    value: "itemId",
                    children: "children"
                },
                fetchConfig: {
                    apiName: "getBaseCodeTree",
                    otherParams: {
                        itemId: "xingzhengquhuadaima"
                    }
                } 
            },
        },
        {
            // isInSearch: true,
            table: {
                width: 250,
                title: "日期(date、week、month、year)",
                format: 'YYYY-MM-DD',
                dataIndex: "birthday",
                filter: true,
            },
            form: {
                label: "日期",
                type: "date",
                placeholder: "请输入..."
            },
        },
        {
            table: {
                width: 300,
                title: "月选择",
                format: 'YYYY-MM',
                dataIndex: "year",
                filter: true,
            },
            form: {
                label: "月选择",
                type: "month",
                placeholder: "请输入..."
            },
        },
        {
            // isInSearch: true,
            table: {
                width: 300,
                title: "日期范围(date、week、month、year)",
                dataIndex: "rangeDate",
                format: 'YYYY-MM',
                filter: true,
            },
            form: {
                label: "日期范围",
                type: "rangeDate",
                picker: "date", //week  month  year  time date 
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
                                    label: "拿数据3",
                                    onClick: "bind:diy1Fn",
                                    isValidate: "curTab"
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
}