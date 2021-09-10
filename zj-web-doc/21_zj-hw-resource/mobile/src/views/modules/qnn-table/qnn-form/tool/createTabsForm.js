//创建tabs表单的方法
import React from "react";
import { Tabs, Spin } from "antd";
const TabPane = Tabs.TabPane;

const createTabsForm = function({ style, tabs }) {
    const { tabsActiveKey, loading } = this.state;
    return (
        <div className={style.tabsContainer} name="tabsContainer">
            <Tabs activeKey={tabsActiveKey} onChange={this.tabsClick}>
                {tabs.map(({ name, title, content }, index) => {
                    return (
                        <TabPane
                            forceRender={false}
                            tab={title}
                            key={index.toString()}
                        >
                            <Spin spinning={loading}>
                                {this.createTabsContent({
                                    name,
                                    title,
                                    content,
                                    style
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
