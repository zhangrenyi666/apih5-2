
import {
    message as Msg
} from "antd";
import $ from "jquery";


//节点右键
const onRightClick = function ({ event,node }) {
    let { pageY,pageX } = event;
    const commonDom = this.refs.commonContainer;
    if (commonDom) {
        const { top,left } = $(commonDom).offset(); 
        pageY -= top;
        pageX -= left;

        if (event.pageY + $(this.rightClickDom).height() + 50 >= window.innerHeight) { 
            pageY -= $(this.rightClickDom).height();
        }
    }

    //设置鼠标信息  

    if (!node) {
        //根节点被点击
        this.setState({
            menuOption: this.props.rightMenuOptionByContainer,
            onRightClickDomInfo: {
                show: true,
                left: pageX,
                top: pageY
            }
        });
        this.rightClickNode = "root";
    } else {
        //普通节点被点击
        let _rightMenuOption = this.props.rightMenuOption;

        if ((typeof _rightMenuOption) === 'function') {
            _rightMenuOption = _rightMenuOption(node,this.props)
        }
        this.setState({
            menuOption: _rightMenuOption,
            onRightClickDomInfo: {
                show: true,
                left: pageX,
                top: pageY
            }
        });
        //将右键的节点赋值到 this.rightClickNode
        this.rightClickNode = node;
    }
    if (this.props.nodeRightClick) {
        this.props.nodeRightClick({
            ...this.rightClickNode,
            props: { node: { dataRef: this.rightClickNode } }
        },this.props);
    }
};
//鼠标移除右键菜单
const rightClickDomOnMouseLeave = function () {
    setTimeout(() => {
        this.setState({
            onRightClickDomInfo: {
                show: false
            }
        });
    },100);
};

//拖动放入时
const onDrop = function (info) {
    const { value,children } = this.keys;
    const dropKey = info.node.props.eventKey;
    const dragKey = info.dragNode.props.eventKey;
    const dropPos = info.node.props.pos.split("-");
    const dropPosition =
        info.dropPosition - Number(dropPos[dropPos.length - 1]);

    if (!info.node.props.loaded) {
        Msg.error("请停留片刻，让节点加载出来并展开后再放手，请重试！");
        return;
    }

    const loop = (data,key,callback) => {
        data.forEach((item,index,arr) => {
            if (item[value] === key) {
                return callback(item,index,arr);
            }
            if (item[children]) {
                return loop(item[children],key,callback);
            }
        });
    };
    const data = [...this.state.data];
    let dragObj;
    loop(data,dragKey,(item,index,arr) => {
        arr.splice(index,1);
        dragObj = item;
    });

    if (!info.dropToGap) {
        // Drop on the content
        loop(data,dropKey,item => {
            item[children] = item[children] || [];
            // where to insert 示例添加到尾部，可以是随意位置
            item[children].push(dragObj);
        });
    } else if (
        (info.node.props.children || []).length > 0 && // Has children
        info.node.props.expanded && // Is expanded
        dropPosition === 1 // On the bottom gap
    ) {
        loop(data,dropKey,item => {
            item[children] = item[children] || [];
            // where to insert 示例添加到尾部，可以是随意位置
            item[children].unshift(dragObj);
        });
    } else {
        let ar;
        let i;
        loop(data,dropKey,(item,index,arr) => {
            ar = arr;
            i = index;
        });
        if (dropPosition === -1) {
            ar.splice(i,0,dragObj);
        } else {
            ar.splice(i + 1,0,dragObj);
        }
    }
    this.setState(
        {
            data
        },
        () => {
            //被拖动节点
            const dragData = info.dragNode.props.dataRef;
            //目标节点
            const targetData = info.node.props.dataRef;
            if (this.props.onDrop) {
                this.props.onDrop({
                    targetNode: info.node,
                    dragNode: info.dragNode,
                    dragData,
                    targetData,
                    callbackFn: this.callbackFn,
                    allDatas: [...data]
                });
            }
        }
    );
};

//加载数据
const onLoadData = function (treeNodeData) {
    return new Promise(resolve => {
        const { value } = this.keys;
        const _value = this.parmasKey ? this.parmasKey : value;
        const params = {};
        let { treeData = [] } = this.state;
        if (treeNodeData) {
            params[_value] = treeNodeData[value];
        }
        this.refresh(params,
            newData => {
                let newTreeData = [...newData]

                if (treeNodeData) {
                    //如果传入了treeNodeData那需要将请求来的数据设置到他的子集中去

                    // console.log("插入请求来的节点进入父节点开始...")
                    this.insertChildrenNode(treeNodeData[value],newTreeData,(treeData) => {
                        if (this.props.fetchConfig.success) {
                            this.props.fetchConfig.success(newData,treeNodeData,treeData)
                        }
                        // console.log("插入请求来的节点进入父节点结束...")
                        resolve({
                            newTreeData: newTreeData,
                            treeData
                        });
                    });
                } else {
                    //首次请求
                    //直接给树结构数据即可
                    treeData = [...newTreeData];
                    this.setTreeData([...treeData]);
                    if (this.props.fetchConfig.success) {
                        this.props.fetchConfig.success(newData,treeNodeData,treeData)
                    }
                    resolve({
                        newTreeData: newTreeData,
                        treeData
                    });
                }
            },
            () => {
                resolve();
            }
        );
    });
};



//点击触发
const onSelect = function (nodeData) {
    const { children,value } = this.keys;
    const { selectedKeys,nodeByLoading = [] } = this.state;

    //设置当前选中的的节点（也就是点击的节点）
    this.setState({ curClickNodeData: nodeData.node })

    if (nodeData.node[children] && nodeData.node.children) {
        //不需要去请求子节点数据了，直接收缩或者展开节点即可
        this.setNodeExpand([nodeData.node[value]],nodeData.node.expanded ? 'close' : 'expand');
    } else {
        //需要去请求节点数据
        nodeByLoading.push(nodeData.node[value]);
        this.setState({
            nodeByLoading
        })
        this.onLoadData(nodeData.node).then(data => {
            //移除正在loading的节点 
            this.setState({
                nodeByLoading: this.state.nodeByLoading.filter(item => item !== nodeData.node[value])
            })
        });
    }

    if (this.props.nodeClick) {
        this.props.nodeClick({ props: { dataRef: { ...nodeData.node } } },selectedKeys);
    }
};

//展开或者关闭某些节点
//@nodeId<Array> | <string>  
//@action<string>[expand | close] 
const setNodeExpand = function (nodeIds = [],action = 'expand',_treeData) {
    return new Promise((resolve) => {
        let { treeData = [],nodeByLoading = [] } = this.state;
        let { value,children } = this.keys;

        treeData = _treeData || treeData;
        // console.log("treeData：",treeData)

        if (typeof nodeIds === 'string') {
            nodeIds = [nodeIds];
        }
        //用于判断被展开节点是否需要先请求子节点
        let isNeedGetChildren = [];

        const loopFn = (treeData) => {
            treeData = treeData.map(item => {
                if (nodeIds.includes(item[value])) {
                    item.expanded = action === 'expand';
                    if (!item[children]) {
                        isNeedGetChildren.push(item);
                    }
                }
                if (item[children] && item[children].length) {
                    item[children] = loopFn(item[children]);
                }
                return item;
            });

            return treeData;
        }
        treeData = loopFn(treeData);

        if (isNeedGetChildren.length) {
            for (let i = 0; i < isNeedGetChildren.length; i++) {

                //正常请求的节点直接跳过
                if (nodeByLoading.includes(isNeedGetChildren[i][value])) {
                    continue;
                }

                nodeByLoading.push(isNeedGetChildren[i][value]);

                // console.log("加载父节点数据开始...")
                this.onLoadData(isNeedGetChildren[i]).then(() => {
                    //移除正在loading的节点 
                    this.setState({
                        nodeByLoading: this.state.nodeByLoading.filter(item => item !== isNeedGetChildren[i][value])
                    },() => {
                        // console.log("加载父节点数据结束...")
                        // this.setTreeData(treeData); 
                        //展开多个还未加载子节点的节点时候第一个请求完成后就resolve
                        if (i === (isNeedGetChildren.length - 1)) {
                            resolve();
                        }
                    });
                });
            }
            this.setState({
                nodeByLoading
            })

        } else {
            // console.log("无需加载节点数据")
            this.setTreeData(treeData);
            resolve();
        }
    });
}

//在某个节点下插入子节点
//@nodeId <string> 目标节点
//@childrenNodes <array> 要插入的子节点
const insertChildrenNode = function (nodeId,childrenNodes,cb) {
    let { treeData = [] } = this.state;
    let { value,children } = this.keys;
    // console.log('插入节点参数:',nodeId,childrenNodes)
    const loopFn = (treeData) => {
        treeData = treeData.map(item => {
            if (item[value] === nodeId) {
                if (!item[children]) { item[children] = [] };
                item[children] = [...childrenNodes,...item[children]];
                item.expanded = true;
            } else if (item[children] && item[children].length) {
                item[children] = loopFn(item[children]);
            }
            return item;
        });

        return treeData;
    }
    if (nodeId === "root") {
        treeData.unshift(childrenNodes[0])
    } else {
        treeData = loopFn(treeData);
    }

    this.setTreeData(treeData,() => {
        if (cb) {
            cb(this.state.treeData);
        }
    });
}

//删除某个节点 
//@nodeId <string> 目标节点id
const delNodeData = function (nodeId) {
    let { treeData = [] } = this.state;
    let { value,children } = this.keys;

    const loopFn = (treeData) => {
        return treeData.filter(item => {
            if (item[children] && item[children].length) {
                item.children = item[children] = loopFn(item[children]);
            }
            return item[value] !== nodeId;
        });
    }
    treeData = loopFn(treeData);

    //删除节点后需要清除掉点击的节点
    this.setState({ curClickNodeData: null });
    this.setTreeData(treeData,() => {
        //这块需要在设置set下不然不会重新渲染 
        this.setState({
            treeData
        })
    });
}

//更新某个节点数据
//@nodeId <string> 目标节点
//@newNodeData <object> 新节点数据
const updateNode = function (nodeId,newNodeData) {
    let { treeData = [] } = this.state;
    let { value,children } = this.keys;
    const loopFn = (treeData) => {
        treeData = treeData.map(item => {
            if (item[value] === nodeId) {
                //后台可能不会返回子节点数据 所以在这备份一下
                let childrenDataBackUp = item[children] || newNodeData[children];
                item = { ...newNodeData };
                item.children = childrenDataBackUp;
            } else if (item[children] && item[children].length) {
                item[children] = loopFn(item[children]);
            }

            return item;
        });
        return treeData;
    }

    treeData = loopFn(treeData);
    this.setTreeData(treeData,() => {
        this.setState({ treeData })
    });
}

//根据节点id获取某个节点
const getNodeDataById = function (nodeId,_treeData) {
    let { treeData = [] } = this.state;
    let { value,children } = this.keys;
    let node = null;
    const loopFn = (treeData) => {
        treeData = treeData.map(item => {
            if (item[value] === nodeId) {
                node = item;
            } else if (item[children] && item[children].length) {
                item[children] = loopFn(item[children]);
            }

            return item;
        });
        return treeData;
    }

    loopFn((_treeData ? _treeData : treeData));

    return node;
}


export {
    onLoadData,
    onSelect,
    onRightClick,
    rightClickDomOnMouseLeave,
    onDrop,

    setNodeExpand,
    insertChildrenNode,
    delNodeData,
    updateNode,
    getNodeDataById
};
