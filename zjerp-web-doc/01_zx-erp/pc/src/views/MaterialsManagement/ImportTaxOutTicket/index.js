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
    // 单据编号
    getDjbhFun(inOrgID, busDate) {
        const { myFetch } = this.props;
        myFetch('getZxSkStockTransferSalesReturnNo', {
            inOrgID: inOrgID,
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
        this.setState({ visibleSendData: false, loadingSendData: false });
    }
    handleCancel = () => {
        this.setState({ visible: false, loading: false });
    }
    //日期控制
    dateChange(val) {
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
                this.table.qnnForm.form.setFieldsValue({
                    busDate: '',
                    billNo: null
                })
            }
        } else {//当前日期大于等于26号
            if (time1 < time3) {
                Msg.warn('不能补录本月26号零点前的数据！');
                this.table.qnnForm.form.setFieldsValue({
                    busDate: '',
                    billNo: null
                })
            }
        }
        let inOrgID = this.table.qnnForm.form.getFieldsValue().inOrgID;
        let busDate = this.table.qnnForm.form.getFieldsValue().busDate;
        if (inOrgID) {
            this.getDjbhFun(inOrgID, busDate);
        }
    }
    render() {
        const { visibleSendData, loadingSendData, selectedRowsData, lockProjectId, loading, idPreview, name } = this.state;
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
                    // desc='本页面所有金额单位为：元'
                    {...config}
                    fetchConfig={{
                        apiName: 'getZxSkStockTransferSalesReturnList',
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
                                title: '单据编号',
                                onClick: 'detail',
                                dataIndex: 'billNo',
                                key: 'billNo',
                                width: 300,
                                filter: true,
                                render: (data, rowData) => {
                                    let color = '';
                                    if (rowData.billStatus === '1') {
                                        color = '';
                                    } else if (rowData.billStatus === '0') {
                                        color = 'red';
                                    }
                                    return <a style={{ color: color }}>{data}</a>
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
                            isInTable: false,
                            form: {
                                field: 'companyId',
                                hide: true,
                                type: 'string'
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                field: 'companyName',
                                hide: true,
                                type: 'string'
                            }
                        },
                        {
                            table: {
                                title: '收料单位',
                                dataIndex: 'inOrgID',
                                key: 'inOrgID',
                                width: 200,
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
                                field: 'inOrgID',
                                required: true,
                                type: 'select',
                                allowClear: false,
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
                                        //2021.06.21 张启明修
                                        // contrStatus: "1",
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
                                children: [
                                    {
                                        field: 'whOrgID'
                                    }
                                ],
                                onChange: (val, rowData) => {
                                    if (this.table && this.table.qnnForm) {
                                        this.table.qnnForm.clearValues(['zxSkStockTransItemSalesReturnList']);
                                        this.table.qnnForm.form.setFieldsValue({
                                            cantEdit: 'can',
                                            inOrgName: rowData.itemData.orgName,
                                            whOrgID: '',
                                            outOrgID: '',
                                            resourceID: ''
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
                                title: '仓库',
                                dataIndex: 'warehouseName',
                                key: 'warehouseName',
                                width: 160,
                                filter: true,
                                fieldsConfig: {
                                    field: 'whOrgID',
                                    type: 'select',
                                    parent: null,
                                    optionConfig: {
                                        label: 'warehouseName',
                                        value: 'id',
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
                                editDisabled: true,
                                allowClear: false,
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
                                        this.table.qnnForm.clearValues(['zxSkStockTransItemSalesReturnList']);
                                        this.table.qnnForm.form.setFieldsValue({
                                            outOrgID: '',
                                            resourceID: ''
                                        });
                                        this.table.qnnForm.form.setFieldsValue({
                                            cantEdit: 'can',
                                            inOrgName: rowData.itemData.orgName,
                                            // whOrgID: '',
                                            outOrgID: '',
                                            resourceID: ''
                                        })
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
                                title: '退货单位',
                                dataIndex: 'outOrgName',
                                key: 'outOrgName',
                                width: 200,
                                filter: true,
                                fieldsConfig: {
                                    field: 'outOrgName',
                                    type: 'string'
                                }
                            },
                            form: {
                                field: 'outOrgID',
                                required: true,
                                type: 'select',
                                editDisabled: true,
                                allowClear: false,
                                showSearch: true,
                                optionConfig: {
                                    label: 'outOrgName',
                                    value: 'outOrgID'
                                },
                                dependenciesReRender: true,//多个依赖-配置
                                dependencies: ['whOrgID', 'inOrgID'],
                                fetchConfig: {
                                    apiName: 'getZxSkStockTransferSalesReturnOutOrgName',
                                    otherParams: (val) => {
                                        let whOrgIDVal = '';
                                        let inOrgIDVal = '';
                                        if (val.btnCallbackFn?.form) {
                                            let aa = val.btnCallbackFn.form.getFieldsValue();
                                            whOrgIDVal = aa.whOrgID;
                                            inOrgIDVal = aa.inOrgID;
                                        } else {
                                            whOrgIDVal = '';
                                            inOrgIDVal = '';
                                        }
                                        return {
                                            whOrgID: whOrgIDVal,
                                            inOrgID: inOrgIDVal
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
                                onChange: (val, rowData) => {
                                    if (this.table && this.table.qnnForm) {
                                        this.table.qnnForm.clearValues(['zxSkStockTransItemSalesReturnList']);
                                        this.table.qnnForm.form.setFieldsValue({
                                            outOrgName: rowData.itemData.outOrgName
                                        });
                                    }
                                }
                            },
                        },
                        {
                            table: {
                                title: '单据金额',
                                width: 120,
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
                                title: '物资类别',
                                dataIndex: 'resourceName',
                                key: 'resourceName',
                                width: 160,
                                filter: true,
                                fieldsConfig: {
                                    field: 'resourceID',
                                    type: 'select',
                                    optionConfig: {
                                        label: 'catName',
                                        value: 'id',
                                    },
                                    fetchConfig: {
                                        apiName: 'getZxSkResCategoryMaterialsListResourceNotRevolve',
                                        otherParams: {
                                            parentOrgID: "1"
                                        }
                                    },
                                }
                            },
                            form: {
                                field: 'resourceID',
                                required: true,
                                allowClear: false,
                                type: 'select',
                                editDisabled: true,
                                showSearch: true,
                                optionConfig: {
                                    label: 'resourceName',
                                    value: 'resourceID',
                                    linkageFields: {
                                        "resourceName": 'resourceName'
                                    }
                                },
                                dependenciesReRender: true,//多个依赖-配置
                                dependencies: ['whOrgID', 'inOrgID', 'outOrgID'],
                                fetchConfig: {
                                    apiName: 'getZxSkStockTransferSalesReturnResourceName',
                                    otherParams: (val) => {
                                        let whOrgIDVal = '';
                                        let inOrgIDVal = '';
                                        let outOrgIDVal = '';
                                        if (val.btnCallbackFn?.form) {
                                            let aa = val.btnCallbackFn.form.getFieldsValue();
                                            whOrgIDVal = aa.whOrgID;
                                            inOrgIDVal = aa.inOrgID;
                                            outOrgIDVal = aa.outOrgID;
                                        } else {
                                            whOrgIDVal = '';
                                            inOrgIDVal = '';
                                            outOrgIDVal = '';
                                        }
                                        return {
                                            whOrgID: whOrgIDVal,
                                            inOrgID: inOrgIDVal,
                                            outOrgID: outOrgIDVal
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
                                onChange: (val) => {
                                    if (this.table && this.table.qnnForm) {
                                        this.table.qnnForm.clearValues(['zxSkStockTransItemSalesReturnList']);
                                        this.table.qnnForm.form.setFieldsValue({
                                            cantEdit: 'can'
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
                                title: '物资来源',
                                width: 120,
                                dataIndex: 'materialSource',
                                key: 'materialSource',
                                type: 'select'
                            },
                            form: {
                                type: 'select',
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
                                title: '凭证号',
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
                                title: '业务日期',
                                width: 120,
                                dataIndex: 'busDate',
                                format: 'YYYY-MM-DD',
                                key: 'busDate',
                                filter: true
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
                                            this.dateChange(val);
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
                                field: 'isDepreciation',
                                type: 'string',
                                initialValue: '0',
                                hide: true
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                field: 'precollecte',
                                type: 'string',
                                initialValue: '1',
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
                            form: {
                                type: 'qnnTable',
                                field: 'zxSkStockTransItemSalesReturnList',
                                incToForm: true,
                                qnnTableConfig: {
                                    antd: {
                                        rowKey: 'zxSkStockTransItemSalesReturnId',
                                        size: 'small'
                                    },
                                    ...configItem,
                                    formConfig: [
                                        {
                                            isInTable: false,
                                            form: {
                                                label: '主键id',
                                                field: 'zxSkStockTransItemSalesReturnId',
                                                hide: true
                                            }
                                        },
                                        {
                                            table: {
                                                title: '物资编码',
                                                width: 200,
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
                                                    value: 'mainIdAndItemId'
                                                },
                                                qnnTableConfig: {
                                                    antd: {
                                                        rowKey: "mainIdAndItemId"
                                                    },
                                                    firstRowIsSearch: false,
                                                    fetchConfig: {
                                                        apiName: "getZxSkStockTransferSalesReturnResName",//???
                                                        otherParams: async (val) => {
                                                            const vals = await this.table.btnCallbackFn.qnnForm.getValues(false);
                                                            const action = this.table.qnnForm.props.clickCb.rowInfo.name;
                                                            return {
                                                                resourceID: vals.resourceID,
                                                                limit: 999,
                                                                page: 1,
                                                                whOrgID: vals.whOrgID,
                                                                outOrgID: vals.outOrgID,
                                                                companyID: companyId,
                                                                inOrgID: vals.inOrgID,
                                                                inAmt: vals.inAmt,
                                                                updateType: action === 'edit' ? '1' : '0',
                                                                id: action === 'add' ? 0 : vals.id//???

                                                            }
                                                        }
                                                    },
                                                    searchBtnsStyle: "inline",
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
                                                            isInForm: false,
                                                            isInSearch: true,
                                                            table: {
                                                                dataIndex: "resCode",
                                                                title: "物资编号",
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
                                                            table: {
                                                                dataIndex: "isOutNumber",
                                                                title: "可退数量",
                                                            },
                                                            form: {
                                                                type: "number"
                                                            }
                                                        },
                                                        {
                                                            isInForm: false,
                                                            table: {
                                                                dataIndex: "isOutNumberStock",
                                                                title: "仓库数量",
                                                            },
                                                            form: {
                                                                type: "number"
                                                            }
                                                        },
                                                        {
                                                            isInForm: false,
                                                            table: {
                                                                dataIndex: "billNo",
                                                                width: 240,
                                                                title: "单据编号",
                                                            },
                                                            form: {
                                                                type: "string"
                                                            }
                                                        },
                                                        {
                                                            isInForm: false,
                                                            table: {
                                                                dataIndex: "inPrice",
                                                                title: "入库单价",
                                                            },
                                                            form: {
                                                                type: "number"
                                                            }
                                                        }
                                                    ]
                                                },
                                                onChange: (colVal, thisProps, tableBtnCallbackFn) => {
                                                    if (colVal) {
                                                        let alltol = 0;
                                                        let alltol1 = 0;
                                                        let alltol2 = 0;
                                                        let alltol3 = 0;
                                                        let alltol4 = 0;
                                                        let shuilv = 0;
                                                        if (this.table.qnnForm.form.getFieldsValue().taxRate) {
                                                            shuilv = Number((this.table.qnnForm.form.getFieldsValue().taxRate) * 0.01);
                                                        }
                                                        let inQtyBei = thisProps.itemData.inQty;
                                                        inQtyBei = (thisProps.itemData.isOutNumber > thisProps.itemData.isOutNumberStock) ? thisProps.itemData.isOutNumberStock : thisProps.itemData.isOutNumber;
                                                        // 下拉的计算值都要加上---数量最大值，库存
                                                        if (inQtyBei && thisProps.itemData.inPrice) {
                                                            alltol = ((this.FloatMulTwo(inQtyBei, thisProps.itemData.inPrice)) / Number(1 + shuilv)).toFixed(2);//不含税金额
                                                            alltol1 = this.FloatMulTwo(inQtyBei, thisProps.itemData.inPrice).toFixed(2);//含税金额
                                                        }
                                                        if (thisProps.itemData.inPrice) {
                                                            alltol2 = (thisProps.itemData.inPrice / Number(1 + shuilv)).toFixed(2);//不含税单价
                                                        }
                                                        if (this.table.qnnForm.form.getFieldsValue().isDeduct === '0') {//参与抵扣-0否   1是
                                                            alltol4 = alltol1;//退账-金额
                                                        } else {
                                                            alltol4 = alltol;//退账-金额
                                                        }
                                                        if (alltol1 && alltol) {
                                                            alltol3 = (alltol1 - alltol).toFixed(2);//税额
                                                        }
                                                        tableBtnCallbackFn.setEditedRowData({
                                                            resName: thisProps.itemData.resName,
                                                            resUnit: thisProps.itemData.resUnit,
                                                            spec: thisProps.itemData.spec,
                                                            inPrice: thisProps.itemData.inPrice,
                                                            resID: thisProps.itemData.resID,
                                                            id: thisProps.itemData.id,
                                                            resCode: thisProps.itemData.resCode,
                                                            inQty: inQtyBei,
                                                            isOutNumber: thisProps.itemData.isOutNumber,
                                                            isOutNumberStock: thisProps.itemData.isOutNumberStock,
                                                            resAllFeeNoTax: alltol,
                                                            resAllFee: alltol1,
                                                            inPriceNoTax: alltol2,
                                                            resAllFeeTax: alltol3,
                                                            inAmt: alltol4,
                                                            mainIdAndItemId: thisProps.itemData.mainIdAndItemId
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
                                                                inQty: 0,
                                                                inPriceNoTax: 0,
                                                                resAllFeeNoTax: 0,
                                                                resAllFeeTax: 0,
                                                                inPrice: 0,
                                                                resAllFee: 0,
                                                                inFee: 0,
                                                                ysFee: 0,
                                                                inAmt: 0
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
                                                field: 'isOutNumber',
                                                hide: true
                                            }
                                        },
                                        {
                                            isInTable: false,
                                            form: {
                                                field: 'isOutNumberStock',
                                                hide: true
                                            }
                                        },
                                        {
                                            isInTable: false,
                                            form: {
                                                field: 'mainIdAndItemId',
                                                hide: true
                                            }
                                        },

                                        {
                                            isInTable: false,
                                            form: {
                                                field: 'resCode',
                                                hide: true
                                            }
                                        },
                                        {
                                            table: {
                                                title: '物资名称',
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
                                                title: '物资规格',
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
                                                title: '退货数量',
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
                                                        // 获取编辑行的值的 标准方案
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

                                                        if ((colVal > rowData.isOutNumber) || (colVal > rowData.isOutNumberStock)) {
                                                            if (colVal > rowData.isOutNumber) {
                                                                Msg.warn('超出单据可退数量！');
                                                            }
                                                            if (colVal > rowData.isOutNumberStock) {
                                                                Msg.warn('超出库存数量！');
                                                            }
                                                            newRowData.inQty = (rowData.isOutNumber > rowData.isOutNumberStock) ? rowData.isOutNumberStock : rowData.isOutNumber;
                                                            if (rowData.inPrice) {
                                                                newRowData.resAllFeeNoTax = ((this.FloatMulTwo(newRowData.inQty, rowData.inPrice)) / Number(1 + shuilv)).toFixed(2);//不含税金额
                                                                newRowData.resAllFee = this.FloatMulTwo(newRowData.inQty, rowData.inPrice).toFixed(2);//含税金额
                                                                if (this.table.qnnForm.form.getFieldsValue().isDeduct === '0') {//参与抵扣-0否   1是
                                                                    newRowData.inAmt = newRowData.resAllFee;//退账-金额
                                                                } else {
                                                                    newRowData.inAmt = newRowData.resAllFeeNoTax;//退账-金额
                                                                }
                                                                if (newRowData.resAllFee && newRowData.resAllFeeNoTax) {
                                                                    newRowData.resAllFeeTax = (newRowData.resAllFee - newRowData.resAllFeeNoTax).toFixed(2);//税额
                                                                }
                                                            }
                                                        } else {
                                                            if (rowData.inPrice) {
                                                                newRowData.resAllFeeNoTax = ((this.FloatMulTwo(colVal, rowData.inPrice)) / Number(1 + shuilv)).toFixed(2);//不含税金额
                                                                newRowData.resAllFee = this.FloatMulTwo(colVal, rowData.inPrice).toFixed(2);//含税金额
                                                                if (this.table.qnnForm.form.getFieldsValue().isDeduct === '0') {//参与抵扣-0否   1是
                                                                    newRowData.inAmt = newRowData.resAllFee;//退账-金额
                                                                } else {
                                                                    newRowData.inAmt = newRowData.resAllFeeNoTax;//退账-金额
                                                                }
                                                                if (newRowData.resAllFee && newRowData.resAllFeeNoTax) {
                                                                    newRowData.resAllFeeTax = (newRowData.resAllFee - newRowData.resAllFeeNoTax).toFixed(2);//税额
                                                                }
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
                                                title: '不含税单价',
                                                width: 120,
                                                dataIndex: 'inPriceNoTax',
                                                key: 'inPriceNoTax'
                                            },
                                            form: {
                                                type: 'number',
                                                field: 'inPriceNoTax',
                                                precision: 6
                                            }
                                        },
                                        {
                                            table: {
                                                title: '不含税金额',
                                                width: 120,
                                                dataIndex: 'resAllFeeNoTax',
                                                key: 'resAllFeeNoTax'
                                            },
                                            form: {
                                                type: 'number',
                                                field: 'resAllFeeNoTax',
                                                precision: 2
                                            }
                                        },
                                        {
                                            table: {
                                                title: '税额',
                                                width: 120,
                                                dataIndex: 'resAllFeeTax',
                                                key: 'resAllFeeTax'
                                            },
                                            form: {
                                                type: 'number',
                                                field: 'resAllFeeTax',
                                                precision: 2
                                            }
                                        },
                                        {
                                            table: {
                                                title: '含税单价',
                                                width: 120,
                                                dataIndex: 'inPrice',
                                                key: 'inPrice',
                                                tdEdit: true
                                            },
                                            form: {
                                                type: 'number',
                                                field: 'inPrice',
                                                precision: 6,
                                                onChange: async (colVal, thisProps, tableBtnCallbackFn) => {
                                                    if (colVal >= 0 && typeof (colVal) === 'number') {
                                                        // 获取编辑行的值的 标准方案
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
                                                        // 不含税单价计算
                                                        newRowData.inPriceNoTax = Number((colVal / Number(1 + shuilv)).toFixed(6));
                                                        if (rowData.inQty) {
                                                            newRowData.resAllFeeNoTax = ((this.FloatMulTwo(colVal, rowData.inQty)) / Number(1 + shuilv)).toFixed(2);//不含税金额
                                                            newRowData.resAllFee = this.FloatMulTwo(colVal, rowData.inQty).toFixed(2);//含税金额
                                                            if (this.table.qnnForm.form.getFieldsValue().isDeduct === '0') {//参与抵扣-0否   1是
                                                                newRowData.inAmt = newRowData.resAllFee;//退账-金额
                                                            } else {
                                                                newRowData.inAmt = newRowData.resAllFeeNoTax;//退账-金额
                                                            }
                                                            if (newRowData.resAllFee && newRowData.resAllFeeNoTax) {
                                                                newRowData.resAllFeeTax = (newRowData.resAllFee - newRowData.resAllFeeNoTax).toFixed(2);//税额
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
                                                title: '含税金额',
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
                                                title: '其他费',
                                                width: 120,
                                                dataIndex: 'inFee',
                                                key: 'inFee',
                                                tdEdit: true
                                            },
                                            form: {
                                                type: 'number',
                                                field: 'inFee',
                                                precision: 2,
                                                onChange: async (colVal, thisProps, tableBtnCallbackFn) => {
                                                    if (colVal >= 0 && typeof (colVal) === 'number') {
                                                        // 获取编辑行的值的 标准方案
                                                        const rowData = await tableBtnCallbackFn.getEditedRowData(false);
                                                        const newRowData = {
                                                            ...rowData
                                                        }
                                                        if (this.table.qnnForm.form.getFieldsValue().isDeduct === '1') {
                                                            if (rowData.resAllFeeNoTax) {
                                                                newRowData.inAmt = (Number(colVal) + Number(rowData.ysFee ? rowData.ysFee : 0) + Number(rowData.resAllFeeNoTax)).toFixed(2);
                                                            }
                                                        } else {
                                                            if (rowData.resAllFee) {
                                                                newRowData.inAmt = (Number(colVal) + Number(rowData.ysFee ? rowData.ysFee : 0) + Number(rowData.resAllFee)).toFixed(2);
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
                                                title: '运输费',
                                                width: 120,
                                                dataIndex: 'ysFee',
                                                key: 'ysFee',
                                                tdEdit: true
                                            },
                                            form: {
                                                type: 'number',
                                                field: 'ysFee',
                                                precision: 2,
                                                onChange: async (colVal, thisProps, tableBtnCallbackFn) => {
                                                    if (colVal >= 0 && typeof (colVal) === 'number') {
                                                        // 获取编辑行的值的 标准方案
                                                        const rowData = await tableBtnCallbackFn.getEditedRowData(false);
                                                        const newRowData = {
                                                            ...rowData
                                                        }
                                                        if (this.table.qnnForm.form.getFieldsValue().isDeduct === '1') {
                                                            if (rowData.resAllFeeNoTax) {
                                                                newRowData.inAmt = (Number(colVal) + Number(rowData.inFee ? rowData.inFee : 0) + Number(rowData.resAllFeeNoTax)).toFixed(2);
                                                            }
                                                        } else {
                                                            if (rowData.resAllFee) {
                                                                newRowData.inAmt = (Number(colVal) + Number(rowData.inFee ? rowData.inFee : 0) + Number(rowData.resAllFee)).toFixed(2);
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
                                                title: '退账金额',
                                                width: 120,
                                                dataIndex: 'inAmt',
                                                key: 'inAmt'
                                            },
                                            form: {
                                                type: 'number',
                                                field: 'inAmt',
                                                precision: 2
                                            }
                                        },
                                        {
                                            table: {
                                                title: '备注',
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
                            isInTable: false,
                            table: {
                                title: '经办人',
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
                                title: '仓库经办人',
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
                            table: {
                                title: '制单人',
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
                                title: '备注',
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
                            isInTable: false,
                            form: {
                                type: 'number',
                                field: 'billType',
                                initialValue: 12,
                                hide: true,
                            }
                        },
                        {
                            table: {
                                title: '单据状态',
                                width: 100,
                                dataIndex: 'billStatus',
                                key: 'billStatus',
                                fixed: 'right',
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
                                field: 'billStatus',
                                hide: true
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: '附件',
                                field: 'fileList',
                                type: 'files',
                                fetchConfig: {
                                    apiName: 'upload'
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
                                Msg.warn('已审核的不能修改!');
                                return false
                            } else {

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
                                    Msg.warn('已审核的不能审核!');
                                } else {
                                    // obj.selectedRows[0].companyID = '1';
                                    confirm({
                                        content: '确定审核选中的数据吗?',
                                        onOk: () => {
                                            myFetch('checkZxSkStockTransferSalesReturn', { id: obj.selectedRows[0].id }).then(
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
                                Msg.warn('只能审核一条数据！')
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
                                Msg.warn('请选择一条数据！')
                            }

                        },
                        // onClickPrint: (obj) => {

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
                                    //存在已审核的数据
                                    arry.push(obj.selectedRows[m].billStatus);
                                }
                            }
                            if (arry.length > 0) {
                                Msg.warn('请选择未审核的数据！')
                            } else {
                                confirm({
                                    content: '确定删除选中的数据吗?',
                                    onOk: () => {
                                        myFetch('batchDeleteUpdateZxSkStockTransferSalesReturn', obj.selectedRows).then(
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
                    //                     apiName: 'addZxSkStockTransferSalesReturn'
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
                    //                     apiName: 'updateZxSkStockTransferSalesReturn'
                    //                 }
                    //             }
                    //         ]
                    //     },
                    //     {
                    //         name: 'diy',
                    //         type: 'primary',
                    //         label: '审核',//接口
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
                    //         onClick: 'bind:onClickPrint'
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
                                tableField: "ImportTaxOutTicket"
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
                    onCancel={this.handleCancelSend}
                    bodyStyle={{ width: '500px' }}
                    centered={true}
                    destroyOnClose={this.handleCancelSend}
                    wrapClassName={'modals'}
                >
                    <Spin spinning={loadingSendData}>
                        <QnnForm
                            {...this.props}
                            match={this.props.match}
                            fetch={this.props.myFetch}
                            upload={this.props.myUpload}
                            headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
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

                                        this.props.myFetch('updateZxSkStockTransferSalesReturn', values).then(
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
                    title="预览"
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
                                this.setState({ loading: false })
                            }} src={`${ureport}preview?_u=minio:ZxSkStockTransferSalesReturn.preview.xml&_t=1,6&_n=${name}&access_token=${access_token}&id=${idPreview}&zxSkStockTransItemSalesReturnId=${idPreview}
                            `}title="byLm"></iframe>
                        </Spin> : ''
                    }

                </Modal>
            </div>
        );
    }
}

export default index;