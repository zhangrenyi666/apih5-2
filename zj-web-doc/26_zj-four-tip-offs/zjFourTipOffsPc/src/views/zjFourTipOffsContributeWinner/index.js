//典型问题曝光列表
import React, { Component } from "react";
import { basic } from "../modules/layouts";
import QnnTable from "../modules/qnn-table";
const config = {
	fetchConfig: {//表格的ajax配置
		apiName: 'getContributionList',
		otherParams: { listType: "0" }, //需要固定写死的参数  可为function 返回必须为object

	},
	antd: { //同步antd table组件配置 ***必传
		rowKey: function (row) {// ***必传
			return row.feedbackId
		},
		size:'small'
	},
	drawerConfig: {
		width: '1000px'
	},
	paginationConfig: {// 同步antd的分页组件配置   
		position: 'bottom'
	},

	firstRowIsSearch: true,
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
	},
	actionBtns: [
		{
			name: 'del',//内置add del
			icon: 'delete',//icon
			type: 'danger',//类型  默认 primary  [primary dashed danger]
			label: '删除',
			fetchConfig: {//ajax配置
				apiName: 'batchDeleteUpdateZjProblemFeedback',
			},
		}
	],
	formConfig: [
		{
			isInTable: false,
			form: {
				field: 'feedbackId',
				type: 'string',
				placeholder: '请输入',
				hide: true,
			}
		},
		// {
		// 	table: {
		// 		title: '投稿标题', //表头标题
		// 		dataIndex: 'feedbackTitle', //表格里面的字段
		// 		key: 'feedbackTitle',//表格的唯一key  
		// 		width: '15%',
		// 		onClick: "detail",
		// 		filter: true
		// 	},
		// 	form: {
		// 		type: 'string',
		// 		placeholder: '请输入',
		// 	}
		// },
		{
			table: {
				title: '投稿内容', //表头标题
				dataIndex: 'feedbackDescribe', //表格里面的字段
				key: 'feedbackDescribe',//表格的唯一key  
				tooltip:15
			},
			form: {
				type: 'string',
				placeholder: '请输入',
			}
		},
		{
			table: {
				title: '交流类型',
				dataIndex: 'typeName',//feedbackType 回显
				key: 'typeName',
				fieldsConfig: {
					type: 'select',
					field: 'serverId',
					placeholder: '请选择',
					fetchConfig: {
						apiName: "getBaseCodeSelect",
						otherParams: {
							itemId: 'fuwuleixing'
						}
					},
					optionConfig: {//下拉选项配置
						label: 'itemName', //默认 label
						value: 'itemId',//
					},
				}
			},
			form: {
				placeholder: '请输入',
				type: 'select',
				fetchConfig: {
					apiName: "getBaseCodeSelect",
					otherParams: {
						itemId: 'fuwuleixing'
					}
				},
				optionConfig: {//下拉选项配置
					label: 'itemName', //默认 label
					value: 'itemId',//
				},
			}
		},
		{
			table: {
				title: '投稿人',
				dataIndex: 'feedbackUserName',
				key: 'feedbackUserName',
			},
			isInForm: false
		},
		{
			table: {
				title: '投稿日期',
				dataIndex: 'feedbackTime',
				key: 'feedbackTime',
				format: 'YYYY-MM-DD HH:mm:ss',
				fieldsConfig: {
					type: "none"
				},
			},
			isInForm: false
		},
		{
			table: {
				title: '等级',
				dataIndex: 'questionName',
				key: 'questionName',
				fieldsConfig: {
					type: 'select',
					field: 'questionId',
					placeholder: '请选择',
					fetchConfig: {
						apiName: "getBaseCodeSelect",
						otherParams: {
							itemId: 'wentidengji'
						}
					},
					optionConfig: {//下拉选项配置
						label: 'itemName', //默认 label
						value: 'itemId',//
					},
				}
			},
			form: {
				placeholder: '请输入',
				type: 'select',
				fetchConfig: {
					apiName: "getBaseCodeSelect",
					otherParams: {
						itemId: 'wentidengji'
					}
				},
				optionConfig: {//下拉选项配置
					label: 'itemName', //默认 label
					value: 'itemId',//
				},
			}
		},
		{
			table: {
				title: '奖金（元）',
				dataIndex: 'bonusAmount',
				key: 'bonusAmount',
				fieldsConfig: {
					type: "none"
				},
			},
			isInForm: false
		}
	]
};

class index extends Component {
	render() {
		return (
			<div>
				<QnnTable
					{...this.props}
					fetch={this.props.myFetch} 
		 upload={this.props.myUpload}
					headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
					{...config}
				/>
			</div>
		);
	}
}
export default basic(index);