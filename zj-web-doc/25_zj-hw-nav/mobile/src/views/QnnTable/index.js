import React, { Component } from "react";
import QnnTable from "../modules/qnn-table";
// import QnnTable from 'qnn-table';

class index extends Component {
  render() {
    return (
      <QnnTable
        history={this.props.history}
        match={this.props.match}
        fetch={this.props.myFetch}
        headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
        {...window.QnnTableConfigByTechnical}
      />
    );
  }
}

export default index;
