import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import QnnForm from '../../modules/qnn-table/qnn-form';
import { message as Msg, Modal, Button } from 'antd';
import s from './style.less';
import FlowFormByMaterialManagementContract from './form';
import DetailForm from './detail';
import Operation from './operation';
const config = {
    antd: {
        rowKey: 'applyId',
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
            departmentId: ((curCompany?.ext1 === '1' || curCompany?.ext1 === '2') && lockProject?.projectId) ? (lockProject.projectId !== 'all' ? lockProject.projectId : curCompany?.companyId) : curCompany?.projectId,
            tableOneID: '',
            exportData: null,
            visible: false
        }
    }
    render() {
        const { realName } = this.props.loginAndLogoutInfo.loginInfo.userInfo;
        const { departmentId, visible, exportData } = this.state;
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
                        apiName: 'getZxCtSuppliesContrApplyList',
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
                                tableField: "MaterialManagementContract"
                            }
                        }
                    }}
                    method={{
                        addSubmitHide: (obj) => {
                            let index = obj.btnCallbackFn.getActiveKey();
                            if (index === "1" || obj?.btnCallbackFn?.form?.getFieldValue?.('applyId')) {
                                return true;
                            } else {
                                return false;
                            }
                        },
                        addSubmitSuccess: (obj) => {
                            if (obj.response.success) {
                                obj.response.data.addDisabled = '1';
                                this.table.setDeawerValues({ ...obj.response.data });
                                this.table.refresh();
                                this.table.setTabsIndex('1');
                            }
                        },
                        diysubmitHide: (obj) => {
                            let index = obj.btnCallbackFn.getActiveKey();
                            if (index === "0") {
                                return true;
                            } else {
                                return false;
                            }
                        },
                        diysubmitClick: (obj) => {
                            if (obj._formData.code7 !== 'WL') {
                                const nullRows = obj._formData.applyShopList.filter(item => !item.workTypeID || !item.workNo || !item.taxRate);
                                if (nullRows.length) {
                                    Msg.error("????????????????????????!");
                                } else {
                                    let body = {
                                        pp5: obj._formData.applyId,
                                        applyShopList: obj._formData.applyShopList
                                    };
                                    this.props.myFetch('batchAddZxCtSuppliesContrApplyShopList', body).then(
                                        ({ success, message }) => {
                                            if (success) {
                                                Msg.success(message);
                                                this.props.myFetch('getZxCtSuppliesContrApplyDetail', { applyId: obj._formData.applyId }).then(
                                                    (editObj) => {
                                                        if (editObj.success) {
                                                            this.table.form.setFieldsValue(editObj.data);
                                                            this.table.refresh();
                                                        } else {
                                                            Msg.error(editObj.message);
                                                        }
                                                    }
                                                );
                                            } else {
                                                Msg.error(message)
                                            }
                                        }
                                    );
                                }
                            } else {
                                const nullRows = obj._formData.applyLeaseList.filter(item => !item.workTypeID || !item.workNo || !item.taxRate);
                                if (nullRows.length) {
                                    Msg.error("????????????????????????!");
                                } else {
                                    let body = {
                                        pp5: obj._formData.applyId,
                                        applyLeaseList: obj._formData.applyLeaseList
                                    };
                                    this.props.myFetch('batchAddZxCtSuppliesContrApplyLeaseList', body).then(
                                        ({ success, message }) => {
                                            if (success) {
                                                Msg.success(message);
                                                this.props.myFetch('getZxCtSuppliesContrApplyDetail', { applyId: obj._formData.applyId }).then(
                                                    (editObj) => {
                                                        if (editObj.success) {
                                                            this.table.form.setFieldsValue(editObj.data);
                                                            this.table.refresh();
                                                        } else {
                                                            Msg.error(editObj.message);
                                                        }
                                                    }
                                                );
                                            } else {
                                                Msg.error(message)
                                            }
                                        }
                                    );
                                }
                            }
                        },
                        editDisabled: (obj) => {
                            let data = obj.btnCallbackFn.getSelectedRows();
                            if (data.length === 1 && (!data[0].apih5FlowStatus || data[0].apih5FlowStatus === "-1" || data[0].apih5FlowStatus === "0")) {
                                return false;
                            } else {
                                return true;
                            }
                        },
                        editSubmit0Hide: (obj) => {
                            let index = obj.btnCallbackFn.getActiveKey();
                            if (index === "1") {
                                return true;
                            } else {
                                return false;
                            }
                        },
                        editSubmit0Success: (obj) => {
                            if (obj.response.success) {
                                this.table.setTabsIndex('1');
                                let rowData = obj.response.data;
                                if (rowData.code7 !== 'WL') {
                                    if (this.table.form.getFieldValue('applyShopList')?.length) {
                                        let tableOneData = this.table.form.getFieldValue('applyShopList').map((item) => {
                                            item.isDeduct = rowData.isDeduct;
                                            return item;
                                        })
                                        this.table.form.setFieldsValue({ applyShopList: tableOneData });
                                    }
                                } else {
                                    if (this.table.form.getFieldValue('applyLeaseList')?.length) {
                                        let tableOneData = this.table.form.getFieldValue('applyLeaseList').map((item) => {
                                            item.isDeduct = rowData.isDeduct;
                                            return item;
                                        })
                                        this.table.form.setFieldsValue({ applyLeaseList: tableOneData });
                                    }
                                }
                            }
                        },
                        editSubmit1Hide: (obj) => {
                            let index = obj.btnCallbackFn.getActiveKey();
                            if (index === "0") {
                                return true;
                            } else {
                                return false;
                            }
                        },
                        editSubmit1Click: (obj) => {
                            if (obj._formData.code7 !== 'WL') {
                                const nullRows = obj._formData.applyShopList.filter(item => !item.workTypeID || !item.workNo || !item.taxRate);
                                if (nullRows.length) {
                                    Msg.error("????????????????????????!");
                                } else {
                                    let body = {
                                        pp5: obj._formData.applyId,
                                        applyShopList: obj._formData.applyShopList
                                    };
                                    this.props.myFetch('batchAddZxCtSuppliesContrApplyShopList', body).then(
                                        ({ success, message }) => {
                                            if (success) {
                                                Msg.success(message);
                                                this.props.myFetch('getZxCtSuppliesContrApplyDetail', { applyId: obj._formData.applyId }).then(
                                                    (editObj) => {
                                                        if (editObj.success) {
                                                            this.table.form.setFieldsValue(editObj.data);
                                                            this.table.refresh();
                                                        } else {
                                                            Msg.error(editObj.message);
                                                        }
                                                    }
                                                );
                                            } else {
                                                Msg.error(message)
                                            }
                                        }
                                    );
                                }
                            } else {
                                const nullRows = obj._formData.applyLeaseList.filter(item => !item.workTypeID || !item.workNo || !item.taxRate);
                                if (nullRows.length) {
                                    Msg.error("????????????????????????!");
                                } else {
                                    let body = {
                                        pp5: obj._formData.applyId,
                                        applyLeaseList: obj._formData.applyLeaseList
                                    };
                                    this.props.myFetch('batchAddZxCtSuppliesContrApplyLeaseList', body).then(
                                        ({ success, message }) => {
                                            if (success) {
                                                Msg.success(message);
                                                this.props.myFetch('getZxCtSuppliesContrApplyDetail', { applyId: obj._formData.applyId }).then(
                                                    (editObj) => {
                                                        if (editObj.success) {
                                                            this.table.form.setFieldsValue(editObj.data);
                                                            this.table.refresh();
                                                        } else {
                                                            Msg.error(editObj.message);
                                                        }
                                                    }
                                                );
                                            } else {
                                                Msg.error(message)
                                            }
                                        }
                                    );
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
                        }

                    }}
                    componentsKey={{
                        FlowFormByMaterialManagementContract: (props) => {
                            let flowData = props?.btnCallbackFn?.getSelectedRows?.()?.[0];
                            return <FlowFormByMaterialManagementContract {...this.props} btnCallbackFn={props.qnnTableInstance} isInQnnTable={true} flowData={{ ...flowData }} />;
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
                                        field: 'applyId',
                                        type: 'string',
                                        hide: true,
                                    },
                                    {
                                        field: 'addDisabled',
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
                                        field: 'firstName',
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
                                        label: '??????',
                                        field: "contractNum",
                                        type: 'string',
                                        placeholder: '?????????',
                                        hide: true,
                                    },
                                    {
                                        label: '????????????',
                                        field: "contractNo",
                                        type: 'string',
                                        disabled: true,
                                        required:true,
                                        placeholder: '?????????',
                                        span: 12
                                    },
                                    {
                                        label: '????????????',
                                        field: "contractName",
                                        type: 'string',
                                        required: true,
                                        condition: [
                                            {
                                                regex: {
                                                    addDisabled: '1'
                                                },
                                                action: 'disabled'
                                            }
                                        ],
                                        placeholder: '?????????',
                                        span: 12
                                    },
                                    {
                                        label: '????????????',
                                        field: "contractType",
                                        type: 'string',
                                        disabled: true,
                                        initialValue: '???????????????',
                                        placeholder: '?????????',
                                        span: 12
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
                                        label: '????????????',
                                        field: "firstID",
                                        type: 'selectByPaging',
                                        optionConfig: {
                                            label: 'orgName',
                                            value: 'orgID',
                                            linkageFields: {
                                                contractNum: 'contractNo',
                                                firstName: 'orgName',
                                                orgID: 'orgID',
                                                orgName: 'orgName',
                                                comID: 'companyId',
                                                comName: 'companyName'
                                            }
                                        },
                                        onChange: (val, obj) => {
                                            let rowData = obj.form.getFieldsValue();
                                            if (val && rowData.code7) {
                                                this.props.myFetch('getZxCtSuppliesContrNoByCode', { firstID: val, code7: rowData.code7, contractNum: rowData.contractNum }).then(
                                                    ({ data, success, message }) => {
                                                        if (success) {
                                                            obj.form.setFieldsValue({
                                                                contractNo: data
                                                            })
                                                        } else {
                                                            Msg.error(message)
                                                        }
                                                    }
                                                );
                                            }
                                        },
                                        fetchConfig: {
                                            apiName: 'getZxCtContractListByStatus',
                                            otherParams: {
                                                orgID: departmentId
                                            },
                                            searchKey: 'orgName'
                                        },
                                        pageConfig: {
                                            //?????????????????????????????????
                                            limit: 10
                                        },
                                        allowClear: false,
                                        showSearch: true,
                                        required: true,
                                        condition: [
                                            {
                                                regex: { applyId: ['!', /(''|null|undefined)/ig] },
                                                action: 'disabled',
                                            }
                                        ],
                                        placeholder: '?????????',
                                        span: 12
                                    },
                                    {
                                        label: '????????????',
                                        field: 'secondName',
                                        type: 'string',
                                        required: true,
                                        detailShow: true,
                                        addShow: false,
                                        editShow: false,
                                        span: 12
                                    },
                                    {
                                        label: '????????????',
                                        field: "secondID",
                                        type: 'selectByPaging',
                                        optionConfig: {
                                            label: 'customerName',
                                            value: 'zxCrCustomerNewId',
                                            linkageFields: {
                                                secondName: 'customerName',
                                            }
                                        },
                                        fetchConfig: {
                                            apiName: 'getZxCrCustomerNewList',
                                            searchKey: 'customerName'
                                        },
                                        allowClear: false,
                                        showSearch: true,
                                        required: true,
                                        detailShow: false,
                                        addShow: true,
                                        editShow: true,
                                        condition: [
                                            {
                                                regex: {
                                                    addDisabled: '1'
                                                },
                                                action: 'disabled'
                                            }
                                        ],
                                        placeholder: '?????????',
                                        span: 12
                                    },
                                    {
                                        label: '???????????????',
                                        field: "agent",
                                        type: 'string',
                                        condition: [
                                            {
                                                regex: {
                                                    addDisabled: '1'
                                                },
                                                action: 'disabled'
                                            }
                                        ],
                                        placeholder: '?????????',
                                        span: 12
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
                                        label: '????????????',
                                        field: "isDeduct",
                                        type: 'checkbox',
                                        initialValue: '0',
                                        optionData: [
                                            {
                                                value: '1'
                                            }
                                        ],
                                        onChange: (val, obj) => {
                                            if (!val.length) {
                                                obj.form.setFieldsValue({ isDeduct: '0' })
                                            } else {
                                                obj.form.setFieldsValue({ isDeduct: '1' })
                                            }
                                        },
                                        condition: [
                                            {
                                                regex: {
                                                    addDisabled: '1'
                                                },
                                                action: 'disabled'
                                            }
                                        ],
                                        span: 12
                                    },
                                    {
                                        label: '????????????',
                                        field: "csTimeLimit",
                                        type: 'string',
                                        placeholder: '?????????',
                                        condition: [
                                            {
                                                regex: {
                                                    addDisabled: '1'
                                                },
                                                action: 'disabled'
                                            }
                                        ],
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
                                        onChange: (val, obj) => {
                                            let rowData = obj.form.getFieldsValue();
                                            if (val && rowData.firstID) {
                                                this.props.myFetch('getZxCtSuppliesContrNoByCode', { code7: val, firstID: rowData.firstID, contractNum: rowData.contractNum }).then(
                                                    ({ data, success, message }) => {
                                                        if (success) {
                                                            obj.form.setFieldsValue({
                                                                contractNo: data
                                                            })
                                                        } else {
                                                            Msg.error(message)
                                                        }
                                                    }
                                                );
                                            }
                                            if (val === 'LC') {
                                                obj.form.setFieldsValue({ materialSource: '001' });
                                            } else {
                                                obj.form.setFieldsValue({ materialSource: null });
                                            }
                                        },
                                        condition: [
                                            {
                                                regex: { applyId: ['!', /(''|null|undefined)/ig] },
                                                action: 'disabled',
                                            }
                                        ],
                                        span: 12,
                                        allowClear: false,
                                        required: true
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
                                        condition: [
                                            {
                                                regex: { code7: 'WZ' },
                                                action: ['required', 'unDisabled'],
                                            },
                                            {
                                                regex: { code7: ['!', 'WZ'] },
                                                action: ['unRequired', 'disabled'],
                                            }
                                        ],
                                        allowClear: false,
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
                                    //     onChange: (val, obj) => {
                                    //         obj.form.setFieldsValue({ shopNumber: null });
                                    //     },
                                    //     span: 12,
                                    //     required: true,
                                    //     allowClear: false,
                                    //     showSearch: true
                                    // },
                                    // {
                                    //     label: '????????????',
                                    //     field: "shopNumber",
                                    //     type: 'string',
                                    //     condition: [
                                    //         {
                                    //             regex: { shopWay: '2' },
                                    //             action: ['unRequired', 'disabled'],
                                    //         },
                                    //         {
                                    //             regex: { shopWay: ['!', '2'] },
                                    //             action: ['required', 'unDisabled'],
                                    //         }
                                    //     ],
                                    //     placeholder: '?????????',
                                    //     span: 12
                                    // },
                                    {
                                        field: 'resCategoryName',
                                        type: 'string',
                                        hide: true,
                                    },
                                    {
                                        label: '????????????',
                                        field: "resCategoryID",
                                        type: 'select',
                                        optionConfig: {
                                            label: 'catName', //?????? label
                                            value: 'id',
                                            linkageFields: {
                                                resCategoryName: 'catName',
                                            }
                                        },
                                        fetchConfig: {
                                            apiName: 'getZxSkResCategoryMaterialsList',
                                            otherParams: {
                                                parentID: '0002'
                                            }
                                        },
                                        allowClear: false,
                                        showSearch: true,
                                        condition: [
                                            {
                                                regex: {
                                                    addDisabled: '1'
                                                },
                                                action: 'disabled'
                                            }
                                        ],
                                        placeholder: '?????????',
                                        span: 12
                                    },
                                    {
                                        label: '????????????',
                                        field: 'content',
                                        type: 'textarea',
                                        placeholder: '?????????',
                                        condition: [
                                            {
                                                regex: {
                                                    addDisabled: '1'
                                                },
                                                action: 'disabled'
                                            }
                                        ],
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
                                        condition: [
                                            {
                                                regex: {
                                                    addDisabled: '1'
                                                },
                                                action: 'disabled'
                                            }
                                        ],
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
                                return ((obj?.clickCb?.rowInfo?.name === "edit" || obj?.clickCb?.rowInfo?.name === "detail") ? false : !obj?.btnCallbackFn?.form?.getFieldValue?.("applyId"))
                            },
                            content: {
                                formConfig: [
                                    {
                                        type: 'qnnTable',
                                        field: 'applyShopList',
                                        incToForm: true,
                                        dependencies: ["code7"],
                                        hide: (obj) => {
                                            if (obj?.form?.getFieldValue?.('code7') !== 'WL') {
                                                return false;
                                            } else {
                                                return true;
                                            }
                                        },
                                        qnnTableConfig: {
                                            // fetchConfig: {
                                            //     apiName: 'getZxCtSuppliesContrApplyShopListList',
                                            //     otherParams: (obj) => {
                                            //         return {
                                            //             pp5: obj?.form?.getFieldValue?.('applyId')
                                            //         }
                                            //     },
                                            //     // success: (obj) => {
                                            //     //     this.props.myFetch('getZxCtSuppliesContrApplyDetail', { applyId: obj?.form?.getFieldValue?.('applyId') }).then(
                                            //     //         (editObj) => {
                                            //     //             if (editObj.success) {
                                            //     //                 console.log(obj.form.getFieldsValue())
                                            //     //                 obj.form.setFieldsValue(editObj.data);
                                            //     //             } else {
                                            //     //                 Msg.error(editObj.message);
                                            //     //             }
                                            //     //         }
                                            //     //     );
                                            //     // }
                                            // },
                                            antd: {
                                                rowKey: 'applyShopListId',
                                                size: 'small'
                                            },
                                            paginationConfig: {
                                                position: 'bottom'
                                            },
                                            wrappedComponentRef: (me) => {
                                                this.tableOne = me;
                                            },
                                            // tableTdEdit: true,
                                            // tableTdEditFetchConfig: (obj) => {
                                            //     let editRowData = obj.qnnTableInstance.getEditedRowDataSync();
                                            //     return {
                                            //         apiName: editRowData?.addFlag ? 'addZxCtSuppliesContrApplyShopListByApplyId' : 'updateZxCtSuppliesContrApplyShopListByApplyId',
                                            //         otherParams: { ...editRowData },
                                            //         success: ({ success, message }) => {
                                            //             if (success) {
                                            //                 obj.btnCallbackFn.refresh();
                                            //                 Msg.success(message)
                                            //             } else {
                                            //                 Msg.error(message)
                                            //             }
                                            //         }
                                            //     };
                                            // },
                                            actionBtns: [
                                                {
                                                    name: "addRow",
                                                    icon: "plus",
                                                    type: "primary",
                                                    label: "?????????",
                                                    field: "addRow",
                                                    addRowDefaultData: () => {
                                                        let rowData = this.table.form.getFieldsValue();
                                                        return {
                                                            pp5: rowData?.applyId,
                                                            isDeduct: rowData.isDeduct && rowData.isDeduct.length ? rowData.isDeduct[0] : '0',
                                                            isNetPrice: '0'
                                                        }
                                                    }
                                                },
                                                {
                                                    name: 'del',
                                                    icon: 'delete',
                                                    type: 'danger',
                                                    label: '??????',
                                                    field: "deldiy"
                                                }
                                            ],
                                            formConfig: [
                                                {
                                                    isInTable: false,
                                                    form: {
                                                        label: '??????id',
                                                        field: 'applyShopListId',
                                                        hide: true
                                                    }
                                                },
                                                {
                                                    isInTable: false,
                                                    form: {
                                                        field: 'addFlag',
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
                                                        field: 'workNo',
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
                                                        dataIndex: 'workTypeID',
                                                        key: 'workTypeID',
                                                        width: 150,
                                                        tdEdit: true,
                                                        fixed: 'left',
                                                        type: 'select'
                                                    },
                                                    form: {
                                                        type: 'select',
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
                                                                editRowData.workNo = null;
                                                                editRowData.workName = null;
                                                                editRowData.spec = null;
                                                                editRowData.unit = null;
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
                                                        tdEdit: true,
                                                        fixed: 'left'
                                                    },
                                                    form: {
                                                        type: 'selectByQnnTable',
                                                        dependencies: ['workTypeID'],
                                                        dependenciesReRender: true,
                                                        optionConfig: {//??????????????????
                                                            label: 'resCode',
                                                            value: 'id'
                                                        },
                                                        dropdownMatchSelectWidth: 800,
                                                        qnnTableConfig: {
                                                            antd: {
                                                                rowKey: "id"
                                                            },
                                                            fetchConfig: {
                                                                apiName: 'getZxSkResourceMaterialsListNameJoinResource',
                                                                otherParams: (obj) => {
                                                                    let editRowData = obj.qnnFormProps?.qnnformData?.qnnFormProps?.qnnTableInstance?.getEditedRowDataSync?.();
                                                                    return {
                                                                        id: editRowData?.workTypeID,
                                                                    }

                                                                }
                                                            },
                                                            searchBtnsStyle: "inline",
                                                            formConfig: [
                                                                {
                                                                    isInForm: false,
                                                                    isInSearch: true,
                                                                    table: {
                                                                        dataIndex: "resCode",
                                                                        title: "??????",
                                                                        width: 100
                                                                    },
                                                                    form: {
                                                                        type: 'string'
                                                                    }
                                                                },
                                                                {
                                                                    isInForm: false,
                                                                    isInSearch: true,
                                                                    table: {
                                                                        dataIndex: "resName",
                                                                        title: "??????",
                                                                        width: 100
                                                                    },
                                                                    form: {
                                                                        type: 'string'
                                                                    }
                                                                },
                                                                {
                                                                    isInForm: false,
                                                                    isInSearch: true,
                                                                    table: {
                                                                        dataIndex: "spec",
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
                                                                        dataIndex: "unit",
                                                                        title: "??????",
                                                                    },
                                                                    form: {
                                                                        type: "string"
                                                                    }
                                                                }
                                                            ]
                                                        },
                                                        onChange: (val, obj, btnCallbackFn) => {
                                                            btnCallbackFn.getEditedRowData(false).then((editRowData) => {
                                                                editRowData.workID = obj.itemData.id;
                                                                editRowData.workNo = obj.itemData.resCode;
                                                                editRowData.workName = obj.itemData.resName;
                                                                editRowData.spec = obj.itemData.spec;
                                                                editRowData.unit = obj.itemData.unit;
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
                                                        width: 150
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
                                                        tdEdit: true,
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
                                                        tdEdit: true,
                                                        render: (data, rowData) => {
                                                            if (rowData.qty || rowData.qty === 0) {
                                                                return rowData.qty.toFixed(3)
                                                            }
                                                        }
                                                    },
                                                    form: {
                                                        type: 'number',
                                                        precision: 3,
                                                        onBlur: (val, obj, btnCallbackFn) => {
                                                            let data = val.target.value;
                                                            btnCallbackFn.getEditedRowData(false).then((editRowData) => {
                                                                editRowData.qty = data ? Number(data) : 0;
                                                                editRowData.price = editRowData?.price || 0;
                                                                editRowData.contractSum = Number((editRowData.qty * editRowData.price).toFixed(2));
                                                                editRowData.priceNoTax = Number((editRowData.price / ((editRowData.taxRate ? (1 + Number(editRowData.taxRate) / 100) : 1))).toFixed(6));
                                                                editRowData.contractSumNoTax = Number((editRowData.qty * editRowData.price / ((editRowData.taxRate ? (1 + Number(editRowData.taxRate) / 100) : 1))).toFixed(2));
                                                                editRowData.contractSumTax = Number((editRowData.qty * editRowData.price - editRowData.qty * editRowData.price / ((editRowData.taxRate ? (1 + Number(editRowData.taxRate) / 100) : 1))).toFixed(2));
                                                                btnCallbackFn.setEditedRowData({ ...editRowData });
                                                            })
                                                        },
                                                        placeholder: '?????????'
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '??????????????????',
                                                        dataIndex: 'price',
                                                        key: 'price',
                                                        width: 120,
                                                        tdEdit: true,
                                                        render: (data, rowData) => {
                                                            if (rowData.price || rowData.price === 0) {
                                                                return rowData.price.toFixed(6)
                                                            }
                                                        }
                                                    },
                                                    form: {
                                                        type: 'number',
                                                        precision: 6,
                                                        onBlur: (val, obj, btnCallbackFn) => {
                                                            let data = val.target.value;
                                                            btnCallbackFn.getEditedRowData(false).then((editRowData) => {
                                                                editRowData.price = data ? Number(data) : 0;
                                                                editRowData.qty = editRowData?.qty || 0;
                                                                editRowData.contractSum = Number((editRowData.qty * editRowData.price).toFixed(2));
                                                                editRowData.priceNoTax = Number((editRowData.price / ((editRowData.taxRate ? (1 + Number(editRowData.taxRate) / 100) : 1))).toFixed(6));
                                                                editRowData.contractSumNoTax = Number((editRowData.qty * editRowData.price / ((editRowData.taxRate ? (1 + Number(editRowData.taxRate) / 100) : 1))).toFixed(2));
                                                                editRowData.contractSumTax = Number((editRowData.qty * editRowData.price - editRowData.qty * editRowData.price / ((editRowData.taxRate ? (1 + Number(editRowData.taxRate) / 100) : 1))).toFixed(2));
                                                                btnCallbackFn.setEditedRowData({ ...editRowData });
                                                            })
                                                        },
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
                                                        width: 120,
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
                                                        width: 120,
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
                                                        title: `<div>??????(%)<span style='color: #ff4d4f'>*</span></div>`,
                                                        dataIndex: 'taxRate',
                                                        key: 'taxRate',
                                                        width: 100,
                                                        tdEdit: true,
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
                                                                editRowData.price = editRowData?.price || 0;
                                                                editRowData.qty = editRowData?.qty || 0;
                                                                editRowData.contractSum = Number((editRowData.qty * editRowData.price).toFixed(2));
                                                                editRowData.priceNoTax = Number((editRowData.price / ((editRowData.taxRate ? (1 + Number(editRowData.taxRate) / 100) : 1))).toFixed(6));
                                                                editRowData.contractSumNoTax = Number((editRowData.qty * editRowData.price / ((editRowData.taxRate ? (1 + Number(editRowData.taxRate) / 100) : 1))).toFixed(2));
                                                                editRowData.contractSumTax = Number((editRowData.qty * editRowData.price - editRowData.qty * editRowData.price / ((editRowData.taxRate ? (1 + Number(editRowData.taxRate) / 100) : 1))).toFixed(2));
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
                                        type: 'qnnTable',
                                        field: 'applyLeaseList',
                                        incToForm: true,
                                        dependencies: ["code7"],
                                        hide: (obj) => {
                                            if (obj?.form?.getFieldValue?.('code7') === 'WL') {
                                                return false;
                                            } else {
                                                return true;
                                            }
                                        },
                                        qnnTableConfig: {
                                            // fetchConfig: {
                                            //     apiName: 'getZxCtSuppliesContrApplyLeaseListList',
                                            //     otherParams: (obj) => {
                                            //         return {
                                            //             pp5: obj?.form?.getFieldValue?.('applyId')
                                            //         }
                                            //     }
                                            // },
                                            antd: {
                                                rowKey: 'applyLeaseListId',
                                                size: 'small'
                                            },
                                            paginationConfig: {
                                                position: 'bottom'
                                            },
                                            wrappedComponentRef: (me) => {
                                                this.tableOne = me;
                                            },
                                            // tableTdEdit: true,
                                            // tableTdEditFetchConfig: (obj) => {
                                            //     let editRowData = obj.qnnTableInstance.getEditedRowDataSync();
                                            //     return {
                                            //         apiName: editRowData?.addFlag ? 'addZxCtSuppliesContrApplyLeaseListByApplyId' : 'updateZxCtSuppliesContrApplyLeaseListByApplyId',
                                            //         otherParams: { ...editRowData },
                                            //         success: ({ success, message }) => {
                                            //             if (success) {
                                            //                 obj.btnCallbackFn.refresh()
                                            //                 Msg.success(message)
                                            //             } else {
                                            //                 Msg.error(message)
                                            //             }
                                            //         }
                                            //     };
                                            // },
                                            actionBtns: [
                                                {
                                                    name: "addRow",
                                                    icon: "plus",
                                                    type: "primary",
                                                    label: "?????????",
                                                    field: "addRow",
                                                    addRowDefaultData: () => {
                                                        let rowData = this.table.form.getFieldsValue();
                                                        return {
                                                            pp5: rowData?.applyId,
                                                            isDeduct: rowData.isDeduct && rowData.isDeduct.length ? rowData.isDeduct[0] : '0',
                                                            isNetPrice: '0'
                                                        }
                                                    }
                                                },
                                                {
                                                    name: 'del',
                                                    icon: 'delete',
                                                    type: 'danger',
                                                    label: '??????',
                                                    field: "deldiy"
                                                }
                                            ],
                                            formConfig: [
                                                {
                                                    isInTable: false,
                                                    form: {
                                                        label: '??????id',
                                                        field: 'applyLeaseListId',
                                                        hide: true
                                                    }
                                                },
                                                {
                                                    isInTable: false,
                                                    form: {
                                                        field: 'addFlag',
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
                                                        field: 'workNo',
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
                                                        dataIndex: 'workTypeID',
                                                        key: 'workTypeID',
                                                        width: 150,
                                                        tdEdit: true,
                                                        fixed: 'left',
                                                        type: 'select'
                                                    },
                                                    form: {
                                                        type: 'select',
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
                                                                editRowData.workNo = null;
                                                                editRowData.workName = null;
                                                                editRowData.spec = null;
                                                                editRowData.unit = null;
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
                                                        tdEdit: true,
                                                        fixed: 'left'
                                                    },
                                                    form: {
                                                        type: 'selectByQnnTable',
                                                        dependencies: ['workTypeID'],
                                                        dependenciesReRender: true,
                                                        optionConfig: {//??????????????????
                                                            label: 'resCode',
                                                            value: 'id'
                                                        },
                                                        dropdownMatchSelectWidth: 800,
                                                        qnnTableConfig: {
                                                            antd: {
                                                                rowKey: "id"
                                                            },
                                                            fetchConfig: {
                                                                apiName: 'getZxSkResourceMaterialsListNameJoinResource',
                                                                otherParams: (obj) => {
                                                                    let editRowData = obj.qnnFormProps?.qnnformData?.qnnFormProps?.qnnTableInstance?.getEditedRowDataSync?.();
                                                                    return {
                                                                        id: editRowData?.workTypeID,
                                                                    }

                                                                }
                                                            },
                                                            searchBtnsStyle: "inline",
                                                            formConfig: [
                                                                {
                                                                    isInForm: false,
                                                                    isInSearch: true,
                                                                    table: {
                                                                        dataIndex: "resCode",
                                                                        title: "??????",
                                                                        width: 100
                                                                    },
                                                                    form: {
                                                                        type: 'string'
                                                                    }
                                                                },
                                                                {
                                                                    isInForm: false,
                                                                    isInSearch: true,
                                                                    table: {
                                                                        dataIndex: "resName",
                                                                        title: "??????",
                                                                        width: 100
                                                                    },
                                                                    form: {
                                                                        type: 'string'
                                                                    }
                                                                },
                                                                {
                                                                    isInForm: false,
                                                                    isInSearch: true,
                                                                    table: {
                                                                        dataIndex: "spec",
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
                                                                        dataIndex: "unit",
                                                                        title: "??????",
                                                                    },
                                                                    form: {
                                                                        type: "string"
                                                                    }
                                                                }
                                                            ]
                                                        },
                                                        onChange: (val, obj, btnCallbackFn) => {
                                                            btnCallbackFn.getEditedRowData(false).then((editRowData) => {
                                                                editRowData.workID = obj.itemData.id;
                                                                editRowData.workNo = obj.itemData.resCode;
                                                                editRowData.workName = obj.itemData.resName;
                                                                editRowData.spec = obj.itemData.spec;
                                                                editRowData.unit = obj.itemData.unit;
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
                                                        width: 150
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
                                                        tdEdit: true,
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
                                                        title: '????????????',
                                                        dataIndex: 'rentUnit',
                                                        key: 'rentUnit',
                                                        width: 150,
                                                        tdEdit: true
                                                    },
                                                    form: {
                                                        type: 'string',
                                                        placeholder: '?????????'
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '??????',
                                                        dataIndex: 'contrTrrm',
                                                        key: 'contrTrrm',
                                                        width: 100,
                                                        tdEdit: true,
                                                        render: (data, rowData) => {
                                                            if (rowData.contrTrrm || rowData.contrTrrm === 0) {
                                                                return rowData.contrTrrm.toFixed(3)
                                                            }
                                                        }
                                                    },
                                                    form: {
                                                        type: 'number',
                                                        precision: 3,
                                                        onBlur: (val, obj, btnCallbackFn) => {
                                                            let data = val.target.value;
                                                            btnCallbackFn.getEditedRowData(false).then((editRowData) => {
                                                                editRowData.contrTrrm = data ? Number(data) : 0;
                                                                editRowData.qty = editRowData?.qty || 0;
                                                                editRowData.price = editRowData?.price || 0;
                                                                editRowData.contractSum = Number((editRowData.qty * editRowData.price * editRowData.contrTrrm).toFixed(2));
                                                                editRowData.priceNoTax = Number((editRowData.price / ((editRowData.taxRate ? (1 + Number(editRowData.taxRate) / 100) : 1))).toFixed(6));
                                                                editRowData.contractSumNoTax = Number((editRowData.qty * editRowData.price * editRowData.contrTrrm / ((editRowData.taxRate ? (1 + Number(editRowData.taxRate) / 100) : 1))).toFixed(2));
                                                                editRowData.contractSumTax = Number((editRowData.qty * editRowData.price * editRowData.contrTrrm - editRowData.qty * editRowData.price * editRowData.contrTrrm / ((editRowData.taxRate ? (1 + Number(editRowData.taxRate) / 100) : 1))).toFixed(2));
                                                                btnCallbackFn.setEditedRowData({ ...editRowData });
                                                            })
                                                        },
                                                        placeholder: '?????????'
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '??????',
                                                        dataIndex: 'qty',
                                                        key: 'qty',
                                                        width: 100,
                                                        tdEdit: true,
                                                        render: (data, rowData) => {
                                                            if (rowData.qty || rowData.qty === 0) {
                                                                return rowData.qty.toFixed(3)
                                                            }
                                                        }
                                                    },
                                                    form: {
                                                        type: 'number',
                                                        precision: 3,
                                                        onBlur: (val, obj, btnCallbackFn) => {
                                                            let data = val.target.value;
                                                            btnCallbackFn.getEditedRowData(false).then((editRowData) => {
                                                                editRowData.qty = data ? Number(data) : 0;
                                                                editRowData.price = editRowData?.price || 0;
                                                                editRowData.contrTrrm = editRowData?.contrTrrm || 0;
                                                                editRowData.contractSum = Number((editRowData.qty * editRowData.price * editRowData.contrTrrm).toFixed(2));
                                                                editRowData.priceNoTax = Number((editRowData.price / ((editRowData.taxRate ? (1 + Number(editRowData.taxRate) / 100) : 1))).toFixed(6));
                                                                editRowData.contractSumNoTax = Number((editRowData.qty * editRowData.price * editRowData.contrTrrm / ((editRowData.taxRate ? (1 + Number(editRowData.taxRate) / 100) : 1))).toFixed(2));
                                                                editRowData.contractSumTax = Number((editRowData.qty * editRowData.price * editRowData.contrTrrm - editRowData.qty * editRowData.price * editRowData.contrTrrm / ((editRowData.taxRate ? (1 + Number(editRowData.taxRate) / 100) : 1))).toFixed(2));
                                                                btnCallbackFn.setEditedRowData({ ...editRowData });
                                                            })
                                                        },
                                                        placeholder: '?????????'
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '??????????????????',
                                                        dataIndex: 'price',
                                                        key: 'price',
                                                        width: 120,
                                                        tdEdit: true,
                                                        render: (data, rowData) => {
                                                            if (rowData.price || rowData.price === 0) {
                                                                return rowData.price.toFixed(6)
                                                            }
                                                        }
                                                    },
                                                    form: {
                                                        type: 'number',
                                                        precision: 6,
                                                        onBlur: (val, obj, btnCallbackFn) => {
                                                            let data = val.target.value;
                                                            btnCallbackFn.getEditedRowData(false).then((editRowData) => {
                                                                editRowData.price = data ? Number(data) : 0;
                                                                editRowData.qty = editRowData?.qty || 0;
                                                                editRowData.contrTrrm = editRowData?.contrTrrm || 0;
                                                                editRowData.contractSum = Number((editRowData.qty * editRowData.price * editRowData.contrTrrm).toFixed(2));
                                                                editRowData.priceNoTax = Number((editRowData.price / ((editRowData.taxRate ? (1 + Number(editRowData.taxRate) / 100) : 1))).toFixed(6));
                                                                editRowData.contractSumNoTax = Number((editRowData.qty * editRowData.price * editRowData.contrTrrm / ((editRowData.taxRate ? (1 + Number(editRowData.taxRate) / 100) : 1))).toFixed(2));
                                                                editRowData.contractSumTax = Number((editRowData.qty * editRowData.price * editRowData.contrTrrm - editRowData.qty * editRowData.price * editRowData.contrTrrm / ((editRowData.taxRate ? (1 + Number(editRowData.taxRate) / 100) : 1))).toFixed(2));
                                                                btnCallbackFn.setEditedRowData({ ...editRowData });
                                                            })
                                                        },
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
                                                        width: 120,
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
                                                        width: 120,
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
                                                        title: `<div>??????(%)<span style='color: #ff4d4f'>*</span></div>`,
                                                        dataIndex: 'taxRate',
                                                        key: 'taxRate',
                                                        width: 100,
                                                        tdEdit: true,
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
                                                                editRowData.price = editRowData?.price || 0;
                                                                editRowData.qty = editRowData?.qty || 0;
                                                                editRowData.contrTrrm = editRowData?.contrTrrm || 0;
                                                                editRowData.contractSum = Number((editRowData.qty * editRowData.price * editRowData.contrTrrm).toFixed(2));
                                                                editRowData.priceNoTax = Number((editRowData.price / ((editRowData.taxRate ? (1 + Number(editRowData.taxRate) / 100) : 1))).toFixed(6));
                                                                editRowData.contractSumNoTax = Number((editRowData.qty * editRowData.price * editRowData.contrTrrm / ((editRowData.taxRate ? (1 + Number(editRowData.taxRate) / 100) : 1))).toFixed(2));
                                                                editRowData.contractSumTax = Number((editRowData.qty * editRowData.price * editRowData.contrTrrm - editRowData.qty * editRowData.price * editRowData.contrTrrm / ((editRowData.taxRate ? (1 + Number(editRowData.taxRate) / 100) : 1))).toFixed(2));
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
                                field: 'applyId',
                                type: 'string',
                                hide: true,
                            }
                        },
                        {
                            table: {
                                title: '????????????',
                                dataIndex: 'contractNo',
                                key: 'contractNo',
                                filter: true,
                                fixed: 'left',
                                width: 150,
                                onClick: 'detail'
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
                                width: 150,
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
                                width: 100
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
                                width: 150,
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
                                width: 150,
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
                                width: 100,
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
                                dataIndex: 'csTimeLimit',
                                key: 'csTimeLimit',
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
                                dataIndex: 'code7',
                                key: 'code7',
                                width: 100,
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
                                        itemId: 'category_contr_type_CL'
                                    }
                                },
                                allowClear: false,
                                disabled: true,
                                showSearch: true,
                                placeholder: '?????????'
                            }
                        },
                        {
                            table: {
                                title: '????????????',
                                dataIndex: 'materialSource',
                                key: 'materialSource',
                                width: 100,
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
                                        itemId: 'category_contract_materialSource'
                                    }
                                },
                                allowClear: false,
                                showSearch: true,
                                placeholder: '?????????'
                            }
                        },
                        {
                            table: {
                                title: '????????????',
                                dataIndex: 'resCategoryName',
                                key: 'resCategoryName',
                                width: 100
                            },
                            form: {
                                type: 'select',
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
                                allowClear: false,
                                showSearch: true,
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
                        }
                    ]}
                />
                {visible ? <div>
                    <Modal
                        width={'500px'}
                        style={{ paddingBottom: '0', top: '0' }}
                        title="????????????"
                        visible={visible}
                        footer={null}
                        bodyStyle={{ padding: '10px', overflow: 'hidden' }}
                        centered={true}
                        closable={false}
                        maskClosable={false}
                        wrapClassName={'replyData'}
                    >
                        <QnnForm
                            fetch={this.props.myFetch}
                            upload={this.props.myUpload} //??????????????????promise
                            //??????????????????ajax???????????????????????????????????????head?????????????????? ?????????????????????
                            headers={{
                                token: this.props.loginAndLogoutInfo.loginInfo.token
                            }}
                            data={exportData}
                            formItemLayout={{
                                labelCol: {
                                    xs: { span: 6 },
                                    sm: { span: 6 }
                                },
                                wrapperCol: {
                                    xs: { span: 18 },
                                    sm: { span: 18 }
                                }
                            }}
                            formConfig={[
                                {
                                    label: '??????',
                                    field: 'attachment',
                                    type: 'files',
                                    required: true,
                                    fetchConfig: {
                                        apiName: 'upload'
                                    }
                                },
                                {
                                    type: 'component',
                                    field: 'component',
                                    Component: obj => {
                                        return (
                                            <div style={{ textAlign: 'center' }}><Button type="primary">??????????????????</Button></div>
                                        );
                                    }
                                }
                            ]}
                            btns={[
                                {
                                    name: "cancel",
                                    type: "dashed",
                                    label: "??????",
                                    field: 'cancel',
                                    isValidate: false,
                                    onClick: (obj) => {
                                        this.setState({ visible: false });
                                    }
                                },
                                {
                                    name: "submit",
                                    type: "primary",
                                    label: "??????",
                                    field: 'submit',
                                    onClick: (obj) => {
                                        obj.btnfns.fetchByCb('importZxCtWorks', obj.values, (exportObj) => {
                                            if (exportObj.success) {
                                                Msg.success(exportObj.message);
                                                this.setState({ visible: false });
                                            } else {
                                                Msg.error(exportObj.message);
                                            }
                                        });
                                    }
                                }
                            ]}
                        />
                    </Modal>
                </div> : null}
            </div>
        );
    }
}

export default index;