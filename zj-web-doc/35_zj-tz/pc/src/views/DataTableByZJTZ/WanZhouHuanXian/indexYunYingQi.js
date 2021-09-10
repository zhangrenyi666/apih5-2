import React, { Component } from "react";
import s from './styleYunYingQi.less';
import ReactEcharts from 'echarts-for-react';
import { Spin, message as Msg } from "antd";
import CenterPIETop from './CenterPIETop';
import CenterPIEBottom from './CenterPIEBottom';
import YiBiaoPan from './YiBiaoPan';
import DurationStatus from './DurationStatus';
import QuShi from './QuShi';
const yanzhong = require('../../../../src/imgs/yanzhong.png');
const yujing = require('../../../../src/imgs/yujing.png');
const zhengchang = require('../../../../src/imgs/zhengchang.png');
const xmtitle = require('../../../../src/imgs/xmtitle.png');
const xmzjqk = require('../../../../src/imgs/xmzjqk.png');
const xmczpm = require('../../../../src/imgs/xmczpm.png');
const xmyj = require('../../../../src/imgs/xmyj.png');
const xmqssj = require('../../../../src/imgs/xmqssj.png');
const rightMiddleB = require('../../../../src/imgs/rightMiddleB.png');
const xmgqzt = require('../../../../src/imgs/xmgqzt.png');
const rightTop = {
    backgroundImage: `url(${xmtitle})`,
    backgroundRepeat: 'no-repeat',
    backgroundSize: '100% 100%'
};
const leftTop = {
    backgroundImage: `url(${xmzjqk})`,
    backgroundRepeat: 'no-repeat',
    backgroundSize: '100% 100%'
};
const leftMiddle = {
    backgroundImage: `url(${xmczpm})`,
    backgroundRepeat: 'no-repeat',
    backgroundSize: '100% 100%'
};
const leftBottom = {
    backgroundImage: `url(${xmyj})`,
    backgroundRepeat: 'no-repeat',
    backgroundSize: '100% 100%'
};
const rightMiddle = {
    backgroundImage: `url(${rightMiddleB})`,
    backgroundRepeat: 'no-repeat',
    backgroundSize: '100% 100%'
};
const rightBottom = {
    backgroundImage: `url(${xmgqzt})`,
    backgroundRepeat: 'no-repeat',
    backgroundSize: '100% 100%'
};
const lastBottom = {
    backgroundImage: `url(${xmqssj})`,
    backgroundRepeat: 'no-repeat',
    backgroundSize: '100% 100%'
};
class index extends Component {
    constructor(props) {
        super(props);
        this.state = {
            aqH: (window.innerHeight - 64) * 0.25,
            loading: false,
            pieData: [],
            legendData: [],
            pieMiddleData: null,
            textData: [],
            leftBottomCenter: null
        }
    }
    componentDidMount() {
        this.refresh();
        this.Middle();
        this.RightTopText();
        this.yujing();
    }
    yujing() {
        const { projectId } = this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany;
        this.props.myFetch('getProjectPageWarning', {
            projectId: projectId,
            warnType: '2'
        }).then(
            ({ success, message, data }) => {
                if (success) {
                    this.setState({
                        leftBottomCenter: data
                    })
                } else {
                    Msg.warn(message)
                }
            }
        );
    }
    refresh = () => {//资金情况饼图
        const { projectId } = this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany;
        this.setState({
            loading: true
        })
        this.props.myFetch('getConstructPageCapital', {
            // projectId: '1EHR09N5O01203330B0A00003A095092',
            projectId: projectId,
            // proProcessId: '2'
        }).then(
            ({ success, message, data }) => {
                if (success) {
                    this.setState({
                        loading: false,
                        pieData: data,
                        legendData: data.listTitle
                    })
                } else {
                    this.setState({
                        loading: false,
                    })
                    Msg.warn(message)
                }
            }
        );
    }
    Middle() {//中间5个饼图
        const { projectId, proProcessId } = this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany;
        this.props.myFetch('getOperatePageCommentAndIncome', {
            projectId: projectId,
            proProcessId: proProcessId
        }).then(
            ({ success, message, data }) => {
                if (success) {
                    this.setState({
                        pieMiddleData: data
                    })
                } else {
                    Msg.warn(message)
                }
            }
        );
    }
    RightTopText() {
        const { projectId } = this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany;
        this.props.myFetch('getZjTzProManageDetails', {
            // projectId: '1EHR09N5O01203330B0A00003A095092'
            projectId: projectId
        }).then(
            ({ success, message, data }) => {
                if (success) {
                    this.setState({
                        textData: data
                    })
                } else {
                    Msg.warn(message)
                }
            }
        );
    }
    getOptionOne = () => {
        const { pieData, legendData } = this.state;
        const option = {
            color: ['#0074f7', '#fd5389', '#39eba8', '#ffdc64'],
            tooltip: {
                trigger: 'item',
                formatter: '{b} : {c} ({d}%)'
            },
            legend: {
                type: 'scroll',
                orient: 'vertical',
                right: 10,
                top: 20,
                bottom: 20,
                textStyle: {
                    color: '#fff',
                    fontSize: 13
                },
                data: legendData
            },
            series: [
                {
                    name: '',
                    type: 'pie',
                    radius: '80%',
                    hoverAnimation: false,
                    center: ['40%', '50%'],
                    label: {
                        show: true,
                        position: 'outer',
                        formatter: '{d}%',
                        fontSize: 14,
                        fontWeight: 'bold'
                    },
                    labelLine: {
                        show: false,
                        normal: {
                            length: 2,
                            length2: 2,
                            lineStyle: {
                                width: 0
                            }
                        }
                    },
                    data: pieData.list,
                    emphasis: {
                        itemStyle: {
                            shadowBlur: 10,
                            shadowOffsetX: 0,
                            shadowColor: 'rgba(0, 0, 0, 0.5)'
                        }
                    }
                }
            ]
        };
        return option
    }
    render() {
        const { loading, pieData, pieMiddleData, textData, leftBottomCenter } = this.state;
        const { projectName } = this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany;
        return (
            <div className={s.root}>
                {/* 左侧 */}
                <div className={s.left}>
                    <div className={s.leftTop} style={leftTop}>
                        <div className={s.leftTopTitle}>
                            <div className={s.leftTopTitleT}>
                                资金情况
                            </div>
                        </div>
                        <div className={s.leftTopCenter}>
                            <Spin spinning={loading}>
                                <ReactEcharts
                                    style={{ height: '100%' }}
                                    option={this.getOptionOne()}
                                    notMerge={true}
                                    lazyUpdate={true}
                                    theme={"theme_name"}
                                />
                            </Spin>
                        </div>
                        <div className={s.leftTopBottom}>
                            <div className={s.leftTopBottomL}>
                                投资总额：<span style={{ color: '#1890ff', paddingLeft: '20px', fontWeight: 'bold' }}>{pieData.ztze ? pieData.ztze : 0}万元</span>
                            </div>
                        </div>
                    </div>
                    <div className={s.leftMiddle} style={leftMiddle}>
                        <div className={s.leftMiddleTitle}>
                            <div className={s.leftMiddleTitleT}>
                                当前时期
                            </div>
                        </div>
                        <div className={s.dangqianshiqi}>
                            <YiBiaoPan {...this.props} clickRouter={'YunYing'} />
                        </div>
                    </div>
                    {/* 预警 */}
                    <div className={s.leftBottom} style={leftBottom}>
                        <div className={s.leftBottomTitle}>
                            <div className={s.leftBottomTitleT}>
                                预警
                            </div>
                        </div>
                        <div className={s.leftBottomCenter}>
                            <div className={s.leftBottomCenterC}>
                                <div className={s.leftBottomCenterCL}>
                                    <div className={s.leftBottomCenterCLL}>
                                        合法合规：
                                    </div>
                                    <div className={s.leftBottomCenterCLR}>
                                        {leftBottomCenter?.[0]?.colourFlag7 === 'red' ? <img src={yanzhong} style={{ height: "22px" }} /> : leftBottomCenter?.[0]?.colourFlag7 === 'yellow' ? <img src={yujing} style={{ height: "22px" }} /> : <img src={zhengchang} style={{ height: "22px" }} />}
                                    </div>
                                </div>
                                <div className={s.leftBottomCenterCL}>
                                    <div className={s.leftBottomCenterCLL}>
                                        风险管理：
                                    </div>
                                    <div className={s.leftBottomCenterCLR} style={{ border: 'none' }}>
                                        {leftBottomCenter?.[0]?.colourFlag5 === 'red' ? <img src={yanzhong} style={{ height: "22px" }} /> : leftBottomCenter?.[0]?.colourFlag5 === 'yellow' ? <img src={yujing} style={{ height: "22px" }} /> : <img src={zhengchang} style={{ height: "22px" }} />}
                                    </div>
                                </div>
                            </div>
                            <div className={s.leftBottomCenterC}>
                                <div className={s.leftBottomCenterCL}>
                                    <div className={s.leftBottomCenterCLL}>
                                        设计流程：
                                    </div>
                                    <div className={s.leftBottomCenterCLR}>
                                        {leftBottomCenter?.[0]?.colourFlag6 === 'red' ? <img src={yanzhong} style={{ height: "22px" }} /> : leftBottomCenter?.[0]?.colourFlag6 === 'yellow' ? <img src={yujing} style={{ height: "22px" }} /> : <img src={zhengchang} style={{ height: "22px" }} />}
                                    </div>
                                </div>
                                <div className={s.leftBottomCenterCL}>
                                    <div className={s.leftBottomCenterCLL}>
                                        投评营收：
                                    </div>
                                    <div className={s.leftBottomCenterCLR} style={{ border: 'none' }}>
                                        {leftBottomCenter?.[0]?.colourFlag4 === 'red' ? <img src={yanzhong} style={{ height: "22px" }} /> : leftBottomCenter?.[0]?.colourFlag4 === 'yellow' ? <img src={yujing} style={{ height: "22px" }} /> : <img src={zhengchang} style={{ height: "22px" }} />}
                                    </div>
                                </div>
                            </div>
                            <div className={s.leftBottomCenterC}>
                                <div className={s.leftBottomCenterCL}>
                                    <div className={s.leftBottomCenterCLL}>
                                    </div>
                                    <div className={s.leftBottomCenterCLR}>
                                    </div>
                                </div>
                                <div className={s.leftBottomCenterCL}>
                                    <div className={s.leftBottomCenterCLL}>
                                    </div>
                                    <div className={s.leftBottomCenterCLR} style={{ border: 'none' }}>
                                    </div>
                                </div>
                            </div>
                            <div className={s.leftBottomCenterC}>
                                <div className={s.leftBottomCenterCL}>
                                    <div className={s.leftBottomCenterCLL}>
                                    </div>
                                    <div className={s.leftBottomCenterCLR}>
                                    </div>
                                </div>
                                <div className={s.leftBottomCenterCL}>
                                    <div className={s.leftBottomCenterCLL}>

                                    </div>
                                    <div className={s.leftBottomCenterCLR} style={{ border: 'none' }}>

                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                {/* 中间 */}
                <div className={s.right}>
                    <div className={s.rightTop} style={rightTop}>
                        {projectName}
                    </div>
                    <div className={s.rightMiddle} style={rightMiddle}>
                        <div className={s.rightMiddleTop}>
                            {pieMiddleData?.length ? <CenterPIETop {...this.props} type={'yunying'} pieMiddleData={pieMiddleData} /> : null}
                        </div>
                        <div className={s.rightMiddleBottom}>
                            {pieMiddleData?.length ? <CenterPIEBottom {...this.props} type={'yunying'} pieMiddleData={pieMiddleData} /> : null}
                        </div>
                    </div>
                    <div className={s.rightBottom} style={rightBottom}>
                        <DurationStatus {...this.props} />
                    </div>
                </div>
                {/* 右侧 */}
                <div className={s.last}>
                    <div className={s.lastTop} style={leftTop}>
                        <div className={s.leftTopTitle}>
                            <div className={s.leftTopTitleT}>
                                项目简介
                            </div>
                        </div>
                        <div className={s.leftTopBottom}>
                            <div className={s.leftTopBottomC}>
                                <div className={s.leftTopBottomCL}>
                                    <div className={s.leftTopBottomCLL}>
                                        项目简称：
                                        </div>
                                    <div className={s.leftTopBottomCLR}>
                                        {textData.projectShortName ? textData.projectShortName : null}
                                    </div>
                                </div>
                                <div className={s.leftTopBottomCL}>
                                    <div className={s.leftTopBottomCLL}>
                                        工程类别：
                                        </div>
                                    <div className={s.leftTopBottomCLR}>
                                        {textData.proCategoryName ? textData.proCategoryName : null}
                                    </div>
                                </div>
                            </div>
                            <div className={s.leftTopBottomC}>
                                <div className={s.leftTopBottomCL}>
                                    <div className={s.leftTopBottomCLL}>
                                        投资模式：
                                        </div>
                                    <div className={s.leftTopBottomCLR}>
                                        {textData.investPatten ? textData.investPatten : null}
                                    </div>
                                </div>
                                <div className={s.leftTopBottomCL}>
                                    <div className={s.leftTopBottomCLL}>
                                        项目进展：
                                        </div>
                                    <div className={s.leftTopBottomCLR}>
                                        {textData.proProcessName ? textData.proProcessName : null}
                                    </div>
                                </div>
                            </div>
                            <div className={s.leftTopBottomC}>
                                <div className={s.leftTopBottomCL}>
                                    <div className={s.leftTopBottomCLL}>
                                        管理单位：
                                        </div>
                                    <div className={s.leftTopBottomCLR}>
                                        {textData.companyName ? textData.companyName : null}
                                    </div>
                                </div>
                                <div className={s.leftTopBottomCL}>
                                    <div className={s.leftTopBottomCLL}>
                                        项目类型：
                                        </div>
                                    <div className={s.leftTopBottomCLR}>
                                        {textData.proTypeName ? textData.proTypeName : null}
                                    </div>
                                </div>
                            </div>
                            <div className={s.leftTopBottomC}>
                                <div className={s.leftTopBottomCL}>
                                    <div className={s.leftTopBottomCLLs}>
                                        我方持股比例：
                                        </div>
                                    <div className={s.leftTopBottomCLRs}>
                                        {textData.company7 ? textData.company7 : null}
                                    </div>
                                </div>
                                <div className={s.leftTopBottomCL}>
                                    <div className={s.leftTopBottomCLL}>
                                        所属地区：
                                        </div>
                                    <div className={s.leftTopBottomCLR}>
                                        {textData.areaName ? textData.areaName : null}
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div className={s.lastBottom} style={lastBottom}>
                        <QuShi {...this.props} clickRoter={'yunying'} />
                    </div>
                </div>
            </div>
        );
    }
}

export default index;