##### 使用前必看

    antd 栅格系统 https://ant.design/components/grid-cn/

##### 使用

    <QnnTable
        {...this.props}
        fetch={this.props.myFetch}
        headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
        {...window.tableConfig.testPage}
    />

##### 所有 function/自定义组件 props 中参数说明（ps:自定义组件、自定义按钮点击事件、apiName 为 function 等...）

    eg: fetchConfig:{
            apiName:function(obj){
                //obj携带的信息如下
                return 'xxx'
            }
        }

    obj
    {

        _formData   未格式化为qnn-form使用的数据 （抽屉打开状态才会有数据）
        formData    qnn-form中的表单数据 （抽屉打开状态才会有数据）
        rowData     点击行的数据
        rowInfo     被点击的按钮的信息
        selectedRows    选中的行数据
        searchData  搜索form表单的数据
        props       qnn-table组件props
        isInQnnTable 是否是嵌入到qnn-table中的
        data        点击的单元格数据
        formConfig       qnn-table的formConfig配置
        ...其他自定义传入给qnn-table的props也会返回出来

        btnCallbackFn 对象
            {

                方法
                closeDrawer  打开/关闭右边抽屉
                    参数： (status:boolean, callback(object:object):void)
                    说明: status    为true则打开抽屉  false则关闭
                        callback    打开/关闭后的回调

                download    下载返回的文件流方法
                    参数： (url:string, data?:object, header?:objct, successCb?:void, errorCb?:void)
                    说明:   url     地址
                            data    参数
                            header  头信息
                            successCb  成功回调
                            errorCb     失败回调

                fetch           ajax方法
                    参数： (apiName:string, data?:object, complete?:void)
                    说明:   apiName  地址
                            data    参数
                            complete(object:object)=>void  请求完成后的回调

                msg         信息弹出
                    详情参见antd message方法
                    eg:msg.success('xxx')、msg.error('xxx')

                openTree    打开/关闭树结构
                    说明
                            打开
                            openTree({
                                fetchConfig: {//获取树结构的配置
                                    apiName: 'getSysDepartmentUserAllTree',
                                },
                                //取消按钮回调
                                onCancel: function () {
                                    openTree({
                                        openTree: false
                                    })
                                },
                                //保存按钮回调
                                onSave:function(val){}
                            })

                            关闭
                                openTree({ openTree: false  })

                refresh         刷新qnn-table的方法
                    参数：无
                    说明    refresh=>void

                //属性
                props       qnn-table组件props
            }
    }

##### 详细配置项

    window.tableConfig.testPage = {
        fetchConfig: {//表格的ajax配置  可为function 返回值为object
            apiName: 'getSxDocumentInfoList',//可为function 返回必须为string
            otherParams: {}, //需要固定写死的参数  可为function 返回必须为object
        },

        desc:'这是一段描述...',

        // data: [], //表格数据 默认为空 当fetchConfig存在时无意义
        antd: { //同步antd table组件配置 ***必传
            rowKey: function (row) {// ***必传
                return row.documentId
            },
        },
        搜索form区域一行有多少个表单项  默认不设置
        当对字段单独设置spanSearch属性后改配置将对该字段失效
        searchFormColNum:3,
        //表格分页配置
        limit: 10,   //每页显示条数 默认10
        curPage: 1, //当前页  默认1
        paginationConfig: {// 同步antd的分页组件配置
            position: 'bottom'
        },

        drawerConfig: { //抽屉的配置 同步antd的Drawer组件配置
            width:800,
            maskClosable:false, //点击蒙层是否关闭抽屉  默认true
        },
        //文字配置 可为false false就是不显示该区域
        labelConfig: {
            actionBtn: '操作',//行右边下拉菜单标题
            detail: '详情',//抽屉详情左上角文字
            add: '新增',//抽屉新增左上角文字
            edit: '编辑' //抽屉新增左上角文字
        },

        isShowRowSelect: true,//是否显示选择框  默认true

        //全局提示框 给false将 返回string | reactDom
        infoAlert: function (selectedRows) {
            return '已选择 ' + selectedRows.length + '项';
        },

        //移动端 列表项  内置flow
        Item:string | fun | ReactDom,

        //每个表单项的布局 -- 表单
        formItemLayout: {
            //默认数据
           labelCol: {
                xs: { span: 24 },
                sm: { span: 4 }
            },
            wrapperCol: {
                xs: { span: 24 },
                sm: { span: 20 }
            }
        },
        //每个表单项的布局 -- 搜索区域
        formItemLayoutSearch: {
            //默认数据
            labelCol: {
                xs: { span: 24 },
                sm: { span: 6 }
            },
            wrapperCol: {
                xs: { span: 24 },
                sm: { span: 18 }
            }
        },

        //所有操作按钮
        //内置 新增add 删除del
        //可为function 返回值为array
        actionBtns: [
            {
                name: 'add',//【add,  del, edit, detail, Component, form】
                icon: 'plus',//icon
                type: 'primary',//类型  默认 primary
                label: '新增',
                drawerTitle:"新增", //点击后的抽屉标题
                //表单里面的按钮  name内置 【submit， cancel】
                formBtns: [
                    {
                        name: 'cancel', //关闭右边抽屉
                        type: 'dashed',//类型  默认 primary
                        label: '取消',
                    },
                    {
                        name: 'submit',//内置add del
                        type: 'primary',//类型  默认 primary
                        label: '提交',//提交数据并且关闭右边抽屉
                        fetchConfig: {//ajax配置
                            apiName: 'submit',
                        }
                    }
                ]
            },
            {
                name: 'del',
                icon: 'delete',//icon
                type: 'danger',//类型  默认 primary  [primary dashed danger]
                label: '删除',
                fetchConfig: {//ajax配置
                    apiName: 'del',
                }
            },
            {
                使用自定义组件时配置js文件应该被打包不能放在public文件夹下
                自定义组件中props里有该条数据的所有数据
                name: 'Component',
                直接打开组件
                Component:MyComponent,
                可为object  ps：{字段1：{值：使用的组件，值：使用的组件}}
                Component: {
                    flowId: {
                        zjPartyFeeUse: FlowFormByDF,
                        zjYongYin: FlowFormByYY
                    },
                    条件满足会覆盖上面的flowId条件
                    sendUserName: {
                        "王宗明测试": FlowFormByYY,
                    }
                }


                render: function () {
                    return '<a>详情</a>'
                },
                onClick:function(obj){//内置的按钮一般不需要传
                    console.log(obj)
                }
            },
            {
                name: 'diy',
                icon: 'more',//icon
                type: 'dashed',//类型  默认 primary  [primary dashed danger]
                label: '其他操作',
                //内置的按钮一般不需要传 传了就相当于一个回调
                onClick: function (obj) {
                    console.log(obj)
                }
            },
            {
                name: 'diy',//diy一个弹出选人的组价
                icon: 'person',//icon
                type: 'primary',//类型  默认 primary  [primary dashed danger]
                label: '人员添加',
                onClick: function (obj) {
                    var openTree = obj.btnCallbackFn.openTree;
                    var fetch = obj.btnCallbackFn.fetch;
                    var msg = obj.btnCallbackFn.msg;
                    var match = obj.btnCallbackFn.match || {}; //路由信息
                    var urlParams = match.params || {};
                    openTree({
                        fetchConfig: {//获取树结构的配置
                            apiName: 'getSysDepartmentUserAllTree',
                        },
                        onCancel: function () {
                            openTree({
                                openTree: false
                            })
                        },
                        onSave: function (val) {
                            var _params = {
                                educationId:urlParams.educationWorkerId,
                                educationName:urlParams.workerName,
                                workerId:val
                            }
                            fetch('addZxQrcodeEducationWorker', _params, function (data) {
                                if (data.success) {
                                    openTree({
                                        openTree: false
                                    })
                                    msg.success(data.message)
                                }else{
                                    msg.error(data.message)
                                }
                            })
                        },
                    })
                }
            },
            {
                name: "form", //内置add del form
                icon: "upload", //icon
                type: "primary", //类型  默认 primary  [primary dashed danger]
                label: "导入",
                // formTitle:'上传',//弹出框的标题
                modalOption: {
                    //同步antd的modal配置
                    closable: false
                },

                qnnFormOption: {
                    formItemLayout: {
                    //默认数据
                    labelCol: {
                        xs: { span: 0 },
                        sm: { span: 0 }
                    },
                    wrapperCol: {
                        xs: { span: 24 },
                        sm: { span: 24 }
                    }
                    },
                    //见qnn-form配置
                    formConfig: [
                    {
                        //files文件上传
                        type: "files",
                        field: "files", //唯一的字段名 ***必传
                        desc: "点击或者拖动上传", //默认 点击或者拖动上传
                        subdesc: "只支持单个上传", //默认空
                        help: "只能导入.xls格式的",
                        fetchConfig: {
                        //配置后将会去请求下拉选项数据
                        apiName: window.globalConfig.apiUrl + "upload"
                        // name:'123', //上传文件的name 默认空
                        },
                        span: "24", //默认独占一行 栅格化
                        style: {
                        //自定义input样式
                        width: "100%"
                        },
                        accept: "image/jpeg", //支持上传的类型 默认都支持  格式"image/gif, image/jpeg"
                        max: 1 //最大上传数量
                    }
                    ],
                    btns: [
                    // {
                    //   name: "close", //关闭右边抽屉
                    //   type: "primary", //类型  默认 primary
                    //   label: "关闭"
                    // }
                    ]
            }
        ],

        //所有字段配置
        //可为function 返回值为array
        formConfig: [// ***必传
            {
                //该字段是否是搜索条件 默认false  可为function 返回必须为bool
                isInSearch: true,
                //该字段是否在表格中存在 默认true 可为function 返回必须为bool
                isInTable: true,
                //该字段是否在表单中存在 默认true 可为function 返回必须为bool
                isInForm: true,

                //表格配置 同步antd table组件配置
                table: {
                    title: '姓名', //表头标题
                    dataIndex: 'createUser', //表格里面的字段
                    key: 'createUser',//表格的唯一key
                    fixed: 'left',//固定到左边
                    tooltip:50,内容长度超过50字时使用...隐藏并且使用小气泡形势弹出全部内容
                    width: 120, //列数比较多时必须设置
                    type:'images', //目前内置images 类型
                    align:'left', //对齐方式 'left' | 'right' | 'center'  默认left
                    defaultSortOrder: 'descend', //默认排序  'ascend' | 'descend'
                    sorter: (a, b) => a.createUser - b.createUser, //排序规则
                    filter:true, //是否开启该列的过滤条件  目前支持的类型 strign number select datetime
                    imgStyle:{}, //只有类型为images时有用  设置图片样式
                    render: function (text) {  //可返回 string | reactDom
                        return text;
                    },
                    /*
                        单元格点击
                        为fn时点击后的方法回调参数{btnCallbackFn:this.btnCallbackFn, data, rows, formConfig}
                        为name时可传 add,  del, edit, detail, Component, form(用户将改条数据推向后台某个接口)
                        ps:不能和render同时存在
                    */
                    // onClick: function (obj) {
                    //     console.log(obj)
                    // },
                    onClick: 'edit',   //【add,  del, edit, detail, Component, form】
                    fetchConfig:{
                        apiName:'editApi'
                    }，
                    drawerTitle:"新增", //如果点击后需要打开右边抽屉设置抽屉的标题 可为function  ()=>string
                },

                //表单配置 不配置将默认使用表格的配置的有【label, field, render】
                form: {
                    label: '姓名',
                    field: 'createUser',
                    type: 'string',
                    placeholder: '请输入',

                    addDisabled:true,//新增禁用
                    editDisabled:true, //修改禁用
                    disabled:true,新增修改都禁用
                    //决定表单是否能平铺
                    spanForm: '12', //表单中行格数 一行24格 默认24   表单
                    spanSearch: '8', //表单中行格数 一行24格 默认8   搜索
                    offsetForm: '0', //表单中行的偏移量 共24 默认0   表单
                    offsetSearch: '0', //表单中行的偏移量 共24 默认0    搜索
                    //增删改表单项布局
                    formItemLayoutForm:{
                        labelCol: {
                            xs: { span: 24 },
                            sm: { span: 6 }
                        },
                        wrapperCol: {
                            xs: { span: 24 },
                            sm: { span: 18 }
                        }
                    },
                    //搜索表单项布局
                    formItemLayoutSearch:{
                        labelCol: {
                            xs: { span: 24 },
                            sm: { span: 6 }
                        },
                        wrapperCol: {
                            xs: { span: 24 },
                            sm: { span: 18 }
                        }
                    },
                    required: true,

                    pullJoin:false,//拉取来的select不使用逗号拆分（多选才能用到）
                    pushJoin:false,//给后台的select数据不使用逗号连接（多选才能用到）

                    hide:false,//设置hide后  addShow detailShow editShow将无意义
                    //设置某个环境下XXXshow是false时需要将其他两个show改为true，否则其他两个环境下也不出来
                    addShow:true,//新增时在表单中隐藏  [booblen]  默认 true
                    detailShow:true,//详情时在表单中隐藏  [booblen] 默认 true
                    editShow:true,//编辑时在表单中隐藏  [booblen] 默认 true
                    tooltip:10, //超过多少字后显示省略号隐藏   [number]
                },
            },
            {
                isInSearch: true,
                table: {
                    title: '标题', //表头标题
                    dataIndex: 'documentTitle', //表格里面的字段
                    key: 'documentTitle',//表格的唯一key
                    width: 150,
                    tooltip: '5',//此属性和render属性不可并存 开启文字提示，当文字内容多时需要开启
                },
                form: {
                    type: 'string',
                    placeholder: '请输入',
                },
            },
            {
                table: {
                    title: '时间', //表头标题
                    dataIndex: 'modifyTime', //表格里面的字段
                    key: 'modifyTime',//表格的唯一key ,
                    width: 151,
                    render: function (time) {
                        return new Date(time).toLocaleDateString()
                    }
                },
                form: {
                    type: 'date',
                }
            },
            {
                isInTable: false,
                form: {
                    label: '图片',
                    field: 'images',
                    type: 'images',
                }
            },
            {
                isInTable: false,
                form: {
                    label: '文件',
                    field: 'files',
                    type: 'files',
                }
            },
            {
                isInTable: false,
                form: {
                    label: '备注',
                    field: 'handlingRequirements',
                    type: 'textarea',
                    placeholder: '请输入',
                }
            },
            {
                isInForm: false,
                table: {
                    showType: 'bubble', //出来的样式 bubble（气泡）  tile（平铺） 默认bubble  （0.6.15版本中将该属性移动到table属性下，也可写到table同级）
                    width: 80,
                    // title: "操作",
                    key: "action",//操作列名称
                    fixed: 'right',//固定到右边
                    btns: [
                        {
                            label:"", //和render不可同时存在
                            name: 'edit', // 内置name有【add,  del, edit, detail, Component, form】
                            render: function (rowData) {
                                return '<a>编辑</a>';
                            },
                            //表单里面的按钮  name内置 【submit， cancel】
                            formBtns: [
                                {
                                    name: 'cancel', //关闭右边抽屉
                                    type: 'dashed',//类型  默认 primary
                                    label: '取消',
                                },
                                {
                                    name: 'submit',//内置add del
                                    type: 'primary',//类型  默认 primary
                                    label: '保存',//提交数据并且关闭右边抽屉
                                    fetchConfig: {//ajax配置
                                        apiName: 'save',
                                    }
                                }
                            ]
                        },
                        {
                            name: 'detail',
                            render: function (rowData) {
                                return '<a>详情</a>';
                            },
                            // fetchConfig:{//ajax配置
                            //     apiName:'123456',
                            //     params:{
                            //         test:'documnetId'
                            //     }
                            // },
                            // onClick:function(rowData){//内置的按钮一般不需要传
                            //     console.log(rowData)
                            // }
                        },
                        {
                            //使用自定义组件时配置js文件应该被打包不能放在public文件夹下
                            name: 'Component',
                            直接打开组件
                            Component:MyComponent,
                            可为object  ps：{字段1：{值：使用的组件，值：使用的组件}}
                            Component: {
                                flowId: {
                                    zjPartyFeeUse: FlowFormByDF,
                                    zjYongYin: FlowFormByYY
                                },
                                条件满足会覆盖上面的flowId条件
                                sendUserName: {
                                    "王宗明测试": FlowFormByYY,
                                }
                            }
                            render: function () {
                                return '<a>详情</a>'
                            },
                            onClick:function(obj){//内置的按钮一般不需要传
                                console.log(obj)
                            }
                        },
                        {
                            name: 'diy',
                            render: function (rowData) {
                                return '<a>自定义</a>';
                            },
                            onClick: function (rowData) {//内置的按钮一般不需要传
                                alert(rowData.documentId)
                            }
                        },
                    ]
                }
            }
        ],
    }
