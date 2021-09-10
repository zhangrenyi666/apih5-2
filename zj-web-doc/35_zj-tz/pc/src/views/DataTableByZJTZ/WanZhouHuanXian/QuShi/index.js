import React, { Component } from "react";
import s from './style.less';
import QnnForm from "../../../modules/qnn-form";
import { Spin } from "antd";
import moment from 'moment';
import ReactEcharts from 'echarts-for-react';
class index extends Component {
    constructor(props) {
        super(props);
        this.state = {
            loading: false,
            xAxisTwoNew: [],
            legendDatanew: [],
            dataval: [],
            selectData: '0',
            seriesData: [],
            clickRoter: props.clickRoter,
            optionData: props.clickRoter === 'jianshe' ? [
                {
                    label: '本项目每年每季投资完成情况',
                    value: '0'
                },
                {
                    label: '本项目所选年份完成投资开累数据',
                    value: '1'
                }
            ] : (props.clickRoter === 'yunying' ? [{
                label: '本项目每年每季投资完成情况',
                value: '0'
            },
            {
                label: '本项目所选年份完成投资开累数据',
                value: '1'
            },
            {
                label: '本项目所选年份当年运营情况',
                value: '2'
            },
            {
                label: '本项目所选年份开累运营情况',
                value: '3'
            }] : [
                    {
                        label: '本项目每年每季投资完成情况',
                        value: '0'
                    },
                    {
                        label: '本项目所选年份完成投资开累数据',
                        value: '1'
                    },
                    {
                        label: '本项目所选年份当年回购情况',
                        value: '2'
                    },
                    {
                        label: '本项目所选年份开累回购情况',
                        value: '3'
                    }
                ])
        }
    }
    componentDidMount() {
        this.refresh();
    }
    refresh = () => {
        const { projectId } = this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany;
        let formData = this.formAC?.form?.getFieldsValue?.();
        let startYear = '';
        let endYear = '';
        if (formData) {
            startYear = moment(formData.periodValue[0]._d).valueOf();
            endYear = moment(formData.periodValue[1]._d).valueOf();
        } else {
            startYear = moment(moment().year(moment().year() - 1).startOf('year')).valueOf();
            endYear = moment().valueOf();
        }
        let body = {
            startYear: startYear,
            endYear: endYear,
            data: formData ? formData.data : '0',
            projectId: projectId
        }
        this.setState({
            loading: true,
            selectData: formData ? formData.data : ''
        })
        this.props.myFetch('getConstructPageTrendData', { ...body }).then(
            ({ success, message, data }) => {
                if (success) {
                    let dataval = data;
                    let legendDatanew = [];
                    let xAxisTwoNew = [];
                    let seriesData = [];
                    for (let k = 0; k < dataval[0].DataThree.length; k++) {
                        legendDatanew.push(dataval[0].DataThree[k].name);
                    }
                    for (let j = 0; j < dataval[0].DataTwo.length; j++) {
                        xAxisTwoNew.push({
                            value: dataval[0].DataTwo[j].value,
                            textStyle: {
                                fontSize: 16,
                                lineHeight: 100
                            }
                        })
                    }
                    seriesData = [
                        {
                            name: dataval[0] ? dataval[0].DataThree[0].name : '',
                            type: 'line',
                            stack: '总量',
                            symbolSize: 8,
                            symbol: 'circle',
                            smooth: true,
                            color: '#fbd965',
                            label: {
                                normal: {
                                    show: false,
                                    position: 'top'
                                }
                            },
                            lineStyle: {
                                normal: {
                                    // color: '#467d7b'
                                }
                            },
                            data: dataval[0] ? dataval[0].DataThree[0].tzwckl : []
                        },
                        {
                            name: dataval[0] ? dataval[0].DataThree[1].name : '',
                            type: 'line',
                            stack: '总量1',
                            symbol: 'circle',
                            smooth: true,
                            color: '#21dde0',
                            label: {
                                normal: {
                                    show: false,
                                    position: 'top'
                                }
                            },
                            lineStyle: {
                                normal: {
                                    // color: '#f85489'
                                }
                            },
                            data: dataval[0] ? dataval[0].DataThree[1].wcjafkl : []
                        }
                    ];
                    this.setState({
                        loading: false,
                        dataval: dataval,
                        legendData: legendDatanew,
                        xAxisTwoNew: xAxisTwoNew,
                        seriesData: seriesData
                    })
                } else {
                    this.setState({
                        loading: false,
                    })
                }
            }
        );
    }
    getOptionOne = () => {
        const { legendData, dataval, xAxisTwoNew, selectData, seriesData } = this.state;

        const optionYear = {
            backgroundColor: 'rgba(0,0,0,0.5)',
            grid: {
                left: 10,
                right: 10,
                top: 60,
                bottom:10,
                containLabel: true
            },
            tooltip: {
                trigger: 'axis',
                axisPointer: {
                    type: 'shadow',
                    label: {
                        show: true,
                        backgroundColor: '#333'
                    }
                }
            },
            legend: {
                show: true,
                x: 'right',
                top: '20',
                textStyle: {
                    color: 'white'
                },
                data: legendData ? legendData : []
            },
            xAxis: [
                {
                    type: 'category',
                    data: dataval[0] ? dataval[0].DataOne : [],
                    axisLabel: {
                        color: 'rgba(255,255,255,0.5)',
                        interval: 0,
                        // formatter: function (value) {
                        //     var str = value.split("");
                        //     return str.join("\n");
                        // }
                    },
                    splitArea: {
                        show: false
                    },
                    splitLine: {
                        lineStyle: {
                            color: '#1890ff'
                        }
                    },
                    axisTick: {
                        show: false,
                    },
                    axisLine: {
                        show:false,
                        lineStyle: {
                            color: 'rgba(24,144,255,1)'
                        }
                    },
                }
            ],
            yAxis: [{
                type: 'value',
                name: '单位：万元',
                nameTextStyle: {
                    color: "rgba(255,255,255,0.5)",
                    fontSize: 14,
                },
                splitArea: {
                    show: false
                },
                splitLine: {
                    lineStyle: {
                        color: '#1890ff'
                    }
                },
                axisLine: {
                    show:false,
                    lineStyle: {
                        color: 'rgba(24,144,255,1)'
                    }
                },

                axisLabel: {
                    color: 'rgba(255,255,255,0.5)'
                },
            }],
            series: seriesData
        };
        const optionSeason = {
            backgroundColor: 'rgba(0,0,0,0.5)',
            grid: {
                left: 10,
                right: 10,
                top: 60,
                bottom:10,
                containLabel: true
            },
            tooltip: {
                trigger: 'axis',
                axisPointer: {
                    type: 'shadow',
                    label: {
                        show: true,
                        backgroundColor: '#333'
                    }
                }
            },
            legend: {
                icon: "circle",
                show: true,
                x: 'right',
                top: '20',
                itemHeight: 10,
                itemWidth: 10,
                textStyle: {
                    color: 'white'
                },
                data: legendData ? legendData : []
            },
            xAxis: [
                {
                    type: 'category',
                    data: dataval[0] ? dataval[0].DataOne : [],
                    axisLabel: {
                        color: 'rgba(255,255,255,0.5)',
                        interval: 0,
                        formatter: function (value) {
                            var str = value.split("");
                            return str.join("\n");
                        }
                    },
                    splitArea: {
                        show: false
                    },
                    splitLine: {
                        lineStyle: {
                            color: '#1890ff'
                        }
                    },
                    axisTick: {
                        show: false,
                    },
                    axisLine: {
                        show:false,
                        lineStyle: {
                            color: 'rgba(24,144,255,1)'
                        }
                    },
                },
                {
                    type: 'category',
                    data: xAxisTwoNew ? xAxisTwoNew : [],
                    position: 'bottom',
                    axisLabel: {
                        color: 'rgba(255,255,255,0.5)'
                    },
                    splitArea: {
                        show: false
                    },
                    splitLine: {
                        lineStyle: {
                            color: '#1890ff'
                        }
                    },
                    axisLine: {
                        show:false,
                        lineStyle: {
                            color: 'rgba(24,144,255,1)'
                        }
                    },
                    axisTick: {
                        show: false,
                    },
                }
            ],
            yAxis: [{
                type: 'value',
                name: '单位：万元',
                nameTextStyle: {
                    color: "rgba(255,255,255,0.5)",
                    fontSize: 14,
                },
                splitArea: {
                    show: false
                },
                splitLine: {
                    lineStyle: {
                        color: '#1890ff'
                    }
                },
                axisLine: {
                    show:false,
                    lineStyle: {
                        color: 'rgba(24,144,255,1)'
                    }
                },

                axisLabel: {
                    color: 'rgba(255,255,255,0.5)'
                },
            }],
            series: seriesData
        };

        return dataval[0] ? (selectData === '1' ? optionYear : optionSeason) : {}
        // return optionSeason
    }
    render() {
        const {
            loading, optionData
        } = this.state;
        return (
            <div className={s.AchievementOfAnnualTargets}>
                <div className={s.leftTop}>
                    <div className={s.leftTopOneL}>
                        趋势数据
                    </div>
                    {/* <div className={s.leftTopOneC}>
                        单位：万元
                    </div> */}
                    <div className={s.leftTopOneR}>
                        <QnnForm
                            fetch={this.props.myFetch}
                            upload={this.props.myUpload}
                            headers={{
                                token: this.props.loginAndLogoutInfo.loginInfo.token
                            }}
                            wrappedComponentRef={(me) => {
                                this.formAC = me;
                            }}
                            formConfig={[
                                {
                                    label: '',
                                    field: 'data',
                                    type: 'select',
                                    initialValue: '0',
                                    optionConfig: {
                                        label: 'label',
                                        value: 'value'
                                    },
                                    optionData: optionData,
                                    allowClear: false,
                                    onChange: () => {
                                        this.refresh();
                                    },
                                    span: 14,
                                    placeholder: '请选择'
                                },
                                {
                                    label: '',
                                    field: 'periodValue',
                                    type: 'rangeDate',
                                    format: 'YYYY',
                                    picker: 'year',
                                    initialValue: [moment(moment().year(moment().year() - 1).startOf('year').format('YYYY'), 'YYYY'), moment(moment().format('YYYY'), 'YYYY')],
                                    onChange: () => {
                                        this.refresh();
                                    },
                                    span: 10,
                                    allowClear: false,
                                    placeholder: '请选择'
                                }
                            ]}
                        />
                    </div>
                </div>
                <div className={s.leftBottom}>
                    <Spin spinning={loading}>
                        <ReactEcharts
                            style={{ height: "100%" }}
                            option={this.getOptionOne()}
                            notMerge={true}
                            lazyUpdate={true}
                            theme={"theme_name"}
                        />
                    </Spin>
                </div>
            </div>
        );
    }
}

export default index;