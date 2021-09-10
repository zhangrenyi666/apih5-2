// 拉人移动端案列
import React, { Component } from "react";
import Tree from "../modules/tree";
export default class index extends Component {
    render() {
        return (
            <div>
                <Tree
                    // visible
                    selectModal="1" //0不可选  1单选(默认)  2多选（暂不可用）
                    myFetch={this.props.myFetch}
                    // btnShow={} //是否显示底部按钮
                    //()=>void
                    yes={value => {
                        console.log("确定:", value);
                    }}
                    //()=>void
                    reset={() => {
                        console.log("重置");
                    }}
                    //(obj{nodeInfo})=>void
                    change={value => {
                        console.log("切换值:", value);
                    }}
                    //将选择的值自定义渲染
                    // (obj{nodeInfo})=>string
                    valueRender={obj => {
                        let parentNameAll = obj.parentNameAll;
                        let _fLabel = parentNameAll.replace(/,/gi, "→");
                        return _fLabel;
                    }}
                    //右键菜单
                    rightMenuOption={[]}
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
                    //ajax请求配置
                    fetchConfig={{
                        parmasKey: "parentId", //点击节点后将节点id赋值该key上传递给后台
                        apiName: "appGetGxProjectLevel",
                        nodeAddApiName: "nodeAddApiName",
                        params: {
                            parentId: "0"
                        }
                    }}
                    //键值配置 默认{value:value,label:label,children:children}
                    keys={{
                        label: "levelName",
                        value: "levelId",
                        children: "zlProjectLevelList"
                    }}
                />
            </div>
        );
    }
}
