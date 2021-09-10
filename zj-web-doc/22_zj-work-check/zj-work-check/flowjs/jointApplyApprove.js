var flowId = l.getUrlParam('id') || "lianhedudao";//流程模版id
var code = l.getUrlParam('code');
Lny.setCookie('code', code, 30);

var $tabSystem = $("#tab-system")//模版顶级jq对象
var $tabBar = $('<div class="tabBar cl"></div>');//tab按钮控制条
var tabCons = []//tab内容页面组
//根据flowId获取流程发起模版数据及数据
var flowFormJson = {
		name: "",
		tabs: []
}
var $tabTitle = $("#tab-title")//模版title
 $tabTitle.html("联合督导申请")

switch (flowId) {
case "lianhedudao":
	flowFormJson = {//流程模版json（升级版：根据流程id动态获取，当前前台写死）
		name: "联合督导申请",
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
		    	                },
								{
		    	                	type: "hidden",//
		    	                	name: "flowLaunchFlag",//
		    	                	label: "是否是流程发起",//0:是   1:不是
									defaultValue:"0",
									must: true
		    	                },
								{
		    	                	type: "text",//
		    	                	name: "title",//
		    	                	label: "标题",//
		    	                	placeholder: "请输入标题",
									must: true
		    	                },
								{
		    	                	type: "pickTree",//
		    	                	name: "oaApplyDep",//接口字段名
		    	                	label: "主责部门",//
		    	                	must: true,
		    	                	pickType: {
		    	                		department: "oaDepartmentList",//部门列表对应接口字段名,false表示不开启该类
		    	                		member: false,//成员列表对应接口字段名,false表示不开启该类
		    	                	}
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
		    	                	must: true,
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
									must: true
		    	                },
								{
		    	                	type: "date",//
		    	                	name: "informantTime",//
		    	                	label: "填报时间",//
									dateFmt: "yyyy-MM-dd",
									defaultValue: new Date(),
									id:"informantTime"
		    	                }]
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
		    	                	projectName: "zj-work-check",
		    	                	uploadUrl: 'uploadFiles',
		    	                	fileType: ["jpg", "jpeg", "png", "gif"]
		    	                }
		    	                ]
		       }
		       ]
}
	break;
default:
	break;
}





$.each(flowFormJson.tabs, function (i, tabItem) {//第一次遍历flowFormJson.tabs
	var $tabBtn = $('<span>' + tabItem.name + '</span>')//创建tab按钮$对象
	$tabBar.append($tabBtn)//向tab按钮控制条插入tab按钮
	var $tabCon = $('<div class="tabCon" id="tab' + i.toString() + '"></div>')//创建tab内容页面$对象
	var customBtnGroup;//tab内容页面中表单的底部按钮组配置
	if (tabItem.isMain) {//如果是主表单
		customBtnGroup = {
				btns: [
				       {
				    	   name: "launch",
				    	   label: '<i class="Hui-iconfont">&#xe603;</i> 发起'
				       },
				       {
				    	   name: "cancel",
				    	   label: "取消"
				       }
				       ],
				       callback: function (dataName, obj) {
				    	   switch (dataName) {
				    	   case "launch":
				    		   var body = {
				    			   flowId: flowId//流程id
				    	   }
				    		   for (var j = 0; j < flowFormJson.tabs.length; j++) {//第二次遍历flowFormJson.tabs，为了让return起作用所以不用$.each而采用for循环
				    			   var tabItemj = flowFormJson.tabs[j]//模版中tabs数组的遍历元素数据对象
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
				    				   body["mainTableName"] = tabItemj.tbName;
				    				   body["mainTablePrimaryIdName"] = tabItemj.tbIdName;
				    				   body["mainTableDataObject"] = tabObjDatas.data;


									   
										body["apiBody"] = {}
									    //add by lny on 717
										for (var key in tabObjDatas.data) {	
											body["apiBody"][key]=tabObjDatas.data[key]
									    }
										//add by lny on 717
										   
									   									   
									  									   
				    				   body["title"] = tabObjDatas.data[flowFormJson.titleName];									   
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
								   
								   
//add by lny on 717
body["apiName"]="addZjLhddJointSupApply"//购置申请发起时调用
//add by lny on 717



				    		   }
							   
							   

				    		   //流程发起特殊代码---开始
				    		   layer.confirm("确定发起？", { icon: 0, }, function (index) {//流程发起请求
				    			   l.ajax("createOpenFlow", body, function (_d, _m, _s, _r) {								   
				    				   if (_s) {//发起成功，返回workId
				    					   loadFlow(_d.flowButtons, {
				    						   otherBody: {
				    							   nodeId: _d.flowNode.nodeId,
				    							   trackId: _d.flowNode.trackId,
				    							   workId: _d.workId,
												   apih5FlowStatus:_d.apih5FlowStatus,
												   apiName:"updateZjLhddJointSupApply",
												   apiBody:{workId:_d.workId,apih5FlowStatus:_d.apih5FlowStatus}
				    						   },
				    						   endFn: function (buttonClass) { obj.close() },
				    						   callback: function (flowObj) {
				    							  flowObj.flowSelectOpen(0)
				    						   }
				    					   })
				    				   }
				    			   })
				    			   layer.close(index);
				    		   });
				    		   //流程发起特殊代码---结束
				    		   break;
				    	   case "cancel":
				    		   obj.close()
				    		   break;
				    	   default:
				    		   obj.close()
				    		   break;
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
	tabCons[i].setOpenData()
	tabCons[i].close = function () {
		parent.pager.page('remote')
		parent.layer.close(parent.myOpenLayer)
	}
})


$tabSystem.append($tabBar).append(tabCons).Huitab({
	index: 0
});


function custom_close() {
	if
	(confirm("您确定要关闭本页吗？")) {
		window.opener = null;
		window.open('', '_self');
		window.close();
	}
	else { }
}