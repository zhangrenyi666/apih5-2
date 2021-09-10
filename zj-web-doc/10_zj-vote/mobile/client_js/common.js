var server = '/vote';
// 投票结束日期
var redVoteDate = '2017-04-18 17:00:00';
var blueVoteDate = '2017-04-20 12:00:00';

// 判断是否过投票期
function isOverdue(type,callback,callback2){
	var voteDate = '';
	if(type == 1){
		voteDate = redVoteDate;
	}
	if(type == 2){
		voteDate = blueVoteDate;
	}
	// 当前时间戳
	var nowtimestamp = new Date().getTime();
	var votetimestamp = new Date(voteDate).getTime();
	// 当前时间戳大于投票时间戳，表示已过投票期
	if( nowtimestamp >= votetimestamp){
		if($.isFunction(callback)){
			callback();
		}
	}else{
		// 未过期
		if($.isFunction(callback2)){
			callback2();
		}
	}
}

function getSession(){
	var url = server +'/vt/session';
	var html = $.ajax({
		  url: url,
		  async: false
		 }).responseText;
	return html;
}