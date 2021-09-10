var saved = true;
var companyId = l.getUrlParam('companyId');
var checkedData = {
	department: [],//部门数据
	member: [] //成员数据
};
if (companyId) {
	l.ajax('getInvestCompanyInfo', { companyId: companyId }, function (data) {
		for (var key in data) {
			if (key == "oaDepartmentList") {
				checkedData.department = data[key];
			} else if (key == "oaMemberList") {
				checkedData.member = data[key];
			} else if (
				key == "landDate" ||
				key == "constructionStudyDate" ||
				key == "financialCapacityDate" ||
				key == "costEffectiveDate" ||
				key == "pppEmbodimentDate" ||
				key == "waterConservationDate" ||
				key == "envirEvaluationDate" ||
				key == "agreementSignDate" ||
				key == "registerDate" ||
				key == "constructionDate" ||
				key == "preliminaryDesignDate" ||
				key == "drawingsDate" ||
				key == "financingDate" ||
				key == "contractAgreement" ||
				key == "winningTime" ||
				key == "constructionPermitDate" ) {
				if (data[key]) {
					$('#formBase [name=' + key + ']').val(Lny.dateFormat(new Date(data[key]), "yyyy-MM-dd"))
				} else {
					$('#formBase [name=' + key + ']').val("")
				}
			} else {
				$('#formBase [name=' + key + ']').val(data[key])
			}
		}
	})
}
$("#tab-system").Huitab({
	index: 0
});
$("body").on("click", "button", function () {
	var name = $(this).attr("data-name");
	var type = $(this).attr("data-type");
	switch (name) {
		case "save":
			save()
			break;
		case "close":
			layer_close()
			break;
		case "search":
			search(type)
			break;
		case "reset":
			reset(type)
			break;
		case "add":
			openDetail(type, 0)
			break;
		case "edit":
			openDetail(type, 1)
			break;
		case "del":
			del(type)
			break;
	}
})
function save() {
	if (saved) {
		var _params = {};
		_params.companyId = companyId;
		_params.oaDepartmentList = checkedData.department || []//部门数据
		_params.oaMemberList = checkedData.member || [] //成员数据
		var arr = $("#formBase").serializeArray()
		for (var i = 0; i < arr.length; i++) {
			_params[arr[i].name] = arr[i].value;
		}
		l.ajax('updateDailyCompany', _params, function (data) {
			parent.pager.page('remote')
			layer_close()
		})
	}
}
function search(type) {
	var _params = {};
	var arr = $("#form" + type).serializeArray()
	for (var i = 0; i < arr.length; i++) {
		_params[arr[i].name] = arr[i].value;
	}
	window["pager" + type].page('remote', _params)
	return false
}
function reset(type) {
	var _params = {};
	$("#form" + type)[0].reset()
	var arr = $("#form" + type).serializeArray()
	for (var i = 0; i < arr.length; i++) {
		_params[arr[i].name] = arr[i].value;
	}
	window["pager" + type].page('remote', _params)
}
function openDetail(type, saveType, id) {
	var idKey = getKeyName(type, "Id"), options;
	if (!id && saveType != 0) {
		var checkedData = l.getTableCheckedData(window["table" + type])


		if (checkedData.length == 1) {

			id = checkedData[0][idKey];
			if (checkedData[0]["checkState"] == 1) {
				layer.confirm("已审核，只可查看，不可编辑！", { icon: 0, btn: ["查看", "关闭"] },
					function (index) {
						options = {
							type: 2,
							title: "查看",
							content: 'TzDetail2.html' + '?companyId=' + companyId + '&urlkey=' + type + '&id=' + id //saveType: 0是新增，1是修改，2是复制新增
						}
						layer.full(layer.open(options))
					}, function (index) {
						layer.close(index);
					});
				return
			}
		} else if (checkedData.length == 0) {
			layer.alert("您需要先选择一个项，才可以进行编辑！", { icon: 0, }, function (index) {
				layer.close(index);
			});
			return
		} else {
			layer.alert("您只能选择一个项，才可以进行编辑！", { icon: 0, }, function (index) {
				layer.close(index);
			});
			return
		}
	}
	options = {
		type: 2,
		title: saveType == "1" ? "编辑" : "新增",
		content: 'TzDetail.html' + '?companyId=' + companyId + '&urlkey=' + type + '&id=' + id + '&saveType=' + saveType//saveType: 0是新增，1是修改，2是复制新增
	}
	layer.full(layer.open(options))
}
function openDetail2(type, id) {
	var idKey = getKeyName(type, "Id")
	var options = {
		type: 2,
		title: "查看",
		content: 'TzDetail2.html' + '?companyId=' + companyId + '&urlkey=' + type + '&id=' + id //saveType: 0是新增，1是修改，2是复制新增
	}
	layer.full(layer.open(options))
}
function del(type, id) {
	var idKey = getKeyName(type, "Id")
	var listKey = getKeyName(type, "List")
	// var params = {};
	var listKey = []
	if (id) {
		var _p = {};
		_p[idKey] = id
		listKey.push(_p)
	} else {
		var checkedData = l.getTableCheckedData(window["table" + type])
		$.each(checkedData, function (i, v) {
			var _p = {};
			_p[idKey] = v[idKey]
			listKey.push(_p)
		})
	}
	if (listKey.length > 0) {
		l.ajax('batchDelete' + type, listKey, function () {
			window["pager" + type].page('remote')
		})
		// l.ajax('batchDelete' + type, params, function (data) {
		// 	window["pager" + type].page('remote')
		// })
	}
}
function review(type, id) {
	var idKey = getKeyName(type, "Id")
	var _params = {};
	_params[idKey] = id
	l.ajax('review' + type, _params, function (data) {
		window["pager" + type].page('remote')
	})
}

function restReview(type, id) {
	var idKey = getKeyName(type, "Id")
	var _params = {};
	_params[idKey] = id
	l.ajax('restReview' + type, _params, function (data) {
		window["pager" + type].page('remote')
	})
}
