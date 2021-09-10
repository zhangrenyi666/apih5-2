var myScroll,
	pullDownEl, pullDownOffset,
	pullUpEl, pullUpOffset;

var flag = 0;

function loaded() {
	pullDownEl = document.getElementById('pullDown');
	pullDownOffset = pullDownEl.offsetHeight;
	pullUpEl = document.getElementById('pullUp');
	pullUpOffset = pullUpEl.offsetHeight;

	myScroll = new iScroll('wrapper', {
		useTransition: true,
		topOffset: pullDownOffset,
		onRefresh: function () {
			if (pullDownEl.className.match('loading')) {
				pullDownEl.className = '';
				pullDownEl.querySelector('.pullDownLabel').innerHTML = '下拉更新...';
			} else if (pullUpEl.className.match('loading')) {
				pullUpEl.className = '';
				if (flag == 0) {
					pullUpEl.querySelector('.pullUpLabel').innerHTML = '上拉加载更多...';
				}
			}
		},
		onScrollMove: function () {
			if (this.y > 5 && !pullDownEl.className.match('flip')) {
				pullDownEl.className = 'flip';
				pullDownEl.querySelector('.pullDownLabel').innerHTML = '释放更新...';
				this.minScrollY = 0;
			} else if (this.y < 5 && pullDownEl.className.match('flip')) {
				pullDownEl.className = '';
				pullDownEl.querySelector('.pullDownLabel').innerHTML = '下拉更新...';
				this.minScrollY = -pullDownOffset;
			} else if (this.y < (this.maxScrollY - 5) && !pullUpEl.className.match('flip')) {
				pullUpEl.className = 'flip';
				pullUpEl.querySelector('.pullUpLabel').innerHTML = '释放更新...'; 
				this.maxScrollY = this.maxScrollY; 
			} else if (this.y > (this.maxScrollY + 5) && pullUpEl.className.match('flip')) {
				pullUpEl.className = '';
				pullUpEl.querySelector('.pullUpLabel').innerHTML = '上拉加载更多...';
				this.maxScrollY = pullUpOffset;
			}
		},
		onScrollEnd: function () {
			if (pullDownEl.className.match('flip')) {
				pullDownEl.className = 'loading';
				pullDownEl.querySelector('.pullDownLabel').innerHTML = '加载中...';
				pullDownAction(); // Execute custom function (ajax call?)
			} else if (pullUpEl.className.match('flip')) {
				pullUpEl.className = 'loading';
				pullUpEl.querySelector('.pullUpLabel').innerHTML = '加载中...';
				pullUpAction(); // Execute custom function (ajax call?)
			}
		}
	});
	window.myScroll = myScroll;
	setTimeout(function () {
		document.getElementById('wrapper').style.left = '0';
	}, 100);
}

document.addEventListener('touchmove', function (e) {
	e.preventDefault();
}, false);

//需要复写
function pullDownAction() {
}
//需要复写
function pullUpAction() {
	// isUpRefresh = true;
	// if (dataLen >= totalNumber) {
	// 	$('.pullUpIcon').hide();
	// 	pullUpEl.className = 'flip';
	// 	pullUpEl.querySelector('.pullUpLabel').innerHTML = '已经到底了。';
	// 	return;
	// }
	// startPage = startPage + 1;
	getDataList()
}

function changeRollInfo(data) {

	if (data.length < 10) {

		flag = 1;

		$(".pullUpLabel").text("没有更多数据了");

	} else {

		flag = 0;

		$(".pullUpLabel").text("上拉加载更多......");

	}
	var liobjs = $("#wrapper li");

	if (data.length == 0 && liobjs.length < 1) {

		$(".pullUpLabel").hide();

		var obj = $("#wrapper").parent().find(".listNoData");

		if (obj.length > 0) {

			$(obj).show();

		} else {

			$("#wrapper").parent().append("<div class=\"listNoData\">没有更多数据了</div>");

		}

	}
}