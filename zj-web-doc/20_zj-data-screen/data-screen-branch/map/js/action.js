//dom操作动作都放这

$(function () {
    var global = {
        bannerSpeed: 1,//速度
        bannerTime: 210//时间
    }

    var gloFn = { //全局方法 
        scrollTranslate: function (jqDom) {
            // var h = $('#Broadcast .con').height();//div可视区域的高度
            // var _st = $('#Broadcast .con')[0].scrollTop;
            // var st = $('#Broadcast .con')[0].scrollTop + h + 20 + global.bannerSpeed;
            // var sh = $('#Broadcast .con')[0].scrollHeight;
            // if (st >= sh) {
            //     $('#Broadcast .con').scrollTop(0);
            // } else {
            //     var _s = _st += 5;
            //     $('#Broadcast .con').scrollTop(_s);
            // }
            if (jqDom[0]) {
                var h = jqDom.height();//div可视区域的高度
                var _st = jqDom[0].scrollTop;
                var st = jqDom[0].scrollTop + h + 20 + global.bannerSpeed;
                var sh = jqDom[0].scrollHeight;
                if (st >= sh) {
                    jqDom.scrollTop(0);
                } else {
                    var _s = _st += 5;
                    jqDom.scrollTop(_s);
                }
            }
        },
        //轮播被点击
        bannerRowClick: function (link, flag) {
            this.echartsClick({ link: link, flag:flag })
            // alert('您当前没有查看权限，请联系管理员授权', { icon: 0 });
        },
        //资源配置被点击
        configItemClick: function (me) {
            this.echartsClick({ link: me.attr('link'), flag:me.attr('flag') })
            // alert('您当前没有查看权限，请联系管理员授权', { icon: 0 });
        },
        //轮播内容被hover时
        bannerRowHover: function (hoverType, me) {
            //轮播内容被hover时 
            if (hoverType === 0) {
                //hover进入
                layer.tips(me.text(), me, {
                    tips: [1, '#3595CC'],
                    time: 0
                });
            } else {
                //hover出去 
                layer.closeAll('tips')
            }
        },
        projectItemClick: function (me) {
            // console.log(me[0])
            this.projectClick(me)
            // alert('您当前没有查看权限，请联系管理员授权', { icon: 0 });
        },
        autoSize: function () {
            window.highwayI.resize();
            window.pathwayI.resize();
            window.railwayI.resize();
            window.municipalI.resize();
            window.housingI.resize();
            window.tunnelI.resize();
        },
        //智慧工地被点击
        navClick: function (isOpen) {
            if (isOpen) {
                window.w.get('initZjHaiKangVideo', { code: window.w.getUrlParam('code', window.parent) }, function (data) {
                    if (data) {
                        window.open(data)
                    } else {
                        alert('您当前没有查看权限，请联系管理员授权');
                    }
                })
            }
        },
        //鼠标移动到标注点上是出现的项目列表被点击执行
        projectClick: function ($me) { 
            var orgName = $me.attr('orgName');
            var cityName = $me.attr('cityName');
            window.parent.changeIframeSrc('map/project/index.html?orgName=' + orgName + '&cityName=' + cityName)
        },
        echartsClick: function (params) {
            //传入code
            params.code = window.w.getUrlParam('code', window.parent);
            window.w.get('initZjBigScreenSuperlinkList', params, function (data) {
                if (data) {
                    window.open(data)
                } else {
                    alert('您当前没有查看权限，请联系管理员授权');
                }
            })
        }

    }

    window.gloFn = gloFn;

    //顶部经营按钮被点击
    $('#manageBtn').click(function () {
        // alert('经营按钮');
        //some code... 
    })
    //顶部投资按钮被点击（海威系统用不到，注释）
    // $('#investmentBtn').click(function () {
    //     var _curUrl = window.location.href; //当前url跳转前需要先存储起来
    //     window.localStorage.setItem('goBeforeUrl', _curUrl);
    //     window.w.get('initZjZjInvestmentList', { code: window.w.getUrlParam('code', window.parent) }, function (data) {
    //         if (data) {
    //             window.parent.changeIframeSrc(data + '?tz=true')
    //         } else {
    //             alert('您当前没有查看权限，请联系管理员授权');
    //         }
    //     })
    // })

    // goBack 省地图的返回按钮
    $('.goBack').click(function () {
        window.parent.changeIframeSrc(window.config.provinceUrl[window.config.model])
    })

    //顶部施工按钮被hover
    $('#manageBtn').hover(function () {
        $(this).attr('src', "http://weixin.fheb.cn:99/woaMapCdn/施工_经过.png")
    }, function () {
        $(this).attr('src', "http://weixin.fheb.cn:99/woaMapCdn/施工.png")
    })
    //顶部投资按钮被hover
    $('#investmentBtn').hover(function () {
        $(this).attr('src', "http://weixin.fheb.cn:99/woaMapCdn/投资_经过.png")
    }, function () {
        $(this).attr('src', "http://weixin.fheb.cn:99/woaMapCdn/投资.png")
    })

    //轮播导航被点击
    $('#Broadcast .nav').find('.item').click(function () {
        $(this).siblings().removeClass('action');
        $(this).addClass('action');
        $('#Broadcast .con').html('<div style="text-align:center;margin-top:20px;">loading...</div>')
        //渲染里面的内容
        var name = $(this).attr('name');
        getData.getBannerData(name, function (data) {
            indexFn.renderBannerCon(data, name);
        });
    })

    //公司排名tabs
    $('.ranking_con .tabs').find('.item').click(function () {
        $(this).siblings().removeClass('action');
        $(this).addClass('action');
        //切换数据
        window.rankingI.showLoading('default', window.getData.loadingOpt);
        var name = $(this).attr('name');
        getData.getRankingData(window.rankingI, name);
    })

    //轮播被hover时暂停滚动
    $('#Broadcast .con').hover(function () {
        clearInterval(gloFn.t);
    }, function () {
        clearInterval(gloFn.t);
        gloFn.t = setInterval(function () {
            gloFn.scrollTranslate($('#Broadcast .con'))
        }, global.bannerTime);
    })

    //轮播被hover时暂停滚动
    $('#ranking').hover(function () {
        clearInterval(gloFn.t1);
    }, function () {
        clearInterval(gloFn.t1);
        gloFn.t1 = setInterval(function () {
            gloFn.scrollTranslate($('#ranking ul'))
        }, global.bannerTime);
    })

    //轮播自动滚动
    gloFn.t = setInterval(function () {
        gloFn.scrollTranslate($('#Broadcast .con'))
    }, global.bannerTime);

    //第一块内容自动滚动
    gloFn.t1 = setInterval(function () {
        gloFn.scrollTranslate($('#ranking ul'))
    }, global.bannerTime);

    //底部轮播 左右按钮
    var len = $('.bottom .group').length;
    var _index = 0;
    $('.bottom .bottomBannerBtn').click(function () {
        _index++;
        if (_index >= len) {
            _index = 0;
        }
        $('.bottom .group').hide(function () {
            $('.bottom .group').eq(_index).show(function () {
                gloFn.autoSize()
            });
        });

    })

    //底部轮播 隐藏按钮
    var show = true;
    $('.bottom .bottomHideBtn').click(function () {
        $(this).find('img').toggle();
        if (show) {
            $('.bottom').css({
                bottom: '-16%'
            });
        } else {
            $('.bottom').css({
                bottom: '-0%'
            });
        }
        show = !show;
    })

})  