var saved = true;
var companyId = l.getUrlParam('companyId');
var startDate = l.getUrlParam('startDate');
var endDate = l.getUrlParam('endDate');
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
			} 
			// else if (key == "agreementSignDate" || key == "registerDate" || key == "landDate" || key == "constructionDate" || key == "preliminaryDesignDate" || key == "drawingsDate" || key == "landUseDate" || key == "financingDate" || key == "constructionPermitDate") {
			// 	if (data[key]) {
			// 		$('#formBase [name=' + key + ']').val(Lny.dateFormat(new Date(data[key]), "yyyy-MM-dd"))
			// 	} else {
			// 		$('#formBase [name=' + key + ']').val("")
			// 	}
			// }
			
			else if (
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
			} 

			else {
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
	var idKey = getKeyName(type, "Id")
	if (!id && saveType != 0) {
		if ($("[name=" + idKey + "]:checked").length == 0) {
			alert("您需要先选择一个项，才可以进行编辑！")
			return
		} else if ($("[name=" + idKey + "]:checked").length == 1) {
			id = $("[name=" + idKey + "]:checked").attr('data-id');
		} else {
			alert("您只能选择一个项，才可以进行编辑！")
			return
		}
	}
	var options = {
		type: 2,
		title: saveType == "1" ? "详情" : "详情",
		content: 'TzDetail2.html' + '?companyId=' + companyId + '&urlkey=' + type + '&id=' + id + '&saveType=' + saveType//saveType: 0是新增，1是修改，2是复制新增
	}
	layer.full(layer.open(options))
}
function del(type, id) {
	var idKey = getKeyName(type, "Id")
	var listKey = getKeyName(type, "List")
	var params = {};
	params[listKey] = []
	if (id) {
		var _p = {};
		_p[idKey] = id
		params[listKey].push(_p)
	} else {
		$("[name=" + idKey + "]:checked").each(function () {
			var _p = {};
			_p[idKey] = $(this).attr('data-id')
			params[listKey].push(_p)
		})
	}
	if (params[listKey].length > 0) {
		l.ajax('batchDelete' + type, params, function (data) {
			window["pager" + type].page('remote')
		})
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
