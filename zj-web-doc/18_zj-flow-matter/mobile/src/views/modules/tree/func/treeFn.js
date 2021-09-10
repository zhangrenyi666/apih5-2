import React from "react";
import { Tree, Input, message as Msg } from "antd";
import $ from "jquery";
const TreeNode = Tree.TreeNode;

const onLoadData = function(treeNode) {
    return new Promise(resolve => {
        const { children, value } = this.keys;
        const _value = this.parmasKey ? this.parmasKey : value;

        this.refresh(
            { [_value]: treeNode.props.eventKey },
            data => {
                //这步处理将会导致不能同步线上数据 为了测试方便 后期考虑删除
                if (treeNode.props.dataRef[children] && data) {
                    let allData = [];
                    [...treeNode.props.dataRef[children], ...data].map(item => {
                        let foo = true;
                        for (let i = 0; i < allData.length; i++) {
                            if (allData[i][value] === item[value]) {
                                foo = false;
                                continue;
                            }
                        }
                        if (foo) {
                            allData.push(item);
                        }
                        return item;
                    });
                    data = allData;
                }

                treeNode.props.dataRef[children] = data;
                this.setState({
                    data: [...this.state.data]
                });
                resolve();
            },
            () => {
                resolve();
            }
        );
    });
};

//选择
const onCheck = function(checkedKeys, { node }) {
    const { selectModal = "1" } = this.props; //0不可选  1单选  2多选

    this.setState({
        value: node.props.dataRef,
        valueByTree: selectModal === "1" ? [node.props.eventKey] : checkedKeys
    });
    if (this.props.onChange) {
        this.props.onChange(node.props.dataRef);
    }
    if (this.props.change) {
        this.props.change(node.props.dataRef);
    }
};

//点击触发
const onSelect = function(selectedKeys, { node }) {
    if (this.props.nodeClick) {
        this.props.nodeClick(node, selectedKeys);
    }
    node.onExpand();
};

//节点右键
const onRightClick = function({ event, node }) {
    event.stopPropagation();
    let { pageY, pageX } = event;
    const commonDom = this.refs.commonContainer;
    if (commonDom) {
        const { top, left } = $(commonDom).offset();
        pageY -= top;
        pageX -= left;
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
        // console.log(this.props.rightMenuOption)
        //普通节点被点击
        this.setState({
            menuOption: this.props.rightMenuOption,
            onRightClickDomInfo: {
                show: true,
                left: pageX,
                top: pageY
            }
        });

        //将右键的节点赋值到 this.rightClickNode
        this.rightClickNode = node;
    }
};

//鼠标移除右键菜单
const rightClickDomOnMouseLeave = function() {
    setTimeout(() => {
        this.setState({
            onRightClickDomInfo: {
                show: false
            }
        });
    }, 100);
};

//渲染具体节点
const renderTreeNodes = function(data) {
    let { value, children, label } = this.keys;
    const { nodeRender, setNodeProps } = this.props;
    return data.map(item => {
        let { edit = false } = item;
        let name = item[this.keys["label"]];
        let nodeProps = {};
        if (setNodeProps) {
            nodeProps = setNodeProps(item, name, this.keys);
        }
        if (nodeRender && !edit) {
            name = nodeRender(item, name, this.keys);
        } else if (edit) {
            //设置树组件属性
            const inpOver = e => {
                let value = e.target.value;
                item[label] = value;
                //执行新增操作
                this.postEditNodeInfo(item);
            };
            name = (
                <Input
                    style={{ width: 200, height: "25px" }}
                    defaultValue={name}
                    onPressEnter={inpOver}
                    onBlur={inpOver}
                    autoFocus
                />
            );
        }
        if (item[children]) {
            return (
                <TreeNode
                    title={name}
                    key={item[value]}
                    dataRef={item}
                    {...nodeProps}
                >
                    {this.renderTreeNodes(item[children])}
                </TreeNode>
            );
        }
        return (
            <TreeNode
                disableCheckbox
                title={name}
                key={item[value]}
                dataRef={item}
                {...nodeProps}
            />
        );
    });
};

//拖动放入时
const onDrop = function(info) {
    const { value, children } = this.keys;
    const dropKey = info.node.props.eventKey;
    const dragKey = info.dragNode.props.eventKey;
    const dropPos = info.node.props.pos.split("-");
    const dropPosition =
        info.dropPosition - Number(dropPos[dropPos.length - 1]);

    if (!info.node.props.loaded) {
        Msg.error("请停留片刻，让节点加载出来并展开后再放手，请重试！");
        return;
    }

    const loop = (data, key, callback) => {
        data.forEach((item, index, arr) => {
            if (item[value] === key) {
                return callback(item, index, arr);
            }
            if (item[children]) {
                return loop(item[children], key, callback);
            }
        });
    };
    const data = [...this.state.data];
    let dragObj;
    loop(data, dragKey, (item, index, arr) => {
        arr.splice(index, 1);
        dragObj = item;
    });

    if (!info.dropToGap) {
        // Drop on the content
        loop(data, dropKey, item => {
            item[children] = item[children] || [];
            // where to insert 示例添加到尾部，可以是随意位置
            item[children].push(dragObj);
        });
    } else if (
        (info.node.props.children || []).length > 0 && // Has children
        info.node.props.expanded && // Is expanded
        dropPosition === 1 // On the bottom gap
    ) {
        loop(data, dropKey, item => {
            item[children] = item[children] || [];
            // where to insert 示例添加到尾部，可以是随意位置
            item[children].unshift(dragObj);
        });
    } else {
        let ar;
        let i;
        loop(data, dropKey, (item, index, arr) => {
            ar = arr;
            i = index;
        });
        if (dropPosition === -1) {
            ar.splice(i, 0, dragObj);
        } else {
            ar.splice(i + 1, 0, dragObj);
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
                    dragNode:info.dragNode,
                    dragData,
                    targetData,
                    callbackFn: this.callbackFn,
                    allDatas:[...data]
                });
            }
        }
    );
};

export {
    onLoadData,
    onCheck,
    onSelect,
    renderTreeNodes,
    onRightClick,
    rightClickDomOnMouseLeave,
    onDrop
};
