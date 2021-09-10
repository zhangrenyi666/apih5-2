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
                                    Msg.error("请填写明细必填项!");
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
                                    Msg.error("请填写明细必填项!");
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
                                    Msg.error("请填写明细必填项!");
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
                                    Msg.error("请填写明细必填项!");
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
                            title: "基础信息",
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
                                        label: '编号',
                                        field: "contractNum",
                                        type: 'string',
                                        placeholder: '请输入',
                                        hide: true,
                                    },
                                    {
                                        label: '合同编号',
                                        field: "contractNo",
                                        type: 'string',
                                        disabled: true,
                                        required:true,
                                        placeholder: '请输入',
                                        span: 12
                                    },
                                    {
                                        label: '合同名称',
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
                                        placeholder: '请输入',
                                        span: 12
                                    },
                                    {
                                        label: '合同类型',
                                        field: "contractType",
                                        type: 'string',
                                        disabled: true,
                                        initialValue: '物资管理类',
                                        placeholder: '请输入',
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
                                        label: '甲方名称',
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
                                            //设置每页显示多少条数据
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
                                        placeholder: '请选择',
                                        span: 12
                                    },
                                    {
                                        label: '乙方名称',
                                        field: 'secondName',
                                        type: 'string',
                                        required: true,
                                        detailShow: true,
                                        addShow: false,
                                        editShow: false,
                                        span: 12
                                    },
                                    {
                                        label: '乙方名称',
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
                                        placeholder: '请选择',
                                        span: 12
                                    },
                                    {
                                        label: '合同签订人',
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
                                        placeholder: '请输入',
                                        span: 12
                                    },
                                    {
                                        label: '含税合同金额(万元)',
                                        field: "contractCost",
                                        type: 'number',
                                        disabled: true,
                                        initialValue: 0,
                                        precision: 6,
                                        placeholder: '请输入',
                                        span: 12
                                    },
                                    {
                                        label: '不含税合同金额(万元)',
                                        field: "contractCostNoTax",
                                        type: 'number',
                                        disabled: true,
                                        initialValue: 0,
                                        precision: 6,
                                        placeholder: '请输入',
                                        span: 12
                                    },
                                    {
                                        label: '合同税额(万元)',
                                        field: "contractCostTax",
                                        type: 'number',
                                        disabled: true,
                                        initialValue: 0,
                                        precision: 6,
                                        placeholder: '请输入',
                                        span: 12
                                    },
                                    {
                                        label: '是否抵扣',
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
                                        label: '合同工期',
                                        field: "csTimeLimit",
                                        type: 'string',
                                        placeholder: '请输入',
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
                                        label: '合同类别',
                                        field: "code7",
                                        type: 'select',
                                        optionConfig: {
                                            label: 'itemName', //默认 label
                                            value: 'itemId'
                                        },
                                        fetchConfig: {
                                            apiName: 'getBaseCodeSelect',
                                            otherParams: {
                                                itemId: 'category_contr_type_CL'
                                            }
                                        },
                                        placeholder: '请选择',
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
                                        label: '物资来源',
                                        field: "materialSource",
                                        type: 'select',
                                        optionConfig: {
                                            label: 'itemName', //默认 label
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
                                        placeholder: '请选择',
                                        span: 12
                                    },
                                    // {
                                    //     label: '采购方式',
                                    //     field: "shopWay",
                                    //     type: 'select',
                                    //     optionConfig: {
                                    //         label: 'label', //默认 label
                                    //         value: 'value'
                                    //     },
                                    //     optionData: [
                                    //         {
                                    //             label: '招标采购',
                                    //             value: '0'
                                    //         },
                                    //         {
                                    //             label: '云电商采购',
                                    //             value: '1'
                                    //         },
                                    //         {
                                    //             label: '其他采购',
                                    //             value: '2'
                                    //         }
                                    //     ],
                                    //     placeholder: '请选择',
                                    //     onChange: (val, obj) => {
                                    //         obj.form.setFieldsValue({ shopNumber: null });
                                    //     },
                                    //     span: 12,
                                    //     required: true,
                                    //     allowClear: false,
                                    //     showSearch: true
                                    // },
                                    // {
                                    //     label: '采购编号',
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
                                    //     placeholder: '请输入',
                                    //     span: 12
                                    // },
                                    {
                                        field: 'resCategoryName',
                                        type: 'string',
                                        hide: true,
                                    },
                                    {
                                        label: '物资类别',
                                        field: "resCategoryID",
                                        type: 'select',
                                        optionConfig: {
                                            label: 'catName', //默认 label
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
                                        placeholder: '请选择',
                                        span: 12
                                    },
                                    {
                                        label: '合同内容',
                                        field: 'content',
                                        type: 'textarea',
                                        placeholder: '请输入',
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
                                        label: '备注',
                                        field: 'remarks',
                                        type: 'textarea',
                                        placeholder: '请输入',
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
                            title: "清单",
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
                                                    label: "新增行",
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
                                                    label: '删除',
                                                    field: "deldiy"
                                                }
                                            ],
                                            formConfig: [
                                                {
                                                    isInTable: false,
                                                    form: {
                                                        label: '主键id',
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
                                                        title: `<div>物资类别<span style='color: #ff4d4f'>*</span></div>`,
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
                                                            label: 'catName', //默认 label
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
                                                        placeholder: '请选择'
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: `<div>物资编码<span style='color: #ff4d4f'>*</span></div>`,
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
                                                        optionConfig: {//下拉选项配置
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
                                                                        title: "编号",
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
                                                                        title: "名称",
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
                                                                        title: "规格型号",
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
                                                                        title: "单位",
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
                                                        placeholder: '请选择'
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '物资名称',
                                                        dataIndex: 'workName',
                                                        key: 'workName',
                                                        width: 150
                                                    },
                                                    form: {
                                                        type: 'string',
                                                        placeholder: '请输入'
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '物资规格',
                                                        dataIndex: 'spec',
                                                        key: 'spec',
                                                        width: 100
                                                    },
                                                    form: {
                                                        type: 'string',
                                                        placeholder: '请输入'
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '单位',
                                                        dataIndex: 'unit',
                                                        key: 'unit',
                                                        width: 100
                                                    },
                                                    form: {
                                                        type: 'string',
                                                        placeholder: '请输入'
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '是否网价',
                                                        dataIndex: 'isNetPrice',
                                                        key: 'isNetPrice',
                                                        width: 100,
                                                        tdEdit: true,
                                                        render: (data) => {
                                                            if (data === '1') {
                                                                return '是';
                                                            } else {
                                                                return '否';
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
                                                        title: '数量',
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
                                                        placeholder: '请输入'
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '含税合同单价',
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
                                                        placeholder: '请输入'
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '含税合同金额',
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
                                                        placeholder: '请输入'
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '不含税合同单价',
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
                                                        placeholder: '请输入'
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '不含税合同金额',
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
                                                        placeholder: '请输入'
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '税额',
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
                                                        placeholder: '请输入'
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: `<div>税率(%)<span style='color: #ff4d4f'>*</span></div>`,
                                                        dataIndex: 'taxRate',
                                                        key: 'taxRate',
                                                        width: 100,
                                                        tdEdit: true,
                                                        type: 'select'
                                                    },
                                                    form: {
                                                        type: 'select',
                                                        optionConfig: {
                                                            label: 'itemName', //默认 label
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
                                                        placeholder: '请选择'
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '是否抵扣',
                                                        dataIndex: 'isDeduct',
                                                        key: 'isDeduct',
                                                        width: 100,
                                                        render: (data) => {
                                                            if (data === '1') {
                                                                return '是';
                                                            } else {
                                                                return '否';
                                                            }
                                                        }
                                                    },
                                                    form: {
                                                        type: 'string',
                                                        placeholder: '请输入'
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '备注',
                                                        dataIndex: 'remarks',
                                                        key: 'remarks',
                                                        width: 100,
                                                        tdEdit: true
                                                    },
                                                    form: {
                                                        type: 'string',
                                                        placeholder: '请输入'
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
                                                    label: "新增行",
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
                                                    label: '删除',
                                                    field: "deldiy"
                                                }
                                            ],
                                            formConfig: [
                                                {
                                                    isInTable: false,
                                                    form: {
                                                        label: '主键id',
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
                                                        title: `<div>物资类别<span style='color: #ff4d4f'>*</span></div>`,
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
                                                            label: 'catName', //默认 label
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
                                                        placeholder: '请选择'
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: `<div>物资编码<span style='color: #ff4d4f'>*</span></div>`,
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
                                                        optionConfig: {//下拉选项配置
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
                                                                        title: "编号",
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
                                                                        title: "名称",
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
                                                                        title: "规格型号",
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
                                                                        title: "单位",
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
                                                        placeholder: '请选择'
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '物资名称',
                                                        dataIndex: 'workName',
                                                        key: 'workName',
                                                        width: 150
                                                    },
                                                    form: {
                                                        type: 'string',
                                                        placeholder: '请输入'
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '物资规格',
                                                        dataIndex: 'spec',
                                                        key: 'spec',
                                                        width: 100
                                                    },
                                                    form: {
                                                        type: 'string',
                                                        placeholder: '请输入'
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '单位',
                                                        dataIndex: 'unit',
                                                        key: 'unit',
                                                        width: 100
                                                    },
                                                    form: {
                                                        type: 'string',
                                                        placeholder: '请输入'
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '是否网价',
                                                        dataIndex: 'isNetPrice',
                                                        key: 'isNetPrice',
                                                        width: 100,
                                                        tdEdit: true,
                                                        render: (data) => {
                                                            if (data === '1') {
                                                                return '是';
                                                            } else {
                                                                return '否';
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
                                                        title: '租期单位',
                                                        dataIndex: 'rentUnit',
                                                        key: 'rentUnit',
                                                        width: 150,
                                                        tdEdit: true
                                                    },
                                                    form: {
                                                        type: 'string',
                                                        placeholder: '请输入'
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '租期',
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
                                                        placeholder: '请输入'
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '数量',
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
                                                        placeholder: '请输入'
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '含税合同单价',
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
                                                        placeholder: '请输入'
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '含税合同金额',
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
                                                        placeholder: '请输入'
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '不含税合同单价',
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
                                                        placeholder: '请输入'
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '不含税合同金额',
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
                                                        placeholder: '请输入'
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '税额',
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
                                                        placeholder: '请输入'
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: `<div>税率(%)<span style='color: #ff4d4f'>*</span></div>`,
                                                        dataIndex: 'taxRate',
                                                        key: 'taxRate',
                                                        width: 100,
                                                        tdEdit: true,
                                                        type: 'select'
                                                    },
                                                    form: {
                                                        type: 'select',
                                                        optionConfig: {
                                                            label: 'itemName', //默认 label
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
                                                        placeholder: '请选择'
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '是否抵扣',
                                                        dataIndex: 'isDeduct',
                                                        key: 'isDeduct',
                                                        width: 100,
                                                        render: (data) => {
                                                            if (data === '1') {
                                                                return '是';
                                                            } else {
                                                                return '否';
                                                            }
                                                        }
                                                    },
                                                    form: {
                                                        type: 'string',
                                                        placeholder: '请输入'
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '备注',
                                                        dataIndex: 'remarks',
                                                        key: 'remarks',
                                                        width: 100,
                                                        tdEdit: true
                                                    },
                                                    form: {
                                                        type: 'string',
                                                        placeholder: '请输入'
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
                                title: '合同编号',
                                dataIndex: 'contractNo',
                                key: 'contractNo',
                                filter: true,
                                fixed: 'left',
                                width: 150,
                                onClick: 'detail'
                            },
                            form: {
                                type: 'string',
                                placeholder: '请输入'
                            }
                        },
                        {
                            table: {
                                title: '合同名称',
                                dataIndex: 'contractName',
                                key: 'contractName',
                                filter: true,
                                fixed: 'left',
                                width: 150,
                            },
                            form: {
                                type: 'string',
                                placeholder: '请输入'
                            }
                        },
                        {
                            table: {
                                title: '合同类型',
                                dataIndex: 'contractType',
                                key: 'contractType',
                                width: 100
                            },
                            form: {
                                type: 'string',
                                initialValue: '物资管理类',
                                placeholder: '请输入'
                            }
                        },
                        {
                            table: {
                                title: '甲方名称',
                                dataIndex: 'firstName',
                                key: 'firstName',
                                width: 150,
                                filter: true
                            },
                            form: {
                                type: 'string',
                                placeholder: '请输入'
                            }
                        },
                        {
                            table: {
                                title: '乙方名称',
                                dataIndex: 'secondName',
                                key: 'secondName',
                                width: 150,
                                filter: true
                            },
                            form: {
                                type: 'string',
                                placeholder: '请输入'
                            }
                        },
                        {
                            table: {
                                title: '合同签订人',
                                dataIndex: 'agent',
                                key: 'agent',
                                width: 100,
                            },
                            form: {
                                type: 'string',
                                placeholder: '请输入'
                            }
                        },
                        {
                            table: {
                                title: '含税合同金额(万元)',
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
                                placeholder: '请输入'
                            }
                        },
                        {
                            table: {
                                title: '是否抵扣',
                                dataIndex: 'isDeduct',
                                key: 'isDeduct',
                                width: 100,
                                render: (data) => {
                                    if (data === '1') {
                                        return '是';
                                    } else {
                                        return '否';
                                    }
                                }
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '合同工期',
                                dataIndex: 'csTimeLimit',
                                key: 'csTimeLimit',
                                width: 100
                            },
                            form: {
                                type: 'string',
                                placeholder: '请输入'
                            }
                        },
                        {
                            table: {
                                title: '合同类别',
                                dataIndex: 'code7',
                                key: 'code7',
                                width: 100,
                                type: 'select'
                            },
                            form: {
                                type: 'select',
                                optionConfig: {
                                    label: 'itemName', //默认 label
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
                                placeholder: '请选择'
                            }
                        },
                        {
                            table: {
                                title: '物资来源',
                                dataIndex: 'materialSource',
                                key: 'materialSource',
                                width: 100,
                                type: 'select'
                            },
                            form: {
                                type: 'select',
                                optionConfig: {
                                    label: 'itemName', //默认 label
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
                                placeholder: '请选择'
                            }
                        },
                        {
                            table: {
                                title: '物资类别',
                                dataIndex: 'resCategoryName',
                                key: 'resCategoryName',
                                width: 100
                            },
                            form: {
                                type: 'select',
                                optionConfig: {
                                    label: 'catName', //默认 label
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
                                placeholder: '请选择'
                            }
                        },
                        {
                            table: {
                                title: '发起人',
                                dataIndex: 'beginPer',
                                key: 'beginPer',
                                width: 100
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '评审状态',
                                dataIndex: 'apih5FlowStatus',
                                key: 'apih5FlowStatus',
                                width: 100,
                                fixed: 'right',
                                render: (data) => {
                                    if (data === '-1') {
                                        return '未审核';
                                    } else if (data === '0') {
                                        return '待提交';
                                    } else if (data === '1') {
                                        return '审核中';
                                    } else if (data === '2') {
                                        return '审核完成';
                                    } else if (data === '3') {
                                        return '审核不通过';
                                    } else if (data === '-9') {
                                        return '审核通过';
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
                        title="清单导入"
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
                            upload={this.props.myUpload} //必须返回一个promise
                            //内部自带发送ajax的组件发送请求时会自动加到head头里面的数据 （上传会用到）
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
                                    label: '附件',
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
                                            <div style={{ textAlign: 'center' }}><Button type="primary">导入模板下载</Button></div>
                                        );
                                    }
                                }
                            ]}
                            btns={[
                                {
                                    name: "cancel",
                                    type: "dashed",
                                    label: "取消",
                                    field: 'cancel',
                                    isValidate: false,
                                    onClick: (obj) => {
                                        this.setState({ visible: false });
                                    }
                                },
                                {
                                    name: "submit",
                                    type: "primary",
                                    label: "确定",
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