import { basic } from '../modules/layouts';
import React, { Component } from "react";
import QnnTable from "../modules/qnn-table";
import { Form } from 'antd';
import s from './index.less';
class index extends Component {
	render() {
		return (
			<div className={s.root}>
				<QnnTable
					{...this.props}
					fetch={this.props.myFetch} 
		 upload={this.props.myUpload}
					headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
					{...window.zjPrEvaluationInfoPage}
				/>
			</div>
		);
	}
}
// export default index;
export default Form.create()(basic(index));
