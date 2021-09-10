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
                                title: '项目名称',
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
                                placeholder: '请选择'
                            },
                        },
                        {
                            isInSearch: false,
                            table: {
                                title: '董事会期次',
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
                                            Msg.error('该项目下这个届次已经添加，请去变更吧！');
                                            break;
                                        }
                                    }
                                },
                                allowClear: true,
                                placeholder: '请选择',
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
                                title: '填报日期',
                                format: 'YYYY-MM-DD',
                                dataIndex: 'businessDate',
                                key: 'businessDate',
                                width: 100
                            },
                            form: {
                                type: 'date',
                                placeholder: '请选择',
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
                                title: '任职期限开始时间',
                                format: 'YYYY-MM-DD',
                                dataIndex: 'startDate',
                                key: 'startDate',
                                width: 150
                            },
                            form: {
                                type: 'date',
                                placeholder: '请选择',
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
                                title: '任职期限结束时间',
                                format: 'YYYY-MM-DD',
                                dataIndex: 'endDate',
                                key: 'endDate',
                                width: 150
                            },
                            form: {
                                type: 'date',
                                placeholder: '请选择',
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
                                title: '备注',
                                dataIndex: 'remarks',
                                key: 'remarks',
                                width: 150
                            },
                            form: {
                                type: 'textarea',
                                editDisabled: true,
                                placeholder: '请输入'
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
                                
                                editDisabled: true,
                                fetchConfig: {
                                    apiName: window.configs.domain + 'upload'
                                }
                            }
                        },
                        {
                            isInSearch: false,
                            table: {
                                title: '变更次数',
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
                                                label: '主键id',
                                                field: 'executivePersonnelId',
                                                hide: true
                                            }
                                        },
                                        {
                                            table: {
                                                title: '序号',
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
                                                title: '股东名称',
                                                dataIndex: 'shareholderName',
                                                key: 'shareholderName',
                                                tdEdit: true,
                                                fixed: 'left',
                                                width: 150,
                                                tooltip: 8
                                            },
                                            form: {
                                                type: 'string',
                                                placeholder: '请输入',
                                            },
                                        },
                                        {
                                            table: {
                                                title: '股比',
                                                dataIndex: 'proportion',
                                                key: 'proportion',
                                                tdEdit: true,
                                                width: 100,
                                            },
                                            form: {
                                                type: 'number',
                                                placeholder: '请输入',
                                            },
                                        },
                                        {
                                            table: {
                                                title: '董事长',
                                                dataIndex: 'directorz',
                                                key: 'directorz',
                                                tdEdit: true,
                                                width: 100,
                                                tooltip: 5
                                            },
                                            form: {
                                                type: 'string',
                                                placeholder: '请输入'
                                            },
                                        },
                                        {
                                            table: {
                                                title: '董事',
                                                dataIndex: 'director',
                                                key: 'director',
                                                tdEdit: true,
                                                width: 100,
                                                tooltip: 5
                                            },
                                            form: {
                                                type: 'string',
                                                placeholder: '请输入',
                                            },
                                        },
                                        {
                                            table: {
                                                title: '监事长',
                                                dataIndex: 'supervisorz',
                                                key: 'supervisorz',
                                                tdEdit: true,
                                                width: 100,
                                                tooltip: 5
                                            },
                                            form: {
                                                type: 'string',
                                                placeholder: '请输入',
                                            },
                                        },
                                        {
                                            table: {
                                                title: '监事',
                                                dataIndex: 'supervisor',
                                                key: 'supervisor',
                                                tdEdit: true,
                                                width: 100,
                                                tooltip: 5
                                            },
                                            form: {
                                                type: 'string',
                                                placeholder: '请输入',
                                            },
                                        },
                                        {
                                            table: {
                                                title: '项目公司总经理',
                                                dataIndex: 'manager',
                                                key: 'manager',
                                                tdEdit: true,
                                                width: 120,
                                                tooltip: 6
                                            },
                                            form: {
                                                type: 'string',
                                                placeholder: '请输入',
                                            },
                                        },
                                        {
                                            table: {
                                                title: '总工',
                                                dataIndex: 'chief1',
                                                key: 'chief1',
                                                tdEdit: true,
                                                width: 100,
                                                tooltip: 5
                                            },
                                            form: {
                                                type: 'string',
                                                placeholder: '请输入',
                                            },
                                        },
                                        {
                                            table: {
                                                title: '总会',
                                                dataIndex: 'chief2',
                                                key: 'chief2',
                                                tdEdit: true,
                                                width: 100,
                                                tooltip: 5
                                            },
                                            form: {
                                                type: 'string',
                                                placeholder: '请输入',
                                            },
                                        },
                                        {
                                            table: {
                                                title: '总经',
                                                dataIndex: 'chief3',
                                                key: 'chief3',
                                                tdEdit: true,
                                                width: 100,
                                                tooltip: 5
                                            },
                                            form: {
                                                type: 'string',
                                                placeholder: '请输入',
                                            },
                                        },
                                        {
                                            table: {
                                                title: '备注',
                                                dataIndex: 'remarks',
                                                key: 'remarks',
                                                tdEdit: true,
                                                width: 150,
                                                tooltip: 8
                                            },
                                            form: {
                                                type: 'string',
                                                placeholder: '请输入',
                                            },
                                        },
                                        {
                                            table: {
                                                title: '排序号',
                                                dataIndex: 'orderNum',
                                                key: 'orderNum',
                                                tdEdit: true,
                                                width: 80,
                                            },
                                            form: {
                                                type: 'string',
                                                placeholder: '请输入',
                                            },
                                        },
                                    ],
                                    actionBtns: [
                                        {
                                            name: "addRow",
                                            icon: "plus",
                                            type: "primary",
                                            label: "新增行"
                                        },
                                        {
                                            name: 'del',
                                            icon: 'delete',
                                            type: 'danger',
                                            label: '删除'
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