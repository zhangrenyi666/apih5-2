 
调用方法： 

#####调用
    <PullPersion 
        help  是否显示帮助文档 
        onChange={(val)=>{}}  切换函数 [fn]
        transform={(val)=>{}} 在这个函数中需return出修改过得数据 [fn]
        title = '请选择'  弹出层左上角的标题 默认是  "请选择"  [string]
        loading = {false}  是否正在加载   默认false  [Boole]
        myFetch={}  请求方法 必传  [fn]  --未完成 暂未使用
        fetchConfig = {{apiName:'string', params:{}}}  必传  [object] --未完成 暂未使用
        ref={(el)=>this.aaa = el}
        label=""         可传可不传 [string || rDom]
        edit={true}      是否是编辑模式 默认true  [ Boole]
        selectType="0"   0部门和人员都能选 1只能选部门 2只能选人员   默认0   [string]
        treeData={}      树结构数据  {} || [] 根节点可以是多个可以是一个  [array || object] 
        maxNumber={}     最多选择数量  [number]  默认 999
        minNumber={}     至少选择数量  [number]  默认 0
        visible     是否显示弹出层 [boolen] 默认false
        //设置节点点击不可选择
        nodeClick={(nodeInfo)=>{return {canSelect:false}}}

        默认数据 (这里面数据结构不需要children)  [{label,value,type}, {label,value,type}]   [array]   
        defaultData = [] 

        useCollect  //使用收藏功能
        collectApi="appGetSysFrequentContactsList"  //查询收藏人员     接受后台参数[{xx:xxx,...}]
        collectApiByAdd = "appAddSysFrequentContacts" //新增收藏人员   传给后台的参数[{xx:xxx,...}]
        collectApiByDel = "appRemoveSysFrequentContacts" //删除收藏人员  传给后台的参数[{xx:xxx,...}]

        //搜索配置
        searchApi = ''  搜索时调用的api  [string]
        searchParamsKey = 'searchText';//搜索文字的K 默认是'searchText'   [string]
        searchOtherParams = {{pageSize:999}};//搜索时的其他参数  [object]

        k = { 默认就是用以下这些键值  （针对树结构的）
            {
                label: 'label',
                value: 'value',
                type: 'type',
                children: 'children'
            }
        }
    插件里的一些文字
    textObj = {{
        loading: 'loading...',  加载中的文字
        noData:'暂无数据',  
        rightTitle: '已选择的部门或成员',   右边列表的文字
        maxNumber:'选择达到了上限',
        minNumber:'选择个数不足',
        }}
      /> 
----
#####方法
*   获取数据：
        let sData = this.aaa.getSelectData();

*   设置数据：
        let sData = this.aaa.setData(); || 直接改变defaultData属性也可以
                

*   树结构数据格式： 

    {
        value:'',
        label:'',
        type:'0', //0根节点 1部门  2成员
        children:[]
    }

----
####界面截图
![截图](./img.png "正常使用时的截图")

---- 

####log:

    1.4.4

        修复某些性能问题

     1.3.4

        修复传入的noData文字配置去错问题
        修复清空输入框后返回的数据任然当成搜索返回数据问题（导致不渲染子节点）

     1.3.2

        新增nodeClick属性
     
     1.2.2

        新增打开后的回调treeDidMount
            

     1.1.2

        修复表单总已选数据项样式
        修复滚动条滚动后数据没了
       
    1.1.0 

        修复一个页面存在多个时渲染性能会出问题
        新增收藏功能

    1.0.X
        稳定运行版  

        新增
        maxNumber={}     最多选择数量  [number]  默认 999
        minNumber={}     至少选择数量  [number]  默认 0
      
    0.0.32
        1增加属性textObj
        2增加search属性
        3增加loading数据


    0.0.26
        修改bug

    0.0.2
        增加 onChange 属性回调可
            transform={(val)=>{}} 在这个函数中需return出修改过得数据
