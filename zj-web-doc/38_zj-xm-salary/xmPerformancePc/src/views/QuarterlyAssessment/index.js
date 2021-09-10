import React, { Component } from "react";
import QnnTable from '../modules/qnn-table';
import { message as Msg, Modal } from 'antd';
const { confirm } = Modal;
const config = {
    antd: {
        rowKey: function (row) {
            return row.assessmentId
        },
        size: 'small'
    },
    drawerConfig: {
        width: '900px'
    },
    paginationConfig: {
        position: 'bottom'
    },
    formItemLayout: {
        labelCol: {
            xs: { span: 3 },
            sm: { span: 3 }
        },
        wrapperCol: {
            xs: { span: 21 },
            sm: { span: 21 }
        }
    }
}
class Index extends Component {
    constructor() {
        super();
        this.state = {
            visibleXZ: false,
            visibleZBK: false,
            ZBKFlag: true,
            visibleQZ: false,
            visibleXMZBK: false,
            XMZBKFlag: true,
            rowData: null
        }
    }
    render() {
        const { visibleZBK, ZBKFlag, visibleQZ, visibleXMZBK, XMZBKFlag, visibleXZ, rowData } = this.state;
        return (
            <div>
                <QnnTable
                    fetch={this.props.myFetch}
                    upload={this.props.myUpload}
                    headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                    {...config}
                    fetchConfig={{
                        apiName: 'getZjXmJxQuarterlyAssessmentList'
                    }}
                    actionBtns={[
                        {
                            name: 'increased',
                            icon: 'plus',
                            type: 'primary',
                            label: '新增',
                            isValidate: false,
                            willExecute: (obj) => {
                                this.setState({
                                    visibleQZ: false,
                                    visibleZBK: false,
                                    visibleXMZBK: false,
                                    rowData: null,
                                    visibleXZ: true
                                }, () => {
                                    obj.btnCallbackFn.setDrawerBtns([
                                        {
                                            field: 'increasedCancel',
                                            name: "cancel",
                                            type: "dashed",
                                            label: "取消"
                                        },
                                        {
                                            field: 'increasedSubmit',
                                            name: 'submit',//内置add del
                                            type: 'primary',//类型  默认 primary
                                            label: '提交',//提交数据并且关闭右边抽屉 
                                            fetchConfig: {//ajax配置
                                                apiName: 'cascadeAddZjXmJxQuarterlyAssessment'
                                            }
                                        }
                                    ])
                                    obj.btnCallbackFn.closeDrawer(true);
                                })
                            }
                        },
                        {
                            name: 'send',
                            icon: 'plus',
                            type: 'primary',
                            label: '发送通知',
                            disabled: (obj) => {
                                if (obj.btnCallbackFn.getSelectedRows().length > 0) {
                                    return false;
                                } else {
                                    return true;
                                }
                            },
                            onClick: (obj) => {
                                // obj.btnCallbackFn.clearSelectedRows();
                                // if (obj.selectedRows.length === 1) {
                                //     if (obj.selectedRows[0].noticeStatus === '1') {
                                //         Msg.error('该数据已发送，请勿重复发送！');
                                //     } else {
                                //         confirm({
                                //             content: '您确认发送该信息吗?',
                                //             centered: true,
                                //             onOk: () => {
                                //                 this.props.myFetch('sendZjXmJxMonthlyAssessmentNotice', ...obj.selectedRows).then(({ success, message, data }) => {
                                //                     if (success) {
                                //                         obj.btnCallbackFn.refresh();
                                //                     } else {
                                //                         Msg.error(message);
                                //                     }
                                //                 });
                                //             }
                                //         });
                                //     }
                                // } else {
                                //     Msg.error('请选择一条数据发送!');
                                // }
                            }
                        },
                        {
                            name: 'diydel',
                            icon: 'delete',
                            type: 'danger',
                            label: '删除',
                            disabled: (obj) => {
                                if (obj.btnCallbackFn.getSelectedRows().length > 0) {
                                    return false;
                                } else {
                                    return true;
                                }
                            },
                            onClick: (obj) => {
                                for (let i = 0; i < obj.selectedRows.length; i++) {
                                    if (obj.selectedRows[i].noticeStatus && obj.selectedRows[i].noticeStatus === '1') {
                                        Msg.error('发起考核的数据不允许删除！');
                                        break;
                                    } else if (i === obj.selectedRows.length - 1) {
                                        this.props.myFetch('batchDeleteUpdateZjXmJxQuarterlyAssessment', obj.selectedRows).then(({ success, message, data }) => {
                                            if (success) {
                                                obj.btnCallbackFn.clearSelectedRows();
                                                obj.btnCallbackFn.refresh();
                                            } else {
                                                Msg.error(message);
                                            }
                                        });
                                    }
                                }
                            }
                        }
                    ]}
                    tabs={visibleXZ ? [
                        {
                            field: "increased",
                            name: "qnnForm",
                            title: "基础信息",
                            content: {
                                formConfig: [
                                    {
                                        field: 'assessmentId',
                                        type: 'string',
                                        hide: true,
                                    },
                                    {
                                        label: '年份',
                                        field: 'yearMonth',
                                        type: 'year',
                                        required: true,
                                        placeholder: '请选择'
                                    },
                                    {
                                        label: '季度',
                                        field: 'quarter',
                                        type: 'select',
                                        optionConfig: {
                                            label: 'label',
                                            value: 'value'
                                        },
                                        optionData: [
                                            {
                                                label: '第一季度',
                                                value: '1'
                                            },
                                            {
                                                label: '第二季度',
                                                value: '2'
                                            },
                                            {
                                                label: '第三季度',
                                                value: '3'
                                            },
                                            {
                                                label: '第四季度',
                                                value: '4'
                                            }
                                        ],
                                        required: true,
                                        placeholder: "请选择"
                                    },
                                    {
                                        label: '考核标题',
                                        field: 'assessmentTitle',
                                        type: 'string',
                                        required: true,
                                        placeholder: "请选择"
                                    },
                                    {
                                        label: '通知内容',
                                        field: 'noticeContent',
                                        type: 'string',
                                        required: true,
                                        placeholder: "请选择"
                                    }
                                ]
                            }
                        }
                    ] : visibleZBK ? [
                        {
                            field: "zbkfsw",
                            name: "qnnForm",
                            title: "指标库非收尾",
                            content: {
                                formConfig: [
                                    {
                                        field: "zbkList",
                                        type: "qnnTable",
                                        qnnTableConfig: {
                                            fetchConfig: () => {
                                                if (rowData) {
                                                    return {
                                                        apiName: 'getZjXmJxQuarterlyLibraryDetailsList',
                                                        otherParams: {
                                                            assessmentId: rowData.assessmentId,
                                                            isClosed: "0"
                                                        }
                                                    }
                                                }
                                                return {};
                                            },
                                            wrappedComponentRef: (me) => {
                                                this.tableZBKFSW = me;
                                            },
                                            antd: {
                                                rowKey: 'detailsId',
                                                size: 'small',
                                                scroll:{
                                                    y: window.innerHeight - 217
                                                }
                                            },
                                            drawerConfig: {
                                                width: '900px'
                                            },
                                            pageSize: 9999,
                                            paginationConfig: false,
                                            actionBtnsPosition: "top",
                                            isShowRowSelect: ZBKFlag,
                                            actionBtns: ZBKFlag ? [
                                                {
                                                    name: 'del',
                                                    icon: 'delete',
                                                    type: 'danger',
                                                    label: '删除',
                                                    fetchConfig: {
                                                        apiName: 'batchDeleteUpdateZjXmJxQuarterlyLibraryDetails',
                                                    },
                                                }
                                            ] : [],
                                            formConfig: [
                                                {
                                                    isInTable: false,
                                                    form: {
                                                        field: 'detailsId',
                                                        type: 'string',
                                                        hide: true,
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '考核标题',
                                                        dataIndex: 'libraryTitle',
                                                        key: 'libraryTitle',
                                                        fixed: 'left',
                                                        filter: true,
                                                        onClick: 'detail'
                                                    },
                                                    form: {
                                                        type: 'string',
                                                        placeholder: '请输入',
                                                        required: true
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '考核内容',
                                                        dataIndex: 'libraryContent',
                                                        key: 'libraryContent',
                                                    },
                                                    form: {
                                                        type: 'string',
                                                        placeholder: '请输入',
                                                        required: true
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '考核责任人',
                                                        dataIndex: 'personLiableName',
                                                        key: 'personLiableName'
                                                    },
                                                    form: {
                                                        type: 'string',
                                                        placeholder: "请输入",
                                                        required: true
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '是否加分项',
                                                        dataIndex: 'isFixedScore',
                                                        key: 'isFixedScore',
                                                        width: 120,
                                                        render: (data) => {
                                                            if (data === '0') {
                                                                return '否';
                                                            }
                                                            return '是';
                                                        }
                                                    },
                                                    form: {
                                                        type: 'radio',
                                                        required: true,
                                                        optionData: [  //可为function (props)=>array
                                                            {
                                                                label: "否",
                                                                value: "0"
                                                            },
                                                            {
                                                                label: "是",
                                                                value: "1"
                                                            }
                                                        ]
                                                    }
                                                },
                                                {
                                                    isInTable: false,
                                                    form: {
                                                        label: '分数',
                                                        field: 'score',
                                                        type: 'number',
                                                        dependencies: ["isFixedScore"],
                                                        hide: function (obj) {
                                                            if (obj.form.getFieldValue("isFixedScore") === '0') {
                                                                return false;
                                                            } else {
                                                                return true;
                                                            }
                                                        },
                                                        required: true,
                                                        placeholder: '请输入'
                                                    }
                                                },
                                                {
                                                    isInTable: false,
                                                    form: {
                                                        label: '加/减分下限',
                                                        field: 'lowerLimitScore',
                                                        type: 'number',
                                                        required: true,
                                                        dependencies: ["isFixedScore"],
                                                        hide: function (obj) {
                                                            if (obj.form.getFieldValue("isFixedScore") === '1') {
                                                                return false;
                                                            } else {
                                                                return true;
                                                            }
                                                        },
                                                        placeholder: '请输入'
                                                    }
                                                },
                                                {
                                                    isInTable: false,
                                                    form: {
                                                        label: '加/减分上限',
                                                        field: 'upperLimitScore',
                                                        type: 'number',
                                                        required: true,
                                                        dependencies: ["isFixedScore"],
                                                        hide: function (obj) {
                                                            if (obj.form.getFieldValue("isFixedScore") === '1') {
                                                                return false;
                                                            } else {
                                                                return true;
                                                            }
                                                        },
                                                        placeholder: '请输入'
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '是否收尾项目',
                                                        dataIndex: 'isClosed',
                                                        key: 'isClosed',
                                                        width: 120,
                                                        render: (data) => {
                                                            if (data === '0') {
                                                                return '否';
                                                            }
                                                            return '是';
                                                        }
                                                    },
                                                    form: {
                                                        type: 'radio',
                                                        required: true,
                                                        optionData: [  //可为function (props)=>array
                                                            {
                                                                label: "否",
                                                                value: "0"
                                                            },
                                                            {
                                                                label: "是",
                                                                value: "1"
                                                            }
                                                        ]
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '备注',
                                                        dataIndex: 'remarks',
                                                        key: 'remarks'
                                                    },
                                                    form: {
                                                        type: 'textarea',
                                                        placeholder: '请输入'
                                                    }
                                                }
                                            ]
                                        }
                                    }
                                ]
                            }
                        },
                        {
                            field: "zbkfsw",
                            name: "qnnForm",
                            title: "指标库收尾",
                            content: {
                                formConfig: [
                                    {
                                        field: "zbkList",
                                        type: "qnnTable",
                                        qnnTableConfig: {
                                            fetchConfig: () => {
                                                if (rowData) {
                                                    return {
                                                        apiName: 'getZjXmJxQuarterlyLibraryDetailsList',
                                                        otherParams: {
                                                            assessmentId: rowData.assessmentId,
                                                            isClosed: "1"
                                                        }
                                                    }
                                                }
                                                return {};
                                            },
                                            wrappedComponentRef: (me) => {
                                                this.tableZBKSW = me;
                                            },
                                            antd: {
                                                rowKey: 'detailsId',
                                                size: 'small',
                                                scroll:{
                                                    y: window.innerHeight - 217
                                                }
                                            },
                                            drawerConfig: {
                                                width: '900px'
                                            },
                                            pageSize: 9999,
                                            paginationConfig: false,
                                            actionBtnsPosition: "top",
                                            isShowRowSelect: ZBKFlag,
                                            actionBtns: ZBKFlag ? [
                                                {
                                                    name: 'del',
                                                    icon: 'delete',
                                                    type: 'danger',
                                                    label: '删除',
                                                    fetchConfig: {
                                                        apiName: 'batchDeleteUpdateZjXmJxQuarterlyLibraryDetails',
                                                    },
                                                }
                                            ] : [],
                                            formConfig: [
                                                {
                                                    isInTable: false,
                                                    form: {
                                                        field: 'detailsId',
                                                        type: 'string',
                                                        hide: true,
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '考核标题',
                                                        dataIndex: 'libraryTitle',
                                                        key: 'libraryTitle',
                                                        fixed: 'left',
                                                        filter: true,
                                                        onClick: 'detail'
                                                    },
                                                    form: {
                                                        type: 'string',
                                                        placeholder: '请输入',
                                                        required: true
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '考核内容',
                                                        dataIndex: 'libraryContent',
                                                        key: 'libraryContent',
                                                    },
                                                    form: {
                                                        type: 'string',
                                                        placeholder: '请输入',
                                                        required: true
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '考核责任人',
                                                        dataIndex: 'personLiableName',
                                                        key: 'personLiableName'
                                                    },
                                                    form: {
                                                        type: 'string',
                                                        placeholder: "请输入",
                                                        required: true
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '是否加分项',
                                                        dataIndex: 'isFixedScore',
                                                        key: 'isFixedScore',
                                                        width: 120,
                                                        render: (data) => {
                                                            if (data === '0') {
                                                                return '否';
                                                            }
                                                            return '是';
                                                        }
                                                    },
                                                    form: {
                                                        type: 'radio',
                                                        required: true,
                                                        optionData: [  //可为function (props)=>array
                                                            {
                                                                label: "否",
                                                                value: "0"
                                                            },
                                                            {
                                                                label: "是",
                                                                value: "1"
                                                            }
                                                        ]
                                                    }
                                                },
                                                {
                                                    isInTable: false,
                                                    form: {
                                                        label: '分数',
                                                        field: 'score',
                                                        type: 'number',
                                                        dependencies: ["isFixedScore"],
                                                        hide: function (obj) {
                                                            if (obj.form.getFieldValue("isFixedScore") === '0') {
                                                                return false;
                                                            } else {
                                                                return true;
                                                            }
                                                        },
                                                        required: true,
                                                        placeholder: '请输入'
                                                    }
                                                },
                                                {
                                                    isInTable: false,
                                                    form: {
                                                        label: '加/减分下限',
                                                        field: 'lowerLimitScore',
                                                        type: 'number',
                                                        required: true,
                                                        dependencies: ["isFixedScore"],
                                                        hide: function (obj) {
                                                            if (obj.form.getFieldValue("isFixedScore") === '1') {
                                                                return false;
                                                            } else {
                                                                return true;
                                                            }
                                                        },
                                                        placeholder: '请输入'
                                                    }
                                                },
                                                {
                                                    isInTable: false,
                                                    form: {
                                                        label: '加/减分上限',
                                                        field: 'upperLimitScore',
                                                        type: 'number',
                                                        required: true,
                                                        dependencies: ["isFixedScore"],
                                                        hide: function (obj) {
                                                            if (obj.form.getFieldValue("isFixedScore") === '1') {
                                                                return false;
                                                            } else {
                                                                return true;
                                                            }
                                                        },
                                                        placeholder: '请输入'
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '是否收尾项目',
                                                        dataIndex: 'isClosed',
                                                        key: 'isClosed',
                                                        width: 120,
                                                        render: (data) => {
                                                            if (data === '0') {
                                                                return '否';
                                                            }
                                                            return '是';
                                                        }
                                                    },
                                                    form: {
                                                        type: 'radio',
                                                        required: true,
                                                        optionData: [  //可为function (props)=>array
                                                            {
                                                                label: "否",
                                                                value: "0"
                                                            },
                                                            {
                                                                label: "是",
                                                                value: "1"
                                                            }
                                                        ]
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '备注',
                                                        dataIndex: 'remarks',
                                                        key: 'remarks'
                                                    },
                                                    form: {
                                                        type: 'textarea',
                                                        placeholder: '请输入'
                                                    }
                                                }
                                            ]
                                        }
                                    }
                                ]
                            }
                        }
                    ] : visibleQZ ? [
                        {
                            field: "qzfsw",
                            name: "qnnForm",
                            title: "权重非收尾",
                            content: {
                                formConfig: [
                                    {
                                        field: "qzList",
                                        type: "qnnTable",
                                        qnnTableConfig: {
                                            fetchConfig: () => {
                                                if (rowData) {
                                                    return {
                                                        apiName: 'getZjXmJxQuarterlyWeightDetailsList',
                                                        otherParams: {
                                                            assessmentId: rowData.assessmentId,
                                                            isClosed: "0"
                                                        }
                                                    }
                                                }
                                                return {};
                                            },
                                            antd: {
                                                rowKey: 'detailsId',
                                                size: 'small',
                                                scroll:{
                                                    y: window.innerHeight - 217
                                                }
                                            },
                                            drawerConfig: {
                                                width: '900px'
                                            },
                                            pageSize: 9999,
                                            paginationConfig: false,
                                            actionBtnsPosition: "top",
                                            isShowRowSelect: false,
                                            formItemLayout: {
                                                labelCol: {
                                                    xs: { span: 6 },
                                                    sm: { span: 6 }
                                                },
                                                wrapperCol: {
                                                    xs: { span: 18 },
                                                    sm: { span: 18 }
                                                }
                                            },
                                            formConfig: [
                                                {
                                                    isInTable: false,
                                                    form: {
                                                        field: 'detailsId',
                                                        type: 'string',
                                                        hide: true,
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '项目类型',
                                                        dataIndex: 'projectTypeName',
                                                        key: 'projectTypeName',
                                                        width: 150,
                                                        fixed: 'left',
                                                        onClick: 'detail'
                                                    },
                                                    form: {
                                                        field: 'projectType',
                                                        type: 'select',
                                                        optionConfig: {
                                                            label: 'label',
                                                            value: 'value'
                                                        },
                                                        optionData: [
                                                            {
                                                                label: '全部类型',
                                                                value: '0'
                                                            },
                                                            {
                                                                label: '路桥类型',
                                                                value: '1'
                                                            },
                                                            {
                                                                label: '房建类型',
                                                                value: '2'
                                                            },
                                                            {
                                                                label: '轨道类型',
                                                                value: '3'
                                                            }
                                                        ],
                                                        addDisabled: true,
                                                        editDisabled: true,
                                                        placeholder: '请选择'
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '是否收尾项目',
                                                        dataIndex: 'isClosed',
                                                        key: 'isClosed',
                                                        width: 120,
                                                        fixed: 'left',
                                                        render: (data) => {
                                                            if (data === '0') {
                                                                return '否';
                                                            }
                                                            return '是';
                                                        }
                                                    },
                                                    form: {
                                                        type: 'radio',
                                                        required: true,
                                                        optionData: [  //可为function (props)=>array
                                                            {
                                                                label: "否",
                                                                value: "0"
                                                            },
                                                            {
                                                                label: "是",
                                                                value: "1"
                                                            }
                                                        ]
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '路桥事业部权重',
                                                        dataIndex: 'roadBridgeWeight',
                                                        key: 'roadBridgeWeight',
                                                        width: 150
                                                    },
                                                    form: {
                                                        type: 'number',
                                                        required: true,
                                                        placeholder: '请输入'
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '城市房建事业部权重',
                                                        dataIndex: 'housingWeight',
                                                        key: 'housingWeight',
                                                        width: 150
                                                    },
                                                    form: {
                                                        type: 'number',
                                                        required: true,
                                                        placeholder: '请输入'
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '铁路轨道事业部权重',
                                                        dataIndex: 'trackWeight',
                                                        key: 'trackWeight',
                                                        width: 150
                                                    },
                                                    form: {
                                                        type: 'number',
                                                        required: true,
                                                        placeholder: '请输入'
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '技术质量部权重',
                                                        dataIndex: 'technicalWeight',
                                                        key: 'technicalWeight',
                                                        width: 150
                                                    },
                                                    form: {
                                                        type: 'number',
                                                        required: true,
                                                        placeholder: '请输入'
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '安全监督部权重',
                                                        dataIndex: 'safetyWeight',
                                                        key: 'safetyWeight',
                                                        width: 150
                                                    },
                                                    form: {
                                                        type: 'number',
                                                        required: true,
                                                        placeholder: '请输入'
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '经营考核部权重',
                                                        dataIndex: 'businessWeight',
                                                        key: 'businessWeight',
                                                        width: 150
                                                    },
                                                    form: {
                                                        type: 'number',
                                                        required: true,
                                                        placeholder: '请输入'
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '财务部权重',
                                                        dataIndex: 'financeWeight',
                                                        key: 'financeWeight',
                                                        width: 150
                                                    },
                                                    form: {
                                                        type: 'number',
                                                        required: true,
                                                        placeholder: '请输入'
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '物资设备部权重',
                                                        dataIndex: 'materialsWeight',
                                                        key: 'materialsWeight',
                                                        width: 150
                                                    },
                                                    form: {
                                                        type: 'number',
                                                        required: true,
                                                        placeholder: '请输入'
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '人力资源部权重',
                                                        dataIndex: 'humanResourcesWeight',
                                                        key: 'humanResourcesWeight',
                                                        width: 150
                                                    },
                                                    form: {
                                                        type: 'number',
                                                        required: true,
                                                        placeholder: '请输入'
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '法律部权重',
                                                        dataIndex: 'legalWeight',
                                                        key: 'legalWeight',
                                                        width: 150
                                                    },
                                                    form: {
                                                        type: 'number',
                                                        required: true,
                                                        placeholder: '请输入'
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '办公室权重',
                                                        dataIndex: 'officeWeight',
                                                        key: 'officeWeight',
                                                        width: 150
                                                    },
                                                    form: {
                                                        type: 'number',
                                                        required: true,
                                                        placeholder: '请输入'
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '经营考核部供应链管理部权重',
                                                        dataIndex: 'supplyChainWeight',
                                                        key: 'supplyChainWeight',
                                                        width: 200
                                                    },
                                                    form: {
                                                        type: 'number',
                                                        required: true,
                                                        placeholder: '请输入'
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '经营考核部收尾中心权重',
                                                        dataIndex: 'closingCenterWeight',
                                                        key: 'closingCenterWeight',
                                                        width: 180
                                                    },
                                                    form: {
                                                        type: 'number',
                                                        required: true,
                                                        placeholder: '请输入'
                                                    }
                                                },
                                                // {
                                                //     table: {
                                                //         title: '备注',
                                                //         dataIndex: 'remarks',
                                                //         key: 'remarks',
                                                //         width:150
                                                //     },
                                                //     form: {
                                                //         type: 'textarea',
                                                //         placeholder: '请输入'
                                                //     }
                                                // }
                                            ]
                                        }
                                    }
                                ]
                            }
                        },
                        {
                            field: "qzsw",
                            name: "qnnForm",
                            title: "权重收尾",
                            content: {
                                formConfig: [
                                    {
                                        field: "qzList",
                                        type: "qnnTable",
                                        qnnTableConfig: {
                                            fetchConfig: () => {
                                                if (rowData) {
                                                    return {
                                                        apiName: 'getZjXmJxQuarterlyWeightDetailsList',
                                                        otherParams: {
                                                            assessmentId: rowData.assessmentId,
                                                            isClosed: "1"
                                                        }
                                                    }
                                                }
                                                return {};
                                            },
                                            antd: {
                                                rowKey: 'detailsId',
                                                size: 'small',
                                                scroll:{
                                                    y: window.innerHeight - 217
                                                }
                                            },
                                            drawerConfig: {
                                                width: '900px'
                                            },
                                            pageSize: 9999,
                                            paginationConfig: false,
                                            actionBtnsPosition: "top",
                                            isShowRowSelect: false,
                                            formItemLayout: {
                                                labelCol: {
                                                    xs: { span: 6 },
                                                    sm: { span: 6 }
                                                },
                                                wrapperCol: {
                                                    xs: { span: 18 },
                                                    sm: { span: 18 }
                                                }
                                            },
                                            formConfig: [
                                                {
                                                    isInTable: false,
                                                    form: {
                                                        field: 'detailsId',
                                                        type: 'string',
                                                        hide: true,
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '项目类型',
                                                        dataIndex: 'projectTypeName',
                                                        key: 'projectTypeName',
                                                        width: 150,
                                                        fixed: 'left',
                                                        onClick: 'detail'
                                                    },
                                                    form: {
                                                        field: 'projectType',
                                                        type: 'select',
                                                        optionConfig: {
                                                            label: 'label',
                                                            value: 'value'
                                                        },
                                                        optionData: [
                                                            {
                                                                label: '全部类型',
                                                                value: '0'
                                                            },
                                                            {
                                                                label: '路桥类型',
                                                                value: '1'
                                                            },
                                                            {
                                                                label: '房建类型',
                                                                value: '2'
                                                            },
                                                            {
                                                                label: '轨道类型',
                                                                value: '3'
                                                            }
                                                        ],
                                                        addDisabled: true,
                                                        editDisabled: true,
                                                        placeholder: '请选择'
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '是否收尾项目',
                                                        dataIndex: 'isClosed',
                                                        key: 'isClosed',
                                                        width: 120,
                                                        fixed: 'left',
                                                        render: (data) => {
                                                            if (data === '0') {
                                                                return '否';
                                                            }
                                                            return '是';
                                                        }
                                                    },
                                                    form: {
                                                        type: 'radio',
                                                        required: true,
                                                        optionData: [  //可为function (props)=>array
                                                            {
                                                                label: "否",
                                                                value: "0"
                                                            },
                                                            {
                                                                label: "是",
                                                                value: "1"
                                                            }
                                                        ]
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '路桥事业部权重',
                                                        dataIndex: 'roadBridgeWeight',
                                                        key: 'roadBridgeWeight',
                                                        width: 150
                                                    },
                                                    form: {
                                                        type: 'number',
                                                        required: true,
                                                        placeholder: '请输入'
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '城市房建事业部权重',
                                                        dataIndex: 'housingWeight',
                                                        key: 'housingWeight',
                                                        width: 150
                                                    },
                                                    form: {
                                                        type: 'number',
                                                        required: true,
                                                        placeholder: '请输入'
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '铁路轨道事业部权重',
                                                        dataIndex: 'trackWeight',
                                                        key: 'trackWeight',
                                                        width: 150
                                                    },
                                                    form: {
                                                        type: 'number',
                                                        required: true,
                                                        placeholder: '请输入'
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '技术质量部权重',
                                                        dataIndex: 'technicalWeight',
                                                        key: 'technicalWeight',
                                                        width: 150
                                                    },
                                                    form: {
                                                        type: 'number',
                                                        required: true,
                                                        placeholder: '请输入'
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '安全监督部权重',
                                                        dataIndex: 'safetyWeight',
                                                        key: 'safetyWeight',
                                                        width: 150
                                                    },
                                                    form: {
                                                        type: 'number',
                                                        required: true,
                                                        placeholder: '请输入'
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '经营考核部权重',
                                                        dataIndex: 'businessWeight',
                                                        key: 'businessWeight',
                                                        width: 150
                                                    },
                                                    form: {
                                                        type: 'number',
                                                        required: true,
                                                        placeholder: '请输入'
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '财务部权重',
                                                        dataIndex: 'financeWeight',
                                                        key: 'financeWeight',
                                                        width: 150
                                                    },
                                                    form: {
                                                        type: 'number',
                                                        required: true,
                                                        placeholder: '请输入'
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '物资设备部权重',
                                                        dataIndex: 'materialsWeight',
                                                        key: 'materialsWeight',
                                                        width: 150
                                                    },
                                                    form: {
                                                        type: 'number',
                                                        required: true,
                                                        placeholder: '请输入'
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '人力资源部权重',
                                                        dataIndex: 'humanResourcesWeight',
                                                        key: 'humanResourcesWeight',
                                                        width: 150
                                                    },
                                                    form: {
                                                        type: 'number',
                                                        required: true,
                                                        placeholder: '请输入'
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '法律部权重',
                                                        dataIndex: 'legalWeight',
                                                        key: 'legalWeight',
                                                        width: 150
                                                    },
                                                    form: {
                                                        type: 'number',
                                                        required: true,
                                                        placeholder: '请输入'
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '办公室权重',
                                                        dataIndex: 'officeWeight',
                                                        key: 'officeWeight',
                                                        width: 150
                                                    },
                                                    form: {
                                                        type: 'number',
                                                        required: true,
                                                        placeholder: '请输入'
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '经营考核部供应链管理部权重',
                                                        dataIndex: 'supplyChainWeight',
                                                        key: 'supplyChainWeight',
                                                        width: 200
                                                    },
                                                    form: {
                                                        type: 'number',
                                                        required: true,
                                                        placeholder: '请输入'
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '经营考核部收尾中心权重',
                                                        dataIndex: 'closingCenterWeight',
                                                        key: 'closingCenterWeight',
                                                        width: 180
                                                    },
                                                    form: {
                                                        type: 'number',
                                                        required: true,
                                                        placeholder: '请输入'
                                                    }
                                                },
                                                // {
                                                //     table: {
                                                //         title: '备注',
                                                //         dataIndex: 'remarks',
                                                //         key: 'remarks',
                                                //         width:150
                                                //     },
                                                //     form: {
                                                //         type: 'textarea',
                                                //         placeholder: '请输入'
                                                //     }
                                                // }
                                            ]
                                        }
                                    }
                                ]
                            }
                        }
                    ] : visibleXMZBK ? [
                        {
                            field: "xmzbkfsw",
                            name: "qnnForm",
                            title: "项目指标库非收尾",
                            content: {
                                formConfig: [
                                    {
                                        field: "xmzbkList",
                                        type: "qnnTable",
                                        qnnTableConfig: {
                                            fetchConfig: () => {
                                                if (rowData) {
                                                    return {
                                                        apiName: 'getZjXmJxQuarterlyProjectDetailsList',
                                                        otherParams: {
                                                            assessmentId: rowData.assessmentId,
                                                            isClosed: "0"
                                                        }
                                                    }
                                                }
                                                return {};
                                            },
                                            wrappedComponentRef: (me) => {
                                                this.tableXMZBKFSW = me;
                                            },
                                            antd: {
                                                rowKey: 'detailsId',
                                                size: 'small',
                                                scroll:{
                                                    y: window.innerHeight - 217
                                                }
                                            },
                                            drawerConfig: {
                                                width: '900px'
                                            },
                                            pageSize: 9999,
                                            paginationConfig: false,
                                            actionBtnsPosition: "top",
                                            isShowRowSelect: XMZBKFlag,
                                            actionBtns: XMZBKFlag ? [
                                                {
                                                    name: 'del',
                                                    icon: 'delete',
                                                    type: 'danger',
                                                    label: '删除',
                                                    fetchConfig: {
                                                        apiName: 'batchDeleteUpdateZjXmJxQuarterlyProjectDetails',
                                                    },
                                                }
                                            ] : [],
                                            formConfig: [
                                                {
                                                    isInTable: false,
                                                    form: {
                                                        field: 'detailsId',
                                                        type: 'string',
                                                        hide: true,
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '考核标题',
                                                        dataIndex: 'libraryTitle',
                                                        key: 'libraryTitle',
                                                        fixed: 'left',
                                                        filter: true,
                                                        onClick: 'detail'
                                                    },
                                                    form: {
                                                        type: 'string',
                                                        placeholder: '请输入',
                                                        required: true
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '项目名称',
                                                        dataIndex: 'projectName',
                                                        key: 'projectName',
                                                    },
                                                    form: {
                                                        type: 'string',
                                                        placeholder: '请输入',
                                                        required: true
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '项目经理/收尾负责人',
                                                        dataIndex: 'managerName',
                                                        key: 'managerName',
                                                    },
                                                    form: {
                                                        type: 'string',
                                                        placeholder: '请输入',
                                                        required: true
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '考核内容',
                                                        dataIndex: 'libraryContent',
                                                        key: 'libraryContent',
                                                    },
                                                    form: {
                                                        type: 'string',
                                                        placeholder: '请输入',
                                                        required: true
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '考核责任人',
                                                        dataIndex: 'personLiableName',
                                                        key: 'personLiableName'
                                                    },
                                                    form: {
                                                        type: 'string',
                                                        placeholder: "请输入",
                                                        required: true
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '是否加分项',
                                                        dataIndex: 'isFixedScore',
                                                        key: 'isFixedScore',
                                                        width: 120,
                                                        render: (data) => {
                                                            if (data === '0') {
                                                                return '否';
                                                            }
                                                            return '是';
                                                        }
                                                    },
                                                    form: {
                                                        type: 'radio',
                                                        required: true,
                                                        optionData: [  //可为function (props)=>array
                                                            {
                                                                label: "否",
                                                                value: "0"
                                                            },
                                                            {
                                                                label: "是",
                                                                value: "1"
                                                            }
                                                        ]
                                                    }
                                                },
                                                {
                                                    isInTable: false,
                                                    form: {
                                                        label: '分数',
                                                        field: 'score',
                                                        type: 'number',
                                                        dependencies: ["isFixedScore"],
                                                        hide: function (obj) {
                                                            if (obj.form.getFieldValue("isFixedScore") === '0') {
                                                                return false;
                                                            } else {
                                                                return true;
                                                            }
                                                        },
                                                        required: true,
                                                        placeholder: '请输入'
                                                    }
                                                },
                                                {
                                                    isInTable: false,
                                                    form: {
                                                        label: '加/减分下限',
                                                        field: 'lowerLimitScore',
                                                        type: 'number',
                                                        required: true,
                                                        dependencies: ["isFixedScore"],
                                                        hide: function (obj) {
                                                            if (obj.form.getFieldValue("isFixedScore") === '1') {
                                                                return false;
                                                            } else {
                                                                return true;
                                                            }
                                                        },
                                                        placeholder: '请输入'
                                                    }
                                                },
                                                {
                                                    isInTable: false,
                                                    form: {
                                                        label: '加/减分上限',
                                                        field: 'upperLimitScore',
                                                        type: 'number',
                                                        required: true,
                                                        dependencies: ["isFixedScore"],
                                                        hide: function (obj) {
                                                            if (obj.form.getFieldValue("isFixedScore") === '1') {
                                                                return false;
                                                            } else {
                                                                return true;
                                                            }
                                                        },
                                                        placeholder: '请输入'
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '是否收尾项目',
                                                        dataIndex: 'isClosed',
                                                        key: 'isClosed',
                                                        width: 120,
                                                        render: (data) => {
                                                            if (data === '0') {
                                                                return '否';
                                                            }
                                                            return '是';
                                                        }
                                                    },
                                                    form: {
                                                        type: 'radio',
                                                        required: true,
                                                        optionData: [  //可为function (props)=>array
                                                            {
                                                                label: "否",
                                                                value: "0"
                                                            },
                                                            {
                                                                label: "是",
                                                                value: "1"
                                                            }
                                                        ]
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '备注',
                                                        dataIndex: 'remarks',
                                                        key: 'remarks'
                                                    },
                                                    form: {
                                                        type: 'textarea',
                                                        placeholder: '请输入'
                                                    }
                                                }
                                            ]
                                        }
                                    }
                                ]
                            }
                        },
                        {
                            field: "xmzbksw",
                            name: "qnnForm",
                            title: "项目指标库收尾",
                            content: {
                                formConfig: [
                                    {
                                        field: "xmzbkList",
                                        type: "qnnTable",
                                        qnnTableConfig: {
                                            fetchConfig: () => {
                                                if (rowData) {
                                                    return {
                                                        apiName: 'getZjXmJxQuarterlyProjectDetailsList',
                                                        otherParams: {
                                                            assessmentId: rowData.assessmentId,
                                                            isClosed: "1"
                                                        }
                                                    }
                                                }
                                                return {};
                                            },
                                            wrappedComponentRef: (me) => {
                                                this.tableXMZBKSW = me;
                                            },
                                            antd: {
                                                rowKey: 'detailsId',
                                                size: 'small',
                                                scroll:{
                                                    y: window.innerHeight - 217
                                                }
                                            },
                                            drawerConfig: {
                                                width: '900px'
                                            },
                                            pageSize: 9999,
                                            paginationConfig: false,
                                            actionBtnsPosition: "top",
                                            isShowRowSelect: XMZBKFlag,
                                            actionBtns: XMZBKFlag ? [
                                                {
                                                    name: 'del',
                                                    icon: 'delete',
                                                    type: 'danger',
                                                    label: '删除',
                                                    fetchConfig: {
                                                        apiName: 'batchDeleteUpdateZjXmJxQuarterlyProjectDetails',
                                                    },
                                                }
                                            ] : [],
                                            formConfig: [
                                                {
                                                    isInTable: false,
                                                    form: {
                                                        field: 'detailsId',
                                                        type: 'string',
                                                        hide: true,
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '考核标题',
                                                        dataIndex: 'libraryTitle',
                                                        key: 'libraryTitle',
                                                        fixed: 'left',
                                                        filter: true,
                                                        onClick: 'detail'
                                                    },
                                                    form: {
                                                        type: 'string',
                                                        placeholder: '请输入',
                                                        required: true
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '项目名称',
                                                        dataIndex: 'projectName',
                                                        key: 'projectName',
                                                    },
                                                    form: {
                                                        type: 'string',
                                                        placeholder: '请输入',
                                                        required: true
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '项目经理/收尾负责人',
                                                        dataIndex: 'managerName',
                                                        key: 'managerName',
                                                    },
                                                    form: {
                                                        type: 'string',
                                                        placeholder: '请输入',
                                                        required: true
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '考核内容',
                                                        dataIndex: 'libraryContent',
                                                        key: 'libraryContent',
                                                    },
                                                    form: {
                                                        type: 'string',
                                                        placeholder: '请输入',
                                                        required: true
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '考核责任人',
                                                        dataIndex: 'personLiableName',
                                                        key: 'personLiableName'
                                                    },
                                                    form: {
                                                        type: 'string',
                                                        placeholder: "请输入",
                                                        required: true
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '是否加分项',
                                                        dataIndex: 'isFixedScore',
                                                        key: 'isFixedScore',
                                                        width: 120,
                                                        render: (data) => {
                                                            if (data === '0') {
                                                                return '否';
                                                            }
                                                            return '是';
                                                        }
                                                    },
                                                    form: {
                                                        type: 'radio',
                                                        required: true,
                                                        optionData: [  //可为function (props)=>array
                                                            {
                                                                label: "否",
                                                                value: "0"
                                                            },
                                                            {
                                                                label: "是",
                                                                value: "1"
                                                            }
                                                        ]
                                                    }
                                                },
                                                {
                                                    isInTable: false,
                                                    form: {
                                                        label: '分数',
                                                        field: 'score',
                                                        type: 'number',
                                                        dependencies: ["isFixedScore"],
                                                        hide: function (obj) {
                                                            if (obj.form.getFieldValue("isFixedScore") === '0') {
                                                                return false;
                                                            } else {
                                                                return true;
                                                            }
                                                        },
                                                        required: true,
                                                        placeholder: '请输入'
                                                    }
                                                },
                                                {
                                                    isInTable: false,
                                                    form: {
                                                        label: '加/减分下限',
                                                        field: 'lowerLimitScore',
                                                        type: 'number',
                                                        required: true,
                                                        dependencies: ["isFixedScore"],
                                                        hide: function (obj) {
                                                            if (obj.form.getFieldValue("isFixedScore") === '1') {
                                                                return false;
                                                            } else {
                                                                return true;
                                                            }
                                                        },
                                                        placeholder: '请输入'
                                                    }
                                                },
                                                {
                                                    isInTable: false,
                                                    form: {
                                                        label: '加/减分上限',
                                                        field: 'upperLimitScore',
                                                        type: 'number',
                                                        required: true,
                                                        dependencies: ["isFixedScore"],
                                                        hide: function (obj) {
                                                            if (obj.form.getFieldValue("isFixedScore") === '1') {
                                                                return false;
                                                            } else {
                                                                return true;
                                                            }
                                                        },
                                                        placeholder: '请输入'
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '是否收尾项目',
                                                        dataIndex: 'isClosed',
                                                        key: 'isClosed',
                                                        width: 120,
                                                        render: (data) => {
                                                            if (data === '0') {
                                                                return '否';
                                                            }
                                                            return '是';
                                                        }
                                                    },
                                                    form: {
                                                        type: 'radio',
                                                        required: true,
                                                        optionData: [  //可为function (props)=>array
                                                            {
                                                                label: "否",
                                                                value: "0"
                                                            },
                                                            {
                                                                label: "是",
                                                                value: "1"
                                                            }
                                                        ]
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '备注',
                                                        dataIndex: 'remarks',
                                                        key: 'remarks'
                                                    },
                                                    form: {
                                                        type: 'textarea',
                                                        placeholder: '请输入'
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
                            table: {
                                title: '年份',
                                dataIndex: 'yearMonth',
                                key: 'yearMonth',
                                filter: true,
                                width: 100,
                                format:'YYYY',
                                fixed:'left'
                            },
                            form: {
                                type: 'year',
                                hide: true,
                                placeholder: '请选择'
                            }
                        },
                        {
                            table: {
                                title: '季度',
                                dataIndex: 'quarter',
                                key: 'quarter',
                                filter: true,
                                tooltip: 10,
                                width: 100,
                                type: 'select',
                                fixed:'left'
                            },
                            form: {
                                type: 'select',
                                optionConfig: {
                                    label: 'label',
                                    value: 'value'
                                },
                                optionData: [
                                    {
                                        label: '第一季度',
                                        value: '1'
                                    },
                                    {
                                        label: '第二季度',
                                        value: '2'
                                    },
                                    {
                                        label: '第三季度',
                                        value: '3'
                                    },
                                    {
                                        label: '第四季度',
                                        value: '4'
                                    }
                                ],
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
                                hide: true,
                                placeholder: '请输入'
                            }
                        },
                        {
                            table: {
                                title: '通知内容',
                                dataIndex: 'noticeContent',
                                key: 'noticeContent',
                                filter: true,
                                tooltip: 20,
                                width: 200
                            },
                            form: {
                                type: 'string',
                                hide: true,
                                placeholder: '请输入'
                            }
                        },
                        {
                            table: {
                                title: '通知状态',
                                dataIndex: 'noticeStatus',
                                key: 'noticeStatus',
                                width: 100,
                                render: (data) => {
                                    if (data === '1') {
                                        return '已发送';
                                    } else {
                                        return '未发送';
                                    }
                                }
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '考核状态',
                                dataIndex: 'assessmentStatus',
                                key: 'assessmentStatus',
                                width: 100,
                                render: (data) => {
                                    if (data === '1') {
                                        return '已发送';
                                    } else {
                                        return '未发送';
                                    }
                                }
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '修改者',
                                dataIndex: 'modifyUserName',
                                key: 'modifyUserName',
                                width: 100
                            },
                            isInForm: false
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
                            table: {
                                title: '指标库',
                                dataIndex: 'zbkDetail',
                                key: 'zbkDetail',
                                width: 100,
                                fixed:'right',
                                onClick: (obj) => {
                                    this.props.myFetch('checkZjXmJxQuarterlyLibraryDetailsConfirmStatus', { assessmentId: obj.rowData.assessmentId }).then(({ success, message, data }) => {
                                        if (success) {
                                            if (data && data.buttonFlag === 'hide') {
                                                this.setState({
                                                    visibleXZ: false,
                                                    visibleQZ: false,
                                                    visibleXMZBK: false,
                                                    rowData: obj.rowData,
                                                    ZBKFlag: false,
                                                    visibleZBK: true
                                                }, () => {
                                                    obj.btnCallbackFn.setDrawerBtns([
                                                        {
                                                            field: 'zbkCancel',
                                                            name: "cancel",
                                                            type: "dashed",
                                                            label: "取消"
                                                        }
                                                    ])
                                                    obj.btnCallbackFn.closeDrawer(true);
                                                })
                                            } else {
                                                this.setState({
                                                    visibleXZ: false,
                                                    visibleQZ: false,
                                                    visibleXMZBK: false,
                                                    rowData: obj.rowData,
                                                    visibleZBK: true,
                                                    ZBKFlag: true,
                                                }, () => {
                                                    obj.btnCallbackFn.setDrawerBtns([
                                                        {
                                                            field: 'zbkCancel',
                                                            name: "cancel",
                                                            type: "dashed",
                                                            label: "取消"
                                                        },
                                                        {
                                                            field: 'zbkSubmit',
                                                            name: 'zbkSubmit',
                                                            type: 'primary',
                                                            label: '确认',
                                                            onClick: (objZBK) => {
                                                                setTimeout(() => {
                                                                    let ZBKFSWData = this.tableZBKFSW.state.data;
                                                                    let ZBKSWData = this.tableZBKSW.state.data;
                                                                    let zbkData = ZBKFSWData.concat(ZBKSWData);
                                                                    let setBtnsLoading = objZBK.btnCallbackFn.setBtnsLoading;
                                                                    setBtnsLoading('add', 'zbkSubmit');
                                                                    this.props.myFetch('batchConfirmZjXmJxQuarterlyLibraryDetails', zbkData).then(({ success, message }) => {
                                                                        if (success) {
                                                                            setBtnsLoading('remove', 'zbkSubmit');
                                                                            obj.btnCallbackFn.closeDrawer(false);
                                                                            obj.btnCallbackFn.refresh();
                                                                        } else {
                                                                            setBtnsLoading('remove', 'zbkSubmit');
                                                                            Msg.error(message);
                                                                        }
                                                                    });
                                                                }, 1000)
                                                            }
                                                        }
                                                    ])
                                                    obj.btnCallbackFn.closeDrawer(true);
                                                })
                                            }
                                        } else {
                                            Msg.error(message);
                                        }
                                    });
                                },
                                render: () => {
                                    return '详情';
                                }
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '权重',
                                dataIndex: 'QZDetail',
                                key: 'QZDetail',
                                width: 100,
                                fixed:'right',
                                onClick: (obj) => {
                                    this.setState({
                                        visibleZBK: false,
                                        visibleXZ: false,
                                        visibleXMZBK: false,
                                        rowData: obj.rowData,
                                        visibleQZ: true
                                    }, () => {
                                        obj.btnCallbackFn.setDrawerBtns([
                                            {
                                                field: 'qzCancel',
                                                name: "cancel",
                                                type: "dashed",
                                                label: "取消"
                                            }
                                        ])
                                        obj.btnCallbackFn.closeDrawer(true);
                                    })
                                },
                                render: () => {
                                    return '详情';
                                }
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '项目指标库',
                                dataIndex: 'XMZBKDetail',
                                key: 'XMZBKDetail',
                                width: 100,
                                fixed:'right',
                                onClick: (obj) => {
                                    this.props.myFetch('checkZjXmJxQuarterlyProjectDetailsConfirmStatus', { assessmentId: obj.rowData.assessmentId }).then(({ success, message, data }) => {
                                        if (success) {
                                            if (data && data.buttonFlag === 'hide') {
                                                this.setState({
                                                    visibleXZ: false,
                                                    visibleQZ: false,
                                                    visibleZBK: false,
                                                    rowData: obj.rowData,
                                                    XMZBKFlag: false,
                                                    visibleXMZBK: true
                                                }, () => {
                                                    obj.btnCallbackFn.setDrawerBtns([
                                                        {
                                                            field: 'xmzbkCancel',
                                                            name: "cancel",
                                                            type: "dashed",
                                                            label: "取消"
                                                        }
                                                    ])
                                                    obj.btnCallbackFn.closeDrawer(true);
                                                })
                                            } else {
                                                this.setState({
                                                    visibleXZ: false,
                                                    visibleQZ: false,
                                                    visibleZBK: false,
                                                    rowData: obj.rowData,
                                                    visibleXMZBK: true,
                                                    XMZBKFlag: true,
                                                }, () => {
                                                    obj.btnCallbackFn.setDrawerBtns([
                                                        {
                                                            field: 'xmzbkCancel',
                                                            name: "cancel",
                                                            type: "dashed",
                                                            label: "取消"
                                                        },
                                                        {
                                                            field: 'xmzbkSubmit',
                                                            name: 'xmzbkSubmit',
                                                            type: 'primary',
                                                            label: '确认',
                                                            onClick: (objXMZBK) => {
                                                                setTimeout(() => {
                                                                    let XMZBKFSWData = this.tableXMZBKFSW.state.data;
                                                                    let XMZBKSWData = this.tableXMZBKSW.state.data;
                                                                    let xmzbkData = XMZBKFSWData.concat(XMZBKSWData);
                                                                    let setBtnsLoading = objXMZBK.btnCallbackFn.setBtnsLoading;
                                                                    setBtnsLoading('add', 'zbkSubmit');
                                                                    this.props.myFetch('batchConfirmZjXmJxQuarterlyProjectDetails', xmzbkData).then(({ success, message }) => {
                                                                        if (success) {
                                                                            setBtnsLoading('remove', 'zbkSubmit');
                                                                            obj.btnCallbackFn.closeDrawer(false);
                                                                            obj.btnCallbackFn.refresh();
                                                                        } else {
                                                                            setBtnsLoading('remove', 'zbkSubmit');
                                                                            Msg.error(message);
                                                                        }
                                                                    });
                                                                }, 1000)
                                                            }
                                                        }
                                                    ])
                                                    obj.btnCallbackFn.closeDrawer(true);
                                                })
                                            }
                                        } else {
                                            Msg.error(message);
                                        }
                                    });
                                },
                                render: () => {
                                    return '详情';
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