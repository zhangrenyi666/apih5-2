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
		title: ["废旧物资管理", "billNo"],
		apiNameByAdd: "updateZxSkWornOut",
		apiNameByUpdate: "updateZxSkWornOut",
		apiNameByGet: "getZxSkWornOutDetail",
		flowId: "zxSkWornOutWorkId",
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
			apiName: "getZxSkWornOutDetail",
			apiType: "POST",
			flowId: "zxSkWornOutWorkId",
			title: "废旧物资管理-" + flowData.billNo
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
						field: "zxSkWornOutId",
						initialValue: list?.zxSkWornOutId,
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
						type: "string",
						label: "公司名称",
						qnnDisabled: true,
						initialValue: list?.comName,
						field: "comName",
						placeholder: "请输入",
						span: 12,
					},
					{
						field: 'zxSkWornOutItemList',
						type: 'qnnTable',
						incToForm: true,
						initialValue: list?.zxSkWornOutItemList,
						qnnTableConfig: {
							antd: {
								rowKey: 'zxSkWornOutItemId',
								size: 'small'
							},
							fetchConfig: {}
						},
						hide: true,
					},
					{
						type: "string",
						required: true,
						label: "项目名称",
						qnnDisabled: true,
						field: "orgName",
						initialValue: list?.orgName,
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
						label: "",
						hide: true,
						initialValue: list?.applyOrgID,
						field: 'applyOrgID',
						type: 'string'
					},
					{
						label: "申请单位",
						required: true,
						qnnDisabled: true,
						initialValue: list?.applyOrgName,
						field: 'applyOrgName',
						type: 'string',
						span: 12,
					},
					{
						type: "number",
						label: "拟处理金额(万元)",
						qnnDisabled: true,
						field: "purchaseAmt",
						initialValue: list?.purchaseAmt,
						placeholder: "请输入",
						span: 12,
					},
					{
						type: "date",
						label: "填报日期",
						qnnDisabled: true,
						field: "reportDate",
						initialValue: list?.reportDate,
						placeholder: "请输入",
						span: 12,
					},
					{
						type: "textarea",
						label: "废旧物资处理说明",
						qnnDisabled: true,
						field: "approval",
						initialValue: list?.approval,
						placeholder: "请输入",

						autoSize: {
							minRows: 1,
							maxRows: 3
						},
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
						type: "textarea",
						label: "项目经理",
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
						label: "项目废旧物资",
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
						label: "公司主管部门",
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
						label: "公司废旧物资处理成员",
						field: "opinionField4",
						opinionField: true,
						span: 12,
						qnnDisabled: true,
						initialValue: () => {
							let data = list && list.opinionField4 ? (list.opinionField4).replace(/<br\/>/g, "\n") : '';
							return data
						}
					},
					{
						type: "textarea",
						label: "公司废旧物资主管领导",
						field: "opinionField5",
						opinionField: true,
						span: 12,
						qnnDisabled: true,
						initialValue: () => {
							let data = list && list.opinionField5 ? (list.opinionField5).replace(/<br\/>/g, "\n") : '';
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
				{flowData && flowData.zxSkWornOutItemList ? <QnnForm
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
						apiName: 'getZxSkWornOutList',
						otherParams: () => {
							if (this.flowForm.rootBody?.workId) {
								return {
									workId: this.flowForm.rootBody.workId
								}
							} else {
								return {
									zxSkWornOutId: flowData.zxSkWornOutId
								}
							}
						}
					}}
					formConfig={[
						{
							type: "qnnTable",
							field: "zxSkWornOutItemList",
							incToForm: true,
							qnnTableConfig: {
								antd: {
									rowKey: "id",
									size: 'small',
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
											field: 'zxSkWornOutItemId',
											hide: true
										}
									},
									{
										table: {
											title: '物资名称',
											width: 150,
											tooltip: 23,
											dataIndex: 'resName',
											key: 'resName'
										},
										form: {
											type: 'string',
											field: 'resName'
										}
									},

									{
										table: {
											title: '单位',
											dataIndex: 'unit',
											key: 'unit',
											width: 80
										},
										form: {
											type: 'string',
											field: 'unit'
										}
									},

									{
										table: {
											title: '数量',
											dataIndex: 'qty',
											key: 'qty',
											width: 80
										},
										form: {
											type: 'number',
											field: 'qty'
										}
									},
									{
										table: {
											title: '总金额(万元)',
											dataIndex: 'amt',
											key: 'amt'
										},
										form: {
											type: 'number',
											field: 'amt'
										}
									},
									{
										table: {
											title: '备注',
											dataIndex: 'remarks',
											key: 'remarks'
										},
										form: {
											type: 'textarea',
											field: 'remarks',
											autoSize: {
												minRows: 1,
												maxRows: 3
											},
										}
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