window.zjProjectSupervise = {//珠海大屏项目桥梁进度
	fetchConfig: {//表格的ajax配置
		apiName: 'getZjPrProjectSuperviseList',
	},
	antd: { //同步antd table组件配置 ***必传
		rowKey: function (row) {// ***必传
			return row.projectId
		},
		size:'small'
	},
	drawerConfig: {
		width: '1000px'
	},
    paginationConfig: {// 同步antd的分页组件配置   
        position: 'bottom'
    },
	actionBtns: [
		{
			name: 'add',//内置add del
			icon: 'plus',//icon
			type: 'primary',//类型  默认 primary  [primary dashed danger]
			label: '新增',
			// Component: MerchantsAdd,
			formBtns: [
				{
					name: 'cancel', //关闭右边抽屉
					type: 'dashed',//类型  默认 primary
					label: '取消',
					hide: function (obj) {
						var index = obj.btnCallbackFn.getActiveKey();
						if (index === "2" || index === "3") {
							return true;
						} else {
							return false;
						}
					},
				},
				{
					name: 'diyJB',//内置add del
					type: 'primary',//类型  默认 primary
					label: '提交',//提交数据并且关闭右边抽屉
					hide: function (obj) {
						var index = obj.btnCallbackFn.getActiveKey();
						if (index === "2" || index === "3") {
							return true;
						} else {
							return false;
						}
					},
					onClick: function (obj) { //此时里面会多一个response
						const { fetch, msg } = obj.btnCallbackFn;
						fetch('addZjPrSdjProjectSupervise', { ...obj._formData }, function ({ data, success, message }) {
							if (success) {
								var projectId = {
									projectId: data.projectId
								};
								obj.props.form.setFieldsValue(projectId);
								msg.success(message)
								obj.btnCallbackFn.refresh();
								obj.btnCallbackFn.closeDrawer(false);
							} else {
								msg.error(message);
								obj.btnCallbackFn.closeDrawer(false);								
							}
						})
					}
				},
			]
		},
		{
			name: 'del',//内置add del
			icon: 'delete',//icon
			type: 'danger',//类型  默认 primary  [primary dashed danger]
			label: '删除',
			fetchConfig: {//ajax配置
				apiName: 'batchDeleteUpdateZjPrSdjProjectSupervise',
			},
		}
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
	},
	tabs: [
		{
			field: "form1",
			name: "qnnForm",
			title: "基本情况",
			content: {
				fetchConfig: function (obj) {
					var rowData = obj.clickCb.rowData;
					if (rowData) {
						return {
							apiName: 'getZjPrSdjProjectSuperviseDetail',
							otherParams: {
								projectId: rowData.projectId
							}
						}
					} else if (obj.form.getFieldsValue().projectId != '') {
						return {
							apiName: 'getZjPrSdjProjectSuperviseDetail',
							otherParams: {
								projectId: obj.form.getFieldsValue().projectId
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
						field: 'projectId', //唯一的字段名 ***必传
						hide: true
					},
					{
						type: 'string',
						label: '项目名称',
						field: 'projectName', //唯一的字段名 ***必传
						required: true,
						editDisabled: true,						
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
						label: '中标资质',
						field: 'code3Name', //唯一的字段名 ***必传
						placeholder: '请选择',
						// pullJoin:false,						
						required: true,
						// multiple: true, //是否开启多选功能 开启后自动开启搜索功能
						// showSearch: false, //是否开启搜索功能 (移动端不建议开启)
						optionData: [//默认选项数据//可为function (props)=>array
						    {
						        name: '一公局',
						        id: '一公局',
						    },
						    {
						        name: '隧道局',
						        id: '隧道局',
						    },
						    {
						        name: '一公局联合体',
						        id: '一公局联合体',
						    },
						    {
						        name: '隧道局联合体',
						        id: '隧道局联合体',
						    },
						    {
						        name: '中国交建',
						        id: '中国交建',
						    },
						    {
						        name: '其他',
						        id: '其他',
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
						label: '工程类别',
						field: 'engineeringType', //唯一的字段名 ***必传
						// required: true,
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
						type: 'string',
						label: '项目简介',
						field: 'projectBrief', //唯一的字段名 ***必传
						// required: true,
						// editDisabled: true,						
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
						label: '项目状态',
						field: 'projectStatus', //唯一的字段名 ***必传
						placeholder: '请选择',
						// pullJoin:false,						
						required: true,
						// multiple: true, //是否开启多选功能 开启后自动开启搜索功能
						// showSearch: false, //是否开启搜索功能 (移动端不建议开启)
						optionData: [//默认选项数据//可为function (props)=>array
						    {
						        name: '在建',
						        id: '0',
						    },
						    {
						        name: '主体已完工',
						        id: '1',
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
						type: 'select',
						label: '所属单位',
						field: 'affiliatedUnit', //唯一的字段名 ***必传
						placeholder: '请选择',
						// pullJoin:false,						
						required: true,
						// multiple: true, //是否开启多选功能 开启后自动开启搜索功能
						// showSearch: false, //是否开启搜索功能 (移动端不建议开启)
						optionData: [//默认选项数据//可为function (props)=>array
							{
								value: "4d0026M1211f18fd7cMc3cf724c68492c819757e60a238b17d4",
								text: "一公司"
							}, {
								value: "4d0026M1211f198db8Mc3cf724c68492c819757e60a238b17d4",
								text: "二公司"
							}, {
								value: "1f33ecbM1215114069dMc3cf724c68492c819757e60a238b17d4",
								text: "三公司"
							}, {
								value: "4d0026M1211f1b6529Mc3cf724c68492c819757e60a238b17d4",
								text: "四公司"
							}, {
								value: "848c58M11ddc135570Mcac6cc7252f26ad204ac504de95ccb51",
								text: "五公司"
							}, {
								value: "4d0026M1211f1bbfe4Mc3cf724c68492c819757e60a238b17d4",
								text: "六公司"
							}, {
								value: "4d0026M1211f1f4cdcMc3cf724c68492c819757e60a238b17d4",
								text: "七公司"
							}, {
								value: "4d0026M1211f214b76Mc3cf724c68492c819757e60a238b17d4",
								text: "厦门公司"
							}, {
								value: "4d0026M1211f20f203Mc3cf724c68492c819757e60a238b17d4",
								text: "桥隧公司"
							}, {
								value: "2fcb4fM11e4c91e321Mcac6cc7252f26ad204ac504de95ccb51",
								text: "海威工程"
							}, {
								value: "4d0026M1211f206d02Mc3cf724c68492c819757e60a238b17d4",
								text: "总承包经营分公司"
							}, {
								value: "15c46b5M137e357f29cM9ce72a2dc070b141941f7d5b228cc039",
								text: "北京建筑分公司"
							}, {
								value: "4d0026M1211f1e7c84Mc3cf724c68492c819757e60a238b17d4",
								text: "中交世通重工"
							}, {
								value: "22bba094M150a1febb4cM9ce72a2dc070b141941f7d5b228cc039",
								text: "海外事业部"
							}, {
								value: "suidj-189",
								text: "北京分公司"
							}, {
								value: "suidj-240",
								text: "西北分公司"
							}, {
								value: "suidj-423",
								text: "南京分公司"
							}, {
								value: "suidj-549",
								text: "西南分公司"
							}, {
								value: "suidj-584",
								text: "八公司"
							}, {
								value: "suidj-2183",
								text: "华北分公司"
							}, {
								value: "suidj-2230",
								text: "华南分公司"
							}, {
								value: "suidj-768",
								text: "盾构工程有限公司"
							}, {
								value: "suidj-800",
								text: "电气化工程有限公司"
							},{
								value: "suidj-8a8bb35a765172cc01768484656d0be1",
								text: "华中工程有限公司"
							}
						],
						optionConfig: {//下拉选项配置
							label: 'text', //默认 label
							value: 'value',// [array | string]  最终的值使用逗号连接 默认值使用valueName 默认['value']
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
					}																											
				]
			}
		}
	],
	formConfig: [
		{
			table: {
				title: '项目名称', //表头标题
				dataIndex: 'projectName', //表格里面的字段
				key: 'projectName',//表格的唯一key  
				onClick:'detail',
				align:'center' 
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
				title: '工程类别', //表头标题
				dataIndex: 'engineeringType', //表格里面的字段
				key: 'engineeringType',//表格的唯一key 
				align:'center'  
			},
			isInForm: false
		},
		{
			table: {
				title: '中标资质', //表头标题
				dataIndex: 'code3Name', //表格里面的字段
				key: 'code3Name',//表格的唯一key  
				align:'center' 
			},
			isInForm: false
		},
		{
			table: {
				// width: 110,
				title: '所属单位', //表头标题
				dataIndex: 'affiliatedUnit', //表格里面的字段
				key: 'affiliatedUnit',//表格的唯一key  
				render: (data) => {
					let _data = "未知";
					switch (data) {
						case "4d0026M1211f18fd7cMc3cf724c68492c819757e60a238b17d4":
							_data = "一公司"
							break;
						case "4d0026M1211f198db8Mc3cf724c68492c819757e60a238b17d4":
							_data = "二公司"
							break;
						case "1f33ecbM1215114069dMc3cf724c68492c819757e60a238b17d4":
							_data = "三公司"
							break;
						case "4d0026M1211f1b6529Mc3cf724c68492c819757e60a238b17d4":
							_data = "四公司"
							break;
						case "848c58M11ddc135570Mcac6cc7252f26ad204ac504de95ccb51":
							_data = "五公司"
							break;
						case "4d0026M1211f1bbfe4Mc3cf724c68492c819757e60a238b17d4":
							_data = "六公司"
							break;
						case "4d0026M1211f1f4cdcMc3cf724c68492c819757e60a238b17d4":
							_data = "七公司"
							break;
						case "4d0026M1211f214b76Mc3cf724c68492c819757e60a238b17d4":
							_data = "厦门公司"
							break;
						case "4d0026M1211f20f203Mc3cf724c68492c819757e60a238b17d4":
							_data = "桥隧公司"
							break;
						case "2fcb4fM11e4c91e321Mcac6cc7252f26ad204ac504de95ccb51":
							_data = "海威工程"
							break;
						case "4d0026M1211f206d02Mc3cf724c68492c819757e60a238b17d4":
							_data = "总承包经营分公司"
							break;
						case "15c46b5M137e357f29cM9ce72a2dc070b141941f7d5b228cc039":
							_data = "北京建筑分公司"
							break;
						case "4d0026M1211f1e7c84Mc3cf724c68492c819757e60a238b17d4":
							_data = "中交世通重工"
							break;
						case "22bba094M150a1febb4cM9ce72a2dc070b141941f7d5b228cc039":
							_data = "海外事业部"
							break;
						case "suidj-189":
							_data = "北京分公司"
							break;
						case "suidj-240":
							_data = "西北分公司"
							break;
						case "suidj-423":
							_data = "南京分公司"
							break;
						case "suidj-549":
							_data = "西南分公司"
							break;
						case "suidj-584":
							_data = "八公司"
							break;
						case "suidj-2183":
							_data = "华北分公司"
							break;
						case "suidj-2230":
							_data = "华南分公司"
							break;
						case "suidj-768":
							_data = "盾构工程有限公司"
							break;
						case "suidj-800":
							_data = "电气化工程有限公司"
							break;
						case "suidj-8a8bb35a765172cc01768484656d0be1":
							_data = "华中工程有限公司"
							break;
					}
					return _data
				}
			},
			isInForm: false
		},	
		{
			table: {
				// width: 110,
				title: '项目状态', //表头标题
				dataIndex: 'projectStatus', //表格里面的字段
				key: 'projectStatus',//表格的唯一key  
				render: (data) => {
					let _data = "未知";
					switch (data) {
						case "0":
							_data = "在建"
							break;
						case "1":
							_data = "主体已完工"
							break;
					}
					return _data
				}
			},
			isInForm: false
		},					
		{
			isInTable: false,
			isInForm: false,
			isInSearch: true,
			form: {
				type: 'select',
				label: '所属单位',
				field: 'affiliatedUnit', //唯一的字段名 ***必传
				placeholder: '请选择',
				multiple: false, //是否开启多选功能 开启后自动开启搜索功能
				showSearch: false, //是否开启搜索功能 (移动端不建议开启)
				optionData: [//默认选项数据//可为function (props)=>array
					{
						value: "4d0026M1211f18fd7cMc3cf724c68492c819757e60a238b17d4",
						text: "一公司"
					}, {
						value: "4d0026M1211f198db8Mc3cf724c68492c819757e60a238b17d4",
						text: "二公司"
					}, {
						value: "1f33ecbM1215114069dMc3cf724c68492c819757e60a238b17d4",
						text: "三公司"
					}, {
						value: "4d0026M1211f1b6529Mc3cf724c68492c819757e60a238b17d4",
						text: "四公司"
					}, {
						value: "848c58M11ddc135570Mcac6cc7252f26ad204ac504de95ccb51",
						text: "五公司"
					}, {
						value: "4d0026M1211f1bbfe4Mc3cf724c68492c819757e60a238b17d4",
						text: "六公司"
					}, {
						value: "4d0026M1211f1f4cdcMc3cf724c68492c819757e60a238b17d4",
						text: "七公司"
					}, {
						value: "4d0026M1211f214b76Mc3cf724c68492c819757e60a238b17d4",
						text: "厦门公司"
					}, {
						value: "4d0026M1211f20f203Mc3cf724c68492c819757e60a238b17d4",
						text: "桥隧公司"
					}, {
						value: "2fcb4fM11e4c91e321Mcac6cc7252f26ad204ac504de95ccb51",
						text: "海威工程"
					}, {
						value: "4d0026M1211f206d02Mc3cf724c68492c819757e60a238b17d4",
						text: "总承包经营分公司"
					}, {
						value: "15c46b5M137e357f29cM9ce72a2dc070b141941f7d5b228cc039",
						text: "北京建筑分公司"
					}, {
						value: "4d0026M1211f1e7c84Mc3cf724c68492c819757e60a238b17d4",
						text: "中交世通重工"
					}, {
						value: "22bba094M150a1febb4cM9ce72a2dc070b141941f7d5b228cc039",
						text: "海外事业部"
					}, {
						value: "suidj-189",
						text: "北京分公司"
					}, {
						value: "suidj-240",
						text: "西北分公司"
					}, {
						value: "suidj-423",
						text: "南京分公司"
					}, {
						value: "suidj-549",
						text: "西南分公司"
					}, {
						value: "suidj-584",
						text: "八公司"
					}, {
						value: "suidj-2183",
						text: "华北分公司"
					}, {
						value: "suidj-2230",
						text: "华南分公司"
					}, {
						value: "suidj-768",
						text: "盾构工程有限公司"
					}, {
						value: "suidj-800",
						text: "电气化工程有限公司"
					},{
						value: "suidj-8a8bb35a765172cc01768484656d0be1",
						text: "华中工程有限公司"
					}
				],
				optionConfig: {//下拉选项配置
					label: 'text', //默认 label
					value: 'value',// [array | string]  最终的值使用逗号连接 默认值使用valueName 默认['value']
				},
			}
		},			
		{
			isInTable: false,
			isInForm: false,
			isInSearch: true,
			form: {
				type: 'select',
				label: '项目状态',
				field: 'projectStatus', //唯一的字段名 ***必传
				placeholder: '请选择',
				multiple: false, //是否开启多选功能 开启后自动开启搜索功能
				showSearch: false, //是否开启搜索功能 (移动端不建议开启)
				optionData: [//默认选项数据//可为function (props)=>array
					{
						value: '0',
						text: '在建'
					},
					{
						value: '1',
						text: '主体已完工'
					}
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
				label: '项目录入周期',
				field: 'startTimeArr', //唯一的字段名 ***必传
				spanSearch: 12,										   
				type: 'rangeDate',
				showTime: true,
				placeholder: '请选择',
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
				title: '修改时间',
				dataIndex: 'modifyTime',
				key: 'modifyTime',
				align:'center',
				format: 'YYYY-MM-DD HH:mm:ss',
			},
			isInForm: false
		},
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
									fetch('updateZjPrSdjProjectSupervise', { ...obj._formData }, function ({ data, success, message }) {
										if (success) {
											var projectId = {
												projectId: data.projectId
											};
											obj.props.form.setFieldsValue(projectId);											
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
	]
}