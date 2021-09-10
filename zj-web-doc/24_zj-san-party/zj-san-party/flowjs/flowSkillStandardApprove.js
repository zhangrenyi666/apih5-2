var flowId = l.getUrlParam('id') || "sanDjProfessionalTitle";//流程模版id
var code = l.getUrlParam('code');
Lny.setCookie('code', code, 30);
var $tabSystem = $("#tab-system")//模版顶级jq对象
var $tabBar = $('<div class="tabBar cl"></div>');//tab按钮控制条
var tabCons = []//tab内容页面组
var table, pagerDom, detailForm;
//根据flowId获取流程发起模版数据及数据
var flowFormJson = {
	name: "",
	tabs: []
}
var $tabTitle = $("#tab-title")//模版title
$tabTitle.html("施工工法、技术标准奖励申请")

switch (flowId) {
	case "sanDjProfessionalTitle":
		flowFormJson = {//流程模版json（升级版：根据流程id动态获取，当前前台写死）
			name: "施工工法、技术标准奖励申请",
			titleName: "zcmc",
			tabs: [
				{
					name: "基本信息",
					type: "1",
					isMain: "1",
					tbName: "zjSanDjFlowSkillStandard",
					tbIdName: "skillStandardId",
					conditions: [
						{
							type: "hidden",//五种形式：text,select,date,hidden,textarea,
							name: "skillStandardId",//输入字段名
						},
						{
							type: "text",//
							name: "proName",//
							label: "项目名称",//
							immutableAdd: true
						},
						{
							type: "text",//
							name: "duty",//
							label: "职务",//
							immutableAdd: true
						},
						{
		    	                	type: "linkage",
		    	                	name: "联动组",
		    	                	children: {
		    	                		type: "selectSearch",
		    	                		name: "gradeStandardId",
		    	                		label: "级别",
		    	                		placeholder: '请选择',
										must: true,
		    	                		ajax: {
		    	                			api: "getZjSanDjGradeStandardList9",
		    	                			valueName: "recordId",
		    	                			textName: "scoreName",
		    	                			searchParamsField: 'scoreName' //搜索时给后台的搜索文字字段名
		    	                		},
		    	                		children: {
		    	                			type: "selectSearch",
		    	                			name: "classId", 
		    	                			label: "类别",
		    	                			placeholder: '请选择',
											must: true,
		    	                			immutableAdd: true,
		    	                			ajax: {
		    	                				api: "getZjSanDjGradeStandardListTwo",
		    	                				valueName: "recordId",
		    	                				textName: "className",
		    	                				searchParamsField: 'className', //搜索时给后台的字段
		    	                				parentParamsField: 'pId', //搜索时给后台的父级值的字段名 
		    	                			},
		    	                			children: {
		    	                				type: "selectSearch",
		    	                				name: "scopeId",
		    	                				label: "奖励范围",
		    	                				placeholder: '请选择',
												must: true,
		    	                				immutableAdd: true,  
		    	                				ajax: {
		    	                					api: "getZjSanDjGradeStandardSelectAllListThreeOther",
		    	                					valueName: "recordId",
		    	                					textName: "scope",
		    	                					searchParamsField: 'scope', //搜索时给后台的字段
		    	                					parentParamsField: 'recordId', //搜索时给后台的父级值的字段名 
		    	                					setChildrenValue:{//子集是非下拉时才能有此属性
		    	                						api: "getZjSanDjGradeStandardSelectAllListFour",
		    	                						parentParamsField: 'recordId', //搜索时给后台的父级值的字段名 
		    	                						textName: "score",
		    	                					}
		    	                				},
		    	                				children: {
		    	                					type: "text",
		    	                					name: "score", 
		    	                					label: "积分",
		    	                					placeholder: '请输入',
													must: true,
		    	                					immutableAdd: true,
		    	                				}						
		    	                			}
		    	                		}
		    	                	},
		    	                },
						{
							type: "textarea",//
							name: "content",//
							label: "内容",//
							placeholder: "请输入内容",
							must: true
						},
						{
							type: "date",//
							name: "date",//
							label: "时间",//
							dateFmt: "yyyy-MM-dd",
							defaultValue: new Date(),
							id: "date",
							must: true
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
							name: "fileList",//
							label: "附件1",//
							btnName: "添加附件",
							projectName: "zj-san-party",
							uploadUrl: 'uploadFilesByFileName',
							fileType: ["jpg", "jpeg", "png", "gif", "docx", "xls", "doc", "ppt", "xlsx", "pptx", "xlsm", "pdf"]
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
							flowId: flowId,//流程id
							personRegisterId: personRegisterId
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
								tabObjDatas.data.personRegisterId = personRegisterId;
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
							body["apiName"] = "addZjSanDjFlowSkillStandard"//新增
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
											apiName: "updateZjSanDjFlowSkillStandard",
											apiBody: { workId: _d.workId, apih5FlowStatus: _d.apih5FlowStatus,fileList:_d.apiBody.fileList }
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
		detailForm = tabCons[i] = $tabCon.detailLayer({
			customBtnGroup: customBtnGroup,
			conditions: tabItem.conditions
		});
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

detailForm.find("form").prepend(
	$(
		'<div class="row cl"><label class="form-label col-2 f-l"><i style="color:red;">* </i>申请人：</label><div class="col-4 f-l"><input type="text" class="input-text SearchSelect" onfocus="SearchSelect({ apiName: \'getZjSanDjPersonRegisterList\', otherKey: \'projectState\' })" value="" name="perName"></div></div>'
	)
);
detailForm.setOpenData({ memberList: { oaMemberList: [] } });

var searchSelectDiv;
var curELe;
var searchSelectDatas = {};
var searchSelectErr = false;
/**
 * addEventlistener兼容函数
 * ie9以上正常使用addEventlistener函数
 * ie7、ie8用传递的function的function.prototype储存经过处理的事件
 * function.prototype["_" + type]：记录处理后的信息
 * function.prototype["_" + type]._function <function>：经过处理的事件
 * function.prototype["_" + type]._element  <array>   ：已经绑定的dom
 */
var ua = navigator.userAgent.toLowerCase();
var isIE = /msie/.test(ua);
/*** addEventlistener ***/
var addListener = (function () {
	if (document.addEventListener) {
		/* ie9以上正常使用addEventListener */
		return function (element, type, fun, useCapture) {
			element.addEventListener(type, fun, useCapture ? useCapture : false);
		};
	} else {
		/* ie7、ie8使用attachEvent */
		return function (element, type, fun) {
			type = type !== "input" ? type : "propertychange";
			if (!fun.prototype["_" + type]) {
				/* 该事件第一次绑定 */
				fun.prototype["_" + type] = {
					_function: function (event) {
						fun.call(element, event);
					},
					_element: [element]
				};
				element.attachEvent("on" + type, fun.prototype["_" + type]._function);
			} else {
				/* 该事件被绑定过 */
				var s = true;
				// 判断当前的element是否已经绑定过该事件
				for (var i in fun.prototype["_" + type]._element) {
					if (fun.prototype["_" + type]._element[i] === element) {
						s = false;
						break;
					}
				}
				// 当前的element没有绑定过该事件
				if (s === true) {
					element.attachEvent("on" + type, fun.prototype["_" + type]._function);
					fun.prototype["_" + type]._element.push(element);
				}
			}
		};
	}
})();
/*** removeEventlistener ***/
var removeListener = (function () {
	if (document.addEventListener) {
		/* ie9以上正常使用removeEventListener */
		return function (element, type, fun) {
			element.removeEventListener(type, fun);
		};
	} else {
		/* ie7、ie8使用detachEvent */
		return function (element, type, fun) {
			type = type !== "input" ? type : "propertychange";
			element.detachEvent("on" + type, fun.prototype["_" + type]._function);
			if (fun.prototype["_" + type]._element.length === 1) {
				// 该事件只有一个element监听，删除function.prototype["_" + type]
				delete fun.prototype["_" + type];
			} else {
				// 该事件只有多个element监听，从function.prototype["_" + type]._element数组中删除该element
				for (var i in fun.prototype["_" + type]._element) {
					if (fun.prototype["_" + type]._element[i] === element) {
						fun.prototype["_" + type]._element.splice(i, 1);
						break;
					}
				}
			}
		};
	}
})();
function setSearchSelect(searchSelectData) {
	if (searchSelectData) {
		personRegisterId = searchSelectData.personRegisterId
		curELe.value = searchSelectData[curELe.name] || "";
		searchSelectDatas[curELe.name] = searchSelectData;
	} else {
		delete searchSelectDatas[curELe.name];
	}
	var selectData = searchSelectDatas[curELe.name] || {};
	var formData = detailForm.getDatas().data;
	var conditions = detailForm.conditions;
	if (curELe.name === "perName") {
		$("input[name=schemeNumber]").val("");
	}
	function isImm(k, arrs) {
		var is = false;
		for (var index = 0; index < arrs.length; index++) {
			if (arrs[index].name === k) {
				is = arrs[index].immutableAdd || false;
				break;
			}
		}
		return is;
	}
	var newData = {};
	for (var key in formData) {
		if (isImm(key, conditions)) {
			newData[key] = selectData[key];
		} else {
			newData[key] = formData[key];
		}
	}

	detailForm.setOpenData(newData);
	closeSearchSelect();
}
function blurSearchSelect() {
	var event = this.event || window.event;
	var target = event.target || event.srcElement; //获取document 对象的引用
	if (target !== curELe) {
		if (curELe && curELe.value) {
			searchSelectErr = true;
			if (confirm("未从列表中选取项目！")) {
				curELe.value = searchSelectDatas[curELe.name][curELe.name] || "";
			}
		} else {
			setSearchSelect();
		}
	}
}
function closeSearchSelect() {
	searchSelectErr = false;
	searchSelectDiv.style.display = "none";
	curELe = null;
	removeListener(document, "mousedown", blurSearchSelect);
}
function SearchSelect(options) {
	options = options || { apiName: "api", otherKey: "other" };
	var apiName = options.apiName || "api";
	var otherKey = options.otherKey || "other";
	var otherValue = getOtherValue(otherKey);

	var event = this.event || window.event;
	var target = event.target || event.srcElement; //获取document 对象的引用
	if (curELe === target && !searchSelectErr) {
		return;
	}
	if (searchSelectErr) {
		if (curELe !== target) {
			target.blur();
		}
		return;
	}
	curELe = target;
	searchSelectDatas[curELe.name] = {};
	var tpos = getPos(curELe);
	function getPos(ele) {
		var pEle = ele.offsetParent;
		if (pEle.nodeName !== "BODY") {
			return {
				top: ele.offsetTop + getPos(pEle).top,
				left: ele.offsetLeft + getPos(pEle).left
			};
		} else {
			return {
				top: ele.offsetTop,
				left: ele.offsetLeft
			};
		}
	}
	if (!searchSelectDiv) {
		searchSelectDiv = document.createElement("div");
		var iframe = document.createElement("iframe");
		searchSelectDiv.appendChild(iframe);
		document.body.appendChild(searchSelectDiv);
	}
	searchSelectDiv.style.display = "block";
	searchSelectDiv.style.position = "absolute";
	searchSelectDiv.style.top = tpos.top + curELe.offsetHeight + "px";
	searchSelectDiv.style.left = tpos.left + "px";
	searchSelectDiv.style.zIndex = "100006";
	var searchSelectiframe = searchSelectDiv.children[0];
	searchSelectiframe.setAttribute("hideFocus", true, 0);
	searchSelectiframe.setAttribute("width", "240px", 0);
	searchSelectiframe.setAttribute("height", "200px", 0);
	searchSelectiframe.setAttribute("frameborder", "0", 0);
	searchSelectiframe.setAttribute("border", "0", 0);
	searchSelectiframe.setAttribute("scrolling", "no", 0);
	searchSelectiframe.setAttribute(
		"src",
		"searchSelect.html?otherKey=" +
		otherKey +
		"&otherValue=" +
		otherValue +
		"&keywordName=" +
		curELe.name +
		"&apiName=" +
		apiName +
		"&keyword=",
		0
	);
	function inputFun() {
		searchSelectErr = false;
		searchSelectiframe.setAttribute(
			"src",
			"searchSelect.html?otherKey=" +
			otherKey +
			"&otherValue=" +
			otherValue +
			"&keywordName=" +
			curELe.name +
			"&apiName=" +
			apiName +
			"&keyword=" +
			curELe.value,
			0
		);
	}
	addListener(curELe, "input", inputFun);
	addListener(document, "mousedown", blurSearchSelect);
}
function getOtherValue(otherKey) {
	switch (otherKey) {
		case "projectState":
			return "2";
			break;
		case "personRegisterId":
			return detailForm.getDatas().data.personRegisterId;
			break;
		default:
			return "";
			break;
	}
}
