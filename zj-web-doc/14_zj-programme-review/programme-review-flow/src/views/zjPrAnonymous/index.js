import { basic } from '../modules/layouts';
import React, { Component } from "react";
import QnnTable from "../modules/qnn-table";
import { Modal, Spin } from 'antd';

class index extends Component {
	constructor() {
		super();
		this.state = {
			visible: false,
			userKey: '',
			flowId:'',
			apiName:'',
			loading: false
		}
	}
	handleCancel = () => {
		this.setState({ visible: false });
	}
	render() {
		const { visible, userKey, flowId, apiName,loading } = this.state;
		return (
			<div>
				<QnnTable
					{...this.props}
					fetch={this.props.myFetch}
					upload={this.props.myUpload}
					headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
					{...window.zjPrAnonymousPage}
					tabs={[
						{
							field: "form1",
							name: "qnnForm",
							title: "基本情况",
							content: {
								fetchConfig: function (obj) {
									var rowData = obj.clickCb.rowData;
									if (rowData) {
										return {
											apiName: 'getZjPrExpertAnonymousDetail',
											otherParams: {
												anonymousId: rowData.anonymousId
											}
										}
									} else if (obj.form.getFieldsValue().anonymousId != '') {
										return {
											apiName: 'getZjPrExpertAnonymousDetail',
											otherParams: {
												anonymousId: obj.form.getFieldsValue().anonymousId
											}
										}
									} else {
										return {};
									}
								},
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
								formConfig: [
									{
										type: 'string',
										label: '主键ID',
										field: 'anonymousId', //唯一的字段名 ***必传
										hide: true
									},
									{
										label: '专家姓名',
										field: 'anonymousList',
										type: 'treeSelect',
										required: true,
										editDisabled: true,
										disabled: true,
										editShow: false,
										detailShow: false,
										addShow: true,
										initialValue: [],
										formItemLayout: {
											labelCol: {
												sm: { span: 3 }
											},
											wrapperCol: {
												sm: { span: 20 }
											}
										},
										treeSelectOption: {
											help: true,
											fetchConfig: {
												// apiName: 'getSysDepartmentUserAllTree',
												apiName: 'getSysUserTreeByZjAddOther',
											},
											maxNumber: 1,
											search: true,
											searchPlaceholder: '姓名、账号、电话',
											// searchApi:'getSysDepartmentUserAllTree',  //搜索时调用的api  [string]
											searchParamsKey: 'search',//搜索文字的K 默认是'searchText'   [string]
											searchOtherParams: { pageSize: 999 }//搜索时的其他参数  [object]
										}
									},
									{
										type: 'string',
										label: '专家姓名',
										field: 'userName', //唯一的字段名 ***必传
										editDisabled: true,
										required: true,
										addShow: false,
										detailShow: true,
										editShow: true,
										placeholder: '请输入',//占位符
										span: 24,
										formItemLayout: {
											labelCol: {
												xs: { span: 24 },
												sm: { span: 3 }
											},
											wrapperCol: {
												xs: { span: 24 },
												sm: { span: 21 }
											}
										},
									},
									{
										type: 'select',
										label: '所属类型',
										field: 'typeStr', //唯一的字段名 ***必传
										placeholder: '请选择',
										pullJoin: false,
										required: true,
										multiple: true, //是否开启多选功能 开启后自动开启搜索功能
										showSearch: false, //是否开启搜索功能 (移动端不建议开启)
										optionData: [//默认选项数据//可为function (props)=>array
											{
												name: '房建',
												id: '0',
											},
											{
												name: '盾构',
												id: '1',
											},
											{
												name: '路基地质岩土',
												id: '2',
											},
											{
												name: '路面',
												id: '3',
											},
											{
												name: '隧道',
												id: '4',
											},
											{
												name: '钢结构',
												id: '5',
											},
											{
												name: '其他',
												id: '6',
											},
											{
												name: '桥梁',
												id: '7',
											},
											{
												name: '试验',
												id: '8',
											},
											{
												name: '测量',
												id: '9',
											},
											{
												name: '机电一体化',
												id: '10',
											},
											{
												name: '设计咨询',
												id: '11',
											}
										],
										optionConfig: {//下拉选项配置
											label: 'name', //默认 label
											value: 'id',// [array | string]  最终的值使用逗号连接 默认值使用valueName 默认['value']
										},
										span: 24,
										formItemLayout: {
											labelCol: {
												xs: { span: 24 },
												sm: { span: 3 }
											},
											wrapperCol: {
												xs: { span: 24 },
												sm: { span: 21 }
											}
										},
									},
									{
										type: 'string',
										label: '专家匿名',
										field: 'anonymousName', //唯一的字段名 ***必传
										required: true,
										placeholder: '请输入',//占位符
										span: 24,
										formItemLayout: {
											labelCol: {
												xs: { span: 24 },
												sm: { span: 3 }
											},
											wrapperCol: {
												xs: { span: 24 },
												sm: { span: 21 }
											}
										},
									}
								],
							}
						}
					]}
					formConfig={[
						{
							table: {
								title: '专家名称', //表头标题
								spanSearch: 12,	
								dataIndex: 'userName', //表格里面的字段
								key: 'userName',//表格的唯一key  
								onClick: 'detail',
								align: 'center'
							},
							isInForm: false,
							isInSearch: true,
							form: {
								type: 'string',
								placeholder: "请输入"
							}
						},
						{
							isInTable: false, 
							isInSearch: true,
							form: {
								label: "方案审批时间",			
								field: 'flowTodoTimeArr', //唯一的字段名 ***必传	
								spanSearch: 12,						   
								type: 'rangeDate',
								placeholder: '请选择',
								showTime: true,
								formItemLayoutSearch: {
									//默认数据
									labelCol: {
										sm: { span: 4 }
									},
									wrapperCol: {
										sm: { span: 20 }
									}
								},											
							}
						},							
						{
							table: {
								title: '匿名名称', //表头标题
								dataIndex: 'anonymousName', //表格里面的字段
								key: 'anonymousName',//表格的唯一key 
								align: 'center'
							},
							isInForm: false,
							isInSearch: true,
							form: {
								type: 'string',
								placeholder: "请输入"
							}							
						},					
						{
							table: {
								title: '待审批数量', //表头标题
								dataIndex: 'toDoReview', //表格里面的字段
								key: 'toDoReview',//表格的唯一key 
								align: 'center',
								onClick: (obj) => {
								this.setState({
									visible: true,
									userKey: obj.rowData.userKey,
									apiName:'getZjPrTodoListByReviewer',
									flowId:"zjProLaunchFlowByOne,zjProLaunchFlowByTwo,zjProLaunchFlowByThree",
								})
							}									
							},
							isInForm: false
						},
						{
							table: {
								title: '审批次数', //表头标题
								dataIndex: 'totalReview', //表格里面的字段
								key: 'totalReview',//表格的唯一key 
								align: 'center',
								onClick: (obj) => {
									this.setState({
										visible: true,
										userKey: obj.rowData.userKey,
										apiName:'getZjPrHasTodoListByReviewer',
										flowId:"zjProLaunchFlowByOne,zjProLaunchFlowByTwo,zjProLaunchFlowByThree",
									})
								}								
							},
							isInForm: false
						},
						{
							table: {
								title: '一级方案审批次数', //表头标题
								dataIndex: 'programmeOneAdoptCount', //表格里面的字段
								key: 'programmeOneAdoptCount',//表格的唯一key 
								align: 'center',
								onClick: (obj) => {
									this.setState({
										visible: true,
										userKey: obj.rowData.userKey,
										apiName:'getZjPrHasTodoListByReviewer',										
										flowId:"zjProLaunchFlowByOne",
									})
								}								
							},
							isInForm: false
						},
						{
							table: {
								title: '二级方案审批次数', //表头标题
								dataIndex: 'programmeTwoAdoptCount', //表格里面的字段
								key: 'programmeTwoAdoptCount',//表格的唯一key 
								align: 'center',
								onClick: (obj) => {
									this.setState({
										visible: true,
										userKey: obj.rowData.userKey,
										apiName:'getZjPrHasTodoListByReviewer',										
										flowId:"zjProLaunchFlowByTwo",
									})
								}								
							},
							isInForm: false
						},												
						// {
						// 	table: {
						// 		title: '修改者', //表头标题
						// 		dataIndex: 'modifyUserName', //表格里面的字段
						// 		key: 'modifyUserName',//表格的唯一key  
						// 		align: 'center'
						// 	},
						// 	isInForm: false
						// },
						// {
						// 	table: {
						// 		title: '修改时间',
						// 		dataIndex: 'modifyTime',
						// 		key: 'modifyTime',
						// 		align: 'center',
						// 		format: 'YYYY-MM-DD HH:mm:ss',
						// 	},
						// 	isInForm: false
						// },
						{
							isInForm: false,
							table: {
								showType: 'tile', //出来的样式 bubble（气泡）  tile（平铺） 默认bubble  （0.6.15版本中将该属性移动到table属性下，也可写到table同级）
								key: "action",//操作列名称
								btns: [
									{
										label: "", //和render不可同时存在
										name: 'edit', // 内置name有【add,  del, edit, detail, Component, form】
										render: function (rowData) {
											return '<a>修改</a>';
										},
										//表单里面的按钮  name内置 【submit， cancel】
										//  可为func
										formBtns: [
											{
												name: 'cancel', //关闭右边抽屉
												type: 'dashed',//类型  默认 primary
												label: '取消',
												hide: function (obj) {
													var index = obj.btnCallbackFn.getActiveKey();
													if (index === "1" || index === "2") {
														return true;
													} else {
														return false;
													}
												},
											},
											{
												name: 'diyJBs',//内置add del
												type: 'primary',//类型  默认 primary
												label: '提交',//提交数据并且关闭右边抽屉
												hide: function (obj) {
													var index = obj.btnCallbackFn.getActiveKey();
													if (index === "1" || index === "2") {
														return true;
													} else {
														return false;
													}
												},
												onClick: function (obj) { //此时里面会多一个response
													const { fetch, msg } = obj.btnCallbackFn;
													fetch('updateZjPrProgrammeExpertAnonymous', { ...obj._formData }, function ({ data, success, message }) {
														if (success) {
															var anonymousId = {
																anonymousId: data.anonymousId
															};
															obj.props.form.setFieldsValue(anonymousId);
															msg.success(message)
															obj.btnCallbackFn.refresh();
															obj.btnCallbackFn.closeDrawer(false);
														} else {
															msg.error(message);
														}
													})
												}
											}
										]
									}
								]
							}
						}
					]}
				/>
				<div>
					<Modal
						width={'90%'}
						style={{ paddingBottom: '0', top: '0' }}
						title="已审批方案"
						visible={visible}
						footer={null}
						onCancel={this.handleCancel}
						bodyStyle={{ padding: '10px', height: '90vh', overflow: 'auto' }}
						centered={true}
						wrapClassName={'modals'}
					>
						<Spin spinning={loading}>
							<QnnTable
								{...this.props}
								fetch={this.props.myFetch}
								upload={this.props.myUpload}
								headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
								// {...configDetail}
								fetchConfig={{//表格的ajax配置
									apiName: apiName,
									otherParams: {
										userKey: userKey,
										flowId: flowId,
									}
								}}
								formConfig={[
									{
										table: {
											title: '项目名称', //表头标题
											dataIndex: 'projectName', //表格里面的字段
											key: 'projectName',//表格的唯一key
										},
										isInForm: false,
										isInSearch: true,
										form: {
											type: 'string',
											placeholder: "请输入"
										}
									},
									{
										table: {
											title: '方案编号', //表头标题
											dataIndex: 'schemeNumber', //表格里面的字段
											key: 'schemeNumber',//表格的唯一key
										},
										isInForm: false,
									},
									{
										table: {
											title: '方案名称', //表头标题
											dataIndex: 'schemeName', //表格里面的字段
											key: 'schemeName',//表格的唯一key
										},
										isInForm: false,
									},
									{
										table: {
											title: '方案计划实施时间',
											dataIndex: 'implementationTime',
											key: 'implementationTime',
											format: 'YYYY-MM-DD',
										},
										isInForm: false
									},
									{
										table: {
											title: '项目总工', //表头标题
											dataIndex: 'projectChiefEng', //表格里面的字段
											key: 'projectChiefEng',//表格的唯一key
										},
										isInForm: false,
									},
									{
										table: {
											title: '方案编制者', //表头标题
											dataIndex: 'createUserName', //表格里面的字段
											key: 'createUserName',//表格的唯一key
										},
										isInForm: false,
									},
									{
										table: {
											title: '评审状态', //表头标题
											dataIndex: 'reviewState', //表格里面的字段
											key: 'reviewState',//表格的唯一key  
											render: (data) => {
												let r = "未知";
												switch (data) {
													case "":
														r = "未发起"
														break
													case "0":
														r = "未评审"
														break
													case "1":
														r = "评审通过"
														break
													case "2"://
														r = "评审中"
														break
													case "3":
														r = "评审未通过"
														break
													case "4"://
														r = "评审已通过"
														break
												}
												return r
											}
										},
										isInForm: false
									},
									{
										table: {
											title: '备注', //表头标题
											dataIndex: 'remarks', //表格里面的字段
											key: 'remarks',//表格的唯一key
										},
										isInForm: false,
									}

								]}
							/>
						</Spin>
					</Modal>
				</div>
			</div>
		);
	}
}
// export default index;
export default basic(index);
