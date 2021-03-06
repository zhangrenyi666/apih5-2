import React, { Component } from "react";
import QnnTable from "qnn-table";
import QnnForm from "qnn-form";
import moment from 'moment';
import { Avatar, message as Msg, Modal, Spin, Tooltip } from 'antd';
const confirm = Modal.confirm;
const config = {
    antd: {
        rowKey: 'id',
        size: 'small',
        scroll: {
            y: document.documentElement.clientHeight * 0.6
        }
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
const configRuZhangItem = {
    drawerConfig: {
        width: '1100px'
    },
    paginationConfig: {
        position: 'bottom'
    },
    actionBtnsPosition: "top",
    firstRowIsSearch: false,
    isShowRowSelect: false
};
class index extends Component {
    constructor(props) {
        super(props);
        this.state = {
            visibleSend: false,
            loadingSend: false,
            visibleSendData: false,
            loadingSendData: false,
            abcVal: '',
            selectedRowsData: {},
            apiNameValue: '',
            contractNoVal: '',
            saveApiNameValue: '',
            ListId: '',
            materialsTransportationDetailID: null,
            materialsExpensesDetailID: null,
            clickBtnName: '',
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

    // ??????
    accAdd(arg1, arg2) {
        var r1, r2, m;
        try { r1 = arg1.toString().split(".")[1].length } catch (e) { r1 = 0 }
        try { r2 = arg2.toString().split(".")[1].length } catch (e) { r2 = 0 }
        m = Math.pow(10, Math.max(r1, r2))

        return (arg1 * m + arg2 * m) / m
    }
    // ????????????
    getDjbhFun(inOrgID, busDate) {
        const { myFetch } = this.props;
        myFetch('getZxSkStockTransferReceivingNo', {
            orgID: inOrgID,
            busDate: busDate ? new Date(busDate._d).getTime() : null
        }).then(
            ({ data, success, message }) => {
                if (success) {
                    this.table.qnnForm.form.setFieldsValue({
                        billNo: data
                    })
                } else {
                }
            }
        );
    }
    handleCancelSend = () => {
        this.setState({ visibleSend: false, loadingSend: false });
    }
    handleCancelSendData = () => {
        this.setState({ visibleSendData: false, loadingSendData: false });
    }

    setTotalAmtFun(rowData) {//?????????????????????????????????????????????
        let djje = 0;
        let djjeRu = 0;
        let tableData = this.formRz.form.getFieldsValue();
        tableData.reviewDetailList.map((item) => {
            if (item.conRevDetailId === rowData.conRevDetailId) {
                djje += Number(Number(rowData.changeContractSum ? rowData.changeContractSum : 0).toFixed(2));
                djjeRu += Number(Number(rowData.changeContractSumNoTax ? rowData.changeContractSumNoTax : 0).toFixed(2));
            } else {
                djje += Number(Number(item.changeContractSum ? item.changeContractSum : 0).toFixed(2));
                djjeRu += Number(Number(item.changeContractSumNoTax ? item.changeContractSumNoTax : 0).toFixed(2));
            }
            return item;
        })
        this.formRz.form.setFieldsValue({
            contractCost: Number(djje.toFixed(2)), //?????????
            // contractCostNoTax: djje //????????????
            contractCostNoTax: Number(djjeRu.toFixed(2)) //????????????
        });

    }
    //????????????
    dateChange(val, type) {
        var date = new Date();
        var year = date.getFullYear();
        var month = date.getMonth();
        // ?????????????????????
        if (month === 0) {//??????????????????
            year = year - 1;
            month = 12;
        } else {
            // month = month - 1;
        }
        var lastDay = new Date(year, month - 1, 26);//?????????26???
        var toDay = new Date(year, month, 26);//??????26???
        const stamp = (value) => {
            const s_time = moment(value).format('YYYY-MM-DD');
            const time = new Date(s_time).getTime();
            return time;
        }
        const time1 = stamp(val._d);//???????????????
        const time2 = stamp(lastDay);//?????????26???
        const time3 = stamp(toDay);//??????26???
        if (date.getDate() < 26) {//??????????????????26???
            if (time1 < time2) {
                Msg.warn('??????????????????26????????????????????????');
                this.table.qnnForm.form.setFieldsValue({
                    busDate: ''
                })
                if (type === 2) {
                    this.formEdit.form.setFieldsValue({
                        busDate: ''
                    })
                }
            }
        } else {//????????????????????????26???
            if (time1 < time3) {
                Msg.warn('??????????????????26????????????????????????');
                this.table.qnnForm.form.setFieldsValue({
                    busDate: ''
                })
                if (type === 2) {
                    this.formEdit.form.setFieldsValue({
                        busDate: ''
                    })
                }
            }
        }
        if (type === 1) {
            let inOrgID = this.table.qnnForm.form.getFieldsValue().inOrgID;
            let busDate = this.table.qnnForm.form.getFieldsValue().busDate;
            if (inOrgID) {
                this.getDjbhFun(inOrgID, busDate);
            }
        }

    }
    handleCancel = () => {
        this.setState({ visible: false, loading: false });
    }
    render() {
        const { visibleSend, loadingSend, apiNameValue, contractNoVal, saveApiNameValue, visibleSendData, loadingSendData, selectedRowsData, clickBtnName, lockProjectId, loading, idPreview, name } = this.state;

        const { ext1, departmentId, companyId, projectId } = this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany;
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
                        apiName: 'getZxSkStockTransferReceivingList',
                        otherParams: {
                            inOrgID: jurisdiction
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
                                onClick: 'detail',
                                dataIndex: 'billNo',
                                key: 'billNo',
                                width: 300,
                                filter: true,
                                render: (data, rowData) => {
                                    let color = '';
                                    if (rowData.nameType === '1') {
                                        color = 'red';
                                    } else if (rowData.nameType === '2') {
                                        color = 'orange';
                                    } else if (rowData.nameType === '3') {
                                        color = '#1890ff';
                                    } else {

                                    }
                                    return <Tooltip title={data}>
                                        <a style={{ color: color }}>{data}</a>
                                    </Tooltip>
                                }
                            },
                            form: {
                                field: 'billNo',
                                type: 'string',
                                addDisabled: true,
                                editDisabled: true,
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
                                }
                            },
                        },
                        {
                            table: {
                                title: '????????????',
                                dataIndex: 'inOrgName',
                                key: 'inOrgName',
                                width: 160,
                                // type: 'select',
                                filter: true,
                                fieldsConfig: {
                                    field: 'inOrgID',
                                    type: 'select',
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
                                field: 'inOrgID',
                                required: true,
                                allowClear: false,
                                type: 'selectByPaging',
                                showSearch: true,
                                editDisabled: true,
                                optionConfig: {
                                    label: 'orgName',
                                    value: 'orgID',
                                    linkageFields: {
                                        companyId: 'companyId',
                                        companyName: 'companyName'
                                    }
                                },
                                fetchConfig: {
                                    apiName: 'getZxCtContractListByOrgId',
                                    otherParams: {
                                        orgID: jurisdiction
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
                                children: [
                                    {
                                        field: 'whOrgID'
                                    },
                                    {
                                        field: 'resourceID'
                                    }
                                ],
                                onChange: (val, rowData) => {
                                    if (this.table && this.table.qnnForm) {
                                        this.table.qnnForm.clearValues(['zxSkStockTransItemReceivingList']);
                                        this.table.qnnForm.form.setFieldsValue({
                                            cantEdit: 'can',
                                            cantEditShui: 'can',
                                            inOrgName: rowData.itemData.orgName,
                                            whOrgID: ''
                                        })
                                        let busDate = this.table.qnnForm.form.getFieldsValue().busDate;
                                        if (busDate || val) {
                                            this.getDjbhFun(val, busDate);
                                        } else {
                                            this.table.qnnForm.form.setFieldsValue({
                                                billNo: null
                                            });
                                        }
                                    }


                                }
                            },

                        },
                        {
                            isInTable: false,
                            form: {
                                type: 'string',
                                field: 'companyId',
                                hide: true
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                type: 'string',
                                field: 'companyName',
                                hide: true
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                type: 'string',
                                field: 'inOrgName',
                                hide: true
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                type: 'string',
                                field: 'warehouseName',
                                hide: true
                            }
                        },
                        {
                            table: {
                                title: '??????',
                                dataIndex: 'warehouseName',
                                key: 'warehouseName',
                                width: 160,
                                filter: true,
                                fieldsConfig: {
                                    field: 'whOrgID',
                                    type: 'select',
                                    optionConfig: {
                                        label: 'warehouseName',
                                        value: 'id'
                                    },
                                    fetchConfig: {
                                        apiName: 'getZxSkWarehouseList',
                                        otherParams: {
                                            parentOrgID: jurisdiction
                                        }
                                    },
                                }
                            },
                            form: {
                                field: 'whOrgID',
                                required: true,
                                allowClear: false,
                                editDisabled: true,
                                type: 'select',
                                optionConfig: {
                                    label: 'warehouseName',
                                    value: 'id',
                                    linkageFields: {
                                        warehouseName: 'warehouseName'
                                    }
                                },
                                parent: 'inOrgID',
                                fetchConfig: {
                                    apiName: 'getZxSkWarehouseByParentOrgIDList',
                                    params: {
                                        parentOrgID: 'inOrgID'
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
                                onChange: () => {
                                    if (this.table && this.table.qnnForm) {
                                        this.table.qnnForm.clearValues(['zxSkStockTransItemReceivingList']);
                                    }
                                }
                            },
                        },
                        {
                            isInTable: false,
                            form: {
                                type: 'string',
                                field: 'resourceName',
                                hide: true
                            }
                        },
                        {
                            table: {
                                title: '????????????',
                                dataIndex: 'resourceName',
                                key: 'resourceName',
                                width: 160,
                                filter: true,
                                fieldsConfig: {
                                    field: 'resourceID',
                                    type: 'select',
                                    optionConfig: {
                                        label: 'catName',
                                        value: 'id'
                                    },
                                    fetchConfig: {
                                        apiName: 'getZxSkResCategoryMaterialsListResourceNotRevolve',
                                        otherParams: {
                                            parentOrgID: '1'
                                        }
                                    },
                                }
                            },
                            form: {
                                field: 'resourceID',
                                required: true,
                                type: 'select',
                                allowClear: false,
                                editDisabled: true,
                                showSearch: true,
                                optionConfig: {
                                    label: 'catName',
                                    value: 'id',
                                    linkageFields: {
                                        "resourceName": 'catName'
                                    }
                                },
                                parent: 'inOrgID',
                                fetchConfig: {
                                    apiName: 'getZxSkResCategoryMaterialsListResourceNotRevolve',
                                    params: {
                                        parentOrgID: 'inOrgID'
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
                                onChange: (val) => {

                                    if (this.table && this.table.qnnForm) {
                                        this.table.qnnForm.clearValues(['zxSkStockTransItemReceivingList']);
                                        this.table.qnnForm.form.setFieldsValue({
                                            cantEdit: 'can',
                                            cantEditShui: 'can'
                                        })
                                    }
                                }
                            },
                        },
                        {
                            table: {
                                title: '????????????',
                                dataIndex: 'outOrgName',
                                key: 'outOrgName',
                                width: 200,
                                filter: true
                            },
                            form: {
                                field: 'outOrgID',
                                required: true,
                                type: 'selectByPaging',
                                allowClear: false,
                                showSearch: true,
                                optionConfig: {
                                    label: 'customerName',
                                    value: 'zxCrCustomerNewId'
                                },
                                fetchConfig: {
                                    apiName: 'getZxCrCustomerNewList'
                                },
                                // pagination: {
                                //     pageSizeOptions: [
                                //         10, 20
                                //     ]
                                // },
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
                                onChange: (val, rowData) => {
                                    if (this.table && this.table.qnnForm) {
                                        this.table.qnnForm.clearValues(['zxSkStockTransItemReceivingList']);
                                    }
                                    if (rowData.itemData && rowData.itemData.customerName) {
                                        this.table.qnnForm.form.setFieldsValue({
                                            outOrgName: rowData.itemData.customerName
                                        })
                                    }

                                }
                            },
                        },
                        {
                            isInTable: false,
                            form: {
                                type: 'string',
                                field: 'outOrgName',
                                hide: true
                            }
                        },
                        {
                            table: {
                                title: '??????????????????',//???
                                dataIndex: 'outOrgType',
                                key: 'outOrgType',
                                width: 160
                            },
                            form: {
                                field: 'outOrgType',
                                required: true,
                                addDisabled: true,
                                editDisabled: true,
                                initialValue: '???????????????',
                                type: 'string',
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
                                }
                            },
                        },
                        {
                            table: {
                                title: '????????????',
                                width: 100,
                                dataIndex: 'totalAmt',
                                key: 'totalAmt',
                                filter: true,
                                fieldsConfig: {
                                    type: 'number',
                                    field: 'amtList',
                                    range: true,
                                }
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '????????????',
                                width: 140,
                                dataIndex: 'materialSource',
                                key: 'materialSource',
                                type: 'select',
                                filter: true
                            },
                            form: {
                                type: 'select',
                                allowClear: false,
                                field: 'materialSource',
                                optionConfig: {
                                    label: 'itemName',
                                    value: 'itemId'
                                },
                                fetchConfig: {
                                    apiName: "getBaseCodeSelect",
                                    otherParams: {
                                        itemId: 'wuZiLaiYuan'
                                    }
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
                                title: '?????????',
                                dataIndex: 'voucherNo',
                                key: 'voucherNo',
                                filter: true
                            },
                            form: {
                                type: 'string',
                                field: 'voucherNo',
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
                                width: 120,
                                dataIndex: 'busDate',
                                format: 'YYYY-MM-DD',
                                key: 'busDate',
                                filter: true,
                                fieldsConfig: {
                                    type: 'rangeDate',
                                    field: 'timeList'
                                },
                            },
                            form: {
                                type: 'date',
                                required: true,
                                field: 'busDate',
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
                                },
                                onChange: (val) => {
                                    if (this.table && this.table.qnnForm) {
                                        if (val) {
                                            this.dateChange(val, 1);
                                        } else {
                                            this.table.qnnForm.form.setFieldsValue({
                                                billNo: null
                                            });
                                        }

                                    }

                                }
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                field: 'cantEdit',
                                type: 'string',
                                hide: true,
                                initialValue: (obj) => {
                                    if (obj.Pstate.drawerDetailTitle === '??????') {
                                        return 'no'
                                    } else if (obj.Pstate.drawerDetailTitle === '??????') {
                                        return 'no'
                                    } else {
                                        return 'can'
                                    }
                                }
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                field: 'cantEditShui',
                                type: 'string',
                                hide: true,
                                initialValue: (obj) => {
                                    if (obj.Pstate.drawerDetailTitle === '??????') {
                                        return 'no'
                                    } else if (obj.Pstate.drawerDetailTitle === '??????') {
                                        return 'no'
                                    } else {
                                        return 'can'
                                    }
                                }
                            }
                        },
                        {
                            isInTable: false,
                            table: {
                                title: '????????????',
                                dataIndex: 'purchType',
                                key: 'purchType',
                                type: 'select',
                            },
                            form: {
                                type: 'select',
                                field: 'purchType',
                                initialValue: '????????????',
                                optionConfig: {
                                    label: 'itemName',
                                    value: 'itemId'
                                },
                                fetchConfig: {
                                    apiName: "getBaseCodeSelect",
                                    otherParams: {
                                        itemId: 'caiGouLeiBie'
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
                                onChange: (val) => {
                                    if (val === '?????????') {//2021.01.29??????
                                        this.setState({
                                            showTicket: 'show'
                                        })
                                        this.table.qnnForm.form.setFieldsValue({
                                            contractDisabled: 'false',
                                            cantEdit: 'no',
                                            cantEditShui: 'can'
                                        })
                                    } else {
                                        this.table.qnnForm.form.setFieldsValue({
                                            contractDisabled: 'true',
                                            cantEdit: 'can',
                                            cantEditShui: 'can'
                                        })
                                        this.setState({
                                            showTicket: 'hide'
                                        })
                                    }
                                    this.table.qnnForm.form.setFieldsValue({
                                        purchaseContractID: '',
                                        isDeduct: '',
                                        taxRate: '',
                                        contractNo: ''
                                    })
                                    if (this.table && this.table.qnnForm) {
                                        this.table.qnnForm.clearValues(['zxSkStockTransItemReceivingList']);
                                    }
                                }
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                type: 'string',
                                field: 'contractDisabled',
                                hide: true,
                                initialValue: (obj) => {
                                    if (obj.Pstate.drawerDetailTitle === '??????') {
                                        return 'true'
                                    } else if (obj.Pstate.drawerDetailTitle === '??????') {
                                        if (this.state.abcVal === 'true') {
                                            return 'true'
                                        } else {
                                            return 'false'
                                        }

                                    } else {
                                        return 'true'
                                    }
                                }
                            }
                        },
                        {
                            isInTable: false,
                            table: {
                                title: '????????????',
                                dataIndex: 'purchaseContractID',
                                key: 'purchaseContractID',
                                type: 'select'
                            },
                            form: {
                                type: 'select',
                                field: 'purchaseContractID',
                                allowClear: false,
                                optionConfig: {
                                    label: 'contractName',
                                    value: 'zxCtSuppliesContractId',
                                    linkageFields: {
                                        contractNo: 'contractNo',
                                        isDeduct: 'isDeduct'
                                    }
                                },
                                dependenciesReRender: true,//????????????-??????
                                dependencies: ['inOrgID', 'outOrgID'],
                                fetchConfig: {
                                    apiName: 'getZxCtSuppliesContractListByFirstId',
                                    otherParams: (val) => {
                                        let inOrgIDVal = '';
                                        let outOrgIDVal = '';
                                        if (val.btnCallbackFn?.form) {
                                            let aa = val.btnCallbackFn.form.getFieldsValue();
                                            inOrgIDVal = aa.inOrgID;
                                            outOrgIDVal = aa.outOrgID;
                                        } else {
                                            inOrgIDVal = '';
                                            outOrgIDVal = '';
                                        }
                                        return {
                                            firstId: inOrgIDVal,
                                            secondID: outOrgIDVal,
                                            pp9: "1",
                                            code7: 'WZ'
                                        }
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
                                condition: [
                                    {
                                        regex: {
                                            contractDisabled: 'true',
                                            purchType: ['!', '?????????'],
                                        },
                                        action: ['disabled', 'unRequired'],
                                    },
                                    {
                                        regex: {
                                            purchType: '?????????'
                                        },
                                        action: 'required'
                                    }
                                ]
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: '????????????',
                                field: 'contractNo',
                                type: 'string',
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
                                },
                            }
                        },
                        {
                            isInTable: false,
                            table: {
                                title: '????????????',
                                dataIndex: 'isDeduct',
                                key: 'isDeduct',
                                type: 'select'
                            },
                            form: {
                                field: 'isDeduct',
                                type: 'select',
                                // initialValue: '0',
                                optionConfig: {
                                    label: 'label',
                                    value: 'value',
                                },
                                optionData: [
                                    {
                                        label: "???",
                                        value: "0"
                                    },
                                    {
                                        label: "???",
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
                                },
                                condition: [
                                    {
                                        regex: { cantEdit: 'no', },
                                        action: 'disabled',
                                    },
                                ]
                            }
                        },
                        {
                            isInTable: false,
                            table: {
                                title: '??????(%)',
                                dataIndex: 'taxRate',
                                key: 'taxRate'
                            },
                            form: {
                                type: 'select',
                                field: 'taxRate',
                                allowClear: false,
                                optionConfig: {
                                    label: 'itemName',
                                    value: 'itemId'
                                },
                                fetchConfig: {
                                    apiName: "getBaseCodeSelect",
                                    otherParams: {
                                        itemId: 'shuiLv'
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
                                condition: [
                                    {
                                        regex: { cantEditShui: 'no', },
                                        action: 'disabled',
                                    },
                                ]
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                type: 'qnnTable',
                                field: 'zxSkStockTransItemReceivingList',
                                incToForm: true,
                                dependencies: ['purchType'],
                                dependenciesReRender: true,
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
                                                title: '????????????',
                                                width: 180,
                                                dataIndex: 'resCode',
                                                key: 'resCode',
                                                tdEdit: true
                                            },
                                            form: {
                                                type: 'selectByQnnTable',
                                                label: '',
                                                allowClear: false,
                                                required: true,
                                                field: 'resCode',
                                                dropdownMatchSelectWidth: 900,
                                                optionConfig: async (obj) => {
                                                    const vals = await this.table.btnCallbackFn.qnnForm.getValues(false);
                                                    if (vals.purchType === '?????????') {
                                                        return {
                                                            label: 'workNo',
                                                            value: 'zxCtSuppliesContrShopListId'
                                                        }
                                                    } else {
                                                        return {
                                                            label: 'resCode',
                                                            value: 'id'
                                                        }
                                                    }

                                                },
                                                qnnTableConfig: {
                                                    antd: {
                                                        rowKey: "id"
                                                    },
                                                    firstRowIsSearch: false,
                                                    fetchConfig: (obj) => {
                                                        const vals = obj?.props?.qnnFormProps?.form?.getFieldsValue?.();
                                                        if (vals?.purchType === '?????????') {
                                                            return {
                                                                apiName: 'getZxCtSuppliesContrShopListListByContIDAddPage',
                                                                otherParams: {
                                                                    isTurnover: 1,//??????????????????0?????????1????????????
                                                                    contractID: vals?.purchaseContractID,
                                                                    workTypeID: vals?.resourceID,
                                                                }
                                                            }
                                                        } else {
                                                            return {
                                                                apiName: 'getZxSkResourceMaterialsListNameJoinResource',
                                                                otherParams: {
                                                                    id: vals?.resourceID,
                                                                    purchaseContractID: vals?.purchaseContractID,
                                                                    contractNo: vals?.contractNo,
                                                                    limit: 999,
                                                                    page: 1
                                                                }
                                                            }
                                                        }
                                                    },
                                                    searchBtnsStyle: "inline",
                                                    formConfig: [
                                                        {
                                                            isInForm: false,
                                                            isInTable: false,
                                                            form: {
                                                                field: 'zxCtSuppliesContrShopListId',
                                                                type: "string"
                                                            }
                                                        },
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
                                                            isInTable: async () => {
                                                                const vals = await this.table.btnCallbackFn.qnnForm.getValues(false);
                                                                return vals.purchType === '?????????' ? false : true
                                                            },
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
                                                            isInTable: async () => {
                                                                const vals = await this.table.btnCallbackFn.qnnForm.getValues(false);
                                                                return vals.purchType === '?????????' ? true : false
                                                            },
                                                            table: {
                                                                dataIndex: "workNo",
                                                                title: "??????",
                                                            },
                                                            form: {
                                                                type: "string"
                                                            }
                                                        },
                                                        {
                                                            isInForm: false,
                                                            isInSearch: true,
                                                            isInTable: async () => {
                                                                const vals = await this.table.btnCallbackFn.qnnForm.getValues(false);
                                                                return vals.purchType === '?????????' ? false : true
                                                            },
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
                                                            isInTable: async () => {
                                                                const vals = await this.table.btnCallbackFn.qnnForm.getValues(false);
                                                                return vals.purchType === '?????????' ? true : false
                                                            },
                                                            table: {
                                                                dataIndex: "workName",
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
                                                onChange: async (colVal, thisProps, tableBtnCallbackFn) => {
                                                    const vals = await this.table.btnCallbackFn.qnnForm.getValues(false);
                                                    if (vals.purchType === '?????????') {
                                                        let shuilvHeTong = 0;
                                                        if (this.table.qnnForm.form.getFieldsValue().taxRate) {
                                                            shuilvHeTong = Number((this.table.qnnForm.form.getFieldsValue().taxRate) * 0.01);
                                                        }
                                                        const item = thisProps.itemData;
                                                        item.id = item.zxCtSuppliesContrShopListId;
                                                        item.resCode = item.workNo;
                                                        item.resName = item.workName;
                                                        item.resID = item.workID;
                                                        item.resUnit = item.unit;
                                                        item.inQty = item.qty;
                                                        item.inPrice = item.price;
                                                        item.inPriceNoTax = Number((item.inPrice / Number(1 + shuilvHeTong)).toFixed(6));//???????????????
                                                        if (item.inQty && item.inPrice) {
                                                            item.resAllFeeNoTax = Number(((this.FloatMulTwo(item.inQty, item.inPrice)) / Number(1 + shuilvHeTong)).toFixed(2));//???????????????
                                                        } else {
                                                            item.resAllFeeNoTax = 0;
                                                        }
                                                        if (item.inQty && item.inPrice) {
                                                            item.resAllFee = Number(this.FloatMulTwo(item.inQty, item.inPrice).toFixed(2));//????????????
                                                        } else {
                                                            item.resAllFee = 0;
                                                        }
                                                        item.remark = item.remarks;
                                                        item.inFee = 0;
                                                        item.ysFee = 0;
                                                        item.inFeeTotal = 0;
                                                        item.ysFeeTotal = 0;
                                                        item.precollecte = '0';
                                                        item.resAllFeeTax = Number((item.resAllFee - item.resAllFeeNoTax).toFixed(2));//??????
                                                        if (item.inQty && item.inPrice) {
                                                            item.inAmtAll = Number(this.FloatMulTwo(item.inQty, item.inPrice).toFixed(2));
                                                            item.inAmtAllBeiYong = Number(this.FloatMulTwo(item.inQty, item.inPrice).toFixed(2));
                                                        }
                                                        if (this.table.qnnForm.form.getFieldsValue().isDeduct === '0') {
                                                            item.inAmt = Number(Number(item.resAllFee).toFixed(2));
                                                            item.inAmtBeiYong = Number(Number(item.resAllFee).toFixed(2));
                                                        } else {
                                                            item.inAmt = Number(Number(item.resAllFeeNoTax).toFixed(2));
                                                            item.inAmtBeiYong = Number(Number(item.resAllFeeNoTax).toFixed(2));
                                                        }
                                                        console.log(colVal);
                                                        console.log(item);
                                                        console.log(thisProps.itemData);
                                                        await tableBtnCallbackFn.setEditedRowData({
                                                            ...item,
                                                            materialsExpensesDetailID: '',
                                                            materialsTransportationDetailID: ''
                                                        });
                                                    } else {
                                                        const itemData = thisProps.itemData;
                                                        await tableBtnCallbackFn.setEditedRowData({
                                                            resCode: itemData.resCode,
                                                            resName: itemData.resName,
                                                            resUnit: itemData.unit,
                                                            spec: itemData.spec,
                                                            resID: itemData.id,
                                                            inQty: 0,
                                                            inPriceNoTax: 0,
                                                            resAllFeeNoTax: 0,
                                                            resAllFeeTax: 0,
                                                            inPrice: 0,
                                                            resAllFee: 0,
                                                            inAmt: 0,
                                                            inFee: 0,
                                                            inFeeTotal: 0,
                                                            ysFee: 0,
                                                            materialsExpensesDetailID: '',
                                                            materialsTransportationDetailID: '',
                                                            ysFeeTotal: 0,
                                                            inAmtAll: 0,

                                                        });
                                                        this.table.qnnForm.form.setFieldsValue({
                                                            cantEdit: 'no',
                                                            cantEditShui: 'no'
                                                        })
                                                    }
                                                }
                                                // onChange: async (colVal, thisProps, tableBtnCallbackFn) => {
                                                //     if (colVal) {
                                                //         const itemData = thisProps.itemData;
                                                //         await tableBtnCallbackFn.setEditedRowData({
                                                //             resCode: itemData.resCode,
                                                //             resName: itemData.resName,
                                                //             resUnit: itemData.unit,
                                                //             spec: itemData.spec,
                                                //             resID: itemData.id,
                                                //             inQty: 0,
                                                //             inPriceNoTax: 0,
                                                //             resAllFeeNoTax: 0,
                                                //             resAllFeeTax: 0,
                                                //             inPrice: 0,
                                                //             resAllFee: 0,
                                                //             inAmt: 0,
                                                //             inFee: 0,
                                                //             inFeeTotal: 0,
                                                //             ysFee: 0,
                                                //             materialsExpensesDetailID: '',
                                                //             materialsTransportationDetailID: '',
                                                //             ysFeeTotal: 0,
                                                //             inAmtAll: 0,

                                                //         });
                                                //         this.table.qnnForm.form.setFieldsValue({
                                                //             cantEdit: 'no',
                                                //             cantEditShui: 'no'
                                                //         })
                                                //     } else {
                                                //         tableBtnCallbackFn.getEditedRowData(false).then((editRowData) => {
                                                //             tableBtnCallbackFn.setEditedRowData({
                                                //                 ...editRowData,
                                                //                 resID: '',
                                                //                 resName: '',
                                                //                 resUnit: '',
                                                //                 spec: '',
                                                //                 inQty: 0,
                                                //                 inPriceNoTax: 0,
                                                //                 resAllFeeNoTax: 0,
                                                //                 resAllFeeTax: 0,
                                                //                 inPrice: 0,
                                                //                 resAllFee: 0,
                                                //                 inAmtAll: 0,
                                                //                 inAmt: 0,
                                                //                 inFee: 0,
                                                //                 inFeeTotal: 0,
                                                //                 ysFee: 0,
                                                //                 materialsExpensesDetailID: '',
                                                //                 materialsTransportationDetailID: '',
                                                //                 ysFeeTotal: 0

                                                //             });
                                                //         })
                                                //     }
                                                // }
                                            }
                                        },
                                        {
                                            isInTable: false,
                                            form: {
                                                field: 'resID',
                                                hide: true
                                            }
                                        },
                                        {
                                            table: {
                                                title: '????????????',
                                                width: 150,
                                                dataIndex: 'resName',
                                                key: 'resName',
                                                // tooltip: 10,
                                            },
                                            form: {
                                                type: 'string',
                                                field: 'resName'
                                            }
                                        },
                                        {
                                            table: {
                                                title: '????????????',
                                                dataIndex: 'spec',
                                                key: 'spec',
                                                width: 100
                                            },
                                            form: {
                                                type: 'string',
                                                field: 'spec'
                                            }
                                        },
                                        {
                                            table: {
                                                title: '????????????',
                                                dataIndex: 'resUnit',
                                                key: 'resUnit',
                                                width: 80,
                                                // tooltip: 10,
                                            },
                                            form: {
                                                type: 'string',
                                                field: 'resUnit'
                                            }
                                        },
                                        {
                                            table: {
                                                title: '????????????',
                                                width: 100,
                                                dataIndex: 'inQty',
                                                key: 'inQty',
                                                tdEdit: true
                                            },
                                            form: {
                                                type: 'number',
                                                field: 'inQty',
                                                precision: 3, //????????????
                                                onChange: async (colVal, thisProps, tableBtnCallbackFn) => {
                                                    console.log(colVal);
                                                    if (colVal >= 0 && typeof (colVal) === 'number') {
                                                        const rowData = await tableBtnCallbackFn.getEditedRowData(false);
                                                        const newRowData = {
                                                            ...rowData
                                                        }
                                                        let shuilv = 0;
                                                        if (this.table.qnnForm.form.getFieldsValue().taxRate) {
                                                            shuilv = Number((this.table.qnnForm.form.getFieldsValue().taxRate) * 0.01);
                                                        }
                                                        // ?????????-??????????????????????????????
                                                        if (this.table.qnnForm.form.getFieldsValue().purchType === '?????????') {

                                                        } else {
                                                            newRowData.ysFeeTotal = rowData.ysFee ? Number(rowData.ysFee).toFixed(2) : 0;
                                                            newRowData.inFeeTotal = rowData.inFee ? Number(rowData.inFee).toFixed(2) : 0;
                                                        }


                                                        if (rowData.inPrice) {
                                                            newRowData.resAllFeeNoTax = Number(((this.FloatMulTwo(rowData.inQty, rowData.inPrice)) / Number(1 + shuilv)).toFixed(2));//???????????????
                                                            newRowData.inPriceNoTax = (rowData.inPrice / Number(1 + shuilv)).toFixed(6);//???????????????
                                                            newRowData.resAllFee = Number(this.FloatMulTwo(rowData.inQty, rowData.inPrice).toFixed(2));//????????????

                                                            newRowData.inAmtAll = Number(parseFloat(Number(this.FloatMulTwo(rowData.inQty, rowData.inPrice)) + Number(newRowData.ysFeeTotal) + Number(newRowData.inFeeTotal)).toFixed(2));//????????????
                                                            newRowData.inAmtAllBeiYong = Number(parseFloat(Number(this.FloatMulTwo(rowData.inQty, rowData.inPrice)) + Number(newRowData.ysFeeTotal) + Number(newRowData.inFeeTotal)).toFixed(2));//????????????

                                                            if (this.table.qnnForm.form.getFieldsValue().isDeduct === '0') {//????????????-0???   1???
                                                                newRowData.inAmt = Number(parseFloat(Number(newRowData.resAllFee) + Number(newRowData.ysFee) + Number(newRowData.inFee)).toFixed(2));//??????-??????
                                                                newRowData.inAmtBeiYong = Number(parseFloat(Number(newRowData.resAllFee) + Number(newRowData.ysFee) + Number(newRowData.inFee)).toFixed(2));//??????-??????
                                                            } else {
                                                                newRowData.inAmt = Number(parseFloat(Number(newRowData.resAllFeeNoTax) + Number(newRowData.ysFee) + Number(newRowData.inFee)).toFixed(2));//??????-??????
                                                                newRowData.inAmtBeiYong = Number(parseFloat(Number(newRowData.resAllFeeNoTax) + Number(newRowData.ysFee) + Number(newRowData.inFee)).toFixed(2));//??????-??????

                                                            }
                                                            if (newRowData.resAllFee && newRowData.resAllFeeNoTax) {
                                                                newRowData.resAllFeeTax = Number((newRowData.resAllFee - newRowData.resAllFeeNoTax).toFixed(2));//??????
                                                            }
                                                        }
                                                        if (rowData.inPrice) {
                                                            newRowData.inPriceNoTax = Number((rowData.inPrice / Number(1 + shuilv)).toFixed(6));//???????????????
                                                        }
                                                        await tableBtnCallbackFn.setEditedRowData({
                                                            ...newRowData
                                                        });
                                                        this.table.qnnForm.form.setFieldsValue({
                                                            cantEdit: 'no',
                                                            cantEditShui: 'no'
                                                        })
                                                    }
                                                }
                                            }
                                        },
                                        {
                                            table: {
                                                title: '???????????????',
                                                width: 120,
                                                dataIndex: 'inPriceNoTax',
                                                key: 'inPriceNoTax'
                                            },
                                            form: {
                                                type: 'number',
                                                field: 'inPriceNoTax',
                                                precision: 6, //????????????
                                            }
                                        },
                                        {
                                            table: {
                                                title: '???????????????',
                                                width: 120,
                                                dataIndex: 'resAllFeeNoTax',
                                                key: 'resAllFeeNoTax'
                                            },
                                            form: {
                                                type: 'number',
                                                field: 'resAllFeeNoTax',
                                                precision: 2, //????????????
                                            }
                                        },
                                        {
                                            table: {
                                                title: '??????',
                                                width: 120,
                                                dataIndex: 'resAllFeeTax',
                                                key: 'resAllFeeTax'
                                            },
                                            form: {
                                                type: 'number',
                                                field: 'resAllFeeTax',
                                                precision: 2, //????????????
                                            }
                                        },
                                        {
                                            table: {
                                                title: '????????????',
                                                width: 120,
                                                dataIndex: 'inPrice',
                                                key: 'inPrice',
                                                tdEdit: true
                                            },
                                            form: {
                                                type: 'number',
                                                field: 'inPrice',
                                                required: true,
                                                precision: 6,
                                                onChange: async (colVal, thisProps, tableBtnCallbackFn) => {
                                                    console.log(colVal);
                                                    if (colVal) {
                                                        // ???????????????????????? ????????????
                                                        const rowData = await tableBtnCallbackFn.getEditedRowData(false);
                                                        const newRowData = {
                                                            ...rowData
                                                        }
                                                        let shuilv = 0;
                                                        if (this.table.qnnForm.form.getFieldsValue().taxRate) {
                                                            shuilv = Number((this.table.qnnForm.form.getFieldsValue().taxRate) * 0.01);
                                                        }
                                                        newRowData.ysFeeTotal = rowData.ysFee ? Number(rowData.ysFee).toFixed(2) : 0;
                                                        newRowData.inFeeTotal = rowData.inFee ? Number(rowData.inFee).toFixed(2) : 0;
                                                        if (rowData.inQty) {
                                                            newRowData.resAllFeeNoTax = ((this.FloatMulTwo(rowData.inQty, rowData.inPrice)) / Number(1 + shuilv)).toFixed(2);//???????????????
                                                            newRowData.resAllFee = Number(this.FloatMulTwo(rowData.inQty, rowData.inPrice).toFixed(2));//????????????
                                                            newRowData.inAmtAll = parseFloat(Number(this.FloatMulTwo(rowData.inQty, rowData.inPrice)) + Number(newRowData.ysFeeTotal) + Number(newRowData.inFeeTotal)).toFixed(2);//????????????
                                                            newRowData.inAmtAllBeiYong = parseFloat(Number(this.FloatMulTwo(rowData.inQty, rowData.inPrice)) + Number(newRowData.ysFeeTotal) + Number(newRowData.inFeeTotal)).toFixed(2);//????????????

                                                            if (this.table.qnnForm.form.getFieldsValue().isDeduct === '0') {//????????????-0???   1???

                                                                newRowData.inAmt = parseFloat(Number(newRowData.resAllFee) + Number(newRowData.ysFee) + Number(newRowData.inFee)).toFixed(2);//??????-??????
                                                                newRowData.inAmtBeiYong = parseFloat(Number(newRowData.resAllFee) + Number(newRowData.ysFee) + Number(newRowData.inFee)).toFixed(2);//??????-??????
                                                            } else {

                                                                newRowData.inAmt = parseFloat(Number(newRowData.resAllFeeNoTax) + Number(newRowData.ysFee) + Number(newRowData.inFee)).toFixed(2);//??????-??????
                                                                newRowData.inAmtBeiYong = parseFloat(Number(newRowData.resAllFeeNoTax) + Number(newRowData.ysFee) + Number(newRowData.inFee)).toFixed(2);//??????-??????

                                                            }

                                                            if (newRowData.resAllFee && newRowData.resAllFeeNoTax) {
                                                                newRowData.resAllFeeTax = (newRowData.resAllFee - newRowData.resAllFeeNoTax).toFixed(2);//??????
                                                            }
                                                        }
                                                        newRowData.inPriceNoTax = (rowData.inPrice / Number(1 + shuilv)).toFixed(6);//???????????????
                                                        await tableBtnCallbackFn.setEditedRowData({
                                                            ...newRowData
                                                        });
                                                        this.table.qnnForm.form.setFieldsValue({
                                                            cantEdit: 'no',
                                                            cantEditShui: 'no'
                                                        })
                                                    }
                                                }
                                            }
                                        },
                                        {
                                            table: {
                                                title: '????????????',
                                                width: 120,
                                                dataIndex: 'resAllFee',
                                                key: 'resAllFee'
                                            },
                                            form: {
                                                type: 'number',
                                                field: 'resAllFee',
                                                precision: 2
                                            }
                                        },
                                        {
                                            table: {
                                                title: '???????????????',
                                                width: 200,
                                                dataIndex: 'inFee',
                                                key: 'inFee',
                                                tdEdit: true,
                                                fieldConfig: {
                                                    disabled: ({ record }) => {
                                                        const formVals = this.table.getDeawerValuesSync();
                                                        if (formVals.purchType === '?????????') {
                                                            return true
                                                        } else {
                                                            return false
                                                        }
                                                    },
                                                }
                                            },
                                            form: {
                                                type: 'number',
                                                field: 'inFee',
                                                precision: 2,
                                                onChange: async (colVal, thisProps, tableBtnCallbackFn) => {
                                                    if (colVal) {
                                                        // ???????????????????????? ????????????
                                                        const rowData = await tableBtnCallbackFn.getEditedRowData(false);
                                                        const newRowData = {
                                                            ...rowData
                                                        }
                                                        let shuilv = 0;
                                                        if (this.table.qnnForm.form.getFieldsValue().taxRate) {
                                                            shuilv = Number((this.table.qnnForm.form.getFieldsValue().taxRate) * 0.01);
                                                        }
                                                        newRowData.inFeeTotal = rowData.inFee.toFixed(2);
                                                        if (rowData.ysFeeTotal) {

                                                        } else {
                                                            rowData.ysFeeTotal = 0;
                                                        }
                                                        if (rowData.inQty) {
                                                            newRowData.resAllFeeNoTax = ((this.FloatMulTwo(rowData.inQty, rowData.inPrice)) / Number(1 + shuilv)).toFixed(2);//???????????????
                                                            newRowData.resAllFee = Number(this.FloatMulTwo(rowData.inQty, rowData.inPrice).toFixed(2));//????????????
                                                            newRowData.inAmtAll = parseFloat(Number(this.FloatMulTwo(rowData.inQty, rowData.inPrice)) + Number(newRowData.inFeeTotal) + Number(rowData.ysFeeTotal)).toFixed(2);//????????????
                                                            newRowData.inAmtAllBeiYong = parseFloat(Number(this.FloatMulTwo(rowData.inQty, rowData.inPrice)) + Number(newRowData.inFeeTotal) + Number(rowData.ysFeeTotal)).toFixed(2);//????????????

                                                            if (this.table.qnnForm.form.getFieldsValue().isDeduct === '0') {//????????????-0???   1???
                                                                newRowData.inAmt = parseFloat(Number(newRowData.resAllFee) + Number(rowData.ysFee) + Number(newRowData.inFee)).toFixed(2);//??????-??????
                                                                newRowData.inAmtBeiYong = parseFloat(Number(newRowData.resAllFee) + Number(rowData.ysFee) + Number(newRowData.inFee)).toFixed(2);//??????-??????

                                                            } else {
                                                                newRowData.inAmt = parseFloat(Number(newRowData.resAllFeeNoTax) + Number(rowData.ysFee) + Number(newRowData.inFee)).toFixed(2);//??????-??????
                                                                newRowData.inAmtBeiYong = parseFloat(Number(newRowData.resAllFeeNoTax) + Number(rowData.ysFee) + Number(newRowData.inFee)).toFixed(2);//??????-??????
                                                            }
                                                            if (newRowData.resAllFee && newRowData.resAllFeeNoTax) {
                                                                newRowData.resAllFeeTax = (newRowData.resAllFee - newRowData.resAllFeeNoTax).toFixed(2);//??????
                                                            }
                                                        }
                                                        newRowData.inPriceNoTax = (rowData.inPrice / Number(1 + shuilv)).toFixed(6);//???????????????
                                                        await tableBtnCallbackFn.setEditedRowData({
                                                            ...newRowData
                                                        });
                                                        this.table.qnnForm.form.setFieldsValue({
                                                            cantEdit: 'no',
                                                            cantEditShui: 'no'
                                                        })
                                                    }
                                                }
                                            }
                                        },
                                        {
                                            isInTable: (obj) => {
                                                const formVals = this.table.getDeawerValuesSync();
                                                if (obj.qnnFormProps.funcCallBackParams.props.Pstate.drawerDetailTitle === '??????') {
                                                    return false
                                                } else {
                                                    if (formVals.purchType === '?????????') {
                                                        return true
                                                    } else {
                                                        return false
                                                    }
                                                }
                                            },
                                            table: {
                                                title: '??????',
                                                dataIndex: 'warnFlag',
                                                key: 'warnFlag',
                                                align: 'center',
                                                width: 50,
                                                render: (data) => {
                                                    return <Avatar shape="square" size={20} src={require('../../../imgs/file.png')} />;
                                                },
                                                onClick: (obj) => {
                                                    let conNo = this.table.qnnForm.form.getFieldsValue();
                                                    console.log(conNo);
                                                    if (conNo.contractNo) {
                                                        if (obj.rowData.materialsExpensesDetailID) {
                                                            this.setState({
                                                                visibleSend: true,
                                                                apiNameValue: 'getZxSkStockFeeDetail',
                                                                contractNoVal: conNo.contractNo,
                                                                ListId: obj.rowData.id,
                                                                materialsExpensesDetailID: obj.rowData.materialsExpensesDetailID,
                                                                saveApiNameValue: 'updateZxSkStockFee',
                                                                clickBtnName: '?????????'
                                                            });
                                                        } else {
                                                            this.setState({
                                                                visibleSend: true,
                                                                // apiNameValue: 'getQiTaContractList',
                                                                apiNameValue: 'getZxCtFsContractQiTaList',
                                                                contractNoVal: conNo.contractNo,
                                                                // contractNoVal: conNo.fromContractNo,
                                                                ListId: obj.rowData.id,
                                                                saveApiNameValue: 'addZxSkStockFee',
                                                                clickBtnName: '?????????'
                                                            });
                                                        }

                                                    } else {
                                                        Msg.warn('????????????????????????')
                                                    }
                                                }
                                            },
                                            isInForm: false
                                        },
                                        {
                                            table: {
                                                title: '???????????????',
                                                width: 120,
                                                dataIndex: 'inFeeTotal',
                                                key: 'inFeeTotal'
                                            },
                                            form: {
                                                type: 'number',
                                                field: 'inFeeTotal'
                                            }
                                        },
                                        {
                                            table: {
                                                title: '???????????????',
                                                width: 120,
                                                dataIndex: 'ysFee',
                                                key: 'ysFee',
                                                tdEdit: true,
                                                fieldConfig: {
                                                    disabled: ({ record }) => {
                                                        const formVals = this.table.getDeawerValuesSync();
                                                        if (formVals.purchType === '?????????') {
                                                            return true
                                                        } else {
                                                            return false
                                                        }
                                                    },
                                                }
                                            },
                                            form: {
                                                type: 'number',
                                                field: 'ysFee',
                                                precision: 2,
                                                onChange: async (colVal, thisProps, tableBtnCallbackFn) => {
                                                    if (colVal) {
                                                        // ???????????????????????? ????????????
                                                        const rowData = await tableBtnCallbackFn.getEditedRowData(false);
                                                        const newRowData = {
                                                            ...rowData
                                                        }
                                                        let shuilv = 0;
                                                        if (this.table.qnnForm.form.getFieldsValue().taxRate) {
                                                            shuilv = Number((this.table.qnnForm.form.getFieldsValue().taxRate) * 0.01);
                                                        }
                                                        newRowData.ysFeeTotal = Number(rowData.ysFee).toFixed(2);
                                                        if (rowData.inFeeTotal) {

                                                        } else {
                                                            rowData.inFeeTotal = 0;
                                                        }
                                                        if (rowData.inQty) {
                                                            newRowData.resAllFeeNoTax = ((this.FloatMulTwo(rowData.inQty, rowData.inPrice)) / Number(1 + shuilv)).toFixed(2);//???????????????
                                                            newRowData.resAllFee = Number(this.FloatMulTwo(rowData.inQty, rowData.inPrice).toFixed(2));//????????????
                                                            newRowData.inAmtAll = parseFloat(Number(this.FloatMulTwo(rowData.inQty, rowData.inPrice)) + Number(newRowData.ysFeeTotal) + Number(rowData.inFeeTotal)).toFixed(2);//????????????
                                                            newRowData.inAmtAllBeiYong = parseFloat(Number(this.FloatMulTwo(rowData.inQty, rowData.inPrice)) + Number(newRowData.ysFeeTotal) + Number(rowData.inFeeTotal)).toFixed(2);//????????????

                                                            if (this.table.qnnForm.form.getFieldsValue().isDeduct === '0') {//????????????-0???   1???
                                                                newRowData.inAmt = parseFloat(Number(newRowData.resAllFee) + Number(newRowData.ysFee) + Number(rowData.inFee)).toFixed(2);//??????-??????
                                                                newRowData.inAmtBeiYong = parseFloat(Number(newRowData.resAllFeeNoTax) + Number(newRowData.ysFee) + Number(rowData.inFee)).toFixed(2);//??????-??????
                                                            } else {
                                                                newRowData.inAmt = parseFloat(Number(newRowData.resAllFeeNoTax) + Number(newRowData.ysFee) + Number(rowData.inFee)).toFixed(2);//??????-??????
                                                                newRowData.inAmtBeiYong = parseFloat(Number(newRowData.resAllFeeNoTax) + Number(newRowData.ysFee) + Number(rowData.inFee)).toFixed(2);//??????-??????

                                                            }
                                                            if (newRowData.resAllFee && newRowData.resAllFeeNoTax) {
                                                                newRowData.resAllFeeTax = (newRowData.resAllFee - newRowData.resAllFeeNoTax).toFixed(2);//??????
                                                            }
                                                        }
                                                        newRowData.inPriceNoTax = (rowData.inPrice / Number(1 + shuilv)).toFixed(6);//???????????????

                                                        await tableBtnCallbackFn.setEditedRowData({
                                                            ...newRowData
                                                        });
                                                        this.table.qnnForm.form.setFieldsValue({
                                                            cantEdit: 'no',
                                                            cantEditShui: 'no'
                                                        })
                                                    }
                                                }
                                            }
                                        },
                                        {
                                            isInTable: false,
                                            form: {
                                                type: 'string',//????????????????????? ???????????????????????? id
                                                field: 'materialsExpensesDetailID'
                                            }
                                        },
                                        {
                                            isInTable: false,
                                            form: {
                                                type: 'string',//??????????????? ???????????????????????? id
                                                field: 'materialsTransportationDetailID'
                                            }
                                        },
                                        {
                                            isInTable: (obj) => {
                                                const formVals = this.table.getDeawerValuesSync();
                                                if (obj.qnnFormProps.funcCallBackParams.props.Pstate.drawerDetailTitle === '??????') {
                                                    return false
                                                } else {
                                                    if (formVals.purchType === '?????????') {
                                                        return true
                                                    } else {
                                                        return false
                                                    }
                                                }
                                            },
                                            table: {
                                                title: '??????',
                                                dataIndex: 'warnFlag',
                                                key: 'warnFlag',
                                                align: 'center',
                                                width: 50,
                                                render: (data) => {
                                                    return <Avatar shape="square" size={20} src={require('../../../imgs/file.png')} />;
                                                },
                                                onClick: (obj) => {
                                                    let conNo = this.table.qnnForm.form.getFieldsValue();
                                                    if (conNo.contractNo) {
                                                        if (obj.rowData.materialsTransportationDetailID) {
                                                            this.setState({
                                                                visibleSend: true,
                                                                apiNameValue: 'getZxSkStockFeeDetail',
                                                                contractNoVal: conNo.contractNo,
                                                                ListId: obj.rowData.id,
                                                                materialsTransportationDetailID: obj.rowData.materialsTransportationDetailID,
                                                                saveApiNameValue: 'updateZxSkStockFee',
                                                                clickBtnName: '?????????'
                                                            });
                                                        } else {
                                                            this.setState({
                                                                visibleSend: true,
                                                                // apiNameValue: 'getYunShuContractList',
                                                                apiNameValue: 'getZxCtFsContractYunShuList',
                                                                contractNoVal: conNo.contractNo,
                                                                // contractNoVal: conNo.fromContractNo,
                                                                ListId: obj.rowData.id,
                                                                saveApiNameValue: 'addZxSkStockFee',
                                                                clickBtnName: '?????????'
                                                            });
                                                        }

                                                    } else {
                                                        Msg.warn('????????????????????????')
                                                    }
                                                }
                                            },
                                            isInForm: false
                                        },
                                        {
                                            table: {
                                                title: '???????????????',
                                                width: 120,
                                                dataIndex: 'ysFeeTotal',
                                                key: 'ysFeeTotal'
                                            },
                                            form: {
                                                type: 'number',
                                                field: 'ysFeeTotal',
                                                precision: 2,
                                            }
                                        },
                                        {
                                            table: {
                                                title: '??????',
                                                width: 120,
                                                dataIndex: 'inAmtAll',
                                                key: 'inAmtAll'
                                            },
                                            form: {
                                                type: 'number',
                                                field: 'inAmtAll',
                                                precision: 2,
                                            }
                                        },
                                        {
                                            table: {
                                                title: '????????????',
                                                width: 120,
                                                dataIndex: 'inAmt',
                                                key: 'inAmt'
                                            },
                                            form: {
                                                type: 'number',
                                                field: 'inAmt',
                                                precision: 2,
                                            }
                                        },
                                        {
                                            table: {
                                                title: '????????????',
                                                width: 120,
                                                dataIndex: 'precollecte',
                                                key: 'precollecte',
                                                type: 'select',
                                                tdEdit: true
                                            },
                                            form: {
                                                field: 'precollecte',
                                                type: 'select',
                                                optionData: [
                                                    {
                                                        label: '???',
                                                        value: '0'
                                                    },
                                                    {
                                                        label: '???',
                                                        value: '1'
                                                    }
                                                ],
                                                onChange: async (colVal, thisProps, tableBtnCallbackFn) => {
                                                    this.table.qnnForm.form.setFieldsValue({
                                                        cantEdit: 'no',
                                                        cantEditShui: 'no'
                                                    })
                                                }
                                            }
                                        },
                                        {
                                            table: {
                                                title: '??????',
                                                width: 120,
                                                // tooltip: 23,
                                                dataIndex: 'remark',
                                                key: 'remark',
                                                tdEdit: true,
                                                fieldConfig: {
                                                    disabled: ({ record }) => {
                                                        if (record.canEdit === 'no') {
                                                            return true
                                                        } else {
                                                            return false
                                                        }
                                                    },
                                                }
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
                                            addRowDefaultData: () => {
                                                return {
                                                    precollecte: '0',
                                                    inFee: 0,
                                                    ysFee: 0,
                                                    inFeeTotal: 0,
                                                    ysFeeTotal: 0
                                                }
                                            }
                                        },
                                        {
                                            name: "del",
                                            icon: 'delete',
                                            type: 'danger',
                                            label: "??????"
                                        }
                                    ]
                                }
                            }
                        },
                        {
                            isInTable: false,
                            table: {
                                title: '?????????',
                                dataIndex: 'outtransactor',
                                key: 'outtransactor',
                            },
                            form: {
                                type: 'string',
                                field: 'outtransactor',
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
                                title: '???????????????',
                                dataIndex: 'waretransactor',
                                key: 'waretransactor',
                            },
                            form: {
                                type: 'string',
                                field: 'waretransactor',
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
                                type: 'number',
                                field: 'billType',
                                initialValue: 11,
                                hide: true,
                            }
                        },
                        {
                            isInTable: false,
                            table: {
                                title: '?????????',
                                dataIndex: 'buyer',
                                key: 'buyer',
                            },
                            form: {
                                type: 'string',
                                field: 'buyer',
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
                                title: '?????????',
                                dataIndex: 'reporter',
                                key: 'reporter',
                            },
                            form: {
                                type: 'string',
                                field: 'reporter',
                                addDisabled: true,
                                editDisabled: true,
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
                                title: '??????',
                                width: 300,
                                tooltip: 23,
                                dataIndex: 'remark',
                                key: 'remark',
                            },
                            form: {
                                type: 'textarea',
                                field: 'remark',
                                autoSize: {
                                    minRows: 1,
                                    maxRows: 3
                                },
                                spanForm: 16,
                                formItemLayoutForm: {
                                    labelCol: {
                                        xs: { span: 24 },
                                        sm: { span: 4 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 24 },
                                        sm: { span: 20 }
                                    }
                                }
                            }
                        },
                        {
                            table: {
                                title: '????????????',
                                width: 100,
                                fixed: 'right',
                                dataIndex: 'billStatus',
                                key: 'billStatus',
                                render: (data) => {
                                    if (data) {
                                        return data === '0' ? '?????????' : '?????????'
                                    } else {
                                        return '?????????'
                                    }
                                }
                            },
                            form: {
                                type: 'string',
                                field: 'billStatus',
                                hide: true
                            }
                        }
                    ]}
                    method={{
                        disabledFunEdit: (obj) => {
                            let data = obj.btnCallbackFn.getSelectedRows();
                            if (data.length !== 1 || data[0].billStatus === '1') {
                                return true;
                            } else {
                                return false;
                            }
                        },
                        willExecuteFunEdit: (obj) => {
                            if (obj.selectedRows[0].billStatus === '1') {
                                obj.btnCallbackFn.clearSelectedRows();
                                Msg.warn('????????????????????????!');
                                return false
                            } else {
                                if (obj.selectedRows[0].purchType === '?????????') {
                                    this.setState({
                                        abcVal: 'false'
                                    })
                                    let selectRow = obj.selectedRows[0].zxSkStockTransItemReceivingList;
                                    obj.qnnTableInstance.setDeawerValues({
                                        zxSkStockTransItemReceivingList: selectRow.map((item) => {
                                            item.canEdit = 'no';
                                            return item
                                        })
                                    });
                                } else {
                                    this.setState({
                                        abcVal: 'true'
                                    })
                                }
                            }
                            obj.btnCallbackFn.clearSelectedRows();
                        },
                        disabledFunShenPi: (obj) => {
                            let data = obj.btnCallbackFn.getSelectedRows();
                            if (data.length !== 1 || data[0].billStatus === '1') {
                                return true;
                            } else {
                                return false;
                            }
                        },
                        onClickFunShenPi: (obj) => {
                            const { myFetch } = this.props;
                            if (obj.selectedRows.length === 1) {
                                if (obj.selectedRows[0].billStatus === '1') {
                                    obj.btnCallbackFn.closeDrawer();
                                    obj.btnCallbackFn.clearSelectedRows();
                                    Msg.warn('????????????????????????!');
                                } else {
                                    confirm({
                                        content: '???????????????????????????????',
                                        onOk: () => {
                                            myFetch('checkZxSkStockTransferReceiving', { id: obj.selectedRows[0].id }).then(
                                                ({ data, success, message }) => {
                                                    if (success) {
                                                        Msg.success(message);
                                                        this.table.refresh();
                                                    } else {
                                                        Msg.warn(message);
                                                        this.table.refresh();
                                                    }
                                                    obj.btnCallbackFn.clearSelectedRows();
                                                }
                                            );
                                        }
                                    });

                                }
                            } else {
                                Msg.warn('???????????????????????????')
                            }
                        },
                        disabledFunEditDate: (obj) => {
                            let data = obj.btnCallbackFn.getSelectedRows();
                            if (data.length !== 1) {
                                return true;
                            } else {
                                return false;
                            }
                        },
                        onClickFunEditDate: (obj) => {
                            if (obj.selectedRows.length === 1) {
                                this.setState({
                                    visibleSendData: true,
                                    loadingSendData: false,
                                    selectedRowsData: obj.selectedRows[0]
                                })
                            } else {
                                Msg.warn('????????????????????????')
                            }
                        },
                        // onClickFunPrint: (obj) => {

                        // },
                        disabledFunDel: (obj) => {
                            let data = obj.btnCallbackFn.getSelectedRows();
                            if (data.length > 0) {
                                for (let m = 0; m < data.length; m++) {
                                    if (data[m].billStatus === '1') {
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
                                if (obj.selectedRows[m].billStatus === '1') {
                                    //????????????????????????
                                    arry.push(obj.selectedRows[m].billStatus);
                                }
                            }
                            if (arry.length > 0) {
                                Msg.warn('??????????????????????????????')
                            } else {
                                confirm({
                                    content: '???????????????????????????????',
                                    onOk: () => {
                                        myFetch('batchDeleteUpdateZxSkStockTransferReceiving', obj.selectedRows).then(
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
                            }
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
                                name: obj.selectedRows[0].billNo
                            })
                        }
                    }}
                    actionBtns={{
                        apiName: "getSysMenuBtn",
                        otherParams: (obj) => {
                            var props = obj.props;
                            let curRouteData = props.routerInfo.routeData[props.routerInfo.curKey];
                            return {
                                menuParentId: curRouteData._value,
                                tableField: "ImportTaxMaterialReceipt"
                            }
                        }
                    }}
                />
                <Modal
                    width='1200px'
                    style={{ top: '0' }}
                    title={clickBtnName === '?????????' ? '???????????????' : '???????????????'}
                    visible={visibleSend}
                    footer={null}
                    onCancel={this.handleCancelSend}
                    bodyStyle={{ width: '1200px' }}
                    centered={true}
                    destroyOnClose={this.handleCancelSend}
                    wrapClassName={'modals'}
                >
                    <Spin spinning={loadingSend}>
                        <QnnForm
                            {...this.props}
                            match={this.props.match}
                            fetch={this.props.myFetch}
                            upload={this.props.myUpload}
                            headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                            fetchConfig={{
                                apiName: apiNameValue,
                                otherParams: () => {
                                    if (this.state.materialsExpensesDetailID && clickBtnName === '?????????') {
                                        return {
                                            zxCtFsContractId: this.state.materialsExpensesDetailID
                                        }
                                    } else if (this.state.materialsTransportationDetailID && clickBtnName === '?????????') {
                                        return {
                                            zxCtFsContractId: this.state.materialsTransportationDetailID
                                        }
                                    } else {
                                        return {
                                            fromContractNo: contractNoVal
                                        }
                                    }
                                }
                            }}
                            formItemLayout={{
                                labelCol: {
                                    xs: { span: 24 },
                                    sm: { span: 6 }
                                },
                                wrapperCol: {
                                    xs: { span: 24 },
                                    sm: { span: 18 }
                                }
                            }}
                            wrappedComponentRef={(me) => {
                                this.formRz = me;
                            }}
                            formConfig={[
                                {
                                    type: 'string',
                                    field: "zxSkStockFeeId",
                                    hide: true
                                },
                                {
                                    type: 'string',
                                    field: 'zxCtFsContractId',
                                    hide: true
                                },
                                {
                                    label: '??????????????????',
                                    type: 'string',
                                    field: 'contractNo',
                                    disabled: true,
                                    span: 8
                                },
                                {
                                    label: '??????????????????',
                                    type: 'string',
                                    field: 'contractName',
                                    disabled: true,
                                    span: 8
                                },
                                {
                                    label: '????????????',
                                    type: 'string',
                                    field: 'contractType',
                                    disabled: true,
                                    span: 8
                                },
                                {
                                    label: '????????????',
                                    type: 'select',
                                    field: 'code7',
                                    disabled: true,
                                    span: 8,
                                    optionConfig: {
                                        label: 'label',
                                        value: 'value',
                                    },
                                    optionData: [
                                        { label: '???????????????', value: '0' },
                                        { label: '???????????????', value: '1' },
                                    ],
                                },
                                {
                                    label: '????????????',
                                    type: 'selectByPaging',
                                    field: 'secondID',
                                    disabled: true,
                                    span: 8,
                                    optionConfig: {
                                        label: 'customerName',
                                        value: 'zxCrCustomerNewId'
                                    },
                                    fetchConfig: {
                                        apiName: 'getZxCrCustomerNewList',
                                        // otherParams: {
                                        //     typeList: '567'
                                        // }
                                    }
                                },
                                {
                                    label: '?????????',
                                    type: 'number',
                                    field: 'contractCost',
                                    disabled: true,
                                    span: 8
                                },
                                {
                                    label: '????????????',
                                    type: 'number',
                                    field: 'contractCostNoTax',
                                    disabled: true,
                                    span: 8
                                },
                                {
                                    label: '????????????',
                                    field: 'isDeduct',
                                    type: 'select',
                                    disabled: true,
                                    span: 8,
                                    optionConfig: {
                                        label: 'label',
                                        value: 'value',
                                    },
                                    optionData: [
                                        {
                                            label: "???",
                                            value: "0"
                                        },
                                        {
                                            label: "???",
                                            value: "1"
                                        }
                                    ],
                                },
                                {
                                    type: 'qnnTable',
                                    field: 'reviewDetailList',
                                    incToForm: true,
                                    qnnTableConfig: {
                                        antd: {
                                            // rowKey: 'zxSkStockFeeItemId',
                                            rowKey: 'conRevDetailId',
                                            size: 'small',
                                            scroll: {
                                                y: document.documentElement.clientHeight * 0.3
                                            }
                                        },
                                        ...configRuZhangItem,
                                        // tableTdEdit: true,
                                        formConfig: [
                                            {
                                                isInTable: false,
                                                form: {
                                                    label: '??????id?????????',
                                                    field: 'conRevDetailId',
                                                    type: 'string',
                                                    hide: true
                                                }
                                            },
                                            {
                                                isInTable: false,
                                                form: {
                                                    label: '??????id?????????',
                                                    type: 'string',
                                                    field: 'zxSkStockFeeItemId',
                                                    hide: true
                                                }
                                            },
                                            {
                                                table: {
                                                    title: '????????????',
                                                    dataIndex: 'workNo',
                                                    key: 'workNo'
                                                }
                                            },
                                            {
                                                table: {
                                                    title: '????????????',
                                                    dataIndex: 'workName',
                                                    key: 'workName'
                                                }
                                            },
                                            {
                                                table: {
                                                    title: '????????????',
                                                    dataIndex: 'unit',
                                                    key: 'unit'
                                                }
                                            },
                                            {
                                                table: {
                                                    title: '????????????',
                                                    dataIndex: 'qty',
                                                    key: 'qty'
                                                }
                                            },
                                            {
                                                table: {
                                                    title: '??????????????????',
                                                    width: 140,
                                                    dataIndex: 'price',
                                                    key: 'price',
                                                    // tdEdit: true
                                                },
                                                form: {
                                                    type: 'number',
                                                    field: 'price',
                                                    required: true,
                                                    precision: 6,

                                                }
                                            },
                                            {
                                                table: {
                                                    title: '?????????????????????',
                                                    dataIndex: 'priceNoTax',
                                                    key: 'priceNoTax'
                                                }
                                            },
                                            {
                                                table: {
                                                    title: '??????(%)',
                                                    dataIndex: 'taxRate',
                                                    key: 'taxRate'
                                                }
                                            },
                                            {
                                                table: {
                                                    title: '??????????????????',
                                                    dataIndex: 'contractSum',
                                                    key: 'contractSum'
                                                }
                                            },
                                            {
                                                table: {
                                                    title: '?????????????????????',
                                                    dataIndex: 'contractSumNoTax',
                                                    key: 'contractSumNoTax'
                                                }
                                            },
                                            {
                                                table: {
                                                    title: '??????',
                                                    dataIndex: 'changeQty',
                                                    key: 'changeQty',
                                                    tdEdit: true
                                                },
                                                form: {
                                                    type: 'number',
                                                    field: 'changeQty',
                                                    precision: 3,
                                                    required: true,
                                                    onChange: async (colVal, thisProps, tableBtnCallbackFn) => {
                                                        const rowData = await tableBtnCallbackFn.getEditedRowData(false);
                                                        const newRowData = {
                                                            ...rowData
                                                        }
                                                        if (typeof (colVal) === 'number' && colVal >= 0) {
                                                            if (colVal <= rowData.qty) {
                                                                let shuilv = 0;
                                                                if (rowData.taxRate) {
                                                                    shuilv = Number((rowData.taxRate) * 0.01);
                                                                }
                                                                // ??????
                                                                if (rowData.price) {
                                                                    newRowData.changeContractSum = Number(Number(this.FloatMulTwo(colVal, rowData.price)).toFixed(2));
                                                                }
                                                                if (rowData.priceNoTax) {
                                                                    if (this.formRz.form.getFieldsValue().isDeduct === '1') {//????????????
                                                                        newRowData.changeContractSumNoTax = Number(Number(newRowData.changeContractSum / (1 + shuilv)).toFixed(2));//??????-??????
                                                                    } else {
                                                                        newRowData.changeContractSumNoTax = Number(this.FloatMulTwo(colVal, rowData.price));//2021.6.15??????
                                                                    }
                                                                }
                                                                this.setTotalAmtFun(newRowData);
                                                                await tableBtnCallbackFn.setEditedRowData({
                                                                    ...newRowData
                                                                });
                                                            } else {
                                                                newRowData.changeQty = 0;
                                                                newRowData.changeContractSum = 0;
                                                                Msg.warn('????????????????????????!');
                                                            }
                                                        } else {
                                                            newRowData.changeQty = 0;
                                                            newRowData.changeContractSum = 0;
                                                            // Msg.warn('???????????????!');
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
                                                    dataIndex: 'changeContractSum',
                                                    key: 'changeContractSum'
                                                }
                                            },
                                            {
                                                table: {
                                                    title: '????????????',
                                                    dataIndex: 'changeContractSumNoTax',
                                                    key: 'changeContractSumNoTax'
                                                }
                                            },
                                            {
                                                table: {
                                                    title: '??????',
                                                    dataIndex: 'remarks',
                                                    key: 'remarks'
                                                }
                                            }
                                        ],
                                        actionBtns: []
                                    }
                                },
                                {
                                    type: 'string',
                                    field: 'zxSkStockTransItemReceivingid',
                                    initialValue: this.state.ListId,
                                    hide: true
                                }
                            ]}
                            btns={[
                                {
                                    name: 'cancel',
                                    type: 'dashed',
                                    label: '??????',
                                    isValidate: false,
                                    onClick: () => {
                                        this.setState({
                                            visibleSend: false,
                                            loadingSend: false
                                        })
                                    }
                                },
                                {
                                    name: 'diySubmit',
                                    type: 'primary',
                                    label: '??????',
                                    onClick: (obj) => {
                                        this.setState({
                                            loadingSend: true
                                        })
                                        this.props.myFetch(saveApiNameValue, obj.values).then(
                                            ({ data, success, message }) => {
                                                if (success) {

                                                    Msg.success(message);
                                                    let tableListData = this.table.qnnForm.form.getFieldsValue().zxSkStockTransItemReceivingList;

                                                    // ?????????????????????
                                                    for (let m = 0; m < tableListData.length; m++) {
                                                        if ((tableListData[m].id === data.zxSkStockTransItemReceivingid) || (tableListData[m].zxCtSuppliesContrShopListId === data.zxSkStockTransItemReceivingid)) {


                                                            // ???????????????????????????????????????????????????????????????????????????????????????????????????????????????
                                                            if (clickBtnName === '?????????') {
                                                                // ???????????????
                                                                tableListData[m].ysFeeTotal = data.contractCost;
                                                                tableListData[m].ysFee = data.contractCostNoTax;
                                                                tableListData[m].materialsTransportationDetailID = data.zxCtFsContractId;

                                                                tableListData[m].inAmtAll = Number(Number(tableListData[m].resAllFee + data.contractCost + tableListData[m].inFeeTotal).toFixed(2));//??????
                                                                if (this.table.qnnForm.form.getFieldsValue().isDeduct === '1') {//????????????
                                                                    tableListData[m].inAmt = Number(Number(tableListData[m].resAllFeeNoTax + data.contractCostNoTax + tableListData[m].inFee).toFixed(2));//????????????
                                                                } else {
                                                                    tableListData[m].inAmt = Number(Number(tableListData[m].resAllFee + data.contractCostNoTax + tableListData[m].inFee).toFixed(2));//????????????
                                                                }

                                                            } else {
                                                                // ???????????????
                                                                tableListData[m].inFeeTotal = data.contractCost;
                                                                tableListData[m].inFee = data.contractCostNoTax;
                                                                tableListData[m].materialsExpensesDetailID = data.zxCtFsContractId;

                                                                tableListData[m].inAmtAll = Number(Number(tableListData[m].resAllFee + data.contractCost + tableListData[m].ysFeeTotal).toFixed(2));//??????
                                                                if (this.table.qnnForm.form.getFieldsValue().isDeduct === '1') {
                                                                    tableListData[m].inAmt = Number(Number(tableListData[m].resAllFeeNoTax + data.contractCostNoTax + tableListData[m].ysFee).toFixed(2));//????????????
                                                                } else {
                                                                    tableListData[m].inAmt = Number(Number(tableListData[m].resAllFee + data.contractCostNoTax + tableListData[m].ysFee).toFixed(2));//????????????
                                                                }

                                                            }

                                                        }
                                                    }
                                                    this.table.qnnForm.form.setFieldsValue({
                                                        // ????????????
                                                        zxSkStockTransItemReceivingList: tableListData
                                                    })
                                                    this.setState({
                                                        visibleSend: false,
                                                        loadingSend: false
                                                    })
                                                } else {
                                                    Msg.error(message)
                                                }
                                            }
                                        );
                                    }
                                }
                            ]}
                            tailFormItemLayout={{
                                wrapperCol: {
                                    xs: {
                                        span: 24,
                                        offset: 0
                                    },
                                    sm: {
                                        span: 24,
                                        offset: 8
                                    }
                                }
                            }}
                        />
                    </Spin>
                </Modal>
                <Modal
                    width='500px'
                    style={{ top: '0' }}
                    title="??????????????????"
                    visible={visibleSendData}
                    footer={null}
                    onCancel={this.handleCancelSendData}
                    bodyStyle={{ width: '500px' }}
                    centered={true}
                    destroyOnClose={this.handleCancelSendData}
                    wrapClassName={'modals'}
                >
                    <Spin spinning={loadingSendData}>
                        <QnnForm
                            {...this.props}
                            match={this.props.match}
                            fetch={this.props.myFetch}
                            upload={this.props.myUpload}
                            headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                            wrappedComponentRef={(me) => {
                                this.formEdit = me;
                            }}
                            formConfig={[
                                {
                                    field: 'busDate',
                                    type: 'date',
                                    label: "????????????",
                                    required: true,
                                    placeholder: '?????????',
                                    formItemLayout: {
                                        labelCol: {
                                            xs: { span: 24 },
                                            sm: { span: 5 }
                                        },
                                        wrapperCol: {
                                            xs: { span: 24 },
                                            sm: { span: 19 }
                                        }
                                    },
                                    onChange: (val) => {
                                        if (val) {
                                            this.dateChange(val, 2);
                                        }
                                    }
                                }
                            ]}
                            btns={[
                                {
                                    name: 'cancel',
                                    type: 'dashed',
                                    label: '??????',
                                    isValidate: false,
                                    onClick: (obj) => {
                                        this.setState({
                                            visibleSendData: false,
                                            loadingSendData: false
                                        })
                                        obj.btnCallbackFn.clearSelectedRows();
                                    }
                                },
                                {
                                    name: 'submit',
                                    type: 'primary',
                                    label: '??????',
                                    onClick: (obj) => {
                                        this.setState({
                                            loadingSendData: true
                                        })
                                        let values = selectedRowsData;
                                        values.busDate = obj.values.busDate;

                                        this.props.myFetch('updateZxSkStockTransferReceiving', values).then(
                                            ({ success, message }) => {
                                                if (success) {
                                                    Msg.success(message);
                                                    this.table.refresh();
                                                    this.setState({
                                                        visibleSendData: false,
                                                        loadingSendData: false
                                                    })
                                                } else {
                                                    Msg.error(message)
                                                }
                                                obj.btnCallbackFn.clearSelectedRows();
                                            }
                                        );

                                    }
                                }
                            ]}
                            tailFormItemLayout={{
                                wrapperCol: {
                                    xs: {
                                        span: 24,
                                        offset: 0
                                    },
                                    sm: {
                                        span: 24,
                                        offset: 8
                                    }
                                }
                            }}
                        />
                    </Spin>
                </Modal>
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
                            <iframe width='100%' height={window.innerHeight * 0.8} frameBorder={0} onLoad={(obj) => {
                                this.setState({ loading: false })
                            }} src={`${ureport}preview?_u=minio:ZxSkStockTransferReceiving.preview.xml&_t=1,6&_n=${name}&access_token=${access_token}&id=${idPreview}
                            `} title="byLm"></iframe>
                        </Spin> : ''
                    }

                </Modal>
            </div>
        );
    }
}

export default index;