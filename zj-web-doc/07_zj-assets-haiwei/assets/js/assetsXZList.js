var code = Lny.getUrlParam('code')
Lny.setCookie('code', code, 30)
var saved = true;
var synxNumber;
var id = l.getUrlParam('id');
function setSelect() {
	l.ajax("getScrapTypeSelectAllList",{},function(data){
		$("#bffsdm").html("")
		$.each(data, function (i, item) {
			$str = "";
			$str += "<option value=" + item.dmbh + ">" + item.dmnr + "</option>";
			$("#bffsdm").append($str);
		});
		getDetails("getAssetsDetails",{recordid: id});
	})
}
function getDetails(apiName,_params){
	_params =_params||{};
	l.ajax(apiName, _params, function (data) {
		for (var key in data) {
			//if(key=detailLayerimageList){
			//		resetUploadList("detailLayerimageList", [])
			//}
			switch (key) {
				default :
					$('[name=' + key + ']').val(data[key]);
					break;
			}
			synxNumber = data.synx
		}

	})
	//resetUploadList("detailLayerimageList", [])
}
setSelect(getDetails);


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
		_params.sszcid = id;
		_params.imageList = getUploadList("detailLayerimageList")||[];
		
		var arr = $("#form").serializeArray()
		for (var i = 0; i < arr.length; i++) {
			_params[arr[i].name] = arr[i].value;
		}
			if(arr[0].value==""){
			layer.alert("报废方式不能为空", { icon: 0, }, function (index) {
				layer.close(index);
			});
		}else if(arr[2].value==""){
			layer.alert("报废原因不能为空", { icon: 0, }, function (index) {
				layer.close(index);
			});
		}else{
			l.ajax('addAssetsScrap', _params, function (data) {
				parent['pager'].page('remote')
				layer_close()
			})
		}
	}
}