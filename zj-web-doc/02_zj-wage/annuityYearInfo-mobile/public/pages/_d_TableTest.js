window.tableTest = {
    "antd": {
        "rowKey": "id",
        "size": "small", 
    },
    "drawerConfig": {
        "width": "800px"
    },
    "firstRowIsSearch": true,
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
                },
                {
                    "name": "diy2",
                    "type": "primary",
                    "label": "tab1隐藏",
                    "field": "hideSub",
                    "isValidate": false,
                    "hide": "bind:hideSub",
                    "icon": "diy1Com"
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
        "apiName": "getSysUserListByBg",
        "otherParams": {
            "name": "张三1"
        },
        "params": {
            "id": "id1"
        }
    },
    "formConfig": [
        {
            "table": {
                "width": 120,
                "dataIndex": "realName",
                "title": "姓名1",
                "noHaveSearchInput": false,
                "type": "string",
                "fieldsConfig": {
                    "field": "search"
                },
                "filter": false,
                "fixed": "left"
            },
            "form": {
                "type": "string"
            },
            "_id": "0",
            "isInTable": true,
            "isInSearch": false
        },
        {
            "table": {
                "title": "test",
                "dataIndex": "test",
                "width": 120,
                "type": "string",
                "filter": false,
                "noHaveSearchInput": false
            },
            "form": {
                "type": "string"
            },
            "_id": "1",
            "isInSearch": false,
            "isInTable": true
        },
        {
            "table": {
                "width": 120,
                "dataIndex": "hobby",
                "title": "爱好",
                "noHaveSearchInput": false,
                "type": "select",
                "filter": false
            },
            "form": {
                "type": "select"
            },
            "_id": "2",
            "isInSearch": false,
            "isInTable": true
        },
        {
            "table": {
                "width": 250,
                "dataIndex": "birthday",
                "format": "YYYY-MM-DD",
                "title": "月份",
                "noHaveSearchInput": false,
                "type": "string",
                "fieldsConfig": {
                    "type": "month",
                    "placeholder": "请选择..."
                },
                "filter": false
            },
            "form": {
                "type": "string"
            },
            "_id": "3",
            "isInSearch": false,
            "isInTable": true
        },
        {
            "table": {
                "width": 300,
                "title": "日期范围(date、week、month、year)",
                "dataIndex": "rangeDate",
                "format": "YYYY-MM",
                "fieldsConfig": {
                    "type": "rangeDate",
                    "placeholder": "请选择..."
                },
                "filter": false,
                "noHaveSearchInput": false
            },
            "form": {},
            "_id": "4",
            "isInSearch": false,
            "isInTable": true
        },
        {
            "table": {
                "width": 300,
                "dataIndex": "year",
                "format": "YYYY-MM",
                "title": "日期下拉(date、week、month、year)",
                "noHaveSearchInput": false,
                "type": "string",
                "fieldsConfig": {
                    "type": "date",
                    "placeholder": "请选择...",
                    "scope": true
                },
                "filter": false
            },
            "form": {
                "type": "string"
            },
            "_id": "5",
            "isInSearch": false,
            "isInTable": true
        },
        {
            "table": {
                "width": 120,
                "title": "住址",
                "dataIndex": "address",
                "filter": false,
                "noHaveSearchInput": false
            },
            "form": {},
            "_id": "6",
            "isInSearch": false,
            "isInTable": true
        },
        {
            "isInForm": false,
            "table": {
                "width": 120,
                "title": "操作",
                "align": "center",
                "dataIndex": "action",
                "key": "action",
                "showType": "bubble",
                "fixed": "right",
                "filter": false,
                "noHaveSearchInput": true
            },
            "_id": "7",
            "isInTable": true
        }
    ],
    "type": "qnnTable",
    "name": "测试表格",
    "isShowRowSelect": true,
    "desc": "1234561122222",
    "searchBtnsStyle": "block"
}