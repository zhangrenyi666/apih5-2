window.contactsTreeTable = {
    "fetchConfig": {
        "apiName": "getSysDepartmentAllTree"
    },

    "antd": {
        rowKey: "value",
        scroll: {
            y: 500
        }
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
                        "apiName": "add"
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
                "apiName": "del"
            }
        }
    ],

    "formConfig": [
        {
            "form": {
                "field": "value",
                "type": "string",
                "placeholder": "请输入",
                "hide": true,
            }
        },
        {
            "isInSearch": false,
            "table": {
                "title": "机构名称",
                "dataIndex": "title",
                "key": "title",
                filter: true,
            },
            "form": {
                "type": "string",
                "placeholder": "请输入",
                required: true
            }
        },
        {
            "table": {
                "title": "所属公司",
                "dataIndex": "label",
                "key": "label",
                filter: true,
            }
            ,form: {
                required: true,
                type: 'treeSelect',
                label: '所属公司',
                field: 'sysUserGroupInfoList',
                treeSelectOption: {
                    selectType: "1",
                    search: true,
                    //配置参照oa拉人组件配置
                    fetchConfig: {
                        apiName: 'getSysDepartmentAllTree',otherParams: {}
                    },
                },
            }
        },
        // {
        //     "table": {
        //         "title": "所属公司",
        //         "dataIndex": "label",
        //         "key": "label",
        //         filter: true,
        //     },
        //     "form": {
        //         "type": "string",
        //         "placeholder": "请输入",
        //         required:true
        //     }
        // },
        {
            "table": {
                "title": "机构类型",
                "dataIndex": "机构类型",
                "key": "机构类型",
                filter: true,
            },
            "form": {
                "field": "机构类型",
                "type": "select",
                "placeholder": "请选择",
                optionData: [
                    {
                        label: "集团",
                        value: "0",
                    },
                    {
                        label: "公司",
                        value: "1",
                    },
                    {
                        label: "部门",
                        value: "2",
                    },
                    {
                        label: "项目",
                        value: "3",
                    },
                    {
                        label: "小组",
                        value: "4",
                    },
                    {
                        label: "虚拟层次",
                        value: "5",
                    }
                ]
            }
        },
        {
            isInForm: false,
            "table": {
                "title": "机构全路径",
                "dataIndex": "label",
                "key": "label",
                filter: true,
            },
            "form": {
                "type": "string",
                "placeholder": "请输入",
            }
        },

        {
            table: {
                title: '排序',
                dataIndex: 'sort',
            }
            ,form: {
                type: 'number',
                placeholder: '请输入',
                required: false,
            }
        },
        {
            "isInForm": false,
            "table": {
                "dataIndex": "action",
                "key": "action",
                showType: "tile",
                title: "操作",
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
                                paramsFormat: "bind:_addRowData::" + JSON.stringify({ parentValue: 'value' }), 
                                "fetchConfig": {
                                    "apiName": "add"
                                }
                            }
                        ]
                    },
                    {
                        label: "新增子项",
                        "name": "add",
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
                                paramsFormat: "bind:_addRowData::" + JSON.stringify({ parentValue: 'value' }), 
                                "fetchConfig": {
                                    "apiName": "add"
                                }
                            }
                        ]
                    },
                ]
            }
        }
    ]
}