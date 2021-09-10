import React, {
	Component
} from "react";
import {
	Form
} from "../modules/work-flow";
import moment from "moment";
let config = {
	workFlowConfig: {
		title: ["applyUserName","YearAndMonth","五公司用印申请"],
		apiNameByAdd: "addZjFlowYySealFive",
		apiNameByUpdate: "updateZjFlowYySealFive",
		apiNameByGet: "getZjFlowYySealFiveDetail",
		flowId: "wgsYongYin",
		formLink: {
			wgsYongYin: "FlowBywgsYongYin"
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
			field: "sealFiveId",
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
			type: "string",
			label: "用印单位",
			required: true,
			field: "sealUnit",
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
			label: "发往单位",
			required: true,
			field: "sendUnit",
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
			type: "select",
			label: "印别",
			required: true,
			initialValue:'0',
			field: "sealType",
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
			type: "date",
			label: "用印时间",
			required: true,
			initialValue:moment().format('YYYY-MM-DD'),
			span:12,
			field: "useTime",
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
			label: "用印次数",
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
			field: "applyUserName",
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
			type: "textarea",
			label: "用印申请内容",
			required: true,
			field: "content",
			placeholder: "请输入",
			// span:12,
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
			label: "部门负责人",
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
			label: "分管领导",
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
			label: "主要领导",
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
			label: "综合办公室",
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
			qnnDisabled:true,
			placeholder: "请输入",
			initialValue: `注：特殊情况用印需由公司领导审批`,
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