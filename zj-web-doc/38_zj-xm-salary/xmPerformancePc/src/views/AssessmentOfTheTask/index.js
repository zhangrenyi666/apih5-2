import React, { Component } from "react";
import QnnTable from '../modules/qnn-table';
import { message as Msg, Button, Modal } from 'antd';
import moment from 'moment';
const { confirm } = Modal;
const config = {
    antd: {
        rowKey: function (row) {
            return row.scoreId
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
    constructor() {
        super();
        this.state = {
            data: []
        }
    }
    handleCancel = () => {
        this.setState({
            visible: false
        })
    }
    render() {
        const { projectId } = this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany;
        return (
            <div>
                <QnnTable
                    fetch={this.props.myFetch}
                    upload={this.props.myUpload}
                    headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                    {...config}
                    wrappedComponentRef={(me) => {
                        this.table = me;
                    }}
                    fetchConfig={{
                        apiName: 'getZjXmJxAssessmentUserScoreListByTaskAuditee',
                        otherParams: {
                            projectId: projectId,
                            noticeStatus: '1'
                        }
                    }}
                    formConfig={[
                        {
                            isInTable: false,
                            form: {
                                field: 'scoreId',
                                type: 'string',
                                hide: true,
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: '姓名',
                                field: 'auditeeName',
                                type: 'string',
                                addDisabled: true,
                                editDisabled: true,
                                placeholder: "请选择",
                                spanForm: 12
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: '部门',
                                field: 'auditeeDeptName',
                                type: 'string',
                                addDisabled: true,
                                editDisabled: true,
                                placeholder: "请选择",
                                spanForm: 12
                            }
                        },
                        {
                            table: {
                                title: '年月',
                                dataIndex: 'yearMonth',
                                key: 'yearMonth',
                                onClick: 'edit',
                                filter: true,
                                willExecute: (obj) => {
                                    this.props.myFetch('getZjXmJxTaskScoreDetailedListByAuditee', { scoreId: obj.rowData.scoreId }).then(({ success, message, data }) => {
                                        if (success) {
                                            this.setState({
                                                data
                                            })
                                        } else {
                                            Msg.error(message);
                                        }
                                    });
                                },
                                btns: (obj) => {
                                    if (obj.rowData.auditStatus === '0' || obj.rowData.auditStatus === '1' || obj.rowData.auditStatus === '5') {
                                        return [
                                            {
                                                field: 'Dcancel',
                                                name: 'cancel',
                                                type: 'dashed',
                                                label: '取消',
                                            },
                                            {
                                                field: 'Dappeal',
                                                name: 'appeal',
                                                type: 'primary',
                                                label: '暂存',
                                                onClick: (obj) => {
                                                    let setBtnsLoading = obj.btnCallbackFn.setBtnsLoading;
                                                    setBtnsLoading('add','Dappeal');
                                                    this.props.myFetch('tempOrConfirmZjXmJxTaskScoreDetailedByAuditee', { scoreId: obj.rowData.scoreId, auditStatus: '1', scoreDetailedList: this.state.data }).then(({ success, message, data }) => {
                                                        if (success) {
                                                            setBtnsLoading('remove','Dappeal');
                                                            obj.btnCallbackFn.closeDrawer(false);
                                                            obj.btnCallbackFn.refresh();
                                                        } else {
                                                            setBtnsLoading('remove','Dappeal');
                                                            Msg.error(message);
                                                        }
                                                    });
                                                }
                                            },
                                            {
                                                field: 'Daffirm',
                                                name: 'affirm',
                                                type: 'primary',
                                                label: '确认',
                                                onClick: (obj) => {
                                                    let setBtnsLoading = obj.btnCallbackFn.setBtnsLoading;
                                                    setBtnsLoading('add','Daffirm');
                                                    for (let i = 0; i < this.state.data.length; i++) {
                                                        if (this.state.data[i].appealStatus === '1') {
                                                            setBtnsLoading('remove','Daffirm');
                                                            Msg.error('申诉中的数据没有完成审核，不可确认!');
                                                            break;
                                                        } else if (i === this.state.data.length - 1) {
                                                            this.props.myFetch('tempOrConfirmZjXmJxTaskScoreDetailedByAuditee', { scoreId: obj.rowData.scoreId, auditStatus: '2', scoreDetailedList: this.state.data }).then(({ success, message, data }) => {
                                                                if (success) {
                                                                    setBtnsLoading('remove','Daffirm');
                                                                    obj.btnCallbackFn.closeDrawer(false);
                                                                    obj.btnCallbackFn.refresh();
                                                                } else {
                                                                    setBtnsLoading('remove','Daffirm');
                                                                    Msg.error(message);
                                                                }
                                                            });
                                                        }
                                                    }
                                                }
                                            }
                                        ]
                                    } else {
                                        return [
                                            {
                                                field: 'Dcancel',
                                                name: 'cancel',
                                                type: 'dashed',
                                                label: '取消',
                                            }
                                        ]
                                    }
                                },
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
                                title: '考核标题',
                                dataIndex: 'assessmentTitle',
                                key: 'assessmentTitle',
                                filter: true,
                                tooltip: 20,
                                width: 200
                            },
                            form: {
                                type: 'string',
                                addDisabled: true,
                                editDisabled: true,
                                placeholder: "请输入",
                                formItemLayout: {
                                    //默认数据
                                    labelCol: {
                                        xs: { span: 2 },
                                        sm: { span: 2 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 22 },
                                        sm: { span: 22 }
                                    }
                                }
                            }
                        },
                        {
                            table: {
                                title: '确认状态',
                                dataIndex: 'confirmStatus',
                                key: 'confirmStatus',
                                width: 100,
                                render: (data) => {
                                    if (data === '0') {
                                        return '未确认';
                                    } else if (data === '1') {
                                        return '手动确认';
                                    } else if (data === '2') {
                                        return '自动确认';
                                    } else {
                                        return '--';
                                    }
                                }
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '得分',
                                dataIndex: 'score',
                                key: 'score',
                                width: 100
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '审核者',
                                dataIndex: 'reviewerName',
                                key: 'reviewerName',
                                width: 100
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '审核时间',
                                dataIndex: 'auditTime',
                                key: 'auditTime',
                                format: 'YYYY-MM-DD',
                                width: 100
                            },
                            isInForm: false
                        },
                        {
                            isInTable: false,
                            form: {
                                type: "component",
                                field: "component1",
                                Component: objs => {
                                    return (
                                        <div style={{ padding: '0 10px' }}>
                                            <QnnTable
                                                fetch={this.props.myFetch}
                                                upload={this.props.myUpload}
                                                headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                                                data={this.state.data}
                                                wrappedComponentRef={(me) => {
                                                    this.tables = me;
                                                }}
                                                antd={{
                                                    rowKey: 'detailedId',
                                                    size: 'small'
                                                }}
                                                isShowRowSelect={false}
                                                formConfig={[
                                                    {
                                                        table: {
                                                            title: '成本责任指标',
                                                            dataIndex: 'costDutyIndex',
                                                            key: 'costDutyIndex',
                                                            width: 200,
                                                            tooltip:12,
                                                            fixed: 'left'
                                                        },
                                                        isInForm: false
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
                                                            title: '申诉意见',
                                                            dataIndex: 'appealOpinion',
                                                            key: 'appealOpinion',
                                                            width: 150,
                                                            tooltip:9,
                                                            tdEdit: true,
                                                            fieldsConfig: {
                                                                type: "string",
                                                                disabled: (obj) => {
                                                                    if ((obj.record.isAutomaticScoring === '0' || obj.record.appealStatus === '1') || (objs.initialValues.auditStatus === '2' || objs.initialValues.auditStatus === '3' || objs.initialValues.auditStatus === '4')) {
                                                                        return true;
                                                                    } else {
                                                                        return false;
                                                                    }
                                                                },
                                                                placeholder: "请输入"
                                                            },
                                                            tdEditCb: (obj) => {
                                                                this.state.data.map((item) => {
                                                                    if (item.detailedId === obj.newRowData.detailedId) {
                                                                        item.appealOpinion = obj.newRowData.appealOpinion;
                                                                    }
                                                                    return item;
                                                                });
                                                            }
                                                        },
                                                        isInForm: false
                                                    },
                                                    {
                                                        table: {
                                                            title: '申诉操作',
                                                            dataIndex: 'sscz',
                                                            key: 'sscz',
                                                            width: 100,
                                                            render: (data, rowData) => {
                                                                if ((rowData.isAutomaticScoring === '0' || rowData.appealStatus === '1') || (objs.initialValues.auditStatus === '2' || objs.initialValues.auditStatus === '3' || objs.initialValues.auditStatus === '4')) {
                                                                    return '';
                                                                } else {
                                                                    return <Button type="primary" onClick={() => {
                                                                        confirm({
                                                                            content: '您确认申诉该信息吗?',
                                                                            centered: true,
                                                                            onOk: () => {
                                                                                this.props.myFetch('appealZjXmJxTaskScoreDetailedByAuditee', { appealOpinion: rowData.appealOpinion, detailedId: rowData.detailedId }).then(({ success, message, data }) => {
                                                                                    if (success) {
                                                                                        this.state.data.map((item) => {
                                                                                            if (item.detailedId === rowData.detailedId) {
                                                                                                item.appealOpinion = rowData.appealOpinion;
                                                                                                item.appealStatus = '1'
                                                                                            }
                                                                                            return item;
                                                                                        });
                                                                                        this.tables.refresh();
                                                                                        Msg.success(message);
                                                                                    } else {
                                                                                        Msg.error(message);
                                                                                    }
                                                                                });
                                                                            }
                                                                        });
                                                                    }}>申诉</Button>;
                                                                }
                                                            }
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
                                                            title: '人事专员意见',
                                                            dataIndex: 'hrOpinion',
                                                            key: 'hrOpinion',
                                                            width: 150,
                                                            tooltip:9,
                                                        },
                                                        isInForm: false
                                                    },
                                                    {
                                                        table: {
                                                            title: '完成情况填写',
                                                            dataIndex: 'completion',
                                                            key: 'completion',
                                                            width: 150,
                                                            tooltip:9,
                                                            tdEdit: true,
                                                            fieldsConfig: {
                                                                type: "string",
                                                                disabled: (obj) => {
                                                                    if (obj.record.isAutomaticScoring === '1' || objs.initialValues.auditStatus === '2' || objs.initialValues.auditStatus === '3' || objs.initialValues.auditStatus === '4') {
                                                                        return true;
                                                                    } else {
                                                                        return false;
                                                                    }
                                                                },
                                                                placeholder: "请输入"
                                                            },
                                                            tdEditCb: (obj) => {
                                                                this.state.data.map((item) => {
                                                                    if (item.detailedId === obj.newRowData.detailedId) {
                                                                        item.completion = obj.newRowData.completion;
                                                                    }
                                                                    return item;
                                                                });
                                                            }
                                                        },
                                                        isInForm: false
                                                    },
                                                    {
                                                        table: {
                                                            title: '上级领导意见',
                                                            dataIndex: 'superiorOpinion',
                                                            key: 'superiorOpinion',
                                                            width: 150,
                                                            tooltip:9,
                                                        },
                                                        isInForm: false
                                                    }
                                                ]}
                                            />
                                        </div>
                                    );
                                }
                            }
                        }
                    ]}
                />
            </div>
        );
    }
}
export default Index;