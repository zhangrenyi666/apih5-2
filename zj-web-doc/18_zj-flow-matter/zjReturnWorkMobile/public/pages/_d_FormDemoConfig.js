window.FormDemoConfig = {
    //form块表单
    formBlockPage: {
        style: { paddingTop: '0px' },
        formConfig: [
            {
                type: "string",
                label: "字段一",
                field: "field1", //唯一的字段名 ***必传
                placeholder: "请输入",
            },
            {
                type: "string",
                label: "字段二",
                field: "field2", //唯一的字段名 ***必传
                placeholder: "请输入",
            },



            // {
            //     type: "qnnForm",
            //     field: "userInfo",
            //     label: "个人信息",
            //     //qnn-form配置
            //     qnnFormConfig: {
            //         formConfig: [
            //             {
            //                 type: "string",
            //                 label: "姓名",
            //                 field: "name", //唯一的字段名 ***必传
            //                 placeholder: "请输入",
            //                 // initialValue: "张三",
            //             },
            //             {
            //                 type: "string",
            //                 label: "年纪",
            //                 field: "age", //唯一的字段名 ***必传
            //                 placeholder: "请输入",
            //             },
            //             {
            //                 type: "string",
            //                 label: "备注",
            //                 field: "desc", //唯一的字段名 ***必传
            //                 placeholder: "请输入",
            //             }
            //         ]
            //     }
            // },


            // {
            //     type: "qnnForm",
            //     field: "otherInfo",
            //     label: "其他信息",
            //     //qnn-form配置
            //     qnnFormConfig: {
            //         formConfig: [
            //             {
            //                 type: "number",
            //                 label: "身高",
            //                 field: "height", //唯一的字段名 ***必传
            //                 placeholder: "请输入",
            //             },
            //             {
            //                 type: "number",
            //                 label: "体重",
            //                 field: "weight", //唯一的字段名 ***必传
            //                 placeholder: "请输入",
            //             },

            //         ]
            //     }
            // },


            {
                type: "qnnForm",
                field: "baoXiao",
                label: "报销细明",
                //文字配置 默认数据如下 [object]
                textObj: {
                    add: "添加报销细明",
                    del: "删除"
                },
                //是否能新增form表单(true可动态增删) 默认true [bool]
                canAddForm: true,
                //qnn-form配置 
                // initialValue:[{yongTu: "默认值",}, {}],
                qnnFormConfig: {
                    formConfig: [
                        // {
                        //     type: "select",
                        //     label: "费用类型",
                        //     field: "typeOfExpense", //唯一的字段名 ***必传
                        //     placeholder: "请选择",
                        //     // required: true,
                        //     optionData: [
                        //         //默认选项数据//可为function (props)=>array
                        //         {
                        //             label: "交通",
                        //             value: "01"
                        //         },
                        //         {
                        //             label: "饭补",
                        //             value: "02"
                        //         }
                        //     ],
                        // },
                        {
                            type: "string",
                            label: "用途",
                            field: "yongTu", //唯一的字段名 ***必传
                            placeholder: "请输入",
                        },
                        {
                            type: "string",
                            label: "测试字段1",
                            field: "field1", //唯一的字段名 ***必传
                            placeholder: "请输入",
                        },
                        // {
                        //     type: "textarea",
                        //     label: "费用说明",
                        //     field: "desc", //唯一的字段名 ***必传
                        //     placeholder: "请输入",
                        // }
                    ]
                }
            }

        ],
        btns: [
            {
                name: "submit",
                type: "primary",
                label: "取值",
                onClick: function (obj) {
                    console.log(obj.values)
                }
            }
        ]
    },

    //tab页表单
    tabsPage: {
        // data: {
        //     month: 1234567891234,
        //     voice: "测试001"
        // },
        // tabsActiveKey: "1",
        // qnnFormContextHeight: window.innerHeight - 45,
        tabs: [
            {
                //content就是qnn-form配置
                field: "one",
                name: "qnnForm",
                title: "表单",
                //隐藏时是否渲染，为true则渲染但是会大大影响性能，为false则无法在隐藏状态获取值
                forceRender: false,
                // disabled: true,
                content: {
                    styleType: "0",
                    formConfig: [
                        {
                            type: "string",
                            label: "隐藏字段",
                            field: "id", //唯一的字段名 ***必传
                            hide: true
                        },
                        {
                            type: "string",
                            label: "超多文字测试",
                            field: "hideByFun", //唯一的字段名 ***必传
                            voice: true,
                            placeholder: "请输入...",
                            hide: function (obj) {
                                return false;
                            }
                        },
                        //微信语音输入
                        {
                            type: "textarea",
                            voice: true,
                            label: "语音输入",
                            field: "voice",
                            placeholder: "请输入", //占位符
                            rows: 4, //行高 默认4
                            required: true
                        },
                        //两字
                        {
                            voice: true,
                            type: "string",
                            label: "两字",
                            field: "tow", //唯一的字段名 ***必传
                            placeholder: "编辑状态", //占位符
                            required: true,
                            initialValue: "测试测试测试测试测试测试对对对订单"
                        },
                        {
                            type: "string",
                            label: "三个字",
                            field: "three", //唯一的字段名 ***必传
                            placeholder: "非编辑状态", //占位符
                            disabled: true,
                            required: true,
                            initialValue: "测试测试测试测试测试测试对对对订单"
                        },
                        //网址取值
                        {
                            type: "string",
                            label: "网址取值",
                            field: "flag", //唯一的字段名 ***必传
                            placeholder: "请输入", //占位符
                            isUrlParams: true,
                            disabled: true,
                            required: true
                        },
                        //树选择
                        {
                            label: "人员/部门",
                            field: "treeSelect",
                            type: "treeSelect",
                            initialValue: [],
                            treeSelectOption: {
                                // fetchConfig: {//配置后将会去请求下拉选项数据
                                //     apiName: 'getSysDepartmentUserAllTree',
                                // }
                                selectType: "0",
                                treeData: {
                                    label: "测试1",
                                    value: "测试1",
                                    children: [
                                        {
                                            label: "测试2",
                                            value: "测试2"
                                        }
                                    ]
                                }
                            }
                        },

                        //普通字段
                        {
                            type: "string",
                            label: "普通字段",
                            field: "string", //唯一的字段名 ***必传
                            placeholder: "请输入", //占位符
                            disabled: true
                        },
                        //数字类型
                        {
                            type: "number",
                            label: "数字字段",
                            field: "number", //唯一的字段名 ***必传
                            placeholder: "请输入",
                            initialValue: 100,
                            disabled: true
                            // max: 99, //最大值
                            // min: 20, //
                            // precision: 2, //数值精度
                            // span:'12',
                        },
                        //条件显隐例子
                        {
                            type: "string",
                            label: "条件显隐",
                            field: "condition", //唯一的字段名 ***必传
                            placeholder: "数字字段为1将隐藏", //占位符, //占位符
                            initialValue: "测试测试测试测试测试测试对对对订单",
                            //条件存在权重 下面条件满足上面条件将会失效
                            //匹配规则为&&匹配
                            //内置三种action【disabled, show, hide】
                            condition: [
                                {
                                    //条件
                                    regex: {
                                        //匹配规则 正则或者字符串
                                        number: 1
                                    },
                                    action: "hide" //disabled,  show,  hide, function(){}
                                }
                            ]
                        },
                        //条件显隐例子
                        {
                            type: "string",
                            label: "显隐二",
                            field: "condition2", //唯一的字段名 ***必传
                            placeholder: "数字字段为1将隐藏", //占位符
                            initialValue: "测试测试测试",
                            //条件存在权重 下面条件满足上面条件将会失效
                            //匹配规则为&&匹配
                            //内置三种action【disabled, show, hide】
                            condition: [
                                {
                                    //条件
                                    regex: {
                                        //匹配规则 正则或者字符串
                                        number: 1
                                    },
                                    action: "hide" //disabled,  show,  hide, function(){}
                                }
                            ]
                        },
                        //邮箱
                        {
                            type: "email",
                            label: "邮箱字段",
                            field: "email", //唯一的字段名 ***必传
                            placeholder: "请输入"
                        },

                        {
                            //dateTime yyyy-mm-dd hh:mm:ss
                            type: "datetime",
                            label: "日期时间",
                            field: "datetime", //唯一的字段名 ***必传
                            placeholder: "请选择",
                            // required: true,
                            initialValue: new Date(),
                            is24: true //是否是24小时制 默认true
                        },
                        //date yyyy-mm-dd
                        {
                            type: "date",
                            label: "日期选择",
                            field: "date", //唯一的字段名 ***必传
                            placeholder: "请选择"
                        },
                        //time yyyy-mm-dd
                        {
                            type: "time",
                            label: "时间选择",
                            field: "time", //唯一的字段名 ***必传
                            placeholder: "请选择",
                            is24: true //是否是24小时制 默认true2
                        },
                        //年月
                        {
                            type: "month",
                            label: "年月选择",
                            field: "month", //唯一的字段名 ***必传
                            placeholder: "请选择"
                        },

                    ],

                    btns: [
                        {
                            label: "重置",
                            isValidate: false, //是否验证表单 默认true
                            onClick: function (obj) {
                                const resetFields = obj.props.form.resetFields;
                                resetFields();
                            }
                        },
                        {
                            label: "提交",
                            type: "primary",
                            // onClick: function (obj) {
                            //     console.log(obj);
                            // }
                            fetchConfig: { apiName: "submit" }
                        },
                        {
                            label: "拿数据",
                            type: "diy",
                            isValidate: false, //是否验证表单 默认true
                            onClick: function (obj) {
                                console.log(obj);
                            }
                        },

                        {
                            type: "primary",
                            label: "切换tab",
                            filed: "changeTab",
                            isValidate: false, //是否验证表单 默认true 
                            onClick: "bind:changeTab"
                        },
                    ]
                }
            },
            {
                //content就是qnn-form配置
                field: "two",
                name: "qnnForm",
                title: "表单2",
                forceRender: false,
                content: {
                    formConfig: [
                        //普通字段
                        {
                            type: "string",
                            label: "普通字段",
                            field: "config2-1", //唯一的字段名 ***必传
                            placeholder: "请输入", //占位符
                            required:true
                        },
                        //数字类型
                        {
                            type: "number",
                            label: "数字字段",
                            field: "config2-2", //唯一的字段名 ***必传
                            placeholder: "请输入",
                            initialValue: 100
                        }
                    ],

                    btns: [
                        {
                            label: "提交",
                            type: "primary",
                            onClick: function (obj) {
                                console.log(obj);
                            }
                        },
                        {
                            label: "拿数据",
                            type: "diy",
                            isValidate: false, //是否验证表单 默认true
                            onClick: function (obj) {
                                console.log(obj);
                            }
                        }
                    ]
                }
            },
            {
                //content就是qnn-table配置
                field: "three",
                name: "qnnTable",
                title: "表格",
                content: {
                    fetchConfig: {
                        apiName: "getSysDepartmentListByCondition"
                    },
                    tabs: [],
                    drawerConfig: {
                        width: "800px"
                        // maskClosable:false, //点击蒙层是否关闭抽屉  默认true
                    },
                    antd: {
                        rowKey: function (row) {
                            //---row.主键id
                            return row.departmentId;
                        }
                    },
                    paginationConfig: {
                        position: "bottom"
                    },
                    formConfig: [
                        {
                            table: {
                                title: "测试",
                                dataIndex: "departmentName",
                                key: "departmentName",
                                filter: true,
                                onClick: "edit",
                                btns: [
                                    {
                                        name: "submit",
                                        label: "提交",
                                        fetchConfig: {
                                            apiName: "123"
                                        },
                                        // isValidate: false,
                                        // onClick: obj => {
                                        //     console.log(obj);
                                        // }
                                    }
                                ]
                            },
                            form: {
                                required: true,
                                type: "string",
                                placeholder: "请输入"
                            }
                        },
                        {
                            table: {
                                title: "培训讲师",
                                dataIndex: "departmentPathName",
                                key: "departmentPathName",
                                filter: true
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
                                dataIndex: "departmentPath",
                                key: "departmentPath"
                            },
                            form: {
                                label: "培训地点",
                                type: "string",
                                placeholder: "请输入"
                            }
                        }
                    ]
                }
            },
            // {
            //     field: "diy1",
            //     name: "diy1",
            //     title: "自定义页面",
            //     //[boolean | ()=>boolean]   默认false
            //     // disabled: true,
            //     content: props => {
            //         console.log(props);
            //         return <div>自定义组件</div>;
            //     }
            // }
        ]
    },

    //普通表单
    common: {
        // formItemLayout: {
        //     labelCol: {
        //         xs: { span: 6 },
        //         sm: { span: 3 }
        //     },
        //     wrapperCol: {
        //         xs: { span: 18 },
        //         sm: { span: 21 }
        //     }
        // },
        formConfig: [

            ...Array.from(new Array(2)).map((_,i) => {
                return {
                    type: 'string',
                    label: '测试' + (i + 1),
                    field: '测试' + (i + 1),
                    placeholder: '请输入',
                    // required: true,
                    voice: true
                }
            }),

            {
                type: "string",
                label: "隐藏字段",
                field: "id", //唯一的字段名 ***必传
                hide: true
            },

            {
                type: "number",
                label: "数字区间测试",
                field: "numberR1",
                placeholder: "请输入...",
                span: 14,
                formItemLayout: {
                    labelCol: { sm: { span: 7 } },
                    wrapperCol: { sm: { span: 17 } }
                },
                colWrapperStyle: {
                    paddingRight: '3px',
                },
            },
            {
                type: "number",
                label: "~~",
                field: "numberR2",
                placeholder: "请输入...",
                span: 10,
                formItemLayout: {
                    colon: false,
                    labelCol: { sm: { span: 2 } },
                    wrapperCol: { sm: { span: 22 } }
                },
                colWrapperStyle: {
                    paddingLeft: '0px',
                },
            },



            {
                type: "string",
                label: "label可点击",
                field: "labelClcikTest", //唯一的字段名 ***必传 
                placeholder: "请输入...",
                labelClcik: "bind:labelClcikFn"
            },

            {
                type: "string",
                label: "普通文字",
                field: "string2", //唯一的字段名 ***必传 
                placeholder: "请输入...",
                required: true,
                hide: "bind:hideTestFn",
                initialValue: "bind:initialValueFn",
                disabled: "bind:disabledFn",
                formatter: "bind:formatterFn",
            },

            //url
            {
                type: "url",
                label: "网址字段",
                field: "url", //唯一的字段名 ***必传
                placeholder: "请输入",
                onChange: "bind:onChangeFn"
            },

            //邮箱
            {
                type: "email",
                label: "邮箱字段",
                field: "email", //唯一的字段名 ***必传
                placeholder: "请输入"
            },
            //密码
            {
                type: "password",
                label: "密码字段",
                field: "password", //唯一的字段名 ***必传
                placeholder: "请输入", //占位符
                // required: true,//是否必填
                prefix: "lock", //前缀图标   [string]
                prefixStyle: { color: "rgba(0,0,0,.25)" } //前缀图标样式
            },


            //textarea类型
            {
                type: "textarea",
                label: "多行文本",
                field: "textarea", //唯一的字段名 ***必传
                placeholder: "请输入", //占位符
                rows: 4, //行高 默认4,
                required: true,
                // initialValue:"10",
                condition: [
                    {
                        //条件
                        regex: {
                            //匹配规则 正则或者字符串
                            money1: 1
                        },
                        action: "hide" //disabled,  show,  hide, function(){}
                    }
                ]
            },
            {
                type: "money",
                label: "金额",
                field: "money1",
                placeholder: "请输入",
                initialValue: 1,
            },
            {
                type: "string",
                label: "自定义规则校验",
                field: "diyRules",
                placeholder: "请输入",
                required: true,
                initialValue: "111.22,111.22",
                diyRules: "bind:diyRulesFn"
            },
            //条件显隐例子
            {
                type: "string",
                label: "条件显隐",
                field: "condition", //唯一的字段名 ***必传
                placeholder: "数字字段为1将隐藏", //占位符, //占位符
                initialValue: "测试测试测试测试测试测试对对对订单",
                //条件存在权重 下面条件满足上面条件将会失效
                //匹配规则为&&匹配
                //内置三种action【disabled, show, hide】
                condition: [
                    {
                        //条件
                        regex: {
                            //匹配规则 正则或者字符串
                            number: 1
                        },
                        action: "hide" //disabled,  show,  hide, function(){}
                    }
                ]
            },

            //条件显隐例子
            {
                type: "string",
                label: "显隐二",
                field: "condition2", //唯一的字段名 ***必传
                placeholder: "数字字段为1将隐藏", //占位符
                initialValue: "测试测试测试",
                //条件存在权重 下面条件满足上面条件将会失效
                //匹配规则为&&匹配
                //内置三种action【disabled, show, hide】
                condition: [
                    {
                        //条件
                        regex: {
                            //匹配规则 正则或者字符串
                            number: 1
                        },
                        action: "hide" //disabled,  show,  hide, function(){}
                    }
                ]
            },

            //qnnTable
            // {
            //     type: "qnnTable",
            //     label: "万能表格",
            //     field: "qnnTable", //唯一的字段名 ***必传
            //     // disabled:true,//是否不可编辑
            //     qnnTableConfig: {
            //         fetchConfig: {
            //             apiName: "getTodoList",
            //             otherParams: {
            //                 flowId: "zxHwZlTroubleJl"
            //             }
            //         },
            //         antd: {
            //             rowKey: "workId"
            //         },
            //         //没有分页的table才能在使用增加行的操作
            //         paginationConfig: false,
            //         //不设置分页后需要将pageSize手动调大些
            //         pageSize: 9999,
            //         //操作按钮的位置  top | bottom  [string]  默认top
            //         actionBtnsPosition: "bottom",
            //         actionBtns: [
            //             {
            //                 name: "addRow",
            //                 icon: "plus",
            //                 type: "primary",
            //                 label: "新增行",
            //                 //新增时候的默认数据
            //                 addRowDefaultData: {
            //                     workId: "new",
            //                     flowStatus: "请选择",
            //                     flowName: "请输入",
            //                     sendTime: "请选择",
            //                     title: "请输入",
            //                     limitTime: "请输入"
            //                 },
            //                 //ajax配置 和 fetchConfig一样
            //                 addRowFetchConfig: {
            //                     apiName: "addRow",
            //                     otherParams: {}
            //                 }
            //             },
            //             {
            //                 name: "del",
            //                 icon: "delete",
            //                 type: "danger",
            //                 label: "删除",
            //                 fetchConfig: {
            //                     //---删除接口
            //                     apiName: "del"
            //                 }
            //             }
            //         ],
            //         formConfig: [
            //             {
            //                 isInSearch: false,
            //                 table: {
            //                     width: 120,
            //                     title: "所属分部",
            //                     dataIndex: "flowStatus",
            //                     key: "flowStatus",
            //                     filter: true,
            //                     tdEdit: true,
            //                     fieldsConfig: {
            //                         type: "select",
            //                         placeholder: "请选择...",
            //                         optionConfig: {
            //                             //下拉选项k值
            //                             label: "departmentName",
            //                             value: "departmentId"
            //                         },
            //                         fetchConfig: {
            //                             //下拉接口
            //                             apiName:
            //                                 "getSysDepartmentListByCondition",
            //                             //下拉接口需要的参数
            //                             otherParams: {
            //                                 departmentPath: "",
            //                                 departmentFlag: 2
            //                             }
            //                         }
            //                     },
            //                     tdEditFetchConfig: {
            //                         apiName: "selectUpDate",
            //                         params: {}, //可为func
            //                         otherParams: {} //可为func
            //                     }
            //                 },
            //                 form: {
            //                     label: "所属分部",
            //                     field: "branchtId",
            //                     type: "select",
            //                     placeholder: "请选择",
            //                     optionConfig: {
            //                         //下拉选项k值
            //                         label: "departmentName",
            //                         value: "departmentId"
            //                     },
            //                     fetchConfig: {
            //                         //下拉接口
            //                         apiName: "getSysDepartmentListByCondition",
            //                         //下拉接口需要的参数
            //                         otherParams: {
            //                             departmentPath: "",
            //                             departmentFlag: 2
            //                         }
            //                     },
            //                     spanForm: "12", //表单中行格数 一行24格 默认24   form
            //                     formItemLayoutForm: {
            //                         labelCol: {
            //                             sm: { span: 8 }
            //                         },
            //                         wrapperCol: {
            //                             sm: { span: 16 }
            //                         }
            //                     }
            //                 }
            //             },
            //             {
            //                 isInSearch: false,
            //                 table: {
            //                     width: 180,
            //                     title: "交底名称",
            //                     dataIndex: "flowName",
            //                     key: "flowName",
            //                     filter: true,
            //                     //单元格可编辑
            //                     tdEdit: true,
            //                     fieldsConfig: {
            //                         type: "string",
            //                         placeholder: "请输入..."
            //                     },
            //                     tdEditFetchConfig: {
            //                         apiName: "upDate",
            //                         params: {}, //可为func
            //                         otherParams: {} //可为func
            //                     }
            //                 },
            //                 form: {
            //                     label: "交底名称",
            //                     type: "string",
            //                     placeholder: "请输入",
            //                     spanForm: "12", //表单中行格数 一行24格 默认24   form
            //                     formItemLayoutForm: {
            //                         labelCol: {
            //                             sm: { span: 8 }
            //                         },
            //                         wrapperCol: {
            //                             sm: { span: 16 }
            //                         }
            //                     }
            //                 }
            //             },
            //             {
            //                 isInSearch: false,
            //                 table: {
            //                     width: 180,
            //                     title: "培训时间",
            //                     dataIndex: "sendTime",
            //                     key: "sendTime",
            //                     format: "YYYY-MM-DD HH:mm:ss",
            //                     filter: true,
            //                     //单元格可编辑
            //                     tdEdit: true,
            //                     fieldsConfig: {
            //                         type: "datetime",
            //                         placeholder: "请输入..."
            //                     },
            //                     tdEditFetchConfig: {
            //                         apiName: "upDate",
            //                         params: {}, //可为func
            //                         otherParams: {} //可为func
            //                     }
            //                 },
            //                 form: {
            //                     label: "培训时间",
            //                     type: "datetime",
            //                     placeholder: "请选择"
            //                 }
            //             },

            //             {
            //                 table: {
            //                     title: "培训地点",
            //                     dataIndex: "title",
            //                     key: "title",
            //                     tdEdit: true,
            //                     fieldsConfig: {
            //                         type: "string",
            //                         placeholder: "请输入..."
            //                     },
            //                     tdEditFetchConfig: {
            //                         apiName: "upDate",
            //                         params: {}, //可为func
            //                         otherParams: {} //可为func
            //                     },
            //                     tooltip: 12
            //                 },
            //                 form: {
            //                     label: "培训地点",
            //                     type: "string",
            //                     placeholder: "请输入"
            //                 }
            //             },
            //             {
            //                 table: {
            //                     // width: 140,
            //                     title: "培训学时",
            //                     dataIndex: "limitTime",
            //                     key: "limitTime",
            //                     align: "right",
            //                     defaultSortOrder: "descend", //默认排序  'ascend' | 'descend' 
            //                     // filter: true,
            //                     tdEdit: true,
            //                     fieldsConfig: {
            //                         type: "string",
            //                         placeholder: "请输入..."
            //                     },
            //                     tdEditFetchConfig: {
            //                         apiName: "upDate",
            //                         params: {}, //可为func
            //                         otherParams: {} //可为func
            //                     }
            //                 },
            //                 form: {
            //                     label: "培训学时",
            //                     type: "string",
            //                     placeholder: "请输入"
            //                 }
            //             }
            //         ]
            //     }
            // },


            {
                type: "qnnForm",
                field: "numbers",
                label: "数字输入",
                //qnn-form配置
                // canAddForm: true,
                // noneTitle:true,
                // initialValue:[
                //     {
                //         float:20
                //     }
                // ],
                qnnFormConfig: {
                    formConfig: [

                        {
                            label: "无限联动",
                            type: "linkage",
                            fetchConfig: {
                                apiName: "getFlowNameSelectList"
                            },
                            optionConfig: {
                                // 暂时只有模式为0有意义 所有子集默认optionConfig
                                value: "apih5FlowId",
                                label: "apih5FlowName",
                                children: "children"
                            },
                            children: {
                                //所有配置见qnn-form
                                isInTable: false,
                                form: {
                                    editDisabled: true,
                                    label: "一级联动",
                                    field: "linkage1",
                                    type: "select",
                                    placeholder: "请选择"
                                    // fetchConfig: {//model为1时有用
                                    //     apiName: 'getFlowNameSelectList',
                                    //     parentKey:'XXX'
                                    // },
                                },
                                children: {
                                    form: {
                                        label: "二级联动",
                                        placeholder: "请选择",
                                        field: "linkage2",
                                        type: "select",
                                        optionData: [
                                            {
                                                apih5FlowId: "111",
                                                apih5FlowName: "111"
                                            }
                                        ]
                                    },
                                    children: {
                                        form: {
                                            label: "三级联动",
                                            placeholder: "请输入",
                                            field: "linkage3",
                                            type: "string"
                                        },
                                        children: {
                                            form: {
                                                label: "四级联动",
                                                placeholder: "请输入",
                                                field: "linkage4",
                                                type: "string"
                                            }
                                        }
                                    }
                                }
                            }
                        },


                        //数字类型
                        {
                            type: "number",
                            label: "数字字段",
                            field: "number", //唯一的字段名 ***必传
                            placeholder: "请输入",
                            initialValue: "bind:numberinitialValue"
                            // disabled: true
                            // max: 99, //最大值
                            // min: 20, //
                            // precision: 2, //数值精度
                            // span:'12',
                        },
                        //整数
                        {
                            type: "integer",
                            label: "整数字段",
                            field: "integer",
                            placeholder: "请输入",
                            required: true,
                            initialValue: 1
                        },
                        //小数
                        {
                            type: "number",
                            label: "小数",
                            field: "float",
                            placeholder: "请输入",
                            precision: 2, //数值精度
                        },
                        //金额
                        {
                            type: "money",
                            label: "金额",
                            field: "money",
                            placeholder: "请输入",
                        },



                    ]
                }
            },

            {
                type: "qnnForm",
                field: "dates",
                label: "时间日期输入",
                //qnn-form配置
                // initialValue: {
                //     timeH: new Date(),
                // },
                qnnFormConfig: {
                    formConfig: [
                        {
                            type: "time",
                            label: "时",
                            field: "timeH",
                            placeholder: "请选择",
                            format: "HH",
                            initialValue: "bind:timeHinitialValue"
                        },
                        {
                            type: "time",
                            label: "时分",
                            field: "time1",
                            placeholder: "请选择",
                            initialValue: new Date(),
                            format: "HH:mm"
                        },
                        {
                            type: "time",
                            label: "时分秒",
                            field: "time2",
                            placeholder: "请选择",
                            initialValue: new Date(),
                        },

                        {
                            type: "year",
                            label: "年选择",
                            field: "year1",
                            placeholder: "请选择",
                            initialValue: new Date(),
                        },

                        {
                            type: "month",
                            label: "年月",
                            field: "month",
                            placeholder: "请选择",
                            initialValue: new Date(),
                        },
                        {
                            type: "date",
                            label: "年月日",
                            field: "date",
                            placeholder: "请选择",
                            initialValue: new Date(),
                        },

                        {
                            type: "datetime",
                            label: "年月日-时",
                            field: "datetime1",
                            placeholder: "请选择",
                            initialValue: new Date(),
                            is24: true,
                            format: "YYYY-MM-DD HH"
                        },
                        {
                            type: "datetime",
                            label: "年月日-时分",
                            field: "datetime2",
                            placeholder: "请选择",
                            initialValue: new Date(),
                            is24: true,
                            format: "YYYY-MM-DD HH:mm"
                        },
                        {
                            type: "datetime",
                            label: "年月日-时分秒",
                            field: "datetime3",
                            placeholder: "请选择",
                            initialValue: new Date(),
                            is24: true
                        },
                        {
                            type: "rangeDate",
                            label: "时间区域",
                            field: "rangeDate",
                            placeholder: "请选择",
                            // showTime:true,
                            initialValue: [new Date(),new Date()],
                        },

                    ]
                }
            },


            {
                type: "qnnForm",
                field: "selects",
                label: "下拉输入",
                //qnn-form配置
                qnnFormConfig: {
                    formConfig: [
                        {
                            //普通选择框
                            type: "select",
                            label: "选择字段",
                            field: "select", //唯一的字段名 ***必传
                            placeholder: "请选择",
                            initialValue: "01id,01orgId",
                            // required: true,
                            multiple: false, //是否开启多选功能 开启后自动开启搜索功能
                            showSearch: false, //是否开启搜索功能 (移动端不建议开启)
                            optionData: [
                                //默认选项数据
                                {
                                    name: "01name",
                                    id: "01id",
                                    orgId: "01orgId"
                                },
                                {
                                    name: "02",
                                    id: "02id",
                                    orgId: "02orgId"
                                }
                            ],
                            optionConfig: {
                                //下拉选项配置
                                label: "name", //默认 label
                                value: ["id","orgId"] //最终的值使用逗号连接 默认值使用valueName 默认['value']
                            }
                        },


                        {
                            //普通选择框 可以和其他字段关联
                            type: "select",
                            label: "双向绑定",
                            field: "selects", //唯一的字段名 ***必传
                            placeholder: "请选择",
                            // initialValue: "01id",
                            multiple: false, //是否开启多选功能 开启后自动开启搜索功能
                            showSearch: false, //是否开启搜索功能 (移动端不建议开启) 
                            optionData: [
                                //默认选项数据
                                {
                                    name: "01name",
                                    id: "01id",
                                    orgId: "01orgId",
                                    name: "oldWang",
                                    twoOption: [
                                        {
                                            name: "111",
                                            id: "111",
                                        },
                                        {
                                            name: "01222name",
                                            id: "222",
                                        }
                                    ]
                                },
                                {
                                    name: "02",
                                    id: "02id",
                                    orgId: "02orgId",
                                    name: "路人甲",
                                    twoOption: [
                                        {
                                            name: "111",
                                            id: "111",
                                        },
                                        {
                                            name: "01222name",
                                            id: "222",
                                        }
                                    ]
                                }
                            ],
                            optionConfig: {
                                //下拉选项配置
                                label: "name",
                                value: "id",
                                linkageFields: {
                                    "selects_Block.linkageOne": "name",
                                    "selects_Block.linkageTwo": "orgId"
                                }
                            }
                        },
                        {
                            type: "string",
                            label: "联动一",
                            field: "linkageOne",
                            placeholder: "请输入",
                            disabled: true,
                        },
                        {
                            type: "string",
                            label: "联动二",
                            field: "linkageTwo",
                            placeholder: "请输入",
                            disabled: true
                        },


                        {
                            //层叠联动
                            type: "cascader",
                            label: "层叠联动",
                            field: "cascader", //唯一的字段名 ***必传
                            placeholder: "请选择",
                            // required: true,
                            showSearch: false, //是否开启搜索功能 (移动端不建议开启)
                            optionData: [
                                //默认选项数据
                                {
                                    name: "01name",
                                    id: "01id",
                                    children: [
                                        {
                                            name: "01name",
                                            id: "01id"
                                        }
                                    ]
                                },
                                {
                                    name: "02",
                                    id: "02id"
                                }
                            ],
                            optionConfig: {
                                //下拉选项配置
                                label: "name", //默认 label
                                value: "id", //
                                children: "children"
                            }
                        },


                        {
                            //分页选择框 可以和其他字段关联
                            type: "selectByPaging",
                            label: "下拉分页1",
                            field: "selectPage1", //唯一的字段名 ***必传
                            help: "这是一段帮助信息",
                            placeholder: "请选择",
                            fetchConfig: {
                                apiName: "getZxQrcodeOrganizationLevelList",
                                otherParams: {
                                    parentId: "0",
                                    first: true
                                }
                                // searchKey:'itemName'
                            },
                            optionConfig: {
                                //下拉选项配置
                                label: "levelShortName",
                                value: "levelId", //这里不能和select类型一样配置为数组 这里只能配置为string
                                // linkageFields: {
                                //     string2: "pidNameAll",
                                //     datetime: "createTime",
                                // }
                            },
                            pageConfig: {
                                //设置每页显示多少条数据
                                limit: 20
                            },
                            onChange: "bind:selectPage1ChangeFn",
                        },


                    ]
                }
            },

            {
                type: "qnnForm",
                field: "chekboxs",
                label: "单选多选等选择",
                //qnn-form配置
                qnnFormConfig: {
                    formConfig: [
                        {
                            type: "switch",
                            label: "开关",
                            field: "switch"
                        },
                        {
                            type: "rate",
                            label: "打分",
                            field: "rate"
                        },
                        {
                            type: "slider",
                            label: "滑块",
                            field: "slider",
                            marks: {
                                0: "A",
                                20: "B",
                                40: "C",
                                60: "D",
                                100: "E"
                            }
                        },
                        {
                            type: "radio",
                            label: "单选",
                            field: "radio", //唯一的字段名 ***必传
                            optionData: [
                                {
                                    label: "测试一",
                                    value: "001"
                                },
                                {
                                    label: "测试二",
                                    value: "002"
                                },
                                {
                                    label: "测试三",
                                    value: "004"
                                },
                                {
                                    label: "测试四",
                                    value: "003"
                                }
                            ]
                        },
                        {
                            type: "checkbox",
                            label: "多选",
                            field: "checkbox", //唯一的字段名 ***必传
                            optionData: [
                                {
                                    label: "测试一",
                                    value: "001"
                                },
                                {
                                    label: "测试二",
                                    value: "002"
                                },
                                {
                                    label: "测试三",
                                    value: "004"
                                },
                                {
                                    label: "测试四",
                                    value: "003"
                                }
                            ]
                        },
                    ]
                }
            },

            {
                type: "qnnForm",
                field: "idV",
                label: "证件类型验证",
                //qnn-form配置
                qnnFormConfig: {
                    formConfig: [
                        {
                            type: "passport",
                            label: "护照",
                            field: "passport", //唯一的字段名 ***必传
                            voice: true,
                            placeholder: "请输入...",
                        },
                        {
                            type: "taiWanIdentity",
                            label: "台湾居民身份证",
                            field: "TaiwanIdentity", //唯一的字段名 ***必传
                            voice: true,
                            placeholder: "请输入...",
                        },
                        {
                            type: "hongKongPerpetualIdentity",
                            label: "香港永久性居民身份证",
                            field: "hongKongPerpetualIdentity", //唯一的字段名 ***必传
                            voice: true,
                            placeholder: "请输入...",
                        },
                        {
                            type: "householdRegister",
                            label: "户口本",
                            field: "householdRegister", //唯一的字段名 ***必传
                            voice: true,
                            placeholder: "请输入...",
                        },
                        {
                            type: "birthCertificate",
                            label: " 出生证",
                            field: "birthCertificate", //唯一的字段名 ***必传
                            voice: true,
                            placeholder: "请输入...",
                        },
                        {
                            type: "postalCode",
                            label: "邮政编码",
                            field: "postalCode", //唯一的字段名 ***必传
                            voice: true,
                            placeholder: "请输入...",
                        },


                        {
                            type: "identity",
                            label: "身份证",
                            field: "identity", //唯一的字段名 ***必传
                            voice: true,
                            placeholder: "请输入...",
                        },

                    ]
                }
            },

            {
                type: "qnnForm",
                field: "phoneInput",
                label: "号码验证",
                //qnn-form配置
                qnnFormConfig: {
                    formConfig: [
                        {
                            type: "phone",
                            label: "手机号/座机",
                            field: "phone", //唯一的字段名 ***必传
                            voice: true,
                            placeholder: "请输入...",
                        },
                        {
                            type: "specialPlane",
                            label: "座机号码",
                            field: "specialPlane", //唯一的字段名 ***必传
                            voice: true,
                            placeholder: "请输入...",
                            help: "只验证座机号码"
                        }
                    ]
                }
            },


            {
                type: "qnnForm",
                field: "otherInput",
                label: "其他",
                //qnn-form配置
                qnnFormConfig: {
                    formConfig: [
                        //格式化显示
                        {
                            type: "string",
                            label: "格式化",
                            field: "formatter", //唯一的字段名 ***必传
                            placeholder: "请输入",
                            // required: true,
                            initialValue: 123456,
                            max: 99, //最大值
                            min: 20, //最大值
                            precision: 2, //数值精度
                            formatter: "bind:formatter"
                        },


                        //前后填充
                        {
                            type: "string",
                            label: "前后填充",
                            field: "prefix", //唯一的字段名 ***必传
                            placeholder: "请输入", //占位符
                            prefixStyle: { color: "rgba(0,0,0,.25)" }, //前缀图标样式
                            prefix: "user", //前缀图标   [string]
                            suffix: "link" //后缀图标   [string]
                        },
                        //前文本填充
                        {
                            type: "string",
                            label: "前文本填充",
                            field: "addonBefore", //唯一的字段名 ***必传
                            placeholder: "请输入", //占位符
                            addonBefore: "Http://" //前填充 [string]
                        },
                        {
                            type: "string",
                            label: "后文本填充",
                            field: "addonAfter", //唯一的字段名 ***必传
                            placeholder: "请输入", //占位符
                            addonAfter: ".com" //后填充  [string]
                        },

                        //树选择
                        {
                            label: "人员/部门",
                            field: "treeSelect",
                            type: "treeSelect",
                            initialValue: [
                                {
                                    label: "测试001",
                                    value: "001",
                                }
                            ],
                            treeSelectOption: {
                                fetchConfig: {//配置后将会去请求下拉选项数据
                                    apiName: 'getSysDepartmentUserAllTree',
                                },
                                selectType: "0",
                            }
                        },

                        //树节点  具体配置查看tree插件配置
                        {
                            type: "treeNode",
                            label: "树节点",
                            field: "treeNode123", //唯一的字段名 ***必传
                            treeNodeOption: {
                                fetchConfig: {
                                    parmasKey: "parentId", //点击节点后将节点id赋值该key上传递给后台
                                    apiName: "getSysMenuAllTree",
                                    params: {
                                        parentId: "0"
                                    }
                                },
                            }
                        },

                        {
                            type: "item",
                            field: "item",
                            // disabled:true,
                            label: "选项类型"
                        },


                        {
                            type: "string",
                            label: "超多文字测试",
                            field: "hideByFun", //唯一的字段名 ***必传
                            voice: true,
                            placeholder: "请输入...",
                        },
                        //两字
                        {
                            voice: true,
                            type: "string",
                            label: "两字",
                            field: "tow", //唯一的字段名 ***必传
                            placeholder: "编辑状态", //占位符
                            required: true,
                            initialValue: "测试测试测试测试测试测试对对对订单"
                        },
                        {
                            type: "string",
                            label: "三个字",
                            field: "three", //唯一的字段名 ***必传
                            placeholder: "非编辑状态", //占位符
                            disabled: true,
                            required: true,
                            initialValue: "测试测试测试测试测试测试对对对订单"
                        },
                        //网址取值
                        {
                            type: "string",
                            label: "网址取值",
                            field: "flag", //唯一的字段名 ***必传
                            placeholder: "请输入", //占位符
                            isUrlParams: true,
                            disabled: true,
                            // required: true
                        },
                        {
                            type: "richText",
                            label: "富文本描述",
                            field: "richText", //唯一的字段名 ***必传
                            ueditorConfig: {
                                //百度ue配置 可用默认的
                                // toolbars: [["fullscreen", "source", "undo", "redo", "bold"]]
                            },
                            fetchConfig: {
                                uploadUrl: window.configs.domain + "upload"
                            }
                        },
                        {
                            type: "textarea",
                            voice: true,
                            label: "数据列表",
                            field: "voice",
                            placeholder: "请输入", //占位符
                            // required: true,
                            // disabled:true,
                            oldValue: [
                                {
                                    text:
                                        "第一条历史数据第一条历史数据第一条历史数据第一条历史数据第一条历史数据第一条历史数据第一条历史数据",
                                    // text:"<a href='#'>第一条历史数据</a>",
                                    time: "2.19-08-49 12:44:55",
                                    name: "王思杨"
                                },
                                {
                                    text: "第二条历史数据",
                                    time: "2.19-8-9 12:44:55",
                                    name: "姜海军"
                                }
                            ],
                            oldValueKey: {
                                //默认数据
                                text: "text",
                                time: "time",
                                name: "name"
                            }
                            // initialValue:
                            //     "333333333333333333333333333333333333333333333333333333333333333333333333333333"
                        },
                        {
                            //自定义组件
                            type: "Component",
                            // label: '自定义组件',
                            field: "diy", //唯一的字段名 ***必传
                            placeholder: "请输入", //占位符

                            //第一种，推荐定义方式
                            Component: "myDiyComponent"

                            //第二种自定义组件方式
                            // Component: obj => {
                            //     return (
                            //         <div
                            //             style={{ width: "100%", border: "1px solid #ccc" }}
                            //             onClick={() => {
                            //                 var history = obj.props.history;
                            //                 var push = history.push;
                            //                 push("0");
                            //             }}
                            //         >
                            //             自定义组件
                            //         </div>
                            //     );
                            // }
                        },
                    ]
                }
            },


            //前后文本填充
            // {
            //   type: "string",
            //   label: "前后文本填充",
            //   field: "addon", //唯一的字段名 ***必传
            //   placeholder: "请输入", //占位符
            //   addonBefore: "Http://", //前填充 [string]
            //   addonAfter: ".com" //后填充  [string]
            // },


            {
                type: "qnnForm",
                field: "filesBlock",
                label: "文件图片上传",
                //qnn-form配置
                qnnFormConfig: {
                    formConfig: [
                        {
                            type: "files",
                            label: "文件上传",
                            field: "files", //唯一的字段名 ***必传
                            // disabled: true,
                            // required: true,//是否必填
                            desc: "点击上传", //默认 点击或者拖动上传 
                            fetchConfig: {
                                //配置后将会去请求下拉选项数据
                                apiName: window.configs.domain + "upload"
                                // name:'123', //上传文件的name 默认空
                            },
                            accept: "image/jpeg", //支持上传的类型 默认都支持  格式"image/gif, image/jpeg"
                            max: 5 //最大上传数量
                        },

                        //files文件上传
                        {
                            type: "filesDragger",
                            label: "文件上传",
                            field: "filesDragger", //唯一的字段名 ***必传
                            // disabled: true,
                            // required: true,//是否必填
                            desc: "点击或者拖动上传", //默认 点击或者拖动上传
                            subdesc: "只支持单个上传", //默认空
                            fetchConfig: {
                                //配置后将会去请求下拉选项数据
                                apiName: window.configs.domain + "upload"
                                // name:'123', //上传文件的name 默认空
                            },
                            accept: "image/jpeg", //支持上传的类型 默认都支持  格式"image/gif, image/jpeg"
                            max: 5 //最大上传数量
                        },
                        //图片上传
                        {
                            type: "images",
                            label: "图片上传",
                            field: "images", //唯一的字段名 ***必传
                            // required: true,//是否必填
                            fetchConfig: {
                                //配置后将会去请求下拉选项数据
                                apiName: window.configs.domain + "upload"
                                // name:'123', //上传文件的name 默认空
                            },
                            accept: "image/jpeg", //支持上传的类型 默认都支持  格式"image/gif, image/jpeg"
                            max: 5 //最大上传数量
                        },
                        //移动端文件上传
                        {
                            type: "camera",
                            label: "移动端Fil",
                            field: "cameraByFiels", //唯一的字段名 ***必传
                            fieldName: "cameraByFiels", //一定要配置唯一标识
                            cameraConfig: {
                                type: "files", //类型
                                showName: false //显示文件名字  默认false
                            },
                            fetchConfig: {
                                //配置后将会去请求下拉选项数据
                                apiName: window.configs.domain + "upload"
                                // name:'123', //上传文件的name 默认空
                            },
                            initialValue: [
                                {
                                    name: "测试",
                                    url:
                                        "http://47.96.150.231/apitongren/upload/1D47LUPKU0SJ8E6410AC000003004852.png",
                                    uid: "1",
                                    mobileUrl:
                                        "http://47.96.150.231/apitongren/upload/1D47LUPKU0SJ8E6410AC000003004852.png"
                                }
                            ],
                            max: 5 //最大上传数量
                        },
                    ]
                }
            },



            //qnn-form组件 案例一
            {
                type: "qnnForm",
                field: "qnnFormFieldOne",
                label: "个人信息",
                // initialValue: {
                //     name: "01",
                //     desc: "1113"
                // },
                //qnn-form配置
                qnnFormConfig: {
                    formConfig: [
                        {
                            type: "string",
                            label: "姓名",
                            field: "name", //唯一的字段名 ***必传
                            placeholder: "请输入",
                            initialValue: "张三",
                            // required: true,
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 6 },
                                    sm: { span: 3 }
                                },
                                wrapperCol: {
                                    xs: { span: 18 },
                                    sm: { span: 21 }
                                }
                            },
                        },
                        {
                            type: "number",
                            label: "年纪",
                            field: "age", //唯一的字段名 ***必传
                            placeholder: "请输入",
                            initialValue: 12,
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 6 },
                                    sm: { span: 3 }
                                },
                                wrapperCol: {
                                    xs: { span: 18 },
                                    sm: { span: 21 }
                                }
                            },
                        },
                        {
                            type: "textarea",
                            label: "备注",
                            field: "desc", //唯一的字段名 ***必传
                            placeholder: "请输入",
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 6 },
                                    sm: { span: 3 }
                                },
                                wrapperCol: {
                                    xs: { span: 18 },
                                    sm: { span: 21 }
                                }
                            },
                        }
                    ]
                }
            },


            //qnn-form组件 案例二
            {
                type: "qnnForm",
                field: "qnnFormFieldTwo",
                label: "报销细明",
                //文字配置 默认数据如下 [object]
                textObj: {
                    add: "添加报销细明",
                    del: "删除"
                },
                // disabled: true,
                //是否能新增form表单(true可动态增删) 默认true [bool]
                // canAddForm: true,
                //qnn-form配置
                // initialValue: [{
                //     typeOfExpense: "01",
                //     desc: "001"
                // },{
                //     typeOfExpense: "02",
                //     desc: "002"
                // }],
                qnnFormConfig: {
                    formConfig: [
                        {
                            type: "select",
                            label: "费用类型",
                            field: "typeOfExpense", //唯一的字段名 ***必传
                            placeholder: "请选择",
                            optionData: [
                                //默认选项数据//可为function (props)=>array
                                {
                                    label: "交通",
                                    value: "01"
                                },
                                {
                                    label: "饭补",
                                    value: "02"
                                }
                            ],

                        },
                        {
                            type: "number",
                            label: "费用金额",
                            field: "amount", //唯一的字段名 ***必传
                            placeholder: "请输入",
                        },
                        {
                            type: "string",
                            label: "文字必填",
                            field: "string", //唯一的字段名 ***必传
                            placeholder: "请输入",
                            // required: true
                        },
                        {
                            type: "textarea",
                            label: "费用说明",
                            field: "desc", //唯一的字段名 ***必传
                            placeholder: "请输入",
                        },
                        {
                            type: "camera",
                            label: "附件",
                            field: "files", //唯一的字段名 ***必传
                            fetchConfig: {
                                apiName: window.configs.domain + "upload"
                            },
                            fieldName: "camera", //一定要配置唯一标识
                            cameraConfig: {
                                type: "images"
                            },
                        }
                    ]
                }
            },



        ],

        btns: [
            {
                label: "重置",
                isValidate: false, //是否验证表单 默认true
                onClick: "bind:resetBtn"
            },
            {
                field: "submit",
                label: "提交",
                type: "primary",
                fetchConfig: {
                    apiName: "submit"
                },
                onClick: "bind:submitBtn"
            },
            {
                label: "拿数据",
                type: "diy",
                isValidate: false, //是否验证表单 默认true
                // affirmDesc: "提交后将无法撤回", //有这文字会点击按钮验证通过时将自动弹出提示
                // affirmYes: "确定", // 确认窗的确定按钮文字 默认确定
                // affirmNo: "取消", //确认窗的取消按钮文字  默认取消
                onClick: "bind:getValues"
            }
        ]
    },


    test: {
        // fetchConfig:{
        //     apiName:"aaa"
        // },

        formConfig: [

            ...Array.from(new Array(2)).map((_,i) => {
                return {
                    type: 'string',
                    label: '测试' + (i + 1),
                    field: 'test33' + (i + 1),
                    initialValue: '测试' + (i + 1),
                    placeholder: '请输入',
                    required: true,
                    voice: true,
                    span: 12,
                    disabled: true
                }
            }),




            // {
            //     type: 'string',
            //     label: '测试2',
            //     field: 'test2',
            //     placeholder: '请输入',
            //     condition: [
            //         {
            //             regex: {
            //                 // number: 1,
            //                 // add1: 2
            //                 test3: "1"
            //             },
            //             action: 'hide', //disabled, show, hide, function(){}
            //         },
            //         // {
            //         //     regex: {
            //         //         number: 3,
            //         //         add1: 4
            //         //     },
            //         //     action: 'disabled', //disabled, show, hide, function(){}
            //         // },
            //     ],
            // },
            {
                type: 'string',
                label: '测试3',
                field: 'test3',
                placeholder: '请输入',
                required: true,
                condition: [
                    {
                        regex: {
                            textarea: "1",
                            // add1: 2
                        },
                        action: 'hide', //disabled, show, hide, function(){}
                    },
                ],
            },

            {
                type: 'textarea',
                label: 'textarea',
                field: 'textarea',
                placeholder: '请输入',
                required: false,
                initialValue: "1",
                autosize: { minRows: 3,maxRows: 12 },

                // condition: [
                //     {
                //         regex: { "qnnFormOne_Block.name": "1" },
                //         action: 'hide', //disabled, show, hide, function(){}
                //     },
                // ],
            },

            // {
            //     type: 'number',
            //     label: '加数1',
            //     field: 'add1',
            //     placeholder: '请输入',
            //     required: false,
            // },
            // {
            //     type: 'number',
            //     label: '加数2',
            //     field: 'add2',
            //     placeholder: '请输入',
            //     required: false,
            // },
            // {
            //     type: 'number',
            //     label: '和',
            //     field: 'number',
            //     placeholder: '请输入',
            //     required: false,
            //     addends: ["add1","add2","qnnFormOne_Block.add3"],
            // },

            {
                type: 'qnnForm',
                label: "不可增加表单块",
                field: "qnnFormOne",
                qnnFormConfig: {
                    //所有配置见qnn-form
                    formConfig: [
                        {
                            type: 'number',
                            label: '加数3',
                            field: 'add3',
                            placeholder: '请输入',
                            required: false,
                        },

                        // {
                        //     type: 'number',
                        //     label: '和2加数1',
                        //     field: 'add4',
                        //     placeholder: '请输入',
                        //     required: false,
                        // },
                        // {
                        //     type: 'number',
                        //     label: '和2加数2',
                        //     field: 'add5',
                        //     placeholder: '请输入',
                        //     required: false,
                        // },


                        // {
                        //     type: 'number',
                        //     label: '和2',
                        //     field: 'number2',
                        //     placeholder: '请输入',
                        //     required: false, 
                        //     // addends: ["qnnFormOne.add4", "qnnFormOne.add5"],
                        // },

                        {
                            type: 'string',
                            label: '姓名',
                            field: 'name',
                            placeholder: '请输入',
                            required: true,
                        },
                        {
                            type: 'string',
                            label: '爱好',
                            field: 'hobby',
                            placeholder: '请输入',

                            condition: [
                                {
                                    regex: {
                                        "qnnFormOne_Block.name": "2",
                                        test3: "2"
                                    },
                                    action: 'hide', //disabled, show, hide, function(){}
                                },
                            ],
                        },
                        {
                            type: 'string',
                            label: 'test2等于1禁用 ',
                            field: 'test5',
                            placeholder: '请输入',
                            condition: [
                                {
                                    regex: {
                                        test2: "1",
                                        number: 5,
                                    },
                                    action: 'disabled', //disabled, show, hide, function(){}
                                },
                            ],
                        },
                        {
                            type: "files",
                            label: "文件上传",
                            field: "files", //唯一的字段名 ***必传
                            // disabled: true,
                            // required: true,//是否必填
                            desc: "点击上传", //默认 点击或者拖动上传 
                            fetchConfig: {
                                //配置后将会去请求下拉选项数据
                                apiName: window.configs.domain + "upload"
                                // name:'123', //上传文件的name 默认空
                            },
                            // accept: "image/jpeg", //支持上传的类型 默认都支持  格式"image/gif, image/jpeg"
                            max: 5 //最大上传数量
                        },

                    ]
                }
            },

            // {
            //     type: 'qnnForm',
            //     label: "可增减表单块",
            //     field: "qnnFormTwo",
            //     textObj: {
            //         add: '添加',
            //         del: '删除',
            //     },
            //     // disabled:true,
            //     // initialValue:[
            //     //     {
            //     //         test1:"123"
            //     //     },
            //     //     {
            //     //         test1:"456"
            //     //     }
            //     // ],
            //     canAddForm: true,
            //     qnnFormConfig: {
            //         //所有配置见qnn-form
            //         formConfig: [
            //             ...Array.from(new Array(1)).map((_,i) => {
            //                 return {
            //                     type: 'string',
            //                     label: '测试' + (i + 1),
            //                     field: 'test' + (i + 1),
            //                     placeholder: '请输入',
            //                     required: true,
            //                     // initialValue:'可增减表单块' + (i + 1),
            //                 }
            //             })

            //         ]
            //     }
            // },

            // {
            //     type: 'qnnForm',
            //     label: "拟用资质",
            //     field: "qnnFormTwo",
            //     textObj: {
            //         add: '添加',
            //         del: '删除',
            //     },
            //     canAddForm: true,
            //     // disabled:true, //使用禁用
            //     initialValue:[
            //         {
            //             aaa:"001",
            //             bbb:"anguanzhengshuzhuanye"
            //         },
            //         {
            //             aaa:"002",
            //             bbb:"wenhuachengdu"
            //         }
            //     ],
            //     qnnFormConfig: {
            //         //所有配置见qnn-form
            //         formConfig: [

            //             {
            //                 type: 'string',
            //                 label: '合同段（号）',
            //                 field: 'aaa',
            //                 placeholder: '请输入',
            //                 required: true,
            //                 span: 14, 
            //                 formItemLayout: {
            //                     labelCol: {
            //                         sm: { span: 14 },
            //                         xs: { span: 14 }
            //                     },
            //                     wrapperCol: {
            //                         sm: { span: 10 },
            //                         xs: { span: 10 }
            //                     }
            //                 },
            //                 style: {
            //                     height:'28px',
            //                     lineHeight: '28px',
            //                     borderBottom: "0px"
            //                 },
            //                 colWrapperStyle: {
            //                     paddingRight: '0px',

            //                 },
            //             },
            //             {
            //                 span: 10,
            //                 type: 'select',
            //                 label: '',
            //                 field: 'bbb',
            //                 placeholder: '请选择',
            //                 required: true,
            //                 fetchConfig: {
            //                     apiName: 'getBaseCodeList',
            //                     otherParams: {
            //                         codePid: "0"
            //                     }
            //                 },
            //                 optionConfig: {
            //                     label: "itemName",
            //                     value: "itemId"
            //                 },
            //                 formItemLayout: {
            //                     labelCol: {
            //                         sm: { span: 0 },
            //                         xs: { span: 0 }
            //                     },
            //                     wrapperCol: {
            //                         sm: { span: 24 },
            //                         xs: { span: 24 }
            //                     }
            //                 },
            //                 colWrapperStyle: {
            //                     overflow: 'hidden',
            //                     height: '41px',
            //                     paddingLeft: '3px',
            //                     paddingBottom: '-3px',
            //                 },
            //                 style: {
            //                     padding: '0px',
            //                     margin: '0px',
            //                 }
            //             }

            //         ]
            //     }
            // },

            // {
            //     type: 'qnnForm',
            //     label: "不可增加表单块",
            //     field: "qnnFormThree", 
            //     qnnFormConfig: {
            //         //所有配置见qnn-form
            //         formConfig: [
            //             ...Array.from(new Array(2)).map((_,i) => {
            //                 return {
            //                     type: 'string',
            //                     label: '测试' + (i + 1),
            //                     field: 'test' + (i + 1),
            //                     placeholder: '请输入', 
            //                     required:true
            //                     // initialValue:'测试' + (i + 1),
            //                 }
            //             })

            //         ]
            //     }
            // },


            // {
            //     type: 'qnnForm',
            //     label: "不可增加表单块",
            //     field: "qnnForm1", 
            //     qnnFormConfig: {
            //         //所有配置见qnn-form
            //         formConfig: [
            //             ...Array.from(new Array(10)).map((_,i) => {
            //                 return {
            //                     type: 'string',
            //                     label: '测试' + (i + 1),
            //                     field: 'test' + (i + 1),
            //                     placeholder: '请输入', 
            //                     required:true
            //                     // initialValue:'测试' + (i + 1),
            //                 }
            //             })

            //         ]
            //     }
            // },


            // {
            //     type: 'qnnForm',
            //     label: "增加表单块2",
            //     field: "qnnForm2",
            //     textObj: {
            //         add: '添加',
            //         del: '删除',
            //     },
            //     canAddForm: true, 
            //     qnnFormConfig: {
            //         //所有配置见qnn-form
            //         formConfig: [
            //             ...Array.from(new Array(10)).map((_,i) => {
            //                 return {
            //                     type: 'string',
            //                     label: '测试' + (i + 1),
            //                     field: 'test' + (i + 1),
            //                     placeholder: '请输入', 
            //                     required:true
            //                     // initialValue:'测试' + (i + 1),
            //                 }
            //             })

            //         ]
            //     }
            // },

            // {
            //     type: 'qnnForm',
            //     label: "qnnForm1",
            //     field: "qnnForm1",
            //     textObj: {
            //         add: '添加报销细明',
            //         del: '删除',
            //     },
            //     anAddForm: false,
            //     qnnFormConfig: {
            //         //所有配置见qnn-form
            //         formConfig: [ 
            //             ...Array.from(new Array(150)).map((_,i) => {
            //                 return {
            //                     type: 'string',
            //                     label: '测试' + i,
            //                     field: '测试' + i,
            //                     placeholder: '请输入',
            //                     required: false,
            //                 }
            //             })

            //         ]
            //     }
            // },
        ],
        btns: [
            {
                field: "submit",
                label: "提交",
                type: "primary",
                fetchConfig: {
                    apiName: "submit"
                },
                onClick: "bind:submitBtn"
            },
            {
                type: "primary",
                label: "拿数据",
                filed: "getValues",
                isValidate: false, //是否验证表单 默认true 
                onClick: "bind:getValues"
            },
            {
                type: "primary",
                label: "拿表单块数据",
                filed: "getValuesByQF",
                isValidate: false, //是否验证表单 默认true 
                onClick: "bind:getValuesByQF"
            },
            {
                type: "primary",
                label: "验证表单",
                filed: "getValuesYZ",
                isValidate: true, //是否验证表单 默认true 
                onClick: "bind:getValuesYZ"
            },
            // {
            //     type: "primary",
            //     label: "重置表单",
            //     filed: "reset",
            //     isValidate: false, //是否验证表单 默认true 
            //     onClick: "bind:resetBtn"
            // },
            // {
            //     type: "primary",
            //     label: "设置值",
            //     filed: "setValueBtn",
            //     isValidate: false, //是否验证表单 默认true 
            //     onClick: "bind:setValueBtn"
            // },
        ]
    },

};
