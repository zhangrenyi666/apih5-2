import React, { Component } from "react";
import QnnTable from "../modules/qnn-table";
import { push } from "react-router-redux";
import { message as Msg, Modal } from "antd";
import s from "./style.less";
const confirm = Modal.confirm;

const config = {

    antd: {
        rowKey: 'riskId',
        size: 'small'
    },
    drawerConfig: {
        width: '900px'
    },
    paginationConfig: {
        position: 'bottom'
    },
    firstRowIsSearch: false,
    isShowRowSelect: true,
    formItemLayout: {
        labelCol: {
            xs: { span: 24 },
            sm: { span: 4 }
        },
        wrapperCol: {
            xs: { span: 24 },
            sm: { span: 20 }
        }
    }
};
const red = require('./red.png');
const grey = require('./grey.png');
class index extends Component {
    constructor(props) {
        super(props);
        this.state = {
            opdata: [],
            projectId: props.loginAndLogoutInfo.loginInfo.userInfo.curCompany.projectId,
            projectName: props.loginAndLogoutInfo.loginInfo.userInfo.curCompany.projectName,
        }
    }
    componentDidMount() {
        const { projectId } = this.state;
        const { myFetch } = this.props;
        myFetch('getZjTzProSubprojectInfoList', { projectId: projectId }).then(
            ({ success, message, data }) => {
                if (success) {
                    this.setState({
                        opdata: data
                    })
                } else {
                }
            }
        );
    }
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
                    fetchConfig={{
                        apiName: 'getZjTzRiskList',
                        otherParams: {
                            projectId: projectId
                        }
                    }}
                    {...config}
                    formConfig={[
                        {
                            isInTable: false,
                            form: {
                                field: 'riskId',
                                type: 'string',
                                hide: true,
                            }
                        },
                        {
                            table: {
                                title: '??????',
                                dataIndex: 'projectName1',
                                key: 'projectName1',
                                width: 70,
                                fixed: 'left',
                                align: 'center',
                                render: (data, rowData) => {
                                    if (rowData.colourFlag === 'red') {
                                        return '<img style="height:19px" alt="" src=' + red + ' />'
                                    } else {
                                        return '<img style="height:19px" alt="" src=' + grey + ' />'
                                    }
                                }
                            },
                            isInForm: false,
                            form: {
                                field: 'projectName1',
                                type: 'string',
                                placeholder: '?????????'
                            },
                        },
                        {
                            isInTable: false,
                            form: {
                                field: 'projectName',
                                hide: true,
                                type: 'string',
                                placeholder: '?????????',
                                initialValue: () => {
                                    return projectName
                                }
                            },
                        },
                        {
                            isInTable: false,
                            form: {
                                label: "????????????",
                                field: 'projectId',
                                type: 'select',
                                showSearch: true,
                                initialValue: projectId,
                                addDisabled: true,
                                editDisabled: true,
                                required: true,
                                optionConfig: {
                                    label: 'projectName',
                                    value: 'projectId'
                                },
                                fetchConfig: {
                                    apiName: "getZjTzProManageList"
                                },
                                placeholder: '?????????',
                                onChange: (val, rowData) => {
                                    console.log(rowData);
                                    const { myFetch } = this.props;
                                    myFetch('getZjTzProSubprojectInfoList', { projectId: val }).then(
                                        ({ success, message, data }) => {
                                            if (success) {
                                                this.setState({
                                                    opdata: data
                                                })
                                            } else {
                                            }
                                        }
                                    );
                                }
                            },
                        },
                        {
                            table: {
                                title: '????????????',
                                width: 350,
                                dataIndex: 'projectName',
                                key: 'projectName',
                                filter: true,
                                // type: 'select',
                                fieldsConfig: {
                                    field: 'projectId',
                                    type: 'select',
                                    showSearch: true,
                                    optionConfig: {
                                        label: 'projectName',
                                        value: 'projectId'
                                    },
                                    fetchConfig: {
                                        apiName: "getZjTzProManageList"
                                    },
                                    placeholder: '?????????'
                                }
                            },
                            isInForm:false
                        },
                        {
                            table: {
                                title: '???????????????',
                                width: 200,
                                tooltip: 23,
                                dataIndex: 'subprojectInfoName',
                                key: 'subprojectInfoName'
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
                                optionData: () => {
                                    return this.state.opdata
                                },
                                // fetchConfig: {
                                //     apiName: 'getZjTzProSubprojectInfoList',
                                //     params: {
                                //         projectId:'projectId'
                                //     }
                                // }
                            },
                        },
                        {
                            table: {
                                title: '??????????????????',
                                dataIndex: 'num1',
                                width: 120,
                                key: 'num1',
                                onClick: 'detail',
                                willExecute: (obj) => {
                                    const { myFetch } = this.props;
                                    myFetch('getZjTzProSubprojectInfoList', { projectId: obj.rowData.projectId }).then(
                                        ({ success, message, data }) => {
                                            if (success) {
                                                this.setState({
                                                    opdata: data
                                                })
                                            } else {
                                            }
                                        }
                                    );
                                },
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '????????????',
                                width: 140,
                                dataIndex: 'modifyTime',
                                key: 'modifyTime',
                                format: 'YYYY-MM-DD'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '?????????????????????',
                                width: 140,
                                dataIndex: 'num2',
                                key: 'num2'
                            },
                            isInForm: false
                        },

                        {
                            table: {
                                title: '????????????????????????',
                                width: 160,
                                dataIndex: 'num3',
                                key: 'num3'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '???????????????????????????',
                                width: 160,
                                dataIndex: 'num4',
                                key: 'num4'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '??????',
                                width: 100,
                                dataIndex: 'releaseName',
                                key: 'releaseName',
                                filter: true
                            },
                            isInForm: false,
                            form: {
                                type: "select",
                                field: "releaseId",
                                hide: true,
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
                            },
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
                                width: 100,
                                btns: [
                                    {
                                        name: 'PolicyDetail',
                                        render: (rowData) => {
                                            return '<a>??????????????????</a>';
                                        },
                                        onClick: (obj) => {
                                            const { mainModule } = obj.props.myPublic.appInfo;
                                            const { riskId, releaseId, projectId, projectName } = obj.rowData;
                                            obj.props.dispatch(
                                                push(`${mainModule}RiskMangeDetail/${riskId}/${releaseId}/${projectId}/${projectName}`)
                                            )
                                        },
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
                        shangBaoClick: (obj) => {//0-?????????
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
                                    Msg.warn('?????????????????????????????????');
                                } else {
                                    confirm({
                                        title: "????????????????",
                                        okText: "??????",
                                        cancelText: "??????",
                                        onOk: () => {
                                            myFetch('batchReleaseZjTzRisk', obj.selectedRows).then(
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
                                Msg.warn('???????????????');
                            }
                        },
                        tuiHuiClick: (obj) => {
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
                                    Msg.warn('??????????????????????????????');
                                } else {
                                    confirm({
                                        title: "????????????????",
                                        okText: "??????",
                                        cancelText: "??????",
                                        onOk: () => {
                                            myFetch('batchRecallZjTzRisk', obj.selectedRows).then(
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
                                Msg.warn('???????????????');
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
                                    Msg.warn('????????????????????????!');
                                    this.table.clearSelectedRows();
                                } else {
                                    confirm({
                                        title: "????????????????",
                                        okText: "??????",
                                        cancelText: "??????",
                                        onOk: () => {
                                            myFetch('batchDeleteUpdateZjTzRisk', obj.selectedRows).then(
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