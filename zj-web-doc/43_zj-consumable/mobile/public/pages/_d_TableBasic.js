
window.tableBasic = {
    antd: {
        rowKey: "userKey",
        size: "small",
    },
    selectType: "radio",
    desc: "这是表格描述",
    actionBtns: [
        {
            field: 'addCancelBtn',
            //willExecute:()=>void //点击前
            name: 'add',//【add, addRow,  del, edit, detail, Component, form】
            icon: 'plus',
            type: 'primary',
            label: '新增',
            drawerTitle: '新增',
            formBtns: [
                {
                    name: 'cancel', //关闭右边抽屉
                    type: 'dashed',//类型  默认 primary
                    label: '取消',
                    field: 'addCancelBtn',
                },
                {
                    name: 'submit',//内置add del
                    type: 'primary',//类型  默认 primary
                    label: '提交',//提交数据并且关闭右边抽屉
                    //格式化数据
                    //paramsFormat:({params, props, ...})=>{return {...}},
                    //控制是否提交表单
                    //isCanSubmit: ({params, props, ...}, callback)=>callback(true), //callback(true) || callback(false)
                    fetchConfig: {//ajax配置
                        apiName: 'getSysUserListByBg',
                        //删除参数中的name、age字段
                        delParams: []
                    },
                    //自定义按钮key值 必须配置 否则可能按钮点击后不会置为loading状态可能会导致重复提交问题
                    field: 'addSubmitBtn',
                    // isValidate:"curTab",
                    isValidate: true,//点击后是否验证表单 默认true
                }
            ]
        },
        {
            field: 'ComponentBtn',
            name: 'Component',//【add, addRow,  del, edit, detail, Component, form】
            Component: 'diyComponent',
            label: '自定义组件',
        },


        {
            field: 'getSearch',
            name: 'getSearch',//【add, addRow,  del, edit, detail, Component, form】 
            type: 'primary',
            label: '获取搜索参数',
            onClick: "bind:getSearch"
        },
        {
            field: 'getSearch',
            name: 'getSearch',//【add, addRow,  del, edit, detail, Component, form】 
            type: 'primary',
            label: '切换搜索表单样式',
            onClick: "bind:changeSearchBtnsStyle"
        },
    ],
    formItemLayout: {
        labelCol: {
            xs: { span: 24 },
            sm: { span: 4 }
        },
        wrapperCol: {
            xs: { span: 24 },
            sm: { span: 20 }
        }
    },
    fetchConfig: {
        apiName: "getSysUserListByBg",
        otherParams: {
            departmentId: "3299248",
            rootId: "999999999"
        }
    },
    // onUploadStart: (uid) => {
    //     console.log(uid)
    // },
    // data: [
    //     {
    //         value: "0",
    //         name: "测试王",
    //         address: "蒙渡村",
    //         age: 21,
    //         sex: "男",
    //         hobby: "0",
    //         birthday: 1582275494301,
    //         year: 1582275494301,
    //         rangeDate: [1582275494301,1582575494301],
    //         hobbyId: "110101,110105",
    //         projectId: "8a8bb38363fd1d89016435f25663086a",
    //         canAddForm: [
    //             {
    //                 money: 1
    //             },
    //             {
    //                 money: 2
    //             },
    //             {
    //                 money: 3
    //             }
    //         ]
    //     },
    //     {
    //         value: "2",
    //         name: "老李头",
    //         address: "沈阳村",
    //         age: 55,
    //         sex: "男",
    //         hobbyId: "120000",
    //         projectId: "8a8bb38363fd1d89016435f25663086a",
    //     }
    // ],
    formConfig: [



        // {
        //     table: {
        //         title: '分页下拉',
        //         dataIndex: 'projectId',
        //     }
        //     ,form: {
        //         type: 'selectByPaging',
        //         placeholder: '请选择',
        //         multiple: false, //是否开启多选功能 开启后自动开启搜索功能
        //         showSearch: false, //是否开启搜索功能 (移动端不建议开启)
        //         fetchConfig: { apiName: 'getZjTzProManageList',otherParams: {} },
        //         //optionData: [],
        //         optionConfig: { label: 'projectName',value: 'projectId',linkageFields: {} },
        //         required: false,
        //     }
        // },

        {
            isInTable: false
            , form: {
                type: 'qnnTable',
                label: 'qnnTable',
                field: 'qnnTable',
                dependencies: ["label"],
                incToForm: true,
                // diyRules: "bind:_tableValidator",
                qnnTableConfig: { 
                    tableTdEdit: true,
                    //所有配置见qnn-form
                    antd: {
                        rowKey: "id"
                    },
                    // fetchConfig: ()=>{
                    //     return {
                    //         apiName: "aaa",
                    //         otherParams: {
                    //             itemId: "minzhu"
                    //         }
                    //     }
                    // }, 
                    paginationConfig: false,
                    isShowRowSelect: false,
                    actionBtnsPosition: 'top',
                    formConfig: [
                        // {
                        //     // isInSearch:true,
                        //     table: {
                        //         // width: 120,
                        //         title: "爱好",
                        //         dataIndex: "hobby",
                        //         tdEdit: true,
                        //         tdEditFetchConfig: {
                        //             apiName: 'getBaseCodeSelect',
                        //         },

                        //         fieldsConfig: {
                        //             type: "selectByPaging",
                        //             field: "aiHao",
                        //             // mode: "tags",

                        //             pageConfig: {
                        //                 //设置每页显示多少条数据
                        //                 limit: 5
                        //             },
                        //             fetchConfig: {
                        //                 apiName: "getSysUserListByBg",
                        //                 otherParams: {
                        //                     departmentId: "2324017",
                        //                     rootId: "999999999"
                        //                 }
                        //             },
                        //             optionConfig: {
                        //                 label: "realName",
                        //                 value: "userKey"
                        //             },
                        //             allowClear: false




                        //         }
                        //     },
                        //     form: {
                        //         type: "select",
                        //         placeholder: "选择...",

                        //     },
                        // },

                        // {
                        //     table: {
                        //         title: 'test1',
                        //         dataIndex: 'evalPro',
                        //         tdEdit: true,
                        //         // tdEditCb: (props) => {
                        //         //     console.log(props)
                        //         // }
                        //         fieldsConfig: {
                        //             type: 'money',
                        //             formatter: function (value, props) {
                        //                 return value ? `${value.replace ? value.replace(/\B(?=(\d{3})+(?!\d))/g, ',').replace(/(%)/ig, '') : value}%` : value
                        //             },
                        //             parser: function (value) {
                        //                 return value ? value.replace(/\$\s?|(,*)/g, '').replace(/(%)/ig, '') : value
                        //             }
                        //         }
                        //     }
                        //     , form: {
                        //         type: 'number',
                        //         placeholder: '请输入',
                        //         required: true,
                        //     }
                        // },
                        // {
                        //     table: {
                        //         title: 'test2',
                        //         dataIndex: 'cascader111',
                        //         tdEdit: true,
                        //         "type": "cascader",
                        //         "optionConfig": {
                        //             "label": "itemName",
                        //             "value": "itemId",
                        //             "children": "children"
                        //         },
                        //         "fetchConfig": {
                        //             "apiName": "getBaseCodeTree",
                        //             "otherParams": {
                        //                 "itemId": "xingzhengquhuadaima"
                        //             }
                        //         }
                        //     }
                        //     , form: {
                        //         "type": "cascader",
                        //         "label": "层叠选择",
                        //         "field": "cascader111",
                        //         "optionConfig": {
                        //             "label": "itemName",
                        //             "value": "itemId",
                        //             "children": "children"
                        //         },
                        //         "fetchConfig": {
                        //             "apiName": "getBaseCodeTree",
                        //             "otherParams": {
                        //                 "itemId": "xingzhengquhuadaima"
                        //             }
                        //         }
                        //     }
                        // },

                        ...new Array(20).fill('1').map((item, index) => {
                            return {
                                table: {
                                    title: 'string'+index,
                                    dataIndex: 'string' + index,
                                    tdEdit: true,
                                }
                                , form: {
                                    type: 'string',
                                    placeholder: '请输入',
                                    required: false,
                                }
                            }
                        }),
                        {
                            table: {
                                title: 'stringR',
                                dataIndex: 'stringR',
                                tdEdit: true,
                            }
                            , form: {
                                type: 'string',
                                placeholder: '请输入',
                                required: true,
                            }
                        }

                    ],
                    actionBtns: [
                        {
                            name: 'addRow',
                            icon: 'plus',
                            type: 'primary',
                            label: '新增行',
                            detailShow: true,
                        },
                    ]
                }
            }
        },

        {
            isInTable: false
            , form: {
                type: 'files',
                label: '文件上传',
                field: 'wang',
                required: false,
                fetchConfig: { apiName: 'upload' },
                max: 999,
            }
        },
        {
            isInTable: false
            , form: {
                type: 'cron',
                label: 'cron',
                field: 'cron',
                // required: false, 
            }
        },

        {
            isInTable: false
            , form: {
                type: 'treeSelect',
                label: '拉人测试',
                field: '拉人测试',
                treeSelectOption: {
                    //配置参照oa拉人组件配置
                    fetchConfig: {
                        apiName: 'getSysDepartmentUserAllTree',
                    },
                    useCollect: true,  //开启收藏功能
                    collectApi: "appGetSysFrequentContactsList",  //查询收藏人员
                    collectApiByAdd: "appAddSysFrequentContacts", //新增收藏人员
                    collectApiByDel: "appRemoveSysFrequentContacts", //删除收藏人员
                },
                required: false,
            }
        },

        {
            isInSearch: true,
            table: {
                title: "姓名",
                dataIndex: "realName",
                // width: "50%",
                style: {
                    color: "red"
                },
                // onClick: "bind:getSearch",
                // tooltip: 10,
                // tdEdit: true,

            },
            form: {
                type: "string",
                required: true,
                placeholder: "请输入...",
                spanForm: 12,
                // labelCanClick: true,
                // labelClick: function (obj) {
                //     console.log(obj)
                // },
                formItemLayoutForm: {
                    labelCol: {
                        sm: { span: 8 }
                    },
                    wrapperCol: {
                        sm: { span: 16 }
                    }
                },
                condition: [
                    {
                        regex: {
                            isJianSuo: '2',
                        },
                        action: ['required', 'disabled'],
                    },
                ]
                // colStyle: {
                //     paddingLeft: 12
                // }
            },
        },

        {
            isInSearch: true,
            table: {
                dataIndex: "createTime",
                width: 120,
                title: "年纪",
                render: "bind:_divide::999::2",
                // tdEdit: true,
                fieldsConfig: {
                    "type": "cascader",
                    "label": "层叠选择",
                    "field": "cascader",
                    "optionConfig": {
                        "label": "itemName",
                        "value": "itemId",
                        "children": "children"
                    },
                    "fetchConfig": {
                        "apiName": "getBaseCodeTree",
                        "otherParams": {
                            "itemId": "xingzhengquhuadaima"
                        }
                    }
                }
            },
            form: {
                type: "string",
                placeholder: "请输入...",
                spanForm: 12,
            },
        },
        {
            isInSearch: true,
            table: {
                width: 120,
                title: "爱好",
                dataIndex: "hobbyId",
                type: "select",
                // multiple: true
            },
            form: {
                type: "select",
                placeholder: "选择...",
                optionConfig: {
                    label: "itemName",
                    value: "itemId",
                    children: "children"
                },
                allowClear: false,
                // multiple: true,
                required: true,
                showSearch: true,
                pullJoin: true,
                pushJoin: true,
                // onChange: (v) => {
                //     console.log(v)
                // },
                fetchConfig: {
                    apiName: "getBaseCodeTree",
                    otherParams: {
                        itemId: "xingzhengquhuadaima"
                    }
                }

            },
        },
        {
            isInSearch: true,
            table: {
                width: 250,
                title: "日期(date、week、month、year)",
                format: 'YYYY-MM-DD',
                dataIndex: "birthday",
            },
            form: {
                label: "日期",
                type: "date",
                placeholder: "请输入..."
            },
        },
        // {
        //     // isInTable: false,
        //     table: {
        //         width: 300,
        //         title: "月选择",
        //         format: 'YYYY-MM',
        //         dataIndex: "year",
        //     },
        //     form: {
        //         label: "月选择",
        //         type: "month",
        //         placeholder: "请输入..."
        //     },
        // },
        // {
        //     // isInTable: false,
        //     table: {
        //         width: 300,
        //         title: "日期范围(date、week、month、year)",
        //         dataIndex: "rangeDate",
        //         format: 'YYYY-MM',
        //     },
        //     form: {
        //         label: "日期范围",
        //         type: "rangeDate",
        //         picker: "date", //week  month  year  time date 
        //         placeholder: "请输入..."
        //     },
        // },
        // {
        //     // isInTable: false,
        //     table: {
        //         width: 120,
        //         title: "住址",
        //         dataIndex: "address",
        //     },
        //     form: {
        //         type: "string",
        //         placeholder: "请输入..."
        //     },
        // },
        {
            isInTable: false
            , form: {
                type: 'richtext',
                label: 'mobile',
                field: 'mobile1',
                placeholder: '请输入',
                fetchConfig: { uploadUrl: window.configs.domain + 'upload' },
            }
        },
        // {
        //     isInTable: false,
        //     form: {
        //         type: "qnnForm",
        //         field: "canAddForm",
        //         // closeed: true,
        //         label: "表单块测试",
        //         "canAddForm": true,
        //         formFields: [
        //             {
        //                 type: "money",
        //                 label: "金额",
        //                 field: "money",
        //                 placeholder: "请输入",
        //                 required: true,
        //                 span: 12
        //             },
        //             {
        //                 type: "string",
        //                 field: "string",
        //                 label: "普通文本输入",
        //                 span: 12
        //             },
        //         ]
        //     },
        // },

        {
            isInForm: false,
            table: {
                width: 120,
                title: "操作",
                align: "center",
                dataIndex: "action", //表格里面的字段
                key: "action", //表格的唯一key
                fixed: "right",
                showType: 'tile',
                btns: function (props) {
                    return [
                        {
                            name: "edit",
                            label: "修改",
                            //抽屉中的按钮
                            formBtns: [
                                {
                                    field: "upCancelBtn",
                                    name: "cancel",
                                    type: "dashed",
                                    label: "取消",
                                },
                                {
                                    field: "upSubmitBtn1",
                                    name: "submit",
                                    type: "primary",
                                    label: "提交",
                                    fetchConfig: {
                                        //修改接口
                                        apiName: "getSysUserListByBg"
                                    }
                                },
                                {
                                    field: "diy1",
                                    name: "diy",
                                    type: "primary",
                                    label: "拿数据",
                                    onClick: "bind:diy1Fn",
                                    // isValidate: "curTab"
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
                            labelStyle: { color: '#1890ff', cursor: 'pointer' },
                        }
                    ]
                }
            }
        }
    ],
}
