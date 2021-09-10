window.testTable ={
    "style": {
        "height": "100%"
    },
    "antd": {
        "size": "small",
        rowKey:"name"
    },
    "formConfig": [
        {
            "isInSearch": true,
            "table": {
                "title": {
                    "type": "span",
                    "key": null,
                    "ref": null,
                    "props": {
                        "dangerouslySetInnerHTML": {
                            "__html": "姓名"
                        }
                    },
                    "_owner": null,
                    "_store": {}
                },
                "dataIndex": "name",
                "key": "name",
                "fieldsConfig": {
                    "type": "string",
                    "field": "name"
                }
            },
            "form": {
                "type": "string",
                "placeholder": "请输入",
                "field": "name",
                "label": "姓名"
            }
        },
        {
            "isInSearch": true,
            "table": {
                "title": {
                    "type": "span",
                    "key": null,
                    "ref": null,
                    "props": {
                        "dangerouslySetInnerHTML": {
                            "__html": "年纪"
                        }
                    },
                    "_owner": null,
                    "_store": {}
                },
                "dataIndex": "age",
                "key": "age",
                "fieldsConfig": {
                    "type": "string",
                    "field": "age"
                }
            },
            "form": {
                "type": "string",
                "placeholder": "请输入",
                "field": "age",
                "label": "年纪"
            }
        },
        {
            "isInSearch": true,
            "table": {
                "title": {
                    "type": "span",
                    "key": null,
                    "ref": null,
                    "props": {
                        "dangerouslySetInnerHTML": {
                            "__html": "爱好"
                        }
                    },
                    "_owner": null,
                    "_store": {}
                },
                "dataIndex": "sex",
                "key": "sex",
                "fieldsConfig": {
                    "type": "string",
                    "field": "sex"
                }
            },
            "form": {
                "type": "string",
                "placeholder": "请输入",
                "field": "sex",
                "label": "爱好"
            }
        },
        {
            "_id": 4,
            "isInTable": true,
            "isInForm": true,
            "isInSearch": false,
            "table": {
                "dataIndex": "test001t",
                "title": {
                    "type": "span",
                    "key": null,
                    "ref": null,
                    "props": {
                        "dangerouslySetInnerHTML": {
                            "__html": "test001t"
                        }
                    },
                    "_owner": null,
                    "_store": {}
                },
                "align": "left",
                "filter": false,
                "fieldsConfig": {
                    "type": "string",
                    "field": "test001t"
                }
            },
            "form": {
                "type": "string",
                "field": "test001t",
                "label": "test001t",
                "disabled": false,
                "editDisabled": false,
                "addDisabled": false
            }
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
                    }
                }
            ]
        }
    ]
}