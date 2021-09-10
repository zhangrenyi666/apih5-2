var code = Apih5.getUrlParam('code')
Apih5.setCookie('code', code, 30)
var recordid = l.getUrlParam('id');
var param = {
    recordid: recordid
}
var questionList;
var table = $('#table').DataTable({
    "info": false,// 是否显示数据信息
    "paging": false,// 是否开启自带分页
    "ordering": false, // 是否开启DataTables的高度自适应
    "processing": false,// 是否显示‘进度’提示
    "searching": false,// 是否开启自带搜索
    "autoWidth": false,// 是否监听宽度变化
    "columnDefs": [// 表格列配置	  		
        {
            "targets": [0],
            "data": "rowIndex",
            "title": 'No.',
            "width": 25,
            "render": function (data) {
                return data + 1
            }
        },		
	    {
            "targets": [1],//第几列
            "data": "type",//接口对应字段
            "title": "类型",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data) {
                var text = ""
                switch (data) {
                    case "0": text = "追问";
                        break;
                    case "1": text = "回答";
                        break;
                    default: text = "未知";
                        break;
                }
                return text
            }
        },
		{
            "targets": [2],//第几列
            "data": "askTime",//接口对应字段
            "title": "时间",//表头名
            "defaultContent": "-",//默认显示
			"render": function (data) {
                return l.dateFormat(new Date(data), "yyyy-MM-dd HH:mm:ss");
            }
        },
        {
            "targets": [3],// 第几列
            "data": "askContent",// 接口对应字段
            "title": "内容",// 表头名
            "defaultContent": "-",// 默认显示
        },
		{
            "targets": [4],// 第几列
            "data": "askUser",// 接口对应字段
            "title": "姓名",// 表头名
            "defaultContent": "-",// 默认显示
        },
		{
            "targets": [5],// 第几列
            "data": "voiceList",// 接口对应字段
            "title": "语音",// 表头名
            "defaultContent": "-",// 默认显示
            "render": function (data) {
                var domain = window.apih5.domain;
                var dom = '';
                for (var i = 0; i < data.length; i++) {
                    var voiceUrl = domain + data[i].url,
                        fileName = data[i].voiceName;

                    dom += '<li style="list-style:none;">';
                    dom += '<audio width="300" src="' + voiceUrl + '" controls preload>';
                    dom += '<embed align="bottom"  autostart="false" height="70" width="300" src="' + voiceUrl + '" />';
                    dom += '</audio>';
                    dom += '</li>'; 
                } 
                return dom;
            }
        }
    ]
});
var pager = $("#pager").page({
    remote: {// ajax请求配置
        url: l.getApiUrl("getAskAndAnswerDetail"),
        params: {
            recordid: recordid
        },
        success: function (result) {
            if (result.success) {
                var data = result.data;
                //console.log(data);
                questionList = data[data.length - 1];
                $.each(data, function (index, item) {
                    item.rowIndex = index
                })
                table.clear().rows.add(data).draw();
            } else {
                layer.alert(result.message, { icon: 0, }, function (index) {
                    layer.close(index);
                });
            }
        },
    }
});

// 详情
var detailLayer = $('#detailLayer').detailLayer({
    conditions: [
        {
            type: "hidden",//
            name: "questionId",//
        },
        {
            type: "text",//
            name: "projectName",//
            id: "project1",
            label: "项目",//
            immutableAdd: true,
        },
        {
            type: 'date',//
            name: "dateOfQuestion",//
            label: "日期",//
            dateFmt: "yyyy-MM-dd HH:mm:ss",
            defaultValue: new Date(),
            id: "dateOfQuestion",
            immutableAdd: true
        },
        {
            type: "text",//
            name: "createUserName",//
            label: "问题提问人",//
            immutableAdd: true
        },
        {
            type: "text",//
            name: "departmentName",//
            label: "部门",//
            immutableAdd: true
        },
        {
            type: "text",//
            name: "questionClassName",//
            label: "类别",//
            immutableAdd: true
        },		
        {
            type: "text",//
            name: "questionTitleName",//
            label: "检查项",//
            immutableAdd: true
        },	
        {
            type: "textarea",//textarea   text 
            name: "questionDescribe",//
            label: "问题描述",//
            immutableAdd: true
        },
        {
            type: "upload",//
            name: "askVoiceList",//
            label: "问题语音",//
            immutableAdd: true
        },
        {
            type: "upload",//
            name: "imageListForPc",//
            label: "问题图片",//
            btnName: "添加图片",
            immutableAdd: true
        },
        {
            type: "text",//
            name: "personInChargeUserName",//
            label: "回答人",//
            immutableAdd: true
        },
        {
            type: "textarea",//
            name: "answerContent",//
            label: "回答问题",//
            immutableAdd: true
        },
        {
            type: "upload",//
            name: "answerImageListForPc",//
            label: "回答图片",//
            immutableAdd: true
        },
        {
            type: "upload",//
            name: "voiceList",//
            label: "回答语音",//
            immutableAdd: true
        }
		/*,
        {
            type: "textarea",//textarea   text 
            name: "leaderOption",//
            label: "领导意见",//
        },
		{
            type: "pickTree",
            name: "oacheckUser",
            label: "问题发起人",
            pickType: {
                department: false,//部门列表对应接口字段名,false表示不开启该类
                member: "oaMemberList",//成员列表对应接口字段名,false表示不开启该类
            }
        },
        */		
    ],	
})

// 同意
var agreeLayer = $('#agreeLayer').detailLayer({
    layerTitle:['审批'],  
    layerArea:['50%', '70%'],    
    conditions: [
        {
            type: "hidden",//
            name: "recordid",//
        },
        {
            type: "textarea",//textarea   text 
            name: "leaderOption",//
            label: "领导意见",//
        },
		{
            type: "select",
            name: "checkUserId",
            label: "部门负责人",
			search: true,
            selectList: [//当类型为select时，option配置
            ],
            ajax: {
                api: "getZjXmQuestionOfficeLeaderAllList",
                valueName: "oaUserId",
                textName: "oaUserName"
            },
			must: true
        },
        /*		
		{
            type: "pickTree",
            name: "oacheckUser",
            label: "问题发起人",
            pickType: {
                department: false,//部门列表对应接口字段名,false表示不开启该类
                member: "oaMemberList",//成员列表对应接口字段名,false表示不开启该类
            }
        },		
		*/
    ],	
    onSave: function (obj, _params) {
      l.ajax('leaderAgree', _params, function (data) {
		 layer.confirm("提交成功！", { icon: 1,}, function (index) {				
            obj.close()
		    parent['pager'].page('remote')
            layer_close()				
		 layer.close(index);
            }); 		 
        })
    }	
})

// 驳回
var rejectLayer = $('#rejectLayer').detailLayer({
    layerTitle:['审批'],      
    conditions: [
        {
            type: "hidden",//
            name: "recordid",//
        },
        {
            type: "textarea",//textarea   text 
            name: "leaderOption",//
            label: "领导意见",//
        }
        /*		
		{
            type: "pickTree",
            name: "oacheckUser",
            label: "问题发起人",
            pickType: {
                department: false,//部门列表对应接口字段名,false表示不开启该类
                member: "oaMemberList",//成员列表对应接口字段名,false表示不开启该类
            }
        },		
		*/
    ],	
    onSave: function (obj, _params) {
      l.ajax('leaderReject', _params, function (data) {
		 layer.confirm("提交成功！", { icon: 1,}, function (index) {				
            obj.close()
		    parent['pager'].page('remote')
            layer_close()				
		 layer.close(index);
            }); 		 
        })
    }	
})

$("body").on("click", "button", function () {
    var checkedData = l.getTableCheckedData(table)
    var name = $(this).attr("data-name");
	var values = $('.textarea').eq(2).val();
	var oacheckUser = $('.pickTreeItem').val();
	param.leaderOption = values;
	param.oacheckUser = oacheckUser;
    // console.log(oacheckUser);
	//console.log(param);
	//return;
    switch (name) {
        case "agree":
	    agreeLayer.open(param);				
            break;
	    case "reject":
		//  layer.confirm("确定驳回？", { icon: 0,}, function (index) {
            rejectLayer.open(param);	
			// 	 l.ajax('leaderReject',param,function(res){    
			// 		pager.page('remote')
			// 		parent['pager'].page('remote')
            //         layer_close()
			// 	})
			// 	layer.close(index);
            // });   
            break;
    }
})
//请求数据给第一个表单赋值
myRefresh();
function myRefresh() {
    l.ajax('getQuestionDetail', param, function (data) {
        $('#detailLayer .btn').hide();
        if (data) { 
            var $input = $('#detailLayer input, #detailLayer select, #detailLayer textarea');
            $input.each(function (index, ele) {
                var _name = $(ele).attr('name');
                $(ele).attr('disabled', true);
                //日期和图片列表特殊处理 因为   type值跟文本一样so
                if (_name === 'dateOfQuestion' && data[_name]) {
                    //日期 
                    var toLocaleTimeString = new Date(data[_name]).toLocaleString();
                    var _t = toLocaleTimeString.replace(/[上午|下午|晚上|早上]/gi, '').replace(/(\/)/gi, '-');
                    $(ele).val(_t);
                } else {
                    $(ele).val(data[_name])
                }
            })
			$('.textarea').eq(2).attr('disabled', false);
            if (data && data.imageListForPc) {
                //图片列表处理
                for (var i = 0; i < data.imageListForPc.length; i++) {
                    var domain = window.apih5.domain;
                    var fileUrl = domain + data.imageListForPc[i].fileUrl,
                        fileName = data.imageListForPc[i].fileName;
                    var oArr = fileUrl.split('.');
                    var fileType = oArr[oArr.length - 1];

                    var $uploadItem = $('<li class="uploadItem"></li>');
                    var $uploadItemA = null;
                    if ((/^(png|gif|jpg|jpeg)$/ig).test(fileType)) {
                        $uploadItemA = $('<a href="' + fileUrl + '" target="_block">  <br> <img class="uploadListImgByWangClass" src="' + fileUrl + '">' + fileName + '</a>');
                    } else if ((/^(xls|xlsx|xlsm)$/ig).test(fileType)) {
                        $uploadItemA = $('<a href="' + fileUrl + '" target="_block">  <br> <img class="uploadListImgByWangClass" src="./img/xlsx.png">' + fileName + '</a>');
                    } else if ((/^(doc|docx)$/ig).test(fileType)) {
                        $uploadItemA = $('<a href="' + fileUrl + '" target="_block">  <br> <img class="uploadListImgByWangClass" src="./img/doc.png">' + fileName + '</a>');
                    } else if ((/^(ppt|pptx)$/ig).test(fileType)) {
                        $uploadItemA = $('<a href="' + fileUrl + '" target="_block">  <br> <img class="uploadListImgByWangClass" src="./img/ppt.png">' + fileName + '</a>');
                    } else if ((/^(pdf)$/ig).test(fileType)) {
                        $uploadItemA = $('<a href="' + fileUrl + '" target="_block">  <br> <img class="uploadListImgByWangClass" src="./img/pdf.png">' + fileName + '</a>');
                    } else {
                        $uploadItemA = $('<a href="' + fileUrl + '" target="_block">  <br> <img class="uploadListImgByWangClass" src="./img/wz.png">' + fileName + '</a>');
                    }

                    $uploadItem.append($uploadItemA);
                    $('[data-name=detailLayerimageListForPc] .uploadList').append($uploadItem)
                }
            }
            if (data && data.answerImageListForPc) {
                //图片列表处理
                for (var i = 0; i < data.answerImageListForPc.length; i++) {
                    var domain = window.apih5.domain;
                    var fileUrl = domain + data.answerImageListForPc[i].fileUrl,
                        fileName = data.answerImageListForPc[i].fileName;

                    var oArr = fileUrl.split('.');
                    var fileType = oArr[oArr.length - 1];

                    var $uploadItem = $('<li class="uploadItem"></li>');
                    var $uploadItemA = null;

                    if ((/^(png|gif|jpg|jpeg)$/ig).test(fileType)) {
                        $uploadItemA = $('<a href="' + fileUrl + '" target="_block">  <br> <img class="uploadListImgByWangClass" src="' + fileUrl + '">' + fileName + '</a>');
                    } else if ((/^(xls|xlsx|xlsm)$/ig).test(fileType)) {
                        $uploadItemA = $('<a href="' + fileUrl + '" target="_block">  <br> <img class="uploadListImgByWangClass" src="./img/xlsx.png">' + fileName + '</a>');
                    } else if ((/^(doc|docx)$/ig).test(fileType)) {
                        $uploadItemA = $('<a href="' + fileUrl + '" target="_block">  <br> <img class="uploadListImgByWangClass" src="./img/doc.png">' + fileName + '</a>');
                    } else if ((/^(ppt|pptx)$/ig).test(fileType)) {
                        $uploadItemA = $('<a href="' + fileUrl + '" target="_block">  <br> <img class="uploadListImgByWangClass" src="./img/ppt.png">' + fileName + '</a>');
                    } else if ((/^(pdf)$/ig).test(fileType)) {
                        $uploadItemA = $('<a href="' + fileUrl + '" target="_block">  <br> <img class="uploadListImgByWangClass" src="./img/pdf.png">' + fileName + '</a>');
                    } else {
                        $uploadItemA = $('<a href="' + fileUrl + '" target="_block">  <br> <img class="uploadListImgByWangClass" src="./img/wz.png">' + fileName + '</a>');
                    }

                    $uploadItem.append($uploadItemA);
                    $('[data-name=detailLayeranswerImageListForPc] .uploadList').append($uploadItem)
                }
            }
			
            if (data && data.askVoiceList) {
                //提问语音

                for (var i = 0; i < data.askVoiceList.length; i++) {
                    var domain = window.apih5.domain;
                    var fileUrl = domain + data.askVoiceList[i].url,
                        fileName = data.askVoiceList[i].voiceName;
                    var $uploadItem = $('<li class="uploadItem"></li>');

                    var voiceListJsx = '';
                    voiceListJsx += '<audio width="300" src="' + fileUrl + '" controls preload>';
                    voiceListJsx += '<embed align="bottom"  autostart="false" height="70" width="300" src="' + fileUrl + '" />';
                    voiceListJsx += '</audio>';

                    $uploadItem.append(voiceListJsx);
                    $('[data-name=detailLayeraskVoiceList] .uploadList').append($uploadItem);
                }
            }

            if (data && data.voiceList) {
                //回答语音
                for (var i = 0; i < data.voiceList.length; i++) {
                    var domain = window.apih5.domain;
                    var fileUrl = domain + data.voiceList[i].url,
                        fileName = data.voiceList[i].voiceName;
                    var $uploadItem = $('<li class="uploadItem"></li>');
                    var _voiceListJsx = '';

                    voiceListJsx += '<audio width="300" src="' + fileUrl + '" controls preload>';
                    voiceListJsx += '<embed align="bottom"  autostart="false" height="70" width="300" src="' + fileUrl + '" />';
                    voiceListJsx += '</audio>';

                    $uploadItem.append(voiceListJsx);
                    $('[data-name=detailLayervoiceList] .uploadList').append($uploadItem);
                }
            }
        } 
    })
}
