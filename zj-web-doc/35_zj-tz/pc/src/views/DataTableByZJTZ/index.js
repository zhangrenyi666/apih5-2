import React, { Component } from "react";
import s from "./style.less";
import moment from 'moment';
import { message as Msg } from 'antd';
import { basic } from '../modules/layouts';
import { push } from "react-router-redux";
import RegionalOverview from './RegionalOverview';
import ProgressOfTheWarning from './ProgressOfTheWarning';
import TheChartData from './TheChartData';
import WanZhouHuanXian from './WanZhouHuanXian/index';
import YunYingQi from './WanZhouHuanXian/indexYunYingQi';
import HuiGou from './WanZhouHuanXian/indexHuiGou';
import PublicizeWidely from './PublicizeWidely';
const background = require('../../../src/imgs/background.png');
const tianqi = require('../../../src/imgs/tianqi.png');
const topRegion1 = require('../../../src/imgs/topRegion1.png');
const topRegion2 = require('../../../src/imgs/topRegion2.png');
const topAdvertusement1 = require('../../../src/imgs/topAdvertusement1.png');
const topAdvertusement2 = require('../../../src/imgs/topAdvertusement2.png');
const topChart1 = require('../../../src/imgs/topChart1.png');
const topChart2 = require('../../../src/imgs/topChart2.png');
const dysj1 = require('../../../src/imgs/dysj1.png');
const dysj2 = require('../../../src/imgs/dysj2.png');
const jdyj1 = require('../../../src/imgs/jdyj1.png');
const jdyj2 = require('../../../src/imgs/jdyj2.png');
const tjfx1 = require('../../../src/imgs/tjfx1.png');
const tjfx2 = require('../../../src/imgs/tjfx2.png');
const grgz1 = require('../../../src/imgs/grgz1.png');
const grgz2 = require('../../../src/imgs/grgz2.png');
const db = require('../../../src/imgs/db.png');
const yb = require('../../../src/imgs/yb.png');
const rootStyle = {
    backgroundImage: `url(${background})`,
    backgroundRepeat: 'no-repeat',
    backgroundSize: '100% 100%'
};
const topRegion3 = {
    backgroundImage: `url(${topRegion1})`,
    backgroundRepeat: 'no-repeat',
    backgroundSize: '100% 100%'
};
const topRegion4 = {
    backgroundImage: `url(${topRegion2})`,
    backgroundRepeat: 'no-repeat',
    backgroundSize: '100% 100%'
};
const topAdvertusement3 = {
    backgroundImage: `url(${topAdvertusement1})`,
    backgroundRepeat: 'no-repeat',
    backgroundSize: '100% 100%'
};
const topAdvertusement4 = {
    backgroundImage: `url(${topAdvertusement2})`,
    backgroundRepeat: 'no-repeat',
    backgroundSize: '100% 100%'
};
const topChart3 = {
    backgroundImage: `url(${topChart1})`,
    backgroundRepeat: 'no-repeat',
    backgroundSize: '100% 100%'
};
const topChart4 = {
    backgroundImage: `url(${topChart2})`,
    backgroundRepeat: 'no-repeat',
    backgroundSize: '100% 100%'
};
class index extends Component {
    state = {
        tabsIndex: '1',
        todoData: null
    }
    componentDidMount () {
        const { curCompany: { projectId }, ext1 } = this.props.loginAndLogoutInfo.loginInfo.userInfo;
        if ((ext1 === '3' || ext1 === '4') && projectId === 'all') {
            this.setState({
                tabsIndex: '4'
            })
        }
        this.props.myFetch('getFlowCount', {}).then(
            ({ success, message, data }) => {
                if (success) {
                    this.setState({
                        todoData: data
                    })
                } else {
                    Msg.error(message);
                }
            }
        );
        this.setLayerStyle("in");
    }
    componentWillUnmount () {
        this.setLayerStyle("out");
    }

    //改变框架的样式
    setLayerStyle = (type = "in") => {
        if (type === "in") {
            //进入页面时
            let _conDom = document.getElementsByClassName("ant-layout");
            let _headerDom = document.getElementsByClassName("ant-layout-header");
            let _footerDom = document.getElementsByClassName("ant-layout-footer");
            let _breadcrumbDom = document.getElementsByClassName("ant-breadcrumb");
            let _alc = document.getElementsByClassName("ant-layout-content");
            if (_conDom && _conDom[5]) {
                _conDom[5].style.padding = 0;
                _conDom[5].style.background = "rgb(0, 21, 43)";
            }
            if (_conDom && _conDom[2]) {
                _conDom[2].style.padding = 0;
                _conDom[2].style.background = "rgb(0, 21, 43)";
            }
            //解决header不显示问题
            if (_headerDom && _headerDom[0]) {
                _headerDom[0].style.zIndex = "1";
            }
            if (_headerDom && _headerDom[1]) {
                _headerDom[1].style.display = "none";
            }
            if (_breadcrumbDom && _breadcrumbDom[0]) {
                _breadcrumbDom[0].style.display = "none";
            }
            if (_breadcrumbDom && _breadcrumbDom[1]) {
                _breadcrumbDom[1].style.display = "none";
            }
            if (_footerDom && _footerDom[0]) {
                _footerDom[0].style.display = "none";
            }
            if (_alc && _alc[0]) {
                _alc[0].style.background = "";
                _alc[0].style.padding = "0";
            }
            if (_alc && _alc[1]) {
                _alc[1].style.background = "";
                _alc[1].style.padding = "0";
            }
        } else {
            //离开页面
            let _conDom = document.getElementsByClassName(".ant-layout");
            let _headerDom = document.getElementsByClassName("ant-layout-header");
            let _breadcrumbDom = document.getElementsByClassName(".ant-breadcrumb");
            let _footerDom = document.getElementsByClassName("ant-layout-footer");
            let _alc = document.getElementsByClassName("ant-layout-content");
            if (_conDom && _conDom[5]) {
                _conDom[5].style.padding = "0 12px";
                _conDom[5].style.background = "#f0f2f5";
            }
            if (_conDom && _conDom[2]) {
                _conDom[2].style.padding = "0 12px";
                _conDom[2].style.background = "#f0f2f5";
            }
            if (_headerDom && _headerDom[1]) {
                _headerDom[1].style.display = "none";
            }
            if (_breadcrumbDom && _breadcrumbDom[0]) {
                _breadcrumbDom[0].style.display = "none";
            }
            if (_breadcrumbDom && _breadcrumbDom[1]) {
                _breadcrumbDom[1].style.display = "none";
            }
            if (_footerDom && _footerDom[0]) {
                _footerDom[0].style.display = "none";
            }
            if (_alc && _alc[0]) {
                _alc[0].style.background = "rgb(255, 255, 255)";
                _alc[0].style.padding = "12";
            }
            if (_alc && _alc[1]) {
                _alc[1].style.background = "rgb(255, 255, 255)";
                _alc[1].style.padding = "12";
            }
        }
    };
    getWeek = (date) => { // 参数时间戳
        let week = moment(date).day()
        switch (week) {
            case 1:
                return '星期一'
            case 2:
                return '星期二'
            case 3:
                return '星期三'
            case 4:
                return '星期四'
            case 5:
                return '星期五'
            case 6:
                return '星期六'
            case 0:
                return '星期日'
        }
    }
    render () {
        const { tabsIndex, todoData } = this.state;
        const {
            dispatch,
            myPublic: {
                appInfo: { mainModule }
            }
        } = this.props;
        const { curCompany: { proProcessId, projectId }, ext1 } = this.props.loginAndLogoutInfo.loginInfo.userInfo;
        return (
            <div className={s.root} style={rootStyle}>
                <div className={s.topTiitle}>
                    <div className={s.topHeader}></div>
                    <div className={s.topTitleLeft}>
                        <div style={{ height: "27px", lineHeight: '27px', color: 'white', fontSize: '16px', fontWeight: '500' }}>{moment(new Date()).format('YYYY年MM月DD日')} {this.getWeek(new Date)} {moment(new Date()).format('HH:mm:ss')}</div>
                        <div style={{ height: "27px", lineHeight: '27px', overflow: 'hidden' }}>
                            <div style={{ float: 'left', paddingRight: '10px', lineHeight: '24px' }}>
                                <img src={tianqi} />
                            </div>
                            <div style={{ float: 'left' }}>
                                <iframe scrolling="no" height="27" frameBorder="0" allowtransparency="true" src="https://i.tianqi.com?c=code&id=26&color=%23FFFFFF&icon=1&site=14"></iframe>
                            </div>
                        </div>
                    </div>
                    {(ext1 === '1' || ext1 === '2') && projectId === 'all' ? <div className={s.topTiitleCenter}>
                        <div className={s.topTiitleCenterL} style={tabsIndex === '1' ? topRegion4 : topRegion3} onClick={() => {
                            this.setState({
                                tabsIndex: '1'
                            })
                        }}>
                            <div>
                                <img src={tabsIndex === '1' ? dysj2 : dysj1} /> <span style={tabsIndex === '1' ? { color: 'white', fontSize: '16px', fontWeight: '500' } : { color: '#e6e6e6', fontSize: '16px', fontWeight: '500' }}>地域数据</span>
                            </div>
                        </div>
                        <div className={s.topTiitleCenterL} style={tabsIndex === '2' ? topAdvertusement4 : topAdvertusement3} onClick={() => {
                            this.setState({
                                tabsIndex: '2'
                            })
                        }}>
                            <div>
                                <img src={tabsIndex === '2' ? jdyj2 : jdyj1} /> <span style={tabsIndex === '2' ? { color: 'white', fontSize: '16px', fontWeight: '500' } : { color: '#e6e6e6', fontSize: '16px', fontWeight: '500' }}>进度预警</span>
                            </div>
                        </div>
                        <div className={s.topTiitleCenterL} style={tabsIndex === '3' ? topAdvertusement4 : topAdvertusement3} onClick={() => {
                            this.setState({
                                tabsIndex: '3'
                            })
                        }}>
                            <div>
                                <img src={tabsIndex === '3' ? tjfx2 : tjfx1} /> <span style={tabsIndex === '3' ? { color: 'white', fontSize: '16px', fontWeight: '500' } : { color: '#e6e6e6', fontSize: '16px', fontWeight: '500' }}>统计分析</span>
                            </div>
                        </div>
                        <div className={s.topTiitleCenterL} style={tabsIndex === '4' ? topChart4 : topChart3} onClick={() => {
                            this.setState({
                                tabsIndex: '4'
                            })
                        }}>
                            <div>
                                <img src={tabsIndex === '4' ? grgz2 : grgz1} /> <span style={tabsIndex === '4' ? { color: 'white', fontSize: '16px', fontWeight: '500' } : { color: '#e6e6e6', fontSize: '16px', fontWeight: '500' }}>广而告之</span>
                            </div>
                        </div>
                    </div> : (ext1 === '3' || ext1 === '4') && projectId === 'all' ? <div className={s.topTiitleCenterT}>
                        <div className={s.topTiitleCenterR} style={tabsIndex === '4' ? topAdvertusement4 : topAdvertusement3} onClick={() => {
                            this.setState({
                                tabsIndex: '4'
                            })
                        }}>
                            <div>
                                <img src={tabsIndex === '4' ? grgz2 : grgz1} /> <span style={tabsIndex === '4' ? { color: 'white', fontSize: '16px', fontWeight: '500' } : { color: '#e6e6e6', fontSize: '16px', fontWeight: '500' }}>广而告之</span>
                            </div>
                        </div>
                    </div> : (ext1 === '3' || ext1 === '4') && projectId !== 'all' ? <div className={s.topTiitleCenterB}>
                        <div className={s.topTiitleCenterR}></div>
                        <div className={s.topTiitleCenterR} style={tabsIndex === '1' ? topRegion4 : topRegion3} onClick={() => {
                            this.setState({
                                tabsIndex: '1'
                            })
                        }}>
                            <div>
                                <img src={tabsIndex === '1' ? tjfx2 : tjfx1} /> <span style={tabsIndex === '1' ? { color: 'white', fontSize: '16px', fontWeight: '500' } : { color: '#e6e6e6', fontSize: '16px', fontWeight: '500' }}>项目数据</span>
                            </div>
                        </div>
                        <div className={s.topTiitleCenterR} style={tabsIndex === '4' ? topChart4 : topChart3} onClick={() => {
                            this.setState({
                                tabsIndex: '4'
                            })
                        }}>
                            <div>
                                <img src={tabsIndex === '4' ? grgz2 : grgz1} /> <span style={tabsIndex === '4' ? { color: 'white', fontSize: '16px', fontWeight: '500' } : { color: '#e6e6e6', fontSize: '16px', fontWeight: '500' }}>广而告之</span>
                            </div>
                        </div>
                        <div className={s.topTiitleCenterR}></div>
                    </div> : <div className={s.topTiitleCenterT}>
                        <div className={s.topTiitleCenterR} style={tabsIndex === '1' ? topAdvertusement4 : topAdvertusement3} onClick={() => {
                            this.setState({
                                tabsIndex: '1'
                            })
                        }}>
                            <div>
                                <img src={tabsIndex === '1' ? tjfx2 : tjfx1} /> <span style={tabsIndex === '1' ? { color: 'white', fontSize: '16px', fontWeight: '500' } : { color: '#e6e6e6', fontSize: '16px', fontWeight: '500' }}>项目数据</span>
                            </div>
                        </div>
                    </div>}
                    <div className={s.topTiitleRight}>
                        <div className={s.topTiitleRightL}>
                            <div className={s.topTiitleRightLC} style={{ textAlign: 'center', lineHeight: '54px' }}>
                                <img src={db} />&nbsp;&nbsp;<span style={{ color: '#e6e6e6', fontSize: '14px', fontWeight: '500' }}>待办：</span>&nbsp;&nbsp;<span style={{ color: '#049ce4', fontSize: '22px', fontWeight: 'bold', borderBottom: '2px solid', cursor: 'pointer' }} onClick={() => {
                                    dispatch(
                                        push(`${mainModule}TodoHasTo`)
                                    )
                                }}>{todoData?.todoCount || 0}</span>
                            </div>
                        </div>
                        <div className={s.topTiitleRightL}>
                            <div className={s.topTiitleRightLC} style={{ lineHeight: '54px' }}>
                                <img src={yb} />&nbsp;&nbsp;<span style={{ color: '#e6e6e6', fontSize: '14px', fontWeight: '500' }}>已办：</span>&nbsp;&nbsp;<span style={{ color: '#04e4e4', fontSize: '22px', fontWeight: 'bold', borderBottom: '2px solid', cursor: 'pointer' }} onClick={() => {
                                    dispatch(
                                        push(`${mainModule}TodoHasToq`)
                                    )
                                }}>{todoData?.hasTodoCount || 0}</span>
                            </div>
                        </div>
                    </div>
                </div>
                <div className={s.center}>
                    {tabsIndex === '1' && (ext1 === '1' || ext1 === '2') && projectId === 'all' ? <RegionalOverview {...this.props} /> : null}
                    {tabsIndex === '2' && (ext1 === '1' || ext1 === '2') && projectId === 'all' ? <ProgressOfTheWarning {...this.props} /> : null}
                    {tabsIndex === '3' && (ext1 === '1' || ext1 === '2') && projectId === 'all' ? <TheChartData {...this.props} /> : null}
                    {tabsIndex === '4' && (ext1 === '1' || ext1 === '2' || ext1 === '3' || ext1 === '4') ? <PublicizeWidely {...this.props} /> : null}

                    {/* 万州环线 */}
                    {tabsIndex === '1' && (proProcessId === '' || proProcessId === '1' || proProcessId === '2' || proProcessId === '3' || proProcessId === '4' || proProcessId === '7') && projectId !== 'all' ? <WanZhouHuanXian {...this.props} /> : null}
                    {tabsIndex === '1' && proProcessId === '5' && projectId !== 'all' ? <YunYingQi {...this.props} /> : null}
                    {tabsIndex === '1' && proProcessId === '6' && projectId !== 'all' ? <HuiGou {...this.props} /> : null}
                </div>
            </div>
        );
    }
}
export default basic(index);
