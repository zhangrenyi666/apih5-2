import { basic } from '../modules/layouts'; 
import React, { Component } from "react";
import QnnTable from "../modules/qnn-table";
import ExtemalLaunchFlowApply from "./extemalLaunchFlowApply";
import ExtemalLaunchFlowDetail from "./extemalLaunchFlowDetail";
import { Modal } from 'antd';
const { confirm } = Modal;
class index extends Component {
	render() {
		return (
				<div>
				<QnnTable
				{...this.props}
				fetch={this.props.myFetch} 
		        upload={this.props.myUpload}
				headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
				fetchConfig = {{//表格的ajax配置
					apiName: 'getZjPrExtemalLaunchFlowAddLaunchList',
				}}
				antd = {{ //同步antd table组件配置 ***必传
					rowKey: function (row) {// ***必传
						return row.launchFlowId
					},
					size:'small'
				}}
				drawerConfig = {{
					width: '1000px'
				}}
				paginationConfig = {{// 同步antd的分页组件配置   
					position: 'bottom'
				}}
				drawerConfig ={ {
					width: '80%'
				}}
				actionBtns = {[
					{
						name: 'Component',//内置add del
						icon: 'plus',//icon
						type: 'primary',//类型  默认 primary  [primary dashed danger]
						label: '发起流程',
						// Component: MerchantsAdd,
						Component: (obj) => {
							return <ExtemalLaunchFlowApply {...obj}/>
						}
					},        
					{
						name: 'export',//内置add del
						icon: 'export',//icon
						type: 'primary',//类型  默认 primary  [primary dashed danger]
						label: '导出',
						onClick: (obj) => {
							console.log(obj);
							const { fetch,msg } = obj.btnCallbackFn;
							if (obj.selectedRows.length) {
								if (obj.selectedRows.length > 1) {
									msg.error('导出时只能选择一条数据！');
								} else{
									confirm({
                                        content: '请选择要加盖印章',
                                        okText: "一公局印章",
                                        cancelText: "隧道局印章",
                                        onOk() {
											window.location.href = "http://weixin.fheb.cn:98/apifangan/zjPrSchemeChangeToPdfHandwriting?filePath=http://weixin.fheb.cn:91/ureport/word?_u=file:zjPrExtemalReview.ureport.xml$url=http://weixin.fheb.cn:98/apifangan/$launchFlowId=" + obj.selectedRows[0].launchFlowId+ "$sealType=ygj"+"&type=.docx&name="+obj.selectedRows[0].schemeName;									
											// window.location.href = "http://test.apih5.com:9091/web/zjPrSchemeChangeToPdfHandwriting?filePath=http://weixin.fheb.cn:91/ureport/word?_u=file:zjPrReviewThree.ureport.xml$url=http://test.apih5.com:9091/web/$launchFlowId=" + obj.selectedRows[0].launchFlowId+"&type=.docx&name="+obj.selectedRows[0].schemeName;											
                                        },
                                        onCancel() {
											window.location.href = "http://weixin.fheb.cn:98/apifangan/zjPrSchemeChangeToPdfHandwriting?filePath=http://weixin.fheb.cn:91/ureport/word?_u=file:zjPrExtemalReview.ureport.xml$url=http://weixin.fheb.cn:98/apifangan/$launchFlowId=" + obj.selectedRows[0].launchFlowId+ "$sealType=sdj"+"&type=.docx&name="+obj.selectedRows[0].schemeName;									
											// window.location.href = "http://test.apih5.com:9091/web/zjPrSchemeChangeToPdfHandwriting?filePath=http://weixin.fheb.cn:91/ureport/word?_u=file:zjPrReviewThree.ureport.xml$url=http://test.apih5.com:9091/web/$launchFlowId=" + obj.selectedRows[0].launchFlowId+"&type=.docx&name="+obj.selectedRows[0].schemeName;
                                        }
                                    }); 									
									} 
							} else {
								msg.error('未选择任何项！');
							}
						}
					},
				]}
				//每个表单项的布局 -- 搜索区域
				formItemLayoutSearch = {{
					//默认数据
					labelCol: {
						xs: { span: 24 },
						sm: { span: 6 }
					},
					wrapperCol: {
						xs: { span: 24 },
						sm: { span: 18 }
					}
				}}
				formConfig = {[
					{
						isInForm: false,
						table: {
							width: 25,
							align: 'center',
							title: 'No.', //表头标题
							dataIndex: 'no', //表格里面的字段
							key: 'no',//表格的唯一key    
							render: (data,rows,index) => {
								return index + 1;
							}
						},
					},
					{
						table: {
							title: '项目名称', //表头标题
							dataIndex: 'projectName', //表格里面的字段
							isInSearch: true,
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
							title: '所属板块', //表头标题
							dataIndex: 'projectClass', //表格里面的字段
							key: 'projectClass',//表格的唯一key  
							render: (data) => {
								let r = "未知";
								switch (data) {
									case "0":
										r = "公路市政"
										break
									case "1":
										r = "铁路轨道"
										break
									case "2"://
										r = "城市房建"
										break
									case "3":
										r = "海外事业部"
										break
								}
								return r
							}
						},                    
						isInForm: false,
						isInSearch: true,
						form: {
							type: 'select',
							label: '所属板块',
							field: 'projectClass', //唯一的字段名 ***必传
							placeholder: '请选择',
							required: true,
							multiple: false, //是否开启多选功能 开启后自动开启搜索功能
							showSearch: false, //是否开启搜索功能 (移动端不建议开启)
							optionData: [//默认选项数据//可为function (props)=>array
								{
									value: '0',
									text: '公路市政'
								},
								{
									value: '1',
									text: '铁路轨道'
								},
								{
									value: '2',
									text: '城市房建'
								},
								{
									value: '3',
									text: '海外事业部'
								},
							],
							optionConfig: {//下拉选项配置
								label: 'text', //默认 label
								value: 'value',// [array | string]  最终的值使用逗号连接 默认值使用valueName 默认['value']
							},
						}
					},                
					{
						table: {
							title: '方案编号', //表头标题
							dataIndex: 'schemeNumber', //表格里面的字段
							key: 'schemeNumber',//表格的唯一key  					
						},
						isInSearch: true,
						form: {
							type: 'string',
							placeholder: "请输入"
						}
					},
					{
						table: {
							title: '状态',
							dataIndex: 'projectGeneralUser',
							key: 'projectGeneralUser',
						},
						isInTable: false,
						isInSearch: true,
						form: {
							type: 'select',
							label: '状态',
							field: 'apih5FlowStatus', //唯一的字段名 ***必传
							placeholder: '请选择',
							required: true,
							multiple: false, //是否开启多选功能 开启后自动开启搜索功能
							showSearch: false, //是否开启搜索功能 (移动端不建议开启)
							optionData: [//默认选项数据//可为function (props)=>array
								{
									value: '1',
									text: '评审中'
								},
								{
									value: '2',
									text: '评审已通过'
								}
							],
							optionConfig: {//下拉选项配置
								label: 'text', //默认 label
								value: 'value',// [array | string]  最终的值使用逗号连接 默认值使用valueName 默认['value']
							},
						}
					},
					{
						table: {
							title: '方案名称', //表头标题
							dataIndex: 'schemeName', //表格里面的字段
							key: 'schemeName',//表格的唯一key  
							isCanClick: function (obj) {
								if (obj.rowData.apih5FlowStatus == '2') {
									return true
								} else {
									return false;
								}
							},
							onClick: 'Component',
							Component: ExtemalLaunchFlowDetail
						},
						isInSearch: true,
						form: {
							type: 'string',
							placeholder: "请输入"
						}
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
							dataIndex: 'projectGeneralUser', //表格里面的字段
							key: 'projectGeneralUser',//表格的唯一key  
						},
						isInForm: false
					},
					{
						table: {
							title: '方案编制者', //表头标题
							dataIndex: 'programmingPerson', //表格里面的字段
							key: 'programmingPerson',//表格的唯一key  
						},
						isInForm: false
					},
					{
						table: {
							title: '评审状态', //表头标题
							dataIndex: 'apih5FlowStatus', //表格里面的字段
							key: 'apih5FlowStatus',//表格的唯一key  
							render: (data) => {
								let r = "未知";
								switch (data) {
									case "1"://
										r = "评审中"
										break
									case "2":
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
							title: '方案上传时间',
							dataIndex: 'createTime',
							key: 'createTime',
							format: 'YYYY-MM-DD HH:mm:ss',
						},
						isInForm: false
					},
					{
						table: {
							title: '方案评审通过时间',
							dataIndex: 'modifyTime',
							key: 'modifyTime',
							format: 'YYYY-MM-DD HH:mm:ss',
						},
						isInForm: false
					},
					{
						table: {
							title: '备注', //表头标题
							dataIndex: 'remarks', //表格里面的字段
							key: 'remarks',//表格的唯一key  
						},
						isInForm: false
					},
					{
						table: {
							title: '项目总工',
							dataIndex: 'projectGeneralUser',
							key: 'projectGeneralUser',
						},
						isInTable: false,
						isInSearch: true,
						form: {
							type: 'string',
							placeholder: "请输入"
						}
					},	
					{
						table: {
							title: '技术等级',
							dataIndex: 'schemeLevel',
							key: 'schemeLevel',
						},
						isInTable: false,
						isInSearch: true,
						form: {
							type: 'select',
							label: '技术等级',
							field: 'schemeLevel', //唯一的字段名 ***必传
							placeholder: '请选择',
							required: true,
							multiple: false, //是否开启多选功能 开启后自动开启搜索功能
							showSearch: false, //是否开启搜索功能 (移动端不建议开启)
							optionData: [//默认选项数据//可为function (props)=>array
								{
									value: '0',
									text: 'Ⅲ级施工方案'
								},
								{
									value: '1',
									text: 'Ⅱ级施工方案'
								},
								{
									value: '2',
									text: 'I级施工方案'
								},
								{
									value: '3',
									text: 'IV级施工方案'
								},
							],
							optionConfig: {//下拉选项配置
								label: 'text', //默认 label
								value: 'value',// [array | string]  最终的值使用逗号连接 默认值使用valueName 默认['value']
							},
						}
					},	
					{
						isInTable: false,
						isInSearch: true,
						form: {
							type: 'select',
							label: '编制主体',
							// spanSearch: 12,										
							field: 'compilingSubject', //唯一的字段名 ***必传
							placeholder: '请选择',
							required: true,
							multiple: false, //是否开启多选功能 开启后自动开启搜索功能
							showSearch: false, //是否开启搜索功能 (移动端不建议开启)
							optionData: [//默认选项数据//可为function (props)=>array
								{
									name: '第一技术分中心',
									id: '4d0026M1211f18fd7cMc3cf724c68492c819757e60a238b17d4',
								},
								{
									name: '第二技术分中心',
									id: '4d0026M1211f198db8Mc3cf724c68492c819757e60a238b17d4',
								},
								{
									name: '第三技术分中心',
									id: '1f33ecbM1215114069dMc3cf724c68492c819757e60a238b17d4',
								},
								{
									name: '第四技术分中心',
									id: '4d0026M1211f1b6529Mc3cf724c68492c819757e60a238b17d4',
								},
								{
									name: '第五技术分中心',
									id: '848c58M11ddc135570Mcac6cc7252f26ad204ac504de95ccb51',
								},
								{
									name: '第六技术分中心',
									id: '4d0026M1211f1bbfe4Mc3cf724c68492c819757e60a238b17d4',
								},
								{
									name: '第七技术分中心',
									id: '4d0026M1211f1f4cdcMc3cf724c68492c819757e60a238b17d4',
								},
								{
									name: '厦门技术分中心',
									id: '4d0026M1211f214b76Mc3cf724c68492c819757e60a238b17d4',
								},
								{
									name: '桥隧技术分中心',
									id: '4d0026M1211f20f203Mc3cf724c68492c819757e60a238b17d4',
								},
								{
									name: '海威技术分中心',
									id: '2fcb4fM11e4c91e321Mcac6cc7252f26ad204ac504de95ccb51',
								},
								{
									name: '总承包技术分中心',
									id: '4d0026M1211f206d02Mc3cf724c68492c819757e60a238b17d4',
								},
								{
									name: '建筑技术分中心',
									id: '15c46b5M137e357f29cM9ce72a2dc070b141941f7d5b228cc039',
								},
								{
									name: '世通技术分中心',
									id: '4d0026M1211f1e7c84Mc3cf724c68492c819757e60a238b17d4',
								},
								{
									name: '海外技术中心',
									id: '22bba094M150a1febb4cM9ce72a2dc070b141941f7d5b228cc039',
								}, {
									id: "suidj-189",
									name: "北京技术分中心"
								}, {
									id: "suidj-240",
									name: "西北技术分中心"
								}, {
									id: "suidj-423",
									name: "南京技术分中心"
								}, {
									id: "suidj-549",
									name: "西南技术分中心"
								}, {
									id: "suidj-584",
									name: "第八技术分中心"
								}, {
									id: "suidj-2183",
									name: "华北技术分中心"
								}, {
									id: "suidj-2230",
									name: "华南技术分中心"
								}, {
									id: "suidj-768",
									name: "盾构技术分中心"
								}, {
									id: "suidj-800",
									name: "电气化技术分中心"
								},{
									id: "suidj-8a8bb35a765172cc01768484656d0be1",
									name: "华中技术分中心"
								}
							],
							optionConfig: {//下拉选项配置
								label: 'name', //默认 label
								value: 'id',// [array | string]  最终的值使用逗号连接 默认值使用valueName 默认['value']
							},
						}
					},													
					{
						isInTable: false, 
						isInSearch: true,
						form: {
							label: "周期查询",			
							field: 'modifyTimeArr', //唯一的字段名 ***必传	
							spanSearch: 8,						   
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
						isInTable: false, 
						isInSearch: true,
						form: {
							label: "上传周期",			
							field: 'creatTimeArr', //唯一的字段名 ***必传	
							spanSearch: 8,						   
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
				]}
				searchFormColNum = {6}
				/>
				</div>
		);
	}
}
export default basic(index);
