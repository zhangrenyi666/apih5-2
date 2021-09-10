import React, {
	Component
} from "react";
import {
	Form
} from "../modules/work-flow";
import moment from "moment";
let config = {
	workFlowConfig: {
		title: ["", "", "北京集体户籍入户申请"],
		apiNameByAdd: "addZjFlowRhHouseholdIn",
		apiNameByUpdate: "updateZjFlowRhHouseholdIn",
		apiNameByGet: "getZjFlowRhHouseholdInDetail",
		apiTitle: "setZjFlowRhHouseholdFlowTitle",
		flowId: "jthjrhApply",
		todo: "todoByZjInHome",
		hasTodo: "hasTodoByZjInHome"
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
			hide: function () {
				return true
			},
			initialValue: function (obj) {
				return obj.match.params["workId"];
			}
		},
		{
			type: "string",
			label: "",
			field: "inId",
			hide: function () {
				return true
			},
		},
		{
			type: "string",
			label: "",
			field: "titleFlag",
			hide: function () {
				return true
			},
			initialValue:'1'
		},
		{
			type: "textarea",
			label: "本人申请",
			field: "opinionField1",
			opinionField: true,
			addShow: true,
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
			label: "公司所属单位意见",
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
			label: "公司人力资源部意见",
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
			label: "公司办公室意见",
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
			label: "公司分管领导意见",
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
			label: "备注",
			field: "zhushi",
			qnnDisabled: true,
			addDisabled: true,
			placeholder: "请输入",
			initialValue: `1.公司必须证明申请入户人员为本单位在职职工；\n2.调离的必须先将户口迁出（不含公司所属各单位内流动）\n3.工作单位如有变动须及时通知公司办公室行政外联处。\n4.所需基本材料：\n（1）准迁证\n（2）身份证\n（3）在京房产核查证明`,
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
			isInQnnTable,
			myPublic: { domain,appInfo: { ureport } }
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
				
				btnsCURD={({ btns, flowData }) => {
					if (flowData && flowData.flowNode.nodeId === 'Node8' || flowData && flowData.flowNode.nodeId === 'Node9') {
						var printUrl = `${ureport}excel?_u=file:zjFlowRhHouseholdIn.xml&url=${domain}&workId=${flowData.workId}`
						btns.push({ buttonClass: "exprot",buttonFun: null,buttonId: "exprot",buttonName: "导出",icon: null,printUrl: printUrl });
					}
					return btns;
				}}
			/>  </div>
		);
	}
}
export default index;