import React, {
	Component
} from "react";
import {
	Form
} from "../../modules/work-flow";
import $ from 'jquery';
let config = {
	workFlowConfig: {
		title: ["废旧物资管理", "billNo", ""],
		apiNameByAdd: "updateZxSkWornOut",
		apiNameByUpdate: "updateZxSkWornOut",
		apiNameByGet: "getZxSkWornOutDetail",
		// apiTitle: "setZjkOaFlowTitle",//???
		flowId: "zxSkWornOutWorkId",
		todo: "TodoHasTo",
		hasTodo: "TodoHasToq",
	},
	parameterName:'orgID',
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
						field: "zxSkWornOutId",
						initialValue: flowData && flowData.zxSkWornOutId ? flowData.zxSkWornOutId : '',
						hide: true
					},
					{
						type: "string",
						label: "单据编号",
						qnnDisabled: true,
						initialValue: flowData && flowData.billNo ? flowData.billNo : '',
						field: "billNo",
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
						label: "公司名称",
						qnnDisabled: true,
						initialValue: flowData && flowData.orgName ? flowData.orgName : '',
						field: "orgName",
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
						initialValue: flowData && flowData.orgID ? flowData.orgID : '',
						field: 'orgID',
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
						label: "申请单位",
						required: true,
						qnnDisabled: true,
						initialValue: flowData && flowData.applyOrgID ? flowData.applyOrgID : '',
						field: 'applyOrgID',
						type: 'select',
						optionConfig: {
							label: 'customerName',
							value: 'zxCrCustomerNewId'
						},
						fetchConfig: {
							apiName: 'getZxCrCustomerNewList',
							otherParams: {
								limit: 10,
								page: 1
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
						type: "number",
						label: "拟处理金额(万元)",
						qnnDisabled: true,
						field: "purchaseAmt",
						initialValue: flowData && flowData.purchaseAmt ? flowData.purchaseAmt : '',
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
						type: "date",
						label: "填报日期",
						qnnDisabled: true,
						field: "reportDate",
						initialValue: flowData && flowData.reportDate ? flowData.reportDate : '',
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
						type: "textarea",
						label: "废旧物资处理说明",
						qnnDisabled: true,
						field: "approval",
						initialValue: flowData && flowData.approval ? flowData.approval : '',
						placeholder: "请输入",
					
						autoSize: {
							minRows: 1,
							maxRows: 3
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
						field: "zxSkWornOutItemList",
						incToForm: true,
						initialValue:flowData && flowData.zxSkWornOutItemList ? flowData.zxSkWornOutItemList : '',
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
										field: 'zxSkWornOutItemId',
										hide: true
									}
								},
								{
									table: {
										title: '物资名称',
										width: 150,
										tooltip: 23,
										dataIndex: 'resName',
										key: 'resName'
									},
									form: {
										type: 'string',
										field: 'resName'
									}
								},

								{
									table: {
										title: '单位',
										dataIndex: 'unit',
										key: 'unit',
										width: 80
									},
									form: {
										type: 'string',
										field: 'unit'
									}
								},

								{
									table: {
										title: '数量',
										dataIndex: 'qty',
										key: 'qty',
										width: 80
									},
									form: {
										type: 'number',
										field: 'qty'
									}
								},
								{
									table: {
										title: '总金额(万元)',
										dataIndex: 'amt',
										key: 'amt'
									},
									form: {
										type: 'number',
										field: 'amt'
									}
								},
								{
									table: {
										title: '备注',
										dataIndex: 'remarks',
										key: 'remarks'
									},
									form: {
										type: 'textarea',
										field: 'remarks',
										autoSize: {
											minRows: 1,
											maxRows: 3
										},
									}
								}
							],
							actionBtns: []
						}
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
						label: "项目废旧物资",
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
						label: "公司废旧物资处理成员",
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
					{
						type: "textarea",
						label: "公司废旧物资主管领导",
						field: "opinionField5",
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
			/>
		</div>
		);
	}
}
export default index;