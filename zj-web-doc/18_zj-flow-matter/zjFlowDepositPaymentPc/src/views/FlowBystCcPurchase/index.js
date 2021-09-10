import React, { Component } from "react";
import QnnTable from "../modules/qnn-table";
import FlowFormBystCcPurchase from "./form";
import { Modal } from "antd";
const confirm = Modal.confirm;
const flowConfig = {
	//流程专属配置
	workFlowConfig: {
		//后台定的字段
		title: ["name", "sendTime", "出差申请"], //标题字段
		apiNameByAdd: "addZjFlowStElection",
		apiNameByUpdate: "updateZjFlowStElection",
		apiNameByGet: "getZjFlowStElectionDetail",
		flowId: "stCcPurchase",
		formLink: {
			stCcPurchase: "FlowBystCcPurchase"
		},
		//待办已办切换路由
		todo: "todoBystCcPurchase",
		hasTodo: "hasTodostCcPurchase",
	},

	mobileModel: "flow",
	fetchConfig: {
		apiName: props => {
			var url = props.match.url;
			var myPublic = props.myPublic.appInfo.mainModule;
			if (url === `${myPublic}todoBystCcPurchase`) {
				//待办
				return "getTodoList";
			} else {
				//已办
				return "getHasTodoList";
			}
		},
		otherParams: {
			flowId: "stCcPurchase"
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
		width: "1200px",
		maskClosable: true
	},
	paginationConfig: {
		position: "bottom"
	},
	actionBtns: [
		{
			name: "Component",
			Component: FlowFormBystCcPurchase,
			icon: "plus",
			type: "primary",
			label: "出差申请"
		},
		{
			name: 'export',//内置add del
			type: 'primary',//类型  默认 primary  [primary dashed danger]
			label: '导出',
			disabled:(obj) => {
				if(obj.btnCallbackFn.getSelectedRows().length === 1){
					return false;
				}else{
					return true;
				}
			},
			onClick:(obj) => {
				confirm({
					content: '确定导出数据吗?',
					onOk: () => {
						let { myPublic: { domain,appInfo: { ureport } } } = obj.props;
						let rowData = obj.selectedRows[0];
						window.open(`${ureport}excel?_u=file:zjFlowStElection.xml&url=${domain}&workId=${rowData.workId}`);
					}
				});
			}
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
						stCcPurchase: FlowFormBystCcPurchase
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
				headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
				{...flowConfig}
			/>
		);
	}
}

export default index;
