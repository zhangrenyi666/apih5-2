import { basic } from '../modules/layouts';
import React, { Component } from "react";
import QnnTable from "../modules/qnn-table";

class index extends Component {
	constructor() {
		super();
		this.state = {
			flag: ""
		}
	}
	componentDidMount() {
		this.props.myFetch('getZjSchemeConfirmationListByUser', {}).then(({ data, success, message }) => {
			if (success) {
				this.setState({ flag: data.flag })
			}
		})
	}
	render() {
		const { flag } = this.state;
		console.log(flag);
		return (
			<div>
				{flag && flag === '1' ? <QnnTable
					{...this.props}
					fetch={this.props.myFetch} 		 upload={this.props.myUpload}
					headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
					upload={this.props.myUpload}
					{...window.schemeByJldPage}
				/> : null}
				{flag && flag === '2' ? <QnnTable
					{...this.props}
					fetch={this.props.myFetch} 		 upload={this.props.myUpload}
					headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
					upload={this.props.myUpload}
					{...window.schemeByZgPage}
				/> : null}
				{flag && flag === '3' ? <QnnTable
					{...this.props}
					fetch={this.props.myFetch} 		 upload={this.props.myUpload}
					headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
					upload={this.props.myUpload}
					{...window.schemeByfqPage}
				/> : null}				
			</div>
		);
	}
}
export default basic(index);
