import { basic } from '../modules/layouts';
import React, { Component } from "react";
import QnnTable from "../modules/qnn-table";
import { Modal, Spin } from 'antd';
const configs = {
	fetchConfig: {//表格的ajax配置
		apiName: 'getZjPrReviewerStatisticsList',
	},
	antd: { //同步antd table组件配置 ***必传
		rowKey: function (row) {// ***必传
			return row.anonymousId
		},
		size:'small'
	},
	drawerConfig: {
		width: '1000px'
	},
	paginationConfig: {// 同步antd的分页组件配置   
		position: 'bottom'
    },
    isShowRowSelect: false,  
	actionBtns: [
        {
            name: 'export',//内置add del
            icon: 'export',//icon
            type: 'primary',//类型  默认 primary  [primary dashed danger]
            label: '导出',
            onClick: (obj) => {
                // window.location.href = "http://test.apih5.com:9091/web/zjPrSchemeChangeToPdf?filePath=http://weixin.fheb.cn:91/ureport/excel?_u=file:评审人统计.ureport.xml&url=http://weixin.fheb.cn:98/apifangan/&type=.xlsx&name=abcdefg"
                
                window.location.href = "http://weixin.fheb.cn:91/ureport/excel?_u=file:评审人统计.ureport.xml&url=http://weixin.fheb.cn:98/apifangan/";//线上
                // window.location.href = "http://weixin.fheb.cn:91/ureport/excel?_u=file:评审人统计.ureport.xml&url=http://test.apih5.com:9091/web/";//本地
            }
        },
	],
	//每个表单项的布局 -- 搜索区域
	formItemLayoutSearch: {
		//默认数据
		labelCol: {
			xs: { span: 24 },
			sm: { span: 6 }
		},
		wrapperCol: {
			xs: { span: 24 },
			sm: { span: 18 }
		}
	}
}
const configDetail = {
	antd: { //同步antd table组件配置 ***必传
		rowKey: function (row) {// ***必传
			return row.anonymousId
		},
		size:'small'
	},
	drawerConfig: {
		width: '1000px'
	},
	paginationConfig: {// 同步antd的分页组件配置   
		position: 'bottom'
    },
    isShowRowSelect: false,  
	actionBtns: [
        {
            name: 'export',//内置add del
            icon: 'export',//icon
            type: 'primary',//类型  默认 primary  [primary dashed danger]
            label: '导出',
            onClick: (obj) => {
                // window.location.href = "http://test.apih5.com:9091/web/zjPrSchemeChangeToPdf?filePath=http://weixin.fheb.cn:91/ureport/excel?_u=file:评审人统计.ureport.xml&url=http://weixin.fheb.cn:98/apifangan/&type=.xlsx&name=abcdefg"
                
                window.location.href = "http://weixin.fheb.cn:91/ureport/excel?_u=file:评审人统计.ureport.xml&url=http://weixin.fheb.cn:98/apifangan/";//线上
                // window.location.href = "http://weixin.fheb.cn:91/ureport/excel?_u=file:评审人统计.ureport.xml&url=http://test.apih5.com:9091/web/";//本地
            }
        },
	],
	//每个表单项的布局 -- 搜索区域
	formItemLayoutSearch: {
		//默认数据
		labelCol: {
			xs: { span: 24 },
			sm: { span: 6 }
		},
		wrapperCol: {
			xs: { span: 24 },
			sm: { span: 18 }
		}
	}
}
class index extends Component {
	constructor() {
		super();
		this.state = {
			visible: false,
			userKey: '',
			flowId:'',
			loading: false
		}
	}
	handleCancel = () => {
		this.setState({ visible: false });
	}
	render() {
		const { visible, userKey, flowId, loading } = this.state;
		return (
			<div>
				<QnnTable
					{...this.props}
					fetch={this.props.myFetch} 		 upload={this.props.myUpload}
					headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
					{...configs}
					formConfig={[
						{
							table: {
								title: '专家名称', //表头标题
								dataIndex: 'userName', //表格里面的字段
								key: 'userName',//表格的唯一key
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
								title: '审核总次数', //表头标题
								dataIndex: 'totalReview', //表格里面的字段
								key: 'totalReview',//表格的唯一key
								onClick: (obj) => {
									this.setState({
										visible: true,
										userKey: obj.rowData.userKey,
										flowId:"zjProLaunchFlowByOne,zjProLaunchFlowByTwo,zjProLaunchFlowByThree",
									})
								}									
							},
							isInForm: false,
						},
						{
							table: {
								title: '<span>一级方案<br/>(审核次数)</span>', //表头标题
								dataIndex: 'programmeOneAdoptCount', //表格里面的字段
								key: 'programmeOneAdoptCount',//表格的唯一key
								onClick: (obj) => {
									this.setState({
										visible: true,
										userKey: obj.rowData.userKey,
										flowId:"zjProLaunchFlowByOne",
									})
								}								
							},
							isInForm: false,
						},
						{
							table: {
								title: '<span>二级方案<br/>(审核次数)</span>', //表头标题
								dataIndex: 'programmeTwoAdoptCount', //表格里面的字段
								key: 'programmeTwoAdoptCount',//表格的唯一key
								onClick: (obj) => {
									this.setState({
										visible: true,
										userKey: obj.rowData.userKey,
										flowId:"zjProLaunchFlowByTwo",
									})
								}									
							},
							isInForm: false,
						},
						{
							table: {
								title: '<span>三级方案<br/>(审核次数)</span>', //表头标题
								dataIndex: 'programmeThreeAdoptCount', //表格里面的字段
								key: 'programmeThreeAdoptCount',//表格的唯一key
								onClick: (obj) => {
									this.setState({
										visible: true,
										userKey: obj.rowData.userKey,
										flowId:"zjProLaunchFlowByThree",
									})
								}									
							},
							isInForm: false,
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
								fetch={this.props.myFetch} 		 upload={this.props.myUpload}
								headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
								// {...configDetail}
								fetchConfig = {{//表格的ajax配置
									apiName: 'getZjPrHasTodoListByReviewer',
									otherParams:{
										userKey:userKey,
										flowId:flowId,
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
export default basic(index);
