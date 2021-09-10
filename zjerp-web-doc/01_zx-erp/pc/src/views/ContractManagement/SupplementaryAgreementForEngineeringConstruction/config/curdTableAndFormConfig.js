//跟表格和表单的配置

import React from "react";
import { EditOutlined, PullRequestOutlined } from "@ant-design/icons";
import FilesForSupplementaryAgreements from '../../filesForSupplementaryAgreements';

const shortLabel = {
	labelCol: {
		sm: { span: 3 },
	},
	wrapperCol: {
		sm: { span: 21 },
	},
};
export default {
	antd: {
		rowKey: "ctContrApplyId",
		size: "small",
	},
	fetchConfig: {
		apiName: "bxGetZxGcsgCtContrApplyList",
		otherParams: "bind:getUserInfo",
	},
	// data: [
	//     { ctContrApplyId: "001", contractNo: "001", contractName: "001" }
	// ],
	drawerConfig: {
		width: "100%",
	},
	formItemLayout: {
		labelCol: {
			sm: { span: 9 },
		},
		wrapperCol: {
			sm: { span: 15 },
		},
	},
	actionBtns: {
		apiName: "getSysMenuBtn",
		otherParams: (obj) => {
			let props = obj.props;
			let curRouteData = props.routerInfo.routeData[props.routerInfo.curKey];
			return {
				menuParentId: curRouteData._value,
				tableField: "SupplementaryAgreementForEngineeringConstruction",
			};
		},
	},
	// actionBtns: [
	//     {
	//         "tableField": "SupplementaryAgreementForEngineeringConstruction",
	//         "tableName": "工程施工补充协议",
	//         "name": "add",
	//         "icon": "plus",
	//         "type": "primary",
	//         "label": "新增",
	//         "formBtns": [
	//             {
	//                 "name": "cancel",
	//                 "type": "dashed",
	//                 "label": "取消"
	//             },
	//             {
	//                 "name": "submit",
	//                 "type": "primary",
	//                 "label": "暂存",
	//                 "field": "temporary",
	//                 "fetchConfig": {
	//                     "apiName": "psAddZxGcsgCtContrApply",
	//                     "otherParams": "bind:getUserInfo::-2"
	//                 },
	//                 "hide": "bind:addSaveHide"
	//             },
	//             {
	//                 "name": "submit",
	//                 "type": "primary",
	//                 "label": "保存",
	//                 "fetchConfig": {
	//                     "apiName": "bxAddZxGcsgCtContrApply",
	//                     "otherParams": "bind:getUserInfo::-1"
	//                 }
	//             }
	//         ]
	//     },
	//     {
	//         "tableField": "SupplementaryAgreementForEngineeringConstruction",
	//         "tableName": "工程施工补充协议",
	//         "name": "exportData",
	//         "icon": "ExportOutlined",
	//         "type": "primary",
	//         "label": "导出",
	//         "onClick": "bind:exportData::bxExportZxGcsgCtContrApplyExcel"
	//     },
	//     {
	//         "tableField": "SupplementaryAgreementForEngineeringConstruction",
	//         "tableName": "工程施工补充协议",
	//         "name": "edit",
	//         "icon": "edit",
	//         "type": "primary",
	//         "label": "修改",
	//         "formBtns": [
	//             {
	//                 "name": "cancel",
	//                 "type": "dashed",
	//                 "label": "取消"
	//             },
	//             {
	//                 "name": "submit",
	//                 "type": "primary",
	//                 "label": "转正式数据",
	//                 "field": "toOfficialBtn",
	//                 "fetchConfig": {
	//                     "apiName": "psUpdateZxGcsgCtContrApplyByTemp",
	//                     "otherParams": "bind:getUserInfo"
	//                 },
	//                 "hide": "bind:toOfficialBtnHide"
	//             },

	//             {
	//                 "name": "submit",
	//                 "type": "primary",
	//                 "label": "保存",
	//                 "fetchConfig": {
	//                     "apiName": "bxUpdateZxGcsgCtContrApply"
	//                 }
	//             }
	//         ]
	//     },

	//     {
	//         "tableField": "SupplementaryAgreementForEngineeringConstruction",
	//         "tableName": "工程施工补充协议",
	//         "name": "Component",
	//         "icon": "plus",
	//         "type": "primary",
	//         "label": "评审申请",
	//         "disabled": "bind:_actionBtnNoSelected",
	//         "Component": "FlowFormCom",
	//         "willExecute": "bind:willOpenFlowFormCom"
	//     },
	//     {
	//         "tableField": "SupplementaryAgreementForEngineeringConstruction",
	//         "tableName": "工程施工补充协议",
	//         "name": "Component",
	//         "icon": "HistoryOutlined",
	//         "Component": "Qsjdcx",
	//         "type": "primary",
	//         "label": "进度查询",
	//         "disabled": "bind:jinDuChaXunDisabled"
	//     },
	//     {
	//         "tableField": "SupplementaryAgreementForEngineeringConstruction",
	//         "tableName": "工程施工补充协议",
	//         "Component": "DetailForm"
	//         , "disabled": "bind:disabledFunDetail"
	//         , "drawerTitle": "详细"
	//         , "label": "详细"
	//         , "name": "Component"
	//         , "tableField": "ProjectConstructionContract"
	//         , "tableName": "物资合同评审"
	//         , "type": "primary"
	//     },
	//     {
	//         "tableField": "SupplementaryAgreementForEngineeringConstruction",
	//         "tableName": "工程施工补充协议",
	//         "name": "del",
	//         "icon": "delete",
	//         "type": "danger",
	//         "label": "删除",
	//         "fetchConfig": {
	//             "apiName": "batchDeleteUpdateZxGcsgCtContrApply"
	//         }
	//     }
	// ],
	formConfig: [
		{
			isInTable: false,
			form: {
				field: "ctContrApplyId",
				type: "string",
				hide: true,
			},
		},
		// {
		//     isInTable: false,
		//     form: {
		//         field: 'ctContractId',
		//         type: 'string',
		//         hide: true
		//     }
		// },

		{
			table: {
				title: "补充协议编号",
				dataIndex: "contractNo",
				filter: true,
				fixed: "left",
				width: 280,
				onClick: "detail",
			},
			form: {
				type: "string",
				placeholder: "请输入",
				editDisabled: true,
				addDisabled: true,
				required:true,
				spanForm: 8,
			},
		},

		{
			table: {
				title: "补充协议名称",
				dataIndex: "agreementName",
				filter: true,
				width: 150,
			},
			form: {
				type: "string",
				required: true,
				spanForm: 8,
			},
		},

		{
			isInTable: false,
			form: {
				field: "contractName",
				type: "string",
				label: "合同名称",
				span: 8,
				addShow: false,
				editShow: true,
				detailShow: true,
				editDisabled: true,
			},
		},
		{
			table: {
				title: "合同名称",
				dataIndex: "contractName",
				filter: true,
				width: 150,
			},
			form: {
				field: "ctContractId",
				// type: 'selectByPaging',
				type: "selectByQnnTable",
				required: true,
				editDisabled: true,
				addDisabled: false,
				editShow: false,
				detailShow: false,
				spanForm: 8,
				// fetchConfig: {
				//     apiName: "bxGetZxGcsgCtContractSelect"
				// },
				dropdownMatchSelectWidth: 700,
				qnnTableConfig: {
					searchFormColNum: 2,
					rowKey: "ctContractId",
					fetchConfig: {
						apiName: "bxGetZxGcsgCtContractSelect",
						otherParams: (arg) => {
							return {
								orgID: arg.props.qnnFormProps?.funcCallBackParams?.getOrgId?.(),
							};
						},
					},
					formConfig: [
						{
							isInSearch: true,
							table: {
								width: 200,
								title: "合同名称",
								dataIndex: "contractName",
							},
							form: {
								type: "string",
							},
						},
						{
							isInSearch: true,
							table: {
								width: 300,
								title: "合同编号",
								dataIndex: "contractNo",
							},
							form: {
								type: "string",
							},
						},
					],
				},
				optionConfig: {
					label: "contractName",
					value: "ctContractId",

					// 搜索时候给接口的key, 默认使用 optionConfig.label
					searchKey: "keywords",
					linkageFields: {
						contractName: "contractName",
					},
				},
				onChange: "bind:contractNameChange",
			},
		},

		{
			table: {
				title: "合同类型",
				dataIndex: "contractType",
				width: 120,
				type: "select",
			},
			form: {
				type: "select",
				editDisabled: true,
				addDisabled: true,
				initialValue: "P2C",
				spanForm: 8,
				optionData: [{ label: "工程施工类补充协议", value: "P2C" }],
			},
		},
		{
			table: {
				title: "甲方名称",
				// dataIndex: 'firstID',
				dataIndex: "firstName",
				width: 150,
				filter: true,
			},
			form: {
				type: "string",
				// type: 'selectByPaging',
				// optionConfig: {
				//     label: "orgName",
				//     value: "orgID",
				// },
				// fetchConfig: {
				//     apiName: "getZxCtContractList",
				// },
				spanForm: 8,
				onChange: "bind:firstIDChange",
				editDisabled: true,
				addDisabled: true,
			},
		},
		{
			table: {
				title: "乙方名称",
				// dataIndex: 'secondID',
				dataIndex: "secondName",
				width: 150,
			},
			form: {
				type: "string",
				spanForm: 8,
				// type: 'selectByPaging',
				// fetchConfig: {
				//     apiName: "getZxCrCustomerExtAttrEngineeringList"
				// },
				// optionConfig: {
				//     label: "customerName",
				//     value: "zxCrCustomerExtAttrId",
				// },
				editDisabled: true,
				addDisabled: true,
			},
		},
		{
			table: {
				title: "合同签订人",
				dataIndex: "agent",
				width: 120,
			},
			form: {
				type: "string",
				spanForm: 8,
			},
		},
		{
			table: {
				title: "含税合同金额（万元）",
				dataIndex: "contractCost",
				width: 120,
			},
			form: {
				type: "number",
				initialValue: 0,
				editDisabled: true,
				addDisabled: true,
				precision: 6, //数值精度
				spanForm: 8,
			},
		},
		{
			isInTable: false,
			table: {
				title: "不含税合同金额（万元）",
				dataIndex: "contractCostNoTax",
				width: 120,
			},
			form: {
				type: "number",
				initialValue: 0,
				editDisabled: true,
				addDisabled: true,
				precision: 6, //数值精度
				spanForm: 8,
			},
		},
		{
			isInTable: false,
			table: {
				title: "合同税额",
				dataIndex: "contractCostTax",
				width: 100,
			},
			form: {
				type: "number",
				initialValue: 0,
				editDisabled: true,
				addDisabled: true,
				spanForm: 8,
			},
		},
		{
			table: {
				title: "本期含税增减金额（万元）",
				dataIndex: "currentTaxAmount",
				width: 150,
			},
			form: {
				type: "number",
				initialValue: 0,
				editDisabled: true,
				addDisabled: true,
				precision: 6, //数值精度
				spanForm: 8,
			},
		},
		{
			isInTable: false,
			table: {
				title: "本期不含税增减金额（万元）",
				dataIndex: "pp4NoTax",
				width: 150,
			},
			form: {
				type: "number",
				initialValue: 0,
				editDisabled: true,
				addDisabled: true,
				precision: 6, //数值精度
				spanForm: 8,
			},
		},
		{
			isInTable: false,
			table: {
				title: "本期递减税额",
				dataIndex: "pp4Tax",
				width: 150,
			},
			form: {
				type: "number",
				initialValue: 0,
				editDisabled: true,
				addDisabled: true,
				precision: 6, //数值精度
				spanForm: 8,
			},
		},
		{
			table: {
				title: "变更后含税金额",
				dataIndex: "alterContractSum",
				width: 150,
			},
			form: {
				type: "number",
				initialValue: 0,
				editDisabled: true,
				addDisabled: true,
				precision: 6, //数值精度
				spanForm: 8,
			},
		},
		{
			isInTable: false,
			table: {
				title: "变更后不含税金额",
				dataIndex: "alterContractSumNoTax",
				width: 150,
			},
			form: {
				type: "number",
				initialValue: 0,
				editDisabled: true,
				addDisabled: true,
				precision: 6, //数值精度
				spanForm: 8,
			},
		},
		{
			isInTable: false,
			table: {
				title: "变更后税额",
				dataIndex: "alterContractSumTax",
				width: 150,
			},
			form: {
				type: "number",
				initialValue: 0,
				editDisabled: true,
				addDisabled: true,
				precision: 6, //数值精度
				spanForm: 8,
			},
		},

		{
			table: {
				title: "是否抵扣",
				dataIndex: "isDeduct",
				width: 90,
				render: (data) => (data === "1" ? "是" : "否"),
			},
			form: {
				type: "radio",
				editDisabled: true,
				addDisabled: true,
				optionData: [
					{ label: "否", value: "0" },
					{ label: "是", value: "1" },
				],
				spanForm: 8,
			},
		},
		{
			table: {
				title: "开工日期",
				dataIndex: "startDate",
				format: "YYYY/MM/DD",
				width: 150,
			},
			form: {
				type: "date",
				spanForm: 8,
			},
		},
		{
			table: {
				title: "竣工日期",
				dataIndex: "endDate",
				format: "YYYY/MM/DD",
				width: 150,
			},
			form: {
				type: "date",
				spanForm: 8,
			},
		},
		{
			table: {
				title: "合同工期",
				dataIndex: "csTimeLimit",
				width: 120,
			},
			form: {
				type: "number",
				editDisabled: true,
				addDisabled: true,
				spanForm: 8,
			},
		},

		{
			table: {
				title: "合同内容",
				dataIndex: "content",
			},
			form: {
				type: "textarea",
				formItemStyle: {
					marginLeft: 4,
				},
				formItemLayout: shortLabel,
			},
		},
		{
			table: {
				title: "备注",
				dataIndex: "remarks",
			},
			form: {
				type: "textarea",
				formItemStyle: {
					marginLeft: 4,
				},
				formItemLayout: shortLabel,
			},
		},
		// {
		//     table: {
		//         title: '附件',
		//         dataIndex: 'attachmentList',
		//         render: (data) => {
		//             if (data) {
		//                 return data.map((item, index) => "<a href=" + item['url'] + " target='_block'>" + item['name'] + "</a>").join()
		//             } else {
		//                 return "--"
		//             }

		//         }
		//     },
		//     form: {
		//         type: 'files',
		//         required: false,
		//         fetchConfig: { apiName: 'upload' },
		//         max: 1,
		//         formItemStyle: {
		//             marginLeft: 4
		//         },
		//         formItemLayout: shortLabel,
		//     }
		// },

		{
			table: {
				title: "发起人",
				dataIndex: "beginPer",
				width: 120,
			},
			isInForm: false,
		},
		{
			isInForm: false,
			table: {
				title: "附件",
				dataIndex: "attachmentListdiy", 
				width: 100,
				align: "center",
				render: (val, rowData) => { 
					if (rowData?.attachmentList?.length || rowData?.docAttachmentList?.length) {
						return <FilesForSupplementaryAgreements dataList={rowData?.attachmentList} ZWFiles={rowData?.docAttachmentList} />;
					} else {
						return "无附件";
					}
				},
			},
		},
		{
			table: {
				title: "评审状态",
				dataIndex: "apih5FlowStatus",
				width: 80,
				fixed: "right",
				align: "center",
				render: (data) => {
					if (data === "0") {
						return "待提交";
					} else if (data === "1") {
						return "审核中";
					} else if (data === "2" || data === "-9") {
						return "审核完成";
					} else if (data === "3") {
						return "退回";
					} else if (data === "-1") {
						return "未审核";
					} else if (data === "-2") {
						return "暂存";
					} else {
						return "待提交";
					}
				},
			},
			isInForm: false,
		},

		{
			isInForm: false,
			table: {
				title: "清单",
				dataIndex: "action",
				key: "action", //操作列名称
				align: "center",
				fixed: "right",
				showType: "tile",
				width: 150,
				// isLeaf
				//叶子节点才渲染挂接按钮
				btns: [
					{
						label: (
							<span style={{ fontWeight: "500", fontSize: 14 }} title="编辑">
								{" "}
								<EditOutlined key="edit-icon" /> 编辑
							</span>
						),
						type: "primary",
						name: "Component",
						Component: "QingDanEdit",
					},
					// {
					//     label: <span style={{ fontWeight: "500", fontSize: 14 }} title="细明编辑"> <EditOutlined /> 细明编辑</span>,
					//     icon: 'save',//icon
					//     type: 'primary', //primary dashed danger
					//     // onClick: "bind:rowImportClick"
					//     name: "Component",
					//     Component: "QingDanEdit",
					//     field: "xiMingBianji",
					// },
					{
						label: (
							<span style={{ fontWeight: "500", fontSize: 14 }} title="挂接">
								{" "}
								<PullRequestOutlined key="edit-icon" /> 挂接
							</span>
						),
						icon: "save", //icon
						type: "primary", //primary dashed danger
						name: "Component",
						Component: "GuaJia",
					},
				],
			},
		},
	],
};
