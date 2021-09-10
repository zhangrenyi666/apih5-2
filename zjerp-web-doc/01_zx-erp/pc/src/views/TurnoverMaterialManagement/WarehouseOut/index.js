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
            selectedRowsData: {},
            lockProjectId: props.loginAndLogoutInfo.loginInfo.userInfo.lockProject && props.loginAndLogoutInfo.loginInfo.userInfo.lockProject.projectId ? props.loginAndLogoutInfo.loginInfo.userInfo.lockProject.projectId : '',
            visible: false,
            loading: false,
            idPreview: null,
            billNo: null
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
        this.setState({ visible: false,loading:false });
    }
    render() {
        const { visibleSendData, loadingSendData, selectedRowsData, lockProjectId, loading, idPreview, billNo } = this.state;
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
                        apiName: 'getZxSkTurnoverOutList',
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
                                required:true,
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
                                label: 'can',
                                type: 'string',
                                field: 'can',
                                hide: true,
                                initialValue: (obj) => {
                                    if (obj.clickCb.rowInfo.name === 'detail' || obj.clickCb.rowInfo.name === '_diy') {
                                        return 'old'
                                    } else {
                                        return 'new'
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
                                key: 'busDate',
                                filter: true,
                                fieldsConfig: {
                                    type: 'rangeDate',
                                    field: 'timeList'
                                },
                                onClick: 'detail',
                                render: (data, rowData) => {
                                    return data ? moment(data).format('YYYY-MM-DD') : null
                                }
                            },
                            form: {
                                type: 'date',
                                required: true,
                                field: 'busDate',
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
                                type: 'select',
                                editDisabled: true,
                                optionConfig: {
                                    label: 'name',
                                    value: 'iecsCBSID',
                                    linkageFields: {
                                        inOrgName: 'name'
                                    }
                                },
                                // parent: 'orgID',
                                dependenciesReRender: true,//多个依赖-配置
                                dependencies: ['orgID'],
                                fetchConfig: {
                                    apiName: 'getZxEqIecsCBSPickingList',
                                    // otherParams: {
                                    //     limit: 10,
                                    //     page: 1,
                                    //     cbsType: "4",
                                    // },
                                    // params: {
                                    //     orgID: 'orgID'
                                    // }
                                    otherParams: (val) => {
                                        let orgIDVal = '';
                                        if (val.btnCallbackFn?.form) {
                                            let aa = val.btnCallbackFn.form.getFieldsValue();
                                            if (aa.orgID) {
                                                orgIDVal = aa.orgID;
                                            }
                                        } else {

                                        }
                                        return {
                                            orgID: orgIDVal,
                                            cbsType: "4",
                                            limit: 10,
                                            page: 1,
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
                                            age: 'old'
                                        },
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
                                                dataIndex: 'resCode',
                                                key: 'resCode',
                                                tdEdit: true,
                                                fieldsConfig: {
                                                    disabled: (obj) => {
                                                        if (obj.qnnTableInstance?.props?.qnnFormProps?.funcCallBackParams?.clickCb?.rowInfo?.name === '_diy' || obj.qnnTableInstance?.props?.qnnFormProps?.funcCallBackParams?.clickCb?.rowInfo?.name === 'detail') {
                                                            return true
                                                        } else {
                                                            return false
                                                        }
                                                    },
                                                }
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
                                                            resCode: itemData.itemData.resCode,
                                                            resName: itemData.itemData.resName,
                                                            resUnit: itemData.itemData.resUnit,
                                                            spec: itemData.itemData.spec,
                                                            resID: itemData.itemData.id,
                                                            stockQty: itemData.itemData.stockQty,
                                                            inBusDate: itemData.itemData.inBusDate,
                                                            remainAmt: itemData.itemData.remainAmt,
                                                            originalAmt: itemData.itemData.originalAmt,
                                                            batchNo: itemData.itemData.batchNo,
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
                                                dataIndex: 'resName',
                                                key: 'resName',
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
                                                key: 'originalAmt'
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
                                                key: 'remainAmt'
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
                                                tdEdit: true,
                                                fieldsConfig: {
                                                    disabled: (obj) => {
                                                        if (obj.qnnTableInstance?.props?.qnnFormProps?.funcCallBackParams?.clickCb?.rowInfo?.name === '_diy' || obj.qnnTableInstance?.props?.qnnFormProps?.funcCallBackParams?.clickCb?.rowInfo?.name === 'detail') {
                                                            return true
                                                        } else {
                                                            return false
                                                        }
                                                    },
                                                }
                                            },
                                            form: {
                                                type: 'number',
                                                field: 'outQty',
                                                precision: 3,
                                                onChange: async (colVal, thisProps, tableBtnCallbackFn) => {
                                                    if (colVal >= 0 && typeof (colVal) === 'number') {
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
                                                tdEdit: true,
                                                fieldsConfig: {
                                                    disabled: (obj) => {
                                                        if (obj.qnnTableInstance?.props?.qnnFormProps?.funcCallBackParams?.clickCb?.rowInfo?.name === '_diy') {
                                                            return false
                                                        } else {
                                                            return true
                                                        }
                                                    },
                                                }
                                            },
                                            form: {
                                                type: 'number',
                                                field: 'hasReturnQty',
                                                precision: 3,
                                                onChange: async (colVal, thisProps, tableBtnCallbackFn) => {
                                                    if (colVal >= 0 && typeof (colVal) === 'number') {
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
                            } else { }
                            obj.btnCallbackFn.clearSelectedRows();
                        },
                        disabledFunGuiHuan: (obj) => {
                            let data = obj.btnCallbackFn.getSelectedRows();
                            if (data.length !== 1 || data[0].status === '0') {
                                return true;
                            } else {
                                return false;
                            }
                        },
                        onClickFunGuiHuan: (obj) => {
                            if (obj.selectedRows.length === 1) {
                                if (obj.selectedRows[0].status === '0') {
                                    obj.btnCallbackFn.closeDrawer();
                                    Msg.warn('未审核的不能归还!');
                                } else {
                                    // setTimeout(() => {
                                    this.table.closeDrawer(true, () => {
                                        if (this.table?.qnnForm?.form) {
                                            this.table.qnnForm.form.setFieldsValue({
                                                busDate: obj.selectedRows[0].busDate,
                                                billNo: obj.selectedRows[0].billNo,
                                                orgID: obj.selectedRows[0].orgID,
                                                inOrgID: obj.selectedRows[0].inOrgID,
                                                remarks: obj.selectedRows[0].remarks,
                                                consignee: obj.selectedRows[0].consignee,
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
                                    // }, 200)
                                }
                            } else {
                                Msg.warn('只能归还一条数据！')
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
                                    obj.btnCallbackFn.clearSelectedRows();
                                    Msg.warn('已审核的不能审核!');
                                } else {
                                    obj.selectedRows[0].companyID = '1';
                                    confirm({
                                        content: '确定审核选中的数据吗?',
                                        onOk: () => {
                                            myFetch('checkZxSkTurnoverOut', { id: obj.selectedRows[0].id }).then(
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
                                        myFetch('batchDeleteUpdateZxSkTurnoverOut', obj.selectedRows).then(
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
                                billNo: obj.selectedRows[0].billNo
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
                    //                 field: 'ccddsField',
                    //             },
                    //             {
                    //                 name: 'submit',
                    //                 type: 'primary',
                    //                 label: '保存',
                    //                 field: 'addsubmit',
                    //                 fetchConfig: {
                    //                     apiName: 'addZxSkTurnoverOut'
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
                    //                 field: 'vfgsField',
                    //             },
                    //             {
                    //                 name: 'submit',
                    //                 type: 'primary',
                    //                 label: '保存',
                    //                 field: 'editField',
                    //                 fetchConfig: {
                    //                     apiName: 'updateZxSkTurnoverOut'
                    //                 }
                    //             }
                    //         ]
                    //     },
                    //     {
                    //         name: 'diy',
                    //         type: 'primary',
                    //         label: '归还',
                    //         disabled: 'bind:disabledFunGuiHuan',
                    //         drawerTitile: '归还',
                    //         onClick: 'bind:onClickFunGuiHuan'
                    //     },
                    //     {
                    //         name: 'diyCheck',
                    //         type: 'primary',
                    //         label: '审核',
                    //         disabled: 'bind:disabledFunShenPi',
                    //         onClick: 'bind:onClickFunShenPi'
                    //     },
                    //     {
                    //         name: 'diyEdit',
                    //         type: 'primary',
                    //         label: '修改业务日期',
                    //         disabled: 'bind:disabledFunEditDate',
                    //         onClick: 'bind:onClickFunEditDate'
                    //     },
                    //     {
                    //         name: 'diyImport',
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
                                tableField: "WarehouseOut"
                            }
                        }
                    }}
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
                                this.setState({ loading: false });
                            }} src={`${ureport}preview?_u=minio:ZxSkTurnoverOut.preview.xml&_t=1,6&_n=${billNo}&access_token=${access_token}&id=${idPreview}
                            `}title="byLm"></iframe>
                        </Spin> : ''
                    }

                </Modal>
            </div>
        );
    }
}

export default index;