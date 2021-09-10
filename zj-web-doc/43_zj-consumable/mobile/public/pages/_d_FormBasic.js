window.FormBasic = {
    // "formItemLayout": {
    //     "labelCol": {
    //         "xs": {
    //             "span": 6
    //         },
    //         "sm": {
    //             "span": 5
    //         }
    //     },
    //     "wrapperCol": {
    //         "xs": {
    //             "span": 18
    //         },
    //         "sm": {
    //             "span": 16
    //         }
    //     }
    // },
    "tailFormItemLayout": {
        "labelCol": {},
        "wrapperCol": {
            "xs": {
                "span": 24
            },
            "sm": {
                "offset": 5,
                "span": 16
            }
        }
    },
    "formConfig": [

        {
            "type": "string",
            "field": "flag",
            "label": "路由取值",
            "isUrlParams": true,
            "onChange": "bind:flagChange",
            "disabled": true,
            "formItemWrapperStyle": {
                "border": "1px solid blue"
            },
            "formItemStyle": {
                "border": "1px solid green"
            },
            "style": {
                "border": "1px solid red"
            },
            "labelStyle": {
                "border": "1px solid red"
            },
            "required": true
        },
        {
            "type": "string",
            "field": "name",
            "label": "可被点击",
            "labelClick": "bind:nameLabelClick",
            "initialValue": "test001",
            "dependencies": [
                "add1"
            ]
        },
        {
            "type": "string",
            "label": "自定义规则",
            "field": "diy.diyRules",
            "placeholder": "请输入",
            "initialValue": "111.22,111.22",
            "diyRules": "bind:diyRulesFn",
            "disabled": true,
        },
        {
            "type": "number",
            "label": "加数1",
            "field": "add1",
            "placeholder": "请输入",
            "required": false
        },
        {
            "type": "number",
            "label": "加数2",
            "field": "add2",
            "placeholder": "请输入",
            "required": false,
            "dependencies": [
                "add1", "name"
            ],
            "hide": "bind:hideFieldHideadd2"
        },
        {
            "type": "number",
            "label": "加数1+加数2",
            "field": "he",
            "addends": [
                "add1",
                "add2",
                "textinput.add3"
            ],
            "placeholder": "请输入",
            "required": false
        },
        {
            "type": "cron",
            "field": "cron",
            "label": "cron",
        },
        {
            "type": "qnnForm",
            "field": "textinput",
            "closeed": true,
            "label": "文本输入框（6）",
            "labelStyle": {
                "color": "red"
            },
            "formFields": [
                {
                    "type": "number",
                    "label": "加数3",
                    "field": "add3",
                    "placeholder": "请输入",
                    "required": false
                },
                {
                    "type": "string",
                    "field": "wxVoice",
                    "label": "微信语音",
                    "voice": true,
                    "help": "string、textarea、number类型输入可配"
                },
                {
                    "type": "money",
                    "label": "金额",
                    "field": "money",
                    "placeholder": "请输入",
                },
                {
                    "type": "string",
                    "field": "string",
                    "label": "普通文本输入"
                },
                {
                    "field": "password",
                    "type": "password",
                    "label": "密码",
                    "placeholder": "请输入..."
                },
                {
                    "field": "item",
                    "type": "item",
                    "label": "选项",
                    "placeholder": "请输入..."
                },
                {
                    "type": "textarea",
                    "field": "textarea",
                    "label": "多文本输入框",
                    // disabled
                },
                {
                    "type": "textarea",
                    "field": "textareaOldValue",
                    "label": "多文本框带对话内容",
                    "oldValueKey": {
                        "text": "text",
                        "time": "time",
                        "name": "name"
                    },
                    "oldValue": [
                        {
                            "text": "今天天气非常好！",
                            "time": "2018.02.12",
                            "name": "张三"
                        },
                        {
                            "style1": {
                                "color": "red"
                            },
                            "style2": {
                                "color": "green"
                            },
                            "style3": {
                                "color": "blue"
                            },
                            "text": "没错！样式可以自定义",
                            "time": "2018.02.11",
                            "name": "李四"
                        },
                        {
                            "text": "nice",
                            "time": "2018.02.10",
                            "name": "王五"
                        }
                    ],
                    "voice": true
                },
                {
                    "type": "richtext",
                    "label": "富文本",
                    "field": "richtext",
                    "fetchConfig": {
                        "uploadUrl": "http://uat.bjzxkj.com:99/apiZt17lw/upload"
                    },
                    "initialValue": "<b>这是初始值</b>"
                }
            ]
        },
        {
            "type": "qnnForm",
            "field": "qnnTable",
            "closeed": true,
            "label": "table组件-数据展示版本（1）",
            "formFields": [
                {
                    "type": "qnnTable",
                    "field": "qnnTable",
                    "qnnTableConfig": {
                        "antd": {
                            "rowKey": "workId"
                        },
                        "isShowRowSelect": false,
                        // "fetchConfig": {
                        //     "apiName": "getTodoList"
                        // },
                        data: [
                            ...Array.from(new Array(5)).map((item, i) => {
                                return {
                                    workId: i + '',
                                    itemName: "单元格" + i,
                                    address: "大连村" + i,
                                    age: i,
                                    sex: "男",
                                    hobby: "0",
                                    birthday: 1582275494301 + i,
                                    year: 1582275494301 + i,
                                    rangeDate: [1582275494301, 1582575494301],
                                }
                            })
                        ],
                        "formConfig": [
                            {
                                "table": {
                                    "title": "流程id",
                                    "dataIndex": "workId",
                                    "width": "40%"
                                },
                                "form": {
                                    "type": "string",
                                    "placeholder": "请输入",
                                    "required": false
                                }
                            },
                            {
                                "table": {
                                    "title": "标题",
                                    "dataIndex": "title",
                                    "width": "40%"
                                },
                                "form": {
                                    "type": "string",
                                    "placeholder": "请输入",
                                    "required": false
                                }
                            },
                            {
                                "table": {
                                    "width": "20%",
                                    "title": "发起时间",
                                    "dataIndex": "createTime",
                                    "format": "YYYY-MM-DD"
                                },
                                "form": {
                                    "type": "string",
                                    "placeholder": "请输入",
                                    "required": false
                                }
                            }
                        ],
                        "actionBtns": []
                    }
                }
            ]
        },

        {
            "type": "qnnForm",
            "field": "qnnTable2",
            "closeed": true,
            "label": "table组件-表格数据存入到表单中（1-1）",
            "formFields": [
                {
                    type: 'qnnTable',
                    label: '表格信息',
                    field: 'qnnTable',
                    //将列表数据存入到表单数据中
                    //开启后将不可进行单字段配置ajax
                    //addRow和del按钮配置的 addCb 和 ajax配置 将无效 因为已经被内置方法接管了
                    //各字段的 tdEditCb 配置将无效 因为已经被内置方法接管了
                    incToForm: true,
                    qnnTableConfig: {
                        antd: {
                            size: "small",
                            rowKey: "id",
                        },
                        paginationConfig: false,
                        actionBtns: [
                            {
                                name: 'addRow',
                                icon: 'plus',
                                type: 'primary',
                                label: '新增行',
                            },
                            {
                                name: 'del',
                                icon: 'del',
                                type: 'danger',
                                label: '删除选择行',
                            }
                        ],
                        formConfig: [
                            {
                                table: {
                                    title: '序号',
                                    dataIndex: 'no',
                                    tdEdit: true,
                                    fieldConfig: {
                                        type: 'string',
                                        required: true
                                    }
                                }
                            },
                            {
                                table: {
                                    title: '公司名称',
                                    dataIndex: 'name',
                                    tdEdit: true,
                                    fieldConfig: {
                                        type: 'string',
                                    }
                                }
                            },
                            {
                                table: {
                                    title: '滑块',
                                    dataIndex: 'slider',
                                    tdEdit: true,
                                },
                                form: {
                                    type: 'number',
                                    
                                },
                            },
                            {
                                table: {
                                    title: '备注',
                                    dataIndex: 'remark',
                                    tdEdit: true,
                                    fieldConfig: {
                                        type: 'string',
                                    }
                                }
                            },
                        ]
                    }
                },
            ]
        },
        {
            "type": "qnnForm",
            "field": "qnnTable3",
            "closeed": true,
            "label": "table组件-表格数据存入到表单中-性能版（1-2）",

            "formFields": [
                {
                    type: 'qnnTable',
                    label: '表格信息',
                    field: 'qnnTable',
                    //将列表数据存入到表单数据中
                    //开启后将不可进行单字段配置ajax
                    //addRow和del按钮配置的 addCb 和 ajax配置 将无效 因为已经被内置方法接管了
                    //各字段的 tdEditCb 配置将无效 因为已经被内置方法接管了
                    incToForm: true,
                    tableTdEdit: true,
                    data: [
                        {
                            id: "0",
                            linkageByTd: "1",
                            linkageOptions: "0-1",
                        }
                    ],
                    qnnTableConfig: {
                        antd: {
                            size: "small",
                            rowKey: "id",
                        },

                        paginationConfig: false,
                        actionBtns: [
                            {
                                name: 'addRow',
                                icon: 'plus',
                                type: 'primary',
                                label: '新增行',
                            },
                            {
                                name: 'del',
                                icon: 'del',
                                type: 'danger',
                                label: '删除选择行',
                            }
                        ],
                        formConfig: [
                            {
                                table: {
                                    title: '序号',
                                    dataIndex: 'no',
                                    tdEdit: true,
                                    fieldConfig: {
                                        type: 'string',
                                        required: true
                                    }
                                }
                            },
                            {
                                table: {
                                    title: '公司名称',
                                    dataIndex: 'name',
                                    tdEdit: true,
                                    fieldConfig: {
                                        type: 'string',
                                    }
                                }
                            },
                            {
                                table: {
                                    title: '备注',
                                    dataIndex: 'remark',
                                    tdEdit: true,
                                    fieldConfig: {
                                        type: 'string',
                                    }
                                }
                            },
                            {
                                table: {
                                    title: "联动下拉",
                                    dataIndex: "linkageByTd",
                                    tdEdit: true,
                                    tdEditFetchConfig: {
                                        apiName: 'getBaseCodeSelect',
                                    },
                                    type: "select",
                                    optionData: [
                                        {
                                            label: "下拉0", value: "0",
                                        },
                                        {
                                            label: "下拉1", value: "1",
                                            children: [{ label: "下拉0-1", value: "0-1" }]
                                        }
                                    ],
                                    //用于表格显示 
                                    fieldConfig: {
                                        optionData: [
                                            {
                                                label: "下拉0", value: "0",
                                            },
                                            {
                                                label: "下拉1", value: "1",
                                                children: [{ label: "下拉0-1", value: "0-1" }]
                                            }
                                        ],

                                        //每一行数据设置不同的type
                                        type: 'select',
                                        onChange: async function (val, obj, tableBtnCallbackFn) {

                                            //设置别的单元格下拉选项(下拉类型的单元格) linkageOptions
                                            const chaildrenOptionData = obj.itemdata?.children || [];
                                            const foo = tableBtnCallbackFn.getCurEditRowFns("linkageOptions");
                                            foo.setOptionData(chaildrenOptionData)

                                            //这个方法层级看情况而定 建议使用this.table.xx形式代替
                                            const realProps = tableBtnCallbackFn.props?.qnnFormProps?.qnnformData?.qnnFormProps || {};
                                            const getLinkageOptionsData = realProps.getLinkageOptionsData
                                            const setLinkageOptionsData = realProps.setLinkageOptionsData
                                            setLinkageOptionsData(chaildrenOptionData)
                                            console.log(getLinkageOptionsData())
                                            // tableBtnCallbackFn.setTdSelectOptions("linkageOptions", chaildrenOptionData);
                                        }
                                    },
                                },
                                form: {
                                    type: "string",
                                    rows: 2,
                                    placeholder: "请输入..."
                                },
                            },
                            {
                                table: {
                                    // width: 120,
                                    title: "联动下拉选项",
                                    dataIndex: "linkageOptions",
                                    tdEdit: true,
                                    fieldsConfig: {
                                        optionConfig: {
                                            label: "label",
                                            value: "value"
                                        },
                                    },
                                    render: (data, rowData, index, tableBtnCallbackFn) => {

                                        //需要显示中文名字
                                        const realProps = tableBtnCallbackFn.props?.qnnFormProps?.qnnformData?.qnnFormProps || {};
                                        const getLinkageOptionsData = realProps.getLinkageOptionsData;
                                        //这里需要注意 如果是有两种情况
                                        //编辑是用getLinkageOptionsData 获取下拉（在业务页面写的方法）
                                        //回显时候需要看情况获取到下拉选项数据然后匹配（这个案例省略该处理）
                                        const optionData = getLinkageOptionsData?.() || [];
                                        return optionData.filter((item) => item.value === data)[0]?.label || data
                                    }
                                },
                                form: {
                                    type: "select",
                                    placeholder: "选择...",
                                },
                            },
                        ]
                    }
                },
            ]
        },


        {
            "type": "qnnForm",
            "field": "dates",
            "closeed": true,
            "label": "日期时间（6）",
            "formFields": [
                {
                    "type": "time",
                    "field": "time",
                    "label": "时分秒"
                },
                {
                    "type": "date",
                    "field": "date",
                    "label": "年月日",
                    "initialValue": "2020-04-23T09:21:17.770Z",
                    "disabled": true
                },
                {
                    "type": "datetime",
                    "field": "datetime",
                    "label": "年月日时分秒"
                },
                {
                    "type": "week",
                    "field": "week",
                    "label": "周选择"
                },
                {
                    "type": "month",
                    "field": "month",
                    "label": "仅选月份"
                },
                {
                    "type": "year",
                    "field": "year",
                    "label": "仅选年份"
                }
            ]
        },
        {
            "type": "qnnForm",
            "field": "rangeDates",
            "closeed": true,
            "label": "日期时间范围（6）",
            "formFields": [
                {
                    "type": "rangeDate",
                    "field": "rangeDate",
                    "label": "日期区域"
                },
                {
                    "type": "rangeDate",
                    "field": "rangeDatetime",
                    "label": "日期时间区域",
                    "showTime": true
                },
                {
                    "type": "rangeDate",
                    "field": "rangeDate_time",
                    "label": "时分秒区域",
                    "picker": "time"
                },
                {
                    "type": "rangeDate",
                    "field": "rangeDate_week",
                    "label": "周区域",
                    "picker": "week"
                },
                {
                    "type": "rangeDate",
                    "field": "rangeDate_month",
                    "label": "月份区域",
                    "picker": "month"
                },
                {
                    "type": "rangeDate",
                    "field": "rangeDate_year",
                    "label": "年份区域",
                    "picker": "year"
                },
                {
                    "type": "rangeDate",
                    "field": "rangeDate_year_dis",
                    "label": "禁用某项",
                    "picker": "year",
                    "disabled": [
                        false,
                        true
                    ],
                    "initialValue": [
                        "2020-04-23T09:21:17.770Z",
                        "2028-05-01T16:00:00.000Z"
                    ]
                }
            ]
        },
        {
            "type": "qnnForm",
            "field": "locInfo",
            "closeed": true,
            "label": "地址信息（7）",
            "formFields": [
                {
                    "type": "string",
                    "field": "wxAddress",
                    "label": "定位地址",
                    "locInfo": "address",
                    "help": "string、textarea类型输入可配"
                },
                {
                    "type": "string",
                    "field": "province",
                    "label": "定位省级",
                    "locInfo": "province",
                    "help": "string、textarea类型输入可配"
                },
                {
                    "type": "string",
                    "field": "city",
                    "label": "定位城市",
                    "locInfo": "city",
                    "help": "string、textarea类型输入可配"
                },
                {
                    "type": "string",
                    "field": "district",
                    "label": "定位区域",
                    "locInfo": "district",
                    "help": "string、textarea类型输入可配"
                },
                {
                    "type": "string",
                    "field": "street",
                    "label": "定位街道",
                    "locInfo": "street",
                    "help": "string、textarea类型输入可配"
                },
                {
                    "type": "string",
                    "field": "longitude",
                    "label": "经度",
                    "locInfo": "longitude",
                    "help": "string、textarea类型输入可配"
                },
                {
                    "type": "string",
                    "field": "latitude",
                    "label": "纬度",
                    "locInfo": "latitude",
                    "help": "string、textarea类型输入可配"
                }
            ]
        },
        {
            "type": "qnnForm",
            "field": "numberBlock",
            "closeed": true,
            "label": "数字类型集合（3）",
            "formFields": [
                {
                    "type": "number",
                    "field": "number1",
                    "label": "普通数字"
                },
                {
                    "type": "integer",
                    "field": "integer",
                    "label": "整数"
                },
                {
                    "type": "number",
                    "field": "precision",
                    "label": "浮点数和最大最小数值",
                    "max": 99,
                    "min": 20,
                    "precision": 2,
                    "help": "精度为两位数，最小数值20, 最大数值99"
                },
                {
                    "type": "number",
                    "field": "formatter",
                    "label": "格式化显示数字"
                }
            ]
        },
        {
            "type": "qnnForm",
            "field": "selectBlock",
            "closeed": true,
            "label": "下拉类型集合（5 + 3）",
            "formFields": [
                {
                    "type": "select",
                    "field": "select",
                    "label": "普通下拉",
                    "optionData": [
                        {
                            "label": "阿里巴巴",
                            "value": "alibaba",
                            "ext1": "第一条数据"
                        },
                        {
                            "label": "腾讯",
                            "value": "tx",
                            "ext1": "第二条数据"
                        },
                        {
                            "label": "百度",
                            "value": "baidu",
                            "ext1": "第三条数据"
                        }
                    ]
                },
                {
                    "type": "select",
                    "field": "selectByMultiple",
                    "label": "多选下拉",
                    "showSearch": true,
                    "multiple": true,
                    "optionData": [
                        {
                            "label": "阿里巴巴",
                            "value": "alibaba",
                            "ext1": "第一条数据"
                        },
                        {
                            "label": "腾讯",
                            "value": "tx",
                            "ext1": "第二条数据"
                        },
                        {
                            "label": "百度",
                            "value": "baidu",
                            "ext1": "第三条数据"
                        }
                    ]
                },
                {
                    "type": "select",
                    "field": "selectByFetch",
                    "label": "下拉数据远程获取",
                    "showSearch": true,
                    "optionConfig": {
                        "label": "title",
                        "value": "workId"
                    },
                    "fetchConfig": {
                        "apiName": "getTodoList"
                    }
                },
                {
                    "type": "selectByPaging",
                    "field": "selectByPaging",
                    "label": "分页下拉",
                    "optionConfig": {
                        "label": "content",
                        "value": "bgId"
                    },
                    "fetchConfig": {
                        "searchKey": "keyword",
                        "apiName": "getSysWebBgList",
                    }
                },
                {
                    "type": "select",
                    "field": "selectByGetFieldToParams",
                    "label": "使用字段值做参数(案例不请求)"
                },
                {
                    "type": "select",
                    "field": "selectByOption",
                    "label": "下拉选项kv配置",
                    "showSearch": true,
                    "optionConfig": {
                        "label": "{{label}} - {{ext1}}",
                        "value": [
                            "value",
                            "label"
                        ]
                    },
                    "optionData": [
                        {
                            "label": "阿里巴巴",
                            "value": "alibaba",
                            "ext1": "第一条数据"
                        },
                        {
                            "label": "腾讯",
                            "value": "tx",
                            "ext1": "第二条数据"
                        },
                        {
                            "label": "百度",
                            "value": "baidu",
                            "ext1": "第三条数据"
                        }
                    ]
                },
                {
                    "type": "select",
                    "field": "selectByLinkageFields",
                    "label": "双向数据配置",
                    "optionConfig": {
                        "linkageFields": {
                            "selectBlock.linkageFieldsTarget": "label"
                        }
                    },
                    "optionData": [
                        {
                            "label": "阿里巴巴",
                            "value": "alibaba",
                            "ext1": "第一条数据"
                        },
                        {
                            "label": "腾讯",
                            "value": "tx",
                            "ext1": "第二条数据"
                        },
                        {
                            "label": "百度",
                            "value": "baidu",
                            "ext1": "第三条数据"
                        }
                    ]
                },
                {
                    "type": "string",
                    "field": "linkageFieldsTarget",
                    "label": "双向数据目标"
                },

                {
                    // 表格下拉
                    type: 'selectByQnnTable',
                    label: '下拉表格-单选',
                    field: 'selectByQnnTable',
                    //用于下拉控件 的输入框显示 选择后的表格数据
                    optionConfig: {
                        value: "itemId",
                        label: "itemName"
                    },
                    //qnnTable组件配置
                    qnnTableConfig: {
                        antd: {
                            rowKey: "itemId"
                        },
                        // selectType:"checkbox",
                        // firstRowIsSearch:true,
                        fetchConfig: {
                            apiName: "getBaseCodeSelect",
                            otherParams: {
                                itemId: "minzhu"
                            }
                        },
                        searchBtnsStyle: "inline",
                        formItemLayoutSearch: {
                            labelCol: {
                                sm: { span: 8 }
                            },
                            wrapperCol: {
                                sm: { span: 16 }
                            }
                        },
                        formConfig: [
                            {
                                isInSearch: true,
                                isInForm: false,
                                table: {
                                    dataIndex: "itemName",
                                    title: "数据名",
                                    // filter:true,
                                    // fieldsConfig:{
                                    //     type:"string"
                                    // }
                                },
                                form: {
                                    type: "string"
                                }
                            },
                            {
                                isInSearch: true,
                                isInForm: false,
                                table: {
                                    dataIndex: "createUserName",
                                    title: "创建人名",
                                },
                                form: {
                                    type: "string"
                                }
                            },
                            {
                                isInForm: false,
                                table: {
                                    dataIndex: "createTime",
                                    title: "创建时间",
                                    format: "YYYY/MM/DD"
                                }
                            }
                        ]
                    }
                },
                {
                    // 表格下拉
                    type: 'selectByQnnTable',
                    label: '下拉表格-多选',
                    field: 'selectByQnnTable2',
                    //用于下拉控件 的输入框显示 选择后的表格数据
                    optionConfig: {
                        value: "itemId",
                        label: "itemName"
                    },
                    //qnnTable组件配置
                    qnnTableConfig: {
                        antd: {
                            rowKey: "itemId"
                        },
                        selectType: "checkbox",
                        firstRowIsSearch: true,
                        fetchConfig: {
                            apiName: "getBaseCodeSelect",
                            otherParams: {
                                itemId: "minzhu"
                            }
                        },
                        searchBtnsStyle: "inline",
                        formItemLayoutSearch: {
                            labelCol: {
                                sm: { span: 8 }
                            },
                            wrapperCol: {
                                sm: { span: 16 }
                            }
                        },
                        formConfig: [
                            {
                                isInForm: false,
                                table: {
                                    dataIndex: "itemName",
                                    title: "数据名",
                                },
                                form: {
                                    type: "string"
                                }
                            },
                            {
                                isInForm: false,
                                table: {
                                    dataIndex: "createUserName",
                                    title: "创建人名",
                                },
                                form: {
                                    type: "string"
                                }
                            },
                            {
                                isInForm: false,
                                table: {
                                    dataIndex: "createTime",
                                    title: "创建时间",
                                    format: "YYYY/MM/DD"
                                }
                            }
                        ]
                    }
                },
            ]
        },
        {
            "type": "qnnForm",
            "field": "cascaderBlock",
            "closeed": true,
            "hide": false,
            "label": "层叠联动（1）",
            "formFields": [
                {
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
            ]
        },
        {
            "type": "qnnForm",
            "closeed": true,
            "hide": false,
            "field": "linkageBlock",
            "label": "无限联动（1）",
            "formFields": [
                {
                    "type": "select",
                    "field": "linkageOne",
                    "label": "无限联动一级",
                    "optionConfig": {
                        "label": "label1",
                        "value": "value",
                        "children": "childrenList"
                    },
                    "optionData": [
                        {
                            "label1": "阿里巴巴",
                            "value": "alibaba",
                            "ext1": "第一条数据",
                            "childrenList": [
                                {
                                    "label1": "淘宝",
                                    "value": "taobao",
                                    "childrenList": [
                                        {
                                            "label1": "天猫",
                                            "value": "tm"
                                        },
                                        {
                                            "label1": "菜鸟",
                                            "value": "cn"
                                        }
                                    ]
                                },
                                {
                                    "label1": "支付宝",
                                    "value": "zhifubao"
                                }
                            ]
                        },
                        {
                            "label1": "腾讯",
                            "value": "tx",
                            "ext1": "第二条数据",
                            "childrenList": [
                                {
                                    "label1": "qq",
                                    "value": "qq"
                                },
                                {
                                    "label1": "微信",
                                    "value": "weixin"
                                }
                            ]
                        },
                        {
                            "label1": "百度",
                            "value": "baidu",
                            "ext1": "第三条数据",
                            "childrenList": [
                                {
                                    "label1": "百度云",
                                    "value": "baiduyun"
                                }
                            ]
                        }
                    ]
                },
                {
                    "type": "select",
                    "field": "linkageTwo",
                    "label": "无限联动二级",
                    "showSearch": true,
                    "optionConfig": {
                        "label": "label1",
                        "value": "value",
                        "children": "childrenList"
                    },
                    "parent": "linkageBlock.linkageOne"
                },
                {
                    "type": "select",
                    "field": "linkageThree",
                    "label": "无限联动三级",
                    "showSearch": true,
                    "optionConfig": {
                        "label": "label1",
                        "value": "value"
                    },
                    "parent": "linkageBlock.linkageTwo"
                },
                {
                    "type": "string",
                    "field": "linkageFour",
                    "label": "无限联动四级",
                    "parent": "linkageBlock.linkageThree"
                }
            ]
        },
        {
            "type": "qnnForm",
            "field": "cangyongv",
            "label": "常用验证（21）",
            "hide": false,
            "closeed": true,
            "formFields": [
                {
                    "type": "email",
                    "field": "email",
                    "label": "邮箱",
                    "help": "eg：3306625609@qq.com"
                },
                {
                    "type": "phone",
                    "field": "phone",
                    "label": "手机号码和座机号码",
                    "help": "eg：0341-86091234、18216811014"
                },
                {
                    "type": "phoneOnly",
                    "field": "phoneOnly",
                    "label": "手机号码",
                    "help": "eg：18216811014"
                },
                {
                    "type": "specialPlane",
                    "field": "specialPlane",
                    "label": "座机号码",
                    "help": "eg：0341-86091234"
                },
                {
                    "type": "url",
                    "field": "url",
                    "label": "网址",
                    "help": "eg：http://baidu.com"
                },
                {
                    "type": "postalCode",
                    "field": "postalCode",
                    "label": "邮政编码",
                    "help": "eg：556884"
                },
                {
                    "type": "birthCertificate",
                    "field": "birthCertificate",
                    "label": "出生证",
                    "help": "eg：0000019"
                },
                {
                    "type": "identity",
                    "field": "identity",
                    "label": "身份证",
                    "help": "eg：522122199902282412"
                },
                {
                    "type": "householdRegister",
                    "field": "householdRegister",
                    "label": "户口本",
                    "help": "eg：522122199902282412"
                },
                {
                    "type": "passport",
                    "field": "passport",
                    "label": "护照",
                    "help": "eg：GUIZhOU"
                },
                {
                    "type": "hongKongPerpetualIdentity",
                    "field": "hongKongPerpetualIdentity",
                    "label": "香港永久性居民身份证",
                    "help": "eg： AB1234567"
                },
                {
                    "type": "taiWanIdentity",
                    "field": "taiWanIdentity",
                    "label": "台湾居民身份证",
                    "help": "eg：U193683453"
                },
                {
                    "type": "trainNumber",
                    "field": "trainNumber",
                    "label": "火车车次",
                    "help": "eg: G1868、D9..."
                },
                {
                    "type": "phoneBodyCode",
                    "field": "phoneBodyCode",
                    "label": "手机机身码",
                    "help": "123456789012345"
                },
                {
                    "type": "creditCode",
                    "field": "creditCode",
                    "label": "统一社会信用代码",
                    "help": "eg: 92371000MA3MXH0E3W"
                },
                {
                    "type": "noLetter",
                    "field": "noLetter",
                    "label": "不能包含字母",
                    "help": "eg: 你好！"
                },
                {
                    "type": "onlyChineseAndNumber",
                    "field": "onlyChineseAndNumber",
                    "label": "只能包含中文和数字",
                    "help": "eg: 你好123456！"
                },
                {
                    "type": "HexadecimalColor",
                    "field": "HexadecimalColor",
                    "label": "16进制颜色",
                    "help": "eg: #f00, #F90, #000, #fe9de8"
                },
                {
                    "type": "qq",
                    "field": "qq",
                    "label": "qq号码",
                    "help": "eg: 3306625609"
                },
                {
                    "type": "weixin",
                    "field": "weixin",
                    "label": "微信号码",
                    "help": "eg: xiaomingdijia"
                },
                {
                    "type": "licensePlateNumber",
                    "field": "licensePlateNumber",
                    "label": "车牌号(新能源+非新能源)",
                    "help": "eg: 京A00599"
                }
            ]
        },
        {
            "type": "qnnForm",
            "field": "diyinput",
            "closeed": true,
            "hide": false,
            "label": "自定义组件（1）",
            "formFields": [
                {
                    "type": "component",
                    "field": "diy",
                    "Component": "myDiyComponent"
                }
            ]
        },
        {
            "type": "qnnForm",
            "field": "treeBlock",
            "closeed": true,
            "hide": false,
            "label": "选人部门/节点选择（2）",
            "formFields": [
                {
                    "label": "拉人组件",
                    "field": "treeSelect",
                    "type": "treeSelect",
                    "initialValue": [
                        {
                            "label": "测试王",
                            "value": "0"
                        }
                    ],
                    "treeSelectOption": {
                        "selectType": "2",
                        "help": true,
                        "treeData": [
                            {
                                "label": "测试王",
                                "value": "0",
                                "type": "2"
                            }
                        ],
                        "search": true,
                        "useCollect": true,
                        "collectApi": "appGetSysFrequentContactsList",
                        "collectApiByAdd": "appAddSysFrequentContacts",
                        "collectApiByDel": "appRemoveSysFrequentContacts",
                        "searchPlaceholder": "姓名、账号、电话",
                        "searchParamsKey": "search",
                        "searchOtherParams": {
                            "pageSize": 999
                        }
                    }
                },
                {
                    "type": "treeNode",
                    "label": "节点选择",
                    "field": "treeNode",
                    "treeNodeOption": {
                        "data": [
                            {
                                "label": "测试王",
                                "value": "0"
                            }
                        ]
                    }
                }
            ]
        },
        {
            "type": "qnnForm",
            "field": "uploadBlock",
            "closeed": true,
            "hide": false,
            "label": "文件上传（4）",
            "formFields": [
                {
                    "type": "files",
                    "label": "文件上传",
                    "field": "files",
                    "desc": "点击上传",
                    "fetchConfig": {
                        "apiName": "http://uat.bjzxkj.com:99/apiZt17lw/upload"
                    },
                    "accept": "image/jpeg",
                    "max": 2
                },
                {
                    "type": "filesDragger",
                    "label": "点击和拖拽",
                    "field": "filesDragger",
                    "desc": "点击或者拖动上传",
                    "subdesc": "只支持单个上传",
                    "fetchConfig": {
                        "apiName": "http://uat.bjzxkj.com:99/apiZt17lw/upload"
                    },
                    "accept": "image/jpeg",
                    "max": 2
                },
                {
                    "type": "camera",
                    "label": "移动文件",
                    "field": "camera",
                    "fetchConfig": {
                        "apiName": "http://uat.bjzxkj.com:99/apiZt17lw/upload"
                    },
                    "cameraConfig": {
                        "showName": false,
                        "type": "files"
                    },
                    "max": 2
                },
                {
                    "type": "camera",
                    "label": "移动文件2",
                    "field": "camera2",
                    "fetchConfig": {
                        "apiName": "http://uat.bjzxkj.com:99/apiZt17lw/upload"
                    },
                    "cameraConfig": {
                        "showName": true,
                        "type": "files"
                    },
                    "max": 2
                },
                {
                    "type": "images",
                    "label": "图片上传",
                    "field": "images",
                    "desc": "",
                    "wrapperStyle": {},
                    "fetchConfig": {
                        "apiName": "http://uat.bjzxkj.com:99/apiZt17lw/upload"
                    },
                    "max": 2
                }
            ]
        },
        {
            "type": "qnnForm",
            "field": "boxinput",
            "closeed": true,
            "hide": false,
            "label": "单选/多选（2）",
            "formFields": [
                {
                    "type": "radio",
                    "label": "单选",
                    "field": "radio",
                    "optionData": [
                        {
                            "label": "测试一",
                            "value": "001",
                            "disabled": true
                        },
                        {
                            "label": "测试二",
                            "value": "002"
                        }
                    ]
                },
                {
                    "type": "checkbox",
                    "label": "多选",
                    "field": "checkbox",
                    "optionData": [
                        {
                            "label": "测试一",
                            "value": "001"
                        },
                        {
                            "label": "测试二",
                            "value": "002"
                        }
                    ]
                },
                {
                    "type": "checkbox",
                    "label": "禁止某项可选",
                    "field": "checkbox1",
                    "optionConfig": {
                        "label": "label1",
                        "disabled": "disabled"
                    },
                    "optionData": [
                        {
                            "label1": "测试一",
                            "value": "001",
                            "disabled": true
                        },
                        {
                            "label1": "测试二",
                            "value": "002"
                        }
                    ]
                }
            ]
        },
        {
            "type": "qnnForm",
            "field": "switchinput",
            "closeed": true,
            "hide": false,
            "label": "打分/滑块/开关（3）",
            "formFields": [
                {
                    "type": "switch",
                    "label": "开关",
                    "field": "switch"
                },
                {
                    "type": "switch",
                    "label": "开关",
                    "field": "switch2",
                    "checkedChildren": "开",
                    "unCheckedChildren": "关"
                },
                {
                    "type": "rate",
                    "label": "打分",
                    "field": "rate"
                },
                {
                    "type": "rate",
                    "label": "打分自定义",
                    "field": "rate1",
                    "character": "好"
                },
                {
                    "type": "slider",
                    "label": "滑块",
                    "field": "slider",
                    "marks": {
                        "0": "0",
                        "20": "20",
                        "40": "40",
                        "60": "60",
                        "80": "80",
                        "100": "100"
                    }
                },
                {
                    "type": "slider",
                    "label": "滑块",
                    "field": "slider2",
                    "marks": {
                        "0": "不及格",
                        "60": "及格",
                        "100": "优秀"
                    }
                },
                {
                    "type": "slider",
                    "label": "双向滑块",
                    "field": "slider3",
                    "range": true,
                    "disabled": true,
                    "marks": {
                        "0": "0",
                        "20": "20",
                        "40": "40",
                        "60": "60",
                        "80": "80",
                        "100": "100"
                    }
                }
            ]
        },
        {
            "type": "qnnForm",
            "field": "showAndHide",
            "closeed": true,
            "hide": false,
            "label": "字段显隐/禁用方式（3）",
            "formFields": [
                {
                    "type": "string",
                    "field": "disabledBoolean",
                    "label": "布尔值设置",
                    "disabled": true,
                    "initialValue": "这是个禁用字段"
                },
                {
                    "type": "string",
                    "field": "disabled1",
                    "label": "禁用方法",
                    "dependencies": [
                        "showAndHide.hide"
                    ],
                    "disabled": "bind:disabledDisabled",
                    "help": "[隐藏方法]等于1禁用",
                    "placeholder": "placeholder测试",
                    "initialValue": "3"
                },
                {
                    "type": "string",
                    "field": "hide",
                    "label": "隐藏方法",
                    "dependencies": [
                        "showAndHide.disabled1"
                    ],
                    "hide": "bind:hideFieldHide",
                    "help": "[禁用方法]等于1隐藏"
                },
                {
                    "type": "string",
                    "field": "condition",
                    "label": "条件配置",
                    "dependencies": [
                        "showAndHide.disabled1"
                    ],
                    "help": "[禁用方法]等于5禁用，等于6隐藏，其余情况正常显示",
                    "condition": [
                        {
                            "regex": {
                                "showAndHide.disabled1": "5"
                            },
                            "action": "disabled"
                        },
                        {
                            "regex": {
                                "showAndHide.disabled1": "6"
                            },
                            "action": "hide"
                        }
                    ]
                }
            ]
        },
        {
            "type": "qnnForm",
            "field": "blockByCanAdd",
            "label": "可增减表单块（1 + 1[普通表单块]）",
            "hide": false,
            "canAddForm": true,
            "initialValue": [],
            "formFields": [
                {
                    "type": "string",
                    "field": "name",
                    "label": "姓名"
                },
                {
                    "type": "number",
                    "field": "money",
                    "label": "资产"
                }
            ]
        }
    ],
    "btns": [
        {
            "field": "submit",
            "label": "提交",
            "type": "primary",
            "fetchConfig": {
                "apiName": "submit"
            },
            "affirmTitle": "确认提交吗？",
            "affirmDesc": "提交后将无法撤回",
            "affirmYes": "确定",
            "affirmNo": "取消"
        },
        {
            "field": "diyClick",
            "label": "不验证",
            "type": "primary",
            "ghost": true,
            "onClick": "bind:diyClick",
            "isValidate": false,
            "condition": [
                {
                    "regex": {
                        "name": "1"
                    },
                    "action": "disabled"
                }
            ]
        },
        {
            "field": "diyClick2",
            "label": "切换换请求参数",
            "type": "primary",
            "ghost": true,
            "onClick": "bind:diyClick2",
            "isValidate": false,
            "hide": "bind:mobileHide"
        },
        {
            "field": "diyClick3",
            "label": "调用刷新方法",
            "type": "primary",
            "ghost": true,
            "onClick": "bind:diyClick3",
            "isValidate": false,
            "hide": "bind:mobileHide"
        },
        {
            "field": "diyClick4",
            "label": "使用props更改配置",
            "type": "primary",
            "ghost": true,
            "onClick": "bind:diyClick4",
            "isValidate": false,
            "hide": "bind:mobileHide"
        },
        {
            "field": "setValues",
            "label": "设置值",
            "type": "primary",
            "ghost": true,
            "onClick": "bind:setValues",
            "isValidate": false
        }
    ],
    "type": "qnnTable"
}