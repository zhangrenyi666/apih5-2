import React, { Component, lazy, Suspense } from 'react'
import s from './style.less'
import { notification } from 'antd'

const Header = lazy(() => import("./components/Header"));
const LeftTop = lazy(() => import("./components/LeftTop"));
const Info = lazy(() => import("./components/Info"));
const BodyOne = lazy(() => import("./components/BodyOne"));
const BodyTwo = lazy(() => import("./components/BodyTwo"));
const BodyItemOne = lazy(() => import("./components/BodyItemOne"));
const BodyItemTwo = lazy(() => import("./components/BodyItemTwo"));
const RightOne = lazy(() => import("./components/RightOne"));
const RightTwo = lazy(() => import("./components/RightTwo"));
const RightThree = lazy(() => import("./components/RightThree"));


class index extends Component {
    state = {
        //顶部数据
        topData: {
            logo: ''
        }
    }

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

    componentWillUnmount() {
        this.setLayerStyle('out');
        this.disconnect();
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


    render() {
        return (
            <div className={s.root} id="fullScreen">
                <div>
                    <Suspense fallback={<div className={s.loadingCon}>loading...</div>}>
                        <Header {...this.props} errMsg={this.errMsg} />
                    </Suspense>
                </div>
                <div className={s.con}>
                    <div className={s.left}>
                        <div className={s.content}>
                            <div className={s.leftTop}>
                                <Suspense fallback={<div className={s.loadingCon}>loading...</div>}>
                                    <LeftTop {...this.props} errMsg={this.errMsg} isNeedGetData={this.isNeedGetData} />
                                </Suspense>
                            </div>
                            <div className={s.leftBottom}>
                                <Suspense fallback={<div className={s.loadingCon}>loading...</div>}>
                                    <Info {...this.props} errMsg={this.errMsg} isNeedGetData={this.isNeedGetData} />
                                </Suspense>
                            </div>
                        </div>
                    </div>

                    <div className={s.body}>
                        <div className={s.content}>
                            <div className={s.bodyOne}>
                                <Suspense fallback={<div className={s.loadingCon}>loading...</div>}>
                                    <BodyOne {...this.props} errMsg={this.errMsg} isNeedGetData={this.isNeedGetData} />
                                </Suspense>
                            </div>
                            <div className={s.bodyTwo}>
                                <Suspense fallback={<div className={s.loadingCon}>loading...</div>}>
                                    <BodyTwo {...this.props} errMsg={this.errMsg} isNeedGetData={this.isNeedGetData} />
                                </Suspense>

                            </div>
                            <div className={s.bodyThree}>
                                <div className={s.item}>
                                    <Suspense fallback={<div className={s.loadingCon}>loading...</div>}>
                                        <BodyItemOne {...this.props} errMsg={this.errMsg} isNeedGetData={this.isNeedGetData} />
                                    </Suspense>
                                </div>
                                <div className={s.item}>
                                    <Suspense fallback={<div className={s.loadingCon}>loading...</div>}>
                                        <BodyItemTwo {...this.props} errMsg={this.errMsg} isNeedGetData={this.isNeedGetData} />
                                    </Suspense>
                                </div>
                            </div>

                        </div>
                    </div>

                    <div className={s.right}>
                        <div className={s.rightOne}>
                            <Suspense fallback={<div className={s.loadingCon}>loading...</div>}>
                                <RightOne  {...this.props} errMsg={this.errMsg} isNeedGetData={this.isNeedGetData} />
                            </Suspense>
                        </div>
                        <div className={s.rightTwo}>
                            <Suspense fallback={<div className={s.loadingCon}>loading...</div>}>
                                <RightTwo  {...this.props} errMsg={this.errMsg} isNeedGetData={this.isNeedGetData} />
                            </Suspense>
                        </div>
                        <div className={s.rightThree}>
                            <Suspense fallback={<div className={s.loadingCon}>loading...</div>}>
                                <RightThree  {...this.props} errMsg={this.errMsg} isNeedGetData={this.isNeedGetData} />
                            </Suspense>
                        </div>
                    </div>
                </div>
            </div>
        )
    }
}
export default index;