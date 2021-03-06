import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import moment from 'moment';
import { Avatar, message as Msg, Modal,Spin } from 'antd';
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
    firstRowIsSearch: false,
    isShowRowSelect: true
};
const configItem = {
    drawerConfig: {
        width: '1100px'
    },
    paginationConfig: false,
    actionBtnsPosition: "top",
    firstRowIsSearch: false,
    isShowRowSelect: true
};
const configBh = {
    paginationConfig: {
        position: 'bottom'
    },
    firstRowIsSearch: false,
    isShowRowSelect: false
};
class index extends Component {
    constructor(props) {
        super(props);
        this.state = {
            visibleBjdh: false,
            loadingBjdh: false,
            idItem:''
        }
    }
    componentDidMount() { }
    FloatMulTwo(arg1, arg2) {
        var m = 0, s1 = arg1.toString(), s2 = arg2.toString();
        try { m += s1.split(".")[1].length } catch (e) { }
        try { m += s2.split(".")[1].length } catch (e) { }
        return Number(s1.replace(".", "")) * Number(s2.replace(".", "")) / Math.pow(10, m)
    }
    handleCancelBjdh = () => {
        this.setState({ loadingBjdh: false, visibleBjdh: false });
    }
    render() {
        const { departmentId } = this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany;
        const { visibleBjdh, loadingBjdh,idItem } = this.state;
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
                        apiName: 'getZxSkTtReqPlanList',
                        otherParams: {
                            projectID: departmentId
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
                                title: '????????????',
                             
                                dataIndex: 'projectNumber',
                                key: 'projectNumber',
                                filter: true
                            },
                            form: {
                                field: 'projectNumber',
                                type: 'string',
                                // editDisabled:true,
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
                                title: '????????????',
                                dataIndex: 'projectName',
                                key: 'projectName',
                                onClick: 'detail',
                                filter: true
                            },
                            form: {
                                field: 'projectID',
                                required: true,
                                type: 'select',
                                editDisabled: true,
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
                                        departmentId: departmentId
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
                                        this.table.qnnForm.clearValues(['zxSkTtReqPlanItemList']);
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
                                dataIndex: 'createDate',
                                format: 'YYYY-MM-DD',
                                key: 'createDate',
                                filter: true,
                                fieldsConfig: {
                                    type: 'rangeDate',
                                    field: 'timeList'
                                }
                            },
                            form: {
                                type: 'date',
                                required: true,
                                editDisabled: true,
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
                                dataIndex: 'aurhorizedPersonnel',
                                key: 'aurhorizedPersonnel',
                                filter: true
                            },
                            form: {
                                type: 'string',
                                field: 'aurhorizedPersonnel',
                                editDisabled: true,
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
                                field: 'zxSkTtReqPlanItemList',
                                incToForm: true,
                                qnnTableConfig: {
                                    antd: {
                                        rowKey: 'id',
                                        size: 'small'
                                    },
                                    ...configItem,
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
                                                title: "<div>????????????<span style='color: #ff4d4f'>*</span></div>",
                                                dataIndex: 'cbsID',
                                                width: 200,
                                                key: 'cbsID',
                                                type: 'select',
                                                // type: 'selectByPaging',
                                                tdEdit: true,

                                            },
                                            form: {
                                                type: 'selectByPaging',
                                                field: 'cbsID',
                                                required: true,
                                                disabled: ({ record }) => {
                                                    if (record.addData === 'true') {
                                                        return true
                                                    } else {
                                                        return false
                                                    }
                                                },
                                                optionConfig: {
                                                    label: 'name',
                                                    value: 'iecsCBSID'
                                                },
                                                fetchConfig: {
                                                    apiName: "getZxEqIecsCBSOrgId",
                                                    otherParams: async () => {
                                                        const vals = await this.table.btnCallbackFn.qnnForm.getValues(false);
                                                        return {
                                                            orgID: vals.projectID,
                                                        }
                                                    }
                                                },
                                                onChange: (colVal, thisProps, tableBtnCallbackFn) => {
                                                    tableBtnCallbackFn.getEditedRowData().then((editRowData) => {
                                                        editRowData.cbsID = colVal;
                                                        tableBtnCallbackFn.setEditedRowData({
                                                            ...editRowData,
                                                        });
                                                    });
                                                },
                                            }
                                        },
                                        {
                                            table: {
                                                title: "<div>????????????<span style='color: #ff4d4f'>*</span></div>",
                                                width: 180,
                                                tooltip: 23,
                                                dataIndex: 'resCode',
                                                key: 'resCode',
                                                tdEdit: true,

                                            },
                                            // form: {
                                            //     type: 'selectByPaging',
                                            //     field: 'resCode',
                                            //     allowClear: false,
                                            //     optionConfig: {
                                            //         label: 'joinName',
                                            //         value: 'resCode'
                                            //     },
                                            //     fetchConfig: {
                                            //         apiName: "getZxSkResourceMaterialsListNameJoin",
                                            //         otherParams: async () => {
                                            //             const vals = await this.table.btnCallbackFn.qnnForm.getValues(false);
                                            //             return {
                                            //                 orgID: vals.projectID,
                                            //             }
                                            //         }
                                            //     },
                                            //     onChange: async (colVal, thisProps, tableBtnCallbackFn) => {
                                            //         // ???????????????????????? ????????????
                                            //         // const rowData = await tableBtnCallbackFn.getEditedRowData(false);
                                            //         const itemData = thisProps.itemData;
                                            //         // ????????????????????????
                                            //         await tableBtnCallbackFn.setEditedRowData({
                                            //             resName: itemData.resName,
                                            //             unit: itemData.unit,
                                            //             spec: itemData.spec
                                            //         });
                                            //     }
                                            // },
                                            form: {
                                                type: 'selectByQnnTable',
                                                label: '',
                                                field: 'resCode',
                                                required: true,
                                                allowClear: false,
                                                dropdownMatchSelectWidth: 900,
                                                optionConfig: {
                                                    label: 'resCode',
                                                    value: 'id'
                                                },
                                                disabled: ({ record }) => {
                                                    if (record.addData === 'true') {
                                                        return true
                                                    } else {
                                                        return false
                                                    }
                                                },
                                                qnnTableConfig: {
                                                    antd: {
                                                        rowKey: "id"
                                                    },
                                                    firstRowIsSearch: false,
                                                    fetchConfig: {
                                                        apiName: "getZxSkResourceMaterialsListNameJoin",
                                                        otherParams: () => {
                                                            return {
                                                                orgID: this.table.btnCallbackFn.qnnForm.form.getFieldValue('projectID'),
                                                            }
                                                        }
                                                    },
                                                    searchBtnsStyle: "inline",
                                                    formConfig: [
                                                        {
                                                            isInForm: false,
                                                            isInTable:false,
                                                            form: {
                                                                field:'id',
                                                                type: "string"
                                                            }
                                                        },
                                                        {
                                                            isInForm: false,
                                                            isInSearch:true,
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
                                                            isInSearch:true,
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
                                                            isInSearch:true,
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
                                                            isInSearch:true,
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
                                                        tableBtnCallbackFn.getEditedRowData().then((editRowData) => {
                                                            tableBtnCallbackFn.setEditedRowData({
                                                                ...editRowData,
                                                                resCode: itemData.itemData[0].resCode,
                                                                resName: itemData.itemData[0].resName,
                                                                unit: itemData.itemData[0].unit,
                                                                spec: itemData.itemData[0].spec,
                                                                lossRate: 0,
                                                                designNum: 0,
                                                                totalNum: 0
                                                            });
                                                        });
                                                    } else {
                                                        tableBtnCallbackFn.getEditedRowData(false).then((editRowData) => {
                                                            tableBtnCallbackFn.setEditedRowData({
                                                                ...editRowData,
                                                                resName: '',
                                                                unit: '',
                                                                spec: '',
                                                                lossRate: 0,
                                                                designNum: 0,
                                                                totalNum: 0
                                                            });
                                                        })
                                                    }
                                                },
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
                                                title: '?????????',
                                                dataIndex: 'lossRate',
                                                width: 100,
                                                key: 'lossRate',
                                                // tdEdit: true,

                                            },
                                            form: {
                                                type: 'number',
                                                field: 'lossRate',
                                                disabled: ({ record }) => {
                                                    if (record.addData === 'true') {
                                                        return true
                                                    } else {
                                                        return false
                                                    }
                                                },
                                                // precision: 2,
                                                onChange: async (colVal, thisProps, tableBtnCallbackFn) => {
                                                    if (colVal) {
                                                        clearTimeout(this.tdEditedTimer);
                                                        // ????????????????????????
                                                        // ????????????
                                                        this.tdEditedTimer = setTimeout(async () => {
                                                            // ???????????????????????? ????????????
                                                            const rowData = await tableBtnCallbackFn.getEditedRowData(false);
                                                            const newRowData = {
                                                                ...rowData
                                                            }
                                                            if (rowData.designNum) {
                                                                newRowData.totalNum = Number(this.FloatMulTwo(colVal, rowData.designNum)) + Number(rowData.designNum);
                                                            }
                                                            await tableBtnCallbackFn.setEditedRowData({
                                                                ...newRowData
                                                            });
                                                        }, 600)
                                                    }

                                                }
                                            }
                                        },
                                        {
                                            table: {
                                                title: '?????????????????????',
                                                dataIndex: 'designNum',
                                                key: 'designNum',
                                                width: 120,
                                                // tdEdit: true,
                                            },
                                            form: {
                                                type: 'number',
                                                field: 'designNum',
                                                min: 0,
                                                precision: 3,
                                                disabled: ({ record }) => {
                                                    if (record.addData === 'true') {
                                                        return true
                                                    } else {
                                                        return false
                                                    }
                                                },
                                                onChange: async (colVal, thisProps, tableBtnCallbackFn) => {
                                                    if (colVal) {
                                                        clearTimeout(this.tdEditedTimer);
                                                        // ????????????????????????
                                                        // ????????????
                                                        this.tdEditedTimer = setTimeout(async () => {
                                                            // ???????????????????????? ????????????
                                                            const rowData = await tableBtnCallbackFn.getEditedRowData(false);
                                                            const newRowData = {
                                                                ...rowData
                                                            }
                                                            if (rowData.lossRate) {
                                                                newRowData.totalNum = Number(this.FloatMulTwo(rowData.lossRate, colVal)) + Number(colVal);
                                                            }
                                                            await tableBtnCallbackFn.setEditedRowData({
                                                                ...newRowData
                                                            });
                                                        }, 600)
                                                    }

                                                }
                                            }
                                        },
                                        {
                                            table: {
                                                title: '??????????????????',
                                                dataIndex: 'totalNum',
                                                width: 120,
                                                key: 'totalNum',
                                                // tdEdit: true,
                                            },
                                            form: {
                                                type: 'number',
                                                field: 'totalNum',
                                                precision: 2,
                                                disabled: ({ record }) => {
                                                    if (record.addData === 'true') {
                                                        return true
                                                    } else {
                                                        return false
                                                    }
                                                }
                                            }
                                        },
                                        {
                                            isInTable: (val) => {
                                                if (val.qnnFormProps.funcCallBackParams.props.Pstate.drawerDetailTitle === '??????' || val.qnnFormProps.funcCallBackParams.props.Pstate.drawerDetailTitle === '??????') {
                                                    return true
                                                } else {
                                                    return false
                                                }
                                            },
                                            table: {
                                                title: '?????????',
                                                width: 100,
                                                dataIndex: 'changeNum',
                                                key: 'changeNum',
                                                tdEdit: true
                                            },
                                            form: {
                                                type: 'number',
                                                field: 'changeNum',
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
                                                tdEdit: true,
                                            },
                                            form: {
                                                type: 'textarea',
                                                field: 'remark',
                                                disabled: ({ record }) => {
                                                    if (record.addData === 'true') {
                                                        return true
                                                    } else {
                                                        return false
                                                    }
                                                },
                                                autoSize: {
                                                    minRows: 1,
                                                    maxRows: 3
                                                }
                                            }
                                        },
                                        {//????????????-??????????????????
                                            table: {
                                                title: '??????',
                                                dataIndex: 'warnFlag',
                                                key: 'warnFlag',
                                                align: 'center',
                                                fixed:'right',
                                                width: 50,
                                                render: (data) => {
                                                    return <Avatar shape="square" size={20} src={require('../../../imgs/file.png')} />;
                                                },
                                                onClick: (obj) => {
                                                    this.setState({
                                                        visibleBjdh: true,
                                                        idItem:obj.rowData.id
                                                    });
                                                }
                                            },
                                            isInForm: false
                                        },
                                    ],
                                    actionBtns: [
                                        {
                                            name: "addRow",
                                            icon: "plus",
                                            type: "primary",
                                            label: "??????",
                                        },
                                        //2021/01/20 ???
                                        // {
                                        //     name: "del",
                                        //     icon: 'delete',
                                        //     type: 'danger',
                                        //     label: "??????",
                                        // }
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
                                editDisabled: true,
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
                        },
                        {
                            table: {
                                title: '??????',
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
                            // isInForm: false,
                            form: {
                                type: 'string',
                                field: 'status',
                                hide: true
                            }
                        }
                    ]}
                    actionBtns={[
                        {
                            name: 'edit',
                            icon: 'edit',
                            type: 'primary',
                            label: '??????',
                            onClick: (obj) => {
                                if (obj.selectedRows[0].status === '1') {
                                    this.table.clearSelectedRows();
                                } else {
                                    Msg.warn('????????????????????????????????????')
                                    this.table.closeDrawer();
                                }
                            },
                            formBtns: [
                                {
                                    name: 'cancel',
                                    type: 'dashed',
                                    label: '??????',
                                },
                                {
                                    name: 'submit',
                                    type: 'primary',
                                    label: '??????',
                                    fetchConfig: {
                                        apiName: 'updateZxSkTtReqPlanCheckOver'
                                    }
                                }
                            ]
                        },
                        // {
                        //     name: 'diy',
                        //     type: 'primary',
                        //     label: '??????',
                        //     disabled: "bind:_actionBtnNoSelected",
                        //     onClick: (obj) => {

                        //     }
                        // }
                    ]}
                />
                <Modal
                    width='1200px'
                    style={{ top: '0' }}
                    title="??????"
                    visible={visibleBjdh}
                    footer={null}
                    onCancel={this.handleCancelBjdh}
                    bodyStyle={{
                        width: '1200px',
                        // height: document.documentElement.clientHeight * 0.6
                    }}
                    centered={true}
                    destroyOnClose={this.handleCancelBjdh}
                    wrapClassName={'modals'}
                >
                    <Spin spinning={loadingBjdh}>
                        <QnnTable
                            {...this.props}
                            fetch={this.props.myFetch}
                            upload={this.props.myUpload}
                            headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                            wrappedComponentRef={(me) => {
                                this.tableBh = me;
                            }}
                            {...configBh}
                            antd={{
                                rowKey: 'zxSkHttreqPlanItemId',
                                size: 'small',
                                scroll: {
                                    y: document.documentElement.clientHeight * 0.6
                                }
                            }}
                            fetchConfig={() => {
                                return {
                                    apiName: 'getZxSkHttreqPlanItemList',
                                    otherParams: {
                                        ttReqPlanItemID:idItem
                                    }
                                }
                            }}
                            formConfig={[
                                {
                                    isInTable: false,
                                    form: {
                                        field: 'zxSkHttreqPlanItemId',
                                        type: 'string',
                                        hide: true,
                                    }
                                },
                                {
                                    table: {
                                        title: '?????????',
                                        dataIndex: 'oper',
                                        key: 'oper'
                                    }
                                },
                                {
                                    table: {
                                        title: '????????????',
                                        dataIndex: 'opTime',
                                        key: 'opTime',
                                        format:'YYYY-MM-DD'
                                    }
                                },
                                {
                                    table: {
                                        title: '?????????',
                                        dataIndex: 'sourceNum',
                                        key: 'sourceNum',
                                    }
                                },
                                {
                                    table: {
                                        title: '?????????',
                                        dataIndex: 'changeNum',
                                        key: 'changeNum',
                                    }
                                },
                                {
                                    table: {
                                        title: '??????',
                                        dataIndex: 'op',
                                        key: 'op',
                                    }
                                }
                            ]}
                            actionBtns={[]}
                        />
                    </Spin>
                </Modal>
            </div>
        );
    }
}

export default index;