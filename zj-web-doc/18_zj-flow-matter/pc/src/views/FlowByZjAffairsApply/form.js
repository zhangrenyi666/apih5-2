import React, {
	Component
} from "react";
import {
	Form
} from "../modules/work-flow";
import moment from "moment";
let config = {
	workFlowConfig: {
		title: ["deptLinkman","yrar","局机关事务申请"],
		apiNameByAdd: "addZjFlowAffairsApply",
		apiNameByUpdate: "updateZjFlowAffairsApply",
		apiNameByGet: "getZjFlowAffairsApplyDetail",
		flowId: "zjAffairsApply",
		formLink: {
			zjAffairsApply: "FlowByzjAffairsApply"
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
    },
	//qnn-form配置
	formConfig: [ 
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
			field: "sealApplyId",
			hide: true
		}, 
		{
			type: "string",
			label: "",
			required: true,
			field: "yrar",
			hide:true,
			initialValue: moment().format('YYYY-MM-DD'),
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
			type: "select",
			label: "公文类型",
			required: true,
			field: "documentType",
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
			field: "applyDate",
			initialValue: moment().format('YYYY-MM-DD'),
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
			}
		},
		{
			label: '申请部门',
			field: "oaDeptList",
			type: 'treeSelect',
			required: true,
			treeSelectOption: {
				selectType: "1",
				search: true,
				maxNumber:1,
				searchParamsKey: "search",
				textObj :{
					loading: 'loading...',
					noData:'暂无数据',  
					rightTitle: '已选择的部门或成员',  
					maxNumber:'选择达到了上限',
					minNumber:'选择个数不足',
				},
				searchApi: "getSysDepartmentAllTree2",
				fetchConfig: {
					apiName: "getSysDepartmentAllTree2"
				}
			},
			span: 12,
			formItemLayout: {
				labelCol: {
					xs: { span: 24 },
					sm: { span: 8 }
				},
				wrapperCol: {
					xs: { span: 24 },
					sm: { span: 16 }
				}
			}
		},
		{
			type: "textarea",
			label: "申请事项",
			required: true,
			field: "applyMatter",
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
			type: "string",
			label: "部门联络人",
			required: true,
			field: "deptLinkman",
			placeholder: "请输入",
			span: 8,
			colStyle: {
				paddingLeft: '10px'
			},
			formItemLayout: {
				labelCol: {
					xs: { span: 24 },
					sm: { span: 12 }
				},
				wrapperCol: {
					xs: { span: 24 },
					sm: { span:12 }
				}
			},
		},
		{
			type: "string",
			label: "房间号",
			required: true,
			field: "roomNumber",
			placeholder: "请输入",
			span: 8,
			formItemLayout: {
				labelCol: {
					xs: { span: 24 },
					sm: { span: 12 }
				},
				wrapperCol: {
					xs: { span: 24 },
					sm: { span:12 }
				}
			},
		},
		{
			type: "phone",
			label: "联系方式",
			required: true,
			field: "phone",
			placeholder: "请输入",
			span: 8,
			formItemLayout: {
				labelCol: {
					xs: { span: 24 },
					sm: { span: 12 }
				},
				wrapperCol: {
					xs: { span: 24 },
					sm: { span:12 }
				}
			},
		},
		{
			type: "textarea",
			label: "内容及原因",
			required: true,
			field: "contectReason",
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
			label: '附件',
			field: 'fileList',
			type: 'files',
			fetchConfig: {
				apiName: window.configs.domain + 'upload',
			},
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
			label: "申请部门负责人",
			field: "opinionField1",
			// field: "opinionField1",
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
			label: "申请部门公司分管领导",
			field: "opinionField2",
			// field: "opinionField2",
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
			label: "办公室负责人",
			field: "opinionField3",
			// field: "opinionField3",
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
			label: "公司分管领导",
			field: "opinionField4",
			// field: "opinionField3",
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
		}
    ]
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
			/>  </div>
		);
	}
}
export default index;