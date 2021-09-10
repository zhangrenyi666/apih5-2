window.FlowManage = {
    "fetchConfig": {
        "apiName": "getBaseFlowCodeList"
    },
    data: [
        {
            flowId: "0",
            apih5FlowId: "123456",
            apih5FlowName: "流程名字"
        }
    ],
    "antd": {
        rowKey: "flowId"
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
            "label": "新增",
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
    tabs: [
        {
            type: 'qnnForm',
            label: "基本设置",
            field: "baise",
            qnnFormConfig: {
                //所有配置见qnn-form
                formConfig: [
                    {
                        "field": "flowCodeId",
                        "type": "string",
                        "placeholder": "请输入",
                        "addShow": false,
                        "editShow": false
                    },
                    {
                        "type": "string",
                        "placeholder": "请输入",
                        "field": "apih5FlowId",
                        "label": "流程ID"
                    },
                    {
                        "label": "流程名称",
                        "type": "string",
                        "placeholder": "请输入",
                        "field": "apih5FlowName",
                    },
                ]
            }
        },
        {
            type: 'qnnTable',
            label: "字段管理",
            field: 'fields',
            qnnTableConfig: {
                //所有配置见qnn-form
                formConfig: [
                    {
                        table: {},
                        form: {
                            type: 'string',
                            label: '字段名',
                            field: '字段名',
                            placeholder: '请输入',
                            required: true,
                        },
                    }
                ],
                actionBtns: [
                    {
                        "name": "add",
                        "icon": "plus",
                        "type": "primary",
                        "label": "新增",
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
                ]
            }
        },
    ],
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
                "title": "流程ID",
                "dataIndex": "apih5FlowId",
                "key": "apih5FlowId",
            },
            "form": {
                "type": "string",
                "placeholder": "请输入",
                "field": "apih5FlowId",
                "label": "流程ID"
            }
        },
        {
            "isInSearch": true,
            "table": {
                "title": "流程名称",
                "dataIndex": "apih5FlowName",
                "key": "apih5FlowName",
            },
            "form": {
                "type": "string",
                "placeholder": "请输入",
                "field": "apih5FlowName",
            }
        },
        {
            "isInSearch": false,
            "isInForm": false,
            "table": {
                "title": "创建者",
                "dataIndex": "createUserName",
                "key": "createUserName",
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
                "title": "创建时间",
                "dataIndex": "createTime",
                "key": "createTime",
                "format": "YYYY-MM-DD HH:mm:ss",
            },
            "form": {
                "type": "string",
                "placeholder": "请输入",
                "field": "createTime",
                "label": "创建时间"
            }
        },
        {
            "isInForm": false,
            "table": {
                "dataIndex": "action",
                "key": "action",
                showType: "tile",
                title: "编辑",
                "btns": [
                    {
                        label: "编辑",
                        "name": "edit",
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
                ]
            }
        }
    ]
}