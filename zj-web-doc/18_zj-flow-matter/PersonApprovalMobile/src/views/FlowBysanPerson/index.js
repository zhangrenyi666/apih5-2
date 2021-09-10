import React, { Component } from "react";
import QnnTable from "../modules/qnn-table";
import FlowBysanPersonForm from './form';
import FlowBysanMinisterForm from '../FlowBysanMinister/form';

const flowConfig = {
	workFlowConfig: {
		title: ["","","人员申请"],
		apiNameByAdd: "addXmFlowSeal",
		apiNameByUpdate: "updateXmFlowSeal",
		apiNameByGet: "getXmFlowSealDetail",
		flowId: "znrhApply",
		//移动端需要用到
		formLink: {
			sanPerson: 'FlowBysanPersonForm',
			sanMinister:'FlowBysanMinisterForm'
		},
		todo: "todoByZjPerson",
		hasTodo: "hasTodoByZjPerson"
	},
	mobileModel: "flow",
	isShowRowSelect: false,
	fetchConfig: {
		apiName: props => {
			var url = props.match.url;
			var myPublic = props.myPublic.appInfo.mainModule;
			if (url === `${myPublic}todoByZjPerson`) {
				return "getTodoList";
			} else {
				return "getHasTodoList";
			}
		},
		otherParams: {
			flowId: "sanPerson,sanMinister"
		}
	},
	antd: {
		rowKey: function (row) {
			return row.workId;
		},
		size: 'small'
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
			Component: FlowBysanPersonForm,
			icon: "plus",
			type: "primary",
			label: "人员申请"
		},
		{
			name: "Component",
			Component: FlowBysanMinisterForm,
			icon: "plus",
			type: "primary",
			label: "部门申请"
		},
	],
	formConfig: [
		{
			isInForm: false,
			table: {
				title: "标题",
				dataIndex: "title",
				key: "title",
				onClick: "Component",
				fieldsConfig: {
                    type: "string",
                    field: "keyword"
                },
				Component: {
					flowId: {
						sanPerson: FlowBysanPersonForm,
						sanMinister:FlowBysanMinisterForm
					}
				}
			}
		},
		{
			isInForm: false,
			table: {
				title: "发起人",
				width: 120,
				align:'center',
				noHaveSearchInput:true,
				dataIndex: "sendUserName",
				key: "sendUserName"
			}
		},
		{
			isInForm: false,
			table: {
				title: "发起时间",
				width:180,
				align:'center',
				noHaveSearchInput:true,
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
				align:'center',
				dataIndex: "flowStatus",
				key: "flowStatus",
				fieldsConfig: {
                    field:"flowStatus",
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
	render() {
		const { isInQnnTable } = this.props; 
		return (
			<div style={{ height: isInQnnTable ? "" : "100vh" }}>
				<QnnTable
					{...this.props}
					fetch={this.props.myFetch} 
					upload={this.props.myUpload}
					headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
					{...flowConfig}
				/>
			</div>
			
		);
	}
}

export default index;
