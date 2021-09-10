import React, { Component } from 'react';
import { ZTXM, ZTAQTJ, ZTXXBB } from './ZT';
import { JDZT, JDSJ, JDGT, JDST, JDLJZ } from './JD';
import { AQRY, AQTZ, AQYH, AQJK, AQAQ } from './AQ';
import { ZLZL, ZLYB, ZLYY, ZLZN, ZLBHZ } from './ZL';
import { ZTQK, ZTFZ } from './ZTFB';
import { JDJD, JDTJ, JDLC, JDZJ } from './JDFB';
import { AQXC } from './AQFB';
import { ZLFBZL, ZLFBJC, ZLFBYB } from './ZLFB';
import { Tabs, Modal, Menu, Dropdown, message as Msg, Button } from 'antd';
import s from './style.less';
import moment from 'moment';
import ReactEcharts from 'echarts-for-react';
import 'echarts/map/js/china.js';
import QnnMap from '../../modules/qnn-map';
const logo = require('./logo.png');
const background = require('../../../imgs/background.png');
const TabPane = Tabs.TabPane;
const sectionsStyle = {
    backgroundImage: `url(${background})`,
    backgroundRepeat: 'no-repeat',
    backgroundSize: '100% 100%'
};
class index extends Component {
    state = {
        logo: logo,
        nowTime: moment().format('YYYY年MM月DD日'),
        nowTimes: moment().format('HH点mm分ss秒'),
        activeKey: '1',
        visible: false,
        key: '',
        data: [],
        flag: false,
        initMapConfig: {
            resizeEnable: true,
            rotateEnable: true,
            pitchEnable: true,
            zoom: 10,
            pitch: 55,
            // rotation: -15,
            // viewMode: '3D',//开启3D视图,默认为关闭
            buildingAnimation: true,//楼块出现是否带动画
            expandZoomRange: true,
            zooms: [3, 18],
            center: [108.772149, 28.264934],
        },
        dtData: [
            {
                Lng: 108.493189,
                lat: 28.496638,
                title: '一分部',
                stakeMark: 'k0+000~k10+910',
                overallLength: '10.91KM',
                team: '18组',
                personnel: '1人',
                departmentId: 'zx01'
            },
            {
                Lng: 108.465593,
                lat: 28.417373,
                title: '二分部',
                stakeMark: 'k10+910~k21+300',
                overallLength: '10.38KM',
                team: '24组',
                personnel: '0人',
                departmentId: 'zx02'
            },
            {
                Lng: 108.456395,
                lat: 28.342116,
                title: '三分部',
                stakeMark: 'k21+300~k30+950',
                overallLength: '9.65KM',
                team: '27组',
                personnel: '19人',
                departmentId: 'zx03'
            },
            {
                Lng: 108.454095,
                lat: 28.240333,
                title: '四分部',
                stakeMark: 'k30+950~k40+120',
                overallLength: '9.08KM',
                team: '21组',
                personnel: '52人',
                departmentId: 'zx04'
            },
            {
                Lng: 108.435698,
                lat: 28.148644,
                title: '五分部',
                stakeMark: 'k40+120~k48+540',
                overallLength: '8.38KM',
                team: '12组',
                personnel: '0人',
                departmentId: 'zx05'
            },
            {
                Lng: 108.410401,
                lat: 28.054836,
                title: '六分部',
                stakeMark: 'k48+540~k54+788',
                overallLength: '8.23KM',
                team: '10组',
                personnel: '245人',
                departmentId: 'zx06'
            },
            {
                Lng: 108.582876,
                lat: 28.060956,
                title: '七分部',
                stakeMark: 'k61+423~k75+830',
                overallLength: '14.25KM',
                team: '36组',
                personnel: '184人',
                departmentId: 'zx07'
            },
            {
                Lng: 108.76225,
                lat: 28.091553,
                title: '八分部',
                stakeMark: 'k75+830~k88+000',
                overallLength: '10.35KM',
                team: '28组',
                personnel: '272人',
                departmentId: 'zx08'
            },
            {
                Lng: 108.925526,
                lat: 28.107868,
                title: '九分部',
                stakeMark: 'k88+000~k97+811',
                overallLength: '11.81KM',
                team: '13组',
                personnel: '125人',
                departmentId: 'zx09'
            },
            {
                Lng: 109.1003,
                lat: 28.146606,
                title: '十分部',
                stakeMark: 'k97+811~k107+307',
                overallLength: '9.471KM',
                team: '15组',
                personnel: '134人',
                departmentId: 'zx10'
            }
        ],
        dt1Data: [
            {
                Lng: 108.509821,
                lat: 28.570259,
                title: '太阳坝隧道',
                startMark: 'K0 + 917 —— K2 + 127',
                endMark: 'K0 + 889 —— K2 + 149',
            },
            {
                Lng: 108.509893,
                lat: 28.568166,
                title: '杨柳隧道',
                startMark: 'K2 + 445 —— K2 + 829',
                endMark: 'K2 + 430 —— K2 + 845',
            },
            {
                Lng: 108.509318,
                lat: 28.566453,
                title: '杨柳大桥',
                startMark: 'K2 + 916 —— K3 + 063',
                endMark: 'K2 + 950 —— K3 + 070',
            },
            {
                Lng: 108.508671,
                lat: 28.564549,
                title: '中界隧道',
                startMark: 'K3 + 670 —— K5 + 660',
                endMark: 'K3 + 656 —— K5 + 648',
            },
            {
                Lng: 108.508312,
                lat: 28.563217,
                title: '皂角水中桥',
                startMark: 'K5 + 669 —— K5 + 759',
                endMark: 'K3 + 666 —— K5 + 755',
            },
            {
                Lng: 108.507737,
                lat: 28.562011,
                title: '罗家赛1#大桥',
                startMark: 'K6 + 168 —— K6 + 198',
                endMark: 'K6 + 162 —— K6 + 192',
            },
            {
                Lng: 108.506802,
                lat: 28.559346,
                title: '罗家赛2#大桥',
                startMark: 'K6 + 348 —— K6 + 798',
                endMark: 'K6 + 359 —— K6 + 809',
            },
            {
                Lng: 108.507952,
                lat: 28.556681,
                title: '瑞公山隧道',
                startMark: 'K6 + 809 —— K7 + 122',
                endMark: 'K6 + 819 —— K7 + 167',
            },
            {
                Lng: 108.507521,
                lat: 28.554651,
                title: '心齐大桥',
                startMark: 'K7 + 913 —— K8 + 513',
                endMark: 'K7 + 973 —— K8 + 513',
            },
            {
                Lng: 108.509102,
                lat: 28.553953,
                title: '蒙子树大桥',
                startMark: 'K8 + 902 —— K9 + 082',
                endMark: 'K8 + 897 —— K9 + 077',
            },
            {
                Lng: 108.505222,
                lat: 28.552493,
                title: '高加特大桥',
                startMark: 'K9 + 623 —— K10 + 783',
                endMark: 'K9 + 663 —— K10 + 903',
            }
        ],
        dt2Data: [
            {
                Lng: 108.490021,
                lat: 28.543995,
                title: '路基',
                startMark: 'K10 + 905 —— K11 + 169.5',
                endMark: 'K10 + 905 —— K11 + 169.5',
            },
            {
                Lng: 108.489721,
                lat: 28.542525,
                title: '高家特大桥',
                startMark: 'K10 + 905 —— K11 + 169.5',
                endMark: 'K10 + 905 —— K11 + 169.5',
            },
            {
                Lng: 108.488648,
                lat: 28.541356,
                title: '晓景隧道',
                startMark: 'K10 + 905 —— K11 + 169.5',
                endMark: 'K10 + 905 —— K11 + 169.5',
            },
            {
                Lng: 108.487575,
                lat: 28.539169,
                title: '长岗岭大桥',
                startMark: 'K10 + 905 —— K11 + 169.5',
                endMark: 'K10 + 905 —— K11 + 169.5',
            },
            {
                Lng: 108.486502,
                lat: 28.537699,
                title: '长岗岭隧道',
                startMark: 'K10 + 905 —— K11 + 169.5',
                endMark: 'K10 + 905 —— K11 + 169.5',
            },
            {
                Lng: 108.486116,
                lat: 28.536794,
                title: '水牛池大桥',
                startMark: 'K10 + 905 —— K11 + 169.5',
                endMark: 'K10 + 905 —— K11 + 169.5',
            },
            {
                Lng: 108.486116,
                lat: 28.535663,
                title: '路基',
                startMark: 'K10 + 905 —— K11 + 169.5',
                endMark: 'K10 + 905 —— K11 + 169.5',
            },
            {
                Lng: 108.486502,
                lat: 28.533552,
                title: '水池村大桥',
                startMark: 'K10 + 905 —— K11 + 169.5',
                endMark: 'K10 + 905 —— K11 + 169.5',
            },
            {
                Lng: 108.486974,
                lat: 28.531704,
                title: '葵家村大桥',
                startMark: 'K10 + 905 —— K11 + 169.5',
                endMark: 'K10 + 905 —— K11 + 169.5',
            },
            {
                Lng: 108.486888,
                lat: 28.529819,
                title: '马鞍山大桥',
                startMark: 'K10 + 905 —— K11 + 169.5',
                endMark: 'K10 + 905 —— K11 + 169.5',
            },
            {
                Lng: 108.486942,
                lat: 28.528839,
                title: '火泽头#1大桥',
                startMark: 'K10 + 905 —— K11 + 169.5',
                endMark: 'K10 + 905 —— K11 + 169.5',
            },
            {
                Lng: 108.487414,
                lat: 28.527557,
                title: '火泽头#2大桥',
                startMark: 'K10 + 905 —— K11 + 169.5',
                endMark: 'K10 + 905 —— K11 + 169.5',
            },
            {
                Lng: 108.488229,
                lat: 28.526576,
                title: '火泽头隧道',
                startMark: 'K10 + 905 —— K11 + 169.5',
                endMark: 'K10 + 905 —— K11 + 169.5',
            }
        ],
        dt3Data: [
            {
                Lng: 108.489291,
                lat: 28.524277,
                title: '关塘大桥',
                startMark: 'K23 + 379 —— K24 + 173',
                endMark: 'K23 + 219 —— K24 + 173',
            },
            {
                Lng: 108.489699,
                lat: 28.523052,
                title: '苦竹村大桥',
                startMark: 'K25 + 253 —— K25 + 480',
                endMark: 'K25 + 230 —— K25 + 510',
            },
            {
                Lng: 108.490042,
                lat: 28.521619,
                title: '苦竹坝大桥',
                startMark: 'K25 + 650 —— K26 + 170',
                endMark: 'K26 + 210 —— K26 + 553',
            },
            {
                Lng: 108.490321,
                lat: 28.520469,
                title: '苍堡大桥',
                startMark: 'K26 + 686 —— K27 + 246',
                endMark: 'K26 + 730 —— K27 + 250',
            },
            {
                Lng: 108.490818,
                lat: 28.517346,
                title: '新寨1#大桥',
                startMark: 'K27 + 357.5 —— K27 + 504.5',
                endMark: 'K27 + 359 —— K27 + 506',
            },
            {
                Lng: 108.490946,
                lat: 28.514895,
                title: '新寨2#大桥',
                startMark: 'K27 + 539 —— K27 + 659',
                endMark: 'K27 + 537.5 —— K27 + 657.5',
            },
            {
                Lng: 108.490646,
                lat: 28.512143,
                title: '夏家沟1#大桥',
                startMark: 'K27 + 852 —— K28 + 012',
                endMark: 'K28 + 055 —— K28 + 355',
            },
            {
                Lng: 108.490045,
                lat: 28.510031,
                title: '夏家沟2#大桥',
                startMark: 'K28 + 418 —— K28 + 538',
                endMark: 'K28 + 415 —— K28 + 535',
            },
            {
                Lng: 108.49026,
                lat: 28.508673,
                title: '夏家沟3#大桥',
                startMark: 'ZK28 + 599 —— ZK29 + 479',
                endMark: 'K28 + 594 —— K29 + 104',
            },
            {
                Lng: 108.490346,
                lat: 28.507353,
                title: '联丰特大桥',
                startMark: 'ZK29 + 616 —— ZK30 + 936',
                endMark: 'K28 + 625 —— K30 + 865',
            }
        ],
        dt4Data: [
            {
                Lng: 108.490581,
                lat: 28.505336,
                title: '李家土隧道',
                startMark: 'Z4K30 + 958 —— Z4K32 + 246',
                endMark: 'K30 + 961 —— K32 + 248',
            },
            {
                Lng: 108.49071,
                lat: 28.503658,
                title: 'π路基',
                startMark: 'K32 + 248 —— K33 + 730',
                endMark: 'K32 + 248 —— K33 + 730',
            },
            {
                Lng: 108.490474,
                lat: 28.502206,
                title: '共和大桥右线1#大桥',
                startMark: 'K33 + 795',
                endMark: 'K33 + 795',
            },
            {
                Lng: 108.489551,
                lat: 28.500829,
                title: '共和大桥左线',
                startMark: 'Z5K34 + 073',
                endMark: 'Z5K34 + 073',
            },
            {
                Lng: 108.48869,
                lat: 28.499511,
                title: '共和大桥右线2#大桥',
                startMark: 'K34 + 188',
                endMark: 'K34 + 188',
            },
            {
                Lng: 108.488164,
                lat: 28.498182,
                title: '共和隧道',
                startMark: 'Z5K34 + 428 —— Z5K35 + 004',
                endMark: 'K34 + 478 —— K35 + 021',
            },
            {
                Lng: 108.487993,
                lat: 28.497276,
                title: '白杨垭1#大桥',
                startMark: 'Z5K34 + 428 —— Z5K35 + 004',
                endMark: 'K35 + 287',
            },
            {
                Lng: 108.487735,
                lat: 28.496051,
                title: '白杨垭中桥',
                startMark: 'Z5K35 + 695',
                endMark: 'K35 + 687',
            },
            {
                Lng: 108.48707,
                lat: 28.494353,
                title: '白杨垭2#大桥',
                startMark: 'Z5K35 + 962',
                endMark: 'K36 + 021.864',
            },
            {
                Lng: 108.486855,
                lat: 28.492882,
                title: '杨家堡大桥',
                startMark: 'Z5K36 + 477',
                endMark: 'K36 + 486',
            },
            {
                Lng: 108.486705,
                lat: 28.491666,
                title: 'π路基',
                startMark: 'K36 + 600 —— 760',
                endMark: 'K36 + 600 —— 760',
            },
            {
                Lng: 108.486533,
                lat: 28.489799,
                title: '杨家堡隧道',
                startMark: 'Z5K36 + 750 —— Z5K39 + 025',
                endMark: 'K36 + 760 —— K39 + 040',
            },
            {
                Lng: 108.486662,
                lat: 28.48829,
                title: '郭家大桥',
                startMark: 'Z5K39 + 221',
                endMark: 'K39 + 185',
            },
            {
                Lng: 108.488014,
                lat: 28.487309,
                title: '郭家隧道',
                startMark: 'Z5K39 + 449 —— Z5K40 + 044',
                endMark: 'K39 + 185 —— K40 + 085',
            }
        ],
        dt5Data: [
            {
                Lng: 108.490581,
                lat: 28.505336,
                title: '李家土隧道',
                startMark: 'Z4K30 + 958 —— Z4K32 + 246',
                endMark: 'K30 + 961 —— K32 + 248',
            },
            {
                Lng: 108.49071,
                lat: 28.503658,
                title: 'π路基',
                startMark: 'K32 + 248 —— K33 + 730',
                endMark: 'K32 + 248 —— K33 + 730',
            },
            {
                Lng: 108.490474,
                lat: 28.502206,
                title: '共和大桥右线1#大桥',
                startMark: 'K33 + 795',
                endMark: 'K33 + 795',
            },
            {
                Lng: 108.489551,
                lat: 28.500829,
                title: '共和大桥左线',
                startMark: 'Z5K34 + 073',
                endMark: 'Z5K34 + 073',
            },
            {
                Lng: 108.48869,
                lat: 28.499511,
                title: '共和大桥右线2#大桥',
                startMark: 'K34 + 188',
                endMark: 'K34 + 188',
            },
            {
                Lng: 108.488164,
                lat: 28.498182,
                title: '共和隧道',
                startMark: 'Z5K34 + 428 —— Z5K35 + 004',
                endMark: 'K34 + 478 —— K35 + 021',
            },
            {
                Lng: 108.487993,
                lat: 28.497276,
                title: '白杨垭1#大桥',
                startMark: 'Z5K34 + 428 —— Z5K35 + 004',
                endMark: 'K35 + 287',
            },
            {
                Lng: 108.487735,
                lat: 28.496051,
                title: '白杨垭中桥',
                startMark: 'Z5K35 + 695',
                endMark: 'K35 + 687',
            },
            {
                Lng: 108.48707,
                lat: 28.494353,
                title: '白杨垭2#大桥',
                startMark: 'Z5K35 + 962',
                endMark: 'K36 + 021.864',
            },
            {
                Lng: 108.486855,
                lat: 28.492882,
                title: '杨家堡大桥',
                startMark: 'Z5K36 + 477',
                endMark: 'K36 + 486',
            },
            {
                Lng: 108.486705,
                lat: 28.491666,
                title: 'π路基',
                startMark: 'K36 + 600 —— 760',
                endMark: 'K36 + 600 —— 760',
            },
            {
                Lng: 108.486533,
                lat: 28.489799,
                title: '杨家堡隧道',
                startMark: 'Z5K36 + 750 —— Z5K39 + 025',
                endMark: 'K36 + 760 —— K39 + 040',
            },
            {
                Lng: 108.486662,
                lat: 28.48829,
                title: '郭家大桥',
                startMark: 'Z5K39 + 221',
                endMark: 'K39 + 185',
            },
            {
                Lng: 108.488014,
                lat: 28.487309,
                title: '郭家隧道',
                startMark: 'Z5K39 + 449 —— Z5K40 + 044',
                endMark: 'K39 + 185 —— K40 + 085',
            }
        ],

        weather: [
            //气象数据
            {
                label: "气温",
                value: "-",
                unit: "°C"
            },
            {
                label: "湿度",
                value: "-",
                unit: "%"
            },
            {
                label: "噪音",
                value: "-",
                unit: "dB"
            },
            {
                label: "pm2.5",
                value: "-",
                unit: "μg/m3"
            },
            {
                label: "pm10",
                value: "-",
                unit: "μg/m3"
            },
            {
                label: "风速",
                value: "-",
                unit: ""
            }
        ],

    }
    getNowTime = () => {
        this.setState({
            nowTime: moment().format('YYYY年MM月DD日'),
            nowTimes: moment().format('HH点mm分ss秒')
        })
    }
    componentDidMount() {
        const { myFetch } = this.props;
        myFetch("getZxHwZhProjectEssentialInformationList", {}).then(({ success, data, message }) => {
            if (success) {
                this.setState({
                    data: data,
                    key: data[0].departmentId
                })
            } else {
                Msg.error(message);
            }
        })
        //获取气象
        myFetch("pcGetHomePageWeatherConditions", {}).then(
            ({ data, success, message }) => {
                if (success) {
                    const {
                        pm25,
                        pm10,
                        winddirection,
                        humidity,
                        temperature,
                        windspeed,
                        noise
                    } = data;
                    //处理数据data
                    this.setState({
                        loading: false,
                        weather: [
                            //气象数据
                            {
                                label: "气温",
                                value: temperature,
                                unit: "°C"
                            },
                            {
                                label: "湿度",
                                value: humidity,
                                unit: "%"
                            },
                            {
                                label: "噪音",
                                value: noise,
                                unit: "dB"
                            },
                            {
                                label: "PM2.5",
                                value: pm25,
                                unit: "μg/m3"
                            },
                            {
                                label: "PM10",
                                value: pm10,
                                unit: "μg/m3"
                            },
                            {
                                label: "风速",
                                value: winddirection ? `${winddirection} ${windspeed}m/s` : '--',
                                unit: ""
                            }
                        ]
                    });
                } else {
                    Msg.error(message);
                }
            }
        );
        clearInterval(window.topTime);
        window.topTime = setInterval(this.getNowTime, 1000);
        this.setLayerStyle('in');
    }
    disconnect = () => {
        const { connectScoket } = this.state;
        if (connectScoket) {
            this.stompClient.disconnect();
        }
    }
    componentWillUnmount() {
        clearInterval(window.topTime);
        this.setLayerStyle('out');
        this.disconnect();
    }
    //改变框架的样式
    setLayerStyle = (type = 'in') => {
        if (type === 'in') {//进入页面时
            let _conDom = document.getElementsByClassName('ant-layout');
            let _footerDom = document.getElementsByClassName('ant-layout-footer');
            let _breadcrumbDom = document.getElementsByClassName('ant-breadcrumb');
            let _alc = document.getElementsByClassName('ant-layout-content');
            if (_conDom && _conDom[2]) {
                _conDom[2].style.padding = 0;
                _conDom[2].style.background = 'rgb(0, 21, 43)';
            }
            if (_breadcrumbDom && _breadcrumbDom[0]) {
                _breadcrumbDom[0].style.display = 'none'
            }
            if (_footerDom && _footerDom[0]) {
                _footerDom[0].style.display = 'none'
            }
            if (_alc && _alc[0]) {
                _alc[0].style.background = '';
                _alc[0].style.padding = '0';
            }
        } else {//离开页面
            let _conDom = document.getElementsByClassName('.ant-layout');
            let _breadcrumbDom = document.getElementsByClassName('.ant-breadcrumb');
            let _footerDom = document.getElementsByClassName('ant-layout-footer');
            let _alc = document.getElementsByClassName('ant-layout-content');
            if (_conDom && _conDom[2]) {
                _conDom[2].style.padding = '0 12px';
                _conDom[2].style.background = '#f0f2f5';
            }
            if (_breadcrumbDom && _breadcrumbDom[0]) {
                _breadcrumbDom[0].style.display = 'block'
            }
            if (_footerDom && _footerDom[0]) {
                _footerDom[0].style.display = 'none'
            }
            if (_alc && _alc[0]) {
                _alc[0].style.background = 'rgb(255, 255, 255)';
                _alc[0].style.padding = '12';
            }
        }

    }
    handleCancel = () => {
        this.setState({ visible: false });
    }
    handleMenuClick = (e) => {
        this.setState({ key: e.key, flag: true });
        window.refreshs(e.key);
    }
    getOption = () => {
        var geoCoordMap = {
            '沿河土家族自治县': [108.510396, 28.571243],
            '印江土家族苗族自治县': [108.41221, 27.999739],
            '松桃苗族自治县': [109.206524, 28.160931],
        };
        var BJData = [
            [{ name: '沿河土家族自治县' }, { name: '沿河土家族自治县', value: 100 }],
            [{ name: '沿河土家族自治县' }, { name: '印江土家族苗族自治县', value: 100 }],
            [{ name: '印江土家族苗族自治县' }, { name: '松桃苗族自治县', value: 100 }],
        ];
        var convertData = function (data) {
            var res = [];
            for (var i = 0; i < data.length; i++) {
                var dataItem = data[i];
                var fromCoord = geoCoordMap[dataItem[0].name];
                var toCoord = geoCoordMap[dataItem[1].name];
                if (fromCoord && toCoord) {
                    res.push({
                        fromName: dataItem[0].name,
                        toName: dataItem[1].name,
                        coords: [fromCoord, toCoord]
                    });
                }
            }
            return res;
        };

        var color = ['#f49100', '#ffa022', '#10cf9b'];
        var series = [];
        [['贵州', BJData]].forEach(function (item, i) {
            series.push({
                name: item[0],
                type: 'lines',
                zlevel: 1,
                effect: {
                    show: true,
                    period: 6,
                    trailLength: 0.7,
                    color: '#fff',
                    symbolSize: 3
                },
                lineStyle: {
                    normal: {
                        color: color[i],
                        width: 0,
                        curveness: 0.2
                    }
                },
                data: convertData(item[1])
            },
                {
                    name: item[0] + ' Top10',
                    type: 'lines',
                    zlevel: 2,
                    effect: {
                        show: true,
                        period: 6,
                        trailLength: 0,
                        //symbol: planePath,
                        symbolSize: 5
                    },
                    lineStyle: {
                        normal: {
                            color: color[i],
                            width: 1,
                            opacity: 0.4,
                            curveness: 0.2
                        }
                    },
                    data: convertData(item[1])
                },
                {
                    name: item[0],
                    type: 'effectScatter',
                    coordinateSystem: 'geo',
                    zlevel: 2,
                    rippleEffect: {
                        brushType: 'stroke'
                    },
                    label: {
                        normal: {
                            show: true,
                            position: 'right',
                            formatter: '{b}'
                        }
                    },
                    symbolSize: function (val) {
                        return val[2] / 8;
                    },
                    itemStyle: {
                        normal: {
                            color: color[i]
                        }
                    },
                    data: item[1].map(function (dataItem) {
                        return {
                            name: dataItem[1].name,
                            value: geoCoordMap[dataItem[1].name].concat([dataItem[1].value])
                        };
                    })
                });
        });

        var option = {
            geo: {
                map: 'china',
                label: {
                    normal: {
                        show: false,
                        textStyle: {
                            color: '#fff'
                        }
                    },
                    emphasis: {
                        textStyle: {
                            color: 'white',
                            fontSize: 14
                        }
                    }
                },
                roam: true,
                itemStyle: {
                    normal: {
                        borderColor: 'rgba(147, 235, 248, 1)',
                        borderWidth: 1,
                        areaColor: {
                            type: 'radial',
                            x: 0.5,
                            y: 0.5,
                            r: 0.8,
                            colorStops: [{
                                offset: 0,
                                color: 'rgba(175,238,238, 0)' // 0% 处的颜色
                            }, {
                                offset: 1,
                                color: 'rgba(	47,79,79, .2)' // 100% 处的颜色
                            }],
                            globalCoord: false // 缺省为 false
                        },
                        shadowColor: 'rgba(128, 217, 248, 1)',
                        shadowOffsetX: -2,
                        shadowOffsetY: 2,
                        shadowBlur: 10
                    },
                    emphasis: {
                        areaColor: '#389BB7',
                        borderWidth: 0
                    }
                },
                regions: [{                                  //在地图中对特定的区域配置样式。
                    name: '贵州',
                    itemStyle: {
                        normal: {
                            areaColor: '#389BB7',
                        },
                        emphasis: {
                            areaColor: '#389BB7'
                        }
                    }
                }],
            },
            series: series
        };
        return option
    }
    onChartClick = (e) => {
        if (e.name == '贵州') {
            this.setState({
                flag: true
            })
        }
    }
    //map对象问地址实例化的对象 
    //AMap对象为高德地图提供的全局对象
    mapOnload = ({ map, AMap }) => {
        this.setState({ map, AMap });
        let styleName = "amap://styles/darkblue";
        map.setMapStyle(styleName);
        if (this.state.key === 'zx00') {
            this.state.dtData.map((item, index) => {
                let marker = new AMap.Marker({
                    icon: 'https://webapi.amap.com/theme/v1.3/markers/n/mark_b' + (index + 1) + '.png',
                    position: new AMap.LngLat(item.Lng, item.lat),
                    map: map
                });
                if (index < 5) {
                    if (index % 2 === 0) {
                        marker.setLabel({
                            offset: new AMap.Pixel(-10, 0),
                            content: '<div onclick="refreshs(\'' + item.departmentId + '\')"><div style="color: #f49100;">' + item.title + '</div><div>' + item.stakeMark + '</div><div>全长:' + item.overallLength + '</div><div>班组:' + item.team + '</div><div>人员:' + item.personnel + '</div></div>', //设置文本标注内容
                            direction: 'left' //设置文本标注方位
                        });
                    } else {
                        marker.setLabel({
                            offset: new AMap.Pixel(10, 0),
                            content: '<div onclick="refreshs(\'' + item.departmentId + '\')"><div style="color: #f49100;">' + item.title + '</div><div>' + item.stakeMark + '</div><div>全长:' + item.overallLength + '</div><div>班组:' + item.team + '</div><div>人员:' + item.personnel + '</div></div>', //设置文本标注内容
                            direction: 'right' //设置文本标注方位
                        });
                    }
                } else {
                    if (index % 2 === 0) {
                        marker.setLabel({
                            offset: new AMap.Pixel(10, 0),
                            content: '<div onclick="refreshs(\'' + item.departmentId + '\')"><div style="color: #f49100;">' + item.title + '</div><div>' + item.stakeMark + '</div><div>全长:' + item.overallLength + '</div><div>班组:' + item.team + '</div><div>人员:' + item.personnel + '</div></div>', //设置文本标注内容
                            direction: 'top' //设置文本标注方位
                        });
                    } else {
                        marker.setLabel({
                            offset: new AMap.Pixel(10, 0),
                            content: '<div onclick="refreshs(\'' + item.departmentId + '\')"><div style="color: #f49100;">' + item.title + '</div><div>' + item.stakeMark + '</div><div>全长:' + item.overallLength + '</div><div>班组:' + item.team + '</div><div>人员:' + item.personnel + '</div></div>', //设置文本标注内容
                            direction: 'bottom' //设置文本标注方位
                        });
                    }
                }
                map.add(marker);
                marker.setAnimation('AMAP_ANIMATION_BOUNCE');
            })
        } else if (this.state.key === 'zx01') {
            this.state.dt1Data.map((item, index) => {
                let marker = new AMap.Marker({
                    position: new AMap.LngLat(item.Lng, item.lat),
                    map: map
                });
                if (index % 2 === 0) {
                    marker.setLabel({
                        offset: new AMap.Pixel(-10, 0),
                        content: '<div><div style="color: #f49100;">' + item.title + '</div><div>' + item.startMark + '</div><div>' + item.endMark + '</div></div>', //设置文本标注内容
                        direction: 'left' //设置文本标注方位
                    });
                } else {
                    marker.setLabel({
                        offset: new AMap.Pixel(10, 0),
                        content: '<div><div style="color: #f49100;">' + item.title + '</div><div>' + item.startMark + '</div><div>' + item.endMark + '</div></div>', //设置文本标注内容
                        direction: 'right' //设置文本标注方位
                    });
                }
                map.add(marker);
                // marker.setAnimation('AMAP_ANIMATION_BOUNCE');
            })
        } else if (this.state.key === 'zx02') {
            this.state.dt2Data.map((item, index) => {
                let marker = new AMap.Marker({
                    position: new AMap.LngLat(item.Lng, item.lat),
                    map: map
                });
                if (index % 2 === 0) {
                    marker.setLabel({
                        offset: new AMap.Pixel(-10, 0),
                        content: '<div><div style="color: #f49100;">' + item.title + '</div><div>' + item.startMark + '</div><div>' + item.endMark + '</div></div>', //设置文本标注内容
                        direction: 'left' //设置文本标注方位
                    });
                } else {
                    marker.setLabel({
                        offset: new AMap.Pixel(10, 0),
                        content: '<div><div style="color: #f49100;">' + item.title + '</div><div>' + item.startMark + '</div><div>' + item.endMark + '</div></div>', //设置文本标注内容
                        direction: 'right' //设置文本标注方位
                    });
                }
                map.add(marker);
                // marker.setAnimation('AMAP_ANIMATION_BOUNCE');
            })
        } else if (this.state.key === 'zx03') {
            this.state.dt3Data.map((item, index) => {
                let marker = new AMap.Marker({
                    position: new AMap.LngLat(item.Lng, item.lat),
                    map: map
                });
                if (index % 2 === 0) {
                    marker.setLabel({
                        offset: new AMap.Pixel(-10, 0),
                        content: '<div><div style="color: #f49100;">' + item.title + '</div><div>' + item.startMark + '</div><div>' + item.endMark + '</div></div>', //设置文本标注内容
                        direction: 'left' //设置文本标注方位
                    });
                } else {
                    marker.setLabel({
                        offset: new AMap.Pixel(10, 0),
                        content: '<div><div style="color: #f49100;">' + item.title + '</div><div>' + item.startMark + '</div><div>' + item.endMark + '</div></div>', //设置文本标注内容
                        direction: 'right' //设置文本标注方位
                    });
                }
                map.add(marker);
                // marker.setAnimation('AMAP_ANIMATION_BOUNCE');
            })
        } else if (this.state.key === 'zx04') {
            this.state.dt4Data.map((item, index) => {
                let marker = new AMap.Marker({
                    position: new AMap.LngLat(item.Lng, item.lat),
                    map: map
                });
                if (index % 2 === 0) {
                    marker.setLabel({
                        offset: new AMap.Pixel(-10, 0),
                        content: '<div><div style="color: #f49100;">' + item.title + '</div><div>' + item.startMark + '</div><div>' + item.endMark + '</div></div>', //设置文本标注内容
                        direction: 'left' //设置文本标注方位
                    });
                } else {
                    marker.setLabel({
                        offset: new AMap.Pixel(10, 0),
                        content: '<div><div style="color: #f49100;">' + item.title + '</div><div>' + item.startMark + '</div><div>' + item.endMark + '</div></div>', //设置文本标注内容
                        direction: 'right' //设置文本标注方位
                    });
                }
                map.add(marker);
                // marker.setAnimation('AMAP_ANIMATION_BOUNCE');
            })
        } else if (this.state.key !== 'zx00') {
            this.state.dt5Data.map((item, index) => {
                let marker = new AMap.Marker({
                    position: new AMap.LngLat(item.Lng, item.lat),
                    map: map
                });
                if (index % 2 === 0) {
                    marker.setLabel({
                        offset: new AMap.Pixel(-10, 0),
                        content: '<div><div style="color: #f49100;">' + item.title + '</div><div>' + item.startMark + '</div><div>' + item.endMark + '</div></div>', //设置文本标注内容
                        direction: 'left' //设置文本标注方位
                    });
                } else {
                    marker.setLabel({
                        offset: new AMap.Pixel(10, 0),
                        content: '<div><div style="color: #f49100;">' + item.title + '</div><div>' + item.startMark + '</div><div>' + item.endMark + '</div></div>', //设置文本标注内容
                        direction: 'right' //设置文本标注方位
                    });
                }
                map.add(marker);
                // marker.setAnimation('AMAP_ANIMATION_BOUNCE');
            })
        }
    }
    onRef = (ref) => {
        this.QnnMap = ref
    }
    render() {
        const { nowTime, logo, nowTimes, activeKey, visible, key, weather = [], flag, initMapConfig } = this.state;
        const strs = '周' + '日一二三四五六'.charAt(new Date().getDay());
        const menu = (
            <Menu onClick={this.handleMenuClick}>
                {
                    this.state.data && this.state.data.length ? this.state.data.map((item, index) => {
                        return <Menu.Item key={item.departmentId} style={key == item.departmentId ? { color: '#f49100' } : null}>{item.departmentName}</Menu.Item>
                    }) : null
                }
            </Menu>
        );
        window.refreshs = (departmentId) => {
            this.setState({
                key: departmentId
            }, () => {
                if (this.state.key === 'zx00') {
                    this.setState({
                        initMapConfig: {
                            resizeEnable: true,
                            rotateEnable: true,
                            pitchEnable: true,
                            zoom: 10,
                            pitch: 55,
                            // rotation: -15,
                            // viewMode: '3D',//开启3D视图,默认为关闭
                            buildingAnimation: true,//楼块出现是否带动画
                            expandZoomRange: true,
                            zooms: [3, 18],
                            center: [108.772149, 28.264934],
                        }
                    }, () => {
                        this.QnnMap.initMap();
                    })
                } else if (this.state.key === 'zx01') {
                    this.setState({
                        initMapConfig: {
                            resizeEnable: true,
                            rotateEnable: true,
                            pitchEnable: true,
                            zoom: 15,
                            pitch: 55,
                            // rotation: -90,
                            // viewMode: '3D',//开启3D视图,默认为关闭
                            buildingAnimation: true,//楼块出现是否带动画
                            expandZoomRange: true,
                            zooms: [3, 18],
                            center: [108.508815, 28.561885],
                        }
                    }, () => {
                        this.QnnMap.initMap();
                    })
                } else if (this.state.key === 'zx02') {
                    this.setState({
                        initMapConfig: {
                            resizeEnable: true,
                            rotateEnable: true,
                            pitchEnable: true,
                            zoom: 15,
                            pitch: 55,
                            // rotation: -90,
                            // viewMode: '3D',//开启3D视图,默认为关闭
                            buildingAnimation: true,//楼块出现是否带动画
                            expandZoomRange: true,
                            zooms: [3, 18],
                            center: [108.48972, 28.534947],
                        }
                    }, () => {
                        this.QnnMap.initMap();
                    })
                } else if (this.state.key === 'zx03') {
                    this.setState({
                        initMapConfig: {
                            resizeEnable: true,
                            rotateEnable: true,
                            pitchEnable: true,
                            zoom: 15,
                            pitch: 55,
                            // rotation: -90,
                            // viewMode: '3D',//开启3D视图,默认为关闭
                            buildingAnimation: true,//楼块出现是否带动画
                            expandZoomRange: true,
                            zooms: [3, 18],
                            center: [108.490879, 28.515981],
                        }
                    }, () => {
                        this.QnnMap.initMap();
                    })
                } else if (this.state.key === 'zx04') {
                    this.setState({
                        initMapConfig: {
                            resizeEnable: true,
                            rotateEnable: true,
                            pitchEnable: true,
                            zoom: 15,
                            pitch: 55,
                            // rotation: -90,
                            // viewMode: '3D',//开启3D视图,默认为关闭
                            buildingAnimation: true,//楼块出现是否带动画
                            expandZoomRange: true,
                            zooms: [3, 18],
                            center: [108.487706, 28.496096],
                        }
                    }, () => {
                        this.QnnMap.initMap();
                    })
                } else if (this.state.key !== 'zx00') {
                    this.setState({
                        initMapConfig: {
                            resizeEnable: true,
                            rotateEnable: true,
                            pitchEnable: true,
                            zoom: 15,
                            pitch: 55,
                            // rotation: -90,
                            // viewMode: '3D',//开启3D视图,默认为关闭
                            buildingAnimation: true,//楼块出现是否带动画
                            expandZoomRange: true,
                            zooms: [3, 18],
                            center: [108.487706, 28.496096],
                        }
                    }, () => {
                        this.QnnMap.initMap();
                    })
                }
            })
        }
        return (
            <div className={s.root} style={sectionsStyle} id="fullScreen">
                <div className={s.top}>
                    <div className={s.item}>
                        <div className={s.left}>
                            <div style={{ width: '100%', textAlign: "center" }}>
                                <div className={s.city}>{nowTime} {strs} {nowTimes} </div>
                                <div className={s.weather}>
                                    {weather.map(({ label, value, unit }, index) => {
                                        return (
                                            <div key={index} className={s.weatherItem}>
                                                <span className={s.headerLabel}>{label}：</span>
                                                <span className={s.headerValue}>
                                                    {value}
                                                    {value ? unit : '--'}
                                                </span>
                                            </div>
                                        );
                                    })}
                                </div>
                            </div>
                        </div>
                    </div>
                    <div className={s.logoCon}><span className={s.logo}><Dropdown overlayClassName='overlay' placement={'bottomCenter'} overlay={menu} trigger={['click']}><img style={{ cursor: 'pointer' }} src={logo} alt="logo" /></Dropdown>贵州中交沿印松高速公路项目大数据云平台</span></div>
                    <div className={s.header}>
                        <span style={activeKey == '1' ? { fontSize: '14px', color: '#37adcb', cursor: 'pointer' } : { fontSize: '14px', color: 'white', cursor: 'pointer' }} onClick={() => {
                            this.setState({ activeKey: '1' });
                        }}>总体态势</span>
                        <span style={activeKey == '2' ? { paddingLeft: '3%', fontSize: '14px', color: '#37adcb', cursor: 'pointer' } : { paddingLeft: '3%', fontSize: '14px', color: 'white', cursor: 'pointer' }} onClick={() => {
                            this.setState({ activeKey: '2' })
                        }}>进度管理</span>
                        <span style={activeKey == '3' ? { paddingLeft: '3%', fontSize: '14px', color: '#37adcb', cursor: 'pointer' } : { paddingLeft: '3%', fontSize: '14px', color: 'white', cursor: 'pointer' }} onClick={() => {
                            this.setState({ activeKey: '3' })
                        }}>安全管理</span>
                        <span style={activeKey == '4' ? { paddingLeft: '3%', fontSize: '14px', color: '#37adcb', cursor: 'pointer' } : { paddingLeft: '3%', fontSize: '14px', color: 'white', cursor: 'pointer' }} onClick={() => {
                            this.setState({ activeKey: '4' })
                        }}>质量管理</span>
                        <span style={{ paddingLeft: '3%', fontSize: '14px', color: 'white', cursor: 'pointer' }} onClick={() => {
                            this.setState({ visible: true })
                        }}>数据中心</span>
                        <span style={{ paddingLeft: '3%', fontSize: '14px', color: 'white', cursor: 'pointer' }} onClick={() => {

                        }}>数据管理</span>
                    </div>
                </div>
                <div className={s.HOME_right}>
                    <div className={s.block1}>
                        <div className={s.troot}>
                            {flag ? <div style={{ width: '100%', height: '100%', position: 'relative' }}>
                                <QnnMap
                                    //地图地址
                                    src={`//webapi.amap.com/maps?v=1.4.14&key=8250dc9402ff7d7f96bd75ac6835ae51&plugin=AMap.ControlBar,Map3D`}
                                    // kml数据文件  可以是相对位置或者绝对位置
                                    // kmlSrc={require('./data.kml')}
                                    initMapConfig={initMapConfig}
                                    onRef={this.onRef}
                                    onload={this.mapOnload}
                                />
                                <div style={{ position: 'absolute', bottom: '10px', right: '21%', zIndex: '99999999' }}>
                                    <Button type="primary" onClick={(e) => {
                                        e.stopPropagation();
                                        if (key === 'zx00') {
                                            this.setState({ flag: false })
                                        } else {
                                            this.setState({
                                                key: 'zx00',
                                                initMapConfig: {
                                                    resizeEnable: true,
                                                    rotateEnable: true,
                                                    pitchEnable: true,
                                                    zoom: 10,
                                                    pitch: 55,
                                                    // rotation: -15,
                                                    // viewMode: '3D',//开启3D视图,默认为关闭
                                                    buildingAnimation: true,//楼块出现是否带动画

                                                    expandZoomRange: true,
                                                    zooms: [3, 18],
                                                    center: [108.772149, 28.264934],
                                                }
                                            }, () => {
                                                this.QnnMap.initMap();
                                            })
                                        }
                                    }}>返回</Button>
                                </div>
                            </div> : null}
                            {
                                !flag ? <ReactEcharts
                                    style={{ height: '100%' }}
                                    option={this.getOption()}
                                    notMerge={true}
                                    lazyUpdate={true}
                                    theme={"theme_name"}
                                    onEvents={{
                                        'click': this.onChartClick,
                                    }}
                                /> : null
                            }
                        </div>
                    </div>
                </div>
                {
                    this.state.key ?
                        <Tabs activeKey={activeKey}>
                            <TabPane key="1" tab="">
                                {
                                    activeKey == '1' ? (key == (this.state.data && this.state.data.length ? this.state.data[0].departmentId : '') ? <div className={s.ZT_con}>
                                        <div className={s.ZT_left}>
                                            <div className={s.leftTop}>
                                                <ZTXM {...this.props} departmentId={this.state.key} />
                                            </div>
                                        </div>
                                        <div className={s.ZT_right}>
                                            <div className={s.block1}></div>
                                        </div>
                                        <div className={s.ZT_lefts} style={{ marginLeft: '8px', marginRight: '0' }}>
                                            <div className={s.leftTops}>
                                                <ZTAQTJ {...this.props} departmentId={this.state.key} />
                                            </div>
                                            <div className={s.leftBottoms}>
                                                <ZTXXBB  {...this.props} departmentId={this.state.key} />
                                            </div>
                                        </div>
                                    </div> : <div className={s.ZTFB_con}>
                                            <div className={s.ZTFB_left}>
                                                <div className={s.leftTop}>
                                                    <ZTQK {...this.props} departmentId={this.state.key} />
                                                </div>
                                                <div className={s.leftBottom}>
                                                    <ZTFZ {...this.props} departmentId={this.state.key} />
                                                </div>
                                            </div>
                                            <div className={s.ZTFB_right}>
                                                <div className={s.block1}></div>
                                            </div>
                                            <div className={s.ZTFB_left} style={{ marginLeft: '8px', marginRight: '0' }}>
                                                <div className={s.leftTops}>
                                                    <ZTAQTJ {...this.props} departmentId={this.state.key} />
                                                </div>
                                                <div className={s.leftBottoms}>
                                                    <ZTXXBB  {...this.props} departmentId={this.state.key} />
                                                </div>
                                            </div>
                                        </div>) : null
                                }
                            </TabPane>
                            <TabPane key="2" tab="">
                                {
                                    activeKey == '2' ? (key == (this.state.data && this.state.data.length ? this.state.data[0].departmentId : '') ? <div className={s.JD_con}>
                                        <div className={s.JD_left}>
                                            <div className={s.leftTop}>
                                                <JDZT {...this.props} departmentId={this.state.key} />
                                            </div>
                                            <div className={s.leftBottom}>
                                                <JDSJ {...this.props} departmentId={this.state.key} />
                                            </div>
                                        </div>
                                        <div className={s.JD_right}>
                                            <div className={s.block1}></div>
                                        </div>
                                        <div className={s.JD_lefts} style={{ marginLeft: '8px', marginRight: '0' }}>
                                            <div className={s.leftBottom}>
                                                <JDGT  {...this.props} departmentId={this.state.key} />
                                            </div>
                                            <div className={s.leftTop}>
                                                <JDST  {...this.props} departmentId={this.state.key} />
                                            </div>
                                            <div className={s.leftBottom}>
                                                <JDLJZ  {...this.props} departmentId={this.state.key} />
                                            </div>
                                        </div>
                                    </div> : <div className={s.JDFB_con}>
                                            <div className={s.JDFB_left}>
                                                <JDJD {...this.props} departmentId={this.state.key} />
                                            </div>
                                            <div className={s.JDFB_right}>
                                                <div className={s.block1}></div>
                                            </div>
                                            <div className={s.JDFB_lefts} style={{ marginLeft: '8px', marginRight: '0' }}>
                                                <div className={s.leftBottom}>
                                                    <JDTJ {...this.props} departmentId={this.state.key} />
                                                </div>
                                                <div className={s.leftTop}>
                                                    <JDLC {...this.props} departmentId={this.state.key} />
                                                </div>
                                                <div className={s.leftBottom}>
                                                    <JDZJ {...this.props} departmentId={this.state.key} />
                                                </div>
                                            </div>
                                        </div>) : null
                                }
                            </TabPane>
                            <TabPane key="3" tab="">
                                {
                                    activeKey == '3' ? (key == (this.state.data && this.state.data.length ? this.state.data[0].departmentId : '') ? <div className={s.AQ_con}>
                                        <div className={s.AQ_left}>
                                            <div className={s.leftTop}>
                                                <AQRY  {...this.props} departmentId={this.state.key} />
                                            </div>
                                            <div className={s.leftTop1}>
                                                <AQTZ  {...this.props} departmentId={this.state.key} />
                                            </div>
                                            <div className={s.leftBottom}>
                                                <AQYH  {...this.props} departmentId={this.state.key} />
                                            </div>
                                        </div>
                                        <div className={s.AQ_right}>
                                            <div className={s.block1}></div>
                                        </div>
                                        <div className={s.AQ_left} style={{ marginLeft: '8px', marginRight: '0' }}>
                                            <div className={s.leftTops}>
                                                <AQJK  {...this.props} departmentId={this.state.key} />
                                            </div>
                                            <div className={s.leftBottoms}>
                                                <AQAQ  {...this.props} departmentId={this.state.key} />
                                            </div>
                                        </div>
                                    </div> : <div className={s.AQ_con}>
                                            <div className={s.AQ_left}>
                                                <div className={s.leftTop}>
                                                    <AQRY  {...this.props} departmentId={this.state.key} />
                                                </div>
                                                <div className={s.leftTop1}>
                                                    <AQTZ  {...this.props} departmentId={this.state.key} />
                                                </div>
                                                <div className={s.leftBottom}>
                                                    <AQYH  {...this.props} departmentId={this.state.key} />
                                                </div>
                                            </div>
                                            <div className={s.AQ_right}>
                                                <div className={s.block1}></div>
                                            </div>
                                            <div className={s.AQ_left} style={{ marginLeft: '8px', marginRight: '0' }}>
                                                <div className={s.leftTops}>
                                                    <AQXC  {...this.props} departmentId={this.state.key} />
                                                </div>
                                                <div className={s.leftBottoms}>
                                                    <AQAQ  {...this.props} departmentId={this.state.key} />
                                                </div>
                                            </div>
                                        </div>) : null
                                }
                            </TabPane>
                            <TabPane key="4" tab="">
                                {
                                    activeKey == '4' ? (key == (this.state.data && this.state.data.length ? this.state.data[0].departmentId : '') ? <div className={s.JD_con}>
                                        <div className={s.JD_left}>
                                            <div className={s.leftTop}>
                                                <ZLZL {...this.props} departmentId={this.state.key} />
                                            </div>
                                            <div className={s.leftBottom}>
                                                <ZLYB {...this.props} departmentId={this.state.key} />
                                            </div>
                                        </div>
                                        <div className={s.JD_right}>
                                            <div className={s.block1}></div>
                                        </div>
                                        <div className={s.JD_lefts} style={{ marginLeft: '8px', marginRight: '0' }}>
                                            <div className={s.leftBottom}>
                                                <ZLYY {...this.props} departmentId={this.state.key} />
                                            </div>
                                            <div className={s.leftTop}>
                                                <ZLZN {...this.props} departmentId={this.state.key} />
                                            </div>
                                            <div className={s.leftBottom}>
                                                <ZLBHZ {...this.props} departmentId={this.state.key} />
                                            </div>
                                        </div>
                                    </div> : <div className={s.JD_con}>
                                            <div className={s.JD_left}>
                                                <div className={s.leftTops}>
                                                    <ZLFBZL {...this.props} departmentId={this.state.key} />
                                                </div>
                                                <div className={s.leftBottoms}>
                                                    <ZLFBJC {...this.props} departmentId={this.state.key} />
                                                </div>
                                                <div className={s.leftTops}>
                                                    <ZLFBYB {...this.props} departmentId={this.state.key} />
                                                </div>
                                            </div>
                                            <div className={s.JD_right}>
                                                <div className={s.block1}></div>
                                            </div>
                                            <div className={s.JD_lefts} style={{ marginLeft: '8px', marginRight: '0' }}>
                                                <div className={s.leftBottom}>
                                                    <ZLYY {...this.props} departmentId={this.state.key} />
                                                </div>
                                                <div className={s.leftTop}>
                                                    <ZLZN {...this.props} departmentId={this.state.key} />
                                                </div>
                                                <div className={s.leftBottom}>
                                                    <ZLBHZ {...this.props} departmentId={this.state.key} />
                                                </div>
                                            </div>
                                        </div>) : null
                                }
                            </TabPane>
                        </Tabs>
                        : null
                }
                <div>
                    <Modal
                        width={'99%'}
                        style={{ paddingBottom: '0', top: '0' }}
                        title="数据中心"
                        visible={visible}
                        footer={null}
                        onCancel={this.handleCancel}
                        bodyStyle={{ padding: '10px', background: 'rgb(13, 20, 48)', height: '95vh' }}
                        destroyOnClose
                        wrapClassName={'modals'}
                    >
                        <iframe scrolling="auto" marginHeight="0" marginWidth="0" width='100%' height='100%' frameBorder={0} src='http://47.96.150.231/pcByFullScreen#/pcByFullScreen'></iframe>
                    </Modal>
                </div>
            </div>
        )
    }
}
export default index;