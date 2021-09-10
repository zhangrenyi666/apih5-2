var code = l.getUrlParam('code');
Lny.setCookie('code', code, 30);
var flowId = l.getUrlParam('id') || "purchasingSystem";//流程模版id
var workId = l.getUrlParam('workId') || "HZ2881f76458a99c016458a99ee70001";
var mainId = l.getUrlParam('sszcid');
var $tabSystem = $("#tab-system")//模版顶级jq对象
var $tabBar = $('<div class="tabBar cl"></div>');//tab按钮控制条
var tabCons = []//tab内容页面组
//根据flowId获取流程发起模版数据及数据
var flowFormJson = {
	name: "",
	tabs: []
}
var $tabTitle = $("#tab-title")//模版title
$tabTitle.html("验收申请")

switch (flowId) {
	case "purchasingSystem":
		flowFormJson = {//流程模版json（升级版：根据流程id动态获取，当前前台写死）
			name: "验收申请",
			titleName: "zcmc",
			tabs: [
				{
					name: "基本信息",
					type: "1",
					isMain: "1",
					tbName: "zjHwzcZcys",
					tbIdName: "recordid",
					conditions: [
						{
							type: "hidden",//
							name: "recordid",//
							label: "主键id",//
							placeholder: "",
						},
						{
							type: "hidden",//
							name: "flowIdFlag",//
							label: "主键id",//
							defaultValue: "2"
						},
						{
							type: "hidden",//
							name: "sszcid",//
							label: "所属资产id",//
							defaultValue: mainId,
							placeholder: "",
						},
						{
							type: "select",
							name: "sszclx1",
							label: "资产大类",
							selectList: [//当类型为select时，option配置
							],
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
							selectList: [//当类型为select时，option配置
							],
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
							placeholder: "请输入资产原值（元）"
						},
						{
							type: "text",//
							name: "ggxh",//
							label: "规格型号",//
							placeholder: "请输入规格型号"
						},
						{
							type: "number",//
							name: "nynx",//
							label: "耐用年限",//
							placeholder: "请输入耐用年限"
						},
						{
							type: "select",
							name: "grfsdm",
							label: "资产来源",
							selectList: [//当类型为select时，option配置
							],
							ajax: {
								api: "getBuyMannerSelectAllList",
								valueName: "dmbh",
								textName: "dmnr"
							}
						},
						{
							type: "date",//
							name: "scrq",//
							label: "出厂日期",//
							dateFmt: "yyyy-MM-dd",
							defaultValue: new Date(),
							id: "scrq"
						},
						{
							type: "pickTree",
							name: "glrymc",
							label: "管理员",
							pickType: {
								department: false,//部门列表对应接口字段名,false表示不开启该类
								member: "oaMemberList",//成员列表对应接口字段名,false表示不开启该类
							}
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
							selectList: [//当类型为select时，option配置
							],
							ajax: {
								api: "getDepartmentSelectAllList",
								valueName: "bmbh",
								textName: "bmmc"
							}
						},
						{
							type: "text",
							name: "cfdd1",
							label: "存放地点",
							placeholder: "请输入存放地点",
							must: true
						},
						{
							type: "text",//
							name: "syr",//接口字段名
							label: "使用人",//
							placeholder: "请输入使用人",
						},
						{
							type: "text",//
							name: "ysr",//接口字段名
							label: "验收人员",//
							placeholder: "请输入验收人员",
						},
						{
							type: "date",//
							name: "yssj",//
							label: "验收日期",//
							dateFmt: "yyyy-MM-dd",
							defaultValue: new Date(),
							id: "yssj",
							must: true
						},
						{
							type: "textarea",//
							name: "remarks",//
							label: "验收说明",//
							placeholder: "请输入验收说明",
						},
						{
							type: "upload",//
							name: "imageList",//zjHwzcAssetsImage
							label: "附件1",//
							btnName: "添加附件",
							projectName: "zj-assets-haiwei",
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

l.ajax("getAssetsDetails", { "workId": l.getUrlParam('workId') }, function (data) {
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
								//  apiData:{}
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
									for (var key in tabObjDatas.data) {
										body["apiBody"][key] = tabObjDatas.data[key]
									}
									body["apiTitle"] = "getZjHwAssetsFlowTitle";
								} else if (tabItemj.type === "2") {//如果是附件类型子表，type==="2"
									//给子表赋值-附件表
									if (!body["subTableObject"]) { body["subTableObject"] = {} }
									for (var key in tabObjDatas.data) {
										var subTableDataObject = tabObjDatas.data[key];
										body["apiBody"][key] = tabObjDatas.data[key]
										body["subTableObject"][key] = { subTablePrimaryIdName: tabItemj.tbIdName, subTableType: tabItemj.type, subTableDataObject: subTableDataObject }
									}
								} else {//如果是普通类型子表，type==="1"，目前只有1和2
									//给子表赋值-普通表
									if (!body["subTableObject"]) { body["subTableObject"] = {} }
									for (var key in tabObjDatas.data) {
										body["apiBody"][key] = tabObjDatas.data[key]
									}
									body["subTableObject"][tabItemj.tbName] = { subTablePrimaryIdName: tabItemj.tbIdName, subTableType: tabItemj.type, subTableDataObject: tabObjDatas.data }

								}
								body["apiName"] = "addZjHwzcZcys"
							}
							//流程发起特殊代码---开始
							layer.confirm("确定发起？", { icon: 0, }, function (index) {//流程发起请求
								l.ajax("createOpenFlow", body, function (_d, _m, _s, _r) {
									if (_s) {//发起成功，返回workId
										loadFlow(_d.flowButtons, {
											otherBody: {
												title: _d["title"],
												flowId: flowId,//流程id
												nodeId: _d.flowNode.nodeId,
												trackId: _d.flowNode.trackId,
												workId: _d.workId,
												flowVars: _d.flowVars,
												nodeVars: _d.nodeVars,
												apih5FlowStatus: _d.apih5FlowStatus,
												apiName: "updateZjHwzcZcys",
												apiBody: { workId: _d.workId, apih5FlowStatus: _d.apih5FlowStatus, title: _d["title"],imageList:_d.apiBody.imageList}
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
		tabCons[i].setOpenData(data)
		tabCons[i].close = function () {
			parent.pager.page('remote')
			parent.layer.close(parent.myOpenLayer)
		}
	})
	$tabSystem.append($tabBar).append(tabCons).Huitab({
		index: 0
	});
})