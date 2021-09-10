import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import QnnForm from "../../modules/qnn-table/qnn-form";
import { message as Msg, Modal, Spin } from 'antd';
import moment from 'moment';
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
const configBh = {
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
            loadingBjdh: false,
            visibleBjdh: false,
            visibleSendData: false,
            loadingSendData: false,
            selectedRowsData: {},
            outOrgIDData: '',
            abcVal: '',
            companyIDData: '1'
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
    getDjbhFun(val) {
        const { myFetch } = this.props;
        myFetch('getZxSkTurnoverCheckNo', {
            orgID: val
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
    handleCancelBjdh = () => {
        this.setState({ loadingBjdh: false, visibleBjdh: false });
    }
    getTotalFunc(rowData) {//计算表格内的总值，显示在表单上
        let ruzhangAll = 0;
        let tableData = this.table.qnnForm.form.getFieldsValue();
        tableData.zxSkTurnoverCheckItemList.map((item) => {
            if (item.id === rowData.id) {
                ruzhangAll += Number(rowData.inAmt ? rowData.inAmt : 0);
            } else {
                ruzhangAll += Number(item.inAmt ? item.inAmt : 0);
            }
            return item;
        })
        this.table.qnnForm.form.setFieldsValue({
            amt: Number(ruzhangAll)
        })
    }
    render() {
        const { visibleBjdh, loadingBjdh, visibleSendData, loadingSendData, selectedRowsData } = this.state;
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
                        apiName: 'getZxSkTurnoverCheckList',
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
                                field: 'makeoutOrgName',
                                initialValue: companyName,
                                hide: true
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                type: 'string',
                                field: 'makeoutOrgID',
                                initialValue: companyId,
                                hide: true
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

                            },
                        },
                        {
                            table: {
                                title: '收料单位',
                                dataIndex: 'orgID',
                                key: 'orgID',
                                width: 250,
                                type: 'select'
                            },
                            form: {
                                field: 'orgID',
                                required: true,
                                type: 'select',
                                showSearch: true,
                                editDisabled: true,
                                allowClear: false,
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
                                        field: 'outOrgID'
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
                                    this.table.qnnForm.form.setFieldsValue({
                                        cantEdit: 'can',
                                        inOrgName: rowData.itemData.orgName
                                    })
                                    this.getDjbhFun(val);
                                }
                            },
                        },
                        {
                            table: {
                                title: '发票号',
                                width: 200,
                                tooltip: 23,
                                dataIndex: 'invoiceNo',
                                key: 'invoiceNo'
                            },
                            form: {
                                field: 'invoiceNo',
                                type: 'string',
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
                            table: {
                                title: '冲账日期',
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
                            }
                        },
                        // {
                        //     isInTable: false,
                        //     form: {
                        //         type: 'string',
                        //         field: 'makeoutOrgID',
                        //         initialValue: '1aae444f-155a414886d-ac0861d985b3c2f1388eac502112f686',
                        //         hide: true
                        //     }
                        // },
                        {
                            isInTable: false,
                            form: {
                                type: 'string',
                                field: 'ysdID',
                                hide: true
                            }
                        },

                        {
                            isInTable: false,
                            form: {
                                field: 'resourceName',
                                hide: true
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                field: 'warehouseName',
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
                                    label: 'label',
                                    value: 'value'
                                },
                                optionData: [
                                    {
                                        label: "购入",
                                        value: "0"
                                    },
                                    {
                                        label: "调拨",
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
                                }
                            }
                        },

                        {
                            table: {
                                title: '预收单编号',
                                width: 200,
                                tooltip: 23,
                                dataIndex: 'ysdNo',
                                key: 'ysdNo',
                            },
                            form: {
                                type: 'string',
                                field: 'ysdNo',
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
                                labelCanClick: function (obj) {
                                    var rowInfo = obj.clickCb.rowInfo;
                                    if (rowInfo.name === "detail") {
                                        return false
                                    } else {
                                        return true
                                    }
                                },
                                labelClick: (val) => {
                                    let outOrgIDData = this.table.qnnForm.form.getFieldsValue().outOrgID;
                                    let orgIDData = this.table.qnnForm.form.getFieldsValue().orgID;
                                    if (outOrgIDData && orgIDData && this.state.companyIDData) {
                                        this.setState({
                                            visibleBjdh: true,
                                            outOrgIDData: outOrgIDData,
                                            orgIDData: orgIDData
                                        })
                                    } else {
                                        Msg.warn('没有单据数据！')
                                    }

                                }
                            }
                        },
                        {
                            table: {
                                title: '采购类别',
                                dataIndex: 'purchType',
                                key: 'purchType'
                            },
                            form: {
                                type: 'select',
                                field: 'purchType',
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
                                        this.table.qnnForm.form.setFieldsValue({
                                            contractDisabled: 'false'
                                        })
                                    } else {
                                        this.table.qnnForm.form.setFieldsValue({
                                            contractDisabled: 'true'
                                        })
                                    }
                                    this.table.qnnForm.form.setFieldsValue({
                                        contractID: ''
                                    })
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
                                editDisabled: true,
                                type: 'select',
                                showSearch: true,
                                optionConfig: {
                                    label: 'customerName',
                                    value: 'zxCrCustomerNewId',
                                    linkageFields: {
                                        "outOrgName": "customerName"
                                    }
                                },
                                parent: 'orgID',
                                fetchConfig: {
                                    apiName: 'getZxSkTurnoverCheckSupplierList',
                                    otherParams: {
                                        limit: 999,
                                        page: 1
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
                                title: '合同名称',
                                dataIndex: 'contractID',
                                key: 'contractID',
                                type: 'select'
                            },
                            form: {
                                type: 'select',
                                allowClear: false,
                                field: 'contractID',
                                optionConfig: {
                                    label: 'contractName',
                                    value: 'zxCtSuppliesContractId'
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
                                            orgIDVal = aa.orgID;
                                            outOrgIDVal = aa.outOrgID;
                                        } else {
                                            orgIDVal = '';
                                            outOrgIDVal = '';
                                        }
                                        return {
                                            firstId: orgIDVal,
                                            secondID: outOrgIDVal,
                                            pp9: "1",
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
                                            purchType:['!','有合同'],
                                        },
                                        action: ['disabled','unRequired'],
                                    },
                                    {
                                        regex: {
                                            purchType: '有合同'
                                        },
                                        action: 'required'
                                    }
                                ],
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
                                type: 'qnnTable',
                                field: 'zxSkTurnoverCheckItemList',
                                incToForm: true,
                                qnnTableConfig: {
                                    antd: {
                                        rowKey: 'id',
                                        size: 'small'
                                    },
                                    ...configItem,
                                    wrappedComponentRef: (me) => {
                                        this.tableItem = me;
                                    },
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
                                                title: '预收单单号',
                                                width: 200,
                                                tooltip: 20,
                                                dataIndex: 'stockTransBillNo',
                                                key: 'stockTransBillNo',
                                            },
                                            isInForm: false
                                        },
                                        {
                                            table: {
                                                title: '物资编码',
                                                width: 200,
                                                tooltip: 20,
                                                dataIndex: 'resCode',
                                                key: 'resCode',
                                            },
                                            isInForm: false
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
                                                width: 250,
                                                tooltip: 50,
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
                                                title: '数量',//校验预收数量
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
                                                    if (colVal) {
                                                        clearTimeout(this.tdEditedTimer);
                                                        this.tdEditedTimer = setTimeout(async () => {
                                                            const rowData = await tableBtnCallbackFn.getEditedRowData(false);
                                                            const newRowData = {
                                                                ...rowData
                                                            }
                                                            if (colVal > rowData.oldQty) {
                                                                Msg.warn('超出库存数量！');
                                                                newRowData.inQty = rowData.oldQty;
                                                            } else {
                                                                if (rowData.inPriceNoTax) {
                                                                    //张修改
                                                                    var feeSum = 0;
                                                                    if(rowData.oldFeeSum){
                                                                        newRowData.feeSum = this.FloatMulTwo((rowData.oldFeeSum / rowData.oldQty),colVal)
                                                                        feeSum = this.FloatMulTwo((rowData.oldFeeSum / rowData.oldQty),colVal)
                                                                    }
                                                                    if (rowData.isDeduct === '1') {
                                                                        newRowData.inAmt = Number(this.FloatMulTwo(rowData.inQty, colVal).toFixed(2));//原值
                                                                        newRowData.remainAmt = Number(this.FloatMulTwo(rowData.inQty, colVal).toFixed(2)) - Number(feeSum);// 净值
                                                                    }else{
                                                                        newRowData.inAmt = Number(this.FloatMulTwo(rowData.inQty, rowData.inPrice).toFixed(2));//原值
                                                                        newRowData.remainAmt = Number(this.FloatMulTwo(rowData.inQty, rowData.inPrice).toFixed(2)) - Number(feeSum);// 净值
                                                                    }
                                                                    // newRowData.inAmt = Number(this.FloatMulTwo(rowData.inPriceNoTax, colVal).toFixed(2));//原值
                                                                }
                                                            }
                                                            // 计算合计
                                                            this.getTotalFunc(newRowData);
                                                            await tableBtnCallbackFn.setEditedRowData({
                                                                ...newRowData
                                                            });
                                                        }, 600)
                                                    }
                                                }
                                            }
                                        },
                                        {
                                            isInTable: false,
                                            form: {
                                                type: 'number',
                                                field: 'kucun'
                                            }
                                        },
                                        {
                                            isInTable: false,
                                            form: {
                                                type: 'number',
                                                field: 'oldQty'
                                            }
                                        },
                                        //张添加
                                        {
                                            isInTable: false,
                                            form: {
                                                type: 'number',
                                                field: 'oldFeeSum'
                                            }
                                        },
                                        {
                                            table: {
                                                title: '是否抵扣',
                                                dataIndex: 'isDeduct',
                                                key: 'isDeduct',
                                                type: 'select'
                                            },
                                            form: {
                                                field: 'isDeduct',
                                                type: 'select',
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
                                                ]
                                            }
                                        },
                                        {
                                            table: {
                                                title: '税率(%)',
                                                width: 120,
                                                dataIndex: 'taxRate',
                                                key: 'taxRate',
                                                type: 'select'
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
                                                }
                                            }
                                        },
                                        {
                                            table: {
                                                title: '购入不含税单价',
                                                width: 120,
                                                dataIndex: 'inPriceNoTax',
                                                key: 'inPriceNoTax',
                                                tdEdit: true
                                            },
                                            form: {
                                                type: 'number',
                                                field: 'inPriceNoTax',
                                                precision: 6,
                                                onChange: async (colVal, thisProps, tableBtnCallbackFn) => {
                                                    if (colVal) {
                                                        clearTimeout(this.tdEditedTimer);
                                                        this.tdEditedTimer = setTimeout(async () => {
                                                            const rowData = await tableBtnCallbackFn.getEditedRowData(false);
                                                            const newRowData = {
                                                                ...rowData
                                                            }
                                                            let shuilv = 0;
                                                            //张修改
                                                            if (rowData.taxRate) {
                                                                shuilv = Number((rowData.taxRate) * 0.01);
                                                            }
                                                            // if (rowData.taxRate && rowData.isDeduct === '1') {
                                                            //     shuilv = Number((rowData.taxRate) * 0.01);
                                                            // }

                                                            //张修改
                                                            if (rowData.inQty && rowData.isDeduct === '1') {
                                                                newRowData.inAmt = Number(this.FloatMulTwo(rowData.inQty, colVal).toFixed(2));//原值
                                                                newRowData.inPriceTax = Number(this.FloatMulTwo(shuilv, colVal).toFixed(2));// 购入单价税额
                                                                newRowData.inPrice = Number(this.FloatMulTwo((shuilv + 1), colVal).toFixed(2));// 购入单价合计
                                                                newRowData.remainAmt = Number(this.FloatMulTwo(rowData.inQty, colVal).toFixed(2)) - Number(rowData.feeSum != null ? rowData.feeSum : 0);// 净值
                                                            }else{
                                                                newRowData.inPriceTax = Number(this.FloatMulTwo(shuilv, colVal).toFixed(2));// 购入单价税额
                                                                newRowData.inPrice = Number(this.FloatMulTwo((shuilv + 1), colVal).toFixed(2));// 购入单价合计
                                                                var inPrice = Number(this.FloatMulTwo((shuilv + 1), colVal).toFixed(2));// 购入单价合计
                                                                newRowData.inAmt = Number(this.FloatMulTwo(rowData.inQty, inPrice).toFixed(2));//原值
                                                                newRowData.remainAmt = Number(this.FloatMulTwo(rowData.inQty, inPrice).toFixed(2)) - Number(rowData.feeSum != null ? rowData.feeSum : 0);// 净值
                                                            }
                                                            // if (rowData.inQty ) {
                                                            //     newRowData.inAmt = Number(this.FloatMulTwo(rowData.inQty, colVal).toFixed(2));//原值
                                                            //     newRowData.inPriceTax = Number(this.FloatMulTwo(shuilv, colVal).toFixed(2));// 购入单价税额
                                                            //     newRowData.inPrice = Number(this.FloatMulTwo((shuilv + 1), colVal).toFixed(2));// 购入单价合计
                                                            //     newRowData.remainAmt = Number(this.FloatMulTwo(rowData.inQty, colVal).toFixed(2));// 净值
                                                            // }
                                                            // 计算合计
                                                            this.getTotalFunc(newRowData);
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
                                            table: {
                                                title: '购入单价合计',
                                                width: 120,
                                                dataIndex: 'inPrice',
                                                key: 'inPrice'
                                            },
                                            form: {
                                                type: 'number',
                                                field: 'inPrice',
                                                precision: 2
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
                                                precision: 2
                                            }
                                        },
                                        {
                                            table: {
                                                title: '累计摊销',
                                                dataIndex: 'feeSum',
                                                key: 'feeSum'
                                            },
                                            form: {
                                                type: 'number',
                                                field: 'feeSum',
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
                                        }
                                    ],
                                    actionBtns: [
                                        {
                                            name: "addRow",
                                            icon: "plus",
                                            type: "primary",
                                            label: "新增",
                                        },
                                        // {
                                        //     name: "del",
                                        //     icon: 'delete',
                                        //     type: 'danger',
                                        //     label: "删除",
                                        //     onClick: (obj) => {
                                        //         if (this.table && this.table.form) {
                                        //             let tableData = this.table.form.getFieldsValue().zxSkTurnoverCheckItemList;
                                                   
                                        //         }
                                        //     }
                                        // },
                                        {
                                            name: "Diydel",
                                            icon: 'delete',
                                            type: 'danger',
                                            label: "删除",
                                            disabled: "bind:_actionBtnNoSelected",
                                            onClick: (obj) => {
                                                if (this.table && this.table.qnnForm) {
                                                    let allData = this.table.qnnForm.form.getFieldsValue().zxSkTurnoverCheckItemList;
                                                    let selectDa = obj.selectedRows;
                                                    let selectRowData = [];
                                                    confirm({
                                                        content: '确定删除选中的数据吗?',
                                                        onOk: () => {
                                                            let obj = {};
                                                            selectDa.forEach(function (item, index) {
                                                                obj[item.id] = true //将数组arr2中的元素值作为obj 中的键，值为true；
                                                            })
                                                            allData.forEach(function (item, index) {
                                                                if (!obj[item.id]) {
                                                                    selectRowData.push(item)
                                                                } else {
                                                                }
                                                            })

                                                            if (this.table && this.table.qnnForm) {
                                                                this.table.qnnForm.form.setFieldsValue({
                                                                    zxSkTurnoverCheckItemList: selectRowData
                                                                });
                                                                let ruzhangAll = 0;
                                                                selectRowData.map((item) => {
                                                                    // if (item.id === obj.selectedRows[0].id) {
            
                                                                    // } else {
                                                                        ruzhangAll += Number(item.inAmt ? item.inAmt : 0);
                                                                    // }
                                                                    return item;
                                                                })
                                                                this.table.qnnForm.form.setFieldsValue({
                                                                    amt: Number(ruzhangAll)
                                                                })
                                                                this.tableItem.clearSelectedRows();
                                                            }
                                                        }
                                                    });
                                                }


                                            }
                                        }
                                    ]
                                }
                            }
                        },
                        {
                            table: {
                                title: '金额',
                                width: 100,
                                dataIndex: 'amt',
                                key: 'amt',
                                filter: true,
                                fieldsConfig: {
                                    type: 'number',
                                    field: 'amtList',
                                    range: true,
                                }
                            },
                            form: {
                                type: 'number',
                                field: 'amt',
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
                                }
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
                                    field: 'addsubmit',
                                    fetchConfig: {
                                        apiName: 'addZxSkTurnoverCheck'
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
                                if (obj.selectedRows[0].billStatus === '1') {
                                    obj.btnCallbackFn.closeDrawer();
                                    Msg.warn('已审核的不能修改!');
                                } else {
                                    if (obj.selectedRows[0].purchType === '有合同') {
                                        this.setState({
                                            abcVal: 'false'
                                        })
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
                                        apiName: 'updateZxSkTurnoverCheck'
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
                                        obj.selectedRows[0].companyID = '1';
                                        confirm({
                                            content: '确定审核选中的数据吗?',
                                            onOk: () => {
                                                myFetch('checkZxSkTurnoverCheck', obj.selectedRows[0]).then(
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
                                            myFetch('batchDeleteUpdateZxSkTurnoverCheck', obj.selectedRows).then(
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
                            formConfig={[
                                {
                                    field: 'busDate',
                                    type: 'date',
                                    label: "日期",
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

                                        this.props.myFetch('updateZxSkTurnoverCheck', values).then(
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
                <Modal
                    width='1200px'
                    style={{ top: '0' }}
                    title="选择单据"
                    visible={visibleBjdh}
                    footer={null}
                    onCancel={this.handleCancelBjdh}
                    bodyStyle={{ width: '1200px' }}
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
                                rowKey: 'stockTransID',
                                size: 'small'
                            }}
                            fetchConfig={{
                                apiName: 'getZxSkTurnoverCheckReceive',
                                otherParams: {
                                    companyID: this.state.companyIDData,
                                    outOrgID: this.state.outOrgIDData,
                                    orgID: this.state.orgIDData
                                }
                            }}
                            formConfig={[
                                {
                                    isInTable: false,
                                    form: {
                                        field: 'stockTransID',
                                        type: 'string',
                                        hide: true,
                                    }
                                },
                                {
                                    table: {
                                        title: '单据编号',
                                        dataIndex: 'billNo',
                                        key: 'billNo',
                                        width: 300,
                                        filter: true
                                    },
                                    isInForm: false
                                },
                                {
                                    table: {
                                        title: '收料单位',
                                        dataIndex: 'orgID',
                                        key: 'orgID',
                                        width: 200,
                                        type: 'select'
                                    },
                                    form: {
                                        field: 'orgID',
                                        type: 'select',
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
                                    }
                                },
                                {
                                    table: {
                                        title: '物资来源',
                                        dataIndex: 'materialSource',
                                        key: 'materialSource',
                                        type: 'select'
                                    },
                                    form: {
                                        type: 'select',
                                        field: 'materialSource',
                                        optionConfig: {
                                            label: 'label',
                                            value: 'value'
                                        },
                                        optionData: [
                                            {
                                                label: "购入",
                                                value: "0"
                                            },
                                            {
                                                label: "调拨",
                                                value: "1"
                                            }
                                        ],
                                    }
                                },
                                {
                                    table: {
                                        title: '业务日期',
                                        dataIndex: 'busDate',
                                        format: 'YYYY-MM-DD',
                                        key: 'busDate'
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
                                    }
                                },
                            ]}
                            actionBtns={[
                                {
                                    name: 'diy',
                                    type: 'dashed',
                                    label: '取消',
                                    onClick: () => {
                                        this.setState({
                                            loadingBjdh: false,
                                            visibleBjdh: false
                                        })
                                    }
                                },
                                {
                                    name: 'duysubmit',
                                    type: 'primary',
                                    label: '保存',
                                    disabled: "bind:_actionBtnNoSelected",
                                    onClick: (obj) => {
                                        let ruzhangAll = 0;
                                        let zongjiaAll = 0;
                                        let ysdPush = '';//预收单拼接
                                        let ysdIdPush = '';//id拼接
                                        let inListArry = [];
                                        let inList = obj.selectedRows;
                                        for (let k = 0; k < inList.length; k++) {
                                            let newInList = inList[k].zxSkTurnoverInItemList;
                                            for (let m = 0; m < newInList.length; m++) {
                                                inListArry.push(newInList[m]);
                                                zongjiaAll += newInList[m].inAmtAll;
                                                ruzhangAll += newInList[m].inAmt;
                                                if (m === 0) {
                                                    ysdPush += inList[k].billNo + ',';//预收单拼接
                                                    ysdIdPush += inList[k].id + ',';//id拼接
                                                }
                                                newInList[m].ysdNo = inList[k].billNo;

                                            }
                                        }
                                        this.setState({
                                            visibleBjdh: false,
                                            loadingBjdh: false
                                        })
                                        this.table.qnnForm.form.setFieldsValue({
                                            zxSkTurnoverCheckItemList: inListArry,
                                            ysdID: ysdIdPush,//拼接id
                                            ysdNo: ysdPush,//拼接-预收单编号
                                            amt: Number(ruzhangAll),
                                            amtTotal: Number(zongjiaAll)
                                        })
                                    }
                                }
                            ]}
                        />
                    </Spin>
                </Modal>
            </div>
        );
    }
}

export default index;