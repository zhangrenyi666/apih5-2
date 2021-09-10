var flowId = l.getUrlParam('id') || "zjLeaveApply";//流程模版id
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
$tabTitle.html("请假申请")

switch (flowId) {
case "zjLeaveApply":
	flowFormJson = {//流程模版json（升级版：根据流程id动态获取，当前前台写死）
		name: "请假申请",
		titleName: "leaveTitle",
		tabs: [
		       {
		    	   name: "基本信息",
		    	   type: "1",//普通tab页1，附件tab页2，列表tab页面3
		    	   isMain: "1",//是否为主表
		    	   tbName: "zjFlowLeaveApply",//表名
		    	   tbIdName: "leaveApplyId",//表主键id
		    	   conditions: [
		    	                {
		    	                	type: "hidden",//
		    	                	name: "leaveApplyId",//
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
                            must: true
                        },
		    	                {
		    	                	type: "date", //
		    	                	name: "applyDate", //
		    	                	label: "申请日期", //
		    	                	dateFmt: "yyyy-MM-dd",
		    	                	defaultValue: new Date(),
		    	                	id: "applyDate",
		    	                	must: true
		    	                },
		    	                {
		                            type: "pickTree", //
		                            name: "oaName", //接口字段名
		                            label: "姓名", //
		                            must: true,
		                            immutableAdd: true,
		                            pickType: {
		                                department: false, //部门列表对应接口字段名,false表示不开启该类
		                                member: "oaMemberList" //成员列表对应接口字段名,false表示不开启该类
		                            }
		                        }, 
								{
		                            type: "pickTree", //
		                            name: "oaDept", //接口字段名--oaDept
		                            label: "部门", //
		                            must: true,
		                            //immutableAdd: true,
		                            pickType: {
		                                department: "oaDepartmentList", //部门列表对应接口字段名,false表示不开启该类
		                                member: false //成员列表对应接口字段名,false表示不开启该类
		                            }
		                        },
		    	                {
		    	                	type: "text",//
		    	                	name: "leaveDays",//
		    	                	label: "请假天数",//
		    	                	placeholder: "请假天数",
		    	                	must: true
		    	                },
		    	                {
		    	                	type: "select",
		    	                	name: "leaveType",
		    	                	label: "请假类型",
		    	                	selectList: [//当类型为select时，option配置   0：公章、1:手签章、2:人名章
		    	                	             {
		    	                	            	 value: "0",
		    	                	            	 text: "年休假"
		    	                	             },
		    	                	             {
		    	                	            	 value: "1",
		    	                	            	 text: "事假"
		    	                	             },
		    	                	             {
		    	                	            	 value: "2",
		    	                	            	 text: "病假"
		    	                	             },
		    	                	             {
		    	                	            	 value: "3",
		    	                	            	 text: "探亲假"
		    	                	             },
		    	                	             {
		    	                	            	 value: "4",
		    	                	            	 text: "婚假"
		    	                	             },
		    	                	             {
		    	                	            	 value: "5",
		    	                	            	 text: "（陪）产假"
		    	                	             },
		    	                	             {
		    	                	            	 value: "6",
		    	                	            	 text: "丧假"
		    	                	             },
		    	                	             {
		    	                	            	 value: "7",
		    	                	            	 text: "倒休假"
		    	                	             }
		    	                	             ],
		    	                	             must: true
		    	                },
		    	                {
		    	                	type: "date", //
		    	                	name: "startTime", //
		    	                	label: "开始时间", //
		    	                	dateFmt: "yyyy-MM-dd",
		    	                	defaultValue: new Date(),
		    	                	id: "startTime",
		    	                	must: true
		    	                },
		    	                {
		    	                	type: "date", //
		    	                	name: "endTime", //
		    	                	label: "结束时间", //
		    	                	dateFmt: "yyyy-MM-dd",
		    	                	defaultValue: new Date(),
		    	                	id: "endTime",
		    	                	must: true
		    	                },
		    	                {
		    	                	type: "date", //
		    	                	name: "terminateDate", //
		    	                	label: "销假日期", //
		    	                	dateFmt: "yyyy-MM-dd",
		    	                	defaultValue: new Date(),
		    	                	id: "terminateDate",
		    	                	must: true
		    	                },	
		    	                {
		    	                    type: "textarea", //
		    	                    name: "leaveReason", //
		    	                    label: "请假原由", //
		    	                    placeholder: ""
		    	                },
		    	                {
		    	                	type: "textarea",//
		    	                	label: "注",//
		    	                	defaultValue: "1）部门、中心、事业部领导以下员工01 事假：因事请假，一天之内的报本部门、中心、事业部负责人批准，两天（含）以上的报公司分管领导批准；02 病假：因病请假，五天之内的报本部门、中心、事业部负责人批准，六天以上的报公司分管领导批准；03 年休假、婚假、（陪）产假、丧假、倒休假：报部门、中心、事业部负责人批准；<br/>（2）部门、中心、事业部领导（含）以上员工因私请假，七天之内的报公司分管领导批准，七天以上的，党群工作部门报党委书记批准，其他部门报公司总经理批准；",
		    	                	immutableAdd: true
		    	                }]
		       }
		       /* {
		    	   name: "附件信息",
		    	   type: "2",
		    	   tbName: "",
		    	   tbIdName: "fileId",//附件表主键id名
		    	   conditions: [
		    	                {
		    	                	type: "upload",//
		    	                	name: "fileList",//附件表名
		    	                	label: "附件1",//
		    	                	btnName: "添加附件",
		    	                	projectName: "zjFlowMatter",
		    	                	uploadUrl: 'uploadFilesByFileName',
		    	                	fileType: ["jpg", "jpeg", "png", "gif", "docx", "xls", "doc", "ppt", "xlsx", "pptx", "xlsm", "pdf"]
		    	                }
		    	                ]
		       } */
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
				    					   body["apiBody"][key] = tabObjDatas.data[key]
				    				   }
				    				   //add by lny on 717 

				    				   body["title"] = tabObjDatas.data[flowFormJson.titleName];				
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
				    			   //add by lny on 717
				    			   body["apiName"] = "addZjFlowLeaveApply"//购置申请发起时调用
				    			   //add by lny on 717
				    		   }
				    		   //流程发起特殊代码---开始
				    		   layer.confirm("确定发起？", { icon: 0, }, function (index) {//流程发起请求
				    			   l.ajax("createOpenFlow", body, function (_d, _m, _s, _r) {
				    				   if (_s) {//发起成功，返回workId
				    					   loadFlow(_d.flowButtons, {
				    						   otherBody: {
				    							   title: body["title"],
				    							   flowId: flowId,//流程id
				    							   nodeId: _d.flowNode.nodeId,
				    							   trackId: _d.flowNode.trackId,
				    							   workId: _d.workId,
				    							   flowVars: _d.flowVars,
				    							   nodeVars: _d.nodeVars,
				    							   apih5FlowStatus: _d.apih5FlowStatus,
				    							   apiName: "updateZjFlowLeaveApply",
				    							   apiBody: { workId: _d.workId, apih5FlowStatus: _d.apih5FlowStatus }
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
		custom_close()
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

l.ajax('getLoginUserDetail',{},function (data,message,success) {
	if (success) {
		var _data = data[0];
		var _d = {
				oaDept: _data.oaDept,
				oaName: _data.oaName,
		};
		tabCons[0].setOpenData(_d);
	} else {
		layer.alert(message,{ icon: 0,},function (index) {
			layer.close(index);
		});
	}
})