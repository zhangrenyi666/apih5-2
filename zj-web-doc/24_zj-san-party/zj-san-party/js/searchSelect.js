var keyword = l.getUrlParam("keyword")
var apiName = l.getUrlParam("apiName")
var keywordName = l.getUrlParam("keywordName")
var otherKey = l.getUrlParam("otherKey")
var otherValue = l.getUrlParam("otherValue")


var totalPage = 1
var page = 1
var limit = 5
function load(page) {
    var body = {
        page: page,
        limit: limit
    }
    body[otherKey] = otherValue
    body[keywordName] = keyword
    l.ajax(apiName, body, function (data, message, success, res) {
        $("#list").html("")
        totalPage = parseInt(res.totalNumber / limit) + (res.totalNumber % limit != 0 ? 1 : 0)
        $("#msg").html('总' + res.totalNumber + '条，'+ page + '/' + totalPage + '页')

        if (data.length) {
            for (var index = 0; index < data.length; index++) {
                var item = data[index];
                var input = $('<input type="hidden"/>')
                input.val(JSON.stringify(item))
                var $item = $('<li title="' + item[keywordName] + '">' + item[keywordName] + '</li>')
                $item.append(input)
                $item.on("click", function () {
                    parent.setSearchSelect(JSON.parse($(this).find('input').val()))
                })
                $("#list").append($item)
            }
        } else {
            $("#list").append('<span style="color:#999;font-size:14px">未找到任何匹配的数据。</span>')
        }

    })
}
load(page)

$("#pageUp").click(function () {
    if (page - 1 < 1) {
        return
    }
    page = page - 1
    load(page)
})
$("#pageDown").click(function () {
    if (page + 1 > totalPage) {
        return
    }
    page = page + 1
    load(page)
})