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
                        //闲置禁用
                        disabledForleaveUnused: (obj) => {
                            let data = obj.btnCallbackFn.getSelectedRows();
                            if (data.length === 1 && (data[0].isXianzhi === '' || data[0].isXianzhi === '0')) {
                                return false;
                            } else {
                                return true;
                            }
                        },
                        //闲置保存
                        saveForleaveUnused: (obj) => {
                            confirm({
                                content: '确定闲置吗?',
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
                        //恢复禁用
                        disabledForRecover: (obj) => {
                            let data = obj.btnCallbackFn.getSelectedRows();
                            if (data.length === 1 && data[0].isXianzhi === '1') {
                                return false;
                            } else {
                                return true;
                            }
                        },
                        //恢复保存
                        saveForRecover: (obj) => {
                            confirm({
                                content: '确定恢复吗?',
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
                        //二维码禁用
                        disabledForQRCode: (obj) => {
                            let data = obj.btnCallbackFn.getSelectedRows();
                            if (data.length === 1) return false;
                            return true;
                        },
                        //二维码保存
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
                                content: '确定下载二维码吗?',
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
                                tableField: "SearchForEquip"
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
                                title: '国产进口',
                                dataIndex: 'isMadeinChina',
                                key: 'isMadeinChina',
                                width: 100,
                                type: 'select',
                                fixed: 'left',
                                filter: true,
                            },
                            form: {
                                type: 'select',
                                optionConfig: { label: 'label', value: 'value', },
                                optionData: [
                                    { label: "国产", value: "0" },
                                    { label: "进口", value: "1" }
                                ],
                            }
                        },
                        {
                            table: {
                                title: '设备名称',
                                dataIndex: 'equipName',
                                key: 'equipName',
                                width: 150,
                                onClick: 'detail',
                                fixed: 'left',
                            }
                        },
                        {
                            table: {
                                title: '机械管理编号',
                                dataIndex: 'equipNo',
                                key: 'equipNo',
                                width: 200,
                            }
                        },
                        {
                            table: {
                                title: '购置日期',
                                dataIndex: 'purDate',
                                key: 'purDate',
                                format: 'YYYY-MM-DD',
                                placeholder: '请选择',
                                filter: true,
                                fieldsConfig: {
                                    type: 'rangeDate',
                                    field: 'purDateSearch'
                                },
                            },
                        },
                        {
                            table: {
                                title: '机种分类',
                                dataIndex: 'isWorkEquip',
                                key: 'isWorkEquip',
                                width: 150,
                                type: 'select',
                            },
                            form: {
                                type: 'select',
                                optionConfig: { label: 'label', value: 'value', },
                                optionData: [
                                    {
                                        label: "车辆",
                                        value: "0"
                                    },
                                    {
                                        label: "施工机械",
                                        value: "1"
                                    },
                                    {
                                        label: "其他机械",
                                        value: "2"
                                    }
                                ],
                            }
                        },
                        {
                            table: {
                                title: '所属单位',
                                dataIndex: 'ownerOrgName',
                                key: 'ownerOrgName',
                                width: 150,
                                filter: true,
                                fieldsConfig: { type: 'string' },
                            },
                        },
                        {
                            table: {
                                title: '使用单位',
                                dataIndex: 'useOrgName',
                                key: 'useOrgName',
                                width: 150,
                                filter: true,
                                fieldsConfig: { type: 'string' },
                            },
                        },
                        {
                            table: {
                                title: '所在地点',
                                dataIndex: 'usePlace',
                                key: 'usePlace',
                                width: 150,
                                filter: true,
                                fieldsConfig: { type: 'string' },
                            }
                        },
                        {
                            table: {
                                title: '管理单位',
                                dataIndex: 'ownerOrgName',
                                key: 'ownerOrgName',
                                width: 150,
                            }
                        },
                        {
                            table: {
                                title: '主机序列号',
                                dataIndex: 'mainserial',
                                key: 'mainserial',
                                width: 150,
                                filter: true,
                                fieldsConfig: { type: 'string' },
                            }
                        },
                        {
                            table: {
                                title: '底盘序列号',
                                dataIndex: 'downserial',
                                key: 'downserial',
                                width: 150,
                                filter: true,
                                fieldsConfig: { type: 'string' },
                            }
                        },
                        {
                            table: {
                                title: '原值',
                                dataIndex: 'orginalValue',
                                key: 'orginalValue',
                                width: 120,
                                filter: true,
                                fieldsConfig: { type: 'number' },
                            }
                        },
                        {
                            table: {
                                title: '净值',
                                dataIndex: 'leftValue',
                                key: 'leftValue',
                                width: 120,
                                filter: true,
                                fieldsConfig: { type: 'number' },
                            }
                        },
                        {
                            table: {
                                title: '车辆牌照号',
                                dataIndex: 'passNo',
                                key: 'passNo',
                                width: 150,
                                filter: true,
                                fieldsConfig: { type: 'string' },
                            }
                        },
                        {
                            table: {
                                title: '状态',
                                dataIndex: 'status',
                                key: 'status',
                                width: 150,
                            }
                        },
                        {
                            table: {
                                title: '入账日期',
                                dataIndex: 'regdate',
                                key: 'regdate',
                                width: 150,
                                format: 'YYYY-MM-DD',
                                filter: true,
                                fieldsConfig: {
                                    type: 'rangeDate',
                                    field: 'regdateSearch'
                                },
                            }
                        },
                        {
                            table: {
                                title: '计划批文号',
                                dataIndex: 'planNo',
                                key: 'planNo',
                                width: 150,
                            }
                        }
                    ]}
                    tabs={[
                        {
                            field: "form1",
                            name: "qnnForm",
                            title: "基础信息",
                            content: {
                                formConfig: [
                                    {
                                        type: "string",
                                        label: "主键ID",
                                        field: "id",
                                        hide: true
                                    },
                                    {
                                        label: 'ABCD分类',
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
                                        label: '国产进口',
                                        field: 'isMadeinChina',
                                        type: 'select',
                                        optionConfig: {
                                            label: 'label',
                                            value: 'value',
                                        },
                                        optionData: [
                                            {
                                                label: "国产",
                                                value: "0"
                                            },
                                            {
                                                label: "进口",
                                                value: "1"
                                            }
                                        ],
                                        allowClear: false,
                                        required: true,
                                        placeholder: '请选择',
                                        span: 8
                                    },
                                    {
                                        label: '机械管理编号',
                                        field: 'equipNo',
                                        type: 'string',
                                        addDisabled: true,
                                        editDisabled: true,
                                        span: 8
                                    },
                                    {
                                        label: '财务编号',
                                        field: 'financeNo',
                                        type: 'string',
                                        placeholder: '请输入',
                                        span: 8
                                    },
                                    {
                                        label: '购置日期',
                                        field: 'purDate',
                                        type: 'date',
                                        placeholder: '请选择',
                                        span: 8
                                    },
                                    {
                                        label: '设备名称',
                                        field: 'equipName',
                                        type: 'string',
                                        required: true,
                                        placeholder: '请输入',
                                        span: 8
                                    },
                                    {
                                        label: '机种分类',
                                        field: 'isWorkEquip',
                                        type: 'select',
                                        optionConfig: {
                                            label: 'label',
                                            value: 'value',
                                        },
                                        optionData: [
                                            {
                                                label: "车辆",
                                                value: "0"
                                            },
                                            {
                                                label: "施工机械",
                                                value: "1"
                                            },
                                            {
                                                label: "其他机械",
                                                value: "2"
                                            }
                                        ],
                                        allowClear: false,
                                        required: true,
                                        placeholder: '请选择',
                                        span: 8
                                    },
                                    {
                                        label: '所属单位',
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
                                        placeholder: '请选择',
                                        span: 8
                                    },
                                    {
                                        label: '使用单位',
                                        field: 'useOrgName',
                                        type: 'string',
                                        addDisabled: true,
                                        editDisabled: true,
                                        span: 8
                                    },
                                    {
                                        label: '所在地点',
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
                                        placeholder: '请选择',
                                        span: 8
                                    },
                                    {
                                        label: '管理单位',
                                        field: 'manageOrgID',
                                        type: 'string',
                                        addDisabled: true,
                                        editDisabled: true,
                                        span: 8
                                    },
                                    {
                                        label: '型号',
                                        field: 'model',
                                        type: 'string',
                                        required: true,
                                        placeholder: '请输入',
                                        span: 8
                                    },
                                    {
                                        label: '规格',
                                        field: 'spec',
                                        type: 'string',
                                        required: true,
                                        placeholder: '请输入',
                                        span: 8
                                    },
                                    {
                                        label: '总功率(KW)',
                                        field: 'power',
                                        type: 'number',
                                        min: 0,
                                        required: true,
                                        placeholder: '请输入',
                                        span: 8
                                    },
                                    {
                                        label: '国家厂家',
                                        field: 'factory',
                                        type: 'string',
                                        placeholder: '请输入',
                                        span: 8
                                    },
                                    {
                                        label: '来源',
                                        field: 'source',
                                        type: 'select',
                                        optionConfig: {
                                            label: 'label',
                                            value: 'value',
                                        },
                                        optionData: [
                                            {
                                                label: "局外调拨",
                                                value: "0"
                                            },
                                            {
                                                label: "局批自购",
                                                value: "1"
                                            }
                                        ],
                                        allowClear: false,
                                        required: true,
                                        placeholder: '请选择',
                                        span: 8
                                    },
                                    {
                                        label: '副机序列号',
                                        field: 'viceserial',
                                        type: 'string',
                                        placeholder: '请输入',
                                        span: 8
                                    },
                                    {
                                        label: '底盘厂家',
                                        field: 'downfactory',
                                        type: 'string',
                                        placeholder: '请输入',
                                        span: 8
                                    },
                                    {
                                        label: '副机出厂日期',
                                        field: 'viceoutfactory',
                                        type: 'date',
                                        placeholder: '请选择',
                                        span: 8
                                    },
                                    {
                                        label: '副机功率(KW)',
                                        field: 'vicepower',
                                        type: 'number',
                                        min: 0,
                                        placeholder: '请输入',
                                        span: 8
                                    },
                                    {
                                        label: '副机型号',
                                        field: 'vicespec',
                                        type: 'string',
                                        placeholder: '请输入',
                                        span: 8
                                    },
                                    {
                                        label: '副机厂牌',
                                        field: 'viceFactory',
                                        type: 'string',
                                        placeholder: '请输入',
                                        span: 8
                                    },
                                    {
                                        label: '主机功率(KW)',
                                        field: 'mainpower',
                                        type: 'number',
                                        min: 0,
                                        placeholder: '请输入',
                                        span: 8
                                    },
                                    {
                                        label: '主机出厂日期',
                                        field: 'mainoutfactory',
                                        type: 'date',
                                        placeholder: '请选择',
                                        span: 8
                                    },
                                    {
                                        label: '主机序列号',
                                        field: 'mainserial',
                                        type: 'string',
                                        placeholder: '请输入',
                                        span: 8
                                    },
                                    {
                                        label: '主机型号',
                                        field: 'mainspec',
                                        type: 'string',
                                        placeholder: '请输入',
                                        span: 8
                                    },
                                    {
                                        label: '主机厂牌',
                                        field: 'mainFactory',
                                        type: 'string',
                                        placeholder: '请输入',
                                        span: 8
                                    },
                                    {
                                        label: '底盘型式',
                                        field: 'downspec',
                                        type: 'string',
                                        placeholder: '请输入',
                                        span: 8
                                    },
                                    {
                                        label: '底盘序列号',
                                        field: 'downserial',
                                        type: 'string',
                                        placeholder: '请输入',
                                        span: 8
                                    },
                                    {
                                        label: '底盘出厂日期',
                                        field: 'downoutfactory',
                                        type: 'date',
                                        placeholder: '请选择',
                                        span: 8
                                    },
                                    {
                                        label: '外型尺寸长宽高(mm)',
                                        field: 'heightlong',
                                        type: 'string',
                                        placeholder: '请输入',
                                        span: 8
                                    },
                                    {
                                        label: '自重(t)',
                                        field: 'weight',
                                        type: 'string',
                                        placeholder: '请输入',
                                        span: 8
                                    },
                                    {
                                        label: '原值',
                                        field: 'orginalValue',
                                        type: 'number',
                                        min: 0,
                                        precision: 2,
                                        placeholder: '请输入',
                                        span: 8
                                    },
                                    {
                                        label: '净值',
                                        field: 'leftValue',
                                        type: 'number',
                                        min: 0,
                                        precision: 2,
                                        placeholder: '请输入',
                                        span: 8
                                    },
                                    {
                                        label: '引进fob价',
                                        field: 'fobPrice',
                                        type: 'number',
                                        span: 8
                                    },
                                    {
                                        label: '引进总价',
                                        field: 'fobAmount',
                                        type: 'number',
                                        span: 8
                                    },
                                    {
                                        label: '引进总折价',
                                        field: 'discountAmount',
                                        type: 'number',
                                        span: 8
                                    },
                                    {
                                        label: '验收时间',
                                        field: 'acceptanceDate',
                                        type: 'date',
                                        placeholder: '请选择',
                                        span: 8
                                    },
                                    {
                                        label: '车辆牌照号',
                                        field: 'passNo',
                                        type: 'string',
                                        placeholder: '请输入',
                                        span: 8
                                    },
                                    {
                                        label: '状态',
                                        field: 'status',
                                        type: 'string',
                                        span: 8
                                    },
                                    {
                                        label: '出厂日期',
                                        field: 'outFactoryDate',
                                        type: 'date',
                                        required: true,
                                        placeholder: '请选择',
                                        span: 8
                                    },
                                    {
                                        label: '出厂序列号',
                                        field: 'outFactorySerial',
                                        type: 'string',
                                        span: 8
                                    },
                                    {
                                        label: '重点设备标志',
                                        field: 'isimportant',
                                        type: 'radio',
                                        optionData: [
                                            {
                                                label: '否',
                                                value: '0'
                                            },
                                            {
                                                label: '是',
                                                value: '1'
                                            }
                                        ],
                                        initialValue: '0',
                                        required: true,
                                        placeholder: '请输入',
                                        span: 8
                                    },
                                    {
                                        label: '状态发生日期',
                                        field: 'changeDate',
                                        type: 'date',
                                        span: 8
                                    },
                                    {
                                        label: '已提折旧',
                                        field: 'actualDepreAmt',
                                        type: 'number',
                                        min: 0,
                                        placeholder: '请输入',
                                        span: 8
                                    },
                                    {
                                        label: '入账日期',
                                        field: 'regdate',
                                        type: 'date',
                                        placeholder: '请选择',
                                        span: 8
                                    },
                                    {
                                        label: '计划批文号',
                                        field: 'planNo',
                                        type: 'string',
                                        placeholder: '请输入',
                                        span: 8
                                    },
                                    {
                                        label: '备注',
                                        field: 'remark',
                                        type: 'textarea',
                                        placeholder: '请输入',
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
                            title: "公司内调拨",
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
                            title: "资产折旧",
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
                            title: "局内调拨",
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
                            title: "资产转让",
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
                            title: "设备报废",
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
                        title={'原值'}
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