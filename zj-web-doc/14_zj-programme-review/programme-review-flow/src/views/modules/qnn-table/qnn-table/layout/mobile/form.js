import React, { Component } from "react";
import QnnForm from "../../qnn-form";
import { Form } from "antd";

class idnex extends Component {
  // componentDidMount() {
    
  // }
  render() {
    return (
      <div>
        <QnnForm
          {...this.props}
          fetch={this.props.myFetch} //必须返回一个promise
          //内部自带发送ajax的组件发送请求时会自动加到head头里面的数据 （上传会用到）
          headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
          {...config}
        />
      </div>
    );
  }
}
export default Form.create()(idnex);
