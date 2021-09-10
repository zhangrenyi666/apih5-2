import { basic } from '../modules/layouts'; 
import React, { Component } from "react";
import QnnTable from "../modules/qnn-table";

class index extends Component {
	render() {
		return (
				<div>
				<QnnTable
				{...this.props}
				fetch={this.props.myFetch} 
		 upload={this.props.myUpload}
				headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
				{...window.zjPrPJudgesTodoPage}
				/>
				</div>
		);
	}
}
export default basic(index);
