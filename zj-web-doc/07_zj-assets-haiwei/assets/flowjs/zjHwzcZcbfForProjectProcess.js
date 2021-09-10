var code = l.getUrlParam('code');
Lny.setCookie('code', code, 30);
var nodeId = l.getUrlParam('nodeId') || "Node1";
var workId = l.getUrlParam('workId') || "HZ2881f76458a99c016458a99ee70001";
var trackId = l.getUrlParam('trackId') || "HZ2881f76458a99c016458a99efe0002";
var workFormJson = {//流程模版json（升级版：根据流程工作id动态获取，当前前台写死）
	name: "项目报废申请",
	titleName: "zcmc",
	tabs: [
		{
			name: "基本信息",
			type: "1",
			isMain: "1",
			tbName: "zjHwzcZcgl",
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
					immutableAdd: true
				},
				{
					type: "text",//
					name: "zcbh",//
					label: "资产编号",//
					placeholder: "请输入资产编号",
					immutableAdd: true
				},
				{
					type: "text",//
					name: "cwbh",//
					label: "财务编号",//
					placeholder: "请输入财务编号",
					immutableAdd: true
				},
				{
					type: "number",//
					name: "quantity",//
					label: "数量",//		    	                	
					defaultValue: "1",
					immutableAdd: true
				},
				{
					type: "select",//
					name: "manufacturerId",//
					label: "生产厂家",
					placeholder: "请输入资产品牌",
					selectList: [//当类型为select时，option配置
					],
					ajax: {
						api: "getManufacturerSelectAllList",
						valueName: "recordid",
						textName: "manufacturerName",
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
					name: "zcyz",//
					label: "资产原值（元）",//
					placeholder: "请输入资产原值（元）",
					immutableAdd: true
				},
				{
					type: "number",//
					name: "ljzj",//
					label: "累计折旧（元）",//
					placeholder: "请输入累计折旧（元）",
					immutableAdd: true
				},
				{
					type: "number",//
					name: "zxjz",//
					label: "资产净值（元）",//
					placeholder: "请输入资产净值（元）",
					immutableAdd: true
				},
				{
					type: "number",//
					name: "synx",//
					label: "规定使用年限（年）",//
					placeholder: "请输入规定使用年限（年）",
					immutableAdd: true
				},
				{
					type: "select",
					name: "bffsdm",
					label: "报废方式",
					ajax: {//如果需要接口获取数据需要添加该属性
						api: "getScrapTypeSelectAllList",//api名称
						valueName: "dmbh",//设置value对应的接口字段名称
						textName: "dmnr"//设置text对应的接口字段名称
					},
					immutableAdd: true
				},
				{
					type: "text",//
					name: "clxs",//
					label: "处理形式",//
					placeholder: "请输入处理形式",
					immutableAdd: true
				},
				{
					type: "number",//
					name: "ysynx",//
					label: "已使用年限（年）",//
					placeholder: "请输入已使用年限（年）",
					immutableAdd: true
				},
				{
					type: "textarea",//
					name: "zcxzbfyy",//
					label: "资产现状及报废原因",//
					placeholder: "请输入资产现状及报废原因",
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
					name: "opinionField5",//
					label: "项目主管部门审批意见",//
					placeholder: "",
					immutableAdd: true,
				},
				{
					type: "textarea",//
					name: "opinionField6",//
					label: "项目领导审批意见",//
					placeholder: "",
					immutableAdd: true,
				},
				{
					type: "textarea",//
					name: "opinionField1",//
					label: "公司主管部门审批意见",//
					placeholder: "",
					immutableAdd: true,
				},
				{
					type: "textarea",//
					name: "opinionField2",//
					label: "公司财务负责人审批意见",//
					placeholder: "",
					immutableAdd: true,
				},
				{
					type: "textarea",//
					name: "opinionField3",//
					label: "公司主管领导审批意见",//
					placeholder: "",
					immutableAdd: true,
				},
				{
					type: "textarea",//
					name: "opinionField4",//
					label: "总经理审批意见",//
					placeholder: "",
					immutableAdd: true,
				},
				{
					type: "textarea",//
					name: "opinionField7",//
					label: "项目经理审批意见",//
					placeholder: "",
					immutableAdd: true,
				},
				{
					type: "textarea",//
					name: "opinionField8",//
					label: "项目财务审批意见",//
					placeholder: "",
					immutableAdd: true,
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
	nodeId: nodeId,
	trackId: trackId,
	workId: workId,
	apiName: "getZjHwzcZcbfDetails",
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
				apiName: "updateZjHwzcZcbf"
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
											window.location.href = 'http://wechat.zjyjhw.com:88/ureport/excel?_u=file:zjHwzcZcbf.xml&url=' + l.domain + '&workId=' + params.workId;
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
									tabItem.conditions.push({
										type: "textarea",//
										name: "opinionContent",//
										label: "您的意见",//
										defaultValue: "同意",
										placeholder: "您的意见",
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