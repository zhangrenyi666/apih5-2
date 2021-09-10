//服务类型列表
import React, { Component } from "react";
import { basic } from "../modules/layouts";
import QnnTable from "../modules/qnn-table";
const config = {
	fetchConfig: {//表格的ajax配置
		apiName: 'pcGetZjFeedbackProblemList',
	},
	antd: { //同步antd table组件配置 ***必传
		rowKey: function (row) {// ***必传
			return row.feedbackId
		},
		size: 'small'
	},
	drawerConfig: {
		width: '1000px'
	},
	paginationConfig: {// 同步antd的分页组件配置   
		position: 'bottom'
	},
	firstRowIsSearch: true,
	actionBtns: [
		{
			name: 'del',//内置add del
			icon: 'delete',//icon
			type: 'danger',//类型  默认 primary  [primary dashed danger]
			label: '删除',
			fetchConfig: {//ajax配置
				apiName: 'batchDeleteUpdateZjProblemFeedback',
			},
		}
	],
	//每个表单项的布局 -- 搜索区域
	formItemLayoutSearch: {
		//默认数据
		labelCol: {
			xs: { span: 24 },
			sm: { span: 6 }
		},
		wrapperCol: {
			xs: { span: 24 },
			sm: { span: 18 }
		}
	},
	tabs: [
		{
			field: "form1",
			name: "qnnForm",
			title: "基础信息",
			content: {
				fetchConfig: function (obj) {
					var rowData = obj.clickCb.rowData;
					return {
						apiName: 'pcGetZjProblemFeedbackDetails',
						otherParams: {
							feedbackId: rowData.feedbackId
						}
					}
				},
				formConfig: [
					{//普通选择框
						type: 'string',
						label: '所属项目',
						field: 'itemId', //唯一的字段名 ***必传
						placeholder: '请选择',
						editDisabled: true,
					},
					{//普通选择框
						type: 'select',
						label: '交流类型',
						field: 'serverId', //唯一的字段名 ***必传
						placeholder: '请选择',
						editDisabled: true,
						optionConfig: {//下拉选项配置
							label: 'itemName', //默认 label
							value: 'itemId',//最终的值使用逗号连接 默认值使用valueName 默认['value']
						},
						fetchConfig: {//配置后将会去请求下拉选项数据
							apiName: "getBaseCodeSelect",
							otherParams: {
								itemId: 'fuwuleixing'
							}
						}
					},
					// {
					// 	type: "string",
					// 	label: "标题",
					// 	field: "feedbackTitle", //唯一的字段名 ***必传
					// 	editDisabled: true,
					// 	placeholder: "请输入", //占位符
					// },
					{
						type: "textarea",
						label: "描述",
						field: "feedbackDescribe", //唯一的字段名 ***必传
						editDisabled: true,
						placeholder: "请输入", //占位符
					},
					{
						type: "string",
						label: "真实姓名",
						field: "feedbackUserName", //唯一的字段名 ***必传
						editDisabled: true,
						placeholder: "请输入", //占位符
						span:12,
						formItemLayout:{
							labelCol: {
								xs: { span: 24 },
								sm: { span: 8 }
							},
							wrapperCol: {
								xs: { span: 24 },
								sm: { span: 16 }
							}
						},
					},
					{
						type: "string",
						label: "手机号码",
						field: "tel", //唯一的字段名 ***必传
						editDisabled: true,
						placeholder: "请输入", //占位符
						span:12,
						formItemLayout:{
							labelCol: {
								xs: { span: 24 },
								sm: { span: 8 }
							},
							wrapperCol: {
								xs: { span: 24 },
								sm: { span: 16 }
							}
						},
					},
					{
						type: "string",
						label: "单位名称",
						field: "unit", //唯一的字段名 ***必传
						editDisabled: true,
						placeholder: "请输入", //占位符
					},
					{
						type: 'images',
						label: '图片附件',
						field: 'attachment', //唯一的字段名 ***必传
						editDisabled: true,
						fetchConfig: {//配置后将会去请求下拉选项数据
							apiName: window.configs.domain + 'upload',
						}
					},
					{
						type: 'files',
						label: '其它附件',
						field: 'otherFiles', //唯一的字段名 ***必传
						editDisabled: true,
						initialValue: [],
						fetchConfig: {//配置后将会去请求下拉选项数据
							apiName: window.configs.domain + 'upload',
						},
					},
				],
			}
		},
		{
			field: "form2",
			name: "qnnForm",
			title: "问题等级",
			content: {
				fetchConfig: function (obj) {
					var rowData = obj.clickCb.rowData;
					return {
						apiName: 'pcGetZjProblemFeedbackDetails',
						otherParams: {
							feedbackId: rowData.feedbackId
						}
					}
				},
				formConfig: [
					{
						type: "string",
						label: "主键ID",
						field: "feedbackId", //唯一的字段名 ***必传
						hide: true
					},
					{
						label: '等级',
						type: 'select',
						field: 'questionId',
						placeholder: '请选择',
						required: true,
						multiple: false, //是否开启多选功能 开启后自动开启搜索功能
						showSearch: false, //是否开启搜索功能 (移动端不建议开启)
						fetchConfig: {//配置后将会去请求下拉选项数据
							apiName: 'getBaseCodeSelect',
							otherParams: { itemId: 'wentidengji' }
						},
						optionConfig: {//下拉选项配置
							label: 'itemName', //默认 label
							value: 'itemId',//最终的值使用逗号连接 默认值使用valueName 默认['value']
						}
					},
				],
			}
		},
		{
			field: "form3",
			name: "qnnForm",
			title: "申请奖金",
			content: {
				fetchConfig: function (obj) {
					var rowData = obj.clickCb.rowData;
					return {
						apiName: 'pcGetZjProblemFeedbackDetails',
						otherParams: {
							feedbackId: rowData.feedbackId
						}
					}
				},
				formConfig: [
					{
						type: "number",
						label: "奖金金额",
						field: "bonusAmount", //唯一的字段名 ***必传
						placeholder: "请输入", //占位符
						required: true,
					},
					{
						type: 'string',
						label: "新增或更新",
						field: "isHave",
						hide: true
					},
					{
						type: 'string',
						label: "修改Id",
						field: "reportBonusId",
						hide: true
					},
				],
			}
		}
	],
	formConfig: [
		{
			isInTable: false,
			form: {
				field: 'feedbackId',
				type: 'string',
				placeholder: '请输入',
				hide: true
			}
		},
		{
			table: {
				title: '交流类型',
				dataIndex: 'typeName',//feedbackType 回显
				key: 'typeName',
				fieldsConfig: {
					type: 'select',
					field: 'serverId',
					placeholder: '请选择',
					fetchConfig: {
						apiName: "getBaseCodeSelect",
						otherParams: {
							itemId: 'fuwuleixing'
						}
					},
					optionConfig: {//下拉选项配置
						label: 'itemName', //默认 label
						value: 'itemId',//
					},
				}
			},
			isInForm: false
		},
		{
			table: {
				title: '描述', //表头标题
				dataIndex: 'feedbackDescribe', //表格里面的字段
				key: 'feedbackDescribe',//表格的唯一key
				tooltip:15,
				onClick: 'detail'
			},
			form: {
				type: 'string',
				placeholder: '请输入',
			}
		},
		{
			table: {
				title: '发起人',
				dataIndex: 'feedbackUserName',
				key: 'feedbackUserName',
			},
			isInForm: false
		},
		{
			table: {
				title: '发起日期',
				dataIndex: 'feedbackTime',
				key: 'feedbackTime',
				format: 'YYYY-MM-DD HH:mm:ss',
				fieldsConfig: {
					type: "none"
				},
			},
			isInForm: false
		},
		{
			table: {
				title: '等级',
				dataIndex: 'questionName',
				key: 'questionName',
				fieldsConfig: {
					type: 'select',
					field: 'questionId',
					placeholder: '请选择',
					fetchConfig: {
						apiName: "getBaseCodeSelect",
						otherParams: {
							itemId: 'wentidengji'
						}
					},
					optionConfig: {//下拉选项配置
						label: 'itemName', //默认 label
						value: 'itemId',//
					},
				}
			},
			isInForm: false
		},
		{
			table: {
				title: '奖金（元）',
				dataIndex: 'bonusAmount',
				key: 'bonusAmount',
				fieldsConfig: {
					type: "none"
				},
			},
			isInForm: false
		},
		{
			isInForm: false,
			table: {
				title: "操作",
				dataIndex: 'action',
				key: 'action',
				align: "center",
				showType: "tile",
				width: 80,
				btns: [
					{
						name: 'edit',
						render: function (rowData) {
							return '<a>修改</a>';
						},
						formBtns: [
							{
								name: 'cancel', //关闭右边抽屉
								type: 'dashed',//类型  默认 primary
								label: '取消',
								field:'cancel',
								hide: function (obj) {
									var index = obj.btnCallbackFn.getActiveKey();
									if (index === "0") {
										return true;
									} else {
										return false;
									}
								},
							},
							{
								name: 'diySubmit',//内置add del
								type: 'primary',//类型  默认 primary
								label: '提交',//提交数据并且关闭右边抽屉
								field:'submit',
								isValidate:'curTab',
								hide: function (obj) {
									var index = obj.btnCallbackFn.getActiveKey();
									if (index === "0") {
										return true;
									} else {
										return false;
									}
								},
								onClick: function (obj) {
									var index = obj.btnCallbackFn.getActiveKey();
									if (index === '1') {
										obj.btnCallbackFn.fetch('updateZjProblemFeedback', obj._formData, function ({ data, success, message }) {
											if (success) {
												obj.btnCallbackFn.msg.success(message)
												obj.btnCallbackFn.refresh();
											} else {
												obj.btnCallbackFn.msg.error(message);
											}
										})
									} else if (index === '2') {
										obj._formData.otherId = obj.rowData.feedbackId;
										obj._formData.bonusType = '1';
										if (obj._formData.isHave === '0') {
											obj.btnCallbackFn.fetch('addZjReportBonus', obj._formData, function ({ data, success, message }) {
												if (success) {
													obj.btnCallbackFn.msg.success(message)
													obj.btnCallbackFn.refresh();
												} else {
													obj.btnCallbackFn.msg.error(message);
												}
											})
										} else {
											obj.btnCallbackFn.fetch('updateZjReportBonus', obj._formData, function ({ data, success, message }) {
												if (success) {
													obj.btnCallbackFn.msg.success(message)
													obj.btnCallbackFn.refresh();
												} else {
													obj.btnCallbackFn.msg.error(message);
												}
											})
										}
									}
								}
							}
						]
					}
				],
			}
		}
	]
};

class index extends Component {
	render() {
		return (
			<div>
				<QnnTable
					{...this.props}
					fetch={this.props.myFetch} 
		 upload={this.props.myUpload}
					headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
					{...config}
				/>
			</div>
		);
	}
}
export default basic(index);