
//是否需要loading 第一次需要loading
var loading = {}; //每一个块都是有自己的loading

var mapDataFi = function (data) {
    var res = [];
    for (var i = 0; i < data.length; i++) {
        if (data[i] && data[i].longitude && data[i].latitude && data[i].value) {
            var longitude = data[i].longitude;
            var latitude = data[i].latitude;
            var value = data[i].value;
            var _d = [longitude, latitude, value];
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
        zlevel: 0
    },
    //是否需要loading 第一次需要loading 
    getMapData: function (mapI) {
        if (!loading.mapI) {
            mapI.showLoading('default', this.loadingOpt);
            loading.mapI = true
        }
        // url存在点击将会打开网址
        // { name: '北京', value: 200, url:'http://www.baidu.com' },

        w.ajax('getDpBranchCountryMapList', {}, function (data, message, success) {
            if (success) {
                data.series.name = 'China'
                data.series.roam = false;
                data.series.type = 'map';
                // data.series.itemStyle
                // delete data.series.coordinateSystem
                data.geo = {};

                data.geo.itemStyle = {
                    map: 'china',
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

                // data.series.data = mapDataFi(data.series.data);
                // data.series.type = 'heatmap';
                // data.series.coordinateSystem = 'bmap';
                data.series = [data.series];


                mapI.setOption(data);

                mapI.hideLoading();
            }
        })
    },
    //获取公司排名数据
    getRankingData: function (rankingI, name) {
        if (!loading.rankingI) {
            rankingI.showLoading('default', this.loadingOpt);
            loading.rankingI = true
        }
        name = name || 'number';

        // data = { //数据格式
        //     number:{ 
        //         xAxis: {
        //             name: '个'
        //         },
        //         yAxis: {
        //             data: ['一公司', '二公司', '三公司', '四公司', '五公司', '六公司', '七公司', '北京建筑分公司']
        //         },
        //         series: [{
        //             name: '项目个数',
        //             type: 'bar',
        //             data: [100, 10, 50, 29034, 88, 131744, 888, 6]
        //         }]
        //     },
        //     balance:{
        //         xAxis: {
        //             name: '万元'
        //         },
        //         yAxis: {
        //             data: ['一公司', '二公司', '三公司', '四公司', '五公司', '六公司', '七公司', '北京建筑分公司']
        //         },
        //         series: [{
        //             name: '项目余额',
        //             type: 'bar',
        //             data: [18203, 10, 23489, 29034, 104970, 131744, 630230, 630230]
        //         }]
        //     }
        // }
        w.ajax('getDpBranchCompanyRankingsList', {}, function (data, message, success) {
            if (success) {
                rankingI.hideLoading();
                rankingI.setOption(data[name]);
            }
        })

    },
    //获取经营数据指标数据
    getDataIndexData: function (manageI, apiName, params) {
        apiName = apiName || 'getDpBranchOperatingIndicatorsList';
        if (!loading.manageI) {
            manageI.showLoading('default', this.loadingOpt);
            loading.rankingI = true
        }

        w.ajax(apiName, params, function (data, message, success) {
            if (success) {
                manageI.setOption(data);
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
    getQuanData: function (quanI, id, name, preColor, apiName, params) { //图标实例* id* 中文名 进度条颜色
        // console.log(apiName)
        var apiName = apiName || 'getDpBranchEngineeringTypeList';
        if (!loading[id]) {
            quanI.showLoading('default', this.loadingOpt);
            loading[id] = true;
        }
        w.ajax(apiName, params, function (data, message, success) {
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
    getBannerData: function (name, cb, apiName, params) { //传入标识返回哪个bar的值
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
        w.ajax(apiName, params, function (data, message, success) {
            if (success) {
                if (data) {
                    cb(data[name]);
                }
            }
        })
    },
    //获取资源配置块
    getConfigData: function (cb, apiName, params) { //传入标识返回哪个bar的值
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
        w.ajax(apiName, params, function (data, message, success) {
            if (success) {
                cb(data);
            }
        })
    },

    //获取智慧工地数据
    getWisdomData: function (cb, apiName, params) {
        var params = params || {};
        var apiName = apiName || 'getDpBranchResourceAllocationList';
        var icon = 'http://weixin.fheb.cn:99/woaMapCdn/zhi_hui_gong_di/';
        var data = [
            { icon: icon + '环境监测.png', title: '环境监测', url: '' },
            { icon: icon + '水泥搅拌车.png', title: '拌合站数据', url: '' },
            { icon: icon + '视频监控.png', title: '设备监控', url: '' },
            { icon: icon + '建筑材料.png', title: '张拉压浆数据', url: '' },
            { icon: icon + '视频.png', title: '现场视频', url: '' },
            { icon: icon + '显微镜.png', title: '实验数据', url: '' },
            { icon: icon + '定位.png', title: '人员定位', url: '' },
            { icon: icon + '更多 (1).png', title: '更多', url: '' }
        ]
        cb(data);
    },
    getOneBlankData: function (apiName, params, cb) {
        w.ajax(apiName, params, function (data, message, success) {
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
    getMarkData: function (apiName, params, cb) {
        var data = [//数据格式
            {
                city: '贵阳市',
                text: '这是贵阳描述'
            }, {
                city: '毕节市',
                text: '这是毕节项目'
            }, {
                city: '六盘水市',
                text: '这是六盘水项目'
            }, {
                city: '铜仁市',
                text: '这是铜仁项目'
            }
        ];
        cb(data);
    },
    getMarkerProjectList: function (apiName, params, cb) {
        var _data = {
            title: '项目列表',
            data: [
                {
                    title: '测试',
                    status: '执行中',
                    type: '综合项目',
                    money: '511000',
                    unit: '万元'
                },
                {
                    title: '测试',
                    status: '执行中',
                    type: '综合项目',
                    money: '511000',
                    unit: '万元'
                },
                {
                    title: '测试',
                    status: '执行中',
                    type: '综合项目',
                    money: '511000',
                    unit: '万元'
                },
                {
                    title: '测试',
                    status: '执行中',
                    type: '综合项目',
                    money: '511000',
                    unit: '万元'
                },
                {
                    title: '测试',
                    status: '执行中',
                    type: '综合项目',
                    money: '511000',
                    unit: '万元'
                },
                {
                    title: '测试',
                    status: '执行中',
                    type: '综合项目',
                    money: '511000',
                    unit: '万元'
                },
                {
                    title: '测试',
                    status: '执行中',
                    type: '综合项目',
                    money: '511000',
                    unit: '万元'
                }
            ]
        }
        cb(_data);
    }
}
window.getData = getData;

//生成底部圈的option
function getQuanOptionData(obj) {
    // console.log(obj)
    obj = obj || {};
    var name = obj.name || ''; //中文名称
    var value = obj.value || 0;//值
    var prColor = obj.prColor || "#35ffff";//进度条的颜色
    var option = {
        tooltip: {
            trigger: 'item',
            position: ['10', '10'],
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
            radius: ['90%', '75%'],
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
            }, {
                name: '',
                key: obj.key,
                value: 100 - value
            }]
        }]
    }
    return option;
}
window.getQuanOptionData = getQuanOptionData;