import React from "react";
import { Tree, Input } from "antd";
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
    if(this.props.nodeClick){
        this.props.nodeClick(node, selectedKeys)
    }
    node.onExpand(); 
};

//节点右键
const onRightClick = function({ event, node }) {
    //设置鼠标信息
    const { pageY, pageX } = event;
    this.setState({ 
        onRightClickDomInfo: {
            show: true,
            left: pageX,
            top: pageY
        }
    });

    //将右键的节点赋值到 this.rightClickNode
    this.rightClickNode = node;
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

export {
    onLoadData,
    onCheck,
    onSelect,
    renderTreeNodes,
    onRightClick,
    rightClickDomOnMouseLeave
};
