import React, { Component } from "react";
import { Spin, message as Msg } from "antd";
import s from "./style.less";
import moment from "moment";
const logo = require("../../logo.png");
const fs = require("../../img/fs.png");
const efs = require("../../img/efs.png");
// pcGetHomePageWeatherConditions  获取气象的接口
export default class Header extends Component {
    state = {
        loading: true,
        logo: logo,
        hello: window.configs.appInfo.title,
        helloSub: "",

        weather: [
            //气象数据
            {
                label: "气温",
                value: "",
                unit: "°C"
            },
            {
                label: "湿度",
                value: "",
                unit: "%"
            },
            // {
            //     label: "噪音",
            //     value: "",
            //     unit: "dB"
            // },
            {
                label: "pm2.5",
                value: "",
                unit: "μg/m3"
            },
            // {
            //     label: "pm10",
            //     value: "",
            //     unit: "μg/m3"
            // },
            {
                label: "风向",
                value: "",
                unit: ""
            }
        ],
        classify: [
            // {
            //     label: '隐蔽工程',
            //     value: '200'
            // },
            // {
            //     label: '质量巡检',
            //     value: '200'
            // }
        ],
        nowTime: moment().format("YYYY年MM月DD日 HH点mm分ss秒"),
        weatherDetails: ""
    };

    componentDidMount() {
        this.refresh();
        this.isDidMount = true;
        clearInterval(window.topTime);
        window.topTime = setInterval(this.getNowTime, 1000);

        //判断是否需要大屏
        if (window.location.search) {
            if (window.location.search.indexOf("fullScreen=true") !== -1) {
                //需要全屏
                this.setState({
                    fullS: true,
                    notNeedSullSBtn: true //不需要全屏按钮
                });
            }
        }


        //三小时一刷新
        setTimeout(this.refresh, 1000 * 60 * 60 * 3)
    }

    componentWillUnmount() {
        this.isDidMount = false;
        clearInterval(window.topTime);
    }


    refresh = () => {
        const { myFetch } = this.props;

        myFetch("getZjProZcWeather", {}).then(({ data, success, message }) => {

            if (success) {
                //处理数据data
                this.isDidMount && this.setState({
                    loading: false,
                    weather: [
                        //气象数据
                        {
                            label: "气温",
                            value: data["airTemperature"],
                            unit: "°C"
                        },
                        {
                            label: "湿度",
                            value: data["humidity"],
                            unit: "%"
                        },
                        // {
                        //     label: "噪音",
                        //     value: data["noise"],
                        //     unit: "dB"
                        // },
                        {
                            label: "pm2.5",
                            value: data["pm2dian5"],
                            unit: "μg/m3"
                        },
                        // {
                        //     label: "pm10",
                        //     value: data["pm10"],
                        //     unit: "μg/m3"
                        // },
                        {
                            label: "风向",
                            value: data["windDirection"],
                            unit: ""
                        }
                    ]
                });
            } else {
                Msg.error(message);
            }
        });

        // //获取气象
        // myFetch("pcGetHomePageWeatherConditions",{}).then(
        //     ({ data,success,message }) => {
        //         if (success) {
        //             // {
        //             //     pm25:'80',
        //             //     pm10:'24.3',
        //             //     widdirection:'西北', //风向
        //             //     updateTime:'2018-10-11 12:50:30', // 更新时间
        //             //     humidity:'84.0', // 湿度
        //             //     temperature:'19.1',//气温
        //             //     noise:'62.9', //噪音
        //             // }
        //             const {
        //                 pm25,
        //                 pm10,
        //                 winddirection,
        //                 humidity,
        //                 temperature,
        //                 windspeed,
        //                 noise
        //             } = data;
        //             //处理数据data
        //             this.setState({
        //                 loading: false,
        //                 weather: [
        //                     //气象数据
        //                     {
        //                         label: "气温",
        //                         value: temperature,
        //                         unit: "°C"
        //                     },
        //                     {
        //                         label: "湿度",
        //                         value: humidity,
        //                         unit: "%"
        //                     },
        //                     {
        //                         label: "噪音",
        //                         value: noise,
        //                         unit: "dB"
        //                     },
        //                     {
        //                         label: "PM2.5",
        //                         value: pm25,
        //                         unit: "μg/m3"
        //                     },
        //                     {
        //                         label: "PM10",
        //                         value: pm10,
        //                         unit: "μg/m3"
        //                     },
        //                     {
        //                         label: "风速",
        //                         value: winddirection ? `${winddirection} ${windspeed}m/s` : '--',
        //                         unit: ""
        //                     }
        //                 ]
        //             });
        //         } else {
        //             Msg.error(message);
        //         }
        //     }
        // );

        // myFetch("getZxHwWeatherDetails",{ city: "贵阳" }).then(
        //     ({ data,success,message }) => {
        //         if (success) {
        //             //处理数据data
        //             this.isDidMount && this.setState({
        //                 weatherDetails: data.weatherDetails
        //             });
        //         } else {
        //             Msg.error(message);
        //         }
        //     }
        // );
    };

    //全屏
    fullS = () => {
        const divObj = document.getElementsByTagName("body")[0];
        this.setState(
            {
                fullS: true
            },
            () => {
                if (divObj.requestFullscreen) {
                    divObj.requestFullscreen();
                } else if (divObj.msRequestFullscreen) {
                    divObj.msRequestFullscreen();
                } else if (divObj.mozRequestFullScreen) {
                    divObj.mozRequestFullScreen();
                } else if (divObj.webkitRequestFullscreen) {
                    divObj.webkitRequestFullscreen();
                } else {
                    var requestMethod =
                        divObj.requestFullScreen ||
                        divObj.webkitRequestFullScreen ||
                        divObj.mozRequestFullScreen ||
                        divObj.msRequestFullScreen ||
                        divObj.msRequestFullScreen;
                    if (requestMethod) {
                        requestMethod.call(divObj);
                    } else if (typeof window.ActiveXObject !== "undefined") {
                        var wscript = new window.ActiveXObject("WScript.Shell");
                        if (wscript !== null) {
                            wscript.SendKeys("{F11}");
                        }
                    }
                }
            }
        );
    };

    //退出全屏
    exitFullS = () => {
        this.setState(
            {
                fullS: false
            },
            () => {
                if (document.exitFullscreen) {
                    document.exitFullscreen();
                } else if (document.mozCancelFullScreen) {
                    document.mozCancelFullScreen();
                } else if (document.webkitExitFullscreen) {
                    document.webkitExitFullscreen();
                }
            }
        );
    };

    getNowTime = () => {
        this.setState({
            nowTime: moment().format("YYYY年MM月DD日 HH点mm分ss秒")
        });
    };

    render() {
        const {
            fullS,
            classify = [],
            loading,
            notNeedSullSBtn,
            weather = [],
            nowTime,
            logo,
            weatherDetails,
        } = this.state;

        return (
            <Spin spinning={loading}>
                {/* 顶部 */}
                {fullS ? (
                    <div className={s.top}>
                        <div className={s.item}>
                            <span className={s.city}>{weatherDetails}</span>
                        </div>
                        <div className={`${s.item} ${s.logoCon}`}>
                            <span className={s.logo}>
                                <img src={logo} alt="logo" />
                                {window.configs.appInfo.title}
                            </span>
                        </div>
                        <div
                            className={`${s.item} ${s.time}`}
                            style={{ paddingRight: notNeedSullSBtn ? "100px" : "" }}
                        >
                            {nowTime}
                        </div>
                    </div>
                ) : null}

                <div className={s.header}>
                    <div className={s.left}>
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
                    <div className={`${s.time}`}>
                        {!fullS ? nowTime : null}
                    </div>
                    <div className={s.right}>
                        <div className={s.rightCon}>
                            {classify.map(({ label, value, url }, index) => {
                                return (
                                    <div key={index}>
                                        <div className={s.label}>{label}</div>
                                        <div className={s.value}>
                                            {url ? <a href={url}>{value}</a> : <a>{value}</a>}
                                        </div>
                                    </div>
                                );
                            })}
                            {/* 全屏按钮 */}
                            {notNeedSullSBtn ? null : (
                                <div
                                    style={{
                                        border: "0px",
                                        marginRight: "10px",
                                        cursor: "pointer"
                                    }}
                                >
                                    {/* <div onClick={fullS ? this.exitFullS : this.fullS} className={s.value}>{fullS ? <Icon type="shrink" theme="outlined" /> : <Icon type="arrows-alt" theme="outlined" />}</div> */}
                                    <div
                                        onClick={fullS ? this.exitFullS : this.fullS}
                                        className={s.value}
                                    >
                                        {fullS ? <img src={efs} alt="alt" /> : <img src={fs} alt="alt" />}
                                    </div>
                                </div>
                            )}
                        </div>
                    </div>
                </div>
            </Spin>
        );
    }
}