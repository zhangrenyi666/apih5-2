window.FlowTypePage ={
    "fetchConfig": {
        "apiName": "getBaseFlowCodeList"
    },
    antd: {
        rowKey: 'flowCodeId',
        size: 'small'
    },
    "drawerConfig": {
        "width": "600px"
    },
    "paginationConfig": {
        "position": "bottom"
    },
    "isShowRowSelect": true,
    "actionBtns": [
        {
            "name": "add",
            "icon": "plus",
            "type": "primary",
            "label": "导入",
            "formBtns": [
                {
                    "name": "cancel",
                    "type": "dashed",
                    "label": "取消"
                },
                {
                    "name": "submit",
                    "type": "primary",
                    "label": "提交",
                    "fetchConfig": {
                        "apiName": "baseFlowCodeImport"
                    }
                }
            ]
        },
        {
            "name": "del",
            "icon": "delete",
            "type": "danger",
            "label": "删除",
            "fetchConfig": {
                "apiName": "batchDeleteUpdateBaseFlowCode"
            }
        }
    ],
    "formItemLayoutSearch": {
        "labelCol": {
            "xs": {
                "span": 24
            },
            "sm": {
                "span": 6
            }
        },
        "wrapperCol": {
            "xs": {
                "span": 24
            },
            "sm": {
                "span": 18
            }
        }
    },
    "formConfig": [
        {
            "isInTable": false,
            "form": {
                "field": "flowCodeId",
                "type": "string",
                "placeholder": "请输入",
                "addShow": false,
                "editShow": false
            }
        },
        {
            "isInSearch": false,
            "table": {
                "title":'流程ID',
                "dataIndex": "apih5FlowId",
                "key": "apih5FlowId",
                "fieldsConfig": {
                    "type": "string",
                    "field": "apih5FlowId"
                }
            },
            "form": {
                "type": "string",
                "placeholder": "请输入",
                "addShow": false,
                "editShow": true,
                "editDisabled": true,
                "field": "apih5FlowId",
                "label": "流程ID"
            }
        },
        {
            "isInSearch": true,
            "table": {
                "title":'流程名称',
                "dataIndex": "apih5FlowName",
                "key": "apih5FlowName",
                "fieldsConfig": {
                    "type": "string",
                    "field": "apih5FlowName"
                }
            },
            "form": {
                "type": "string",
                "placeholder": "请输入",
                "addShow": false,
                "editShow": true,
                "field": "apih5FlowName",
                "label": "流程名称"
            }
        },
        {
            "isInSearch": false,
            "table": {
                "title":'apih5NodeId',
                "dataIndex": "apih5NodeId",
                "key": "apih5NodeId",
                "fieldsConfig": {
                    "type": "string",
                    "field": "apih5NodeId"
                }
            },
            "form": {
                "type": "string",
                "placeholder": "请输入",
                "addShow": false,
                "editShow": true,
                "field": "apih5NodeId",
                "label": "节点ID"
            }
        },
        {
            "isInSearch": false,
            "table": {
                "title": '节点名称',
                "dataIndex": "apih5NodeName",
                "key": "apih5NodeName",
                "fieldsConfig": {
                    "type": "string",
                    "field": "apih5NodeName"
                }
            },
            "form": {
                "type": "string",
                "placeholder": "请输入",
                "addShow": false,
                "editShow": true,
                "field": "apih5NodeName",
                "label": "节点名称"
            }
        },
        {
            "isInSearch": false,
            "isInForm": false,
            "table": {
                "title":'创建者',
                "dataIndex": "createUserName",
                "key": "createUserName",
                "fieldsConfig": {
                    "type": "string",
                    "field": "createUserName"
                }
            },
            "form": {
                "type": "string",
                "placeholder": "请输入",
                "field": "createUserName",
                "label": "创建者"
            }
        },
        {
            "isInSearch": false,
            "isInForm": false,
            "table": {
                "title": '创建时间',
                "dataIndex": "createTime",
                "key": "createTime",
                "format": "YYYY-MM-DD HH:mm:ss",
                "fieldsConfig": {
                    "type": "string",
                    "field": "createTime"
                }
            },
            "form": {
                "type": "string",
                "placeholder": "请输入",
                "field": "createTime",
                "label": "创建时间"
            }
        },
        {
            "isInTable": false,
            "form": {
                "label": "导入流程xml",
                "field": "xmlFileList",
                "type": "files",
                "accept": ".xml",
                "initialValue": [],
                "addShow": true,
                "editShow": false,
                "fetchConfig": {
                    // "apiName": "http://weixin.fheb.cn:99/apiwoa/upload",
                    apiName: window.configs.domain + 'upload',
                }
            }
        },
        {
            "isInForm": false,
            "table": {
                "dataIndex": "action",
                "key": "action",
                title: '操作',
                width:80,
                align: 'center',
                showType: "tile",
                "btns": [
                    {
                        "name": "edit",
                        label:'修改',
                        "formBtns": [
                            {
                                "name": "cancel",
                                "type": "dashed",
                                "label": "取消"
                            },
                            {
                                "name": "submit",
                                "type": "primary",
                                "label": "提交",
                                "fetchConfig": {
                                    "apiName": "updateBaseFlowCode"
                                }
                            }
                        ]
                    }
                ],
                "fieldsConfig": {
                    "type": "string",
                    "field": "action"
                }
            }
        }
    ]
}