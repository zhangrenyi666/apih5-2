import React, { Component } from "react";
import s from "./index.less"
import { NavBar, Icon, Modal, DatePicker, List, Toast, Button, Flex } from "antd-mobile"
import moment from "moment";
import { Table } from 'antd';
import ReactEcharts from 'echarts-for-react';
import { push } from "react-router-redux";
class index extends Component {
    constructor(props) {
        super(props);
        this.state = {
            dataArr: [],
            modal: false,
            modalTop: false,
            month: new Date(),
            fraction: [2.0, 4.9, 7.0, 23.2, 25.6, 76.7, 135.6, 162.2, 32.6, 20.0, 6.4, 3.3],
            average: [2.0, 2.2, 3.3, 4.5, 6.3, 10.2, 20.3, 23.4, 23.0, 16.5, 12.0, 6.2],
            xAxisData: [],
        }
    }
    componentDidMount() {
        this.getData()
    }
    getData() {
        this.props.myFetch('getZjLzehTeamScoreItemgetChartByMonth', {
            scoreMonth: moment(this.state.month).valueOf()
        }).then(({ data, success, message }) => {
            if (success) {
                this.setState({ dataArr: data, modal: false })
                const teamList = []
                const fraction = []
                const average = []
                if (data.length) {
                    data.map(item => {
                        teamList.push(item.teamName)
                        fraction.push(item.monthScore)
                        average.push(item.avgScore)
                    })
                    this.setState({
                        xAxisData: [...teamList],
                        fraction: [...fraction],
                        average: [...average],
                        current: 1
                    })
                } else {
                    Toast.fail('当前月份暂无数据')
                }
            } else {
                this.setState({ modal: false })
                Toast.fail(message, 1);
            }
        })
    }
    getOptionTow = () => {
        const { xAxisData, fraction, average } = this.state;
        var option = {
            tooltip: {
                trigger: 'axis',
                axisPointer: {
                    type: 'cross',
                    crossStyle: {
                        color: '#999'
                    }
                }
            },
            toolbox: {
                feature: {
                    dataView: { show: false },
                    magicType: { show: true, type: ['line', 'bar'] },
                    restore: { show: true },
                    saveAsImage: { show: false },
                    left: 'right',
                    bottom: '10px'
                }
            },
            legend: {
                data: ['分数', '平均分']
            },
            xAxis: [
                {
                    type: 'category',
                    data: xAxisData,
                    axisPointer: {
                        type: 'shadow'
                    }
                }
            ],
            yAxis: [
                {
                    type: 'value',
                    name: '分数',
                    min: 0,
                    max: 100,
                    interval: 20,
                    axisLabel: {
                        formatter: '{value}'
                    }
                },
                {
                    type: 'value',
                    name: '平均分',
                    min: 0,
                    max: 100,
                    interval: 20,
                    axisLabel: {
                        formatter: '{value}'
                    }
                }
            ],
            series: [
                {
                    name: '分数',
                    type: 'bar',
                    data: fraction
                },
                {
                    name: '平均分',
                    type: 'line',
                    yAxisIndex: 1,
                    data: average
                }
            ]
        };
        return option;
    }
    render() {
        const columns = [
            {
                title: '本月排名',
                dataIndex: 'monthRank',
                width: 90,
                key: 'monthRank',
            },
            {
                title: '劳务公司',
                dataIndex: 'companyName',
                width: 150,
                key: 'companyName',
            },
            {
                title: '班组名称',
                dataIndex: 'teamName',
                width: 120,
                key: 'teamName',
            },
            {
                title: '月度评分',
                dataIndex: 'monthScore',
                width: 120,
                key: 'monthScore',

            },
        ];
        const {
            mainModule
        } = this.props.myPublic.appInfo;
        
        const { dataArr, current } = this.state
        return (
            <div className={s.root}>
                <div>
                    <NavBar
                        style={{ width: "100%" }}
                        mode="dark"
                        icon={<Icon type="left" />}
                        onLeftClick={() => {
                            // this.props.dispatch(push(`${mainModule}App`));
                            this.props.myPublic?.androidApi?.closeActivity?.() || this.props.dispatch(push(`${mainModule}App`));
                            // this.props.history.goBack()
                        }}
                        rightContent={[
                            <Icon key="0" type="search" style={{ margin: '5px 5px 6px 5px', color: '#fff' }}
                                onClick={() => { this.setState({ modal: true }) }}
                            />

                        ]}
                    >
                        {"班组统计表"}
                    </NavBar>
                </div>
                <div className={s.center}>
                    <div>
                        <Flex direction={'row'} className={s.modalTop} justify={'between'} style={{ height: this.state.modalTop ? '40px' : 0 }}>
                            <div>月份{moment(this.state.month).format('YYYY-MM')}</div>
                            <Button
                                style={{ marginLeft: '20px' }} type="primary" inline size={'small'}
                                onClick={() => { this.setState({ modalTop: false, month: new Date() }, () => { this.getData() }) }}
                            >清除</Button>
                        </Flex>
                        <Table
                            bordered={true} size={'small'} columns={columns}
                            dataSource={this.state.dataArr}
                            pagination={{
                                position: 'bottom', pageSize: 5, current, onChange: (a) => {
                                    this.setState({current:a})
                                }
                            }}
                            scroll={{ x: 500 }}
                        />
                    </div>
                    <div style={{
                        background: '#eee', height: '35px', lineHeight: '35px', paddingLeft: '10px'
                    }}>
                        统计图表
                    </div>
                    <div style={{ marginTop: 10 }}>
                        {dataArr.length ?
                            <ReactEcharts
                                option={this.getOptionTow()}
                                notMerge={true}
                                lazyUpdate={true}
                                theme={"theme_name"}
                                style={{ flex: 1 }}
                            ></ReactEcharts> : null}
                    </div>
                </div>
                <Modal
                    visible={this.state.modal}
                    transparent
                    maskClosable={false}
                >
                    <List style={{ backgroundColor: 'white' }}>
                        <DatePicker
                            mode="month"
                            extra="请选择"
                            value={this.state.month}
                            format="YYYY-MM"
                            onChange={date => {
                                this.setState({ month: date })
                            }}
                        >
                            <Flex direction={'row'} justify={'between'} style={{ padding: '10px' }}>
                                <div>月份</div>
                                <div>{moment(this.state.month).format('YYYY-MM')}</div>
                            </Flex>
                        </DatePicker>
                    </List>
                    <div className={s.seachButtonView}>
                        <Button style={{ marginRight: '20px', background: '#eee' }} inline onClick={() => { this.setState({ modal: false }) }}>取消</Button>
                        <Button
                            style={{ marginLeft: '20px' }} type="primary" inline
                            onClick={() => {
                                this.setState({ modalTop: true }, () => {
                                    this.getData(this.state.month)
                                })
                            }}
                        >确定</Button>
                    </div>
                </Modal>
            </div>
        );
    }
}
export default index;
