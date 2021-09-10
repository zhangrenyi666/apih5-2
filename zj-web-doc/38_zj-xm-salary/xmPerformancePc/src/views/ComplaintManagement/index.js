import React, { Component } from "react";
import QnnTable from '../modules/qnn-table';
import { message as Msg } from 'antd';
import moment from 'moment';
import Operation from './operation';
const config = {
    antd: {
        rowKey: function (row) {
            return row.detailedId
        },
        size: 'small'
    },
    drawerConfig: {
        width: '900px'
    },
    paginationConfig: {
        position: 'bottom'
    },
    isShowRowSelect: false
}
class Index extends Component {
    render() {
        const { projectId } = this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany;
        return (
            <div>
                <QnnTable
                    fetch={this.props.myFetch}
                    upload={this.props.myUpload}
                    headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                    {...config}
                    fetchConfig={{
                        apiName: 'getZjXmJxTaskScoreDetailedListByHR',
                        otherParams: {
                            auditeeProId: projectId
                        }
                    }}
                    tabs={[
                        {
                            field: "jbxx",
                            name: "qnnForm",
                            title: "基本信息",
                            content: {
                                formConfig: [
                                    {
                                        field: 'detailedId',
                                        type: 'string',
                                        hide: true,
                                    },
                                    {
                                        label: '成本责任指标',
                                        field: 'costDutyIndex',
                                        type: 'string',
                                        disabled: true,
                                        placeholder: "请输入"
                                    },
                                    {
                                        label: '目标值',
                                        field: 'targetValue',
                                        type: 'string',
                                        disabled: true,
                                        placeholder: "请输入"
                                    },
                                    {
                                        label: '评价计分标准',
                                        field: 'scoringStandard',
                                        type: 'string',
                                        disabled: true,
                                        placeholder: "请输入"
                                    },
                                    {
                                        label: '权重',
                                        field: 'weightValue',
                                        type: 'number',
                                        disabled: true,
                                        placeholder: "请输入"
                                    },
                                    {
                                        label: '项目',
                                        field: 'auditeeProName',
                                        type: 'string',
                                        disabled: true,
                                        placeholder: "请输入"
                                    },
                                    {
                                        label: '部门',
                                        field: 'auditeeDeptName',
                                        type: 'string',
                                        disabled: true,
                                        placeholder: "请输入"
                                    },
                                    {
                                        label: '申诉者',
                                        field: 'auditeeName',
                                        type: 'string',
                                        disabled: true,
                                        placeholder: "请输入"
                                    },
                                    {
                                        label: '申诉意见',
                                        field: 'appealOpinion',
                                        type: 'textarea',
                                        disabled: true,
                                        placeholder: "请输入"
                                    },
                                    {
                                        label: '分数',
                                        field: 'score',
                                        type: 'number',
                                        disabled: true,
                                        hide:(obj) => {
                                            if(obj.initialValues.appealStatus === '1'){
                                                return true;
                                            }else{
                                                return false;
                                            }
                                        },
                                        placeholder: "请输入"
                                    },
                                    {
                                        label: '专员意见',
                                        field: 'hrOpinion',
                                        type: 'textarea',
                                        disabled: true,
                                        hide:(obj) => {
                                            if(obj.initialValues.appealStatus === '1'){
                                                return true;
                                            }else{
                                                return false;
                                            }
                                        },
                                        placeholder: "请输入"
                                    },
                                    {
                                        label: '分数',
                                        field: 'score',
                                        type: 'number',
                                        required: true,
                                        min:0,
                                        max:100,
                                        hide:(obj) => {
                                            if(obj.initialValues.appealStatus === '1'){
                                                return false;
                                            }else{
                                                return true;
                                            }
                                        },
                                        placeholder: "请输入"
                                    },
                                    {
                                        label: '专员意见',
                                        field: 'hrOpinion',
                                        type: 'textarea',
                                        hide:(obj) => {
                                            if(obj.initialValues.appealStatus === '1'){
                                                return false;
                                            }else{
                                                return true;
                                            }
                                        },
                                        placeholder: "请输入"
                                    },
                                ]
                            }
                        },
                        {
                            field: "diy1",
                            name: "diy1",
                            title: "历史意见",
                            content: props => {
                                return <Operation {...props} myFetch={this.props.myFetch} />;
                            }
                        }
                    ]}
                    formConfig={[
                        {
                            isInTable: false,
                            form: {
                                field: 'detailedId',
                                type: 'string',
                                hide: true,
                            }
                        },
                        {
                            table: {
                                title: '年月',
                                dataIndex: 'yearMonth',
                                key: 'yearMonth',
                                onClick: 'edit',
                                btns: (obj) => {
                                    if (obj.rowData.appealStatus === '1') {
                                        return [
                                            {
                                                name: 'cancel',
                                                type: 'dashed',
                                                label: '取消',
                                            },
                                            {
                                                field:'appeal',
                                                name: 'appeal',
                                                type: 'primary',
                                                label: '驳回',
                                                isValidate: false,
                                                onClick: (obj) => {
                                                    let setBtnsLoading = obj.btnCallbackFn.setBtnsLoading;
                                                    setBtnsLoading('add','appeal');
                                                    let body = {
                                                        appealStatus: '3',
                                                        detailedId: obj._formData.detailedId,
                                                        score: obj._formData.score,
                                                        hrOpinion: obj._formData.hrOpinion
                                                    };
                                                    this.props.myFetch('rejectOrConfirmZjXmJxTaskScoreDetailedByHR', body).then(({ success, message, data }) => {
                                                        if (success) {
                                                            setBtnsLoading('remove','appeal');
                                                            obj.btnCallbackFn.closeDrawer(false);
                                                            obj.btnCallbackFn.refresh();
                                                        } else {
                                                            setBtnsLoading('remove','appeal');
                                                            Msg.error(message);
                                                        }
                                                    });
                                                }
                                            },
                                            {
                                                field:'affirm',
                                                name: 'affirm',
                                                type: 'primary',
                                                label: '同意',
                                                onClick: (obj) => {
                                                    let setBtnsLoading = obj.btnCallbackFn.setBtnsLoading;
                                                    setBtnsLoading('add','affirm');
                                                    let body = {
                                                        appealStatus: '2',
                                                        detailedId: obj._formData.detailedId,
                                                        score: obj._formData.score,
                                                        hrOpinion: obj._formData.hrOpinion
                                                    };
                                                    this.props.myFetch('rejectOrConfirmZjXmJxTaskScoreDetailedByHR', body).then(({ success, message, data }) => {
                                                        if (success) {
                                                            setBtnsLoading('remove','affirm');
                                                            obj.btnCallbackFn.closeDrawer(false);
                                                            obj.btnCallbackFn.refresh();
                                                        } else {
                                                            setBtnsLoading('remove','affirm');
                                                            Msg.error(message);
                                                        }
                                                    });
                                                }
                                            }
                                        ]
                                    } else {
                                        return [
                                            {
                                                name: 'cancel',
                                                type: 'dashed',
                                                label: '取消',
                                            }
                                        ]
                                    }
                                },
                                filter: true,
                                fixed: "left",
                                width: 100,
                                render: (data) => {
                                    return moment(data).format('YYYY-MM');
                                }
                            },
                            form: {
                                type: 'month',
                                hide: true,
                                placeholder: '请选择'
                            }
                        },
                        {
                            table: {
                                title: '成本责任指标',
                                dataIndex: 'costDutyIndex',
                                key: 'costDutyIndex',
                                fixed: "left",
                                filter: true,
                                width: 150,
                                tooltip:9
                            },
                            form: {
                                type: 'string',
                                hide: true,
                                placeholder: '请输入'
                            }
                        },
                        {
                            table: {
                                title: '目标值',
                                dataIndex: 'targetValue',
                                key: 'targetValue',
                                width: 100,
                                tooltip:6
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '评价计分标准',
                                dataIndex: 'scoringStandard',
                                key: 'scoringStandard',
                                width: 150,
                                tooltip:9
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '权重',
                                dataIndex: 'weightValue',
                                key: 'weightValue',
                                width: 100
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '分数',
                                dataIndex: 'score',
                                key: 'score',
                                width: 100
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '项目',
                                dataIndex: 'auditeeProName',
                                key: 'auditeeProName',
                                width: 200,
                                tooltip:12
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '部门',
                                dataIndex: 'auditeeDeptName',
                                key: 'auditeeDeptName',
                                width: 200,
                                tooltip:12
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '申诉者',
                                dataIndex: 'auditeeName',
                                key: 'auditeeName',
                                width: 100
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '申诉意见',
                                dataIndex: 'appealOpinion',
                                key: 'appealOpinion',
                                width: 150,
                                tooltip:9
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '人事专员意见',
                                dataIndex: 'hrOpinion',
                                key: 'hrOpinion',
                                width: 150,
                                tooltip:9
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '申诉状态',
                                dataIndex: 'appealStatus',
                                key: 'appealStatus',
                                width: 100,
                                render: (data) => {
                                    if (data === '0') {
                                        return '未申诉';
                                    } else if (data === '1') {
                                        return '申诉中';
                                    } else if (data === '2') {
                                        return '同意';
                                    } else if (data === '3') {
                                        return '驳回';
                                    } else {
                                        return '--';
                                    }
                                }
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '人事专员',
                                dataIndex: 'hrName',
                                key: 'hrName',
                                width: 100
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '时间',
                                dataIndex: 'modifyTime',
                                key: 'modifyTime',
                                format: 'YYYY-MM-DD',
                                width: 100
                            },
                            isInForm: false
                        },
                    ]}
                />
            </div>
        );
    }
}
export default Index;