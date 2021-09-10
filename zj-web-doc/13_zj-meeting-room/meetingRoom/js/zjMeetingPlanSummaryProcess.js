var nodeId = l.getUrlParam('nodeId') || "Node1";
var flowId = l.getUrlParam('flowId') || "";
var workId = l.getUrlParam('workId') || "HZ2881f76458a99c016458a99ee70001";
var trackId = l.getUrlParam('trackId') || "HZ2881f76458a99c016458a99efe0002";
var title = l.getUrlParam('title') || "";
var nodeName = l.getUrlParam('nodeName') || "";
var state = l.getUrlParam('state') || "";
var code = l.getUrlParam('code');
Lny.setCookie('code', code, 30);

var workFormJson = {//流程模版json（升级版：根据流程工作id动态获取，当前前台写死）
	name: "用印申请",
	titleName: "sealApplyName",
	tabs: [
		{
			name: "基本信息",
			type: "1",
			isMain: "1",
			tbName: "zjMeetingReservationsFlow",
			tbIdName: "reservationsFlowId",
			conditions: [
				//1
				{
					type: "hidden",//
					name: "typeAssets",//
					label: "申请类型代码",// 1为公司  2为项目
					defaultValue: "1",
					placeholder: "",
					immutableAdd: true
				},
				//2
				{
					type: "qnnTable",//
					name: "qnnTableTest",//
					// label: "万能表格测试",//   
					createFormCb: function (tableId, pagerId) {
						//表格创建完毕  正常请求数据
						// console.log("表格创建完毕，可以请求数据");
						table1 = $("#" + tableId).DataTable({
							info: false, //是否显示数据信息
							paging: false, //是否开启自带分页
							ordering: false, //是否开启DataTables的高度自适应
							processing: false, //是否显示‘进度’提示
							searching: false, //是否开启自带搜索
							autoWidth: false, //是否监听宽度变化
							columnDefs: [
								//表格列配置

								{
									targets: [0],
									data: "rowIndex",
									title: "No.",
									width: 25,
									render: function (data) {
										return data + 1;
									}
								},
								{
									targets: [1], //第几列
									data: "fallInYear", //接口对应字段
									title: "年度", //表头名
									defaultContent: "-", //默认显示
								},
								{
									targets: [2], //第几列
									data: "meetingTimeStr", //接口对应字段
									title: "会议时间", //表头名
									defaultContent: "-", //默认显示
								},
								{
									targets: [3], //第几列
									data: "meetingNameStr", //接口对应字段
									title: "会议名称", //表头名
									defaultContent: "-", //默认显示
								},
								{
									targets: [4], //第几列
									data: "meetingForm", //接口对应字段
									title: "会议形式", //表头名
									defaultContent: "-", //默认显示
								},
								{
									targets: [5], //第几列
									data: "joinObject", //接口对应字段
									title: "参加对象", //表头名
									defaultContent: "-", //默认显示
								},
								{
									targets: [6], //第几列
									data: "joinNumber", //接口对应字段
									title: "人数", //表头名
									defaultContent: "-", //默认显示
								},
								{
									targets: [7], //第几列
									data: "sponsoringDept", //接口对应字段
									title: "主办部门", //表头名
									defaultContent: "-", //默认显示
								},
								{
									targets: [8], //第几列
									data: "coOperationDept", //接口对应字段
									class: "text-overflow",
									title: "协办部门", //表头名
									defaultContent: "-", //默认显示
								},
								{
									targets: [9], //第几列
									data: "budgetaryCost", //接口对应字段                                              
									title: "预算费用（万元）", //表头名
									defaultContent: "-", //默认显示
								},
								{
									targets: [10], //第几列
									data: "meetingRemakes", //接口对应字段                                              
									title: "备注", //表头名
									defaultContent: "-", //默认显示
								}
							]
						});
						pager1 = $("#" + pagerId).page({
							remote: {
								//ajax请求配置
								url: l.getApiUrl("getZjMeetingPlanFallInListFallInYear"),
								params: {
									fallInYear: fallInYear,
									approvalState: "2"
								},
								success: function (result) {
									if (result.success) {
										var data = result.data;
										$.each(data, function (index, item) {
											item.rowIndex = index;
										});
										table1
											.clear()
											.rows.add(data)
											.draw();
									} else {
										layer.alert(
											result.message,
											{
												icon: 0
											},
											function (index) {
												layer.close(index);
											}
										);
									}
								}
							}
						});
					}
				},
				{
					type: "textarea",//
					name: "opinionField1",//
					label: "公司分管领导",//
					// placeholder: "请输入申请单位负责人",
					immutableAdd: true
				},
				{
					type: "textarea",//
					name: "opinionField2",//
					label: "办公室主任",//
					// placeholder: "请输入公司办公室",
					immutableAdd: true
				},
				{
					type: "textarea",//
					name: "opinionField3",//
					label: "公司主管领导",//
					// placeholder: "请输入局办公室",
					immutableAdd: true
				}
			]
		},
		{
			name: "流程进度图",
			type: "4"
		}
	]
}
//add by apih5 on 717
var _body = {
	title: title,
	nodeId: nodeId,
	trackId: trackId,
	workId: workId,
	flowId: flowId,
	apiName: "getZjMeetingPlanFlowDetailByWorkId",
	apiBody: { workId: workId }
}

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
				apiName: "updateZjMeetingPlanFlow"
			},
			submitFn: function () {
				parent.pager.page('remote')
				parent.layer.close(parent.myOpenLayer)
			},
			callback: function (flowObj) {
				var $tabSystem = $("#tab-system")//模版顶级jq对象
				var $tabBar = $('<div class="tabBar cl"></div>');//tab按钮控制条
				var tabCons = []//tab内容页面组
				var mainTableDataObject = _d.mainTableDataObject//主表数据对象
				var subTableObject = _d.subTableObject//子表数据对象数组
				var flowWebUrl = _d.flowWebUrl || ""//子表数据对象数组
				$.each(workFormJson.tabs, function (i, tabItem) {//第一次遍历workFormJson.tabs
					var $tabBtn = $('<span>' + tabItem.name + '</span>')//创建tab按钮$对象
					$tabBar.append($tabBtn)//向tab按钮控制条插入tab按钮
					var $tabCon = $('<div class="tabCon" id="tab' + i.toString() + '"></div>')//创建tab内容页面$对象
					if (tabItem.type === "4") {
						//流程图tab
						if (flowWebUrl) {
							var $iframe = $('<iframe width="100%" height="600" src="' + flowWebUrl + '"/>')
							tabCons[i] = $tabCon.append($iframe)
						} else {
							tabCons[i] = $tabCon.append($('<div style="color:#666;text-align:center;line-height:50px">未发现流程图</div>'))
						}

					} else if (tabItem.type === "3") {
						//列表tab
					} else {
						var customBtnGroup;//tab内容页面中表单的底部按钮组配置
						if (tabItem.isMain) {//如果是主表单
							var btns = flowObj.btns
							btns.push({
								name: "cancel",
								label: "取消"
							})
							customBtnGroup = {
								btns: btns,
								callback: function (dataName, obj) {
									switch (dataName) {
										case "export"://导出
											var params = {};
											params.workId = workId;
											//zjMeetingReservationFlowExportExcel
											l.ajax('zjMeetingRoomChangeToPdf', params, function (res) {
												layer.alert("导出成功！", { icon: 0 }, function (index) {
													layer.close(index);
													window.location.href = res;
												})
											})
											break;
										case "cancel":
											obj.close()
											break
										default:
											var body = {}
											for (var j = 0; j < workFormJson.tabs.length; j++) {//第二次遍历workFormJson.tabs，为了让return起作用所以不用$.each而采用for循环
												var tabItemj = workFormJson.tabs[j]//模版中tabs数组的遍历元素数据对象
												if (tabItemj.type === "4") {
													//流程图tab
												} else if (tabItemj.type === "3") {
													//列表tab
												} else {
													var tabObjDatas = tabCons[j].getDatas();//tab内容页面组的遍历对象获取数据对象
													if (tabObjDatas.err.length) {//判断是否有错误（字段不能为空、超过个数限制等）
														layer.alert(tabObjDatas.err.join("<br/>"), { icon: 0, }, function (index) {
															$tabSystem.Huitab({
																index: j
															});
															layer.close(index);
														});
														return//直接停止for循环，且for循环之后的代码也不执行
													}
													if (tabItemj.isMain) {//如果是主表
														//给主表赋值
														body["mainTableName"] = tabItemj.tbName
														body["mainTablePrimaryIdName"] = tabItemj.tbIdName
														body["mainTableDataObject"] = tabObjDatas.data;
														//add by apih5 on 717
														body["apiBody"] = {}
														for (var key in tabObjDatas.data) {
															body["apiBody"][key] = tabObjDatas.data[key]
														}
														//add by apih5 on 717
														// body["title"] = tabObjDatas.data[workFormJson.titleName];
														body["title"] = title;
														body["opinionContent"] = tabObjDatas.data['opinionContent'];
													} else if (tabItemj.type === "2") {//如果是附件类型子表，type==="2"
														//给子表赋值-附件表
														if (!body["subTableObject"]) { body["subTableObject"] = {} }
														for (var key in tabObjDatas.data) {
															var subTableDataObject = tabObjDatas.data[key];
															//add by apih5 on 717
															body["apiBody"][key] = tabObjDatas.data[key]
															//add by apih5 on 717
															body["subTableObject"][key] = { subTablePrimaryIdName: tabItemj.tbIdName, subTableType: tabItemj.type, subTableDataObject: subTableDataObject }
														}
													} else {//如果是普通类型子表，type==="1"，目前只有1和2
														//给子表赋值-普通表
														if (!body["subTableObject"]) { body["subTableObject"] = {} }
														//add by apih5 on 717
														for (var key in tabObjDatas.data) {
															body["apiBody"][key] = tabObjDatas.data[key]
														}
														//add by apih5 on 717
														body["subTableObject"][tabItemj.tbName] = { subTablePrimaryIdName: tabItemj.tbIdName, subTableType: tabItemj.type, subTableDataObject: tabObjDatas.data }
													}
												}
											}
											//流程操作特殊代码---开始
											if (false) {
												layer.confirm("确定打印？", { icon: 0, }, function (index) {//流程发起请求
													l.ajax(buttonUrl, body, function (data, message, success) {
														if (success) {
															window.location.href = data
															// window.open(data)
														}
													})
													layer.close(index);
												});
											} else {
												flowObj.flowSelectOpen(dataName, body)
											}
										//流程操作特殊代码---结束
									}
								}
							}
							if (_d.nodeVars != null) {
								//如果需要显示意见框 
								if (_d.nodeVars.opinionShowFlag === '1') {
									tabItem.conditions.push({
										type: "textarea",//
										name: "opinionContent",//
										label: "您的意见",//
										defaultValue: "同意",
										must: true,
										placeholder: "您的意见"
									})
								}
							}
						} else {
							customBtnGroup = {
								btns: [],
								callback: function (dataName, obj) {
								}
							}
						}
						tabCons[i] = $tabCon.detailLayer({ customBtnGroup: customBtnGroup, conditions: tabItem.conditions })
						//流程操作特殊代码（向各个表单中赋值）---开始
						//add by apih5 on 717
						var apiData = _d.apiData
						var apiName = _d.apiName
						if (apiName) {
							tabCons[i].setOpenData(JSON.parse(apiData))
						} else {
							//add by apih5 on 717
							if (tabItem.isMain) {
								tabCons[i].setOpenData(mainTableDataObject)
							} else if (tabItem.type === "2") {
								var _subTableDataObject = {}
								for (var key in subTableObject) {
									_subTableDataObject[key] = subTableObject[key].subTableDataObject
								}
								tabCons[i].setOpenData(_subTableDataObject)
							} else {
								tabCons[i].setOpenData(subTableObject[tabItem.tbName].subTableDataObject)
							}
						}
						//流程操作特殊代码（向各个表单中赋值）---结束
						tabCons[i].close = function () {
							parent.layer.close(parent.myOpenLayer)
						}
					}
				})
				$tabSystem.append($tabBar).append(tabCons).Huitab({
					index: 0
				});

			}
		})
	}
})
