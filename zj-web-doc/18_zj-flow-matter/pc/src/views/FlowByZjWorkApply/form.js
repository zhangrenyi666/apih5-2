import React, {
	Component
} from "react";
import {
	Form
} from "../modules/work-flow";
import moment from "moment";
let config = {
	workFlowConfig: {
		title: ["name","YearAndMonth","加班申请"],
		apiNameByAdd: "addZjFlowOvertimeApply",
		apiNameByUpdate: "updateZjFlowOvertimeApply",
		apiNameByGet: "getZjFlowOvertimeApplyDetail",
		flowId: "zjWorkApply",
		formLink: {
			zjWorkApply: "FlowByzjWorkApply"
		},
		todo: "todoByzjYongYin",
		hasTodo: "hasTodoByzjYongYin"
		
	},
	formTailLayout: {
        wrapperCol: {
            sm: {
                span: 12,
                offset: 12
            }
        }
    }
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
						field: "overtimeApplyId",
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
						type: "select",
						label: "公文类型",
						required: true,
						field: "documentType",
						placeholder: "请输入",
						span:12,
						formItemLayout: {
							labelCol: {
								xs: { span: 24 },
								sm: { span: 8 }
							},
							wrapperCol: {
								xs: { span: 24 },
								sm: { span: 16 }
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
						type: "date",
						label: "申请日期",
						required: true,
						initialValue:moment().format('YYYY-MM-DD'),
						field: "applyDate",
						placeholder: "请输入",
						span:12,
						formItemLayout: {
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
						label: "姓名",
						required: true,
						qnnDisabled:true,
						initialValue: (obj) => {
							if (obj.loginAndLogoutInfo) {
								return obj.loginAndLogoutInfo.loginInfo.userInfo.realName
							} else {
								return {}
							}
						},
						field: "name",
						placeholder: "请输入",
						span: 12,
						formItemLayout: {
							labelCol: {
								xs: { span: 24 },
								sm: { span: 8 }
							},
							wrapperCol: {
								xs: { span: 24 },
								sm: { span:16 }
							}
						}
					},
					{
						label: '部门',
						field: 'oaDeptList',
						type: 'treeSelect',
						required: true,
						initialValue: function (obj) {
							var userInfo = obj.loginAndLogoutInfo.loginInfo.userInfo;
							var departmentList = userInfo.departmentList.map(item => {
								return {
									label: item.departmentName,
									value: item.departmentId,
									type: "1",
								}
							});
							if (departmentList && departmentList.length) {
								return departmentList
							}
							return [];
						},
						treeSelectOption: {
							help: true,
							selectType: "1",
							maxNumber: 1,
							fetchConfig: {
								apiName: "getSysDepartmentAllTree2"
							},
							search: true,
							searchPlaceholder: '姓名、账号、电话',
							searchParamsKey: 'search',
							searchOtherParams: { pageSize: 999 }
						},
						span:12,
						formItemLayout: {
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
						label: "加班天数",
						required: true,
						span:12,
						field: "overtimeDays",
						placeholder: "请输入",
						formItemLayout: {
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
						label: "倒休天数",
						required: true,
						field: "offDays",
						placeholder: "请输入",
						span: 12,
						formItemLayout: {
							labelCol: {
								xs: { span: 24 },
								sm: { span: 8 }
							},
							wrapperCol: {
								xs: { span: 24 },
								sm: { span:16 }
							}
						}
					},
				
					{
						type: "date",
						label: "开始时间",
						required: true,
						initialValue:moment().format('YYYY-MM-DD'),
						field: "startTime",
						placeholder: "请输入",
						span:12,
						formItemLayout: {
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
						type: "date",
						label: "结束时间",
						required: true,
						initialValue:moment().format('YYYY-MM-DD'),
						field: "endTime",
						placeholder: "请输入",
						span:12,
						formItemLayout: {
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
						type: "textarea",
						label: "加班事由",
						autosize: { minRows: 2,maxRows: 3 },
						field: "overtimeReason",
						placeholder: "请输入",
						formItemLayout: {
							labelCol: {
								xs: { span: 24 },
								sm: { span: 4 }
							},
							wrapperCol: {
								xs: { span: 24 },
								sm: { span: 20 }
							}
						},
					},
					{
						type: "textarea",
						label: "部门负责人意见",
						field: "opinionField1",
						addShow: false,
						placeholder: "请输入",
						opinionField: true,
						formItemLayout: {
							labelCol: {
								xs: { span: 24 },
								sm: { span: 4 }
							},
							wrapperCol: {
								xs: { span: 24 },
								sm: { span: 20 }
							}
						},
					},
					{
						type: "textarea",
						label: "公司分管领导意见",
						field: "opinionField2",
						opinionField: true,
						addShow: false,
						placeholder: "请输入",
						formItemLayout: {
							labelCol: {
								xs: { span: 24 },
								sm: { span: 4 }
							},
							wrapperCol: {
								xs: { span: 24 },
								sm: { span: 20 }
							}
						},
					},
					{
						type: "textarea",
						label: "公司总经理意见",
						field: "opinionField3",
						opinionField: true,
						addShow: false,
						placeholder: "请输入",
						formItemLayout: {
							labelCol: {
								xs: { span: 24 },
								sm: { span: 4 }
							},
							wrapperCol: {
								xs: { span: 24 },
								sm: { span: 20 }
							}
						},
					},
					{
						type: "textarea",
						label: "公司董事长意见",
						field: "opinionField4",
						opinionField: true,
						addShow: false,
						placeholder: "请输入",
						formItemLayout: {
							labelCol: {
								xs: { span: 24 },
								sm: { span: 4 }
							},
							wrapperCol: {
								xs: { span: 24 },
								sm: { span: 20 }
							}
						},
					},
					{
						type: "textarea",
						label: "注",
						field: "remark",
						qnnDisabled: true,
						autosize: { minRows: 3,maxRows: 4 },
						placeholder: "请输入",
						initialValue: `部门、中心领导以下员工报部门、中心负责人批准，部门、中心领导（含）以上员工报主管领导批准`,
						formItemLayout: {
							labelCol: {
								xs: { span: 24 },
								sm: { span: 4 }
							},
							wrapperCol: {
								xs: { span: 24 },
								sm: { span: 20 }
							}
						},
					},
				]}
			/>  </div>
		);
	}
}
export default index;