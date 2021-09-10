var code = l.getUrlParam('code');
Lny.setCookie('code', code, 30);
var nodeId = l.getUrlParam('nodeId') || "Node1";
var workId = l.getUrlParam('workId') || "HZ2881ed657a246501657a24667e0001";
var trackId = l.getUrlParam('trackId') || "HZ2881f76458a99c016458a99efe0002";

var workFormJson = {//流程模版json（升级版：根据流程工作id动态获取，当前前台写死）
		name: "联合督导申请（其他）",
		titleName: "zcmc",
		tabs: [
		       {
		    	   name: "基本信息",
		    	   type: "1",
		    	   isMain: "1",
		    	   tbName: "zjLhddJointSupApply",
		    	   tbIdName: "applyId",
		    	   conditions: [
		    	                {
		    	                	type: "hidden",//
		    	                	name: "applyId",//
		    	                	label: "主键id",//
		    	                	placeholder: "",
									disabled:true, //流程处理时不可编辑
									immutableAdd:true
		    	                }, 
								{
		    	                	type: "text",//
		    	                	name: "title",//
		    	                	label: "标题",//
		    	                	placeholder: "请输入标题",
									disabled:true, //流程处理时不可编辑
									immutableAdd:true
		    	                },
								{
		    	                	type: "text",//
		    	                	name: "applyDepName",//
		    	                	label: "主责部门",//
		    	                	placeholder: "请输入主责部门",
									disabled:true, //流程处理时不可编辑
									immutableAdd:true
		    	                },																
                                {
		    	                	type: "pickTree",//
		    	                	name: "oaNeedDep",//接口字段名
		    	                	label: "配合的部门",//
		    	                	pickType: {
		    	                		department: "oaDepartmentList",//部门列表对应接口字段名,false表示不开启该类
		    	                		member: false,//成员列表对应接口字段名,false表示不开启该类
		    	                	}
		    	                },
								{
		    	                	type: "pickTree",//
		    	                	name: "oaCheckObject",//接口字段名
		    	                	label: "检查对象",//
		    	                	immutableAdd: true,
									disabled:true, //流程处理时不可编辑
		    	                	pickType: {
		    	                		department: "oaDepartmentList",//部门列表对应接口字段名,false表示不开启该类
		    	                		member: false,//成员列表对应接口字段名,false表示不开启该类
		    	                	}
		    	                },
								{
		    	                	type: "text",//
		    	                	name: "informant",//
		    	                	label: "填报人",//
		    	                	placeholder: "请输入填报人",
									disabled:true, //流程处理时不可编辑
									immutableAdd:true
		    	                },
								{
		    	                	type: "date",//
		    	                	name: "informantTime",//
		    	                	label: "填报时间",//
									dateFmt: "yyyy-MM-dd",
									defaultValue: new Date(),
									id:"informantTime",
									disabled:true, //流程处理时不可编辑
									immutableAdd:true
		    	                },
								{
		    	                	type: "textarea",//
		    	                	name: "opinionField1",//
		    	                	label: "部门总经理",//
		    	                	placeholder: "请输入",
									disabled:true, //流程处理时不可编辑
									immutableAdd:true
		    	                },
								{
		    	                	type: "textarea",//
		    	                	name: "opinionField2",//
		    	                	label: "相关部门",//
		    	                	placeholder: "请输入",
									disabled:true, //流程处理时不可编辑
									immutableAdd:true
		    	                },
								{
		    	                	type: "textarea",//
		    	                	name: "opinionField3",//
		    	                	label: "监督委员",//
		    	                	placeholder: "请输入",
									disabled:true, //流程处理时不可编辑
									immutableAdd:true
		    	                },
								{
		    	                	type: "textarea",//
		    	                	name: "opinionField4",//
		    	                	label: "分管领导",//
		    	                	placeholder: "请输入",
									disabled:true, //流程处理时不可编辑
									immutableAdd:true
		    	                },
								{
		    	                	type: "textarea",//
		    	                	name: "opinionField5",//
		    	                	label: "主要领导",//
		    	                	placeholder: "请输入",
									disabled:true, //流程处理时不可编辑
									immutableAdd:true
		    	                },
								{
		    	                	type: "textarea",//
		    	                	name: "opinionField6",//
		    	                	label: "其他领导",//
		    	                	placeholder: "请输入",
									disabled:true, //流程处理时不可编辑
									immutableAdd:true
		    	                }
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
		    	                	name: "zjLhddJointSupFile",//
		    	                	label: "附件1",//
		    	                	btnName: "添加附件",
		    	                	projectName: "zj-work-check",
									disabled:true, //流程处理时不可编辑
		    	                	immutableAdd: true,
		    	                	// uploadUrl: 'uploadFiles',
		    	                	fileType: ["jpg", "jpeg", "png", "gif"]
		    	                }
		    	                ]
		       }, 
			   {
		    	   name: "流程进度图",
		    	   type: "4"
		       }
		       ]
}


//add by lny on 717
var _body={
nodeId: nodeId,
 trackId: trackId,
 workId: workId,
 apiName:"getZjLhddJointSupApplyDetailsByWorkId",
 apiBody:{workId:workId}
}




l.ajax("openFlow",_body, function (_d, _m, _s, _r) {
	
	var apiName1 ;
    if (_d.nodeVars != null && _d.nodeVars.formEditFlag === "1") {
	     apiName1 ='updateZjLhddJointSupApplyForEdit'
	}else{
	     apiName1 ='updateZjLhddJointSupApply'
	}
	
	if (_s) {
		loadFlow(_d.flowButtons, {
			otherBody: {
				nodeId: _d.flowNode.nodeId,
				trackId: _d.flowNode.trackId,
				workId: _d.workId,	
                flowVars:_d.flowVars,
				nodeVars:_d.nodeVars,				
				apiName:apiName1

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
					$.each(workFormJson.tabs, function(tIndex, tItem) {
						if (tItem.isMain === "1" || tItem.type === "2") { 
							var conditions = tItem.conditions;
							for (var w = 0; w < conditions.length; w++) {
								if(!conditions[w].opinionField && !conditions[w].disabled){
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
											        body["apiBody"][key]=tabObjDatas.data[key]
									                }							
										            //add by lny on 717

													
													
													
													body["title"] = tabObjDatas.data[workFormJson.titleName];
													body["opinionContent"] = tabObjDatas.data['opinionContent'];
												} else if (tabItemj.type === "2") {//如果是附件类型子表，type==="2"
													//给子表赋值-附件表
													if (!body["subTableObject"]) { body["subTableObject"] = {} }
													for (var key in tabObjDatas.data) {
														var subTableDataObject = tabObjDatas.data[key];
														
														
														
														//add by lny on 717
														body["apiBody"][key]= tabObjDatas.data[key]
														//add by lny on 717
															
															
															
														body["subTableObject"][key] = { subTablePrimaryIdName: tabItemj.tbIdName, subTableType: tabItemj.type, subTableDataObject: subTableDataObject }
													}
												} else {//如果是普通类型子表，type==="1"，目前只有1和2
													//给子表赋值-普通表
													if (!body["subTableObject"]) { body["subTableObject"] = {} }
													
													
													
													    //add by lny on 717
														    for (var key in tabObjDatas.data) {											
											                    body["apiBody"][key]=tabObjDatas.data[key]
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

				
							
							if(_d.nodeVars != null){
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
						
					
					
					//add by lny on 717
					var apiData=_d.apiData					
					var apiName=_d.apiName
					if(apiName){
						tabCons[i].setOpenData(JSON.parse(apiData))
					}else{
					//add by lny on 717


					
						//流程操作特殊代码（向各个表单中赋值）---开始
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