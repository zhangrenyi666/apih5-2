import React, { Component } from "react";
import QnnForm from "../../../modules/qnn-form";
import QnnTable from "../../../modules/qnn-table";
import s from './style.less';
import { Spin } from "antd";
import $ from 'jquery';
import moment from 'moment';
const one = require('../../../../../src/imgs/one.png');
const tow = require('../../../../../src/imgs/tow.png');
const three = require('../../../../../src/imgs/three.png');
class index extends Component {
    constructor(props) {
        super(props);
        this.state = {
            loading: false,
            leftTableConfig: [
                {
                    table: {
                        title: '',
                        dataIndex: 'ranking',
                        width: 50,
                        align: "center",
                        render: (data, rowData) => {
                            if (rowData.dataType === '1') {
                                if (rowData.bjqytzpm === 1) {
                                    return <img style={{ height: '20px' }} src={one} />;
                                } else if (rowData.bjqytzpm === 2) {
                                    return <img style={{ height: '20px' }} src={tow} />;
                                } else if (rowData.bjqytzpm === 3) {
                                    return <img style={{ height: '20px' }} src={three} />;
                                } else {
                                    return <div style={{ height: '21px' }}>{rowData.bjqytzpm}</div>
                                }
                            } else if (rowData.dataType === '2') {
                                if (rowData.bjjjafpm === 1) {
                                    return <img style={{ height: '20px' }} src={one} />;
                                } else if (rowData.bjjjafpm === 2) {
                                    return <img style={{ height: '20px' }} src={tow} />;
                                } else if (rowData.bjjjafpm === 3) {
                                    return <img style={{ height: '20px' }} src={three} />;
                                } else {
                                    return <div style={{ height: '21px' }}>{rowData.bjjjafpm}</div>
                                }
                            } else if (rowData.dataType === '3') {
                                if (rowData.pmztz === 1) {
                                    return <img style={{ height: '20px' }} src={one} />;
                                } else if (rowData.pmztz === 2) {
                                    return <img style={{ height: '20px' }} src={tow} />;
                                } else if (rowData.pmztz === 3) {
                                    return <img style={{ height: '20px' }} src={three} />;
                                } else {
                                    return <div style={{ height: '21px' }}>{rowData.pmztz}</div>
                                }
                            }
                        }
                    }
                },
                {
                    table: {
                        title: '',
                        dataIndex: 'shortName',
                        width: 100,
                        tooltip: 15,
                        align: "center"
                    }
                },
                {
                    table: {
                        title: '',
                        dataIndex: 'dataType',
                        width: 50,
                        align: "center",
                        render: (data, rowData) => {
                            if (rowData.dataType === '1') {
                                if (rowData.bjqytzbfb) {
                                    return rowData.bjqytzbfb.toFixed(2) + '%';
                                } else {
                                    return '0.00%';
                                }
                            } else if (rowData.dataType === '2') {
                                if (rowData.bjjjafbfb) {
                                    return rowData.bjjjafbfb.toFixed(2) + '%';
                                } else {
                                    return '0.00%';
                                }
                            } else if (rowData.dataType === '3') {
                                if (rowData.bnztzbfb) {
                                    return rowData.bnztzbfb.toFixed(2) + '%';
                                } else {
                                    return '0.00%';
                                }
                            } else {
                                return '--';
                            }
                        }
                    }
                },
                {
                    table: {
                        title: '',
                        dataIndex: 'dataType1',
                        width: 30,
                        align: "center",
                        render: (data, rowData) => {
                            if (rowData.dataType === '1') {
                                let difference = (rowData?.sjqytzpm || 0) - (rowData?.bjqytzpm || 0);
                                if (difference === 0) {
                                    return <div style={{ color: 'yellow', fontWeight: 'bold', fontSize: '13px' }}>{rowData?.sjqytzpm || '0'}</div>
                                } else if (difference < 0) {
                                    return <div style={{ color: '#fb4141', fontWeight: 'bold', fontSize: '13px' }}>{rowData?.sjqytzpm || '0'}↓</div>
                                } else if (difference > 0) {
                                    return <div style={{ color: '#3af43a', fontWeight: 'bold', fontSize: '13px' }}>{rowData?.sjqytzpm || '0'}↑</div>
                                }
                            } else if (rowData.dataType === '2') {
                                let difference = (rowData?.sjjjafpm || 0) - (rowData?.bjjjafpm || 0);
                                if (difference === 0) {
                                    return <div style={{ color: 'yellow', fontWeight: 'bold', fontSize: '13px' }}>{rowData?.sjjjafpm || '0'}</div>
                                } else if (difference < 0) {
                                    return <div style={{ color: '#fb4141', fontWeight: 'bold', fontSize: '13px' }}>{rowData?.sjjjafpm || '0'}↓</div>
                                } else if (difference > 0) {
                                    return <div style={{ color: '#3af43a', fontWeight: 'bold', fontSize: '13px' }}>{rowData?.sjjjafpm || '0'}↑</div>
                                }
                            } else {
                                return '--';
                            }
                        }
                    }
                },
            ],
            leftTableData: [],
            tableHeight: 850 - 112,
            formData: {},
            type: '1'
        }
    }
    componentDidMount() {
        this.resize();
        window.addEventListener("resize", this.resize, false);
        this.refresh();
    }
    //滚动列表数据
    scrollTableData = () => {
        const timer = 1 * 2000; //动画间隔

        const aniFn = ($dom, dataName, tdHeight = 24) => {
            const dataList = this.state[dataName];
            let bodyHeight = $(`.AssessmentRankings .ant-table-body`).height();
            if ((dataList.length * 24) <= (bodyHeight + 2)) {
                clearInterval(this.scrollLeftTableDataTimerA);
                return;
            }
            $dom.css({
                transition: `${timer / 1000}s`,
                transform: `translateY(-${tdHeight}px)`
            });
            setTimeout(() => {
                let delEle = dataList.shift();
                dataList.push(delEle);
                this.setState({
                    [dataName]: dataList
                }, () => {
                    $dom.css({
                        transition: `0s`,
                        transform: `translateY(-0px)`
                    })
                })
            }, timer)
        }

        //执行两表格的动画 
        clearInterval(this.scrollLeftTableDataTimerA);
        this.scrollLeftTableDataTimerA = setInterval(() => aniFn($(`.AssessmentRankings .ant-table-body table`).eq(0), "leftTableData"), timer * 2);

    }

    componentWillUnmount() {
        window.removeEventListener("resize", this.resize);
        clearInterval(this.scrollLeftTableDataTimerA);
    }
    resize = () => {
        this.setState({
            tableHeight: 850 - 112,
        })
    };
    refresh = () => {
        let formData = this.formA?.form?.getFieldsValue?.() || {};
        let formData1 = this.formAA?.form?.getFieldsValue?.() || {};
        let dataType = '';
        if (formData) {
            if (formData.type === '2') {
                formData.endTime = moment(formData.year).format('YYYY');
                formData1.dataType = '3';
            } else {
                let quarterName = '';
                if (moment(formData.quarter).quarter() === 1) {
                    quarterName = '第一季度';
                } else if (moment(formData.quarter).quarter() === 2) {
                    quarterName = '第二季度';
                } else if (moment(formData.quarter).quarter() === 3) {
                    quarterName = '第三季度';
                } else if (moment(formData.quarter).quarter() === 4) {
                    quarterName = '第四季度';
                }
                formData.endTime = moment(formData.quarter).format('YYYY') + quarterName;
            }
        } else {
            let quarterName = '';
            let quarter = moment().quarter(moment().quarter() - 1).startOf('quarter').valueOf();
            if (moment(quarter).quarter() === 1) {
                quarterName = '第一季度';
            } else if (moment(quarter).quarter() === 2) {
                quarterName = '第二季度';
            } else if (moment(quarter).quarter() === 3) {
                quarterName = '第三季度';
            } else if (moment(quarter).quarter() === 4) {
                quarterName = '第四季度';
            }
            formData = {
                endTime: moment(quarter).format('YYYY') + quarterName
            }
            formData1.dataType = '1';
        }
        if (formData1.dataType === '1') {
            dataType = '权益投资计划完成情况';
        } else if (formData1.dataType === '2') {
            dataType = '局建安费计划完成情况';
        } else if (formData1.dataType === '3') {
            dataType = '总投资计划完成情况';
        }
        let body = {
            dataType: formData1.dataType,
            endTime: formData.endTime
        }
        this.setState({
            loading: true,
            formData: {
                dataType,
                endTime: formData.endTime
            }
        })
        this.props.myFetch('getHomeProgressWarningChecking', body).then(
            ({ success, message, data }) => {
                if (success) {
                    this.setState({
                        loading: false,
                        leftTableData: data.map((item, index) => {
                            return {
                                ...item,
                                ranking: index + 1
                            }
                        })
                    }, () => {
                        this.scrollTableData();
                    })
                } else {
                    this.setState({
                        loading: false
                    })
                }
            }
        );
    }
    render() {
        const {
            loading,
            leftTableData,
            leftTableConfig,
            tableHeight,
            formData,
            type
        } = this.state;
        const qnnTableCommConfig = {
            fetch: this.props.myFetch,
            paginationConfig: false,
            isShowRowSelect: false
        }
        return (
            <div className={s.AssessmentRankings}>
                <div className={s.leftTop}>
                    <div className={s.leftTopL}>
                        考核排名
                    </div>
                    <div className={s.leftTopR}>
                        <QnnForm
                            fetch={this.props.myFetch}
                            upload={this.props.myUpload}
                            headers={{
                                token: this.props.loginAndLogoutInfo.loginInfo.token
                            }}
                            wrappedComponentRef={(me) => {
                                this.formA = me;
                            }}
                            formConfig={[
                                {
                                    label: '',
                                    field: 'type',
                                    type: 'select',
                                    optionConfig: {
                                        label: 'label',
                                        value: 'value'
                                    },
                                    optionData: [
                                        {
                                            label: '季度',
                                            value: '1'
                                        },
                                        {
                                            label: '年度',
                                            value: '2'
                                        }
                                    ],
                                    initialValue: type,
                                    allowClear: false,
                                    onMouseEnter: () => {
                                        clearInterval(this.scrollLeftTableDataTimerA);
                                    },
                                    onMouseLeave: () => {
                                        this.scrollTableData();
                                    },
                                    onChange: (val) => {
                                        if (val === '1') {
                                            this.formAA.form.setFieldsValue({
                                                dataType: '1'
                                            })
                                            this.setState({
                                                type: val,
                                                leftTableData: [],
                                                leftTableConfig: [
                                                    {
                                                        table: {
                                                            title: '',
                                                            dataIndex: 'ranking',
                                                            width: 50,
                                                            align: "center",
                                                            render: (data, rowData) => {
                                                                if (rowData.dataType === '1') {
                                                                    if (rowData.bjqytzpm === 1) {
                                                                        return <img style={{ height: '20px' }} src={one} />;
                                                                    } else if (rowData.bjqytzpm === 2) {
                                                                        return <img style={{ height: '20px' }} src={tow} />;
                                                                    } else if (rowData.bjqytzpm === 3) {
                                                                        return <img style={{ height: '20px' }} src={three} />;
                                                                    } else {
                                                                        return <div style={{ height: '21px' }}>{rowData.bjqytzpm}</div>
                                                                    }
                                                                } else if (rowData.dataType === '2') {
                                                                    if (rowData.bjjjafpm === 1) {
                                                                        return <img style={{ height: '20px' }} src={one} />;
                                                                    } else if (rowData.bjjjafpm === 2) {
                                                                        return <img style={{ height: '20px' }} src={tow} />;
                                                                    } else if (rowData.bjjjafpm === 3) {
                                                                        return <img style={{ height: '20px' }} src={three} />;
                                                                    } else {
                                                                        return <div style={{ height: '21px' }}>{rowData.bjjjafpm}</div>
                                                                    }
                                                                } else if (rowData.dataType === '3') {
                                                                    if (rowData.pmztz === 1) {
                                                                        return <img style={{ height: '20px' }} src={one} />;
                                                                    } else if (rowData.pmztz === 2) {
                                                                        return <img style={{ height: '20px' }} src={tow} />;
                                                                    } else if (rowData.pmztz === 3) {
                                                                        return <img style={{ height: '20px' }} src={three} />;
                                                                    } else {
                                                                        return <div style={{ height: '21px' }}>{rowData.pmztz}</div>
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    },
                                                    {
                                                        table: {
                                                            title: '',
                                                            dataIndex: 'shortName',
                                                            width: 100,
                                                            tooltip: 15,
                                                            align: "center"
                                                        }
                                                    },
                                                    {
                                                        table: {
                                                            title: '',
                                                            dataIndex: 'dataType',
                                                            width: 50,
                                                            align: "center",
                                                            render: (data, rowData) => {
                                                                if (rowData.dataType === '1') {
                                                                    if (rowData.bjqytzbfb) {
                                                                        return rowData.bjqytzbfb.toFixed(2) + '%';
                                                                    } else {
                                                                        return '0.00%';
                                                                    }
                                                                } else if (rowData.dataType === '2') {
                                                                    if (rowData.bjjjafbfb) {
                                                                        return rowData.bjjjafbfb.toFixed(2) + '%';
                                                                    } else {
                                                                        return '0.00%';
                                                                    }
                                                                } else if (rowData.dataType === '3') {
                                                                    if (rowData.bnztzbfb) {
                                                                        return rowData.bnztzbfb.toFixed(2) + '%';
                                                                    } else {
                                                                        return '0.00%';
                                                                    }
                                                                } else {
                                                                    return '--';
                                                                }
                                                            }
                                                        }
                                                    },
                                                    {
                                                        table: {
                                                            title: '',
                                                            dataIndex: 'dataType1',
                                                            width: 30,
                                                            align: "center",
                                                            render: (data, rowData) => {
                                                                if (rowData.dataType === '1') {
                                                                    let difference = (rowData?.sjqytzpm || 0) - (rowData?.bjqytzpm || 0);
                                                                    if (difference === 0) {
                                                                        return <div style={{ color: 'yellow', fontWeight: 'bold', fontSize: '13px' }}>{rowData?.sjqytzpm || '0'}</div>
                                                                    } else if (difference < 0) {
                                                                        return <div style={{ color: '#fb4141', fontWeight: 'bold', fontSize: '13px' }}>{rowData?.sjqytzpm || '0'}↓</div>
                                                                    } else if (difference > 0) {
                                                                        return <div style={{ color: '#3af43a', fontWeight: 'bold', fontSize: '13px' }}>{rowData?.sjqytzpm || '0'}↑</div>
                                                                    }
                                                                } else if (rowData.dataType === '2') {
                                                                    let difference = (rowData?.sjjjafpm || 0) - (rowData?.bjjjafpm || 0);
                                                                    if (difference === 0) {
                                                                        return <div style={{ color: 'yellow', fontWeight: 'bold', fontSize: '13px' }}>{rowData?.sjjjafpm || '0'}</div>
                                                                    } else if (difference < 0) {
                                                                        return <div style={{ color: '#fb4141', fontWeight: 'bold', fontSize: '13px' }}>{rowData?.sjjjafpm || '0'}↓</div>
                                                                    } else if (difference > 0) {
                                                                        return <div style={{ color: '#3af43a', fontWeight: 'bold', fontSize: '13px' }}>{rowData?.sjjjafpm || '0'}↑</div>
                                                                    }
                                                                } else {
                                                                    return '--';
                                                                }
                                                            }
                                                        }
                                                    },
                                                ]
                                            }, () => {
                                                this.refresh();
                                            })
                                        } else {
                                            this.formAA.form.setFieldsValue({
                                                dataType: '3'
                                            })
                                            this.setState({
                                                type: val,
                                                leftTableData: [],
                                                leftTableConfig: [
                                                    {
                                                        table: {
                                                            title: '',
                                                            dataIndex: 'ranking',
                                                            width: 50,
                                                            align: "center",
                                                            render: (data, rowData) => {
                                                                if (rowData.dataType === '1') {
                                                                    if (rowData.bjqytzpm === 1) {
                                                                        return <img style={{ height: '20px' }} src={one} />;
                                                                    } else if (rowData.bjqytzpm === 2) {
                                                                        return <img style={{ height: '20px' }} src={tow} />;
                                                                    } else if (rowData.bjqytzpm === 3) {
                                                                        return <img style={{ height: '20px' }} src={three} />;
                                                                    } else {
                                                                        return <div style={{ height: '21px' }}>{rowData.bjqytzpm}</div>
                                                                    }
                                                                } else if (rowData.dataType === '2') {
                                                                    if (rowData.bjjjafpm === 1) {
                                                                        return <img style={{ height: '20px' }} src={one} />;
                                                                    } else if (rowData.bjjjafpm === 2) {
                                                                        return <img style={{ height: '20px' }} src={tow} />;
                                                                    } else if (rowData.bjjjafpm === 3) {
                                                                        return <img style={{ height: '20px' }} src={three} />;
                                                                    } else {
                                                                        return <div style={{ height: '21px' }}>{rowData.bjjjafpm}</div>
                                                                    }
                                                                } else if (rowData.dataType === '3') {
                                                                    if (rowData.pmztz === 1) {
                                                                        return <img style={{ height: '20px' }} src={one} />;
                                                                    } else if (rowData.pmztz === 2) {
                                                                        return <img style={{ height: '20px' }} src={tow} />;
                                                                    } else if (rowData.pmztz === 3) {
                                                                        return <img style={{ height: '20px' }} src={three} />;
                                                                    } else {
                                                                        return <div style={{ height: '21px' }}>{rowData.pmztz}</div>
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    },
                                                    {
                                                        table: {
                                                            title: '',
                                                            dataIndex: 'shortName',
                                                            width: 100,
                                                            tooltip: 15,
                                                            align: "center"
                                                        }
                                                    },
                                                    {
                                                        table: {
                                                            title: '',
                                                            dataIndex: 'dataType',
                                                            width: 50,
                                                            align: "center",
                                                            render: (data, rowData) => {
                                                                if (rowData.dataType === '1') {
                                                                    if (rowData.bjqytzbfb) {
                                                                        return rowData.bjqytzbfb.toFixed(2) + '%';
                                                                    } else {
                                                                        return '0.00%';
                                                                    }
                                                                } else if (rowData.dataType === '2') {
                                                                    if (rowData.bjjjafbfb) {
                                                                        return rowData.bjjjafbfb.toFixed(2) + '%';
                                                                    } else {
                                                                        return '0.00%';
                                                                    }
                                                                } else if (rowData.dataType === '3') {
                                                                    if (rowData.bnztzbfb) {
                                                                        return rowData.bnztzbfb.toFixed(2) + '%';
                                                                    } else {
                                                                        return '0.00%';
                                                                    }
                                                                } else {
                                                                    return '--';
                                                                }
                                                            }
                                                        }
                                                    }
                                                ]
                                            }, () => {
                                                this.refresh();
                                            })
                                        }

                                    },
                                    span: 12,
                                    placeholder: '请选择'
                                },
                                {
                                    label: '',
                                    field: 'quarter',
                                    type: 'quarter',
                                    allowClear: false,
                                    span: 12,
                                    condition: [
                                        {
                                            regex: {
                                                type: '2'
                                            },
                                            action: 'hide'
                                        }
                                    ],
                                    onMouseEnter: () => {
                                        clearInterval(this.scrollLeftTableDataTimerA);
                                    },
                                    onMouseLeave: () => {
                                        this.scrollTableData();
                                    },
                                    onChange: () => {
                                        this.setState({
                                            leftTableData: []
                                        }, () => {
                                            this.refresh();
                                        })
                                    },
                                    format: 'YYYY-第Q季度',
                                    initialValue: moment().quarter(moment().quarter() - 1).startOf('quarter').valueOf(),
                                    placeholder: '请选择'
                                },
                                {
                                    label: '',
                                    field: 'year',
                                    type: 'year',
                                    allowClear: false,
                                    span: 12,
                                    condition: [
                                        {
                                            regex: {
                                                type: '1'
                                            },
                                            action: 'hide'
                                        }
                                    ],
                                    onMouseEnter: () => {
                                        clearInterval(this.scrollLeftTableDataTimerA);
                                    },
                                    onMouseLeave: () => {
                                        this.scrollTableData();
                                    },
                                    onChange: () => {
                                        this.setState({
                                            leftTableData: []
                                        }, () => {
                                            this.refresh();
                                        })
                                    },
                                    initialValue: moment().year(moment().year() - 1).startOf('year').valueOf(),
                                    placeholder: '请选择'
                                }
                            ]}
                        />
                    </div>
                </div>
                <div className={s.leftCenter}>
                    <div className={s.leftCenterL}>
                        <div>
                            {formData?.endTime}
                        </div>
                        <div>
                            {formData?.dataType}
                        </div>
                    </div>
                    <div className={s.leftCenterR}>
                        {type === '1' ? <QnnForm
                            fetch={this.props.myFetch}
                            upload={this.props.myUpload}
                            headers={{
                                token: this.props.loginAndLogoutInfo.loginInfo.token
                            }}
                            wrappedComponentRef={(me) => {
                                this.formAA = me;
                            }}
                            formConfig={[
                                {
                                    label: '',
                                    field: 'dataType',
                                    type: 'select',
                                    optionConfig: {
                                        label: 'label',
                                        value: 'value'
                                    },
                                    optionData: [
                                        {
                                            label: '权益投资计划完成情况',
                                            value: '1'
                                        },
                                        {
                                            label: '局建安费计划完成情况',
                                            value: '2'
                                        }
                                    ],
                                    initialValue: '1',
                                    allowClear: false,
                                    onMouseEnter: () => {
                                        clearInterval(this.scrollLeftTableDataTimerA);
                                    },
                                    onMouseLeave: () => {
                                        this.scrollTableData();
                                    },
                                    onChange: () => {
                                        this.setState({
                                            leftTableData: []
                                        }, () => {
                                            this.refresh();
                                        })
                                    },
                                    span: 24,
                                    placeholder: '请选择'
                                }
                            ]}
                        /> : <QnnForm
                            fetch={this.props.myFetch}
                            upload={this.props.myUpload}
                            headers={{
                                token: this.props.loginAndLogoutInfo.loginInfo.token
                            }}
                            wrappedComponentRef={(me) => {
                                this.formAA = me;
                            }}
                            formConfig={[
                                {
                                    label: '',
                                    field: 'dataType',
                                    type: 'select',
                                    optionConfig: {
                                        label: 'label',
                                        value: 'value'
                                    },
                                    optionData: [
                                        {
                                            label: '总投资计划完成情况',
                                            value: '3'
                                        }
                                    ],
                                    initialValue: '3',
                                    allowClear: false,
                                    onMouseEnter: () => {
                                        clearInterval(this.scrollLeftTableDataTimerA);
                                    },
                                    onMouseLeave: () => {
                                        this.scrollTableData();
                                    },
                                    onChange: () => {
                                        this.setState({
                                            leftTableData: []
                                        }, () => {
                                            this.refresh();
                                        })
                                    },
                                    span: 24,
                                    placeholder: '请选择'
                                }
                            ]}
                        />}
                    </div>
                </div>
                <div className={s.leftBottom}>
                    <div className={`${s.leftBottomTable}  AssessmentRankings`}>
                        <Spin spinning={loading}>
                            <QnnTable
                                formConfig={leftTableConfig}
                                data={leftTableData}
                                antd={{
                                    rowKey: "ranking",
                                    size: "small",
                                    scroll: {
                                        y: tableHeight
                                    }
                                }}
                                {...qnnTableCommConfig}
                            />
                        </Spin>
                    </div>
                </div>
            </div>
        );
    }
}

export default index;