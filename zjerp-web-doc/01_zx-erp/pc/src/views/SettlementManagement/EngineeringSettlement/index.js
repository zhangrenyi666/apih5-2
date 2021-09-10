import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import { message as Msg, Modal } from 'antd';
import EngineeringSettlementForm from './form';
import ManageView from './manageView';
import DetailView from './detail';
import moment from 'moment';
import Operation from './operation'
import QnnForm from "../../modules/qnn-form";
import Button from "antd/es/button";
const config = {
    antd: {
        rowKey: 'id',
        size: 'small'
    },
    desc: '本页面所有金额单位为: 元',
    drawerConfig: {
        width: '80%'
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
            showModel: false,
            departmentId: ((curCompany?.ext1 === '1' || curCompany?.ext1 === '2') && lockProject?.projectId) ? (lockProject.projectId !== 'all' ? lockProject.projectId : curCompany?.companyId) : curCompany?.projectId,
        }
    }

    handleCancelBjdh = () => {
        this.setState({ showModel: false });
    }
    render() {
        const { departmentId, showModel } = this.state;
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
                        apiName: 'getZxSaProjectSettleAuditList',
                        otherParams: { orgID: departmentId }
                    }}
                    drawerShowToggle={(obj) => {
                        let { drawerIsShow } = obj;
                        if (!drawerIsShow) {
                            obj.btnCallbackFn.refresh();
                            obj.btnCallbackFn.clearSelectedRows()
                        }
                    }}
                    desc='本页面所有金额单位为：元'
                    tabsWillChange={(obj, canChange) => {
                        if (this.tableFour) {
                            this.tableFour.refresh();
                        }
                        canChange(true);
                    }}
                    componentsKey={{
                        EngineeringSettlementForm: (obj) => {
                            let data = obj.qnnTableInstance.getSelectedRows()
                            return <EngineeringSettlementForm {...this.props} btnCallbackFn={obj.btnCallbackFn} isInQnnTable={obj.isInQnnTable} flowData={data[0]} />
                        },
                        ManageView: (obj) => {
                            let data = obj.qnnTableInstance.getSelectedRows()
                            return <ManageView {...this.props} btnCallbackFn={obj.btnCallbackFn} flowData={data[0]} />
                        },
                        DetailView: (obj) => {
                            let data = obj.qnnTableInstance.getSelectedRows()
                            return <DetailView {...this.props} flowData={data[0]} btnCallbackFn={obj.btnCallbackFn} isInQnnTable={obj.isInQnnTable} />
                        },
                        Operation: (obj) => {
                            return <Operation apiName={'openFlowByReport'} {...this.props} btnCallbackFn={obj.btnCallbackFn} />
                        },
                    }}
                    method={{
                        //抽屉取消按钮是否显示
                        hideForCancel: (obj) => {
                            let index = obj.btnCallbackFn.getActiveKey();
                            if (index === '3') return true
                            return false
                        },
                        //新增保存按钮
                        hideForaddSubmit: (obj) => {
                            let index = obj.btnCallbackFn.getActiveKey();
                            if (index !== "0" || obj?.btnCallbackFn?.form?.getFieldValue?.('id')) return true;
                            return false;
                        },
                        //修改保存按钮
                        hideForEditSubmit: (obj) => {
                            let index = obj.btnCallbackFn.getActiveKey();
                            if (index !== "0") return true;
                            return false;
                        },
                        //修改按钮禁用
                        disabledFunEdit: (obj) => {
                            let data = obj.btnCallbackFn.getSelectedRows();
                            if (data.length !== 1 || data[0].apih5FlowStatus === '1' || data[0].apih5FlowStatus === '2') {
                                return true;
                            } else {
                                return false;
                            }
                        },
                        //营改增按钮禁用
                        disabledForYGZ: (obj) => {
                            let data = obj.btnCallbackFn.getSelectedRows();
                            if (data.length !== 1) return true
                            return false
                        },
                        //新增保存按钮回调
                        addSuccessCb: (obj) => {
                            if (obj.response.success) {
                                this.table.setDeawerValues({ ...obj.response.data });
                                this.table.setTabsIndex('1');
                            }
                        },
                        //修改保存按钮回调
                        editSuccessCb: (obj) => {
                            if (obj.response.success) {
                                this.table.setTabsIndex('1');
                            }
                        },
                        //清单保存按钮
                        listSubmitHide: (obj) => {
                            let index = obj.btnCallbackFn.getActiveKey();
                            if (index !== "1") return true;
                            return false;
                        },
                        //清单保存按钮配置
                        listSubmitFetch: async (obj) => {
                            let params = {}
                            let workSettleItemList = obj.curFormData.workSettleItemList
                            params.zxSaProjectWorkSettleItemList = workSettleItemList
                            params.period = this.table.form.getFieldValue('period')
                            params.contractID = this.table.form.getFieldValue('contractID')
                            return {
                                apiName: 'batchSaveOrUpdateZxSaProjectWorkSettleItem',
                                otherParams: params,
                                success: ({ success }) => {
                                    if (success) {
                                        let params = {
                                            id: this.table.form.getFieldValue('id'),
                                            workPayFlag: '1'
                                        }
                                        this.props.myFetch('getZxSaProjectSettleAuditDetail', params)
                                            .then(({ data, success }) => {
                                                if (success) {
                                                    obj.qnnTableInstance.getQnnForm().form.setFieldsValue({
                                                        workContractAmt: data.workContractAmt,
                                                        workChangeAmt: data.workChangeAmt,
                                                        workThisAmt: data.workThisAmt,
                                                        workThisAmtNoTax: data.workThisAmtNoTax,
                                                        workThisAmtTax: data.workThisAmtTax,
                                                        workTotalAmt: data.workTotalAmt,
                                                        workSettleItemList: data.workSettleItemList
                                                    })
                                                }
                                            })
                                    }
                                }
                            }
                        },
                        //支付项按钮是否隐藏
                        hideForPaySubmit: (obj) => {
                            let index = obj.btnCallbackFn.getActiveKey();
                            if (index !== "2") return true;
                            return false;
                        },
                        //支付项按钮配置
                        cbForPaySubmit: async (obj) => {
                            let params = {}
                            await obj.curFormData.paySettleItemList.map((item) => {
                                if (item.addFlag === '1') item.projectPaySettleItemId = null
                                return item
                            })
                            params.zxSaProjectPaySettleItemList = obj.curFormData.paySettleItemList
                            return {
                                apiName: 'batchSaveOrUpdateZxSaProjectPaySettleItem',
                                otherParams: params,
                                success: ({ success }) => {
                                    if (success) {
                                        let params = {
                                            id: this.table.form.getFieldValue('id'),
                                            workPayFlag: '2'
                                        }
                                        this.props.myFetch('getZxSaProjectSettleAuditDetail', params)
                                            .then(({ data, success }) => {
                                                if (success) {
                                                    obj.qnnTableInstance.getQnnForm().form.setFieldsValue({
                                                        payThisAmt: data.payThisAmt,
                                                        payThisAmtNoTax: data.payThisAmtNoTax,
                                                        payMaterialAmt: data.payMaterialAmt,
                                                        payTotalAmt: data.payTotalAmt,
                                                        payMachineAmt: data.payMachineAmt,
                                                        payTempAmt: data.payTempAmt,
                                                        payFineAmt: data.payFineAmt,
                                                        payRecoupAmt: data.payRecoupAmt,
                                                        payOtherAmt: data.payOtherAmt,
                                                        paySettleItemList: data.paySettleItemList
                                                    })
                                                }
                                            })
                                    }
                                }
                            }
                        },
                        //进度查询按钮禁用
                        disabledFunComponent: (obj) => {
                            let data = obj.btnCallbackFn.getSelectedRows();
                            if (data.length === 1 && data[0].workId) {
                                return false;
                            } else {
                                return true;
                            }
                        },
                        //删除按钮禁用
                        disabledForDelete: (obj) => {
                            let data = obj.qnnTableInstance.getSelectedRows()
                            let disabledLength = 0
                            if (data.length > 0 && (data[0].apih5FlowStatus === '1' || data[0].apih5FlowStatus === '2')) {
                                disabledLength++
                            }
                            if (data.length !== 1 || disabledLength > 0) return true
                        },
                        //详细按钮禁用筛选
                        disabledFunDetail: (obj) => {
                            let data = obj.btnCallbackFn.getSelectedRows();
                            if (data.length === 1 && (data[0].apih5FlowStatus === '1' || data[0].apih5FlowStatus === '2')) {
                                return false;
                            } else {
                                return true;
                            }
                        },
                        //流程按钮禁用
                        disabledForFlow: (obj) => {
                            let data = obj.btnCallbackFn.getSelectedRows();
                            if (data.length !== 1 || data[0].apih5FlowStatus !== '-1') {
                                return true;
                            } else {
                                return false;
                            }
                        },
                        //补录附件禁用
                        disabledForSupplementFile: (obj) => {
                            let data = obj.btnCallbackFn.getSelectedRows();
                            if (data.length === 1 && data[0].apih5FlowStatus === '1') {
                                return false;
                            } else {
                                return true;
                            }
                        },
                        //补录附件回调
                        callBcForSupplementFile: (obj) => {
                            let data = obj.btnCallbackFn.getSelectedRows()[0];
                            if (data.drafterId !== this.props.loginAndLogoutInfo.loginInfo.userInfo.userKey) {
                                Msg.warn('只有项目拟稿人支持补录附件')
                                return
                            }
                            this.setState({
                                showModel: true,
                            })
                        }
                    }}
                    actionBtns={{
                        apiName: "getSysMenuBtn",
                        otherParams: (obj) => {
                            var props = obj.props;
                            let curRouteData = props.routerInfo.routeData[props.routerInfo.curKey];
                            return {
                                menuParentId: curRouteData._value,
                                tableField: "EngineeringSettlement"
                            }
                        }
                    }}
                    formConfig={[
                        {
                            table: {
                                title: '结算单编号',
                                dataIndex: 'billNo',
                                key: 'billNo',
                                filter: true,
                                fieldsConfig: { type: 'string' },
                                fixed: 'left',
                                width: 180,
                                onClick: 'detail',
                            }
                        },
                        {
                            table: {
                                title: '项目名称',
                                dataIndex: 'orgName',
                                key: 'orgName',
                                filter: true,
                                fieldsConfig: { type: 'string' },
                                fixed: 'left',
                                width: 180,
                            }
                        },
                        {
                            table: {
                                title: '结算期次',
                                dataIndex: 'periodTimeWasted',
                                key: 'periodTimeWasted',
                                filter: true,
                                fieldsConfig: {
                                    type: 'month',
                                    field: 'periodTimeWasted'
                                },
                                width: 120,
                                format: 'YYYY-MM'
                            }
                        },
                        {
                            table: {
                                title: '结算类型',
                                dataIndex: 'billType',
                                key: 'billType',
                                width: 120,
                                filter: true,
                                fieldsConfig: {
                                    type: 'select',
                                    optionData: [
                                        { label: '中期', value: '0' },
                                        { label: '最终', value: '1' },
                                        { label: '返还保证金', value: '2' },
                                    ],
                                    optionConfig: {
                                        label: 'label',
                                        value: 'value',
                                    },
                                },
                                render: (h) => {
                                    if (h === '0') {
                                        return '中期'
                                    } else if (h === '1') {
                                        return '最终'
                                    } else if (h === '2') {
                                        return '返还保证金'
                                    }
                                }
                            }
                        },
                        {
                            table: {
                                title: '合同名称',
                                dataIndex: 'contractName',
                                key: 'contractName',
                                filter: true,
                                fieldsConfig: { type: 'string' },
                                width: 180,
                            }
                        },
                        {
                            table: {
                                title: '合同乙方',
                                dataIndex: 'secondName',
                                key: 'secondName',
                                width: 180,
                                filter: true,
                                fieldsConfig: { type: 'string' },
                            }
                        },
                        {
                            table: {
                                title: '本期结算含税金额',
                                dataIndex: 'thisAmt',
                                key: 'thisAmt',
                                width: 150,
                            }
                        },
                        {
                            table: {
                                title: '开累结算含税金额',
                                dataIndex: 'totalAmt',
                                key: 'totalAmt',
                                width: 150,
                            }
                        },
                        {
                            table: {
                                title: '本期应支付含税金额',
                                dataIndex: 'thisPayAmt',
                                key: 'thisPayAmt',
                                width: 150,
                            }
                        },
                        {
                            table: {
                                title: '开累应支付含税金额',
                                dataIndex: 'totalPayAmt',
                                key: 'totalPayAmt',
                                width: 150,
                            }
                        },
                        {
                            table: {
                                title: '重新评审次数',
                                dataIndex: 'notPassNum',
                                key: 'notPassNum',
                                width: 150,
                            }
                        },
                        {
                            table: {
                                title: '结算期限开始时间',
                                dataIndex: 'beginDate',
                                key: 'beginDate',
                                width: 150,
                                format: "YYYY-MM-DD"
                            }
                        },
                        {
                            table: {
                                title: '结算期限结束时间',
                                dataIndex: 'endDate',
                                key: 'endDate',
                                width: 150,
                                format: "YYYY-MM-DD"
                            }
                        },
                        {
                            table: {
                                title: '业务日期',
                                dataIndex: 'businessDate',
                                key: 'businessDate',
                                width: 100,
                                format: "YYYY-MM-DD"
                            }
                        },
                        {
                            table: {
                                title: '发起人',
                                dataIndex: 'flowBeginPerson',
                                key: 'flowBeginPerson',
                                width: 100,
                            }
                        },
                        {
                            table: {
                                title: '评审状态',
                                dataIndex: 'apih5FlowStatus',
                                key: 'apih5FlowStatus',
                                width: 100,
                                fixed: 'right',
                                render: (data) => {
                                    if (data === '0') {
                                        return '待提交';
                                    } else if (data === '1') {
                                        return '审核中';
                                    } else if (data === '2') {
                                        return '审核完成';
                                    } else if (data === '3') {
                                        return '退回';
                                    } else if (data === '-1') {
                                        return '未审核';
                                    }
                                }
                            }
                        },
                    ]}
                    tabs={[
                        {
                            field: "form1",
                            name: "qnnForm",
                            title: "基础信息",
                            content: {
                                formConfig: [
                                    {
                                        field: 'period',
                                        type: 'number',
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
                                        hide: true,
                                    },
                                    {
                                        field: 'companyId',
                                        type: 'string',
                                        hide: true,
                                    },
                                    {
                                        field: 'settleType',
                                        type: 'string',
                                        hide: true,
                                    },
                                    {
                                        field: 'id',
                                        type: 'string',
                                        hide: true,
                                    },
                                    {
                                        field: 'secondID',
                                        type: 'string',
                                        hide: true,
                                    },
                                    {
                                        field: 'companyName',
                                        type: 'string',
                                        hide: true,
                                    },
                                    {
                                        field: 'finishStatus',
                                        type: 'string',
                                        hide: true,
                                    },
                                    {
                                        label: '项目名称',
                                        field: "orgName",
                                        type: 'string',
                                        span: 12,
                                        disabled: true,
                                        addShow: false,
                                        editShow: true,
                                        detailShow: true,
                                    },
                                    {
                                        label: '合同编号',
                                        field: "contractNo",
                                        type: 'string',
                                        span: 12,
                                        disabled: true,
                                        addShow: false,
                                        editShow: true,
                                        detailShow: true,
                                    },
                                    {
                                        label: '项目名称',
                                        field: "orgID",
                                        type: 'select',
                                        placeholder: '请选择',
                                        showSearch: true,
                                        span: 12,
                                        required: true,
                                        addShow: true,
                                        editShow: false,
                                        detailShow: false,
                                        editDisabled: true,
                                        optionConfig: {
                                            label: 'orgName',
                                            value: 'orgID',
                                            linkageFields: {
                                                orgName: 'orgName',
                                                companyId: 'comID',
                                                companyName: 'comName',
                                                finishStatus: 'finishStatus'
                                            }
                                        },
                                        onChange: (val, obj) => {
                                            obj.form.setFieldsValue({
                                                billNo: '',
                                                signedNo: "",
                                                initSerialNumber: '01',
                                                billType: null
                                            })
                                        },
                                        fetchConfig: (obj) => {
                                            if (obj.btnCallbackFn.getActiveKey() === '0') {
                                                return {
                                                    apiName: 'getZxSaProjectUnFinishList',
                                                    otherParams: { orgID: departmentId, settleTypeCode: 'P2' }
                                                }
                                            }
                                            return {}
                                        },
                                    },
                                    {
                                        label: '合同编号',
                                        field: "contractID",
                                        type: (obj) => {
                                            if (obj.clickCb.rowInfo.name === 'add') {
                                                return 'selectByQnnTable'
                                            }
                                            return 'string'
                                        },
                                        showSearch: true,
                                        span: 12,
                                        parent: 'orgID',
                                        required: true,
                                        addShow: true,
                                        editShow: false,
                                        detailShow: false,
                                        placeholder: '请选择',
                                        optionConfig: {
                                            label: 'contractNo',
                                            value: 'contractID',
                                            linkageFields: {
                                                contractName: 'contractName',
                                                secondID: 'secondID',
                                                secondName: 'secondName',
                                                isDeduct: 'isDeduct',
                                                contractNo: 'contractNo',
                                                contractAmt: 'contractCost',
                                                changeAmt: 'alterContractSumTax',
                                                settleType: 'settleType'
                                            }
                                        },
                                        onChange: (val, obj) => {
                                            if (val) {
                                                let pageData = obj.form.getFieldsValue()
                                                const selectItem = obj.itemData
                                                let PeriodDate = ''
                                                let billNo = ''
                                                let signedNo = ''
                                                let statementNo = Number(selectItem.statementNo) < 9 ? '0' + (selectItem.statementNo + 1) : '' + (Number(selectItem.statementNo) + 1);
                                                let isFirst = selectItem.statementNo === 0 ? '1' : '0'
                                                if (pageData.periodTimeWasted) {
                                                    PeriodDate = moment(pageData.periodTimeWasted).format('YY-MM').replace('-', '');
                                                    billNo = `${pageData.contractNo}-${PeriodDate}-${statementNo}`;
                                                    signedNo = `${pageData.contractNo}-SL-${PeriodDate}-${statementNo}`;
                                                }
                                                obj.form.setFieldsValue({
                                                    billNo,
                                                    signedNo,
                                                    initSerialNumber: statementNo,
                                                    isFirst,
                                                    billType: null
                                                })
                                            } else {
                                                obj.form.setFieldsValue({
                                                    billNo: '',
                                                    signedNo: "",
                                                    initSerialNumber: '01',
                                                    billType: null
                                                })
                                            }
                                        },
                                        dropdownMatchSelectWidth: 800,
                                        qnnTableConfig: {
                                            antd: {
                                                rowKey: "contractID"
                                            },
                                            firstRowIsSearch: false,
                                            fetchConfig: {
                                                apiName: 'getZxSaProjectSettleAuditContractNoList',
                                                otherParams: () => {
                                                    return {
                                                        orgID: this.table.form.getFieldValue('orgID')
                                                    }
                                                }
                                            },
                                            searchBtnsStyle: "inline",
                                            formConfig: [
                                                {
                                                    isInForm: false,
                                                    table: {
                                                        dataIndex: "contractNo",
                                                        title: "合同编号",
                                                    }
                                                },
                                                {
                                                    isInForm: false,
                                                    table: {
                                                        dataIndex: "contractName",
                                                        title: "合同名称",
                                                    }
                                                },
                                            ]
                                        },
                                    },
                                    {
                                        label: '结算单编号',
                                        field: "billNo",
                                        type: 'string',
                                        span: 12,
                                        disabled: true,
                                        required: true,
                                    },
                                    {
                                        label: '合同名称',
                                        field: "contractName",
                                        type: 'string',
                                        span: 12,
                                        disabled: true,
                                    },
                                    {
                                        label: '签认单编号',
                                        field: "signedNo",
                                        type: 'string',
                                        span: 12,
                                        disabled: true,
                                        required: true,
                                    },
                                    {
                                        label: '合同乙方',
                                        field: "secondName",
                                        disabled: true,
                                        span: 12,
                                        type: 'string',
                                    },
                                    {
                                        label: '结算期次',
                                        field: "periodTimeWasted",
                                        type: 'month',
                                        placeholder: '请选择',
                                        required: true,
                                        span: 12,
                                        editDisabled: true,
                                        onChange: (val, obj) => {
                                            const pageData = obj.form.getFieldsValue();
                                            const PeriodDate = moment(val).format('YY-MM').replace('-', '');
                                            if (pageData.contractID) {
                                                let statementNo = pageData.initSerialNumber.toString().split('').length === 1 ? '0' + (pageData.initSerialNumber) : pageData.initSerialNumber;
                                                const billNo = `${pageData.contractNo}-${PeriodDate}-${statementNo}`;
                                                const signedNo = `${pageData.contractNo}-SL-${PeriodDate}-${statementNo}`;
                                                obj.form.setFieldsValue({
                                                    billNo,
                                                    signedNo,
                                                    period: moment(val).valueOf()
                                                })
                                            }
                                        }
                                    },
                                    {
                                        label: '结算类型',
                                        field: "billType",
                                        type: 'select',
                                        required: true,
                                        placeholder: '请选择',
                                        span: 12,
                                        optionConfig: {
                                            label: 'label',
                                            value: 'value',
                                        },
                                        editDisabled: true,
                                        dependencies: ['isFished', 'settleType', 'finishStatus'],
                                        dependenciesReRender: true,
                                        optionData: (obj) => {
                                            let objData = obj?.form?.getFieldsValue()
                                            if (objData.isFished === '1' || objData.settleType === '已最终结算' || objData.finishStatus === '1' || objData.billType === '2') {
                                                return [
                                                    { label: '返还保证金', value: '2' }
                                                ]
                                            } else {
                                                return [
                                                    { label: '中期', value: '0' },
                                                    { label: '最终', value: '1' },
                                                ]
                                            }
                                        },
                                    },
                                    {
                                        label: '是否抵扣',
                                        field: "isDeduct",
                                        type: 'radio',
                                        disabled: true,
                                        optionData: [
                                            { label: '是', value: '1' },
                                            { label: '否', value: '0' }
                                        ],
                                        initialValue: '0',
                                        span: 8,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 12 },
                                                sm: { span: 12 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 8 },
                                                sm: { span: 8 }
                                            }
                                        }
                                    },
                                    {
                                        label: '是否完工后结算',
                                        field: "isFished",
                                        type: 'radio',
                                        optionData: [
                                            { label: '是', value: '1' },
                                            { label: '否', value: '0' }
                                        ],
                                        initialValue: '0',
                                        span: 8,
                                        editDisabled: true,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 12 },
                                                sm: { span: 12 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 8 },
                                                sm: { span: 8 }
                                            }
                                        },
                                        onChange: (h, obj) => {
                                            obj.form.setFieldsValue({
                                                billType: null
                                            })
                                        }
                                    },
                                    {
                                        label: '是否首次结算',
                                        field: "isFirst",
                                        type: 'radio',
                                        optionData: [
                                            { label: '是', value: '1' },
                                            { label: '否', value: '0' }
                                        ],
                                        initialValue: '0',
                                        disabled: true,
                                        span: 8,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 12 },
                                                sm: { span: 12 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 8 },
                                                sm: { span: 8 }
                                            }
                                        }
                                    },
                                    {
                                        label: '结算单初始化顺序号',
                                        field: "initSerialNumber",
                                        required: true,
                                        editDisabled: true,
                                        condition: [
                                            {
                                                regex: { isFirst: '0' },
                                                action: 'disabled',
                                            },
                                        ],
                                        span: 12,
                                        placeholder: '请输入',
                                        type: 'string',
                                        onBlur: (obj) => {
                                            const pageData = this.table.qnnForm.form.getFieldsValue();
                                            let val = obj.target.defaultValue;
                                            if (isNaN(val) || !(/(^[0-9]\d*$)/.test(val)) || val.split('').length > 2 || Number(val) < 1) {
                                                Msg.warn('请输入正确的结算单初始化顺序号!')
                                                this.table.qnnForm.form.setFieldsValue({
                                                    initSerialNumber: '',
                                                    billNo: '',
                                                    signedNo: '',
                                                })
                                                return
                                            }
                                            let initSerialNumberL = val.toString().split('').length
                                            val = initSerialNumberL === 1 && val !== '0' ? '0' + val : val
                                            this.table.qnnForm.form.setFieldsValue({
                                                initSerialNumber: val
                                            })
                                            if (pageData.contractID && pageData.periodTimeWasted) {
                                                const PeriodDate = moment(pageData.periodTimeWasted).format('YY-MM').replace('-', '');
                                                const billNo = `${pageData.contractNo}-${PeriodDate}-${val}`;
                                                const signedNo = `${pageData.contractNo}-SL-${PeriodDate}-${val}`;
                                                this.table.qnnForm.form.setFieldsValue({
                                                    billNo,
                                                    signedNo,
                                                })
                                            }
                                        }
                                    },
                                    {
                                        label: '复核人',
                                        field: "reCountPerson",
                                        type: 'string',
                                        span: 12,
                                        placeholder: '请输入',
                                    },
                                    {
                                        label: '填报人',
                                        field: "reportPerson",
                                        type: 'string',
                                        span: 12,
                                        placeholder: '请输入',
                                        initialValue: () => {
                                            let reportPerson = this.props.loginAndLogoutInfo.loginInfo.userInfo.realName
                                            return reportPerson
                                        }
                                    },
                                    {
                                        label: '填报人电话',
                                        field: "reportPersonTel",
                                        type: 'string',
                                        span: 12,
                                        placeholder: '请输入',
                                    },
                                    {
                                        label: '填报日期',
                                        field: "reportDate",
                                        span: 12,
                                        type: 'date',
                                        required: true,
                                    },
                                    {
                                        label: '业务日期',
                                        field: "businessDate",
                                        span: 12,
                                        type: 'date',
                                    },
                                    {
                                        label: '结算期限开始时间',
                                        field: "beginDate",
                                        span: 12,
                                        type: 'date',
                                        required: true,
                                        onChange: (val, obj) => {
                                            let formData = obj.form.getFieldsValue()
                                            if (val) {
                                                if (formData.endDate) {
                                                    let beginDate = Number(moment(val).format("YYYYMMDD"))
                                                    let endDate = Number(moment(formData.endDate).format("YYYYMMDD"))
                                                    if (beginDate > endDate) {
                                                        Msg.warn('开始时间不能早于结束时间')
                                                        obj.form.setFieldsValue({
                                                            beginDate: null
                                                        })
                                                    }
                                                }
                                            }
                                        }
                                    },
                                    {
                                        label: '结算期限结束时间',
                                        field: "endDate",
                                        span: 12,
                                        type: 'date',
                                        required: true,
                                        onChange: (val, obj) => {
                                            let formData = obj.form.getFieldsValue()
                                            if (val) {
                                                if (formData.beginDate) {
                                                    let beginDate = Number(moment(formData.beginDate).format("YYYYMMDD"))
                                                    let endDate = Number(moment(val).format("YYYYMMDD"))
                                                    console.log(beginDate, endDate);
                                                    if (beginDate > endDate) {
                                                        Msg.warn('开始时间不能早于结束时间')
                                                        obj.form.setFieldsValue({
                                                            endDate: null
                                                        })
                                                    }
                                                }
                                            }
                                        }
                                    },
                                    {
                                        label: '施工内容',
                                        field: 'content',
                                        type: 'textarea',
                                        placeholder: '请输入',
                                        span: 12,
                                    },
                                    {
                                        label: '工程质量评价',
                                        field: 'appraisal',
                                        type: 'textarea',
                                        placeholder: '请输入',
                                        span: 12,
                                    },
                                    {
                                        label: '备注信息',
                                        field: 'remark',
                                        type: 'textarea',
                                        span: 12,
                                        placeholder: '请输入',
                                    },
                                ]
                            }
                        },
                        {
                            field: "form2",
                            name: "qnnForm",
                            title: "清单结算",
                            disabled: function (obj) {
                                return ((obj.clickCb.rowInfo.name === "edit" || obj.clickCb.rowInfo.name === "detail") ? false : !obj.btnCallbackFn?.form?.getFieldValue("id"))
                            },
                            content: {
                                addIsGetData: true,
                                fetchConfig: {
                                    apiName: 'getZxSaProjectSettleAuditDetail',
                                    otherParams: () => {
                                        return {
                                            id: this.table.form.getFieldValue('id'),
                                            workPayFlag: '1'
                                        }
                                    }
                                },
                                formConfig: [
                                    {
                                        field: "projectWorkSettleId",
                                        type: 'string',
                                        hide: true,
                                    },
                                    {
                                        label: '含税合同金额(万元)',
                                        field: "workContractAmt",
                                        type: 'number',
                                        disabled: true,
                                        initialValue: 0,
                                        span: 12
                                    },
                                    {
                                        label: '变更后含税合同金额(万元)',
                                        field: "workChangeAmt",
                                        type: 'number',
                                        disabled: true,
                                        initialValue: 0,
                                        span: 12
                                    },
                                    {
                                        label: '本期清单结算含税金额(元)',
                                        field: "workThisAmt",
                                        type: 'number',
                                        disabled: true,
                                        initialValue: 0,
                                        span: 12
                                    },
                                    {
                                        label: '本期清单结算不含税金额(元)',
                                        field: "workThisAmtNoTax",
                                        type: 'number',
                                        disabled: true,
                                        initialValue: 0,
                                        span: 12
                                    },
                                    {
                                        label: '本期清单结算税额(元)',
                                        field: "workThisAmtTax",
                                        type: 'number',
                                        disabled: true,
                                        initialValue: 0,
                                        span: 12
                                    },
                                    {
                                        label: '累计清单结算含税金额(元)',
                                        field: "workTotalAmt",
                                        type: 'number',
                                        disabled: true,
                                        initialValue: 0,
                                        span: 12
                                    },
                                    {
                                        type: 'qnnTable',
                                        incToForm: true,
                                        field: 'workSettleItemList',
                                        qnnTableConfig: {
                                            rowDisabledCondition: () => {
                                                return this.table?.form.getFieldsValue().billType === '2'
                                            },
                                            antd: {
                                                rowKey: 'projectWorkSettleItemId',
                                                size: 'small'
                                            },
                                            paginationConfig: {
                                                position: 'bottom'
                                            },
                                            isShowRowSelect: false,
                                            formConfig: [
                                                {
                                                    isInTable: false,
                                                    form: {
                                                        label: '主键id',
                                                        field: 'projectWorkSettleItemId',
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '清单细目',
                                                        dataIndex: 'workNo',
                                                        key: 'workNo',
                                                        tdEdit: false,
                                                        align: 'center',
                                                        width: 150,
                                                        fixed: 'left'
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '清单名称',
                                                        dataIndex: 'workName',
                                                        key: 'workName',
                                                        tdEdit: false,
                                                        align: 'center',
                                                        width: 200,
                                                        fixed: 'left'
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '计量单位',
                                                        dataIndex: 'unit',
                                                        key: 'unit',
                                                        tdEdit: false,
                                                        align: 'center',
                                                        width: 140,
                                                        fixed: 'left'
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '含税单价(元)',
                                                        dataIndex: 'price',
                                                        key: 'price',
                                                        tdEdit: false,
                                                        align: 'center',
                                                        width: 150
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '合同数量',
                                                        dataIndex: 'contractQty',
                                                        key: 'contractQty',
                                                        tdEdit: false,
                                                        align: 'center',
                                                        width: 120
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '合同含税金额(元)',
                                                        dataIndex: 'contractAmt',
                                                        key: 'contractAmt',
                                                        tdEdit: false,
                                                        align: 'center',
                                                        width: 150
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '变更后含税单价(元)',
                                                        dataIndex: 'changePrice',
                                                        key: 'changePrice',
                                                        tdEdit: false,
                                                        align: 'center',
                                                        width: 160
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '变更后合同数量',
                                                        dataIndex: 'changeQty',
                                                        key: 'changeQty',
                                                        tdEdit: false,
                                                        align: 'center',
                                                        width: 160
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '变更后合同含税金额',
                                                        dataIndex: 'changeAmt',
                                                        key: 'changeAmt',
                                                        tdEdit: false,
                                                        align: 'center',
                                                        width: 160,
                                                    },
                                                },
                                                {
                                                    table: {
                                                        title: '税率(%)',
                                                        dataIndex: 'taxRate',
                                                        key: 'taxRate',
                                                        initialValue: '0',
                                                        tdEdit: false,
                                                        width: 120,
                                                        align: 'center',
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '本期结算数量',
                                                        dataIndex: 'thisQty',
                                                        key: 'thisQty',
                                                        tdEdit: true,
                                                        width: 170,
                                                        align: 'center',
                                                    },
                                                    form: {
                                                        precision: 3,
                                                        type: 'number',
                                                        required: true,
                                                        placeholder: '请输入',
                                                        onBlur: async (val, obj) => {
                                                            let rowData = await obj.qnnTableInstance.getEditedRowData()
                                                            let value = rowData.thisQty
                                                            if (rowData.changeQty && value > rowData.changeQty) {
                                                                Msg.warn('本期结算数量不能大于变更后合同数量')
                                                                rowData.thisQty = null
                                                                await obj.qnnTableInstance.setEditedRowData(rowData)
                                                                return
                                                            }
                                                            if (!rowData.changeQty && rowData.contractQty && value > rowData.contractQty) {
                                                                Msg.warn('本期结算数量不能大于合同数量')
                                                                rowData.thisQty = null
                                                                await obj.qnnTableInstance.setEditedRowData(rowData)
                                                                return
                                                            }
                                                            rowData.allQty = value + (rowData.upAllQty ? rowData.upAllQty : 0)
                                                            if (rowData.changePrice) {
                                                                rowData.thisTotalAmt = value * rowData.changePrice
                                                                rowData.allTotalAmt = value * rowData.changePrice + (rowData.upAllTotalAmt ? rowData.upAllTotalAmt : 0)
                                                            } else {
                                                                rowData.thisTotalAmt = value * rowData.price ? rowData.price : 0
                                                                rowData.allTotalAmt = (value * rowData.price ? rowData.price : 0) + (rowData.upAllTotalAmt ? rowData.upAllTotalAmt : 0)
                                                            }
                                                            await obj.qnnTableInstance.setEditedRowData(rowData)
                                                        }
                                                    },
                                                },
                                                {
                                                    table: {
                                                        title: '本期结算含税金额',
                                                        dataIndex: 'thisTotalAmt',
                                                        key: 'thisTotalAmt',
                                                        tdEdit: false,
                                                        width: 160,
                                                        align: 'center',
                                                        render: (h) => {
                                                            if (h) return h.toFixed(2)
                                                        }
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '上期末累计结算数量',
                                                        dataIndex: 'upAllQty',
                                                        key: 'upAllQty',
                                                        tdEdit: false,
                                                        width: 160,
                                                        align: 'center',
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '上期末累计结算含税金额',
                                                        dataIndex: 'upAllTotalAmt',
                                                        key: 'upAllTotalAmt',
                                                        tdEdit: false,
                                                        width: 170,
                                                        align: 'center',
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '累计结算合同数量',
                                                        dataIndex: 'allQty',
                                                        key: 'allQty',
                                                        tdEdit: false,
                                                        width: 170,
                                                        align: 'center',
                                                        render: (h) => {
                                                            if (h) return h.toFixed(2)
                                                        }
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '累计结算含税金额(元)',
                                                        dataIndex: 'allTotalAmt',
                                                        key: 'allTotalAmt',
                                                        tdEdit: false,
                                                        width: 170,
                                                        align: 'center',
                                                        render: (h) => {
                                                            if (h) return h.toFixed(2)
                                                        }
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '计算式',
                                                        dataIndex: 'planning',
                                                        key: 'planning',
                                                        tdEdit: true,
                                                        width: 150,
                                                        align: 'center',
                                                        initialValue: 0,
                                                    },
                                                    form: {
                                                        type: 'string',
                                                        placeholder: '请输入',
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '图号',
                                                        dataIndex: 'mapNum',
                                                        key: 'mapNum',
                                                        tdEdit: true,
                                                        width: 150,
                                                        align: 'center',
                                                    },
                                                    form: {
                                                        type: 'string',
                                                        placeholder: '请输入',
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '备注',
                                                        dataIndex: 'remark',
                                                        key: 'remark',
                                                        tdEdit: true,
                                                        width: 200,
                                                        align: 'center',
                                                    },
                                                    form: {
                                                        type: 'string',
                                                        placeholder: '请输入',
                                                    }
                                                },
                                            ],
                                        }
                                    }
                                ]
                            }
                        },
                        {
                            field: "form3",
                            name: "qnnForm",
                            disabled: function (obj) {
                                return ((obj.clickCb.rowInfo.name === "edit" || obj.clickCb.rowInfo.name === "detail") ? false : !obj?.btnCallbackFn?.form?.getFieldValue("id"))
                            },
                            title: "支付项",
                            content: {
                                fetchConfig: () => {
                                    if (this.table.getTabsIndex() === '2') {
                                        return {
                                            apiName: 'getZxSaProjectSettleAuditDetail',
                                            otherParams: {
                                                id: this.table.form.getFieldValue('id'),
                                                workPayFlag: '2'
                                            }
                                        }
                                    }
                                },
                                formConfig: [
                                    {
                                        field: "projectPaySettleId",
                                        type: 'string',
                                        hide: true,
                                    },
                                    {
                                        label: '本期支付项结算含税金额(元)',
                                        field: "payThisAmt",
                                        type: 'number',
                                        disabled: true,
                                        initialValue: 0,
                                        span: 12
                                    },
                                    {
                                        label: '本期支付项结算不含税金额(元)',
                                        field: "payThisAmtNoTax",
                                        type: 'number',
                                        disabled: true,
                                        initialValue: 0,
                                        span: 12,
                                    },
                                    {
                                        label: '累计支付项结算金额(元)',
                                        field: "payTotalAmt",
                                        type: 'number',
                                        disabled: true,
                                        initialValue: 0,
                                        span: 12
                                    },
                                    {
                                        label: '物资调拨费用本期结算小计(元)',
                                        field: "payMaterialAmt",
                                        type: 'number',
                                        disabled: true,
                                        initialValue: 0,
                                        span: 12,
                                    },
                                    {
                                        label: '机械调拨费用本期结算小计(元)',
                                        field: "payMachineAmt",
                                        type: 'number',
                                        disabled: true,
                                        initialValue: 0,
                                        span: 12
                                    },
                                    {
                                        label: '临时用工费本期结算小计(元)',
                                        field: "payTempAmt",
                                        type: 'number',
                                        disabled: true,
                                        initialValue: 0,
                                        span: 12,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 9 },
                                                sm: { span: 9 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 15 },
                                                sm: { span: 15 }
                                            }
                                        }
                                    },
                                    {
                                        label: '奖罚金额本期结算小计(元)',
                                        field: "payFineAmt",
                                        type: 'number',
                                        disabled: true,
                                        initialValue: 0,
                                        span: 12
                                    },
                                    {
                                        label: '补偿金额本期结算小计(元)',
                                        field: "payRecoupAmt",
                                        type: 'number',
                                        disabled: true,
                                        initialValue: 0,
                                        span: 12,
                                    },
                                    {
                                        label: '其他款项本期结算小计(元)',
                                        field: "payOtherAmt",
                                        type: 'number',
                                        disabled: true,
                                        initialValue: 0,
                                        span: 12,
                                    },
                                    {
                                        type: 'qnnTable',
                                        field: 'paySettleItemList',
                                        incToForm: true,
                                        qnnTableConfig: {
                                            rowDisabledCondition: () => {
                                                return this.table.form.getFieldsValue().billType === '2'
                                            },
                                            antd: {
                                                rowKey: 'projectPaySettleItemId',
                                                size: 'small'
                                            },
                                            paginationConfig: {
                                                position: 'bottom'
                                            },
                                            wrappedComponentRef: (me) => {
                                                this.tableTwo = me;
                                            },
                                            actionBtns: [
                                                {
                                                    name: 'addRow',
                                                    icon: 'plus',
                                                    type: 'primary',
                                                    label: '新增行',
                                                    addRowDefaultData: { addFlag: '1' },
                                                    hide: () => {
                                                        return this.table?.form?.getFieldsValue?.()?.billType === '2'
                                                    }
                                                },
                                                {
                                                    name: 'delRow',
                                                    icon: 'delete',
                                                    type: 'danger',
                                                    label: '删除',
                                                    isRefreshTable: true,
                                                    hide: () => {
                                                        return this.table?.form?.getFieldsValue?.()?.billType === '2'
                                                    }
                                                }
                                            ],
                                            formConfig: [
                                                {
                                                    isInTable: false,
                                                    form: {
                                                        label: '主键id',
                                                        field: 'projectPaySettleItemId',
                                                    }
                                                },
                                                {
                                                    isInTable: false,
                                                    form: {
                                                        field: 'payID',
                                                        type: 'string',
                                                        hide: true
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '支付项类型',
                                                        dataIndex: 'payType',
                                                        key: 'payType',
                                                        tdEdit: false,
                                                        fixed: 'left',
                                                        width: 120
                                                    },
                                                },
                                                {
                                                    table: {
                                                        title: '名称',
                                                        dataIndex: 'payName',
                                                        key: 'payName',
                                                        tdEdit: true,
                                                        fixed: 'left',
                                                        width: 150,
                                                        type: 'select',
                                                    },
                                                    form: {
                                                        required: true,
                                                        allowClear: false,//是否显示清除按钮
                                                        type: 'select',
                                                        showSearch: true,
                                                        placeholder: '请选择',
                                                        optionConfig: {
                                                            label: 'workName',
                                                            value: 'workName',
                                                        },
                                                        fetchConfig: {
                                                            apiName: 'getZxSaProjectSetItemList',
                                                            otherParams: () => {
                                                                return {
                                                                    orgID: this.table?.form?.getFieldValue('orgID'),
                                                                    contrType: 'enginner'
                                                                }
                                                            }
                                                        },
                                                        onChange: async (val, obj) => {
                                                            let tableOneData = obj.rowData
                                                            let allData = await this.tableTwo.getTableData()
                                                            for (var i = 0; i < allData.length; i++) {
                                                                if (obj.rowIndex === i) continue
                                                                if (allData[i].payID === obj.itemData.id) {
                                                                    tableOneData.payName = null
                                                                    tableOneData.payID = null
                                                                    tableOneData.payType = null
                                                                    tableOneData.unit = null
                                                                    await obj.qnnTableInstance.setEditedRowData(tableOneData)
                                                                    Msg.warn('已存在相同名称，请重试!')
                                                                    obj.qnnTableInstance.refresh()
                                                                    return
                                                                }
                                                            }
                                                            tableOneData.payID = obj.itemData.id
                                                            tableOneData.payName = obj.itemData.workName
                                                            tableOneData.payType = obj.itemData.workType
                                                            tableOneData.unit = obj.itemData.unit
                                                            tableOneData.qty = 0
                                                            tableOneData.price = 0
                                                            tableOneData.taxRate = 0
                                                            tableOneData.thisAmt = 0
                                                            tableOneData.thisAmtNoTax = 0
                                                            tableOneData.thisAmtTax = 0
                                                            obj.qnnTableInstance.setEditedRowData(tableOneData)
                                                        },
                                                    },
                                                },
                                                {
                                                    table: {
                                                        title: '单位',
                                                        dataIndex: 'unit',
                                                        key: 'unit',
                                                        tdEdit: false
                                                    },
                                                },
                                                {
                                                    table: {
                                                        title: '数量',
                                                        dataIndex: 'qty',
                                                        key: 'qty',
                                                        tdEdit: true
                                                    },
                                                    form: {
                                                        required: true,
                                                        precision: 3,
                                                        field: 'qty',
                                                        type: 'number',
                                                        placeholder: '请输入',
                                                        onChange: async (val, obj) => {
                                                            let rowData = await obj.qnnTableInstance.getEditedRowData()
                                                            rowData.qty = val
                                                            if (val < 0) {
                                                                Msg.warn('数量必须大于0')
                                                                return
                                                            }
                                                            rowData.thisAmt = (rowData.price ? rowData.price : 0) * Number(val)  //本期结算金额
                                                            if (rowData.taxRate && rowData.taxRate !== '0') {
                                                                rowData.thisAmtNoTax = ((rowData.price ? rowData.price : 0) * Number(val)) / (1 + Number(rowData.taxRate) / 100)  //本期结算不含税金额
                                                                rowData.thisAmtTax = (rowData.price ? rowData.price : 0) * Number(val) - ((rowData.price ? rowData.price : 0) * Number(val)) / (1 + Number(rowData.taxRate) / 100)//本期结算税额
                                                            }
                                                            await obj.qnnTableInstance.setEditedRowData(rowData)
                                                        }
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '单价(元)',
                                                        dataIndex: 'price',
                                                        key: 'price',
                                                        tdEdit: true,
                                                        width: 120
                                                    },
                                                    form: {
                                                        required: true,
                                                        field: 'price',
                                                        type: 'number',
                                                        placeholder: '请输入',
                                                        precision: 6,
                                                        onChange: async (val, obj) => {
                                                            let rowData = await obj.qnnTableInstance.getEditedRowData()
                                                            rowData.price = val
                                                            rowData.thisAmt = (rowData.qty ? rowData.qty : 0) * val  //本期结算含税金额
                                                            if (rowData.taxRate && rowData.taxRate !== '0') {
                                                                rowData.thisAmtNoTax = ((rowData.qty ? rowData.qty : 0) * val) / (1 + Number(rowData.taxRate) / 100) //本期结算不含税金额
                                                                rowData.thisAmtTax = (rowData.qty ? rowData.qty : 0) * val - ((rowData.qty ? rowData.qty : 0) * val) / (1 + Number(rowData.taxRate) / 100)//本期结算税额
                                                            }
                                                            await obj.qnnTableInstance.setEditedRowData(rowData)
                                                        }
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '税率(%)',
                                                        dataIndex: 'taxRate',
                                                        key: 'taxRate',
                                                        tdEdit: true,
                                                        width: 120
                                                    },
                                                    form: {
                                                        required: true,
                                                        field: 'taxRate',
                                                        type: 'select',
                                                        allowClear: false,
                                                        optionConfig: {
                                                            label: 'itemName',
                                                            value: 'itemId',
                                                        },
                                                        fetchConfig: {
                                                            apiName: 'getBaseCodeSelect',
                                                            otherParams: { itemId: 'shuiLv' }
                                                        },
                                                        onChange: async (val, obj) => {
                                                            let rowData = await obj.qnnTableInstance.getEditedRowData()
                                                            rowData.taxRate = val
                                                            rowData.thisAmtNoTax = ((rowData.qty ? rowData.qty : 0) * (rowData.price ? rowData.price : 0)) / (1 + Number(val) / 100)  //本期结算不含税金额
                                                            rowData.thisAmtTax = (rowData.qty ? rowData.qty : 0) * (rowData.price ? rowData.price : 0) - ((rowData.qty ? rowData.qty : 0) * (rowData.price ? rowData.price : 0)) / (1 + Number(val) / 100) //本期结算税额
                                                            await obj.qnnTableInstance.setEditedRowData(rowData)
                                                        },
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '含税金额(元)',
                                                        dataIndex: 'thisAmt',
                                                        key: 'thisAmt',
                                                        tdEdit: false,
                                                        width: 170,
                                                        render: (val, rowData) => {
                                                            return rowData.thisAmt ? rowData.thisAmt.toFixed(2) : 0
                                                        }
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '不含税金额(元)',
                                                        dataIndex: 'thisAmtNoTax',
                                                        key: 'thisAmtNoTax',
                                                        tdEdit: false,
                                                        width: 180,
                                                        render: (val, rowData) => {
                                                            return rowData.thisAmtNoTax ? rowData.thisAmtNoTax.toFixed(2) : 0
                                                        }
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '税额(元)',
                                                        dataIndex: 'thisAmtTax',
                                                        key: 'thisAmtTax',
                                                        tdEdit: false,
                                                        width: 140,
                                                        render: (val, rowData) => {
                                                            return rowData.thisAmtTax ? rowData.thisAmtTax.toFixed(2) : 0
                                                        }
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '备注',
                                                        dataIndex: 'remark',
                                                        key: 'remark',
                                                        tdEdit: true,
                                                        width: 120,
                                                    },
                                                    form: {
                                                        field: 'remark',
                                                        type: 'string',
                                                        placeholder: '请输入',
                                                    }
                                                }
                                            ],
                                        }
                                    }
                                ]
                            }
                        },
                        {
                            field: "table1",
                            name: "qnnTable",
                            title: "统计项",
                            disabled: function (obj) {
                                return ((obj.clickCb.rowInfo.name === "edit" || obj.clickCb.rowInfo.name === "detail") ? false : !obj.btnCallbackFn.form.getFieldValue("id"))
                            },
                            content: {
                                antd: {
                                    size: "small",
                                    rowKey: "projectSettleAuditItemId",
                                },
                                tableTdEdit: true,
                                rowDisabledCondition: (rowData) => {
                                    return rowData.sort !== 2
                                },
                                wrappedComponentRef: (me) => {
                                    this.tableFour = me;
                                },
                                paginationConfig: false,
                                pageSize: 999999,
                                isShowRowSelect: false,
                                fetchConfig: {
                                    apiName: 'getZxSaProjectSettleAuditItemList',
                                    otherParams: (obj) => {
                                        return {
                                            projectSettleAuditId: obj.tableFns.form.getFieldValue('id')
                                        }
                                    }
                                },
                                tableTdEditFetchConfig: async (obj) => {
                                    let body = await obj.qnnTableInstance.getEditedRowData()
                                    if (!body.thisAmt || isNaN(body.thisAmt)) {
                                        Msg.warn('请输入正确的金额')
                                        return {}
                                    }
                                    return {
                                        apiName: 'updateZxSaProjectSettleAuditItem',
                                        otherParams: { ...body },
                                        success: ({ success, message }) => {
                                            if (success) {
                                                this.tableFour.refresh()
                                                Msg.success(message)
                                            } else {
                                                Msg.error(message)
                                            }
                                        }
                                    }
                                },
                                formConfig: [
                                    {
                                        isInTable: false,
                                        form: {
                                            field: 'projectSettleAuditItemId',
                                            type: 'string',
                                            hide: true,
                                        }
                                    },

                                    {
                                        table: {
                                            title: '统计项',
                                            dataIndex: 'statisticsName',
                                            key: 'statisticsName',
                                            width: 100,
                                            align: 'center',
                                        },
                                    },
                                    {
                                        table: {
                                            title: '本期（元）',
                                            dataIndex: 'thisAmt',
                                            key: 'thisAmt',
                                            width: 150,
                                            tdEdit: true,
                                            align: 'center',
                                            render: (val) => {
                                                if (val && !isNaN(val)) {
                                                    return Number(val).toFixed(2)
                                                } else {
                                                    return val
                                                }
                                            },
                                        },
                                        form: {
                                            field: 'thisAmt',
                                            type: 'string',
                                            placeholder: '请输入',
                                            precision: 2
                                        }
                                    },
                                    {
                                        table: {
                                            title: '开累（元）',
                                            dataIndex: 'totalAmt',
                                            key: 'totalAmt',
                                            width: 100,
                                            align: 'center',
                                            render: (val) => {
                                                if (val && !isNaN(val)) {
                                                    return Number(val).toFixed(2)
                                                } else {
                                                    return val
                                                }
                                            },
                                        },
                                    },
                                ]
                            }
                        }
                    ]}
                />
                <Modal
                    width='80%'
                    style={{ top: '0' }}
                    title=" 补传附件"
                    visible={showModel}
                    footer={null}
                    onCancel={this.handleCancelBjdh}
                    bodyStyle={{ width: '90%', height: '70%' }}
                    centered={true}
                    destroyOnClose={this.handleCancelBjdh}
                    wrapClassName={'modals'}
                >
                    <QnnForm
                        fetch={this.props.myFetch}
                        wrappedComponentRef={(me) => {
                            this.FormOne = me;
                        }}
                        upload={this.props.myUpload}
                        fetchConfig={{
                            apiName: 'getZxSaProjectSettleAuditDetail',
                            otherParams: {
                                id: this.table?.getSelectedRows()[0]?.id,
                            }
                        }}
                        formItemLayout={{
                            labelCol: {
                                xs: { span: 9 },
                                sm: { span: 9 }
                            },
                            wrapperCol: {
                                xs: { span: 15 },
                                sm: { span: 15 }
                            }
                        }}
                        formConfig={[
                            {
                                type: 'component',
                                field: 'diyButtonsssss',
                                span: 24,
                                Component: obj => {
                                    return (
                                        <Button
                                            type={'primary'}
                                            onClick={() => {
                                                this.props.myFetch('zxSaProjectSettleAuditSubmitFile', {
                                                    zxErpFileList: this.FormOne.form?.getFieldValue('zxErpFileList'),
                                                    id: this.table?.getSelectedRows()[0]?.id,
                                                }).then(({ success, message }) => {
                                                    if (success) {
                                                        Msg.success(message)
                                                        this.setState({
                                                            showModel: false,
                                                        })
                                                    } else {
                                                        Msg.error(message)
                                                    }
                                                })
                                            }}
                                        >
                                            保存
                                        </Button>
                                    )
                                }
                            },
                            {
                                field: 'billNo',
                                type: 'string',
                                label: '结算单编号',
                                span: 12,
                                disabled: true,
                            },
                            {
                                field: 'orgName',
                                type: 'string',
                                label: '项目名称',
                                span: 12,
                                disabled: true,
                            },
                            {
                                field: 'periodTimeWasted',
                                type: 'month',
                                label: '结算期次',
                                span: 12,
                                disabled: true,
                            },
                            {
                                field: 'contractNo',
                                type: 'string',
                                disabled: true,
                                label: '合同编号',
                                span: 12,
                            },
                            {
                                label: '合同名称',
                                field: "contractName",
                                type: 'string',
                                span: 12,
                                disabled: true,
                            },
                            {
                                field: 'secondName',
                                type: 'string',
                                disabled: true,
                                label: '合同乙方',
                                span: 12,
                            },
                            {
                                label: '结算类型',
                                field: 'billType',
                                type: 'select',
                                span: 12,
                                disabled: true,
                                optionConfig: {
                                    label: 'label',
                                    value: 'value',
                                },
                                optionData: [
                                    { label: '中期', value: '0' },
                                    { label: '最终', value: '1' },
                                    { label: '返还保证金', value: '2' }
                                ],
                            },
                            {
                                label: '是否抵扣',
                                field: 'isDeduct',
                                type: 'radio',
                                optionData: [
                                    { label: '是', value: '1' },
                                    { label: '否', value: '0' }
                                ],
                                disabled: true,
                                span: 12,
                            },
                            {
                                label: '结算期限开始时间',
                                field: "beginDate",
                                span: 12,
                                type: 'date',
                                disabled: true,
                            },
                            {
                                label: '结算期限结束时间',
                                field: "endDate",
                                span: 12,
                                type: 'date',
                                disabled: true,
                            },
                            {
                                field: 'content',
                                type: 'string',
                                disabled: true,
                                label: '结算内容及说明',
                                span: 12,
                            },
                            {
                                label: '业务日期',
                                field: "businessDate",
                                span: 12,
                                type: 'date',
                                disabled: true,
                            },
                            {
                                label: '填报人',
                                field: "reportPerson",
                                type: 'string',
                                span: 12,
                                disabled: true,
                            },
                            {
                                type: 'files',
                                label: '附件',
                                field: 'zxErpFileList',
                                fetchConfig: {
                                    apiName: 'upload'
                                },
                                span: 12,
                                formItemLayout: {
                                    labelCol: {
                                        xs: { span: 4 },
                                        sm: { span: 4 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 16 },
                                        sm: { span: 16 }
                                    }
                                },
                            },
                            {
                                type: 'qnnTable',
                                field: 'table1',
                                label: '统计项',
                                span: 24,
                                formItemLayout: {
                                    labelCol: {
                                        xs: { span: 2 },
                                        sm: { span: 2 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 22 },
                                        sm: { span: 22 }
                                    }
                                },
                                qnnTableConfig: {
                                    rowKey: 'table1',
                                    fetchConfig: {
                                        apiName: 'getZxSaProjectSettleAuditItemList',
                                        otherParams: {
                                            projectSettleAuditId: this.table?.getSelectedRows()[0]?.id,
                                        }
                                    },
                                    paginationConfig: false,
                                    isShowRowSelect: false,
                                    pageSize: 999,
                                    formConfig: [
                                        {
                                            table: {
                                                title: '统计项',
                                                dataIndex: 'statisticsName',
                                                key: 'statisticsName',
                                                width: 100,
                                                align: 'center',
                                            },
                                        },
                                        {
                                            table: {
                                                title: '本期（元）',
                                                dataIndex: 'thisAmt',
                                                key: 'thisAmt',
                                                width: 150,
                                                align: 'center',
                                                render: (val) => {
                                                    if (val && !isNaN(val)) {
                                                        return Number(val).toFixed(2)
                                                    } else {
                                                        return val
                                                    }
                                                },
                                            },
                                        },
                                        {
                                            table: {
                                                title: '开累（元）',
                                                dataIndex: 'totalAmt',
                                                key: 'totalAmt',
                                                width: 100,
                                                align: 'center',
                                                render: (val) => {
                                                    if (val && !isNaN(val)) {
                                                        return Number(val).toFixed(2)
                                                    } else {
                                                        return val
                                                    }
                                                },
                                            },
                                        },
                                    ],
                                }
                            }
                        ]}
                    />
                </Modal>
            </div>
        )
    }
}

export default index;