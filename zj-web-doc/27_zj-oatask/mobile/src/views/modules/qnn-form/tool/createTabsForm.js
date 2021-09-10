//创建tabs表单的方法
import React from "react";
import { Tabs,Spin } from "antd";
const TabPane = Tabs.TabPane;

const createTabsForm = function ({ style,tabs }) {
    const { tabsActiveKey,loading,loadingByForm,qnnFormContextHeight } = this.state;
    return (
        <div className={`${style.tabsContainer} ${this.isMobile() ? style.tabsContainerByMobile : ""}`} name="tabsContainer">
            <Tabs style={{ height: "100%" }} activeKey={tabsActiveKey} onChange={this.tabsClick}>
                {tabs.map(({ name,title,content,disabled,...otherArgs },index) => {
                    if (typeof disabled === "function") {
                        disabled = disabled({
                            ...this.props,
                            fetch: this.fetch,
                            btnCallbackFn: this.btnCallbackFn,
                            state: this.state
                        });
                    }
                    let tabContextHeight = 0;
                    if (this.isMobile()) {
                        tabContextHeight = qnnFormContextHeight; //45顶部tab 
                    } else {
                        tabContextHeight = "100%";
                    }
                    return (
                        <TabPane
                            forceRender={true}
                            tab={title}
                            key={index.toString()}
                            disabled={disabled}
                            style={{ height: tabContextHeight }}
                            {...otherArgs}
                        >
                            <Spin spinning={(loading ? loading : loadingByForm)}>
                                {this.createTabsContent({
                                    name,
                                    title,
                                    content,
                                    style,
                                    tabContextHeight
                                })}
                            </Spin>
                        </TabPane>
                    );
                })}
            </Tabs>
        </div>
    );
};
export default createTabsForm;
