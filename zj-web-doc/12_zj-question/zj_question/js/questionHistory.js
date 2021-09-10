var code = Apih5.getUrlParam('code')
Apih5.setCookie('code', code, 30)
var allData;
var pager = $("#pager").page({
    remote: {// ajax请求配置
        url: l.getApiUrl("getQuestionHistoryByQuestionApprovalId"),
        params: {
            questionApprovalId: l.getUrlParam("id")
        },
        success: function (result) {
            if (result.success) {
                var data = result.data;
                allData = data;
                for (var i = 0; i < allData.length; i++) {
                    // console.log(allData[i]);
                    var liClass = '';
                    if (i % 2 == 0) {
                        liClass = 'timerShaft-li a-right'
                    } else {
                        liClass = 'timerShaft-li a-left'
                    }
                    var $li = $('<li class="' + liClass + '"></li>');
                    var dot = $('<div class="dot"></div>');//小圆点
                    var arr = $('<div class="arr"></div>');//小箭头
                    var $timerShaftCon = $('<div class="timerShaft-li-con cl"></div>');
                    var $con = $('<div class="con">' +
                        '<div >问题检查项:<span>' + allData[i].questionClassName + '</span></div>' +
                        '<div>步骤:<span>' + allData[i].operationSteps +'</span></div>' +
                        '<div>内容:<span> ' + allData[i].operationContent + '</span></div>' +
                        '<div>操作员:<span>' + (allData[i].createUserName || '-') + '</span></div>' +
                        '<div>操作部门:<span>' + (allData[i].operatorDepartments || '-') + '</span></div>' +
                        '<div>操作时间:<span>' + (l.dateFormat(new Date(allData[i].createTime), "yyyy-MM-dd HH:mm:ss") || '-') + '</span></div>' + '</div>'); 
                        $timerShaftCon.append($con);

                    $con.append(arr);
                    $con.append(dot);
                    $li.append($timerShaftCon);

                    $('.timerShaft-ul').append($li)
                }
                $.each(data, function (index, item) {
                    item.rowIndex = index
                })
                // table.clear().rows.add(data).draw();
            } else {
                layer.alert(result.message, { icon: 0, }, function (index) {
                    layer.close(index);
                });
            }
        },
    }
});