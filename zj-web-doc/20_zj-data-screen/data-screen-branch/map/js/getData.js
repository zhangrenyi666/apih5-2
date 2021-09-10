
//是否需要loading 第一次需要loading
var loading = {}; //每一个块都是有自己的loading

var mapDataFi = function (data) {
    var res = [];
    for (var i = 0; i < data.length; i++) {
        if (data[i] && data[i].longitude && data[i].latitude && data[i].value) {
            var longitude = data[i].longitude;
            var latitude = data[i].latitude;
            var value = data[i].value;
            var name = data[i].name;
            var _d = [longitude,latitude,value,name];
            res.push(_d)
        }
    }
    return res;
}


var getData = {
    loadingOpt: {
        text: 'loading',
        color: '#c23531',
        textColor: '#ffffff',
        maskColor: 'rgba(0, 0, 0, 0.35)',
        zlevel: 3
    },
    //是否需要loading 第一次需要loading 
    getMapData: function (mapI) {
        if (!loading.mapI) {
            mapI.showLoading('default',this.loadingOpt);
            loading.mapI = true;
        }
        // url存在点击将会打开网址
        // { name: '北京', value: 200, url:'http://www.baidu.com' },

        w.ajax('getDpBranchCountryMapList',{},function (data,message,success) {
            if (success) {
                data.series.type = 'effectScatter';
                data.series.animation = true;
                //设置小圈最小值 10  最大值60
                data.series.symbolSize = function (val) {
                    if (val[2] / 100000 > 60) {
                        return 60;
                    } else if (val[2] / 100000 < 10) {
                        return 10;
                    }
                    return val[2] / 100000;
                }
                if (!data.series.itemStyle.normal.color) {
                    //后台没给值就使用默认的
                    data.series.itemStyle.normal.color = '#f4ae21';
                }
                data.series.showEffectOn = 'render';
                data.series.rippleEffect = {
                    brushType: 'stroke'
                }

                data.series.data = mapDataFi(data.series.data);
                data.series = [data.series];

                mapI.setOption(data);

                mapI.hideLoading();
            }
        })
    },
    //获取公司排名数据
    getRankingData: function (rankingI,name) {
        if (!loading.rankingI) {
            rankingI.showLoading('default',this.loadingOpt);
            loading.rankingI = true;
        }
        name = name || 'number';

        // data = { //数据格式
        //     number:{ 
        //         xAxis: {
        //             name: '个'
        //         },
        //         yAxis: {
        //             data: ['一公司', '二公司']
        //         },
        //         series: [{
        //             name: '项目个数',
        //             type: 'bar',
        //             data: [{value,link}, {value,link}]
        //         }]
        //     },
        //     balance:{ 
        //     }
        // }
        w.ajax('getDpBranchCompanyRankingsList',{},function (data,message,success) {
            // var number = data.number;
            // var _data = number.series.data; 
            // let _a = [];
            // for (var i = 0; i < _data.length; i++) {
            //     _a.push({
            //         value: number.series.data[i],
            //         link: 'https://baidu.com'
            //     })
            // }
            // data.number.series.data = _a  

            if (success) {
                rankingI.hideLoading();
                rankingI.setOption(data[name]);
            }
        })

    },
    //获取经营数据指标数据
    getDataIndexData: function (manageI,apiName,params) {
        apiName = apiName || 'getDpBranchOperatingIndicatorsList';
        if (!loading.manageI) {
            manageI.showLoading('default',this.loadingOpt);
            loading.rankingI = true;
        }

        w.ajax(apiName,params,function (data,message,success) {
            if (success) {
                data && manageI.setOption(data);
                manageI.hideLoading();
            }
        })


        // setTimeout(function () {
        //     //获取经营数据指标数据
        //     var rankingData = {
        //         legend: {
        //             data: ['产值情况', '合同额', '计量']
        //         },
        //         xAxis: {
        //             data: ['2012']
        //         },
        //         series: [{
        //             name: '产值情况',
        //             type: 'bar',
        //             data: [220, 182]
        //         },
        //         {
        //             name: '合同额',
        //             type: 'bar',
        //             data: [150, 232]
        //         },
        //         {
        //             name: '计量',
        //             type: 'bar',
        //             data: [98, 77]
        //         }]
        //     }
        //     rankingI.setOption(rankingData);
        //     rankingI.hideLoading();
        // }, 0);
    },

    //获取底部小圈数据 
    getQuanData: function (quanI,id,name,preColor,apiName,params) { //图标实例* id* 中文名 进度条颜色
        // console.log(apiName)
        var apiName = apiName || 'getDpBranchEngineeringTypeList';
        if (!loading[id]) {
            quanI.showLoading('default',this.loadingOpt);
            loading[id] = true;
        }
        w.ajax(apiName,params,function (data,message,success) {
            if (success) {
                quanI.setOption(getQuanOptionData(data[id]));
                quanI.hideLoading();
            } else {
                quanI.hideLoading();
            }
        })

        // { 
        //     XXX:{
        //         name: name,
        //         value: (Math.random() * 100).toFixed(2),
        //         prColor: prColor
        //     },
        //     XXX:{
        //         name: name,
        //         value: (Math.random() * 100).toFixed(2),
        //         prColor: prColor
        //     }
        // } 
    },

    //获取轮播数据
    getBannerData: function (name,cb,apiName,params) { //传入标识返回哪个bar的值
        var params = params || {};
        // {
        //     Signed: [//合同签订
        //         [{ text: '1公司' }, { text: '今天下午' }, { text: '11亿' }],
        //         [{ text: '1公司' }, { text: '今天下午' }, { text: '11亿' }]
        //     ],
        //         account: [ //合同结算
        //             [{ text: '1公司' }, { text: '今天下午' }, { text: '11亿' }],
        //             [{ text: '1公司' }, { text: '今天下午' }, { text: '11亿' }]
        //         ],
        //             storage: [ //物资入库 供应商
        //                 [{ text: '1公司' }, { text: '今天下午' }, { text: '11亿' }],
        //                 [{ text: '1公司' }, { text: '今天下午' }, { text: '11亿' }]
        //             ],
        //                 supplier: [ //供应商
        //                     [{ text: '1公司' }, { text: '今天下午' }, { text: '11亿' }],
        //                     [{ text: '1公司' }, { text: '今天下午' }, { text: '11亿' }]
        //                 ]
        // }

        var apiName = apiName || 'getDpBranchDynamicInformationList';
        w.ajax(apiName,params,function (data,message,success) {
            if (success) {
                if (data) {
                    cb(data[name]);
                }
            }
        })
    },
    //获取资源配置块
    getConfigData: function (cb,apiName,params) { //传入标识返回哪个bar的值
        var params = params || {};
        // var icon = 'http://weixin.fheb.cn:99/woaMapCdn/总设备数.png';
        // var data = {
        //     inventory: [
        //         { icon: icon, title: '钢材', value: (Math.random() * 1000000).toFixed(2) },
        //         { icon: icon, title: '水泥', value: (Math.random() * 1000000).toFixed(2) },
        //         { icon: icon, title: '地材', value: (Math.random() * 1000000).toFixed(2) },
        //         { icon: icon, title: '沥青', value: (Math.random() * 1000000).toFixed(2) }
        //     ],
        //     equipment: [
        //         { icon: icon, title: '自有设备', value: (Math.random() * 1000000).toFixed(2) },
        //         { icon: icon, title: '外租设备', value: (Math.random() * 1000000).toFixed(2) },
        //         { icon: icon, title: '外租设备', value: (Math.random() * 1000000).toFixed(2) }
        //     ]
        // }

        var apiName = apiName || 'getDpBranchResourceAllocationList';
        w.ajax(apiName,params,function (data,message,success) {
            if (success) {
                cb(data);
            }
        })
    },

    //获取智慧工地数据
    getWisdomData: function (cb,apiName,params) {
        var params = params || {};
        var apiName = apiName || 'getDpBranchResourceAllocationList';
        var icon = 'http://weixin.fheb.cn:99/woaMapCdn/zhi_hui_gong_di/';
        var data = [ //isOpen是否可以被点击
            { icon: icon + '环境监测.png',title: '环境监测',url: '' },
            { icon: icon + '水泥搅拌车.png',title: '拌合站数据',url: '' },
            { icon: icon + '视频监控.png',title: '设备监控',url: '' },
            { icon: icon + '建筑材料.png',title: '张拉压浆数据',url: '' },
            // { icon: icon + '视频.png', title: '视频监控', url: '', isOpen: true },
            { icon: icon + '视频.png',title: '视频监控',url: '' },
            { icon: icon + '显微镜.png',title: '实验数据',url: '' },
            { icon: icon + '定位.png',title: '人员定位',url: '' },
            { icon: icon + '更多 (1).png',title: '更多',url: '' }
        ]
        cb(data);
    },
    //获取全国地图第一块的数据
    getOneBlankData: function (apiName,params,cb) {
        w.ajax(apiName,params,function (data,message,success) {
            // data = [
            //     { 
            //         //   项目名称：orgName
            //         // 工程类别：projType
            //         // 合同总额：contractCost
            //         // 建设单位：consultative 
            //     }
            // ]
            if (success) {
                _data = {
                    title: '项目列表',
                    data: data
                }
                cb(_data);
            }
        })
    },
    //获取项目地图第一块的数据 
    getProjectOneBlankData: function (apiName,params,cb) {
        w.ajax(apiName,params,function (data,message,success) {
            if (success) {
                _data = {
                    title: '基本情况',
                    data: data
                }
                cb(_data);
            }
        })
        // var icon = 'http://weixin.fheb.cn:99/woaMapCdn/zhi_hui_gong_di/环境监测.png';
        // var data = {
        //     title: '基本情况',
        //     data: [  
        //         {
        //             label: '全国总造价',
        //             value: '39.44亿',
        //             icon: icon
        //         }
        //     ]
        // };
        // cb(data)
    },
    //获取月趋势图
    getMonthTrendDiagramData: function (apiName,params,cb) {
        // var icon = 'http://weixin.fheb.cn:99/woaMapCdn/总设备数.png';
        // var data = {
        //     title:'月趋势图',
        //     number: 68,
        //     unit: '天',
        //     status: '安全生产',
        //     infoList: [
        //         {
        //             label: '现场作业人员',
        //             value: '123人',
        //             icon: icon
        //         }, 
        //     ]
        // }
        w.ajax(apiName,params,function (data,message,success) {
            if (success) {
                cb(data)
            }
        })
    },
    getMarkData: function (apiName,params,cb) {
        // var data = [//数据格式
        //     {
        //         city: '贵阳市',
        //         text: '这是贵阳描述'
        //     }, {
        //         city: '毕节市',
        //         text: '这是毕节项目'
        //     }, {
        //         city: '六盘水市',
        //         text: '这是六盘水项目'
        //     }, {
        //         city: '铜仁市',
        //         text: '这是铜仁项目'
        //     }
        // ];
        // cb(data); 
        w.ajax(apiName,params,function (data,message,success) {
            if (success) {
                cb(data);
            }
        })
    },
    getMarkerProjectList: function (apiName,params,cb) {
        w.ajax(apiName,params,function (data,message,success) {
            if (success) {
                var _data = {
                    title: '项目列表',
                    data: data
                }
                cb(_data);
            }
        })
        // var _data = {
        //     title: '项目列表',
        //     data: [
        //         {
        //             title: '测试',
        //             status: '执行中',
        //             type: '综合项目',
        //             money: '511000',
        //             unit: '万元'
        //         }
        //     ]
        // }
        // cb(_data);
    },
    //获取首页标题
    getTitle: function (apiName,params,cb) {
        w.ajax(apiName,params,function (data,message,success) {
            if (success) {
                cb(data)
            } else {

            }
        })
    }
}
window.getData = getData;

//生成底部圈的option
function getQuanOptionData(obj) {
    // console.log(obj)
    obj = obj || {};
    var name = obj.name || ''; //中文名称
    var link = obj.link || ''; //链接地址
    var value = obj.value || 0;//值
    var prColor = obj.prColor || "#35ffff";//进度条的颜色
    var option = {
        tooltip: {
            trigger: 'item',
            position: ['10','10'],
            formatter: function (obj) {
                return obj.data.key
            }
        },
        title: {
            text: value + '%', //+'%\n'+name,
            subtext: name,
            x: 'center',
            y: '25%',
            textStyle: {
                color: '#fff',
                fontSize: '13px'
            },
            subtextStyle: {
                color: '#fff',
                fontSize: '13px'
            }
        },
        color: ['rgba(176, 212, 251, 1)'],
        series: [{
            name: name,
            type: 'pie',
            clockWise: true,
            radius: ['90%','75%'],
            itemStyle: {
                normal: {
                    labelLine: {
                        show: false
                    },
                    label: {
                        show: false
                    }
                }
            },
            hoverAnimation: false,
            data: [{
                key: obj.key,
                value: value,
                link: link,
                // name: '进度条',
                itemStyle: {
                    normal: {
                        color: prColor,//青色
                        label: {
                            show: false
                        },
                        labelLine: {
                            show: false
                        }
                    }
                }
            },{
                name: '',
                link: link,
                key: obj.key,
                value: 100 - value
            }]
        }]
    }
    return option;
}
window.getQuanOptionData = getQuanOptionData;