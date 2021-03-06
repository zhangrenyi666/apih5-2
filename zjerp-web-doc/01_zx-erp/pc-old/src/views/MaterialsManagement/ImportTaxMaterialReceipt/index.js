import React, { Component } from "react";
import QnnTable from "qnn-table";
import QnnForm from "qnn-form";
import moment from 'moment';
import { Avatar, message as Msg, Modal, Spin } from 'antd';
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
    paginationConfig: false,
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
            clickBtnName: ''
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
    getItenList(hetong) {
        const { myFetch } = this.props;
        myFetch('getZxCtSuppliesContrShopListListByContID', {
            isTurnover: 1,//是否周转材（0：是；1：不是）
            contractID: hetong,
            // taxRate: shuilv
        }).then(
            ({ data, success, message }) => {
                if (success) {
                    if (this.table && this.table.qnnForm) {
                        let shuilvHeTong = 0;
                        if (this.table.qnnForm.form.getFieldsValue().taxRate) {
                            shuilvHeTong = Number(Number(this.table.qnnForm.form.getFieldsValue().taxRate) * 0.01);
                        }
                        this.table.qnnForm.form.setFieldsValue({
                            zxSkStockTransItemReceivingList: data.map((item) => {
                                item.canEdit = 'no';
                                item.id = item.zxCtSuppliesContrShopListId;
                                item.resCode = item.workNo;
                                item.resName = item.workName;
                                item.resID = item.workID;
                                item.resUnit = item.unit;
                                item.inQty = item.qty;
                                item.inPrice = item.price;
                                // item.inPriceNoTax = item.priceNoTax;//不含税单价
                                item.inPriceNoTax = (item.inPrice / Number(1 + shuilvHeTong)).toFixed(6);//不含税单价
                                // item.resAllFeeNoTax = item.contractSumNoTax;//不含税金额
                                item.resAllFeeNoTax = Number(((this.FloatMulTwo(item.inQty, item.inPrice)) / Number(1 + shuilvHeTong)).toFixed(2));//不含税金额
                                // item.resAllFee = item.contractSum;//含税金额
                                item.resAllFee = Number(this.FloatMulTwo(item.inQty, item.inPrice).toFixed(2));//含税金额
                                item.remark = item.remarks;
                                item.inFee = 0;
                                item.ysFee = 0;
                                item.inFeeTotal = 0;
                                item.ysFeeTotal = 0;
                                item.precollecte = '0';
                                // item.resAllFeeTax = item.contractSumTax;//税额
                                item.resAllFeeTax = Number((item.resAllFee - item.resAllFeeNoTax).toFixed(2));//税额
                                if (item.inQty && item.inPrice) {
                                    item.inAmtAll = Number(this.FloatMulTwo(item.inQty, item.inPrice).toFixed(2));
                                }
                                if (this.table.qnnForm.form.getFieldsValue().isDeduct === '0') {
                                    item.inAmt = Number(Number(item.resAllFee).toFixed(2));
                                } else {
                                    item.inAmt = Number(Number(item.resAllFeeNoTax).toFixed(2));
                                }
                                return item
                            })
                        });
                    }
                    // Msg.success(message);
                } else {
                    Msg.error(message);
                }
            }
        );
    }
    setTotalAmtFun(rowData) {//计算表格内的总值，显示在表单上
        let djje = 0;
        let djjeRu = 0;
        let tableData = this.formRz.form.getFieldsValue();
        tableData.reviewDetailList.map((item) => {
            if (item.conRevDetailId === rowData.conRevDetailId) {
                djje += Number(rowData.changeContractSum ? rowData.changeContractSum : 0);
                djjeRu += Number(rowData.changeContractSumNoTax ? rowData.changeContractSumNoTax : 0);
            } else {
                djje += Number(this.FloatMulTwo((item.price ? item.price : 0), (item.changeQty ? item.changeQty : 0)));
                djjeRu += Number(this.FloatMulTwo((item.priceNoTax ? item.priceNoTax : 0), (item.changeQty ? item.changeQty : 0)));
            }
            return item;
        })
        this.formRz.form.setFieldsValue({
            contractCost: djje, //总金额
            contractCostNoTax: djjeRu //入账金额
        });

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
                this.table.qnnForm.form.setFieldsValue({
                    busDate: ''
                })
                if (type === 2) {
                    this.formEdit.form.setFieldsValue({
                        busDate: ''
                    })
                }
            }
        } else {//当前日期大于等于26号
            if (time1 < time3) {
                Msg.warn('不能补录本月26号零点前的数据！');
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
    render() {
        const { visibleSend, loadingSend, apiNameValue, contractNoVal, saveApiNameValue, visibleSendData, loadingSendData, selectedRowsData, clickBtnName } = this.state;
        const { departmentId } = this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany;

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
                            inOrgID: departmentId
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
                                    // 审核状态为未审核或者空的标红,如果单据明细中有预收的标蓝,
                                    // 收料单如果单据明细中有预收的并且没有冲账完的，则标蓝。这个规则可以放到做完冲账后再做
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
                                            departmentId: departmentId
                                        }
                                    },
                                }
                            },
                            form: {
                                field: 'inOrgID',
                                required: true,
                                allowClear: false,
                                // type: 'select',
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
                                    apiName: 'getZxCtContractList',
                                    otherParams: {
                                        contrStatus: "1",
                                        orgID: departmentId
                                    }
                                },
                                //使用如下配置控制分页---没看到效果
                                // pagination: {
                                //     pageSizeOptions: [
                                //         10, 20
                                //     ]
                                // },
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
                                title: '仓库',
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
                                            parentOrgID: departmentId
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
                                field: 'resourceName',
                                hide: true
                            }
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
                                title: '供货单位',
                                dataIndex: 'outOrgName',
                                key: 'outOrgName',
                                width: 200,
                                // type: 'select',
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
                                title: '供货单位类型',//???
                                dataIndex: 'outOrgType',
                                key: 'outOrgType',
                                width: 160
                            },
                            form: {
                                field: 'outOrgType',
                                required: true,
                                addDisabled: true,
                                editDisabled: true,
                                initialValue: '物资供应商',
                                type: 'string',
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
                                title: '单据金额',
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
                                title: '物资来源',
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
                            isInTable: false,
                            table: {
                                title: '采购类别',
                                dataIndex: 'purchType',
                                key: 'purchType',
                                type: 'select',
                            },
                            form: {
                                type: 'select',
                                field: 'purchType',
                                initialValue: '暂无合同',
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
                                    if (val === '有合同') {//2021.01.29修改
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
                            table: {
                                title: '采购合同',
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
                                dependenciesReRender: true,//多个依赖-配置
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
                                            this.table.qnnForm.clearValues(['zxSkStockTransItemReceivingList']);
                                        }
                                        this.getItenList(val);
                                    }
                                }
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: '合同编号',
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
                                title: '是否抵扣',
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
                                    // 切换税率，重新计算明细。
                                    // 明细为空，按照税率 = 0计算
                                    if (this.table && this.table.qnnForm) {
                                        this.table.qnnForm.clearValues(['zxSkStockTransItemReceivingList']);
                                    }
                                    let taxRateVal = this.table.qnnForm.form.getFieldsValue();
                                    if (taxRateVal.purchaseContractID) {
                                        this.getItenList(taxRateVal.purchaseContractID);
                                    }
                                }
                            }
                        },

                        {
                            isInTable: false,
                            form: {
                                type: 'qnnTable',
                                field: 'zxSkStockTransItemReceivingList',
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
                                                width: 180,
                                                tooltip: 80,
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
                                                disabled: ({ record }) => {
                                                    if (record.canEdit === 'no') {
                                                        return true
                                                    } else {
                                                        return false
                                                    }
                                                },
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
                                                        apiName: "getZxSkResourceMaterialsListNameJoinResource",
                                                        otherParams: async () => {
                                                            const vals = await this.table.btnCallbackFn.qnnForm.getValues(false);
                                                            return {
                                                                id: vals.resourceID,
                                                                purchaseContractID: vals.purchaseContractID ? vals.purchaseContractID : '',
                                                                contractNo: vals.contractNo ? vals.contractNo : '',
                                                                limit: 999,
                                                                page: 1
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
                                                                dataIndex: "unit",
                                                                title: "单位",
                                                            },
                                                            form: {
                                                                type: "string"
                                                            }
                                                        },
                                                        // {
                                                        //     isInForm: false,
                                                        //     // isInSearch:true,
                                                        //     table: {
                                                        //         dataIndex: "unit",
                                                        //         title: "最终数量",
                                                        //     },
                                                        //     form: {
                                                        //         type: "number"
                                                        //     }
                                                        // },
                                                        // {
                                                        //     isInForm: false,
                                                        //     // isInSearch:true,
                                                        //     table: {
                                                        //         dataIndex: "refpriceType",
                                                        //         title: "含税单价",
                                                        //     },
                                                        //     form: {
                                                        //         type: "number"
                                                        //     }
                                                        // }
                                                    ]
                                                },
                                                onChange: async (colVal, thisProps, tableBtnCallbackFn) => {
                                                    if (colVal) {
                                                        const itemData = thisProps.itemData;
                                                        await tableBtnCallbackFn.setEditedRowData({
                                                            resCode: itemData[0].resCode,
                                                            resName: itemData[0].resName,
                                                            resUnit: itemData[0].unit,
                                                            spec: itemData[0].spec,
                                                            resID: itemData[0].id,
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
                                                                inAmtAll: 0,
                                                                inAmt: 0,
                                                                inFee: 0,
                                                                inFeeTotal: 0,
                                                                ysFee: 0,
                                                                materialsExpensesDetailID: '',
                                                                materialsTransportationDetailID: '',
                                                                ysFeeTotal: 0,
                                                                inAmtAll: 0,

                                                            });
                                                        })
                                                    }

                                                }
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
                                                title: '物资规格',
                                                dataIndex: 'spec',
                                                key: 'spec',
                                                width: 100,
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
                                                title: '实收数量',
                                                width: 100,
                                                dataIndex: 'inQty',
                                                key: 'inQty',
                                                tdEdit: true
                                            },
                                            form: {
                                                type: 'number',
                                                field: 'inQty',
                                                precision: 3, //数值精度
                                                onChange: async (colVal, thisProps, tableBtnCallbackFn) => {
                                                    if (colVal) {
                                                        clearTimeout(this.tdEditedTimer);
                                                        this.tdEditedTimer = setTimeout(async () => {
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

                                                            if (rowData.inPrice) {
                                                                newRowData.resAllFeeNoTax = Number(((this.FloatMulTwo(rowData.inQty, rowData.inPrice)) / Number(1 + shuilv)).toFixed(2));//不含税金额
                                                                newRowData.inPriceNoTax = (rowData.inPrice / Number(1 + shuilv)).toFixed(6);//不含税单价
                                                                newRowData.resAllFee = Number(this.FloatMulTwo(rowData.inQty, rowData.inPrice).toFixed(2));//含税金额
                                                                newRowData.inAmtAll = Number(parseFloat(Number(this.FloatMulTwo(rowData.inQty, rowData.inPrice)) + Number(newRowData.ysFeeTotal) + Number(newRowData.inFeeTotal)).toFixed(2));//含税总价
                                                                if (this.table.qnnForm.form.getFieldsValue().isDeduct === '0') {//参与抵扣-0否   1是
                                                                    newRowData.inAmt = Number(parseFloat(Number(newRowData.resAllFee) + Number(newRowData.ysFeeTotal) + Number(newRowData.inFeeTotal)).toFixed(2));//入账-金额
                                                                } else {
                                                                    newRowData.inAmt = Number(parseFloat(Number(newRowData.resAllFeeNoTax) + Number(newRowData.ysFeeTotal) + Number(newRowData.inFeeTotal)).toFixed(2));//入账-金额
                                                                }
                                                                if (newRowData.resAllFee && newRowData.resAllFeeNoTax) {
                                                                    newRowData.resAllFeeTax = Number((newRowData.resAllFee - newRowData.resAllFeeNoTax).toFixed(2));//税额
                                                                }
                                                            }
                                                            if (rowData.inPrice) {
                                                                newRowData.inPriceNoTax = Number((rowData.inPrice / Number(1 + shuilv)).toFixed(6));//不含税单价
                                                            }
                                                            await tableBtnCallbackFn.setEditedRowData({
                                                                ...newRowData
                                                            });
                                                            this.table.qnnForm.form.setFieldsValue({
                                                                cantEdit: 'no',
                                                                cantEditShui: 'no'
                                                            })
                                                        }, 600)
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
                                                precision: 6, //数值精度
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
                                                precision: 2, //数值精度
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
                                                precision: 2, //数值精度
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
                                                required: true,
                                                precision: 6,
                                                disabled: ({ record }) => {
                                                    if (record.canEdit === 'no') {
                                                        return true
                                                    } else {
                                                        return false
                                                    }
                                                },
                                                onChange: async (colVal, thisProps, tableBtnCallbackFn) => {
                                                    if (colVal) {
                                                        clearTimeout(this.tdEditedTimer);
                                                        // 设置编辑行值方法
                                                        // 节流设置
                                                        this.tdEditedTimer = setTimeout(async () => {
                                                            // 获取编辑行的值的 标准方案
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
                                                                newRowData.resAllFeeNoTax = ((this.FloatMulTwo(rowData.inQty, rowData.inPrice)) / Number(1 + shuilv)).toFixed(2);//不含税金额
                                                                newRowData.resAllFee = Number(this.FloatMulTwo(rowData.inQty, rowData.inPrice).toFixed(2));//含税金额
                                                                newRowData.inAmtAll = parseFloat(Number(this.FloatMulTwo(rowData.inQty, rowData.inPrice)) + Number(newRowData.ysFeeTotal) + Number(newRowData.inFeeTotal)).toFixed(2);//含税总价

                                                                if (this.table.qnnForm.form.getFieldsValue().isDeduct === '0') {//参与抵扣-0否   1是

                                                                    newRowData.inAmt = parseFloat(Number(newRowData.resAllFee) + Number(newRowData.ysFeeTotal) + Number(newRowData.inFeeTotal)).toFixed(2);//入账-金额
                                                                } else {

                                                                    newRowData.inAmt = parseFloat(Number(newRowData.resAllFeeNoTax) + Number(newRowData.ysFeeTotal) + Number(newRowData.inFeeTotal)).toFixed(2);//入账-金额
                                                                }

                                                                if (newRowData.resAllFee && newRowData.resAllFeeNoTax) {
                                                                    newRowData.resAllFeeTax = (newRowData.resAllFee - newRowData.resAllFeeNoTax).toFixed(2);//税额
                                                                }
                                                            }
                                                            newRowData.inPriceNoTax = (rowData.inPrice / Number(1 + shuilv)).toFixed(6);//不含税单价
                                                            await tableBtnCallbackFn.setEditedRowData({
                                                                ...newRowData
                                                            });
                                                            this.table.qnnForm.form.setFieldsValue({
                                                                cantEdit: 'no',
                                                                cantEditShui: 'no'
                                                            })
                                                        }, 600)
                                                    }


                                                }
                                            }
                                        },
                                        {
                                            table: {
                                                title: '含税金额',
                                                width: 120,
                                                dataIndex: 'resAllFee',
                                                key: 'resAllFee',
                                                // tdEdit: true
                                            },
                                            form: {
                                                type: 'number',
                                                field: 'resAllFee',
                                                precision: 2,
                                                disabled: ({ record }) => {
                                                    if (record.canEdit === 'no') {
                                                        return true
                                                    } else {
                                                        return false
                                                    }
                                                },
                                                onChange: async (colVal, thisProps, tableBtnCallbackFn) => {
                                                    if (colVal) {
                                                        clearTimeout(this.tdEditedTimer);
                                                        // 设置编辑行值方法
                                                        // 节流设置
                                                        this.tdEditedTimer = setTimeout(async () => {
                                                            // 获取编辑行的值的 标准方案
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

                                                            newRowData.resAllFeeNoTax = (rowData.resAllFee / Number(1 + shuilv)).toFixed(2);//不含税金额

                                                            if (this.table.qnnForm.form.getFieldsValue().isDeduct === '0') {//参与抵扣-0否   1是

                                                                newRowData.inAmt = parseFloat(Number(rowData.resAllFee) + Number(newRowData.ysFeeTotal) + Number(newRowData.inFeeTotal)).toFixed(2);//入账-金额
                                                            } else {

                                                                newRowData.inAmt = parseFloat(Number(newRowData.resAllFeeNoTax) + Number(newRowData.ysFeeTotal) + Number(newRowData.inFeeTotal)).toFixed(2);//入账-金额
                                                            }

                                                            newRowData.inAmtAll = parseFloat(Number(rowData.resAllFee) + Number(newRowData.ysFeeTotal) + Number(newRowData.inFeeTotal)).toFixed(2);//含税总价
                                                            if (newRowData.resAllFeeNoTax) {
                                                                newRowData.resAllFeeTax = (rowData.resAllFee - newRowData.resAllFeeNoTax).toFixed(2);// 税额
                                                            }
                                                            if (rowData.inQty) {//实收数量
                                                                newRowData.inPrice = (rowData.resAllFee / rowData.inQty).toFixed(6);//含税单价
                                                                newRowData.inPriceNoTax = (newRowData.resAllFeeNoTax / rowData.inQty).toFixed(6);//不含税单价
                                                            }
                                                            await tableBtnCallbackFn.setEditedRowData({
                                                                ...newRowData
                                                            });
                                                            this.table.qnnForm.form.setFieldsValue({
                                                                cantEdit: 'no',
                                                                cantEditShui: 'no'
                                                            })
                                                        }, 600)
                                                    }


                                                }
                                            }
                                        },
                                        {
                                            table: {
                                                title: '入账其他费',
                                                width: 200,
                                                dataIndex: 'inFee',
                                                key: 'inFee',
                                                tdEdit: true
                                            },
                                            form: {
                                                type: 'number',
                                                field: 'inFee',
                                                precision: 2,
                                                disabled: ({ record }) => {
                                                    if (record.canEdit === 'no') {
                                                        return true
                                                    } else {
                                                        return false
                                                    }
                                                },
                                                onChange: async (colVal, thisProps, tableBtnCallbackFn) => {
                                                    if (colVal) {
                                                        clearTimeout(this.tdEditedTimer);
                                                        // 设置编辑行值方法
                                                        // 节流设置
                                                        this.tdEditedTimer = setTimeout(async () => {
                                                            // 获取编辑行的值的 标准方案
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
                                                                newRowData.resAllFeeNoTax = ((this.FloatMulTwo(rowData.inQty, rowData.inPrice)) / Number(1 + shuilv)).toFixed(2);//不含税金额
                                                                newRowData.resAllFee = Number(this.FloatMulTwo(rowData.inQty, rowData.inPrice).toFixed(2));//含税金额
                                                                newRowData.inAmtAll = parseFloat(Number(this.FloatMulTwo(rowData.inQty, rowData.inPrice)) + Number(newRowData.inFeeTotal) + Number(rowData.ysFeeTotal)).toFixed(2);//含税总价

                                                                if (this.table.qnnForm.form.getFieldsValue().isDeduct === '0') {//参与抵扣-0否   1是
                                                                    newRowData.inAmt = parseFloat(Number(newRowData.resAllFee) + Number(rowData.ysFeeTotal) + Number(newRowData.inFeeTotal)).toFixed(2);//入账-金额
                                                                } else {

                                                                    newRowData.inAmt = parseFloat(Number(newRowData.resAllFeeNoTax) + Number(rowData.ysFeeTotal) + Number(newRowData.inFeeTotal)).toFixed(2);//入账-金额
                                                                }

                                                                if (newRowData.resAllFee && newRowData.resAllFeeNoTax) {
                                                                    newRowData.resAllFeeTax = (newRowData.resAllFee - newRowData.resAllFeeNoTax).toFixed(2);//税额
                                                                }
                                                            }
                                                            newRowData.inPriceNoTax = (rowData.inPrice / Number(1 + shuilv)).toFixed(6);//不含税单价
                                                            await tableBtnCallbackFn.setEditedRowData({
                                                                ...newRowData
                                                            });
                                                            this.table.qnnForm.form.setFieldsValue({
                                                                cantEdit: 'no',
                                                                cantEditShui: 'no'
                                                            })
                                                        }, 600)
                                                    }


                                                }
                                            }
                                        },
                                        {
                                            isInTable: (obj) => {
                                                if (obj.qnnFormProps.funcCallBackParams.props.Pstate.drawerDetailTitle === '详情') {
                                                    return false
                                                } else {
                                                    if (obj.qnnFormProps.form.getFieldsValue().purchType === '有合同') {

                                                        return true
                                                    } else {
                                                        return false
                                                    }
                                                }
                                            },
                                            table: {
                                                title: '操作',
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
                                                        if (obj.rowData.materialsExpensesDetailID) {
                                                            this.setState({
                                                                visibleSend: true,
                                                                apiNameValue: 'getZxSkStockFeeDetail',
                                                                contractNoVal: conNo.contractNo,
                                                                ListId: obj.rowData.id,
                                                                materialsExpensesDetailID: obj.rowData.materialsExpensesDetailID,
                                                                saveApiNameValue: 'updateZxSkStockFee',
                                                                clickBtnName: '其他费'
                                                            });
                                                        } else {
                                                            this.setState({
                                                                visibleSend: true,
                                                                apiNameValue: 'getQiTaContractList',
                                                                contractNoVal: conNo.contractNo,
                                                                ListId: obj.rowData.id,
                                                                saveApiNameValue: 'addZxSkStockFee',
                                                                clickBtnName: '其他费'
                                                            });
                                                        }

                                                    } else {
                                                        Msg.warn('合同编号不存在！')
                                                    }
                                                }
                                            },
                                            isInForm: false
                                        },
                                        {
                                            table: {
                                                title: '其他费合计',
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
                                                title: '入账运输费',
                                                width: 120,
                                                dataIndex: 'ysFee',
                                                key: 'ysFee',
                                                tdEdit: true
                                            },
                                            form: {
                                                type: 'number',
                                                field: 'ysFee',
                                                precision: 2,
                                                disabled: ({ record }) => {
                                                    if (record.canEdit === 'no') {
                                                        return true
                                                    } else {
                                                        return false
                                                    }
                                                },
                                                onChange: async (colVal, thisProps, tableBtnCallbackFn) => {
                                                    if (colVal) {
                                                        clearTimeout(this.tdEditedTimer);
                                                        // 设置编辑行值方法
                                                        // 节流设置
                                                        this.tdEditedTimer = setTimeout(async () => {
                                                            // 获取编辑行的值的 标准方案
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
                                                                newRowData.resAllFeeNoTax = ((this.FloatMulTwo(rowData.inQty, rowData.inPrice)) / Number(1 + shuilv)).toFixed(2);//不含税金额
                                                                newRowData.resAllFee = Number(this.FloatMulTwo(rowData.inQty, rowData.inPrice).toFixed(2));//含税金额
                                                                newRowData.inAmtAll = parseFloat(Number(this.FloatMulTwo(rowData.inQty, rowData.inPrice)) + Number(newRowData.ysFeeTotal) + Number(rowData.inFeeTotal)).toFixed(2);//含税总价

                                                                if (this.table.qnnForm.form.getFieldsValue().isDeduct === '0') {//参与抵扣-0否   1是
                                                                    newRowData.inAmt = parseFloat(Number(newRowData.resAllFee) + Number(newRowData.ysFeeTotal) + Number(rowData.inFeeTotal)).toFixed(2);//入账-金额
                                                                } else {
                                                                    newRowData.inAmt = parseFloat(Number(newRowData.resAllFeeNoTax) + Number(newRowData.ysFeeTotal) + Number(rowData.inFeeTotal)).toFixed(2);//入账-金额
                                                                }
                                                                if (newRowData.resAllFee && newRowData.resAllFeeNoTax) {
                                                                    newRowData.resAllFeeTax = (newRowData.resAllFee - newRowData.resAllFeeNoTax).toFixed(2);//税额
                                                                }
                                                            }
                                                            newRowData.inPriceNoTax = (rowData.inPrice / Number(1 + shuilv)).toFixed(6);//不含税单价

                                                            await tableBtnCallbackFn.setEditedRowData({
                                                                ...newRowData
                                                            });
                                                            this.table.qnnForm.form.setFieldsValue({
                                                                cantEdit: 'no',
                                                                cantEditShui: 'no'
                                                            })
                                                        }, 600)
                                                    }


                                                }
                                            }
                                        },
                                        {
                                            isInTable: false,
                                            form: {
                                                type: 'string',//入账其他费明细 存储在外层明细的 id
                                                field: 'materialsExpensesDetailID'
                                            }
                                        },
                                        {
                                            isInTable: false,
                                            form: {
                                                type: 'string',//运输费明细 存储在外层明细的 id
                                                field: 'materialsTransportationDetailID'
                                            }
                                        },
                                        {
                                            isInTable: (obj) => {
                                                if (obj.qnnFormProps.funcCallBackParams.props.Pstate.drawerDetailTitle === '详情') {
                                                    return false
                                                } else {
                                                    if (obj.qnnFormProps.form.getFieldsValue().purchType === '有合同') {
                                                        return true
                                                    } else {
                                                        return false
                                                    }
                                                }
                                            },
                                            table: {
                                                title: '操作',
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
                                                                clickBtnName: '运输费'
                                                            });
                                                        } else {
                                                            this.setState({
                                                                visibleSend: true,
                                                                apiNameValue: 'getYunShuContractList',
                                                                contractNoVal: conNo.contractNo,
                                                                ListId: obj.rowData.id,
                                                                saveApiNameValue: 'addZxSkStockFee',
                                                                clickBtnName: '运输费'
                                                            });
                                                        }

                                                    } else {
                                                        Msg.warn('合同编号不存在！')
                                                    }
                                                }
                                            },
                                            isInForm: false
                                        },
                                        {
                                            table: {
                                                title: '运输费合计',
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
                                                title: '总价',
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
                                                title: '入账金额',
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
                                                title: '是否预收',
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
                                                        label: '否',
                                                        value: '0'
                                                    },
                                                    {
                                                        label: '是',
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
                                                disabled: ({ record }) => {
                                                    if (record.canEdit === 'no') {
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
                                        }
                                    ],
                                    actionBtns: [
                                        {
                                            name: "addRow",
                                            icon: "plus",
                                            type: "primary",
                                            label: "新增",
                                            addRowDefaultData: () => {
                                                return {
                                                    precollecte: '0',
                                                    inFee: 0,
                                                    ysFee: 0,
                                                    inFeeTotal: 0,
                                                    ysFeeTotal: 0
                                                }
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
                                            label: "删除"
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
                                title: '发票号',
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
                            table: {
                                title: '单据状态',
                                width: 100,
                                fixed: 'right',
                                dataIndex: 'billStatus',
                                key: 'billStatus',
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
                                    fetchConfig: {
                                        apiName: 'addZxSkStockTransferReceiving'
                                    },
                                    field: 'addsubmit'
                                }
                            ]
                        },
                        {
                            name: 'edit',
                            icon: 'edit',
                            type: 'primary',
                            label: '修改',
                            onClick: (obj) => {
                                if (obj.selectedRows[0].billStatus === '1') {
                                    obj.btnCallbackFn.closeDrawer();
                                    Msg.warn('已审核的不能修改!');
                                } else {
                                    if (obj.selectedRows[0].purchType === '有合同') {
                                        this.setState({
                                            abcVal: 'false'
                                        })
                                        let selectRow = obj.selectedRows[0].zxSkStockTransItemReceivingList;
                                        if (this.table && this.table.qnnForm) {
                                            this.table.qnnForm.form.setFieldsValue({
                                                zxSkStockTransItemReceivingList: selectRow.map((item) => {
                                                    item.canEdit = 'no';
                                                    return item
                                                })
                                            })
                                        }

                                    } else {
                                        this.setState({
                                            abcVal: 'true'
                                        })
                                    }
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
                                        apiName: 'updateZxSkStockTransferReceiving'
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
                                    if (obj.selectedRows[0].billStatus === '1') {
                                        obj.btnCallbackFn.closeDrawer();
                                        this.table.clearSelectedRows();
                                        Msg.warn('已审核的不能审核!');
                                    } else {
                                        confirm({
                                            content: '确定审核选中的数据吗?',
                                            onOk: () => {
                                                myFetch('checkZxSkStockTransferReceiving', {id: obj.selectedRows[0].id }).then(
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
                                            myFetch('batchDeleteUpdateZxSkStockTransferReceiving', obj.selectedRows).then(
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
                    width='1200px'
                    style={{ top: '0' }}
                    title={clickBtnName === '运输费' ? '入账运输费' : '入账其他费'}
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
                                    if (this.state.materialsExpensesDetailID && clickBtnName === '其他费') {
                                        return {
                                            zxCtFsContractId: this.state.materialsExpensesDetailID
                                        }
                                    } else if (this.state.materialsTransportationDetailID && clickBtnName === '运输费') {
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
                                    label: '附属合同编号',
                                    type: 'string',
                                    field: 'contractNo',
                                    disabled: true,
                                    span: 8
                                },
                                {
                                    label: '附属合同名称',
                                    type: 'string',
                                    field: 'contractName',
                                    disabled: true,
                                    span: 8
                                },
                                {
                                    label: '合同类型',
                                    type: 'string',
                                    field: 'contractType',
                                    disabled: true,
                                    span: 8
                                },
                                {
                                    label: '合同类别',
                                    type: 'select',
                                    field: 'code7',
                                    disabled: true,
                                    span: 8,
                                    optionConfig: {
                                        label: 'label',
                                        value: 'value',
                                    },
                                    optionData: [
                                        { label: '物资其他类', value: '0' },
                                        { label: '物资运输类', value: '1' },
                                    ],
                                },
                                {
                                    label: '乙方名称',
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
                                    label: '总金额',
                                    type: 'number',
                                    field: 'contractCost',
                                    disabled: true,
                                    span: 8
                                },
                                {
                                    label: '入账金额',
                                    type: 'number',
                                    field: 'contractCostNoTax',
                                    disabled: true,
                                    span: 8
                                },
                                {
                                    label: '是否抵扣',
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
                                            label: "否",
                                            value: "0"
                                        },
                                        {
                                            label: "是",
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
                                        tableTdEdit: true,
                                        formConfig: [
                                            {
                                                isInTable: false,
                                                form: {
                                                    label: '主键id合同用',
                                                    field: 'conRevDetailId',
                                                    hide: true
                                                }
                                            },
                                            {
                                                isInTable: false,
                                                form: {
                                                    label: '主键id物资用',
                                                    field: 'zxSkStockFeeItemId',
                                                    hide: true
                                                }
                                            },
                                            {
                                                table: {
                                                    title: '清单编号',
                                                    dataIndex: 'workNo',
                                                    key: 'workNo'
                                                }
                                            },
                                            {
                                                table: {
                                                    title: '清单名称',
                                                    dataIndex: 'workName',
                                                    key: 'workName'
                                                }
                                            },
                                            {
                                                table: {
                                                    title: '计量单位',
                                                    dataIndex: 'unit',
                                                    key: 'unit'
                                                }
                                            },
                                            {
                                                table: {
                                                    title: '合同数量',
                                                    dataIndex: 'qty',
                                                    key: 'qty'
                                                }
                                            },
                                            {
                                                table: {
                                                    title: '含税合同单价',
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
                                                    title: '不含税合同单价',
                                                    dataIndex: 'priceNoTax',
                                                    key: 'priceNoTax'
                                                }
                                            },
                                            {
                                                table: {
                                                    title: '税率(%)',
                                                    dataIndex: 'taxRate',
                                                    key: 'taxRate'
                                                }
                                            },
                                            {
                                                table: {
                                                    title: '含税合同金额',
                                                    dataIndex: 'contractSum',
                                                    key: 'contractSum'
                                                }
                                            },
                                            {
                                                table: {
                                                    title: '不含税合同金额',
                                                    dataIndex: 'contractSumNoTax',
                                                    key: 'contractSumNoTax'
                                                }
                                            },
                                            {
                                                table: {
                                                    title: '数量',
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
                                                        if (typeof (colVal) === 'number' && colVal >= 0) {
                                                            clearTimeout(this.tdEditedTimer);
                                                            this.tdEditedTimer = setTimeout(async () => {
                                                                const rowData = await tableBtnCallbackFn.getEditedRowData(false);
                                                                const newRowData = {
                                                                    ...rowData
                                                                }
                                                                // 金额
                                                                if (rowData.price) {
                                                                    newRowData.changeContractSum = Number(this.FloatMulTwo(colVal, rowData.price));
                                                                }
                                                                if (rowData.priceNoTax) {
                                                                    if (this.formRz.form.getFieldsValue().isDeduct === '0') {//参与抵扣
                                                                        newRowData.changeContractSumNoTax = newRowData.changeContractSum;//入账-金额
                                                                        // 入账金额
                                                                    } else {
                                                                        newRowData.changeContractSumNoTax = Number(this.FloatMulTwo(colVal, rowData.priceNoTax));
                                                                    }
                                                                }
                                                                this.setTotalAmtFun(newRowData);
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
                                                    title: '金额',
                                                    dataIndex: 'changeContractSum',
                                                    key: 'changeContractSum'
                                                }
                                            },
                                            {
                                                table: {
                                                    title: '入账金额',
                                                    dataIndex: 'changeContractSumNoTax',
                                                    key: 'changeContractSumNoTax'
                                                }
                                            },
                                            {
                                                table: {
                                                    title: '备注',
                                                    dataIndex: 'remarks',
                                                    key: 'remarks'
                                                }
                                            }
                                        ],
                                        actionBtns: [
                                            // {
                                            //     name: "addRow",
                                            //     icon: "plus",
                                            //     type: "primary",
                                            //     label: "新增",
                                            // },
                                            // {
                                            //     name: "del",
                                            //     icon: 'delete',
                                            //     type: 'danger',
                                            //     label: "删除",
                                            // }
                                        ]
                                    }
                                },
                                {
                                    type: 'string',
                                    field: 'zxSkStockTransItemReceivingid',
                                    initialValue: this.state.ListId,
                                    hide: true
                                },
                                {
                                    label: '附件',
                                    field: 'fileList',
                                    type: 'files',
                                    fetchConfig: {
                                        apiName: 'upload'
                                    },
                                    formItemLayout: {
                                        labelCol: {
                                            xs: { span: 24 },
                                            sm: { span: 3 }
                                        },
                                        wrapperCol: {
                                            xs: { span: 24 },
                                            sm: { span: 21 }
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
                                            visibleSend: false,
                                            loadingSend: false
                                        })
                                    }
                                },
                                {
                                    name: 'diySubmit',
                                    type: 'primary',
                                    label: '保存',
                                    onClick: (obj) => {
                                        this.setState({
                                            loadingSend: true
                                        })
                                        this.props.myFetch(saveApiNameValue, obj.values).then(
                                            ({ data, success, message }) => {
                                                if (success) {
                                                    Msg.success(message);
                                                    let tableListData = this.table.qnnForm.form.getFieldsValue().zxSkStockTransItemReceivingList;
                                                    // 其他费合计计算
                                                    for (let m = 0; m < tableListData.length; m++) {
                                                        if ((tableListData[m].id === data.zxSkStockTransItemReceivingid) || (tableListData[m].zxCtSuppliesContrShopListId === data.zxSkStockTransItemReceivingid)) {
                                                            if (clickBtnName === '运输费') {
                                                                // 入账运输费
                                                                tableListData[m].ysFeeTotal = data.contractCostNoTax;
                                                                tableListData[m].ysFee = data.contractCostNoTax;
                                                                tableListData[m].materialsTransportationDetailID = data.zxCtFsContractId;
                                                            } else {
                                                                // 入账其他费
                                                                tableListData[m].inFeeTotal = data.contractCostNoTax;
                                                                tableListData[m].inFee = data.contractCostNoTax;
                                                                tableListData[m].materialsExpensesDetailID = data.zxCtFsContractId;
                                                            }

                                                            // 总价
                                                            tableListData[m].inAmtAll = Number((Number(data.contractCostNoTax) + Number(tableListData[m].inAmtAll)).toFixed(2));

                                                            // 入账金额
                                                            tableListData[m].inAmt = Number((Number(data.contractCostNoTax) + Number(tableListData[m].inAmt)).toFixed(2));
                                                        }
                                                    }
                                                    this.table.qnnForm.form.setFieldsValue({
                                                        // 赋值表格
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

                                        this.props.myFetch('updateZxSkStockTransferReceiving', values).then(
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