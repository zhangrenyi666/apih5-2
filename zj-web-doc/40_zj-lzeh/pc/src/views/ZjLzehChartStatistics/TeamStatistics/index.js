import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import QnnForm from "../../modules/qnn-table/qnn-form";
import { message as Msg, Button, Table } from 'antd';
import ReactEcharts from 'echarts-for-react';
class index extends Component {
    constructor(props) {
        super(props);
        this.state = {
            fraction: [2.0, 4.9, 7.0, 23.2, 25.6, 76.7, 135.6, 162.2, 32.6, 20.0, 6.4, 3.3],
            average: [2.0, 2.2, 3.3, 4.5, 6.3, 10.2, 20.3, 23.4, 23.0, 16.5, 12.0, 6.2],
            xAxisData: [],
            queryDataOfDateCompFunction: undefined
        }
    }

    componentDidMount = () => {

    }
    setChartsDataFunc = () => {

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
                    dataView: {
                        show: true, readOnly: false, optionToContent: function (opt) {
                            var axisData = opt.xAxis[0].data;
                            var series = opt.series;
                            var table = '<table class="layui-table" style="width:100%;text-align:center"><tbody><tr>'
                                + '<td>班组名称</td>'
                                + '<td>' + series[0].name + '</td>'
                                + '<td>' + series[1].name + '</td>'
                                + '</tr>';
                            for (var i = 0, l = axisData.length; i < l; i++) {
                                table += '<tr>'
                                    + '<td>' + axisData[i] + '</td>'
                                    + '<td>' + series[0].data[i] + '</td>'
                                    + '<td>' + series[1].data[i] + '</td>'
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
                data: ['分数', '平均分']
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
        const { queryDataOfDateCompFunction, xAxisData, fraction, average } = this.state
        return (
            < div >
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
                                                            queryDataOfDateCompFunction: val.month
                                                        })
                                                        // this.tableOne.refresh()
                                                    })
                                                    // if (queryDataOfDateCompFunction === val.month && !this.tableOne.getTableData().length) {
                                                    //     Msg.warning('当前月份暂无数据')
                                                    // }

                                                    if (queryDataOfDateCompFunction === val.month && !this.tableOne.state.data.length) {
                                                        this.setState({
                                                            xAxisData: []
                                                        })
                                                        Msg.warning('当前月份暂无数据')
                                                    }
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
                            rowKey: 'zjLzehTeamItemId',
                            size: 'small'
                        }
                    }
                    wrappedComponentRef={(me) => {
                        this.tableOne = me;
                    }}
                    isShowRowSelect={false}
                    fetchConfig={
                        queryDataOfDateCompFunction ? {
                            apiName: 'getZjLzehTeamScoreItemgetChartByMonth',
                            otherParams: {
                                scoreMonth: queryDataOfDateCompFunction
                            },
                            success: (res) => {
                                const teamList = []
                                const fraction = []
                                const average = []
                                const { data } = res
                                if (data.length) {
                                    data.map(item => {
                                        teamList.push(item.teamName)
                                        fraction.push(item.monthScore)
                                        average.push(item.avgScore)
                                    })
                                    this.setState({
                                        xAxisData: [...teamList],
                                        fraction: [...fraction],
                                        average: [...average]
                                    })
                                } else {
                                    Msg.warning('当前月份暂无数据')
                                    this.setState({
                                        xAxisData: [],
                                    })
                                }
                            }
                        } : { apiName: 'getZjLzehTeamScoreItemgetChartByMonth', }
                    }
                    formConfig={[
                        {
                            isInTable: false,
                            form: {
                                field: 'zjLzehTeamItemId',
                                type: 'string',
                                hide: true,
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                field: 'zjLzehTeamScoreId',
                                type: 'string',
                                hide: true,
                            }
                        },
                        {
                            table: {
                                title: '本月排名',
                                dataIndex: 'monthRank',
                                key: 'monthRank',
                                width: 150,
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '劳务公司',
                                dataIndex: 'companyName',
                                key: 'companyName',
                                width: 150,
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '班组名称',
                                // tooltip: 7,
                                dataIndex: 'teamName',
                                key: 'teamName',
                                // width: 150,
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '月度评分',
                                dataIndex: 'monthScore',
                                key: 'monthScore',
                                width: 150,
                            },
                            isInForm: false
                        },
                    ]}
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
            </div >
        )
    }
}

export default index