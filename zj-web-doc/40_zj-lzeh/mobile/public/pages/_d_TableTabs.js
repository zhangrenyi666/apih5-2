window.tableTabs = {
    "firstRowIsSearch": true,
    "selectType": "radio",
    "antd": {
        "rowKey": "workId",
        "size": "small"
    },
    "drawerConfig": {
        "width": "800px"
    },
    "actionBtns": [
        {
            "field": "addCancelBtn",
            "name": "add",
            "icon": "plus",
            "type": "primary",
            "label": "新增",
            "drawerTitle": "新增",
            "formBtns": [
                {
                    "name": "cancel",
                    "type": "dashed",
                    "label": "取消",
                    "field": "addCancelBtn"
                },
                {
                    "name": "submit",
                    "type": "primary",
                    "label": "验证全部tab页面表单",
                    "fetchConfig": {
                        "apiName": "login",
                        "delParams": []
                    },
                    "field": "addSubmitBtn",
                    "isValidate": true
                },
                {
                    "name": "submit",
                    "type": "primary",
                    "label": "验证当前tab页面表单",
                    "fetchConfig": {
                        "apiName": "login"
                    },
                    "field": "addSubmitBtnCur",
                    "isValidate": "curTab"
                },
                {
                    "name": "diyTab",
                    "type": "primary",
                    "label": "获取当前tab缩影并跳转2",
                    "field": "diyTab",
                    "isValidate": false,
                    "onClick": "bind:diyTab"
                }
            ]
        }
    ],
    "fetchConfig": {
        "apiName": "getTodoList"
    },
    "formConfig": [
        {
            "table": {
                "title": "姓名",
                "dataIndex": "name",
                "width": 120,
                // "tdEdit": true,
                "tdEditFetchConfig": {
                    "apiName": "login"
                },
                "fieldsConfig": {
                    "type": "string",
                    "initialValue": "测试王"
                }
            },
            "form": {
                "type": "string",
                "placeholder": "请输入..."
            }
        },
        {
            "table": {
                "width": 120,
                "title": "年纪",
                // "tdEdit": true,
                "tdEditFetchConfig": {
                    "apiName": "login"
                },
                "dataIndex": "age"
            },
            "form": {
                "type": "string",
                "placeholder": "请输入..."
            }
        },
        {
            "table": {
                "width": 120,
                "title": "爱好",
                "dataIndex": "hobby",
                // "tdEdit": true,
                "tdEditFetchConfig": {
                    "apiName": "login"
                },
                "type": "select",
                "fieldsConfig": {
                    "type": "select",
                    "optionConfig": {
                        "label": "itemName",
                        "value": "itemId",
                        "children": "children"
                    },
                    "fetchConfig": {
                        "apiName": "getBaseCodeTree",
                        "otherParams": {
                            "itemId": "xingzhengquhuadaima"
                        }
                    }
                }
            },
            "form": {
                "type": "select",
                "placeholder": "选择...",
                "optionData": [
                    {
                        "label": "吃饭",
                        "value": "0"
                    },
                    {
                        "label": "睡觉",
                        "value": "1"
                    },
                    {
                        "label": "玩游戏",
                        "value": "2"
                    }
                ]
            }
        },
        {
            "table": {
                "width": 250,
                "title": "日期(date、week、month、year)",
                "format": "YYYY-MM-DD",
                "dataIndex": "birthday",
                // "tdEdit": true,
                "tdEditFetchConfig": {
                    "apiName": "login"
                },
                "fieldsConfig": {
                    "type": "date",
                    "placeholder": "请选择..."
                }
            },
            "form": {
                "label": "日期",
                "type": "date",
                "placeholder": "请输入..."
            }
        },
        {
            "table": {
                "width": 300,
                "title": "日期范围下拉(date、week、month、year)",
                "format": "YYYY-MM",
                "dataIndex": "year",
                // "tdEdit": true,
                "tdEditFetchConfig": {
                    "apiName": "login"
                },
                "fieldsConfig": {
                    "placeholder": "请选择...",
                    "type": "month",
                    "showTime": false,
                    "isLouDou": true
                }
            },
            "form": {
                "label": "日期范围下拉",
                "type": "date",
                "placeholder": "请输入..."
            }
        },
        {
            "table": {
                "width": 300,
                "title": "日期范围(date、week、month、year)",
                "dataIndex": "rangeDate",
                "format": "YYYY-MM",
                // "tdEdit": true,
                "tdEditFetchConfig": {
                    "apiName": "login"
                },
                "fieldsConfig": {
                    "type": "rangeDate",
                    "picker": "month",
                    "placeholder": "请选择...",
                    "showTime": false
                }
            },
            "form": {
                "label": "日期范围",
                "type": "rangeDate",
                "placeholder": "请输入..."
            }
        },
        {
            "table": {
                "width": 120,
                "title": "住址",
                "dataIndex": "address"
            },
            "form": {
                "type": "string",
                "placeholder": "请输入..."
            }
        },
        {
            "isInForm": false,
            "table": {
                "width": 120,
                "title": "操作",
                "align": "center",
                "dataIndex": "action",
                "key": "action",
                "fixed": "right",
                "showType": "tile"
            }
        }
    ],
    "tabs": [
        {
            "field": "one",
            "name": "qnnForm",
            "title": "表单",
            "content": "bind:getQnnnFormConfig"
        },
        {
            "field": "one2",
            "name": "qnnForm",
            "title": "表单",
            "content": {
                "fetchConfig": {
                    "apiName": "getBaseCodeTree",
                    "otherParams": {
                        "itemId": "xingzhengquhuadaima"
                    }
                },
                "formConfig": [
                    {
                        "type": "string",
                        "label": "其他",
                        "field": "itemName",
                        "placeholder": "请输入",
                        "required": true
                    }
                ]
            }
        },
        {
            "field": "two",
            "name": "qnnTable",
            "title": "表格",
            "content": {
                antd: {
                    rowKey: "itemId"
                },
                "fetchConfig": {
                    "apiName": "getBaseCodeSelect",
                    otherParams: {
                        itemId: "minzhu"
                    }
                },
                tableTdEdit: true,
                actionBtns: [
                    {
                        name: 'addRow',
                        icon: 'plus',
                        type: 'primary',
                        label: '新增行',
                        detailShow: true,
                        addRowDefaultData: () => ({
                            workId: "1111",
                            itemId: Math.random()
                        })
                    },
                ],
                "formConfig": [
                    {
                        "table": {
                            "title": "流程id",
                            "dataIndex": "workId",
                            "width": 300,
                            "ellipsis": true,

                            tdEdit: true
                        },
                        "form": {
                            "type": "string",
                            "placeholder": "请输入",
                            "required": false
                        }
                    },
                    {
                        isInSearch: true,
                        table: {
                            title: "姓名",
                            dataIndex: "name",
                            children: [
                                {
                                    title: "姓",
                                    dataIndex: "sname",
                                    width: 120,
                                    tdEdit: true,
                                    tdEditFetchConfig: {
                                        apiName: 'login',
                                    },
                                    fieldConfig: {
                                        type: "select",
                                        optionData: [
                                            {
                                                label: "王",
                                                value: "wang"
                                            },
                                            {
                                                label: "张",
                                                value: "zhang"
                                            }
                                        ],
                                        onChange:(e, f, b)=>{
                                            console.log(e,  f, b)
                                        }
                                        // onChange: "bind:snameChange"
                                    },
                                },
                                {
                                    title: "名",
                                    dataIndex: "birthday",
                                    width: 120,
                                    render: "bind:_divide::100::3",
                                },
                                {
                                    title: "辈",
                                    dataIndex: "generation",
                                    width: 120,
                                    tdEdit: true, 
                                    fieldConfig: {
                                        type: "string", 
                                        onBlur:(e, f, b)=>{
                                            console.log(e,  f, b)
                                        }
                                        // onChange: "bind:snameChange"
                                    },
                                }
                            ]
                        },
                        form: {
                            type: "string",
                            placeholder: "请输入..."
                        },
                    },
            
                    {
                        "table": {
                            "title": "标题",
                            "dataIndex": "title",
                            "ellipsis": true,
                            tdEdit: true
                        },
                        "form": {
                            "type": "string",
                            "placeholder": "请输入",
                            "required": false
                        }
                    },
                    {
                        "table": {
                            "title": "发起时间",
                            "dataIndex": "createTime",
                            "format": "YYYY-MM-DD",
                            "ellipsis": true
                        },
                        "form": {
                            "type": "string",
                            "placeholder": "请输入",
                            "required": false
                        }
                    }
                ]
            }
        },
        {
            "field": "diy1",
            "name": "diy1",
            "title": "自定义页面",
            "content": "diy1Com"
        },
        {
            "field": "diy1",
            "name": "diy1",
            "title": "自定义页面",
            "content": "diy1Com"
        },
        {
            "field": "diy1",
            "name": "diy1",
            "title": "自定义页面",
            "content": "diy1Com"
        },
        {
            "field": "diy1",
            "name": "diy1",
            "title": "自定义页面",
            "content": "diy1Com"
        },
        {
            "field": "diy1",
            "name": "diy1",
            "title": "自定义页面",
            "content": "diy1Com"
        },
        {
            "field": "diy1",
            "name": "diy1",
            "title": "自定义页面",
            "content": "diy1Com"
        },
        {
            "field": "diy1",
            "name": "diy1",
            "title": "自定义页面",
            "content": "diy1Com"
        },
        {
            "field": "diy1",
            "name": "diy1",
            "title": "自定义页面",
            "content": "diy1Com"
        }
    ]
}