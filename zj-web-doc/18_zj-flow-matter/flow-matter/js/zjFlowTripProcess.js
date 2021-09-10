var nodeId = l.getUrlParam("nodeId") || "Node1";
var flowId = l.getUrlParam("flowId") || "";
var workId = l.getUrlParam("workId") || "HZ2881f76458a99c016458a99ee70001";
var trackId = l.getUrlParam("trackId") || "HZ2881f76458a99c016458a99efe0002";
var title = l.getUrlParam("title") || "";
var code = l.getUrlParam('code');
Lny.setCookie('code', code, 30);
var tripApplyId = l.getUrlParam("id") || "";

var workFormJson = {
	//流程模版json（升级版：根据流程工作id动态获取，当前前台写死）
	name: "出差申请",
	titleName: "tripTitle",
	tabs: [
		{
			name: "基本信息",
			type: "1",//普通tab页1，附件tab页2，列表tab页面3
			isMain: "1",//是否为主表
			tbName: "zjFlowTripApply",//表名
			tbIdName: "tripApplyId",//表主键id
			conditions: [
				{
					type: "hidden",//
					name: "tripApplyId",//
					label: "主键id",//
					placeholder: ""
				},
				{
					type: "select",
					name: "documentType",
					label: "公文类型",
					selectList: [//当类型为select时，option配置   0：公章、1:手签章、2:人名章
						{
							value: "0",
							text: "请示批复"
						},
						{
							value: "1",
							text: "通知通报"
						},
						{
							value: "2",
							text: "报告函"
						}
					],
					immutableAdd: true
				},
				{
					type: "date", //
					name: "applyDate", //
					label: "申请日期", //
					dateFmt: "yyyy-MM-dd",
					defaultValue: new Date(),
					id: "applyDate",
					immutableAdd: true
				},
				{
					type: "text",//
					name: "objectName",//
					label: "部门",//
					placeholder: "部门",
					immutableAdd: true
				},
				{
					type: "text",//
					name: "name",//
					label: "姓名",//
					placeholder: "姓名",
					immutableAdd: true
				},
				{
					type: "text",//
					name: "position",//
					label: "职别",//
					placeholder: "职别",
					immutableAdd: true
				},
				{
					type: "date", //
					name: "startTime", //
					label: "开始时间", //
					dateFmt: "yyyy-MM-dd",
					defaultValue: new Date(),
					id: "startTime",
					immutableAdd: true
				},
				{
					type: "date", //
					name: "endTime", //
					label: "结束时间", //
					dateFmt: "yyyy-MM-dd",
					defaultValue: new Date(),
					id: "endTime",
					immutableAdd: true
				},
				{
					type: "text",//
					name: "tripPlace",//
					label: "出差地点",//
					placeholder: "出差地点",
					immutableAdd: true
				},
				{
					type: "select",
					name: "vehicle",
					label: "交通工具",
					selectList: [//当类型为select时，option配置   0：公章、1:手签章、2:人名章
						{
							value: "0",
							text: "汽车"
						},
						{
							value: "1",
							text: "火车"
						},
						{
							value: "2",
							text: "飞机"
						},
						{
							value: "3",
							text: "其他"
						}
					],
					immutableAdd: true
				},
				{
					type: "text",//
					name: "traveller",//
					label: "出差人",//
					placeholder: "出差人",
					immutableAdd: true
				},
				{
					type: "textarea", //
					name: "tripReason", //
					label: "出差事由", //
					placeholder: "",
					immutableAdd: true
				},
				{
					type: "textarea", //
					name: "flyReason", //
					label: "乘坐飞机原因", //
					placeholder: "",
					immutableAdd: true
				},
				{
					type: "textarea", //
					name: "opinionField2", //
					label: "部门领导意见", //
					placeholder: "",
					immutableAdd: true
				},
				{
					type: "textarea", //
					name: "opinionField3", //
					label: "公司分管领导意见", //
					placeholder: "",
					immutableAdd: true
				},
				{
					type: "textarea", //
					name: "opinionField4", //
					label: "公司总经理意见", //
					placeholder: "",
					immutableAdd: true
				},
				{
					type: "textarea", //
					name: "opinionField5", //
					label: "公司董事长意见", //
					placeholder: "",
					immutableAdd: true
				},
				{
					type: "textarea",//
					label: "注",//
					defaultValue: "01.北京市内出差：部门、中心、事业部领导以下员工报部门、中心、事业部负责人批准，部门、中心、事业部领导（含）以上员工报公司报分管领导审批；<br/>02.北京市以外地区出差：部门、中心领导以下员工出差3天以下（含3天）由所在部门、中心负责人批准；3天以上7天以下（含7天）由分管领导批准；7天以上，党群部门由公司党委书记批准，其他部门、中心由公司总经理批准。部门、中心领导（含）以上员工出差7天以下（含7天）由分管领导批准；7天以上，党群部门由公司党委书记批准，其他部门、中心由公司总经理批准； 事业部公司管干部正副职负责人出差，7天以下（含7天）由公司分管领导批准；7天以上，党群部门由公司党委书记批准，其他部门由公司总经理批准。事业部其他人员出差，1天以下（含1天）由所在部门处室负责人批准；2天以上7天以下（含7天）由事业部部门负责人批准；7天以上，党群部门由事业部党委书记批准，其他部门由事业部总经理批准。 加班：部门、中心、事业部领导以下员工报部门、中心、事业部负责人批准，部门、中心、事业部领导（含）以上员工报分管领导批准",
					immutableAdd: true
				}]
		},
		{
			//详情弹窗
			name: "出差详情",
			type: "3", //table是表格
			tbName: "",
			tbIdName: "fileId"
		},
		{
			name: "操作历史",
			type: "5"
		}
	]
};

//add by lny on 717
var _body = {
	title: title,
	nodeId: nodeId,
	trackId: trackId,
	workId: workId,
	flowId: flowId,
	apiName: "getZjFlowTripApplyDetail",
	apiBody: { workId: workId }
};

l.ajax("openFlow", _body, function (_d, _m, _s, _r) {
	if (_s) {
		loadFlow(_d.flowButtons, {
			otherBody: {
				nodeId: _d.flowNode.nodeId,
				trackId: _d.flowNode.trackId,
				workId: _d.workId,
				flowId: _d.flowId,
				flowVars: _d.flowVars,
				nodeVars: _d.nodeVars,
				apiName: "updateZjFlowTripApply"
			},
			submitFn: function () {
				parent.pager.page("remote");
				parent.layer.close(parent.myOpenLayer);
			},
			callback: function (flowObj) {
				var $tabSystem = $("#tab-system"); //模版顶级jq对象
				var $tabBar = $('<div class="tabBar cl"></div>'); //tab按钮控制条
				var tabCons = []; //tab内容页面组
				var mainTableDataObject = _d.mainTableDataObject; //主表数据对象
				var subTableObject = _d.subTableObject; //子表数据对象数组
				var flowWebUrl = _d.flowWebUrl || ""; //子表数据对象数组
				var apiDataObj = JSON.parse(_d.apiData);
				tripApplyId = apiDataObj.tripApplyId;
				$.each(workFormJson.tabs, function (i, tabItem) {
					//第一次遍历workFormJson.tabs
					var $tabBtn = $("<span>" + tabItem.name + "</span>"); //创建tab按钮$对象
					$tabBar.append($tabBtn); //向tab按钮控制条插入tab按钮
					var $tabCon = $(
						'<div class="tabCon" id="tab' +
						i.toString() +
						'"></div>'
					); //创建tab内容页面$对象
					//操作历史start
					if (tabItem.type === "5") {
						var flowHistoryList = _d.flowHistoryList;
						if (flowHistoryList && flowHistoryList.length) {
							var $timeLineContainer = $(
								'<div style="border-left:2px solid #e8e8e8;margin-top:20px;padding:20px 0px;"></div>'
							);
							for (var j = 0; j < flowHistoryList.length; j++) {
								var item = flowHistoryList[j];
								var $histItem = $(
									'<div style="position:relative;padding-left:20px;margin-bottom:20px;font-size:14px;color:#666;"></div>'
								);
								if (item.historyFlag == "2") {
									$histItem.css({
										color: "#000"
									});
								}
								var $histItemArr = $(
									'<div style="background:white;color:#1890ff;font-weight:800;height:15px;line-height:12px;font-size:25px;;left:-9px;top:0px;bottom:0px;margin:auto;position:absolute;">○</div>'
								);
								var $nodeName = $(
									"<div>节点名称：" +
									item["nodeName"] +
									"</div>"
								); //节点名称
								var $nodePer = $(
									"<div>审核者：" +
									item["realName"] +
									"</div>"
								); //审核者
							
								var $time = $(
									"<div>时间：" +
									(item["actionTime"]
										? l.dateFormat(
											new Date(item["actionTime"]),
											"yyyy-MM-dd HH:mm:ss"
										)
										: "--") +
									"</div>"
								); //意见
								$histItem.append($histItemArr); //空格圆
								$histItem.append($nodeName);
								$histItem.append($nodePer);
								
								$histItem.append($time);
								$timeLineContainer.append($histItem);
							}
							tabCons[i] = $tabCon.append($timeLineContainer);
						} else {
							tabCons[i] = $tabCon.append(
								'<div style="color:#777">暂无操作历史</div>'
							);
						}
					}
					//操作历史end
					else if (tabItem.type === "3") {
						//列表tab
						//列表tab
						var $con = $('<div class="page-container"></div>'); //
						var $table = $(
							'<table id="table" class="table table-border table-bordered table-bg table-hover"></table>'
						);
						var $cl = $('<div class="cl"></div>');
						pagerDom = $('<div id="pager" class="m-pagination f-r"></div>');
						var $btnCon = $('<div class="f-l mt-10"></div>');

						$cl.append($btnCon);
						$cl.append(pagerDom);
						$con.append($table);
						$con.append($cl);

						table = $table.DataTable({
							info: false, //是否显示数据信息
							paging: false, //是否开启自带分页
							ordering: false, //是否开启DataTables的高度自适应
							processing: false, //是否显示‘进度’提示
							searching: false, //是否开启自带搜索
							autoWidth: false, //是否监听宽度变化
							columnDefs: [//表格列配置
								{
									targets: [0],
									data: "rowIndex",
									width: 13,
									title: '<input type="checkbox">',
									render: function (data) {
										return (
											'<input type="checkbox" name="checkbox" data-rowIndex="' +
											data +
											'" >'
										);
									}
								},
								{
									targets: [1],
									data: "rowIndex",
									title: "No.",
									width: 25,
									render: function (data) {
										return data + 1;
									}
								},
								{
									targets: [2], //第几列
									data: "startTime", //接口对应字段
									title: "开始时间", //表头名
									defaultContent: "-", //默认显示
									render: function (data) {
										return data ? l.dateFormat(new Date(data), "yyyy-MM-dd") : "-";
									}
								},
								{
									targets: [3], //第几列
									data: "endTime", //接口对应字段
									title: "结束时间", //表头名
									defaultContent: "-", //默认显示
									render: function (data) {
										return data ? l.dateFormat(new Date(data), "yyyy-MM-dd") : "-";
									}
								},
								{
									targets: [4], //第几列
									data: "startPlace", //接口对应字段
									title: "开始地点", //表头名
									defaultContent: "-" //默认显示
								},
								{
									targets: [5], //第几列
									data: "endPlace", //接口对应字段
									title: "结束地点", //表头名
									defaultContent: "-" //默认显示
								}
							]
						});

						$tabCon.append($con);
						tabCons[i] = $tabCon;

						//检查项
						var pager = pagerDom.page({
							remote: {
								//ajax请求配置
								url: l.getApiUrl("getZjFlowTripApplyDetailList"),
								params: {
									tripApplyId: tripApplyId
								},
								success: function (result) {
									if (result.success) {
										var data = result.data;
										$.each(data, function (index, item) {
											item.rowIndex = index;
										});
										table
											.clear()
											.rows.add(data)
											.draw();
									} else {
										layer.alert(result.message, { icon: 0 }, function (index) {
											layer.close(index);
										});
									}
								}
							}
						});











					} else {
						var customBtnGroup; //tab内容页面中表单的底部按钮组配置
						if (tabItem.isMain) {
							//如果是主表单
							var btns = flowObj.btns;
							btns.push({
								name: "cancel",
								label: "取消"
							});



							var btna = flowObj.btns
							if (_d.flowNode != null) {
								if ((_d.flowNode.nodeName == "申请人") || (_d.flowNode.nodeName == "结束")) {
									btns.push({
										name: "export",
										label: "导出"
									})
								}
							}



							customBtnGroup = {
								btns: btns,
								callback: function (dataName, obj) {
									switch (dataName) {


										case "export"://导出
											var params = {};
											params.workId = workId;
											window.location.href = 'http://weixin.fheb.cn:91/ureport/pdf?_u=file:zjFlowTripApply.xml&url=' + l.domain + '&workId=' + params.workId;
											break;


										case "cancel":
											obj.close();
											break;
										default:
											var body = {};
											for (
												var j = 0;
												j < workFormJson.tabs.length;
												j++
											) {
												//第二次遍历workFormJson.tabs，为了让return起作用所以不用$.each而采用for循环
												var tabItemj =
													workFormJson.tabs[j]; //模版中tabs数组的遍历元素数据对象
												if (
													tabItemj.type === "4" ||
													tabItemj.type === "5"
												) {
													//流程图tab
												} else if (
													tabItemj.type === "3"
												) {
													//列表tab
												} else {
													var tabObjDatas = tabCons[
														j
													].getDatas(); //tab内容页面组的遍历对象获取数据对象
													if (
														tabObjDatas.err.length
													) {
														//判断是否有错误（字段不能为空、超过个数限制等）
														layer.alert(
															tabObjDatas.err.join(
																"<br/>"
															),
															{ icon: 0 },
															function (index) {
																$tabSystem.Huitab(
																	{
																		index: j
																	}
																);
																layer.close(
																	index
																);
															}
														);
														return; //直接停止for循环，且for循环之后的代码也不执行
													}
													if (tabItemj.isMain) {
														//如果是主表
														//给主表赋值
														body["mainTableName"] =
															tabItemj.tbName;
														body[
															"mainTablePrimaryIdName"
														] = tabItemj.tbIdName;
														body[
															"mainTableDataObject"
														] = tabObjDatas.data;

														//add by lny on 717
														body["apiBody"] = {};
														for (var key in tabObjDatas.data) {
															body["apiBody"][
																key
															] =
																tabObjDatas.data[
																key
																];
														}
														//add by lny on 717

														// body["title"] = tabObjDatas.data[workFormJson.titleName];
														body["title"] = title;
														body["opinionContent"] =
															tabObjDatas.data[
															"opinionContent"
															];
													} else if (
														tabItemj.type === "2"
													) {
														//如果是附件类型子表，type==="2"
														//给子表赋值-附件表
														if (
															!body[
															"subTableObject"
															]
														) {
															body[
																"subTableObject"
															] = {};
														}
														for (var key in tabObjDatas.data) {
															var subTableDataObject =
																tabObjDatas
																	.data[key];

															//add by lny on 717
															body["apiBody"][
																key
															] =
																tabObjDatas.data[
																key
																];
															//add by lny on 717

															body[
																"subTableObject"
															][key] = {
																	subTablePrimaryIdName:
																		tabItemj.tbIdName,
																	subTableType:
																		tabItemj.type,
																	subTableDataObject: subTableDataObject
																};
														}
													} else {
														//如果是普通类型子表，type==="1"，目前只有1和2
														//给子表赋值-普通表
														if (
															!body[
															"subTableObject"
															]
														) {
															body[
																"subTableObject"
															] = {};
														}

														//add by lny on 717
														for (var key in tabObjDatas.data) {
															body["apiBody"][
																key
															] =
																tabObjDatas.data[
																key
																];
														}
														//add by lny on 717

														body["subTableObject"][
															tabItemj.tbName
														] = {
																subTablePrimaryIdName:
																	tabItemj.tbIdName,
																subTableType:
																	tabItemj.type,
																subTableDataObject:
																	tabObjDatas.data
															};
													}
												}
											}
											//流程操作特殊代码---开始
											if (false) {
												layer.confirm("确定打印？", { icon: 0 }, function (index) {
													//流程发起请求
													l.ajax(buttonUrl, body, function (data, message, success) {
														if (success) {
															window.location.href = data;
															// window.open(data)
														}
													}
													);
													layer.close(index);
												}
												);
											} else {
												flowObj.flowSelectOpen(dataName, body);
											}
										//流程操作特殊代码---结束
									}
								}
							};

							if (_d.nodeVars != null) {
								//如果需要显示意见框
								if (_d.nodeVars.opinionShowFlag === "1") {
									tabItem.conditions.push({
										type: "textarea", //
										name: "opinionContent", //
										label: "您的意见", //
										defaultValue: "同意",
										must: true,
										placeholder: "您的意见"
									});
								}
							}
						} else {
							customBtnGroup = {
								btns: [],
								callback: function (dataName, obj) { }
							};
						}
						tabCons[i] = $tabCon.detailLayer({
							customBtnGroup: customBtnGroup,
							conditions: tabItem.conditions
						});
						//流程操作特殊代码（向各个表单中赋值）---开始

						//add by lny on 717
						var apiData = _d.apiData;
						var apiName = _d.apiName;
						if (apiName) {
							tabCons[i].setOpenData(JSON.parse(apiData));
						} else {
							//add by lny on 717
							if (tabItem.isMain) {
								tabCons[i].setOpenData(mainTableDataObject);
							} else if (tabItem.type === "2") {
								var _subTableDataObject = {};
								for (var key in subTableObject) {
									_subTableDataObject[key] =
										subTableObject[key].subTableDataObject;
								}
								tabCons[i].setOpenData(_subTableDataObject);
							} else {
								tabCons[i].setOpenData(
									subTableObject[tabItem.tbName]
										.subTableDataObject
								);
							}
						}
						//流程操作特殊代码（向各个表单中赋值）---结束
						tabCons[i].close = function () {
							parent.layer.close(parent.myOpenLayer);
						};
					}
				});
				$tabSystem
					.append($tabBar)
					.append(tabCons)
					.Huitab({
						index: 0
					});
			}
		});
	}
});
