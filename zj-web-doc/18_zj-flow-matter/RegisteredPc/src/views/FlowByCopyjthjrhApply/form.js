import React, {
	Component
} from "react";
import {
	Form
} from "../modules/work-flow";
import moment from "moment";
let config = {
	workFlowConfig: {
		title: ["", "", "外省市入北京集体户籍入户申请"],
		apiNameByAdd: "addZjFlowRhHouseholdOut",
		apiNameByUpdate: "updateZjFlowRhHouseholdOut",
		apiNameByGet: "getZjFlowRhHouseholdOutDetail",
		apiTitle: "setZjFlowRhHouseholdFlowTitle",
		flowId: "CopyjthjrhApply",
		todo: "todoByZjInHome",
		hasTodo: "hasTodoByZjInHome"
	},
	formTailLayout: {
		wrapperCol: {
			sm: {
				// span: 12,
				offset: 12
			}
		}
	},
	//qnn-form配置
	formConfig: [
		{
			type: "string",
			label: "workId",
			field: "workId",
			hide: function () {
				return true
			},
			initialValue: function (obj) {
				return obj.match.params["workId"];
			}
		},
		{
			type: "string",
			label: "",
			field: "outId",
			hide: function () {
				return true
			},
		},
		{
			type: "string",
			label: "",
			field: "titleFlag",
			hide: function () {
				return true
			},
			initialValue: '2'
		},
		{
			type: "qnnForm",
			field: "outInfoList",
			label: "集体户籍申请",
			textObj: {
				add: "添加",
				del: "删除"
			},
			canAddForm: true,
			addBtnStyle: {
				width: '87px'
			},
			qnnFormConfig: {
				formItemLayout: {
					labelCol: {
						xs: { span: 6 },
						sm: { span: 6 }
					},
					wrapperCol: {
						xs: { span: 18 },
						sm: { span: 18 }
					}
				},
				formConfig: [
					{
						label: '申请入户人姓名',
						type: 'string',
						required: true,
						field: 'outName',
						placeholder: '请输入...',
						span: 12,
						// colStyle:{
						// 	paddingLeft:12
						// },
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
						label: '申请入户人身份证号码',
						type: 'identity',
						field: 'outIdCard',
						required: true,
						placeholder: '请输入...',
						span: 12,
						// colStyle:{
						// 	paddingLeft:12
						// },
						formItemLayout: {
							labelCol: {
								xs: { span: 24 },
								sm: { span: 9 }
							},
							wrapperCol: {
								xs: { span: 24 },
								sm: { span: 15 }
							}
						}
					},
					{
						label: '京内职工姓名',
						required: true,
						type: 'string',
						field: 'beijingName',
						placeholder: '请输入...',
						span: 12,
						// colStyle:{
						// 	paddingLeft:12
						// },
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
						label: '京内职工联系方式',
						type: 'phone',
						required: true,
						field: 'beijingPhone',
						placeholder: '请输入...',
						span: 12,
						formItemLayout: {
							labelCol: {
								xs: { span: 24 },
								sm: { span: 9 }
							},
							wrapperCol: {
								xs: { span: 24 },
								sm: { span: 15 }
							}
						}
					},
					{
						label: '与申请人关系',
						type: 'string',
						required: true,
						field: 'relationship',
						placeholder: '请输入...',
						// span: 12,
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
						label: '进京理由',
						type: 'textarea',
						field: 'toBeijingReason',
						placeholder: '请输入...',
						// span: 8,
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
						label: '附件',
						field: 'fileList',
						type: 'files',
						fetchConfig: {
							apiName: window.configs.domain + 'upload',
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
					}
				]
			}
		},
		{
			type: "textarea",
			label: "备注",
			field: "zhushi",
			qnnDisabled: true,
			placeholder: "请输入",
			// initialValue: `1.公司必须证明申请入户人员为本单位在职职工；\n2.调离的必须先将户口迁出（不含公司所属各单位内流动）\n3.工作单位如有变动须及时通知公司办公室行政外联处。`,
			initialValue: '所需基本材料：\n1、准迁证\n2、身份证\n3、在京房产核查证明',
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
			label: "职工本人申请",
			field: "opinionField1",
			opinionField: true,
			addShow: true,
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
			type: "textarea",
			label: "公司所属单位意见",
			field: "opinionField2",
			opinionField: true,
			addShow: false,
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
			type: "textarea",
			label: "公司人力资源部意见",
			field: "opinionField3",
			opinionField: true,
			addShow: false,
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
			type: "textarea",
			label: "公司办公室意见",
			field: "opinionField4",
			opinionField: true,
			addShow: false,
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
			type: "textarea",
			label: "公司分管领导意见",
			field: "opinionField5",
			opinionField: true,
			addShow: false,
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

	]
};
class index extends Component {
	render() {
		const {
			isInQnnTable,
			myPublic: { domain, appInfo: { ureport } }
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

				btnsCURD={({ btns, flowData }) => {
					if (flowData && flowData.flowNode.nodeId === 'Node8' || flowData && flowData.flowNode.nodeId === 'Node9') {
						var printUrl = `${ureport}excel?_u=file:zjFlowRhHouseholdOtherKidList.xml&url=${domain}&workId=${flowData.workId}`
						btns.push({ buttonClass: "exprot", buttonFun: null, buttonId: "exprot", buttonName: "导出", icon: null, printUrl: printUrl });
					}
					return btns;
				}}
			/>  </div>
		);
	}
}
export default index;