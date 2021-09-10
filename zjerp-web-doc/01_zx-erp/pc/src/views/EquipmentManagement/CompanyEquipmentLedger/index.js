import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import s from "./style.less";
import { Modal, Avatar, message as Msg } from "antd";
const { confirm, info } = Modal;
const config = {
    antd: {
        rowKey: 'id',
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
            departmentId: ((curCompany?.ext1 === '1' || curCompany?.ext1 === '2') && lockProject?.projectId) ? (lockProject.projectId !== 'all' ? lockProject.projectId : curCompany?.companyId) : curCompany?.projectId
        }
    }
    render () {
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
                        apiName: 'getZxEqEquipList',
                        otherParams: {
                            useOrgID: departmentId,
                            statusFlag: '0'
                        }
                    }}
                    actionBtns={{
                        apiName: "getSysMenuBtn",
                        otherParams: (obj) => {
                            let props = obj.props;
                            let curRouteData = props.routerInfo.routeData[props.routerInfo.curKey];
                            return {
                                menuParentId: curRouteData._value,
                                tableField: "CompanyEquipmentLedger"
                            }
                        }
                    }}
                    method={{
                        leaveUnusedDisabled: (obj) => {
                            let data = obj.btnCallbackFn.getSelectedRows();
                            if (data.length === 1 && (data[0].isXianzhi === '' || data[0].isXianzhi === '0')) {
                                return false;
                            } else {
                                return true;
                            }
                        },
                        leaveUnusedOnClick: (obj) => {
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
                        },
                        recoverDisabled: (obj) => {
                            let data = obj.btnCallbackFn.getSelectedRows();
                            if (data.length === 1 && data[0].isXianzhi === '1') {
                                return false;
                            } else {
                                return true;
                            }
                        },
                        recoverOnClick: (obj) => {
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
                        },
                        QRCodeDisabled: (obj) => {
                            if (obj.btnCallbackFn.getSelectedRows().length === 1) {
                                return false;
                            } else {
                                return true;
                            }
                        },
                        QRCodeOnClick: (obj) => {
                            let { domain } = obj.props.myPublic;
                            let qrcodeUrl = obj.selectedRows[0].qrcodeUrl;
                            let qrcodeName = obj.selectedRows[0].qrcodeName;
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
                        QRCodeDownloadDisabled: (obj) => {
                            if (obj.btnCallbackFn.getSelectedRows().length === 1) {
                                return false;
                            } else {
                                return true;
                            }
                        },
                        QRCodeDownloadOnClick: (obj) => {
                            confirm({
                                content: '确定下载二维码吗?',
                                onOk: () => {
                                    let { domain } = obj.props.myPublic;
                                    let qrcodeDownUrl = obj.selectedRows[0].qrcodeDownUrl;
                                    window.location.href = domain + qrcodeDownUrl;
                                }
                            });
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
                                        type: "string",
                                        label: "主键ID",
                                        field: "id", //唯一的字段名 ***必传
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
                                        showSearch: true,
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
                                        showSearch: true,
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
                                        placeholder: '请输入',
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
                                        // optionConfig: {
                                        //     label: 'departmentName',
                                        //     value: 'departmentId',
                                        //     linkageFields:{
                                        //         ownerOrgName:'departmentName'
                                        //     }
                                        // },
                                        // fetchConfig: {
                                        //     apiName: 'getSysCompanyProject',
                                        //     otherParams:{
                                        //         departmentId:departmentId
                                        //     }
                                        // },
                                        // allowClear: false,
                                        // showSearch: true,
                                        required: true,
                                        addDisabled: true,
                                        editDisabled: true,
                                        placeholder: '请输入',
                                        span: 8
                                    },
                                    {
                                        label: '使用单位',
                                        field: 'useOrgName',
                                        type: 'string',
                                        // optionConfig: {
                                        //     label: 'departmentName',
                                        //     value: 'departmentId',
                                        //     linkageFields:{
                                        //         useOrgName:'departmentName'
                                        //     }
                                        // },
                                        // fetchConfig: {
                                        //     apiName: 'getSysCompanyProject',
                                        //     otherParams:{
                                        //         departmentId:departmentId
                                        //     }
                                        // },
                                        // allowClear: false,
                                        // showSearch: true,
                                        addDisabled: true,
                                        editDisabled: true,
                                        placeholder: '请输入',
                                        span: 8
                                    },
                                    {
                                        label: '所在地点',
                                        field: 'usePlace',
                                        type: 'string',
                                        // optionConfig: {
                                        //     label: 'itemName',
                                        //     value: 'itemName',
                                        // },
                                        // fetchConfig: {
                                        //     apiName: "getBaseCodeSelect",
                                        //     otherParams: {
                                        //         itemId: 'xingzhengquhuadaima'
                                        //     }
                                        // },
                                        allowClear: false,
                                        showSearch: true,
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
                                        showSearch: true,
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
                                        label: '付机功率(KW)',
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
                                        label: '是否闲置',
                                        field: 'isXianzhi',
                                        type: 'select',
                                        optionConfig: {
                                            label: 'label',
                                            value: 'value'
                                        },
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
                                        placeholder: '请选择',
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
                            field: "table1",
                            name: "qnnTable",
                            title: "公司内调拨",
                            disabled: (obj) => {
                                if (obj.clickCb.rowInfo.name === 'edit') {
                                    return true;
                                } else {
                                    return false;
                                }
                            },
                            content: {
                                tabs: [],
                                fetchConfig: {
                                    apiName: 'getZxEqMoveUseOrgItemListForTab',
                                    otherParams: (obj) => {
                                        let rowData = obj.clickCb.rowData;
                                        if (rowData) {
                                            return { equipID: obj.clickCb.rowData.id };
                                        }
                                    },
                                },
                                wrappedComponentRef: (me) => {
                                    this.tableGS = me;
                                },
                                drawerConfig: {
                                    width: "1000px"
                                },
                                antd: {
                                    rowKey: 'id',
                                    size: 'small'
                                },
                                paginationConfig: {
                                    position: "bottom"
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
                                },
                                isShowRowSelect: false,
                                formConfig: [
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
                                            title: '调令号',
                                            dataIndex: 'transferNo',
                                            key: 'transferNo',
                                            filter: true,
                                            onClick: 'detail'
                                        },
                                        form: {
                                            type: 'string',
                                            placeholder: '请输入',
                                            spanForm: 8
                                        }
                                    },
                                    {
                                        table: {
                                            title: '调令日期',
                                            dataIndex: 'movedate',
                                            key: 'movedate',
                                            format: 'YYYY-MM-DD'
                                        },
                                        form: {
                                            type: 'date',
                                            placeholder: '请选择',
                                            spanForm: 8
                                        }
                                    },
                                    {
                                        table: {
                                            title: '调出方',
                                            dataIndex: 'outOrgName',
                                            key: 'outOrgName'
                                        },
                                        form: {
                                            type: 'string',
                                            allowClear: false,
                                            showSearch: true,
                                            placeholder: '请输入',
                                            spanForm: 8
                                        }
                                    },
                                    {
                                        table: {
                                            title: '接收方',
                                            dataIndex: 'acceptOrgName',
                                            key: 'acceptOrgName'
                                        },
                                        form: {
                                            type: 'string',
                                            allowClear: false,
                                            showSearch: true,
                                            placeholder: '请输入',
                                            spanForm: 8
                                        }
                                    },
                                    {
                                        table: {
                                            title: '原值',
                                            dataIndex: 'orginalValue',
                                            key: 'orginalValue'
                                        },
                                        form: {
                                            type: 'number',
                                            min: 0,
                                            precision: 2,
                                            placeholder: '请输入',
                                            spanForm: 8
                                        }
                                    },
                                    {
                                        table: {
                                            title: '净值',
                                            dataIndex: 'leftValue',
                                            key: 'leftValue'
                                        },
                                        form: {
                                            type: 'number',
                                            min: 0,
                                            precision: 2,
                                            placeholder: '请输入',
                                            spanForm: 8
                                        }
                                    },
                                    {
                                        isInTable: false,
                                        form: {
                                            label: '调拨依据',
                                            field: 'reason',
                                            type: 'string',
                                            placeholder: '请输入',
                                            spanForm: 20,
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
                                    },
                                    {
                                        isInTable: false,
                                        form: {
                                            label: '注意事项',
                                            field: 'careinfo',
                                            type: 'string',
                                            placeholder: '请输入',
                                            spanForm: 8
                                        }
                                    },
                                    {
                                        isInTable: false,
                                        form: {
                                            label: '主管领导',
                                            field: 'adminLeader',
                                            type: 'string',
                                            placeholder: '请输入',
                                            spanForm: 8
                                        }
                                    },
                                    {
                                        isInTable: false,
                                        form: {
                                            label: '机械主管',
                                            field: 'equipAdmin',
                                            type: 'string',
                                            placeholder: '请输入',
                                            spanForm: 8
                                        }
                                    },
                                    {
                                        isInTable: false,
                                        form: {
                                            label: '财务科',
                                            field: 'finance',
                                            type: 'string',
                                            placeholder: '请输入',
                                            spanForm: 8
                                        }
                                    },
                                    {
                                        isInTable: false,
                                        form: {
                                            label: '制表人',
                                            field: 'lister',
                                            type: 'string',
                                            placeholder: '请输入',
                                            spanForm: 8
                                        }
                                    },
                                    {
                                        isInTable: false,
                                        form: {
                                            label: '备注',
                                            field: 'remark',
                                            type: 'textarea',
                                            placeholder: '请输入',
                                            spanForm: 20,
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
                                    }
                                ]
                            }
                        },
                        {
                            field: "table2",
                            name: "qnnTable",
                            title: "资产折旧",
                            disabled: (obj) => {
                                if (obj.clickCb.rowInfo.name === 'edit') {
                                    return true;
                                } else {
                                    return false;
                                }
                            },
                            content: {
                                tabs: [],
                                fetchConfig: {
                                    apiName: 'getZxEqEquipDepreciationItemListForTab',
                                    otherParams: (obj) => {
                                        let rowData = obj.clickCb.rowData;
                                        if (rowData) {
                                            return { equipID: obj.clickCb.rowData.id };
                                        }
                                    },
                                },
                                wrappedComponentRef: (me) => {
                                    this.tableZC = me;
                                },
                                drawerConfig: {
                                    width: "1000px"
                                },
                                antd: {
                                    rowKey: 'id',
                                    size: 'small'
                                },
                                paginationConfig: {
                                    position: "bottom"
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
                                },
                                isShowRowSelect: false,
                                formConfig: [
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
                                            title: '计提单位',
                                            dataIndex: 'orgID',
                                            key: 'orgID',
                                            filter: true,
                                            type: 'select'
                                        },
                                        form: {
                                            type: 'select',
                                            optionConfig: {
                                                label: 'departmentName',
                                                value: 'departmentId'
                                            },
                                            fetchConfig: {
                                                apiName: 'getSysCompanyProject'
                                            },
                                            allowClear: false,
                                            showSearch: true,
                                            placeholder: '请选择',
                                            spanForm: 8
                                        }
                                    },
                                    {
                                        table: {
                                            title: '计提日期',
                                            dataIndex: 'depreDate',
                                            key: 'depreDate',
                                            format: 'YYYY-MM-DD'
                                        },
                                        form: {
                                            type: 'date',
                                            placeholder: '请选择',
                                            spanForm: 8
                                        }
                                    },
                                    {
                                        table: {
                                            title: '计提月份',
                                            dataIndex: 'depreperiodDate',
                                            key: 'depreperiodDate',
                                            format: 'YYYY-MM'
                                        },
                                        form: {
                                            type: 'month',
                                            placeholder: '请选择',
                                            spanForm: 8
                                        }
                                    },
                                    {
                                        table: {
                                            title: '使用部门',
                                            dataIndex: 'useOrgID',
                                            key: 'useOrgID',
                                            type: 'select'
                                        },
                                        form: {
                                            type: 'select',
                                            optionConfig: {
                                                label: 'departmentName',
                                                value: 'departmentId'
                                            },
                                            fetchConfig: {
                                                apiName: 'getSysCompanyProject'
                                            },
                                            allowClear: false,
                                            showSearch: true,
                                            placeholder: '请选择',
                                            spanForm: 8
                                        }
                                    },
                                    {
                                        table: {
                                            title: '本月折旧',
                                            dataIndex: 'depreamout',
                                            key: 'depreamout'
                                        },
                                        form: {
                                            type: 'number',
                                            min: 0,
                                            precision: 2,
                                            placeholder: '请输入',
                                            spanForm: 8
                                        }
                                    },
                                    {
                                        table: {
                                            title: '原值',
                                            dataIndex: 'orginalValue',
                                            key: 'orginalValue',
                                            onClick: 'detail'
                                        },
                                        form: {
                                            type: 'number',
                                            min: 0,
                                            precision: 2,
                                            placeholder: '请输入',
                                            spanForm: 8
                                        }
                                    },
                                    {
                                        table: {
                                            title: '净值',
                                            dataIndex: 'leftValue',
                                            key: 'leftValue'
                                        },
                                        form: {
                                            type: 'number',
                                            min: 0,
                                            precision: 2,
                                            placeholder: '请输入',
                                            spanForm: 8
                                        }
                                    },
                                    {
                                        isInTable: false,
                                        form: {
                                            label: '备注',
                                            field: 'remark',
                                            type: 'textarea',
                                            placeholder: '请输入',
                                            spanForm: 20,
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
                                    }
                                ]
                            }
                        },
                        {
                            field: "table3",
                            name: "qnnTable",
                            title: "局内调拨",
                            disabled: (obj) => {
                                if (obj.clickCb.rowInfo.name === 'edit') {
                                    return true;
                                } else {
                                    return false;
                                }
                            },
                            content: {
                                tabs: [],
                                fetchConfig: {
                                    apiName: 'getZxEqMoveSupervisorItemListForTab',
                                    otherParams: (obj) => {
                                        let rowData = obj.clickCb.rowData;
                                        if (rowData) {
                                            return { equipID: obj.clickCb.rowData.id };
                                        }
                                    },
                                },
                                wrappedComponentRef: (me) => {
                                    this.tableJN = me;
                                },
                                drawerConfig: {
                                    width: "1000px"
                                },
                                antd: {
                                    rowKey: 'id',
                                    size: 'small'
                                },
                                paginationConfig: {
                                    position: "bottom"
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
                                },
                                isShowRowSelect: false,
                                formConfig: [
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
                                            title: '单据号',
                                            dataIndex: 'billNo',
                                            key: 'billNo',
                                            filter: true,
                                            onClick: 'detail'
                                        },
                                        form: {
                                            type: 'string',
                                            placeholder: '请输入',
                                            spanForm: 8
                                        }
                                    },
                                    {
                                        table: {
                                            title: '调出方',
                                            dataIndex: 'outOrgName',
                                            key: 'outOrgName'
                                        },
                                        form: {
                                            type: 'string',
                                            placeholder: '请输入',
                                            spanForm: 8
                                        }
                                    },
                                    {
                                        table: {
                                            title: '调入方',
                                            dataIndex: 'inOrgName',
                                            key: 'inOrgName'
                                        },
                                        form: {
                                            type: 'string',
                                            placeholder: '请输入',
                                            spanForm: 8
                                        }
                                    },
                                    {
                                        table: {
                                            title: '调拨日期',
                                            dataIndex: 'busDate',
                                            key: 'busDate',
                                            format: 'YYYY-MM-DD'
                                        },
                                        form: {
                                            type: 'date',
                                            placeholder: '请选择',
                                            spanForm: 8
                                        }
                                    },
                                    {
                                        table: {
                                            title: '签收日期',
                                            dataIndex: 'inDate',
                                            key: 'inDate',
                                            format: 'YYYY-MM-DD'
                                        },
                                        form: {
                                            type: 'date',
                                            placeholder: '请选择',
                                            spanForm: 8
                                        }
                                    },
                                ]
                            }
                        },
                        {
                            field: "table4",
                            name: "qnnTable",
                            title: "运转记录",
                            disabled: (obj) => {
                                if (obj.clickCb.rowInfo.name === 'edit') {
                                    return true;
                                } else {
                                    return false;
                                }
                            },
                            content: {
                                tabs: [],
                                fetchConfig: {
                                    apiName: 'getZxEqEWorkList',
                                    otherParams: (obj) => {
                                        let rowData = obj.clickCb.rowData;
                                        if (rowData) {
                                            return { equipID: obj.clickCb.rowData.id };
                                        }
                                    },
                                },
                                wrappedComponentRef: (me) => {
                                    this.tableJN = me;
                                },
                                drawerConfig: {
                                    width: "1000px"
                                },
                                antd: {
                                    rowKey: 'id',
                                    size: 'small'
                                },
                                paginationConfig: {
                                    position: "bottom"
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
                                },
                                isShowRowSelect: false,
                                formConfig: [
                                    {
                                        isInTable: false,
                                        form: {
                                            field: 'id',
                                            type: 'string',
                                            hide: true,
                                        }
                                    },
                                    {
                                        isInTable: false,
                                        form: {
                                            field: 'orgID',
                                            type: 'string',
                                            hide: true,
                                        }
                                    },
                                    {
                                        table: {
                                            title: '使用单位',
                                            dataIndex: 'orgName',
                                            key: 'orgName',
                                            onClick: "detail",
                                            filter: true
                                        },
                                        form: {
                                            type: 'string',
                                            placeholder: '请输入',
                                            spanForm: 8
                                        }
                                    },
                                    {
                                        table: {
                                            title: '机械编号',
                                            dataIndex: 'equipNo',
                                            key: 'equipNo'
                                        },
                                        form: {
                                            type: 'string',
                                            placeholder: '请输入',
                                            spanForm: 8
                                        }
                                    },
                                    {
                                        table: {
                                            title: '机械名称',
                                            dataIndex: 'equipName',
                                            key: 'equipName'
                                        },
                                        form: {
                                            type: 'string',
                                            placeholder: '请输入',
                                            spanForm: 8
                                        }
                                    },
                                    {
                                        table: {
                                            title: '技术规格',
                                            dataIndex: 'spec',
                                            key: 'spec'
                                        },
                                        form: {
                                            type: 'string',
                                            placeholder: '请输入',
                                            spanForm: 8
                                        }
                                    },
                                    {
                                        table: {
                                            title: '填写日期',
                                            dataIndex: 'bizDate',
                                            key: 'bizDate',
                                            format: 'YYYY-MM-DD'
                                        },
                                        form: {
                                            type: 'date',
                                            placeholder: '请选择',
                                            spanForm: 8
                                        }
                                    },
                                    {
                                        table: {
                                            title: '状态',
                                            dataIndex: 'status',
                                            key: 'status',
                                            render: (data) => {
                                                if (!data) {
                                                    return '运行中';
                                                } else {
                                                    return data;
                                                }
                                            }
                                        },
                                        form: {
                                            hide: true,
                                            type: 'string',
                                            placeholder: '请输入',
                                            spanForm: 8
                                        }
                                    },
                                    {
                                        table: {
                                            title: '备注',
                                            dataIndex: 'remark',
                                            key: 'remark'
                                        },
                                        form: {
                                            type: 'textarea',
                                            placeholder: '请输入',
                                            spanForm: 21,
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
                                    },
                                    {
                                        isInTable: false,
                                        form: {
                                            label: "附件",
                                            field: "fileList",
                                            type: "files",
                                            initialValue: [],
                                            fetchConfig: {
                                                apiName: "upload"
                                            },
                                            spanForm: 21,
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
                                    }
                                ]
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
                            isInTable: false,
                            form: {
                                label: 'ABCD分类',
                                field: 'abcType',
                                addDisabled: true,
                                editDisabled: true,
                                type: 'select',
                                optionConfig: {
                                    label: 'globalCode',
                                    value: 'id',
                                },
                                fetchConfig: {
                                    apiName: "getZxEqGlobalCodeList",
                                    otherParams: {
                                        categoryID: "category100203",
                                        startFlag: '1'
                                    }
                                },
                                allowClear: false,
                                showSearch: true,
                                placeholder: '请选择',
                                spanForm: 8
                            }
                        },
                        {
                            isInTable: false,
                            form: {
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
                                showSearch: true,
                                required: true,
                                placeholder: '请选择',
                                spanForm: 8
                            }
                        },
                        {
                            isInTable: false,
                            form: {
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
                                showSearch: true,
                                required: true,
                                placeholder: '请选择',
                                spanForm: 8
                            }
                        },
                        {
                            table: {
                                title: '机械管理编号',
                                dataIndex: 'equipNo',
                                key: 'equipNo',
                                width: 200,
                                fixed: 'left',
                                filter: true
                            },
                            form: {
                                type: 'string',
                                addDisabled: true,
                                editDisabled: true,
                                placeholder: '请输入',
                                spanForm: 8
                            }
                        },
                        {
                            table: {
                                title: '财务固定资产编号',
                                dataIndex: 'financeNo',
                                key: 'financeNo',
                                onClick: 'detail',
                                filter: true,
                                width: 160
                            },
                            form: {
                                type: 'string',
                                placeholder: '请输入',
                                spanForm: 8
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: '购置日期',
                                field: 'purDate',
                                type: 'date',
                                placeholder: '请选择',
                                spanForm: 8
                            }
                        },
                        {
                            table: {
                                title: '设备名称',
                                dataIndex: 'equipName',
                                key: 'equipName',
                                width: 150,
                                filter: true
                            },
                            form: {
                                type: 'string',
                                required: true,
                                placeholder: '请输入',
                                spanForm: 8
                            }
                        },
                        {
                            table: {
                                title: '国家厂家',
                                dataIndex: 'factory',
                                key: 'factory',
                                width: 150,
                            },
                            form: {
                                type: 'string',
                                placeholder: '请输入',
                                spanForm: 8
                            }
                        },
                        {
                            table: {
                                title: '型号',
                                dataIndex: 'model',
                                key: 'model',
                                width: 150,
                            },
                            form: {
                                type: 'string',
                                required: true,
                                placeholder: '请输入',
                                spanForm: 8
                            }
                        },
                        {
                            table: {
                                title: '规格',
                                dataIndex: 'spec',
                                key: 'spec',
                                width: 150,
                            },
                            form: {
                                type: 'string',
                                required: true,
                                placeholder: '请输入',
                                spanForm: 8
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: '设备分类',
                                field: 'resCatalogID',
                                type: 'select',
                                optionConfig: {
                                    label: 'catName',
                                    value: 'id',
                                    children: "childrenList"
                                },
                                fetchConfig: {
                                    apiName: "getZxEqResCategoryList",
                                    otherParams: {
                                        parentID: '0003',
                                        isGroup: '1'
                                    }
                                },
                                allowClear: false,
                                showSearch: true,
                                required: true,
                                placeholder: '请输入',
                                spanForm: 8
                            }
                        },
                        {
                            table: {
                                title: '所属单位',
                                dataIndex: 'ownerOrgName',
                                key: 'ownerOrgName',
                                width: 150,
                                filter: true
                                // type:"select"
                            },
                            form: {
                                type: 'string',
                                required: true,
                                // optionConfig: {
                                //     label: 'departmentName',
                                //     value: 'departmentId'
                                // },
                                // fetchConfig: {
                                //     apiName: 'getSysCompanyProject',
                                //     otherParams:{
                                //         departmentId:departmentId
                                //     }
                                // },
                                placeholder: '请输入',
                                spanForm: 8
                            }
                        },
                        {
                            table: {
                                title: '使用单位',
                                dataIndex: 'useOrgName',
                                key: 'useOrgName',
                                width: 150,
                                filter: true
                            },
                            form: {
                                type: 'string',
                                // optionConfig: {
                                //     label: 'departmentName',
                                //     value: 'departmentId'
                                // },
                                // fetchConfig: {
                                //     apiName: 'getSysCompanyProject',
                                //     otherParams:{
                                //         departmentId:departmentId
                                //     }
                                // },
                                addDisabled: true,
                                editDisabled: true,
                                placeholder: '请输入',
                                spanForm: 8
                            }
                        },
                        {
                            table: {
                                title: '所在地点',
                                dataIndex: 'usePlace',
                                key: 'usePlace',
                                width: 150,
                            },
                            form: {
                                type: 'string',
                                // optionConfig: {
                                //     label: 'itemName',
                                //     value: 'itemName',
                                // },
                                // fetchConfig: {
                                //     apiName: "getBaseCodeSelect",
                                //     otherParams: {
                                //         itemId: 'suoShuQuYu'
                                //     }
                                // },
                                allowClear: false,
                                showSearch: true,
                                placeholder: '请输入',
                                spanForm: 8
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: '总功率(KW)',
                                field: 'power',
                                type: 'number',
                                min: 0,
                                required: true,
                                placeholder: '请输入',
                                spanForm: 8
                            }
                        },
                        {
                            isInTable: false,
                            form: {
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
                                showSearch: true,
                                required: true,
                                placeholder: '请选择',
                                spanForm: 8
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: '副机系列号',
                                field: 'viceserial',
                                type: 'string',
                                placeholder: '请输入',
                                spanForm: 8
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: '底盘厂家',
                                field: 'downfactory',
                                type: 'string',
                                placeholder: '请输入',
                                spanForm: 8
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: '副机出厂日期',
                                field: 'viceoutfactory',
                                type: 'date',
                                placeholder: '请选择',
                                spanForm: 8
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: '副机型号',
                                field: 'vicespec',
                                type: 'string',
                                placeholder: '请输入',
                                spanForm: 8
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: '副机厂牌',
                                field: 'viceFactory',
                                type: 'string',
                                placeholder: '请输入',
                                spanForm: 8
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: '主机出厂日期',
                                field: 'mainoutfactory',
                                type: 'date',
                                placeholder: '请选择',
                                spanForm: 8
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: '主机系列号',
                                field: 'mainserial',
                                type: 'string',
                                placeholder: '请输入',
                                spanForm: 8
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: '主机型号',
                                field: 'mainspec',
                                type: 'string',
                                placeholder: '请输入',
                                spanForm: 8
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: '主机厂牌',
                                field: 'mainFactory',
                                type: 'string',
                                placeholder: '请输入',
                                spanForm: 8
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: '底盘型式',
                                field: 'downspec',
                                type: 'string',
                                placeholder: '请输入',
                                spanForm: 8
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: '底盘系列号',
                                field: 'downserial',
                                type: 'string',
                                placeholder: '请输入',
                                spanForm: 8
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: '底盘出厂日期',
                                field: 'downoutfactory',
                                type: 'date',
                                placeholder: '请选择',
                                spanForm: 8
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: '外型尺寸长宽高(mm)',
                                field: 'heightlong',
                                type: 'string',
                                placeholder: '请输入',
                                spanForm: 8
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: '自重(t)',
                                field: 'weight',
                                type: 'string',
                                placeholder: '请输入',
                                spanForm: 8
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: '原值',
                                field: 'orginalValue',
                                type: 'number',
                                min: 0,
                                placeholder: '请输入',
                                spanForm: 8
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: '净值',
                                field: 'leftValue',
                                type: 'number',
                                min: 0,
                                placeholder: '请输入',
                                spanForm: 8
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: '验收时间',
                                field: 'acceptanceDate',
                                type: 'date',
                                placeholder: '请选择',
                                spanForm: 8
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: '牌照号码',
                                field: 'passNo',
                                type: 'string',
                                placeholder: '请输入',
                                spanForm: 8
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: '出厂日期',
                                field: 'outFactoryDate',
                                type: 'date',
                                required: true,
                                placeholder: '请选择',
                                spanForm: 8
                            }
                        },
                        {
                            isInTable: false,
                            form: {
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
                                spanForm: 8
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: '已提折旧',
                                field: 'actualDepreAmt',
                                type: 'number',
                                min: 0,
                                placeholder: '请输入',
                                spanForm: 8
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: '主机功率(KW)',
                                field: 'mainpower',
                                type: 'number',
                                min: 0,
                                placeholder: '请输入',
                                spanForm: 8
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: '付机功率(KW)',
                                field: 'vicepower',
                                type: 'number',
                                min: 0,
                                placeholder: '请输入',
                                spanForm: 8
                            }
                        },
                        {
                            table: {
                                title: '入账日期',
                                dataIndex: 'regdate',
                                key: 'regdate',
                                width: 150,
                                format: 'YYYY-MM-DD'
                            },
                            form: {
                                type: 'date',
                                placeholder: '请选择',
                                spanForm: 8
                            }
                        },
                        {
                            table: {
                                title: '计划批文号',
                                dataIndex: 'planNo',
                                key: 'planNo',
                                width: 150,
                            },
                            form: {
                                type: 'string',
                                placeholder: '请输入',
                                spanForm: 8
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
                            },
                            form: {
                                type: 'select',
                                optionConfig: {
                                    label: 'label',
                                    value: 'value'
                                },
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
                                placeholder: '请选择',
                                spanForm: 8
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: '备注',
                                field: 'remark',
                                type: 'textarea',
                                placeholder: '请输入',
                                spanForm: 24,
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
                        },
                        {
                            isInTable: false,
                            form: {
                                label: "附件",
                                field: "fileList",
                                type: "files",
                                initialValue: [],
                                fetchConfig: {
                                    apiName: "upload"
                                },
                                spanForm: 24,
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
                        }
                    ]}
                />
            </div>
        );
    }
}

export default index;