import React, { Component } from "react";
import QnnForm from "../../../modules/qnn-form";
import QnnTable from "../../../modules/qnn-table";
import s from './style.less';
import { Spin } from "antd";
import $ from 'jquery';
import moment from 'moment';
class index extends Component {
    constructor(props) {
        super(props);
        this.state = {
            loading: false,
            leftTableConfig: [
                {
                    table: {
                        title: '项目简称',
                        dataIndex: 'shortName',
                        align: "center",
                        width: 50,
                        tooltip:15,
                        render: (data, rowData) => {
                            if (rowData?.zbjzykhzb < rowData?.sjtrzyzjZtzzebl) {
                                return <span style={{ color: 'red' }}>{data}</span>
                            } else {
                                return data;
                            }
                        }
                    }
                },
                {
                    table: {
                        title: '总投资额',
                        dataIndex: 'hte',
                        align: "center",
                        width: 50,
                        render: (data, rowData) => {
                            if (rowData?.zbjzykhzb < rowData?.sjtrzyzjZtzzebl) {
                                return <span style={{ color: 'red' }}>{rowData?.hte ? rowData.hte.toFixed(2) : '0.00'}</span>
                            } else {
                                return rowData?.hte ? rowData.hte.toFixed(2) : '0.00';
                            }
                        }
                    }
                },
                {
                    table: {
                        title: '一公局投<br/>入资本金',
                        dataIndex: 'ljdwzjZyzj',
                        align: "center",
                        width: 50,
                        render: (data, rowData) => {
                            if (rowData?.zbjzykhzb < rowData?.sjtrzyzjZtzzebl) {
                                return <span style={{ color: 'red' }}>{rowData?.ljdwzjZyzj ? rowData.ljdwzjZyzj.toFixed(2) : '0.00'}</span>
                            } else {
                                return rowData?.ljdwzjZyzj ? rowData.ljdwzjZyzj.toFixed(2) : '0.00';
                            }
                        }
                    }
                },
                {
                    table: {
                        title: '资金考核<br/>占用指标',
                        dataIndex: 'zbjzykhzb',
                        align: "center",
                        width: 50,
                        render: (data, rowData) => {
                            if (rowData?.zbjzykhzb < rowData?.sjtrzyzjZtzzebl) {
                                return <span style={{ color: 'red' }}>{rowData.zbjzykhzb ? (rowData.zbjzykhzb.toFixed(2) + '%') : '0.00%'}</span>
                            } else {
                                return rowData?.zbjzykhzb ? (rowData.zbjzykhzb.toFixed(2) + '%') : '0.00%';
                            }
                        }
                    }
                },
                {
                    table: {
                        title: '实际资金<br/>占用指标',
                        dataIndex: 'sjtrzyzjZtzzebl',
                        align: "center",
                        width: 50,
                        render: (data, rowData) => {
                            if (rowData?.zbjzykhzb < rowData?.sjtrzyzjZtzzebl) {
                                return <span style={{ color: 'red' }}>{rowData.sjtrzyzjZtzzebl ? (rowData.sjtrzyzjZtzzebl.toFixed(2) + '%') : '0.00%'}</span>
                            } else {
                                return rowData?.sjtrzyzjZtzzebl ? (rowData.sjtrzyzjZtzzebl.toFixed(2) + '%') : '0.00%';
                            }
                        }
                    }
                }
            ],
            leftTableData: [],
            tableHeight: 850 * 0.72 - 40
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

        const aniFn = ($dom, dataName, tdHeight = 21) => {
            const dataList = this.state[dataName];
            let bodyHeight = $(`.FinancialStatus .ant-table-body`).height();
            if ((dataList.length * 21) <= (bodyHeight + 2)) {
                clearInterval(this.scrollLeftTableDataTimerF);
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
        clearInterval(this.scrollLeftTableDataTimerF);
        this.scrollLeftTableDataTimerF = setInterval(() => aniFn($(`.FinancialStatus .ant-table-body table`).eq(0), "leftTableData"), timer * 2);

    }

    componentWillUnmount() {
        window.removeEventListener("resize", this.resize);
        clearInterval(this.scrollLeftTableDataTimerF);
    }
    resize = () => {
        this.setState({
            tableHeight: 850 * 0.72 - 40
        })
    };
    refresh = () => {
        let formData = this.formW?.form?.getFieldsValue?.();
        if (formData) {
            formData.period = moment(formData.period).valueOf();
        } else {
            formData = {
                period: moment().valueOf(),
                zjzycb: '',
                proProcessId:'2'
            }
        }
        let body = {
            period: formData.period,
            zjzycb: formData.zjzycb,
            proProcessId: formData.proProcessId
        }
        this.setState({
            loading: true
        })
        this.props.myFetch('getHomeChartCapitalStatus', body).then(
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
                        loading: false,
                    })
                }
            }
        );
    }
    render() {
        const {
            loading,
            leftTableData, leftTableConfig, tableHeight
        } = this.state;
        const qnnTableCommConfig = {
            fetch: this.props.myFetch,
            paginationConfig: false,
            isShowRowSelect: false,
        }
        return (
            <div className={s.FinancialStatus}>
                <div className={s.leftTop}>
                    <div className={s.leftTopOneL}>
                        资金情况
                    </div>
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
                                    field: 'proProcessId',
                                    type: 'select',
                                    optionConfig: {
                                        label: 'label',
                                        value: 'value'
                                    },
                                    optionData: [
                                        {
                                            label: '前期',
                                            value: '1'
                                        },
                                        {
                                            label: '建设期',
                                            value: '2'
                                        },
                                        {
                                            label: '建设/运营期',
                                            value: '3'
                                        },
                                        {
                                            label: '建设/回购期',
                                            value: '4'
                                        }
                                    ],
                                    initialValue:'2',
                                    onMouseEnter: () => {
                                        clearInterval(this.scrollLeftTableDataTimerF);
                                    },
                                    onMouseLeave: () => {
                                        this.scrollTableData();
                                    },
                                    onChange: () => {
                                        this.setState({
                                            leftTableData:[]
                                        },() => {
                                           this.refresh(); 
                                        })
                                    },
                                    span: 8,
                                    allowClear: false,
                                    placeholder: '请选择'
                                },
                                {
                                    label: '',
                                    field: 'period',
                                    type: 'month',
                                    allowClear: false,
                                    span: 8,
                                    onMouseEnter: () => {
                                        clearInterval(this.scrollLeftTableDataTimerF);
                                    },
                                    onMouseLeave: () => {
                                        this.scrollTableData();
                                    },
                                    onChange: () => {
                                        this.setState({
                                            leftTableData:[]
                                        },() => {
                                           this.refresh(); 
                                        })
                                    },
                                    initialValue: moment().month(moment().month() - 1).startOf('month').valueOf(),
                                    placeholder: '请选择'
                                },
                                {
                                    label: '',
                                    field: 'zjzycb',
                                    type: 'select',
                                    optionConfig: {
                                        label: 'label',
                                        value: 'value'
                                    },
                                    optionData: [
                                        {
                                            label: '资金占用超标',
                                            value: '1'
                                        }
                                    ],
                                    onMouseEnter: () => {
                                        clearInterval(this.scrollLeftTableDataTimerF);
                                    },
                                    onMouseLeave: () => {
                                        this.scrollTableData();
                                    },
                                    onChange: () => {
                                        this.setState({
                                            leftTableData:[]
                                        },() => {
                                           this.refresh(); 
                                        })
                                    },
                                    span: 8,
                                    placeholder: '请选择'
                                }
                            ]}
                        />
                    </div>
                </div>
                <div className={s.leftBottom}>
                    <div className={`${s.leftBottomTable}  FinancialStatus`}>
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