import React, { Component } from "react";
import {
    refresh,
    getDeviceType,
    onLoadData,
    onSelect,
    clearValue,
    getValue,
    setValue,
    onRightClick,
    rightClickDomOnMouseLeave,
    nodeAdd,
    nodeDel,
    nodeEdit,
    rightMenuOptionClick,
    postEditNodeInfo,
    onDrop,
    generateNodeProps,
    setNodeExpand,

    insertChildrenNode,
    delNodeData,
    setTreeData,
    updateNode,
    getNodeDataById
} from "./func";
import {
    // Button,
    Popconfirm, Spin,
    message as Msg
} from "antd";
import { Toast } from "antd-mobile";
import PropTypes from "prop-types";
import s from "./style.less";
import 'react-sortable-tree/style.css';
import SortableTree from 'react-sortable-tree';
import FileExplorerTheme from 'react-sortable-tree-theme-full-node-drag';
class index extends Component {
    static getDerivedStateFromProps(props, state) {
        let obj = {
            ...state,
            ...props
        };
        return obj;
    }
    constructor(...args) {
        super(...args);
        const props = this.props;
        const { fetchConfig = {} } = props
        const { apiName, params = {}, parmasKey } = fetchConfig;
        let _onRightClickDomInfo = {
            //右键节点的信息
            show: false,
            left: 0,
            top: 0
        };
        this.state = {
            params,
            valueByTree: props.valueByTree || [], //树结构的value
            value: props.value || null, //最终取的节点信息
            onRightClickDomInfo: _onRightClickDomInfo,
            rightMenuOption: props.rightMenuOption,
            rightMenuOptionByContainer: props.rightMenuOptionByContainer,
            menuOption: [], //实际的右键菜单选项 
            selectText:
                props.selectText === false ? null : props.selectText || "选择",


            treeData: props.data || props.treeData || [], //树数据 

            data: props.data || props.treeData || [], //树数据(为兼容以前的并无实际用处)

            //处理loading状态的树节点
            nodeByLoading: [],

            //整个树是否处理loading状态
            loading: true,

            curClickNodeData: null,

            //当前编辑的节点值
            editValue: null

        };

        this._mounted = false;
        this.qnnSetState = (...args) => {
            this._mounted && this.setState(...args)
        }

        this.myFetch = props.myFetch;
        this.refresh = refresh.bind(this);
        this.onLoadData = onLoadData.bind(this);
        this.onSelect = onSelect.bind(this);
        this.clearValue = clearValue.bind(this);
        // this.renderTreeNodes = renderTreeNodes.bind(this);
        this.getValue = getValue.bind(this);
        this.setValue = setValue.bind(this);
        this.nodeAdd = nodeAdd.bind(this);
        this.nodeDel = nodeDel.bind(this);
        this.nodeEdit = nodeEdit.bind(this);
        this.rightMenuOptionClick = rightMenuOptionClick.bind(this);
        this.postEditNodeInfo = postEditNodeInfo.bind(this);
        this.onRightClick = onRightClick.bind(this);
        this.rightClickDomOnMouseLeave = rightClickDomOnMouseLeave.bind(this);
        this.onDrop = onDrop.bind(this);
        this.generateNodeProps = generateNodeProps.bind(this);
        this.setNodeExpand = setNodeExpand.bind(this);
        this.insertChildrenNode = insertChildrenNode.bind(this);
        this.delNodeData = delNodeData.bind(this);
        this.setTreeData = setTreeData.bind(this);
        this.getTreeData = () => this.state.treeData;
        this.updateNode = updateNode.bind(this);
        this.getNodeDataById = getNodeDataById.bind(this);

        //获取当前点击的节点
        this.getCurClickNodeData = () => this.state.curClickNodeData;

        this.isMobiele = getDeviceType() === "mobile";
        this.keys = {
            label: "label",
            value: "value",
            children: "children",
            ...props.keys
        };
        //以前用的antd的 处理兼容
        this.createNodeData = (nodeData) => {
            const { value } = this.keys;
            if (nodeData === 'root') { return "root" };
            delete nodeData.title;
            const newNode = {
                ...nodeData,
                props: {
                    expanded: nodeData.expanded,
                    dataRef: { ...nodeData }
                },

                //兼容初始版本锁设置
                //isLoadChilren<boolean> 默认false 
                onExpand: () => {
                    this.setNodeExpand([nodeData[value]]);
                },
                insertChildrenNode: (...args) => {
                    this.insertChildrenNode(...args);
                },


            }
            return newNode;
        }

        this.apiName = apiName;
        this.params = params;
        this.parmasKey = parmasKey;
        this.curNode = null; //当前操作的对象 改名等操作会用到
        this.rightClickNode = null; //当前右键的节点

        this.callbackFn = {
            Toast, //antd-mobile  Toast(消息提示组件)
            props, //页面props
            onLoadData: this.onLoadData //加载节点数据，需要传入node数据
        };
    }
    componentDidMount() {
        this._mounted = true;
        const { treeData = [] } = this.state;
        //默认数据存在将不会请求数据
        if (!treeData.length) {
            this.onLoadData().then(() => {
                this.setState({ loading: false })
            });
        } else {
            this.setState({ loading: false })
        }
    }

    componentWillUnmount() {
        this._mounted = false
    }

    render() {
        const {
            onRightClickDomInfo: { show, left, top },
            menuOption = [],
            treeData,
            loading,
        } = this.state;
        const {
            // btnShow = true,
            // draggable,
            onDragStart,
            noDataText = "暂无数据，右键新增一个吧！"
        } = this.props; //0不可选  1单选  2多选 


        //右键弹出tip
        const onRightClickDom = (
            <div
                onMouseLeave={this.rightClickDomOnMouseLeave}
                className={s.onRightClickDom}
                style={{
                    display:
                        menuOption && menuOption.length && show
                            ? "block"
                            : "none",
                    left: left,
                    top: top
                }}
                ref={(me) => {
                    if (me) { this.rightClickDom = me }
                }}
            >
                <ul>
                    {menuOption &&
                        menuOption.map((item, index) => {
                            let { label, name } = item;
                            if (name === "del") {
                                return (
                                    <Popconfirm
                                        placement="topLeft"
                                        title={"确认删除吗？"}
                                        key={index}
                                        onConfirm={this.rightMenuOptionClick.bind(
                                            this,
                                            item
                                        )}
                                        okText="确认"
                                        cancelText="取消"
                                    >
                                        <li>{label}</li>
                                    </Popconfirm>
                                );
                            }
                            return (
                                <li
                                    key={index}
                                    onClick={this.rightMenuOptionClick.bind(
                                        this,
                                        item
                                    )}
                                >
                                    {label}
                                </li>
                            );
                        })}
                </ul>
            </div>
        );

        //具体数dom 
        const treeDom = (
            <div onContextMenu={(e) => e.preventDefault()} className={s.SortableTreeContainer}>
                {treeData && treeData.length ? (
                    <SortableTree
                        treeData={treeData}
                        onChange={treeData => {
                            this.setTreeData(treeData);
                        }}
                        theme={FileExplorerTheme}
                        generateNodeProps={this.generateNodeProps}
                        getNodeKey={({ node }) => {
                            return node[this.keys.value];
                        }}
                        rowDirection={"ltr"}
                        rowHeight={32}
                        scaffoldBlockPxWidth={24}
                        slideRegionSize={50}
                        //这个组件暂时配置这个属性将不支持react17
                        // isVirtualized={true}
                        isVirtualized={false}

                        onMoveNode={(e) => {
                            this.setState({ isDragging: false })
                            if (this.props.onDrop) {
                                let { nextParentNode, treeData } = e;
                                let _nextParentNodeChildren = [];
                                let _backupTreeData = Array.isArray(this.backUpTreeData) ? [...this.backUpTreeData] : [{ ...this.backUpTreeData }];
                                if (nextParentNode) {
                                    const oldParentData = this.getNodeDataById(nextParentNode[this.keys.value], _backupTreeData);
                                    //子节点未展开情况需要先展开节点 
                                    //这块判断必须判断子集否则会存在节点拖动到该节点下后expanded属性就是存在的
                                    if (oldParentData.expanded && oldParentData.children) {
                                        _nextParentNodeChildren = nextParentNode[this.keys.children];
                                    } else {
                                        Msg.error("请先点击展开目标节点！");
                                        this.setTreeData(_backupTreeData);
                                        return;
                                    }
                                } else {
                                    _nextParentNodeChildren = treeData;
                                }

                                //是否可被拖动到目标地点
                                if (this.props.isCanDropTarget) {
                                    let isCanDropTarget = this.props.isCanDropTarget({
                                        callbackFn: this.callbackFn,
                                        restoreTreeData: () => this.setTreeData(_backupTreeData)
                                    });
                                    if (!isCanDropTarget === false) {
                                        this.setTreeData(_backupTreeData);
                                        return;
                                    }
                                }

                                //得找出相邻的两个兄弟节点
                                let beforeNode = {};
                                let afterNode = {};
                                let loopTree = (data) => {
                                    for (let i = 0; i < data.length; i++) {
                                        let item = data[i];
                                        if (item[this.keys.value] === e.node[this.keys.value]) {
                                            //查找上兄弟节点 
                                            if (i > 0) {
                                                beforeNode = data[i - 1];
                                            }
                                            //查找出下一个兄弟节点
                                            if (i < (data.length - 1)) {
                                                afterNode = data[i + 1];
                                            }
                                        }
                                        if (item.children && item.children.length) {
                                            loopTree(item.children)
                                        }
                                    }
                                }
                                loopTree(_nextParentNodeChildren);

                                //执行拖拽后的回调
                                this.props.onDrop({
                                    dragNode: this.createNodeData(e.node),
                                    dragData: e.node,
                                    callbackFn: this.callbackFn,
                                    allDatas: [...treeData],
                                    beforeNodeData: beforeNode,
                                    afterNodeData: afterNode,
                                    //移动后的节点的后一个兄弟节点
                                    afterNode: afterNode[this.keys.value],
                                    //移动后的节点的前一个兄弟节点
                                    beforeNode: beforeNode[this.keys.value],
                                    //移动后的节点的父节点
                                    parentNode: nextParentNode,
                                    //移动的节点
                                    targetNode: e.node[this.keys.value],
                                    //调用该方法可以直接还原数据
                                    restoreTreeData: () => this.setTreeData(_backupTreeData),
                                    ...e,
                                });
                            }

                            this.backUpTreeData = null;

                            //防止不刷新节点 
                            this.setTreeData(treeData);
                        }}

                        onDragStateChanged={(e) => {
                            if (e.isDragging && !this.backUpTreeData) {
                                this.backUpTreeData = treeData;
                                if (onDragStart) {
                                    onDragStart({
                                        event: e,
                                        node: this.createNodeData(e.draggedNode),
                                        callbackFn: this.callbackFn
                                    });
                                }
                            }
                        }}

                        canDrag={(obj) => {
                            if(obj.node.parentId === '0' || obj.node.permissionType !== '1'){
                                return false;
                            }
                            return true;
                        }}

                    />

                ) : (
                        <div className={s.noData}>{loading ? null : noDataText}</div>
                    )}
            </div>
        );

        const createTreeDomFn = (
            <div className={"TreeDom"}>
                {onRightClickDom}
                <div onContextMenu={(e) => e.preventDefault()} className={s.con} >{treeDom}</div>
            </div>
        );


        return (
            <div
                className={s.treeContainer}
                //阻止右键菜单
                onContextMenu={e => {
                    e.preventDefault();
                    //右键是根节右键
                    this.onRightClick({ event: e, node: null });
                }}
            >
                <Spin spinning={loading} tip="loading..." style={{ paddingTop: "50px", width: "100%" }}></Spin>
                <div
                    ref="commonContainer"
                    className={s.commonContainer}
                    onContextMenu={(e) => e.preventDefault()}
                >
                    {createTreeDomFn}
                </div>
            </div>
        );
    }
}

index.propTypes = {
    myFetch: PropTypes.func,
    nodeClick: PropTypes.func,
    fetchConfig: PropTypes.object,
    keys: PropTypes.object,
    search: PropTypes.bool,
    valueRender: PropTypes.func,
    nodeRender: PropTypes.func,
    rightMenuOption: PropTypes.any,
    rightMenuOptionByContainer: PropTypes.array,
    draggable: PropTypes.bool,
    isCanDropTarget: PropTypes.func,
};
index.defaultProps = {
    modalType: "drawer", //common  drawer
    rightMenuOption: [
        {
            label: "新增子节点",
            name: "add",
            cb: newNodeInfo => {
                console.log(newNodeInfo);
            }
        },
        {
            label: "删除节点",
            name: "del",
            cb: deelNodeInfo => {
                console.log(deelNodeInfo);
            }
        },
        {
            label: "修改节点",
            name: "edit",
            cb: newNodeInfo => {
                console.log(newNodeInfo);
            }
        }
    ]
};

export default index;
