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
                                placeholder: '?????????',
                                initialValue: () => {
                                    return projectName
                                }
                            },
                        },
                        {
                            isInSearch: false,
                            table: {
                                title: '????????????',
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
                                placeholder: '?????????',
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
                                title: '???????????????',
                                filter: true,
                                width: 200,
                                dataIndex: 'subprojectInfoName',
                                key: 'subprojectInfoName',
                            },
                            form: {
                                type: "select",
                                label: "???????????????",
                                field: "subprojectInfoId",
                                placeholder: "?????????",
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
                                title: '????????????',
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
                                placeholder: '?????????',
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
                                title: '?????????????????????',
                                width: 200,
                                tooltip: 18,
                                dataIndex: 'numberContent',
                                key: 'numberContent',

                            }, isInForm: false
                        },
                        {
                            table: {
                                title: '????????????',
                                dataIndex: 'term',
                                width: 200,
                                tooltip: 18,
                                key: 'term'
                            }, isInForm: false
                        },
                        {
                            table: {
                                title: '??????????????????',
                                dataIndex: 'riskFlag',
                                width: 130,
                                onClick: 'detail',
                                key: 'riskFlag'
                            }, isInForm: false
                        },
                        {
                            table: {
                                title: '????????????',
                                dataIndex: 'measure',
                                width: 200,
                                tooltip: 18,
                                key: 'measure'
                            }, isInForm: false
                        },
                        {
                            table: {
                                title: '????????????????????????',
                                dataIndex: 'negotiateFlag',
                                width: 180,
                                key: 'negotiateFlag'
                            }, isInForm: false
                        },
                        {
                            table: {
                                title: '????????????????????????',
                                dataIndex: 'deptAndLeader',
                                width: 200,
                                tooltip: 18,
                                key: 'deptAndLeader'
                            }, isInForm: false
                        },
                        {
                            table: {
                                title: '??????????????????',
                                dataIndex: 'implement',
                                width: 200,
                                tooltip: 18,
                                key: 'implement'
                            }, isInForm: false
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
                                label: '??????',
                                field: 'zjTzFileList',
                                type: 'files',
                                fetchConfig: {
                                    apiName: window.configs.domain + 'upload',
                                    otherParams: {
                                        name: 'PPP????????????'
                                    }
                                },
                                showDownloadIcon: true,//????????????????????????
                                onPreview: "bind:_docFilesByOfficeUrl",//365??????
                                
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
                                label: '????????????',
                                required: true,
                                addDisabled: true,
                                editDisabled: true,
                                placeholder: '?????????',
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
                                title: '??????',
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
                                        label: "?????????",
                                        value: "0"
                                    },
                                    {
                                        label: "?????????",
                                        value: "1"
                                    },
                                    {
                                        label: "?????????",
                                        value: "2"
                                    }
                                ]
                            }
                        },
                        {
                            isInForm: false,
                            table: {
                                title: "??????",
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
                                            return '<a>??????</a>';
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
                                Msg.warn('???????????????????????????')
                            }
                        },
                        editClick: (obj) => {
                            if (obj.selectedRows[0].statusId === '0' || obj.selectedRows[0].statusId === '2') {
                                this.table.clearSelectedRows();
                            } else {
                                obj.btnCallbackFn.closeDrawer();
                                this.table.clearSelectedRows();
                                Msg.warn('??????????????????/??????????????????')
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
                                    Msg.warn('??????????????????/??????????????????!');
                                    this.table.clearSelectedRows();
                                } else {
                                    confirm({
                                        title: "????????????????",
                                        okText: "??????",
                                        cancelText: "??????",
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
                                Msg.warn('???????????????!');
                            }
                        },
                        // ??????---
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
                                    Msg.warn('?????????????????????????????????');
                                } else {
                                    confirm({
                                        title: "????????????????",
                                        okText: "??????",
                                        cancelText: "??????",
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
                                Msg.warn('??????????????????');
                            }
                        },
                        // ??????
                        tuiHuiClick: (obj) => {
                            this.table.clearSelectedRows();
                            const { myFetch } = obj.props;
                            if (obj.selectedRows.length > 0) {
                                let aa = [];
                                for (var i = 0; i < obj.selectedRows.length; i++) {
                                    // ?????????/?????????
                                    if (obj.selectedRows[i].statusId === '0' || obj.selectedRows[i].statusId === '2') {
                                        aa.push(obj.selectedRows[i].statusId);
                                    }
                                }
                                if (aa.length > 0) {
                                    Msg.warn('?????????/?????????????????????????????????');
                                } else {
                                    confirm({
                                        title: "????????????????",
                                        okText: "??????",
                                        cancelText: "??????",
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
                                Msg.warn('??????????????????');
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