import React, { Component } from "react";
import QnnForm from "../../../modules/qnn-form";
import QnnTable from "../../../modules/qnn-table";
import s from './style.less';
import { Spin, Checkbox } from "antd";
import $ from 'jquery';
import moment from 'moment';
class index extends Component {
    constructor(props) {
        super(props);
        this.state = {
            loading: false,
            leftTableConfig: [],
            leftTableData: [],
            tableHeight: ((850 * 0.72)) * 0.5 - 40
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
            let bodyHeight = $(`.OperationalData .ant-table-body`).height();
            if ((dataList.length * 21) <= (bodyHeight + 2)) {
                clearInterval(this.scrollLeftTableDataTimerO);
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
        clearInterval(this.scrollLeftTableDataTimerO);
        this.scrollLeftTableDataTimerO = setInterval(() => aniFn($(`.OperationalData .ant-table-body table`).eq(0), "leftTableData"), timer * 2);

    }

    componentWillUnmount() {
        window.removeEventListener("resize", this.resize);
        clearInterval(this.scrollLeftTableDataTimerO);
    }
    resize = () => {
        this.setState({
            tableHeight: ((850 * 0.72)) * 0.5 - 40
        })
    };
    refresh = () => {
        let formData = this.formW?.form?.getFieldsValue?.();
        if (formData) {
            formData.period = moment(formData.period).valueOf();
        } else {
            formData = {
                period: moment().valueOf(),
                comname: ''
            }
        }
        let body = {
            period: formData.period,
            comname: formData.comname
        }
        this.setState({
            loading: true
        })
        this.props.myFetch('getHomeChartOperateData', body).then(
            ({ success, message, data }) => {
                if (success) {
                    this.setState({
                        loading: false,
                        leftTableData: data.map((item, index) => {
                            return {
                                ...item,
                                ranking: index + 1
                            }
                        }),
                        leftTableConfig: [
                            {
                                table: {
                                    title: '项目简称',
                                    dataIndex: 'shortName',
                                    align: "center",
                                    tooltip: 15,
                                    width: 50,
                                    render: (data, rowData) => {
                                        if (rowData?.bqYyzsr < rowData?.bntpsr) {
                                            return <span style={{ color: 'red' }}>{rowData?.data || ''}</span>
                                        } else {
                                            return rowData?.data || '';
                                        }
                                    }
                                }
                            },
                            {
                                table: {
                                    title: '管理单位',
                                    dataIndex: 'comname',
                                    align: "center",
                                    tooltip: 15,
                                    width: 50,
                                    render: (data, rowData) => {
                                        if (rowData?.bqYyzsr < rowData?.bntpsr) {
                                            return <span style={{ color: 'red' }}>{rowData?.data || ''}</span>
                                        } else {
                                            return rowData?.data || '';
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
                                        if (rowData?.bqYyzsr < rowData?.bntpsr) {
                                            if (data) {
                                                return <span style={{ color: 'red' }}>{data.toFixed(2)}</span>;
                                            } else {
                                                return <span style={{ color: 'red' }}>0.00</span>
                                            }
                                        } else {
                                            if (data) {
                                                return data.toFixed(2);
                                            } else {
                                                return '0.00';
                                            }
                                        }
                                    }
                                }
                            },
                            {
                                table: {
                                    title: '本年<br/>投评收入',
                                    dataIndex: 'bntpsr',
                                    align: "center",
                                    width: 50,
                                    render: (data, rowData) => {
                                        if (rowData?.bqYyzsr < rowData?.bntpsr) {
                                            if (data) {
                                                return <span style={{ color: 'red' }}>{data.toFixed(2)}</span>;
                                            } else {
                                                return <span style={{ color: 'red' }}>0.00</span>
                                            }
                                        } else {
                                            if (data) {
                                                return data.toFixed(2);
                                            } else {
                                                return '0.00';
                                            }
                                        }
                                    }
                                }
                            },
                            {
                                table: {
                                    title: '本年<br/>营业收入',
                                    dataIndex: 'bnYyzsr',
                                    align: "center",
                                    width: 50,
                                    render: (data, rowData) => {
                                        if (rowData?.bqYyzsr < rowData?.bntpsr) {
                                            if (data) {
                                                return <span style={{ color: 'red' }}>{data.toFixed(2)}</span>;
                                            } else {
                                                return <span style={{ color: 'red' }}>0.00</span>
                                            }
                                        } else {
                                            if (data) {
                                                return data.toFixed(2);
                                            } else {
                                                return '0.00';
                                            }
                                        }
                                    }
                                }
                            },
                            {
                                table: {
                                    title: '本年完成<br/>投评百分比',
                                    dataIndex: 'bntpbfb',
                                    align: "center",
                                    width: 60,
                                    render: (data, rowData) => {
                                        if (rowData?.bqYyzsr < rowData?.bntpsr) {
                                            if (data) {
                                                return <span style={{ color: 'red' }}>{data.toFixed(2)}</span>;
                                            } else {
                                                return <span style={{ color: 'red' }}>0.00</span>
                                            }
                                        } else {
                                            if (data) {
                                                return data.toFixed(2);
                                            } else {
                                                return '0.00';
                                            }
                                        }
                                    }
                                }
                            },
                            {
                                table: {
                                    title: '本月<br/>投评收入',
                                    dataIndex: 'bqtpsr',
                                    align: "center",
                                    width: 50,
                                    render: (data, rowData) => {
                                        if (rowData?.bqYyzsr < rowData?.bntpsr) {
                                            if (data) {
                                                return <span style={{ color: 'red' }}>{data.toFixed(2)}</span>;
                                            } else {
                                                return <span style={{ color: 'red' }}>0.00</span>
                                            }
                                        } else {
                                            if (data) {
                                                return data.toFixed(2);
                                            } else {
                                                return '0.00';
                                            }
                                        }
                                    }
                                }
                            },
                            {
                                table: {
                                    title: '本月<br/>营业收入',
                                    dataIndex: 'bqYyzsr',
                                    align: "center",
                                    width: 50,
                                    render: (data, rowData) => {
                                        if (rowData?.bqYyzsr < rowData?.bntpsr) {
                                            if (data) {
                                                return <span style={{ color: 'red' }}>{data.toFixed(2)}</span>;
                                            } else {
                                                return <span style={{ color: 'red' }}>0.00</span>
                                            }
                                        } else {
                                            if (data) {
                                                return data.toFixed(2);
                                            } else {
                                                return '0.00';
                                            }
                                        }
                                    }
                                }
                            },
                            {
                                table: {
                                    title: '本月完成<br/>投评百分比',
                                    dataIndex: 'bqtpbfb',
                                    align: "center",
                                    width: 60,
                                    render: (data, rowData) => {
                                        if (rowData?.bqYyzsr < rowData?.bntpsr) {
                                            if (data) {
                                                return <span style={{ color: 'red' }}>{data.toFixed(2)}</span>;
                                            } else {
                                                return <span style={{ color: 'red' }}>0.00</span>
                                            }
                                        } else {
                                            if (data) {
                                                return data.toFixed(2);
                                            } else {
                                                return '0.00';
                                            }
                                        }
                                    }
                                }
                            }
                        ]
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
    onCheckbox = (e) => {
        if (e.target.checked) {
            this.setState({
                leftTableConfig: [
                    {
                        table: {
                            title: '项目简称',
                            dataIndex: 'shortName',
                            align: "center",
                            tooltip: 15,
                            width: 50,
                            render: (data, rowData) => {
                                if (rowData?.bqYyzsr < rowData?.bntpsr) {
                                    return <span style={{ color: 'red' }}>{rowData?.data || ''}</span>
                                } else {
                                    return rowData?.data || '';
                                }
                            }
                        }
                    },
                    {
                        table: {
                            title: '管理单位',
                            dataIndex: 'comname',
                            align: "center",
                            tooltip: 15,
                            width: 50,
                            render: (data, rowData) => {
                                if (rowData?.bqYyzsr < rowData?.bntpsr) {
                                    return <span style={{ color: 'red' }}>{rowData?.data || ''}</span>
                                } else {
                                    return rowData?.data || '';
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
                                if (rowData?.bqYyzsr < rowData?.bntpsr) {
                                    if (data) {
                                        return <span style={{ color: 'red' }}>{data.toFixed(2)}</span>;
                                    } else {
                                        return <span style={{ color: 'red' }}>0.00</span>
                                    }
                                } else {
                                    if (data) {
                                        return data.toFixed(2);
                                    } else {
                                        return '0.00';
                                    }
                                }
                            }
                        }
                    },
                    {
                        table: {
                            title: '开累<br/>投评收入',
                            dataIndex: 'kltpsr',
                            align: "center",
                            width: 50,
                            render: (data, rowData) => {
                                if (rowData?.bqYyzsr < rowData?.bntpsr) {
                                    if (data) {
                                        return <span style={{ color: 'red' }}>{data.toFixed(2)}</span>;
                                    } else {
                                        return <span style={{ color: 'red' }}>0.00</span>
                                    }
                                } else {
                                    if (data) {
                                        return data.toFixed(2);
                                    } else {
                                        return '0.00';
                                    }
                                }
                            }
                        }
                    },
                    {
                        table: {
                            title: '开累<br/>营业收入',
                            dataIndex: 'klYyzsr',
                            align: "center",
                            width: 50,
                            render: (data, rowData) => {
                                if (rowData?.bqYyzsr < rowData?.bntpsr) {
                                    if (data) {
                                        return <span style={{ color: 'red' }}>{data.toFixed(2)}</span>;
                                    } else {
                                        return <span style={{ color: 'red' }}>0.00</span>
                                    }
                                } else {
                                    if (data) {
                                        return data.toFixed(2);
                                    } else {
                                        return '0.00';
                                    }
                                }
                            }
                        }
                    },
                    {
                        table: {
                            title: '开累完成<br/>投评百分比',
                            dataIndex: 'kltpbfb',
                            align: "center",
                            width: 60,
                            render: (data, rowData) => {
                                if (rowData?.bqYyzsr < rowData?.bntpsr) {
                                    if (data) {
                                        return <span style={{ color: 'red' }}>{data.toFixed(2)}</span>;
                                    } else {
                                        return <span style={{ color: 'red' }}>0.00</span>
                                    }
                                } else {
                                    if (data) {
                                        return data.toFixed(2);
                                    } else {
                                        return '0.00';
                                    }
                                }
                            }
                        }
                    },
                    {
                        table: {
                            title: '本年<br/>投评收入',
                            dataIndex: 'bntpsr',
                            align: "center",
                            width: 50,
                            render: (data, rowData) => {
                                if (rowData?.bqYyzsr < rowData?.bntpsr) {
                                    if (data) {
                                        return <span style={{ color: 'red' }}>{data.toFixed(2)}</span>;
                                    } else {
                                        return <span style={{ color: 'red' }}>0.00</span>
                                    }
                                } else {
                                    if (data) {
                                        return data.toFixed(2);
                                    } else {
                                        return '0.00';
                                    }
                                }
                            }
                        }
                    },
                    {
                        table: {
                            title: '本年<br/>营业收入',
                            dataIndex: 'bnYyzsr',
                            align: "center",
                            width: 50,
                            render: (data, rowData) => {
                                if (rowData?.bqYyzsr < rowData?.bntpsr) {
                                    if (data) {
                                        return <span style={{ color: 'red' }}>{data.toFixed(2)}</span>;
                                    } else {
                                        return <span style={{ color: 'red' }}>0.00</span>
                                    }
                                } else {
                                    if (data) {
                                        return data.toFixed(2);
                                    } else {
                                        return '0.00';
                                    }
                                }
                            }
                        }
                    },
                    {
                        table: {
                            title: '本年完成<br/>投评百分比',
                            dataIndex: 'bntpbfb',
                            align: "center",
                            width: 60,
                            render: (data, rowData) => {
                                if (rowData?.bqYyzsr < rowData?.bntpsr) {
                                    if (data) {
                                        return <span style={{ color: 'red' }}>{data.toFixed(2)}</span>;
                                    } else {
                                        return <span style={{ color: 'red' }}>0.00</span>
                                    }
                                } else {
                                    if (data) {
                                        return data.toFixed(2);
                                    } else {
                                        return '0.00';
                                    }
                                }
                            }
                        }
                    },
                    {
                        table: {
                            title: '本月<br/>投评收入',
                            dataIndex: 'bqtpsr',
                            align: "center",
                            width: 50,
                            render: (data, rowData) => {
                                if (rowData?.bqYyzsr < rowData?.bntpsr) {
                                    if (data) {
                                        return <span style={{ color: 'red' }}>{data.toFixed(2)}</span>;
                                    } else {
                                        return <span style={{ color: 'red' }}>0.00</span>
                                    }
                                } else {
                                    if (data) {
                                        return data.toFixed(2);
                                    } else {
                                        return '0.00';
                                    }
                                }
                            }
                        }
                    },
                    {
                        table: {
                            title: '本月<br/>营业收入',
                            dataIndex: 'bqYyzsr',
                            align: "center",
                            width: 50,
                            render: (data, rowData) => {
                                if (rowData?.bqYyzsr < rowData?.bntpsr) {
                                    if (data) {
                                        return <span style={{ color: 'red' }}>{data.toFixed(2)}</span>;
                                    } else {
                                        return <span style={{ color: 'red' }}>0.00</span>
                                    }
                                } else {
                                    if (data) {
                                        return data.toFixed(2);
                                    } else {
                                        return '0.00';
                                    }
                                }
                            }
                        }
                    },
                    {
                        table: {
                            title: '本月完成<br/>投评百分比',
                            dataIndex: 'bqtpbfb',
                            align: "center",
                            width: 60,
                            render: (data, rowData) => {
                                if (rowData?.bqYyzsr < rowData?.bntpsr) {
                                    if (data) {
                                        return <span style={{ color: 'red' }}>{data.toFixed(2)}</span>;
                                    } else {
                                        return <span style={{ color: 'red' }}>0.00</span>
                                    }
                                } else {
                                    if (data) {
                                        return data.toFixed(2);
                                    } else {
                                        return '0.00';
                                    }
                                }
                            }
                        }
                    }
                ]
            })
        } else {
            this.setState({
                leftTableConfig: [
                    {
                        table: {
                            title: '项目简称',
                            dataIndex: 'shortName',
                            align: "center",
                            tooltip: 15,
                            width: 50,
                            render: (data, rowData) => {
                                if (rowData?.bqYyzsr < rowData?.bntpsr) {
                                    return <span style={{ color: 'red' }}>{rowData?.data || ''}</span>
                                } else {
                                    return rowData?.data || '';
                                }
                            }
                        }
                    },
                    {
                        table: {
                            title: '管理单位',
                            dataIndex: 'comname',
                            align: "center",
                            tooltip: 15,
                            width: 50,
                            render: (data, rowData) => {
                                if (rowData?.bqYyzsr < rowData?.bntpsr) {
                                    return <span style={{ color: 'red' }}>{rowData?.data || ''}</span>
                                } else {
                                    return rowData?.data || '';
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
                                if (rowData?.bqYyzsr < rowData?.bntpsr) {
                                    if (data) {
                                        return <span style={{ color: 'red' }}>{data.toFixed(2)}</span>;
                                    } else {
                                        return <span style={{ color: 'red' }}>0.00</span>
                                    }
                                } else {
                                    if (data) {
                                        return data.toFixed(2);
                                    } else {
                                        return '0.00';
                                    }
                                }
                            }
                        }
                    },
                    {
                        table: {
                            title: '本年<br/>投评收入',
                            dataIndex: 'bntpsr',
                            align: "center",
                            width: 50,
                            render: (data, rowData) => {
                                if (rowData?.bqYyzsr < rowData?.bntpsr) {
                                    if (data) {
                                        return <span style={{ color: 'red' }}>{data.toFixed(2)}</span>;
                                    } else {
                                        return <span style={{ color: 'red' }}>0.00</span>
                                    }
                                } else {
                                    if (data) {
                                        return data.toFixed(2);
                                    } else {
                                        return '0.00';
                                    }
                                }
                            }
                        }
                    },
                    {
                        table: {
                            title: '本年<br/>营业收入',
                            dataIndex: 'bnYyzsr',
                            align: "center",
                            width: 50,
                            render: (data, rowData) => {
                                if (rowData?.bqYyzsr < rowData?.bntpsr) {
                                    if (data) {
                                        return <span style={{ color: 'red' }}>{data.toFixed(2)}</span>;
                                    } else {
                                        return <span style={{ color: 'red' }}>0.00</span>
                                    }
                                } else {
                                    if (data) {
                                        return data.toFixed(2);
                                    } else {
                                        return '0.00';
                                    }
                                }
                            }
                        }
                    },
                    {
                        table: {
                            title: '本年完成<br/>投评百分比',
                            dataIndex: 'bntpbfb',
                            align: "center",
                            width: 60,
                            render: (data, rowData) => {
                                if (rowData?.bqYyzsr < rowData?.bntpsr) {
                                    if (data) {
                                        return <span style={{ color: 'red' }}>{data.toFixed(2)}</span>;
                                    } else {
                                        return <span style={{ color: 'red' }}>0.00</span>
                                    }
                                } else {
                                    if (data) {
                                        return data.toFixed(2);
                                    } else {
                                        return '0.00';
                                    }
                                }
                            }
                        }
                    },
                    {
                        table: {
                            title: '本月<br/>投评收入',
                            dataIndex: 'bqtpsr',
                            align: "center",
                            width: 50,
                            render: (data, rowData) => {
                                if (rowData?.bqYyzsr < rowData?.bntpsr) {
                                    if (data) {
                                        return <span style={{ color: 'red' }}>{data.toFixed(2)}</span>;
                                    } else {
                                        return <span style={{ color: 'red' }}>0.00</span>
                                    }
                                } else {
                                    if (data) {
                                        return data.toFixed(2);
                                    } else {
                                        return '0.00';
                                    }
                                }
                            }
                        }
                    },
                    {
                        table: {
                            title: '本月<br/>营业收入',
                            dataIndex: 'bqYyzsr',
                            align: "center",
                            width: 50,
                            render: (data, rowData) => {
                                if (rowData?.bqYyzsr < rowData?.bntpsr) {
                                    if (data) {
                                        return <span style={{ color: 'red' }}>{data.toFixed(2)}</span>;
                                    } else {
                                        return <span style={{ color: 'red' }}>0.00</span>
                                    }
                                } else {
                                    if (data) {
                                        return data.toFixed(2);
                                    } else {
                                        return '0.00';
                                    }
                                }
                            }
                        }
                    },
                    {
                        table: {
                            title: '本月完成<br/>投评百分比',
                            dataIndex: 'bqtpbfb',
                            align: "center",
                            width: 60,
                            render: (data, rowData) => {
                                if (rowData?.bqYyzsr < rowData?.bntpsr) {
                                    if (data) {
                                        return <span style={{ color: 'red' }}>{data.toFixed(2)}</span>;
                                    } else {
                                        return <span style={{ color: 'red' }}>0.00</span>
                                    }
                                } else {
                                    if (data) {
                                        return data.toFixed(2);
                                    } else {
                                        return '0.00';
                                    }
                                }
                            }
                        }
                    }
                ]
            })
        }
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
            <div className={s.OperationalData}>
                <div className={s.leftTop}>
                    <div className={s.leftTopOneL}>
                        运营数据
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
                                this.formW = me;
                            }}
                            formConfig={[
                                {
                                    label: '',
                                    field: 'period',
                                    type: 'month',
                                    allowClear: false,
                                    span: 12,
                                    onMouseEnter: () => {
                                        clearInterval(this.scrollLeftTableDataTimerO);
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
                                    initialValue: moment().month(moment().month() - 1).startOf('month').valueOf(),
                                    placeholder: '请选择'
                                },
                                {
                                    label: '',
                                    field: 'comname',
                                    type: 'select',
                                    optionConfig: {
                                        label: 'companyName',
                                        value: 'companyName'
                                    },
                                    fetchConfig: {
                                        apiName: 'getHomeProgressPlaningComname'
                                    },
                                    onMouseEnter: () => {
                                        clearInterval(this.scrollLeftTableDataTimerO);
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
                                    showSearch: true,
                                    span: 12,
                                    placeholder: '请选择'
                                }
                            ]}
                        />
                    </div>
                    <div className={s.leftTopOneRR}>
                        <Checkbox onChange={this.onCheckbox}>显示累计数据</Checkbox>
                    </div>
                </div>
                <div className={s.leftBottom}>
                    <div className={`${s.leftBottomTable}  OperationalData`}>
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