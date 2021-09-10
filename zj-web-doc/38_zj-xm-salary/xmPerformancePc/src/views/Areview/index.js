import React, { Component } from "react";
import QnnTable from '../modules/qnn-table';
import { message as Msg, Modal } from 'antd';
import moment from 'moment';
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
            xs: { span: 2 },
            sm: { span: 2 }
        },
        wrapperCol: {
            xs: { span: 22 },
            sm: { span: 22 }
        }
    }
}
class Index extends Component {
    constructor() {
        super();
        this.state = {
            visibleXZ: false,
            visibleRW: false,
            visibleZB: false,
            visibleXM: false,
            rowData: null
        }
    }
    render() {
        const { visibleRW, visibleZB, visibleXM, visibleXZ, rowData } = this.state;
        const { projectId, projectName } = this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany;
        return (
            <div>
                <QnnTable
                    fetch={this.props.myFetch}
                    upload={this.props.myUpload}
                    headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                    {...config}
                    fetchConfig={{
                        apiName: 'getZjXmJxMonthlyAssessmentList',
                        otherParams: {
                            projectId: projectId
                        }
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
                                    visibleZB: false,
                                    visibleRW: false,
                                    visibleXM: false,
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
                                                apiName: 'cascadeAddZjXmJxMonthlyAssessment'
                                            }
                                        }
                                    ])
                                    obj.btnCallbackFn.closeDrawer(true);
                                })
                            }
                        },
                        {
                            name: 'editDiy',
                            icon: 'edit',
                            type: 'primary',
                            label: '修改',
                            disabled: (obj) => {
                                if (obj.btnCallbackFn.getSelectedRows().length > 0) {
                                    return false;
                                } else {
                                    return true;
                                }
                            },
                            isValidate: false,
                            willExecute: (obj) => {
                                obj.btnCallbackFn.clearSelectedRows();
                                if (obj.selectedRows.length === 1) {
                                    if (obj.selectedRows[0].noticeStatus === '1') {
                                        Msg.error('该数据已发送，不可修改！');
                                    } else {
                                        this.setState({
                                            visibleZB: false,
                                            visibleRW: false,
                                            visibleXM: false,
                                            rowData: obj.selectedRows[0],
                                            visibleXZ: true
                                        }, () => {
                                            obj.btnCallbackFn.setDrawerBtns([
                                                {
                                                    field: 'editDiyCancel',
                                                    name: "cancel",
                                                    type: "dashed",
                                                    label: "取消"
                                                },
                                                {
                                                    field: 'editDiySubmit',
                                                    name: 'submit',//内置add del
                                                    type: 'primary',//类型  默认 primary
                                                    label: '提交',//提交数据并且关闭右边抽屉 
                                                    fetchConfig: {//ajax配置
                                                        apiName: 'updateZjXmJxMonthlyAssessment'
                                                    }
                                                }
                                            ])
                                            obj.btnCallbackFn.closeDrawer(true);
                                        })
                                    }
                                } else {
                                    Msg.error('请选择一条修改数据！');
                                }
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
                                obj.btnCallbackFn.clearSelectedRows();
                                if (obj.selectedRows.length === 1) {
                                    if (obj.selectedRows[0].noticeStatus === '1') {
                                        Msg.error('该数据已发送，请勿重复发送！');
                                    } else {
                                        confirm({
                                            content: '您确认发送该信息吗?',
                                            centered: true,
                                            onOk: () => {
                                                this.props.myFetch('sendZjXmJxMonthlyAssessmentNotice', ...obj.selectedRows).then(({ success, message, data }) => {
                                                    if (success) {
                                                        Msg.success(message);
                                                        obj.btnCallbackFn.refresh();
                                                    } else {
                                                        Msg.error(message);
                                                    }
                                                });
                                            }
                                        });
                                    }
                                } else {
                                    Msg.error('请选择一条数据发送!');
                                }
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
                                confirm({
                                    content: '您确认删除该信息吗?',
                                    centered: true,
                                    onOk: () => {
                                        this.props.myFetch('batchDeleteUpdateZjXmJxMonthlyAssessment', obj.selectedRows).then(({ success, message, data }) => {
                                            if (success) {
                                                obj.btnCallbackFn.clearSelectedRows();
                                                obj.btnCallbackFn.refresh();
                                            } else {
                                                Msg.error(message);
                                            }
                                        });
                                    }
                                });
                            }
                        }
                    ]}
                    tabs={visibleXZ ? [
                        {
                            field: "increased",
                            name: "qnnForm",
                            title: "基础信息",
                            content: {
                                fetchConfig: rowData ? {
                                    apiName: 'getZjXmJxMonthlyAssessmentDetails',
                                    otherParams: {
                                        assessmentId: rowData.assessmentId
                                    }
                                } : {},
                                formConfig: [
                                    {
                                        field: 'assessmentId',
                                        type: 'string',
                                        hide: true,
                                    },
                                    {
                                        field: 'projectId',
                                        type: 'string',
                                        initialValue: projectId,
                                        hide: true,
                                    },
                                    {
                                        field: 'projectName',
                                        type: 'string',
                                        initialValue: projectName,
                                        hide: true,
                                    },
                                    {
                                        label: '年月',
                                        field: 'yearMonth',
                                        type: 'month',
                                        required: true,
                                        placeholder: "请选择"
                                    },
                                    {
                                        label: '通知内容',
                                        field: 'assessmentTitle',
                                        type: 'string',
                                        required: true,
                                        placeholder: "请选择"
                                    }
                                ]
                            }
                        }
                    ] : visibleRW ? [
                        {
                            field: "rwkh",
                            name: "qnnForm",
                            title: "任务考核",
                            content: {
                                fetchConfig: rowData ? {
                                    apiName: 'getZjXmJxMonthlyAssessmentTaskDetails',
                                    otherParams: {
                                        assessmentId: rowData.assessmentId
                                    }
                                } : {},
                                formConfig: [
                                    {
                                        field: 'assessmentId',
                                        label: '主键',
                                        type: 'string',
                                        hide: true
                                    },
                                    {
                                        field: 'projectId',
                                        type: 'string',
                                        hide: true,
                                    },
                                    {
                                        field: 'projectName',
                                        type: 'string',
                                        hide: true,
                                    },
                                    {
                                        field: 'yearMonth',
                                        label: '年月',
                                        type: 'month',
                                        disabled: true,
                                        placeholder: '请输入'
                                    },
                                    {
                                        field: 'assessmentTitle',
                                        label: '通知内容',
                                        type: 'string',
                                        disabled: true,
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
                                        label: '被考核人',
                                        field: 'deputyArray',
                                        type: 'treeSelect',
                                        treeSelectOption: {
                                            selectType: "2",
                                            fetchConfig: {//配置后将会去请求下拉选项数据
                                                apiName: 'getSysDepartmentUserAllTree'
                                            },
                                            search: true,
                                            searchPlaceholder: '姓名、账号、电话',
                                            // searchApi:'getSysDepartmentUserAllTree',  //搜索时调用的api  [string]
                                            searchParamsKey: 'search',//搜索文字的K 默认是'searchText'   [string]
                                            searchOtherParams: { pageSize: 999 }//搜索时的其他参数  [object]
                                        },
                                        disabled: rowData.noticeStatus === '1' ? true : false
                                    },
                                    {
                                        type: "component",
                                        field: "component2",
                                        Component: obj => {
                                            return (
                                                <div style={{ background: '#f0f2f5', padding: '8px 12px' }}>
                                                    部门负责人
                                                </div>
                                            );
                                        }
                                    },
                                    {
                                        label: '被考核人',
                                        field: 'leaderArray',
                                        type: 'treeSelect',
                                        treeSelectOption: {
                                            selectType: "2",
                                            fetchConfig: {//配置后将会去请求下拉选项数据
                                                apiName: 'getSysDepartmentUserAllTree'
                                            },
                                            search: true,
                                            searchPlaceholder: '姓名、账号、电话',
                                            // searchApi:'getSysDepartmentUserAllTree',  //搜索时调用的api  [string]
                                            searchParamsKey: 'search',//搜索文字的K 默认是'searchText'   [string]
                                            searchOtherParams: { pageSize: 999 }//搜索时的其他参数  [object]
                                        },
                                        disabled: rowData.noticeStatus === '1' ? true : false,
                                        required: true
                                    },
                                    {
                                        type: "component",
                                        field: "component3",
                                        Component: obj => {
                                            return (
                                                <div style={{ background: '#f0f2f5', padding: '8px 12px' }}>
                                                    普通员工
                                                </div>
                                            );
                                        }
                                    },
                                    {
                                        label: '被考核人',
                                        field: 'employeeArray',
                                        type: 'treeSelect',
                                        treeSelectOption: {
                                            selectType: "2",
                                            fetchConfig: {//配置后将会去请求下拉选项数据
                                                apiName: 'getSysDepartmentUserAllTree'
                                            },
                                            search: true,
                                            searchPlaceholder: '姓名、账号、电话',
                                            // searchApi:'getSysDepartmentUserAllTree',  //搜索时调用的api  [string]
                                            searchParamsKey: 'search',//搜索文字的K 默认是'searchText'   [string]
                                            searchOtherParams: { pageSize: 999 }//搜索时的其他参数  [object]
                                        },
                                        disabled: rowData.noticeStatus === '1' ? true : false,
                                        required: true
                                    },
                                    {
                                        field: 'taskRemarks',
                                        label: '备注',
                                        type: 'textarea',
                                        disabled: rowData.noticeStatus === '1' ? true : false,
                                        placeholder: '请输入'
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
                                fetchConfig:rowData ? {
                                    apiName: 'getZjXmJxMonthlyAssessmentDetails',
                                    otherParams: {
                                        assessmentId: rowData.assessmentId
                                    }
                                } : {},
                                formConfig: [
                                    {
                                        field: 'assessmentId',
                                        label: '主键',
                                        type: 'string',
                                        hide: true
                                    },
                                    {
                                        field: 'projectId',
                                        type: 'string',
                                        hide: true,
                                    },
                                    {
                                        field: 'projectName',
                                        type: 'string',
                                        hide: true,
                                    },
                                    {
                                        field: 'yearMonth',
                                        label: '年月',
                                        type: 'month',
                                        disabled: true,
                                        placeholder: '请输入'
                                    },
                                    {
                                        field: 'assessmentTitle',
                                        label: '通知内容',
                                        type: 'string',
                                        disabled: true,
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
                                        field: "deputyPeerWeight",
                                        label: "同级权重",
                                        type: "number",
                                        required: true,
                                        disabled: rowData.noticeStatus === '1' ? true : false,
                                        span: 8,
                                        formItemLayout: {
                                            labelCol: {
                                                sm: { span: 9 }
                                            },
                                            wrapperCol: {
                                                sm: { span: 15 }
                                            }
                                        },
                                        placeholder: "请输入",
                                    },
                                    {
                                        field: "deputySubordinateWeight",
                                        label: "下级权重",
                                        type: "number",
                                        required: true,
                                        disabled: rowData.noticeStatus === '1' ? true : false,
                                        span: 8,
                                        formItemLayout: {
                                            labelCol: {
                                                sm: { span: 9 }
                                            },
                                            wrapperCol: {
                                                sm: { span: 15 }
                                            }
                                        },
                                        placeholder: "请输入",
                                    },
                                    {
                                        label: '被考核人',
                                        field: 'deputyArray',
                                        type: 'treeSelect',
                                        treeSelectOption: {
                                            selectType: "2",
                                            fetchConfig: {//配置后将会去请求下拉选项数据
                                                apiName: 'getSysDepartmentUserAllTree'
                                            },
                                            search: true,
                                            searchPlaceholder: '姓名、账号、电话',
                                            // searchApi:'getSysDepartmentUserAllTree',  //搜索时调用的api  [string]
                                            searchParamsKey: 'search',//搜索文字的K 默认是'searchText'   [string]
                                            searchOtherParams: { pageSize: 999 }//搜索时的其他参数  [object]
                                        },
                                        disabled: rowData.noticeStatus === '1' ? true : false
                                    },
                                    {
                                        type: "component",
                                        field: "component2",
                                        Component: obj => {
                                            return (
                                                <div style={{ background: '#f0f2f5', padding: '8px 12px' }}>
                                                    部门负责人
                                                </div>
                                            );
                                        }
                                    },
                                    {
                                        field: "leaderSuperiorWeight",
                                        label: "上级权重",
                                        type: "number",
                                        required: true,
                                        disabled: rowData.noticeStatus === '1' ? true : false,
                                        span: 8,
                                        formItemLayout: {
                                            labelCol: {
                                                sm: { span: 9 }
                                            },
                                            wrapperCol: {
                                                sm: { span: 15 }
                                            }
                                        },
                                        placeholder: "请输入",
                                    },
                                    {
                                        field: "leaderPeerWeight",
                                        label: "同级权重",
                                        type: "number",
                                        required: true,
                                        disabled: rowData.noticeStatus === '1' ? true : false,
                                        span: 8,
                                        formItemLayout: {
                                            labelCol: {
                                                sm: { span: 9 }
                                            },
                                            wrapperCol: {
                                                sm: { span: 15 }
                                            }
                                        },
                                        placeholder: "请输入",
                                    },
                                    {
                                        field: "leaderSubordinateWeight",
                                        label: "下级权重",
                                        type: "number",
                                        required: true,
                                        disabled: rowData.noticeStatus === '1' ? true : false,
                                        span: 8,
                                        formItemLayout: {
                                            labelCol: {
                                                sm: { span: 9 }
                                            },
                                            wrapperCol: {
                                                sm: { span: 15 }
                                            }
                                        },
                                        placeholder: "请输入",
                                    },
                                    {
                                        label: '被考核人',
                                        field: 'leaderArray',
                                        type: 'treeSelect',
                                        treeSelectOption: {
                                            selectType: "2",
                                            fetchConfig: {//配置后将会去请求下拉选项数据
                                                apiName: 'getSysDepartmentUserAllTree'
                                            },
                                            search: true,
                                            searchPlaceholder: '姓名、账号、电话',
                                            // searchApi:'getSysDepartmentUserAllTree',  //搜索时调用的api  [string]
                                            searchParamsKey: 'search',//搜索文字的K 默认是'searchText'   [string]
                                            searchOtherParams: { pageSize: 999 }//搜索时的其他参数  [object]
                                        },
                                        disabled: rowData.noticeStatus === '1' ? true : false,
                                        required: true
                                    },
                                    {
                                        type: "component",
                                        field: "component3",
                                        Component: obj => {
                                            return (
                                                <div style={{ background: '#f0f2f5', padding: '8px 12px' }}>
                                                    普通员工
                                                </div>
                                            );
                                        }
                                    },
                                    {
                                        field: "employeeSuperiorWeight",
                                        label: "上级权重",
                                        type: "number",
                                        required: true,
                                        disabled: rowData.noticeStatus === '1' ? true : false,
                                        span: 8,
                                        formItemLayout: {
                                            labelCol: {
                                                sm: { span: 9 }
                                            },
                                            wrapperCol: {
                                                sm: { span: 15 }
                                            }
                                        },
                                        placeholder: "请输入",
                                    },
                                    {
                                        field: "employeePeerWeight",
                                        label: "同级权重",
                                        type: "number",
                                        required: true,
                                        disabled: rowData.noticeStatus === '1' ? true : false,
                                        span: 8,
                                        formItemLayout: {
                                            labelCol: {
                                                sm: { span: 9 }
                                            },
                                            wrapperCol: {
                                                sm: { span: 15 }
                                            }
                                        },
                                        placeholder: "请输入",
                                    },
                                    {
                                        label: '被考核人',
                                        field: 'employeeArray',
                                        type: 'treeSelect',
                                        treeSelectOption: {
                                            selectType: "2",
                                            fetchConfig: {//配置后将会去请求下拉选项数据
                                                apiName: 'getSysDepartmentUserAllTree'
                                            },
                                            search: true,
                                            searchPlaceholder: '姓名、账号、电话',
                                            // searchApi:'getSysDepartmentUserAllTree',  //搜索时调用的api  [string]
                                            searchParamsKey: 'search',//搜索文字的K 默认是'searchText'   [string]
                                            searchOtherParams: { pageSize: 999 }//搜索时的其他参数  [object]
                                        },
                                        disabled: rowData.noticeStatus === '1' ? true : false,
                                        required: true
                                    },
                                    {
                                        field: 'peripheryRemarks',
                                        label: '备注',
                                        type: 'textarea',
                                        disabled: rowData.noticeStatus === '1' ? true : false,
                                        placeholder: '请输入'
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
                                fetchConfig:rowData ? {
                                    apiName: 'getZjXmJxMonthlyAssessmentPrincipalDetails',
                                    otherParams: {
                                        assessmentId: rowData.assessmentId
                                    }
                                } : {},
                                formConfig: [
                                    {
                                        field: 'assessmentId',
                                        label: '主键',
                                        type: 'string',
                                        hide: true
                                    },
                                    {
                                        field: 'projectId',
                                        type: 'string',
                                        hide: true,
                                    },
                                    {
                                        field: 'projectName',
                                        type: 'string',
                                        hide: true,
                                    },
                                    {
                                        field: 'yearMonth',
                                        label: '年月',
                                        type: 'month',
                                        disabled: true,
                                        placeholder: '请输入'
                                    },
                                    {
                                        field: 'assessmentTitle',
                                        label: '通知内容',
                                        type: 'string',
                                        disabled: true,
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
                                        label: '被考核人',
                                        field: 'deputyArray',
                                        type: 'treeSelect',
                                        treeSelectOption: {
                                            selectType: "2",
                                            fetchConfig: {//配置后将会去请求下拉选项数据
                                                apiName: 'getSysDepartmentUserAllTree'
                                            },
                                            search: true,
                                            searchPlaceholder: '姓名、账号、电话',
                                            // searchApi:'getSysDepartmentUserAllTree',  //搜索时调用的api  [string]
                                            searchParamsKey: 'search',//搜索文字的K 默认是'searchText'   [string]
                                            searchOtherParams: { pageSize: 999 }//搜索时的其他参数  [object]
                                        },
                                        disabled: rowData.noticeStatus === '1' ? true : false
                                    },
                                    {
                                        type: "component",
                                        field: "component2",
                                        Component: obj => {
                                            return (
                                                <div style={{ background: '#f0f2f5', padding: '8px 12px' }}>
                                                    部门负责人
                                                </div>
                                            );
                                        }
                                    },
                                    {
                                        label: '被考核人',
                                        field: 'leaderArray',
                                        type: 'treeSelect',
                                        treeSelectOption: {
                                            selectType: "2",
                                            fetchConfig: {//配置后将会去请求下拉选项数据
                                                apiName: 'getSysDepartmentUserAllTree'
                                            },
                                            search: true,
                                            searchPlaceholder: '姓名、账号、电话',
                                            // searchApi:'getSysDepartmentUserAllTree',  //搜索时调用的api  [string]
                                            searchParamsKey: 'search',//搜索文字的K 默认是'searchText'   [string]
                                            searchOtherParams: { pageSize: 999 }//搜索时的其他参数  [object]
                                        },
                                        disabled: rowData.noticeStatus === '1' ? true : false,
                                        required: true
                                    },
                                    {
                                        type: "component",
                                        field: "component3",
                                        Component: obj => {
                                            return (
                                                <div style={{ background: '#f0f2f5', padding: '8px 12px' }}>
                                                    普通员工
                                                </div>
                                            );
                                        }
                                    },
                                    {
                                        label: '被考核人',
                                        field: 'employeeArray',
                                        type: 'treeSelect',
                                        treeSelectOption: {
                                            selectType: "2",
                                            fetchConfig: {//配置后将会去请求下拉选项数据
                                                apiName: 'getSysDepartmentUserAllTree'
                                            },
                                            search: true,
                                            // useCollect: true,
                                            // collectApi: "appGetSysFrequentContactsList",  //查询收藏人员     接受后台参数[{xx:xxx,...}]
                                            // collectApiByAdd: "appAddSysFrequentContacts", //新增收藏人员   传给后台的参数[{xx:xxx,...}]
                                            // collectApiByDel: "appRemoveSysFrequentContacts", //删除收藏人员
                                            searchPlaceholder: '姓名、账号、电话',
                                            // searchApi:'getSysDepartmentUserAllTree',  //搜索时调用的api  [string]
                                            searchParamsKey: 'search',//搜索文字的K 默认是'searchText'   [string]
                                            searchOtherParams: { pageSize: 999 }//搜索时的其他参数  [object]
                                        },
                                        disabled: rowData.noticeStatus === '1' ? true : false,
                                        required: true
                                    },
                                    {
                                        field: 'principalRemarks',
                                        label: '备注',
                                        type: 'textarea',
                                        disabled: rowData.noticeStatus === '1' ? true : false,
                                        placeholder: '请输入'
                                    }
                                ]
                            }
                        }
                    ] : []}
                    formConfig={[
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
                                title: '通知内容',
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
                                title: '状态',
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
                                title: '任务考核',
                                dataIndex: 'taskStatus',
                                key: 'taskStatus',
                                width: 100,
                                onClick: (obj) => {
                                    this.setState({
                                        visibleZB: false,
                                        visibleXZ: false,
                                        visibleXM: false,
                                        rowData: obj.rowData,
                                        visibleRW: true
                                    }, () => {
                                        if (obj.rowData.noticeStatus === '1') {
                                            obj.btnCallbackFn.setDrawerBtns([
                                                {
                                                    field: 'rwkhCancel',
                                                    name: "cancel",
                                                    type: "dashed",
                                                    label: "取消"
                                                }
                                            ])
                                        } else {
                                            obj.btnCallbackFn.setDrawerBtns([
                                                {
                                                    field: 'rwkhCancel',
                                                    name: "cancel",
                                                    type: "dashed",
                                                    label: "取消"
                                                },
                                                {
                                                    field: 'rwkhSubmit',
                                                    name: 'submit',//内置add del
                                                    type: 'primary',//类型  默认 primary
                                                    label: '提交',//提交数据并且关闭右边抽屉 
                                                    fetchConfig: {//ajax配置
                                                        apiName: 'submitZjXmJxMonthlyAssessmentForTask'
                                                    }
                                                }
                                            ])
                                        }
                                        obj.btnCallbackFn.closeDrawer(true);
                                    })
                                },
                                render: () => {
                                    return '设置';
                                }
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '周边考核',
                                dataIndex: 'peripheryStatus',
                                key: 'peripheryStatus',
                                width: 100,
                                onClick: (obj) => {
                                    this.setState({
                                        visibleRW: false,
                                        visibleXZ: false,
                                        visibleXM: false,
                                        rowData: obj.rowData,
                                        visibleZB: true
                                    }, () => {
                                        if (obj.rowData.noticeStatus === '1') {
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
                                                    field: 'zbkhSubmit',
                                                    name: 'submit',//内置add del
                                                    type: 'primary',//类型  默认 primary
                                                    label: '提交',//提交数据并且关闭右边抽屉 
                                                    fetchConfig: {//ajax配置
                                                        apiName: 'submitZjXmJxMonthlyAssessmentForPeriphery'
                                                    }
                                                }
                                            ])
                                        }
                                        obj.btnCallbackFn.closeDrawer(true);
                                    })
                                },
                                render: () => {
                                    return '设置';
                                }
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '项目正职',
                                dataIndex: 'principalStatus',
                                key: 'principalStatus',
                                width: 100,
                                onClick: (obj) => {
                                    this.setState({
                                        visibleRW: false,
                                        visibleXZ: false,
                                        visibleZB: false,
                                        rowData: obj.rowData,
                                        visibleXM: true
                                    }, () => {
                                        if (obj.rowData.noticeStatus === '1') {
                                            obj.btnCallbackFn.setDrawerBtns([
                                                {
                                                    field: 'xmzzCancel',
                                                    name: "cancel",
                                                    type: "dashed",
                                                    label: "取消"
                                                }
                                            ])
                                        } else {
                                            obj.btnCallbackFn.setDrawerBtns([
                                                {
                                                    field: 'xmzzCancel',
                                                    name: "cancel",
                                                    type: "dashed",
                                                    label: "取消"
                                                },
                                                {
                                                    field: 'xmzzSubmit',
                                                    name: 'submit',//内置add del
                                                    type: 'primary',//类型  默认 primary
                                                    label: '提交',//提交数据并且关闭右边抽屉 
                                                    fetchConfig: {//ajax配置
                                                        apiName: 'submitZjXmJxMonthlyAssessmentForPrincipal'
                                                    }
                                                }
                                            ])
                                        }
                                        obj.btnCallbackFn.closeDrawer(true);
                                    })
                                },
                                render: () => {
                                    return '设置';
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