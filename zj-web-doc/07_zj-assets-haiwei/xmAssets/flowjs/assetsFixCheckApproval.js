var code = l.getUrlParam('code');
Lny.setCookie('code', code, 30);
var flowId = l.getUrlParam('id') || "zjXmZcCheck";//流程模版id
var workId = l.getUrlParam('workId') || "HZ2881f76458a99c016458a99ee70001";
var mainId = l.getUrlParam('sszcid');
var $tabSystem = $("#tab-system")//模版顶级jq对象
var $tabBar = $('<div class="tabBar cl"></div>');//tab按钮控制条
var tabCons = []//tab内容页面组
var flowFormJson = {
	name: "",
	tabs: []
}
var $tabTitle = $("#tab-title")//模版title
$tabTitle.html("厦门资产验收申请")
switch (flowId) {
	case "zjXmZcCheck":
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
							name: "sszcid",//
							label: "所属资产id",//
							defaultValue: mainId,
							placeholder: "",
						},
						{
							type: "select",
							name: "typeAssets",
							label: "资产类别",
							immutableAdd: true,
							selectList: [//当类型为select时，option配置  0:全公司  1：机关  2：项目
								{
									value: "",
									text: "---"
								},
								{
									value: "0",
									text: "行政固定资产"
								},
								{
									value: "1",
									text: "信息化固定资产"
								},
								{
									value: "2",
									text: "实验器材、测量仪器"
								}
							],
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
							name: "ggxh",//
							label: "规格型号",//
							placeholder: "请输入规格型号",
							immutableAdd: true
						},
						{
							type: "text",//
							name: "zcyz",//
							label: "预估单价（元）",//
							placeholder: "请输入预估单价（元）",
							immutableAdd: true
						},
						{
							type: "date",//
							name: "appTime",//
							label: "申请时间",//
							dateFmt: "yyyy-MM-dd",
							defaultValue: new Date(),
							immutableAdd: true,
							id: "appTime"
						},
						{
							type: "text", //
							name: "syr", //接口字段名
							label: "使用人", //
							placeholder: "请输入使用人",
							immutableAdd: true
						},
						{
							type: "select",//
							name: "sybmid",//
							label: "使用部门",
							placeholder: "请输入使用部门",
							immutableAdd: true,
							selectList: [//当类型为select时，option配置
							],
							ajax: {
								api: "getDepartmentSelectAllList",
								valueName: "recordid",
								textName: "bmmc",
							}
						},
						{
							type: "number",//
							name: "synx",//
							label: "使用年限（年）",//
							placeholder: "请输入使用年限（年）",
							immutableAdd: true
						},
						{
							type: "text",//
							name: "brand",//
							label: "厂家品牌",//
							placeholder: "请输入厂家品牌",
							immutableAdd: true
						},
						{
							type: "upload",//
							name: "buyFileList",//zjHwzcAssetsImage
							label: "购置附件",//
							btnName: "添加附件",
							immutableAdd: true,
							projectName: "zj-xm-assets",
							uploadUrl: 'uploadFilesByFileName',
							fileType: ["jpg", "jpeg", "png", "gif", "docx", "xls", "doc", "ppt", "xlsx", "pptx", "xlsm", "pdf"]
						},
						{
							type: "text",//
							name: "zcbh",//
							label: "资产编号",//
							placeholder: "请输入资产编号"
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
							},
						},
						{
							type: "text",
							name: "buyAmount",
							label: "购买金额（不含税）",
							placeholder: "请输入购买金额（不含税）",
							must: true
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
							name: "ysr",//接口字段名
							label: "验收人员",//
							placeholder: "请输入验收人员",
							must: true
						},
						{
							type: "textarea",//
							name: "remarks",//
							label: "备注",//
							placeholder: "请输入备注"
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
							name: "checkFileList",//zjHwzcAssetsImage
							label: "随机工具附件",//
							btnName: "添加附件",
							projectName: "zj-xm-assets",
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
l.ajax("getZjHwzcZcglDetails", { "recordid": l.getUrlParam('sszcid') }, function (data) {
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
									body["apiTitle"] = "setZjXmzcFlowTitle";
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
									//add by lny on 717
									for (var key in tabObjDatas.data) {
										body["apiBody"][key] = tabObjDatas.data[key]
									}
									//add by lny on 717
									body["subTableObject"][tabItemj.tbName] = { subTablePrimaryIdName: tabItemj.tbIdName, subTableType: tabItemj.type, subTableDataObject: tabObjDatas.data }
								}
								body["apiName"] = "addZjXmAssetsCheck"
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
												apiName: "updateZjXmAssetsCheck",
												apiBody: { workId: _d.workId, apih5FlowStatus: _d.apih5FlowStatus, title: _d["title"] }
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
