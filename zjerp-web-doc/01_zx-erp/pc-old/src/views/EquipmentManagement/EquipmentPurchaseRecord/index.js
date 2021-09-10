import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import QnnForm from '../../modules/qnn-table/qnn-form';
import s from "./style.less";
import { message as Msg, Modal, Spin } from "antd";
const confirm = Modal.confirm;
const config = {
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
            xs: { span: 11 },
            sm: { span: 11 }
        },
        wrapperCol: {
            xs: { span: 13 },
            sm: { span: 13 }
        }
    }
};
class index extends Component {
    constructor(props) {
        super(props);
        this.state = {
            visible: false,
            rowData: null,
            visibles: false,
            loading: false
        }
    }
    render() {
        let { myPublic: { domain, appInfo: { ureport } } } = this.props;
        const { departmentId, companyId, companyName } = this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany;
        const { ext1 } = this.props.loginAndLogoutInfo.loginInfo.userInfo;
        const { visible, visibles, rowData, loading } = this.state;
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
                        apiName: 'getZxEqPurRecordList',
                        otherParams:{
                            useProjID:departmentId
                        }
                    }}
                    actionBtns={[
                        {
                            name: 'RequestNumber',
                            type: 'primary',//类型  默认 primary  [primary dashed danger]
                            label: '请求编号',
                            disabled: (obj) => {
                                let data = obj.btnCallbackFn.getSelectedRows();
                                if (data.length && !data.filter(item => item.isimported === '1' || item.isimported === '2').length) {
                                    return false;
                                } else {
                                    return true;
                                }
                            },
                            onClick: (obj) => {
                                confirm({
                                    content: '确定请求该条数据编号吗?',
                                    onOk: () => {
                                        this.props.myFetch('requestNumberZxEqPurRecord', obj.selectedRows).then(
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
                            name: 'FillInTheNumber',
                            type: 'primary',//类型  默认 primary  [primary dashed danger]
                            label: '填写编号',
                            disabled: (obj) => {
                                let data = obj.btnCallbackFn.getSelectedRows();
                                if (data.length === 1 && data[0].isimported === '1') {
                                    return false;
                                } else {
                                    return true;
                                }
                            },
                            hide:() => {
                                if (ext1 === '' || ext1 === '1') {
                                    return false;
                                }else{
                                    return true;
                                }
                            },
                            onClick: (obj) => {
                                this.setState({
                                    visible: true,
                                    rowData: obj.selectedRows[0]
                                }, () => {
                                    obj.btnCallbackFn.clearSelectedRows();
                                })
                            }
                        },
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
                                        apiName: 'addZxEqPurRecord',
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
                                if (data.length === 1 && (data[0].isimported === '-1' || data[0].isimported === '0' || data[0].isimported === '3')) {
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
                                        apiName: 'updateZxEqPurRecord',
                                    },
                                    onClick: (obj) => {
                                        obj.btnCallbackFn.clearSelectedRows();
                                    }
                                }
                            ]
                        },
                        {
                            name: 'goback',
                            type: 'primary',//类型  默认 primary  [primary dashed danger]
                            label: '退回',
                            disabled: (obj) => {
                                let data = obj.btnCallbackFn.getSelectedRows();
                                if (data.length === 1 && data[0].isimported === '1') {
                                    return false;
                                } else {
                                    return true;
                                }
                            },
                            hide:() => {
                                if (ext1 === '' || ext1 === '1') {
                                    return false;
                                }else{
                                    return true;
                                }
                            },
                            onClick: (obj) => {
                                confirm({
                                    content: '确定退回该条数据吗?',
                                    onOk: () => {
                                        this.props.myFetch('returnZxEqPurRecord', ...obj.selectedRows).then(
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
                        // {
                        //     name: 'audit',
                        //     type: 'primary',//类型  默认 primary  [primary dashed danger]
                        //     label: '审核',
                        //     disabled: (obj) => {
                        //         let data = obj.btnCallbackFn.getSelectedRows();
                        //         if (data.length === 1 && (data[0].isimported === '3' || data[0].isimported === '4')) {
                        //             return false;
                        //         } else {
                        //             return true;
                        //         }
                        //     },
                        //     hide:() => {
                        //         if (ext1 === '' || ext1 === '1') {
                        //             return false;
                        //         }else{
                        //             return true;
                        //         }
                        //     },
                        //     onClick: (obj) => {
                        //         confirm({
                        //             content: '确定审核该条数据吗?',
                        //             onOk: () => {
                        //                 this.props.myFetch('auditZxEqPurRecord', ...obj.selectedRows).then(
                        //                     ({ success, message }) => {
                        //                         if (success) {
                        //                             Msg.success(message);
                        //                             obj.btnCallbackFn.refresh();
                        //                             obj.btnCallbackFn.clearSelectedRows();
                        //                         } else {
                        //                             Msg.error(message);
                        //                         }
                        //                     }
                        //                 );
                        //             }
                        //         });
                        //     }
                        // },
                        {
                            name: 'auditee',
                            type: 'primary',//类型  默认 primary  [primary dashed danger]
                            label: '反审核',
                            disabled: (obj) => {
                                let data = obj.btnCallbackFn.getSelectedRows();
                                if (data.length === 1 && data[0].isimported === '2') {
                                    return false;
                                } else {
                                    return true;
                                }
                            },
                            hide:() => {
                                if (ext1 === '' || ext1 === '1' || ext1 === '2') {
                                    return false;
                                }else{
                                    return true;
                                }
                            },
                            onClick: (obj) => {
                                confirm({
                                    content: '确定反审核该条数据吗?',
                                    onOk: () => {
                                        this.props.myFetch('reverseAuditZxEqPurRecord', ...obj.selectedRows).then(
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
                        // {
                        //     name: 'preview',
                        //     type: 'primary',//类型  默认 primary  [primary dashed danger]
                        //     label: '预览',
                        //     disabled: (obj) => {
                        //         let data = obj.btnCallbackFn.getSelectedRows();
                        //         if (data.length === 1) {
                        //             return false;
                        //         } else {
                        //             return true;
                        //         }
                        //     },
                        //     onClick: (obj) => {
                        //         if (obj.selectedRows[0]?.id === this.rowData?.id ) {
                        //             this.setState({ visibles: true });
                        //         } else {
                        //             this.setState({
                        //                 rowData: obj.selectedRows[0]
                        //             }, () => {
                        //                 this.setState({ visibles: true, loading: true });
                        //             })
                        //         }
                        //     }
                        // },
                        {
                            name: 'del',//内置add del
                            icon: 'delete',//icon
                            type: 'danger',//类型  默认 primary  [primary dashed danger]
                            label: '删除',
                            disabled: (obj) => {
                                let data = obj.btnCallbackFn.getSelectedRows();
                                if (data.filter(item => item.isimported !== '-1' && item.isimported !== '0' && item.isimported !== '3').length) {
                                    return true;
                                } else {
                                    return false;
                                }
                            },
                            fetchConfig: {//ajax配置
                                apiName: 'batchDeleteUpdateZxEqPurRecord',
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
                            isInTable: false,
                            form: {
                                field: 'companyId',
                                type: 'string',
                                hide: true,
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                field: 'companyName',
                                type: 'string',
                                hide: true,
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                field: 'orgID',
                                type: 'string',
                                initialValue: companyId,
                                hide: true,
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                field: 'uesProject',
                                type: 'string',
                                hide: true,
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                field: 'resCatalog',
                                type: 'string',
                                hide: true,
                            }
                        },
                        {
                            table: {
                                title: '购置单位',
                                dataIndex: 'orgName',
                                key: 'orgName',
                                filter: true,
                                width: 150,
                                tooltip: 15,
                                fixed: 'left',
                                onClick:'detail'
                            },
                            form: {
                                type: 'string',
                                initialValue: () => {
                                    return companyName;
                                },
                                required: true,
                                addDisabled:true,
                                editDisabled:true,
                                placeholder: '请输入',
                                spanForm: 8
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: "计划批文号",
                                field: 'planNo',
                                type: 'string',
                                placeholder: '请输入',
                                spanForm: 8
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: "合同编号",
                                field: 'contractID',
                                type: 'select',
                                optionConfig: {
                                    label: 'contractNo',
                                    value: 'contractID',
                                    linkageFields:{
                                        supplieID:'secondName'
                                    }
                                },
                                fetchConfig: {
                                    apiName: "getZxCtEqContractListForEqMan",
                                    otherParams:{
                                        contractStatus:'1',
                                        orgID:departmentId
                                    }
                                },
                                placeholder: '请选择',
                                spanForm: 8
                            }
                            // form: {
                            //     label:'合同编号',
                            //     field:'contractID',
                            //     type: 'selectByQnnTable',
                            //     optionConfig: {//下拉选项配置
                            //         label: 'contractNo',
                            //         value: 'contractID'
                            //     },
                            //     dropdownMatchSelectWidth: false,
                            //     qnnTableConfig: {
                            //         antd: {
                            //             rowKey: "id"
                            //         },
                            //         firstRowIsSearch: false,
                            //         fetchConfig: {
                            //             apiName: "getZxCtEqContractListForEqMan"
                            //         },
                            //         searchBtnsStyle: "inline",
                            //         formConfig: [
                            //             {
                            //                 isInForm: false,
                            //                 table: {
                            //                     dataIndex: "contractNo",
                            //                     title: "合同编号",
                            //                     width:200
                            //                 }
                            //             },
                            //             {
                            //                 isInForm: false,
                            //                 table: {
                            //                     dataIndex: "contractName",
                            //                     title: "合同名称",
                            //                     width:200
                            //                 }
                            //             }
                            //         ]
                            //     },
                            //     allowClear: false,
                            //     showSearch: true,
                            //     required: true,
                            //     spanForm: 8,
                            //     placeholder: '请选择',
                            // },
                        },
                        {
                            isInTable: false,
                            form: {
                                label: "验收单号",
                                field: 'acceptNO',
                                type: 'string',
                                placeholder: '请输入',
                                spanForm: 8
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: "设备供应商",
                                field: 'supplieID',
                                type: 'string',
                                placeholder: '请输入',
                                spanForm: 8
                            }
                        },
                        {
                            table: {
                                title: '来源',
                                dataIndex: 'source',
                                key: 'source',
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
                                        label: "局批自购",
                                        value: "0"
                                    },
                                    {
                                        label: "局外调拨",
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
                                title: '购置日期',
                                dataIndex: 'purDate',
                                key: 'purDate',
                                width: 100,
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
                                title: '采购地点',
                                dataIndex: 'purPlace',
                                key: 'purPlace',
                                width: 100,
                                filter: true,
                                type: 'select'
                            },
                            form: {
                                type: 'select',
                                optionConfig: {
                                    label: 'itemName', //默认 label
                                    value: 'itemId'
                                },
                                fetchConfig:{
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
                                title: '使用单位',
                                dataIndex: 'useProjID',
                                key: 'useProjID',
                                width: 100,
                                type: 'select'
                            },
                            form: {
                                type: 'select',
                                optionConfig: {
                                    label: 'departmentName',
                                    value: 'departmentId',
                                    linkageFields: {
                                        uesProject: 'departmentName',
                                        companyId:'companyId',
                                        companyName:'companyName'
                                    }
                                },
                                fetchConfig: {
                                    apiName: 'getSysCompanyProject',
                                    otherParams:{
                                        departmentId:departmentId
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
                            isInTable: false,
                            form: {
                                label: "所属区域",
                                field: 'area',
                                type: 'select',
                                optionConfig: {
                                    label: 'itemName',
                                    value: 'itemName'
                                },
                                fetchConfig: {
                                    apiName: "getBaseCodeSelect",
                                    otherParams: {
                                        itemId: 'suoShuQuYu'
                                    }
                                },
                                onChange: (val, obj) => {
                                    obj.form.setFieldsValue({ locality: null })
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
                                title: '所在地点',
                                dataIndex: 'locality',
                                key: 'locality',
                                width: 100
                            },
                            form: {
                                type: 'select',
                                optionConfig: {
                                    label: 'itemName', //默认 label
                                    value: 'itemName'
                                },
                                fetchConfig: (obj) => {
                                    if (obj.form.getFieldValue('area') === '中国') {
                                        return {
                                            apiName: 'getBaseCodeSelect',
                                            otherParams: {
                                                itemId: 'xingzhengquhuadaima'
                                            }
                                        }
                                    }else if(obj.form.getFieldValue('area') === '海外'){
                                        return {
                                            apiName: 'getBaseCodeSelect',
                                            otherParams: {
                                                itemId: 'haiWaiXiangMuSuoZaiShengFenJianCheng'
                                            }
                                        }
                                    }else{
                                        return {};
                                    }
                                },
                                allowClear: false,
                                showSearch: true,
                                parent: "area",
                                required: true,
                                placeholder: '请选择',
                                spanForm: 8
                            }
                        },
                        {
                            table: {
                                title: '财务固定资产编号',
                                dataIndex: 'financeNo',
                                key: 'financeNo',
                                width: 150
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
                                title: '设备分类',
                                dataIndex: 'resCatalogID',
                                key: 'resCatalogID',
                                width: 150,
                                filter: true,
                                type: 'select'
                            },
                            form: {
                                type: 'select',
                                optionConfig: {
                                    label: 'catName',
                                    value: 'id',
                                    children: "childrenList",
                                    linkageFields:{
                                        resCatalog:'catName',
                                    }
                                },
                                fetchConfig: {
                                    apiName: "getZxEqResCategoryList",
                                    otherParams: {
                                        parentID: '0003'
                                    }
                                },
                                allowClear: false,
                                showSearch: true,
                                onChange: (val, obj) => {
                                    obj.form.setFieldsValue({ equipno: null })
                                },
                                required: true,
                                placeholder: '请选择',
                                spanForm: 8
                            }
                        },
                        {
                            table: {
                                title: '设备名称一类',
                                dataIndex: 'equipno',
                                key: 'equipno',
                                width: 120
                            },
                            form: {
                                type: 'select',
                                optionConfig: {
                                    label: 'catName',
                                    value: 'catName'
                                },
                                allowClear: false,
                                showSearch: true,
                                parent: "resCatalogID",
                                required: true,
                                placeholder: '请输入',
                                spanForm: 8
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: "设备名称二类",
                                field: 'equipnoSecond',
                                type: 'string',
                                placeholder: '请输入',
                                spanForm: 8
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: "型号",
                                field: 'model',
                                type: 'string',
                                required: true,
                                placeholder: '请输入',
                                spanForm: 8
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: "规格",
                                field: 'spec',
                                type: 'string',
                                required: true,
                                placeholder: '请输入',
                                spanForm: 8
                            }
                        },
                        {
                            table: {
                                title: '自重(t)',
                                dataIndex: 'weight',
                                key: 'weight',
                                width: 100
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
                                title: '外型尺寸长宽高(mm)',
                                dataIndex: 'heightlong',
                                key: 'heightlong',
                                width: 150
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
                                label: "国家厂家",
                                field: 'factory',
                                type: 'string',
                                required: true,
                                placeholder: '请输入',
                                spanForm: 8
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: "出厂日期",
                                field: 'outfactoryDate',
                                type: 'date',
                                required: true,
                                placeholder: '请选择',
                                spanForm: 8
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: "新旧程度",
                                field: 'oldrate',
                                type: 'string',
                                placeholder: '请输入',
                                spanForm: 8
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: "主机厂牌",
                                field: 'mainFactory',
                                type: 'string',
                                required: true,
                                placeholder: '请输入',
                                spanForm: 8
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: "主机型号",
                                field: 'mainspec',
                                type: 'string',
                                required: true,
                                placeholder: '请输入',
                                spanForm: 8
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: "主机系列号",
                                field: 'mainserial',
                                type: 'string',
                                required: true,
                                placeholder: '请输入',
                                spanForm: 8
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: "主机出厂日期",
                                field: 'mainoutfactory',
                                type: 'date',
                                placeholder: '请选择',
                                spanForm: 8
                            }
                        },
                        {
                            table: {
                                title: '出厂系列号',
                                dataIndex: 'outFactorySerial',
                                key: 'outFactorySerial',
                                width: 100
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
                                label: "副机厂牌",
                                field: 'viceFactory',
                                type: 'string',
                                placeholder: '请输入',
                                spanForm: 8
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: "副机型号",
                                field: 'vicespec',
                                type: 'string',
                                placeholder: '请输入',
                                spanForm: 8
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: "副机系列号",
                                field: 'viceserial',
                                type: 'string',
                                placeholder: '请输入',
                                spanForm: 8
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: "副机出厂日期",
                                field: 'viceoutfactory',
                                type: 'date',
                                placeholder: '请选择',
                                spanForm: 8
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: "底盘厂家",
                                field: 'downfactory',
                                type: 'string',
                                placeholder: '请输入',
                                spanForm: 8
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: "底盘型式",
                                field: 'downspec',
                                type: 'string',
                                placeholder: '请输入',
                                spanForm: 8
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: "底盘系列号",
                                field: 'downserial',
                                type: 'string',
                                placeholder: '请输入',
                                spanForm: 8
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: "底盘出厂日期",
                                field: 'downoutfactory',
                                type: 'date',
                                placeholder: '请选择',
                                spanForm: 8
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: "原价(不含税)(元)",
                                field: 'orginalvalue',
                                type: 'number',
                                // onChange:(val,obj) => {
                                //     let rowData = obj.form.getFieldsValue();
                                //     obj.form.setFieldsValue({totalvalue:(rowData.orginalvalue ? rowData.orginalvalue : 0) + (rowData.vicevalue ? rowData.vicevalue : 0) + (rowData.tranvalue ? rowData.tranvalue : 0)})
                                // },
                                required: true,
                                precision: 2,
                                min: 0,
                                placeholder: '请输入',
                                spanForm: 8
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: "配套件价值(元)",
                                field: 'vicevalue',
                                type: 'number',
                                // onChange:(val,obj) => {
                                //     let rowData = obj.form.getFieldsValue();
                                //     obj.form.setFieldsValue({totalvalue:(rowData.orginalvalue ? rowData.orginalvalue : 0) + (rowData.vicevalue ? rowData.vicevalue : 0) + (rowData.tranvalue ? rowData.tranvalue : 0)})
                                // },
                                required: true,
                                precision: 2,
                                min: 0,
                                placeholder: '请输入',
                                spanForm: 8
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: "运杂费/购置税(元)",
                                field: 'tranvalue',
                                type: 'number',
                                // onChange:(val,obj) => {
                                //     let rowData = obj.form.getFieldsValue();
                                //     obj.form.setFieldsValue({totalvalue:(rowData.orginalvalue ? rowData.orginalvalue : 0) + (rowData.vicevalue ? rowData.vicevalue : 0) + (rowData.tranvalue ? rowData.tranvalue : 0)})
                                // },
                                required: true,
                                precision: 2,
                                min: 0,
                                placeholder: '请输入',
                                spanForm: 8
                            }
                        },
                        {
                            table: {
                                title: '完全价值(合计:元)',
                                dataIndex: 'totalvalue',
                                key: 'totalvalue',
                                width: 150
                            },
                            form: {
                                type: 'number',
                                addends: ["orginalvalue", "vicevalue", "tranvalue"],
                                addDisabled: true,
                                editDisabled: true,
                                precision: 2,
                                min: 0,
                                placeholder: '请输入',
                                spanForm: 8
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: "牌照号码",
                                field: 'passNo',
                                type: 'string',
                                placeholder: '请输入',
                                spanForm: 8
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: "备注",
                                field: 'remark',
                                type: 'textarea',
                                placeholder: '请输入',
                                spanForm: 21,
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
                        },
                        {
                            isInTable: false,
                            form: {
                                type: 'qnnTable',
                                field: 'zxEqPurAccessoriesList',
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
                                                title: '随机工具及配件名称',
                                                dataIndex: 'name',
                                                key: 'name',
                                                tdEdit: true
                                            },
                                            form: {
                                                type: 'string',
                                                placeholder: '请输入',
                                            },
                                        },
                                        {
                                            table: {
                                                title: '规格',
                                                dataIndex: 'spec',
                                                key: 'spec',
                                                tdEdit: true
                                            },
                                            form: {
                                                type: 'string',
                                                placeholder: '请输入',
                                            },
                                        },
                                        {
                                            table: {
                                                title: '单位',
                                                dataIndex: 'unit',
                                                key: 'unit',
                                                tdEdit: true
                                            },
                                            form: {
                                                type: 'string',
                                                placeholder: '请输入',
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
                                                type: 'number',
                                                min: 0,
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
                                type: 'qnnTable',
                                field: 'zxEqPurLibList',
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
                                        this.tableTow = me;
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
                                                title: '随机技术资料名称',
                                                dataIndex: 'name',
                                                key: 'name',
                                                tdEdit: true
                                            },
                                            form: {
                                                type: 'string',
                                                placeholder: '请输入',
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
                                                type: 'number',
                                                min: 0,
                                                placeholder: '请输入',
                                            },
                                        },
                                        {
                                            table: {
                                                title: '说明',
                                                dataIndex: 'remark',
                                                key: 'remark',
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
                                label: "验收时间",
                                field: 'acceptanceDate',
                                type: 'date',
                                required: true,
                                placeholder: '请选择',
                                spanForm: 8
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: "设备类型",
                                field: 'isWorkEquip',
                                type: 'select',
                                optionConfig: {
                                    label: 'label',
                                    value: 'value',
                                },
                                optionData: [
                                    {
                                        label: "施工机械",
                                        value: "0"
                                    },
                                    {
                                        label: "车辆",
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
                                label: "主机功率(kw)",
                                field: 'mainpower',
                                type: 'number',
                                required: true,
                                precision: 0,
                                min: 0,
                                placeholder: '请输入',
                                spanForm: 8
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: "付机功率(kw)",
                                field: 'vicepower',
                                type: 'number',
                                precision: 0,
                                min: 0,
                                placeholder: '请输入',
                                spanForm: 8
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: "总功率(kw)",
                                field: 'power',
                                type: 'number',
                                addends: [
                                    "mainpower",
                                    "vicepower"
                                ],
                                precision: 0,
                                min: 0,
                                addDisabled: true,
                                editDisabled: true,
                                placeholder: '请输入',
                                spanForm: 8
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: "国产进口",
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
                            isInTable: false,
                            form: {
                                label: "折旧年限",
                                field: 'depreciation',
                                type: 'select',
                                optionConfig: {
                                    label: 'label',
                                    value: 'value',
                                },
                                optionData: [
                                    {
                                        label: "5年",
                                        value: "5"
                                    },
                                    {
                                        label: "10年",
                                        value: "10"
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
                                        xs: { span: 4 },
                                        sm: { span: 4 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 20 },
                                        sm: { span: 20 }
                                    }
                                }
                            }
                        },
                        {
                            table: {
                                title: '入账状态',
                                dataIndex: 'isimported',
                                key: 'isimported',
                                width: 100,
                                fixed: 'right',
                                render: (data) => {
                                    if (data === '0') {
                                        return '未申请入帐';
                                    } else if (data === '1') {
                                        return '申请入帐中';
                                    } else if (data === '2') {
                                        return '已入帐';
                                    } else if (data === '3') {
                                        return '已入帐反审核';
                                    } else if (data === '-1') {
                                        return '已退回';
                                    }
                                }
                            },
                            form: {
                                type: 'string',
                                hide: true
                            }
                        }
                    ]}
                />
                {
                    visible ? <div>
                        <Modal
                            width={'500px'}
                            style={{ paddingBottom: '0', top: '0' }}
                            title="填写编号并审核"
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
                                data={rowData}
                                formConfig={[
                                    {
                                        type: "string",
                                        label: "主键id",
                                        field: "id", //唯一的字段名 ***必传
                                        hide: true
                                    },
                                    {
                                        type: "string",
                                        label: "购置单位",
                                        field: "orgName",
                                        disabled: true,
                                        placeholder: "请输入",
                                    },
                                    {
                                        type: "string",
                                        label: "管理编号",
                                        field: "equipManNo", //唯一的字段名 ***必传
                                        required: true,
                                        placeholder: "请输入",
                                    },
                                    {
                                        label: "ABCD分类",
                                        field: "abcType", //唯一的字段名 ***必传
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
                                        required: true,
                                        placeholder: "请输入",
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
                                        label: "保存并审核",
                                        field: 'submit',
                                        onClick: (obj) => {
                                            obj.btnfns.fetchByCb('writeNumberZxEqPurRecord', obj.values, ({ data, success, message }) => {
                                                if (success) {
                                                    Msg.success(message);
                                                    this.table.refresh();
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
                    </div> : null
                }
                {
                    visibles ? <div>
                        <Modal
                            width={'90%'}
                            style={{ paddingBottom: '0', top: '0' }}
                            title="报表预览"
                            visible={this.state.visibles}
                            footer={null}
                            onCancel={() => {
                                this.setState({
                                    visibles: false
                                })
                            }}
                            bodyStyle={{ padding: '10px', overflow: 'hidden' }}
                            centered={true}
                            wrapClassName={'modals'}
                        >
                            <Spin spinning={loading}>
                                <iframe title="myf" width='100%' height={window.innerHeight * 0.8} frameBorder={0} onLoad={(obj) => {
                                    this.setState({ loading: false, flag: false })
                                }} src={`${ureport}preview?_u=file:zxEqPurRecord.xml&url=${domain}&delFlag=0&id=${rowData.id}&_t=6`}></iframe>
                            </Spin>

                        </Modal>
                    </div> : null
                }
            </div>
        );
    }
}

export default index;