window.login(function () {

    //设置cookie  
    function setCookie(cname, cvalue, exdays) {
        var d = new Date();
        d.setTime(d.getTime() + (exdays * 24 * 60 * 60 * 1000));
        var expires = "expires=" + d.toUTCString();
        document.cookie = cname + "=" + cvalue + "; " + expires;
    }
    //获取cookie  
    function getCookie(cname) {
        var name = cname + "=";
        var ca = document.cookie.split(';');
        for (var i = 0; i < ca.length; i++) {
            var c = ca[i];
            while (c.charAt(0) == ' ') c = c.substring(1);
            if (c.indexOf(name) != -1) return c.substring(name.length, c.length);
        }
        return "";
    }
    function getUrl(k) {//获取地址栏参数，k为键名
        var m = new RegExp("(^|&)" + k + "=([^&]*)(&|$)");
        var r = window.location.search.substr(1).match(m);
        if (r != null) return decodeURI(r[2]); return null;
    };


    new WOW().init();

    //数据请求遍历
    var params = {
        pageSize: 99999
    };
    getAssetsList(params);
    function getAssetsList(params) {
        l.ajax("getAssetsList", params, function (res) {
            $.each(res, function (i, v) {
                var zcztName = '';
                switch (v.zcztdm) {
                    case '1':
                        zcztName = '正常使用';
                        break;
                    case '2':
                        zcztName = '未验收';
                        break;
                    case '3':
                        zcztName = '不合格';
                        break;
                    case '4':
                        zcztName = '已丢失';
                        break;
                    case '5':
                        zcztName = '已报废';
                        break;
                }



                var $li = '';
                $li += '<a  class="DataList_a wow animated fadeInUp" href="./assetsDetail.html?id=' + v.recordid + ' ">';
                $li += '<li class="DataList_li">';//模板li开始
                $li += '<div class=""DataList_li_con>';
                $li += '<div class="DataList_li_con_top">';
                $li += '<span class="zcbh">';
                $li += v.zcbh;
                $li += '</span>';
                $li += '<span class="accest_state">';
                $li += zcztName;
                $li += '</span>';
                $li += '</div>';
                $li += '<div class="DataList_li_con_con">';
                $li += '<h4 class="zcmc">';
                $li += v.zcmc;
                $li += '</h4>';
                $li += '<p class="zc-class">';
                $li += '<span class="big_class">';
                $li += v.sszclx1Name || '-';
                $li += '</span>';
                $li += '/';
                $li += '<span class="smart_class">';
                $li += v.sszclx2Name || '-';
                $li += '</span>';
                $li += '</p>'
                $li += '<div class="cfdd">';
                $li += v.cfdd1;
                $li += '</div>';
                $li += '</div>';

                $li += '</div>';
                $li += '</li>';//模板li over
                $li += '</a>';
                setTimeout(function () {//加载时动画效果
                    $(".DataList_ul").append($li);
                    $(".DataList_li:odd").css({
                        "border-left": ".05rem solid rgb(255, 67, 0)",
                        "box-sizing": "border-box"
                    })
                    $(".DataList_li:even").css({
                        "border-left": ".05rem solid  green",
                        "box-sizing": "border-box"
                    })
                }, i * 200)
            })
        })
    }

    //点击搜索
    $('.search-btn').click(function () {
        var searchText = $('.search-input').val()
        var params = {
            pageSize: 99999,
            zcmc: searchText
        }
        $(".DataList_ul").html('');
        getAssetsList(params);
    })





})

