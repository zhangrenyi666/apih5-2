var code = Apih5.getUrlParam('code')
Apih5.setCookie('code', code, 30)
var voteId = l.getUrlParam('id');//投票ID
var params = {
    voteId: voteId,
};
l.ajax('getVoteCandidateListByPc', params, function (data, message, success) {//获取投票信息
    if (success && data) {
        var voteTitle = data.voteTitle || "";//标题
        var rule = data.rule || "";//副标题
        var voteCandidateList = data.voteCandidateList || [];//候选人list
        $("#voteTitle").html("<span>" + data.voteTitle + "</span>");//标题
        $("#xiaobiao").html("<span>" + data.rule + "<i style='color:red'>* </i></span>");//小标题
        if (voteCandidateList.length > 0) {
            $.each(voteCandidateList, function (i, item) {
                var $str = "";
                $str += "<div style='margin-bottom: 20px'>";
                $str += item.imageList && item.imageList[0] && item.imageList[0].fileUrl ? "<div class='xiaq'><img class='xiaq-img' src='" + l.domain + item.imageList[0].fileUrl + "'></div>" : "";
                $str += "<div class='xiaka'>";
                $str += item.titleOne ? "<p><span style='color: #39A4DB'>" + item.titleOne + ":</span>" + item.contentOne + "</p>" : "";
                $str += item.titleTwo ? "<p><span>" + item.titleTwo + ":</span>" + item.contentTwo + "</p>" : "";
                $str += item.titleThree ? "<p><span>" + item.titleThree + ":</span>" + item.contentThree + "</p>" : "";
                $str += item.titleFour ? "<p><span>" + item.titleFour + ":</span>" + item.contentFour + "</p>" : "";
                $str += item.titleFive ? "<p><span>" + item.titleFive + ":</span>" + item.contentFive + "</p>" : "";
                if (item.isVoteFlag == "1") {
                    $str += "<button  data-candidateId='" + item.candidateId + "' onclick='touSave(this)'class='btn-primary' style='background-color:#bfbfbf;border-color:#bfbfbf;' disabled='disabled'>已投票</button>";
                }
                else {
                    $str += "<button  data-candidateId='" + item.candidateId + "' onclick='touSave(this)'class='btn-primary' >投票</button>";
                }
                $str += "<span style='display:inline-block;width:100%;text-align:center;'>" + item.voteTotalNum + "</span>";
                $str += "</div></div>";
                if (i % 3 == 0) {
                    $("#dleft").append($str);
                } else {
                    $("#dright").append($str);
                }
            });
            $('.xiaq-img').css({
                display: 'block',
                margin: '0 auto'
            })
            $("#dleft>div, #dright>div").css({
                textAlign: "center",
                border: '1px solid rgba(0, 0, 0, 0.2)',
                boxShadow: '0 0 20px rgba(0, 0, 0, 0.2) inset',
                padding: '20px 10px',
                boxSizing: 'border-box'
            })
        }
    }

});

function touSave(dom) {
    var params = {
        voteId: voteId,
        candidateId: $(dom).attr("data-candidateId")
    };
    l.ajax('saveVoteResult', params, function (data, message, success) {
        if (success) {
            layer.alert(message, { icon: 0, }, function (index) {
                layer.close(index);
                $(dom).next().text(Number($(dom).next().text()) + 1);
                $(dom).css({
                    "background-color": "#bfbfbf",
                    "border-color": "#bfbfbf",
                }).attr("disabled", "disabled");
            })
        }
    })

}