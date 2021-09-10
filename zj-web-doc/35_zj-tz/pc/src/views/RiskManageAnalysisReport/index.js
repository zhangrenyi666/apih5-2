import React, { Component } from "react";
import QnnTable from "../modules/qnn-table";
import s from "./style.less";
import { message as Msg, Modal } from "antd";
const confirm = Modal.confirm;
const config = {
    
    antd: {
        rowKey: function (row) {
            return row.riskAnalysisId
        },
        size: 'small',
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
        }
    }
    componentDidMount() {

    }
    render() {
        const { projectId,projectName } = this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany;
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
                    fetchConfig={{
                        apiName: 'getZjTzRiskAnalysisList',
                        otherParams: {
                            projectId:projectId
                        }
                    }}
                    {...config}
                    formConfig={[
                        {
                            isInTable: false,
                            form: {
                                field: 'riskAnalysisId',
                                type: 'string',
                                hide: true,
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                field: 'projectName',
                                type: 'string',
                                hide: true,
                                initialValue:projectName
                            }
                        },
                        {
                            table: {
                                title: '年度',
                                dataIndex: 'riskYear',
                                key: 'riskYear',
                                format: 'YYYY',
                                align:'center'
                            },
                            form: {
                                type: 'year',
                                field: 'riskYear',
                                required: true
                            }
                        },

                        {
                            table: {
                                title: '项目名称',
                                dataIndex: 'projectName',
                                key: 'projectName',
                                width: 260,
                                tooltip: 23,
                                onClick:'detail'
                            },
                            form: {
                                label: '项目名称',
                                field: 'projectId',
                                type: 'select',
                                showSearch: true,
                                required: true,
                                addDisabled:true,
                                editDisabled: true,
                                initialValue: () => {
                                    return projectId
                                },
                                optionConfig: {
                                    label: 'projectName',
                                    value: 'projectId'
                                },
                                fetchConfig: {
                                    apiName: "getZjTzProManageList"
                                },
                                placeholder: '请选择'
                            },
                        },
                        {
                            table: {
                                title: '文件名称',
                                dataIndex: 'fileName',
                                key: 'fileName',
                                width: 200,
                                tooltip: 18
                            },
                            form: {
                                type: 'string',
                                field: 'fileName',
                                placeholder: '请输入',
                                required: true,
                                formItemLayout: {
                                    labelCol: {
                                        xs: { span: 21 },
                                        sm: { span: 3 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 21 },
                                        sm: { span: 21 }
                                    }
                                }
                            }
                        },
                        {
                            table: {
                                title: '主要内容',
                                dataIndex: 'mainContent',
                                width: 200,
                                tooltip: 23,
                                key: 'mainContent',
                            },
                            form: {
                                label: '主要内容',
                                type: 'textarea',
                                field: 'mainContent',
                                placeholder: '请输入',
                                required: true,
                                formItemLayout: {
                                    labelCol: {
                                        xs: { span: 21 },
                                        sm: { span: 3 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 21 },
                                        sm: { span: 21 }
                                    }
                                }
                            }
                        },
                        {
                            table: {
                                title: '状态',
                                dataIndex: 'releaseId',
                                key: 'releaseId',
                                filter: true,
                                width: 100,
                                type: 'select'
                            },
                            isInForm: false,
                            form: {
                                type: 'select',
                                field: 'releaseId',
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
                                        label: "被退回",
                                        value: "2"
                                    }
                                ]
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: '附件',
                                field: 'zjTzFileList',
                                type: 'files',
                                required:true,
                                showDownloadIcon: true,//是否显示下载按钮
                                onPreview: "bind:_docFilesByOfficeUrl",//365显示
                                fetchConfig: {
                                    apiName: window.configs.domain + 'upload'
                                }
                            }
                        }
                    ]}
                    method={{
                        addClick: (obj) => {
                            if (projectId === 'all') {
                                obj.btnCallbackFn.closeDrawer();
                                obj.btnCallbackFn.clearSelectedRows();
                                Msg.warn('请选择右上角项目！')
                            }
                        },
                        editClick: (obj) => {
                            if (obj.selectedRows[0].releaseId === '1') {
                                obj.btnCallbackFn.closeDrawer();
                                Msg.warn('已上报的不能修改!');
                                this.table.clearSelectedRows();
                            } else {
                                this.table.clearSelectedRows();
                            }
                        },
                        shangBaoClick: (obj) => {
                            this.table.clearSelectedRows();
                            const { myFetch } = obj.props;
                            if (obj.selectedRows.length > 0) {
                                let aa = [];
                                for (var i = 0; i < obj.selectedRows.length; i++) {
                                    if (obj.selectedRows[i].releaseId === '1') {
                                        aa.push(obj.selectedRows[i].releaseId);
                                    }
                                }
                                if (aa.length > 0) {
                                    Msg.warn('已上报的消息不能上报！');
                                } else {
                                    confirm({
                                        title: "确定上报么?",
                                        okText: "确认",
                                        cancelText: "取消",
                                        onOk: () => {
                                            myFetch('batchReleaseZjTzRiskAnalysis', obj.selectedRows).then(
                                                ({ data, success, message }) => {
                                                    if (success) {
                                                        Msg.success(message);
                                                        this.table.refresh();
                                                    } else {
                                                        Msg.error(message)
                                                    }
                                                }
                                            );
                                        }
                                    });
                                }
                            } else {
                                Msg.warn('请选择数据！');
                            }
                        },
                        // 退回
                        tuiHuiClick: (obj) => {
                            this.table.clearSelectedRows();
                            const { myFetch } = obj.props;
                            if (obj.selectedRows.length > 0) {
                                let aa = [];
                                for (var i = 0; i < obj.selectedRows.length; i++) {
                                    // 未上报/被退回
                                    if (obj.selectedRows[i].releaseId === '0' || obj.selectedRows[i].releaseId === '2') {
                                        aa.push(obj.selectedRows[i].releaseId);
                                    }
                                }
                                if (aa.length > 0) {
                                    Msg.warn('未上报/被退回的消息不能退回！');
                                } else {
                                    confirm({
                                        title: "确定退回么?",
                                        okText: "确认",
                                        cancelText: "取消",
                                        onOk: () => {
                                            myFetch('batchRecallZjTzRiskAnalysis', obj.selectedRows).then(
                                                ({ success, message }) => {
                                                    if (success) {
                                                        Msg.success(message);
                                                        this.table.refresh();
                                                    } else {
                                                        Msg.error(message)
                                                    }
                                                }
                                            );
                                        }
                                    });
                                }

                            } else {
                                Msg.warn('请选择数据！');
                            }

                        },
                        filestExport: (obj) => {
                            this.table.clearSelectedRows();
                            const { myFetch } = obj.props;
                            if (obj.selectedRows.length > 0) {
                                confirm({
                                    title: "确定导出附件么?",
                                    okText: "确认",
                                    cancelText: "取消",
                                    onOk: () => {
                                        myFetch('batchExportZjTzRiskAnalysisFile', obj.selectedRows).then(
                                            ({ success, message, data }) => {
                                                if (success) {
                                                    Msg.success(message);
                                                    window.location.href = data;
                                                    this.table.refresh();
                                                    this.table.clearSelectedRows();
                                                } else {
                                                    Msg.error(message)
                                                }
                                            }
                                        );
                                    }
                                })
                            } else {
                                Msg.warn('请选择数据');
                            }
                        },
                        delClick: (obj) => {
                            const { myFetch } = obj.props;
                            let aa = [];
                            if (obj.selectedRows.length > 0) {
                                for (var i = 0; i < obj.selectedRows.length; i++) {
                                    if (obj.selectedRows[i].releaseId === '1') {
                                        aa.push(obj.selectedRows[i].releaseId);
                                    }
                                }
                                if (aa.length > 0) {
                                    obj.btnCallbackFn.closeDrawer();
                                    Msg.warn('已上报的不能删除!');
                                    this.table.clearSelectedRows();
                                } else {
                                    confirm({
                                        title: "确定删除么?",
                                        okText: "确认",
                                        cancelText: "取消",
                                        onOk: () => {
                                            myFetch('batchDeleteUpdateZjTzRiskAnalysis', obj.selectedRows).then(
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