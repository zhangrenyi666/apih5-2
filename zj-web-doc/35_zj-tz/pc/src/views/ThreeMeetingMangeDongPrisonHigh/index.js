import React, { Component } from "react";
import QnnTable from "../modules/qnn-table";
import { push } from "react-router-redux";
import s from "./style.less";
import { message as Msg, Modal, Tooltip } from "antd";
const confirm = Modal.confirm;
const config = {
    antd: {
        rowKey: function (row) {
            return row.executiveMeetId
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
            proNameId: props.loginAndLogoutInfo.loginInfo.userInfo.curCompany ? props.loginAndLogoutInfo.loginInfo.userInfo.curCompany.projectId : '',
        }
    }
    render() {
        const { proNameId } = this.state;
        let { myPublic: { apis } } = this.props;
        console.log(apis);
        // console.log(myPublic);
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
                        apiName: 'getZjTzExecutiveMeetList',
                        otherParams: {
                            projectId: proNameId
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
                                    showSearch: true,
                                    type: 'select',
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
                                showSearch: true,
                                addDisabled: true,
                                disabled: true,
                                editDisabled: true,
                                initialValue: () => {
                                    return proNameId
                                },
                                optionConfig: {
                                    label: 'projectName',
                                    value: 'projectId'
                                },
                                fetchConfig: {
                                    apiName: "getZjTzProManageList"
                                },
                                placeholder: '?????????',
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
                                filter: true,
                                format: 'YYYY-MM-DD',
                                dataIndex: 'businessDate',
                                key: 'businessDate',
                                width: 100,
                                fieldsConfig: {
                                    type: 'rangeDate',
                                    field: 'businessDateList'
                                }
                            },
                            form: {
                                type: 'date',
                                placeholder: '?????????',
                                required: true,
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
                                filter: true,
                                format: 'YYYY-MM-DD',
                                dataIndex: 'startDate',
                                key: 'startDate',
                                width: 150
                            },
                            form: {
                                type: 'date',
                                placeholder: '?????????',
                                required: true,
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
                                filter: true,
                                format: 'YYYY-MM-DD',
                                dataIndex: 'endDate',
                                key: 'endDate',
                                width: 150
                            },
                            form: {
                                type: 'date',
                                placeholder: '?????????',
                                required: true,
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
                                title: '???????????????',
                                dataIndex: 'legal',
                                key: 'legal',
                                width: 150
                            },
                            form: {
                                type: 'string',
                                addDisabled: true,
                                editDisabled: true,
                                placeholder: '?????????',
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
                            isInTable: false,
                            form: {
                                label: '??????',
                                field: 'remarks',
                                type: 'textarea',
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

                                fetchConfig: {
                                    apiName: window.configs.domain + 'upload'
                                }
                            }
                        },
                        {
                            isInSearch: false,
                            table: {
                                title: '????????????',
                                dataIndex: 'conentDesc',
                                key: 'conentDesc',
                                width: 200
                            },
                            isInForm: false
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
                            table: {
                                title: '????????????',
                                dataIndex: 'statusName',
                                key: 'statusName',
                                width: 80,
                                fixed: 'right'
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
                                    fetchConfig: {
                                        apiName: 'getZjTzExecutiveMeetListFromProjectShareholder',
                                        otherParams: () => {
                                            if (this.table?.qnnForm?.form?.getFieldValue?.('executiveMeetId')) {
                                                return {
                                                    executiveMeetId: this.table.qnnForm.form.getFieldValue('executiveMeetId')
                                                }
                                            } else {
                                                return {
                                                    projectId: proNameId
                                                }
                                            }
                                        }
                                    },
                                    actionBtnsPosition: "top",
                                    antd: {
                                        rowKey: 'executivePersonnelId',
                                        size: 'small',
                                        // rowClassName: (record,index) => {
                                        //     if (record.directorz !== record.directorzOld) {
                                        //         $('input[field="directorz"]').each((i) => {
                                        //             if (index === i) {
                                        //                  $('input[field="directorz"]').eq(i).css('color','red !important')
                                        //             }
                                        //         })
                                        //     }
                                        //     return '';
                                        // }
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
                                                title: '????????????',
                                                dataIndex: 'shareType',
                                                key: 'shareType',
                                                tdEdit: true,
                                                width: 100,
                                            },
                                            form: {
                                                type: 'select',
                                                field: 'shareTypeId',
                                                placeholder: '?????????',
                                                optionConfig: {
                                                    label: 'itemName',
                                                    value: 'itemId'
                                                },
                                                fetchConfig: {
                                                    apiName: "getBaseCodeSelect",
                                                    otherParams: {
                                                        itemId: 'guDongLeiXing'
                                                    }
                                                },
                                            },
                                        },
                                        {
                                            table: {
                                                title: '?????????',
                                                dataIndex: 'directorz',
                                                key: 'directorz',
                                                tdEdit: true,
                                                width: 100,
                                                render: (data, rowData) => {
                                                    if (rowData.directorz !== rowData.directorzOld) {
                                                        return <Tooltip title={data}><div style={{ color: 'red', width: '100%', overflow: 'hidden', textOverflow: 'ellipsis', whiteSpace: 'nowrap' }}>{data}</div></Tooltip>
                                                    } else {
                                                        return data;
                                                    }
                                                }
                                            },
                                            form: {
                                                type: 'string',
                                                placeholder: '?????????',
                                                // style: (val) => {
                                                //     for (let i = 0; i < this.tables.state.data.length; i++) {
                                                //         if(val === this.tables.state.data[i].directorz && this.tables.state.data[i].directorz !== this.tables.state.data[i].directorzOld && this.tables.state.data[i].executiveMeetId){
                                                //             return {color:'red'};
                                                //         }
                                                //     }
                                                // }
                                            },
                                        },
                                        {
                                            table: {
                                                title: '??????',
                                                dataIndex: 'director',
                                                key: 'director',
                                                tdEdit: true,
                                                width: 100,
                                                render: (data, rowData) => {
                                                    if (rowData.director !== rowData.directorOld) {
                                                        return <Tooltip title={data}><div style={{ color: 'red', width: '100%', overflow: 'hidden', textOverflow: 'ellipsis', whiteSpace: 'nowrap' }}>{data}</div></Tooltip>
                                                    } else {
                                                        return data;
                                                    }
                                                }
                                            },
                                            form: {
                                                type: 'string',
                                                placeholder: '?????????'
                                            },
                                        },
                                        {
                                            table: {
                                                title: '?????????',
                                                dataIndex: 'supervisorz',
                                                key: 'supervisorz',
                                                tdEdit: true,
                                                width: 100,
                                                render: (data, rowData) => {
                                                    if (rowData.supervisorz !== rowData.supervisorzOld) {
                                                        return <Tooltip title={data}><div style={{ color: 'red', width: '100%', overflow: 'hidden', textOverflow: 'ellipsis', whiteSpace: 'nowrap' }}>{data}</div></Tooltip>
                                                    } else {
                                                        return data;
                                                    }
                                                }
                                            },
                                            form: {
                                                type: 'string',
                                                placeholder: '?????????'
                                            },
                                        },
                                        {
                                            table: {
                                                title: '??????',
                                                dataIndex: 'supervisor',
                                                key: 'supervisor',
                                                tdEdit: true,
                                                width: 100,
                                                render: (data, rowData) => {
                                                    if (rowData.supervisor !== rowData.supervisorOld) {
                                                        return <Tooltip title={data}><div style={{ color: 'red', width: '100%', overflow: 'hidden', textOverflow: 'ellipsis', whiteSpace: 'nowrap' }}>{data}</div></Tooltip>
                                                    } else {
                                                        return data;
                                                    }
                                                }
                                            },
                                            form: {
                                                type: 'string',
                                                placeholder: '?????????'
                                            },
                                        },
                                        {
                                            table: {
                                                title: '?????????????????????',
                                                dataIndex: 'manager',
                                                key: 'manager',
                                                tdEdit: true,
                                                width: 120,
                                                render: (data, rowData) => {
                                                    if (rowData.manager !== rowData.managerOld) {
                                                        return <Tooltip title={data}><div style={{ color: 'red', width: '100%', overflow: 'hidden', textOverflow: 'ellipsis', whiteSpace: 'nowrap' }}>{data}</div></Tooltip>
                                                    } else {
                                                        return data;
                                                    }
                                                }
                                            },
                                            form: {
                                                type: 'string',
                                                placeholder: '?????????'
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
                                width: 80,
                                btns: [
                                    {
                                        name: 'PolicyDetail',
                                        render: (rowData) => {
                                            return '<a>????????????</a>';
                                        },
                                        onClick: (obj) => {
                                            const { mainModule } = obj.props.myPublic.appInfo;
                                            obj.props.dispatch(
                                                push(`${mainModule}ThreeMeetingMangeDongPrisonHighDetail/${encodeURIComponent(JSON.stringify(obj.rowData))}`)
                                            )
                                        },
                                    }
                                ]
                            }
                        }
                    ]}
                    method={{
                        addClick: (obj) => {
                            if (proNameId === 'all') {
                                obj.btnCallbackFn.closeDrawer();
                                obj.btnCallbackFn.clearSelectedRows();
                                Msg.warn('???????????????????????????')
                            } else {
                                // console.log(obj.btnCallbackFn.qnnForm.props.Pstate.drawerDetailTitle);
                                setTimeout(() => {
                                    if (obj?.btnCallbackFn?.qnnForm?.props?.Pstate?.drawerDetailTitle === '??????') {
                                        const { myFetch } = obj.props;
                                        myFetch('getZjTzProManageDetails', {//??????????????????
                                            projectId: proNameId
                                        }).then(
                                            ({ data, success, message }) => {
                                                if (success) {
                                                    // Msg.success(message);
                                                    if (data && data.legalPersonName) {
                                                        this.table.qnnForm.form.setFieldsValue({
                                                            legal: data.legalPersonName
                                                        })
                                                    }
                                                } else {
                                                    // Msg.error(message)
                                                }
                                            }
                                        );
                                    }
                                }, 200)

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
                            }
                        },
                        delClick: (obj) => {
                            const { myFetch } = obj.props;
                            let statusIdArry = [];
                            if (obj.selectedRows.length > 0) {
                                for (var i = 0; i < obj.selectedRows.length; i++) {
                                    if (obj.selectedRows[i].statusId === '1' || obj.selectedRows[i].statusId === '2') {
                                        statusIdArry.push(obj.selectedRows[i].statusId);
                                    }
                                }
                                if (statusIdArry.length > 0) {
                                    obj.btnCallbackFn.closeDrawer();
                                    Msg.warn('???????????????????????????!');
                                    this.table.clearSelectedRows();
                                } else {
                                    confirm({
                                        title: "????????????????",
                                        okText: "??????",
                                        cancelText: "??????",
                                        onOk: () => {
                                            myFetch('batchDeleteUpdateZjTzExecutiveMeet', obj.selectedRows).then(
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
                        appearClick: (obj) => {
                            if (obj.selectedRows.length === 1) {
                                if (obj.selectedRows[0].statusId === '0' || obj.selectedRows[0].statusId === '3') {
                                    confirm({
                                        title: "????????????????",
                                        okText: "??????",
                                        cancelText: "??????",
                                        onOk: () => {
                                            this.props.myFetch('correctiveZjTzExecutiveMeet', ...obj.selectedRows).then(
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
                                } else {
                                    Msg.warn('?????????????????????????????????????????????')
                                }
                            } else {
                                Msg.warn('??????????????????????????????')
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