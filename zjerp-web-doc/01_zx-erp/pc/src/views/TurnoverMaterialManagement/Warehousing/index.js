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
    // 单据编号
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
            isTurnover: 0,//是否周转材（0：是；1：不是）
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
                                // item.resAllFeeNoTax = item.contractSumNoTax;//不存在这个字段
                                item.inPrice = item.price;// 购入单价合计
                                // item.inPriceNoTax = item.priceNoTax; // 购入单价
                                item.inPriceNoTax = Number((item.inPrice / (1 + shuilvNew)).toFixed(6)); // 购入单价
                                // item.resAllFee = item.contractSum;//不存在这个字段
                                item.remark = item.remarks;
                                // item.precollecte = '1';//编号：000004197
                                item.precollecte = '0';
                                // item.resAllFeeTax = item.contractSumTax;//不存在这个字段



                                item.inPriceTax = Number(this.FloatMulTwo(item.inPriceNoTax, shuilvNew).toFixed(2)); // 购入单价税额
                                item.inAmtTotal = Number(this.FloatMulTwo(item.inPrice, item.inQty).toFixed(2));// 总值

                                // if (this.table.qnnForm.form.getFieldsValue().isDeduct === '0') {
                                //     item.inAmt = Number(Number(item.resAllFee).toFixed(2));//原-值
                                //     item.remainAmt = Number(this.FloatMulTwo(item.inPrice, item.inQty).toFixed(2));//净-值
                                // } else {
                                //     item.inAmt = Number(Number(item.resAllFeeNoTax).toFixed(2));//原-值
                                //     item.remainAmt = Number(this.FloatMulTwo(item.inPriceNoTax, item.inQty).toFixed(2));//净-值
                                // }

                                if (this.table.qnnForm.form.getFieldsValue().isDeduct === '0') {//参与抵扣-0否   1是
                                    item.inAmt = Number(this.FloatMulTwo(item.inPrice, item.inQty).toFixed(2));//原-值
                                    item.remainAmt = Number((this.FloatMulTwo(item.inPrice, item.inQty) - Number(item.feeSum ? item.feeSum : 0)).toFixed(2));//净-值
                                } else {
                                    item.inAmt = Number(this.FloatMulTwo(item.inPriceNoTax, item.inQty).toFixed(2));//原-值
                                    item.remainAmt = Number((this.FloatMulTwo(item.inPriceNoTax, item.inQty) - Number(item.feeSum ? item.feeSum : 0)).toFixed(2));//净-值
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
    //日期控制
    dateChange(val, type) {
        var date = new Date();
        var year = date.getFullYear();
        var month = date.getMonth();
        // 取上个月的时间
        if (month === 0) {//当前是一月份
            year = year - 1;
            month = 12;
        } else {
            // month = month - 1;
        }
        var lastDay = new Date(year, month - 1, 26);//上个月26号
        var toDay = new Date(year, month, 26);//本月26号
        const stamp = (value) => {
            const s_time = moment(value).format('YYYY-MM-DD');
            const time = new Date(s_time).getTime();
            return time;
        }
        const time1 = stamp(val._d);//选择的日期
        const time2 = stamp(lastDay);//上个月26号
        const time3 = stamp(toDay);//本月26号
        if (date.getDate() < 26) {//当前日期小于26号
            if (time1 < time2) {
                Msg.warn('不能补录上月26号零点前的数据！');
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
        } else {//当前日期大于等于26号
            if (time1 < time3) {
                Msg.warn('不能补录本月26号零点前的数据！');
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
                                title: '单据编号',
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
                                placeholder: '请输入',
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
                                title: '收料单位',
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
                                placeholder: '请选择',
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
                                title: '物资来源',
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
                                        label: "购入",
                                        value: "1"
                                    },
                                    {
                                        label: "调拨",
                                        value: "3"
                                    }
                                ],
                                placeholder: '请选择',
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
                                title: '入库日期',
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
                                title: '物资员',
                                width: 100,
                                dataIndex: 'consignee',
                                key: 'consignee',
                            },
                            form: {
                                label: '材料员',
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
                                title: '供货单位',
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
                                placeholder: '请选择',
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
                                    if (obj.Pstate.drawerDetailTitle === '详情') {
                                        return 'no'
                                    } else if (obj.Pstate.drawerDetailTitle === '编辑') {
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
                                    if (obj.Pstate.drawerDetailTitle === '详情') {
                                        return 'no'
                                    } else if (obj.Pstate.drawerDetailTitle === '编辑') {
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
                                title: '采购类别',
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
                                placeholder: '请选择',
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
                                    if (val === '有合同') {
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
                                    if (obj.Pstate.drawerDetailTitle === '详情') {
                                        return 'true'
                                    } else if (obj.Pstate.drawerDetailTitle === '编辑') {
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
                                title: '物资合同',
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
                                dependenciesReRender: true,//多个依赖-配置 
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
                                placeholder: '请选择',
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
                                            purchType: ['!', '有合同'],
                                        },
                                        action: ['disabled', 'unRequired'],
                                    },
                                    {
                                        regex: {
                                            purchType: '有合同'
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
                                title: '是否抵扣',
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
                                        label: "否",
                                        value: "0"
                                    },
                                    {
                                        label: "是",
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
                                title: '税率(%)',
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
                                placeholder: '请选择',
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
                                title: '备注',
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
                                                label: '主键id',
                                                field: 'id',
                                                hide: true
                                            }
                                        },
                                        {
                                            table: {
                                                title: '物资编码',
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
                                                                id: vals.resourceID,//物资类别
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
                                                                title: "物资编码",
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
                                                                title: "物资名称",
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
                                                                title: "规格型号",
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
                                                                title: "计量单位",
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
                                                                title: "计价方式",
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
                                                                title: "编制机构",
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
                                                title: '物资名称',
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
                                                title: '规格型号',
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
                                                title: '计量单位',
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
                                                title: '数量',
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

                                                        if (rowData.inPriceNoTax) {//购入单价
                                                            if (this.table.qnnForm.form.getFieldsValue().purchType === '有合同') {
                                                                newRowData.inPrice = rowData.inPrice;
                                                            } else {
                                                                newRowData.inPrice = Number(this.accAdd(rowData.inPriceNoTax, rowData.inPriceTax));// 购入单价合计
                                                            }
                                                            if (this.table.qnnForm.form.getFieldsValue().isDeduct === '0') {//参与抵扣-0否   1是
                                                                newRowData.inAmt = Number(this.FloatMulTwo(newRowData.inPrice, colVal).toFixed(2));//原-值
                                                                newRowData.remainAmt = Number((this.FloatMulTwo(newRowData.inPrice, colVal) - Number(rowData.feeSum ? rowData.feeSum : 0)).toFixed(2));//净-值
                                                            } else {
                                                                newRowData.inAmt = Number(this.FloatMulTwo(rowData.inPriceNoTax, colVal).toFixed(2));//原-值
                                                                newRowData.remainAmt = Number((this.FloatMulTwo(rowData.inPriceNoTax, colVal) - Number(rowData.feeSum ? rowData.feeSum : 0)).toFixed(2));//净-值
                                                            }
                                                            newRowData.inAmtTotal = Number(this.FloatMulTwo(newRowData.inPrice, colVal).toFixed(2));// 总值
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
                                                title: '购入单价税额',
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
                                                title: '购入单价',
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
                                                        if (rowData.inQty) {//数量
                                                            newRowData.inPriceTax = Number(this.FloatMulTwo(colVal, shuilv).toFixed(2)); // 购入单价税额
                                                            newRowData.inPrice = Number(colVal + newRowData.inPriceTax);// 购入单价合计


                                                            if (this.table.qnnForm.form.getFieldsValue().isDeduct === '0') {//参与抵扣-0否   1是
                                                                newRowData.inAmt = Number(this.FloatMulTwo(newRowData.inPrice, rowData.inQty).toFixed(2));//原-值
                                                                newRowData.remainAmt = Number((this.FloatMulTwo(newRowData.inPrice, rowData.inQty) - Number(rowData.feeSum ? rowData.feeSum : 0)).toFixed(2));//净-值
                                                            } else {
                                                                newRowData.inAmt = Number(this.FloatMulTwo(colVal, rowData.inQty).toFixed(2));//原-值
                                                                newRowData.remainAmt = Number((this.FloatMulTwo(colVal, rowData.inQty) - Number(rowData.feeSum ? rowData.feeSum : 0)).toFixed(2));//净-值
                                                            }
                                                            newRowData.inAmtTotal = Number(this.FloatMulTwo(newRowData.inPrice, rowData.inQty).toFixed(2));// 总值
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
                                                title: '购入单价合计',
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
                                                        newRowData.inPriceNoTax = Number((colVal / (1 + shuilv)).toFixed(6)); // 购入单价-000004193
                                                        // newRowData.inPriceTax = Number(this.FloatMulTwo(colVal, shuilv).toFixed(2));
                                                        newRowData.inPriceTax = Number(this.FloatMuljianfa(Number(colVal), newRowData.inPriceNoTax)); // 购入单价税额
                                                        newRowData.inAmtTotal = Number(this.FloatMulTwo(colVal, rowData.inQty).toFixed(2));// 总值
                                                        if (rowData.inQty) {
                                                            if (this.table.qnnForm.form.getFieldsValue().isDeduct === '0') {//参与抵扣-0否   1是
                                                                newRowData.inAmt = Number(this.FloatMulTwo(colVal, rowData.inQty).toFixed(2));//原-值
                                                                newRowData.remainAmt = Number((this.FloatMulTwo(colVal, rowData.inQty) - Number(rowData.feeSum ? rowData.feeSum : 0)).toFixed(2));//净-值
                                                            } else {
                                                                newRowData.inAmt = Number((newRowData.inAmtTotal / (1 + shuilv)).toFixed(2));//原-值
                                                                newRowData.remainAmt = Number((this.FloatMulTwo(newRowData.inPriceNoTax, rowData.inQty) - Number(rowData.feeSum ? rowData.feeSum : 0)).toFixed(2));//净-值
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
                                                title: '原值',
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
                                                title: '总值',
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
                                                title: '累计摊销',
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
                                                        if (rowData.inAmt) {//数量
                                                            newRowData.remainAmt = Number((rowData.inAmt - colVal).toFixed(2));// 净值=原值-累计摊销
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
                                                title: '净值',
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
                                                title: '是否预收',
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
                                                        label: '否',
                                                        value: '0'
                                                    },
                                                    {
                                                        label: '是',
                                                        value: '1'
                                                    }
                                                ]
                                            }
                                        },
                                        {
                                            table: {
                                                title: '备注',
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
                                            label: "新增",
                                            addRowDefaultData: {
                                                // precollecte: '1'//编号：000004197
                                                precollecte: '0'//编号：000004197
                                            },
                                            hide: (val) => {
                                                let formData = this.table.qnnForm.form.getFieldsValue();
                                                if (formData.purchType === '有合同') {//有合同
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
                                            label: "删除",
                                        }
                                    ]
                                }
                            }
                        },
                        {
                            // isInTable: false,
                            table: {
                                title: '收料人员',
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
                                title: '采购人员',
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
                                title: '单据状态',
                                width: 100,
                                dataIndex: 'status',
                                key: 'status',
                                render: (data) => {
                                    if (data) {
                                        return data === '0' ? '未审核' : '已审核'
                                    } else {
                                        return '未审核'
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
                                Msg.warn('已审核的不能修改!');
                                return false
                            } else {
                                if (obj.selectedRows[0].purchType === '有合同') {
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
                                    // 20210329修改
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
                                    Msg.warn('已审核的不能审核!');
                                } else {
                                    obj.selectedRows[0].companyID = '1';
                                    confirm({
                                        content: '确定审核选中的数据吗?',
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
                                Msg.warn('只能审核一条数据！')
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
                                Msg.warn('请选择一条数据！')
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
                                    //存在已审核的数据
                                    arry.push(obj.selectedRows[m].status);
                                }
                            }
                            if (arry.length > 0) {
                                Msg.warn('请选择未审核的数据！')
                            } else {
                                confirm({
                                    content: '确定删除选中的数据吗?',
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
                        // 预览
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
                    //         label: '新增',
                    //         formBtns: [
                    //             {
                    //                 name: 'cancel',
                    //                 type: 'dashed',
                    //                 label: '取消',
                    //             },
                    //             {
                    //                 name: 'submit',
                    //                 type: 'primary',
                    //                 label: '保存',
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
                    //         label: '修改',
                    //         disabled: 'bind:disabledFunEdit',
                    //         willExecute: 'bind:willExecuteFunEdit',
                    //         formBtns: [
                    //             {
                    //                 name: 'cancel',
                    //                 type: 'dashed',
                    //                 label: '取消',
                    //             },
                    //             {
                    //                 name: 'submit',
                    //                 type: 'primary',
                    //                 label: '保存',
                    //                 fetchConfig: {
                    //                     apiName: 'updateZxSkTurnoverIn'
                    //                 }
                    //             }
                    //         ]
                    //     },
                    //     {
                    //         name: 'diy',
                    //         type: 'primary',
                    //         label: '审核',
                    //         disabled: 'bind:disabledFunShenPi',
                    //         onClick: 'bind:onClickFunShenPi'
                    //     },
                    //     {
                    //         name: 'diy',
                    //         type: 'primary',
                    //         label: '修改业务日期',
                    //         disabled: 'bind:disabledFunEditDate',
                    //         onClick: 'bind:onClickFunEditDate'
                    //     },
                    //     {
                    //         name: 'diy',
                    //         type: 'primary',
                    //         label: '打印',
                    //         disabled: "bind:_actionBtnNoSelected",
                    //         onClick: 'bind:onClickFunPrint'
                    //     },
                    //     {
                    //         name: 'diyDel',
                    //         icon: 'delete',
                    //         type: 'danger',
                    //         label: '删除',
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
                    title="修改入库日期"
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
                                    label: "入库日期",
                                    required: true,
                                    placeholder: '请输入',
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
                                    label: '取消',
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
                                    label: '保存',
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
                    title="打印"
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