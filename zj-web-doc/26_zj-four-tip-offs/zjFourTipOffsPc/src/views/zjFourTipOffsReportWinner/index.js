//典型问题曝光列表
import React, { Component } from "react";
import { basic } from "../modules/layouts";
import QnnTable from "../modules/qnn-table";
const config = {
	fetchConfig: {//表格的ajax配置
		apiName: 'getReportingList',

	},
	antd: { //同步antd table组件配置 ***必传
		rowKey: function (row) {// ***必传
			return row.reportId
		},
		size:'small'
	},
	drawerConfig: {
		width: '10000px'
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
				apiName: 'batchDeleteUpdateZjReportInfo',
			},
		}
	],
	formConfig: [
		{
			isInTable: false,
			form: {
				field: 'reportId',
				type: 'string',
				placeholder: '请输入',
				hide:true,
			}
		},
		{
			table: {
				title: '问题标题', //表头标题
				dataIndex: 'title', //表格里面的字段
				key: 'title',//表格的唯一key  
				tooltip:15
			},
			form: {
				type: 'string',
				required: true,
				placeholder: '请输入',
			}
		},
		{
			table: {
				title: '问题描述', //表头标题
				dataIndex: 'problemDescribe', //表格里面的字段
				key: 'problemDescribe',//表格的唯一key  
				tooltip:15
			},
			form: {
				type: 'textarea',
				placeholder: '请输入',
			}
		},
		{
			table: {
				title: '来源',
				dataIndex: 'reportType',
				key: 'reportType',
				render: function (data) {
					var text = ""
					switch (data) {
						case "0": text = "匿名举报";
							break;
						case "1": text = "实名举报";
							break;
						default: text = "未知";
							break;
					}
					return text
				},
				fieldsConfig: {
					type: 'select',
					field: 'reportType',
					placeholder: '请选择',
					optionData: [//默认选项数据//可为function (props)=>array
					    {
					        name: '匿名举报',
					        id: '0'
					    },
					    {
					        name: '实名举报',
					        id: '1'
						}
					],
					optionConfig: {
						label: 'name',
						value: 'id',
					},
				}
			},
			form: {
				type: 'select',
				placeholder: '请选择',
				optionData: [//默认选项数据
					{
						reportType: '匿名举报',
						id: '0'
					},
					{
						reportType: '实名举报',
						id: '1'
					},
				],
				optionConfig: {//下拉选项配置
					label: 'reportType', //默认 label
					value: 'id',//最终的值使用逗号连接 默认值使用valueName 默认['value']
				}
			}
		},
		// {
		// 	table: {
		// 		title: '所属分类',
		// 		dataIndex: 'type',
		// 		key: 'type',
		// 		render: function (data) {
		// 			var text = ""
		// 			switch (data) {
		// 				case "1": text = "技术质量";
		// 					break;
		// 				case "2": text = "违规违纪";
		// 					break;
		// 				case "3": text = "安环隐患";
		// 					break;
		// 				default: text = "未知";
		// 					break;
		// 			}
		// 			return text
		// 		},
		// 		fieldsConfig: {
		// 			type: 'select',
		// 			field: 'type',
		// 			placeholder: '请选择',
		// 			optionData: [//默认选项数据//可为function (props)=>array
		// 			    {
		// 			        name: '技术质量',
		// 			        id: '1'
		// 			    },
		// 			    {
		// 			        name: '违规违纪',
		// 			        id: '2'
		// 				},
		// 				{
		// 			        name: '安环隐患',
		// 			        id: '3'
		// 				}
		// 			],
		// 			optionConfig: {
		// 				label: 'name',
		// 				value: 'id',
		// 			},
		// 		}
		// 	},
		// 	isInForm: false
		// },
		{
			table: {
				title: '举报日期',
				dataIndex: 'reportTime',
				key: 'reportTime',
				format: 'YYYY-MM-DD HH:mm:ss',
				fieldsConfig: {
					type: "none"
				},
			},
			isInForm: false
		},
		{
			table: {
				title: '严重程度',
				dataIndex: 'problemLevel',
				key: 'problemLevel',
				render: function (data) {
					if(data === '0'){
						return <div><span style={{display:'inline-block',backgroundColor:'#5ad084',height:'10px',width:'10px',borderRadius:'50%',lineHeight:'21px'}}></span> 正常</div>
					}else if(data === '1'){
						return <div><span style={{display:'inline-block',backgroundColor:'#44c4f7',height:'10px',width:'10px',borderRadius:'50%',lineHeight:'21px'}}></span> 忽略</div>
					}else if(data === '2'){
						return <div><span style={{display:'inline-block',backgroundColor:'#e4ff18',height:'10px',width:'10px',borderRadius:'50%',lineHeight:'21px'}}></span> 轻微</div>
					}else if(data === '3'){
						return <div><span style={{display:'inline-block',backgroundColor:'#ff7a18',height:'10px',width:'10px',borderRadius:'50%',lineHeight:'21px'}}></span> 严重</div>
					}else if(data === '4'){
						return <div><span style={{display:'inline-block',backgroundColor:'#f10707',height:'10px',width:'10px',borderRadius:'50%',lineHeight:'21px'}}></span> 警告</div>
					}
				},
				fieldsConfig: {
					type: 'select',
					field: 'problemLevel',
					placeholder: '请选择',
					optionData: [//默认选项数据//可为function (props)=>array
					    {
					        name: '正常',
					        id: '0'
					    },
					    {
					        name: '忽略',
					        id: '1'
						},
						{
					        name: '轻微',
					        id: '2'
						},
						{
					        name: '严重',
					        id: '3'
						},
						{
					        name: '警告',
					        id: '4'
					    },
					],
					optionConfig: {
						label: 'name',
						value: 'id',
					},
				}
			},
			form: {
				type: 'select',
				placeholder: '请选择',
				optionData: [//默认选项数据
					{
						problemLevel: '正常',
						id: '0'
					},
					{
						problemLevel: '忽略',
						id: '1'
					},
					{
						problemLevel: '轻微',
						id: '2'
					},
					{
						problemLevel: '严重',
						id: '3'
					},
					{
						problemLevel: '警告',
						id: '4'
					},
				],
				optionConfig: {//下拉选项配置
					label: 'problemLevel', //默认 label
					value: ['id'],//最终的值使用逗号连接 默认值使用valueName 默认['value']
				}
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
		},
		{
			table: {
				title: '举报位置',
				dataIndex: 'reportLocal',
				key: 'reportLocal',
				tooltip:15
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
					fetch={this.props.myFetch} 		 upload={this.props.myUpload}
					headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
					{...config}
				/>
			</div>
		);
	}
}
export default basic(index);