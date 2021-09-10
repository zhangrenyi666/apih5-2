import React, {
	Component
} from "react";
import {
	Form
} from "../modules/work-flow";
import moment from "moment";
let config = {
	workFlowConfig: {
		title: ["name","YearAndMonth","请假申请"],
		apiNameByAdd: "addZjFlowLeaveApply",
		apiNameByUpdate: "updateZjFlowLeaveApply",
		apiNameByGet: "getZjFlowLeaveApplyDetail",
		flowId: "zjLeaveApply",
		formLink: {
			zjLeaveApply: "FlowByzjLeaveApply"
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
					label: "请假天数",
					required: true,
					span:12,
					field: "leaveDays",
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
					type: 'select',
					label: "请假类型",
					placeholder: "请选择",
					field: "leaveType",
					optionConfig: {
						label: 'text', 
						value: 'value',
					},
					optionData: [
						{
							value: "0",
							text: "年休假"
						},
						{
							value: "1",
							text: "事假"
						},
						{
							value: "2",
							text: "病假"
						},
						{
							value: "3",
							text: "探亲假"
						},
						{
							value: "4",
							text: "婚假"
						},
						{
							value: "5",
							text: "（陪）产假"
						},
						{
							value: "6",
							text: "丧假"
						},
						{
							value: "7",
							text: "倒休假"
						}
					],
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
					type: "date",
					label: "销假日期",
					required: true,
					field: "terminateDate",
					initialValue:moment().format('YYYY-MM-DD'),
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
					type: "textarea",
					label: "请假原由",
					autosize: { minRows: 2,maxRows: 3 },
					field: "leaveReason",
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
					initialValue: `01 事假：部门、中心、事业部领导以下员工因事请假，一天之内的报本部门、中心、事业部负责人批准，三天（含）以内的报公司分管领导批准，四天以上的党群部门由公司党委书记批准，其他部门、中心由公司总经理批准；部门、中心、事业部领导（含）以上员工因事请假的先报分管领导审批，之后党群部门由公司党委书记批准，其他部门、中心由公司总经理批准；\n02 病假：部门、中心、事业部领导以下员工因病请假，五天之内的报本部门、中心、事业部负责人批准，六天以上十天（含）以内的报公司分管领导批准，十一天以上的党群部门由公司党委书记批准，其他部门、中心由公司总经理批准；部门、中心、事业部领导（含）以上员工因病请假的先报分管领导审批，之后党群部门由公司党委书记批准，其他部门、中心由公司总经理批准；\n03.年休假、婚假、（陪）产假、丧假、倒休假：普通员工报部门、中心、事业部负责人批准；部门、中心、事业部负责人报公司分管领导批准；`,
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