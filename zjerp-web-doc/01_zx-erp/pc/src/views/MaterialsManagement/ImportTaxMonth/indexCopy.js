import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import moment from 'moment';
import { Modal, Spin } from 'antd';
const confirm = Modal.confirm;
const config = {

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
            lockProjectId: props.loginAndLogoutInfo.loginInfo.userInfo.lockProject && props.loginAndLogoutInfo.loginInfo.userInfo.lockProject.projectId ? props.loginAndLogoutInfo.loginInfo.userInfo.lockProject.projectId : '',
            visible: false,
            loading: false,
            idPreview: null,
            name: null
        }
    }
    componentDidMount() { }
    FloatMulTwo(arg1, arg2) {
        var m = 0, s1 = arg1.toString(), s2 = arg2.toString();
        try { m += s1.split(".")[1].length } catch (e) { }
        try { m += s2.split(".")[1].length } catch (e) { }
        return Number(s1.replace(".", "")) * Number(s2.replace(".", "")) / Math.pow(10, m)
    }
    handleCancel = () => {
        this.setState({ visible: false, loading: false });
    }
    render() {
        const { ext1, departmentId, companyId, projectId } = this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany;
        const { lockProjectId, loading, idPreview, name } = this.state;
        let { myPublic: { appInfo: { ureport } } } = this.props;
        const { access_token } = this.props.loginAndLogoutInfo.loginInfo;
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
                    {...config}
                    fetchConfig={{
                        apiName: 'getZxSkMmReqPlanList',
                        otherParams: {
                            projectID: jurisdiction
                        }
                    }}
                    antd={{
                        rowKey: 'id',
                        size: 'small',

                    }}
                    method={{
                        onClickFunDel: (obj) => {
                            const { myFetch } = this.props;
                            confirm({
                                content: '???????????????????????????????',
                                onOk: () => {
                                    myFetch('batchDeleteUpdateZxSkMmReqPlan', obj.selectedRows).then(
                                        ({ data, success, message }) => {
                                            if (success) {
                                                this.table.refresh();
                                            } else {
                                            }
                                            obj.btnCallbackFn.clearSelectedRows();
                                        }
                                    );
                                }
                            });
                        },
                        // ??????
                        disabledFunpreview: (obj) => {
                            let data = obj.btnCallbackFn.getSelectedRows();
                            if (data.length !== 1) {
                                return true;
                            } else {
                                return false;
                            }
                        },
                        onClickFunpreview: (obj) => {
                            this.setState({
                                visible: true,
                                loading: true,
                                idPreview: obj.selectedRows[0].id,
                                name: obj.selectedRows[0].projectName
                            })
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
                                field: 'projectName',
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
                            table: {
                                title: '??????',
                                dataIndex: 'periodDate',
                                key: 'periodDate',
                                format: 'YYYY-MM'
                                // type: 'select'
                            },
                            form: {
                                field: 'periodDate',
                                type: 'month',
                                required: true,
                                editDisabled: true,
                                placeholder: '?????????',
                                spanForm: 12,
                                formItemLayoutForm: {
                                    labelCol: {
                                        xs: { span: 24 },
                                        sm: { span: 6 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 24 },
                                        sm: { span: 18 }
                                    }
                                }
                            },
                        },
                        {
                            table: {
                                title: '????????????',

                                dataIndex: 'planCode',
                                key: 'planCode',
                                filter: true
                            },
                            form: {
                                field: 'planCode',
                                type: 'string',
                                placeholder: '?????????',
                                spanForm: 12,
                                formItemLayoutForm: {
                                    labelCol: {
                                        xs: { span: 24 },
                                        sm: { span: 6 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 24 },
                                        sm: { span: 18 }
                                    }
                                }
                            },
                        },
                        {
                            table: {
                                title: '????????????',
                                dataIndex: 'projectID',
                                key: 'projectID',
                                type: 'select',
                                filter: true,
                                fieldsConfig: {
                                    type: 'select',
                                    field: 'orgID2',
                                    optionConfig: {
                                        label: 'departmentName',
                                        value: 'departmentId'
                                    },
                                    fetchConfig: {
                                        apiName: 'getSysProjectBySelect',
                                        otherParams: {
                                            departmentId: jurisdiction
                                        }
                                    },
                                }
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
                                        projectName: 'projectName',
                                        companyName: 'companyName',
                                        companyId: 'companyId'
                                    }
                                },
                                fetchConfig: {
                                    apiName: 'getSysProjectBySelect',
                                    otherParams: {
                                        departmentId: jurisdiction
                                    }
                                },
                                placeholder: '?????????',
                                spanForm: 12,
                                formItemLayoutForm: {
                                    labelCol: {
                                        xs: { span: 24 },
                                        sm: { span: 6 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 24 },
                                        sm: { span: 18 }
                                    }
                                },
                                onChange: (val, rowData) => {
                                    if (this.table && this.table.qnnForm) {
                                        this.table.qnnForm.clearValues(['zxSkMmReqPlanItemList']);
                                        // this.table.qnnForm.qnnSetState({
                                        //     id:null
                                        // })
                                    }
                                }
                            },
                        },
                        {
                            table: {
                                title: '??????',
                                width: 120,
                                onClick: 'detail',
                                dataIndex: 'createDate',
                                // format: 'YYYY-MM-DD',
                                key: 'createDate',
                                filter: true,
                                fieldsConfig: {
                                    type: 'rangeDate',
                                    field: 'timeList'
                                },
                                render: (data) => {
                                    return data ? moment(data).format('YYYY-MM-DD') : null
                                }
                            },
                            form: {
                                type: 'date',
                                required: true,
                                field: 'createDate',
                                initialValue: () => {
                                    return moment(new Date()).format('YYYY-MM-DD')
                                },
                                spanForm: 12,
                                formItemLayoutForm: {
                                    labelCol: {
                                        xs: { span: 24 },
                                        sm: { span: 6 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 24 },
                                        sm: { span: 18 }
                                    }
                                }
                            }
                        },
                        {
                            table: {
                                title: '?????????',
                                dataIndex: 'creator',
                                key: 'creator',
                                filter: true
                            },
                            form: {
                                type: 'string',
                                field: 'creator',
                                spanForm: 12,
                                formItemLayoutForm: {
                                    labelCol: {
                                        xs: { span: 24 },
                                        sm: { span: 6 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 24 },
                                        sm: { span: 18 }
                                    }
                                }
                            }
                        },

                        {
                            isInTable: false,
                            form: {
                                type: 'qnnTable',
                                field: 'zxSkMmReqPlanItemList',
                                incToForm: true,
                                qnnTableConfig: {
                                    antd: {
                                        rowKey: 'id',
                                        size: 'small'
                                    },
                                    ...configItem,
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
                                                title: "<div>????????????<span style='color: #ff4d4f'>*</span></div>",
                                                dataIndex: 'cbsID',
                                                width: 200,
                                                key: 'cbsID',
                                                type: 'select',
                                                tdEdit: true
                                            },
                                            form: {
                                                type: 'selectByPaging',
                                                field: 'cbsID',
                                                required: true,
                                                optionConfig: {
                                                    label: 'name',
                                                    value: 'iecsCBSID'
                                                },
                                                fetchConfig: {
                                                    apiName: "getZxEqIecsCBSOrgId",
                                                    otherParams: (val) => {
                                                        let vals = val.form.getFieldsValue();
                                                        return {
                                                            orgID: vals.projectID,
                                                        }
                                                    }
                                                }
                                            }
                                        },
                                        {
                                            table: {
                                                title: "<div>????????????<span style='color: #ff4d4f'>*</span></div>",
                                                width: 180,
                                                tooltip: 23,
                                                dataIndex: 'resCode',
                                                key: 'resCode',
                                                tdEdit: true
                                            },
                                            form: {
                                                type: 'selectByQnnTable',
                                                label: '',
                                                required: true,
                                                field: 'resCode',
                                                allowClear: false,
                                                dropdownMatchSelectWidth: 900,
                                                optionConfig: {
                                                    label: 'resCode',
                                                    value: 'id'
                                                },
                                                qnnTableConfig: {
                                                    antd: {
                                                        rowKey: "id"
                                                    },
                                                    firstRowIsSearch: false,
                                                    fetchConfig: {
                                                        apiName: "getZxSkResourceMaterialsListNameJoin",
                                                        otherParams: (val) => {
                                                            return {
                                                                orgID: val.qnnTableInstance.props.qnnFormProps.form.getFieldsValue().projectID
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
                                                        },
                                                        {
                                                            isInForm: false,
                                                            // isInSearch:true,
                                                            table: {
                                                                dataIndex: "refpriceType",
                                                                title: "????????????",
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
                                                        // ????????????????????????
                                                        tableBtnCallbackFn.setEditedRowData({
                                                            resCode: itemData.itemData.resCode,
                                                            resName: itemData.itemData.resName,
                                                            unit: itemData.itemData.unit,
                                                            spec: itemData.itemData.spec,
                                                            resID: itemData.itemData.id,
                                                            price: 0,
                                                            purNum: 0,
                                                            totalMoney: 0,
                                                            nextMonthNum: 0,
                                                            nextMonthAmt: 0
                                                        });
                                                    } else {
                                                        tableBtnCallbackFn.setEditedRowData({
                                                            resName: '',
                                                            resID: '',
                                                            unit: '',
                                                            spec: '',
                                                            price: 0,
                                                            purNum: 0,
                                                            totalMoney: 0,
                                                            nextMonthNum: 0,
                                                            nextMonthAmt: 0
                                                        });
                                                    }

                                                },
                                            }
                                        },
                                        {
                                            table: {
                                                title: '????????????',
                                                width: 200,
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
                                            isInTable: false,
                                            form: {
                                                type: 'string',
                                                field: 'resID'
                                            }
                                        },
                                        {
                                            table: {
                                                title: '????????????',
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
                                                dataIndex: 'price',
                                                width: 100,
                                                key: 'price',
                                                tdEdit: true
                                            },
                                            form: {
                                                type: 'number',
                                                field: 'price',
                                                min: 0,
                                                precision: 6,
                                                onChange: async (colVal, thisProps, tableBtnCallbackFn) => {
                                                    if (colVal >= 0 && typeof (colVal) === 'number') {
                                                        // ???????????????????????? ????????????
                                                        const rowData = await tableBtnCallbackFn.getEditedRowData(false);
                                                        const newRowData = {
                                                            ...rowData
                                                        }
                                                        if (rowData.purNum) {
                                                            newRowData.totalMoney = this.FloatMulTwo(rowData.price, rowData.purNum);
                                                        }
                                                        if (rowData.nextMonthNum) {
                                                            newRowData.nextMonthAmt = this.FloatMulTwo(rowData.price, rowData.nextMonthNum);
                                                        }
                                                        await tableBtnCallbackFn.setEditedRowData({
                                                            ...newRowData
                                                        });
                                                    }
                                                }
                                            }
                                        },
                                        {
                                            table: {
                                                title: '?????????????????????',
                                                dataIndex: 'purNum',
                                                key: 'purNum',
                                                width: 120,
                                                tdEdit: true
                                            },
                                            form: {
                                                type: 'number',
                                                field: 'purNum',
                                                min: 0,
                                                precision: 3,
                                                onChange: async (colVal, thisProps, tableBtnCallbackFn) => {
                                                    if (colVal >= 0 && typeof (colVal) === 'number') {
                                                        // ???????????????????????? ????????????
                                                        const rowData = await tableBtnCallbackFn.getEditedRowData(false);
                                                        const newRowData = {
                                                            ...rowData
                                                        }
                                                        if (rowData.price) {
                                                            newRowData.totalMoney = this.FloatMulTwo(rowData.price, rowData.purNum);
                                                        }
                                                        await tableBtnCallbackFn.setEditedRowData({
                                                            ...newRowData
                                                        });
                                                    }
                                                }
                                            }
                                        },
                                        {
                                            table: {
                                                title: '??????',
                                                dataIndex: 'totalMoney',
                                                width: 120,
                                                key: 'totalMoney'
                                            },
                                            form: {
                                                type: 'number',
                                                field: 'totalMoney',
                                                precision: 2,
                                            }
                                        },
                                        {
                                            table: {
                                                title: '??????????????????',
                                                dataIndex: 'nextMonthNum',
                                                width: 130,
                                                key: 'nextMonthNum',
                                                tdEdit: true
                                            },
                                            form: {
                                                type: 'number',
                                                field: 'nextMonthNum',
                                                min: 0,
                                                precision: 3,
                                                onChange: async (colVal, thisProps, tableBtnCallbackFn) => {
                                                    if (colVal >= 0 && typeof (colVal) === 'number') {
                                                        // ???????????????????????? ????????????
                                                        const rowData = await tableBtnCallbackFn.getEditedRowData(false);
                                                        const newRowData = {
                                                            ...rowData
                                                        }
                                                        if (rowData.price) {
                                                            newRowData.nextMonthAmt = this.FloatMulTwo(rowData.price, rowData.nextMonthNum);
                                                        }
                                                        await tableBtnCallbackFn.setEditedRowData({
                                                            ...newRowData
                                                        });
                                                    }
                                                }
                                            }
                                        },
                                        {
                                            table: {
                                                title: '??????????????????',
                                                dataIndex: 'nextMonthAmt',
                                                width: 130,
                                                key: 'nextMonthAmt',
                                            },
                                            form: {
                                                type: 'number',
                                                field: 'nextMonthAmt',
                                                precision: 2,
                                            }
                                        },
                                        {
                                            table: {
                                                title: '??????',
                                                width: 120,
                                                tooltip: 23,
                                                dataIndex: 'remark',
                                                key: 'remark',
                                                tdEdit: true
                                            },
                                            form: {
                                                type: 'textarea',
                                                field: 'remark',
                                                autoSize: {
                                                    minRows: 1,
                                                    maxRows: 3
                                                }
                                            }
                                        }
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
                        {
                            table: {
                                title: '??????',
                                width: 300,
                                tooltip: 23,
                                dataIndex: 'remark',
                                key: 'remark',
                            },
                            form: {
                                type: 'textarea',
                                field: 'remark',
                                formItemLayoutForm: {
                                    labelCol: {
                                        xs: { span: 24 },
                                        sm: { span: 3 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 24 },
                                        sm: { span: 21 }
                                    }
                                },
                            }
                        }
                    ]}
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
                    //                 field: 'addsubmit',
                    //                 fetchConfig: {
                    //                     apiName: 'addZxSkMmReqPlan'
                    //                 }
                    //             }
                    //         ]
                    //     },
                    //     {
                    //         name: 'edit',
                    //         icon: 'edit',
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
                    //                     apiName: 'updateZxSkMmReqPlan'
                    //                 }
                    //             }
                    //         ]
                    //     },
                    //     {
                    //         name: 'diyPreview',
                    //         type: 'primary',
                    //         label: '??????',
                    //         disabled: 'bind:disabledFunpreview',
                    //         onClick: 'bind:onClickFunpreview'
                    //     },
                    //     {
                    //         name: 'diyDel',
                    //         icon: 'delete',
                    //         type: 'danger',
                    //         label: '??????',
                    //         disabled: "bind:_actionBtnNoSelected",
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
                                tableField: "ImportTaxMonth"
                            }
                        }
                    }}
                />
                <Modal
                    width={'90%'}
                    style={{ paddingBottom: '0', top: '0' }}
                    title="??????"
                    visible={this.state.visible}
                    footer={null}
                    onCancel={this.handleCancel}
                    destroyOnClose={this.handleCancel}
                    bodyStyle={{ padding: '10px', overflow: 'hidden' }}
                    centered={true}
                    wrapClassName={'modals'}
                >
                    {
                        idPreview ? <Spin spinning={loading}>
                            <iframe  width='100%' height={window.innerHeight * 0.8} frameBorder={0} onLoad={(obj) => {
                                this.setState({ loading: false });
                            }} src={`${ureport}preview?_u=minio:ZxSkMmReqPlan.preview.xml&_t=1,6&_n=${name}&access_token=${access_token}&id=${idPreview}
                            `}title="byLm"></iframe>
                        </Spin> : ''
                    }

                </Modal>
            </div>
        );
    }
}

export default index;