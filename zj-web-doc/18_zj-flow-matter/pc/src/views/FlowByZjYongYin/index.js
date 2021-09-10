import React, { Component } from "react";
import QnnTable from "../modules/qnn-table";
// 用印申请
import FlowFormByzjYongYin from "./form";
// 外携用印申请----密码：Test@#34---iexplore
import FlowFormByzjYyOutSeal from "../FlowByZjYyOutSeal/form";
// 党委用印申请
import FlowFormByZjDwYongYin from "../FlowByZjDwYongYin/form";
// 因私出国
import FlowFormByZjGoAbroad from "../FlowByZjGoAbroad/form";
// 党费使用管理
import FlowByZjPartyFeeUse from "../FlowByZjPartyFeeUse/form";
// 事务申请流程
import FlowByZjAffairsApply from '../FlowByZjAffairsApply/form';
import FlowByFlowId2 from '../FlowByFlowId2/form';
import FlowByFlowIdZj1 from '../FlowByFlowIdZj1/form';
import FlowByZjjwYongYin from '../FlowByZjjwYongYin/form';
import FlowByZjTripApply from '../FlowByZjTripApply/form';
import FlowByZjWorkApply from '../FlowByZjWorkApply/form';
import FlowByzZjLeaveApply from '../FlowByzZjLeaveApply/form';
import FlowByWgsWxYongYin from '../FlowByWgsWxYongYin/form';
import FlowByWgsYongYin from '../FlowByWgsYongYin/form';
import FlowByLgsYongYin from '../FlowByLgsYongYin/form';
import FlowByZjjgGoAbroad from '../FlowByZjjgGoAbroad/form';


const flowConfig = {
	workFlowConfig: {
		title: ["", "用印", "申请"],
		apiNameByAdd: "addFlowSealInLaunch",
        apiNameByUpdate: "updateFlowSealAfterSubmit",
        apiNameByGet: "getFlowSealDetailByWorkId",
		flowId: "zjYongYin",
		formLink: {
			zjYongYin: "FlowByzjYongYin"
		},
		todo: "todoByzjYongYin",
		hasTodo: "hasTodoByzjYongYin"
	},

	mobileModel: "flow",
	isShowRowSelect: false,
	fetchConfig: {
		apiName: props => {
			var url = props.match.url;
			var myPublic = props.myPublic.appInfo.mainModule;
			if (url === `${myPublic}todoByzjYongYin`) {
				return "getTodoList";
			} else {
				return "getHasTodoList";
			}
		},
		otherParams: {
			flowId: "zjYongYin,zjYyOutSeal,zjDwYongYin,zjGoAbroad,zjPartyFeeUse,zjAffairsApply,flowId2,flowIdZj1,zjjwYongYin,zjTripApply,zjWorkApply,zjLeaveApply,wgsWxYongYin,wgsYongYin,lgsYongYin,zjjgGoAbroad"
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
	// firstRowIsSearch: true,
	actionBtns: [
		{
			name: "Component",
			Component: FlowFormByzjYongYin,
			icon: "plus",
			type: "primary",
			label: "用印申请"
		},
		{
			name: "Component",
			Component: FlowFormByzjYyOutSeal,
			icon: "plus",
			type: "primary",
			label: "外携用印申请"
		},
		{
			name: "Component",
			Component: FlowFormByZjDwYongYin,
			icon: "plus",
			type: "primary",
			label: "党委用印申请"
		},
		{
			name: "Component",
			Component: FlowFormByZjGoAbroad,
			icon: "plus",
			type: "primary",
			label: " 因私出国申请（各单位）"
		},
		{
			name: "Component",
			Component: FlowByZjjgGoAbroad,
			icon: "plus",
			type: "primary",
			label: "因私出国申请（机关）"
		},
		{
			name: "Component",
			Component: FlowByZjPartyFeeUse,
			icon: "plus",
			type: "primary",
			label: " 党费使用申请"
		},
		{
			name: "Component",
			Component: FlowByZjAffairsApply,
			icon: "plus",
			type: "primary",
			label: " 事务申请流程"
		},
		{
			name: "Component",
			Component: FlowByFlowId2,
			icon: "plus",
			type: "primary",
			label: " 信息化立项申请流程"
		},
		{
			name: "Component",
			Component: FlowByFlowIdZj1,
			icon: "plus",
			type: "primary",
			label: " 信息化需求确认流程"
		},
		{
			name: "Component",
			Component: FlowByZjjwYongYin,
			icon: "plus",
			type: "primary",
			label: "纪委用印申请流程"
		},
		{
			name: "Component",
			Component: FlowByZjTripApply,
			icon: "plus",
			type: "primary",
			label: "出差申请"
		},
		{
			name: "Component",
			Component: FlowByZjWorkApply,
			icon: "plus",
			type: "primary",
			label: "加班申请"
		},
		{
			name: "Component",
			Component: FlowByzZjLeaveApply,
			icon: "plus",
			type: "primary",
			label: "请假申请"
		}, 
		{
			name: "Component",
			Component: FlowByWgsWxYongYin,
			icon: "plus",
			type: "primary",
			label: "五公司外携用印"
		}, 
		{
			name: "Component",
			Component: FlowByWgsYongYin,
			icon: "plus",
			type: "primary",
			label: "五公司用印"
		}, 
		{
			name: "Component",
			Component: FlowByLgsYongYin,
			icon: "plus",
			type: "primary",
			label: "六公司用印"
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
						zjYongYin: FlowFormByzjYongYin,
						zjYyOutSeal: FlowFormByzjYyOutSeal,
						zjDwYongYin: FlowFormByZjDwYongYin,
						zjGoAbroad: FlowFormByZjGoAbroad,
						zjPartyFeeUse:FlowByZjPartyFeeUse,
						zjAffairsApply: FlowByZjAffairsApply,
						flowId2: FlowByFlowId2,
						flowIdZj1: FlowByFlowIdZj1,
						zjjwYongYin: FlowByZjjwYongYin,
						zjTripApply: FlowByZjTripApply,
						zjWorkApply: FlowByZjWorkApply,
						zjLeaveApply: FlowByzZjLeaveApply,
						wgsWxYongYin: FlowByWgsWxYongYin,
						wgsYongYin: FlowByWgsYongYin,
						lgsYongYin: FlowByLgsYongYin,
						zjjgGoAbroad:FlowByZjjgGoAbroad
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
