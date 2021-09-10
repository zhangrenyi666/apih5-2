var flowId = l.getUrlParam("flowId") || "zjjgGoAbroad"; //流程模版id
var code = l.getUrlParam("code");
var type = l.getUrlParam("type"); //详情需要将所有按钮隐藏 detail edit
var goAbroadApplyId = l.getUrlParam("id") || "";
Lny.setCookie("code", code, 30);
var $tabSystem = $("#tab-system"); //模版顶级jq对象
var $tabBar = $('<div class="tabBar cl"></div>'); //tab按钮控制条
var tabCons = []; //tab内容页面组
var table, pagerDom, detailForm;
var $tabTitle = $("#tab-title"); //模版title
$tabTitle.html("因私出国（机关）申请");
//根据flowId获取流程发起模版数据及数据
var flowFormJson = {
	name: "因私出国（机关）申请",
	titleName: "zjjgGoAbroad",
	tabs: [
		{
			name: "基本信息",
			type: "1",//普通tab页1，附件tab页2，列表tab页面3
			isMain: "1",//是否为主表
			tbName: "zjFlowGoAbroadApply",//表名
			tbIdName: "goAbroadApplyId",//表主键id
			conditions: [
				{
					type: "hidden", //五种形式：text,select,date,hidden,textarea,
					name: "goAbroadApplyId" //输入字段名
				},
				{
					type: "text", //
					name: "name", //
					label: "姓名", //
					placeholder: "请输入姓名",
					must: true
				},
				{
					type: "hidden", //
					name: "typeFlag", //
					label: "类别标识", //
					defaultValue: "1",
					must: true
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
					type: "select", //
					name: "sex", //
					label: "性别", //
					selectList: [//当类型为select时，option配置   0：公章、1:手签章、2:人名章
						{
							value: "0",
							text: "男"
						},
						{
							value: "1",
							text: "女"
						}
					],
					must: true
				},
				{
					type: "date", //
					name: "birth", //
					label: "出生年月", //
					dateFmt: "yyyy-MM-dd",
					defaultValue: new Date(),
					id: "birth",
					must: true
				},
				{
					type: "text", //
					name: "nation", //
					label: "民族", //
					placeholder: "请输入民族",
					must: true
				},
				{
					type: "text", //
					name: "nativePlace", //
					label: "籍贯", //
					placeholder: "请输入籍贯",
					must: true
				},
				{
					type: "text", //
					name: "politicsPlace", //
					label: "政治面貌", //
					placeholder: "请输入政治面貌",
					must: true
				},
				{
					type: "text", //
					name: "workUnit", //
					label: "工作单位", //
					placeholder: "请输入工作单位",
					must: true
				},
				{
					type: "text", //
					name: "duty", //
					label: "职务", //
					placeholder: "请输入职务",
					must: true
				},
				{
					type: "text", //
					name: "professional", //
					label: "职称", //
					placeholder: "请输入职称",
					must: true
				},
				{
					type: "text", //
					name: "residentAddress", //
					label: "户口所在地", //
					placeholder: "请输入户口所在地",
					must: true
				},
				{
					type: "text", //
					name: "idCard", //
					label: "身份证号码", //
					placeholder: "请输入身份证号码",
					must: true
				},
				{
					type: "text", //
					name: "passportNumber", //
					label: "护照号码", //
					placeholder: "请输入护照号码",
					must: true
				},
				{
					type: "textarea", //
					name: "goAbroadReason", //
					label: "出国（境）事由", //
					placeholder: "请输入出国（境）事由",
					must: true
				},
				{
					type: "text", //
					name: "goCountry", //
					label: "前往国家/地区", //
					placeholder: "请输入前往国家/地区",
					must: true
				},
				{
					type: "text", //
					name: "expectedTravel", //
					label: "预计行程", //
					placeholder: "请输入预计行程",
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
					type: "number", //
					name: "peersNumber", //
					label: "同行人数", //
					placeholder: "请输入同行人数",
					must: true
				}
			]
		},
		{
			//详情弹窗
			name: "同行人员信息",
			type: "3", //table是表格
			tbName: "",
			tbIdName: "fileId"
		}
	]
};

$.each(flowFormJson.tabs, function (i, tabItem) {
	//第一次遍历flowFormJson.tabs
	var $tabBtn = $("<span>" + tabItem.name + "</span>"); //创建tab按钮$对象
	$tabBar.append($tabBtn); //向tab按钮控制条插入tab按钮
	var $tabCon = $('<div class="tabCon" id="tab' + i.toString() + '"></div>'); //创建tab内容页面$对象
	var customBtnGroup; //tab内容页面中表单的底部按钮组配置
	if (tabItem.isMain) {
		//如果是主表单
		customBtnGroup = {
			btns: [
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
			callback: function (dataName, obj) {
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
									layer.alert(tabObjDatas.err.join("<br/>"), { icon: 0 }, function (index) {
										$tabSystem.Huitab({ index: j });
										layer.close(index);
									}
									);
									return; //直接停止for循环，且for循环之后的代码也不执行
								}
								body = tabObjDatas.data;
							}
						}
						l.ajax("addZjFlowGoAbroadApply", body, function (data, message, success) {
							if (success) {
								goAbroadApplyId = data.goAbroadApplyId;
								pager.page("remote", { goAbroadApplyId: goAbroadApplyId });
								layer.alert(message, { icon: 0 }, function (index) {
									// parent.pager.page("remote");
									layer.close(index);
									$("#tab-system").Huitab({ index: 1 });
								});
							}
						});
						break;
					case "launch":
						if (!goAbroadApplyId) {
							layer.alert("请先提交基本信息！", { icon: 0 }, function (index) {
								layer.close(index);
								$("#tab-system").Huitab({
									index: 0
								});
							});
							return;
						}
						var body = {
							flowId: flowId, //流程id
							goAbroadApplyId: goAbroadApplyId
						};
						for (var j = 0; j < flowFormJson.tabs.length; j++) {
							//第二次遍历flowFormJson.tabs，为了让return起作用所以不用$.each而采用for循环
							var tabItemj = flowFormJson.tabs[j]; //模版中tabs数组的遍历元素数据对象
							if (tabCons[j].getDatas) {
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
									//如果是主表
									tabObjDatas.data.goAbroadApplyId = goAbroadApplyId;
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
									 body["title"] = tabObjDatas.data['name'] + '-  因私出国（机关）申请';
									//body["title"] = flowFormJson.name;
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
								body["apiName"] = "updateZjFlowGoAbroadApply"; //购置申请发起时调用
								//add by lny on 717
							}
						}
						//流程发起特殊代码---开始
						layer.confirm("确定发起？", { icon: 0 }, function (index) {
							//流程发起请求
							l.ajax("createOpenFlow", body, function (_d, _m, _s, _r) {
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
											apiName: "updateZjFlowGoAbroadApply",
											apiBody: {
												workId: _d.workId,
												apih5FlowStatus: _d.apih5FlowStatus,
												goAbroadApplyId: goAbroadApplyId
											}
										},
										endFn: function (buttonClass) {
											obj.close();
										},
										callback: function (flowObj) {
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
			callback: function (dataName, obj) { }
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
					render: function (data) {
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
					render: function (data) {
						return data + 1;
					}
				},
				{
					targets: [2], //第几列
					data: "appellation", //接口对应字段
					title: "称谓", //表头名
					defaultContent: "-" //默认显示
				},
				{
					targets: [3], //第几列
					data: "infoName", //接口对应字段
					title: "姓名", //表头名
					defaultContent: "-" //默认显示
				},
				{
					targets: [4], //第几列
					data: "infoAge", //接口对应字段
					title: "年龄", //表头名
					defaultContent: "-" //默认显示
				},
				{
					targets: [5], //第几列
					data: "infoPoliticsPlace", //接口对应字段
					title: "政治面貌", //表头名
					defaultContent: "-" //默认显示
				},
				{
					targets: [6], //第几列
					data: "infoWorkUnitAndDuty", //接口对应字段
					title: "工作单位及职务", //表头名
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

$tabSystem.append($tabBar).append(tabCons).Huitab({ index: 0 });

//检查项
var pager = pagerDom.page({
	remote: {
		//ajax请求配置
		url: l.getApiUrl("getZjFlowGoAbroadInfoList"),
		params: {
			goAbroadApplyId: goAbroadApplyId
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
loadPage();
function loadPage() {
	if (goAbroadApplyId) {
		l.ajax("getZjFlowGoAbroadApplyDetail", { goAbroadApplyId: goAbroadApplyId }, function (data, message, success) {
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
			name: "goAbroadInfoId" //输入字段名
		},
		{
			type: "hidden", //五种形式：text,select,date,hidden,textarea,
			name: "goAbroadApplyId" //输入字段名
		},
		{
			type: "text", //
			name: "appellation", //
			label: "称谓", //
			placeholder: "请输入"
		},
		{
			type: "text", //
			name: "infoName", //
			label: "姓名", //
			placeholder: "请输入"
		},
		{
			type: "number", //
			name: "infoAge", //
			label: "年龄", //
			placeholder: "请输入"
		},
		{
			type: "text", //
			name: "infoPoliticsPlace", //
			label: "政治面貌", //
			placeholder: "请输入"
		},
		{
			type: "text", //
			name: "infoWorkUnitAndDuty", //
			label: "工作单位及职务", //
			placeholder: "请输入"
		}
	],
	onAdd: function (obj, _params) {
		l.ajax("addZjFlowGoAbroadInfo", _params, function (data, message, success) {
			if (success) {
				pager.page("remote", { goAbroadApplyId: goAbroadApplyId });
				layer.alert(message, { icon: 0 }, function (index) {
					layer.close(index);
					obj.close();
				});
			}
		});
	}
});
$("body").on("click", "button", function () {
	var checkedData = l.getTableCheckedData(table);
	var name = $(this).attr("data-name");
	switch (name) {
		case "add":
			if (goAbroadApplyId) {
				detailLayer.open({ isAdd: true, goAbroadApplyId: goAbroadApplyId });
			} else {
				layer.alert("请先填写出国信息申请点击确认后再新增同行人员信息！", { icon: 0 }, function (index) {
					layer.close(index);
					$("#tab-system").Huitab({ index: 0 });
				}
				);
			}
			break;
		case "del":
			if (checkedData.length == 0) {
				layer.alert("未选择任何项", { icon: 0 }, function (index) {
					layer.close(index);
				});
			} else {
				layer.confirm("确定删除？", { icon: 0 }, function (index) {
					l.ajax("batchDeleteUpdateZjFlowGoAbroadInfo", checkedData, function (data, success, message) {
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