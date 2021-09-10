//节点增删改等信息操作
import { Toast } from "antd-mobile";
const nodeAdd = function(cb, name) {
    const { children, label, value } = this.keys;
    let newNode = {
        [label]: "未命名",
        [value]: "newNode",
        edit: true //可编辑
    }; 
    if (name !== "root" && !this.rightClickNode.props.expanded) {
        this.rightClickNode.onExpand();
    }
    if (name !== "root" &&  !this.rightClickNode.props.dataRef[children]) {
        this.rightClickNode.props.dataRef[children] = [];
    }

    //先请求数据然后在新增
    const { nodeAddApiName, nodeAddParamsFormat } = this.props.fetchConfig;
    if (!nodeAddApiName) {
        console.error("新增节点必须配置nodeAddApiName");
        return;
    }
    if (nodeAddParamsFormat) {
        newNode = nodeAddParamsFormat(newNode, this.rightClickNode);
    }
    Toast.loading("请稍等...", 0);
    this.myFetch(nodeAddApiName, newNode).then(({ success, message, data }) => {
        if (success && data) {
            Toast.hide();
            data.edit = true;
            if (name !== "root") {
                this.rightClickNode.props.dataRef[children] = [
                    { ...data },
                    ...this.rightClickNode.props.dataRef[children]
                ];
            }
            this.setState(
                {
                    data: [...this.state.data],
                    onRightClickDomInfo: {
                        show: false
                    }
                },
                () => {
                    //获取到新增的节点
                    if(name === "root"){
                        // this.curNode = data;
                    }else{
                        this.curNode = this.rightClickNode.getNodeChildren()[0]; 
                    }
                }
            );
        } else {
            Toast.fail(message);
        }
        if (cb) {
            cb({ success, message, data });
        }
    });
};
const nodeDel = function(cb) {
    let nodeInfo = this.rightClickNode.props.dataRef;
    let delDom = this.rightClickNode.selectHandle.parentNode;
    const { nodeDelApiName, nodeDelParamsFormat } = this.props.fetchConfig;
    if (!nodeDelApiName) {
        console.error("修改节点必须配置nodeEditApiName");
        return;
    }
    if (nodeDelParamsFormat) {
        nodeInfo = nodeDelParamsFormat(nodeInfo, this.rightClickNode);
    }
    Toast.loading("请稍等...", 0);
    this.myFetch(nodeDelApiName, nodeInfo).then(
        ({ success, message, data }) => {
            if (success && data) {
                Toast.hide();
                delDom.style.display = "none";
                this.setState({
                    data: [...this.state.data]
                });
                if (cb) {
                    cb(data);
                }
            } else {
                Toast.fail(message);
            }
        }
    );
};
const nodeEdit = function() {
    this.curNode = this.rightClickNode;
    this.rightClickNode.props.dataRef.edit = true;
    this.setState({
        data: [...this.state.data],
        onRightClickDomInfo: {
            show: false
        }
    });
};
const rightMenuOptionClick = function(obj) {
    const { name, cb } = obj;
    switch (name) {
        case "add":
            this.nodeAdd(cb);
            break;
        case "addRoot": 
            this.nodeAdd(cb, "root");
            break;
        case "del":
            this.nodeDel(cb);
            break;
        case "edit":
            this.nodeEdit(cb);
            break;
        default:
            if(cb){
                cb(this.rightClickNode, obj); 
            }
    }
};
const postEditNodeInfo = function(nodeInfo) {
    // const {label} = this.keys;
    // if(this.curNode.props.dataRef[label] === nodeInfo[label]){
    //     //未修改过
    //     this.curNode.props.dataRef.edit = false; //将编辑状态改为不可非编辑状态
    //     this.setState({
    //         data: [...this.state.data]
    //     });
    //     return;
    // }
    if (!this.curNode) {
        console.error("当前操作节点不可为空");
        return;
    }
    // console.log("修改节点:", this.curNode, nodeInfo);

    const { nodeEditApiName, nodeEditParamsFormat } = this.props.fetchConfig;
    if (!nodeEditApiName) {
        console.error("修改节点必须配置nodeEditApiName");
        return;
    }
    if (nodeEditParamsFormat) {
        nodeInfo = nodeEditParamsFormat(nodeInfo, this.curNode);
    }
    Toast.loading("请稍等...", 0);
    this.myFetch(nodeEditApiName, nodeInfo).then(
        ({ success, message, data }) => {
            if (success && data) {
                Toast.hide();
                this.curNode.props.dataRef.edit = false; //将编辑状态改为不可非编辑状态
                this.setState({
                    data: [...this.state.data]
                });
            } else {
                Toast.fail(message);
            }
        }
    );
};
export { nodeAdd, nodeDel, nodeEdit, rightMenuOptionClick, postEditNodeInfo };
