import React, { Component } from "react";
import QnnTable from "../modules/qnn-table";
import { push } from "react-router-redux";
import s from "./style.less";
import { message as Msg, Modal, Tooltip } from "antd";
const { confirm } = Modal;
const config = {

    antd: {
        rowKey: function (row) {
            return row.pppTermId
        },
        size: 'small',
        scroll: {
            y: document.documentElement.clientHeight * 0.65
        }
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
            pppTermId: '',
            projectId: props.loginAndLogoutInfo.loginInfo.userInfo.curCompany.projectId,
            projectName: props.loginAndLogoutInfo.loginInfo.userInfo.curCompany.projectName,
        }
    }
    componentDidMount() { }
    render() {
        const { projectId, projectName } = this.state;
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
                        apiName: 'getZjTzPppTermList',
                        otherParams: {
                            projectId: projectId
                        }
                    }}
                    formConfig={[
                        {
                            isInTable: false,
                            form: {
                                field: 'pppTermId',
                                type: 'string',
                                hide: true,
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                field: 'projectName',
                                required: true,
                                addDisabled: true,
                                disabled: true,
                                editDisabled: true,
                                hide: true,
                                type: 'string',
                                placeholder: '请输入',
                                initialValue: () => {
                                    return projectName
                                }
                            },
                        },
                        {
                            isInSearch: false,
                            table: {
                                title: '项目名称',
                                dataIndex: 'projectId',
                                key: 'projectId',
                                fixed: 'left',
                                type: 'select',
                                filter: true,
                                width: 350,
                                tooltip: 23
                            },
                            form: {
                                field: 'projectId',
                                type: 'select',
                                showSearch: true,
                                initialValue: projectId,
                                editDisabled: true,
                                required: true,
                                addDisabled: true,
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
                                        sm: { span: 3 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 24 },
                                        sm: { span: 21 }
                                    }
                                },
                            },
                        },
                        {
                            table: {
                                title: '子项目名称',
                                filter: true,
                                width: 200,
                                dataIndex: 'subprojectInfoName',
                                key: 'subprojectInfoName',
                            },
                            form: {
                                type: "select",
                                label: "子项目名称",
                                field: "subprojectInfoId",
                                placeholder: "请输入",
                                disabled: true,
                                editDisabled: true,
                                optionConfig: {
                                    label: 'subprojectName',
                                    value: 'subprojectInfoId'
                                },
                                fetchConfig: {
                                    apiName: "getZjTzProSubprojectInfoList",
                                    otherParams: {
                                        projectId: projectId
                                    }
                                },
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
                            },
                        },
                        {
                            isInSearch: false,
                            table: {
                                title: '分析期次',
                                width: 120,
                                dataIndex: 'issueId',
                                key: 'issueId',
                                type: 'select'
                            },
                            form: {
                                type: 'select',
                                field: 'issueId',
                                editDisabled: true,
                                optionConfig: {
                                    label: 'itemName',
                                    value: 'itemId'
                                },
                                fetchConfig: {
                                    apiName: "getBaseCodeSelect",
                                    otherParams: {
                                        itemId: 'fenXiQiCi'
                                    }
                                },
                                placeholder: '请输入',
                                required: true,
                                formItemLayoutForm: {
                                    labelCol: {
                                        xs: { span: 24 },
                                        sm: { span: 3 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 24 },
                                        sm: { span: 21 }
                                    }
                                },
                            }
                        },
                        {
                            table: {
                                title: '条款编号与内容',
                                width: 200,
                                tooltip: 18,
                                dataIndex: 'numberContent',
                                key: 'numberContent',

                            }, isInForm: false
                        },
                        {
                            table: {
                                title: '条款分析',
                                dataIndex: 'term',
                                width: 200,
                                tooltip: 18,
                                key: 'term'
                            }, isInForm: false
                        },
                        {
                            table: {
                                title: '是否存在风险',
                                dataIndex: 'riskFlag',
                                width: 130,
                                onClick: 'detail',
                                key: 'riskFlag'
                            }, isInForm: false
                        },
                        {
                            table: {
                                title: '应对措施',
                                dataIndex: 'measure',
                                width: 200,
                                tooltip: 18,
                                key: 'measure'
                            }, isInForm: false
                        },
                        {
                            table: {
                                title: '是否需要二次谈判',
                                dataIndex: 'negotiateFlag',
                                width: 180,
                                key: 'negotiateFlag'
                            }, isInForm: false
                        },
                        {
                            table: {
                                title: '责任部门与责任人',
                                dataIndex: 'deptAndLeader',
                                width: 200,
                                tooltip: 18,
                                key: 'deptAndLeader'
                            }, isInForm: false
                        },
                        {
                            table: {
                                title: '措施落实情况',
                                dataIndex: 'implement',
                                width: 200,
                                tooltip: 18,
                                key: 'implement'
                            }, isInForm: false
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
                                label: '附件',
                                field: 'zjTzFileList',
                                type: 'files',
                                fetchConfig: {
                                    apiName: window.configs.domain + 'upload',
                                    otherParams: {
                                        name: 'PPP合同分析'
                                    }
                                },
                                showDownloadIcon: true,//是否显示下载按钮
                                onPreview: "bind:_docFilesByOfficeUrl",//365显示
                                
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
                                filter: true,
                                width: 120,
                                // fixed:'right',
                                dataIndex: 'statusId',
                                key: 'statusId',
                                type: 'select'
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
                                        label: "被退回",
                                        value: "2"
                                    }
                                ]
                            }
                        },
                        {
                            isInForm: false,
                            table: {
                                title: "操作",
                                fixed: 'right',
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
                                            return '<a>明细</a>';
                                        },
                                        onClick: (obj) => {
                                            const { mainModule } = obj.props.myPublic.appInfo;
                                            const { pppTermId, statusId, projectName } = obj.rowData;

                                            obj.props.dispatch(
                                                push(`${mainModule}ContractAnalysisDetail/${pppTermId}/${statusId}/${projectName}/${0}`)
                                            )
                                        }
                                    }
                                ]
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
                            if (obj.selectedRows[0].statusId === '0' || obj.selectedRows[0].statusId === '2') {
                                this.table.clearSelectedRows();
                            } else {
                                obj.btnCallbackFn.closeDrawer();
                                this.table.clearSelectedRows();
                                Msg.warn('请选择未上报/被退回数据！')
                            }
                        },
                        delClick: (obj) => {
                            const { myFetch } = obj.props;
                            let statusIdArry = [];
                            if (obj.selectedRows.length > 0) {
                                for (var i = 0; i < obj.selectedRows.length; i++) {
                                    if (obj.selectedRows[i].statusId === '0' || obj.selectedRows[i].statusId === '2') {

                                    } else {
                                        statusIdArry.push(obj.selectedRows[i].statusId);
                                    }
                                }
                                if (statusIdArry.length > 0) {
                                    obj.btnCallbackFn.closeDrawer();
                                    Msg.warn('请选择未上报/被退回的数据!');
                                    this.table.clearSelectedRows();
                                } else {
                                    confirm({
                                        title: "确定删除么?",
                                        okText: "确认",
                                        cancelText: "取消",
                                        onOk: () => {
                                            myFetch('batchDeleteUpdateZjTzPppTerm', obj.selectedRows).then(
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
                        // 上报---
                        shangBaoClick: (obj) => {
                            this.table.clearSelectedRows();
                            const { myFetch } = obj.props;
                            if (obj.selectedRows.length > 0) {
                                let aa = [];
                                for (var i = 0; i < obj.selectedRows.length; i++) {
                                    if (obj.selectedRows[i].statusId === '1') {
                                        aa.push(obj.selectedRows[i].statusId);
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
                                            myFetch('batchReleaseZjTzPppTerm', obj.selectedRows).then(
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
                                    if (obj.selectedRows[i].statusId === '0' || obj.selectedRows[i].statusId === '2') {
                                        aa.push(obj.selectedRows[i].statusId);
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
                                            myFetch('batchRecallZjTzPppTerm', obj.selectedRows).then(
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