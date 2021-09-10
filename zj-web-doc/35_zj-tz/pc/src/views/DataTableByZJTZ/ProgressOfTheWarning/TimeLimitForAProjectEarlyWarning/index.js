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
                    data: ['建设中项目数量', '正常完工项目数量', '超期完工项目数量', '应交工未交工项目数量', '应竣工未竣工项目数量']
                }
            ],
            series: [
                {
                    name: '数量',
                    type: 'pie',
                    center: ['60%', '50%'],
                    radius: '75%',
                    data: [
                        { value: data?.[0]?.count3 || 0, name: '建设中项目数量' },
                        { value: data?.[0]?.count1 || 0, name: '正常完工项目数量' },
                        { value: data?.[0]?.count2 || 0, name: '超期完工项目数量' },
                        { value: data?.[0]?.count5 || 0, name: '应交工未交工项目数量' },
                        { value: data?.[0]?.count4 || 0, name: '应竣工未竣工项目数量' }
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
        if(params.name === '正常完工项目数量'){
            status = '1';
            leftTableConfig = [
                {
                    isInForm:false,
                    table: {
                        title: '项目编号',
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
                        title: '管理单位',
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
                        title: '项目简称',
                        dataIndex: 'projectShortName',
                        width: 150,
                        tooltip:12,
                        // align: "center"
                    }
                },
                {
                    isInForm:false,
                    table: {
                        title: '子项目名称',
                        dataIndex: 'subprojectName',
                        width: 150,
                        tooltip:12,
                    }
                },
                {
                    isInForm:false,
                    table: {
                        title: '分类',
                        dataIndex: 'periodWarn',
                        width: 80,
                        tooltip:9,
                        align: "center"
                    }
                },
                {
                    isInForm:false,
                    table: {
                        title: '建设期(月)',
                        dataIndex: 'constructPeriod',
                        width: 100,
                        align: "center"
                    }
                },
                {
                    isInForm:false,
                    table: {
                        title: '建设期结束标志',
                        dataIndex: 'constructEnd',
                        width: 120,
                        tooltip:9,
                        align: "center"
                    }
                },
                {
                    isInForm:false,
                    table: {
                        title: '实际开工日期',
                        dataIndex: 'actualDate',
                        width: 120,
                        align: "center"
                    }
                },
                {
                    isInForm:false,
                    table: {
                        title: dateType === '1' ? '合同约定交工时间' : '策划批复交工时间',
                        dataIndex: 'handoverDatePlan',
                        width: 130,
                        align: "center"
                    }
                },
                {
                    isInForm:false,
                    table: {
                        title: '实际交工日期',
                        dataIndex: 'handoverDateActrual',
                        width: 120,
                        align: "center"
                    }
                },
                {
                    isInForm:false,
                    table: {
                        title: '实际建设期天数',
                        dataIndex: 'constructActualDay',
                        width: 120,
                        align: "center"
                    }
                },
                {
                    isInForm:false,
                    table: {
                        title: '交工提前天数',
                        dataIndex: 'handoverAdvanceDay',
                        width: 120,
                        align: "center"
                    }
                },
                {
                    isInForm:false,
                    table: {
                        title: dateType === '1' ? '合同约定竣工时间' : '策划批复竣工时间',
                        dataIndex: 'complateDatePlan',
                        width: 130,
                        align: "center"
                    }
                },
                {
                    isInForm:false,
                    table: {
                        title: '实际竣工日期',
                        dataIndex: 'complateDateActrual',
                        width: 120,
                        align: "center"
                    }
                },
                {
                    isInForm:false,
                    table: {
                        title: '实际竣工阶段天数',
                        dataIndex: 'completeActualDay',
                        width: 130,
                        align: "center"
                    }
                },
                {
                    isInForm:false,
                    table: {
                        title: '竣工提前天数',
                        dataIndex: 'completeAdvanceDay',
                        width: 120,
                        align: "center"
                    }
                }
            ]
        }else if(params.name === '建设中项目数量'){
            status = '3';
            leftTableConfig = [
                {
                    isInForm:false,
                    table: {
                        title: '项目编号',
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
                        title: '管理单位',
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
                        title: '项目简称',
                        dataIndex: 'projectShortName',
                        width: 150,
                        tooltip:12,
                        // align: "center"
                    }
                },
                {
                    isInForm:false,
                    table: {
                        title: '子项目名称',
                        dataIndex: 'subprojectName',
                        width: 150,
                        tooltip:12,
                    }
                },
                {
                    isInForm:false,
                    table: {
                        title: '分类',
                        dataIndex: 'periodWarn',
                        width: 80,
                        tooltip:9,
                        align: "center"
                    }
                },
                {
                    isInForm:false,
                    table: {
                        title: '建设期(月)',
                        dataIndex: 'constructPeriod',
                        width: 100,
                        align: "center"
                    }
                },
                {
                    isInForm:false,
                    table: {
                        title: '建设期结束标志',
                        dataIndex: 'constructEnd',
                        width: 120,
                        tooltip:9,
                        align: "center"
                    }
                },
                {
                    isInForm:false,
                    table: {
                        title: '实际开工日期',
                        dataIndex: 'actualDate',
                        width: 120,
                        align: "center"
                    }
                },
                {
                    isInForm:false,
                    table: {
                        title: dateType === '1' ? '合同约定交工时间' : '策划批复交工时间',
                        dataIndex: 'handoverDatePlan',
                        width: 130,
                        align: "center"
                    }
                },
                {
                    isInForm:false,
                    table: {
                        title: '实际交工日期',
                        dataIndex: 'handoverDateActrual',
                        width: 120,
                        align: "center"
                    }
                },
                {
                    isInForm:false,
                    table: {
                        title: '当前建设期天数',
                        dataIndex: 'constructCurrentDay',
                        width: 120,
                        align: "center"
                    }
                },
                {
                    isInForm:false,
                    table: {
                        title: '实际建设期天数',
                        dataIndex: 'constructActualDay',
                        width: 120,
                        align: "center"
                    }
                },
                {
                    isInForm:false,
                    table: {
                        title: '交工推迟天数',
                        dataIndex: 'handoverDelayDay',
                        width: 120,
                        align: "center"
                    }
                },
                {
                    isInForm:false,
                    table: {
                        title: '交工提前天数',
                        dataIndex: 'handoverAdvanceDay',
                        width: 120,
                        align: "center"
                    }
                },
                {
                    isInForm:false,
                    table: {
                        title: '距离交工天数',
                        dataIndex: 'handoverDay',
                        width: 120,
                        align: "center"
                    }
                },
                {
                    isInForm:false,
                    table: {
                        title: dateType === '1' ? '合同约定竣工时间' : '策划批复竣工时间',
                        dataIndex: 'complateDatePlan',
                        width: 130,
                        align: "center"
                    }
                },
                {
                    isInForm:false,
                    table: {
                        title: '当前竣工阶段天数',
                        dataIndex: 'completeCurrentDay',
                        width: 130,
                        align: "center"
                    }
                },
                {
                    isInForm:false,
                    table: {
                        title: '距离竣工天数',
                        dataIndex: 'completeDay',
                        width: 120,
                        align: "center"
                    }
                },
            ]
        }else if(params.name === '超期完工项目数量'){
            status = '2';
            leftTableConfig = [
                {
                    isInForm:false,
                    table: {
                        title: '项目编号',
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
                        title: '管理单位',
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
                        title: '项目简称',
                        dataIndex: 'projectShortName',
                        width: 150,
                        tooltip:12,
                        // align: "center"
                    }
                },
                {
                    isInForm:false,
                    table: {
                        title: '子项目名称',
                        dataIndex: 'subprojectName',
                        width: 150,
                        tooltip:12,
                    }
                },
                {
                    isInForm:false,
                    table: {
                        title: '分类',
                        dataIndex: 'periodWarn',
                        width: 80,
                        tooltip:9,
                        align: "center"
                    }
                },
                {
                    isInForm:false,
                    table: {
                        title: '建设期(月)',
                        dataIndex: 'constructPeriod',
                        width: 100,
                        align: "center"
                    }
                },
                {
                    isInForm:false,
                    table: {
                        title: '建设期结束标志',
                        dataIndex: 'constructEnd',
                        width: 120,
                        tooltip:9,
                        align: "center"
                    }
                },
                {
                    isInForm:false,
                    table: {
                        title: '实际开工日期',
                        dataIndex: 'actualDate',
                        width: 120,
                        align: "center"
                    }
                },
                {
                    isInForm:false,
                    table: {
                        title: dateType === '1' ? '合同约定交工时间' : '策划批复交工时间',
                        dataIndex: 'handoverDatePlan',
                        width: 130,
                        align: "center"
                    }
                },
                {
                    isInForm:false,
                    table: {
                        title: '实际交工日期',
                        dataIndex: 'handoverDateActrual',
                        width: 120,
                        align: "center"
                    }
                },
                {
                    isInForm:false,
                    table: {
                        title: '实际建设期天数',
                        dataIndex: 'constructActualDay',
                        width: 120,
                        align: "center"
                    }
                },
                {
                    isInForm:false,
                    table: {
                        title: '交工提前天数',
                        dataIndex: 'handoverAdvanceDay',
                        width: 120,
                        align: "center"
                    }
                },
                {
                    isInForm:false,
                    table: {
                        title: '交工推迟天数',
                        dataIndex: 'handoverDelayDay',
                        width: 120,
                        align: "center"
                    }
                },
                {
                    isInForm:false,
                    table: {
                        title: dateType === '1' ? '合同约定竣工时间' : '策划批复竣工时间',
                        dataIndex: 'complateDatePlan',
                        width: 130,
                        align: "center"
                    }
                },
                {
                    isInForm:false,
                    table: {
                        title: '实际竣工日期',
                        dataIndex: 'complateDateActrual',
                        width: 120,
                        align: "center"
                    }
                },
                {
                    isInForm:false,
                    table: {
                        title: '实际竣工阶段天数',
                        dataIndex: 'completeActualDay',
                        width: 130,
                        align: "center"
                    }
                },
                {
                    isInForm:false,
                    table: {
                        title: '竣工推迟天数',
                        dataIndex: 'completeDelayDay',
                        width: 120,
                        align: "center"
                    }
                },
                {
                    isInForm:false,
                    table: {
                        title: '竣工提前天数',
                        dataIndex: 'completeAdvanceDay',
                        width: 120,
                        align: "center"
                    }
                }
            ]
        }else if(params.name === '应竣工未竣工项目数量'){
            status = '4';
            leftTableConfig = [
                {
                    isInForm:false,
                    table: {
                        title: '项目编号',
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
                        title: '管理单位',
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
                        title: '项目简称',
                        dataIndex: 'projectShortName',
                        width: 150,
                        tooltip:12,
                        // align: "center"
                    }
                },
                {
                    isInForm:false,
                    table: {
                        title: '子项目名称',
                        dataIndex: 'subprojectName',
                        width: 150,
                        tooltip:12,
                    }
                },
                {
                    isInForm:false,
                    table: {
                        title: '分类',
                        dataIndex: 'periodWarn',
                        width: 80,
                        tooltip:9,
                        align: "center"
                    }
                },
                {
                    isInForm:false,
                    table: {
                        title: '建设期(月)',
                        dataIndex: 'constructPeriod',
                        width: 100,
                        align: "center"
                    }
                },
                {
                    isInForm:false,
                    table: {
                        title: '建设期结束标志',
                        dataIndex: 'constructEnd',
                        width: 120,
                        tooltip:9,
                        align: "center"
                    }
                },
                {
                    isInForm:false,
                    table: {
                        title: '实际开工日期',
                        dataIndex: 'actualDate',
                        width: 120,
                        align: "center"
                    }
                },
                {
                    isInForm:false,
                    table: {
                        title: dateType === '1' ? '合同约定交工时间' : '策划批复交工时间',
                        dataIndex: 'handoverDatePlan',
                        width: 130,
                        align: "center"
                    }
                },
                {
                    isInForm:false,
                    table: {
                        title: '实际交工日期',
                        dataIndex: 'handoverDateActrual',
                        width: 120,
                        align: "center"
                    }
                },
                {
                    isInForm:false,
                    table: {
                        title: '当前建设期天数',
                        dataIndex: 'constructCurrentDay',
                        width: 120,
                        align: "center"
                    }
                },
                {
                    isInForm:false,
                    table: {
                        title: '实际建设期天数',
                        dataIndex: 'constructActualDay',
                        width: 120,
                        align: "center"
                    }
                },
                {
                    isInForm:false,
                    table: {
                        title: '交工推迟天数',
                        dataIndex: 'handoverDelayDay',
                        width: 120,
                        align: "center"
                    }
                },
                {
                    isInForm:false,
                    table: {
                        title: '交工提前天数',
                        dataIndex: 'handoverAdvanceDay',
                        width: 120,
                        align: "center"
                    }
                },
                {
                    isInForm:false,
                    table: {
                        title: dateType === '1' ? '合同约定竣工时间' : '策划批复竣工时间',
                        dataIndex: 'complateDatePlan',
                        width: 130,
                        align: "center"
                    }
                },
                {
                    isInForm:false,
                    table: {
                        title: '当前竣工阶段天数',
                        dataIndex: 'completeCurrentDay',
                        width: 130,
                        align: "center"
                    }
                },
                {
                    isInForm:false,
                    table: {
                        title: '超期竣工天数',
                        dataIndex: 'overduecompleteDay',
                        width: 120,
                        align: "center"
                    }
                },
            ]
        }else if(params.name === '应交工未交工项目数量'){
            status = '5';
            leftTableConfig = [
                {
                    isInForm:false,
                    table: {
                        title: '项目编号',
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
                        title: '管理单位',
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
                        title: '项目简称',
                        dataIndex: 'projectShortName',
                        width: 150,
                        tooltip:12,
                        // align: "center"
                    }
                },
                {
                    isInForm:false,
                    table: {
                        title: '子项目名称',
                        dataIndex: 'subprojectName',
                        width: 150,
                        tooltip:12,
                    }
                },
                {
                    isInForm:false,
                    table: {
                        title: '分类',
                        dataIndex: 'periodWarn',
                        width: 80,
                        tooltip:9,
                        align: "center"
                    }
                },
                {
                    isInForm:false,
                    table: {
                        title: '建设期(月)',
                        dataIndex: 'constructPeriod',
                        width: 100,
                        align: "center"
                    }
                },
                {
                    isInForm:false,
                    table: {
                        title: '建设期结束标志',
                        dataIndex: 'constructEnd',
                        width: 120,
                        tooltip:9,
                        align: "center"
                    }
                },
                {
                    isInForm:false,
                    table: {
                        title: '实际开工日期',
                        dataIndex: 'actualDate',
                        width: 120,
                        align: "center"
                    }
                },
                {
                    isInForm:false,
                    table: {
                        title: dateType === '1' ? '合同约定交工时间' : '策划批复交工时间',
                        dataIndex: 'handoverDatePlan',
                        width: 130,
                        align: "center"
                    }
                },
                {
                    isInForm:false,
                    table: {
                        title: '实际交工日期',
                        dataIndex: 'handoverDateActrual',
                        width: 120,
                        align: "center"
                    }
                },
                {
                    isInForm:false,
                    table: {
                        title: '当前建设期天数',
                        dataIndex: 'constructCurrentDay',
                        width: 120,
                        align: "center"
                    }
                },
                {
                    isInForm:false,
                    table: {
                        title: '超期交工天数',
                        dataIndex: 'overdueHandoverDay',
                        width: 120,
                        align: "center"
                    }
                },
                {
                    isInForm:false,
                    table: {
                        title: dateType === '1' ? '合同约定竣工时间' : '策划批复竣工时间',
                        dataIndex: 'complateDatePlan',
                        width: 130,
                        align: "center"
                    }
                }
            ]
        }
        this.setState({
            title: dateType === '1' ? `合同约定-${params.name}` : `策划批复-${params.name}`,
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
                        工期预警
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
                                fileName: '工期预警导出表_' + moment(new Date()).format('YYYYMMDD')
                            }
                            let URL = `${domain + "exportHomeProjectStatus"}`;
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
                                    label: '预警日期类型',
                                    field: 'dateType',
                                    type: 'select',
                                    optionConfig: {
                                        label: 'label',
                                        value: 'value'
                                    },
                                    optionData: [
                                        {
                                            label: '合同约定',
                                            value: '1'
                                        },
                                        {
                                            label: '策划批复',
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
                                    placeholder: '请选择'
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