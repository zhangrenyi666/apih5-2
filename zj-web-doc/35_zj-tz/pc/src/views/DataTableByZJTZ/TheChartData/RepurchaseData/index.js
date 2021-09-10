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
                        tooltip:15,
                        width: 80
                    }
                },
                {
                    table: {
                        title: '管理单位',
                        dataIndex: 'comname',
                        align: "center",
                        tooltip:15,
                        width: 80,
                    }
                },
                {
                    table: {
                        title: '总投资额',
                        dataIndex: 'hte',
                        align: "center",
                        width: 80,
                        render:(data) => {
                            if(data){
                               return data.toFixed(2); 
                            }else{
                                return '0.00';
                            }
                        }
                    }
                },
                {
                    table: {
                        title: '预计<br/>回购总额',
                        dataIndex: 'yjhgze',
                        align: "center",
                        width: 80,
                        render:(data) => {
                            if(data){
                               return data.toFixed(2); 
                            }else{
                                return '0.00';
                            }
                        }
                    }
                },
                {
                    table: {
                        title: '开累已<br/>回购总额',
                        dataIndex: 'sjhgje',
                        align: "center",
                        width: 80,
                        render:(data) => {
                            if(data){
                               return data.toFixed(2); 
                            }else{
                                return '0.00';
                            }
                        }
                    }
                },
                {
                    table: {
                        title: '开累已<br/>回购比例',
                        dataIndex: 'klhgbl',
                        align: "center",
                        width: 80,
                        render:(data) => {
                            if(data){
                               return data.toFixed(2) + '%'; 
                            }else{
                                return '0.00%';
                            }
                        }
                    }
                },
                {
                    table: {
                        title: '本年应<br/>回购金额',
                        dataIndex: 'bnyhg',
                        align: "center",
                        width: 80,
                        render:(data) => {
                            if(data){
                               return data.toFixed(2); 
                            }else{
                                return '0.00';
                            }
                        }
                    }
                },
                {
                    table: {
                        title: '本年实际<br/>回购金额',
                        dataIndex: 'bnlj',
                        align: "center",
                        width: 80,
                        render:(data) => {
                            if(data){
                               return data.toFixed(2); 
                            }else{
                                return '0.00';
                            }
                        }
                    }
                },
                {
                    table: {
                        title: '本月实际<br/>回购金额',
                        dataIndex: 'hgjeBq',
                        align: "center",
                        width: 80,
                        render:(data) => {
                            if(data){
                               return data.toFixed(2); 
                            }else{
                                return '0.00';
                            }
                        }
                    }
                }
            ],
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
            let bodyHeight = $(`.RepurchaseData .ant-table-body`).height();
            if ((dataList.length * 21) <= (bodyHeight + 2)) {
                clearInterval(this.scrollLeftTableDataTimerR);
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
        clearInterval(this.scrollLeftTableDataTimerR);
        this.scrollLeftTableDataTimerR = setInterval(() => aniFn($(`.RepurchaseData .ant-table-body table`).eq(0), "leftTableData"), timer * 2);

    }

    componentWillUnmount() {
        window.removeEventListener("resize", this.resize);
        clearInterval(this.scrollLeftTableDataTimerR);
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
        this.props.myFetch('getHomeChartHgData', body).then(
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
            <div className={s.RepurchaseData}>
                <div className={s.leftTop}>
                    <div className={s.leftTopOneL}>
                        回购数据
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
                                        clearInterval(this.scrollLeftTableDataTimerR);
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
                                        clearInterval(this.scrollLeftTableDataTimerR);
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
                                    showSearch: true,
                                    span: 12,
                                    placeholder: '请选择'
                                }
                            ]}
                        />
                    </div>
                </div>
                <div className={s.leftBottom}>
                    <div className={`${s.leftBottomTable}  RepurchaseData`}>
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