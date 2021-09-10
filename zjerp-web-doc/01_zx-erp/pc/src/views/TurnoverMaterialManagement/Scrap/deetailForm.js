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
		title: ["周转材报废", "billNo"],
		apiNameByAdd: "updateZxSkTurnOverScrap",
		apiNameByUpdate: "updateZxSkTurnOverScrap",
		apiNameByGet: "getZxSkTurnOverScrapDetail",
		flowId: "zxSkTurnOverScrapWorkId",
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
			apiName: "getZxSkTurnOverScrapDetail",
			apiType: "POST",
			flowId: "zxSkTurnOverScrapWorkId",
			title: "周转材报废-" + flowData.billNo
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
						label: "",
						hide: true,
						initialValue: list?.orgID,
						field: 'orgID',
						type: 'string'
					},
					{
						label: '报废单位',
						field: 'orgName',
						type: 'string',
						qnnDisabled: true,
						initialValue: list?.orgName,
						span: 12,
					},

					{
						label: "收购单位名称",
						required: true,
						qnnDisabled: true,
						initialValue: list?.acceptOrgName,
						field: 'acceptOrgName',
						type: 'string',
						span: 12,
					},
					{
						field: 'zxSkTurnOverScrapItemList',
						type: 'qnnTable',
						incToForm: true,
						initialValue: list?.zxSkTurnOverScrapItemList,
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
						label: "单据编号",
						qnnDisabled: true,
						initialValue: list?.billNo,
						field: 'billNo',
						type: 'string',
						span: 12,
					},
					{
						type: "date",
						label: "日期",
						required: true,
						qnnDisabled: true,
						field: "busDate",
						initialValue: list?.busDate,
						placeholder: "请输入",
						span: 12,
					},
					{
						type: "string",
						label: "经办人",
						qnnDisabled: true,
						initialValue: list?.consignee,
						field: "consignee",
						placeholder: "请输入",
						span: 12,
					},
					{
						type: "textarea",
						label: "备注",
						qnnDisabled: true,
						initialValue: list?.remarks,
						field: "remarks",
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
						label: "公司主管部门",
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
				{flowData && flowData.zxSkTurnOverScrapItemList ? <QnnForm
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
						apiName: 'getZxSkTurnOverScrapList',
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
							"type": "qnnTable",
							"field": "zxSkTurnOverScrapItemList",
							incToForm: true,
							"qnnTableConfig": {
								"antd": {
									"rowKey": "id",
									size: 'small'
								},
								paginationConfig: {
									position: 'bottom'
								},
								isShowRowSelect: false,
								"formConfig": [
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
											title: '物资编码',
											width: 200,
											// tooltip:80,
											dataIndex: 'resCode',
											key: 'resCode'
										},
										form: {
											type: 'selectByPaging',
											field: 'resCode',
											allowClear: false,
											optionConfig: {
												label: 'joinName',
												value: 'resCode'
											}
										}
									},
									{
										isInTable: false,
										form: {
											field: 'resID',
											hide: true
										}
									},
									{
										table: {
											title: '物资名称',
											// width: 150,
											dataIndex: 'resName',
											key: 'resName',
											// tooltip:10,
										},
										form: {
											type: 'string',
											field: 'resName'
										}
									},
									{
										table: {
											title: '规格型号',
											dataIndex: 'spec',
											key: 'spec',
											// width: 80,
											// tooltip:10,
										},
										form: {
											type: 'string',
											field: 'spec'
										}
									},
									{
										table: {
											title: '计量单位',
											dataIndex: 'resUnit',
											key: 'resUnit',
											// width: 80,
										},
										form: {
											type: 'string',
											field: 'resUnit'
										}
									},
									{
										table: {
											title: '入库日期',
											dataIndex: 'inBusDate',
											key: 'inBusDate',
											// width: 80,
											format: 'YYYY-MM-DD'
										},
										form: {
											type: 'date',
											field: 'inBusDate'
										}
									},
									{
										table: {
											title: '库存数量',
											dataIndex: 'stockQty',
											key: 'stockQty',
											// width: 80,
										},
										form: {
											type: 'number',
											field: 'stockQty'
										}
									},
									{
										table: {
											title: '原值',
											dataIndex: 'originalAmt',
											key: 'originalAmt',
											// width: 80,
										},
										form: {
											type: 'number',
											field: 'originalAmt'
										}
									},
									{
										table: {
											title: '净值',
											dataIndex: 'currentAmt',
											key: 'currentAmt',
											// width: 80,
										},
										form: {
											type: 'number',
											field: 'currentAmt'
										}
									},
									{
										table: {
											title: '处理数量',
											// width:100,
											dataIndex: 'discardQty',
											key: 'discardQty'
										},
										form: {
											type: 'number',
											field: 'discardQty'
										}
									},
									{
										table: {
											title: '处理单价',
											// width:100,
											dataIndex: 'discardPrice',
											key: 'discardPrice'
										},
										form: {
											type: 'number',
											field: 'discardPrice'
										}
									},
									{
										table: {
											title: '处理金额',
											// width:120,
											dataIndex: 'discardAmt',
											key: 'discardAmt'
										},
										form: {
											type: 'number',
											field: 'discardAmt'
										}
									}
								],
								"actionBtns": []
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