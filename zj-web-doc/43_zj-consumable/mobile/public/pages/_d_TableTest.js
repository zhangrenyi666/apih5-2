window.tableTest = {
    "antd": {
        "rowKey": "itemId",
        "size": "small",
        "title": "标题测试122"
    },
    "drawerConfig": {
        "width": "800px"
    },
    "firstRowIsSearch": false,
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
                    "label": "提交",
                    "fetchConfig": {
                        "apiName": "login",
                        "delParams": []
                    },
                    "field": "addSubmitBtn",
                    "isValidate": true
                }
            ]
        },
        {
            "field": "delBtn",
            "name": "del",
            "icon": "delete",
            "fetchConfig": {
                "apiName": "submit",
                "delParams": []
            },
            "type": "danger",
            "label": "删除"
        },
        {
            "field": "delBtn",
            "name": "diy",
            "icon": "diy1Com",
            "type": "danger",
            "label": "自定义icon"
        }
    ],
    "formItemLayout": {
        "labelCol": {
            "xs": {
                "span": 24
            },
            "sm": {
                "span": 4
            }
        },
        "wrapperCol": {
            "xs": {
                "span": 24
            },
            "sm": {
                "span": 16
            }
        }
    },
    "fetchConfig": {
        "apiName": "getBaseCodeSelect",
        "otherParams": {
            "itemId": "minzhu"
        }
    },
    "type": "qnnTable",
    "name": "测试表格",
    "isShowRowSelect": true,
    "desc": "描述测试",
    "searchBtnsStyle": "block",
    "searchType": "isInSearch",
    "formConfig": [
        {
            "table": {
                "width": 100,
                "dataIndex": "realName",
                "btns": false,
                "title": "姓名1",
                "filter": false,
                "noHaveSearchInput": false,
                "type": "string",
                "fieldsConfig": {
                    "field": "search"
                }
            },
            "form": {
                "type": "string"
            },
            "_id": "0",
            "isInTable": true,
            "isInSearch": true,
            "isInForm": false,
            "_edit": false
        },
        {
            "table": {
                "width": 120,
                "dataIndex": "ext10",
                "title": "爱好0",
                "filter": false,
                "noHaveSearchInput": false,
                "type": "string"
            },
            "form": {
                "type": "string"
            },
            "_id": "1",
            "isInSearch": true,
            "isInTable": true,
            "isInForm": false,
            "_edit": false
        },
        {
            "table": {
                "width": 100,
                "dataIndex": "delFlag",
                "title": "test",
                "filter": false,
                "noHaveSearchInput": false,
                "type": "string"
            },
            "form": {
                "type": "string"
            },
            "_id": "2",
            "isInSearch": true,
            "isInTable": true,
            "isInForm": false,
            "_edit": false
        },
        {
            "table": {
                "width": 250,
                "dataIndex": "modifyTime",
                "btns": false,
                "format": "YYYY-MM-DD",
                "title": "月份",
                "filter": false,
                "noHaveSearchInput": false,
                "type": "string",
                "fieldsConfig": {
                    "type": "month",
                    "placeholder": "请选择..."
                }
            },
            "form": {
                "type": "string"
            },
            "_id": "3",
            "isInSearch": true,
            "isInTable": true,
            "isInForm": false,
            "_edit": false
        },
        {
            "table": {
                "width": 300,
                "dataIndex": "createTime",
                "format": "YYYY-MM",
                "title": "日期下拉(date、week、month、year)",
                "filter": false,
                "noHaveSearchInput": false,
                "type": "string",
                "fieldsConfig": {
                    "type": "date",
                    "placeholder": "请选择...",
                    "scope": true
                }
            },
            "form": {
                "type": "string"
            },
            "_id": "4",
            "isInSearch": true,
            "isInTable": true,
            "isInForm": false,
            "_edit": false
        },
        {
            "table": {
                "width": 120,
                "dataIndex": "flowName",
                "title": "住址",
                "filter": false,
                "noHaveSearchInput": false,
                "type": "string"
            },
            "form": {
                "type": "string"
            },
            "_id": "5",
            "isInSearch": true,
            "isInTable": true,
            "isInForm": false,
            "_edit": false
        },
        {
            "_id": "6",
            "isInForm": false,
            "isInTable": true,
            "isInSearch": false,
            "table": {
                "title": "操作",
                "align": "center",
                "dataIndex": "action",
                "key": "action",
                "fixed": "right",
                "width": 120,
                "btns": [
                    {
                        "field": "xiangqing",
                        "label": "详情",
                        "name": "detail"
                    },
                    {
                        "field": "bianji",
                        "label": "编辑",
                        "name": "edit",
                        "formBtns": []
                    }
                ],
                "showType": "tile"
            },
            "_edit": false,
            "form": {}
        },
        {
            "isInTable": false,
            "isInForm": true,
            "isInSearch": false,
            "table": {},
            "form": {
                "colon": true,
                "addDisabled": false,
                "editDisabled": false,
                "addShow": true,
                "editShow": true,
                "detailShow": true,
                "spanForm": 12,
                "offsetForm": 0,
                "pullJoin": true,
                "pushJoin": true,
                "field": "field",
                "label": "姓名姓名姓名姓名姓名姓名姓名姓名",
                "type": "string",
                "formItemWrapperStyle": {
                    "transform": 0
                },
                "required": true,
                "isUrlParams": false,
                "formItemLayoutForm": {
                    "labelCol": {
                        "xs": {
                            "span": 24
                        },
                        "sm": {
                            "span": 21
                        }
                    },
                    "wrapperCol": {
                        "xs": {
                            "span": 24
                        },
                        "sm": {
                            "span": 3
                        }
                    }
                }
            },
            "_id": "7",
            "_edit": true
        },
        {
            "isInTable": false,
            "isInForm": true,
            "isInSearch": false,
            "table": {},
            "form": {
                "colon": true,
                "addDisabled": false,
                "editDisabled": false,
                "addShow": true,
                "editShow": true,
                "detailShow": true,
                "spanForm": 12,
                "offsetForm": 0,
                "pullJoin": true,
                "pushJoin": true,
                "field": "age",
                "label": "年纪 ",
                "type": "number",
                "formItemWrapperStyle": {
                    "transform": 0
                },
                "formItemLayout": {
                    "labelCol": {
                        "xs": {
                            "span": 24
                        },
                        "sm": {
                            "span": 8
                        }
                    },
                    "wrapperCol": {
                        "xs": {
                            "span": 24
                        },
                        "sm": {
                            "span": 16
                        }
                    }
                }
            },
            "_id": "8",
            "_edit": false
        },
        {
            "isInTable": false,
            "isInForm": true,
            "isInSearch": false,
            "table": {},
            "form": {
                "colon": true,
                "addDisabled": false,
                "editDisabled": false,
                "addShow": true,
                "editShow": true,
                "detailShow": true,
                "spanForm": 24,
                "offsetForm": 0,
                "pullJoin": true,
                "pushJoin": true,
                "field": "minzhu",
                "label": "民族",
                "type": "select",
                "formItemWrapperStyle": {
                    "transform": 0
                },
                "fetchConfig": {
                    "apiName": "getBaseCodeSelect",
                    "params": {
                        "name": "zs"
                    },
                    "otherParams": {
                        "itemId": "minzhu"
                    }
                },
                "optionConfig": {
                    "label": "itemName",
                    "value": "itemId",
                    "children": "children"
                },
                "formItemLayoutForm": {
                    "labelCol": {
                        "xs": {
                            "span": 24
                        },
                        "sm": {
                            "span": 4
                        }
                    },
                    "wrapperCol": {
                        "xs": {
                            "span": 24
                        },
                        "sm": {
                            "span": 20
                        }
                    }
                }
            },
            "_id": "9",
            "_edit": false
        },
        {
            "isInTable": false,
            "isInForm": true,
            "isInSearch": false,
            "table": {},
            "form": {
                "colon": true,
                "addDisabled": false,
                "editDisabled": false,
                "addShow": true,
                "editShow": true,
                "detailShow": true,
                "spanForm": 24,
                "offsetForm": 0,
                "pullJoin": true,
                "pushJoin": true,
                "field": "tc",
                "label": "特长",
                "type": "textarea",
                "formItemWrapperStyle": {
                    "transform": 0
                },
                "formItemLayoutForm": {
                    "labelCol": {
                        "xs": {
                            "span": 24
                        },
                        "sm": {
                            "span": 4
                        }
                    },
                    "wrapperCol": {
                        "xs": {
                            "span": 24
                        },
                        "sm": {
                            "span": 20
                        }
                    }
                }
            },
            "_id": "10",
            "_edit": false
        }
    ]
}