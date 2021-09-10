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



            {
                type: "qnnForm",
                field: "userInfo",
                label: "个人信息",
                //qnn-form配置
                qnnFormConfig: {
                    formConfig: [
                        {
                            type: "string",
                            label: "姓名",
                            field: "name", //唯一的字段名 ***必传
                            placeholder: "请输入",
                            // initialValue: "张三",
                        },
                        {
                            type: "string",
                            label: "年纪",
                            field: "age", //唯一的字段名 ***必传
                            placeholder: "请输入",
                        },
                        {
                            type: "string",
                            label: "备注",
                            field: "desc", //唯一的字段名 ***必传
                            placeholder: "请输入",
                        }
                    ]
                }
            },


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
        tabs: [
            {
                //content就是qnn-form配置
                field: "one",
                name: "qnnForm",
                title: "表单",
                disabled: true,
                content: {
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
                        //树节点  具体配置查看tree插件配置
                        // {
                        //     type: "treeNode",
                        //     label: "树节点",
                        //     field: "treeNode123", //唯一的字段名 ***必传
                        //     treeNodeOption: {
                        //         fetchConfig: {
                        //             parmasKey: "parentId", //点击节点后将节点id赋值该key上传递给后台
                        //             apiName: "appGetGxProjectLevel",
                        //             params: {
                        //                 parentId: "0"
                        //             }
                        //         },
                        //         keys: {
                        //             label: "levelName",
                        //             value: "levelId",
                        //             children: "zlProjectLevelList"
                        //         },
                        //         nodeRender: nodeData => {
                        //             return (
                        //                 <span>
                        //                     {nodeData["levelName"]}（总
                        //                     {nodeData["totalNum"]}道工序，已完成
                        //                     {nodeData["finishedNum"]}道）
                        //                 </span>
                        //             );
                        //         },
                        //         valueRender: obj => {
                        //             let parentNameAll = obj.parentNameAll;
                        //             if (parentNameAll) {
                        //                 let _fLabel = parentNameAll.replace(/,/gi, "→");
                        //                 return _fLabel;
                        //             } else {
                        //                 return "";
                        //             }
                        //         },
                        //         rightMenuOption: []
                        //     }
                        // },
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
                            formatter: function (value) {
                                if (value) {
                                    return value.toString().indexOf("$") !== -1
                                        ? value
                                        : value + "$";
                                }
                            } //格式化显示
                        },
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
                            type: "item",
                            field: "item",
                            // disabled:true,
                            label: "选项类型"
                        },
                        {
                            //无限联动
                            isInTable: false, //必须为false  默认false
                            form: {
                                type: "linkage",
                                model: "0", // string  默认0  0所有数据都给到前端  1触焦时去请求数据
                                fetchConfig: {
                                    //只有模式为0才写到这
                                    apiName: "getZxHwTlDBTThreeLevelLinkage"
                                },
                                optionConfig: {
                                    // 暂时只有模式为0有意义 所有子集默认optionConfig
                                    value: "key",
                                    label: "value",
                                    children: "childrenList"
                                },
                                children: {
                                    //所有配置见qnn-form
                                    isInTable: false,
                                    form: {
                                        editDisabled: true,
                                        label: "所属分部",
                                        field: "linkage1",
                                        type: "select",
                                        placeholder: "请选择"
                                    },
                                    children: {
                                        form: {
                                            label: "所属梁场",
                                            placeholder: "请选择",
                                            field: "linkage2",
                                            type: "select",
                                        },
                                        children: {
                                            form: {
                                                label: "所属T梁",
                                                placeholder: "请输入",
                                                field: "linkage3",
                                                type: "select",
                                            }
                                        }
                                    }
                                }
                            }
                        },

                        {
                            label: "无限联动",
                            type: "linkage",
                            //   fetchConfig: {
                            //     apiName: "getFlowNameSelectList"
                            //   },
                            optionConfig: {
                                // 暂时只有模式为0有意义 所有子集默认optionConfig
                                value: "key",
                                label: "value",
                                children: "childrenList"
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


                        //url
                        {
                            type: "url",
                            label: "网址字段",
                            field: "url", //唯一的字段名 ***必传
                            placeholder: "请输入"
                            // required: true,
                        },

                        //textarea类型
                        {
                            type: "textarea",
                            label: "多行文本",
                            field: "textarea", //唯一的字段名 ***必传
                            placeholder: "请输入", //占位符
                            rows: 4 //行高 默认4
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

                        //files文件上传
                        {
                            type: "files",
                            label: "文件上传",
                            field: "files", //唯一的字段名 ***必传
                            disabled: true,
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
                        //可选相机的文件上传
                        {
                            type: "camera",
                            label: "拍摄照片",
                            field: "camera", //唯一的字段名 ***必传
                            // required: true,//是否必填
                            disabled: true,
                            fetchConfig: {
                                //配置后将会去请求下拉选项数据
                                apiName: window.configs.domain + "upload"
                                // name:'123', //上传文件的name 默认空
                            },
                            accept: "image/jpeg", //支持上传的类型 默认都支持  格式"image/gif, image/jpeg"
                            max: 5 //最大上传数量
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
                                uploadUrl: window.configs.domain + "uploadimage"
                            }
                        }
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
            // {
            //     //content就是qnn-form配置
            //     field: "two",
            //     name: "qnnForm",
            //     title: "表单2",
            //     content: {
            //         formConfig: [
            //             //普通字段
            //             {
            //                 type: "string",
            //                 label: "普通字段",
            //                 field: "config2-1", //唯一的字段名 ***必传
            //                 placeholder: "请输入" //占位符
            //             },
            //             //数字类型
            //             {
            //                 type: "number",
            //                 label: "数字字段",
            //                 field: "config2-2", //唯一的字段名 ***必传
            //                 placeholder: "请输入",
            //                 initialValue: 100
            //             }
            //         ],

            //         btns: [
            //             {
            //                 label: "提交",
            //                 type: "primary",
            //                 onClick: function (obj) {
            //                     console.log(obj);
            //                 }
            //             },
            //             {
            //                 label: "拿数据",
            //                 type: "diy",
            //                 isValidate: false, //是否验证表单 默认true
            //                 onClick: function (obj) {
            //                     console.log(obj.values);
            //                 }
            //             }
            //         ]
            //     }
            // },
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

            {
                //普通选择框 可以和其他字段关联
                type: "select",
                label: "双向绑定",
                field: "bindings", //唯一的字段名 ***必传
                placeholder: "请选择",
                // initialValue: "01id",
                multiple: false, //是否开启多选功能 开启后自动开启搜索功能
                showSearch: false, //是否开启搜索功能 (移动端不建议开启)
                // onChange:function(v, p){
                //     let a = optionData.fullter(item=>{
                //         return item.id === v
                //     })[0];
                //     console.log(v, p) 
                // },
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
                        linkageOne: "name",
                        linkageTwo: "orgId"
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
                type: "string",
                label: "隐藏字段",
                field: "id", //唯一的字段名 ***必传
                hide: true
            },

            {
                type: "string",
                label: "label可点击",
                field: "labelClcikTest", //唯一的字段名 ***必传 
                placeholder: "请输入...",
                labelClcik: function (obj) {
                    console.log(obj)
                }
            },

            {
                type: "string",
                label: "普通文字",
                field: "string2", //唯一的字段名 ***必传 
                placeholder: "请输入...",
            },


            //万能类型字段（目前支持：身份证、 军官证或士兵证、护照、警官证、台湾居民身份证、台湾永久性居民身份证、户口本、邮政编码、其他【将不进行验证】）
            //暂不可用
            // {
            //     type: "qnnType",
            //     label: "qnnType",
            //     field: "qnnType", //唯一的字段名 ***必传 
            //     placeholder: "请输入...",
            // },

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
                        {
                            //整数
                            type: "integer",
                            label: "整数字段",
                            field: "integer", //唯一的字段名 ***必传
                            placeholder: "请输入"
                            // required: true,
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
                        {
                            type: "string",
                            label: "超多文字测试",
                            field: "hideByFun", //唯一的字段名 ***必传
                            voice: true,
                            placeholder: "请输入...",
                            // optionConfig:a.twoOption
                            hide: function (obj) {
                                return false;
                            }
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
                    ]
                }
            },


            //qnnTable
            {
                type: "qnnTable",
                label: "万能表格",
                field: "qnnTable", //唯一的字段名 ***必传
                // disabled:true,//是否不可编辑
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
                                    apiName: "getSysDepartmentListByCondition",
                                    //下拉接口需要的参数
                                    otherParams: {
                                        departmentPath: "",
                                        departmentFlag: 2
                                    }
                                },
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
                                width: 180,
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
                                // width: 140,
                                title: "培训学时",
                                dataIndex: "limitTime",
                                key: "limitTime",
                                align: "right",
                                defaultSortOrder: "descend", //默认排序  'ascend' | 'descend'
                                sorter: function (a,b) {
                                    return a.educationPeriod - b.educationPeriod
                                }, //排序规则
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
            //树节点  具体配置查看tree插件配置
            // {
            //     type: "treeNode",
            //     label: "树节点",
            //     field: "treeNode123", //唯一的字段名 ***必传
            //     treeNodeOption: {
            //         fetchConfig: {
            //             parmasKey: "parentId", //点击节点后将节点id赋值该key上传递给后台
            //             apiName: "appGetGxProjectLevel",
            //             params: {
            //                 parentId: "0"
            //             }
            //         },
            //         keys: {
            //             label: "levelName",
            //             value: "levelId",
            //             children: "zlProjectLevelList"
            //         },
            //         nodeRender: nodeData => {
            //             return (
            //                 <span>
            //                     {nodeData["levelName"]}（总
            //                     {nodeData["totalNum"]}道工序，已完成
            //                     {nodeData["finishedNum"]}道）
            //                 </span>
            //             );
            //         },
            //         valueRender: obj => {
            //             let parentNameAll = obj.parentNameAll;
            //             if (parentNameAll) {
            //                 let _fLabel = parentNameAll.replace(/,/gi, "→");
            //                 return _fLabel;
            //             } else {
            //                 return "";
            //             }
            //         },
            //         rightMenuOption: []
            //     }
            // },
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
                formatter: function (value) {
                    if (value) {
                        return value.toString().indexOf("$") !== -1
                            ? value
                            : value + "$";
                    }
                } //格式化显示
            },
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
                type: "item",
                field: "item",
                // disabled:true,
                label: "选项类型"
            },
            {
                label: "无限联动",
                type: "linkage",
                //   fetchConfig: {
                //     apiName: "getFlowNameSelectList"
                //   },
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
                //dateTime yyyy-mm-dd hh:mm:ss
                type: "datetime",
                label: "日期时间",
                field: "datetime", //唯一的字段名 ***必传
                placeholder: "请选择",
                // required: true,
                initialValue: new Date(),
                is24: true //是否是24小时制 默认true
            },
            // //date yyyy-mm-dd
            // {
            //     type: "date",
            //     label: "日期选择",
            //     field: "date", //唯一的字段名 ***必传
            //     placeholder: "请选择"
            // },
            // //time yyyy-mm-dd
            // {
            //     type: "time",
            //     label: "时间选择",
            //     field: "time", //唯一的字段名 ***必传
            //     placeholder: "请选择",
            //     is24: true //是否是24小时制 默认true2
            // },
            // //年月
            // {
            //     type: "month",
            //     label: "年月选择",
            //     field: "month", //唯一的字段名 ***必传
            //     placeholder: "请选择"
            // }, 

            //url
            {
                type: "url",
                label: "网址字段",
                field: "url", //唯一的字段名 ***必传
                placeholder: "请输入"
                // required: true,
            },

            //textarea类型
            {
                type: "textarea",
                label: "多行文本",
                field: "textarea", //唯一的字段名 ***必传
                placeholder: "请输入", //占位符
                rows: 4 //行高 默认4
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

            //files文件上传
            {
                type: "files",
                label: "文件上传",
                field: "files", //唯一的字段名 ***必传
                disabled: true,
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
            {
                type: "richText",
                label: "富文本描述",
                field: "richText", //唯一的字段名 ***必传
                ueditorConfig: {
                    //百度ue配置 可用默认的
                    // toolbars: [["fullscreen", "source", "undo", "redo", "bold"]]
                },
                fetchConfig: {
                    uploadUrl: window.configs.domain + "uploadimage"
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
                canAddForm: true,
                //qnn-form配置
                // initialValue: [{},{}],
                qnnFormConfig: {
                    formConfig: [
                        {
                            type: "select",
                            label: "费用类型",
                            field: "typeOfExpense", //唯一的字段名 ***必传
                            placeholder: "请选择",
                            // required: true,
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
                            label: "费用说明",
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
            }
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
                onClick: function (obj) {
                    console.log(obj);
                }
            },
            {
                label: "拿数据",
                type: "diy",
                isValidate: false, //是否验证表单 默认true
                // affirmDesc: "提交后将无法撤回", //有这文字会点击按钮验证通过时将自动弹出提示
                // affirmYes: "确定", // 确认窗的确定按钮文字 默认确定
                // affirmNo: "取消", //确认窗的取消按钮文字  默认取消
                onClick: function (obj) {
                    console.log('vals：',obj.values);
                }
            }
        ]
    }
};
