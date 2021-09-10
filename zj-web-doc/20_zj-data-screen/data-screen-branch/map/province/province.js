$(function () {
    //页面底部小圈dom
    var quanIdArr = ['railway','municipal','highway','housing','pathway','tunnel'];
    var global = {
        markerData: '',//标注点数据
        province: decodeURI(window.w.getUrlParam('province')) || '北京' //经度 
    }

    var opts = {
        width: 300,     // 信息窗口宽度
        height: 250,     // 信息窗口高度
        title: "标题", // 信息窗口标题
        enableMessage: true//设置允许信息窗发送短息 
    }

    //百度地图
    var bMap = {
        addMarker: function (point) {
            var marker = new BMap.Marker(point);
            var _this = this;
            marker.setAnimation(BMAP_ANIMATION_DROP); //跳动的动画
            window.map.addOverlay(marker);
            //鼠标移动上标注时出现列表
            marker.addEventListener("mouseover",function (e) {
                var pt = e.point;
                var geoc = new BMap.Geocoder();
                geoc.getLocation(pt,function (rs) {
                    //将百度地图经纬度进行逆地址解析
                    var addComp = rs.addressComponents;
                    var city = addComp.city;
                    var markerData = global.markerData;
                    var exg = new RegExp('(' + city + ')','gi');
                    //获取到城市名后需要在匹配数据里的城市名
                    for (var i = 0; i < markerData.length; i++) {
                        if (exg.test(markerData[i].city)) {
                            city = markerData[i].city
                        }
                    }
                    window.getData.getMarkerProjectList('getBranchProjectListByCityName',{ cityName: city },function (data) {
                        _this.markerHover(data,e.point);
                    });
                });

            });
        },
        //标注点被hover
        markerHover: function (data,point) {
            //标注被hover打开项目列表
            opts.title = data.title;
            data = data.data;
            // console.log(data);
            //创建dom
            var list = window.indexFn.renderMarkerProjectList(data)

            var infoWindow = new BMap.InfoWindow(list,opts);  // 创建信息窗口对象 
            window.map.openInfoWindow(infoWindow,point); //开启信息窗口
        },
        //标注点被点击
        // projectClick: function (orgName) { 
        //     //iframe地址跳转 
        //     window.parent.changeIframeSrc('/map/project/index.html?orgName='+orgName)
        // },
        //画直线
        drawLine: function (data) {
            //画线
            var pointStr = "106.69452,26.651828,106.949783,26.70761,106.846298,26.955195,106.942884,27.130236,107.048668,27.237184,107.048668,27.237184,107.048668,27.237184,107.138355,27.549204,107.057867,27.6496,107.057867,27.6496".split(",");
            var pointArr = [];
            for (var k = 0; k < pointStr.length; k += 2) {
                pointArr.push({
                    lng: pointStr[k],
                    lat: pointStr[k + 1]
                });
            }

            // 生成坐标点
            var trackPoint = [];
            for (var i = 0,j = pointArr.length; i < j; i++) {
                trackPoint.push(new BMap.Point(pointArr[i].lng,pointArr[i].lat));
            }

            //移动并且缩放地图
            // window.map.centerAndZoom(trackPoint[0], 15);

            // 画线
            var polyline = new BMap.Polyline(trackPoint,{
                strokeColor: "rgb(0,247,222)",
                strokeWeight: 3,
                strokeOpacity: 1
            });
            window.map.addOverlay(polyline);

            // 配置图片
            var size = new BMap.Size(26,26);
            var offset = new BMap.Size(0,-13);
            var imageSize = new BMap.Size(26,26);
            var icon = new BMap.Icon("http://weixin.fheb.cn:99/woaMapCdn/lmark.png",size,{
                imageSize: imageSize
            });

            // 画图标
            for (var i = 0,j = trackPoint.length; i < j; i++) {
                var marker = new BMap.Marker(trackPoint[i],{
                    icon: icon,
                    offset: offset
                }); // 创建标注    
                window.map.addOverlay(marker);
            }

            //根据经纬极值计算绽放级别。 (从网上复制)
            function getZoom(maxLng,minLng,maxLat,minLat) {
                var zoom = ["50","100","200","500","1000","2000","5000","10000","20000","25000","50000","100000","200000","500000","1000000","2000000"]; // 级别18到3。
                var pointA = new BMap.Point(maxLng,maxLat); // 创建点坐标A
                var pointB = new BMap.Point(minLng,minLat); // 创建点坐标B
                var distance = map.getDistance(pointA,pointB).toFixed(1); //获取两点距离,保留小数点后两位
                for (var i = 0,zoomLen = zoom.length; i < zoomLen; i++) {
                    if (zoom[i] - distance > 0) {
                        return 18 - i + 3; //之所以会多3，是因为地图范围常常是比例尺距离的10倍以上。所以级别会增加3。
                    }
                }
            }

            //  (从网上复制)
            function setZoom(points) {
                if (points.length > 0) {
                    var maxLng = points[0].lng;
                    var minLng = points[0].lng;
                    var maxLat = points[0].lat;
                    var minLat = points[0].lat;
                    var res;
                    for (var i = points.length - 1; i >= 0; i--) {
                        res = points[i];
                        if (res.lng > maxLng) maxLng = res.lng;
                        if (res.lng < minLng) minLng = res.lng;
                        if (res.lat > maxLat) maxLat = res.lat;
                        if (res.lat < minLat) minLat = res.lat;
                    }
                    var cenLng = (parseFloat(maxLng) + parseFloat(minLng)) / 2;
                    var cenLat = (parseFloat(maxLat) + parseFloat(minLat)) / 2;
                    var zoom = getZoom(maxLng,minLng,maxLat,minLat);
                    //移动并且缩放地图
                    // window.map.centerAndZoom(new BMap.Point(cenLng, cenLat), zoom);
                } else {
                    //没有坐标，显示全中国
                    window.map.centerAndZoom(new BMap.Point(103.388611,35.563611),5);
                }
            }

            setZoom(pointArr)
        },
        //渲染标注
        renderMarkData: function (data) {
            if (data) {
                //清除全部标注
                // window.map.clearOverlays();
                //渲染标注      
                for (var i = 0; i < data.length; i++) {
                    var item = data[i] || {};
                    //创建小红点 
                    var point = item.point;
                    var text = item.text;
                    this.addMarker(point);

                    //创建文本
                    // if (text) {
                    //     var opts = {
                    //         position: point,    // 指定文本标注所在的地理位置
                    //         offset: new BMap.Size(0, -30)    //设置文本偏移量
                    //     }
                    //     var label = new BMap.Label(text, opts);  // 创建文本标注对象
                    //     label.setStyle({
                    //         color: "rgb(0,247,222)",
                    //         fontSize: "12px",
                    //         height: "20px",
                    //         lineHeight: "20px",
                    //         fontFamily: "微软雅黑",
                    //         backgroundColor: 'transparent',
                    //         border: '0px solid red',
                    //         textAlign: 'left'
                    //     });
                    //     window.map.addOverlay(label);
                    // }

                }
            }

        },

        createMap: function (point) {
            var map = new BMap.Map("map");
            window.map = map;
            map.addControl(new BMap.NavigationControl());               // 添加平移缩放控件 
            map.enableScrollWheelZoom();                            //启用滚轮放大缩小 
            map.disable3DBuilding();
            map.centerAndZoom(point,8);
            //监听地图缩放 缩放级别低于5将跳到全国地图
            map.addEventListener('zoomend',function () {
                if (map.getZoom() <= 5) {
                    if (window.parent.changeIframeSrc) {
                        window.parent.changeIframeSrc(window.w.index)
                    }
                }
            })

            //将市的轮廓画突出一些
            this.drawPBorder();

            map.setMapStyle({
                styleJson: [
                    {
                        "featureType": "background",
                        "elementType": "all",
                        "stylers": {
                            "color": "#0050a5",
                            "lightness": -18,
                            "saturation": -40
                        }
                    },
                    {
                        "featureType": "poilabel",
                        "elementType": "all",
                        "stylers": {
                            "visibility": "off"
                        }
                    },
                    {
                        "featureType": "town",
                        "elementType": "labels",
                        "stylers": {
                            "visibility": "on"
                        }
                    },
                    {
                        "featureType": "boundary",
                        "elementType": "all",
                        "stylers": {
                            "color": "#055a9a"
                        }
                    },
                    {
                        "featureType": "boundary",
                        "elementType": "geometry",
                        "stylers": {
                            "color": "#ddd"
                        }
                    },
                    {
                        "featureType": "highway",
                        "elementType": "all",
                        "stylers": {
                            "color": "#e69138ff",
                            "visibility": "off"
                        }
                    },
                    {
                        "featureType": "city",
                        "elementType": "labels.text.fill",
                        "stylers": {
                            "color": "#ac9f79",
                            "weight": "3.6",
                            "lightness": -16,
                            "visibility": "on"
                        }
                    },
                    {
                        "featureType": "arterial",
                        "elementType": "all",
                        "stylers": {
                            "color": "#0370c3"
                        }
                    },
                    {
                        "featureType": "railway",
                        "elementType": "all",
                        "stylers": {
                            "visibility": "off"
                        }
                    },
                    {
                        "featureType": "district",
                        "elementType": "labels",
                        "stylers": {
                            "color": "#66666682",
                            "weight": "3.6",
                            "lightness": 47,
                            "visibility": "on"
                        }
                    },
                    {
                        "featureType": "local",
                        "elementType": "all",
                        "stylers": {
                            "color": "#ac9f79",
                            "weight": "3.6",
                            "lightness": -16,
                            "visibility": "off"
                        }
                    },
                    {
                        "featureType": "arterial",
                        "elementType": "all",
                        "stylers": {
                            "visibility": "off"
                        }
                    },
                    {
                        "featureType": "subway",
                        "elementType": "all",
                        "stylers": {
                            "visibility": "off"
                        }
                    }
                ]
            });
        },
        drawPBorder: function () {
            var bdary = new BMap.Boundary();
            var proLen = window.w.mapData && window.w.mapData.length;
            var index = 0;
            var bdaryFn = function () {
                if (index < proLen) {
                    bdary.get(window.w.mapData[index]['province'],function (rs) {       //获取行政区域   
                        var count = rs.boundaries.length; //行政区域的点有多少个 
                        for (var i = 0; i < count; i++) {
                            var ply = new BMap.Polygon(rs.boundaries[i],
                                {
                                    strokeWeight: 2, //设置多边形边线线粗
                                    strokeOpacity: 1, //设置多边形边线透明度0-1
                                    StrokeStyle: "solid", //设置多边形边线样式为实线或虚线，取值 solid 或 dashed
                                    strokeColor: "#075e92", //设置多边形边线颜色
                                    fillColor: "##075e92", //设置多边形填充颜色
                                    fillOpacity: 0.01 //设置多边形填充颜色透明度0-1  注：标红的地放你们可以去掉看一下效果，自己体验一下
                                }); //建立多边形覆盖物
                            window.map.addOverlay(ply);  //添加覆盖物  
                        }

                        index++;
                        if (index < proLen) {
                            bdaryFn();

                        }
                    })
                }
            }

            bdaryFn();

            //市轮廓渲染
            var _province = global.province;
            for (var i = 0; i < proLen; i++) {
                if (window.w.mapData[i].province === _province) {
                    var _index = 0;
                    var city = window.w.mapData[i].citys;
                    if (city) {
                        var _bdaryFn = function () {
                            if (_index < city.length) {
                                bdary.get(city[_index],function (rs) {       //获取行政区域   
                                    var count = rs.boundaries.length; //行政区域的点有多少个 
                                    for (var i = 0; i < count; i++) {
                                        var ply = new BMap.Polygon(rs.boundaries[i],
                                            {
                                                strokeWeight: 2, //设置多边形边线线粗
                                                strokeOpacity: 1, //设置多边形边线透明度0-1
                                                StrokeStyle: "solid", //设置多边形边线样式为实线或虚线，取值 solid 或 dashed
                                                strokeColor: "#075e92", //设置多边形边线颜色
                                                fillColor: "##075e92", //设置多边形填充颜色
                                                fillOpacity: 0.01 //设置多边形填充颜色透明度0-1  注：标红的地放你们可以去掉看一下效果，自己体验一下
                                            }); //建立多边形覆盖物
                                        window.map.addOverlay(ply);  //添加覆盖物  
                                    }

                                    _index++;
                                    if (_index < city.length) {
                                        _bdaryFn();
                                    }
                                })
                            }
                        }

                        _bdaryFn();
                    }
                    return;
                }
            }


        },
        init: function () {
            var _this = this;
            //从网址中取出省份经纬度
            // http://localhost:8000/map/province/index.html?province=北京 
            //将地址的省名转为坐标点point 
            var myGeo = new BMap.Geocoder();
            // 将地址解析结果显示在地图上,并调整地图视野
            myGeo.getPoint(global.province,function (point) {
                _this.createMap(point);
            });
        }
    }
    bMap.init();

    //echarts
    var render = {
        ins: {},
        //左二
        dataIndex: function () {
            var option1 = {
                color: ['#006699','#4cabce','#e5323e'],
                title: {
                    text: '经营数据指标',
                    padding: [25,20],
                    textStyle: {
                        color: 'white'
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
                    },
                    // data: ['2012', '2013'],
                    data: []
                },
                yAxis: {
                    type: 'value',
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
                series: [
                    {
                        type: 'bar',
                        // data: [1000, 500]
                        data: []
                    }
                ]
            };

            var dataIndexdom = document.getElementById('dataIndex');
            var edom = echarts.init(dataIndexdom,'shine');
            edom.setOption(option1);
            return edom;
        },
        //底部
        bottom: function () {
            for (var i = 0; i < quanIdArr.length; i++) {
                var _id = quanIdArr[i];
                var option = getQuanOptionData({});
                var dom = document.getElementById(_id);
                var edom = echarts.init(dom,'shine');
                edom.setOption(option);
                edom.on('click',function (params) {
                    var link = params.data.link;
                    gloFn.echartsClick({ link: link })
                });
                this.ins[_id] = window[_id + 'I'] = edom;
            }
        },
        //地图自适应
        autoSize: function () {
            //重置样式
            var dw = $(document).width();
            var dh = $(document).height();
            $('#container .left').css({
                width: '25%'
            })

            //字体大小自适应
            //排名
            $('.ranking_con .item').css({
                "font-size": dw / 100 * 0.75 + 'px'
            })

            $('.ranking_con .item .row').css({
                "font-size": dw / 100 * 0.55 + 'px'
            })

            //轮播
            $('#Broadcast .nav, #Broadcast .row').css({
                "font-size": dw / 100 * 0.75 + 'px'
            })
            //资源配置
            $('#config .title').css({
                "font-size": dw / 100 * 1 + 'px'
            })
            $('#config ul li').css({
                "font-size": dw / 100 * 0.75 + 'px'
            })
            $('#nav .title').css({
                "font-size": dw / 100 * 1 + 'px'
            })
            $('#config .subtit').css({
                "font-size": dw / 100 * 0.85 + 'px'
            })

            //智慧工地
            $('#nav .title').css({
                "font-size": dw / 100 * 1 + 'px'
            })
            $('#nav .con .item').css({
                "font-size": dw / 100 * 0.85 + 'px'
            })
            $('#nav .con .item img').css({
                "width": dw / 100 * 1.65 + 'px'
            })

            //第一块内容
            $('#ranking li').css({
                "font-size": dw / 100 * 0.9 + 'px'
            })

            $('.bottom .item').css({
                'height': $('.bottom .wrapper').height()
            })

            //重置图表  
            this.ins['dataIndex'].resize();
            for (var i = 0; i < quanIdArr.length; i++) {
                var _id = quanIdArr[i];
                this.ins[_id].resize();
            }


            //logo文字自适应
            $('.top .logo .text').css({
                "fontSize": (dw / 100 * 1.15) + "px",
                "height": ($('.top .logo img').height()) + "px",
                "lineHeight": ($('.top .logo img').height()) + "px"
            });
        },
        //监听地图自适应
        listerAutoSize: function () {
            this.autoSize();
            var _this = this;
            $(window).resize(function (e) {
                _this.autoSize()
            })
        },
        //初始化地图
        init: function () {
            this.bottom();
            this.ins['dataIndex'] = this.dataIndex();
            this.listerAutoSize();


            //获取首页标题
            getData.getTitle('getBranchCompanyNameByToken',{},function (data) {//资源配置 
                $('.top .logo .text').text(data);
            });
        }
    }

    //刷新函数
    var refresh = function () {
        $('.updateTime span').text(new Date().toLocaleDateString() + new Date().toLocaleTimeString());
        //echarts数据
        getData.getDataIndexData(render.ins['dataIndex'],'getBranchProvinceOperatingIndicatorsList',{ provinceName: global.province });
        for (var i = 0; i < quanIdArr.length; i++) {
            var _id = quanIdArr[i];
            getData.getQuanData(render.ins[_id],_id,'公路','#35ffff','getBranchProvinceZjDpEngineeringType',{ provinceName: global.province });
        }

        //地图标注
        getData.getMarkData('getBranchProvinceMap',{ provinceName: global.province },function (data) {
            var _data = [];//转为经纬度后存入 {longitude:'',dimensionality:'',text:''}
            var index = 0;
            global.markerData = data; 
             
            var getPoint = function (item) {
                var city = item.city;
                if (city) {
                    var myGeo = new BMap.Geocoder();
                    // 将地址解析结果显示在地图上,并调整地图视野
                    myGeo.getPoint(city,function (point) {
                        var _obj = {
                            point: point,
                            text: item['text'],
                            city: city
                        }
                        _data.push(_obj);
                        index++;
                        if (index < data.length) {
                            getPoint(data[index])
                        } else { 
                            // 解析完成  
                            if(point){ 
                                window.map && window.map.centerAndZoom(point, 8);
                            }
                            bMap.renderMarkData(_data);
                        }
                    }, global.province);
                } else {
                    index++;
                    if (index < data.length) {
                        getPoint(data[index])
                    } else {
                        // 解析完成
                        bMap.renderMarkData(_data);
                    }
                }

            }
            getPoint(data[index]);
        })

        getData.getBannerData('signed',function (data) {//广播
            indexFn.renderBannerCon(data);
        });
        getData.getConfigData(function (data) {//资源配置
            indexFn.renderConfig(data);
        },'getBranchProvinceResourceAllocation',{ provinceName: global.province });
        getData.getWisdomData(function (data) {
            indexFn.renderNavCon(data);
        });
        //第一块的数据
        getData.getOneBlankData('getBranchProvinceProjectInformationList',{ provinceName: global.province },function (data) {
            indexFn.renderOneBlank(data);
        })

    }

    render.init();
    refresh();
})