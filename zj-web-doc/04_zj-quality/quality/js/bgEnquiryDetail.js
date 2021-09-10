var enquiryId = l.getUrlParam("id") || ""
var body = {
    enquiryId: enquiryId
}

l.ajax('getEnquiryDetailByPc', body, function (data) {
    $("#enquiryDetail").html(renderString(data))
})

function renderString(data) {
    var jsx = ""
    jsx += '<div class="ed_title">'
    jsx += data.levelDiff === "1" ? '<span style="color:#ff4000">' + levelDiffToText(data.levelDiff) + ' · </span>' : ''
    jsx += ' <span style="color:#0099dd">' + enquiryDiffToText(data.enquiryDiff) + ' · </span>' + data.enquiryTitle + '</div >'
    jsx += '<ul class="ed_list">'
    jsx += '<li class="ed_listItem clearfix">'
    jsx += '<div class="ed_listItem_l">'
    jsx += '<div class="ed_imgBox">' + data.enquiryUserName.substr(0, 1) + '</div>'
    jsx += '<div class="ed_floorCount">' + '1楼' + '</div>'
    jsx += '</div>'
    jsx += '<div class="ed_listItem_r">'
    jsx += '<div>' + data.enquiryUserName + '</div>'
    jsx += '<div class="ed_time">' + fromNow(data.createTime) + '</div>'
    jsx += '<div class="ed_content">' + data.enquiryContent + '</div>'
    jsx += renderImglist(data.imageInfoList)
    jsx += renderVoiceList(data.voiceInfoList)

    jsx += '<div>指定回答人：' + data.answerUserName + '</div>'
    jsx += '</div>'
    jsx += '</li>'
    jsx += '<li class="ed_answerListTitle">全部回答</li>'
    for (var i = 0; i < data.answerList.length; i++) {
        var answerItem = data.answerList[i];
        jsx += '<li class="ed_listItem clearfix">'
        jsx += '<div class="ed_listItem_l">'
        jsx += '<div class="ed_imgBox">' + answerItem.flowUserName.substr(0, 1) + '</div>'
        jsx += '<div class="ed_floorCount">' + (i + 2) + '楼' + '</div>'
        jsx += '</div>'
        jsx += '<div class="ed_listItem_r">'
        jsx += '<div>' + answerItem.flowUserName + '</div>'
        jsx += '<div class="ed_time">' + fromNow(answerItem.createTime) + '</div>'
        jsx += '<div class="ed_content">' + answerItem.answerContent + '</div>'
        jsx += renderImglist(answerItem.imageInfoList)
        jsx += renderVoiceList(answerItem.voiceInfoList)
        jsx += '</div>'
        jsx += '</li>'
    }

    if (data.endUserId) {
        jsx += '<li class="ed_listItem clearfix">'
        jsx += '<div class="ed_listItem_l">'
        jsx += '<div class="ed_imgBox">' + data.endUserName.substr(0, 1) + '</div>'
        jsx += '<div class="ed_floorCount">末楼</div>'
        jsx += '</div>'
        jsx += '<div class="ed_listItem_r">'
        jsx += '<div>' + data.endUserName + '</div>'
        jsx += '<div class="ed_time">' + fromNow(data.endDate) + '</div>'
        jsx += '<div class="ed_content">结束了该问询</div>'

        jsx += '</div>'
        jsx += '</li>'
    }
    jsx += '</ul>'
    return jsx
}

function renderVoiceList(voiceInfoList) {
    var voiceListJsx = ""
    if (voiceInfoList && voiceInfoList.length > 0) {
        voiceListJsx += '<div class="ed_voiceList">'
        for (var j = 0; j < voiceInfoList.length; j++) {
            var voiceItem = voiceInfoList[j];
            voiceListJsx += '<div>'
            voiceListJsx += '<audio width="300" src="' + voiceItem.infoAddress + '" controls preload>'
            voiceListJsx += '<embed align="bottom"  autostart="false" height="70" width="300" src="' + voiceItem.infoAddress + '" />'
            voiceListJsx += '</audio>'
            voiceListJsx += '<div/>'
        }
        voiceListJsx += '</div>'
    }
    return voiceListJsx
}
function renderImglist(imageInfoList) {
    var imgListjsx = ""
    if (imageInfoList && imageInfoList.length > 0) {
        imgListjsx += '<div class="ed_imgList clearfix">'
        for (var j = 0; j < imageInfoList.length; j++) {
            var imageItem = imageInfoList[j];
            imgListjsx += '<a class="ed_imgItem" target="_blank" href="' + imageItem.infoAddress + '">'
            imgListjsx += '<img src="' + imageItem.infoAddress + '"/>'
            imgListjsx += '</a>'
        }
        imgListjsx += '</div>'
    }
    return imgListjsx
}
function enquiryDiffToText(enquiryDiff) {
    var text = "";
    switch (enquiryDiff) {
        case "0":
            text = "技术交底"
            break;
        case "1":
            text = "关键工序管控"
            break;
        case "2":
            text = "非关键工序管控"
            break;
        case "3":
            text = "疑难问题"
            break;
        case "4":
            text = "互动"
            break;
        default:
            text = '未知类型'
            break;
    }
    return text
}
function levelDiffToText(levelDiff) {
    var text = "";
    switch (levelDiff) {
        case "1":
            text = "紧急"
            break;
        default:
            text = ""
            break;
    }
    return text

}


function fromNow(preDate) {
    var curDate = new Date().getTime();
    var difDate = Math.max(curDate - preDate, 0);
    var textDate = ""
    var count = 0;
    if (difDate < 60 * 1000) {//小于一分钟
        textDate = "刚刚"
    } else if (difDate < 60 * 60 * 1000) {//小于一小时
        count = parseInt(difDate / (60 * 1000), 10)
        textDate = count + "分钟前"
    } else if (difDate < 24 * 60 * 60 * 1000) {//小于一天
        count = parseInt(difDate / (60 * 60 * 1000), 10)
        textDate = count + "小时前"
    } else if (difDate < 7 * 24 * 60 * 60 * 1000) {//小于一周
        count = parseInt(difDate / (24 * 60 * 60 * 1000), 10)
        textDate = count + "天前"
    } else {
        if (new Date(preDate).getFullYear() !== new Date(curDate).getFullYear) {
            textDate = l.dateFormat(new Date(preDate), "yyyy-MM-dd")
        } else {
            textDate = l.dateFormat(new Date(preDate), "MM-dd")
        }
    }
    return textDate
}