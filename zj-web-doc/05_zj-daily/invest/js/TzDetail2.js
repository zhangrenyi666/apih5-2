var saved = true;
var companyId = l.getUrlParam('companyId');
var id = l.getUrlParam('id');
var urlkey = l.getUrlParam('urlkey')
var idkey = getKeyName(urlkey, "Id")


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


$("body").on("click", "button", function () {
	var name = $(this).attr("data-name");
	switch (name) {
		case "close":
			layer_close()
			break;
	}
})


