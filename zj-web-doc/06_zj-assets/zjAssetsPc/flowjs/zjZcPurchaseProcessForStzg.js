var nodeId = l.getUrlParam("nodeId") || "Node1";
var flowId = l.getUrlParam("flowId") || "";
var workId = l.getUrlParam("workId") || "HZ2881f76458a99c016458a99ee70001";
var trackId = l.getUrlParam("trackId") || "HZ2881f76458a99c016458a99efe0002";
var title = l.getUrlParam("title") || "";
var code = l.getUrlParam('code');
Lny.setCookie('code', code, 30);
var recordid = l.getUrlParam("id") || "";

var workFormJson = {
	//流程模版json（升级版：根据流程工作id动态获取，当前前台写死）
	name: "世通重工资产申请",
	titleName: "zcmc",
	tabs: [
		{
			name: "基本信息",
			type: "1",
			isMain: "1",
			tbName: "zjZcManage",
			tbIdName: "recordid",
			conditions: [
				{
					type: "hidden",//
					name: "recordid",//
					label: "主键id",//
					placeholder: "",
					immutableAdd: true
				},
				{
					type: "select",
					name: "sszclx1",
					label: "资产大类",
					ajax: {
						api: "getAssetsTypeTreeAllList",
						child: "sszclx2",
						valueName: "recordid",
						textName: "assetTypeIdAndName",
						childrenName: "currentList",
					},
					immutableAdd: true
				},
				{
					type: "select",
					name: "sszclx2",
					label: "资产小类",
					ajax: {
						parent: "sszclx1",
						valueName: "recordid",
						textName: "assetTypeIdAndName",
					},
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
					name: "zcyz",//
					label: "资产原值（元）",//
					placeholder: "请输入资产原值（元）",
					immutableAdd: true
				},
				{
					type: "select",
					name: "czlId",
					label: "残值率（%）",
					selectList: [//当类型为select时，option配置
					],
					ajax: {
						api: "getCzlSelectAllList",
						valueName: "dmbh",
						textName: "dmnr"
					},
					immutableAdd: true
				},
				{
					type: "select",
					name: "synxId",
					label: "使用年限（年）",
					selectList: [//当类型为select时，option配置
					],
					ajax: {
						api: "getZjZcDurableYearList",
						valueName: "durableYearId",
						textName: "year"
					},
					immutableAdd: true
				},
				{
					type: "number",//
					name: "bxts",//
					label: "保修天数（月）",//
					placeholder: "请输入保修天数（月）",
					immutableAdd: true,
					max: 9999999999
				},
				{
					type: "text",//
					name: "ggxh",//
					label: "规格型号",//
					placeholder: "请输入规格型号",
					immutableAdd: true
				},
				{
					type: "date",//
					name: "grrq",//
					label: "购入日期",//
					dateFmt: "yyyy-MM-dd",
					defaultValue: new Date(),
					id: "grrq",
					immutableAdd: true
				},
				{
					type: "select",
					name: "grfsdm",
					label: "购入方式",
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
					type: "hidden",//
					name: "cgbh",//
					label: "采购编号",//
					placeholder: "请输入采购编号",
					immutableAdd: true
				},
				{
					type: "pickTree",
					name: "glrymc",
					label: "管理员",
					immutableAdd: true,
					pickType: {
						department: false,//部门列表对应接口字段名,false表示不开启该类
						member: "oaMemberList",//成员列表对应接口字段名,false表示不开启该类
					}
				},
				{
					type: "select",
					name: "cqdwid",
					label: "产权单位",
					immutableAdd: true,
					selectList: [//当类型为select时，option配置
					],
					ajax: {
						api: "getUnitSelectAllList",
						valueName: "dwbh",
						textName: "dwmc"
					},
					must: true
				},
				{
					type: "pickTree",//
					name: "oaSydw",//接口字段名
					label: "使用单位",//
					immutableAdd: true,
					pickType: {
						department: "oaDepartmentList",//部门列表对应接口字段名,false表示不开启该类
						member: false,//成员列表对应接口字段名,false表示不开启该类
					}
				},
				{
					type: "select",//
					name: "sybmid",//接口字段名
					label: "使用部门",//
					immutableAdd: true,
					selectList: [//当类型为select时，option配置
					],
					ajax: {
						api: "getDepartmentSelectAllList",
						valueName: "bmbh",
						textName: "bmmc"
					}
				},
				{
					type: "select",
					name: "cfdd1",
					label: "存放地点",
					immutableAdd: true,
					selectList: [//当类型为select时，option配置
					],
					ajax: {
						api: "getAssetsPlaceAllList",
						valueName: "recordid",
						textName: "cfddmc"
					},
				},
				{
					type: "text",//
					name: "syr",//接口字段名
					label: "使用人",//
					immutableAdd: true,
					placeholder: "请输入使用人",
				},
				{
					type: "date",//
					name: "scrq",//
					label: "出厂日期",//
					dateFmt: "yyyy-MM-dd",
					defaultValue: new Date(),
					immutableAdd: true,
					id: "scrq"
				},
				{
					type: "text",//
					name: "serialNumber",//
					label: "出厂编号",//
					immutableAdd: true,
					placeholder: "请输入出厂编号"
				},
				{
					type: "text",//
					name: "gysName",//
					label: "供应商",//
					immutableAdd: true,
					placeholder: "请输入供应商"
				},
				{
					type: "text",//
					name: "rangeName",//
					label: "供应范围",//
					immutableAdd: true,
					placeholder: "请输入供应范围"
				},
				{
					type: "text",//
					name: "brandName",//
					label: "品牌",//
					immutableAdd: true,
					placeholder: "请输入品牌"
				},
				{
					type: "textarea",//
					name: "bz",//
					label: "备注",//
					immutableAdd: true,
					placeholder: "请输入备注",
				},
				{
					type: "upload",//
					name: "imageList",//
					label: "资产图片",//
					btnName: "添加图片",
					projectName: "zj-assets",
					uploadUrl: 'uploadFilesByFileName',
					immutableAdd: true,
					fileType: ["jpg", "jpeg", "png", "gif", "docx", "xls", "doc", "ppt", "xlsx", "pptx", "xlsm", "pdf"]
				},
				{
		    	    type: "textarea",//
		    	    name: "opinionField1",//
		    	    label: "部门领导意见",//
		    	    placeholder: "",
		    	    immutableAdd: true
		    	},
				{
		    	    type: "textarea",//
		    	    name: "opinionField2",//
		    	    label: "机关办公室意见",//
		    	    placeholder: "",
		    	    immutableAdd: true
		    	},
				{
		    	    type: "textarea",//
		    	    name: "opinionField3",//
		    	    label: "公司领导意见",//
		    	    placeholder: "",
		    	    immutableAdd: true
		    	}				
			]
		},
		{
			//子资产清单
			name: "子资产清单",
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

var _body = {
	title: title,
	nodeId: nodeId,
	trackId: trackId,
	workId: workId,
	flowId: flowId,
	apiName: "getZjAssetsPcDetails",
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
				apiName: "updateZjAssetsFlow"
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
				recordid = apiDataObj.recordid;
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
								var $option = $("<div>意见：" + (item["comments"] || "--") + "</div>"); //意见
								var $time = $("<div>时间：" + (item["actionTime"] ? l.dateFormat(new Date(item["actionTime"]), "yyyy-MM-dd HH:mm:ss") : "--") + "</div>"); //意见
								$histItem.append($histItemArr); //空格圆
								$histItem.append($nodeName);
								$histItem.append($nodePer);
								$histItem.append($option);
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
									data: "subName", //接口对应字段
									title: "资产名称", //表头名
									defaultContent: "-" //默认显示
								},
								{
									targets: [2], //第几列
									data: "subRemarks", //接口对应字段
									title: "备注姓名", //表头名
									defaultContent: "-" //默认显示
								},
								{
									targets: [3], //第几列
									data: "modifyUserName", //接口对应字段
									title: "操作员", //表头名
									defaultContent: "-" //默认显示
								},
								{
									targets: [4], //第几列
									data: "modifyTime", //接口对应字段
									title: "操作时间", //表头名
									defaultContent: "-",//默认显示
									render: function (data) {
										return l.dateFormat(new Date(data), "yyyy-MM-dd HH:mm:ss");
									}
								}
							]
						});
						$tabCon.append($con);
						tabCons[i] = $tabCon;
						//子资产列表
						var pager = pagerDom.page({
							remote: {
								//ajax请求配置
								url: l.getApiUrl("getZjZcSubassetList"),
								params: {
									mainAssteId: recordid
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
							var btna = flowObj.btns
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
										window.location.href = 'http://weixin.fheb.cn:91/ureport/pdf?_u=file:stzg.xml&url=' + l.domain + '&workId=' + params.workId;
										break;
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
