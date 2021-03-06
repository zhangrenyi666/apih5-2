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
                                title: '????????????',
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
                                placeholder: '?????????',
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
                                title: '??????',
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
                                        label: "?????????",
                                        value: "0"
                                    },
                                    {
                                        label: "?????????",
                                        value: "1"
                                    },
                                    {
                                        label: "?????????",
                                        value: "3"
                                    },
                                    {
                                        label: "?????????",
                                        value: "2"
                                    },
                                    {
                                        label: "?????????",
                                        value: "4"
                                    }
                                ]
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: '??????',
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
                                title: '????????????',
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
                                label: '???????????????????????????',
                                field: 'zjTzFileList',
                                showDownloadIcon: true,//????????????????????????
                                onPreview: "bind:_docFilesByOfficeUrl",//365??????
                                
                                type: 'files',
                                fetchConfig: {
                                    apiName: window.configs.domain + 'upload',
                                    otherParams: {
                                        name: '?????????????????????'
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
                                label: '????????????',
                                required: true,
                                addDisabled: true,
                                editDisabled: true,
                                placeholder: '?????????',
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
                                label: '????????????',
                                required: true,
                                addDisabled: true,
                                editDisabled: true,
                                placeholder: '?????????',
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
                                title: "????????????",
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
                                            return '<a>????????????</a>';
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
                                title: "??????????????????",
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
                                                return '<a>?????????</a>';
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
                            //????????????????????????
                            let selectedRows = obj.selectedRows;
                            if (!selectedRows || !selectedRows.length || selectedRows.length > 1) {
                                obj.btnCallbackFn.closeDrawer();
                                obj.btnCallbackFn.msg.error('????????????????????????');
                                return <div />
                            }
                            if (obj.selectedRows[0].workId != "") {
                                obj.btnCallbackFn.closeDrawer();
                                obj.btnCallbackFn.msg.error('????????????????????????????????????');
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
                                Msg.warn('???????????????????????????')
                            }
                        },
                        editClick: (obj) => {
                            this.table.clearSelectedRows();
                            if (obj.selectedRows[0].statusId === '1') {
                                Msg.warn('?????????????????????????????????')
                                obj.btnCallbackFn.closeDrawer();
                            } else if (obj.selectedRows[0].statusId === '2') {
                                Msg.warn('????????????????????????????????????')
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
                                    Msg.warn('???????????????????????????!');
                                    this.table.clearSelectedRows();
                                } else {
                                    confirm({
                                        title: "????????????????",
                                        okText: "??????",
                                        cancelText: "??????",
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
                                Msg.warn('???????????????!');
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