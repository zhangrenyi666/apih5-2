window.contactsTreeTable = {
    "fetchConfig": {
        "apiName": "getSysDepartmentListByCondition",
        otherParams: {
            departmentParentId: "0"
        }
    },
    // data: [
    //     {
    //         departmentName: "测试12555",
    //         departmentId:"001"
    //     }
    // ],
    "antd": {
        size: "small",
        rowKey: "departmentId",
        scroll: {
            y: 500
        }
    },
    "paginationConfig": {
        "position": "bottom"
    },
    "isShowRowSelect": true,
    "actionBtns": [{
        "name": "add",
        "field": "add",
        "icon": "plus",
        "type": "primary",
        "label": "新增",
        "formBtns": [{
            "name": "cancel",
            "type": "dashed",
            "label": "取消"
        }, {
            "name": "submit",
            "type": "primary",
            "label": "提交",
            "fetchConfig": {
                "apiName": "addSysDepartment"
            }
        }]
    }, {
        "name": "del",
        "icon": "delete",
        "type": "danger",
        "label": "删除",
        isRefreshTable: false,
        onClick: "bind:delCb",
        "fetchConfig": {
            "apiName": "batchDeleteUpdateSysDepartment"
        }
    }],
    drawerShowToggle: "bind:drawerShowToggle",
    "formConfig": [{
        isInTable: false,
        "form": {
            "field": "departmentId",
            "type": "string",
            "placeholder": "请输入",
            "hide": true
        }
    }, {
        "isInSearch": false,
        "table": {
            width: 350,
            "title": "机构名称",
            "dataIndex": "departmentName",
            "key": "departmentName",
            filter: true,
            fixed: "left",
            onClick: "bind:expandChild"
        },
        "form": {
            "type": "string",
            "placeholder": "请输入",
            required: true
        }
    }, {
        isInTable: false,
        "table": {
            "title": "上级机构",
            "dataIndex": "deparentParentName",
            "key": "上级机构" // filter: true,

        },
        form: {
            addDisabled: true,
            editDisabled: true,
            // required: true,
            type: 'string',
            label: '上级机构',
            field: 'deparentParentName'
        }
    }, {
        isInTable: false,
        isInTable: false,
        form: {
            addDisabled: true,
            editDisabled: true,
            required: true,
            type: 'string',
            label: '上级机构id',
            field: 'departmentParentId',
            initialValue: "0",
            hide: true
        }
    }, // {
    //     isInTable: false,
    //     "table": {
    //         "title": "上级机构",
    //         "dataIndex": "deparentParentName",
    //         "key": "上级机构",
    //         filter: true,
    //     }
    //     ,form: {
    //         addDisabled: true,
    //         editDisabled: true,
    //         required: true,
    //         type: 'treeSelect',
    //         label: '上级机构',
    //         field: 'deparentParent',
    //         initialValue: [
    //             {
    //                 value: "9999999999",
    //                 label: "顶层节点",
    //                 type: "1"
    //             }
    //         ],
    //         treeSelectOption: {
    //             selectType: "1",
    //             search: true,
    //             //配置参照oa拉人组件配置
    //             fetchConfig: {
    //                 apiName: 'getSysDepartmentAllTree',otherParams: {}
    //             },
    //         },
    //     }
    // },
    {
        "table": {
            width: 200,
            tooltip: 20,
            "title": "所属公司",
            "dataIndex": "companyName",
            "key": "label" // filter: true,

        },
        form: {
            type: 'string',
            label: '所属公司',
            field: 'companyName',
            addDisabled: true,
            editDisabled: true,
            hide: function (props) {
                return props.clickCb.rowInfo.field === 'add';
            }
        }
    }, {
        isInForm: false,
        "table": {
            width: 200,
            tooltip: 20,
            "title": "所属项目",
            "dataIndex": "projectName",
            "key": "projectName" // filter: true,

        },
        form: {
            required: true,
            type: 'treeSelect',
            label: '所属项目',
            field: 'project',
            addDisabled: true,
            editDisabled: true,
            // hide: function (props) {
            //     return props.clickCb.rowInfo.field === 'add'
            // },
            treeSelectOption: {
                selectType: "1",
                // search: true,
                //配置参照oa拉人组件配置
                fetchConfig: {
                    apiName: 'getSysDepartmentAllTree',
                    otherParams: {}
                }
            }
        }
    }, {
        "table": {
            width: 100,
            "title": "机构属性",
            "dataIndex": "departmentFlagName",
            "key": "departmentFlagName",
            filter: true
        },
        "form": {
            "field": "departmentFlag",
            "type": "select",
            "placeholder": "请选择",
            optionConfig: {
                label: "itemName",
                value: "itemId",
                children: "children"
            },
            onChange:(val,obj) => {
                if(val !== '4'){
                    obj.form.setFieldsValue({ projType:null, companyType:null, proLevel:null })
                }
            },
            fetchConfig: {
                apiName: "getBaseCodeSelect",
                otherParams: {
                    itemId: "departmentType"
                }
            } // optionData: [
            //     {
            //         label: "集团",
            //         value: "0",
            //     },
            //     {
            //         label: "公司",
            //         value: "1",
            //     },
            //     {
            //         label: "部门",
            //         value: "2",
            //     },
            //     {
            //         label: "项目",
            //         value: "3",
            //     },
            //     {
            //         label: "小组",
            //         value: "4",
            //     },
            //     {
            //         label: "虚拟层次",
            //         value: "5",
            //     }
            // ]

        }
    }, {
        isInTable:false,
        "table": {
            width: 100,
            "title": "工程类型",
            "dataIndex": "projType",
            "key": "projType",
            filter: true
        },
        "form": {
            "field": "projType",
            "type": "select",
            "placeholder": "请选择",
            optionConfig: {
                label: "itemName",
                value: "itemId",
                children: "children"
            },
            condition: [
                {
                    regex: {
                        departmentFlag: ['!','4']
                    },
                    action: 'hide'
                }
            ],
            fetchConfig: {
                apiName: "getBaseCodeSelect",
                otherParams: {
                    itemId: "gongChengLeiXing"
                }
            }
        }
    }, {
        isInTable:false,
        "table": {
            width: 100,
            "title": "机构类型",
            "dataIndex": "companyType",
            "key": "companyType",
            filter: true
        },
        "form": {
            "field": "companyType",
            "type": "select",
            "placeholder": "请选择",
            optionConfig: {
                label: "itemName",
                value: "itemId",
                children: "children"
            },
            condition: [
                {
                    regex: {
                        departmentFlag: ['!','4']
                    },
                    action: 'hide'
                }
            ],
            fetchConfig: {
                apiName: "getBaseCodeSelect",
                otherParams: {
                    itemId: "jiGouLeiXing"
                }
            }
        }
    }, {
        isInTable:false,
        "table": {
            width: 100,
            "title": "项目级别",
            "dataIndex": "proLevel",
            "key": "proLevel",
            filter: true
        },
        "form": {
            "field": "proLevel",
            "type": "select",
            "placeholder": "请选择",
            optionConfig: {
                label: "itemName",
                value: "itemId",
                children: "children"
            },
            condition: [
                {
                    regex: {
                        departmentFlag: ['!','4']
                    },
                    action: 'hide'
                }
            ],
            fetchConfig: {
                apiName: "getBaseCodeSelect",
                otherParams: {
                    itemId: "xiangMuJiBie"
                }
            }
        }
    }, {
        isInForm: false,
        "table": {
            width: 500,
            tooltip: 40,
            "title": "机构全路径",
            "dataIndex": "departmentPathName",
            "key": "departmentPathName" // filter: true,

        },
        "form": {
            "type": "string",
            "placeholder": "请输入"
        }
    }, {
        table: {
            width: 80,
            title: '排序',
            dataIndex: 'sort'
        },
        form: {
            type: 'number',
            placeholder: '请输入',
            required: false
        }
    }, {
        "isInForm": false,
        "table": {
            width: 120,
            "dataIndex": "action",
            "key": "action",
            showType: "tile",
            title: "操作",
            fixed: "right",
            align: "center",
            "btns": [{
                willExecute: "bind:operationDisabled::departmentFlag::4",
                label: "编辑",
                "name": "edit",
                "formBtns": [{
                    "name": "cancel",
                    "type": "dashed",
                    "label": "取消"
                }, {
                    "name": "submit",
                    "type": "primary",
                    "label": "提交",
                    isRefreshTable: false,
                    onClick: "bind:editChildCb",
                    paramsFormat: "bind:_addRowData::" + JSON.stringify({
                        departmentParentId: 'departmentId'
                    }),
                    "fetchConfig": {
                        "apiName": "updateSysDepartment"
                    }
                }]
            }, // 这里提交不能去刷新列表需要将新增的数据插入到列表中 刷新列表会导致树表格收缩
            {
                willExecute: "bind:operationDisabled::departmentFlag::4",
                label: "新增子项",
                "name": "add",
                "formBtns": [{
                    "name": "cancel",
                    "type": "dashed",
                    "label": "取消"
                }, {
                    "name": "submit",
                    "type": "primary",
                    "label": "提交",
                    isRefreshTable: false,
                    onClick: "bind:addChildCb",
                    paramsFormat: "bind:_addRowData::" + JSON.stringify({
                        departmentParentId: 'departmentId'
                    }),
                    "fetchConfig": {
                        "apiName": "addSysDepartment"
                    }
                }]
            }]
        }
    }]
};