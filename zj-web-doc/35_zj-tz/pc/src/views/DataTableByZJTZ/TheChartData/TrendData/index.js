import React, { Component } from "react";
import QnnForm from "../../../modules/qnn-form";
import ReactEcharts from 'echarts-for-react';
import s from './style.less';
import { Spin } from "antd";
import moment from 'moment';
class index extends Component {
    constructor(props) {
        super(props);
        this.state = {
            loading: false,
            data: [],
            dataType: '1'
        }
    }
    componentDidMount() {
        this.refresh();
    }
    refresh = () => {
        let formData = this.formW?.form?.getFieldsValue?.() || {};
        if (formData?.periodValue) {
            formData.startYear = moment(formData.periodValue[0]._d).valueOf();
            formData.endYear = moment(formData.periodValue[1]._d).valueOf();
        } else {
            formData.startYear = moment(moment().year(moment().year() - 5).startOf('year')).valueOf();
            formData.endYear = moment().valueOf();
            formData.data = '1';
        }
        let body = {
            startYear: formData.startYear,
            endYear: formData.endYear,
            data: formData.data
        };
        this.setState({
            loading: true
        })
        this.props.myFetch('getHomeChartTrendData', body).then(
            ({ success, message, data }) => {
                if (success) {
                    this.setState({
                        loading: false,
                        data: data
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
        const { data, dataType } = this.state;
        let option = {
            backgroundColor: 'rgba(0,0,0,0.5)',
            title: {
                top: '2%',
                text: "单位：万元",
                textStyle: {
                    fontSize: 14,
                    color: "rgba(255,255,255,0.5)"
                }
            },
            color: ['#1890ff', '#10cf9b', '#ff6d18', '#ffcd18'],
            tooltip: {
                trigger: 'axis',
                position: function (point) {
                    return [0, point[1]];
                },
                formatter: (params) => {
                    let _params = '';
                    let percentage = 0;
                    params.map((item, index) => {
                        if(index === 0){
                            percentage = data?.[0]?.DataThree?.[index]?.tbamount?.[params?.[index]?.dataIndex] || 0
                        }else if(index === 1){
                            percentage = data?.[0]?.DataThree?.[index]?.tbtzwc?.[params?.[index]?.dataIndex] || 0
                        }else if(index === 2){
                            percentage = data?.[0]?.DataThree?.[index]?.tbqyamount?.[params?.[index]?.dataIndex] || 0
                        }else if(index === 3){
                            percentage = data?.[0]?.DataThree?.[index]?.tbqytzwc?.[params?.[index]?.dataIndex] || 0
                        }
                        if(percentage < 0){
                            _params += (item.marker + item.seriesName + '：' + item.value + ' 同比减少：' + Math.abs(percentage) + '%' + '<br>')
                        }else{
                            _params += (item.marker + item.seriesName + '：' + item.value + ' 同比增长：' + percentage + '%' + '<br>');  
                        }
                    })
                    return _params;
                },
            },
            grid: {
                left: '5%',
                bottom: '15%',
                top: '10%',
                right: '5%',
                containLabel: true,
            },
            legend: [
                {
                    bottom: 25,
                    textStyle: {
                        color: 'white'
                    },
                    data: [data?.[0]?.DataThree?.[0]?.name,data?.[0]?.DataThree?.[1]?.name]
                },
                {
                    bottom: 5,
                    textStyle: {
                        color: 'white'
                    },
                    data: [data?.[0]?.DataThree?.[2]?.name,data?.[0]?.DataThree?.[3]?.name]
                }
            ],
            xAxis: [
                {
                    type: 'category',
                    axisLabel: {
                        interval: 0
                    },
                    axisLine: {
                        lineStyle: {
                            color: 'rgba(255,255,255,0.5)'
                        }
                    },
                    data: data?.[0]?.DataOne || []
                }
            ],
            yAxis: [
                {
                    type: 'value',
                    axisLine: {
                        lineStyle: {
                            color: 'rgba(255,255,255,0.5)'
                        }
                    }
                }
            ],
            series: [
                {
                    name: data?.[0]?.DataThree?.[0]?.name || '',
                    type: 'line',
                    smooth: true,
                    data: data?.[0]?.DataThree?.[0]?.hte || []
                },
                {
                    name: data?.[0]?.DataThree?.[1]?.name || '',
                    type: 'line',
                    smooth: true,
                    data: data?.[0]?.DataThree?.[1]?.tze || []
                },
                {
                    name: data?.[0]?.DataThree?.[2]?.name || '',
                    type: 'line',
                    smooth: true,
                    data: data?.[0]?.DataThree?.[2]?.qyhte || []
                },
                {
                    name: data?.[0]?.DataThree?.[3]?.name || '',
                    type: 'line',
                    smooth: true,
                    data: data?.[0]?.DataThree?.[3]?.qytze || []
                }
            ]
        };
        return option;
    }
    render() {
        const {
            loading,
            dataType
        } = this.state;
        return (
            <div className={s.TrendData}>
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
                                this.formW = me;
                            }}
                            formConfig={[
                                {
                                    label: '',
                                    field: 'data',
                                    type: 'select',
                                    optionConfig: {
                                        label: 'label',
                                        value: 'value'
                                    },
                                    optionData: [
                                        {
                                            label: '当年数据',
                                            value: '1'
                                        },
                                        {
                                            label: '开累数据',
                                            value: '2'
                                        }
                                    ],
                                    initialValue: dataType,
                                    allowClear: false,
                                    span: 12,
                                    onChange: (val) => {
                                        this.setState({
                                            dataType: val
                                        }, () => {
                                            this.refresh();
                                        })
                                    },
                                    placeholder: '请选择'
                                },
                                {
                                    label: '',
                                    field: 'periodValue',
                                    type: 'rangeDate',
                                    onChange: () => {
                                        this.refresh();
                                    },
                                    span: 12,
                                    format: 'YYYY',
                                    picker: 'year',
                                    allowClear: false,
                                    initialValue: [moment(moment().year(moment().year() - 5).startOf('year').format('YYYY'), 'YYYY'), moment(moment().format('YYYY'), 'YYYY')],
                                    placeholder: '请选择'
                                }
                            ]}
                        />
                    </div>
                </div>
                <div className={s.leftBottom}>
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
            </div>
        );
    }
}

export default index;