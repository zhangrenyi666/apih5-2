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
    postEditNodeInfo
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
    constructor(props) {
        super(...props);
        console.assert(props.fetchConfig, "Tree组件fetchConfig属性必传");
        const { apiName, params = {}, parmasKey } = props.fetchConfig;
        console.assert(apiName, "Tree组件fetchConfig{apiName}属性必传");
        this.state = {
            params,
            visible: props.visible || false,
            disabled: props.disabled || false,
            visibleBySelectTip: false,
            data: [],
            valueByTree: [], //树结构的value
            value: props.value || null, //最终取的节点信息 
            onRightClickDomInfo: {
                //右键节点的信息
                show: false,
                left: 0,
                top: 0
            },
            rightMenuOption: props.rightMenuOption || [
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

        this.myFetch = props.myFetch;
        this.refresh = refresh.bind(this);
        this.showDrawer = showDrawer.bind(this);
        this.onClose = onClose.bind(this);
        this.onLoadData = onLoadData.bind(this);
        this.onCheck = onCheck.bind(this);
        this.onSelect = onSelect.bind(this);
        this.onCloseBySelectTip = onCloseBySelectTip.bind(this);
        this.clearValue = clearValue.bind(this);
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
    }
    componentDidMount() {
        Toast.loading("请稍等...", 0);
        this.refresh(this.params, data => {
            this.setState({ data });
            Toast.hide();
        });
        setTimeout(() => {
            Toast.hide();
        }, 5000);
    }

    render() {
        const {
            visible,
            valueByTree = [],
            value,
            data,
            visibleBySelectTip,
            onRightClickDomInfo: { show, left, top },
            rightMenuOption = []
        } = this.state;
        const {
            selectModal = "1",
            valueRender,
            btnShow = true,
            drawerConfig = {},
            disabled
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
                    display: rightMenuOption.length && show ? "block" : "none",
                    left: left,
                    top: top
                }}
            >
                <ul>
                    {rightMenuOption.map((item, index) => {
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
            <div>
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
                >
                    {this.renderTreeNodes(data)}
                </Tree>
            </div>
        );

        //树结构弹出容器
        const treeModal = (
            <Drawer
                placement={"left"}
                width={this.isMobiele ? "100%" : "50%"}
                closable={false}
                onClose={this.onClose}
                visible={visible}
                style={{
                    height: "100%",
                    position: "relative"
                }}
                className={`${s.myDrawer} ${
                    !this.isMobiele ? s.myDrawerByPc : null
                }`}
                {...drawerConfig}
            >
                {selectTip}
                {onRightClickDom}
                <div className={s.con}>{treeDom}</div>
                {btnShow === false ? null : (
                    <div className={s.btns}>
                        <Button onClick={this.clearValue}>重置</Button>
                        <Button
                            onClick={this.yes}
                            type="primary"
                            style={{ marginLeft: 8 }}
                        >
                            确定
                        </Button>
                    </div>
                )}
            </Drawer>
        );
        let text = !value
            ? "选择"
            : valueRender
            ? valueRender(value)
            : value[this.keys.label];

        if (!value && disabled) {
            text = null;
        }

        return (
            <div className={s.treeContainer}>
                <div className={s.valueCon}>
                    {disabled ? text : <a onClick={this.showDrawer}>{text}</a>}
                </div>
                <div>{visible ? treeModal : null}</div>
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
    rightMenuOption: PropTypes.array
};

export default index;
