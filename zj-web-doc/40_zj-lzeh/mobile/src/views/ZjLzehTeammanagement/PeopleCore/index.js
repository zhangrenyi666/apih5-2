import React, { Component } from "react";
import s from "./index.less"
import { NavBar, Icon, Toast, List, Button, Modal, Flex, DatePicker } from "antd-mobile"
import moment from 'moment';
import { Table } from 'antd';
import ReactEcharts from 'echarts-for-react';
import { push } from "react-router-redux";
class index extends Component {
    constructor(props) {
        super(props)
        this.state = {
            dataArr: [],
            modal: false,
            modalTop: false,
            month: new Date(),
            allqty: [],
            cqty: [],
            avgRate: [],
            cRate: [],
            xAxisData: [],
        }
    }
    componentDidMount() {
        document.title = "任务人员统计表";
        this.getData()
    }
    getData() {
        this.props.myFetch('getTaskCensusItemChartByCenMonth', {
            cenMonth: moment(this.state.month).valueOf()
        }).then(({ data, success, message }) => {
            if (success) {
                this.setState({ dataArr: data, modal: false })
                if (data.length) {
                    let list = {
                        xData: [],
                        allqty: [],
                        cqty: [],
                        avgRate: [],
                        cRate: [],
                    }
                    data.map(item => {
                        list.xData.push(item.personName)
                        list.allqty.push(item.allqty)
                        list.cqty.push(item.cqty)
                        list.avgRate.push(item.avgRate)
                        list.cRate.push(item.cRate)
                    })
                    this.setState({
                        xAxisData: [...list.xData],
                        allqty: [...list.allqty],
                        cqty: [...list.cqty],
                        avgRate: [...list.avgRate],
                        cRate: [...list.cRate],
                        current: 1
                    })
                } else {
                    Toast.fail('当月暂无数据', 1);
                }
            } else {
                this.setState({ modal: false })
                Toast.fail(message, 1);
            }
        })
    }
    getOptionTow = () => {
        const { xAxisData, allqty, cqty, avgRate, cRate } = this.state;
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
                },
                left: 'right',
                bottom: '10px'
            },
            legend: {
                data: ['任务数', '完成数', '完成比例', '平均比例']
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
                    name: '任务数',
                    min: 0,
                    max: 100,
                    interval: 50,
                    axisLabel: {
                        formatter: '{value} '
                    }
                },
                {
                    type: 'value',
                    name: '比例',
                    min: 0,
                    max: 100,
                    interval: 10,
                    axisLabel: {
                        formatter: '{value} %'
                    }
                }
            ],
            series: [
                {
                    name: '任务数',
                    type: 'bar',
                    data: allqty
                },
                {
                    name: '完成数',
                    type: 'bar',
                    data: cqty
                },
                {
                    name: '完成比例',
                    type: 'line',
                    yAxisIndex: 1,
                    data: cRate
                },
                {
                    name: '平均比例',
                    type: 'line',
                    yAxisIndex: 1,
                    data: avgRate,
                }
            ]
        };
        return option;
    }
    render() {
        const {
            mainModule
        } = this.props.myPublic.appInfo;
        
        const columns = [
            {
                title: '本月排名',
                dataIndex: 'xuhao',
                width: 100,
                key: 'xuhao',
            },
            {
                title: '人员姓名',
                dataIndex: 'personName',
                width: 150,
                key: 'personName',
            },
            {
                title: '计划任务总数',
                dataIndex: 'allqty',
                width: 120,
                key: 'allqty',
            },
            {
                title: '完成任务总数',
                dataIndex: 'cqty',
                width: 120,
                key: 'cqty',

            },
            {
                title: '未完成任务总数',
                dataIndex: 'qty',
                width: 140,
                key: 'qty',
            },
            {
                title: '任务完成比例',
                dataIndex: 'cRate',
                width: 120,
                key: 'cRate',
                render: val => {
                    return <div >{val ? val + '%' : '0%'}</div>
                }
            },
        ];
        const { dataArr, current } = this.state
        return (
            <div className={s.root}>
                <div>
                    <NavBar
                        style={{ width: "100%" }}
                        mode="dark"
                        icon={<Icon type="left" />}
                        onLeftClick={() => {
                            // this.props.history.goBack()
                            // this.props.dispatch(push(`${mainModule}App`));
                            this.props.myPublic?.androidApi?.closeActivity?.() || this.props.dispatch(push(`${mainModule}App`));
                        }}
                        rightContent={[
                            <Icon key="0" type="search" style={{ margin: '5px 5px 6px 5px', color: '#fff' }}
                                onClick={() => { this.setState({ modal: true }) }}
                            />

                        ]}
                    >
                        {"任务人员统计"}
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
                                    this.setState({ current: a })
                                }
                            }} scroll={{ x: 630 }}
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
