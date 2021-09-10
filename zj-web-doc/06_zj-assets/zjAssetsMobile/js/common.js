
/* 接口变量 */
// var  API = "http://weixin.fheb.cn:99/woa-test/";//公共通讯协议
// var API = "http://192.168.1.105:8080/";
var API = window.api;


/* ajax请求超时 状态变量 */
var timeout = '5000';//ajax请求超时 时间为5秒

// 为null和undefined时转为空字符串
function noNull(obj) {
	if (obj == null || typeof (obj) == "undefined") {
		return ""
	};
	return obj;
}

function dateFormat(d, fmt) {
	var o = {
		"M+": d.getMonth() + 1, //月份
		"d+": d.getDate(), //日
		"H+": d.getHours(), //小时
		"m+": d.getMinutes(), //分
		"s+": d.getSeconds(), //秒
		"q+": Math.floor((d.getMonth() + 3) / 3), //季度
		"S": d.getMilliseconds() //毫秒
	};
	if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (d.getFullYear() + "").substr(4 - RegExp.$1.length));
	for (var k in o)
		if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
	return fmt;
}

function dateToUnix(d) {
	return new Date(Date.parse(d.toString().replace(/-/g, "/"))).getTime()
}

// -------wang
var JQuery = $;
var layer = layer;

//处理移动端和pc端layer不兼容问题
if (layer) {
	layer.alert = function (msg) {
		layer.alert = layer.open({
			content: '<div style="font-size:1.2rem;">' + msg + '<div>'
			, skin: 'msg'
			, time: 1.5
		});
	}
}


//全局Lny
var Lny = window.Lny = function (apiNames, domain) {
	this.domain = API;
	this.apiNames = apiNames || {};
}
Lny.dev = true;//是不是开发环境，是开发环境时有console.log

!function ($, L) {
	L.log = (Lny.dev && console && console.log) ? console.log : function () { }
	L.getUrlParam = function (k) {//获取地址栏参数，k未键名
		var m = new RegExp("(^|&)" + k + "=([^&]*)(&|$)");
		var r = window.location.search.substr(1).match(m);
		if (r != null) return decodeURI(r[2]); return null;
	};
	L.dateFormat = function (d, fmt) {//格式化日期，d未new Date(),fmt为格式
		var o = {
			"M+": d.getMonth() + 1, //月份   
			"d+": d.getDate(), //日   
			"H+": d.getHours(), //小时   
			"m+": d.getMinutes(), //分   
			"s+": d.getSeconds(), //秒   
			"q+": Math.floor((d.getMonth() + 3) / 3), //季度   
			"S": d.getMilliseconds() //毫秒   
		};
		if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (d.getFullYear() + "").substr(4 - RegExp.$1.length));
		for (var k in o)
			if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
		return fmt;
	}
	L.dateToUnix = function (str) {//转unix时间戳，str是带有“-”格式化日期字符串
		return new Date(Date.parse(str.toString().replace(/-/g, "/"))).getTime()
	}
	L.prototype = {
		getTableCheckedData: function (table) {
			var checkBoxs = table.$("[name=checkbox]:checked")
			var checkedData = [];
			if (checkBoxs.length > 0) {
				for (var i = 0; i < checkBoxs.length; i++) {
					checkedData.push(table.row(checkBoxs.eq(i).attr("data-rowIndex")).data())
				}
			}
			return checkedData
		},
		delTableRow: function (idKey, listKey, urlname, data, callback) {
			var params = {};
			var arr = []
			for (var i = 0; i < data.length; i++) {
				var param = {};
				param[idKey] = data[i][idKey]
				arr.push(param)
			}
			params[listKey] = arr;
			l.ajax(urlname, params, function (data) {
				callback(data)
			})
		},
		delTableRowById: function (idKey, urlname, data, callback) {
			var params = {};
			params[idKey] = data[idKey];
			l.ajax(urlname, params, function (data) {
				callback(data)
			})
		},
		addApi: function (apis) {
			apis = apis || {}
			for (var key in apis) {
				this.apiNames[key] = apis[key]
			}
			return this.apiNames
		},
		getApiUrl: function (apiName) {
			apiName = apiName || ""
			if (!this.apiNames[apiName]) {
				L.log(apiName, "尚未在mainjs中定义")
			}
			return this.domain + this.apiNames[apiName]
		},
		ajax: function (apiName, params, success) {
			if (layer.load) {
				var loader = layer.load(1, {
					shade: [0.3, '#000'] //0.1透明度的白色背景
				});
			} else {
				var loader = layer.open({
					type: 2
					, content: '加载中'
				});
			}
			$.ajax({
				url: this.getApiUrl(apiName),
				type: 'POST',
				dataType: 'json',
				data: JSON.stringify(params),
				contentType: "application/json; charset=utf-8",
				beforeSend: function (xhr) {
					xhr.setRequestHeader('X-Requested-With', 'XMLHttpRequest');
					// 新增携带token请求
					var token = window.getUserInfo().token
					xhr.setRequestHeader('token', token);//
				},
				complete: function (result) {
					layer.close(loader)
				},
				error: function (result, status, error) {//服务器响应失败处理函数
					console.log(apiName, error)
				},
				success: function (result) {
					if (result.success) {
						success(result.data, result.message, result.success)
					} else {
						if (result.code == '3003' || result.code == '3004') {
							//重新获取code登录
							window.reset();
						}

						layer.open({
							content: '<div style="font-size:30px">' + result.message + '</div>'
							, btn: '<div style="font-size:26px;height:50px">我知道了</div>'
						});

					}
				}
			})
		},
		log: L.log,
		getUrlParam: L.getUrlParam,//获取地址栏参数，k未键名
		dateFormat: L.dateFormat,//格式化日期，d未new Date(),fmt为格式
		dateToUnix: L.dateToUnix,//带有“-”格式化日期字符串转unix时间戳
	}
}(JQuery, Lny)

//不引用main.js就是用这个方法获取
getUrlParam = function (k) {//获取地址栏参数，k未键名
	var m = new RegExp("(^|&)" + k + "=([^&]*)(&|$)");
	var r = window.location.search.substr(1).match(m);
	if (r != null) return decodeURI(r[2]); return null;
};

//返回上一级
function goBack(n) {
	var n = n || -1;
	window.history.go(n);
}

// 本地数据 存 取 删  把id传进来 [{id:'#id'},{id:'id1'}]  遍历
function wangLocalAdd(localStorageIds) {
	$.each(localStorageIds, function (i, v) {
		$(v.id).change(function () {
			localStorage.setItem(v.id, JSON.stringify($(this).val()))
		})
	})
}
function wangLocalGet(localStorageIds) {
	$.each(localStorageIds, function (i, v) {
		$(v.id).val(JSON.parse(localStorage.getItem(v.id)))
	})
}
function wangLocalDel(localStorageIds) {
	$.each(localStorageIds, function (i, v) {
		localStorage.removeItem(v.id)
	})
}

//返回后有消息弹出消息
function successMsg(desc, n, time) {//文字消息 返回步数  成功后延伸多久后返回
	var desc = desc || '成功';
	var time = time || '1500';
	var n = n || null;
	layer.open({
		content: desc
		, skin: 'msg'
		, style: "font-size: 28px;padding: 5px"
		, success: function (index) {
			if (n && time) {
				setTimeout(function () {
					goBack(n)
				}, time);
			} else {
				layer.close(index)
			}
		}
	});
}

//图片点击预览方法
function imgLayer(url) {//直接把图片地址传入即可
	layer.open({
		type: 1,
		title: false,
		closeBtn: 1,
		skin: 'layui-layer-nobg', //没有背景色
		shadeClose: true,
		content: '<div"><img style="width:100%;vertical-align:middle;margin:0 auto;max-height:' + (window.innerHeight - 200) + 'px" src="' + url + '" /></div>'
	});
}






