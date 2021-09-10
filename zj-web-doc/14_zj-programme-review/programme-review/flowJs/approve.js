var flowId = l.getUrlParam('flowId') || "";//流程模版id
var code = l.getUrlParam('code');
Apih5.setCookie('code', code, 30);
var launchId = l.getUrlParam("id") || "";
var table, detailForm;
var $tabSystem = $("#tab-system")//模版顶级jq对象
var $tabBar = $('<div class="tabBar cl"></div>');//tab按钮控制条
var tabCons = [];//tab内容页面组
//根据flowId获取流程发起模版数据及数据
var flowFormJson = {
	name: "",
	tabs: []
};
var $tabTitle = $("#tab-title");//模版title
$tabTitle.html("方案评审发起");
flowFormJson = {//流程模版json（升级版：根据流程id动态获取，当前前台写死）
	name: "方案评审发起",
	titleName: "faps",
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
					"lineNum": 0
				},
				{
					type: "text",//五种形式：text,select,date,hidden,textarea,
					name: "schemeName",//输入字段名
					label: "方案名称",
					immutableAdd: true,
					"lineNum": 0
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
					name: "schemeLevel",
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
					type: "hidden",//五种形式：text,select,date,hidden,textarea,
					name: "launchId",//输入字段名
					label: "方案审批id",
					immutableAdd: true
				},
				{
					type: "hidden",//五种形式：text,select,date,hidden,textarea,
					name: "schemeId",//输入字段名
					label: "方案id",
					immutableAdd: true
				},
				{
					type: "hidden",//五种形式：text,select,date,hidden,textarea,
					name: "detailedListId",//输入字段名
					label: "清单id",
					immutableAdd: true
				},
				{
					type: "hidden",//五种形式：text,select,date,hidden,textarea,
					name: "reviewState",//输入字段名
					label: "评审状态",
					immutableAdd: true
				}

			]
		},
		{
			name: "附件信息",
			type: "2",
			tbName: "",
			tbIdName: "recordid",// fileId
			conditions: [
				{
					type: "upload",//
					name: "imageList",//
					label: "附件1",//
					btnName: "添加附件",
					projectName: "zj-assets-haiwei",
					uploadUrl: 'uploadFilesByFileName',
					fileType: ["jpg", "jpeg", "png", "gif", "docx", "xls", "doc", "ppt", "xlsx", "pptx", "xlsm", "pdf"]
				}
			]
		}
	]
};
l.ajax("getzjPrProgrammeLaunchDetail", { launchId: launchId }, function (data) {
	$.each(flowFormJson.tabs, function (i, tabItem) {//第一次遍历flowFormJson.tabs
		var $tabBtn = $('<span>' + tabItem.name + '</span>');//创建tab按钮$对象
		$tabBar.append($tabBtn);//向tab按钮控制条插入tab按钮
		var $tabCon = $('<div class="tabCon" id="tab' + i.toString() + '"></div>');//创建tab内容页面$对象
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
								var tabItemj = flowFormJson.tabs[j];//模版中tabs数组的遍历元素数据对象
								var tabObjDatas = tabCons[j].getDatas();//tab内容页面组的遍历对象获取数据对象
								if (tabObjDatas.err.length) {//判断是否有错误（字段不能为空、超过个数限制等）
									layer.alert(tabObjDatas.err.join("<br/>"), { icon: 0 }, function (index) {
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
									body["apiBody"] = {};
									//add by apih5 on 717
									for (var key in tabObjDatas.data) {
										body["apiBody"][key] = tabObjDatas.data[key];
									}
									//add by apih5 on 717 
									// title: ['applyUserId', 'sendTime', '用印申请'], //标题字段
									// body["title"] = tabObjDatas.data[flowFormJson.titleName];
									var now = new Date();
									var y = now.getFullYear();
									var m = now.getMonth() + 1;
									var d = now.getDate();

									var h = now.getHours();
									var mm = now.getMinutes();
									var s = now.getSeconds();
									var formatDate = y + '-' + m + '-' + d + ' ' + h + ':' + mm + ':' + s;
									body["title"] = tabObjDatas.data['meetingRoomTitle'];
									var schemeLevel = tabObjDatas.data.schemeLevel;
									if (schemeLevel == "2") {
										body["flowId"] = "zjProLaunchFlowByOne";
									} else if (schemeLevel == "1") {
										body["flowId"] = "zjProLaunchFlowByTwo";
									} else if (schemeLevel == "0") {
										body["flowId"] = "zjProLaunchFlowByThree";
									}
								} else if (tabItemj.type === "2") {//如果是附件类型子表，type==="2"
									//给子表赋值-附件表
									if (!body["subTableObject"]) { body["subTableObject"] = {} }
									for (var key in tabObjDatas.data) {
										var subTableDataObject = tabObjDatas.data[key];
										//add by apih5 on 717
										body["apiBody"][key] = tabObjDatas.data[key];
										//add by apih5 on 717										  										   										   										   
										body["subTableObject"][key] = { subTablePrimaryIdName: tabItemj.tbIdName, subTableType: tabItemj.type, subTableDataObject: subTableDataObject };
									}
								} else {//如果是普通类型子表，type==="1"，目前只有1和2
									//给子表赋值-普通表
									if (!body["subTableObject"]) { body["subTableObject"] = {} }
									//add by apih5 on 717
									for (var key in tabObjDatas.data) {
										body["apiBody"][key] = tabObjDatas.data[key];
									}
									//add by apih5 on 717
									body["subTableObject"][tabItemj.tbName] = { subTablePrimaryIdName: tabItemj.tbIdName, subTableType: tabItemj.type, subTableDataObject: tabObjDatas.data };
								}
								//add by apih5 on 717
								body["apiName"] = "addZjPrProgrammeLaunchFlow";//方案评审发起时调用
								//add by apih5 on 717
							}
							//流程发起特殊代码---开始
							layer.confirm("确定发起？", { icon: 0 }, function (index) {//流程发起请求
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
												apiName: "updateZjPrProgrammeLaunchFlow",
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
							obj.close();
							break;
						default:
							obj.close();
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
		if (tabItem.type === "1") {
			detailForm = tabCons[i] = $tabCon.detailLayer({
				customBtnGroup: customBtnGroup,
				conditions: tabItem.conditions
			});		
			tabCons[i].setOpenData(data);
		}
		tabCons[i] = $tabCon.detailLayer({ customBtnGroup: customBtnGroup, conditions: tabItem.conditions });
		tabCons[i].setOpenData(data);		
		tabCons[i].close = function () {
			parent.pager.page('remote');
			parent.layer.close(parent.myOpenLayer);
		}
	})
	$tabSystem.append($tabBar).append(tabCons).Huitab({
		index: 0
	});
})
function custom_close() {
	if(confirm("您确定要关闭本页吗？")) {
		window.opener = null;
		window.open('', '_self');
		window.close();
	}
}

