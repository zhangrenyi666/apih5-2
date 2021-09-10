import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import QnnForm from '../../modules/qnn-table/qnn-form';
import { message as Msg, Modal, Button } from 'antd';
import s from './style.less';
import moment from 'moment';
import FlowFormByMaterialManagementContract from './form';
// import { debounce } from "lodash";
import Operation from './operation';
const confirm = Modal.confirm;
const config = {
    antd: {
        rowKey: function (row) {
            return row.applyId
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
        this.state = {
            tableOneID: '',
            exportData: null,
            visible: false
        }
    }
    render() {
        const { realName } = this.props.loginAndLogoutInfo.loginInfo.userInfo;
        const { departmentId } = this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany;
        const { visible, exportData } = this.state;
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
                    actionBtns={[
                        {
                            name: 'add',//内置add del
                            icon: 'plus',//icon
                            type: 'primary',//类型  默认 primary  [primary dashed danger]
                            label: '新增',
                            drawerTitle: '新增',
                            willExecute: (obj) => {
                                obj.btnCallbackFn.setBtnsDisabled('remove', 'diySubmit');
                                this.setState({
                                    tableOneID: ''
                                })
                            },
                            formBtns: [
                                {
                                    name: 'cancel', //关闭右边抽屉
                                    type: 'dashed',//类型  默认 primary
                                    label: '取消',
                                    hide: (obj) => {
                                        let index = obj.btnCallbackFn.getActiveKey();
                                        if (index === "1") {
                                            return true;
                                        } else {
                                            return false;
                                        }
                                    },
                                },
                                {
                                    name: 'diySubmit',
                                    field: 'diySubmit',
                                    type: 'primary',
                                    label: '保存',
                                    hide: (obj) => {
                                        let index = obj.btnCallbackFn.getActiveKey();
                                        if (index === "1") {
                                            return true;
                                        } else {
                                            return false;
                                        }
                                    },
                                    onClick: (obj) => {
                                        obj.btnCallbackFn.setBtnsLoading('add', 'diySubmit');
                                        this.props.myFetch('addZxCtSuppliesContrApply', obj._formData).then(
                                            ({ data, success, message }) => {
                                                if (success) {
                                                    obj.btnCallbackFn.setBtnsDisabled('add', 'diySubmit');
                                                    obj.btnCallbackFn.setBtnsLoading('remove', 'diySubmit');
                                                    Msg.success(message);
                                                    obj.btnCallbackFn.form.setFieldsValue(data);
                                                    obj.btnCallbackFn.setActiveKey('1');
                                                } else {
                                                    obj.btnCallbackFn.setBtnsLoading('remove', 'diySubmit');
                                                    Msg.error(message);
                                                }
                                            }
                                        );
                                    }
                                }
                            ]
                        },
                        {
                            name: 'edit',//内置add del
                            icon: 'edit',//icon
                            type: 'primary',//类型  默认 primary  [primary dashed danger]
                            label: '修改',
                            drawerTitle: '修改',
                            disabled: (obj) => {
                                let data = obj.btnCallbackFn.getSelectedRows();
                                if (data.length === 1 && (!data[0].apih5FlowStatus || data[0].apih5FlowStatus === "-1" || data[0].apih5FlowStatus === "0")) {
                                    // && data[0].apih5FlowStatus === "-1" || data[0].apih5FlowStatus === "0"
                                    return false;
                                } else {
                                    return true;
                                }
                            },
                            willExecute: (obj) => {
                                obj.btnCallbackFn.setBtnsDisabled('remove', 'diySubmit');
                                this.setState({
                                    tableOneID: ''
                                })
                            },
                            formBtns: [
                                {
                                    name: 'cancel',
                                    type: 'dashed',//类型  默认 primary
                                    label: '取消',
                                    hide: (obj) => {
                                        let index = obj.btnCallbackFn.getActiveKey();
                                        if (index === "1") {
                                            return true;
                                        } else {
                                            return false;
                                        }
                                    },
                                },
                                {
                                    name: 'diySubmit',
                                    field: 'diySubmit',
                                    type: 'primary',
                                    label: '保存',
                                    hide: (obj) => {
                                        let index = obj.btnCallbackFn.getActiveKey();
                                        if (index === "1") {
                                            return true;
                                        } else {
                                            return false;
                                        }
                                    },
                                    onClick: (obj) => {
                                        obj.btnCallbackFn.setBtnsLoading('add', 'diySubmit');
                                        this.props.myFetch('updateZxCtSuppliesContrApply', obj._formData).then(
                                            ({ data, success, message }) => {
                                                if (success) {
                                                    obj.btnCallbackFn.setBtnsLoading('remove', 'diySubmit');
                                                    Msg.success(message);
                                                    obj.btnCallbackFn.setActiveKey('1');
                                                } else {
                                                    obj.btnCallbackFn.setBtnsLoading('remove', 'diySubmit');
                                                    Msg.error(message);
                                                }
                                            }
                                        );
                                    }
                                }
                            ]
                        },
                        {
                            name: 'Component',
                            type: 'primary',
                            label: '发起评审',
                            drawerTitle: '发起评审',
                            disabled: (obj) => {
                                let data = obj.btnCallbackFn.getSelectedRows();
                                if (data.length === 1 && !data[0].workId) {
                                    // && data[0].apih5FlowStatus === "-1" || data[0].apih5FlowStatus === "0"
                                    return false;
                                } else {
                                    return true;
                                }
                            },
                            // disabled: (obj) => {
                            //     let data = obj.btnCallbackFn.getSelectedRows();
                            //     if (data.length === 1 && !data[0].workId) {
                            //         return false;
                            //     } else {
                            //         return true;
                            //     }
                            // },
                            Component: (props) => {
                                return <FlowFormByMaterialManagementContract {...this.props} btnCallbackFn={props.btnCallbackFn} isInQnnTable={props.isInQnnTable} flowData={props.selectedRows[0]} />;
                            }
                        },
                        {
                            name: 'Component',
                            type: 'primary',
                            label: '进度查询',
                            drawerTitle: '进度查询',
                            disabled: (obj) => {
                                let data = obj.btnCallbackFn.getSelectedRows();
                                if (data.length === 1 && data[0].workId) {
                                    return false;
                                } else {
                                    return true;
                                }
                            },
                            Component: (props) => {
                                return <Operation apiName={'openFlowByReport'} {...props} />
                            }
                        },
                        {
                            name: 'del',//内置add del
                            icon: 'delete',//icon
                            type: 'danger',//类型  默认 primary  [primary dashed danger]
                            label: '删除',
                            disabled: (obj) => {
                                let data = obj.btnCallbackFn.getSelectedRows();
                                if (data.length === 1 && (!data[0].apih5FlowStatus || data[0].apih5FlowStatus === "-1" || data[0].apih5FlowStatus === "0")) {
                                    // && data[0].apih5FlowStatus === "-1" || data[0].apih5FlowStatus === "0"
                                    return false;
                                } else {
                                    return true;
                                }
                            },
                            // disabled: (obj) => {
                            //     let data = obj.btnCallbackFn.getSelectedRows();
                            //     if (!data.filter(item => item.workId).length) {
                            //         return false;
                            //     } else {
                            //         return true;
                            //     }
                            // },
                            fetchConfig: {//ajax配置
                                apiName: 'batchDeleteUpdateZxCtSuppliesContrApplyFlow',
                            },
                        }
                    ]}
                    drawerShowToggle={(obj) => {
                        let { drawerIsShow } = obj;
                        if (!drawerIsShow) {
                            obj.btnCallbackFn.refresh();
                            obj.btnCallbackFn.clearSelectedRows();
                        }
                    }}
                    tabsWillChange={(obj, canChange) => {
                        if (this.tableOne) {
                            this.tableOne.refresh();
                        }
                        canChange(true);
                    }}
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
                                        placeholder: '请输入',
                                        span: 12
                                    },
                                    {
                                        label: '合同名称',
                                        field: "contractName",
                                        type: 'string',
                                        required: true,
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
                                        detailShow:true,
                                        addShow:false,
                                        editShow:false,
                                        span: 12
                                    },
                                    {
                                        label: '乙方名称',
                                        field: "secondID",
                                        type: 'selectByPaging',
                                        optionConfig: {
                                            label:'customerName',
                                            value: 'zxCrCustomerNewId',
                                            linkageFields: {
                                                secondName: 'customerName',
                                            }
                                        },
                                        fetchConfig: {
                                            apiName: 'getZxCrCustomerNewList',
                                            searchKey: 'customerName'
                                        },
                                        showSizeChanger: false,
                                        allowClear: false,
                                        showSearch: true,
                                        required: true,
                                        detailShow:false,
                                        addShow:true,
                                        editShow:true,
                                        placeholder: '请选择',
                                        span: 12
                                    },
                                    {
                                        label: '合同签订人',
                                        field: "agent",
                                        type: 'string',
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
                                            }
                                        },
                                        span: 12
                                    },
                                    {
                                        label: '合同工期',
                                        field: "csTimeLimit",
                                        type: 'string',
                                        placeholder: '请输入',
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
                                            // let firstID = obj.form.getFieldValue('firstID')
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
                                        showSearch: true,
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
                                        showSearch: true,
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
                                        placeholder: '请选择',
                                        span: 12
                                    },
                                    {
                                        label: '合同内容',
                                        field: 'content',
                                        type: 'textarea',
                                        placeholder: '请输入',
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
                            field: "table1",
                            name: "qnnTable",
                            title: "清单",
                            disabled: (obj) => {
                                return ((obj.clickCb.rowInfo.name === "edit" || obj.clickCb.rowInfo.name === "detail") ? false : !obj.btnCallbackFn.form.getFieldValue("applyId"))
                            },
                            content: {
                                tabs: [],
                                fetchConfig: (obj) => {
                                    if (obj.tableFns.qnnForm.form.getFieldValue('applyId')) {
                                        if (obj.tableFns.qnnForm.form.getFieldValue('code7') !== 'WL') {
                                            return {
                                                apiName: 'getZxCtSuppliesContrApplyShopListList',
                                                otherParams: {
                                                    pp5: obj.tableFns.qnnForm.form.getFieldValue('applyId')
                                                },
                                                success: () => {
                                                    this.props.myFetch('getZxCtSuppliesContrApplyDetail', { applyId: obj.tableFns.qnnForm.form.getFieldValue('applyId') }).then(
                                                        (editObj) => {
                                                            if (editObj.success) {
                                                                obj.tableFns.qnnForm.form.setFieldsValue(editObj.data);
                                                            } else {
                                                                Msg.error(editObj.message);
                                                            }
                                                        }
                                                    );
                                                }
                                            }
                                        } else {
                                            return {
                                                apiName: 'getZxCtSuppliesContrApplyLeaseListList',
                                                otherParams: {
                                                    pp5: obj.tableFns.qnnForm.form.getFieldValue('applyId')
                                                },
                                                success: () => {
                                                    this.props.myFetch('getZxCtSuppliesContrApplyDetail', { applyId: obj.tableFns.qnnForm.form.getFieldValue('applyId') }).then(
                                                        (editObj) => {
                                                            if (editObj.success) {
                                                                obj.tableFns.qnnForm.form.setFieldsValue(editObj.data);
                                                            } else {
                                                                Msg.error(editObj.message);
                                                            }
                                                        }
                                                    );
                                                }
                                            }
                                        }
                                    } else {
                                        return {};
                                    }
                                },
                                wrappedComponentRef: (me) => {
                                    this.tableOne = me;
                                },
                                antd: {
                                    size: "small",
                                    rowKey: (rowData) => {
                                        return rowData.applyLeaseListId ? rowData.applyLeaseListId : rowData.applyShopListId;
                                    }
                                },
                                rowDisabledCondition: (rowData) => {
                                    if (rowData.applyLeaseListId) {
                                        return rowData.applyLeaseListId !== this.state.tableOneID
                                    } else if (rowData.applyShopListId) {
                                        return rowData.applyShopListId !== this.state.tableOneID
                                    }
                                },
                                paginationConfig: false,
                                isShowRowSelect: true,
                                actionBtns: [
                                    {
                                        name: 'addRow',
                                        icon: 'plus',
                                        type: 'primary',
                                        label: '新增',
                                        hide: (obj) => {
                                            if (obj.props.clickCb.rowInfo.name === 'detail') {
                                                return true;
                                            } else {
                                                return false;
                                            }
                                        },
                                        addCb: (obj) => {
                                            if (this.state.tableOneID) {
                                                Msg.error('有数据未保存,请保存后新增!');
                                            } else {
                                                let oneData = this.tableOne.state.data;
                                                let rowData = obj.props.tableFns.qnnForm.form.getFieldsValue();
                                                let body = rowData.code7 !== 'WL' ? {
                                                    applyShopListId: moment(new Date()).valueOf(),
                                                    pp5: rowData.applyId,
                                                    isDeduct: rowData.isDeduct && rowData.isDeduct.length ? rowData.isDeduct[0] : '0',
                                                    isNetPrice: '0'
                                                } : {
                                                        applyLeaseListId: moment(new Date()).valueOf(),
                                                        pp5: rowData.applyId,
                                                        isDeduct: rowData.isDeduct && rowData.isDeduct.length ? rowData.isDeduct[0] : '0',
                                                        isNetPrice: '0'
                                                    };
                                                this.setState({
                                                    tableOneID: rowData.code7 !== 'WL' ? body.applyShopListId : body.applyLeaseListId
                                                }, () => {
                                                    this.tableOne.btnCallbackFn.setState({
                                                        data: [body].concat(oneData)
                                                    })
                                                })
                                            }
                                        },
                                    },
                                    {
                                        name: 'export',
                                        type: 'primary',
                                        label: '导入',
                                        hide: (obj) => {
                                            if (obj.props.clickCb.rowInfo.name === 'detail') {
                                                return true;
                                            } else {
                                                return false;
                                            }
                                        },
                                        onClick: (obj) => {
                                            let rowData = obj.props.tableFns.qnnForm.form.getFieldsValue();
                                            this.setState({
                                                visible: true,
                                                exportData: rowData
                                            })
                                        }
                                    },
                                    {
                                        name: 'diydel',
                                        icon: 'del',
                                        type: 'danger',
                                        label: '删除',
                                        hide: (obj) => {
                                            if (obj.props.clickCb.rowInfo.name === 'detail') {
                                                return true;
                                            } else {
                                                return false;
                                            }
                                        },
                                        disabled: (obj) => {
                                            if (obj.btnCallbackFn.getSelectedRows().length) {
                                                return false;
                                            } else {
                                                return true;
                                            }
                                        },
                                        onClick: (obj) => {
                                            confirm({
                                                content: '确定删除选中的数据吗?',
                                                onOk: () => {
                                                    if (this.state.tableOneID) {
                                                        Msg.error('有数据未保存,请保存后删除!');
                                                    } else {
                                                        let rowData = obj.props.tableFns.qnnForm.form.getFieldsValue();
                                                        this.props.myFetch(rowData.code7 !== 'WL' ? 'batchDeleteZxCtSuppliesContrApplyShopListByApplyId' : 'batchDeleteUpdateZxCtSuppliesContrApplyLeaseList', obj.selectedRows).then(
                                                            ({ success, message }) => {
                                                                if (success) {
                                                                    Msg.success(message);
                                                                    obj.btnCallbackFn.clearSelectedRows();
                                                                    obj.btnCallbackFn.refresh();
                                                                } else {
                                                                    Msg.error(message);
                                                                }
                                                            }
                                                        );
                                                    }
                                                }
                                            })
                                        }
                                    }
                                ],
                                formConfig: [
                                    {
                                        isInTable: false,
                                        form: {
                                            field: 'applyLeaseListId',
                                            type: 'string',
                                            hide: true,
                                        }
                                    },
                                    {
                                        isInTable: false,
                                        form: {
                                            field: 'applyShopListId',
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
                                            title: `<div>物资类别<span style='color: #ff4d4f'>*</span></div>`,
                                            dataIndex: 'workTypeID',
                                            key: 'workTypeID',
                                            width: 150,
                                            type: 'select',
                                            tdEdit: true,
                                            fixed: 'left'
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
                                                    let tableOneData = this.tableOne.state.data.map((item) => {
                                                        if ((item.applyShopListId || item.applyLeaseListId) === this.state.tableOneID) {
                                                            item.workTypeID = obj.itemData.id;
                                                            item.workType = obj.itemData.catName;
                                                            item.workNo = null;
                                                            item.workName = null;
                                                            item.spec = null;
                                                            item.unit = null;
                                                            item.isDeduct = editRowData.isDeduct;
                                                            item.remarks = editRowData.remarks;
                                                            item.isNetPrice = editRowData.isNetPrice;
                                                            if (this.table.btnCallbackFn.form.getFieldValue('code7') === 'WL') {
                                                                item.rentUnit = editRowData.rentUnit;
                                                            }
                                                        }
                                                        return item;
                                                    })
                                                    this.tableOne.btnCallbackFn.setState({
                                                        data: tableOneData
                                                    })
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
                                            // type: 'select',
                                            tdEdit: true,
                                            fixed: 'left',
                                            render: (data, rowData) => {
                                                return rowData.workNo;
                                            }
                                        },
                                        form: {
                                            type: 'selectByQnnTable',
                                            optionConfig: {//下拉选项配置
                                                label: 'resCode',
                                                value: 'resCode'
                                            },
                                            dropdownMatchSelectWidth: 800,
                                            qnnTableConfig: {
                                                antd: {
                                                    rowKey: "id"
                                                },
                                                fetchConfig: {
                                                    apiName: 'getZxSkResourceMaterialsListNameJoinResource',
                                                    otherParams: (obj) => {
                                                        return {
                                                            id: obj.qnnFormProps?.qnnformData?.qnnFormProps?.rowData?.workTypeID,
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
                                                    let tableOneData = this.tableOne.state.data.map((item) => {
                                                        if ((item.applyShopListId || item.applyLeaseListId) === this.state.tableOneID) {
                                                            item.workID = obj.itemData[0].id;
                                                            item.workNo = obj.itemData[0].resCode;
                                                            item.workName = obj.itemData[0].resName;
                                                            item.spec = obj.itemData[0].spec;
                                                            item.unit = obj.itemData[0].unit;
                                                            item.isDeduct = editRowData.isDeduct;
                                                            item.remarks = editRowData.remarks;
                                                            item.isNetPrice = editRowData.isNetPrice;
                                                            if (this.table.btnCallbackFn.form.getFieldValue('code7') === 'WL') {
                                                                item.rentUnit = editRowData.rentUnit;
                                                            }
                                                        }
                                                        return item;
                                                    })
                                                    this.tableOne.btnCallbackFn.setState({
                                                        data: tableOneData
                                                    })
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
                                        isInTable: (obj) => {
                                            if (obj.tableFns.qnnForm.form.getFieldValue('code7') === 'WL') {
                                                return true;
                                            } else {
                                                return false;
                                            }
                                        },
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
                                        isInTable: (obj) => {
                                            if (obj.tableFns.qnnForm.form.getFieldValue('code7') === 'WL') {
                                                return true;
                                            } else {
                                                return false;
                                            }
                                        },
                                        table: {
                                            title: '租期',
                                            dataIndex: 'contrTrrm',
                                            key: 'contrTrrm',
                                            width: 100,
                                            tdEdit: true
                                        },
                                        form: {
                                            type: 'number',
                                            onBlur: (obj) => {
                                                let val = obj.target.value;
                                                this.tableOne.getEditedRowData(false).then((editRowData) => {
                                                    let tableOneData = this.tableOne.state.data.map((item) => {
                                                        if ((item.applyShopListId || item.applyLeaseListId) === this.state.tableOneID) {
                                                            if (Number(editRowData.contrTrrm) !== Number(item.contrTrrm)) {
                                                                item.qty = item.qty ? item.qty : 0;
                                                                item.price = item.price ? item.price : 0;
                                                                item.contrTrrm = val ? Number(val) : 0;
                                                                item.contractSum = (item.qty ? item.qty : 0) * (item.price ? item.price : 0) * (val ? Number(val) : 0);
                                                                item.priceNoTax = (item.price ? item.price : 0) / ((item.taxRate ? (1 + Number(item.taxRate) / 100) : 1));
                                                                item.contractSumNoTax = (item.qty ? item.qty : 0) * (item.price ? item.price : 0) * (val ? Number(val) : 0) / ((item.taxRate ? (1 + Number(item.taxRate) / 100) : 1));
                                                                item.contractSumTax = (item.qty ? item.qty : 0) * (item.price ? item.price : 0) * (val ? Number(val) : 0) - (item.qty ? item.qty : 0) * (item.price ? item.price : 0) * (val ? Number(val) : 0) / ((item.taxRate ? (1 + Number(item.taxRate) / 100) : 1));
                                                                item.remarks = editRowData.remarks;
                                                                item.isDeduct = editRowData.isDeduct;
                                                                item.isNetPrice = editRowData.isNetPrice;
                                                                item.rentUnit = editRowData.rentUnit;
                                                            } else {
                                                                item = null;
                                                            }
                                                        }
                                                        return item;
                                                    })
                                                    if (!tableOneData.filter(item => item === null).length) {
                                                        setTimeout(() => {
                                                            this.tableOne.btnCallbackFn.setState({
                                                                data: tableOneData
                                                            })
                                                        }, 0)
                                                    }
                                                });
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
                                                    return rowData.qty.toFixed(2)
                                                }
                                            }
                                        },
                                        form: {
                                            type: 'number',
                                            precision: 2,
                                            onBlur: (obj) => {
                                                let val = obj.target.value;
                                                this.tableOne.getEditedRowData(false).then((editRowData) => {
                                                    let tableOneData = this.tableOne.state.data.map((item) => {
                                                        if ((item.applyShopListId || item.applyLeaseListId) === this.state.tableOneID) {
                                                            if (Number(editRowData.qty) !== Number(item.qty)) {
                                                                if (this.table.btnCallbackFn.form.getFieldValue('code7') === 'WL') {
                                                                    item.qty = val ? Number(val) : 0;
                                                                    item.price = item.price ? item.price : 0;
                                                                    item.contrTrrm = item.contrTrrm ? item.contrTrrm : 0;
                                                                    item.contractSum = (val ? Number(val) : 0) * (item.price ? item.price : 0) * (item.contrTrrm ? item.contrTrrm : 0);
                                                                    item.priceNoTax = (item.price ? item.price : 0) / ((item.taxRate ? (1 + Number(item.taxRate) / 100) : 1));
                                                                    item.contractSumNoTax = (val ? Number(val) : 0) * (item.price ? item.price : 0) * (item.contrTrrm ? item.contrTrrm : 0) / ((item.taxRate ? (1 + Number(item.taxRate) / 100) : 1));
                                                                    item.contractSumTax = (val ? Number(val) : 0) * (item.price ? item.price : 0) * (item.contrTrrm ? item.contrTrrm : 0) - (val ? Number(val) : 0) * (item.price ? item.price : 0) * (item.contrTrrm ? item.contrTrrm : 0) / ((item.taxRate ? (1 + Number(item.taxRate) / 100) : 1));
                                                                    item.remarks = editRowData.remarks;
                                                                    item.isDeduct = editRowData.isDeduct;
                                                                    item.isNetPrice = editRowData.isNetPrice;
                                                                    item.rentUnit = editRowData.rentUnit;
                                                                } else {
                                                                    item.qty = val ? Number(val) : 0;
                                                                    item.price = item.price ? item.price : 0;
                                                                    item.contractSum = (val ? Number(val) : 0) * (item.price ? item.price : 0);
                                                                    item.priceNoTax = (item.price ? item.price : 0) / ((item.taxRate ? (1 + Number(item.taxRate) / 100) : 1));
                                                                    item.contractSumNoTax = (val ? Number(val) : 0) * (item.price ? item.price : 0) / ((item.taxRate ? (1 + Number(item.taxRate) / 100) : 1));
                                                                    item.contractSumTax = (val ? Number(val) : 0) * (item.price ? item.price : 0) - (val ? Number(val) : 0) * (item.price ? item.price : 0) / ((item.taxRate ? (1 + Number(item.taxRate) / 100) : 1));
                                                                    item.remarks = editRowData.remarks;
                                                                    item.isDeduct = editRowData.isDeduct;
                                                                    item.isNetPrice = editRowData.isNetPrice;
                                                                }
                                                            } else {
                                                                item = null;
                                                            }
                                                        }
                                                        return item;
                                                    })
                                                    if (!tableOneData.filter(item => item === null).length) {
                                                        setTimeout(() => {
                                                            this.tableOne.btnCallbackFn.setState({
                                                                data: tableOneData
                                                            })
                                                        }, 0)
                                                    }
                                                });
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
                                                    return rowData.price.toFixed(2)
                                                }
                                            }
                                        },
                                        form: {
                                            type: 'number',
                                            precision: 2,
                                            onBlur: (obj) => {
                                                let val = obj.target.value;
                                                this.tableOne.getEditedRowData(false).then((editRowData) => {
                                                    let tableOneData = this.tableOne.state.data.map((item) => {
                                                        if ((item.applyShopListId || item.applyLeaseListId) === this.state.tableOneID) {
                                                            if (Number(editRowData.price) !== Number(item.price)) {
                                                                if (this.table.btnCallbackFn.form.getFieldValue('code7') === 'WL') {
                                                                    item.qty = item.qty ? item.qty : 0;
                                                                    item.price = val ? Number(val) : 0;
                                                                    item.contrTrrm = item.contrTrrm ? item.contrTrrm : 0;
                                                                    item.contractSum = (item.qty ? item.qty : 0) * (val ? Number(val) : 0) * (item.contrTrrm ? item.contrTrrm : 0);
                                                                    item.priceNoTax = (val ? Number(val) : 0) / ((item.taxRate ? (1 + Number(item.taxRate) / 100) : 1));
                                                                    item.contractSumNoTax = (item.qty ? item.qty : 0) * (val ? Number(val) : 0) * (item.contrTrrm ? item.contrTrrm : 0) / ((item.taxRate ? (1 + Number(item.taxRate) / 100) : 1));
                                                                    item.contractSumTax = (item.qty ? item.qty : 0) * (val ? Number(val) : 0) * (item.contrTrrm ? item.contrTrrm : 0) - (item.qty ? item.qty : 0) * (val ? Number(val) : 0) * (item.contrTrrm ? item.contrTrrm : 0) / ((item.taxRate ? (1 + Number(item.taxRate) / 100) : 1));
                                                                    item.remarks = editRowData.remarks;
                                                                    item.isDeduct = editRowData.isDeduct;
                                                                    item.isNetPrice = editRowData.isNetPrice;
                                                                    item.rentUnit = editRowData.rentUnit;
                                                                } else {
                                                                    item.qty = item.qty ? item.qty : 0;
                                                                    item.price = val ? Number(val) : 0;
                                                                    item.contractSum = (item.qty ? item.qty : 0) * (val ? Number(val) : 0);
                                                                    item.priceNoTax = (val ? Number(val) : 0) / ((item.taxRate ? (1 + Number(item.taxRate) / 100) : 1));
                                                                    item.contractSumNoTax = (item.qty ? item.qty : 0) * (val ? Number(val) : 0) / ((item.taxRate ? (1 + Number(item.taxRate) / 100) : 1));
                                                                    item.contractSumTax = (item.qty ? item.qty : 0) * (val ? Number(val) : 0) - (item.qty ? item.qty : 0) * (val ? Number(val) : 0) / ((item.taxRate ? (1 + Number(item.taxRate) / 100) : 1));
                                                                    item.remarks = editRowData.remarks;
                                                                    item.isDeduct = editRowData.isDeduct;
                                                                    item.isNetPrice = editRowData.isNetPrice;
                                                                }
                                                            } else {
                                                                item = null;
                                                            }
                                                        }
                                                        return item;
                                                    })
                                                    if (!tableOneData.filter(item => item === null).length) {
                                                        setTimeout(() => {
                                                            this.tableOne.btnCallbackFn.setState({
                                                                data: tableOneData
                                                            })
                                                        }, 0)
                                                    }
                                                });
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
                                                    return rowData.priceNoTax.toFixed(2)
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
                                                    let tableOneData = this.tableOne.state.data.map((item) => {
                                                        if ((item.applyShopListId || item.applyLeaseListId) === this.state.tableOneID) {
                                                            if (this.table.btnCallbackFn.form.getFieldValue('code7') === 'WL') {
                                                                item.qty = item.qty ? item.qty : 0;
                                                                item.price = item.price ? item.price : 0;
                                                                item.contrTrrm = item.contrTrrm ? item.contrTrrm : 0;
                                                                item.taxRate = val;
                                                                item.contractSum = (item.qty ? item.qty : 0) * (item.price ? item.price : 0) * (item.contrTrrm ? item.contrTrrm : 0);
                                                                item.priceNoTax = (item.price ? item.price : 0) / ((val ? (1 + Number(val) / 100) : 1));
                                                                item.contractSumNoTax = (item.qty ? item.qty : 0) * (item.price ? item.price : 0) * (item.contrTrrm ? item.contrTrrm : 0) / ((val ? (1 + Number(val) / 100) : 1));
                                                                item.contractSumTax = (item.qty ? item.qty : 0) * (item.price ? item.price : 0) * (item.contrTrrm ? item.contrTrrm : 0) - (item.qty ? item.qty : 0) * (item.price ? item.price : 0) * (item.contrTrrm ? item.contrTrrm : 0) / ((val ? (1 + Number(val) / 100) : 1));
                                                                item.remarks = editRowData.remarks;
                                                                item.isDeduct = editRowData.isDeduct;
                                                                item.isNetPrice = editRowData.isNetPrice;
                                                                item.rentUnit = editRowData.rentUnit;
                                                            } else {
                                                                item.qty = item.qty ? item.qty : 0;
                                                                item.price = item.price ? item.price : 0;
                                                                item.taxRate = val;
                                                                item.contractSum = (item.qty ? item.qty : 0) * (item.price ? item.price : 0);
                                                                item.priceNoTax = (item.price ? item.price : 0) / ((val ? (1 + Number(val) / 100) : 1));
                                                                item.contractSumNoTax = (item.qty ? item.qty : 0) * (item.price ? item.price : 0) / ((val ? (1 + Number(val) / 100) : 1));
                                                                item.contractSumTax = (item.qty ? item.qty : 0) * (item.price ? item.price : 0) - (item.qty ? item.qty : 0) * (item.price ? item.price : 0) / ((val ? (1 + Number(val) / 100) : 1));
                                                                item.remarks = editRowData.remarks;
                                                                item.isDeduct = editRowData.isDeduct;
                                                                item.isNetPrice = editRowData.isNetPrice;
                                                            }
                                                        }
                                                        return item;
                                                    })
                                                    this.tableOne.btnCallbackFn.setState({
                                                        data: tableOneData
                                                    })
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
                                    },
                                    {
                                        isInForm: false,
                                        table: {
                                            showType: 'tile', //出来的样式 bubble（气泡）  tile（平铺） 默认bubble  （0.6.15版本中将该属性移动到table属性下，也可写到table同级）
                                            width: 80,
                                            title: "操作",
                                            key: "action",//操作列名称
                                            fixed: 'right',//固定到右边
                                            render: (data, rowData) => {
                                                if (this.table.state.clickCb.rowInfo.name === 'detail') {
                                                    return '';
                                                } else {
                                                    if ((rowData.applyShopListId || rowData.applyLeaseListId) === this.state.tableOneID) {
                                                        return <a>保存</a>;
                                                    } else {
                                                        return <a>编辑</a>;
                                                    }
                                                }
                                            },
                                            onClick: (obj) => {
                                                if (this.state.tableOneID) {
                                                    if (this.state.tableOneID === (obj.rowData.applyShopListId || obj.rowData.applyLeaseListId)) {
                                                        obj.btnCallbackFn.getEditedRowData().then((data) => {
                                                            obj.rowData.isDeduct = data.isDeduct ? data.isDeduct : '0';
                                                            obj.rowData.remarks = data.remarks;
                                                            obj.rowData.isNetPrice = data.isNetPrice ? data.isNetPrice : '0';
                                                            if (obj.props.tableFns.qnnForm.form.getFieldValue('code7') === 'WL') {
                                                                obj.rowData.rentUnit = data.rentUnit;
                                                            }
                                                            setTimeout(() => {
                                                                if (typeof this.state.tableOneID === 'number') {
                                                                    this.props.myFetch(obj.props.tableFns.qnnForm.form.getFieldValue('code7') !== 'WL' ? 'addZxCtSuppliesContrApplyShopListByApplyId' : 'addZxCtSuppliesContrApplyLeaseListByApplyId', obj.rowData).then(
                                                                        ({ success, message }) => {
                                                                            if (success) {
                                                                                Msg.success(message);
                                                                                this.setState({
                                                                                    tableOneID: ''
                                                                                })
                                                                                obj.btnCallbackFn.refresh();
                                                                            } else {
                                                                                Msg.error(message);
                                                                            }
                                                                        }
                                                                    );
                                                                } else {
                                                                    this.props.myFetch(obj.props.tableFns.qnnForm.form.getFieldValue('code7') !== 'WL' ? 'updateZxCtSuppliesContrApplyShopListByApplyId' : 'updateZxCtSuppliesContrApplyLeaseListByApplyId', obj.rowData).then(
                                                                        ({ success, message }) => {
                                                                            if (success) {
                                                                                Msg.success(message);
                                                                                this.setState({
                                                                                    tableOneID: ''
                                                                                })
                                                                                obj.btnCallbackFn.refresh();
                                                                            } else {
                                                                                Msg.error(message);
                                                                            }
                                                                        }
                                                                    );
                                                                }
                                                            }, 300);
                                                        })
                                                    } else {
                                                        Msg.error('有数据未保存,请保存后编辑!')
                                                    }
                                                } else {
                                                    this.setState({
                                                        tableOneID: obj.rowData.applyShopListId ? obj.rowData.applyShopListId : obj.rowData.applyLeaseListId
                                                    })
                                                }
                                            }
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
                                onClick: 'detail',
                                willExecute: (obj) => {
                                    this.setState({
                                        tableOneID: ''
                                    })
                                }
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
                                dataIndex: 'resCategoryID',
                                key: 'resCategoryID',
                                width: 100,
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
                                        return '评审通过';
                                    } else if (data === '3') {
                                        return '退回';
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