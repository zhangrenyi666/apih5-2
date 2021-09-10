import React from "react";
import { Drawer,Button } from "antd"; //, Form
import QnnForm from "../qnn-form";
import s from "../style.less";
const index = function () {
    const {
        DrawerShow,
        drawerConfig,
        drawerDetailTitle,
        drawerBtns = [],
        drawerBtnsByComponent=[],
        MyComponent,
        clickCb,
        tabsIndex,
        maskClosable,
        loadingBtnsByField,
        disabledBtnsByField,
        formIsChangeed,
        formIsChangeedAlertTextContent
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

    const btnsDom = (btnsData) => {
        return <div
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
            {btnsData.map((item,index) => {
                let { label,type,hide,field } = item;
                let isLoading = false;
                let isDisabled = false;
                if (!field) {
                    console.warn(`抽屉中的每个按钮必须设置一个主键 field，${label}按钮未设置field属性！`)
                }
                if (field && loadingBtnsByField.includes(field)) {
                    isLoading = true;
                }
                if (field && disabledBtnsByField.includes(field)) {
                    isDisabled = true;
                }
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
                            loading={isLoading}
                            disabled={isDisabled}
                        >
                            {label}
                        </Button>
                    );
                }
            })}
        </div>
    }; 
    return (
        <div>
            {
                !DrawerShow ? null : <Drawer
                    // maskClosable={maskClosable} 
                    // maskClick={()=>{
                    //     console.log(123456)
                    // }}
                    className={s.myDrawer}
                    title={drawerDetailTitle}
                    width="55%"
                    placement="right"
                    onClose={(e) => {
                        let className = e.target.getAttribute("class");
                        if(className === "ant-drawer-mask" && !maskClosable){
                            //检查是否进行过表达修改 做出相应提示
                            if(formIsChangeed){ 
                                this.setState({
                                    formIsChangeedAlertText:<div>{formIsChangeedAlertTextContent.body}</div>
                                })
                            }else{
                                this.closeDrawer(false);
                            }
                        }else{ 
                            this.closeDrawer(false);
                        } 
                    }}
                    visible={DrawerShow}
                    destroyOnClose={true} //不设会导致打开自定义组件后无法再继续渲染内置表单（增删改）
                    {...drawerConfig}
                >

                    {MyComponent ? (
                        MyComponent
                    ) : (
                            <div
                                className={s.drawerQnnForm}
                                style={{
                                    height: window.innerHeight - 55,
                                }}
                            >
                                <QnnForm
                                    wrappedComponentRef={(me) => {
                                        if (me) {
                                            this.qnnForm = me;
                                        }
                                    }}
                                    clickCb={clickCb}
                                    fetch={this.props.myFetch} //必须返回一个promise
                                    Pstate={this.state}
                                    tabsActiveKey={tabsIndex}
                                    tableFns={this.btnCallbackFn}
                                    //内部自带发送ajax的组件发送请求时会自动加到head头里面的数据 （上传会用到）
                                    {..._props}
                                    {...formConfig}
                                />
                            </div>
                        )}

                    {drawerBtns.length && !MyComponent ? btnsDom(drawerBtns) : null}
                    {MyComponent && drawerBtnsByComponent.length ? btnsDom(drawerBtnsByComponent) : null}
                </Drawer>

            }

        </div>
    );
};

export default index;
