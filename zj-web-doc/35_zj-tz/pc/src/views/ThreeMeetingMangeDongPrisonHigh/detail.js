import React, { Component } from "react";
import QnnTable from "../modules/qnn-table";
import s from "./style.less";
import { message as Msg } from "antd";
import { goBack } from "connected-react-router";
const config = {
    antd: {
        rowKey: function (row) {
            return row.executiveMeetChangeRecordId
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
class index extends Component {
    constructor(props) {
        super(props);
        this.state = {
            rowData: props.match.params.rowData ? JSON.parse(decodeURIComponent(props.match.params.rowData)) : {},
        }
    }
    render() {
        const { rowData } = this.state;
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
                    getRowSelection={(obj) => {
                        return {
                            getCheckboxProps: record => ({
                                name: record.name,
                                disabled: record.executiveMeetChangeRecordId !== this.table.state.data[0].executiveMeetChangeRecordId,
                            })
                        }
                    }}
                    {...config}
                    fetchConfig={{
                        apiName: 'getZjTzExecutiveMeetChangeRecordList',
                        otherParams: {
                            executiveMeetId: rowData.executiveMeetId
                        }
                    }}
                    method={{
                        goBack: (obj) => {
                            this.props.dispatch(
                                goBack()
                            )
                        },
                        buttonHide: () => {
                            if (rowData.statusId === '1' || rowData.statusId === '2') {
                                return true;
                            }
                            return false;
                        },
                        buttonDisabled: (obj) => {
                            if (obj.btnCallbackFn.getSelectedRows().length > 0) {
                                return false;
                            } else {
                                return true;
                            }
                        },
                        editClick: (obj) => {
                            this.table.clearSelectedRows();
                        },
                        diysubmitClick: (obj) => {
                            obj.btnCallbackFn.setBtnsLoading('add', 'diysubmitClick');
                            rowData.personnelList = obj._formData.personnelList;
                            this.props.myFetch('updateZjTzExecutiveMeet', rowData).then(
                                ({ success, message }) => {
                                    if (success) {
                                        Msg.success(message);
                                        obj.btnCallbackFn.closeDrawer(false);
                                        obj.btnCallbackFn.refresh();
                                        obj.btnCallbackFn.setBtnsLoading('remove', 'diysubmitClick');
                                    } else {
                                        Msg.error(message);
                                        obj.btnCallbackFn.setBtnsLoading('remove', 'diysubmitClick');
                                    }
                                }
                            );
                        },
                        diydelClick: (obj) => {
                            if (obj.response.success) {
                                rowData.personnelList = this.table.state.data[0].personnelList;
                                this.props.myFetch('updateZjTzExecutiveMeet', rowData).then(
                                    ({ success, message }) => {
                                    }
                                );
                            } else {
                                this.table.clearSelectedRows();
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
                    formConfig={[
                        {
                            isInTable: false,
                            form: {
                                field: 'executiveMeetId',
                                type: 'string',
                                hide: true,
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                field: 'executiveMeetChangeRecordId',
                                type: 'string',
                                hide: true,
                            }
                        },
                        {
                            table: {
                                title: '????????????',
                                filter: true,
                                width: 300,
                                fixed: 'left',
                                dataIndex: 'projectId',
                                key: 'projectId',
                                type: 'select',
                                fieldsConfig: {
                                    field: 'projectId',
                                    type: 'selectByPaging',
                                    optionConfig: {
                                        label: 'projectName',
                                        value: 'projectId'
                                    },
                                    fetchConfig: {
                                        apiName: "getZjTzProManageList"
                                    },
                                }
                            },
                            form: {
                                field: 'projectId',
                                type: 'select',
                                optionConfig: {
                                    label: 'projectName',
                                    value: 'projectId'
                                },
                                fetchConfig: {
                                    apiName: "getZjTzProManageList"
                                },
                                editDisabled: true,
                                placeholder: '?????????'
                            },
                        },
                        {
                            isInSearch: false,
                            table: {
                                title: '???????????????',
                                onClick: 'detail',
                                width: 100,
                                dataIndex: 'periodName',
                                key: 'periodName'
                            },
                            form: {
                                type: 'select',
                                required: true,
                                field: 'periodId',
                                optionConfig: {
                                    label: 'itemName',
                                    value: 'itemId'
                                },
                                fetchConfig: {
                                    apiName: 'getBaseCodeSelect',
                                    otherParams: { itemId: 'huiYiJieCi' },
                                },
                                onChange: (val) => {
                                    for (let i = 0; i < this.table.state.data.length; i++) {
                                        if (val === this.table.state.data[i].periodId) {
                                            Msg.error('?????????????????????????????????????????????????????????');
                                            break;
                                        }
                                    }
                                },
                                allowClear: true,
                                placeholder: '?????????',
                                editDisabled: true,
                                spanForm: 12,
                                formItemLayout: {
                                    labelCol: {
                                        xs: { span: 24 },
                                        sm: { span: 8 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 24 },
                                        sm: { span: 16 }
                                    }
                                }
                            }
                        },
                        {
                            isInSearch: false,
                            table: {
                                title: '????????????',
                                format: 'YYYY-MM-DD',
                                dataIndex: 'businessDate',
                                key: 'businessDate',
                                width: 100
                            },
                            form: {
                                type: 'date',
                                placeholder: '?????????',
                                required: true,
                                editDisabled: true,
                                spanForm: 12,
                                formItemLayout: {
                                    labelCol: {
                                        xs: { span: 24 },
                                        sm: { span: 8 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 24 },
                                        sm: { span: 16 }
                                    }
                                }
                            }
                        },
                        {
                            isInSearch: false,
                            table: {
                                title: '????????????????????????',
                                format: 'YYYY-MM-DD',
                                dataIndex: 'startDate',
                                key: 'startDate',
                                width: 150
                            },
                            form: {
                                type: 'date',
                                placeholder: '?????????',
                                required: true,
                                editDisabled: true,
                                spanForm: 12,
                                formItemLayout: {
                                    labelCol: {
                                        xs: { span: 24 },
                                        sm: { span: 8 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 24 },
                                        sm: { span: 16 }
                                    }
                                }
                            }
                        },
                        {
                            isInSearch: false,
                            table: {
                                title: '????????????????????????',
                                format: 'YYYY-MM-DD',
                                dataIndex: 'endDate',
                                key: 'endDate',
                                width: 150
                            },
                            form: {
                                type: 'date',
                                placeholder: '?????????',
                                required: true,
                                editDisabled: true,
                                spanForm: 12,
                                formItemLayout: {
                                    labelCol: {
                                        xs: { span: 24 },
                                        sm: { span: 8 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 24 },
                                        sm: { span: 16 }
                                    }
                                }
                            }
                        },
                        {
                            isInSearch: false,
                            table: {
                                title: '??????',
                                dataIndex: 'remarks',
                                key: 'remarks',
                                width: 150
                            },
                            form: {
                                type: 'textarea',
                                editDisabled: true,
                                placeholder: '?????????'
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: '??????',
                                field: 'zjTzFileList',
                                type: 'files',
                                showDownloadIcon: true,//????????????????????????
                                onPreview: "bind:_docFilesByOfficeUrl",//365??????
                                
                                editDisabled: true,
                                fetchConfig: {
                                    apiName: window.configs.domain + 'upload'
                                }
                            }
                        },
                        {
                            isInSearch: false,
                            table: {
                                title: '????????????',
                                dataIndex: 'changeNum',
                                key: 'changeNum',
                                width: 80
                            },
                            isInForm: false
                        },
                        {
                            isInTable: false,
                            form: {
                                type: 'qnnTable',
                                field: 'personnelList',
                                incToForm: true,
                                qnnTableConfig: {
                                    actionBtnsPosition: "top",
                                    antd: {
                                        rowKey: 'executivePersonnelId',
                                        size: 'small'
                                    },
                                    paginationConfig: {
                                        position: 'bottom'
                                    },
                                    wrappedComponentRef: (me) => {
                                        this.tables = me;
                                    },
                                    formConfig: [
                                        {
                                            isInTable: false,
                                            form: {
                                                label: '??????id',
                                                field: 'executivePersonnelId',
                                                hide: true
                                            }
                                        },
                                        {
                                            table: {
                                                title: '??????',
                                                dataIndex: 'index',
                                                key: 'index',
                                                width: 50,
                                                fixed: 'left',
                                                render: (data, rowData, index) => {
                                                    return index + 1;
                                                }
                                            },
                                            isInForm: false
                                        },
                                        {
                                            table: {
                                                title: '????????????',
                                                dataIndex: 'shareholderName',
                                                key: 'shareholderName',
                                                tdEdit: true,
                                                fixed: 'left',
                                                width: 150,
                                                tooltip: 8
                                            },
                                            form: {
                                                type: 'string',
                                                placeholder: '?????????',
                                            },
                                        },
                                        {
                                            table: {
                                                title: '??????',
                                                dataIndex: 'proportion',
                                                key: 'proportion',
                                                tdEdit: true,
                                                width: 100,
                                            },
                                            form: {
                                                type: 'number',
                                                placeholder: '?????????',
                                            },
                                        },
                                        {
                                            table: {
                                                title: '?????????',
                                                dataIndex: 'directorz',
                                                key: 'directorz',
                                                tdEdit: true,
                                                width: 100,
                                                tooltip: 5
                                            },
                                            form: {
                                                type: 'string',
                                                placeholder: '?????????'
                                            },
                                        },
                                        {
                                            table: {
                                                title: '??????',
                                                dataIndex: 'director',
                                                key: 'director',
                                                tdEdit: true,
                                                width: 100,
                                                tooltip: 5
                                            },
                                            form: {
                                                type: 'string',
                                                placeholder: '?????????',
                                            },
                                        },
                                        {
                                            table: {
                                                title: '?????????',
                                                dataIndex: 'supervisorz',
                                                key: 'supervisorz',
                                                tdEdit: true,
                                                width: 100,
                                                tooltip: 5
                                            },
                                            form: {
                                                type: 'string',
                                                placeholder: '?????????',
                                            },
                                        },
                                        {
                                            table: {
                                                title: '??????',
                                                dataIndex: 'supervisor',
                                                key: 'supervisor',
                                                tdEdit: true,
                                                width: 100,
                                                tooltip: 5
                                            },
                                            form: {
                                                type: 'string',
                                                placeholder: '?????????',
                                            },
                                        },
                                        {
                                            table: {
                                                title: '?????????????????????',
                                                dataIndex: 'manager',
                                                key: 'manager',
                                                tdEdit: true,
                                                width: 120,
                                                tooltip: 6
                                            },
                                            form: {
                                                type: 'string',
                                                placeholder: '?????????',
                                            },
                                        },
                                        {
                                            table: {
                                                title: '??????',
                                                dataIndex: 'chief1',
                                                key: 'chief1',
                                                tdEdit: true,
                                                width: 100,
                                                tooltip: 5
                                            },
                                            form: {
                                                type: 'string',
                                                placeholder: '?????????',
                                            },
                                        },
                                        {
                                            table: {
                                                title: '??????',
                                                dataIndex: 'chief2',
                                                key: 'chief2',
                                                tdEdit: true,
                                                width: 100,
                                                tooltip: 5
                                            },
                                            form: {
                                                type: 'string',
                                                placeholder: '?????????',
                                            },
                                        },
                                        {
                                            table: {
                                                title: '??????',
                                                dataIndex: 'chief3',
                                                key: 'chief3',
                                                tdEdit: true,
                                                width: 100,
                                                tooltip: 5
                                            },
                                            form: {
                                                type: 'string',
                                                placeholder: '?????????',
                                            },
                                        },
                                        {
                                            table: {
                                                title: '??????',
                                                dataIndex: 'remarks',
                                                key: 'remarks',
                                                tdEdit: true,
                                                width: 150,
                                                tooltip: 8
                                            },
                                            form: {
                                                type: 'string',
                                                placeholder: '?????????',
                                            },
                                        },
                                        {
                                            table: {
                                                title: '?????????',
                                                dataIndex: 'orderNum',
                                                key: 'orderNum',
                                                tdEdit: true,
                                                width: 80,
                                            },
                                            form: {
                                                type: 'string',
                                                placeholder: '?????????',
                                            },
                                        },
                                    ],
                                    actionBtns: [
                                        {
                                            name: "addRow",
                                            icon: "plus",
                                            type: "primary",
                                            label: "?????????"
                                        },
                                        {
                                            name: 'del',
                                            icon: 'delete',
                                            type: 'danger',
                                            label: '??????'
                                        }
                                    ]
                                }
                            }
                        }
                    ]}
                />
            </div>
        );
    }
}

export default index;