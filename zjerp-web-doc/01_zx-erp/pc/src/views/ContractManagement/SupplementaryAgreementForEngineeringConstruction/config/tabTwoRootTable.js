import React from "react";
import { PullRequestOutlined } from "@ant-design/icons";

//下级清单列表
export default {
	antd: {
		rowKey: "ccCoAlterWorkId",
		size: "small",
		expandable: {
			//不可以展开和收缩
			onExpand: () => {},
			expandIcon: () => "",
		},
	},
	formConfig: [
		{
			//修改时：
			//  禁用：归属主合同清单编号、增减单价、是否为叶子节点、税率
			//  类型：清单名称 类型为下拉表格, 反之为输入框
			table: {
				title: "变更类型",
				dataIndex: "alterType",
				width: 100,
				type: "select",
				fixed: "left",
			},
			form: {
				type: "select",
				optionData: [
					{ label: "新增", value: "1" },
					{ label: "修改", value: "2" },
				],
			},
		},
		{
			table: {
				title: "清单编号",
				dataIndex: "ccWorksNo",
				fixed: "left",
				width: 200,
			},
			form: {
				type: "string",
			},
		},
		{
			table: {
				title: "清单名称",
				dataIndex: "ccWorksName",
				fixed: "left",
				width: 160,
			},
			form: {
				type: "string",
			},
		},
		{
			//未选中 禁用： 变更递减数量、递减单价、税率， 反之不禁用
			table: {
				title: "是否叶子节点",
				dataIndex: "isLeaf",
				width: 110,
				align: "center",
				render: (data) => (data === 1 ? "是" : "否"),
			},
			form: {
				type: "switch",
			},
		},
		{
			table: {
				title: "单位",
				dataIndex: "ccWorksUnit",
				width: 150,
			},
			form: {
				type: "string",
			},
		},
		{
			table: {
				title: "原合同数量",
				dataIndex: "originQty",
			},
			form: {
				type: "string",
			},
		},
		{
			table: {
				title: "原含税合同单价",
				dataIndex: "originPrice",
				width: 120,
			},
			form: {
				type: "string",
			},
		},
		// {
		//    table: {
		//       title: '不含税合同单价',
		//       dataIndex: 'contractPriceNoTax',
		//       width: 120
		//    }
		//    , form: {
		//       type: 'string',
		//    }
		// },
		{
			table: {
				title: "原含税合同金额",
				dataIndex: "contractPrice",
				width: 120,
			},
			form: {
				type: "string",
			},
		},
		{
			table: {
				title: "原不含税合同金额",
				dataIndex: "contractCostNoTax",
				width: 120,
			},
			form: {
				type: "string",
			},
		},
		{
			table: {
				title: "变更增减数量",
				dataIndex: "changeQty",
				width: 120,
			},
			form: {
				type: "number",
			},
		},
		{
			table: {
				title: "变更后数量",
				dataIndex: "afterChangeQty",
				width: 100,
			},
			form: {
				type: "number",
			},
		},
		{
			table: {
				title: "变更后含税金额",
				dataIndex: "afterAmt",
				width: 100,
			},
			form: {
				type: "number",
			},
		},
		{
			table: {
				title: "变更后不含税金额",
				dataIndex: "afterAmtNoTax",
				width: 130,
			},
			form: {
				type: "number",
			},
		},
		{
			table: {
				title: "变更后税额",
				dataIndex: "afterAmtTax",
				width: 100,
			},
			form: {
				type: "number",
			},
		},

		{
			table: {
				title: "税率（%）",
				dataIndex: "taxRate",
			},
			form: {
				type: "string",
			},
		},
		{
			table: {
				title: "是否抵扣",
				dataIndex: "isDeduct",
				width: 90,
				render: (data) => (data === "1" ? "是" : "否"),
			},
			isInForm: false,
		},

		{
			table: {
				title: "已挂接工序数",
				width: 120,
				dataIndex: "gjNum",
				align: "center",
			},
			form: {
				type: "number",
			},
		},
		{
			isInForm: false,
			isInTable: (args) => {
				return args.curEnv !== "detail";
			},
			table: {
				title: "挂接",
				dataIndex: "action",
				key: "action", //操作列名称
				align: "center",
				fixed: "right",
				showType: "tile",
				width: 60,
				// isLeaf
				//叶子节点才渲染挂接按钮
				btns: (args) => { 
					if (args.rowData.alterType === "2") {
						return [];
					}
					return [
						{
							label: (
								<span style={{ fontWeight: "800", fontSize: 18 }} title="挂接">
									<PullRequestOutlined />
								</span>
							),
							isValidate: true, //点击后是否验证表单 默认true
							icon: "save", //icon
							type: "primary", //primary dashed danger
							onClick: "bind:rowGJClick",
						},
					];
				},
			},
		},
	],
};
