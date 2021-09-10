window.testTable ={
    "style": {
        "height": "100%"
    },
    "antd": {
        "size": "small",
        "rowKey": "itemName"
    },
    "formConfig": [
        {
            "_id": 1,
            "isInTable": true,
            "isInForm": true,
            "isInSearch": false,
            "table": {
                "dataIndex": "itemName",
                "title": {
                    "type": "span",
                    "key": null,
                    "ref": null,
                    "props": {
                        "dangerouslySetInnerHTML": {
                            "__html": "itemName"
                        }
                    },
                    "_owner": null,
                    "_store": {}
                },
                "align": "left",
                "filter": false,
                "fieldsConfig": {
                    "type": "string",
                    "field": "itemName"
                }
            },
            "form": {
                "type": "string",
                "field": "itemName",
                "label": "itemName",
                "disabled": false,
                "editDisabled": false,
                "addDisabled": false
            }
        },
        {
            "_id": 2,
            "isInTable": true,
            "isInForm": true,
            "isInSearch": false,
            "table": {
                "dataIndex": "itemId",
                "title": {
                    "type": "span",
                    "key": null,
                    "ref": null,
                    "props": {
                        "dangerouslySetInnerHTML": {
                            "__html": "itemId"
                        }
                    },
                    "_owner": null,
                    "_store": {}
                },
                "align": "left",
                "filter": false,
                "fieldsConfig": {
                    "type": "string",
                    "field": "itemId"
                }
            },
            "form": {
                "type": "string",
                "field": "itemId",
                "label": "itemId",
                "disabled": false,
                "editDisabled": false,
                "addDisabled": false
            }
        },
        {
            "_id": 3,
            "isInTable": true,
            "isInForm": true,
            "isInSearch": false,
            "table": {
                "dataIndex": "aa",
                "title": {
                    "type": "span",
                    "key": null,
                    "ref": null,
                    "props": {
                        "dangerouslySetInnerHTML": {
                            "__html": "aa"
                        }
                    },
                    "_owner": null,
                    "_store": {}
                },
                "align": "left",
                "filter": false,
                "fieldsConfig": {
                    "type": "string",
                    "field": "aa"
                }
            },
            "form": {
                "type": "string",
                "field": "aa",
                "label": "aa",
                "disabled": false,
                "editDisabled": false,
                "addDisabled": false
            }
        },
        {
            "_id": 4,
            "isInTable": true,
            "isInForm": true,
            "isInSearch": true,
            "table": {
                "dataIndex": "dd",
                "title": {
                    "type": "span",
                    "key": null,
                    "ref": null,
                    "props": {
                        "dangerouslySetInnerHTML": {
                            "__html": "ddd"
                        }
                    },
                    "_owner": null,
                    "_store": {}
                },
                "align": "left",
                "filter": false,
                "fieldsConfig": {
                    "type": "string",
                    "field": "dd"
                }
            },
            "form": {
                "type": "string",
                "field": "dd",
                "label": "ddd",
                "disabled": false,
                "editDisabled": false,
                "addDisabled": false
            }
        },
        {
            "_id": 5,
            "isInTable": true,
            "isInForm": true,
            "isInSearch": false,
            "table": {
                "dataIndex": "qq",
                "title": {
                    "type": "span",
                    "key": null,
                    "ref": null,
                    "props": {
                        "dangerouslySetInnerHTML": {
                            "__html": "qqq"
                        }
                    },
                    "_owner": null,
                    "_store": {}
                },
                "align": "right",
                "filter": false,
                "fieldsConfig": {
                    "type": "string",
                    "field": "qq"
                }
            },
            "form": {
                "type": "string",
                "field": "qq",
                "label": "qqq",
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
                    },
                    "field": "submit_0"
                }
            ]
        }
    ],
    "fetchConfig": {
        "apiName": "getBaseCodeList",
        "params": ""
    }
}