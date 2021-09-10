
    
    与tree组件的区别 

        tree-v2不可配置复选框 
        tree-v2没有抽屉弹出方式

        api与tree组件基本一致

    
    
    
    使用时应该给容器设置一个高，

    主要用于拖动

    <TreeV2

        默认数据 默认数据存在fetchConfig属性将失去意义 [array]
        treeData = {[]}      

        myFetch={this.props.myFetch}
 
        (obj{nodeInfo})=>void
        change={value => {
            console.log("切换值:", value);
        }}

        树节点点击
        nodeClick = (node, selectedKeys) => void

        树节点右键
        nodeRightClick = (node, props) => void
        
        设置节点属性 可设置项【disableCheckbox、disabled、iconisLeaf、key、selectable、title】
        setNodeProps={nodeInfo => {
            return { disableCheckbox: false };
        }}

        右键菜单选项
        可为(rightClickNode)=>rightMenuOption
        rightMenuOption={[
            {
                label: "新增子节点",
                name: "add",  内置 add del edit  addRootNode（新增跟节点） 
            },
            {
                label: "自定义",
                name: "diyXXX", 
                cb: newNodeInfo => {
                    console.log(newNodeInfo);
                }
            },
        ]}

        树结构容器中的右键菜单 [ array | null ]
        rightMenuOptionByContainer={[
            {
                label: "新增根节点",
                name: "addRoot"  
            }
        ]}

        节点的自定义渲染(obj{nodeInfo})=>string   返回 null 或者 false则不渲染
        nodeRender={nodeData => {
            return (
                <span>
                    {nodeData["levelName"]}（总
                    {nodeData["totalNum"]}道工序，已完成
                    {nodeData["finishedNum"]}道）
                </span>
            );
        }}

        /* 
        ***** 参数注释 ****
        *
        *   event：事件对象
        *   node、dragNode:被拖动的节点
        *   dragData:拖动的节点数据
        *   targetData：目标节点数据 
        *   callbackFn：
        *       {             
        *           Toast, antd-mobile  Toast(消息提示组件)        
        *           props, 页面props        
        *           onLoadData:this.onLoadData, 加载节点数据，调用传入需要加载的node节点 [treeNode]   
        *          
        *       }
        */

        被拖动的节点是否可以被放入到目标地
        isCanDropTarget:({callbackFn, restoreTreeData})=>boolean

        是否开启拖动 [bool]
        draggable

        拖动开始
        onDragStart={({event, node, callbackFn})=>void}  

        拖动中(废弃)
        onDragLeave={({event, node, callbackFn})=>void}  

        拖动放下后 
        @allDatas 拖动后的整个树节点数据 
        @dragNode 被拖动的树节点
        @dragData 被拖动的树节点数据
        @targetNode 拖动到的目标节点
        @targetData 拖动到的目标节点数据
        @callbackFn 一些方法
        ...
        onDrop={({ allDatas, dragNode, targetNode, dragData, targetData, callbackFn})=>void}  
        
        fetchConfig={{
            parmasKey: "parentId",  点击节点后将节点id赋值该key上传递给后台
            apiName: "appGetGxProjectLevel",
            searchApiName: "",
            params: {
                parentId: "0"
            },
            //没次请求树结构都会调用
            success(data,treeNode,allNodeData):void 

            新增节点的接口
            nodeAddApiName:"", 
            数据处理 新增根节时parentNode === 'root'
            nodeAddParamsFormat:(nodeInfo, parentNode)=>object  
            nodeAddCb:(response:{success, message, data}, props)=>void

            //编辑
            nodeEditApiName:"", 
            nodeEditParamsFormat:(nodeInfo, curNode)=>object
            nodeEditCb:(response:{success, message, data}, props)=>void

            //删除
            nodeDelApiName:"",
            nodeDelParamsFormat:(nodeInfo, curNode)=>object
            nodeDelCb:(response:{success, message, data}, props)=>void
        }}

        keys={{
            label: "levelName",
            value: "levelId",
            children: "zlProjectLevelList"
        }}  

        ref={(me)=>this.tree=me}

        是否可以被点击展开
        canExpand={({nodeData, props})=>{ 
            return false;
        }}
    />

    方法

        获取当前树结构数据 (treeData)=>void  eg：let curTreeData = this.tree.getTreeData()
        getTreeData()

        设置树结构数据 (treeData)=>void
        setTreeData(treeData)

        