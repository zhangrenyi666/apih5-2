import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import moment from 'moment';
import { message as Msg, Modal } from 'antd';
const confirm = Modal.confirm;
const config = {
    antd: {
        rowKey: 'zxSkStockDifMonthId',
        size: 'small'
    },
    drawerConfig: {
        width: '1200px'
    },
    paginationConfig: {
        position: 'bottom'
    },
    firstRowIsSearch: false,
    isShowRowSelect: true
};
const configItem = {
    drawerConfig: {
        width: '1100px'
    },
    paginationConfig: {
        position: 'bottom'
    },
    actionBtnsPosition: "top",
    firstRowIsSearch: false,
    isShowRowSelect: true
};
class index extends Component {
    constructor(props) {
        super(props);
        this.state = {
            lockProjectId: props.loginAndLogoutInfo.loginInfo.userInfo.lockProject && props.loginAndLogoutInfo.loginInfo.userInfo.lockProject.projectId ? props.loginAndLogoutInfo.loginInfo.userInfo.lockProject.projectId : ''
        }
    }
    componentDidMount() {

    }
    render() {
        const { ext1, departmentId, companyId, projectId } = this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany;
        const { lockProjectId } = this.state;
        let jurisdiction = departmentId;
        if (lockProjectId !== 'all' && lockProjectId !== '') {
            jurisdiction = lockProjectId;
        } else {
            if (ext1 === '1' || ext1 === '2') {
                jurisdiction = companyId;
            } else if (ext1 === '3' || ext1 === '4') {
                jurisdiction = projectId;
            } else {

            }
        }
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
                        apiName: 'getZxSkStockDifMonthList',
                        otherParams: {
                            projectID: jurisdiction
                        }
                    }}
                    {...config}
                    formConfig={[
                        {
                            isInTable: false,
                            form: {
                                field: 'zxSkStockDifMonthId',
                                type: 'string',
                                hide: true,
                            }
                        },


                        {
                            isInTable: false,
                            form: {
                                type: 'string',
                                field: 'comID',
                                initialValue: companyId,
                                hide: true
                            }
                        },
                        {
                            table: {
                                title: '????????????',
                                onClick: 'detail',
                                dataIndex: 'comName',
                                key: 'comName'
                            },
                            form: {
                                type: 'string',
                                field: 'comName',
                                addDisabled: true,
                                editDisabled: true,
                                spanForm: 8,
                                formItemLayoutForm: {
                                    labelCol: {
                                        xs: { span: 24 },
                                        sm: { span: 8 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 24 },
                                        sm: { span: 16 }
                                    }
                                }
                            }
                        },
                        {
                            table: {
                                title: '????????????',
                                dataIndex: 'projectID',
                                key: 'projectID',
                                type: 'select'
                            },
                            form: {
                                field: 'projectID',
                                required: true,
                                type: 'select',
                                showSearch: true,
                                optionConfig: {
                                    label: 'departmentName',
                                    value: 'departmentId',
                                    linkageFields: {
                                        projectName: 'departmentName',
                                        comName: 'companyName'
                                    }
                                },
                                fetchConfig: {
                                    apiName: 'getSysProjectBySelect',
                                    otherParams: {
                                        departmentId: jurisdiction
                                    }
                                },
                                placeholder: '?????????',
                                spanForm: 8,
                                formItemLayoutForm: {
                                    labelCol: {
                                        xs: { span: 24 },
                                        sm: { span: 8 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 24 },
                                        sm: { span: 16 }
                                    }
                                },
                            },
                        },
                        {
                            isInTable: false,
                            form: {
                                type: 'string',
                                field: 'projectName',
                                hide: true
                            }
                        },
                        {
                            table: {
                                title: '??????',
                                dataIndex: 'periodDate',
                                key: 'periodDate',
                                format: 'YYYY-MM'
                            },
                            form: {
                                type: 'month',
                                required: true,
                                field: 'periodDate',
                                spanForm: 8,
                                formItemLayoutForm: {
                                    labelCol: {
                                        xs: { span: 24 },
                                        sm: { span: 8 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 24 },
                                        sm: { span: 16 }
                                    }
                                }
                            }

                        },
                        {
                            table: {
                                title: '?????????',
                                width: 120,
                                dataIndex: 'reporter',
                                key: 'reporter'
                            },
                            form: {
                                type: 'string',
                                required: true,
                                addDisabled: true,
                                editDisabled: true,
                                field: 'reporter',
                                initialValue: () => {
                                    return this.props.loginAndLogoutInfo.loginInfo.userInfo.realName
                                },
                                spanForm: 8,
                                formItemLayoutForm: {
                                    labelCol: {
                                        xs: { span: 24 },
                                        sm: { span: 8 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 24 },
                                        sm: { span: 16 }
                                    }
                                }
                            }
                        },
                        {
                            isInTable: false,
                            table: {
                                title: '????????????',
                                width: 120,
                                dataIndex: 'reportDate',
                                format: 'YYYY-MM-DD',
                                key: 'reportDate',
                                filter: true
                            },
                            form: {
                                type: 'date',
                                required: true,
                                addDisabled: true,
                                editDisabled: true,
                                field: 'reportDate',
                                initialValue: () => {
                                    return moment(new Date()).format('YYYY-MM-DD')
                                },
                                spanForm: 8,
                                formItemLayoutForm: {
                                    labelCol: {
                                        xs: { span: 24 },
                                        sm: { span: 8 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 24 },
                                        sm: { span: 16 }
                                    }
                                }
                            }
                        },
                        {
                            table: {
                                title: '????????????',
                                width: 100,
                                dataIndex: 'status',
                                key: 'status',
                                render: (data) => {
                                    if (data) {
                                        return data === '0' ? '?????????' : '?????????'
                                    } else {
                                        return '?????????'
                                    }
                                }
                            },

                            form: {
                                type: 'select',
                                field: 'status',
                                hide: true,
                                optionConfig: {
                                    label: 'label',
                                    value: 'value',
                                },
                                optionData: [
                                    {
                                        label: "?????????",
                                        value: "0"
                                    },
                                    {
                                        label: "?????????",
                                        value: "1"
                                    }
                                ],
                                spanForm: 8,
                                formItemLayoutForm: {
                                    labelCol: {
                                        xs: { span: 24 },
                                        sm: { span: 8 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 24 },
                                        sm: { span: 16 }
                                    }
                                }
                            }
                        },

                        {
                            isInTable: false,
                            form: {
                                type: 'qnnTable',
                                field: 'zxSkStockDifMonthItemList',
                                incToForm: true,
                                qnnTableConfig: {
                                    antd: {
                                        rowKey: 'zxSkStockDifMonthItemId',
                                        size: 'small'
                                    },
                                    ...configItem,
                                    formConfig: [
                                        {
                                            isInTable: false,
                                            form: {
                                                label: '??????id',
                                                field: 'zxSkStockDifMonthItemId',
                                                hide: true
                                            }
                                        },
                                        {
                                            table: {
                                                title: '??????',
                                                dataIndex: 'mtType',
                                                width: 160,
                                                key: 'mtType',
                                                type: 'select',
                                                tdEdit: true
                                            },
                                            form: {
                                                field: 'mtType',
                                                required: true,
                                                type: 'select',
                                                optionConfig: {
                                                    label: 'label',
                                                    value: 'value',
                                                },
                                                optionData: [
                                                    {
                                                        label: "?????????",
                                                        value: "1"
                                                    },
                                                    {
                                                        label: "???????????????",
                                                        value: "2"
                                                    }
                                                ]
                                            }
                                        },
                                        {
                                            table: {
                                                title: '????????????',
                                                width: 200,
                                                // dataIndex: 'categoryName',
                                                // key: 'categoryName',
                                                dataIndex: 'categoryID',
                                                key: 'categoryID',
                                                // type:'select',
                                                type: 'selectByPaging',
                                                tdEdit: true
                                            },
                                            form: {
                                                type: 'selectByPaging',
                                                field: 'categoryID',
                                                required: true,
                                                optionConfig: {
                                                    label: 'catName',
                                                    value: 'id'
                                                },
                                                fetchConfig: {
                                                    apiName: "getZxSkResCategoryMaterialsListResource",
                                                    otherParams: (val) => {
                                                        let vals = val.form.getFieldsValue();
                                                        return {
                                                            parentOrgID: vals.projectID,
                                                        }
                                                    }
                                                }
                                            }
                                        },
                                        {
                                            table: {
                                                title: '????????????',
                                                dataIndex: 'resCode',
                                                width: 180,
                                                key: 'resCode',
                                                tdEdit: true
                                            },
                                            form: {
                                                type: 'selectByQnnTable',
                                                label: '',
                                                field: 'resCode',
                                                required: true,
                                                // parent: 'categoryID',
                                                dependenciesReRender: true,//????????????-??????
                                                dependencies: ['categoryID'],
                                                optionConfig: {
                                                    label: 'resCode',
                                                    value: 'id'
                                                },
                                                dropdownMatchSelectWidth: 900,
                                                qnnTableConfig: {
                                                    antd: {
                                                        rowKey: "id"
                                                    },
                                                    firstRowIsSearch: false,
                                                    fetchConfig: {
                                                        apiName: "getZxSkResourceMaterialsListNameJoinResource",
                                                        otherParams: (obj) => {
                                                            const rowData2 = obj.qnnFormProps?.qnnformData?.qnnFormProps?.qnnTableInstance?.getEditedRowDataSync?.();

                                                            return {
                                                                id: rowData2 ? rowData2.categoryID : '',
                                                            }
                                                        }
                                                    },
                                                    searchBtnsStyle: "inline",
                                                    formConfig: [
                                                        {
                                                            isInForm: false,
                                                            isInTable: false,
                                                            form: {
                                                                field: 'id',
                                                                type: "string"
                                                            }
                                                        },
                                                        {
                                                            isInForm: false,
                                                            isInSearch: true,
                                                            table: {
                                                                dataIndex: "resCode",
                                                                title: "??????",
                                                            },
                                                            form: {
                                                                type: "string"
                                                            }
                                                        },
                                                        {
                                                            isInForm: false,
                                                            isInSearch: true,
                                                            table: {
                                                                dataIndex: "resName",
                                                                title: "??????",
                                                            },
                                                            form: {
                                                                type: "string"
                                                            }
                                                        },
                                                        {
                                                            isInForm: false,
                                                            isInSearch: true,
                                                            table: {
                                                                dataIndex: "spec",
                                                                title: "????????????",
                                                            },
                                                            form: {
                                                                type: "string"
                                                            }
                                                        },
                                                        {
                                                            isInForm: false,
                                                            isInSearch: true,
                                                            table: {
                                                                dataIndex: "unit",
                                                                title: "??????",
                                                            },
                                                            form: {
                                                                type: "string"
                                                            }
                                                        }
                                                    ]
                                                },
                                                onChange: (colVal, thisProps, tableBtnCallbackFn) => {
                                                    if (colVal) {
                                                        const itemData = thisProps;
                                                        tableBtnCallbackFn.setEditedRowData({
                                                            resCode: itemData.itemData.resCode,
                                                            resID: itemData.itemData.id,
                                                            resName: itemData.itemData.resName,
                                                            unit: itemData.itemData.unit,
                                                            spec: itemData.itemData.spec,
                                                            price: itemData.itemData.price,
                                                            thisPrice: 0,
                                                            thisQty: 0,
                                                            designQty: 0,
                                                            sunHaoLv: 0
                                                        });
                                                    } else {
                                                        tableBtnCallbackFn.getEditedRowData(false).then((editRowData) => {
                                                            tableBtnCallbackFn.setEditedRowData({
                                                                ...editRowData,
                                                                resID: '',
                                                                resName: '',
                                                                unit: '',
                                                                spec: '',
                                                                thisPrice: 0,
                                                                thisQty: 0,
                                                                designQty: 0,
                                                                sunHaoLv: 0
                                                            });
                                                        })
                                                    }

                                                },
                                            }
                                        },
                                        {
                                            isInTable: false,
                                            form: {
                                                type: 'string',
                                                field: 'categoryName'
                                            }
                                        },
                                        {
                                            isInTable: false,
                                            form: {
                                                type: 'string',
                                                field: 'resID'
                                            }
                                        },
                                        {
                                            table: {
                                                title: '????????????',
                                                width: 150,
                                                tooltip: 23,
                                                dataIndex: 'resName',
                                                key: 'resName'
                                            },
                                            form: {
                                                type: 'string',
                                                field: 'resName'
                                            }
                                        },
                                        {
                                            table: {
                                                title: '????????????',
                                                width: 100,
                                                dataIndex: 'spec',
                                                key: 'spec'
                                            },
                                            form: {
                                                type: 'string',
                                                field: 'spec'
                                            }
                                        },
                                        {
                                            table: {
                                                title: '??????',
                                                dataIndex: 'unit',
                                                key: 'unit',
                                                width: 80,
                                            },
                                            form: {
                                                type: 'string',
                                                field: 'unit'
                                            }
                                        },
                                        {
                                            table: {
                                                title: '?????????????????????????????????',
                                                dataIndex: 'thisPrice',
                                                width: 150,
                                                key: 'thisPrice',
                                                tdEdit: true
                                            },
                                            form: {
                                                type: 'number',
                                                field: 'thisPrice',
                                                precision: 6
                                            }
                                        },
                                        {
                                            table: {
                                                title: '?????????????????????',
                                                dataIndex: 'thisQty',
                                                width: 150,
                                                key: 'thisQty',
                                                tdEdit: true
                                            },
                                            form: {
                                                type: 'number',
                                                field: 'thisQty',
                                                precision: 3
                                            }
                                        },
                                        {
                                            table: {
                                                title: '????????????',
                                                dataIndex: 'designQty',
                                                width: 120,
                                                key: 'designQty',
                                                tdEdit: true
                                            },
                                            form: {
                                                type: 'number',
                                                field: 'designQty'
                                            }
                                        },
                                        {
                                            table: {
                                                title: '??????????????????(%)',
                                                dataIndex: 'sunHaoLv',
                                                width: 120,
                                                key: 'sunHaoLv',
                                                tdEdit: true
                                            },
                                            form: {
                                                type: 'number',
                                                field: 'sunHaoLv'
                                            }
                                        },
                                        {
                                            table: {
                                                title: '??????',
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
                                        },
                                    ],
                                    actionBtns: [
                                        {
                                            name: "addRow",
                                            icon: "plus",
                                            type: "primary",
                                            label: "??????",
                                        },
                                        {
                                            name: "del",
                                            icon: 'delete',
                                            type: 'danger',
                                            label: "??????",
                                        }
                                    ]
                                }
                            }
                        },
                    ]}
                    method={{
                        disabledFunEdit: (obj) => {
                            let data = obj.btnCallbackFn.getSelectedRows();
                            if (data.length !== 1 || data[0].status === '1') {
                                return true;
                            } else {
                                return false;
                            }
                        },
                        willExecuteFunEdit: (obj) => {
                            if (obj.selectedRows[0].status === '1') {
                                obj.btnCallbackFn.clearSelectedRows();
                                Msg.warn('????????????????????????!');
                                return false
                            } else {

                            }
                            obj.btnCallbackFn.clearSelectedRows();
                        },
                        disabledFunShenPi: (obj) => {
                            let data = obj.btnCallbackFn.getSelectedRows();
                            if (data.length !== 1 || data[0].status === '1') {
                                return true;
                            } else {
                                return false;
                            }
                        },
                        onClickFunShenPi: (obj) => {
                            const { myFetch } = this.props;
                            if (obj.selectedRows.length === 1) {
                                if (obj.selectedRows[0].status === '1') {
                                    obj.btnCallbackFn.closeDrawer();
                                    Msg.warn('????????????????????????!');
                                } else {
                                    obj.selectedRows[0].companyID = '1';
                                    confirm({
                                        content: '???????????????????????????????',
                                        onOk: () => {
                                            myFetch('checkZxSkStockDifMonth', obj.selectedRows[0]).then(
                                                ({ data, success, message }) => {
                                                    if (success) {
                                                        Msg.success(message)
                                                        this.table.refresh();
                                                    } else {
                                                        Msg.warn(message);
                                                        this.table.refresh();
                                                    }
                                                }
                                            );
                                        }
                                    });

                                }
                            } else {
                                Msg.warn('???????????????????????????')
                            }
                            obj.btnCallbackFn.clearSelectedRows();
                        },
                        disabledFunFanShenPi: (obj) => {
                            let data = obj.btnCallbackFn.getSelectedRows();
                            if (data.length !== 1 || data[0].status === '0') {
                                return true;
                            } else {
                                return false;
                            }
                        },
                        onClickFunFanShenPi: (obj) => {
                            const { myFetch } = this.props;
                            if (obj.selectedRows.length === 1) {
                                if (obj.selectedRows[0].status === '0') {
                                    obj.btnCallbackFn.closeDrawer();
                                    Msg.warn('???????????????????????????!');
                                } else {
                                    obj.selectedRows[0].companyID = '1';
                                    confirm({
                                        content: '??????????????????????????????????',
                                        onOk: () => {
                                            myFetch('counterCheckZxSkStockDifMonth', obj.selectedRows[0]).then(
                                                ({ data, success, message }) => {
                                                    if (success) {
                                                        Msg.success(message)
                                                        this.table.refresh();
                                                    } else {
                                                        Msg.warn(message);
                                                    }
                                                }
                                            );
                                        }
                                    });

                                }
                            } else {
                                Msg.warn('??????????????????????????????')
                            }
                            obj.btnCallbackFn.clearSelectedRows();
                        },
                        disabledFunDel: (obj) => {
                            let data = obj.btnCallbackFn.getSelectedRows();
                            if (data.length > 0) {
                                for (let m = 0; m < data.length; m++) {
                                    if (data[m].status === '1') {
                                        return true;
                                    }
                                }
                                return false
                            } else {
                                return true;
                            }
                        },
                        onClickFunDel: (obj) => {
                            const { myFetch } = this.props;
                            let arry = [];
                            for (let m = 0; m < obj.selectedRows.length; m++) {
                                if (obj.selectedRows[m].status === '1') {
                                    //????????????????????????
                                    arry.push(obj.selectedRows[m].status);
                                }
                            }
                            if (arry.length > 0) {
                                Msg.warn('??????????????????????????????')
                            } else {
                                confirm({
                                    content: '???????????????????????????????',
                                    onOk: () => {
                                        myFetch('batchDeleteUpdateZxSkStockDifMonth', obj.selectedRows).then(
                                            ({ data, success, message }) => {
                                                if (success) {
                                                    this.table.refresh();
                                                } else {
                                                }
                                            }
                                        );
                                    }
                                });
                            }
                            obj.btnCallbackFn.clearSelectedRows();
                        }
                    }}
                    // actionBtns={[
                    //     {
                    //         name: 'add',
                    //         icon: 'plus',
                    //         type: 'primary',
                    //         label: '??????',
                    //         formBtns: [
                    //             {
                    //                 name: 'cancel',
                    //                 type: 'dashed',
                    //                 label: '??????',
                    //             },
                    //             {
                    //                 name: 'submit',
                    //                 type: 'primary',
                    //                 label: '??????',
                    //                 fetchConfig: {
                    //                     apiName: 'addZxSkStockDifMonth'
                    //                 },
                    //                 field: 'addsubmit'
                    //             }
                    //         ]
                    //     },
                    //     {
                    //         name: 'edit',
                    //         icon: 'edit',
                    //         type: 'primary',
                    //         label: '??????',
                    //         disabled: 'bind:disabledFunEdit',
                    //         willExecute: 'bind:willExecuteFunEdit',
                    //         formBtns: [
                    //             {
                    //                 name: 'cancel',
                    //                 type: 'dashed',
                    //                 label: '??????',
                    //             },
                    //             {
                    //                 name: 'submit',
                    //                 type: 'primary',
                    //                 label: '??????',
                    //                 fetchConfig: {
                    //                     apiName: 'updateZxSkStockDifMonth'
                    //                 }
                    //             }
                    //         ]
                    //     },
                    //     {
                    //         name: 'diy',
                    //         type: 'primary',
                    //         label: '??????',
                    //         disabled: 'bind:disabledFunShenPi',
                    //         onClick: 'bind:onClickFunShenPi'
                    //     },
                    //     {
                    //         name: 'diy',
                    //         type: 'primary',
                    //         label: '?????????',
                    //         disabled: 'bind:disabledFunFanShenPi',
                    //         onClick: 'bind:onClickFunFanShenPi'
                    //     },
                    //     {
                    //         name: 'diyDel',
                    //         icon: 'delete',
                    //         type: 'danger',
                    //         label: '??????',
                    //         disabled: 'bind:disabledFunDel',
                    //         onClick: 'bind:onClickFunDel'
                    //     }
                    // ]}
                    actionBtns={{
                        apiName: "getSysMenuBtn",
                        otherParams: (obj) => {
                            var props = obj.props;
                            let curRouteData = props.routerInfo.routeData[props.routerInfo.curKey];
                            return {
                                menuParentId: curRouteData._value,
                                tableField: "MaterialPriceMonth"
                            }
                        }
                    }}
                />
            </div>
        );
    }
}

export default index;