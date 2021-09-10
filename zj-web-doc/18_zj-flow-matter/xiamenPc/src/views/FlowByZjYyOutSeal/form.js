import React, {
	Component
} from "react";
import {
	Form
} from "../modules/work-flow";
import moment from "moment";
let config = {
	workFlowConfig: {
		title: ["applyRealName","yearAndTime","外携用印"],
		apiNameByAdd: "addFlowOutSealInLaunch",
		apiNameByUpdate: "updateFlowOutSealAfterSubmit",
		apiNameByGet: "getFlowOutSealDetailByWorkId",
		flowId: "zjYyOutSeal",
		formLink: {
			zjYyOutSeal: "FlowByzjYyOutSeal"
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
			label: "发文id",
			field: "outSealApplyId",
			hide: true
		}, 
		{
			type: "string",
			label: "",
			required: true,
			hide:true,
			field: "yearAndTime",
			initialValue: moment().format('YYYY-MM-DD'),
			formItemLayout: {
				labelCol: {
					xs: { span: 24 },
					sm: { span:3 }
				},
				wrapperCol: {
					xs: { span: 24 },
					sm: { span: 21 }
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
			label: "外协用印单位",
			required: true,
			field: "outSealUnit",
			placeholder: "请输入",
			formItemLayout: {
				labelCol: {
					xs: { span: 24 },
					sm: { span:3 }
				},
				wrapperCol: {
					xs: { span: 24 },
					sm: { span: 21 }
				}
			},
		},
		{
			type: "string",
			label: "发往单位",
			required: true,
			field: "sendUnit",
			placeholder: "请输入",
			formItemLayout: {
				labelCol: {
					xs: { span: 24 },
					sm: { span:3 }
				},
				wrapperCol: {
					xs: { span: 24 },
					sm: { span: 21 }
				}
			},
		},
		{
			type: "date",
			label: "用印时间",
			required: true,
			field: "applyUseTime",
			initialValue: moment().format('YYYY-MM-DD'),
			formItemLayout: {
				labelCol: {
					xs: { span: 24 },
					sm: { span:3 }
				},
				wrapperCol: {
					xs: { span: 24 },
					sm: { span: 21 }
				}
			},
		},
		{
			type: "textarea",
			label: "内容",
			required: true,
			field: "content",
			placeholder: "请输入",
			formItemLayout: {
				labelCol: {
					xs: { span: 24 },
					sm: { span:3 }
				},
				wrapperCol: {
					xs: { span: 24 },
					sm: { span: 21 }
				}
			},
		},
		{
			type: "select",
			label: "印别",
			required: true,
			field: "sealType",
			placeholder: "请输入",
			initialValue:'0',
			span: 12,
			colStyle: {
				paddingLeft: '10px'
			},
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
			optionConfig: {
				label: 'text', 
				value: 'value',
			},
			optionData: [
				{
					value: "0",
					text: "公章"
				},
				{
					value: "1",
					text: "手签章"
				},
				{
					value: "2",
					text: "人名章"
				},
				{
					value: "3",
					text: "公章,手签章"
				},
				{
					value: "4",
					text: "公章,人名章"
				},
				{
					value: "5",
					text: "手签章,人名章"
				},
				{
					value: "6",
					text: "公章,手签章,人名章"
				}
			]
		},
		{
			type: "number",
			label: "用印次数",
			required: true,
			span: 12,
			initialValue:0,
			field: "useNumber",
			placeholder: "请输入",
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
			label: "经办人",
			required: true,
			field: "applyRealName",
			placeholder: "请输入",
			span: 12,
			colStyle: {
				paddingLeft: '10px'
			},
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
			type: "phone",
			label: "联系电话",
			field: "applyUserPhone",
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
			label: '附件',
			field: 'outSealFileList',
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
		},

		{
			type: "textarea",
			label: "法律审核",
			field: "legalOpinion",
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
			label: "部门负责人",
			field: "depHeadOpinion",
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
			label: "公司分管领导",
			field: "leaderOpinion",
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
			label: "注",
			field: "remark",
			qnnDisabled:true,
			placeholder: "请输入",
			initialValue: `1.涉及授权委托事宜的用印审批，须对口部门合法合规人员审批签字\n2.内容一栏需详细填写用印事由，准确反映用印具体用途。`,
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