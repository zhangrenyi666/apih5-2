- log

    未修复问题： 

        按钮的form类型需要完善、
        富文本类型没写完、
        新增|修改|详情字段隐藏配置需要优化 
        action.js在循环中写了函数
     
    v0.0.49
        移动端样式完善
        

    v0.0.48
        点击按钮增加自定义组件类型

    v0.0.47
        增加desc属性
        修复表单取值错误
        table增加select类型
        增加表格单元格可点击配置
        ...

    v0.0.463   

        修复表格控制区edit name类型
        

    v0.0.462     

        修复无限联动下拉 
        
    v0.0.461
        修复新增修改详情隐藏 

    v0.0.46
        
        修复默认值问题    
        新增linkage组件
        
    v0.0.45

        新增item组件
        修改字段在新增和详情编辑时字段可针对该动作下单独出现或者隐藏
        修改下拉或者层级下拉多选时自动将值用逗号连接给后台，后台只需要存入即可
        按钮内置name新增form  可弹出qnn-form组件  ----暂未完善

    v0.0.43

        添加camera组件 | 给时间类组件添加 timestamp 属性可将后台给的10位时间戳转为13位 将前台13位转为10位给后台

------

    <QnnTable
        fetch={this.props.myFetch}
        headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
        {...window.tableConfig.testPage}
    />

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

        //表格分页配置
        limit: 10,   //每页显示条数 默认10
        curPage: 1, //当前页  默认1
        paginationConfig: {// 同步antd的分页组件配置
            position: 'bottom' 
        },

        drawerConfig: { //抽屉的配置 同步antd的Drawer组件配置
            width:800,
            maskClosable:false, //点击蒙层不关闭抽屉 
        },
        //文字配置
        labelConfig: {
            actionBtn: '操作',//行右边下拉菜单标题
            detail: '详情',//抽屉详情左上角文字
            add: '新增',//抽屉新增左上角文字
            edit: '编辑' //抽屉新增左上角文字
        },

        isShowRowSelect: true,//是否显示选择框  默认true

        //全局提示框 给false将
        infoAlert: function (selectedRows) {
            return '已选择 ' + selectedRows.length + '项';
        },

        //每个表单项的布局 -- 表单
        formItemLayout: {
            //默认数据
            labelCol: {
                xs: { span: 24 },
                sm: { span: 5 }
            },
            wrapperCol: {
                xs: { span: 24 },
                sm: { span: 19 }
            }
        },
        //每个表单项的布局 -- 搜索区域
        formItemLayoutSearch: {
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

        //所有操作按钮
        //内置 新增add 删除del
        //可为function 返回值为array
        actionBtns: [   
            {
                name: 'add',//【add,  del, edit, detail, Component, form】
                icon: 'plus',//icon
                type: 'primary',//类型  默认 primary
                label: '新增',
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
                    key: 'delete',//默认deleteList
                },
                //内置的按钮一般不需要传 传了就相当于一个回调
                onClick: function (data) {
                    console.log(data)
                }
            },
            {
                //使用自定义组件时配置js文件应该被打包不能放在public文件夹下
                name: 'Component', 
                Component:MyComponent,//MyComponent组件中props里有该条数据的所有数据
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
                    type:'', //目前内置images 类型
                    align:'left', //对齐方式 'left' | 'right' | 'center'  默认left
                    defaultSortOrder: 'descend', //默认排序  'ascend' | 'descend'	
                    sorter: (a, b) => a.createUser - b.createUser, //排序规则
                    imgStyle:{}, //只有类型为images时有用  设置图片样式
                    render: function (text) {
                        return text
                    },
                    /*
                        单元格点击
                        为fn时点击后的方法回调参数{btnCallbackFn:this.btnCallbackFn, data, rows, formConfig}
                        为name时可传 add edit detail pull(用户将改条数据推向后台某个接口)
                        ps:不能和render同时存在
                    */
                    // onClick: function (obj) {
                    //     console.log(obj)
                    // },
                    onClick: 'edit',
                    fetchConfig:{
                        apiName:'editApi'
                    }
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
                    // spanForm: '12', //表单中行格数 一行24格 默认24
                    spanSearch: '8', //表单中行格数 一行24格 默认8
                    required: true,


                    pullJoin:false,//拉取来的select不使用逗号拆分（多选才能用到）
                    pushJoin:false,//给后台的select数据不适用逗号连接（多选才能用到）

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
            }, {
                isInTable: false,
                form: {
                    label: '图片',
                    field: 'images',
                    type: 'images',
                }
            }, {
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
                showType: 'bubble', //出来的样式 bubble（气泡）  tile（平铺） 默认bubble
                table: {
                    width: 80,
                    // title: "操作",
                    key: "action",//操作列名称
                    fixed: 'right',//固定到右边
                    btns: [
                        {
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
                            Component:MyComponent,//MyComponent组件中props里有该条数据的所有数据
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
