import React, { Component } from "react";
import QnnTable from "../modules/qnn-table";
import s from "./style.less";
import moment from 'moment';
import { message as Msg, Modal } from "antd";
const confirm = Modal.confirm;
const config = {

    antd: {
        rowKey: function (row) {
            return row.financeMonthId
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
            projectName: props.loginAndLogoutInfo.loginInfo.userInfo.curCompany.projectName,
        }
    }
    componentDidMount() {

    }
    render() {
        const { projectId, projectName } = this.state;
        console.log(projectId);
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
                        apiName: 'getZjTzFinanceMonthList',
                        otherParams: {
                            projectId: projectId
                        }
                        // otherParams: () => {
                        //     return {
                        //         projectId1:'1EHIIF0HF4UB03330B0A00009DE1C5BF'
                        //     }
                        // }
                    }}
                    {...config}
                    formConfig={[
                        {
                            isInTable: false,
                            form: {
                                field: 'financeMonthId',
                                type: 'string',
                                hide: true,
                            }
                        },

                        {
                            table: {
                                title: '项目名称',
                                filter: true,

                                dataIndex: 'projectId',
                                key: 'projectId',
                                type: 'select',
                            },
                            form: {
                                label: '项目名称',
                                field: 'projectId',
                                type: 'select',
                                showSearch: true,
                                required: true,
                                initialValue: projectId,
                                addDisabled: true,
                                editDisabled: true,
                                optionConfig: {
                                    label: 'projectName',
                                    value: 'projectId'
                                },
                                fetchConfig: {
                                    apiName: "getZjTzProManageList"
                                },
                                placeholder: '请选择',
                                spanForm: 12,
                                formItemLayoutForm: {
                                    labelCol: {
                                        xs: { span: 24 },
                                        sm: { span: 6 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 24 },
                                        sm: { span: 18 }
                                    }
                                },
                            },
                        },

                        {
                            table: {
                                title: '年月',
                                dataIndex: 'yearMonthDate',
                                filter: true,
                                onClick: 'detail',
                                format: 'YYYY-MM',
                                key: 'yearMonthDate',
                                render: (val) => {
                                    return moment(val).format('YYYY-MM-DD')
                                }
                            },
                            form: {
                                type: 'month',
                                editDisabled: true,
                                field: 'yearMonthDate',
                                spanForm: 12,
                                formItemLayoutForm: {
                                    labelCol: {
                                        xs: { span: 24 },
                                        sm: { span: 6 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 24 },
                                        sm: { span: 18 }
                                    }
                                },
                            }
                        },

                        {
                            isInTable: false,
                            form: {
                                label: '融资落实过程',
                                type: 'textarea',
                                field: 'process',
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
                            isInTable: false,
                            form: {
                                label: '附件',
                                field: 'zjTzFileList',
                                type: 'files',
                                showDownloadIcon: true,//是否显示下载按钮
                                onPreview: "bind:_docFilesByOfficeUrl",//365显示
                                
                                fetchConfig: {
                                    apiName: window.configs.domain + 'upload',
                                    otherParams: {
                                        name: '一公局未落实融资（融资暂停）情况月报'
                                    }
                                }
                            }
                        },
                        {
                            table: {
                                title: '登记日期',
                                dataIndex: 'registerDate',
                                format: 'YYYY-MM-DD',
                                key: 'registerDate'
                            },
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
                                        sm: { span: 6 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 24 },
                                        sm: { span: 18 }
                                    }
                                },
                                initialValue: () => {
                                    return new Date()
                                }
                            },
                        },
                        {
                            table: {
                                title: '登记用户',
                                dataIndex: 'registerPerson',
                                key: 'registerPerson'
                            },
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
                                        sm: { span: 6 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 24 },
                                        sm: { span: 18 }
                                    }
                                },
                                initialValue: (obj) => {
                                    return obj.loginAndLogoutInfo.loginInfo.userInfo.realName
                                }
                            },
                        },
                        {
                            table: {
                                title: '状态',
                                dataIndex: 'releaseName',
                                key: 'releaseName'
                            },
                            isInForm: false,
                            form: {
                                type: "select",
                                field: "releaseId",
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
                            },
                        },
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
                            if (obj.selectedRows[0].apih5FlowStatus === '1' || obj.selectedRows[0].apih5FlowStatus === '2') {
                                obj.btnCallbackFn.closeDrawer();
                                Msg.warn('请选择未审核的数据!');
                                this.table.clearSelectedRows();
                            } else {
                                this.table.clearSelectedRows();
                            }
                        },
                        delClick: (obj) => {
                            const { myFetch } = obj.props;
                            let apih5FlowStatusArry = [];
                            if (obj.selectedRows.length > 0) {
                                for (var i = 0; i < obj.selectedRows.length; i++) {
                                    if (obj.selectedRows[i].apih5FlowStatus === '1' || obj.selectedRows[i].apih5FlowStatus === '2') {
                                        apih5FlowStatusArry.push(obj.selectedRows[i].apih5FlowStatus);
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
                                            myFetch('batchDeleteUpdateZjTzFinanceMonth', obj.selectedRows).then(
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
                        },
                        faBuClick: (obj) => {
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
                                            myFetch('batchReleaseZjTzFinanceMonth', obj.selectedRows).then(
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
                                Msg.warn('请选择数据');
                            }
                        },
                        cheHuiClick: (obj) => {
                            this.table.clearSelectedRows();
                            const { myFetch } = obj.props;
                            if (obj.selectedRows.length > 0) {
                                let aa = [];
                                for (var i = 0; i < obj.selectedRows.length; i++) {
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
                                            myFetch('batchRecallZjTzFinanceMonth', obj.selectedRows).then(
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
                                Msg.warn('请选择数据');
                            }

                        },
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