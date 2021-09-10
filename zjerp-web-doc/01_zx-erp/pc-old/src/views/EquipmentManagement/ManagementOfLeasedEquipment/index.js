import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import QnnForm from '../../modules/qnn-table/qnn-form';
import s from "./style.less";
import { message as Msg, Modal, Tabs, Button, Avatar } from "antd";
const { confirm, info } = Modal;
const { TabPane } = Tabs;
const config1 = {
    antd: {
        rowKey: function (row) {
            return row.id
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
            xs: { span: 10 },
            sm: { span: 10 }
        },
        wrapperCol: {
            xs: { span: 14 },
            sm: { span: 14 }
        }
    }
};
const config2 = {
    antd: {
        rowKey: function (row) {
            return row.id
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
            activeKey: '1',
            visible: false
        }
    }
    tabsCallback = (activeKey) => {
        this.setState({
            activeKey
        })
    }
    render() {
        const { departmentId, companyId, companyName } = this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany;
        const { activeKey, visible } = this.state;
        return (
            <div className={s.root}>
                <Tabs activeKey={activeKey} onChange={this.tabsCallback}>
                    <TabPane tab="外部租用机械设备入场验收" key="1">
                        <QnnTable
                            {...this.props}
                            fetch={this.props.myFetch}
                            upload={this.props.myUpload}
                            headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                            wrappedComponentRef={(me) => {
                                this.table1 = me;
                            }}
                            {...config1}
                            fetchConfig={{
                                apiName: 'getZxEqOuterEquipList',
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
                                    formBtns: [
                                        {
                                            name: 'cancel', //关闭右边抽屉
                                            type: 'dashed',//类型  默认 primary
                                            label: '取消',
                                        },
                                        {
                                            name: 'submit',//内置add del
                                            type: 'primary',//类型  默认 primary
                                            label: '保存',//提交数据并且关闭右边抽屉 
                                            fetchConfig: {//ajax配置
                                                apiName: 'addZxEqOuterEquip',
                                            }
                                        }
                                    ]
                                },
                                {
                                    name: 'edit',//内置add del
                                    icon: 'edit',//icon
                                    type: 'primary',//类型  默认 primary  [primary dashed danger]
                                    label: '修改',
                                    formBtns: [
                                        {
                                            name: 'cancel', //关闭右边抽屉
                                            type: 'dashed',//类型  默认 primary
                                            label: '取消',
                                        },
                                        {
                                            name: 'submit',//内置add del
                                            type: 'primary',//类型  默认 primary
                                            label: '保存',//提交数据并且关闭右边抽屉 
                                            fetchConfig: {//ajax配置
                                                apiName: 'updateZxEqOuterEquip',
                                            },
                                            onClick: (obj) => {
                                                obj.btnCallbackFn.clearSelectedRows();
                                            }
                                        }
                                    ]
                                },
                                {
                                    name: 'QRCode',//内置add del
                                    type: 'primary',//类型  默认 primary  [primary dashed danger]
                                    label: '二维码',
                                    disabled: (obj) => {
                                        if (obj.btnCallbackFn.getSelectedRows().length === 1) {
                                            return false;
                                        } else {
                                            return true;
                                        }
                                    },
                                    onClick: (obj) => {
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
                                    }
                                },
                                {
                                    name: 'QRCodeDownload',//内置add del
                                    type: 'primary',//类型  默认 primary  [primary dashed danger]
                                    label: '二维码下载',
                                    disabled: (obj) => {
                                        if (obj.btnCallbackFn.getSelectedRows().length === 1) {
                                            return false;
                                        } else {
                                            return true;
                                        }
                                    },
                                    onClick: (obj) => {
                                        confirm({
                                            content: '确定下载二维码吗?',
                                            onOk: () => {
                                                let { domain } = obj.props.myPublic;
                                                let qrcodeDownUrl = obj.selectedRows[0].qrcodeDownUrl;
                                                window.location.href = domain + qrcodeDownUrl;
                                            }
                                        });
                                    }
                                },
                                {
                                    name: 'del',//内置add del
                                    icon: 'delete',//icon
                                    type: 'danger',//类型  默认 primary  [primary dashed danger]
                                    label: '删除',
                                    fetchConfig: {//ajax配置
                                        apiName: 'batchDeleteUpdateZxEqOuterEquip',
                                    },
                                }
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
                                        field: 'comID',
                                        type: 'string',
                                        hide: true,
                                    }
                                },
                                {
                                    isInTable: false,
                                    form: {
                                        field: 'orgName',
                                        type: 'string',
                                        hide: true,
                                    }
                                },
                                {
                                    isInTable: false,
                                    form: {
                                        field: 'secondID',
                                        type: 'string',
                                        hide: true,
                                    }
                                },
                                {
                                    isInTable: false,
                                    form: {
                                        field: 'secondName',
                                        type: 'string',
                                        hide: true,
                                    }
                                },
                                {
                                    isInTable: false,
                                    form: {
                                        field: 'equipName',
                                        type: 'string',
                                        hide: true,
                                    }
                                },
                                {
                                    isInTable: false,
                                    form: {
                                        field: 'parentID',
                                        type: 'string',
                                        hide: true,
                                    }
                                },
                                {
                                    isInTable: false,
                                    form: {
                                        field: 'parentName',
                                        type: 'string',
                                        hide: true,
                                    }
                                },
                                {
                                    isInTable: false,
                                    form: {
                                        field: 'placeName',
                                        type: 'string',
                                        hide: true,
                                    }
                                },
                                {
                                    table: {
                                        title: '公司名称',
                                        dataIndex: 'comName',
                                        key: 'comName',
                                        width: 150,
                                        fixed: 'left',
                                        onClick: 'detail'
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
                                        title: '单位名称',
                                        dataIndex: 'orgID',
                                        key: 'orgID',
                                        width: 150,
                                        type: 'select'
                                    },
                                    form: {
                                        type: 'select',
                                        optionConfig: {
                                            label: 'departmentName',
                                            value: 'departmentId',
                                            linkageFields: {
                                                orgName: 'departmentName',
                                                comID: 'companyId',
                                                comName: 'companyName'
                                            }
                                        },
                                        fetchConfig: {
                                            apiName: 'getSysCompanyProject',
                                            otherParams: {
                                                departmentId: departmentId
                                            }
                                        },
                                        allowClear: false,
                                        showSearch: true,
                                        required: true,
                                        placeholder: '请选择',
                                        spanForm: 8
                                    }
                                },
                                {
                                    table: {
                                        title: '合同编号',
                                        dataIndex: 'contrItem',
                                        key: 'contrItem',
                                        width: 150
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
                                        title: '租赁合同',
                                        dataIndex: 'contrID',
                                        key: 'contrID',
                                        width: 150,
                                        type:'select'
                                    },
                                    form: {
                                        type: 'select',
                                        optionConfig: {
                                            label: 'contractName',
                                            value: 'contractID',
                                            children: "zxCtEqContrItemList",
                                            linkageFields: {
                                                contrItem: 'contractNo',
                                                supplierID: "secondName",
                                                secondID: 'secondID',
                                                secondName: 'secondName'
                                            }
                                        },
                                        parent: 'orgID',
                                        fetchConfig: {
                                            apiName: 'getZxCtEqContractListForOuterEquip',
                                            otherParams: {
                                                contractStatus: '1'
                                            },
                                            params:{
                                                firstId:'orgID'
                                            }
                                        },
                                        condition: [
                                            {
                                                regex: {
                                                    orgID: ['=', /(''|null|undefined)/ig]
                                                },
                                                action: 'disabled'
                                            }
                                        ],
                                        allowClear: false,
                                        showSearch: true,
                                        placeholder: '请选择',
                                        spanForm: 8
                                    }
                                },
                                {
                                    table: {
                                        title: '设备名称',
                                        dataIndex: 'equipName',
                                        key: 'equipName',
                                        width: 100
                                    },
                                    form: {
                                        field:'equipID',
                                        type: 'select',
                                        optionConfig: {
                                            label: 'catName',
                                            value: 'zxEqResCategoryId',
                                            linkageFields: {
                                                equipName:'catName',
                                                equipNo: 'catCode',
                                                spec: 'spec',
                                                model: 'unit',
                                                parentID:'parentID',
                                                parentName:'catParentName'
                                            }
                                        },
                                        parent: "contrID",
                                        condition: [
                                            {
                                                regex: {
                                                    contrID: ['=', /(''|null|undefined)/ig]
                                                },
                                                action: 'disabled'
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
                                        title: '编号',
                                        dataIndex: 'equipNo',
                                        key: 'equipNo',
                                        width: 100
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
                                        dataIndex: 'spec',
                                        key: 'spec',
                                        width: 100
                                    },
                                    form: {
                                        type: 'string',
                                        placeholder: '请输入',
                                        spanForm: 8
                                    }
                                },
                                {
                                    table: {
                                        title: '规格',
                                        dataIndex: 'model',
                                        key: 'model',
                                        width: 100
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
                                        label: '功率(KW)',
                                        field: 'power',
                                        type: 'string',
                                        // min: 0,
                                        placeholder: '请输入',
                                        spanForm: 8
                                    }
                                },
                                {
                                    isInTable: false,
                                    form: {
                                        label: '生产厂家',
                                        field: 'outfactory',
                                        type: 'string',
                                        placeholder: '请输入',
                                        spanForm: 8
                                    }
                                },
                                {
                                    isInTable: false,
                                    form: {
                                        label: '出厂日期',
                                        field: 'outfactoryDate',
                                        type: 'date',
                                        placeholder: '请选择',
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
                                        precision: 2,
                                        placeholder: '请输入',
                                        spanForm: 8
                                    }
                                },
                                {
                                    table: {
                                        title: '使用地点',
                                        dataIndex: 'placeName',
                                        key: 'placeName',
                                        width: 100
                                    },
                                    form: {
                                        field:'place',
                                        type: 'select',
                                        optionConfig: {
                                            label: 'itemName', //默认 label
                                            value: 'itemId',
                                            linkageFields:{
                                                placeName:'itemName'
                                            }
                                        },
                                        fetchConfig: {
                                            apiName: 'getBaseCodeSelect',
                                            otherParams: {
                                                itemId: 'xingzhengquhuadaima'
                                            }
                                        },
                                        allowClear: false,
                                        showSearch: true,
                                        placeholder: '请选择',
                                        spanForm: 8
                                    }
                                },
                                {
                                    table: {
                                        title: '租赁期限(月)',
                                        dataIndex: 'leaseLimit',
                                        key: 'leaseLimit',
                                        width: 150
                                    },
                                    form: {
                                        type: 'string',
                                        // min: 0,
                                        placeholder: '请输入',
                                        spanForm: 8
                                    }
                                },
                                {
                                    table: {
                                        title: '租赁价格(元/月)',
                                        dataIndex: 'leaseprice',
                                        key: 'leaseprice',
                                        width: 150
                                    },
                                    form: {
                                        type: 'string',
                                        // min: 0,
                                        placeholder: '请输入',
                                        spanForm: 8
                                    }
                                },
                                {
                                    table: {
                                        title: '租赁公司',
                                        dataIndex: 'supplierID',
                                        key: 'supplierID',
                                        width: 100
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
                                        title: '租赁公司负责人',
                                        dataIndex: 'supplierMaster',
                                        key: 'supplierMaster',
                                        width: 150
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
                                        label: '机主姓名',
                                        field: 'operator',
                                        type: 'string',
                                        placeholder: '请输入',
                                        spanForm: 8
                                    }
                                },
                                {
                                    isInTable: false,
                                    form: {
                                        label: '入场时间',
                                        field: 'inDate',
                                        type: 'date',
                                        required: true,
                                        placeholder: '请选择',
                                        spanForm: 8
                                    }
                                },
                                {
                                    isInTable: false,
                                    form: {
                                        label: '退场时间',
                                        field: 'outDate',
                                        type: 'date',
                                        required: true,
                                        placeholder: '请选择',
                                        spanForm: 8
                                    }
                                },
                                {
                                    isInTable: false,
                                    form: {
                                        label: '牌照号',
                                        field: 'passNo',
                                        type: 'string',
                                        placeholder: '请输入',
                                        spanForm: 8
                                    }
                                },
                                {
                                    isInTable: false,
                                    form: {
                                        label: '开竣工日期',
                                        field: 'startEndDate',
                                        type: 'date',
                                        placeholder: '请选择',
                                        spanForm: 8
                                    }
                                },
                                {
                                    table: {
                                        title: '验收总体评价',
                                        dataIndex: 'acceptance',
                                        key: 'acceptance',
                                        width: 150,
                                        type: 'select'
                                    },
                                    form: {
                                        type: 'select',
                                        optionConfig: {
                                            label: 'label',
                                            value: 'value',
                                        },
                                        optionData: [
                                            {
                                                label: "不合格",
                                                value: "0"
                                            },
                                            {
                                                label: "合格",
                                                value: "1"
                                            }
                                        ],
                                        allowClear: false,
                                        showSearch: true,
                                        placeholder: '请选择',
                                        spanForm: 8
                                    }
                                },
                                {
                                    table: {
                                        title: '是否特种设备',
                                        dataIndex: 'isSepcial',
                                        key: 'isSepcial',
                                        width: 150,
                                        type: 'select'
                                    },
                                    form: {
                                        type: 'select',
                                        optionConfig: {
                                            label: 'label',
                                            value: 'value',
                                        },
                                        optionData: [
                                            {
                                                label: "否",
                                                value: "0"
                                            },
                                            {
                                                label: "是",
                                                value: "1"
                                            }
                                        ],
                                        allowClear: false,
                                        showSearch: true,
                                        placeholder: '请选择',
                                        spanForm: 8
                                    }
                                },
                                {
                                    table: {
                                        title: '特种设备检验报告',
                                        dataIndex: 'inspReport',
                                        key: 'inspReport',
                                        width: 150,
                                        type: 'select'
                                    },
                                    form: {
                                        type: 'select',
                                        optionConfig: {
                                            label: 'label',
                                            value: 'value',
                                        },
                                        optionData: [
                                            {
                                                label: "无",
                                                value: "0"
                                            },
                                            {
                                                label: "有",
                                                value: "1"
                                            }
                                        ],
                                        allowClear: false,
                                        showSearch: true,
                                        placeholder: '请选择',
                                        spanForm: 8
                                    }
                                },
                                {
                                    table: {
                                        title: '特种设备使用登记证',
                                        dataIndex: 'inspCert',
                                        key: 'inspCert',
                                        width: 150,
                                        type: 'select'
                                    },
                                    form: {
                                        type: 'select',
                                        optionConfig: {
                                            label: 'label',
                                            value: 'value',
                                        },
                                        optionData: [
                                            {
                                                label: "无",
                                                value: "0"
                                            },
                                            {
                                                label: "有",
                                                value: "1"
                                            }
                                        ],
                                        allowClear: false,
                                        showSearch: true,
                                        placeholder: '请选择',
                                        spanForm: 8
                                    }
                                },
                                {
                                    table: {
                                        title: '操作人员证',
                                        dataIndex: 'opCert',
                                        key: 'opCert',
                                        width: 100,
                                        type: 'select'
                                    },
                                    form: {
                                        type: 'select',
                                        optionConfig: {
                                            label: 'label',
                                            value: 'value',
                                        },
                                        optionData: [
                                            {
                                                label: "无",
                                                value: "0"
                                            },
                                            {
                                                label: "有",
                                                value: "1"
                                            }
                                        ],
                                        allowClear: false,
                                        showSearch: true,
                                        placeholder: '请选择',
                                        spanForm: 8
                                    }
                                },
                                {
                                    table: {
                                        title: '是否退场',
                                        dataIndex: 'isOut',
                                        key: 'isOut',
                                        width: 100,
                                        type: 'select'
                                    },
                                    form: {
                                        type: 'select',
                                        optionConfig: {
                                            label: 'label',
                                            value: 'value',
                                        },
                                        optionData: [
                                            {
                                                label: "否",
                                                value: "0"
                                            },
                                            {
                                                label: "是",
                                                value: "1"
                                            }
                                        ],
                                        allowClear: false,
                                        showSearch: true,
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
                                        label: '附件',
                                        field: 'fileList',
                                        type: 'files',
                                        initialValue: [],
                                        fetchConfig: {
                                            apiName: "upload"
                                        },
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
                            ]}
                        />
                    </TabPane>
                    <TabPane tab="外租机械设备运转记录" key="2">
                        <QnnTable
                            {...this.props}
                            fetch={this.props.myFetch}
                            upload={this.props.myUpload}
                            headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                            wrappedComponentRef={(me) => {
                                this.table2 = me;
                            }}
                            {...config2}
                            fetchConfig={{
                                apiName: 'getZxEqRentOutEqRecordList',
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
                                    formBtns: [
                                        {
                                            name: 'cancel', //关闭右边抽屉
                                            type: 'dashed',//类型  默认 primary
                                            label: '取消',
                                        },
                                        {
                                            name: 'submit',//内置add del
                                            type: 'primary',//类型  默认 primary
                                            label: '保存',//提交数据并且关闭右边抽屉 
                                            fetchConfig: {//ajax配置
                                                apiName: 'addZxEqRentOutEqRecord',
                                            }
                                        }
                                    ]
                                },
                                {
                                    name: 'edit',//内置add del
                                    icon: 'edit',//icon
                                    type: 'primary',//类型  默认 primary  [primary dashed danger]
                                    label: '修改',
                                    disabled: (obj) => {
                                        let data = obj.btnCallbackFn.getSelectedRows();
                                        if (data.length === 1 && (data[0].status === '' || data[0].status === '0')) {
                                            return false;
                                        } else {
                                            return true;
                                        }
                                    },
                                    formBtns: [
                                        {
                                            name: 'cancel', //关闭右边抽屉
                                            type: 'dashed',//类型  默认 primary
                                            label: '取消',
                                        },
                                        {
                                            name: 'submit',//内置add del
                                            type: 'primary',//类型  默认 primary
                                            label: '保存',//提交数据并且关闭右边抽屉 
                                            fetchConfig: {//ajax配置
                                                apiName: 'updateZxEqRentOutEqRecord',
                                            },
                                            onClick: (obj) => {
                                                obj.btnCallbackFn.clearSelectedRows();
                                            }
                                        }
                                    ]
                                },
                                {
                                    name: 'audit',//内置add del
                                    type: 'primary',//类型  默认 primary  [primary dashed danger]
                                    label: '审核',
                                    disabled: (obj) => {
                                        let data = obj.btnCallbackFn.getSelectedRows();
                                        if (data.length === 1 && (data[0].status === '' || data[0].status === '0')) {
                                            return false;
                                        } else {
                                            return true;
                                        }
                                    },
                                    onClick: (obj) => {
                                        confirm({
                                            content: '确定审核该条数据吗?',
                                            onOk: () => {
                                                obj.selectedRows[0].status = '1';
                                                this.props.myFetch('auditZxEqRentOutEqRecord', ...obj.selectedRows).then(
                                                    ({ data, success, message }) => {
                                                        if (success) {
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
                                    name: 'auditee',//内置add del
                                    type: 'primary',//类型  默认 primary  [primary dashed danger]
                                    label: '反审核',
                                    disabled: (obj) => {
                                        let data = obj.btnCallbackFn.getSelectedRows();
                                        if (data.length === 1 && data[0].status === '1') {
                                            return false;
                                        } else {
                                            return true;
                                        }
                                    },
                                    onClick: (obj) => {
                                        confirm({
                                            content: '确定反审核该条数据吗?',
                                            onOk: () => {
                                                obj.selectedRows[0].status = '0';
                                                this.props.myFetch('auditZxEqRentOutEqRecord', ...obj.selectedRows).then(
                                                    ({ data, success, message }) => {
                                                        if (success) {
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
                                // {
                                //     name: 'import',//内置add del
                                //     type: 'primary',//类型  默认 primary  [primary dashed danger]
                                //     label: '导入',
                                //     onClick: () => {
                                //         this.setState({
                                //             visible: true
                                //         })
                                //     }
                                // },
                                {
                                    name: 'del',//内置add del
                                    icon: 'delete',//icon
                                    type: 'danger',//类型  默认 primary  [primary dashed danger]
                                    label: '删除',
                                    disabled: (obj) => {
                                        let data = obj.btnCallbackFn.getSelectedRows();
                                        if (!data.filter(item => item.status === '1').length) {
                                            return false;
                                        } else {
                                            return true;
                                        }
                                    },
                                    fetchConfig: {//ajax配置
                                        apiName: 'batchDeleteUpdateZxEqRentOutEqRecord',
                                    },
                                }
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
                                        field: 'orgName',
                                        type: 'string',
                                        hide: true,
                                    }
                                },
                                {
                                    isInTable: false,
                                    form: {
                                        field: 'rentOrgName',
                                        type: 'string',
                                        hide: true,
                                    }
                                },
                                {
                                    isInTable: false,
                                    form: {
                                        field: 'companyId',
                                        type: 'string',
                                        hide: true,
                                    }
                                },
                                {
                                    table: {
                                        title: '公司名称',
                                        dataIndex: 'companyName',
                                        key: 'companyName',
                                        onClick: 'detail'
                                    },
                                    form: {
                                        type: 'string',
                                        addDisabled: true,
                                        editDisabled: true,
                                        spanForm: 8
                                    }
                                },
                                {
                                    table: {
                                        title: '单据编号',
                                        dataIndex: 'billNo',
                                        key: 'billNo',
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
                                        title: '使用单位',
                                        dataIndex: 'orgName',
                                        key: 'orgName'
                                    },
                                    form: {
                                        field:'orgID',
                                        type: 'select',
                                        optionConfig: {
                                            label: 'departmentName',
                                            value: 'departmentId',
                                            linkageFields: {
                                                orgName: 'departmentName',
                                                companyId: 'companyId',
                                                companyName: 'companyName'
                                            }
                                        },
                                        fetchConfig: {
                                            apiName: 'getSysCompanyProject',
                                            otherParams: {
                                                departmentId: departmentId
                                            }
                                        },
                                        onChange: (val, obj) => {
                                            obj.form.setFieldsValue({ outerEquipID: null, rentOrgID: null });
                                        },
                                        allowClear: false,
                                        showSearch: true,
                                        required: true,
                                        placeholder: '请选择',
                                        spanForm: 8
                                    }
                                },
                                {
                                    table: {
                                        title: '出租单位',
                                        dataIndex: 'rentOrgName',
                                        key: 'rentOrgName'
                                    },
                                    form: {
                                        field:'rentOrgID',
                                        type: 'select',
                                        optionConfig: {
                                            label: 'secondName',
                                            value: 'secondID',
                                            linkageFields: {
                                                rentOrgName: 'secondName',
                                            }
                                        },
                                        parent: 'orgID',
                                        fetchConfig: {
                                            apiName: 'getZxEqOuterEquipListForRecord',
                                            params:{
                                                orgID:'orgID'
                                            }
                                        },
                                        onChange: (val, obj) => {
                                            obj.form.setFieldsValue({ outerEquipID: null });
                                        },
                                        condition: [
                                            {
                                                regex: {
                                                    orgID: ['=', /(''|null|undefined)/ig]
                                                },
                                                action: 'disabled'
                                            }
                                        ],
                                        allowClear: false,
                                        showSearch: true,
                                        placeholder: '请选择',
                                        spanForm: 8
                                    }
                                },
                                {
                                    table: {
                                        title: '机械名称',
                                        dataIndex: 'outerEquipID',
                                        key: 'outerEquipID',
                                        type: 'select'
                                    },
                                    form: {
                                        type: 'select',
                                        optionConfig: {
                                            label: 'equipName',
                                            value: 'id'
                                        },
                                        parent: 'rentOrgID',
                                        fetchConfig: {
                                            apiName: 'getZxEqOuterEquipListForRecord',
                                            otherParams:(obj) => {
                                                let orgID = obj?.form?.getFieldValue?.('orgID');
                                                let rentOrgName = obj?.form?.getFieldValue?.('rentOrgName');
                                                return {
                                                    orgID:orgID,
                                                    supplierID:rentOrgName
                                                }
                                            }
                                        },
                                        condition: [
                                            {
                                                regex: {
                                                    rentOrgID: ['=', /(''|null|undefined)/ig]
                                                },
                                                action: 'disabled'
                                            }
                                        ],
                                        allowClear: false,
                                        showSearch: true,
                                        placeholder: '请选择',
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
                                        required: true,
                                        placeholder: '请输入',
                                        spanForm: 8
                                    }
                                },
                                {
                                    table: {
                                        title: '填写日期',
                                        dataIndex: 'busDate',
                                        key: 'busDate',
                                        format: 'YYYY-MM-DD'
                                    },
                                    form: {
                                        type: 'date',
                                        required: true,
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
                                            if (data === '' || data === '0') {
                                                return '未审核';
                                            } else if (data === '1') {
                                                return '已审核';
                                            }
                                        }
                                    },
                                    form: {
                                        type: 'string',
                                        hide: true,
                                        placeholder: '请输入'
                                    }
                                },
                                {
                                    table: {
                                        title: '备注',
                                        dataIndex: 'remark',
                                        key: 'remark',
                                        width: 150,
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
                                        type: 'qnnTable',
                                        field: 'itemList',
                                        incToForm: true,
                                        qnnTableConfig: {
                                            actionBtnsPosition: "top",
                                            antd: {
                                                rowKey: 'id',
                                                size: 'small'
                                            },
                                            paginationConfig: {
                                                position: 'bottom'
                                            },
                                            wrappedComponentRef: (me) => {
                                                this.tableOne = me;
                                            },
                                            tableTdEdit: true,
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
                                                        title: '序号',
                                                        dataIndex: 'index',
                                                        key: 'index',
                                                        width: 50,
                                                        fixed: 'left',
                                                        render: (data, rowData, index) => {
                                                            return index + 1;
                                                        }
                                                    },
                                                    isInForm: false
                                                },
                                                {
                                                    table: {
                                                        title: "<div>日期<span style='color: #ff4d4f'>*</span></div>",
                                                        dataIndex: 'useDate',
                                                        key: 'useDate',
                                                        width: 150,
                                                        fixed: 'left',
                                                        tdEdit: true,
                                                        format: 'YYYY-MM-DD'
                                                    },
                                                    form: {
                                                        type: 'date',
                                                        required: true,
                                                        placeholder: '请选择',
                                                    },
                                                },
                                                {
                                                    table: {
                                                        title: '工作内容',
                                                        dataIndex: 'content',
                                                        key: 'content',
                                                        width: 150,
                                                        fixed: 'left',
                                                        tdEdit: true
                                                    },
                                                    form: {
                                                        type: 'string',
                                                        placeholder: '请输入',
                                                    },
                                                },
                                                {
                                                    table: {
                                                        title: '使用地点',
                                                        dataIndex: 'place',
                                                        key: 'place',
                                                        width: 150,
                                                        tdEdit: true
                                                    },
                                                    form: {
                                                        type: 'string',
                                                        placeholder: '请输入',
                                                    },
                                                },
                                                {
                                                    table: {
                                                        title: '完成工程量单位',
                                                        dataIndex: 'unit',
                                                        key: 'unit',
                                                        width: 150,
                                                        tdEdit: true
                                                    },
                                                    form: {
                                                        type: 'string',
                                                        placeholder: '请输入',
                                                    },
                                                },
                                                {
                                                    table: {
                                                        title: '完成工程量数量',
                                                        dataIndex: 'qty',
                                                        key: 'qty',
                                                        width: 150,
                                                        tdEdit: true
                                                    },
                                                    form: {
                                                        type: 'number',
                                                        min: 0,
                                                        placeholder: '请输入',
                                                    },
                                                },
                                                {
                                                    table: {
                                                        title: '里程数',
                                                        dataIndex: 'mile',
                                                        key: 'mile',
                                                        width: 100,
                                                        tdEdit: true
                                                    },
                                                    form: {
                                                        type: 'number',
                                                        min: 0,
                                                        placeholder: '请输入',
                                                    },
                                                },
                                                {
                                                    table: {
                                                        title: '日历天数',
                                                        dataIndex: 'calendarNumDay',
                                                        key: 'calendarNumDay',
                                                        width: 100,
                                                        tdEdit: true
                                                    },
                                                    form: {
                                                        type: 'number',
                                                        min: 0,
                                                        placeholder: '请输入',
                                                    },
                                                },
                                                {
                                                    table: {
                                                        title: '完好台日',
                                                        dataIndex: 'wellDays',
                                                        key: 'wellDays',
                                                        width: 100,
                                                        tdEdit: true
                                                    },
                                                    form: {
                                                        type: 'number',
                                                        min: 0,
                                                        placeholder: '请输入',
                                                    },
                                                },
                                                {
                                                    table: {
                                                        title: '运转台日',
                                                        dataIndex: 'runDay',
                                                        key: 'runDay',
                                                        width: 100,
                                                        tdEdit: true
                                                    },
                                                    form: {
                                                        type: 'number',
                                                        min: 0,
                                                        placeholder: '请输入',
                                                    },
                                                },
                                                {
                                                    table: {
                                                        title: '运转台时',
                                                        dataIndex: 'actualQty',
                                                        key: 'actualQty',
                                                        width: 100,
                                                        tdEdit: true
                                                    },
                                                    form: {
                                                        type: 'number',
                                                        min: 0,
                                                        placeholder: '请输入',
                                                    },
                                                },
                                                {
                                                    table: {
                                                        title: '待工停滞台时',
                                                        dataIndex: 'waitQty',
                                                        key: 'waitQty',
                                                        width: 150,
                                                        tdEdit: true
                                                    },
                                                    form: {
                                                        type: 'number',
                                                        min: 0,
                                                        placeholder: '请输入',
                                                    },
                                                },
                                                {
                                                    table: {
                                                        title: '气候停滞台时',
                                                        dataIndex: 'weatherQty',
                                                        key: 'weatherQty',
                                                        width: 150,
                                                        tdEdit: true
                                                    },
                                                    form: {
                                                        type: 'number',
                                                        min: 0,
                                                        placeholder: '请输入',
                                                    },
                                                },
                                                {
                                                    table: {
                                                        title: '故障停滞台时',
                                                        dataIndex: 'problemQty',
                                                        key: 'problemQty',
                                                        width: 150,
                                                        tdEdit: true
                                                    },
                                                    form: {
                                                        type: 'number',
                                                        min: 0,
                                                        placeholder: '请输入',
                                                    },
                                                },
                                                {
                                                    table: {
                                                        title: '汽油消耗(升)',
                                                        dataIndex: 'gasQty',
                                                        key: 'gasQty',
                                                        width: 150,
                                                        tdEdit: true
                                                    },
                                                    form: {
                                                        type: 'number',
                                                        min: 0,
                                                        placeholder: '请输入',
                                                    },
                                                },
                                                {
                                                    table: {
                                                        title: '柴油消耗(升)',
                                                        dataIndex: 'dervQty',
                                                        key: 'dervQty',
                                                        width: 150,
                                                        tdEdit: true
                                                    },
                                                    form: {
                                                        type: 'number',
                                                        min: 0,
                                                        placeholder: '请输入',
                                                    },
                                                },
                                                {
                                                    table: {
                                                        title: '煤消耗(千克)',
                                                        dataIndex: 'coalQty',
                                                        key: 'coalQty',
                                                        width: 150,
                                                        tdEdit: true
                                                    },
                                                    form: {
                                                        type: 'number',
                                                        min: 0,
                                                        placeholder: '请输入',
                                                    },
                                                },
                                                {
                                                    table: {
                                                        title: '电消耗(度)',
                                                        dataIndex: 'consumption',
                                                        key: 'consumption',
                                                        width: 150,
                                                        tdEdit: true
                                                    },
                                                    form: {
                                                        type: 'number',
                                                        min: 0,
                                                        placeholder: '请输入',
                                                    },
                                                },
                                                {
                                                    table: {
                                                        title: '司机名称',
                                                        dataIndex: 'driverName',
                                                        key: 'driverName',
                                                        width: 100,
                                                        tdEdit: true
                                                    },
                                                    form: {
                                                        type: 'string',
                                                        placeholder: '请输入',
                                                    },
                                                },
                                            ],
                                            actionBtns: [
                                                {
                                                    name: "addRow",
                                                    icon: "plus",
                                                    type: "primary",
                                                    label: "新增行"
                                                },
                                                {
                                                    name: 'del',
                                                    icon: 'delete',
                                                    type: 'danger',
                                                    label: '删除'
                                                }
                                            ]
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
                            ]}
                        />
                    </TabPane>
                </Tabs>
                {visible ? <div>
                    <Modal
                        width={'500px'}
                        style={{ paddingBottom: '0', top: '0' }}
                        title="导入"
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
                                    field: 'companyId',
                                    type: 'string',
                                    initialValue: companyId,
                                    hide: true,
                                },
                                {
                                    field: 'orgName',
                                    type: 'string',
                                    hide: true,
                                },
                                {
                                    field: 'rentOrgName',
                                    type: 'string',
                                    hide: true,
                                },
                                {
                                    label: '导入数据文件',
                                    field: 'fileList',
                                    type: 'files',
                                    required: true,
                                    fetchConfig: {
                                        apiName: 'upload'
                                    },
                                    accept: '.xls,.xlsx'
                                },
                                {
                                    label: '公司名称',
                                    field: 'companyName',
                                    type: 'string',
                                    hide: true,
                                    initialValue: companyName,
                                    placeholder: '请输入'
                                },
                                {
                                    label: '单据编号',
                                    field: 'billNo',
                                    type: 'string',
                                    required: true,
                                    placeholder: '请输入'
                                },
                                {
                                    label: '使用单位',
                                    field: 'orgID',
                                    type: 'select',
                                    optionConfig: {
                                        label: 'departmentName',
                                        value: 'departmentId',
                                        linkageFields: {
                                            orgName: 'departmentName',
                                        }
                                    },
                                    fetchConfig: {
                                        apiName: 'getSysCompanyProject',
                                        otherParams: {
                                            departmentId: departmentId
                                        }
                                    },
                                    onChange: (val, obj) => {
                                        obj.form.setFieldsValue({ outerEquipID: null });
                                    },
                                    allowClear: false,
                                    showSearch: true,
                                    required: true,
                                    placeholder: '请选择'
                                },
                                {
                                    label: '出租单位',
                                    field: 'rentOrgID',
                                    type: 'select',
                                    optionConfig: {
                                        label: 'departmentName',
                                        value: 'departmentId',
                                        linkageFields: {
                                            rentOrgName: 'departmentName',
                                        }
                                    },
                                    fetchConfig: {
                                        apiName: 'getSysProjectBySelect',
                                        otherParams: {
                                            departmentId: departmentId
                                        }
                                    },
                                    allowClear: false,
                                    showSearch: true,
                                    placeholder: '请选择'
                                },
                                {
                                    label: '机械名称',
                                    field: 'outerEquipID',
                                    type: 'select',
                                    optionConfig: {
                                        label: 'equipName',
                                        value: 'id'
                                    },
                                    parent: 'orgID',
                                    fetchConfig: {
                                        apiName: 'getZxEqOuterEquipList',
                                        params: {
                                            useOrgID: 'orgID'
                                        }
                                    },
                                    allowClear: false,
                                    showSearch: true,
                                    placeholder: '请选择'
                                },
                                {
                                    label: '技术规格',
                                    field: 'spec',
                                    type: 'string',
                                    placeholder: '请输入'
                                },
                                {
                                    label: '填写日期',
                                    field: 'busDate',
                                    type: 'date',
                                    placeholder: '请选择'
                                },
                                {
                                    label: '备注',
                                    field: 'remark',
                                    type: 'textarea',
                                    placeholder: '请输入'
                                },
                                {
                                    field: 'component',
                                    type: 'component',
                                    Component: () => {
                                        return <div style={{ textAlign: 'center' }}><Button type="primary">导入模板下载</Button></div>
                                    }
                                },
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
                                        obj.btnfns.fetchByCb('importZxEqRentOutEqRecordList', obj.values, ({ data, success, message }) => {
                                            if (success) {
                                                Msg.success(message);
                                                this.table2.refresh();
                                                this.setState({ visible: false });
                                            } else {
                                                Msg.error(message);
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