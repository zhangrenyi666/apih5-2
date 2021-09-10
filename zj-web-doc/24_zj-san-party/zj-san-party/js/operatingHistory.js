var workId = l.getUrlParam("id");
var code = Lny.getUrlParam('code');
Lny.setCookie('code', code, 30);
var params = {
    workId:workId 
};
l.ajax('getHistory', params, function (res) {
    if(res.length){
        for(var i = 0; i < res.length; i++){
            if(!res[i].nodeName){
                res[i].nodeName = '--';
            }
            if(!res[i].realName){
                res[i].realName = '--';
            }
            if(!res[i].comments){
                res[i].comments = '--';
            }
            if(!res[i].actionTime){
                res[i].actionTime = '--';
            }else{
                res[i].actionTime = new Date(res[i].actionTime).toLocaleString().replace(/:\d{1,2}$/,' ');
            }
            $('.about4_main ul').append('<li><div>节点名称：'+res[i].nodeName+'</div><div>审核者：'+res[i].realName+'</div><div>意见：'+res[i].comments+'</div><div>时间：'+res[i].actionTime+'</div></li>')
        }
    }else{
        $('.about4_main ul').append('<li>暂无操作记录...</li>')
    }
})