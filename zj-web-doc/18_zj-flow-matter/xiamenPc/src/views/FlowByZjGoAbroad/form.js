import React, {
	Component
} from "react";
import {
	Form
} from "../modules/work-flow";
import moment from "moment";
let config = {
	workFlowConfig: {
        title: ["name","YearAndMonth","因私出国（各单位）申请"],
        apiNameByAdd: "addZjFlowGoAbroadApply",
        apiNameByUpdate: "updateZjFlowGoAbroadApply",
        apiNameByGet: "getZjFlowGoAbroadApplyDetail",
        flowId: "zjGoAbroad",
        formLink: {
            zjGoAbroad: "FlowByzjGoAbroad"
        },
        todo: "todoByzjYongYin",
		hasTodo: "hasTodoByzjYongYin",
		// actionParamsFormat: function (body,props) {
		// 	let apiBody = JSON.parse(body.apiBody);
		// 	apiBody.formEditFlag = body.nodeVars.formEditFlag;
		// 	body.apiBody = JSON.stringify(apiBody);
		// 	return body
		// }
	},
	formTailLayout: {
        wrapperCol: {
            sm: {
                span: 12,
                offset: 12
            }
        }
    },
	//qnn-form配置
	
};
class index extends Component {
	render() {
		const {
			isInQnnTable
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
						label: "",
						field: "goAbroadApplyId",
						hide: true
					}, 
					{
						type: "string",
						label: "",
						field: "YearAndMonth",
						hide: true,
						initialValue:moment().format('YYYY-MM-DD HH:mm:ss')
					},
					{
						type: "string",
						label: "类别标识",
						field: "typeFlag",
						initialValue:'0',
						hide: true
					}, 
					{
						type: "select",
						label: "公文类型",
						required: true,
						field: "documentType",
						placeholder: "请输入",
						// span:12,
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
						optionConfig: {
							label: 'text', 
							value: 'value',
						},
						optionData: [
							{
								value: "0",
								text: "请示批复"
							},
							{
								value: "1",
								text: "通知通报"
							},
							{
								value: "2",
								text: "报告函"
							}
						]
					},
					{
						type: "string",
						label: "姓名",
						required:true,
						field: "name",
						placeholder: "请输入",
						span:8,
						formItemLayout: {
							labelCol: {
								xs: { span: 24 },
								sm: { span: 9 }
							},
							wrapperCol: {
								xs: { span: 24 },
								sm: { span: 15 }
							}
						},
					},
					{
						type: "select",
						label: "性别",
						required:true,
						field: "sex",
						placeholder: "请输入",
						initialValue:'男',
						optionConfig: {
							label: 'text', 
							value: 'value',
						},
						optionData: [
							{
								value: "0",
								text: "男"
							},
							{
								value: "1",
								text: "女"
							} 
						],
						span:8,
						formItemLayout: {
							labelCol: {
								xs: { span: 24 },
								sm: { span: 9 }
							},
							wrapperCol: {
								xs: { span: 24 },
								sm: { span: 15 }
							}
						},
					},
					{
						type: "date",
						required:true,
						label: "出生年月",
						field: "birth",
						placeholder: "请输入",
						initialValue: moment().format('YYYY-MM-DD'),
						span:8,
						formItemLayout: {
							labelCol: {
								xs: { span: 24 },
								sm: { span: 9 }
							},
							wrapperCol: {
								xs: { span: 24 },
								sm: { span: 15 }
							}
						},
					}, 
					{
						type: "string",
						required:true,
						label: "民族",
						field: "nation",
						placeholder: "请输入",
						span:8,
						formItemLayout: {
							labelCol: {
								xs: { span: 24 },
								sm: { span: 9 }
							},
							wrapperCol: {
								xs: { span: 24 },
								sm: { span: 15 }
							}
						},
					}, 
					{
						type: "string",
						required:true,
						label: "籍贯",
						placeholder: "请输入",
						field: "nativePlace",
						span:8,
						formItemLayout: {
							labelCol: {
								xs: { span: 24 },
								sm: { span: 9 }
							},
							wrapperCol: {
								xs: { span: 24 },
								sm: { span: 15 }
							}
						},
					}, 
					{
						type: "string",
						label: "政治面貌",
						required: true,
						placeholder: "请输入",
						field: "politicsPlace",
						span:8,
						formItemLayout: {
							labelCol: {
								xs: { span: 24 },
								sm: { span: 9 }
							},
							wrapperCol: {
								xs: { span: 24 },
								sm: { span: 15 }
							}
						},
					}, 
					{
						type: "string",
						label: "工作单位",
						required: true,
						placeholder: "请输入",
						field: "workUnit",
						span:8,
						formItemLayout: {
							labelCol: {
								xs: { span: 24 },
								sm: { span: 9 }
							},
							wrapperCol: {
								xs: { span: 24 },
								sm: { span: 15 }
							}
						},
					}, 
					{
						type: "string",
						label: "职务",
						required: true,
						placeholder: "请输入",
						field: "duty",
						span:8,
						formItemLayout: {
							labelCol: {
								xs: { span: 24 },
								sm: { span: 9 }
							},
							wrapperCol: {
								xs: { span: 24 },
								sm: { span: 15 }
							}
						},
					}, 
					{
						type: "string",
						label: "职称",
						required: true,
						placeholder: "请输入",
						field: "professional",
						span:8,
						formItemLayout: {
							labelCol: {
								xs: { span: 24 },
								sm: { span: 9 }
							},
							wrapperCol: {
								xs: { span: 24 },
								sm: { span: 15 }
							}
						},
					}, 
					{
						type: "string",
						required:true,
						label: "户口所在地",
						placeholder: "请输入",
						field: "residentAddress",
						span:8,
						formItemLayout: {
							labelCol: {
								xs: { span: 24 },
								sm: { span: 9 }
							},
							wrapperCol: {
								xs: { span: 24 },
								sm: { span: 15 }
							}
						},
					}, 
					{
						type: "identity",
						label: "身份证号码",
						placeholder: "请输入",
						required:true,
						field: "idCard",
						span:8,
						formItemLayout: {
							labelCol: {
								xs: { span: 24 },
								sm: { span: 9 }
							},
							wrapperCol: {
								xs: { span: 24 },
								sm: { span: 15 }
							}
						},
					}, 
					{
						type: "passport",
						required:true,
						label: "护照号码",
						placeholder: "请输入",
						field: "passportNumber",
						span:8,
						formItemLayout: {
							labelCol: {
								xs: { span: 24 },
								sm: { span: 9 }
							},
							wrapperCol: {
								xs: { span: 24 },
								sm: { span: 15 }
							}
						},
					}, 
					{
						type: "textarea",
						label: "出国（境）事由",
						placeholder: "请输入",
						required: true,
						// colStyle: {
						// 	paddingLeft: '37px'
						// },
						field: "goAbroadReason",
						// span:8,
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
						label: "前往国家/地区",
						placeholder: "请输入",
						required:true,
						field: "goCountry",
						span:8,
						formItemLayout: {
							labelCol: {
								xs: { span: 24 },
								sm: { span: 9 }
							},
							wrapperCol: {
								xs: { span: 24 },
								sm: { span: 15 }
							}
						},
					},
					{
						type: "string",
						label: "预计行程",
						placeholder: "请输入",
						required:true,
						field: "expectedTravel",
						span:8,
						formItemLayout: {
							labelCol: {
								xs: { span: 24 },
								sm: { span: 9 }
							},
							wrapperCol: {
								xs: { span: 24 },
								sm: { span: 15 }
							}
						},
					},
					{
						type: "number",
						label: "同行人数",
						placeholder: "请输入",
						field: "peersNumber",
						required:true,
						initialValue:0,
						span:8,
						formItemLayout: {
							labelCol: {
								xs: { span: 24 },
								sm: { span: 9 }
							},
							wrapperCol: {
								xs: { span: 24 },
								sm: { span: 15 }
							}
						},
					},
					{
						type: "date",
						label: "开始时间",
						required: true,
						placeholder: "请输入",
						field: "startTime",
						initialValue: moment().format('YYYY-MM-DD'),
						span:12,
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
						label: "结束时间",
						placeholder: "请输入",
						required:true,
						field: "endTime",
						initialValue: moment().format('YYYY-MM-DD'),
						span:12,
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
						field: "detailList",
						label: "同行人员信息",
						textObj: {
							add: "添加",
							del: "删除"
						},
						formItemLayout: {
							labelCol: {
								xs: { span: 0 },
								sm: { span: 0 }
							},
							wrapperCol: {
								xs: { span: 24 },
								sm: { span: 24 }
							}
						},
						canAddForm: true,     
						qnnFormConfig: {
							formConfig: [
								// {
								// 	type: "string",
								// 	label: "",
								// 	field: "goAbroadInfoId",
								// 	hide: true
								// },
								{
									type: "string",
									label: "称谓",
									field: "appellation",
									placeholder: "请输入",
									span:12,
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
									label: "姓名",
									field: "infoName",
									placeholder: "请输入",
									span:12,
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
									label: "年龄",
									field: "infoAge",
									placeholder: "请输入",
									span:12,
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
									label: "政治面貌",
									field: "infoPoliticsPlace",
									placeholder: "请输入",
									span:12,
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
									label: "工作单及职务",
									field: "infoWorkUnitAndDuty",
									placeholder: "请输入",
									// span:12,
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
						label: "公司党委书记意见",
						field: "opinionField1",
						addShow: false,
						placeholder: "请输入",
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
					},
					{
						type: "textarea",
						label: "集团分管领导意见",
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
						label: "党委组织部意见",
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
						label: "公司董事长意见",
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
				]}
			/>  </div>
		);
	}
}
export default index;