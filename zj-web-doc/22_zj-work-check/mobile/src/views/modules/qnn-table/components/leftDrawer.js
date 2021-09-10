import React from "react";
import { Drawer, Button } from "antd";
import QnnForm from "../qnn-form";
import s from "../style.less";
const index = function() {
    const {
        DrawerShow,
        drawerConfig,
        drawerDetailTitle,
        drawerBtns = [],
        MyComponent
    } = this.state;
    const formConfig = this.getFormConfig(); 
    return (
        <div>
            <Drawer
                maskClosable={true}
                className={s.myDrawer}
                title={drawerDetailTitle}
                width="45%"
                placement="right"
                ref={me => {
                    if (me) {
                        let titleConDom = document.getElementsByClassName(
                            "ant-drawer-header"
                        );
                        let titleConDomHeight = 0;
                        if (titleConDom && titleConDom[0]) {
                            titleConDomHeight = titleConDom[0].offsetHeight;
                            this.setState({
                                titleConDomHeight
                            });
                        }
                    }
                }}
                onClose={() => {
                    this.closeDrawer(false);
                }}
                visible={DrawerShow}
                destroyOnClose={true}
                {...drawerConfig}
            >
                {/* 自定义组件存在将使用自定义组件 */}
                {/* 这里不使用display形式会出警告  后期需要注意 */}

                {MyComponent}
                <div
                    style={{
                        height: window.innerHeight - 55,
                        paddingBottom: "30px",
                        display: MyComponent ? "none" : "auto"
                    }}
                >
                    <QnnForm {...formConfig} />
                </div>

                {drawerBtns.length && !MyComponent > 0 ? (
                    <div
                        style={{
                            position: "absolute",
                            bottom: 0,
                            width: "100%",
                            borderTop: "1px solid #e8e8e8",
                            padding: "10px 16px",
                            textAlign: "right",
                            left: 0,
                            background: "#fff",
                            borderRadius: "0 0 4px 4px"
                        }}
                    >
                        {drawerBtns.map((item, index) => {
                            let { label, type } = item;
                            return (
                                <Button
                                    key={index}
                                    onClick={() => {
                                        this.action(item);
                                    }}
                                    style={{
                                        marginLeft: "8px"
                                    }}
                                    type={type || "primary"}
                                >
                                    {label}
                                </Button>
                            );
                        })}
                    </div>
                ) : null}
            </Drawer>
        </div>
    );
};

export default index;
