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
                            title: "????????????",
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
                                        label: '????????????',
                                        disabled:(obj) => {
                                            if (obj.btnCallbackFn.getSelectedRows().length > 0) {
                                                return false;
                                            } else {
                                                return true;
                                            }
                                        },
                                        onClick: (obj) => {
                                            confirm({
                                                title: `????????????????????????????`,
                                                content: `????????????????????????????????????`,
                                                okText: "??????",
                                                cancelText: "??????",
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
                                            title: '??????',
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
                                                            label: '??????',
                                                        },
                                                        {
                                                            field: 'rwkhappeal',
                                                            name: 'appeal',
                                                            type: 'primary',
                                                            label: '??????',
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
                                                            label: '??????',
                                                            onClick: (obj) => {
                                                                let setBtnsLoading = obj.btnCallbackFn.setBtnsLoading;
                                                                setBtnsLoading('add', 'rwkhaffirm');
                                                                for (let i = 0; i < this.state.dataRWKH.length; i++) {
                                                                    if (!this.state.dataRWKH[i].score) {
                                                                        setBtnsLoading('remove', 'rwkhaffirm');
                                                                        Msg.error('??????????????????????????????!');
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
                                                            label: '??????',
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
                                            title: '????????????',
                                            dataIndex: 'assessmentTitle',
                                            key: 'assessmentTitle',
                                            width: 150,
                                            tooltip: 15
                                        },
                                        isInForm: false
                                    },
                                    {
                                        table: {
                                            title: '??????',
                                            dataIndex: 'auditeeName',
                                            key: 'auditeeName',
                                            width: 100
                                        },
                                        form: {
                                            type: 'string',
                                            addDisabled: true,
                                            editDisabled: true,
                                            placeholder: "?????????",
                                            spanForm: 12
                                        }
                                    },
                                    {
                                        table: {
                                            title: '??????',
                                            dataIndex: 'auditeeDeptName',
                                            key: 'auditeeDeptName',
                                            width: 100
                                        },
                                        form: {
                                            type: 'string',
                                            addDisabled: true,
                                            editDisabled: true,
                                            placeholder: "?????????",
                                            spanForm: 12
                                        }
                                    },
                                    {
                                        isInTable: false,
                                        form: {
                                            label: '????????????',
                                            field: 'assessmentTitle',
                                            type: 'string',
                                            addDisabled: true,
                                            editDisabled: true,
                                            placeholder: "?????????",
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
                                            title: '??????',
                                            dataIndex: 'score',
                                            key: 'score',
                                            width: 100
                                        },
                                        isInForm: false
                                    },
                                    {
                                        table: {
                                            title: '??????',
                                            dataIndex: 'auditStatus',
                                            key: 'auditStatus',
                                            width: 100,
                                            render: (data) => {
                                                if (data === '0') {
                                                    return '?????????';
                                                } else if (data === '1') {
                                                    return '??????????????????';
                                                } else if (data === '2') {
                                                    return '?????????';
                                                } else if (data === '3') {
                                                    return '???????????????';
                                                } else if (data === '4') {
                                                    return '?????????';
                                                } else if (data === '5') {
                                                    return '?????????';
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
                                                                        title: '??????????????????',
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
                                                                        title: '?????????',
                                                                        dataIndex: 'targetValue',
                                                                        key: 'targetValue',
                                                                        width: 100,
                                                                        tooltip: 6,
                                                                    },
                                                                    isInForm: false
                                                                },
                                                                {
                                                                    table: {
                                                                        title: '??????????????????',
                                                                        dataIndex: 'scoringStandard',
                                                                        key: 'scoringStandard',
                                                                        width: 150,
                                                                        tooltip: 9
                                                                    },
                                                                    isInForm: false
                                                                },
                                                                {
                                                                    table: {
                                                                        title: '??????',
                                                                        dataIndex: 'weightValue',
                                                                        key: 'weightValue',
                                                                        width: 100
                                                                    },
                                                                    isInForm: false
                                                                },
                                                                {
                                                                    table: {
                                                                        title: '??????????????????',
                                                                        dataIndex: 'completion',
                                                                        key: 'completion',
                                                                        width: 150,
                                                                        tooltip: 9,
                                                                    },
                                                                    isInForm: false
                                                                },
                                                                {
                                                                    table: {
                                                                        title: '??????',
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
                                                                            placeholder: "?????????"
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
                                                                        title: '??????????????????',
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
                                                                            placeholder: "?????????"
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
                            title: "????????????",
                            content: {
                                formConfig: [
                                    {
                                        field: 'assessmentId',
                                        label: '??????',
                                        type: 'string',
                                        initialValue: rowData ?.assessmentId,
                                        hide: true
                                    },
                                    {
                                        field: 'yearMonth',
                                        label: '??????',
                                        type: 'month',
                                        disabled: true,
                                        initialValue: rowData ?.yearMonth,
                                        placeholder: '?????????'
                                    },
                                    {
                                        field: 'assessmentTitle',
                                        label: '????????????',
                                        type: 'string',
                                        disabled: true,
                                        initialValue: rowData ?.assessmentTitle,
                                        placeholder: '?????????'
                                    },
                                    {
                                        type: "component",
                                        field: "component1",
                                        Component: obj => {
                                            return (
                                                <div style={{ background: '#f0f2f5', padding: '8px 12px' }}>
                                                    ????????????
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
                                                        upload={this.props.myUpload} //??????????????????promise
                                                        //??????????????????ajax???????????????????????????????????????head?????????????????? ?????????????????????
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
                                                    ???????????????
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
                                                        upload={this.props.myUpload} //??????????????????promise
                                                        //??????????????????ajax???????????????????????????????????????head?????????????????? ?????????????????????
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
                                                    ????????????
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
                                                        upload={this.props.myUpload} //??????????????????promise
                                                        //??????????????????ajax???????????????????????????????????????head?????????????????? ?????????????????????
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
                            title: "????????????",
                            content: {
                                formConfig: [
                                    {
                                        field: 'assessmentId',
                                        label: '??????',
                                        type: 'string',
                                        initialValue: rowData ?.assessmentId,
                                        hide: true
                                    },
                                    {
                                        field: 'yearMonth',
                                        label: '??????',
                                        type: 'month',
                                        disabled: true,
                                        initialValue: rowData ?.yearMonth,
                                        placeholder: '?????????'
                                    },
                                    {
                                        field: 'assessmentTitle',
                                        label: '????????????',
                                        type: 'string',
                                        disabled: true,
                                        initialValue: rowData ?.assessmentTitle,
                                        placeholder: '?????????'
                                    },
                                    {
                                        type: "component",
                                        field: "component1",
                                        Component: obj => {
                                            return (
                                                <div style={{ background: '#f0f2f5', padding: '8px 12px' }}>
                                                    ????????????
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
                                                        upload={this.props.myUpload} //??????????????????promise
                                                        //??????????????????ajax???????????????????????????????????????head?????????????????? ?????????????????????
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
                                                    ???????????????
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
                                                        upload={this.props.myUpload} //??????????????????promise
                                                        //??????????????????ajax???????????????????????????????????????head?????????????????? ?????????????????????
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
                                                    ????????????
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
                                                        upload={this.props.myUpload} //??????????????????promise
                                                        //??????????????????ajax???????????????????????????????????????head?????????????????? ?????????????????????
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
                            title: "????????????",
                            content: {
                                formConfig: [
                                    {
                                        field: 'assessmentId',
                                        label: '??????',
                                        type: 'string',
                                        initialValue: rowData ?.assessmentId,
                                        hide: true
                                    },
                                    {
                                        field: 'yearMonth',
                                        label: '??????',
                                        type: 'month',
                                        disabled: true,
                                        initialValue: rowData ?.yearMonth,
                                        placeholder: '?????????'
                                    },
                                    {
                                        field: 'assessmentTitle',
                                        label: '????????????',
                                        type: 'string',
                                        disabled: true,
                                        initialValue: rowData ?.assessmentTitle,
                                        placeholder: '?????????'
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
                                            isShowRowSelect: this.state.rowData.compositeComp === '??????' ? true : false,
                                            fetchConfig: {
                                                apiName: 'getZjXmJxAssessmentUserScoreListByCompositeReviewer',
                                                otherParams: {
                                                    assessmentId: this.state.rowData.assessmentId
                                                }
                                            },
                                            actionBtns: this.state.rowData.compositeComp === '??????' ? [
                                                {
                                                    name: 'addRow',
                                                    icon: 'plus',
                                                    type: 'primary',
                                                    label: '??????',
                                                },
                                                {
                                                    name: 'del',
                                                    icon: 'del',
                                                    type: 'danger',
                                                    label: '??????',
                                                }
                                            ] : [],
                                            formConfig: [
                                                {
                                                    table: {
                                                        title: '??????',
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
                                                            fetchConfig: {//??????????????????????????????????????????
                                                                apiName: 'getSysDepartmentUserAllTree'
                                                            },
                                                            edit: this.state.rowData.compositeComp === '??????' ? true : false,
                                                            search: true,
                                                            searchPlaceholder: '????????????????????????',
                                                            // searchApi:'getSysDepartmentUserAllTree',  //??????????????????api  [string]
                                                            searchParamsKey: 'search',//???????????????K ?????????'searchText'   [string]
                                                            searchOtherParams: { pageSize: 999 }//????????????????????????  [object]
                                                        },
                                                        placeholder: '?????????'
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '??????',
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
                                                        title: '????????????',
                                                        dataIndex: 'deductReasonArray',
                                                        key: 'deductReasonArray',
                                                        width: 200,
                                                        tdEdit: true
                                                    },
                                                    form: {
                                                        type: "select",
                                                        mode: 'tags',
                                                        disabled: this.state.rowData.compositeComp === '??????' ? false : true,
                                                        onChange: (value, obj) => {
                                                            setTimeout(() => {
                                                                this.rootTable.btnCallbackFn.qnnForm.getValues(false, (val) => {
                                                                    //?????????vs?????? ??????????.
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
                                                                label: "??????",
                                                                value: "??????"
                                                            },
                                                            {
                                                                label: "??????",
                                                                value: "??????"
                                                            },
                                                            {
                                                                label: "??????",
                                                                value: "??????"
                                                            }
                                                        ]
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '??????',
                                                        dataIndex: 'score',
                                                        key: 'score',
                                                        width: 200,
                                                        tdEdit: true
                                                    },
                                                    form: {
                                                        type: "number",
                                                        disabled: this.state.rowData.compositeComp === '??????' ? false : true,
                                                        onBlur: () => {
                                                            setTimeout(() => {
                                                                this.rootTable.btnCallbackFn.qnnForm.getValues(false, (val) => {
                                                                    //?????????vs?????? ??????????.
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
                                title: '??????',
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
                                placeholder: '?????????'
                            }
                        },
                        {
                            table: {
                                title: '????????????',
                                dataIndex: 'assessmentTitle',
                                key: 'assessmentTitle',
                                filter: true,
                                width: 150,
                                tooltip: 25
                            },
                            form: {
                                type: 'string',
                                hide: true,
                                placeholder: '?????????'
                            }
                        },
                        {
                            table: {
                                title: '????????????',
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
                                title: '????????????(?????????/?????????)',
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
                                title: '????????????',
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
                                                        placeholder: "??????",
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
                                                        placeholder: "??????",
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
                                                        placeholder: "??????",
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
                                                            label: "??????"
                                                        }
                                                    ])
                                                } else {
                                                    obj.btnCallbackFn.setDrawerBtns([
                                                        {
                                                            field: 'zbkhCancel',
                                                            name: "cancel",
                                                            type: "dashed",
                                                            label: "??????"
                                                        },
                                                        {
                                                            field: 'zbkhZdjs',
                                                            name: 'zdzs',//??????add del
                                                            type: 'primary',//??????  ?????? primary
                                                            label: '????????????',//????????????????????????????????????
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
                                                            name: 'zbkhzc',//??????add del
                                                            type: 'primary',//??????  ?????? primary
                                                            label: '??????',//????????????????????????????????????
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
                                                            name: 'zbkhSubmit',//??????add del
                                                            type: 'primary',//??????  ?????? primary
                                                            label: '??????',//???????????????????????????????????? 
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
                                                                            Msg.error('??????????????????????????????!');
                                                                            setBtnsLoading('remove', 'zbkhSubmit');
                                                                            return;
                                                                        }
                                                                    }
                                                                }
                                                                if (datatjfz.length) {
                                                                    for (let i = 0; i < datatjfz.length; i++) {
                                                                        if (!datatjfz[i].score) {
                                                                            Msg.error('?????????????????????????????????!');
                                                                            setBtnsLoading('remove', 'zbkhSubmit');
                                                                            return;
                                                                        }
                                                                    }
                                                                }
                                                                if (datatjyg.length) {
                                                                    for (let i = 0; i < datatjyg.length; i++) {
                                                                        if (!datatjyg[i].score) {
                                                                            Msg.error('??????????????????????????????!');
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
                                title: '????????????',
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
                                                        placeholder: "??????",
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
                                                        placeholder: "??????",
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
                                                        placeholder: "??????",
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
                                                            label: "??????"
                                                        }
                                                    ])
                                                } else {
                                                    obj.btnCallbackFn.setDrawerBtns([
                                                        {
                                                            field: 'xmkhCancel',
                                                            name: "cancel",
                                                            type: "dashed",
                                                            label: "??????"
                                                        },
                                                        {
                                                            field: 'xmkhZdjs',
                                                            name: 'zdzs',//??????add del
                                                            type: 'primary',//??????  ?????? primary
                                                            label: '????????????',//???????????????????????????????????? 
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
                                                            name: 'xmkhzc',//??????add del
                                                            type: 'primary',//??????  ?????? primary
                                                            label: '??????',//???????????????????????????????????? 
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
                                                            name: 'xmkhSubmit',//??????add del
                                                            type: 'primary',//??????  ?????? primary
                                                            label: '??????',//???????????????????????????????????? 
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
                                                                            Msg.error('??????????????????????????????!');
                                                                            setBtnsLoading('remove', 'xmkhSubmit');
                                                                            return;
                                                                        }
                                                                    }
                                                                }
                                                                if (datatjfz.length) {
                                                                    for (let i = 0; i < datatjfz.length; i++) {
                                                                        if (!datatjfz[i].score) {
                                                                            Msg.error('?????????????????????????????????!');
                                                                            setBtnsLoading('remove', 'xmkhSubmit');
                                                                            return;
                                                                        }
                                                                    }
                                                                }
                                                                if (datatjyg.length) {
                                                                    for (let i = 0; i < datatjyg.length; i++) {
                                                                        if (!datatjyg[i].score) {
                                                                            Msg.error('??????????????????????????????!');
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
                                title: '????????????',
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
                                        if (obj.rowData.compositeComp !== '??????') {
                                            obj.btnCallbackFn.setDrawerBtns([
                                                {
                                                    field: 'zhpjCancel',
                                                    name: "cancel",
                                                    type: "dashed",
                                                    label: "??????"
                                                }
                                            ])
                                        } else {
                                            obj.btnCallbackFn.setDrawerBtns([
                                                {
                                                    field: 'zhpjCancel',
                                                    name: "cancel",
                                                    type: "dashed",
                                                    label: "??????"
                                                },
                                                {
                                                    field: 'zhpjzc',
                                                    name: 'zhpjzc',//??????add del
                                                    type: 'primary',//??????  ?????? primary
                                                    label: '??????',//????????????????????????????????????
                                                    onClick: (objzh) => {
                                                        let setBtnsLoading = objzh.btnCallbackFn.setBtnsLoading;
                                                        setBtnsLoading('add', 'zhpjzc');
                                                        let body = [];
                                                        if (objzh._formData.qnnTableZZ.length) {
                                                            for (let i = 0; i < objzh._formData.qnnTableZZ.length; i++) {
                                                                if (!objzh._formData.qnnTableZZ[i].userArray || !objzh._formData.qnnTableZZ[i].score || !objzh._formData.qnnTableZZ[i].deductReasonArray) {
                                                                    Msg.error('????????????????????????????????????????????????!');
                                                                    setBtnsLoading('remove', 'zhpjzc');
                                                                    break;
                                                                }else if(objzh._formData.qnnTableZZ[i].score >= 0 || objzh._formData.qnnTableZZ[i].score < -100){
                                                                    Msg.error('???????????????0??????-100!');
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
                                                            Msg.error('????????????????????????');
                                                        }
                                                    }
                                                },
                                                {
                                                    field: 'zhpjSubmit',
                                                    name: 'zhpjSubmit',//??????add del
                                                    type: 'primary',//??????  ?????? primary
                                                    label: '??????',//????????????????????????????????????
                                                    onClick: (objzh) => {
                                                        let setBtnsLoading = objzh.btnCallbackFn.setBtnsLoading;
                                                        setBtnsLoading('add', 'zhpjSubmit');
                                                        let body = [];
                                                        if (objzh._formData.qnnTableZZ.length) {
                                                            for (let i = 0; i < objzh._formData.qnnTableZZ.length; i++) {
                                                                if (!objzh._formData.qnnTableZZ[i].userArray || !objzh._formData.qnnTableZZ[i].score || !objzh._formData.qnnTableZZ[i].deductReasonArray) {
                                                                    Msg.error('????????????????????????????????????????????????!');
                                                                    setBtnsLoading('remove', 'zhpjSubmit');
                                                                    break;
                                                                }else if(objzh._formData.qnnTableZZ[i].score >= 0 || objzh._formData.qnnTableZZ[i].score < -100){
                                                                    Msg.error('???????????????0??????-100!');
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
                                                            Msg.error('????????????????????????');
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