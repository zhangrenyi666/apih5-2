import React, { Component } from "react";
import QnnTable from "../modules/qnn-table";
import FlowFormByDepositPayment from "./form";
import FlowFormByDepositPaymentForJg from "../FlowByDepositPaymentForJg/form";

const flowConfig = {
	//流程专属配置
	workFlowConfig: {
		//后台定的字段
		title: ["title_sendTime", "机关保证金支付", "申请"], //标题字段
		apiNameByAdd: "addZjFlowDepositPayment",
		apiNameByUpdate: "updateZjFlowDepositPayment",
		apiNameByGet: "getZjFlowDepositPaymentDetail",
		flowId: "jgDepositPayment",
		formLink: {
			DepositPayment: "FlowByDepositPayment",
			jgDepositPayment: "FlowByjgDepositPayment"
		},
		//待办已办切换路由
		todo: "todoByjgDepositPayment",
		hasTodo: "hasTodoByjgDepositPayment"
	},

	mobileModel: "flow",
	fetchConfig: {
		apiName: props => {
			var url = props.match.url;
			var myPublic = props.myPublic.appInfo.mainModule;
			if (url === `${myPublic}todoByDepositPayment`) {
				//待办
				return "getTodoList";
			} else {
				//已办
				//return "getHasTodoList";
				return "getHasTodoList";
			}
		},
		otherParams: {
			flowId: "DepositPayment,jgDepositPayment"
		}
	},
	antd: {
		rowKey: function (row) {
			//---row.主键id
			return row.workId;
		}
	},
    isShowRowSelect:false,
	drawerConfig: {
		width: "1200px",
		maskClosable: true
	},
	paginationConfig: {
		position: "bottom"
	},
	actionBtns: [
		{
			name: "Component",
			Component: FlowFormByDepositPaymentForJg,
			icon: "plus",
			type: "primary",
			label: "机关保证金支付"
		}
	],
	formConfig: [
		{
			isInForm: false,
			table: {
				title: "标题",
				dataIndex: "title",
				key: "title",
				onClick: "Component",
				Component: {
					flowId: {
						DepositPayment: FlowFormByDepositPayment,
						jgDepositPayment: FlowFormByDepositPaymentForJg
					}
				}
			}
		},
		{
			isInForm: false,
			table: {
				title: "发起人",
				dataIndex: "sendUserName",
				key: "sendUserName"
			}
		},
		{
			isInForm: false,
			table: {
				title: "发起时间",
				dataIndex: "sendTime",
				key: "sendTime",
				format: "YYYY-MM-DD HH:mm:ss"
			}
		},
		{
			isInForm: false,
			table: {
				title: "流程状态",
				dataIndex: "flowStatus",
				key: "flowStatus"
			}
		}
	]
};

class index extends Component {
	render() {
		return (
			<QnnTable
				{...this.props}
				fetch={this.props.myFetch} 
		 upload={this.props.myUpload} 
		 upload={this.props.myUpload}
				upload={this.props.myUpload}
				headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
				{...flowConfig}
			/>
		);
	}
}

export default index;
