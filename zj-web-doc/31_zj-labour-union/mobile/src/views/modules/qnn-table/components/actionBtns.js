import React from "react";
import s from "../style.less";
import { Row,Col,Button } from "antd";
import { editBtnsConfig } from "../config"

const index = function () {
    let { actionBtns,selectedRows,actionBtnsPosition,edit } = this.state;

    actionBtns = this.bind(actionBtns);
    if (typeof actionBtns === "function") {
        actionBtns = actionBtns({
            ...this.props,
            btnCallbackFn: this.btnCallbackFn
        });
    }
    //开发者模式下提供的按钮
    let _actionBtns = [...actionBtns];
    if (edit) {
        _actionBtns = [..._actionBtns,...editBtnsConfig];
    }
    if (this.props.disabled || !_actionBtns.length) {
        return <div />
    }
    return (
        <Row
            className={s.ctrlBtnCon}
            style={actionBtnsPosition === "bottom" ? { padding: "0" } : null}
        >
            <Col span={24}>
                {_actionBtns.map((item,index) => {
                    let { label,type,icon,name,hide,disabled } = item;
                    let needHide = name === "del" || name === "_delField" || name === "edit"; //是否需要隐藏 

                    hide = this.bind(hide);
                    if ((typeof hide) === 'function') {
                        hide = hide({
                            btnInfo: item,
                            props: this.props,
                            btnCallbackFn: this.btnCallbackFn
                        });
                    }

                    if (hide) {
                        return null;
                    }

                    disabled = this.bind(disabled);
                    if ((typeof disabled) === 'function') {
                        disabled = disabled({
                            btnInfo: item,
                            props: this.props,
                            btnCallbackFn: this.btnCallbackFn
                        });
                    }

                    if (!disabled) {
                        disabled = needHide && selectedRows.length === 0
                    }

                    return (
                        <Button
                            type={type || "primary"}
                            key={index}
                            onClick={() => {
                                const _params = {
                                    ...this.props,
                                    ...item
                                };
                                this.action(_params);
                            }}
                            disabled={disabled}
                            style={{
                                marginLeft: index === 0 ? 0 : 8,
                            }}
                            icon={icon}
                        >
                            {label}
                        </Button>
                    );
                })}
            </Col>
        </Row>
    );
};

export default index;
