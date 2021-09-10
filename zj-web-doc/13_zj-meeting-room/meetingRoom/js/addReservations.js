




var saved = true;
var id = l.getUrlParam('id');
var newDate = new Date();
var systemDate = newDate.getFullYear()+"-"+(newDate.getMonth()+1)+"-"+newDate.getDate();//获取当前日期 格式化yy-MMM-dd
$("#rzrq").val(systemDate);
function setSelect() {
//    l.ajax("getCheckSelectAllList",{},function(data){
//        $("#checkState").html("")
//        $.each(data, function (i, item) {
//            $str = "";
//            $str += "<option value=" + item.dmbh + ">" + item.dmnr + "</option>";
//            $("#checkState").append($str);
//        });
//        getDetails("getAssetsDetails",{recordid: id});
//    })
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
        var arr = $("#form").serializeArray()
        for (var i = 0; i < arr.length; i++) {
            _params[arr[i].name] = arr[i].value;
        }

        var ysdh= arr[1].value;//验收单号
        var ysdhGs = /^[a-zA-Z0-9]{1,20}$/.test(ysdh);//验收单号格式限制
        
        if(arr[1].value==""){
           layer.alert("验收单号不能为空", { icon: 0, }, function (index) {
                layer.close(index);
            });
		}else if(arr[2].value==""){
            layer.alert("入账日期不能为空", { icon: 0, }, function (index) {
                layer.close(index);
            });
        }else{
            //console.log('验收参数是：',_params)
            l.ajax('addCheck', _params, function (data) {
                parent['pager'].page('remote')
                layer_close()
            })
        }
    }
}

function addLine(){
 //$("#append").append("<span style='color:red;'>你想添加的内容</span>");
 $("#append").append("<br><span>姓名</span>：<input class='text' name='remarks'" +
 		" placeholder='请输入验收说明'></input><span>时间</span>：<input type='text' " +
 		"placeholder='' value='' class = 'aaa' onfocus='WdatePicker({dateFmt:"+'"yyyy-MM-dd HH:mm"'+"})'>" +
 		"</input><span>地点</span>：<input class='text' name='remarks' placeholder='请输入验收说明'></input>");
}
