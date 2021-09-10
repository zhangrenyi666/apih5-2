import React, { Component } from "react";
import { Form } from '../../modules/work-flow';
let config = {
	//流程专属配置   
	parameterName: 'projectId',
	editDocCdnAddress: window.configs.ntkoAddress,
	workFlowConfig: {
		title: ['分包限价新增', 'subTitle'],
		apiNameByAdd: "addZxCtSubContractLpriceReviewApplyFlow",
		apiNameByUpdate: "updateZxCtSubContractLpriceReviewApplyFlow",
		apiNameByGet: "getZxCtSubContractLpriceReviewDetail",
		todo: "TodoList",
		hasTodo: "HasTodoList",
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
	docFieldName: "zxZhengWenFileList",
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
	filesFieldName: "zxErpFileList",
	//请求左侧待办已办列表的ajax配置
	//@curList 当前处于什么列表 todo(待办)  hasTodo(已办)
	getTodoDataFetchConfig: window.qnnGetTodoDataFetchConfig,
};
class index extends Component {
	constructor(props) {
		super(props);
		this.state = {
			lockProjectId: props.loginAndLogoutInfo.loginInfo.userInfo.lockProject && props.loginAndLogoutInfo.loginInfo.userInfo.lockProject.projectId ? props.loginAndLogoutInfo.loginInfo.userInfo.lockProject.projectId : '',
			list: null,
			flowButtons: null
		}
	}
	componentWillMount() {
		const {
			flowData
		} = this.props;
		let paramsArry = {
			workId: flowData.workId,
			apiName: "getZxCtSubContractLpriceReviewDetail",
			apiType: "POST",
			flowId: "ZxCtSubContractLprice",
			title: "分包限价新增-" + flowData.subTitle
		};
		this.props.myFetch('openFlowByReport', paramsArry).then(({ success, message, data }) => {
			if (success) {
				let list = JSON.parse(data.apiData);
				console.log(list);
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
		const { list,flowButtons } = this.state;
		this.props.match.path = url + '/HasTodoList';

		return (
			<div style={{ height: isInQnnTable ? "" : "100vh" }}>
				{list ? <Form
					{...this.props}
					{...config}
					upload={this.props.myUpload}
					wrappedComponentRef={(me) => {
						this.tableForm = me;
					}}
					formConfig={[
						{
							field: 'workId',
							type: 'string',
							initialValue: list?.workId,
							hide: true,
						},
						{
							field: 'zxCtSubContractLpriceReviewId',
							type: 'string',
							initialValue: list?.zxCtSubContractLpriceReviewId,
							hide: true,
						},

						{
							field: 'projectName',
							type: 'string',
							label: '项目名称',
							required: true,
							qnnDisabled: true,
							initialValue: list?.projectName,
							span: 12
						},
						{
							field: 'subTitle',
							type: 'string',
							initialValue: list?.subTitle,
							required: true,
							qnnDisabled: true,
							label: '标题',
							span: 12
						},
						{
							field: 'drafterName',
							type: 'string',
							qnnDisabled: true,
							label: '拟稿人',
							required: true,
							initialValue: () => {
								return this.props.loginAndLogoutInfo.loginInfo.userInfo.realName
							},
							span: 12
						},
						{
							field: 'businessDate',
							type: 'date',
							qnnDisabled: true,
							initialValue: list?.businessDate,
							required: true,
							label: '业务日期',
							span: 12,
						},
						{
							field: 'remarks',
							type: 'textarea',
							qnnDisabled: true,
							initialValue: list?.remarks,
							required: true,
							label: '备注',
							span: 12
						},
						{
							field: 'handle',
							type: 'textarea',
							qnnDisabled: true,
							initialValue: list?.handle,
							label: '协办',
							span: 12
						},
						{
							label: "协办甲",
							field: 'opinionField1',
							type: 'textarea',
							opinionField: true,
							qnnDisabled: true,
							addShow: false,
							placeholder: '请输入',
							span: 12,
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
							initialValue: () => {
								let data = list && list.opinionField1 ? (list.opinionField1).replace(/<br\/>/g, "\n") : '';
								return data
							}
						},
						{
							type: "textarea",
							label: "协办乙",
							qnnDisabled: true,
							field: "opinionField2",
							opinionField: true,
							span: 12,
							addShow: false,
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
							initialValue: () => {
								let data = list && list.opinionField2 ? (list.opinionField2).replace(/<br\/>/g, "\n") : '';
								return data
							}
						},
					]}
					todoListModuleShow={true}
					doctModuleShow={true}
					filesModuleShow={true}
					flowButtons={flowButtons}
					initialValueByDocs={list?.zxZhengWenFileList}
					initialValueByFiles={list?.zxErpFileList}
					{...this.props.workFlowConfig}
					{...config.workFlowConfig}
					flowId={'ZxCtSubContractLprice'}
					fieldsCURD={(obj, flowData, props) => {
						if (props.onRefreshDrawer) {
							props.onRefreshDrawer();
						}
						obj = obj.map((item) => {
							var url = props.match.url;
							var myPublic = props.myPublic.appInfo.mainModule;
							// 已办

							if (url === `${myPublic}HasTodoList`) {
								item.disabled = true;
								return item
							} else {
								if (item.field === 'businessDate' || item.field === 'projectId' || item.field === 'subTitle' || item.field === 'subTitle' || item.field === 'remarks' || item.field === 'handle') {
									item.disabled = false;
								}
								if (item.field === 'projectId') {
									item.disabled = true;
									console.log('1=====', item.disabled);
								}

								return item
							}
						})
						return obj
					}}
				/> : null}
			</div>
		);
	}
}
export default index;
