window.FormBasic = {
    formItemLayout: {
        labelCol: {
            xs: { span: 6 },
            sm: { span: 5 }
        },
        wrapperCol: {
            xs: { span: 18 },
            sm: { span: 16 }
        }
    },

    tailFormItemLayout: {
        labelCol: {},
        wrapperCol: {
            xs: { span: 24 },
            sm: { offset: 5,span: 16 },
        }
    },

    formConfig: [
        {
            type: "string",
            field: "itemName",
            label: "路由取值",
            isUrlParams: true,
            onChange: "bind:flagChange",
            disabled: true,
            formItemWrapperStyle: {
                border: "1px solid blue",
            },
            formItemStyle: {
                border: "1px solid green",
            },
            style: {
                border: "1px solid red",
            },
            labelStyle: {
                border: "1px solid red",
            },
            required: true,
            // noStyle:true
        },


        {
            type: "string",
            field: "name",
            label: "可被点击",
            labelClick: "bind:nameLabelClick",
            initialValue: "test001",
            disabled: (obj) => {
                var add1 = obj.form.getFieldValue('add1')
                return add1 === 2
            },
            dependencies: ["add1"]
        },

        {
            type: "string",
            label: "自定义规则",
            field: "diy.diyRules",
            placeholder: "请输入",
            initialValue: "111.22,111.22",
            diyRules: "bind:diyRulesFn",
            disabled: true,
            required: true
        },

        {
            type: 'number',
            label: '加数1',
            field: 'add1',
            placeholder: '请输入',
            required: false,
        },
        {
            type: 'number',
            label: '加数2',
            field: 'add2',
            placeholder: '请输入',
            required: false,
            dependencies: ["add1"],
            hide: "bind:hideFieldHideadd2",
        },

        {
            type: 'number',
            label: '加数1+加数2',
            field: 'he',
            addends: ['add1','add2','textinput.add3'],
            placeholder: '请输入',
            required: false,
            // disabled: true,
        },
        {
            type: "qnnForm",
            field: "textinput",
            closeed: true,
            label: "文本输入框（6）",
            labelStyle: { color: "red" },
            formFields: [
                {
                    type: 'number',
                    label: '加数3',
                    field: 'add3',
                    placeholder: '请输入',
                    required: false,
                },
                {
                    type: "string",
                    field: "wxVoice",
                    label: "微信语音",
                    voice: true,
                    help: "string、textarea、number类型输入可配",
                },
                {
                    type: "money",
                    label: "金额",
                    field: "money",
                    placeholder: "请输入",
                    required: true
                },
                {
                    type: "string",
                    field: "string",
                    label: "普通文本输入",
                },
                {
                    field: 'password',
                    type: "password",
                    label: "密码",
                    placeholder: "请输入...",
                },
                {
                    field: 'item',
                    type: "item",
                    label: "选项",
                    placeholder: "请输入...",
                },
                {
                    type: "textarea",
                    field: "textarea",
                    label: "多文本输入框",
                },
                {
                    type: "textarea",
                    field: "textareaOldValue",
                    label: "多文本框带对话内容",
                    oldValueKey: {
                        text: "text",
                        time: "time",
                        name: "name"
                    },
                    oldValue: [
                        {
                            text: "今天天气非常好！",
                            time: "2018.02.12",
                            name: "张三"
                        },
                        {
                            //可单独设置样式
                            style1: { color: 'red' },
                            style2: { color: 'green' },
                            style3: { color: 'blue' },

                            text: "没错！样式可以自定义",
                            time: "2018.02.11",
                            name: "李四",
                        },
                        {
                            text: "nice",
                            time: "2018.02.10",
                            name: "王五"
                        },
                    ],
                    voice: true,
                },
                {
                    type: "richtext",
                    label: "富文本",
                    field: "richtext", //唯一的字段名 ***必传
                    fetchConfig: {
                        //必须配置  上传图片地址
                        uploadUrl: window.configs.domain + 'upload' //***必传
                    },
                    initialValue: "<b>这是初始值</b>"
                },

            ]
        },
        {
            type: "qnnForm",
            field: "qnnTable",
            closeed: true,
            label: "table组件（1）",
            formFields: [
                {
                    type: 'qnnTable',
                    // label: "table组件",
                    field: "qnnTable",
                    qnnTableConfig: {
                        antd: { rowKey: "workId" },
                        isShowRowSelect: false,
                        fetchConfig: {
                            apiName: "getTodoList"
                        },
                        formConfig: [
                            {
                                table: {
                                    title: '流程id',
                                    dataIndex: 'workId',
                                    width: "40%",
                                }
                                ,form: {
                                    type: 'string',
                                    placeholder: '请输入',
                                    required: false,
                                }
                            },
                            {
                                table: {
                                    title: '标题',
                                    dataIndex: 'title',
                                    width: "40%",
                                }
                                ,form: {
                                    type: 'string',
                                    placeholder: '请输入',
                                    required: false,
                                }
                            },
                            {
                                table: {
                                    width: "20%",
                                    title: '发起时间',
                                    dataIndex: 'createTime',
                                    format: "YYYY-MM-DD"
                                }
                                ,form: {
                                    type: 'string',
                                    placeholder: '请输入',
                                    required: false,
                                }
                            }

                        ],
                        actionBtns: []
                    }
                },
            ]
        },
        {
            type: "qnnForm",
            field: "dates",
            closeed: true,
            // hide: false,
            label: "日期时间（6）",
            formFields: [

                {
                    type: "time",
                    field: "time",
                    label: "时分秒",
                },
                {
                    type: "date",
                    field: "date",
                    label: "年月日",
                    initialValue: new Date(),
                    disabled: true
                },
                {
                    type: "datetime",
                    field: "datetime",
                    label: "年月日时分秒",
                },
                {
                    type: "week",
                    field: "week",
                    label: "周选择",
                },
                {
                    type: "month",
                    field: "month",
                    label: "仅选月份",
                },
                {
                    type: "year",
                    field: "year",
                    label: "仅选年份",
                },

            ]
        },
        {
            type: "qnnForm",
            field: "rangeDates",
            closeed: true,
            // hide: false,
            label: "日期时间范围（6）",
            formFields: [
                {
                    type: "rangeDate",
                    field: "rangeDate",
                    label: "日期区域",
                },
                {
                    type: "rangeDate",
                    field: "rangeDatetime",
                    label: "日期时间区域",
                    showTime: true
                },
                {
                    type: "rangeDate",
                    field: "rangeDate_time",
                    label: "时分秒区域",
                    picker: "time"
                },
                {
                    type: "rangeDate",
                    field: "rangeDate_week",
                    label: "周区域",
                    picker: "week"
                },
                {
                    type: "rangeDate",
                    field: "rangeDate_month",
                    label: "月份区域",
                    picker: "month"
                },
                {
                    type: "rangeDate",
                    field: "rangeDate_year",
                    label: "年份区域",
                    picker: "year"
                },

                {
                    type: "rangeDate",
                    field: "rangeDate_year_dis",
                    label: "禁用某项",
                    picker: "year",
                    disabled: [false,true],
                    initialValue: [new Date(),new Date("2028-05-2")]
                },
            ]
        },
        {
            type: "qnnForm",
            field: "locInfo",
            closeed: true,
            // hide: false,
            label: "地址信息（7）",
            formFields: [
                {
                    type: "string",
                    field: "wxAddress",
                    label: "定位地址",
                    locInfo: "address",
                    help: "string、textarea类型输入可配",
                },
                {
                    type: "string",
                    field: "province",
                    label: "定位省级",
                    locInfo: "province",
                    help: "string、textarea类型输入可配",
                },
                {
                    type: "string",
                    field: "city",
                    label: "定位城市",
                    locInfo: "city",
                    help: "string、textarea类型输入可配",
                },
                {
                    type: "string",
                    field: "district",
                    label: "定位区域",
                    locInfo: "district",
                    help: "string、textarea类型输入可配",
                },
                {
                    type: "string",
                    field: "street",
                    label: "定位街道",
                    locInfo: "street",
                    help: "string、textarea类型输入可配",
                },
                {
                    type: "string",
                    field: "longitude",
                    label: "经度",
                    locInfo: "longitude",
                    help: "string、textarea类型输入可配",
                },
                {
                    type: "string",
                    field: "latitude",
                    label: "纬度",
                    locInfo: "latitude",
                    help: "string、textarea类型输入可配",
                },

            ]
        },
        {
            type: "qnnForm",
            field: "numberBlock",
            closeed: true,
            // hide: false,
            label: "数字类型集合（3）",
            formFields: [
                {
                    type: "number",
                    field: "number1",
                    label: "普通数字",
                },
                {
                    type: "integer",
                    field: "integer",
                    label: "整数",
                },
                {
                    type: "number",
                    field: "precision",
                    label: "浮点数和最大最小数值",
                    max: 99,
                    min: 20,
                    precision: 2, //数值精度
                    help: "精度为两位数，最小数值20, 最大数值99"
                },
                {
                    type: "number",
                    field: "formatter",
                    label: "格式化显示数字",
                    //指定输入框展示值的格式
                    formatter: value => `$ ${value}`.replace(/\B(?=(\d{3})+(?!\d))/g,','),
                    // 指定从 formatter 里转换回数字的方式，和 formatter 搭配使用	funct
                    parser: value => value.replace(/\$\s?|(,*)/g,'')
                },

            ]
        },
        {
            type: "qnnForm",
            field: "selectBlock",
            closeed: true,
            label: "下拉类型集合（4 + 3）",
            formFields: [
                {
                    type: "select",
                    field: "select",
                    label: "普通下拉",
                    // showSearch: true,
                    onChange: function (val) {
                        console.log(val)
                    },
                    optionData: [
                        {
                            label: "阿里巴巴",
                            value: "alibaba",
                            ext1: "第一条数据"
                        },
                        {
                            label: "腾讯",
                            value: "tx",
                            ext1: "第二条数据"
                        },
                        {
                            label: "百度",
                            value: "baidu",
                            ext1: "第三条数据"
                        }
                    ]
                },
                {
                    type: "select",
                    field: "selectByMultiple",
                    label: "多选下拉",
                    showSearch: true,
                    multiple: true,
                    optionData: [
                        {
                            label: "阿里巴巴",
                            value: "alibaba",
                            ext1: "第一条数据"
                        },
                        {
                            label: "腾讯",
                            value: "tx",
                            ext1: "第二条数据"
                        },
                        {
                            label: "百度",
                            value: "baidu",
                            ext1: "第三条数据"
                        }
                    ]
                },

                {
                    type: "select",
                    field: "selectByFetch",
                    label: "下拉数据远程获取",
                    showSearch: true,
                    optionConfig: {
                        label: "title",
                        value: "workId",
                    },
                    fetchConfig: {
                        apiName: "getTodoList"
                    }
                },

                {
                    type: "selectByPaging",
                    field: "selectByPaging",
                    label: "分页下拉",
                    optionConfig: {
                        label: "title",
                        value: "workId",
                    },
                    fetchConfig: {
                        searchKey: "keyword",
                        apiName: "getTodoList",
                    }
                },

                {
                    type: "select",
                    field: "selectByGetFieldToParams",
                    label: "使用字段值做参数",
                    optionConfig: {
                        label: "title",
                        value: "workId",
                    },
                    fetchConfig: {
                        searchKey: "keyword",
                        apiName: "getTodoList",
                        params: {
                            aaa: "selectByPaging"
                        }
                    }
                },

                {
                    type: "select",
                    field: "selectByOption",
                    label: "下拉选项kv配置",
                    showSearch: true,
                    optionConfig: {
                        label: "{{label}} - {{ext1}}",
                        value: ["value","label"]
                    },
                    optionData: [
                        {
                            label: "阿里巴巴",
                            value: "alibaba",
                            ext1: "第一条数据"
                        },
                        {
                            label: "腾讯",
                            value: "tx",
                            ext1: "第二条数据"
                        },
                        {
                            label: "百度",
                            value: "baidu",
                            ext1: "第三条数据"
                        }
                    ]
                },

                {
                    type: "select",
                    field: "selectByLinkageFields",
                    label: "双向数据配置",
                    optionConfig: {
                        //与其他字段双向绑定配置
                        linkageFields: {
                            "selectBlock.linkageFieldsTarget": "label",
                            // "linkageFour": "label"
                        }
                    },
                    optionData: [
                        {
                            label: "阿里巴巴",
                            value: "alibaba",
                            ext1: "第一条数据"
                        },
                        {
                            label: "腾讯",
                            value: "tx",
                            ext1: "第二条数据"
                        },
                        {
                            label: "百度",
                            value: "baidu",
                            ext1: "第三条数据"
                        }
                    ]
                },
                {
                    type: "string",
                    field: "linkageFieldsTarget",
                    label: "双向数据目标",
                }
            ]
        },

        {
            type: "qnnForm",
            field: "cascaderBlock",
            closeed: true,
            hide: false,
            label: "层叠联动（1）",
            formFields: [
                {
                    type: "cascader",
                    label: "层叠选择",
                    field: "cascader",
                    optionConfig: {
                        label: "itemName",
                        value: "itemId",
                        children: "children"
                    },
                    fetchConfig: {
                        apiName: "getBaseCodeTree",
                        otherParams: {
                            itemId: "xingzhengquhuadaima"
                        }
                    }
                },
            ]
        },
        {
            type: "qnnForm",
            closeed: true,
            hide: false,
            field: "linkageBlock",
            label: "无限联动（1）",
            formFields: [
                {
                    type: "select",
                    field: "linkageOne",
                    label: "无限联动一级",
                    optionConfig: {
                        label: "label1",
                        value: "value",
                        children: "childrenList"
                    },
                    optionData: [
                        {
                            label1: "阿里巴巴",
                            value: "alibaba",
                            ext1: "第一条数据",
                            childrenList: [
                                {
                                    label1: "淘宝",
                                    value: "taobao",
                                    childrenList: [
                                        {
                                            label1: "天猫",
                                            value: "tm",
                                        },
                                        {
                                            label1: "菜鸟",
                                            value: "cn",
                                        }
                                    ]
                                },
                                {
                                    label1: "支付宝",
                                    value: "zhifubao",
                                }
                            ]
                        },
                        {
                            label1: "腾讯",
                            value: "tx",
                            ext1: "第二条数据",
                            childrenList: [
                                {
                                    label1: "qq",
                                    value: "qq",
                                },
                                {
                                    label1: "微信",
                                    value: "weixin",
                                }
                            ]
                        },
                        {
                            label1: "百度",
                            value: "baidu",
                            ext1: "第三条数据",
                            childrenList: [
                                {
                                    label1: "百度云",
                                    value: "baiduyun",
                                }
                            ]
                        }
                    ]
                },

                {
                    type: "select",
                    field: "linkageTwo",
                    label: "无限联动二级",
                    showSearch: true,
                    optionConfig: {
                        label: "label1",
                        value: "value",
                        children: "childrenList"
                    },
                    //父级
                    parent: "linkageBlock.linkageOne",
                    // parent: "linkageOne",
                },
                {
                    type: "select",
                    field: "linkageThree",
                    label: "无限联动三级",
                    showSearch: true,
                    optionConfig: {
                        label: "label1",
                        value: "value",
                    },
                    //父级
                    parent: "linkageBlock.linkageTwo",
                    // parent: "linkageTwo",
                },
                {
                    type: "string",
                    field: "linkageFour",
                    label: "无限联动四级",
                    //父级
                    parent: "linkageBlock.linkageThree",
                    // parent: "linkageThree",
                },

            ]
        },

        {
            type: "qnnForm",
            field: "cangyongv",
            label: "常用验证（21）",
            hide: false,
            closeed: true,
            formFields: [
                {
                    type: "email",
                    field: "email",
                    label: "邮箱",
                    help: "eg：3306625609@qq.com"
                },
                {
                    type: "phone",
                    field: "phone",
                    label: "手机号码和座机号码",
                    help: "eg：0341-86091234、18216811014"
                },
                {
                    type: "phoneOnly",
                    field: "phoneOnly",
                    label: "手机号码",
                    help: "eg：18216811014"
                },
                {
                    type: "specialPlane",
                    field: "specialPlane",
                    label: "座机号码",
                    help: "eg：0341-86091234"
                },
                {
                    type: "url",
                    field: "url",
                    label: "网址",
                    help: "eg：http://baidu.com"
                },
                {
                    type: "postalCode",
                    field: "postalCode",
                    label: "邮政编码",
                    help: "eg：556884"
                },
                {
                    type: "birthCertificate",
                    field: "birthCertificate",
                    label: "出生证",
                    help: "eg：0000019"
                },
                {
                    type: "identity",
                    field: "identity",
                    label: "身份证",
                    help: "eg：522122199902282412"
                },
                {
                    type: "householdRegister",
                    field: "householdRegister",
                    label: "户口本",
                    help: "eg：522122199902282412"
                },
                {
                    type: "passport",
                    field: "passport",
                    label: "护照",
                    help: "eg：GUIZhOU"
                },
                {
                    type: "hongKongPerpetualIdentity",
                    field: "hongKongPerpetualIdentity",
                    label: "香港永久性居民身份证",
                    help: "eg： AB1234567"
                },
                {
                    type: "taiWanIdentity",
                    field: "taiWanIdentity",
                    label: "台湾居民身份证",
                    help: "eg：U193683453"
                },
                {
                    type: "trainNumber",
                    field: "trainNumber",
                    label: "火车车次",
                    help: "eg: G1868、D9..."
                },
                {
                    type: "phoneBodyCode",
                    field: "phoneBodyCode",
                    label: "手机机身码",
                    help: "123456789012345"
                },
                {
                    type: "creditCode",
                    field: "creditCode",
                    label: "统一社会信用代码",
                    help: "eg: 92371000MA3MXH0E3W"
                },

                {
                    type: "noLetter",
                    field: "noLetter",
                    label: "不能包含字母",
                    help: "eg: 你好！"
                },
                {
                    type: "onlyChineseAndNumber",
                    field: "onlyChineseAndNumber",
                    label: "只能包含中文和数字",
                    help: "eg: 你好123456！"
                },
                {
                    type: "HexadecimalColor",
                    field: "HexadecimalColor",
                    label: "16进制颜色",
                    help: "eg: #f00, #F90, #000, #fe9de8"
                },
                {
                    type: "qq",
                    field: "qq",
                    label: "qq号码",
                    help: "eg: 3306625609"
                },
                {
                    type: "weixin",
                    field: "weixin",
                    label: "微信号码",
                    help: "eg: xiaomingdijia"
                },
                {
                    type: "licensePlateNumber",
                    field: "licensePlateNumber",
                    label: "车牌号(新能源+非新能源)",
                    help: "eg: 京A00599"
                },

            ]
        },
        {
            type: "qnnForm",
            field: "diyinput",
            closeed: true,
            hide: false,
            label: "自定义组件（1）",
            formFields: [
                {
                    type: 'component',
                    field: 'diy',
                    Component: "myDiyComponent",
                },

            ]
        },
        {
            type: "qnnForm",
            field: "treeBlock",
            closeed: true,
            hide: false,
            label: "选人部门/节点选择（2）",
            formFields: [
                {
                    label: '拉人组件',
                    field: 'treeSelect',
                    type: 'treeSelect',
                    initialValue: [],
                    treeSelectOption: {
                        selectType: "2",
                        help: true,
                        fetchConfig: {
                            apiName: 'getSysDepartmentUserAllTree',
                        },
                        search: true,
                        useCollect: true,  //使用收藏功能
                        collectApi: "appGetSysFrequentContactsList",  //查询收藏人员     接受后台参数[{xx:xxx,...}]
                        collectApiByAdd: "appAddSysFrequentContacts", //新增收藏人员   传给后台的参数[{xx:xxx,...}]
                        collectApiByDel: "appRemoveSysFrequentContacts", //删除收藏人员  传给后台的参数[{xx:xxx,...}]
                        searchPlaceholder: '姓名、账号、电话',
                        // searchApi:'getSysDepartmentUserAllTree',  //搜索时调用的api  [string]
                        searchParamsKey: 'search',//搜索文字的K 默认是'searchText'   [string]
                        searchOtherParams: { pageSize: 999 }//搜索时的其他参数  [object]
                    }
                },
                {
                    type: "treeNode",
                    label: "节点选择",
                    field: "treeNode", //唯一的字段名 ***必传
                    // disabled: true,
                    treeNodeOption: {
                        keys: {
                            label: "itemName",
                            value: "codeId",
                            children: "getBaseCodeList"
                        },
                        fetchConfig: {
                            parmasKey: "codePid",  //点击节点后将节点id赋值该key上传递给后台
                            apiName: "getBaseCodeList",
                            searchApiName: "",
                            params: {
                                codePid: "0"
                            }
                        },
                    }
                },
            ]
        },
        {
            type: "qnnForm",
            field: "uploadBlock",
            closeed: true,
            hide: false,
            label: "文件上传（4）",
            formFields: [
                {
                    type: 'files',
                    label: '文件上传',
                    field: 'files',
                    // required: true,
                    desc: '点击上传',
                    fetchConfig: {
                        apiName: window.configs.domain + 'upload',
                    },
                    accept: 'image/jpeg',
                    max: 2,
                },
                {
                    type: 'filesDragger',
                    label: '点击和拖拽',
                    field: 'filesDragger', //唯一的字段名 ***必传
                    required: true,//是否必填
                    desc: '点击或者拖动上传', //默认 点击或者拖动上传
                    subdesc: '只支持单个上传',//默认空
                    fetchConfig: {
                        apiName: window.configs.domain + 'upload',
                    },
                    accept: 'image/jpeg', //支持上传的类型 默认都支持  格式"image/gif, image/jpeg"
                    max: 2, //最大上传数量 
                },

                {
                    type: 'camera',
                    label: '移动文件',
                    field: 'camera', //唯一的字段名 ***必传 
                    fetchConfig: {
                        apiName: window.configs.domain + 'upload',
                    },
                    cameraConfig: {
                        showName: false, //显示文件名字  默认false
                        //三种可选类型  其中camera 直接打开相， files和images是打开文件夹。预览时camera和images是判断平台，如微信使用微信预览图片，files类型直接打开地址
                        type: "files", //  (images | files | camera)
                        //accept: 'image/jpeg', //支持上传的类型 默认都支持  格式"image/gif, image/jpeg"   选填
                    },
                    required: true,
                    max: 2, //最大上传数量
                    // disabled:true
                },
                {
                    type: 'camera',
                    label: '移动文件2',
                    field: 'camera2', //唯一的字段名 ***必传 
                    fetchConfig: {
                        apiName: window.configs.domain + 'upload',
                    },
                    cameraConfig: {
                        showName: true, //显示文件名字  默认false
                        //三种可选类型  其中camera 直接打开相， files和images是打开文件夹。预览时camera和images是判断平台，如微信使用微信预览图片，files类型直接打开地址
                        type: "files", //  (images | files | camera)
                        //accept: 'image/jpeg', //支持上传的类型 默认都支持  格式"image/gif, image/jpeg"   选填
                    },
                    max: 2, //最大上传数量
                    // disabled:true
                },

                {
                    type: 'images',
                    label: '图片上传',
                    field: 'images', //唯一的字段名 ***必传
                    required: true,//是否必填
                    desc: '', //默认  上传 
                    wrapperStyle: {}, //容器样式
                    fetchConfig: {
                        apiName: window.configs.domain + 'upload',
                        // name:'123', //上传文件的name 默认空
                    },
                    max: 2, //最大上传数量
                    // disabled:true
                },
            ]
        },
        {
            type: "qnnForm",
            field: "boxinput",
            closeed: true,
            hide: false,
            label: "单选/多选（2）",
            formFields: [
                {
                    type: "radio",
                    label: "单选",
                    field: "radio",
                    optionData: [
                        {
                            label: "测试一",
                            value: "001",
                            disabled: true
                        },
                        {
                            label: "测试二",
                            value: "002"
                        }
                    ]
                },
                {
                    type: "checkbox",
                    label: "多选",
                    field: "checkbox",
                    optionData: [
                        {
                            label: "测试一",
                            value: "001",
                        },
                        {
                            label: "测试二",
                            value: "002"
                        }
                    ]
                    // optionConfig: {
                    //     label: "itemName",
                    //     value: "itemId",
                    //     children: "children"
                    // },
                    // fetchConfig: {
                    //     apiName: "getBaseCodeTree",
                    //     otherParams: {
                    //         itemId: "xingzhengquhuadaima"
                    //     }
                    // }
                },
                {
                    type: "checkbox",
                    label: "禁止某项可选",
                    field: "checkbox1",
                    optionConfig: {
                        label: "label1",
                        disabled: "disabled"
                    },
                    optionData: [
                        {
                            label1: "测试一",
                            value: "001",
                            disabled: true
                        },
                        {
                            label1: "测试二",
                            value: "002"
                        }
                    ]
                },
            ]
        },
        {
            type: "qnnForm",
            field: "switchinput",
            closeed: true,
            hide: false,
            label: "打分/滑块/开关（3）",
            formFields: [
                {
                    type: "switch",
                    label: "开关",
                    field: "switch",
                },
                {
                    type: "switch",
                    label: "开关",
                    field: "switch2",
                    checkedChildren: "开",
                    unCheckedChildren: "关",
                },

                {
                    type: "rate",
                    label: "打分",
                    field: "rate",
                },
                {
                    type: "rate",
                    label: "打分自定义",
                    field: "rate1",
                    character: "好"
                },
                {
                    type: "slider",
                    label: "滑块",
                    field: "slider",
                    marks: {
                        0: 'A',20: 'B',40: 'C',60: 'D',80: 'E',100: 'F',
                    }
                },
                {
                    type: "slider",
                    label: "滑块",
                    field: "slider2",
                    marks: {
                        0: '不及格',60: '及格',100: '优秀'
                    }
                },
                {
                    type: "slider",
                    label: "双向滑块",
                    field: "slider3",
                    range: true,
                    disabled: true,
                    marks: {
                        0: 'A',20: 'B',40: 'C',60: 'D',80: 'E',100: 'F',
                    }
                },
            ]
        },
        {
            type: "qnnForm",
            field: "showAndHide",
            closeed: true,
            hide: false,
            label: "字段显隐/禁用方式（3）",
            formFields: [
                {
                    type: "string",
                    field: "disabledBoolean",
                    label: "布尔值设置",
                    disabled: true,
                    // hide:true,//隐藏
                    initialValue: "这是个禁用字段"
                },

                {
                    type: "string",
                    field: "disabled1",
                    label: "禁用方法",
                    dependencies: ["showAndHide.hide"],
                    disabled: "bind:disabledDisabled",
                    help: "[隐藏方法]等于1禁用",
                    placeholder: "placeholder测试",
                    initialValue: "3"
                },
                {
                    type: "string",
                    field: "hide",
                    label: "隐藏方法",
                    dependencies: ["showAndHide.disabled1"],
                    hide: "bind:hideFieldHide",
                    help: "[禁用方法]等于1隐藏",
                },

                {
                    type: "string",
                    field: "condition",
                    label: "条件配置",
                    dependencies: ["showAndHide.disabled1"],
                    help: "[禁用方法]等于5禁用，等于6隐藏，其余情况正常显示",
                    condition: [
                        {
                            regex: {
                                //该对象相当于 [&&条件]
                                "showAndHide.disabled1": "5",
                            },
                            action: 'disabled',
                        },
                        {
                            regex: {
                                "showAndHide.disabled1": "6",
                            },
                            action: 'hide',
                        }
                    ]
                },
            ]
        },

        {
            type: "qnnForm",
            field: "blockByCanAdd",
            label: "可增减表单块（1 + 1[普通表单块]）",
            hide: false,
            canAddForm: true,
            initialValue: [{},{}],
            disabled: true,
            formFields: [
                {
                    type: "string",
                    field: "name",
                    label: "姓名",
                },
                {
                    type: "number",
                    field: "money",
                    label: "资产",
                }
            ]
        },
    ],

    btns: [
        {
            field: "submit",
            label: "提交",
            type: 'primary', //primary dashed danger
            fetchConfig: {
                //api 默认提交整个表单的数据
                apiName: 'submit',
                //此参数存在将不会提交全部表单参数而是选取 params 里的参数提交
                //提交时需要字段改名获取后台只需要几个字段时会用到
                // params:{k:field},
                // delParams:[field,...], //删除不需要提交的参数
                //定死参数
                // otherParams:{test:'111'}
            },
            onClick: function (obj) { //此时里面会多一个 response
                console.log(obj)
            },
            // isValidate: false,

            //当事件确定要发生时需要提醒用户时用到

            affirmTitle: '确认提交吗？',//有这文字会点击按钮验证通过时将自动弹出提示
            affirmDesc: '提交后将无法撤回',//有这文字会点击按钮验证通过时将自动弹出提示
            affirmYes: '确定',// 确认窗的确定按钮文字 默认确定
            affirmNo: '取消',//确认窗的取消按钮文字  默认取消 
        },
        {
            field: "diyClick",
            label: '不验证',
            type: 'primary', //primary dashed danger 
            ghost: true,
            onClick: "bind:diyClick",
            isValidate: false,
            condition: [
                {//条件
                    regex: {//匹配规则 正则或者字符串
                        name: '1',
                    },
                    action: 'disabled', //disabled, show, hide, function(){}
                }
            ]
        },
        {
            field: "diyClick2",
            label: '切换换请求参数',
            type: 'primary', //primary dashed danger 
            ghost: true,
            onClick: "bind:diyClick2",
            isValidate: false,
            hide: "bind:mobileHide"
        },
        {
            field: "diyClick3",
            label: '调用刷新方法',
            type: 'primary', //primary dashed danger 
            ghost: true,
            onClick: "bind:diyClick3",
            isValidate: false,
            hide: "bind:mobileHide"
        },

        {
            field: "diyClick4",
            label: '使用props更改配置',
            type: 'primary', //primary dashed danger 
            ghost: true,
            onClick: "bind:diyClick4",
            isValidate: false,
            hide: "bind:mobileHide"
        },

        {
            field: "setValues",
            label: '设置值',
            type: 'primary', //primary dashed danger 
            ghost: true,
            onClick: "bind:setValues",
            isValidate: false,
        },
    ]
};
