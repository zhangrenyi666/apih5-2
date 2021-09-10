window.TableDemoConfig = {
    //普通配置
    common: {

        fetchConfig: {
            apiName: "getTodoList",
            otherParams: {},
        },
        // formItemLayout: {
        //     labelCol: {
        //         xs: { span: 24 },
        //         sm: { span: 6 }
        //     },
        //     wrapperCol: {
        //         xs: { span: 24 },
        //         sm: { span: 18 }
        //     }
        // },
        //是否在表格第一个行加搜索
        firstRowIsSearch: true,
        // data: [
        //     {
        //         workId: "0",
        //         flowStatus: "123456",
        //         flowName1: '0000',
        //         sendTime: 1234567891234,
        //         address: '沈阳市辉南区',
        //         limitTime: 122,
        //         other2: "other2",
        //         other3: "other3",
        //         checkbox: "1",
        //         qnnForm: {
        //             name: '张三',
        //             age: 88
        //         }
        //     },
        //     {
        //         workId: "1",
        //         flowStatus: "3215644",
        //         flowName1: '2222',
        //         sendTime: 1234567891234,
        //         address: '大连市中山区',
        //         limitTime: 22,
        //         other2: "other2",
        //         other3: "other3",
        //         checkbox: "1",
        //         qnnForm: {
        //             name: '李四',
        //             age: 50
        //         }
        //     }
        // ],
        getRowSelection: function (obj) {
            return {
                getCheckboxProps: function (record) {
                    return {
                        name: record.name,
                        disabled: record.flowStatus === '审核中', // Column configuration not to be checked
                    }
                }
            }
        },
        antd: {
            rowKey: "workId",
            size: "small",
        },
        // diyTableRow: function (reactDom) {
        //     // console.log(reactDom);
        //     // if (reactDom.props.index === 2) {
        //     //     delete reactDom.props.columns[0]
        //     // }
        //     console.log(reactDom)
        //     // let aaa = reactDom.props.children();
        //     // console.log(aaa)
        //     return reactDom;
        // },
        // desc: "这是一段描述...",
        //和表格连在一起的地步表格按钮配置
        // drawerConfig: {
        //     width: "800px"
        //     maskClosable:false, //点击蒙层是否关闭抽屉  默认true
        // },
        // infoAlert: function(selectedRows) {
        //     return "已选择 " + selectedRows.length + "项";
        // }, 
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
                        field: "getDataBtn",
                        name: "diy",
                        type: "dashed",
                        label: "拿数据",
                        onClick: function (obj) {
                            // console.log(obj.props.form.getFieldsValue());
                            // console.log(obj)
                        }
                    },
                    {
                        field: "cancelBtn",
                        name: "cancel",
                        type: "dashed",
                        label: "取消"
                    },
                    {
                        field: "submitBtn",
                        name: "submit",
                        type: "primary",
                        label: "提交",
                        fetchConfig: {
                            //---新增接口
                            apiName: "add"
                        }
                    }
                ],
                hide: function (obj) {
                    // console.log(obj)
                    return false;
                }
            },
            {
                label: "自定义组件",
                name: "Component",
                Component: "diyComponent"
            },
            {
                name: 'diy',//diy一个弹出选人的组价
                icon: 'person',//icon
                type: 'primary',//类型  默认 primary  [primary dashed danger]
                label: '人员添加',
                onClick: function (obj) {
                    var openTree = obj.btnCallbackFn.openTree;
                    openTree({
                        field: "testaa",
                        fetchConfig: {//获取树结构的配置
                            apiName: 'getSysDepartmentUserAllTree',
                        },
                        initialValue: [
                            {
                                value: "000",
                                label: '数据一'
                            }
                        ],
                        onCancel: function () {
                            openTree({
                                openTree: false
                            })
                        },
                        onSave: function (val) {
                            console.log(val)
                        },
                    })
                }
            },
            {
                name: "del",
                icon: "delete",
                type: "danger",
                label: "删除",
                // fetchConfig: {
                //     //---删除接口
                //     apiName: "del"
                // }
            },
        ],

        // getBackEndConfig:{apiName:'todoList'},
        formConfig: [
            {
                isInTable: false,
                table: {
                    title: "主键id",
                    dataIndex: "educationId",

                },
                form: {
                    label: "主键id",
                    field: "educationId",
                    type: "string",
                    hide: true
                }
            },


            // {
            //     isInTable: false,
            //     table: {
            //         width: 100,
            //         title: "用于点击",
            //         dataIndex: "click",
            //         render: function () {
            //             return "可点击";
            //         },
            //         onClick: "edit"
            //     },
            //     form: {
            //         label: "主键id",
            //         type: "string",
            //         hide: true
            //     }
            // },


            // {
            //     isInTable: false,
            //     form: {
            //         label: "总数",
            //         field: "total",
            //         type: "number",
            //         addDisabled: true
            //     }
            // },
            // {
            //     isInTable: false,
            //     form: {
            //         label: "加数",
            //         field: "add1",
            //         type: "number"
            //     }
            // },
            // {
            //     isInTable: false,
            //     form: {
            //         label: "加数2",
            //         field: "add2",
            //         type: "number"
            //     }
            // },
            // {
            //     isInTable: false,
            //     form: {
            //         label: "diyField",
            //         field: "diyField",
            //         type: "Component",
            //         Component: "diyField"
            //     }
            // },


            // {
            //     isInTable: false,
            //     form: {
            //         //content就是qnn-form配置
            //         label: "表单块",
            //         field: "qnnForm",
            //         type: "qnnForm",
            //         // canAddForm: true, 
            //         qnnFormConfig: {
            //             canAddForm: true,
            //             formConfig: [
            //                 //普通字段
            //                 {
            //                     type: "string",
            //                     label: "普通字段",
            //                     field: "config2-1", //唯一的字段名 ***必传
            //                     placeholder: "请输入" //占位符
            //                 },
            //                 //数字类型
            //                 {
            //                     type: "number",
            //                     label: "数字字段",
            //                     field: "config2-2", //唯一的字段名 ***必传
            //                     placeholder: "请输入",
            //                     initialValue: 100
            //                 }
            //             ]
            //         }
            //     }
            // },

            {
                isInSearch: true,
                table: {
                    fixed: 'left',
                    width: 120,
                    title: "所属分部",
                    dataIndex: "flowStatus",
                    key: "flowStatus",
                    // filter: true, 
                    tdEdit: function (rowData,colData,props) {
                        // console.log(rowData);
                        // if (rowData.flowName === '测试') return false;
                        return true;
                    },
                    fieldsConfig: {
                        type: "select",
                        placeholder: "请选择",
                        optionData: [
                            {
                                label: "选项一",
                                value: '0'
                            },
                            {
                                label: "选项二",
                                value: '1'
                            }
                        ],
                        disabled: function (obj) {
                            if (obj.record.flowStatus === "654321") {
                                return true
                            }
                            return false;
                        }

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
                        otherParams: {}, //可为func
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
                        otherParams: { departmentPath: "",departmentFlag: 2 }
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
                    dataIndex: "sendUserName",
                    key: "sendUserName",
                    tdEdit: true,
                    // // filter: true,
                    // //单元格可编辑
                    // tdEdit: true,
                    // //配置tdEdit后fieldsConfig必须配置
                    // //该配置和formConfig的form【配置项】一样  field默认和dataIndex一样【也可单独配置】
                    fieldsConfig: {
                        field: "keyword",
                        type: "string",
                        placeholder: "请输入...",
                        disabled: function (obj) {
                            if (obj.record.flowStatus === "654321") {
                                return true
                            }
                            return false;
                        }
                    },

                    // children: [
                    //     {
                    //         dataIndex: 'flowName',
                    //         title: "字段1",
                    //         type: "string",
                    //         tdEdit: true,
                    //         fieldsConfig: { 
                    //             type: "string",
                    //             placeholder: "请输入..."
                    //         },
                    //         tdEditFetchConfig: {
                    //             apiName: "upDate",
                    //             params: {}, //可为func
                    //             otherParams: {} //可为func
                    //         },
                    //     },
                    //     {
                    //         dataIndex: 'sendUserName',
                    //         title: "字段2",
                    //         type: "string",
                    //         tdEdit: true,
                    //         fieldsConfig: { 
                    //             type: "string",
                    //             placeholder: "请输入..."
                    //         },
                    //         tdEditFetchConfig: {
                    //             apiName: "upDate",
                    //             params: {}, //可为func
                    //             otherParams: {} //可为func
                    //         },
                    //     }
                    // ]
                },
                form: {
                    // addDisabled: true,
                    required: true,
                    label: "交底名称",
                    type: "string",

                    labelClcik: function (obj) {
                        // console.log(obj);
                        var openTree = obj.tableFns.openTree;
                        var setFieldsValue = obj.form.setFieldsValue;

                        openTree({
                            field: "testaa",
                            fetchConfig: {//获取树结构的配置
                                apiName: 'getSysDepartmentUserAllTree',
                            },
                            initialValue: [
                                {
                                    value: "000",
                                    label: '数据一'
                                }
                            ],
                            onCancel: function () {
                                openTree({
                                    openTree: false
                                })
                            },
                            onSave: function (val) {
                                // console.log(val);
                                setFieldsValue({
                                    flowName1: val[0].label
                                });
                                openTree({
                                    openTree: false
                                })
                            },
                        })
                    },

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

            // {
            //     isInSearch: true,
            //     table: {
            //         title: "培训讲师",
            //         dataIndex: "sendUserName",
            //         key: "sendUserName",
            //         fieldsConfig: {
            //             field: 'sendUserNameSearch',
            //             type: "string",
            //             placeholder: "请输入..."
            //         }
            //     },
            //     form: {
            //         label: "培训讲师",
            //         type: "string",
            //         placeholder: "请输入"
            //     }
            // },
            {
                table: {
                    title: "培训地点",
                    dataIndex: "sendUserName1",
                    key: "sendUserName1",
                    fieldsConfig: {
                        field: 'addressSearch',
                        type: "string",
                        placeholder: "请输入..."
                    },
                },
                form: {
                    label: "培训地点",
                    type: "string",
                    placeholder: "请输入",
                    // initialValue:"123"
                    initialValue: function () {
                        return "444"
                    }
                }
            },
            {
                table: {
                    title: "培训学时",
                    dataIndex: "limitTime",
                    key: "limitTime",
                    align: "right",
                    defaultSortOrder: "descend", //默认排序  'ascend' | 'descend'
                    sorter: function (a,b) { return a.educationPeriod - b.educationPeriod }, //排序规则
                    // filter: true 
                    fieldsConfig: {
                        field: 'limitTimeSearch',
                        type: "string",
                        placeholder: "请输入..."
                    },
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
                isInForm: false,
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
                            rowKey: function (row) {
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
                                label: "抽屉形式新增",
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
                                    sorter: function (a,b) {
                                        return a.educationPeriod - b.educationPeriod; //排序规则
                                    },
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
            {
                isInTable: false,
                form: {
                    label: "表单字段测试",
                    field: "qnnForm",
                    type: "qnnForm",
                    qnnFormConfig: {
                        antd: {
                            rowKey: function (row) {
                                //---row.主键id
                                return row.workId;
                            }
                        },
                        formConfig: [
                            {
                                field: 'name',
                                label: "姓名",
                                type: "string",
                                placeholder: "请输入"
                            },
                            {
                                field: 'age',
                                label: "年纪",
                                type: "number",
                                placeholder: "请输入"
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
                table: {
                    dataIndex: "other2",
                    title: 'other2',
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
                },
                form: {
                    label: "other2",
                    field: "other2",
                    type: "string"
                }
            },
            {
                table: {
                    dataIndex: "other3",
                    title: 'other3',
                },
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
                        apiName: window.configs.domain + "upload"
                    }
                }
            },
            {
                isInForm: false,
                table: {
                    width: 110,
                    title: "操作",
                    align: "center",
                    // showType: "tile",
                    dataIndex: "action", //表格里面的字段
                    key: "action", //表格的唯一key
                    fixed: "right",
                    btns: function (props) {
                        return [
                            {
                                name: "edit",
                                label: "修改1",
                                //抽屉中的按钮
                                formBtns: [
                                    {
                                        field: "upCancelBtn",
                                        name: "cancel",
                                        type: "dashed",
                                        label: "取消",
                                    },
                                    {
                                        field: "upSubmitBtn",
                                        name: "submit",
                                        type: "primary",
                                        label: "提交",
                                        // fetchConfig: {
                                        //     //修改接口
                                        //     apiName: "update"
                                        // }
                                    }
                                ],

                                //条件配置
                                condition: [
                                    {
                                        //匹配规则 正则或者字符串
                                        //eg:行数据中的字段 id:'01' 时将禁用输入框
                                        regex: {
                                            workId: '1',
                                        },
                                        action: 'hide', //disabled,  show,  hide, function(){}
                                    }
                                ]

                            },
                            {
                                name: "detail",
                                label: "详情",
                                labelStyle: { color: '#1890ff',cursor: 'pointer' },
                            }
                        ]
                    }
                }
            }
        ]
    },

    //tabs页面配置==============
    tabsPage: {
        //   fetchConfig: {
        //     apiName: "getZxQrcodeTechnicalBasisList"
        //   },
        antd: {
            rowKey: function (row) {
                //---row.主键id
                return row.educationId;
            },
            size: "small",
        },
        desc: "这是一段描述...",
        // drawerConfig: {
        //     width: "800px"
        //     maskClosable:false, //点击蒙层是否关闭抽屉  默认true
        // },
        // infoAlert: function (selectedRows) {
        //     return "已选择 " + selectedRows.length + "项";
        // },
        //是否在表格第一个行加搜索
        firstRowIsSearch: true,
        searchFormColNum: 4,
        paginationConfig: {
            position: "bottom"
        },
        isShowRowSelect: false,
        data: [
            {
                educationId: "1",
                branchtName: "一分部",
                educationName: "测试交底",
                educationDateTime: new Date(),
                month: new Date().getTime(),
                checkbox: "0",
                name: "姓名",
                age: 100
            },
            {
                educationId: "2",
                branchtName: "二分部",
                educationName: "测试交底2",
                educationDateTime: new Date(),
                month: new Date().getTime(),
                checkbox: "0",
            },
            {
                educationId: "3",
                branchtName: "三分部",
                educationName: "测试交底3",
                educationDateTime: new Date(),
                month: new Date().getTime(),
                checkbox: "0",
            },
            {
                educationId: "4",
                branchtName: "四分部",
                educationName: "测试交底3",
                educationDateTime: new Date(),
                month: new Date().getTime(),
                checkbox: "0",
            }
        ],
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
                        // hide: true,
                        hide: function (obj) {
                            if (obj.btnCallbackFn.getActiveKey() === "2") {
                                return true;
                            } else {
                                return false;
                            }
                        },
                        onClick: function (obj) {
                            console.log(obj);
                            // console.log(obj.props.form.getFieldsValue());
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
        //存在tabs配置 所有打开的抽屉都是这个配置
        tabs: [
            {
                //content就是qnn-form配置
                field: "form1",
                name: "qnnForm",
                title: "表单",
                content: {
                    // fetchConfig: {
                    //     apiName: "getTodoList"
                    // },
                    formConfig: [
                        {
                            type: "string",
                            label: "姓名",
                            field: "name", //唯一的字段名 ***必传
                            placeholder: "请输入" //占位符
                        },
                        {
                            type: "number",
                            label: "年纪",
                            field: "age", //唯一的字段名 ***必传
                            placeholder: "请输入" //占位符
                        },
                        {
                            type: "linkage",
                            // fetchConfig: {
                            //     apiName: "getZjFlowPartyFeeDetailAllList"
                            // },
                            optionConfig: {
                                value: "detailId",
                                label: "detailName",
                                children: "currentList"
                            },
                            children: {
                                //所有配置见qnn-form
                                form: {
                                    label: "党费明细一",
                                    field: "feeDetailId1",
                                    type: "select",
                                    // required: true, //是否必填
                                    placeholder: "请选择"
                                },
                                children: {
                                    form: {
                                        label: "党费明细二",
                                        placeholder: "请选择",
                                        field: "feeDetailId2",
                                        // required: true, //是否必填
                                        type: "select"
                                    }
                                }
                            }
                        }
                    ]
                }
            },
            {
                //content就是qnn-form配置
                field: "form2",
                name: "qnnForm",
                title: "表单2",
                content: {
                    // fetchConfig: {
                    //     apiName: "getTodoList"
                    // },
                    formConfig: [
                        //普通字段
                        {
                            type: "string",
                            label: "普通字段",
                            field: "title2", //唯一的字段名 ***必传
                            placeholder: "请输入" //占位符
                        },
                        //数字类型
                        {
                            type: "number",
                            label: "数字字段",
                            field: "number2", //唯一的字段名 ***必传
                            placeholder: "请输入",
                            initialValue: 100
                        }
                    ]
                }
            },
            {
                //content就是qnn-table配置
                field: "table1",
                name: "qnnTable",
                title: "表格",
                // disabled: true,
                content: {
                    fetchConfig: {
                        apiName: "list-aaa"
                    },
                    // tabs: [], //注意：嵌入到tabs中的qnn-table配置必须配置tabs项 否则tabs项将和上级重复
                    drawerConfig: {
                        width: "800px"
                        // maskClosable:false, //点击蒙层是否关闭抽屉  默认true
                    },
                    antd: {
                        rowKey: function (row) {
                            //---row.主键id
                            return row.id;
                        }
                    },
                    actionBtns: [
                        {
                            name: "add", //【add,  del, edit, detail, Component, form】
                            icon: "plus", //icon
                            type: "primary", //类型  默认 primary
                            label: "新增",
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
                            ],

                        }
                    ],
                    paginationConfig: {
                        position: "bottom"
                    },
                    data: [
                        {
                            id: "0",
                            test: "车船税",
                            month: 1234567891234,
                            createUserName: "oldWang",
                            educationPlace: "沈阳高新区"
                        }
                    ],
                    formConfig: [
                        {
                            table: {
                                title: "测试",
                                dataIndex: "test",
                                key: "test",
                                filter: true,
                                onClick: "edit",
                            },
                            form: {
                                type: "string",
                                placeholder: "请输入",
                                hide: function (obj) {
                                    console.log(obj)
                                },
                                // addShow:false,
                                // editShow:true,
                                // detailShow:true,
                            }
                        },
                        {
                            table: {
                                title: "月测试",
                                dataIndex: "month",
                                key: "month",
                                format: "YYYY-MM"
                            },
                            form: {
                                field: "month",
                                type: "month",
                                placeholder: "请选择"
                            }
                        },
                        {
                            table: {
                                title: "培训讲师",
                                dataIndex: "createUserName",
                                key: "createUserName",
                                filter: true
                            },
                            form: {
                                label: "培训讲师",
                                field: "createUserName",
                                type: "string",
                                placeholder: "请输入"
                            }
                        },
                        {
                            table: {
                                title: "培训地点",
                                dataIndex: "educationPlace",
                                key: "educationPlace"
                            },
                            form: {
                                label: "培训地点",
                                field: "educationPlace",
                                type: "string",
                                placeholder: "请输入"
                            }
                        }
                    ]
                }
            }
        ],

        //正常的qnn-table配置 需要注意的地方所有项的isInForm都只能为false
        formConfig: [
            {
                isInTable: false,
                isInForm: false, //tabs配置存在时必须设为false
                table: {
                    title: "主键id",
                    dataIndex: "educationId"
                }
            },
            {
                isInForm: false,
                table: {
                    title: "所属分部",
                    dataIndex: "branchtName",
                    key: "branchtName",
                    // onClick: "editCol",
                    onClick: "detail",
                    btns: false,
                    render: function (data,row,index) {
                        if (row.educationId === "1") {
                            return {
                                children: data,
                                props: {
                                    colSpan: 2,
                                }
                            }
                        }
                        if (row.educationId === "2") {
                            return {
                                children: data,
                                props: {
                                    rowSpan: 2,
                                }
                            }
                        } else if (row.educationId === "3") {
                            return {
                                children: data,
                                props: {
                                    rowSpan: 0,
                                }
                            }
                        }
                        return data;
                    },
                    // filter: true
                },
                form: {
                    type: "string"
                }
            },
            {
                isInSearch: true,
                isInForm: false,
                table: {
                    title: "交底名称",
                    dataIndex: "educationName",
                    key: "educationName",
                    render: function (data,row,index) {
                        if (index === 0) {
                            return {
                                children: data,
                                props: {
                                    colSpan: 0,
                                }
                            }
                        }
                        return data;
                    }
                },
                form: {
                    type: "string"
                }
            },
            {
                isInSearch: true,
                isInForm: false,
                table: {
                    // colSpan:2,
                    title: "培训时间",
                    dataIndex: "educationDateTime",
                    key: "educationDateTime",
                    format: "YYYY-MM-DD HH:mm:ss"
                },
                form: {
                    type: "datetime"
                }
            },
            {
                isInSearch: true,
                isInForm: false,
                table: {
                    title: "月测试",
                    dataIndex: "month",
                    key: "month",
                    format: "YYYY-MM"
                },
                form: {
                    type: "month"
                }
            },
            {
                isInForm: false,
                table: {
                    title: "培训讲师",
                    dataIndex: "createUserName",
                    key: "createUserName"
                }
            },
            {
                isInForm: false,
                table: {
                    title: "培训地点",
                    dataIndex: "educationPlace",
                    key: "educationPlace"
                }
            },
            {
                isInForm: false,
                table: {
                    title: "培训学时",
                    dataIndex: "educationPeriod",
                    key: "educationPeriod",
                    align: "right",
                    defaultSortOrder: "descend", //默认排序  'ascend' | 'descend'
                    sorter: function (a,b) {
                        return a.educationPeriod - b.educationPeriod
                    } //排序规则
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
                    btns: function (obj) {
                        console.log(obj)
                        return [
                            {
                                name: "edit",
                                render: function () {
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
                                render: function () {
                                    return "<a>详情</a>";
                                }
                            }
                        ]
                    }
                }
            }
        ]
    },

    //无分页列表配置=============
    noPages: {
        fetchConfig: {
            apiName: "getTodoList",
            otherParams: {
                flowId: "zxHwZlTroubleJl"
            }
        },
        antd: {
            rowKey: function (row) {
                //---row.主键id
                return row.workId;
            },
            size: "small",
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
                //新增行完后的回调在配置fetchConfig后将无意义
                // addCb:(obj)=>{
                //     console.log(obj)
                // }
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
                            apiName: "getSysDepartmentListByCondition",
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
                    // optionConfig: {
                    //     //下拉选项k值
                    //     label: "departmentName",
                    //     value: "departmentId"
                    // },
                    // fetchConfig: {
                    //     //下拉接口
                    //     apiName: "getSysDepartmentListByCondition",
                    //     //下拉接口需要的参数
                    //     otherParams: { departmentPath: "",departmentFlag: 2 }
                    // },
                    optionData: [{ label: "分部一",value: '0' },{ label: "分部二",value: '1' }],
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
                    width: 180,
                    title: "交底名称",
                    dataIndex: "flowName",
                    key: "flowName",
                    filter: true,
                    //单元格可编辑
                    tdEdit: true,
                    //配置tdEdit后fieldsConfig必须配置
                    //该配置和formConfig的form【配置项】一样  field默认和dataIndex一样【也可单独配置】
                    fieldsConfig: {
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
                    width: 180,
                    title: "培训时间",
                    dataIndex: "sendTime",
                    key: "sendTime",
                    format: "YYYY-MM-DD HH:mm:ss",
                    filter: true,
                    //单元格可编辑
                    tdEdit: true,
                    fieldsConfig: {
                        type: "month",
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
                    type: "month",
                    placeholder: "请选择"
                }
            },

            {
                table: {
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
                    // align: "right",
                    // defaultSortOrder: "descend", //默认排序  'ascend' | 'descend'
                    // sorter: function (a,b) {
                    //     return a.educationPeriod - b.educationPeriod
                    // }, //排序规则
                    filter: true,
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
};
