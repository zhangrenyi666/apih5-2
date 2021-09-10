var saved = true;
var companyId = l.getUrlParam('companyId');
var saveType = l.getUrlParam('saveType');
var id = l.getUrlParam('id');
var urlkey = l.getUrlParam('urlkey')
var idkey = getKeyName(urlkey, "Id")


switch (saveType) {//0是新增，1是修改，2是复制新增
	case "0":
		$('[name=fillDate]').val(l.dateFormat(new Date(), "yyyy-MM-dd"))
		break;
	case "1":
		var _params = {};
		_params[idkey] = id
		l.ajax('get' + urlkey + 'Details', _params, function (data) {
			for (var key in data) {
				switch (key) {
					case "fillDate":
						$('[name=' + key + ']').val(l.dateFormat(new Date(data[key]), "yyyy-MM-dd"))
						break;
					case "accessoryList":
						Lny.log(data[key])
						resetUploadList(key, data[key])
						break;
					default:
						$('[name=' + key + ']').val(data[key])
						break
				}
			}
		})
		break;
	case "2":
		var _params = {};
		_params[idkey] = id
		l.ajax('get' + urlkey + 'Details', _params, function (data) {
			for (var key in data) {
				switch (key) {
					case "changes":
						$('[name=' + key + ']').val(0)
						break;
					case "changeDesc":
						$('[name=' + key + ']').val("")
						break;
					case "fillDate":
						$('[name=' + key + ']').val(l.dateFormat(new Date(), "yyyy-MM-dd"))
						break;
					case "accessoryList":
						resetUploadList(key,  data[key])
						break;
					default:
						$('[name=' + key + ']').val(data[key])
						break
				}
			}
		})
		break;
}
$("body").on("click", "button", function () {
	var name = $(this).attr("data-name");
	switch (name) {
		case "save":
			save()
			break;
		case "close":
			layer_close()
			break;
	}
})
function save() {
	if (saved) {
		var _params = {};
		_params.companyId = companyId;
		_params.accessoryList =getUploadList("accessoryList");
		_params[idkey] = id;
		var arr = $("#form").serializeArray()
		for (var i = 0; i < arr.length; i++) {
			if (arr[i].name == "fillDate") {
				_params[arr[i].name] = l.dateToUnix(arr[i].value);
			} else {
				_params[arr[i].name] = arr[i].value;
			}
		}
		switch (saveType) {//0是新增，1是修改，2是复制新增
			case "0":
			case "2":
				l.ajax('add' + urlkey, _params, function (data) {
					parent['pager' + urlkey].page('remote')
					layer_close()
				})
				break;
			case "1":
				l.ajax('update' + urlkey, _params, function (data) {
					parent['pager' + urlkey].page('remote')
					layer_close()
				})
				break;
		}
	}
}

