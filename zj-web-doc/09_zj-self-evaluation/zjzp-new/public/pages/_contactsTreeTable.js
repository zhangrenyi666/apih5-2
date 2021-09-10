window.contactsTreeTable = {
    "fetchConfig": {
        "apiName": "getSysDepartmentListByCondition",
        "otherParams": {
            "departmentParentId": "0"
        }
    },
    "antd": {
        "size": "small",
        "rowKey": "departmentId",
        "scroll": {
            "y": 500
        }
    },
    "paginationConfig": {
        "position": "bottom"
    },
    "isShowRowSelect": true,
    "actionBtns": [
        {
            "name": "add",
            "field": "add",
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
                        "apiName": "addSysDepartment"
                    }
                }
            ]
        },
        {
            "name": "del",
            "icon": "delete",
            "type": "danger",
            "label": "删除",
            "isRefreshTable": false,
            "onClick": "bind:delCb",
            "fetchConfig": {
                "apiName": "batchDeleteUpdateSysDepartment"
            }
        }
    ],
    "drawerShowToggle": "bind:drawerShowToggle",
    "formConfig": [
        {
            "isInTable": false,
            "form": {
                "field": "departmentId",
                "type": "string",
                "placeholder": "请输入",
                "hide": true
            }
        },
        {
            "isInSearch": false,
            "table": {
                "width": 350,
                "title": "机构名称",
                "dataIndex": "departmentName",
                "key": "departmentName",
                "filter": true,
                "fixed": "left",
                "onClick": "bind:expandChild"
            },
            "form": {
                "type": "string",
                "placeholder": "请输入",
                "required": true
            }
        },
        {
            "isInTable": false,
            "table": {
                "title": "上级机构",
                "dataIndex": "deparentParentName",
                "key": "上级机构"
            },
            "form": {
                "addDisabled": true,
                "editDisabled": true,
                "type": "string",
                "label": "上级机构",
                "field": "deparentParentName"
            }
        },
        {
            "isInTable": false,
            "form": {
                "addDisabled": true,
                "editDisabled": true,
                "required": true,
                "type": "string",
                "label": "上级机构id",
                "field": "departmentParentId",
                "initialValue": "0",
                "hide": true
            }
        },
        {
            "table": {
                "width": 200,
                "tooltip": 20,
                "title": "所属公司",
                "dataIndex": "companyName",
                "key": "label"
            },
            "form": {
                "type": "string",
                "label": "所属公司",
                "field": "companyName",
                "addDisabled": true,
                "editDisabled": true
            }
        },
        {
            "isInForm": false,
            "table": {
                "width": 200,
                "tooltip": 20,
                "title": "所属项目",
                "dataIndex": "projectName",
                "key": "projectName"
            },
            "form": {
                "required": true,
                "type": "treeSelect",
                "label": "所属项目",
                "field": "project",
                "addDisabled": true,
                "editDisabled": true,
                "treeSelectOption": {
                    "selectType": "1",
                    "fetchConfig": {
                        "apiName": "getSysDepartmentAllTree",
                        "otherParams": {}
                    }
                }
            }
        },
        {
            "table": {
                "width": 100,
                "title": "机构类型",
                "dataIndex": "departmentFlagName",
                "key": "departmentFlagName",
                "filter": true
            },
            "form": {
                "field": "departmentFlag",
                "type": "select",
                "placeholder": "请选择",
                "optionConfig": {
                    "label": "itemName",
                    "value": "itemId",
                    "children": "children"
                },
                "fetchConfig": {
                    "apiName": "getBaseCodeSelect",
                    "otherParams": {
                        "itemId": "departmentType"
                    }
                }
            }
        },
        {
            "isInForm": false,
            "table": {
                "width": 500,
                "tooltip": 40,
                "title": "机构全路径",
                "dataIndex": "departmentPathName",
                "key": "departmentPathName"
            },
            "form": {
                "type": "string",
                "placeholder": "请输入"
            }
        },
        {
            "table": {
                "width": 80,
                "title": "排序",
                "dataIndex": "sort"
            },
            "form": {
                "type": "number",
                "placeholder": "请输入",
                "required": false
            }
        },
        {
            "isInForm": false,
            "table": {
                "width": 120,
                "dataIndex": "action",
                "key": "action",
                "showType": "tile",
                "title": "操作",
                "fixed": "right",
                "align": "center",
                "btns": [
                    {
                        "label": "编辑",
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
                                "isRefreshTable": false,
                                "onClick": "bind:editChildCb",
                                "paramsFormat": "bind:_addRowData::{\"departmentParentId\":\"departmentId\"}",
                                "fetchConfig": {
                                    "apiName": "updateSysDepartment"
                                }
                            }
                        ]
                    },
                    {
                        "label": "新增子项",
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
                                "isRefreshTable": false,
                                "onClick": "bind:addChildCb",
                                "paramsFormat": "bind:_addRowData::{\"departmentParentId\":\"departmentId\"}",
                                "fetchConfig": {
                                    "apiName": "addSysDepartment"
                                }
                            }
                        ]
                    }
                ]
            }
        }
    ]
}