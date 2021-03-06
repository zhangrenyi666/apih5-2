import React, { Component } from "react";
import QnnForm from "../../../modules/qnn-form";
import ReactEcharts from 'echarts-for-react';
import s from './style.less';
import { Spin, Button, Modal } from "antd";
import moment from 'moment';
import downLoad from "../../../modules/download";
import QnnTable from "../../../modules/qnn-table";
const confirm = Modal.confirm;
class index extends Component {
    constructor(props) {
        super(props);
        this.state = {
            loading: false,
            data: null,
            visible: false,
            title: '',
            dateType:'1',
            status:'1',
            leftTableConfig:[]
        }
    }
    componentDidMount() {
        this.refresh();
    }
    refresh = () => {
        let formData = this.formT?.form?.getFieldsValue?.() || {};
        let body = {
            dateType: formData?.dateType || '1'
        }
        this.setState({
            loading: true,
            dateType:body.dateType
        })
        this.props.myFetch('getHomeProjectStatusAllProject', body).then(
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
            color: ['rgba(0,149,255,1)', 'rgba(0,255,234,1)', 'rgba(230,189,69,1)', 'rgba(225, 118, 7,1)', 'rgba(255,51,85,1)'],
            tooltip: {
                trigger: 'item',
                formatter: "{a} <br/>{b} : {c} ({d}%)",
                confine: true
            },
            toolbox: {
                show: true,

            },
            legend: [
                {
                    icon: "circle",
                    orient: "vertical",
                    x: 'left',
                    y: 'center',
                    bottom: 25,
                    itemHeight: 10,
                    itemWidth: 10,
                    textStyle: {
                        color: 'white'
                    },
                    selectedMode: false,
                    data: ['?????????????????????', '????????????????????????', '????????????????????????', '??????????????????????????????', '??????????????????????????????']
                }
            ],
            series: [
                {
                    name: '??????',
                    type: 'pie',
                    center: ['60%', '50%'],
                    radius: '75%',
                    data: [
                        { value: data?.[0]?.count3 || 0, name: '?????????????????????' },
                        { value: data?.[0]?.count1 || 0, name: '????????????????????????' },
                        { value: data?.[0]?.count2 || 0, name: '????????????????????????' },
                        { value: data?.[0]?.count5 || 0, name: '??????????????????????????????' },
                        { value: data?.[0]?.count4 || 0, name: '??????????????????????????????' }
                    ],
                    label: {
                        show: true,
                        position: 'outer',
                        formatter: '{d}%',
                        fontSize: 14,
                        fontWeight: 'bold'
                    },
                    labelLine: {
                        show: false,
                        normal: {
                            length: 2,
                            length2: 2,
                            lineStyle: {
                                width: 0
                            }
                        }
                    },
                }
            ]
        }
        return option
    }
    onChartTowClick = (params) => {
        const { dateType } = this.state;
        let status = '1';
        let leftTableConfig = [];
        if(params.name === '????????????????????????'){
            status = '1';
            leftTableConfig = [
                {
                    isInForm:false,
                    table: {
                        title: '????????????',
                        dataIndex: 'proNo',
                        width: 80,
                        tooltip:8,
                        // align: "center",
                        fixed:'left'
                    }
                },
                {
                    isInForm:false,
                    table: {
                        title: '????????????',
                        dataIndex: 'companyName',
                        width: 100,
                        tooltip:8,
                        // align: "center",
                        fixed:'left'
                    }
                },
                {
                    isInForm:false,
                    table: {
                        title: '????????????',
                        dataIndex: 'projectShortName',
                        width: 150,
                        tooltip:12,
                        // align: "center"
                    }
                },
                {
                    isInForm:false,
                    table: {
                        title: '???????????????',
                        dataIndex: 'subprojectName',
                        width: 150,
                        tooltip:12,
                    }
                },
                {
                    isInForm:false,
                    table: {
                        title: '??????',
                        dataIndex: 'periodWarn',
                        width: 80,
                        tooltip:9,
                        align: "center"
                    }
                },
                {
                    isInForm:false,
                    table: {
                        title: '?????????(???)',
                        dataIndex: 'constructPeriod',
                        width: 100,
                        align: "center"
                    }
                },
                {
                    isInForm:false,
                    table: {
                        title: '?????????????????????',
                        dataIndex: 'constructEnd',
                        width: 120,
                        tooltip:9,
                        align: "center"
                    }
                },
                {
                    isInForm:false,
                    table: {
                        title: '??????????????????',
                        dataIndex: 'actualDate',
                        width: 120,
                        align: "center"
                    }
                },
                {
                    isInForm:false,
                    table: {
                        title: dateType === '1' ? '????????????????????????' : '????????????????????????',
                        dataIndex: 'handoverDatePlan',
                        width: 130,
                        align: "center"
                    }
                },
                {
                    isInForm:false,
                    table: {
                        title: '??????????????????',
                        dataIndex: 'handoverDateActrual',
                        width: 120,
                        align: "center"
                    }
                },
                {
                    isInForm:false,
                    table: {
                        title: '?????????????????????',
                        dataIndex: 'constructActualDay',
                        width: 120,
                        align: "center"
                    }
                },
                {
                    isInForm:false,
                    table: {
                        title: '??????????????????',
                        dataIndex: 'handoverAdvanceDay',
                        width: 120,
                        align: "center"
                    }
                },
                {
                    isInForm:false,
                    table: {
                        title: dateType === '1' ? '????????????????????????' : '????????????????????????',
                        dataIndex: 'complateDatePlan',
                        width: 130,
                        align: "center"
                    }
                },
                {
                    isInForm:false,
                    table: {
                        title: '??????????????????',
                        dataIndex: 'complateDateActrual',
                        width: 120,
                        align: "center"
                    }
                },
                {
                    isInForm:false,
                    table: {
                        title: '????????????????????????',
                        dataIndex: 'completeActualDay',
                        width: 130,
                        align: "center"
                    }
                },
                {
                    isInForm:false,
                    table: {
                        title: '??????????????????',
                        dataIndex: 'completeAdvanceDay',
                        width: 120,
                        align: "center"
                    }
                }
            ]
        }else if(params.name === '?????????????????????'){
            status = '3';
            leftTableConfig = [
                {
                    isInForm:false,
                    table: {
                        title: '????????????',
                        dataIndex: 'proNo',
                        width: 80,
                        tooltip:8,
                        // align: "center",
                        fixed:'left'
                    }
                },
                {
                    isInForm:false,
                    table: {
                        title: '????????????',
                        dataIndex: 'companyName',
                        width: 100,
                        tooltip:8,
                        // align: "center",
                        fixed:'left'
                    }
                },
                {
                    isInForm:false,
                    table: {
                        title: '????????????',
                        dataIndex: 'projectShortName',
                        width: 150,
                        tooltip:12,
                        // align: "center"
                    }
                },
                {
                    isInForm:false,
                    table: {
                        title: '???????????????',
                        dataIndex: 'subprojectName',
                        width: 150,
                        tooltip:12,
                    }
                },
                {
                    isInForm:false,
                    table: {
                        title: '??????',
                        dataIndex: 'periodWarn',
                        width: 80,
                        tooltip:9,
                        align: "center"
                    }
                },
                {
                    isInForm:false,
                    table: {
                        title: '?????????(???)',
                        dataIndex: 'constructPeriod',
                        width: 100,
                        align: "center"
                    }
                },
                {
                    isInForm:false,
                    table: {
                        title: '?????????????????????',
                        dataIndex: 'constructEnd',
                        width: 120,
                        tooltip:9,
                        align: "center"
                    }
                },
                {
                    isInForm:false,
                    table: {
                        title: '??????????????????',
                        dataIndex: 'actualDate',
                        width: 120,
                        align: "center"
                    }
                },
                {
                    isInForm:false,
                    table: {
                        title: dateType === '1' ? '????????????????????????' : '????????????????????????',
                        dataIndex: 'handoverDatePlan',
                        width: 130,
                        align: "center"
                    }
                },
                {
                    isInForm:false,
                    table: {
                        title: '??????????????????',
                        dataIndex: 'handoverDateActrual',
                        width: 120,
                        align: "center"
                    }
                },
                {
                    isInForm:false,
                    table: {
                        title: '?????????????????????',
                        dataIndex: 'constructCurrentDay',
                        width: 120,
                        align: "center"
                    }
                },
                {
                    isInForm:false,
                    table: {
                        title: '?????????????????????',
                        dataIndex: 'constructActualDay',
                        width: 120,
                        align: "center"
                    }
                },
                {
                    isInForm:false,
                    table: {
                        title: '??????????????????',
                        dataIndex: 'handoverDelayDay',
                        width: 120,
                        align: "center"
                    }
                },
                {
                    isInForm:false,
                    table: {
                        title: '??????????????????',
                        dataIndex: 'handoverAdvanceDay',
                        width: 120,
                        align: "center"
                    }
                },
                {
                    isInForm:false,
                    table: {
                        title: '??????????????????',
                        dataIndex: 'handoverDay',
                        width: 120,
                        align: "center"
                    }
                },
                {
                    isInForm:false,
                    table: {
                        title: dateType === '1' ? '????????????????????????' : '????????????????????????',
                        dataIndex: 'complateDatePlan',
                        width: 130,
                        align: "center"
                    }
                },
                {
                    isInForm:false,
                    table: {
                        title: '????????????????????????',
                        dataIndex: 'completeCurrentDay',
                        width: 130,
                        align: "center"
                    }
                },
                {
                    isInForm:false,
                    table: {
                        title: '??????????????????',
                        dataIndex: 'completeDay',
                        width: 120,
                        align: "center"
                    }
                },
            ]
        }else if(params.name === '????????????????????????'){
            status = '2';
            leftTableConfig = [
                {
                    isInForm:false,
                    table: {
                        title: '????????????',
                        dataIndex: 'proNo',
                        width: 80,
                        tooltip:8,
                        // align: "center",
                        fixed:'left'
                    }
                },
                {
                    isInForm:false,
                    table: {
                        title: '????????????',
                        dataIndex: 'companyName',
                        width: 100,
                        tooltip:8,
                        // align: "center",
                        fixed:'left'
                    }
                },
                {
                    isInForm:false,
                    table: {
                        title: '????????????',
                        dataIndex: 'projectShortName',
                        width: 150,
                        tooltip:12,
                        // align: "center"
                    }
                },
                {
                    isInForm:false,
                    table: {
                        title: '???????????????',
                        dataIndex: 'subprojectName',
                        width: 150,
                        tooltip:12,
                    }
                },
                {
                    isInForm:false,
                    table: {
                        title: '??????',
                        dataIndex: 'periodWarn',
                        width: 80,
                        tooltip:9,
                        align: "center"
                    }
                },
                {
                    isInForm:false,
                    table: {
                        title: '?????????(???)',
                        dataIndex: 'constructPeriod',
                        width: 100,
                        align: "center"
                    }
                },
                {
                    isInForm:false,
                    table: {
                        title: '?????????????????????',
                        dataIndex: 'constructEnd',
                        width: 120,
                        tooltip:9,
                        align: "center"
                    }
                },
                {
                    isInForm:false,
                    table: {
                        title: '??????????????????',
                        dataIndex: 'actualDate',
                        width: 120,
                        align: "center"
                    }
                },
                {
                    isInForm:false,
                    table: {
                        title: dateType === '1' ? '????????????????????????' : '????????????????????????',
                        dataIndex: 'handoverDatePlan',
                        width: 130,
                        align: "center"
                    }
                },
                {
                    isInForm:false,
                    table: {
                        title: '??????????????????',
                        dataIndex: 'handoverDateActrual',
                        width: 120,
                        align: "center"
                    }
                },
                {
                    isInForm:false,
                    table: {
                        title: '?????????????????????',
                        dataIndex: 'constructActualDay',
                        width: 120,
                        align: "center"
                    }
                },
                {
                    isInForm:false,
                    table: {
                        title: '??????????????????',
                        dataIndex: 'handoverAdvanceDay',
                        width: 120,
                        align: "center"
                    }
                },
                {
                    isInForm:false,
                    table: {
                        title: '??????????????????',
                        dataIndex: 'handoverDelayDay',
                        width: 120,
                        align: "center"
                    }
                },
                {
                    isInForm:false,
                    table: {
                        title: dateType === '1' ? '????????????????????????' : '????????????????????????',
                        dataIndex: 'complateDatePlan',
                        width: 130,
                        align: "center"
                    }
                },
                {
                    isInForm:false,
                    table: {
                        title: '??????????????????',
                        dataIndex: 'complateDateActrual',
                        width: 120,
                        align: "center"
                    }
                },
                {
                    isInForm:false,
                    table: {
                        title: '????????????????????????',
                        dataIndex: 'completeActualDay',
                        width: 130,
                        align: "center"
                    }
                },
                {
                    isInForm:false,
                    table: {
                        title: '??????????????????',
                        dataIndex: 'completeDelayDay',
                        width: 120,
                        align: "center"
                    }
                },
                {
                    isInForm:false,
                    table: {
                        title: '??????????????????',
                        dataIndex: 'completeAdvanceDay',
                        width: 120,
                        align: "center"
                    }
                }
            ]
        }else if(params.name === '??????????????????????????????'){
            status = '4';
            leftTableConfig = [
                {
                    isInForm:false,
                    table: {
                        title: '????????????',
                        dataIndex: 'proNo',
                        width: 80,
                        tooltip:8,
                        // align: "center",
                        fixed:'left'
                    }
                },
                {
                    isInForm:false,
                    table: {
                        title: '????????????',
                        dataIndex: 'companyName',
                        width: 100,
                        tooltip:8,
                        // align: "center",
                        fixed:'left'
                    }
                },
                {
                    isInForm:false,
                    table: {
                        title: '????????????',
                        dataIndex: 'projectShortName',
                        width: 150,
                        tooltip:12,
                        // align: "center"
                    }
                },
                {
                    isInForm:false,
                    table: {
                        title: '???????????????',
                        dataIndex: 'subprojectName',
                        width: 150,
                        tooltip:12,
                    }
                },
                {
                    isInForm:false,
                    table: {
                        title: '??????',
                        dataIndex: 'periodWarn',
                        width: 80,
                        tooltip:9,
                        align: "center"
                    }
                },
                {
                    isInForm:false,
                    table: {
                        title: '?????????(???)',
                        dataIndex: 'constructPeriod',
                        width: 100,
                        align: "center"
                    }
                },
                {
                    isInForm:false,
                    table: {
                        title: '?????????????????????',
                        dataIndex: 'constructEnd',
                        width: 120,
                        tooltip:9,
                        align: "center"
                    }
                },
                {
                    isInForm:false,
                    table: {
                        title: '??????????????????',
                        dataIndex: 'actualDate',
                        width: 120,
                        align: "center"
                    }
                },
                {
                    isInForm:false,
                    table: {
                        title: dateType === '1' ? '????????????????????????' : '????????????????????????',
                        dataIndex: 'handoverDatePlan',
                        width: 130,
                        align: "center"
                    }
                },
                {
                    isInForm:false,
                    table: {
                        title: '??????????????????',
                        dataIndex: 'handoverDateActrual',
                        width: 120,
                        align: "center"
                    }
                },
                {
                    isInForm:false,
                    table: {
                        title: '?????????????????????',
                        dataIndex: 'constructCurrentDay',
                        width: 120,
                        align: "center"
                    }
                },
                {
                    isInForm:false,
                    table: {
                        title: '?????????????????????',
                        dataIndex: 'constructActualDay',
                        width: 120,
                        align: "center"
                    }
                },
                {
                    isInForm:false,
                    table: {
                        title: '??????????????????',
                        dataIndex: 'handoverDelayDay',
                        width: 120,
                        align: "center"
                    }
                },
                {
                    isInForm:false,
                    table: {
                        title: '??????????????????',
                        dataIndex: 'handoverAdvanceDay',
                        width: 120,
                        align: "center"
                    }
                },
                {
                    isInForm:false,
                    table: {
                        title: dateType === '1' ? '????????????????????????' : '????????????????????????',
                        dataIndex: 'complateDatePlan',
                        width: 130,
                        align: "center"
                    }
                },
                {
                    isInForm:false,
                    table: {
                        title: '????????????????????????',
                        dataIndex: 'completeCurrentDay',
                        width: 130,
                        align: "center"
                    }
                },
                {
                    isInForm:false,
                    table: {
                        title: '??????????????????',
                        dataIndex: 'overduecompleteDay',
                        width: 120,
                        align: "center"
                    }
                },
            ]
        }else if(params.name === '??????????????????????????????'){
            status = '5';
            leftTableConfig = [
                {
                    isInForm:false,
                    table: {
                        title: '????????????',
                        dataIndex: 'proNo',
                        width: 80,
                        tooltip:8,
                        // align: "center",
                        fixed:'left'
                    }
                },
                {
                    isInForm:false,
                    table: {
                        title: '????????????',
                        dataIndex: 'companyName',
                        width: 100,
                        tooltip:8,
                        // align: "center",
                        fixed:'left'
                    }
                },
                {
                    isInForm:false,
                    table: {
                        title: '????????????',
                        dataIndex: 'projectShortName',
                        width: 150,
                        tooltip:12,
                        // align: "center"
                    }
                },
                {
                    isInForm:false,
                    table: {
                        title: '???????????????',
                        dataIndex: 'subprojectName',
                        width: 150,
                        tooltip:12,
                    }
                },
                {
                    isInForm:false,
                    table: {
                        title: '??????',
                        dataIndex: 'periodWarn',
                        width: 80,
                        tooltip:9,
                        align: "center"
                    }
                },
                {
                    isInForm:false,
                    table: {
                        title: '?????????(???)',
                        dataIndex: 'constructPeriod',
                        width: 100,
                        align: "center"
                    }
                },
                {
                    isInForm:false,
                    table: {
                        title: '?????????????????????',
                        dataIndex: 'constructEnd',
                        width: 120,
                        tooltip:9,
                        align: "center"
                    }
                },
                {
                    isInForm:false,
                    table: {
                        title: '??????????????????',
                        dataIndex: 'actualDate',
                        width: 120,
                        align: "center"
                    }
                },
                {
                    isInForm:false,
                    table: {
                        title: dateType === '1' ? '????????????????????????' : '????????????????????????',
                        dataIndex: 'handoverDatePlan',
                        width: 130,
                        align: "center"
                    }
                },
                {
                    isInForm:false,
                    table: {
                        title: '??????????????????',
                        dataIndex: 'handoverDateActrual',
                        width: 120,
                        align: "center"
                    }
                },
                {
                    isInForm:false,
                    table: {
                        title: '?????????????????????',
                        dataIndex: 'constructCurrentDay',
                        width: 120,
                        align: "center"
                    }
                },
                {
                    isInForm:false,
                    table: {
                        title: '??????????????????',
                        dataIndex: 'overdueHandoverDay',
                        width: 120,
                        align: "center"
                    }
                },
                {
                    isInForm:false,
                    table: {
                        title: dateType === '1' ? '????????????????????????' : '????????????????????????',
                        dataIndex: 'complateDatePlan',
                        width: 130,
                        align: "center"
                    }
                }
            ]
        }
        this.setState({
            title: dateType === '1' ? `????????????-${params.name}` : `????????????-${params.name}`,
            visible: true,
            status,
            leftTableConfig
        })
    }
    render() {
        const {
            loading,
            visible,
            title,
            dateType,
            status,
            leftTableConfig
        } = this.state;
        const qnnTableCommConfig = {
            fetch: this.props.myFetch,
            paginationConfig: {
                position: 'bottom'
            },
            isShowRowSelect: false,
        }
        return (
            <div className={s.TimeLimitForAProjectEarlyWarning}>
                <div className={s.leftTop}>
                    <div className={s.leftTopL}>
                        ????????????
                    </div>
                    <div className={s.leftTopR}>
                        <Button size="small" style={{ width: '70px', height: '30px' }} type="primary" onClick={() => {
                            const {
                                loginAndLogoutInfo: {
                                    loginInfo: { token }
                                },
                                myPublic: { domain }
                            } = this.props;
                            let body = {
                                fileName: '?????????????????????_' + moment(new Date()).format('YYYYMMDD')
                            }
                            let URL = `${domain + "exportHomeProjectStatus"}`;
                            confirm({
                                content: '??????????????????????',
                                centered: true,
                                onOk: () => {
                                    downLoad(URL, body, { token });
                                }
                            });
                        }}>??????</Button>
                    </div>
                    <div className={s.leftTopOneR}>
                        <QnnForm
                            fetch={this.props.myFetch}
                            upload={this.props.myUpload}
                            headers={{
                                token: this.props.loginAndLogoutInfo.loginInfo.token
                            }}
                            wrappedComponentRef={(me) => {
                                this.formT = me;
                            }}
                            formItemLayout={{
                                labelCol: {
                                    xs: { span: 12 },
                                    sm: { span: 12 }
                                },
                                wrapperCol: {
                                    xs: { span: 12 },
                                    sm: { span: 12 }
                                }
                            }}
                            formConfig={[
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
                                    onChange: () => {
                                        this.refresh(); 
                                    },
                                    initialValue:'1',
                                    allowClear: false,
                                    size: 'small',
                                    labelStyle: {
                                        color: 'rgba(255,255,255,0.5)'
                                    },
                                    placeholder: '?????????'
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
                            onEvents={{
                                'click': this.onChartTowClick
                            }}
                        />
                    </Spin>
                </div>
                {visible ? <Modal
                    width={'80%'}
                    style={{ paddingBottom: '0', top: '0' }}
                    title={title}
                    visible={visible}
                    footer={null}
                    bodyStyle={{ padding: '10px' }}
                    centered={true}
                    maskClosable={false}
                    onCancel={() => {
                        this.setState({
                            visible: false
                        })
                    }}
                    wrapClassName={'timeModals'}
                >
                    <QnnTable
                        {...qnnTableCommConfig}
                        fetchConfig={{
                            apiName:'getHomeProjectStatusAllProjectAlertPage',
                            otherParams:{
                                status:status,
                                dateType:dateType
                            }
                        }}
                        antd={{
                            rowKey: "projectId",
                            size: "small",
                            scroll: {
                                y: window.innerHeight * 0.8 - 110
                            }
                        }}
                        formConfig={leftTableConfig}
                    />
                </Modal> : null}
            </div>
        );
    }
}

export default index;