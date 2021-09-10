import React, { lazy, Suspense } from 'react'
// import {
//     Readme,LeftBottomOne,LeftBottomTwo,
//     BodyRightTop,BodyRightBottom,RightOne,RightThree,RightTwo
// } from './components'
import s from './style.less'
import { notification } from 'antd'
import Apih5 from 'qnn-apih5'

const Header = lazy(() => import("./components/Header"));
const BodyLeftTop = lazy(() => import("./components/BodyLeftTop"));
const BodyLeftBottom = lazy(() => import("./components/BodyLeftBottom"));
const Readme = lazy(() => import("./components/Readme"));
const LeftBottomOne = lazy(() => import("./components/LeftBottomOne"));
const LeftBottomTwo = lazy(() => import("./components/LeftBottomTwo"));
const BodyRightTopTwo = lazy(() => import("./components/BodyRightTopTwo"));
const BodyRightBottom = lazy(() => import("./components/BodyRightBottom"));
const RightOne = lazy(() => import("./components/RightOne"));
const RightPengLIng = lazy(() => import("./components/RightPengLIng"));
const RightTwo = lazy(() => import("./components/RightTwo"));
const RightThree = lazy(() => import("./components/RightThree"));
const Info2 = lazy(() => import("./components/Info2"));

class index extends Apih5 {
    state = {
        //顶部数据
        topData: {
            logo: ''
        }
    }
    //生命周期钩子函数(挂载完成后执行)
    componentDidMount() {
        this.setLayerStyle('in');
        // this.connectScoket();
    }

    connectScoket = () => {
        //建立scoket连接  /customendpoint
        var socket = new window.SockJS(window.configs.domain + 'customendpoint');//连接SockJs的endpoint-/customendpoint
        this.stompClient = window.Stomp.over(socket)//使用STOMP子协议的WebSocket客户端
        const _this = this;
        this.stompClient.connect({}, function (frame) {
            _this.setState({
                connectScoket: true
            })
            if (_this.stompClient) {
                _this.stompClient.subscribe("/topic/getResponse", function (response) {
                    const body = JSON.parse(response.body);
                    _this.openNotificationWithIcon({
                        className: s.message,
                        message: '工序审核完成',
                        description: body.responseMessage,
                    })
                });
            }
        }, function () {
            console.log('连接失败')
        });
    }

    disconnect = () => {
        const { connectScoket } = this.state;
        if (connectScoket) {
            this.stompClient.disconnect();
        }
    }

    openNotificationWithIcon = (messageObj = {}, type = 'success') => {
        const { message, description } = messageObj;
        notification[type]({
            message,
            description,
        });
    };
    //生命周期钩子函数(卸载阶段)
    componentWillUnmount() {
        this.setLayerStyle('out');
        // this.disconnect();
    }

    refresh = () => {
        console.log('刷新');
    }

    errMsg = (message, code) => {
        if (code === '-1') {
            notification.error({
                message: '系统遇到问题，请联系运维人员',
                description: message,
                duration: 3
            });
        } else {
            notification.warn({
                message: '提示',
                description: message,
                duration: 3
            });
        }
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

    //是否需要请求后台数据 
    isNeedGetData = true;

    openWindow = (route) => {
        //固定写法
        let url = `${window.location.origin}${this.apih5.mainModule}#${this.apih5.mainModule}`;
        let width = window.innerWidth * 0.85;
        let height = window.innerHeight * 0.8;
        let left = (window.innerWidth - width) / 2;
        let top = (window.innerHeight - height) / 1.5;
        console.log(top)
        window.open(`${url}${route}`, route, `width=${width}, height=${height}, left=${left}, top=${top}, toolbar=no, menubar=no, scrollbars=no, resizable=no, location=no, status=no`);
    }

    render() {
        let commProps = {
            ...this.props,
            openWindow: this.openWindow,
            errMsg: this.errMsg,
            isNeedGetData: this.isNeedGetData
        }

        return (
            <div className={s.root} id="fullScreen">
                <div>
                    <Suspense fallback={<div className={s.loadingCon}>loading...</div>}>
                        <Header {...commProps} />
                    </Suspense>
                </div>
                <div className={s.con}>
                    <div className={s.left}>
                        <div className={s.content}>
                            <div className={s.leftTop}>
                                <Suspense fallback={<div className={s.loadingCon}>loading...</div>}>
                                    <Readme r {...commProps} />
                                </Suspense>
                            </div>
                            <div className={s.leftBottom}>
                                <Suspense fallback={<div className={s.loadingCon}>loading...</div>}>
                                    <LeftBottomOne {...commProps} />
                                </Suspense>
                            </div>
                            <div className={s.leftBottom}>
                                <Suspense fallback={<div className={s.loadingCon}>loading...</div>}>
                                    <LeftBottomTwo {...commProps} />
                                </Suspense>
                            </div>
                        </div>
                    </div>

                    <div className={s.body}>
                        <div className={s.content}>
                            <div className={s.bodyLeft}>
                                <div className={s.bodyLeftTop}>
                                    <Suspense fallback={<div className={s.loadingCon}>loading...</div>}>
                                        <BodyLeftTop {...commProps} />
                                    </Suspense>
                                </div>
                                <div className={s.bodyLeftBottom}>
                                    <Suspense fallback={<div className={s.loadingCon}>loading...</div>}>
                                        <BodyLeftBottom {...commProps} />
                                    </Suspense>
                                </div>
                            </div>
                            <div className={s.bodyRight}>
                                <div className={s.bodyRightTop}>
                                    <Suspense fallback={<div className={s.loadingCon}>loading...</div>}>
                                        <Info2 {...commProps} />
                                    </Suspense>
                                </div>
                                <div className={s.bodyRightTop}>
                                    <Suspense fallback={<div className={s.loadingCon}>loading...</div>}>
                                        <BodyRightTopTwo {...commProps} />
                                    </Suspense>
                                </div>
                                <div className={s.bodyRightBottom}>
                                    <Suspense fallback={<div className={s.loadingCon}>loading...</div>}>
                                        <BodyRightBottom {...commProps} />
                                    </Suspense>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div className={s.right}>
                        <div className={s.one}>
                            <Suspense fallback={<div className={s.loadingCon}>loading...</div>}>
                                <RightOne  {...commProps} />
                            </Suspense>
                        </div>
                        <div className={s.two}>
                            <Suspense fallback={<div className={s.loadingCon}>loading...</div>}>
                                <RightTwo  {...commProps} />
                            </Suspense>
                        </div>
                        <div className={s.three}>
                            <Suspense fallback={<div className={s.loadingCon}>loading...</div>}>
                                <RightThree  {...commProps} />
                            </Suspense>
                        </div>
                        <div className={s.four}>
                            <Suspense fallback={<div className={s.loadingCon}>loading...</div>}>
                                <RightPengLIng  {...commProps} />
                            </Suspense>
                        </div>
                    </div>

                </div>
            </div>
        )
    }
}
export default index;