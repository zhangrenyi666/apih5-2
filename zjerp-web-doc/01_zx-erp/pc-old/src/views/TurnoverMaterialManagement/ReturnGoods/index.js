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
    paginationConfig: false,
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
            selectedRowsData: {}
        }
    }
    componentDidMount() { }
    FloatMulTwo(arg1, arg2) {
        var m = 0, s1 = arg1.toString(), s2 = arg2.toString();
        try { m += s1.split(".")[1].length } catch (e) { }
        try { m += s2.split(".")[1].length } catch (e) { }
        return Number(s1.replace(".", "")) * Number(s2.replace(".", "")) / Math.pow(10, m)
    }
    // 单据编号
    getDjbhFun(val, busDate) {
        const { myFetch } = this.props;
        myFetch('getZxSkReturnsNo', {
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
    //日期控制
    dateChange(val, type) {
        var date = new Date;
        var year = date.getFullYear();
        var month = date.getMonth();
        // 取上个月的时间
        if (month == 0) {//当前是一月份
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
    render() {
        const { visibleSendData, loadingSendData, selectedRowsData } = this.state;
        const { departmentId, companyName, companyId } = this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany;
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
                        apiName: 'getZxSkReturnsList',
                        otherParams: {
                            orgID: departmentId
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
                            table: {
                                title: '单据编号',
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
                                    return <a style={{ color: color }}>{data}</a>
                                }
                            },
                            form: {
                                field: 'billNo',
                                type: 'string',
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
                                title: '退货单位',
                                dataIndex: 'orgID',
                                key: 'orgID',
                                width: 160,
                                type: 'select'
                            },
                            form: {
                                field: 'orgID',
                                required: true,
                                allowClear: false,
                                type: 'select',
                                showSearch: true,
                                editDisabled: true,
                                optionConfig: {
                                    label: 'orgName',
                                    value: 'orgID'
                                },
                                fetchConfig: {
                                    apiName: 'getZxCtContractList',
                                    otherParams: {
                                        contrStatus: "1",
                                        orgID: departmentId
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
                                field: 'orgName',
                                hide: true
                            }
                        },

                        {
                            table: {
                                title: '退货日期',
                                width: 120,
                                onClick: 'detail',
                                dataIndex: 'busDate',
                                format: 'YYYY-MM-DD',
                                key: 'busDate',
                                render: (data) => {
                                    return data ? moment(data).format('YYYY-MM-DD') : null
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
                                onChange: (val) => {
                                    if (val) {
                                        this.dateChange(val, 1);
                                    }
                                }
                            }
                        },

                        {
                            table: {
                                title: '供货单位',
                                dataIndex: 'outOrgID',
                                key: 'outOrgID',
                                width: 200,
                                type: 'select'
                            },
                            form: {
                                field: 'outOrgID',
                                required: true,
                                allowClear: false,
                                type: 'selectByPaging',
                                showSearch: true,
                                optionConfig: {
                                    label: 'customerName',
                                    value: 'zxCrCustomerNewId',
                                    linkageFields: {
                                        "outOrgType": "supplierType"
                                    }
                                },
                                fetchConfig: {
                                    apiName: 'getZxCrCustomerNewList',
                                    // otherParams: {
                                    //     limit: 999,
                                    //     page: 1
                                    // }
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
                                    this.table.qnnForm.form.setFieldsValue({
                                        outOrgName: rowData.itemData.customerName
                                    })
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
                            table: {
                                title: '是否抵扣',
                                dataIndex: 'isDeduct',
                                key: 'isDeduct',
                                type: 'select'
                            },
                            form: {
                                field: 'isDeduct',
                                type: 'select',
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
                                        regex: { cantEdit: 'no', },
                                        action: 'disabled',
                                    },
                                ]
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
                                field: 'zxSkReturnsItemList',
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
                                                label: '主键id',
                                                field: 'id',
                                                hide: true
                                            }
                                        },
                                        {
                                            table: {
                                                title: '物资编码',
                                                width: 200,
                                                tooltip: 50,
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
                                                        apiName: "getZxSkReturnsResourceList",
                                                        otherParams: async () => {
                                                            const vals = await this.table.btnCallbackFn.qnnForm.getValues(false);
                                                            return {
                                                                orgID: vals.orgID,
                                                                outOrgID: vals.outOrgID
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
                                                                title: "编号",
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
                                                                title: "名称",
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
                                                                title: "单位",
                                                            },
                                                            form: {
                                                                type: "string"
                                                            }
                                                        },
                                                        {
                                                            isInForm: false,
                                                            // isInSearch:true,
                                                            table: {
                                                                dataIndex: "inBusDate",
                                                                title: "入库日期",
                                                                format: 'YYYY-MM-DD'
                                                            },
                                                            form: {
                                                                type: "date"
                                                            }
                                                        },
                                                        {
                                                            isInForm: false,
                                                            // isInSearch:true,
                                                            table: {
                                                                dataIndex: "stockQty",
                                                                title: "库存数量",
                                                            },
                                                            form: {
                                                                type: "number"
                                                            }
                                                        },
                                                        {
                                                            isInForm: false,
                                                            // isInSearch:true,
                                                            table: {
                                                                dataIndex: "originAmt",
                                                                title: "原值",
                                                            },
                                                            form: {
                                                                type: "number"
                                                            }
                                                        },
                                                        {
                                                            isInForm: false,
                                                            // isInSearch:true,
                                                            table: {
                                                                dataIndex: "remainAmt",
                                                                title: "净值",
                                                            },
                                                            form: {
                                                                type: "number"
                                                            }
                                                        }
                                                    ]
                                                },
                                                onChange: (colVal, thisProps, tableBtnCallbackFn) => {
                                                    if (colVal) {
                                                        const itemData = thisProps;
                                                        tableBtnCallbackFn.setEditedRowData({
                                                            resName: itemData.itemData[0].resName,
                                                            resUnit: itemData.itemData[0].resUnit,
                                                            spec: itemData.itemData[0].spec,
                                                            resID: itemData.itemData[0].resID,
                                                            stockQty: itemData.itemData[0].stockQty,
                                                            inBusDate: itemData.itemData[0].inBusDate,
                                                            batchNo: itemData.itemData[0].batchNo,
                                                            returnQty: 0,
                                                            returnPriceNoTax: 0,
                                                            returnPriceTax: 0,
                                                            returnPrice: 0,
                                                            returnAmt: 0,
                                                            originAmt: 0,
                                                            remainAmt: 0
                                                        });
                                                        this.table.qnnForm.form.setFieldsValue({
                                                            cantEdit: 'no'
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
                                                                stockQty: 0,
                                                                returnQty: 0,
                                                                returnPriceNoTax: 0,
                                                                returnPriceTax: 0,
                                                                returnPrice: 0,
                                                                returnAmt: 0,
                                                                originAmt: 0,
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
                                                title: '入库日期',
                                                dataIndex: 'inBusDate',
                                                key: 'inBusDate',
                                                format: 'YYYY-MM-DD'
                                            },
                                            form: {
                                                type: 'date',
                                                field: 'inBusDate'
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
                                                title: '库存量',
                                                dataIndex: 'stockQty',
                                                key: 'stockQty',
                                            },
                                            form: {
                                                type: 'number',
                                                field: 'stockQty',
                                                precision: 3
                                            }
                                        },
                                        {
                                            table: {
                                                title: '退货数量',
                                                width: 100,
                                                dataIndex: 'returnQty',
                                                key: 'returnQty',
                                                tdEdit: true
                                            },
                                            form: {
                                                type: 'number',
                                                field: 'returnQty',
                                                precision: 3,
                                                onChange: async (colVal, thisProps, tableBtnCallbackFn) => {
                                                    if (colVal) {
                                                        clearTimeout(this.tdEditedTimer);
                                                        this.tdEditedTimer = setTimeout(async () => {
                                                            const rowData = await tableBtnCallbackFn.getEditedRowData(false);
                                                            const newRowData = {
                                                                ...rowData
                                                            }
                                                            this.table.qnnForm.form.setFieldsValue({
                                                                cantEdit: 'no'
                                                            })

                                                            if (colVal > rowData.stockQty) {//校验库存：退货数量、库存量
                                                                Msg.warn('超出库存数量！');
                                                                newRowData.returnQty = rowData.stockQty;
                                                            } else {
                                                                if (rowData.returnPriceNoTax) {
                                                                    newRowData.returnAmt = Number(this.FloatMulTwo(rowData.returnPriceNoTax, colVal).toFixed(2));// 退货金额
                                                                }
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
                                                title: '退货不含税单价',
                                                width: 120,
                                                dataIndex: 'returnPriceNoTax',
                                                key: 'returnPriceNoTax',
                                                tdEdit: true
                                            },
                                            form: {
                                                type: 'number',
                                                field: 'returnPriceNoTax',
                                                precision: 6,
                                                onChange: async (colVal, thisProps, tableBtnCallbackFn) => {
                                                    if (colVal) {
                                                        clearTimeout(this.tdEditedTimer);
                                                        this.tdEditedTimer = setTimeout(async () => {
                                                            const rowData = await tableBtnCallbackFn.getEditedRowData(false);
                                                            const newRowData = {
                                                                ...rowData
                                                            }
                                                            this.table.qnnForm.form.setFieldsValue({
                                                                cantEdit: 'no'
                                                            })
                                                            let shuilv = 0;
                                                            if (this.table.qnnForm.form.getFieldsValue().taxRate) {
                                                                shuilv = Number((this.table.qnnForm.form.getFieldsValue().taxRate) * 0.01);
                                                            }
                                                            if (rowData.returnQty) {
                                                                newRowData.returnPriceTax = Number(this.FloatMulTwo(shuilv, colVal).toFixed(2));// 退货单价税金
                                                                newRowData.returnPrice = Number(this.FloatMulTwo((shuilv + 1), colVal).toFixed(2));// 退货单价合计
                                                                if (this.table.qnnForm.form.getFieldsValue().isDeduct === '0') {
                                                                    //参与抵扣-0否   1是
                                                                    newRowData.returnAmt = Number(this.FloatMulTwo(rowData.returnQty, newRowData.returnPrice).toFixed(2));// 退货-金额
                                                                } else {
                                                                    newRowData.returnAmt = Number(this.FloatMulTwo(rowData.returnQty, colVal).toFixed(2));// 退货-金额
                                                                }
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
                                                title: '退货单价税金',
                                                width: 120,
                                                dataIndex: 'returnPriceTax',
                                                key: 'returnPriceTax',
                                            },
                                            form: {
                                                type: 'number',
                                                field: 'returnPriceTax',
                                                precision: 2
                                            }
                                        },
                                        {
                                            table: {
                                                title: '退货单价合计',
                                                width: 120,
                                                dataIndex: 'returnPrice',
                                                key: 'returnPrice',
                                                tdEdit: true
                                            },
                                            form: {
                                                type: 'number',
                                                field: 'returnPrice',
                                                precision: 2,
                                                onChange: async (colVal, thisProps, tableBtnCallbackFn) => {
                                                    this.table.qnnForm.form.setFieldsValue({
                                                        cantEdit: 'no'
                                                    })
                                                }
                                            }
                                        },
                                        {
                                            table: {
                                                title: '退货金额',
                                                width: 120,
                                                dataIndex: 'returnAmt',
                                                key: 'returnAmt',
                                                // tdEdit: true
                                            },
                                            form: {
                                                type: 'number',
                                                field: 'returnAmt',
                                                precision: 2,
                                                onChange: async (colVal, thisProps, tableBtnCallbackFn) => {
                                                    this.table.qnnForm.form.setFieldsValue({
                                                        cantEdit: 'no'
                                                    })
                                                }
                                            }
                                        },
                                        {
                                            table: {
                                                title: '退货原值',
                                                width: 120,
                                                dataIndex: 'originAmt',
                                                key: 'originAmt',
                                                tdEdit: true
                                            },
                                            form: {
                                                type: 'number',
                                                required: true,
                                                field: 'originAmt',
                                                precision: 2,
                                                onChange: async (colVal, thisProps, tableBtnCallbackFn) => {
                                                    this.table.qnnForm.form.setFieldsValue({
                                                        cantEdit: 'no'
                                                    })
                                                }
                                            }
                                        },
                                        {
                                            table: {
                                                title: '退货净值',
                                                width: 120,
                                                dataIndex: 'remainAmt',
                                                key: 'remainAmt',
                                                tdEdit: true
                                            },
                                            form: {
                                                type: 'number',
                                                required: true,
                                                field: 'remainAmt',
                                                precision: 2,
                                                onChange: async (colVal, thisProps, tableBtnCallbackFn) => {
                                                    this.table.qnnForm.form.setFieldsValue({
                                                        cantEdit: 'no'
                                                    })
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
                            table: {
                                title: '审核状态',
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
                    actionBtns={[
                        {
                            name: 'add',
                            icon: 'plus',
                            type: 'primary',
                            label: '新增',
                            formBtns: [
                                {
                                    name: 'cancel',
                                    type: 'dashed',
                                    label: '取消',
                                },
                                {
                                    name: 'submit',
                                    type: 'primary',
                                    label: '保存',
                                    field: 'addsubmit',
                                    fetchConfig: {
                                        apiName: 'addZxSkReturns'
                                    }
                                }
                            ]
                        },
                        {
                            name: 'edit',
                            icon: 'edit',
                            type: 'primary',
                            label: '修改',
                            onClick: (obj) => {
                                if (obj.selectedRows[0].status === '1') {
                                    obj.btnCallbackFn.closeDrawer();
                                    Msg.warn('已审核的不能修改!');
                                } else {

                                }
                                this.table.clearSelectedRows();
                            },
                            formBtns: [
                                {
                                    name: 'cancel',
                                    type: 'dashed',
                                    label: '取消',
                                },
                                {
                                    name: 'submit',
                                    type: 'primary',
                                    label: '保存',
                                    fetchConfig: {
                                        apiName: 'updateZxSkReturns'
                                    }
                                }
                            ]
                        },
                        {
                            name: 'diy',
                            type: 'primary',
                            label: '审核',
                            disabled: "bind:_actionBtnNoSelected",
                            onClick: (obj) => {
                                const { myFetch } = this.props;
                                if (obj.selectedRows.length === 1) {
                                    if (obj.selectedRows[0].status === '1') {
                                        obj.btnCallbackFn.closeDrawer();
                                        this.table.clearSelectedRows();
                                        Msg.warn('已审核的不能审核!');
                                    } else {
                                        // obj.selectedRows[0].companyID = '1';
                                        confirm({
                                            content: '确定审核选中的数据吗?',
                                            onOk: () => {
                                                myFetch('checkZxSkReturns', { id: obj.selectedRows[0].id }).then(
                                                    ({ data, success, message }) => {
                                                        if (success) {
                                                            Msg.warn(message);
                                                            this.table.refresh();
                                                            this.table.clearSelectedRows();
                                                        } else {
                                                            Msg.warn(message);
                                                            this.table.refresh();
                                                            this.table.clearSelectedRows();
                                                        }
                                                    }
                                                );
                                            }
                                        });
                                    }
                                } else {
                                    Msg.warn('只能审核一条数据！')
                                }
                            }
                        },
                        {
                            name: 'diy',
                            type: 'primary',
                            label: '修改业务日期',
                            disabled: "bind:_actionBtnNoSelected",
                            onClick: (obj) => {
                                if (obj.selectedRows.length === 1) {
                                    this.setState({
                                        visibleSendData: true,
                                        loadingSendData: false,
                                        selectedRowsData: obj.selectedRows[0]
                                    })
                                } else {
                                    Msg.warn('请选择一条数据！')
                                }
                            }
                        },
                        {
                            name: 'diy',
                            type: 'primary',
                            label: '打印',
                            disabled: "bind:_actionBtnNoSelected",
                            onClick: (obj) => {

                            }
                        },
                        {
                            name: 'diyDel',
                            icon: 'delete',
                            type: 'danger',
                            label: '删除',
                            disabled: "bind:_actionBtnNoSelected",
                            onClick: (obj) => {
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
                                            myFetch('batchDeleteUpdateZxSkReturns', obj.selectedRows).then(
                                                ({ data, success, message }) => {
                                                    if (success) {
                                                        this.table.refresh();
                                                        this.table.clearSelectedRows();
                                                    } else {
                                                    }
                                                }
                                            );
                                        }
                                    });
                                }
                            }
                        }
                    ]}

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
                                    label: "退货日期",
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
                                    onClick: () => {
                                        this.setState({
                                            visibleSendData: false,
                                            loadingSendData: false
                                        })
                                        this.table.clearSelectedRows();
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
                                        this.table.clearSelectedRows();
                                        this.props.myFetch('updateZxSkReturns', values).then(
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
            </div>
        );
    }
}

export default index;