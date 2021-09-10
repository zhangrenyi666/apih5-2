var code = Lny.getUrlParam('code');
Lny.setCookie('code', code, 30);
var datas = [];
var table = $('#table').DataTable({
	"info": false,//是否显示数据信息
	"paging": false,//是否开启自带分页
	"ordering": false, //是否开启DataTables的高度自适应
	"processing": false,//是否显示‘进度’提示
	"searching": false,//是否开启自带搜索
	"autoWidth": false,//是否监听宽度变化
	"columnDefs": [//表格列配置
		{
			"targets": [0],
			"data": "rowIndex",
			"width": 13,
			"title": '<input type="checkbox">',
			"render": function (data) {
				return '<input type="checkbox" name="checkbox" data-rowIndex="' + data + '" >';
			}
		},
		{
			"targets": [1],
			"data": "rowIndex",
			"title": 'No.',
			"width": 25,
			"render": function (data) {
				return data + 1
			}
		},
		{
			"targets": [2],//第几列
			"data": "title",//接口对应字段
			"title": "标题",//表头名
			"width": 300,
			"defaultContent": "-",//默认显示
			"render": function (data, index, row) {
				var a;
				if (data) {
					a = '<a style="color:blue;" onclick="myOpen(\'' + '流程处理' + '\',\'' + encodeURI(JSON.stringify(row)) + '\',\'' + 'process' + '\')" href="javascript:void(0);">' + data + '</a>'
				}
				return a;
			}

		},
		{
			"targets": [3],//第几列
			"data": "sendUserName",//接口对应字段
			"title": "递交者",//表头名
			"defaultContent": "-",//默认显示
		},

		{
			"targets": [4],//第几列
			"data": "nodeName",//接口对应字段
			"title": "当前活动名称",//表头名
			"defaultContent": "-",//默认显示
		},
		{
			"targets": [5],//第几列
			"data": "flowStatus",//接口对应字段
			"title": "流程状态",//表头名
			"defaultContent": "-",//默认显示 
		},
		{
			"targets": [6],//第几列
			"width": 150,
			"data": "createTime",//接口对应字段
			"title": "发起时间",//表头名
			"defaultContent": "-",//默认显示
			"render": function (data) {
				return l.dateFormat(new Date(data), "yyyy-MM-dd HH:mm:ss");
			}
		},
		{
			"targets": [7],//第几列
			"width": 100,
			"data": "flowName",//接口对应字段
			"title": "所属流程",//表头名
			"defaultContent": "-",//默认显示
		}
	]
});

var form = $('#form').filterfrom({
	conditions: [//表单项配置
        {
			type: "select",
			name: "flowId",
			label: "流程类型",
			selectList: [//当类型为select时，option配置  0:全公司  1：机关  2：项目
				{
					value: "zjXmZcBuy,zjXmZcCheck,zjXmZcTransfer,zjXmZcScrap,zjXmZcScrapReport",
					text: "全部"
				},
				{
					value: "zjXmZcBuy",
					text: "资产购置"
				},
				{
					value: "zjXmZcCheck",
					text: "资产验收"
				},
				{
					value: "zjXmZcTransfer",
					text: "资产调拨"
				},
				{
					value: "zjXmZcScrap",
					text: "资产报废"
				},
				{
					value: "zjXmZcScrapReport",
					text: "资产报废报告"
				}
			],
		},
		{
			type: "text",//三种形式：text,select,date,
			name: "keyword",//输入字段名
			label: "标题",//输入字段对杨的中文名称
			placeholder: "请输入标题",
		},
		{
			type: "text",//三种形式：text,select,date,
			name: "sendUserName",//输入字段名
			label: "递交者",//输入字段对杨的中文名称
			placeholder: "请输入递交者",
		}
	],
	onSearch: function (arr) {//搜索按钮回调
		var _params = {};
		for (var i = 0; i < arr.length; i++) {
			if (arr[i].name == "startDate" || arr[i].name == "endDate") {
				if (arr[i].value) {
					_params[arr[i].name] = l.dateFormat(new Date(new Date(arr[i].value).getTime()), "yyyy-MM-dd");
				} else {
					_params[arr[i].name] = "";
				}
			} else {
				_params[arr[i].name] = arr[i].value;
			}
		}
		pager.page('remote', 0, _params)
	},
	onReset: function (arr) {//重置按钮回调
		var _params = {};
		for (var i = 0; i < arr.length; i++) {
			_params[arr[i].name] = arr[i].value;
		}
		pager.page('remote', _params)
	}
})
var pager = $("#pager").page({
	remote: {// ajax请求配置
		url: l.getApiUrl("getTodoList"),
		params: {
			flowId: "zjXmZcBuy,zjXmZcCheck,zjXmZcTransfer,zjXmZcScrap,zjXmZcScrapReport"
		},
		success: function (result) {
			if (result.success) {
				var data = result.data;
				$.each(data, function (index, item) {
					item.rowIndex = index
				})
				table.clear().rows.add(data).draw();
			} else {
				layer.alert(result.message, { icon: 0, }, function (index) {
					layer.close(index);
				});
			}
		},
	}
});
var selectData = [];
$("body").on("click", "button", function () {
	var checkedData = l.getTableCheckedData(table);
	var name = $(this).attr("data-name");
	switch (name) {
		case "process":
			if (checkedData.length) {
				var _body = {
					title: checkedData[0].title,
					nodeId: checkedData[0].nodeId,
					trackId: checkedData[0].trackId,
					workId: checkedData[0].workId,
					flowId: checkedData[0].flowId,
					apiBody: { workId: checkedData[0].workId }
				}
				l.ajax('openFlow', _body, function (_d, _m, _s) {
					if (_s) {
						if (_d && _d.nodeVars && _d.nodeVars.batchFlag && _d.nodeVars.batchFlag === '1') {
							var otherBody = {
								nodeId: _d.flowNode.nodeId,
								trackId: _d.flowNode.trackId,
								workId: _d.workId,
								flowVars: _d.flowVars,
								nodeVars: _d.nodeVars,
								actionData: '{"operate":"submit","operateClazz":"com.horizon.wf.action.ActionSubmit","operateText":"提交","operateFlag":1,"reOpen":false}'
							};
							selectData= [];
							l.ajax('actionFlow', otherBody, function (_ds, _ms, _ss) {
								if (_ss) {
									for (var i = 0; i < _ds.nextNodes.length; i++) {
										_ds.nextNodes[i].value = _ds.nextNodes[i].nodeId;
										_ds.nextNodes[i].text = _ds.nextNodes[i].selectName;
										selectData.push(_ds.nextNodes[i]);
									}
									myOpen1(encodeURI(JSON.stringify(checkedData)), encodeURI(JSON.stringify(_d.nodeVars)));
								}
							})
						} else {
							if (checkedData.length == 1) {
								myOpen("流程处理", encodeURI(JSON.stringify(checkedData[0])), " ");
							} else {
								layer.alert("只能选择一个", { icon: 0, }, function (index) {
									layer.close(index);
								});
							}
						}
					}
				})
			} else if (checkedData.length == 0) {
				layer.alert("未选择任何项", { icon: 0, }, function (index) {
					layer.close(index);
				});
			}
			break;
	}
})
var detailLayer;
function detailLayerFun(datas, selectData) {
	return detailLayer = $('#detailLayer').detailLayer({
		layerArea: ["65%", "65%"],
		layerTitle: '流程处理',
		customBtnGroup: {
			btns: [
				{ label: '提交', name: '提交' },
				// {label:'退回',name:'退回'},
				{ label: '取消', name: '取消' }
			],
			callback: function (dataName, obj) {
				var _params = obj.getDatas().data;
				if (dataName == '提交') {
					if (_params.opinionContent && _params.nextNodeId && _params.sendList.sendList.length) {
						for (var i = 0; i < datas.length; i++) {
							datas[i].opinionContent = _params.opinionContent;
							datas[i].sendUserKey = _params.sendList.sendList[0].userid;
							datas[i].sendRealName = _params.sendList.sendList[0].name;
							datas[i].sendDepartmentId = _params.sendList.sendList[0].orgId;
							datas[i].apiBody = {};
							datas[i].opinionField = _params.opinionField;
							datas[i].nextNodeId = _params.nextNodeId;
						}
						l.ajax('batchAppZjHwzcZcgl', datas, function (_d, _m, _s) {
							if (_s) {
								selectData = [];
								pager.page('remote');
								obj.close();
							}
						})
					} else {
						layer.alert("请填写必填项!", { icon: 0, }, function (index) {
							layer.close(index);
						});
					}
				} else if (dataName == '退回') {

				} else {
					obj.close();
				}
			}
		},
		conditions: [
			{
				type: "hidden",
				name: "opinionField",
				placeholder: "请输入"
			},
			{
				type: "textarea",
				name: "opinionContent",
				label: "您的意见",
				must: true,
				placeholder: "请输入"
			},
			{
				type: "select",
				name: "nextNodeId",
				label: "处理人节点",
				must: true,
				selectList: selectData
			},
			{
				type: "pickTree",
				name: "sendList",
				label: "处理人",
				must: true,
				pickType: {
					department: false,//部门列表对应接口字段名,false表示不开启该类
					member: "sendList",//成员列表对应接口字段名,false表示不开启该类
				}
			},
		]
	})
}
var detailLayers;
function detailLayersFun(datas, selectData) {
	return detailLayers = $('#detailLayers').detailLayer({
		layerArea: ["65%", "65%"],
		layerTitle: '流程处理',
		customBtnGroup: {
			btns: [
				{ label: '提交', name: '提交' },
				// {label:'退回',name:'退回'},
				{ label: '取消', name: '取消' }
			],
			callback: function (dataName, obj) {
				var _params = obj.getDatas().data;
				if (dataName == '提交') {
					if (_params.nextNodeId && _params.sendList.sendList.length) {
						for (var i = 0; i < datas.length; i++) {
							datas[i].sendUserKey = _params.sendList.sendList[0].userid;
							datas[i].sendRealName = _params.sendList.sendList[0].name;
							datas[i].sendDepartmentId = _params.sendList.sendList[0].orgId;
							datas[i].apiBody = {};
							datas[i].opinionField = _params.opinionField;
							datas[i].nextNodeId = _params.nextNodeId;
						}
						l.ajax('batchAppZjHwzcZcgl', datas, function (_d, _m, _s) {
							if (_s) {
								selectData = [];
								pager.page('remote');
								obj.close();
							}
						})
					} else {
						layer.alert("请填写必填项!", { icon: 0, }, function (index) {
							layer.close(index);
						});
					}
				} else if (dataName == '退回') {

				} else {
					obj.close();
				}
			}
		},
		conditions: [
			{
				type: "hidden",
				name: "opinionField",
				placeholder: "请输入"
			},
			{
				type: "select",
				name: "nextNodeId",
				label: "处理人节点",
				must: true,
				selectList: selectData
			},
			{
				type: "pickTree",
				name: "sendList",
				label: "处理人",
				must: true,
				pickType: {
					department: false,//部门列表对应接口字段名,false表示不开启该类
					member: "sendList",//成员列表对应接口字段名,false表示不开启该类
				}
			},
		]
	})
}
var myOpenLayer;
function myOpen(title, data, url) {
	var data = JSON.parse(decodeURI(data));
	if (data.flowId == 'zjXmZcBuy') {//购置流程
		url = 'process'
	} else if (data.flowId == 'zjXmZcCheck') {//验收
		url = 'assetsFixCheckProcess'
	} else if (data.flowId == 'zjXmZcTransfer') {//调拨
		url = 'assetsTranProProcess'
	} else if (data.flowId == 'zjXmZcScrap') {//报废
		url = 'assetsScrapCorpProcess'
	} else if (data.flowId == 'zjXmZcScrapReport') {//报废报告
		url = 'assetsScrapReportProcess'
	}

	var options = {
		type: 2,
		title: title,
		content: url + ".html?workId=" + data.workId + "&trackId=" + data.trackId + "&nodeId=" + data.nodeId + "&code=" + code
	}
	myOpenLayer = layer.open(options)
	layer.full(myOpenLayer)
}

function myOpen1(data, nodeVars) {
	var datas = JSON.parse(decodeURI(data));
	var nodeVar = JSON.parse(decodeURI(nodeVars));
	for (var i = 0; i < datas.length; i++) {
		var flowId;
		var nodeId;
		if (i === datas.length - 1) {
			flowId = datas[i].flowId;
			nodeId = datas[i].nodeId;
		} else {
			flowId = datas[i + 1].flowId;
			nodeId = datas[i + 1].nodeId;
		}
		if (datas[i].flowId !== flowId || datas[i].nodeId !== nodeId) {
			layer.alert("请选择相同所属流程及相同活动名称处理!", { icon: 0, }, function (index) {
				layer.close(index);
			});
			break;
		} else if (i === datas.length - 1) {
			if (nodeVar && nodeVar.opinionShowFlag) {
				detailLayerFun(datas, selectData);
				detailLayer.open({ opinionField: nodeVar && nodeVar.opinionField ? nodeVar.opinionField : '' });
			} else {
				detailLayersFun(datas, selectData);
				detailLayers.open({ opinionField: nodeVar && nodeVar.opinionField ? nodeVar.opinionField : '' });
			}
		}
	}
}