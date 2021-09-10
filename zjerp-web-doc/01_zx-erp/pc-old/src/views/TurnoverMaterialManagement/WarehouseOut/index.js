import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import QnnForm from "../../modules/qnn-table/qnn-form";
// import Guihuan from "./guihuan.js";
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
    componentDidMount() {
     }
    FloatMulTwo(arg1, arg2) {
        var m = 0, s1 = arg1.toString(), s2 = arg2.toString();
        try { m += s1.split(".")[1].length } catch (e) { }
        try { m += s2.split(".")[1].length } catch (e) { }
        return Number(s1.replace(".", "")) * Number(s2.replace(".", "")) / Math.pow(10, m)
    }
    // 单据编号
    getDjbhFun(val, busDate) {
        const { myFetch } = this.props;
        myFetch('getZxSkTurnoverOutNo', {
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
                        apiName: 'getZxSkTurnoverOutList',
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
                                width: 300,
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
                                },
                                initialValue: (obj) => {
                                    if (obj.clickCb && obj.clickCb.rowInfo) {
                                        if (obj.clickCb.rowInfo.name === 'detail') {
                                            return obj.clickCb.rowData.billNo
                                        } else if (obj.clickCb.rowInfo.name === 'edit') {
                                            return obj.clickCb.selectedRows[0].billNo
                                        } else if (obj.clickCb.rowInfo.name === 'diy') {
                                            return obj.clickCb.selectedRows[0].billNo
                                        } else {
                                            return ''
                                        }
                                    } else {
                                        return ''
                                    }

                                },
                                condition: [
                                    {
                                        regex: { djbh: 'old', },
                                        action: 'disabled'
                                    }
                                ]
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: 'age',
                                type: 'string',
                                field: 'age',
                                hide: true,
                                initialValue: (obj) => {
                                    if (obj.clickCb.rowInfo.name === 'add') {
                                        return 'new'
                                    } else {
                                        return 'old'
                                    }
                                },
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: 'djbh',
                                type: 'string',
                                field: 'djbh',
                                hide: true,
                                initialValue: (obj) => {
                                    return 'old'
                                },
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: 'can',
                                type: 'string',
                                field: 'can',
                                hide: true,
                                initialValue: (obj) => {
                                    if (obj.clickCb.rowInfo.name === 'detail' || obj.clickCb.rowInfo.name === 'diy') {
                                        return 'old'
                                    } else {
                                        return 'new'
                                    }
                                },
                            }
                        },
                        {
                            table: {
                                title: '发料单位',
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
                                            departmentId: departmentId
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
                                    apiName: 'getZxCtContractList',
                                    otherParams: {
                                        contrStatus: "1",
                                        orgID: departmentId
                                    }
                                },
                                children: [
                                    {
                                        field: 'inOrgID'
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
                                },
                                onChange: (val, rowData) => {
                                    if (this.table && this.table.qnnForm) {
                                        if (rowData) {
                                            this.table.qnnForm.form.setFieldsValue({
                                                orgName: rowData.itemData.orgName,
                                                inOrgID: ''
                                            })
                                        }

                                        this.table.qnnForm.clearValues(['zxSkTurnoverOutItemList']);
                                        let busDate = this.table.qnnForm.form.getFieldsValue().busDate;
                                        if (busDate || val) {
                                            this.getDjbhFun(val, busDate);
                                        } else {
                                            this.table.qnnForm.form.setFieldsValue({
                                                billNo: null
                                            });
                                        }
                                    }


                                },
                                initialValue: (obj) => {
                                    if (obj.clickCb && obj.clickCb.rowInfo) {
                                        if (obj.clickCb.rowInfo.name === 'detail') {
                                            return obj.clickCb.rowData.orgID
                                        } else if (obj.clickCb.rowInfo.name === 'edit') {
                                            return obj.clickCb.selectedRows[0].orgID
                                        } else if (obj.clickCb.rowInfo.name === 'diy') {
                                            return obj.clickCb.selectedRows[0].orgID
                                        } else {
                                            return ''
                                        }
                                    } else {
                                        return ''
                                    }

                                },
                                condition: [
                                    {
                                        regex: { age: 'old', },
                                        action: 'disabled'
                                    }
                                ]
                            },
                        },
                        {
                            table: {
                                title: '出库日期',
                                width: 120,
                                dataIndex: 'busDate',
                                format: 'YYYY-MM-DD',
                                key: 'busDate',
                                filter: true,
                                fieldsConfig: {
                                    type: 'rangeDate',
                                    field: 'timeList'
                                },
                                onClick: 'detail',
                                render: (data) => {
                                    return data ? moment(data).format('YYYY-MM-DD') : null
                                }
                            },
                            form: {
                                type: 'date',
                                required: true,
                                field: 'busDate',
                                initialValue: (obj) => {
                                    if (obj.clickCb && obj.clickCb.rowInfo) {
                                        if (obj.clickCb.rowInfo.name === 'detail') {
                                            return obj.clickCb.rowData.busDate
                                        } else if (obj.clickCb.rowInfo.name === 'edit') {
                                            return obj.clickCb.selectedRows[0].busDate
                                        } else if (obj.clickCb.rowInfo.name === 'diy') {
                                            return obj.clickCb.selectedRows[0].busDate
                                        } else {
                                            return moment(new Date()).format('YYYY-MM-DD')
                                        }
                                    } else {
                                        return moment(new Date()).format('YYYY-MM-DD')
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
                                },
                                condition: [
                                    {
                                        regex: { age: 'old', },
                                        action: 'disabled'
                                    }
                                ],
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
                                title: '领料单位',
                                dataIndex: 'inOrgName',
                                key: 'inOrgName',
                                width: 160,
                                filter: true,
                                fieldsConfig: {
                                    type: 'string',
                                    field: 'inOrgName'
                                }
                            },
                            form: {
                                field: 'inOrgID',
                                required: true,
                                initialValue: (obj) => {
                                    if (obj.clickCb && obj.clickCb.rowInfo) {
                                        if (obj.clickCb.rowInfo.name === 'detail') {
                                            return obj.clickCb.rowData.inOrgID
                                        } else if (obj.clickCb.rowInfo.name === 'edit') {
                                            return obj.clickCb.selectedRows[0].inOrgID
                                        } else if (obj.clickCb.rowInfo.name === 'diy') {
                                            return obj.clickCb.selectedRows[0].inOrgID
                                        } else {
                                            return ''
                                        }
                                    } else {
                                        return ''
                                    }

                                },
                                type: 'select',
                                showSearch: true,
                                editDisabled: true,
                                optionConfig: {
                                    label: 'name',
                                    value: 'iecsCBSID',
                                    linkageFields: {
                                        inOrgName: 'name'
                                    }
                                },
                                parent: 'orgID',
                                fetchConfig: {
                                    apiName: 'getZxEqIecsCBSPickingList',
                                    otherParams: {
                                        limit: 10,
                                        page: 1,
                                        cbsType: "4",
                                    },
                                    params: {
                                        orgID: 'orgID'
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
                                        regex: { age: 'old', },
                                        action: 'disabled'
                                    }
                                ]
                            },
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
                                field: 'orgName',
                                hide: true
                            }
                        },
                        {
                            table: {
                                title: '物资员',
                                width: 100,
                                dataIndex: 'consignee',
                                key: 'consignee',
                                filter: true,

                            },
                            form: {
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
                                },
                                initialValue: (obj) => {
                                    if (obj.clickCb && obj.clickCb.rowInfo) {
                                        if (obj.clickCb.rowInfo.name === 'detail') {
                                            return obj.clickCb.rowData.consignee
                                        } else if (obj.clickCb.rowInfo.name === 'edit') {
                                            return obj.clickCb.selectedRows[0].consignee
                                        } else if (obj.clickCb.rowInfo.name === 'diy') {
                                            return obj.clickCb.selectedRows[0].consignee
                                        } else {
                                            return ''
                                        }
                                    } else {
                                        return ''
                                    }

                                },
                                condition: [
                                    {
                                        regex: { can: 'old', },
                                        action: 'disabled'
                                    }
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
                                initialValue: (obj) => {
                                    if (obj.clickCb && obj.clickCb.rowInfo) {
                                        if (obj.clickCb.rowInfo.name === 'detail') {
                                            return obj.clickCb.rowData.remarks
                                        } else if (obj.clickCb.rowInfo.name === 'edit') {
                                            return obj.clickCb.selectedRows[0].remarks
                                        } else if (obj.clickCb.rowInfo.name === 'diy') {
                                            return obj.clickCb.selectedRows[0].remarks
                                        } else {
                                            return ''
                                        }
                                    } else {
                                        return ''
                                    }

                                },
                                condition: [
                                    {
                                        regex: { can: 'old', },
                                        action: 'disabled'
                                    }
                                ]
                            }
                        },

                        {
                            isInTable: false,
                            form: {
                                type: 'qnnTable',
                                field: 'zxSkTurnoverOutItemList',
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
                                                tooltip: 80,
                                                dataIndex: 'resCode',
                                                key: 'resCode',
                                                tdEdit: true
                                            },
                                            form: {
                                                type: 'selectByQnnTable',
                                                label: '',
                                                field: 'resCode',
                                                dropdownMatchSelectWidth: 900,
                                                optionConfig: {
                                                    label: 'resCode',
                                                    value: 'id'
                                                },
                                                // disabled: (obj) => {
                                                //     if (obj?.btnCallbackFn?.props?.qnnFormProps?.qnnformData?.qnnFormProps?.clickCb?.rowInfo?.name === 'diy' || obj?.btnCallbackFn?.props?.qnnFormProps?.qnnformData?.qnnFormProps?.clickCb?.rowInfo?.name === 'detail') {
                                                //         return true
                                                //     } else {
                                                //         return false
                                                //     }
                                                // },
                                                qnnTableConfig: {
                                                    antd: {
                                                        rowKey: "id"
                                                    },
                                                    firstRowIsSearch: false,
                                                    fetchConfig: {
                                                        apiName: "getZxSkTurnoverOutResourceList",
                                                        otherParams: async () => {
                                                            const vals = await this.table.btnCallbackFn.qnnForm.getValues(false);
                                                            return {
                                                                orgID: vals.orgID,
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
                                                                dataIndex: "originalAmt",
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
                                                            resCode: itemData.itemData[0].resCode,
                                                            resName: itemData.itemData[0].resName,
                                                            resUnit: itemData.itemData[0].resUnit,
                                                            spec: itemData.itemData[0].spec,
                                                            resID: itemData.itemData[0].id,
                                                            stockQty: itemData.itemData[0].stockQty,
                                                            inBusDate: itemData.itemData[0].inBusDate,
                                                            remainAmt: itemData.itemData[0].remainAmt,
                                                            originalAmt: itemData.itemData[0].originalAmt,
                                                            batchNo: itemData.itemData[0].batchNo,
                                                            outQty: 0,
                                                            hasReturnQty: 0,
                                                            // oldhasReturnQty: 0
                                                        });
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
                                                                originalAmt: 0,
                                                                remainAmt: 0,
                                                                outQty: 0,
                                                                hasReturnQty: 0,
                                                                // oldhasReturnQty: 0
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
                                            isInTable: false,
                                            form: {
                                                field: 'batchNo',
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
                                            },
                                            form: {
                                                type: 'string',
                                                field: 'resUnit'
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
                                                title: '库存数量',
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
                                                title: '原值',
                                                dataIndex: 'originalAmt',
                                                key: 'originalAmt',
                                                width: 80,
                                            },
                                            form: {
                                                type: 'number',
                                                field: 'originalAmt',
                                                precision: 2
                                            }
                                        },
                                        {
                                            table: {
                                                title: '净值',
                                                dataIndex: 'remainAmt',
                                                key: 'remainAmt',
                                                width: 80,
                                            },
                                            form: {
                                                type: 'number',
                                                field: 'remainAmt',
                                                precision: 2
                                            }
                                        },
                                        {
                                            table: {
                                                title: '出库数量',
                                                width: 100,
                                                dataIndex: 'outQty',
                                                key: 'outQty',
                                                tdEdit: true
                                            },
                                            form: {
                                                type: 'number',
                                                field: 'outQty',
                                                precision: 3,
                                                disabled: (obj) => {
                                                    if (obj?.btnCallbackFn?.props?.qnnFormProps?.qnnformData?.qnnFormProps?.clickCb?.rowInfo?.name === 'diy' || obj?.btnCallbackFn?.props?.qnnFormProps?.qnnformData?.qnnFormProps?.clickCb?.rowInfo?.name === 'detail') {
                                                        return true
                                                    } else {
                                                        return false
                                                    }
                                                },
                                                onChange: async (colVal, thisProps, tableBtnCallbackFn) => {
                                                    if (colVal) {
                                                        clearTimeout(this.tdEditedTimer);
                                                        this.tdEditedTimer = setTimeout(async () => {
                                                            const rowData = await tableBtnCallbackFn.getEditedRowData(false);
                                                            const newRowData = {
                                                                ...rowData
                                                            }
                                                            if (colVal > rowData.stockQty) {
                                                                Msg.warn('超出库存数量！');
                                                                newRowData.outQty = rowData.stockQty;
                                                            } else {
                                                                newRowData.outQty = colVal;
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
                                                title: '已归还数量',
                                                width: 120,
                                                dataIndex: 'hasReturnQty',
                                                key: 'hasReturnQty',
                                                // tdEdit: drawerName === 'diy' ? true : false,
                                                tdEdit: true
                                            },
                                            form: {
                                                type: 'number',
                                                field: 'hasReturnQty',
                                                precision: 3,
                                                disabled: (obj) => {
                                                    if (obj?.btnCallbackFn?.props?.qnnFormProps?.qnnformData?.qnnFormProps?.clickCb?.rowInfo?.name === 'diy') {
                                                        return false
                                                    } else {
                                                        return true
                                                    }
                                                },
                                                onChange: async (colVal, thisProps, tableBtnCallbackFn) => {
                                                    if (colVal) {
                                                        clearTimeout(this.tdEditedTimer);
                                                        this.tdEditedTimer = setTimeout(async () => {
                                                            const rowData = await tableBtnCallbackFn.getEditedRowData(false);
                                                            const newRowData = {
                                                                ...rowData
                                                            }
                                                            if (colVal > rowData.outQty) {
                                                                Msg.warn('超出库存数量！');
                                                                newRowData.hasReturnQty = rowData.outQty;
                                                            } else {
                                                                // 上次归还赋值给新字段
                                                                if (rowData.hasReturnQty) {
                                                                    // newRowData.oldhasReturnQty = rowData.hasReturnQty;
                                                                }
                                                            }
                                                            await tableBtnCallbackFn.setEditedRowData({
                                                                ...newRowData
                                                            });
                                                        }, 600)
                                                    }
                                                },
                                            }
                                        },

                                        {
                                            isInTable: false,
                                            form: {
                                                type: 'number',
                                                field: 'oldhasReturnQty',
                                            }
                                        }
                                    ],
                                    actionBtns: [
                                        {
                                            name: "addRow",
                                            icon: "plus",
                                            type: "primary",
                                            label: "新增",
                                            hide: (obj) => {
                                                if (obj?.btnCallbackFn?.props?.qnnFormProps?.qnnformData?.qnnFormProps?.clickCb?.rowInfo?.name === 'edit' || obj?.btnCallbackFn?.props?.qnnFormProps?.qnnformData?.qnnFormProps?.clickCb?.rowInfo?.name === 'add') {
                                                    return false
                                                } else {
                                                    return true
                                                }
                                            },
                                        },
                                        {
                                            name: "del",
                                            icon: 'delete',
                                            type: 'danger',
                                            label: "删除",
                                            hide: (obj) => {
                                                if (obj?.btnCallbackFn?.props?.qnnFormProps?.qnnformData?.qnnFormProps?.clickCb?.rowInfo?.name === 'edit' || obj?.btnCallbackFn?.props?.qnnFormProps?.qnnformData?.qnnFormProps?.clickCb?.rowInfo?.name === 'add') {
                                                    return false
                                                } else {
                                                    return true
                                                }
                                            },
                                        }
                                    ]
                                }
                            }
                        },
                        {
                            table: {
                                title: '单据状态',
                                dataIndex: 'status',
                                key: 'status',
                                fixed: 'right',
                                width: 100,
                                render: (data) => {
                                    if (data === '0') {
                                        return '未审核';
                                    } else {
                                        return '已审核';
                                    }
                                }
                            },
                            form: {
                                type: 'string',
                                field: 'status',
                                hide: true
                            }
                        },
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
                                    field: 'ccddsField',
                                },
                                {
                                    name: 'submit',
                                    type: 'primary',
                                    label: '保存',
                                    field: 'addsubmit',
                                    fetchConfig: {
                                        apiName: 'addZxSkTurnoverOut'
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
                                    field: 'vfgsField',
                                },
                                {
                                    name: 'submit',
                                    type: 'primary',
                                    label: '保存',
                                    field: 'editField',
                                    fetchConfig: {
                                        apiName: 'updateZxSkTurnoverOut'
                                    }
                                }
                            ]
                        },
                        // {
                        //     drawerTitle: "归还",
                        //     name: 'Component',
                        //     type: 'primary',
                        //     label: '归还',
                        //     disabled: "bind:_actionBtnNoSelected",
                        //     Component: (obj) => {
                        //         if (obj.selectedRows[0].status === "0") {
                        //             obj.btnCallbackFn.closeDrawer();
                        //             obj.btnCallbackFn.msg.error('未审核的不能归还!');
                        //             this.table.clearSelectedRows();
                        //             return <div />
                        //         }
                        //         if (obj.selectedRows.length != 1) {
                        //             obj.btnCallbackFn.closeDrawer();
                        //             obj.btnCallbackFn.msg.error('请选择一条数据！');
                        //             this.table.clearSelectedRows();
                        //             return <div />
                        //         }
                        //         return <Guihuan {...obj} id={obj.selectedRows[0].id} />
                        //     }
                        // },
                        {
                            name: 'diy',
                            type: 'primary',
                            label: '归还',
                            disabled: "bind:_actionBtnNoSelected",
                            drawerTitile: '归还',
                            onClick: (obj) => {
                                if (obj.selectedRows.length === 1) {
                                    if (obj.selectedRows[0].status === '0') {
                                        obj.btnCallbackFn.closeDrawer();
                                        Msg.warn('未审核的不能归还!');
                                    } else {
                                        setTimeout(() => {
                                            this.table.closeDrawer(true, () => {
                                                if (this.table?.qnnForm?.form) {
                                                    this.table.qnnForm.form.setFieldsValue({
                                                        zxSkTurnoverOutItemList: obj.selectedRows[0].zxSkTurnoverOutItemList
                                                    });
                                                    this.table.setDrawerBtns([
                                                        {
                                                            name: 'cancel',
                                                            type: 'dashed',
                                                            label: '取消',
                                                            field: 'cancelField',
                                                            onClick: () => {
                                                                this.table.closeDrawer(true);
                                                            }
                                                        },
                                                        {
                                                            name: 'diysubmit',
                                                            type: 'primary',
                                                            label: '保存',
                                                            field: 'guihuanField',
                                                            onClick: (val) => {
                                                                const { myFetch } = this.props;
                                                                val._formData.status = obj.selectedRows[0].status;
                                                                myFetch('returnZxSkTurnoverOut', val._formData).then(
                                                                    ({ success, message }) => {
                                                                        if (success) {
                                                                            Msg.success(message);
                                                                            this.table.refresh();
                                                                            this.table.closeDrawer(false);
                                                                        } else {
                                                                            Msg.error(message);
                                                                            this.table.closeDrawer(false);
                                                                        }
                                                                    }
                                                                );
                                                            }
                                                        }
                                                    ])
                                                }
                                            });
                                        }, 200)
                                    }
                                } else {
                                    Msg.warn('只能归还一条数据！')
                                }
                                this.table.clearSelectedRows();
                            }
                        },
                        {
                            name: 'diyCheck',
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
                                        obj.selectedRows[0].companyID = '1';
                                        confirm({
                                            content: '确定审核选中的数据吗?',
                                            onOk: () => {
                                                myFetch('checkZxSkTurnoverOut', { id: obj.selectedRows[0].id }).then(
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
                            name: 'diyEdit',
                            type: 'primary',
                            label: '修改业务日期',
                            disabled: "bind:_actionBtnNoSelected",
                            onClick: (obj) => {
                                if (obj.selectedRows.length === 1) {
                                    if (obj.selectedRows[0].status === '1') {
                                        this.setState({
                                            visibleSendData: true,
                                            loadingSendData: false,
                                            selectedRowsData: obj.selectedRows[0]
                                        })
                                    } else {
                                        Msg.warn('请选择已审核的数据！');
                                    }
                                } else {
                                    Msg.warn('请选择一条数据！');
                                }
                            }
                        },
                        {
                            name: 'diyImport',
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
                                            myFetch('batchDeleteUpdateZxSkTurnoverOut', obj.selectedRows).then(
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
                    title="修改业务日期"
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
                                    label: "业务日期",
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
                                    onChange: (val, rowData) => {
                                        if (val) {
                                            this.dateChange(val, 2);
                                        }
                                    },
                                }
                            ]}
                            btns={[
                                {
                                    name: 'cancel',
                                    type: 'dashed',
                                    label: '取消',
                                    field: 'ccaField',
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
                                    field: 'ywrq',
                                    onClick: (obj) => {
                                        this.setState({
                                            loadingSendData: true
                                        })
                                        let values = selectedRowsData;
                                        values.busDate = obj.values.busDate;

                                        this.props.myFetch('updateZxSkTurnoverOut', values).then(
                                            ({ success, message }) => {
                                                if (success) {
                                                    Msg.success(message);
                                                    this.table.refresh();
                                                    this.table.clearSelectedRows();
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