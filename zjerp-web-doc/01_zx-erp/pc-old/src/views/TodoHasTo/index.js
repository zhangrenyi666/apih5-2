//公司内部请假申请
import React, { Component } from "react";
import QnnTable from "../modules/qnn-table";
// import { push } from 'react-router-redux';
import FlowByImportTaxCatchForm from "../MaterialsManagement/ImportTaxCatch/form";
import FlowByImportPriceLimitChangeForm from "../MaterialsManagement/ImportPriceLimitChange/form";
import FlowByPurchaseApplicationForm from "../TurnoverMaterialManagement/PurchaseApplication/form";
import FlowByWasteMaterialsManagementForm from "../TurnoverMaterialManagement/WasteMaterialsManagement/form";
import FlowByScrapForm from "../TurnoverMaterialManagement/Scrap/form";
import FlowByImportPriceLimitManageForm from "../MaterialsManagement/ImportPriceLimitManage/form";
import primalBuildContractForm from '../CountPay/PrimalBuildContract/form';
import currentBuildContractForm from '../CountPay/CurrentBuildContract/form';
import MachineryManagementContractLeaseForm from '../ContractManagement/MachineryManagementContract/formLease';
import MachineryManagementContractPurchaseForm from '../ContractManagement/MachineryManagementContract/formPurchase';
import MaterialManagementContractFrom from '../ContractManagement/MaterialManagementContract/form';
import CollectionOfEquipmentLeasingPriceLimitFrom from '../EquipmentManagement/CollectionOfEquipmentLeasingPriceLimit/form';
import SupplementaryAgreementToTheEquipmentContractForm from '../ContractManagement/SupplementaryAgreementToTheEquipmentContract/form';
import SupplementaryAgreementToSubsidiaryContractForm from '../ContractManagement/SupplementaryAgreementToSubsidiaryContract/form';
import MachineRentCountListForm from '../SettlementManagement/MachineRentCountList/form';
import MaterialPurchaseSettlementForm from '../SettlementManagement/MaterialPurchaseSettlement/form';
import MaterialLeasingSettlementForm from '../SettlementManagement/MaterialLeasingSettlement/form';
import CollateralContractForm from '../ContractManagement/CollateralContract/form';
import SupplementaryAgreementOnMaterialManagementForm from '../ContractManagement/SupplementaryAgreementOnMaterialManagement/form';
import AffiliatFinalStatementForm from '../SettlementManagement/AffiliatFinalStatement/form';
import ConstructionSchemeForm from '../ProjectMangerment/ConstructionScheme/form';
import OtherContractForm from '../ContractManagement/OtherContract/form';
import OtherSupplementaryAgreementsForm from '../ContractManagement/OtherSupplementaryAgreements/form';
import OtherSettlementForm from '../SettlementManagement/OtherSettlement/form';
import ProjectConstructionContractForm from '../ContractManagement/ProjectConstructionContract/FlowForm';
import SupplementaryAgreementForEngineeringConstructionForm from '../ContractManagement/SupplementaryAgreementForEngineeringConstruction/FlowForm';
import EngineeringSettlementForm from '../SettlementManagement/EngineeringSettlement/form'

const flowConfig = {
	//流程专属配置
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
			let flowId = [
				'zxskmonthPurWorkId,',
				'zxCtContrCsjz,',
				'zxCtContrDqjz,',
				'zxCtEqContrApply,',
				'SCContrApply,',
				'ZxCtSuppliesContrApply,',
				'zxEqEquipLimitPrice,',
				'zxCtEqContrSupply,',
				'zxSaEquipSettleAudit,',
				'FsSideAmagment,',
				'ZxCtSuppliesShopSettlement,',
				'ZxCtSuppliesLeaseSettlement,',
				'ZxFsReviewWorkFlow,',
				'FsSelementWorkFlow,',
				'ZxCtSuppliesContrReplenish,',
				'zxScGroupScheme,',
				'zxSkLimitPriceWorkId,',
				'zxCtOtherReviewApply,',
				'zxCtOtherSupplyAgreementReviewAp,',
				'zxSkPurchaseWorkId,',
				'zxSkLimitPriceAdjustWorkId,',
				'zxSkTurnOverScrapWorkId,',
				'zxSkWornOutWorkId,',
				'zxSaOtherEquipSettleReviewApply,',
				'psZxGcsgCtContrApply,',
				'bxZxGcsgCtContrApply,',
				'zxSaProjectSettleAudit'
			]
			return {
				flowId: flowId.join('')
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
		width: window.screen.width * 0.7,
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
									flowId: {
										zxSkPurchaseWorkId: FlowByPurchaseApplicationForm,
										zxSkLimitPriceAdjustWorkId: FlowByImportPriceLimitChangeForm,
										zxSkTurnOverScrapWorkId: FlowByScrapForm,
										zxSkWornOutWorkId: FlowByWasteMaterialsManagementForm,
										zxSkLimitPriceWorkId: FlowByImportPriceLimitManageForm,
										zxskmonthPurWorkId: FlowByImportTaxCatchForm,
										zxCtContrCsjz: primalBuildContractForm,
										zxCtContrDqjz: currentBuildContractForm,
										zxCtEqContrApply: MachineryManagementContractLeaseForm,
										SCContrApply:MachineryManagementContractPurchaseForm,
										ZxCtSuppliesContrApply: MaterialManagementContractFrom,
										zxEqEquipLimitPrice: CollectionOfEquipmentLeasingPriceLimitFrom,
										zxCtEqContrSupply: SupplementaryAgreementToTheEquipmentContractForm,
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
