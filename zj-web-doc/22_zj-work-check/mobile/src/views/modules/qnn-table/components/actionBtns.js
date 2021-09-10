import React from "react";
import s from "../style.less";
import { Row, Col, Button } from "antd";
const index = function() {
    const { actionBtns, selectedRows } = this.state;
    return (
        <Row className={s.ctrlBtnCon}>
            <Col span={24}>
                {actionBtns.map((item, index) => {
                    let { label, type, icon, name } = item;
                    let needHide = name === "del" || name === "edit"; //是否需要隐藏

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
                            style={{
                                marginLeft: index === 0 ? 0 : 8,
                                display:
                                    needHide && selectedRows.length === 0
                                        ? "none"
                                        : ""
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
