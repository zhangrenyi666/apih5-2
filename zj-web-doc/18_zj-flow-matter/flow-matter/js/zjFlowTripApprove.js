var flowId = l.getUrlParam("flowId") || "zjTripApply"; //流程模版id
var code = l.getUrlParam("code");
var type = l.getUrlParam("type"); //详情需要将所有按钮隐藏 detail edit
var tripApplyId = l.getUrlParam("id") || "";
Lny.setCookie("code", code, 30);
var $tabSystem = $("#tab-system"); //模版顶级jq对象
var $tabBar = $('<div class="tabBar cl"></div>'); //tab按钮控制条
var tabCons = []; //tab内容页面组
var table, pagerDom, detailForm;
//根据flowId获取流程发起模版数据及数据
var flowFormJson = {
		name: "出差申请",
		titleName: "tripTitle",
		tabs: [
		       {
		    	   name: "基本信息",
		    	   type: "1",//普通tab页1，附件tab页2，列表tab页面3
		    	   isMain: "1",//是否为主表
		    	   tbName: "zjFlowTripApply",//表名
		    	   tbIdName: "tripApplyId",//表主键id
		    	   conditions: [
		    	                {
		    	                	type: "hidden",//
		    	                	name: "tripApplyId",//
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
		    	                	name: "position",//
		    	                	label: "职别",//
		    	                	placeholder: "职别",
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
		    	                	type: "text",//
		    	                	name: "tripPlace",//
		    	                	label: "出差地点",//
		    	                	placeholder: "出差地点",
		    	                	must: true
		    	                },
		    	                {
		    	                	type: "select",
		    	                	name: "vehicle",
		    	                	label: "交通工具",
		    	                	selectList: [//当类型为select时，option配置   0：公章、1:手签章、2:人名章
		    	                	             {
		    	                	            	 value: "0",
		    	                	            	 text: "汽车"
		    	                	             },
		    	                	             {
		    	                	            	 value: "1",
		    	                	            	 text: "火车"
		    	                	             },
		    	                	             {
		    	                	            	 value: "2",
		    	                	            	 text: "飞机"
		    	                	             },
		    	                	             {
		    	                	            	 value: "3",
		    	                	            	 text: "其他"
		    	                	             }
		    	                	             ],
		    	                	             must: true
		    	                },
		    	                {
		    	                	type: "text",//
		    	                	name: "traveller",//
		    	                	label: "出差人",//
		    	                	placeholder: "出差人",
		    	                	must: true
		    	                },
		    	                {
		    	                	type: "textarea", //
		    	                	name: "tripReason", //
		    	                	label: "出差事由", //
		    	                	placeholder: ""
		    	                },
		    	                {
		    	                	type: "textarea", //
		    	                	name: "flyReason", //
		    	                	label: "乘坐飞机原因", //
		    	                	placeholder: ""
		    	                },
		    	                {
		    	                	type: "textarea",//
		    	                	label: "注",//
		    	                	defaultValue: "01.北京市内出差：部门、中心、事业部领导以下员工报部门、中心、事业部负责人批准，部门、中心、事业部领导（含）以上员工报公司报分管领导审批；<br/>02.北京市以外地区出差：部门、中心领导以下员工出差3天以下（含3天）由所在部门、中心负责人批准；3天以上7天以下（含7天）由分管领导批准；7天以上，党群部门由公司党委书记批准，其他部门、中心由公司总经理批准。部门、中心领导（含）以上员工出差7天以下（含7天）由分管领导批准；7天以上，党群部门由公司党委书记批准，其他部门、中心由公司总经理批准； 事业部公司管干部正副职负责人出差，7天以下（含7天）由公司分管领导批准；7天以上，党群部门由公司党委书记批准，其他部门由公司总经理批准。事业部其他人员出差，1天以下（含1天）由所在部门处室负责人批准；2天以上7天以下（含7天）由事业部部门负责人批准；7天以上，党群部门由事业部党委书记批准，其他部门由事业部总经理批准。 加班：部门、中心、事业部领导以下员工报部门、中心、事业部负责人批准，部门、中心、事业部领导（含）以上员工报分管领导批准",
		    	                	immutableAdd: true
		    	                }]
		       },
		       {
		    	   //详情弹窗
		    	   name: "出差详情",
		    	   type: "3", //table是表格
		    	   tbName: "",
		    	   tbIdName: "fileId"
		       }
		       ]
};

$.each(flowFormJson.tabs, function(i, tabItem) {
	//第一次遍历flowFormJson.tabs
	var $tabBtn = $("<span>" + tabItem.name + "</span>"); //创建tab按钮$对象
	$tabBar.append($tabBtn); //向tab按钮控制条插入tab按钮
	var $tabCon = $('<div class="tabCon" id="tab' + i.toString() + '"></div>'); //创建tab内容页面$对象
	var customBtnGroup; //tab内容页面中表单的底部按钮组配置
	if (tabItem.isMain) {
		//如果是主表单
		customBtnGroup = {
				btns:[
		   {
			   name: "add",
			   label: '<i class="Hui-iconfont">&#xe600;</i> 确定'
		   },
		   {
			   name: "launch",
			   label: '<i class="Hui-iconfont">&#xe603;</i> 发起'
		   },
		   {
			   name: "cancel",
			   label: "取消"
		   }
		   ],
		   callback: function(dataName, obj) {
			   switch (dataName) {
			   case "add":
				   var body = {};
				   for (var j = 0; j < flowFormJson.tabs.length; j++) {
					   //第二次遍历flowFormJson.tabs，为了让return起作用所以不用$.each而采用for循环

					   var tabItemj = flowFormJson.tabs[j]; //模版中tabs数组的遍历元素数据对象
					   if (tabCons[j].getDatas) {
						   var tabObjDatas = tabCons[j].getDatas(); //tab内容页面组的遍历对象获取数据对象
						   if (tabObjDatas.err && tabObjDatas.err.length) {
							   //判断是否有错误（字段不能为空、超过个数限制等）
							   layer.alert( tabObjDatas.err.join("<br/>"),{ icon: 0 }, function(index) {
										   $tabSystem.Huitab({index: j});
										   layer.close(index);
									   }
							   );
							   return; //直接停止for循环，且for循环之后的代码也不执行
						   }
						   body = tabObjDatas.data;
					   }
				   }
				   l.ajax("addZjFlowTripApply", body, function(data, message, success) {
					   if (success) {
						   tripApplyId = data.tripApplyId;
						   pager.page("remote", { tripApplyId: tripApplyId });
						   layer.alert(message, { icon: 0 }, function(index) {
							   // parent.pager.page("remote");
							   layer.close(index);
							   $("#tab-system").Huitab({ index: 1});
						   });
					   }
				   });
				   break;
			   case "launch":
				   if (!tripApplyId) {
					   layer.alert("请先提交基本信息！", { icon: 0 }, function(index) {
						   layer.close(index);
						   $("#tab-system").Huitab({
							   index: 0
						   });
					   });
					   return;
				   }
				   var body = {
						   flowId: flowId, //流程id
						   tripApplyId: tripApplyId
				   };
				   for (var j = 0; j < flowFormJson.tabs.length; j++) {
					   //第二次遍历flowFormJson.tabs，为了让return起作用所以不用$.each而采用for循环
					   var tabItemj = flowFormJson.tabs[j]; //模版中tabs数组的遍历元素数据对象
					   if (tabCons[j].getDatas) {
						   var tabObjDatas = tabCons[j].getDatas(); //tab内容页面组的遍历对象获取数据对象
						   if (tabObjDatas.err.length) {
							   //判断是否有错误（字段不能为空、超过个数限制等）
							   layer.alert(tabObjDatas.err.join("<br/>"),{ icon: 0 },function(index) {
										   $tabSystem.Huitab({index: j});
										   layer.close(index);
									   }
							   );
							   return; //直接停止for循环，且for循环之后的代码也不执行
						   }
						   if (tabItemj.isMain) {
							   //如果是主表
							   tabObjDatas.data.tripApplyId = tripApplyId;
							   //给主表赋值
							   body["mainTableName"] = tabItemj.tbName;
							   body["mainTablePrimaryIdName"] = tabItemj.tbIdName;
							   body["mainTableDataObject"] = tabObjDatas.data;

							   body["apiBody"] = {};
							   //add by lny on 717
							   for (var key in tabObjDatas.data) {
								   body["apiBody"][key] = tabObjDatas.data[key];
							   }
							   //add by lny on 717

							   // body["title"] = tabObjDatas.data[flowFormJson.titleName];
							   body["title"] = flowFormJson.name;
						   } else {
							   //如果是普通类型子表，type==="1"，目前只有1和2
							   //给子表赋值-普通表
							   if (!body["subTableObject"]) {
								   body["subTableObject"] = {};
							   }

							   //add by lny on 717
							   for (var key in tabObjDatas.data) {
								   body["apiBody"][key] = tabObjDatas.data[key];
							   }
							   //add by lny on 717

							   body["subTableObject"][tabItemj.tbName] = {
									   subTablePrimaryIdName: tabItemj.tbIdName,
									   subTableType: tabItemj.type,
									   subTableDataObject: tabObjDatas.data
							   };
						   }

						   //add by lny on 717
						   body["apiName"] = "updateZjFlowTripApply"; //购置申请发起时调用
						   //add by lny on 717
					   }
				   }
				   //流程发起特殊代码---开始
				   layer.confirm("确定发起？", { icon: 0 }, function(index) {
					   //流程发起请求
					   l.ajax("createOpenFlow", body, function(_d, _m, _s, _r) {
						   if (_s) {
							   //发起成功，返回workId
							   loadFlow(_d.flowButtons, {
								   otherBody: {
									   title: body["title"],
									   nodeId: _d.flowNode.nodeId,
									   trackId: _d.flowNode.trackId,
									   workId: _d.workId,
									   flowVars: _d.flowVars,
									   nodeVars: _d.nodeVars,
									   apih5FlowStatus: _d.apih5FlowStatus,
									   apiName: "updateZjFlowTripApply",
									   apiBody: {
										   workId: _d.workId,
										   apih5FlowStatus: _d.apih5FlowStatus,
										   tripApplyId: tripApplyId
									   }
								   },
								   endFn: function(buttonClass) {
									   obj.close();
								   },
								   callback: function(flowObj) {
									   flowObj.flowSelectOpen(0);
								   }
							   });
						   }
					   });
					   layer.close(index);
					   //  parent.layer.close(parent.myOpenLayer);
				   });
				   //流程发起特殊代码---结束
				   break;
			   case "cancel":
			   default:
				   obj.close();
			   break;
			   }
		   }
		};
	} else {
		customBtnGroup = {
				btns: [],
				callback: function(dataName, obj) {}
		};
	}
	if (tabItem.type === "3") {
		//表格
		var $con = $('<div class="page-container"></div>'); //
		var $table = $('<table id="table" class="table table-border table-bordered table-bg table-hover"></table>');
		var $cl = $('<div class="cl"></div>');
		pagerDom = $('<div id="pager" class="m-pagination f-r"></div>');
		var $btnCon = $('<div class="f-l mt-10"></div>');
		var $add = $('<button data-name="add" data-type="TzLand" class="btn size-S S ml-10  btn-primary" type="button"><i class="Hui-iconfont">&#xe600;</i> 新增</button>');
		var $del = $('<button data-name="del" data-type="TzLand" class="btn size-S S ml-10  btn-primary" type="button"><i class="Hui-iconfont">&#xe6e2;</i> 删除</button>');

		$btnCon.append($add);
		$btnCon.append($del);    
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
			            	 width: 13,
			            	 title: '<input type="checkbox">',
			            	 render: function(data) {
			            		 return (
			            				 '<input type="checkbox" name="checkbox" data-rowIndex="' +
			            				 data +
			            				 '" >'
			            		 );
			            	 }
			             },
			             {
			            	 targets: [1],
			            	 data: "rowIndex",
			            	 title: "No.",
			            	 width: 25,
			            	 render: function(data) {
			            		 return data + 1;
			            	 }
			             },
			             {
			            	 targets: [2], //第几列
			            	 data: "startTime", //接口对应字段
			            	 title: "开始时间", //表头名
			            	 defaultContent: "-", //默认显示
			            	 render: function(data) {
			            		 return data ? l.dateFormat(new Date(data), "yyyy-MM-dd") : "-";
			            	 }
			             },
			             {
			            	 targets: [3], //第几列
			            	 data: "endTime", //接口对应字段
			            	 title: "结束时间", //表头名
			            	 defaultContent: "-", //默认显示
			            	 render: function(data) {
			            		 return data ? l.dateFormat(new Date(data), "yyyy-MM-dd") : "-";
			            	 }
			             },
			             {
			            	 targets: [4], //第几列
			            	 data: "startPlace", //接口对应字段
			            	 title: "开始地点", //表头名
			            	 defaultContent: "-" //默认显示
			             },       
			             {
			            	 targets: [5], //第几列
			            	 data: "endPlace", //接口对应字段
			            	 title: "结束地点", //表头名
			            	 defaultContent: "-" //默认显示
			             }
			             ]
		});
		$tabCon.append($con);
		tabCons[i] = $tabCon;
	} else {
		detailForm = tabCons[i] = $tabCon.detailLayer({
			customBtnGroup: customBtnGroup,
			conditions: tabItem.conditions
		});
	}
	tabCons[i].close = function () {
		custom_close()
	}
});

$tabSystem.append($tabBar).append(tabCons).Huitab({index: 0});

//检查项
var pager = pagerDom.page({
	remote: {
		//ajax请求配置
		url: l.getApiUrl("getZjFlowTripApplyDetailList"),
		params: {
			tripApplyId: tripApplyId
		},
		success: function(result) {
			if (result.success) {
				var data = result.data;
				$.each(data, function(index, item) {
					item.rowIndex = index;
				});
				table.clear().rows.add(data).draw();
			} else {
				layer.alert(result.message, { icon: 0 }, function(index) {
					layer.close(index);
				});
			}
		}
	}
});
loadPage();
function loadPage() {
	if (tripApplyId) {
		l.ajax("getZjFlowTripApplyDetail",{ tripApplyId: tripApplyId },function(data, message, success) {
			if (success) {
				detailForm.setOpenData(data);
			}
		}
		);
	} else {
		detailForm.setOpenData({ memberList: { oaMemberList: [] } });
	}
}

var detailLayer = $("#detailLayer").detailLayer({
	layerArea: ["100%", "100%"],
	conditions: [
	             {
	            	 type: "hidden", //五种形式：text,select,date,hidden,textarea,
	            	 name: "tripApplyDetailId" //输入字段名
	             },
	             {
	            	 type: "hidden", //五种形式：text,select,date,hidden,textarea,
	            	 name: "tripApplyId" //输入字段名
	             },    
	             {
	            	 type: "date", //
	            	 name: "startTime", //
	            	 label: "开始时间", //
	            	 dateFmt: "yyyy-MM-dd",
	            	 defaultValue: new Date(),
	            	 id: "startTime"
	             },
	             {
	            	 type: "date", //
	            	 name: "endTime", //
	            	 label: "结束时间", //
	            	 dateFmt: "yyyy-MM-dd",
	            	 defaultValue: new Date(),
	            	 id: "endTime"
	             },
	             {
	            	 type: "text", //
	            	 name: "startPlace", //
	            	 label: "开始地点", //
	            	 placeholder: "请输入"
	             },
	             {
	            	 type: "text", //
	            	 name: "endPlace", //
	            	 label: "结束地点", //
	            	 placeholder: "请输入"
	             }
	             ],
	             onAdd: function(obj, _params) {
	            	 l.ajax("addZjFlowTripApplyDetail", _params, function(data, message, success) {
	            		 if (success) {
	            			 pager.page("remote", { tripApplyId: tripApplyId });
	            			 layer.alert(message, { icon: 0 }, function(index) {
	            				 layer.close(index);
	            				 obj.close();
	            			 });
	            		 }
	            	 });
	             }
});
$("body").on("click", "button", function() {
	var checkedData = l.getTableCheckedData(table);
	var name = $(this).attr("data-name");
	switch (name) {
	case "add":
		if (tripApplyId) {
			detailLayer.open({ isAdd: true, tripApplyId: tripApplyId });
		} else {
			layer.alert("请先填写出差申请点击确认后再新增出差详情！",{ icon: 0 },function(index) {
				layer.close(index);
				$("#tab-system").Huitab({index: 0});
			}
			);
		}
		break;
	case "del":
		if (checkedData.length == 0) {
			layer.alert("未选择任何项", { icon: 0 }, function(index) {
				layer.close(index);
			});
		} else {
			layer.confirm("确定删除？", { icon: 0 }, function(index) {
				l.ajax("batchDeleteUpdateZjFlowTripApplyDetail", checkedData, function(data,success,message) {
					if (success) {
						pager.page("remote");
					}
				});
				layer.close(index);
			});
		}
		break;
	case "close":
		parent.layer.close(parent.myOpenLayer);
		break;
	}
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