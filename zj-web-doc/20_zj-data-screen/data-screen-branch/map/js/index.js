$(function () {
    var dw = $(document).width();
    init();
    refresh();

    var mapI;
    var rankingI;
    var dataIndexI;
    var highwayI;
    var pathwayI;
    var railwayI;
    var municipalI;
    var housingI;
    var tunnelI;
    function init() {
        mapI = initMap();
        //渲染板块
        // rankingI = window.rankingI = ranking();
        dataIndexI = dataIndex();
        highwayI = window.highwayI = highway();
        pathwayI = window.pathwayI = pathway();
        railwayI = window.railwayI = railway();
        municipalI = window.municipalI = municipal();
        housingI = window.housingI = housing();
        tunnelI = window.tunnelI = tunnel();
        //获取数据
        setData();

        //第一块的数据 provinceName: global.province
        getData.getOneBlankData('getBranchProjectListByCityName',{},function (data) {
            indexFn.renderOneBlank(data);
        })


        //自适应
        listerAutoSize();

        //设置默认渲染 
        getData.getBannerData('account',function (data) {//轮播
            indexFn.renderBannerCon(data,'account');
        });
        getData.getConfigData(function (data) {//资源配置
            indexFn.renderConfig(data);
        });
        getData.getWisdomData(function (data) {//资源配置
            indexFn.renderNavCon(data);
        });

        //浏览器判断
        var isIE = window.w.isIE();
        if (isIE === '-1') {//非ie
            var sLink = $('<link rel="stylesheet" id="style" href="map/style/noIe.css">');
            $('head')[0].append(sLink[0])
        }



        //获取首页标题
        getData.getTitle('getBranchCompanyNameByToken',{},function (data) {//资源配置 
            $('.top .logo .text').text(data);
        });
    }

    //设置数据
    function setData() {
        getData.getMapData(mapI);
        // getData.getRankingData(rankingI);
        getData.getDataIndexData(dataIndexI);
        // indexFn.renderBottomArc();
        getData.getQuanData(highwayI,'highway','公路','#35ffff');
        getData.getQuanData(pathwayI,'pathway','轨道','#6dfe00');
        getData.getQuanData(railwayI,'railway','铁路','#ff2f36');
        getData.getQuanData(municipalI,'municipal','市政','#6dfe00');
        getData.getQuanData(housingI,'housing','房建','#35ffff');
        getData.getQuanData(tunnelI,'tunnel','6','#35ffff');
        $('.updateTime span').text(new Date().toLocaleDateString() + new Date().toLocaleTimeString())
    }

    //渲染地图
    function initMap() {
        var option = {
            tooltip: {
                trigger: 'item',
                formatter: function (params,ticket,callback) {
                    return params.value[3] + '<br/>' + (params.value[2] || 0) + '（万元）';
                }
            },
            visualMap: [{
                show: false,
                left: '40%',
                top: '10%',
                textStyle: {
                    color: 'white'
                }
            }],
            geo: {
                z: '0',
                top: '0',
                map: 'china',
                label: {
                    show: true,
                    textStyle: {
                        color: 'white'
                    }
                },
                roam: true,
                itemStyle: {
                    normal: {
                        areaColor: '#055a9a',
                        borderColor: '#00a1cf'
                    },
                    emphasis: {
                        areaColor: '#00a1cf',
                        color: 'white',
                        fontWeight: '800',
                        fontSize: '18',
                        show: true
                    }
                }
            },
            series: [
                {
                    z: 0,
                    type: 'map',
                    mapType: 'China',
                    roam: 'scale',
                    zoom: '0.8',
                    showEffectOn: 'render',
                    rippleEffect: {
                        brushType: 'stroke'
                    },
                    label: {
                        show: true,
                        color: 'white',
                        fontSize: '13',
                        emphasis: {
                            color: 'white',
                            fontWeight: '800',
                            fontSize: '18',
                            show: true
                        }
                    },
                    data: []
                }
            ]
        };

        var mapDom = document.getElementById('map');
        // var myChart = echarts.init(mapDom, 'shine', {renderer: 'svg'});
        var myChart = echarts.init(mapDom,'shine');
        myChart.setOption(option);
        myChart.on('click',function (params) {
            // if (params.componentType === 'geo') {
            //     var search = window.location.search;
            //     if (!search) {//没有search的话加上? 在拼上市名称
            //         search = '?'
            //     } else {
            //         search = search + "&"
            //     }
            //     search += 'province=' + params.name;
            //     window.parent.changeIframeSrc('map/province/index.html' + search);
            // }
        });

        return myChart;
    }

    //公司排名
    // function ranking() {
    //     var option1 = {
    //         title: {
    //             text: '公司排名',
    //             padding: [20,20],
    //             textStyle: {
    //                 color: 'white',
    //                 fontSize: dw / 100 * 1.2
    //             }
    //         },
    //         tooltip: {
    //             trigger: 'axis',
    //             axisPointer: {
    //                 type: 'shadow'
    //             }
    //         },
    //         legend: {
    //             show: false
    //         },
    //         grid: {
    //             left: '1%',
    //             bottom: '2%',
    //             containLabel: true
    //         },
    //         xAxis: {
    //             type: 'value',
    //             name: '万元',
    //             boundaryGap: [0,0.01],
    //             lineStyle: {
    //                 color: 'white'
    //             },
    //             axisLabel: {
    //                 color: 'white'
    //             },
    //             axisLine: {
    //                 lineStyle: {
    //                     color: 'white'
    //                 }
    //             }
    //         },
    //         yAxis: {
    //             type: 'bar',
    //             type: 'category',
    //             // data: ['一公司', '二公司', '三公司', '四公司', '五公司', '六公司', '七公司', '北京建筑分公司'],
    //             data: [],
    //             lineStyle: {
    //                 color: 'transparent'
    //             },
    //             axisLabel: {
    //                 color: 'white'
    //             },
    //             axisLine: {
    //                 show: false,
    //                 lineStyle: {
    //                     color: 'white'
    //                 }
    //             }
    //         },
    //         series: []
    //     };

    //     var rankingdom = document.getElementById('ranking');
    //     var ranking = echarts.init(rankingdom,'shine');
    //     ranking.setOption(option1);
    //     ranking.on('click',function (params) {
    //         var link = params.data.link;
    //         gloFn.echartsClick({ link: link,flag: '1' })
    //     });
    //     return ranking;
    // }

    //经营数据指标
    function dataIndex() {
        var option1 = {
            color: ['#006699','#4cabce','#e5323e'],
            title: {
                text: '经营数据指标',
                padding: [25,20],
                textStyle: {
                    color: 'white',
                    fontSize: dw / 100 * 1.1
                }
            },
            tooltip: {
                trigger: 'axis',
                axisPointer: {
                    type: 'shadow'
                }
            },
            legend: {
                textStyle: {
                    color: 'white'
                }
            },
            grid: {
                left: '1%',
                bottom: '2%',
                containLabel: true
            },
            xAxis: {
                type: 'category',
                axisTick: { show: false },
                axisLabel: {
                    color: 'white'
                },
                axisLine: {
                    show: false,
                    lineStyle: {
                        color: 'white'
                    }
                }
                // data: ['2012', '2013']
            },
            yAxis: {
                type: 'value',
                nameGap: 8,
                axisLabel: {
                    color: 'white'
                },
                axisLine: {
                    show: true,
                    lineStyle: {
                        color: 'white'
                    }
                }
            },
            series: []
        };

        var dataIndexdom = document.getElementById('dataIndex');
        var edom = echarts.init(dataIndexdom,'shine');
        edom.setOption(option1);
        return edom;
    }

    //公路
    function highway() {
        var option = getQuanOptionData({});
        var dataIndexdom = document.getElementById('highway');
        var edom = echarts.init(dataIndexdom,'shine');
        edom.setOption(option);
        edom.on('click',function (params) {
            var link = params.data.link;
            gloFn.echartsClick({ link: link,flag: '2' })
        });
        return edom;
    }

    //轨道
    function pathway() {
        var option = getQuanOptionData({});
        var dataIndexdom = document.getElementById('pathway');
        var edom = echarts.init(dataIndexdom,'shine');
        edom.setOption(option);

        edom.on('click',function (params) {
            var link = params.data.link;
            gloFn.echartsClick({ link: link,flag: '2' })
        });
        return edom;
    }

    //铁路
    function railway() {
        var option = getQuanOptionData({});

        var dom = document.getElementById('railway');
        var edom = echarts.init(dom,'shine');
        edom.setOption(option);
        edom.on('click',function (params) {
            var link = params.data.link;
            gloFn.echartsClick({ link: link,flag: '2' })
        });
        return edom;
    }

    //市政
    function municipal() {

        var option = getQuanOptionData({});
        var dom = document.getElementById('municipal');
        var edom = echarts.init(dom,'shine');
        edom.setOption(option);
        edom.on('click',function (params) {
            var link = params.data.link;
            gloFn.echartsClick({ link: link,flag: '2' })
        });
        return edom;
    }

    //房建
    function housing() {
        var option = getQuanOptionData({});
        var dom = document.getElementById('housing');
        var edom = echarts.init(dom,'shine');
        edom.setOption(option);
        edom.on('click',function (params) {
            var link = params.data.link;
            gloFn.echartsClick({ link: link,flag: '2' })
        });
        return edom;
    }

    //最后一个圈
    function tunnel() {
        var option = getQuanOptionData({});
        var dom = document.getElementById('tunnel');
        var edom = echarts.init(dom,'shine');
        edom.setOption(option);
        edom.on('click',function (params) {
            var link = params.data.link;
            gloFn.echartsClick({ link: link,flag: '2' })
        });
        return edom;
    }

    //自适应
    function autoSize() {
        //重置样式
        var dw = $(document).width();
        var dh = $(document).height();
        $('#container').css({
            height: $(window).height() + "px"
        })
        $('#container .left').css({
            width: '25%'
        })
        $('#map').css({
            width: dw,
            height: dh
        })
        $('#map').css({
            width: dw,
            height: dh
        })
        //字体大小自适应

        //排名
        $('.ranking_con .item').css({
            "font-size": dw / 100 * 0.9 + 'px'
        })

        //轮播
        $('#Broadcast .nav, #Broadcast .row').css({
            "font-size": dw / 100 * 0.9 + 'px'
        })

        //项目列表标题
        $('#ranking .title').css({
            "font-size": dw / 100 * 1.1 + 'px'
        })
        //资源配置
        $('#config .title').css({
            "font-size": dw / 100 * 1.1 + 'px'
        })
        $('#config ul li').css({
            "font-size": dw / 100 * 0.85 + 'px'
        })
        // $('#nav .title').css({
        //     "font-size": dw / 100 * 1.1 + 'px'
        // })
        $('#config .subtit').css({
            "font-size": dw / 100 * 0.9 + 'px'
        })

        //智慧工地
        // $('#nav .title').css({
        //     "font-size": dw / 100 * 1 + 'px'
        // })
        // $('#nav .con .item').css({
        //     "font-size": dw / 100 * 0.85 + 'px'
        // })
        // $('#nav .con .item img').css({
        //     "width": dw / 100 * 1.65 + 'px'
        // })

        // $('.bottom .item').css({
        //     'height': dh / 100 * 12 + 'px'
        // })

        $('.bottom .item').css({
            'height': $('.bottom .wrapper').height()
        })

        //logo文字自适应
        $('.top .logo .text').css({
            "fontSize": (dw / 100 * 1.15) + "px",
            "height": ($('.top .logo img').height()) + "px",
            "lineHeight": ($('.top .logo img').height()) + "px"
        });

        //重置图表
        mapI.resize();
        // rankingI.resize();
        dataIndexI.resize();
        highwayI.resize();
        pathwayI.resize();
        railwayI.resize();
        municipalI.resize();
        housingI.resize();
        tunnelI.resize();
    }

    //监听自适应
    function listerAutoSize() {
        autoSize();
        $(window).resize(function (e) {
            autoSize();
        });


    }

    //自动刷新
    function refresh() { //5分钟刷一次
        setInterval(setData,1000 * 60 * 5)
    }

})


