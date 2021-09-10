import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import QnnForm from "../../modules/qnn-table/qnn-form";
import { message as Msg, Button, Table } from 'antd';
import ReactEcharts from 'echarts-for-react';

class index extends Component {
    constructor(props) {
        super(props);
        this.state = {
            allqty: [],
            cqty: [],
            avgRate: [],
            cRate: [],
            xAxisData: [],
            cenMonth: undefined
        }
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
                    dataView: {
                        show: true, readOnly: false, optionToContent: function (opt) {
                            var axisData = opt.xAxis[0].data;
                            var series = opt.series;
                            var table = '<table class="layui-table" style="width:100%;text-align:center"><tbody><tr>'
                                + '<td>人员姓名</td>'
                                + '<td>' + series[0].name + '</td>'
                                + '<td>' + series[1].name + '</td>'
                                + '<td>' + series[2].name + '</td>'
                                + '<td>' + series[3].name + '</td>'
                                + '</tr>';
                            for (var i = 0, l = axisData.length; i < l; i++) {
                                table += '<tr>'
                                    + '<td>' + axisData[i] + '</td>'
                                    + '<td>' + series[0].data[i] + '</td>'
                                    + '<td>' + series[1].data[i] + '</td>'
                                    + '<td>' + series[2].data[i] + '</td>'
                                    + '<td>' + series[3].data[i] + '</td>'
                                    + '</tr>';
                            }
                            table += '</tbody></table>';
                            return table;
                        }
                    },
                    magicType: { show: true, type: ['line', 'bar'] },
                    restore: { show: true },
                    saveAsImage: { show: true }
                }
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
                    },
                    axisLabel: {
                        interval: 0,
                        formatter: function (value) {
                            return value.split("").join("\n");
                        }
                    },
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
        const { xAxisData, cenMonth } = this.state
        return (
            <div>
                <QnnForm
                    wrappedComponentRef={(me) => {
                        this.formOne = me;
                    }}
                    formConfig={
                        [
                            {
                                type: 'month',
                                label: '年月',
                                field: 'month', //唯一的字段名 ***必传
                                placeholder: '请选择',
                                required: false,
                                format: "YYYY-MM",
                                showTime: false, //不显示时间
                                scope: false, //是否可选择范围
                                span: 8,
                                onChange: (val, obj) => {
                                    if (!val) {
                                        this.setState({
                                            xAxisData: []
                                        })
                                    }
                                }
                            },
                            {
                                type: 'component',
                                field: 'diy',
                                span: 6,
                                //第一种，推荐定义方式 需要将componentsKey对象传到qnn-form
                                Component: "myDiyComponent",

                                //第二种自定义组件方式
                                Component: obj => {
                                    return (
                                        <div style={{ height: '100%', display: 'flex', alignItems: 'center' }}>
                                            <Button type="primary" onClick={() => {
                                                this.formOne.getValues().then(val => {
                                                    setTimeout(() => {
                                                        this.setState({
                                                            cenMonth: val.month
                                                        })

                                                        // if (cenMonth === val.month && !this.tableOne.getTableData().length) {
                                                        //     Msg.warning('当前月份暂无数据')
                                                        // }

                                                        if (cenMonth === val.month && !this.tableOne.state.data.length) {
                                                            setTimeout(() => {
                                                                this.setState({
                                                                    xAxisData: []
                                                                })
                                                                Msg.warning('当前月份暂无数据2')
                                                            }, 0)

                                                        }
                                                        // this.tableOne.refresh()
                                                    })
                                                })
                                            }}>查询</Button>
                                        </div>
                                    );
                                }
                            }
                        ]
                    }
                ></QnnForm>
                <QnnTable
                    {...this.props}
                    fetch={this.props.myFetch}
                    upload={this.props.myUpload}
                    antd={
                        {
                            rowKey: 'zjLzehTaskCensusItemId',
                            size: 'small'
                        }
                    }
                    wrappedComponentRef={(me) => {
                        this.tableOne = me;
                    }}
                    isShowRowSelect={false}
                    fetchConfig={
                        cenMonth ? {
                            apiName: 'getTaskCensusItemChartByCenMonth',
                            otherParams: {
                                cenMonth
                            },
                            success: (res) => {
                                const list = {
                                    xData: [],
                                    allqty: [],
                                    cqty: [],
                                    avgRate: [],
                                    cRate: [],
                                }
                                // new common
                                // const {data} = res.response
                                const { data } = res
                                if (data.length) {
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
                                    })
                                } else {
                                    Msg.warning('当前月份暂无数据')
                                    this.setState({
                                        xAxisData: []
                                    })
                                }
                            }
                        } : { apiName: 'getTaskCensusItemChartByCenMonth', }
                    }

                    formConfig={
                        [
                            {
                                isInTable: false,
                                form: {
                                    field: 'zjLzehTaskCensusItemId',
                                    type: 'string',
                                    hide: true,
                                }
                            },
                            {
                                isInTable: false,
                                form: {
                                    field: 'zjLzehTaskCensusId',
                                    type: 'string',
                                    hide: true,
                                }
                            },
                            {
                                table: {
                                    title: '本月排名',
                                    dataIndex: 'xuhao',
                                    key: 'xuhao',
                                    width: 150,
                                },
                                isInForm: false
                            },
                            {
                                table: {
                                    title: '人员姓名',
                                    dataIndex: 'personName',
                                    key: 'personName',
                                    width: 150,
                                },
                                isInForm: false
                            },
                            {
                                table: {
                                    title: '计划任务总数',
                                    dataIndex: 'allqty',
                                    key: 'allqty',
                                    width: 150,
                                },
                                isInForm: false
                            },
                            {
                                table: {
                                    title: '完成任务总数',
                                    dataIndex: 'cqty',
                                    key: 'cqty',
                                    width: 150,
                                },
                                isInForm: false
                            },
                            {
                                table: {
                                    title: '未完成任务总数',
                                    dataIndex: 'qty',
                                    key: 'qty',
                                    width: 150,
                                },
                                isInForm: false
                            },
                            {
                                table: {
                                    title: '任务完成比例',
                                    dataIndex: 'cRate',
                                    key: 'cRate',
                                    width: 150,
                                    render: val => {
                                        return val ? val + '%' : '0%'
                                    }
                                },
                                isInForm: false
                            },
                        ]
                    }
                ></QnnTable>
                <div style={{ marginTop: 150 }}>
                    {xAxisData.length ?
                        <ReactEcharts
                            option={this.getOptionTow()}
                            notMerge={true}
                            lazyUpdate={true}
                            theme={"theme_name"}
                        ></ReactEcharts> : null}
                </div>
            </div>
        )
    }
}

export default index