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
            "width": 13,
            "title": '<input type="checkbox">',
            "render": function (data) {
                return '<input type="checkbox" name="checkbox" data-rowIndex="' + data + '" >';
            }
        },
        {
            "targets": [1],
            "data": "rowIndex",
            "title": 'No.',
            "width": 25,
            "render": function (data) {
                return data + 1
            }
        },
        {
            "targets": [2],//第几列
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
            "targets": [3],//第几列
            "data": "askTime",//接口对应字段
            "title": "时间",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data) {
                return l.dateFormat(new Date(data), "yyyy-MM-dd HH:mm:ss");
            }
        },
        {
            "targets": [4],// 第几列
            "data": "askContent",// 接口对应字段
            "title": "内容",// 表头名
            "defaultContent": "-",// 默认显示
        },
        {
            "targets": [5],// 第几列
            "data": "askUser",// 接口对应字段
            "title": "姓名",// 表头名
            "defaultContent": "-",// 默认显示
        },
        {
            "targets": [6],// 第几列
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
    ],

})
//追加回答
var answerLayer = $('#answerLayer').detailLayer({
    layerTitle:['回答'],      
    layerArea: ['40%', '40%'],
    conditions: [
        {
            type: "hidden",//
            name: "askId"//
        },
        {
            type: "hidden",//
            name: "questionApprovalId"//
        },
        {
            type: "hidden",//
            name: "checkDepartmentName"//
        },                 
        {
            type: "hidden",//
            name: "qAFlag"//
        },
        {
            type: "textarea",//
            name: "answerContent",//
            label: "回答",//
        },
    ],
    onSave: function (obj, _params) {
        var qAFlag = _params.qAFlag;
        _params.firstAnswerFlag = '1'
        if (qAFlag == "1") {
            layer.alert("只能选择问题回答", { icon: 0, }, function (index) {
                layer.close(index);
            });
        } else {
            l.ajax('addZjQuestionAnswer', _params, function (data) {
                layer.alert("操作成功！", { icon: 1, }, function (index) {
                    pager.page('remote')
                    obj.close()
                    layer.close(index);
                });
            })
        }
    },
})
$("body").on("click", "button", function () {
    var checkedData = l.getTableCheckedData(table)
    var name = $(this).attr("data-name");
    var params;
    switch (name) {
        case "answer":
            if (checkedData.length == 1) {
                if (checkedData[0].type == 1) {
                    layer.alert("该条是回答，请选择追问", { icon: 0, }, function (index) {
                        layer.close(index);
                    });
                } else {
                    checkedData[0].questionApprovalId = param.recordid;
                    checkedData[0].checkDepartmentName = param.checkDepartmentName;
                    answerLayer.open(checkedData[0]);
                    $(".layui-layer-title").html('我的回答');
                }
            } else if (checkedData.length == 0) {
                layer.alert("未选择任何项", { icon: 0, }, function (index) {
                    layer.close(index);
                });
            } else {
                layer.alert("只能选择一个", { icon: 0, }, function (index) {
                    layer.close(index);
                });
            }
            break;
        case "close":
            //pager.page('remote')
            parent['pager'].page('remote')
			//parent.close(false);
            //parent.close(parent.myOpenLayer)			
			parent.layer.closeAll()
            break;
    }
})

//请求数据给第一个表单赋值
myRefresh();
function myRefresh() {
    l.ajax('getQuestionDetail', param, function (data) {
        param.questionApprovalId = data.recordid;
        param.checkDepartmentName = data.checkDepartmentName;
        $('#detailLayer .btn').hide();
        if (data) {
            var $input = $('#detailLayer input, #detailLayer select,#detailLayer textarea');
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
            $('.textarea').eq(0).attr('disabled', true);
            $('.textarea').eq(1).attr('disabled', true);
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