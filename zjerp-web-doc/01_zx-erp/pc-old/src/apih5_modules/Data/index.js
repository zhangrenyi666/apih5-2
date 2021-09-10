const get = (name, params) => {
    return new Promise((resolve, reject) => {
        let data,
            has = true;
        switch (name) {
            case "login":
                data = { token: "000000" };
                break;
            case "routerInfo":
                data = [];
                break;
            case "qnnTable":
                data = {
                    fetchConfig: {
                        apiName: "getTodoList",
                        otherParams: {}
                    },
                    antd: {
                        rowKey: "workId"
                    },
                    desc: "这是一段描述...",
                    //和表格连在一起的地步表格按钮配置
                    // drawerConfig: {
                    //     width: "800px"
                    //     maskClosable:false, //点击蒙层是否关闭抽屉  默认true
                    // },
                    infoAlert: function(selectedRows) {
                        return "已选择 " + selectedRows.length + "项";
                    },
                    // infoAlert:"已选择",
                    searchFormColNum: 4,
                    paginationConfig: {
                        position: "bottom"
                    },
                    actionBtns: [
                        {
                            name: "add",
                            icon: "plus",
                            type: "primary",
                            label: "新增",
                            formBtns: [
                                {
                                    name: "diy",
                                    type: "dashed",
                                    label: "拿数据",
                                    onClick: function(obj) {
                                        console.log(obj);
                                    }
                                },
                                {
                                    name: "cancel",
                                    type: "dashed",
                                    label: "取消"
                                },
                                {
                                    name: "submit",
                                    type: "primary",
                                    label: "提交",
                                    fetchConfig: {
                                        //---新增接口
                                        apiName: "add"
                                    }
                                }
                            ]
                        },
                        {
                            label: "自定义组件",
                            name: "Component",
                            Component: "diyComponent"
                        },
                        {
                            name: "del",
                            icon: "delete",
                            type: "danger",
                            label: "删除",
                            fetchConfig: {
                                //---删除接口
                                apiName: "del"
                            }
                        }
                    ],

                    //是否在表格第一个行加搜索
                    firstRowIsSearch: true,

                    formConfig: [
                        {
                            isInTable: false,
                            table: {
                                title: "主键id",
                                dataIndex: "educationId"
                            },
                            form: {
                                label: "主键id",
                                field: "educationId",
                                type: "string",
                                hide: true
                            }
                        },
                        {
                            isInTable: false,
                            table: {
                                width: 100,
                                title: "用于点击",
                                dataIndex: "click",
                                render: function() {
                                    return "可点击";
                                },
                                onClick: "edit"
                            },
                            form: {
                                label: "主键id",
                                type: "string",
                                hide: true
                            }
                        },

                        {
                            isInSearch: true,
                            table: {
                                width: 120,
                                title: "所属分部",
                                dataIndex: "flowStatus",
                                key: "flowStatus",
                                // filter: true,
                                tdEdit: true,
                                fieldsConfig: {
                                    type: "select",
                                    placeholder: "请选择",
                                    optionData: [
                                        {
                                            label: "选项一",
                                            value: "0"
                                        },
                                        {
                                            label: "选项二",
                                            value: "1"
                                        }
                                    ]
                                    // optionConfig: {
                                    //     //下拉选项k值
                                    //     label: "departmentName",
                                    //     value: "departmentId"
                                    // },
                                    // fetchConfig: {
                                    //     //下拉接口
                                    //     apiName: "getSysDepartmentListByCondition",
                                    //     //下拉接口需要的参数
                                    //     otherParams: {
                                    //         departmentPath: "",
                                    //         departmentFlag: 2
                                    //     }
                                    // }
                                },
                                tdEditFetchConfig: {
                                    apiName: "selectUpDate",
                                    params: {}, //可为func
                                    otherParams: {} //可为func
                                }
                            },
                            form: {
                                label: "所属分部",
                                field: "branchtId",
                                type: "select",
                                placeholder: "请选择",
                                optionConfig: {
                                    //下拉选项k值
                                    label: "departmentName",
                                    value: "departmentId"
                                },
                                fetchConfig: {
                                    //下拉接口
                                    apiName: "getSysDepartmentListByCondition",
                                    //下拉接口需要的参数
                                    otherParams: {
                                        departmentPath: "",
                                        departmentFlag: 2
                                    }
                                },
                                spanForm: 12, //表单中行格数 一行24格 默认24   form
                                formItemLayoutForm: {
                                    labelCol: {
                                        sm: { span: 8 }
                                    },
                                    wrapperCol: {
                                        sm: { span: 16 }
                                    }
                                }
                            }
                        },
                        {
                            isInSearch: true,
                            table: {
                                width: 180,
                                title: "交底名称",
                                dataIndex: "flowName",
                                key: "flowName",
                                // filter: true,
                                //单元格可编辑
                                tdEdit: true,
                                //配置tdEdit后fieldsConfig必须配置
                                //该配置和formConfig的form【配置项】一样  field默认和dataIndex一样【也可单独配置】
                                fieldsConfig: {
                                    field: "keyword",
                                    type: "string",
                                    placeholder: "请输入..."
                                },
                                //不配置fetchConfig的回调（配置fetchConfig该属性将无意义） 包含新旧行数据 新旧table数据以及插件props和内置方法
                                // tdEditCb:(obj)=>{
                                //     console.log(obj)
                                // },
                                tdEditFetchConfig: {
                                    apiName: "upDate",
                                    params: {}, //可为func
                                    otherParams: {} //可为func
                                }
                                //配置了fetchConfig的回调 参数为后台返回数据 不配置将采用内置行为(失败成功都只是弹出提示)
                                // fetchCB:(res)=>{
                                //     console.log(res)
                                // }
                            },
                            form: {
                                addDisabled: true,
                                required: true,
                                label: "交底名称",
                                type: "string",
                                placeholder: "请输入",
                                spanForm: 12, //表单中行格数 一行24格 默认24   form
                                formItemLayoutForm: {
                                    labelCol: {
                                        sm: { span: 8 }
                                    },
                                    wrapperCol: {
                                        sm: { span: 16 }
                                    }
                                }
                            }
                        },
                        {
                            isInSearch: true,
                            table: {
                                width: 230,
                                title: "培训时间",
                                dataIndex: "sendTime",
                                key: "sendTime",
                                format: "YYYY-MM-DD HH:mm:ss",
                                // filter: true,
                                //单元格可编辑
                                tdEdit: true,
                                fieldsConfig: {
                                    type: "datetime",
                                    placeholder: "请选择"
                                },
                                tdEditFetchConfig: {
                                    apiName: "upDate",
                                    params: {}, //可为func
                                    otherParams: {} //可为func
                                }
                            },
                            form: {
                                label: "培训时间",
                                type: "datetime",
                                placeholder: "请选择"
                            }
                        },

                        {
                            isInSearch: true,
                            table: {
                                title: "培训讲师",
                                dataIndex: "sendUserName",
                                key: "sendUserName",
                                fieldsConfig: {
                                    field: "sendUserNameSearch",
                                    type: "string",
                                    placeholder: "请输入..."
                                }
                            },
                            form: {
                                label: "培训讲师",
                                type: "string",
                                placeholder: "请输入"
                            }
                        },
                        {
                            table: {
                                title: "培训地点",
                                dataIndex: "address",
                                key: "address",
                                fieldsConfig: {
                                    field: "addressSearch",
                                    type: "string",
                                    placeholder: "请输入..."
                                }
                            },
                            form: {
                                label: "培训地点",
                                type: "string",
                                placeholder: "请输入"
                            }
                        },
                        {
                            table: {
                                title: "培训学时",
                                dataIndex: "limitTime",
                                key: "limitTime",
                                align: "right",
                                defaultSortOrder: "descend", //默认排序  'ascend' | 'descend'
                                sorter: (a, b) =>
                                    a.educationPeriod - b.educationPeriod, //排序规则
                                // filter: true
                                fieldsConfig: {
                                    field: "limitTimeSearch",
                                    type: "string",
                                    placeholder: "请输入..."
                                }
                            },
                            form: {
                                label: "培训学时",
                                type: "string",
                                placeholder: "请输入"
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: "复选框",
                                field: "checkbox",
                                type: "checkbox",
                                optionData: [
                                    {
                                        label: "测试1",
                                        value: "0"
                                    },
                                    {
                                        label: "测试2",
                                        value: "1"
                                    },
                                    {
                                        label: "测试3",
                                        value: "2"
                                    }
                                ]
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: "表格字段测试",
                                field: "qnnTable",
                                type: "qnnTable",
                                qnnTableConfig: {
                                    fetchConfig: {
                                        apiName: "getTodoList",
                                        otherParams: {
                                            flowId: "zxHwZlTroubleJl"
                                        }
                                    },
                                    antd: {
                                        rowKey: function(row) {
                                            //---row.主键id
                                            return row.workId;
                                        }
                                    },
                                    //没有分页的table才能在使用增加行的操作
                                    paginationConfig: false,
                                    //不设置分页后需要将pageSize手动调大些
                                    pageSize: 9999,
                                    //操作按钮的位置  top | bottom  [string]  默认top
                                    actionBtnsPosition: "bottom",
                                    actionBtns: [
                                        {
                                            name: "addRow",
                                            icon: "plus",
                                            type: "primary",
                                            label: "新增行",
                                            //新增时候的默认数据
                                            addRowDefaultData: {
                                                workId: "new",
                                                flowStatus: "请选择",
                                                flowName: "请输入",
                                                sendTime: "请选择",
                                                title: "请输入",
                                                limitTime: "请输入"
                                            },
                                            //ajax配置 和 fetchConfig一样
                                            addRowFetchConfig: {
                                                apiName: "addRow",
                                                otherParams: {}
                                            }
                                        },
                                        {
                                            name: "add", //【add, addRow,  del, edit, detail, Component, form】
                                            icon: "plus", //icon
                                            type: "primary", //类型  默认 primary
                                            label: "新增by弹窗",
                                            drawerTitle: "新增", //点击后的抽屉标题
                                            //表单里面的按钮  name内置 【submit， cancel】
                                            formBtns: [
                                                {
                                                    name: "cancel", //关闭右边抽屉
                                                    type: "dashed", //类型  默认 primary
                                                    label: "取消"
                                                },
                                                {
                                                    name: "submit", //内置add del
                                                    type: "primary", //类型  默认 primary
                                                    label: "提交", //提交数据并且关闭右边抽屉
                                                    fetchConfig: {
                                                        //ajax配置
                                                        apiName: "submit"
                                                    }
                                                }
                                            ]
                                        },
                                        {
                                            name: "del",
                                            icon: "delete",
                                            type: "danger",
                                            label: "删除",
                                            fetchConfig: {
                                                //---删除接口
                                                apiName: "del"
                                            }
                                        }
                                    ],
                                    formConfig: [
                                        {
                                            isInSearch: false,
                                            table: {
                                                width: 120,
                                                title: "所属分部",
                                                dataIndex: "flowStatus",
                                                key: "flowStatus",
                                                filter: true,
                                                tdEdit: true,
                                                fieldsConfig: {
                                                    type: "select",
                                                    placeholder: "请选择...",
                                                    optionConfig: {
                                                        //下拉选项k值
                                                        label: "departmentName",
                                                        value: "departmentId"
                                                    },
                                                    fetchConfig: {
                                                        //下拉接口
                                                        apiName:
                                                            "getSysDepartmentListByCondition",
                                                        //下拉接口需要的参数
                                                        otherParams: {
                                                            departmentPath: "",
                                                            departmentFlag: 2
                                                        }
                                                    }
                                                },
                                                tdEditFetchConfig: {
                                                    apiName: "selectUpDate",
                                                    params: {}, //可为func
                                                    otherParams: {} //可为func
                                                }
                                            },
                                            form: {
                                                label: "所属分部",
                                                field: "branchtId",
                                                type: "select",
                                                placeholder: "请选择",
                                                optionConfig: {
                                                    //下拉选项k值
                                                    label: "departmentName",
                                                    value: "departmentId"
                                                },
                                                fetchConfig: {
                                                    //下拉接口
                                                    apiName:
                                                        "getSysDepartmentListByCondition",
                                                    //下拉接口需要的参数
                                                    otherParams: {
                                                        departmentPath: "",
                                                        departmentFlag: 2
                                                    }
                                                },
                                                spanForm: 12, //表单中行格数 一行24格 默认24   form
                                                formItemLayoutForm: {
                                                    labelCol: {
                                                        sm: { span: 8 }
                                                    },
                                                    wrapperCol: {
                                                        sm: { span: 16 }
                                                    }
                                                }
                                            }
                                        },
                                        {
                                            isInSearch: false,
                                            table: {
                                                width: 200,
                                                title: "交底名称",
                                                dataIndex: "flowName",
                                                key: "flowName",
                                                filter: true,
                                                //单元格可编辑
                                                tdEdit: true,
                                                fieldsConfig: {
                                                    type: "string",
                                                    placeholder: "请输入..."
                                                },
                                                tdEditFetchConfig: {
                                                    apiName: "upDate",
                                                    params: {}, //可为func
                                                    otherParams: {} //可为func
                                                }
                                            },
                                            form: {
                                                label: "交底名称",
                                                type: "string",
                                                placeholder: "请输入",
                                                spanForm: "12", //表单中行格数 一行24格 默认24   form
                                                formItemLayoutForm: {
                                                    labelCol: {
                                                        sm: { span: 8 }
                                                    },
                                                    wrapperCol: {
                                                        sm: { span: 16 }
                                                    }
                                                }
                                            }
                                        },
                                        {
                                            isInSearch: false,
                                            table: {
                                                width: 220,
                                                title: "培训时间",
                                                dataIndex: "sendTime",
                                                key: "sendTime",
                                                format: "YYYY-MM-DD HH:mm:ss",
                                                filter: true,
                                                //单元格可编辑
                                                tdEdit: true,
                                                fieldsConfig: {
                                                    type: "datetime",
                                                    placeholder: "请输入..."
                                                },
                                                tdEditFetchConfig: {
                                                    apiName: "upDate",
                                                    params: {}, //可为func
                                                    otherParams: {} //可为func
                                                }
                                            },
                                            form: {
                                                label: "培训时间",
                                                type: "datetime",
                                                placeholder: "请选择"
                                            }
                                        },

                                        {
                                            table: {
                                                width: 180,
                                                title: "培训地点",
                                                dataIndex: "title",
                                                key: "title",
                                                tdEdit: true,
                                                fieldsConfig: {
                                                    type: "string",
                                                    placeholder: "请输入..."
                                                },
                                                tdEditFetchConfig: {
                                                    apiName: "upDate",
                                                    params: {}, //可为func
                                                    otherParams: {} //可为func
                                                },
                                                tooltip: 12
                                            },
                                            form: {
                                                label: "培训地点",
                                                type: "string",
                                                placeholder: "请输入"
                                            }
                                        },
                                        {
                                            table: {
                                                width: 140,
                                                title: "培训学时",
                                                dataIndex: "limitTime",
                                                key: "limitTime",
                                                align: "right",
                                                defaultSortOrder: "descend", //默认排序  'ascend' | 'descend'
                                                sorter: (a, b) =>
                                                    a.educationPeriod -
                                                    b.educationPeriod, //排序规则
                                                // filter: true,
                                                tdEdit: true,
                                                fieldsConfig: {
                                                    type: "string",
                                                    placeholder: "请输入..."
                                                },
                                                tdEditFetchConfig: {
                                                    apiName: "upDate",
                                                    params: {}, //可为func
                                                    otherParams: {} //可为func
                                                }
                                            },
                                            form: {
                                                label: "培训学时",
                                                type: "string",
                                                placeholder: "请输入"
                                            }
                                        }
                                    ]
                                }
                            }
                        },
                        // {
                        //     isInTable: false,
                        //     form: {
                        //         label: "富文本测试",
                        //         field: "Rich",
                        //         type: "richtext",
                        //         defaultVlaue: ""
                        //     }
                        // },
                        {
                            isInSearch: false,
                            isInTable: false,
                            form: {
                                label: "发起对象",
                                field: "launcherList",
                                type: "treeSelect",
                                treeSelectOption: {
                                    search: true,
                                    searchParamsKey: "search",
                                    searchApi: "getSysDepartmentUserAllTree",
                                    selectType: "1",
                                    fetchConfig: {
                                        apiName: "getSysDepartmentUserAllTree"
                                    }
                                }
                            }
                        },
                        {
                            isInSearch: false,
                            isInTable: false,
                            form: {
                                label: "被选对象",
                                field: "candidateList",
                                type: "treeSelect",
                                treeSelectOption: {
                                    selectType: "2",
                                    search: true,
                                    searchParamsKey: "search",
                                    searchApi: "getSysDepartmentUserAllTree",
                                    fetchConfig: {
                                        apiName: "getSysDepartmentUserAllTree"
                                    }
                                }
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: "other2",
                                field: "other2",
                                type: "string"
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: "other3",
                                field: "other3",
                                type: "string"
                            }
                        },
                        {
                            isInSearch: true,
                            isInTable: false,
                            form: {
                                label: "other4",
                                field: "other4",
                                type: "string"
                            }
                        },
                        {
                            isInSearch: true,
                            isInTable: false,
                            form: {
                                label: "other5",
                                field: "other5",
                                type: "string"
                            }
                        },
                        {
                            isInSearch: true,
                            isInTable: false,
                            form: {
                                label: "other6",
                                field: "other6",
                                type: "string"
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: "附件",
                                field: "zxQrcodeAttachmentList",
                                type: "files",
                                initialValue: [],
                                fetchConfig: {
                                    apiName: "upload"
                                }
                            }
                        },
                        {
                            isInForm: false,
                            table: {
                                width: 110,
                                title: "操作",
                                align: "center",
                                showType: "tile",
                                dataIndex: "action", //表格里面的字段
                                key: "action", //表格的唯一key
                                btns: [
                                    {
                                        name: "edit",
                                        render: function() {
                                            return "<a>修改</a>";
                                        },
                                        formBtns: [
                                            {
                                                name: "cancel",
                                                type: "dashed",
                                                label: "取消"
                                            },
                                            {
                                                name: "submit",
                                                type: "primary",
                                                label: "提交",
                                                fetchConfig: {
                                                    //修改接口
                                                    apiName: "update"
                                                }
                                            }
                                        ]
                                    },
                                    {
                                        name: "detail",
                                        render: function() {
                                            return "<a>详情</a>";
                                        }
                                    } 
                                ]
                            }
                        }
                    ]
                };
                break;
            default:
                has = false;
                break;
        }
        if (has) {
            resolve(data);
        } else {
            reject();
        }
    });
};
const MyData = {
    get
};
export default MyData;
