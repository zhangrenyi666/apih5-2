import React, { Component } from "react";
import Detail from './Detail';
import Assess from './assess';
import { Tabs } from 'antd';
const { TabPane } = Tabs;
class index extends Component {
  constructor(props) {
    super(props)
    this.state = {
      activeKey: '1',
    }
  }
  tabsCallback = (activeKey) => {
    this.setState({
      activeKey
    })
  }
  render() {
    const { activeKey } = this.state;
    return (
      <div>
        <Tabs activeKey={activeKey} onChange={this.tabsCallback}>
          <TabPane tab="学历" key="1">
            <Detail {...this.props} categoryID={'categorysafe001003'} />
          </TabPane>
          <TabPane tab="职称" key="2">
            <Detail {...this.props} categoryID={'categorysafe001004'} />
          </TabPane>
          <TabPane tab="人员类型" key="3">
            <Detail {...this.props} categoryID={'categorysafe001008'} />
          </TabPane>
          <TabPane tab="特种作业" key="4">
            <Detail {...this.props} categoryID={'categorysafe001005'} />
          </TabPane>
          <TabPane tab="培训内容" key="5">
            <Detail {...this.props} categoryID={'category_safe_educontext'} />
          </TabPane>
          <TabPane tab="预算比率" key="6">
            <Detail {...this.props} categoryID={'categorysafe001009'} />
          </TabPane>
          <TabPane tab="考核库" key="7">
            <Assess {...this.props} />
          </TabPane>
        </Tabs>
      </div>
    );
  }
}

export default index;