import React, {
	Component
} from "react";
import {
	Form
} from "../../modules/work-flow";
import $ from 'jquery';
let config = {
	workFlowConfig: {
		title: ["物资月度采购计划", "projectNumber", ""],
		apiNameByAdd: "updateZxSkMonthPur",
		apiNameByUpdate: "updateZxSkMonthPur",
		apiNameByGet: "getZxSkMonthPurDetails",
		// apiTitle: "setZjkOaFlowTitle",//???
		flowId: "zxskmonthPurWorkId",
		todo: "TodoHasTo",
		hasTodo: "TodoHasToq",
	},
	parameterName:'projectID',
	formTailLayout: {
		wrapperCol: {
			sm: {
				span: 12,
				offset: 12
			}
		}
	},
	
};
class index extends Component {
	constructor(props) {
		super(props);
		this.state = {}
	}
	render() {
		const {
			isInQnnTable, flowData
		} = this.props;
		const { departmentId } = this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany;
		return (<div style={
			{
				height: isInQnnTable ? "" : "100vh"
			}
		} >
			<Form
				{...this.props}
				{...config}
				{...this.props.workFlowConfig}
				{...config.workFlowConfig}
				formConfig={[
					{
						type: "string",
						label: "workId",
						field: "workId",
						hide: true,
						initialValue: function (obj) {
							return obj.match.params["workId"];
						}
					},
					{
						type: "string",
						label: "",
						field: "id",
						initialValue: flowData && flowData.id ? flowData.id : '',
						hide: true
					},
					{
						type: "string",
						label: "",
						field: "companyId",
						initialValue: flowData && flowData.companyId ? flowData.companyId : '',
						hide: true
					},
					{
						type: "string",
						label: "",
						field: "projectName",
						initialValue: flowData && flowData.projectName ? flowData.projectName : '',
						hide: true
					},
					{
						type: "string",
						label: "",
						field: "companyName",
						initialValue: flowData && flowData.companyName ? flowData.companyName : '',
						hide: true
					},
					{
						type: "string",
						label: "计划编号",
						qnnDisabled: true,
						initialValue: flowData && flowData.projectNumber ? flowData.projectNumber : '',
						field: "projectNumber",
						placeholder: "请输入",
						span: 12,
						formItemLayout: {
							labelCol: {
								xs: { span: 24 },
								sm: { span: 6 }
							},
							wrapperCol: {
								xs: { span: 24 },
								sm: { span: 18 }
							}
						},
					},
					{
						label: "项目名称",
						required: true,
						qnnDisabled: true,
						initialValue: flowData && flowData.projectID ? flowData.projectID : '',
						field: 'projectID',
						type: 'select',
						optionConfig: {
							label: 'departmentName',
							value: 'departmentId',
						},
						fetchConfig: {
							apiName: 'getSysProjectBySelect',
							otherParams: {
								departmentId:departmentId
							}
						},
						span: 12,
						formItemLayout: {
							labelCol: {
								xs: { span: 24 },
								sm: { span: 6 }
							},
							wrapperCol: {
								xs: { span: 24 },
								sm: { span: 18 }
							}
						},
					},
					{
						type: "date",
						label: "日期",
						required: true,
						qnnDisabled: true,
						field: "createDate",
						initialValue: flowData && flowData.createDate ? flowData.createDate : '',
						placeholder: "请输入",
						span: 12,
						formItemLayout: {
							labelCol: {
								xs: { span: 24 },
								sm: { span: 6 }
							},
							wrapperCol: {
								xs: { span: 24 },
								sm: { span: 18 }
							}
						},
					},
					{
						type: "string",
						label: "编制人",
						qnnDisabled: true,
						initialValue: flowData && flowData.aurhorizedPersonnel ? flowData.aurhorizedPersonnel : '',
						field: "aurhorizedPersonnel",
						placeholder: "请输入",
						span: 12,
						formItemLayout: {
							labelCol: {
								xs: { span: 24 },
								sm: { span: 6 }
							},
							wrapperCol: {
								xs: { span: 24 },
								sm: { span: 18 }
							}
						},
					},
					{
						type: "component",
						field: "component5",
						Component: obj => {
							return (
								<div style={{ background: '#f0f2f5', padding: '8px 12px' }}>
									详细信息
								</div>
							);
						}
					},
					{
						type: "qnnTable",
						field: "zxSkMonthPurItemList",
						incToForm: true,
						initialValue:flowData && flowData.zxSkMonthPurItemList ? flowData.zxSkMonthPurItemList : '',
						// initialValue: () => {
						// 	return flowData && flowData.zxSkMonthPurItemList ? flowData.zxSkMonthPurItemList : ''
						// },
						qnnTableConfig: {
							antd: {
								rowKey: "id",
								size: 'small',
								rowClassName: (record, val) => {
									$('.ant-table-footer').css('display', 'none')
									return '';
								}
							},
							// footer:false,
							paginationConfig: false,
							isShowRowSelect: false,
							// data: flowData && flowData.zxSkMonthPurItemList ? flowData.zxSkMonthPurItemList : '',
							formConfig: [
								{
									isInTable: false,
									form: {
										label: '主键id',
										field: 'id',
										hide: true
									}
								},
								{
									table: {
										title: '分部分项',
										dataIndex: 'cbsName',
										width: 200,
										key: 'cbsName'
									},
									isInForm: false
								},
								{
									table: {
										title: '物资编码',
										width: 200,
										tooltip: 23,
										dataIndex: 'resCode',
										key: 'resCode'
									},
									isInForm: false
								},
								{
									table: {
										title: '物资名称',
										width: 150,
										tooltip: 23,
										dataIndex: 'resName',
										key: 'resName'
									},
									isInForm: false
								},
								{
									table: {
										title: '计量单位',
										dataIndex: 'unit',
										key: 'unit',
										width: 80,
									},
									form: {
										type: 'string',
										field: 'unit'
									}
								},
								{
									table: {
										title: '规格型号',
										width: 100,
										dataIndex: 'spec',
										key: 'spec'
									},
									form: {
										type: 'string',
										field: 'spec'
									}
								},
								{
									table: {
										title: '质量标准',
										width: 120,
										dataIndex: 'qualityStand',
										key: 'qualityStand'
									},
									isInForm: false
								},
								{
									table: {
										title: '计划采购数量',
										dataIndex: 'purNum',
										width: 130,
										key: 'purNum'
									},
									isInForm: false
								},
								{
									table: {
										title: '估算单价',
										dataIndex: 'price',
										key: 'price',
										width: 100,
									},
									isInForm: false
								},
								{
									table: {
										title: '估算总价',
										dataIndex: 'totalMoney',
										width: 100,
										key: 'totalMoney'
									},
									isInForm: false
								},
								{
									table: {
										title: '备注',
										width: 120,
										tooltip: 23,
										dataIndex: 'remark',
										key: 'remark'
									},
									isInForm: false
								}
							],
							actionBtns: []
						}
					},
					// {
					// 	label: '审核状态',
					// 	type: 'select',
					// 	field: 'status',
					// 	initialValue: flowData && flowData.status ? flowData.status : '',
					// 	qnnDisabled: true,
					// 	optionConfig: {
					// 		label: 'label',
					// 		value: 'value',
					// 	},
					// 	optionData: [
					// 		{
					// 			label: "未审核",
					// 			value: "0"
					// 		},
					// 		{
					// 			label: "已审核",
					// 			value: "1"
					// 		}
					// 	],
					// 	span: 12,
					// 	formItemLayout: {
					// 		labelCol: {
					// 			xs: { span: 24 },
					// 			sm: { span: 6 }
					// 		},
					// 		wrapperCol: {
					// 			xs: { span: 24 },
					// 			sm: { span: 18 }
					// 		}
					// 	},
					// },
					{
						label: '备注',
						type: 'textarea',
						qnnDisabled: true,
						initialValue: flowData && flowData.remark ? flowData.remark : '',
						field: 'remark',
						autoSize: {
							minRows: 1,
							maxRows: 3
						},
						span: 12,
						formItemLayout: {
							labelCol: {
								xs: { span: 24 },
								sm: { span: 6 }
							},
							wrapperCol: {
								xs: { span: 24 },
								sm: { span: 18 }
							}
						},
					},
					{
						label: '公文附件',
						field: 'fileList',//???
						type: 'files',
						required:true,
						// qnnDisabled: true,
						initialValue: flowData && flowData.fileList ? flowData.fileList : '',
						onPreview: "bind:_docPre",
						fetchConfig: {
							apiName: 'upload',
						},
						formItemLayout: {
							labelCol: {
								xs: { span: 24 },
								sm: { span: 3 }
							},
							wrapperCol: {
								xs: { span: 24 },
								sm: { span: 21 }
							}
						},
					},
					{
						type: "textarea",
						label: "相关部门审核",
						field: "opinionField1",
						opinionField: true,
						formItemLayout: {
							labelCol: {
								xs: { span: 24 },
								sm: { span: 3 }
							},
							wrapperCol: {
								xs: { span: 24 },
								sm: { span: 21 }
							}
						},
						addShow: false
					},
					{
						type: "textarea",
						label: "项目领导审核",
						field: "opinionField2",
						opinionField: true,
						formItemLayout: {
							labelCol: {
								xs: { span: 24 },
								sm: { span: 3 }
							},
							wrapperCol: {
								xs: { span: 24 },
								sm: { span: 21 }
							}
						},
						addShow: false
					}
				]}
				createParamsFormat={(params, props) => {
					let list = eval('(' + params.apiBody + ')');
					list.zxSkMonthPurItemList = flowData.zxSkMonthPurItemList;
					params.apiBody = JSON.stringify(list);
					return params
				}}
			/>
		</div>
		);
	}
}
export default index;