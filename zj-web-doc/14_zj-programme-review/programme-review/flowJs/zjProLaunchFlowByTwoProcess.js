var code = l.getUrlParam("code");
Apih5.setCookie("code", code, 30);
var flowId = l.getUrlParam("flowId") || "";
var nodeId = l.getUrlParam("nodeId") || "Node1";
var workId = l.getUrlParam("workId") || "";
var trackId = l.getUrlParam("trackId") || "";
var title = l.getUrlParam("title") || "";
var btnFlag = l.getUrlParam("btnFlag") || "0";
var reviewState = l.getUrlParam('reviewState') || "";
var table, pagerDom, detailForm;
var workFormJson = {//流程模版json（升级版：根据流程工作id动态获取，当前前台写死）
		name: "方案评审审批",
		titleName: "zcmc",
		tabs: [
		        {
      name: "基本信息",
      type: "1", //普通tab页1，附件tab页2，列表tab页面3
      isMain: "1", //是否为主表
      tbName: "zjPrProgrammeLaunchFlow",//表名
	  tbIdName: "launchId",//表主键id
      conditions: [
        {
            type: "text",//五种形式：text,select,date,hidden,textarea,
            name: "schemeNumber",//输入字段名
            label: "方案编号",			
            immutableAdd: true,
			"lineNum":0
        },
        {
            type: "text",//五种形式：text,select,date,hidden,textarea,
            name: "schemeName",//输入字段名
            label: "方案名称",
            immutableAdd: true,
			"lineNum":0
        },
        {
            type: "text",//五种形式：text,select,date,hidden,textarea,
            name: "projectName",//输入字段名
            label: "项目名称",
            immutableAdd: true,
			lineNum: 1
        },
        {
            type: "text",
            name: "unitName",
            label: "实施单位",
            immutableAdd: true,
            lineNum: 1
        },
		{
            type: "text",
            name: "schemeLevelText",
            label: "方案等级",
            immutableAdd: true,
            lineNum: 2
        },
		        {
            type: "date",
            name: "implementationTime",
            id: "implementationTime",			
            label: "方案计划实施时间",
            immutableAdd: true,
            lineNum: 2
        },
        {
            type: "date",
            name: "projectStartDate",
            id: "projectStartDate",			
            label: "项目开工日期",
            immutableAdd: true,
            lineNum: 3
        },
        {
            type: "date",
            name: "projectEndDate",
            id: "projectEndDate",			
            label: "项目交工日期",
            immutableAdd: true,
            lineNum: 3
        },
        {
            type: "text",//text,select,date,
            name: "programmingPerson",
            label: "方案编制人",
            immutableAdd: true,
            lineNum: 4
        },
        {
            type: "text",//text,select,date,
            name: "projectGeneralUser",
            label: "项目总工",
            immutableAdd: true,
            lineNum: 4
        },
        {
            type: "text",//text,select,date,
            name: "programmingPersonTel",
            label: "方案编制人联系方式",
            immutableAdd: true,
            lineNum: 5
        },
        {
            type: "text",
            name: "projectGeneralUserTel",
            label: "项目总工联系方式",
            immutableAdd: true,
            lineNum: 5
        },
        {
            type: "textarea",
            name: "programmingPreliminaryTrial",
            label: "方案初评情况",
            immutableAdd: true,
            lineNum: 6
        },
        {
            type: "textarea",
            name: "opinionField2",
            label: "技术中心自审审批意见",
            immutableAdd: true,			
            lineNum: 7
        },
        {
            type: "textarea",
            name: "opinionField3",
            label: "事业部人员审核意见",
            immutableAdd: true,				
            lineNum: 8
        },
        {
            type: "textarea",//
            name: "opinionField4",//
            label: "单位总工审核意见",//
            immutableAdd: true,
            lineNum: 9			
        },
        {
            type: "textarea",//
            name: "opinionField5",//
            label: "特、高级专家审批意见",//
            immutableAdd: true,
            lineNum: 10			
        },
        {
            type: "textarea",//
            name: "opinionField6",//
            label: "事业部技术负责人审批意见",//
            immutableAdd: true,
            lineNum: 11			
        },
        {
            type: "textarea",//
            name: "opinionField7",//
            label: "公司总工审批意见",//
            immutableAdd: true,
            lineNum: 12		
        },		
	    {
            type: "hidden",//五种形式：text,select,date,hidden,textarea,
            name: "launchId",//输入字段名
            label: "方案审批id",
            immutableAdd: true
        },
      ]
    },
		       {
		    	   name: "附件信息",
		    	   type: "2",
		    	   tbName: "",
		    	   tbIdName: "fileId",// fileId
		    	   conditions: [
		    	                {
		    	                	type: "upload",//
		    	                	name: "fileList",//
		    	                	label: "附件1",//
		    	                	btnName: "添加附件",
		    	                	projectName: "zj-san-party",
		    	                	immutableAdd: true,
		    	                	uploadUrl: 'uploadFilesByFileName',
		    	                	fileType: ["jpg", "jpeg", "png", "gif", "docx", "xls", "doc", "ppt", "xlsx", "pptx", "xlsm", "pdf"]
		    	                }
		    	                ]
		       },
		       {
		    	   name: "操作历史",
		    	   type: "5"
		       }
		       ]
}

//add by apih5 on 717
var _body={
		title: title,
		nodeId: nodeId,
		trackId: trackId,
		workId: workId,
		flowId: flowId,
		apiName:"getZjPrProgrammeLaunchFlowDetailByWorkId",
		apiBody:{workId:workId}
}

l.ajax("openFlow",_body, function (_d, _m, _s, _r) {
	if (_s) {
		loadFlow(_d.flowButtons, {
			otherBody: {
				nodeId: _d.flowNode.nodeId,
				trackId: _d.flowNode.trackId,
				workId: _d.workId,	
				flowVars:_d.flowVars,
				nodeVars:_d.nodeVars,				
				apiName:"updateZjPrProgrammeLaunchFlow"
			},
			submitFn: function () {
				parent.pager.page('remote')
				parent.layer.close(parent.myOpenLayer)
			},
			callback: function (flowObj) {
				if(btnFlag == "1"){
               //如果	btnFlag = 1，那么不显示流程的按钮				
				flowObj.btns = [];	
				}				
				var $tabSystem = $("#tab-system")//模版顶级jq对象
				var $tabBar = $('<div class="tabBar cl"></div>');//tab按钮控制条
				var tabCons = []//tab内容页面组
				var mainTableDataObject = _d.mainTableDataObject//主表数据对象
				var subTableObject = _d.subTableObject//子表数据对象数组
				var flowWebUrl = _d.flowWebUrl || ""//子表数据对象数组

				$.each(workFormJson.tabs, function(i, tabItem) {
					//第一次遍历workFormJson.tabs
					var $tabBtn = $("<span>" + tabItem.name + "</span>"); //创建tab按钮$对象
					$tabBar.append($tabBtn); //向tab按钮控制条插入tab按钮
					var $tabCon = $( '<div class="tabCon" id="tab' + i.toString() + '"></div>' ); //创建tab内容页面$对象
					if (tabItem.type === "4") {
						//流程图tab
						if (flowWebUrl) {
							var $iframe = $( '<iframe width="100%" height="600" src="' + flowWebUrl + '"/>' );
							tabCons[i] = $tabCon.append($iframe);
						} else {
							tabCons[i] = $tabCon.append( $('<div style="color:#666;text-align:center;line-height:50px">未发现流程图</div>' ));
						}
					}
					//操作历史start
					else if (tabItem.type === "5") {
						var flowHistoryList = _d.flowHistoryList;
						if (flowHistoryList && flowHistoryList.length) {
							var $timeLineContainer = $('<div style="border-left:2px solid #e8e8e8;margin-top:20px;padding:20px 0px;"></div>');
							for (var j = 0; j < flowHistoryList.length; j++) {
								var item = flowHistoryList[j];
								var $histItem = $('<div style="position:relative;padding-left:20px;margin-bottom:20px;font-size:14px;color:#666;"></div>');
								if (item.historyFlag == "2") {
									$histItem.css({color: "#000"});
								}
								var $histItemArr = $('<div style="background:white;color:#1890ff;font-weight:800;height:15px;line-height:12px;font-size:25px;;left:-9px;top:0px;bottom:0px;margin:auto;position:absolute;">○</div>');
								var $nodeName = $( "<div>节点名称：" + item["nodeName"] + "</div>" ); //节点名称
								var $nodePer = $( "<div>审核者：" + item["realName"] + "</div>" ); //审核者
								var $option = $( "<div>意见：" + (item["comments"] || "--") + "</div>" ); //意见
								var $time = $( "<div>时间：" + (item["actionTime"] ? l.dateFormat( new Date(item["actionTime"]), "yyyy-MM-dd HH:mm:ss") : "--") + "</div>" ); //意见
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
					} else {
						var customBtnGroup; //tab内容页面中表单的底部按钮组配置
						if (tabItem.isMain) {
							//如果是主表单
							var btns = flowObj.btns;
							if(reviewState == "4"){
							btns.push({
								name: "export",
								label: "打印"
							})		
							}								
							btns.push({
								name: "cancel",
								label: "关闭"
							});						
							customBtnGroup = {
									btns: btns,
									callback: function(dataName, obj) {
										switch (dataName) {
	       case "export"://导出
            var params = {};
			params.workId = workId;
                l.ajax('zjPrProgrammeLaunchFlowExportWord', params, function (res) {
                    layer.alert("导出成功！", { icon: 0 }, function (index) {
                        layer.close(index);
                        window.location.href = res;
                    })
                })
            break;												
										case "cancel":
											obj.close();
											break;
										default:
											var body = {};
										for (var j = 0;j < workFormJson.tabs.length;j++) {
											//第二次遍历workFormJson.tabs，为了让return起作用所以不用$.each而采用for循环
											var tabItemj = workFormJson.tabs[j]; //模版中tabs数组的遍历元素数据对象
											if (tabItemj.type === "4" ||tabItemj.type === "5") {//流程图tab
											} else if (tabItemj.type === "3") {//列表tab
											} else {var tabObjDatas = tabCons[j].getDatas(); //tab内容页面组的遍历对象获取数据对象
												if (tabObjDatas.err.length) {//判断是否有错误（字段不能为空、超过个数限制等）
													layer.alert(tabObjDatas.err.join("<br/>"),{ icon: 0 },function(index) {
																$tabSystem.Huitab({index: j});
																layer.close(index);
																}
													);
													return; //直接停止for循环，且for循环之后的代码也不执行
												}
												if (tabItemj.isMain) {
													//如果是主表
													//给主表赋值
													body["mainTableName"] =tabItemj.tbName;
													body["mainTablePrimaryIdName" ] = tabItemj.tbIdName;
													body["mainTableDataObject"] = tabObjDatas.data;
													
													//add by apih5 on 717
													body["apiBody"] = {};
													for (var key in tabObjDatas.data) {
														body["apiBody"][key] = tabObjDatas.data[key];
													}
													//add by apih5 on 717
													//body["title"] = tabObjDatas.data[workFormJson.titleName];
													body["title"] = title;
													body["opinionContent"] = tabObjDatas.data["opinionContent"];
												} else if (tabItemj.type === "2") {//如果是附件类型子表，type==="2"
													//给子表赋值-附件表
													if (!body["subTableObject"]) {
														body["subTableObject"] = {};
													}
													for (var key in tabObjDatas.data) {
														var subTableDataObject =tabObjDatas.data[key];
														//add by apih5 on 717
														body["apiBody"][key] =tabObjDatas.data[key];
														//add by apih5 on 717
														body[ "subTableObject" ][key] = {subTablePrimaryIdName:tabItemj.tbIdName,subTableType:tabItemj.type,subTableDataObject: subTableDataObject};
													}
												} else {//如果是普通类型子表，type==="1"，目前只有1和2
													//给子表赋值-普通表
													if (!body["subTableObject"]) {
													    body["subTableObject"] = {};
													}
													//add by apih5 on 717
													for (var key in tabObjDatas.data) {
														body["apiBody"][key] =tabObjDatas.data[key ];
													}
													//add by apih5 on 717
													body["subTableObject"][tabItemj.tbName] = {subTablePrimaryIdName:tabItemj.tbIdName,subTableType:tabItemj.type,subTableDataObject:tabObjDatas.data};
												}
											}
										}
										//流程操作特殊代码---开始
										if (false) {
											layer.confirm("确定打印？",{ icon: 0 },function(index) {
														//流程发起请求
														l.ajax(buttonUrl,body,function(data,message,success) {
																	if (success) {
																		window.location.href = data;
																	}
																}
														);
														layer.close(index);
													}
											);
										} else {
											flowObj.flowSelectOpen(dataName,body);
										}
										//流程操作特殊代码---结束
										}
									}
							};
							if(btnFlag == "0"){
							if (_d.nodeVars != null) {
								//如果需要显示意见框
								console.log(_d.nodeVars.opinionShowFlag);
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
							}

						} else {
							customBtnGroup = {
									btns: [],
									callback: function(dataName, obj) {}
							};
						}
						tabCons[i] = $tabCon.detailLayer({customBtnGroup: customBtnGroup,conditions: tabItem.conditions});

						//add by apih5 on 717
						var apiData = _d.apiData;
						var apiName = _d.apiName;
						if (apiName) {
							tabCons[i].setOpenData(JSON.parse(apiData));
						} else {
							//add by apih5 on 717

							//流程操作特殊代码（向各个表单中赋值）---开始
							if (tabItem.isMain) {
								tabCons[i].setOpenData(mainTableDataObject);
							} else if (tabItem.type === "2") {
								var _subTableDataObject = {};
								for (var key in subTableObject) {
									_subTableDataObject[key] =subTableObject[key].subTableDataObject;
								}
								tabCons[i].setOpenData(_subTableDataObject);
							} else {
								tabCons[i].setOpenData(subTableObject[tabItem.tbName].subTableDataObject
								);
							}
						}
						//流程操作特殊代码（向各个表单中赋值）---结束
						tabCons[i].close = function() {
							parent.pager.page("remote");
							parent.layer.close(parent.myOpenLayer);
						};
					}
				});
				$tabSystem.append($tabBar).append(tabCons).Huitab({
					index: 0
				});

			}
		})
	}
})
