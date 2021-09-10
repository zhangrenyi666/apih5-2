import React from "react";
//结算单
const logLabel = {
	labelCol: {
		sm: { span: 14 },
	},
	wrapperCol: {
		sm: { span: 10 },
	},
};

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
		rowKey: "id",
		size: "small",
	},
	desc: <span style={{ color: "red" }}>注意：审批状态为：“需重新评审，评审不通过”时，点击“详细”，查看审批意见</span>,
	fetchConfig: {
		apiName: "getZxSaProjectSettleAuditList",
		otherParams: "bind:getFinalStatementOtherParams",
	},
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
	actionBtns: [],
	formConfig: [
		{
			isInTable: false,
			form: {
				field: "id",
				type: "string",
				hide: true,
			},
		},
		{
			isInTable: false,
			table: {
				title: "公司名称",
				dataIndex: "comName",
				fixed: "left", //固定到右边
				width: 130,
			},
			form: {
				type: "string",
				spanForm: 8,
			},
		},
		{
			table: {
				title: "结算单编号",
				dataIndex: "billNo",
				onClick: "detail",
				fetchConfig: {
					apiName: "getZxSaProjectSettleAuditDetail",
					params: {
						id: "id",
					},
				},
				width: 300,
				fixed: "left",
			},
			form: {
				type: "string",
				required: true,
				spanForm: 8,
			},
		},
		{
			table: {
				title: "项目名称",
				dataIndex: "orgName",
				width: 260,
			},
			form: {
				type: "string",
				spanForm: 8,
			},
		},
		{
			table: {
				title: "结算期次",
				dataIndex: "period",
			},
			form: {
				type: "string",
				spanForm: 8,
			},
		},
		{
			isInTable: false,
			table: {
				title: "合同编号",
				dataIndex: "contractNo",
			},
			form: {
				type: "string",
				spanForm: 8,
			},
		},
		{
			table: {
				title: "合同名称",
				dataIndex: "contractName",
			},
			form: {
				type: "string",
				spanForm: 8,
			},
		},
		{
			table: {
				title: "合同乙方",
				dataIndex: "secondName",
				width: 260,
			},
			form: {
				type: "string",
				spanForm: 8,
			},
		},
		{
			table: {
				title: "结算类型",
				dataIndex: "billType",
				render: (data) => {
					return {
						"0": "中期",
						"1": "最终",
						"2": "返还保证金",
					}[data];
				},
			},
			form: {
				type: "select",
				spanForm: 8,
				optionData:[
					{ value:"0", label:"中期" },
					{ value:"1", label:"最终" },
					{ value:"2", label:"返还保证金" },
				]
			},
		},

		{
			table: {
				title: "结算期限开始时间",
				dataIndex: "beginDate",
				width: 140,
				format: "YYYY-MM-DD",
			},
			form: {
				type: "date",
				spanForm: 8,
				// formItemLayout: logLabel,
			},
		},
		{
			table: {
				title: "结算期限结束时间",
				dataIndex: "endDate",
				width: 140,
				format: "YYYY-MM-DD",
			},
			form: {
				type: "date",
				spanForm: 8,
				// formItemLayout: logLabel,
			},
		},
		{
			isInForm: false,
			table: {
				title: "本期结算金额（元）",
				dataIndex: "thisAmt",
				width: 150,
			},
			form: {
				type: "number",
				spanForm: 8,
				formItemLayout: logLabel,
			},
		},
		{
			isInForm: false,
			table: {
				title: "开累结算金额（元）",
				dataIndex: "totalAmt",
				width: 150,
			},
			form: {
				type: "number",
				spanForm: 8,
			},
		},
		{
			isInForm: false,
			table: {
				title: "本期应支付金额（元）",
				dataIndex: "thisPayAmt",
				width: 160,
			},
			form: {
				type: "number",
				spanForm: 8,
			},
		},
		{
			isInForm: false,
			table: {
				title: "开累应支付金额（元）",
				dataIndex: "totalPayAmt",
				width: 160,
			},
			form: {
				type: "number",
				spanForm: 8,
			},
		},
		{
			table: {
				title: "财务审批状态",
				dataIndex: "auditStatus",
				width: 130,
			},
			form: {
				type: "string",
				spanForm: 8,
			},
		},
		{
			table: {
				title: "财务审批状态说明",
				dataIndex: "cwStatusRemark",
				width: 150,
			},
			form: {
				type: "string",
				spanForm: 8,
			},
		},
		{
			table: {
				title: "调差后累计结算金额",
				dataIndex: "tchljjsje",
				width: 150,
			},
			form: {
				type: "number",
				spanForm: 8,
			},
		},
		{
			isInForm: false,
			table: {
				title: "本期调整后金额",
				dataIndex: "bqtchjsje",
				width: 150,
			},
			form: {
				type: "number",
				spanForm: 8,
			},
		},
		{
			isInTable: false,
			table: {
				title: "施工内容",
				dataIndex: "content",
				width: 150,
			},
			form: {
				type: "textarea",
				spanForm: 24,
				formItemLayout: shortLabel,
				formItemStyle: {
					marginLeft: 4,
				},
			},
		},

		{
			table: {
				title: "交工日期",
				dataIndex: "finishDate",
				format: "YYYY-MM-DD",
				width: 130,
			},
			form: {
				type: "date",
				placeholder: "请选择",
				spanForm: 8,
			},
		},
		{
			table: {
				title: "业务日期",
				dataIndex: "businessDate",
				format: "YYYY-MM-DD",
				width: 120,
			},
			form: {
				type: "date",
				placeholder: "请选择",
				spanForm: 8,
			},
		},
		{
			table: {
				title: "重新评审次数",
				dataIndex: "notPassNum",
				width: 120,
			},
			form: {
				type: "number",
				spanForm: 8,
			},
		},
		{
			table: {
				title: "发起人",
				dataIndex: "reportPerson",
				width: 120,
			},
			form: {
				type: "string",
				formItemLayout: shortLabel,
				formItemStyle: {
					marginLeft: 4,
				},
			},
		},

		//统计表格
		{
			isInTable: false,
			form: {
				type: "qnnTable",
				label: "",
				field: "projectSettleAuditItemList",
				incToForm: true,
				// formItemStyle: {
				//    marginLeft: 4
				// },
				// formItemLayout: shortLabel,
				qnnTableConfig: {
					isShowRowSelect: false,
					tableTdEdit: true,
					antd: {
						rowKey: "statisticsID",
						size: "small",
					},
					formConfig: [
						{
							table: {
								title: "统计项",
								dataIndex: "statisticsName",
								align: "center",
							},
							form: {
								type: "string",
							},
						},
						{
							table: {
								title: "本期（元）",
								dataIndex: "thisAmt",
								align: "center",
							},
							form: {
								type: "string",
							},
						},

						{
							table: {
								title: "开累（元）",
								dataIndex: "totalAmt",
								align: "center",
							},
							form: {
								type: "string",
							},
						},
					],
				},
			},
		},

		{
			isInTable: false,
			table: {
				title: "备注",
				dataIndex: "remark",
			},
			form: {
				type: "textarea",
				spanForm: 24,
				formItemLayout: shortLabel,
				formItemStyle: {
					marginLeft: 4,
				},
			},
		},
		{
			isInTable: false,
			table: {
				title: "计算人",
				dataIndex: "countPerson",
			},
			form: {
				type: "string",
				spanForm: 8,
			},
		},
		{
			isInTable: false,
			table: {
				title: "复核人",
				dataIndex: "reCountPerson",
			},
			form: {
				type: "string",
				spanForm: 8,
			},
		},

		{
			isInForm: false,
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
					} else if (data === "2") {
						return "审核完成";
					} else if (data === "3") {
						return "退回";
					} else if (data === "-1") {
						return "未审核";
					} else {
						return "待提交";
					}
				},
			}
		},

		{
			isInTable: false,
			form: {
				type: "files",
				label: "附件",
				field: "zxErpFileList",
				required: false,
				fetchConfig: { apiName: "upload" },
				max: 999,
				formItemStyle: {
					marginLeft: 4,
				},
				formItemLayout: shortLabel,
			},
		},
	],
};
