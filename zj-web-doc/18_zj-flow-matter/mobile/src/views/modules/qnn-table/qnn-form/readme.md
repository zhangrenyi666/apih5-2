### qnn-form 帮助文档

-   1、引用

              import QnnForm from 'XXX/qnn-form'

*   2、使用

            <QnnForm
                {...this.props}
                //内部自带发送ajax的组件发送请求时会自动加到head头里面的数据 （上传会用到）
                headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                //具体配置
                {...config}
                //自定义组件key定义
                componentsKey={{
                    myDiyComponent: DIY_COMPONENT
                }}
            />

        ps: this.props 中必须包含react-router的信息{ match, history } 和 fetch（返回一个pormise）方法

        config配置
        {
            //布局
            formItemLayout={
                labelCol: {
                    xs: { span: 24 },
                    sm: { span: 4 }
                },
                wrapperCol: {
                    xs: { span: 24 },
                    sm: { span: 20 }
                }
            }
             //布局配置
            formContainerLayoutLeftAndRright: {
                left: {
                    span: 21,
                    name: "qnnForm"
                },
                right: {
                    span: 3,
                    name: "diy",
                    content: () => {
                        return <div>123456</div> 
                    }
                }
            },
            formContainerOnScroll:(e)=>void, //form表单滚动条滚动监听
            styleType:"0", //0 | 1 样式类型  1类型暂时停止维护部分样式可能会出问题
            请求配置  后会自动去请求并且赋值到表单可使用rcform设置数据
            fetchConfig:{
                apiName:'123',//可为function 返回必须为string
                params:{//从表单中取字段  此时可以取默认值或者网址上的值
                    id:'id',
                },
                otherParams:{//定死的数据  可为function 返回必须为对象
                    test:'123456'
                }
            },

            //动态渲染方式
            formConfig:{
                //如果配置为fetchConfig的话会自动请求后台数据渲染表单项
                fetchConfig:{
                    apiName:"apiName",
                    otherParams:{},
                    params:{}
                },
                //返回的数据格式化
                // resFormat:function(){}
            },
            //当前激活的tabs项
            tabsActiveKey:"0",
            tabs:[],
            表单字段
            formConfig:[
                {
                    // 通用属性
                    field: 'id', //唯一的字段名 [string]  ***必传
                    type: 'string', //类型 [string]  ***必传
                    label: 'id', //label [string]
                    hide: true, //是否隐藏 [boolean] 默认false  可为function
                    disabled: false,//是否禁用 [boolean] 默认 false
                    placeholder: '请输入',// [string]
                    required: true,//是否必填 [boolean] 默认false
                    isUrlParams: true,//是否是从地址参数中取值 [boolean] 默认false
                    help: '必须输入全称。',//帮助信息 [string] 默认null
                    initialValue:'', //初始值  可为function
                    defaultValue:'', //默认值
                    span:24,//默认独占一行 栅格化  [number]
                    offse:0', //格子偏移量  [number]
                    voice:true, //开启微信语音输入  请确保jsSdk已经正确配置
                    //表单项布局
                    formItemLayout:{
                        labelCol: {
                            xs: { span: 24 },
                            sm: { span: 6 }
                        },
                        wrapperCol: {
                            xs: { span: 24 },
                            sm: { span: 18 }
                        }
                    },
                    editDisabled:true,//编辑处于禁用状态
                    style:{//自定义input样式
                        color:'red'
                    },
                    最外层col样式
                    colStyle:{
                        margin:'100px'
                    },
                    onChange: (v) => {//值切换时的回调
                        console.log(v)
                    },
                    //显示格式化
                    formatter:function(value, prev, all){
                        return '123'
                    }
                    message:'必填',  //当用户未填写字段时提醒的文字 [string]
                    typeMessage:'只能填写整数',  //类型填写错误时的提示文字 [string]
                    //前后填充暂时只支持普通文本填充
                    addonBefore:"Http://",//前填充 [string]
                    addonAfter:".com", //后填充  [string]
                    //前后缀图标暂时只支持icon名填充具体看 https://ant.design/components/icon-cn/
                    prefix:'user',//前缀图标   [string]
                    prefixStyle:{color:'rgba(0,0,0,.25)'},//前缀图标样式
                    suffix:'link',//后缀图标   [string]
                    suffixStyle:{color:'rgba(0,0,0,.25)'},//后缀图标样式

                    fetchConfig: {
                        apiName: 'getSysDepartmentList',
                        otherParams: {}
                    },

                    //特殊属性
                    //默认选项数据 下拉类型特有
                    optionData: [//可为function (props)=>array
                        {
                            name: '01name',
                            id: '01id',
                            orgId: '01orgId'
                        },
                        {
                            name: '02',
                            id: '02id',
                            orgId: '02orgId'
                        }
                    ],
                    optionConfig: {//下拉选项配置 下拉类型特有
                        label: 'name', //默认 label
                        value: ['id', 'orgId'],//最终的值使用逗号连接 默认值使用valueName 默认['value']
                    },

                    //textarea特有
                    autosize:{ minRows: 2, maxRows: 12 },  //自适应高度  [object] 默认{ minRows: 2, maxRows: 12 }
                    rows:4, //autosize存在即无意义  [number]  默认 4
                    oldValue:[ //该数据存在会自动在textarea框上新增列表数据
                        {
                            text:"第一条历史数据",
                            // text:"<a href='#'>第一条历史数据</a>",
                            time:'2.19-08-49 12:44:55',
                            name:"王思杨"
                        },
                        {
                            text:"第二条历史数据",
                            time:'2.19-8-9 12:44:55',
                            name:"姜海军"
                        }
                    ],
                    oldValueKey:{//默认数据
                        text:"text",
                        time:"time",
                        name:"name"
                    },
                },
                //隐藏字段 并且是 从浏览器网址取的值
                {
                    type: 'string',
                    label: 'id',
                    field: 'id', //唯一的字段名 ***必传
                    hide: true, //是否隐藏 默认 false
                    disabled: false,//是否禁用 默认 false
                    placeholder: '请输入',
                    required: true,
                    isUrlParams: true,//是否是从地址参数中取值 默认false
                },
                //文本类型
                {
                    type: 'string',
                    label: 'string',
                    field: 'name', //唯一的字段名 ***必传
                    placeholder: '请输入',//占位符
                    required: true,//是否必填
                    help: '必须输入全称。',//帮助信息
                    onChange: (v) => {
                        console.log(v)
                    }
                },
                //富文本
                {
                    type: "richText",
                    label: "富文本",
                    field: "richText", //唯一的字段名 ***必传
                    fetchConfig:{
                        //必须配置  上传图片地址
                        uploadUrl:window.configs.domain + 'upload' //***必传
                    },
                    //参考链接 http://fex.baidu.com/ueditor/#start-config
                    //ueditorConfig:{
                    //    toolbars: [
                    //        ['fullscreen', 'source', 'undo', 'redo', 'bold']
                    //    ],
                    //},

                },
                //密码
                {
                    type: 'password',
                    label: 'password',
                    field: 'password', //唯一的字段名 ***必传
                    placeholder: '请输入',//占位符
                    required: true,//是否必填
                    prefix: 'lock',//前缀图标   [string]
                    prefixStyle: { color: 'rgba(0,0,0,.25)' },//前缀图标样式
                },
                //数字类型
                {
                    type: 'number',
                    label: 'number',
                    field: 'number', //唯一的字段名 ***必传
                    placeholder: '请输入',
                    required: true,
                    max: 99, //最大值
                    min: 20, //最大值
                    precision: 2, //数值精度
                    // formatter:function(value){return value + '$'}, //格式化显示
                },
                {//整数
                    type: 'integer',
                    label: 'integer',
                    field: 'integer', //唯一的字段名 ***必传
                    placeholder: '请输入',
                    required: true,
                },
                {//邮箱
                    type: 'email',
                    label: 'email',
                    field: 'email', //唯一的字段名 ***必传
                    placeholder: '请输入',
                    required: true,
                },
                //url
                {
                    type: 'url',
                    label: 'url',
                    field: 'url', //唯一的字段名 ***必传
                    placeholder: '请输入',
                    required: true,
                },
                //date yyyy-mm-dd
                {
                    type: 'date',
                    label: 'date',
                    field: 'date', //唯一的字段名 ***必传
                    placeholder: '请选择',
                    required: true,
                        format:"YYYY-MM-DD",
                        showTime:false, //不显示时间
                },
                //time yyyy-mm-dd
                {
                    type: 'time',
                    label: 'time',
                    field: 'time', //唯一的字段名 ***必传
                    placeholder: '请选择',
                    required: true,
                    is24: true,//是否是24小时制 默认true2
                },
                {//dateTime yyyy-mm-dd hh:mm:ss
                    type: 'datetime',
                    label: 'datetime',
                    field: 'datetime', //唯一的字段名 ***必传
                    placeholder: '请选择',
                    required: true,
                    is24: true,//是否是24小时制 默认true
                },
                //年月
                {
                    type: "month",
                    label: "年月选择",
                    field: "month", //唯一的字段名 ***必传
                    placeholder: "请选择"
                },
                {
                    type: 'Component',
                    // label: '自定义组件',
                    field: 'diy', //唯一的字段名 ***必传
                    placeholder: '请输入',//占位符

                    //第一种，推荐定义方式 需要将componentsKey对象传到qnn-form
                    Component:"myDiyComponent",

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
                {//普通选择框
                    type: 'select',
                    label: 'select',
                    field: 'select', //唯一的字段名 ***必传
                    placeholder: '请选择',
                    required: true,
                    multiple: false, //是否开启多选功能 开启后自动开启搜索功能
                    showSearch: false, //是否开启搜索功能 (移动端不建议开启)
                    // optionData: [//默认选项数据//可为function (props)=>array
                    //     {
                    //         name: '01name',
                    //         id: '01id',
                    //         orgId: '01orgId'
                    //     },
                    //     {
                    //         name: '02',
                    //         id: '02id',
                    //         orgId: '02orgId'
                    //     },
                    // ],
                    optionConfig: {//下拉选项配置
                        label: 'name', //默认 label
                        value: ['id', 'orgId'],// [array | string]  最终的值使用逗号连接 默认值使用valueName 默认['value']
                        //与其他字段双向绑定配置
                        linkageFields:{
                            linkageOne:"name",
                            linkageTwo:"orgId",
                        }
                    },
                    fetchConfig: {//配置后将会去请求下拉选项数据
                        apiName: 'getSysDepartmentList',
                        otherParams: {}, 
                    }
                },
                {//层叠联动
                    type: 'cascader',
                    label: 'cascader',
                    field: 'cascader', //唯一的字段名 ***必传
                    placeholder: '请选择',
                    required: true,
                    showSearch: false, //是否开启搜索功能 (移动端不建议开启)
                    optionData: [//默认选项数据//可为function (props)=>array
                        {
                            name: '01name',
                            id: '01id',
                            children: [{
                                name: '01name',
                                id: '01id',
                            }]
                        },
                        {
                            name: '02',
                            id: '02id',
                        },
                    ],
                    optionConfig: {//下拉选项配置
                        label: 'name', //默认 label
                        value: 'id',//
                        children: 'children'
                    },
                    // fetchConfig: {//配置后将会去请求下拉选项数据
                    //     apiName: 'getSysDepartmentList',
                    //     otherParams: {}
                    // }
                },
                //树选择
                {
                    isInTable:false,
                    form: {
                        label:'树选择',
                        field:'treeSelect',
                        type: 'treeSelect',
                        initialValue:[],
                        treeSelectOption:{
                            help:true,
                            fetchConfig: {
                                apiName: 'getSysDepartmentUserAllTree',
                            },
                            search:true,
                            searchPlaceholder:'姓名、账号、电话',
                            // searchApi:'getSysDepartmentUserAllTree',  //搜索时调用的api  [string]
                            searchParamsKey:'search',//搜索文字的K 默认是'searchText'   [string]
                            searchOtherParams:{pageSize:999}//搜索时的其他参数  [object]
                        }
                    }
                },

                //树节点  具体配置查看tree插件配置
                {
                    type: "treeNode",
                    label: "树节点",
                    field: "treeNode", //唯一的字段名 ***必传
                    disabled: true,
                    treeNodeOption: {
                        fetchConfig: {
                            parmasKey: "parentId", //点击节点后将节点id赋值该key上传递给后台
                            apiName: "appGetGxProjectLevel"
                        },
                        keys: {
                            label: "levelName",
                            value: "levelId",
                            children: "zlProjectLevelList"
                        },
                        nodeRender: nodeData => {
                            return (
                                <span>
                                    {nodeData["levelName"]}（总
                                    {nodeData["totalNum"]}道工序，已完成
                                    {nodeData["finishedNum"]}道）
                                </span>
                            );
                        },
                        valueRender: obj => {
                            let parentNameAll = obj.parentNameAll;
                            let _fLabel = parentNameAll.replace(/,/gi, "→");
                            return _fLabel;
                        },
                        rightMenuOption: []
                    }
                },
                //条件显隐例子
                {
                    type: 'string',
                    label: '条件显隐',
                    field: 'condition', //唯一的字段名 ***必传
                    placeholder: '请输入',//占位符
                    required: true,//是否必填
                    // hide:true,
                    //array类型
                    //条件存在权重 下面条件满足上面条件将会失效
                    //匹配规则为&&匹配
                    //内置三种action【disabled, show, hide】
                    condition: [
                        {//条件
                            regex: {//匹配规则 正则或者字符串
                                id: '0',
                                name: 'aaa'
                            },
                            action: 'hide', //disabled,  show,  hide, function(){}
                        }
                    ]
                },
                //formatter属性例子，显示格式化
                {
                        type: 'string',
                        label: 'formatter案例',
                        field: 'formatter', //唯一的字段名 ***必传
                        placeholder: '请输入',//占位符
                        required: true,//是否必填
                        initialValue: '1',
                        //使用formatter需要注意的是 格式化的时候需要判断格式化过的就别格式化了
                        formatter: function (value, prev, all) {
                            if (value) {
                                return (value).toString().indexOf("$") !== -1 ? value : value + "$";
                            }
                        }
                },
                //textarea类型
                {
                    type: 'textarea',
                    label: 'textarea',
                    field: 'textarea', //唯一的字段名 ***必传
                    placeholder: '请输入',//占位符
                    required: true,//是否必填
                    rows: 4, //行高 默认4
                },
                //files文件上传(只建议pc端使用否则请使用camera类型)
                {
                    type: 'files',
                    label: 'files',
                    field: 'files', //唯一的字段名 ***必传
                    required: true,//是否必填
                    desc: '点击或者拖动上传', //默认 点击或者拖动上传
                    subdesc: '只支持单个上传',//默认空
                    fetchConfig: {
                        apiName: window.configs.domain + 'upload',
                        // name:'123', //上传文件的name 默认空
                    },
                    accept: 'image/jpeg', //支持上传的类型 默认都支持  格式"image/gif, image/jpeg"
                    max: 2, //最大上传数量
                },
                //图片上传
                {
                    type: 'images',
                    label: 'images',
                    field: 'images', //唯一的字段名 ***必传
                    required: true,//是否必填
                    desc: '', //默认  上传 
                    icon:"", //reactDom
                    wrapperStyle:{}, //容器样式
                    fetchConfig: {
                        apiName: window.configs.domain + 'upload',
                        // name:'123', //上传文件的name 默认空
                    },
                    accept: 'image/jpeg', //支持上传的类型 默认都支持  格式"image/gif, image/jpeg"
                    max: 2, //最大上传数量
                },
                //移动端文件上传
                //会自动根据类型和运行环境自动选择图片文件预览方式（cameraConfig.type===[images|camera]）
                {
                    type: 'camera',
                    label: 'camera',
                    field: 'camera', //唯一的字段名 ***必传
                    required: true,//是否必填
                    fetchConfig: {
                        apiName: window.configs.domain + 'upload',
                        // name:'123', //上传文件的name 默认空
                    },
                    fieldName:"camera", //一定要配置唯一标识
                    cameraConfig:{
                        showName:true, //显示文件名字  默认false
                        //三种可选类型  其中camera 直接打开相， files和images是打开文件夹。预览时camera和images是判断平台，如微信使用微信预览图片，files类型直接打开地址
                        type:"files",  (images | files | camera)
                        //accept: 'image/jpeg', //支持上传的类型 默认都支持  格式"image/gif, image/jpeg"   选填
                    },
                    max: 2, //最大上传数量
                },

                //item类型   使用场景：上一个字段是问题，然后可用这个类型设置问题答案选项
                {
                    field:'option',
                    type: "item",
                    placeholder: "请输入...",
                    required: true
                },
                {
                    type: "richText",
                    label: "富文本描述",
                    field: "richText", //唯一的字段名 ***必传
                    // disabled:true,
                    fetchConfig: {
                        uploadUrl: window.configs.domain + "upload"
                    }
                },
                {
                    type: "radio",
                    label: "单选",
                    field: "radio", //唯一的字段名 ***必传
                    optionData: [  //可为function (props)=>array
                        {
                            label: "测试一",
                            value: "001"
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
                    field: "checkbox", //唯一的字段名 ***必传
                    optionData: [//可为function (props)=>array
                        {
                            label: "测试一",
                            value: "001"
                        },
                        {
                            label: "测试二",
                            value: "002"
                        }
                    ]
                },
                {
                    type: "switch",
                    label: "开关",
                    field: "switch",
                },
                {
                    type: "rate",
                    label: "打分",
                    field: "rate",
                },
                {
                    type: "slider",
                    label: "滑块",
                    field: "slider",
                    marks:{
                        0: 'A', 20: 'B', 40: 'C', 60: 'D', 80: 'E', 100: 'F',
                    }
                },
                {
                    type: "qnnTable",
                    label: "qnnTable",
                    field: "slider",
                    qnnTableConfig:{
                        //同步qnn-table插件配置
                    }
                },

                //qnnForm控件（表单块）
                {
                    type: "qnnForm",
                    field: "qnnFormField", 
                    label: "报销细明",
                    //文字配置 默认数据如下 [object]
                    textObj: {
                        add: "添加报销细明",
                        del: "删除"
                    },
                    //是否能新增form表单(true可动态增删) 默认false [bool]
                    //注意：开启后表单项值的类型为array  关闭为object
                    canAddForm: true,
                    //canAddForm===true 的初期値设置格式（提交数据格式&后台返回字段格式 同）
                    initialValue:[
                        {
                            typeOfExpense:"01",
                            costAmount:"100",
                        }
                    ],
                    //canAddForm===false 的初期値设置格式（提交数据格式&后台返回字段格式 同）
                    initialValue: {
                        typeOfExpense: "01",
                        costAmount: "100",
                        desc: "1113"
                    },
                    //qnn-form配置
                    qnnFormConfig: {
                        formConfig: [
                            {
                                type: "select",
                                label: "费用类型",
                                field: "typeOfExpense", //唯一的字段名 ***必传
                                placeholder: "请选择",
                                required: true,
                                optionData: [ 
                                    {
                                        label: "交通",
                                        value: "01"
                                    } 
                                ]
                            },
                            {
                                type: "textarea",
                                label: "费用说明",
                                field: "desc", //唯一的字段名 ***必传
                                placeholder: "请输入"
                            } 
                        ]
                    }
                },

                {//无限联动
                    isInTable: false,//必须为false  默认false
                    /*
                        无需配置field因为无意义
                        联动表单从第一层children开始 第一层不出现在form中
                        children配置和form里的字段配置一样
                        可联动大部分类型(部分类型暂不支持，例如图片)

                        数据格式（ model:'0'）
                            [
                                {
                                    apih5FlowId:'0',
                                    apih5FlowName:'名称',
                                    children:[
                                        {
                                            apih5FlowId:'0',
                                            apih5FlowName:'名称',
                                        },
                                        ...
                                    ]
                                },
                                ...
                            ]
                    */
                    form: {
                        type: 'linkage',
                        model:'0', // string  默认0  0所有数据都给到前端  1触焦时去请求数据
                        fetchConfig: {//只有模式为0才写到这
                            apiName: 'getFlowNameSelectList',
                        },
                        optionConfig: { // 暂时只有模式为0有意义 所有子集默认optionConfig
                            value: 'apih5FlowId',
                            label: 'apih5FlowName',
                            children:'children'
                        },
                        children: {//所有配置见qnn-form
                            isInTable: false,
                            form: {
                                editDisabled: true,
                                label: '一级',
                                field: 'linkage1',
                                type: 'select',
                                placeholder: '请选择',
                                // fetchConfig: {//model为1时有用
                                //     apiName: 'getFlowNameSelectList',
                                //     parentKey:'XXX'
                                // },
                            },
                            children: {
                                form: {
                                    label: '二级',
                                    placeholder: '请选择',
                                    field: 'linkage2',
                                    type: 'select',
                                    optionData:[//可为function (props)=>array
                                        {
                                            apih5FlowId:'111',
                                            apih5FlowName:'111'
                                        }
                                    ]
                                },
                                children: {
                                    form: {
                                        label: '三级',
                                        placeholder: '请输入',
                                        field: 'linkage3',
                                        type: 'string',
                                    }
                                }
                            }
                        }
                    }
                },
                },
            ],
            btns: [  ---可为func
                {
                    label: '获取值',
                    type: 'primary',
                    isValidate: false,//是否验证表单 默认true
                    onClick: function (obj) {
                        console.log(obj)
                    },
                    //同表单条件一样
                    condition: [
                        {//条件
                            regex: {//匹配规则 正则或者字符串
                                id: '01',
                                // name: 'aaa'
                            },
                            action: 'hide', //disabled,  show,  hide, function(){}
                        }
                    ]
                },
                {
                    label: '提交',
                    type: 'primary', //primary dashed danger
                    fetchConfig: {
                        //api 默认提交整个表单的数据
                        apiName: 'submit',
                        //此参数存在将不会提交全部表单参数而是选取params里的参数提交
                        //提交时需要字段改名获取后台只需要几个字段时会用到
                        // params:{k:field},
                        // delParams:[field,...], //删除不需要提交的参数
                        //定死参数
                        // otherParams:{test:'111'}
                    },
                    onClick: function (obj) { //此时里面会多一个response
                        console.log(obj)
                    },

                    //当事件确定要发生时需要提醒用户时用到

                    affirmDesc: '提交后将无法撤回',//有这文字会点击按钮验证通过时将自动弹出提示
                    affirmYes: '确定',// 确认窗的确定按钮文字 默认确定
                    affirmNo: '取消',//确认窗的取消按钮文字  默认取消

                    //其他属性
                    isValidate: true,//点击后是否验证表单 默认true
                    disabled: false, //是否禁用
                    loading: false, //是否加载状态
                    icon: 'save',//icon
                    // block:true,//将按钮宽度调整为其父宽度
                    // href:'http://baidu.com', //点击事件失效 改为跳转
                    // target:true, //相当于 a 链接的 target 属性，href 存在时生效

                }
            ]
        }

-   使用实例

          <QnnForm
              form={this.props.form} //form对象一定要在这传入
              fetch={this.props.myFetch}
              //内部自带发送ajax的组件发送请求时会自动加到head头里面的数据
              headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
              {...this.state.config}
          />

          //设置已有的值（非请求方式设置值）
          componentDidMount() {
              let data = {
                  name: '测试王',
                  date: 1234569877894,
              };
              //得使用sFormatData静态方法格式化
              let _d = QnnForm.sFormatData(data, this.state.config.formConfig);
              this.props.form.setFieldsValue(_d)
          }
