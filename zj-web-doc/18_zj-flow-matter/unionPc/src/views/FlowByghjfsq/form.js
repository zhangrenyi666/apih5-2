import React, {
	Component
} from "react";
import {
	Form
} from "../modules/work-flow";
import moment from "moment";
let config = {
	workFlowConfig: {
		title: ["handler","title_sendTime","工会经费申请"], //标题字段
		apiNameByAdd: "addZjFlowUnionDues",
		apiNameByUpdate: "updateZjFlowUnionDues",
		apiNameByGet: "getZjFlowUnionDuesDetails",
		flowId: "ghjfsq",
		todo: "todoByghjfsq",
		hasTodo: "hasTodoByghjfsq"
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
            label: "申请时间format",
            field: "title_sendTime",
            placeholder: "请输入",
            initialValue: moment().format("YYYY-MM-DD HH:mm:ss"),
            hide: true
        },
		{
			type: "string",
			label: "",
			field: "unionDuesId",
			hide: function () {
				return true
			},
		},
		{
			type: "string",
			label: "经办人",
			field: "handler",
			required: true,
			placeholder: "请输入",
			formItemLayout: {
				labelCol: {
					xs: { span: 5 },
					sm: { span: 5 }
				},
				wrapperCol: {
					xs: { span: 19 },
					sm: { span: 19 }
				}
			},
		},
		{
			type: "string",
			label: "电话",
			field: "phone",
			required: true,
			placeholder: "请输入",
			formItemLayout: {
				labelCol: {
					xs: { span: 5 },
					sm: { span: 5 }
				},
				wrapperCol: {
					xs: { span: 19 },
					sm: { span: 19 }
				}
			},
		},	
		{
			type: "string",
			label: "申请单位",
			field: "appUnit",
			required: true,
			placeholder: "请输入",
			formItemLayout: {
				labelCol: {
					xs: { span: 5 },
					sm: { span: 5 }
				},
				wrapperCol: {
					xs: { span: 19 },
					sm: { span: 19 }
				}
			},
		},
		{
			type: "string",
			label: "工会主席",
			field: "laborUnionChairman",
			required: true,
			placeholder: "请填写工会主席姓名",
			formItemLayout: {
				labelCol: {
					xs: { span: 5 },
					sm: { span: 5 }
				},
				wrapperCol: {
					xs: { span: 19 },
					sm: { span: 19 }
				}
			},
		},
		{
			type: "number",
			label: "申请金额",
			field: "appAmountSmall",
			required: true,
			onChange:(value,props) => {
				props.form.setFieldsValue({apiBody: {suggestAmountSmall: value}})
			},
			placeholder: "请输入",
			formItemLayout: {
				labelCol: {
					xs: { span: 5 },
					sm: { span: 5 }
				},
				wrapperCol: {
					xs: { span: 19 },
					sm: { span: 19 }
				}
			},
		},
		{
			type: "textarea",
			label: "申请原因",
			field: "appReason",
			required: true,
			placeholder: "请输入",
			formItemLayout: {
				labelCol: {
					xs: { span: 5 },
					sm: { span: 5 }
				},
				wrapperCol: {
					xs: { span: 19 },
					sm: { span: 19 }
				}
			},
		},
		{
			type: "textarea",
			label: "资金使用计划",
			field: "fundUsePlan",
			required: true,
			placeholder: "请输入",
			formItemLayout: {
				labelCol: {
					xs: { span: 5 },
					sm: { span: 5 }
				},
				wrapperCol: {
					xs: { span: 19 },
					sm: { span: 19 }
				}
			},
		},
		{
			type: "string",
			label: "收款单位账号名称",
			field: "payeeName",
			required: true,
			placeholder: "请输入收款单位账号名称",
			formItemLayout: {
				labelCol: {
					xs: { span: 5 },
					sm: { span: 5 }
				},
				wrapperCol: {
					xs: { span: 19 },
					sm: { span: 19 }
				}
			},
		},
		{
			type: "string",
			label: "银行账号",
			field: "bankAccount",
			required: true,
			placeholder: "请输入银行账号",
			formItemLayout: {
				labelCol: {
					xs: { span: 5 },
					sm: { span: 5 }
				},
				wrapperCol: {
					xs: { span: 19 },
					sm: { span: 19 }
				}
			},
		},
		{
			type: "string",
			label: "开户银行及网点",
			field: "bank",
			required: true,
			placeholder: "请输入开户银行及网点",
			formItemLayout: {
				labelCol: {
					xs: { span: 5 },
					sm: { span: 5 }
				},
				wrapperCol: {
					xs: { span: 19 },
					sm: { span: 19 }
				}
			},
		},
		{
			type: "textarea",
			label: "所在单位工会意见",
			field: "opinionField1",
			opinionField: true,
			addShow: false,
			placeholder: "请输入",
			formItemLayout: {
				labelCol: {
					xs: { span: 5 },
					sm: { span: 5 }
				},
				wrapperCol: {
					xs: { span: 19 },
					sm: { span: 19 }
				}
			},
		},
		{
			type: "money",
			label: "建议金额",
			field: "suggestAmountSmall",
			hide: (obj) => {
				if(obj.rowData){
					return false;
				}else{
					return true;
				}
			},
			placeholder: "请输入",
			formItemLayout: {
				labelCol: {
					xs: { span: 5 },
					sm: { span: 5 }
				},
				wrapperCol: {
					xs: { span: 19 },
					sm: { span: 19 }
				}
			},
		},
		{
			type: "textarea",
			label: "工会办公室负责人意见",
			field: "opinionField3",
			opinionField: true,
			addShow: false,
			placeholder: "请输入",
			formItemLayout: {
				labelCol: {
					xs: { span: 5 },
					sm: { span: 5 }
				},
				wrapperCol: {
					xs: { span: 19 },
					sm: { span: 19 }
				}
			},
		},
		{
			type: "textarea",
			label: "集团工会主席意见",
			field: "opinionField2",
			opinionField: true,
			addShow: false,
			placeholder: "请输入",
			formItemLayout: {
				labelCol: {
					xs: { span: 5 },
					sm: { span: 5 }
				},
				wrapperCol: {
					xs: { span: 19 },
					sm: { span: 19 }
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
					xs: { span: 5 },
					sm: { span: 5 }
				},
				wrapperCol: {
					xs: { span: 19 },
					sm: { span: 19 }
				}
			},
		},
		{
			type: "textarea",
			label: "备注",
			field: "remarks",
			qnnDisabled: true,
			placeholder: "请输入",
			initialValue: '本单作为工会放款的辅助单据一起入账。',
			formItemLayout: {
				labelCol: {
					xs: { span: 5 },
					sm: { span: 5 }
				},
				wrapperCol: {
					xs: { span: 19 },
					sm: { span: 19 }
				}
			},
		},
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
					if (flowData && flowData.flowNode.nodeId === 'Node4' || flowData && flowData.flowNode.nodeId === 'Node7') {
						var printUrl = `${ureport}pdf?_u=file:zjFlowUnionDues.xml&url=${domain}&workId=${flowData.workId}&_n=工会经费流程申请单-${moment(new Date()).format('YYYY-MM-DD')}`
						btns.push({ buttonClass: "exprot",buttonFun: null,buttonId: "exprot",buttonName: "导出",icon: null,printUrl: printUrl });
					}
					return btns;
				}}
				fieldsCURD={(obj, flowData, props) => {
					if(flowData?.flowNode?.nodeId === 'Node6'){
						obj = obj.map((item) => {
							if(item.field === 'suggestAmountSmall'){
								item.disabled = false;
								item.required = true;
							}
							return item;
						})
					}
					return obj;
				}}
			/>  </div>
		);
	}
}
export default index;