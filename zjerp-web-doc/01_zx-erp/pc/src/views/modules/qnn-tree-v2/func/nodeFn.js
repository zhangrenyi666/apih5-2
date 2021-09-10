//节点增删改等信息操作
import { Toast } from "antd-mobile";
import { message as Msg, Modal } from "antd"
const confirm = Modal.confirm;
const nodeAdd = function (name, cb) {

    const { label, value } = this.keys;

    let parentNode = this.rightClickNode;

    let id = `newNode_${window.parseInt(Math.random() * 1000)}`

    //关闭右菜单
    this.setState({ onRightClickDomInfo: { show: false } });

    //将数据提交给后台的方法
    const postEndbackFn = (body) => {
        return new Promise((resolve) => {

            let _body = { ...body };
            const { nodeAddApiName, nodeAddParamsFormat, nodeAddCb } = this.props.fetchConfig;
            if (!nodeAddApiName) {
                console.error("新增节点必须配置nodeAddApiName");
                return;
            }

            //参数格式化
            if (nodeAddParamsFormat) {
                _body = nodeAddParamsFormat(_body, this.createNodeData(parentNode), this.props);
            }
            if(!_body.levelName){
                Msg.error('节点名称必填!');
                return;
            }
            //请求开始
            Toast.loading("请稍等...", 0);
            this.myFetch(nodeAddApiName, _body).then(({ success, message, data }) => {
                if (nodeAddCb) {
                    nodeAddCb({ success, message, data }, this.props)
                }
                if (success && data) {
                    Toast.hide();

                    this.setState({ editValue: "" })

                    //同步后台返回的最新数据 
                    this.updateNode(id, data);
                } else {
                    Toast.hide();
                    Toast.fail(message);

                    //清空正在编辑的节点并且删除
                    this.setState({ editValue: null });
                    this.delNodeData(id);

                }

                //这是将新增节点的数据提交到后台的回调
                resolve({ success, message, data });

                //这个回调是调用新增节点的回调
                if (cb) {
                    cb({ success, message, data });
                }
            });

        });
    }

    //向树节点中插入数据的方法
    const addFn = () => {
        let newNode = {
            [label]: "未命名",
            [value]: id,
            placeholder: "请输入...",
            isAdd: true, //编辑完会请求新增接口
            postEndbackFn
        };
        this.setState({ editValue: id }, () => {
            setTimeout(() => this.insertChildrenNode((parentNode[value] || parentNode), [newNode]), 1)
        })
    }


    if (name === 'root') {
        //新增根节点   
        addFn();
    } else {
        //新增子节点 
        if (parentNode.expanded) {
            addFn();
        } else {
            //先展开节点
            this.setNodeExpand(parentNode[value]).then(() => {
                addFn()
            });
        }
    }
}

//删除节点具体方法
const nodeDel = function (cb) {
    const { value, children } = this.keys;
    //nodeInfo是个数组
    let nodeInfo = { ...this.rightClickNode };
    delete nodeInfo.title;
    //不能提交titile和节点里面包含的方法 ，因为提交给 后台的 数据需要解析为字符串，解析时候回报错  
    let loopFnDelAttr = (nodeInfo) => {
        delete nodeInfo.title;
        if (nodeInfo[children]) {
            nodeInfo[children] = nodeInfo[children].map(item => {
                return loopFnDelAttr(item);
            });
            nodeInfo.children = nodeInfo[children];
        }
        return nodeInfo;
    }
    loopFnDelAttr(nodeInfo);
    // let nodeInfo = this.rightClickNode.props.dataRef;
    // let delDom = this.rightClickNode.selectHandle.parentNode;
    const { nodeDelCheckApiName, nodeDelApiName, nodeDelParamsFormat, nodeDelCb } = this.props.fetchConfig;

    if (!nodeDelApiName) {
        console.error("删除节点必须配置nodeDelApiName");
        return;
    }
    if (nodeDelParamsFormat) {
        nodeInfo = nodeDelParamsFormat(nodeInfo, this.createNodeData(this.rightClickNode));
    }

    Toast.loading("请稍等...", 0);
    this.myFetch(nodeDelCheckApiName, nodeInfo).then(
        ({ success, message, data }) => {
            if (success) {
                Toast.hide();
                if (data?.flag === '0') {
                    Toast.loading("请稍等...", 0);
                    delete nodeInfo?.children;
                    this.myFetch(nodeDelApiName, nodeInfo).then(
                        ({ success, message, data }) => {
                            if (nodeDelCb) {
                                nodeDelCb({ success, message, data }, this.props, this.rightClickNode)
                            }
                            if (success) {
                                Toast.hide();
                                this.delNodeData(this.rightClickNode[value]);
                                this.rightClickNode = null;
                                Msg.success(message);
                                if (cb) {
                                    cb(data);
                                }
                            } else {
                                Toast.hide();
                                Toast.fail(message);
                            }
                        }
                    );
                }else if(data?.flag === '1'){
                    confirm({
                        content: '是否同时删除该资料夹下的所有数据?',
                        onOk: () => {
                            Toast.loading("请稍等...", 0);
                            delete nodeInfo?.children;
                            nodeInfo = JSON.parse(JSON.stringify(nodeInfo))
                            this.myFetch(nodeDelApiName, nodeInfo).then(
                                ({ success, message, data }) => {
                                    if (nodeDelCb) {
                                        nodeDelCb({ success, message, data }, this.props, this.rightClickNode)
                                    }
                                    if (success) {
                                        Toast.hide();
                                        this.delNodeData(this.rightClickNode[value]);
                                        this.rightClickNode = null;
                                        Msg.success(message);
                                        if (cb) {
                                            cb(data);
                                        }
                                    } else {
                                        Toast.hide();
                                        Toast.fail(message);
                                    }
                                }
                            );
                        }
                    });
                }
            } else {
                Toast.hide();
                Msg.error(message);
            }
        }
    );
};



const rightMenuOptionClick = function (obj) {
    const { name, cb } = obj;
    switch (name) {
        case "add":
            this.nodeAdd(cb);
            break;
        case "addRoot":
            this.nodeAdd("root", cb);
            break;
        case "del":
            this.nodeDel(cb);
            break;
        case "edit":
            this.nodeEdit(cb);
            break;
        default:
            if (cb) {
                cb(this.createNodeData(this.rightClickNode), obj);
            }
    }
};


//将节点变为编辑
const nodeEdit = function () {
    const { value, label } = this.keys;
    this.curNode = this.createNodeData(this.rightClickNode);
    this.inputValue = this.curNode[label] || '';
    // this.rightClickNode.props.dataRef.edit = true;
    this.setState({
        editValue: this.curNode[value],
        onRightClickDomInfo: {
            show: false
        }
    });
};

//编辑节点后执行请求
const postEditNodeInfo = function (nodeInfo) {
    const { value, children } = this.keys;
    delete nodeInfo.title;
    //不能提交titile和节点里面包含的方法 ，因为提交给 后台的 数据需要解析为字符串，解析时候回报错  
    let loopFnDelAttr = (nodeInfo) => {
        delete nodeInfo.title;
        if (nodeInfo[children]) {
            nodeInfo[children] = nodeInfo[children].map(item => {
                return loopFnDelAttr(item);
            });
            nodeInfo.children = nodeInfo[children];
        }
        return nodeInfo;
    }
    loopFnDelAttr(nodeInfo);

    if (!this.curNode) {
        console.error("当前操作节点不可为空");
        return;
    }

    const { nodeEditApiName, nodeEditParamsFormat, nodeEditCb } = this.props.fetchConfig;
    if (!nodeEditApiName) {
        console.error("修改节点必须配置nodeEditApiName");
        return;
    }
    if (nodeEditParamsFormat) {
        nodeInfo = nodeEditParamsFormat(nodeInfo, this.curNode);
    }
    if(!nodeInfo.levelName){
        Msg.error('节点名称必填!');
        return;
    }
    Toast.loading("请稍等...", 0);
    this.myFetch(nodeEditApiName, nodeInfo).then(
        ({ success, message, data }) => {
            if (nodeEditCb) {
                nodeEditCb({ success, message, data }, this.props)
            }
            if (success && data) {
                Toast.hide();
                this.setState({
                    editValue: null
                });
                this.updateNode(nodeInfo[value], { ...data });
            } else {
                Toast.hide();
                Toast.fail(message);
                this.setState({
                    editValue: null
                });
            }
        }
    );
};

export { nodeAdd, nodeDel, nodeEdit, rightMenuOptionClick, postEditNodeInfo };
