import React, {
	Component
} from "react";
import {
	Form
} from "../modules/work-flow";
import moment from "moment";
let config = {
	workFlowConfig: {
		title: ["handler", "title_sendTime", "工会会员慰问金或慰问品申请"], //标题字段
		apiNameByAdd: "addZjFlowUnionMemberSolatium",
		apiNameByUpdate: "updateZjFlowUnionMemberSolatium",
		apiNameByGet: "getZjFlowUnionMemberSolatiumDetails",
		flowId: "ghwwjsq",
		todo: "todoByghwwjsq",
		hasTodo: "hasTodoByghwwjsq"
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
			label: "姓名",
			field: "name",
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
			type: "select",
			label: "性别",
			field: "sex",
			required: true,
			placeholder: "请选择",
			optionData: [
				{
					name: '男',
					id: '0'
				},
				{
					name: '女',
					id: '1'
				},
			],
			optionConfig: {
				label: 'name',
				value: 'id'
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
			type: "number",
			label: "年龄",
			field: "age",
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
			label: "所在单位或部门",
			field: "unitDept",
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
			type: "select",
			label: "申请金额或物品",
			field: "solatium",
			required: true,
			optionData: (obj) => {
				if(obj.rowData){
					return [
						{
							label:'1000元',
							value:1000
						},
						{
							label:'2000元',
							value:2000
						},
						{
							label:'3000元',
							value:3000
						},
						{
							label:'4000元',
							value:4000
						},
						{
							label:'5000元',
							value:5000
						},
						{
							label:'780元',
							value:780
						},
						{
							label:'830元',
							value:830
						},
						{
							label:'980元',
							value:980
						},
						{
							label:'920元',
							value:920
						},
						{
							label:'950元',
							value:950
						},
						{
							label:'1000元',
							value:1000
						}
					]
				}else{
					return [
						{
							label:'1000元',
							value:1000
						},
						{
							label:'2000元',
							value:2000
						},
						{
							label:'3000元',
							value:3000
						},
						{
							label:'4000元',
							value:4000
						},
						{
							label:'5000元',
							value:5000
						},
						{
							label:'磁悬浮台灯（退休）Ф15cm白光，780元',
							value:780
						},
						{
							label:'磁悬浮台灯（退休）Ф15cm彩光，830元',
							value:830
						},
						{
							label:'磁悬浮台灯（退休）Ф18cm白光），980元',
							value:980
						},
						{
							label:'一公局集团办公大楼模型（退休）、920元',
							value:920
						},
						{
							label:'中交集团纪念杯（退休），950元',
							value:950
						},
						{
							label:'其他(限额1000元)',
							value:1000
						}
					]
				}
			},
			optionConfig: {
				label: 'label',
				value: 'value'
			},
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
			label: "所在项目(工会小组)意见",
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
			type: "textarea",
			label: "所在单位工会主席意见",
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
			type: "number",
			label: "建议申请金额或物品",
			field: "suggestAmountSmall",
			placeholder: "请输入",
			hide: (obj) => {
				if(obj.rowData){
					return false;
				}else{
					return true;
				}
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
			label: "集团工会办公室负责人意见",
			field: "opinionField4",
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
			initialValue: `1.会员生育、结婚、生病住院、会员去世、直系亲属去世慰问等，请随报出生证明、结婚证、诊断书、住院证明等证明文件扫描件；\n2.申请退休纪念品，请注明纪念品名称；\n3.本单作为机关工会发放慰问金或慰问品的原始单据入账。`,
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
			myPublic: { domain, appInfo: { ureport } }
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
					if (flowData && flowData.flowNode.nodeId === 'Node6' || flowData && flowData.flowNode.nodeId === 'Node9') {
						var printUrl = `${ureport}pdf?_u=file:zjFlowUnionMemberSolatium.xml&url=${domain}&workId=${flowData.workId}&_n=工会慰问金流程申请单-${moment(new Date()).format('YYYY-MM-DD')}`
						btns.push({ buttonClass: "exprot", buttonFun: null, buttonId: "exprot", buttonName: "导出", icon: null, printUrl: printUrl });
					}
					return btns;
				}}
			fieldsCURD={(obj, flowData, props) => {
				if(flowData?.flowNode?.nodeId === 'Node8'){
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