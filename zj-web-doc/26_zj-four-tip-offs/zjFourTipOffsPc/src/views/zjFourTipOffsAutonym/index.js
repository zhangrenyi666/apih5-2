//典型问题曝光列表
import React, { Component } from "react";
import { basic } from "../modules/layouts";
import QnnTable from "../modules/qnn-table";
import { Modal, Spin } from 'antd';
import Operation from './operation';
import downLoad from "../modules/download";
const confirm = Modal.confirm;
const config = {
	// firstRowIsSearch: true,
	fetchConfig: {//表格的ajax配置
		apiName: 'pcGetZjTechnicalQualityReportInfoList',
		otherParams: { reportType: "1" },
	},
	// firstRowIsSearch: true,
	antd: { //同步antd table组件配置 ***必传
		rowKey: function (row) {// ***必传
			return row.reportId
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
	//存在tabs配置 所有打开的抽屉都是这个配置
	tabs: [
		{
			field: "form1",
			name: "qnnForm",
			title: "基础信息",
			content: {
				fetchConfig: function (obj) {
					var rowData = obj.clickCb.rowData;
					return {
						apiName: 'pcGetZjJszlDetails',
						otherParams: {
							reportId: rowData.reportId
						}
					}
				},
				formConfig: [
					{
						type: "string",
						label: "主键ID",
						field: "reportId", //唯一的字段名 ***必传
						hide: true
					},
					{//普通选择框
						type: 'string',
						label: '所属项目',
						field: 'itemId', //唯一的字段名 ***必传
						placeholder: '无',
						editDisabled: true,
					},
					{
						type: "string",
						label: "举报位置",
						field: "reportLocal", //唯一的字段名 ***必传
						placeholder: "请输入", //占位符
						editDisabled: true,
					},
					// {
					// 	type: "string",
					// 	label: "问题标题",
					// 	field: "title", //唯一的字段名 ***必传
					// 	placeholder: "请输入", //占位符
					// 	editDisabled: true,
					// },
					{
						type: "string",
						label: "工程位置",
						field: "reportProjectLocation", //唯一的字段名 ***必传
						editDisabled: true,
						placeholder: "请输入", //占位符
					},
					{
						type: "textarea",
						label: "问题描述",
						field: "problemDescribe", //唯一的字段名 ***必传
						placeholder: "请输入", //占位符
						editDisabled: true,
					},
					{
						type: "string",
						label: "真实姓名",
						field: "realName", //唯一的字段名 ***必传
						placeholder: "请输入", //占位符
						editDisabled: true,
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
						placeholder: "请输入", //占位符
						editDisabled: true,
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
						type: 'images',
						label: '图片附件',
						field: 'files', //唯一的字段名 ***必传
						editDisabled: true,
						fetchConfig: {//配置后将会去请求下拉选项数据
							apiName: window.configs.domain + 'upload',
						}
					},
					{
						type: 'files',
						label: '其它附件',
						field: 'otherFiles', //唯一的字段名 ***必传
						desc: '点击上传',
						editDisabled: true,
						initialValue: [],
						fetchConfig: {//配置后将会去请求下拉选项数据
							apiName: window.configs.domain + 'upload',
						}
					},
					// {
					// 	type: "string",
					// 	label: "举报者单位",
					// 	field: "unit", //唯一的字段名 ***必传
					// 	placeholder: "请输入", //占位符
					// 	editDisabled: true,
					// },
					{
						type: 'radio',
						label: "典型曝光",
						field: "exposureState",
						editDisabled: true,
						optionData: [//默认选项数据
							{
								label: '非典型曝光',
								value: '0'
							},
							{
								label: '典型曝光',
								value: '1'
							}
						]
					},
					{
						type: "select",
						label: "工程类别",
						field: "engineerCategoryId", //唯一的字段名 ***必传
						placeholder: "请选择", //占位符
						fetchConfig: {//配置后将会去请求下拉选项数据
							apiName: 'getBaseCodeSelect',
							otherParams: { itemId: 'gongChengLeiBie' },
						},
						optionConfig: {//下拉选项配置
							label: 'itemName', //默认 label
							value: 'itemId',//最终的值使用逗号连接 默认值使用valueName 默认['value']
						},
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
						type: "select",
						label: "所属公司",
						field: "affiliatCompanyId", //唯一的字段名 ***必传
						placeholder: "请选择", //占位符
						fetchConfig: {//配置后将会去请求下拉选项数据
							apiName: 'getBaseCodeSelect',
							otherParams: { itemId: 'suoShuGongSi' },
						},
						optionConfig: {//下拉选项配置
							label: 'itemName', //默认 label
							value: 'itemId',//最终的值使用逗号连接 默认值使用valueName 默认['value']
						},
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
						type: 'radio',
						placeholder: '请选择',
						label: "严重程度",
						field: "problemLevel",
						optionData: [//默认选项数据
							{
								label: '正常',
								value: '0'
							},
							{
								label: '忽略',
								value: '1'
							},
							{
								label: '轻微',
								value: '2'
							},
							{
								label: '严重',
								value: '3'
							},
							{
								label: '警告',
								value: '4'
							},
						]
					},
					{
						type: "textarea",
						label: "严重程度描述",
						field: "problemLevelDescribe", //唯一的字段名 ***必传
						placeholder: "请输入", //占位符
					},
					{
						type: 'radio',
						placeholder: '请选择',
						label: "处理情况",
						field: "processState",
						optionData: [//默认选项数据
							{
								label: '核实中',
								value: '0'
							},
							{
								label: '确认证实',
								value: '1'
							},
							{
								label: '未证实',
								value: '2'
							},
							{
								label: '已处理',
								value: '3'
							},
							{
								label: '不予处理',
								value: '4'
							},
							// {
							// 	label: '奖金核实中',
							// 	value: '5'
							// },
							// {
							// 	label: '奖金已发放',
							// 	value: '6'
							// },
							// {
							// 	label: '奖金发放失败',
							// 	value: '7'
							// },
						]
					},
					{
						type: "textarea",
						label: "处理描述",
						field: "processStateDescribe", //唯一的字段名 ***必传
						placeholder: "请输入", //占位符
					},
					{
						type: 'files',
						label: '处理附件',
						field: 'procdssAttachment', //唯一的字段名 ***必传
						desc: '点击上传',
						initialValue: [],
						fetchConfig: {//配置后将会去请求下拉选项数据
							apiName: window.configs.domain + 'upload',
						}
					},
				],
			}
		},
		{
			//自定義組件
			field: "form2",
			name: "diy",
			title: "操作记录",
			content: props => {
				return <Operation {...props} />;
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
						apiName: 'getZjReportBonusDetails',
						otherParams: {
							otherId: rowData.reportId
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
				// btns: [
				// 	{
				// 		name: "submit",
				// 		type: "primary",
				// 		label: "申请奖金",
				// 		onClick: function (obj) { //此时里面会多一个response
				// 			const { Msg, myFetch } = obj.btnfns;
				// 			const { rowData } = obj.props.clickCb;
				// 			let body = {
				// 				otherId:rowData.reportId,
				// 				bonusAmount:obj.values.bonusAmount,
				// 				bonusType:obj.values.bonusType,
				// 			}
				// 			myFetch('addZjReportBonus', body, function ({ data, success, message }) {
				// 				if (success) {
				// 					Msg.success(message);
				// 				} else {
				// 					Msg.error(message);
				// 				}
				// 			})
				// 		},
				// 	}
				// ]
			}
		},
		// {
		// 	//content就是qnn-table配置
		// 	field: "table1",
		// 	name: "qnnTable",
		// 	title: "曝光可见对象",
		// 	content: {
		// 		tabs: [], //注意：嵌入到tabs中的qnn-table配置必须配置tabs项 否则tabs项将和上级重复
		// 		fetchConfig: {
		// 			apiName: 'getZjExposureVisibleList',
		// 			otherParams: (obj) => {
		// 				let rowData = obj.clickCb.rowData;
		// 				if (rowData) {
		// 					return { reportId: rowData.reportId };
		// 				}
		// 			},
		// 		},
		// 		drawerConfig: {
		// 			width: "800px"
		// 			// maskClosable:false, //点击蒙层是否关闭抽屉  默认true
		// 		},
		// 		antd: {
		// 			rowKey: function (row) {
		// 				//---row.主键id
		// 				return row.exposureId;
		// 			},
		// 			size:'small'
		// 		},
		// 		paginationConfig: {
		// 			position: "bottom"
		// 		},
		// 		firstRowIsSearch: false,
		// 		actionBtns: [
		// 			{
		// 				name: "add",
		// 				icon: "plus",
		// 				type: "primary",
		// 				label: "新增",
		// 				hide:(obj) => {
		// 					if(obj.props.clickCb.rowInfo.name === 'detail'){
		// 						return true;
		// 					}else{
		// 						return false;
		// 					}
		// 				},
		// 				formBtns: [
		// 					{
		// 						name: "cancel",
		// 						type: "dashed",
		// 						label: "取消"
		// 					},
		// 					{
		// 						name: "submit",
		// 						type: "primary",
		// 						label: "提交",
		// 						fetchConfig: {
		// 							apiName: "addZjExposureVisibleList",
		// 							otherParams: function (obj) {
		// 								return {
		// 									reportId: obj.clickCb.rowData.reportId
		// 								}
		// 							}
		// 						}
		// 					}
		// 				]
		// 			},
		// 			{
		// 				name: "del",
		// 				icon: "delete",
		// 				type: "danger",
		// 				label: "删除",
		// 				hide:(obj) => {
		// 					if(obj.props.clickCb.rowInfo.name === 'detail'){
		// 						return true;
		// 					}else{
		// 						return false;
		// 					}
		// 				},
		// 				fetchConfig: {
		// 					apiName: "batchDeleteUpdateZjExposureVisibleObject"
		// 				}
		// 			}
		// 		],
		// 		formConfig: [
		// 			{
		// 				table: {
		// 					title: "昵称",
		// 					dataIndex: "nickname",
		// 					key: "nickname",
		// 				},
		// 				isInForm: false
		// 			},
		// 			{
		// 				table: {
		// 					title: "头像地址",
		// 					dataIndex: "imgUrl",
		// 					key: "imgUrl",
		// 				},
		// 				isInForm: false
		// 			},
		// 			{
		// 				isInTable: false,
		// 				form: {
		// 					label: '曝光用户',
		// 					field: 'exposureUserKeyList',
		// 					type: 'treeSelect',
		// 					initialValue: [],
		// 					treeSelectOption: {
		// 						fetchConfig: {//配置后将会去请求下拉选项数据
		// 							apiName: 'getSysDepartmentUserAllTree',
		// 						},
		// 						search: true,
		// 						selectType: "2",
		// 						searchPlaceholder: '姓名、账号、电话',
		// 						searchParamsKey: 'search',//搜索文字的K 默认是'searchText'   [string]
		// 					}
		// 				}
		// 			},
		// 		]
		// 	}
		// }
	],
	formConfig: [
		{
			isInTable: false,
			table: {
				title: "主键id",
				dataIndex: "reportId"
			},
			isInForm: false
		},
		{
			table: {
				title: '描述', //表头标题
				dataIndex: 'problemDescribe', //表格里面的字段
				key: 'problemDescribe',//表格的唯一key  
				onClick: 'detail',
				tooltip:15
			},
			isInForm: false
		},
		{
			table: {
				title: '所属项目',
				dataIndex: 'itemId',
				key: 'itemId',
				tooltip:15
			},
			isInForm: false
		},
		{
			table: {
				title: '举报者',
				dataIndex: 'realName',
				key: 'realName',
			},
			isInForm: false
		},
		{
			table: {
				title: '举报日期',
				dataIndex: 'reportTime',
				key: 'reportTime',
				format: 'YYYY-MM-DD HH:mm:ss',
				fieldsConfig: {
					type: "none"
				},
			},
			isInForm: false
		},
		{
			table: {
				title: '严重程度',
				dataIndex: 'problemLevel',
				key: 'problemLevel',
				render: function (data) {
					if (data === '0') {
						return <div><span style={{ display: 'inline-block', backgroundColor: '#5ad084', height: '10px', width: '10px', borderRadius: '50%', lineHeight: '21px' }}></span> 正常</div>
					} else if (data === '1') {
						return <div><span style={{ display: 'inline-block', backgroundColor: '#44c4f7', height: '10px', width: '10px', borderRadius: '50%', lineHeight: '21px' }}></span> 忽略</div>
					} else if (data === '2') {
						return <div><span style={{ display: 'inline-block', backgroundColor: '#e4ff18', height: '10px', width: '10px', borderRadius: '50%', lineHeight: '21px' }}></span> 轻微</div>
					} else if (data === '3') {
						return <div><span style={{ display: 'inline-block', backgroundColor: '#ff7a18', height: '10px', width: '10px', borderRadius: '50%', lineHeight: '21px' }}></span> 严重</div>
					} else if (data === '4') {
						return <div><span style={{ display: 'inline-block', backgroundColor: '#f10707', height: '10px', width: '10px', borderRadius: '50%', lineHeight: '21px' }}></span> 警告</div>
					}
				},
				fieldsConfig: {
					type: 'select',
					field: 'problemLevel',
					placeholder: '请选择',
					optionData: [//默认选项数据//可为function (props)=>array
					    {
					        name: '正常',
					        id: '0'
					    },
					    {
					        name: '忽略',
					        id: '1'
						},
						{
					        name: '轻微',
					        id: '2'
						},
						{
					        name: '严重',
					        id: '3'
						},
						{
					        name: '警告',
					        id: '4'
					    },
					],
					optionConfig: {
						label: 'name',
						value: 'id',
					},
				}
			},
			isInForm: false
		},
		{
			table: {
				title: '处理情况',
				dataIndex: 'processState',
				key: 'processState',
				render: function (data) {
					var text = ""
					switch (data) {
						case "0": text = "核实中";
							break;
						case "1": text = "确认证实";
							break;
						case "2": text = "未证实";
							break;
						case "3": text = "已处理";
							break;
						case "4": text = "不予处理";
							break;
						case "5": text = "奖金核实中";
							break;
						case "6": text = "奖金已发放";
							break;
						case "7": text = "奖金发放失败";
							break;
						default: text = "未知";
							break;
					}
					return text
				},
				fieldsConfig: {
					type: 'select',
					field: 'processState',
					placeholder: '请选择',
					optionData: [//默认选项数据//可为function (props)=>array
					    {
					        name: '核实中',
					        id: '0'
					    },
					    {
					        name: '确认证实',
					        id: '1'
						},
						{
					        name: '未证实',
					        id: '2'
						},
						{
					        name: '已处理',
					        id: '3'
						},
						{
					        name: '不予处理',
					        id: '4'
						},
						{
					        name: '奖金核实中',
					        id: '5'
						},
						{
					        name: '奖金已发放',
					        id: '6'
						},
						{
					        name: '奖金发放失败',
					        id: '7'
					    },
					],
					optionConfig: {
						label: 'name',
						value: 'id',
					},
				}
			},
			isInForm: false
		},
		{
			table: {
				title: '曝光状态',
				dataIndex: 'exposureState',
				key: 'exposureState',
				render: function (data) {
					var text = ""
					switch (data) {
						case "0": text = "未曝光";
							break;
						case "1": text = "典型曝光";
							break;
						default: text = "未知";
							break;
					}
					return text
				},
				fieldsConfig: {
					type: 'select',
					field: 'exposureState',
					placeholder: '请选择',
					optionData: [//默认选项数据//可为function (props)=>array
					    {
					        name: '未曝光',
					        id: '0'
					    },
					    {
					        name: '典型曝光',
					        id: '1'
						}
					],
					optionConfig: {
						label: 'name',
						value: 'id',
					},
				}
			},
			isInForm: false
		},
		{
			table: {
				title: '举报位置',
				dataIndex: 'reportLocal',
				key: 'reportLocal',
				tooltip:15
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
								field:'cancel',
								label: '取消',
								hide: function (obj) {
									var index = obj.btnCallbackFn.getActiveKey();
									if (index === "1" || index === "3") {
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
									if (index === "1" || index === "3") {
										return true;
									} else {
										return false;
									}
								},
								onClick: function (obj) {
									var index = obj.btnCallbackFn.getActiveKey();
									if (index === '0') {
										obj.btnCallbackFn.fetch('updateZjTechnicalQualityReportInfo', obj._formData, function ({ data, success, message }) {
											if (success) {
												obj.btnCallbackFn.msg.success(message);
												obj.btnCallbackFn.refresh();
											} else {
												obj.btnCallbackFn.msg.error(message);
											}
										})
									} else if (index === '2') {
										obj._formData.otherId = obj.rowData.reportId;
										obj._formData.bonusType = '0';
										if (obj._formData.isHave === '0') {
											obj.btnCallbackFn.fetch('addZjReportBonus', obj._formData, function ({ data, success, message }) {
												if (success) {
													obj.btnCallbackFn.msg.success(message);
												} else {
													obj.btnCallbackFn.msg.error(message);
												}
											})
										} else {
											obj.btnCallbackFn.fetch('updateZjReportBonus', obj._formData, function ({ data, success, message }) {
												if (success) {
													obj.btnCallbackFn.msg.success(message);
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
	constructor() {
        super();
        this.state = {
			reportId:'',
            visible: false,
            loading: false,
            flag:true
        }
	}
	handleCancel = () => {
        this.setState({ visible: false });
    }
	render() {
		let { myPublic: { domain,appInfo: { ureport } } } = this.props;
		const { reportId, visible, loading } = this.state;
		return (
			<div>
				<QnnTable
					{...this.props}
					fetch={this.props.myFetch} 
		 upload={this.props.myUpload}
					headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
					actionBtns = {[
						// {
						// 	name: "diy",
						// 	icon: "sound",
						// 	type: "primary",
						// 	label: "批量曝光",
						// 	isValidate: false,//是否验证表单 默认true 
						// 	onClick: function (obj) { //此时里面会多一个response
						// 		const { fetch, msg } = obj.btnCallbackFn;
						// 		const body = obj.selectedRows.map((item) => {
						// 			return { reportId: item.reportId };
						// 		})
						// 		if (obj.selectedRows.length) {
						// 			confirm({
						// 				title: `您确定要曝光么?`,
						// 				content: `取消曝光请点击取消按钮。`,
						// 				okText: "确认",
						// 				cancelText: "取消",
						// 				onOk: () => {
						// 					fetch('batchExposureZjJszl', body, function ({ data, success, message }) {
						// 						if (success) {
						// 							msg.success(message)
						// 							obj.btnCallbackFn.refresh();
						// 						} else {
						// 							msg.error(message);
						// 						}
						// 					})
						// 				},
						// 			});
						// 		} else {
						// 			msg.error("请选择数据")
						// 		}
						// 	},
						// },
						{
							name: 'export', //内置add del
							type: 'primary', //类型  默认 primary
							icon: 'export',
							label: '导出',
							isValidate: false,//是否验证表单 默认true 
							onClick: ({ props, selectedRows, btnCallbackFn }) => {
								const { msg } = btnCallbackFn;
								const {
									loginAndLogoutInfo: {
										loginInfo: { token }
									},
									myPublic: { domain }
								} = props;
								if (selectedRows.length) {
									confirm({
										title: `您确定要导出数据么?`,
										content: `取消导出请点击取消按钮。`,
										okText: "确认",
										cancelText: "取消",
										onOk: () => {
											var URL = `${domain + "exportZjTechnicalQualityReport"}`;
											downLoad(URL, selectedRows, { token }, '中交四公局技术质量有奖举报.zip');
										},
									});
								}else{
									msg.error("请选择数据");
								}
							}
						},
						// {
						// 	name: "diy",
						// 	type: "primary",
						// 	icon: "export",
						// 	label: "报表预览",
						// 	onClick: (obj) => {
						// 		if (obj.selectedRows.length === 1) {
						// 			if (obj.selectedRows[0].reportId === this.state.reportId) {
						// 				this.setState({ visible: true });
						// 			}else{
						// 				this.setState({
						// 					reportId: obj.selectedRows[0].reportId
						// 				}, () => {
						// 					this.setState({ visible: true, loading: true });
						// 				})
						// 			}
						// 		} else {
						// 			Msg.error("请选择一条数据！");
						// 		}
						// 	}
						// },
						{
							name: 'del',//内置add del
							icon: 'delete',//icon
							type: 'danger',//类型  默认 primary  [primary dashed danger]
							label: '删除',
							fetchConfig: {//ajax配置
								apiName: 'batchDeleteUpdateZjTechnicalQualityReportInfo',
							},
						}
					]}
					{...config}
				/>
				<div>
                    <Modal
                        width={'45%'}
                        style={{ paddingBottom: '0', top: '0' }}
                        title="报表预览"
                        visible={visible}
                        footer={null}
                        onCancel={this.handleCancel}
                        bodyStyle={{ padding: '10px', overflow:'hidden' }}
                        centered={true}
                        wrapClassName={'modals'}
                    >
                        <Spin spinning={loading}>
                            <iframe title="myf" width='100%' height={window.innerHeight * 0.8} frameBorder={0} onLoad={(obj) => {
                                this.setState({ loading: false,flag:false })
                            }} src={`${ureport}preview?_u=file:technicalQualityReport.xml&url=${domain}&delFlag=0&reportId=${reportId}`}></iframe>
                        </Spin>
                    </Modal>
                </div>
			</div>
		);
	}
}
export default basic(index);