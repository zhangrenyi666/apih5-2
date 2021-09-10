v0.0.1
 
    <Tree
        visible  //树结构是否处于出现状态

        modalType:"drawer", //common  drawer  抽屉出现方式或者普通的

        selectText="选择"  //false为不显示

        selectModal="1" //0 不可选 1 单选(默认) 2 多选（暂不可用）

        myFetch={this.props.myFetch}

        btnShow={true} //true(默认)  false

        //()=>void
        yes={value => {
            console.log("确定:", value);
        }}

        //()=>void
        reset={() => {
            console.log("重置");
        }}

        //取消按钮
        cancel={()=>{}}

        //(obj{nodeInfo})=>void
        change={value => {
            console.log("切换值:", value);
        }}

        //将选择的值自定义渲染
        // (obj{nodeInfo})=>string
        valueRender={obj => {
            let parentNameAll = obj.parentNameAll;
            let \_fLabel = parentNameAll.replace(/,/gi, "→");
            return \_fLabel;
        }}

        //设置树节点props（eg:禁用某一项）
        setNodeProps = {(item, name, this.keys)=>obj};
        
        //树节点点击
        nodeClick = (node, selectedKeys)=>void
        
        //设置节点不禁用
        setNodeProps={nodeInfo => {
            return { disableCheckbox: false };
        }}

        //右键菜单选项
        rightMenuOption={[
            {
                    label: "新增子节点",
                    name: "add", //内置 add del ediitt
                    cb: newNodeInfo => {  //内置name改组件为回调   自定义则为点击且参数为右键节点
                        console.log(newNodeInfo);
                    }
            },
        ]}

        //节点的自定义渲染
        // (obj{nodeInfo})=>string
        nodeRender={nodeData => {
            return (
                <span>
                    {nodeData["levelName"]}（总
                    {nodeData["totalNum"]}道工序，已完成
                    {nodeData["finishedNum"]}道）
                </span>
            );
        }}
 

        fetchConfig={{
            parmasKey: "parentId", //点击节点后将节点id赋值该key上传递给后台
            apiName: "appGetGxProjectLevel",
            // searchApiName: "",
            params: {
                parentId: "0"
            },
            nodeAddApiName="", //新增节点的接口
            nodeAddParamsFormat={(nodeInfo, parentNode)=>object} //传递给后台的新增数据处理
            nodeEditApiName="",
            nodeEditParamsFormat={(nodeInfo, curNode)=>object}
            nodeDelApiName="",
            nodeDelParamsFormat={(nodeInfo, curNode)=>object}
        }}

        keys={{
            label: "levelName",
            value: "levelId",
            children: "zlProjectLevelList"
        }}

        //弹出抽屉配置  参考antd Drawer配置
        drawerConfig={{}}
    />
