var code = l.getUrlParam('code');
Lny.setCookie('code', code, 30);
var flowId = l.getUrlParam('id') || " ";//流程模版id
var $tabSystem = $("#tab-system")//模版顶级jq对象
var $tabBar = $('<div class="tabBar cl"></div>');//tab按钮控制条
var tabCons = []//tab内容页面组
//根据flowId获取流程发起模版数据及数据
var flowFormJson = {
	name: "",
	tabs: []
}
var $tabTitle = $("#tab-title")//模版title
$tabTitle.html("购置申请")

switch (flowId) {
	case "cropPurchasingProcess":
		flowFormJson = {//流程模版json（升级版：根据流程id动态获取，当前前台写死）
			name: "公司购置申请",
			titleName: "zcmc",
			tabs: [
				{
					name: "基本信息",
					type: "1",
					isMain: "1",
					tbName: "zjHwzcZcgl",
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
							name: "typeAssets",//
							label: "申请类型代码",// 1为公司  2为项目
							defaultValue: "1",
							placeholder: "",
						},
						{
							type: "hidden",//
							name: "zcztdm",//
							label: "资产状态代码（默认为未验收2）",//
							defaultValue: "2",
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
							must: true
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
							must: true
						},
						{
							type: "text",//
							name: "zcmc",//
							label: "资产名称",//
							placeholder: "请输入资产名称",
							must: true
						},
						{
							type: "number",//
							name: "zcyz",//
							label: "参考价（元）",//
							placeholder: "请输入参考价（元）"
						},
						{
							type: "text",//
							name: "ggxh",//
							label: "规格型号",//
							placeholder: "请输入规格型号",
							must: true
						},
						{
							type: "date",//
							name: "grrq",//
							label: "申购日期",//
							dateFmt: "yyyy-MM-dd",
							defaultValue: new Date(),
							id: "grrq",
							must: true
						},
						{
							type: "pickTree",//
							name: "oaSydw",//接口字段名
							label: "申购单位",//
							//must: true,
							pickType: {
								department: "oaDepartmentList",//部门列表对应接口字段名,false表示不开启该类
								member: false,//成员列表对应接口字段名,false表示不开启该类
							}
						},
						{
							type: "text",//
							name: "measureUnit",//
							label: "计量单位",//
							placeholder: "请输入计量单位",
							must: true
						},
						{
							type: "hidden",//
							name: "quantity",//
							label: "数量",//		    	                	
							defaultValue: "1",
							immutableAdd: true
						},
						{
							type: "select",//
							name: "manufacturerId",//
							label: "生产厂家",
							search: true,
							placeholder: "请输入资产品牌",
							selectList: [//当类型为select时，option配置
							],
							ajax: {
								api: "getManufacturerSelectAllList",
								valueName: "recordid",
								textName: "manufacturerName",
							},
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
							type: "select",
							name: "xzl",
							label: "残值率（%）",
							selectList: [//当类型为select时，option配置
							],
							ajax: {
								api: "getCzlSelectAllList",
								valueName: "dmbh",
								textName: "dmnr"
							},
							must: true
						},
						{
							type: "select",
							name: "grfsdm",
							label: "购入方式",
							selectList: [//当类型为select时，option配置
							],
							ajax: {
								api: "getBuyMannerSelectAllList",
								valueName: "dmbh",
								textName: "dmnr"
							},
						},
						{
							type: "textarea",//
							name: "appReason",//
							label: "申购原因",//
							placeholder: "请输入申购原因",
						},
						{
							type: "textarea",//
							name: "bz",//
							label: "备注",//
							placeholder: "请输入备注",
						},
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
		}
		break;
	case "projectDepPurchasingProcess":
		flowFormJson = {//流程模版json（升级版：根据流程id动态获取，当前前台写死）
			name: "项目部购置申请",
			titleName: "zcmc",
			tabs: [
				{
					name: "基本信息",
					type: "1",
					isMain: "1",
					tbName: "zjHwzcZcgl",
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
							name: "typeAssets",//
							label: "申请类型代码",// 1为公司  2为项目
							defaultValue: "2",
							placeholder: "",
						},
						{
							type: "hidden",//
							name: "zcztdm",//
							label: "资产状态代码（默认为未验收2）",//
							defaultValue: "2",
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
							must: true
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
							must: true
						},
						{
							type: "text",//
							name: "zcmc",//
							label: "资产名称",//
							placeholder: "请输入资产名称",
							must: true
						},
						{
							type: "number",//
							name: "zcyz",//
							label: "参考价元）",//
							placeholder: "请输入参考价（元）"
						},
						{
							type: "text",//
							name: "ggxh",//
							label: "规格型号",//
							placeholder: "请输入规格型号",
							must: true
						},
						{
							type: "date",//
							name: "grrq",//
							label: "申购日期",//
							dateFmt: "yyyy-MM-dd",
							defaultValue: new Date(),
							id: "grrq",
							must: true
						},
						{
							type: "pickTree",//
							name: "oaSydw",//接口字段名
							label: "申购单位",//
							//must: true,
							pickType: {
								department: "oaDepartmentList",//部门列表对应接口字段名,false表示不开启该类
								member: false,//成员列表对应接口字段名,false表示不开启该类
							}
						},
						{
							type: "text",//
							name: "measureUnit",//
							label: "计量单位",//
							placeholder: "请输入计量单位",
							must: true
						},
						{
							type: "hidden",//
							name: "quantity",//
							label: "数量",//		    	                	
							defaultValue: "1",
							immutableAdd: true
						},
						{
							type: "select",//
							name: "manufacturerId",//
							label: "生产厂家",
							search: true,
							placeholder: "请输入资产品牌",
							selectList: [//当类型为select时，option配置
							],
							ajax: {
								api: "getManufacturerSelectAllList",
								valueName: "recordid",
								textName: "manufacturerName",
							},
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
							type: "select",
							name: "xzl",
							label: "残值率（%）",
							selectList: [//当类型为select时，option配置
							],
							ajax: {
								api: "getCzlSelectAllList",
								valueName: "dmbh",
								textName: "dmnr"
							},
							must: true
						},
						{
							type: "select",
							name: "grfsdm",
							label: "购入方式",
							selectList: [//当类型为select时，option配置
							],
							ajax: {
								api: "getBuyMannerSelectAllList",
								valueName: "dmbh",
								textName: "dmnr"
							},
						},
						{
							type: "textarea",//
							name: "appReason",//
							label: "申购原因",//
							placeholder: "请输入申购原因",
						},
						{
							type: "textarea",//
							name: "bz",//
							label: "备注",//
							placeholder: "请输入备注",
						},
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
								body["apiTitle"] = "getZjHwAssetsFlowTitle";
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
							body["apiName"] = "addFlowAssetsInLaunch"//购置申请发起时调用
							//add by lny on 717
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
											apiName: "updateZjHwzcZCGLApih5FlowStatus",
											apiBody: { workId: _d.workId, apih5FlowStatus: _d.apih5FlowStatus, title: _d["title"],imageList:_d.apiBody.imageList }
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

$tabSystem.append($tabBar).append(tabCons).Huitab({ index: 0 });