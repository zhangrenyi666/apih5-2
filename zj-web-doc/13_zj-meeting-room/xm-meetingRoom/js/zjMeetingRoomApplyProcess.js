var nodeId = l.getUrlParam('nodeId') || "Node1";
var flowId = l.getUrlParam('flowId') || "";
var workId = l.getUrlParam('workId') || "HZ2881f76458a99c016458a99ee70001";
var trackId = l.getUrlParam('trackId') || "HZ2881f76458a99c016458a99efe0002";
var title = l.getUrlParam('title') || "";
var nodeName = l.getUrlParam('nodeName') || "";
var state = l.getUrlParam('state') || "";

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
		    	                	type: "text",//
		    	                	name: "meetingRoomTitle",//
		    	                	label: "会议名称",//
		    	                	placeholder: "请输入会议名称",
		    	                	immutableAdd: true,
		    	                	lineNum: 2

		    	                },
		    	                //3								
		    	                {
		    	                	type: "date",//
		    	                	name: "meetingStartTime",//
		    	                	label: "会议开始日期",//
		    	                	dateFmt: "yyyy-MM-dd HH:mm:ss",
		    	                	defaultValue: new Date(),
		    	                	id:"meetingStartTime",
		    	                	immutableAdd: true,
		    	                	lineNum: 4
		    	                },	
		    	                //4
		    	                {
		    	                	type: "date",//
		    	                	name: "meetingEndTime",//
		    	                	label: "会议结束日期",//
		    	                	dateFmt: "yyyy-MM-dd HH:mm:ss",
		    	                	defaultValue: new Date(),
		    	                	id:"meetingEndTime",
		    	                	immutableAdd: true,
		    	                	lineNum: 4
		    	                },
		    	                //5
		    	                {
		    	                	type: "text",//
		    	                	name: "applyUnit",//
		    	                	label: "申请单位",//
		    	                	placeholder: "请输入申请单位",
		    	                	immutableAdd: true,
		    	                	lineNum: 2
		    	                },	
		    	                //6
		    	                {
		    	                	type: "text",//
		    	                	name: "meetingDecoration",//
		    	                	label: "会议室摆设",//
		    	                	placeholder: "请输入会议室摆设",
		    	                	immutableAdd: true,
		    	                	lineNum: 6
		    	                },
		    	                //7
		    	                {
		    	                	type: "text",//
		    	                	name: "meetingForm",//
		    	                	label: "会议形式",//
		    	                	placeholder: "请输入会议形式",
		    	                	immutableAdd: true,
		    	                	lineNum: 7
		    	                },	
		    	                //8
		    	                {
		    	                	type: "date",//
		    	                	name: "debuggingTime",//
		    	                	label: "调试时间",//
		    	                	dateFmt: "yyyy-MM-dd",
		    	                	defaultValue: new Date(),
		    	                	id:"debuggingTime",
		    	                	immutableAdd: true,
		    	                	lineNum: 7
		    	                },	
		    	                //9
		    	                {
		    	                	type: "select",//
		    	                	name: "meetingRoomId",//
		    	                	label: "会议室",
		    	                	immutableAdd: true,										
		    	                	placeholder: "请输入会议室",
		    	                	selectList: [//当类型为select时，option配置
		    	                	             ],
		    	                	             ajax: {
		    	                	            	 api: "getMeetingRoomAllListForFlow",//api名称
		    	                	            	 valueName: "recordid",//设置value对应的接口字段名称
		    	                	            	 textName: "meetingRoomName",//设置text对应的接口字段名称
		    	                	             },
		    	                	             lineNum: 6,											 
		    	                },	
		    	                //10
		    	                {
		    	                	type: "text",//
		    	                	name: "meetingPeopleNumber",//
		    	                	label: "参会人数",//
		    	                	placeholder: "请输入参会人数",
		    	                	immutableAdd: true,
		    	                	lineNum: 10
		    	                },	
		    	                //11
		    	                {
		    	                	type: "text",//
		    	                	name: "joinUnitMainVenue",//
		    	                	label: "参会单位（主会场）",//
		    	                	placeholder: "单位与单位之间以”,“分隔",
		    	                	immutableAdd: true,
		    	                	lineNum: 11
		    	                },	
		    	                //12
		    	                {
		    	                	type: "text",//
		    	                	name: "joinUnitHardVideo",//
		    	                	label: "参会单位（硬视频）",//
		    	                	placeholder: "单位与单位之间以”,“分隔",
		    	                	immutableAdd: true,
		    	                	lineNum: 12
		    	                },	
		    	                //13
		    	                {
		    	                	type: "text",//
		    	                	name: "joinUnitSoftVideo",//
		    	                	label: "参会单位（软视频）",//
		    	                	placeholder: "单位与单位之间以”,“分隔",
		    	                	immutableAdd: true,
		    	                	lineNum: 13
		    	                },		
		    	                //14
		    	                {
		    	                	type: "text",//
		    	                	name: "meetingLobbyScreen",//
		    	                	label: "会议室及大堂屏幕",//
		    	                	placeholder: "",
		    	                	immutableAdd: true,
		    	                	lineNum: 10									

		    	                },
		    	                //15
		    	                {
		    	                	type: "text",//
		    	                	name: "rostrumMike",//
		    	                	label: "主席台麦克（只）",//
		    	                	placeholder: "请输入主席台麦克数量（只）",
		    	                	immutableAdd: true,
		    	                	lineNum: 15
		    	                },
		    	                //16
		    	                {
		    	                	type: "text",//
		    	                	name: "moveMike",//
		    	                	label: "移动麦克风（只）",//
		    	                	placeholder: "请输入移动麦克数量（只）",
		    	                	immutableAdd: true,
		    	                	lineNum: 15
		    	                },	
		    	                //17
		    	                {
		    	                	type: "select",
		    	                	name: "projector",
		    	                	label: "投影仪",
		    	                	selectList: [//当类型为select时，option配置
		    	                	             {
		    	                	            	 value: 0,//输入字段的值
		    	                	            	 text: "是",//显示中文名
		    	                	            	 selected: true//默认是否选中
		    	                	             },
		    	                	             {
		    	                	            	 value: 1,//输入字段的值
		    	                	            	 text: "否",//显示中文名
		    	                	            	 selected: false//默认是否选中
		    	                	             }
		    	                	             ],
		    	                	             immutableAdd: true,
		    	                	             lineNum: 17
		    	                },
		    	                //18
		    	                {
		    	                	type: "select",
		    	                	name: "soundRecording",
		    	                	label: "录音",
		    	                	selectList: [//当类型为select时，option配置
		    	                	             {
		    	                	            	 value: 0,//输入字段的值
		    	                	            	 text: "是",//显示中文名
		    	                	            	 selected: true//默认是否选中
		    	                	             },
		    	                	             {
		    	                	            	 value: 1,//输入字段的值
		    	                	            	 text: "否",//显示中文名
		    	                	            	 selected: false//默认是否选中
		    	                	             }
		    	                	             ],
		    	                	             immutableAdd: true,
		    	                	             lineNum: 17
		    	                },	
		    	                //19
		    	                {
		    	                	type: "select",
		    	                	name: "videotape",
		    	                	label: "录像",
		    	                	selectList: [//当类型为select时，option配置
		    	                	             {
		    	                	            	 value: 0,//输入字段的值
		    	                	            	 text: "是",//显示中文名
		    	                	            	 selected: true//默认是否选中
		    	                	             },
		    	                	             {
		    	                	            	 value: 1,//输入字段的值
		    	                	            	 text: "否",//显示中文名
		    	                	            	 selected: false//默认是否选中
		    	                	             }
		    	                	             ],
		    	                	             immutableAdd: true,
		    	                	             lineNum: 18
		    	                },
		    	                //20
		    	                {
		    	                	type: "select",
		    	                	name: "music",
		    	                	label: "音乐",
		    	                	selectList: [//当类型为select时，option配置
		    	                	             {
		    	                	            	 value: 0,//输入字段的值
		    	                	            	 text: "是",//显示中文名
		    	                	            	 selected: true//默认是否选中
		    	                	             },
		    	                	             {
		    	                	            	 value: 1,//输入字段的值
		    	                	            	 text: "否",//显示中文名
		    	                	            	 selected: false//默认是否选中
		    	                	             }
		    	                	             ],
		    	                	             immutableAdd: true,
		    	                	             lineNum: 18
		    	                },	
		    	                //21
		    	                {
		    	                	type: "select",
		    	                	name: "pPT",
		    	                	label: "PPT",
		    	                	selectList: [//当类型为select时，option配置
		    	                	             {
		    	                	            	 value: 0,//输入字段的值
		    	                	            	 text: "是",//显示中文名
		    	                	            	 selected: true//默认是否选中
		    	                	             },
		    	                	             {
		    	                	            	 value: 1,//输入字段的值
		    	                	            	 text: "否",//显示中文名
		    	                	            	 selected: false//默认是否选中
		    	                	             }
		    	                	             ],
		    	                	             immutableAdd: true,
		    	                	             lineNum: 18
		    	                },
		    	                //22
		    	                {
		    	                	type: "select",
		    	                	name: "tableCard",
		    	                	label: "桌牌",
		    	                	selectList: [//当类型为select时，option配置
		    	                	             {
		    	                	            	 value: 0,//输入字段的值
		    	                	            	 text: "是",//显示中文名
		    	                	            	 selected: true//默认是否选中
		    	                	             },
		    	                	             {
		    	                	            	 value: 1,//输入字段的值
		    	                	            	 text: "否",//显示中文名
		    	                	            	 selected: false//默认是否选中
		    	                	             }
		    	                	             ],
		    	                	             immutableAdd: true,
		    	                	             lineNum: 22
		    	                },	
		    	                //23
		    	                {
		    	                	type: "select",
		    	                	name: "teaWater",
		    	                	label: "茶水",
		    	                	selectList: [//当类型为select时，option配置
		    	                	             {
		    	                	            	 value: 0,//输入字段的值
		    	                	            	 text: "是",//显示中文名
		    	                	            	 selected: true//默认是否选中
		    	                	             },
		    	                	             {
		    	                	            	 value: 1,//输入字段的值
		    	                	            	 text: "否",//显示中文名
		    	                	            	 selected: false//默认是否选中
		    	                	             }
		    	                	             ],
		    	                	             immutableAdd: true,
		    	                	             lineNum: 22
		    	                },
		    	                //24
		    	                {
		    	                	type: "select",
		    	                	name: "platform",
		    	                	label: "讲台",
		    	                	selectList: [//当类型为select时，option配置
		    	                	             {
		    	                	            	 value: 0,//输入字段的值
		    	                	            	 text: "是",//显示中文名
		    	                	            	 selected: true//默认是否选中
		    	                	             },
		    	                	             {
		    	                	            	 value: 1,//输入字段的值
		    	                	            	 text: "否",//显示中文名
		    	                	            	 selected: false//默认是否选中
		    	                	             }
		    	                	             ],
		    	                	             immutableAdd: true,
		    	                	             lineNum: 22
		    	                },
		    	                //25
		    	                {
		    	                	type: "select",
		    	                	name: "vdeoconferencing",
		    	                	label: "视频会议",
		    	                	selectList: [//当类型为select时，option配置
		    	                	             {
		    	                	            	 value: 0,//输入字段的值
		    	                	            	 text: "是",//显示中文名
		    	                	            	 selected: true//默认是否选中
		    	                	             },
		    	                	             {
		    	                	            	 value: 1,//输入字段的值
		    	                	            	 text: "否",//显示中文名
		    	                	            	 selected: false//默认是否选中
		    	                	             }
		    	                	             ],
		    	                	             immutableAdd: true,
		    	                	             lineNum: 25
		    	                },
		    	                //28
		    	                {
		    	                	type: "text",//
		    	                	name: "applyUnitContacts",//
		    	                	label: "申请单位联系人",//
		    	                	placeholder: "请输入申请单位联系人",
		    	                	immutableAdd: true,
		    	                	lineNum: 25
		    	                },
		    	                //29
		    	                {
		    	                	type: "text",//
		    	                	name: "applyUnitContactsWay",//
		    	                	label: "联系方式",//
		    	                	placeholder: "请输入联系方式",
		    	                	immutableAdd: true,
		    	                	lineNum: 25
		    	                },	
		    	                //30
		    	                {
		    	                	type: "text",//
		    	                	name: "meetingSpecialNeeds",//
		    	                	label: "会议主要流程及特殊需要",//
		    	                	placeholder: "请输入会议主要流程及特殊需要",
		    	                	immutableAdd: true
		    	                },									
		    	                {
		    	                	type: "textarea",//
		    	                	name: "opinionField1",//
		    	                	label: "领导",//
		    	                	placeholder: "请输入领导意见",
		    	                	immutableAdd: true
		    	                },
							    {
		    	                	type: "textarea",//
		    	                	name: "remakes",//
		    	                	label: "备注",//
									defaultValue: "1、所有会议调试时间一般安排在会前0.5天。\r"+
                                                  "2、预定会议室请提前2天预定。",		    	                
						            immutableAdd: true,
		    	                }	
		    	                ]
		       },
		       {
		    	   name: "附件信息",
		    	   type: "2",
		    	   tbName: "",
		    	   tbIdName: "fileId",
		    	   conditions: [
		    	                {
		    	                	type: "upload",//
		    	                	name: "sealFileList",//
		    	                	label: "附件1",//
		    	                	btnName: "添加附件",
		    	                	projectName: "zjSeal",
		    	                	fileType: ["jpg", "jpeg", "png", "gif", "docx", "xls"]
		    	                }
		    	                ]
		       }, {
		    	   name: "流程进度图",
		    	   type: "4"
		       }
		       ]
}




//add by lny on 717
var _body = {
		title: title,
		nodeId: nodeId,
		trackId: trackId,
		workId: workId,
		flowId: flowId,
		apiName: "getZjMeetingApplyFlowDetailByWorkId",
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
				apiName: "updateXmMeetingReservationsFlow"
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
							if(state == "结束"){
							btns.push({
								name: "export",
								label: "导出"
							})		
							}						
							var btna = flowObj.btns
							if(nodeName == "公司办公室"){
							btns.push({
								name: "export",
								label: "导出"
							})
							}
							if(nodeName == "确认/编号"){
							btns.push({
								name: "export",
								label: "导出"
							})								
							}
							customBtnGroup = {
								btns: btns,
								callback: function (dataName, obj) {
									switch (dataName) {
	       case "export"://导出
            var params = {};
			params.workId = workId;
                l.ajax('zjMeetingReservationFlowExportExcel', params, function (res) {
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


													//add by lny on 717
													body["apiBody"] = {}
													for (var key in tabObjDatas.data) {
														body["apiBody"][key] = tabObjDatas.data[key]
													}
													//add by lny on 717



													// body["title"] = tabObjDatas.data[workFormJson.titleName];
													body["title"] = title;
													body["opinionContent"] = tabObjDatas.data['opinionContent'];
												} else if (tabItemj.type === "2") {//如果是附件类型子表，type==="2"
													//给子表赋值-附件表
													if (!body["subTableObject"]) { body["subTableObject"] = {} }
													for (var key in tabObjDatas.data) {
														var subTableDataObject = tabObjDatas.data[key];


														//add by lny on 717
														body["apiBody"][key] = tabObjDatas.data[key]
														//add by lny on 717



														body["subTableObject"][key] = { subTablePrimaryIdName: tabItemj.tbIdName, subTableType: tabItemj.type, subTableDataObject: subTableDataObject }
													}
												} else {//如果是普通类型子表，type==="1"，目前只有1和2
													//给子表赋值-普通表
													if (!body["subTableObject"]) { body["subTableObject"] = {} }


													//add by lny on 717
													for (var key in tabObjDatas.data) {
														body["apiBody"][key] = tabObjDatas.data[key]
													}
													//add by lny on 717



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


						//add by lny on 717
						var apiData = _d.apiData
						var apiName = _d.apiName
						if (apiName) {
							tabCons[i].setOpenData(JSON.parse(apiData))
						} else {
							//add by lny on 717
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
