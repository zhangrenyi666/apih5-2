import React, {
	Component
} from "react";
import {
	Form
} from "../../modules/work-flow";
import $ from 'jquery';
let config = {
	workFlowConfig: {
		title: ["物资限价调整", "adjustNo", ""],
		apiNameByAdd: "updateZxSkLimitPriceAdjust",
		apiNameByUpdate: "updateZxSkLimitPriceAdjust",
		apiNameByGet: "getZxSkLimitPriceAdjustDetails",
		// apiTitle: "setZjkOaFlowTitle",//???
		flowId: "zxSkLimitPriceAdjustWorkId",
		todo: "TodoHasTo",
		hasTodo: "TodoHasToq",
	},
	parameterName:'projectId',
	formTailLayout: {
		wrapperCol: {
			sm: {
				span: 8,
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
			isInQnnTable,flowData
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
						field: "orgID",
						initialValue: flowData && flowData.orgID ? flowData.orgID : '',
						hide: true
					},
					{
						type: "string",
						label: "调整编号",
						qnnDisabled: true,
						initialValue: flowData && flowData.adjustNo ? flowData.adjustNo : '',
						field: "adjustNo",
						placeholder: "请输入",
						span: 8,
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
						label: "公司名称",
						qnnDisabled: true,
						initialValue: flowData && flowData.orgName ? flowData.orgName : '',
						field: "orgName",
						placeholder: "请输入",
						span: 8,
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
						initialValue: flowData && flowData.projectId ? flowData.projectId : '',
						field: 'projectId',
						type: 'select',
						optionConfig: {
							label: 'orgName',
							value: 'orgID'
						},
						fetchConfig: {
							apiName: 'getZxCtContractList',
							otherParams: {
								contrStatus: "1",
								orgID: departmentId
							}
						},
						span: 8,
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
						label: "限价编号",
						qnnDisabled: true,
						initialValue: flowData && flowData.limitNo ? flowData.limitNo : '',
						field: "limitNo",
						placeholder: "请输入",
						span: 8,
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
						label: "项目所属省份",
						qnnDisabled: true,
						initialValue: flowData && flowData.province ? flowData.province : '',
						field: 'province',
						type: 'string',
						span: 8,
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
						label: "工程类型",
						qnnDisabled: true,
						initialValue: flowData && flowData.projectType ? flowData.projectType : '',
						field: 'projectType',
						type: 'string',
						span: 8,
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
						type: "month",
						label: "数据期次",
						required: true,
						qnnDisabled: true,
						field: "periodDate",
						initialValue: flowData && flowData.periodDate ? flowData.periodDate : '',
						placeholder: "请输入",
						span: 8,
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
						label: "填报人",
						qnnDisabled: true,
						initialValue: flowData && flowData.perpare ? flowData.perpare : '',
						field: 'perpare',
						type: 'string',
						span: 8,
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
						label: "填报日期",
						qnnDisabled: true,
						initialValue: flowData && flowData.prepareDate ? flowData.prepareDate : '',
						field: 'prepareDate',
						type: 'date',
						span: 8,
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
						field: "component66",
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
						field: "zxSkLimitPriceAdjustItemList",
						incToForm: true,
						initialValue: flowData && flowData.zxSkLimitPriceAdjustItemList ? flowData.zxSkLimitPriceAdjustItemList : '',
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
										title: '调整类型',
										dataIndex: 'adjustType',
										width: 120,
										key: 'adjustType',
										type: 'select'
									},
									form: {
										field: 'adjustType',
										type: 'select',
										optionConfig: {
											label: 'label',
											value: 'value',
										},
										optionData: [
											{
												label: "新增",//1新增  2修改---2021/2/1修改
												value: "1"
											},
											{
												label: "修改",
												value: "2"
											}
										]
									}
								},
								{
									table: {
										title: '物资大类',
										dataIndex: 'resourceName',
										width: 200,
										key: 'resourceName'
									},
								},
								{
									table: {
										title: '物资编码',
										width: 150,
										tooltip: 23,
										dataIndex: 'resourceNo',
										key: 'resourceNo'
									},
									form: {
										type: 'string',
										field: 'resourceNo'
									}
								},
								{
									table: {
										title: '物资名称',
										width: 150,
										tooltip: 23,
										dataIndex: 'workName',
										key: 'workName'
									},
									form: {
										type: 'string',
										field: 'workName'
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
										title: '单位',
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
										title: '当期采集单价',
										dataIndex: 'price',
										width: 120,
										key: 'price'
									},
									form: {
										type: 'number',
										field: 'price'
									}
								},
								{
									table: {
										title: '调整采集单价',
										dataIndex: 'adjustPrice',
										width: 120,
										key: 'adjustPrice'
									},
									form: {
										type: 'number',
										field: 'adjustPrice'
									}
								},
								{
									isInTable: false,
									table: {
										title: '填报日期',
										width: 120,
										dataIndex: 'prepareDate',
										format: 'YYYY-MM-DD',
										key: 'prepareDate',
										tdEdit: true
									},
									form: {
										type: 'date',
										field: 'prepareDate'
									}
								},
							],
							actionBtns: []
						}
					},
					{
						label: '附件',
						field: 'fileList',
						type: 'files',
						qnnDisabled: true,
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
						label: "项目经理",
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
						label: "项目总经济师",
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
					},
					{
						type: "textarea",
						label: "公司主管部审批",
						field: "opinionField3",
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
						label: "公司主管领导",
						field: "opinionField4",
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
			
				]}
			/>
		</div>
		);
	}
}
export default index;