import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import QnnForm from '../../modules/qnn-table/qnn-form';
import { message as Msg, Modal, Button } from 'antd';
import s from "./style.less";
import Tree from "../../modules/tree";
const confirm = Modal.confirm;
const config = {
    antd: {
        rowKey: 'id',
        size: 'small'
    },
    drawerConfig: {
        width: window.innerWidth * 0.8
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
        this.state = {
            listData: [
                {
                    id:'1',
                    pledgeMoney: '合同履约保证金'
                },
                {
                    id:'2',
                    pledgeMoney: '民工工资偿付保证金'
                },
                {
                    id:'3',
                    pledgeMoney: '安全生产保证金'
                },
                {
                    id:'4',
                    pledgeMoney: '工程奖励基金'
                }
            ],
            treeData: null,
            valuePid: '',
            value: '',
            defaultExpandedKeys: [],
            QDFlagData: null,
            visible: false,
            exportData: null
        }
    }
    contractOn = (obj) => {
        let rowData = obj.form.getFieldsValue();
        let projectProperty = '';
        let projType = '';
        let contractor = '';
        let biddingQualification = '';
        if (rowData?.contractor && !rowData?.biddingQualification) {
            biddingQualification = '()-';
        }
        let projectLocation = '';
        let shortName = '';
        let lotNo = '';
        if (rowData?.projectProperty) {
            projectProperty = rowData.projectProperty === 'T' ? 'T-' : 'X-';
        }
        if (rowData?.projType) {
            if (rowData.projType === '路基工程' || rowData.projType === '路面工程' || rowData.projType === '桥梁工程' || rowData.projType === '隧道工程' || rowData.projType === '综合项目') {
                projType = 'GL-';
            } else if (rowData.projType === '市政工程') {
                projType = 'SZ-';
            } else if (rowData.projType === '铁路工程') {
                projType = 'TL-';
            } else if (rowData.projType === '房建工程') {
                projType = 'FJ-';
            } else if (rowData.projType === '轨道交通') {
                projType = 'GD-';
            } else if (rowData.projType === '港口工程') {
                projType = 'GH-';
            } else if (rowData.projType === '其它工程') {
                projType = 'QT-';
            }
        }
        if (rowData?.contractor) {
            contractor = rowData.contractor;
        }
        if (rowData?.biddingQualification) {
            biddingQualification = `(${rowData.biddingQualification})-`;
        }
        if (rowData?.projectLocation) {
            if (rowData.projectLocation === '110000') {
                projectLocation = 'BEJ-';
            } else if (rowData.projectLocation === '120000') {
                projectLocation = 'TIJ-';
            } else if (rowData.projectLocation === '130000') {
                projectLocation = 'HEB-';
            } else if (rowData.projectLocation === '140000') {
                projectLocation = 'SXI-';
            } else if (rowData.projectLocation === '150000') {
                projectLocation = 'NMG-';
            } else if (rowData.projectLocation === '210000') {
                projectLocation = 'LIN-';
            } else if (rowData.projectLocation === '220000') {
                projectLocation = 'JIL-';
            } else if (rowData.projectLocation === '230000') {
                projectLocation = 'HLJ-';
            } else if (rowData.projectLocation === '310000') {
                projectLocation = 'SHH-';
            } else if (rowData.projectLocation === '330000') {
                projectLocation = 'ZHJ-';
            } else if (rowData.projectLocation === '340000') {
                projectLocation = 'ANH-';
            } else if (rowData.projectLocation === '350000') {
                projectLocation = 'FUJ-';
            } else if (rowData.projectLocation === '360000') {
                projectLocation = 'JIX-';
            } else if (rowData.projectLocation === '370000') {
                projectLocation = 'SHD-';
            } else if (rowData.projectLocation === '410000') {
                projectLocation = 'HEN-';
            } else if (rowData.projectLocation === '420000') {
                projectLocation = 'HUB-';
            } else if (rowData.projectLocation === '430000') {
                projectLocation = 'HUN-';
            } else if (rowData.projectLocation === '440000') {
                projectLocation = 'GUD-';
            } else if (rowData.projectLocation === '450000') {
                projectLocation = 'GUX-';
            } else if (rowData.projectLocation === '460000') {
                projectLocation = 'HAN-';
            } else if (rowData.projectLocation === '500000') {
                projectLocation = 'CHQ-';
            } else if (rowData.projectLocation === '510000') {
                projectLocation = 'SIC-';
            } else if (rowData.projectLocation === '520000') {
                projectLocation = 'GUZ-';
            } else if (rowData.projectLocation === '530000') {
                projectLocation = 'YUN-';
            } else if (rowData.projectLocation === '540000') {
                projectLocation = 'XIZ-';
            } else if (rowData.projectLocation === '610000') {
                projectLocation = 'SX2-';
            } else if (rowData.projectLocation === '620000') {
                projectLocation = 'GAS-';
            } else if (rowData.projectLocation === '630000') {
                projectLocation = 'QIH-';
            } else if (rowData.projectLocation === '640000') {
                projectLocation = 'NIX-';
            } else if (rowData.projectLocation === '650000') {
                projectLocation = 'XIJ-';
            } else if (rowData.projectLocation === '710000') {
                projectLocation = 'TAW-';
            } else if (rowData.projectLocation === '810000') {
                projectLocation = 'XIG-';
            } else if (rowData.projectLocation === '820000') {
                projectLocation = 'AOM-';
            }
        }
        if (rowData?.shortName) {
            shortName = rowData.shortName + '-';
        }
        if (rowData?.lotNo) {
            lotNo = rowData.lotNo;
        }
        if (contractor && biddingQualification && `(${contractor})-` === biddingQualification) {
            biddingQualification = '(T)-';
        }
        let contractNo = projectProperty + projType + contractor + biddingQualification + projectLocation + shortName + lotNo;
        obj.form.setFieldsValue({ contractNo: contractNo });
    }
    render() {
        const { departmentId } = this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany;
        const { realName } = this.props.loginAndLogoutInfo.loginInfo.userInfo;
        let { myPublic: { domain } } = this.props;
        const { listData, visible } = this.state;
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
                        apiName: 'getZxCtContractList',
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
                            onClick: (obj) => {
                                obj.btnCallbackFn.setActiveKey('0');
                            },
                            formBtns: [
                                {
                                    name: 'cancel', //关闭右边抽屉
                                    type: 'dashed',//类型  默认 primary
                                    field: 'cancel',
                                    label: '取消',
                                },
                                {
                                    name: 'submit',//内置add del
                                    type: 'primary',//类型  默认 primary
                                    field: 'submit',
                                    label: '保存',//提交数据并且关闭右边抽屉 
                                    fetchConfig: {//ajax配置
                                        apiName: 'addZxCtContract',
                                    }
                                }
                            ]
                        },
                        {
                            name: 'edit',//内置add del
                            icon: 'edit',//icon
                            type: 'primary',//类型  默认 primary  [primary dashed danger]
                            label: '修改',
                            onClick: (obj) => {
                                obj.btnCallbackFn.setActiveKey('0');
                                this.props.myFetch('getZxCtWorksWorkNameTree', { orgID: obj.selectedRows[0].orgID }).then(
                                    ({ data, success, message }) => {
                                        if (success) {
                                            this.setState({
                                                treeData: data,
                                                valuePid: data.length ? data[0].valuePid : '',
                                                value: data.length ? data[0].value : '',
                                                defaultExpandedKeys: data.length ? [data[0].value] : [],
                                            })
                                        } else {
                                            Msg.error(message)
                                        }
                                    }
                                );
                                this.props.myFetch('getZxCtWorkBookList', { orgID: obj.selectedRows[0].orgID }).then(
                                    ({ data, success, message }) => {
                                        if (success) {
                                            if (data.length) {
                                                this.setState({
                                                    QDFlagData: data[0]
                                                })
                                            }
                                        } else {
                                            Msg.error(message)
                                        }
                                    }
                                );
                            },
                            formBtns: [
                                {
                                    name: 'cancel', //关闭右边抽屉
                                    type: 'dashed',//类型  默认 primary
                                    field: 'cancel',
                                    label: '取消',
                                    hide: (obj) => {
                                        let rowData = this.table.getSelectedRows()[0];
                                        if (obj.btnCallbackFn.getActiveKey() === '0' && (rowData.contrStatus === '' || rowData.contrStatus === '0')) {
                                            return false;
                                        } else {
                                            return true;
                                        }
                                    }
                                },
                                {
                                    name: 'submit',//内置add del
                                    type: 'primary',//类型  默认 primary
                                    field: 'diysubmit',
                                    label: '保存',//提交数据并且关闭右边抽屉 
                                    fetchConfig: {//ajax配置
                                        apiName: 'updateZxCtContract',
                                    },
                                    hide: (obj) => {
                                        let rowData = this.table.getSelectedRows()[0];
                                        if (obj.btnCallbackFn.getActiveKey() === '0' && (rowData.contrStatus === '' || rowData.contrStatus === '0')) {
                                            return false;
                                        } else {
                                            return true;
                                        }
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
                            label: '基础信息审核',
                            disabled: 'bind:auditDisabled',
                            onClick: 'bind:auditClick'
                        },
                        {
                            name: 'auditee',//内置add del
                            type: 'primary',//类型  默认 primary  [primary dashed danger]
                            label: '反审核',
                            disabled: 'bind:auditeeDisabled',
                            onClick: 'bind:auditeeClick'
                        },
                        {
                            name: 'del',//内置add del
                            icon: 'delete',//icon
                            type: 'danger',//类型  默认 primary  [primary dashed danger]
                            label: '删除',
                            fetchConfig: {//ajax配置
                                apiName: 'batchDeleteUpdateZxCtContract',
                            },
                        }
                    ]}
                    // actionBtns={{
                    //     apiName: "getSysMenuBtn",
                    //     otherParams: (obj) => {
                    //         let props = obj.Pprops;
                    //         let curRouteData = props.routerInfo.routeData[props.routerInfo.curKey];
                    //         return {
                    //             menuParentId: curRouteData._value,
                    //             tableField: "OwnerSContractAccount"
                    //         }
                    //     }
                    // }}
                    drawerShowToggle={(obj) => {
                        let { drawerIsShow } = obj;
                        if (!drawerIsShow) {
                            obj.btnCallbackFn.clearSelectedRows();
                        }
                    }}
                    method={{
                        editClick: (obj) => {
                            obj.btnCallbackFn.setActiveKey('0');
                            this.props.myFetch('getZxCtWorksWorkNameTree', { orgID: obj.selectedRows[0].orgID }).then(
                                ({ data, success, message }) => {
                                    if (success) {
                                        this.setState({
                                            treeData: data,
                                            valuePid: data.length ? data[0].valuePid : '',
                                            value: data.length ? data[0].value : '',
                                            defaultExpandedKeys: data.length ? [data[0].value] : [],
                                        })
                                    } else {
                                        Msg.error(message)
                                    }
                                }
                            );
                            this.props.myFetch('getZxCtWorkBookList', { orgID: obj.selectedRows[0].orgID }).then(
                                ({ data, success, message }) => {
                                    if (success) {
                                        if (data.length) {
                                            this.setState({
                                                QDFlagData: data[0]
                                            })
                                        }
                                    } else {
                                        Msg.error(message)
                                    }
                                }
                            );
                        },
                        cancelHide: (obj) => {
                            let rowData = this.table.getSelectedRows()[0];
                            if (obj.btnCallbackFn.getActiveKey() === '0' && (rowData.contrStatus === '' || rowData.contrStatus === '0')) {
                                return false;
                            } else {
                                return true;
                            }
                        },
                        submitHide: (obj) => {
                            let rowData = this.table.getSelectedRows()[0];
                            if (obj.btnCallbackFn.getActiveKey() === '0' && (rowData.contrStatus === '' || rowData.contrStatus === '0')) {
                                return false;
                            } else {
                                return true;
                            }
                        },
                        submitOnClick: (obj) => {
                            obj.btnCallbackFn.clearSelectedRows();
                        },
                        auditDisabled: (obj) => {
                            let rowData = obj.btnCallbackFn.getSelectedRows();
                            if (rowData.length === 1 && (rowData[0].contrStatus === '' || rowData[0].contrStatus === '0')) {
                                return false;
                            } else {
                                return true
                            }
                        },
                        auditClick: (obj) => {
                            confirm({
                                content: '确定审核该条数据吗?',
                                onOk: () => {
                                    let _formData = obj.selectedRows[0];
                                    _formData.contrStatus = '1';
                                    this.props.myFetch('updateZxCtContractContrStatus', _formData).then(({ success, message, data }) => {
                                        if (success) {
                                            Msg.success(message);
                                            obj.btnCallbackFn.refresh();
                                            obj.btnCallbackFn.clearSelectedRows();
                                        } else {
                                            Msg.error(message);
                                        }
                                    });
                                }
                            });
                        },
                        auditeeDisabled: (obj) => {
                            let rowData = obj.btnCallbackFn.getSelectedRows();
                            if (rowData.length === 1 && rowData[0].contrStatus === '1') {
                                return false;
                            } else {
                                return true
                            }
                        },
                        auditeeClick: (obj) => {
                            confirm({
                                content: '确定审核该条数据吗?',
                                onOk: () => {
                                    let _formData = obj.selectedRows[0];
                                    _formData.contrStatus = '0';
                                    this.props.myFetch('updateZxCtContractContrStatus', _formData).then(({ success, message, data }) => {
                                        if (success) {
                                            Msg.success(message);
                                            obj.btnCallbackFn.refresh();
                                            obj.btnCallbackFn.clearSelectedRows();
                                        } else {
                                            Msg.error(message);
                                        }
                                    });
                                }
                            });
                        }
                    }}
                    tabs={[
                        {
                            field: "form1",
                            name: "qnnForm",
                            title: "合同信息",
                            content: {
                                // fetchConfig:(obj) => {
                                //     if(obj.initialValues && obj.initialValues.id){
                                //         return {
                                //             apiName: 'getZxCtContractDetail',
                                //             otherParams:{
                                //                 id:obj.initialValues.id
                                //             }
                                //         };
                                //     } 
                                // },
                                formConfig: [
                                    {
                                        type: "string",
                                        label: "主键ID",
                                        field: "id",
                                        hide: true
                                    },
                                    {
                                        field: 'delList',
                                        type: 'qnnTable',
                                        hide: true,
                                    },
                                    {
                                        type: "string",
                                        label: "审核状态",
                                        field: "contrStatus",
                                        hide: true
                                    },
                                    {
                                        type: "component",
                                        field: "component1",
                                        Component: obj => {
                                            return (
                                                <div style={{ background: '#f0f2f5', padding: '8px 12px' }}>
                                                    项目基本信息
                                                </div>
                                            );
                                        }
                                    },
                                    {
                                        type: "string",
                                        label: "项目名称",
                                        field: "orgName", //唯一的字段名 ***必传
                                        hide: true
                                    },
                                    {
                                        type: "string",
                                        field: "companyId", //唯一的字段名 ***必传
                                        hide: true
                                    },
                                    {
                                        type: "string",
                                        field: "companyName", //唯一的字段名 ***必传
                                        hide: true
                                    },
                                    {
                                        label: '项目名称',
                                        field: 'orgID',
                                        type: 'select',
                                        optionConfig: {
                                            label: 'departmentName',
                                            value: 'departmentId',
                                            linkageFields: {
                                                orgName: 'departmentName',
                                                companyId: 'companyId',
                                                companyName: 'companyName',
                                                comID: 'companyId'
                                            }
                                        },
                                        fetchConfig: {
                                            apiName: 'getSysProjectBySelect',
                                            otherParams: {
                                                departmentId: departmentId
                                            }
                                        },
                                        onChange: (val, obj) => {
                                            if (obj?.itemData?.departmentId) {
                                                obj.itemData.orgName = obj.itemData.departmentName;
                                                obj.itemData.projectName = obj.itemData.departmentFullName;
                                                obj.itemData.shortName = obj.itemData.projectPinyin;
                                                obj.itemData.projQuality = obj.itemData.contractingType;
                                                obj.itemData.projDetail = obj.itemData.detail;
                                            }
                                            obj.form.setFieldsValue({ ...obj.itemData });
                                        },
                                        allowClear: false,
                                        showSearch: true,
                                        required: true,
                                        condition: [
                                            {
                                                regex: {
                                                    id: ['!', /(''|null|undefined)/ig]
                                                },
                                                action: 'disabled'
                                            }
                                        ],
                                        placeholder: '请选择',
                                        span: 8
                                    },
                                    {
                                        label: '项目全称',
                                        field: 'projectName',
                                        type: 'string',
                                        required: true,
                                        condition: [
                                            {
                                                regex: {
                                                    contrStatus: '1'
                                                },
                                                action: 'disabled'
                                            }
                                        ],
                                        placeholder: '请输入',
                                        span: 8
                                    },
                                    {
                                        label: '项目性质',
                                        field: 'projectProperty',
                                        type: 'select',
                                        optionConfig: {
                                            label: 'itemName', //默认 label
                                            value: 'itemId'
                                        },
                                        fetchConfig: {
                                            apiName: 'getBaseCodeSelect',
                                            otherParams: {
                                                itemId: 'xiangMuXingZhi'
                                            }
                                        },
                                        allowClear: false,
                                        showSearch: true,
                                        required: true,
                                        condition: [
                                            {
                                                regex: {
                                                    id: ['!', /(''|null|undefined)/ig]
                                                },
                                                action: 'disabled'
                                            }
                                        ],
                                        onChange: (val, obj) => {
                                            this.contractOn(obj);
                                        },
                                        placeholder: '请选择',
                                        span: 8
                                    },
                                    {
                                        label: '项目简称',
                                        field: 'shortName',
                                        type: 'string',
                                        required: true,
                                        condition: [
                                            {
                                                regex: {
                                                    contrStatus: '1'
                                                },
                                                action: 'disabled'
                                            }
                                        ],
                                        onChange: (val, obj) => {
                                            this.contractOn(obj);
                                        },
                                        diyRules: (obj) => {
                                            var required = obj.required;
                                            var message = obj.message;
                                            return [
                                                {
                                                    required: required,
                                                    message: message
                                                },
                                                {
                                                    pattern: new RegExp(/^[A-Z]+$/),
                                                    message: "请输入英文大写"
                                                }
                                            ];
                                        },
                                        placeholder: '请输入',
                                        span: 8
                                    },
                                    {
                                        label: '工程类别',
                                        field: 'projType',
                                        type: 'select',
                                        optionConfig: {
                                            label: 'itemName', //默认 label
                                            value: 'itemId'
                                        },
                                        fetchConfig: {
                                            apiName: 'getBaseCodeSelect',
                                            otherParams: {
                                                itemId: 'gongChengLeiBie'
                                            }
                                        },
                                        condition: [
                                            {
                                                regex: {
                                                    contrStatus: '1'
                                                },
                                                action: 'disabled'
                                            }
                                        ],
                                        onChange: (val, obj) => {
                                            this.contractOn(obj);
                                        },
                                        required: true,
                                        allowClear: false,
                                        showSearch: true,
                                        placeholder: '请选择',
                                        span: 8
                                    },
                                    {
                                        label: '项目施工单位',
                                        field: 'locationInDeprFormula',
                                        type: 'select',
                                        optionConfig: {
                                            label: 'itemName', //默认 label
                                            value: 'itemId'
                                        },
                                        fetchConfig: {
                                            apiName: 'getBaseCodeSelect',
                                            otherParams: {
                                                itemId: 'shiGongDanWei'
                                            }
                                        },
                                        condition: [
                                            {
                                                regex: {
                                                    contrStatus: '1'
                                                },
                                                action: 'disabled'
                                            }
                                        ],
                                        allowClear: false,
                                        showSearch: true,
                                        required: true,
                                        placeholder: '请选择',
                                        span: 8
                                    },
                                    {
                                        label: '承建单位简称',
                                        field: 'contractor',
                                        type: 'select',
                                        optionConfig: {
                                            label: 'itemName', //默认 label
                                            value: 'itemId'
                                        },
                                        fetchConfig: {
                                            apiName: 'getBaseCodeSelect',
                                            otherParams: {
                                                itemId: 'chengJianDanWeiJianCheng'
                                            }
                                        },
                                        condition: [
                                            {
                                                regex: {
                                                    contrStatus: '1'
                                                },
                                                action: 'disabled'
                                            }
                                        ],
                                        onChange: (val, obj) => {
                                            this.contractOn(obj);
                                        },
                                        allowClear: false,
                                        showSearch: true,
                                        required: true,
                                        placeholder: '请选择',
                                        span: 8
                                    },
                                    {
                                        label: '标段号',
                                        field: 'lotNo',
                                        type: 'string',
                                        required: true,
                                        condition: [
                                            {
                                                regex: {
                                                    contrStatus: '1'
                                                },
                                                action: 'disabled'
                                            }
                                        ],
                                        diyRules: (obj) => {
                                            var required = obj.required;
                                            var message = obj.message;
                                            return [
                                                {
                                                    required: required,
                                                    message: message
                                                },
                                                {
                                                    pattern: new RegExp(/^\d{2}$/),
                                                    message: "请输入两位数字"
                                                }
                                            ];
                                        },
                                        onChange: (val, obj) => {
                                            this.contractOn(obj);
                                        },
                                        placeholder: '请输入',
                                        span: 8
                                    },
                                    {
                                        label: '项目所在区域',
                                        field: 'subArea',
                                        type: 'select',
                                        optionConfig: {
                                            label: 'itemName', //默认 label
                                            value: 'itemId'
                                        },
                                        fetchConfig: {
                                            apiName: 'getBaseCodeSelect',
                                            otherParams: {
                                                itemId: 'suoShuQuYu'
                                            }
                                        },
                                        condition: [
                                            {
                                                regex: {
                                                    contrStatus: '1'
                                                },
                                                action: 'disabled'
                                            }
                                        ],
                                        onChange: (val, obj) => {
                                            obj.form.setFieldsValue({ projectLocation: null, provinceAbbreviation: null, cityName: null });
                                        },
                                        allowClear: false,
                                        showSearch: true,
                                        required: true,
                                        placeholder: '请选择',
                                        span: 8
                                    },
                                    {
                                        label: '项目所在地',
                                        field: 'projectLocation',
                                        type: 'select',
                                        optionConfig: {
                                            label: 'itemName', //默认 label
                                            value: 'itemId',
                                            linkageFields: {
                                                provinceAbbreviation: 'itemAsName',
                                            }
                                        },
                                        parent: 'subArea',
                                        onChange: (val, obj) => {
                                            this.contractOn(obj);
                                        },
                                        fetchConfig: (obj) => {
                                            if (obj.form.getFieldValue('subArea') === '10001') {
                                                return {
                                                    apiName: 'getBaseCodeSelect',
                                                    otherParams: {
                                                        itemId: 'xingzhengquhuadaima'
                                                    }
                                                }
                                            } else if (obj.form.getFieldValue('subArea') === '10002') {
                                                return {
                                                    apiName: 'getBaseCodeSelect',
                                                    otherParams: {
                                                        itemId: 'haiWaiXiangMuSuoZaiShengFenJianCheng'
                                                    }
                                                }
                                            } else {
                                                return {};
                                            }
                                        },
                                        condition: [
                                            {
                                                regex: {
                                                    contrStatus: '1'
                                                },
                                                action: 'disabled'
                                            }
                                        ],
                                        allowClear: false,
                                        showSearch: true,
                                        required: true,
                                        placeholder: '请选择',
                                        span: 8
                                    },
                                    {
                                        label: '项目所在省份简称',
                                        field: 'provinceAbbreviation',
                                        type: 'string',
                                        allowClear: false,
                                        showSearch: true,
                                        disabled: true,
                                        placeholder: '请输入',
                                        span: 8
                                    },
                                    {
                                        label: '项目所在市级简称',
                                        field: 'cityName',
                                        type: 'select',
                                        optionConfig: {
                                            label: 'itemName', //默认 label
                                            value: 'itemId'
                                        },
                                        parent: 'projectLocation',
                                        fetchConfig: (obj) => {
                                            if (obj.form.getFieldValue('projectLocation')) {
                                                return {
                                                    apiName: 'getBaseCodeSelect',
                                                    otherParams: {
                                                        itemId: obj.form.getFieldValue('projectLocation')
                                                    }
                                                }
                                            }
                                        },
                                        condition: [
                                            {
                                                regex: {
                                                    contrStatus: '1'
                                                },
                                                action: 'disabled'
                                            },
                                            {
                                                regex: {
                                                    subArea: '10001'
                                                },
                                                action: 'required'
                                            },
                                            {
                                                regex: {
                                                    subArea: '10002'
                                                },
                                                action: 'unRequired'
                                            }
                                        ],
                                        allowClear: false,
                                        showSearch: true,
                                        placeholder: '请选择',
                                        span: 8
                                    },
                                    {
                                        label: '项目经理姓名',
                                        field: 'projectChiefName',
                                        type: 'string',
                                        condition: [
                                            {
                                                regex: {
                                                    contrStatus: '1'
                                                },
                                                action: 'disabled'
                                            }
                                        ],
                                        placeholder: '请输入',
                                        span: 8
                                    },
                                    {
                                        label: '项目经理电话',
                                        field: 'projectChiefTel',
                                        type: 'phone',
                                        condition: [
                                            {
                                                regex: {
                                                    contrStatus: '1'
                                                },
                                                action: 'disabled'
                                            }
                                        ],
                                        placeholder: '请输入',
                                        span: 8
                                    },
                                    {
                                        label: '项目参建性质',
                                        field: 'projQuality',
                                        type: 'select',
                                        optionConfig: {
                                            label: 'itemName', //默认 label
                                            value: 'itemId'
                                        },
                                        fetchConfig: {
                                            apiName: 'getBaseCodeSelect',
                                            otherParams: {
                                                itemId: 'xiangMuCanJianXingZhi'
                                            }
                                        },
                                        condition: [
                                            {
                                                regex: {
                                                    contrStatus: '1'
                                                },
                                                action: 'disabled'
                                            }
                                        ],
                                        allowClear: false,
                                        showSearch: true,
                                        placeholder: '请选择',
                                        span: 8
                                    },
                                    {
                                        label: '合同开工日期',
                                        field: 'actualStartDate',
                                        type: 'date',
                                        condition: [
                                            {
                                                regex: {
                                                    contrStatus: '1'
                                                },
                                                action: 'disabled'
                                            }
                                        ],
                                        placeholder: '请输入',
                                        span: 8
                                    },
                                    {
                                        label: '合同竣工日期',
                                        field: 'actualEndDate',
                                        type: 'date',
                                        condition: [
                                            {
                                                regex: {
                                                    contrStatus: '1'
                                                },
                                                action: 'disabled'
                                            }
                                        ],
                                        placeholder: '请输入',
                                        span: 8
                                    },
                                    {
                                        label: '所属事业部',
                                        field: 'bizDep',
                                        type: 'select',
                                        optionConfig: {
                                            label: 'itemName', //默认 label
                                            value: 'itemId'
                                        },
                                        fetchConfig: {
                                            apiName: 'getBaseCodeSelect',
                                            otherParams: {
                                                itemId: 'suoShuShiYeBu'
                                            }
                                        },
                                        condition: [
                                            {
                                                regex: {
                                                    contrStatus: '1'
                                                },
                                                action: 'disabled'
                                            }
                                        ],
                                        allowClear: false,
                                        showSearch: true,
                                        required: true,
                                        placeholder: '请选择',
                                        span: 8
                                    },
                                    {
                                        label: '中标资质',
                                        field: 'biddingQualification',
                                        type: 'select',
                                        optionConfig: {
                                            label: 'itemName', //默认 label
                                            value: 'itemId'
                                        },
                                        fetchConfig: {
                                            apiName: 'getBaseCodeSelect',
                                            otherParams: {
                                                itemId: 'zhongBiaoZiZhi'
                                            }
                                        },
                                        condition: [
                                            {
                                                regex: {
                                                    contrStatus: '1'
                                                },
                                                action: 'disabled'
                                            }
                                        ],
                                        onChange: (val, obj) => {
                                            this.contractOn(obj);
                                        },
                                        allowClear: false,
                                        showSearch: true,
                                        required: true,
                                        placeholder: '请选择',
                                        span: 8
                                    },
                                    {
                                        label: '项目特征',
                                        field: 'projFea',
                                        type: 'select',
                                        optionConfig: {
                                            label: 'itemName', //默认 label
                                            value: 'itemId'
                                        },
                                        fetchConfig: {
                                            apiName: 'getBaseCodeSelect',
                                            otherParams: {
                                                itemId: 'xiangMuTeZheng'
                                            }
                                        },
                                        condition: [
                                            {
                                                regex: {
                                                    contrStatus: '1'
                                                },
                                                action: 'disabled'
                                            }
                                        ],
                                        allowClear: false,
                                        showSearch: true,
                                        required: true,
                                        placeholder: '请选择',
                                        span: 8
                                    },
                                    {
                                        label: '是否参与指标考核',
                                        field: 'isSettle',
                                        type: 'checkbox',
                                        optionData: [
                                            {
                                                value: '1'
                                            }
                                        ],
                                        disabled: true,
                                        placeholder: '请选择',
                                        span: 8
                                    },
                                    {
                                        label: '项目开工日期',
                                        field: 'realBeginDate',
                                        type: 'date',
                                        condition: [
                                            {
                                                regex: {
                                                    contrStatus: '1'
                                                },
                                                action: 'disabled'
                                            }
                                        ],
                                        placeholder: '请输入',
                                        span: 8
                                    },
                                    {
                                        label: '项目竣工日期',
                                        field: 'realEndDate',
                                        type: 'date',
                                        condition: [
                                            {
                                                regex: {
                                                    contrStatus: '1'
                                                },
                                                action: 'disabled'
                                            }
                                        ],
                                        placeholder: '请输入',
                                        span: 8
                                    },
                                    {
                                        label: '主体完工日期',
                                        field: 'mainEndDate',
                                        type: 'date',
                                        condition: [
                                            {
                                                regex: {
                                                    contrStatus: '1'
                                                },
                                                action: 'disabled'
                                            }
                                        ],
                                        placeholder: '请输入',
                                        span: 8
                                    },
                                    {
                                        label: '项目交工日期',
                                        field: 'deliveryDate',
                                        type: 'date',
                                        condition: [
                                            {
                                                regex: {
                                                    contrStatus: '1'
                                                },
                                                action: 'disabled'
                                            }
                                        ],
                                        placeholder: '请输入',
                                        span: 8
                                    },
                                    {
                                        label: '项目归档日期',
                                        field: 'docDate',
                                        type: 'date',
                                        condition: [
                                            {
                                                regex: {
                                                    contrStatus: '1'
                                                },
                                                action: 'disabled'
                                            }
                                        ],
                                        placeholder: '请输入',
                                        span: 8
                                    },
                                    {
                                        type: "component",
                                        field: "component2",
                                        Component: obj => {
                                            return (
                                                <div style={{ background: '#f0f2f5', padding: '8px 12px' }}>
                                                    合同基本信息
                                                </div>
                                            );
                                        }
                                    },
                                    {
                                        label: '合同名称',
                                        field: 'contractName',
                                        type: 'string',
                                        required: true,
                                        condition: [
                                            {
                                                regex: {
                                                    contrStatus: '1'
                                                },
                                                action: 'disabled'
                                            }
                                        ],
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
                                    },
                                    {
                                        label: '甲方单位',
                                        field: 'firstName',
                                        type: 'string',
                                        condition: [
                                            {
                                                regex: {
                                                    contrStatus: '1'
                                                },
                                                action: 'disabled'
                                            }
                                        ],
                                        placeholder: '请输入',
                                        span: 8
                                    },
                                    {
                                        label: '甲方代表',
                                        field: 'firstPrincipal',
                                        type: 'string',
                                        condition: [
                                            {
                                                regex: {
                                                    contrStatus: '1'
                                                },
                                                action: 'disabled'
                                            }
                                        ],
                                        placeholder: '请输入',
                                        span: 8
                                    },
                                    {
                                        label: '甲方联系电话',
                                        field: 'firstUnitTel',
                                        type: 'phone',
                                        condition: [
                                            {
                                                regex: {
                                                    contrStatus: '1'
                                                },
                                                action: 'disabled'
                                            }
                                        ],
                                        placeholder: '请输入',
                                        span: 8
                                    },
                                    {
                                        label: '甲方联系地址',
                                        field: 'firstAddr',
                                        type: 'string',
                                        condition: [
                                            {
                                                regex: {
                                                    contrStatus: '1'
                                                },
                                                action: 'disabled'
                                            }
                                        ],
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
                                    },
                                    {
                                        label: '乙方单位',
                                        field: 'secondName',
                                        type: 'string',
                                        condition: [
                                            {
                                                regex: {
                                                    contrStatus: '1'
                                                },
                                                action: 'disabled'
                                            }
                                        ],
                                        placeholder: '请输入',
                                        span: 8
                                    },
                                    {
                                        label: '乙方代表',
                                        field: 'secondPrincipal',
                                        type: 'string',
                                        condition: [
                                            {
                                                regex: {
                                                    contrStatus: '1'
                                                },
                                                action: 'disabled'
                                            }
                                        ],
                                        placeholder: '请输入',
                                        span: 8
                                    },
                                    {
                                        label: '乙方联系电话',
                                        field: 'secondUnitTel',
                                        type: 'phone',
                                        condition: [
                                            {
                                                regex: {
                                                    contrStatus: '1'
                                                },
                                                action: 'disabled'
                                            }
                                        ],
                                        placeholder: '请输入',
                                        span: 8
                                    },
                                    {
                                        label: '乙方联系地址',
                                        field: 'secondAddr',
                                        type: 'string',
                                        condition: [
                                            {
                                                regex: {
                                                    contrStatus: '1'
                                                },
                                                action: 'disabled'
                                            }
                                        ],
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
                                    },
                                    {
                                        type: "component",
                                        field: "component3",
                                        Component: obj => {
                                            return (
                                                <div style={{ background: '#f0f2f5', padding: '8px 12px' }}>
                                                    合同金额
                                                </div>
                                            );
                                        }
                                    },
                                    {
                                        label: '含税合同金额(万元)',
                                        field: 'contractCost',
                                        type: 'number',
                                        required: true,
                                        onChange: (val, obj) => {
                                            let formData = obj.form.getFieldsValue();
                                            if (formData.contractCost && (Number(formData.taxRate) || Number(formData.taxRate) === 0)) {
                                                obj.form.setFieldsValue({
                                                    contractCostNoTax: (formData.contractCost / (1 + Number(formData.taxRate) / 100)),
                                                    contractCostTax: (formData.contractCost - formData.contractCost / (1 + Number(formData.taxRate) / 100))
                                                })
                                            } else {
                                                obj.form.setFieldsValue({
                                                    contractCostNoTax: 0,
                                                    contractCostTax: 0
                                                })
                                            }
                                        },
                                        condition: [
                                            {
                                                regex: {
                                                    contrStatus: '1'
                                                },
                                                action: 'disabled'
                                            }
                                        ],
                                        placeholder: '请输入',
                                        span: 8
                                    },
                                    {
                                        label: '税率(%)',
                                        field: 'taxRate',
                                        type: 'select',
                                        optionConfig: {
                                            label: 'itemName', //默认 label
                                            value: 'itemId'
                                        },
                                        fetchConfig: {
                                            apiName: 'getBaseCodeSelect',
                                            otherParams: {
                                                itemId: 'shuiLv'
                                            }
                                        },
                                        condition: [
                                            {
                                                regex: {
                                                    contrStatus: '1'
                                                },
                                                action: 'disabled'
                                            }
                                        ],
                                        onChange: (val, obj) => {
                                            let formData = obj.form.getFieldsValue();
                                            if (formData.contractCost && (Number(formData.taxRate) || Number(formData.taxRate) === 0)) {
                                                obj.form.setFieldsValue({
                                                    contractCostNoTax: (formData.contractCost / (1 + Number(formData.taxRate) / 100)),
                                                    contractCostTax: (formData.contractCost - formData.contractCost / (1 + Number(formData.taxRate) / 100))
                                                })
                                            } else {
                                                obj.form.setFieldsValue({
                                                    contractCostNoTax: 0,
                                                    contractCostTax: 0
                                                })
                                            }
                                        },
                                        allowClear: false,
                                        showSearch: true,
                                        required: true,
                                        placeholder: '请选择',
                                        span: 8
                                    },
                                    {
                                        label: '不含税合同金额(万元)',
                                        field: 'contractCostNoTax',
                                        type: 'number',
                                        disabled: true,
                                        precision: 2,
                                        placeholder: '请输入',
                                        span: 8
                                    },
                                    {
                                        label: '清单金额(万元)',
                                        field: 'qdMoney',
                                        type: 'number',
                                        placeholder: '请输入',
                                        condition: [
                                            {
                                                regex: {
                                                    contrStatus: '1'
                                                },
                                                action: 'disabled'
                                            }
                                        ],
                                        span: 8
                                    },
                                    {
                                        label: '计税方法',
                                        field: 'isEasyTax',
                                        type: 'select',
                                        optionConfig: {
                                            label: 'itemName', //默认 label
                                            value: 'itemId'
                                        },
                                        fetchConfig: {
                                            apiName: 'getBaseCodeSelect',
                                            otherParams: {
                                                itemId: 'shiFou'
                                            }
                                        },
                                        allowClear: false,
                                        showSearch: true,
                                        required: true,
                                        condition: [
                                            {
                                                regex: {
                                                    contrStatus: '1'
                                                },
                                                action: 'disabled'
                                            }
                                        ],
                                        placeholder: '请选择',
                                        span: 8
                                    },
                                    {
                                        label: '税金(万元)',
                                        field: 'contractCostTax',
                                        type: 'number',
                                        disabled: true,
                                        precision: 2,
                                        placeholder: '请输入',
                                        span: 8
                                    },
                                    {
                                        label: '当前合同总造价(万元)',
                                        field: 'contractMoney',
                                        type: 'number',
                                        condition: [
                                            {
                                                regex: {
                                                    contrStatus: '1'
                                                },
                                                action: 'disabled'
                                            }
                                        ],
                                        placeholder: '请输入',
                                        span: 8
                                    },
                                    {
                                        label: '对下标准合同编码',
                                        field: 'contractNo',
                                        type: 'string',
                                        placeholder: '请输入',
                                        disabled: true,
                                        span: 16,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 5 },
                                                sm: { span: 5 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 19 },
                                                sm: { span: 19 }
                                            }
                                        }
                                    },
                                    {
                                        type: "component",
                                        field: "component4",
                                        Component: obj => {
                                            return (
                                                <div style={{ background: '#f0f2f5', padding: '8px 12px' }}>
                                                    合同工期
                                                </div>
                                            );
                                        }
                                    },
                                    {
                                        label: '合同开始时间',
                                        field: 'planStartDate',
                                        type: 'date',
                                        condition: [
                                            {
                                                regex: {
                                                    contrStatus: '1'
                                                },
                                                action: 'disabled'
                                            }
                                        ],
                                        placeholder: '请选择',
                                        span: 8
                                    },
                                    {
                                        label: '合同结束时间',
                                        field: 'planEndDate',
                                        type: 'date',
                                        condition: [
                                            {
                                                regex: {
                                                    contrStatus: '1'
                                                },
                                                action: 'disabled'
                                            }
                                        ],
                                        placeholder: '请选择',
                                        span: 8
                                    },
                                    {
                                        label: '合同工期(天)',
                                        field: 'csTimeLimit',
                                        type: 'string',
                                        condition: [
                                            {
                                                regex: {
                                                    contrStatus: '1'
                                                },
                                                action: 'disabled'
                                            }
                                        ],
                                        placeholder: '请输入',
                                        span: 8
                                    },
                                    {
                                        label: '签订日期',
                                        field: 'signatureDate',
                                        type: 'date',
                                        condition: [
                                            {
                                                regex: {
                                                    contrStatus: '1'
                                                },
                                                action: 'disabled'
                                            }
                                        ],
                                        placeholder: '请选择',
                                        span: 8
                                    },
                                    {
                                        label: '合同状态',
                                        field: 'contractStatus',
                                        type: 'select',
                                        optionConfig: {
                                            label: 'itemName', //默认 label
                                            value: 'itemId'
                                        },
                                        fetchConfig: {
                                            apiName: 'getBaseCodeSelect',
                                            otherParams: {
                                                itemId: 'heTongZhuangTai'
                                            }
                                        },
                                        allowClear: false,
                                        showSearch: true,
                                        required: true,
                                        condition: [
                                            {
                                                regex: {
                                                    contrStatus: '1'
                                                },
                                                action: 'disabled'
                                            }
                                        ],
                                        initialValue: '1',
                                        placeholder: '请选择',
                                        span: 8
                                    },
                                    {
                                        type: "component",
                                        field: "component5",
                                        Component: obj => {
                                            return (
                                                <div style={{ background: '#f0f2f5', padding: '8px 12px' }}>
                                                    外部单位信息
                                                </div>
                                            );
                                        }
                                    },
                                    {
                                        label: '监理单位',
                                        field: 'consultative',
                                        type: 'string',
                                        condition: [
                                            {
                                                regex: {
                                                    contrStatus: '1'
                                                },
                                                action: 'disabled'
                                            }
                                        ],
                                        placeholder: '请输入',
                                        span: 8
                                    },
                                    {
                                        label: '监理单位联系人',
                                        field: 'consultativeUser',
                                        type: 'string',
                                        condition: [
                                            {
                                                regex: {
                                                    contrStatus: '1'
                                                },
                                                action: 'disabled'
                                            }
                                        ],
                                        placeholder: '请输入',
                                        span: 8
                                    },
                                    {
                                        label: '监理单位电话',
                                        field: 'consultativeTel',
                                        type: 'phone',
                                        condition: [
                                            {
                                                regex: {
                                                    contrStatus: '1'
                                                },
                                                action: 'disabled'
                                            }
                                        ],
                                        placeholder: '请输入',
                                        span: 8
                                    },
                                    {
                                        label: '监理单位地址',
                                        field: 'consultativeAddr',
                                        type: 'string',
                                        placeholder: '请输入',
                                        condition: [
                                            {
                                                regex: {
                                                    contrStatus: '1'
                                                },
                                                action: 'disabled'
                                            }
                                        ],
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
                                        label: '设计单位',
                                        field: 'designUnit',
                                        type: 'string',
                                        condition: [
                                            {
                                                regex: {
                                                    contrStatus: '1'
                                                },
                                                action: 'disabled'
                                            }
                                        ],
                                        placeholder: '请输入',
                                        span: 8
                                    },
                                    {
                                        label: '设计单位联系人',
                                        field: 'designUser',
                                        type: 'string',
                                        condition: [
                                            {
                                                regex: {
                                                    contrStatus: '1'
                                                },
                                                action: 'disabled'
                                            }
                                        ],
                                        placeholder: '请输入',
                                        span: 8
                                    },
                                    {
                                        label: '设计单位电话',
                                        field: 'designPhone',
                                        type: 'phone',
                                        condition: [
                                            {
                                                regex: {
                                                    contrStatus: '1'
                                                },
                                                action: 'disabled'
                                            }
                                        ],
                                        placeholder: '请输入',
                                        span: 8
                                    },
                                    {
                                        label: '设计单位地址',
                                        field: 'designAddr',
                                        type: 'string',
                                        condition: [
                                            {
                                                regex: {
                                                    contrStatus: '1'
                                                },
                                                action: 'disabled'
                                            }
                                        ],
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
                                    },
                                    {
                                        type: "component",
                                        field: "component6",
                                        Component: obj => {
                                            return (
                                                <div style={{ background: '#f0f2f5', padding: '8px 12px' }}>
                                                    合同条款
                                                </div>
                                            );
                                        }
                                    },
                                    {
                                        label: '动员预付款(元)',
                                        field: 'dyyfkMoney',
                                        type: 'number',
                                        condition: [
                                            {
                                                regex: {
                                                    contrStatus: '1'
                                                },
                                                action: 'disabled'
                                            }
                                        ],
                                        placeholder: '请输入',
                                        span: 8
                                    },
                                    {
                                        label: '动员预付款起扣点(元)',
                                        field: 'dyyfkqkdMoney',
                                        type: 'number',
                                        condition: [
                                            {
                                                regex: {
                                                    contrStatus: '1'
                                                },
                                                action: 'disabled'
                                            }
                                        ],
                                        placeholder: '请输入',
                                        span: 8
                                    },
                                    {
                                        label: '扣回动员预付款比例(%)',
                                        field: 'khdyyfkPercent',
                                        type: 'number',
                                        condition: [
                                            {
                                                regex: {
                                                    contrStatus: '1'
                                                },
                                                action: 'disabled'
                                            }
                                        ],
                                        placeholder: '请输入',
                                        span: 8
                                    },
                                    {
                                        label: '动员预付款全额扣回点(元)',
                                        field: 'dyyfkqekhdMoney',
                                        type: 'number',
                                        condition: [
                                            {
                                                regex: {
                                                    contrStatus: '1'
                                                },
                                                action: 'disabled'
                                            }
                                        ],
                                        placeholder: '请输入',
                                        span: 8
                                    },
                                    {
                                        label: '材料预付款比例(%)',
                                        field: 'clyfkPercent',
                                        type: 'number',
                                        condition: [
                                            {
                                                regex: {
                                                    contrStatus: '1'
                                                },
                                                action: 'disabled'
                                            }
                                        ],
                                        placeholder: '请输入',
                                        span: 8
                                    },
                                    {
                                        label: '材料预付款限额(元)',
                                        field: 'clyfkxeMoney',
                                        type: 'number',
                                        condition: [
                                            {
                                                regex: {
                                                    contrStatus: '1'
                                                },
                                                action: 'disabled'
                                            }
                                        ],
                                        placeholder: '请输入',
                                        span: 8
                                    },
                                    {
                                        label: '材料扣回款比例(%)',
                                        field: 'clkhkblPercent',
                                        type: 'number',
                                        condition: [
                                            {
                                                regex: {
                                                    contrStatus: '1'
                                                },
                                                action: 'disabled'
                                            }
                                        ],
                                        placeholder: '请输入',
                                        span: 8
                                    },
                                    {
                                        label: '迟扣款利息(%)',
                                        field: 'ckklxPercent',
                                        type: 'number',
                                        condition: [
                                            {
                                                regex: {
                                                    contrStatus: '1'
                                                },
                                                action: 'disabled'
                                            }
                                        ],
                                        placeholder: '请输入',
                                        span: 8
                                    },
                                    {
                                        label: '迟扣款利息限额(元)',
                                        field: 'ckklxxeMoney',
                                        type: 'number',
                                        condition: [
                                            {
                                                regex: {
                                                    contrStatus: '1'
                                                },
                                                action: 'disabled'
                                            }
                                        ],
                                        placeholder: '请输入',
                                        span: 8
                                    },
                                    {
                                        label: '违约金限额(元)',
                                        field: 'wyjxeMoney',
                                        type: 'number',
                                        condition: [
                                            {
                                                regex: {
                                                    contrStatus: '1'
                                                },
                                                action: 'disabled'
                                            }
                                        ],
                                        placeholder: '请输入',
                                        span: 8
                                    },
                                    {
                                        label: '索赔金限额(元)',
                                        field: 'cpjexeMoney',
                                        type: 'number',
                                        condition: [
                                            {
                                                regex: {
                                                    contrStatus: '1'
                                                },
                                                action: 'disabled'
                                            }
                                        ],
                                        placeholder: '请输入',
                                        span: 8
                                    },
                                    {
                                        label: '保留金扣款比例(%)',
                                        field: 'bljblPercent',
                                        type: 'number',
                                        condition: [
                                            {
                                                regex: {
                                                    contrStatus: '1'
                                                },
                                                action: 'disabled'
                                            }
                                        ],
                                        placeholder: '请输入',
                                        span: 8
                                    },
                                    {
                                        label: '保留金起扣点(元)',
                                        field: 'bljqkdMoney',
                                        type: 'number',
                                        condition: [
                                            {
                                                regex: {
                                                    contrStatus: '1'
                                                },
                                                action: 'disabled'
                                            }
                                        ],
                                        placeholder: '请输入',
                                        span: 8
                                    },
                                    {
                                        label: '累计保留金限额(元)',
                                        field: 'ljkbljxeMoney',
                                        type: 'number',
                                        condition: [
                                            {
                                                regex: {
                                                    contrStatus: '1'
                                                },
                                                action: 'disabled'
                                            }
                                        ],
                                        placeholder: '请输入',
                                        span: 8
                                    },
                                    {
                                        type: 'qnnTable',
                                        field: 'bondList',
                                        incToForm: true,
                                        initialValue: listData,
                                        condition: [
                                            {
                                                regex: {
                                                    contrStatus: '1'
                                                },
                                                action: 'disabled'
                                            }
                                        ],
                                        qnnTableConfig: {
                                            actionBtnsPosition: "top",
                                            antd: {
                                                rowKey: 'id',
                                                size: 'small'
                                            },
                                            paginationConfig: false,
                                            pageSize: 9999,
                                            wrappedComponentRef: (me) => {
                                                this.tableOne = me;
                                            },
                                            // tableTdEdit:true,
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
                                                        title: '保证金',
                                                        dataIndex: 'pledgeMoney',
                                                        key: 'pledgeMoney'
                                                    },
                                                    form: {
                                                        type: 'string',
                                                        placeholder: '请输入',
                                                    },
                                                },
                                                {
                                                    table: {
                                                        title: '类型',
                                                        dataIndex: 'type',
                                                        key: 'type',
                                                        tdEdit: true,
                                                        render: (data, rowData) => {
                                                            if (rowData.pledgeMoney === '合同履约保证金') {
                                                                if (data === '10001') {
                                                                    return '现金';
                                                                } else if (data === '10002') {
                                                                    return '现金加保函';
                                                                } else if (data === '10003') {
                                                                    return '银行保函';
                                                                } else if (data === '10004') {
                                                                    return '资产抵押';
                                                                }
                                                            } else {
                                                                if (data === '10001') {
                                                                    return '中期扣付计量款';
                                                                } else if (data === '10002') {
                                                                    return '银行保函';
                                                                } else if (data === '10003') {
                                                                    return '资产抵押';
                                                                }
                                                            }
                                                        }
                                                    },
                                                    form: {
                                                        type: 'select',
                                                        optionConfig: {
                                                            label: 'itemName',
                                                            value: 'itemId'
                                                        },
                                                        fetchConfig: {
                                                            apiName: 'getBaseCodeSelect',
                                                            otherParams: (obj) => {
                                                                // console.log(obj);
                                                                // console.log(this.tableOne.qnnTableTdForm.getFieldsValue())
                                                                if (obj.rowData.pledgeMoney === '合同履约保证金') {
                                                                    return {
                                                                        itemId: 'heTongLvYueBaoZhengJinLeiXing'
                                                                    }
                                                                } else {
                                                                    return {
                                                                        itemId: 'nongMinGongAnQuanGongChengJiangLiLeiXing'
                                                                    }
                                                                }
                                                            }
                                                        },
                                                        // onChange:(val,obj,btnCallbackFn) => {
                                                        //     btnCallbackFn.getEditedRowData(false).then((editRowData) => {
                                                        //         editRowData.type = obj.itemData.itemId;
                                                        //         btnCallbackFn.setEditedRowData({...editRowData});
                                                        //     })
                                                        // },
                                                        placeholder: '请选择'
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '现金金额',
                                                        dataIndex: 'money',
                                                        key: 'money',
                                                        tdEdit: true,
                                                        // fieldConfig: {
                                                        //     formatter: (value, props) => {
                                                        //         if (props ?.rowData ?.pledgeMoney !== '合同履约保证金' && props ?.rowData ?.type === '10001') {
                                                        //             return value ? (`${value.toString().replace(/%/ig, '')}%`) : value;
                                                        //         } else {
                                                        //             return value;
                                                        //         }
                                                        //     },
                                                        //     parser: (value, props) => {
                                                        //         return value ? `${value}`.replace(/%/ig, '') : value;
                                                        //     },
                                                        // },
                                                        render: (data, rowData) => {
                                                            if (rowData.pledgeMoney !== '合同履约保证金' && rowData.type === '10001') {
                                                                return data + '%';
                                                            } else {
                                                                return data;
                                                            }
                                                        }
                                                    },
                                                    form: {
                                                        type: 'money',
                                                        // onChange:(val,obj,btnCallbackFn) => {
                                                        //     btnCallbackFn.getEditedRowData(false).then((editRowData) => {
                                                        //         editRowData.money = val;
                                                        //         btnCallbackFn.setEditedRowData({...editRowData});
                                                        //     })
                                                        // },
                                                        formatter: (value, props) => {
                                                            if (props?.rowData?.pledgeMoney !== '合同履约保证金' && props?.rowData?.type === '10001') {
                                                                return value ? (`${value.toString().replace(/%/ig, '')}%`) : value;
                                                            } else {
                                                                return value;
                                                            }
                                                        },
                                                        parser: (value, props) => {
                                                            return value ? `${value}`.replace(/%/ig, '') : value;
                                                        },
                                                        placeholder: '请输入'
                                                    },
                                                },
                                                {
                                                    table: {
                                                        title: '返还条件',
                                                        dataIndex: 'backCondition',
                                                        key: 'backCondition',
                                                        tdEdit: true
                                                    },
                                                    form: {
                                                        type: 'string',
                                                        // onChange:(val,obj,btnCallbackFn) => {
                                                        //     btnCallbackFn.getEditedRowData(false).then((editRowData) => {
                                                        //         editRowData.backCondition = val;
                                                        //         btnCallbackFn.setEditedRowData({...editRowData});
                                                        //     })
                                                        // },
                                                        placeholder: '请输入',
                                                    },
                                                },
                                                {
                                                    table: {
                                                        title: '期限',
                                                        dataIndex: 'timeLimit',
                                                        key: 'timeLimit',
                                                        tdEdit: true
                                                    },
                                                    form: {
                                                        type: 'string',
                                                        // onChange:(val,obj,btnCallbackFn) => {
                                                        //     btnCallbackFn.getEditedRowData(false).then((editRowData) => {
                                                        //         editRowData.timeLimit = val;
                                                        //         btnCallbackFn.setEditedRowData({...editRowData});
                                                        //     })
                                                        // },
                                                        placeholder: '请输入',
                                                    },
                                                }
                                            ],
                                            actionBtns: [
                                                {
                                                    name: 'del',
                                                    icon: 'delete',
                                                    type: 'danger',
                                                    label: '删除',
                                                    onClick: (obj) => {
                                                        const { selectedRows, btnCallbackFn } = obj;
                                                        //有可能是树表
                                                        let selectedRowsKey = selectedRows.map(item => item['id']);
                                                        const delFn = (listData) => {
                                                            return listData.filter(item => {
                                                                if (item.children) {
                                                                    item.children = delFn(item.children);
                                                                }
                                                                return !selectedRowsKey.includes(item['id'])
                                                            })

                                                        }
                                                        btnCallbackFn.setState({
                                                            data: delFn(obj.state.data),
                                                            selectedRows: []
                                                        })
                                                    }
                                                }
                                            ]
                                        }
                                    },
                                    {
                                        type: "component",
                                        field: "component7",
                                        Component: obj => {
                                            return (
                                                <div style={{ background: '#f0f2f5', padding: '8px 12px' }}>
                                                    项目概况
                                                </div>
                                            );
                                        }
                                    },
                                    {
                                        label: '主要工程项目工程数量',
                                        field: 'projDetail',
                                        type: 'textarea',
                                        required: true,
                                        placeholder: '请输入',
                                        condition: [
                                            {
                                                regex: {
                                                    contrStatus: '1'
                                                },
                                                action: 'disabled'
                                            }
                                        ],
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 24 },
                                                sm: { span: 24 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 24 },
                                                sm: { span: 24 }
                                            }
                                        }
                                    },
                                    {
                                        label: '业主节点工期',
                                        field: 'ownerNodeDeys',
                                        type: 'textarea',
                                        required: true,
                                        placeholder: '请输入',
                                        condition: [
                                            {
                                                regex: {
                                                    contrStatus: '1'
                                                },
                                                action: 'disabled'
                                            }
                                        ],
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 24 },
                                                sm: { span: 24 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 24 },
                                                sm: { span: 24 }
                                            }
                                        }
                                    },
                                    {
                                        label: '业主调差、变更定价原则等主要专用条款',
                                        field: 'ownerSpecialClause',
                                        type: 'textarea',
                                        required: true,
                                        placeholder: '请输入',
                                        condition: [
                                            {
                                                regex: {
                                                    contrStatus: '1'
                                                },
                                                action: 'disabled'
                                            }
                                        ],
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 24 },
                                                sm: { span: 24 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 24 },
                                                sm: { span: 24 }
                                            }
                                        }
                                    },
                                    {
                                        label: '合同内容概述',
                                        field: 'summaryOfContractContents',
                                        type: 'textarea',
                                        placeholder: '请输入',
                                        condition: [
                                            {
                                                regex: {
                                                    contrStatus: '1'
                                                },
                                                action: 'disabled'
                                            }
                                        ],
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
                                        label: '附件',
                                        field: 'attachment',
                                        type: 'files',
                                        required: true,
                                        fetchConfig: {
                                            apiName: 'upload'
                                        },
                                        condition: [
                                            {
                                                regex: {
                                                    contrStatus: '1'
                                                },
                                                action: 'disabled'
                                            }
                                        ],
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
                            field: "tableqd",
                            name: "tableqd",
                            title: "清单",
                            disabled: (obj) => {
                                if (obj.clickCb.rowInfo.name === 'add') {
                                    return true;
                                } else {
                                    return false;
                                }
                            },
                            content: props => {
                                return <div className={s.root}>
                                    <div className={s.rootl}
                                        ref={(me) => {
                                            if (me) {
                                                this.leftDom = me;
                                            }
                                        }}>
                                        <div
                                            className={s.hr}
                                            ref={(me) => {
                                                if (me) {
                                                    let _this = this;
                                                    function moveFn(e) {
                                                        let conDomLeft = document.getElementsByClassName('ant-drawer-content-wrapper')[0].offsetLeft;
                                                        _this.leftDom.style['flex-basis'] = e.pageX - conDomLeft + 'px'
                                                    }
                                                    me.addEventListener('mousedown', (e) => {
                                                        this.onDragStartPos = e.pageX;
                                                        document.addEventListener('mousemove', moveFn, false)
                                                    }, false);
                                                    document.addEventListener('mouseup', (e) => {
                                                        document.removeEventListener('mousemove', moveFn, false)
                                                    }, false)
                                                }
                                            }}
                                        ></div>
                                        {this.state.treeData ? <Tree
                                            selectText={false}
                                            modalType="common" //common  drawer  抽屉出现方式或者普通的
                                            visible
                                            selectModal="0" //0不可选  1单选(默认)  2多选（暂不可用）
                                            myFetch={this.props.myFetch}
                                            upload={this.props.myUpload}
                                            btnShow={false} //是否显示底部按钮
                                            disabled={true}
                                            draggable={false}
                                            nodeRender={nodeData => {
                                                return (
                                                    <span>
                                                        {nodeData["label"]}
                                                    </span>
                                                );
                                            }}
                                            treeProps={{
                                                showLine: true
                                            }}
                                            defaultExpandedKeys={this.state.defaultExpandedKeys}
                                            rightMenuOption={[]}
                                            nodeClick={(node) => {

                                                this.setState({
                                                    valuePid: ''
                                                }, () => {
                                                    this.setState({
                                                        defaultExpandedKeys: [node.value],
                                                        value: node.value,
                                                        valuePid: node.valuePid
                                                    })
                                                })
                                            }}
                                            data={this.state.treeData}
                                            //键值配置 默认{value:value,label:label,children:children}
                                            keys={{
                                                label: "label",
                                                value: "value",
                                                children: "children"
                                            }}
                                        /> : null}
                                    </div>
                                    <div className={s.rootr}>
                                        {this.state.valuePid ? <QnnTable
                                            // {...this.props}
                                            fetch={this.props.myFetch}
                                            upload={this.props.myUpload}
                                            headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                                            wrappedComponentRef={(me) => {
                                                this.tableQD = me;
                                            }}
                                            fetchConfig={{
                                                apiName: 'getZxCtWorksTreeList',
                                                otherParams: {
                                                    parentID: this.state.valuePid === '-1' ? this.state.valuePid : this.state.value,
                                                    orgID: props.initialValues.orgID
                                                }
                                            }}
                                            desc={<div><span style={{ color: "black" }}>提示：黑色字体表示未变更</span> <span style={{ color: "#ffd400" }}>黄色字体表示变更中</span> <span style={{ color: "red" }}>红色字体表示变更后</span></div>}
                                            actionBtns={[
                                                {
                                                    name: 'export',//内置add del
                                                    type: 'primary',//类型  默认 primary  [primary dashed danger]
                                                    disabled: this.state.QDFlagData && (this.state.QDFlagData.pp1 === '1' || this.state.QDFlagData.status === '1') ? true : false,
                                                    label: '清单导入',
                                                    onClick: () => {
                                                        this.setState({
                                                            visible: true
                                                        })
                                                    }
                                                },
                                                {
                                                    name: 'BJXJ',//内置add del
                                                    type: 'primary',//类型  默认 primary  [primary dashed danger]
                                                    disabled: this.state.QDFlagData && this.state.QDFlagData.pp1 === '1' ? true : false,
                                                    label: '编辑下级清单',
                                                    onClick: (obj) => {
                                                        if (obj.selectedRows.length === 1) {
                                                            obj.btnCallbackFn.closeDrawer(true);
                                                        } else {
                                                            Msg.error('请选择一条数据！');
                                                        }
                                                    }
                                                },
                                                {
                                                    name: 'PLBJ',//内置add del
                                                    type: 'primary',//类型  默认 primary  [primary dashed danger]
                                                    disabled: this.state.QDFlagData && this.state.QDFlagData.pp1 === '1' ? true : false,
                                                    label: '批量编辑',
                                                    onClick: (obj) => {
                                                        this.table.form.setFieldsValue({
                                                            delList: []
                                                        })
                                                        obj.btnCallbackFn.closeDrawer(true);
                                                    }
                                                },
                                                {
                                                    name: 'HTSLQR',//内置add del
                                                    type: 'primary',//类型  默认 primary  [primary dashed danger]
                                                    disabled: this.state.QDFlagData && this.state.QDFlagData.pp1 === '1' ? true : this.state.QDFlagData && this.state.QDFlagData.status === '0' ? false : true,
                                                    label: '合同数量确认',
                                                    onClick: () => {
                                                        confirm({
                                                            content: '确认后不可修改,确定要确认吗?',
                                                            onOk: () => {
                                                                let HTSLQRData = this.state.QDFlagData;
                                                                HTSLQRData.status = '1';
                                                                this.props.myFetch('zxCtContractQuantity', HTSLQRData).then(
                                                                    ({ data, success, message }) => {
                                                                        if (success) {
                                                                            this.setState({
                                                                                QDFlagData: HTSLQRData
                                                                            })
                                                                        } else {
                                                                            Msg.error(message)
                                                                        }
                                                                    }
                                                                );
                                                            }
                                                        })
                                                    }
                                                },
                                                {
                                                    name: 'HDSLQR',//内置add del
                                                    type: 'primary',//类型  默认 primary  [primary dashed danger]
                                                    disabled: this.state.QDFlagData && this.state.QDFlagData.pp1 === '1' ? true : this.state.QDFlagData && this.state.QDFlagData.status === '1' ? false : true,
                                                    label: '核定数量确认',
                                                    onClick: () => {
                                                        confirm({
                                                            content: '确认后不可修改,确定要确认吗?',
                                                            onOk: () => {
                                                                let HTSLQRData = this.state.QDFlagData;
                                                                HTSLQRData.pp1 = '1';
                                                                this.props.myFetch('zxCtVerificationQuantity', HTSLQRData).then(
                                                                    ({ data, success, message }) => {
                                                                        if (success) {
                                                                            this.setState({
                                                                                QDFlagData: HTSLQRData
                                                                            })
                                                                        } else {
                                                                            Msg.error(message)
                                                                        }
                                                                    }
                                                                );
                                                            }
                                                        })
                                                    }
                                                },
                                                {
                                                    name: 'BG',//内置add del
                                                    type: 'primary',//类型  默认 primary  [primary dashed danger]
                                                    disabled: this.state.QDFlagData && this.state.QDFlagData.status === '0' ? true : false,
                                                    label: '变更',
                                                    onClick: (obj) => {
                                                        if (obj.selectedRows.length === 1) {
                                                            obj.btnCallbackFn.closeDrawer(true);
                                                        } else {
                                                            Msg.error('请选择一条数据！');
                                                        }
                                                    }
                                                },
                                            ]}
                                            {...config}
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
                                                        title: '清单编号',
                                                        dataIndex: 'workNo',
                                                        key: 'workNo',
                                                        width: 150,
                                                        fixed: 'left',
                                                        render: (data, rowData) => {
                                                            return <div style={{ textIndent: (rowData.treeNode.length - 4) * 2 + 'px' }}>{data}</div>;
                                                        }
                                                    },
                                                    isInForm: false
                                                },
                                                {
                                                    table: {
                                                        title: '清单名称',
                                                        dataIndex: 'workName',
                                                        key: 'workName',
                                                        width: 150,
                                                        fixed: 'left'
                                                    },
                                                    isInForm: false
                                                },
                                                {
                                                    table: {
                                                        title: '计量单位',
                                                        dataIndex: 'unit',
                                                        key: 'unit',
                                                        width: 150,
                                                    },
                                                    isInForm: false
                                                },
                                                {
                                                    table: {
                                                        title: '合同数量',
                                                        dataIndex: 'contractQty',
                                                        key: 'contractQty',
                                                        width: 150,
                                                    },
                                                    isInForm: false
                                                },
                                                {
                                                    table: {
                                                        title: '合同单价',
                                                        dataIndex: 'contractPrice',
                                                        key: 'contractPrice',
                                                        width: 150,
                                                    },
                                                    isInForm: false
                                                },
                                                {
                                                    table: {
                                                        title: '合同金额',
                                                        dataIndex: 'contractAmt',
                                                        key: 'contractAmt',
                                                        width: 150,
                                                    },
                                                    isInForm: false
                                                },
                                                {
                                                    table: {
                                                        title: '核定数量',
                                                        dataIndex: 'checkQty',
                                                        key: 'checkQty',
                                                        width: 150,
                                                    },
                                                    isInForm: false
                                                },
                                                {
                                                    table: {
                                                        title: '核定金额',
                                                        dataIndex: 'checkAmt',
                                                        key: 'checkAmt',
                                                        width: 150,
                                                    },
                                                    isInForm: false
                                                },
                                                {
                                                    table: {
                                                        title: '变更后数量',
                                                        dataIndex: 'quantity',
                                                        key: 'quantity',
                                                        width: 150,
                                                    },
                                                    isInForm: false
                                                },
                                                {
                                                    table: {
                                                        title: '变更后单价',
                                                        dataIndex: 'price',
                                                        key: 'price',
                                                        width: 150,
                                                    },
                                                    isInForm: false
                                                },
                                                {
                                                    table: {
                                                        title: '变更后金额',
                                                        dataIndex: 'changeAmt',
                                                        key: 'changeAmt',
                                                        width: 150,
                                                    },
                                                    isInForm: false
                                                },
                                                {
                                                    table: {
                                                        title: '备注',
                                                        dataIndex: 'remarks',
                                                        key: 'remarks',
                                                        width: 150,
                                                    },
                                                    isInForm: false
                                                },
                                                {
                                                    isInTable: false,
                                                    form: {
                                                        field: "BJXJList",
                                                        type: "qnnTable",
                                                        incToForm: true,
                                                        hide: (obj) => {
                                                            if (obj.clickCb.rowInfo.name === 'BJXJ') {
                                                                return false;
                                                            } else {
                                                                return true;
                                                            }
                                                        },
                                                        formItemLayout: {
                                                            labelCol: {
                                                                xs: { span: 0 },
                                                                sm: { span: 0 }
                                                            },
                                                            wrapperCol: {
                                                                xs: { span: 24 },
                                                                sm: { span: 24 }
                                                            }
                                                        },
                                                        qnnTableConfig: {
                                                            fetchConfig: {
                                                                apiName: 'getZxCtWorksTreeList',
                                                                otherParams: () => {
                                                                    let rowData = this.tableQD.getSelectedRows()?.[0];
                                                                    return {
                                                                        parentID: rowData?.id,
                                                                        orgID: rowData?.orgID
                                                                    }

                                                                }
                                                            },
                                                            actionBtnsPosition: "top",
                                                            antd: {
                                                                rowKey: 'id',
                                                                size: 'small'
                                                            },
                                                            paginationConfig: false,
                                                            pageSize: 999999,
                                                            wrappedComponentRef: (me) => {
                                                                this.tableBJXJ = me;
                                                            },
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
                                                                        title: '清单编号',
                                                                        dataIndex: 'workNo',
                                                                        key: 'workNo',
                                                                        tdEdit: true
                                                                    },
                                                                    form: {
                                                                        type: 'string',
                                                                        disabled: (obj) => {
                                                                            if (this.state.QDFlagData.status === '1') {
                                                                                return true;
                                                                            } else {
                                                                                return false;
                                                                            }
                                                                        },
                                                                        placeholder: '请输入'
                                                                    }
                                                                },
                                                                {
                                                                    table: {
                                                                        title: '清单名称',
                                                                        dataIndex: 'workName',
                                                                        key: 'workName',
                                                                        tdEdit: true
                                                                    },
                                                                    form: {
                                                                        type: 'string',
                                                                        disabled: (obj) => {
                                                                            if (this.state.QDFlagData.status === '1') {
                                                                                return true;
                                                                            } else {
                                                                                return false;
                                                                            }
                                                                        },
                                                                        placeholder: '请输入'
                                                                    }
                                                                },
                                                                {
                                                                    table: {
                                                                        title: '计量单位',
                                                                        dataIndex: 'unit',
                                                                        key: 'unit',
                                                                        tdEdit: true
                                                                    },
                                                                    form: {
                                                                        type: 'string',
                                                                        disabled: (obj) => {
                                                                            if (this.state.QDFlagData.status === '1') {
                                                                                return true;
                                                                            } else {
                                                                                return false;
                                                                            }
                                                                        },
                                                                        placeholder: '请输入'
                                                                    }
                                                                },
                                                                {
                                                                    table: {
                                                                        title: '工程量',
                                                                        dataIndex: 'contractQty',
                                                                        key: 'contractQty',
                                                                        tdEdit: true
                                                                    },
                                                                    form: {
                                                                        type: 'number',
                                                                        disabled: (obj) => {
                                                                            if (this.state.QDFlagData.status === '1') {
                                                                                return true;
                                                                            } else {
                                                                                return false;
                                                                            }
                                                                        },
                                                                        placeholder: '请输入'
                                                                    }
                                                                },
                                                                {
                                                                    table: {
                                                                        title: '合同单价',
                                                                        dataIndex: 'contractPrice',
                                                                        key: 'contractPrice',
                                                                        tdEdit: true
                                                                    },
                                                                    form: {
                                                                        type: 'number',
                                                                        disabled: (obj) => {
                                                                            if (this.state.QDFlagData.status === '1') {
                                                                                return true;
                                                                            } else {
                                                                                return false;
                                                                            }
                                                                        },
                                                                        placeholder: '请输入'
                                                                    }
                                                                },
                                                                {
                                                                    table: {
                                                                        title: '核定数量',
                                                                        dataIndex: 'checkQty',
                                                                        key: 'checkQty',
                                                                        tdEdit: true
                                                                    },
                                                                    form: {
                                                                        type: 'number',
                                                                        placeholder: '请输入'
                                                                    }
                                                                },
                                                                {
                                                                    table: {
                                                                        title: '备注',
                                                                        dataIndex: 'remarks',
                                                                        key: 'remarks',
                                                                        tdEdit: true
                                                                    },
                                                                    form: {
                                                                        type: 'string',
                                                                        placeholder: '请输入'
                                                                    }
                                                                }
                                                            ],
                                                            actionBtns: [
                                                                {
                                                                    name: "addRow",
                                                                    icon: "plus",
                                                                    type: "primary",
                                                                    label: "新增行",
                                                                    hide: () => {
                                                                        if (this.state.QDFlagData.status === '1') {
                                                                            return true;
                                                                        }
                                                                    }
                                                                },
                                                                {
                                                                    name: "save",
                                                                    type: "primary",
                                                                    label: "保存",
                                                                    onClick: (obj) => {
                                                                        confirm({
                                                                            content: '确定保存数据吗?',
                                                                            onOk: () => {
                                                                                let tableQDRowData = this.tableQD.btnCallbackFn.getSelectedRows()[0];
                                                                                obj.state.data = obj.state.data.map((item) => {
                                                                                    if (!item.addFlag) {
                                                                                        item.parentID = tableQDRowData.id;
                                                                                        item.addFlag = '1';
                                                                                        item.orgID = tableQDRowData.orgID;
                                                                                        item.treeNode = tableQDRowData.treeNode;
                                                                                        item.workBookID = tableQDRowData.workBookID;
                                                                                    }
                                                                                    return item;
                                                                                })
                                                                                let body = {
                                                                                    parentID: tableQDRowData.id,
                                                                                    treeNode: tableQDRowData.treeNode,
                                                                                    children: obj.state.data
                                                                                }
                                                                                this.props.myFetch('updateZxCtWorksList', body).then(
                                                                                    (treeObj) => {
                                                                                        if (treeObj.success) {
                                                                                            this.tableQD.btnCallbackFn.closeDrawer(false);
                                                                                            this.setState({
                                                                                                treeData: null
                                                                                            }, () => {
                                                                                                this.props.myFetch('getZxCtWorksWorkNameTree', { orgID: tableQDRowData.orgID }).then(
                                                                                                    ({ data, success, message }) => {
                                                                                                        if (success) {
                                                                                                            this.setState({
                                                                                                                treeData: data,
                                                                                                                defaultExpandedKeys: data.length ? [data[0].value] : [],
                                                                                                            }, () => {
                                                                                                                this.tableQD.refresh();
                                                                                                            })
                                                                                                        } else {
                                                                                                            Msg.error(message)
                                                                                                        }
                                                                                                    }
                                                                                                );
                                                                                            })
                                                                                        } else {
                                                                                            Msg.error(treeObj.message);
                                                                                        }
                                                                                    }
                                                                                );
                                                                            }
                                                                        });
                                                                    }
                                                                },
                                                                {
                                                                    name: 'del',
                                                                    icon: 'delete',
                                                                    type: 'danger',
                                                                    label: '删除',
                                                                    hide: () => {
                                                                        if (this.state.QDFlagData.status === '1') {
                                                                            return true;
                                                                        }
                                                                    }
                                                                }
                                                            ]
                                                        }
                                                    }
                                                },
                                                {
                                                    isInTable: false,
                                                    form: {
                                                        field: "PLBJList",
                                                        type: "qnnTable",
                                                        incToForm: true,
                                                        hide: (obj) => {
                                                            if (obj.clickCb.rowInfo.name === 'PLBJ') {
                                                                return false;
                                                            } else {
                                                                return true;
                                                            }
                                                        },
                                                        formItemLayout: {
                                                            labelCol: {
                                                                xs: { span: 0 },
                                                                sm: { span: 0 }
                                                            },
                                                            wrapperCol: {
                                                                xs: { span: 24 },
                                                                sm: { span: 24 }
                                                            }
                                                        },
                                                        qnnTableConfig: {
                                                            fetchConfig: {
                                                                apiName: 'getZxCtWorksTreeList',
                                                                otherParams: () => {
                                                                    return {
                                                                        parentID: this.state.valuePid === '-1' ? this.state.valuePid : this.state.value,
                                                                        orgID: props.initialValues.orgID,
                                                                        page: this.tableQD?.state?.curPage,
                                                                        limit: this.tableQD?.state?.limit
                                                                    }
                                                                }
                                                            },
                                                            getRowSelection: (obj) => {
                                                                return {
                                                                    getCheckboxProps: record => ({
                                                                        name: record.name,
                                                                        disabled: record.isLeaf === 0 || record.parentID === '-1'
                                                                    }),
                                                                }
                                                            },
                                                            actionBtnsPosition: "top",
                                                            antd: {
                                                                rowKey: 'id',
                                                                size: 'small'
                                                            },
                                                            paginationConfig: false,
                                                            wrappedComponentRef: (me) => {
                                                                this.tablePLBJ = me;
                                                            },
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
                                                                        title: '清单编号',
                                                                        dataIndex: 'workNo',
                                                                        key: 'workNo',
                                                                        tdEdit: true
                                                                    },
                                                                    form: {
                                                                        type: 'string',
                                                                        disabled: (obj) => {
                                                                            if (obj.record.parentID === '-1' || this.state.QDFlagData.status === '1') {
                                                                                return true;
                                                                            } else {
                                                                                return false;
                                                                            }
                                                                        },
                                                                        placeholder: '请输入'
                                                                    }
                                                                },
                                                                {
                                                                    table: {
                                                                        title: '清单名称',
                                                                        dataIndex: 'workName',
                                                                        key: 'workName',
                                                                        tdEdit: true
                                                                    },
                                                                    form: {
                                                                        type: 'string',
                                                                        disabled: (obj) => {
                                                                            if (obj.record.parentID === '-1' || this.state.QDFlagData.status === '1') {
                                                                                return true;
                                                                            } else {
                                                                                return false;
                                                                            }
                                                                        },
                                                                        placeholder: '请输入'
                                                                    }
                                                                },
                                                                {
                                                                    table: {
                                                                        title: '计量单位',
                                                                        dataIndex: 'unit',
                                                                        key: 'unit',
                                                                        tdEdit: true
                                                                    },
                                                                    form: {
                                                                        type: 'string',
                                                                        disabled: (obj) => {
                                                                            if (this.state.QDFlagData.status === '1') {
                                                                                return true;
                                                                            } else {
                                                                                return false;
                                                                            }
                                                                        },
                                                                        placeholder: '请输入'
                                                                    }
                                                                },
                                                                {
                                                                    table: {
                                                                        title: '工程量',
                                                                        dataIndex: 'contractQty',
                                                                        key: 'contractQty',
                                                                        tdEdit: true
                                                                    },
                                                                    form: {
                                                                        type: 'number',
                                                                        disabled: (obj) => {
                                                                            if (this.state.QDFlagData.status === '1') {
                                                                                return true;
                                                                            } else {
                                                                                return false;
                                                                            }
                                                                        },
                                                                        placeholder: '请输入'
                                                                    }
                                                                },
                                                                {
                                                                    table: {
                                                                        title: '合同单价',
                                                                        dataIndex: 'contractPrice',
                                                                        key: 'contractPrice',
                                                                        tdEdit: true
                                                                    },
                                                                    form: {
                                                                        type: 'number',
                                                                        disabled: (obj) => {
                                                                            if (this.state.QDFlagData.status === '1') {
                                                                                return true;
                                                                            } else {
                                                                                return false;
                                                                            }
                                                                        },
                                                                        placeholder: '请输入'
                                                                    }
                                                                },
                                                                {
                                                                    table: {
                                                                        title: '核定数量',
                                                                        dataIndex: 'checkQty',
                                                                        key: 'checkQty',
                                                                        tdEdit: true
                                                                    },
                                                                    form: {
                                                                        type: 'number',
                                                                        placeholder: '请输入'
                                                                    }
                                                                },
                                                                {
                                                                    table: {
                                                                        title: '备注',
                                                                        dataIndex: 'remarks',
                                                                        key: 'remarks',
                                                                        tdEdit: true
                                                                    },
                                                                    form: {
                                                                        type: 'string',
                                                                        placeholder: '请输入'
                                                                    }
                                                                }
                                                            ],
                                                            actionBtns: [
                                                                {
                                                                    name: "save",
                                                                    type: "primary",
                                                                    label: "保存",
                                                                    onClick: (obj) => {
                                                                        confirm({
                                                                            content: '确定保存数据吗?',
                                                                            onOk: () => {
                                                                                let delData = this.table.form.getFieldValue('delList');
                                                                                let tableQDRowData = this.tableQD.state.data[0];
                                                                                let body = {
                                                                                    orgID: tableQDRowData.orgID,
                                                                                    treeNode: tableQDRowData.treeNode,
                                                                                    children: delData?.length ? obj.state.data.concat(delData) : obj.state.data
                                                                                }
                                                                                this.props.myFetch('saveZxCtWorksList', body).then(
                                                                                    (treeObj) => {
                                                                                        if (treeObj.success) {
                                                                                            this.tableQD.btnCallbackFn.closeDrawer(false);
                                                                                            this.setState({
                                                                                                treeData: null
                                                                                            }, () => {
                                                                                                this.props.myFetch('getZxCtWorksWorkNameTree', { orgID: tableQDRowData.orgID }).then(
                                                                                                    ({ data, success, message }) => {
                                                                                                        if (success) {
                                                                                                            this.setState({
                                                                                                                treeData: data,
                                                                                                                defaultExpandedKeys: data.length ? [data[0].value] : [],
                                                                                                            }, () => {
                                                                                                                this.tableQD.refresh();
                                                                                                            })
                                                                                                        } else {
                                                                                                            Msg.error(message)
                                                                                                        }
                                                                                                    }
                                                                                                );
                                                                                            })
                                                                                        } else {
                                                                                            Msg.error(treeObj.message);
                                                                                        }
                                                                                    }
                                                                                );
                                                                            }
                                                                        });
                                                                    }
                                                                },
                                                                {
                                                                    name: 'del',
                                                                    icon: 'delete',
                                                                    type: 'danger',
                                                                    label: '删除',
                                                                    hide: () => {
                                                                        if (this.state.QDFlagData.status === '1') {
                                                                            return true;
                                                                        }
                                                                    },
                                                                    onClick: (obj) => {
                                                                        let delLists = this.table.form.getFieldValue('delList') || [];
                                                                        let delList = obj.selectedRows.map((item) => {
                                                                            item.addFlag = '2';
                                                                            return item;
                                                                        })
                                                                        delLists = delLists.concat(delList);
                                                                        this.table.form.setFieldsValue({
                                                                            delList: delLists
                                                                        })
                                                                        let tablePLBJData = this.tableQD.btnCallbackFn.form.getFieldValue('PLBJList');
                                                                        delLists = delLists.concat(tablePLBJData);
                                                                        this.props.myFetch('getZxCtWorksTreeList', { parentID: this.state.valuePid === '-1' ? this.state.valuePid : this.state.value, orgID: props.initialValues.orgID }).then(
                                                                            ({ data, success, message }) => {
                                                                                if (success) {
                                                                                    data = data.filter(item => !delLists.some(ele => ele.id === item.id));
                                                                                    tablePLBJData = tablePLBJData.map((item) => {
                                                                                        if (!data.filter((items) => item.id === items.parentID).length) {
                                                                                            item.isLeaf = 1;
                                                                                        }
                                                                                        return item;
                                                                                    })
                                                                                    this.tablePLBJ.btnCallbackFn.setState({
                                                                                        data: tablePLBJData
                                                                                    })
                                                                                } else {
                                                                                    Msg.error(message)
                                                                                }
                                                                            }
                                                                        );
                                                                    }
                                                                }
                                                            ]
                                                        }
                                                    }
                                                },
                                                {
                                                    isInTable: false,
                                                    form: {
                                                        field: "qnnForm",
                                                        type: "qnnForm",
                                                        hide: (obj) => {
                                                            if (obj.clickCb.rowInfo.name === 'BG') {
                                                                return false;
                                                            } else {
                                                                return true;
                                                            }
                                                        },
                                                        qnnFormConfig: {
                                                            formConfig: [
                                                                {
                                                                    field: 'id',
                                                                    type: 'string',
                                                                    hide: true,
                                                                },
                                                                {
                                                                    label: '清单编号',
                                                                    field: 'workNo',
                                                                    type: 'string',
                                                                    disabled: true,
                                                                    initialValue: (obj) => {
                                                                        if (obj.clickCb.selectedRows.length) {
                                                                            return obj.clickCb.selectedRows[0].workNo;
                                                                        }
                                                                    },
                                                                    placeholder: '请输入',
                                                                    span: 8
                                                                },
                                                                {
                                                                    label: '清单名称',
                                                                    field: 'workName',
                                                                    type: 'string',
                                                                    disabled: true,
                                                                    initialValue: (obj) => {
                                                                        if (obj.clickCb.selectedRows.length) {
                                                                            return obj.clickCb.selectedRows[0].workName;
                                                                        }
                                                                    },
                                                                    placeholder: '请输入',
                                                                    span: 8
                                                                },
                                                                {
                                                                    label: '计量单位',
                                                                    field: 'unit',
                                                                    type: 'string',
                                                                    disabled: true,
                                                                    initialValue: (obj) => {
                                                                        if (obj.clickCb.selectedRows.length) {
                                                                            return obj.clickCb.selectedRows[0].unit;
                                                                        }
                                                                    },
                                                                    placeholder: '请输入',
                                                                    span: 8
                                                                },
                                                                {
                                                                    label: '合同数量',
                                                                    field: 'contractQty',
                                                                    type: 'number',
                                                                    disabled: true,
                                                                    initialValue: (obj) => {
                                                                        if (obj.clickCb.selectedRows.length) {
                                                                            return obj.clickCb.selectedRows[0].contractQty;
                                                                        }
                                                                    },
                                                                    placeholder: '请输入',
                                                                    span: 8
                                                                },
                                                                {
                                                                    label: '合同单价',
                                                                    field: 'contractPrice',
                                                                    type: 'number',
                                                                    disabled: true,
                                                                    initialValue: (obj) => {
                                                                        if (obj.clickCb.selectedRows.length) {
                                                                            return obj.clickCb.selectedRows[0].contractPrice;
                                                                        }
                                                                    },
                                                                    placeholder: '请输入',
                                                                    span: 8
                                                                },
                                                                {
                                                                    label: '合同金额',
                                                                    field: 'contractAmt',
                                                                    type: 'number',
                                                                    disabled: true,
                                                                    initialValue: (obj) => {
                                                                        if (obj.clickCb.selectedRows.length) {
                                                                            return obj.clickCb.selectedRows[0].contractAmt;
                                                                        }
                                                                    },
                                                                    placeholder: '请输入',
                                                                    span: 8
                                                                },
                                                                {
                                                                    label: '变更后数量',
                                                                    field: 'quantity',
                                                                    type: 'number',
                                                                    disabled: true,
                                                                    initialValue: (obj) => {
                                                                        if (obj.clickCb.selectedRows.length) {
                                                                            return obj.clickCb.selectedRows[0].quantity;
                                                                        }
                                                                    },
                                                                    placeholder: '请输入',
                                                                    span: 8
                                                                },
                                                                {
                                                                    label: '变更后单价',
                                                                    field: 'price',
                                                                    type: 'number',
                                                                    disabled: true,
                                                                    initialValue: (obj) => {
                                                                        if (obj.clickCb.selectedRows.length) {
                                                                            return obj.clickCb.selectedRows[0].price;
                                                                        }
                                                                    },
                                                                    placeholder: '请输入',
                                                                    span: 8
                                                                },
                                                                {
                                                                    label: '变更后金额',
                                                                    field: 'changeAmt',
                                                                    type: 'number',
                                                                    disabled: true,
                                                                    initialValue: (obj) => {
                                                                        if (obj.clickCb.selectedRows.length) {
                                                                            return obj.clickCb.selectedRows[0].changeAmt;
                                                                        }
                                                                    },
                                                                    placeholder: '请输入',
                                                                    span: 8
                                                                },
                                                                {
                                                                    label: '备注',
                                                                    field: 'remarks',
                                                                    type: 'string',
                                                                    disabled: true,
                                                                    initialValue: (obj) => {
                                                                        if (obj.clickCb.selectedRows.length) {
                                                                            return obj.clickCb.selectedRows[0].remarks;
                                                                        }
                                                                    },
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
                                                            ],
                                                            btns: []
                                                        }
                                                    }
                                                },
                                                {
                                                    isInTable: false,
                                                    form: {
                                                        field: "BGList",
                                                        type: "qnnTable",
                                                        incToForm: true,
                                                        hide: (obj) => {
                                                            if (obj.clickCb.rowInfo.name === 'BG') {
                                                                return false;
                                                            } else {
                                                                return true;
                                                            }
                                                        },
                                                        formItemLayout: {
                                                            labelCol: {
                                                                xs: { span: 0 },
                                                                sm: { span: 0 }
                                                            },
                                                            wrapperCol: {
                                                                xs: { span: 24 },
                                                                sm: { span: 24 }
                                                            }
                                                        },
                                                        qnnTableConfig: {
                                                            fetchConfig: {
                                                                apiName: 'getZxCtWorkAlterSingleList',
                                                                otherParams: () => {
                                                                    let rowData = this.tableQD.btnCallbackFn.getSelectedRows()?.[0];
                                                                    return {
                                                                        workID: rowData.id
                                                                    }
                                                                }
                                                            },
                                                            actionBtnsPosition: "top",
                                                            antd: {
                                                                rowKey: 'id',
                                                                size: 'small'
                                                            },
                                                            drawerConfig: {
                                                                width: '1000px'
                                                            },
                                                            paginationConfig: {
                                                                position: 'bottom'
                                                            },
                                                            wrappedComponentRef: (me) => {
                                                                this.tableBG = me;
                                                            },
                                                            isShowRowSelect: false,
                                                            desc: <div style={{ color: "red" }}>变更历史</div>,
                                                            actionBtns: [
                                                                {
                                                                    name: 'XZQD',
                                                                    type: 'primary',
                                                                    label: '新增清单',
                                                                    disabled: () => {
                                                                        if (this.tableQD.btnCallbackFn.getSelectedRows().length) {
                                                                            let XZQDRowData = this.tableQD.btnCallbackFn.getSelectedRows()[0];
                                                                            if (XZQDRowData.isLeaf === 1) {
                                                                                return true;
                                                                            } else {
                                                                                return false;
                                                                            }
                                                                        }
                                                                    },
                                                                    onClick: (obj) => {
                                                                        obj.btnCallbackFn.setDrawerBtns([
                                                                            {
                                                                                field: 'xzqdCancel',
                                                                                name: "cancel",
                                                                                type: "dashed",
                                                                                label: "取消"
                                                                            },
                                                                            {
                                                                                field: 'xzqdSubmit',
                                                                                name: 'submit',//内置add del
                                                                                type: 'primary',//类型  默认 primary
                                                                                label: '保存',//提交数据并且关闭右边抽屉 
                                                                                fetchConfig: {//ajax配置
                                                                                    apiName: 'addZxCtWorksChange',
                                                                                },
                                                                                onClick: (obj) => {
                                                                                    if (obj.response.success) {
                                                                                        this.tableQD.btnCallbackFn.closeDrawer(false);
                                                                                        this.setState({
                                                                                            treeData: null
                                                                                        }, () => {
                                                                                            this.props.myFetch('getZxCtWorksWorkNameTree', { orgID: this.state.QDFlagData.orgID }).then(
                                                                                                ({ data, success, message }) => {
                                                                                                    if (success) {
                                                                                                        this.setState({
                                                                                                            treeData: data,
                                                                                                            defaultExpandedKeys: data.length ? [data[0].value] : [],
                                                                                                        }, () => {
                                                                                                            this.tableQD.refresh();
                                                                                                        })
                                                                                                    } else {
                                                                                                        Msg.error(message)
                                                                                                    }
                                                                                                }
                                                                                            );
                                                                                        })
                                                                                    }
                                                                                }
                                                                            }
                                                                        ])
                                                                        obj.btnCallbackFn.closeDrawer(true);
                                                                    }
                                                                },
                                                                {
                                                                    name: 'QDBG',
                                                                    type: 'primary',
                                                                    label: '清单变更',
                                                                    onClick: (obj) => {
                                                                        obj.btnCallbackFn.setDrawerBtns([
                                                                            {
                                                                                field: 'qdbgCancel',
                                                                                name: "cancel",
                                                                                type: "dashed",
                                                                                label: "取消"
                                                                            },
                                                                            {
                                                                                field: 'qdbgSubmit',
                                                                                name: 'submit',//内置add del
                                                                                type: 'primary',//类型  默认 primary
                                                                                label: '保存',//提交数据并且关闭右边抽屉 
                                                                                fetchConfig: {//ajax配置
                                                                                    apiName: 'addZxCtWorkAlterSingle',
                                                                                },
                                                                                onClick: (obj) => {
                                                                                    if (obj.response.success) {
                                                                                        this.tableQD.btnCallbackFn.closeDrawer(false);
                                                                                        this.setState({
                                                                                            treeData: null
                                                                                        }, () => {
                                                                                            this.props.myFetch('getZxCtWorksWorkNameTree', { orgID: this.state.QDFlagData.orgID }).then(
                                                                                                ({ data, success, message }) => {
                                                                                                    if (success) {
                                                                                                        this.setState({
                                                                                                            treeData: data,
                                                                                                            defaultExpandedKeys: data.length ? [data[0].value] : [],
                                                                                                        }, () => {
                                                                                                            this.tableQD.refresh();
                                                                                                        })
                                                                                                    } else {
                                                                                                        Msg.error(message)
                                                                                                    }
                                                                                                }
                                                                                            );
                                                                                        })
                                                                                    }
                                                                                }
                                                                            }
                                                                        ])
                                                                        obj.btnCallbackFn.closeDrawer(true);
                                                                    }
                                                                },
                                                                {
                                                                    name: 'QXBG',
                                                                    type: 'primary',
                                                                    label: '取消变更',
                                                                    onClick: (obj) => {
                                                                        confirm({
                                                                            content: '确认取消上一次变更吗?',
                                                                            onOk: () => {
                                                                                let QXBGRowData = this.tableQD.btnCallbackFn.getSelectedRows()[0];
                                                                                this.props.myFetch('zxCtCancelWorkAlterSingle', { workID: QXBGRowData.id }).then(
                                                                                    (QXBGObj) => {
                                                                                        if (QXBGObj.success) {
                                                                                            this.tableQD.btnCallbackFn.closeDrawer(false);
                                                                                            this.setState({
                                                                                                treeData: null
                                                                                            }, () => {
                                                                                                this.props.myFetch('getZxCtWorksWorkNameTree', { orgID: this.state.QDFlagData.orgID }).then(
                                                                                                    ({ data, success, message }) => {
                                                                                                        if (success) {
                                                                                                            this.setState({
                                                                                                                treeData: data,
                                                                                                                defaultExpandedKeys: data.length ? [data[0].value] : [],
                                                                                                            }, () => {
                                                                                                                this.tableQD.refresh();
                                                                                                            })
                                                                                                        } else {
                                                                                                            Msg.error(message)
                                                                                                        }
                                                                                                    }
                                                                                                );
                                                                                            })
                                                                                        } else {
                                                                                            Msg.error(QXBGObj.message)
                                                                                        }
                                                                                    }
                                                                                );
                                                                            }
                                                                        })
                                                                    }
                                                                }
                                                            ],
                                                            tabs: [
                                                                {
                                                                    field: "qdczQnnForm",
                                                                    name: "qnnForm",
                                                                    title: "清单操作",
                                                                    content: {
                                                                        formConfig: [
                                                                            {
                                                                                field: 'id',
                                                                                type: 'string',
                                                                                initialValue: (obj) => {
                                                                                    if (obj.clickCb.rowInfo.name === 'XZQD') {
                                                                                        if (this.tableQD.btnCallbackFn.getSelectedRows().length) {
                                                                                            let qdxzRowData = this.tableQD.btnCallbackFn.getSelectedRows()[0];
                                                                                            return qdxzRowData.id
                                                                                        }
                                                                                    }
                                                                                },
                                                                                hide: true,
                                                                            },
                                                                            {
                                                                                field: 'workBookID',
                                                                                type: 'string',
                                                                                initialValue: (obj) => {
                                                                                    if (obj.clickCb.rowInfo.name === 'XZQD') {
                                                                                        if (this.tableQD.btnCallbackFn.getSelectedRows().length) {
                                                                                            let qdxzRowData = this.tableQD.btnCallbackFn.getSelectedRows()[0];
                                                                                            return qdxzRowData.workBookID
                                                                                        }
                                                                                    }
                                                                                },
                                                                                hide: true,
                                                                            },
                                                                            {
                                                                                field: 'orgID',
                                                                                type: 'string',
                                                                                initialValue: (obj) => {
                                                                                    if (obj.clickCb.rowInfo.name === 'XZQD') {
                                                                                        if (this.tableQD.btnCallbackFn.getSelectedRows().length) {
                                                                                            let qdxzRowData = this.tableQD.btnCallbackFn.getSelectedRows()[0];
                                                                                            return qdxzRowData.orgID
                                                                                        }
                                                                                    }
                                                                                },
                                                                                hide: true,
                                                                            },
                                                                            {
                                                                                field: 'treeNode',
                                                                                type: 'string',
                                                                                initialValue: (obj) => {
                                                                                    if (obj.clickCb.rowInfo.name === 'XZQD') {
                                                                                        if (this.tableQD.btnCallbackFn.getSelectedRows().length) {
                                                                                            let qdxzRowData = this.tableQD.btnCallbackFn.getSelectedRows()[0];
                                                                                            return qdxzRowData.treeNode
                                                                                        }
                                                                                    }
                                                                                },
                                                                                hide: true,
                                                                            },
                                                                            {
                                                                                field: 'workID',
                                                                                type: 'string',
                                                                                initialValue: (obj) => {
                                                                                    if (obj.clickCb.rowInfo.name === 'QDBG') {
                                                                                        if (this.tableQD.btnCallbackFn.getSelectedRows().length) {
                                                                                            let qdxzRowData = this.tableQD.btnCallbackFn.getSelectedRows()[0];
                                                                                            return qdxzRowData.id
                                                                                        }
                                                                                    }
                                                                                },
                                                                                hide: true,
                                                                            },
                                                                            {
                                                                                field: 'originalQuantity',
                                                                                type: 'number',
                                                                                initialValue: (obj) => {
                                                                                    if (obj.clickCb.rowInfo.name === 'QDBG') {
                                                                                        if (this.tableQD.btnCallbackFn.getSelectedRows().length) {
                                                                                            let qdxzRowData = this.tableQD.btnCallbackFn.getSelectedRows()[0];
                                                                                            return qdxzRowData.contractQty
                                                                                        }
                                                                                    }
                                                                                },
                                                                                hide: true,
                                                                            },
                                                                            {
                                                                                field: 'originalPrice',
                                                                                type: 'number',
                                                                                initialValue: (obj) => {
                                                                                    if (obj.clickCb.rowInfo.name === 'QDBG') {
                                                                                        if (this.tableQD.btnCallbackFn.getSelectedRows().length) {
                                                                                            let qdxzRowData = this.tableQD.btnCallbackFn.getSelectedRows()[0];
                                                                                            return qdxzRowData.contractPrice
                                                                                        }
                                                                                    }
                                                                                },
                                                                                hide: true,
                                                                            },
                                                                            {
                                                                                label: '清单编号',
                                                                                field: 'workNo',
                                                                                type: 'string',
                                                                                placeholder: '请输入',
                                                                                required: true,
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
                                                                                initialValue: (obj) => {
                                                                                    if (obj.clickCb.rowInfo.name === 'QDBG') {
                                                                                        if (this.tableQD.btnCallbackFn.getSelectedRows().length) {
                                                                                            let qdxzRowData = this.tableQD.btnCallbackFn.getSelectedRows()[0];
                                                                                            return qdxzRowData.workNo
                                                                                        }
                                                                                    }
                                                                                },
                                                                                span: 8
                                                                            },
                                                                            {
                                                                                label: '清单名称',
                                                                                field: 'workName',
                                                                                type: 'string',
                                                                                required: true,
                                                                                placeholder: '请输入',
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
                                                                                initialValue: (obj) => {
                                                                                    if (obj.clickCb.rowInfo.name === 'QDBG') {
                                                                                        if (this.tableQD.btnCallbackFn.getSelectedRows().length) {
                                                                                            let qdxzRowData = this.tableQD.btnCallbackFn.getSelectedRows()[0];
                                                                                            return qdxzRowData.workName
                                                                                        }
                                                                                    }
                                                                                },
                                                                                span: 8
                                                                            },
                                                                            {
                                                                                label: '计量单位',
                                                                                field: 'unit',
                                                                                type: 'string',
                                                                                placeholder: '请输入',
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
                                                                                initialValue: (obj) => {
                                                                                    if (obj.clickCb.rowInfo.name === 'QDBG') {
                                                                                        if (this.tableQD.btnCallbackFn.getSelectedRows().length) {
                                                                                            let qdxzRowData = this.tableQD.btnCallbackFn.getSelectedRows()[0];
                                                                                            return qdxzRowData.unit
                                                                                        }
                                                                                    }
                                                                                },
                                                                                span: 8
                                                                            },
                                                                            {
                                                                                label: '变更后数量',
                                                                                field: 'quantity',
                                                                                type: 'number',
                                                                                dependencies: ["isLeaf"],
                                                                                disabled: (obj) => {
                                                                                    if (obj.clickCb.rowInfo.name === 'XZQD') {
                                                                                        if (obj.form.getFieldValue("isLeaf") === 1) {
                                                                                            return false;
                                                                                        } else {
                                                                                            return true;
                                                                                        }
                                                                                    }
                                                                                    if (obj.clickCb.rowInfo.name === 'QDBG') {
                                                                                        if (this.tableQD.btnCallbackFn.getSelectedRows().length) {
                                                                                            let qdxzRowData = this.tableQD.btnCallbackFn.getSelectedRows()[0];
                                                                                            if (qdxzRowData.isLeaf === 1) {
                                                                                                return false;
                                                                                            } else {
                                                                                                return true;
                                                                                            }
                                                                                        }
                                                                                    }
                                                                                },
                                                                                onChange: (val, obj) => {
                                                                                    let changeData = obj.form.getFieldsValue();
                                                                                    obj.form.setFieldsValue({
                                                                                        changeAmt: (val ? val : 0) * (changeData.price ? changeData.price : 0)
                                                                                    })
                                                                                },
                                                                                initialValue: (obj) => {
                                                                                    if (obj.clickCb.rowInfo.name === 'QDBG') {
                                                                                        if (this.tableQD.btnCallbackFn.getSelectedRows().length) {
                                                                                            let qdxzRowData = this.tableQD.btnCallbackFn.getSelectedRows()[0];
                                                                                            return qdxzRowData.quantity
                                                                                        }
                                                                                    } else {
                                                                                        return 0;
                                                                                    }
                                                                                },
                                                                                placeholder: '请输入',
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
                                                                                span: 8
                                                                            },
                                                                            {
                                                                                label: '变更后单价',
                                                                                field: 'price',
                                                                                type: 'number',
                                                                                dependencies: ["isLeaf"],
                                                                                disabled: (obj) => {
                                                                                    if (obj.clickCb.rowInfo.name === 'XZQD') {
                                                                                        if (obj.form.getFieldValue("isLeaf") === 1) {
                                                                                            return false;
                                                                                        } else {
                                                                                            return true;
                                                                                        }
                                                                                    }
                                                                                    if (obj.clickCb.rowInfo.name === 'QDBG') {
                                                                                        if (this.tableQD.btnCallbackFn.getSelectedRows().length) {
                                                                                            let qdxzRowData = this.tableQD.btnCallbackFn.getSelectedRows()[0];
                                                                                            if (qdxzRowData.isLeaf === 1) {
                                                                                                return false;
                                                                                            } else {
                                                                                                return true;
                                                                                            }
                                                                                        }
                                                                                    }
                                                                                },
                                                                                onChange: (val, obj) => {
                                                                                    let changeData = obj.form.getFieldsValue();
                                                                                    obj.form.setFieldsValue({
                                                                                        changeAmt: (val ? val : 0) * (changeData.quantity ? changeData.quantity : 0)
                                                                                    })
                                                                                },
                                                                                initialValue: (obj) => {
                                                                                    if (obj.clickCb.rowInfo.name === 'QDBG') {
                                                                                        if (this.tableQD.btnCallbackFn.getSelectedRows().length) {
                                                                                            let qdxzRowData = this.tableQD.btnCallbackFn.getSelectedRows()[0];
                                                                                            return qdxzRowData.price
                                                                                        }
                                                                                    } else {
                                                                                        return 0;
                                                                                    }
                                                                                },
                                                                                placeholder: '请输入',
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
                                                                                span: 8
                                                                            },
                                                                            {
                                                                                label: '变更后金额',
                                                                                field: 'changeAmt',
                                                                                type: 'number',
                                                                                disabled: true,
                                                                                placeholder: '请输入',
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
                                                                                initialValue: (obj) => {
                                                                                    if (obj.clickCb.rowInfo.name === 'QDBG') {
                                                                                        if (this.tableQD.btnCallbackFn.getSelectedRows().length) {
                                                                                            let qdxzRowData = this.tableQD.btnCallbackFn.getSelectedRows()[0];
                                                                                            return (qdxzRowData.quantity ? qdxzRowData.quantity : 0) * (qdxzRowData.price ? qdxzRowData.price : 0)
                                                                                        }
                                                                                    } else {
                                                                                        return 0;
                                                                                    }
                                                                                },
                                                                                span: 8
                                                                            },
                                                                            {
                                                                                label: '变更人',
                                                                                field: 'alterPerson',
                                                                                type: 'string',
                                                                                disabled: true,
                                                                                initialValue: realName,
                                                                                placeholder: '请输入',
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
                                                                                span: 8
                                                                            },
                                                                            {
                                                                                label: '变更时间',
                                                                                field: 'alterDate',
                                                                                type: 'date',
                                                                                initialValue: new Date(),
                                                                                placeholder: '请选择',
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
                                                                                span: 8
                                                                            },
                                                                            {
                                                                                label: '是否叶子节点',
                                                                                field: 'isLeaf',
                                                                                type: 'radio',
                                                                                optionData: [
                                                                                    {
                                                                                        label: "否",
                                                                                        value: 0
                                                                                    },
                                                                                    {
                                                                                        label: "是",
                                                                                        value: 1
                                                                                    }
                                                                                ],
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
                                                                                onChange: (val, obj) => {
                                                                                    if (!val.length) {
                                                                                        obj.form.setFieldsValue({
                                                                                            quantity: 0,
                                                                                            price: 0,
                                                                                            changeAmt: 0
                                                                                        })
                                                                                    }
                                                                                },
                                                                                hide: (obj) => {
                                                                                    if (obj.clickCb.rowInfo.name === 'QDBG') {
                                                                                        return true
                                                                                    } else {
                                                                                        return false;
                                                                                    }
                                                                                },
                                                                                initialValue: (obj) => {
                                                                                    if (obj.clickCb.rowInfo.name === 'QDBG') {
                                                                                        if (this.tableQD.btnCallbackFn.getSelectedRows().length) {
                                                                                            let qdxzRowData = this.tableQD.btnCallbackFn.getSelectedRows()[0];
                                                                                            return qdxzRowData.isLeaf
                                                                                        }
                                                                                    } else {
                                                                                        return 0;
                                                                                    }
                                                                                },
                                                                                span: 8
                                                                            },
                                                                            {
                                                                                label: '说明',
                                                                                field: 'information',
                                                                                type: 'string',
                                                                                placeholder: '请输入',
                                                                                initialValue: (obj) => {
                                                                                    if (obj.clickCb.rowInfo.name === 'QDBG') {
                                                                                        if (this.tableQD.btnCallbackFn.getSelectedRows().length) {
                                                                                            let qdxzRowData = this.tableQD.btnCallbackFn.getSelectedRows()[0];
                                                                                            return qdxzRowData.information
                                                                                        }
                                                                                    }
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
                                                                            },
                                                                            {
                                                                                type: 'component',
                                                                                field: 'component',
                                                                                Component: obj => {
                                                                                    return (
                                                                                        <div style={{ color: 'red', textAlign: 'center' }}>请注意：没有勾选“是否叶子节点”则可以在该节点下继续新增子节点，反之则不能！</div>
                                                                                    );
                                                                                },
                                                                                hide: (obj) => {
                                                                                    if (obj.clickCb.rowInfo.name === 'QDBG') {
                                                                                        return true
                                                                                    } else {
                                                                                        return false;
                                                                                    }
                                                                                },
                                                                            }
                                                                        ]
                                                                    }
                                                                },
                                                            ],
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
                                                                        title: '类型',
                                                                        dataIndex: 'alterType',
                                                                        key: 'alterType'
                                                                    },
                                                                    isInForm: false
                                                                },
                                                                {
                                                                    table: {
                                                                        title: '清单编号',
                                                                        dataIndex: 'workNo',
                                                                        key: 'workNo'
                                                                    },
                                                                    isInForm: false
                                                                },
                                                                {
                                                                    table: {
                                                                        title: '清单名称',
                                                                        dataIndex: 'workName',
                                                                        key: 'workName'
                                                                    },
                                                                    isInForm: false
                                                                },
                                                                {
                                                                    table: {
                                                                        title: '原来数量',
                                                                        dataIndex: 'originalQuantity',
                                                                        key: 'originalQuantity'
                                                                    },
                                                                    isInForm: false
                                                                },
                                                                {
                                                                    table: {
                                                                        title: '原来单价',
                                                                        dataIndex: 'originalPrice',
                                                                        key: 'originalPrice'
                                                                    },
                                                                    isInForm: false
                                                                },
                                                                {
                                                                    table: {
                                                                        title: '变更后数量',
                                                                        dataIndex: 'quantity',
                                                                        key: 'quantity'
                                                                    },
                                                                    isInForm: false
                                                                },
                                                                {
                                                                    table: {
                                                                        title: '变更后单价',
                                                                        dataIndex: 'price',
                                                                        key: 'price'
                                                                    },
                                                                    isInForm: false
                                                                },
                                                                {
                                                                    table: {
                                                                        title: '变更时间',
                                                                        dataIndex: 'alterDate',
                                                                        key: 'alterDate',
                                                                        format: 'YYYY-MM-DD'
                                                                    },
                                                                    isInForm: false
                                                                },
                                                                {
                                                                    table: {
                                                                        title: '编辑时间',
                                                                        dataIndex: 'editTime',
                                                                        key: 'editTime',
                                                                        format: 'YYYY-MM-DD HH:mm:ss'
                                                                    },
                                                                    isInForm: false
                                                                }
                                                            ]
                                                        }
                                                    }
                                                }
                                            ]}
                                        /> : null}
                                    </div>
                                </div>;
                            }
                        },
                        {
                            field: "table2",
                            name: "qnnTable",
                            title: "合同变更",
                            disabled: (obj) => {
                                if (obj.clickCb.rowInfo.name === 'add') {
                                    return true;
                                } else {
                                    return false;
                                }
                            },
                            content: {
                                tabs: [],
                                fetchConfig: {
                                    apiName: 'getZxCtAlterList',
                                    otherParams: (obj) => {
                                        let rowData = obj.initialValues;
                                        if (rowData) {
                                            return { orgID: rowData.orgID };
                                        }
                                    },
                                },
                                wrappedComponentRef: (me) => {
                                    this.tableBG = me;
                                },
                                drawerConfig: {
                                    width: window.innerWidth * 0.8
                                },
                                antd: {
                                    rowKey: 'id',
                                    size: 'small'
                                },
                                paginationConfig: {
                                    position: "bottom"
                                },
                                isShowRowSelect: true,
                                actionBtns: [
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
                                                    apiName: 'addZxCtAlter',
                                                }
                                            }
                                        ]
                                    },
                                    {
                                        name: 'edit',//内置add del
                                        icon: 'edit',//icon
                                        type: 'primary',//类型  默认 primary  [primary dashed danger]
                                        label: '修改',
                                        onClick: (obj) => {
                                            if (obj.selectedRows[0].auditStatus === '2') {
                                                obj.btnCallbackFn.closeDrawer(false);
                                                Msg.error('已审核通过数据不可修改！');
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
                                                    apiName: 'updateZxCtAlter',
                                                },
                                                onClick: (obj) => {
                                                    obj.btnCallbackFn.clearSelectedRows();
                                                }
                                            }
                                        ]
                                    },
                                    {
                                        name: 'audit',
                                        type: 'primary',//类型  默认 primary  [primary dashed danger]
                                        label: '审核',
                                        disabled: (obj) => {
                                            if (obj.btnCallbackFn.getSelectedRows().length) {
                                                return false;
                                            } else {
                                                return true;
                                            }
                                        },
                                        onClick: (obj) => {
                                            if (obj.selectedRows.length === 1) {
                                                if (obj.selectedRows[0].auditStatus === '0') {
                                                    this.props.myFetch('zxCtAlterAudit', obj.selectedRows[0]).then(
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
                                                } else {
                                                    Msg.error('已审核通过数据无需再次审核！');
                                                    obj.btnCallbackFn.clearSelectedRows();
                                                }
                                            } else {
                                                Msg.error('请选择一条数据！');
                                                obj.btnCallbackFn.clearSelectedRows();
                                            }
                                        }
                                    },
                                    // {
                                    //     name: 'Reported',
                                    //     type: 'primary',//类型  默认 primary  [primary dashed danger]
                                    //     label: '上报',
                                    //     disabled: (obj) => {
                                    //         if (obj.btnCallbackFn.getSelectedRows().length) {
                                    //             return false;
                                    //         } else {
                                    //             return true;
                                    //         }
                                    //     },
                                    //     onClick: (obj) => {
                                    //         if (obj.selectedRows.length === 1) {
                                    //             if(obj.selectedRows[0].auditStatus === '2'){
                                    //                 this.props.myFetch('zxCtAlterReporting', obj.selectedRows[0]).then(
                                    //                     ({ success, message }) => {
                                    //                         if (success) {
                                    //                             Msg.success(message);
                                    //                             obj.btnCallbackFn.clearSelectedRows();
                                    //                             obj.btnCallbackFn.refresh();
                                    //                         } else {
                                    //                             Msg.error(message);
                                    //                         }
                                    //                     }
                                    //                 );
                                    //             }else{
                                    //                 Msg.error('已审核数据才能上报！');
                                    //                 obj.btnCallbackFn.clearSelectedRows();
                                    //             }
                                    //         } else {
                                    //             Msg.error('请选择一条数据！');
                                    //             obj.btnCallbackFn.clearSelectedRows();
                                    //         }
                                    //     }
                                    // },
                                    {
                                        name: 'del',//内置add del
                                        icon: 'delete',//icon
                                        type: 'danger',//类型  默认 primary  [primary dashed danger]
                                        label: '删除',
                                        isCanSubmit: (obj, callback) => {
                                            for (let i = 0; i < obj.selectedRows.length; i++) {
                                                if (obj.selectedRows[i].auditStatus === '2') {
                                                    Msg.error('已审核通过数据不可删除！');
                                                    callback(false);
                                                    break;
                                                } else if (i === obj.selectedRows.length - 1) {
                                                    callback(true);
                                                }
                                            }
                                        },
                                        fetchConfig: {//ajax配置
                                            apiName: 'batchDeleteUpdateZxCtAlter',
                                        },
                                    }
                                ],
                                formConfig: [
                                    {
                                        isInTable: false,
                                        form: {
                                            field: 'orgID',
                                            type: 'string',
                                            initialValue: (obj) => {
                                                return obj.clickCb.selectedRows[0].orgID;
                                            },
                                            hide: true,
                                        }
                                    },
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
                                            field: 'workBookID',
                                            type: 'string',
                                            hide: true,
                                        }
                                    },
                                    {
                                        isInTable: false,
                                        form: {
                                            field: 'auditStatus',
                                            type: 'string',
                                            hide: true,
                                        }
                                    },
                                    {
                                        table: {
                                            title: '提出单位',
                                            dataIndex: 'proposer',
                                            key: 'proposer',
                                            width: 150,
                                            filter: true,
                                            fixed: 'left',
                                            onClick: "detail"
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
                                            title: '申报文号',
                                            dataIndex: 'applyNo',
                                            key: 'applyNo',
                                            width: 150,
                                            filter: true,
                                            fixed: 'left'
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
                                            title: '申报金额',
                                            dataIndex: 'applyAmount',
                                            key: 'applyAmount',
                                            width: 100,
                                        },
                                        form: {
                                            type: 'number',
                                            placeholder: '请输入',
                                            spanForm: 8
                                        }
                                    },
                                    {
                                        isInTable: false,
                                        form: {
                                            label: '申报日期',
                                            field: 'applyDate',
                                            type: 'date',
                                            initialValue: new Date(),
                                            placeholder: '请选择',
                                            spanForm: 8
                                        }
                                    },
                                    {
                                        isInTable: false,
                                        form: {
                                            label: '变更等级',
                                            field: 'alterLevel',
                                            type: 'select',
                                            optionConfig: {
                                                label: 'itemName', //默认 label
                                                value: 'itemId'
                                            },
                                            fetchConfig: {
                                                apiName: 'getBaseCodeSelect',
                                                otherParams: {
                                                    itemId: 'bianGengDengJi'
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
                                            label: '发生日期',
                                            field: 'happenDate',
                                            type: 'date',
                                            initialValue: new Date(),
                                            placeholder: '请选择',
                                            spanForm: 8
                                        }
                                    },
                                    {
                                        isInTable: false,
                                        form: {
                                            label: '需要公司协助',
                                            field: 'companyHelp',
                                            type: 'radio',
                                            optionData: [
                                                {
                                                    label: "否",
                                                    value: "0",
                                                },
                                                {
                                                    label: "是",
                                                    value: "1",
                                                }
                                            ],
                                            placeholder: '请选择',
                                            spanForm: 8
                                        }
                                    },
                                    {
                                        table: {
                                            title: '变更内容',
                                            dataIndex: 'alterContent',
                                            key: 'alterContent',
                                            width: 150,
                                        },
                                        form: {
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
                                            label: '变更原因',
                                            field: 'alterReason',
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
                                            label: '驻地监理确认金额',
                                            field: 'confirmatAmountOfResidentSupervision',
                                            type: 'number',
                                            placeholder: '请输入',
                                            spanForm: 8
                                        }
                                    },
                                    {
                                        isInTable: false,
                                        form: {
                                            label: '驻地监理确认日期',
                                            field: 'confirmatDateOfResidentSupervisor',
                                            type: 'date',
                                            placeholder: '请选择',
                                            spanForm: 8
                                        }
                                    },
                                    {
                                        isInTable: false,
                                        form: {
                                            label: '总监办确认金额',
                                            field: 'amountConfirmedByDirectorOffice',
                                            type: 'number',
                                            placeholder: '请输入',
                                            spanForm: 8
                                        }
                                    },
                                    {
                                        isInTable: false,
                                        form: {
                                            label: '总监办确认日期',
                                            field: 'dateConfirmedByDirectorOffice',
                                            type: 'date',
                                            placeholder: '请选择',
                                            spanForm: 8
                                        }
                                    },
                                    {
                                        table: {
                                            title: '批复文号',
                                            dataIndex: 'replyNo',
                                            key: 'replyNo',
                                            width: 150,
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
                                            label: '业主批复日期',
                                            field: 'replyDate',
                                            type: 'date',
                                            placeholder: '请选择',
                                            spanForm: 8
                                        }
                                    },
                                    {
                                        table: {
                                            title: '业主批复金额',
                                            dataIndex: 'replyAmount',
                                            key: 'replyAmount',
                                            width: 120,
                                        },
                                        form: {
                                            type: 'number',
                                            placeholder: '请输入',
                                            spanForm: 8
                                        }
                                    },
                                    {
                                        table: {
                                            title: '业主批复状态',
                                            dataIndex: 'replyStatus',
                                            key: 'replyStatus',
                                            width: 120,
                                            type: 'select'
                                        },
                                        form: {
                                            type: 'select',
                                            initialValue: (obj) => {
                                                return obj.clickCb.selectedRows[0].replyStatus;
                                            },
                                            optionConfig: {
                                                label: 'itemName', //默认 label
                                                value: 'itemId'
                                            },
                                            fetchConfig: {
                                                apiName: 'getBaseCodeSelect',
                                                otherParams: {
                                                    itemId: 'yeZhuPiFuZhuangTai'
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
                                            title: '审核状态',
                                            dataIndex: 'auditStatus',
                                            key: 'auditStatus',
                                            width: 100,
                                            render: (data) => {
                                                if (data === '0') {
                                                    return '未审核';
                                                } else {
                                                    return '已审核';
                                                }
                                            }
                                        },
                                        isInForm: false
                                    },
                                    {
                                        table: {
                                            title: '备注',
                                            dataIndex: 'remarks',
                                            key: 'remarks',
                                            width: 100,
                                        },
                                        form: {
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
                                            field: 'attachment',
                                            type: "files",
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
                                ]
                            }
                        },
                        {
                            field: "table3",
                            name: "qnnTable",
                            title: "索赔",
                            disabled: (obj) => {
                                if (obj.clickCb.rowInfo.name === 'add') {
                                    return true;
                                } else {
                                    return false;
                                }
                            },
                            content: {
                                tabs: [],
                                fetchConfig: {
                                    apiName: 'getZxCtContrClaimList',
                                    otherParams: (obj) => {
                                        let rowData = obj.initialValues;
                                        if (rowData) {
                                            return { contractID: rowData.id };
                                        }
                                    },
                                },
                                wrappedComponentRef: (me) => {
                                    this.tableSP = me;
                                },
                                drawerConfig: {
                                    width: window.innerWidth * 0.8
                                },
                                antd: {
                                    rowKey: 'id',
                                    size: 'small'
                                },
                                paginationConfig: {
                                    position: "bottom"
                                },
                                isShowRowSelect: true,
                                actionBtns: [
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
                                                    apiName: 'addZxCtContrClaim',
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
                                                    apiName: 'updateZxCtContrClaim',
                                                },
                                                onClick: (obj) => {
                                                    obj.btnCallbackFn.clearSelectedRows();
                                                }
                                            }
                                        ]
                                    },
                                    {
                                        name: 'del',//内置add del
                                        icon: 'delete',//icon
                                        type: 'danger',//类型  默认 primary  [primary dashed danger]
                                        label: '删除',
                                        fetchConfig: {//ajax配置
                                            apiName: 'batchDeleteUpdateZxCtContrClaim',
                                        },
                                    }
                                ],
                                formConfig: [
                                    {
                                        isInTable: false,
                                        form: {
                                            field: 'contractID',
                                            type: 'string',
                                            initialValue: (obj) => {
                                                return obj.clickCb.selectedRows[0].id;
                                            },
                                            hide: true,
                                        }
                                    },
                                    {
                                        isInTable: false,
                                        form: {
                                            field: 'orgID',
                                            type: 'string',
                                            initialValue: (obj) => {
                                                return obj.clickCb.selectedRows[0].orgID;
                                            },
                                            hide: true,
                                        }
                                    },
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
                                            title: '申报文号',
                                            dataIndex: 'applyNo',
                                            key: 'applyNo',
                                            width: 150,
                                            filter: true,
                                            fixed: 'left',
                                            onClick: "detail"
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
                                            title: '要求索赔方',
                                            dataIndex: 'proposer',
                                            key: 'proposer',
                                            width: 150,
                                            filter: true,
                                            fixed: 'left'
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
                                            title: '主要内容',
                                            dataIndex: 'alterContent',
                                            key: 'alterContent',
                                            width: 150,
                                        },
                                        form: {
                                            type: 'textarea',
                                            required: true,
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
                                            label: '主要原因',
                                            field: 'alterReason',
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
                                        table: {
                                            title: '发生日期',
                                            dataIndex: 'happenDate',
                                            key: 'happenDate',
                                            width: 100,
                                            format: 'YYYY-MM-DD',
                                        },
                                        form: {
                                            type: 'date',
                                            initialValue: new Date(),
                                            placeholder: '请选择',
                                            spanForm: 8
                                        }
                                    },
                                    {
                                        table: {
                                            title: '申报金额',
                                            dataIndex: 'applyAmount',
                                            key: 'applyAmount',
                                            width: 100,
                                        },
                                        form: {
                                            type: 'number',
                                            placeholder: '请输入',
                                            spanForm: 8
                                        }
                                    },
                                    {
                                        table: {
                                            title: '申报日期',
                                            dataIndex: 'applyDate',
                                            key: 'applyDate',
                                            width: 100,
                                            format: 'YYYY-MM-DD',
                                        },
                                        form: {
                                            type: 'date',
                                            initialValue: new Date(),
                                            placeholder: '请选择',
                                            spanForm: 8
                                        }
                                    },
                                    {
                                        isInTable: false,
                                        form: {
                                            label: '申报延期天数',
                                            field: 'applyDelay',
                                            type: 'string',
                                            placeholder: '请输入',
                                            spanForm: 8
                                        }
                                    },
                                    {
                                        isInTable: false,
                                        form: {
                                            label: '驻地监理确认金额',
                                            field: 'confirmatAmountOfResidentSupervision',
                                            type: 'number',
                                            placeholder: '请输入',
                                            spanForm: 8
                                        }
                                    },
                                    {
                                        isInTable: false,
                                        form: {
                                            label: '驻地监理确认日期',
                                            field: 'confirmatDateOfResidentSupervisor',
                                            type: 'date',
                                            placeholder: '请选择',
                                            spanForm: 8
                                        }
                                    },
                                    {
                                        isInTable: false,
                                        form: {
                                            label: '总监办确认金额',
                                            field: 'amountConfirmedByDirectorOffice',
                                            type: 'number',
                                            placeholder: '请输入',
                                            spanForm: 8
                                        }
                                    },
                                    {
                                        isInTable: false,
                                        form: {
                                            label: '总监办确认日期',
                                            field: 'dateConfirmedByDirectorOffice',
                                            type: 'date',
                                            placeholder: '请选择',
                                            spanForm: 8
                                        }
                                    },
                                    {
                                        table: {
                                            title: '批复金额',
                                            dataIndex: 'replyAmount',
                                            key: 'replyAmount',
                                            width: 100
                                        },
                                        form: {
                                            type: 'number',
                                            placeholder: '请输入',
                                            spanForm: 8
                                        }
                                    },
                                    {
                                        table: {
                                            title: '批复日期',
                                            dataIndex: 'replyDate',
                                            key: 'replyDate',
                                            format: 'YYYY-MM-DD',
                                            width: 100
                                        },
                                        form: {
                                            type: 'date',
                                            initialValue: new Date(),
                                            placeholder: '请输入',
                                            spanForm: 8
                                        }
                                    },
                                    {
                                        table: {
                                            title: '批复文号',
                                            dataIndex: 'replyNo',
                                            key: 'replyNo',
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
                                            label: '批复延期天数',
                                            field: 'replyDelay',
                                            type: 'string',
                                            placeholder: '请输入',
                                            spanForm: 8
                                        }
                                    },
                                    {
                                        table: {
                                            title: '业主批复状态',
                                            dataIndex: 'replyStatus',
                                            key: 'replyStatus',
                                            width: 120,
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
                                                    itemId: 'yeZhuPiFuZhuangTai'
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
                                        isInTable: false,
                                        form: {
                                            label: '需要公司协助',
                                            field: 'companyHelp',
                                            type: 'radio',
                                            optionData: [
                                                {
                                                    label: "否",
                                                    value: "0",
                                                },
                                                {
                                                    label: "是",
                                                    value: "1",
                                                }
                                            ],
                                            placeholder: '请选择',
                                            spanForm: 8
                                        }
                                    },
                                    {
                                        table: {
                                            title: '索赔工期',
                                            dataIndex: 'claimPeriod',
                                            key: 'claimPeriod',
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
                                            title: '备注',
                                            dataIndex: 'remarks',
                                            key: 'remarks',
                                            width: 100,
                                        },
                                        form: {
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
                                            type: 'qnnTable',
                                            field: 'contrClaimItemList',
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
                                                            title: '索赔明细编号',
                                                            dataIndex: 'claimDetailNo',
                                                            key: 'claimDetailNo',
                                                            tdEdit: true
                                                        },
                                                        form: {
                                                            type: 'string',
                                                            placeholder: '请输入',
                                                        },
                                                    },
                                                    {
                                                        table: {
                                                            title: '索赔明细名称',
                                                            dataIndex: 'claimDetailName',
                                                            key: 'claimDetailName',
                                                            tdEdit: true
                                                        },
                                                        form: {
                                                            type: 'string',
                                                            placeholder: '请输入',
                                                        },
                                                    },
                                                    {
                                                        table: {
                                                            title: '内容',
                                                            dataIndex: 'claimDetailContent',
                                                            key: 'claimDetailContent',
                                                            tdEdit: true
                                                        },
                                                        form: {
                                                            type: 'string',
                                                            placeholder: '请输入',
                                                        },
                                                    },
                                                    {
                                                        table: {
                                                            title: '备注',
                                                            dataIndex: 'remarks',
                                                            key: 'remarks',
                                                            tdEdit: true
                                                        },
                                                        form: {
                                                            type: 'string',
                                                            placeholder: '请输入',
                                                        },
                                                    }
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
                                            label: '附件',
                                            field: 'attachment',
                                            type: "files",
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
                                ]
                            }
                        },
                        {
                            field: "table4",
                            name: "qnnTable",
                            title: "产值",
                            disabled: (obj) => {
                                if (obj.clickCb.rowInfo.name === 'add') {
                                    return true;
                                } else {
                                    return false;
                                }
                            },
                            content: {
                                tabs: [],
                                fetchConfig: {
                                    apiName: 'getZxCtProduceAmtCalList',
                                    otherParams: (obj) => {
                                        let rowData = obj.initialValues;
                                        if (rowData) {
                                            return { orgID: rowData.orgID };
                                        }
                                    },
                                },
                                wrappedComponentRef: (me) => {
                                    this.tableCZ = me;
                                },
                                drawerConfig: {
                                    width: window.innerWidth * 0.8
                                },
                                antd: {
                                    rowKey: 'id',
                                    size: 'small'
                                },
                                paginationConfig: {
                                    position: "bottom"
                                },
                                isShowRowSelect: true,
                                actionBtns: [
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
                                                    apiName: 'addZxCtProduceAmtCal',
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
                                                    apiName: 'updateZxCtProduceAmtCal',
                                                },
                                                onClick: (obj) => {
                                                    obj.btnCallbackFn.clearSelectedRows();
                                                }
                                            }
                                        ]
                                    },
                                    {
                                        name: 'del',//内置add del
                                        icon: 'delete',//icon
                                        type: 'danger',//类型  默认 primary  [primary dashed danger]
                                        label: '删除',
                                        fetchConfig: {//ajax配置
                                            apiName: 'batchDeleteUpdateZxCtProduceAmtCal',
                                        },
                                    }
                                ],
                                formConfig: [
                                    {
                                        isInTable: false,
                                        form: {
                                            field: 'id',
                                            type: 'string',
                                            hide: true,
                                        }
                                    },
                                    // {
                                    //     isInTable: false,
                                    //     form: {
                                    //         field: 'period',
                                    //         type: 'string',
                                    //         hide: true,
                                    //     }
                                    // },
                                    {
                                        isInTable: false,
                                        form: {
                                            field: 'orgID',
                                            type: 'string',
                                            initialValue: (obj) => {
                                                return obj.clickCb.selectedRows[0].orgID;
                                            },
                                            hide: true,
                                        }
                                    },
                                    {
                                        table: {
                                            title: '项目名称',
                                            dataIndex: 'orgName',
                                            key: 'orgName',
                                            filter: true,
                                            onClick: "detail"
                                        },
                                        form: {
                                            type: 'string',
                                            addDisabled: true,
                                            editDisabled: true,
                                            initialValue: (obj) => {
                                                return obj?.clickCb?.selectedRows?.[0]?.orgName;
                                            },
                                            placeholder: '请输入',
                                            spanForm: 8
                                        }
                                    },
                                    {
                                        table: {
                                            title: '期次',
                                            dataIndex: 'period',
                                            key: 'period'
                                        },
                                        form: {
                                            field:'periodDate',
                                            type: 'month',
                                            required: true,
                                            placeholder: '请选择',
                                            spanForm: 8
                                        }
                                    },
                                    {
                                        table: {
                                            title: '产值',
                                            dataIndex: 'produceAmt',
                                            key: 'produceAmt'
                                        },
                                        form: {
                                            type: 'number',
                                            placeholder: '请输入',
                                            spanForm: 8
                                        }
                                    },
                                    {
                                        table: {
                                            title: '登记人',
                                            dataIndex: 'reporter',
                                            key: 'reporter'
                                        },
                                        form: {
                                            type: 'string',
                                            initialValue: realName,
                                            placeholder: '请输入',
                                            spanForm: 8
                                        }
                                    },
                                    {
                                        table: {
                                            title: '备注',
                                            dataIndex: 'remarks',
                                            key: 'remarks'
                                        },
                                        form: {
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
                                            field: 'attachment',
                                            type: "files",
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
                                ]
                            }
                        },
                        {
                            field: "table5",
                            name: "qnnTable",
                            title: "计量",
                            disabled: (obj) => {
                                if (obj.clickCb.rowInfo.name === 'add') {
                                    return true;
                                } else {
                                    return false;
                                }
                            },
                            content: {
                                fetchConfig: {
                                    apiName: 'getZxCtBalanceList',
                                    otherParams: (obj) => {
                                        let rowData = obj.initialValues;
                                        if (rowData) {
                                            return { orgID: rowData.orgID };
                                        }
                                    },
                                },
                                wrappedComponentRef: (me) => {
                                    this.tableJL = me;
                                },
                                drawerConfig: {
                                    width: window.innerWidth * 0.8
                                },
                                antd: {
                                    rowKey: 'id',
                                    size: 'small'
                                },
                                paginationConfig: {
                                    position: "bottom"
                                },
                                isShowRowSelect: true,
                                actionBtns: [
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
                                                    apiName: 'addZxCtBalance',
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
                                                hide: (obj) => {
                                                    if (obj.btnCallbackFn.getActiveKey() === '0') {
                                                        return false;
                                                    } else {
                                                        return true;
                                                    }
                                                }
                                            },
                                            {
                                                name: 'submit',//内置add del
                                                type: 'primary',//类型  默认 primary
                                                label: '保存',//提交数据并且关闭右边抽屉 
                                                fetchConfig: {//ajax配置
                                                    apiName: 'updateZxCtBalance',
                                                },
                                                hide: (obj) => {
                                                    if (obj.btnCallbackFn.getActiveKey() === '0') {
                                                        return false;
                                                    } else {
                                                        return true;
                                                    }
                                                },
                                                onClick: (obj) => {
                                                    obj.btnCallbackFn.clearSelectedRows();
                                                }
                                            }
                                        ]
                                    },
                                    {
                                        name: 'del',//内置add del
                                        icon: 'delete',//icon
                                        type: 'danger',//类型  默认 primary  [primary dashed danger]
                                        label: '删除',
                                        fetchConfig: {//ajax配置
                                            apiName: 'batchDeleteUpdateZxCtBalance',
                                        },
                                    }
                                ],
                                formItemLayout: {
                                    labelCol: {
                                        xs: { span: 10 },
                                        sm: { span: 10 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 14 },
                                        sm: { span: 14 }
                                    }
                                },
                                tabs: [
                                    {
                                        field: "form1",
                                        name: "qnnForm",
                                        title: "基础信息",
                                        content: {
                                            formConfig: [
                                                {
                                                    field: 'id',
                                                    type: 'string',
                                                    hide: true,
                                                },
                                                {
                                                    field: 'orgID',
                                                    type: 'string',
                                                    initialValue: (obj) => {
                                                        return obj.clickCb.selectedRows[0].orgID;
                                                    },
                                                    hide: true,
                                                },
                                                {
                                                    label: '编号',
                                                    field: 'balNo',
                                                    type: 'string',
                                                    required: true,
                                                    placeholder: '请输入',
                                                    span: 8
                                                },
                                                {
                                                    label: '期次',
                                                    field: 'caption',
                                                    type: 'string',
                                                    required: true,
                                                    placeholder: '请输入',
                                                    span: 8
                                                },
                                                {
                                                    label: '计量时间',
                                                    field: 'period',
                                                    type: 'select',
                                                    optionConfig: {
                                                        label: 'label',
                                                        value: 'value',
                                                    },
                                                    optionData: [
                                                        {
                                                            label: "202012",
                                                            value: "202012"
                                                        },
                                                        {
                                                            label: "202011",
                                                            value: "202011"
                                                        },
                                                        {
                                                            label: "202010",
                                                            value: "202010"
                                                        },
                                                        {
                                                            label: "202009",
                                                            value: "202009"
                                                        }
                                                    ],
                                                    condition: [
                                                        {
                                                            regex: {
                                                                id: ['!', undefined],
                                                            },
                                                            action: 'disabled',
                                                        },
                                                        {
                                                            regex: {
                                                                id: ['!', ''],
                                                            },
                                                            action: 'disabled',
                                                        },
                                                        {
                                                            regex: {
                                                                id: ['!', null],
                                                            },
                                                            action: 'disabled',
                                                        }
                                                    ],
                                                    allowClear: false,
                                                    showSearch: true,
                                                    required: true,
                                                    placeholder: '请选择',
                                                    span: 8
                                                },
                                                {
                                                    label: '申请日期',
                                                    field: 'applyDate',
                                                    type: 'date',
                                                    initialValue: new Date(),
                                                    placeholder: '请选择',
                                                    span: 8
                                                },
                                                {
                                                    label: '申请金额',
                                                    field: 'applyamt',
                                                    type: 'number',
                                                    required: true,
                                                    placeholder: '请输入',
                                                    span: 8
                                                },
                                                {
                                                    label: '驻地监理确认金额',
                                                    field: 'stationRlyamt',
                                                    type: 'number',
                                                    placeholder: '请输入',
                                                    span: 8
                                                },
                                                {
                                                    label: '驻地监理确认日期',
                                                    field: 'stationRlyDate',
                                                    type: 'date',
                                                    initialValue: new Date(),
                                                    placeholder: '请选择',
                                                    span: 8
                                                },
                                                {
                                                    label: '总监确认金额',
                                                    field: 'majorRlyamt',
                                                    type: 'number',
                                                    placeholder: '请输入',
                                                    span: 8
                                                },
                                                {
                                                    label: '总监办确认日期',
                                                    field: 'majorRlyDate',
                                                    type: 'date',
                                                    initialValue: new Date(),
                                                    placeholder: '请选择',
                                                    span: 8
                                                },
                                                {
                                                    label: '业主批复日期',
                                                    field: 'ownerRlyDate',
                                                    type: 'date',
                                                    initialValue: new Date(),
                                                    placeholder: '请选择',
                                                    span: 8
                                                },
                                                {
                                                    label: '业主批复金额',
                                                    field: 'ownerRlyamt',
                                                    type: 'number',
                                                    placeholder: '请输入',
                                                    span: 8
                                                },
                                                {
                                                    label: '业务日期',
                                                    field: 'createDate',
                                                    type: 'date',
                                                    initialValue: new Date(),
                                                    required: true,
                                                    placeholder: '请选择',
                                                    span: 8
                                                },
                                                {
                                                    label: '记录人',
                                                    field: 'reportPerson',
                                                    type: 'string',
                                                    addDisabled: true,
                                                    editDisabled: true,
                                                    initialValue: realName,
                                                    placeholder: '请输入',
                                                    span: 8
                                                },
                                                {
                                                    label: '说明',
                                                    field: 'remarks',
                                                    type: 'string',
                                                    placeholder: '请输入',
                                                    span: 8
                                                },
                                                {
                                                    label: '附件',
                                                    field: 'attachment',
                                                    type: "files",
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
                                            ]
                                        }
                                    },
                                    {
                                        field: "tableqdjl",
                                        name: "qnnTable",
                                        title: "清单计量",
                                        disabled: (obj) => {
                                            if (obj.initialValues && obj.initialValues.id) {
                                                return false;
                                            } else {
                                                return true;
                                            }
                                        },
                                        content: {
                                            wrappedComponentRef: (me) => {
                                                this.tableQDJL = me;
                                            },
                                            fetchConfig: {
                                                apiName: 'getZxCtWorksBalanceList',
                                                otherParams: (obj) => {
                                                    let rowData = obj.initialValues;
                                                    if (rowData) {
                                                        return { parentID: '-1', orgID: rowData.orgID, balID: rowData.id };
                                                    }
                                                },
                                                success: (obj) => {
                                                    if (obj.data && obj.data.length) {
                                                        let expandedRowKeys = [];
                                                        let loopFn = (data) => {
                                                            for (var i = 0; i < data.length; i++) {
                                                                expandedRowKeys.push(data[i].id);
                                                                if (data[i].children) {
                                                                    loopFn(data[i].children)
                                                                }
                                                            }
                                                            return expandedRowKeys;
                                                        }
                                                        expandedRowKeys = loopFn(obj.data);
                                                        let setState = this.tableQDJL.btnCallbackFn.setState;
                                                        setState({
                                                            expandedRowKeys: [],
                                                        }, () => {
                                                            setState({
                                                                expandedRowKeys: expandedRowKeys
                                                            })
                                                        })
                                                    }
                                                }
                                            },
                                            drawerConfig: {
                                                width: window.innerWidth * 0.8
                                            },
                                            antd: {
                                                rowKey: 'id',
                                                size: 'small'
                                            },
                                            paginationConfig: false,
                                            pageSize: 9999,
                                            isShowRowSelect: false,
                                            actionBtns: [
                                                {
                                                    name: 'add',//内置add del
                                                    icon: 'edit',//icon
                                                    type: 'primary',//类型  默认 primary  [primary dashed danger]
                                                    label: '编辑',
                                                    hide: () => {
                                                        if (this.tableJL.getSelectedRows().length) {
                                                            return false;
                                                        }
                                                        return true;
                                                    },
                                                    drawerTitle: "编辑",
                                                    formBtns: []
                                                }
                                            ],
                                            tabs: [
                                                {
                                                    field: "tableqdjlbj",
                                                    name: "qnnTable",
                                                    title: "清单计量编辑",
                                                    content: {
                                                        fetchConfig: {
                                                            apiName: 'getZxCtWorksBalanceEditList',
                                                            otherParams: (obj) => {
                                                                console.log(obj);
                                                                let rowData = obj.parentProps.parentProps.initialValues;
                                                                if (rowData) {
                                                                    return { orgID: rowData.orgID, balID: rowData.id };
                                                                }
                                                            },
                                                        },
                                                        wrappedComponentRef: (me) => {
                                                            this.tableQDJLBJ = me;
                                                        },
                                                        antd: {
                                                            rowKey: 'id',
                                                            size: 'small',
                                                            scroll: {
                                                                y: window.innerHeight - 280
                                                            }
                                                        },
                                                        isShowRowSelect: false,
                                                        paginationConfig: false,
                                                        pageSize: 9999,
                                                        actionBtns: [
                                                            {
                                                                name: 'QDJLBJsubmit',//内置add del
                                                                type: 'primary',//类型  默认 primary
                                                                label: '保存',//提交数据并且关闭右边抽屉 
                                                                onClick: (obj) => {
                                                                    let setBtnsLoading = obj.btnCallbackFn.setBtnsLoading;
                                                                    setBtnsLoading('add', 'QDJLBJsubmit');
                                                                    this.props.myFetch('updateZxCtWorksBalanceList', this.tableQDJLBJ.state.data).then(({ success, message, data }) => {
                                                                        if (success) {
                                                                            setBtnsLoading('remove', 'QDJLBJsubmit');
                                                                            this.tableQDJL.btnCallbackFn.closeDrawer(false);
                                                                            this.tableQDJL.btnCallbackFn.refresh();
                                                                        } else {
                                                                            setBtnsLoading('remove', 'QDJLBJsubmit');
                                                                            Msg.error(message);
                                                                        }
                                                                    });
                                                                }
                                                            }
                                                        ],
                                                        formConfig: [
                                                            {
                                                                isInTable: false,
                                                                form: {
                                                                    label: '主键ID',
                                                                    type: 'id',
                                                                    hide: true
                                                                }
                                                            },
                                                            {
                                                                table: {
                                                                    title: '编号',
                                                                    dataIndex: 'workNo',
                                                                    key: 'workNo',
                                                                    width: 150,
                                                                    fixed: 'left'
                                                                },
                                                                form: {
                                                                    type: 'string',
                                                                    placeholder: '请输入'
                                                                }
                                                            },
                                                            {
                                                                table: {
                                                                    title: '名称',
                                                                    dataIndex: 'workName',
                                                                    key: 'workName',
                                                                    width: 150,
                                                                    fixed: 'left'
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
                                                                    width: 150,
                                                                },
                                                                form: {
                                                                    type: 'string',
                                                                    placeholder: '请输入'
                                                                }
                                                            },
                                                            {
                                                                table: {
                                                                    title: '合同单价',
                                                                    dataIndex: 'contractPrice',
                                                                    key: 'contractPrice',
                                                                    width: 100,
                                                                },
                                                                form: {
                                                                    type: 'number',
                                                                    placeholder: '请输入'
                                                                }
                                                            },
                                                            {
                                                                table: {
                                                                    title: '核定工程数量',
                                                                    dataIndex: 'checkQty',
                                                                    key: 'checkQty',
                                                                    width: 150,
                                                                },
                                                                form: {
                                                                    type: 'number',
                                                                    placeholder: '请输入'
                                                                }
                                                            },
                                                            {
                                                                table: {
                                                                    title: '核定工程金额',
                                                                    dataIndex: 'checkAmt',
                                                                    key: 'checkAmt',
                                                                    width: 150,
                                                                },
                                                                form: {
                                                                    type: 'number',
                                                                    placeholder: '请输入'
                                                                }
                                                            },
                                                            {
                                                                table: {
                                                                    title: '变更后数量',
                                                                    dataIndex: 'quantity',
                                                                    key: 'quantity',
                                                                    width: 100,
                                                                },
                                                                form: {
                                                                    type: 'number',
                                                                    placeholder: '请输入'
                                                                }
                                                            },
                                                            {
                                                                table: {
                                                                    title: '变更后单价',
                                                                    dataIndex: 'price',
                                                                    key: 'price',
                                                                    width: 100,
                                                                },
                                                                form: {
                                                                    type: 'number',
                                                                    placeholder: '请输入'
                                                                }
                                                            },
                                                            {
                                                                table: {
                                                                    title: '变更后金额',
                                                                    dataIndex: 'changeAmt',
                                                                    key: 'changeAmt',
                                                                    width: 100,
                                                                },
                                                                form: {
                                                                    type: 'number',
                                                                    placeholder: '请输入'
                                                                }
                                                            },
                                                            {
                                                                table: {
                                                                    title: '上期末计量数量',
                                                                    dataIndex: 'lastTotalQty',
                                                                    key: 'lastTotalQty',
                                                                    width: 150,
                                                                },
                                                                form: {
                                                                    type: 'number',
                                                                    placeholder: '请输入'
                                                                }
                                                            },
                                                            {
                                                                table: {
                                                                    title: '上期末计量金额',
                                                                    dataIndex: 'lastTotalAmt',
                                                                    key: 'lastTotalAmt',
                                                                    width: 150,
                                                                },
                                                                form: {
                                                                    type: 'number',
                                                                    placeholder: '请输入'
                                                                }
                                                            },
                                                            {
                                                                table: {
                                                                    title: '计量单价',
                                                                    dataIndex: 'price',
                                                                    key: 'price',
                                                                    width: 150,
                                                                },
                                                                form: {
                                                                    type: 'number',
                                                                    placeholder: '请输入'
                                                                }
                                                            },
                                                            {
                                                                table: {
                                                                    title: '合同内计量数量',
                                                                    dataIndex: 'balQty',
                                                                    key: 'balQty',
                                                                    width: 150,
                                                                    tdEdit: true
                                                                },
                                                                form: {
                                                                    type: 'number',
                                                                    onChange: (val, obj, btnCallbackFn) => {
                                                                        let tableQDJLBJIndex = Number(this.tableQDJLBJ.foucesField.split('_')[1]);
                                                                        let tableQDJLBJData = this.tableQDJLBJ.state.data.map((item, index) => {
                                                                            if (tableQDJLBJIndex === index) {
                                                                                item = {
                                                                                    ...item,
                                                                                    balQty: val,
                                                                                    balAmt: (item.price ? item.price : 0) * (val ? val : 0) + (item.price ? item.price : 0) * (item.balAltQty ? item.balAltQty : 0) + item.changeAltAmt ? item.changeAltAmt : 0,
                                                                                    thisTotalAmt: (item.price ? item.price : 0) * (val ? val : 0) + (item.price ? item.price : 0) * (item.balAltQty ? item.balAltQty : 0) + item.changeAltAmt ? item.changeAltAmt : 0,
                                                                                }
                                                                            }
                                                                            return item;
                                                                        });
                                                                        this.tableQDJLBJ.btnCallbackFn.setState({
                                                                            data: tableQDJLBJData
                                                                        })
                                                                    },
                                                                    placeholder: '请输入'
                                                                }
                                                            },
                                                            {
                                                                table: {
                                                                    title: '合同外计量数量',
                                                                    dataIndex: 'balAltQty',
                                                                    key: 'balAltQty',
                                                                    width: 150,
                                                                    tdEdit: true
                                                                },
                                                                form: {
                                                                    type: 'number',
                                                                    onChange: (val, obj, btnCallbackFn) => {
                                                                        let tableQDJLBJIndex = Number(this.tableQDJLBJ.foucesField.split('_')[1]);
                                                                        let tableQDJLBJData = this.tableQDJLBJ.state.data.map((item, index) => {
                                                                            if (tableQDJLBJIndex === index) {
                                                                                item = {
                                                                                    ...item,
                                                                                    balAltQty: val,
                                                                                    balAmt: (item.price ? item.price : 0) * (item.balQty ? item.balQty : 0) + (item.price ? item.price : 0) * (val ? val : 0) + item.changeAltAmt ? item.changeAltAmt : 0,
                                                                                    thisTotalAmt: (item.price ? item.price : 0) * (item.balQty ? item.balQty : 0) + (item.price ? item.price : 0) * (val ? val : 0) + item.changeAltAmt ? item.changeAltAmt : 0,
                                                                                }
                                                                            }
                                                                            return item;
                                                                        });
                                                                        this.tableQDJLBJ.btnCallbackFn.setState({
                                                                            data: tableQDJLBJData
                                                                        })
                                                                    },
                                                                    placeholder: '请输入'
                                                                }
                                                            },
                                                            {
                                                                table: {
                                                                    title: '合同增补金额',
                                                                    dataIndex: 'changeAltAmt',
                                                                    key: 'changeAltAmt',
                                                                    width: 150,
                                                                    tdEdit: true
                                                                },
                                                                form: {
                                                                    type: 'number',
                                                                    onChange: (val, obj, btnCallbackFn) => {
                                                                        let tableQDJLBJIndex = Number(this.tableQDJLBJ.foucesField.split('_')[1]);
                                                                        let tableQDJLBJData = this.tableQDJLBJ.state.data.map((item, index) => {
                                                                            if (tableQDJLBJIndex === index) {
                                                                                item = {
                                                                                    ...item,
                                                                                    changeAltAmt: val,
                                                                                    balAmt: (item.price ? item.price : 0) * (item.balQty ? item.balQty : 0) + (item.price ? item.price : 0) * (item.balAltQty ? item.balAltQty : 0) + val ? val : 0,
                                                                                    thisTotalAmt: (item.price ? item.price : 0) * (item.balQty ? item.balQty : 0) + (item.price ? item.price : 0) * (item.balAltQty ? item.balAltQty : 0) + val ? val : 0,
                                                                                }
                                                                            }
                                                                            return item;
                                                                        });
                                                                        this.tableQDJLBJ.btnCallbackFn.setState({
                                                                            data: tableQDJLBJData
                                                                        })
                                                                    },
                                                                    placeholder: '请输入'
                                                                }
                                                            },
                                                            {
                                                                table: {
                                                                    title: '本期计量金额',
                                                                    dataIndex: 'balAmt',
                                                                    key: 'balAmt',
                                                                    width: 150
                                                                },
                                                                form: {
                                                                    type: 'number',
                                                                    placeholder: '请输入'
                                                                }
                                                            },
                                                            {
                                                                table: {
                                                                    title: '本期末计量金额',
                                                                    dataIndex: 'thisTotalAmt',
                                                                    key: 'thisTotalAmt',
                                                                    width: 150,
                                                                },
                                                                form: {
                                                                    type: 'number',
                                                                    placeholder: '请输入'
                                                                }
                                                            }
                                                        ]
                                                    }
                                                }
                                            ],
                                            formConfig: [
                                                {
                                                    isInTable: false,
                                                    form: {
                                                        label: '主键ID',
                                                        type: 'id',
                                                        hide: true
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '编号',
                                                        dataIndex: 'workNo',
                                                        key: 'workNo',
                                                        width: 150,
                                                        fixed: 'left'
                                                    },
                                                    form: {
                                                        type: 'string',
                                                        placeholder: '请输入'
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '名称',
                                                        dataIndex: 'workName',
                                                        key: 'workName',
                                                        width: 150,
                                                        fixed: 'left',
                                                        onClick: function (obj) {
                                                            var btnCallbackFn = obj.btnCallbackFn;
                                                            var fetch = btnCallbackFn.fetch;
                                                            var getSearchParams = btnCallbackFn.getSearchParams;
                                                            var setState = btnCallbackFn.setState;
                                                            var msg = btnCallbackFn.msg;
                                                            var rowData = obj.rowData;
                                                            var oldData = obj.state.data;
                                                            var expandedRowKeys = obj.state.expandedRowKeys;
                                                            getSearchParams(function (vals) {
                                                                var parentID = rowData.id;

                                                                //子集存在的话就不在去请求了
                                                                if (rowData.children && rowData.children.length) {

                                                                    if (!expandedRowKeys.includes(parentID)) {
                                                                        //展开节点
                                                                        expandedRowKeys.push(parentID);
                                                                        setState({
                                                                            expandedRowKeys: [],
                                                                        }, () => {
                                                                            setState({
                                                                                expandedRowKeys: expandedRowKeys,
                                                                            })
                                                                        })
                                                                    } else {
                                                                        //收缩节点
                                                                        var index = expandedRowKeys.indexOf(parentID);
                                                                        expandedRowKeys.splice(index, 1)
                                                                        setState({
                                                                            expandedRowKeys: [],
                                                                        }, () => {
                                                                            setState({
                                                                                expandedRowKeys: expandedRowKeys,
                                                                            })
                                                                        })
                                                                    }
                                                                    return;
                                                                }


                                                                //去请求子集数据 然后递归数据放入
                                                                msg.loading("loading...");
                                                                fetch('getZxCtWorksBalanceList', { parentID: parentID, ...vals, orgID: rowData.orgID, balID: rowData.id }, function (res) {
                                                                    msg.destroy();
                                                                    var success = res.success;
                                                                    var childrenData = res.data;
                                                                    var message = res.message;
                                                                    if (!childrenData.length) {
                                                                        msg.info("该节点没有子集数据")
                                                                        return;
                                                                    }
                                                                    if (success) {
                                                                        //递归循环
                                                                        var loopFn = function (data) {
                                                                            for (var i = 0; i < data.length; i++) {
                                                                                if (data[i].id === parentID) {
                                                                                    data[i].children = childrenData;
                                                                                } else if (data[i].children) {
                                                                                    data[i].children = loopFn(data[i].children)
                                                                                }
                                                                            }
                                                                            return data;
                                                                        }
                                                                        oldData = loopFn(oldData);
                                                                        if (!expandedRowKeys.includes(parentID)) {
                                                                            expandedRowKeys.push(parentID);
                                                                        }
                                                                        setState({
                                                                            expandedRowKeys: expandedRowKeys,
                                                                            data: oldData,
                                                                        }, () => {
                                                                            setState({
                                                                                expandedRowKeys: expandedRowKeys,
                                                                            })
                                                                        })
                                                                    } else {
                                                                        msg.error(message)
                                                                    }
                                                                })
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
                                                        title: '单位',
                                                        dataIndex: 'unit',
                                                        key: 'unit',
                                                        width: 100,
                                                    },
                                                    form: {
                                                        type: 'string',
                                                        placeholder: '请输入'
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '合同单价',
                                                        dataIndex: 'contractPrice',
                                                        key: 'contractPrice',
                                                        width: 100,
                                                    },
                                                    form: {
                                                        type: 'number',
                                                        placeholder: '请输入'
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '核定工程数量',
                                                        dataIndex: 'checkQty',
                                                        key: 'checkQty',
                                                        width: 150,
                                                    },
                                                    form: {
                                                        type: 'number',
                                                        placeholder: '请输入'
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '核定工程金额',
                                                        dataIndex: 'checkAmt',
                                                        key: 'checkAmt',
                                                        width: 150,
                                                    },
                                                    form: {
                                                        type: 'number',
                                                        placeholder: '请输入'
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '变更后数量',
                                                        dataIndex: 'quantity',
                                                        key: 'quantity',
                                                        width: 100,
                                                    },
                                                    form: {
                                                        type: 'number',
                                                        placeholder: '请输入'
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '变更后单价',
                                                        dataIndex: 'price',
                                                        key: 'price',
                                                        width: 100,
                                                    },
                                                    form: {
                                                        type: 'number',
                                                        placeholder: '请输入'
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '变更后金额',
                                                        dataIndex: 'changeAmt',
                                                        key: 'changeAmt',
                                                        width: 100,
                                                    },
                                                    form: {
                                                        type: 'number',
                                                        placeholder: '请输入'
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '上期末计量数量',
                                                        dataIndex: 'lastTotalQty',
                                                        key: 'lastTotalQty',
                                                        width: 150,
                                                    },
                                                    form: {
                                                        type: 'number',
                                                        placeholder: '请输入'
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '上期末计量金额',
                                                        dataIndex: 'lastTotalAmt',
                                                        key: 'lastTotalAmt',
                                                        width: 150,
                                                    },
                                                    form: {
                                                        type: 'number',
                                                        placeholder: '请输入'
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '合同内计量数量',
                                                        dataIndex: 'balQty',
                                                        key: 'balQty',
                                                        width: 150,
                                                        style: {
                                                            color: 'red'
                                                        }
                                                    },
                                                    form: {
                                                        type: 'number',
                                                        placeholder: '请输入'
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '合同外计量数量',
                                                        dataIndex: 'balAltQty',
                                                        key: 'balAltQty',
                                                        width: 150,
                                                        style: {
                                                            color: 'red'
                                                        }
                                                    },
                                                    form: {
                                                        type: 'number',
                                                        placeholder: '请输入'
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '合同增补金额',
                                                        dataIndex: 'changeAltAmt',
                                                        key: 'changeAltAmt',
                                                        width: 150,
                                                        style: {
                                                            color: 'red'
                                                        }
                                                    },
                                                    form: {
                                                        type: 'number',
                                                        placeholder: '请输入'
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '本期计量金额',
                                                        dataIndex: 'balAmt',
                                                        key: 'balAmt',
                                                        width: 150,
                                                        style: {
                                                            color: 'red'
                                                        }
                                                    },
                                                    form: {
                                                        type: 'number',
                                                        placeholder: '请输入'
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '本期末计量金额',
                                                        dataIndex: 'thisTotalAmt',
                                                        key: 'thisTotalAmt',
                                                        width: 150
                                                    },
                                                    form: {
                                                        type: 'number',
                                                        placeholder: '请输入'
                                                    }
                                                }
                                            ]
                                        }
                                    },
                                    {
                                        field: "tablezfxjl",
                                        name: "qnnTable",
                                        title: "支付项计量",
                                        disabled: (obj) => {
                                            if (obj.initialValues && obj.initialValues.id) {
                                                return false;
                                            } else {
                                                return true;
                                            }
                                        },
                                        content: {
                                            formConfig: [
                                                {
                                                    isInTable: false,
                                                    form: {
                                                        label: '主键ID',
                                                        type: 'id',
                                                        hide: true
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '编号',
                                                        dataIndex: '1',
                                                        key: '1',
                                                        fixed: 'left'
                                                    },
                                                    form: {
                                                        type: 'string',
                                                        placeholder: '请输入'
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '名称',
                                                        dataIndex: '2',
                                                        key: '2',
                                                        fixed: 'left'
                                                    },
                                                    form: {
                                                        type: 'string',
                                                        placeholder: '请输入'
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '是否暂扣款',
                                                        dataIndex: '3',
                                                        key: '3'
                                                    },
                                                    form: {
                                                        type: 'string',
                                                        placeholder: '请输入'
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '批复金额',
                                                        dataIndex: '4',
                                                        key: '4'
                                                    },
                                                    form: {
                                                        type: 'number',
                                                        placeholder: '请输入'
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '备注',
                                                        dataIndex: '5',
                                                        key: '5'
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
                                        field: "tablezdj",
                                        name: "qnnTable",
                                        title: "暂定金、计日工计量",
                                        disabled: (obj) => {
                                            if (obj.initialValues && obj.initialValues.id) {
                                                return false;
                                            } else {
                                                return true;
                                            }
                                        },
                                        content: {
                                            fetchConfig: {
                                                apiName: 'getZxCtDayworkList',
                                                otherParams: (obj) => {
                                                    let rowData = obj.initialValues;
                                                    if (rowData) {
                                                        return { balID: rowData.id };
                                                    }
                                                },
                                            },
                                            wrappedComponentRef: (me) => {
                                                this.tableZDJ = me;
                                            },
                                            drawerConfig: {
                                                width: window.innerWidth * 0.8
                                            },
                                            antd: {
                                                rowKey: 'id',
                                                size: 'small'
                                            },
                                            paginationConfig: {
                                                position: "bottom"
                                            },
                                            isShowRowSelect: true,
                                            actionBtns: [
                                                {
                                                    name: 'add',//内置add del
                                                    icon: 'plus',//icon
                                                    type: 'primary',//类型  默认 primary  [primary dashed danger]
                                                    label: '新增',
                                                    hide: () => {
                                                        if (this.tableJL.getSelectedRows().length) {
                                                            return false;
                                                        }
                                                        return true;
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
                                                                apiName: 'addZxCtDaywork',
                                                            }
                                                        }
                                                    ]
                                                },
                                                {
                                                    name: 'edit',//内置add del
                                                    icon: 'edit',//icon
                                                    type: 'primary',//类型  默认 primary  [primary dashed danger]
                                                    label: '修改',
                                                    hide: () => {
                                                        if (this.tableJL.getSelectedRows().length) {
                                                            return false;
                                                        }
                                                        return true;
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
                                                                apiName: 'updateZxCtDaywork',
                                                            },
                                                            onClick: (obj) => {
                                                                obj.btnCallbackFn.clearSelectedRows();
                                                            }
                                                        }
                                                    ]
                                                },
                                                {
                                                    name: 'del',//内置add del
                                                    icon: 'delete',//icon
                                                    type: 'danger',//类型  默认 primary  [primary dashed danger]
                                                    label: '删除',
                                                    hide: () => {
                                                        if (this.tableJL.getSelectedRows().length) {
                                                            return false;
                                                        }
                                                        return true;
                                                    },
                                                    fetchConfig: {//ajax配置
                                                        apiName: 'batchDeleteUpdateZxCtDaywork',
                                                    },
                                                }
                                            ],
                                            formConfig: [
                                                {
                                                    isInTable: false,
                                                    form: {
                                                        label: '主键ID',
                                                        type: 'string',
                                                        field: 'id',
                                                        hide: true
                                                    }
                                                },
                                                {
                                                    isInTable: false,
                                                    form: {
                                                        field: 'balID',
                                                        type: 'string',
                                                        initialValue: (obj) => {
                                                            return obj.parentProps.initialValues.id;
                                                        },
                                                        hide: true,
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '单号',
                                                        dataIndex: 'billNo',
                                                        key: 'billNo',
                                                        fixed: 'left',
                                                        onClick: 'detail'
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
                                                        title: '业务日期',
                                                        dataIndex: 'busDate',
                                                        key: 'busDate',
                                                        format: 'YYYY-MM-DD',
                                                        fixed: 'left'
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
                                                        title: '金额',
                                                        dataIndex: 'totalAmt',
                                                        key: 'totalAmt'
                                                    },
                                                    form: {
                                                        type: 'number',
                                                        addDisabled: true,
                                                        editDisabled: true,
                                                        initialValue: 0,
                                                        placeholder: '请输入',
                                                        spanForm: 8
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '计日工',
                                                        dataIndex: 'dayPrice',
                                                        key: 'dayPrice'
                                                    },
                                                    form: {
                                                        type: 'number',
                                                        addDisabled: true,
                                                        editDisabled: true,
                                                        initialValue: 0,
                                                        placeholder: '请输入',
                                                        spanForm: 8
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '暂定金',
                                                        dataIndex: 'tempPrice',
                                                        key: 'tempPrice'
                                                    },
                                                    form: {
                                                        type: 'number',
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
                                                        formItemLayout: {
                                                            labelCol: {
                                                                xs: { span: 3 },
                                                                sm: { span: 3 }
                                                            },
                                                            wrapperCol: {
                                                                xs: { span: 21 },
                                                                sm: { span: 21 }
                                                            }
                                                        },
                                                    }
                                                },
                                                {
                                                    isInTable: false,
                                                    form: {
                                                        type: 'qnnTable',
                                                        field: 'dayworkItemList',
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
                                                                this.tableZDJMX = me;
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
                                                                        title: '资源名称',
                                                                        dataIndex: 'resName',
                                                                        key: 'resName',
                                                                        tdEdit: true
                                                                    },
                                                                    form: {
                                                                        type: 'string',
                                                                        placeholder: '请输入'
                                                                    }
                                                                },
                                                                {
                                                                    table: {
                                                                        title: '金额',
                                                                        dataIndex: 'amount',
                                                                        key: 'amount',
                                                                        tdEdit: true
                                                                    },
                                                                    form: {
                                                                        type: 'number',
                                                                        onChange: (val) => {
                                                                            let number = 0;
                                                                            this.tableZDJMX.props.data.map((item) => {
                                                                                if (item.amount) {
                                                                                    number += item.amount;
                                                                                }
                                                                                return item;
                                                                            })
                                                                            this.tableZDJ.btnCallbackFn.qnnForm.form.setFieldsValue({
                                                                                totalAmt: number + val,
                                                                                dayPrice: number + val
                                                                            })
                                                                        },
                                                                        placeholder: '请输入'
                                                                    }
                                                                },
                                                                {
                                                                    table: {
                                                                        title: '工作内容',
                                                                        dataIndex: 'workContent',
                                                                        key: 'workContent',
                                                                        tdEdit: true
                                                                    },
                                                                    form: {
                                                                        type: 'string',
                                                                        placeholder: '请输入'
                                                                    }
                                                                },
                                                                {
                                                                    table: {
                                                                        title: '备注',
                                                                        dataIndex: 'remark',
                                                                        key: 'remark',
                                                                        tdEdit: true
                                                                    },
                                                                    form: {
                                                                        type: 'string',
                                                                        placeholder: '请输入'
                                                                    }
                                                                }
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
                                                        field: "attachment",
                                                        type: "files",
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
                                                }
                                            ]
                                        }
                                    }
                                ],
                                formConfig: [
                                    {
                                        table: {
                                            title: '编号',
                                            dataIndex: 'balNo',
                                            key: 'balNo',
                                            width: 150,
                                            filter: true,
                                            fixed: 'left',
                                            onClick: "detail"
                                        },
                                        form: {
                                            type: 'string',
                                            required: true,
                                            placeholder: '请输入'
                                        }
                                    },
                                    {
                                        table: {
                                            title: '期次',
                                            dataIndex: 'caption',
                                            key: 'caption',
                                            width: 100,
                                            filter: true,
                                            fixed: 'left'
                                        },
                                        form: {
                                            type: 'string',
                                            required: true,
                                            placeholder: '请输入'
                                        }
                                    },
                                    {
                                        table: {
                                            title: '计量时间',
                                            dataIndex: 'period',
                                            key: 'period',
                                            width: 100,
                                        },
                                        form: {
                                            type: 'select',
                                            optionConfig: {
                                                label: 'label',
                                                value: 'value',
                                            },
                                            optionData: [
                                                {
                                                    label: "202012",
                                                    value: "202012"
                                                },
                                                {
                                                    label: "202011",
                                                    value: "202011"
                                                },
                                                {
                                                    label: "202010",
                                                    value: "202010"
                                                },
                                                {
                                                    label: "202009",
                                                    value: "202009"
                                                }
                                            ],
                                            allowClear: false,
                                            showSearch: true,
                                            required: true,
                                            placeholder: '请选择'
                                        }
                                    },
                                    {
                                        table: {
                                            title: '上期末计量(元)',
                                            dataIndex: 'totalLastBalAmt',
                                            key: 'totalLastBalAmt',
                                            width: 120,
                                        },
                                        isInForm: false
                                    },
                                    {
                                        table: {
                                            title: '本期计量(元)',
                                            dataIndex: 'balAmt',
                                            key: 'balAmt',
                                            width: 100,
                                        },
                                        isInForm: false
                                    },
                                    {
                                        table: {
                                            title: '开累计量(元)',
                                            dataIndex: 'totalbalAmt',
                                            key: 'totalbalAmt',
                                            width: 100,
                                        },
                                        isInForm: false
                                    },
                                    {
                                        table: {
                                            title: '业务日期',
                                            dataIndex: 'createDate',
                                            key: 'createDate',
                                            format: "YYYY-MM-DD",
                                            width: 100,
                                        },
                                        form: {
                                            type: 'date',
                                            initialValue: new Date(),
                                            required: true,
                                            placeholder: '请选择'
                                        }
                                    },
                                    {
                                        table: {
                                            title: '记录人',
                                            dataIndex: 'reportPerson',
                                            key: 'reportPerson',
                                            width: 100,
                                        },
                                        form: {
                                            type: 'string',
                                            addDisabled: true,
                                            editDisabled: true,
                                            initialValue: realName,
                                            placeholder: '请输入'
                                        }
                                    },
                                    {
                                        table: {
                                            title: '说明',
                                            dataIndex: 'remarks',
                                            key: 'remarks',
                                            width: 100,
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
                            field: "table6",
                            name: "qnnTable",
                            title: "信用评价",
                            disabled: (obj) => {
                                if (obj.clickCb.rowInfo.name === 'add') {
                                    return true;
                                } else {
                                    return false;
                                }
                            },
                            content: {
                                tabs: [],
                                fetchConfig: {
                                    apiName: 'getZxCtContrEvaluateList',
                                    otherParams: (obj) => {
                                        let rowData = obj.initialValues;
                                        if (rowData) {
                                            return { contractID: rowData.id };
                                        }
                                    },
                                },
                                wrappedComponentRef: (me) => {
                                    this.tableBG = me;
                                },
                                drawerConfig: {
                                    width: window.innerWidth * 0.8
                                },
                                antd: {
                                    rowKey: 'id',
                                    size: 'small'
                                },
                                paginationConfig: {
                                    position: "bottom"
                                },
                                isShowRowSelect: true,
                                actionBtns: [
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
                                                    apiName: 'addZxCtContrEvaluate',
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
                                                    apiName: 'updateZxCtContrEvaluate',
                                                },
                                                onClick: (obj) => {
                                                    obj.btnCallbackFn.clearSelectedRows();
                                                }
                                            }
                                        ]
                                    },
                                    {
                                        name: 'del',//内置add del
                                        icon: 'delete',//icon
                                        type: 'danger',//类型  默认 primary  [primary dashed danger]
                                        label: '删除',
                                        fetchConfig: {//ajax配置
                                            apiName: 'batchDeleteUpdateZxCtContrEvaluate',
                                        },
                                    }
                                ],
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
                                            field: 'contractID',
                                            type: 'string',
                                            initialValue: (obj) => {
                                                return obj.clickCb.selectedRows[0].id;
                                            },
                                            hide: true,
                                        }
                                    },
                                    {
                                        table: {
                                            title: '评价类型',
                                            dataIndex: 'type',
                                            key: 'type',
                                            type: "select"
                                        },
                                        form: {
                                            type: 'select',
                                            required: true,
                                            optionConfig: {
                                                label: 'label',
                                                value: 'value',
                                            },
                                            optionData: [
                                                {
                                                    label: "总体评价",
                                                    value: "0"
                                                },
                                                {
                                                    label: "分期评价",
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
                                            title: '评价日期',
                                            dataIndex: 'recordDate',
                                            key: 'recordDate',
                                            format: "YYYY-MM-DD"
                                        },
                                        form: {
                                            type: 'date',
                                            initialValue: new Date(),
                                            placeholder: '请选择',
                                            spanForm: 8
                                        }
                                    },
                                    {
                                        table: {
                                            title: '记录人',
                                            dataIndex: 'recordMan',
                                            key: 'recordMan'
                                        },
                                        form: {
                                            type: 'string',
                                            placeholder: '请输入',
                                            spanForm: 8
                                        }
                                    },
                                    {
                                        table: {
                                            title: '评价内容',
                                            dataIndex: 'content',
                                            key: 'content'
                                        },
                                        form: {
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
                                        table: {
                                            title: '评价结果',
                                            dataIndex: 'auditOption',
                                            key: 'auditOption'
                                        },
                                        form: {
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
                                        table: {
                                            title: '备注',
                                            dataIndex: 'remark',
                                            key: 'remark'
                                        },
                                        form: {
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
                                            field: 'attachment',
                                            type: "files",
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
                                ]
                            }
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
                            table: {
                                title: '合同序号',
                                dataIndex: 'index',
                                key: 'index',
                                fixed: 'left',
                                width: 80,
                                render: (data, rowData, index) => {
                                    return index + 1;
                                }
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '项目名称',
                                dataIndex: 'orgID',
                                key: 'orgID',
                                filter: true,
                                fixed: 'left',
                                width: 150,
                                type: 'select'
                            },
                            form: {
                                type: 'select',
                                optionConfig: {
                                    label: 'departmentName',
                                    value: 'departmentId'
                                },
                                fetchConfig: {
                                    apiName: 'getSysProjectBySelect',
                                    otherParams: {
                                        departmentId: departmentId
                                    }
                                },
                                placeholder: '请选择'
                            }
                        },
                        {
                            table: {
                                title: '项目全称',
                                dataIndex: 'projectName',
                                key: 'projectName',
                                filter: true,
                                fixed: 'left',
                                width: 150
                            },
                            form: {
                                type: 'string',
                                placeholder: '请输入'
                            }
                        },
                        {
                            table: {
                                title: '项目所在地',
                                dataIndex: 'projectLocation',
                                key: 'projectLocation',
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
                                        itemId: 'xingzhengquhuadaima'
                                    }
                                },
                                placeholder: '请选择'
                            }
                        },
                        {
                            table: {
                                title: '对下标准合同编码',
                                dataIndex: 'contractNo',
                                key: 'contractNo',
                                width: 150,
                                filter: true,
                            },
                            form: {
                                type: 'string',
                                placeholder: '请输入'
                            }
                        },
                        {
                            table: {
                                title: '含税合同金额<br/>(万元)',
                                dataIndex: 'contractCost',
                                key: 'contractCost',
                                width: 100
                            },
                            form: {
                                type: 'number',
                                placeholder: '请输入'
                            }
                        },
                        {
                            table: {
                                title: '合同开工<br/>日期',
                                dataIndex: 'actualStartDate',
                                key: 'actualStartDate',
                                width: 100,
                                format: "YYYY-MM-DD"
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '合同竣工<br/>日期',
                                dataIndex: 'actualEndDate',
                                key: 'actualEndDate',
                                width: 100,
                                format: "YYYY-MM-DD"
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '合同状态',
                                dataIndex: 'contractStatus',
                                key: 'contractStatus',
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
                                        itemId: 'heTongZhuangTai'
                                    }
                                },
                                placeholder: '请选择'
                            }
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
                            data={this.state.QDFlagData}
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
                                    field: 'workBookID',
                                    type: 'string',
                                    initialValue: this.state.QDFlagData.id,
                                    placeholder: '请输入',
                                    hide: true,
                                },
                                {
                                    label: '清单类型',
                                    field: 'inputWorkType',
                                    type: 'select',
                                    optionConfig: {
                                        label: 'label',
                                        value: 'value'
                                    },
                                    allowClear: false,
                                    initialValue:'0',
                                    optionData: [
                                        {
                                            label: '公路清单',
                                            value: '0'
                                        },
                                        {
                                            label: '铁路清单',
                                            value: '1'
                                        },
                                        {
                                            label: '市政清单',
                                            value: '2'
                                        },
                                        {
                                            label: '房建清单',
                                            value: '3'
                                        }
                                    ],
                                    placeholder: '请选择',
                                    required: true
                                },
                                {
                                    type: 'component',
                                    field: 'component',
                                    Component: obj => {
                                        return (
                                            <div style={{ textAlign: 'center' }}><Button type="primary" onClick={() => {
                                                let formData = obj.form.getFieldsValue();
                                                if(formData.inputWorkType === '0'){
                                                    confirm({
                                                        content: '确定下载公路清单模板吗?',
                                                        onOk: () => {
                                                            window.location.href = `${domain}upload/template/【业主合同台账】模块清单导入模板-公路清单.xls`;
                                                        }
                                                    });
                                                }else if(formData.inputWorkType === '1'){
                                                    confirm({
                                                        content: '确定下载铁路清单模板吗?',
                                                        onOk: () => {
                                                            window.location.href = `${domain}upload/template/【业主合同台账】模块清单导入模板-铁路清单.xls`;
                                                        }
                                                    });
                                                }else if(formData.inputWorkType === '2'){
                                                    confirm({
                                                        content: '确定下载市政清单模板吗?',
                                                        onOk: () => {
                                                            window.location.href = `${domain}upload/template/【业主合同台账】模块清单导入模板-市政清单.xls`;
                                                        }
                                                    });
                                                }else if(formData.inputWorkType === '3'){
                                                    confirm({
                                                        content: '确定下载房建清单模板吗?',
                                                        onOk: () => {
                                                            window.location.href = `${domain}upload/template/【业主合同台账】模块清单导入模板-房建清单.xls`;
                                                        }
                                                    });
                                                }
                                            }}>导入模板下载</Button></div>
                                        );
                                    }
                                },
                                {
                                    label: '附件',
                                    field: 'attachment',
                                    type: 'files',
                                    required: true,
                                    fetchConfig: {
                                        apiName: 'upload'
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
                                                this.setState({
                                                    treeData: null
                                                }, () => {
                                                    this.props.myFetch('getZxCtWorksWorkNameTree', { orgID: this.state.QDFlagData.orgID }).then(
                                                        ({ data, success, message }) => {
                                                            if (success) {
                                                                this.setState({
                                                                    treeData: data,
                                                                    defaultExpandedKeys: data.length ? [data[0].value] : [],
                                                                }, () => {
                                                                    this.tableQD.refresh();
                                                                })
                                                            } else {
                                                                Msg.error(message)
                                                            }
                                                        }
                                                    );
                                                })
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