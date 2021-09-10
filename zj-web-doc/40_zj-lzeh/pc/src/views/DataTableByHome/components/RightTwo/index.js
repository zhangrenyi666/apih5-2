import React from "react";
import { Spin } from "antd";
import s from "./style.less";
import Apih5 from "qnn-apih5"
import ReactEcharts from 'echarts-for-react';

class Com extends Apih5 {
    state = {
        loading: true,
        data: {
            topData: [
                {
                    label: "参建单位数量",
                    value: "",
                    className: s.yellow
                },
                {
                    label: "在场人员数量",
                    value: "",
                    className: s.blue
                },
                {
                    label: "班组数量",
                    value: "",
                    className: s.green
                },
                {
                    label: "本月工资发放总和",
                    value: "",
                    className: s.orange
                }
            ],
            bottomData: [
                {
                    label: "桩基班组",
                    a: 0,
                    b: 0,
                },
                {
                    label: "钢筋加工班组",
                    a: 0,
                    b: 0,
                },
                {
                    label: "桥涵班组",
                    a: 0,
                    b: 0,
                },
                {
                    label: "路基班组",
                    a: 0,
                    b: 0,
                },
                {
                    label: "箱涵及下穿班组",
                    a: 0,
                    b: 0,
                },
                {
                    label: "交安班组",
                    a: 0,
                    b: 0,
                },
                {
                    label: "路基路面班组",
                    a: 0,
                    b: 0,
                }
            ]
        }
    };
    componentDidMount() {

        if (this.props.isNeedGetData) {
            this.refresh();
            this.refreshBottom()
        } else {
            this.setState({
                loading: false,
            })
        }

    }
    //获取上面数据
    refresh = async () => {
        const { myFetch,errMsg } = this.props;
        const { data,success,message,code } = await myFetch("getZjLzehLaborRealNameList",{});
        if (success) {
            let { seeUnitNumber = 0,currentMonthSalaryTotal = 0,presencePersonnelNumber = 0,teamNumber = 0 } = (data[0] || {});

            //处理数据data
            this.setState({
                data: {
                    ...this.state.data,
                    topData: [
                        {
                            label: "参建单位数量",
                            value: seeUnitNumber,
                            className: s.yellow
                        },
                        {
                            label: "在场人员数量",
                            value: presencePersonnelNumber,
                            className:  s.blue
                        },
                        {
                            label: "班组数量",
                            value: teamNumber,
                            className: s.green
                        },
                        {
                            label: "本月工资发放总和",
                            value: currentMonthSalaryTotal,
                            className: s.orange
                        }
                    ],
                },
                loading: false
            });
        } else {
            errMsg(message,code);
        }
    };

    //刷新下面
    refreshBottom = async () => {
        const { myFetch,errMsg } = this.props;
        const { data,success,message,code } = await myFetch("getZjLzehTeamInformationList",{});
        if (success) {
            //处理数据data
            this.setState({
                data: {
                    ...this.state.data,
                    bottomData: data.map(item => {
                        return {
                            label: item.teamName,
                            a: item.aSectionPeopleNumber,
                            b: item.bSectionPeopleNumber,
                        }
                    }),
                },

                loading: false
            });
        } else {
            errMsg(message,code);
        }
    };


    maxValue = (numArr) => [...numArr].reduce((m,v) => Math.max(m,v),-Infinity);

    getOption = (data) => {
        return {
            color: ['#0bd0d9','#f49100'],
            tooltip: {
                show:false
            },
            legend: {
                right: 0,
                orient: 'vertical',
                icon: "pin",
                textStyle: {
                    fontSize: 10,
                    color: 'white'
                },
                data: ['A段','B段']
            },
            radar: {
                shape: 'polygon',
                name: {
                    textStyle: {
                        color: '#ffffff',
                        fontSize: 11,

                        rich: {
                            a: {
                                color: '#0bd0d9',
                            },
                            b: {
                                color: '#f49100',
                            },
                        }
                    },
                },
                indicator: data.map(item => {
                    return {
                        name: `${item.label}\n {a|A:${item.a}} {b|B:${item.b}}`,
                        max: Math.max(item.a,item.b)
                    }
                }),
                radius: "70%",
            },
            series: [{
                name: 'A段 vs B段',
                type: 'radar',
                areaStyle: {
                    normal: {
                        color: 'transparent',
                    }
                },
                data: [
                    {
                        value: data.map(item => item.a),
                        name: 'A段'
                    },
                    {
                        value: data.map(item => item.b),
                        name: 'B段'
                    }
                ]
            }]
        }

    }

    render() {
        const {
            loading,
            data: { topData = [],bottomData = [] },
        } = this.state;
        return (
            <Spin spinning={loading} wrapperClassName={s.loading}>
                <div className={s.info}>
                    <div className={s.title}>
                        劳务实名制
                    </div>
                    <div className={s.con}>
                        <div className={s.top}>
                            {
                                topData.map(({ label,value,className },index) => {
                                    return <div key={index} className={s.dItem}>
                                        <span>{label}：</span>
                                        <span className={[className]}>{value}</span>
                                    </div>
                                })
                            }
                        </div>
                        <div className={s.bottom}>
                            <ReactEcharts
                                style={{ width: '100%',height: "100%" }}
                                // option={this.getOption(bottomData)}
                                option={{}}
                                ref={(me) => {
                                    if (me) {
                                        setTimeout(() => {
                                            let ech = me.getEchartsInstance();
                                            ech && ech.setOption && ech.setOption(this.getOption(bottomData));
                                        },300)
                                    }
                                }}
                                notMerge={true}
                                lazyUpdate={true}
                                theme={"theme_name"}
                            />
                        </div>
                    </div>
                </div>
            </Spin>
        );
    }
}
export default Com;
