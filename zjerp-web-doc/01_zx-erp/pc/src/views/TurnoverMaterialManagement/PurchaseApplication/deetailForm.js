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
		hasTodo: "HasTodoList",
	},
	parameterName: 'projectId',
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
};
class index extends Component {
	constructor(props) {
		super(props);
		this.state = {
			list: null,
			lockProjectId: props.loginAndLogoutInfo.loginInfo.userInfo.lockProject && props.loginAndLogoutInfo.loginInfo.userInfo.lockProject.projectId ? props.loginAndLogoutInfo.loginInfo.userInfo.lockProject.projectId : '',
			flowButtons: null
		}
	}
	componentWillMount() {
		const {
			flowData
		} = this.props;
		let paramsArry = {
			workId: flowData.workId,
			apiName: "getZxSkPurchaseDetail",
			apiType: "POST",
			flowId: "zxSkPurchaseWorkId",
			title: "周转材料购置-" + flowData.billNo
		};
		this.props.myFetch('openFlowByReport', paramsArry).then(({ success, message, data }) => {
			if (success) {
				// let list = eval('(' + data.apiData + ')');
				let list = JSON.parse(data.apiData);
				this.setState({
					list: list,
					flowButtons: data?.flowButtons || []
				});
			} else {
			}
		});
	}
	render() {
		const {
			isInQnnTable, match: { url }
		} = this.props;
		const { list, flowButtons } = this.state;
		this.props.match.path = url + '/HasTodoList';
		return (<div style={
			{
				height: isInQnnTable ? "" : "50vh"
			}
		} >
			{list ? <Form
				{...this.props}
				{...config}
				{...this.props.workFlowConfig}
				{...config.workFlowConfig}
				todoListModuleShow={true}
				doctModuleShow={true}
				filesModuleShow={true}
				flowButtons={flowButtons}
				initialValueByDocs={list?.zxZhengWenFileList}//公文正文默认值
				initialValueByFiles={list?.zxErpFileList}//公文附件默认值
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
						initialValue: list?.id,
						hide: true
					},
					{
						type: "string",
						label: "单据编号",
						qnnDisabled: true,
						initialValue: list?.billNo,
						field: "billNo",
						placeholder: "请输入",
						span: 12,
					},
					{
						label: "公司名称",
						required: true,
						qnnDisabled: true,
						initialValue: list?.comName,
						field: 'comName',
						type: 'string',
						span: 12,
					},
					{
						label: "",
						hide: true,
						initialValue: list?.orgID,
						field: 'orgID',
						type: 'string'
					},
					{
						label: "项目名称",
						required: true,
						qnnDisabled: true,
						initialValue: list?.orgName,
						field: 'orgName',
						type: 'string',
						span: 12,
					},
					{
						type: "number",
						label: "购置金额(万元)",
						qnnDisabled: true,
						initialValue: list?.purchaseAmt,
						field: "purchaseAmt",
						placeholder: "请输入",
						span: 12,
					},
					{
						label: '发起人',
						type: 'string',
						qnnDisabled: true,
						initialValue: list?.beginPer,
						field: 'beginPer',
						span: 12,
					},
					{
						label: '填报日期',
						type: 'date',
						qnnDisabled: true,
						initialValue: list?.reportDate,
						field: 'reportDate',
						span: 12,
					},
					{
						type: "textarea",
						label: "审批内容",
						qnnDisabled: true,
						field: "approval",
						initialValue: list?.approval,
						placeholder: "请输入",
						span: 12,
					},

					{
						type: "textarea",
						label: "公司主管部门",
						field: "opinionField1",
						opinionField: true,
						span: 12,
						qnnDisabled: true,
						initialValue: () => {
							// Unnecessary escape character: \<  \>不必要的转义字符
							let data = list && list.opinionField1 ? (list.opinionField1).replace(/<br\/>/g, "\n") : '';
							return data
						}
					},
					{
						type: "textarea",
						label: "材料主管领导",
						field: "opinionField2",
						opinionField: true,
						span: 12,
						qnnDisabled: true,
						initialValue: () => {
							let data = list && list.opinionField2 ? (list.opinionField2).replace(/<br\/>/g, "\n") : '';
							return data
						}
					},
					{
						type: "textarea",
						label: "部门会签",
						field: "opinionField3",
						opinionField: true,
						span: 12,
						qnnDisabled: true,
						initialValue: () => {
							let data = list && list.opinionField3 ? (list.opinionField3).replace(/<br\/>/g, "\n") : '';
							return data
						}
					},
					{
						type: "textarea",
						label: "项目归档",
						field: "opinionField4",
						opinionField: true,
						span: 12,
						qnnDisabled: true,
						initialValue: () => {
							let data = list && list.opinionField4 ? (list.opinionField4).replace(/<br\/>/g, "\n") : '';
							return data
						}
					}
				]}
			/> : null}
		</div>
		);
	}
}
export default index;