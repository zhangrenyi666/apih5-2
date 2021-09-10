var code = l.getUrlParam('code');
Lny.setCookie('code', code, 30);
var flowId = l.getUrlParam('id') || "zcbZcTransfer";//流程模版id
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
$tabTitle.html("总承包资产调拨申请")

switch (flowId) {
	case "zcbZcTransfer":
		flowFormJson = {//流程模版json（升级版：根据流程id动态获取，当前前台写死）
			name: "总承包资产调拨申请",
			titleName: "zcmc",
			tabs: [
				{
					name: "基本信息",
					type: "1",
					isMain: "1",
					tbName: "zjZcTransfer",
					tbIdName: "tranId",
					conditions: [
						{
							type: "hidden",//
							name: "tranId",//
							label: "主键id",//
							placeholder: "",
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
								api: "getZjAssetsTypeTreeAllList",
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
							type: "date",//
							name: "scrq",//
							label: "出厂日期",//
							dateFmt: "yyyy-MM-dd",
							defaultValue: new Date(),
							id: "scrq",
							immutableAdd: true
						},
						{
							type: "text",//
							name: "zcyz",//
							label: "资产原值（元）",//
							placeholder: "请输入资产原值（元）",
							immutableAdd: true
						},
						{
							type: "number",//
							name: "ljzj",//
							label: "累计折旧（元）",//
							placeholder: "请输入累计折旧（元）",
							immutableAdd: true
						},
						{
							type: "number",//
							name: "zxjz",//
							label: "资产净值（元）",//
							placeholder: "请输入资产净值（元）",
							immutableAdd: true
						},
						{
							type: "text",//
							name: "ggxh",//
							label: "规格型号",//
							placeholder: "请输入规格型号",
							immutableAdd: true
						},					
						{
							type: "text",//
							name: "sydwName",//
							label: "使用单位",//
							placeholder: "请输入使用单位",
							immutableAdd: true
						},												
						{
							type: "text",//
							name: "tranUnit",//
							label: "调入单位",//
							placeholder: "请输入调入单位",
							must: true
						},
						{
							type: "pickTree",
							name: "oaTranAdmin",
							label: "调拨后管理员",
							must: true,
							pickType: {
								department: false,//部门列表对应接口字段名,false表示不开启该类
								member: "oaMemberList",//成员列表对应接口字段名,false表示不开启该类
							}
						},
						{
							type: "date",//
							name: "tranTime",//
							label: "调拨日期",//
							dateFmt: "yyyy-MM-dd",
							defaultValue: new Date(),
							id: "tranTime",
							must: true
						},
						{
							type: "textarea",//
							name: "tranGist",//
							label: "调拨依据",//
							placeholder: "请输入调拨依据",
						},
						{
							type: "textarea",//
							name: "tranRemarks",//
							label: "调拨备注",//
							placeholder: "请输入调拨备注",
						},
						{
							type: "upload",//
							name: "imageList",//
							label: "附件",//
							btnName: "添加附件",
							projectName: "zj-assets",
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

l.ajax("geteZjZcTransferDetail", { "workId": l.getUrlParam('workId'), "sszcid": l.getUrlParam('sszcid') }, function (data) {
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
										$tabSystem.Huitab({ index: j });
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
									body["title"] = tabObjDatas.data[flowFormJson.titleName];
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
								body["apiName"] = "addZjZcTransfer"
							}
							//流程发起特殊代码---开始
							layer.confirm("确定发起？", { icon: 0, }, function (index) {//流程发起请求
								l.ajax("createOpenFlow", body, function (_d, _m, _s, _r) {
									console.log(_d.workId)
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
												apiName: "updateZjZcTransfer",
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
	$tabSystem.append($tabBar).append(tabCons).Huitab({ index: 0 });
})