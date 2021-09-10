import React, {
	Component
} from "react";
import {
	Form
} from "../modules/work-flow";
import moment from "moment";
let config = {
	workFlowConfig: {
		title: ["traveller","YearAndMonth","出差申请"],
		apiNameByAdd: "addZjFlowTripApply",
		apiNameByUpdate: "updateZjFlowTripApply",
		apiNameByGet: "getZjFlowTripApplyDetail",
		flowId: "zjTripApply",
		formLink: {
			zjTripApply: "FlowByzjTripApply"
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
					field: "tripApplyId",
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
				// {
				// 	label: '申请部门',
				// 	field: "oaDeptList",
				// 	type: 'treeSelect',
				// 	required: true,
				// 	treeSelectOption: {
				// 		selectType: "1",
				// 		search: true,
				// 		maxNumber:1,
				// 		searchParamsKey: "search",
				// 		textObj :{
				// 			loading: 'loading...',
				// 			noData:'暂无数据',  
				// 			rightTitle: '已选择的部门或成员',  
				// 			maxNumber:'选择达到了上限',
				// 			minNumber:'选择个数不足',
				// 		},
				// 		searchApi: "getSysDepartmentAllTree2",
				// 		fetchConfig: {
				// 			apiName: "getSysDepartmentAllTree2"
				// 		}
				// 	},
				// 	span:12,
				// 	formItemLayout: {
				// 		labelCol: {
				// 			xs: { span: 24 },
				// 			sm: { span: 8 }
				// 		},
				// 		wrapperCol: {
				// 			xs: { span: 24 },
				// 			sm: { span: 16 }
				// 		}
				// 	},
				// },
				{
					type: "string",
					label: "出差人",
					required: true,
					span:12,
					field: "traveller",
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
					label: "职别",
					required: true,
					field: "position",
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
					type: "string",
					label: "出差地点",
					required: true,
					field: "tripPlace",
					placeholder: "请输入",
					span: 12,
					colStyle: {
						paddingLeft: '10px'
					},
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
					type: "select",
					label: "交通工具",
					required: true,
					field: "vehicle",
					placeholder: "请输入",
					span: 12,
					// colStyle: {
					// 	paddingLeft: '10px'
					// },
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
							text: "汽车"
						},
						{
							value: "1",
							text: "火车"
						},
						{
							value: "2",
							text: "飞机"
						},
						{
							value: "3",
							text: "其他"
						}
					]
				},
				
				{
					type: "textarea",
					label: "出差事由",
					autosize: { minRows: 2,maxRows: 3 },
					field: "tripReason",
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
					label: "乘坐飞机原因",
					autosize: { minRows: 2,maxRows: 3 },
					field: "flyReason",
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
					type: "qnnForm",
					field: "detailList",
					label: "出差详情",
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
							{
								type: "date",
								label: "开始时间",
								field: "startTime",
								placeholder: "请输入",
								required:true,
								initialValue:moment().format('YYYY-MM-DD'),
								span:12,
								formItemLayout: {
									labelCol: {
										xs: { span: 24 },
										sm: { span:8 }
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
								required:true,
								field: "endTime",
								placeholder: "请输入",
								initialValue:moment().format('YYYY-MM-DD'),
								span:12,
								formItemLayout: {
									labelCol: {
										xs: { span: 24 },
										sm: { span:8 }
									},
									wrapperCol: {
										xs: { span: 24 },
										sm: { span: 16 }
									}
								},
							},
							{
								type: "string",
								label: "开始地点",
								required:true,
								field: "startPlace",
								placeholder: "请输入",
								span:12,
								formItemLayout: {
									labelCol: {
										xs: { span: 24 },
										sm: { span:8 }
									},
									wrapperCol: {
										xs: { span: 24 },
										sm: { span: 16 }
									}
								},
							},
							{
								type: "string",
								label: "结束地点",
								required:true,
								field: "endPlace",
								placeholder: "请输入",
								span:12,
								formItemLayout: {
									labelCol: {
										xs: { span: 24 },
										sm: { span:8 }
									},
									wrapperCol: {
										xs: { span: 24 },
										sm: { span: 16 }
									}
								},
							},
						]
					}
				},
				
				{
					type: "textarea",
					label: "部门领导意见",
					field: "opinionField2",
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
					label: "公司总经理意见",
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
					label: "公司董事长意见",
					field: "opinionField5",
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
					initialValue: `01.北京市内出差：部门、中心、事业部领导以下员工报部门、中心、事业部负责人批准，部门、中心、事业部领导（含）以上员工报公司报分管领导审批；\n02.北京市以外地区出差：部门、中心领导以下员工出差3天以下（含3天）由所在部门、中心负责人批准；3天以上7天以下（含7天）由分管领导批准；7天以上，党群部门由公司党委书记批准，其他部门、中心由公司总经理批准。部门、中心领导（含）以上员工出差7天以下（含7天）由分管领导批准；7天以上，党群部门由公司党委书记批准，其他部门、中心由公司总经理批准； 事业部公司管干部正副职负责人出差，7天以下（含7天）由公司分管领导批准；7天以上，党群部门由公司党委书记批准，其他部门由公司总经理批准。事业部其他人员出差，1天以下（含1天）由所在部门处室负责人批准；2天以上7天以下（含7天）由事业部部门负责人批准；7天以上，党群部门由事业部党委书记批准，其他部门由事业部总经理批准。 加班：部门、中心、事业部领导以下员工报部门、中心、事业部负责人批准，部门、中心、事业部领导（含）以上员工报分管领导批准`,
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