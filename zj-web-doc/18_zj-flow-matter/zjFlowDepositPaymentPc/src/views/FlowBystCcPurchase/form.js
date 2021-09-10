import { Form } from "../modules/work-flow";
import React, { Component } from "react";
import moment from 'moment';
const config = {
	//流程专属配置  --qnn-table中配了这也得配置
	workFlowConfig: {
		//后台定的字段
		title: ["name", "sendTime", "出差申请"], //标题字段
		apiNameByAdd: "addZjFlowStElection",
		apiNameByUpdate: "updateZjFlowStElection",
		apiNameByGet: "getZjFlowStElectionDetail",
		flowId: "stCcPurchase",
		formLink: {
			stCcPurchase: "FlowBystCcPurchase"
		},
		//待办已办切换路由
		todo: "todoBystCcPurchase",
		hasTodo: "hasTodostCcPurchase",
		fieldsCURD: function (obj, flowData, props) {
			let apiData = JSON.parse(flowData.apiData);
			if (flowData.flowNode.nodeId === 'Node7') {
				obj = obj.map((item) => {
					if (item.field === 'completeStatus' || item.field === 'backDate' || item.field === 'actualDays' || item.field === 'subsidyAmount' || item.field === 'note') {
						item.hide = false;
						if (item.field !== 'actualDays' && item.field !== 'subsidyAmount') {
							item.disabled = false;
						}
					}
					return item
				})
			}
			if (apiData.backDate) {
				obj = obj.map((item) => {
					if (item.field === 'completeStatus' || item.field === 'backDate' || item.field === 'actualDays' || item.field === 'subsidyAmount' || item.field === 'note') {
						item.hide = false;
					}
					return item
				})
			}
			return obj
		}
	},
};
class index extends Component {
	state = {
		dateMin: null,
		dateMax: null,
		isNeedClassifyConfig: false
	}
	render() {
		const { dateMin, dateMax } = this.state;
		return (
			<div
				style={{
					height: "100vh"
				}}
			>
				<Form
					{...this.props}
					{...config}
					{...this.props.workFlowConfig}
					{...config.workFlowConfig}
					isNeedClassifyConfig={this.state.isNeedClassifyConfig}
					changeIsNeedClassifyConfig={() => {
						this.setState({
							isNeedClassifyConfig: false
						})
					}}
					// btnsCURD={({ btns,flowData: { nodeVars,flowId,workId } }) => {
					//     if (nodeVars && nodeVars.canExport === '1') {
					//         let { myPublic: { domain,appInfo: { ureport } } } = this.props;
					//         var printUrl = `${ureport}excel?_u=file:strategicCooperation.xml&url=${domain}&workId=${workId}`
					//         btns.push({ buttonClass: "exprot",buttonFun: null,buttonId: "exprot",buttonName: "导出",icon: null,printUrl: printUrl });
					//     }
					//     return btns;
					// }}
					formConfig={[
						{
							type: "string",
							label: "workId",
							field: "workId",
							hide: true
						},
						{
							type: "string",
							label: "申请时间format",
							field: "sendTime",
                            initialValue: moment().format("YYYY-MM-DD HH:mm:ss"),
							hide: true
						},
						{
							type: "string",
							label: "姓名",
							field: "name",
							required: true,
							// initialValue: (obj) => {
							//   let userInfo = obj.loginAndLogoutInfo.loginInfo.userInfo;
							//   return userInfo.realName;
							// },
							formItemLayout: {
								labelCol: {
									xs: { span: 4 },
									sm: { span: 4 }
								},
								wrapperCol: {
									xs: { span: 20 },
									sm: { span: 20 }
								}
							}
						},
						{
							label: '部门',
							field: 'dept',
							type: 'string',
							// type: 'treeSelect',
							// initialValue: (obj) => {
							//   let userInfo = obj.loginAndLogoutInfo.loginInfo.userInfo;
							//   let departmentList = userInfo.departmentList.map(item => {
							//     return {
							//       label: item.departmentName,
							//       value: item.departmentId,
							//       type: "1",
							//     }
							//   });
							//   if (departmentList && departmentList.length) {
							//     return departmentList
							//   }
							//   return [];
							// },
							// treeSelectOption: {
							//   help: true,
							//   selectType: "1",
							//   maxNumber: 1,
							//   fetchConfig: {//配置后将会去请求下拉选项数据
							//     apiName: "getSysDepartmentAllTree2"
							//   },
							//   search: true,
							//   searchPlaceholder: '姓名、账号、电话',
							//   // searchApi:'getSysDepartmentUserAllTree',  //搜索时调用的api  [string]
							//   searchParamsKey: 'search',//搜索文字的K 默认是'searchText'   [string]
							//   searchOtherParams: { pageSize: 999 }//搜索时的其他参数  [object]
							// },
							required: true,
							formItemLayout: {
								labelCol: {
									xs: { span: 4 },
									sm: { span: 4 }
								},
								wrapperCol: {
									xs: { span: 20 },
									sm: { span: 20 }
								}
							}
						},
						{
							type: "string",
							label: "职别",
							field: "duty",
							required: true,
							formItemLayout: {
								labelCol: {
									xs: { span: 4 },
									sm: { span: 4 }
								},
								wrapperCol: {
									xs: { span: 20 },
									sm: { span: 20 }
								}
							}
						},
						{
							type: "date",
							label: "计划开始时间",
							field: "planStart",
							required: true,
							onChange: (val, obj) => {
								this.setState({
									dateMin: moment(val).valueOf(),
									dateMax: moment(val).subtract(-3, "days").valueOf(),
									isNeedClassifyConfig: true
								}, () => {
									obj.form.setFieldsValue({
										apiBody: {
											planEnd: undefined,
											planDays: null,
											backDate: undefined,
											actualDays: null
										}
									})
								})
							},
							formItemLayout: {
								labelCol: {
									xs: { span: 4 },
									sm: { span: 4 }
								},
								wrapperCol: {
									xs: { span: 20 },
									sm: { span: 20 }
								}
							}
						},
						{
							type: "date",
							label: "计划结束时间",
							field: "planEnd",
							required: true,
							condition: [
								{
									regex: {
										'apiBody.planStart': ['=', /(''|null|undefined)/ig]
									},
									action: 'disabled'
								}
							],
							onChange: (val, obj) => {
								this.setState({
									isNeedClassifyConfig: true
								}, () => {
									let rowData = obj.form.getFieldsValue();
									if (rowData?.apiBody?.planStart && rowData?.apiBody?.planEnd) {
										let planDays = moment(rowData.apiBody.planEnd).diff(moment(rowData.apiBody.planStart), 'days');
										obj.form.setFieldsValue({ apiBody: { planDays: String(planDays) } });
									}
								})
							},
							disabledDate: (current) => {
								return current && (current > moment(dateMax).endOf('day') || current <= moment(dateMin).startOf('day'));
							},
							formItemLayout: {
								labelCol: {
									xs: { span: 4 },
									sm: { span: 4 }
								},
								wrapperCol: {
									xs: { span: 20 },
									sm: { span: 20 }
								}
							}
						},
						{
							type: "string",
							label: "计划出差天数",
							field: "planDays",
							qnnDisabled: true,
							formItemLayout: {
								labelCol: {
									xs: { span: 4 },
									sm: { span: 4 }
								},
								wrapperCol: {
									xs: { span: 20 },
									sm: { span: 20 }
								}
							}
						},
						{
							type: "string",
							label: "出差地点",
							field: "businessPlace",
							required: true,
							formItemLayout: {
								labelCol: {
									xs: { span: 4 },
									sm: { span: 4 }
								},
								wrapperCol: {
									xs: { span: 20 },
									sm: { span: 20 }
								}
							}
						},
						{
							type: "string",
							label: "交通工具",
							field: "vehicle",
							required: true,
							formItemLayout: {
								labelCol: {
									xs: { span: 4 },
									sm: { span: 4 }
								},
								wrapperCol: {
									xs: { span: 20 },
									sm: { span: 20 }
								}
							}
						},
						{
							type: "textarea",
							label: "出差事由",
							field: "reason",
							required: true,
							formItemLayout: {
								labelCol: {
									xs: { span: 4 },
									sm: { span: 4 }
								},
								wrapperCol: {
									xs: { span: 20 },
									sm: { span: 20 }
								}
							}
						},
						{
							type: "textarea",
							label: "乘坐飞机原因",
							field: "byAirplane",
							required: true,
							formItemLayout: {
								labelCol: {
									xs: { span: 4 },
									sm: { span: 4 }
								},
								wrapperCol: {
									xs: { span: 20 },
									sm: { span: 20 }
								}
							}
						},
						{
							type: "textarea",
							label: "主要任务完成情况",
							field: "completeStatus",
							required: true,
							hide: true,
							formItemLayout: {
								labelCol: {
									xs: { span: 4 },
									sm: { span: 4 }
								},
								wrapperCol: {
									xs: { span: 20 },
									sm: { span: 20 }
								}
							}
						},
						{
							type: "date",
							label: "销假日期",
							field: "backDate",
							required: true,
							hide: true,
							condition: [
								{
									regex: {
										'apiBody.planStart': ['=', /(''|null|undefined)/ig]
									},
									action: 'disabled'
								}
							],
							disabledDate: (current) => {
								return current && current <= moment(dateMin).startOf('day');
							},
							onChange: (val, obj) => {
								this.setState({
									isNeedClassifyConfig: true
								}, () => {
									let rowData = obj.form.getFieldsValue();
									if (rowData?.apiBody?.planStart && rowData?.apiBody?.backDate) {
										let actualDays = moment(rowData.apiBody.backDate).diff(moment(rowData.apiBody.planStart), 'days');
										let subsidyAmount = '';
										if (actualDays <= 15) {
											subsidyAmount = actualDays * 80;
										} else if (actualDays > 15 && actualDays <= 30) {
											subsidyAmount = actualDays * 50;
										} else if (actualDays > 30) {
											subsidyAmount = actualDays * 30;
										}
										obj.form.setFieldsValue({ apiBody: { actualDays: String(actualDays), subsidyAmount: String(subsidyAmount) } });
									}
								})
							},
							formItemLayout: {
								labelCol: {
									xs: { span: 4 },
									sm: { span: 4 }
								},
								wrapperCol: {
									xs: { span: 20 },
									sm: { span: 20 }
								}
							}
						},
						{
							type: "string",
							label: "实际出差天数",
							field: "actualDays",
							qnnDisabled: true,
							hide: true,
							formItemLayout: {
								labelCol: {
									xs: { span: 4 },
									sm: { span: 4 }
								},
								wrapperCol: {
									xs: { span: 20 },
									sm: { span: 20 }
								}
							}
						},
						{
							type: "number",
							label: "补助金额",
							field: "subsidyAmount",
							hide: true,
							qnnDisabled: true,
							formItemLayout: {
								labelCol: {
									xs: { span: 4 },
									sm: { span: 4 }
								},
								wrapperCol: {
									xs: { span: 20 },
									sm: { span: 20 }
								}
							}
						},
						{
							type: "textarea",
							label: "情况说明",
							field: "note",
							required: true,
							// hide: (obj) => {
							//   let rowData = obj.form.getFieldsValue();
							//   console.log(rowData.operateObj)
							//   if (rowData?.apiBody?.planDays && rowData?.apiBody?.actualDays && rowData?.apiBody?.planDays !== rowData?.apiBody?.actualDays) {
							//     return false;
							//   } else {
							//     return true;
							//   }
							// },
							hide: true,
							formItemLayout: {
								labelCol: {
									xs: { span: 4 },
									sm: { span: 4 }
								},
								wrapperCol: {
									xs: { span: 20 },
									sm: { span: 20 }
								}
							}
						},
						{
							label: "附件",
							field: "fileList",
							type: "files",
							initialValue: [],
							fetchConfig: {
								apiName: window.configs.domain + "upload"
							},
							formItemLayout: {
								labelCol: {
									xs: { span: 4 },
									sm: { span: 4 }
								},
								wrapperCol: {
									xs: { span: 20 },
									sm: { span: 20 }
								}
							}
						},
						{
							type: "textarea",
							label: "部门领导意见",
							field: "opinionField1",
							addShow: false,
							formItemLayout: {
								labelCol: {
									xs: { span: 4 },
									sm: { span: 4 }
								},
								wrapperCol: {
									xs: { span: 20 },
									sm: { span: 20 }
								}
							}
						},
						{
							type: "textarea",
							label: "分管领导意见",
							field: "opinionField2",
							addShow: false,
							formItemLayout: {
								labelCol: {
									xs: { span: 4 },
									sm: { span: 4 }
								},
								wrapperCol: {
									xs: { span: 20 },
									sm: { span: 20 }
								}
							}
						},
						{
							type: "textarea",
							label: "公司主要领导意见",
							field: "opinionField3",
							addShow: false,
							formItemLayout: {
								labelCol: {
									xs: { span: 4 },
									sm: { span: 4 }
								},
								wrapperCol: {
									xs: { span: 20 },
									sm: { span: 20 }
								}
							}
						},
						{
							type: "textarea",
							label: "补助标准",
							field: " ",
							qnnDisabled: true,
							initialValue: "15天内(80元/天);\n 16~30天(50元/天);\n 31天以上(30元/天);",
							formItemLayout: {
								labelCol: {
									xs: { span: 4 },
									sm: { span: 4 }
								},
								wrapperCol: {
									xs: { span: 20 },
									sm: { span: 20 }
								}
							}
						},
					]}
				/>
			</div>
		);
	}
}
export default index;
