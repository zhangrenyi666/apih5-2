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
            data: null
        }
    }
    componentDidMount() {
        this.refresh();
    }
    refresh = () => {
        let formData = this.formAC?.form?.getFieldsValue?.();
        if (formData) {
            formData.periodValue = moment(formData.periodValue).format('YYYY');
        } else {
            formData = {
                periodValue: moment().format('YYYY')
            }
        }
        let body = {
            periodValue: formData.periodValue
        }
        this.setState({
            loading: true
        })
        this.props.myFetch('getHomeProgressWarningCompleteStatus', { ...body }).then(
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
        const { data } = this.state;
        const option = {
            backgroundColor: 'rgba(0,0,0,0.5)',
            tooltip: {
                trigger: 'axis',
                axisPointer: {
                    type: 'none'

                }
            },
            xAxis: {
                show: false
            },
            yAxis: {
                type: 'category',
                inverse: 'true', //排序
                data: ['本年计划权益投资总额', '本年完成权益投资总额', '本年计划局建安费', '本年完成局建安费',],
                axisLabel: {
                    show: true,
                    textStyle: {
                        color: 'rgba(255,255,255,0.5)'
                    },
                    interval: 0,
                    formatter: function (value) {
                        var ret = "";//拼接加\n返回的类目项  
                        var maxLength = 4;//每项显示文字个数  
                        var valLength = value.length;//X轴类目项的文字个数  
                        var rowN = Math.ceil(valLength / maxLength); //类目项需要换行的行数  
                        if (rowN > 1)//如果类目项的文字大于3,  
                        {
                            for (var i = 0; i < rowN; i++) {
                                var temp = "";//每次截取的字符串  
                                var start = i * maxLength;//开始截取的位置  
                                var end = start + maxLength;//结束截取的位置  
                                //这里也可以加一个是否是最后一行的判断，但是不加也没有影响，那就不加吧  
                                temp = value.substring(start, end) + "\n";
                                ret += temp; //凭借最终的字符串  
                            }
                            return ret;
                        }
                        else {
                            return value;
                        }
                    }
                },
                axisLine: {
                    show: true,
                    lineStyle: {
                        color: 'white',
                    }
                },
            },
            grid: {
                left: 5,
                right: 100,
                top: 20,
                bottom: 5,
                containLabel: true
            },
            series: [{
                data: [
                    {
                        value: data?.bnjhwcqytz || 0,
                        itemStyle: {
                            color: "#1890cc"
                        }
                    },
                    {
                        value: data?.qytzwcbn || 0,
                        itemStyle: {
                            color: "#00ffea"
                        }
                    },
                    {
                        value: data?.ygjbnjhwcja || 0,
                        itemStyle: {
                            color: "#075dd4"
                        }
                    },
                    {
                        value: data?.ygjjtwcjafbn || 0,
                        itemStyle: {
                            color: "#00e9ff"
                        }
                    }
                ],
                type: 'bar',
                itemStyle: {
                    normal: {
                        label: {
                            show: true,
                            position: 'right',
                            textStyle: {
                                fontWeight: 'bold',
                                fontSize: 14
                            }
                        }
                    }
                },
            }]
        };
        return option
    }
    render() {
        const {
            loading
        } = this.state;
        return (
            <div className={s.AchievementOfAnnualTargets}>
                <div className={s.leftTop}>
                    <div className={s.leftTopOneL}>
                        年度目标完成情况
                    </div>
                    <div className={s.leftTopOneC}>
                        单位：万元
                    </div>
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
                                    field: 'periodValue',
                                    type: 'year',
                                    initialValue: new Date(),
                                    onChange: () => {
                                        this.refresh();
                                    },
                                    allowClear: false,
                                    span: 24,
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