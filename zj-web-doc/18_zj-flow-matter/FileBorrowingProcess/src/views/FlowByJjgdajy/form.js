import React, {
	Component
} from "react";
import {
	Form
} from "../modules/work-flow";
import moment from 'moment';
let config = {
	workFlowConfig: {
		title: ["appUser", "档案借阅(局机关)申请", "createTime"],
		apiNameByAdd: "addZjFlowArchivesLibrary",
		apiNameByUpdate: "updateZjFlowArchivesLibrary",
		apiNameByGet: "getZjFlowArchivesLibraryDetails",
		apiTitle: "getZjFlowArchivesLibraryTitle",
		flowId: "Jjgdajy",
		todo: "todoFlow",//???
		hasTodo: "hasTodoFlow",//???
		fieldsCURD: function (obj, flowData, props) {
			console.log(flowData.flowNode.nodeId);
			if (props.onRefreshDrawer) {
				props.onRefreshDrawer();
			}
			obj = obj.map((item) => {
				if (item.field == 'selectJuFlag') {
					if (flowData.flowNode.nodeId === 'Node7') {
						console.log(flowData.flowNode.nodeId);
						item.required = true;
						item.qnnDisabled = false;
						item.disabled = false;
					} else {
						item.qnnDisabled = false;
						item.required = false;
					}
				}
				return item
			})
			return obj
		}
	},
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
	state = {}
	render() {
		const {
			isInQnnTable, myPublic: { domain }
		} = this.props;
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
						label: "编号",
						qnnDisabled: true,
						field: "autoNum",
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
						label: "时间",
						qnnDisabled: true,
						field: "createTime",
						placeholder: "请输入",
						initialValue: () => {
							return moment(new Date()).format('YYYY-MM-DD')
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
						type: "string",
						label: "申请单位",
						required:true,
						field: "appUnit",
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
						label: "申请部门",
						required:true,
						field: "appDept",
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
						label: "申请人",
						field: "appUser",
						placeholder: "请输入",
						qnnDisabled: true,
						initialValue: () => {
							return this.props.loginAndLogoutInfo.loginInfo.userInfo.realName
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
						type: "string",
						label: "借阅目的",
						required:true,
						field: "borrowPurpose",
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
						type: "number",
						label: "借阅份数",
						qnnDisabled: true,
						field: "borrowCopy",
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
						label: "联系方式",
						required:true,
						field: "phone",
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
						hide: true,
						initialValue: '0',
						field: "archivesType",
						placeholder: "请输入"
					},
					{
						type: "string",
						label: "借阅效果",
						qnnDisabled: true,
						field: "borrowEffect",
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
						label: "归还时间",
						qnnDisabled: true,
						field: "returnTime",
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
						type: "qnnForm",
						field: "itemList",
						label: "明细",
						addBtnStyle: {},
						formBlockStyle: { marginTop: "0px" },
						formBlockFormStyle: { padding: "0px 12px" },
						textObj: {
							add: "添加细明",
							del: "删除"
						},
						titleStyle: {},
						hide: false,
						canAddForm: true,
						formFields: [
							{
								type: "string",
								label: "档案名称",
								required:true,
								field: "archivesName",
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
								},
							},
							{
								type: "string",
								label: "档案号",
								field: "archivesNumber",
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
								type: "select",
								label: "借阅方式",
								required:true,
								field: "borrowWay",
								placeholder: "请输入",
								optionConfig: {
									label: 'label',
									value: 'value'
								},
								optionData: [
									{ label: '借出原件', value: '0' },
									{ label: '拷贝电子文件', value: '1' },
									{ label: '查阅', value: '2' },
									{ label: '复印或拍照', value: '3' },
									{ label: '加盖归档的公章', value: '4' },
								],
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
						]
					},

					
					{
						label: '附件',
						field: 'fileList',
						type: 'files',
						fetchConfig: {
							apiName: window.configs.domain + 'upload'
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
						type: "select",
						label: "是否分管领导审批",
						qnnDisabled:true,
						field: "selectJuFlag",
						placeholder: "请输入",
						optionConfig: {
							label: 'label',
							value: 'value'
						},
						optionData: [
							{ label: '否', value: '0' },
							{ label: '是', value: '1' }
						],
						span: 12,
						formItemLayout: {
							labelCol: {
								xs: { span: 24 },
								sm: { span: 12 }
							},
							wrapperCol: {
								xs: { span: 24 },
								sm: { span: 12 }
							}
						},
					},
					{
						type: "textarea",
						label: "借阅单位负责人意见",
						field: "opinionField1",
						opinionField: true,
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
						addShow: false
					},
					{
						type: "textarea",
						label: "档案产生(相关) 部门负责人意见",
						field: "opinionField2",
						opinionField: true,
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
						addShow: false
					},
					{
						type: "textarea",
						label: "公司分管领导意见",
						field: "opinionField3",
						opinionField: true,
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
						addShow: false
					},
					{
						type: "textarea",
						label: "档案部门负责人意见",
						field: "opinionField4",
						opinionField: true,
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
						addShow: false
					}
				]}

			/>
		</div>
		);
	}
}
export default index;