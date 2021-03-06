import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import s from './style.less';
import FlowFormBySupplementaryAgreementOnMaterialManagementt from './form';
import moment from 'moment';
// import { debounce } from "lodash";
import Operation from './operation';
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
        this.state = {

        }
    }
    render() {
        const { realName } = this.props.loginAndLogoutInfo.loginInfo.userInfo;
        const { departmentId } = this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany;
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
                        otherParams:{
                            orgID:departmentId
                        }
                    }}
                    actionBtns={[
                        {
                            name: 'add',//内置add del
                            icon: 'plus',//icon
                            type: 'primary',//类型  默认 primary  [primary dashed danger]
                            label: '新增',
                            drawerTitle: '新增',
                            formBtns: [
                                {
                                    name: 'cancel',
                                    type: 'dashed',
                                    label: '取消'
                                },
                                {
                                    name: 'submit',
                                    type: 'primary',
                                    label: '保存',
                                    isCloseDrawer: false,
                                    isValidate: 'curTab',
                                    fetchConfig: (obj) => {
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
                                                    }
                                                }
                                            } else {
                                                if (rowData.zxCtSuppliesContractChangeId) {
                                                    return {
                                                        apiName: 'updateZxCtSupContrReplLeaseDetailBycontrAlterID',
                                                        otherParams: {
                                                            ...rowData
                                                        }
                                                    }
                                                } else {
                                                    return {
                                                        apiName: 'addZxCtSupContrReplLeaseDetailBycontrAlterID',
                                                        otherParams: {
                                                            ...rowData
                                                        }
                                                    }
                                                }
                                            }
                                        } else {
                                            return {
                                                apiName: 'addZxCtSuppliesContrReplenishAgreementByContrId',
                                                otherParams: {
                                                    ...rowData
                                                }
                                            }
                                        }
                                    },
                                    onClick: (obj) => {
                                        if (obj.response.success) {
                                            obj.btnCallbackFn.form.setFieldsValue(obj.response.data);
                                            obj.btnCallbackFn.setActiveKey('1');
                                        }
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
                            // disabled: (obj) => {
                            //     if (obj.btnCallbackFn.getSelectedRows().length) {
                            //         return false;
                            //     } else {
                            //         return true;
                            //     }
                            // },
                            formBtns: [
                                {
                                    name: 'cancel',
                                    type: 'dashed',
                                    label: '取消'
                                },
                                {
                                    name: 'submit',
                                    type: 'primary',
                                    label: '保存',
                                    isValidate: 'curTab',
                                    isCloseDrawer: false,
                                    fetchConfig: (obj) => {
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
                                                }
                                            }
                                        } else {
                                            if (rowData.zxCtSuppliesContractChangeId) {
                                                return {
                                                    apiName: 'updateZxCtSupContrReplLeaseDetailBycontrAlterID',
                                                    otherParams: {
                                                        ...rowData
                                                    }
                                                }
                                            } else {
                                                return {
                                                    apiName: 'addZxCtSupContrReplLeaseDetailBycontrAlterID',
                                                    otherParams: {
                                                        ...rowData
                                                    }
                                                }
                                            }
                                        }
                                    },
                                    onClick: (obj) => {
                                        if (obj.response.success) {
                                            obj.btnCallbackFn.setActiveKey('1');
                                        }
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
                                    return false;
                                } else {
                                    return true;
                                }
                            },
                            Component: (props) => {
                                return <FlowFormBySupplementaryAgreementOnMaterialManagementt {...this.props} btnCallbackFn={props.btnCallbackFn} isInQnnTable={props.isInQnnTable} flowData={props.selectedRows[0]} />;
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
                            disabled: (obj) => {
                                let data = obj.btnCallbackFn.getSelectedRows();
                                if (data.length === 1 && (!data[0].apih5FlowStatus || data[0].apih5FlowStatus === "-1" || data[0].apih5FlowStatus === "0")) {
                                    // && data[0].apih5FlowStatus === "-1" || data[0].apih5FlowStatus === "0"
                                    return false;
                                } else {
                                    return true;
                                }
                            }, label: '删除',
                            fetchConfig: {//ajax配置
                                apiName: 'batchDeleteUpdateZxCtSuppliesContrReplenishAgreement',
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
                    tabs={[
                        {
                            field: "form1",
                            name: "qnnForm",
                            title: "基础信息",
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
                                        label: '补充协议编号',
                                        field: "contractNo",
                                        type: 'string',
                                        disabled: true,
                                        placeholder: '请输入',
                                        span: 12
                                    },
                                    {
                                        label: '补充协议名称',
                                        field: "pp3",
                                        type: 'string',
                                        required: true,
                                        placeholder: '请输入',
                                        span: 12
                                    },
                                    {
                                        label: '合同名称',
                                        field: "pp6",
                                        type: 'selectByPaging',
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
                                        fetchConfig: {
                                            apiName: 'getZxCtSuppliesContractListAddAgreementNum',
                                            otherParams: {
                                                pp9: '1',
                                                orgID:departmentId
                                            },
                                            searchKey:'contractName'
                                        },
                                        condition: [
                                            {
                                                regex: { replenishAgreementId: ['!', /(''|null|undefined)/ig] },
                                                action: 'disabled',
                                            }
                                        ],
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
                                        label: '开工日期',
                                        field: "startDate",
                                        type: 'date',
                                        placeholder: '请选择',
                                        span: 12
                                    },
                                    {
                                        label: '竣工日期',
                                        field: "endDate",
                                        type: 'date',
                                        placeholder: '请选择',
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
                                        label: '合同类型',
                                        field: "contractType",
                                        type: 'string',
                                        disabled: true,
                                        initialValue: '物资管理类补充协议',
                                        placeholder: '请输入',
                                        span: 12
                                    },
                                    {
                                        label: '甲方名称',
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
                                        label: '乙方名称',
                                        field: 'secondName',
                                        type: 'string',
                                        disabled: true,
                                        span: 12
                                    },
                                    {
                                        label: '乙方名称',
                                        field: "secondID",
                                        type: 'string',
                                        hide: true
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
                                        label: '本期含税增减金额(万元)',
                                        field: "pp4",
                                        type: 'string',
                                        disabled: true,
                                        initialValue: "0",
                                        precision: 6,
                                        placeholder: '请输入',
                                        span: 12
                                    },
                                    {
                                        label: '本期不含税增减金额(万元)',
                                        field: "pp4NoTax",
                                        type: 'number',
                                        disabled: true,
                                        initialValue: 0,
                                        precision: 6,
                                        placeholder: '请输入',
                                        span: 12
                                    },
                                    {
                                        label: '本期增减税额(万元)',
                                        field: "pp4Tax",
                                        type: 'number',
                                        disabled: true,
                                        initialValue: 0,
                                        precision: 6,
                                        placeholder: '请输入',
                                        span: 12
                                    },
                                    {
                                        label: '变更后含税金额(万元)',
                                        field: "alterContractSum",
                                        type: 'number',
                                        disabled: true,
                                        initialValue: 0,
                                        precision: 6,
                                        placeholder: '请输入',
                                        span: 12
                                    },
                                    {
                                        label: '变更后不含税金额(万元)',
                                        field: "alterContractSumNoTax",
                                        type: 'number',
                                        disabled: true,
                                        initialValue: 0,
                                        precision: 6,
                                        placeholder: '请输入',
                                        span: 12
                                    },
                                    {
                                        label: '变更后税额(万元)',
                                        field: "alterContractSumTax",
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
                                        optionData: [
                                            {
                                                value: '1'
                                            }
                                        ],
                                        disabled: true,
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
                                        disabled: true,
                                        span: 12
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
                                        disabled: true,
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
                                    //     disabled: true,
                                    //     span: 12
                                    // },
                                    // {
                                    //     label: '采购编号',
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
                                    //     placeholder: '请输入',
                                    //     span: 12
                                    // },
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
                                    },
                                    {
                                        label: '附件',
                                        field: 'replenishAgreementFileList',
                                        type: 'files',
                                        fetchConfig: {
                                            apiName: 'upload'
                                        },
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 4 },
                                                sm: { span: 4 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 24 },
                                                sm: { span: 24 }
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
                                        label: '补充协议编号',
                                        field: "contractNo",
                                        type: 'string',
                                        disabled: true,
                                        placeholder: '请输入',
                                        span: 12
                                    },
                                    {
                                        label: '批复单位',
                                        field: "replyUnit",
                                        type: 'select',
                                        optionConfig: {
                                            label: 'label',
                                            value: 'value'
                                        },
                                        optionData: [
                                            {
                                                label: '项目',
                                                value: '0'
                                            },
                                            {
                                                label: '公司',
                                                value: '1'
                                            },
                                            {
                                                label: '局',
                                                value: '2'
                                            }
                                        ],
                                        placeholder: '请选择',
                                        span: 12
                                    },
                                    {
                                        label: '批复日期',
                                        field: "replyDate",
                                        type: 'date',
                                        placeholder: '请输入',
                                        span: 12
                                    },
                                    {
                                        label: '变更内容',
                                        field: 'alterContent',
                                        type: 'string',
                                        required: true,
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
                                        label: '变更原因',
                                        field: 'alterReason',
                                        type: 'string',
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
                                    // {
                                    //     label: '含税合同金额(万元)',
                                    //     field: "contractCost",
                                    //     type: 'number',
                                    //     disabled: true,
                                    //     precision: 6,
                                    //     placeholder: '请输入',
                                    //     span: 12
                                    // },
                                    // {
                                    //     label: '不含税合同金额(万元)',
                                    //     field: "contractCostNoTax",
                                    //     type: 'number',
                                    //     disabled: true,
                                    //     precision: 6,
                                    //     placeholder: '请输入',
                                    //     span: 12
                                    // },
                                    // {
                                    //     label: '合同税额(万元)',
                                    //     field: "contractCostTax",
                                    //     type: 'number',
                                    //     disabled: true,
                                    //     precision: 6,
                                    //     placeholder: '请输入',
                                    //     span: 12
                                    // },
                                    // {
                                    //     label: '本期含税增减金额(万元)',
                                    //     field: "pp4",
                                    //     type: 'string',
                                    //     disabled: true,
                                    //     precision: 6,
                                    //     placeholder: '请输入',
                                    //     span: 12
                                    // },
                                    // {
                                    //     label: '本期不含税增减金额(万元)',
                                    //     field: "pp4NoTax",
                                    //     type: 'number',
                                    //     disabled: true,
                                    //     precision: 6,
                                    //     placeholder: '请输入',
                                    //     span: 12
                                    // },
                                    // {
                                    //     label: '本期增减税额(万元)',
                                    //     field: "pp4Tax",
                                    //     type: 'number',
                                    //     disabled: true,
                                    //     precision: 6,
                                    //     placeholder: '请输入',
                                    //     span: 12
                                    // },
                                    // {
                                    //     label: '变更后含税金额(万元)',
                                    //     field: "alterContractSum",
                                    //     type: 'number',
                                    //     disabled: true,
                                    //     precision: 6,
                                    //     placeholder: '请输入',
                                    //     span: 12
                                    // },
                                    // {
                                    //     label: '变更后不含税金额(万元)',
                                    //     field: "alterContractSumNoTax",
                                    //     type: 'number',
                                    //     disabled: true,
                                    //     precision: 6,
                                    //     placeholder: '请输入',
                                    //     span: 12
                                    // },
                                    // {
                                    //     label: '变更后税额(万元)',
                                    //     field: "alterContractSumTax",
                                    //     type: 'number',
                                    //     disabled: true,
                                    //     precision: 6,
                                    //     placeholder: '请输入',
                                    //     span: 12
                                    // },
                                    {
                                        type: "qnnTable",
                                        field: "replenishShopDetailedList",
                                        incToForm: true,
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
                                            tableTdEdit: true,
                                            actionBtns: [
                                                {
                                                    name: 'addRow',
                                                    icon: 'plus',
                                                    type: 'primary',
                                                    label: '新增',
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
                                                    label: '删除',
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
                                                        title: `<div>变更类型<span style='color: #ff4d4f'>*</span></div>`,
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
                                                            label: 'label', //默认 label
                                                            value: 'value'
                                                        },
                                                        optionData: [
                                                            {
                                                                label: '新增',
                                                                value: '1'
                                                            },
                                                            {
                                                                label: '修改',
                                                                value: '2'
                                                            }
                                                        ],
                                                        onChange: (val, obj, btnCallbackFn) => {
                                                            btnCallbackFn.getEditedRowData(false).then((editRowData) => {
                                                                editRowData.workTypeID = null;
                                                                editRowData.workType = null;
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
                                                        required: true,
                                                        placeholder: '请选择'
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: `<div>物资类别<span style='color: #ff4d4f'>*</span></div>`,
                                                        dataIndex: 'workTypeID',
                                                        key: 'workTypeID',
                                                        width: 150,
                                                        // type: 'select',
                                                        tdEdit: true,
                                                        fixed: 'left',
                                                        render:(data,rowData) => {
                                                            return rowData.workType;
                                                        }
                                                    },
                                                    form: {
                                                        type: 'select',
                                                        disabled: (obj) => {
                                                            if (!obj.record.alterType || obj.record.alterType === '2') {
                                                                return true
                                                            } else {
                                                                return false;
                                                            }
                                                        },
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
                                                        placeholder: '请选择'
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: `<div>物资编码<span style='color: #ff4d4f'>*</span></div>`,
                                                        dataIndex: 'workID',
                                                        key: 'workID',
                                                        width: 150,
                                                        // type: 'select',
                                                        tdEdit: true,
                                                        fixed: 'left',
                                                        render: (data, rowData) => {
                                                            return rowData.workNo;
                                                        }
                                                    },
                                                    form: {
                                                        type: 'select',
                                                        optionConfig: (obj) => {
                                                            if (obj.rowData.alterType === '1') {
                                                                return {
                                                                    label: 'resCode', //默认 label
                                                                    value: 'id'
                                                                }
                                                            } else {
                                                                return {
                                                                    label: 'workNo', //默认 label
                                                                    value: 'workID'
                                                                }
                                                            }
                                                        },
                                                        disabled: (obj) => {
                                                            if (!obj.record.alterType) {
                                                                return true
                                                            } else {
                                                                return false;
                                                            }
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
                                                                }
                                                            } else {
                                                                return {
                                                                    apiName: 'getZxCtSuppliesContrShopListList',
                                                                    // apiName: 'getZxCtSuppliesContrApplyShopListList',
                                                                    otherParams: {
                                                                        contractID: this.table.form.getFieldValue('pp6')
                                                                    }
                                                                }
                                                            }
                                                        },
                                                        onChange: (val, obj, btnCallbackFn) => {
                                                            btnCallbackFn.getEditedRowData(false).then((editRowData) => {
                                                                if (editRowData.alterType === '1') {
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
                                                        placeholder: '请选择'
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '物资名称',
                                                        dataIndex: 'workName',
                                                        key: 'workName',
                                                        width: 100
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
                                                        disabled: (obj) => {
                                                            if (!obj.record.alterType || obj.record.alterType === '2') {
                                                                return true
                                                            } else {
                                                                return false;
                                                            }
                                                        },
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
                                                        render: (data, rowData) => {
                                                            if (rowData.qty || rowData.qty === 0) {
                                                                return rowData.qty.toFixed(2)
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
                                                        title: '含税合同单价',
                                                        dataIndex: 'price',
                                                        key: 'price',
                                                        width: 120,
                                                        render: (data, rowData) => {
                                                            if (rowData.price || rowData.price === 0) {
                                                                return rowData.price.toFixed(2)
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
                                                        title: '税率(%)',
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
                                                        disabled: (obj) => {
                                                            if (!obj.record.alterType || obj.record.alterType === '2') {
                                                                return true
                                                            } else {
                                                                return false;
                                                            }
                                                        },
                                                        onChange: (val, obj, btnCallbackFn) => {
                                                            btnCallbackFn.getEditedRowData(false).then((editRowData) => {
                                                                editRowData.changePriceNoTax = (editRowData.changePrice ? editRowData.changePrice : 0) / ((val ? (1 + Number(val) / 100) : 1));
                                                                editRowData.changeContractSum = (editRowData.changeQty ? editRowData.changeQty : 0) * (editRowData.changePrice ? editRowData.changePrice : 0);
                                                                editRowData.changeContractSumNoTax = ((editRowData.changeQty ? editRowData.changeQty : 0) * (editRowData.changePrice ? editRowData.changePrice : 0)) / (val ? (1 + Number(val) / 100) : 1);
                                                                editRowData.changeContractSumTax = (editRowData.changeQty ? editRowData.changeQty : 0) * (editRowData.changePrice ? editRowData.changePrice : 0) - ((editRowData.changeQty ? editRowData.changeQty : 0) * (editRowData.changePrice ? editRowData.changePrice : 0)) / (val ? (1 + Number(val) / 100) : 1);
                                                                btnCallbackFn.setEditedRowData({ ...editRowData });
                                                                // let tableData = this.table.form.getFieldsValue();
                                                                // tableData.pp4 = 0;
                                                                // tableData.pp4NoTax = 0;
                                                                // tableData.pp4Tax = 0;
                                                                // tableData.alterContractSum = 0;
                                                                // tableData.alterContractSumNoTax = 0;
                                                                // tableData.alterContractSumTax = 0;
                                                                // tableData.replenishShopDetailedList.map((item) => {
                                                                //     if (item.zxCtSuppliesContrReplenishShopDetailId === editRowData.zxCtSuppliesContrReplenishShopDetailId) {
                                                                //         tableData.pp4 += ((editRowData.changeContractSum ? editRowData.changeContractSum : 0) / 10000);
                                                                //         tableData.pp4NoTax += (editRowData.changeContractSumNoTax ? editRowData.changeContractSumNoTax : 0) / 10000;
                                                                //         tableData.pp4Tax += (editRowData.changeContractSumTax ? editRowData.changeContractSumTax : 0) / 10000;
                                                                //         tableData.alterContractSum += (((editRowData.changeContractSum ? editRowData.changeContractSum : 0) / 10000) + (tableData.contractCost ? tableData.contractCost : 0));
                                                                //         tableData.alterContractSumNoTax += (((editRowData.changeContractSumNoTax ? editRowData.changeContractSumNoTax : 0) / 10000) + (tableData.contractCostNoTax ? tableData.contractCostNoTax : 0));
                                                                //         tableData.alterContractSumTax += (((editRowData.changeContractSumTax ? editRowData.changeContractSumTax : 0) / 10000) + (tableData.contractCostTax ? tableData.contractCostTax : 0));
                                                                //     } else {
                                                                //         tableData.pp4 += ((item.changeContractSum ? item.changeContractSum : 0) / 10000);
                                                                //         tableData.pp4NoTax += (item.changeContractSumNoTax ? item.changeContractSumNoTax : 0) / 10000;
                                                                //         tableData.pp4Tax += (item.changeContractSumTax ? item.changeContractSumTax : 0) / 10000;
                                                                //         tableData.alterContractSum += (((item.changeContractSum ? item.changeContractSum : 0) / 10000) + (tableData.contractCost ? tableData.contractCost : 0));
                                                                //         tableData.alterContractSumNoTax += (((item.changeContractSumNoTax ? item.changeContractSumNoTax : 0) / 10000) + (tableData.contractCostNoTax ? tableData.contractCostNoTax : 0));
                                                                //         tableData.alterContractSumTax += (((item.changeContractSumTax ? item.changeContractSumTax : 0) / 10000) + (tableData.contractCostTax ? tableData.contractCostTax : 0));
                                                                //     }
                                                                //     return item;
                                                                // })
                                                                // tableData.pp4 = tableData.pp4.toFixed(6);
                                                                // this.table.form.setFieldsValue(tableData);
                                                            })
                                                        },
                                                        allowClear: false,
                                                        showSearch: true,
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
                                                        title: '已结算数量',
                                                        dataIndex: 'a',
                                                        key: 'a',
                                                        width: 100,
                                                        render: (data, rowData) => {
                                                            if (rowData.a || rowData.a === 0) {
                                                                return rowData.a.toFixed(2)
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
                                                        title: '变更后数量',
                                                        dataIndex: 'changeQty',
                                                        key: 'changeQty',
                                                        width: 120,
                                                        tdEdit: true,
                                                        render: (data, rowData) => {
                                                            if (rowData.changeQty || rowData.changeQty === 0) {
                                                                return rowData.changeQty.toFixed(2)
                                                            }
                                                        }
                                                    },
                                                    form: {
                                                        type: 'number',
                                                        precision: 2,
                                                        // onBlur: (obj) => {
                                                        //     let val = obj.target.value;
                                                        //     this.tableOne.getEditedRowData(false).then((editRowData) => {
                                                        //         editRowData.changeQty = val ? Number(val) : 0;
                                                        //         editRowData.changePriceNoTax = (editRowData.changePrice ? editRowData.changePrice : 0) / ((editRowData.taxRate ? (1 + Number(editRowData.taxRate) / 100) : 1));
                                                        //         editRowData.changeContractSum = (val ? Number(val) : 0) * (editRowData.changePrice ? editRowData.changePrice : 0);
                                                        //         editRowData.changeContractSumNoTax = ((val ? Number(val) : 0) * (editRowData.changePrice ? editRowData.changePrice : 0)) / (editRowData.taxRate ? (1 + Number(editRowData.taxRate) / 100) : 1);
                                                        //         editRowData.changeContractSumTax = (val ? Number(val) : 0) * (editRowData.changePrice ? editRowData.changePrice : 0) - ((val ? Number(val) : 0) * (editRowData.changePrice ? editRowData.changePrice : 0)) / (editRowData.taxRate ? (1 + Number(editRowData.taxRate) / 100) : 1);
                                                        //         this.tableOne.setEditedRowData({ ...editRowData });
                                                        //     })
                                                        // },
                                                        onBlur: (e, obj, btnCallbackFn) => {
                                                            let val = e.target.value;
                                                            setTimeout(async () => {
                                                                await btnCallbackFn.getEditedRowData(false).then((editRowData) => {
                                                                    editRowData.changeQty = val ? Number(val) : 0;
                                                                    editRowData.changePriceNoTax = (editRowData.changePrice ? editRowData.changePrice : 0) / ((editRowData.taxRate ? (1 + Number(editRowData.taxRate) / 100) : 1));
                                                                    editRowData.changeContractSum = (val ? Number(val) : 0) * (editRowData.changePrice ? editRowData.changePrice : 0);
                                                                    editRowData.changeContractSumNoTax = ((val ? Number(val) : 0) * (editRowData.changePrice ? editRowData.changePrice : 0)) / (editRowData.taxRate ? (1 + Number(editRowData.taxRate) / 100) : 1);
                                                                    editRowData.changeContractSumTax = (val ? Number(val) : 0) * (editRowData.changePrice ? editRowData.changePrice : 0) - ((val ? Number(val) : 0) * (editRowData.changePrice ? editRowData.changePrice : 0)) / (editRowData.taxRate ? (1 + Number(editRowData.taxRate) / 100) : 1);
                                                                    btnCallbackFn.setEditedRowData({ ...editRowData });
                                                                })
                                                            }, 0)
                                                        },
                                                        placeholder: '请输入'
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '变更后含税单价',
                                                        dataIndex: 'changePrice',
                                                        key: 'changePrice',
                                                        width: 120,
                                                        tdEdit: true,
                                                        render: (data, rowData) => {
                                                            if (rowData.changePrice || rowData.changePrice === 0) {
                                                                return rowData.changePrice.toFixed(2)
                                                            }
                                                        }
                                                    },
                                                    form: {
                                                        type: 'number',
                                                        precision: 2,
                                                        disabled: (obj) => {
                                                            if (!obj.record.alterType || obj.record.alterType === '2') {
                                                                return true
                                                            } else {
                                                                return false;
                                                            }
                                                        },
                                                        onBlur: (e, obj, btnCallbackFn) => {
                                                            let val = e.target.value;
                                                            setTimeout(async () => {
                                                                await btnCallbackFn.getEditedRowData(false).then((editRowData) => {
                                                                    editRowData.changePrice = val ? Number(val) : 0;
                                                                    editRowData.changePriceNoTax = (val ? Number(val) : 0) / ((editRowData.taxRate ? (1 + Number(editRowData.taxRate) / 100) : 1));
                                                                    editRowData.changeContractSum = (editRowData.changeQty ? editRowData.changeQty : 0) * (val ? Number(val) : 0);
                                                                    editRowData.changeContractSumNoTax = ((editRowData.changeQty ? editRowData.changeQty : 0) * (val ? Number(val) : 0)) / (editRowData.taxRate ? (1 + Number(editRowData.taxRate) / 100) : 1);
                                                                    editRowData.changeContractSumTax = (editRowData.changeQty ? editRowData.changeQty : 0) * (val ? Number(val) : 0) - ((editRowData.changeQty ? editRowData.changeQty : 0) * (val ? Number(val) : 0)) / (editRowData.taxRate ? (1 + Number(editRowData.taxRate) / 100) : 1);
                                                                    btnCallbackFn.setEditedRowData({ ...editRowData });
                                                                })
                                                            }, 0)
                                                        },
                                                        // onBlur: (obj) => {
                                                        //     let val = obj.target.value;
                                                        //     this.tableOne.getEditedRowData(false).then((editRowData) => {
                                                        //         editRowData.changePrice = val ? Number(val) : 0;
                                                        //         editRowData.changePriceNoTax = (val ? Number(val) : 0) / ((editRowData.taxRate ? (1 + Number(editRowData.taxRate) / 100) : 1));
                                                        //         editRowData.changeContractSum = (editRowData.changeQty ? editRowData.changeQty : 0) * (val ? Number(val) : 0);
                                                        //         editRowData.changeContractSumNoTax = ((editRowData.changeQty ? editRowData.changeQty : 0) * (val ? Number(val) : 0)) / (editRowData.taxRate ? (1 + Number(editRowData.taxRate) / 100) : 1);
                                                        //         editRowData.changeContractSumTax = (editRowData.changeQty ? editRowData.changeQty : 0) * (val ? Number(val) : 0) - ((editRowData.changeQty ? editRowData.changeQty : 0) * (val ? Number(val) : 0)) / (editRowData.taxRate ? (1 + Number(editRowData.taxRate) / 100) : 1);
                                                        //         this.tableOne.setEditedRowData({ ...editRowData });
                                                        //     })
                                                        // },
                                                        // onChange: debounce((val, obj, btnCallbackFn) => {
                                                        //     btnCallbackFn.getEditedRowData(false).then((editRowData) => {
                                                        //         editRowData.changePrice = val ? Number(val) : 0;
                                                        //         editRowData.changePriceNoTax = (val ? Number(val) : 0) / ((editRowData.taxRate ? (1 + Number(editRowData.taxRate) / 100) : 1));
                                                        //         editRowData.changeContractSum = (editRowData.changeQty ? editRowData.changeQty : 0) * (val ? Number(val) : 0);
                                                        //         editRowData.changeContractSumNoTax = ((editRowData.changeQty ? editRowData.changeQty : 0) * (val ? Number(val) : 0)) / (editRowData.taxRate ? (1 + Number(editRowData.taxRate) / 100) : 1);
                                                        //         editRowData.changeContractSumTax = (editRowData.changeQty ? editRowData.changeQty : 0) * (val ? Number(val) : 0) - ((editRowData.changeQty ? editRowData.changeQty : 0) * (val ? Number(val) : 0)) / (editRowData.taxRate ? (1 + Number(editRowData.taxRate) / 100) : 1);
                                                        //         btnCallbackFn.setEditedRowData({ ...editRowData });
                                                        //         // let tableData = this.table.form.getFieldsValue();
                                                        //         // tableData.pp4 = 0;
                                                        //         // tableData.pp4NoTax = 0;
                                                        //         // tableData.pp4Tax = 0;
                                                        //         // tableData.alterContractSum = 0;
                                                        //         // tableData.alterContractSumNoTax = 0;
                                                        //         // tableData.alterContractSumTax = 0;
                                                        //         // tableData.replenishShopDetailedList.map((item) => {
                                                        //         //     if (item.zxCtSuppliesContrReplenishShopDetailId === editRowData.zxCtSuppliesContrReplenishShopDetailId) {
                                                        //         //         tableData.pp4 += ((editRowData.changeContractSum ? editRowData.changeContractSum : 0) / 10000);
                                                        //         //         tableData.pp4NoTax += (editRowData.changeContractSumNoTax ? editRowData.changeContractSumNoTax : 0) / 10000;
                                                        //         //         tableData.pp4Tax += (editRowData.changeContractSumTax ? editRowData.changeContractSumTax : 0) / 10000;
                                                        //         //         tableData.alterContractSum += (((editRowData.changeContractSum ? editRowData.changeContractSum : 0) / 10000) + (tableData.contractCost ? tableData.contractCost : 0));
                                                        //         //         tableData.alterContractSumNoTax += (((editRowData.changeContractSumNoTax ? editRowData.changeContractSumNoTax : 0) / 10000) + (tableData.contractCostNoTax ? tableData.contractCostNoTax : 0));
                                                        //         //         tableData.alterContractSumTax += (((editRowData.changeContractSumTax ? editRowData.changeContractSumTax : 0) / 10000) + (tableData.contractCostTax ? tableData.contractCostTax : 0));
                                                        //         //     } else {
                                                        //         //         tableData.pp4 += ((item.changeContractSum ? item.changeContractSum : 0) / 10000);
                                                        //         //         tableData.pp4NoTax += (item.changeContractSumNoTax ? item.changeContractSumNoTax : 0) / 10000;
                                                        //         //         tableData.pp4Tax += (item.changeContractSumTax ? item.changeContractSumTax : 0) / 10000;
                                                        //         //         tableData.alterContractSum += (((item.changeContractSum ? item.changeContractSum : 0) / 10000) + (tableData.contractCost ? tableData.contractCost : 0));
                                                        //         //         tableData.alterContractSumNoTax += (((item.changeContractSumNoTax ? item.changeContractSumNoTax : 0) / 10000) + (tableData.contractCostNoTax ? tableData.contractCostNoTax : 0));
                                                        //         //         tableData.alterContractSumTax += (((item.changeContractSumTax ? item.changeContractSumTax : 0) / 10000) + (tableData.contractCostTax ? tableData.contractCostTax : 0));
                                                        //         //     }
                                                        //         //     return item;
                                                        //         // })
                                                        //         // tableData.pp4 = tableData.pp4.toFixed(6);
                                                        //         // this.table.form.setFieldsValue(tableData);
                                                        //     })
                                                        // }, 500),
                                                        placeholder: '请输入'
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '变更后不含税单价',
                                                        dataIndex: 'changePriceNoTax',
                                                        key: 'changePriceNoTax',
                                                        width: 130,
                                                        render: (data, rowData) => {
                                                            if (rowData.changePriceNoTax || rowData.changePriceNoTax === 0) {
                                                                return rowData.changePriceNoTax.toFixed(2)
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
                                                        title: '变更后含税金额',
                                                        dataIndex: 'changeContractSum',
                                                        key: 'changeContractSum',
                                                        width: 120,
                                                        render: (data, rowData) => {
                                                            if (rowData.changeContractSum || rowData.changeContractSum === 0) {
                                                                return rowData.changeContractSum.toFixed(2)
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
                                                        title: '变更后不含税金额',
                                                        dataIndex: 'changeContractSumNoTax',
                                                        key: 'changeContractSumNoTax',
                                                        width: 130,
                                                        render: (data, rowData) => {
                                                            if (rowData.changeContractSumNoTax || rowData.changeContractSumNoTax === 0) {
                                                                return rowData.changeContractSumNoTax.toFixed(2)
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
                                                        title: '变更后税额',
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
                                        type: "qnnTable",
                                        field: "replenishLeaseDetailedList",
                                        incToForm: true,
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
                                            tableTdEdit: true,
                                            actionBtns: [
                                                {
                                                    name: 'addRow',
                                                    icon: 'plus',
                                                    type: 'primary',
                                                    label: '新增',
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
                                                    label: '删除',
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
                                                        title: `<div>变更类型<span style='color: #ff4d4f'>*</span></div>`,
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
                                                            label: 'label', //默认 label
                                                            value: 'value'
                                                        },
                                                        optionData: [
                                                            {
                                                                label: '新增',
                                                                value: '1'
                                                            },
                                                            {
                                                                label: '修改',
                                                                value: '2'
                                                            }
                                                        ],
                                                        onChange: (val, obj, btnCallbackFn) => {
                                                            btnCallbackFn.getEditedRowData(false).then((editRowData) => {
                                                                editRowData.workTypeID = null;
                                                                editRowData.workType = null;
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
                                                        placeholder: '请选择'
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: `<div>物资类别<span style='color: #ff4d4f'>*</span></div>`,
                                                        dataIndex: 'workTypeID',
                                                        key: 'workTypeID',
                                                        width: 150,
                                                        // type: 'select',
                                                        tdEdit: true,
                                                        fixed: 'left',
                                                        render:(data,rowData) => {
                                                            return rowData.workType;
                                                        }
                                                    },
                                                    form: {
                                                        type: 'select',
                                                        disabled: (obj) => {
                                                            if (!obj.record.alterType || obj.record.alterType === '2') {
                                                                return true
                                                            } else {
                                                                return false;
                                                            }
                                                        },
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
                                                        allowClear: false,
                                                        showSearch: true,
                                                        placeholder: '请选择'
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: `<div>物资编码<span style='color: #ff4d4f'>*</span></div>`,
                                                        dataIndex: 'workID',
                                                        key: 'workID',
                                                        width: 150,
                                                        tdEdit: true,
                                                        fixed: 'left',
                                                        render: (data, rowData) => {
                                                            return rowData.workNo;
                                                        }
                                                    },
                                                    form: {
                                                        type: 'select',
                                                        optionConfig: (obj) => {
                                                            if (obj.rowData.alterType === '1') {
                                                                return {
                                                                    label: 'resCode', //默认 label
                                                                    value: 'id'
                                                                }
                                                            } else {
                                                                return {
                                                                    label: 'workNo', //默认 label
                                                                    value: 'workID'
                                                                }
                                                            }
                                                        },
                                                        disabled: (obj) => {
                                                            if (!obj.record.alterType) {
                                                                return true
                                                            } else {
                                                                return false;
                                                            }
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
                                                                }
                                                            } else {
                                                                return {
                                                                    apiName: 'getZxCtSuppliesContrLeaseListList',
                                                                    // apiName: 'getZxCtSuppliesContrApplyLeaseListList',
                                                                    otherParams: {
                                                                        contractID: this.table.form.getFieldValue('pp6')
                                                                    }
                                                                }
                                                            }
                                                        },
                                                        onChange: (val, obj, btnCallbackFn) => {
                                                            btnCallbackFn.getEditedRowData(false).then((editRowData) => {
                                                                if (editRowData.alterType === '1') {
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
                                                                    editRowData.rentUnit = obj.itemData.rentUnit;
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
                                                        allowClear: false,
                                                        showSearch: true,
                                                        placeholder: '请选择'
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '物资名称',
                                                        dataIndex: 'workName',
                                                        key: 'workName',
                                                        width: 100
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
                                                        disabled: (obj) => {
                                                            if (!obj.record.alterType || obj.record.alterType === '2') {
                                                                return true
                                                            } else {
                                                                return false;
                                                            }
                                                        },
                                                        optionData: [
                                                            {
                                                                value: "1"
                                                            }
                                                        ]
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '租期',
                                                        dataIndex: 'contrTrrm',
                                                        key: 'contrTrrm',
                                                        width: 100
                                                    },
                                                    form: {
                                                        type: 'number',
                                                        placeholder: '请输入'
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '数量',
                                                        dataIndex: 'qty',
                                                        key: 'qty',
                                                        width: 100,
                                                        render: (data, rowData) => {
                                                            if (rowData.qty || rowData.qty === 0) {
                                                                return rowData.qty.toFixed(2)
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
                                                        title: '含税合同单价',
                                                        dataIndex: 'price',
                                                        key: 'price',
                                                        width: 120,
                                                        render: (data, rowData) => {
                                                            if (rowData.price || rowData.price === 0) {
                                                                return rowData.price.toFixed(2)
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
                                                        title: '税率(%)',
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
                                                        disabled: (obj) => {
                                                            if (!obj.record.alterType || obj.record.alterType === '2') {
                                                                return true
                                                            } else {
                                                                return false;
                                                            }
                                                        },
                                                        onChange: (val, obj, btnCallbackFn) => {
                                                            btnCallbackFn.getEditedRowData(false).then((editRowData) => {
                                                                editRowData.changePriceNoTax = (editRowData.changePrice ? editRowData.changePrice : 0) / ((val ? (1 + Number(val) / 100) : 1));
                                                                editRowData.changeContractSum = (editRowData.changeQty ? editRowData.changeQty : 0) * (editRowData.changePrice ? editRowData.changePrice : 0) * (editRowData.alterTrrm ? editRowData.alterTrrm : 0);
                                                                editRowData.changeContractSumNoTax = ((editRowData.changeQty ? editRowData.changeQty : 0) * (editRowData.changePrice ? editRowData.changePrice : 0)) * (editRowData.alterTrrm ? editRowData.alterTrrm : 0) / (val ? (1 + Number(val) / 100) : 1);
                                                                editRowData.changeContractSumTax = (editRowData.changeQty ? editRowData.changeQty : 0) * (editRowData.changePrice ? editRowData.changePrice : 0) * (editRowData.alterTrrm ? editRowData.alterTrrm : 0) - ((editRowData.changeQty ? editRowData.changeQty : 0) * (editRowData.changePrice ? editRowData.changePrice : 0)) * (editRowData.alterTrrm ? editRowData.alterTrrm : 0) / (val ? (1 + Number(val) / 100) : 1);
                                                                btnCallbackFn.setEditedRowData({ ...editRowData });
                                                                // let tableData = this.table.form.getFieldsValue();
                                                                // tableData.pp4 = 0;
                                                                // tableData.pp4NoTax = 0;
                                                                // tableData.pp4Tax = 0;
                                                                // tableData.alterContractSum = 0;
                                                                // tableData.alterContractSumNoTax = 0;
                                                                // tableData.alterContractSumTax = 0;
                                                                // tableData.replenishLeaseDetailedList.map((item) => {
                                                                //     if (item.zxCtSuppliesContrReplenishLeaseDetailId === editRowData.zxCtSuppliesContrReplenishLeaseDetailId) {
                                                                //         tableData.pp4 += ((editRowData.changeContractSum ? editRowData.changeContractSum : 0) / 10000);
                                                                //         tableData.pp4NoTax += (editRowData.changeContractSumNoTax ? editRowData.changeContractSumNoTax : 0) / 10000;
                                                                //         tableData.pp4Tax += (editRowData.changeContractSumTax ? editRowData.changeContractSumTax : 0) / 10000;
                                                                //         tableData.alterContractSum += (((editRowData.changeContractSum ? editRowData.changeContractSum : 0) / 10000) + (tableData.contractCost ? tableData.contractCost : 0));
                                                                //         tableData.alterContractSumNoTax += (((editRowData.changeContractSumNoTax ? editRowData.changeContractSumNoTax : 0) / 10000) + (tableData.contractCostNoTax ? tableData.contractCostNoTax : 0));
                                                                //         tableData.alterContractSumTax += (((editRowData.changeContractSumTax ? editRowData.changeContractSumTax : 0) / 10000) + (tableData.contractCostTax ? tableData.contractCostTax : 0));
                                                                //     } else {
                                                                //         tableData.pp4 += ((item.changeContractSum ? item.changeContractSum : 0) / 10000);
                                                                //         tableData.pp4NoTax += (item.changeContractSumNoTax ? item.changeContractSumNoTax : 0) / 10000;
                                                                //         tableData.pp4Tax += (item.changeContractSumTax ? item.changeContractSumTax : 0) / 10000;
                                                                //         tableData.alterContractSum += (((item.changeContractSum ? item.changeContractSum : 0) / 10000) + (tableData.contractCost ? tableData.contractCost : 0));
                                                                //         tableData.alterContractSumNoTax += (((item.changeContractSumNoTax ? item.changeContractSumNoTax : 0) / 10000) + (tableData.contractCostNoTax ? tableData.contractCostNoTax : 0));
                                                                //         tableData.alterContractSumTax += (((item.changeContractSumTax ? item.changeContractSumTax : 0) / 10000) + (tableData.contractCostTax ? tableData.contractCostTax : 0));
                                                                //     }
                                                                //     return item;
                                                                // })
                                                                // tableData.pp4 = tableData.pp4.toFixed(6);
                                                                // this.table.form.setFieldsValue(tableData);
                                                            })
                                                        },
                                                        allowClear: false,
                                                        showSearch: true,
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
                                                        title: '已结算数量',
                                                        dataIndex: 'a',
                                                        key: 'a',
                                                        width: 100,
                                                        render: (data, rowData) => {
                                                            if (rowData.a || rowData.a === 0) {
                                                                return rowData.a.toFixed(2)
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
                                                        title: '租期单位',
                                                        dataIndex: 'rentUnit',
                                                        key: 'rentUnit',
                                                        width: 100,
                                                        tdEdit: true
                                                    },
                                                    form: {
                                                        type: 'string',
                                                        disabled: (obj) => {
                                                            if (!obj.record.alterType || obj.record.alterType === '2') {
                                                                return true
                                                            } else {
                                                                return false;
                                                            }
                                                        },
                                                        placeholder: '请输入'
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '变更后租期',
                                                        dataIndex: 'alterTrrm',
                                                        key: 'alterTrrm',
                                                        width: 100,
                                                        tdEdit: true
                                                    },
                                                    form: {
                                                        type: 'number',
                                                        onBlur: (e, obj, btnCallbackFn) => {
                                                            let val = e.target.value;
                                                            setTimeout(async () => {
                                                                await btnCallbackFn.getEditedRowData(false).then((editRowData) => {
                                                                    editRowData.alterTrrm = val ? Number(val) : 0;
                                                                    editRowData.changePriceNoTax = (editRowData.changePrice ? editRowData.changePrice : 0) / ((editRowData.taxRate ? (1 + Number(editRowData.taxRate) / 100) : 1));
                                                                    editRowData.changeContractSum = (editRowData.changeQty ? editRowData.changeQty : 0) * (editRowData.changePrice ? editRowData.changePrice : 0) * (val ? Number(val) : 0);
                                                                    editRowData.changeContractSumNoTax = ((editRowData.changeQty ? editRowData.changeQty : 0) * (editRowData.changePrice ? editRowData.changePrice : 0)) * (val ? Number(val) : 0) / (editRowData.taxRate ? (1 + Number(editRowData.taxRate) / 100) : 1);
                                                                    editRowData.changeContractSumTax = (editRowData.changeQty ? editRowData.changeQty : 0) * (editRowData.changePrice ? editRowData.changePrice : 0) * (val ? Number(val) : 0) - ((editRowData.changeQty ? editRowData.changeQty : 0) * (editRowData.changePrice ? editRowData.changePrice : 0)) * (val ? Number(val) : 0) / (editRowData.taxRate ? (1 + Number(editRowData.taxRate) / 100) : 1);
                                                                    btnCallbackFn.setEditedRowData({ ...editRowData });
                                                                })
                                                            }, 0)
                                                        },
                                                        // onBlur: (obj) => {
                                                        //     let val = obj.target.value;
                                                        //     this.tableOne.getEditedRowData(false).then((editRowData) => {
                                                        //         editRowData.alterTrrm = val ? Number(val) : 0;
                                                        //         editRowData.changePriceNoTax = (editRowData.changePrice ? editRowData.changePrice : 0) / ((editRowData.taxRate ? (1 + Number(editRowData.taxRate) / 100) : 1));
                                                        //         editRowData.changeContractSum = (editRowData.changeQty ? editRowData.changeQty : 0) * (editRowData.changePrice ? editRowData.changePrice : 0) * (val ? Number(val) : 0);
                                                        //         editRowData.changeContractSumNoTax = ((editRowData.changeQty ? editRowData.changeQty : 0) * (editRowData.changePrice ? editRowData.changePrice : 0)) * (val ? Number(val) : 0) / (editRowData.taxRate ? (1 + Number(editRowData.taxRate) / 100) : 1);
                                                        //         editRowData.changeContractSumTax = (editRowData.changeQty ? editRowData.changeQty : 0) * (editRowData.changePrice ? editRowData.changePrice : 0) * (val ? Number(val) : 0) - ((editRowData.changeQty ? editRowData.changeQty : 0) * (editRowData.changePrice ? editRowData.changePrice : 0)) * (val ? Number(val) : 0) / (editRowData.taxRate ? (1 + Number(editRowData.taxRate) / 100) : 1);
                                                        //         this.tableOne.setEditedRowData({ ...editRowData });
                                                        //     })
                                                        // },
                                                        // onChange: debounce((val, obj, btnCallbackFn) => {
                                                        //     btnCallbackFn.getEditedRowData(false).then((editRowData) => {
                                                        //         editRowData.alterTrrm = val ? Number(val) : 0;
                                                        //         editRowData.changePriceNoTax = (editRowData.changePrice ? editRowData.changePrice : 0) / ((editRowData.taxRate ? (1 + Number(editRowData.taxRate) / 100) : 1));
                                                        //         editRowData.changeContractSum = (editRowData.changeQty ? editRowData.changeQty : 0) * (editRowData.changePrice ? editRowData.changePrice : 0) * (val ? Number(val) : 0);
                                                        //         editRowData.changeContractSumNoTax = ((editRowData.changeQty ? editRowData.changeQty : 0) * (editRowData.changePrice ? editRowData.changePrice : 0)) * (val ? Number(val) : 0) / (editRowData.taxRate ? (1 + Number(editRowData.taxRate) / 100) : 1);
                                                        //         editRowData.changeContractSumTax = (editRowData.changeQty ? editRowData.changeQty : 0) * (editRowData.changePrice ? editRowData.changePrice : 0) * (val ? Number(val) : 0) - ((editRowData.changeQty ? editRowData.changeQty : 0) * (editRowData.changePrice ? editRowData.changePrice : 0)) * (val ? Number(val) : 0) / (editRowData.taxRate ? (1 + Number(editRowData.taxRate) / 100) : 1);
                                                        //         btnCallbackFn.setEditedRowData({ ...editRowData });
                                                        //         // let tableData = this.table.form.getFieldsValue();
                                                        //         // tableData.pp4 = 0;
                                                        //         // tableData.pp4NoTax = 0;
                                                        //         // tableData.pp4Tax = 0;
                                                        //         // tableData.alterContractSum = 0;
                                                        //         // tableData.alterContractSumNoTax = 0;
                                                        //         // tableData.alterContractSumTax = 0;
                                                        //         // tableData.replenishLeaseDetailedList.map((item) => {
                                                        //         //     if (item.zxCtSuppliesContrReplenishLeaseDetailId === editRowData.zxCtSuppliesContrReplenishLeaseDetailId) {
                                                        //         //         tableData.pp4 += ((editRowData.changeContractSum ? editRowData.changeContractSum : 0) / 10000);
                                                        //         //         tableData.pp4NoTax += (editRowData.changeContractSumNoTax ? editRowData.changeContractSumNoTax : 0) / 10000;
                                                        //         //         tableData.pp4Tax += (editRowData.changeContractSumTax ? editRowData.changeContractSumTax : 0) / 10000;
                                                        //         //         tableData.alterContractSum += (((editRowData.changeContractSum ? editRowData.changeContractSum : 0) / 10000) + (tableData.contractCost ? tableData.contractCost : 0));
                                                        //         //         tableData.alterContractSumNoTax += (((editRowData.changeContractSumNoTax ? editRowData.changeContractSumNoTax : 0) / 10000) + (tableData.contractCostNoTax ? tableData.contractCostNoTax : 0));
                                                        //         //         tableData.alterContractSumTax += (((editRowData.changeContractSumTax ? editRowData.changeContractSumTax : 0) / 10000) + (tableData.contractCostTax ? tableData.contractCostTax : 0));
                                                        //         //     } else {
                                                        //         //         tableData.pp4 += ((item.changeContractSum ? item.changeContractSum : 0) / 10000);
                                                        //         //         tableData.pp4NoTax += (item.changeContractSumNoTax ? item.changeContractSumNoTax : 0) / 10000;
                                                        //         //         tableData.pp4Tax += (item.changeContractSumTax ? item.changeContractSumTax : 0) / 10000;
                                                        //         //         tableData.alterContractSum += (((item.changeContractSum ? item.changeContractSum : 0) / 10000) + (tableData.contractCost ? tableData.contractCost : 0));
                                                        //         //         tableData.alterContractSumNoTax += (((item.changeContractSumNoTax ? item.changeContractSumNoTax : 0) / 10000) + (tableData.contractCostNoTax ? tableData.contractCostNoTax : 0));
                                                        //         //         tableData.alterContractSumTax += (((item.changeContractSumTax ? item.changeContractSumTax : 0) / 10000) + (tableData.contractCostTax ? tableData.contractCostTax : 0));
                                                        //         //     }
                                                        //         //     return item;
                                                        //         // })
                                                        //         // tableData.pp4 = tableData.pp4.toFixed(6);
                                                        //         // this.table.form.setFieldsValue(tableData);
                                                        //     })
                                                        // },500),
                                                        placeholder: '请输入'
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '变更后数量',
                                                        dataIndex: 'changeQty',
                                                        key: 'changeQty',
                                                        width: 120,
                                                        tdEdit: true,
                                                        render: (data, rowData) => {
                                                            if (rowData.changeQty || rowData.changeQty === 0) {
                                                                return rowData.changeQty.toFixed(2)
                                                            }
                                                        }
                                                    },
                                                    form: {
                                                        type: 'number',
                                                        precision: 2,
                                                        onBlur: (e, obj, btnCallbackFn) => {
                                                            let val = e.target.value;
                                                            setTimeout(async () => {
                                                                await btnCallbackFn.getEditedRowData(false).then((editRowData) => {
                                                                    editRowData.changeQty = val ? Number(val) : 0;
                                                                    editRowData.changePriceNoTax = (editRowData.changePrice ? editRowData.changePrice : 0) / ((editRowData.taxRate ? (1 + Number(editRowData.taxRate) / 100) : 1));
                                                                    editRowData.changeContractSum = (val ? Number(val) : 0) * (editRowData.changePrice ? editRowData.changePrice : 0) * (editRowData.alterTrrm ? editRowData.alterTrrm : 0);
                                                                    editRowData.changeContractSumNoTax = ((val ? Number(val) : 0) * (editRowData.changePrice ? editRowData.changePrice : 0)) * (editRowData.alterTrrm ? editRowData.alterTrrm : 0) / (editRowData.taxRate ? (1 + Number(editRowData.taxRate) / 100) : 1);
                                                                    editRowData.changeContractSumTax = (val ? Number(val) : 0) * (editRowData.changePrice ? editRowData.changePrice : 0) * (editRowData.alterTrrm ? editRowData.alterTrrm : 0) - ((val ? Number(val) : 0) * (editRowData.changePrice ? editRowData.changePrice : 0)) * (editRowData.alterTrrm ? editRowData.alterTrrm : 0) / (editRowData.taxRate ? (1 + Number(editRowData.taxRate) / 100) : 1);
                                                                    btnCallbackFn.setEditedRowData({ ...editRowData });
                                                                })
                                                            }, 0)
                                                        },
                                                        // onBlur: (obj) => {
                                                        //     let val = obj.target.value;
                                                        //     this.tableOne.getEditedRowData(false).then((editRowData) => {
                                                        //         editRowData.changeQty = val ? Number(val) : 0;
                                                        //         editRowData.changePriceNoTax = (editRowData.changePrice ? editRowData.changePrice : 0) / ((editRowData.taxRate ? (1 + Number(editRowData.taxRate) / 100) : 1));
                                                        //         editRowData.changeContractSum = (val ? Number(val) : 0) * (editRowData.changePrice ? editRowData.changePrice : 0) * (editRowData.alterTrrm ? editRowData.alterTrrm : 0);
                                                        //         editRowData.changeContractSumNoTax = ((val ? Number(val) : 0) * (editRowData.changePrice ? editRowData.changePrice : 0)) * (editRowData.alterTrrm ? editRowData.alterTrrm : 0) / (editRowData.taxRate ? (1 + Number(editRowData.taxRate) / 100) : 1);
                                                        //         editRowData.changeContractSumTax = (val ? Number(val) : 0) * (editRowData.changePrice ? editRowData.changePrice : 0) * (editRowData.alterTrrm ? editRowData.alterTrrm : 0) - ((val ? Number(val) : 0) * (editRowData.changePrice ? editRowData.changePrice : 0)) * (editRowData.alterTrrm ? editRowData.alterTrrm : 0) / (editRowData.taxRate ? (1 + Number(editRowData.taxRate) / 100) : 1);
                                                        //         this.tableOne.setEditedRowData({ ...editRowData });
                                                        //     })
                                                        // },
                                                        // onChange: debounce((val, obj, btnCallbackFn) => {
                                                        //     btnCallbackFn.getEditedRowData(false).then((editRowData) => {
                                                        //         editRowData.changeQty = val ? Number(val) : 0;
                                                        //         editRowData.changePriceNoTax = (editRowData.changePrice ? editRowData.changePrice : 0) / ((editRowData.taxRate ? (1 + Number(editRowData.taxRate) / 100) : 1));
                                                        //         editRowData.changeContractSum = (val ? Number(val) : 0) * (editRowData.changePrice ? editRowData.changePrice : 0) * (editRowData.alterTrrm ? editRowData.alterTrrm : 0);
                                                        //         editRowData.changeContractSumNoTax = ((val ? Number(val) : 0) * (editRowData.changePrice ? editRowData.changePrice : 0)) * (editRowData.alterTrrm ? editRowData.alterTrrm : 0) / (editRowData.taxRate ? (1 + Number(editRowData.taxRate) / 100) : 1);
                                                        //         editRowData.changeContractSumTax = (val ? Number(val) : 0) * (editRowData.changePrice ? editRowData.changePrice : 0) * (editRowData.alterTrrm ? editRowData.alterTrrm : 0) - ((val ? Number(val) : 0) * (editRowData.changePrice ? editRowData.changePrice : 0)) * (editRowData.alterTrrm ? editRowData.alterTrrm : 0) / (editRowData.taxRate ? (1 + Number(editRowData.taxRate) / 100) : 1);
                                                        //         btnCallbackFn.setEditedRowData({ ...editRowData });
                                                        //         // let tableData = this.table.form.getFieldsValue();
                                                        //         // tableData.pp4 = 0;
                                                        //         // tableData.pp4NoTax = 0;
                                                        //         // tableData.pp4Tax = 0;
                                                        //         // tableData.alterContractSum = 0;
                                                        //         // tableData.alterContractSumNoTax = 0;
                                                        //         // tableData.alterContractSumTax = 0;
                                                        //         // tableData.replenishLeaseDetailedList.map((item) => {
                                                        //         //     if (item.zxCtSuppliesContrReplenishLeaseDetailId === editRowData.zxCtSuppliesContrReplenishLeaseDetailId) {
                                                        //         //         tableData.pp4 += ((editRowData.changeContractSum ? editRowData.changeContractSum : 0) / 10000);
                                                        //         //         tableData.pp4NoTax += (editRowData.changeContractSumNoTax ? editRowData.changeContractSumNoTax : 0) / 10000;
                                                        //         //         tableData.pp4Tax += (editRowData.changeContractSumTax ? editRowData.changeContractSumTax : 0) / 10000;
                                                        //         //         tableData.alterContractSum += (((editRowData.changeContractSum ? editRowData.changeContractSum : 0) / 10000) + (tableData.contractCost ? tableData.contractCost : 0));
                                                        //         //         tableData.alterContractSumNoTax += (((editRowData.changeContractSumNoTax ? editRowData.changeContractSumNoTax : 0) / 10000) + (tableData.contractCostNoTax ? tableData.contractCostNoTax : 0));
                                                        //         //         tableData.alterContractSumTax += (((editRowData.changeContractSumTax ? editRowData.changeContractSumTax : 0) / 10000) + (tableData.contractCostTax ? tableData.contractCostTax : 0));
                                                        //         //     } else {
                                                        //         //         tableData.pp4 += ((item.changeContractSum ? item.changeContractSum : 0) / 10000);
                                                        //         //         tableData.pp4NoTax += (item.changeContractSumNoTax ? item.changeContractSumNoTax : 0) / 10000;
                                                        //         //         tableData.pp4Tax += (item.changeContractSumTax ? item.changeContractSumTax : 0) / 10000;
                                                        //         //         tableData.alterContractSum += (((item.changeContractSum ? item.changeContractSum : 0) / 10000) + (tableData.contractCost ? tableData.contractCost : 0));
                                                        //         //         tableData.alterContractSumNoTax += (((item.changeContractSumNoTax ? item.changeContractSumNoTax : 0) / 10000) + (tableData.contractCostNoTax ? tableData.contractCostNoTax : 0));
                                                        //         //         tableData.alterContractSumTax += (((item.changeContractSumTax ? item.changeContractSumTax : 0) / 10000) + (tableData.contractCostTax ? tableData.contractCostTax : 0));
                                                        //         //     }
                                                        //         //     return item;
                                                        //         // })
                                                        //         // tableData.pp4 = tableData.pp4.toFixed(6);
                                                        //         // this.table.form.setFieldsValue(tableData);
                                                        //     })
                                                        // },500),
                                                        placeholder: '请输入'
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '变更后含税单价',
                                                        dataIndex: 'changePrice',
                                                        key: 'changePrice',
                                                        width: 120,
                                                        tdEdit: true,
                                                        render: (data, rowData) => {
                                                            if (rowData.changePrice || rowData.changePrice === 0) {
                                                                return rowData.changePrice.toFixed(2)
                                                            }
                                                        }
                                                    },
                                                    form: {
                                                        type: 'number',
                                                        precision: 2,
                                                        disabled: (obj) => {
                                                            if (!obj.record.alterType || obj.record.alterType === '2') {
                                                                return true
                                                            } else {
                                                                return false;
                                                            }
                                                        },
                                                        onBlur: (e, obj, btnCallbackFn) => {
                                                            let val = e.target.value;
                                                            setTimeout(async () => {
                                                                await btnCallbackFn.getEditedRowData(false).then((editRowData) => {
                                                                    editRowData.changePrice = val ? Number(val) : 0;
                                                                editRowData.changePriceNoTax = (val ? Number(val) : 0) / ((editRowData.taxRate ? (1 + Number(editRowData.taxRate) / 100) : 1));
                                                                editRowData.changeContractSum = (editRowData.changeQty ? editRowData.changeQty : 0) * (val ? Number(val) : 0) * (editRowData.alterTrrm ? editRowData.alterTrrm : 0);
                                                                editRowData.changeContractSumNoTax = ((editRowData.changeQty ? editRowData.changeQty : 0) * (val ? Number(val) : 0)) * (editRowData.alterTrrm ? editRowData.alterTrrm : 0) / (editRowData.taxRate ? (1 + Number(editRowData.taxRate) / 100) : 1);
                                                                editRowData.changeContractSumTax = (editRowData.changeQty ? editRowData.changeQty : 0) * (val ? Number(val) : 0) * (editRowData.alterTrrm ? editRowData.alterTrrm : 0) - ((editRowData.changeQty ? editRowData.changeQty : 0) * (val ? Number(val) : 0)) * (editRowData.alterTrrm ? editRowData.alterTrrm : 0) / (editRowData.taxRate ? (1 + Number(editRowData.taxRate) / 100) : 1);
                                                                    btnCallbackFn.setEditedRowData({ ...editRowData });
                                                                })
                                                            }, 0)
                                                        },
                                                        // onBlur: (obj) => {
                                                        //     let val = obj.target.value;
                                                        //     this.tableOne.getEditedRowData(false).then((editRowData) => {
                                                        //         editRowData.changePrice = val ? Number(val) : 0;
                                                        //         editRowData.changePriceNoTax = (val ? Number(val) : 0) / ((editRowData.taxRate ? (1 + Number(editRowData.taxRate) / 100) : 1));
                                                        //         editRowData.changeContractSum = (editRowData.changeQty ? editRowData.changeQty : 0) * (val ? Number(val) : 0) * (editRowData.alterTrrm ? editRowData.alterTrrm : 0);
                                                        //         editRowData.changeContractSumNoTax = ((editRowData.changeQty ? editRowData.changeQty : 0) * (val ? Number(val) : 0)) * (editRowData.alterTrrm ? editRowData.alterTrrm : 0) / (editRowData.taxRate ? (1 + Number(editRowData.taxRate) / 100) : 1);
                                                        //         editRowData.changeContractSumTax = (editRowData.changeQty ? editRowData.changeQty : 0) * (val ? Number(val) : 0) * (editRowData.alterTrrm ? editRowData.alterTrrm : 0) - ((editRowData.changeQty ? editRowData.changeQty : 0) * (val ? Number(val) : 0)) * (editRowData.alterTrrm ? editRowData.alterTrrm : 0) / (editRowData.taxRate ? (1 + Number(editRowData.taxRate) / 100) : 1);
                                                        //         this.tableOne.setEditedRowData({ ...editRowData });
                                                        //     })
                                                        // },
                                                        // onChange: debounce((val, obj, btnCallbackFn) => {
                                                        //     btnCallbackFn.getEditedRowData(false).then((editRowData) => {
                                                        //         editRowData.changePrice = val ? Number(val) : 0;
                                                        //         editRowData.changePriceNoTax = (val ? Number(val) : 0) / ((editRowData.taxRate ? (1 + Number(editRowData.taxRate) / 100) : 1));
                                                        //         editRowData.changeContractSum = (editRowData.changeQty ? editRowData.changeQty : 0) * (val ? Number(val) : 0) * (editRowData.alterTrrm ? editRowData.alterTrrm : 0);
                                                        //         editRowData.changeContractSumNoTax = ((editRowData.changeQty ? editRowData.changeQty : 0) * (val ? Number(val) : 0)) * (editRowData.alterTrrm ? editRowData.alterTrrm : 0) / (editRowData.taxRate ? (1 + Number(editRowData.taxRate) / 100) : 1);
                                                        //         editRowData.changeContractSumTax = (editRowData.changeQty ? editRowData.changeQty : 0) * (val ? Number(val) : 0) * (editRowData.alterTrrm ? editRowData.alterTrrm : 0) - ((editRowData.changeQty ? editRowData.changeQty : 0) * (val ? Number(val) : 0)) * (editRowData.alterTrrm ? editRowData.alterTrrm : 0) / (editRowData.taxRate ? (1 + Number(editRowData.taxRate) / 100) : 1);
                                                        //         btnCallbackFn.setEditedRowData({ ...editRowData });
                                                        //         // let tableData = this.table.form.getFieldsValue();
                                                        //         // tableData.pp4 = 0;
                                                        //         // tableData.pp4NoTax = 0;
                                                        //         // tableData.pp4Tax = 0;
                                                        //         // tableData.alterContractSum = 0;
                                                        //         // tableData.alterContractSumNoTax = 0;
                                                        //         // tableData.alterContractSumTax = 0;
                                                        //         // tableData.replenishLeaseDetailedList.map((item) => {
                                                        //         //     if (item.zxCtSuppliesContrReplenishLeaseDetailId === editRowData.zxCtSuppliesContrReplenishLeaseDetailId) {
                                                        //         //         tableData.pp4 += ((editRowData.changeContractSum ? editRowData.changeContractSum : 0) / 10000);
                                                        //         //         tableData.pp4NoTax += (editRowData.changeContractSumNoTax ? editRowData.changeContractSumNoTax : 0) / 10000;
                                                        //         //         tableData.pp4Tax += (editRowData.changeContractSumTax ? editRowData.changeContractSumTax : 0) / 10000;
                                                        //         //         tableData.alterContractSum += (((editRowData.changeContractSum ? editRowData.changeContractSum : 0) / 10000) + (tableData.contractCost ? tableData.contractCost : 0));
                                                        //         //         tableData.alterContractSumNoTax += (((editRowData.changeContractSumNoTax ? editRowData.changeContractSumNoTax : 0) / 10000) + (tableData.contractCostNoTax ? tableData.contractCostNoTax : 0));
                                                        //         //         tableData.alterContractSumTax += (((editRowData.changeContractSumTax ? editRowData.changeContractSumTax : 0) / 10000) + (tableData.contractCostTax ? tableData.contractCostTax : 0));
                                                        //         //     } else {
                                                        //         //         tableData.pp4 += ((item.changeContractSum ? item.changeContractSum : 0) / 10000);
                                                        //         //         tableData.pp4NoTax += (item.changeContractSumNoTax ? item.changeContractSumNoTax : 0) / 10000;
                                                        //         //         tableData.pp4Tax += (item.changeContractSumTax ? item.changeContractSumTax : 0) / 10000;
                                                        //         //         tableData.alterContractSum += (((item.changeContractSum ? item.changeContractSum : 0) / 10000) + (tableData.contractCost ? tableData.contractCost : 0));
                                                        //         //         tableData.alterContractSumNoTax += (((item.changeContractSumNoTax ? item.changeContractSumNoTax : 0) / 10000) + (tableData.contractCostNoTax ? tableData.contractCostNoTax : 0));
                                                        //         //         tableData.alterContractSumTax += (((item.changeContractSumTax ? item.changeContractSumTax : 0) / 10000) + (tableData.contractCostTax ? tableData.contractCostTax : 0));
                                                        //         //     }
                                                        //         //     return item;
                                                        //         // })
                                                        //         // tableData.pp4 = tableData.pp4.toFixed(6);
                                                        //         // this.table.form.setFieldsValue(tableData);
                                                        //     })
                                                        // },500),
                                                        placeholder: '请输入'
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '变更后不含税单价',
                                                        dataIndex: 'changePriceNoTax',
                                                        key: 'changePriceNoTax',
                                                        width: 130,
                                                        render: (data, rowData) => {
                                                            if (rowData.changePriceNoTax || rowData.changePriceNoTax === 0) {
                                                                return rowData.changePriceNoTax.toFixed(2)
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
                                                        title: '变更后含税金额',
                                                        dataIndex: 'changeContractSum',
                                                        key: 'changeContractSum',
                                                        width: 120,
                                                        render: (data, rowData) => {
                                                            if (rowData.changeContractSum || rowData.changeContractSum === 0) {
                                                                return rowData.changeContractSum.toFixed(2)
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
                                                        title: '变更后不含税金额',
                                                        dataIndex: 'changeContractSumNoTax',
                                                        key: 'changeContractSumNoTax',
                                                        width: 130,
                                                        render: (data, rowData) => {
                                                            if (rowData.changeContractSumNoTax || rowData.changeContractSumNoTax === 0) {
                                                                return rowData.changeContractSumNoTax.toFixed(2)
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
                                                        title: '变更后税额',
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
                                        label: '附件',
                                        field: 'replenishShopListFileList',
                                        type: 'files',
                                        fetchConfig: {
                                            apiName: 'upload'
                                        },
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 4 },
                                                sm: { span: 4 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 24 },
                                                sm: { span: 24 }
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
                                field: 'replenishAgreementId',
                                type: 'string',
                                hide: true,
                            }
                        },
                        {
                            table: {
                                title: '补充协议编号',
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
                                title: '补充协议名称',
                                dataIndex: 'pp3',
                                key: 'pp3',
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
                                        return rowData.contractCost.toFixed(2)
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
                                title: '本期含税增减金额(万元)',
                                dataIndex: 'pp4',
                                key: 'pp4',
                                width: 180,
                                render: (data, rowData) => {
                                    if (rowData.pp4 || rowData.pp4 === 0) {
                                        return Number(rowData.pp4).toFixed(2)
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
                                title: '变更后含税金额(万元)',
                                dataIndex: 'alterContractSum',
                                key: 'alterContractSum',
                                width: 180,
                                render: (data, rowData) => {
                                    if (rowData.alterContractSum || rowData.alterContractSum === 0) {
                                        return rowData.alterContractSum.toFixed(2)
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
                                title: '开工日期',
                                dataIndex: 'startDate',
                                key: 'startDate',
                                width: 100,
                                format: 'YYYY-MM-DD',
                            },
                            form: {
                                type: 'date',
                                placeholder: '请输入'
                            }
                        },
                        {
                            table: {
                                title: '竣工日期',
                                dataIndex: 'endDate',
                                key: 'endDate',
                                width: 100,
                                format: 'YYYY-MM-DD',
                            },
                            form: {
                                type: 'date',
                                placeholder: '请输入'
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
                                        return '已审核';
                                    } else if (data === '3') {
                                        return '退回';
                                    }
                                }
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '物资来源',
                                dataIndex: 'materialSource',
                                key: 'materialSource',
                                width: 100,
                                type: 'select',
                                fixed: 'right',
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
                        }
                    ]}
                />
            </div>
        );
    }
}

export default index;