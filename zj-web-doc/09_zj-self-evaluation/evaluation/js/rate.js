var evaluationType = l.getUrlParam("evaluationType") || 0;
var selfUserId = l.getUrlParam("selfUserId") || "";
var userId = l.getUrlParam("userId") || "";
var bmId = l.getUrlParam("bmId") || "";
var assessmentId = l.getUrlParam("assessmentId") || "";

var body = {
    evaluationType: evaluationType,
    selfUserId: selfUserId,
    userId: userId,
    bmId: bmId,
    assessmentId:assessmentId
}
l.ajax("getUserScoreRateRankList", body, function (data, message, success) {
    if (success) {
        render(data)
    }
})
function render(data, disabled) {
    var domString = '',//dom字符串
        tableString = '',
        theadString = '',
        tbodyString = '',
        tfootString = '';
    //thead
    theadString = '<thead class="text-c">'
    theadString = theadString + '<tr>'
    theadString = theadString + '<th colspan="5">得分统计排名</th>'
    theadString = theadString + '</tr>'
    theadString = theadString + '<tr>'
    theadString = theadString + '<th>工作模块</th>'
    theadString = theadString + '<th>自评得分</th>'
    theadString = theadString + '<th>自评得分率</th>'
    theadString = theadString + '<th>复评得分</th>'
    theadString = theadString + '<th>复评得分率</th>'
    theadString = theadString + '</tr>'
    theadString = theadString + '</thead>'

    //tbody
    tbodyString = '<tbody>'
    $.each(data.zjZpUserDetailList, function (index, item) {
        var tdString0 = '<td>' + item.moduleTypeName + '</td>';
        var tdString1 = '<td>' + item.selfScore + '</td>';
        var tdString2 = '<td>' + item.selfScoreRate + '</td>';
        var tdString3 = '<td>' + item.reviewScore + '</td>';
        var tdString4 = '<td>' + item.reviewScoreRate + '</td>';
        var trString = '<tr>'
            + tdString0
            + tdString1
            + tdString2
            + tdString3
            + tdString4
            + '</tr>';
        tbodyString = tbodyString + trString
    })
    tbodyString = tbodyString + '</tbody >'

    //tfoot
    tfootString = '<tfoot style="font-weight:bold">'
    tfootString = tfootString + '<tr>'
    tfootString = tfootString + '<td colspan="5">本次信息化工作考核最终得分为：' + data.totalScore + '； 考核单位中排名：' + data.rankNo + '</td>'
    tfootString = tfootString + '</tr>'
    tfootString = tfootString + '</tfoot>'

    //table
    tableString = '<table class="table table-border table-bg  table-bordered radius">'
        + theadString
        + tbodyString
        + tfootString
        + '</table >'
    var closeString = '<input style="margin-top:20px;margin-left:20px;" class="btn btn-primary size-L radius" id="close" type="button" value="关闭">'
    var contralString = '<div style="text-align:right">' + closeString + '</div>'
    domString = domString
        + tableString
        + contralString
    var $dom = $(domString)
    $("#root").html($dom)
}

$("body").on("click", "#close", function () {
    var index = parent.layer.getFrameIndex(window.name);
    parent.layer.close(index);
})