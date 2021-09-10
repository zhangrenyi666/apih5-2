<h1><center>qnn-table说明文档</center></h1>
 
####文档导航

<ul> 
    <li><b><a href="#download">下载</a></li>
    <li><b><a href="#import">引用</a></li>
    <li><b><a href="#example">使用实例</a></li> 
    <li><b><a href="#commonByQnnForm">qnn-table配置</a></li>
    <li><b><a href="#fieldConfig">字段配置的通用属性说明</a></li> 
    <li><b><a href="#actionBtns">操作按钮配置</a></li> 
    <li><b><a href="#funcs">手动调用方法</a></li>  
    <li><b><a href="#propsReadme">function/自定义组件 props 中参数说明</a></li>  
    <li><b><a href="#tabs">tabs页面配置</a></li>
    <li><b><a href="#otherDetailConfig">其他详细配置（最后一列操作列配置、表单块、表格块...）</a></li>
    <li><b><a href="#apis">接口统一配置（暂不可用）</a></li>
    
</ul>
<br />

###### <a id="import">下载</a>

    yarn安装：

        yarn add qnn-form

    npm安装：

        npm i qnn-form

###### <a id="import">引用</a>

    import QnnForm from 'XXX/qnn-form'

###### <a id="example">使用实例</a>

    import React,{ Component } from "react";
    import QnnTable from "../modules/qnn-table";
    import { Form } from 'antd';

    class index extends Component {
        render() {
            return (

                //不建议将不用的数据传入props中
                //特别是在自定义组件中使用QnnTable组件把整个props在传入到QnnTable会出些奇怪的问题

                <QnnTable
                    myFetch={this.props.myFetch}
                    headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                    history={this.props.history}
                    match={this.props.match}

                    //上传文件使用的 
                    //(apiName)=>(e)=>Promise(({success, data, message})=>{/**code...**/}) 
                    //e:{ target:files[file] }
                    upload={this.props.myUpload}

                    //自定义组件key定义
                    componentsKey={{
                        myDiyComponent: DIY_COMPONENT
                    }}

                    //获取qnn-table实例
                    wrappedComponentRef={(me) => {
                        this.table = me;
                    }}

                    formConfig:[
                        {
                            table: {
                                title: '姓名', //表头标题
                                dataIndex: 'name', //表格里面的字段
                                key: 'name',//表格的唯一key ,
                            },
                            form: {
                                type: 'date',
                                field:'name',// [可选] 默认取table配置的title属性
                                placeholder:"请输入..."
                            }
                        }
                    ],

                    方法集合
                    method={{
                        onClick:()=>{},
                        ...
                    }}

                    //手动执行刷新 并且 关闭刷新
                    isNeedClassifyData={isNeedClassifyData}
                    isNeedClassifyDataCb={() => {
                        this.setState({
                            isNeedClassifyData: false
                        })
                    }}

                    //抽屉关闭或者打开后的回调
                    drawerShowToggle:({drawerIsShow, ...obj})=>void

                    //{...其他需要用到的数据可手动传入 在自定义组件中或者回调方法中能获取到}
                />
            );
        }
    }

    export default Form.create()(idnex);

###### <a id="commonByQnnForm">qnn-table 配置</a>

    ps:
        所有函数都建议写为bind方式去绑定函数执行，
        可做到配置和方法完全。
        配置代码清晰，增强代码可读性。
        eg:
        fetchConfig: "bind:sendUserNameDisabled"


    {
        ajax方法 ()=>Promise(({success, data, message})=>{/**code...**/})
        myFetch={this.props.myFetch}

        上传时给后台的头信息
        headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}

        路由信息
        history={this.props.history}
        match={this.props.match}

        自定义组件key定义
        componentsKey={{
            myDiyComponent: DIY_COMPONENT
        }}

        获取qnn-table实例
        wrappedComponentRef={(me) => {
            this.table = me;
        }}

        字段配置（查看字段配置） [array | ()=>formConfig]
        formConfig:[]

        操作按钮配置 [array | ()=>actionBtns]  （查看操作按钮配置）
        actionBtns:[]

        操作按钮的位置  top | bottom  [string]  默认top
        actionBtnsPosition:"top",

        抽屉tab页配置
        tabs:[],

         动态获取配置
        [obj | func]
        getBackEndConfig={{
            apiName:'qnnTable', //                  [string | func]
            params:{},//自动在浏览器地址取值 或者 表单中取值 都去不到则为空 //      [obj | func]
            otherParams:{}, //定死值  //             [obj | func]
            请求成功后的回调
            success:({res, props})=>void


        }}

        字段值改变后将自动调用
        fieldsValueChange: (props,changedValues,allValues)=>void

        移动端列表项样式  ()=>reactDom
        mobileListItem={(props)=>{
                return <div style={{height:'100px', boxSizing:'border-box', border:'1px solid red',  marginBottom:10}}>
                {props.rowID}
                </div>}}

        移动端 列表项被点击
        mobileItemClick={()=>void}

        移动端时有效
        详情表单form的fetchConfig配置和qnn-form的fetchConfig配置一样
        formFetchConfig={}


        移动端时有效
        内置样式 目前只有style1
        mobileListItem="style1"

        移动端时有效
        列表项中的字段布局
        layout={
            [
                //頭部
                ["flowName","flowStatus"],
                //内容
                [{value:"title",label:"内容"}],
                //底部
                ["sendUserName",{ type: 'datetime',value: "sendTime" },"nodeName"]
            ]
        }

        移动端时有效
        列表标题
        title="普通qnn-table测试"

        选择框的默认属性配置获取 ()=>object
        getRowSelection: function (obj) {
            return {
                //设置某行为禁止选中
                getCheckboxProps: record => ({
                    name:record.name,
                    disabled: record.flowStatus === '审核中', // Column configuration not to be checked
                }),
            }
        },

        表格的ajax配置(更新后将自动刷新页面)  可为function 返回值为object
        fetchConfig: {
            apiName: 'getSxDocumentInfoList',//可为function 返回必须为string
            params:{},//从浏览器地址取值 或者从表单中取值
            otherParams: {}, //需要固定写死的参数  可为function 返回必须为object
            success:(res)=>{}  //请求完成后的回调参数（注意 是完成  不是成功）
        },

        抽屉默认是否出现  默认false [bool]
        DrawerShow:false,

        表格顶部描述说明
        desc:'这是一段描述...',

        表格数据（更新后将自动刷新页面） 默认为空 当fetchConfig存在时无意义
        data: [],

        同步antd table组件配置 ***必传
        antd: {
            设置主键的方式两种
            1、
            rowKey:'documentId',
            2、
            rowKey: function (row) {// ***必传  [string | func]
                return row.documentId
            },

            设置表头
            title:(curPage)=>void
            ps:title在qnn-table中可为string

            //title为string时有意义
            titleStyle:{},

            ...其他参照antd Table配置
        },

        搜索form区域一行有多少个表单项  默认不设置
        当对字段单独设置spanSearch属性后改配置将对该字段失效
        searchFormColNum:3,

        表格分页配置
        limit: 10,   //每页显示条数 默认10
        curPage: 1, //当前页  默认1、

        同步 antd 的分页组件配置
        paginationConfig: {
            position: 'bottom'
        },

        没有分页的表格
        paginationConfig: false,

        抽屉的配置 同步antd的Drawer组件配置
        drawerConfig: {
            width:800,
            maskClosable:false, //点击蒙层是否关闭抽屉  默认true
        },

        抽屉左上角文字配置 可为false false就是不显示该区域
        labelConfig: {
            actionBtn: '操作',//行右边下拉菜单标题
            detail: '详情',//抽屉详情左上角文字
            add: '新增',//抽屉新增左上角文字
            edit: '编辑' //抽屉新增左上角文字
        },

        是否显示选择框  默认true
        isShowRowSelect: true,

        全局提示框 给false将 返回string | reactDom
        infoAlert: function (selectedRows) {
            return '已选择 ' + selectedRows.length + '项';
        },

        移动端模型
        mobileModel:"flow", //内置flow

        抽屉布局配置
        left指左边内容  right指右边内容  name: "qnnForm" 为formConfig表单
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


        是否在表格第一个行加搜索行 [boolean]  默认false (ps：加了后需要给字段添加对应的fieldsConfig配置 具体参见field配置)
        firstRowIsSearch: true,

        抽屉中的表单滚动条滚动监听
        formContainerOnScroll: e => {
            const dom = e.target;
            const scrollTop = dom.scrollTop;
            //some code...
        },

        每个表单项的布局 -- 抽屉中表单配置
        小提示：有qnnTable类型的类型时 可以设置满一点
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

        每个表单项的布局 -- 搜索区域
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

        自定义渲染表格行（不包括表头）
        diyTableRow:(reactDOM, props)=>reactDOM,

        表单是否被输入过提示弹出样式 0（modal 默认） | 1（顶部滑出）
        formIsChangeedAlertModalType:"0",

        被输入过的的提示文字 reactDom
        formIsChangeedAlertTextContent: {title:string | dom, body:string:body},

    }

###### <a id="apis">接口统一配置（暂不可用）</a>

    统一接口配置 各接口配置同按钮的fetchConfig一样
    如果按钮未配置对应的接口将采用改配置
    apis: {
        //新增接口配置
        add: {
            apiName: "add",
            otherParams: {}, //object | ()=>object
        },
        //删除接口配置
        del: {
            apiName: "del",
        },
        //更新接口配置
        update: {
            apiName: "update",
            otherParams: {}, //object | ()=>object
        },
        //列表接口配置
        list: {
            apiName: "getTodoList",
            otherParams: {}, //object | ()=>object
        }
    },

###### <a id="fieldConfig">字段通用配置说明</a>

    {
        该字段是否是搜索条件 默认false  可为function 返回必须为bool
        开启后不可开启filter配置和isInSearch
        isInSearch: true,

        该字段是否在表格中存在 默认true 可为function 返回必须为bool
        isInTable: true,

        该字段是否在表单中存在 默认true 可为function 返回必须为bool
        isInForm: true,

        表格配置 同步antd table组件配置
        table: {
            title: '姓名', //表头标题
            dataIndex: 'createUser', //表格里面的字段
            key: 'createUser',//表格的唯一key
            fixed: 'left',//固定到左边
            tooltip:50,内容长度超过50字时使用...隐藏并且使用小气泡形势弹出全部内容
            width: 120, //列数比较多时必须设置
            type:'images', //目前内置images、select、month类型  (为select类型必须配置fetchConfig或者optionData)
            align:'left', //对齐方式 'left' | 'right' | 'center'  默认left
            defaultSortOrder: 'descend', //默认排序  'ascend' | 'descend'
            sorter: (a, b) => a.createUser - b.createUser, //排序规则

            是否开启该列的过滤条件  目前支持的类型 string number select datetime month
            开启后不可开启firstRowIsSearch配置和isInSearch
            filter:true,

            imgStyle:{}, //只有类型为images时有用  设置图片样式
            render: function (data,rows, index) {  //可返回 string | reactDom
                return data;
            },

            单元格点击
            为fn时点击后的方法回调参数{btnCallbackFn:this.btnCallbackFn, data, rows, formConfig}
            为name时可传 add,  del, edit, detail, Component, form(用户将改条数据推向后台某个接口)
            ps:不能和render同时存在

            可为func
            onClick: function (obj) {
                console.log(obj)
            },

            可为字符串【add,  del, edit, detail, Component, form】
            onClick: 'edit',

            单元格是否可被点击  ()=>boolean  |  {key:val}
            isCanClick:({ index, rowData, ...args })=>bool

            当行数据没有name时也将不可被点击  多个属性是&&匹配
            isCanClick:{name:"老王"}

            btns: [...参照actionBtns配置], //false为不显示抽屉底部按钮
            fetchConfig:{
                apiName:'editApi'
            }，
            drawerTitle:"新增", //如果点击后需要打开右边抽屉设置抽屉的标题 可为function  ()=>string

            单元格可编辑  目前支持的类型有 string、number、select、textarea
            可为bool 或者 (rowData,colData,props)=>bool
            tdEdit: true,
            tdEdit: function (rowData,colData,props) {
                if (rowData.flowName === '测试') return false;
                return true;
            },

            配置tdEdit后fieldsConfig必须配置
            该配置和formConfig的form【配置项】一样  field默认和dataIndex一样【也可单独配置】
            fieldsConfig: {
                type: "string", 
                placeholder:"请输入...",

                实现某个单元格不可被编辑
                disabled: function (obj:{record<rowData>, xxx}) {
                    if(obj.record.flowStatus === "654321"){
                        return true
                    }
                    return false;
                }
            },

            fieldsConfig配置为date格式
            fieldsConfig: {
                type: "date",
                showTime:false,
                format:"YYYY-MM-DD"
            },


            fieldsConfig配置为多选
            fieldsConfig: {
                type: "select",
                mode:"multiple",
                optionData: [
                    {
                        label: "吃饭",
                        value: "0"
                    },
                    {
                        label: "睡觉",
                        value: "1"
                    }
                ]
            }

            不配置fetchConfig的回调（配置fetchConfig该属性将无意义） 包含新旧行数据 新旧table数据以及插件props和内置方法
             tdEditCb:(obj)=>{
                 console.log(obj)
             },
            tdEditFetchConfig:{
                apiName:"upDate",
                params:{}, //可为func
                otherParams:{}, //可为func
            },
            配置了fetchConfig的回调 参数为后台返回数据 不配置将采用内置行为(失败成功都只是弹出提示)
             fetchCB:(res)=>{
                 console.log(res)
             }

            日期字段格式化
            format: "YYYY-MM-DD HH:mm:ss",

            是否强制去除表头搜索框（所占位置也将被清除）
            一般用于表头分组后设置到除了最底层的输入框出现后其他父层的输入框都隐藏
            默认false  [boolean]
            (存在意义：如果没有这个设置而是默认直接将表头分组中的每个父表头输入框去掉的话 将会导致父表头无法再特殊情况下加入搜索框了)
            noHaveSearchInput:false,

            表头分组
            单元格需要能编辑的话需要给父级也加上tdEdit: true,
            children元素按照qnn-table的formConfig[n].table格式结构配置
            children: [
                {
                    dataIndex: 'flowName',
                    title: "字段1",
                    type: "string",
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
                {
                    dataIndex: 'sendUserName',
                    title: "字段2",
                    type: "string",
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
                }
            ]
        },

        //表单配置 不配置将默认使用表格的配置的有【label, field, render】
        form: {
            label: '姓名',
            field: 'createUser',
            type: 'string',
            placeholder: '请输入',

            disabled:true,新增修改都禁用--暂不可用
            //决定表单是否能平铺   int 类型
            spanForm: 12', //表单中行格数 一行24格 默认24   表单
            spanSearch: 8, //表单中行格数 一行24格 默认8   搜索
            offsetForm: 0, //表单中行的偏移量 共24 默认0   表单
            offsetSearch: 0, //表单中行的偏移量 共24 默认0    搜索
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
            addDisabled:true,//新增禁用
            editDisabled:true, //修改禁用
            //设置某个环境下XXXshow是false时需要将其他两个show改为true，否则其他两个环境下也不出来
            addShow:true,//新增时在表单中隐藏  [booblen]  默认 true
            detailShow:true,//详情时在表单中隐藏  [booblen] 默认 true
            editShow:true,//编辑时在表单中隐藏  [booblen] 默认 true
            tooltip:10, //超过多少字后显示省略号隐藏   [number]
        },
    }

###### <a id="actionBtns">操作按钮配置</a>

    ps：name内置有 add、del、edit、detail、addRow、submit、cancel、form。其他需要自定义
    [
        //新增行一般用在表格可编辑的情况
        {
            name: "addRow",
            icon: "plus",
            type: "primary",
            label: "新增行",
            //新增时候的默认数据
            addRowDefaultData:{
                workId:"new",
                flowStatus:"请选择",
                flowName:"请输入",
                sendTime:"请选择",
                title:"请输入",
                limitTime:"请输入",
            },
            //ajax配置 和 fetchConfig一样
            addRowFetchConfig:{
                apiName:"addRow",
                otherParams:{}
            },
            //新增行完后的回调在配置fetchConfig后将无意义
            // addCb:(obj)=>{
            //     console.log(obj)
            // },

            //是否隐藏  [boolean | ()=>boolean]
            hide:function(obj){
                return false;
            },

            是否禁用  [boolean | ()=>boolean]
            disabled:function(obj){
                return false;
            },

        },

        //新增按钮
        {
            //自定义按钮key值 必须配置
            field:"addCancelBtn",
            willExecute:()=>void, //点击前
            name: 'add',//【add, addRow,  del, edit, detail, Component, form】
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
                    // hide: true, //可为fun
                    hide: function(obj) {
                        if (obj.btnCallbackFn.getActiveKey() === "2") {
                            return true;
                        } else {
                            return false;
                        }
                    },

                    //自定义按钮key值 必须配置 否则可能按钮点击后不会置为loading状态可能会导致重复提交问题
                    field:"addCancelBtn",
                },
                {
                    name: 'submit',//内置add del
                    type: 'primary',//类型  默认 primary
                    label: '提交',//提交数据并且关闭右边抽屉

                    格式化数据
                    paramsFormat:({params, props, ...})=>{return {...}},

                    配置 可控制是否提交表单
                    isCanSubmit: ({params, props, ...}, callback)=>callback(true), //callback(true) || callback(false)

                    // hide: true,
                    hide: function(obj) {
                        if (obj.btnCallbackFn.getActiveKey() === "2") {
                            return true;
                        } else {
                            return false;
                        }
                    },
                    fetchConfig: {//ajax配置

                        apiName: 'submit',

                        删除参数中的name、age字段
                        delParams:["name", "age"]
                    },

                    //自定义按钮key值 必须配置 否则可能按钮点击后不会置为loading状态可能会导致重复提交问题
                    field:"addSubmitBtn",


                    isValidate: true,//点击后是否验证表单 默认true
                }
            ]
        },

        //删除按钮
        {
            name: 'del',
            icon: 'delete',//icon
            type: 'danger',//类型  默认 primary  [primary dashed danger]
            label: '删除',
            fetchConfig: {//ajax配置
                apiName: 'del',
            },

            配置 可控制是否提交表单  params在这是个数组
            isCanSubmit: ({params, props, ...}, callback)=>callback(true), //callback(true) || callback(false)
        },

        //自定义页面
        {
            //自定义按钮key值 必须配置
            field:"addCancelBtn",
            使用自定义组件时配置js文件应该被打包不能放在public文件夹下
            自定义组件中props里有该条数据的所有数据
            name: 'Component',
            第一种方式：直接打开组件
            Component:MyComponent,
            第二种方式：可为object  ps：{字段1：{值：使用的组件，值：使用的组件}}  可混合第三种方式使用
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
            第三种方式：
            Component:"ComponentName",

            render: function () {
                return '<a>详情</a>'
            },
            onClick:function(obj){//内置的按钮一般不需要传
                console.log(obj)
            }
        },

        //自定义页面
        {
            name: 'diy',
            icon: 'more',//icon
            type: 'dashed',//类型  默认 primary  [primary dashed danger]
            label: '其他操作',
            //内置的按钮一般不需要传 传了就相当于一个回调
            onClick: function (obj) {
                console.log(obj)
            },
            //自定义按钮key值 必须配置
            field:"addCancelBtn",
        },

        //自定义页面
        {
            //自定义按钮key值 必须配置
            field:"addCancelBtn",

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
                    //设置字段
                    field:"pullPerson",
                    //打开后的回调 (这里obj是树组件的props)
                    treeDidMount:(obj)=>void,
                    //初始值
                    initialValue:[
                        {
                            value:"000",
                            label:'数据一'
                        }
                    ],
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


        //form（未完善...）
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
    ]

###### <a id="funcs">手动调用方法</a>

        this.table.xxx()

        可用方法

            关闭/开启抽屉
            closeDrawer(toggle:boolean, cb:()=>void)

            刷新数据
            refresh()

            格式化数据（内部调用的qnn-form的格式化数据）
            @type  'get' | 'set'
            @data  数据
            @formConfig  表单字段配置（注意是qnn-form的formConfig配置）
            sFormatData(data:object, formConfig:Array, type:string')

            qnn-table内部按钮调用方法（后续补充详细）
            action(rowInfo:object, rowData:object)

            执行搜索
            search()

            是否是移动端
            isMobile():boolean

            请求方法（回调方式 非promise方式）
            myFetch(apiName:string, params:any, success:()=>void):resonse

            请求方法（返回的是一个promise）
            fetch(apiName:string, params:any):promise(resonse:any)

            打开选人组件
            openTree(config:object)

            一些动作
            eg：打开新增抽屉
            action(
                {
                    drawerTitle: "新增",
                    name: "add",
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
                                apiName: "新增"
                            }
                        }
                    ]
                },
                {/*编辑时这里传入行数据即可 新增为空对象*/}
            );

            设置某个按钮loading状态 setBtnsLoading(action<add | remove>, field)
            setBtnsLoading

            设置某个按钮disabled状态 setBtnsLoading(action<add | remove>, field)
            setBtnsDisabled

            设置setActionBtns  setActionBtns([{...}])
            setActionBtns


            设置选中的数据
            setSelectedRows,

            置空已选项
            clearSelectedRows,

            获取选中数据
            getSelectedRows

            设置抽屉表单是否全部禁用setFormGlobalDisabledStatus(boolean)
            setFormGlobalDisabledStatus

            设置抽屉中的按钮setDrawerBtns(btns)
            setDrawerBtns

            设置抽屉中自定义组件的按钮setDrawerBtnsByComponent(btns)
            setDrawerBtnsByComponent

            设置抽屉表单字段
            setForms

            搜索表单的qnnFform组件实例化对象 参见qnnForm中的方法调用方式
            searchForm

            设置抽屉的配置
            setDrawerConfig(drawerConfig);

            获取抽屉的配置
            getDrawerConfig()   ()=>drawerConfig;

            数字格式化 为 金额等
            numberFormat: this.numberFormat,

            获取搜索参数
            getSearchParams:this.getSearchParams

            ...其他

---

##### <a id="propsReadme">所有 function/自定义组件 props 中参数说明（ps:自定义组件、自定义按钮点击事件、apiName 为 function 等...）</a>

    eg: fetchConfig:{
            apiName:function(obj){
                //obj携带的信息如下
                return 'xxx'
            }
        }

    obj
    {

        _formData   未格式化为qnn-form使用的数据 （抽屉打开状态才会有数据）
        formData    qnn-form中的表单数据 （抽屉打开状态才会有数据）tabs属性存在时为所有tabs项的表单内容集合
        curFormData  qnn-table配置了tabs属性时有效，当前tab项中表单内容
        formDataIncAllTabs   所有tabs页面的表单数据
        rowData     点击行的数据
        rowInfo     被点击的按钮的信息
        selectedRows    选中的行数据
        searchData  搜索form表单的数据
        props       qnn-table组件props
        isInQnnTable 是否是嵌入到qnn-table中的
        data        点击的单元格数据
        formConfig       qnn-table的formConfig配置
        tableFns         一般在表单在抽屉中时抽屉中的表单需要调用表格的方法就能用到此方法
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

                parentProps 代码块中可以使用父级的props

                isQnnTable  是否在qnnTable中  bool

                设置选中的数据
                setSelectedRows,

                置空已选项
                clearSelectedRows,

                获取选中数据
                getSelectedRows

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


                设置tab当前激活项
                setActiveKey

                获取
                getActiveKey:()=>this.state.tabsIndex, //获取tab激活项索引的方法

                设置抽屉表单是否全部禁用setFormGlobalDisabledStatus(boolean)
                setFormGlobalDisabledStatus

                设置抽屉中的按钮setDrawerBtns(btns)
                setDrawerBtns

                设置抽屉中自定义组件的按钮setDrawerBtnsByComponent(btns)
                setDrawerBtnsByComponent

                设置抽屉表单字段
                setForms

                搜索表单的qnnFform组件实例化对象 参见qnnForm中的方法调用方式
                searchForm

                设置抽屉的配置
                setDrawerConfig(drawerConfig);

                获取抽屉的配置
                getDrawerConfig()   ()=>drawerConfig;

                深度克隆
                deepCopy


            }
    }

###### <a id="tabs">tabs 页面配置</a>

    ps：内置name有 qnnForm和qnnTable 可以自定义
    [
        {
            field: "one",
            name: "qnnForm",
            title: "表单",
            //[boolean | ()=>boolean]   默认false
            disabled:true,
            content:{
                formConfig:[{...}],
                btns:[{...}]
                ...具体查看qnn-form配置
            }
        },
        {
            field: "two",
            name: "qnnTable",
            title: "表格",
            //[boolean | ()=>boolean]   默认false
            disabled:true,
            content:{
                formConfig:[{...}],
                actionBtns:[{...}]
                ...具体查看qnn-table配置
            }
        },
        {
            field: "diy1",
            name: "diy1",
            title: "自定义页面",
            //[boolean | ()=>boolean]   默认false
            disabled:true,
            content: props => {
                return <div>自定义组件</div>;
            }
        }
    ]

###### <a id="otherDetailConfig">其他详细配置</a>

    {


        //所有字段配置
        //可为function 返回值为array
        // ***必传
        formConfig: [
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

            //表单块配置
            {
                isInTable: false,
                form: {
                    //content就是qnn-form配置
                    label: "表单块",
                    field: "qnnForm",
                    type: "qnnForm",
                    qnnFormConfig: {
                        formConfig: [
                            //普通字段
                            {
                                type: "string",
                                label: "普通字段",
                                field: "config2-1", //唯一的字段名 ***必传
                                placeholder: "请输入" //占位符
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
                        btns: []
                    }
                }
            },

             //表格块配置
            {
                isInTable: false,
                form: {
                    //content就是qnn-form配置
                    label: "表格块",
                    field: "qnnTable",
                    type: "qnnTable",
                    qnnTableConfig: {
                        //qnn-table配置
                    }
                }
            },

            //操作列按钮配置
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
                            和render不可同时存在
                            label:"编辑",

                            //不建议使用render 建议直接使用label代替
                            render: function (data,rows, index) {
                                return '<a>编辑</a>';
                            },

                            设置label的样式 默认{ color: '#1890ff', cursor:'pointer' }
                            labelStyle:{ color: '#1890ff', cursor:'pointer' },

                            内置name有【add,  del, edit, detail, Component, form】
                            name: 'edit',

                            表单里面的按钮  name内置 【submit， cancel】 可为func ()=>array
                            formBtns: [
                                {
                                    name: 'cancel', //关闭右边抽屉
                                    type: 'dashed',//类型  默认 primary
                                    label: '取消',
                                },
                                {
                                    //自定义按钮key值 必须配置
                                    field:"addCancelBtn",
                                    name: 'submit',//内置add del
                                    type: 'primary',//类型  默认 primary
                                    label: '保存',//提交数据并且关闭右边抽屉
                                    fetchConfig: {//ajax配置  ---可为func
                                        apiName: 'save',
                                    }
                                }
                            ],

                            //条件显隐配置
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
                            name: 'detail',
                            render: function (data,rows, index) {
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
                            render: function (data,rows, index) {
                                return '<a>自定义</a>';
                            },
                            onClick: function (data,rows, index) {//内置的按钮一般不需要传
                                alert(rowData.documentId)
                            }
                        },
                    ]
                }
            }
        ],
    }
