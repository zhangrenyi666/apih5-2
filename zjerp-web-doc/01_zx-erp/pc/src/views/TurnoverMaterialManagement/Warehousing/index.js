import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import QnnForm from "../../modules/qnn-table/qnn-form";
import moment from 'moment';
import { message as Msg, Modal, Spin } from 'antd';
const confirm = Modal.confirm;
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
            visibleSendData: false,
            loadingSendData: false,
            showTicket: 'hide',
            abcVal: '',
            selectedRowsData: {},
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
    accAdd(arg1, arg2) {
        var r1, r2, m;
        try { r1 = arg1.toString().split(".")[1].length } catch (e) { r1 = 0 }
        try { r2 = arg2.toString().split(".")[1].length } catch (e) { r2 = 0 }
        m = Math.pow(10, Math.max(r1, r2))

        return (arg1 * m + arg2 * m) / m
    }
    FloatMuljianfa(arg1, arg2) {
        var r1, r2, m, n;
        try { r1 = arg1.toString().split(".")[1].length } catch (e) { r1 = 0 }
        try { r2 = arg2.toString().split(".")[1].length } catch (e) { r2 = 0 }
        m = Math.pow(10, Math.max(r1, r2));
        n = (r1 >= r2) ? r1 : r2;
        return ((arg1 * m - arg2 * m) / m).toFixed(n);
    }
    // ????????????
    getDjbhFun(val, busDate) {
        const { myFetch } = this.props;
        myFetch('getZxSkTurnoverInNo', {
            orgID: val,
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
    handleCancelSendData = () => {
        this.setState({ visibleSendData: false, loadingSendData: false });
    }
    getItenList(hetong) {
        const { myFetch } = this.props;
        myFetch('getZxCtSuppliesContrShopListListByContID', {
            isTurnover: 0,//??????????????????0?????????1????????????
            contractID: hetong
        }).then(
            ({ data, success, message }) => {
                if (success) {
                    if (this.table && this.table.qnnForm) {
                        let shuilvNew = 0;
                        if (this.table.qnnForm.form.getFieldsValue().taxRate) {
                            shuilvNew = Number(Number(this.table.qnnForm.form.getFieldsValue().taxRate) * 0.01);
                        }
                        this.table.qnnForm.form.setFieldsValue({
                            zxSkTurnoverInItemList: data.map((item) => {
                                item.canEdit = 'no';
                                item.id = item.zxCtSuppliesContrShopListId;
                                item.resCode = item.workNo;
                                item.resName = item.workName;
                                item.resID = item.workID;
                                item.resUnit = item.unit;
                                item.inQty = item.qty;
                                // item.resAllFeeNoTax = item.contractSumNoTax;//?????????????????????
                                item.inPrice = item.price;// ??????????????????
                                // item.inPriceNoTax = item.priceNoTax; // ????????????
                                item.inPriceNoTax = Number((item.inPrice / (1 + shuilvNew)).toFixed(6)); // ????????????
                                // item.resAllFee = item.contractSum;//?????????????????????
                                item.remark = item.remarks;
                                // item.precollecte = '1';//?????????000004197
                                item.precollecte = '0';
                                // item.resAllFeeTax = item.contractSumTax;//?????????????????????



                                item.inPriceTax = Number(this.FloatMulTwo(item.inPriceNoTax, shuilvNew).toFixed(2)); // ??????????????????
                                item.inAmtTotal = Number(this.FloatMulTwo(item.inPrice, item.inQty).toFixed(2));// ??????

                                // if (this.table.qnnForm.form.getFieldsValue().isDeduct === '0') {
                                //     item.inAmt = Number(Number(item.resAllFee).toFixed(2));//???-???
                                //     item.remainAmt = Number(this.FloatMulTwo(item.inPrice, item.inQty).toFixed(2));//???-???
                                // } else {
                                //     item.inAmt = Number(Number(item.resAllFeeNoTax).toFixed(2));//???-???
                                //     item.remainAmt = Number(this.FloatMulTwo(item.inPriceNoTax, item.inQty).toFixed(2));//???-???
                                // }

                                if (this.table.qnnForm.form.getFieldsValue().isDeduct === '0') {//????????????-0???   1???
                                    item.inAmt = Number(this.FloatMulTwo(item.inPrice, item.inQty).toFixed(2));//???-???
                                    item.remainAmt = Number((this.FloatMulTwo(item.inPrice, item.inQty) - Number(item.feeSum ? item.feeSum : 0)).toFixed(2));//???-???
                                } else {
                                    item.inAmt = Number(this.FloatMulTwo(item.inPriceNoTax, item.inQty).toFixed(2));//???-???
                                    item.remainAmt = Number((this.FloatMulTwo(item.inPriceNoTax, item.inQty) - Number(item.feeSum ? item.feeSum : 0)).toFixed(2));//???-???
                                }
                                return item
                            })
                        });
                    }
                    Msg.success(message);
                } else {
                    Msg.error(message);
                }
            }
        );
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
                if (type === 2) {
                    this.formEdit.form.setFieldsValue({
                        busDate: ''
                    })
                } else {
                    this.table.qnnForm.form.setFieldsValue({
                        busDate: '',
                        billNo: null
                    })
                }
            }
        } else {//????????????????????????26???
            if (time1 < time3) {
                Msg.warn('??????????????????26????????????????????????');
                if (type === 2) {
                    this.formEdit.form.setFieldsValue({
                        busDate: ''
                    })
                } else {
                    this.table.qnnForm.form.setFieldsValue({
                        busDate: '',
                        billNo: null
                    })
                }
            }
        }
        if (type === 1) {
            let orgID = this.table.qnnForm.form.getFieldsValue().orgID;
            let busDate = this.table.qnnForm.form.getFieldsValue().busDate;
            if (orgID) {
                this.getDjbhFun(orgID, busDate);
            }
        }
    }
    handleCancel = () => {
        this.setState({ visible: false, loading: false });
    }
    render() {
        const { visibleSendData, loadingSendData, selectedRowsData, lockProjectId, loading, idPreview, name } = this.state;
        const { ext1, departmentId, companyName, companyId, projectId } = this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany;
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
                        apiName: 'getZxSkTurnoverInList',
                        otherParams: {
                            orgID: jurisdiction
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
                                width: 200,
                                filter: true,
                                render: (data, rowData) => {
                                    let color = '';
                                    if (rowData.status === '1') {
                                        color = '';
                                    } else if (rowData.status === '0') {
                                        color = 'red';
                                    }
                                    return <span style={{ color: color }}>{data}</span>
                                }
                            },
                            form: {
                                field: 'billNo',
                                type: 'string',
                                required: true,
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
                                dataIndex: 'orgID',
                                key: 'orgID',
                                width: 160,
                                type: 'select',
                                filter: true,
                                fieldsConfig: {
                                    field: 'orgID2',
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
                                field: 'orgID',
                                required: true,
                                type: 'select',
                                showSearch: true,
                                editDisabled: true,
                                optionConfig: {
                                    label: 'orgName',
                                    value: 'orgID'
                                },
                                fetchConfig: {
                                    apiName: 'getZxCtContractListByOrgId',
                                    otherParams: {
                                        contrStatus: "1",
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
                                onChange: (val, rowData) => {

                                    if (this.table && this.table.qnnForm) {
                                        this.table.qnnForm.form.setFieldsValue({
                                            cantEdit: 'can',
                                            orgName: rowData.itemData.orgName
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
                                field: 'companyName',
                                initialValue: companyName,
                                hide: true
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                type: 'string',
                                field: 'companyId',
                                initialValue: companyId,
                                hide: true
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                type: 'string',
                                field: 'orgName',
                                hide: true
                            }
                        },
                        {
                            table: {
                                title: '????????????',
                                dataIndex: 'materialSource',
                                key: 'materialSource',
                                width: 160,
                                type: 'select',
                                filter: true
                            },
                            form: {
                                field: 'materialSource',
                                required: true,
                                type: 'select',
                                optionConfig: {
                                    label: 'label',
                                    value: 'value'
                                },
                                optionData: [
                                    {
                                        label: "??????",
                                        value: "1"
                                    },
                                    {
                                        label: "??????",
                                        value: "3"
                                    }
                                ],
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
                                width: 120,
                                dataIndex: 'busDate',
                                format: 'YYYY-MM-DD',
                                key: 'busDate',
                                filter: true,
                                fieldsConfig: {
                                    type: 'rangeDate',
                                    field: 'timeList'
                                }
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
                                onChange: (val, rowData) => {
                                    if (this.table && this.table.qnnForm) {
                                        if (val) {
                                            this.dateChange(val, 1);
                                        } else {
                                            this.table.qnnForm.form.setFieldsValue({
                                                billNo: null
                                            });
                                        }
                                    }
                                },
                            }
                        },
                        {
                            table: {
                                title: '?????????',
                                width: 100,
                                dataIndex: 'consignee',
                                key: 'consignee',
                            },
                            form: {
                                label: '?????????',
                                type: 'string',
                                field: 'consignee',
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
                                dataIndex: 'outOrgName',
                                key: 'outOrgName',
                                width: 200,
                            },
                            form: {
                                field: 'outOrgID',
                                required: true,
                                type: 'selectByPaging',
                                showSearch: true,
                                optionConfig: {
                                    label: 'customerName',
                                    value: 'zxCrCustomerNewId',
                                    linkageFields: {
                                        "outOrgType": "supplierType",
                                        outOrgName: 'customerName'
                                    }
                                },
                                fetchConfig: {
                                    apiName: 'getZxCrCustomerNewList'
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
                            // isInTable: false,
                            table: {
                                title: '????????????',
                                dataIndex: 'purchType',
                                key: 'purchType'
                            },
                            form: {
                                type: 'select',
                                field: 'purchType',
                                required: true,
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
                                    if (val === '?????????') {
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
                                        contractID: '',
                                        // isDeduct: '',
                                        taxRate: '',
                                        contractNo: ''
                                    })
                                    if (this.table && this.table.qnnForm) {
                                        this.table.qnnForm.clearValues(['zxSkTurnoverInItemList']);
                                        // this.table.qnnForm.qnnSetState({
                                        //     id: null
                                        // })
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
                            form: {
                                type: 'string',
                                hide: true,
                                field: 'contractName',
                            }
                        },
                        {
                            table: {
                                title: '????????????',
                                dataIndex: 'contractName',
                                key: 'contractName'
                            },
                            form: {
                                type: 'select',
                                field: 'contractID',
                                optionConfig: {
                                    label: 'contractName',
                                    value: 'zxCtSuppliesContractId',
                                    linkageFields: {
                                        contractNo: 'contractNo',
                                        isDeduct: 'isDeduct',
                                        contractName: "contractName"
                                    }
                                },
                                dependenciesReRender: true,//????????????-?????? 
                                dependencies: ['orgID', 'outOrgID'],
                                fetchConfig: {
                                    apiName: 'getZxCtSuppliesContractListByFirstId',
                                    otherParams: (val) => {
                                        let orgIDVal = '';
                                        let outOrgIDVal = '';
                                        if (val.btnCallbackFn?.form) {
                                            let aa = val.btnCallbackFn.form.getFieldsValue();
                                            orgIDVal = aa.orgID ? aa.orgID : orgIDVal;
                                            outOrgIDVal = aa.outOrgID ? aa.outOrgID : outOrgIDVal;
                                        } else {

                                        }
                                        return {
                                            firstId: orgIDVal,
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
                                ],
                                onChange: (val, rowData) => {
                                    if (val) {
                                        if (this.table && this.table.qnnForm) {
                                            this.table.qnnForm.clearValues(['zxSkTurnoverInItemList']);
                                        }
                                        this.getItenList(val);
                                    }
                                }
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
                                required: true,
                                allowClear: false,
                                initialValue: '0',
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
                                // required:true,
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
                                ],
                                onChange: (val) => {
                                    if (this.table && this.table.qnnForm) {
                                        this.table.qnnForm.clearValues(['zxSkTurnoverInItemList']);
                                        let taxRateVal = this.table.qnnForm.form.getFieldsValue();
                                        if (taxRateVal.contractID) {
                                            this.getItenList(taxRateVal.contractID);
                                        }
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
                                dataIndex: 'remarks',
                                key: 'remarks',
                            },
                            form: {
                                type: 'textarea',
                                field: 'remarks',
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
                            isInTable: false,
                            form: {
                                type: 'qnnTable',
                                field: 'zxSkTurnoverInItemList',
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
                                                title: '????????????',
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
                                                        apiName: "getZxSkTurnoverInResourceList",
                                                        otherParams: async () => {
                                                            const vals = await this.table.btnCallbackFn.qnnForm.getValues(false);
                                                            return {
                                                                id: vals.resourceID,//????????????
                                                                contractID: vals.contractID,
                                                                contractNo: vals.contractNo,
                                                                limit: 10,
                                                                page: 3
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
                                                                dataIndex: "resName",
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
                                                                dataIndex: "resUnit",
                                                                title: "????????????",
                                                            },
                                                            form: {
                                                                type: "string"
                                                            }
                                                        },
                                                        {
                                                            isInForm: false,
                                                            // isInSearch:true,
                                                            table: {
                                                                dataIndex: "pricingManner",
                                                                title: "????????????",
                                                            },
                                                            form: {
                                                                type: "string"
                                                            }
                                                        },
                                                        {
                                                            isInForm: false,
                                                            // isInSearch:true,
                                                            table: {
                                                                dataIndex: "planningAuthorities",
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
                                                        tableBtnCallbackFn.setEditedRowData({
                                                            resCode: itemData.itemData.resCode,
                                                            resName: itemData.itemData.resName,
                                                            resUnit: itemData.itemData.resUnit,
                                                            spec: itemData.itemData.spec,
                                                            resID: itemData.itemData.resID,
                                                            inBusDate: undefined,
                                                            inQty: 0,
                                                            inPriceTax: 0,
                                                            inPriceNoTax: 0,
                                                            inPrice: 0,
                                                            inAmt: 0,
                                                            inAmtTotal: 0,
                                                            feeSum: 0,
                                                            remainAmt: 0
                                                        });
                                                        this.table.qnnForm.form.setFieldsValue({
                                                            cantEdit: 'no',
                                                            cantEditShui: 'no'
                                                        })
                                                    } else {
                                                        tableBtnCallbackFn.getEditedRowData(false).then((editRowData) => {
                                                            tableBtnCallbackFn.setEditedRowData({
                                                                ...editRowData,
                                                                resID: '',
                                                                resName: '',
                                                                resUnit: '',
                                                                spec: '',
                                                                inBusDate: undefined,
                                                                inQty: 0,
                                                                inPriceTax: 0,
                                                                inPriceNoTax: 0,
                                                                inPrice: 0,
                                                                inAmt: 0,
                                                                inAmtTotal: 0,
                                                                feeSum: 0,
                                                                remainAmt: 0
                                                            });
                                                        })
                                                    }

                                                },
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
                                                tooltip: 10,
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
                                                width: 80,
                                                tooltip: 10,
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
                                                tooltip: 10,
                                            },
                                            form: {
                                                type: 'string',
                                                field: 'resUnit'
                                            }
                                        },
                                        {
                                            table: {
                                                title: '??????',
                                                width: 100,
                                                dataIndex: 'inQty',
                                                key: 'inQty',
                                                tdEdit: true
                                            },
                                            form: {
                                                type: 'number',
                                                field: 'inQty',
                                                precision: 3,
                                                onChange: async (colVal, thisProps, tableBtnCallbackFn) => {
                                                    if (colVal >= 0 && typeof (colVal) === 'number') {
                                                        const rowData = await tableBtnCallbackFn.getEditedRowData(false);
                                                        const newRowData = {
                                                            ...rowData
                                                        }
                                                        this.table.qnnForm.form.setFieldsValue({
                                                            cantEdit: 'no',
                                                            cantEditShui: 'no'
                                                        })

                                                        if (rowData.inPriceNoTax) {//????????????
                                                            if (this.table.qnnForm.form.getFieldsValue().purchType === '?????????') {
                                                                newRowData.inPrice = rowData.inPrice;
                                                            } else {
                                                                newRowData.inPrice = Number(this.accAdd(rowData.inPriceNoTax, rowData.inPriceTax));// ??????????????????
                                                            }
                                                            if (this.table.qnnForm.form.getFieldsValue().isDeduct === '0') {//????????????-0???   1???
                                                                newRowData.inAmt = Number(this.FloatMulTwo(newRowData.inPrice, colVal).toFixed(2));//???-???
                                                                newRowData.remainAmt = Number((this.FloatMulTwo(newRowData.inPrice, colVal) - Number(rowData.feeSum ? rowData.feeSum : 0)).toFixed(2));//???-???
                                                            } else {
                                                                newRowData.inAmt = Number(this.FloatMulTwo(rowData.inPriceNoTax, colVal).toFixed(2));//???-???
                                                                newRowData.remainAmt = Number((this.FloatMulTwo(rowData.inPriceNoTax, colVal) - Number(rowData.feeSum ? rowData.feeSum : 0)).toFixed(2));//???-???
                                                            }
                                                            newRowData.inAmtTotal = Number(this.FloatMulTwo(newRowData.inPrice, colVal).toFixed(2));// ??????
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
                                                width: 120,
                                                dataIndex: 'inPriceTax',
                                                key: 'inPriceTax'
                                            },
                                            form: {
                                                type: 'number',
                                                field: 'inPriceTax',
                                                precision: 2
                                            }
                                        },
                                        {
                                            isInTable: false,
                                            form: {
                                                type: 'number',
                                                field: 'canEdit'
                                            }
                                        },
                                        {
                                            table: {
                                                title: '????????????',
                                                width: 120,
                                                dataIndex: 'inPriceNoTax',
                                                key: 'inPriceNoTax',
                                                // tdEdit: true,
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
                                                type: 'number',
                                                field: 'inPriceNoTax',
                                                precision: 6,
                                                onChange: async (colVal, thisProps, tableBtnCallbackFn) => {
                                                    if (colVal >= 0 && typeof (colVal) === 'number') {
                                                        const rowData = await tableBtnCallbackFn.getEditedRowData(false);
                                                        const newRowData = {
                                                            ...rowData
                                                        }
                                                        this.table.qnnForm.form.setFieldsValue({
                                                            cantEdit: 'no',
                                                            cantEditShui: 'no'
                                                        })
                                                        let shuilv = 0;
                                                        if (this.table.qnnForm.form.getFieldsValue().taxRate) {
                                                            shuilv = Number((this.table.qnnForm.form.getFieldsValue().taxRate) * 0.01);
                                                        }
                                                        if (rowData.inQty) {//??????
                                                            newRowData.inPriceTax = Number(this.FloatMulTwo(colVal, shuilv).toFixed(2)); // ??????????????????
                                                            newRowData.inPrice = Number(colVal + newRowData.inPriceTax);// ??????????????????


                                                            if (this.table.qnnForm.form.getFieldsValue().isDeduct === '0') {//????????????-0???   1???
                                                                newRowData.inAmt = Number(this.FloatMulTwo(newRowData.inPrice, rowData.inQty).toFixed(2));//???-???
                                                                newRowData.remainAmt = Number((this.FloatMulTwo(newRowData.inPrice, rowData.inQty) - Number(rowData.feeSum ? rowData.feeSum : 0)).toFixed(2));//???-???
                                                            } else {
                                                                newRowData.inAmt = Number(this.FloatMulTwo(colVal, rowData.inQty).toFixed(2));//???-???
                                                                newRowData.remainAmt = Number((this.FloatMulTwo(colVal, rowData.inQty) - Number(rowData.feeSum ? rowData.feeSum : 0)).toFixed(2));//???-???
                                                            }
                                                            newRowData.inAmtTotal = Number(this.FloatMulTwo(newRowData.inPrice, rowData.inQty).toFixed(2));// ??????
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
                                                width: 120,
                                                dataIndex: 'inPrice',
                                                key: 'inPrice',
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
                                                type: 'number',
                                                field: 'inPrice',
                                                precision: 0,
                                                onChange: async (colVal, thisProps, tableBtnCallbackFn) => {
                                                    if (colVal >= 0 && typeof (colVal) === 'number') {
                                                        const rowData = await tableBtnCallbackFn.getEditedRowData(false);
                                                        const newRowData = {
                                                            ...rowData
                                                        }
                                                        this.table.qnnForm.form.setFieldsValue({
                                                            cantEdit: 'no',
                                                            cantEditShui: 'no'
                                                        })
                                                        let shuilv = 0;
                                                        if (this.table.qnnForm.form.getFieldsValue().taxRate) {
                                                            shuilv = Number((this.table.qnnForm.form.getFieldsValue().taxRate) * 0.01);
                                                        }
                                                        newRowData.inPriceNoTax = Number((colVal / (1 + shuilv)).toFixed(6)); // ????????????-000004193
                                                        // newRowData.inPriceTax = Number(this.FloatMulTwo(colVal, shuilv).toFixed(2));
                                                        newRowData.inPriceTax = Number(this.FloatMuljianfa(Number(colVal), newRowData.inPriceNoTax)); // ??????????????????
                                                        newRowData.inAmtTotal = Number(this.FloatMulTwo(colVal, rowData.inQty).toFixed(2));// ??????
                                                        if (rowData.inQty) {
                                                            if (this.table.qnnForm.form.getFieldsValue().isDeduct === '0') {//????????????-0???   1???
                                                                newRowData.inAmt = Number(this.FloatMulTwo(colVal, rowData.inQty).toFixed(2));//???-???
                                                                newRowData.remainAmt = Number((this.FloatMulTwo(colVal, rowData.inQty) - Number(rowData.feeSum ? rowData.feeSum : 0)).toFixed(2));//???-???
                                                            } else {
                                                                newRowData.inAmt = Number((newRowData.inAmtTotal / (1 + shuilv)).toFixed(2));//???-???
                                                                newRowData.remainAmt = Number((this.FloatMulTwo(newRowData.inPriceNoTax, rowData.inQty) - Number(rowData.feeSum ? rowData.feeSum : 0)).toFixed(2));//???-???
                                                            }

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
                                                title: '??????',
                                                width: 120,
                                                dataIndex: 'inAmtTotal',
                                                key: 'inAmtTotal'
                                            },
                                            form: {
                                                type: 'number',
                                                field: 'inAmtTotal',
                                                precision: 2,
                                            }
                                        },
                                        {
                                            table: {
                                                title: '????????????',
                                                width: 120,
                                                dataIndex: 'feeSum',
                                                key: 'feeSum',
                                                tdEdit: true
                                            },
                                            form: {
                                                type: 'number',
                                                field: 'feeSum',
                                                precision: 2,
                                                onChange: async (colVal, thisProps, tableBtnCallbackFn) => {
                                                    if (colVal >= 0 && typeof (colVal) === 'number') {
                                                        this.table.qnnForm.form.setFieldsValue({
                                                            cantEdit: 'no',
                                                            cantEditShui: 'no'
                                                        })
                                                        const rowData = await tableBtnCallbackFn.getEditedRowData(false);
                                                        const newRowData = {
                                                            ...rowData
                                                        }
                                                        if (rowData.inAmt) {//??????
                                                            newRowData.remainAmt = Number((rowData.inAmt - colVal).toFixed(2));// ??????=??????-????????????
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
                                                width: 120,
                                                dataIndex: 'remainAmt',
                                                key: 'remainAmt'
                                            },
                                            form: {
                                                type: 'number',
                                                field: 'remainAmt',
                                                precision: 2
                                            }
                                        },
                                        {
                                            isInTable: false,
                                            table: {
                                                title: '????????????',
                                                width: 120,
                                                dataIndex: 'precollecte',
                                                key: 'precollecte',
                                                type: 'select'
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
                                                ]
                                            }
                                        },
                                        {
                                            table: {
                                                title: '??????',
                                                width: 120,
                                                tooltip: 23,
                                                dataIndex: 'remarks',
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
                                        }
                                    ],
                                    actionBtns: [
                                        {
                                            name: "addRow",
                                            icon: "plus",
                                            type: "primary",
                                            label: "??????",
                                            addRowDefaultData: {
                                                // precollecte: '1'//?????????000004197
                                                precollecte: '0'//?????????000004197
                                            },
                                            hide: (val) => {
                                                let formData = this.table.qnnForm.form.getFieldsValue();
                                                if (formData.purchType === '?????????') {//?????????
                                                    return true
                                                } else {
                                                    return false
                                                }
                                            }
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
                            // isInTable: false,
                            table: {
                                title: '????????????',
                                dataIndex: 'recePerson',
                                key: 'recePerson',
                            },
                            form: {
                                type: 'string',
                                field: 'recePerson',
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
                            // isInTable: false,
                            table: {
                                title: '????????????',
                                dataIndex: 'purcPerson',
                                key: 'purcPerson',
                            },
                            form: {
                                type: 'string',
                                field: 'purcPerson',
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
                                type: 'string',
                                field: 'status',
                                hide: true
                            }
                        }
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
                                if (obj.selectedRows[0].purchType === '?????????') {
                                    this.setState({
                                        abcVal: 'false'
                                    })
                                    let selectRow = obj.selectedRows[0].zxSkTurnoverInItemList;
                                    obj.qnnTableInstance.setDeawerValues({
                                        zxSkTurnoverInItemList: selectRow.map((item) => {
                                            item.canEdit = 'no';
                                            return item
                                        })
                                    });
                                    // 20210329??????
                                    // if (this.table && this.table.qnnForm) {
                                    //     this.table.qnnForm.form.setFieldsValue({
                                    //         zxSkTurnoverInItemList: selectRow.map((item) => {
                                    //             item.canEdit = 'no';
                                    //             return item
                                    //         })
                                    //     })
                                    // }
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
                                            myFetch('checkZxSkTurnoverIn', { id: obj.selectedRows[0].id }).then(
                                                ({ data, success, message }) => {
                                                    if (success) {
                                                        Msg.success(message);
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
                        onClickFunPrint: (obj) => { },
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
                                        myFetch('batchDeleteUpdateZxSkTurnoverIn', obj.selectedRows).then(
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
                    //                     apiName: 'addZxSkTurnoverIn'
                    //                 }
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
                    //                     apiName: 'updateZxSkTurnoverIn'
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
                    //         label: '??????????????????',
                    //         disabled: 'bind:disabledFunEditDate',
                    //         onClick: 'bind:onClickFunEditDate'
                    //     },
                    //     {
                    //         name: 'diy',
                    //         type: 'primary',
                    //         label: '??????',
                    //         disabled: "bind:_actionBtnNoSelected",
                    //         onClick: 'bind:onClickFunPrint'
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
                                tableField: "Warehousing"
                            }
                        }
                    }}
                />
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

                                        this.props.myFetch('updateZxSkTurnoverIn', values).then(
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
                                            }
                                        );

                                        obj.btnCallbackFn.clearSelectedRows();
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
                            }} src={`${ureport}preview?_u=minio:ZxSkTurnoverIn.preview.xml&_t=1,6&_n=${name}&access_token=${access_token}&id=${idPreview}
                            `} title="byLm"></iframe>
                        </Spin> : ''
                    }

                </Modal>
            </div>
        );
    }
}

export default index;