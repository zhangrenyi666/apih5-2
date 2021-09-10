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
        myFetch('getZxSkStockTransferMaterialRequisitionNo', {
            outOrgID: val,
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
    setTotalAmtFun(rowData) {//计算表格内的总值，显示在表单上
        let djje = 0;
        let tableData = this.table.qnnForm.form.getFieldsValue();
        tableData.zxSkStockTransItemMaterialRequisitionList.map((item) => {
            if (rowData.zongjia === 'zj') {
                if (item.id === rowData.id) {
                    djje += Number(rowData.outAmt);
                } else {
                    djje += Number(item.outAmt);
                }
            } else {
                if (item.id === rowData.id) {
                    djje += Number(this.FloatMulTwo((rowData.stdPrice ? rowData.stdPrice : 0), (rowData.outQty ? rowData.outQty : 0)));
                } else {
                    djje += Number(this.FloatMulTwo((item.stdPrice ? item.stdPrice : 0), (item.outQty ? item.outQty : 0)));
                }
            }

            return item;
        })
        this.table.qnnForm.form.setFieldsValue({
            totalAmt: Number(djje.toFixed(2))
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
            let outOrgID = this.table.qnnForm.form.getFieldsValue().outOrgID;
            let busDate = this.table.qnnForm.form.getFieldsValue().busDate;
            if (outOrgID) {
                this.getDjbhFun(outOrgID, busDate);
            }
        }

    }
    render() {
        const { visibleSendData, loadingSendData, selectedRowsData } = this.state;
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
                        apiName: 'getZxSkStockTransferMaterialRequisitionList',
                        otherParams: {
                            outOrgID: departmentId
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
                                fixed: 'left',
                                width: 320,
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
                            table: {
                                title: '发料单位',
                                dataIndex: 'outOrgName',
                                key: 'outOrgName',
                                width: 200,
                                // type: 'select',
                                filter: true,
                                fieldsConfig: {
                                    field: 'outOrgID',
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
                                field: 'outOrgID',
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
                                        sm: { span: 6 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 24 },
                                        sm: { span: 18 }
                                    }
                                },
                                onChange: (val, rowData) => {
                                    if (this.table && this.table.qnnForm) {
                                        this.table.qnnForm.clearValues(['zxSkStockTransItemMaterialRequisitionList']);
                                        if (rowData.itemData && rowData.itemData.orgName) {
                                            this.table.qnnForm.form.setFieldsValue({
                                                outOrgName: rowData.itemData.orgName
                                            })
                                        }
                                        this.table.qnnForm.form.setFieldsValue({
                                            cantEdit: 'can',
                                            resourceID: '',
                                            whOrgID: '',
                                            inOrgID: ''
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
                                },
                                children: [
                                    {
                                        field: 'inOrgID'
                                    },
                                    {
                                        field: 'whOrgID'
                                    },
                                    {
                                        field: 'cbsID'
                                    }
                                ]
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
                                field: 'outOrgName',
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
                                allowClear: false,
                                required: true,
                                editDisabled: true,
                                type: 'select',
                                optionConfig: {
                                    label: 'warehouseName',
                                    value: 'id',
                                    linkageFields: {
                                        warehouseName: 'warehouseName'
                                    }
                                },
                                parent: 'outOrgID',
                                fetchConfig: {
                                    apiName: 'getZxSkWarehouseByParentOrgIDList',
                                    params: {
                                        parentOrgID: 'outOrgID'
                                    }
                                },
                                placeholder: '请选择',
                                spanForm: 8,
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
                                onChange: (val) => {
                                    if (this.table && this.table.qnnForm) {
                                        this.table.qnnForm.clearValues(['zxSkStockTransItemMaterialRequisitionList']);
                                        this.table.qnnForm.form.setFieldsValue({
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
                                field: 'inOrgName',
                                hide: true
                            }
                        },
                        {
                            table: {
                                title: '领料部门',
                                dataIndex: 'inOrgName',
                                key: 'inOrgName',
                                width: 200,
                                filter: true,
                                fieldsConfig: {
                                    field: 'inOrgName',
                                    type: 'string',
                                }
                            },
                            form: {
                                field: 'inOrgID',
                                required: true,
                                type: 'select',
                                showSearch: true,
                                allowClear: false,
                                optionConfig: {
                                    label: 'name',
                                    value: 'iecsCBSID',
                                    linkageFields: {
                                        inOrgName: 'name'
                                    }
                                },
                                parent: 'outOrgID',
                                fetchConfig: {
                                    apiName: 'getZxEqIecsCBSPickingList',
                                    otherParams: {
                                        cbsType: 4
                                    },
                                    params: {
                                        orgID: 'outOrgID'
                                    }
                                },
                                placeholder: '请选择',
                                spanForm: 8,
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
                                        this.table.qnnForm.clearValues(['zxSkStockTransItemMaterialRequisitionList']);
                                        this.table.qnnForm.form.setFieldsValue({
                                            resourceID: ''
                                        })
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
                            },
                            form: {
                                type: 'number',
                                field: 'totalAmt',
                                addDisabled: true,
                                editDisabled: true,
                                spanForm: 8,
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
                                editDisabled: true,
                                allowClear: false,
                                showSearch: true,
                                optionConfig: {
                                    label: 'resourceName',
                                    value: 'resourceId',
                                    linkageFields: {
                                        "resourceName": "resourceName"
                                    }
                                },
                                dependenciesReRender: true,//多个依赖-配置
                                dependencies: ['whOrgID', 'outOrgID'],
                                fetchConfig: {
                                    apiName: 'getZxSkStockResCategoryDataList',
                                    otherParams: (val) => {
                                        let whOrgIDVal = '';
                                        let outOrgIDVal = '';
                                        if (val.btnCallbackFn?.form) {
                                            let aa = val.btnCallbackFn.form.getFieldsValue();
                                            // if (aa.whOrgID && aa.outOrgID) {
                                            whOrgIDVal = aa.whOrgID;
                                            outOrgIDVal = aa.outOrgID;
                                            // }
                                        } else {
                                            whOrgIDVal = '';
                                            outOrgIDVal = '';
                                        }
                                        return {
                                            whOrgID: whOrgIDVal,
                                            orgID: outOrgIDVal
                                        }
                                    }
                                },
                                placeholder: '请选择',
                                spanForm: 8,
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
                                onChange: (val) => {

                                    if (this.table && this.table.qnnForm) {
                                        this.table.qnnForm.clearValues(['zxSkStockTransItemMaterialRequisitionList']);
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
                                field: 'resourceName',
                                hide: true
                            }
                        },

                        {
                            table: {
                                title: '分部分项',
                                dataIndex: 'cbsName',
                                width: 200,
                                key: 'cbsName',
                                filter: true,
                                fieldsConfig: {
                                    field: 'cbsName',
                                    type: 'string',
                                }
                            },
                            form: {
                                type: 'select',
                                field: 'cbsID',
                                required: true,
                                allowClear: false,
                                optionConfig: {
                                    label: 'name',
                                    value: 'iecsCBSID',
                                    linkageFields: {
                                        'cbsName': "name"
                                    }
                                },
                                parent: 'outOrgID',
                                fetchConfig: {
                                    apiName: 'getZxEqIecsCBSOrgId',
                                    params: {
                                        orgID: 'outOrgID'
                                    }
                                },
                                spanForm: 8,
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
                                type: 'string',
                                field: 'cbsName',
                                hide: true
                            }
                        },
                        {
                            // isInTable:false,
                            table: {
                                title: '凭证号',
                                dataIndex: 'voucherNo',
                                key: 'voucherNo',
                                width: 160,
                                filter: true
                            },
                            form: {
                                type: 'string',
                                field: 'voucherNo',
                                spanForm: 8,
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
                                title: '业务日期',
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
                                        sm: { span: 6 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 24 },
                                        sm: { span: 18 }
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
                                        // if (this.state.disAbled === 'no') {
                                        return 'no'
                                        // } else {
                                        //     return 'can'
                                        // }

                                    } else {
                                        return 'can'
                                    }
                                }
                            }
                        },

                        {
                            isInTable: false,
                            form: {
                                type: 'qnnTable',
                                field: 'zxSkStockTransItemMaterialRequisitionList',
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
                                                        apiName: "getZxSkStockList",
                                                        otherParams: async () => {
                                                            const vals = await this.table.btnCallbackFn.qnnForm.getValues(false);
                                                            return {
                                                                resourceId: vals.resourceID,//分类id
                                                                limit: 999,
                                                                page: 1,
                                                                whOrgID: vals.whOrgID,//仓库id
                                                                orgID: vals.outOrgID,//发料单位id
                                                                // companyID: companyId//公司id
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
                                                            // isInSearch:true,
                                                            table: {
                                                                dataIndex: "stockQty",
                                                                title: "库存",
                                                            },
                                                            form: {
                                                                type: "number"
                                                            }
                                                        },
                                                        {
                                                            isInForm: false,
                                                            // isInSearch:true,
                                                            table: {
                                                                dataIndex: "stockPrice",
                                                                title: "参考单价",
                                                            },
                                                            form: {
                                                                type: "number"
                                                            }
                                                        },
                                                        {
                                                            isInForm: false,
                                                            // isInSearch:true,
                                                            table: {
                                                                dataIndex: "stockAmt",
                                                                title: "参考金额",
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
                                                        const alltol = Number((itemData.itemData[0].stockPrice * itemData.itemData[0].stockQty).toFixed(2));
                                                        tableBtnCallbackFn.setEditedRowData({
                                                            resName: itemData.itemData[0].resName,
                                                            resCode: itemData.itemData[0].resCode,
                                                            resUnit: itemData.itemData[0].unit,
                                                            spec: itemData.itemData[0].spec,
                                                            stdPrice: itemData.itemData[0].stockPrice,
                                                            stockQty: itemData.itemData[0].stockQty,
                                                            outQty: itemData.itemData[0].stockQty,
                                                            resID: itemData.itemData[0].resID,
                                                            outAmt: alltol
                                                        });
                                                        tableBtnCallbackFn.getEditedRowData(false).then((editRow) => {
                                                            editRow.resName = itemData.itemData[0].resName;
                                                            editRow.resUnit = itemData.itemData[0].unit;
                                                            editRow.spec = itemData.itemData[0].spec;
                                                            editRow.stdPrice = itemData.itemData[0].stockPrice;
                                                            editRow.stockQty = itemData.itemData[0].stockQty;
                                                            editRow.outQty = itemData.itemData[0].stockQty;
                                                            editRow.resID = itemData.itemData[0].resID;
                                                            editRow.outAmt = alltol;

                                                            this.setTotalAmtFun(editRow);
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
                                                                stdPrice: 0,
                                                                outQty: 0,
                                                                outAmt: 0

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
                                                title: '单价',
                                                width: 100,
                                                dataIndex: 'stdPrice',
                                                key: 'stdPrice'
                                            },
                                            form: {
                                                type: 'number',
                                                field: 'stdPrice',
                                                precision: 6,
                                            }
                                        },
                                        {
                                            table: {
                                                title: '数量',
                                                width: 120,
                                                dataIndex: 'outQty',
                                                key: 'outQty',
                                                tdEdit: true
                                            },
                                            form: {
                                                type: 'number',
                                                min: 0,
                                                precision: 3,
                                                field: 'outQty',
                                                onChange: async (colVal, thisProps, tableBtnCallbackFn) => {
                                                    if (colVal) {
                                                        clearTimeout(this.tdEditedTimer);
                                                        this.tdEditedTimer = setTimeout(async () => {
                                                            const rowData = await tableBtnCallbackFn.getEditedRowData(false);
                                                            const newRowData = {
                                                                ...rowData
                                                            }
                                                            if (rowData.outQty > rowData.stockQty) {
                                                                Msg.warn('超出库存数量！');
                                                                newRowData.outQty = rowData.stockQty;
                                                            } else {
                                                                rowData.outQty = Number(rowData.outQty.toFixed(2));
                                                                if (rowData.stdPrice) {
                                                                    newRowData.outAmt = Number(this.FloatMulTwo(rowData.stdPrice, rowData.outQty).toFixed(2));

                                                                }
                                                            }
                                                            rowData.zongjia = 'sl';
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
                                                title: '总价',
                                                width: 100,
                                                dataIndex: 'outAmt',
                                                key: 'outAmt',
                                                tdEdit: true
                                            },
                                            form: {
                                                type: 'number',
                                                field: 'outAmt',
                                                precision: 2,
                                                onChange: async (colVal, thisProps, tableBtnCallbackFn) => {
                                                    if (colVal) {
                                                        clearTimeout(this.tdEditedTimer);
                                                        this.tdEditedTimer = setTimeout(async () => {
                                                            const rowData = await tableBtnCallbackFn.getEditedRowData(false);
                                                            rowData.outAmt = Number(colVal.toFixed(2));
                                                            const newRowData = {
                                                                ...rowData
                                                            }
                                                            if (rowData.stdPrice) {
                                                                newRowData.outQty = Number((rowData.outAmt / rowData.stdPrice).toFixed(2));
                                                            }
                                                            rowData.zongjia = 'zj';
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
                                        // {
                                        //     name: "del",
                                        //     icon: 'delete',
                                        //     type: 'danger',
                                        //     label: "删除",
                                        //     onClick: (obj) => {
                                        //         if (this.table && this.table.form) {
                                        //             let tableData = this.table.form.getFieldsValue().zxSkStockTransItemMaterialRequisitionList;

                                        //         }

                                        //     }
                                        // }
                                        {
                                            name: "Diydel",
                                            icon: 'delete',
                                            type: 'danger',
                                            label: "删除",
                                            disabled: "bind:_actionBtnNoSelected",
                                            onClick: (obj) => {
                                                if (this.table && this.table.qnnForm) {
                                                    let allData = this.table.qnnForm.form.getFieldsValue().zxSkStockTransItemMaterialRequisitionList;
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
                                                                    zxSkStockTransItemMaterialRequisitionList: selectRowData
                                                                });
                                                                let djje = 0;
                                                                selectRowData.map((item) => {
                                                                    // if (item.id === obj.selectedRows[0].id) {

                                                                    // } else {
                                                                    djje += Number(item.outAmt);
                                                                    // }
                                                                    return item
                                                                })
                                                                this.table.qnnForm.form.setFieldsValue({
                                                                    totalAmt: Number(djje.toFixed(2))
                                                                });
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
                            isInTable: false,
                            form: {
                                type: 'number',
                                field: 'billType',
                                initialValue: 21,
                                hide: true,
                            }
                        },
                        {
                            isInTable: false,
                            table: {
                                title: '经办人',
                                dataIndex: 'intransactor',
                                key: 'intransactor',
                            },
                            form: {
                                type: 'string',
                                field: 'intransactor',
                                spanForm: 8,
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
                            table: {
                                title: '发料',
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
                            table: {
                                title: '料账',
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
                            table: {
                                title: '操作人',
                                dataIndex: 'auditor',
                                key: 'auditor',
                                filter: true
                            },
                            form: {
                                type: 'string',
                                field: 'auditor',
                                addDisabled: true,
                                editDisabled: true,
                                initialValue: () => {
                                    return this.props.loginAndLogoutInfo.loginInfo.userInfo.realName
                                },
                                spanForm: 8,
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
                            table: {
                                title: '领料',
                                dataIndex: 'billFlag',
                                key: 'billFlag',
                            },
                            form: {
                                type: 'string',
                                field: 'billFlag',
                                spanForm: 8,
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
                                        sm: { span: 3 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 24 },
                                        sm: { span: 21 }
                                    }
                                }
                            }
                        },
                        {
                            table: {
                                title: '审核状态',
                                dataIndex: 'billStatus',
                                key: 'billStatus',
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
                            // isInForm: false,
                            form: {
                                type: 'string',
                                field: 'billStatus',
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
                                },
                                {
                                    name: 'submit',
                                    type: 'primary',
                                    label: '保存',
                                    field: 'addsubmit',
                                    fetchConfig: {
                                        apiName: 'addZxSkStockTransferMaterialRequisition'
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
                                        apiName: 'updateZxSkStockTransferMaterialRequisition'
                                    }
                                }
                            ]
                        },
                        {
                            name: 'diy',
                            type: 'primary',
                            label: '审核',//???没有接口
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
                                                myFetch('checkZxSkStockTransferMaterialRequisition', {id: obj.selectedRows[0].id }).then(
                                                    ({ data, success, message }) => {
                                                        if (success) {
                                                            Msg.warn(message);
                                                            this.table.refresh();
                                                            this.table.clearSelectedRows();
                                                        } else {
                                                            Msg.warn(message)
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
                                if (obj.selectedRows[0].billStatus === '1') {
                                    if (obj.selectedRows.length === 1) {
                                        this.setState({
                                            visibleSendData: true,
                                            loadingSendData: false,
                                            selectedRowsData: obj.selectedRows[0]
                                        })
                                    } else {
                                        Msg.warn('请选择一条数据！')
                                    }
                                } else {
                                    Msg.warn('请选择已审核的数据!');
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
                                            myFetch('batchDeleteUpdateZxSkStockTransferMaterialRequisition', obj.selectedRows).then(
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

                                        this.props.myFetch('updateZxSkStockTransferMaterialRequisition', values).then(
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