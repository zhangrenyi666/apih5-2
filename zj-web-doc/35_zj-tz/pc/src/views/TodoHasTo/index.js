//公司内部请假申请
import React, { Component } from "react";
import QnnTable from "../modules/qnn-table";
import { push } from 'react-router-redux';
import FlowByLifeCyclePlanningForm from "../LifeCyclePlanning/form";
import FlowByLifeCycleChangeForm from "../LifeCycleChange/form";
import FlowByWorkPlanningForm from "../WorkPlanning/form";
import FlowByThreeMeetingMangeForm from "../ThreeMeetingMange/form";
import FlowByThreeMeetingMangeShareholderForm from "../ThreeMeetingMangeShareholder/form";
import FlowByThreeMeetingMangeSupervisorsForm from "../ThreeMeetingMangeSupervisors/form";
import FlowByPlaceForm from "../Place/form";
import FlowByMonthlyReportForm from "../MonthlyReport/form";
import FlowByMbalanceSheetForm from "../MbalanceSheet/form";
import FlowByIncomeStatementForm from "../IncomeStatement/form";
import FlowByCashFlowStatementForm from "../CashFlowStatement/form";
import FlowByDesignChangeManageForm from "../DesignChangeManage/form";
import FlowByInvestmentScaleControlForm from "../InvestmentScaleControl/form";
import FlowByContractAnalysisForm from "../ContractAnalysis/form";

const flowConfig = {
	//流程专属配置
	workFlowConfig: {
        //后台定的字段
        title: ["applyUnitId","title_sendTime",""], //标题字段
        apiNameByAdd: "addZtEndMonthChange",
        apiNameByUpdate: "updateZtEndMonthChange",
        apiNameByGet: "getZtEndMonthChangeDetail",
        flowId: "zEndMonthChange",

        //移动端需要用到
        //待办已办切换路由
        todo: "TodoHasTo",
        hasTodo: "TodoHasToq"
    },

	mobileModel: "flow",
	isShowRowSelect: false,//是否显示选择框  默认true
	fetchConfig: {
		apiName: props => {
			var url = props.match.url;
			var myPublic = props.myPublic.appInfo.mainModule;
			if (url === `${myPublic}TodoHasTo`) {
				//待办
				return "getTodoList";
			} else {
				//已办
				return "getHasTodoList";
			}
		},
		otherParams: {
			flowId: "zjTzLifeCycle,zjTzLifeCycleChange,zjTzRunScheme,zjTzThreeShareholder,zjTzThreeDirector,zjTzThreeSupervisor,zjTzPolicyLocal,zjTzFinanceMonth,zjTzDebt,zjTzProfit,zjTzCash,zjTzDesignChangeRecord,zjTzSizeControlRecord,zjTzPppTerm,zjTzLifeCycleChange3,zjTzLifeCycle3,zjTzRunScheme3"
		}
	},
	antd: {
		rowKey: function (row) {
			//---row.主键id
			return row.workId;
		},
		size: 'small',
		scroll:{
            y:document.documentElement.clientHeight*0.6
        }
	},
	drawerConfig: {
		width: "1000px",
		maskClosable: true
	},
	paginationConfig: {
		position: "bottom"
	},
	firstRowIsSearch: true,
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
				formConfig={
					[
						{
							isInForm: false,
							table: {
								title: "标题",
								width:300,
								dataIndex: "title",
								tooltip:30,
								key: "title",
								fieldsConfig: {
                                    field:"keyword",
                                    type: "string"
                                },
								onClick: "Component",
								Component: {
									flowId: {
										zjTzLifeCycle: FlowByLifeCyclePlanningForm,
										zjTzLifeCycle3: FlowByLifeCyclePlanningForm,
										zjTzLifeCycleChange: FlowByLifeCycleChangeForm,
										zjTzLifeCycleChange3: FlowByLifeCycleChangeForm,
										zjTzRunScheme: FlowByWorkPlanningForm,
										zjTzRunScheme3: FlowByWorkPlanningForm,
										zjTzThreeShareholder: FlowByThreeMeetingMangeForm,
										zjTzThreeDirector:FlowByThreeMeetingMangeShareholderForm,
										zjTzThreeSupervisor: FlowByThreeMeetingMangeSupervisorsForm,
										zjTzPolicyLocal: FlowByPlaceForm,
										zjTzFinanceMonth:FlowByMonthlyReportForm,
										zjTzDebt: FlowByMbalanceSheetForm,
										zjTzProfit: FlowByIncomeStatementForm,
										zjTzCash: FlowByCashFlowStatementForm,
										zjTzDesignChangeRecord: FlowByDesignChangeManageForm,
										zjTzSizeControlRecord: FlowByInvestmentScaleControlForm,
										zjTzPppTerm:FlowByContractAnalysisForm
									  }
								}
							}
						},
						{
							isInForm: false,
							table: {
								noHaveSearchInput: true,
								title: "发起人",
								width: 200,
								dataIndex: "sendUserName",
								key: "sendUserName"
							}
						},
						{
							isInForm: false,
							table: {
								noHaveSearchInput: true,
								width: 200,
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
								width: 160,
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
						},
						{
                            isInForm: false,
                            table: {
                                title: "操作",
                                fixed: 'right',
                                dataIndex: 'action',
                                key: 'action',
                                align: "center",
                                noHaveSearchInput: true,
                                showType: "tile",
                                width: 120,
                                btns: [
                                    {
                                        name: 'PolicyDetail',
										render: (rowData) => {
											if (rowData.rowData.flowId === 'zjTzThreeDirector' || rowData.rowData.flowId === 'zjTzThreeShareholder' || rowData.rowData.flowId === 'zjTzThreeSupervisor' || rowData.rowData.flowId === 'zjTzDebt' || rowData.rowData.flowId === 'zjTzProfit' || rowData.rowData.flowId === 'zjTzCash' || rowData.rowData.flowId === 'zjTzPppTerm') {
												return '<a>明细</a>'; 
											}else {
												return ''
											}
                                        },
                                        onClick: (obj) => {
											const { mainModule } = obj.props.myPublic.appInfo;
											const { myFetch } = obj.props;
											const { workId } = obj.rowData;
											if (obj.rowData.flowId === 'zjTzThreeDirector') {//董事会
												myFetch('getZjTzThreeDirectorDetails',{workId:workId}).then(
													({ success,message,data }) => {
														if (success) {
															
															obj.props.dispatch(
																push(`${mainModule}ThreeMeetingMangeShareholderDetail/${workId}/${data.apih5FlowStatus}/${1}`)
															)
														} else {}
													}
												);
												
											}
											if (obj.rowData.flowId === 'zjTzPppTerm') {//PPP合同分析
												// statusId 不存在
												myFetch('getZjTzPppTermDetails',{workId:workId}).then(
													({ success,message,data }) => {
														if (success) {
															obj.props.dispatch(
																push(`${mainModule}ContractAnalysisDetail/${workId}/${data.statusId}/${data.apih5FlowStatus}/${1}`)
															)
														} else {}
													}
												);
											}
											if (obj.rowData.flowId === 'zjTzThreeShareholder') {//股东会
												myFetch('getZjTzThreeShareholderDetails',{workId:workId}).then(
													({ success,message,data }) => {
														if (success) {
															obj.props.dispatch(
																push(`${mainModule}ThreeMeetingMangeDetail/${workId}/${data.apih5FlowStatus}/${1}`)
															)
														} else {}
													}
												);
												
											}
											if (obj.rowData.flowId === 'zjTzThreeSupervisor') {//监事会
												myFetch('getZjTzThreeSupervisorDetails',{workId:workId}).then(
													({ success,message,data }) => {
														if (success) {
															obj.props.dispatch(
																push(`${mainModule}ThreeMeetingMangeSupervisorsDetail/${workId}/${data.apih5FlowStatus}/${1}`)
															)
														} else {}
													}
												);
												
											}
											if (obj.rowData.flowId === 'zjTzDebt') {//资产负债情况表
												myFetch('getZjTzDebtDetails',{workId:workId}).then(
													({ success,message,data }) => {
														if (success) {
															obj.props.dispatch(
																push(`${mainModule}MBalanceSheetDetail/${workId}/${data.apih5FlowStatus}/${1}`)
															)
														} else {}
													}
												);
												
											}
											if (obj.rowData.flowId === 'zjTzProfit') {//利润表
												myFetch('getZjTzProfitDetails',{workId:workId}).then(
													({ success,message,data }) => {
														if (success) {
															obj.props.dispatch(
																push(`${mainModule}IncomeStatementDetail/${workId}/${data.apih5FlowStatus}/${1}`)
															)
														} else {}
													}
												);
											}
											if (obj.rowData.flowId === 'zjTzCash') {//现金流量表
												myFetch('getZjTzCashDetails',{workId:workId}).then(
													({ success,message,data }) => {
														if (success) {
															obj.props.dispatch(
																push(`${mainModule}CashFlowStatementDetail/${workId}/${data.apih5FlowStatus}/${1}`)
															)
														} else {}
													}
												);
											}
                                        }
                                    }
                                ]
                            }
                        }
					]
				}
			/>
		);
	}
}

export default index;
