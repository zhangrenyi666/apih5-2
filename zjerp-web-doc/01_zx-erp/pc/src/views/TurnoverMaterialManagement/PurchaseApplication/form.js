import React, {
	Component
} from "react";
import {
	Form
} from "../../modules/work-flow";
let config = {
	editDocCdnAddress: window.configs.ntkoAddress,
	workFlowConfig: {
		title: ["周转材料购置", "billNo"],
		apiNameByAdd: "updateZxSkPurchase",
		apiNameByUpdate: "updateZxSkPurchase",
		apiNameByGet: "getZxSkPurchaseDetail",
		flowId: "zxSkPurchaseWorkId",
		todo: "TodoList",
		hasTodo: "HasTodoList"
	},
	parameterName: 'orgID',
	formTailLayout: {
		wrapperCol: {
			sm: {
				span: 12,
				offset: 12
			}
		}
	},
	//切换布局
	formLayout: "leftDoc",
	//是否使用描述式表单
	formType: "descriptions",
	descriptionsConfig: {
		"layout": "horizontal",
		"column": 12,
		size: "small"
	},
	isHaveDoc: true,
	docFieldLable: "公文正文",
	docFieldName: "zxZhengWenFileList",//???
	docFieldIsRequired: true,
	docIsReadOnly: false,
	docFormFormItemLayout: {
		labelCol: {
			xs: { span: 3 },
			sm: { span: 3 }
		},
		wrapperCol: {
			xs: { span: 20 },
			sm: { span: 20 }
		}
	},
	//左侧公文附件字段名字  
	filesFieldName: "zxErpFileList",//???
	//请求左侧待办已办列表的ajax配置
	//@curList 当前处于什么列表 todo(待办)  hasTodo(已办)

	getTodoDataFetchConfig: window.qnnGetTodoDataFetchConfig,

	// 打开流程表单后请求某个接口获取数据并赋值到表单中
	// 只有创建流程的表单会调用这个接口，处理流程时候不会调用

	// 这个方法和flowData默认赋值会不会冲突？？？
	fetchConfigByinitialValues: {  //可为函数
		apiName: "getZxSkPurchaseDetail",
		// flowData是传入到组件中的
		otherParams: ({ props: { flowData } }) => {

			return {
				id: flowData['id']
			}
		}, //可为函数
	}
};
class index extends Component {
	constructor(props) {
		super(props);
		this.state = {
			lockProjectId: props.loginAndLogoutInfo.loginInfo.userInfo.lockProject && props.loginAndLogoutInfo.loginInfo.userInfo.lockProject.projectId ? props.loginAndLogoutInfo.loginInfo.userInfo.lockProject.projectId : ''
		}
	}
	render() {
		const {
			isInQnnTable, flowData, match: { url }
		} = this.props;
		this.props.match.path = url + '/TodoList';
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
				wrappedComponentRef={(me) => {
					this.flowForm = me;
				}}
				todoListModuleShow={true}
				doctModuleShow={true}
				filesModuleShow={true}
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
						field: "id",
						hide: true
					},
					{
						type: "string",
						label: "单据编号",
						qnnDisabled: true,
						field: "billNo",
						placeholder: "请输入",
						span: 12,
					},
					{
						label: "公司名称",
						required: true,
						qnnDisabled: true,
						field: 'comName',
						type: 'string',
						span: 12,
					},
					{
						label: "",
						hide: true,
						initialValue: flowData && flowData.orgID ? flowData.orgID : '',
						field: 'orgID',
						type: 'string'
					},
					{
						label: "项目名称",
						required: true,
						qnnDisabled: true,
						initialValue: flowData && flowData.orgName ? flowData.orgName : '',
						field: 'orgName',
						type: 'string',
						span: 12,
					},
					{
						type: "number",
						label: "购置金额(万元)",
						qnnDisabled: true,
						field: "purchaseAmt",
						placeholder: "请输入",
						span: 12,
					},
					{
						label: '发起人',
						type: 'string',
						qnnDisabled: true,
						field: 'beginPer',
						span: 12,
					},
					{
						label: '填报日期',
						type: 'date',
						qnnDisabled: true,
						field: 'reportDate',
						span: 12,
					},
					{
						type: "textarea",
						label: "审批内容",
						qnnDisabled: true,
						field: "approval",
						placeholder: "请输入",
						span: 12,
					},

					{
						type: "textarea",
						label: "公司主管部门",
						field: "opinionField1",
						opinionField: true,
						span: 12,
						addShow: false
					},
					{
						type: "textarea",
						label: "材料主管领导",
						field: "opinionField2",
						opinionField: true,
						span: 12,
						addShow: false
					},
					{
						type: "textarea",
						label: "部门会签",
						field: "opinionField3",
						opinionField: true,
						span: 12,
						addShow: false
					},
					{
						type: "textarea",
						label: "项目归档",
						field: "opinionField4",
						opinionField: true,
						span: 12,
						addShow: false
					}
				]}
			/>
		</div>
		);
	}
}
export default index;