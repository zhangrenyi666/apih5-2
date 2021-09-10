import React, { Component } from "react";
import {
    refresh,
    getDeviceType,
    showDrawer,
    onClose,
    onLoadData,
    onCheck,
    onSelect,
    onCloseBySelectTip,
    renderTreeNodes,
    clearValue,
    getValue,
    setValue,
    yes,
    onRightClick,
    rightClickDomOnMouseLeave,
    nodeAdd,
    nodeDel,
    nodeEdit,
    rightMenuOptionClick,
    postEditNodeInfo,
    cancel,
    onDrop
} from "./func";
import { Drawer, Button, Tree, Popconfirm } from "antd";
import { Toast } from "antd-mobile";
import PropTypes from "prop-types";
import s from "./style.less";

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
        const { apiName, params = {}, parmasKey } = props.fetchConfig;
        let _onRightClickDomInfo = {
            //右键节点的信息
            show: false,
            left: 0,
            top: 0
        };
        this.state = {
            params,
            visible: props.visible || false,
            disabled: props.disabled || false,
            visibleBySelectTip: false,
            data: props.data || [],
            valueByTree: props.valueByTree || [], //树结构的value
            value: props.value || null, //最终取的节点信息
            onRightClickDomInfo: _onRightClickDomInfo,
            rightMenuOption: props.rightMenuOption,
            rightMenuOptionByContainer: props.rightMenuOptionByContainer,
            menuOption: [], //实际的右键菜单选项
            modalType: props.modalType,
            selectText:
                props.selectText === false ? null : props.selectText || "选择"
        };

        this.myFetch = props.myFetch;
        this.refresh = refresh.bind(this);
        this.showDrawer = showDrawer.bind(this);
        this.onClose = onClose.bind(this);
        this.onLoadData = onLoadData.bind(this);
        this.onCheck = onCheck.bind(this);
        this.onSelect = onSelect.bind(this);
        this.onCloseBySelectTip = onCloseBySelectTip.bind(this);
        this.clearValue = clearValue.bind(this);
        this.cancel = cancel.bind(this);
        this.renderTreeNodes = renderTreeNodes.bind(this);
        this.getValue = getValue.bind(this);
        this.setValue = setValue.bind(this);
        this.yes = yes.bind(this);
        this.nodeAdd = nodeAdd.bind(this);
        this.nodeDel = nodeDel.bind(this);
        this.nodeEdit = nodeEdit.bind(this);
        this.rightMenuOptionClick = rightMenuOptionClick.bind(this);
        this.postEditNodeInfo = postEditNodeInfo.bind(this);
        this.onRightClick = onRightClick.bind(this);
        this.rightClickDomOnMouseLeave = rightClickDomOnMouseLeave.bind(this);
        this.onDrop = onDrop.bind(this);

        this.isMobiele = getDeviceType() === "mobile";
        this.keys = {
            label: "label",
            value: "value",
            children: "children",
            ...props.keys
        };
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
        const { data = [] } = this.state;
        //默认数据存在将不会请求数据
        if (!data.length) {
            Toast.loading("请稍等...", 0);
            this.refresh(this.params, data => {
                this.setState({ data });
                Toast.hide();
            });
            setTimeout(() => {
                Toast.hide();
            }, 5000);
        }
    }

    render() {
        const {
            visible,
            valueByTree = [],
            value,
            data,
            visibleBySelectTip,
            onRightClickDomInfo: { show, left, top },
            menuOption = [],
            modalType,
            selectText
        } = this.state;
        const {
            selectModal = "1",
            valueRender,
            btnShow = true,
            drawerConfig = {},
            disabled,
            draggable,
            onDragStart,
            onDragLeave,
            treeProps = {}
        } = this.props; //0不可选  1单选  2多选
        // 切换选择时的提醒   ----暂未完成
        const selectTip = (
            <Drawer
                placement={"top"}
                width={"50%"}
                height={35}
                closable={false}
                mask={false}
                onClose={this.onCloseBySelectTip}
                visible={visibleBySelectTip}
                className={s.myDrawerBySelectTip}
            >
                <div className={s.selectedValue}>已选择{1111}</div>
            </Drawer>
        );
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
            <div style={{ height: "100%" }}>
                {data && data.length ? (
                    <Tree
                        checkStrictly
                        onCheck={this.onCheck}
                        checkedKeys={valueByTree}
                        checkable={selectModal !== "0" ? true : false}
                        loadData={this.onLoadData}
                        onSelect={this.onSelect}
                        defaultExpandedKeys={valueByTree}
                        defaultSelectedKeys={valueByTree}
                        defaultCheckedKeys={valueByTree}
                        onRightClick={this.onRightClick}
                        draggable={draggable}
                        onDragStart={({ event, node }) => {
                            //拖动开始
                            if (onDragStart) {
                                onDragStart({
                                    event,
                                    node,
                                    callbackFn: this.callbackFn
                                });
                            }
                        }}
                        onDragLeave={({ event, node }) => {
                            //拖动中...
                            if (onDragLeave) {
                                onDragLeave({
                                    event,
                                    node,
                                    callbackFn: this.callbackFn
                                });
                            }
                        }}
                        // onDragEnter={()=>{
                        //     console.log("进入可放区域")
                        // }}
                        // onDragOver={()=>{
                        //     console.log("离开可放区域")
                        // }}
                        onDrop={this.onDrop}
                        blockNode={true}
                        {...treeProps}
                    >
                        {this.renderTreeNodes(data)}
                    </Tree>
                ) : (
                    <div className={s.noData}>暂无数据，右键新增一个吧！</div>
                )}
            </div>
        );

        const createTreeDomFn = (
            <div className={"TreeDom"}>
                {onRightClickDom}
                <div className={s.con}>{treeDom}</div>
                {btnShow === false ? null : (
                    <div className={s.btns}>
                        <Button onClick={this.cancel}>取消</Button>
                        <Button
                            type="danger"
                            onClick={this.clearValue}
                            style={{ marginLeft: 8 }}
                        >
                            重置
                        </Button>
                        <Button
                            onClick={this.yes}
                            type="primary"
                            style={{ marginLeft: 8 }}
                        >
                            确定
                        </Button>
                    </div>
                )}
            </div>
        );

        //树结构弹出容器
        const treeModal = (
            <Drawer
                placement={this.isMobiele ? "right" : "left"}
                width={this.isMobiele ? "100%" : "50%"}
                closable={false}
                onClose={this.onClose}
                visible={visible}
                style={{
                    height: "100%"
                }}
                className={`${s.myDrawer} ${
                    !this.isMobiele ? s.myDrawerByPc : null
                }`}
                {...drawerConfig}
            >
                {selectTip}
                {createTreeDomFn}
            </Drawer>
        );
        let text =
            !value || JSON.stringify(value) === "{}"
                ? selectText
                : valueRender
                ? valueRender(value)
                : value[this.keys.label];

        if (!value && disabled) {
            text = null;
        }
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
                <div className={s.valueCon}>
                    {disabled ? text : <a onClick={this.showDrawer}>{text}</a>}
                </div>
                {modalType === "common" ? (
                    visible ? (
                        <div
                            ref="commonContainer"
                            className={s.commonContainer}
                        >
                            {createTreeDomFn}
                        </div>
                    ) : null
                ) : (
                    <div>{visible ? treeModal : null}</div>
                )}
            </div>
        );
    }
}

index.propTypes = {
    myFetch: PropTypes.func,
    nodeClick: PropTypes.func,
    fetchConfig: PropTypes.object,
    keys: PropTypes.object,
    yes: PropTypes.func,
    reset: PropTypes.func,
    visible: PropTypes.bool,
    search: PropTypes.bool,
    selectModal: PropTypes.string,
    valueRender: PropTypes.func,
    nodeRender: PropTypes.func,
    btnShow: PropTypes.bool,
    rightMenuOption: PropTypes.array,
    rightMenuOptionByContainer: PropTypes.array,
    modalType: PropTypes.string,
    disabled: PropTypes.bool,
    draggable: PropTypes.bool
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
