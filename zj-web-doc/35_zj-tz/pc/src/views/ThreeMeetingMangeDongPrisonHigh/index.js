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
                                title: '项目名称',
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
                                placeholder: '请选择',
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
                                placeholder: '请选择',
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
                                title: '任职期限开始时间',
                                filter: true,
                                format: 'YYYY-MM-DD',
                                dataIndex: 'startDate',
                                key: 'startDate',
                                width: 150
                            },
                            form: {
                                type: 'date',
                                placeholder: '请选择',
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
                                title: '任职期限结束时间',
                                filter: true,
                                format: 'YYYY-MM-DD',
                                dataIndex: 'endDate',
                                key: 'endDate',
                                width: 150
                            },
                            form: {
                                type: 'date',
                                placeholder: '请选择',
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
                                title: '法定代表人',
                                dataIndex: 'legal',
                                key: 'legal',
                                width: 150
                            },
                            form: {
                                type: 'string',
                                addDisabled: true,
                                editDisabled: true,
                                placeholder: '请输入',
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
                                label: '备注',
                                field: 'remarks',
                                type: 'textarea',
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

                                fetchConfig: {
                                    apiName: window.configs.domain + 'upload'
                                }
                            }
                        },
                        {
                            isInSearch: false,
                            table: {
                                title: '上报内容',
                                dataIndex: 'conentDesc',
                                key: 'conentDesc',
                                width: 200
                            },
                            isInForm: false
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
                            table: {
                                title: '审核状态',
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
                                                title: '股东类型',
                                                dataIndex: 'shareType',
                                                key: 'shareType',
                                                tdEdit: true,
                                                width: 100,
                                            },
                                            form: {
                                                type: 'select',
                                                field: 'shareTypeId',
                                                placeholder: '请选择',
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
                                                title: '董事长',
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
                                                placeholder: '请输入',
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
                                                title: '董事',
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
                                                placeholder: '请输入'
                                            },
                                        },
                                        {
                                            table: {
                                                title: '监事长',
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
                                                placeholder: '请输入'
                                            },
                                        },
                                        {
                                            table: {
                                                title: '监事',
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
                                                placeholder: '请输入'
                                            },
                                        },
                                        {
                                            table: {
                                                title: '项目公司总经理',
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
                                                placeholder: '请输入'
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
                                width: 80,
                                btns: [
                                    {
                                        name: 'PolicyDetail',
                                        render: (rowData) => {
                                            return '<a>变更记录</a>';
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
                                Msg.warn('请选择右上角项目！')
                            } else {
                                // console.log(obj.btnCallbackFn.qnnForm.props.Pstate.drawerDetailTitle);
                                setTimeout(() => {
                                    if (obj?.btnCallbackFn?.qnnForm?.props?.Pstate?.drawerDetailTitle === '新增') {
                                        const { myFetch } = obj.props;
                                        myFetch('getZjTzProManageDetails', {//调接口，赋值
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
                                Msg.warn('审核中的数据不能修改！')
                                obj.btnCallbackFn.closeDrawer();
                            } else if (obj.selectedRows[0].statusId === '2') {
                                Msg.warn('审核结束的数据不能修改！')
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
                                    Msg.warn('请选择未审核的数据!');
                                    this.table.clearSelectedRows();
                                } else {
                                    confirm({
                                        title: "确定删除么?",
                                        okText: "确认",
                                        cancelText: "取消",
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
                                Msg.warn('请选择数据!');
                            }
                        },
                        appearClick: (obj) => {
                            if (obj.selectedRows.length === 1) {
                                if (obj.selectedRows[0].statusId === '0' || obj.selectedRows[0].statusId === '3') {
                                    confirm({
                                        title: "确定上报么?",
                                        okText: "确认",
                                        cancelText: "取消",
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
                                    Msg.warn('审核中或审核结束数据不可上报！')
                                }
                            } else {
                                Msg.warn('请选择一条数据上报！')
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