import React, { Component } from "react";
import QnnTable from "../modules/qnn-table";
import FlowByghjfsq from './form';


const flowConfig = {
	workFlowConfig: {
		title: ["handler","title_sendTime","工会经费申请"], //标题字段
		apiNameByAdd: "addZjFlowUnionDues",
		apiNameByUpdate: "updateZjFlowUnionDues",
		apiNameByGet: "getZjFlowUnionDuesDetails",
		flowId: "ghjfsq",
		todo: "todoByghjfsq",
		hasTodo: "hasTodoByghjfsq"
	},
	mobileModel: "flow",
	isShowRowSelect: false,
	fetchConfig: {
		apiName: props => {
			var url = props.match.url;// 	/ProPc/hasTodoByZjInHome
			var myPublic = props.myPublic.appInfo.mainModule;//		/ProPc/
			if (url === `${myPublic}todoByghjfsq`) {
				//待办
				return "getTodoList";
			} else {
				//已办
				return "getHasTodoList";
			}
		},
		otherParams: {
			flowId: "ghjfsq"
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
	actionBtns: [
		{
			name: "Component",
			Component: FlowByghjfsq,
			icon: "plus",
			type: "primary",
			label: "工会经费",
			drawerTitle:<div style={{fontSize:'22px',fontWeight:'bold',textAlign:'center'}}><div>中交一公局集团有限公司</div><div>工会经费申请单</div></div>
		},
		// {
		// 	name: "Component",
		// 	Component: FlowByjthjrhApply,
		// 	icon: "plus",
		// 	type: "primary",
		// 	label: "北京集体户籍入户申请"
		// },
		// {
		// 	name: "Component",
		// 	Component: FlowByCopyjthjrhApply,
		// 	icon: "plus",
		// 	type: "primary",
		// 	label: "外省市入北京集体户籍入户申请"
		// },
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
				drawerTitle:<div style={{fontSize:'22px',fontWeight:'bold',textAlign:'center'}}><div>中交一公局集团有限公司</div><div>工会经费申请单</div></div>,
				Component: {
					flowId: {
						ghjfsq: FlowByghjfsq
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
