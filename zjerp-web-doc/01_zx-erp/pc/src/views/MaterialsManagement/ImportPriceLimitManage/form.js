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
		title: ["限价数据采集", "limitNo"],
		apiNameByAdd: "updateZxSkLimitPrice",
		apiNameByUpdate: "updateZxSkLimitPrice",
		apiNameByGet: "getZxSkLimitPriceDetails",
		// apiTitle: "setZjkOaFlowTitle",//???
		flowId: "zxSkLimitPriceWorkId",
		todo: "TodoList",
		hasTodo: "HasTodoList"
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
			lockProjectId: props.loginAndLogoutInfo.loginInfo.userInfo.lockProject && props.loginAndLogoutInfo.loginInfo.userInfo.lockProject.projectId ? props.loginAndLogoutInfo.loginInfo.userInfo.lockProject.projectId : ''
		}
	}
	handleCancelBjdh = () => {
		this.setState({ visibleBjdh: false });
	}
	render() {
		const {
			isInQnnTable, flowData, match: { url }
		} = this.props;
		const { visibleBjdh } = this.state;
		this.props.match.path = url + '/TodoList';
		return (<div style={
			{
				height: isInQnnTable ? "" : "50vh"
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
				// flowButtons={[
				// 	{
				// 	  "buttonName": "暂存",
				// 	  "buttonClass": "com.horizon.wf.action.ActionSave",
				// 	  "buttonId": "submit"
				// 	}
				// ],
				// [
				// 	{
				// 	  "buttonName": "发起",
				// 	  "buttonClass": "createOpenFlow",
				// 	  "buttonId": "submit"
				// 	}
				// ]}
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
						initialValue: flowData && flowData.id ? flowData.id : '',
						hide: true
					},
					{
						type: "string",
						label: "",
						field: "faqi",
						initialValue: '1',
						hide: true
					},
					{
						field: 'zxSkLimitPriceItemList',
						type: 'qnnTable',
						incToForm: true,
						initialValue: flowData?.zxSkLimitPriceItemList,
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
						label: "计划编号",
						qnnDisabled: true,
						require:true,
						initialValue: flowData && flowData.limitNo ? flowData.limitNo : '',
						field: "limitNo",
						placeholder: "请输入",
						span: 12,
					},
					{
						type: "string",
						required: true,
						label: "项目名称",
						qnnDisabled: true,
						field: "projectName",
						initialValue: flowData && flowData.projectName ? flowData.projectName : '',
						span: 12,
					},
					{
						label: "",
						hide: true,
						initialValue: flowData && flowData.projectId ? flowData.projectId : '',
						field: 'projectId',
						type: 'string'
					},
					{
						type: "halfYear",
						label: "数据期次",
						required: true,
						qnnDisabled: true,
						field: "periodDate",
						initialValue: flowData && flowData.periodDate ? flowData.periodDate : '',
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
						type: "textarea",
						label: "项目总经",
						field: "opinionField1",
						opinionField: true,
						span: 12,
						addShow: false
					},
					{
						type: "textarea",
						label: "项目经理",
						field: "opinionField2",
						opinionField: true,
						span: 12,
						addShow: false
					},
					{
						type: "textarea",
						label: "公司主管部门",
						field: "opinionField3",
						opinionField: true,
						span: 12,
						addShow: false
					},
					{
						type: "textarea",
						label: "公司主管领导",
						field: "opinionField4",
						opinionField: true,
						span: 12,
						addShow: false
					},
				]}
				// btns={[
				// 	{
				// 		name: 'diy',
				// 		type: 'dashed',
				// 		label: '暂存',
				// 		onClick: () => {
		
				// 		}
				// 	},
				// ]}
				btnsCURD={({ btns, flowData }) => {
					if (btns) {
						btns.push({
							buttonClass: "com.horizon.wf.action.ActionSave", buttonFun: (obj) => {
								// this.props.myFetch('exportZjFlowBlanketInsurance',{blanketInsuranceId:blanketInsuranceId,proName:proName}).then(({ success, data, message }) => {
								// 	if (success) {
								// 		window.location.href = data;
								// 	} else {
								// 		Msg.error(message)
								// 	}
								// })
							}, buttonId: '', buttonName: "暂存", icon: null
						});
					} else {

					}

					return btns
				}}
			/>
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
				<QnnForm
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
						apiName: 'getZxSkLimitPriceList',
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
							field: "zxSkLimitPriceItemList",
							incToForm: true,
							qnnTableConfig: {
								antd: {
									rowKey: 'stockVOID',
									size: 'small',
									scroll: {
										y: document.documentElement.clientHeight * 0.6
									}
								},
								drawerConfig: {
									width: '900px'
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
											title: '物资大类',
											dataIndex: 'resourceName',
											width: 200,
											key: 'resourceName'
										},
										isInForm: false
									},
									{
										table: {
											title: '物资编码',
											width: 150,
											tooltip: 23,
											dataIndex: 'resourceNo',
											key: 'resourceNo'
										},
										isInForm: false
									},
									{
										table: {
											title: '物资编码',
											dataIndex: 'workNo',
											width: 180,
											key: 'workNo'
										},
										isInForm: false
									},

									{
										table: {
											title: '物资名称',
											width: 150,
											tooltip: 23,
											dataIndex: 'workName',
											key: 'workName'
										},
										isInForm: false
									},

									{
										table: {
											title: '规格型号',
											width: 100,
											dataIndex: 'spec',
											key: 'spec'
										},
										isInForm: false
									},
									{
										table: {
											title: '单位',
											dataIndex: 'unit',
											key: 'unit',
											width: 80,
										},
										isInForm: false
									},
									{
										table: {
											title: '当期采集单价',
											dataIndex: 'price',
											width: 120,
											key: 'price'
										},
										isInForm: false
									}
								],
								actionBtns: []
							}
						}
					]}
				/>

			</Modal>
		</div>
		);
	}
}
export default index;