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
                            ureportFlag: 'comCar',
                        }
                    }}
                    actionBtns={[
                        {
                            name: 'leaveUnused',//内置add del
                            type: 'primary',//类型  默认 primary  [primary dashed danger]
                            label: '闲置',
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
                                    content: '确定闲置吗?',
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
                            name: 'recover',//内置add del
                            type: 'primary',//类型  默认 primary  [primary dashed danger]
                            label: '恢复',
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
                                    content: '确定恢复吗?',
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
                            name: 'edit',//内置add del
                            icon: 'edit',//icon
                            type: 'primary',//类型  默认 primary  [primary dashed danger]
                            label: '修改',
                            willExecute: (obj) => {
                                obj.btnCallbackFn.setActiveKey('0');
                            },
                            hide: true,
                            formBtns: [
                                {
                                    name: 'cancel', //关闭右边抽屉
                                    field: 'cancel',
                                    type: 'dashed',//类型  默认 primary
                                    label: '取消',
                                },
                                {
                                    name: 'submit',//内置add del
                                    field: 'submit',
                                    type: 'primary',//类型  默认 primary
                                    label: '保存',//提交数据并且关闭右边抽屉 
                                    fetchConfig: {//ajax配置
                                        apiName: 'updateZxEqEquip',
                                    },
                                    onClick: (objs) => {
                                        objs.btnCallbackFn.clearSelectedRows();
                                    }
                                }
                            ]
                        },
                        // {
                        //     name: 'QRCode',//内置add del
                        //     type: 'primary',//类型  默认 primary  [primary dashed danger]
                        //     label: '二维码',
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
                        //     name: 'QRCodeDownload',//内置add del
                        //     type: 'primary',//类型  默认 primary  [primary dashed danger]
                        //     label: '二维码下载',
                        //     disabled: (obj) => {
                        //         if (obj.btnCallbackFn.getSelectedRows().length === 1) {
                        //             return false;
                        //         } else {
                        //             return true;
                        //         }
                        //     },
                        //     onClick: (obj) => {
                        //         confirm({
                        //             content: '确定下载二维码吗?',
                        //             onOk: () => {
                        //                 let { domain } = obj.props.myPublic;
                        //                 let qrcodeDownUrl = obj.selectedRows[0].qrcodeDownUrl;
                        //                 window.location.href = domain + qrcodeDownUrl;
                        //             }
                        //         });
                        //     }
                        // },
                        {
                            name: 'QRCodeDownload',//内置add del
                            type: 'primary',//类型  默认 primary  [primary dashed danger]
                            label: '原值',
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
                                title: '机械管理编号',
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
                                title: '财务固定资产编号',
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
                                title: '设备名称',
                                dataIndex: 'equipName',
                                key: 'equipName',
                                width: 150,
                                filter: true,
                                fieldsConfig: { type: 'string' },
                            }
                        },
                        {
                            table: {
                                title: '国家厂家',
                                dataIndex: 'factory',
                                key: 'factory',
                                width: 150,
                            }
                        },
                        {
                            table: {
                                title: '型号',
                                dataIndex: 'model',
                                key: 'model',
                                width: 150,
                            }
                        },
                        {
                            table: {
                                title: '规格',
                                dataIndex: 'spec',
                                key: 'spec',
                                width: 150,
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
                            },
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
                                    field: 'regdate'
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
                        },
                        {
                            table: {
                                title: '是否闲置',
                                dataIndex: 'isXianzhi',
                                key: 'isXianzhi',
                                width: 100,
                                fixed: 'right',
                                render: (data) => {
                                    if (data === '0') {
                                        return '否';
                                    } else if (data === '1') {
                                        return '是';
                                    }
                                }
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
                                        label: 'ABCD分类',
                                        field: 'abcName',
                                        type: 'string',
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
                                        label: '财务固定资产编号',
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
                                        label: '国家厂家',
                                        field: 'factory',
                                        type: 'string',
                                        placeholder: '请输入',
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
                                        label: '设备分类',
                                        field: 'resCatalog',
                                        type: 'string',
                                        required: true,
                                        placeholder: '请输入',
                                        span: 8
                                    },
                                    {
                                        label: '设备分类',
                                        field: 'resCatalogID',
                                        type: 'string',
                                        hide: true
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
                                                        departmentId
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
                                        label: '总功率(KW)',
                                        field: 'power',
                                        type: 'number',
                                        min: 0,
                                        required: true,
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
                                        label: '副机系列号',
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
                                        label: '主机出厂日期',
                                        field: 'mainoutfactory',
                                        type: 'date',
                                        placeholder: '请选择',
                                        span: 8
                                    },
                                    {
                                        label: '主机系列号',
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
                                        label: '底盘系列号',
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
                                        label: '验收时间',
                                        field: 'acceptanceDate',
                                        type: 'date',
                                        placeholder: '请选择',
                                        span: 8
                                    },
                                    {
                                        label: '牌照号码',
                                        field: 'passNo',
                                        type: 'string',
                                        placeholder: '请输入',
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
                                        label: '已提折旧',
                                        field: 'actualDepreAmt',
                                        type: 'number',
                                        min: 0,
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
                                        label: '副机功率(KW)',
                                        field: 'vicepower',
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
                                    },
                                    {
                                        label: "附件",
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
                            name: "diyComponent3",
                            field: "TabFiveList",
                            hide: function (obj) {
                                return (obj.clickCb.rowInfo.name === 'edit' ? true : false)
                            },
                            title: "运转记录",
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