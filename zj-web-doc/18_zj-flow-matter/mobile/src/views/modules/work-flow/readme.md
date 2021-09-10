### 配置基于 qnn-table & qnn-form， 内部表单和表格实现也是基于 table 和 form

#### 一些特殊字段说明

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
            qnnDisabled:true, //任何情况下都禁用
        },

#### 路由配置

    流程列表
    待办列表 http://localhost/app/#/app/FlowByYYAwait  (其中YY为自定义段 其他勿改)
    已办列表 http://localhost/app/#/app/FlowByYYOver   (其中YY为自定义段 其他勿改)

    ps:FlowByYYAwait都是自定义的 需要在流程列表的js(index.js)中判断使用接口（比如判断FlowByYYAwait就使用待办）


    新增流程(FlowByYYAwait跟上面列表最后一个路由段一样，待办已办都行/flowId/workId)
    http://localhost/app/#/app/FlowByYYAwait/zjPartyFeeUse/add/0    ***workId（新增必须是add）


    处理流程
    http://localhost/app/#/app/FlowByYYAwait/zjPartyFeeUse/:workid/:title

#### 具体调用配置

*   列表配置（直接调用 qnn-table 即可 具体看案例）

    ....

*   表单配置

    引入
    import { Form } from "../modules/work-flow";

       <Form
           //固定配置
           title:['applyUserId', 'sendTime', '用印申请'], //标题字段 array|string  规则 表单中取不到时就直接赋值给title 为数组就将几个字段值拼接起来
           apiNameByAdd: 'addFlowSealInLaunch',
           apiNameByUpdate: 'updateFlowSealAfterSubmit',
           apiNameByGet: 'getZjYySealApplyDetailByFlowWorkId',
           flowId:"", //string | (props)=>string
           closeedToUrl:"有该地址的话 处理流程后直接跳转到该地址"
           
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

           在传入的配置有某一处配置有改变时候使用
           比如通过state改变某个字段配置的下拉选择项
           初始值不可传入 turn 否则打开流程不会去设置值表单值 
           isNeedClassifyConfig={this.state.isNeedClassifyConfig}

            组件会在刷新完配置候自动调用该方法该方法内容 必须包含将isNeedClassifyConfig设置为false的操作
           changeIsNeedClassifyConfig={()=>{
               this.setState({
                   isNeedClassifyConfig:false
               })
           }}

           formTailLayout={
               ...按钮布局
           }

           直接发起的弹窗的特殊配置
           isInQnnTable
           noForm  //没有form的标识


            在线编辑文档  使用此功能记住需要在cdn.js中引入ntkoofficecontrol.min.js
            是否启用在线文档编辑、新增功能 默认false
            isHaveDoc={false}

            因为红头文件没有在表单里面所有得单独配置下以下这几个配置
            docFieldLable:"红头文件", //label
            docFieldName: "redHeaderFiles", //field
            docFieldIsRequired:true, //是否必填默认true
            docFormFormItemLayout:{
                labelCol: {
                    xs: { span: 24 },
                    sm: { span: 3 }
                },
                wrapperCol: {
                    xs: { span: 24 },
                    sm: { span: 21 }
                }
            },


            红头模板 默认[]
            注意 id必須是1開頭的數字** id必须是number类型  最小值11
            redModels={[
                {
                    id: 11, //用于文档页面菜单按钮排序和点击后区使用哪个模板使用
                    name: "黑龙江省交通运输厅文件", //菜单按钮名字
                    url: "http://cdn.apih5.com/test/red2.doc"
                },
                {
                    id: 12, //用于文档页面菜单按钮排序和点击后区使用哪个模板使用
                    name: "模板二", //菜单按钮名字
                    url: "http://cdn.apih5.com/test/red1.doc"
                }
            ]}

            最大创建文档的数量 默认99999999
            docMaxNumber={2}

            文档编辑器静态目录（非特殊情况无需更改） 默认放于http://cdn.apih5.com/ntko  注意：地址最后不可加 /
            editDocCdnAddress="http://cdn.apih5.com/ntko"

            文档编辑器页面地址(相对文档编辑器静态目录 非特殊情况无需更改) 默认/editindex.html
            editDocAddress="/editindex.html"
    />
