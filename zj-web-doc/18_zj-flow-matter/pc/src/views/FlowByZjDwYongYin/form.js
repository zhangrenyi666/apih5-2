import React, {
	Component
} from "react";
import {
	Form
} from "../modules/work-flow";
import moment from "moment";
let config = {
	workFlowConfig: {
		title: ["applyUserId","YearAndMonth","党委用印申请"],
		apiNameByAdd: "addFlowSealInLaunch",
		apiNameByUpdate: "updateFlowSealAfterSubmit",
		apiNameByGet: "getFlowSealDetailByWorkId",
		flowId: "zjDwYongYin",
		formLink: {
			zjDwYongYin: "FlowByzjDwYongYin"
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
			field: "YearAndMonth",
			hide: true,
			initialValue:moment().format('YYYY-MM-DD')
		},
		{
			type: "string",
			label: "用印单位",
			required: true,
			field: "sendUnitId",
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
			label: "发往单位",
			required: true,
			field: "sealUnitId",
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
			label: "用印申请内容",
			required: true,
			field: "content",
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
			type: "select",
			label: "印别",
			required: true,
			initialValue:'0',
			field: "sealType",
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
					text: "党委印章"
				} 
			]
		},
		{
			type: "number",
			label: "用印次数",
			initialValue:0,
			required: true,
			span:12,
			field: "useNumber",
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
			label: "经办人",
			required: true,
			field: "applyUserId",
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
			type: "phone",
			label: "联系电话",
			field: "applyUserPhone",
			required: true,
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
			label: '附件',
			field: 'sealFileList',
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
			label: "法务部意见",
			field: "legalOpinion",
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
			label: "部门负责人意见",
			field: "depHeadOpinion",
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
			label: "公司分管领导意见",
			field: "leaderOpinion",
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
			label: "注",
			field: "remark",
			qnnDisabled:true,
			placeholder: "请输入",
			initialValue: `1.涉及授权委托事宜的用印审批，须对口部门合法合规人员审批签字\n2.内容一栏需详细填写用印事由，准确反映用印具体用途。`,
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