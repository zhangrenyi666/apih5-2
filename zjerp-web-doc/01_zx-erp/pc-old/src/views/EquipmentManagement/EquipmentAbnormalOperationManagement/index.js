import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import s from "./style.less";
import { message as Msg, Tabs, Modal, Spin } from "antd";
import moment from "moment";
const { TabPane } = Tabs;
const confirm = Modal.confirm;
const config1 = {
    antd: {
        rowKey: function (row) {
            return row.id
        },
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
            xs: { span: 10 },
            sm: { span: 10 }
        },
        wrapperCol: {
            xs: { span: 14 },
            sm: { span: 14 }
        }
    }
};
const config2 = {
    antd: {
        rowKey: function (row) {
            return row.id
        },
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
            xs: { span: 8 },
            sm: { span: 8 }
        },
        wrapperCol: {
            xs: { span: 16 },
            sm: { span: 16 }
        }
    }
};
const config3 = {
    antd: {
        rowKey: function (row) {
            return row.id
        },
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
            xs: { span: 8 },
            sm: { span: 8 }
        },
        wrapperCol: {
            xs: { span: 16 },
            sm: { span: 16 }
        }
    }
};
const config4 = {
    antd: {
        rowKey: function (row) {
            return row.id
        },
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
            xs: { span: 8 },
            sm: { span: 8 }
        },
        wrapperCol: {
            xs: { span: 16 },
            sm: { span: 16 }
        }
    }
};
const config5 = {
    antd: {
        rowKey: function (row) {
            return row.id
        },
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
            xs: { span: 10 },
            sm: { span: 10 }
        },
        wrapperCol: {
            xs: { span: 14 },
            sm: { span: 14 }
        }
    }
};
class index extends Component {
    constructor(props) {
        super(props);
        this.state = {
            activeKey: '1',
            loading: false,
            rowData: null,
            visible: false,
            visibles: false,
            visiblet: false
        }
    }
    tabsCallback = (activeKey) => {
        this.setState({
            activeKey
        })
    }
    render() {
        let { myPublic: { domain, appInfo: { ureport } } } = this.props;
        const { departmentId, departmentName, companyId, companyName } = this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany;
        const { ext1 } = this.props.loginAndLogoutInfo.loginInfo.userInfo;
        const { activeKey, loading, rowData, visible, visibles, visiblet } = this.state;
        return (
            <div className={s.root}>
                <Tabs activeKey={activeKey} onChange={this.tabsCallback}>
                    <TabPane tab="???????????????" key="1">
                        <QnnTable
                            {...this.props}
                            fetch={this.props.myFetch}
                            upload={this.props.myUpload}
                            headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                            wrappedComponentRef={(me) => {
                                this.table1 = me;
                            }}
                            {...config1}
                            fetchConfig={{
                                apiName: 'getZxEqMoveUseOrgList',
                                otherParams: {
                                    outOrgID: departmentId
                                }
                            }}
                            actionBtns={[
                                {
                                    name: 'add',//??????add del
                                    icon: 'plus',//icon
                                    type: 'primary',//??????  ?????? primary  [primary dashed danger]
                                    label: '??????',
                                    formBtns: [
                                        {
                                            name: 'cancel', //??????????????????
                                            type: 'dashed',//??????  ?????? primary
                                            label: '??????',
                                        },
                                        {
                                            name: 'submit',//??????add del
                                            type: 'primary',//??????  ?????? primary
                                            label: '??????',//???????????????????????????????????? 
                                            fetchConfig: {//ajax??????
                                                apiName: 'addZxEqMoveUseOrg',
                                            }
                                        }
                                    ]
                                },
                                {
                                    name: 'edit',//??????add del
                                    icon: 'edit',//icon
                                    type: 'primary',//??????  ?????? primary  [primary dashed danger]
                                    label: '??????',
                                    disabled: (obj) => {
                                        let data = obj.btnCallbackFn.getSelectedRows();
                                        if (data.length === 1 && data[0].approveStatus === '0' && departmentId === data[0].comID) {
                                            return false;
                                        } else {
                                            return true;
                                        }
                                    },
                                    formBtns: [
                                        {
                                            name: 'cancel', //??????????????????
                                            type: 'dashed',//??????  ?????? primary
                                            label: '??????',
                                        },
                                        {
                                            name: 'submit',//??????add del
                                            type: 'primary',//??????  ?????? primary
                                            label: '??????',//???????????????????????????????????? 
                                            fetchConfig: {//ajax??????
                                                apiName: 'updateZxEqMoveUseOrg',
                                            },
                                            onClick: (obj) => {
                                                obj.btnCallbackFn.clearSelectedRows();
                                            }
                                        }
                                    ]
                                },
                                {
                                    name: 'CallOutPartyConfirm',//??????add del
                                    type: 'primary',//??????  ?????? primary  [primary dashed danger]
                                    label: '???????????????',
                                    disabled: (obj) => {
                                        let data = obj.btnCallbackFn.getSelectedRows();
                                        if (data.length === 1 && data[0].approveStatus === '0' && data[0].outOrgName === departmentName) {
                                            return false;
                                        } else {
                                            return true;
                                        }
                                    },
                                    onClick: (obj) => {
                                        confirm({
                                            content: '????????????????',
                                            onOk: () => {
                                                this.props.myFetch('outConfirmZxEqMoveUseOrg', ...obj.selectedRows).then(
                                                    ({ success, message }) => {
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
                                    }
                                },
                                {
                                    name: 'edit',//??????add del
                                    type: 'primary',//??????  ?????? primary  [primary dashed danger]
                                    label: '???????????????',
                                    disabled: (obj) => {
                                        let data = obj.btnCallbackFn.getSelectedRows();
                                        if (data.length === 1 && data[0].approveStatus === '1' && data[0].acceptOrgName === departmentName) {
                                            return false;
                                        } else {
                                            return true;
                                        }
                                    },
                                    formBtns: [
                                        {
                                            name: 'cancel', //??????????????????
                                            type: 'dashed',//??????  ?????? primary
                                            label: '??????',
                                        },
                                        {
                                            name: 'submit',//??????add del
                                            type: 'primary',//??????  ?????? primary
                                            label: '??????',//???????????????????????????????????? 
                                            fetchConfig: {//ajax??????
                                                apiName: 'inConfirmZxEqMoveUseOrg',
                                            },
                                            onClick: (obj) => {
                                                obj.btnCallbackFn.clearSelectedRows();
                                            }
                                        }
                                    ]
                                },
                                // {
                                //     name: 'preview',
                                //     type: 'primary',//??????  ?????? primary  [primary dashed danger]
                                //     label: '??????',
                                //     disabled: (obj) => {
                                //         let data = obj.btnCallbackFn.getSelectedRows();
                                //         if (data.length === 1) {
                                //             return false;
                                //         } else {
                                //             return true;
                                //         }
                                //     },
                                //     onClick: (obj) => {
                                //         if (obj.selectedRows[0]?.id === this.rowData?.id ) {
                                //             this.setState({ visibles: true });
                                //         } else {
                                //             this.setState({
                                //                 rowData: obj.selectedRows[0]
                                //             }, () => {
                                //                 this.setState({ visibles: true, loading: true });
                                //             })
                                //         }
                                //     }
                                // },
                                {
                                    name: 'del',//??????add del
                                    icon: 'delete',//icon
                                    type: 'danger',//??????  ?????? primary  [primary dashed danger]
                                    label: '??????',
                                    disabled: (obj) => {
                                        let data = obj.btnCallbackFn.getSelectedRows();
                                        if (data.filter(item => item.approveStatus !== '0' || departmentId !== item.comID).length) {
                                            return true;
                                        } else {
                                            return false;
                                        }
                                    },
                                    fetchConfig: {//ajax??????
                                        apiName: 'batchDeleteUpdateZxEqMoveUseOrg',
                                    },
                                }
                            ]}
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
                                        field: 'acceptOrgName',
                                        type: 'string',
                                        hide: true,
                                    }
                                },
                                {
                                    isInTable: false,
                                    form: {
                                        field: 'outComID',
                                        type: 'string',
                                        hide: true,
                                    }
                                },
                                {
                                    isInTable: false,
                                    form: {
                                        field: 'acceptComID',
                                        type: 'string',
                                        hide: true,
                                    }
                                },
                                {
                                    isInTable: false,
                                    form: {
                                        field: 'comID',
                                        type: 'string',
                                        initialValue: companyId,
                                        hide: true,
                                    }
                                },
                                {
                                    table: {
                                        title: '????????????',
                                        dataIndex: 'comName',
                                        key: 'comName',
                                        width: 150,
                                        fixed: 'left',
                                        onClick: 'detail'
                                    },
                                    form: {
                                        type: 'string',
                                        addDisabled: true,
                                        editDisabled: true,
                                        initialValue: companyName,
                                        placeholder: '?????????',
                                        spanForm: 8
                                    }
                                },
                                {
                                    table: {
                                        title: '?????????',
                                        dataIndex: 'transferNo',
                                        key: 'transferNo',
                                        width: 100
                                    },
                                    form: {
                                        type: 'string',
                                        required: true,
                                        placeholder: '?????????',
                                        condition: [
                                            {
                                                regex: {
                                                    approveStatus: '1'
                                                },
                                                action: 'disabled'
                                            }
                                        ],
                                        spanForm: 8
                                    }
                                },
                                {
                                    table: {
                                        title: '????????????',
                                        dataIndex: 'equipName',
                                        key: 'equipName',
                                        width: 100
                                    },
                                    isInForm: false
                                },
                                {
                                    table: {
                                        title: '????????????',
                                        dataIndex: 'movedate',
                                        key: 'movedate',
                                        format: 'YYYY-MM-DD',
                                        width: 100
                                    },
                                    form: {
                                        type: 'date',
                                        required: true,
                                        placeholder: '?????????',
                                        condition: [
                                            {
                                                regex: {
                                                    approveStatus: '1'
                                                },
                                                action: 'disabled'
                                            }
                                        ],
                                        spanForm: 8
                                    }
                                },
                                {
                                    table: {
                                        title: '?????????',
                                        dataIndex: 'outOrgName',
                                        key: 'outOrgName',
                                        width: 100
                                    },
                                    form: {
                                        type: 'select',
                                        field: 'outOrgID',
                                        optionConfig: {
                                            label: 'departmentName',
                                            value: 'departmentId',
                                            linkageFields: {
                                                outOrgName: 'departmentName',
                                                outComName: 'companyName',
                                                outComID: 'companyId'
                                            }
                                        },
                                        fetchConfig: {
                                            apiName: 'getSysCompanyProject',
                                            otherParams: {
                                                departmentId: departmentId
                                            }
                                        },
                                        editDisabled: true,
                                        onChange: (val, obj) => {
                                            obj.qnnformData.qnnFormProps.tableFns.qnnForm.clearValues(['itemList']);
                                            obj.qnnformData.qnnFormProps.tableFns.qnnForm.qnnSetState({
                                                editRowId: null
                                            })
                                            this.tableOne.refresh();
                                        },
                                        allowClear: false,
                                        showSearch: true,
                                        required: true,
                                        placeholder: '?????????',
                                        condition: [
                                            {
                                                regex: {
                                                    approveStatus: '1'
                                                },
                                                action: 'disabled'
                                            }
                                        ],
                                        editShow: false,
                                        detailShow: false,
                                        spanForm: 8
                                    }
                                },
                                {
                                    isInTable: false,
                                    form: {
                                        label: '?????????',
                                        type: 'string',
                                        field: 'outOrgName',
                                        required: true,
                                        placeholder: '?????????',
                                        condition: [
                                            {
                                                regex: {
                                                    approveStatus: '1'
                                                },
                                                action: 'disabled'
                                            }
                                        ],
                                        editDisabled: true,
                                        addShow: false,
                                        spanForm: 8
                                    }
                                },
                                {
                                    table: {
                                        title: '?????????????????????',
                                        dataIndex: 'outComName',
                                        key: 'outComName',
                                        width: 150
                                    },
                                    form: {
                                        type: 'string',
                                        addDisabled: true,
                                        editDisabled: true,
                                        placeholder: '?????????',
                                        condition: [
                                            {
                                                regex: {
                                                    approveStatus: '1'
                                                },
                                                action: 'disabled'
                                            }
                                        ],
                                        spanForm: 8
                                    }
                                },
                                {
                                    table: {
                                        title: '?????????',
                                        dataIndex: 'acceptOrgName',
                                        key: 'acceptOrgName',
                                        width: 100
                                    },
                                    form: {
                                        type: 'select',
                                        field: 'acceptOrgID',
                                        optionConfig: {
                                            label: 'departmentName',
                                            value: 'departmentId',
                                            linkageFields: {
                                                acceptOrgName: 'departmentName',
                                                acceptComName: 'companyName',
                                                acceptComID: 'companyId'
                                            }
                                        },
                                        fetchConfig: {
                                            apiName: 'getSysCompanyProject',
                                            otherParams: {
                                                departmentId: companyId
                                            }
                                        },
                                        allowClear: false,
                                        showSearch: true,
                                        required: true,
                                        placeholder: '?????????',
                                        condition: [
                                            {
                                                regex: {
                                                    approveStatus: '1'
                                                },
                                                action: 'disabled'
                                            }
                                        ],
                                        spanForm: 8
                                    }
                                },
                                {
                                    table: {
                                        title: '?????????????????????',
                                        dataIndex: 'acceptComName',
                                        key: 'acceptComName',
                                        width: 150,
                                    },
                                    form: {
                                        type: 'string',
                                        addDisabled: true,
                                        editDisabled: true,
                                        placeholder: '?????????',
                                        spanForm: 8
                                    }
                                },
                                {
                                    isInTable: false,
                                    form: {
                                        type: 'qnnTable',
                                        field: 'itemList',
                                        incToForm: true,
                                        condition: [
                                            {
                                                regex: {
                                                    approveStatus: '1'
                                                },
                                                action: 'disabled'
                                            }
                                        ],
                                        qnnTableConfig: {
                                            actionBtnsPosition: "top",
                                            antd: {
                                                rowKey: 'id',
                                                size: 'small'
                                            },
                                            paginationConfig: {
                                                position: 'bottom'
                                            },
                                            wrappedComponentRef: (me) => {
                                                this.tableOne = me;
                                            },
                                            tableTdEdit: true,
                                            desc: '?????????????????????????????????',
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
                                                        label: 'equipNo',
                                                        field: 'equipNo',
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
                                                        title: "<div>??????????????????<span style='color: #ff4d4f'>*</span></div>",
                                                        dataIndex: 'equipNo',
                                                        key: 'equipNo',
                                                        width: 200,
                                                        fixed: 'left',
                                                        tdEdit: true
                                                    },
                                                    form: {
                                                        field:'equipID',
                                                        type: 'select',
                                                        optionConfig: {
                                                            label: 'equipNo',
                                                            value: 'id',
                                                        },
                                                        fetchConfig: () => {
                                                            return {
                                                                apiName: "getZxEqEquipListForMoveUseOrg",
                                                                otherParams: {
                                                                    useOrgID: this.table1.qnnForm.form.getFieldValue('outOrgID'),
                                                                    statusFlagForMoveUseOrg:'888'
                                                                }
                                                            };

                                                        },
                                                        onChange: (val, obj, btnCallbackFn) => {
                                                            btnCallbackFn.getEditedRowData(false).then((editRowData) => {
                                                                editRowData.equipNo = obj.itemData.equipNo;
                                                                editRowData.financeNo = obj.itemData.financeNo;
                                                                editRowData.equipName = obj.itemData.equipName;
                                                                editRowData.spec = obj.itemData.spec;
                                                                editRowData.model = obj.itemData.model;
                                                                editRowData.orginalValue = obj.itemData.orginalValue;
                                                                editRowData.leftValue = obj.itemData.leftValue;
                                                                editRowData.factory = obj.itemData.factory;
                                                                editRowData.outFactoryDate = obj.itemData.outFactoryDate;
                                                                btnCallbackFn.setEditedRowData({ ...editRowData });
                                                            });
                                                        },
                                                        allowClear: false,
                                                        showSearch: true,
                                                        required: true,
                                                        placeholder: '?????????',
                                                    },
                                                },
                                                {
                                                    table: {
                                                        title: '????????????',
                                                        dataIndex: 'financeNo',
                                                        key: 'financeNo',
                                                        width: 150,
                                                        tdEdit: true
                                                    },
                                                    form: {
                                                        type: 'string',
                                                        disabled: true,
                                                        placeholder: '?????????',
                                                    },
                                                },
                                                {
                                                    table: {
                                                        title: '????????????',
                                                        dataIndex: 'equipName',
                                                        key: 'equipName',
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
                                                        dataIndex: 'startPlace',
                                                        key: 'startPlace',
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
                                                        title: '??????',
                                                        dataIndex: 'spec',
                                                        key: 'spec',
                                                        width: 150,
                                                        tdEdit: true
                                                    },
                                                    form: {
                                                        type: 'string',
                                                        disabled: true,
                                                        placeholder: '?????????',
                                                    },
                                                },
                                                {
                                                    table: {
                                                        title: '??????',
                                                        dataIndex: 'model',
                                                        key: 'model',
                                                        width: 150,
                                                        tdEdit: true
                                                    },
                                                    form: {
                                                        type: 'string',
                                                        disabled: true,
                                                        placeholder: '?????????',
                                                    },
                                                },
                                                {
                                                    table: {
                                                        title: '??????',
                                                        dataIndex: 'orginalValue',
                                                        key: 'orginalValue',
                                                        width: 150,
                                                        tdEdit: true
                                                    },
                                                    form: {
                                                        type: 'number',
                                                        min: 0,
                                                        placeholder: '?????????',
                                                    },
                                                },
                                                {
                                                    table: {
                                                        title: '??????',
                                                        dataIndex: 'leftValue',
                                                        key: 'leftValue',
                                                        width: 150,
                                                        tdEdit: true
                                                    },
                                                    form: {
                                                        type: 'number',
                                                        min: 0,
                                                        placeholder: '?????????'
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
                                                        disabled: true,
                                                        placeholder: '?????????',
                                                    },
                                                },
                                                {
                                                    table: {
                                                        title: '????????????',
                                                        dataIndex: 'outFactoryDate',
                                                        key: 'outFactoryDate',
                                                        width: 150,
                                                        format: 'YYYY-MM-DD',
                                                        tdEdit: true
                                                    },
                                                    form: {
                                                        type: 'date',
                                                        disabled: true,
                                                        placeholder: '?????????',
                                                    },
                                                },
                                                {
                                                    table: {
                                                        title: '????????????',
                                                        dataIndex: 'startDate',
                                                        key: 'startDate',
                                                        width: 150,
                                                        format: 'YYYY-MM-DD',
                                                        tdEdit: true
                                                    },
                                                    form: {
                                                        type: 'date',
                                                        placeholder: '?????????',
                                                    },
                                                },
                                            ],
                                            actionBtns: [
                                                {
                                                    name: "addRow",
                                                    icon: "plus",
                                                    type: "primary",
                                                    label: "?????????",
                                                    disabled: () => {
                                                        if (this.table1.qnnForm.form.getFieldsValue().outOrgID) {
                                                            return false;
                                                        } else {
                                                            return true;
                                                        }
                                                    }
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
                                        label: '????????????',
                                        field: 'reason',
                                        type: 'string',
                                        placeholder: '?????????',
                                        condition: [
                                            {
                                                regex: {
                                                    approveStatus: '1'
                                                },
                                                action: 'disabled'
                                            }
                                        ],
                                        spanForm: 19,
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
                                        label: '????????????',
                                        field: 'careinfo',
                                        type: 'string',
                                        placeholder: '?????????',
                                        condition: [
                                            {
                                                regex: {
                                                    approveStatus: '1'
                                                },
                                                action: 'disabled'
                                            }
                                        ],
                                        spanForm: 19,
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
                                        label: '??????????????????',
                                        field: 'outTransactor',
                                        type: 'string',
                                        placeholder: '?????????',
                                        condition: [
                                            {
                                                regex: {
                                                    approveStatus: '1'
                                                },
                                                action: 'disabled'
                                            }
                                        ],
                                        spanForm: 8
                                    }
                                },
                                {
                                    isInTable: false,
                                    form: {
                                        label: '????????????',
                                        field: 'adminLeader',
                                        type: 'string',
                                        placeholder: '?????????',
                                        condition: [
                                            {
                                                regex: {
                                                    approveStatus: '1'
                                                },
                                                action: 'disabled'
                                            }
                                        ],
                                        spanForm: 8
                                    }
                                },
                                {
                                    isInTable: false,
                                    form: {
                                        label: '????????????',
                                        field: 'equipAdmin',
                                        type: 'string',
                                        placeholder: '?????????',
                                        condition: [
                                            {
                                                regex: {
                                                    approveStatus: '1'
                                                },
                                                action: 'disabled'
                                            }
                                        ],
                                        spanForm: 8
                                    }
                                },
                                {
                                    isInTable: false,
                                    form: {
                                        label: '?????????',
                                        field: 'finance',
                                        type: 'string',
                                        placeholder: '?????????',
                                        condition: [
                                            {
                                                regex: {
                                                    approveStatus: '1'
                                                },
                                                action: 'disabled'
                                            }
                                        ],
                                        spanForm: 8
                                    }
                                },
                                {
                                    isInTable: false,
                                    form: {
                                        label: '?????????',
                                        field: 'lister',
                                        type: 'string',
                                        placeholder: '?????????',
                                        condition: [
                                            {
                                                regex: {
                                                    approveStatus: '1'
                                                },
                                                action: 'disabled'
                                            }
                                        ],
                                        spanForm: 8
                                    }
                                },
                                {
                                    isInTable: false,
                                    form: {
                                        label: '??????????????????',
                                        field: 'acceptTransactor',
                                        type: 'string',
                                        placeholder: '?????????',
                                        required: true,
                                        hide: true,
                                        condition: [
                                            {
                                                regex: {
                                                    approveStatus: '1'
                                                },
                                                action: 'show'
                                            },
                                            {
                                                regex: {
                                                    approveStatus: '2'
                                                },
                                                action: 'show'
                                            }
                                        ],
                                        spanForm: 8
                                    }
                                },
                                {
                                    isInTable: false,
                                    form: {
                                        label: '????????????',
                                        field: 'locality',
                                        type: 'select',
                                        optionConfig: {
                                            label: 'itemName', //?????? label
                                            value: 'itemId'
                                        },
                                        fetchConfig: {
                                            apiName: 'getBaseCodeSelect',
                                            otherParams: {
                                                itemId: 'xingzhengquhuadaima'
                                            }
                                        },
                                        placeholder: '?????????',
                                        required: true,
                                        hide: true,
                                        condition: [
                                            {
                                                regex: {
                                                    approveStatus: '1'
                                                },
                                                action: 'show'
                                            },
                                            {
                                                regex: {
                                                    approveStatus: '2'
                                                },
                                                action: 'show'
                                            }
                                        ],
                                        spanForm: 8
                                    }
                                },
                                {
                                    isInTable: false,
                                    form: {
                                        label: '???????????????????????????',
                                        field: 'inEquipManageDpt',
                                        type: 'string',
                                        placeholder: '?????????',
                                        required: true,
                                        hide: true,
                                        condition: [
                                            {
                                                regex: {
                                                    approveStatus: '1'
                                                },
                                                action: 'show'
                                            },
                                            {
                                                regex: {
                                                    approveStatus: '2'
                                                },
                                                action: 'show'
                                            }
                                        ],
                                        spanForm: 8
                                    }
                                },
                                {
                                    isInTable: false,
                                    form: {
                                        label: '?????????????????????',
                                        field: 'acceptFinance',
                                        type: 'string',
                                        placeholder: '?????????',
                                        required: true,
                                        hide: true,
                                        condition: [
                                            {
                                                regex: {
                                                    approveStatus: '1'
                                                },
                                                action: 'show'
                                            },
                                            {
                                                regex: {
                                                    approveStatus: '2'
                                                },
                                                action: 'show'
                                            }
                                        ],
                                        spanForm: 8
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
                                        spanForm: 20,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 3 },
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
                                        title: '????????????',
                                        dataIndex: 'approveStatus',
                                        key: 'approveStatus',
                                        render: (data) => {
                                            if (data === '0') {
                                                return '??????????????????';
                                            } else if (data === '1') {
                                                return '??????????????????';
                                            } else if (data === '2') {
                                                return '????????????';
                                            }
                                        }
                                    },
                                    form: {
                                        type: 'string',
                                        hide: true
                                    }
                                }
                            ]}
                        />
                    </TabPane>
                    <TabPane tab="????????????" key="2">
                        <QnnTable
                            {...this.props}
                            fetch={this.props.myFetch}
                            upload={this.props.myUpload}
                            headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                            wrappedComponentRef={(me) => {
                                this.table2 = me;
                            }}
                            {...config2}
                            fetchConfig={{
                                apiName: 'getZxEqEquipDepreciationList',
                                otherParams: {
                                    orgID: departmentId
                                }
                            }}
                            actionBtns={[
                                {
                                    name: 'add',//??????add del
                                    icon: 'plus',//icon
                                    type: 'primary',//??????  ?????? primary  [primary dashed danger]
                                    label: '??????',
                                    formBtns: [
                                        {
                                            name: 'cancel', //??????????????????
                                            type: 'dashed',//??????  ?????? primary
                                            label: '??????',
                                        },
                                        {
                                            name: 'submit',//??????add del
                                            type: 'primary',//??????  ?????? primary
                                            label: '??????',//???????????????????????????????????? 
                                            fetchConfig: {//ajax??????
                                                apiName: 'addZxEqEquipDepreciation',
                                            }
                                        }
                                    ]
                                },
                                {
                                    name: 'edit',//??????add del
                                    icon: 'edit',//icon
                                    type: 'primary',//??????  ?????? primary  [primary dashed danger]
                                    label: '??????',
                                    disabled: (obj) => {
                                        let data = obj.btnCallbackFn.getSelectedRows();
                                        if (data.length === 1 && data[0].auditStatus === '?????????') {
                                            return false;
                                        } else {
                                            return true;
                                        }
                                    },
                                    onClick:(obj) => {
                                        let itemList = obj.selectedRows[0].itemList.map((item) => {
                                            if(item.leftMonth === 1){
                                                item.id = null;
                                            }
                                            return item;
                                        });
                                        obj.btnCallbackFn.form.setFieldsValue({
                                            itemList:itemList
                                        })
                                    },
                                    formBtns: [
                                        {
                                            name: 'cancel', //??????????????????
                                            type: 'dashed',//??????  ?????? primary
                                            label: '??????',
                                        },
                                        {
                                            name: 'submit',//??????add del
                                            type: 'primary',//??????  ?????? primary
                                            label: '??????',//???????????????????????????????????? 
                                            fetchConfig: {//ajax??????
                                                apiName: 'updateZxEqEquipDepreciation',
                                            },
                                            onClick: (obj) => {
                                                obj.btnCallbackFn.clearSelectedRows();
                                            }
                                        }
                                    ]
                                },
                                {
                                    name: 'audit',//??????add del
                                    type: 'primary',//??????  ?????? primary  [primary dashed danger]
                                    label: '??????',
                                    disabled: (obj) => {
                                        let data = obj.btnCallbackFn.getSelectedRows();
                                        if (data.length === 1 && data[0].auditStatus === '?????????') {
                                            return false;
                                        } else {
                                            return true;
                                        }
                                    },
                                    onClick: (obj) => {
                                        confirm({
                                            content: '????????????????????????????',
                                            onOk: () => {
                                                this.props.myFetch('auditZxEqEquipDepreciation', ...obj.selectedRows).then(
                                                    ({ success, message }) => {
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
                                    }
                                },
                                {
                                    name: 'del',//??????add del
                                    icon: 'delete',//icon
                                    type: 'danger',//??????  ?????? primary  [primary dashed danger]
                                    label: '??????',
                                    disabled: (obj) => {
                                        let data = obj.btnCallbackFn.getSelectedRows();
                                        if (data.filter(item => item.auditStatus === '?????????').length) {
                                            return true;
                                        } else {
                                            return false;
                                        }
                                    },
                                    fetchConfig: {//ajax??????
                                        apiName: 'batchDeleteUpdateZxEqEquipDepreciation',
                                    },
                                }
                            ]}
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
                                    table: {
                                        title: '????????????',
                                        dataIndex: 'orgName',
                                        key: 'orgName',
                                        onClick: 'detail',
                                        filter: true,
                                        fieldConfig: {
                                            field: 'orgIDSearch',
                                            type: "select",
                                            optionConfig: {
                                                label: 'companyName',
                                                value: 'companyId'
                                            },
                                            fetchConfig: {
                                                apiName: 'getSysCompanyBySelect',
                                                otherParams: {
                                                    departmentId: departmentId
                                                }
                                            },
                                        }
                                    },
                                    form: {
                                        field: 'orgID',
                                        type: 'select',
                                        optionConfig: {
                                            label: 'companyName',
                                            value: 'companyId',
                                            linkageFields: {
                                                orgName: 'companyName',
                                                comID: 'companyId',
                                                comName: 'companyName'
                                            }
                                        },
                                        fetchConfig: {
                                            apiName: 'getSysCompanyBySelect',
                                            otherParams: {
                                                departmentId: departmentId
                                            }
                                        },
                                        onChange: (val, obj) => {
                                            if (this.table2?.qnnForm?.form?.setFieldsValue) {
                                                if (val) {
                                                    this.props.myFetch('getZxEqEquipListForDepreciationRemove', { useOrgID: val }).then(
                                                        ({ data, success, message }) => {
                                                            if (success) {
                                                                let datas = data.map((item) => {
                                                                    item.equipID = item.id;
                                                                    if(item.leftMonth === 1){
                                                                        item.id = null;
                                                                    }
                                                                    if (((item.orginalValue ? item.orginalValue : 0) - (item.financeOrginalValue ? item.financeOrginalValue : 0) - (item.depreamout ? item.depreamout : 0)) < 0) {
                                                                        item.leftValue = 0;
                                                                    } else {
                                                                        item.leftValue = (item.orginalValue ? item.orginalValue : 0) - (item.financeOrginalValue ? item.financeOrginalValue : 0) - (item.depreamout ? item.depreamout : 0);
                                                                    }
                                                                    if (!item.financeOrginalValue) {
                                                                        item.financeOrginalValue = 0;
                                                                    }
                                                                    return item;
                                                                })
                                                                this.table2.qnnForm.form.setFieldsValue({
                                                                    itemList: datas
                                                                })
                                                            } else {
                                                                Msg.error(message)
                                                            }
                                                        }
                                                    );
                                                } else {
                                                    this.table2.qnnForm.form.setFieldsValue({
                                                        itemList: []
                                                    })
                                                }
                                            }
                                        },
                                        allowClear: false,
                                        showSearch: true,
                                        editDisabled: true,
                                        required: true,
                                        placeholder: '?????????',
                                        spanForm: 8
                                    }
                                },
                                {
                                    table: {
                                        title: '????????????',
                                        dataIndex: 'depreDate',
                                        key: 'depreDate',
                                        format: 'YYYY-MM-DD',
                                        // onClick: 'detail',
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
                                        title: '????????????',
                                        dataIndex: 'depreperiodDate',
                                        key: 'depreperiodDate',
                                        format: 'YYYY-MM',
                                        filter: true
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
                                        title: '??????',
                                        dataIndex: 'orginalValue',
                                        key: 'orginalValue',
                                        render: (data, rowData) => {
                                            if (rowData.orginalValue || rowData.orginalValue === 0) {
                                                return rowData.orginalValue.toFixed(2)
                                            }
                                        }
                                    },
                                    form: {
                                        type: 'number',
                                        addShow: false,
                                        editShow: false,
                                        editDisabled: true,
                                        min: 0,
                                        precision: 2,
                                        placeholder: '?????????',
                                        spanForm: 8
                                    }
                                },
                                {
                                    table: {
                                        title: '????????????',
                                        dataIndex: 'depreamout',
                                        key: 'depreamout',
                                        render: (data, rowData) => {
                                            if (rowData.depreamout || rowData.depreamout === 0) {
                                                return rowData.depreamout.toFixed(2)
                                            }
                                        }
                                    },
                                    form: {
                                        type: 'number',
                                        addShow: false,
                                        editShow: false,
                                        editDisabled: true,
                                        min: 0,
                                        precision: 2,
                                        placeholder: '?????????',
                                        spanForm: 8
                                    }
                                },
                                {
                                    table: {
                                        title: '????????????',
                                        dataIndex: 'leftValue',
                                        key: 'leftValue',
                                        render: (data, rowData) => {
                                            if (rowData.leftValue || rowData.leftValue === 0) {
                                                return rowData.leftValue.toFixed(2)
                                            }
                                        }
                                    },
                                    form: {
                                        type: 'number',
                                        addShow: false,
                                        editShow: false,
                                        editDisabled: true,
                                        min: 0,
                                        precision: 2,
                                        placeholder: '?????????',
                                        spanForm: 8
                                    }
                                },
                                {
                                    table: {
                                        title: '????????????',
                                        dataIndex: 'auditStatus',
                                        key: 'auditStatus'
                                    },
                                    form: {
                                        type: 'string',
                                        placeholder: '?????????',
                                        hide: (obj) => {
                                            if (obj.clickCb.rowInfo.name === 'add') {
                                                return true;
                                            }
                                            return false;
                                        },
                                        allowClear: false,
                                        showSearch: true,
                                        addDisabled: true,
                                        editDisabled: true,
                                        spanForm: 8
                                    }
                                },
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
                                            isShowRowSelect: false,
                                            wrappedComponentRef: (me) => {
                                                this.tableOne = me;
                                            },
                                            tableTdEdit: true,
                                            rowDisabledCondition: function (rowData) {
                                                return rowData.leftMonth === 1;
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
                                                        label: 'equipName',
                                                        field: 'equipName',
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
                                                        title: '??????????????????',
                                                        dataIndex: 'equipNo',
                                                        key: 'equipNo',
                                                        width: 200,
                                                        fixed: 'left'
                                                    },
                                                    form: {
                                                        type: 'string',
                                                        placeholder: '?????????',
                                                    },
                                                },
                                                {
                                                    table: {
                                                        title: '????????????',
                                                        dataIndex: 'equipName',
                                                        key: 'equipName',
                                                        width: 150,
                                                        // tdEdit: true
                                                    },
                                                    form: {
                                                        field:'equipID',
                                                        type: 'select',
                                                        optionConfig: {
                                                            label: 'equipName',
                                                            value: 'id'
                                                        },
                                                        allowClear: false,
                                                        showSearch: true,
                                                        fetchConfig: {
                                                            apiName: "getZxEqEquipList",
                                                            otherParams: () => {
                                                                return {
                                                                    companyId: this.table2.qnnForm.form.getFieldValue('comID')
                                                                }
                                                            }
                                                        },
                                                        onChange: (val, obj, btnCallbackFn) => {
                                                            btnCallbackFn.getEditedRowData(false).then((editRowData) => {
                                                                editRowData.equipName = obj.itemData.equipName;
                                                                editRowData.equipNo = obj.itemData.equipNo;
                                                                editRowData.useOrgID = obj.itemData.useOrgID;
                                                                editRowData.orginalValue = obj.itemData.orginalValue;
                                                                editRowData.financeOrginalValue = obj.itemData.financeOrginalValue;
                                                                btnCallbackFn.setEditedRowData({ ...editRowData });
                                                            });
                                                        },
                                                        placeholder: '?????????',
                                                    },
                                                },
                                                {
                                                    table: {
                                                        title: '????????????',
                                                        dataIndex: 'useOrgID',
                                                        key: 'useOrgID',
                                                        width: 150,
                                                        // tdEdit: true,
                                                        type: 'select'
                                                    },
                                                    form: {
                                                        type: 'select',
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
                                                        allowClear: false,
                                                        showSearch: true,
                                                        placeholder: '?????????',
                                                    },
                                                },
                                                {
                                                    table: {
                                                        title: '??????',
                                                        dataIndex: 'orginalValue',
                                                        key: 'orginalValue',
                                                        width: 150,
                                                        render: (data, rowData) => {
                                                            if (rowData.orginalValue || rowData.orginalValue === 0) {
                                                                return rowData.orginalValue.toFixed(2)
                                                            }
                                                        }
                                                    },
                                                    form: {
                                                        type: 'number',
                                                        min: 0,
                                                        precision: 2,
                                                        placeholder: '?????????',
                                                    },
                                                },
                                                {
                                                    table: {
                                                        title: '????????????',
                                                        dataIndex: 'financeOrginalValue',
                                                        key: 'financeOrginalValue',
                                                        width: 150,
                                                        render: (data, rowData) => {
                                                            if (rowData.financeOrginalValue || rowData.financeOrginalValue === 0) {
                                                                return rowData.financeOrginalValue.toFixed(2)
                                                            }
                                                        }
                                                    },
                                                    form: {
                                                        type: 'number',
                                                        min: 0,
                                                        precision: 2,
                                                        placeholder: '?????????',
                                                    },
                                                },
                                                {
                                                    table: {
                                                        title: '????????????',
                                                        dataIndex: 'depreamout',
                                                        key: 'depreamout',
                                                        width: 150,
                                                        tdEdit: true,
                                                        render: (data, rowData) => {
                                                            if (rowData.depreamout || rowData.depreamout === 0) {
                                                                return rowData.depreamout.toFixed(2)
                                                            }
                                                        }
                                                    },
                                                    form: {
                                                        type: 'number',
                                                        onBlur: (e, obj, btnCallbackFn) => {
                                                            let val = e.target.value < 0 ? 0 : e.target.value;
                                                            setTimeout(async () => {
                                                                await btnCallbackFn.getEditedRowData(false).then((editRowData) => {
                                                                    editRowData.depreamout = val ? Number(val) : 0;
                                                                    if (((editRowData.orginalValue ? editRowData.orginalValue : 0) - (editRowData.financeOrginalValue ? editRowData.financeOrginalValue : 0) - (val ? Number(val) : 0)) < 0) {
                                                                        editRowData.leftValue = 0;
                                                                        editRowData.depreamout = (editRowData.orginalValue ? editRowData.orginalValue : 0) - (editRowData.financeOrginalValue ? editRowData.financeOrginalValue : 0);
                                                                    } else {
                                                                        editRowData.leftValue = (editRowData.orginalValue ? editRowData.orginalValue : 0) - (editRowData.financeOrginalValue ? editRowData.financeOrginalValue : 0) - (val ? Number(val) : 0);
                                                                    }
                                                                    btnCallbackFn.setEditedRowData({ ...editRowData });
                                                                })
                                                            }, 100)
                                                        },
                                                        min: 0,
                                                        precision: 2,
                                                        placeholder: '?????????',
                                                    },
                                                },
                                                {
                                                    table: {
                                                        title: '????????????',
                                                        dataIndex: 'leftValue',
                                                        key: 'leftValue',
                                                        width: 150,
                                                        render: (data, rowData) => {
                                                            if (rowData.leftValue || rowData.leftValue === 0) {
                                                                return rowData.leftValue.toFixed(2)
                                                            }
                                                        }
                                                    },
                                                    form: {
                                                        type: 'number',
                                                        min: 0,
                                                        precision: 2,
                                                        placeholder: '?????????',
                                                    },
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
                                        spanForm: 20,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 3 },
                                                sm: { span: 3 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 21 },
                                                sm: { span: 21 }
                                            }
                                        }
                                    }
                                }
                            ]}
                        />
                    </TabPane>
                    <TabPane tab="????????????" key="3">
                        <QnnTable
                            {...this.props}
                            fetch={this.props.myFetch}
                            upload={this.props.myUpload}
                            headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                            wrappedComponentRef={(me) => {
                                this.table3 = me;
                            }}
                            {...config3}
                            fetchConfig={{
                                apiName: 'getZxEqMoveSupervisorList',
                                otherParams: {
                                    outOrgID: departmentId
                                }
                            }}
                            drawerShowToggle={(obj) => {
                                let { drawerIsShow } = obj;
                                if (!drawerIsShow) {
                                    obj.btnCallbackFn.clearSelectedRows();
                                }
                            }}
                            actionBtns={[
                                {
                                    name: 'add',//??????add del
                                    icon: 'plus',//icon
                                    type: 'primary',//??????  ?????? primary  [primary dashed danger]
                                    label: '??????',
                                    formBtns: [
                                        {
                                            name: 'cancel', //??????????????????
                                            type: 'dashed',//??????  ?????? primary
                                            label: '??????',
                                        },
                                        {
                                            name: 'submit',//??????add del
                                            type: 'primary',//??????  ?????? primary
                                            label: '??????',//???????????????????????????????????? 
                                            fetchConfig: {//ajax??????
                                                apiName: 'addZxEqMoveSupervisor',
                                            }
                                        }
                                    ]
                                },
                                {
                                    name: 'edit',//??????add del
                                    icon: 'edit',//icon
                                    type: 'primary',//??????  ?????? primary  [primary dashed danger]
                                    label: '??????',
                                    disabled: (obj) => {
                                        let data = obj.btnCallbackFn.getSelectedRows();
                                        if (data.length === 1 && data[0].auditStatus === '0' && data[0].outOrgID === departmentId) {
                                            return false;
                                        } else {
                                            return true;
                                        }
                                    },
                                    formBtns: [
                                        {
                                            name: 'cancel', //??????????????????
                                            type: 'dashed',//??????  ?????? primary
                                            label: '??????',
                                        },
                                        {
                                            name: 'submit',//??????add del
                                            type: 'primary',//??????  ?????? primary
                                            label: '??????',//???????????????????????????????????? 
                                            fetchConfig: {//ajax??????
                                                apiName: 'updateZxEqMoveSupervisor',
                                            },
                                            onClick: (obj) => {
                                                obj.btnCallbackFn.clearSelectedRows();
                                            }
                                        }
                                    ]
                                },
                                {
                                    name: 'CallOutPartyConfirm',//??????add del
                                    type: 'primary',//??????  ?????? primary  [primary dashed danger]
                                    label: '???????????????',
                                    disabled: (obj) => {
                                        let data = obj.btnCallbackFn.getSelectedRows();
                                        if (data.length === 1 && data[0].auditStatus === '4' && data[0].outOrgID === departmentId) {
                                            return false;
                                        } else {
                                            return true;
                                        }
                                    },
                                    onClick: (obj) => {
                                        confirm({
                                            content: '????????????????',
                                            onOk: () => {
                                                this.props.myFetch('outConfirmZxEqMoveSupervisor', ...obj.selectedRows).then(
                                                    ({ success, message }) => {
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
                                    }
                                },
                                {
                                    name: 'edit',//??????add del
                                    type: 'primary',//??????  ?????? primary  [primary dashed danger]
                                    label: '???????????????',
                                    disabled: (obj) => {
                                        let data = obj.btnCallbackFn.getSelectedRows();
                                        if (data.length === 1 && data[0].auditStatus === '1' && data[0].inOrgID === departmentId) {
                                            return false;
                                        } else {
                                            return true;
                                        }
                                    },
                                    formBtns: [
                                        {
                                            name: 'cancel', //??????????????????
                                            type: 'dashed',//??????  ?????? primary
                                            label: '??????',
                                        },
                                        {
                                            name: 'submit',//??????add del
                                            type: 'primary',//??????  ?????? primary
                                            label: '??????',//???????????????????????????????????? 
                                            fetchConfig: {//ajax??????
                                                apiName: 'inConfirmZxEqMoveSupervisor',
                                            },
                                            onClick: (obj) => {
                                                obj.btnCallbackFn.clearSelectedRows();
                                            }
                                        }
                                    ]
                                },
                                {
                                    name: 'RequestNumber',
                                    type: 'primary',//??????  ?????? primary  [primary dashed danger]
                                    label: '????????????',
                                    disabled: (obj) => {
                                        let data = obj.btnCallbackFn.getSelectedRows();
                                        if (data.length && !data.filter(item => item.auditStatus !== '0').length && !data.filter(item => item.outOrgID !== departmentId).length) {
                                            return false;
                                        } else {
                                            return true;
                                        }
                                    },
                                    onClick: (obj) => {
                                        confirm({
                                            content: '??????????????????????????????????',
                                            onOk: () => {
                                                this.props.myFetch('batchRequestZxEqMoveSupervisorNum', obj.selectedRows).then(
                                                    ({ success, message }) => {
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
                                    }
                                },
                                {
                                    name: 'edit',
                                    type: 'primary',//??????  ?????? primary  [primary dashed danger]
                                    label: '????????????',
                                    disabled: (obj) => {
                                        let data = obj.btnCallbackFn.getSelectedRows();
                                        if (data.length === 1 && data[0].auditStatus === '3') {
                                            return false;
                                        } else {
                                            return true;
                                        }
                                    },
                                    hide:() => {
                                        if (ext1 === '' || ext1 === '1') {
                                            return false;
                                        }else{
                                            return true;
                                        }
                                    },
                                    formBtns: [
                                        {
                                            name: 'cancel', //??????????????????
                                            type: 'dashed',//??????  ?????? primary
                                            label: '??????',
                                        },
                                        {
                                            name: 'submit',//??????add del
                                            type: 'primary',//??????  ?????? primary
                                            label: '??????',//???????????????????????????????????? 
                                            fetchConfig: {//ajax??????
                                                apiName: 'writeZxEqMoveSupervisorNum',
                                            },
                                            onClick: (obj) => {
                                                obj.btnCallbackFn.clearSelectedRows();
                                            }
                                        }
                                    ]
                                },
                                {
                                    name: 'del',//??????add del
                                    icon: 'delete',//icon
                                    type: 'danger',//??????  ?????? primary  [primary dashed danger]
                                    label: '??????',
                                    disabled: (obj) => {
                                        let data = obj.btnCallbackFn.getSelectedRows();
                                        if (data.filter(item => item.auditStatus !== '0' || item.outOrgID !== departmentId).length) {
                                            return true;
                                        } else {
                                            return false;
                                        }
                                    },
                                    fetchConfig: {//ajax??????
                                        apiName: 'batchDeleteUpdateZxEqMoveSupervisor',
                                    },
                                }
                            ]}
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
                                        initialValue: companyId,
                                        hide: true,
                                    }
                                },
                                {
                                    isInTable: false,
                                    form: {
                                        field: 'comName',
                                        type: 'string',
                                        initialValue: companyName,
                                        hide: true,
                                    }
                                },
                                {
                                    isInTable: false,
                                    form: {
                                        field: 'outOrgID',
                                        type: 'string',
                                        initialValue: departmentId,
                                        hide: true,
                                    }
                                },
                                {
                                    isInTable: false,
                                    form: {
                                        field: 'inOrgName',
                                        type: 'string',
                                        hide: true,
                                    }
                                },
                                {
                                    table: {
                                        title: '?????????',
                                        dataIndex: 'billNo',
                                        key: 'billNo',
                                        onClick: 'detail'
                                    },
                                    form: {
                                        type: 'string',
                                        required: true,
                                        condition: [
                                            {
                                                regex: {
                                                    auditStatus: ['=', /(1|3)/ig]
                                                },
                                                action: 'disabled'
                                            }
                                        ],
                                        placeholder: '?????????',
                                        spanForm: 8
                                    }
                                },
                                {
                                    table: {
                                        title: '????????????',
                                        dataIndex: 'outOrgName',
                                        key: 'outOrgName'
                                    },
                                    form: {
                                        type: 'string',
                                        initialValue: departmentName,
                                        addDisabled: true,
                                        editDisabled: true,
                                        placeholder: '?????????',
                                        spanForm: 8
                                    }
                                },
                                {
                                    table: {
                                        title: '?????????',
                                        dataIndex: 'inOrgName',
                                        key: 'inOrgName'
                                    },
                                    form: {
                                        field: 'inOrgID',
                                        type: 'select',
                                        optionConfig: {
                                            label: 'companyName',
                                            value: 'companyId',
                                            linkageFields: {
                                                inOrgName: 'companyName',
                                            }
                                        },
                                        fetchConfig: {
                                            apiName: 'getSysCompanyBySelect'
                                        },
                                        allowClear: false,
                                        showSearch: true,
                                        required: true,
                                        placeholder: '?????????',
                                        condition: [
                                            {
                                                regex: {
                                                    auditStatus: ['=', /(1|3)/ig]
                                                },
                                                action: 'disabled'
                                            }
                                        ],
                                        onChange: (val, obj) => {
                                            obj.qnnformData.qnnFormProps.tableFns.qnnForm.clearValues(['itemList']);
                                            obj.qnnformData.qnnFormProps.tableFns.qnnForm.qnnSetState({
                                                editRowId: null
                                            })
                                            this.tableOne.refresh();
                                        },
                                        spanForm: 8
                                    }
                                },
                                {
                                    isInTable: false,
                                    form: {
                                        label: '????????????',
                                        field: 'reason',
                                        type: 'string',
                                        // required: true,
                                        placeholder: '?????????',
                                        condition: [
                                            {
                                                regex: {
                                                    auditStatus: ['=', /(1|3)/ig]
                                                },
                                                action: 'disabled'
                                            }
                                        ],
                                        spanForm: 20,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 3 },
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
                                        title: '????????????',
                                        dataIndex: 'busDate',
                                        key: 'busDate',
                                        format: 'YYYY-MM-DD'
                                    },
                                    form: {
                                        type: 'date',
                                        required: true,
                                        placeholder: '?????????',
                                        condition: [
                                            {
                                                regex: {
                                                    auditStatus: ['=', /(1|3)/ig]
                                                },
                                                action: 'disabled'
                                            }
                                        ],
                                        onChange: (val, obj) => {
                                            obj.qnnformData.qnnFormProps.tableFns.qnnForm.clearValues(['itemList']);
                                            obj.qnnformData.qnnFormProps.tableFns.qnnForm.qnnSetState({
                                                editRowId: null
                                            })
                                            this.tableOne.refresh();
                                        },
                                        spanForm: 8
                                    }
                                },
                                {
                                    table: {
                                        title: '????????????',
                                        dataIndex: 'inDate',
                                        key: 'inDate',
                                        format: 'YYYY-MM-DD'
                                    },
                                    form: {
                                        type: 'date',
                                        addDisabled: true,
                                        editDisabled: true,
                                        placeholder: '?????????',
                                        spanForm: 8
                                    }
                                },
                                {
                                    table: {
                                        title: '??????',
                                        dataIndex: 'auditStatus',
                                        key: 'auditStatus',
                                        type: 'select'
                                    },
                                    form: {
                                        type: 'select',
                                        optionConfig: {
                                            label: 'label',
                                            value: 'value'
                                        },
                                        optionData: [
                                            {
                                                label: '???????????????',
                                                value: '0'
                                            },
                                            {
                                                label: '??????????????????',
                                                value: '1'
                                            },
                                            {
                                                label: '????????????',
                                                value: '2'
                                            },
                                            {
                                                label: '???????????????',
                                                value: '3'
                                            },
                                            {
                                                label: '??????????????????',
                                                value: '4'
                                            }
                                        ],
                                        placeholder: '?????????',
                                        hide: (obj) => {
                                            if (obj.clickCb.rowInfo.name === 'add') {
                                                return true;
                                            }
                                            return false;
                                        },
                                        allowClear: false,
                                        showSearch: true,
                                        addDisabled: true,
                                        editDisabled: true,
                                        spanForm: 8
                                    }
                                },
                                {
                                    isInTable: false,
                                    form: {
                                        type: 'qnnTable',
                                        field: 'itemList',
                                        incToForm: true,
                                        condition: [
                                            {
                                                regex: {
                                                    auditStatus: '1'
                                                },
                                                action: 'disabled'
                                            }
                                        ],
                                        qnnTableConfig: {
                                            actionBtnsPosition: "top",
                                            antd: {
                                                rowKey: 'id',
                                                size: 'small'
                                            },
                                            paginationConfig: {
                                                position: 'bottom'
                                            },
                                            wrappedComponentRef: (me) => {
                                                this.tableOne = me;
                                            },
                                            tableTdEdit: true,
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
                                                        label: 'equipNo',
                                                        field: 'equipNo',
                                                        hide: true
                                                    }
                                                },
                                                {
                                                    isInTable: false,
                                                    form: {
                                                        label: 'useOrgName',
                                                        field: 'useOrgName',
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
                                                        title: '?????????????????????',
                                                        dataIndex: 'equipNo',
                                                        key: 'equipNo',
                                                        width: 200,
                                                        fixed: 'left',
                                                        tdEdit: true
                                                    },
                                                    form: {
                                                        field:'equipID',
                                                        type: 'select',
                                                        optionConfig: {
                                                            label: 'equipNo',
                                                            value: 'id',
                                                        },
                                                        fetchConfig: {
                                                            apiName: "getZxEqEquipListForDepreciation",
                                                            otherParams: () => {
                                                                return {
                                                                    useOrgID: this.table3.qnnForm.form.getFieldValue('outOrgID'),
                                                                    busDate: moment(this.table3.qnnForm.form.getFieldValue('busDate')).valueOf()
                                                                }
                                                            }
                                                        },
                                                        onChange: (val, obj, btnCallbackFn) => {
                                                            btnCallbackFn.getEditedRowData(false).then((editRowData) => {
                                                                editRowData.equipNo = obj.itemData.equipNo;
                                                                editRowData.financeNo = obj.itemData.financeNo;
                                                                editRowData.equipName = obj.itemData.equipName;
                                                                editRowData.spec = obj.itemData.spec;
                                                                editRowData.model = obj.itemData.model;
                                                                editRowData.outFactoryDate = obj.itemData.outFactoryDate;
                                                                editRowData.orginalValue = obj.itemData.orginalValue;
                                                                editRowData.leftvalue = obj.itemData.leftValue;
                                                                editRowData.factory = obj.itemData.factory;
                                                                btnCallbackFn.setEditedRowData({ ...editRowData });
                                                            });
                                                        },
                                                        disabled:(obj) => {
                                                            if(obj.Pprops?.qnnFormProps?.form?.getFieldValue?.('auditStatus') === '3'){
                                                                return true;
                                                            }
                                                        },
                                                        allowClear: false,
                                                        showSearch: true,
                                                        placeholder: '?????????',
                                                    },
                                                },
                                                {
                                                    isInTable:(obj) => {
                                                        console.log(obj);
                                                        console.log(this.table3);
                                                        if(this.table3?.getSelectedRows?.()?.[0]?.auditStatus === '3'){
                                                            return false;
                                                        }else{
                                                            return true;
                                                        }
                                                    },
                                                    table: {
                                                        title: '?????????????????????',
                                                        dataIndex: 'newManageNo',
                                                        key: 'newManageNo',
                                                        width: 150,
                                                        tdEdit: true
                                                    },
                                                    form: {
                                                        type: 'string',
                                                        disabled: true,
                                                        placeholder: '?????????',
                                                    },
                                                },
                                                {
                                                    isInTable:() => {
                                                        if(this.table3?.getSelectedRows?.()?.[0]?.auditStatus === '3'){
                                                            return true;
                                                        }else{
                                                            return false;
                                                        }
                                                    },
                                                    table: {
                                                        title: "<div>?????????????????????<span style='color: #ff4d4f'>*</span></div>",
                                                        dataIndex: 'newManageNo',
                                                        key: 'newManageNo',
                                                        width: 150,
                                                        tdEdit: true
                                                    },
                                                    form: {
                                                        type: 'string',
                                                        required: true,
                                                        placeholder: '?????????',
                                                    },
                                                },
                                                {
                                                    table: {
                                                        title: '????????????',
                                                        dataIndex: 'equipName',
                                                        key: 'equipName',
                                                        width: 150,
                                                        tdEdit: true
                                                    },
                                                    form: {
                                                        type: 'string',
                                                        disabled:(obj) => {
                                                            if(obj.Pprops?.qnnFormProps?.form?.getFieldValue?.('auditStatus') === '3'){
                                                                return true;
                                                            }
                                                        },
                                                        placeholder: '?????????',
                                                    },
                                                },
                                                {
                                                    table: {
                                                        title: '????????????',
                                                        dataIndex: 'spec',
                                                        key: 'spec',
                                                        width: 150,
                                                        tdEdit: true
                                                    },
                                                    form: {
                                                        type: 'string',
                                                        disabled:(obj) => {
                                                            if(obj.Pprops?.qnnFormProps?.form?.getFieldValue?.('auditStatus') === '3'){
                                                                return true;
                                                            }
                                                        },
                                                        placeholder: '?????????',
                                                    },
                                                },
                                                {
                                                    table: {
                                                        title: '??????',
                                                        dataIndex: 'model',
                                                        key: 'model',
                                                        width: 150,
                                                        tdEdit: true
                                                    },
                                                    form: {
                                                        type: 'string',
                                                        disabled:(obj) => {
                                                            if(obj.Pprops?.qnnFormProps?.form?.getFieldValue?.('auditStatus') === '3'){
                                                                return true;
                                                            }
                                                        },
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
                                                        disabled:(obj) => {
                                                            if(obj.Pprops?.qnnFormProps?.form?.getFieldValue?.('auditStatus') === '3'){
                                                                return true;
                                                            }
                                                        },
                                                        placeholder: '?????????',
                                                    },
                                                },
                                                {
                                                    table: {
                                                        title: '????????????',
                                                        dataIndex: 'outFactoryDate',
                                                        key: 'outFactoryDate',
                                                        format: 'YYYY-MM',
                                                        width: 150,
                                                        tdEdit: true
                                                    },
                                                    form: {
                                                        type: 'month',
                                                        disabled:(obj) => {
                                                            if(obj.Pprops?.qnnFormProps?.form?.getFieldValue?.('auditStatus') === '3'){
                                                                return true;
                                                            }
                                                        },
                                                        placeholder: '?????????',
                                                    },
                                                },
                                                {
                                                    table: {
                                                        title: '??????(???)',
                                                        dataIndex: 'orginalValue',
                                                        key: 'orginalValue',
                                                        width: 150,
                                                        tdEdit: true
                                                    },
                                                    form: {
                                                        type: 'number',
                                                        min: 0,
                                                        disabled:(obj) => {
                                                            if(obj.Pprops?.qnnFormProps?.form?.getFieldValue?.('auditStatus') === '3'){
                                                                return true;
                                                            }
                                                        },
                                                        placeholder: '?????????',
                                                    },
                                                },
                                                {
                                                    table: {
                                                        title: '??????(???)',
                                                        dataIndex: 'leftvalue',
                                                        key: 'leftvalue',
                                                        width: 150,
                                                        tdEdit: true
                                                    },
                                                    form: {
                                                        type: 'number',
                                                        min: 0,
                                                        disabled:(obj) => {
                                                            if(obj.Pprops?.qnnFormProps?.form?.getFieldValue?.('auditStatus') === '3'){
                                                                return true;
                                                            }
                                                        },
                                                        placeholder: '?????????',
                                                    },
                                                },
                                                {
                                                    table: {
                                                        title: "<div>????????????<span style='color: #ff4d4f'>*</span></div>",
                                                        dataIndex: 'useOrgId',
                                                        key: 'useOrgId',
                                                        width: 150,
                                                        tdEdit: true,
                                                        type: "select"
                                                    },
                                                    form: {
                                                        type: 'select',
                                                        optionConfig: {
                                                            label: 'departmentName',
                                                            value: 'departmentId'
                                                        },
                                                        fetchConfig: {
                                                            apiName: 'getSysCompanyProject',
                                                            otherParams: () => {
                                                                return {
                                                                    departmentId: this.table3.qnnForm.form.getFieldValue('inOrgID')
                                                                }
                                                            }
                                                        },
                                                        onChange: (val, obj, btnCallbackFn) => {
                                                            btnCallbackFn.getEditedRowData(false).then((editRowData) => {
                                                                editRowData.useOrgName = obj.itemData.departmentName;
                                                                btnCallbackFn.setEditedRowData({ ...editRowData });
                                                            });
                                                        },
                                                        disabled:(obj) => {
                                                            if(obj.Pprops?.qnnFormProps?.form?.getFieldValue?.('auditStatus') === '3'){
                                                                return true;
                                                            }
                                                        },
                                                        allowClear: false,
                                                        showSearch: true,
                                                        required: true,
                                                        placeholder: '?????????',
                                                    },
                                                },
                                            ],
                                            actionBtns: [
                                                {
                                                    name: "addRow",
                                                    icon: "plus",
                                                    type: "primary",
                                                    label: "?????????",
                                                    hide:(obj) => {
                                                        if(obj.props?.qnnFormProps?.form?.getFieldValue?.('auditStatus') === '3'){
                                                            return true;
                                                        }
                                                    },
                                                    disabled: () => {
                                                        if (this.table3.qnnForm.form.getFieldsValue().busDate) {
                                                            return false;
                                                        } else {
                                                            return true;
                                                        }
                                                    }
                                                },
                                                {
                                                    name: 'del',
                                                    icon: 'delete',
                                                    type: 'danger',
                                                    label: '??????',
                                                    hide:(obj) => {
                                                        if(obj.props?.qnnFormProps?.form?.getFieldValue?.('auditStatus') === '3'){
                                                            return true;
                                                        }
                                                    }
                                                }
                                            ]
                                        }
                                    }
                                },
                                {
                                    isInTable: false,
                                    form: {
                                        label: '?????????????????????????????????',
                                        field: 'outman1',
                                        type: 'string',
                                        placeholder: '?????????',
                                        condition: [
                                            {
                                                regex: {
                                                    auditStatus: ['=', /(1|3)/ig]
                                                },
                                                action: 'disabled'
                                            }
                                        ],
                                        required: true,
                                        spanForm: 8,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 14 },
                                                sm: { span: 14 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 10 },
                                                sm: { span: 10 }
                                            }
                                        }
                                    }
                                },
                                {
                                    isInTable: false,
                                    form: {
                                        label: '?????????????????????????????????',
                                        field: 'outman2',
                                        type: 'string',
                                        placeholder: '?????????',
                                        condition: [
                                            {
                                                regex: {
                                                    auditStatus: ['=', /(1|3)/ig]
                                                },
                                                action: 'disabled'
                                            }
                                        ],
                                        required: true,
                                        spanForm: 8,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 14 },
                                                sm: { span: 14 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 10 },
                                                sm: { span: 10 }
                                            }
                                        }
                                    }
                                },
                                {
                                    isInTable: false,
                                    form: {
                                        label: '????????????????????????',
                                        field: 'outman5',
                                        type: 'string',
                                        placeholder: '?????????',
                                        condition: [
                                            {
                                                regex: {
                                                    auditStatus: ['=', /(1|3)/ig]
                                                },
                                                action: 'disabled'
                                            }
                                        ],
                                        required: true,
                                        spanForm: 8,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 14 },
                                                sm: { span: 14 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 10 },
                                                sm: { span: 10 }
                                            }
                                        }
                                    }
                                },
                                {
                                    isInTable: false,
                                    form: {
                                        label: '?????????????????????????????????',
                                        field: 'outman3',
                                        type: 'string',
                                        placeholder: '?????????',
                                        condition: [
                                            {
                                                regex: {
                                                    auditStatus: ['=', /(1|3)/ig]
                                                },
                                                action: 'disabled'
                                            }
                                        ],
                                        required: true,
                                        spanForm: 8,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 14 },
                                                sm: { span: 14 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 10 },
                                                sm: { span: 10 }
                                            }
                                        }
                                    }
                                },
                                {
                                    isInTable: false,
                                    form: {
                                        label: '?????????????????????????????????',
                                        field: 'outman4',
                                        type: 'string',
                                        placeholder: '?????????',
                                        condition: [
                                            {
                                                regex: {
                                                    auditStatus: ['=', /(1|3)/ig]
                                                },
                                                action: 'disabled'
                                            }
                                        ],
                                        required: true,
                                        spanForm: 8,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 14 },
                                                sm: { span: 14 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 10 },
                                                sm: { span: 10 }
                                            }
                                        }
                                    }
                                },
                                {
                                    isInTable: false,
                                    form: {
                                        label: '????????????????????????',
                                        field: 'inman1',
                                        type: 'string',
                                        placeholder: '?????????',
                                        spanForm: 8,
                                        hide: true,
                                        condition: [
                                            {
                                                regex: {
                                                    auditStatus: ['=', /(1|2)/ig]
                                                },
                                                action: 'show'
                                            }
                                        ],
                                        required: true,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 14 },
                                                sm: { span: 14 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 10 },
                                                sm: { span: 10 }
                                            }
                                        }
                                    }
                                },
                                {
                                    isInTable: false,
                                    form: {
                                        label: '????????????????????????',
                                        field: 'inman2',
                                        type: 'string',
                                        hide: true,
                                        condition: [
                                            {
                                                regex: {
                                                    auditStatus: ['=', /(1|2)/ig]
                                                },
                                                action: 'show'
                                            }
                                        ],
                                        required: true,
                                        placeholder: '?????????',
                                        spanForm: 8,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 14 },
                                                sm: { span: 14 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 10 },
                                                sm: { span: 10 }
                                            }
                                        }
                                    }
                                },
                                {
                                    isInTable: false,
                                    form: {
                                        label: '????????????????????????',
                                        field: 'inman3',
                                        type: 'string',
                                        hide: true,
                                        condition: [
                                            {
                                                regex: {
                                                    auditStatus: ['=', /(1|2)/ig]
                                                },
                                                action: 'show'
                                            }
                                        ],
                                        required: true,
                                        placeholder: '?????????',
                                        spanForm: 8,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 14 },
                                                sm: { span: 14 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 10 },
                                                sm: { span: 10 }
                                            }
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
                                        condition: [
                                            {
                                                regex: {
                                                    auditStatus: ['=', /(1|3)/ig]
                                                },
                                                action: 'disabled'
                                            }
                                        ],
                                        spanForm: 20,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 3 },
                                                sm: { span: 3 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 21 },
                                                sm: { span: 21 }
                                            }
                                        }
                                    }
                                }
                            ]}
                        />
                    </TabPane>
                    <TabPane tab="????????????" key="4">
                        <QnnTable
                            {...this.props}
                            fetch={this.props.myFetch}
                            upload={this.props.myUpload}
                            headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                            wrappedComponentRef={(me) => {
                                this.table4 = me;
                            }}
                            {...config4}
                            fetchConfig={{
                                apiName: 'getZxEqAssetSellList',
                                otherParams: {
                                    outOrgID: departmentId
                                }
                            }}
                            actionBtns={[
                                {
                                    name: 'add',//??????add del
                                    icon: 'plus',//icon
                                    type: 'primary',//??????  ?????? primary  [primary dashed danger]
                                    label: '??????',
                                    formBtns: [
                                        {
                                            name: 'cancel', //??????????????????
                                            type: 'dashed',//??????  ?????? primary
                                            label: '??????',
                                        },
                                        {
                                            name: 'submit',//??????add del
                                            type: 'primary',//??????  ?????? primary
                                            label: '??????',//???????????????????????????????????? 
                                            fetchConfig: {//ajax??????
                                                apiName: 'addZxEqAssetSell',
                                            }
                                        }
                                    ]
                                },
                                {
                                    name: 'edit',//??????add del
                                    icon: 'edit',//icon
                                    type: 'primary',//??????  ?????? primary  [primary dashed danger]
                                    label: '??????',
                                    disabled: (obj) => {
                                        let data = obj.btnCallbackFn.getSelectedRows();
                                        if (data.length === 1 && (data[0].auditStatus === '0' || data[0].auditStatus === '5')) {
                                            return false;
                                        } else {
                                            return true;
                                        }
                                    },
                                    formBtns: [
                                        {
                                            name: 'cancel', //??????????????????
                                            type: 'dashed',//??????  ?????? primary
                                            label: '??????',
                                        },
                                        {
                                            name: 'submit',//??????add del
                                            type: 'primary',//??????  ?????? primary
                                            label: '??????',//???????????????????????????????????? 
                                            fetchConfig: {//ajax??????
                                                apiName: 'updateZxEqAssetSell',
                                            },
                                            onClick: (obj) => {
                                                obj.btnCallbackFn.clearSelectedRows();
                                            }
                                        }
                                    ]
                                },
                                {
                                    name: 'Reported',//??????add del
                                    type: 'primary',//??????  ?????? primary  [primary dashed danger]
                                    label: '??????',
                                    disabled: (obj) => {
                                        let data = obj.btnCallbackFn.getSelectedRows();
                                        if (data.length === 1 && (data[0].auditStatus === '0' || data[0].auditStatus === '5')) {
                                            return false;
                                        } else {
                                            return true;
                                        }
                                    },
                                    hide: () => {
                                        if (ext1 === '2') {
                                            return false;
                                        } else {
                                            return true;
                                        }
                                    },
                                    onClick: (obj) => {
                                        confirm({
                                            content: '????????????????????????????',
                                            onOk: () => {
                                                this.props.myFetch('reportZxEqAssetSell', ...obj.selectedRows).then(
                                                    ({ success, message }) => {
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
                                    }
                                },
                                {
                                    name: 'audit',//??????add del
                                    type: 'primary',//??????  ?????? primary  [primary dashed danger]
                                    label: '????????????',
                                    disabled: (obj) => {
                                        let data = obj.btnCallbackFn.getSelectedRows();
                                        if (data.length === 1 && data[0].auditStatus === '3') {
                                            return false;
                                        } else {
                                            return true;
                                        }
                                    },
                                    hide: () => {
                                        if (ext1 === '' || ext1 === '1') {
                                            return false;
                                        } else {
                                            return true;
                                        }
                                    },
                                    onClick: (obj) => {
                                        confirm({
                                            content: '????????????????????????????',
                                            onOk: () => {
                                                this.props.myFetch('auditAgreeZxEqAssetSell', ...obj.selectedRows).then(
                                                    ({ success, message }) => {
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
                                    }
                                },
                                {
                                    name: 'auditee',//??????add del
                                    type: 'primary',//??????  ?????? primary  [primary dashed danger]
                                    label: '???????????????',
                                    disabled: (obj) => {
                                        let data = obj.btnCallbackFn.getSelectedRows();
                                        if (data.length === 1 && data[0].auditStatus === '3') {
                                            return false;
                                        } else {
                                            return true;
                                        }
                                    },
                                    hide: () => {
                                        if (ext1 === '' || ext1 === '1') {
                                            return false;
                                        } else {
                                            return true;
                                        }
                                    },
                                    onClick: (obj) => {
                                        confirm({
                                            content: '???????????????????????????????',
                                            onOk: () => {
                                                this.props.myFetch('auditRefuseZxEqAssetSell', ...obj.selectedRows).then(
                                                    ({ success, message }) => {
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
                                    }
                                },
                                // {
                                //     name: 'preview',
                                //     type: 'primary',//??????  ?????? primary  [primary dashed danger]
                                //     label: '??????',
                                //     disabled: (obj) => {
                                //         let data = obj.btnCallbackFn.getSelectedRows();
                                //         if (data.length === 1) {
                                //             return false;
                                //         } else {
                                //             return true;
                                //         }
                                //     },
                                //     onClick: (obj) => {
                                //         if (obj.selectedRows[0]?.id === this.rowData?.id ) {
                                //             this.setState({ visible: true });
                                //         } else {
                                //             this.setState({
                                //                 rowData: obj.selectedRows[0]
                                //             }, () => {
                                //                 this.setState({ visible: true, loading: true });
                                //             })
                                //         }
                                //     }
                                // },
                                {
                                    name: 'del',//??????add del
                                    icon: 'delete',//icon
                                    type: 'danger',//??????  ?????? primary  [primary dashed danger]
                                    label: '??????',
                                    disabled: (obj) => {
                                        let data = obj.btnCallbackFn.getSelectedRows();
                                        if (data.filter(item => item.auditStatus === '3' || item.auditStatus === '4').length) {
                                            return true;
                                        } else {
                                            return false;
                                        }
                                    },
                                    fetchConfig: {//ajax??????
                                        apiName: 'batchDeleteUpdateZxEqAssetSell',
                                    },
                                }
                            ]}
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
                                        initialValue: companyId,
                                        hide: true,
                                    }
                                },
                                {
                                    isInTable: false,
                                    form: {
                                        field: 'comName',
                                        type: 'string',
                                        initialValue: companyName,
                                        hide: true,
                                    }
                                },
                                {
                                    isInTable: false,
                                    form: {
                                        field: 'outOrgID',
                                        type: 'string',
                                        initialValue: departmentId,
                                        hide: true,
                                    }
                                },
                                {
                                    table: {
                                        title: '?????????',
                                        dataIndex: 'approvalNo',
                                        key: 'approvalNo',
                                        onClick: 'detail'
                                    },
                                    form: {
                                        type: 'string',
                                        required: true,
                                        placeholder: '?????????',
                                        spanForm: 8
                                    }
                                },
                                {
                                    table: {
                                        title: '????????????',
                                        dataIndex: 'outOrgName',
                                        key: 'outOrgName'
                                    },
                                    form: {
                                        type: 'string',
                                        required: true,
                                        initialValue: departmentName,
                                        placeholder: '?????????',
                                        spanForm: 8
                                    }
                                },
                                {
                                    table: {
                                        title: '???????????????',
                                        dataIndex: 'inOrgName',
                                        key: 'inOrgName'
                                    },
                                    form: {
                                        type: 'string',
                                        required: true,
                                        placeholder: '?????????',
                                        spanForm: 8
                                    }
                                },
                                {
                                    table: {
                                        title: '????????????',
                                        dataIndex: 'equipName',
                                        key: 'equipName'
                                    },
                                    isInForm: false
                                },
                                {
                                    isInTable: false,
                                    form: {
                                        label: '????????????',
                                        field: 'reason',
                                        type: 'string',
                                        placeholder: '?????????',
                                        spanForm: 20,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 3 },
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
                                        title: '????????????',
                                        dataIndex: 'busDate',
                                        key: 'busDate',
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
                                        title: '????????????',
                                        dataIndex: 'inDate',
                                        key: 'inDate',
                                        format: 'YYYY-MM-DD'
                                    },
                                    form: {
                                        type: 'date',
                                        placeholder: '?????????',
                                        spanForm: 8
                                    }
                                },
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
                                            wrappedComponentRef: (me) => {
                                                this.tableOne = me;
                                            },
                                            tableTdEdit: true,
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
                                                        label: 'equipNo',
                                                        field: 'equipNo',
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
                                                        title: "<div>??????????????????<span style='color: #ff4d4f'>*</span></div>",
                                                        dataIndex: 'equipNo',
                                                        key: 'equipNo',
                                                        width: 200,
                                                        fixed: 'left',
                                                        tdEdit: true
                                                    },
                                                    form: {
                                                        field:'equipID',
                                                        type: 'select',
                                                        optionConfig: {
                                                            label: 'equipNo',
                                                            value: 'id',
                                                        },
                                                        fetchConfig: {
                                                            apiName: "getZxEqEquipListForDepreciation",
                                                            otherParams: () => {
                                                                return {
                                                                    useOrgID: this.table4.qnnForm.form.getFieldValue('outOrgID'),
                                                                    statusFlag:'888'
                                                                }
                                                            }
                                                        },
                                                        onChange: (val, obj, btnCallbackFn) => {
                                                            btnCallbackFn.getEditedRowData(false).then((editRowData) => {
                                                                editRowData.equipNo = obj.itemData.equipNo;
                                                                editRowData.financeNo = obj.itemData.financeNo;
                                                                editRowData.equipName = obj.itemData.equipName;
                                                                editRowData.spec = obj.itemData.spec;
                                                                editRowData.model = obj.itemData.model;
                                                                editRowData.orginalValue = obj.itemData.orginalValue;
                                                                editRowData.leftvalue = obj.itemData.leftValue;
                                                                editRowData.factory = obj.itemData.factory;
                                                                editRowData.outFactoryDate = obj.itemData.outFactoryDate;
                                                                btnCallbackFn.setEditedRowData({ ...editRowData });
                                                            });
                                                        },
                                                        allowClear: false,
                                                        showSearch: true,
                                                        required: true,
                                                        placeholder: '?????????',
                                                    },
                                                },
                                                {
                                                    table: {
                                                        title: '????????????',
                                                        dataIndex: 'financeNo',
                                                        key: 'financeNo',
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
                                                        dataIndex: 'equipName',
                                                        key: 'equipName',
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
                                                        title: '??????',
                                                        dataIndex: 'model',
                                                        key: 'model',
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
                                                        title: '??????',
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
                                                        title: '????????????',
                                                        dataIndex: 'outFactoryDate',
                                                        key: 'outFactoryDate',
                                                        format: 'YYYY-MM',
                                                        width: 150,
                                                        tdEdit: true
                                                    },
                                                    form: {
                                                        type: 'month',
                                                        placeholder: '?????????',
                                                    },
                                                },
                                                {
                                                    table: {
                                                        title: '????????????',
                                                        dataIndex: 'orginalValue',
                                                        key: 'orginalValue',
                                                        width: 150,
                                                        tdEdit: true
                                                    },
                                                    form: {
                                                        type: 'number',
                                                        min: 0,
                                                        placeholder: '?????????',
                                                    },
                                                },
                                                {
                                                    table: {
                                                        title: '????????????',
                                                        dataIndex: 'leftvalue',
                                                        key: 'leftvalue',
                                                        width: 150,
                                                        tdEdit: true
                                                    },
                                                    form: {
                                                        type: 'number',
                                                        min: 0,
                                                        placeholder: '?????????',
                                                    },
                                                },
                                                {
                                                    table: {
                                                        title: '????????????',
                                                        dataIndex: 'sellValue',
                                                        key: 'sellValue',
                                                        width: 150,
                                                        tdEdit: true
                                                    },
                                                    form: {
                                                        type: 'number',
                                                        min: 0,
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
                                    isInTable: false,
                                    form: {
                                        label: '?????????????????????????????????',
                                        field: 'outman1',
                                        type: 'string',
                                        placeholder: '?????????',
                                        spanForm: 8,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 14 },
                                                sm: { span: 14 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 10 },
                                                sm: { span: 10 }
                                            }
                                        }
                                    }
                                },
                                {
                                    isInTable: false,
                                    form: {
                                        label: '?????????????????????????????????',
                                        field: 'outman2',
                                        type: 'string',
                                        placeholder: '?????????',
                                        spanForm: 8,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 14 },
                                                sm: { span: 14 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 10 },
                                                sm: { span: 10 }
                                            }
                                        }
                                    }
                                },
                                {
                                    isInTable: false,
                                    form: {
                                        label: '????????????????????????',
                                        field: 'outman5',
                                        type: 'string',
                                        placeholder: '?????????',
                                        spanForm: 8,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 14 },
                                                sm: { span: 14 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 10 },
                                                sm: { span: 10 }
                                            }
                                        }
                                    }
                                },
                                {
                                    isInTable: false,
                                    form: {
                                        label: '?????????????????????????????????',
                                        field: 'outman3',
                                        type: 'string',
                                        placeholder: '?????????',
                                        spanForm: 8,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 14 },
                                                sm: { span: 14 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 10 },
                                                sm: { span: 10 }
                                            }
                                        }
                                    }
                                },
                                {
                                    isInTable: false,
                                    form: {
                                        label: '?????????????????????????????????',
                                        field: 'outman4',
                                        type: 'string',
                                        placeholder: '?????????',
                                        spanForm: 8,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 14 },
                                                sm: { span: 14 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 10 },
                                                sm: { span: 10 }
                                            }
                                        }
                                    }
                                },
                                {
                                    isInTable: false,
                                    form: {
                                        label: '????????????????????????',
                                        field: 'inman1',
                                        type: 'string',
                                        placeholder: '?????????',
                                        spanForm: 8,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 14 },
                                                sm: { span: 14 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 10 },
                                                sm: { span: 10 }
                                            }
                                        }
                                    }
                                },
                                {
                                    isInTable: false,
                                    form: {
                                        label: '????????????????????????',
                                        field: 'inman2',
                                        type: 'string',
                                        placeholder: '?????????',
                                        spanForm: 8,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 14 },
                                                sm: { span: 14 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 10 },
                                                sm: { span: 10 }
                                            }
                                        }
                                    }
                                },
                                {
                                    isInTable: false,
                                    form: {
                                        label: '????????????????????????',
                                        field: 'inman3',
                                        type: 'string',
                                        placeholder: '?????????',
                                        spanForm: 8,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 14 },
                                                sm: { span: 14 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 10 },
                                                sm: { span: 10 }
                                            }
                                        }
                                    }
                                },
                                {
                                    table: {
                                        title: '????????????',
                                        dataIndex: 'auditStatus',
                                        key: 'auditStatus',
                                        type: 'select'
                                    },
                                    form: {
                                        type: 'select',
                                        optionConfig: {
                                            label: 'label',
                                            value: 'value',
                                        },
                                        optionData: [
                                            {
                                                label: '?????????',
                                                value: '0'
                                            },
                                            {
                                                label: '?????????',
                                                value: '3'
                                            },
                                            {
                                                label: '????????????',
                                                value: '4'
                                            },
                                            {
                                                label: '???????????????',
                                                value: '5'
                                            }
                                        ],
                                        placeholder: '?????????',
                                        hide: (obj) => {
                                            if (obj.clickCb.rowInfo.name === 'add') {
                                                return true;
                                            }
                                            return false;
                                        },
                                        allowClear: false,
                                        showSearch: true,
                                        addDisabled: true,
                                        editDisabled: true,
                                        spanForm: 8
                                    }
                                },
                                {
                                    isInTable: false,
                                    form: {
                                        label: '??????',
                                        field: 'remark',
                                        type: 'textarea',
                                        placeholder: '?????????',
                                        spanForm: 20,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 3 },
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
                                        label: "??????",
                                        field: "fileList",
                                        type: "files",
                                        initialValue: [],
                                        fetchConfig: {
                                            apiName: "upload"
                                        },
                                        spanForm: 20,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 3 },
                                                sm: { span: 3 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 21 },
                                                sm: { span: 21 }
                                            }
                                        }
                                    }
                                }
                            ]}
                        />
                    </TabPane>
                    <TabPane tab="????????????" key="5">
                        <QnnTable
                            {...this.props}
                            fetch={this.props.myFetch}
                            upload={this.props.myUpload}
                            headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                            wrappedComponentRef={(me) => {
                                this.table5 = me;
                            }}
                            {...config5}
                            fetchConfig={{
                                apiName: 'getZxEqEquipScrapeList',
                                otherParams: {
                                    orgID: departmentId
                                }
                            }}
                            actionBtns={[
                                {
                                    name: 'add',//??????add del
                                    icon: 'plus',//icon
                                    type: 'primary',//??????  ?????? primary  [primary dashed danger]
                                    label: '??????',
                                    formBtns: [
                                        {
                                            name: 'cancel', //??????????????????
                                            type: 'dashed',//??????  ?????? primary
                                            label: '??????',
                                        },
                                        {
                                            name: 'submit',//??????add del
                                            type: 'primary',//??????  ?????? primary
                                            label: '??????',//???????????????????????????????????? 
                                            fetchConfig: {//ajax??????
                                                apiName: 'addZxEqEquipScrape',
                                            }
                                        }
                                    ]
                                },
                                {
                                    name: 'edit',//??????add del
                                    icon: 'edit',//icon
                                    type: 'primary',//??????  ?????? primary  [primary dashed danger]
                                    label: '??????',
                                    disabled: (obj) => {
                                        let data = obj.btnCallbackFn.getSelectedRows();
                                        if (data.length === 1 && (data[0].auditStatus === '0' || data[0].auditStatus === '3')) {
                                            return false;
                                        } else {
                                            return true;
                                        }
                                    },
                                    formBtns: [
                                        {
                                            name: 'cancel', //??????????????????
                                            type: 'dashed',//??????  ?????? primary
                                            label: '??????',
                                        },
                                        {
                                            name: 'submit',//??????add del
                                            type: 'primary',//??????  ?????? primary
                                            label: '??????',//???????????????????????????????????? 
                                            fetchConfig: {//ajax??????
                                                apiName: 'updateZxEqEquipScrape',
                                            },
                                            onClick: (obj) => {
                                                obj.btnCallbackFn.clearSelectedRows();
                                            }
                                        }
                                    ]
                                },
                                {
                                    name: 'Reported',//??????add del
                                    type: 'primary',//??????  ?????? primary  [primary dashed danger]
                                    label: '??????',
                                    disabled: (obj) => {
                                        let data = obj.btnCallbackFn.getSelectedRows();
                                        if (data.length === 1 && (data[0].auditStatus === '0' || data[0].auditStatus === '3')) {
                                            return false;
                                        } else {
                                            return true;
                                        }
                                    },
                                    hide: () => {
                                        if (ext1 === '2') {
                                            return false;
                                        } else {
                                            return true;
                                        }
                                    },
                                    onClick: (obj) => {
                                        confirm({
                                            content: '????????????????????????????',
                                            onOk: () => {
                                                this.props.myFetch('reportZxEqEquipScrape', ...obj.selectedRows).then(
                                                    ({ success, message }) => {
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
                                    }
                                },
                                {
                                    name: 'audit',//??????add del
                                    type: 'primary',//??????  ?????? primary  [primary dashed danger]
                                    label: '????????????',
                                    disabled: (obj) => {
                                        let data = obj.btnCallbackFn.getSelectedRows();
                                        if (data.length === 1 && data[0].auditStatus === '2') {
                                            return false;
                                        } else {
                                            return true;
                                        }
                                    },
                                    hide: () => {
                                        if (ext1 === '' || ext1 === '1') {
                                            return false;
                                        } else {
                                            return true;
                                        }
                                    },
                                    onClick: (obj) => {
                                        confirm({
                                            content: '????????????????????????????',
                                            onOk: () => {
                                                this.props.myFetch('agreeZxEqEquipScrape', ...obj.selectedRows).then(
                                                    ({ success, message }) => {
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
                                    }
                                },
                                {
                                    name: 'auditee',//??????add del
                                    type: 'primary',//??????  ?????? primary  [primary dashed danger]
                                    label: '???????????????',
                                    disabled: (obj) => {
                                        let data = obj.btnCallbackFn.getSelectedRows();
                                        if (data.length === 1 && data[0].auditStatus === '2') {
                                            return false;
                                        } else {
                                            return true;
                                        }
                                    },
                                    hide: () => {
                                        if (ext1 === '' || ext1 === '1') {
                                            return false;
                                        } else {
                                            return true;
                                        }
                                    },
                                    onClick: (obj) => {
                                        confirm({
                                            content: '???????????????????????????????',
                                            onOk: () => {
                                                this.props.myFetch('refuseZxEqEquipScrape', ...obj.selectedRows).then(
                                                    ({ success, message }) => {
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
                                    }
                                },
                                // {
                                //     name: 'preview',
                                //     type: 'primary',//??????  ?????? primary  [primary dashed danger]
                                //     label: '??????',
                                //     disabled: (obj) => {
                                //         let data = obj.btnCallbackFn.getSelectedRows();
                                //         if (data.length === 1) {
                                //             return false;
                                //         } else {
                                //             return true;
                                //         }
                                //     },
                                //     onClick: (obj) => {
                                //         if (obj.selectedRows[0]?.id === this.rowData?.id) {
                                //             this.setState({ visiblet: true });
                                //         } else {
                                //             this.setState({
                                //                 rowData: obj.selectedRows[0]
                                //             }, () => {
                                //                 this.setState({ visiblet: true, loading: true });
                                //             })
                                //         }
                                //     }
                                // },
                                {
                                    name: 'del',//??????add del
                                    icon: 'delete',//icon
                                    type: 'danger',//??????  ?????? primary  [primary dashed danger]
                                    label: '??????',
                                    disabled: (obj) => {
                                        let data = obj.btnCallbackFn.getSelectedRows();
                                        if (data.filter(item => item.auditStatus !== '0' && item.auditStatus !== '3').length) {
                                            return true;
                                        } else {
                                            return false;
                                        }
                                    },
                                    fetchConfig: {//ajax??????
                                        apiName: 'batchDeleteUpdateZxEqEquipScrape',
                                    },
                                }
                            ]}
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
                                        initialValue: companyId,
                                        hide: true,
                                    }
                                },
                                {
                                    isInTable: false,
                                    form: {
                                        field: 'comName',
                                        type: 'string',
                                        initialValue: companyName,
                                        hide: true,
                                    }
                                },
                                {
                                    isInTable: false,
                                    form: {
                                        field: 'orgID',
                                        type: 'string',
                                        initialValue: departmentId,
                                        hide: true,
                                    }
                                },
                                {
                                    table: {
                                        title: '?????????',
                                        dataIndex: 'approvalNo',
                                        key: 'approvalNo',
                                        fixed: 'left',
                                        onClick: 'detail',
                                        width: 150
                                    },
                                    form: {
                                        type: 'string',
                                        required: true,
                                        placeholder: '?????????',
                                        spanForm: 8
                                    }
                                },
                                {
                                    table: {
                                        title: '????????????',
                                        dataIndex: 'orgName',
                                        key: 'orgName',
                                        fixed: 'left',
                                        width: 150
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
                                        dataIndex: 'periodDate',
                                        key: 'periodDate',
                                        format: 'YYYY-MM',
                                        width: 100
                                    },
                                    form: {
                                        type: 'month',
                                        placeholder: '?????????',
                                        spanForm: 8
                                    }
                                },
                                {
                                    table: {
                                        title: '????????????',
                                        dataIndex: 'equipName',
                                        key: 'equipName',
                                        width: 100
                                    },
                                    form: {
                                        field: 'equipID',
                                        type: 'select',
                                        optionConfig: {
                                            label: 'equipName',
                                            value: 'id',
                                            linkageFields: {
                                                equipName: 'equipName',
                                                leftvalue: 'leftValue',
                                                deprevalue: 'actualDepreAmt',
                                                financeNo: 'financeNo',
                                                spec: 'spec',
                                                factory: 'factory',
                                                enginerno: 'enginerno',
                                                downserial: 'downserial',
                                                outFactoryDate: 'outFactoryDate',
                                                requireYear: 'requireYear',
                                                actualYear: 'actualYear',
                                                orginalValue: 'orginalValue',
                                                place: 'place',
                                                passNo: 'passNo',
                                            }
                                        },
                                        fetchConfig: {
                                            apiName: "getZxEqEquipListForDepreciation",
                                            otherParams: (obj) => {
                                                return {
                                                    useOrgID: obj.form.getFieldValue('orgID'),
                                                    leftValue: 0
                                                }
                                            }
                                        },
                                        allowClear: false,
                                        showSearch: true,
                                        required: true,
                                        addShow: true,
                                        editShow: true,
                                        detailShow: false,
                                        placeholder: '?????????',
                                        spanForm: 8
                                    }
                                },
                                {
                                    isInTable: false,
                                    form: {
                                        label: '????????????',
                                        field: 'equipName',
                                        type: 'string',
                                        required: true,
                                        addShow: false,
                                        editShow: false,
                                        detailShow: true,
                                        placeholder: '?????????',
                                        spanForm: 8
                                    }
                                },
                                {
                                    table: {
                                        title: '????????????',
                                        dataIndex: 'financeNo',
                                        key: 'financeNo',
                                        width: 100
                                    },
                                    form: {
                                        type: 'string',
                                        placeholder: '?????????',
                                        spanForm: 8
                                    }
                                },
                                {
                                    table: {
                                        title: '????????????',
                                        dataIndex: 'spec',
                                        key: 'spec',
                                        width: 100
                                    },
                                    form: {
                                        type: 'string',
                                        placeholder: '?????????',
                                        spanForm: 8
                                    }
                                },
                                {
                                    table: {
                                        title: '????????????',
                                        dataIndex: 'factory',
                                        key: 'factory',
                                        width: 100
                                    },
                                    form: {
                                        type: 'string',
                                        placeholder: '?????????',
                                        spanForm: 8
                                    }
                                },
                                {
                                    isInTable: false,
                                    form: {
                                        label: '????????????',
                                        field: 'enginerno',
                                        type: 'string',
                                        placeholder: '?????????',
                                        spanForm: 8
                                    }
                                },
                                {
                                    isInTable: false,
                                    form: {
                                        label: '???????????????',
                                        field: 'downserial',
                                        type: 'string',
                                        placeholder: '?????????',
                                        spanForm: 8
                                    }
                                },
                                {
                                    isInTable: false,
                                    form: {
                                        label: '????????????',
                                        field: 'outFactoryDate',
                                        type: 'date',
                                        placeholder: '?????????',
                                        spanForm: 8
                                    }
                                },
                                {
                                    table: {
                                        title: '??????????????????',
                                        dataIndex: 'requireYear',
                                        key: 'requireYear',
                                        width: 120
                                    },
                                    form: {
                                        type: 'string',
                                        placeholder: '?????????',
                                        spanForm: 8
                                    }
                                },
                                {
                                    table: {
                                        title: '??????????????????',
                                        dataIndex: 'actualYear',
                                        key: 'actualYear',
                                        width: 120
                                    },
                                    form: {
                                        type: 'string',
                                        placeholder: '?????????',
                                        spanForm: 8
                                    }
                                },
                                {
                                    table: {
                                        title: '????????????',
                                        dataIndex: 'orginalValue',
                                        key: 'orginalValue',
                                        width: 100
                                    },
                                    form: {
                                        type: 'number',
                                        min: 0,
                                        placeholder: '?????????',
                                        spanForm: 8
                                    }
                                },
                                {
                                    table: {
                                        title: '????????????',
                                        dataIndex: 'deprevalue',
                                        key: 'deprevalue',
                                        width: 100
                                    },
                                    form: {
                                        type: 'number',
                                        min: 0,
                                        placeholder: '?????????',
                                        spanForm: 8
                                    }
                                },
                                {
                                    table: {
                                        title: '????????????',
                                        dataIndex: 'leftvalue',
                                        key: 'leftvalue',
                                        width: 100
                                    },
                                    form: {
                                        type: 'number',
                                        min: 0,
                                        placeholder: '?????????',
                                        spanForm: 8
                                    }
                                },
                                {
                                    isInTable: false,
                                    form: {
                                        label: "????????????",
                                        field: 'place',
                                        type: 'string',
                                        placeholder: '?????????',
                                        spanForm: 8
                                    }
                                },
                                {
                                    isInTable: false,
                                    form: {
                                        label: "???????????????",
                                        field: 'passNo',
                                        type: 'string',
                                        placeholder: '?????????',
                                        spanForm: 8
                                    }
                                },
                                {
                                    isInTable: false,
                                    form: {
                                        label: "???????????????????????????",
                                        field: 'scrapeReason',
                                        type: 'string',
                                        placeholder: '?????????',
                                        spanForm: 8
                                    }
                                },
                                {
                                    isInTable: false,
                                    form: {
                                        label: "??????(???)??????????????????????????????",
                                        field: 'option1',
                                        type: 'string',
                                        placeholder: '?????????',
                                        spanForm: 20,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 6 },
                                                sm: { span: 6 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 18 },
                                                sm: { span: 18 }
                                            }
                                        }
                                    }
                                },
                                {
                                    table: {
                                        title: '???????????????????????????',
                                        dataIndex: 'handleway',
                                        key: 'handleway',
                                        width: 150,
                                        type: 'select'
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
                                                value: "0"
                                            },
                                            {
                                                label: "????????????",
                                                value: "1"
                                            },
                                            {
                                                label: "???????????????",
                                                value: "2"
                                            },
                                            {
                                                label: "???????????????",
                                                value: "3"
                                            },
                                            {
                                                label: "???????????????",
                                                value: "4"
                                            }
                                        ],
                                        allowClear: false,
                                        showSearch: true,
                                        placeholder: '?????????',
                                        spanForm: 20,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 6 },
                                                sm: { span: 6 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 18 },
                                                sm: { span: 18 }
                                            }
                                        }
                                    }
                                },
                                {
                                    table: {
                                        title: '????????????',
                                        dataIndex: 'scrapeDate',
                                        key: 'scrapeDate',
                                        format: "YYYY-MM-DD",
                                        width: 100
                                    },
                                    form: {
                                        type: 'date',
                                        required: true,
                                        placeholder: '?????????',
                                        spanForm: 20,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 6 },
                                                sm: { span: 6 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 18 },
                                                sm: { span: 18 }
                                            }
                                        }
                                    }
                                },
                                {
                                    isInTable: false,
                                    form: {
                                        label: "????????????????????????",
                                        field: 'option2',
                                        type: 'string',
                                        placeholder: '?????????',
                                        spanForm: 20,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 6 },
                                                sm: { span: 6 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 18 },
                                                sm: { span: 18 }
                                            }
                                        }
                                    }
                                },
                                {
                                    table: {
                                        title: '????????????',
                                        dataIndex: 'auditStatus',
                                        key: 'auditStatus',
                                        width: 100,
                                        fixed: 'right',
                                        render: (data) => {
                                            if (data === '0') {
                                                return '?????????';
                                            } else if (data === '1') {
                                                return '????????????';
                                            } else if (data === '2') {
                                                return '?????????';
                                            } else if (data === '3') {
                                                return '???????????????';
                                            }
                                        }
                                    },
                                    form: {
                                        type: 'select',
                                        optionConfig: {
                                            label: 'label',
                                            value: 'value',
                                        },
                                        optionData: [
                                            {
                                                label: '?????????',
                                                value: '0'
                                            },
                                            {
                                                label: '????????????',
                                                value: '1'
                                            },
                                            {
                                                label: '?????????',
                                                value: '2'
                                            },
                                            {
                                                label: '???????????????',
                                                value: '3'
                                            }
                                        ],
                                        placeholder: '?????????',
                                        hide: (obj) => {
                                            if (obj.clickCb.rowInfo.name === 'add') {
                                                return true;
                                            }
                                            return false;
                                        },
                                        allowClear: false,
                                        showSearch: true,
                                        addDisabled: true,
                                        editDisabled: true,
                                        spanForm: 20,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 6 },
                                                sm: { span: 6 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 18 },
                                                sm: { span: 18 }
                                            }
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
                                        spanForm: 20,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 6 },
                                                sm: { span: 6 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 18 },
                                                sm: { span: 18 }
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
                                        spanForm: 20,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 6 },
                                                sm: { span: 6 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 18 },
                                                sm: { span: 18 }
                                            }
                                        }
                                    }
                                }
                            ]}
                        />
                    </TabPane>
                </Tabs>
                {
                    visibles ? <div>
                        <Modal
                            width={'90%'}
                            style={{ paddingBottom: '0', top: '0' }}
                            title="????????????"
                            visible={this.state.visibles}
                            footer={null}
                            onCancel={() => {
                                this.setState({
                                    visibles: false
                                })
                            }}
                            bodyStyle={{ padding: '10px', overflow: 'hidden' }}
                            centered={true}
                            wrapClassName={'modals'}
                        >
                            <Spin spinning={loading}>
                                <iframe title="myf" width='100%' height={window.innerHeight * 0.8} frameBorder={0} onLoad={(obj) => {
                                    this.setState({ loading: false, flag: false })
                                }} src={`${ureport}preview?_u=file:zxEqMoveUseOrgItemList.xml&url=${domain}&delFlag=0&id=${rowData.id}&_t=6`}></iframe>
                            </Spin>

                        </Modal>
                    </div> : null
                }
                {
                    visible ? <div>
                        <Modal
                            width={'90%'}
                            style={{ paddingBottom: '0', top: '0' }}
                            title="????????????"
                            visible={this.state.visible}
                            footer={null}
                            onCancel={() => {
                                this.setState({
                                    visible: false
                                })
                            }}
                            bodyStyle={{ padding: '10px', overflow: 'hidden' }}
                            centered={true}
                            wrapClassName={'modals'}
                        >
                            <Spin spinning={loading}>
                                <iframe title="myf" width='100%' height={window.innerHeight * 0.8} frameBorder={0} onLoad={(obj) => {
                                    this.setState({ loading: false, flag: false })
                                }} src={`${ureport}preview?_u=file:zxEqAssetSellItemList.xml&url=${domain}&delFlag=0&_t=6`}></iframe>
                            </Spin>

                        </Modal>
                    </div> : null
                }
                {
                    visiblet ? <div>
                        <Modal
                            width={'90%'}
                            style={{ paddingBottom: '0', top: '0' }}
                            title="????????????"
                            visible={this.state.visiblet}
                            footer={null}
                            onCancel={() => {
                                this.setState({
                                    visiblet: false
                                })
                            }}
                            bodyStyle={{ padding: '10px', overflow: 'hidden' }}
                            centered={true}
                            wrapClassName={'modals'}
                        >
                            <Spin spinning={loading}>
                                <iframe title="myf" width='100%' height={window.innerHeight * 0.8} frameBorder={0} onLoad={(obj) => {
                                    this.setState({ loading: false, flag: false })
                                }} src={`${ureport}preview?_u=file:zxEqEquipScrape.xml&url=${domain}&delFlag=0&id=${rowData.id}&_t=6`}></iframe>
                            </Spin>

                        </Modal>
                    </div> : null
                }
            </div>
        );
    }
}

export default index;