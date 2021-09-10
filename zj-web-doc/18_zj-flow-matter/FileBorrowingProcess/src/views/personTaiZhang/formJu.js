import React, { Component } from "react";
import { Button, message as Msg } from 'antd';
import QnnForm from "../modules/qnn-table/qnn-form";
import moment from 'moment';
class index extends Component {
	constructor(props) {
		super(props);
		this.state = {
			flowData: props.flowData ? props.flowData : '',
			height: document.body.clientHeight - 55 - 53,
			clickAction: props.clickAction ? props.clickAction : ''
		}
	}
	componentDidMount() {

	}
	render() {
		const { height, flowData, clickAction } = this.state;
		return (
			<div style={{ height: "100%", position: 'relative' }}>
				<div style={{ background: '#fff', height: height, overflow: 'auto' }}>
					<div style={{ overflow: 'auto' }}>
						<QnnForm
							{...this.props}
							match={this.props.match}
							fetch={this.props.myFetch}
							upload={this.props.myUpload}
							headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
							wrappedComponentRef={(me) => {
								this.formHasTicket = me;
							}}
							fetchConfig={{
								apiName: 'getZjFlowArchivesLibraryDetails',
								otherParams: {
									archivesLibraryId: flowData
								}
							}}
							formConfig={[
								{
									type: "string",
									label: "archivesLibraryId",
									field: "archivesLibraryId",
									hide: true
								},
								{
									type: "string",
									label: "编号",
									disabled: true,
									field: "autoNum",
									placeholder: "请输入",
									span: 12,
									formItemLayout: {
										labelCol: {
											xs: { span: 24 },
											sm: { span: 6 }
										},
										wrapperCol: {
											xs: { span: 24 },
											sm: { span: 18 }
										}
									},
								},
								{
									type: "date",
									label: "时间",
									disabled: true,
									field: "createTime",
									placeholder: "请输入",
									span: 12,
									formItemLayout: {
										labelCol: {
											xs: { span: 24 },
											sm: { span: 6 }
										},
										wrapperCol: {
											xs: { span: 24 },
											sm: { span: 18 }
										}
									},
								},
								{
									type: "string",
									label: "申请单位",
									field: "appUnit",
									disabled: true,
									placeholder: "请输入",
									span: 12,
									formItemLayout: {
										labelCol: {
											xs: { span: 24 },
											sm: { span: 6 }
										},
										wrapperCol: {
											xs: { span: 24 },
											sm: { span: 18 }
										}
									},
								},
								{
									type: "string",
									label: "申请部门",
									field: "appDept",
									disabled: true,
									placeholder: "请输入",
									span: 12,
									formItemLayout: {
										labelCol: {
											xs: { span: 24 },
											sm: { span: 6 }
										},
										wrapperCol: {
											xs: { span: 24 },
											sm: { span: 18 }
										}
									},
								},
								{
									type: "string",
									label: "申请人",
									field: "appUser",
									placeholder: "请输入",
									disabled: true,
									span: 12,
									formItemLayout: {
										labelCol: {
											xs: { span: 24 },
											sm: { span: 6 }
										},
										wrapperCol: {
											xs: { span: 24 },
											sm: { span: 18 }
										}
									},
								},
								{
									type: "string",
									label: "借阅目的",
									disabled: true,
									field: "borrowPurpose",
									placeholder: "请输入",
									span: 12,
									formItemLayout: {
										labelCol: {
											xs: { span: 24 },
											sm: { span: 6 }
										},
										wrapperCol: {
											xs: { span: 24 },
											sm: { span: 18 }
										}
									},
								},
								{
									type: "number",
									label: "借阅份数",
									disabled: true,
									field: "borrowCopy",
									placeholder: "请输入",
									span: 12,
									formItemLayout: {
										labelCol: {
											xs: { span: 24 },
											sm: { span: 6 }
										},
										wrapperCol: {
											xs: { span: 24 },
											sm: { span: 18 }
										}
									},
								},
								{
									type: "string",
									label: "联系方式",
									field: "phone",
									disabled: true,
									placeholder: "请输入",
									span: 12,
									formItemLayout: {
										labelCol: {
											xs: { span: 24 },
											sm: { span: 6 }
										},
										wrapperCol: {
											xs: { span: 24 },
											sm: { span: 18 }
										}
									},
								},
								{
									type: "string",
									hide: true,
									initialValue: '1',
									field: "archivesType",
									placeholder: "请输入"
								},
								{
									type: "string",
									label: "借阅效果",
									required: true,
									disabled: clickAction === 'detail' ? true : false,
									field: "borrowEffect",
									placeholder: "请输入",
									span: 12,
									formItemLayout: {
										labelCol: {
											xs: { span: 24 },
											sm: { span: 6 }
										},
										wrapperCol: {
											xs: { span: 24 },
											sm: { span: 18 }
										}
									},
								},
								{
									type: "date",
									label: "归还时间",
									field: "returnTime",
									disabled: clickAction === 'detail' ? true : false,
									initialValue: () => {
										return moment(new Date()).format('YYYY-MM-DD')
									},
									placeholder: "请输入",
									span: 12,
									formItemLayout: {
										labelCol: {
											xs: { span: 24 },
											sm: { span: 6 }
										},
										wrapperCol: {
											xs: { span: 24 },
											sm: { span: 18 }
										}
									},
								},
								{
									label:'状态',
									type: 'select',
									disabled: true,
									field:'returnStatus',
									optionConfig: {
										label: 'label',
										value: 'value'
									},
									optionData: [
										{ label: '未归还', value: '0' },
										{ label: '已归还', value: '1' },
										{ label: '结束', value: '2' }
									],
									span: 12,
									formItemLayout: {
										labelCol: {
											xs: { span: 24 },
											sm: { span: 6 }
										},
										wrapperCol: {
											xs: { span: 24 },
											sm: { span: 18 }
										}
									},
									hide: () => {
										if (clickAction === 'detail') {
											return false
										} else {
											return true
										}
									},
								},
								{
									type: "qnnForm",
									field: "itemList",
									label: "明细",
									formBlockStyle: { marginTop: "0px" },
									formBlockFormStyle: { padding: "0px 12px" },
									textObj: {
										add: "添加细明",
										del: "删除"
									},
									// addBtnStyle: {
									// 	display:'none'
									// },
									titleStyle: {},
									hide: false,
									disabled: true,
									// canAddForm: false,
									canAddForm: true,
									formFields: [
										{
											type: "string",
											label: "档案名称",
											field: "archivesName",
											disabled: true,
											placeholder: "请输入",
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
											type: "string",
											label: "档案号",
											disabled: true,
											field: "archivesNumber",
											placeholder: "请输入",
											span: 12,
											formItemLayout: {
												labelCol: {
													xs: { span: 24 },
													sm: { span: 6 }
												},
												wrapperCol: {
													xs: { span: 24 },
													sm: { span: 18 }
												}
											},
										},
										{
											type: "select",
											label: "借阅方式",
											disabled: true,
											field: "borrowWay",
											placeholder: "请输入",
											optionConfig: {
												label: 'label',
												value: 'value'
											},
											optionData: [
												{ label: '借出原件', value: '0' },
												{ label: '拷贝电子文件', value: '1' },
												{ label: '查阅', value: '2' },
												{ label: '复印或拍照', value: '3' },
												{ label: '加盖归档公章', value: '4' },
											],
											span: 12,
											formItemLayout: {
												labelCol: {
													xs: { span: 24 },
													sm: { span: 6 }
												},
												wrapperCol: {
													xs: { span: 24 },
													sm: { span: 18 }
												}
											},
										},
									]
								},
								{
									label: '附件',
									field: 'fileList',
									type: 'files',
									disabled: true,
									fetchConfig: {
										apiName: window.configs.domain + 'upload'
									},
									span: 12,
									formItemLayout: {
										labelCol: {
											xs: { span: 24 },
											sm: { span: 6 }
										},
										wrapperCol: {
											xs: { span: 24 },
											sm: { span: 18 }
										}
									},
								}
							]}
							btns={[
								// {
								//     name: "submit",
								//     type: "primary",
								//     label: "确定",
								//     field: 'submit',
								//     onClick: (obj) => {
								//         console.log(obj);
								//     }
								// }
							]}
						/>
					</div>
				</div>
				<div style={{ position: 'absolute', bottom: 0, background: 'white', textAlign: 'end', width: '100%', padding: '10px 16px', borderTop: '1px solid rgb(232, 232, 232)', display: clickAction === 'detail' ? 'none' : 'block' }}>
					<Button type="dashed" style={{ marginRight: '8px' }} onClick={() => {
						this.props.btnCallbackFn.closeDrawer();
					}}>返回</Button>
					<Button type="primary" onClick={() => {
						let value = this.formHasTicket.form.getFieldsValue();
						console.log(value);
						if (value.borrowEffect) {
							this.props.myFetch("returnZjFlowArchivesLibraryForPerson", value).then(({ success, data, message }) => {
								if (success) {
									Msg.success(message);
									this.props.btnCallbackFn.refresh();
									this.props.btnCallbackFn.closeDrawer();
								} else {
									Msg.error(message);
								}
							})
						} else {
							Msg.warn('请填写借阅效果！');
						}
						

					}}>保存</Button>
				</div>
			</div>
		);
	}
}
export default index;
