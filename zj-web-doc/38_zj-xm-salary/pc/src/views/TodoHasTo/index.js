//公司内部请假申请
import React, { Component } from "react";
import QnnTable from "../modules/qnn-table";
// import { push } from 'react-router-redux';

import FlowByHiredToSubmitForApprovalForm from "../workManagement/HiredToSubmitForApproval/form"	// 录用报审
import FlowByPostSalaryReportExaminationApprovalForm from "../workManagement/PostSalaryReportExaminationApproval/form"	// 岗薪报审审批
import FlowByReportExaminationApprovalForm from "../workManagement/ReportExaminationApproval/form"	// 报备报审审批
import FlowByEmployeeTransferReportApprovalForm from "../workManagement/EmployeeTransferReportApproval/form"	// 员工转岗报审审批
import FlowByReserveCadresReportExaminationApprovalForm from "../workManagement/ReserveCadresReportExaminationApproval/form"	// 后备干部报审审批
import FlowByProjectOverstaffedApprovalForm from "../workManagement/ProjectOverstaffedApproval/form"	// 项目超员审批
import FlowByProjectOverstaffedApprovalFormTwo from "../workManagement/ProjectOverstaffedApproval/formTwo"	// 项目超员审批

const flowConfig = {
	//流程专属配置
	editDocCdnAddress: window.configs.ntkoAddress,
	workFlowConfig: {
		//后台定的字段
		title: ["applyUnitId", "title_sendTime", ""], //标题字段
		apiNameByAdd: "addZtEndMonthChange",
		apiNameByUpdate: "updateZtEndMonthChange",
		apiNameByGet: "getZtEndMonthChangeDetail",
		flowId: "zxskmonthPurWorkId",

		//移动端需要用到
		//待办已办切换路由
		todo: "TodoList",
		hasTodo: "HasTodoList"
	},

	mobileModel: "flow",
	isShowRowSelect: false,//是否显示选择框  默认true
	fetchConfig: {
		apiName: props => {
			var url = props.match.url;
			var myPublic = props.myPublic.appInfo.mainModule;
			if (url === `${myPublic}TodoList`) {
				//待办
				return "getTodoList";
			} else {
				//已办
				return "getHasTodoList";
			}
		},
		otherParams: () => {
			/*
				录聘用:		employApproval
				岗薪:		salaryApproval
				报备:		reportApproval
				员工转岗:	userPositionApproval
				后备干部: 	reserveCadreApproval
				项目超员: 	projectOverstaffingApproval

							 ↑↓
				
				录聘用:    employApproval --> rylpApply

				岗薪:    salaryApproval --> gxbsApply

				报备:    reportApproval --> baobeibaoshenApply

				转岗:    userPositionApproval --> ygzgApplu

				后备:    reserveCadreApproval --> hbgbbsApply

				超员1:    projectOverstaffingApproval --> cyspApply

				超员2:                                --> CopycyspApply


			*/
			let flowId = [
				'rylpApply',
				'gxbsApply',
				'baobeibaoshenApply',
				'userPositionApproval',
				'ygzgApplu',
				'hbgbbsApply',
				'cyspApply',
				'CopycyspApply',
			]
			return {
				flowId: flowId.join(',')
			}

		}
	},
	antd: {
		rowKey: function (row) {
			//---row.主键id
			return row.workId;
		},
		size: 'small',
		scroll: {
			y: document.documentElement.clientHeight * 0.6
		}
	},
	drawerConfig: {
		// width: window.screen.width * 0.7,
		width: window.screen.width * 0.8,
		maskClosable: true
	},
	paginationConfig: {
		position: "bottom"
	},
	firstRowIsSearch: true,
};

class index extends Component {
	render() {
		// return <></>
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
								fieldsConfig: {
									field: "keyword",
									type: "string"
								},
								tooltip: 80,
								width: '50%',
								onClick: "Component",
								Component: {
									/*
										录聘用:		employApproval
										岗薪:		salaryApproval
										报备:		reportApproval
										员工转岗:	userPositionApproval
										后备干部: 	reserveCadreApproval
										项目超员: 	projectOverstaffingApproval
										
													↑↓
										
										录聘用:    employApproval --> rylpApply

										岗薪:    salaryApproval --> gxbsApply

										报备:    reportApproval --> baobeibaoshenApply

										转岗:    userPositionApproval --> ygzgApplu

										后备:    reserveCadreApproval --> hbgbbsApply

										超员1:    projectOverstaffingApproval --> cyspApply

				 						超员2:                                --> CopycyspApply
									*/
									flowId: {
										rylpApply: FlowByHiredToSubmitForApprovalForm,	// 录聘用
										gxbsApply: FlowByPostSalaryReportExaminationApprovalForm,	// 岗薪
										baobeibaoshenApply: FlowByReportExaminationApprovalForm,	// 报备
										ygzgApplu: FlowByEmployeeTransferReportApprovalForm,	// 员工转岗
										hbgbbsApply: FlowByReserveCadresReportExaminationApprovalForm,	// 后备干部
										cyspApply: FlowByProjectOverstaffedApprovalForm,	// 超员1
										CopycyspApply:FlowByProjectOverstaffedApprovalFormTwo // 超员2
									}
								}
							}
						},
						{
							isInForm: false,
							table: {
								noHaveSearchInput: true,
								title: "发起人",
								width: '15%',
								dataIndex: "sendUserName",
								key: "sendUserName"
							}
						},
						{
							isInForm: false,
							table: {
								noHaveSearchInput: true,
								width: '20%',
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
								width: '15%',
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
						},
						// {
						//     isInForm: false,
						//     table: {
						//         title: "操作",
						//         fixed: 'right',
						//         dataIndex: 'action',
						//         key: 'action',
						//         align: "center",
						//         noHaveSearchInput: true,
						//         showType: "tile",
						//         width: 120,
						//         btns: [
						//             {
						//                 name: 'PolicyDetail',
						// 				render: (rowData) => {
						// 					if (rowData.rowData.flowId === 'zjTzThreeDirector') {
						// 						return '<a>明细</a>'; 
						// 					}else {
						// 						return ''
						// 					}
						//                 },
						//                 onClick: (obj) => {
						// 					const { mainModule } = obj.props.myPublic.appInfo;
						// 					const { myFetch } = obj.props;
						// 					const { workId } = obj.rowData;
						// 					if (obj.rowData.flowId === 'zjTzThreeDirector') {//董事会
						// 						myFetch('getZjTzThreeDirectorDetails',{workId:workId}).then(
						// 							({ success,message,data }) => {
						// 								if (success) {

						// 									obj.props.dispatch(
						// 										push(`${mainModule}ThreeMeetingMangeShareholderDetail/${workId}/${data.apih5FlowStatus}/${1}`)
						// 									)
						// 								} else {}
						// 							}
						// 						);

						// 					}
						//                 }
						//             }
						//         ]
						//     }
						// }
					]
				}
			/>
		);
	}
}

export default index;
