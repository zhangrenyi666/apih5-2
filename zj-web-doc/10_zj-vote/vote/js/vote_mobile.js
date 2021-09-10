/**
 * 初期化
 */
$(function () {

		$('#voteId').val(Apih5.getUrlParam('voteId'))
		$('#previewFlag').val(Apih5.getUrlParam('previewFlag'));



		initData(0, 2);

		var unfilled = [];//未选题目的索引值
		//$(".per_info_b").click(function () {
		$(document).on('click', '.per_info_b', function () {
			if ($(this).attr("data-state") == 0) {
				$(this).attr("data-state", "1").text("收起更多")
				$(this).prev(".per_info_m").removeClass("hide")
			} else {
				$(this).attr("data-state", "0").text("展开更多")
				$(this).prev(".per_info_m").addClass("hide")
			}
		})
		$(".que_item").click(function () {
			$(this).removeClass("unfilled")
		})

		// 提交上一页
		$(".con_submit_up").click(function () {
			unfilled = [];
			// 判断是否成果获取答题数据，把没有填写的题目push进unfilled,例如unfilled.push(0)
			if (unfilled.length > 0) {
				var items = $(".que_item");
				items.removeClass("unfilled")
				for (var i = 0; i < unfilled.length; i++) {
					items.eq(unfilled[i]).addClass("unfilled");
				}
				var h = items.eq(unfilled[0]).offset().top - 50;
				$("html, body").animate({ scrollTop: h }, 300);
			}
			else {
				updateData(0);
			}

		})
		// 提交下一页
		$(".con_submit_next").click(function () {
			unfilled = [];
			if (unfilled.length > 0) {
				var items = $(".que_item");
				items.removeClass("unfilled")
				for (var i = 0; i < unfilled.length; i++) {
					items.eq(unfilled[i]).addClass("unfilled");
				}
				var h = items.eq(unfilled[0]).offset().top - 50;
				$("html, body").animate({ scrollTop: h }, 300);
			}
			else {
				updateData(1);
			}
		})



		// 最终提交
		$(".con_submit_end").click(function () {
			unfilled = [];
			if (unfilled.length > 0) {
				var items = $(".que_item");
				items.removeClass("unfilled")
				for (var i = 0; i < unfilled.length; i++) {
					items.eq(unfilled[i]).addClass("unfilled");
				}
				var h = items.eq(unfilled[0]).offset().top - 50;
				$("html, body").animate({ scrollTop: h }, 300);
			}
			else {
				updateData(2);
			}
		})





})

// 初始化数据
function initData(startNum, pageNumFlag) {
	var previewFlag = $("#previewFlag").val();
	// var voteId = $("#voteId").val();
	var voteId = Apih5.getUrlParam('id');
	var previewFlag = Apih5.getUrlParam('previewFlag');
	var startNum = $("#startNum").val();
	var params = {
		voteId: voteId,
		startNum: startNum,
		previewFlag: previewFlag
	};

	l.ajax('getVoteDetailedPreview', params, function (result) {
	console.log(result)
		if (previewFlag == 1) {
			voteInfo(result);
			candidate(result)
			questions(result)
		}
		else {
			// 投票时间过期
			var nowtime = new Date(getNowTime());
			var endDate = new Date(result.endDate);
			var readOnly = false;
			// 投票结束
			// 投票者已经投票过，不允许二次投票
			if (endDate <= nowtime || result.data.state == 2 || result.data.voterList[0]["waiverFlag"] == 1) {
				readOnly = true;
			}
			if (!readOnly || (result.showFlag == 0 && readOnly)) {
				voteInfo(result);
				candidate(result)
				questions(result)
			}
			else {
				layer.open({
					content: '无权限查看'
					, skin: 'msg'
					, time: 2 //2秒后自动关闭
				})
				setTimeout(function () {
					history.go(-1)
				}, 2000)
				// window.location.href = "http://haiwei-oa.fheb.cn:89/haiwei/www/client/html/subscribe_qrcode.html";
			}

			if (readOnly) {
				$(":checkbox,:radio").attr("disabled", "disabled");
				$("textarea").attr("readOnly", true);
				$("#conItem2").hide();
			}
			// 投票结束
			if (readOnly) {
				$(":checkbox,:radio").attr("disabled", "disabled");
				$("textarea").attr("readOnly", true);
				$("#conItem2").hide();
			}
			// 投票者已经投票过，不允许二次投票
			if (readOnly) {
				$(":checkbox,:radio").attr("disabled", "disabled");
				$("textarea").attr("readOnly", true);
				$("#conItem2").hide();
			}
		}

	})



}

/**
 * 投票基本信息
 * 
 * @param {} result
 */
function voteInfo(result) {
	obj = result;
	voterList = result.voterList;
	$str = '';
	$str += "<input name='voterId' type='hidden' value=" + voterList[0]["voterId"] + ">";
	$str += "<h1 class='pro_h1'>" + obj.voteTitle + "</h1>";
	$str += "<ul class='pro_info'>";
	$str += "    <li>";
	$str += "        <div class='pro_info_tit'>投票开始时间:</div>";
	$str += "        <div class='pro_info_con'>" + new Date(obj.startDate).Format("yyyy-MM-dd hh:mm:ss") + "</div>";
	$str += "    </li>";
	$str += "    <li>";
	$str += "        <div class='pro_info_tit'>投票结束时间:</div>";
	$str += "        <div class='pro_info_con'>" + new Date(obj.endDate).Format("yyyy-MM-dd hh:mm:ss") + "</div>";
	$str += "    </li>";
	$str += "</ul>";

	$("#voteInfo").html($str);
}

/**
 * 候选人信息
 * 
 * @param {} result
 */
function candidate(result) {
	obj = result.voteCandidateList
	var n = result.startNum;
	if ((n + 1) == obj.length) {
		$("#conItem").hide();
		$("#conItem2").show();
	}
	else {
		$("#conItem2").hide();
		$("#conItem").show();
	}
	if (n == 0) {
		$("#upPageItem").hide();
	}
	else {
		$("#upPageItem").show();
	}

	$str = '';
	$str += "<div class='per_info_t' >";
	if (obj[n]["image"] != null && obj[n]["image"] != "") {
		$str += "<div class='per_info_img'>";
		//$str+=" <img stc=\""+obj[n]["image"]+"\">   <img/>";
		var img = obj[n]["image"];
		img = img.substring(img.indexOf('|') + 1);
		$str += "<img src=\"" + img + "\">";
		$str += "</div>";
		$str += "<ul class='per_info_base'>";
	}
	else {
		$str += "<ul class='per_info_base' style='padding-left: 0px;'>";
	}

	if (obj[n]["contentOne"] != null && obj[n]["contentOne"] != "投票类型固定值") {



		if (obj[n]["titleOne"] != null && obj[n]["titleOne"] != '') {
			$str += "    <li>";
			$str += "        <div class='per_info_tit'>" + obj[n]["titleOne"] + ":</div>";
			$str += "        <div class='per_info_con'>" + obj[n]["contentOne"] + "</div>";
			$str += "    </li>";
		}
		if (obj[n]["titleTwo"] != null && obj[n]["titleTwo"] != '') {
			$str += "    <li>";
			$str += "        <div class='per_info_tit'>" + obj[n]["titleTwo"] + ":</div>";
			$str += "        <div class='per_info_con'>" + obj[n]["contentTwo"] + "</div>";
			$str += "    </li>";
		}
		if (obj[n]["titleThree"] != null && obj[n]["titleThree"] != '') {
			$str += "    <li>";
			$str += "        <div class='per_info_tit'>" + obj[n]["titleThree"] + ":</div>";
			$str += "        <div class='per_info_con'>" + obj[n]["contentThree"] + "</div>";
			$str += "    </li>";
		}
		if (obj[n]["titleFour"] != null && obj[n]["titleFour"] != '') {
			$str += "    <li>";
			$str += "        <div class='per_info_tit'>" + obj[n]["titleFour"] + ":</div>";
			$str += "        <div class='per_info_con'>" + obj[n]["contentFour"] + "</div>";
			$str += "    </li>";
		}
		if (obj[n]["titleFive"] != null && obj[n]["titleFive"] != '') {
			$str += "    <li>";
			$str += "        <div class='per_info_tit'>" + obj[n]["titleFive"] + ":</div>";
			$str += "        <div class='per_info_con'>" + obj[n]["contentFive"] + "</div>";
			$str += "    </li>";
		}
	}

	$str += "</ul>";
	$str += "</div>";
	$str += "<input name='candidateId' type='hidden' value=" + obj[n]["candidateId"] + ">";

	//$('#candidateInfo').append($str);
	$("#candidateInfo").html($str);

	if (obj[n]["contentOne"] != null && obj[n]["contentOne"] == "投票类型固定值") {
		$("#candidateInfo").hide();
	}
}

/**
 * 遍历试题
 * 
 * @param {} result
 */
function questions(result) {
	$str = '';
	obj = result.voteQuestionsList
	$.each(obj, function (n, value) {
		objDetails = value.questionsDetailsList;
		// 1：打分；2：单选；3：多选；4问答
		if (value.questionsDifCode == "1") {
			$str += "<li class='que_item must'>";
			$str += "    <dl>";
			$str += "        <dt class='que_tit'>" + (n + 1) + "、" + value.title + "<i>（打分）</i></dt>";

			$.each(objDetails, function (m, details) {
				if (m % 3 == 0) {
					$str += "        <dd>";
				}
				if (details.answerFlag == "1") {
					$str += "            <label>" + questionsNo(value.questionsNo, m) + "<input type='radio' name='" + n + "'  checked='checked' value='" + details.questionsId + "_" + details.questionsDetailsId + "_" + details.content + "'><span class='que_opt'>" + details.content + "分</span></label>";
				}
				else {
					$str += "            <label>" + questionsNo(value.questionsNo, m) + "<input type='radio' name='" + n + "' value='" + details.questionsId + "_" + details.questionsDetailsId + "_" + details.content + "'><span class='que_opt'>" + details.content + "分</span></label>";
				}
				if (m + 1 % 3 == 0) {
					$str += "        </dd>";
				}
			});

			$str += "    </dl>";
			$str += "</li>";
		}
		else if (value.questionsDifCode == "2") {
			$str += "<li class='que_item must'>";
			$str += "   <dl>";
			$str += "       <dt class='que_tit'>" + (n + 1) + "、" + value.title + "<i>（单选）</i></dt>";
			$.each(objDetails, function (m, details) {
				$str += "       <dd>";
				if (details.answerFlag == "1") {
					$str += "            <label>" + questionsNo(value.questionsNo, m) + "<input type='radio' name='" + n + "' checked='checked' value='" + details.questionsId + "_" + details.questionsDetailsId + "_" + details.content + "'><span class='que_opt'>" + details.content + "</span></label>";
				}
				else {
					$str += "            <label>" + questionsNo(value.questionsNo, m) + "<input type='radio' name='" + n + "' value='" + details.questionsId + "_" + details.questionsDetailsId + "_" + details.content + "'><span class='que_opt'>" + details.content + "</span></label>";
				}
				$str += "        </dd>";
			});
			$str += "   </dl>";
			$str += "</li>";
		}
		else if (value.questionsDifCode == "3") {
			$str += "<li class='que_item must'>";
			$str += "    <dl>";
			$str += "        <dt class='que_tit'>" + (n + 1) + "、" + value.title + "<i>（多选）</i></dt>";

			$.each(objDetails, function (m, details) {
				$str += "        <dd>";
				if (details.answerFlag == "1") {
					$str += "            <label>" + questionsNo(value.questionsNo, m) + "<input type='checkbox' name='" + n + "' checked='checked' value='" + details.questionsId + "_" + details.questionsDetailsId + "_" + details.content + "'><span>" + details.content + "</span></label>";
				}
				else {
					$str += "            <label>" + questionsNo(value.questionsNo, m) + "<input type='checkbox' name='" + n + "' value='" + details.questionsId + "_" + details.questionsDetailsId + "_" + details.content + "'><span>" + details.content + "</span></label>";
				}
				$str += "        </dd>";
			});

			//$str+="        <dd>";
			//$str+="            <label><span class='que_rem'>备注:</span><input class='que_text' placeholder='备注'></label>";
			//$str+="        </dd>";
			$str += "    </dl>";
			$str += "</li>";
		}
		else if (value.questionsDifCode == "4") {
			$str += "<li class='que_item'>";
			$str += "    <dl>";
			$str += "        <dt class='que_tit'>" + (n + 1) + "、" + value.title + "<i>（问答）</i></dt>";
			$.each(objDetails, function (m, details) {
				//$str+="        <dd>";
				// $str+="            <label><span class='que_sub'>副标题副标题副副标题</span></label>";
				//$str+="        </dd>";
				$str += "        <dd>";
				if (details.answerFlag == "1") {
					$str += "            <label><textarea class='que_text' name='" + n + "' rows='" + n + "' cols='100%' value='" + details.questionsId + "_" + details.questionsDetailsId + "'  placeholder='请输入内容'>" + details.answer + "</textarea></label>";
				}
				else {
					$str += "            <label><textarea class='que_text' name='" + n + "' rows='" + n + "' cols='100%' value='" + details.questionsId + "_" + details.questionsDetailsId + "'  placeholder='请输入内容'>" + details.content + "</textarea></label>";
				}
				$str += "        </dd>";
				$str += "<input name=h" + n + " type='hidden' value='" + details.questionsId + "_" + details.questionsDetailsId + "'>";
			});
			$str += "    </dl>";
			$str += "</li>";
		}
		//$('#questionsInfo').append($str);
	});
	$("#questionsInfo").html($str);
}

/**
 * 提交数据
 */
function updateData(pageNumFlag) {
console.log("二"+pageNumFlag);
	var voteId = $("#voteId").val();
	var startNum = $("#startNum").val();
	var previewFlag = $("#previewFlag").val();
	// 上一页
	if (pageNumFlag == 0) {
		startNum--;
	}
	// 下一页
	else if (pageNumFlag == 1) {
		startNum++;
	}
	// 候选人
	var candidate = { candidateId: $("[name='candidateId']").val() };
	var voteCandidateList = [];
	voteCandidateList.push(candidate);

	// 投票者
	var voter = { voterId: $("[name='voterId']").val() };
	var voterList = [];
	voterList.push(voter);

	var unfilled = [];
	var voterAnswerList = [];
	var queItemList = $(".que_item");
	for (var i = 0; i < queItemList.length; i++) {
		var queItem = queItemList[i];
		var id = $(queItem).find(".que_id").val();
		// 1：打分；2：单选；3：多选；4问答
		if ($(queItem).find("input[type=radio]").length > 0) {
			var radio = $(queItem).find("input[type=radio]:checked");
			if (radio.length > 0) {
				var val = $(radio).val();
				var qid = val.split("_")[0];
				var detid = val.split("_")[1];
				var answer = val.split("_")[2];
				var ans = { questionsId: qid, questionsDetailsId: detid, answer: answer };
				voterAnswerList.push(ans);
			}
			else {
				unfilled.push(i);
			}
		} else if ($(queItem).find("input[type=checkbox]").length > 0) {
			var checkboxs = $(queItem).find("input[type=checkbox]:checked");
			if (checkboxs.length > 0) {
				var qid = "";
				var detid = "";
				for (var j = 0; j < checkboxs.length; j++) {
					var checkbox = checkboxs[j];
					var val = $(checkbox).val();
					qid = val.split("_")[0];
					detid = val.split("_")[1];
					answer = val.split("_")[2];
					var ans = { questionsId: qid, questionsDetailsId: detid, answer: answer };
					voterAnswerList.push(ans);
				}
			}
			else {
				unfilled.push(i);
			}
		} else if ($(queItem).find("textarea").length > 0) {
			var textarea = $(queItem).find("textarea");
			var answer = textarea.val();
			var val = $(textarea).attr("value");
			var qid = val.split("_")[0];
			var detid = val.split("_")[1];
			if (answer != null && "" != answer) {
				var ans = { questionsId: qid, questionsDetailsId: detid, answer: answer };
				voterAnswerList.push(ans);
			}
			else {
				unfilled.push(i);
			}
		}
	}

	// check
	if (unfilled.length > 0 && previewFlag != 1) {
		var items = $(".que_item");
		items.removeClass("unfilled")
		for (var i = 0; i < unfilled.length; i++) {
			items.eq(unfilled[i]).addClass("unfilled");
		}
		var h = items.eq(unfilled[0]).offset().top - 50;
		$("html, body").animate({ scrollTop: h }, 300);
	}
	else {
		endFlag = "";
		if (pageNumFlag == 2) {
			endFlag = "end";
		}
		var params = {
			voteId: Apih5.getUrlParam('id'),
			startNum: startNum,
			endFlag: endFlag,
			voterAnswerList: voterAnswerList,
			voteCandidateList: voteCandidateList,
			voterList: voterList
		};

		if (previewFlag == 1) {
			if (pageNumFlag == 2) {
				alert('投票成功！');
				// WeixinJSBridge.call('closeWindow');
				window.history.go(-1)
			}
			else {
				startNum = $("#startNum").val();
				// 上一页
				if (pageNumFlag == 0) {
					startNum--;
				}
				// 下一页
				else if (pageNumFlag == 1) {
					startNum++;
				}
				$("#startNum").val(startNum);
				initData(startNum, pageNumFlag);
			}
		}
		else { 
			l.ajax('addAnswerInfo', params, function (result) {
				if (pageNumFlag == 2) {
					alert('投票成功！');
					history.go(-1)
					// WeixinJSBridge.call('closeWindow');
				}
				else {
					$("#startNum").val(result.startNum);
					initData(result.startNum, pageNumFlag);
				}
				if (result.success == "true") {
				} else {
				}
			}) 

		}
	}
}

/**
 * 返回序号
 */
function questionsNo(questionsNoFlag, num) {
	// 无排序
	if (questionsNoFlag == 0) {
		return "";
	}
	// 字母排序
	else if (questionsNoFlag == 1) {
		return String.fromCharCode(65 + parseInt(num++)) + "、 ";
	}
	// 数字排序
	else if (questionsNoFlag == 2) {
		var numValue = num;
		num = parseInt(numValue++);
		return parseInt(numValue++) + "、 ";
	}
}

// 当前时间获取
function getNowTime() {
	var nowtime = new Date();
	var year = nowtime.getFullYear();
	var month = padleft0(nowtime.getMonth() + 1);
	var day = padleft0(nowtime.getDate());
	var hour = padleft0(nowtime.getHours());
	var minute = padleft0(nowtime.getMinutes());
	var second = padleft0(nowtime.getSeconds());
	var millisecond = nowtime.getMilliseconds(); millisecond = millisecond.toString().length == 1 ? "00" + millisecond : millisecond.toString().length == 2 ? "0" + millisecond : millisecond;
	return year + "-" + month + "-" + day + " " + hour + ":" + minute + ":" + second;
}
//补齐两位数  
function padleft0(obj) {
	return obj.toString().replace(/^[0-9]{1}$/, "0" + obj);
}

Date.prototype.Format = function (fmt) { //author: meizz 
	var o = {
		"M+": this.getMonth() + 1, //月份 
		"d+": this.getDate(), //日 
		"h+": this.getHours(), //小时 
		"m+": this.getMinutes(), //分 
		"s+": this.getSeconds(), //秒 
		"q+": Math.floor((this.getMonth() + 3) / 3), //季度 
		"S": this.getMilliseconds() //毫秒 
	};
	if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
	for (var k in o)
		if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
	return fmt;
}