window.zjPrReviewStatePageaaa = {//珠海大屏项目进度数据
	fetchConfig: {//表格的ajax配置
		apiName: 'getSchemeReviewListList',
	},
	antd: { //同步antd table组件配置 ***必传
		rowKey: function (row) {// ***必传
			return row.launchId
		},
		size: 'small'
	},
	drawerConfig: {
		width: '80%'
	},
	paginationConfig: {// 同步antd的分页组件配置   
		position: 'bottom'
	},
	tionBtnsPosition: "bottom",
	actionBtns: [
		{
			name: 'export',//内置add del
			icon: 'export',//icon
			type: 'primary',//类型  默认 primary  [primary dashed danger]
			label: '导出',
			onClick: (obj) => {
				userId = obj.props.loginAndLogoutInfo.loginInfo.userInfo.userKey;
				if (obj.props.form.getFieldsValue().search__flowCreatTimeArr && obj.props.form.getFieldsValue().search__flowCreatTimeArr.length) {
					flowCreatTimeStart = new Date(obj.props.form.getFieldsValue().search__flowCreatTimeArr[0]._d).valueOf();
					flowCreatTimeEnd = new Date(obj.props.form.getFieldsValue().search__flowCreatTimeArr[1]._d).valueOf();
					// flowCreatTimeArr = obj.props.form.getFieldsValue().search__flowCreatTimeArr;
				} else {
					flowCreatTimeStart = null;
					flowCreatTimeEnd = null;
				}
				if (obj.props.form.getFieldsValue().search__implementationTimeArr && obj.props.form.getFieldsValue().search__implementationTimeArr.length) {
					startTime = new Date(obj.props.form.getFieldsValue().search__implementationTimeArr[0]._d).valueOf();
					endTime = new Date(obj.props.form.getFieldsValue().search__implementationTimeArr[1]._d).valueOf();					
					// implementationTimeArr = obj.props.form.getFieldsValue().search__implementationTimeArr;
				} else {
					startTime = null;
					endTime = null;
				}
				if (obj.props.form.getFieldsValue().search__modifyTimeArr && obj.props.form.getFieldsValue().search__modifyTimeArr.length) {
					adoptStartTime = new Date(obj.props.form.getFieldsValue().search__modifyTimeArr[0]._d).valueOf();					
					adoptEndTime = new Date(obj.props.form.getFieldsValue().search__modifyTimeArr[1]._d).valueOf();
					// modifyTimeArr = obj.props.form.getFieldsValue().search__modifyTimeArr;
				} else {
					adoptStartTime = null;					
					adoptEndTime = null;
				}

				if (obj.props.form.getFieldsValue().search__programmingScore) {
					programmingScore = obj.props.form.getFieldsValue().search__programmingScore;
				} else {
					programmingScore = "";
				}
				if (obj.props.form.getFieldsValue().search__programmingScoreTo) {
					programmingScoreTo = obj.props.form.getFieldsValue().search__programmingScoreTo;
				} else {
					programmingScoreTo = "";
				}

				if (obj.props.form.getFieldsValue().search__compilingSubject) {
					compilingSubject = obj.props.form.getFieldsValue().search__compilingSubject;
				} else {
					compilingSubject = "";
				}
				if (obj.props.form.getFieldsValue().search__reviewState) {
					reviewState = obj.props.form.getFieldsValue().search__reviewState;
				} else {
					reviewState = "";
				}
				if (obj.props.form.getFieldsValue().search__schemeType) {
					schemeType = obj.props.form.getFieldsValue().search__schemeType;
				} else {
					schemeType = "";
				}
				if (obj.props.form.getFieldsValue().search__projectClass) {
					projectClass = obj.props.form.getFieldsValue().search__projectClass;
				} else {
					projectClass = "";
				}
				if (obj.props.form.getFieldsValue().search__projectName) {
					projectName = obj.props.form.getFieldsValue().search__projectName;
				} else {
					projectName = "";
				}
				if (obj.props.form.getFieldsValue().search__schemeLevel) {
					schemeLevel = obj.props.form.getFieldsValue().search__schemeLevel;
				} else {
					schemeLevel = "";
				}
				if (obj.props.form.getFieldsValue().search__schemeNumber) {
					schemeNumber = obj.props.form.getFieldsValue().search__schemeNumber;
				} else {
					schemeNumber = "";
				}
					window.location.href = "http://weixin.fheb.cn:91/ureport/excel?_u=file:schemeReviewList.ureport.xml&url=http://weixin.fheb.cn:98/apifangan/&userKey=" + userId
						+ "&compilingSubject=" + compilingSubject + "&reviewState=" + reviewState + "&schemeType=" + schemeType + "&projectClass=" + projectClass + "&projectName=" + projectName
						+ "&schemeLevel=" + schemeLevel + "&schemeNumber=" + schemeNumber + "&flowCreatTimeStart=" + flowCreatTimeStart+ "&flowCreatTimeEnd=" + flowCreatTimeEnd 
						+ "&startTime=" + startTime + "&endTime=" + endTime+ "&adoptStartTime=" + adoptStartTime+ "&adoptEndTime=" + adoptEndTime+ "&programmingScore=" + programmingScore
						+ "&programmingScoreTo=" + programmingScoreTo;
			}
		},
	],
	//每个表单项的布局 -- 搜索区域
	formItemLayoutSearch: {
		//默认数据
		labelCol: {
			sm: { span: 6 }
		},
		wrapperCol: {
			sm: { span: 18 }
		}
	},
	formConfig: [
		{
			isInForm: false,
			table: {
				width: 25,
				align: 'center',
				title: 'No.', //表头标题
				dataIndex: 'no', //表格里面的字段
				key: 'no',//表格的唯一key    
				render: (data, rows, index) => {
					return index + 1;
				}
			},
		},
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
		//检索条件
		{
			isInTable: false,
			isInSearch: true,
			form: {
				type: 'select',
				label: '项目所属板块',
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
				title: '项目位置', //表头标题
				dataIndex: 'projectLocation', //表格里面的字段
				key: 'projectLocation',//表格的唯一key  
			},
			isInForm: false
		},
		{
			table: {
				title: '编制主体', //表头标题
				dataIndex: 'compilingSubject', //表格里面的字段
				key: 'compilingSubject',//表格的唯一key  
				render: (data) => {
					let _data = "未知";
					switch (data) {
						case "4d0026M1211f18fd7cMc3cf724c68492c819757e60a238b17d4":
							_data = "第一技术分中心"
							break;
						case "4d0026M1211f198db8Mc3cf724c68492c819757e60a238b17d4":
							_data = "第二技术分中心"
							break;
						case "1f33ecbM1215114069dMc3cf724c68492c819757e60a238b17d4":
							_data = "第三技术分中心"
							break;
						case "4d0026M1211f1b6529Mc3cf724c68492c819757e60a238b17d4":
							_data = "第四技术分中心"
							break;
						case "848c58M11ddc135570Mcac6cc7252f26ad204ac504de95ccb51":
							_data = "第五技术分中心"
							break;
						case "4d0026M1211f1bbfe4Mc3cf724c68492c819757e60a238b17d4":
							_data = "第六技术分中心"
							break;
						case "4d0026M1211f1f4cdcMc3cf724c68492c819757e60a238b17d4":
							_data = "第七技术分中心"
							break;
						case "4d0026M1211f214b76Mc3cf724c68492c819757e60a238b17d4":
							_data = "厦门技术分中心"
							break;
						case "4d0026M1211f20f203Mc3cf724c68492c819757e60a238b17d4":
							_data = "桥隧技术分中心"
							break;
						case "2fcb4fM11e4c91e321Mcac6cc7252f26ad204ac504de95ccb51":
							_data = "海威技术分中心"
							break;
						case "4d0026M1211f206d02Mc3cf724c68492c819757e60a238b17d4":
							_data = "总承包技术分中心"
							break;
						case "15c46b5M137e357f29cM9ce72a2dc070b141941f7d5b228cc039":
							_data = "建筑技术分中心"
							break;
						case "4d0026M1211f1e7c84Mc3cf724c68492c819757e60a238b17d4":
							_data = "世通技术分中心"
							break;
						case "22bba094M150a1febb4cM9ce72a2dc070b141941f7d5b228cc039":
							_data = "海外技术中心"
							break;
						case "suidj-189":
							_data = "北京技术分中心"
							break;
						case "suidj-240":
							_data = "西北技术分中心"
							break;
						case "suidj-423":
							_data = "南京技术分中心"
							break;
						case "suidj-549":
							_data = "西南技术分中心"
							break;
						case "suidj-584":
							_data = "第八技术分中心"
							break;
						case "suidj-2183":
							_data = "华北技术分中心"
							break;
						case "suidj-2230":
							_data = "华南技术分中心"
							break;
						case "suidj-768":
							_data = "盾构技术分中心"
							break;
						case "suidj-800":
							_data = "电气化技术分中心"
							break;
						case "suidj-8a8bb35a765172cc01768484656d0be1":
							_data = "华中技术分中心"
							break;
						default:
							_data = "未知"
							break;
					}
					return _data
				}
			},
			isInForm: false
		},
		{
			table: {
				title: '项目所属板块', //表头标题
				dataIndex: 'projectClass', //表格里面的字段
				key: 'projectClass',//表格的唯一key  
				render: (data) => {
					// console.log(data)	
					let _data = "未知";
					switch (data) {
						case "0":
							_data = "公路市政"
							break
						case "1":
							_data = "铁路轨道"
							break
						case "2":
							_data = "城市房建"
							break
						case "3":
							_data = "海外事业部"
							break
					}
					return _data
				}
			},
			isInForm: false
		},
		{
			table: {
				title: '中标资质', //表头标题
				dataIndex: 'code3Name', //表格里面的字段
				key: 'code3Name',//表格的唯一key  
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
				title: '分部分项工程', //表头标题
				dataIndex: 'partialProjects', //表格里面的字段
				key: 'partialProjects',//表格的唯一key  
			},
			isInForm: false
		},
		{
			table: {
				title: '方案名称', //表头标题
				dataIndex: 'schemeName', //表格里面的字段
				key: 'schemeName',//表格的唯一key  
			},
			isInForm: false,
			// isInSearch: true,
			// form: {
			// 	type: 'string',
			// 	placeholder: "请输入"
			// }			
		},
		{
			table: {
				title: '方案编号', //表头标题
				dataIndex: 'schemeNumber', //表格里面的字段
				key: 'schemeNumber',//表格的唯一key  
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
				title: '方案分级', //表头标题
				dataIndex: 'schemeLevel', //表格里面的字段
				key: 'schemeLevel',//表格的唯一key  
				render: (data) => {
					let _data = "未知";
					switch (data) {
						case "0":
							_data = "Ⅲ级施工方案"
							break;
						case "1":
							_data = "Ⅱ级施工方案"
							break;
						case "2":
							_data = "Ⅰ级施工方案"
							break;
						case "3":
							_data = "Ⅳ级施工方案"
							break;
						default:
							_data = "未知"
							break;
					}
					return _data
				}
			},
			isInForm: false
		},
		{
			isInTable: false,
			isInSearch: true,
			form: {
				type: 'select',
				label: '方案分级',
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
						text: 'Ⅰ级施工方案'
					},
					{
						value: '3',
						text: 'Ⅳ级施工方案'
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
				title: '方案类型', //表头标题
				dataIndex: 'schemeType', //表格里面的字段
				key: 'schemeType',//表格的唯一key  
				render: (data) => {
					let _data = "未知";
					switch (data) {
						case "0":
							_data = "房建"
							break
						case "1":
							_data = "盾构"
							break
						case "2"://
							_data = "路基地质岩土"
							break
						case "3":
							_data = "路面"
							break
						case "4":
							_data = "隧道"
							break
						case "5":
							_data = "钢结构"
							break
						case "6":
							_data = "其他"
							break
						case "7":
							_data = "桥梁"
							break																			
						case "8":
							_data = "试验"
							break										
						case "9":
							_data = "测量"
							break																			
						case "10":
							_data = "机电一体化"
							break										
						case "11":
							_data = "设计咨询"
							break	
					}
					return _data
				}
			},
			isInForm: false
		},
		{
			isInTable: false,
			isInSearch: true,
			form: {
				type: 'select',
				label: '方案类型',
				field: 'schemeType', //唯一的字段名 ***必传
				placeholder: '请选择',
				required: true,
				multiple: false, //是否开启多选功能 开启后自动开启搜索功能
				showSearch: false, //是否开启搜索功能 (移动端不建议开启)
				optionData: [//默认选项数据//可为function (props)=>array
					{
						text: '房建',
						value: '0',
					},
					{
						text: '盾构',
						value: '1',
					},
					{
						text: '路基地质岩土',
						value: '2',
					},
					{
						text: '路面',
						value: '3',
					},
					{
						text: '隧道',
						value: '4',
					},
					{
						text: '钢结构',
						value: '5',
					},
					{
						text: '其他',
						value: '6',
					},
					{
						text: '桥梁',
						value: '7',
					},
					{
						text: '试验',
						value: '8',
					},
					{
						text: '测量',
						value: '9',
					},
					{
						text: '机电一体化',
						value: '10',
					},
					{
						text: '设计咨询',
						value: '11',
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
				type: 'select',
				label: '编制主体',
				field: 'compilingSubject', //唯一的字段名 ***必传
				placeholder: '请选择',
				required: true,
				multiple: false, //是否开启多选功能 开启后自动开启搜索功能
				showSearch: false, //是否开启搜索功能 (移动端不建议开启)
				optionData: [//默认选项数据//可为function (props)=>array
					{
						value: "4d0026M1211f18fd7cMc3cf724c68492c819757e60a238b17d4",
						text: "第一技术分中心"
					}, {
						value: "4d0026M1211f198db8Mc3cf724c68492c819757e60a238b17d4",
						text: "第二技术分中心"
					}, {
						value: "1f33ecbM1215114069dMc3cf724c68492c819757e60a238b17d4",
						text: "第三技术分中心"
					}, {
						value: "4d0026M1211f1b6529Mc3cf724c68492c819757e60a238b17d4",
						text: "第四技术分中心"
					}, {
						value: "848c58M11ddc135570Mcac6cc7252f26ad204ac504de95ccb51",
						text: "第五技术分中心"
					}, {
						value: "4d0026M1211f1bbfe4Mc3cf724c68492c819757e60a238b17d4",
						text: "第六技术分中心"
					}, {
						value: "4d0026M1211f1f4cdcMc3cf724c68492c819757e60a238b17d4",
						text: "第七技术分中心"
					}, {
						value: "4d0026M1211f214b76Mc3cf724c68492c819757e60a238b17d4",
						text: "厦门技术分中心"
					}, {
						value: "4d0026M1211f20f203Mc3cf724c68492c819757e60a238b17d4",
						text: "桥隧技术分中心"
					}, {
						value: "2fcb4fM11e4c91e321Mcac6cc7252f26ad204ac504de95ccb51",
						text: "海威技术分中心"
					}, {
						value: "4d0026M1211f206d02Mc3cf724c68492c819757e60a238b17d4",
						text: "总承包技术分中心"
					}, {
						value: "15c46b5M137e357f29cM9ce72a2dc070b141941f7d5b228cc039",
						text: "建筑技术分中心"
					}, {
						value: "4d0026M1211f1e7c84Mc3cf724c68492c819757e60a238b17d4",
						text: "世通技术分中心"
					}, {
						value: "22bba094M150a1febb4cM9ce72a2dc070b141941f7d5b228cc039",
						text: "海外技术中心"
					}, {
						value: "suidj-189",
						text: "北京技术分中心"
					}, {
						value: "suidj-240",
						text: "西北技术分中心"
					}, {
						value: "suidj-423",
						text: "南京技术分中心"
					}, {
						value: "suidj-549",
						text: "西南技术分中心"
					}, {
						value: "suidj-584",
						text: "第八技术分中心"
					}, {
						value: "suidj-2183",
						text: "华北技术分中心"
					}, {
						value: "suidj-2230",
						text: "华南技术分中心"
					}, {
						value: "suidj-768",
						text: "盾构技术分中心"
					}, {
						value: "suidj-800",
						text: "电气化技术分中心"
					},{
						value: "suidj-8a8bb35a765172cc01768484656d0be1",
						text: "华中技术分中心"
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
				title: '等级划分说明/施工重难点', //表头标题
				dataIndex: 'hierarchyDescription', //表格里面的字段
				key: 'hierarchyDescription',//表格的唯一key  
			},
			isInForm: false
		},
		{
			table: {
				title: '方案计划实施时间',
				dataIndex: 'implementationTime',
				key: 'implementationTime',
				format: 'YYYY-MM-DD HH:mm:ss',
			},
			isInForm: false,
			isInSearch: false,
		},
		{
			isInTable: false,
			isInSearch: true,
			form: {
				type: 'select',
				label: '评审状态',
				field: 'reviewState', //唯一的字段名 ***必传
				placeholder: '请选择',
				required: true,
				multiple: false, //是否开启多选功能 开启后自动开启搜索功能
				showSearch: false, //是否开启搜索功能 (移动端不建议开启)
				optionData: [//默认选项数据//可为function (props)=>array
					{
						value: '',
						text: '全部'
					},
					{
						value: '0',
						text: '未发起'
					},
					{
						value: '2',
						text: '评审中'
					},
					{
						value: '3',
						text: '评审未通过'
					},
					{
						value: '4',
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
				title: '清单通过时间',
				dataIndex: 'toExamineTime',
				key: 'toExamineTime',
				format: 'YYYY-MM-DD HH:mm:ss',
			},
			isInForm: false
		},
		{
			table: {
				title: '方案发起时间',
				dataIndex: 'flowCreatTime',
				key: 'flowCreatTime',
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
				title: '项目联系人', //表头标题
				dataIndex: 'projectChiefEng', //表格里面的字段
				key: 'projectChiefEng',//表格的唯一key  
			},
			isInForm: false
		},
		{
			table: {
				title: '方案得分', //表头标题
				dataIndex: 'programmingScore', //表格里面的字段
				key: 'programmingScore',//表格的唯一key  
			},
			isInForm: false
		},	
		{
			isInTable: false, 
			isInSearch: true,
			form: {
				label: "方案发起时间",			
				field: 'flowCreatTimeArr', //唯一的字段名 ***必传	
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
			isInTable: false,
			isInSearch: true,
			form: {
				label: '评审通过时间',
				field: 'modifyTimeArr', //唯一的字段名 ***必传
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
			isInTable: false,
			isInSearch: true,
			form: {
				type: "number",
				label: "方案得分",
				field: "programmingScore",
				placeholder: "请输入...",
				spanSearch: 6,
				formItemLayoutSearch: {
					labelCol: { sm: { span: 6 } },
					wrapperCol: { sm: { span: 18 } }
				},
				colWrapperStyle: {
					paddingRight: '3px',
				},
			}
		},
		{
			isInTable: false,
			isInSearch: true,
			form: {
				type: "number",
				label: "~~",
				field: "programmingScoreTo",
				placeholder: "请输入...",
				spanSearch: 6,
				formItemLayoutSearch: {
					colon: false,
					labelCol: { sm: { span: 2 } },
					wrapperCol: { sm: { span: 22 } }
				},
				colWrapperStyle: {
					paddingLeft: '0px',
				},
			}
		},			
		{
			isInTable: false,
			isInSearch: true,
			form: {
				label: "方案计划实施时间",
				type: 'rangeDate',
				field: 'implementationTimeArr', //唯一的字段名 ***必传	
				spanSearch: 12,							   
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
	],
	searchFormColNum: 4,
}