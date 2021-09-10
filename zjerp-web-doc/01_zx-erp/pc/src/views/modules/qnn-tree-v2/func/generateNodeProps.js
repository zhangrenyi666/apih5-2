import React from "react";
import {
    Input,Tooltip,
} from "antd";
import { ActivityIndicator } from "antd-mobile"
import s from "../style.less"

const generateNodeProps = function (data) {
    const { nodeRender,nodeNoTooltip } = this.props;
    const { editValue,nodeByLoading = [],curClickNodeData = {} } = this.state;
    const { label,value,children,canExpand = "canExpand" } = this.keys;
    let _label = data.node[label];
    let _value = data.node[value];
    let isCanExpand = data.node[canExpand] || false;
    let { isAdd,postEndbackFn } = data.node;
    let edit = editValue === _value;
    const changeLabel = e => {
        let inputValue = e.target.value.toString().trim();
        if (isAdd) {
            //新增
            postEndbackFn({
                [label]: inputValue,
                [value]: data.node[value],
                [children]: data.node[children],
            });
        } else {
            //编辑 
            if (inputValue !== this.inputValue) {
                this.postEditNodeInfo({ ...data.node,[label]: inputValue });
            }
        }

        //清空this.inputValue
        this.inputValue = null;
    };

    //自定义节点渲染
    if (nodeRender) {
        _label = nodeRender(data.node);
    }

    data.node.title = (<div
        onContextMenu={(e) => {
            e.preventDefault();
            e.stopPropagation();
            this.onRightClick({
                event: e,
                node: {
                    ...data.node
                }
            });
        }}>

        {
            edit ? (
                <Input
                    autoFocus
                    defaultValue={this.inputValue}
                    placeholder={"请输入..."}
                    onBlur={changeLabel}
                    onPressEnter={changeLabel}
                />
            ) : (<div className={`${curClickNodeData && curClickNodeData[value] === _value ? s.curClickNode : null}  ${s.node}`}>
                {
                    !nodeNoTooltip && _label && _label.length > 15 ? (
                        <Tooltip title={_label}>
                            <span>{_label}</span>
                        </Tooltip>
                    ) : (<>{nodeByLoading.includes(_value) ? <ActivityIndicator text={_label} /> : (_label || '未命名')}</>)
                }
            </div>)
        }

    </div>);

    //设置子集
    data.node.children = data.node.children || data.node[children] || (isCanExpand ? [] : null);

    return {
        onClick: () => {
            //选中数据
            this.onSelect(data);
        },

        //双击展开/收缩节点
        // onDoubleClick: () => {
        //     this.onSelect(data);
        // },

        listIndex: data.treeIndex,
        ...data
    }
}

export default generateNodeProps;
