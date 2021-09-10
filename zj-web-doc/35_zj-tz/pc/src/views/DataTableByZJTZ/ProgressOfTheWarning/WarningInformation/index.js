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
                        title: '序号',
                        dataIndex: 'ranking',
                        align: "center",
                        width: 50
                    }
                },
                {
                    table: {
                        title: '项目简称',
                        dataIndex: 'shortName',
                        align: "center",
                        width: 100,
                        tooltip: 15,
                    }
                },
                {
                    table: {
                        title: '本季度完成计划百分比',
                        dataIndex: 'percentage',
                        align: "center",
                        width: 120,
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
            ],
            leftTableData: [],
            tableHeight: 850 - 110,
            formData: {}
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

        const aniFn = ($dom, dataName, tdHeight = 51) => {
            const dataList = this.state[dataName];
            let bodyHeight = $(`.WarningInformation .ant-table-body`).height();
            if ((dataList.length * 51) <= (bodyHeight + 2)) {
                clearInterval(this.scrollLeftTableDataTimerW);
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
        clearInterval(this.scrollLeftTableDataTimerW);
        this.scrollLeftTableDataTimerW = setInterval(() => aniFn($(`.WarningInformation .ant-table-body table`).eq(0), "leftTableData"), timer * 2);

    }

    componentWillUnmount() {
        window.removeEventListener("resize", this.resize);
        clearInterval(this.scrollLeftTableDataTimerW);
    }
    resize = () => {
        this.setState({
            tableHeight: 850 - 110,
        })
    };
    refresh = () => {
        let formData = this.formW?.form?.getFieldsValue?.();
        let dataType = '';
        if (formData) {
            if (formData.dataType === '3') {
                formData.endTime = moment(formData.year).format('YYYY');
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
                dataType: '1',
                endTime: moment(quarter).format('YYYY') + quarterName
            }
        }
        let body = {
            dataType: formData.dataType,
            endTime: formData.endTime
        }
        if (formData.dataType === '1') {
            dataType = '权益投资完成情况预警';
        } else if (formData.dataType === '2') {
            dataType = '局建安费完成情况预警';
        } else if (formData.dataType === '3') {
            dataType = '总投资完成情况年终否决';
        }
        this.setState({
            loading: true,
            formData: {
                dataType,
                endTime: formData.endTime
            }
        })
        this.props.myFetch('getHomeProgressWarningInfo', body).then(
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
            leftTableData,
            leftTableConfig,
            tableHeight,
            formData
        } = this.state;
        const qnnTableCommConfig = {
            fetch: this.props.myFetch,
            paginationConfig: false,
            isShowRowSelect: false,
        }
        return (
            <div className={s.WarningInformation}>
                <div className={s.leftTop}>
                    <div className={s.leftTopL}>
                        预警信息
                    </div>
                    <div className={s.leftTopR}>
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
                                    field: 'dataType',
                                    type: 'select',
                                    optionConfig: {
                                        label: 'label',
                                        value: 'value'
                                    },
                                    optionData: [
                                        {
                                            label: '权益投资完成情况预警',
                                            value: '1'
                                        },
                                        {
                                            label: '局建安费完成情况预警',
                                            value: '2'
                                        },
                                        {
                                            label: '总投资完成情况年终否决',
                                            value: '3'
                                        }
                                    ],
                                    initialValue: '1',
                                    allowClear: false,
                                    onMouseEnter: () => {
                                        clearInterval(this.scrollLeftTableDataTimerW);
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
                                                dataType: '3'
                                            },
                                            action: 'hide'
                                        }
                                    ],
                                    onMouseEnter: () => {
                                        clearInterval(this.scrollLeftTableDataTimerW);
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
                                    format: 'YYYY-第Q季度',
                                    initialValue: moment().quarter(moment().quarter() - 1).startOf('quarter').valueOf(),
                                    placeholder: '请选择'
                                },
                                {
                                    label: '截止时期',
                                    field: 'year',
                                    type: 'year',
                                    allowClear: false,
                                    span: 10,
                                    condition: [
                                        {
                                            regex: {
                                                dataType: ['!', '3']
                                            },
                                            action: 'hide'
                                        }
                                    ],
                                    onMouseEnter: () => {
                                        clearInterval(this.scrollLeftTableDataTimerW);
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
                                    initialValue: moment().year(moment().year() - 1).startOf('year').valueOf(),
                                    placeholder: '请选择'
                                }
                            ]}
                        />
                    </div>
                </div>
                <div className={s.leftCenter}>
                    {formData?.endTime}{formData?.dataType}
                </div>
                <div className={s.leftBottom}>
                    <div className={`${s.leftBottomTable}  WarningInformation`}>
                        <Spin spinning={loading}>
                            <QnnTable
                                formConfig={leftTableConfig}
                                data={leftTableData}
                                antd={{
                                    rowKey: "ranking",
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