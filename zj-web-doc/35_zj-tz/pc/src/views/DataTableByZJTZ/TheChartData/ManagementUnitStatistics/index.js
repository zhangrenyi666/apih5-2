import React, { Component } from "react";
import QnnForm from "../../../modules/qnn-form";
import ReactEcharts from 'echarts-for-react';
import s from './style.less';
import { Spin, Radio } from "antd";
import moment from 'moment';
class index extends Component {
    constructor(props) {
        super(props);
        this.state = {
            loading: false,
            data: [],
            tjType: '1'
        }
    }
    componentDidMount() {
        this.refresh();
    }
    refresh = () => {
        this.setState({
            loading: true
        })
        const { tjType } = this.state;
        let formData = this.formW?.form?.getFieldsValue?.();
        if (formData) {
            formData.period = moment(formData.period).valueOf();
        } else {
            formData = {
                period: moment().valueOf()
            }
        }
        let body = {
            period: tjType === '1' ? null : formData.period,
            tjType: tjType
        }
        this.props.myFetch('getHomeChartComnameStatis', body).then(
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
        const { data, tjType } = this.state;
        let option = {
            backgroundColor: 'rgba(0,0,0,0.5)',
            title: {
                text: tjType === '1' ? "单位：个" : "单位：万元",
                textStyle: {
                    fontSize: 14,
                    color: "rgba(255,255,255,0.5)"
                }
            },
            color: ['#1890ff'],
            tooltip: {
                trigger: 'axis'
            },
            grid: {
                left: '5%',
                bottom: '5%',
                top: '12%',
                right: '5%',
                containLabel: true,
            },
            xAxis: {
                type: 'category',
                axisTick: {
                    show: false,
                },
                axisLabel: {
                    interval: 0,
                    fontSize:14,
                    // formatter: function (params) {
                    //     var newParamsName = "";// 最终拼接成的字符串
                    //     var paramsNameNumber = params.length;// 实际标签的个数
                    //     var provideNumber = 4;// 每行能显示的字的个数
                    //     var rowNumber = Math.ceil(paramsNameNumber / provideNumber);// 换行的话，需要显示几行，向上取整
                    //     /**
                    //      * 判断标签的个数是否大于规定的个数， 如果大于，则进行换行处理 如果不大于，即等于或小于，就返回原标签
                    //      */
                    //     // 条件等同于rowNumber>1
                    //     if (paramsNameNumber > provideNumber) {
                    //         /** 循环每一行,p表示行 */
                    //         for (var p = 0; p < rowNumber; p++) {
                    //             var tempStr = "";// 表示每一次截取的字符串
                    //             var start = p * provideNumber;// 开始截取的位置
                    //             var end = start + provideNumber;// 结束截取的位置
                    //             // 此处特殊处理最后一行的索引值
                    //             if (p == rowNumber - 1) {
                    //                 // 最后一次不换行
                    //                 tempStr = params.substring(start, paramsNameNumber);
                    //             } else {
                    //                 // 每一次拼接字符串并换行
                    //                 tempStr = params.substring(start, end) + "\n";
                    //             }
                    //             newParamsName += tempStr;// 最终拼成的字符串
                    //         }

                    //     } else {
                    //         // 将旧标签的值赋给新标签
                    //         newParamsName = params;
                    //     }
                    //     //将最终的字符串返回
                    //     return newParamsName
                    // }
                },
                axisLine: {
                    show: false,
                    lineStyle: {
                        color: 'rgba(255,255,255,0.5)'
                    }
                },
                data: data.map((item) => {
                    return item.comname;
                })
            },
            yAxis: {
                show: false
            },
            series: [
                {
                    name: tjType === '1' ? '数量' : '金额',
                    type: 'bar',
                    data: data.map((item) => {
                        if(tjType === '1'){
                           return item.count; 
                        }else if(tjType === '2'){
                            return item.xmzht;
                        }else if(tjType === '3'){
                            return item.xmzja;
                        }
                    }),
                    itemStyle: {
                        color: '#1890ff'
                    },
                    label: {
                        show: true,
                        position: 'top',
                        fontSize:14,
                        fontWeight:'bold'
                    }
                }
            ]
        };
        return option;
    }
    radioOnChange = (e) => {
        this.setState({ 
            tjType: e.target.value
        },() => {
            this.refresh();
        });
    }
    render() {
        const {
            loading, tjType
        } = this.state;
        return (
            <div className={s.ManagementUnitStatistics}>
                <div className={s.leftTop}>
                    <div className={s.leftTopOneL}>
                        管理单位统计
                    </div>
                    {/* <div className={s.leftTopOneC}>
                        {tjType === '1' ? '单位：个' : '单位：万元'}
                    </div> */}
                    <div className={s.leftTopOneR}>
                        {tjType === '1' ? null : <QnnForm
                            fetch={this.props.myFetch}
                            upload={this.props.myUpload}
                            headers={{
                                token: this.props.loginAndLogoutInfo.loginInfo.token
                            }}
                            wrappedComponentRef={(me) => {
                                this.formW = me;
                            }}
                            formItemLayout={{
                                labelCol: {
                                    xs: { span: 8 },
                                    sm: { span: 8 }
                                },
                                wrapperCol: {
                                    xs: { span: 16 },
                                    sm: { span: 16 }
                                }
                            }}
                            formConfig={[
                                {
                                    label: '年月',
                                    field: 'period',
                                    type: 'month',
                                    onChange: () => {
                                        this.refresh();
                                    },
                                    allowClear: false,
                                    initialValue: moment().month(moment().month() - 1).startOf('month').valueOf(),
                                    placeholder: '请选择'
                                }
                            ]}
                        />}
                    </div>
                    <div className={s.leftTopOneLR}>
                        <Radio.Group value={tjType} onChange={this.radioOnChange}>
                            <Radio.Button style={{width:'120px',textAlign:'center'}} value="1">项目数量</Radio.Button>
                            <Radio.Button style={{width:'120px',textAlign:'center'}} value="2">项目总合同额</Radio.Button>
                            <Radio.Button style={{width:'120px',textAlign:'center'}} value="3">项目总建安费</Radio.Button>
                        </Radio.Group>
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