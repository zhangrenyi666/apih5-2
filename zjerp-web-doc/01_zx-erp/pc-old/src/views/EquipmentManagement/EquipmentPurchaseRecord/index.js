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
                            type: 'primary',//??????  ?????? primary  [primary dashed danger]
                            label: '????????????',
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
                                    content: '??????????????????????????????????',
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
                            type: 'primary',//??????  ?????? primary  [primary dashed danger]
                            label: '????????????',
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
                            name: 'add',//??????add del
                            icon: 'plus',//icon
                            type: 'primary',//??????  ?????? primary  [primary dashed danger]
                            label: '??????',
                            formBtns: [
                                {
                                    name: 'cancel', //??????????????????
                                    type: 'dashed',//??????  ?????? primary
                                    label: '??????',
                                },
                                {
                                    name: 'submit',//??????add del
                                    type: 'primary',//??????  ?????? primary
                                    label: '??????',//???????????????????????????????????? 
                                    fetchConfig: {//ajax??????
                                        apiName: 'addZxEqPurRecord',
                                    }
                                }
                            ]
                        },
                        {
                            name: 'edit',//??????add del
                            icon: 'edit',//icon
                            type: 'primary',//??????  ?????? primary  [primary dashed danger]
                            label: '??????',
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
                                    name: 'cancel', //??????????????????
                                    type: 'dashed',//??????  ?????? primary
                                    label: '??????',
                                },
                                {
                                    name: 'submit',//??????add del
                                    type: 'primary',//??????  ?????? primary
                                    label: '??????',//???????????????????????????????????? 
                                    fetchConfig: {//ajax??????
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
                            type: 'primary',//??????  ?????? primary  [primary dashed danger]
                            label: '??????',
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
                                    content: '????????????????????????????',
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
                        //     type: 'primary',//??????  ?????? primary  [primary dashed danger]
                        //     label: '??????',
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
                        //             content: '????????????????????????????',
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
                            type: 'primary',//??????  ?????? primary  [primary dashed danger]
                            label: '?????????',
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
                                    content: '???????????????????????????????',
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
                        //     type: 'primary',//??????  ?????? primary  [primary dashed danger]
                        //     label: '??????',
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
                            name: 'del',//??????add del
                            icon: 'delete',//icon
                            type: 'danger',//??????  ?????? primary  [primary dashed danger]
                            label: '??????',
                            disabled: (obj) => {
                                let data = obj.btnCallbackFn.getSelectedRows();
                                if (data.filter(item => item.isimported !== '-1' && item.isimported !== '0' && item.isimported !== '3').length) {
                                    return true;
                                } else {
                                    return false;
                                }
                            },
                            fetchConfig: {//ajax??????
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
                                title: '????????????',
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
                                placeholder: '?????????',
                                spanForm: 8
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: "???????????????",
                                field: 'planNo',
                                type: 'string',
                                placeholder: '?????????',
                                spanForm: 8
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: "????????????",
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
                                placeholder: '?????????',
                                spanForm: 8
                            }
                            // form: {
                            //     label:'????????????',
                            //     field:'contractID',
                            //     type: 'selectByQnnTable',
                            //     optionConfig: {//??????????????????
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
                            //                     title: "????????????",
                            //                     width:200
                            //                 }
                            //             },
                            //             {
                            //                 isInForm: false,
                            //                 table: {
                            //                     dataIndex: "contractName",
                            //                     title: "????????????",
                            //                     width:200
                            //                 }
                            //             }
                            //         ]
                            //     },
                            //     allowClear: false,
                            //     showSearch: true,
                            //     required: true,
                            //     spanForm: 8,
                            //     placeholder: '?????????',
                            // },
                        },
                        {
                            isInTable: false,
                            form: {
                                label: "????????????",
                                field: 'acceptNO',
                                type: 'string',
                                placeholder: '?????????',
                                spanForm: 8
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: "???????????????",
                                field: 'supplieID',
                                type: 'string',
                                placeholder: '?????????',
                                spanForm: 8
                            }
                        },
                        {
                            table: {
                                title: '??????',
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
                                        label: "????????????",
                                        value: "0"
                                    },
                                    {
                                        label: "????????????",
                                        value: "1"
                                    }
                                ],
                                allowClear: false,
                                showSearch: true,
                                placeholder: '?????????',
                                spanForm: 8
                            }
                        },
                        {
                            table: {
                                title: '????????????',
                                dataIndex: 'purDate',
                                key: 'purDate',
                                width: 100,
                                format: 'YYYY-MM-DD'
                            },
                            form: {
                                type: 'date',
                                required: true,
                                placeholder: '?????????',
                                spanForm: 8
                            }
                        },
                        {
                            table: {
                                title: '????????????',
                                dataIndex: 'purPlace',
                                key: 'purPlace',
                                width: 100,
                                filter: true,
                                type: 'select'
                            },
                            form: {
                                type: 'select',
                                optionConfig: {
                                    label: 'itemName', //?????? label
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
                                placeholder: '?????????',
                                spanForm: 8
                            }
                        },
                        {
                            table: {
                                title: '????????????',
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
                                placeholder: '?????????',
                                spanForm: 8
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: "????????????",
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
                                placeholder: '?????????',
                                spanForm: 8
                            }
                        },
                        {
                            table: {
                                title: '????????????',
                                dataIndex: 'locality',
                                key: 'locality',
                                width: 100
                            },
                            form: {
                                type: 'select',
                                optionConfig: {
                                    label: 'itemName', //?????? label
                                    value: 'itemName'
                                },
                                fetchConfig: (obj) => {
                                    if (obj.form.getFieldValue('area') === '??????') {
                                        return {
                                            apiName: 'getBaseCodeSelect',
                                            otherParams: {
                                                itemId: 'xingzhengquhuadaima'
                                            }
                                        }
                                    }else if(obj.form.getFieldValue('area') === '??????'){
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
                                placeholder: '?????????',
                                spanForm: 8
                            }
                        },
                        {
                            table: {
                                title: '????????????????????????',
                                dataIndex: 'financeNo',
                                key: 'financeNo',
                                width: 150
                            },
                            form: {
                                type: 'string',
                                required: true,
                                placeholder: '?????????',
                                spanForm: 8
                            }
                        },
                        {
                            table: {
                                title: '????????????',
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
                                placeholder: '?????????',
                                spanForm: 8
                            }
                        },
                        {
                            table: {
                                title: '??????????????????',
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
                                placeholder: '?????????',
                                spanForm: 8
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: "??????????????????",
                                field: 'equipnoSecond',
                                type: 'string',
                                placeholder: '?????????',
                                spanForm: 8
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: "??????",
                                field: 'model',
                                type: 'string',
                                required: true,
                                placeholder: '?????????',
                                spanForm: 8
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: "??????",
                                field: 'spec',
                                type: 'string',
                                required: true,
                                placeholder: '?????????',
                                spanForm: 8
                            }
                        },
                        {
                            table: {
                                title: '??????(t)',
                                dataIndex: 'weight',
                                key: 'weight',
                                width: 100
                            },
                            form: {
                                type: 'string',
                                required: true,
                                placeholder: '?????????',
                                spanForm: 8
                            }
                        },
                        {
                            table: {
                                title: '?????????????????????(mm)',
                                dataIndex: 'heightlong',
                                key: 'heightlong',
                                width: 150
                            },
                            form: {
                                type: 'string',
                                required: true,
                                placeholder: '?????????',
                                spanForm: 8
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: "????????????",
                                field: 'factory',
                                type: 'string',
                                required: true,
                                placeholder: '?????????',
                                spanForm: 8
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: "????????????",
                                field: 'outfactoryDate',
                                type: 'date',
                                required: true,
                                placeholder: '?????????',
                                spanForm: 8
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: "????????????",
                                field: 'oldrate',
                                type: 'string',
                                placeholder: '?????????',
                                spanForm: 8
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: "????????????",
                                field: 'mainFactory',
                                type: 'string',
                                required: true,
                                placeholder: '?????????',
                                spanForm: 8
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: "????????????",
                                field: 'mainspec',
                                type: 'string',
                                required: true,
                                placeholder: '?????????',
                                spanForm: 8
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: "???????????????",
                                field: 'mainserial',
                                type: 'string',
                                required: true,
                                placeholder: '?????????',
                                spanForm: 8
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: "??????????????????",
                                field: 'mainoutfactory',
                                type: 'date',
                                placeholder: '?????????',
                                spanForm: 8
                            }
                        },
                        {
                            table: {
                                title: '???????????????',
                                dataIndex: 'outFactorySerial',
                                key: 'outFactorySerial',
                                width: 100
                            },
                            form: {
                                type: 'string',
                                required: true,
                                placeholder: '?????????',
                                spanForm: 8
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: "????????????",
                                field: 'viceFactory',
                                type: 'string',
                                placeholder: '?????????',
                                spanForm: 8
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: "????????????",
                                field: 'vicespec',
                                type: 'string',
                                placeholder: '?????????',
                                spanForm: 8
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: "???????????????",
                                field: 'viceserial',
                                type: 'string',
                                placeholder: '?????????',
                                spanForm: 8
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: "??????????????????",
                                field: 'viceoutfactory',
                                type: 'date',
                                placeholder: '?????????',
                                spanForm: 8
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: "????????????",
                                field: 'downfactory',
                                type: 'string',
                                placeholder: '?????????',
                                spanForm: 8
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: "????????????",
                                field: 'downspec',
                                type: 'string',
                                placeholder: '?????????',
                                spanForm: 8
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: "???????????????",
                                field: 'downserial',
                                type: 'string',
                                placeholder: '?????????',
                                spanForm: 8
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: "??????????????????",
                                field: 'downoutfactory',
                                type: 'date',
                                placeholder: '?????????',
                                spanForm: 8
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: "??????(?????????)(???)",
                                field: 'orginalvalue',
                                type: 'number',
                                // onChange:(val,obj) => {
                                //     let rowData = obj.form.getFieldsValue();
                                //     obj.form.setFieldsValue({totalvalue:(rowData.orginalvalue ? rowData.orginalvalue : 0) + (rowData.vicevalue ? rowData.vicevalue : 0) + (rowData.tranvalue ? rowData.tranvalue : 0)})
                                // },
                                required: true,
                                precision: 2,
                                min: 0,
                                placeholder: '?????????',
                                spanForm: 8
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: "???????????????(???)",
                                field: 'vicevalue',
                                type: 'number',
                                // onChange:(val,obj) => {
                                //     let rowData = obj.form.getFieldsValue();
                                //     obj.form.setFieldsValue({totalvalue:(rowData.orginalvalue ? rowData.orginalvalue : 0) + (rowData.vicevalue ? rowData.vicevalue : 0) + (rowData.tranvalue ? rowData.tranvalue : 0)})
                                // },
                                required: true,
                                precision: 2,
                                min: 0,
                                placeholder: '?????????',
                                spanForm: 8
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: "?????????/?????????(???)",
                                field: 'tranvalue',
                                type: 'number',
                                // onChange:(val,obj) => {
                                //     let rowData = obj.form.getFieldsValue();
                                //     obj.form.setFieldsValue({totalvalue:(rowData.orginalvalue ? rowData.orginalvalue : 0) + (rowData.vicevalue ? rowData.vicevalue : 0) + (rowData.tranvalue ? rowData.tranvalue : 0)})
                                // },
                                required: true,
                                precision: 2,
                                min: 0,
                                placeholder: '?????????',
                                spanForm: 8
                            }
                        },
                        {
                            table: {
                                title: '????????????(??????:???)',
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
                                placeholder: '?????????',
                                spanForm: 8
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: "????????????",
                                field: 'passNo',
                                type: 'string',
                                placeholder: '?????????',
                                spanForm: 8
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: "??????",
                                field: 'remark',
                                type: 'textarea',
                                placeholder: '?????????',
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
                                                label: '??????id',
                                                field: 'id',
                                                hide: true
                                            }
                                        },
                                        {
                                            table: {
                                                title: '??????',
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
                                                title: '???????????????????????????',
                                                dataIndex: 'name',
                                                key: 'name',
                                                tdEdit: true
                                            },
                                            form: {
                                                type: 'string',
                                                placeholder: '?????????',
                                            },
                                        },
                                        {
                                            table: {
                                                title: '??????',
                                                dataIndex: 'spec',
                                                key: 'spec',
                                                tdEdit: true
                                            },
                                            form: {
                                                type: 'string',
                                                placeholder: '?????????',
                                            },
                                        },
                                        {
                                            table: {
                                                title: '??????',
                                                dataIndex: 'unit',
                                                key: 'unit',
                                                tdEdit: true
                                            },
                                            form: {
                                                type: 'string',
                                                placeholder: '?????????',
                                            },
                                        },
                                        {
                                            table: {
                                                title: '??????',
                                                dataIndex: 'qty',
                                                key: 'qty',
                                                tdEdit: true
                                            },
                                            form: {
                                                type: 'number',
                                                min: 0,
                                                placeholder: '?????????',
                                            },
                                        }
                                    ],
                                    actionBtns: [
                                        {
                                            name: "addRow",
                                            icon: "plus",
                                            type: "primary",
                                            label: "?????????"
                                        },
                                        {
                                            name: 'del',
                                            icon: 'delete',
                                            type: 'danger',
                                            label: '??????'
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
                                                label: '??????id',
                                                field: 'id',
                                                hide: true
                                            }
                                        },
                                        {
                                            table: {
                                                title: '??????',
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
                                                title: '????????????????????????',
                                                dataIndex: 'name',
                                                key: 'name',
                                                tdEdit: true
                                            },
                                            form: {
                                                type: 'string',
                                                placeholder: '?????????',
                                            },
                                        },
                                        {
                                            table: {
                                                title: '??????',
                                                dataIndex: 'qty',
                                                key: 'qty',
                                                tdEdit: true
                                            },
                                            form: {
                                                type: 'number',
                                                min: 0,
                                                placeholder: '?????????',
                                            },
                                        },
                                        {
                                            table: {
                                                title: '??????',
                                                dataIndex: 'remark',
                                                key: 'remark',
                                                tdEdit: true
                                            },
                                            form: {
                                                type: 'string',
                                                placeholder: '?????????',
                                            },
                                        }
                                    ],
                                    actionBtns: [
                                        {
                                            name: "addRow",
                                            icon: "plus",
                                            type: "primary",
                                            label: "?????????"
                                        },
                                        {
                                            name: 'del',
                                            icon: 'delete',
                                            type: 'danger',
                                            label: '??????'
                                        }
                                    ]
                                }
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: "????????????",
                                field: 'acceptanceDate',
                                type: 'date',
                                required: true,
                                placeholder: '?????????',
                                spanForm: 8
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: "????????????",
                                field: 'isWorkEquip',
                                type: 'select',
                                optionConfig: {
                                    label: 'label',
                                    value: 'value',
                                },
                                optionData: [
                                    {
                                        label: "????????????",
                                        value: "0"
                                    },
                                    {
                                        label: "??????",
                                        value: "1"
                                    },
                                    {
                                        label: "????????????",
                                        value: "2"
                                    }
                                ],
                                allowClear: false,
                                showSearch: true,
                                required: true,
                                placeholder: '?????????',
                                spanForm: 8
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: "????????????(kw)",
                                field: 'mainpower',
                                type: 'number',
                                required: true,
                                precision: 0,
                                min: 0,
                                placeholder: '?????????',
                                spanForm: 8
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: "????????????(kw)",
                                field: 'vicepower',
                                type: 'number',
                                precision: 0,
                                min: 0,
                                placeholder: '?????????',
                                spanForm: 8
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: "?????????(kw)",
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
                                placeholder: '?????????',
                                spanForm: 8
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: "????????????",
                                field: 'isMadeinChina',
                                type: 'select',
                                optionConfig: {
                                    label: 'label',
                                    value: 'value',
                                },
                                optionData: [
                                    {
                                        label: "??????",
                                        value: "0"
                                    },
                                    {
                                        label: "??????",
                                        value: "1"
                                    }
                                ],
                                allowClear: false,
                                showSearch: true,
                                required: true,
                                placeholder: '?????????',
                                spanForm: 8
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: "????????????",
                                field: 'depreciation',
                                type: 'select',
                                optionConfig: {
                                    label: 'label',
                                    value: 'value',
                                },
                                optionData: [
                                    {
                                        label: "5???",
                                        value: "5"
                                    },
                                    {
                                        label: "10???",
                                        value: "10"
                                    }
                                ],
                                allowClear: false,
                                showSearch: true,
                                required: true,
                                placeholder: '?????????',
                                spanForm: 8
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: "??????",
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
                                title: '????????????',
                                dataIndex: 'isimported',
                                key: 'isimported',
                                width: 100,
                                fixed: 'right',
                                render: (data) => {
                                    if (data === '0') {
                                        return '???????????????';
                                    } else if (data === '1') {
                                        return '???????????????';
                                    } else if (data === '2') {
                                        return '?????????';
                                    } else if (data === '3') {
                                        return '??????????????????';
                                    } else if (data === '-1') {
                                        return '?????????';
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
                            title="?????????????????????"
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
                                upload={this.props.myUpload} //??????????????????promise
                                //??????????????????ajax???????????????????????????????????????head?????????????????? ?????????????????????
                                headers={{
                                    token: this.props.loginAndLogoutInfo.loginInfo.token
                                }}
                                data={rowData}
                                formConfig={[
                                    {
                                        type: "string",
                                        label: "??????id",
                                        field: "id", //?????????????????? ***??????
                                        hide: true
                                    },
                                    {
                                        type: "string",
                                        label: "????????????",
                                        field: "orgName",
                                        disabled: true,
                                        placeholder: "?????????",
                                    },
                                    {
                                        type: "string",
                                        label: "????????????",
                                        field: "equipManNo", //?????????????????? ***??????
                                        required: true,
                                        placeholder: "?????????",
                                    },
                                    {
                                        label: "ABCD??????",
                                        field: "abcType", //?????????????????? ***??????
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
                                        placeholder: "?????????",
                                    }
                                ]}
                                btns={[
                                    {
                                        name: "cancel",
                                        type: "dashed",
                                        label: "??????",
                                        field: 'cancel',
                                        isValidate: false,
                                        onClick: (obj) => {
                                            this.setState({ visible: false });
                                        }
                                    },
                                    {
                                        name: "submit",
                                        type: "primary",
                                        label: "???????????????",
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
                            title="????????????"
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