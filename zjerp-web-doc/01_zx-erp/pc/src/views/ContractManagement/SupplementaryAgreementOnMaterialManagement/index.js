import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import s from './style.less';
import FlowFormBySupplementaryAgreementOnMaterialManagementt from './form';
import moment from 'moment';
import Operation from './operation';
import DetailForm from './detail';
import FilesForSupplementaryAgreements from '../filesForSupplementaryAgreements';
const config = {
    antd: {
        rowKey: function (row) {
            return row.replenishAgreementId
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
        const { realName } = this.props.loginAndLogoutInfo.loginInfo.userInfo;
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
                        apiName: 'getZxCtSuppliesContrReplenishAgreementListByContrId',
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
                                tableField: "SupplementaryAgreementOnMaterialManagement"
                            }
                        }
                    }}
                    method={{
                        addSubmitFetchConfig: (obj) => {
                            let index = obj.btnCallbackFn.getActiveKey();
                            let rowData = obj.btnCallbackFn.qnnForm.form.getFieldsValue();
                            if (rowData.startDate) {
                                rowData.startDate = moment(rowData.startDate).valueOf();
                            }
                            if (rowData.endDate) {
                                rowData.endDate = moment(rowData.endDate).valueOf();
                            }
                            if (rowData.replyDate) {
                                rowData.replyDate = moment(rowData.replyDate).valueOf();
                            }
                            if (rowData.isDeduct && rowData.isDeduct.length) {
                                rowData.isDeduct = rowData.isDeduct[0];
                            }
                            if (rowData.replenishAgreementId) {
                                if (index === '0') {
                                    return {
                                        apiName: 'updateZxCtSuppliesContrReplenishAgreementByContrId',
                                        otherParams: {
                                            ...rowData
                                        },
                                        success: (objs) => {
                                            if (objs.response.success) {
                                                obj.btnCallbackFn.form.setFieldsValue(objs.response.data);
                                                obj.btnCallbackFn.setActiveKey('1');
                                                this.table.refresh();
                                            }
                                        }
                                    }
                                } else {
                                    if (rowData.zxCtSuppliesContractChangeId) {
                                        return {
                                            apiName: 'updateZxCtSupContrReplLeaseDetailBycontrAlterID',
                                            otherParams: {
                                                ...rowData
                                            },
                                            success: (objs) => {
                                                if (objs.response.success) {
                                                    obj.btnCallbackFn.form.setFieldsValue(objs.response.data);
                                                    obj.btnCallbackFn.setActiveKey('1');
                                                    this.table.refresh();
                                                }
                                            }
                                        }
                                    } else {
                                        return {
                                            apiName: 'addZxCtSupContrReplLeaseDetailBycontrAlterID',
                                            otherParams: {
                                                ...rowData
                                            },
                                            success: (objs) => {
                                                if (objs.response.success) {
                                                    obj.btnCallbackFn.form.setFieldsValue(objs.response.data);
                                                    obj.btnCallbackFn.setActiveKey('1');
                                                    this.table.refresh();
                                                }
                                            }
                                        }
                                    }
                                }
                            } else {
                                return {
                                    apiName: 'addZxCtSuppliesContrReplenishAgreementByContrId',
                                    otherParams: {
                                        ...rowData
                                    },
                                    success: (objs) => {
                                        if (objs.response.success) {
                                            obj.btnCallbackFn.form.setFieldsValue(objs.response.data);
                                            obj.btnCallbackFn.setActiveKey('1');
                                            this.table.refresh();
                                        }
                                    }
                                }
                            }
                        },
                        editDisabled: (obj) => {
                            let data = obj.btnCallbackFn.getSelectedRows();
                            if (data.length === 1 && (!data[0].apih5FlowStatus || data[0].apih5FlowStatus === "-1" || data[0].apih5FlowStatus === "0")) {
                                // && data[0].apih5FlowStatus === "-1" || data[0].apih5FlowStatus === "0"
                                return false;
                            } else {
                                return true;
                            }
                        },
                        editSubmitFetchConfig: (obj) => {
                            let index = obj.btnCallbackFn.getActiveKey();
                            let rowData = obj.btnCallbackFn.qnnForm.form.getFieldsValue();
                            if (rowData.startDate) {
                                rowData.startDate = moment(rowData.startDate).valueOf();
                            }
                            if (rowData.endDate) {
                                rowData.endDate = moment(rowData.endDate).valueOf();
                            }
                            if (rowData.replyDate) {
                                rowData.replyDate = moment(rowData.replyDate).valueOf();
                            }
                            if (rowData.isDeduct && rowData.isDeduct.length) {
                                rowData.isDeduct = rowData.isDeduct[0];
                            }
                            if (index === '0') {
                                return {
                                    apiName: 'updateZxCtSuppliesContrReplenishAgreementByContrId',
                                    otherParams: {
                                        ...rowData
                                    },
                                    success: (objs) => {
                                        if (objs.response.success) {
                                            obj.btnCallbackFn.setActiveKey('1');
                                            this.table.refresh();
                                        }
                                    }
                                }
                            } else {
                                if (rowData.zxCtSuppliesContractChangeId) {
                                    return {
                                        apiName: 'updateZxCtSupContrReplLeaseDetailBycontrAlterID',
                                        otherParams: {
                                            ...rowData
                                        },
                                        success: (objs) => {
                                            if (objs.response.success) {
                                                obj.btnCallbackFn.setActiveKey('1');
                                                this.table.refresh();
                                            }
                                        }
                                    }
                                } else {
                                    return {
                                        apiName: 'addZxCtSupContrReplLeaseDetailBycontrAlterID',
                                        otherParams: {
                                            ...rowData
                                        },
                                        success: (objs) => {
                                            if (objs.response.success) {
                                                obj.btnCallbackFn.setActiveKey('1');
                                                this.table.refresh();
                                            }
                                        }
                                    }
                                }
                            }
                        },
                        ComponentDisabled: (obj) => {
                            let data = obj.btnCallbackFn.getSelectedRows();
                            if (data.length === 1 && data[0].apih5FlowStatus === '-1') {
                                return false;
                            } else {
                                return true;
                            }
                        },
                        Component1Disabled: (obj) => {
                            let data = obj.btnCallbackFn.getSelectedRows();
                            if (data.length === 1 && data[0].apih5FlowStatus !== '-1' && data[0].createTime) {
                                return false;
                            } else {
                                return true;
                            }
                        },
                        disabledFunDetail: (obj) => {
                            let data = obj.btnCallbackFn.getSelectedRows();
                            if (data.length === 1 && (data[0].apih5FlowStatus === '1' || data[0].apih5FlowStatus === '2')) {
                                return false;
                            } else {
                                return true;
                            }
                        },
                        delDisabled: (obj) => {
                            let data = obj.btnCallbackFn.getSelectedRows();
                            if (data.length && !data.filter(item => item.apih5FlowStatus === '1' || item.apih5FlowStatus === '2' || !item.createTime).length) {
                                return false;
                            } else {
                                return true;
                            }
                        },
                    }}
                    componentsKey={{
                        FlowFormBySupplementaryAgreementOnMaterialManagementt: (props) => {
                            let flowData = props?.btnCallbackFn?.getSelectedRows?.()?.[0];
                            return <FlowFormBySupplementaryAgreementOnMaterialManagementt {...this.props} btnCallbackFn={props.qnnTableInstance} isInQnnTable={true} flowData={{ ...flowData }} />;
                        },
                        Operation: (props) => {
                            return <Operation apiName={'openFlowByReport'} {...props} />
                        },
                        DetailForm: (obj) => {
                            return <DetailForm
                                {...this.props}
                                flowData={obj.qnnTableInstance.getSelectedRows()[0]}
                                isInQnnTable={true}
                            />
                        }
                    }}
                    // drawerShowToggle={(obj) => {
                    //     let { drawerIsShow } = obj;
                    //     if (!drawerIsShow) {
                    //         obj.btnCallbackFn.refresh();
                    //         obj.btnCallbackFn.clearSelectedRows();
                    //     }
                    // }}
                    tabs={[
                        {
                            field: "form1",
                            name: "qnnForm",
                            title: "????????????",
                            content: {
                                formConfig: [
                                    {
                                        field: 'replenishAgreementId',
                                        type: 'string',
                                        hide: true,
                                    },
                                    {
                                        field: 'workId',
                                        type: 'string',
                                        hide: true,
                                    },
                                    {
                                        field: 'apih5FlowStatus',
                                        type: 'string',
                                        initialValue: '-1',
                                        hide: true,
                                    },
                                    {
                                        field: 'comID',
                                        type: 'string',
                                        hide: true,
                                    },
                                    {
                                        field: 'comName',
                                        type: 'string',
                                        hide: true,
                                    },
                                    {
                                        field: 'orgID',
                                        type: 'string',
                                        hide: true,
                                    },
                                    {
                                        field: 'orgName',
                                        type: 'string',
                                        hide: true,
                                    },
                                    {
                                        field: 'contractName',
                                        type: 'string',
                                        hide: true,
                                    },
                                    {
                                        field: 'beginPer',
                                        type: 'string',
                                        initialValue: realName,
                                        hide: true,
                                    },
                                    {
                                        label: '??????????????????',
                                        field: "contractNo",
                                        type: 'string',
                                        disabled: true,
                                        required:true,
                                        placeholder: '?????????',
                                        span: 12
                                    },
                                    {
                                        label: '??????????????????',
                                        field: "pp3",
                                        type: 'string',
                                        required: true,
                                        placeholder: '?????????',
                                        span: 12
                                    },
                                    {
                                        label: '????????????',
                                        field: "pp6",
                                        type: 'selectByQnnTable',
                                        required: true,
                                        optionConfig: {
                                            label: 'contractName',
                                            value: 'zxCtSuppliesContractId',
                                            linkageFields: {
                                                comID: 'comID',
                                                comName: 'comName',
                                                orgID: 'orgID',
                                                orgName: "orgName",
                                                contractName: 'contractName',
                                                contractNo: 'agreementNum',
                                                contractType: 'contractType',
                                                firstID: 'firstId',
                                                firstName: "firstName",
                                                secondID: 'secondID',
                                                secondName: "secondName",
                                                contractCost: 'contractCost',
                                                contractCostNoTax: 'contractCostNoTax',
                                                contractCostTax: 'contractCostTax',
                                                pp4: 'pp4',
                                                pp4NoTax: 'pp4NoTax',
                                                pp4Tax: 'pp4Tax',
                                                alterContractSum: 'alterContractSum',
                                                alterContractSumNoTax: 'alterContractSumNoTax',
                                                alterContractSumTax: 'alterContractSumTax',
                                                isDeduct: 'isDeduct',
                                                code7: 'code7',
                                                materialSource: 'materialSource',
                                                shopWay: 'shopWay',
                                                shopNumber: 'shopNumber'
                                            }
                                        },
                                        dropdownMatchSelectWidth: 600,
                                        qnnTableConfig: {
                                            antd: {
                                                rowKey: "zxCtSuppliesContractId"
                                            },
                                            firstRowIsSearch: false,
                                            fetchConfig: {
                                                apiName: 'getZxCtSuppliesContractListAddAgreementNum',
                                                otherParams: {
                                                    pp9: '1',
                                                    orgID: departmentId
                                                },
                                                searchKey: 'contractName'
                                            },
                                            searchBtnsStyle: "inline",
                                            formConfig: [
                                                {
                                                    isInForm: false,
                                                    isInSearch: true,
                                                    table: {
                                                        dataIndex: "contractNo",
                                                        title: "????????????",
                                                    },
                                                    form: {
                                                        type: "string"
                                                    }
                                                },
                                                {
                                                    isInForm: false,
                                                    isInSearch: true,
                                                    table: {
                                                        dataIndex: "contractName",
                                                        title: "????????????",
                                                    },
                                                    form: {
                                                        type: "string"
                                                    }
                                                }
                                            ]
                                        },
                                        condition: [
                                            {
                                                regex: { replenishAgreementId: ['!', /(''|null|undefined)/ig] },
                                                action: 'disabled',
                                            }
                                        ],
                                        placeholder: '?????????',
                                        span: 12
                                    },
                                    {
                                        label: '???????????????',
                                        field: "agent",
                                        type: 'string',
                                        placeholder: '?????????',
                                        span: 12
                                    },
                                    {
                                        label: '????????????',
                                        field: "startDate",
                                        type: 'date',
                                        placeholder: '?????????',
                                        span: 12
                                    },
                                    {
                                        label: '????????????',
                                        field: "endDate",
                                        type: 'date',
                                        placeholder: '?????????',
                                        span: 12
                                    },
                                    {
                                        label: '????????????',
                                        field: "csTimeLimit",
                                        type: 'string',
                                        placeholder: '?????????',
                                        span: 12
                                    },
                                    {
                                        label: '????????????',
                                        field: "contractType",
                                        type: 'string',
                                        disabled: true,
                                        initialValue: '???????????????????????????',
                                        placeholder: '?????????',
                                        span: 12
                                    },
                                    {
                                        label: '????????????',
                                        field: 'firstName',
                                        type: 'string',
                                        disabled: true,
                                        span: 12
                                    },
                                    {
                                        field: 'firstID',
                                        type: 'string',
                                        hide: true,
                                    },
                                    {
                                        label: '????????????',
                                        field: 'secondName',
                                        type: 'string',
                                        disabled: true,
                                        span: 12
                                    },
                                    {
                                        label: '????????????',
                                        field: "secondID",
                                        type: 'string',
                                        hide: true
                                    },
                                    {
                                        label: '??????????????????(??????)',
                                        field: "contractCost",
                                        type: 'number',
                                        disabled: true,
                                        initialValue: 0,
                                        precision: 6,
                                        placeholder: '?????????',
                                        span: 12
                                    },
                                    {
                                        label: '?????????????????????(??????)',
                                        field: "contractCostNoTax",
                                        type: 'number',
                                        disabled: true,
                                        initialValue: 0,
                                        precision: 6,
                                        placeholder: '?????????',
                                        span: 12
                                    },
                                    {
                                        label: '????????????(??????)',
                                        field: "contractCostTax",
                                        type: 'number',
                                        disabled: true,
                                        initialValue: 0,
                                        precision: 6,
                                        placeholder: '?????????',
                                        span: 12
                                    },
                                    {
                                        label: '????????????????????????(??????)',
                                        field: "pp4",
                                        type: 'string',
                                        disabled: true,
                                        initialValue: "0",
                                        precision: 6,
                                        placeholder: '?????????',
                                        span: 12
                                    },
                                    {
                                        label: '???????????????????????????(??????)',
                                        field: "pp4NoTax",
                                        type: 'number',
                                        disabled: true,
                                        initialValue: 0,
                                        precision: 6,
                                        placeholder: '?????????',
                                        span: 12
                                    },
                                    {
                                        label: '??????????????????(??????)',
                                        field: "pp4Tax",
                                        type: 'number',
                                        disabled: true,
                                        initialValue: 0,
                                        precision: 6,
                                        placeholder: '?????????',
                                        span: 12
                                    },
                                    {
                                        label: '?????????????????????(??????)',
                                        field: "alterContractSum",
                                        type: 'number',
                                        disabled: true,
                                        initialValue: 0,
                                        precision: 6,
                                        placeholder: '?????????',
                                        span: 12
                                    },
                                    {
                                        label: '????????????????????????(??????)',
                                        field: "alterContractSumNoTax",
                                        type: 'number',
                                        disabled: true,
                                        initialValue: 0,
                                        precision: 6,
                                        placeholder: '?????????',
                                        span: 12
                                    },
                                    {
                                        label: '???????????????(??????)',
                                        field: "alterContractSumTax",
                                        type: 'number',
                                        disabled: true,
                                        initialValue: 0,
                                        precision: 6,
                                        placeholder: '?????????',
                                        span: 12
                                    },
                                    {
                                        label: '????????????',
                                        field: "isDeduct",
                                        type: 'checkbox',
                                        optionData: [
                                            {
                                                value: '1'
                                            }
                                        ],
                                        disabled: true,
                                        span: 12
                                    },
                                    {
                                        label: '????????????',
                                        field: "code7",
                                        type: 'select',
                                        optionConfig: {
                                            label: 'itemName', //?????? label
                                            value: 'itemId'
                                        },
                                        fetchConfig: {
                                            apiName: 'getBaseCodeSelect',
                                            otherParams: {
                                                itemId: 'category_contr_type_CL'
                                            }
                                        },
                                        placeholder: '?????????',
                                        disabled: true,
                                        span: 12
                                    },
                                    {
                                        label: '????????????',
                                        field: "materialSource",
                                        type: 'select',
                                        optionConfig: {
                                            label: 'itemName', //?????? label
                                            value: 'itemId'
                                        },
                                        fetchConfig: {
                                            apiName: 'getBaseCodeSelect',
                                            otherParams: {
                                                itemId: 'category_contract_materialSource'
                                            }
                                        },
                                        disabled: true,
                                        placeholder: '?????????',
                                        span: 12
                                    },
                                    // {
                                    //     label: '????????????',
                                    //     field: "shopWay",
                                    //     type: 'select',
                                    //     optionConfig: {
                                    //         label: 'label', //?????? label
                                    //         value: 'value'
                                    //     },
                                    //     optionData: [
                                    //         {
                                    //             label: '????????????',
                                    //             value: '0'
                                    //         },
                                    //         {
                                    //             label: '???????????????',
                                    //             value: '1'
                                    //         },
                                    //         {
                                    //             label: '????????????',
                                    //             value: '2'
                                    //         }
                                    //     ],
                                    //     placeholder: '?????????',
                                    //     disabled: true,
                                    //     span: 12
                                    // },
                                    // {
                                    //     label: '????????????',
                                    //     field: "shopNumber",
                                    //     type: 'string',
                                    //     dependencies: ["shopWay"],
                                    //     hide: (obj) => {
                                    //         if (obj.form.getFieldValue('shopWay') !== '0' && obj.form.getFieldValue('shopWay') !== '1') {
                                    //             return true;
                                    //         } else {
                                    //             return false;
                                    //         }
                                    //     },
                                    //     disabled: true,
                                    //     placeholder: '?????????',
                                    //     span: 12
                                    // },
                                    {
                                        label: '????????????',
                                        field: 'content',
                                        type: 'textarea',
                                        placeholder: '?????????',
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
                                    },
                                    {
                                        label: '??????',
                                        field: 'remarks',
                                        type: 'textarea',
                                        placeholder: '?????????',
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
                                ]
                            }
                        },
                        {
                            field: "form2",
                            name: "qnnForm",
                            title: "??????",
                            disabled: (obj) => {
                                return ((obj.clickCb.rowInfo.name === "edit" || obj.clickCb.rowInfo.name === "detail") ? false : !obj.btnCallbackFn.form.getFieldValue("replenishAgreementId"))
                            },
                            content: {
                                formConfig: [
                                    {
                                        field: 'zxCtSuppliesContractChangeId',
                                        type: 'string',
                                        hide: true,
                                    },
                                    {
                                        field: 'tableOneID',
                                        type: 'string',
                                        hide: true,
                                    },
                                    {
                                        label: '??????????????????',
                                        field: "contractNo",
                                        type: 'string',
                                        disabled: true,
                                        placeholder: '?????????',
                                        span: 12
                                    },
                                    {
                                        label: '????????????',
                                        field: "replyUnit",
                                        type: 'select',
                                        optionConfig: {
                                            label: 'label',
                                            value: 'value'
                                        },
                                        optionData: [
                                            {
                                                label: '??????',
                                                value: '0'
                                            },
                                            {
                                                label: '??????',
                                                value: '1'
                                            },
                                            {
                                                label: '???',
                                                value: '2'
                                            }
                                        ],
                                        placeholder: '?????????',
                                        span: 12
                                    },
                                    {
                                        label: '????????????',
                                        field: "replyDate",
                                        type: 'date',
                                        placeholder: '?????????',
                                        span: 12
                                    },
                                    {
                                        label: '????????????',
                                        field: 'alterContent',
                                        type: 'string',
                                        required: true,
                                        placeholder: '?????????',
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
                                    },
                                    {
                                        label: '????????????',
                                        field: 'alterReason',
                                        type: 'string',
                                        placeholder: '?????????',
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
                                    },
                                    // {
                                    //     label: '??????????????????(??????)',
                                    //     field: "contractCost",
                                    //     type: 'number',
                                    //     disabled: true,
                                    //     precision: 6,
                                    //     placeholder: '?????????',
                                    //     span: 12
                                    // },
                                    // {
                                    //     label: '?????????????????????(??????)',
                                    //     field: "contractCostNoTax",
                                    //     type: 'number',
                                    //     disabled: true,
                                    //     precision: 6,
                                    //     placeholder: '?????????',
                                    //     span: 12
                                    // },
                                    // {
                                    //     label: '????????????(??????)',
                                    //     field: "contractCostTax",
                                    //     type: 'number',
                                    //     disabled: true,
                                    //     precision: 6,
                                    //     placeholder: '?????????',
                                    //     span: 12
                                    // },
                                    // {
                                    //     label: '????????????????????????(??????)',
                                    //     field: "pp4",
                                    //     type: 'string',
                                    //     disabled: true,
                                    //     precision: 6,
                                    //     placeholder: '?????????',
                                    //     span: 12
                                    // },
                                    // {
                                    //     label: '???????????????????????????(??????)',
                                    //     field: "pp4NoTax",
                                    //     type: 'number',
                                    //     disabled: true,
                                    //     precision: 6,
                                    //     placeholder: '?????????',
                                    //     span: 12
                                    // },
                                    // {
                                    //     label: '??????????????????(??????)',
                                    //     field: "pp4Tax",
                                    //     type: 'number',
                                    //     disabled: true,
                                    //     precision: 6,
                                    //     placeholder: '?????????',
                                    //     span: 12
                                    // },
                                    // {
                                    //     label: '?????????????????????(??????)',
                                    //     field: "alterContractSum",
                                    //     type: 'number',
                                    //     disabled: true,
                                    //     precision: 6,
                                    //     placeholder: '?????????',
                                    //     span: 12
                                    // },
                                    // {
                                    //     label: '????????????????????????(??????)',
                                    //     field: "alterContractSumNoTax",
                                    //     type: 'number',
                                    //     disabled: true,
                                    //     precision: 6,
                                    //     placeholder: '?????????',
                                    //     span: 12
                                    // },
                                    // {
                                    //     label: '???????????????(??????)',
                                    //     field: "alterContractSumTax",
                                    //     type: 'number',
                                    //     disabled: true,
                                    //     precision: 6,
                                    //     placeholder: '?????????',
                                    //     span: 12
                                    // },
                                    {
                                        type: "qnnTable",
                                        field: "replenishShopDetailedList",
                                        incToForm: true,
                                        dependencies: ["code7"],
                                        hide: (obj) => {
                                            let rowData = obj?.tableFns?.qnnForm?.form?.getFieldsValue?.();
                                            if (rowData && rowData.code7 !== 'WL') {
                                                return false;
                                            } else {
                                                return true;
                                            }
                                        },
                                        qnnTableConfig: {
                                            antd: {
                                                size: "small",
                                                rowKey: 'zxCtSuppliesContrReplenishShopDetailId'
                                            },
                                            paginationConfig: false,
                                            isShowRowSelect: true,
                                            actionBtns: [
                                                {
                                                    name: 'addRow',
                                                    icon: 'plus',
                                                    type: 'primary',
                                                    label: '??????',
                                                    hide: (obj) => {
                                                        if (obj.props.qnnFormProps.funcCallBackParams.clickCb.rowInfo.name === 'detail') {
                                                            return true;
                                                        } else {
                                                            return false;
                                                        }
                                                    },
                                                    addRowDefaultData: () => {
                                                        let rowData = this.table.form.getFieldsValue();
                                                        return {
                                                            pp5: rowData.pp6,
                                                            isDeduct: rowData.isDeduct && rowData.isDeduct.length ? rowData.isDeduct[0] : '0'
                                                        }
                                                    }
                                                },
                                                {
                                                    name: 'del',
                                                    icon: 'del',
                                                    type: 'danger',
                                                    label: '??????',
                                                    hide: (obj) => {
                                                        if (obj.props.qnnFormProps.funcCallBackParams.clickCb.rowInfo.name === 'detail') {
                                                            return true;
                                                        } else {
                                                            return false;
                                                        }
                                                    }
                                                }
                                            ],
                                            wrappedComponentRef: (me) => {
                                                this.tableOne = me;
                                            },
                                            formConfig: [
                                                {
                                                    isInTable: false,
                                                    form: {
                                                        field: 'contrItemID',
                                                        type: 'string',
                                                        hide: true,
                                                    }
                                                },
                                                {
                                                    isInTable: false,
                                                    form: {
                                                        field: 'zxCtSuppliesContrReplenishShopDetailId',
                                                        type: 'string',
                                                        hide: true,
                                                    }
                                                },
                                                {
                                                    isInTable: false,
                                                    form: {
                                                        field: 'coBookID',
                                                        type: 'string',
                                                        hide: true,
                                                    }
                                                },
                                                {
                                                    isInTable: false,
                                                    form: {
                                                        field: 'pp5',
                                                        type: 'string',
                                                        hide: true,
                                                    }
                                                },
                                                {
                                                    isInTable: false,
                                                    form: {
                                                        field: 'workID',
                                                        type: 'string',
                                                        hide: true,
                                                    }
                                                },
                                                {
                                                    isInTable: false,
                                                    form: {
                                                        field: 'workType',
                                                        type: 'string',
                                                        hide: true,
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: `<div>????????????<span style='color: #ff4d4f'>*</span></div>`,
                                                        dataIndex: 'alterType',
                                                        key: 'alterType',
                                                        width: 100,
                                                        type: 'select',
                                                        tdEdit: true,
                                                        fixed: 'left'
                                                    },
                                                    form: {
                                                        type: 'select',
                                                        optionConfig: {
                                                            label: 'label', //?????? label
                                                            value: 'value'
                                                        },
                                                        optionData: [
                                                            {
                                                                label: '??????',
                                                                value: '1'
                                                            },
                                                            {
                                                                label: '??????',
                                                                value: '2'
                                                            }
                                                        ],
                                                        onChange: (val, obj, btnCallbackFn) => {
                                                            btnCallbackFn.getEditedRowData(false).then((editRowData) => {
                                                                editRowData.alterType = val;
                                                                editRowData.workTypeID = null;
                                                                editRowData.workType = null;
                                                                editRowData.workID = null;
                                                                editRowData.workName = null;
                                                                editRowData.workNo = null;
                                                                editRowData.spec = null;
                                                                editRowData.unit = null;
                                                                editRowData.isNetPrice = [];
                                                                editRowData.qty = null;
                                                                editRowData.price = null;
                                                                editRowData.taxRate = null;
                                                                editRowData.contractSum = null;
                                                                editRowData.priceNoTax = null;
                                                                editRowData.contractSumNoTax = null;
                                                                editRowData.contractSumTax = null;
                                                                editRowData.changeQty = null;
                                                                editRowData.changePrice = null;
                                                                editRowData.changePriceNoTax = null;
                                                                editRowData.changeContractSum = null;
                                                                editRowData.changeContractSumNoTax = null;
                                                                editRowData.changeContractSumTax = null;
                                                                btnCallbackFn.setEditedRowData({ ...editRowData });
                                                                editRowData.isNetPrice = '0';
                                                                btnCallbackFn.setEditedRowData({ ...editRowData });
                                                            })
                                                        },
                                                        required: true,
                                                        placeholder: '?????????'
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: `<div>????????????<span style='color: #ff4d4f'>*</span></div>`,
                                                        dataIndex: 'workTypeID',
                                                        key: 'workTypeID',
                                                        width: 150,
                                                        tdEdit: (obj) => { return obj?.alterType === '1' ? true : false },
                                                        fixed: 'left',
                                                        type: 'select',
                                                    },
                                                    form: {
                                                        type: 'select',
                                                        dependencies: ['alterType'],
                                                        dependenciesReRender: true,
                                                        optionConfig: {
                                                            label: 'catName', //?????? label
                                                            value: 'id'
                                                        },
                                                        fetchConfig: {
                                                            apiName: 'getZxSkResCategoryMaterialsList',
                                                            otherParams: {
                                                                parentID: '0002'
                                                            }
                                                        },
                                                        onChange: (val, obj, btnCallbackFn) => {
                                                            btnCallbackFn.getEditedRowData(false).then((editRowData) => {
                                                                editRowData.workTypeID = obj.itemData.id;
                                                                editRowData.workType = obj.itemData.catName;
                                                                editRowData.workID = null;
                                                                editRowData.workName = null;
                                                                editRowData.workNo = null;
                                                                editRowData.spec = null;
                                                                editRowData.unit = null;
                                                                editRowData.qty = null;
                                                                editRowData.price = null;
                                                                editRowData.taxRate = null;
                                                                editRowData.contractSum = null;
                                                                editRowData.priceNoTax = null;
                                                                editRowData.contractSumNoTax = null;
                                                                editRowData.contractSumTax = null;
                                                                editRowData.changeQty = null;
                                                                editRowData.changePrice = null;
                                                                editRowData.changePriceNoTax = null;
                                                                editRowData.changeContractSum = null;
                                                                editRowData.changeContractSumNoTax = null;
                                                                editRowData.changeContractSumTax = null;
                                                                btnCallbackFn.setEditedRowData({ ...editRowData });
                                                            })
                                                        },
                                                        allowClear: false,
                                                        showSearch: true,
                                                        required: true,
                                                        placeholder: '?????????'
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: `<div>????????????<span style='color: #ff4d4f'>*</span></div>`,
                                                        dataIndex: 'workNo',
                                                        key: 'workNo',
                                                        width: 150,
                                                        tdEdit: (obj) => { return obj?.alterType ? true : false },
                                                        fixed: 'left'
                                                    },
                                                    form: {
                                                        type: 'selectByQnnTable',
                                                        dependencies: ['alterType', 'workTypeID'],
                                                        dependenciesReRender: true,
                                                        optionConfig: (obj) => {
                                                            if (obj.rowData.alterType === '1') {
                                                                return {
                                                                    label: 'resCode', //?????? label
                                                                    value: 'resCode'
                                                                }
                                                            } else if (obj.rowData.alterType === '2') {
                                                                return {
                                                                    label: 'workNo', //?????? label
                                                                    value: 'workNo'
                                                                }
                                                            }
                                                        },
                                                        dropdownMatchSelectWidth: 600,
                                                        qnnTableConfig: {
                                                            antd: {
                                                                rowKey: "id"
                                                            },
                                                            fetchConfig: (obj) => {
                                                                if (obj.rowData.alterType === '1') {
                                                                    if (obj.rowData.workTypeID) {
                                                                        return {
                                                                            apiName: 'getZxSkResourceMaterialsListNameJoinResource',
                                                                            otherParams: {
                                                                                id: obj.rowData.workTypeID
                                                                            }
                                                                        }
                                                                    }else{
                                                                        return {};
                                                                    }
                                                                } else{
                                                                    return {
                                                                        apiName: 'getZxCtSuppliesContrShopListList',
                                                                        // apiName: 'getZxCtSuppliesContrApplyLeaseListList',
                                                                        otherParams: {
                                                                            contractID: this.table.form.getFieldValue('pp6')
                                                                        }
                                                                    }
                                                                }
                                                            },
                                                            searchBtnsStyle: "inline",
                                                            formConfig: [
                                                                {
                                                                    isInTable:(obj) => {
                                                                        if(obj?.props?.rowData?.alterType === '1'){
                                                                            return true;
                                                                        }
                                                                        return false;
                                                                    },
                                                                    table: {
                                                                        dataIndex: "resCode",
                                                                        title: "??????",
                                                                    },
                                                                    form: {
                                                                        type: "string"
                                                                    }
                                                                },
                                                                {
                                                                    isInTable:(obj) => {
                                                                        if(obj?.props?.rowData?.alterType === '1'){
                                                                            return true;
                                                                        }
                                                                        return false;
                                                                    },
                                                                    table: {
                                                                        dataIndex: "resName",
                                                                        title: "??????",
                                                                    },
                                                                    form: {
                                                                        type: "string"
                                                                    }
                                                                },
                                                                {
                                                                    isInTable:(obj) => {
                                                                        if(obj?.props?.rowData?.alterType === '2'){
                                                                            return true;
                                                                        }
                                                                        return false;
                                                                    },
                                                                    table: {
                                                                        dataIndex: "workNo",
                                                                        title: "????????????",
                                                                    },
                                                                    form: {
                                                                        type: "string"
                                                                    }
                                                                },
                                                                {
                                                                    isInTable:(obj) => {
                                                                        if(obj?.props?.rowData?.alterType === '2'){
                                                                            return true;
                                                                        }
                                                                        return false;
                                                                    },
                                                                    table: {
                                                                        dataIndex: "workName",
                                                                        title: "????????????",
                                                                    },
                                                                    form: {
                                                                        type: "string"
                                                                    }
                                                                },
                                                                {
                                                                    table: {
                                                                        dataIndex: "spec",
                                                                        title: "????????????",
                                                                    },
                                                                    form: {
                                                                        type: "string"
                                                                    }
                                                                },
                                                                {
                                                                    table: {
                                                                        dataIndex: "unit",
                                                                        title: "??????",
                                                                    },
                                                                    form: {
                                                                        type: "string"
                                                                    }
                                                                },
                                                                {
                                                                    isInTable:(obj) => {
                                                                        if(obj?.props?.rowData?.alterType === '2'){
                                                                            return true;
                                                                        }
                                                                        return false;
                                                                    },
                                                                    table: {
                                                                        dataIndex: "changePrice",
                                                                        title: "????????????",
                                                                        render:(data,rowData) => {
                                                                            if(data){
                                                                                return data;
                                                                            }else{
                                                                                return rowData?.price
                                                                            }
                                                                        }
                                                                    },
                                                                    form: {
                                                                        type: "string"
                                                                    }
                                                                },
                                                                {
                                                                    isInTable:(obj) => {
                                                                        if(obj?.props?.rowData?.alterType === '2'){
                                                                            return true;
                                                                        }
                                                                        return false;
                                                                    },
                                                                    table: {
                                                                        dataIndex: "changeQty",
                                                                        title: "????????????",
                                                                        render:(data,rowData) => {
                                                                            if(data){
                                                                                return data;
                                                                            }else{
                                                                                return rowData?.qty
                                                                            }
                                                                        }
                                                                    },
                                                                    form: {
                                                                        type: "string"
                                                                    }
                                                                },
                                                            ]
                                                        },
                                                        onChange: (val, obj, btnCallbackFn) => {
                                                            btnCallbackFn.getEditedRowData(false).then((editRowData) => {
                                                                editRowData.editFlag = null;
                                                                if (editRowData.alterType === '1') {
                                                                    editRowData.workID = obj.itemData.id;
                                                                    editRowData.workName = obj.itemData.resName;
                                                                    editRowData.workNo = obj.itemData.resCode;
                                                                    editRowData.spec = obj.itemData.spec;
                                                                    editRowData.unit = obj.itemData.unit;
                                                                    editRowData.qty = null;
                                                                    editRowData.price = null;
                                                                    editRowData.taxRate = null;
                                                                    editRowData.contractSum = null;
                                                                    editRowData.priceNoTax = null;
                                                                    editRowData.contractSumNoTax = null;
                                                                    editRowData.contractSumTax = null;
                                                                    editRowData.changeQty = null;
                                                                    editRowData.changePrice = null;
                                                                    editRowData.changePriceNoTax = 0;
                                                                    editRowData.changeContractSum = 0;
                                                                    editRowData.changeContractSumNoTax = 0;
                                                                    editRowData.changeContractSumTax = 0;
                                                                } else {
                                                                    editRowData.workID = obj.itemData.workID;
                                                                    editRowData.coBookID = obj.itemData.zxCtSuppliesContrShopListId;
                                                                    editRowData.workTypeID = obj.itemData.workTypeID;
                                                                    editRowData.workType = obj.itemData.workType;
                                                                    editRowData.workName = obj.itemData.workName;
                                                                    editRowData.workNo = obj.itemData.workNo;
                                                                    editRowData.spec = obj.itemData.spec;
                                                                    editRowData.unit = obj.itemData.unit;
                                                                    editRowData.isNetPrice = obj.itemData.isNetPrice;
                                                                    editRowData.qty = obj.itemData.qty;
                                                                    editRowData.price = obj.itemData.price;
                                                                    editRowData.taxRate = obj.itemData.taxRate;
                                                                    editRowData.contractSum = obj.itemData.contractSum;
                                                                    editRowData.priceNoTax = obj.itemData.priceNoTax;
                                                                    editRowData.contractSumNoTax = obj.itemData.contractSumNoTax;
                                                                    editRowData.contractSumTax = obj.itemData.contractSumTax;
                                                                    editRowData.settledQty = obj.itemData.settledQty;
                                                                    editRowData.contrItemID = obj.itemData.zxCtSuppliesContrShopListId;
                                                                    if (obj.itemData.changeContractSum || obj.itemData.changeContractSum === 0) {
                                                                        editRowData.changeContractSum = obj.itemData.changeContractSum;
                                                                        editRowData.changeQty = obj.itemData.changeQty;
                                                                        editRowData.changePrice = obj.itemData.changePrice;
                                                                        editRowData.changePriceNoTax = obj.itemData.changePriceNoTax;
                                                                        editRowData.changeContractSumNoTax = obj.itemData.changeContractSumNoTax;
                                                                        editRowData.changeContractSumTax = obj.itemData.changeContractSumTax;
                                                                    } else {
                                                                        editRowData.changeContractSum = obj.itemData.contractSum;
                                                                        editRowData.changeQty = obj.itemData.qty;
                                                                        editRowData.changePrice = obj.itemData.price;
                                                                        editRowData.changePriceNoTax = obj.itemData.priceNoTax;
                                                                        editRowData.changeContractSumNoTax = obj.itemData.contractSumNoTax;
                                                                        editRowData.changeContractSumTax = obj.itemData.contractSumTax;
                                                                    }
                                                                }
                                                                btnCallbackFn.setEditedRowData({ ...editRowData });
                                                            })
                                                        },
                                                        allowClear: false,
                                                        showSearch: true,
                                                        required: true,
                                                        placeholder: '?????????'
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '????????????',
                                                        dataIndex: 'workName',
                                                        key: 'workName',
                                                        width: 100
                                                    },
                                                    form: {
                                                        type: 'string',
                                                        placeholder: '?????????'
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
                                                        placeholder: '?????????'
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '??????',
                                                        dataIndex: 'unit',
                                                        key: 'unit',
                                                        width: 100
                                                    },
                                                    form: {
                                                        type: 'string',
                                                        placeholder: '?????????'
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '????????????',
                                                        dataIndex: 'isNetPrice',
                                                        key: 'isNetPrice',
                                                        width: 100,
                                                        tdEdit: (obj) => { return obj?.alterType === '1' ? true : false },
                                                        render: (data) => {
                                                            if (data === '1') {
                                                                return '???';
                                                            } else {
                                                                return '???';
                                                            }
                                                        }
                                                    },
                                                    form: {
                                                        type: 'checkbox',
                                                        optionData: [
                                                            {
                                                                value: "1"
                                                            }
                                                        ]
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '??????',
                                                        dataIndex: 'qty',
                                                        key: 'qty',
                                                        width: 100,
                                                        render: (data, rowData) => {
                                                            if (rowData.qty || rowData.qty === 0) {
                                                                return rowData.qty.toFixed(3)
                                                            }
                                                        }
                                                    },
                                                    form: {
                                                        type: 'number',
                                                        precision: 3,
                                                        placeholder: '?????????'
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '??????????????????',
                                                        dataIndex: 'price',
                                                        key: 'price',
                                                        width: 120,
                                                        render: (data, rowData) => {
                                                            if (rowData.price || rowData.price === 0) {
                                                                return rowData.price.toFixed(6)
                                                            }
                                                        }
                                                    },
                                                    form: {
                                                        type: 'number',
                                                        precision: 6,
                                                        placeholder: '?????????'
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '??????????????????',
                                                        dataIndex: 'contractSum',
                                                        key: 'contractSum',
                                                        width: 120,
                                                        render: (data, rowData) => {
                                                            if (rowData.contractSum || rowData.contractSum === 0) {
                                                                return rowData.contractSum.toFixed(2)
                                                            }
                                                        }
                                                    },
                                                    form: {
                                                        type: 'number',
                                                        precision: 2,
                                                        placeholder: '?????????'
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '?????????????????????',
                                                        dataIndex: 'priceNoTax',
                                                        key: 'priceNoTax',
                                                        width: 130,
                                                        render: (data, rowData) => {
                                                            if (rowData.priceNoTax || rowData.priceNoTax === 0) {
                                                                return rowData.priceNoTax.toFixed(6)
                                                            }
                                                        }
                                                    },
                                                    form: {
                                                        type: 'number',
                                                        precision: 6,
                                                        placeholder: '?????????'
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '?????????????????????',
                                                        dataIndex: 'contractSumNoTax',
                                                        key: 'contractSumNoTax',
                                                        width: 130,
                                                        render: (data, rowData) => {
                                                            if (rowData.contractSumNoTax || rowData.contractSumNoTax === 0) {
                                                                return rowData.contractSumNoTax.toFixed(2)
                                                            }
                                                        }
                                                    },
                                                    form: {
                                                        type: 'number',
                                                        precision: 2,
                                                        placeholder: '?????????'
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '??????',
                                                        dataIndex: 'contractSumTax',
                                                        key: 'contractSumTax',
                                                        width: 100,
                                                        render: (data, rowData) => {
                                                            if (rowData.contractSumTax || rowData.contractSumTax === 0) {
                                                                return rowData.contractSumTax.toFixed(2)
                                                            }
                                                        }
                                                    },
                                                    form: {
                                                        type: 'number',
                                                        precision: 2,
                                                        placeholder: '?????????'
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '??????(%)',
                                                        dataIndex: 'taxRate',
                                                        key: 'taxRate',
                                                        width: 100,
                                                        tdEdit: (obj) => { return obj?.alterType === '1' ? true : false },
                                                        type: 'select'
                                                    },
                                                    form: {
                                                        type: 'select',
                                                        optionConfig: {
                                                            label: 'itemName', //?????? label
                                                            value: 'itemId'
                                                        },
                                                        fetchConfig: {
                                                            apiName: 'getBaseCodeSelect',
                                                            otherParams: {
                                                                itemId: 'shuiLV'
                                                            }
                                                        },
                                                        onChange: (val, obj, btnCallbackFn) => {
                                                            btnCallbackFn.getEditedRowData(false).then((editRowData) => {
                                                                editRowData.taxRate = val;
                                                                editRowData.changePriceNoTax = (editRowData.changePrice ? editRowData.changePrice : 0) / ((val ? (1 + Number(val) / 100) : 1));
                                                                editRowData.changeContractSum = (editRowData.changeQty ? editRowData.changeQty : 0) * (editRowData.changePrice ? editRowData.changePrice : 0);
                                                                editRowData.changeContractSumNoTax = ((editRowData.changeQty ? editRowData.changeQty : 0) * (editRowData.changePrice ? editRowData.changePrice : 0)) / (val ? (1 + Number(val) / 100) : 1);
                                                                editRowData.changeContractSumTax = (editRowData.changeQty ? editRowData.changeQty : 0) * (editRowData.changePrice ? editRowData.changePrice : 0) - ((editRowData.changeQty ? editRowData.changeQty : 0) * (editRowData.changePrice ? editRowData.changePrice : 0)) / (val ? (1 + Number(val) / 100) : 1);
                                                                btnCallbackFn.setEditedRowData({ ...editRowData });
                                                            })
                                                        },
                                                        allowClear: false,
                                                        showSearch: true,
                                                        placeholder: '?????????'
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '????????????',
                                                        dataIndex: 'isDeduct',
                                                        key: 'isDeduct',
                                                        width: 100,
                                                        render: (data) => {
                                                            if (data === '1') {
                                                                return '???';
                                                            } else {
                                                                return '???';
                                                            }
                                                        }
                                                    },
                                                    form: {
                                                        type: 'string',
                                                        placeholder: '?????????'
                                                    }
                                                },
                                                // {
                                                //     table: {
                                                //         title: '???????????????',
                                                //         dataIndex: 'settledQty',
                                                //         key: 'settledQty',
                                                //         width: 100,
                                                //         render: (data, rowData) => {
                                                //             if (rowData.a || rowData.a === 0) {
                                                //                 return rowData.a.toFixed(2)
                                                //             }
                                                //         }
                                                //     },
                                                //     form: {
                                                //         type: 'number',
                                                //         placeholder: '?????????'
                                                //     }
                                                // },
                                                {
                                                    table: {
                                                        title: '???????????????',
                                                        dataIndex: 'changeQty',
                                                        key: 'changeQty',
                                                        width: 120,
                                                        tdEdit: true,
                                                        render: (data, rowData) => {
                                                            if (rowData.changeQty || rowData.changeQty === 0) {
                                                                return rowData.changeQty.toFixed(3)
                                                            }
                                                        }
                                                    },
                                                    form: {
                                                        type: 'number',
                                                        precision: 3,
                                                        onBlur: (e, obj, btnCallbackFn) => {
                                                            let val = e.target.value;
                                                            btnCallbackFn.getEditedRowData(false).then((editRowData) => {
                                                                editRowData.changeQty = val ? Number(val) : 0;
                                                                editRowData.changePriceNoTax = (editRowData.changePrice ? editRowData.changePrice : 0) / ((editRowData.taxRate ? (1 + Number(editRowData.taxRate) / 100) : 1));
                                                                editRowData.changeContractSum = (val ? Number(val) : 0) * (editRowData.changePrice ? editRowData.changePrice : 0);
                                                                editRowData.changeContractSumNoTax = ((val ? Number(val) : 0) * (editRowData.changePrice ? editRowData.changePrice : 0)) / (editRowData.taxRate ? (1 + Number(editRowData.taxRate) / 100) : 1);
                                                                editRowData.changeContractSumTax = (val ? Number(val) : 0) * (editRowData.changePrice ? editRowData.changePrice : 0) - ((val ? Number(val) : 0) * (editRowData.changePrice ? editRowData.changePrice : 0)) / (editRowData.taxRate ? (1 + Number(editRowData.taxRate) / 100) : 1);
                                                                btnCallbackFn.setEditedRowData({ ...editRowData });
                                                            })
                                                        },
                                                        placeholder: '?????????'
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '?????????????????????',
                                                        dataIndex: 'changePrice',
                                                        key: 'changePrice',
                                                        width: 130,
                                                        tdEdit: (obj) => { return obj?.alterType === '1' ? true : false },
                                                        render: (data, rowData) => {
                                                            if (rowData.changePrice || rowData.changePrice === 0) {
                                                                return rowData.changePrice.toFixed(6)
                                                            }
                                                        }
                                                    },
                                                    form: {
                                                        type: 'number',
                                                        precision: 6,
                                                        onBlur: (e, obj, btnCallbackFn) => {
                                                            let val = e.target.value;
                                                            btnCallbackFn.getEditedRowData(false).then((editRowData) => {
                                                                editRowData.changePrice = val ? Number(val) : 0;
                                                                editRowData.changePriceNoTax = (val ? Number(val) : 0) / ((editRowData.taxRate ? (1 + Number(editRowData.taxRate) / 100) : 1));
                                                                editRowData.changeContractSum = (editRowData.changeQty ? editRowData.changeQty : 0) * (val ? Number(val) : 0);
                                                                editRowData.changeContractSumNoTax = ((editRowData.changeQty ? editRowData.changeQty : 0) * (val ? Number(val) : 0)) / (editRowData.taxRate ? (1 + Number(editRowData.taxRate) / 100) : 1);
                                                                editRowData.changeContractSumTax = (editRowData.changeQty ? editRowData.changeQty : 0) * (val ? Number(val) : 0) - ((editRowData.changeQty ? editRowData.changeQty : 0) * (val ? Number(val) : 0)) / (editRowData.taxRate ? (1 + Number(editRowData.taxRate) / 100) : 1);
                                                                btnCallbackFn.setEditedRowData({ ...editRowData });
                                                            })
                                                        },
                                                        placeholder: '?????????'
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '????????????????????????',
                                                        dataIndex: 'changePriceNoTax',
                                                        key: 'changePriceNoTax',
                                                        width: 140,
                                                        render: (data, rowData) => {
                                                            if (rowData.changePriceNoTax || rowData.changePriceNoTax === 0) {
                                                                return rowData.changePriceNoTax.toFixed(6)
                                                            }
                                                        }
                                                    },
                                                    form: {
                                                        type: 'number',
                                                        precision: 6,
                                                        placeholder: '?????????'
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '?????????????????????',
                                                        dataIndex: 'changeContractSum',
                                                        key: 'changeContractSum',
                                                        width: 130,
                                                        render: (data, rowData) => {
                                                            if (rowData.changeContractSum || rowData.changeContractSum === 0) {
                                                                return rowData.changeContractSum.toFixed(2)
                                                            }
                                                        }
                                                    },
                                                    form: {
                                                        type: 'number',
                                                        precision: 2,
                                                        placeholder: '?????????'
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '????????????????????????',
                                                        dataIndex: 'changeContractSumNoTax',
                                                        key: 'changeContractSumNoTax',
                                                        width: 140,
                                                        render: (data, rowData) => {
                                                            if (rowData.changeContractSumNoTax || rowData.changeContractSumNoTax === 0) {
                                                                return rowData.changeContractSumNoTax.toFixed(2)
                                                            }
                                                        }
                                                    },
                                                    form: {
                                                        type: 'number',
                                                        precision: 2,
                                                        placeholder: '?????????'
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '???????????????',
                                                        dataIndex: 'changeContractSumTax',
                                                        key: 'changeContractSumTax',
                                                        width: 120,
                                                        render: (data, rowData) => {
                                                            if (rowData.changeContractSumTax || rowData.changeContractSumTax === 0) {
                                                                return rowData.changeContractSumTax.toFixed(2)
                                                            }
                                                        }
                                                    },
                                                    form: {
                                                        type: 'number',
                                                        precision: 2,
                                                        placeholder: '?????????'
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '??????',
                                                        dataIndex: 'remarks',
                                                        key: 'remarks',
                                                        width: 100,
                                                        tdEdit: true
                                                    },
                                                    form: {
                                                        type: 'string',
                                                        placeholder: '?????????'
                                                    }
                                                }
                                            ]
                                        }
                                    },
                                    {
                                        type: "qnnTable",
                                        field: "replenishLeaseDetailedList",
                                        incToForm: true,
                                        dependencies: ["code7"],
                                        hide: (obj) => {
                                            let rowData = obj?.tableFns?.qnnForm?.form?.getFieldsValue?.();
                                            if (rowData && rowData.code7 === 'WL') {
                                                return false;
                                            } else {
                                                return true;
                                            }
                                        },
                                        qnnTableConfig: {
                                            wrappedComponentRef: (me) => {
                                                this.tableOne = me;
                                            },
                                            antd: {
                                                size: "small",
                                                rowKey: 'zxCtSuppliesContrReplenishLeaseDetailId'
                                            },
                                            paginationConfig: false,
                                            isShowRowSelect: true,
                                            actionBtns: [
                                                {
                                                    name: 'addRow',
                                                    icon: 'plus',
                                                    type: 'primary',
                                                    label: '??????',
                                                    hide: (obj) => {
                                                        if (obj.props.qnnFormProps.funcCallBackParams.clickCb.rowInfo.name === 'detail') {
                                                            return true;
                                                        } else {
                                                            return false;
                                                        }
                                                    },
                                                    addRowDefaultData: () => {
                                                        let rowData = this.table.form.getFieldsValue();
                                                        return {
                                                            pp5: rowData.pp6,
                                                            isDeduct: rowData.isDeduct && rowData.isDeduct.length ? rowData.isDeduct[0] : '0'
                                                        }
                                                    }
                                                },
                                                {
                                                    name: 'del',
                                                    icon: 'del',
                                                    type: 'danger',
                                                    label: '??????',
                                                    hide: (obj) => {
                                                        if (obj.props.qnnFormProps.funcCallBackParams.clickCb.rowInfo.name === 'detail') {
                                                            return true;
                                                        } else {
                                                            return false;
                                                        }
                                                    }
                                                }
                                            ],
                                            formConfig: [
                                                {
                                                    isInTable: false,
                                                    form: {
                                                        field: 'contrItemID',
                                                        type: 'string',
                                                        hide: true,
                                                    }
                                                },
                                                {
                                                    isInTable: false,
                                                    form: {
                                                        field: 'zxCtSuppliesContrReplenishLeaseDetailId',
                                                        type: 'string',
                                                        hide: true,
                                                    }
                                                },
                                                {
                                                    isInTable: false,
                                                    form: {
                                                        field: 'coBookID',
                                                        type: 'string',
                                                        hide: true,
                                                    }
                                                },
                                                {
                                                    isInTable: false,
                                                    form: {
                                                        field: 'pp5',
                                                        type: 'string',
                                                        hide: true,
                                                    }
                                                },
                                                {
                                                    isInTable: false,
                                                    form: {
                                                        field: 'workID',
                                                        type: 'string',
                                                        hide: true,
                                                    }
                                                },
                                                {
                                                    isInTable: false,
                                                    form: {
                                                        field: 'workType',
                                                        type: 'string',
                                                        hide: true,
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: `<div>????????????<span style='color: #ff4d4f'>*</span></div>`,
                                                        dataIndex: 'alterType',
                                                        key: 'alterType',
                                                        width: 100,
                                                        type: 'select',
                                                        tdEdit: true,
                                                        fixed: 'left'
                                                    },
                                                    form: {
                                                        type: 'select',
                                                        optionConfig: {
                                                            label: 'label', //?????? label
                                                            value: 'value'
                                                        },
                                                        optionData: [
                                                            {
                                                                label: '??????',
                                                                value: '1'
                                                            },
                                                            {
                                                                label: '??????',
                                                                value: '2'
                                                            }
                                                        ],
                                                        onChange: (val, obj, btnCallbackFn) => {
                                                            btnCallbackFn.getEditedRowData(false).then((editRowData) => {
                                                                editRowData.alterType = val;
                                                                editRowData.workTypeID = null;
                                                                editRowData.workType = null;
                                                                editRowData.workID = null;
                                                                editRowData.workName = null;
                                                                editRowData.workNo = null;
                                                                editRowData.spec = null;
                                                                editRowData.unit = null;
                                                                editRowData.contrTrrm = null;
                                                                editRowData.isNetPrice = [];
                                                                editRowData.qty = null;
                                                                editRowData.price = null;
                                                                editRowData.taxRate = null;
                                                                editRowData.contractSum = null;
                                                                editRowData.priceNoTax = null;
                                                                editRowData.contractSumNoTax = null;
                                                                editRowData.contractSumTax = null;
                                                                editRowData.rentUnit = null;
                                                                editRowData.alterTrrm = null;
                                                                editRowData.changeQty = null;
                                                                editRowData.changePrice = null;
                                                                editRowData.changePriceNoTax = null;
                                                                editRowData.changeContractSum = null;
                                                                editRowData.changeContractSumNoTax = null;
                                                                editRowData.changeContractSumTax = null;
                                                                btnCallbackFn.setEditedRowData({ ...editRowData });
                                                                editRowData.isNetPrice = '0';
                                                                btnCallbackFn.setEditedRowData({ ...editRowData });
                                                            })
                                                        },
                                                        required: true,
                                                        placeholder: '?????????'
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: `<div>????????????<span style='color: #ff4d4f'>*</span></div>`,
                                                        dataIndex: 'workTypeID',
                                                        key: 'workTypeID',
                                                        width: 150,
                                                        tdEdit: (obj) => { return obj?.alterType === '1' ? true : false },
                                                        fixed: 'left',
                                                        type: 'select'
                                                    },
                                                    form: {
                                                        type: 'select',
                                                        optionConfig: {
                                                            label: 'catName', //?????? label
                                                            value: 'id'
                                                        },
                                                        dependencies: ['alterType'],
                                                        dependenciesReRender: true,
                                                        fetchConfig: {
                                                            apiName: 'getZxSkResCategoryMaterialsList',
                                                            otherParams: {
                                                                parentID: '0002'
                                                            }
                                                        },
                                                        onChange: (val, obj, btnCallbackFn) => {
                                                            btnCallbackFn.getEditedRowData(false).then((editRowData) => {
                                                                editRowData.workTypeID = obj.itemData.id;
                                                                editRowData.workType = obj.itemData.catName;
                                                                editRowData.workID = null;
                                                                editRowData.workName = null;
                                                                editRowData.workNo = null;
                                                                editRowData.spec = null;
                                                                editRowData.unit = null;
                                                                editRowData.contrTrrm = null;
                                                                editRowData.qty = null;
                                                                editRowData.price = null;
                                                                editRowData.taxRate = null;
                                                                editRowData.contractSum = null;
                                                                editRowData.priceNoTax = null;
                                                                editRowData.contractSumNoTax = null;
                                                                editRowData.contractSumTax = null;
                                                                editRowData.rentUnit = null;
                                                                editRowData.alterTrrm = null;
                                                                editRowData.changeQty = null;
                                                                editRowData.changePrice = null;
                                                                editRowData.changePriceNoTax = null;
                                                                editRowData.changeContractSum = null;
                                                                editRowData.changeContractSumNoTax = null;
                                                                editRowData.changeContractSumTax = null;
                                                                btnCallbackFn.setEditedRowData({ ...editRowData });
                                                            })
                                                        },
                                                        required: true,
                                                        allowClear: false,
                                                        showSearch: true,
                                                        placeholder: '?????????'
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: `<div>????????????<span style='color: #ff4d4f'>*</span></div>`,
                                                        dataIndex: 'workNo',
                                                        key: 'workNo',
                                                        width: 150,
                                                        tdEdit: (obj) => { return obj?.alterType ? true : false },
                                                        fixed: 'left'
                                                    },
                                                    form: {
                                                        type: 'selectByQnnTable',
                                                        dependencies: ['alterType', 'workTypeID'],
                                                        dependenciesReRender: true,
                                                        optionConfig: (obj) => {
                                                            if (obj.rowData.alterType === '1') {
                                                                return {
                                                                    label: 'resCode', //?????? label
                                                                    value: 'resCode'
                                                                }
                                                            } else if (obj.rowData.alterType === '2') {
                                                                return {
                                                                    label: 'workNo', //?????? label
                                                                    value: 'workNo'
                                                                }
                                                            }
                                                        },
                                                        dropdownMatchSelectWidth: 600,
                                                        qnnTableConfig: {
                                                            antd: {
                                                                rowKey: "id"
                                                            },
                                                            fetchConfig: (obj) => {
                                                                if (obj.rowData.alterType === '1') {
                                                                    if (obj.rowData.workTypeID) {
                                                                        return {
                                                                            apiName: 'getZxSkResourceMaterialsListNameJoinResource',
                                                                            otherParams: {
                                                                                id: obj.rowData.workTypeID
                                                                            }
                                                                        }
                                                                    }else{
                                                                        return {};
                                                                    }
                                                                } else{
                                                                    return {
                                                                        apiName: 'getZxCtSuppliesContrLeaseListList',
                                                                        // apiName: 'getZxCtSuppliesContrApplyLeaseListList',
                                                                        otherParams: {
                                                                            contractID: this.table.form.getFieldValue('pp6')
                                                                        }
                                                                    }
                                                                }
                                                            },
                                                            searchBtnsStyle: "inline",
                                                            formConfig: [
                                                                {
                                                                    isInTable:(obj) => {
                                                                        if(obj?.props?.rowData?.alterType === '1'){
                                                                            return true;
                                                                        }
                                                                        return false;
                                                                    },
                                                                    table: {
                                                                        dataIndex: "resCode",
                                                                        title: "??????",
                                                                    },
                                                                    form: {
                                                                        type: "string"
                                                                    }
                                                                },
                                                                {
                                                                    isInTable:(obj) => {
                                                                        if(obj?.props?.rowData?.alterType === '1'){
                                                                            return true;
                                                                        }
                                                                        return false;
                                                                    },
                                                                    table: {
                                                                        dataIndex: "resName",
                                                                        title: "??????",
                                                                    },
                                                                    form: {
                                                                        type: "string"
                                                                    }
                                                                },
                                                                {
                                                                    isInTable:(obj) => {
                                                                        if(obj?.props?.rowData?.alterType === '2'){
                                                                            return true;
                                                                        }
                                                                        return false;
                                                                    },
                                                                    table: {
                                                                        dataIndex: "workNo",
                                                                        title: "????????????",
                                                                    },
                                                                    form: {
                                                                        type: "string"
                                                                    }
                                                                },
                                                                {
                                                                    isInTable:(obj) => {
                                                                        if(obj?.props?.rowData?.alterType === '2'){
                                                                            return true;
                                                                        }
                                                                        return false;
                                                                    },
                                                                    table: {
                                                                        dataIndex: "workName",
                                                                        title: "????????????",
                                                                    },
                                                                    form: {
                                                                        type: "string"
                                                                    }
                                                                },
                                                                {
                                                                    table: {
                                                                        dataIndex: "spec",
                                                                        title: "????????????",
                                                                    },
                                                                    form: {
                                                                        type: "string"
                                                                    }
                                                                },
                                                                {
                                                                    table: {
                                                                        dataIndex: "unit",
                                                                        title: "??????",
                                                                    },
                                                                    form: {
                                                                        type: "string"
                                                                    }
                                                                },
                                                                {
                                                                    isInTable:(obj) => {
                                                                        if(obj?.props?.rowData?.alterType === '2'){
                                                                            return true;
                                                                        }
                                                                        return false;
                                                                    },
                                                                    table: {
                                                                        dataIndex: "changePrice",
                                                                        title: "????????????",
                                                                        render:(data,rowData) => {
                                                                            if(data){
                                                                                return data;
                                                                            }else{
                                                                                return rowData?.price
                                                                            }
                                                                        }
                                                                    },
                                                                    form: {
                                                                        type: "string"
                                                                    }
                                                                },
                                                                {
                                                                    isInTable:(obj) => {
                                                                        if(obj?.props?.rowData?.alterType === '2'){
                                                                            return true;
                                                                        }
                                                                        return false;
                                                                    },
                                                                    table: {
                                                                        dataIndex: "changeQty",
                                                                        title: "????????????",
                                                                        render:(data,rowData) => {
                                                                            if(data){
                                                                                return data;
                                                                            }else{
                                                                                return rowData?.qty
                                                                            }
                                                                        }
                                                                    },
                                                                    form: {
                                                                        type: "string"
                                                                    }
                                                                },
                                                            ]
                                                        },
                                                        onChange: (val, obj, btnCallbackFn) => {
                                                            btnCallbackFn.getEditedRowData(false).then((editRowData) => {
                                                                if (editRowData.alterType === '1') {
                                                                    editRowData.workID = obj.itemData.id;
                                                                    editRowData.workName = obj.itemData.resName;
                                                                    editRowData.workNo = obj.itemData.resCode;
                                                                    editRowData.spec = obj.itemData.spec;
                                                                    editRowData.unit = obj.itemData.unit;
                                                                    editRowData.contrTrrm = null;
                                                                    editRowData.qty = null;
                                                                    editRowData.price = null;
                                                                    editRowData.taxRate = null;
                                                                    editRowData.contractSum = null;
                                                                    editRowData.priceNoTax = null;
                                                                    editRowData.contractSumNoTax = null;
                                                                    editRowData.contractSumTax = null;
                                                                    editRowData.rentUnit = null;
                                                                    editRowData.alterTrrm = null;
                                                                    editRowData.changeQty = null;
                                                                    editRowData.changePrice = null;
                                                                    editRowData.changePriceNoTax = 0;
                                                                    editRowData.changeContractSum = 0;
                                                                    editRowData.changeContractSumNoTax = 0;
                                                                    editRowData.changeContractSumTax = 0;
                                                                } else {
                                                                    editRowData.workID = obj.itemData.workID;
                                                                    editRowData.coBookID = obj.itemData.zxCtSuppliesContrShopListId;
                                                                    editRowData.workTypeID = obj.itemData.workTypeID;
                                                                    editRowData.workType = obj.itemData.workType;
                                                                    editRowData.workName = obj.itemData.workName;
                                                                    editRowData.workNo = obj.itemData.workNo;
                                                                    editRowData.spec = obj.itemData.spec;
                                                                    editRowData.unit = obj.itemData.unit;
                                                                    editRowData.isNetPrice = obj.itemData.isNetPrice;
                                                                    editRowData.contrTrrm = obj.itemData.contrTrrm;
                                                                    editRowData.qty = obj.itemData.qty;
                                                                    editRowData.price = obj.itemData.price;
                                                                    editRowData.taxRate = obj.itemData.taxRate;
                                                                    editRowData.contractSum = obj.itemData.contractSum;
                                                                    editRowData.priceNoTax = obj.itemData.priceNoTax;
                                                                    editRowData.contractSumNoTax = obj.itemData.contractSumNoTax;
                                                                    editRowData.contractSumTax = obj.itemData.contractSumTax;
                                                                    editRowData.contrItemID = obj.itemData.zxCtSuppliesContrLeaseListId;
                                                                    editRowData.rentUnit = obj.itemData.rentUnit;
                                                                    editRowData.settledQty = obj.itemData.settledQty;
                                                                    if (obj.itemData.changeContractSum || obj.itemData.changeContractSum === 0) {
                                                                        editRowData.changeContractSum = obj.itemData.changeContractSum;
                                                                        editRowData.alterTrrm = obj.itemData.alterTrrm;
                                                                        editRowData.changeQty = obj.itemData.changeQty;
                                                                        editRowData.changePrice = obj.itemData.changePrice;
                                                                        editRowData.changePriceNoTax = obj.itemData.changePriceNoTax;
                                                                        editRowData.changeContractSumNoTax = obj.itemData.changeContractSumNoTax;
                                                                        editRowData.changeContractSumTax = obj.itemData.changeContractSumTax;
                                                                    } else {
                                                                        editRowData.changeContractSum = obj.itemData.contractSum;
                                                                        editRowData.alterTrrm = obj.itemData.contrTrrm;
                                                                        editRowData.changeQty = obj.itemData.qty;
                                                                        editRowData.changePrice = obj.itemData.price;
                                                                        editRowData.changePriceNoTax = obj.itemData.priceNoTax;
                                                                        editRowData.changeContractSumNoTax = obj.itemData.contractSumNoTax;
                                                                        editRowData.changeContractSumTax = obj.itemData.contractSumTax;
                                                                    }
                                                                }
                                                                btnCallbackFn.setEditedRowData({ ...editRowData });
                                                            })
                                                        },
                                                        required: true,
                                                        allowClear: false,
                                                        showSearch: true,
                                                        placeholder: '?????????'
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '????????????',
                                                        dataIndex: 'workName',
                                                        key: 'workName',
                                                        width: 100
                                                    },
                                                    form: {
                                                        type: 'string',
                                                        placeholder: '?????????'
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
                                                        placeholder: '?????????'
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '??????',
                                                        dataIndex: 'unit',
                                                        key: 'unit',
                                                        width: 100
                                                    },
                                                    form: {
                                                        type: 'string',
                                                        placeholder: '?????????'
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '????????????',
                                                        dataIndex: 'isNetPrice',
                                                        key: 'isNetPrice',
                                                        width: 100,
                                                        tdEdit: (obj) => { return obj?.alterType === '1' ? true : false },
                                                        render: (data) => {
                                                            if (data === '1') {
                                                                return '???';
                                                            } else {
                                                                return '???';
                                                            }
                                                        }
                                                    },
                                                    form: {
                                                        type: 'checkbox',
                                                        optionData: [
                                                            {
                                                                value: "1"
                                                            }
                                                        ]
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '??????',
                                                        dataIndex: 'contrTrrm',
                                                        key: 'contrTrrm',
                                                        width: 100,
                                                        render: (data, rowData) => {
                                                            if (rowData.contrTrrm || rowData.contrTrrm === 0) {
                                                                return rowData.contrTrrm.toFixed(3)
                                                            }
                                                        }
                                                    },
                                                    form: {
                                                        type: 'number',
                                                        precision: 3,
                                                        placeholder: '?????????'
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '??????',
                                                        dataIndex: 'qty',
                                                        key: 'qty',
                                                        width: 100,
                                                        render: (data, rowData) => {
                                                            if (rowData.qty || rowData.qty === 0) {
                                                                return rowData.qty.toFixed(3)
                                                            }
                                                        }
                                                    },
                                                    form: {
                                                        type: 'number',
                                                        precision: 3,
                                                        placeholder: '?????????'
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '??????????????????',
                                                        dataIndex: 'price',
                                                        key: 'price',
                                                        width: 120,
                                                        render: (data, rowData) => {
                                                            if (rowData.price || rowData.price === 0) {
                                                                return rowData.price.toFixed(6)
                                                            }
                                                        }
                                                    },
                                                    form: {
                                                        type: 'number',
                                                        precision: 6,
                                                        placeholder: '?????????'
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '??????????????????',
                                                        dataIndex: 'contractSum',
                                                        key: 'contractSum',
                                                        width: 120,
                                                        render: (data, rowData) => {
                                                            if (rowData.contractSum || rowData.contractSum === 0) {
                                                                return rowData.contractSum.toFixed(2)
                                                            }
                                                        }
                                                    },
                                                    form: {
                                                        type: 'number',
                                                        precision: 2,
                                                        placeholder: '?????????'
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '?????????????????????',
                                                        dataIndex: 'priceNoTax',
                                                        key: 'priceNoTax',
                                                        width: 130,
                                                        render: (data, rowData) => {
                                                            if (rowData.priceNoTax || rowData.priceNoTax === 0) {
                                                                return rowData.priceNoTax.toFixed(6)
                                                            }
                                                        }
                                                    },
                                                    form: {
                                                        type: 'number',
                                                        precision: 6,
                                                        placeholder: '?????????'
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '?????????????????????',
                                                        dataIndex: 'contractSumNoTax',
                                                        key: 'contractSumNoTax',
                                                        width: 130,
                                                        render: (data, rowData) => {
                                                            if (rowData.contractSumNoTax || rowData.contractSumNoTax === 0) {
                                                                return rowData.contractSumNoTax.toFixed(2)
                                                            }
                                                        }
                                                    },
                                                    form: {
                                                        type: 'number',
                                                        precision: 2,
                                                        placeholder: '?????????'
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '??????',
                                                        dataIndex: 'contractSumTax',
                                                        key: 'contractSumTax',
                                                        width: 100,
                                                        render: (data, rowData) => {
                                                            if (rowData.contractSumTax || rowData.contractSumTax === 0) {
                                                                return rowData.contractSumTax.toFixed(2)
                                                            }
                                                        }
                                                    },
                                                    form: {
                                                        type: 'number',
                                                        precision: 2,
                                                        placeholder: '?????????'
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '??????(%)',
                                                        dataIndex: 'taxRate',
                                                        key: 'taxRate',
                                                        width: 100,
                                                        tdEdit: (obj) => { return obj?.alterType === '1' ? true : false },
                                                        type: 'select'
                                                    },
                                                    form: {
                                                        type: 'select',
                                                        optionConfig: {
                                                            label: 'itemName', //?????? label
                                                            value: 'itemId'
                                                        },
                                                        fetchConfig: {
                                                            apiName: 'getBaseCodeSelect',
                                                            otherParams: {
                                                                itemId: 'shuiLV'
                                                            }
                                                        },
                                                        onChange: (val, obj, btnCallbackFn) => {
                                                            btnCallbackFn.getEditedRowData(false).then((editRowData) => {
                                                                editRowData.taxRate = val;
                                                                editRowData.changePriceNoTax = (editRowData.changePrice ? editRowData.changePrice : 0) / ((val ? (1 + Number(val) / 100) : 1));
                                                                editRowData.changeContractSum = (editRowData.changeQty ? editRowData.changeQty : 0) * (editRowData.changePrice ? editRowData.changePrice : 0) * (editRowData.alterTrrm ? editRowData.alterTrrm : 0);
                                                                editRowData.changeContractSumNoTax = ((editRowData.changeQty ? editRowData.changeQty : 0) * (editRowData.changePrice ? editRowData.changePrice : 0)) * (editRowData.alterTrrm ? editRowData.alterTrrm : 0) / (val ? (1 + Number(val) / 100) : 1);
                                                                editRowData.changeContractSumTax = (editRowData.changeQty ? editRowData.changeQty : 0) * (editRowData.changePrice ? editRowData.changePrice : 0) * (editRowData.alterTrrm ? editRowData.alterTrrm : 0) - ((editRowData.changeQty ? editRowData.changeQty : 0) * (editRowData.changePrice ? editRowData.changePrice : 0)) * (editRowData.alterTrrm ? editRowData.alterTrrm : 0) / (val ? (1 + Number(val) / 100) : 1);
                                                                btnCallbackFn.setEditedRowData({ ...editRowData });
                                                            })
                                                        },
                                                        allowClear: false,
                                                        showSearch: true,
                                                        placeholder: '?????????'
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '????????????',
                                                        dataIndex: 'isDeduct',
                                                        key: 'isDeduct',
                                                        width: 100,
                                                        render: (data) => {
                                                            if (data === '1') {
                                                                return '???';
                                                            } else {
                                                                return '???';
                                                            }
                                                        }
                                                    },
                                                    form: {
                                                        type: 'string',
                                                        placeholder: '?????????'
                                                    }
                                                },
                                                // {
                                                //     table: {
                                                //         title: '???????????????',
                                                //         dataIndex: 'settledQty',
                                                //         key: 'settledQty',
                                                //         width: 100,
                                                //         render: (data, rowData) => {
                                                //             if (rowData.a || rowData.a === 0) {
                                                //                 return rowData.a.toFixed(2)
                                                //             }
                                                //         }
                                                //     },
                                                //     form: {
                                                //         type: 'number',
                                                //         placeholder: '?????????'
                                                //     }
                                                // },
                                                {
                                                    table: {
                                                        title: '????????????',
                                                        dataIndex: 'rentUnit',
                                                        key: 'rentUnit',
                                                        width: 100,
                                                        tdEdit: (obj) => { return obj?.alterType === '1' ? true : false }
                                                    },
                                                    form: {
                                                        type: 'string',
                                                        placeholder: '?????????'
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '???????????????',
                                                        dataIndex: 'alterTrrm',
                                                        key: 'alterTrrm',
                                                        width: 100,
                                                        tdEdit: true,
                                                        render: (data, rowData) => {
                                                            if (rowData.alterTrrm || rowData.alterTrrm === 0) {
                                                                return rowData.alterTrrm.toFixed(3)
                                                            }
                                                        }
                                                    },
                                                    form: {
                                                        type: 'number',
                                                        onBlur: (e, obj, btnCallbackFn) => {
                                                            let val = e.target.value;
                                                            btnCallbackFn.getEditedRowData(false).then((editRowData) => {
                                                                editRowData.alterTrrm = val ? Number(val) : 0;
                                                                editRowData.changePriceNoTax = (editRowData.changePrice ? editRowData.changePrice : 0) / ((editRowData.taxRate ? (1 + Number(editRowData.taxRate) / 100) : 1));
                                                                editRowData.changeContractSum = (editRowData.changeQty ? editRowData.changeQty : 0) * (editRowData.changePrice ? editRowData.changePrice : 0) * (val ? Number(val) : 0);
                                                                editRowData.changeContractSumNoTax = ((editRowData.changeQty ? editRowData.changeQty : 0) * (editRowData.changePrice ? editRowData.changePrice : 0)) * (val ? Number(val) : 0) / (editRowData.taxRate ? (1 + Number(editRowData.taxRate) / 100) : 1);
                                                                editRowData.changeContractSumTax = (editRowData.changeQty ? editRowData.changeQty : 0) * (editRowData.changePrice ? editRowData.changePrice : 0) * (val ? Number(val) : 0) - ((editRowData.changeQty ? editRowData.changeQty : 0) * (editRowData.changePrice ? editRowData.changePrice : 0)) * (val ? Number(val) : 0) / (editRowData.taxRate ? (1 + Number(editRowData.taxRate) / 100) : 1);
                                                                btnCallbackFn.setEditedRowData({ ...editRowData });
                                                            })
                                                        },
                                                        precision: 3,
                                                        placeholder: '?????????'
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '???????????????',
                                                        dataIndex: 'changeQty',
                                                        key: 'changeQty',
                                                        width: 120,
                                                        tdEdit: true,
                                                        render: (data, rowData) => {
                                                            if (rowData.changeQty || rowData.changeQty === 0) {
                                                                return rowData.changeQty.toFixed(3)
                                                            }
                                                        }
                                                    },
                                                    form: {
                                                        type: 'number',
                                                        precision: 3,
                                                        onBlur: (e, obj, btnCallbackFn) => {
                                                            let val = e.target.value;
                                                            btnCallbackFn.getEditedRowData(false).then((editRowData) => {
                                                                editRowData.changeQty = val ? Number(val) : 0;
                                                                editRowData.changePriceNoTax = (editRowData.changePrice ? editRowData.changePrice : 0) / ((editRowData.taxRate ? (1 + Number(editRowData.taxRate) / 100) : 1));
                                                                editRowData.changeContractSum = (val ? Number(val) : 0) * (editRowData.changePrice ? editRowData.changePrice : 0) * (editRowData.alterTrrm ? editRowData.alterTrrm : 0);
                                                                editRowData.changeContractSumNoTax = ((val ? Number(val) : 0) * (editRowData.changePrice ? editRowData.changePrice : 0)) * (editRowData.alterTrrm ? editRowData.alterTrrm : 0) / (editRowData.taxRate ? (1 + Number(editRowData.taxRate) / 100) : 1);
                                                                editRowData.changeContractSumTax = (val ? Number(val) : 0) * (editRowData.changePrice ? editRowData.changePrice : 0) * (editRowData.alterTrrm ? editRowData.alterTrrm : 0) - ((val ? Number(val) : 0) * (editRowData.changePrice ? editRowData.changePrice : 0)) * (editRowData.alterTrrm ? editRowData.alterTrrm : 0) / (editRowData.taxRate ? (1 + Number(editRowData.taxRate) / 100) : 1);
                                                                btnCallbackFn.setEditedRowData({ ...editRowData });
                                                            })
                                                        },
                                                        placeholder: '?????????'
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '?????????????????????',
                                                        dataIndex: 'changePrice',
                                                        key: 'changePrice',
                                                        width: 130,
                                                        tdEdit: (obj) => { return obj?.alterType === '1' ? true : false },
                                                        render: (data, rowData) => {
                                                            if (rowData.changePrice || rowData.changePrice === 0) {
                                                                return rowData.changePrice.toFixed(6)
                                                            }
                                                        }
                                                    },
                                                    form: {
                                                        type: 'number',
                                                        precision: 6,
                                                        onBlur: (e, obj, btnCallbackFn) => {
                                                            let val = e.target.value;
                                                            btnCallbackFn.getEditedRowData(false).then((editRowData) => {
                                                                editRowData.changePrice = val ? Number(val) : 0;
                                                                editRowData.changePriceNoTax = (val ? Number(val) : 0) / ((editRowData.taxRate ? (1 + Number(editRowData.taxRate) / 100) : 1));
                                                                editRowData.changeContractSum = (editRowData.changeQty ? editRowData.changeQty : 0) * (val ? Number(val) : 0) * (editRowData.alterTrrm ? editRowData.alterTrrm : 0);
                                                                editRowData.changeContractSumNoTax = ((editRowData.changeQty ? editRowData.changeQty : 0) * (val ? Number(val) : 0)) * (editRowData.alterTrrm ? editRowData.alterTrrm : 0) / (editRowData.taxRate ? (1 + Number(editRowData.taxRate) / 100) : 1);
                                                                editRowData.changeContractSumTax = (editRowData.changeQty ? editRowData.changeQty : 0) * (val ? Number(val) : 0) * (editRowData.alterTrrm ? editRowData.alterTrrm : 0) - ((editRowData.changeQty ? editRowData.changeQty : 0) * (val ? Number(val) : 0)) * (editRowData.alterTrrm ? editRowData.alterTrrm : 0) / (editRowData.taxRate ? (1 + Number(editRowData.taxRate) / 100) : 1);
                                                                btnCallbackFn.setEditedRowData({ ...editRowData });
                                                            })
                                                        },
                                                        placeholder: '?????????'
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '????????????????????????',
                                                        dataIndex: 'changePriceNoTax',
                                                        key: 'changePriceNoTax',
                                                        width: 140,
                                                        render: (data, rowData) => {
                                                            if (rowData.changePriceNoTax || rowData.changePriceNoTax === 0) {
                                                                return rowData.changePriceNoTax.toFixed(6)
                                                            }
                                                        }
                                                    },
                                                    form: {
                                                        type: 'number',
                                                        precision: 6,
                                                        placeholder: '?????????'
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '?????????????????????',
                                                        dataIndex: 'changeContractSum',
                                                        key: 'changeContractSum',
                                                        width: 130,
                                                        render: (data, rowData) => {
                                                            if (rowData.changeContractSum || rowData.changeContractSum === 0) {
                                                                return rowData.changeContractSum.toFixed(2)
                                                            }
                                                        }
                                                    },
                                                    form: {
                                                        type: 'number',
                                                        precision: 2,
                                                        placeholder: '?????????'
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '????????????????????????',
                                                        dataIndex: 'changeContractSumNoTax',
                                                        key: 'changeContractSumNoTax',
                                                        width: 140,
                                                        render: (data, rowData) => {
                                                            if (rowData.changeContractSumNoTax || rowData.changeContractSumNoTax === 0) {
                                                                return rowData.changeContractSumNoTax.toFixed(2)
                                                            }
                                                        }
                                                    },
                                                    form: {
                                                        type: 'number',
                                                        precision: 2,
                                                        placeholder: '?????????'
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '???????????????',
                                                        dataIndex: 'changeContractSumTax',
                                                        key: 'changeContractSumTax',
                                                        width: 120,
                                                        render: (data, rowData) => {
                                                            if (rowData.changeContractSumTax || rowData.changeContractSumTax === 0) {
                                                                return rowData.changeContractSumTax.toFixed(2)
                                                            }
                                                        }
                                                    },
                                                    form: {
                                                        type: 'number',
                                                        precision: 2,
                                                        placeholder: '?????????'
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '??????',
                                                        dataIndex: 'remarks',
                                                        key: 'remarks',
                                                        width: 100,
                                                        tdEdit: true
                                                    },
                                                    form: {
                                                        type: 'string',
                                                        placeholder: '?????????'
                                                    }
                                                }
                                            ]
                                        }
                                    }
                                ]
                            }
                        }
                    ]}
                    formConfig={[
                        {
                            isInTable: false,
                            form: {
                                field: 'replenishAgreementId',
                                type: 'string',
                                hide: true,
                            }
                        },
                        {
                            table: {
                                title: '??????????????????',
                                dataIndex: 'contractNo',
                                key: 'contractNo',
                                filter: true,
                                fixed: 'left',
                                width: 180,
                                onClick: 'detail'
                            },
                            form: {
                                type: 'string',
                                placeholder: '?????????'
                            }
                        },
                        {
                            table: {
                                title: '??????????????????',
                                dataIndex: 'pp3',
                                key: 'pp3',
                                filter: true,
                                fixed: 'left',
                                width: 180
                            },
                            form: {
                                type: 'string',
                                placeholder: '?????????'
                            }
                        },
                        {
                            table: {
                                title: '????????????',
                                dataIndex: 'contractName',
                                key: 'contractName',
                                filter: true,
                                fixed: 'left',
                                width: 180
                            },
                            form: {
                                type: 'string',
                                placeholder: '?????????'
                            }
                        },
                        {
                            table: {
                                title: '????????????',
                                dataIndex: 'contractType',
                                key: 'contractType',
                                width: 150
                            },
                            form: {
                                type: 'string',
                                initialValue: '???????????????',
                                placeholder: '?????????'
                            }
                        },
                        {
                            table: {
                                title: '????????????',
                                dataIndex: 'firstName',
                                key: 'firstName',
                                width: 180,
                                filter: true
                            },
                            form: {
                                type: 'string',
                                placeholder: '?????????'
                            }
                        },
                        {
                            table: {
                                title: '????????????',
                                dataIndex: 'secondName',
                                key: 'secondName',
                                width: 180,
                                filter: true
                            },
                            form: {
                                type: 'string',
                                placeholder: '?????????'
                            }
                        },
                        {
                            table: {
                                title: '???????????????',
                                dataIndex: 'agent',
                                key: 'agent',
                                width: 100
                            },
                            form: {
                                type: 'string',
                                placeholder: '?????????'
                            }
                        },
                        {
                            table: {
                                title: '??????????????????(??????)',
                                dataIndex: 'contractCost',
                                key: 'contractCost',
                                width: 150,
                                render: (data, rowData) => {
                                    if (rowData.contractCost || rowData.contractCost === 0) {
                                        return rowData.contractCost.toFixed(6)
                                    }
                                }
                            },
                            form: {
                                type: 'number',
                                placeholder: '?????????'
                            }
                        },
                        {
                            table: {
                                title: '????????????????????????(??????)',
                                dataIndex: 'pp4',
                                key: 'pp4',
                                width: 180,
                                render: (data, rowData) => {
                                    if (rowData.pp4 || rowData.pp4 === 0) {
                                        return Number(rowData.pp4).toFixed(6)
                                    }
                                }
                            },
                            form: {
                                type: 'string',
                                placeholder: '?????????'
                            }
                        },
                        {
                            table: {
                                title: '?????????????????????(??????)',
                                dataIndex: 'alterContractSum',
                                key: 'alterContractSum',
                                width: 180,
                                render: (data, rowData) => {
                                    if (rowData.alterContractSum || rowData.alterContractSum === 0) {
                                        return rowData.alterContractSum.toFixed(6)
                                    }
                                }
                            },
                            form: {
                                type: 'number',
                                placeholder: '?????????'
                            }
                        },
                        {
                            table: {
                                title: '????????????',
                                dataIndex: 'isDeduct',
                                key: 'isDeduct',
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
                            table: {
                                title: '????????????',
                                dataIndex: 'startDate',
                                key: 'startDate',
                                width: 100,
                                format: 'YYYY-MM-DD',
                            },
                            form: {
                                type: 'date',
                                placeholder: '?????????'
                            }
                        },
                        {
                            table: {
                                title: '????????????',
                                dataIndex: 'endDate',
                                key: 'endDate',
                                width: 100,
                                format: 'YYYY-MM-DD',
                            },
                            form: {
                                type: 'date',
                                placeholder: '?????????'
                            }
                        },
                        {
                            table: {
                                title: '?????????',
                                dataIndex: 'beginPer',
                                key: 'beginPer',
                                width: 100
                            },
                            isInForm: false
                        },
                        {
                          table: {
                            title: '??????',
                            dataIndex: 'replenishAgreementFileList',
                            key: 'replenishAgreementFileList',
                            width: 100,
                            align:'center',
                            render: (val,obj) => {
                              if (obj?.replenishAgreementFileList?.length || obj?.documentFileList?.length) {
                                return <FilesForSupplementaryAgreements dataList={obj?.replenishAgreementFileList} ZWFiles={obj?.documentFileList}/>
                              } else {
                                return '?????????'
                              }
                            }
                          }
                        },
                        {
                            table: {
                                title: '????????????',
                                dataIndex: 'apih5FlowStatus',
                                key: 'apih5FlowStatus',
                                width: 100,
                                fixed: 'right',
                                render: (data) => {
                                    if (data === '-1') {
                                        return '?????????';
                                    } else if (data === '0') {
                                        return '?????????';
                                    } else if (data === '1') {
                                        return '?????????';
                                    } else if (data === '2') {
                                        return '????????????';
                                    } else if (data === '3') {
                                        return '???????????????';
                                    } else if (data === '-9') {
                                        return '????????????';
                                    }
                                }
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '????????????',
                                dataIndex: 'materialSource',
                                key: 'materialSource',
                                width: 100,
                                type: 'select',
                                fixed: 'right',
                            },
                            form: {
                                type: 'select',
                                optionConfig: {
                                    label: 'itemName', //?????? label
                                    value: 'itemId'
                                },
                                fetchConfig: {
                                    apiName: 'getBaseCodeSelect',
                                    otherParams: {
                                        itemId: 'category_contract_materialSource'
                                    }
                                },
                                allowClear: false,
                                showSearch: true,
                                placeholder: '?????????'
                            }
                        }
                    ]}
                />
            </div>
        );
    }
}

export default index;