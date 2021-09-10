import React, { Component } from "react";
import QnnTable from "../modules/qnn-table";
import FlowFormBygszlhzApply from "./form";
import FlowFormByzlhzApply from "../FlowByzlhzApply/form";

const flowConfig = {
	//流程专属配置
	workFlowConfig: {
		//后台定的字段
		title: ["sendTime", "战略合作框架协议、联合体协议评审(各单位)"], //标题字段
		apiNameByAdd: "addZjFlowStrategicCooperation",
		apiNameByUpdate: "updateZjFlowStrategicCooperation",
		apiNameByGet: "getZjFlowStrategicCooperationDetail",
		flowId: "gszlhzApply",
		formLink: {
			gszlhzApply: "FlowBygszlhzApply",
			zlhzApply: "FlowByzlhzApply"
		},
		//待办已办切换路由
		todo: "todoBygszlhzApply",
		hasTodo: "hasTodogszlhzApply"
	},

	mobileModel: "flow",
	fetchConfig: {
		apiName: props => {
			var url = props.match.url;
			var myPublic = props.myPublic.appInfo.mainModule;
			if (url === `${myPublic}FlowBygszlhzApplyAwait`) {
				//待办
				return "getTodoList";
			} else {
				//已办
				//return "getHasTodoList";
				return "getTodoList";
			}
		},
		otherParams: {
			flowId: "gszlhzApply,zlhzApply"
		}
	},
	antd: {
		rowKey: function (row) {
			//---row.主键id
			return row.workId;
		},
		size:'small'
	},
	drawerConfig: {
		width: "800px",
		maskClosable: true
	},
	paginationConfig: {
		position: "bottom"
	},
	actionBtns: [
		{
			name: "Component",
			Component: FlowFormBygszlhzApply,
			icon: "plus",
			type: "primary",
			label: "战略合作框架协议、联合体协议评审(各单位)"
		},
		{
			name: "Component",
			Component: FlowFormByzlhzApply,
			icon: "plus",
			type: "primary",
			label: "战略合作框架协议、联合体协议评审(局)"
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
						gszlhzApply: FlowFormBygszlhzApply,
						zlhzApply: FlowFormByzlhzApply
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
				dataIndex: "trackStatus",
				key: "trackStatus"
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
				headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
				{...flowConfig}
			/>
		);
	}
}

export default index;
