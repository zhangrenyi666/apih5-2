import React, { Component } from "react";
import QnnForm from "../../../modules/qnn-form";
import QnnTable from "../../../modules/qnn-table";
import s from './style.less';
import { Spin, Button, Modal } from "antd";
import $ from 'jquery';
import moment from 'moment';
import downLoad from "../../../modules/download";
const yanzhong = require('../../../../../src/imgs/yanzhong.png');
const yujing = require('../../../../../src/imgs/yujing.png');
const zhengchang = require('../../../../../src/imgs/zhengchang.png');
const confirm = Modal.confirm;
class index extends Component {
    constructor(props) {
        super(props);
        this.state = {
            loading: false,
            leftTableConfig: [
                {
                    table: {
                        title: '预警',
                        dataIndex: 'colourFlag',
                        align: "center",
                        width: 30,
                        render: (data) => {
                            if (data === 'red') {
                                return <img style={{height:'20px'}} src={yanzhong} />;
                            } else if (data === 'yellow') {
                                return <img style={{height:'20px'}} src={yujing} />;
                            } else if(data === 'green'){
                                return <img style={{height:'20px'}} src={zhengchang} />;
                            }
                        }
                    }
                },
                {
                    table: {
                        title: '项目编号',
                        dataIndex: 'proNum',
                        align: "center",
                        width: 50
                    }
                },
                {
                    table: {
                        title: '项目简称',
                        dataIndex: 'shortName',
                        align: "center",
                        tooltip:5,
                        width: 50
                    }
                },
                {
                    table: {
                        title: '股权比例',
                        dataIndex: 'gqbl',
                        align: "center",
                        width: 50,
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
                        title: '管理单位',
                        dataIndex: 'comname',
                        align: "center",
                        tooltip:5,
                        width: 50
                    }
                },
                {
                    table: {
                        title: '年度计划',
                        width: 100,
                        children: [
                            {
                                title: '完成权益投资',
                                dataIndex: 'bnjhwcqytz',
                                align: 'center',
                                width: 50,
                                render:(data) => {
                                    if(data){
                                       return data.toFixed(2); 
                                    }else{
                                        return '0.00';
                                    }
                                }
                            },
                            {
                                title: '完成局建安费',
                                dataIndex: 'ygjbnjhwcja',
                                align: 'center',
                                width: 50,
                                render:(data) => {
                                    if(data){
                                       return data.toFixed(2); 
                                    }else{
                                        return '0.00';
                                    }
                                }
                            },
                        ]
                    },
                },
                {
                    table: {
                        title: '本年完成',
                        width: 100,
                        children: [
                            {
                                title: '完成权益投资',
                                dataIndex: 'qytzwcbn',
                                align: 'center',
                                width: 50,
                                render:(data) => {
                                    if(data){
                                       return data.toFixed(2); 
                                    }else{
                                        return '0.00';
                                    }
                                }
                            },
                            {
                                title: '完成局建安费',
                                dataIndex: 'ygjjtwcjafbn',
                                align: 'center',
                                width: 50,
                                render:(data) => {
                                    if(data){
                                       return data.toFixed(2); 
                                    }else{
                                        return '0.00';
                                    }
                                }
                            },
                        ]
                    },
                },
                {
                    table: {
                        title: '本年完成百分比',
                        width: 100,
                        children: [
                            {
                                title: '完成权益投资',
                                dataIndex: 'bnqytzbfb',
                                align: 'center',
                                width: 50,
                                render:(data) => {
                                    if(data){
                                       return data.toFixed(2) + '%'; 
                                    }else{
                                        return '0.00%';
                                    }
                                }
                            },
                            {
                                title: '完成局建安费',
                                dataIndex: 'bnjjafbfb',
                                align: 'center',
                                width: 50,
                                render:(data) => {
                                    if(data){
                                       return data.toFixed(2) + '%'; 
                                    }else{
                                        return '0.00%';
                                    }
                                }
                            },
                        ]
                    },
                },
                {
                    table: {
                        title: '截至上季度完成季度计划百分比',
                        width: 120,
                        children: [
                            {
                                title: '完成权益投资',
                                dataIndex: 'bjqytzbfb',
                                align: 'center',
                                width: 60,
                                render:(data) => {
                                    if(data){
                                       return data.toFixed(2) + '%'; 
                                    }else{
                                        return '0.00%';
                                    }
                                }
                            },
                            {
                                title: '完成局建安费',
                                dataIndex: 'bjjjafbfb',
                                align: 'center',
                                width: 60,
                                render:(data) => {
                                    if(data){
                                       return data.toFixed(2) + '%'; 
                                    }else{
                                        return '0.00%';
                                    }
                                }
                            },
                        ]
                    },

                },
            ],
            leftTableData: [],
            tableHeight: 850 * 0.6 - 40
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
            let bodyHeight = $(`.DesignSchedule .ant-table-body`).height();
            if ((dataList.length * 24) <= (bodyHeight + 2)) {
                clearInterval(this.scrollLeftTableDataTimerD);
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
        clearInterval(this.scrollLeftTableDataTimerD);
        this.scrollLeftTableDataTimerD = setInterval(() => aniFn($(`.DesignSchedule .ant-table-body table`).eq(0), "leftTableData"), timer * 2);

    }

    componentWillUnmount() {
        window.removeEventListener("resize", this.resize);
        clearInterval(this.scrollLeftTableDataTimerD);
    }
    resize = () => {
        this.setState({
            tableHeight: 850 * 0.6 - 40
        })
    };
    refresh = () => {
        let formData = this.formD?.form?.getFieldsValue?.() || {};
        if (formData) {
            formData.period = moment(formData.period).valueOf();
        } else {
            formData = {
                period: moment().month(moment().month() - 1).startOf('month').valueOf(),
                comname: '',
                sortType: '1',
                warnSelect: ''
            }
        }
        let body = {
            period: formData.period,
            comname: formData.comname,
            sortType: formData.sortType,
            warnSelect: formData.warnSelect
        }
        this.setState({
            loading: true
        })
        this.props.myFetch('getHomeProgressWarningPlanningProgress', { ...body }).then(
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
            <div className={s.DesignSchedule}>
                <div className={s.leftTop}>
                    <div className={s.leftTopOneL}>
                        计划进度
                    </div>
                    <div className={s.leftTopOneC}>
                        单位：万元
                    </div>
                    <div className={s.leftTopOneRR}>
                        <Button size="small" style={{width:'70px', height:'30px'}} type="primary" onClick={() => {
                            const {
                                loginAndLogoutInfo: {
                                    loginInfo: { token }
                                },
                                myPublic: { domain }
                            } = this.props;
                            let formData = this.formD?.form?.getFieldsValue?.() || {};
                            if (formData) {
                                formData.period = moment(formData.period).valueOf();
                            }
                            let body = {
                                fileName: '计划进度',
                                ...formData
                            }
                            let URL = `${domain + "exportHomeProgressWarningPlanningProgress"}`;
                            confirm({
                                content: '确定导出数据吗?',
                                centered: true,
                                onOk: () => {
                                    downLoad(URL, body, { token });
                                }
                            });
                        }}>导出</Button>
                    </div>
                    <div className={s.leftTopOneR}>
                        <QnnForm
                            fetch={this.props.myFetch}
                            upload={this.props.myUpload}
                            headers={{
                                token: this.props.loginAndLogoutInfo.loginInfo.token
                            }}
                            wrappedComponentRef={(me) => {
                                this.formD = me;
                            }}
                            formConfig={[
                                {
                                    label: '',
                                    field: 'period',
                                    type: 'month',
                                    span: 6,
                                    allowClear: false,
                                    onMouseEnter: () => {
                                        clearInterval(this.scrollLeftTableDataTimerD);
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
                                    field: 'warnSelect',
                                    type: 'select',
                                    optionConfig: {
                                        label: 'label',
                                        value: 'value'
                                    },
                                    optionData: [
                                        {
                                            label: '黄色预警',
                                            value: 'yellow'
                                        },
                                        {
                                            label: '红色预警',
                                            value: 'red'
                                        }
                                    ],
                                    onMouseEnter: () => {
                                        clearInterval(this.scrollLeftTableDataTimerD);
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
                                    span: 6,
                                    placeholder: '请选择'
                                },
                                {
                                    label: '',
                                    field: 'sortType',
                                    type: 'select',
                                    optionConfig: {
                                        label: 'label',
                                        value: 'value'
                                    },
                                    optionData: [
                                        {
                                            label: '项目编号',
                                            value: '1'
                                        },
                                        {
                                            label: '年度计划完成权益投资金额升序',
                                            value: '2'
                                        },
                                        {
                                            label: '年度计划完成权益投资金额降序',
                                            value: '3'
                                        },
                                        {
                                            label: '年度计划完成局建安费金额升序',
                                            value: '4'
                                        },
                                        {
                                            label: '年度计划完成局建安费金额降序',
                                            value: '5'
                                        },
                                        {
                                            label: '本年完成权益投资金额升序',
                                            value: '6'
                                        },
                                        {
                                            label: '本年完成权益投资金额降序',
                                            value: '7'
                                        },
                                        {
                                            label: '本年完成局建安费金额升序',
                                            value: '8'
                                        },
                                        {
                                            label: '本年完成局建安费金额降序',
                                            value: '9'
                                        },
                                        {
                                            label: '本年完成权益投资百分比升序',
                                            value: '10'
                                        },
                                        {
                                            label: '本年完成权益投资百分比降序',
                                            value: '11'
                                        },
                                        {
                                            label: '本年完成局建安费百分比升序',
                                            value: '12'
                                        },
                                        {
                                            label: '本年完成局建安费百分比降序',
                                            value: '13'
                                        },
                                        {
                                            label: '上季度完成权益投资百分比升序',
                                            value: '14'
                                        },
                                        {
                                            label: '上季度完成权益投资百分比降序',
                                            value: '15'
                                        },
                                        {
                                            label: '上季度完成权益投资百分比升序',
                                            value: '16'
                                        },
                                        {
                                            label: '上季度完成局建安费百分比降序',
                                            value: '17'
                                        }
                                    ],
                                    allowClear: false,
                                    initialValue: '1',
                                    onMouseEnter: () => {
                                        clearInterval(this.scrollLeftTableDataTimerD);
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
                                    span: 6,
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
                                    showSearch: true,
                                    onMouseEnter: () => {
                                        clearInterval(this.scrollLeftTableDataTimerD);
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
                                    span: 6,
                                    placeholder: '请选择'
                                }
                            ]}
                        />
                    </div>
                </div>
                <div className={s.leftBottom}>
                    <div className={`${s.leftBottomTable}  DesignSchedule`}>
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