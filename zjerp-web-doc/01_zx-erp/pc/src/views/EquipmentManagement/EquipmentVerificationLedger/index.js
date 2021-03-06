import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import s from "./style.less";
import { message as Msg, Modal } from "antd";
const { confirm } = Modal;
const config = {
    antd: {
        rowKey: 'id',
        size: 'small'
    },
    drawerConfig: {
        width: '1200px'
    },
    paginationConfig: {
        position: 'bottom'
    },
    formItemLayout: {
        labelCol: {
            xs: { span: 11 },
            sm: { span: 11 }
        },
        wrapperCol: {
            xs: { span: 13 },
            sm: { span: 13 }
        }
    }
};
class index extends Component {
    constructor(props) {
        super(props);
        const curCompany = this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany;
        const lockProject = this.props.loginAndLogoutInfo.loginInfo.userInfo?.lockProject;
        this.state = {
            departmentId: ((curCompany?.ext1 === '1' || curCompany?.ext1 === '2') && lockProject?.projectId) ? (lockProject.projectId !== 'all' ? lockProject.projectId : curCompany?.companyId) : curCompany?.projectId,
            departmentName: ((curCompany?.ext1 === '1' || curCompany?.ext1 === '2') && lockProject?.projectId) ? (lockProject.projectId !== 'all' ? lockProject.projectName : curCompany?.companyName) : curCompany?.projectName
        }
    }
    render () {
        const { departmentId, departmentName } = this.state;
        const { realName } = this.props.loginAndLogoutInfo.loginInfo.userInfo;
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
                        apiName: 'getZxEqEquipFactList',
                        otherParams: {
                            orgID: departmentId
                        }
                    }}
                    actionBtns={{
                        apiName: "getSysMenuBtn",
                        otherParams: (obj) => {
                            let props = obj.props;
                            let curRouteData = props.routerInfo.routeData[props.routerInfo.curKey];
                            return {
                                menuParentId: curRouteData._value,
                                tableField: "EquipmentVerificationLedger"
                            }
                        }
                    }}
                    method={{
                        editDisabled: (obj) => {
                            let data = obj.btnCallbackFn.getSelectedRows();
                            if (data.length === 1 && data[0].isUse !== '1' && (data[0].auditStatus === '' || data[0].auditStatus === '0')) {
                                return false;
                            } else {
                                return true;
                            }
                        },
                        auditDisabled: (obj) => {
                            let data = obj.btnCallbackFn.getSelectedRows();
                            if (data.length === 1 && data[0].isUse !== '1' && (data[0].auditStatus === '' || data[0].auditStatus === '0')) {
                                return false;
                            } else {
                                return true;
                            }
                        },
                        auditOnClick: (obj) => {
                            confirm({
                                content: '????????????????????????????',
                                onOk: () => {
                                    obj.selectedRows[0].auditStatus = '1';
                                    this.props.myFetch('auditZxEqEquipFact', ...obj.selectedRows).then(
                                        ({ data, success, message }) => {
                                            if (success) {
                                                Msg.success(message);
                                                obj.btnCallbackFn.refresh();
                                                obj.btnCallbackFn.clearSelectedRows();
                                            } else {
                                                Msg.error(message);
                                            }
                                        }
                                    );
                                }
                            });
                        },
                        auditeeDisabled: (obj) => {
                            let data = obj.btnCallbackFn.getSelectedRows();
                            if (data.length === 1 && data[0].isUse !== '1' && data[0].auditStatus === '1') {
                                return false;
                            } else {
                                return true;
                            }
                        },
                        auditeeOnClick: (obj) => {
                            confirm({
                                content: '???????????????????????????????',
                                onOk: () => {
                                    obj.selectedRows[0].auditStatus = '0';
                                    this.props.myFetch('auditZxEqEquipFact', ...obj.selectedRows).then(
                                        ({ data, success, message }) => {
                                            if (success) {
                                                Msg.success(message);
                                                obj.btnCallbackFn.refresh();
                                                obj.btnCallbackFn.clearSelectedRows();
                                            } else {
                                                Msg.error(message);
                                            }
                                        }
                                    );
                                }
                            });
                        },
                        delDisabled: (obj) => {
                            let data = obj.btnCallbackFn.getSelectedRows();
                            if (data.length && !data.filter(item => item.auditStatus === '1' && (item.isUse === '0' || item.isUse === '1')).length) {
                                return false;
                            } else {
                                return true;
                            }
                        }
                    }}
                    formConfig={[
                        {
                            isInTable: false,
                            form: {
                                field: 'id',
                                type: 'string',
                                hide: true,
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                field: 'comID',
                                type: 'string',
                                hide: true,
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                field: 'comName',
                                type: 'string',
                                hide: true,
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                field: 'orgName',
                                type: 'string',
                                hide: true,
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                field: 'depID',
                                type: 'string',
                                initialValue: departmentId,
                                hide: true,
                            }
                        },
                        {
                            isInForm: false,
                            table: {
                                title: '????????????',
                                dataIndex: 'comName',
                                key: 'comName',
                                width: 150,
                                fixed: 'left',
                                filter: true,
                                onClick: 'detail'
                            },
                            form: {
                                type: 'string',
                                spanForm: 8
                            }
                        },
                        {
                            table: {
                                title: '????????????',
                                dataIndex: 'orgName',
                                key: 'orgName',
                                width: 150,
                                tooltip: 15,
                                fixed: 'left',
                                filter: true,
                                fieldsConfig: {
                                    type: 'select',
                                    field: 'orgIDSearch',
                                    optionConfig: {
                                        label: 'departmentName',
                                        value: 'departmentId'
                                    },
                                    fetchConfig: {
                                        apiName: 'getSysCompanyProject',
                                        otherParams: {
                                            departmentId: departmentId
                                        }
                                    },
                                }
                            },
                            form: {
                                field:'orgID',
                                type: 'select',
                                optionConfig: {
                                    label: 'departmentName',
                                    value: 'departmentId',
                                    linkageFields: {
                                        orgName: 'departmentName',
                                        comID: 'companyId',
                                        comName: 'companyName'
                                    }
                                },
                                fetchConfig: {
                                    apiName: 'getSysProjectBySelect',
                                    otherParams: {
                                        departmentId: departmentId
                                    }
                                },
                                allowClear: false,
                                showSearch: true,
                                onChange: (val, obj) => {
                                    obj.qnnformData.qnnFormProps.tableFns.setDeawerValues({ itemList: [] });
                                },
                                required: true,
                                placeholder: '?????????',
                                spanForm: 8
                            }
                        },
                        {
                            table: {
                                title: '??????',
                                dataIndex: 'periodDate',
                                key: 'periodDate',
                                width: 100,
                                format: 'YYYY-MM',
                                filter: true,
                            },
                            form: {
                                type: 'month',
                                required: true,
                                placeholder: '?????????',
                                spanForm: 8
                            }
                        },
                        {
                            table: {
                                title: '?????????',
                                dataIndex: 'reporter',
                                key: 'reporter',
                                width: 100,
                            },
                            form: {
                                type: 'string',
                                addDisabled: true,
                                editDisabled: true,
                                initialValue: realName,
                                placeholder: '?????????',
                                spanForm: 8
                            }
                        },
                        {
                            table: {
                                title: '??????',
                                dataIndex: 'depName',
                                key: 'depName',
                                width: 100,
                            },
                            form: {
                                type: 'string',
                                addDisabled: true,
                                editDisabled: true,
                                initialValue: departmentName,
                                placeholder: '?????????',
                                spanForm: 8
                            }
                        },
                        {
                            table: {
                                title: '????????????',
                                dataIndex: 'reportDate',
                                key: 'reportDate',
                                width: 100,
                                format: 'YYYY-MM-DD'
                            },
                            form: {
                                type: 'date',
                                required: true,
                                placeholder: '?????????',
                                spanForm: 8
                            }
                        },
                        {
                            table: {
                                title: '??????????????????',
                                dataIndex: 'source1',
                                key: 'source1',
                                width: 120
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '??????????????????',
                                dataIndex: 'source2',
                                key: 'source2',
                                width: 120
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '??????????????????',
                                dataIndex: 'source3',
                                key: 'source3',
                                width: 120
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '????????????',
                                dataIndex: 'auditStatus',
                                key: 'auditStatus',
                                width: 100,
                                render: (data) => {
                                    if (data === '' || data === '0') {
                                        return '?????????';
                                    } else {
                                        return '?????????';
                                    }
                                }
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '????????????',
                                dataIndex: 'isUse',
                                key: 'isUse',
                                width: 100,
                                render: (data) => {
                                    if (data === '1') {
                                        return '???';
                                    } else {
                                        return '???';
                                    }
                                }
                            },
                            isInForm: false
                        },
                        {
                            isInTable: false,
                            form: {
                                label: '????????????',
                                field: 'copyPeriod',
                                type: 'select',
                                optionConfig: {//??????????????????
                                    label: 'period',
                                    value: 'id'
                                },
                                parent: 'orgID',
                                fetchConfig: (obj) => {
                                    if (obj.form.getFieldValue('orgID')) {
                                        return {
                                            apiName: 'getZxEqEquipFactListForCopy',
                                            params: {
                                                orgID: 'orgID'
                                            }
                                        }
                                    } else {
                                        return {};
                                    }
                                },
                                onChange: (val, obj) => {
                                    obj.qnnformData.qnnFormProps.tableFns.setDeawerValues({ itemList: obj.itemData.itemList });
                                },
                                allowClear: false,
                                showSearch: true,
                                placeholder: '?????????',
                                spanForm: 8
                            }
                        },
                        // {
                        //     isInTable: false,
                        //     form: {
                        //         label: '???????????????',
                        //         field: 'isUse',
                        //         type: 'checkbox',
                        //         optionData: [
                        //             {
                        //                 value: '1'
                        //             }
                        //         ],
                        //         dependencies: ["copyPeriod"],
                        //         hide: function (obj) {
                        //             if (obj.form.getFieldValue("copyPeriod")) {
                        //                 return true;
                        //             } else {
                        //                 return false;
                        //             }
                        //         },
                        //         spanForm: 8
                        //     }
                        // },
                        {
                            isInTable: false,
                            form: {
                                type: 'qnnTable',
                                field: 'itemList',
                                incToForm: true,
                                qnnTableConfig: {
                                    actionBtnsPosition: "top",
                                    antd: {
                                        rowKey: 'id',
                                        size: 'small'
                                    },
                                    paginationConfig: {
                                        position: 'bottom'
                                    },
                                    formConfig: [
                                        {
                                            isInTable: false,
                                            form: {
                                                label: '??????id',
                                                field: 'id',
                                                hide: true
                                            }
                                        },
                                        {
                                            isInTable: false,
                                            form: {
                                                label: '??????id',
                                                field: 'equipID',
                                                hide: true
                                            }
                                        },
                                        {
                                            isInTable: false,
                                            form: {
                                                label: '????????????',
                                                field: 'equipNo',
                                                hide: true
                                            }
                                        },
                                        {
                                            table: {
                                                title: '??????',
                                                dataIndex: 'index',
                                                key: 'index',
                                                width: 80,
                                                fixed: 'left',
                                                render: (data, rowData, index) => {
                                                    return index + 1;
                                                }
                                            },
                                            isInForm: false
                                        },
                                        {
                                            table: {
                                                title: "<div>????????????<span style='color: #ff4d4f'>*</span></div>",
                                                dataIndex: 'equipName',
                                                key: 'equipName',
                                                width: 200,
                                                fixed: 'left',
                                                tdEdit: true,
                                            },
                                            form: {
                                                type: 'selectByQnnTable',
                                                optionConfig: {//??????????????????
                                                    label: 'catName',
                                                    value: 'catName'
                                                },
                                                dropdownMatchSelectWidth: 600,
                                                qnnTableConfig: {
                                                    antd: {
                                                        rowKey: "catName"
                                                    },
                                                    fetchConfig: {
                                                        apiName: 'getZxEqResCategoryItemList',
                                                        otherParams: {
                                                            isGroup: '1'
                                                        }
                                                    },
                                                    searchBtnsStyle: "inline",
                                                    formConfig: [
                                                        {
                                                            table: {
                                                                dataIndex: "catCode",
                                                                title: "??????",
                                                            },
                                                            form: {
                                                                type: "string"
                                                            }
                                                        },
                                                        {
                                                            table: {
                                                                dataIndex: "catName",
                                                                title: "??????",
                                                            },
                                                            form: {
                                                                type: "string"
                                                            }
                                                        }
                                                    ]
                                                },
                                                onChange: (val, obj, btnCallbackFn) => {
                                                    let editRowData = btnCallbackFn.getEditedRowData(false);
                                                    editRowData.equipID = obj.itemData.id;
                                                    editRowData.equipNo = obj.itemData.catCode;
                                                    editRowData.equipName = obj.itemData.catName;
                                                    btnCallbackFn.setEditedRowData({ ...editRowData });
                                                },
                                                allowClear: false,
                                                showSearch: true,
                                                required: true,
                                                placeholder: '?????????',
                                            },
                                        },
                                        {
                                            table: {
                                                title: '??????',
                                                dataIndex: 'spec',
                                                key: 'spec',
                                                width: 150,
                                                tdEdit: true
                                            },
                                            form: {
                                                type: 'string',
                                                placeholder: '?????????',
                                            },
                                        },
                                        {
                                            table: {
                                                title: '????????????',
                                                dataIndex: 'factory',
                                                key: 'factory',
                                                width: 150,
                                                tdEdit: true
                                            },
                                            form: {
                                                type: 'string',
                                                placeholder: '?????????',
                                            },
                                        },
                                        {
                                            table: {
                                                title: '?????????',
                                                dataIndex: 'carNo',
                                                key: 'carNo',
                                                width: 150,
                                                tdEdit: true
                                            },
                                            form: {
                                                type: 'string',
                                                placeholder: '?????????',
                                            },
                                        },
                                        {
                                            table: {
                                                title: '?????????',
                                                dataIndex: 'supplier',
                                                key: 'supplier',
                                                width: 150,
                                                tdEdit: true
                                            },
                                            form: {
                                                type: 'string',
                                                placeholder: '?????????',
                                            },
                                        },
                                        {
                                            table: {
                                                title: '????????????',
                                                dataIndex: 'inDate',
                                                key: 'inDate',
                                                width: 150,
                                                format: 'YYYY-MM-DD',
                                                tdEdit: true
                                            },
                                            form: {
                                                type: 'date',
                                                placeholder: '?????????',
                                            },
                                        },
                                        {
                                            table: {
                                                title: '????????????',
                                                dataIndex: 'source',
                                                key: 'source',
                                                width: 150,
                                                type: 'select',
                                                tdEdit: true
                                            },
                                            form: {
                                                type: 'select',
                                                optionConfig: {
                                                    label: 'label',
                                                    value: 'value',
                                                },
                                                optionData: [
                                                    {
                                                        label: "??????",
                                                        value: "2"
                                                    },
                                                    {
                                                        label: "??????????????????",
                                                        value: "3"
                                                    },
                                                    {
                                                        label: "??????",
                                                        value: "1"
                                                    }
                                                ],
                                                allowClear: false,
                                                showSearch: true,
                                                placeholder: '?????????',
                                            },
                                        },
                                        {
                                            table: {
                                                title: '????????????',
                                                dataIndex: 'isOut',
                                                key: 'isOut',
                                                width: 100,
                                                type: 'select',
                                                tdEdit: true
                                            },
                                            form: {
                                                type: 'select',
                                                optionConfig: {
                                                    label: 'label',
                                                    value: 'value',
                                                },
                                                optionData: [
                                                    {
                                                        label: "???",
                                                        value: "0"
                                                    },
                                                    {
                                                        label: "???",
                                                        value: "1"
                                                    }
                                                ],
                                                allowClear: false,
                                                showSearch: true,
                                                placeholder: '?????????',
                                            },
                                        }
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
                            isInTable: false,
                            form: {
                                label: '??????',
                                field: 'remark',
                                type: 'textarea',
                                placeholder: '?????????',
                                spanForm: 21,
                                formItemLayout: {
                                    labelCol: {
                                        xs: { span: 4 },
                                        sm: { span: 4 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 20 },
                                        sm: { span: 20 }
                                    }
                                }
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: "??????",
                                field: "fileList",
                                type: "files",
                                initialValue: [],
                                fetchConfig: {
                                    apiName: "upload"
                                },
                                spanForm: 21,
                                formItemLayout: {
                                    labelCol: {
                                        xs: { span: 4 },
                                        sm: { span: 4 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 20 },
                                        sm: { span: 20 }
                                    }
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