import React, {
	Component
} from "react";
import {
	Form
} from "../modules/work-flow";
import moment from "moment";
let config = {
	workFlowConfig: {
		title: ["verifier","YearAndMonth","信息化需求确认"],
		apiNameByAdd: "addFlowNeedsConfirmInLaunch",
		apiNameByUpdate: "updateFlowNeedsConfirmAfterSubmit",
		apiNameByGet: "getFlowNeedsConfirmDetailByWorkId",
		flowId: "flowIdZj1",
		formLink: {
			flowIdZj1: "FlowByflowIdZj1"
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
			field: "needsConfirmId",
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
			// span: 12,
			formItemLayout: {
				labelCol: {
					xs: { span: 24 },
					sm: { span: 4 }
				},
				wrapperCol: {
					xs: { span: 24 },
					sm: { span: 20 }
				}
			}
		},
		{
			type: "string",
			label: "业务模块",
			required: true,
			field: "serviceModule",
			placeholder: "请输入",
			// span: 12,
			formItemLayout: {
				labelCol: {
					xs: { span: 24 },
					sm: { span: 4 }
				},
				wrapperCol: {
					xs: { span: 24 },
					sm: { span: 20 }
				}
			}
		},
		{
			type: "string",
			label: "确认人",
			required: true,
			field: "verifier",
			placeholder: "请输入",
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
			type: "phone",
			label: "联系方式",
			required: true,
			field: "phone",
			placeholder: "请输入",
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
			type: "select",
			label: "工作内容",
			required: true,
			field: "workContent",
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
			optionConfig: {
				label: 'text', 
				value: 'value',
			},
			optionData: [
				{
					value: "0",
					text: "系统分析"
				},
				{
					value: "1",
					text: "系统开发"
				},
				{
					value: "2",
					text: "系统测试"
				},
				{
					value: "3",
					text: "现场验证"
				},
				{
					value: "4",
					text: "系统分析   、  系统开发"
				},
				{
					value: "5",
					text: "系统分析   、  系统测试"
				},
				{
					value: "6",
					text: "系统分析   、 现场验证"
				},
				{
					value: "7",
					text: "系统开发   、  系统测试"
				},
				{
					value: "8",
					text: "系统开发   、   现场验证"
				},
				{
					value: "9",
					text: "系统测试   、   现场验证"
				},
				{
					value: "10",
					text: "系统分析   、系统开发  、 系统测试"
				},
				{
					value: "11",
					text: "系统分析   、系统开发  、 现场验证"
				},
				{
					value: "12",
					text: "系统开发   、系统测试  、 现场验证"
				},
				{
					value: "13",
					text:
						"系统分析   、系统开发  、 系统测试 、 现场验证"
				}
			]
		},
		{
			type: "date",
			label: "期望完成时间",
			span: 12,
			initialValue:moment().format('YYYY-MM-DD'),
			field: "estimatedTime",
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
			type: "textarea",
			label: "基础需求",
			field: "contentDesc",
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
			label: "审核流程要求",
			field: "opinionField7",
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
			label: "汇总及输出要求",
			field: "opinionField8",
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
			label: "展现形式要求",
			field: "opinionField9",
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
			label: "各级权限要求",
			field: "opinionField10",
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
			field: 'needsConfirmFileList',
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
			label: "分管领导意见",
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
			label: "其他部门意见",
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
			label: "业务部门意见",
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
			label: "信息化管理部意见",
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