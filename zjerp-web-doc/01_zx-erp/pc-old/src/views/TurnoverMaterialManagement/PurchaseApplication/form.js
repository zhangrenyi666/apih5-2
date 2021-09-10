import React, {
	Component
} from "react";
import {
	Form
} from "../../modules/work-flow";
let config = {
	workFlowConfig: {
		title: ["周转材料购置", "billNo", ""],
		apiNameByAdd: "updateZxSkPurchase",
		apiNameByUpdate: "updateZxSkPurchase",
		apiNameByGet: "getZxSkPurchaseDetail",
		// apiTitle: "setZjkOaFlowTitle",//???
		flowId: "zxSkPurchaseWorkId",
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
						field: "id",
						initialValue: flowData && flowData.id ? flowData.id : '',
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
						}
					},
					{
						label: "公司名称",
						required: true,
						qnnDisabled: true,
						initialValue: flowData && flowData.comName ? flowData.comName : '',
						field: 'comName',
						type: 'string',
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
						}
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
						}
					},
					{
						type: "number",
						label: "购置金额",
						qnnDisabled: true,
						initialValue: flowData && flowData.purchaseAmt ? flowData.purchaseAmt : '',
						field: "purchaseAmt",
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
						}
					},
					{
						label: '发起人',
						type: 'string',
						qnnDisabled: true,
						initialValue: flowData && flowData.beginPer ? flowData.beginPer : '',
						field: 'beginPer',
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
						}
					},
					{
						label: '填报日期',
						type: 'date',
						qnnDisabled: true,
						initialValue: flowData && flowData.reportDate ? flowData.reportDate : '',
						field: 'reportDate',
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
						}
					},
					{
						type: "textarea",
						label: "审批内容",
						qnnDisabled: true,
						field: "approval",
						initialValue: flowData && flowData.approval ? flowData.approval : '',
						placeholder: "请输入",
						formItemLayout: {
							labelCol: {
								xs: { span: 24 },
								sm: { span: 3 }
							},
							wrapperCol: {
								xs: { span: 24 },
								sm: { span: 21 }
							}
						}
					},
					{
						label: '公文附件',
						field: 'fileList',
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
						}
					},
					{
						type: "textarea",
						label: "公司主管部门",
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
						label: "材料主管领导",
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
						label: "部门会签",
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
						label: "项目归档",
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
					}
				]}
			/>
		</div>
		);
	}
}
export default index;