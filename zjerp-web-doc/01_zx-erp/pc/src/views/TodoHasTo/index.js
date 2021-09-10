//公司内部请假申请
import React from "react";
import Apih5 from "qnn-apih5";
import QnnTable from "../modules/qnn-table";
import { RedoOutlined } from "@ant-design/icons";
// import { push } from 'react-router-redux';
import FlowByImportTaxCatchForm from "../MaterialsManagement/ImportTaxCatch/form";
import FlowByImportPriceLimitChangeForm from "../MaterialsManagement/ImportPriceLimitChange/form";
import FlowByPurchaseApplicationForm from "../TurnoverMaterialManagement/PurchaseApplication/form";
import FlowByWasteMaterialsManagementForm from "../TurnoverMaterialManagement/WasteMaterialsManagement/form";
import FlowByScrapForm from "../TurnoverMaterialManagement/Scrap/form";
import FlowByImportPriceLimitManageForm from "../MaterialsManagement/ImportPriceLimitManage/form";
import primalBuildContractForm from "../CountPay/PrimalBuildContract/form";
import primalBuildContractFormForPartCom from "../CountPay/PrimalBuildContract/formForPartCom";
import currentBuildContractForm from "../CountPay/CurrentBuildContract/form";
import currentBuildContractFormForHQ from "../CountPay/CurrentBuildContract/formHQ";
import currentBuildContractFormForPart from "../CountPay/CurrentBuildContract/formPart";
import MachineryManagementContractLeaseForm from "../ContractManagement/MachineryManagementContract/formLease";
import MachineryManagementContractPayForm from "../ContractManagement/MachineryManagementContract/formPay";
import MaterialManagementContractFrom from "../ContractManagement/MaterialManagementContract/form";
import CollectionOfEquipmentLeasingPriceLimitFrom from "../EquipmentManagement/CollectionOfEquipmentLeasingPriceLimit/form";
import SupplementaryAgreementToTheEquipmentContractForm from "../ContractManagement/SupplementaryAgreementToTheEquipmentContract/form";
import SupplementaryAgreementToTheEquipmentContractFormForPay from "../ContractManagement/SupplementaryAgreementToTheEquipmentContract/formPay";
import SupplementaryAgreementToSubsidiaryContractForm from "../ContractManagement/SupplementaryAgreementToSubsidiaryContract/form";
import MachineRentCountListForm from "../SettlementManagement/MachineRentCountList/form";
import MaterialPurchaseSettlementForm from "../SettlementManagement/MaterialPurchaseSettlement/form";
import MaterialLeasingSettlementForm from "../SettlementManagement/MaterialLeasingSettlement/form";
import CollateralContractForm from "../ContractManagement/CollateralContract/form";
import SupplementaryAgreementOnMaterialManagementForm from "../ContractManagement/SupplementaryAgreementOnMaterialManagement/form";
import AffiliatFinalStatementForm from "../SettlementManagement/AffiliatFinalStatement/form";
import ConstructionSchemeForm from "../ProjectMangerment/ConstructionScheme/form";
import OtherContractForm from "../ContractManagement/OtherContract/form";
import OtherSupplementaryAgreementsForm from "../ContractManagement/OtherSupplementaryAgreements/form";
import OtherSettlementForm from "../SettlementManagement/OtherSettlement/form";
import ProjectConstructionContractForm from "../ContractManagement/ProjectConstructionContract/FlowForm";
import SupplementaryAgreementForEngineeringConstructionForm from "../ContractManagement/SupplementaryAgreementForEngineeringConstruction/FlowForm";
import EngineeringSettlementForm from "../SettlementManagement/EngineeringSettlement/form";
import SubcontractingPriceLimitForm from "../ProjectMangerment/SubcontractingPriceLimit/form";
import QuarterlyCreditEvaluation from "../CreditEvaluation/QuarterlyCreditEvaluation/form";

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
		hasTodo: "HasTodoList",
	},

	mobileModel: "flow",
	// isShowRowSelect: false, //是否显示选择框  默认true
	fetchConfig: {
		apiName: (props) => {
			var url = props.match.url;
			var myPublic = props.myPublic.appInfo.mainModule;
			if (url === myPublic + "Closed") {
				return "getZxHasReadList";
			}
			if (url === myPublic + "Undocumented") {
				return "getZxReadList";
			}
			if (url === `${myPublic}TodoList`) {
				//待办
				return "getTodoList";
			} else {
				//已办
				return "getHasTodoList";
			}
		},
		otherParams: () => {
			let flowId = ["zxskmonthPurWorkId,", "zxCtContrCsjz,", "zbzxCtContrCsjz,", "zxCtContrDqjz,", "zxCtEqContrApply,", "SCContrApply,", "ZxCtSuppliesContrApply,", "zxEqEquipLimitPrice,", "zxCtEqContrSupply,", "ZxCtSuppliesContrApplySBCGB,", "zxSaEquipSettleAudit,", "FsSideAmagment,", "ZxCtSuppliesShopSettlement,", "ZxCtSuppliesLeaseSettlement,", "ZxFsReviewWorkFlow,", "FsSelementWorkFlow,", "ZxCtSuppliesContrReplenish,", "zxScGroupScheme,", "zxSkLimitPriceWorkId,", "zxCtOtherReviewApply,", "zxCtOtherSupplyAgreementReviewAp,", "zxSkPurchaseWorkId,", "zxSkLimitPriceAdjustWorkId,", "zxSkTurnOverScrapWorkId,", "zxSkWornOutWorkId,", "zxSaOtherEquipSettleReviewApply,", "psZxGcsgCtContrApply,", "bxZxGcsgCtContrApply,", "zxSaProjectSettleAudit,", "ZxCtSubContractLprice,", "ZxCrHalfYearCreditEvaAudit,", "zbzxCtContrCsdqjz,", "zxCtContrCsgsdqjz"];
			return {
				flowId: flowId.join(""),
			};
		},
	},
	antd: {
		rowKey: function(row) {
			//---row.主键id
			return row.workId;
		},
		size: "small",
		scroll: {
			y: document.documentElement.clientHeight * 0.6,
		},
	},
	drawerConfig: {
		// width: window.screen.width * 0.7,
		width: window.screen.width * 0.8,
		maskClosable: true,
	},
	paginationConfig: {
		position: "bottom",
	},
	firstRowIsSearch: true,
};

class index extends Apih5 {
	render() {
		const url = this.props.match.url;
		const myPublic = this.props.myPublic.appInfo.mainModule;
		// return <></>

		// 归档和未归档页面禁止编辑
		const disabled = url === `${myPublic}Closed` || url === `${myPublic}Undocumented`;

		return (
			<QnnTable
				{...this.props}
				fetch={this.props.myFetch}
				upload={this.props.myUpload}
				headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
				{...flowConfig}
				disabled={disabled}
				componentsKey={{
					RedoOutlined: <RedoOutlined />,
				}}
				// actionBtns={{
				// 	apiName: "getSysMenuBtn",
				// 	otherParams: (obj) => {
				// 		let props = obj.props;
				// 		let curRouteData = props.routerInfo.routeData[props.routerInfo.curKey];
				// 		return {
				// 			menuParentId: curRouteData._value,
				// 			tableField: "TodoList"
				// 		}
				// 	}
				// }}
				actionBtns={[
					{
						name: "diyDel",
						field: "diyDel",
						type:"danger",
						icon: "RedoOutlined", 
						label: "撤回流程（不可用）",
						onClick: "bind:huifu", 
						disabled:'bind:actionBtnNoSelected',
					},
				]}
				method={{
					actionBtnNoSelected:(arg)=>{
						return arg.qnnTableInstance.getSelectedRows().length !== 1;
					},
					huifu: async ({ selectedRows, qnnTableInstance }) => { 
						const rowData = selectedRows[0];
						const { success, message, code } = await this.props.myFetch("deleteFlowBiz", {
							flowId: rowData.flowId,
							workId: rowData.workId,
						});
						if (success) {
							qnnTableInstance.refresh();
						} else {
							this.apih5.errMsg(message, code);
						}
					},
				}}
				localized={() => {
					return {
						confirmDelete: "确认将选中的数据恢复到业务数据吗？",
					};
				}}
				// actionBtns={[
				// 	{
				// 		name: "del",
				// 		icon: "RedoOutlined",
				// 		type: "danger",
				// 		label: "恢复到业务数据",
				// 		// willExecute: "bind:willOpenFlowFormCom",
				// 		fetchConfig: {
				// 			apiName: "deleteFlowBiz",
				// 		},
				// 	},
				// ]}
				formConfig={[
					{
						isInForm: false,
						table: {
							title: "标题",
							dataIndex: "title",
							key: "title",
							fieldsConfig: {
								field: "keyword",
								type: "string",
							},
							tooltip: 80,
							width: 500,
							onClick: "Component",
							Component: {
								flowId: {
									zxSkPurchaseWorkId: FlowByPurchaseApplicationForm,
									zxSkLimitPriceAdjustWorkId: FlowByImportPriceLimitChangeForm,
									zxSkTurnOverScrapWorkId: FlowByScrapForm,
									zxSkWornOutWorkId: FlowByWasteMaterialsManagementForm,
									zxSkLimitPriceWorkId: FlowByImportPriceLimitManageForm,
									zxskmonthPurWorkId: FlowByImportTaxCatchForm,
									zxCtContrCsjz: primalBuildContractForm,
									zbzxCtContrCsjz: primalBuildContractFormForPartCom,
									zxCtContrDqjz: currentBuildContractForm,
									zbzxCtContrCsdqjz: currentBuildContractFormForHQ,
									zxCtContrCsgsdqjz: currentBuildContractFormForPart,
									zxCtEqContrApply: MachineryManagementContractLeaseForm,
									SCContrApply: MachineryManagementContractPayForm,
									ZxCtSuppliesContrApply: MaterialManagementContractFrom,
									zxEqEquipLimitPrice: CollectionOfEquipmentLeasingPriceLimitFrom,
									zxCtEqContrSupply: SupplementaryAgreementToTheEquipmentContractForm,
									ZxCtSuppliesContrApplySBCGB: SupplementaryAgreementToTheEquipmentContractFormForPay,
									zxSaEquipSettleAudit: MachineRentCountListForm,
									FsSideAmagment: SupplementaryAgreementToSubsidiaryContractForm,
									ZxCtSuppliesShopSettlement: MaterialPurchaseSettlementForm,
									ZxCtSuppliesLeaseSettlement: MaterialLeasingSettlementForm,
									ZxFsReviewWorkFlow: CollateralContractForm,
									FsSelementWorkFlow: AffiliatFinalStatementForm,
									ZxCtSuppliesContrReplenish: SupplementaryAgreementOnMaterialManagementForm,
									zxScGroupScheme: ConstructionSchemeForm,
									zxCtOtherReviewApply: OtherContractForm,
									zxCtOtherSupplyAgreementReviewAp: OtherSupplementaryAgreementsForm,
									zxSaOtherEquipSettleReviewApply: OtherSettlementForm,
									psZxGcsgCtContrApply: ProjectConstructionContractForm,
									bxZxGcsgCtContrApply: SupplementaryAgreementForEngineeringConstructionForm,
									zxSaProjectSettleAudit: EngineeringSettlementForm,
									ZxCtSubContractLprice: SubcontractingPriceLimitForm,
									ZxCrHalfYearCreditEvaAudit: QuarterlyCreditEvaluation,
								},
							},
						},
					},
					{
						isInForm: false,
						table: {
							noHaveSearchInput: true,
							title: "发起人",
							width: 100,
							dataIndex: "sendUserName",
							key: "sendUserName",
						},
					},
					{
						isInForm: false,
						table: {
							noHaveSearchInput: true,
							width: 100,
							title: "发起时间",
							dataIndex: "sendTime",
							key: "sendTime",
							format: "YYYY-MM-DD HH:mm:ss",
						},
					},
					{
						isInForm: false,
						table: {
							noHaveSearchInput: disabled ? true : false,
							title: "流程状态",
							width: 100,
							dataIndex: "flowStatus",
							key: "flowStatus",
							fieldsConfig: {
								field: "flowStatus",
								type: "select",
								placeholder: "请选择...",
								fetchConfig: {
									apiName: "getBaseCodeSelect",
									otherParams: {
										itemId: "liuChengZhuangTai",
									},
								},
								optionConfig: {
									label: "itemName",
									value: "itemId",
								},
							},
						},
					},
					// {
					// 	isInForm: false,
					// 	table: {
					// 		title: "操作",
					// 		dataIndex: "action",
					// 		showType: "tile",
					// 		width: 150,
					// 		btns: [
					// 			{
					// 				name: "diyDel",
					// 				icon: "RedoOutlined",
					// 				labelStyle: { color: "#ff4000" },
					// 				label: "恢复到业务数据",
					// 				onClick: "bind:huifu",
					// 			},
					// 		],
					// 	},
					// },
				]}
			/>
		);
	}
}

export default index;
