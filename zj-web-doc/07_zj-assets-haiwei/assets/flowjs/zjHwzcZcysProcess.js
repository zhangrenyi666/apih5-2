var code = l.getUrlParam('code');
Lny.setCookie('code', code, 30);
var nodeId = l.getUrlParam('nodeId') || "Node1";
var workId = l.getUrlParam('workId') || "HZ2881f76458a99c016458a99ee70001";
var trackId = l.getUrlParam('trackId') || "HZ2881f76458a99c016458a99efe0002";
var title = l.getUrlParam("title") || "";
var workFormJson = {//流程模版json（升级版：根据流程工作id动态获取，当前前台写死）
	name: "验收申请",
	titleName: "zcmc",
	tabs: [
		{
			name: "基本信息",
			type: "1",
			isMain: "1",
			tbName: "zjHwzcZcys",
			tbIdName: "recordid",
			conditions: [
				{
					type: "hidden",//
					name: "recordid",//
					label: "主键id",//
					placeholder: "",
				},
				{
					type: "text",//
					name: "sszclx1Name",//
					label: "资产大类",//
					placeholder: "请输入",
					disabled: true, //流程处理时不可编辑
					immutableAdd: true
				},
				{
					type: "text",//
					name: "sszclx2Name",//
					label: "资产小类",//
					placeholder: "请输入",
					disabled: true, //流程处理时不可编辑
					immutableAdd: true
				},
				{
					type: "text",//
					name: "zcmc",//
					label: "资产名称",//
					placeholder: "请输入资产名称",
					disabled: true, //流程处理时不可编辑
					immutableAdd: true
				},
				{
					type: "text",//
					name: "ysdh",//
					label: "验收单号",//
					placeholder: "请输入验收单号",
					disabled: true, //流程处理时不可编辑
					immutableAdd: true
				},
				{
					type: "text",//
					name: "cwbh",//
					label: "财务编号",//
					placeholder: "请输入",
					//disabled: true, //流程处理时不可编辑
					immutableAdd: true
				},
				{
					type: "text",//
					name: "zcyz",//
					label: "资产原值（元）",//
					placeholder: "请输入资产原值（元）",
					immutableAdd: true
				},
				{
					type: "text",//
					name: "ggxh",//
					label: "规格型号",//
					placeholder: "请输入规格型号",
					immutableAdd: true
				},
				{
					type: "number",//
					name: "nynx",//
					label: "耐用年限",//
					placeholder: "请输入耐用年限",
					immutableAdd: true
				},
				{
					type: "select",
					name: "grfsdm",
					label: "资产来源",
					selectList: [//当类型为select时，option配置
					],
					ajax: {
						api: "getBuyMannerSelectAllList",
						valueName: "dmbh",
						textName: "dmnr"
					},
					immutableAdd: true
				},
				{
					type: "date",//
					name: "scrq",//
					label: "出厂日期",//
					dateFmt: "yyyy-MM-dd",
					defaultValue: new Date(),
					id: "scrq",
					immutableAdd: true
				},
				{
					type: "text",//
					name: "glryName",//
					label: "管理员",//
					placeholder: "请输入管理员",
					disabled: true, //流程处理时不可编辑
					immutableAdd: true
				},
				{
					type: "text",//
					name: "sydwName",//
					label: "使用单位",//
					placeholder: "请输入",
					disabled: true, //流程处理时不可编辑
					immutableAdd: true
				},
				{
					type: "select",//
					name: "sybmid",//接口字段名
					label: "使用部门",//
					selectList: [//当类型为select时，option配置
					],
					ajax: {
						api: "getDepartmentSelectAllList",
						valueName: "bmbh",
						textName: "bmmc"
					},
					immutableAdd: true
				},
				{
					type: "text",
					name: "cfdd1",
					label: "存放地点",
					placeholder: "请输入存放地点",
					immutableAdd: true
				},
				{
					type: "text",//
					name: "syr",//接口字段名
					label: "使用人",//
					placeholder: "请输入使用人",
					immutableAdd: true
				},
				{
					type: "text",//
					name: "ysr",//接口字段名
					label: "验收人员",//
					placeholder: "请输入验收人员",
					immutableAdd: true
				},
				{
					type: "date",//
					name: "yssj",//
					label: "验收日期",//
					dateFmt: "yyyy-MM-dd",
					defaultValue: new Date(),
					id: "yssj",
					immutableAdd: true
				},
				{
					type: "textarea",//
					name: "remarks",//
					label: "验收说明",//
					placeholder: "请输入验收说明",
					immutableAdd: true
				},
				{
					type: "upload",//
					name: "imageList",//
					label: "附件1",//
					btnName: "添加附件",
					projectName: "zj-assets-haiwei",
					immutableAdd: true,
					uploadUrl: 'uploadFilesByFileName',
					fileType: ["jpg", "jpeg", "png", "gif", "docx", "xls", "doc", "ppt", "xlsx", "pptx", "xlsm", "pdf"]
				},
				{
					type: "textarea",//
					name: "opinionField1",//
					label: "管理部门负责人",//
					placeholder: "",
					opinionField: true, //领导意见的特殊标识
					immutableAdd: true
				},
				{
					type: "textarea",//
					name: "opinionField2",//
					label: "使用部门",//
					placeholder: "",
					opinionField: true, //领导意见的特殊标识
					immutableAdd: true
				},
				{
					type: "textarea",//
					name: "opinionField3",//
					label: "公司主管部门",//
					placeholder: "",
					opinionField: true, //领导意见的特殊标识
					immutableAdd: true
				},
				{
					type: "textarea",//
					name: "opinionField4",//
					label: "项目财务部门负责人",//
					placeholder: "",
					opinionField: true, //领导意见的特殊标识
					immutableAdd: true
				},
				{
					type: "textarea",//
					name: "opinionField5",//
					label: "公司财务负责人",//
					placeholder: "",
					opinionField: true, //领导意见的特殊标识
					immutableAdd: true
				},
				{
					type: "textarea",//
					name: "opinionField6",//
					label: "公司财务固定资产管理人员",//
					placeholder: "",
					opinionField: true, //领导意见的特殊标识
					immutableAdd: true
				}
			]
		},
		{
			name: "操作历史",
			type: "5"
		}
	]
}

var _body = {
    title: title,
	nodeId: nodeId,
	trackId: trackId,
	workId: workId,
	apiName: "getZjHwzcZcysDetail",
	apiBody: { workId: workId }
}

l.ajax("openFlow", _body, function (_d, _m, _s, _r) {
	if (_s) {
		loadFlow(_d.flowButtons, {
			otherBody: {
				nodeId: _d.flowNode.nodeId,
				trackId: _d.flowNode.trackId,
				workId: _d.workId,
				flowVars: _d.flowVars,
				nodeVars: _d.nodeVars,
				apiName: "updateZjHwzcZcys"
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

				$.each(workFormJson.tabs, function (i, tabItem) {//第一次遍历workFormJson.tabs
					var $tabBtn = $('<span>' + tabItem.name + '</span>')//创建tab按钮$对象
					$tabBar.append($tabBtn)//向tab按钮控制条插入tab按钮
					var $tabCon = $('<div class="tabCon" id="tab' + i.toString() + '"></div>')//创建tab内容页面$对象				
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
								var $option = $(
									"<div>意见：" +
									(item["comments"] || "--") +
									"</div>"
								); //意见
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
								$histItem.append($option);
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
					} else {
						var customBtnGroup;//tab内容页面中表单的底部按钮组配置
						if (tabItem.isMain) {//如果是主表单
							var btns = flowObj.btns
							btns.push({
								name: "cancel",
								label: "取消"
							});
							// var btna = flowObj.btns
                            // if (_d.flowNode != null) {
                            //     if (_d.flowNode.nodeName == "申请人" || _d.flowNode.nodeName == "组织部文秘" || _d.flowNode.nodeName == "结束") {
                            //         btns.push({
                            //             name: "export",
                            //             label: "导出"
                            //         })
                            //     }
                            // }
							btns.push({
								name: "export",
								label: "导出"
							})
							customBtnGroup = {
								btns: btns,
								callback: function (dataName, obj) {
									switch (dataName) {
										case "export"://导出
										var params = {};
										params.workId = workId;
										window.location.href = 'http://wechat.zjyjhw.com:88/ureport/excel?_u=file:hwzcCheckNew.xml&url=' + l.domain + '&workId=' + params.workId;
										break;
										case "cancel":
											obj.close()
											break
										default:
											var body = {}
											for (var j = 0; j < workFormJson.tabs.length; j++) {//第二次遍历workFormJson.tabs，为了让return起作用所以不用$.each而采用for循环
												var tabItemj = workFormJson.tabs[j]//模版中tabs数组的遍历元素数据对象
												if (tabItemj.type === "4" || tabItemj.type === "5") {
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
														body["apiBody"] = {}
														for (var key in tabObjDatas.data) {
															body["apiBody"][key] = tabObjDatas.data[key]
														}				
														body["apiTitle"] = "getZjHwAssetsFlowTitle";
														body["opinionContent"] = tabObjDatas.data['opinionContent'];
													} else if (tabItemj.type === "2") {//如果是附件类型子表，type==="2"
														//给子表赋值-附件表
														if (!body["subTableObject"]) { body["subTableObject"] = {} }
														for (var key in tabObjDatas.data) {
															var subTableDataObject = tabObjDatas.data[key];
															body["apiBody"][key] = tabObjDatas.data[key]		
															body["subTableObject"][key] = { subTablePrimaryIdName: tabItemj.tbIdName, subTableType: tabItemj.type, subTableDataObject: subTableDataObject }
														}
													} else {//如果是普通类型子表，type==="1"，目前只有1和2
														//给子表赋值-普通表
														if (!body["subTableObject"]) { body["subTableObject"] = {} }
														for (var key in tabObjDatas.data) {
															body["apiBody"][key] = tabObjDatas.data[key]
														}
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
									if (_d.nodeVars.opinionField === 'opinionField6') {
										tabItem.conditions.push(
											{
												type: "textarea",//
												name: "opinionContent",//
												label: "您的意见",//
												defaultValue: "同意",
												placeholder: "您的意见",
											},
											{
												type: "text",//
												name: "zcbh",//
												label: "资产编号",//
												placeholder: "请输入资产编号",
												must: true
											}
										)
									} else {
										tabItem.conditions.push(
											{
												type: "textarea",//
												name: "opinionContent",//
												label: "您的意见",//
												defaultValue: "同意",
												placeholder: "您的意见",
											}
										)
									}
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
						var apiData = _d.apiData
						var apiName = _d.apiName

						if (apiName) {
							tabCons[i].setOpenData(JSON.parse(apiData))
						} else {
							//流程操作特殊代码（向各个表单中赋值）---开始
							if (tabItem.isMain) {
								tabCons[i].setOpenData(mainTableDataObject)
							} else if (tabItem.type === "2") {
								var _subTableDataObject = {}
								for (var key in subTableObject) {
									_subTableDataObject[key] = subTableObject[key].subTableDataObject
								}
								tabCons[i].setOpenData(apiData)
								tabCons[i].setOpenData(_subTableDataObject)
							} else {
								tabCons[i].setOpenData(apiData)
								tabCons[i].setOpenData(subTableObject[tabItem.tbName].subTableDataObject)
							}
						}
						//流程操作特殊代码（向各个表单中赋值）---结束
						tabCons[i].close = function () {
							parent.pager.page('remote')
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
