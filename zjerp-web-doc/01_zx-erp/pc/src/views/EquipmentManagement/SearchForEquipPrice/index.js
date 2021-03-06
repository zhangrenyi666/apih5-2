import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import { Modal, Avatar, message as Msg, Drawer } from "antd";
import TabTwoList from './TabTwo';
import TabThreeList from './TabThree';
import TabFourList from './TabFour';
import TabFiveList from './TabFive';
import TabSixList from './TabSix';
import YuanZhi from './YuanZhi'
const { confirm, info } = Modal;
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
            departmentId: ((curCompany?.ext1 === '1' || curCompany?.ext1 === '2') && lockProject?.projectId) ? (lockProject.projectId !== 'all' ? lockProject.projectId : curCompany?.companyId) : curCompany?.projectId,
            visible: false,
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
                            useOrgID: departmentId
                        }
                    }}
                    method={{
                        //????????????
                        disabledForleaveUnused: (obj) => {
                            let data = obj.btnCallbackFn.getSelectedRows();
                            if (data.length === 1 && (data[0].isXianzhi === '' || data[0].isXianzhi === '0')) {
                                return false;
                            } else {
                                return true;
                            }
                        },
                        //????????????
                        saveForleaveUnused: (obj) => {
                            confirm({
                                content: '????????????????',
                                onOk: () => {
                                    this.props.myFetch('unusedZxEqEquip', ...obj.btnCallbackFn.getSelectedRows()).then(
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
                        //????????????
                        disabledForRecover: (obj) => {
                            let data = obj.btnCallbackFn.getSelectedRows();
                            if (data.length === 1 && data[0].isXianzhi === '1') {
                                return false;
                            } else {
                                return true;
                            }
                        },
                        //????????????
                        saveForRecover: (obj) => {
                            confirm({
                                content: '????????????????',
                                onOk: () => {
                                    this.props.myFetch('recoverZxEqEquip', ...obj.btnCallbackFn.getSelectedRows()).then(
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
                        //???????????????
                        disabledForQRCode: (obj) => {
                            let data = obj.btnCallbackFn.getSelectedRows();
                            if (data.length === 1) return false;
                            return true;
                        },
                        //???????????????
                        saveForQRCode: (obj) => {
                            let { domain } = obj.props.myPublic;
                            let qrcodeUrl = obj.btnCallbackFn.getSelectedRows()[0].qrcodeUrl;
                            let qrcodeName = obj.btnCallbackFn.getSelectedRows()[0].qrcodeName;
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
                        saveForQRCodeDownload: (obj) => {
                            confirm({
                                content: '?????????????????????????',
                                onOk: () => {
                                    let { domain } = obj.props.myPublic;
                                    let qrcodeDownUrl = obj.btnCallbackFn.getSelectedRows()[0].qrcodeDownUrl;
                                    window.location.href = domain + qrcodeDownUrl;
                                }
                            });
                        }
                    }}
                    actionBtns={{
                        apiName: "getSysMenuBtn",
                        otherParams: (obj) => {
                            var props = obj.props;
                            let curRouteData = props.routerInfo.routeData[props.routerInfo.curKey];
                            return {
                                menuParentId: curRouteData._value,
                                tableField: "SearchForEquipPrice"
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
                            table: {
                                title: '????????????',
                                dataIndex: 'equipName',
                                key: 'equipName',
                                width: 150,
                                onClick: 'detail',
                                fixed: 'left',
                                filter: true,
                                fieldsConfig: { type: 'string' },
                            }
                        },
                        {
                            table: {
                                title: '??????????????????',
                                dataIndex: 'equipNo',
                                key: 'equipNo',
                                width: 200,
                                filter: true,
                                fieldsConfig: { type: 'string' },
                            }
                        },
                        {
                            table: {
                                title: '??????',
                                dataIndex: 'spec',
                                key: 'spec',
                                width: 120,
                            }
                        },
                        {
                            table: {
                                title: '??????',
                                dataIndex: 'model',
                                key: 'model',
                                width: 120,
                            }
                        },
                        {
                            table: {
                                title: '??????',
                                dataIndex: 'orginalValue',
                                key: 'orginalValue',
                                width: 120,
                                filter: true,
                                fieldsConfig: { type: 'number', range: true, field: 'orginalValueSearch' },

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
                                dataIndex: 'purDate',
                                key: 'purDate',
                                format: 'YYYY-MM-DD',
                                placeholder: '?????????',
                                filter: true,
                                fieldsConfig: {
                                    type: 'rangeDate',
                                    field: 'purDateSearch'
                                }
                            },
                        },
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
                                        label: 'ABCD??????',
                                        field: 'abcType',
                                        addDisabled: true,
                                        editDisabled: true,
                                        type: 'select',
                                        optionConfig: {
                                            label: 'globalCode',
                                            value: 'id',
                                        },
                                        fetchConfig: () => {
                                            if (this.table.getActiveKey() === '0') {
                                                return {
                                                    apiName: "getZxEqGlobalCodeList",
                                                    otherParams: {
                                                        categoryID: "category100203",
                                                        startFlag: '1'
                                                    }
                                                }
                                            }
                                        },
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
                                        label: '????????????',
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
                                        field: 'ownerOrgName',
                                        type: 'string',
                                        optionConfig: {
                                            label: 'departmentName',
                                            value: 'departmentId',
                                            linkageFields: {
                                                ownerOrgName: 'departmentName'
                                            }
                                        },
                                        fetchConfig: () => {
                                            if (this.table.getActiveKey() === '0') {
                                                return {
                                                    apiName: 'getSysCompanyProject',
                                                    otherParams: {
                                                        departmentId: departmentId
                                                    }
                                                }
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
                                        fetchConfig: () => {
                                            if (this.table.getActiveKey() === '0') {
                                                return {
                                                    apiName: "getBaseCodeSelect",
                                                    otherParams: {
                                                        itemId: 'shengfenjianchengdaima'
                                                    }
                                                }
                                            }
                                        },
                                        allowClear: false,
                                        showSearch: true,
                                        placeholder: '?????????',
                                        span: 8
                                    },
                                    {
                                        label: '????????????',
                                        field: 'manageOrgID',
                                        type: 'string',
                                        addDisabled: true,
                                        editDisabled: true,
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
                                        label: '?????????(KW)',
                                        field: 'power',
                                        type: 'number',
                                        min: 0,
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
                                        label: '????????????(KW)',
                                        field: 'vicepower',
                                        type: 'number',
                                        min: 0,
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
                                        label: '????????????(KW)',
                                        field: 'mainpower',
                                        type: 'number',
                                        min: 0,
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
                                        label: '??????fob???',
                                        field: 'fobPrice',
                                        type: 'number',
                                        span: 8
                                    },
                                    {
                                        label: '????????????',
                                        field: 'fobAmount',
                                        type: 'number',
                                        span: 8
                                    },
                                    {
                                        label: '???????????????',
                                        field: 'discountAmount',
                                        type: 'number',
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
                                        label: '???????????????',
                                        field: 'passNo',
                                        type: 'string',
                                        placeholder: '?????????',
                                        span: 8
                                    },
                                    {
                                        label: '??????',
                                        field: 'status',
                                        type: 'string',
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
                                        label: '???????????????',
                                        field: 'outFactorySerial',
                                        type: 'string',
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
                                        label: '??????????????????',
                                        field: 'changeDate',
                                        type: 'date',
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
                            name: "diyComponent4",
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
                        {
                            name: "diyComponent5",
                            field: "TabSixList",
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
                                return <TabSixList {...params} />;
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