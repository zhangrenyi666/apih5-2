import React, {
	Component
} from "react";
import {
	Form
} from "../../modules/work-flow";
import $ from 'jquery';
let config = {
	workFlowConfig: {
		title: ["限价数据采集", "limitNo", ""],
		apiNameByAdd: "updateZxSkLimitPrice",
		apiNameByUpdate: "updateZxSkLimitPrice",
		apiNameByGet: "getZxSkLimitPriceDetails",
		// apiTitle: "setZjkOaFlowTitle",//???
		flowId: "zxSkLimitPriceWorkId",
		todo: "TodoHasTo",
		hasTodo: "TodoHasToq",
	},
	parameterName:'projectId',
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
						field: "faqi",
						initialValue: '1',
						hide: true
					},
					{
						type: "string",
						label: "计划编号",
						qnnDisabled: true,
						initialValue: flowData && flowData.limitNo ? flowData.limitNo : '',
						field: "limitNo",
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
						initialValue: flowData && flowData.projectId ? flowData.projectId : '',
						field: 'projectId',
						type: 'select',
						optionConfig: {
							label: 'orgName',
							value: 'orgID',
							linkageFields: {
								"comName": "companyName",//公司
								"projectName": "departmentName"
							}
						},
						fetchConfig: {
							apiName: 'getZxCtContractList',
							otherParams: {
								contrStatus: "1",
								orgId: departmentId
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
						type: "halfYear",
						label: "数据期次",
						required: true,
						qnnDisabled: true,
						field: "periodDate",
						initialValue: flowData && flowData.periodDate ? flowData.periodDate : '',
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
						field: "zxSkLimitPriceItemList",
						incToForm: true,
						initialValue: flowData && flowData.zxSkLimitPriceItemList ? flowData.zxSkLimitPriceItemList : '',
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
										title: '物资大类',
										dataIndex: 'resourceName',
										width: 200,
										key: 'resourceName'
									},
									isInForm: false
								},
								{
									table: {
										title: '物资编码',
										width: 150,
										tooltip: 23,
										dataIndex: 'resourceNo',
										key: 'resourceNo'
									},
									isInForm: false
								},
								{
									table: {
										title: '物资编码',
										dataIndex: 'workNo',
										width: 180,
										key: 'workNo'
									},
									isInForm: false
								},

								{
									table: {
										title: '物资名称',
										width: 150,
										tooltip: 23,
										dataIndex: 'workName',
										key: 'workName'
									},
									isInForm: false
								},

								{
									table: {
										title: '规格型号',
										width: 100,
										dataIndex: 'spec',
										key: 'spec'
									},
									isInForm: false
								},
								{
									table: {
										title: '单位',
										dataIndex: 'unit',
										key: 'unit',
										width: 80,
									},
									isInForm: false
								},
								{
									table: {
										title: '当期采集单价',
										dataIndex: 'price',
										width: 120,
										key: 'price'
									},
									isInForm: false
								}
							],
							actionBtns: []
						}
					},
					{
						label: '附件',
						field: 'fileList',
						type: 'files',
						required: true,
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
						label: "项目总经",
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
						label: "项目经理",
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
						label: "公司主管部门",
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