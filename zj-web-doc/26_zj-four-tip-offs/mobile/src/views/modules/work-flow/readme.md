    
    
    eg:
    //流程 zjYongYin   
    //路由名为 FlowByzjYongYin 

    ps：所有字段配置同步qnn-form 
        特殊属性为opinionField 为领导意见标识  在申请人能编辑时不能编辑领导意见
        eg{
            type: "textarea",
            label: "主管意见",
            field: "opinionField2", 
            addShow: false,
            opinionField:true, //领导意见的特殊标识
        },


    {
        label: "zjYongYin流程路由配置",
        children: [
            { 
                label: "流程列表",
                defaultPath: "FlowByzjYongYin/0/todo",
                pathName: "FlowByzjYongYin/:flowId/:status", //流程id 流程类状态（待办已办）
                comKey: "FlowByzjYongYin",
            },
            {
                label: "流程处理",
                defaultPath: "FlowByzjYongYin/0/todo/0",
                pathName: "FlowByzjYongYin/:flowId/:status/:workId", //流程id 流程类状态（待办已办） workId（等于0是创建流程其他是处理流程）
                comKey: "FlowByzjYongYin",
            }
        ]
    }


    流程列表
    待办列表 http://localhost/app/#/app/FlowByzjYongYin/zjYongYin/todo 
    已办列表 http://localhost/app/#/app/FlowByzjYongYin/zjYongYin/hastodo 

    新增流程
    http://localhost/app/#/app/FlowByzjYongYin/zjYongYin/todo/add    ***workId（新增必须是add）


    //处理流程时的网址(不用管，点击列表会自动跳)
    http://localhost/app/#/app/FlowByzjYongYin/zjYongYin/todo/123456456  
    

    列表配置

        <FlowList  
            CustomItem={(obj)=>{}} //自定义item 【可选】 
            //路由名字 【必填】
            formLink={{
                zjYongYin: "FlowByzjYongYin",
                zjYyOutSeal: "FlowByzjOutYongYin",
            }}  
            {...this.props} 【必填】
        />

    表单配置
        调用这个必须给容器设置一个高
         style={{height:'100vh'}}
        <Form
            //固定配置
            title:['applyUserId', 'sendTime', '用印申请'], //标题字段 array|string  规则 表单中取不到时就直接赋值给title 为数组就将几个字段值拼接起来
            apiNameByAdd: 'addFlowSealInLaunch',
            apiNameByUpdate: 'updateFlowSealAfterSubmit',
            apiNameByGet: 'getZjYySealApplyDetailByFlowWorkId',
            flowId:"", //string | (props)=>string
            
            //以下四个属性只需会有一个参数 值为数据  需要返回新的数据
            //申请时提交的参数自定义格式化
            createParamsFormat: props.createParamsFormat || function(obj, props) {},
            //打开流程时提交的参数自定义格式化
            openParamsFormat: props.openParamsFormat || function(obj, props) {},
            //打开流程时获得的数据自定义格式化
            flowDataFormat: props.flowDataFormat || function(obj, props, resData) {}, 
            //打开流程时动态改变增删改一些字段
            fieldsCURD: props.fieldsCURD || function(fields[], flowData, props) {}, 
            //action时改变参数的方法
            actionParamsFormat:(params, props)=>obj, 
            {...this.props}  //【必填】
        />
        

        

