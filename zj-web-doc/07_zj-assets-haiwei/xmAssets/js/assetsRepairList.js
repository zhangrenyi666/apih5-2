var code = Lny.getUrlParam('code')
Lny.setCookie('code', code, 30)
var saved = true;
var id = l.getUrlParam('id');
var newDate = new Date();
var systemDate = newDate.getFullYear()+"-"+(newDate.getMonth()+1)+"-"+newDate.getDate();//获取当前日期 格式化yy-MMM-dd
$("#bxrq").val(systemDate);
$("#wxrq").val(systemDate);
function setSelect() {
    l.ajax("getMaintainStateSelectAllList",{},function(data){
        $("#wxztdm").html("")
        $.each(data, function (i, item) {
            $str = "";
            $str += "<option value=" + item.dmbh + ">" + item.dmnr + "</option>";
            $("#wxztdm").append($str);
        });
        getDetails("getZjHwzcZcglDetails",{recordid: id});
    })
}
function getDetails(apiName,_params){
    _params =_params||{}
    l.ajax(apiName, _params, function (data) {
        for (var key in data) {
            switch (key) {
                default :
                    $('[name=' + key + ']').val(data[key]);
                    break;
            }
        }
    })
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
            if(arr[i].name=='bxrq'){
                _params[arr[i].name] = l.dateToUnix(arr[i].value);
            }else {
                _params[arr[i].name] = arr[i].value;
            }
        }
        if(arr[0].value==""){
            layer.alert("报修日期不能为空", { icon: 0, }, function (index) {
                layer.close(index);
            });
        }else  if(arr[1].value==""){
            layer.alert("维修方不能为空", { icon: 0, }, function (index) {
                layer.close(index);
            });
        }else {
            l.ajax('addRepair', _params, function (data) {
                parent['pager'].page('remote')
                layer_close()
            })
        }
    }
}


