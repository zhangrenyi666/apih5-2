import React, { Component } from "react";
import QnnTable from "../modules/qnn-table";
import { Spin, message as Msg } from "antd";
import FlowFormByJjgdajy from "./form";
import FlowFormByDajy from "../FlowByDajy/form";

const flowConfig = {
	workFlowConfig: {
		title: ["", "", ""],
		apiNameByAdd: "addZjFlowArchivesLibrary",
		apiNameByUpdate: "updateZjFlowArchivesLibrary",
		apiNameByGet: "getZjFlowArchivesLibraryDetails",
		apiTitle: "setZjkOaFlowTitle",//???
		flowId: "Jjgdajy",
		todo: "todoFlow",//???
		hasTodo: "hasTodoFlow",//???
	},
	mobileModel: "flow",
	isShowRowSelect: false,
	fetchConfig: {
		apiName: props => {
			var url = props.match.url;
			var myPublic = props.myPublic.appInfo.mainModule;
			if (url === `${myPublic}todoFlow`) {
				return "getTodoList";
			} else {
				return "getHasTodoList";
			}
		},
		otherParams: {
			flowId: "Jjgdajy,Dajy"
		}
	},
	antd: {
		rowKey: function (row) {
			return row.workId;
		},
		size: 'small'
	},
	drawerConfig: {
		width: "1000px",
		maskClosable: true
	},
	paginationConfig: {
		position: "bottom"
	},
	firstRowIsSearch: true,
	isShowRowSelect: true,
	actionBtns: [
		{
			name: "Component",
			Component: FlowFormByJjgdajy,
			icon: "plus",
			type: "primary",
			label: "档案借阅(局机关)申请单"
		},
		{
			name: "Component",
			Component: FlowFormByDajy,
			icon: "plus",
			type: "primary",
			label: "档案借阅(公司)申请单"
		},
		
	],
	formConfig: [
		{
			isInForm: false,
			table: {
				title: "标题",
				dataIndex: "title",
				key: "title",
				width:300,
				onClick: "Component",
				fieldsConfig: {
					type: "string",
					field: "keyword"
				},
				Component: {
					flowId: {
						Jjgdajy: FlowFormByJjgdajy,
						Dajy:FlowFormByDajy
					}
				}
			}
		},
		{
			isInForm: false,
			table: {
				title: "发起人",
				width: 120,
				align: 'center',
				noHaveSearchInput: true,
				dataIndex: "sendUserName",
				key: "sendUserName"
			}
		},
		{
			isInForm: false,
			table: {
				title: "发起时间",
				width: 180,
				align: 'center',
				noHaveSearchInput: true,
				dataIndex: "sendTime",
				key: "sendTime",
				format: "YYYY-MM-DD HH:mm:ss"
			}
		},
		{
			isInForm: false,
			table: {
				title: "流程状态",
				width: 120,
				align: 'center',
				dataIndex: "flowStatus",
				key: "flowStatus",
				fieldsConfig: {
					field: "flowStatus",
					type: "select",
					placeholder: "请选择...",
					fetchConfig: {
						apiName: "getBaseCodeSelect",
						otherParams: {
							itemId: 'liuChengZhuangTai'
						}
					},
					optionConfig: {
						label: 'itemName',
						value: 'itemId',
					},
				},
			}
		}
	]
};

class index extends Component {
	constructor(props) {
		super(props);
		this.state = {
			loadingSend: false
		}
	}
	render() {
		const { loadingSend } = this.state;
		return (
			<Spin spinning={loadingSend}>
				<QnnTable
					{...this.props}
					fetch={this.props.myFetch}
					upload={this.props.myUpload}
					headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
					{...flowConfig}
					wrappedComponentRef={(me) => {
						this.table = me;
					}}
				/>
			</Spin>

		);
	}
}

export default index;
