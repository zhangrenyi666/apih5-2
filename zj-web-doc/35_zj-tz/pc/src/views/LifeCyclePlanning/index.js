import React, { Component } from "react";
import QnnTable from "../modules/qnn-table";
import { push } from "react-router-redux";
import s from "./style.less";
import { message as Msg, Modal } from "antd";
import FlowByLifeCyc from './form';
const confirm = Modal.confirm;
const config = {

    antd: {
        rowKey: function (row) {
            return row.lifeCycleId
        },
        size: 'small'
    },
    drawerConfig: {
        width: '1000px'
    },
    paginationConfig: {
        position: 'bottom'
    },
    firstRowIsSearch: false,
    isShowRowSelect: true,
    formItemLayout: {
        labelCol: {
            xs: { span: 24 },
            sm: { span: 3 }
        },
        wrapperCol: {
            xs: { span: 24 },
            sm: { span: 21 }
        }
    }
};
class index extends Component {
    constructor(props) {
        super(props);
        this.state = {
            projectId: props.loginAndLogoutInfo.loginInfo.userInfo.curCompany.projectId,
        }
    }
    componentDidMount() {

    }
    render() {
        const { projectId } = this.state;
        return (
            <div className={s.root}>
                <QnnTable
                    {...this.props}
                    fetch={this.props.myFetch}
                    upload={this.props.myUpload}
                    headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                    wrappedComponentRef={(me) => {
                        this.table = me;
                    }}
                    {...config}
                    fetchConfig={{
                        apiName: 'getZjTzLifeCycleList',
                        otherParams: {
                            projectId: projectId
                        }
                    }}
                    formConfig={[
                        {
                            isInTable: false,
                            form: {
                                field: 'lifeCycleId',
                                type: 'string',
                                hide: true,
                            }
                        },
                        {
                            isInSearch: false,
                            table: {
                                title: '项目名称',
                                dataIndex: 'projectName',
                                key: 'projectName',
                                fixed: 'left',
                                width: 300,
                                tooltip: 23,
                                onClick: 'detail',
                                filter: true,
                                
                            },
                            form: {
                                field: 'projectId',
                                type: 'select',
                                editDisabled: true,
                                addDisabled: true,
                                showSearch:true,
                                initialValue: projectId,
                                required: true,
                                optionConfig: {
                                    label: 'projectName',
                                    value: 'projectId'
                                },
                                fetchConfig: {
                                    apiName: "getZjTzProManageList"
                                },
                                placeholder: '请输入',
                                formItemLayoutForm: {
                                    labelCol: {
                                        xs: { span: 24 },
                                        sm: { span: 4 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 24 },
                                        sm: { span: 20 }
                                    }
                                },
                            },
                        },

                        {
                            table: {
                                title: '状态',
                                width: 160,
                                dataIndex: 'statusId',
                                key: 'statusId',
                                type: 'select',
                                filter: true
                            },
                            isInForm: false,
                            form: {
                                type: 'select',
                                field: 'statusId',
                                optionData: [
                                    {
                                        label: "未上报",
                                        value: "0"
                                    },
                                    {
                                        label: "已上报",
                                        value: "1"
                                    },
                                    {
                                        label: "已退回",
                                        value: "3"
                                    },
                                    {
                                        label: "已通过",
                                        value: "2"
                                    },
                                    {
                                        label: "评审中",
                                        value: "4"
                                    }
                                ]
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: '备注',
                                field: 'remark',
                                type: 'textarea',
                                formItemLayoutForm: {
                                    labelCol: {
                                        xs: { span: 24 },
                                        sm: { span: 4 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 24 },
                                        sm: { span: 20 }
                                    }
                                },
                            }
                        },
                        {
                            table: {
                                title: '登记日期',
                                dataIndex: 'registerDate',
                                format: 'YYYY-MM-DD',
                                key: 'registerDate',
                                width: 100,
                            },
                            isInForm: false
                        },
                        {
                            isInTable: false,
                            form: {
                                label: '全生命周期策划附件',
                                field: 'zjTzFileList',
                                showDownloadIcon: true,//是否显示下载按钮
                                onPreview: "bind:_docFilesByOfficeUrl",//365显示
                                
                                type: 'files',
                                fetchConfig: {
                                    apiName: window.configs.domain + 'upload',
                                    otherParams: {
                                        name: '全生命周期策划'
                                    }
                                },
                                formItemLayoutForm: {
                                    labelCol: {
                                        xs: { span: 24 },
                                        sm: { span: 4 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 24 },
                                        sm: { span: 20 }
                                    }
                                },
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                field: 'registerDate',
                                type: 'date',
                                label: '登记日期',
                                required: true,
                                addDisabled: true,
                                editDisabled: true,
                                placeholder: '请输入',
                                spanForm: 12,
                                formItemLayoutForm: {
                                    labelCol: {
                                        xs: { span: 24 },
                                        sm: { span: 8 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 24 },
                                        sm: { span: 16 }
                                    }
                                },
                                initialValue: () => {
                                    return new Date()
                                }
                            },
                        },
                        {
                            isInTable: false,
                            form: {
                                field: 'registerPerson',
                                type: 'string',
                                label: '登记用户',
                                required: true,
                                addDisabled: true,
                                editDisabled: true,
                                placeholder: '请输入',
                                spanForm: 12,
                                formItemLayoutForm: {
                                    labelCol: {
                                        xs: { span: 24 },
                                        sm: { span: 8 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 24 },
                                        sm: { span: 16 }
                                    }
                                },
                                initialValue: (obj) => {
                                    return obj.loginAndLogoutInfo.loginInfo.userInfo.realName
                                }
                            },
                        },
                        {
                            isInForm: false,
                            table: {
                                title: "评审意见",
                                // fixed: 'right',
                                dataIndex: 'action',
                                key: 'action',
                                align: "center",
                                noHaveSearchInput: true,
                                showType: "tile",
                                width: 120,
                                btns: [
                                    {
                                        name: 'PolicyDetail',
                                        render: (rowData) => {
                                            return '<a>评审详情</a>';
                                        },
                                        onClick: (obj) => {
                                            const { mainModule } = obj.props.myPublic.appInfo;
                                            const { lifeCycleId } = obj.rowData;
                                            obj.props.dispatch(
                                                push(`${mainModule}LifeCyclePlanningDetail/${lifeCycleId}/${'1'}`)
                                            )
                                        },
                                    }
                                ]
                            }
                        },
                        {
                            isInForm: false,
                            table: {
                                title: "策划变更查看",
                                // fixed: 'right',
                                dataIndex: 'action',
                                key: 'action',
                                align: "center",
                                noHaveSearchInput: true,
                                showType: "tile",
                                width: 120,
                                btns: [
                                    {
                                        name: 'PolicyDetail',
                                        render: (rowData) => {
                                            if (rowData.rowData.showFlag === '1') {
                                                return '<a>有变更</a>';
                                            } else {
                                                return ''
                                            }
                                        },
                                        onClick: (obj) => {
                                            const { mainModule } = obj.props.myPublic.appInfo;
                                            const { lifeCycleId, showFlag } = obj.rowData;
                                            if (showFlag === '1') {
                                                obj.props.dispatch(
                                                    push(`${mainModule}LifeCyclePlanningDetail/${lifeCycleId}/${'0'}`)
                                                )
                                            } else {

                                            }
                                        }
                                    }
                                ]
                            }
                        }
                    ]}
                    componentsKey={{
                        FlowByLifeCyc: (obj) => {
                            this.table.clearSelectedRows();
                            //判断是否选中数据
                            let selectedRows = obj.selectedRows;
                            if (!selectedRows || !selectedRows.length || selectedRows.length > 1) {
                                obj.btnCallbackFn.closeDrawer();
                                obj.btnCallbackFn.msg.error('请选择一条数据！');
                                return <div />
                            }
                            if (obj.selectedRows[0].workId != "") {
                                obj.btnCallbackFn.closeDrawer();
                                obj.btnCallbackFn.msg.error('已发起审批的不可再发起！');
                                return <div />
                            }
                            return <FlowByLifeCyc {...obj} flowData={selectedRows[0]} />
                        }
                    }}
                    method={{
                        addClick: (obj) => {
                            // "onClick": "bind:addClick",
                            if (projectId === 'all') {
                                obj.btnCallbackFn.closeDrawer();
                                obj.btnCallbackFn.clearSelectedRows();
                                Msg.warn('请选择右上角项目！')
                            }
                        },
                        editClick: (obj) => {
                            this.table.clearSelectedRows();
                            if (obj.selectedRows[0].statusId === '1') {
                                Msg.warn('审核中的数据不能修改！')
                                obj.btnCallbackFn.closeDrawer();
                            } else if (obj.selectedRows[0].statusId === '2') {
                                Msg.warn('审核结束的数据不能修改！')
                                obj.btnCallbackFn.closeDrawer();
                            } else {

                            }
                        },
                        delClick: (obj) => {
                            const { myFetch } = obj.props;
                            let apih5FlowStatusArry = [];
                            if (obj.selectedRows.length > 0) {
                                for (var i = 0; i < obj.selectedRows.length; i++) {
                                    if (obj.selectedRows[i].statusId === '1' || obj.selectedRows[i].statusId === '2') {
                                        apih5FlowStatusArry.push(obj.selectedRows[i].statusId);
                                    }
                                }
                                if (apih5FlowStatusArry.length > 0) {
                                    obj.btnCallbackFn.closeDrawer();
                                    Msg.warn('请选择未审核的数据!');
                                    this.table.clearSelectedRows();
                                } else {
                                    confirm({
                                        title: "确定删除么?",
                                        okText: "确认",
                                        cancelText: "取消",
                                        onOk: () => {
                                            myFetch('batchDeleteUpdateZjTzLifeCycle', obj.selectedRows).then(
                                                ({ success, message }) => {
                                                    if (success) {
                                                        Msg.success(message);
                                                        this.table.refresh();
                                                        this.table.clearSelectedRows();
                                                    } else {
                                                        Msg.error(message)
                                                    }
                                                }
                                            );
                                        }
                                    })
                                }
                            } else {
                                Msg.warn('请选择数据!');
                            }
                        }
                    }}
                    actionBtns={{
                        apiName: "getSysMenuBtn",
                        otherParams: function (obj) {
                            var props = obj.Pprops;
                            let curRouteData = props.routerInfo.routeData[props.routerInfo.curKey];
                            return {
                                menuParentId: curRouteData._value,
                                tableField: "projectInfo"
                            }
                        }
                    }}
                />
            </div>
        );
    }
}

export default index;