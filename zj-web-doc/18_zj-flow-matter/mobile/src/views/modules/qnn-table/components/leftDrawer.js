import React from "react";
import { Drawer, Button } from "antd"; //, Form
import QnnForm from "../qnn-form";
import s from "../style.less";
const index = function() {
    const {
        DrawerShow,
        drawerConfig,
        drawerDetailTitle,
        drawerBtns = [],
        MyComponent,
        clickCb
    } = this.state;
    const formConfig = this.getFormConfig();
    const _props = {
        ...this.props
    };
    //删除掉一些父级的props
    delete _props.formConfig;
    delete _props.desc;
    delete _props.antd;
    delete _props.searchFormColNum;
    delete _props.infoAlert;
    delete _props.data;
    delete _props.Item;
    delete _props.formItemLayout;
    delete _props.formItemLayoutSearch;
    delete _props.actionBtns;
    delete _props.fetchConfig;
    delete _props.drawerConfig;
    delete _props.labelConfig;
    delete _props.isShowRowSelect;
    delete _props.tabs;
    return (
        <div>
            <Drawer
                maskClosable={true}
                className={s.myDrawer}
                title={drawerDetailTitle}
                width="55%"
                placement="right"
                onClose={() => {
                    this.closeDrawer(false);
                }}
                visible={DrawerShow}
                destroyOnClose={true} //不设会导致打开自定义组件后无法再继续渲染内置表单（增删改）
                {...drawerConfig}
            >
                {/* 不采用这种写法是因为在qnn-form组件加载完后会默认去模拟点击一下tabs项以刷新数据，导致配置了tabs和自定义组件冲突了 */}
                {/* {DrawerShow ? (MyComponent ? MyComponent : null) : null}
                {DrawerShow ? (
                    <div
                        style={{
                            height: window.innerHeight - 55,
                            paddingBottom: "30px",
                            display: MyComponent ? "none" : ""
                        }}
                    >
                        <QnnForm
                            clickCb={clickCb}
                            fetch={this.props.myFetch} //必须返回一个promise
                            //内部自带发送ajax的组件发送请求时会自动加到head头里面的数据 （上传会用到）
                            {..._props}
                            {...formConfig}
                        />
                    </div>
                ) : null} */}
                {MyComponent ? (
                    MyComponent
                ) : (
                    <div
                        className={s.drawerQnnForm}
                        style={{
                            height: window.innerHeight - 55,
                            paddingBottom: "30px"
                        }}
                    >
                        <QnnForm
                            clickCb={clickCb}
                            fetch={this.props.myFetch} //必须返回一个promise
                            Pstate={this.state}
                            //内部自带发送ajax的组件发送请求时会自动加到head头里面的数据 （上传会用到）
                            {..._props}
                            {...formConfig} 
                        />
                        {/* <div className={s.drawerQnnFormCon}>
                            <QnnForm
                                clickCb={clickCb}
                                fetch={this.props.myFetch} //必须返回一个promise
                                //内部自带发送ajax的组件发送请求时会自动加到head头里面的数据 （上传会用到）
                                {..._props}
                                {...formConfig}
                            />
                        </div>
                        <div className={s.right}>123456</div> */}
                    </div>
                )}

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
                            let { label, type, hide } = item;
                            if (typeof hide === "function") {
                                hide = hide({
                                    ...this.props,
                                    fetch: this.fetch,
                                    btnCallbackFn: this.btnCallbackFn,
                                    state: this.state
                                });
                            }
                            if (hide) {
                                return <div key={index} />;
                            } else {
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
                            }
                        })}
                    </div>
                ) : null}
            </Drawer>
        </div>
    );
};

// export default Form.create()(index);
export default index;
