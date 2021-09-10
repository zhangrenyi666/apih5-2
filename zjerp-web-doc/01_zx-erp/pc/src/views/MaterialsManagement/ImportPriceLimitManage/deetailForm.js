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
		flowId: "zxSkLimitPriceWorkId",
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
			apiName: "getZxSkLimitPriceDetails",
			apiType: "POST",
			flowId: "zxSkLimitPriceWorkId",
			title: "限价数据采集-" + flowData.limitNo
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
						field: "faqi",
						initialValue: '1',
						hide: true
					},
					{
						field: 'zxSkLimitPriceItemList',
						type: 'qnnTable',
						incToForm: true,
						initialValue: list?.zxSkLimitPriceItemList,
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
						required: true,
						initialValue: list?.limitNo,
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
						initialValue: list?.projectName,
						span: 12,
					},
					{
						label: "",
						hide: true,
						initialValue: list?.projectId,
						field: 'projectId',
						type: 'string'
					},
					{
						type: "halfYear",
						label: "数据期次",
						required: true,
						qnnDisabled: true,
						field: "periodDate",
						initialValue: list?.periodDate,
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
						qnnDisabled: true,
						initialValue: () => {
							let data = list && list.opinionField1 ? (list.opinionField1).replace(/<br\/>/g, "\n") : '';
							return data
						}
					},
					{
						type: "textarea",
						label: "项目经理",
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
						label: "公司主管领导",
						field: "opinionField4",
						opinionField: true,
						span: 12,
						qnnDisabled: true,
						initialValue: () => {
							let data = list && list.opinionField4 ? (list.opinionField4).replace(/<br\/>/g, "\n") : '';
							return data
						}
					},
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
				{flowData && flowData.zxSkLimitPriceItemList ? <QnnForm
					fetch={this.props.myFetch}
					upload={this.props.myUpload}
					headers={{
						token: this.props.loginAndLogoutInfo.loginInfo.token
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
				/> : null}

			</Modal>
		</div>
		);
	}
}
export default index;