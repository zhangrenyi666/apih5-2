import React, { Component } from "react";
import QnnTable from '../modules/qnn-table';
import QnnForm from '../modules/qnn-table/qnn-form';
import { message as Msg, Modal } from 'antd';
import moment from 'moment';
const confirm = Modal.confirm;
const config = {
    antd: {
        rowKey: 'scoreId',
        size: 'small',
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
            visibleRW: false,
            visibleZB: false,
            visibleXM: false,
            visibleZH: false,
            fieldCanDragFlag: true,
            rowData: null,
            dataRWKH: [],
            formConfigZH: [],
            formConfigFZ: [],
            formConfigYG: []
        }
    }
    render() {
        const { visibleRW, visibleZB, visibleXM, visibleZH, formConfigZH, formConfigFZ, formConfigYG, rowData, fieldCanDragFlag } = this.state;
        const { projectId, projectName } = this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany;
        const { jobType, ext2 } = this.props.loginAndLogoutInfo.loginInfo.userInfo;
        return (
            <div>
                <QnnTable
                    fetch={this.props.myFetch}
                    upload={this.props.myUpload}
                    headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                    {...config}
                    fetchConfig={{
                        apiName: 'getZjXmJxAssessmentUserScoreListByReviewer',
                        otherParams: {
                            auditeeProId: projectId,
                            noticeStatus: '1'
                        }
                    }}
                    wrappedComponentRef={(table) => {
                        this.rootTable = table;
                    }}
                    tabs={visibleRW ? [
                        {
                            field: "rwkh",
                            name: "qnnTable",
                            title: "任务考核",
                            content: {
                                fetchConfig: {
                                    apiName: 'getZjXmJxAssessmentUserScoreListByTaskReviewer',
                                    otherParams: {
                                        assessmentId: rowData.assessmentId
                                    }
                                },
                                isShowRowSelect: true,
                                antd: {
                                    rowKey: 'scoreId',
                                    size: 'small'
                                },
                                actionBtns:[
                                    {
                                        name: 'diy',
                                        type: 'primary',
                                        label: '撤销评分',
                                        disabled:(obj) => {
                                            if (obj.btnCallbackFn.getSelectedRows().length > 0) {
                                                return false;
                                            } else {
                                                return true;
                                            }
                                        },
                                        onClick: (obj) => {
                                            confirm({
                                                title: `您确定要撤销数据么?`,
                                                content: `取消撤销请点击取消按钮。`,
                                                okText: "确认",
                                                cancelText: "取消",
                                                onOk: () => {
                                                    this.props.myFetch('cancelZjXmJxTaskScoreDetailedByLeader', obj.selectedRows).then(({ success, message, data }) => {
                                                        if (success) {
                                                            obj.btnCallbackFn.clearSelectedRows();
                                                            obj.btnCallbackFn.refresh();
                                                            this.rootTable.refresh();
                                                        } else {
                                                            Msg.error(message);
                                                        }
                                                    });
                                                },
                                            });
                                        }
                                    }
                                ],
                                formConfig: [
                                    {
                                        isInTable: false,
                                        form: {
                                            field: 'scoreId',
                                            type: 'string',
                                            hide: true,
                                        },
                                    },
                                    {
                                        table: {
                                            title: '年月',
                                            dataIndex: 'yearMonth',
                                            key: 'yearMonth',
                                            width: 100,
                                            fixed: 'left',
                                            onClick: 'edit',
                                            willExecute: (obj) => {
                                                this.props.myFetch('getZjXmJxTaskScoreDetailedListByAuditee', { scoreId: obj.rowData.scoreId }).then(({ success, message, data }) => {
                                                    if (success) {
                                                        this.setState({
                                                            dataRWKH: data
                                                        })
                                                    } else {
                                                        Msg.error(message);
                                                    }
                                                });
                                            },
                                            btns: (obj) => {
                                                if (obj.rowData.auditStatus === '2') {
                                                    return [
                                                        {
                                                            field: 'rwkhcancel',
                                                            name: 'cancel',
                                                            type: 'dashed',
                                                            label: '取消',
                                                        },
                                                        {
                                                            field: 'rwkhappeal',
                                                            name: 'appeal',
                                                            type: 'primary',
                                                            label: '驳回',
                                                            onClick: (obj) => {
                                                                let setBtnsLoading = obj.btnCallbackFn.setBtnsLoading;
                                                                setBtnsLoading('add', 'rwkhappeal');
                                                                let body = {
                                                                    auditStatus: '5',
                                                                    scoreId: obj._formData.scoreId,
                                                                    scoreDetailedList: this.state.dataRWKH
                                                                };
                                                                this.props.myFetch('rejectOrSubmitZjXmJxTaskScoreDetailedByLeader', body).then(({ success, message, data }) => {
                                                                    if (success) {
                                                                        setBtnsLoading('remove', 'rwkhappeal');
                                                                        this.rootTable.refresh();
                                                                        obj.btnCallbackFn.closeDrawer(false);
                                                                        obj.btnCallbackFn.refresh();
                                                                    } else {
                                                                        setBtnsLoading('remove', 'rwkhappeal');
                                                                        Msg.error(message);
                                                                    }
                                                                });
                                                            }
                                                        },
                                                        {
                                                            field: 'rwkhaffirm',
                                                            name: 'affirm',
                                                            type: 'primary',
                                                            label: '提交',
                                                            onClick: (obj) => {
                                                                let setBtnsLoading = obj.btnCallbackFn.setBtnsLoading;
                                                                setBtnsLoading('add', 'rwkhaffirm');
                                                                for (let i = 0; i < this.state.dataRWKH.length; i++) {
                                                                    if (!this.state.dataRWKH[i].score) {
                                                                        setBtnsLoading('remove', 'rwkhaffirm');
                                                                        Msg.error('审核数据必须设置分值!');
                                                                        break;
                                                                    } else if (i === this.state.dataRWKH.length - 1) {
                                                                        let body = {
                                                                            auditStatus: '4',
                                                                            scoreId: obj._formData.scoreId,
                                                                            scoreDetailedList: this.state.dataRWKH
                                                                        };
                                                                        this.props.myFetch('rejectOrSubmitZjXmJxTaskScoreDetailedByLeader', body).then(({ success, message, data }) => {
                                                                            if (success) {
                                                                                setBtnsLoading('remove', 'rwkhaffirm');
                                                                                this.rootTable.refresh();
                                                                                obj.btnCallbackFn.closeDrawer(false);
                                                                                obj.btnCallbackFn.refresh();
                                                                            } else {
                                                                                setBtnsLoading('remove', 'rwkhaffirm');
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
                                                            field: 'rwkhcancel',
                                                            name: 'cancel',
                                                            type: 'dashed',
                                                            label: '取消',
                                                        }
                                                    ]
                                                }
                                            },
                                            render: (data) => {
                                                return moment(data).format('YYYY-MM');
                                            }
                                        },
                                        isInForm: false
                                    },
                                    {
                                        table: {
                                            title: '考核标题',
                                            dataIndex: 'assessmentTitle',
                                            key: 'assessmentTitle',
                                            width: 150,
                                            tooltip: 15
                                        },
                                        isInForm: false
                                    },
                                    {
                                        table: {
                                            title: '姓名',
                                            dataIndex: 'auditeeName',
                                            key: 'auditeeName',
                                            width: 100
                                        },
                                        form: {
                                            type: 'string',
                                            addDisabled: true,
                                            editDisabled: true,
                                            placeholder: "请选择",
                                            spanForm: 12
                                        }
                                    },
                                    {
                                        table: {
                                            title: '部门',
                                            dataIndex: 'auditeeDeptName',
                                            key: 'auditeeDeptName',
                                            width: 100
                                        },
                                        form: {
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
                                            label: '考核标题',
                                            field: 'assessmentTitle',
                                            type: 'string',
                                            addDisabled: true,
                                            editDisabled: true,
                                            placeholder: "请选择",
                                            formItemLayout: {
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
                                            title: '得分',
                                            dataIndex: 'score',
                                            key: 'score',
                                            width: 100
                                        },
                                        isInForm: false
                                    },
                                    {
                                        table: {
                                            title: '状态',
                                            dataIndex: 'auditStatus',
                                            key: 'auditStatus',
                                            width: 100,
                                            render: (data) => {
                                                if (data === '0') {
                                                    return '未发起';
                                                } else if (data === '1') {
                                                    return '被考核人暂存';
                                                } else if (data === '2') {
                                                    return '审核中';
                                                } else if (data === '3') {
                                                    return '审核者暂存';
                                                } else if (data === '4') {
                                                    return '已审核';
                                                } else if (data === '5') {
                                                    return '已驳回';
                                                } else {
                                                    return '--';
                                                }
                                            }
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
                                                            data={this.state.dataRWKH}
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
                                                                        width: 150,
                                                                        tooltip: 9,
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
                                                                        tooltip: 6,
                                                                    },
                                                                    isInForm: false
                                                                },
                                                                {
                                                                    table: {
                                                                        title: '评价计分标准',
                                                                        dataIndex: 'scoringStandard',
                                                                        key: 'scoringStandard',
                                                                        width: 150,
                                                                        tooltip: 9
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
                                                                        title: '完成情况自评',
                                                                        dataIndex: 'completion',
                                                                        key: 'completion',
                                                                        width: 150,
                                                                        tooltip: 9,
                                                                    },
                                                                    isInForm: false
                                                                },
                                                                {
                                                                    table: {
                                                                        title: '分数',
                                                                        dataIndex: 'score',
                                                                        key: 'score',
                                                                        width: 100,
                                                                        tdEdit: true,
                                                                        fieldsConfig: {
                                                                            type: "number",
                                                                            min: 0,
                                                                            max: 100,
                                                                            // disabled: (obj) => {
                                                                            //     if (obj.record.isAutomaticScoring === '1' || objs.initialValues.auditStatus !== '2') {
                                                                            //         return true;
                                                                            //     } else {
                                                                            //         return false;
                                                                            //     }
                                                                            // },
                                                                            disabled: (obj) => {
                                                                                if (objs.initialValues.auditStatus !== '2') {
                                                                                    return true;
                                                                                } else {
                                                                                    return false;
                                                                                }
                                                                            },
                                                                            placeholder: "请输入"
                                                                        },
                                                                        tdEditCb: (obj) => {
                                                                            this.state.dataRWKH.map((item) => {
                                                                                if (item.detailedId === obj.newRowData.detailedId) {
                                                                                    item.score = obj.newRowData.score;
                                                                                }
                                                                                return item;
                                                                            });
                                                                        }
                                                                    },
                                                                    isInForm: false
                                                                },
                                                                {
                                                                    table: {
                                                                        title: '领导审批意见',
                                                                        dataIndex: 'superiorOpinion',
                                                                        key: 'superiorOpinion',
                                                                        width: 150,
                                                                        tooltip: 9,
                                                                        tdEdit: true,
                                                                        fieldsConfig: {
                                                                            type: "string",
                                                                            // disabled: (obj) => {
                                                                            //     if (obj.record.isAutomaticScoring === '1' || objs.initialValues.auditStatus !== '2') {
                                                                            //         return true;
                                                                            //     } else {
                                                                            //         return false;
                                                                            //     }
                                                                            // },
                                                                            disabled: (obj) => {
                                                                                if (objs.initialValues.auditStatus !== '2') {
                                                                                    return true;
                                                                                } else {
                                                                                    return false;
                                                                                }
                                                                            },
                                                                            placeholder: "请输入"
                                                                        },
                                                                        tdEditCb: (obj) => {
                                                                            this.state.dataRWKH.map((item) => {
                                                                                if (item.detailedId === obj.newRowData.detailedId) {
                                                                                    item.superiorOpinion = obj.newRowData.superiorOpinion;
                                                                                }
                                                                                return item;
                                                                            });
                                                                        }
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
                                ]
                            }
                        }
                    ] : visibleZB ? [
                        {
                            field: "zbkh",
                            name: "qnnForm",
                            title: "周边考核",
                            content: {
                                formConfig: [
                                    {
                                        field: 'assessmentId',
                                        label: '主键',
                                        type: 'string',
                                        initialValue: rowData ?.assessmentId,
                                        hide: true
                                    },
                                    {
                                        field: 'yearMonth',
                                        label: '年月',
                                        type: 'month',
                                        disabled: true,
                                        initialValue: rowData ?.yearMonth,
                                        placeholder: '请输入'
                                    },
                                    {
                                        field: 'assessmentTitle',
                                        label: '考核标题',
                                        type: 'string',
                                        disabled: true,
                                        initialValue: rowData ?.assessmentTitle,
                                        placeholder: '请输入'
                                    },
                                    {
                                        type: "component",
                                        field: "component1",
                                        Component: obj => {
                                            return (
                                                <div style={{ background: '#f0f2f5', padding: '8px 12px' }}>
                                                    项目副职
                                                </div>
                                            );
                                        }
                                    },
                                    {
                                        type: "component",
                                        field: "component2",
                                        Component: obj => {
                                            return (
                                                <div style={{ padding: '10px' }}>
                                                    <QnnForm
                                                        match={this.props.match}
                                                        fetch={this.props.myFetch}
                                                        upload={this.props.myUpload} //必须返回一个promise
                                                        //内部自带发送ajax的组件发送请求时会自动加到head头里面的数据 （上传会用到）
                                                        headers={{
                                                            token: this.props.loginAndLogoutInfo.loginInfo.token
                                                        }}
                                                        fieldCanDrag={fieldCanDragFlag}
                                                        fieldDragCbs={{
                                                            onDragEnd: (obj) => {
                                                                this.setState({
                                                                    formConfigZH: obj.newFormConfig
                                                                })
                                                            }
                                                        }}
                                                        formConfig={formConfigZH}
                                                    />
                                                </div>
                                            );
                                        }
                                    },
                                    {
                                        type: "component",
                                        field: "component3",
                                        Component: obj => {
                                            return (
                                                <div style={{ background: '#f0f2f5', padding: '8px 12px' }}>
                                                    部门负责人
                                                </div>
                                            );
                                        }
                                    },
                                    {
                                        type: "component",
                                        field: "component4",
                                        Component: obj => {
                                            return (
                                                <div style={{ padding: '10px' }}>
                                                    <QnnForm
                                                        match={this.props.match}
                                                        fetch={this.props.myFetch}
                                                        upload={this.props.myUpload} //必须返回一个promise
                                                        //内部自带发送ajax的组件发送请求时会自动加到head头里面的数据 （上传会用到）
                                                        headers={{
                                                            token: this.props.loginAndLogoutInfo.loginInfo.token
                                                        }}
                                                        fieldCanDrag={fieldCanDragFlag}
                                                        fieldDragCbs={{
                                                            onDragEnd: (obj) => {
                                                                this.setState({
                                                                    formConfigFZ: obj.newFormConfig
                                                                })
                                                            }
                                                        }}
                                                        formConfig={formConfigFZ}
                                                    />
                                                </div>
                                            );
                                        }
                                    },
                                    {
                                        type: "component",
                                        field: "component5",
                                        Component: obj => {
                                            return (
                                                <div style={{ background: '#f0f2f5', padding: '8px 12px' }}>
                                                    普通员工
                                                </div>
                                            );
                                        }
                                    },
                                    {
                                        type: "component",
                                        field: "component6",
                                        Component: obj => {
                                            return (
                                                <div style={{ padding: '10px' }}>
                                                    <QnnForm
                                                        match={this.props.match}
                                                        fetch={this.props.myFetch}
                                                        upload={this.props.myUpload} //必须返回一个promise
                                                        //内部自带发送ajax的组件发送请求时会自动加到head头里面的数据 （上传会用到）
                                                        headers={{
                                                            token: this.props.loginAndLogoutInfo.loginInfo.token
                                                        }}
                                                        fieldCanDrag={fieldCanDragFlag}
                                                        fieldDragCbs={{
                                                            onDragEnd: (obj) => {
                                                                this.setState({
                                                                    formConfigYG: obj.newFormConfig
                                                                })
                                                            }
                                                        }}
                                                        formConfig={formConfigYG}
                                                    />
                                                </div>
                                            );
                                        }
                                    }
                                ]
                            }
                        }
                    ] : visibleXM ? [
                        {
                            field: "xmkh",
                            name: "qnnForm",
                            title: "项目正职",
                            content: {
                                formConfig: [
                                    {
                                        field: 'assessmentId',
                                        label: '主键',
                                        type: 'string',
                                        initialValue: rowData ?.assessmentId,
                                        hide: true
                                    },
                                    {
                                        field: 'yearMonth',
                                        label: '年月',
                                        type: 'month',
                                        disabled: true,
                                        initialValue: rowData ?.yearMonth,
                                        placeholder: '请输入'
                                    },
                                    {
                                        field: 'assessmentTitle',
                                        label: '考核标题',
                                        type: 'string',
                                        disabled: true,
                                        initialValue: rowData ?.assessmentTitle,
                                        placeholder: '请输入'
                                    },
                                    {
                                        type: "component",
                                        field: "component1",
                                        Component: obj => {
                                            return (
                                                <div style={{ background: '#f0f2f5', padding: '8px 12px' }}>
                                                    项目副职
                                                </div>
                                            );
                                        }
                                    },
                                    {
                                        type: "component",
                                        field: "component2",
                                        Component: obj => {
                                            return (
                                                <div style={{ padding: '10px' }}>
                                                    <QnnForm
                                                        match={this.props.match}
                                                        fetch={this.props.myFetch}
                                                        upload={this.props.myUpload} //必须返回一个promise
                                                        //内部自带发送ajax的组件发送请求时会自动加到head头里面的数据 （上传会用到）
                                                        headers={{
                                                            token: this.props.loginAndLogoutInfo.loginInfo.token
                                                        }}
                                                        fieldCanDrag={fieldCanDragFlag}
                                                        fieldDragCbs={{
                                                            onDragEnd: (obj) => {
                                                                this.setState({
                                                                    formConfigZH: obj.newFormConfig
                                                                })
                                                            }
                                                        }}
                                                        formConfig={formConfigZH}
                                                    />
                                                </div>
                                            );
                                        }
                                    },
                                    {
                                        type: "component",
                                        field: "component3",
                                        Component: obj => {
                                            return (
                                                <div style={{ background: '#f0f2f5', padding: '8px 12px' }}>
                                                    部门负责人
                                                </div>
                                            );
                                        }
                                    },
                                    {
                                        type: "component",
                                        field: "component4",
                                        Component: obj => {
                                            return (
                                                <div style={{ padding: '10px' }}>
                                                    <QnnForm
                                                        match={this.props.match}
                                                        fetch={this.props.myFetch}
                                                        upload={this.props.myUpload} //必须返回一个promise
                                                        //内部自带发送ajax的组件发送请求时会自动加到head头里面的数据 （上传会用到）
                                                        headers={{
                                                            token: this.props.loginAndLogoutInfo.loginInfo.token
                                                        }}
                                                        fieldCanDrag={fieldCanDragFlag}
                                                        fieldDragCbs={{
                                                            onDragEnd: (obj) => {
                                                                this.setState({
                                                                    formConfigFZ: obj.newFormConfig
                                                                })
                                                            }
                                                        }}
                                                        formConfig={formConfigFZ}
                                                    />
                                                </div>
                                            );
                                        }
                                    },
                                    {
                                        type: "component",
                                        field: "component5",
                                        Component: obj => {
                                            return (
                                                <div style={{ background: '#f0f2f5', padding: '8px 12px' }}>
                                                    普通员工
                                                </div>
                                            );
                                        }
                                    },
                                    {
                                        type: "component",
                                        field: "component6",
                                        Component: obj => {
                                            return (
                                                <div style={{ padding: '10px' }}>
                                                    <QnnForm
                                                        match={this.props.match}
                                                        fetch={this.props.myFetch}
                                                        upload={this.props.myUpload} //必须返回一个promise
                                                        //内部自带发送ajax的组件发送请求时会自动加到head头里面的数据 （上传会用到）
                                                        headers={{
                                                            token: this.props.loginAndLogoutInfo.loginInfo.token
                                                        }}
                                                        fieldCanDrag={fieldCanDragFlag}
                                                        fieldDragCbs={{
                                                            onDragEnd: (obj) => {
                                                                this.setState({
                                                                    formConfigYG: obj.newFormConfig
                                                                })
                                                            }
                                                        }}
                                                        formConfig={formConfigYG}
                                                    />
                                                </div>
                                            );
                                        }
                                    }
                                ]
                            }
                        }
                    ] : visibleZH ? [
                        {
                            field: "zhpj",
                            name: "qnnForm",
                            title: "综合评价",
                            content: {
                                formConfig: [
                                    {
                                        field: 'assessmentId',
                                        label: '主键',
                                        type: 'string',
                                        initialValue: rowData ?.assessmentId,
                                        hide: true
                                    },
                                    {
                                        field: 'yearMonth',
                                        label: '年月',
                                        type: 'month',
                                        disabled: true,
                                        initialValue: rowData ?.yearMonth,
                                        placeholder: '请输入'
                                    },
                                    {
                                        field: 'assessmentTitle',
                                        label: '考核标题',
                                        type: 'string',
                                        disabled: true,
                                        initialValue: rowData ?.assessmentTitle,
                                        placeholder: '请输入'
                                    },
                                    {
                                        type: "qnnTable",
                                        field: "qnnTableZZ",
                                        incToForm: true,
                                        qnnTableConfig: {
                                            antd: {
                                                size: "small",
                                                rowKey: "scoreId",
                                            },
                                            paginationConfig: false,
                                            pageSize: 9999,
                                            actionBtnsPosition: "top",
                                            isShowRowSelect: this.state.rowData.compositeComp === '评分' ? true : false,
                                            fetchConfig: {
                                                apiName: 'getZjXmJxAssessmentUserScoreListByCompositeReviewer',
                                                otherParams: {
                                                    assessmentId: this.state.rowData.assessmentId
                                                }
                                            },
                                            actionBtns: this.state.rowData.compositeComp === '评分' ? [
                                                {
                                                    name: 'addRow',
                                                    icon: 'plus',
                                                    type: 'primary',
                                                    label: '新增',
                                                },
                                                {
                                                    name: 'del',
                                                    icon: 'del',
                                                    type: 'danger',
                                                    label: '删除',
                                                }
                                            ] : [],
                                            formConfig: [
                                                {
                                                    table: {
                                                        title: '姓名',
                                                        dataIndex: 'userArray',
                                                        key: 'userArray',
                                                        width: 200,
                                                        tdEdit: true,
                                                    },
                                                    form: {
                                                        type: "treeSelect",
                                                        initialValue: [],
                                                        treeSelectOption: {
                                                            maxNumber: 1,
                                                            selectType: "2",
                                                            fetchConfig: {//配置后将会去请求下拉选项数据
                                                                apiName: 'getSysDepartmentUserAllTree'
                                                            },
                                                            edit: this.state.rowData.compositeComp === '评分' ? true : false,
                                                            search: true,
                                                            searchPlaceholder: '姓名、账号、电话',
                                                            // searchApi:'getSysDepartmentUserAllTree',  //搜索时调用的api  [string]
                                                            searchParamsKey: 'search',//搜索文字的K 默认是'searchText'   [string]
                                                            searchOtherParams: { pageSize: 999 }//搜索时的其他参数  [object]
                                                        },
                                                        placeholder: '请输入'
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '部门',
                                                        dataIndex: 'auditeeDeptName',
                                                        key: 'auditeeDeptName',
                                                        width: 200,
                                                        render: (data, rowData) => {
                                                            if (rowData && rowData.userArray && rowData.userArray[0] && rowData.userArray[0].departmentName) {
                                                                return rowData.userArray[0].departmentName;
                                                            } else if (data) {
                                                                return data;
                                                            } else {
                                                                return '';
                                                            }

                                                        }
                                                    },
                                                    isInForm: false
                                                },
                                                {
                                                    table: {
                                                        title: '扣分原因',
                                                        dataIndex: 'deductReasonArray',
                                                        key: 'deductReasonArray',
                                                        width: 200,
                                                        tdEdit: true
                                                    },
                                                    form: {
                                                        type: "select",
                                                        mode: 'tags',
                                                        disabled: this.state.rowData.compositeComp === '评分' ? false : true,
                                                        onChange: (value, obj) => {
                                                            setTimeout(() => {
                                                                this.rootTable.btnCallbackFn.qnnForm.getValues(false, (val) => {
                                                                    //最新版vs支持 可选连?.
                                                                    const newTableData = val.qnnTableZZ.map(item => {
                                                                        let _ded = item.deductReasonArray || [];
                                                                        let _dedLen = _ded.length;
                                                                        if (_dedLen) {
                                                                            return {
                                                                                ...item,
                                                                                deductReasonArray: [_ded[_dedLen - 1]]
                                                                            }
                                                                        } else {
                                                                            return {
                                                                                ...item,
                                                                                deductReasonArray: null
                                                                            }
                                                                        }
                                                                    });
                                                                    this.rootTable.btnCallbackFn.qnnForm.setValues({
                                                                        qnnTableZZ: newTableData
                                                                    })
                                                                })
                                                            }, 100)
                                                        },
                                                        optionData: [
                                                            {
                                                                label: "安全",
                                                                value: "安全"
                                                            },
                                                            {
                                                                label: "质量",
                                                                value: "质量"
                                                            },
                                                            {
                                                                label: "环保",
                                                                value: "环保"
                                                            }
                                                        ]
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '扣分',
                                                        dataIndex: 'score',
                                                        key: 'score',
                                                        width: 200,
                                                        tdEdit: true
                                                    },
                                                    form: {
                                                        type: "number",
                                                        disabled: this.state.rowData.compositeComp === '评分' ? false : true,
                                                        onBlur: () => {
                                                            setTimeout(() => {
                                                                this.rootTable.btnCallbackFn.qnnForm.getValues(false, (val) => {
                                                                    //最新版vs支持 可选连?.
                                                                    const newTableData = val.qnnTableZZ;
                                                                    this.rootTable.btnCallbackFn.qnnForm.setValues({
                                                                        qnnTableZZ: newTableData
                                                                    })
                                                                })
                                                            }, 100)
                                                        }
                                                    }
                                                }
                                            ]
                                        }
                                    }
                                ]
                            }
                        }
                    ] : []}
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
                            table: {
                                title: '年月',
                                dataIndex: 'yearMonth',
                                key: 'yearMonth',
                                filter: true,
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
                                width: 150,
                                tooltip: 25
                            },
                            form: {
                                type: 'string',
                                hide: true,
                                placeholder: '请输入'
                            }
                        },
                        {
                            table: {
                                title: '修改时间',
                                dataIndex: 'modifyTime',
                                key: 'modifyTime',
                                format: 'YYYY-MM-DD',
                                width: 100
                            },
                            isInForm: false
                        },
                        {
                            isInTable: ext2 === '2' ? false : true,
                            table: {
                                title: '任务考核(未评分/已评分)',
                                dataIndex: 'taskRatio',
                                key: 'taskRatio',
                                width: 120,
                                onClick: (obj) => {
                                    this.setState({
                                        visibleZB: false,
                                        visibleZH: false,
                                        visibleXM: false,
                                        rowData: obj.rowData,
                                        visibleRW: true
                                    }, () => {
                                        obj.btnCallbackFn.setDrawerBtns([]);
                                        obj.btnCallbackFn.closeDrawer(true);
                                    })
                                },
                                render: (data) => {
                                    return data;
                                }
                            },
                            isInForm: false
                        },
                        {
                            isInTable: jobType === '50103' || jobType === '50102' || jobType === '50101' ? false : true,
                            table: {
                                title: '周边考核',
                                dataIndex: 'peripheryComp',
                                key: 'peripheryComp',
                                width: 100,
                                onClick: (obj) => {
                                    this.props.myFetch('getZjXmJxPeripheryScoreDetailedListByReviewer', { assessmentId: obj.rowData.assessmentId }).then(({ success, message, data }) => {
                                        if (success) {
                                            this.setState({
                                                visibleRW: false,
                                                visibleZH: false,
                                                visibleXM: false,
                                                visibleZB: true,
                                                fieldCanDragFlag: data.buttonFlag === '0' ? false : true,
                                                rowData: obj.rowData,
                                                formConfigZH: data ?.deputyArray ?.length ? data.deputyArray.map((item) => {
                                                    return {
                                                        label: item.label,
                                                        field: item.value,
                                                        initialValue: item.score,
                                                        valuePid: item.valuePid,
                                                        type: 'number',
                                                        min: 0.1,
                                                        max: 100,
                                                        precision: 1,
                                                        placeholder: "分数",
                                                        span: 6,
                                                        disabled: data.buttonFlag === '0' ? true : false,
                                                        required: true,
                                                        onBlur: (e) => {
                                                            this.state.formConfigZH.map((items) => {
                                                                if (items.field === e.target ?.id ?.split('_')[1]) {
                                                                    if (e.target.value) {
                                                                        items.initialValue = Number(e.target.value);
                                                                    } else {
                                                                        items.initialValue = null;
                                                                    }
                                                                }
                                                                return items;
                                                            });
                                                            for (let m = 0; m < this.state.formConfigZH.length; m++) {
                                                                if (!this.state.formConfigZH[m].initialValue && this.state.formConfigZH[m].initialValue !== 0) {
                                                                    break;
                                                                } else if (m === this.state.formConfigZH.length - 1) {
                                                                    for (var j = 0; j < this.state.formConfigZH.length - 1; j++) {
                                                                        for (var i = 0; i < this.state.formConfigZH.length - 1; i++) {
                                                                            if (this.state.formConfigZH[i].initialValue < this.state.formConfigZH[i + 1].initialValue) {
                                                                                var temp = this.state.formConfigZH[i];
                                                                                this.state.formConfigZH[i] = this.state.formConfigZH[i + 1];
                                                                                this.state.formConfigZH[i + 1] = temp;
                                                                            }
                                                                        }
                                                                    }
                                                                    this.setState({
                                                                        formConfigZH: this.state.formConfigZH
                                                                    })
                                                                }
                                                            }
                                                        },
                                                        formItemLayout: {
                                                            labelCol: {
                                                                xs: { span: 12 },
                                                                sm: { span: 12 }
                                                            },
                                                            wrapperCol: {
                                                                xs: { span: 12 },
                                                                sm: { span: 12 }
                                                            }
                                                        }
                                                    }
                                                }) : [],
                                                formConfigFZ: data ?.leaderArray ?.length ? data.leaderArray.map((item) => {
                                                    return {
                                                        label: item.label,
                                                        field: item.value,
                                                        initialValue: item.score,
                                                        valuePid: item.valuePid,
                                                        type: 'number',
                                                        min: 0.1,
                                                        max: 100,
                                                        precision: 1,
                                                        placeholder: "分数",
                                                        span: 6,
                                                        disabled: data.buttonFlag === '0' ? true : false,
                                                        required: true,
                                                        onBlur: (e) => {
                                                            this.state.formConfigFZ.map((items) => {
                                                                if (items.field === e.target ?.id ?.split('_')[1]) {
                                                                    if (e.target.value) {
                                                                        items.initialValue = Number(e.target.value);
                                                                    } else {
                                                                        items.initialValue = null;
                                                                    }
                                                                }
                                                                return items;
                                                            });
                                                            for (let m = 0; m < this.state.formConfigFZ.length; m++) {
                                                                if (!this.state.formConfigFZ[m].initialValue && this.state.formConfigFZ[m].initialValue !== 0) {
                                                                    break;
                                                                } else if (m === this.state.formConfigFZ.length - 1) {
                                                                    for (var j = 0; j < this.state.formConfigFZ.length - 1; j++) {
                                                                        for (var i = 0; i < this.state.formConfigFZ.length - 1; i++) {
                                                                            if (this.state.formConfigFZ[i].initialValue < this.state.formConfigFZ[i + 1].initialValue) {
                                                                                var temp = this.state.formConfigFZ[i];
                                                                                this.state.formConfigFZ[i] = this.state.formConfigFZ[i + 1];
                                                                                this.state.formConfigFZ[i + 1] = temp;
                                                                            }
                                                                        }
                                                                    }
                                                                    this.setState({
                                                                        formConfigFZ: this.state.formConfigFZ
                                                                    })
                                                                }
                                                            }
                                                        },
                                                        formItemLayout: {
                                                            labelCol: {
                                                                xs: { span: 12 },
                                                                sm: { span: 12 }
                                                            },
                                                            wrapperCol: {
                                                                xs: { span: 12 },
                                                                sm: { span: 12 }
                                                            }
                                                        }
                                                    }
                                                }) : [],
                                                formConfigYG: data ?.employeeArray ?.length ? data.employeeArray.map((item) => {
                                                    return {
                                                        label: item.label,
                                                        field: item.value,
                                                        initialValue: item.score,
                                                        valuePid: item.valuePid,
                                                        type: 'number',
                                                        min: 0.1,
                                                        max: 100,
                                                        precision: 1,
                                                        placeholder: "分数",
                                                        span: 6,
                                                        disabled: data.buttonFlag === '0' ? true : false,
                                                        required: true,
                                                        onBlur: (e) => {
                                                            this.state.formConfigYG.map((items) => {
                                                                if (items.field === e.target ?.id ?.split('_')[1]) {
                                                                    if (e.target.value) {
                                                                        items.initialValue = Number(e.target.value);
                                                                    } else {
                                                                        items.initialValue = null;
                                                                    }
                                                                }
                                                                return items;
                                                            });
                                                            for (let m = 0; m < this.state.formConfigYG.length; m++) {
                                                                if (!this.state.formConfigYG[m].initialValue && this.state.formConfigYG[m].initialValue !== 0) {
                                                                    break;
                                                                } else if (m === this.state.formConfigYG.length - 1) {
                                                                    for (var j = 0; j < this.state.formConfigYG.length - 1; j++) {
                                                                        for (var i = 0; i < this.state.formConfigYG.length - 1; i++) {
                                                                            if (this.state.formConfigYG[i].initialValue < this.state.formConfigYG[i + 1].initialValue) {
                                                                                var temp = this.state.formConfigYG[i];
                                                                                this.state.formConfigYG[i] = this.state.formConfigYG[i + 1];
                                                                                this.state.formConfigYG[i + 1] = temp;
                                                                            }
                                                                        }
                                                                    }
                                                                    this.setState({
                                                                        formConfigYG: this.state.formConfigYG
                                                                    })
                                                                }
                                                            }
                                                        },
                                                        formItemLayout: {
                                                            labelCol: {
                                                                xs: { span: 12 },
                                                                sm: { span: 12 }
                                                            },
                                                            wrapperCol: {
                                                                xs: { span: 12 },
                                                                sm: { span: 12 }
                                                            }
                                                        }
                                                    }
                                                }) : []
                                            }, () => {
                                                if (data.buttonFlag === '0') {
                                                    obj.btnCallbackFn.setDrawerBtns([
                                                        {
                                                            field: 'zbkhCancel',
                                                            name: "cancel",
                                                            type: "dashed",
                                                            label: "取消"
                                                        }
                                                    ])
                                                } else {
                                                    obj.btnCallbackFn.setDrawerBtns([
                                                        {
                                                            field: 'zbkhCancel',
                                                            name: "cancel",
                                                            type: "dashed",
                                                            label: "取消"
                                                        },
                                                        {
                                                            field: 'zbkhZdjs',
                                                            name: 'zdzs',//内置add del
                                                            type: 'primary',//类型  默认 primary
                                                            label: '自动计算',//提交数据并且关闭右边抽屉
                                                            onClick: () => {
                                                                for (let i = 0; i < this.state.formConfigZH.length; i++) {
                                                                    if (this.state.formConfigZH[i] ?.initialValue) {
                                                                        for (let j = 0; j < this.state.formConfigZH.length; j++) {
                                                                            if (Number(this.state.formConfigZH[i].initialValue) + ((i - j) / 10) > 0 && Number(this.state.formConfigZH[i].initialValue) + ((i - j) / 10) <= 100) {
                                                                                this.state.formConfigZH[j].initialValue = Number(this.state.formConfigZH[i].initialValue) + ((i - j) / 10);
                                                                            } else if (Number(this.state.formConfigZH[i].initialValue) + ((i - j) / 10) > 100) {
                                                                                this.state.formConfigZH[j].initialValue = 100;
                                                                            } else {
                                                                                this.state.formConfigZH[j].initialValue = 0.1;
                                                                            }
                                                                        }
                                                                        break;
                                                                    }
                                                                }
                                                                for (let i = 0; i < this.state.formConfigFZ.length; i++) {
                                                                    if (this.state.formConfigFZ[i] ?.initialValue) {
                                                                        for (let j = 0; j < this.state.formConfigFZ.length; j++) {
                                                                            if (Number(this.state.formConfigFZ[i].initialValue) + ((i - j) / 10) > 0 && Number(this.state.formConfigFZ[i].initialValue) + ((i - j) / 10) <= 100) {
                                                                                this.state.formConfigFZ[j].initialValue = Number(this.state.formConfigFZ[i].initialValue) + ((i - j) / 10);
                                                                            } else if (Number(this.state.formConfigFZ[i].initialValue) + ((i - j) / 10) > 100) {
                                                                                this.state.formConfigFZ[j].initialValue = 100;
                                                                            } else {
                                                                                this.state.formConfigFZ[j].initialValue = 0.1;
                                                                            }
                                                                        }
                                                                        break;
                                                                    }
                                                                }
                                                                for (let i = 0; i < this.state.formConfigYG.length; i++) {
                                                                    if (this.state.formConfigYG[i] ?.initialValue) {
                                                                        for (let j = 0; j < this.state.formConfigYG.length; j++) {
                                                                            if (Number(this.state.formConfigYG[i].initialValue) + ((i - j) / 10) > 0 && Number(this.state.formConfigYG[i].initialValue) + ((i - j) / 10) <= 100) {
                                                                                this.state.formConfigYG[j].initialValue = Number(this.state.formConfigYG[i].initialValue) + ((i - j) / 10);
                                                                            } else if (Number(this.state.formConfigYG[i].initialValue) + ((i - j) / 10) > 100) {
                                                                                this.state.formConfigYG[j].initialValue = 100;
                                                                            } else {
                                                                                this.state.formConfigYG[j].initialValue = 0.1;
                                                                            }
                                                                        }
                                                                        break;
                                                                    }
                                                                }
                                                                this.setState({
                                                                    formConfigZH: this.state.formConfigZH,
                                                                    formConfigFZ: this.state.formConfigFZ,
                                                                    formConfigYG: this.state.formConfigYG
                                                                })
                                                            }
                                                        },
                                                        {
                                                            field: 'zbkhzc',
                                                            name: 'zbkhzc',//内置add del
                                                            type: 'primary',//类型  默认 primary
                                                            label: '暂存',//提交数据并且关闭右边抽屉
                                                            onClick: (objzc) => {
                                                                let setBtnsLoading = objzc.btnCallbackFn.setBtnsLoading;
                                                                setBtnsLoading('add', 'zbkhzc');
                                                                let datazczh = [];
                                                                let datazcfz = [];
                                                                let datazcyg = [];
                                                                this.state.formConfigZH.map((item) => {
                                                                    datazczh.push({
                                                                        label: item.label,
                                                                        value: item.field,
                                                                        valuePid: item.valuePid,
                                                                        score: item.initialValue
                                                                    })
                                                                    return item;
                                                                })
                                                                this.state.formConfigFZ.map((item) => {
                                                                    datazcfz.push({
                                                                        label: item.label,
                                                                        value: item.field,
                                                                        valuePid: item.valuePid,
                                                                        score: item.initialValue
                                                                    })
                                                                    return item;
                                                                })
                                                                this.state.formConfigYG.map((item) => {
                                                                    datazcyg.push({
                                                                        label: item.label,
                                                                        value: item.field,
                                                                        valuePid: item.valuePid,
                                                                        score: item.initialValue
                                                                    })
                                                                    return item;
                                                                })
                                                                let body = {
                                                                    assessmentId: obj.rowData.assessmentId,
                                                                    auditStatus: '3',
                                                                    deputyArray: datazczh,
                                                                    leaderArray: datazcfz,
                                                                    employeeArray: datazcyg
                                                                };
                                                                this.props.myFetch('tempOrSubmitZjXmJxPeripheryScoreDetailedList', body).then(({ success, message, data }) => {
                                                                    if (success) {
                                                                        setBtnsLoading('remove', 'zbkhzc');
                                                                        objzc.btnCallbackFn.closeDrawer(false);
                                                                        objzc.btnCallbackFn.refresh();
                                                                    } else {
                                                                        setBtnsLoading('remove', 'zbkhzc');
                                                                        Msg.error(message);
                                                                    }
                                                                });
                                                            }
                                                        },
                                                        {
                                                            field: 'zbkhSubmit',
                                                            name: 'zbkhSubmit',//内置add del
                                                            type: 'primary',//类型  默认 primary
                                                            label: '提交',//提交数据并且关闭右边抽屉 
                                                            onClick: (objtj) => {
                                                                let setBtnsLoading = objtj.btnCallbackFn.setBtnsLoading;
                                                                setBtnsLoading('add', 'zbkhSubmit');
                                                                let datatjzh = [];
                                                                let datatjfz = [];
                                                                let datatjyg = [];
                                                                this.state.formConfigZH.map((item) => {
                                                                    datatjzh.push({
                                                                        label: item.label,
                                                                        value: item.field,
                                                                        valuePid: item.valuePid,
                                                                        score: item.initialValue
                                                                    })
                                                                    return item;
                                                                })
                                                                this.state.formConfigFZ.map((item) => {
                                                                    datatjfz.push({
                                                                        label: item.label,
                                                                        value: item.field,
                                                                        valuePid: item.valuePid,
                                                                        score: item.initialValue
                                                                    })
                                                                    return item;
                                                                })
                                                                this.state.formConfigYG.map((item) => {
                                                                    datatjyg.push({
                                                                        label: item.label,
                                                                        value: item.field,
                                                                        valuePid: item.valuePid,
                                                                        score: item.initialValue
                                                                    })
                                                                    return item;
                                                                })
                                                                if (datatjzh.length) {
                                                                    for (let i = 0; i < datatjzh.length; i++) {
                                                                        if (!datatjzh[i].score) {
                                                                            Msg.error('项目副职分值不能为空!');
                                                                            setBtnsLoading('remove', 'zbkhSubmit');
                                                                            return;
                                                                        }
                                                                    }
                                                                }
                                                                if (datatjfz.length) {
                                                                    for (let i = 0; i < datatjfz.length; i++) {
                                                                        if (!datatjfz[i].score) {
                                                                            Msg.error('部门负责人分值不能为空!');
                                                                            setBtnsLoading('remove', 'zbkhSubmit');
                                                                            return;
                                                                        }
                                                                    }
                                                                }
                                                                if (datatjyg.length) {
                                                                    for (let i = 0; i < datatjyg.length; i++) {
                                                                        if (!datatjyg[i].score) {
                                                                            Msg.error('普通员工分值不能为空!');
                                                                            setBtnsLoading('remove', 'zbkhSubmit');
                                                                            return;
                                                                        }
                                                                    }
                                                                }
                                                                let body = {
                                                                    assessmentId: obj.rowData.assessmentId,
                                                                    auditStatus: '4',
                                                                    deputyArray: datatjzh,
                                                                    leaderArray: datatjfz,
                                                                    employeeArray: datatjyg
                                                                };
                                                                this.props.myFetch('tempOrSubmitZjXmJxPeripheryScoreDetailedList', body).then(({ success, message, data }) => {
                                                                    if (success) {
                                                                        setBtnsLoading('remove', 'zbkhSubmit');
                                                                        objtj.btnCallbackFn.closeDrawer(false);
                                                                        objtj.btnCallbackFn.refresh();
                                                                    } else {
                                                                        setBtnsLoading('remove', 'zbkhSubmit');
                                                                        Msg.error(message);
                                                                    }
                                                                });
                                                            }
                                                        }
                                                    ])
                                                }
                                                obj.btnCallbackFn.closeDrawer(true);
                                            })
                                        } else {
                                            Msg.error(message);
                                        }
                                    });
                                },
                                render: (data) => {
                                    return data;
                                }
                            },
                            isInForm: false
                        },
                        {
                            isInTable: jobType === '50103' || jobType === '50102' || jobType === '50101' ? true : false,
                            table: {
                                title: '项目正职',
                                dataIndex: 'principalComp',
                                key: 'principalComp',
                                width: 100,
                                onClick: (obj) => {
                                    this.props.myFetch('getZjXmJxPrincipalScoreDetailedListByReviewer', { assessmentId: obj.rowData.assessmentId }).then(({ success, message, data }) => {
                                        if (success) {
                                            this.setState({
                                                visibleRW: false,
                                                visibleZH: false,
                                                visibleZB: false,
                                                rowData: obj.rowData,
                                                fieldCanDragFlag: data.buttonFlag === '0' ? false : true,
                                                visibleXM: true,
                                                formConfigZH: data ?.deputyArray ?.length ? data.deputyArray.map((item) => {
                                                    return {
                                                        label: item.label,
                                                        field: item.value,
                                                        initialValue: item.score,
                                                        valuePid: item.valuePid,
                                                        type: 'number',
                                                        min: 0.1,
                                                        max: 100,
                                                        precision: 1,
                                                        placeholder: "分数",
                                                        span: 6,
                                                        disabled: data.buttonFlag === '0' ? true : false,
                                                        required: true,
                                                        onBlur: (e) => {
                                                            this.state.formConfigZH.map((items) => {
                                                                if (items.field === e.target ?.id ?.split('_')[1]) {
                                                                    if (e.target.value) {
                                                                        items.initialValue = Number(e.target.value);
                                                                    } else {
                                                                        items.initialValue = null;
                                                                    }
                                                                }
                                                                return items;
                                                            });
                                                            for (let m = 0; m < this.state.formConfigZH.length; m++) {
                                                                if (!this.state.formConfigZH[m].initialValue && this.state.formConfigZH[m].initialValue !== 0) {
                                                                    break;
                                                                } else if (m === this.state.formConfigZH.length - 1) {
                                                                    for (var j = 0; j < this.state.formConfigZH.length - 1; j++) {
                                                                        for (var i = 0; i < this.state.formConfigZH.length - 1; i++) {
                                                                            if (this.state.formConfigZH[i].initialValue < this.state.formConfigZH[i + 1].initialValue) {
                                                                                var temp = this.state.formConfigZH[i];
                                                                                this.state.formConfigZH[i] = this.state.formConfigZH[i + 1];
                                                                                this.state.formConfigZH[i + 1] = temp;
                                                                            }
                                                                        }
                                                                    }
                                                                    this.setState({
                                                                        formConfigZH: this.state.formConfigZH
                                                                    })
                                                                }
                                                            }
                                                        },
                                                        formItemLayout: {
                                                            labelCol: {
                                                                xs: { span: 12 },
                                                                sm: { span: 12 }
                                                            },
                                                            wrapperCol: {
                                                                xs: { span: 12 },
                                                                sm: { span: 12 }
                                                            }
                                                        }
                                                    }
                                                }) : [],
                                                formConfigFZ: data ?.leaderArray ?.length ? data.leaderArray.map((item) => {
                                                    return {
                                                        label: item.label,
                                                        field: item.value,
                                                        initialValue: item.score,
                                                        valuePid: item.valuePid,
                                                        type: 'number',
                                                        min: 0.1,
                                                        max: 100,
                                                        precision: 1,
                                                        placeholder: "分数",
                                                        span: 6,
                                                        disabled: data.buttonFlag === '0' ? true : false,
                                                        required: true,
                                                        onBlur: (e) => {
                                                            this.state.formConfigFZ.map((items) => {
                                                                if (items.field === e.target ?.id ?.split('_')[1]) {
                                                                    if (e.target.value) {
                                                                        items.initialValue = Number(e.target.value);
                                                                    } else {
                                                                        items.initialValue = null;
                                                                    }
                                                                }
                                                                return items;
                                                            });
                                                            for (let m = 0; m < this.state.formConfigFZ.length; m++) {
                                                                if (!this.state.formConfigFZ[m].initialValue && this.state.formConfigFZ[m].initialValue !== 0) {
                                                                    break;
                                                                } else if (m === this.state.formConfigFZ.length - 1) {
                                                                    for (var j = 0; j < this.state.formConfigFZ.length - 1; j++) {
                                                                        for (var i = 0; i < this.state.formConfigFZ.length - 1; i++) {
                                                                            if (this.state.formConfigFZ[i].initialValue < this.state.formConfigFZ[i + 1].initialValue) {
                                                                                var temp = this.state.formConfigFZ[i];
                                                                                this.state.formConfigFZ[i] = this.state.formConfigFZ[i + 1];
                                                                                this.state.formConfigFZ[i + 1] = temp;
                                                                            }
                                                                        }
                                                                    }
                                                                    this.setState({
                                                                        formConfigFZ: this.state.formConfigFZ
                                                                    })
                                                                }
                                                            }
                                                        },
                                                        formItemLayout: {
                                                            labelCol: {
                                                                xs: { span: 12 },
                                                                sm: { span: 12 }
                                                            },
                                                            wrapperCol: {
                                                                xs: { span: 12 },
                                                                sm: { span: 12 }
                                                            }
                                                        }
                                                    }
                                                }) : [],
                                                formConfigYG: data ?.employeeArray ?.length ? data.employeeArray.map((item) => {
                                                    return {
                                                        label: item.label,
                                                        field: item.value,
                                                        initialValue: item.score,
                                                        valuePid: item.valuePid,
                                                        type: 'number',
                                                        min: 0.1,
                                                        max: 100,
                                                        precision: 1,
                                                        placeholder: "分数",
                                                        span: 6,
                                                        disabled: data.buttonFlag === '0' ? true : false,
                                                        required: true,
                                                        onBlur: (e) => {
                                                            this.state.formConfigYG.map((items) => {
                                                                if (items.field === e.target ?.id ?.split('_')[1]) {
                                                                    if (e.target.value) {
                                                                        items.initialValue = Number(e.target.value);
                                                                    } else {
                                                                        items.initialValue = null;
                                                                    }
                                                                }
                                                                return items;
                                                            });
                                                            for (let m = 0; m < this.state.formConfigYG.length; m++) {
                                                                if (!this.state.formConfigYG[m].initialValue && this.state.formConfigYG[m].initialValue !== 0) {
                                                                    break;
                                                                } else if (m === this.state.formConfigYG.length - 1) {
                                                                    for (var j = 0; j < this.state.formConfigYG.length - 1; j++) {
                                                                        for (var i = 0; i < this.state.formConfigYG.length - 1; i++) {
                                                                            if (this.state.formConfigYG[i].initialValue < this.state.formConfigYG[i + 1].initialValue) {
                                                                                var temp = this.state.formConfigYG[i];
                                                                                this.state.formConfigYG[i] = this.state.formConfigYG[i + 1];
                                                                                this.state.formConfigYG[i + 1] = temp;
                                                                            }
                                                                        }
                                                                    }
                                                                    this.setState({
                                                                        formConfigYG: this.state.formConfigYG
                                                                    })
                                                                }
                                                            }
                                                        },
                                                        formItemLayout: {
                                                            labelCol: {
                                                                xs: { span: 12 },
                                                                sm: { span: 12 }
                                                            },
                                                            wrapperCol: {
                                                                xs: { span: 12 },
                                                                sm: { span: 12 }
                                                            }
                                                        }
                                                    }
                                                }) : []
                                            }, () => {
                                                if (data.buttonFlag === '0') {
                                                    obj.btnCallbackFn.setDrawerBtns([
                                                        {
                                                            field: 'xmkhCancel',
                                                            name: "cancel",
                                                            type: "dashed",
                                                            label: "取消"
                                                        }
                                                    ])
                                                } else {
                                                    obj.btnCallbackFn.setDrawerBtns([
                                                        {
                                                            field: 'xmkhCancel',
                                                            name: "cancel",
                                                            type: "dashed",
                                                            label: "取消"
                                                        },
                                                        {
                                                            field: 'xmkhZdjs',
                                                            name: 'zdzs',//内置add del
                                                            type: 'primary',//类型  默认 primary
                                                            label: '自动计算',//提交数据并且关闭右边抽屉 
                                                            onClick: () => {
                                                                for (let i = 0; i < this.state.formConfigZH.length; i++) {
                                                                    if (this.state.formConfigZH[i] ?.initialValue) {
                                                                        for (let j = 0; j < this.state.formConfigZH.length; j++) {
                                                                            if (Number(this.state.formConfigZH[i].initialValue) + ((i - j) / 10) > 0 && Number(this.state.formConfigZH[i].initialValue) + ((i - j) / 10) <= 100) {
                                                                                this.state.formConfigZH[j].initialValue = Number(this.state.formConfigZH[i].initialValue) + ((i - j) / 10);
                                                                            } else if (Number(this.state.formConfigZH[i].initialValue) + ((i - j) / 10) > 100) {
                                                                                this.state.formConfigZH[j].initialValue = 100;
                                                                            } else {
                                                                                this.state.formConfigZH[j].initialValue = 0.1;
                                                                            }
                                                                        }
                                                                        break;
                                                                    }
                                                                }
                                                                for (let i = 0; i < this.state.formConfigFZ.length; i++) {
                                                                    if (this.state.formConfigFZ[i] ?.initialValue) {
                                                                        for (let j = 0; j < this.state.formConfigFZ.length; j++) {
                                                                            if (Number(this.state.formConfigFZ[i].initialValue) + ((i - j) / 10) > 0 && Number(this.state.formConfigFZ[i].initialValue) + ((i - j) / 10) <= 100) {
                                                                                this.state.formConfigFZ[j].initialValue = Number(this.state.formConfigFZ[i].initialValue) + ((i - j) / 10);
                                                                            } else if (Number(this.state.formConfigFZ[i].initialValue) + ((i - j) / 10) > 100) {
                                                                                this.state.formConfigFZ[j].initialValue = 100;
                                                                            } else {
                                                                                this.state.formConfigFZ[j].initialValue = 0.1;
                                                                            }
                                                                        }
                                                                        break;
                                                                    }
                                                                }
                                                                for (let i = 0; i < this.state.formConfigYG.length; i++) {
                                                                    if (this.state.formConfigYG[i] ?.initialValue) {
                                                                        for (let j = 0; j < this.state.formConfigYG.length; j++) {
                                                                            if (Number(this.state.formConfigYG[i].initialValue) + ((i - j) / 10) > 0 && Number(this.state.formConfigYG[i].initialValue) + ((i - j) / 10) <= 100) {
                                                                                this.state.formConfigYG[j].initialValue = Number(this.state.formConfigYG[i].initialValue) + ((i - j) / 10);
                                                                            } else if (Number(this.state.formConfigYG[i].initialValue) + ((i - j) / 10) > 100) {
                                                                                this.state.formConfigYG[j].initialValue = 100;
                                                                            } else {
                                                                                this.state.formConfigYG[j].initialValue = 0.1;
                                                                            }
                                                                        }
                                                                        break;
                                                                    }
                                                                }
                                                                this.setState({
                                                                    formConfigZH: this.state.formConfigZH,
                                                                    formConfigFZ: this.state.formConfigFZ,
                                                                    formConfigYG: this.state.formConfigYG
                                                                })
                                                            }
                                                        },
                                                        {
                                                            field: 'xmkhzc',
                                                            name: 'xmkhzc',//内置add del
                                                            type: 'primary',//类型  默认 primary
                                                            label: '暂存',//提交数据并且关闭右边抽屉 
                                                            onClick: (objzc) => {
                                                                let setBtnsLoading = objzc.btnCallbackFn.setBtnsLoading;
                                                                setBtnsLoading('add', 'xmkhzc');
                                                                let datazczh = [];
                                                                let datazcfz = [];
                                                                let datazcyg = [];
                                                                this.state.formConfigZH.map((item) => {
                                                                    datazczh.push({
                                                                        label: item.label,
                                                                        value: item.field,
                                                                        valuePid: item.valuePid,
                                                                        score: item.initialValue
                                                                    })
                                                                    return item;
                                                                })
                                                                this.state.formConfigFZ.map((item) => {
                                                                    datazcfz.push({
                                                                        label: item.label,
                                                                        value: item.field,
                                                                        valuePid: item.valuePid,
                                                                        score: item.initialValue
                                                                    })
                                                                    return item;
                                                                })
                                                                this.state.formConfigYG.map((item) => {
                                                                    datazcyg.push({
                                                                        label: item.label,
                                                                        value: item.field,
                                                                        valuePid: item.valuePid,
                                                                        score: item.initialValue
                                                                    })
                                                                    return item;
                                                                })
                                                                let body = {
                                                                    assessmentId: obj.rowData.assessmentId,
                                                                    auditStatus: '3',
                                                                    deputyArray: datazczh,
                                                                    leaderArray: datazcfz,
                                                                    employeeArray: datazcyg
                                                                };
                                                                this.props.myFetch('tempOrSubmitZjXmJxPrincipalScoreDetailedList', body).then(({ success, message, data }) => {
                                                                    if (success) {
                                                                        setBtnsLoading('remove', 'xmkhzc');
                                                                        objzc.btnCallbackFn.closeDrawer(false);
                                                                        objzc.btnCallbackFn.refresh();
                                                                    } else {
                                                                        setBtnsLoading('remove', 'xmkhzc');
                                                                        Msg.error(message);
                                                                    }
                                                                });
                                                            }
                                                        },
                                                        {
                                                            field: 'xmkhSubmit',
                                                            name: 'xmkhSubmit',//内置add del
                                                            type: 'primary',//类型  默认 primary
                                                            label: '提交',//提交数据并且关闭右边抽屉 
                                                            onClick: (objtj) => {
                                                                let setBtnsLoading = objtj.btnCallbackFn.setBtnsLoading;
                                                                setBtnsLoading('add', 'xmkhSubmit');
                                                                let datatjzh = [];
                                                                let datatjfz = [];
                                                                let datatjyg = [];
                                                                this.state.formConfigZH.map((item) => {
                                                                    datatjzh.push({
                                                                        label: item.label,
                                                                        value: item.field,
                                                                        valuePid: item.valuePid,
                                                                        score: item.initialValue
                                                                    })
                                                                    return item;
                                                                })
                                                                this.state.formConfigFZ.map((item) => {
                                                                    datatjfz.push({
                                                                        label: item.label,
                                                                        value: item.field,
                                                                        valuePid: item.valuePid,
                                                                        score: item.initialValue
                                                                    })
                                                                    return item;
                                                                })
                                                                this.state.formConfigYG.map((item) => {
                                                                    datatjyg.push({
                                                                        label: item.label,
                                                                        value: item.field,
                                                                        valuePid: item.valuePid,
                                                                        score: item.initialValue
                                                                    })
                                                                    return item;
                                                                })
                                                                if (datatjzh.length) {
                                                                    for (let i = 0; i < datatjzh.length; i++) {
                                                                        if (!datatjzh[i].score) {
                                                                            Msg.error('项目副职分值不能为空!');
                                                                            setBtnsLoading('remove', 'xmkhSubmit');
                                                                            return;
                                                                        }
                                                                    }
                                                                }
                                                                if (datatjfz.length) {
                                                                    for (let i = 0; i < datatjfz.length; i++) {
                                                                        if (!datatjfz[i].score) {
                                                                            Msg.error('部门负责人分值不能为空!');
                                                                            setBtnsLoading('remove', 'xmkhSubmit');
                                                                            return;
                                                                        }
                                                                    }
                                                                }
                                                                if (datatjyg.length) {
                                                                    for (let i = 0; i < datatjyg.length; i++) {
                                                                        if (!datatjyg[i].score) {
                                                                            Msg.error('普通员工分值不能为空!');
                                                                            setBtnsLoading('remove', 'xmkhSubmit');
                                                                            return;
                                                                        }
                                                                    }
                                                                }
                                                                let body = {
                                                                    assessmentId: obj.rowData.assessmentId,
                                                                    auditStatus: '4',
                                                                    deputyArray: datatjzh,
                                                                    leaderArray: datatjfz,
                                                                    employeeArray: datatjyg
                                                                };
                                                                this.props.myFetch('tempOrSubmitZjXmJxPrincipalScoreDetailedList', body).then(({ success, message, data }) => {
                                                                    if (success) {
                                                                        setBtnsLoading('remove', 'xmkhSubmit');
                                                                        objtj.btnCallbackFn.closeDrawer(false);
                                                                        objtj.btnCallbackFn.refresh();
                                                                    } else {
                                                                        setBtnsLoading('remove', 'xmkhSubmit');
                                                                        Msg.error(message);
                                                                    }
                                                                });
                                                            }
                                                        }
                                                    ])
                                                }
                                                obj.btnCallbackFn.closeDrawer(true);
                                            })
                                        } else {
                                            Msg.error(message);
                                        }
                                    });
                                },
                                render: (data) => {
                                    return data;
                                }
                            },
                            isInForm: false
                        },
                        {
                            isInTable: jobType === '50103' || jobType === '50102' ? true : false,
                            table: {
                                title: '综合评价',
                                dataIndex: 'compositeComp',
                                key: 'compositeComp',
                                width: 100,
                                onClick: (obj) => {
                                    this.setState({
                                        visibleRW: false,
                                        visibleZB: false,
                                        visibleXM: false,
                                        rowData: obj.rowData,
                                        visibleZH: true
                                    }, () => {
                                        if (obj.rowData.compositeComp !== '评分') {
                                            obj.btnCallbackFn.setDrawerBtns([
                                                {
                                                    field: 'zhpjCancel',
                                                    name: "cancel",
                                                    type: "dashed",
                                                    label: "取消"
                                                }
                                            ])
                                        } else {
                                            obj.btnCallbackFn.setDrawerBtns([
                                                {
                                                    field: 'zhpjCancel',
                                                    name: "cancel",
                                                    type: "dashed",
                                                    label: "取消"
                                                },
                                                {
                                                    field: 'zhpjzc',
                                                    name: 'zhpjzc',//内置add del
                                                    type: 'primary',//类型  默认 primary
                                                    label: '暂存',//提交数据并且关闭右边抽屉
                                                    onClick: (objzh) => {
                                                        let setBtnsLoading = objzh.btnCallbackFn.setBtnsLoading;
                                                        setBtnsLoading('add', 'zhpjzc');
                                                        let body = [];
                                                        if (objzh._formData.qnnTableZZ.length) {
                                                            for (let i = 0; i < objzh._formData.qnnTableZZ.length; i++) {
                                                                if (!objzh._formData.qnnTableZZ[i].userArray || !objzh._formData.qnnTableZZ[i].score || !objzh._formData.qnnTableZZ[i].deductReasonArray) {
                                                                    Msg.error('请添加人员和扣分原因及扣分后暂存!');
                                                                    setBtnsLoading('remove', 'zhpjzc');
                                                                    break;
                                                                }else if(objzh._formData.qnnTableZZ[i].score >= 0 || objzh._formData.qnnTableZZ[i].score < -100){
                                                                    Msg.error('扣分需小于0大于-100!');
                                                                    setBtnsLoading('remove', 'zhpjzc');
                                                                }else if (i === objzh._formData.qnnTableZZ.length - 1) {
                                                                    objzh._formData.qnnTableZZ.map((item) => {
                                                                        if (item ?.userArray ?.length) {
                                                                            body.push({
                                                                                scoreId: item.scoreId,
                                                                                assessmentId: obj.rowData.assessmentId,
                                                                                auditeeDeptId: item.auditeeDeptId ? item.auditeeDeptId : item.userArray[0].value,
                                                                                auditeeDeptName: item.auditeeDeptName ? item.auditeeDeptName : item.userArray[0].departmentName,
                                                                                auditStatus: '3',
                                                                                auditeeProId: projectId,
                                                                                auditeeProName: projectName,
                                                                                userArray: item.userArray,
                                                                                score: item.score,
                                                                                deductReasonArray: item.deductReasonArray
                                                                            });
                                                                        }
                                                                        return item;
                                                                    })
                                                                    this.props.myFetch('batchSubmitZjXmJxAssessmentUserScoreBySecretary', body).then(({ success, message, data }) => {
                                                                        if (success) {
                                                                            setBtnsLoading('remove', 'zhpjzc');
                                                                            objzh.btnCallbackFn.closeDrawer(false);
                                                                            objzh.btnCallbackFn.refresh();
                                                                        } else {
                                                                            setBtnsLoading('remove', 'zhpjzc');
                                                                            Msg.error(message);
                                                                        }
                                                                    })
                                                                }
                                                            }
                                                        } else {
                                                            setBtnsLoading('remove', 'zhpjzc');
                                                            Msg.error('请新增数据暂存！');
                                                        }
                                                    }
                                                },
                                                {
                                                    field: 'zhpjSubmit',
                                                    name: 'zhpjSubmit',//内置add del
                                                    type: 'primary',//类型  默认 primary
                                                    label: '提交',//提交数据并且关闭右边抽屉
                                                    onClick: (objzh) => {
                                                        let setBtnsLoading = objzh.btnCallbackFn.setBtnsLoading;
                                                        setBtnsLoading('add', 'zhpjSubmit');
                                                        let body = [];
                                                        if (objzh._formData.qnnTableZZ.length) {
                                                            for (let i = 0; i < objzh._formData.qnnTableZZ.length; i++) {
                                                                if (!objzh._formData.qnnTableZZ[i].userArray || !objzh._formData.qnnTableZZ[i].score || !objzh._formData.qnnTableZZ[i].deductReasonArray) {
                                                                    Msg.error('请添加人员和扣分原因及评分后提交!');
                                                                    setBtnsLoading('remove', 'zhpjSubmit');
                                                                    break;
                                                                }else if(objzh._formData.qnnTableZZ[i].score >= 0 || objzh._formData.qnnTableZZ[i].score < -100){
                                                                    Msg.error('扣分需小于0大于-100!');
                                                                    setBtnsLoading('remove', 'zhpjSubmit');
                                                                } else if (i === objzh._formData.qnnTableZZ.length - 1) {
                                                                    objzh._formData.qnnTableZZ.map((item) => {
                                                                        if (item ?.userArray ?.length) {
                                                                            body.push({
                                                                                scoreId: item.scoreId,
                                                                                assessmentId: obj.rowData.assessmentId,
                                                                                auditeeDeptId: item.auditeeDeptId ? item.auditeeDeptId : item.userArray[0].value,
                                                                                auditeeDeptName: item.auditeeDeptName ? item.auditeeDeptName : item.userArray[0].departmentName,
                                                                                auditStatus: '4',
                                                                                auditeeProId: projectId,
                                                                                auditeeProName: projectName,
                                                                                userArray: item.userArray,
                                                                                score: item.score,
                                                                                deductReasonArray: item.deductReasonArray
                                                                            });
                                                                        }
                                                                        return item;
                                                                    })
                                                                    this.props.myFetch('batchSubmitZjXmJxAssessmentUserScoreBySecretary', body).then(({ success, message, data }) => {
                                                                        if (success) {
                                                                            setBtnsLoading('remove', 'zhpjSubmit');
                                                                            objzh.btnCallbackFn.closeDrawer(false);
                                                                            objzh.btnCallbackFn.refresh();
                                                                        } else {
                                                                            setBtnsLoading('remove', 'zhpjSubmit');
                                                                            Msg.error(message);
                                                                        }
                                                                    })
                                                                }
                                                            }
                                                        } else {
                                                            setBtnsLoading('remove', 'zhpjSubmit');
                                                            Msg.error('请新增数据提交！');
                                                        }
                                                    }
                                                }
                                            ])
                                        }
                                        obj.btnCallbackFn.closeDrawer(true);
                                    })
                                },
                                render: (data) => {
                                    return data;
                                }
                            },
                            isInForm: false
                        }
                    ]}
                />
            </div>
        );
    }
}
export default Index;