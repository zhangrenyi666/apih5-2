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
                //console.log("数据",data);
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

//三种身份的人共同查看详情页面
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
            name: "checkDepartmentName",//
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
        /*		
		{
            type: "address",
            name: "questionClassId",
			id: "questionClassId",
            label: "地址",
            must: true,
            //defaultValue: '0_0_0',
            ajax: {
                api: "getQuestionSelectThreeAllList",
                valueName: "number",
                textName: "dzmc",
                childrenName: "addressCodeList",//设置children对应的接口字段名称
            }
        },		
		*/
        {
            type: "textarea",//textarea   text 
            name: "questionDescribe",//
            label: "问题描述",//
            immutableAdd: true,
            render: function (v) {
                //console.log(v)
            }
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
            label: "问题附件",//
            btnName: "添加图片",
            projectName: "zj_question_picture",
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
            disabled: true
        },
        {
            type: "upload",//
            name: "answerImageListForPc",//
            label: "回复附件",//
            btnName: "添加图片",
            projectName: "zj_question_picture",
        },
        {
            type: "upload",//
            name: "voiceList",//
            label: "回答语音",//
            immutableAdd: true
        },
        {
            type: "select",
            name: "leaderApprovalResult",
            label: "领导审批结果",
            immutableAdd: true,
            selectList: [//当类型为select时，option配置  0：未审批 1：审批中  2：通过 3：未通过   4：问题关闭
                {
                    value: "",
                    text: "未选择"
                },
                {
                    value: "0",
                    text: "同意"
                },
                {
                    value: "1",
                    text: "驳回"
                }
            ],
        },
        {
            type: "textarea",//
            name: "leaderApprovalOpinion",//
            label: "领导意见",//
            immutableAdd: true
        }
    ],

})


$("body").on("click", "button", function () {
    var checkedData = l.getTableCheckedData(table)
    var name = $(this).attr("data-name");
    var params;
    switch (name) {
        case "close":
            layer.confirm("确定关闭问题？", { icon: 0, }, function (index) {
                l.ajax("questionClose", param, function (data) {
                    pager.page('remote')
                    parent['pager'].page('remote')
                    layer_close()
                })
                layer.close(index);
            });
            break;
        case "remind":
            l.ajax('getQuestionDetail', param, function (data) {
                var allData = data;
                if (allData.questionState == '4') {
                    layer.alert("问题关闭，不需要提醒", { icon: 0, }, function (index) {
                        layer.close(index);
                    });
                } else {
                    if (allData.questionState == '2' || allData.questionState == '4') {//首次回答完成
                        if (allData.haveAskFlag == '0') {//有追问
                            if (allData.askState == '0' || allData.askState == '1') {//追问未回答或待回答===提醒
                                l.ajax("sendRemind", param, function (data) {//首次问题的提醒	   
                                    pager.page('remote')
                                    parent['pager'].page('remote')
                                    layer_close()
                                })
                            } else {//追问回答了----不用提醒
                                layer.alert("问题已经回答，不需要提醒", { icon: 0, }, function (index) {
                                    layer.close(index);
                                });
                            }
                        } else {//没有追问----不用提醒
                            layer.alert("问题已经回答，不需要提醒", { icon: 0, }, function (index) {
                                layer.close(index);
                            });
                        }
                    } else {
                        //首次回答未完成==提醒
                        l.ajax("sendRemind", param, function (data) {//首次问题的提醒	   
                            pager.page('remote')
                            parent['pager'].page('remote')
                            layer_close()
                        })
                    }
                }
            })
            break;
    }
})


//请求数据给第一个表单赋值
myRefresh();
function myRefresh() {
    l.ajax('getQuestionDetail',param,function (data) {
        //console.log('搜索参数是：',data)
        if (data.loginTypeFlag == '0' || data.loginTypeFlag == '1') {
            $("#re").hide();
        }
        if (data.loginTypeFlag == '0' || data.loginTypeFlag == '1') {
            $("#cl").hide();
        }

        if (data.loginTypeFlag == '2' && data.questionState != '2') {
            $("#cl").hide();
        }

        $('#detailLayer .btn').hide();
        if (data) {
            var $input = $('#detailLayer input, #detailLayer select, #detailLayer textarea,#detailLayer address');
            $input.each(function (index,ele) {
                var _name = $(ele).attr('name');
                $(ele).attr('disabled',true);
                //日期和图片列表特殊处理 因为   type值跟文本一样so
                if (_name === 'dateOfQuestion' && data[_name]) {
                    //日期 
                    var toLocaleTimeString = new Date(data[_name]).toLocaleString();
                    var _t = toLocaleTimeString.replace(/[上午|下午|晚上|早上]/gi,'').replace(/(\/)/gi,'-');
                    $(ele).val(_t);
                } else {
                    $(ele).val(data[_name])
                }
            })

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