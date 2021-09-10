import React from "react";
import { Form } from "apih5/modules/work-flow";
import curdTableAndFormConfig from "./config/curdTableAndFormConfig";
import Apih5 from "qnn-apih5";
import { Modal, Button } from "antd";
import QingDan from "./QingDan";
const config = {
	parameterName: "projectId",
	//流程专属配置
	editDocCdnAddress: window.configs.ntkoAddress,
	workFlowConfig: {
		//后台定的字段
		title: ["工程施工类补充协议", "agreementName", "contractNo"], //标题字段
		apiNameByAdd: "bxUpdateZxGcsgCtContrApplyFirstByFlow",
		apiNameByUpdate: "bxUpdateZxGcsgCtContrApplySecondByFlow",
		apiNameByGet: "bxGetZxGcsgCtContrApplyDetailsByFlow",
		todo: "TodoList",
		hasTodo: "HasTodoList",
		flowId: "bxZxGcsgCtContrApply",
	},

	//切换布局
	formLayout: "leftDoc",
	//是否使用描述式表单
	formType: "descriptions",
	descriptionsConfig: {
		layout: "horizontal",
		column: 12,
		size: "small",
	},
	//左侧公文附件字段名字
	filesFieldName: "attachmentList",
	//请求左侧待办已办列表的ajax配置
	//@curList 当前处于什么列表 todo(待办)  hasTodo(已办)
	getTodoDataFetchConfig: window.qnnGetTodoDataFetchConfig,
};
const formItemLayout = {
	labelCol: {
		sm: { span: 9 },
	},
	wrapperCol: {
		sm: { span: 15 },
	},
};

class index extends Apih5 {
	state = {
		visibleBjdh: false,
	};
	componentDidMount() {
		this.props.isDetail && this.openFlow();
	}

	openFlow() {
		const { flowFormData } = this.props;
		let paramsArry = {
			workId: flowFormData.workId,
			apiName: config.workFlowConfig.apiNameByGet,
			apiType: "POST",
			flowId: config.workFlowConfig.flowId,
			title: `工程施工类补充协议-${flowFormData.agreementName}-${flowFormData.contractNo}`,
		};
		this.props.myFetch("openFlowByReport", paramsArry).then(({ success, message, code, data }) => {
			if (success) {
				const apiData = JSON.parse(data.apiData || "{}");
				this.flowForm.qnnForm.setValues({
					apiBody: apiData,
				}); 
				this.flowForm.setState({ 
					flowButtons:data?.flowButtons || []
				});
				this.flowForm.setFilesData(apiData["attachmentList"]);
				this.flowForm.setDocFilesData(apiData["docAttachmentList"]);
			} else {
				this.apih5.errMsg(message, code);
			}
		});
	}
	// 意见字段
	opinionFields = [
		{ label: "项目各部门会签", field: "opinionField1" },
		{ label: "项目各领导会签", field: "opinionField2" },
		{ label: "公司主管部门审核", field: "opinionField3" },
		{ label: "公司主管部门领导审核", field: "opinionField4" },
		{ label: "公司各部门（含法律部）会签", field: "opinionField5" },
		{ label: "公司总经理审批", field: "opinionField6" },
		{ label: "总部主管部门审核", field: "opinionField7" },
		{ label: "总部各部门会签", field: "opinionField8" },
		{ label: "总部领导会签", field: "opinionField9" },
		// { label: "托管公司结算中心审核", field: "opinionField10" }, 
		{ label: "托管公司主管部门审核", field: "opinionField10" },
		{ label: "托管公司各部门会签", field: "opinionField11" },
		{ label: "托管公司领导审批", field: "opinionField12" },
		{ label: "事业部审核", field: "opinionField13" },
		{ label: "事业部领导审批", field: "opinionField14" },
		{ label: "相关事业部审核", field: "opinionField15" },
		{ label: "相关事业部领导审批", field: "opinionField16" },
		{ label: "法律顾问审核", field: "opinionField17" },
		{ label: "合同校对", field: "opinionField18" },
	];

	render() {
		const { isInQnnTable, flowFormData = {}, disabled, isDetail } = this.props;
		const onlyLook = disabled || this.props.onlyLook;
		const showField = ["agreementName", "ctContrApplyId", "contractNo", "firstName", "secondName", "ctContractId", "contractType", "cloudEcoID"]
			.map((field) => {
				return curdTableAndFormConfig.formConfig.find((formConfigItem) => {
					const realField = formConfigItem.form?.field || formConfigItem.table?.dataIndex;
					return field === realField;
				});
			})
			.filter(Boolean);
			
		const otherProps = {};
		if (isDetail) {
			otherProps.match = {
				...this.props.match,
				path: "/HasTodoList",
			};
		}
		return (
			<div style={{ height: isInQnnTable ? "" : "100vh" }}>
				<Form
					{...this.props}
					{...config}
					{...otherProps}
					formTailLayout={{
						wrapperCol: {
							offset: 3,
						},
					}}
					formConfig={[
						{
							field: "flowId",
							type: "string",
							initialValue: "zxCtOtherSupplyAgreementReviewAp",
							hide: true,
						},
						{
							field: "projectId",
							type: "string",
							initialValue: flowFormData["projectId"],
							hide: true,
						},

						// ...curdTableAndFormConfig.formConfig.filter((item) => item.isInForm !== false).map(item => {
						...showField.map((item) => {
							const { spanForm, ...formConfig } = item.form || {};
							const realField = formConfig.field || item.table?.dataIndex;
							if (item.form.field === "agreementName") {
								formConfig.hide = true;
							}
							return {
								formItemLayout,
								// span: formConfig?.type === "textarea" ? 12 : 6,
								...formConfig,
								span: 12,
								field: realField,
								label: formConfig.label || item.table?.title,
								qnnDisabled: true,
								required: false,
								initialValue: flowFormData[realField],
							};
						}),
						{
							type: "string",
							label: "正文模板类型",
							field: "templateType",
							placeholder: "请输入",
							span: 12,
							initialValue: "局模板",
							qnnDisabled: true,
						},
						{
							type: "component",
							field: "component66",
							label: "信息查看",
							Component: (obj) => {
								return (
									<Button
										type="primary"
										onClick={() => {
											this.setState({
												visibleBjdh: true,
											});
										}}
									>
										清单查看
									</Button>
								);
							},
							span: 12,
						},
						{
							label: "主要内容",
							field: "opinionField19",
							type: "textarea", 
							opinionField: true, // 意见字段标识
							// 标识作用：创建流程时将字段名改为 opinionField， 回显时使用 opinionField19
							opinionFieldByCreate: true, 
							// 点击 详细 操作按钮，打开的是一个新增类型的表单这时候就需要显示意见框
							qnnDisabled: onlyLook ? true : false, 
							placeholder: "请输入",
							span: 12, 
							formatter: (val) => {
								if (!val) return undefined;
								return val.replace(/<br\/>/gi, "\r\n");
							},
						},
						...this.opinionFields.map((item) => {
							return {
								label: item.label,
								field: item.field,
								type: "textarea",
								opinionField: true,
								// 点击 详细 操作按钮，打开的是一个新增类型的表单这时候就需要显示意见框
								addShow: onlyLook ? true : false,
								qnnDisabled: onlyLook ? true : false,
								placeholder: "请输入",
								span: 12,
								formatter: (val) => {
									if (!val) return undefined;
									return val.replace(/<br\/>/gi, "\r\n");
								},
							};
						}),
					]}
					wrappedComponentRef={(me) => {
						this.flowForm = me;
					}}
					disabled={onlyLook}  
					{...config.workFlowConfig}
					isHaveDoc={true}
					docFieldLable={"公文正文"} //label
					docFieldName={"docAttachmentList"} //field
					docFieldIsRequired={true} //是否必填默认true
					docFormFormItemLayout={{
						labelCol: {
							xs: { span: 24 },
							sm: { span: 3 },
						},
						wrapperCol: {
							xs: { span: 24 },
							sm: { span: 21 },
						},
					}}
				/>

				<Modal
					width="80%"
					style={{ top: "0" }}
					title="详细信息"
					visible={this.state.visibleBjdh}
					footer={null}
					onCancel={() =>
						this.setState({
							visibleBjdh: false,
						})
					}
					centered={true}
					destroyOnClose={() =>
						this.setState({
							visibleBjdh: false,
						})
					}
				>
					<QingDan
						upload={this.props.myUpload}
						fetch={this.props.myFetch}
						fetchConfig={{
							apiName: "getZxGcsgCcCoAlterWorkListByFlow",
							otherParams: () => {
								return {
									ctContrApplyId: this.flowForm.qnnForm.form.getFieldValue("apiBody.ctContrApplyId"),
								};
							},
						}}
					/>
				</Modal>
			</div>
		);
	}
}
export default index;
