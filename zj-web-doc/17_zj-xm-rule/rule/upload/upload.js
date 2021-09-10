if (!accessoryList) {
	var accessoryList = {};
}
$("body").on('click', '.uploadFile .uploadRemove', function () {
	var dataName = $(this).parents(".uploadFile").attr("data-name")
	var index = $(this).attr("data-index")
	accessoryList[dataName].splice(index, 1)
	updateUploadList(dataName, accessoryList[dataName])
})
$("body").on('click', '.uploadFile .inputFile', function (e) {
	var dataName = $(this).parents(".uploadFile").attr("data-name");
	var maxLen = $(this).parents(".uploadFile").attr("data-maxLen");
	var curLen = getUploadList(dataName).length;
	if (curLen >= maxLen) {
		layer.alert("上传文件不能超过" + maxLen + "个", { icon: 0, }, function (index) {
			layer.close(index);
		});
		return false
	}
})
$("body").on('change', '.uploadFile .inputFile', function () {
	var projectName = $(this).parents(".uploadFile").attr("data-projectName");
	var uploadUrl = $(this).parents(".uploadFile").attr("data-uploadUrl") || 'uploadFiles.do';
	var fileElementId = $(this).attr("id");
	var dataName = $(this).parents(".uploadFile").attr("data-name")
	var apkIntroduce = $(this).val();
	var filename = apkIntroduce.replace(/.*(\/|\\)/, "");
	var fileExt = (/[.]/.exec(filename)) ? /[^.]+$/.exec(filename.toLowerCase()) : '';
	var fileType = JSON.parse($(this).attr("data-fileType"))
	var typeState = false;
	if (fileType.length > 0) {
		for (var j = 0; j < fileType.length; j++) {
			if (fileType[j] == fileExt) {
				typeState = true
				break
			}
		}
	} else {
		typeState = true
	}
	if (!typeState) {
		layer.alert("上传文件格式不符合要求！！", { icon: 0, }, function (index) {
			layer.close(index);
		});
		return
	}
	var loader = layer.load(1, {
		shade: [0.3, '#000'] //0.1透明度的白色背景
	});
	$.ajaxFileUpload({
		url: l.domain + uploadUrl + (projectName ? ('?projectName=' + projectName) : ''), //用于文件上传的服务器端请求地址
		secureuri: false, //是否需要安全协议，一般设置为false
		fileElementId: fileElementId, //文件上传域的ID
		dataType: 'text', //返回值类型 一般设置为json
		complete: function (result) {
			$(".uploadFile[data-name=" + dataName + "] .inputFile").val("")
			layer.close(loader)
		},
		success: function (data, status) { //服务器成功响应处理函数
			data = JSON.parse(data)
			if (data.success == "true") {
				if (accessoryList[dataName]) {
					accessoryList[dataName].push({
						fileId: data.fileId || "",
						fileName: data.fileName || "",
						fileUrl: data.fileUrl || "",
						realUrl: data.realUrl || "",
						thumbPath: data.thumbPath || "",
						fileSize: data.fileSize || ""
					})
				} else {
					accessoryList[dataName] = [];
					accessoryList[dataName].push({
						fileId: data.fileId || "",
						fileName: data.fileName || "",
						fileUrl: data.fileUrl || "",
						realUrl: data.realUrl || "",
						thumbPath: data.thumbPath || "",
						fileSize: data.fileSize || ""
					})
				}
				updateUploadList(dataName, accessoryList[dataName])
			} else {
				Lny.log(data.message)
			}
		},
		error: function (data, status, e) {//服务器响应失败处理函数
			Lny.log(e)
		}
	})
})
function updateUploadList(dataName, _uploadList, disabled) {
	$(".uploadFile[data-name=" + dataName + "] .uploadList").html("");
	if (disabled) {
		$(".uploadFile[data-name=" + dataName + "]>label,.uploadFile[data-name=" + dataName + "]>span").hide();
	} else {
		$(".uploadFile[data-name=" + dataName + "]>label,.uploadFile[data-name=" + dataName + "]>span").show();
	}
	if (disabled && !_uploadList.length > 0) {
		$(".uploadFile[data-name=" + dataName + "] .uploadList").append("<li class='uploadItem'>无附件</li>")
	} else {
		$.each(_uploadList, function (i, v) {
			var fileUrl = v.url || v.fileUrl
			if (new RegExp("http://").test(fileUrl) || new RegExp("https://").test(fileUrl)) {
				fileUrl = fileUrl
			} else {
				fileUrl = l.domain + fileUrl
			}
			fileUrl = decodeURI(fileUrl);
			var oArr = fileUrl.split('.');
			var fileType = oArr[oArr.length - 1];
			// var _url = '../../../weblib/public/upload/'
			var _url = 'http://weixin.fheb.cn:90/cdn/public/upload/'
			if ((/^(png|gif|jpg|jpeg)$/ig).test(fileType)) {
				$(".uploadFile[data-name=" + dataName + "] .uploadList").append("<li class='uploadItem'><a  onClick=\"window.open(\'" + encodeURI(fileUrl) + "\');\" href='javascript:void(0);'>  <br /> <img class='uploadListImgByWangClass' src='" + encodeURI(fileUrl) + "'/>  " + v.fileName + "</a>" + (disabled ? "" : "<i data-index='" + i + "' class='uploadRemove icon Hui-iconfont'></i>") + "</li>")
			} else if ((/^(xls|xlsx|xlsm)$/ig).test(fileType)) {
				$(".uploadFile[data-name=" + dataName + "] .uploadList").append("<li class='uploadItem'><a  onClick=\"window.open(\'" + encodeURI(fileUrl) + "\');\" href='javascript:void(0);'>  <br /> <img class='uploadListImgByWangClass' src='" + _url + "img/xlsx.png'/>  " + v.fileName + "</a>" + (disabled ? "" : "<i data-index='" + i + "' class='uploadRemove icon Hui-iconfont'></i>") + "</li>")
			} else if ((/^(doc|docx)$/ig).test(fileType)) {
				$(".uploadFile[data-name=" + dataName + "] .uploadList").append("<li class='uploadItem'><a  onClick=\"window.open(\'" + encodeURI(fileUrl) + "\');\" href='javascript:void(0);'>  <br /> <img class='uploadListImgByWangClass' src='" + _url + "img/doc.png'/>  " + v.fileName + "</a>" + (disabled ? "" : "<i data-index='" + i + "' class='uploadRemove icon Hui-iconfont'></i>") + "</li>")
			} else if ((/^(ppt|pptx)$/ig).test(fileType)) {
				$(".uploadFile[data-name=" + dataName + "] .uploadList").append("<li class='uploadItem'><a  onClick=\"window.open(\'" + encodeURI(fileUrl) + "\');\" href='javascript:void(0);'>  <br /> <img class='uploadListImgByWangClass' src='" + _url + "img/ppt.png'/>  " + v.fileName + "</a>" + (disabled ? "" : "<i data-index='" + i + "' class='uploadRemove icon Hui-iconfont'></i>") + "</li>")
			} else if ((/^(pdf)$/ig).test(fileType)) {
				$(".uploadFile[data-name=" + dataName + "] .uploadList").append("<li class='uploadItem'><a  onClick=\"window.open(\'" + encodeURI(fileUrl) + "\');\" href='javascript:void(0);'>  <br /> <img class='uploadListImgByWangClass' src='" + _url + "img/pdf.png'/>  " + v.fileName + "</a>" + (disabled ? "" : "<i data-index='" + i + "' class='uploadRemove icon Hui-iconfont'></i>") + "</li>")
			} else {
				$(".uploadFile[data-name=" + dataName + "] .uploadList").append("<li class='uploadItem'><a  onClick=\"window.open(\'" + encodeURI(fileUrl) + "\');\" href='javascript:void(0);'>  <br /> <img class='uploadListImgByWangClass' src='" + _url + "img/wz.png'/>  " + v.fileName + "</a>" + (disabled ? "" : "<i data-index='" + i + "' class='uploadRemove icon Hui-iconfont'></i>") + "</li>")
			}
		})
  
		//增加悬浮时将全名显示出来
		$('.uploadItem').find('a').hover(function () {
			fileNameHover(0, $(this))
		}, function () {
			fileNameHover(1)
		})
	}

}
function getUploadList(dataName) {
	return accessoryList[dataName]
}
function resetUploadList(dataName, _uploadList, disabled) {
	accessoryList[dataName] = [];
	accessoryList[dataName] = _uploadList;
	updateUploadList(dataName, accessoryList[dataName], disabled ? disabled : false)
}

//文件名被悬浮时出现
function fileNameHover(hoverType, me) {
	//轮播内容被hover时 
	if (hoverType === 0) {
		//hover进入
		layer.tips(me.text(), me, {
			tips: [1, '#3595CC'],
			time: 0
		});
	} else {
		//hover出去 
		layer.closeAll('tips')
	}
}