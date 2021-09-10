window.tableHeaderClass = {
    antd: {
        rowKey: "id",
        size: "small",
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
                }
            ]
        },
    ],
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
            address: "蒙渡村",
            age: 21,
            sex: "男",
            hobby: "0",
            birthday: 1582275494301,
            year: 1582275494301,
            rangeDate: [1582275494301,1582575494301],
        },
        {
            id: "2",
            name: "老李头",
            address: "沈阳村",
            age: 55,
            sex: "男"
        }
    ],
    formConfig: [
        {
            isInSearch: true,
            table: {
                title: "姓名",
                dataIndex: "name",
                width: 120,
                children: [
                    {
                        title: "姓",
                        dataIndex: "sname",
                        width: 40,
                    },
                    {
                        title: "名",
                        dataIndex: "ename",
                        width: 40,
                    },
                    {
                        title: "辈",
                        dataIndex: "zi",
                        width: 40,
                    }
                ]
            },
            form: {
                type: "string",
                placeholder: "请输入..."
            },
        },

        {
            isInSearch: true,
            table: {
                width: 120,
                title: "年纪",
                dataIndex: "age",
                children: [
                    {
                        title: "虚岁",
                        dataIndex: "sname",
                        width: 40,
                    },
                    {
                        title: "周岁",
                        dataIndex: "ename",
                        width: 40,
                    },
                    {
                        title: "实际",
                        dataIndex: "zi",
                        width: 40,
                    }
                ]
            },
            form: {
                type: "string",
                placeholder: "请输入..."
            },
        },
        {
            isInSearch: true,
            table: { 
                title: "词类", 
                dataIndex: "partsSpeech",
                children: [
                    {
                        title: "实词",
                        dataIndex: "notionalWord", 
                        children:[
                            {
                                title: "体词",
                                dataIndex: "1", 
                                children:[
                                    {
                                        width:80,
                                        title: "名词",
                                        dataIndex: "1-1", 
                                    },
                                    {
                                        width:80,
                                        title: "数词",
                                        dataIndex: "1-2", 
                                    },
                                    {
                                        width:80,
                                        title: "量词",
                                        dataIndex: "1-3", 
                                    }
                                ]
                            },
                            {
                                title: "谓词",
                                dataIndex: "2", 
                                children:[
                                    {
                                        width:80,
                                        title: "形容词",
                                        dataIndex: "2-1", 
                                    },
                                    {
                                        width:80,
                                        title: "动词",
                                        dataIndex: "2-2", 
                                    } 
                                ]
                            },
                            {
                                title: "加词",
                                dataIndex: "3", 
                                children:[
                                    {
                                        width:80,
                                        title: "区分词",
                                        dataIndex: "3-1", 
                                    },
                                    {
                                        width:80,
                                        title: "副词",
                                        dataIndex: "3-2", 
                                    } 
                                ]
                            }
                        ]
                    },
                    {
                        width:80,
                        title: "虚词",
                        dataIndex: "functionWord", 
                    },
                    {
                        width:80,
                        title: "叹词",
                        dataIndex: "interjection", 
                    },
                    {
                        width:80,
                        title: "拟声词",
                        dataIndex: "mimeticWord", 
                    }
                ]
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
                btns: [
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

                    },
                    {
                        name: "detail",
                        label: "详情",
                        labelStyle: { color: '#1890ff',cursor: 'pointer' },
                    }
                ]
            }
        }
    ],

}