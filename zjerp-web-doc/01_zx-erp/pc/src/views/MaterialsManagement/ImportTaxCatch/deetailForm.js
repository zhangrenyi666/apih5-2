import React, {
	Component
} from "react";
import {
	Form
} from "../../modules/work-flow";
import QnnForm from "../../modules/qnn-form";
import { Modal, Button } from 'antd';
let config = {
	editDocCdnAddress: window.configs.ntkoAddress,
	workFlowConfig: {
		title: ["物资月度采购计划", "projectNumber"],
		apiNameByAdd: "updateZxSkMonthPur",
		apiNameByUpdate: "updateZxSkMonthPur",
		apiNameByGet: "getZxSkMonthPurDetails",
		flowId: "zxskmonthPurWorkId",
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
			visibleBjdh: false,
			list: null,
			lockProjectId: props.loginAndLogoutInfo.loginInfo.userInfo.lockProject && props.loginAndLogoutInfo.loginInfo.userInfo.lockProject.projectId ? props.loginAndLogoutInfo.loginInfo.userInfo.lockProject.projectId : '',
			flowButtons: null
		}
	}
	handleCancelBjdh = () => {
		this.setState({ visibleBjdh: false });
	}
	componentWillMount() {
		const {
			flowData
		} = this.props;
		let paramsArry = {
			workId: flowData.workId,
			apiName: "getZxSkMonthPurDetails",
			apiType: "POST",
			flowId: "zxskmonthPurWorkId",
			title: "物资月度采购计划-" + flowData.projectNumber
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
			isInQnnTable, flowData, match: { url }
		} = this.props;
		const { visibleBjdh, list, flowButtons } = this.state;
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
				wrappedComponentRef={(me) => {
					this.flowForm = me;
				}}
				todoListModuleShow={true}
				doctModuleShow={true}
				filesModuleShow={true}
				flowButtons={flowButtons}
				initialValueByDocs={list?.zxZhengWenFileList}
				initialValueByFiles={list?.zxErpFileList}
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
						label: "",
						field: "companyId",
						initialValue: list?.companyId,
						hide: true
					},
					{
						field: 'zxSkMonthPurItemList',
						type: 'qnnTable',
						incToForm: true,
						initialValue: list?.zxSkMonthPurItemList,
						qnnTableConfig: {
							antd: {
								rowKey: 'id',
								size: 'small'
							},
							fetchConfig: {}
						},
						hide: true,
					},

					{
						type: "string",
						label: "",
						field: "companyName",
						initialValue: list?.companyName,
						hide: true
					},
					{
						type: "string",
						label: "计划编号",
						qnnDisabled: true,
						initialValue: list?.projectNumber,
						field: "projectNumber",
						placeholder: "请输入",
						span: 12,
					},
					{
						type: "string",
						field: "projectID",
						initialValue: list?.projectID,
						span: 12,
						hide: true
					},
					{
						type: "string",
						required: true,
						label: "项目名称",
						qnnDisabled: true,
						field: "projectName",
						initialValue: list?.projectName,
						span: 12,
					},
					{
						type: "date",
						label: "日期",
						required: true,
						qnnDisabled: true,
						field: "createDate",
						initialValue: list?.createDate,
						placeholder: "请输入",
						span: 12,
					},
					{
						type: "string",
						label: "编制人",
						qnnDisabled: true,
						initialValue: list?.aurhorizedPersonnel,
						field: "aurhorizedPersonnel",
						placeholder: "请输入",
						span: 12,
					},
					{
						type: "component",
						field: "component66",
						label: '详细信息',
						Component: obj => {
							return (
								<Button type="primary" onClick={() => {
									this.setState({
										visibleBjdh: true
									})
								}}>查看</Button>
							);
						},
						span: 12,
					},
					{
						label: '备注',
						type: 'textarea',
						qnnDisabled: true,
						initialValue: list?.remark,
						field: 'remark',
						autoSize: {
							minRows: 1,
							maxRows: 3
						},
						span: 12,
					},
					{
						type: "textarea",
						label: "相关部门审核",
						field: "opinionField1",
						opinionField: true,
						span: 12,
						qnnDisabled: true,
						initialValue: () => {
							let data = list && list.opinionField1 ? (list.opinionField1).replace(/<br\/>/g, "\n") : '';
							return data
						}
					},
					{
						type: "textarea",
						label: "项目领导审核",
						field: "opinionField2",
						opinionField: true,
						span: 12,
						qnnDisabled: true,
						initialValue: () => {
							let data = list && list.opinionField2 ? (list.opinionField2).replace(/<br\/>/g, "\n") : '';
							return data
						}
					}
				]}
			/> : null}
			<Modal
				width='1200px'
				style={{ top: '0' }}
				title="详细信息"
				visible={visibleBjdh}
				footer={null}
				onCancel={this.handleCancelBjdh}
				bodyStyle={{ width: '1200px' }}
				centered={true}
				destroyOnClose={this.handleCancelBjdh}
				wrapClassName={'modals'}
			>
				{flowData && flowData.zxSkMonthPurItemList ? <QnnForm
					fetch={this.props.myFetch}
					upload={this.props.myUpload}
					headers={{
						token: this.props.loginAndLogoutInfo.loginInfo.token
					}}
					wrappedComponentRef={(me) => {
						this.inputForm = me;
					}}
					formItemLayout={{
						labelCol: {
							xs: { span: 24 },
							sm: { span: 12 }
						},
						wrapperCol: {
							xs: { span: 24 },
							sm: { span: 24 }
						}
					}}
					fetchConfig={{
						apiName: 'getZxSkMonthPurList',
						otherParams: () => {
							if (this.flowForm.rootBody?.workId) {
								return {
									workId: this.flowForm.rootBody.workId
								}
							} else {
								return {
									id: flowData.id
								}
							}
						}
					}}
					formConfig={[
						{
							type: "qnnTable",
							field: "zxSkMonthPurItemList",
							incToForm: true,
							qnnTableConfig: {
								antd: {
									rowKey: "id",
									size: 'small'
								},
								paginationConfig: {
									position: 'bottom'
								},
								isShowRowSelect: false,
								formConfig: [
									{
										isInTable: false,
										form: {
											label: '主键id',
											field: 'id',
											hide: true
										}
									},
									{
										table: {
											title: '分部分项',
											dataIndex: 'cbsName',
											width: 200,
											key: 'cbsName'
										},
										isInForm: false
									},
									{
										table: {
											title: '物资编码',
											width: 200,
											tooltip: 23,
											dataIndex: 'resCode',
											key: 'resCode'
										},
										isInForm: false
									},
									{
										table: {
											title: '物资名称',
											width: 150,
											tooltip: 23,
											dataIndex: 'resName',
											key: 'resName'
										},
										isInForm: false
									},
									{
										table: {
											title: '计量单位',
											dataIndex: 'unit',
											key: 'unit',
											width: 80,
										},
										form: {
											type: 'string',
											field: 'unit'
										}
									},
									{
										table: {
											title: '规格型号',
											width: 100,
											dataIndex: 'spec',
											key: 'spec'
										},
										form: {
											type: 'string',
											field: 'spec'
										}
									},
									{
										table: {
											title: '质量标准',
											width: 120,
											dataIndex: 'qualityStand',
											key: 'qualityStand'
										},
										isInForm: false
									},
									{
										table: {
											title: '计划采购数量',
											dataIndex: 'purNum',
											width: 130,
											key: 'purNum'
										},
										isInForm: false
									},
									{
										table: {
											title: '估算单价',
											dataIndex: 'price',
											key: 'price',
											width: 100,
										},
										isInForm: false
									},
									{
										table: {
											title: '估算总价',
											dataIndex: 'totalMoney',
											width: 100,
											key: 'totalMoney'
										},
										isInForm: false
									},
									{
										table: {
											title: '备注',
											width: 120,
											tooltip: 23,
											dataIndex: 'remark',
											key: 'remark'
										},
										isInForm: false
									}
								],
								actionBtns: []
							}
						},
					]}
				/> : null}

			</Modal>
		</div>
		);
	}
}
export default index;