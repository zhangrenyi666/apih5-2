var code = Apih5.getUrlParam('code')
Apih5.setCookie('code', code, 30)
var saved = true;
function setSelect() {
    l.ajax("getZjMeetingInsideMeetingSelectAllList",{},function(data){
        $("#insideId").html("")
        $.each(data, function (i, item) {
            $str = "";
            $str += "<option value=" + item.insideMeetingId + ">" + item.insideMeetingTitle + "</option>";
            $("#insideId").append($str);
        });
        // getDetails("getAssetsDetails",{recordid: id});
    })
}
function change(){
    // alert($('#checkState option:selected') .val());
    var _params = {}
    _params.insideMeetingId = $('#insideId option:selected') .val();
    l.ajax('getZjMeetingInsideMeetingDetail', _params, function (data) {
        console.log(data.insideMeetingContent)
        $("#insideMeetingContent").val(data.insideMeetingContent);
        // $("#meetingStartTime").val(data.meetingStartTime);
        // $("#meetingEndTime").val(data.meetingEndTime);
        $("#meetingRoomId").val(data.meetingRoomId);
        $("#insideMeetingId").val(data.insideMeetingId);
        // $("#rzrq").val(systemDate);
        // alert(data.insideMeetingContent);
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
        var arr = $("#form").serializeArray()
        for (var i = 0; i < arr.length; i++) {
            _params[arr[i].name] = arr[i].value;
        }
        var remindTimeStr = [];
        $.each($('input:checkbox:checked'),function(){
            console.log($(this).val());
            remindTimeStr.push($(this).val());
        });
        console.log(remindTimeStr);
        _params.remindTimeStr = remindTimeStr;
        console.log(_params);
        if(_params.meetingStartTime){
            var meetingStartTime = new Date(_params.meetingStartTime); 
            _params.meetingStartTime = meetingStartTime.getTime();  
        }
        if(_params.meetingEndTime){
            var meetingEndTime = new Date(_params.meetingEndTime); 
            _params.meetingEndTime = meetingEndTime.getTime();  
        }
        if(arr[2].value==""){
            layer.alert("入账日期不能为空", { icon: 0, }, function (index) {
                layer.close(index);
            });
        }else{
            //console.log('验收参数是：',_params)
            l.ajax('addZjInsideMeetingReserveTimingRemind', _params, function (data) {
                parent['pager'].page('remote')
                layer_close()
            })
        }
    }
}