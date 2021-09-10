import React, { Component } from "react";

import FlowList from "./Model/flowModel/List";

class index extends Component {
  sModel = mobileModel => {
    if (typeof mobileModel === "string") {
      switch (mobileModel) {
        case "flow":
          return <FlowList {...this.props}/>;
        default:
          return (
            <div>
              {mobileModel || ["未传入mobileModel"]}
              该移动端模型当前qnn-table中未定义
            </div>
          );
      }
    }else{
      //自定义模型
      return <div>暂不支持移动端</div>
    }
  };

  render() {
    const { mobileModel } = this.props;
    return this.sModel.bind(this, mobileModel)();
  }
}

export default index;
