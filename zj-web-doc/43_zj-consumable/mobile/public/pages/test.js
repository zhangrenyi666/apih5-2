window.testTable = {
    "style": {
        "height": "100%"
    },
    "antd": {
        "size": "small",
        "rowKey": "itemId"
    },
    "formConfig": [
        {
            "table": {
                "width": 100,
                "dataIndex": "itemName",
                "btns": false,
                "title": "姓名",
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
                "width": 100,
                "dataIndex": "itemId",
                "btns": false,
                "title": "年纪",
                "filter": false,
                "noHaveSearchInput": false
            },
            "form": {},
            "_id": "1",
            "isInTable": true,
            "isInSearch": true,
            "isInForm": false,
            "_edit": false
        },
        {
            "table": {
                "width": 120,
                "dataIndex": "createTime",
                "title": "创建名称",
                "filter": false,
                "noHaveSearchInput": false,
                "type": "string",
                "format": "YYYY-MM-DD"
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
            "_id": "3",
            "isInForm": false,
            "isInTable": false,
            "isInSearch": false,
            "table": {
                "title": "操作",
                "align": "center",
                "dataIndex": "action",
                "key": "action"
            }
        },
        {
            "isInTable": false,
            "isInForm": true,
            "isInSearch": false,
            "table": {},
            "form": {
                "field": "newField_4_1598956454044",
                "label": "未命名",
                "type": "string"
            },
            "_id": "newField_4_1598956454044"
        }
    ],
    "title": "测试使用",
    "actionBtns": [
        {
            "name": "add",
            "icon": "plus",
            "type": "primary",
            "label": "新增",
            "formBtns": [
                {
                    "name": "submit",
                    "type": "primary",
                    "label": "提交",
                    "fetchConfig": {
                        "apiName": "submit"
                    },
                    "field": "submit_0"
                }
            ]
        }
    ],
    "fetchConfig": {
        "apiName": "getBaseCodeSelect",
        "otherParams": {
            "itemId": "minzhu"
        }
    },
    "type": "qnnTable",
    "name": "民族字典表",
    "isShowRowSelect": false
}