var code = Apih5.getUrlParam('code')
Apih5.setCookie('code',code,30)
var nodeId = l.getUrlParam("nodeId") || "Node1";
var flowId = l.getUrlParam("flowId") || "";
var workId = l.getUrlParam("workId") || "HZ2881f76458a99c016458a99ee70001";
var trackId = l.getUrlParam("trackId") || "HZ2881f76458a99c016458a99efe0002";
var title = l.getUrlParam("title") || "";
var ministerApproveId = l.getUrlParam("workId") || "";

var workFormJson = {
	//流程模版json（升级版：根据流程工作id动态获取，当前前台写死）
	name: "部长申请",
	titleName: "",
	tabs: [
		 {
			name: "基本信息",
			type: "1",//普通tab页1，附件tab页2，列表tab页面3
			isMain: "1",//是否为主表
			tbName: "sanMinister",//表名
			tbIdName: "ministerApproveId",//表主键id
			conditions: [
				{
					type: "hidden",//
					name: "ministerApproveId",//
					label: "主键id",//
					defaultValue: ministerApproveId,
					placeholder: ""
				},
				{
					type: "text",//
					name: "proName",//
					label: "项目名称",//
					placeholder: "请输入",
					immutableAdd: true
				},
				{
					type: "text",//
					name: "proLeader",//
					label: "项目负责人",//
					placeholder: "请输入",
					immutableAdd: true
				},
				{
					type: "text",//
					name: "ministerApproveTitle",//
					label: "标题",//
					placeholder: "请输入",
					immutableAdd: true
				},
				{
					type: "upload",//
					name: "fileList",//附件表名
					label: "附件",//
					btnName: "添加附件",
					projectName: "zjSeal",
					// immutableAdd: true,
					uploadUrl: 'uploadFilesByFileName',
					fileType: ["jpg", "jpeg", "png", "gif", "docx", "xls", "doc", "ppt", "xlsx", "pptx", "xlsm", "pdf"],
					immutableAdd: true
				}
			]
		},
		{
			name: "操作历史",
			type: "5"
		}
	]
};

var _body = {
	title: title,
	nodeId: nodeId,
	trackId: trackId,
	workId: workId,
	flowId: flowId,
	apiName: "getZjFlowMinisterApproveDetail",
	apiBody: { workId: workId }
};

l.ajax("openFlow", _body, function (_d, _m, _s, _r) {
	// console.log(_d);
	if (_s) {
		loadFlow(_d.flowButtons, {
			otherBody: {
				nodeId: _d.flowNode.nodeId,
				trackId: _d.flowNode.trackId,
				workId: _d.workId,
				flowId: _d.flowId,
				flowVars: _d.flowVars,
				nodeVars: _d.nodeVars,
				apiName: "updateZjFlowMinisterApproveForFlow"
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
				ministerApproveId = apiDataObj.ministerApproveId;
				//该条件满足就可以编辑
				if (_d.nodeVars && _d.nodeVars.formEditFlag === "1") {
					$.each(workFormJson.tabs, function (tIndex, tItem) {
						if (tItem.isMain === "1" || tItem.type === "2") {
							var conditions = tItem.conditions;
							for (var w = 0; w < conditions.length; w++) {
								if (!conditions[w].opinionField && !conditions[w].disabled) {
									conditions[w].immutableAdd = false;
								}
							}
							tItem.conditions = conditions;
						}
					});
				}

				$.each(workFormJson.tabs, function (i, tabItem) {
					//第一次遍历workFormJson.tabs
					var $tabBtn = $("<span>" + tabItem.name + "</span>"); //创建tab按钮$对象
					$tabBar.append($tabBtn); //向tab按钮控制条插入tab按钮
					var $tabCon = $('<div class="tabCon" id="tab' + i.toString() + '"></div>'); //创建tab内容页面$对象	
					//操作历史start
					if (tabItem.type === "5") {
						var flowHistoryList = _d.flowHistoryList;
						if (flowHistoryList && flowHistoryList.length) {
							var $timeLineContainer = $('<div style="border-left:2px solid #e8e8e8;margin-top:20px;padding:20px 0px;"></div>');
							for (var j = 0; j < flowHistoryList.length; j++) {
								var item = flowHistoryList[j];
								var $histItem = $('<div style="position:relative;padding-left:20px;margin-bottom:20px;font-size:14px;color:#666;"></div>');
								if (item.historyFlag == "2") {
									$histItem.css({ color: "#000" });
								}
								var $histItemArr = $('<div style="background:white;color:#1890ff;font-weight:800;height:15px;line-height:12px;font-size:25px;;left:-9px;top:0px;bottom:0px margin:auto;position:absolute;">○</div>');
								var $nodeName = $("<div>节点名称：" + item["nodeName"] + "</div>"); //节点名称
								var $nodePer = $("<div>审核者：" + item["realName"] + "</div>"); //审核者
								//var $option = $("<div>意见：" + (item["comments"] || "--") + "</div>"); //意见
								var $time = $("<div>时间：" + (item["actionTime"] ? l.dateFormat(new Date(item["actionTime"]), "yyyy-MM-dd HH:mm:ss") : "--") + "</div>"); //意见
								$histItem.append($histItemArr); //空格圆
								$histItem.append($nodeName);
								$histItem.append($nodePer);
								//$histItem.append($option);
								$histItem.append($time);
								$timeLineContainer.append($histItem);
							}
							tabCons[i] = $tabCon.append($timeLineContainer);
						} else {
							tabCons[i] = $tabCon.append('<div style="color:#777">暂无操作历史</div>');
						}
					}
					//操作历史end
					else if (tabItem.type === "3") {
						//列表tab
						var $con = $('<div class="page-container"></div>'); //
						var $table = $('<table id="table" class="table table-border table-bordered table-bg table-hover"></table>');
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
								
							]
						});
						$tabCon.append($con);
						tabCons[i] = $tabCon;
						//子资产列表
						var pager = pagerDom.page({
							remote: {
								//ajax请求配置
								url: l.getApiUrl("getZjFlowMinisterInfoList"),
								params: {
									ministerApproveId: ministerApproveId
								},
								success: function (result) {
									if (result.success) {
										var data = result.data;
										$.each(data, function (index, item) {
											item.rowIndex = index;
										});
										table.clear().rows.add(data).draw();
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
							customBtnGroup = {
								btns: btns,
								callback: function (dataName, obj) {
									switch (dataName) {
										case "cancel":
											obj.close();
											break;
										default:
											var body = {};
											for (var j = 0; j < workFormJson.tabs.length; j++) {
												//第二次遍历workFormJson.tabs，为了让return起作用所以不用$.each而采用for循环
												var tabItemj = workFormJson.tabs[j]; //模版中tabs数组的遍历元素数据对象
												if (tabItemj.type === "4" || tabItemj.type === "5") {
													//流程图tab
												} else if (tabItemj.type === "3") {
													//列表tab
												} else {
													var tabObjDatas = tabCons[j].getDatas(); //tab内容页面组的遍历对象获取数据对象
													if (tabObjDatas.err.length) {
														//判断是否有错误（字段不能为空、超过个数限制等）
														layer.alert(tabObjDatas.err.join("<br/>"), { icon: 0 }, function (index) {
															$tabSystem.Huitab({ index: j });
															layer.close(index);
														}
														);
														return; //直接停止for循环，且for循环之后的代码也不执行
													}
													if (tabItemj.isMain) {
														//如果是主表--给主表赋值
														body["mainTableName"] = tabItemj.tbName;
														body["mainTablePrimaryIdName"] = tabItemj.tbIdName;
														body["mainTableDataObject"] = tabObjDatas.data;
														body["apiBody"] = {};
														for (var key in tabObjDatas.data) {
															body["apiBody"][key] = tabObjDatas.data[key];
														}
														body["title"] = title;
														body["opinionContent"] = tabObjDatas.data["opinionContent"];
													} else if (tabItemj.type === "2") {
														//如果是附件类型子表，type==="2"
														if (!body["subTableObject"]) {
															body["subTableObject"] = {};
														}
														for (var key in tabObjDatas.data) {
															var subTableDataObject = tabObjDatas.data[key];
															body["apiBody"][key] = tabObjDatas.data[key];
															body["subTableObject"][key] = {
																subTablePrimaryIdName: tabItemj.tbIdName,
																subTableType: tabItemj.type,
																subTableDataObject: subTableDataObject
															};
														}
													} else {
														//如果是普通类型子表，type==="1"，目前只有1和2
														//给子表赋值-普通表
														if (!body["subTableObject"]) {
															body["subTableObject"] = {};
														}
														for (var key in tabObjDatas.data) {
															body["apiBody"][key] = tabObjDatas.data[key];
														}

														body["subTableObject"][tabItemj.tbName] = {
															subTablePrimaryIdName: tabItemj.tbIdName,
															subTableType: tabItemj.type,
															subTableDataObject: tabObjDatas.data
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
						var apiData = _d.apiData;
						var apiName = _d.apiName;
						if (apiName) {
							tabCons[i].setOpenData(JSON.parse(apiData));
						} else {
							//流程操作特殊代码（向各个表单中赋值）---开始
							if (tabItem.isMain) {
								tabCons[i].setOpenData(mainTableDataObject);
							} else if (tabItem.type === "2") {
								var _subTableDataObject = {};
								for (var key in subTableObject) {
									_subTableDataObject[key] = subTableObject[key].subTableDataObject;
								}
								tabCons[i].setOpenData(_subTableDataObject);
							} else {
								tabCons[i].setOpenData(subTableObject[tabItem.tbName].subTableDataObject);
							}
						}
						//流程操作特殊代码（向各个表单中赋值）---结束
						tabCons[i].close = function () {
							parent.pager.page("remote");
							parent.layer.close(parent.myOpenLayer);
						};
					}
				});
				$tabSystem.append($tabBar).append(tabCons).Huitab({ index: 0 });
			}
		});
	}
});
