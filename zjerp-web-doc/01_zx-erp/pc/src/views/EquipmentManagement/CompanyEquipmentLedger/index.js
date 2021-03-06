import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import s from "./style.less";
import { Modal, Avatar, message as Msg } from "antd";
const { confirm, info } = Modal;
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
        const curCompany = this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany;
        const lockProject = this.props.loginAndLogoutInfo.loginInfo.userInfo?.lockProject;
        this.state = {
            departmentId: ((curCompany?.ext1 === '1' || curCompany?.ext1 === '2') && lockProject?.projectId) ? (lockProject.projectId !== 'all' ? lockProject.projectId : curCompany?.companyId) : curCompany?.projectId
        }
    }
    render () {
        const { departmentId } = this.state;
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
                        apiName: 'getZxEqEquipList',
                        otherParams: {
                            useOrgID: departmentId,
                            statusFlag: '0'
                        }
                    }}
                    actionBtns={{
                        apiName: "getSysMenuBtn",
                        otherParams: (obj) => {
                            let props = obj.props;
                            let curRouteData = props.routerInfo.routeData[props.routerInfo.curKey];
                            return {
                                menuParentId: curRouteData._value,
                                tableField: "CompanyEquipmentLedger"
                            }
                        }
                    }}
                    method={{
                        leaveUnusedDisabled: (obj) => {
                            let data = obj.btnCallbackFn.getSelectedRows();
                            if (data.length === 1 && (data[0].isXianzhi === '' || data[0].isXianzhi === '0')) {
                                return false;
                            } else {
                                return true;
                            }
                        },
                        leaveUnusedOnClick: (obj) => {
                            confirm({
                                content: '????????????????',
                                onOk: () => {
                                    this.props.myFetch('unusedZxEqEquip', ...obj.selectedRows).then(
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
                        },
                        recoverDisabled: (obj) => {
                            let data = obj.btnCallbackFn.getSelectedRows();
                            if (data.length === 1 && data[0].isXianzhi === '1') {
                                return false;
                            } else {
                                return true;
                            }
                        },
                        recoverOnClick: (obj) => {
                            confirm({
                                content: '????????????????',
                                onOk: () => {
                                    this.props.myFetch('recoverZxEqEquip', ...obj.selectedRows).then(
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
                        },
                        QRCodeDisabled: (obj) => {
                            if (obj.btnCallbackFn.getSelectedRows().length === 1) {
                                return false;
                            } else {
                                return true;
                            }
                        },
                        QRCodeOnClick: (obj) => {
                            let { domain } = obj.props.myPublic;
                            let qrcodeUrl = obj.selectedRows[0].qrcodeUrl;
                            let qrcodeName = obj.selectedRows[0].qrcodeName;
                            info({
                                title: qrcodeName,
                                content: (
                                    <>
                                        <Avatar shape="square" size={150} src={domain + qrcodeUrl} />
                                    </>
                                ),
                                centered: true
                            });
                        },
                        QRCodeDownloadDisabled: (obj) => {
                            if (obj.btnCallbackFn.getSelectedRows().length === 1) {
                                return false;
                            } else {
                                return true;
                            }
                        },
                        QRCodeDownloadOnClick: (obj) => {
                            confirm({
                                content: '?????????????????????????',
                                onOk: () => {
                                    let { domain } = obj.props.myPublic;
                                    let qrcodeDownUrl = obj.selectedRows[0].qrcodeDownUrl;
                                    window.location.href = domain + qrcodeDownUrl;
                                }
                            });
                        }
                    }}
                    tabs={[
                        {
                            field: "form1",
                            name: "qnnForm",
                            title: "????????????",
                            content: {
                                formConfig: [
                                    {
                                        type: "string",
                                        label: "??????ID",
                                        field: "id", //?????????????????? ***??????
                                        hide: true
                                    },
                                    {
                                        field: 'companyId',
                                        type: 'string',
                                        hide: true,
                                    },
                                    {
                                        field: 'companyName',
                                        type: 'string',
                                        hide: true,
                                    },
                                    {
                                        field: 'abcType',
                                        type: 'string',
                                        hide: true,
                                    },
                                    {
                                        label: 'ABCD??????',
                                        field: 'abcName',
                                        type: 'string',
                                        placeholder: '?????????',
                                        span: 8
                                    },
                                    {
                                        label: '????????????',
                                        field: 'isWorkEquip',
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
                                                label: "????????????",
                                                value: "2"
                                            }
                                        ],
                                        allowClear: false,
                                        showSearch: true,
                                        required: true,
                                        placeholder: '?????????',
                                        span: 8
                                    },
                                    {
                                        label: '????????????',
                                        field: 'isMadeinChina',
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
                                                label: "??????",
                                                value: "1"
                                            }
                                        ],
                                        allowClear: false,
                                        showSearch: true,
                                        required: true,
                                        placeholder: '?????????',
                                        span: 8
                                    },
                                    {
                                        label: '??????????????????',
                                        field: 'equipNo',
                                        type: 'string',
                                        addDisabled: true,
                                        editDisabled: true,
                                        placeholder: '?????????',
                                        span: 8
                                    },
                                    {
                                        label: '????????????????????????',
                                        field: 'financeNo',
                                        type: 'string',
                                        placeholder: '?????????',
                                        span: 8
                                    },
                                    {
                                        label: '????????????',
                                        field: 'purDate',
                                        type: 'date',
                                        placeholder: '?????????',
                                        span: 8
                                    },
                                    {
                                        label: '????????????',
                                        field: 'equipName',
                                        type: 'string',
                                        required: true,
                                        placeholder: '?????????',
                                        span: 8
                                    },
                                    {
                                        label: '????????????',
                                        field: 'factory',
                                        type: 'string',
                                        placeholder: '?????????',
                                        span: 8
                                    },
                                    {
                                        label: '??????',
                                        field: 'model',
                                        type: 'string',
                                        required: true,
                                        placeholder: '?????????',
                                        span: 8
                                    },
                                    {
                                        label: '??????',
                                        field: 'spec',
                                        type: 'string',
                                        required: true,
                                        placeholder: '?????????',
                                        span: 8
                                    },
                                    {
                                        label: '????????????',
                                        field: 'resCatalog',
                                        type: 'string',
                                        required: true,
                                        placeholder: '?????????',
                                        span: 8
                                    },
                                    {
                                        label: '????????????',
                                        field: 'resCatalogID',
                                        type: 'string',
                                        hide: true
                                    },
                                    {
                                        label: '????????????',
                                        field: 'ownerOrgName',
                                        type: 'string',
                                        // optionConfig: {
                                        //     label: 'departmentName',
                                        //     value: 'departmentId',
                                        //     linkageFields:{
                                        //         ownerOrgName:'departmentName'
                                        //     }
                                        // },
                                        // fetchConfig: {
                                        //     apiName: 'getSysCompanyProject',
                                        //     otherParams:{
                                        //         departmentId:departmentId
                                        //     }
                                        // },
                                        // allowClear: false,
                                        // showSearch: true,
                                        required: true,
                                        addDisabled: true,
                                        editDisabled: true,
                                        placeholder: '?????????',
                                        span: 8
                                    },
                                    {
                                        label: '????????????',
                                        field: 'useOrgName',
                                        type: 'string',
                                        // optionConfig: {
                                        //     label: 'departmentName',
                                        //     value: 'departmentId',
                                        //     linkageFields:{
                                        //         useOrgName:'departmentName'
                                        //     }
                                        // },
                                        // fetchConfig: {
                                        //     apiName: 'getSysCompanyProject',
                                        //     otherParams:{
                                        //         departmentId:departmentId
                                        //     }
                                        // },
                                        // allowClear: false,
                                        // showSearch: true,
                                        addDisabled: true,
                                        editDisabled: true,
                                        placeholder: '?????????',
                                        span: 8
                                    },
                                    {
                                        label: '????????????',
                                        field: 'usePlace',
                                        type: 'string',
                                        // optionConfig: {
                                        //     label: 'itemName',
                                        //     value: 'itemName',
                                        // },
                                        // fetchConfig: {
                                        //     apiName: "getBaseCodeSelect",
                                        //     otherParams: {
                                        //         itemId: 'xingzhengquhuadaima'
                                        //     }
                                        // },
                                        allowClear: false,
                                        showSearch: true,
                                        placeholder: '?????????',
                                        span: 8
                                    },
                                    {
                                        label: '?????????(KW)',
                                        field: 'power',
                                        type: 'number',
                                        min: 0,
                                        required: true,
                                        placeholder: '?????????',
                                        span: 8
                                    },
                                    {
                                        label: '??????',
                                        field: 'source',
                                        type: 'select',
                                        optionConfig: {
                                            label: 'label',
                                            value: 'value',
                                        },
                                        optionData: [
                                            {
                                                label: "????????????",
                                                value: "0"
                                            },
                                            {
                                                label: "????????????",
                                                value: "1"
                                            }
                                        ],
                                        allowClear: false,
                                        showSearch: true,
                                        required: true,
                                        placeholder: '?????????',
                                        span: 8
                                    },
                                    {
                                        label: '???????????????',
                                        field: 'viceserial',
                                        type: 'string',
                                        placeholder: '?????????',
                                        span: 8
                                    },
                                    {
                                        label: '????????????',
                                        field: 'downfactory',
                                        type: 'string',
                                        placeholder: '?????????',
                                        span: 8
                                    },
                                    {
                                        label: '??????????????????',
                                        field: 'viceoutfactory',
                                        type: 'date',
                                        placeholder: '?????????',
                                        span: 8
                                    },
                                    {
                                        label: '????????????',
                                        field: 'vicespec',
                                        type: 'string',
                                        placeholder: '?????????',
                                        span: 8
                                    },
                                    {
                                        label: '????????????',
                                        field: 'viceFactory',
                                        type: 'string',
                                        placeholder: '?????????',
                                        span: 8
                                    },
                                    {
                                        label: '??????????????????',
                                        field: 'mainoutfactory',
                                        type: 'date',
                                        placeholder: '?????????',
                                        span: 8
                                    },
                                    {
                                        label: '???????????????',
                                        field: 'mainserial',
                                        type: 'string',
                                        placeholder: '?????????',
                                        span: 8
                                    },
                                    {
                                        label: '????????????',
                                        field: 'mainspec',
                                        type: 'string',
                                        placeholder: '?????????',
                                        span: 8
                                    },
                                    {
                                        label: '????????????',
                                        field: 'mainFactory',
                                        type: 'string',
                                        placeholder: '?????????',
                                        span: 8
                                    },
                                    {
                                        label: '????????????',
                                        field: 'downspec',
                                        type: 'string',
                                        placeholder: '?????????',
                                        span: 8
                                    },
                                    {
                                        label: '???????????????',
                                        field: 'downserial',
                                        type: 'string',
                                        placeholder: '?????????',
                                        span: 8
                                    },
                                    {
                                        label: '??????????????????',
                                        field: 'downoutfactory',
                                        type: 'date',
                                        placeholder: '?????????',
                                        span: 8
                                    },
                                    {
                                        label: '?????????????????????(mm)',
                                        field: 'heightlong',
                                        type: 'string',
                                        placeholder: '?????????',
                                        span: 8
                                    },
                                    {
                                        label: '??????(t)',
                                        field: 'weight',
                                        type: 'string',
                                        placeholder: '?????????',
                                        span: 8
                                    },
                                    {
                                        label: '??????',
                                        field: 'orginalValue',
                                        type: 'number',
                                        min: 0,
                                        precision: 2,
                                        placeholder: '?????????',
                                        span: 8
                                    },
                                    {
                                        label: '??????',
                                        field: 'leftValue',
                                        type: 'number',
                                        min: 0,
                                        precision: 2,
                                        placeholder: '?????????',
                                        span: 8
                                    },
                                    {
                                        label: '????????????',
                                        field: 'acceptanceDate',
                                        type: 'date',
                                        placeholder: '?????????',
                                        span: 8
                                    },
                                    {
                                        label: '????????????',
                                        field: 'passNo',
                                        type: 'string',
                                        placeholder: '?????????',
                                        span: 8
                                    },
                                    {
                                        label: '????????????',
                                        field: 'outFactoryDate',
                                        type: 'date',
                                        required: true,
                                        placeholder: '?????????',
                                        span: 8
                                    },
                                    {
                                        label: '??????????????????',
                                        field: 'isimportant',
                                        type: 'radio',
                                        optionData: [
                                            {
                                                label: '???',
                                                value: '0'
                                            },
                                            {
                                                label: '???',
                                                value: '1'
                                            }
                                        ],
                                        initialValue: '0',
                                        required: true,
                                        placeholder: '?????????',
                                        span: 8
                                    },
                                    {
                                        label: '????????????',
                                        field: 'actualDepreAmt',
                                        type: 'number',
                                        min: 0,
                                        placeholder: '?????????',
                                        span: 8
                                    },
                                    {
                                        label: '????????????(KW)',
                                        field: 'mainpower',
                                        type: 'number',
                                        min: 0,
                                        placeholder: '?????????',
                                        span: 8
                                    },
                                    {
                                        label: '????????????(KW)',
                                        field: 'vicepower',
                                        type: 'number',
                                        min: 0,
                                        placeholder: '?????????',
                                        span: 8
                                    },
                                    {
                                        label: '????????????',
                                        field: 'regdate',
                                        type: 'date',
                                        placeholder: '?????????',
                                        span: 8
                                    },
                                    {
                                        label: '???????????????',
                                        field: 'planNo',
                                        type: 'string',
                                        placeholder: '?????????',
                                        span: 8
                                    },
                                    {
                                        label: '????????????',
                                        field: 'isXianzhi',
                                        type: 'select',
                                        optionConfig: {
                                            label: 'label',
                                            value: 'value'
                                        },
                                        optionData: [
                                            {
                                                label: '???',
                                                value: '0'
                                            },
                                            {
                                                label: '???',
                                                value: '1'
                                            }
                                        ],
                                        placeholder: '?????????',
                                        span: 8
                                    },
                                    {
                                        label: '??????',
                                        field: 'remark',
                                        type: 'textarea',
                                        placeholder: '?????????',
                                        span: 24,
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
                                    },
                                    {
                                        label: "??????",
                                        field: "fileList",
                                        type: "files",
                                        initialValue: [],
                                        fetchConfig: {
                                            apiName: "upload"
                                        },
                                        span: 24,
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
                                ]
                            }
                        },
                        {
                            field: "table1",
                            name: "qnnTable",
                            title: "???????????????",
                            disabled: (obj) => {
                                if (obj.clickCb.rowInfo.name === 'edit') {
                                    return true;
                                } else {
                                    return false;
                                }
                            },
                            content: {
                                tabs: [],
                                fetchConfig: {
                                    apiName: 'getZxEqMoveUseOrgItemListForTab',
                                    otherParams: (obj) => {
                                        let rowData = obj.clickCb.rowData;
                                        if (rowData) {
                                            return { equipID: obj.clickCb.rowData.id };
                                        }
                                    },
                                },
                                wrappedComponentRef: (me) => {
                                    this.tableGS = me;
                                },
                                drawerConfig: {
                                    width: "1000px"
                                },
                                antd: {
                                    rowKey: 'id',
                                    size: 'small'
                                },
                                paginationConfig: {
                                    position: "bottom"
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
                                },
                                isShowRowSelect: false,
                                formConfig: [
                                    {
                                        isInTable: false,
                                        form: {
                                            field: 'id',
                                            type: 'string',
                                            hide: true,
                                        }
                                    },
                                    {
                                        table: {
                                            title: '?????????',
                                            dataIndex: 'transferNo',
                                            key: 'transferNo',
                                            filter: true,
                                            onClick: 'detail'
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
                                            dataIndex: 'movedate',
                                            key: 'movedate',
                                            format: 'YYYY-MM-DD'
                                        },
                                        form: {
                                            type: 'date',
                                            placeholder: '?????????',
                                            spanForm: 8
                                        }
                                    },
                                    {
                                        table: {
                                            title: '?????????',
                                            dataIndex: 'outOrgName',
                                            key: 'outOrgName'
                                        },
                                        form: {
                                            type: 'string',
                                            allowClear: false,
                                            showSearch: true,
                                            placeholder: '?????????',
                                            spanForm: 8
                                        }
                                    },
                                    {
                                        table: {
                                            title: '?????????',
                                            dataIndex: 'acceptOrgName',
                                            key: 'acceptOrgName'
                                        },
                                        form: {
                                            type: 'string',
                                            allowClear: false,
                                            showSearch: true,
                                            placeholder: '?????????',
                                            spanForm: 8
                                        }
                                    },
                                    {
                                        table: {
                                            title: '??????',
                                            dataIndex: 'orginalValue',
                                            key: 'orginalValue'
                                        },
                                        form: {
                                            type: 'number',
                                            min: 0,
                                            precision: 2,
                                            placeholder: '?????????',
                                            spanForm: 8
                                        }
                                    },
                                    {
                                        table: {
                                            title: '??????',
                                            dataIndex: 'leftValue',
                                            key: 'leftValue'
                                        },
                                        form: {
                                            type: 'number',
                                            min: 0,
                                            precision: 2,
                                            placeholder: '?????????',
                                            spanForm: 8
                                        }
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
                                        isInTable: false,
                                        form: {
                                            label: '????????????',
                                            field: 'careinfo',
                                            type: 'string',
                                            placeholder: '?????????',
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
                                    }
                                ]
                            }
                        },
                        {
                            field: "table2",
                            name: "qnnTable",
                            title: "????????????",
                            disabled: (obj) => {
                                if (obj.clickCb.rowInfo.name === 'edit') {
                                    return true;
                                } else {
                                    return false;
                                }
                            },
                            content: {
                                tabs: [],
                                fetchConfig: {
                                    apiName: 'getZxEqEquipDepreciationItemListForTab',
                                    otherParams: (obj) => {
                                        let rowData = obj.clickCb.rowData;
                                        if (rowData) {
                                            return { equipID: obj.clickCb.rowData.id };
                                        }
                                    },
                                },
                                wrappedComponentRef: (me) => {
                                    this.tableZC = me;
                                },
                                drawerConfig: {
                                    width: "1000px"
                                },
                                antd: {
                                    rowKey: 'id',
                                    size: 'small'
                                },
                                paginationConfig: {
                                    position: "bottom"
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
                                },
                                isShowRowSelect: false,
                                formConfig: [
                                    {
                                        isInTable: false,
                                        form: {
                                            field: 'id',
                                            type: 'string',
                                            hide: true,
                                        }
                                    },
                                    {
                                        table: {
                                            title: '????????????',
                                            dataIndex: 'orgID',
                                            key: 'orgID',
                                            filter: true,
                                            type: 'select'
                                        },
                                        form: {
                                            type: 'select',
                                            optionConfig: {
                                                label: 'departmentName',
                                                value: 'departmentId'
                                            },
                                            fetchConfig: {
                                                apiName: 'getSysCompanyProject'
                                            },
                                            allowClear: false,
                                            showSearch: true,
                                            placeholder: '?????????',
                                            spanForm: 8
                                        }
                                    },
                                    {
                                        table: {
                                            title: '????????????',
                                            dataIndex: 'depreDate',
                                            key: 'depreDate',
                                            format: 'YYYY-MM-DD'
                                        },
                                        form: {
                                            type: 'date',
                                            placeholder: '?????????',
                                            spanForm: 8
                                        }
                                    },
                                    {
                                        table: {
                                            title: '????????????',
                                            dataIndex: 'depreperiodDate',
                                            key: 'depreperiodDate',
                                            format: 'YYYY-MM'
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
                                            dataIndex: 'useOrgID',
                                            key: 'useOrgID',
                                            type: 'select'
                                        },
                                        form: {
                                            type: 'select',
                                            optionConfig: {
                                                label: 'departmentName',
                                                value: 'departmentId'
                                            },
                                            fetchConfig: {
                                                apiName: 'getSysCompanyProject'
                                            },
                                            allowClear: false,
                                            showSearch: true,
                                            placeholder: '?????????',
                                            spanForm: 8
                                        }
                                    },
                                    {
                                        table: {
                                            title: '????????????',
                                            dataIndex: 'depreamout',
                                            key: 'depreamout'
                                        },
                                        form: {
                                            type: 'number',
                                            min: 0,
                                            precision: 2,
                                            placeholder: '?????????',
                                            spanForm: 8
                                        }
                                    },
                                    {
                                        table: {
                                            title: '??????',
                                            dataIndex: 'orginalValue',
                                            key: 'orginalValue',
                                            onClick: 'detail'
                                        },
                                        form: {
                                            type: 'number',
                                            min: 0,
                                            precision: 2,
                                            placeholder: '?????????',
                                            spanForm: 8
                                        }
                                    },
                                    {
                                        table: {
                                            title: '??????',
                                            dataIndex: 'leftValue',
                                            key: 'leftValue'
                                        },
                                        form: {
                                            type: 'number',
                                            min: 0,
                                            precision: 2,
                                            placeholder: '?????????',
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
                                    }
                                ]
                            }
                        },
                        {
                            field: "table3",
                            name: "qnnTable",
                            title: "????????????",
                            disabled: (obj) => {
                                if (obj.clickCb.rowInfo.name === 'edit') {
                                    return true;
                                } else {
                                    return false;
                                }
                            },
                            content: {
                                tabs: [],
                                fetchConfig: {
                                    apiName: 'getZxEqMoveSupervisorItemListForTab',
                                    otherParams: (obj) => {
                                        let rowData = obj.clickCb.rowData;
                                        if (rowData) {
                                            return { equipID: obj.clickCb.rowData.id };
                                        }
                                    },
                                },
                                wrappedComponentRef: (me) => {
                                    this.tableJN = me;
                                },
                                drawerConfig: {
                                    width: "1000px"
                                },
                                antd: {
                                    rowKey: 'id',
                                    size: 'small'
                                },
                                paginationConfig: {
                                    position: "bottom"
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
                                },
                                isShowRowSelect: false,
                                formConfig: [
                                    {
                                        isInTable: false,
                                        form: {
                                            field: 'id',
                                            type: 'string',
                                            hide: true,
                                        }
                                    },
                                    {
                                        table: {
                                            title: '?????????',
                                            dataIndex: 'billNo',
                                            key: 'billNo',
                                            filter: true,
                                            onClick: 'detail'
                                        },
                                        form: {
                                            type: 'string',
                                            placeholder: '?????????',
                                            spanForm: 8
                                        }
                                    },
                                    {
                                        table: {
                                            title: '?????????',
                                            dataIndex: 'outOrgName',
                                            key: 'outOrgName'
                                        },
                                        form: {
                                            type: 'string',
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
                                            type: 'string',
                                            placeholder: '?????????',
                                            spanForm: 8
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
                                ]
                            }
                        },
                        {
                            field: "table4",
                            name: "qnnTable",
                            title: "????????????",
                            disabled: (obj) => {
                                if (obj.clickCb.rowInfo.name === 'edit') {
                                    return true;
                                } else {
                                    return false;
                                }
                            },
                            content: {
                                tabs: [],
                                fetchConfig: {
                                    apiName: 'getZxEqEWorkList',
                                    otherParams: (obj) => {
                                        let rowData = obj.clickCb.rowData;
                                        if (rowData) {
                                            return { equipID: obj.clickCb.rowData.id };
                                        }
                                    },
                                },
                                wrappedComponentRef: (me) => {
                                    this.tableJN = me;
                                },
                                drawerConfig: {
                                    width: "1000px"
                                },
                                antd: {
                                    rowKey: 'id',
                                    size: 'small'
                                },
                                paginationConfig: {
                                    position: "bottom"
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
                                },
                                isShowRowSelect: false,
                                formConfig: [
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
                                            field: 'orgID',
                                            type: 'string',
                                            hide: true,
                                        }
                                    },
                                    {
                                        table: {
                                            title: '????????????',
                                            dataIndex: 'orgName',
                                            key: 'orgName',
                                            onClick: "detail",
                                            filter: true
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
                                            dataIndex: 'equipNo',
                                            key: 'equipNo'
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
                                            dataIndex: 'equipName',
                                            key: 'equipName'
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
                                            key: 'spec'
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
                                            dataIndex: 'bizDate',
                                            key: 'bizDate',
                                            format: 'YYYY-MM-DD'
                                        },
                                        form: {
                                            type: 'date',
                                            placeholder: '?????????',
                                            spanForm: 8
                                        }
                                    },
                                    {
                                        table: {
                                            title: '??????',
                                            dataIndex: 'status',
                                            key: 'status',
                                            render: (data) => {
                                                if (!data) {
                                                    return '?????????';
                                                } else {
                                                    return data;
                                                }
                                            }
                                        },
                                        form: {
                                            hide: true,
                                            type: 'string',
                                            placeholder: '?????????',
                                            spanForm: 8
                                        }
                                    },
                                    {
                                        table: {
                                            title: '??????',
                                            dataIndex: 'remark',
                                            key: 'remark'
                                        },
                                        form: {
                                            type: 'textarea',
                                            placeholder: '?????????',
                                            spanForm: 21,
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
                                            spanForm: 21,
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
                                ]
                            }
                        },
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
                                label: 'ABCD??????',
                                field: 'abcType',
                                addDisabled: true,
                                editDisabled: true,
                                type: 'select',
                                optionConfig: {
                                    label: 'globalCode',
                                    value: 'id',
                                },
                                fetchConfig: {
                                    apiName: "getZxEqGlobalCodeList",
                                    otherParams: {
                                        categoryID: "category100203",
                                        startFlag: '1'
                                    }
                                },
                                allowClear: false,
                                showSearch: true,
                                placeholder: '?????????',
                                spanForm: 8
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: '????????????',
                                field: 'isWorkEquip',
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
                                        label: "????????????",
                                        value: "2"
                                    }
                                ],
                                allowClear: false,
                                showSearch: true,
                                required: true,
                                placeholder: '?????????',
                                spanForm: 8
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: '????????????',
                                field: 'isMadeinChina',
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
                                        label: "??????",
                                        value: "1"
                                    }
                                ],
                                allowClear: false,
                                showSearch: true,
                                required: true,
                                placeholder: '?????????',
                                spanForm: 8
                            }
                        },
                        {
                            table: {
                                title: '??????????????????',
                                dataIndex: 'equipNo',
                                key: 'equipNo',
                                width: 200,
                                fixed: 'left',
                                filter: true
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
                            table: {
                                title: '????????????????????????',
                                dataIndex: 'financeNo',
                                key: 'financeNo',
                                onClick: 'detail',
                                filter: true,
                                width: 160
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
                                field: 'purDate',
                                type: 'date',
                                placeholder: '?????????',
                                spanForm: 8
                            }
                        },
                        {
                            table: {
                                title: '????????????',
                                dataIndex: 'equipName',
                                key: 'equipName',
                                width: 150,
                                filter: true
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
                                dataIndex: 'factory',
                                key: 'factory',
                                width: 150,
                            },
                            form: {
                                type: 'string',
                                placeholder: '?????????',
                                spanForm: 8
                            }
                        },
                        {
                            table: {
                                title: '??????',
                                dataIndex: 'model',
                                key: 'model',
                                width: 150,
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
                                title: '??????',
                                dataIndex: 'spec',
                                key: 'spec',
                                width: 150,
                            },
                            form: {
                                type: 'string',
                                required: true,
                                placeholder: '?????????',
                                spanForm: 8
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: '????????????',
                                field: 'resCatalogID',
                                type: 'select',
                                optionConfig: {
                                    label: 'catName',
                                    value: 'id',
                                    children: "childrenList"
                                },
                                fetchConfig: {
                                    apiName: "getZxEqResCategoryList",
                                    otherParams: {
                                        parentID: '0003',
                                        isGroup: '1'
                                    }
                                },
                                allowClear: false,
                                showSearch: true,
                                required: true,
                                placeholder: '?????????',
                                spanForm: 8
                            }
                        },
                        {
                            table: {
                                title: '????????????',
                                dataIndex: 'ownerOrgName',
                                key: 'ownerOrgName',
                                width: 150,
                                filter: true
                                // type:"select"
                            },
                            form: {
                                type: 'string',
                                required: true,
                                // optionConfig: {
                                //     label: 'departmentName',
                                //     value: 'departmentId'
                                // },
                                // fetchConfig: {
                                //     apiName: 'getSysCompanyProject',
                                //     otherParams:{
                                //         departmentId:departmentId
                                //     }
                                // },
                                placeholder: '?????????',
                                spanForm: 8
                            }
                        },
                        {
                            table: {
                                title: '????????????',
                                dataIndex: 'useOrgName',
                                key: 'useOrgName',
                                width: 150,
                                filter: true
                            },
                            form: {
                                type: 'string',
                                // optionConfig: {
                                //     label: 'departmentName',
                                //     value: 'departmentId'
                                // },
                                // fetchConfig: {
                                //     apiName: 'getSysCompanyProject',
                                //     otherParams:{
                                //         departmentId:departmentId
                                //     }
                                // },
                                addDisabled: true,
                                editDisabled: true,
                                placeholder: '?????????',
                                spanForm: 8
                            }
                        },
                        {
                            table: {
                                title: '????????????',
                                dataIndex: 'usePlace',
                                key: 'usePlace',
                                width: 150,
                            },
                            form: {
                                type: 'string',
                                // optionConfig: {
                                //     label: 'itemName',
                                //     value: 'itemName',
                                // },
                                // fetchConfig: {
                                //     apiName: "getBaseCodeSelect",
                                //     otherParams: {
                                //         itemId: 'suoShuQuYu'
                                //     }
                                // },
                                allowClear: false,
                                showSearch: true,
                                placeholder: '?????????',
                                spanForm: 8
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: '?????????(KW)',
                                field: 'power',
                                type: 'number',
                                min: 0,
                                required: true,
                                placeholder: '?????????',
                                spanForm: 8
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: '??????',
                                field: 'source',
                                type: 'select',
                                optionConfig: {
                                    label: 'label',
                                    value: 'value',
                                },
                                optionData: [
                                    {
                                        label: "????????????",
                                        value: "0"
                                    },
                                    {
                                        label: "????????????",
                                        value: "1"
                                    }
                                ],
                                allowClear: false,
                                showSearch: true,
                                required: true,
                                placeholder: '?????????',
                                spanForm: 8
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: '???????????????',
                                field: 'viceserial',
                                type: 'string',
                                placeholder: '?????????',
                                spanForm: 8
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: '????????????',
                                field: 'downfactory',
                                type: 'string',
                                placeholder: '?????????',
                                spanForm: 8
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: '??????????????????',
                                field: 'viceoutfactory',
                                type: 'date',
                                placeholder: '?????????',
                                spanForm: 8
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: '????????????',
                                field: 'vicespec',
                                type: 'string',
                                placeholder: '?????????',
                                spanForm: 8
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: '????????????',
                                field: 'viceFactory',
                                type: 'string',
                                placeholder: '?????????',
                                spanForm: 8
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: '??????????????????',
                                field: 'mainoutfactory',
                                type: 'date',
                                placeholder: '?????????',
                                spanForm: 8
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: '???????????????',
                                field: 'mainserial',
                                type: 'string',
                                placeholder: '?????????',
                                spanForm: 8
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: '????????????',
                                field: 'mainspec',
                                type: 'string',
                                placeholder: '?????????',
                                spanForm: 8
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: '????????????',
                                field: 'mainFactory',
                                type: 'string',
                                placeholder: '?????????',
                                spanForm: 8
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: '????????????',
                                field: 'downspec',
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
                                label: '??????????????????',
                                field: 'downoutfactory',
                                type: 'date',
                                placeholder: '?????????',
                                spanForm: 8
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: '?????????????????????(mm)',
                                field: 'heightlong',
                                type: 'string',
                                placeholder: '?????????',
                                spanForm: 8
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: '??????(t)',
                                field: 'weight',
                                type: 'string',
                                placeholder: '?????????',
                                spanForm: 8
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: '??????',
                                field: 'orginalValue',
                                type: 'number',
                                min: 0,
                                placeholder: '?????????',
                                spanForm: 8
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: '??????',
                                field: 'leftValue',
                                type: 'number',
                                min: 0,
                                placeholder: '?????????',
                                spanForm: 8
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: '????????????',
                                field: 'acceptanceDate',
                                type: 'date',
                                placeholder: '?????????',
                                spanForm: 8
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: '????????????',
                                field: 'passNo',
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
                                required: true,
                                placeholder: '?????????',
                                spanForm: 8
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: '??????????????????',
                                field: 'isimportant',
                                type: 'radio',
                                optionData: [
                                    {
                                        label: '???',
                                        value: '0'
                                    },
                                    {
                                        label: '???',
                                        value: '1'
                                    }
                                ],
                                initialValue: '0',
                                required: true,
                                placeholder: '?????????',
                                spanForm: 8
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: '????????????',
                                field: 'actualDepreAmt',
                                type: 'number',
                                min: 0,
                                placeholder: '?????????',
                                spanForm: 8
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: '????????????(KW)',
                                field: 'mainpower',
                                type: 'number',
                                min: 0,
                                placeholder: '?????????',
                                spanForm: 8
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: '????????????(KW)',
                                field: 'vicepower',
                                type: 'number',
                                min: 0,
                                placeholder: '?????????',
                                spanForm: 8
                            }
                        },
                        {
                            table: {
                                title: '????????????',
                                dataIndex: 'regdate',
                                key: 'regdate',
                                width: 150,
                                format: 'YYYY-MM-DD'
                            },
                            form: {
                                type: 'date',
                                placeholder: '?????????',
                                spanForm: 8
                            }
                        },
                        {
                            table: {
                                title: '???????????????',
                                dataIndex: 'planNo',
                                key: 'planNo',
                                width: 150,
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
                                dataIndex: 'isXianzhi',
                                key: 'isXianzhi',
                                width: 100,
                                fixed: 'right',
                                render: (data) => {
                                    if (data === '0') {
                                        return '???';
                                    } else if (data === '1') {
                                        return '???';
                                    }
                                }
                            },
                            form: {
                                type: 'select',
                                optionConfig: {
                                    label: 'label',
                                    value: 'value'
                                },
                                optionData: [
                                    {
                                        label: '???',
                                        value: '0'
                                    },
                                    {
                                        label: '???',
                                        value: '1'
                                    }
                                ],
                                placeholder: '?????????',
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
                                spanForm: 24,
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
                                spanForm: 24,
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
            </div>
        );
    }
}

export default index;