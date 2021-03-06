import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import { Modal, message as Msg, Drawer } from "antd";
import TabTwoList from './TabTwo';
import TabThreeList from './TabThree';
import TabFourList from './TabFour';
import TabFiveList from './TabFive';
import YuanZhi from './YuanZhi'
const { confirm } = Modal;
const config = {
    antd: {
        rowKey: function (row) {
            return row.id
        },
        size: 'small'
    },
    drawerConfig: {
        width: '70%'
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
            visible: false,
            departmentId: ((curCompany?.ext1 === '1' || curCompany?.ext1 === '2') && lockProject?.projectId) ? (lockProject.projectId !== 'all' ? lockProject.projectId : curCompany?.companyId) : curCompany?.projectId,
        }
    }
    onClose = () => {
        this.setState({
            visible: false,
        });
    };
    render() {
        const { visible, departmentId } = this.state;
        return (
            <div>
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
                            ureportFlag: 'comCarForMech',
                        }
                    }}
                    actionBtns={[
                        {
                            name: 'leaveUnused',//??????add del
                            type: 'primary',//??????  ?????? primary  [primary dashed danger]
                            label: '??????',
                            disabled: (obj) => {
                                let data = obj.btnCallbackFn.getSelectedRows();
                                if (data.length === 1 && (data[0].isXianzhi === '' || data[0].isXianzhi === '0')) {
                                    return false;
                                } else {
                                    return true;
                                }
                            },
                            onClick: (obj) => {
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
                            }
                        },
                        {
                            name: 'recover',//??????add del
                            type: 'primary',//??????  ?????? primary  [primary dashed danger]
                            label: '??????',
                            disabled: (obj) => {
                                let data = obj.btnCallbackFn.getSelectedRows();
                                if (data.length === 1 && data[0].isXianzhi === '1') {
                                    return false;
                                } else {
                                    return true;
                                }
                            },
                            onClick: (obj) => {
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
                            }
                        },
                        {
                            name: 'edit',//??????add del
                            icon: 'edit',//icon
                            type: 'primary',//??????  ?????? primary  [primary dashed danger]
                            label: '??????',
                            willExecute: (obj) => {
                                obj.btnCallbackFn.setActiveKey('0');
                            },
                            hide: true,
                            formBtns: [
                                {
                                    name: 'cancel', //??????????????????
                                    field: 'cancel',
                                    type: 'dashed',//??????  ?????? primary
                                    label: '??????',
                                },
                                {
                                    name: 'submit',//??????add del
                                    field: 'submit',
                                    type: 'primary',//??????  ?????? primary
                                    label: '??????',//???????????????????????????????????? 
                                    fetchConfig: {//ajax??????
                                        apiName: 'updateZxEqEquip',
                                    },
                                    onClick: (objs) => {
                                        objs.btnCallbackFn.clearSelectedRows();
                                    }
                                }
                            ]
                        },
                        // {
                        //     name: 'QRCode',//??????add del
                        //     type: 'primary',//??????  ?????? primary  [primary dashed danger]
                        //     label: '?????????',
                        //     disabled: (obj) => {
                        //         if (obj.btnCallbackFn.getSelectedRows().length === 1) {
                        //             return false;
                        //         } else {
                        //             return true;
                        //         }
                        //     },
                        //     onClick: (obj) => {
                        //         let { domain } = obj.props.myPublic;
                        //         let qrcodeUrl = obj.selectedRows[0].qrcodeUrl;
                        //         let qrcodeName = obj.selectedRows[0].qrcodeName;
                        //         info({
                        //             title: qrcodeName,
                        //             content: (
                        //                 <>
                        //                     <Avatar shape="square" size={150} src={domain + qrcodeUrl} />
                        //                 </>
                        //             ),
                        //             centered: true
                        //         });
                        //     }
                        // },
                        // {
                        //     name: 'QRCodeDownload',//??????add del
                        //     type: 'primary',//??????  ?????? primary  [primary dashed danger]
                        //     label: '???????????????',
                        //     disabled: (obj) => {
                        //         if (obj.btnCallbackFn.getSelectedRows().length === 1) {
                        //             return false;
                        //         } else {
                        //             return true;
                        //         }
                        //     },
                        //     onClick: (obj) => {
                        //         confirm({
                        //             content: '?????????????????????????',
                        //             onOk: () => {
                        //                 let { domain } = obj.props.myPublic;
                        //                 let qrcodeDownUrl = obj.selectedRows[0].qrcodeDownUrl;
                        //                 window.location.href = domain + qrcodeDownUrl;
                        //             }
                        //         });
                        //     }
                        // },
                        {
                            name: 'QRCodeDownload',//??????add del
                            type: 'primary',//??????  ?????? primary  [primary dashed danger]
                            label: '??????',
                            hide: true,
                            disabled: (obj) => {
                                if (obj.btnCallbackFn.getSelectedRows().length === 1) {
                                    return false;
                                } else {
                                    return true;
                                }
                            },
                            onClick: () => {
                                this.setState({ visible: true })
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
                            table: {
                                title: '??????????????????',
                                dataIndex: 'equipNo',
                                key: 'equipNo',
                                width: 200,
                                fixed: 'left',
                                filter: true,
                                fieldsConfig: { type: 'string' },
                            }
                        },
                        {
                            table: {
                                title: '????????????????????????',
                                dataIndex: 'financeNo',
                                key: 'financeNo',
                                onClick: 'detail',
                                filter: true,
                                fieldsConfig: { type: 'string' },
                                width: 160,
                                willExecute: (obj) => {
                                    obj.btnCallbackFn.setActiveKey('0');
                                },
                            }
                        },
                        {
                            table: {
                                title: '????????????',
                                dataIndex: 'equipName',
                                key: 'equipName',
                                width: 150,
                                filter: true,
                                fieldsConfig: { type: 'string' },
                            }
                        },
                        {
                            table: {
                                title: '????????????',
                                dataIndex: 'factory',
                                key: 'factory',
                                width: 150,
                            }
                        },
                        {
                            table: {
                                title: '??????',
                                dataIndex: 'model',
                                key: 'model',
                                width: 150,
                            }
                        },
                        {
                            table: {
                                title: '??????',
                                dataIndex: 'spec',
                                key: 'spec',
                                width: 150,
                            }
                        },
                        {
                            table: {
                                title: '????????????',
                                dataIndex: 'ownerOrgName',
                                key: 'ownerOrgName',
                                width: 150,
                                filter: true,
                                fieldsConfig: { type: 'string' },
                            },
                        },
                        {
                            table: {
                                title: '????????????',
                                dataIndex: 'useOrgName',
                                key: 'useOrgName',
                                width: 150,
                                filter: true,
                                fieldsConfig: { type: 'string' },
                            },
                        },
                        {
                            table: {
                                title: '????????????',
                                dataIndex: 'usePlace',
                                key: 'usePlace',
                                width: 150,
                            },
                        },
                        {
                            table: {
                                title: '????????????',
                                dataIndex: 'regdate',
                                key: 'regdate',
                                width: 150,
                                format: 'YYYY-MM-DD',
                                filter: true,
                                fieldsConfig: {
                                    type: 'rangeDate',
                                    field: 'regdate'
                                },
                            }
                        },
                        {
                            table: {
                                title: '???????????????',
                                dataIndex: 'planNo',
                                key: 'planNo',
                                width: 150,
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
                            }
                        }
                    ]}
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
                                        field: "id",
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
                                        optionConfig: {
                                            label: 'departmentName',
                                            value: 'departmentId',
                                            linkageFields: {
                                                ownerOrgName: 'departmentName'
                                            }
                                        },
                                        fetchConfig: {
                                            apiName: 'getSysCompanyProject',
                                            otherParams: {
                                                departmentId
                                            }
                                        },
                                        allowClear: false,
                                        required: true,
                                        placeholder: '?????????',
                                        span: 8
                                    },
                                    {
                                        label: '????????????',
                                        field: 'useOrgName',
                                        type: 'string',
                                        addDisabled: true,
                                        editDisabled: true,
                                        span: 8
                                    },
                                    {
                                        label: '????????????',
                                        field: 'usePlace',
                                        type: 'string',
                                        optionConfig: {
                                            label: 'itemName',
                                            value: 'itemName',
                                        },
                                        fetchConfig: {
                                            apiName: "getBaseCodeSelect",
                                            otherParams: {
                                                itemId: 'shengfenjianchengdaima'
                                            }
                                        },
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
                            name: "diyComponent",
                            field: "TabTwoList",
                            hide: function (obj) {
                                return (obj.clickCb.rowInfo.name === 'edit' ? true : false)
                            },
                            title: "???????????????",
                            content: obj => {
                                let params = { ...this.props }
                                let rowData = obj.btnCallbackFn.form.getFieldsValue()
                                let paramsData = {}
                                paramsData.id = rowData.id ? rowData.id : ''
                                paramsData.tabIndex = obj.tableFns.getActiveKey()
                                params.paramsData = paramsData
                                return <TabTwoList {...params} />;
                            }
                        },
                        {
                            name: "diyComponent2",
                            field: "TabThreeList",
                            hide: function (obj) {
                                return (obj.clickCb.rowInfo.name === 'edit' ? true : false)
                            },
                            title: "????????????",
                            content: obj => {
                                let params = { ...this.props }
                                let rowData = obj.btnCallbackFn.form.getFieldsValue()
                                let paramsData = {}
                                paramsData.id = rowData.id ? rowData.id : ''
                                paramsData.tabIndex = obj.tableFns.getActiveKey()
                                params.paramsData = paramsData
                                return <TabThreeList {...params} />;
                            }
                        },
                        {
                            name: "diyComponent3",
                            field: "TabFourList",
                            hide: function (obj) {
                                return (obj.clickCb.rowInfo.name === 'edit' ? true : false)
                            },
                            title: "????????????",
                            content: obj => {
                                let params = { ...this.props }
                                let rowData = obj.btnCallbackFn.form.getFieldsValue()
                                let paramsData = {}
                                paramsData.id = rowData.id ? rowData.id : ''
                                paramsData.tabIndex = obj.tableFns.getActiveKey()
                                params.paramsData = paramsData
                                return <TabFourList {...params} />;
                            }
                        },
                        {
                            name: "diyComponent3",
                            field: "TabFiveList",
                            hide: function (obj) {
                                return (obj.clickCb.rowInfo.name === 'edit' ? true : false)
                            },
                            title: "????????????",
                            content: obj => {
                                let params = { ...this.props }
                                let rowData = obj.btnCallbackFn.form.getFieldsValue()
                                let paramsData = {}
                                paramsData.id = rowData.id ? rowData.id : ''
                                paramsData.tabIndex = obj.tableFns.getActiveKey()
                                params.paramsData = paramsData
                                return <TabFiveList {...params} />;
                            }
                        },
                    ]}
                />
                {
                    visible ? <Drawer
                        title={'??????'}
                        placement="right"
                        onClose={this.onClose}
                        visible={visible}
                        width={window.screen.width * 0.75}
                    >
                        <YuanZhi {...this.props} flowData={this.table} />
                    </Drawer> : null
                }
            </div>
        );
    }
}

export default index;