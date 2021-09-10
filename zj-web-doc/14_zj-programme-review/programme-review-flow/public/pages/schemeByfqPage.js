var recordid = null;
var compilingSubject = null;
var projectLocation = null;
var contractNo = null;
window.schemeByfqPage = {//方案清单发起
	fetchConfig: {//表格的ajax配置
		apiName: 'getZjSchemeConfirmationList',
	},
	antd: { //同步antd table组件配置 ***必传
		rowKey: function (row) {// ***必传
			return row.recordid
		},
		size: 'small'
	},
	drawerConfig: {
		width: '1000px'
	},
	drawerConfig: {
		width: '88%'
	},
	paginationConfig: {// 同步antd的分页组件配置   
		position: 'bottom'
	},
	actionBtns: [
		{
			name: 'add',//内置add del
			icon: 'plus',//icon
			type: 'primary',//类型  默认 primary  [primary dashed danger]
			// field: "getZjWoaProjectDataList",
			field: "getZjPrYgjProjectSuperviseList",
			label: '新增一公局项目',
			willExecute: (obj) => {
				obj.btnCallbackFn.setActiveKey('0');
				recordid = null;
			},
			formBtns: [
				{
					name: 'cancel', //关闭右边抽屉
					type: 'dashed',//类型  默认 primary
					label: '取消',
					hide: function (obj) {
						var index = obj.btnCallbackFn.getActiveKey();
						if (index === "1") {
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
						if (index === "1") {
							return true;
						} else {
							return false;
						}
					},
					onClick: function (obj) { //此时里面会多一个response
						const { fetch, msg } = obj.btnCallbackFn;
						fetch('addZjSchemeConfirmationNew', { ...obj._formData }, function ({ data, success, message }) {
							if (success) {
								recordid = data.recordid;
								console.log("新增----" + recordid);
								// var recordid = {
								// 	recordid: data.recordid
								// };
								// obj.props.form.setFieldsValue(recordid);
								if (this.table) {
									this.table.refresh();
								}
								msg.success(message);
								obj.btnCallbackFn.refresh();
								obj.btnCallbackFn.setActiveKey("1");
							} else {
								msg.error(message);
							}
						})
					}
				},
			]
		},
		{
			name: 'add',//内置add del
			icon: 'plus',//icon
			type: 'primary',//类型  默认 primary  [primary dashed danger]
			label: '新增隧道局或海外项目',
			field: "getZjPrSdjProjectSuperviseList",
			willExecute: (obj) => {
				recordid = null;
				obj.btnCallbackFn.setActiveKey('0');
			},
			// Component: MerchantsAdd,
			formBtns: [
				{
					name: 'cancel', //关闭右边抽屉
					type: 'dashed',//类型  默认 primary
					label: '取消',
					hide: function (obj) {
						var index = obj.btnCallbackFn.getActiveKey();
						if (index === "1") {
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
						if (index === "1") {
							return true;
						} else {
							return false;
						}
					},
					onClick: function (obj) { //此时里面会多一个response
						const { fetch, msg } = obj.btnCallbackFn;
						fetch('addZjSchemeConfirmationSdjNew', { ...obj._formData }, function ({ data, success, message }) {
							if (success) {
								recordid = data.recordid;
								// var recordid = {
								// 	recordid: data.recordid
								// };
								// obj.props.form.setFieldsValue(recordid);
								if (this.table) {
									this.table.refresh();
								}
								msg.success(message);
								obj.btnCallbackFn.refresh();
								obj.btnCallbackFn.setActiveKey("1");
							} else {
								msg.error(message);
							}
						})
					}
				},
			]
		},
		{
			name: 'diyedit',//内置add del
			icon: 'edit',//icon
			type: 'primary',//类型  默认 primary  [primary dashed danger]
			label: '修改',
			field: 'editScheme',
			drawerDetailTitle: "修改",
			// drawerTitle:"修改",
			onClick: (obj) => {
				if (obj.selectedRows.length) {
					obj.btnCallbackFn.setActiveKey('0');
					recordid = obj.selectedRows[0].recordid;
					compilingSubject = obj.selectedRows[0].compilingSubject;
					projectLocation = obj.selectedRows[0].projectLocation;

					if (obj.selectedRows[0].projectState != "0") {
						const { fetch, msg } = obj.btnCallbackFn;
						obj.btnCallbackFn.confirm({
							title: `该项已提交评审，编辑后需要重新提交，确定编辑么？`,
							onOk: () => {
								fetch('reviseTheScheme', { ...obj.selectedRows[0] }, function ({ data, success, message }) {
									if (success) {
										var _obj = obj;
										obj.btnCallbackFn.closeDrawer(true, function () {
											obj.btnCallbackFn.fetch('ZjSchemeConfirmationDetail', { recordid: obj.selectedRows[0].recordid }, function ({ data, success, message }) {
												if (success) {
													var recordid = {
														recordid: obj.selectedRows[0].recordid
													};
													obj.props.form.setFieldsValue(recordid);
													obj.btnCallbackFn.setDrawerBtns([
														{
															name: 'cancel', //关闭右边抽屉
															type: 'dashed',//类型  默认 primary
															label: '取消',
															hide: function (obj) {
																var index = obj.btnCallbackFn.getActiveKey();
																if (index === "1") {
																	return true;
																} else {
																	return false;
																}
															},
														},
														{
															name: 'diyJB',//内置add del
															type: 'primary',//类型  默认 primary
															label: '修改',//提交数据并且关闭右边抽屉
															hide: function (obj) {
																var index = obj.btnCallbackFn.getActiveKey();
																if (index === "1") {
																	return true;
																} else {
																	return false;
																}
															},
															onClick: (obj) => {
																// const { fetch, msg } = obj.btnCallbackFn;
																obj.btnCallbackFn.setActiveKey('0');
															},
															onClick: function (obj) { //此时里面会多一个response
																const { fetch, msg } = obj.btnCallbackFn;
																fetch('updateZjSchemeConfirmation', { ...obj._formData }, function ({ data, success, message }) {
																	if (success) {
																		var recordid = {
																			recordid: data.recordid
																		};
																		obj.props.form.setFieldsValue(recordid);
																		msg.success(message)
																		obj.btnCallbackFn.refresh();
																		obj.btnCallbackFn.closeDrawer(false);
																	} else {
																		msg.error(message);
																	}
																})
															}
														},
													])
													var props = _obj;
													if (!props.clickCb) {
														props.clickCb = {
															rowInfo: {
																field: ""
															}
														}
													}

													// console.log('编辑：',obj)
													var qnnForm = obj.btnCallbackFn.getQnnForm();
													qnnForm.setValues(data)
													// console.log('-----', props);
													// data = obj.btnCallbackFn.formatData(data, obj.state.tabs[0].content.formConfig, 'set', false, props);
													// obj.props.form.setFieldsValue(data);
												}
											})

										});
									} else {
										msg.error(message);
									}
								})
							},
							// onCancel:() => {
							// 	dispatch(push(`${mainModule}HomePage/0`));
							// }
						});
					} else {

						obj.btnCallbackFn.closeDrawer(true, function () {
							obj.btnCallbackFn.fetch('ZjSchemeConfirmationDetail', { recordid: obj.selectedRows[0].recordid }, function ({ data, success, message }) {
								if (success) {
									var recordid = {
										recordid: obj.selectedRows[0].recordid
									};
									obj.props.form.setFieldsValue(recordid);
									obj.btnCallbackFn.setDrawerBtns([
										{
											name: 'cancel', //关闭右边抽屉
											type: 'dashed',//类型  默认 primary
											label: '取消',
											field: "wbbb",
											hide: function (obj) {
												var index = obj.btnCallbackFn.getActiveKey();
												if (index === "1") {
													return true;
												} else {
													return false;
												}
											},
										},
										{
											name: 'diyJB',//内置add del
											type: 'primary',//类型  默认 primary
											label: '修改',//提交数据并且关闭右边抽屉
											field: "wbbb",
											hide: function (obj) {
												var index = obj.btnCallbackFn.getActiveKey();
												if (index === "1") {
													return true;
												} else {
													return false;
												}
											},
											onClick: (obj) => {
												// const { fetch, msg } = obj.btnCallbackFn;
												obj.btnCallbackFn.setActiveKey('0');
											},
											onClick: function (obj) { //此时里面会多一个response
												const { fetch, msg } = obj.btnCallbackFn;
												fetch('updateZjSchemeConfirmation', { ...obj._formData }, function ({ data, success, message }) {
													if (success) {
														var recordid = {
															recordid: data.recordid
														};
														obj.props.form.setFieldsValue(recordid);
														msg.success(message)
														obj.btnCallbackFn.refresh();
														obj.btnCallbackFn.closeDrawer(false);
													} else {
														msg.error(message);
													}
												})
											}
										},
									])
									// var props = obj;
									// if (!props.clickCb) {
									// 	props.clickCb = {
									// 		rowInfo: {
									// 			field: ""
									// 		}
									// 	}
									// }
									//获取qnnForm实例化对象
									var qnnForm = obj.btnCallbackFn.getQnnForm();
									qnnForm.setValues(data)
									// console.log('-----', obj);
									// data = obj.btnCallbackFn.formatData(data, obj.state.tabs[0].content.formConfig, 'set', false, props);
									// obj.props.form.setFieldsValue(data);
								}
							})

						});
					}
				}else{
					obj.btnCallbackFn.msg.error('请选择一条数据!');
				}
			},

			btns: [
				{
					name: 'cancel', //关闭右边抽屉
					type: 'dashed',//类型  默认 primary
					label: '取消',
					field: "wbbb",
				},
				{
					name: 'diyJB',//内置add del
					type: 'primary',//类型  默认 primary
					label: '修改',//提交数据并且关闭右边抽屉
					field: "waaa",
					onClick: (obj) => {
						// const { fetch, msg } = obj.btnCallbackFn;
						obj.btnCallbackFn.setActiveKey('0');
					},
					onClick: function (obj) { //此时里面会多一个response
						const { fetch, msg } = obj.btnCallbackFn;
						fetch('updateZjSchemeConfirmation', { ...obj._formData }, function ({ data, success, message }) {
							if (success) {
								var recordid = {
									recordid: data.recordid
								};
								obj.props.form.setFieldsValue(recordid);
								msg.success(message)
								obj.btnCallbackFn.refresh();
								obj.btnCallbackFn.closeDrawer(false);
							} else {
								msg.error(message);
							}
						})
					}
				},
			]
		},
		{
			name: 'diydel',//内置add del
			icon: 'delete',//icon
			type: 'danger',//类型  默认 primary  [primary dashed danger]
			label: '删除',
			onClick: (obj) => {
				const { fetch, msg } = obj.btnCallbackFn;
				// console.log(obj);
				if (obj.selectedRows.length) {
					for (let i = 0; i < obj.selectedRows.length; i++) {
						if (obj.selectedRows[i].projectState != "0") {
							msg.error('选择项中存在已提交审核的！');
							return;
						}
					}
					var selectedRows = obj.selectedRows;
					fetch('batchDeleteUpdateZjSchemeConfirmation', [...selectedRows], function ({ data, success, message }) {
						if (success) {
							msg.success(message)
							obj.btnCallbackFn.refresh();
						} else {
							msg.error(message);
						}
					})
				} else {
					msg.error('未选择任何项！');
					return
				}
			},
			// fetchConfig: {//ajax配置
			// 	apiName: 'batchDeleteUpdateZjSchemeConfirmation',
			// },
		},
		{
			name: 'diyJB',//内置add del
			icon: 'highlight',//icon
			type: 'primary',//类型  默认 primary  [primary dashed danger]
			label: '提交审批',
			onClick: (obj) => {
				const { fetch, msg } = obj.btnCallbackFn;
				// console.log(obj);
				if (obj.selectedRows.length) {
					for (let i = 0; i < obj.selectedRows.length; i++) {
						if (obj.selectedRows[i].projectState != "0") {
							msg.error('已提交过评审的不可再次提交！');
							return;
						}
					}
					var selectedRows = obj.selectedRows;
					fetch('submitToExamine', [...selectedRows], function ({ data, success, message }) {
						if (success) {
							msg.success(message)
							obj.btnCallbackFn.refresh();
						} else {
							msg.error(message);
						}
					})
				} else {
					msg.error('未选择任何项！');
					return
				}
			},
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
	tabs: [
		{
			field: "form1",
			name: "qnnForm",
			title: "基本情况",
			content: {
				formItemLayout: {
					labelCol: {
						sm: { span: 3 }
					},
					wrapperCol: {
						sm: { span: 20 }
					}
				},
				formConfig: [
					{
						type: 'string',
						label: 'flag',
						field: 'flag', //唯一的字段名 ***必传
						hide: true
					},
					{
						type: 'string',
						label: '主键ID',
						field: 'recordid', //唯一的字段名 ***必传
						hide: true
					},
					{
						type: 'string',
						label: '单位标识（1为隧道局）',
						field: 'projectUnit', //唯一的字段名 ***必传
						initialValue: function (obj) {
							// console.log(obj.clickCb.rowInfo.field);
							// return "1";
							if (obj.clickCb && obj.clickCb.rowInfo && obj.clickCb.rowInfo.field === "getZjPrSdjProjectSuperviseList") {
								return "1";
							} else {
								return "";
							}

						},
						hide: true
					},
					{
						type: 'string',
						label: '单位名称',
						field: 'unitName', //唯一的字段名 ***必传
						hide: true
					},
					{
						//普通选择框 可以和其他字段关联
						type: "selectByPaging",
						label: "项目名称",
						// disabled: this.state.dStatus === 'detail',
						field: "projectName", //唯一的字段名 ***必传
						placeholder: "请选择",
						required: true,
						fetchConfig: function (obj) {
							if (obj.clickCb.rowInfo.field === "getZjPrSdjProjectSuperviseList" || obj.clickCb.rowInfo.field === "getZjPrYgjProjectSuperviseList") {
								return {
									apiName: obj.clickCb.rowInfo.field,
									searchKey: 'projectName'
								}
							} else {
								return false
							}

						},
						optionConfig: {
							//下拉选项配置
							label: "projectName",
							value: "projectName",
							linkageFields: {
								engineeringType: "engineeringType",
								code3Name: "code3Name",
								contractNo: "contractNo",
								unitName: "unitName",
								projectId: "projectId",
								projectStatus: "projectStatus",								
							}
						},
						span: 24,
						formItemLayout: {
							labelCol: {
								sm: { span: 3 }
							},
							wrapperCol: {
								sm: { span: 20 }
							}
						},
						//可以和别的输入框联动起来
						condition: [
							{//条件
								regex: {
									schemeNumber: null,
								},
								action: 'disabled',
							},
							{//条件
								regex: {
									flag: 'detail',
								},
								action: 'disabled',
							},
						]
					},
					{
						type: 'string',
						label: '项目名称',
						field: 'projectId', //唯一的字段名 ***必传
						hide: true,
						onChange: function (val) {
							console.log(val);
							projectId = val;
						}
					},
					{
						type: 'string',
						label: '合同编号',
						field: 'contractNo', //唯一的字段名 ***必传
						hide: true
					},
					{
						type: 'select',
						label: '项目所属板块',
						field: 'projectClass', //唯一的字段名 ***必传
						placeholder: '请选择',
						span: 24,
						required: true,
						disabled: true,
						optionData: [//默认选项数据//可为function (props)=>array
							{
								name: '公路市政',
								id: '0',
							},
							{
								name: '铁路轨道',
								id: '1',
							},
							{
								name: '城市房建',
								id: '2',
							},
							{
								name: '海外事业部',
								id: '3',
							},
						],
						optionConfig: {//下拉选项配置
							label: 'name', //默认 label
							value: ['id'],// [array | string]  最终的值使用逗号连接 默认值使用valueName 默认['value']
						},
						formItemLayout: {
							labelCol: {
								sm: { span: 3 }
							},
							wrapperCol: {
								sm: { span: 20 }
							}
						},
					},
					{
						type: 'select',
						label: '项目状态',
						field: 'projectStatus', //唯一的字段名 ***必传
						placeholder: '请选择',
						span: 24,
						disabled: true,
						addDisabled: true,
						editDisabled: true,
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
							value: ['id'],// [array | string]  最终的值使用逗号连接 默认值使用valueName 默认['value']
						},
						formItemLayout: {
							labelCol: {
								sm: { span: 3 }
							},
							wrapperCol: {
								sm: { span: 20 }
							}
						},
					},					
					{
						type: 'select',
						label: '编制主体',
						field: 'compilingSubject', //唯一的字段名 ***必传
						placeholder: '请选择',
						span: 24,
						required: true,
						disabled: true,
						// editDisabled: true,
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
                            }, {
								id: "suidj-3",
								name: "其他"
							}
						],
						optionConfig: {//下拉选项配置
							label: 'name', //默认 label
							value: ['id'],// [array | string]  最终的值使用逗号连接 默认值使用valueName 默认['value']
						},
						formItemLayout: {
							labelCol: {
								sm: { span: 3 }
							},
							wrapperCol: {
								sm: { span: 20 }
							}
						},
						onChange: function (val) {
							compilingSubject = val;
						}
					},
					{
						type: 'string',
						label: '工程类别',
						field: 'engineeringType', //唯一的字段名 ***必传
						// required: true,
						disabled: true,
						addDisabled: true,
						editDisabled: true,
						placeholder: '请输入',//占位符
						span: 24,
						formItemLayout: {
							labelCol: {
								sm: { span: 3 }
							},
							wrapperCol: {
								sm: { span: 20 }
							}
						},
					},
					{
						type: 'string',
						label: '中标资质',
						field: 'code3Name', //唯一的字段名 ***必传
						// required: true,
						addDisabled: true,
						disabled: true,
						editDisabled: true,
						placeholder: '请输入',//占位符
						span: 24,
						formItemLayout: {
							labelCol: {
								sm: { span: 3 }
							},
							wrapperCol: {
								sm: { span: 20 }
							}
						},
					},
					{
						type: 'string',
						label: '清单方案数量',
						field: 'detailedListNum', //唯一的字段名 ***必传
						// required: true,
						addDisabled: true,
						disabled: true,
						editDisabled: true,
						placeholder: '请输入',//占位符
						span: 24,
						formItemLayout: {
							labelCol: {
								sm: { span: 3 }
							},
							wrapperCol: {
								sm: { span: 20 }
							}
						},
					},
					{
						type: 'string',
						label: '项目位置',
						field: 'projectLocation', //唯一的字段名 ***必传
						placeholder: "格式为XX省XX市",
						required: true,
						disabled: true,
						span: 24,
						formItemLayout: {
							labelCol: {
								sm: { span: 3 }
							},
							wrapperCol: {
								sm: { span: 20 }
							}
						},
						onChange: function (val) {
							console.log(val);
							projectLocation = val;
						}
					},
					{
						type: 'string',
						label: '项目总工',
						field: 'projectChiefEng', //唯一的字段名 ***必传
						required: true,
						disabled: true,
						placeholder: '请输入',//占位符
						span: 24,
						formItemLayout: {
							labelCol: {
								sm: { span: 3 }
							},
							wrapperCol: {
								sm: { span: 20 }
							}
						},
					},
					{
						type: 'string',
						label: '项目总工联系方式',
						field: 'projectManagerTel', //唯一的字段名 ***必传
						required: true,
						disabled: true,
						placeholder: '请输入',//占位符
						span: 24,
						formItemLayout: {
							labelCol: {
								sm: { span: 3 }
							},
							wrapperCol: {
								sm: { span: 20 }
							}
						},
					},
					{
						type: 'textarea',
						label: '备注',
						field: 'remarks', //唯一的字段名 ***必传
						rows: 20, //行高 默认4                        
						placeholder: '请输入',//占位符
						span: 24,
						disabled: true,
						autosize: { minRows: 6, maxRows: 20 },
						formItemLayout: {
							labelCol: {
								sm: { span: 3 }
							},
							wrapperCol: {
								sm: { span: 20 }
							}
						},
					},
					{
						label: '分中心审查人',
						field: 'chiefEngineerNew',
						type: 'treeSelect',
						required: true,
						editDisabled: true,
						disabled: true,
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
								apiName: 'getSysDepartmentUserAllTree',
							},
							search: true,
							searchPlaceholder: '姓名、账号、电话',
							// searchApi:'getSysDepartmentUserAllTree',  //搜索时调用的api  [string]
							searchParamsKey: 'search',//搜索文字的K 默认是'searchText'   [string]
							searchOtherParams: { pageSize: 999 }//搜索时的其他参数  [object]
						}
					},
					{
						type: 'textarea',
						label: '分中心审查意见',
						field: 'chiefEngineerExamineOpinion', //唯一的字段名 ***必传
						disabled: true,
						addDisabled: true,
						editDisabled: true,
						rows: 20, //行高 默认4                        
						placeholder: '请输入',//占位符
						span: 24,
						autosize: { minRows: 6, maxRows: 20 },
						formItemLayout: {
							labelCol: {
								sm: { span: 3 }
							},
							wrapperCol: {
								sm: { span: 20 }
							}
						},
					},
					{
						type: 'textarea',
						label: '事业部技术部审核意见',
						field: 'bureauExamineOpinion', //唯一的字段名 ***必传
						disabled: true,
						addDisabled: true,
						editDisabled: true,
						rows: 20, //行高 默认4                        
						placeholder: '请输入',//占位符
						span: 24,
						autosize: { minRows: 6, maxRows: 20 },
						formItemLayout: {
							labelCol: {
								sm: { span: 3 }
							},
							wrapperCol: {
								sm: { span: 20 }
							}
						},
					}
				],
			}
		},
		{
			//content就是qnn-table配置
			field: "table",
			name: "qnnTable",
			title: "方案列表",
			disabled: (obj) => {  //  [function | boolean]
				if (obj.form.getFieldsValue().recordid != '') {
					return false;
				} else if (obj.clickCb.rowData) {
					return false;
				} else {
					return true;
				}
			},
			content: {
				tabs: [], //注意：嵌入到tabs中的qnn-table配置必须配置tabs项 否则tabs项将和上级重复
				fetchConfig: {
					apiName: 'getZjSchemeDetailedList',
					otherParams: function (obj) {
						console.log('recordid%s', recordid);
						// console.log(obj);
						return { recordid: recordid };
					}
				},
				drawerConfig: {
					width: "1000px"
					// maskClosable:false, //点击蒙层是否关闭抽屉  默认true
				},
				drawerConfig: {
					width: '70%'
				},
				antd: {
					rowKey: function (row) {
						//---row.主键id
						return row.detailedListId;
					},
					size: 'small'
				},
				paginationConfig: {
					position: "bottom"
				},
				actionBtns: [
					{
						name: "add",
						icon: "plus",
						type: "primary",
						label: "方案新增",
						field: "addFn",
						// field:"",
						hide: (obj) => {
							if (obj.props.Pstate.drawerDetailTitle === '详情') {
								return true;
							} else {
								return false;
							}
						},
						onClick: (obj) => {
							// console.log(obj.clickCb.rowInfo.field);
							// console.log(obj.props.clickCb.rowInfo.field);
							// console.log(obj.props.clickCb.rowInfo.field === "getQfProvidePageDataList");
							// if (obj.props.form.getFieldsValue().recordid) {
							if (recordid) {
							} else {
								const { fetch, msg } = obj.btnCallbackFn;
								msg.error('请先切换到基本信息完善必填信息点击确认后再新增方案！');
								obj.btnCallbackFn.closeDrawer(false);
								return;
							}
						},
						formBtns: [
							{
								name: "cancel",
								type: "dashed",
								label: "取消"
							},
							{
								name: "submit",
								type: "primary",
								label: "提交",
								fetchConfig: {
									apiName: "addZjSchemeDetailedList",
									otherParams: function (obj) {
										console.log(projectId);
										return {
											projectId: obj.clickCb._formData.projectId,
											contractNo: obj.clickCb._formData.contractNo,
											// recordid: obj.clickCb._formData.recordid
											recordid: recordid
										};
									}
								}
							}
						]
					},
					{
						name: 'edit',//内置add del
						icon: 'edit',//icon
						type: 'primary',//类型  默认 primary  [primary dashed danger]
						label: '编辑',
						hide: (obj) => {
							if (obj.props.Pstate.drawerDetailTitle === '详情') {
								return true;
							} else {
								return false;
							}
						},
						onClick: (obj) => {
							const { fetch, msg } = obj.btnCallbackFn;
							if (obj.selectedRows.length) {
								if (obj.selectedRows.length > 1) {
									msg.error('只能选择一条数据！');
									return;
								}
							} else {
								msg.error('未选择任何项！');
								return
							}
						},
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
								label: '修改',//提交数据并且关闭右边抽屉
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
									fetch('updateZjSchemeDetailedList', { ...obj._formData }, function ({ data, success, message }) {
										if (success) {
											// var recordid = {
											// 	recordid: data.recordid
											// };
											// obj.props.form.setFieldsValue(recordid);
											msg.success(message)
											obj.btnCallbackFn.refresh();
											obj.btnCallbackFn.closeDrawer(false);
										} else {
											msg.error(message);
										}
									})
								}
							},
						]
					},
					{
						name: "del",
						icon: "delete",
						type: "danger",
						label: "删除",
						hide: (obj) => {
							if (obj.props.Pstate.drawerDetailTitle === '详情') {
								return true;
							} else {
								return false;
							}
						},
						fetchConfig: {
							apiName: "batchDeleteUpdateZjSchemeDetailedList"
						}
					}
				],
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
						isInTable: false,
						form: {
							field: 'contractNoDel',
							type: 'string',
							hide: true
						},
						initialValue: function () {
							return projectId
						}
					},
					{
						isInTable: false,
						form: {
							field: 'projectIdDel',
							type: 'string',
							hide: true
						},
						initialValue: function () {
							return projectId
						}
					},
					{
						isInTable: false,
						form: {
							field: 'detailedListId',
							type: 'string',
							hide: true
						}
					},
					{
						table: {
							title: "项目位置",
							dataIndex: "projectLocation",
							key: "projectLocation",
						},
						form: {
							field: 'projectLocationDel',
							type: 'string',
							hide: true,
							initialValue: function () {
								return projectLocation
							}
						}
					},
					{
						isInTable: false,
						form: {
							type: 'select',
							label: '方案类型',
							field: 'schemeType', //唯一的字段名 ***必传
							placeholder: '请选择',
							help: "无匹配类型，可选择类似专业",							
							// required: true,
							// span: 12,
							disabled: true,
							// editDisabled: true,
							// multiple: false, //是否开启多选功能 开启后自动开启搜索功能
							// showSearch: false, //是否开启搜索功能 (移动端不建议开启)
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
								value: ['id'],// [array | string]  最终的值使用逗号连接 默认值使用valueName 默认['value']
							},
							formItemLayoutForm:{
								labelCol: {
									xs: { span: 5 },
									sm: { span: 5 }
								},
								wrapperCol: {
									xs: { span: 19 },
									sm: { span: 19 }
								}
							},
						}
					},
					{
						isInTable: false,
						form: {
							type: 'select',
							label: '方案分级',
							field: 'schemeLevel', //唯一的字段名 ***必传
							placeholder: '请选择',
							// required: true,
							// span: 12,
							disabled: true,
							editDisabled: true,
							// multiple: false, //是否开启多选功能 开启后自动开启搜索功能
							// showSearch: false, //是否开启搜索功能 (移动端不建议开启)
							optionData: [//默认选项数据//可为function (props)=>array
								{
									name: 'Ⅲ级施工方案',
									id: '0',
								},
								{
									name: 'Ⅱ级施工方案',
									id: '1',
								},
								{
									name: 'Ⅰ级施工方案',
									id: '2',
								},
								// {
								// 	name: 'Ⅳ级施工方案',
								// 	id: '3',
								// },
							],
							formItemLayoutForm:{
								labelCol: {
									xs: { span: 5 },
									sm: { span: 5 }
								},
								wrapperCol: {
									xs: { span: 19 },
									sm: { span: 19 }
								}
							},
							optionConfig: {//下拉选项配置
								label: 'name', //默认 label
								value: ['id'],// [array | string]  最终的值使用逗号连接 默认值使用valueName 默认['value']
							},
						}
					},
					{
						table: {
							title: '编制分中心', //表头标题
							dataIndex: 'compilingSubject', //表格里面的字段
							key: 'compilingSubject',//表格的唯一key  
							render: (data) => {
								let r = "未知";
								switch (data) {
									case "4d0026M1211f18fd7cMc3cf724c68492c819757e60a238b17d4":
										r = "第一技术分中心"
										break
									case "4d0026M1211f198db8Mc3cf724c68492c819757e60a238b17d4":
										r = "第二技术分中心"
										break
									case "1f33ecbM1215114069dMc3cf724c68492c819757e60a238b17d4":
										r = "第三技术分中心"
										break
									case "4d0026M1211f1b6529Mc3cf724c68492c819757e60a238b17d4"://
										r = "第四技术分中心"
										break
									case "848c58M11ddc135570Mcac6cc7252f26ad204ac504de95ccb51":
										r = "第五技术分中心"
										break
									case "4d0026M1211f1bbfe4Mc3cf724c68492c819757e60a238b17d4"://
										r = "第六技术分中心"
										break
									case "4d0026M1211f1f4cdcMc3cf724c68492c819757e60a238b17d4"://
										r = "第七技术分中心"
										break
									case "4d0026M1211f20f203Mc3cf724c68492c819757e60a238b17d4"://
										r = "桥隧技术分中心"
										break
									case "2fcb4fM11e4c91e321Mcac6cc7252f26ad204ac504de95ccb51"://
										r = "海威技术分中心"
										break
									case "4d0026M1211f206d02Mc3cf724c68492c819757e60a238b17d4"://
										r = "总承包技术分中心"
										break
									case "15c46b5M137e357f29cM9ce72a2dc070b141941f7d5b228cc039"://
										r = "建筑技术分中心"
										break
									case "4d0026M1211f1e7c84Mc3cf724c68492c819757e60a238b17d4"://
										r = "世通技术分中心"
										break
									case "22bba094M150a1febb4cM9ce72a2dc070b141941f7d5b228cc039"://
										r = "海外技术中心"
										break
									case "4d0026M1211f214b76Mc3cf724c68492c819757e60a238b17d4"://
										r = "厦门技术分中心"									
										break
									case "suidj-189"://
										r = "北京技术分中心"
										break
									case "suidj-240"://
										r = "西北技术分中心"
										break
									case "suidj-423"://
										r = "南京技术分中心"
										break
									case "suidj-549"://
										r = "西南技术分中心"
										break
									case "suidj-584"://
										r = "第八技术分中心"
										break
									case "suidj-2183"://
										r = "华北技术分中心"
										break
									case "suidj-2230"://
										r = "华南技术分中心"
										break
									case "suidj-768"://
										r = "盾构技术分中心"
										break
									case "suidj-800"://
										r = "电气化技术分中心"
										break
										case "suidj-8a8bb35a765172cc01768484656d0be1"://
										r = "华中技术分中心"
										break										
								}
								return r
							}
						},
						form: {
							type: 'select',
							label: '编制主体',
							field: 'compilingSubjectDel', //唯一的字段名 ***必传
							placeholder: '请选择',
							// span: 12,
							addDisabled: true,
							editDisabled: true,
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
							formItemLayoutForm:{
								labelCol: {
									xs: { span: 5 },
									sm: { span: 5 }
								},
								wrapperCol: {
									xs: { span: 19 },
									sm: { span: 19 }
								}
							},
							optionConfig: {//下拉选项配置
								label: 'name', //默认 label
								value: ['id'],// [array | string]  最终的值使用逗号连接 默认值使用valueName 默认['value']
							},
							initialValue: function () {
								return compilingSubject
							}
						}
					},
					// {
					// 	isInTable: false,
					// 	form: {
					// 		type: 'string',
					// 		label: '项目位置',
					// 		field: 'projectLocation', //唯一的字段名 ***必传
					// 		placeholder: '请输入',
					// 		addDisabled: true,
					// 		editDisabled: true,
					// 	}
					// },
					{
						table: {
							title: '项目所属板块', //表头标题
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
						isInForm: false
					},
					{
						table: {
							title: "中标资质",
							dataIndex: "code3Name",
							key: "code3Name",
						},
						isInForm: false
					},
					{
						table: {
							title: "分部分项工程",
							dataIndex: "partialProjects",
							key: "partialProjects",
						},
						isInForm: false
					},
					{
						table: {
							title: "方案名称",
							dataIndex: "schemeName",
							key: "schemeName",
						},
						form: {
							type: 'string',
							placeholder: '请输入',
							required: true,
							formItemLayoutForm:{
								labelCol: {
									xs: { span: 5 },
									sm: { span: 5 }
								},
								wrapperCol: {
									xs: { span: 19 },
									sm: { span: 19 }
								}
							},
						}
					},
					{
						table: {
							title: "方案编号",
							dataIndex: "schemeNumber",
							key: "schemeNumber",
						},
						form: {
							type: 'string',
							placeholder: '请输入',
							addDisabled: true,
							editDisabled: true,
							formItemLayoutForm:{
								labelCol: {
									xs: { span: 5 },
									sm: { span: 5 }
								},
								wrapperCol: {
									xs: { span: 19 },
									sm: { span: 19 }
								}
							},
						}
					},
					{
						isInTable: false,
						form: {
							type: 'string',
							label: '分部分项工程',
							field: 'partialProjects', //唯一的字段名 ***必传					
							placeholder: '请输入',
							required: true,
							formItemLayoutForm:{
								labelCol: {
									xs: { span: 5 },
									sm: { span: 5 }
								},
								wrapperCol: {
									xs: { span: 19 },
									sm: { span: 19 }
								}
							},
						}
					},
					{
						isInTable: false,
						form: {
							type: 'datetime',
							label: '方案计划实施时间',
							field: 'implementationTime', //唯一的字段名 ***必传
							// span: 12,
							placeholder: '请选择',
							// disabled: true,
							// editDisabled: true,
							is24: true,//是否是24小时制 默认true
							formItemLayoutForm:{
								labelCol: {
									xs: { span: 5 },
									sm: { span: 5 }
								},
								wrapperCol: {
									xs: { span: 19 },
									sm: { span: 19 }
								}
							},
						},
					},
					{
						table: {
							title: '方案分级', //表头标题
							dataIndex: 'schemeLevel', //表格里面的字段
							key: 'schemeLevel',//表格的唯一key  
							render: (data) => {
								let r = "未知";
								switch (data) {
									case "0":
										r = "Ⅲ级施工方案"
										break
									case "1":
										r = "Ⅱ级施工方案"
										break
									case "2"://
										r = "Ⅰ级施工方案"
										break
									// case "3":
									// 	r = "Ⅳ级施工方案"
									// 	break
								}
								return r
							}
						},
						isInForm: false
					},
					{
						table: {
							title: '方案类型', //表头标题
							dataIndex: 'schemeType', //表格里面的字段
							key: 'schemeType',//表格的唯一key  
							render: (data) => {
								let r = "未知";
								switch (data) {
									case "0":
										r = "房建"
										break
									case "1":
										r = "盾构"
										break
									case "2"://
										r = "路基地质岩土"
										break
									case "3":
										r = "路面"
										break
									case "4":
										r = "隧道"
										break
									case "5":
										r = "钢结构"
										break
									case "6":
										r = "其他"
										break
									case "7":
										r = "桥梁"
										break																			
									case "8":
										r = "试验"
										break										
									case "9":
										r = "测量"
										break																			
									case "10":
										r = "机电一体化"
										break										
									case "11":
										r = "设计咨询"
										break										
								}
								return r
							}
						},
						isInForm: false
					},
					{
						table: {
							title: "等级划分说明/施工重难点",
							dataIndex: "hierarchyDescription",
							key: "hierarchyDescription",
						},
						form: {
							label:"等级划分说明/施工重难点(限300字)",
							type: 'textarea',
							placeholder: '请输入',
							maxLength:150,
							autosize: { minRows: 6, maxRows: 20 },
							formItemLayoutForm:{
								labelCol: {
									xs: { span: 5 },
									sm: { span: 5 }
								},
								wrapperCol: {
									xs: { span: 19 },
									sm: { span: 19 }
								}
							},
							required: true,
						}
					},
					{
						table: {
							title: '方案计划实施时间',
							dataIndex: 'implementationTime',
							key: 'implementationTime',
							format: 'YYYY-MM-DD HH:mm:ss',
						},
						isInForm: false
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
							title: '方案审批通过时间',
							dataIndex: 'listAdoptTime',
							key: 'listAdoptTime',
							format: 'YYYY-MM-DD HH:mm:ss',
						},
						isInForm: false
					},
					{
						table: {
							title: "方案状态",
							dataIndex: "reviewState",
							key: "reviewState",
						},
						isInForm: false
					},					
					{
						table: {
							title: "项目联系人",
							dataIndex: "projectChiefEng",
							key: "projectChiefEng",
						},
						isInForm: false
					},
					{
						table: {
							title: "方案得分",
							dataIndex: "programmingScore",
							key: "programmingScore",
						},
						isInForm: false
					},
					{
						isInTable: false,
						form: {
							type: 'textarea',
							label: '备注（限200字）',
							field: 'delRemarks', //唯一的字段名 ***必传
							// required: true,
							rows: 20, //行高 默认4                        
							placeholder: '请输入',//占位符
							// span: 24,
							autosize: { minRows: 6, maxRows: 20 },
							formItemLayoutForm:{
								labelCol: {
									xs: { span: 5 },
									sm: { span: 5 }
								},
								wrapperCol: {
									xs: { span: 19 },
									sm: { span: 19 }
								}
							},
						},
					},
				]
			}
		}
	],
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
				width: 300,
				title: '项目名称', //表头标题
				dataIndex: 'projectName', //表格里面的字段
				key: 'projectName',//表格的唯一key  
				onClick: 'detail',
				willExecute: (obj) => {
					recordid = obj.rowData.recordid;
					obj.btnCallbackFn.setActiveKey('0');
					obj.btnCallbackFn.fetch('ZjSchemeConfirmationDetail', { recordid: obj.rowData.recordid }, function ({ data, success, message }) {
						if (success) {
							data.flag = 'detail'
							var props = obj;
							if (!props.clickCb) {
								props.clickCb = {
									rowInfo: {
										field: ""
									}
								}
							}

							var qnnForm = obj.btnCallbackFn.getQnnForm();
							qnnForm.setValues(data);
						}
					})
				},
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
			isInForm: false,
			isInSearch: true,
			form: {
				type: 'select',
				label: '项目类别',
				field: 'projectClass', //唯一的字段名 ***必传
				placeholder: '请选择',
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
				title: '状态', //表头标题
				dataIndex: 'projectState', //表格里面的字段
				key: 'projectState',//表格的唯一key  
				render: (data) => {
					let r = "未知";
					switch (data) {
						case "0":
							r = "未审批"
							break
						case "1":
							r = "审批中"
							break
						case "2"://
							r = "通过"
							break
						case "3":
							r = "未通过"
							break
					}
					return r
				}
			},
			isInForm: false,
			isInSearch: true,
			form: {
				type: 'select',
				label: '状态',
				field: 'projectState', //唯一的字段名 ***必传
				placeholder: '请选择',
				multiple: false, //是否开启多选功能 开启后自动开启搜索功能
				showSearch: false, //是否开启搜索功能 (移动端不建议开启)
				optionData: [//默认选项数据//可为function (props)=>array
					{
						value: '0',
						text: '未审批'
					},
					{
						value: '1',
						text: '审批中'
					},
					{
						value: '2',
						text: '通过'
					},
					{
						value: '3',
						text: '未通过'
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
				title: '技术等级',
				dataIndex: 'schemeLevel',
				key: 'schemeLevel',
			},
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
				label: "上报时间",			
				field: 'creatTimeArr', //唯一的字段名 ***必传	
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
				title: '提交备注', //表头标题
				dataIndex: 'remarks', //表格里面的字段
				key: 'remarks',//表格的唯一key  
			},
			isInForm: false
		},
		{
			table: {
				title: '项目类别', //表头标题
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
					}
					return r
				}
			},
			isInForm: false
		},
		{
			table: {
				title: '总工', //表头标题
				dataIndex: 'chiefEngineerName', //表格里面的字段
				key: 'chiefEngineerName',//表格的唯一key  
			},
			isInForm: false
		},
		{
			table: {
				title: '总工意见', //表头标题
				dataIndex: 'chiefEngineerExamineOpinion', //表格里面的字段
				key: 'chiefEngineerExamineOpinion',//表格的唯一key  
			},
			isInForm: false
		},
		{
			table: {
				title: '更新时间',
				dataIndex: 'modifyTime',
				key: 'modifyTime',
				format: 'YYYY-MM-DD HH:mm:ss',
			},
			isInForm: false
		},
	]
}