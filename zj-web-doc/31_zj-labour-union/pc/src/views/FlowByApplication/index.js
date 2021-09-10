//公司内部请假申请
import React, { Component } from "react";
import QnnTable from "../modules/qnn-table";
import { push } from 'react-router-redux';
import FlowFormByApplication from '../GroupAcase/form';
import FlowFormByProposal from '../DepartmentToCarryOutThe/form';
const flowConfig = {
	//流程专属配置
	workFlowConfig: {
        //后台定的字段
        title: ["applicationName", "立案"], //标题字段
        apiNameByAdd: "updateZjLabourUnionFlowApplicationFirstByFlow",
        apiNameByUpdate: "updateZjLabourUnionFlowApplicationSecondByFlow",
        apiNameByGet: "getZjLabourUnionFlowApplicationDetailsByFlow",
        flowId: "zjLabourUnionFlowApplication",

        todo: "FlowByApplicationAwait",
        hasTodo: "FlowByApplicationOver"
    },

	mobileModel: "flow",
	isShowRowSelect: false,//是否显示选择框  默认true
	fetchConfig: {
		apiName: props => {
			var url = props.match.url;
			var myPublic = props.myPublic.appInfo.mainModule;
			if (url === `${myPublic}FlowByApplicationAwait`) {
				//待办
				return "getTodoList";
			} else {
				//已办
				return "getHasTodoList";
			}
		},
		otherParams: {
			flowId: "zjLabourUnionFlowApplication,zjLabourUnionProposal"
		}
	},
	antd: {
		rowKey: function (row) {
			//---row.主键id
			return row.workId;
		},
		size: 'small',
	},
	drawerConfig: {
		width: "1000px",
	},
	paginationConfig: {
		position: "bottom"
	}
};

class index extends Component {
	
	render() {
		const { dispatch, myPublic: { appInfo: { mainModule } } } = this.props;
		return (
			<QnnTable
				{...this.props}
				fetch={this.props.myFetch} 
		 		upload={this.props.myUpload}
				headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
				{...flowConfig}
				formConfig={
					[
						{
							isInForm: false,
							table: {
								title: "标题",
								dataIndex: "title",
								key: "title",
								onClick: "Component",
								tooltip:15,
								Component: {
									flowId: {
										zjLabourUnionFlowApplication:FlowFormByApplication,
										zjLabourUnionProposal:FlowFormByProposal
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
								key: "flowStatus",
							}
						},
						{
							isInForm: false,
							table: {
								title: "操作",
								dataIndex: 'action',
								key: 'action',
								align: "center",
								showType: "tile",
								width: 100,
								btns:[
									{
										name: 'diy',
										render: function (rowData) {
											if(rowData.rowData.flowId === 'zjLabourUnionFlowApplication'){
												return '<a>立案明细</a>'
											}else {
												return ''
											}
										},
										onClick:(obj)=>{
											if(obj.rowData.flowId === 'zjLabourUnionFlowApplication'){
												dispatch(push(`${mainModule}CaseListOfTheGroup/0/${obj.rowData.workId}/${obj.rowData.flowStatus === '退回' ? '3' : '8'}`));
											}
										}
									}
								]
							}
						},
					]
				}
			/>
		);
	}
}

export default index;
