import React, { Component } from 'react';
import s from './style.less';
import { Spin } from 'antd';
import { Toast, NavBar, Icon, Tabs } from 'antd-mobile';
import ReactEcharts from 'echarts-for-react';
import moment from 'moment';
import Representative from "./representative";
import Report from "./report";
import Contribute from "./contribute";
import Technology from './technology';
import Safety from './safety';
import Stipulate from './stipulate';
class Exposure extends Component {
    constructor(props) {
        super(props);
        this.state = {
            page: 0,
            loading: false,
            loadings: false,
            data: []
        };
    }
    componentDidMount() {
        this.refresh();
    }
    refresh = () => {
        this.setState({
            loading: true
        })
        const { myFetch } = this.props;
        myFetch('getNearlyWeekChart', {}).then(({ data, success, message }) => {
            if (success) {
                this.setState({
                    loading: false,
                    data: data
                })
            } else {
                Toast.fail(message)
            }
        })
    }
    getOptions = () => {
        const { data } = this.state;
        const option = {
            title: {
                text: '曝光近一周趋势图',
                x: 'center',
                textStyle: {
                    color: '#108ee9',
                    fontSize: 14
                },
            },
            tooltip: {
                trigger: 'axis'
            },
            legend: {
                top: 18,
                data: ['有奖获奖', '典型曝光'],
                x: 'center',
            },
            color: ['#1890ff', '#10cf9b'],
            grid: {
                left: 5,
                right: 5,
                bottom: 0,
                top: 43,
                containLabel: true,
            },
            xAxis: {
                type: 'category',
                data: data.map((item) => {
                    return moment(item.dateTime).format('DD');
                }),
                axisLabel: {
                    interval: 0
                },
            },
            yAxis: {
                type: 'value',
            },
            series: [
                {
                    name: '有奖获奖',
                    type: 'line',
                    stack: '总量',
                    data: data.map((item) => {
                        return item.prize
                    })
                },
                {
                    name: '典型曝光',
                    type: 'line',
                    stack: '总量',
                    data: data.map((item) => {
                        return item.typicalExposure
                    })
                }
            ]
        };
        return option
    }
    render() {
        const { loading, loadings, data, page } = this.state;
        const { myPublic: { appInfo: { mainModule } } } = this.props;
        return (
            <div className={s.root}>
                <div className={s.top}>
                    <NavBar
                        style={{ width: "100%" }}
                        mode="dark"
                        icon={<Icon type="left" />}
                        onLeftClick={() => {
                            window.history.back();
                        }}
                    >{"曝光页面"}</NavBar>
                </div>
                <Spin spinning={loading}>
                    <div className={s.center}>
                        <ReactEcharts
                            style={{ height: '30vh' }}
                            option={this.getOptions()}
                            notMerge={true}
                            lazyUpdate={true}
                            theme={"theme_name"}
                        />
                    </div>
                </Spin>
                <Spin spinning={loadings}>
                    <div className={s.footer}>
                        <Tabs
                            swipeable={false}
                            tabs={[{ title: "典型曝光" }, { title: "举报获奖" }, { title: "投稿获奖" }, { title: "技术质量" },{ title: "安全环保" }, { title: "八项规定" }]}
                            initialPage={page}
                            page={page}
                            renderTabBar={props => <Tabs.DefaultTabBar {...props} page={3} />}
                            onChange={(_, page) => {
                                this.setState({ page });
                            }}
                        >
                            <Representative {...this.props} tabBarHeight={88.5} />
                            <Report {...this.props} tabBarHeight={88.5} />
                            <Contribute {...this.props} tabBarHeight={88.5} />
                            <Technology {...this.props} tabBarHeight={88.5} />
                            <Safety {...this.props} tabBarHeight={88.5} />
                            <Stipulate {...this.props} tabBarHeight={88.5} />
                        </Tabs>
                    </div>
                </Spin>
            </div>
        )
    }
}

export default Exposure;
