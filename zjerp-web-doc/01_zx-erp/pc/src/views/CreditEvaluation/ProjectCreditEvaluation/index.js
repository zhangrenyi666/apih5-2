import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import { message as Msg, Modal } from 'antd';
const confirm = Modal.confirm;
const config = {
    antd: {
        rowKey: 'zxCrProjectEvaluationId',
        size: 'small',
        scroll: {
            y: document.documentElement.clientHeight * 0.6
        }
    },
    drawerConfig: {
        width: '70%'
    },
    paginationConfig: {
        position: 'bottom'
    },
    firstRowIsSearch: false,
    isShowRowSelect: true,
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
            ext1: curCompany?.ext1,
            departmentId: ((curCompany?.ext1 === '1' || curCompany?.ext1 === '2') && lockProject?.projectId) ? (lockProject.projectId !== 'all' ? lockProject.projectId : curCompany?.companyId) : curCompany?.projectId,
            isLocked: ((curCompany?.ext1 === '1' || curCompany?.ext1 === '2') && lockProject?.projectId) ? (lockProject.projectId !== 'all' ? true : false) : false
        }
    }
    FloatMulTwo(arg1, arg2) {
        //arg1标准分值
        //arg2输入的值
        var r1, r2, m, n;
        try { r1 = arg1.toString().split(".")[1].length } catch (e) { r1 = 0 }
        try { r2 = arg2.toString().split(".")[1].length } catch (e) { r2 = 0 }
        m = Math.pow(10, Math.max(r1, r2));
        n = (r1 >= r2) ? r1 : r2;
        return ((arg1 * m - arg2 * m) / m).toFixed(n);
    }
    render() {
        const { projectId, projectName, companyId, companyName } = this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany;
        const { departmentId, ext1, isLocked } = this.state;
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
                    fetchConfig={{
                        apiName: 'getZxCrProjectEvaluationList',
                        otherParams: {
                            orgID: departmentId
                        }
                    }}
                    drawerShowToggle={(obj) => {
                        let { drawerIsShow } = obj;
                        if (!drawerIsShow) {
                            obj.btnCallbackFn.refresh();
                        }
                    }}
                    {...config}
                    method={{
                        //新增保存按钮
                        addSubmitHide: (obj) => {
                            let index = obj.btnCallbackFn.getActiveKey();
                            if (index !== "0" || obj?.btnCallbackFn?.form?.getFieldValue?.('zxCrProjectEvaluationId')) {
                                return true;
                            } else {
                                return false;
                            }
                        },
                        addListOneSubmitHide: (obj) => {
                            let index = obj.btnCallbackFn.getActiveKey();
                            if (index !== "1") {
                                return true;
                            }
                            return false;
                        },
                        addListTwoSubmitHide: (obj) => {
                            let index = obj.btnCallbackFn.getActiveKey();
                            if (index !== "2") {
                                return true;
                            }
                            return false;
                        },
                        //新增保存按钮
                        disabledForAdd: () => {
                            if (ext1 === '3' || ext1 === '4' || isLocked) return false
                            return true
                        },
                        //新增保存按钮回调
                        addSuccessCb: (obj) => {
                            obj.btnCallbackFn.setBtnsLoading('add', 'diySubmit');
                            this.props.myFetch('addZxCrProjectEvaluation', obj._formData)
                                .then(({ data, success, message }) => {
                                    if (success) {
                                        Msg.success(message);
                                        this.table.setDeawerValues(data)
                                        if (obj._formData?.checkStandard === '0') {
                                            this.table.setDeawerValues({ ...data });
                                            obj.btnCallbackFn.setActiveKey('2');
                                        } else if (obj._formData?.checkStandard === '1') {
                                            this.table.setDeawerValues({ ...data });
                                            obj.btnCallbackFn.setActiveKey('1');
                                        }
                                    } else {
                                        Msg.error(message)
                                    }
                                    obj.btnCallbackFn.setBtnsLoading('remove', 'diySubmit');
                                }
                                );
                        },
                        addListOneSuccessCb: (obj) => {
                            obj.btnCallbackFn.setBtnsLoading('add', 'diySubmits');
                            let detailList = this.tableOne.getTableData()
                            this.props.myFetch('projectEvaluationScoreUpdateBath', detailList)
                                .then(
                                    ({ success, message }) => {
                                        obj.btnCallbackFn.setBtnsLoading('remove', 'diySubmits');
                                        if (success) {
                                            Msg.success(message);
                                            obj.qnnTableInstance.closeDrawer()
                                        } else {
                                            Msg.error(message)
                                        }
                                    }
                                );
                        },
                        addListTwoSuccessCb: (obj) => {
                            obj.btnCallbackFn.setBtnsLoading('add', 'diySubmitss');
                            let detailList = this.tableTwo.getTableData()
                            this.props.myFetch('projectEvaluationBadUpdateBath', detailList)
                                .then(
                                    ({ success, message }) => {
                                        obj.btnCallbackFn.setBtnsLoading('remove', 'diySubmitss');
                                        if (success) {
                                            Msg.success(message);
                                            obj.qnnTableInstance.closeDrawer()
                                        } else {
                                            Msg.error(message)
                                        }
                                    }
                                );

                        },
                        //清单保存按钮
                        disabledForListSubmit: (obj) => {
                            let data = obj.btnCallbackFn.getSelectedRows()
                            if (data.length !== 1 || ext1 === '1' || ext1 === '2') {
                                return true
                            }
                            if (data?.[0].auditStatus === '1') {
                                return true
                            }
                        },
                        //修改清单保存按钮回调
                        editListSubmitCb: async (obj) => {
                            obj.btnCallbackFn.setBtnsLoading('add', 'editSubmit');
                            const { myFetch } = this.props;
                            if (obj.btnCallbackFn.getTabsIndex() === "0") {
                                myFetch('updateZxCrProjectEvaluation', obj._formData)
                                    .then(
                                        ({ data, success, message }) => {
                                            obj.btnCallbackFn.setBtnsLoading('remove', 'editSubmit')
                                            if (success) {
                                                Msg.success(message);
                                                if (obj._formData.checkStandard === '0') {
                                                    obj.btnCallbackFn.setActiveKey('2');
                                                } else if (obj._formData.checkStandard === '1') {
                                                    obj.btnCallbackFn.setActiveKey('1');
                                                }
                                            } else {
                                                Msg.error(message)
                                            }
                                        }
                                    );
                            } else if (obj.btnCallbackFn.getTabsIndex() === "1") {
                                let detailList = await this.tableOne.getTableData()
                                myFetch('projectEvaluationScoreUpdateBath', detailList)
                                    .then(
                                        ({ success, message }) => {
                                            obj.btnCallbackFn.setBtnsLoading('remove', 'editSubmit')
                                            if (success) {
                                                Msg.success(message);
                                                obj.qnnTableInstance.closeDrawer()
                                            } else {
                                                Msg.error(message)
                                            }
                                        }
                                    );
                            } else if (obj.btnCallbackFn.getTabsIndex() === "2") {
                                let detailList = await this.tableTwo.getTableData()
                                console.log(detailList);
                                myFetch('projectEvaluationBadUpdateBath', detailList)
                                    .then(
                                        ({ success, message }) => {
                                            obj.btnCallbackFn.setBtnsLoading('remove', 'editSubmit')
                                            if (success) {
                                                Msg.success(message);
                                                obj.qnnTableInstance.closeDrawer()
                                            } else {
                                                Msg.error(message)
                                            }
                                        }
                                    );
                            }
                        },
                        //删除按钮禁用
                        disabledForDelete: (obj) => {
                            let data = obj.btnCallbackFn.getSelectedRows();
                            let disabledLength = 0
                            for (var i = 0; i < data.length; i++) {
                                if (data[i].auditStatus === '1') {
                                    disabledLength++
                                }
                            }
                            if (disabledLength > 0 || data.length === 0) return true
                        },
                        //删除按钮回调
                        cbForDelete: (obj) => {
                            confirm({
                                content: '确定删除选中的数据吗?',
                                onOk: () => {
                                    let data = obj.btnCallbackFn.getSelectedRows();
                                    this.props.myFetch('batchDeleteUpdateZxCrProjectEvaluation', data).then(
                                        ({ success, message }) => {
                                            if (success) {
                                                Msg.success(message)
                                                this.table.refresh();
                                                this.table.clearSelectedRows();
                                            } else {
                                                Msg.error(message)
                                            }
                                        }
                                    );
                                }
                            });
                        },
                        //流程按钮禁用
                        disabledForFlow: (obj) => {
                            let data = obj.btnCallbackFn.getSelectedRows();
                            if (data.length !== 1 || data[0].auditStatus === '1') {
                                return true;
                            } else {
                                return false;
                            }
                        },
                        //流程按钮回调
                        cbForFlow: (obj) => {
                            let data = obj.btnCallbackFn.getSelectedRows();
                            const { myFetch } = this.props;
                            data[0].companyID = '1';
                            confirm({
                                content: '确定审核选中的数据吗?',
                                onOk: () => {
                                    myFetch('updateAuditStatus', data[0]).then(
                                        ({ success }) => {
                                            if (success) {
                                                this.table.refresh();
                                                this.table.clearSelectedRows();
                                            }
                                        }
                                    );
                                }
                            })
                        },
                    }}
                    actionBtns={{
                        apiName: "getSysMenuBtn",
                        otherParams: (obj) => {
                            var props = obj.props;
                            let curRouteData = props.routerInfo.routeData[props.routerInfo.curKey];
                            return {
                                menuParentId: curRouteData._value,
                                tableField: "ProjectCreditEvaluation"
                            }
                        }
                    }}
                    formConfig={[
                        {
                            table: {
                                title: '项目名称',
                                width: 180,
                                fixed: 'left',
                                dataIndex: 'projectName',
                                key: 'projectName',
                                onClick: 'detail',
                                filter: true,
                                fieldsConfig: { type: 'string' }
                            }
                        },
                        {
                            table: {
                                title: '协作单位名称',
                                dataIndex: 'customerName',
                                key: 'customerName',
                                width: 200,
                                filter: true,
                                fieldsConfig: { type: 'string' }
                            }
                        },
                        {
                            table: {
                                title: '组织机构代码',
                                width: 180,
                                dataIndex: 'orgCertificate',
                                key: 'orgCertificate',
                                filter: true,
                                fieldsConfig: { type: 'string' }
                            }
                        },
                        {
                            table: {
                                title: '考核期次',
                                dataIndex: 'period',
                                key: 'period',
                                type: 'select',
                                width: 120,
                                filter: true,
                            },
                            form: {
                                type: 'select',
                                optionConfig: {
                                    label: 'itemName',
                                    value: 'itemId'
                                },
                                fetchConfig: {
                                    apiName: "getBaseCodeSelect",
                                    otherParams: {
                                        itemId: 'qiCi'//问张启明
                                    }
                                }
                            }
                        },
                        {
                            table: {
                                title: '考核标准',
                                width: 150,
                                dataIndex: 'checkStandard',
                                key: 'checkStandard',
                                type: 'select',
                                filter: true,
                            },
                            form: {
                                type: 'select',
                                optionConfig: {
                                    label: 'label',
                                    value: 'value'
                                },
                                optionData: [
                                    {
                                        label: '严重不良行为考核表',
                                        value: '0'
                                    },
                                    {
                                        label: '打分考核表',
                                        value: '1'
                                    }
                                ],
                            }
                        },
                        {
                            table: {
                                title: '专业类别',
                                dataIndex: 'catID',
                                key: 'catID',
                                type: 'select',
                                width: 120,
                                filter: true,
                            },
                            form: {
                                type: 'select',
                                optionConfig: {
                                    label: 'catName',
                                    value: 'id',
                                },
                                fetchConfig: {
                                    apiName: 'getZxCrProjectEvaluationListCatName',
                                }
                            }
                        },
                        {
                            table: {
                                title: '项目考核得分',
                                dataIndex: 'totalScore',
                                key: 'totalScore',
                                width: 120,
                            }
                        },
                        {
                            table: {
                                title: '项目考核日期',
                                dataIndex: 'checkDate',
                                key: 'checkDate',
                                width: 120,
                                format: 'YYYY-MM-DD'
                            }
                        },
                        {
                            table: {
                                title: '协作单位负责人',
                                dataIndex: 'chargeMan',
                                width: 160,
                                key: 'chargeMan'
                            }
                        },
                        {
                            table: {
                                title: '进场日期',
                                dataIndex: 'inDate',
                                key: 'inDate',
                                format: 'YYYY-MM-DD',
                                width: 120,
                            }
                        },
                        {
                            table: {
                                title: '退场日期',
                                dataIndex: 'outDate',
                                key: 'outDate',
                                format: 'YYYY-MM-DD',
                                width: 120,
                            }
                        },
                        {
                            table: {
                                title: '所属单位',
                                dataIndex: 'orgName',
                                key: 'orgName',
                                width: 120,
                                filter: true,
                                fieldsConfig: { type: 'string' }
                            }
                        },
                        {
                            table: {
                                title: '状态',
                                dataIndex: 'auditStatus',
                                key: 'auditStatus',
                                width: 100,
                                fixed: 'right',
                                render: (data) => {
                                    if (data === '0') {
                                        return '未审核';
                                    } else if (data === '1') {
                                        return '已审核';
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
                                        field: 'zxCrProjectEvaluationId',
                                        type: 'string',
                                        hide: true,
                                    },
                                    {
                                        field: 'isDropToD',
                                        type: 'string',
                                        hide: true,
                                    },
                                    {
                                        field: 'projectId',
                                        type: 'string',
                                        initialValue: projectId,
                                        hide: true,
                                    },
                                    {
                                        field: 'orgID',
                                        type: 'string',
                                        initialValue: departmentId,
                                        hide: true,
                                    },
                                    {
                                        field: 'companyId',
                                        type: 'string',
                                        initialValue: companyId,
                                        hide: true,
                                    },
                                    {
                                        hide: true,
                                        type: 'string',
                                        field: 'customerName'
                                    },
                                    {
                                        type: 'string',
                                        hide: true,
                                        field: 'parentID'
                                    },
                                    {
                                        type: 'string',
                                        hide: true,
                                        field: 'resName'
                                    },
                                    {
                                        label: '所属单位',
                                        field: 'orgName',
                                        type: 'string',
                                        initialValue: companyName,
                                        addDisabled: true,
                                        editDisabled: true,
                                        span: 12,
                                    },
                                    {
                                        label: '项目名称',
                                        field: 'projectName',
                                        type: 'string',
                                        initialValue: projectName,
                                        addDisabled: true,
                                        editDisabled: true,
                                        span: 12,
                                    },
                                    {
                                        label: '考核期次',
                                        field: 'period',
                                        type: 'select',
                                        required: true,
                                        optionConfig: {
                                            label: 'itemName',
                                            value: 'itemId'
                                        },
                                        fetchConfig: {
                                            apiName: "getBaseCodeSelect",
                                            otherParams: {
                                                itemId: 'qiCi'//问张启明
                                            },
                                        },
                                        placeholder: '请输入',
                                        span: 12,
                                    },
                                    {
                                        label: '协作单位名称',
                                        field: 'customerId',
                                        required: true,
                                        type: 'selectByPaging',
                                        showSearch: true,
                                        optionConfig: {
                                            label: 'customerName',
                                            value: 'orgCertificate',
                                            linkageFields: {
                                                customerName: "customerName",
                                                orgCertificate: 'orgCertificate'
                                            }
                                        },
                                        fetchConfig: {
                                            apiName: 'getZxCrCustomerInfoListAll'
                                        },
                                        placeholder: '请选择',
                                        span: 12,
                                    },
                                    {
                                        label: '组织机构代码',
                                        field: 'orgCertificate',
                                        type: 'string',
                                        addDisabled: true,
                                        editDisabled: true,
                                        span: 12,
                                    },
                                    {
                                        label: '承建工程合同额(万元)',
                                        field: 'contractAmt',
                                        type: 'number',
                                        placeholder: '请输入',
                                        span: 12,
                                        required: true,
                                    },
                                    {
                                        label: '专业类别',
                                        type: 'select',
                                        field: 'catID',
                                        required: true,
                                        optionConfig: {
                                            label: 'catName',
                                            value: 'id',
                                            linkageFields: {
                                                parentID: 'parentID'
                                            }
                                        },
                                        fetchConfig: {
                                            apiName: 'getZxCrProjectEvaluationListCatName',
                                        },
                                        span: 12,
                                        onChange: () => {
                                            this.table.btnCallbackFn.refresh();
                                        },
                                    },
                                    {
                                        label: '分类名称',
                                        type: 'select',
                                        required: true,
                                        field: 'resID',
                                        optionConfig: {
                                            label: 'resName',
                                            value: 'id',
                                            linkageFields: {
                                                resCode: "resCode",
                                                resName: 'resName'
                                            }
                                        },
                                        dependenciesReRender: true,//多个依赖-配置
                                        dependencies: ['parentID', 'catID'],
                                        fetchConfig: {//parentID应该存在列表接口里面--待验证
                                            apiName: 'getZxCrProjectEvaluationListResName',
                                            otherParams: (val) => {
                                                let parentIDVal = '';
                                                let catIDVal = '';
                                                if (val.btnCallbackFn?.form) {
                                                    let aa = val.btnCallbackFn.form.getFieldsValue();
                                                    parentIDVal = aa.parentID;
                                                    catIDVal = aa.catID;
                                                } else {
                                                    parentIDVal = '';
                                                    catIDVal = '';
                                                }
                                                return {
                                                    parentID: parentIDVal,
                                                    catID: catIDVal
                                                }
                                            }
                                        },
                                        span: 12
                                    },
                                    {
                                        label: '考核标准',
                                        type: 'select',
                                        required: true,
                                        field: 'checkStandard',
                                        optionConfig: {
                                            label: 'label',
                                            value: 'value'
                                        },
                                        optionData: [
                                            {
                                                label: '严重不良行为考核表',
                                                value: '0'
                                            },
                                            {
                                                label: '打分考核表',
                                                value: '1'
                                            }
                                        ],
                                        span: 12,
                                        onChange: (val) => {
                                            if (val === '0') {
                                                this.table.form.setFieldsValue({ totalScore: 0 })
                                            }
                                        }
                                    },
                                    {
                                        label: '协作单位负责人',
                                        field: 'chargeMan',
                                        type: 'string',
                                        placeholder: '请输入',
                                        span: 12,
                                    },
                                    {
                                        label: '负责人联系电话',
                                        field: 'chargeManPhone',
                                        type: 'number',
                                        placeholder: '请输入',
                                        span: 12,
                                    },
                                    {
                                        label: '项目考核得分',
                                        field: 'totalScore',
                                        type: 'number',
                                        addDisabled: true,
                                        editDisabled: true,
                                        span: 12,
                                    },
                                    {
                                        label: '填报人',
                                        field: 'preparer',
                                        type: 'string',
                                        addDisabled: true,
                                        editDisabled: true,
                                        initialValue: () => {
                                            return this.props.loginAndLogoutInfo.loginInfo.userInfo.realName
                                        },
                                        span: 12,
                                    },
                                    {
                                        label: '审核人',
                                        type: 'string',
                                        addDisabled: true,
                                        editDisabled: true,
                                        field: 'auditor',
                                        span: 12,
                                    },
                                    {
                                        label: '项目考核日期',
                                        type: 'date',
                                        field: 'checkDate',
                                        span: 12,
                                    },
                                    {
                                        label: '进场日期',
                                        type: 'date',
                                        required: true,
                                        field: 'inDate',
                                        span: 12,
                                    },
                                    {
                                        label: '退场日期',
                                        type: 'date',
                                        field: 'outDate',
                                        span: 12,
                                    },
                                    {
                                        label: '附件',
                                        field: 'fileList',
                                        type: 'files',
                                        fetchConfig: {
                                            apiName: 'upload'
                                        },
                                        span: 12,
                                    }
                                ]
                            }
                        },
                        {
                            field: "table1",
                            name: "qnnTable",
                            title: "打分考核表",
                            disabled: (obj) => {
                                if (obj.clickCb.rowInfo.name === "edit" || obj.clickCb.rowInfo.name === "detail") {
                                    if (obj?.clickCb?.rowData?.checkStandard !== '1') {
                                        return true
                                    }
                                    return false
                                } else if (obj.btnCallbackFn.form.getFieldValue("zxCrProjectEvaluationId") && obj.btnCallbackFn.form.getFieldValue("checkStandard") === '1') {
                                    return false
                                }
                                return true
                            },
                            content: {
                                fetchConfig: (obj) => {
                                    return {
                                        apiName: 'getZxCrProjectEvaluationScoreList',
                                        otherParams: { mainID: this.table.form.getFieldValue('zxCrProjectEvaluationId') },
                                    }
                                },
                                wrappedComponentRef: (me) => {
                                    this.tableOne = me;
                                },
                                antd: {
                                    size: "small",
                                    rowKey: "zxCrProjectEvaluationScoreId",
                                },
                                paginationConfig: false,
                                pageSize: 99,
                                isShowRowSelect: false,
                                formConfig: [
                                    {
                                        isInTable: false,
                                        form: {
                                            label: '主键id',
                                            field: 'id',
                                            hide: true
                                        }
                                    },
                                    {
                                        table: {
                                            title: '评价内容',
                                            width: 220,
                                            dataIndex: 'evalContent',
                                            key: 'evalContent'
                                        },
                                    },
                                    {
                                        table: {
                                            title: '评价细目',
                                            width: 220,
                                            dataIndex: 'scoreItem',
                                            key: 'scoreItem'
                                        },
                                    },
                                    {
                                        table: {
                                            title: '标准分值',
                                            width: 100,
                                            dataIndex: 'standardScore',
                                            key: 'standardScore'
                                        },
                                    },
                                    {
                                        table: {
                                            title: '项目减分',
                                            width: 100,
                                            dataIndex: 'deductScore',
                                            key: 'deductScore',
                                            tdEdit: true
                                        },
                                        form: {
                                            type: 'number',
                                            field: 'deductScore',
                                            precision: 1,
                                            onChange: async (val, obj) => {
                                                let rowData = await obj.qnnTableInstance.getEditedRowData()
                                                let value = rowData.deductScore
                                                if (!value && value !== 0) {
                                                    rowData.deductScore = 0;
                                                    rowData.getScore = rowData.category === '1' ? rowData.standardScore : 0
                                                    await obj.qnnTableInstance.setEditedRowData(rowData)
                                                    return
                                                }
                                                if (value < 0) {
                                                    Msg.warn('请输入正确的数值');
                                                    rowData.deductScore = 0;
                                                    rowData.getScore = rowData.standardScore
                                                    await obj.qnnTableInstance.setEditedRowData(rowData)
                                                    return
                                                }
                                                if (value > rowData.standardScore) {
                                                    Msg.warn('不能大于标准分值！');
                                                    rowData.deductScore = 0;
                                                    if (rowData.category === '1') {
                                                        rowData.getScore = rowData.standardScore
                                                    } else if (rowData.category === '2') {
                                                        rowData.getScore = 0
                                                    }
                                                    await obj.qnnTableInstance.setEditedRowData(rowData)
                                                    return
                                                }
                                                if (rowData.category === '1') {
                                                    rowData.getScore = Number(this.FloatMulTwo(rowData.standardScore, value));
                                                } else if (rowData.category === '2') {
                                                    rowData.getScore = value
                                                }
                                                await obj.qnnTableInstance.setEditedRowData(rowData)
                                            }
                                        }
                                    },
                                    {
                                        table: {
                                            title: '项目得分',
                                            width: 100,
                                            dataIndex: 'getScore',
                                            key: 'getScore'
                                        },
                                        form: {
                                            type: 'number',
                                            precision: 1
                                        }
                                    },
                                    {
                                        table: {
                                            title: '备注',
                                            dataIndex: 'remarks',
                                            width: 120,
                                            key: 'remarks',
                                            tdEdit: true
                                        },
                                        form: {
                                            type: 'string',
                                            field: 'remarks',
                                        }
                                    }
                                ]
                            }
                        },
                        {
                            field: "table2",
                            name: "qnnTable",
                            title: "严重不良行为考核表",
                            disabled: (obj) => {
                                if (obj.clickCb.rowInfo.name === "edit" || obj.clickCb.rowInfo.name === "detail") {
                                    if (obj?.clickCb?.rowData?.checkStandard !== '0') return true
                                    return false
                                } else if (obj.btnCallbackFn.form.getFieldValue("zxCrProjectEvaluationId") && obj.btnCallbackFn.form.getFieldValue("checkStandard") === '0') {
                                    return false
                                }
                                return true
                            },
                            content: {
                                fetchConfig: (obj) => {
                                    return {
                                        apiName: 'getZxCrProjectEvaluationBadList',
                                        otherParams: { mainID: this.table.form.getFieldValue('zxCrProjectEvaluationId') },
                                    }
                                },
                                wrappedComponentRef: (me) => {
                                    this.tableTwo = me;
                                },
                                antd: {
                                    size: "small",
                                    rowKey: "zxCrProjectEvaluationBadId",
                                },
                                paginationConfig: false,
                                pageSize: 99,
                                isShowRowSelect: false,
                                formConfig: [
                                    {
                                        isInTable: false,
                                        form: {
                                            label: '主键id',
                                            field: 'id',
                                            hide: true
                                        }
                                    },
                                    {
                                        table: {
                                            title: '评价内容',
                                            width: 220,
                                            dataIndex: 'evalContent',
                                            key: 'evalContent'
                                        }
                                    },
                                    {
                                        table: {
                                            title: '评价细目',
                                            width: 220,
                                            dataIndex: 'scoreItem',
                                            key: 'scoreItem'
                                        }
                                    },
                                    {
                                        table: {
                                            title: '是否存在严重不良行为',
                                            width: 100,
                                            dataIndex: 'isBad',
                                            key: 'isBad',
                                            type: 'select',
                                            tdEdit: true
                                        },
                                        form: {
                                            type: 'select',
                                            field: 'isBad',
                                            optionConfig: {
                                                label: 'label', //默认 label
                                                value: 'value'
                                            },
                                            optionData: [//问张启明
                                                {
                                                    label: '否',
                                                    value: '0'
                                                },
                                                {
                                                    label: '是',
                                                    value: '1'
                                                }
                                            ]
                                        }
                                    },
                                    {
                                        table: {
                                            title: '备注',
                                            dataIndex: 'remarks',
                                            width: 120,
                                            key: 'remarks',
                                            tdEdit: true
                                        },
                                        form: {
                                            type: 'textarea',
                                            field: 'remarks',
                                            autoSize: {
                                                minRows: 1,
                                                maxRows: 3
                                            }
                                        }
                                    }
                                ]
                            }
                        }
                    ]}
                />
            </div>
        );
    }
}

export default index;