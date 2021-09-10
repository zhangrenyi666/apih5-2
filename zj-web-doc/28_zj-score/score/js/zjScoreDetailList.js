var code = l.getUrlParam("code") || "";
Lny.setCookie("code", code, 30);
var id = '';
l.ajax("getZjScoreAnnualAssessmentList", {}, function (
    data,
    message,
    success
) {
    if (success) {
        var selectDom = '';
        if(data &&  data.length){
            $.each(data, function (index, item) {
                selectDom += "<li class='selectDomLi' data-id="+ item.assessmentId +" style='cursor:pointer; margin-bottom:10px;border-bottom:1px solid #ccc;padding-bottom:10px;text-align: center;'>"+ item.title +"</li>";
            });
            var idex = layer.open({
                title: "请选择打分活动",
                type: 1,
                skin: "layui-layer-demo", //样式类名
                closeBtn: 0, //不显示关闭按钮
                anim: 2,
                content:
                    "<ul class='selectDom' style='padding:26px'>" + selectDom + "</ul>"
            });
            $(".selectDomLi").click(function() {
                id = $(this).attr("data-id");
                render(id);
                layer.close(idex);
            });
        }else{
            var idex = layer.open({
                title: "请选择打分活动",
                type: 1,
                skin: "layui-layer-demo", //样式类名
                closeBtn: 0, //不显示关闭按钮
                anim: 2,
                content:
                    "<ul class='selectDom' style='padding:26px'>暂无打分内容...</ul>"
            });
        }
    }
});
function render(id){
    l.ajax("getZjScoreScoringItemListHeader", { assessmentId: id }, function (
        data,
        message,
        success
    ) {
        if (success) {
            var total = 0;
            for (var a = 0; a < 9; a++) {
                if (data[a] && data[a].length) {
                    total += data[a].length;
                }
            }
            $('#table').css({ 'width': (total * 100 + 525) + 'px' });
            var theadString = '<thead>';
            theadString += "<tr>";
            theadString += '<th class="thLeft" rowspan="3" width="25px">No.</th>';
            theadString += '<th class="thLeft" rowspan="3" width="100px">分组</th>';
            theadString += '<th class="thLeft" rowspan="3" width="200px">单位全称</th>';
            theadString += '<th class="thLeft" rowspan="3" width="100px">单位简称</th>';
            if (data["0"] && data["0"].length) {
                theadString += '<th colspan="' + data["0"].length + '" width="' + (data["0"].length * 100) + "px" + '">办公室</th>';
            }
            if (data["1"] && data["1"].length) {
                theadString += '<th colspan="' + data["1"].length + '" width="' + (data["1"].length * 100) + "px" + '">合同管理与结算中心</th>';
            }
            if (data["2"] && data["2"].length) {
                theadString += '<th colspan="' + data["2"].length + '" width="' + (data["2"].length * 100) + "px" + '">资源管理与采购中心</th>';
            }
            if (data["3"] && data["3"].length) {
                theadString += '<th colspan="' + data["3"].length + '" width="' + (data["3"].length * 100) + "px" + '">工程管理部</th>';
            }
            if (data["4"] && data["4"].length) {
                theadString += '<th colspan="' + data["4"].length + '" width="' + (data["4"].length * 100) + "px" + '">科学技术质量部</th>';
            }
            if (data["5"] && data["5"].length) {
                theadString += '<th colspan="' + data["5"].length + '" width="' + (data["5"].length * 100) + "px" + '">安全监督部</th>';
            }
            if (data["6"] && data["6"].length) {
                theadString += '<th colspan="' + data["6"].length + '" width="' + (data["6"].length * 100) + "px" + '">运营管理部</th>';
            }
            if (data["7"] && data["7"].length) {
                theadString += '<th colspan="' + data["7"].length + '" width="' + (data["7"].length * 100) + "px" + '">法律部</th>';
            }
            if (data["8"] && data["8"].length) {
                theadString += '<th colspan="' + data["8"].length + '" width="' + (data["8"].length * 100) + "px" + '">投资事业部</th>';
            }
            theadString += '<th rowspan="3" width="100px">合计</th>';
            theadString += "</tr>";
            theadString += "<tr>";
            for (var n = 0; n < 9; n++) {
                if (data[n] && data[n].length) {
                    var data1 = [];
                    var data2 = [];
                    var data3 = [];
                    for (var i = 0; i < data[n].length; i++) {
                        if (data[n][i].moduleType === '0') {
                            data1.push(data[n][i]);
                        } else if (data[n][i].moduleType === '1') {
                            data2.push(data[n][i]);
                        } else if (data[n][i].moduleType === '2') {
                            data3.push(data[n][i]);
                        }
                    }
                    if (data1 && data1.length) {
                        theadString += '<th colspan="' + data1.length + '" width="' + (data1.length * 100) + "px" + '">一公局自建</th>';
                    }
                    if (data2 && data2.length) {
                        theadString += '<th colspan="' + data2.length + '" width="' + (data2.length * 100) + "px" + '">隧道局自建</th>';
                    }
                    if (data3 && data3.length) {
                        theadString += '<th colspan="' + data3.length + '" width="' + (data3.length * 100) + "px" + '">集团统建</th>';
                    }
                }
            }
            theadString += "</tr>";
            theadString += "<tr>";
            for (var n = 0; n < 9; n++) {
                if (data[n] && data[n].length) {
                    var data1 = [];
                    var data2 = [];
                    var data3 = [];
                    for (var i = 0; i < data[n].length; i++) {
                        if (data[n][i].moduleType === '0') {
                            data1.push(data[n][i]);
                        } else if (data[n][i].moduleType === '1') {
                            data2.push(data[n][i]);
                        } else if (data[n][i].moduleType === '2') {
                            data3.push(data[n][i]);
                        }
                    }
                    if (data1 && data1.length) {
                        for (var m = 0; m < data1.length; m++) {
                            theadString += '<th width="100px">' + data1[m].content + '</th>';
                        }
                    }
                    if (data2 && data2.length) {
                        for (var m = 0; m < data2.length; m++) {
                            theadString += '<th width="100px">' + data2[m].content + '</th>';
                        }
                    }
                    if (data3 && data3.length) {
                        for (var m = 0; m < data3.length; m++) {
                            theadString += '<th width="100px">' + data3[m].content + '</th>';
                        }
                    }
                }
            }
            theadString += "</tr>";
            theadString += '</thead>';
            $('#table').append(theadString);
            pager = pagers(id,JSON.stringify(data));
        } else {
            layer.alert(
                message,
                {
                    icon: 0
                },
                function (index) {
                    layer.close(index);
                }
            );
        }
    });
}
var pager; 
function pagers(id,dataTop){
    return pager = $("#pager").page({
        remote: {
            //ajax请求配置
            url: l.getApiUrl("pcGetZjScoreCompanyDetailList"),
            params: { assessmentId: id },
            success: function (result) {
                if (result.success) {
                    var data = result.data;
                    $.each(data, function (index, item) {
                        item.rowIndex = index;
                    });
                    var totalList = [];
                    dataTop = JSON.parse(dataTop);
                    for(var i = 0; i < 9; i++){
                        if(dataTop[i] && dataTop[i].length){
                            for(var j = 0; j < dataTop[i].length; j++){
                                totalList.push(dataTop[i][j]);
                            }
                        }
                    }
                    var tbodyString = "<tbody>";
                    if(data && data.length){
                        for(var i = 0; i < data.length; i++){
                            if(data[i].companyType === '0'){
                                data[i].companyType = '各单位';
                            }else if(data[i].companyType === '1'){
                                data[i].companyType = '总承包部、直属项目部等';
                            }else{
                                data[i].companyType = '项目公司';
                            }
                            tbodyString += "<tr>";
                            tbodyString += "<td class='tdLeft'>"+ (i+1) +"</td>";
                            tbodyString += "<td class='tdLeft'>"+ data[i].companyType +"</td>";
                            tbodyString += "<td class='tdLeft'>"+ data[i].companyFullName +"</td>";
                            tbodyString += "<td class='tdLeft'>"+ data[i].companyShortName +"</td>";
                            if(totalList && totalList.length){
                                for(var j = 0; j < totalList.length; j++){
                                    var itemId = totalList[j].itemId;
                                    if(data[i][itemId] === -1){
                                        tbodyString += "<td style='background-color:#ccc;'>-</td>";
                                    }else if(data[i][itemId] === 0 || data[i][itemId]){
                                        tbodyString += "<td>"+ data[i][itemId] +"</td>";
                                    }     
                                }
                            }
                            tbodyString += "<td>"+ data[i].totalScore +"</td>";
                            tbodyString += "</tr>";
                        }
                    } 
                    tbodyString += "</tbody>";
                    $('#table').append(tbodyString);
                    $("#tableDiv").scroll(function () {
                        var left = $("#tableDiv").scrollLeft();
                        var trs = $(".thLeft");
                        trs.each(function (i) {
                            if(i === 3){
                                $(this).css({ "position": "relative", "top": "0px", "left": left,"border-right":"1px solid #ddd" });
                            }else{
                                $(this).css({ "position": "relative", "top": "0px", "left": left });
                            }
                        });
                        var tds = $(".tdLeft");
                        tds.each(function (i) {
                            if((i+1) % 4 === 0){
                                $(this).css({ "position": "relative", "top": "0px", "left": left,"border-right":"1px solid #ddd","background-color":"white" });
                            }else{
                                $(this).css({ "position": "relative", "top": "0px", "left": left,"background-color":"white" });
                            }
                        });
                    });
                } else {
                    layer.alert(
                        result.message,
                        {
                            icon: 0
                        },
                        function (index) {
                            layer.close(index);
                        }
                    );
                }
            }
        }
    });
}
$("body").on("click", "button", function() {
    var name = $(this).attr("data-name");
    switch (name) {
        case "export":
            //导出
            l.ajax(
                "exportZjScoreCompanyDetail",
                {assessmentId: id},
                function(data, message, success) {
                    if (success) {
                        layer.alert(message);
                        window.location.href = data;
                    } else {
                        layer.alert(result.message, { icon: 0 }, function(
                            index
                        ) {
                            layer.close(index);
                        });
                    }
                }
            );
            break;
    }
});

