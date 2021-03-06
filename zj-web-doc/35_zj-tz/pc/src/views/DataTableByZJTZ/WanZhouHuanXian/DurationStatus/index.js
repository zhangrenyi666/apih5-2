import React, { Component } from "react";
import ReactEcharts from 'echarts-for-react';
import s from './style.less';
import QnnForm from "../../../modules/qnn-form";
import { message as Msg, Spin } from "antd";
class index extends Component {
    constructor(props) {
        super(props);
        this.state = {
            loading: false,
            data: null,
            constructEnd: '0',
            dateType: '1',
            echartsData: null,
            optionData: [],
            subprojectId: null
        }
    }
    componentDidMount () {
        const { analySubject } = this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany;
        if (analySubject === '1') {
            this.refreshs();
        } else {
            this.refresh();
        }
    }
    refresh = () => {
        const { projectId } = this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany;
        let formData = this.formWD?.form?.getFieldsValue?.() || {};
        if (formData?.constructEnd) {
            formData.projectId = projectId;
        } else {
            formData = {
                constructEnd: '0',
                dateType: '1',
                subprojectId: '',
                projectId: projectId
            }
        }
        this.setState({
            loading: true,
            constructEnd: formData.constructEnd,
            dateType: formData.dateType,
            subprojectId: formData.subprojectId
        })
        this.props.myFetch('getHomeProjectStatus', {
            ...formData
        }).then(
            ({ success, message, data }) => {
                if (success) {
                    this.setState({
                        loading: false,
                        echartsData: data
                    })
                } else {
                    this.setState({
                        loading: false,
                    })
                }
            }
        );
    }
    refreshs = () => {
        const { projectId } = this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany;
        this.setState({
            loading: true
        })
        this.props.myFetch('getZjTzProSubprojectInfoList', {
            projectId: projectId
        }).then(
            ({ success, message, data }) => {
                if (success) {
                    this.setState({
                        optionData: data,
                        subprojectId: data?.[0]?.subprojectInfoId
                    }, () => {
                        let formData = {
                            constructEnd: '0',
                            dateType: '1',
                            subprojectId: this.state.subprojectId,
                            projectId: projectId
                        };
                        this.props.myFetch('getHomeProjectStatus', {
                            ...formData
                        }).then(
                            (obj) => {
                                if (obj.success) {
                                    this.setState({
                                        loading: false,
                                        echartsData: obj.data
                                    })
                                } else {
                                    this.setState({
                                        loading: false,
                                    })
                                }
                            }
                        );
                    })
                } else {
                    Msg.error(message);
                    this.setState({
                        loading: false,
                    })
                }
            }
        );
    }
    getOptionOne = () => {
        // ?????????????????????????????????
        const { constructEnd, dateType, echartsData } = this.state;
        let option = {
            grid: {
                containLabel: true,
                left: 100,
                right: 100,
                top: 10,
                bottom: 10,
            },
            tooltip: {
                show: false,
            },
            legend: {
                show: false,
            },
            xAxis: {
                boundaryGap: ['5%', '5%'],
                axisLine: {
                    show: false,
                },
                axisTick: {
                    show: false,
                },
                splitLine: {
                    show: false,
                },
                axisLabel: {
                    show: false,
                },
            },
            yAxis: {
                data: ["le"],
                axisLabel: {
                    show: false,
                },
                axisLine: {
                    show: false,
                },
                axisTick: {
                    show: false,
                },
                splitLine: {
                    show: false,
                },
            },
            series: [],
        }
        if (constructEnd === '0' && dateType === '1' && !echartsData?.periodId) {
            option.title = {
                text: '?????????????????????',
                x: "center",
                y: '40%',
                textStyle: {
                    color: 'rgba(255,255,255,0.5)',
                    fontSize: 25,
                    fontWeight: 'bold'
                }
            };
        } else if (constructEnd === '0' && dateType === '1' && (echartsData?.periodId === '5' || echartsData?.periodId === '7' || echartsData?.periodId === '9' || echartsData?.periodId === '11')) {
            option.title = [
                {
                    text: echartsData?.periodFlag,
                    x: "center",
                    y: 10,
                    textStyle: {
                        color: 'rgba(255,255,255,0.5)',
                        fontSize: 18,
                        fontWeight: 'bold'
                    }
                },
                {
                    text: `????????????????????????${echartsData?.constructActualDay}`,
                    x: "25%",
                    y: 'bottom',
                    textStyle: {
                        color: 'rgba(255,255,255,0.5)',
                        fontSize: 16,
                        fontWeight: 'bold'
                    }
                },
                {
                    text: `?????????????????????${echartsData?.handoverAdvanceDay}`,
                    x: "60%",
                    y: 'bottom',
                    textStyle: {
                        color: 'rgba(255,255,255,0.5)',
                        fontSize: 16,
                        fontWeight: 'bold'
                    }
                }
            ];
            option.series = [
                {
                    type: "bar",
                    name: `??????????????????\r\n${echartsData?.actualDate}`,
                    stack: "1",
                    label: {
                        normal: {
                            show: true,
                            formatter: `??????????????????\r\n${echartsData?.actualDate}`,
                            position: [0,-30],
                            textStyle: {
                                color: "#fff",
                                rich: {
                                    value: {
                                        fontSize: "16",
                                        fontWeight: 500,
                                    },
                                },
                            },
                        },
                    },
                    color: '#05d863',
                    barWidth: 30,
                    data: [echartsData?.constructActualDay]
                },
                {
                    type: "bar",
                    name: echartsData?.handoverAdvanceDay > 0 ? `??????????????????\r\n${echartsData?.handoverDateActrual}` : `????????????????????????\r\n${echartsData?.handoverDatePlan}`,
                    stack: "1",
                    label: {
                        normal: {
                            show: true,
                            formatter: echartsData?.handoverAdvanceDay > 0 ? `??????????????????\r\n${echartsData?.handoverDateActrual}` : `????????????????????????\r\n${echartsData?.handoverDatePlan}`,
                            position: [-30,40],
                            textStyle: {
                                color: "#fff",
                                rich: {
                                    value: {
                                        fontSize: "16",
                                        fontWeight: 500,
                                    },
                                },
                            },
                        },
                    },
                    color: '#2ebcf3',
                    barWidth: 30,
                    data: [echartsData?.handoverAdvanceDay]
                },
                {
                    type: "bar",
                    name: echartsData?.handoverAdvanceDay <= 0 ? `??????????????????\r\n${echartsData?.handoverDateActrual}` : `????????????????????????\r\n${echartsData?.handoverDatePlan}`,
                    stack: "1",
                    label: {
                        normal: {
                            show: true,
                            formatter: echartsData?.handoverAdvanceDay <= 0 ? `??????????????????\r\n${echartsData?.handoverDateActrual}` : `????????????????????????\r\n${echartsData?.handoverDatePlan}`,
                            position: [-30, -30],
                            textStyle: {
                                color: "#fff",
                                rich: {
                                    value: {
                                        fontSize: "16",
                                        fontWeight: 500,
                                    },
                                },
                            },
                        },
                    },
                    barWidth: 30,
                    data: [0]
                }
            ]
        } else if (constructEnd === '0' && dateType === '1' && (echartsData?.periodId === '6' || echartsData?.periodId === '8' || echartsData?.periodId === '10' || echartsData?.periodId === '12')) {
            option.title = [
                {
                    text: echartsData?.periodFlag,
                    x: "center",
                    y: 10,
                    textStyle: {
                        color: 'rgba(255,255,255,0.5)',
                        fontSize: 18,
                        fontWeight: 'bold'
                    }
                },
                {
                    text: `????????????????????????${echartsData?.constructActualDay}`,
                    x: "25%",
                    y: 'bottom',
                    textStyle: {
                        color: 'rgba(255,255,255,0.5)',
                        fontSize: 16,
                        fontWeight: 'bold'
                    }
                },
                {
                    text: `?????????????????????${echartsData?.handoverDelayDay}`,
                    x: "60%",
                    y: 'bottom',
                    textStyle: {
                        color: 'rgba(255,255,255,0.5)',
                        fontSize: 16,
                        fontWeight: 'bold'
                    }
                }
            ];
            option.series = [
                {
                    type: "bar",
                    name: `??????????????????\r\n${echartsData?.actualDate}`,
                    stack: "1",
                    label: {
                        normal: {
                            show: true,
                            formatter: `??????????????????\r\n${echartsData?.actualDate}`,
                            position: [0,-30],
                            textStyle: {
                                color: "#fff",
                                rich: {
                                    value: {
                                        fontSize: "16",
                                        fontWeight: 500,
                                    },
                                },
                            },
                        },
                    },
                    color: '#05d863',
                    barWidth: 30,
                    data: [echartsData?.constructActualDay]
                },
                {
                    type: "bar",
                    name: echartsData?.handoverDelayDay > 0 ? `????????????????????????\r\n${echartsData?.handoverDatePlan}` : `??????????????????\r\n${echartsData?.handoverDateActrual}`,
                    stack: "1",
                    label: {
                        normal: {
                            show: true,
                            formatter: echartsData?.handoverDelayDay > 0 ? `????????????????????????\r\n${echartsData?.handoverDatePlan}` : `??????????????????\r\n${echartsData?.handoverDateActrual}`,
                            position: [-30,40],
                            textStyle: {
                                color: "#fff",
                                rich: {
                                    value: {
                                        fontSize: "16",
                                        fontWeight: 500,
                                    },
                                },
                            },
                        },
                    },
                    color: '#f32668',
                    barWidth: 30,
                    data: [echartsData?.handoverDelayDay]
                },
                {
                    type: "bar",
                    name: echartsData?.handoverDelayDay <= 0 ? `????????????????????????\r\n${echartsData?.handoverDatePlan}` : `??????????????????\r\n${echartsData?.handoverDateActrual}`,
                    stack: "1",
                    label: {
                        normal: {
                            show: true,
                            formatter: echartsData?.handoverDelayDay <= 0 ? `????????????????????????\r\n${echartsData?.handoverDatePlan}` : `??????????????????\r\n${echartsData?.handoverDateActrual}`,
                            position: [-30, -30],
                            textStyle: {
                                color: "#fff",
                                rich: {
                                    value: {
                                        fontSize: "16",
                                        fontWeight: 500,
                                    },
                                },
                            },
                        },
                    },
                    barWidth: 30,
                    data: [0]
                }
            ]
        } else if (constructEnd === '0' && dateType === '1' && echartsData?.periodId === '13') {
            option.title = [
                {
                    text: echartsData?.periodFlag,
                    x: "center",
                    y: 10,
                    textStyle: {
                        color: 'rgba(255,255,255,0.5)',
                        fontSize: 18,
                        fontWeight: 'bold'
                    }
                },
                {
                    text: `????????????????????????${echartsData?.constructCurrentDay}`,
                    x: "25%",
                    y: 'bottom',
                    textStyle: {
                        color: 'rgba(255,255,255,0.5)',
                        fontSize: 16,
                        fontWeight: 'bold'
                    }
                },
                {
                    text: `?????????????????????${echartsData?.handoverDay}`,
                    x: "60%",
                    y: 'bottom',
                    textStyle: {
                        color: 'rgba(255,255,255,0.5)',
                        fontSize: 16,
                        fontWeight: 'bold'
                    }
                }
            ];
            option.series = [
                {
                    type: "bar",
                    name: `??????????????????\r\n${echartsData?.actualDate}`,
                    stack: "1",
                    label: {
                        normal: {
                            show: true,
                            formatter: `??????????????????\r\n${echartsData?.actualDate}`,
                            position: [0,-30],
                            textStyle: {
                                color: "#fff",
                                rich: {
                                    value: {
                                        fontSize: "16",
                                        fontWeight: 500,
                                    },
                                },
                            },
                        },
                    },
                    color: '#05d863',
                    barWidth: 30,
                    data: [echartsData?.constructCurrentDay]
                },
                {
                    type: "bar",
                    name: echartsData?.handoverDay > 0 ? `????????????\r\n${echartsData?.currDate}` : `????????????????????????\r\n${echartsData?.handoverDatePlan}`,
                    stack: "1",
                    label: {
                        normal: {
                            show: true,
                            formatter: echartsData?.handoverDay > 0 ? `????????????\r\n${echartsData?.currDate}` : `????????????????????????\r\n${echartsData?.handoverDatePlan}`,
                            position: [-30,40],
                            textStyle: {
                                color: "#fff",
                                rich: {
                                    value: {
                                        fontSize: "16",
                                        fontWeight: 500,
                                    },
                                },
                            },
                        },
                    },
                    itemStyle: {
                        color: 'none',
                        borderColor: "#05d863",
                        borderWidth: 3,
                    },
                    barWidth: 20,
                    data: [echartsData?.handoverDay]
                },
                {
                    type: "bar",
                    name: echartsData?.handoverDay <= 0 ? `????????????\r\n${echartsData?.currDate}` : `????????????????????????\r\n${echartsData?.handoverDatePlan}`,
                    stack: "1",
                    label: {
                        normal: {
                            show: true,
                            formatter: echartsData?.handoverDay <= 0 ? `????????????\r\n${echartsData?.currDate}` : `????????????????????????\r\n${echartsData?.handoverDatePlan}`,
                            position: [-30, -30],
                            textStyle: {
                                color: "#fff",
                                rich: {
                                    value: {
                                        fontSize: "16",
                                        fontWeight: 500,
                                    },
                                },
                            },
                        },
                    },
                    barWidth: 30,
                    data: [0]
                }
            ]
        } else if (constructEnd === '0' && dateType === '1' && echartsData?.periodId === '14') {
            option.title = [
                {
                    text: echartsData?.periodFlag,
                    x: "center",
                    y: 10,
                    textStyle: {
                        color: 'rgba(255,255,255,0.5)',
                        fontSize: 18,
                        fontWeight: 'bold'
                    }
                },
                {
                    text: `????????????????????????${echartsData?.constructCurrentDay}`,
                    x: "25%",
                    y: 'bottom',
                    textStyle: {
                        color: 'rgba(255,255,255,0.5)',
                        fontSize: 16,
                        fontWeight: 'bold'
                    }
                },
                {
                    text: `?????????????????????${echartsData?.overdueHandoverDay}`,
                    x: "60%",
                    y: 'bottom',
                    textStyle: {
                        color: 'rgba(255,255,255,0.5)',
                        fontSize: 16,
                        fontWeight: 'bold'
                    }
                }
            ];
            option.series = [
                {
                    type: "bar",
                    name: `??????????????????\r\n${echartsData?.actualDate}`,
                    stack: "1",
                    label: {
                        normal: {
                            show: true,
                            formatter: `??????????????????\r\n${echartsData?.actualDate}`,
                            position: [0,-30],
                            textStyle: {
                                color: "#fff",
                                rich: {
                                    value: {
                                        fontSize: "16",
                                        fontWeight: 500,
                                    },
                                },
                            },
                        },
                    },
                    color: '#05d863',
                    barWidth: 30,
                    data: [echartsData?.constructCurrentDay]
                },
                {
                    type: "bar",
                    name: echartsData?.overdueHandoverDay > 0 ? `????????????????????????\r\n${echartsData?.handoverDatePlan}` : `????????????\r\n${echartsData?.currDate}`,
                    stack: "1",
                    label: {
                        normal: {
                            show: true,
                            formatter: echartsData?.overdueHandoverDay > 0 ? `????????????????????????\r\n${echartsData?.handoverDatePlan}` : `????????????\r\n${echartsData?.currDate}`,
                            position: [-30,40],
                            textStyle: {
                                color: "#fff",
                                rich: {
                                    value: {
                                        fontSize: "16",
                                        fontWeight: 500,
                                    },
                                },
                            },
                        },
                    },
                    itemStyle: {
                        color: 'none',
                        borderColor: "#f32668",
                        borderWidth: 3,
                    },
                    barWidth: 20,
                    data: [echartsData?.overdueHandoverDay]
                },
                {
                    type: "bar",
                    name: echartsData?.overdueHandoverDay <= 0 ? `????????????????????????\r\n${echartsData?.handoverDatePlan}` : `????????????\r\n${echartsData?.currDate}`,
                    stack: "1",
                    label: {
                        normal: {
                            show: true,
                            formatter: echartsData?.overdueHandoverDay <= 0 ? `????????????????????????\r\n${echartsData?.handoverDatePlan}` : `????????????\r\n${echartsData?.currDate}`,
                            position: [-30, -30],
                            textStyle: {
                                color: "#fff",
                                rich: {
                                    value: {
                                        fontSize: "16",
                                        fontWeight: 500,
                                    },
                                },
                            },
                        },
                    },
                    barWidth: 30,
                    data: [0]
                }
            ]
        } else if (constructEnd === '1' && dateType === '1' && !echartsData?.periodId) {
            option.title = {
                text: '??????????????????????????????????????????',
                x: "center",
                y: '40%',
                textStyle: {
                    color: 'rgba(255,255,255,0.5)',
                    fontSize: 25,
                    fontWeight: 'bold'
                }
            };
        } else if (constructEnd === '1' && dateType === '1' && echartsData?.periodId === '1') {
            option.title = [
                {
                    text: echartsData?.periodFlag,
                    x: "center",
                    y: 10,
                    textStyle: {
                        color: 'rgba(255,255,255,0.5)',
                        fontSize: 18,
                        fontWeight: 'bold'
                    }
                },
                {
                    text: `????????????????????????${echartsData?.constructActualDay}`,
                    x: "25%",
                    y: 'bottom',
                    textStyle: {
                        color: 'rgba(255,255,255,0.5)',
                        fontSize: 16,
                        fontWeight: 'bold'
                    }
                },
                {
                    text: `?????????????????????${echartsData?.completeAdvanceDay}`,
                    x: "60%",
                    y: 'bottom',
                    textStyle: {
                        color: 'rgba(255,255,255,0.5)',
                        fontSize: 16,
                        fontWeight: 'bold'
                    }
                }
            ];
            option.series = [
                {
                    type: "bar",
                    name: `??????????????????\r\n${echartsData?.actualDate}`,
                    stack: "1",
                    label: {
                        normal: {
                            show: true,
                            formatter: `??????????????????\r\n${echartsData?.actualDate}`,
                            position: [0,-30],
                            textStyle: {
                                color: "#fff",
                                rich: {
                                    value: {
                                        fontSize: "16",
                                        fontWeight: 500,
                                    },
                                },
                            },
                        },
                    },
                    color: '#05d863',
                    barWidth: 30,
                    data: [echartsData?.constructActualDay]
                },
                {
                    type: "bar",
                    name: echartsData?.completeAdvanceDay > 0 ? `??????????????????\r\n${echartsData?.complateDateActrual}` : `????????????????????????\r\n${echartsData?.complateDatePlan}`,
                    stack: "1",
                    label: {
                        normal: {
                            show: true,
                            formatter: echartsData?.completeAdvanceDay > 0 ? `??????????????????\r\n${echartsData?.complateDateActrual}` : `????????????????????????\r\n${echartsData?.complateDatePlan}`,
                            position: [-30,40],
                            textStyle: {
                                color: "#fff",
                                rich: {
                                    value: {
                                        fontSize: "16",
                                        fontWeight: 500,
                                    },
                                },
                            },
                        },
                    },
                    color: '#2ebcf3',
                    barWidth: 30,
                    data: [echartsData?.completeAdvanceDay]
                },
                {
                    type: "bar",
                    name: echartsData?.completeAdvanceDay <= 0 ? `??????????????????\r\n${echartsData?.complateDateActrual}` : `????????????????????????\r\n${echartsData?.complateDatePlan}`,
                    stack: "1",
                    label: {
                        normal: {
                            show: true,
                            formatter: echartsData?.completeAdvanceDay <= 0 ? `??????????????????\r\n${echartsData?.complateDateActrual}` : `????????????????????????\r\n${echartsData?.complateDatePlan}`,
                            position: [-30, -30],
                            textStyle: {
                                color: "#fff",
                                rich: {
                                    value: {
                                        fontSize: "16",
                                        fontWeight: 500,
                                    },
                                },
                            },
                        },
                    },
                    barWidth: 30,
                    data: [0]
                }
            ]
        } else if (constructEnd === '1' && dateType === '1' && echartsData?.periodId === '2') {
            option.title = [
                {
                    text: echartsData?.periodFlag,
                    x: "center",
                    y: 10,
                    textStyle: {
                        color: 'rgba(255,255,255,0.5)',
                        fontSize: 18,
                        fontWeight: 'bold'
                    }
                },
                {
                    text: `????????????????????????${echartsData?.constructActualDay}`,
                    x: "25%",
                    y: 'bottom',
                    textStyle: {
                        color: 'rgba(255,255,255,0.5)',
                        fontSize: 16,
                        fontWeight: 'bold'
                    }
                },
                {
                    text: `?????????????????????${echartsData?.completeDelayDay}`,
                    x: "60%",
                    y: 'bottom',
                    textStyle: {
                        color: 'rgba(255,255,255,0.5)',
                        fontSize: 16,
                        fontWeight: 'bold'
                    }
                }
            ];
            option.series = [
                {
                    type: "bar",
                    name: `??????????????????\r\n${echartsData?.actualDate}`,
                    stack: "1",
                    label: {
                        normal: {
                            show: true,
                            formatter: `??????????????????\r\n${echartsData?.actualDate}`,
                            position: [0,-30],
                            textStyle: {
                                color: "#fff",
                                rich: {
                                    value: {
                                        fontSize: "16",
                                        fontWeight: 500,
                                    },
                                },
                            },
                        },
                    },
                    color: '#05d863',
                    barWidth: 30,
                    data: [echartsData?.constructActualDay]
                },
                {
                    type: "bar",
                    name: echartsData?.completeDelayDay > 0 ? `????????????????????????\r\n${echartsData?.complateDatePlan}` : `??????????????????\r\n${echartsData?.complateDateActrual}`,
                    stack: "1",
                    label: {
                        normal: {
                            show: true,
                            formatter: echartsData?.completeDelayDay > 0 ? `????????????????????????\r\n${echartsData?.complateDatePlan}` : `??????????????????\r\n${echartsData?.complateDateActrual}`,
                            position: [-30,40],
                            textStyle: {
                                color: "#fff",
                                rich: {
                                    value: {
                                        fontSize: "16",
                                        fontWeight: 500,
                                    },
                                },
                            },
                        },
                    },
                    color: '#f32668',
                    barWidth: 30,
                    data: [echartsData?.completeDelayDay]
                },
                {
                    type: "bar",
                    name: echartsData?.completeDelayDay <= 0 ? `????????????????????????\r\n${echartsData?.complateDatePlan}` : `??????????????????\r\n${echartsData?.complateDateActrual}`,
                    stack: "1",
                    label: {
                        normal: {
                            show: true,
                            formatter: echartsData?.completeDelayDay <= 0 ? `????????????????????????\r\n${echartsData?.complateDatePlan}` : `??????????????????\r\n${echartsData?.complateDateActrual}`,
                            position: [-30,-30],
                            textStyle: {
                                color: "#fff",
                                rich: {
                                    value: {
                                        fontSize: "16",
                                        fontWeight: 500,
                                    },
                                },
                            },
                        },
                    },
                    barWidth: 30,
                    data: [0]
                }
            ]
        } else if (constructEnd === '1' && dateType === '1' && echartsData?.periodId === '3') {
            option.title = [
                {
                    text: echartsData?.periodFlag,
                    x: "center",
                    y: 10,
                    textStyle: {
                        color: 'rgba(255,255,255,0.5)',
                        fontSize: 18,
                        fontWeight: 'bold'
                    }
                },
                {
                    text: `????????????????????????${echartsData?.constructCurrentDay}`,
                    x: "25%",
                    y: 'bottom',
                    textStyle: {
                        color: 'rgba(255,255,255,0.5)',
                        fontSize: 16,
                        fontWeight: 'bold'
                    }
                },
                {
                    text: `?????????????????????${echartsData?.completeDay}`,
                    x: "60%",
                    y: 'bottom',
                    textStyle: {
                        color: 'rgba(255,255,255,0.5)',
                        fontSize: 16,
                        fontWeight: 'bold'
                    }
                }
            ];
            option.series = [
                {
                    type: "bar",
                    name: `??????????????????\r\n${echartsData?.actualDate}`,
                    stack: "1",
                    label: {
                        normal: {
                            show: true,
                            formatter: `??????????????????\r\n${echartsData?.actualDate}`,
                            position: [0,-30],
                            textStyle: {
                                color: "#fff",
                                rich: {
                                    value: {
                                        fontSize: "16",
                                        fontWeight: 500,
                                    },
                                },
                            },
                        },
                    },
                    color: '#05d863',
                    barWidth: 30,
                    data: [echartsData?.constructCurrentDay]
                },
                {
                    type: "bar",
                    name: echartsData?.completeDay > 0 ? `????????????\r\n${echartsData?.currDate}` : `????????????????????????\r\n${echartsData?.complateDatePlan}`,
                    stack: "1",
                    label: {
                        normal: {
                            show: true,
                            formatter: echartsData?.completeDay > 0 ? `????????????\r\n${echartsData?.currDate}` : `????????????????????????\r\n${echartsData?.complateDatePlan}`,
                            position: [-30,40],
                            textStyle: {
                                color: "#fff",
                                rich: {
                                    value: {
                                        fontSize: "16",
                                        fontWeight: 500,
                                    },
                                },
                            },
                        },
                    },
                    itemStyle: {
                        color: 'none',
                        borderColor: "#05d863",
                        borderWidth: 3,
                    },
                    barWidth: 20,
                    data: [echartsData?.completeDay]
                },
                {
                    type: "bar",
                    name: echartsData?.completeDay <= 0 ? `????????????\r\n${echartsData?.currDate}` : `????????????????????????\r\n${echartsData?.complateDatePlan}`,
                    stack: "1",
                    label: {
                        normal: {
                            show: true,
                            formatter: echartsData?.completeDay <= 0 ? `????????????\r\n${echartsData?.currDate}` : `????????????????????????\r\n${echartsData?.complateDatePlan}`,
                            position: [-30,-30],
                            textStyle: {
                                color: "#fff",
                                rich: {
                                    value: {
                                        fontSize: "16",
                                        fontWeight: 500,
                                    },
                                },
                            },
                        },
                    },
                    barWidth: 30,
                    data: [0]
                }
            ]
        } else if (constructEnd === '1' && dateType === '1' && echartsData?.periodId === '4') {
            option.title = [
                {
                    text: echartsData?.periodFlag,
                    x: "center",
                    y: 10,
                    textStyle: {
                        color: 'rgba(255,255,255,0.5)',
                        fontSize: 18,
                        fontWeight: 'bold'
                    }
                },
                {
                    text: `????????????????????????${echartsData?.constructCurrentDay}`,
                    x: "25%",
                    y: 'bottom',
                    textStyle: {
                        color: 'rgba(255,255,255,0.5)',
                        fontSize: 16,
                        fontWeight: 'bold'
                    }
                },
                {
                    text: `?????????????????????${echartsData?.overduecompleteDay}`,
                    x: "60%",
                    y: 'bottom',
                    textStyle: {
                        color: 'rgba(255,255,255,0.5)',
                        fontSize: 16,
                        fontWeight: 'bold'
                    }
                }
            ];
            option.series = [
                {
                    type: "bar",
                    name: `??????????????????\r\n${echartsData?.actualDate}`,
                    stack: "1",
                    label: {
                        normal: {
                            show: true,
                            formatter: `??????????????????\r\n${echartsData?.actualDate}`,
                            position: [0,-30],
                            textStyle: {
                                color: "#fff",
                                rich: {
                                    value: {
                                        fontSize: "16",
                                        fontWeight: 500,
                                    },
                                },
                            },
                        },
                    },
                    color: '#05d863',
                    barWidth: 30,
                    data: [echartsData?.constructCurrentDay]
                },
                {
                    type: "bar",
                    name: echartsData?.overduecompleteDay > 0 ? `????????????????????????\r\n${echartsData?.complateDatePlan}` : `????????????\r\n${echartsData?.currDate}`,
                    stack: "1",
                    label: {
                        normal: {
                            show: true,
                            formatter: echartsData?.overduecompleteDay > 0 ? `????????????????????????\r\n${echartsData?.complateDatePlan}` : `????????????\r\n${echartsData?.currDate}`,
                            position: [-30,40],
                            textStyle: {
                                color: "#fff",
                                rich: {
                                    value: {
                                        fontSize: "16",
                                        fontWeight: 500,
                                    },
                                },
                            },
                        },
                    },
                    itemStyle: {
                        color: 'none',
                        borderColor: "#f32668",
                        borderWidth: 3,
                    },
                    barWidth: 20,
                    data: [echartsData?.overduecompleteDay]
                },
                {
                    type: "bar",
                    name: echartsData?.overduecompleteDay <= 0 ? `????????????????????????\r\n${echartsData?.complateDatePlan}` : `????????????\r\n${echartsData?.currDate}`,
                    stack: "1",
                    label: {
                        normal: {
                            show: true,
                            formatter: echartsData?.overduecompleteDay <= 0 ? `????????????????????????\r\n${echartsData?.complateDatePlan}` : `????????????\r\n${echartsData?.currDate}`,
                            position: [-30,-30],
                            textStyle: {
                                color: "#fff",
                                rich: {
                                    value: {
                                        fontSize: "16",
                                        fontWeight: 500,
                                    },
                                },
                            },
                        },
                    },
                    barWidth: 30,
                    data: [0]
                }
            ]
        } else if (constructEnd === '1' && dateType === '1' && (echartsData?.periodId === '5' || echartsData?.periodId === '6')) {
            option.title = [
                {
                    text: echartsData?.periodFlag,
                    x: "center",
                    y: 10,
                    textStyle: {
                        color: 'rgba(255,255,255,0.5)',
                        fontSize: 18,
                        fontWeight: 'bold'
                    }
                },
                {
                    text: `???????????????????????????${echartsData?.completeActualDay}`,
                    x: "25%",
                    y: 'bottom',
                    textStyle: {
                        color: 'rgba(255,255,255,0.5)',
                        fontSize: 16,
                        fontWeight: 'bold'
                    }
                },
                {
                    text: `?????????????????????${echartsData?.completeAdvanceDay}`,
                    x: "60%",
                    y: 'bottom',
                    textStyle: {
                        color: 'rgba(255,255,255,0.5)',
                        fontSize: 16,
                        fontWeight: 'bold'
                    }
                }
            ];
            option.series = [
                {
                    type: "bar",
                    name: `??????????????????\r\n${echartsData?.handoverDateActrual}`,
                    stack: "1",
                    label: {
                        normal: {
                            show: true,
                            formatter: `??????????????????\r\n${echartsData?.handoverDateActrual}`,
                            position: [0,-30],
                            textStyle: {
                                color: "#fff",
                                rich: {
                                    value: {
                                        fontSize: "16",
                                        fontWeight: 500,
                                    },
                                },
                            },
                        },
                    },
                    color: '#05d863',
                    barWidth: 30,
                    data: [echartsData?.completeActualDay]
                },
                {
                    type: "bar",
                    name: echartsData?.completeAdvanceDay > 0 ? `??????????????????\r\n${echartsData?.complateDateActrual}` : `????????????????????????\r\n${echartsData?.complateDatePlan}`,
                    stack: "1",
                    label: {
                        normal: {
                            show: true,
                            formatter: echartsData?.completeAdvanceDay > 0 ? `??????????????????\r\n${echartsData?.complateDateActrual}` : `????????????????????????\r\n${echartsData?.complateDatePlan}`,
                            position: [-30,40],
                            textStyle: {
                                color: "#fff",
                                rich: {
                                    value: {
                                        fontSize: "16",
                                        fontWeight: 500,
                                    },
                                },
                            },
                        },
                    },
                    color: '#2ebcf3',
                    barWidth: 30,
                    data: [echartsData?.completeAdvanceDay]
                },
                {
                    type: "bar",
                    name: echartsData?.completeAdvanceDay <= 0 ? `??????????????????\r\n${echartsData?.complateDateActrual}` : `????????????????????????\r\n${echartsData?.complateDatePlan}`,
                    stack: "1",
                    label: {
                        normal: {
                            show: true,
                            formatter: echartsData?.completeAdvanceDay <= 0 ? `??????????????????\r\n${echartsData?.complateDateActrual}` : `????????????????????????\r\n${echartsData?.complateDatePlan}`,
                            position: [-30,-30],
                            textStyle: {
                                color: "#fff",
                                rich: {
                                    value: {
                                        fontSize: "16",
                                        fontWeight: 500,
                                    },
                                },
                            },
                        },
                    },
                    barWidth: 30,
                    data: [0]
                }
            ]
        } else if (constructEnd === '1' && dateType === '1' && (echartsData?.periodId === '7' || echartsData?.periodId === '8')) {
            option.title = [
                {
                    text: echartsData?.periodFlag,
                    x: "center",
                    y: 10,
                    textStyle: {
                        color: 'rgba(255,255,255,0.5)',
                        fontSize: 18,
                        fontWeight: 'bold'
                    }
                },
                {
                    text: `???????????????????????????${echartsData?.completeActualDay}`,
                    x: "25%",
                    y: 'bottom',
                    textStyle: {
                        color: 'rgba(255,255,255,0.5)',
                        fontSize: 16,
                        fontWeight: 'bold'
                    }
                },
                {
                    text: `?????????????????????${echartsData?.completeDelayDay}`,
                    x: "60%",
                    y: 'bottom',
                    textStyle: {
                        color: 'rgba(255,255,255,0.5)',
                        fontSize: 16,
                        fontWeight: 'bold'
                    }
                }
            ];
            option.series = [
                {
                    type: "bar",
                    name: `??????????????????\r\n${echartsData?.handoverDateActrual}`,
                    stack: "1",
                    label: {
                        normal: {
                            show: true,
                            formatter: `??????????????????\r\n${echartsData?.handoverDateActrual}`,
                            position: [0,-30],
                            textStyle: {
                                color: "#fff",
                                rich: {
                                    value: {
                                        fontSize: "16",
                                        fontWeight: 500,
                                    },
                                },
                            },
                        },
                    },
                    color: '#05d863',
                    barWidth: 30,
                    data: [echartsData?.completeActualDay]
                },
                {
                    type: "bar",
                    name: echartsData?.completeDelayDay > 0 ? `??????????????????\r\n${echartsData?.complateDateActrual}` : `????????????????????????\r\n${echartsData?.complateDatePlan}`,
                    stack: "1",
                    label: {
                        normal: {
                            show: true,
                            formatter: echartsData?.completeDelayDay > 0 ? `??????????????????\r\n${echartsData?.complateDateActrual}` : `????????????????????????\r\n${echartsData?.complateDatePlan}`,
                            position: [-30,40],
                            textStyle: {
                                color: "#fff",
                                rich: {
                                    value: {
                                        fontSize: "16",
                                        fontWeight: 500,
                                    },
                                },
                            },
                        },
                    },
                    color: '#f32668',
                    barWidth: 30,
                    data: [echartsData?.completeDelayDay]
                },
                {
                    type: "bar",
                    name: echartsData?.completeDelayDay <= 0 ? `??????????????????\r\n${echartsData?.complateDateActrual}` : `????????????????????????\r\n${echartsData?.complateDatePlan}`,
                    stack: "1",
                    label: {
                        normal: {
                            show: true,
                            formatter: echartsData?.completeDelayDay <= 0 ? `??????????????????\r\n${echartsData?.complateDateActrual}` : `????????????????????????\r\n${echartsData?.complateDatePlan}`,
                            position: [-30,-30],
                            textStyle: {
                                color: "#fff",
                                rich: {
                                    value: {
                                        fontSize: "16",
                                        fontWeight: 500,
                                    },
                                },
                            },
                        },
                    },
                    barWidth: 30,
                    data: [0]
                }
            ]
        } else if (constructEnd === '1' && dateType === '1' && (echartsData?.periodId === '9' || echartsData?.periodId === '10')) {
            option.title = [
                {
                    text: echartsData?.periodFlag,
                    x: "center",
                    y: 10,
                    textStyle: {
                        color: 'rgba(255,255,255,0.5)',
                        fontSize: 18,
                        fontWeight: 'bold'
                    }
                },
                {
                    text: `???????????????????????????${echartsData?.completeCurrentDay}`,
                    x: "25%",
                    y: 'bottom',
                    textStyle: {
                        color: 'rgba(255,255,255,0.5)',
                        fontSize: 16,
                        fontWeight: 'bold'
                    }
                },
                {
                    text: `?????????????????????${echartsData?.completeDay}`,
                    x: "60%",
                    y: 'bottom',
                    textStyle: {
                        color: 'rgba(255,255,255,0.5)',
                        fontSize: 16,
                        fontWeight: 'bold'
                    }
                }
            ];
            option.series = [
                {
                    type: "bar",
                    name: `??????????????????\r\n${echartsData?.handoverDateActrual}`,
                    stack: "1",
                    label: {
                        normal: {
                            show: true,
                            formatter: `??????????????????\r\n${echartsData?.handoverDateActrual}`,
                            position: [0,-30],
                            textStyle: {
                                color: "#fff",
                                rich: {
                                    value: {
                                        fontSize: "16",
                                        fontWeight: 500,
                                    },
                                },
                            },
                        },
                    },
                    color: '#05d863',
                    barWidth: 30,
                    data: [echartsData?.completeCurrentDay]
                },
                {
                    type: "bar",
                    name: echartsData?.completeDay > 0 ? `????????????\r\n${echartsData?.currDate}` : `????????????????????????\r\n${echartsData?.complateDatePlan}`,
                    stack: "1",
                    label: {
                        normal: {
                            show: true,
                            formatter: echartsData?.completeDay > 0 ? `????????????\r\n${echartsData?.currDate}` : `????????????????????????\r\n${echartsData?.complateDatePlan}`,
                            position: [-30,40],
                            textStyle: {
                                color: "#fff",
                                rich: {
                                    value: {
                                        fontSize: "16",
                                        fontWeight: 500,
                                    },
                                },
                            },
                        },
                    },
                    itemStyle: {
                        color: 'none',
                        borderColor: "#05d863",
                        borderWidth: 3,
                    },
                    barWidth: 20,
                    data: [echartsData?.completeDay]
                },
                {
                    type: "bar",
                    name: echartsData?.completeDay <= 0 ? `????????????\r\n${echartsData?.currDate}` : `????????????????????????\r\n${echartsData?.complateDatePlan}`,
                    stack: "1",
                    label: {
                        normal: {
                            show: true,
                            formatter: echartsData?.completeDay <= 0 ? `????????????\r\n${echartsData?.currDate}` : `????????????????????????\r\n${echartsData?.complateDatePlan}`,
                            position: [-30,-30],
                            textStyle: {
                                color: "#fff",
                                rich: {
                                    value: {
                                        fontSize: "16",
                                        fontWeight: 500,
                                    },
                                },
                            },
                        },
                    },
                    barWidth: 30,
                    data: [0]
                }
            ]
        } else if (constructEnd === '1' && dateType === '1' && (echartsData?.periodId === '11' || echartsData?.periodId === '12')) {
            option.title = [
                {
                    text: echartsData?.periodFlag,
                    x: "center",
                    y: 10,
                    textStyle: {
                        color: 'rgba(255,255,255,0.5)',
                        fontSize: 18,
                        fontWeight: 'bold'
                    }
                },
                {
                    text: `???????????????????????????${echartsData?.completeCurrentDay}`,
                    x: "25%",
                    y: 'bottom',
                    textStyle: {
                        color: 'rgba(255,255,255,0.5)',
                        fontSize: 16,
                        fontWeight: 'bold'
                    }
                },
                {
                    text: `?????????????????????${echartsData?.overduecompleteDay}`,
                    x: "60%",
                    y: 'bottom',
                    textStyle: {
                        color: 'rgba(255,255,255,0.5)',
                        fontSize: 16,
                        fontWeight: 'bold'
                    }
                }
            ];
            option.series = [
                {
                    type: "bar",
                    name: `??????????????????\r\n${echartsData?.handoverDateActrual}`,
                    stack: "1",
                    label: {
                        normal: {
                            show: true,
                            formatter: `??????????????????\r\n${echartsData?.handoverDateActrual}`,
                            position: [0,-30],
                            textStyle: {
                                color: "#fff",
                                rich: {
                                    value: {
                                        fontSize: "16",
                                        fontWeight: 500,
                                    },
                                },
                            },
                        },
                    },
                    color: '#05d863',
                    barWidth: 30,
                    data: [echartsData?.completeCurrentDay]
                },
                {
                    type: "bar",
                    name: echartsData?.overduecompleteDay > 0 ? `????????????????????????\r\n${echartsData?.complateDatePlan}` : `????????????\r\n${echartsData?.currDate}`,
                    stack: "1",
                    label: {
                        normal: {
                            show: true,
                            formatter: echartsData?.overduecompleteDay > 0 ? `????????????????????????\r\n${echartsData?.complateDatePlan}` : `????????????\r\n${echartsData?.currDate}`,
                            position: [-30,40],
                            textStyle: {
                                color: "#fff",
                                rich: {
                                    value: {
                                        fontSize: "16",
                                        fontWeight: 500,
                                    },
                                },
                            },
                        },
                    },
                    itemStyle: {
                        color: 'none',
                        borderColor: "#f32668",
                        borderWidth: 3,
                    },
                    barWidth: 20,
                    data: [echartsData?.overduecompleteDay]
                },
                {
                    type: "bar",
                    name: echartsData?.overduecompleteDay <= 0 ? `????????????????????????\r\n${echartsData?.complateDatePlan}` : `????????????\r\n${echartsData?.currDate}`,
                    stack: "1",
                    label: {
                        normal: {
                            show: true,
                            formatter: echartsData?.overduecompleteDay <= 0 ? `????????????????????????\r\n${echartsData?.complateDatePlan}` : `????????????\r\n${echartsData?.currDate}`,
                            position: [-30,-30],
                            textStyle: {
                                color: "#fff",
                                rich: {
                                    value: {
                                        fontSize: "16",
                                        fontWeight: 500,
                                    },
                                },
                            },
                        },
                    },
                    barWidth: 30,
                    data: [0]
                }
            ]
        } else if (constructEnd === '0' && dateType === '2' && !echartsData?.periodId) {
            option.title = {
                text: '?????????????????????',
                x: "center",
                y: '40%',
                textStyle: {
                    color: 'rgba(255,255,255,0.5)',
                    fontSize: 25,
                    fontWeight: 'bold'
                }
            };
        } else if (constructEnd === '0' && dateType === '2' && (echartsData?.periodId === '5' || echartsData?.periodId === '7' || echartsData?.periodId === '9' || echartsData?.periodId === '11')) {
            option.title = [
                {
                    text: echartsData?.periodFlag,
                    x: "center",
                    y: 10,
                    textStyle: {
                        color: 'rgba(255,255,255,0.5)',
                        fontSize: 18,
                        fontWeight: 'bold'
                    }
                },
                {
                    text: `????????????????????????${echartsData?.constructActualDay}`,
                    x: "25%",
                    y: 'bottom',
                    textStyle: {
                        color: 'rgba(255,255,255,0.5)',
                        fontSize: 16,
                        fontWeight: 'bold'
                    }
                },
                {
                    text: `?????????????????????${echartsData?.handoverAdvanceDay}`,
                    x: "60%",
                    y: 'bottom',
                    textStyle: {
                        color: 'rgba(255,255,255,0.5)',
                        fontSize: 16,
                        fontWeight: 'bold'
                    }
                }
            ];
            option.series = [
                {
                    type: "bar",
                    name: `??????????????????\r\n${echartsData?.actualDate}`,
                    stack: "1",
                    label: {
                        normal: {
                            show: true,
                            formatter: `??????????????????\r\n${echartsData?.actualDate}`,
                            position: [0,-30],
                            textStyle: {
                                color: "#fff",
                                rich: {
                                    value: {
                                        fontSize: "16",
                                        fontWeight: 500,
                                    },
                                },
                            },
                        },
                    },
                    color: '#05d863',
                    barWidth: 30,
                    data: [echartsData?.constructActualDay]
                },
                {
                    type: "bar",
                    name: echartsData?.handoverAdvanceDay > 0 ? `??????????????????\r\n${echartsData?.handoverDateActrual}` : `????????????????????????\r\n${echartsData?.handoverDatePlan}`,
                    stack: "1",
                    label: {
                        normal: {
                            show: true,
                            formatter: echartsData?.handoverAdvanceDay > 0 ? `??????????????????\r\n${echartsData?.handoverDateActrual}` : `????????????????????????\r\n${echartsData?.handoverDatePlan}`,
                            position: [-30,40],
                            textStyle: {
                                color: "#fff",
                                rich: {
                                    value: {
                                        fontSize: "16",
                                        fontWeight: 500,
                                    },
                                },
                            },
                        },
                    },
                    color: '#2ebcf3',
                    barWidth: 30,
                    data: [echartsData?.handoverAdvanceDay]
                },
                {
                    type: "bar",
                    name: echartsData?.handoverAdvanceDay <= 0 ? `??????????????????\r\n${echartsData?.handoverDateActrual}` : `????????????????????????\r\n${echartsData?.handoverDatePlan}`,
                    stack: "1",
                    label: {
                        normal: {
                            show: true,
                            formatter: echartsData?.handoverAdvanceDay <= 0 ? `??????????????????\r\n${echartsData?.handoverDateActrual}` : `????????????????????????\r\n${echartsData?.handoverDatePlan}`,
                            position: [-30,-30],
                            textStyle: {
                                color: "#fff",
                                rich: {
                                    value: {
                                        fontSize: "16",
                                        fontWeight: 500,
                                    },
                                },
                            },
                        },
                    },
                    barWidth: 30,
                    data: [0]
                }
            ]
        } else if (constructEnd === '0' && dateType === '2' && (echartsData?.periodId === '6' || echartsData?.periodId === '8' || echartsData?.periodId === '10' || echartsData?.periodId === '12')) {
            option.title = [
                {
                    text: echartsData?.periodFlag,
                    x: "center",
                    y: 10,
                    textStyle: {
                        color: 'rgba(255,255,255,0.5)',
                        fontSize: 18,
                        fontWeight: 'bold'
                    }
                },
                {
                    text: `????????????????????????${echartsData?.constructActualDay}`,
                    x: "25%",
                    y: 'bottom',
                    textStyle: {
                        color: 'rgba(255,255,255,0.5)',
                        fontSize: 16,
                        fontWeight: 'bold'
                    }
                },
                {
                    text: `?????????????????????${echartsData?.handoverDelayDay}`,
                    x: "60%",
                    y: 'bottom',
                    textStyle: {
                        color: 'rgba(255,255,255,0.5)',
                        fontSize: 16,
                        fontWeight: 'bold'
                    }
                }
            ];
            option.series = [
                {
                    type: "bar",
                    name: `??????????????????\r\n${echartsData?.actualDate}`,
                    stack: "1",
                    label: {
                        normal: {
                            show: true,
                            formatter: `??????????????????\r\n${echartsData?.actualDate}`,
                            position: [0,-30],
                            textStyle: {
                                color: "#fff",
                                rich: {
                                    value: {
                                        fontSize: "16",
                                        fontWeight: 500,
                                    },
                                },
                            },
                        },
                    },
                    color: '#05d863',
                    barWidth: 30,
                    data: [echartsData?.constructActualDay]
                },
                {
                    type: "bar",
                    name: echartsData?.handoverDelayDay > 0 ? `????????????????????????\r\n${echartsData?.handoverDatePlan}` : `??????????????????\r\n${echartsData?.handoverDateActrual}`,
                    stack: "1",
                    label: {
                        normal: {
                            show: true,
                            formatter: echartsData?.handoverDelayDay > 0 ? `????????????????????????\r\n${echartsData?.handoverDatePlan}` : `??????????????????\r\n${echartsData?.handoverDateActrual}`,
                            position: [-30,40],
                            textStyle: {
                                color: "#fff",
                                rich: {
                                    value: {
                                        fontSize: "16",
                                        fontWeight: 500,
                                    },
                                },
                            },
                        },
                    },
                    color: '#f32668',
                    barWidth: 30,
                    data: [echartsData?.handoverDelayDay]
                },
                {
                    type: "bar",
                    name: echartsData?.handoverDelayDay <= 0 ? `????????????????????????\r\n${echartsData?.handoverDatePlan}` : `??????????????????\r\n${echartsData?.handoverDateActrual}`,
                    stack: "1",
                    label: {
                        normal: {
                            show: true,
                            formatter: echartsData?.handoverDelayDay <= 0 ? `????????????????????????\r\n${echartsData?.handoverDatePlan}` : `??????????????????\r\n${echartsData?.handoverDateActrual}`,
                            position: [-30,-30],
                            textStyle: {
                                color: "#fff",
                                rich: {
                                    value: {
                                        fontSize: "16",
                                        fontWeight: 500,
                                    },
                                },
                            },
                        },
                    },
                    barWidth: 30,
                    data: [0]
                }
            ]
        } else if (constructEnd === '0' && dateType === '2' && echartsData?.periodId === '13') {
            option.title = [
                {
                    text: echartsData?.periodFlag,
                    x: "center",
                    y: 10,
                    textStyle: {
                        color: 'rgba(255,255,255,0.5)',
                        fontSize: 18,
                        fontWeight: 'bold'
                    }
                },
                {
                    text: `????????????????????????${echartsData?.constructCurrentDay}`,
                    x: "25%",
                    y: 'bottom',
                    textStyle: {
                        color: 'rgba(255,255,255,0.5)',
                        fontSize: 16,
                        fontWeight: 'bold'
                    }
                },
                {
                    text: `?????????????????????${echartsData?.handoverDay}`,
                    x: "60%",
                    y: 'bottom',
                    textStyle: {
                        color: 'rgba(255,255,255,0.5)',
                        fontSize: 16,
                        fontWeight: 'bold'
                    }
                }
            ];
            option.series = [
                {
                    type: "bar",
                    name: `??????????????????\r\n${echartsData?.actualDate}`,
                    stack: "1",
                    label: {
                        normal: {
                            show: true,
                            formatter: `??????????????????\r\n${echartsData?.actualDate}`,
                            position: [0,-30],
                            textStyle: {
                                color: "#fff",
                                rich: {
                                    value: {
                                        fontSize: "16",
                                        fontWeight: 500,
                                    },
                                },
                            },
                        },
                    },
                    color: '#05d863',
                    barWidth: 30,
                    data: [echartsData?.constructCurrentDay]
                },
                {
                    type: "bar",
                    name: echartsData?.handoverDay > 0 ? `????????????\r\n${echartsData?.currDate}` : `????????????????????????\r\n${echartsData?.handoverDatePlan}`,
                    stack: "1",
                    label: {
                        normal: {
                            show: true,
                            formatter: echartsData?.handoverDay > 0 ? `????????????\r\n${echartsData?.currDate}` : `????????????????????????\r\n${echartsData?.handoverDatePlan}`,
                            position: [-30,40],
                            textStyle: {
                                color: "#fff",
                                rich: {
                                    value: {
                                        fontSize: "16",
                                        fontWeight: 500,
                                    },
                                },
                            },
                        },
                    },
                    itemStyle: {
                        color: 'none',
                        borderColor: "#05d863",
                        borderWidth: 3,
                    },
                    barWidth: 20,
                    data: [echartsData?.handoverDay]
                },
                {
                    type: "bar",
                    name: echartsData?.handoverDay <= 0 ? `????????????\r\n${echartsData?.currDate}` : `????????????????????????\r\n${echartsData?.handoverDatePlan}`,
                    stack: "1",
                    label: {
                        normal: {
                            show: true,
                            formatter: echartsData?.handoverDay <= 0 ? `????????????\r\n${echartsData?.currDate}` : `????????????????????????\r\n${echartsData?.handoverDatePlan}`,
                            position: [-30,-30],
                            textStyle: {
                                color: "#fff",
                                rich: {
                                    value: {
                                        fontSize: "16",
                                        fontWeight: 500,
                                    },
                                },
                            },
                        },
                    },
                    barWidth: 30,
                    data: [0]
                }
            ]
        } else if (constructEnd === '0' && dateType === '2' && echartsData?.periodId === '14') {
            option.title = [
                {
                    text: echartsData?.periodFlag,
                    x: "center",
                    y: 10,
                    textStyle: {
                        color: 'rgba(255,255,255,0.5)',
                        fontSize: 18,
                        fontWeight: 'bold'
                    }
                },
                {
                    text: `????????????????????????${echartsData?.constructCurrentDay}`,
                    x: "25%",
                    y: 'bottom',
                    textStyle: {
                        color: 'rgba(255,255,255,0.5)',
                        fontSize: 16,
                        fontWeight: 'bold'
                    }
                },
                {
                    text: `?????????????????????${echartsData?.overdueHandoverDay}`,
                    x: "60%",
                    y: 'bottom',
                    textStyle: {
                        color: 'rgba(255,255,255,0.5)',
                        fontSize: 16,
                        fontWeight: 'bold'
                    }
                }
            ];
            option.series = [
                {
                    type: "bar",
                    name: `??????????????????\r\n${echartsData?.actualDate}`,
                    stack: "1",
                    label: {
                        normal: {
                            show: true,
                            formatter: `??????????????????\r\n${echartsData?.actualDate}`,
                            position: [0,-30],
                            textStyle: {
                                color: "#fff",
                                rich: {
                                    value: {
                                        fontSize: "16",
                                        fontWeight: 500,
                                    },
                                },
                            },
                        },
                    },
                    color: '#05d863',
                    barWidth: 30,
                    data: [echartsData?.constructCurrentDay]
                },
                {
                    type: "bar",
                    name: echartsData?.overdueHandoverDay > 0 ? `????????????????????????\r\n${echartsData?.handoverDatePlan}` : `????????????\r\n${echartsData?.currDate}`,
                    stack: "1",
                    label: {
                        normal: {
                            show: true,
                            formatter: echartsData?.overdueHandoverDay > 0 ? `????????????????????????\r\n${echartsData?.handoverDatePlan}` : `????????????\r\n${echartsData?.currDate}`,
                            position: [-30,40],
                            textStyle: {
                                color: "#fff",
                                rich: {
                                    value: {
                                        fontSize: "16",
                                        fontWeight: 500,
                                    },
                                },
                            },
                        },
                    },
                    itemStyle: {
                        color: 'none',
                        borderColor: "#f32668",
                        borderWidth: 3,
                    },
                    barWidth: 20,
                    data: [echartsData?.overdueHandoverDay]
                },
                {
                    type: "bar",
                    name: echartsData?.overdueHandoverDay <= 0 ? `????????????????????????\r\n${echartsData?.handoverDatePlan}` : `????????????\r\n${echartsData?.currDate}`,
                    stack: "1",
                    label: {
                        normal: {
                            show: true,
                            formatter: echartsData?.overdueHandoverDay <= 0 ? `????????????????????????\r\n${echartsData?.handoverDatePlan}` : `????????????\r\n${echartsData?.currDate}`,
                            position: [-30,-30],
                            textStyle: {
                                color: "#fff",
                                rich: {
                                    value: {
                                        fontSize: "16",
                                        fontWeight: 500,
                                    },
                                },
                            },
                        },
                    },
                    barWidth: 30,
                    data: [0]
                }
            ]
        } else if (constructEnd === '1' && dateType === '2' && !echartsData?.periodId) {
            option.title = {
                text: '??????????????????????????????????????????',
                x: "center",
                y: '40%',
                textStyle: {
                    color: 'rgba(255,255,255,0.5)',
                    fontSize: 25,
                    fontWeight: 'bold'
                }
            };
        } else if (constructEnd === '1' && dateType === '2' && echartsData?.periodId === '1') {
            option.title = [
                {
                    text: echartsData?.periodFlag,
                    x: "center",
                    y: 10,
                    textStyle: {
                        color: 'rgba(255,255,255,0.5)',
                        fontSize: 18,
                        fontWeight: 'bold'
                    }
                },
                {
                    text: `????????????????????????${echartsData?.constructActualDay}`,
                    x: "25%",
                    y: 'bottom',
                    textStyle: {
                        color: 'rgba(255,255,255,0.5)',
                        fontSize: 16,
                        fontWeight: 'bold'
                    }
                },
                {
                    text: `?????????????????????${echartsData?.completeAdvanceDay}`,
                    x: "60%",
                    y: 'bottom',
                    textStyle: {
                        color: 'rgba(255,255,255,0.5)',
                        fontSize: 16,
                        fontWeight: 'bold'
                    }
                }
            ];
            option.series = [
                {
                    type: "bar",
                    name: `??????????????????\r\n${echartsData?.actualDate}`,
                    stack: "1",
                    label: {
                        normal: {
                            show: true,
                            formatter: `??????????????????\r\n${echartsData?.actualDate}`,
                            position: [0,-30],
                            textStyle: {
                                color: "#fff",
                                rich: {
                                    value: {
                                        fontSize: "16",
                                        fontWeight: 500,
                                    },
                                },
                            },
                        },
                    },
                    color: '#05d863',
                    barWidth: 30,
                    data: [echartsData?.constructActualDay]
                },
                {
                    type: "bar",
                    name: echartsData?.completeAdvanceDay > 0 ? `??????????????????\r\n${echartsData?.complateDateActrual}` : `????????????????????????\r\n${echartsData?.complateDatePlan}`,
                    stack: "1",
                    label: {
                        normal: {
                            show: true,
                            formatter: echartsData?.completeAdvanceDay > 0 ? `??????????????????\r\n${echartsData?.complateDateActrual}` : `????????????????????????\r\n${echartsData?.complateDatePlan}`,
                            position: [-30,40],
                            textStyle: {
                                color: "#fff",
                                rich: {
                                    value: {
                                        fontSize: "16",
                                        fontWeight: 500,
                                    },
                                },
                            },
                        },
                    },
                    color: '#2ebcf3',
                    barWidth: 30,
                    data: [echartsData?.completeAdvanceDay]
                },
                {
                    type: "bar",
                    name: echartsData?.completeAdvanceDay <= 0 ? `??????????????????\r\n${echartsData?.complateDateActrual}` : `????????????????????????\r\n${echartsData?.complateDatePlan}`,
                    stack: "1",
                    label: {
                        normal: {
                            show: true,
                            formatter: echartsData?.completeAdvanceDay <= 0 ? `??????????????????\r\n${echartsData?.complateDateActrual}` : `????????????????????????\r\n${echartsData?.complateDatePlan}`,
                            position: [-30,-30],
                            textStyle: {
                                color: "#fff",
                                rich: {
                                    value: {
                                        fontSize: "16",
                                        fontWeight: 500,
                                    },
                                },
                            },
                        },
                    },
                    barWidth: 30,
                    data: [0]
                }
            ]
        } else if (constructEnd === '1' && dateType === '2' && echartsData?.periodId === '2') {
            option.title = [
                {
                    text: echartsData?.periodFlag,
                    x: "center",
                    y: 10,
                    textStyle: {
                        color: 'rgba(255,255,255,0.5)',
                        fontSize: 18,
                        fontWeight: 'bold'
                    }
                },
                {
                    text: `????????????????????????${echartsData?.constructActualDay}`,
                    x: "25%",
                    y: 'bottom',
                    textStyle: {
                        color: 'rgba(255,255,255,0.5)',
                        fontSize: 16,
                        fontWeight: 'bold'
                    }
                },
                {
                    text: `?????????????????????${echartsData?.completeDelayDay}`,
                    x: "60%",
                    y: 'bottom',
                    textStyle: {
                        color: 'rgba(255,255,255,0.5)',
                        fontSize: 16,
                        fontWeight: 'bold'
                    }
                }
            ];
            option.series = [
                {
                    type: "bar",
                    name: `??????????????????\r\n${echartsData?.actualDate}`,
                    stack: "1",
                    label: {
                        normal: {
                            show: true,
                            formatter: `??????????????????\r\n${echartsData?.actualDate}`,
                            position: [0,-30],
                            textStyle: {
                                color: "#fff",
                                rich: {
                                    value: {
                                        fontSize: "16",
                                        fontWeight: 500,
                                    },
                                },
                            },
                        },
                    },
                    color: '#05d863',
                    barWidth: 30,
                    data: [echartsData?.constructActualDay]
                },
                {
                    type: "bar",
                    name: echartsData?.completeDelayDay > 0 ? `????????????????????????\r\n${echartsData?.complateDatePlan}` : `??????????????????\r\n${echartsData?.complateDateActrual}`,
                    stack: "1",
                    label: {
                        normal: {
                            show: true,
                            formatter: echartsData?.completeDelayDay > 0 ? `????????????????????????\r\n${echartsData?.complateDatePlan}` : `??????????????????\r\n${echartsData?.complateDateActrual}`,
                            position: [-30,40],
                            textStyle: {
                                color: "#fff",
                                rich: {
                                    value: {
                                        fontSize: "16",
                                        fontWeight: 500,
                                    },
                                },
                            },
                        },
                    },
                    color: '#f32668',
                    barWidth: 30,
                    data: [echartsData?.completeDelayDay]
                },
                {
                    type: "bar",
                    name: echartsData?.completeDelayDay <= 0 ? `????????????????????????\r\n${echartsData?.complateDatePlan}` : `??????????????????\r\n${echartsData?.complateDateActrual}`,
                    stack: "1",
                    label: {
                        normal: {
                            show: true,
                            formatter: echartsData?.completeDelayDay <= 0 ? `????????????????????????\r\n${echartsData?.complateDatePlan}` : `??????????????????\r\n${echartsData?.complateDateActrual}`,
                            position: [-30,-30],
                            textStyle: {
                                color: "#fff",
                                rich: {
                                    value: {
                                        fontSize: "16",
                                        fontWeight: 500,
                                    },
                                },
                            },
                        },
                    },
                    barWidth: 30,
                    data: [0]
                }
            ]
        } else if (constructEnd === '1' && dateType === '2' && echartsData?.periodId === '3') {
            option.title = [
                {
                    text: echartsData?.periodFlag,
                    x: "center",
                    y: 10,
                    textStyle: {
                        color: 'rgba(255,255,255,0.5)',
                        fontSize: 18,
                        fontWeight: 'bold'
                    }
                },
                {
                    text: `????????????????????????${echartsData?.constructCurrentDay}`,
                    x: "25%",
                    y: 'bottom',
                    textStyle: {
                        color: 'rgba(255,255,255,0.5)',
                        fontSize: 16,
                        fontWeight: 'bold'
                    }
                },
                {
                    text: `?????????????????????${echartsData?.completeDay}`,
                    x: "60%",
                    y: 'bottom',
                    textStyle: {
                        color: 'rgba(255,255,255,0.5)',
                        fontSize: 16,
                        fontWeight: 'bold'
                    }
                }
            ];
            option.series = [
                {
                    type: "bar",
                    name: `??????????????????\r\n${echartsData?.actualDate}`,
                    stack: "1",
                    label: {
                        normal: {
                            show: true,
                            formatter: `??????????????????\r\n${echartsData?.actualDate}`,
                            position: [0,-30],
                            textStyle: {
                                color: "#fff",
                                rich: {
                                    value: {
                                        fontSize: "16",
                                        fontWeight: 500,
                                    },
                                },
                            },
                        },
                    },
                    color: '#05d863',
                    barWidth: 30,
                    data: [echartsData?.constructCurrentDay]
                },
                {
                    type: "bar",
                    name: echartsData?.completeDay > 0 ? `????????????\r\n${echartsData?.currDate}` : `????????????????????????\r\n${echartsData?.complateDatePlan}`,
                    stack: "1",
                    label: {
                        normal: {
                            show: true,
                            formatter: echartsData?.completeDay > 0 ? `????????????\r\n${echartsData?.currDate}` : `????????????????????????\r\n${echartsData?.complateDatePlan}`,
                            position: [-30,40],
                            textStyle: {
                                color: "#fff",
                                rich: {
                                    value: {
                                        fontSize: "16",
                                        fontWeight: 500,
                                    },
                                },
                            },
                        },
                    },
                    itemStyle: {
                        color: 'none',
                        borderColor: "#05d863",
                        borderWidth: 3,
                    },
                    barWidth: 20,
                    data: [echartsData?.completeDay]
                },
                {
                    type: "bar",
                    name: echartsData?.completeDay <= 0 ? `????????????\r\n${echartsData?.currDate}` : `????????????????????????\r\n${echartsData?.complateDatePlan}`,
                    stack: "1",
                    label: {
                        normal: {
                            show: true,
                            formatter: echartsData?.completeDay <= 0 ? `????????????\r\n${echartsData?.currDate}` : `????????????????????????\r\n${echartsData?.complateDatePlan}`,
                            position: [-30,-30],
                            textStyle: {
                                color: "#fff",
                                rich: {
                                    value: {
                                        fontSize: "16",
                                        fontWeight: 500,
                                    },
                                },
                            },
                        },
                    },
                    barWidth: 30,
                    data: [0]
                }
            ]
        } else if (constructEnd === '1' && dateType === '2' && echartsData?.periodId === '4') {
            option.title = [
                {
                    text: echartsData?.periodFlag,
                    x: "center",
                    y: 10,
                    textStyle: {
                        color: 'rgba(255,255,255,0.5)',
                        fontSize: 18,
                        fontWeight: 'bold'
                    }
                },
                {
                    text: `????????????????????????${echartsData?.constructCurrentDay}`,
                    x: "25%",
                    y: 'bottom',
                    textStyle: {
                        color: 'rgba(255,255,255,0.5)',
                        fontSize: 16,
                        fontWeight: 'bold'
                    }
                },
                {
                    text: `?????????????????????${echartsData?.overduecompleteDay}`,
                    x: "60%",
                    y: 'bottom',
                    textStyle: {
                        color: 'rgba(255,255,255,0.5)',
                        fontSize: 16,
                        fontWeight: 'bold'
                    }
                }
            ];
            option.series = [
                {
                    type: "bar",
                    name: `??????????????????\r\n${echartsData?.actualDate}`,
                    stack: "1",
                    label: {
                        normal: {
                            show: true,
                            formatter: `??????????????????\r\n${echartsData?.actualDate}`,
                            position: [0,-30],
                            textStyle: {
                                color: "#fff",
                                rich: {
                                    value: {
                                        fontSize: "16",
                                        fontWeight: 500,
                                    },
                                },
                            },
                        },
                    },
                    color: '#05d863',
                    barWidth: 30,
                    data: [echartsData?.constructCurrentDay]
                },
                {
                    type: "bar",
                    name: echartsData?.overduecompleteDay > 0 ? `????????????????????????\r\n${echartsData?.complateDatePlan}` : `????????????\r\n${echartsData?.currDate}`,
                    stack: "1",
                    label: {
                        normal: {
                            show: true,
                            formatter: echartsData?.overduecompleteDay > 0 ? `????????????????????????\r\n${echartsData?.complateDatePlan}` : `????????????\r\n${echartsData?.currDate}`,
                            position: [-30,40],
                            textStyle: {
                                color: "#fff",
                                rich: {
                                    value: {
                                        fontSize: "16",
                                        fontWeight: 500,
                                    },
                                },
                            },
                        },
                    },
                    itemStyle: {
                        color: 'none',
                        borderColor: "#f32668",
                        borderWidth: 3,
                    },
                    barWidth: 20,
                    data: [echartsData?.overduecompleteDay]
                },
                {
                    type: "bar",
                    name: echartsData?.overduecompleteDay <= 0 ? `????????????????????????\r\n${echartsData?.complateDatePlan}` : `????????????\r\n${echartsData?.currDate}`,
                    stack: "1",
                    label: {
                        normal: {
                            show: true,
                            formatter: echartsData?.overduecompleteDay <= 0 ? `????????????????????????\r\n${echartsData?.complateDatePlan}` : `????????????\r\n${echartsData?.currDate}`,
                            position: [-30,-30],
                            textStyle: {
                                color: "#fff",
                                rich: {
                                    value: {
                                        fontSize: "16",
                                        fontWeight: 500,
                                    },
                                },
                            },
                        },
                    },
                    barWidth: 30,
                    data: [0]
                }
            ]
        } else if (constructEnd === '1' && dateType === '2' && (echartsData?.periodId === '5' || echartsData?.periodId === '6')) {
            option.title = [
                {
                    text: echartsData?.periodFlag,
                    x: "center",
                    y: 10,
                    textStyle: {
                        color: 'rgba(255,255,255,0.5)',
                        fontSize: 18,
                        fontWeight: 'bold'
                    }
                },
                {
                    text: `???????????????????????????${echartsData?.completeActualDay}`,
                    x: "25%",
                    y: 'bottom',
                    textStyle: {
                        color: 'rgba(255,255,255,0.5)',
                        fontSize: 16,
                        fontWeight: 'bold'
                    }
                },
                {
                    text: `?????????????????????${echartsData?.completeAdvanceDay}`,
                    x: "60%",
                    y: 'bottom',
                    textStyle: {
                        color: 'rgba(255,255,255,0.5)',
                        fontSize: 16,
                        fontWeight: 'bold'
                    }
                }
            ];
            option.series = [
                {
                    type: "bar",
                    name: `??????????????????\r\n${echartsData?.handoverDateActrual}`,
                    stack: "1",
                    label: {
                        normal: {
                            show: true,
                            formatter: `??????????????????\r\n${echartsData?.handoverDateActrual}`,
                            position: [0,-30],
                            textStyle: {
                                color: "#fff",
                                rich: {
                                    value: {
                                        fontSize: "16",
                                        fontWeight: 500,
                                    },
                                },
                            },
                        },
                    },
                    color: '#05d863',
                    barWidth: 30,
                    data: [echartsData?.completeActualDay]
                },
                {
                    type: "bar",
                    name: echartsData?.completeAdvanceDay > 0 ? `??????????????????\r\n${echartsData?.complateDateActrual}` : `????????????????????????\r\n${echartsData?.complateDatePlan}`,
                    stack: "1",
                    label: {
                        normal: {
                            show: true,
                            formatter: echartsData?.completeAdvanceDay > 0 ? `??????????????????\r\n${echartsData?.complateDateActrual}` : `????????????????????????\r\n${echartsData?.complateDatePlan}`,
                            position: [-30,40],
                            textStyle: {
                                color: "#fff",
                                rich: {
                                    value: {
                                        fontSize: "16",
                                        fontWeight: 500,
                                    },
                                },
                            },
                        },
                    },
                    color: '#2ebcf3',
                    barWidth: 30,
                    data: [echartsData?.completeAdvanceDay]
                },
                {
                    type: "bar",
                    name: echartsData?.completeAdvanceDay <= 0 ? `??????????????????\r\n${echartsData?.complateDateActrual}` : `????????????????????????\r\n${echartsData?.complateDatePlan}`,
                    stack: "1",
                    label: {
                        normal: {
                            show: true,
                            formatter: echartsData?.completeAdvanceDay <= 0 ? `??????????????????\r\n${echartsData?.complateDateActrual}` : `????????????????????????\r\n${echartsData?.complateDatePlan}`,
                            position: [-30,-30],
                            textStyle: {
                                color: "#fff",
                                rich: {
                                    value: {
                                        fontSize: "16",
                                        fontWeight: 500,
                                    },
                                },
                            },
                        },
                    },
                    barWidth: 30,
                    data: [0]
                }
            ]
        } else if (constructEnd === '1' && dateType === '2' && (echartsData?.periodId === '7' || echartsData?.periodId === '8')) {
            option.title = [
                {
                    text: echartsData?.periodFlag,
                    x: "center",
                    y: 10,
                    textStyle: {
                        color: 'rgba(255,255,255,0.5)',
                        fontSize: 18,
                        fontWeight: 'bold'
                    }
                },
                {
                    text: `???????????????????????????${echartsData?.completeActualDay}`,
                    x: "25%",
                    y: 'bottom',
                    textStyle: {
                        color: 'rgba(255,255,255,0.5)',
                        fontSize: 16,
                        fontWeight: 'bold'
                    }
                },
                {
                    text: `?????????????????????${echartsData?.completeDelayDay}`,
                    x: "60%",
                    y: 'bottom',
                    textStyle: {
                        color: 'rgba(255,255,255,0.5)',
                        fontSize: 16,
                        fontWeight: 'bold'
                    }
                }
            ];
            option.series = [
                {
                    type: "bar",
                    name: `??????????????????\r\n${echartsData?.handoverDateActrual}`,
                    stack: "1",
                    label: {
                        normal: {
                            show: true,
                            formatter: `??????????????????\r\n${echartsData?.handoverDateActrual}`,
                            position: [0,-30],
                            textStyle: {
                                color: "#fff",
                                rich: {
                                    value: {
                                        fontSize: "16",
                                        fontWeight: 500,
                                    },
                                },
                            },
                        },
                    },
                    color: '#05d863',
                    barWidth: 30,
                    data: [echartsData?.completeActualDay]
                },
                {
                    type: "bar",
                    name: echartsData?.completeDelayDay > 0 ? `??????????????????\r\n${echartsData?.complateDateActrual}` : `????????????????????????\r\n${echartsData?.complateDatePlan}`,
                    stack: "1",
                    label: {
                        normal: {
                            show: true,
                            formatter: echartsData?.completeDelayDay > 0 ? `??????????????????\r\n${echartsData?.complateDateActrual}` : `????????????????????????\r\n${echartsData?.complateDatePlan}`,
                            position: [-30,40],
                            textStyle: {
                                color: "#fff",
                                rich: {
                                    value: {
                                        fontSize: "16",
                                        fontWeight: 500,
                                    },
                                },
                            },
                        },
                    },
                    color: '#f32668',
                    barWidth: 30,
                    data: [echartsData?.completeDelayDay]
                },
                {
                    type: "bar",
                    name: echartsData?.completeDelayDay <= 0 ? `??????????????????\r\n${echartsData?.complateDateActrual}` : `????????????????????????\r\n${echartsData?.complateDatePlan}`,
                    stack: "1",
                    label: {
                        normal: {
                            show: true,
                            formatter: echartsData?.completeDelayDay <= 0 ? `??????????????????\r\n${echartsData?.complateDateActrual}` : `????????????????????????\r\n${echartsData?.complateDatePlan}`,
                            position: [-30,-30],
                            textStyle: {
                                color: "#fff",
                                rich: {
                                    value: {
                                        fontSize: "16",
                                        fontWeight: 500,
                                    },
                                },
                            },
                        },
                    },
                    barWidth: 30,
                    data: [0]
                }
            ]
        } else if (constructEnd === '1' && dateType === '2' && (echartsData?.periodId === '9' || echartsData?.periodId === '10')) {
            option.title = [
                {
                    text: echartsData?.periodFlag,
                    x: "center",
                    y: 10,
                    textStyle: {
                        color: 'rgba(255,255,255,0.5)',
                        fontSize: 18,
                        fontWeight: 'bold'
                    }
                },
                {
                    text: `???????????????????????????${echartsData?.completeCurrentDay}`,
                    x: "25%",
                    y: 'bottom',
                    textStyle: {
                        color: 'rgba(255,255,255,0.5)',
                        fontSize: 16,
                        fontWeight: 'bold'
                    }
                },
                {
                    text: `?????????????????????${echartsData?.completeDay}`,
                    x: "60%",
                    y: 'bottom',
                    textStyle: {
                        color: 'rgba(255,255,255,0.5)',
                        fontSize: 16,
                        fontWeight: 'bold'
                    }
                }
            ];
            option.series = [
                {
                    type: "bar",
                    name: `??????????????????\r\n${echartsData?.handoverDateActrual}`,
                    stack: "1",
                    label: {
                        normal: {
                            show: true,
                            formatter: `??????????????????\r\n${echartsData?.handoverDateActrual}`,
                            position: [0,-30],
                            textStyle: {
                                color: "#fff",
                                rich: {
                                    value: {
                                        fontSize: "16",
                                        fontWeight: 500,
                                    },
                                },
                            },
                        },
                    },
                    color: '#05d863',
                    barWidth: 30,
                    data: [echartsData?.completeCurrentDay]
                },
                {
                    type: "bar",
                    name: echartsData?.completeDay > 0 ? `????????????\r\n${echartsData?.currDate}` : `????????????????????????\r\n${echartsData?.complateDatePlan}`,
                    stack: "1",
                    label: {
                        normal: {
                            show: true,
                            formatter: echartsData?.completeDay > 0 ? `????????????\r\n${echartsData?.currDate}` : `????????????????????????\r\n${echartsData?.complateDatePlan}`,
                            position: [-30,40],
                            textStyle: {
                                color: "#fff",
                                rich: {
                                    value: {
                                        fontSize: "16",
                                        fontWeight: 500,
                                    },
                                },
                            },
                        },
                    },
                    itemStyle: {
                        color: 'none',
                        borderColor: "#05d863",
                        borderWidth: 3,
                    },
                    barWidth: 20,
                    data: [echartsData?.completeDay]
                },
                {
                    type: "bar",
                    name: echartsData?.completeDay <= 0 ? `????????????\r\n${echartsData?.currDate}` : `????????????????????????\r\n${echartsData?.complateDatePlan}`,
                    stack: "1",
                    label: {
                        normal: {
                            show: true,
                            formatter: echartsData?.completeDay <= 0 ? `????????????\r\n${echartsData?.currDate}` : `????????????????????????\r\n${echartsData?.complateDatePlan}`,
                            position: [-30,-30],
                            textStyle: {
                                color: "#fff",
                                rich: {
                                    value: {
                                        fontSize: "16",
                                        fontWeight: 500,
                                    },
                                },
                            },
                        },
                    },
                    barWidth: 30,
                    data: [0]
                }
            ]
        } else if (constructEnd === '1' && dateType === '2' && (echartsData?.periodId === '11' || echartsData?.periodId === '12')) {
            option.title = [
                {
                    text: echartsData?.periodFlag,
                    x: "center",
                    y: 10,
                    textStyle: {
                        color: 'rgba(255,255,255,0.5)',
                        fontSize: 18,
                        fontWeight: 'bold'
                    }
                },
                {
                    text: `???????????????????????????${echartsData?.completeCurrentDay}`,
                    x: "25%",
                    y: 'bottom',
                    textStyle: {
                        color: 'rgba(255,255,255,0.5)',
                        fontSize: 16,
                        fontWeight: 'bold'
                    }
                },
                {
                    text: `?????????????????????${echartsData?.overduecompleteDay}`,
                    x: "60%",
                    y: 'bottom',
                    textStyle: {
                        color: 'rgba(255,255,255,0.5)',
                        fontSize: 16,
                        fontWeight: 'bold'
                    }
                }
            ];
            option.series = [
                {
                    type: "bar",
                    name: `??????????????????\r\n${echartsData?.handoverDateActrual}`,
                    stack: "1",
                    label: {
                        normal: {
                            show: true,
                            formatter: `??????????????????\r\n${echartsData?.handoverDateActrual}`,
                            position: [0,-30],
                            textStyle: {
                                color: "#fff",
                                rich: {
                                    value: {
                                        fontSize: "16",
                                        fontWeight: 500,
                                    },
                                },
                            },
                        },
                    },
                    color: '#05d863',
                    barWidth: 30,
                    data: [echartsData?.completeCurrentDay]
                },
                {
                    type: "bar",
                    name: echartsData?.overduecompleteDay > 0 ? `????????????????????????\r\n${echartsData?.complateDatePlan}` : `????????????\r\n${echartsData?.currDate}`,
                    stack: "1",
                    label: {
                        normal: {
                            show: true,
                            formatter: echartsData?.overduecompleteDay > 0 ? `????????????????????????\r\n${echartsData?.complateDatePlan}` : `????????????\r\n${echartsData?.currDate}`,
                            position: [-30,40],
                            textStyle: {
                                color: "#fff",
                                rich: {
                                    value: {
                                        fontSize: "16",
                                        fontWeight: 500,
                                    },
                                },
                            },
                        },
                    },
                    itemStyle: {
                        color: 'none',
                        borderColor: "#f32668",
                        borderWidth: 3,
                    },
                    barWidth: 20,
                    data: [echartsData?.overduecompleteDay]
                },
                {
                    type: "bar",
                    name: echartsData?.overduecompleteDay <= 0 ? `????????????????????????\r\n${echartsData?.complateDatePlan}` : `????????????\r\n${echartsData?.currDate}`,
                    stack: "1",
                    label: {
                        normal: {
                            show: true,
                            formatter: echartsData?.overduecompleteDay <= 0 ? `????????????????????????\r\n${echartsData?.complateDatePlan}` : `????????????\r\n${echartsData?.currDate}`,
                            position: [-30,-30],
                            textStyle: {
                                color: "#fff",
                                rich: {
                                    value: {
                                        fontSize: "16",
                                        fontWeight: 500,
                                    },
                                },
                            },
                        },
                    },
                    barWidth: 30,
                    data: [0]
                }
            ]
        }
        return option;
    }

    render () {
        const { analySubject } = this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany;
        const {
            loading,
            optionData,
            subprojectId,
            echartsData
        } = this.state;
        return (
            <div className={s.duration}>
                <div className={s.leftTop}>
                    <div className={s.leftTopOneL}>
                        ????????????
                    </div>
                    <div className={s.leftTopOneR}>
                        {(analySubject === '1' && echartsData || analySubject !== '1') ? <QnnForm
                            fetch={this.props.myFetch}
                            upload={this.props.myUpload}
                            headers={{
                                token: this.props.loginAndLogoutInfo.loginInfo.token
                            }}
                            wrappedComponentRef={(me) => {
                                this.formWD = me;
                            }}
                            formItemLayout={{
                                labelCol: {
                                    xs: { span: 11 },
                                    sm: { span: 11 }
                                },
                                wrapperCol: {
                                    xs: { span: 13 },
                                    sm: { span: 13 }
                                }
                            }}
                            formConfig={[
                                {
                                    label: '??????',
                                    field: 'constructEnd',
                                    type: 'select',
                                    optionConfig: {
                                        label: 'label',
                                        value: 'value'
                                    },
                                    optionData: [
                                        {
                                            label: '??????',
                                            value: '0'
                                        },
                                        {
                                            label: '??????',
                                            value: '1'
                                        }
                                    ],
                                    initialValue: '0',
                                    allowClear: false,
                                    onChange: () => {
                                        this.refresh();
                                    },
                                    span: 8,
                                    labelStyle: {
                                        color: 'rgba(255,255,255,0.5)'
                                    },
                                    placeholder: '?????????'
                                },
                                {
                                    label: '???????????????',
                                    field: 'subprojectId',
                                    type: 'select',
                                    optionConfig: {
                                        label: 'subprojectName',
                                        value: 'subprojectInfoId'
                                    },
                                    optionData: optionData,
                                    initialValue: subprojectId,
                                    onChange: () => {
                                        this.refresh();
                                    },
                                    span: 8,
                                    labelStyle: {
                                        color: 'rgba(255,255,255,0.5)'
                                    },
                                    allowClear: false,
                                    placeholder: '?????????'
                                },
                                {
                                    label: '??????????????????',
                                    field: 'dateType',
                                    type: 'select',
                                    optionConfig: {
                                        label: 'label',
                                        value: 'value'
                                    },
                                    optionData: [
                                        {
                                            label: '????????????',
                                            value: '1'
                                        },
                                        {
                                            label: '????????????',
                                            value: '2'
                                        }
                                    ],
                                    initialValue: '1',
                                    allowClear: false,
                                    onChange: () => {
                                        this.refresh();
                                    },
                                    span: 8,
                                    labelStyle: {
                                        color: 'rgba(255,255,255,0.5)'
                                    },
                                    placeholder: '?????????'
                                },
                            ]}
                        /> : null}
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