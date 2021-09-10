import React from "react";
import s from "../style.less";
import { Row,Col,Button } from "antd";
import { editBtnsConfig } from "../config"

const index = function () {
    let { actionBtns,selectedRows,actionBtnsPosition,edit } = this.state;
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
    if (this.props.disabled) {
        return <div />
    }
    return (
        <Row
            className={s.ctrlBtnCon}
            style={actionBtnsPosition === "bottom" ? { padding: "0" } : null}
        >
            <Col span={24}>
                {_actionBtns.map((item,index) => {
                    let { label,type,icon,name,hide } = item;
                    let needHide = name === "del" || name === "edit"; //是否需要隐藏 
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
                            disabled={needHide && selectedRows.length === 0}
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
