window.FormTabs = {
    formItemLayout: {
        labelCol: {
            xs: { span: 6 },
            sm: { span: 5 }
        },
        wrapperCol: {
            xs: { span: 18 },
            sm: { span: 16 }
        }
    },

    tailFormItemLayout: {
        labelCol: {},
        wrapperCol: {
            xs: { span: 24 },
            sm: { offset: 5,span: 16 },
        }
    },
    tabsIndex:"0",
    tabs: [
        {
            title: "基本信息",
            type: "qnnForm",
            field: "one",
            onShow: (obj) => { console.log(obj); console.log('one页面出现') },
            content: {
                //按钮
                btns: [
                    {
                        field: "submit",
                        label: "提交",
                        type: 'primary', //primary dashed danger
                        fetchConfig: {
                            //api 默认提交整个表单的数据
                            apiName: 'submit',
                        },

                        //当事件确定要发生时需要提醒用户时用到

                        affirmTitle: '确认提交吗？',//有这文字会点击按钮验证通过时将自动弹出提示
                        affirmDesc: '提交后将无法撤回',//有这文字会点击按钮验证通过时将自动弹出提示
                        affirmYes: '确定',// 确认窗的确定按钮文字 默认确定
                        affirmNo: '取消',//确认窗的取消按钮文字  默认取消 
                    },
                    {
                        field: "diyClick",
                        label: '自定义',
                        type: 'danger',
                        ghost: true,
                        onClick: "bind:diyClick",
                        condition: [
                            {//条件
                                regex: {//匹配规则 正则或者字符串
                                    name: '1',
                                },
                                action: 'disabled', //disabled, show, hide, function(){}
                            }
                        ]
                    },
                    {
                        field: "diyClick",
                        label: '不验证',
                        type: 'primary', //primary dashed danger 
                        ghost: true,
                        onClick: "bind:diyClick",
                        isValidate: false,
                    },
                    {
                        field: "diyClick",
                        label: '只验证当前表单',
                        type: 'primary', //primary dashed danger 
                        ghost: true,
                        onClick: "bind:diyClick",
                        isValidate: "curTab",
                        hide: "bind:mobileHide"
                    }
                ],
                formConfig: [
                    {
                        type: "string",
                        field: 'apiBody.name',
                        label: "姓名",
                        required: true
                    },
                    {
                        type: "string",
                        field: "hobby",
                        label: "爱好",
                    },
                    {
                        type: "select",
                        field: "sex",
                        label: "性别",
                        optionData: [{ label: "男",value: "0" },{ label: "女",value: "1" }]
                    },
                    ...Array.from(new Array(20)).map((_,i) => {
                        return {
                            type: "string",
                            field: 'apiBody' + i,
                            label: "姓名" + i,
                        }
                    })
                ]
            }
        },
        {
            title: "社会关系",
            type: "qnnForm",
            field: "two",
            onShow: () => { console.log('two页面出现') },
            content: {
                formConfig: [
                    {
                        type: "number",
                        field: "person",
                        label: "认识人数",
                        required: true
                    },
                    {
                        type: "qnnForm",
                        field: "block",
                        label: "上头信息",
                        formFields: [
                            {
                                type: "string",
                                field: "name",
                                label: "上头姓名",
                                required: true
                            },
                            {
                                type: "number",
                                field: "money",
                                label: "上头资产",
                            }
                        ]
                    },
                ],
                btns: [

                    {
                        field: "diyClick",
                        label: '不验证',
                        type: 'primary', //primary dashed danger 
                        ghost: true,
                        onClick: "bind:diyClick",
                        isValidate: false,
                    },
                    {
                        field: "diyClick",
                        label: '只验证当前表单',
                        type: 'primary', //primary dashed danger 
                        ghost: true,
                        onClick: "bind:diyClick",
                        isValidate: "curTab",
                    }
                ],
            }
        },
        {
            title: "table",
            type: "qnnTable",
            field: "table",
            onShow: () => { console.log('table页面出现') },
            content: {
                antd: { rowKey: "workId" },
                isShowRowSelect: false,
                fetchConfig: {
                    apiName: "getTodoList"
                },
                formConfig: [
                    {
                        table: {
                            title: '流程id',
                            dataIndex: 'workId',
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
                            format: "YYYY-MM-DD"
                        }
                        ,form: {
                            type: 'string',
                            placeholder: '请输入',
                            required: false,
                        }
                    }

                ],
            }
        },
        {
            title: "自定义页面",
            type: "diyOne",
            field: "diyOne",
            onShow: () => { console.log('自定义组件页面出现') },
            content: "diyOne"
        }
    ]
};
