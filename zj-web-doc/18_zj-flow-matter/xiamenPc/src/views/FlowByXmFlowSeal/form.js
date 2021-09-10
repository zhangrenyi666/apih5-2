import React, {
	Component
} from "react";
import {
	Form
} from "../modules/work-flow";
import moment from "moment";
let config = {
	workFlowConfig: {
		title: ["sealPro","YearAndMonth","厦门用印（公司）申请"],
		apiNameByAdd: "addXmFlowSeal",
		apiNameByUpdate: "updateXmFlowSeal",
		apiNameByGet: "getXmFlowSealDetail",
		flowId: "xmFlowSeal",
		formLink: {
			xmFlowSeal: "FlowByxmFlowSeal"
		},
		todo: "todoByxmFlowSeal",
		hasTodo: "hasTodoByxmFlowSeal"
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
			hide: true,
			initialValue: function (obj) {
				return obj.match.params["workId"];
			}
		},
		{
			type: "string",
			label: "",
			field: "sealId",
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
			label: "用印项目",
			required: true,
			field: "sealPro",
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
			type: "string",
			label: "发往单位",
			required: true,
			field: "sendUnit",
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
			label: "内容",
			required: true,
			field: "sealContent",
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
			type: "select",
			label: "印别",
			required: true,
			initialValue:'0',
			field: "sealType",
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
					text: "公司公章"
				},
				{
					value: "1",
					text: "法人章"
				},                              
				{
					value: "2",
					text: "公司公章,法人章"
				}   
			]
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
		},
		{
			type: "textarea",
			label: "部门负责人",
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
			label: "分管领导",
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
			label: "综合办公室",
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
			label: "主要领导",
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